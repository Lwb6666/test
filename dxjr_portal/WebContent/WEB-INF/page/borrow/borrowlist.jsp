<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/page/common/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<%@ include file="/WEB-INF/page/common/public.jsp"%>
		<link rel="stylesheet" href="${basePath}/js/thickbox/thickbox.css" type="text/css"/>
		<script type="text/javascript" src="${basePath}/js/thickbox/thickbox.js"></script>
	</head>
	<body>
		<div id="Container">
<div id="body_main">
<div id="left">
	<div id="listTitle">
		<div class="listTitle">
			<ul>
				<li id="borrow_list"><a href="javascript:searchInvestList('list','')">借款列表</a></li>
				<li id="borrow_default" class="selected"><a href="javascript:searchInvestList('default','')">招标中的列表</a></li>
				<li id="borrow_advance"><a href="javascript:searchInvestList('advance','')">预发标列表</a></li>
				<li id="borrow_complete"><a href="javascript:searchInvestList('complete','')">完成列表</a></li>
				<li id="borrow_completeDYB"><a href="javascript:searchInvestList('completeDYB','')">完成的抵押标</a></li>
				<li id="borrow_completeJZB"><a href="javascript:searchInvestList('completeJZB','')">完成的净值标</a></li>
				<li id="borrow_completeDBB"><a href="javascript:searchInvestList('completeDBB','')">完成的担保标</a></li>
				<li id="borrow_completeTJB"><a href="javascript:searchInvestList('completeTJB','')">完成的信用标</a></li>
				<!-- <li id="borrow_completeMB"><a href="javascript:searchInvestList('completeMB','')">完成的秒标</a></li> -->
				<li id="borrow_overdue"><a href="javascript:searchInvestList('overdue','')">逾期列表</a></li>
			</ul>
		</div>				
		<span class="listtt" id="type_tip">排序：
			<a href="javascript:searchInvestListOrder($('#searchType').val(),'apr')"><span id="apr_tip"></span>利率</a>&nbsp;
			<a href="javascript:searchInvestListOrder($('#searchType').val(),'period')"><span id="period_tip"></span>周期</a>&nbsp;
			<a href="javascript:searchInvestListOrder($('#searchType').val(),'schedule')"><span id="schedule_tip"></span>时间</a>&nbsp;
			<a href="javascript:searchInvestListOrder($('#searchType').val(),'account')"><span id="account_tip"></span>金额</a>&nbsp;
		</span>
	</div>
	<div align="right">
	标题：<input type="text" id="search_title" name="search_title" style="height:20px;width:150px;"/>&nbsp;&nbsp;借款人：<input type="text" id="search_username" name="search_username" style="height:20px;width:100px;"/>&nbsp;&nbsp;<input type="button" value="搜&nbsp;&nbsp;索" onclick="search_sumbit();" class="user_buttom"/>
	</div>

	<%--优先列表开始 --%>
	<div id="firstInvestBorrowList"></div>
	<%--优先列表结束 --%>
	<div id="borrowTitle" style="margin-top:5px">
		<div class="btt1"> 头像</div> 
        <div class="btt11"> 标题/借款人</div>
        <div class="btt2"> 周期/类型</div> 
        <div class="btt2"> 金额/利率</div>
        <div class="btt2"> 还款方式/进度</div>
        <div class="btt2"> 发布时间/还款时间</div>
        <div class="btt2"> 投标次数/合同编号</div>
        <div class="btt10"> 状态</div>
	</div>
	<div id="borrowListMid">
		<div id="cur_borrow_list">
		
        </div>
	</div>
	
</div>
</div>
</div>
<form id="investForm" action="">
	<input type="hidden" name="searchType" id="searchType" value="${searchType}"/>
	<input type="hidden" name="searchOrderBy" id="searchOrderBy" value="${searchOrderBy}"/>
	<input type="hidden" name="orderByType" id="orderByType" value="${orderByType}"/>
	<input type="hidden" name="pageNum" id="pageNum" value="${pageNum}"/>
	<input type="hidden" name="pageSize" id="pageSize" value="10"/>
</form>
	</body>
</html>

<script type="text/javascript">
var pageNum_search_status = "0";
var pageNum = $('#pageNum').val();
var pageSize = $('#pageSize').val();

$(document).ready(function(){
	var pageNum = $('#pageNum').val();
	if(pageNum==null || pageNum==""){
		pageNum=1;
		$('#pageNum').val(pageNum);
	}
	searchInvestList('default','');
});

function test(){
	var s='';
	<c:forEach items="${borrow_list}" var="borrow">
	 s=s+'${borrow.name }';
	</c:forEach>
	console.log("s="+s);
}

function searchInvestList(searchType,searchOrderBy){
	var pageNum = $('#pageNum').val();
	$('#searchType').val(searchType);
	$('#searchOrderBy').val(searchOrderBy);
	var orderByType = $('#orderByType').val();

	if(orderByType==null || orderByType==""){
		orderByType = "DESC";
	}
	
	$('#orderByType').val(orderByType);
	$('#searchType').val(searchType);
	$.post("${basePath}/borrow/investment/searchborrowlist/10.html?searchType="+searchType+"&searchOrderBy="+searchOrderBy+"&orderByType="+orderByType+"&pageNum="+pageNum, {
	}, function(data) {
		setlicss_borrow();
		setlicss();
		$("#borrow_"+searchType).addClass("selected");
		$("#cur_borrow_list").html(data);
	});
	//查询优先列表
	searchFirstInvestBorrowList(searchType);
}

