<%@page import="com.dxjr.utils.DateUtils"%>
<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/page/common/taglib.jsp"%>


<!doctype html>
<html>
<head>
<%@ include file="/WEB-INF/page/common/public.jsp"%>
<meta charset="utf-8">
<title>我的账户_投资管理_定期宝_定期宝</title>
</head>

<body>

	<!-- header start   -->
	<%@ include file="/WEB-INF/page/common/header.jsp"%>
	<!-- header end     -->



	<!--   定期宝 s -->

	<div id="Bmain">
		<!-- 导航 s  -->
		<div class="title">
			<span class="home"> <a href="${path}/">顶玺金融</a></span> <span>
				<a href="${path}/myaccount/toIndex.html">我的账户</a>
			</span> <span><a href="#">投资管理</a></span> <span><a
				href="${path}/dingqibao/myAccount.html">定期宝</a></span>
		</div>
		<!-- 导航 e  -->


		<!-- 导航下内容 s  -->
		<div id="menu_centert">

			<!--我的账户左侧 开始 -->
			<%@ include file="/WEB-INF/page/account/myAccount_leftMenu.jsp"%>
			<!--我的账户左侧 结束 -->


			<!--我的账户右侧 - 定期宝 s -->
    <!--我的账户右侧开始 -->
    <div class="lb-waikuang whitebg dqb-top-div">
      <div class="searchbox fl">
        <div class="dqb-lb_title f26 p-r">定期宝 </div>
      </div>
      <div class="fl w848 ow bd-line">
        <div class="fl dqb-current-t">
          <ul class="ul-investment">
            <li class="poudcout-li" style="width:158px">
              <p class="textleft text-height">定期宝已赚收益</p>
              <p style="white-space:nowrap;"><span  class="yhs-eifh" style="color:red; ">
              ${retMap.repayYesAccountYes} 
               </span> <span>(元)</span></p>
            </li>
            <li class="dqb-line"></li>
            <li class="poudcout-li" style="width:158px">
              <p class="textleft text-height">定期宝总金额</p>
              <p style="white-space:nowrap;"><span class="yhs-eifh" style="color:#70b928;">  ${retMap.capital} </span><span>(元)</span></p>
            </li>
            <li class="dqb-line"></li>
            <li class="poudcout-li" style="width:158px  ">
              <p class="textleft text-height">预期总收益</p>
              <p style="white-space:nowrap;"><span  class="yhs-eifh"  style="color:#70b928; "> ${retMap.repayYesAccountNo}  </span><span>(元)</span></p>
            </li>
          </ul>
        </div>
      </div>
    </div>
			<!--我的账户右侧 -定期宝 e -->


			<!--我的账户右侧 - 收益明细tab s -->
			<div class="lb-waikuang">
				<div class="men_title col3">
					<ul id="topupInnerDiv">
						<li class="men_li"><a id="tab1Jrz"
							href="javascript:void(0);" onclick="load_jrz(this)">加入中（<fmt:formatNumber value="${retMap.jrz }" pattern="#,##0" />
							）</a>
						</li>
						<li><a id="tab2Syz" href="javascript:void(0);"
							onclick="load_syz(this)">收益中（<fmt:formatNumber value="${retMap.syz }" pattern="#,##0" />）</a></li>
							
							<li><a id="tab3Ytc" href="javascript:void(0);"
							onclick="load_tcz(this)">已退出（<fmt:formatNumber value="${retMap.ytc }" pattern="#,##0" />）</a></li>
					</ul>
				</div>
				 
				
				
				<div class="clear"></div>
				<div id="containerRight"></div>
			</div>
			<!--我的账户右侧 - 收益明细tab e -->
		</div>
		<!-- 导航下内容 e  -->

	</div>
	<!--   定期宝 e -->
	<!-- ps s -->
	<div class="clearfix"></div>
	<!-- ps e -->

	<!-- foot start -->
	<%@ include file="/WEB-INF/page/common/footer.jsp"%>
	<!-- foot end -->

</body>

