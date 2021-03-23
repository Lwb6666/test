<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/page/common/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ include file="/WEB-INF/page/common/public.jsp"%>
<title>自动投资</title>
</head>

<body style="background:#f9f9f9;">
<!--头部开始-->
<%@ include file="/WEB-INF/page/common/header.jsp" %>
<!--头部结束-->

<!--自动投标左侧开始-->
<div id="Container">
<div id="Bmain">
    <div class="title">
    	<span class="home"><a href="${path}/">顶玺金融</a></span><span><a href="${path}/myaccount/toIndex.html">我的账户</a></span><span><a href="#">投资管理</a></span><span><a href="${path }/myaccount/autoInvest/autoInvestMain.html">自动投资</a></span>
  	</div>
	<!--自动投标左侧 开始 -->    
	<%@ include file="/WEB-INF/page/account/myAccount_leftMenu.jsp"%> 
	<!--自动投标左侧 结束 -->    
	<!--自动投标右侧开始 --> 
	<div id="menu_right">
         <div class="title-items" style="display:none;">
               <h2>自动投资&nbsp;</h2>
               <b class="line"></b>
         </div >
         <div id="menu_column">
             <div class="men_title col2">
                  <ul>
	                  <li id="auto_invest_list"><a href="javascript:autoInvestList();">自动投标</a> </li>
	                  <li id="auto_invest_record"><a href="javascript:autoInvestFix();">自动投定期宝 </a></li>
                  </ul>
             </div>
          </div>
          <div id="main_content"></div>      
     </div>            
	<!--自动投标右侧结束 -->
</div>
</div>
<%@ include file="/WEB-INF/page/common/footer.jsp" %>
</body>
<script type="text/javascript">
$(function(){
	autoInvestList();
});
//自动投标页面
function autoInvestList(){
	$("#auto_invest_list").addClass("men_li");
	$("#add_auto_invest").removeClass("men_li");
	$("#auto_invest_record").removeClass("men_li");
	$("#autoTip").html("添加自动投标");
	$.ajax({
		url : '${basePath}/myaccount/autoInvest/autoInvestList.html',
		data :{
		} ,
		type : 'post',
		dataType : 'text',
		success : function(data){
			$("#main_content").html(data);
		},
		error : function(data) {
			layer.msg("网络连接异常，请刷新页面或稍后重试！", 2, 5);
	    }
	});
	
}
//自动投定期宝页面
function autoInvestFix(){
	$("#auto_invest_record").addClass("men_li");
	$("#auto_invest_list").removeClass("men_li");
	$("#add_auto_invest").removeClass("men_li");
	$.ajax({
		url : '${basePath}/myaccount/autoInvestFix/autoInvestFixMain.html',
		type : 'post',
		dataType : 'html',
		success : function(data){
			$("#main_content").html(data);
		},
		error : function(data) {
			layer.msg("网络连接异常，请刷新页面或稍后重试！", 2, 5);
	    }
	});
}
//设置自动投标功能
function settingAuto(){
	
	var _url='${basePath}/myaccount/autoInvest/autoInvestSeting.html';
	 
	$.ajax({
		url : _url,
		data : {},
		type : 'post',
		dataType : 'html',
		success : function(data) {
			$.layer({
			    type : 1,
			    title : '设置自动投标',
			    fix : false,
			    offset:['50px' , ''],
			    area : ['680','510'],//横坐标纵坐标，
			    page : {html : data}
			});
		},
		error : function(data) {
			layer.msg('网络连接异常，请刷新页面或稍后重试！', 1, 5);
		}
	});
	 
}

</script>
</html>
