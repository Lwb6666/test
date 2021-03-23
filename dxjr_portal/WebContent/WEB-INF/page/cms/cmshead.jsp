<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/page/common/taglib.jsp"%>

<%
    String version = "1000042";
%>

<!-- 公共js -->
<script type="text/javascript" src="${basePath }/js/jquery-1.11.0.js?version=<%=version%>"></script>
<script type="text/javascript" src="${basePath }/js/layer/layer.min.js?version=<%=version%>"></script>
<script type="text/javascript" src="${basePath }/js/layer/extend/layer.ext.js?version=<%=version%>"></script>
<script src="${basePath }/js/main/global-1.1.0.min.js" type="text/javascript"></script>
<script src="${basePath }/js/main/global-1.1.0.min.js" type="text/javascript"></script>
<script type="text/javascript"  src="${basePath}/js/superslide.2.1.js?version=<%=version%>"></script>
<script type="text/javascript"  src="${basePath}/js/common.js?version=<%=version%>"></script>

<!--cms中的css -->
<link href="${basePath }/css/cms/style.css?version=<%=version%>" rel="stylesheet" type="text/css">
<link href="${basePath }/css/cms/css.css?version=<%=version%>" rel="stylesheet" type="text/css">
<link href="${basePath }/css/cms/layout.css?version=<%=version%>" rel="stylesheet" type="text/css">
<link href="${basePath }/css/basezixun.css?version=<%=version%>" rel="stylesheet" type="text/css">
 
<script>
// 百度
var _hmt = _hmt || [];
(function() {
  var hm = document.createElement("script");
  hm.src = "//hm.baidu.com/hm.js?4149b06464223c795b9f11534606ae1c";
  var s = document.getElementsByTagName("script")[0]; 
  s.parentNode.insertBefore(hm, s);
})();
</script>
 
<script>
// 谷歌
  (function(i,s,o,g,r,a,m){i['GoogleAnalyticsObject']=r;i[r]=i[r]||function(){
  (i[r].q=i[r].q||[]).push(arguments)},i[r].l=1*new Date();a=s.createElement(o),
  m=s.getElementsByTagName(o)[0];a.async=1;a.src=g;m.parentNode.insertBefore(a,m)
  })(window,document,'script','//www.google-analytics.com/analytics.js','ga');
  ga('create', 'UA-58400238-1', 'auto');
  ga('send', 'pageview');
</script>

<!-- WPA Button Begin --> 
<script charset="utf-8" type="text/javascript" src="http://wpa.b.qq.com/cgi/wpa.php?key=XzgwMDAyMTMwNV83MzQwN184MDAwMjEzMDVf"></script> 
<!-- WPA Button END -->

<div id="header">
         <div class="inner">      
			   <div class="loginbar">          
                     <ul class="for">
	                    <li id="navDown4"><a href="${path}/toubiao.html" class="gls">我要投资&nbsp;<img src="${basePath}/images/list_arrow_gray.png" /></a>
		                	<div class="dropdown">
		                	    <a  href="${path}/dingqibao.html">定期宝</a>
		                        <a  href="${path}/curInController.html">活期宝</a>
		                        <a href="${path }/toubiao.html">散标投资</a>
		                      <%--   <a  href="${path}/zhitongche.html">直通车专区</a> --%>
		                        <a  href="${path}/zhaiquan.html">债权转让</a>
		                      <%--   <a  href="${path }/zifei.html">投标资费</a> --%>
		                      <b class="arrow_up"></b>
		                      <b class="arrow_up_in"></b>                  
						   </div>
		                </li>
	                     <li id="navDown2"><a href="${path }/bangzhu/25.html" class="gls">我要融资</a>
		                </li>
	                     <li id="navDown3"><a  class="gls" href="${path}/myaccount/toIndex.html"   rel="nofollow">我的账户 &nbsp;<%-- <img src="${basePath }/images/list_arrow_gray.png" alt="活期宝"/> --%></a>
		                <%--      <div class="dropdown">
		                    	<a  href="${path }/accOpertingRecord/paymentDetails/1.html" target="_parent">资金明细</a>
		                        <a  href="${path }/account/InvestManager/collectionindex/c.html" target="_parent">我的待收</a>
		                     	<a  href="${path }/repayment/enterRepayMent.html" target="_parent">我的待还</a>
		                        <a  href="${path }/repayment/enterRepayMent.html" target="_parent">我要还款</a>
		                        <a  href="${path }/account/topup/toTopupIndex.html" target="_parent">我要充值</a>
		                        <a  href="${path }/myaccount/cashRecord/toCashIndex.html" target="_parent">我要提现</a>
		                      <b class="arrow_up"></b>
		                      <b class="arrow_up_in"></b>                  
						     </div> --%>
		                     </li>
	                     <li><a href="${path }/bangzhu.html">帮助中心</a></li>
					</ul> 
               </div>
               <div class="infors"  style="width: 200px">
		                   <shiro:authenticated>          	
		     				<a href="${path }/myaccount/toIndex.html" style="color:#00a7e5;width: 120px;" >
		     				${portal:currentUser().userName}
		     				 <i class="icons app-yl"></i>
		     			</a>
			            <a  href="javascript:void(0);" onclick="logout();" style="color:#00a7e5;width: 50px;" >退出<i class="icons app-yl"></i></a>
		            </shiro:authenticated>
		            <shiro:notAuthenticated>          	
		     			<a   href="${path }/member/toRegisterPage.html" rel="nofollow">注册</a>
		            	<a href="${path}/member/toLoginPage.html" rel="nofollow">登录</a>
		            </shiro:notAuthenticated>
               </div>
               
               
               
			    <${isHome!=null?'h1':'div style="top:28px"'} class="logo">
                   <a href="${path}"><img alt="顶玺金融" src="${basePath}/images/cms/logo.png" width="160" height="72"></a>
               </${isHome!=null?'h1':'div'}>
        <div class="userbar">
            <ul class="nav">
				<li><a href="${path}">顶玺首页</a></li>
				<c:forEach items="${showChannels}"  var="channel" >
				  <li><a href="${path}/${channel.urlCode}.html">${channel.name}</a></li>
				</c:forEach>
				
			  <%-- 	
			    <li><a href="${path}/meiti.html">媒体报道</a></li>
				<li><a href="${path}/pingtai.html">平台公告</a></li>
				<li><a href="${path}/geren.html">个人理财</a></li>
				<li><a href="${path}/gonglue.html">投资攻略</a></li>
				<li><a href="${path}/zhufang.html">房屋抵押贷款</a></li>
				<li><a href="${path}/wenti.html">常见问题</a></li>
				<li><a href="${path}/baike.html">知识百科</a></li>
			  --%>
			  
                <li><a href="${bbsPath}" target="_blank">顶玺社区</a><img class="mt-5" src="${basePath}/images/cms/hot.gif"/></li>
            </ul>
            
        </div>
    </div>
   
    <div class="topbar"></div>
    </div>
    
    <script type="text/javascript">
	function logout() {
	layer.confirm("确认退出?" , function (){
		
		$.ajax({
			url : '${basePath }/logout.html',
			type : 'post',
			dataType : 'text',
			success : function(data) {
				window.open("${path }/","_self");
			},
			error : function(data) {
				layer.msg('网络连接异常，请刷新页面或稍后重试！', 1, 5);
			}
		});
	} , "确认退出" , function (){
	});
	
}
	
	$(document).ready(function (){
		$("#side_follow").attr("style","display:none");  
	});
	
	
	
	
	
	
	
</script>