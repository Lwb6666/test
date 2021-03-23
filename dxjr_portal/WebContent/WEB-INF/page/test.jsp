<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/page/common/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ include file="/WEB-INF/page/common/public.jsp"%>
<title>网站账户</title>
<script type="text/javascript">
function findata1(){
	var date = $('#date1').val();
	if(date.length==0){
		layer.alert("查询的日期不能为空！");
	}else{
		$.ajax({
			url : '${basePath}/wdty/api/interface/transaction_info.html',
			data :{date:date
			} ,
			type : 'post',
			dataType : 'text',
			success : function(data){
				$("#content_interface").html(data);
			},
			error : function(data) {
				layer.msg("网络连接异常，请刷新页面或稍后重试！", 2, 5);
		    }
		});
	}
}
function findata2(){
	var date = $('#date2').val();
	if(date.length==0){
		layer.alert("查询的日期不能为空！");
	}else{
		$.ajax({
			url : '${basePath}/wdty/api/interface/loan_info.html',
			data :{date:date
			} ,
			type : 'post',
			dataType : 'text',
			success : function(data){
				$("#content_interface").html(data);
			},
			error : function(data) {
				layer.msg("网络连接异常，请刷新页面或稍后重试！", 2, 5);
		    }
		});
	}
}
function findata3(){
	$.ajax({
		url : '${basePath}/wdzj/api/interface/getNowProjects.html',
		data :{
		} ,
		type : 'post',
		dataType : 'text',
		success : function(data){
			$("#content_interface2").html(data);
		},
		error : function(data) {
			layer.msg("网络连接异常，请刷新页面或稍后重试！", 2, 5);
	    }
	});
}
function findata4(){
	var date = $('#date3').val();
	if(date.length==0){
		layer.alert("查询的日期不能为空！");
	}else{
		$.ajax({
			url : '${basePath}/wdzj/api/interface/getProjectsByDate.html',
			data :{date:date
			} ,
			type : 'post',
			dataType : 'text',
			success : function(data){
				$("#content_interface2").html(data);
			},
			error : function(data) {
				layer.msg("网络连接异常，请刷新页面或稍后重试！", 2, 5);
		    }
		});
	}
}
function findata5(){
	$.ajax({
		url : '${basePath}/wdzj/api/interface/getTodayProjects.html',
		data :{
		} ,
		type : 'post',
		dataType : 'text',
		success : function(data){
			$("#content_interface2").html(data);
		},
		error : function(data) {
			layer.msg("网络连接异常，请刷新页面或稍后重试！", 2, 5);
	    }
	});
}
function findata6(){
	var date = $('#date4').val();
	if(date.length==0){
		layer.alert("查询的日期不能为空！");
	}else{
		$.ajax({
			url : '${basePath}/wdzj/api/interface/getPaiedLoanInfo.html',
			data :{date:date
			} ,
			type : 'post',
			dataType : 'text',
			success : function(data){
				$("#content_interface2").html(data);
			},
			error : function(data) {
				layer.msg("网络连接异常，请刷新页面或稍后重试！", 2, 5);
		    }
		});
	}
}
function findata7(){
	var userName = $("#userName").val();
	var realName = $("#realName").val();
	var cardId = $("#cardId").val();
	if(userName.length==0){
		layer.alert("用户名不能为空！");
	}else if(realName.length==0){
		layer.alert("真实姓名不能为空！");
	}else if(cardId.length==0){
		layer.alert("身份证号不能为空！");
	}else{
		$.ajax({
			url : '${basePath}/wdzj/api/interface/checkUserInfo.html',
			data :{userName:userName,realName:realName,cardId:cardId
			} ,
			type : 'post',
			dataType : 'text',
			success : function(data){
				$("#content_interface2").html(data);
			},
			error : function(data) {
				layer.msg("网络连接异常，请刷新页面或稍后重试！", 2, 5);
		    }
		});
	}
}

function getBorrowsForHSW(){
	var status = $("#hsw_status").val();
	var time_from = $("#time_from").val();
	var time_to = $("#time_to").val();
	var page_size = $("#page_size_1").val();
	var page_index = $("#page_index_1").val();
	
	$.ajax({
		url : '${basePath}/hsw/api/interface/getBorrows.html',
		data :{status:status,time_from:time_from,time_to:time_to,page_size:page_size,page_index:page_index
		} ,
		type : 'post',
		dataType : 'text',
		success : function(data){
			$("#content_interface3").html(data);
		},
		error : function(data) {
			layer.msg("网络连接异常，请刷新页面或稍后重试！", 2, 5);
	    }
	});
}

