<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/page/common/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <title>资金周转_投资理财平台_p2p网络投资理财-顶玺金融官网</title>
	<meta name="keywords" content="资金周转,投资理财平台,p2p网络投资理财" />
	<meta name="description" content="顶玺金融是中国3A信用评级互联网理财借贷平台，如果你想了解更多P2P理财、网络投资理财或者资金周转借贷信息，详情请登陆www.dxjr.com。">
	<%@ include file="/WEB-INF/page/common/public.jsp"%>
	<link href="${basePath}/css/circle.css" rel="stylesheet" type="text/css" />
</head>

<body style="background: #f9f9f9;">
	<!--头部开始-->
	<%@ include file="/WEB-INF/page/common/header.jsp"%>
	<!--头部结束-->
	<div class="clear"></div>
	<!--内容开始-->
	<div id="Bmain">
		<div class="title">
			<span class="home"><a href="${path}/">顶玺金融</a></span> <span><a
				href="javascript:void(0);">我要投资</a></span> <span><a
				href="${path}/quanbutoubiao.html">全部投标</a></span>
			<span class="fr">
				<a href="${path }/zhaiquan.html">[债权转让]</a>
				 <a href="${path}/zhitongche.html">[投标直通车]</a> 
			</span>
				
		</div>
		<div id="rz_main">
			<div class="rztitle">筛选项目</div>
			<div class="rz_borrow1">
				<div class="tbproject">
					<table>

						<tr height="35">
							<td class="rz_font">标的类型：</td>
							<td><span class="tbp_btn selected borrowType"><a
									href="javascript:void(0);" borrowType=''>不限</a></span></td>
							<td><span class="tbp_btn borrowType"><a
									href="javascript:void(0);" borrowType='2'>资产抵押标</a></span></td>
							<td><span class="tbp_btn borrowType"><a
									href="javascript:void(0);" borrowType='5'>机构担保标</a></span></td>
							<td><span class="tbp_btn borrowType"><a
									href="javascript:void(0);" borrowType='1'>信用认证标</a></span></td>
							<td><span class="tbp_btn borrowType"><a
									href="javascript:void(0);" borrowType='3'>净值标</a></span></td>
						</tr>

						<tr>
							<td class="rz_font">标的状态：</td>
							<td><span class="tbp_btn transferStatus"><a
									href="javascript:void(0);" transferStatus="">不限</a></span></td>
									<td><span class="tbp_btn transferStatus"><a href="javascript:void(0);" transferStatus="isAdvanced">预发 </a></span></td>
							<td><span class="tbp_btn selected transferStatus"><a href="javascript:void(0);" transferStatus="isTendering">转让/投标中</a></span></td>
							<td><span class="tbp_btn transferStatus"><a href="javascript:void(0);" transferStatus="isComplete">完成</a></span></td>
						    <td><span class="tbp_btn transferStatus"><a href="javascript:void(0);" transferStatus="isOverdue">逾期</a></span></td>
						</tr>
						
						<tr>
							<td class="rz_font">期限：</td>
							<td><span class="tbp_btn selected remainingTerm"><a href="javascript:void(0);" remainingTerm=''>不限</a></span></td>
							<td><span class="tbp_btn remainingTerm"><a href="javascript:void(0);" remainingTerm='1'>1个月内</a></span></td>
							<td><span class="tbp_btn remainingTerm"><a href="javascript:void(0);" remainingTerm='2'>1-3个月</a></span></td>
							<td><span class="tbp_btn remainingTerm"><a href="javascript:void(0);" remainingTerm='3'>3-6个月</a></span></td>
							<td><span class="tbp_btn remainingTerm"><a href="javascript:void(0);" remainingTerm='4'>6个月以上</a></span></td>
						</tr>

						<tr>
							<td class="rz_font">还款方式：</td>
							<td><span class="tbp_btn selected repayType"><a
									href="javascript:void(0);" repayType=''>不限</a></span></td>
							<td><span class="tbp_btn repayType"><a
									href="javascript:void(0);" repayType='3'>到期还本付息</a></span></td>
							<td><span class="tbp_btn repayType"><a
									href="javascript:void(0);" repayType='2'>按月付息到期还本</a></span></td>
							<td><span class="tbp_btn repayType"><a
									href="javascript:void(0);" repayType='1'>等额本息</a></span></td>
							<td><span class="tbp_btn repayType"><a
									href="javascript:void(0);" repayType='4'>按天还款</a></span></td>
						</tr>

					</table>
				</div>
			</div>
			 
		</div>
		
		<div class="tbsort">
			<div class="tbproject">
				<table width="82%" border="0">
                    <tr>
	                 <td colspan="3"> 
	                       <div class="tbborde">
	                           <a  class="tbhove tbnone" href="${path}/toubiao.html">普通标</a>
	                           <a class="tbhove " href="${path}/zhaiquan.html" >债权转让</a>
	                           <a  class="tbhove sec" href="${path}/jinzhibiao.html">净值标</a> 
	                         </div>
	                  </td>
                    <td width="10%" class="rz_font">排序方式：</td>
                    <td width="10%"><span class="tbtype orderBy"><a href="javascript:void(0);" orderBy="apr" >年化利率<span  id ="apr_order" class="tbtype orderType" orderType="apr"></span></a></span></td>
                    <td width="10%"><span class="tbtype orderBy"><a href="javascript:void(0);"  orderBy="account"  >借款金额<span id="account_order" class="tbtype orderType" orderType="account"></span></a></span ></td>
                    <td width="10%"><span class="tbtype orderBy"><a href="javascript:void(0);"  orderBy="schedule"  >投标进度<span id="schedule_order" class="tbtype orderType" orderType="schedule"></span></a></span></td>
                    <td width="10%"><span class="tbtype orderBy"><a href="javascript:void(0);"  orderBy="timeLimit"  >借款期限<span id="timeLimit_order" class="tbtype orderType" orderType="timeLimit"></span></a></span></td>
                    <td width="10%"><span class="tbtype orderBy"><a href="javascript:void(0);" orderBy="publicTime"  >发布时间<span id="publicTime_order" class="tbtype orderType" orderType="publicTime"></span></a></span></td>
                    <td width="10%"><span class="tbtype orderBy"><a href="javascript:void(0);"  orderBy="succesTime"  >满标时间<span id="succesTime_order" class="tbtype orderType" orderType="succesTime"></span></a></span></td>
                    
                    </tr>
                </table>
			</div>
		</div>
 

		<div id="rz_main" style="margin-top: 0">
 
                                <div class="tbztcr">
                                <span class="top"  style="padding:15px 0px"  ><h3>投标列表&nbsp; 
                                
                                 <a href="${path }/borrow/calculator/toCounter.html"><input type="button" class="but dgreen" value="理财计算器" style="cursor: pointer;" /></a>
                                
                                </h3></span><span class="top" >
                           		    借款编号:<input class="" id="borrowID" type="text" style="width:75px" /> 
                                	标题:<input class="" id="title" type="text" style="width:75px" />  
                                	转让/借款人:<input class="" id="borrowPeople" type="text" style="width:75px" />
                                	发布时间:  
								    <input id="beginTime" class="" type="text" onClick="WdatePicker();" style="width:75px" />-
								    <input id="endTime" class="" type="text" onClick="WdatePicker();" style="width:75px"/>
								
                                	 <input type="button"  id="bSeach" class="but bluess" value="搜索" />
                                	 </span> 
                               <div class="clear"> </div> 
                               
                      		   <div id="dataList"> </div>
                               
                               </div>
                        
		</div>

	</div>

	<%@ include file="/WEB-INF/page/common/footer.jsp"%>