<script type="text/javascript">
	$(function() {
		//alert('init');
		load_syz(document.getElementById("tab2Syz"));
	});

	// 加载-收益明细
	function load_jrz(obj) {
		//alert('symx');
		//切换选项卡的时候样式处理
		processTabStyle(obj);
		var dataParam = {
			initTag : 0
		};

		$.ajax({
			url : '${basePath}/dingqibao/tab1JrzDetail/1.html',
			data : dataParam,
			type : 'post',
			success : function(data) {
				//alert(9);
				$("#containerRight").html(data);
			},
			error : function(data) {
				layer.msg("网络连接异常，请刷新页面或稍后重试！", 1, 5);
			}
		});
	}

	// 加载-资金信息
	function load_syz(obj) {
		//alert('zjxx');
		//切换选项卡的时候样式处理
		processTabStyle(obj);

		$.ajax({
			url : '${basePath}/dingqibao/tab2SyzDetail/1.html',
			type : 'post',
			success : function(data) {
				//alert('zjxx_suc');
				$("#containerRight").html(data);
			},
			error : function(data) {
				layer.msg("网络连接异常，请刷新页面或稍后重试！", 1, 5);
			}
		});
	}

	// 加载-资金信息
	function load_tcz(obj) {
		//alert('zjxx');
		//切换选项卡的时候样式处理
		processTabStyle(obj);

		$.ajax({
			url : '${basePath}/dingqibao/tab3TczDetail/1.html',
			type : 'post',
			success : function(data) {
				//alert('zjxx_suc');
				$("#containerRight").html(data);
			},
			error : function(data) {
				layer.msg("网络连接异常，请刷新页面或稍后重试！", 1, 5);
			}
		});
		
	}

	
	//收益明细 - 分页
	function pageParent_jrz(pageNo,lockstyle) {
		var dataParam = {
				tag : lockstyle
			};
		$.ajax({
			url : '${basePath}/dingqibao/tab1JrzDetail/'
					+ pageNo + '.html',
			type : 'post',
			dataType : 'text',
			async: false,
			data : dataParam, 
			success : function(data) {
				$("#containerRight").html(data);

			},
			error : function(data) {
				layer.msg("网络连接异常，请刷新页面或稍后重试！", 1, 5);
			}
		});
		processChangeTabStyle(lockstyle);
	}

	//资金信息 - 分页
	function pageParent_syz(pageNo,lockstyle) {
		var dataParam = {
				tag : lockstyle
			};
		$.ajax({
			url : '${basePath}/dingqibao/tab2SyzDetail/' + pageNo
					+ '.html',
			type : 'post',
			data : dataParam, 
			dataType : 'text',
			async: false,
			success : function(data) {
				$("#containerRight").html(data);

			},
			error : function(data) {
				layer.msg("网络连接异常，请刷新页面或稍后重试！", 1, 5);
			}
		});
		processChangeTabStyle(lockstyle);
	}

	//资金信息 - 分页
	function pageParent_tcz(pageNo,lockstyle) {
		var dataParam = {
				tag : lockstyle
			};
			
		$.ajax({
			url : '${basePath}/dingqibao/tab3TczDetail/' + pageNo
					+ '.html',
			type : 'post',
			data : dataParam, 
			dataType : 'text',
			async: false,
			success : function(data) {
				$("#containerRight").html(data);

			},
			error : function(data) {
				layer.msg("网络连接异常，请刷新页面或稍后重试！", 1, 5);
			}
		});
	}
	
	//收益明细- 查询btn 
	function search_jrz(pageNum, tag,obj) {
		//alert('查询btn = '+ pageNum+' tag = '+ tag);
		var dataParam = {
			tag : tag
		};

		$.ajax({
			url : '${basePath}/dingqibao/tab1JrzDetail/'
					+ pageNum + '.html',
			data : dataParam,
			type : 'post',
			dataType : 'text',
			
			async: false,
			success : function(data) {
				$("#containerRight").html(data);

			},
			error : function(data) {
				layer.msg('网络连接异常，请刷新页面或稍后重试！', 1, 5);
			}
		});
		processInnerTabStyle(obj);
	 
	}

	
		
	//收益明细- 查询btn 
	function search_syz(pageNum, tag,obj) {
		//alert('查询btn = '+ pageNum+' tag = '+ tag);
 
		var dataParam = {
			tag : tag
		};
		$.ajax({
			url : '${basePath}/dingqibao/tab2SyzDetail/'
					+ pageNum + '.html',
			data : dataParam,
			type : 'post',
			dataType : 'text',
			async: false,
			success : function(data) {
				$("#containerRight").html(data);

			},
			error : function(data) {
				layer.msg('网络连接异常，请刷新页面或稍后重试！', 1, 5);
			}
		});
		processInnerTabStyle(obj);
	}
	
	//资金信息- 查询btn 
	function search_tcz(pageNum, tag,obj) {
		//alert('查询btn = '+ pageNum+' tag = '+ tag);
		var dataParam = {
			tag : tag
		};
		$.ajax({
			url : '${basePath}/dingqibao/tab3TczDetail/' + pageNum
					+ '.html',
			data : dataParam,
			type : 'post',
			dataType : 'text',
			async: false,
			success : function(data) {
				//alert('btn-zjxx1');
				$("#containerRight").html(data);

			},
			error : function(data) {
				layer.msg('网络连接异常，请刷新页面或稍后重试！', 1, 5);
			}
		});
		processInnerTabStyle(obj);
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
	 * 切换选项卡的时候样式处理
	 */
	function processInnerTabStyle(obj) {
		//alert('qie_huan_style');
		$("#topupInnerDivDynic").find("li span").removeClass("dqb-lb_title_checked");
		$("#"+obj.id).addClass("dqb-lb_title_checked"); 
				 
	 
	}
	
	/**
	 * 分页时样式处理
	 */
	function processChangeTabStyle(obj) {
		//alert('qie_huan_style');
		$("#topupInnerDivDynic").find("li span").removeClass("dqb-lb_title_checked");
		if(obj==''){
			$("#quanbu").addClass("dqb-lb_title_checked"); 
		}else if(obj=='1'){
			$("#month1").addClass("dqb-lb_title_checked"); 	
		}else if(obj=='3'){
			$("#month3").addClass("dqb-lb_title_checked"); 	
		}
	}
	
</script>


</html>
