<%@page import="com.dxjr.utils.DateUtils"%>
<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/page/common/taglib.jsp"%>

<!doctype html>
<html>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="keywords" content="顶玺金融,金融,投资,理财" />
<meta name="description" content="顶玺金融提供安全、有担保的互联网理财投资服务。风险保证金保障！随时可赎回！上顶玺，好收益！">
<meta name="author" content="顶玺金融" />
<meta name="copyright" content="2014 上海顶玺金融信息服务有限公司" />
<%@ include file="/WEB-INF/page/common/public4.jsp"%>
<head>
<title>顶玺金融</title>
</head>

<body>

	<%@ include file="/WEB-INF/page/common/header.jsp"%>
	<!--头部end-->
	<!--main-->
	<div id="myaccount">
		<%@ include file="/WEB-INF/page/common/topmenu.jsp"%>
		<!--banner star-->
		<%@ include file="/WEB-INF/page/common/wraper.jsp"%>



		<div class="wraper mac-box mt20">
			<div class="myacc-list baifen33 clearfix">
				<span class="fr mr20"><a href="javascript:toShowFixXiyi('${fixTenderRealVo.fixBorrowId}');"> 查看定期宝服务协议</a> <a href="javascript:history.back();"
					class="btn-small btn-blue">返回</a></span> <span class="blue f20 ml40">定期宝</span>
				<span> ${fixTenderRealVo.lockLimit}个月
					（${fixTenderRealVo.contractNo}） <a href="javascript:toFixBorrowDetail(${fixTenderRealVo.fixBorrowId});">查看详情</a>
				</span>
			</div>

			<div class="myacc-into clearboth">
				<div class="myacc-dqb fl">
					<p class="my-hq">
						<em>年化收益</em>
						<span class="f20">
						<fmt:formatNumber value="${fixTenderRealVo.apr}" pattern="##.##"/>%
						</span>
					</p>
					<p class="my-hq">
						<em>已赚利息</em><span class="f20 red">${fixTenderRealVo.hasReturnMoneyStr}</span><strong class="gary2">元</strong>
					</p>
					<p class="my-hq">
						<em>加入金额</em><span><fmt:formatNumber
								value="${fixTenderRealVo.account}" pattern="#,##0.00" />元</span>
					</p>
					<p class="my-hq">
						<em>预计收益</em><span>${fixTenderRealVo.yqsyStr}元</span>
					</p>
					<p class="my-hq">
						<em>剩余天数</em> <span>${fixTenderRealVo.remainDay<0?0:fixTenderRealVo.remainDay}天</span>
					</p>
					<p class="my-hq">
						<em>到期日期</em><span>
						<fmt:formatDate value="${fixTenderRealVo.lockEndTime }" pattern="yyyy-MM-dd"/> </span>
					</p>
				</div>

				<div class="myacc-right fl">
					<div class="zhuantai3">已退出</div>
				</div>
			</div>
		</div>


		<!--活期宝star-->
		<div class="wraper mt20">
			<div class="prduct-menu  background clearfix">
				<div class="menu-tbl">
					<ul class="col2">
						<li onclick="load_tb('tab1tb')" class="active">投标信息</li>
						<li  onclick="load_jr('tab2jr')" >加入明细</li>
					</ul>
				</div>
				<div class="menucont">
					<div class="tbl-cont">
						<p>&nbsp;</p>
						<div id="tab1tb" class="product-deatil">
						</div>
					</div>
					<div class="tbl-cont" style="display: none">
						<p>&nbsp;</p>
						<div class="product-deatil" id="tab2jr">
							
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	
		<!--浮动-->
	<div class="clearfix bompd20"></div>
	
	<%@ include file="/WEB-INF/page/common/footer.jsp"%>

	
	
<script type="text/javascript">
	$(function() {
		//alert('init');
		load_tb(document.getElementById("tab1tb"));
	});
	$(function(){
		$("#tzgl").attr("class","active");  //添加样式 
		$("#dqb").attr("class","active");  //添加样式 

	})

	// 加载-收益明细
	function load_tb(obj) {
		//alert('symx');
		//切换选项卡的时候样式处理
		processTabStyle(obj);
		var dataParam = {
			initTag : 0
		};

		$.ajax({
			url : '${basePath}/dingqibao/tab1tbDetail/'+'${fixTenderRealVo.fixBorrowId}'+'/1.html',
			data : dataParam,
			type : 'post',
			success : function(data) {
				//alert(9);
				$("#tab1tb").html(data);
			},
			error : function(data) {
				layer.msg("网络连接异常，请刷新页面或稍后重试！", 1, 5);
			}
		});
	}

	//收益明细 - 分页
	function pageParent_tb(pageNo) {
		$.ajax({
			url : '${basePath}/dingqibao/tab1tbDetail/'+'${fixTenderRealVo.fixBorrowId}'+'/'+
					+ pageNo + '.html',
			type : 'post',
			dataType : 'text',
			success : function(data) {
				$("#tab1tb").html(data);

			},
			error : function(data) {
				layer.msg("网络连接异常，请刷新页面或稍后重试！", 1, 5);
			}
		});
	}

	// 加载-资金信息
	function load_jr(obj) {
		//alert('zjxx');
		//切换选项卡的时候样式处理
		processTabStyle(obj);

		$.ajax({
			url : '${basePath}/dingqibao/tab2jrDetail/'+'${fixTenderRealVo.fixBorrowId}'+'/1.html',
			type : 'post',
			success : function(data) {
				//alert('zjxx_suc');
				$("#tab2jr").html(data);
			},
			error : function(data) {
				layer.msg("网络连接异常，请刷新页面或稍后重试！", 1, 5);
			}
		});
	}


	

	//资金信息 - 分页
	function pageParent_jr(pageNo) {
		$.ajax({
			url : '${basePath}/dingqibao/tab2jrDetail/' +'${fixTenderRealVo.fixBorrowId}'+'/'+
			+ pageNo + '.html',
			type : 'post',
			dataType : 'text',
			success : function(data) {
				$("#tab2jr").html(data);

			},
			error : function(data) {
				layer.msg("网络连接异常，请刷新页面或稍后重试！", 1, 5);
			}
		});
	}



	
	/**
	 * 切换选项卡的时候样式处理
	 */
	function processTabStyle(obj) {
		//alert('qie_huan_style');
		$("#topupInnerDiv").find("li").removeClass("men_li");
		$(obj).parent().addClass("men_li");
	}
	/**
	 *  show 协议
	 */
	function toShowFixXiyi(id) {
		$.layer({
			type : 2,
			fix : false,
			shade : [ 0.6, '#E3E3E3', true ],
			shadeClose : true,
			border : [ 10, 0.7, '#272822', true ],
			title : [ '', false ],
			offset : [ '50px', '' ],
			area : [ '1075px', '450px' ],
			iframe : {
				src : '${basePath }/dingqibao/toShowFixXiyi/' + id
						+ '.html'
			},
			close : function(index) {
				layer.close(index);
			}
		});
	}
	/**
	 * 定期宝详细
	 */
	function toFixBorrowDetail(fixBorrowId){
		window.open("${basePath}/dingqibao/"+fixBorrowId+".html"); 
	};
	
</script>

	
</body>
</html>
