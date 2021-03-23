<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script type="text/javascript" src="${basePath}/js/main/global-1.1.0.min.js"></script>
<script type="text/javascript">
$(document).ready(function () {
	
$(".head-weixin-icon").hover(function(){
$(".head-ewm-tag").css("display","block");
},function(){
$(".head-ewm-tag").css("display","none");
});

});


</script>
<div id="header">
         <div class="inner">   
                  
                   <a class="head-phone-icon" href="http://bbs.dxjr.com/ext/androidapp.html"> 手机版</a>
          <a class="head-weixin-icon" style="margin-left:20px;">微信</a>    
          
 
         
          <div class="head-ewm-tag" style="">
              <div class="head-ewm-img" style=" clear: left;">
            <img src="${basePath }/images/erweima1.png" alt="顶玺金融"/>
            <span>服务号</span>
          </div>
          <div class="head-ewm-img" style=" margin-left:50px;clear: right;">
            <img src="${basePath }/images/erweima.png" alt="顶玺金融"/>
            <span>公众号</span>
          </div>
          </div>
              
			   <div class="loginbar" style="width:480px;"> 
                     <div class="for" style="width: 520px;">
                     <li><a href="${path}/sycee.html" rel="nofollow"><em class="yellow-c">元宝商城</em></a></li>
                     <li><a href="${path }/lottery_chance/info.html" rel="nofollow"><img src="${basePath}/images/award.png" alt="抽奖"/>&nbsp;<em class="yellow-c">抽大奖</em><label id="chanceTotalNumTip" style="display:none;">(<em class="orange-c">2</em>)</label></a></li>
                     <li><a href="${path}/" rel="nofollow">首页</a></li>
                     <li id="navDown3"><a  class="gls" href="${path}/myaccount/toIndex.html" 
                     rel="nofollow">我的账户 &nbsp;<img src="${basePath }/images/list_arrow_gray.png" alt="活期宝"/></a>
                     <div class="dropdown">
                    	<a  href="${path }/accOpertingRecord/paymentDetails/1.html" target="_parent">资金明细</a>
                        <a  href="${path }/account/InvestManager/collectionindex/c.html" target="_parent">我的待收</a>
                     	<a  href="${path }/repayment/enterRepayMent.html" target="_parent">我的待还</a>
                        <a  href="${path }/repayment/enterRepayMent.html" target="_parent">我要还款</a>
                        <a  href="${path }/account/topup/toTopupIndex.html" target="_parent">我要充值</a>
                        <a  href="${path }/myaccount/cashRecord/toCashIndex.html" target="_parent">我要提现</a>
                      <b class="arrow_up"></b>
                      <b class="arrow_up_in"></b>                  
				     </div>
                     </li>
                     <li id="navDown5"><a href="${path }/zixun.html" class="gls">顶玺资讯</a></li>
                     <li><a href="${path }/bangzhu.html" rel="nofollow">帮助中心</a></li>
					 </div>
               </div>
			  	<div class="logo" align="left">
			 
           <c:if test="${logotype != null && logotype == '1' }">
	           <h1> 
	           <a href="${path}/"><img alt="顶玺金融" src="${basePath}/images/main/logo.png" width="160" height="72"></a>
	           </h1> 
           </c:if>    
             
          <c:if test="${logotype == null || logotype ==''}">
                <a href="${path}/"><img alt="顶玺金融" src="${basePath}/images/main/logo.png" width="160" height="72"></a>
          </c:if>
        </div>               
        <div class="userbar">
            <ul class="nav">
				<li id="navDown"><a href="${path}/toubiao.html" class="gls">我要投资&nbsp;<img alt="定期宝" src="${basePath}/images/main/list_arrow.png" /></a>
                	<div class="dropdown">
                		<a  href="${path}/curInController.html">活期宝</a>
                		<a  href="${path}/dingqibao.html">定期宝</a>
                        <a  href="${path}/toubiao.html">投标专区</a>
                        <a  href="${path}/zhitongche.html">直通车专区</a>
                        <a  href="${path}/zhaiquan.html">债权转让</a>
                        <a  href="${path }/zifei.html">投标资费</a>
                      <b class="arrow_up"></b>
                      <b class="arrow_up_in"></b>                  
				   </div>
                </li>
                <li id="navDown2">
                
                   <a href="${path }/bangzhu/25.html" class="gls">快速融资&nbsp;</a>
                	                 
                </li>
                <!-- 
				<li id="navDown2"><a href="${path }/bangzhu/12.html" class="gls">我要融资&nbsp;<img src="${basePath}/images/main/list_arrow.png" /></a>
                	<div class="dropdown">
                		<a  href="${path }/chengxindai.html" target="_blank">诚薪贷</a>
                		<a  href="${path }/chengshangdai.html" target="_blank">诚商贷</a>
                		<%-- <a  href="${path }/jingzhidai.html" target="_blank">净值贷</a> --%>
                     	<%-- <a  href="${path }/global/investment/borrowTariff.html" target="_blank">融资资费</a> --%>
                      <b class="arrow_up"></b>
                      <b class="arrow_up_in"></b>                  
				   </div>
                </li>
                 -->
                <li id="navDown6">
	                <a href="${path }/newPeopleArea/newPeopleAreaBiao.html" class="gls">新人专区</a>
	                <img style="margin-top:-18px;" src="${basePath }/images/new1.gif" alt="注册奖励">
                </li>
        
        
        
				<li id="navDown3"><a href="${bbsPath}" target="_blank"   class="gls">顶玺社区</a>
                </li>

				<li id="navDown4"><a href="${path}/jianjie.html" class="gls">关于我们&nbsp;<img  alt="定期宝" src="${basePath}/images/main/list_arrow.png" /></a>
                	<div class="dropdown">
                    	<a  href="${path}/jianjie.html" rel="nofollow">公司简介</a>
                    	<a  href="${path}/jianjie/course.html" rel="nofollow">发展历程</a>
                    	<a  href="${path}/jianjie/tuandui.html" rel="nofollow" >管理团队</a>
                    	<a  href="${path}/jianjie/data.html" rel="nofollow">运营数据</a>
                        <a  href="${path}/jianjie/huodong.html" rel="nofollow">团队活动</a>
                        <a  href="${path}/jianjie/supervise.html" rel="nofollow">投资监督</a>
                        <a  href="${path}/baodao.html" >媒体报道</a>
                        <a  href="${path}/gonggao.html" >平台公告</a>
                        <a  href="${path}/jianjie/lianxi.html" rel="nofollow">联系我们</a>
                        <a  href="${path}/jianjie/zhaopin.html" rel="nofollow">招贤纳士</a>
                      <b class="arrow_up"></b>
                      <b class="arrow_up_in"></b>                  
				   </div>
                </li>
            </ul>
            <div class="use" >
            <shiro:authenticated>          	
     				<a href="${path }/myaccount/toIndex.html" style="color:#00a7e5;width: 120px;" >
     				${fn:substring(portal:currentUser().userName,0,6)}
     				<c:if test="${fn:length(portal:currentUser().userName)>6}">..</c:if><c:if test="${redDot!=null}"><img class="redDot" src="${basePath}/images/notice.png" /></c:if><i class="icons app-yl"></i>
     			</a>
	            <a  href="javascript:void(0);" onclick="logout();" style="color:#00a7e5;width: 50px;" >退出<i class="icons app-yl"></i></a>
            </shiro:authenticated>
            <shiro:notAuthenticated>          	
     			<a style="color:#00a7e5" id="toRegisterPageLink" href="${path }/member/toRegisterPage.html" rel="nofollow">注册</a>
            	<a href="${path}/member/toLoginPage.html" rel="nofollow">登录</a>
            </shiro:notAuthenticated>
            </div>
        </div>
    </div>
   
    <div class="topbar"></div>
    
