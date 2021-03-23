<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/page/common/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ include file="/WEB-INF/page/common/public4.jsp"%>
<title>我的账户-账户奖励-奖励记录</title>
</head>
<body>
	<!--头部开始-->
	<%@ include file="/WEB-INF/page/common/header.jsp"%>
	<!--头部结束-->
	<div id="myaccount">
				<%@ include file="/WEB-INF/page/common/topmenu.jsp"%>
		<div class="wraper mt20"><!--table-->
			 <div class="tz-detail">
	            <img src="${basePath}/images/myaccount/jl-banner.jpg"/>
	        </div>
	    	<div class="prduct-menu">
	        	<div class="menu-tbl">
	           		<ul class="col4" id="topupInnerDiv">
					    <li id="hongbao_tab" onclick="load_hongbao_lott(this)">奖励记录</li>
						<li id="link_xj_lott" onclick="load_xj_lott(this)">我的元宝</li>
						<li id="sw_tab" onclick="load_sw_lott(this)">我的红包 </li>
						<li id="award_tab" onclick="load_jljl_lott(this)">推荐奖励</li>
					</ul>
	            </div>
	         </div>
	         	<div class="clear"></div>
				<div id="containerRight"></div>
         </div>
   </div>
   <!-- 弹层开始 -->
       <div class="reveal-modal"  id="lingqushiwu"></div>
	   <div class="reveal-modal"  id="lingqush"></div>
	   <div class="reveal-modal"  id="div-copyok">
	       	<div class="cont-word">
	            <p>&nbsp;</p>
	        	<p class="mt30 f14"><span id=copetext></span></p>
	        	<p class="mt10 f14"><span id="copeUrl"></span></p>
	            <div class="form-col2 mt30">
	             <button type="button" class="enter" onClick="closeCope();">确认</button>
	            </div>
		   </div> 
	    </div>
   <!-- 弹层结束-->
	<%@ include file="/WEB-INF/page/common/footer.jsp"%>	