function getTenderRecordForHSW(){
	var id = $("#borrowID").val();
	if(id.length==0){
		layer.alert("借款标ID不能为空！");
	}else{
		var page_size = $("#page_size_2").val();
		var page_index = $("#page_index_2").val();
		
		$.ajax({
			url : '${basePath}/hsw/api/interface/getTenderRecord.html',
			data :{id:id,page_size:page_size,page_index:page_index
			} ,
			type : 'post',
			dataType : 'text',
			success : function(data){
				$("#content_interface3").html(data);
			},
			error : function(data) {
				layer.msg("网络连接异常，请刷新页面或稍后重试！", 2, 5);
		    }
		});
	}
}

function getOverdueBorrowsForHSW(){
	var page_size = $("#page_size_3").val();
	var page_index = $("#page_index_3").val();
	
	$.ajax({
		url : '${basePath}/hsw/api/interface/getOverdueBorrows.html',
		data :{page_size:page_size,page_index:page_index
		} ,
		type : 'post',
		dataType : 'text',
		success : function(data){
			$("#content_interface3").html(data);
		},
		error : function(data) {
			layer.msg("网络连接异常，请刷新页面或稍后重试！", 2, 5);
	    }
	});
}
</script>
</head>
<body>
网贷天眼测试接口：<br/>
接口1  查询的日期：<input type="text" id="date1" onClick="WdatePicker()" class="Wdate"/>(日期格式为：yyyy-MM-dd)&nbsp;&nbsp;<input type="button" onclick="findata1()" value="获取每日成交量数据" style="height:24px;cursor:pointer;"/><br/><br/>
接口2  查询的日期：<input type="text" id="date2" onClick="WdatePicker()" class="Wdate"/>(日期格式为：yyyy-MM-dd)&nbsp;&nbsp;<input type="button" onclick="findata2()" value="获取平台贷款数据" style="height:24px;cursor:pointer;"/><br/><br/>
<br/>查询结果如下：<br/>
<div id="content_interface"></div>
<br/>
<br/>
网贷之家测试接口：<br/>
接口1  <input type="button" onclick="findata3()" value="获取投标中的标信息" style="height:24px;cursor:pointer;"/><br/><br/>
接口2  查询的日期：<input type="text" id="date3" onClick="WdatePicker()" class="Wdate"/>(日期格式为：yyyy-MM-dd)&nbsp;&nbsp;<input type="button" onclick="findata4()" value="获取满标的标数据" style="height:24px;cursor:pointer;"/><br/><br/>
接口3  <input type="button" onclick="findata5()" value="获取当天满标的标信息" style="height:24px;cursor:pointer;"/><br/><br/>
接口4  查询的日期：<input type="text" id="date4" onClick="WdatePicker()" class="Wdate"/>(日期格式为：yyyy-MM-dd)&nbsp;&nbsp;<input type="button" onclick="findata6()" value="获取某天还款的标信息" style="height:24px;cursor:pointer;"/><br/><br/>
接口5  用户名：<input type="text" id="userName" style="height:24px;"/>&nbsp;&nbsp;
 	  真实姓名：<input type="text" id="realName" style="height:24px;"/>&nbsp;&nbsp;
 	  身份证号：<input type="text" id="cardId" style="height:24px;"/>&nbsp;&nbsp;
 	  <input type="button" onclick="findata7()" value="验证用户是否是平台用户" style="height:24px;cursor:pointer;"/><br/><br/>

<br/>查询结果如下：<br/>
<div id="content_interface2"></div>

<br/>
<br/>
<br/>
海树网测试接口：<br/>
接口1： 状态：
	 <select id="hsw_status" name="status">
		<option value="0">投标中</option>
		<option value="1">已完成</option>
		<option value="2">已逾期</option>
     </select>
	 起始时间：<input type="text" id="time_from" onClick="WdatePicker()" class="Wdate"/>
	 截止时间：<input type="text" id="time_to" onClick="WdatePicker()" class="Wdate"/>
	每页条数：<input type="text" id="page_size_1" value="20"/>  页码：<input type="text" id="page_index_1" value="1"/>
	
	<input type="button" onclick="getBorrowsForHSW()" value="获取借款标" style="height:24px;cursor:pointer;"/>
<br/>
接口2： 借款标ID：<input type="text" id="borrowID"/>
	每页条数：<input type="text" id="page_size_2" value="20"/>  页码：<input type="text" id="page_index_2" value="1"/>
	
	<input type="button" onclick="getTenderRecordForHSW()" value="获取投标记录" style="height:24px;cursor:pointer;"/>
<br/>
接口3： 每页条数：<input type="text" id="page_size_3" value="20"/>  页码：<input type="text" id="page_index_3" value="1"/>
	<input type="button" onclick="getOverdueBorrowsForHSW()" value="获取逾期记录" style="height:24px;cursor:pointer;"/>

<br/>
<br/>
<br/>查询结果如下：<br/>
<div id="content_interface3"></div>
</body>
</html>