</body>

<script type="text/javascript">
	var BorrowType;
	var RepayType;
	var TransferStatus="isTendering";
	var RemainingTerm;
	var OrderBy;
	var orderType = '';
	var OrderName;
	var Title;
	var BorrowPeople;
	var orderType = '';
	var OrderName;
	var BeginTime;
	var EndTime;
	var BorrowID='';

	$(document).ready(function() {

		loadTransferList(1);
		 $("#bSeach").click(function(){
			    Title= $("#title").val();
			    BorrowPeople =  $("#borrowPeople").val();
			    BeginTime= $("#beginTime").val();
			    EndTime= $("#endTime").val();
			    BorrowID= $("#borrowID").val();
			    searchTransferList(1);
			  });
		
		
		//获取标的类型
		$(".borrowType").click(function() { //注册click事件
			$(".borrowType").removeClass("selected");
			$(this).parent('td').siblings().removeClass("selected"); //移除同辈节点的class
			$(this).parent('td').addClass("selected"); //给当前节点添加class
			BorrowType = $(this).children("a").attr("borrowType"); //赋值
			searchTransferList(1);
		});
		//获取标的状态
		$(".transferStatus").click(function() { //注册click事件
			$(".transferStatus").removeClass("selected");
			$(this).parent('td').siblings().removeClass("selected"); //移除同辈节点的class
			$(this).parent('td').addClass("selected"); //给当前节点添加class
			TransferStatus = $(this).children("a").attr("transferStatus"); //赋值
			searchTransferList(1);
		});
		//获取剩余期限
		$(".remainingTerm").click(function() { //注册click事件
			$(".remainingTerm").removeClass("selected");
			$(this).parent('td').siblings().removeClass("selected"); //移除同辈节点的class
			$(this).parent('td').addClass("selected"); //给当前节点添加class
			RemainingTerm = $(this).children("a").attr("remainingterm"); //赋值
			searchTransferList(1);
		});
		//获取还款方式
		$(".repayType").click(function() { //注册click事件
			$(".repayType").removeClass("selected");
			$(this).parent('td').siblings().removeClass("selected"); //移除同辈节点的class
			$(this).parent('td').addClass("selected"); //给当前节点添加class
			RepayType = $(this).children("a").attr("repayType"); //赋值
			searchTransferList(1);
		});
		//获取排序
		$(".orderBy").click(function() { //注册click事件	
			clear();
			OrderBy = $(this).children("a").attr("orderBy"); //赋值

			//判断是否点击同一个排序
			if (OrderName != OrderBy) {
				orderType = "";
			}
			OrderName = OrderBy;

			//判断箭头朝向后赋值，各个排序独立
			if (orderType == '') {
				$("#" + OrderBy + "_order").html('&uarr;');
				orderType = 'asc';
			} else if (orderType == 'desc') {
				$("#" + OrderBy + "_order").html('&uarr;');
				orderType = 'asc';
			} else if (orderType == 'asc') {
				$("#" + OrderBy + "_order").html('&darr;');
				orderType = 'desc';
			}
			searchTransferList(1);
		});

	});

	//清除所有箭头
	function clear(){
		$("#apr_order").html('');
		$("#account_order").html('');
		$("#schedule_order").html('');
		$("#timeLimit_order").html('');
		$("#publicTime_order").html('');
		$("#succesTime_order").html('');
	}

	function searchTransferList(pageNum) {

		$.ajax({
			url : '${basePath}/queryalltenderList.html',
			data : {
				pageNum : pageNum,
				pageSize : 10,
				'borrowType' : BorrowType,
				'limitTime' : TransferStatus,
				'remainingterm' : RemainingTerm,
				'repayType' : RepayType,
				'orderBy' : OrderBy,
				'orderType' : orderType,
				'title':Title,
				'borrowPeople':BorrowPeople,
				'orderType':orderType,
				'beginTime':BeginTime,
				'endTime':EndTime,
				'borrowID':BorrowID
			},
			type : 'post',
			dataType : 'text',
			success : function(data) {
				$("#dataList").html(data);
			},
			error : function(data) {
				alert("网络连接异常，请刷新页面或稍后重试！");
			}
		});
	}

	function loadTransferList(pageNum) {
		$.ajax({
			url : '${basePath}/queryalltenderList.html',
			data : {
				pageNum : pageNum,
				pageSize : 10,
				'borrowType' : BorrowType,
				'limitTime' : TransferStatus,
				'remainingTerm' : RemainingTerm,
				'repayType' : RepayType,
				'orderBy' : OrderBy,
				'orderType' : orderType,
				'title':Title,
				'borrowPeople':BorrowPeople,
				'orderType':orderType,
				'beginTime':BeginTime,
				'endTime':EndTime,
				'borrowID':BorrowID
			},
			type : 'post',
			dataType : 'text',
			success : function(data) {
				$("#dataList").html(data);
			},
			error : function(data) {
				alert("网络连接异常，请刷新页面或稍后重试！");
			}
		});
	}
</script>
</html>