</body>
<script type="text/javascript">
 	$(function() {
		if('${tabType}' == '1'){
			load_xj_lott(document.getElementById("link_xj_lott"));
		}else if('${tabType}' == '2'){
			load_sw_lott(document.getElementById("sw_tab"));
		}else if('${tabType}' == '3'){
			load_jljl_lott(document.getElementById("award_tab"),'${redId}');
		}else{
			load_hongbao_lott(document.getElementById("hongbao_tab"));
		}
		$("#zhjl").attr("class","active"); //添加样式 	
	});

	/**
	 * 加载 实物奖励
	 */
	function load_sw_lott(obj) {
		$.ajax({
			url : '${basePath}/lottery_use_record/sw_lottnew/1.html',
			data : {
				status : $('#status').val()
			},
			type : 'post',
			dataType : 'text',
			success : function(data) {
				$("#containerRight").html(data);
				$("#sw_tab").attr("class","active"); //添加样式 	
			},
			error : function(data) {
				layer.msg("网络连接异常，请刷新页面或稍后重试！", 1, 5);
			}
		});
	}

	/**
	 * 加载 jljl
	 */
	function load_jljl_lott(obj,redId) {
		var isRecommendSuccess=1;
		if(undefined==redId){
			redId=0
		}
		$.ajax({
			url : '${basePath}/lottery_use_record/jljl_lottnew/1/'+ isRecommendSuccess+'/'+redId+'.html',
			data : {
				status : $('#status').val()
			},
			type : 'post',
			dataType : 'text',
			success : function(data) {
				$("#containerRight").html(data);
				$("#InviterSuccess a").attr("class","active"); 
				window._bd_share_main.init();
				$("#award_tab").attr("class","active"); //添加样式 	
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
		$.ajax({
			url : '${basePath}/lottery_use_record/lott_hongbaonew/1/0.html',
			type : 'post',
			dataType : 'text',
			success : function(data) {
				$("#containerRight").html(data);
				$("#hongbao_tab").attr("class","active"); //添加样式 	
				$("#all a").attr("class","active"); 
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
			url : '${basePath}/lottery_use_record/xj_lottnew/' + pageNo + '.html',
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
			url : '${basePath}/lottery_use_record/sw_lottnew/' + pageNo + '.html',
			type : 'post',
			dataType : 'text',
			success : function(data) {
				$("#containerRight").html(data);
				$('.nobao').slideDown("fast");
				$('.page').slideDown("fast");
				 $('.hb a').removeClass("arrowdown");
			},
			error : function(data) {
				layer.msg("网络连接异常，请刷新页面或稍后重试！", 1, 5);
			}
		});
	}
  
	/**
	 *  jljl 页面
	 */
	function lott_jljl_pageParent(pageNo,isRecommendSuccess,redId) {
		$.ajax({
			url : '${basePath}/lottery_use_record/jljl_lottnew/' + pageNo+'/' +isRecommendSuccess
					+ '/'+redId+'.html',
			type : 'post',
			dataType : 'text',
			success : function(data) {
				$("#containerRight").html(data);
				var isRecommendSuccess=$("#isRecommendSuccess").val();
				 if(isRecommendSuccess==1){
		    		 $("#InviterSuccess a").attr("class","active"); 
		    		 $("#InviterFail a").attr("class",""); 
		    	 }
		    	 if(isRecommendSuccess==2){
		    		 $("#InviterFail a").attr("class","active"); 
		    		 $("#InviterSuccess a").attr("class",""); 
		    	 }
		    	 window._bd_share_main.init();
			},
			error : function(data) {
				layer.msg("网络连接异常，请刷新页面或稍后重试！", 1, 5);
			}
		});
	}
    function lott_hongbao_pageParent(pageNo,awardType){
    	$.ajax({
			url : '${basePath}/lottery_use_record/lott_hongbaonew/' + pageNo+'/'+awardType
					+ '.html',
			type : 'post',
			dataType : 'text',
			success : function(data) {
				 $("#containerRight").html(data);
				 if(awardType==0){
		    		 $("#all a").attr("class","active"); 
		    		 $("#yuanbao a").attr("class",""); 
		    		 $("#hongbao a").attr("class",""); 
		    		 $("#cash a").attr("class",""); 
		    		 $("#shiwu a").attr("class",""); 
		    		 $("#choujiang a").attr("class",""); 
		    	 }
		    	 if(awardType==1){
		    		 $("#all a").attr("class",""); 
		    		 $("#yuanbao a").attr("class",""); 
		    		 $("#hongbao a").attr("class",""); 
		    		 $("#cash a").attr("class","active"); 
		    		 $("#shiwu a").attr("class",""); 
		    		 $("#choujiang a").attr("class",""); 
		    	 }
		    	 if(awardType==2){
		    		 $("#all a").attr("class",""); 
		    		 $("#yuanbao a").attr("class",""); 
		    		 $("#hongbao a").attr("class",""); 
		    		 $("#cash a").attr("class",""); 
		    		 $("#shiwu a").attr("class","active"); 
		    		 $("#choujiang a").attr("class",""); 
		    	 }
		    	 if(awardType==4){
		    		 $("#all a").attr("class",""); 
		    		 $("#yuanbao a").attr("class",""); 
		    		 $("#hongbao a").attr("class",""); 
		    		 $("#cash a").attr("class",""); 
		    		 $("#shiwu a").attr("class",""); 
		    		 $("#choujiang a").attr("class","active"); 
		    	 }
		    	 if(awardType==5){
		    		 $("#all a").attr("class",""); 
		    		 $("#yuanbao a").attr("class",""); 
		    		 $("#hongbao a").attr("class","active"); 
		    		 $("#cash a").attr("class",""); 
		    		 $("#shiwu a").attr("class",""); 
		    		 $("#choujiang a").attr("class",""); 
		    	 }
		    	 if(awardType==6){
		    		 $("#all a").attr("class",""); 
		    		 $("#yuanbao a").attr("class","active"); 
		    		 $("#hongbao a").attr("class",""); 
		    		 $("#cash a").attr("class",""); 
		    		 $("#shiwu a").attr("class",""); 
		    		 $("#choujiang a").attr("class",""); 
		    	 }
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
		$.ajax({
			url : '${basePath}/lottery_use_record/xj_lottnew/1.html',
			type : 'post',
			success : function(data) {
				$("#containerRight").html(data);
				$("#link_xj_lott").attr("class","active"); //添加样式 	
			},
			error : function(data) {
				layer.msg("网络连接异常，请刷新页面或稍后重试！", 1, 5);
			}
		});
	}

	/**
	 * 加载 jljl
	 */
	function queryInviter(isRecommendSuccess,redId) {
		$.ajax({
			url : '${basePath}/lottery_use_record/jljl_lottnew/1/'+ isRecommendSuccess+'/'+redId+'.html',
			data : {
				status : $('#status').val()
			},
			type : 'post',
			dataType : 'text',
			success : function(data) {
				$("#containerRight").html(data);
				 if(isRecommendSuccess==1){
		    		 $("#InviterSuccess a").attr("class","active"); 
		    		 $("#InviterFail a").attr("class",""); 
		    	 }
		    	 if(isRecommendSuccess==2){
		    		 $("#InviterFail a").attr("class","active"); 
		    		 $("#InviterSuccess a").attr("class",""); 
		    	 }
		    	window._bd_share_main.init();
			},
			error : function(data) {
				layer.msg("网络连接异常，请刷新页面或稍后重试！", 1, 5);
			}
		});
	}
	/**
	 *  红包 加载的红包页面
	 */
	function queryRewardRecord(awardType) {
		$.ajax({
			url : '${basePath}/lottery_use_record/lott_hongbaonew/1'+'/'+awardType+'.html',
			type : 'post',
			dataType : 'text',
			success : function(data) {
				$("#containerRight").html(data);
				 if(awardType==0){
		    		 $("#all a").attr("class","active"); 
		    		 $("#yuanbao a").attr("class",""); 
		    		 $("#hongbao a").attr("class",""); 
		    		 $("#cash a").attr("class",""); 
		    		 $("#shiwu a").attr("class",""); 
		    		 $("#choujiang a").attr("class",""); 
		    	 }
		    	 if(awardType==1){
		    		 $("#all a").attr("class",""); 
		    		 $("#yuanbao a").attr("class",""); 
		    		 $("#hongbao a").attr("class",""); 
		    		 $("#cash a").attr("class","active"); 
		    		 $("#shiwu a").attr("class",""); 
		    		 $("#choujiang a").attr("class",""); 
		    	 }
		    	 if(awardType==2){
		    		 $("#all a").attr("class",""); 
		    		 $("#yuanbao a").attr("class",""); 
		    		 $("#hongbao a").attr("class",""); 
		    		 $("#cash a").attr("class",""); 
		    		 $("#shiwu a").attr("class","active"); 
		    		 $("#choujiang a").attr("class",""); 
		    	 }
		    	 if(awardType==4){
		    		 $("#all a").attr("class",""); 
		    		 $("#yuanbao a").attr("class",""); 
		    		 $("#hongbao a").attr("class",""); 
		    		 $("#cash a").attr("class",""); 
		    		 $("#shiwu a").attr("class",""); 
		    		 $("#choujiang a").attr("class","active"); 
		    	 }
		    	 if(awardType==5){
		    		 $("#all a").attr("class",""); 
		    		 $("#yuanbao a").attr("class",""); 
		    		 $("#hongbao a").attr("class","active"); 
		    		 $("#cash a").attr("class",""); 
		    		 $("#shiwu a").attr("class",""); 
		    		 $("#choujiang a").attr("class",""); 
		    	 }
		    	 if(awardType==6){
		    		 $("#all a").attr("class",""); 
		    		 $("#yuanbao a").attr("class","active"); 
		    		 $("#hongbao a").attr("class",""); 
		    		 $("#cash a").attr("class",""); 
		    		 $("#shiwu a").attr("class",""); 
		    		 $("#choujiang a").attr("class",""); 
		    	 }
			},
			error : function(data) {
				layer.msg("网络连接异常，请刷新页面或稍后重试！", 1, 5);
			}
		});
	}
	function closeWin(){ 
		$('#lingqushiwu').trigger('reveal:close'); 
		} 
	function closeWinsh(){ 
		$('#lingqush').trigger('reveal:close'); 
		} 
	function closeCope(){ 
		$('#div-copyok').trigger('reveal:close'); 
		}
</script>
</html>
