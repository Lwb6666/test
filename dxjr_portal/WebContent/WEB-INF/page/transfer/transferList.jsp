<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/page/common/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <title>债权转让_P2P债权转让_债权转让模式-顶玺金融官网</title>
	<meta name="keywords" content="P2P债权转让,债权转让,债权转让模式" />
	<meta name="description" content="顶玺金融债权转让是指债权持有人通过顶玺金融债权转让平台，将债权挂出，将所持有的债权转让给购买人的操作。如果你想了解更多P2P债权转让或者债权转让模式，详情请登陆www.dxjr.com。">

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
				href="${path}/zhaiquan.html">债权转让</a></span>
			<div class="fr">
				 <a href="${path}/toubiao.html">[普通投标]</a> 
				<a href="${path }/zhitongche.html">[直通车专区]</a>
			</div>
				
		</div>
		<div id="rz_main">
			<div class="rztitle">筛选债权转让项目</div>
			<div class="rz_borrow2">
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
							<td><span class="tbp_btn selected transferStatus"><a
									href="javascript:void(0);" transferStatus="2">转让中</a></span></td>
							<td><span class="tbp_btn transferStatus"><a
									href="javascript:void(0);" transferStatus="4">已转让</a></span></td>
						</tr>
						
						<tr>
							<td class="rz_font">剩余期限：</td>
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
			
			<div class="rz_borrow3">   
                               <div class="tbproject">
                                     <table>
                                     <tr>
                                     <th>新手指引</th>
                                     </tr>
                                     <tr>
                                     <td><a href="${path }/bangzhu/11.html#listId=47">什么是债权转让？</a></td>
                                     </tr>
                                     <tr>
                                     <td><a href="${path }/bangzhu/11.html#listId=56">债权价格是如何计算的？</a></td>
                                     </tr>
                                     <tr>
                                     <td><a href="${path }/bangzhu/11.html#listId=60">债权转让有哪些注意事项？</a></td>
                                     </tr>
                                     </table>
                                     </div>
                                     </div>
			
			
		</div>


		<div class="tbsort">
			<div class="tbproject">
				<table width="100%" border="0">
					<tr>
						   <td  width="44%" colspan="4" > 
		                       <div class="tbborde" style="width:440px">
		                            <a class="tbhove"  href="${path}/toubiao.html">普通标</a> 
		                            <a class="tbhove sec" href="${path}/zhaiquan.html" >债权转让</a> 
		                            <a class="tbhove " href="${path}/zhitongche/zhuanrang.html" >直通车转让</a> 
		                            <%-- <a  class="tbhove" style="width:88px" href="${path}/jingzhibiao.html">净值标</a> --%> 
		                         </div>
		                  </td>
						<td width="8%" class="rz_font">排序方式：</td>
						<td width="8%" ><span class="tbtype orderBy"><a
								href="javascript:void(0);" orderBy="apr">现利率<span
									id="apr_order" class="tbtype orderType" orderType="apr"></span></a></span></td>
						<td width="8%"><span class="tbtype orderBy"><a
								href="javascript:void(0);" orderBy="pro">进度<span
									id="pro_order" class="tbtype orderType" orderType="pro"></span></a></span></td>
						<td width="8%"><span class="tbtype orderBy"><a
								href="javascript:void(0);" orderBy="coef">转让系数<span
									id="coef_order" class="tbtype orderType" orderType="coef"></span></a></span></td>
						<td width="8%"><span class="tbtype orderBy"><a
								href="javascript:void(0);" orderBy="accountReal">转出价格<span
									id="accountReal_order" class="tbtype orderType"
									orderType="accountReal"></span></a></span></td>
						<td width="8%"><span class="tbtype orderBy"><a
								href="javascript:void(0);" orderBy="addTime">发布时间<span
									id="addTime_order" class="tbtype orderType"
									orderType="addTime"></span></a></span></td>
						<td width="8%"><span class="tbtype orderBy"><a
								href="javascript:void(0);" orderBy="timeLimit">剩余期限<span
									id="timeLimit_order" class="tbtype orderType"
									orderType="timeLimit"></span></a></span></td>
					</tr>
				</table>
			</div>
		</div>

		<div id="rz_main" style="margin-top: 0">

			<div class="tbztcr">
				<span class="top"><h3>转让列表&nbsp;&nbsp;</h3></span>
				
				<div class="clear"></div>

				<div id="dataList"></div>

			</div>



		</div>

	</div>

	<%@ include file="/WEB-INF/page/common/footer.jsp"%>
</body>

<script type="text/javascript">
	var BorrowType;
	var RepayType;
	var TransferStatus=2;
	var RemainingTerm;
	var OrderBy;
	var orderType = '';
	var OrderName;

	$(document).ready(function() {

		loadTransferList(1);
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
			RemainingTerm = $(this).children("a").attr("remainingTerm"); //赋值
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
	function clear() {
		$("#apr_order").html('');
		$("#pro_order").html('');
		$("#coef_order").html('');
		$("#accountReal_order").html('');
		$("#addTime_order").html('');
		$("#timeLimit_order").html('');
	}

	function searchTransferList(pageNum) {

		$.ajax({
			url : '${basePath}/queryTransferList.html',
			data : {
				pageNum : pageNum,
				pageSize : 10,
				'borrowType' : BorrowType,
				'transferStatus' : TransferStatus,
				'remainingTerm' : RemainingTerm,
				'repayType' : RepayType,
				'orderBy' : OrderBy,
				'orderType' : orderType
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
			url : '${basePath}/queryTransferList.html',
			data : {
				pageNum : pageNum,
				pageSize : 10,
				'borrowType' : BorrowType,
				'transferStatus' : TransferStatus,
				'remainingTerm' : RemainingTerm,
				'repayType' : RepayType,
				'orderBy' : OrderBy,
				'orderType' : orderType
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