</div>
<div class="clear"></div>

<!--百度统计代码-->
<script>
var _hmt = _hmt || [];
(function() {
  var hm = document.createElement("script");
  hm.src = "//hm.baidu.com/hm.js?4149b06464223c795b9f11534606ae1c";
  var s = document.getElementsByTagName("script")[0]; 
  s.parentNode.insertBefore(hm, s);
})();
</script>
<!--Google analytics代码-->
<script>
  (function(i,s,o,g,r,a,m){i['GoogleAnalyticsObject']=r;i[r]=i[r]||function(){
  (i[r].q=i[r].q||[]).push(arguments)},i[r].l=1*new Date();a=s.createElement(o),
  m=s.getElementsByTagName(o)[0];a.async=1;a.src=g;m.parentNode.insertBefore(a,m)
  })(window,document,'script','//www.google-analytics.com/analytics.js','ga');

  ga('create', 'UA-58400238-1', 'auto');
  ga('send', 'pageview');

</script>

<!--有道智选代码-->
<script type="text/javascript">
var youdao_conv_id = 277382; 
</script> 
<!-- 访客找回代码  -->
<script type="text/javascript">
var _mvq = _mvq || [];
_mvq.push(['$setAccount', 'm-127723-0']);
_mvq.push(['$logConversion']);
(function() {
    var mvl = document.createElement('script');
    mvl.type = 'text/javascript'; mvl.async = true;
    mvl.src = ('https:' == document.location.protocol ? 'https://static-ssl.mediav.com/mvl.js' : 'http://static.mediav.com/mvl.js');
    var s = document.getElementsByTagName('script')[0];
    s.parentNode.insertBefore(mvl, s); 
})();
</script>