function searchInvestListOrder(searchType,searchOrderBy){
	var pageNum = $('#pageNum').val();
	$('#searchType').val(searchType);
	$('#searchOrderBy').val(searchOrderBy);
	var orderByType = $('#orderByType').val();

	if(orderByType==null || orderByType=="" || orderByType=="ASC"){
		orderByType = "DESC";
	}else{
		orderByType = "ASC";
	}
	setlicss();
	if(orderByType == "DESC"){
		$("#"+searchOrderBy+"_tip").html("↓");
		$("#"+searchOrderBy+"_tip").parent().attr("style","color:#ffaa29;");
	}else if(orderByType == "ASC"){
		$("#"+searchOrderBy+"_tip").html("↑");
		$("#"+searchOrderBy+"_tip").parent().attr("style","color:#ffaa29;");
	}
	$('#orderByType').val(orderByType);
	$('#searchType').val(searchType);
	//alert($('#searchOrderBy').val());
	
	var search_title = $("#search_title").val();
	var search_username = $("#search_username").val();
	
	var url="${path}/borrow/investment/searchborrowlist/10.html?searchType="+searchType+"&searchOrderBy="+searchOrderBy+"&orderByType="+orderByType+"&pageNum="+pageNum;
	
	$.post(url, {
		search_title:search_title,search_username:search_username
	}, function(data) {
		$("#cur_borrow_list").html(data);
	});
	//查询优先列表
	searchFirstInvestBorrowList(searchType);
}

function setlicss(){
	$("#apr_tip").html("");
	$("#period_tip").html("");
	$("#schedule_tip").html("");
	$("#account_tip").html("");
	//$("#type_tip").children("a").removeAttr("style");
	$("#apr_tip").parent().removeAttr("style");
	$("#period_tip").parent().removeAttr("style");
	$("#schedule_tip").parent().removeAttr("style");
	$("#account_tip").parent().removeAttr("style");
	$("#advance_tip").parent().removeAttr("style");
}

function setlicss_borrow(){
	$("#borrow_list").removeClass("selected");
	$("#borrow_default").removeClass("selected");
	$("#borrow_complete").removeClass("selected");
	$("#borrow_completeDYB").removeClass("selected");
	$("#borrow_completeJZB").removeClass("selected");
	$("#borrow_completeMB").removeClass("selected");
	$("#borrow_overdue").removeClass("selected");
	$("#borrow_advance").removeClass("selected");
	$("#borrow_completeTJB").removeClass("selected");
}

//搜索
function search_sumbit(){
	var pageNum = $('#pageNum').val();
	var searchType = $("#searchType").val();
	var orderByType = $('#orderByType').val();
	if(orderByType==null || orderByType==""){
		orderByType = "DESC";
	}
	var searchOrderBy = $("#searchOrderBy").val();
	
	var search_title = $("#search_title").val();
	var search_username = $("#search_username").val();
	$.post("${basePath}/borrow/investment/searchborrowlist/10.html?searchType="+searchType+"&searchOrderBy="+searchOrderBy+"&orderByType="+orderByType+"&pageNum="+pageNum, {
		search_title:search_title,search_username:search_username
	}, function(data) {
		$("#cur_borrow_list").html(data);
		setlicss_borrow();
		$("#borrow_"+searchType).addClass("selected");
	});
	//查询优先列表
	searchFirstInvestBorrowList(searchType);
}


/**
 * 查询优先计划投标列表
 */
 function searchFirstInvestBorrowList(searchType){
		//查询优先列表,判断是否是招标的列表
		if("default"!=searchType){
			$("#firstInvestBorrowList").hide();
			return;
		}else{
			$("#firstInvestBorrowList").show();
		}
		var pageNum = 1;
		//查询优先计划开通中的记录
		searchType = "firstBorrow";
		$.post("${basePath}/newdxjr/zhitongche//searchFirstInvestBorrowList.html?searchType="+searchType+"&pageNum="+pageNum+"&pageSize="+1, {
		}, function(data) {
			$("#firstInvestBorrowList").html(data);
		});
}

function goToInvest(borrowId){
	/* $.post("${basePath}/member/isPayPasswordExist.html", {
	}, function(data) {
		if(data == 'notlogin'){
			alert('您还没有登录,请先登录！');
			window.location.href = "${path }/home/login.html";
		}else if(data == 'nopaypassword'){
			alert('您还没有设置交易密码,请先设置交易密码！');
			$("#modifyPasswd").click();
		}else if(data == 'success'){
			window.location.href = "${path }/newdxjr/investment/invest/toInvest.html?id="+borrowId;
		}
	}); */
	window.location.href = "${path}/borrow/borrow/toTender.html?id="+borrowId;
}

function turnPageParent(pageNum){
	var searchType = $('#searchType').val();
	var searchOrderBy = $('#searchOrderBy').val();
	var orderByType = $('#orderByType').val();
	if(orderByType==null || orderByType==""){
		orderByType = "DESC";
	}
	var pageSize = $('#pageSize').val();
	
	var search_title = $("#search_title").val();
	var search_username = $("#search_username").val();
	
	$.post("${basePath}/borrow/investment/searchborrowlist/10.html?searchType="+searchType+"&searchOrderBy="+searchOrderBy+"&orderByType="+orderByType+"&pageNo="+pageNum, {
		search_title:search_title,search_username:search_username
	}, function(data) {
		$("#cur_borrow_list").html(data);
	});
	//查询优先列表
	searchFirstInvestBorrowList(searchType);
}
</script>
