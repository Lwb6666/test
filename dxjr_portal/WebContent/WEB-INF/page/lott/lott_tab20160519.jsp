<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/page/common/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ include file="/WEB-INF/page/common/public.jsp"%>
<title>我的账户_有奖活动_我的奖励</title>
</head>

<body style="background:#f9f9f9;">
	<!--头部开始-->
	<%@ include file="/WEB-INF/page/common/header.jsp"%>
	<!--头部结束-->

	<!--我的账户左侧开始-->
	<div id="Container">
		<div id="Bmain">
			<div class="title">
				<span class="home"> <a href="${path}/">顶玺金融</a></span><span>
					<a href="${path}/myaccount/toIndex.html">我的账户</a>
				</span><span> <a href="#">有奖活动</a></span><span> <a
					href="${path }/myaccount/cashRecord/toCashIndex.html">我的奖励</a></span>
			</div>


			<!--我的账户左侧 开始 -->
			<%@ include file="/WEB-INF/page/account/myAccount_leftMenu.jsp"%>
			<!--我的账户左侧 结束 -->

			<!--我的账户右侧开始 -->
			<div class="lb_waikuang">
				<div class="men_title col4">
					<ul id="topupInnerDiv">
					    <li class="men_li"><a id="hongbao_tab"
							href="javascript:void(0);" onclick="load_hongbao_lott(this)">我的红包</a>
						</li>
						<li class="men_li"><a id="link_xj_lott"
							href="javascript:void(0);" onclick="load_xj_lott(this)">我的现金奖励</a>
						</li>
						<li id="link_sw_lott"><a id="sw_tab" href="javascript:void(0);"
							onclick="load_sw_lott(this)">我的实物奖励 </a></li>
						<li id="link_jljl_lott"><a id="award_tab" href="javascript:void(0);"
							onclick="load_jljl_lott(this)">我的获奖记录</a></li>
					</ul>
				</div>
				<div class="clear"></div>
				<div id="containerRight"></div>
			</div>
			<!--我的账户右侧结束 -->


		</div>
	</div>

	<%@ include file="/WEB-INF/page/common/footer.jsp"%>
</body>
<script type="text/javascript">
	$(function() {
		if('${tabType}' == '1'){
			load_xj_lott(document.getElementById("link_xj_lott"));
		}else if('${tabType}' == '2'){
			load_sw_lott(document.getElementById("sw_tab"));		
		}else if('${tabType}' == '3'){
			load_jljl_lott(document.getElementById("award_tab"));		
		}else{
			load_hongbao_lott(document.getElementById("hongbao_tab"));
			$(".redDot").hide();
		}
			
	});

	/**
	 * 加载 实物奖励
	 */
	function load_sw_lott(obj) {
		//切换选项卡的时候样式处理
		processTabStyle(obj);
		//alert('sw');
		$.ajax({
			url : '${basePath}/lottery_use_record/sw_lott/1.html',
			data : {
				status : $('#status').val()
			},
			type : 'post',
			dataType : 'text',
			success : function(data) {
				$("#containerRight").html(data);

			},
			error : function(data) {
				layer.msg("网络连接异常，请刷新页面或稍后重试！", 1, 5);
			}
		});
	}

	/**
	 * 加载 jljl
	 */
	function load_jljl_lott(obj) {
		//切换选项卡的时候样式处理
		processTabStyle(obj);
		//alert('jljl');
		$.ajax({
			url : '${basePath}/lottery_use_record/jljl_lott/1.html',
			data : {
				status : $('#status').val()
			},
			type : 'post',
			dataType : 'text',
			success : function(data) {
				$("#containerRight").html(data);

			},
			error : function(data) {
				layer.msg("网络连接异常，请刷新页面或稍后重试！", 1, 5);
			}
		});
	}
	/**
	 *  红包 加载的红包页面
	 */
	function load_hongbao_lott(obj) {
		processTabStyle(obj);
		$.ajax({
			url : '${basePath}/lottery_use_record/lott_hongbao/1.html',
			type : 'post',
			dataType : 'text',
			success : function(data) {
				$("#containerRight").html(data);

			},
			error : function(data) {
				layer.msg("网络连接异常，请刷新页面或稍后重试！", 1, 5);
			}
		});
	}
	/**
	 *  xj 奖励页面
	 */
	function lott_xj_pageParent(pageNo) {
		 
		$.ajax({
			url : '${basePath}/lottery_use_record/xj_lott/' + pageNo + '.html',
			type : 'post',
			dataType : 'text',
			success : function(data) {
				$("#containerRight").html(data);

			},
			error : function(data) {
				layer.msg("网络连接异常，请刷新页面或稍后重试！", 1, 5);
			}
		});
	}

	/**
	 *  sw 奖励页面
	 */
	function lott_sw_pageParent(pageNo) {
		//alert(1);
		$.ajax({
			url : '${basePath}/lottery_use_record/sw_lott/' + pageNo + '.html',
			type : 'post',
			dataType : 'text',
			success : function(data) {
				$("#containerRight").html(data);

			},
			error : function(data) {
				layer.msg("网络连接异常，请刷新页面或稍后重试！", 1, 5);
			}
		});
	}
  
	/**
	 *  jljl 页面
	 */
	function lott_jljl_pageParent(pageNo) {
		//alert('jljl');
		$.ajax({
			url : '${basePath}/lottery_use_record/jljl_lott/' + pageNo
					+ '.html',
			type : 'post',
			dataType : 'text',
			success : function(data) {
				$("#containerRight").html(data);

			},
			error : function(data) {
				layer.msg("网络连接异常，请刷新页面或稍后重试！", 1, 5);
			}
		});
	}
    function lott_hongbao_pageParent(pageNo){
    	$.ajax({
			url : '${basePath}/lottery_use_record/lott_hongbao/' + pageNo
					+ '.html',
			type : 'post',
			dataType : 'text',
			success : function(data) {
				 $("#containerRight").html(data);
				 $('.yema').css('display','block');
				 $('#hb').removeClass("hb-xiala");
				 $('#hb').addClass("hb-xiala1");
				 $('.historyhg').slideDown("fast");
			},
			error : function(data) {
				layer.msg("网络连接异常，请刷新页面或稍后重试！", 1, 5);
			}
		});
    }
	/**
	 * 转可提记录翻页
	 */
	function turnDrawPageParent(pageNo) {
		$.ajax({
			url : '${basePath}/account/topup/toDrawLogRecord/' + pageNo
					+ '.html',
			data : {},
			type : 'post',
			dataType : 'text',
			success : function(data) {
				$("#containerRight").html(data);

			},
			error : function(data) {
				layer.msg("网络连接异常，请刷新页面或稍后重试！", 1, 5);
			}
		});
	}

	/**
	 *  xj
	 */
	function load_xj_lott(obj) {
		//切换选项卡的时候样式处理
		processTabStyle(obj);
		$.ajax({
			url : '${basePath}/lottery_use_record/xj_lott/1.html',
			type : 'post',
			success : function(data) {
				$("#containerRight").html(data);
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
		$("#topupInnerDiv").find("li").removeClass("men_li");
		$(obj).parent().addClass("men_li");
	}
	
</script>
</html>