<!-- WPA Button Begin --> 
<script charset="utf-8" type="text/javascript" src="http://wpa.b.qq.com/cgi/wpa.php?key=XzgwMDAyMTMwNV83MzQwN184MDAwMjEzMDVf"></script> 
<!-- WPA Button END -->

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
</script>

<!--vip提醒-->
<script type="text/javascript">
$(function(){
<shiro:authenticated>
/* $.ajax({
	url : "${basePath}/getRemindStatus.html",
	data : {},
	type : 'get',
	success : function(data){
		if(data.code=="-1"){
			//alert("未登录，不做处理");
		}
		else{
			if(data.code=="1"){
				//alert("//有缓存，不提醒；");
			}
			else{
				if(data.message =="3" ){
					if(layer.confirm("温馨提示：您的vip临近过期。为享有本息保障，请提前做好VIP续费准备！",function(){
						
						location.href="${path}/account/approve/vip/vipforinsert.html";
					}));
				}
				if(data.message =="1" ){
					if(layer.confirm("温馨提示：您的vip即将过期。为享有本息保障，请提前做好VIP续费准备！",function(){
						
						location.href="${path}/account/approve/vip/vipforinsert.html";
					}));
				}
				if(data.message =="2" ){
					if(layer.confirm("温馨提示：您的vip已过期。为享有本息保障，请及时做好VIP续费准备！",function(){
						
						location.href="${path}/account/approve/vip/vipforinsert.html";
					}));
				}
			}
		}
	}
}); */
// 统计抽奖机会有效总次数
$.ajax({
	url : "${path}/lottery_chance/queryLotteryChanceUseNumTotal.html",
	data : {},
	type : 'post',
	success : function(data){
		if(data > 0){
			$("#chanceTotalNumTip").attr("style","display:;");
			$("#chanceTotalNumTip").html("<a href='${path }/lottery_chance/info.html' rel='nofollow'>(<em class='orange-c'>"+data+"</em>)</a>");
		}else{
			$("#chanceTotalNumTip").attr("style","display:none;");
			$("#chanceTotalNumTip").html("");
		}
	},
	error : function(data) {
		layer.msg('网络连接异常，请刷新页面或稍后重试！', 1, 5);
	}
});
</shiro:authenticated>
});
</script>
<%-- <!-- WPA Button Begin --> 
<%
	String result = request.getServletPath();
	if(!result.contains("common/main.jsp")){
%>
<script charset="utf-8" type="text/javascript" src="http://wpa.b.qq.com/cgi/wpa.php?key=XzgwMDAyMTMwNV8yOTU2MDJfODAwMDIxMzA1Xw"></script> 
<%}%>
<!-- WPA Button End -->  --%>