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



	<!--   活期生息 s -->

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


			<!--我的账户右侧 -活期生息 s -->
    <!--我的账户右侧开始 -->
      <div class="lb-waikuang whitebg dqb-top-div">
      <div class="searchbox fl">
        <div class="dqb-lb_title f26 p-r">
        
        定期宝 <span class="f14">${fixTenderRealVo.lockLimit}个月</span> <span class="f14">（${fixTenderRealVo.contractNo}）</span> <a href="javascript:toFixBorrowDetail(${fixTenderRealVo.fixBorrowId});" class="f14" style="color:#3199cc;">查看详情</a> <a class="dqb-back-btn" onclick="javascript:history.back();">返 回</a>
        <a href="javascript:toShowFixXiyi('${fixTenderRealVo.fixBorrowId}');" class="f14 dqb-service-a" style="color:#0000ff;">查看定期宝服务协议</a> </div>
      </div>
      <div class="fl w848 ow bd-line" style="height:155px;">
        <div class="fl" style="margin: 10px 0 10px 0;width:100%;">
          <div class="dqb-column-div">
            <ul>
              <li style="line-height:40px;">年化收益</li>
              <li style="font-size: 24px;line-height: 40px;"><fmt:formatNumber value="${fixTenderRealVo.apr}" pattern="##.##"/>%</li>
            </ul>
            <ul>
              <li style="line-height:40px;">加入金额</li>
              <li style="line-height:40px;"><fmt:formatNumber value="${fixTenderRealVo.account }" pattern="#,##0.00" />元</li>
            </ul>
            
            <ul>
              <li style="line-height:40px;">剩余天数</li>
              <li style="line-height:40px;">${fixTenderRealVo.remainDay<0?0:fixTenderRealVo.remainDay}天</li>
            </ul>
          </div>
          <div class="dqb-line"></div>
          <div class="dqb-column-div">
            <ul>
              <li style="line-height:40px;">已赚利息</li>
              <li><span style="font-size: 24px;line-height: 30px;color:red;">${fixTenderRealVo.hasReturnMoneyStr}</span>元</li>
            </ul>
            <ul>
              <li style="line-height:40px;">预计收益</li>
              <li style="line-height:40px;">${fixTenderRealVo.yqsyStr}元
            </ul>
            <ul>
              <li style="line-height:40px;">到期日期</li>
              <li style="line-height:40px;"> <fmt:formatDate value="${fixTenderRealVo.lockEndTime }" pattern="yyyy-MM-dd"/> 
            </ul>
            
          </div>
         
        <div class="dqb-line"></div> 
         <div class="dqb-status dqb-status-gray">已退出</div> 
         	
        </div>
      </div>
    </div>

			<!--我的账户右侧 -活期生息 e -->

    <div class="lb-waikuang" >
      <div class="men_title col2">
       <ul id="topupInnerDiv">
       
 
      <li class="men_li">
      <a id="tab1tb" href="javascript:void(0);" onclick="load_tb(this)">投标信息</a>
     </li>
      <li> <a id="tab2jr" href="javascript:void(0);" onclick="load_jr(this)">加入明细</a>
       </li>
    </ul>

        
      </div>
      
      			<div class="clear"></div>
				<div id="containerRight"></div>
    </div>
    
    <!--我的账户左侧 结束 --> 
    
    <!--我的账户右侧开始 --> 
    
  </div>
</div>



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
		load_tb(document.getElementById("tab1tb"));
	});

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
				$("#containerRight").html(data);
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
				$("#containerRight").html(data);
			},
			error : function(data) {
				layer.msg("网络连接异常，请刷新页面或稍后重试！", 1, 5);
			}
		});
	}


	
	//收益明细 - 分页
	function pageParent_tb(pageNo) {
		$.ajax({
			url : '${basePath}/dingqibao/tab1JrDetail/'+'${fixTenderRealVo.fixBorrowId}'+'/'+
					+ pageNo + '.html',
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

	//资金信息 - 分页
	function pageParent_jr(pageNo) {
		$.ajax({
			url : '${basePath}/dingqibao/tab2jrDetail/' +'${fixTenderRealVo.fixBorrowId}'+'/'+
			+ pageNo + '.html',
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


</html>
