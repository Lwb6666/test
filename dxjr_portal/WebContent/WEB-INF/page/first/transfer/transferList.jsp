<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/page/common/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ include file="/WEB-INF/page/common/public.jsp"%>
<link href="${basePath}/css/circle.css" rel="stylesheet" type="text/css" />
<title>直通车转让</title>
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
				href="${path}/zhitongche.html">直通车专区</a></span>
				
			<div class="fr">
				<a href="${path}/toubiao.html">[普通投标]</a> 
				<a href="${path }/zhaiquan.html" >[债权转让]</a>
			</div>
		</div>
		<div class="tblist_title">
			<ul>
				<li><a href="${path}/zhitongche.html">直通车开通</a></li>
				<li class="selected"><a
					href="${path}/zhitongche/zhuanrang.html">直通车转让</a>
					<img style="position: relative;top: -40px;left: 50px;" src="${basePath}/images/new1.gif">
				</li>
			</ul>
		</div>
		<div id="rz_main" class="mt bdn"
			style="margin-top: 0; border-top-style: none;">
			<div class="rztitle">筛选债权转让项目</div>
			<div class="rz_borrow1">
				<div class="tbproject">
					<table>

						<tr height="35">
							<td class="rz_font">资金范围：</td>
							<td><span class="tbp_btn selected acountRange"><a
									href="javascript:void(0);" acountRange=''>不限</a></span></td>
							<td><span class="tbp_btn acountRange"><a
									href="javascript:void(0);" acountRange='1'>1万以内</a></span></td>
							<td><span class="tbp_btn acountRange"><a
									href="javascript:void(0);" acountRange='2'>1-5万</a></span></td>
							<td><span class="tbp_btn acountRange"><a
									href="javascript:void(0);" acountRange='3'>5-10万</a></span></td>
							<td><span class="tbp_btn acountRange"><a
									href="javascript:void(0);" acountRange='4'>10-50万</a></span></td>
							<td><span class="tbp_btn acountRange"><a
									href="javascript:void(0);" acountRange='5'>50万以上</a></span></td>
						</tr>

						<tr height="35">
							<td class="rz_font">开通状态：</td>
							<td><span class="tbp_btn transferStatus"><a
									href="javascript:void(0);" transferStatus="">不限</a></span></td>
							<td><span class="tbp_btn selected transferStatus"><a
									href="javascript:void(0);" transferStatus="2">转让中</a></span></td>
							<td><span class="tbp_btn transferStatus"><a
									href="javascript:void(0);" transferStatus="4">已转让</a></span></td>
						</tr>
					</table>
				</div>
			</div>

		</div>


		<div class="tbsort">
			<div class="tbproject">
				<table width="82%" border="0">
					<tr>
						<td width="14%" class="rz_font">排序方式：</td>
						<td width="19%"><span class="tbtype orderBy"><a
								href="javascript:void(0);" orderBy="addTime">发布时间<span
									id="addTime_order" class="tbtype orderType" orderType="addTime"></span></a></span></td>
						<td width="19%"><span class="tbtype orderBy"><a
								href="javascript:void(0);" orderBy="accountReal">转出价格<span
									id="accountReal_order" class="tbtype orderType"
									orderType="accountReal"></span></a></span></td>
						<td width="19%"><span class="tbtype orderBy"><a
								href="javascript:void(0);" orderBy="awardMoney">奖励金额<span
									id="awardMoney_order" class="tbtype orderType"
									orderType="awardMoney"></span></a></span></td>
						<td width="19%"><span class="tbtype orderBy"><a
								href="javascript:void(0);" orderBy="awardApr">奖励比例<span
									id="awardApr_order" class="tbtype orderType"
									orderType="awardApr"></span></a></span></td>
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
	var AcountRange;
	var RepayType;
	var TransferStatus = 2;
	var OrderBy;
	var orderType = '';
	var OrderName;

	/**
	 * 页面初始化
	 */
	$(document).ready(function() {
		searchTransferList(1);
		//获取标的类型
		$(".acountRange").click(function() { //注册click事件
			$(".acountRange").removeClass("selected");
			$(this).parent('td').siblings().removeClass("selected"); //移除同辈节点的class
			$(this).parent('td').addClass("selected"); //给当前节点添加class
			AcountRange = $(this).children("a").attr("acountRange"); //赋值
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

	/**
	 * 清除所有箭头
	 */
	function clear() {
		$("#accountReal_order").html('');
		$("#addTime_order").html('');
		$("#awardMoney_order").html('');
		$("#awardApr_order").html('');
	}

	/**
	 * 查询直通车转让信息
	 */
	function searchTransferList(pageNum) {

		$.ajax({
			url : '${basePath}/zhitongche/zhuanrang/queryTransferList/'
					+ pageNum + '.html',
			data : {
				'acountRange' : AcountRange,
				'transferStatus' : TransferStatus,
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

