<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/page/common/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
  	<title>新春巨献活动-顶玺金融官网</title>
	<meta name="keywords" content="关于顶玺金融" />
	<meta name="description" content="顶玺金融是专业的互联网金融公司，成立于2013年，总部位于国际金融中心上海。以其自主研发的技术平台和规范、透明的运作方式，成为成长最快、口碑最佳的平台之一。关于顶玺金融详情请登陆www.dxjr.com。">
	<link href="${basePath}/css/activity/invitedStyle.css" rel="stylesheet" type="text/css" />
	<script type="text/javascript" src="${basePath }/js/jquery-1.11.0.js"></script>
	<script src="${basePath }/js/zclip/jquery.zclip.min.js"></script>

<script> 
$(document).ready(function(){
 
var x = 0;
$(".rewardrule-more-btn").click(function(){
$(".rewardrule-morecontent-div").slideToggle("slow");
if(x==0){
$(".rewardrule-more-btn").css("background","url(${path}/images/invited/reward-more-icon.png) 0px 18px");
x=1;
}
else{
$(".rewardrule-more-btn").css("background","url(${path}/images/invited/reward-more-icon.png) 0px 0px");
x=0;
}
});

$(".rewardrule-close-btn").click(function(){
$(".rewardrule-div").css("display","none");
$(".shadow").css("display","none");
$(document.body).css("overflow","scroll");
$(document.body).css("overflow-x","scroll");
$(document.body).css("overflow-y","scroll");
});
$(".reward-btn").click(function(){
$(".rewardrule-div").css("display","block");
$(".shadow").css("display","block");
$(document.body).css("overflow","hidden");
$(document.body).css("overflow-x","hidden");
$(document.body).css("overflow-y","hidden");
center($('.rewardrule-div'));   
});
$(".copy-btn").click(function(){
$(".tips-div").css("display","block");
$(".tips-div").fadeOut(5000,"");
});
});
// 居中 
function center(obj) {   
var screenWidth = $(window).width(), screenHeight = $(window).height(); //当前浏览器窗口的 宽高   
var scrolltop = $(document).scrollTop();//获取当前窗口距离页面顶部高度   
var objLeft = (screenWidth - obj.width())/2 ;   
var objTop = (screenHeight - obj.height())/2 + scrolltop;   
obj.css("left", objLeft + "px"); 
obj.css( "top" , objTop + "px"); 
$(".shadow").css("top","0");
} 
function getDivShow(){
	var dis=document.getElementById("divInvited").style.display;
	if(dis=="none"){
		document.getElementById("divInvited").style.display="block";
	}else{
		document.getElementById("divInvited").style.display="none";
	}	
}
function toRegPage(){
	window.open("${path}/lottery_chance/toNewAward.html","_blank");
	//location.href="${path}/lottery_chance/toNewAward.html";	
}
</script>
	
</head>

<body>
<style type="text/css">
	  @media only screen and (min-width: 321px) and (max-width: 1024px){ 
          .jan-banner,.dc-main{ width:1300px; } 
      } 
</style>
<div class="jan-banner"  >
 <div class="haoyoubox">
<div class="kbox"></div>
<div class="hysm">
	<p>您本月已成功邀请<span>${invitedNum }</span>位好友，奖励<span>${invitedMoney }</span>元</p>
    <ul>
    	<li style=" padding-bottom:10px;"><span style="text-align:left;" >好友手机号</span><span> 注册时间 </span><span style="width:160px;  ">累计金额≥10000 </span><span style="padding-right:0px; width:100px;"> 状态</span> </li>    	
   			<c:forEach items="${invitedList}" var="invited" varStatus="sta">
				<c:if test="${sta.index<3}">
					<li><a href="${path}/accountdetail/show.html?userName=${portal:encode(portal:encode(invited.userNameEncrypt))}" target="_blank" ><span style="text-align:left;">${invited.mobile } </span></a><span><fmt:formatDate value="${invited.regTime }" pattern="yyyy.MM.dd"/> </span><span style="width:160px;">是</span><span style="padding-right:0px; width:100px;">符合</span> </li>
				</c:if>				
			</c:forEach>
			<div id="divInvited" style="display:none;">
			<c:forEach items="${invitedList}" var="invited" varStatus="sta">				
				<c:if test="${sta.index>=3}">					
					<li><a href="${path}/accountdetail/show.html?userName=${portal:encode(portal:encode(invited.userNameEncrypt))}" target="_blank" ><span style="text-align:left;">${invited.mobile } </span></a><span><fmt:formatDate value="${invited.regTime }" pattern="yyyy.MM.dd"/> </span><span style="width:160px;">是</span><span style="padding-right:0px; width:100px;">符合</span> </li>					
				</c:if>				
			</c:forEach>
			</div>
        <li><a href="javascript:getDivShow();" class="djxl-btn">点击下拉</a></li>
    </ul>
</div>
</div>
</div>
<div class="dc-main">
	<div class="dc-img" >
    	<img src="${path}/images/invited/janimg.jpg"/>
        <div class="dc-reg">
        	<button  type="button" class="reg-btn" onClick="javascript:toRegPage();"></button>
        </div>
    </div>
    <div class="dc-info">
    <div  class="dec-body">
     	<div class="copy-div">
        <input type="text" value="${basePath.concat(recommendPath)}" data-s-chanid="1000" id="txt-link-input" class="link-input">
        <a class="copy-btn btn">
        	<img id="copyBtn" value="${basePath.concat(recommendPath)}"  src="${path}/images/invited/copy-btn.png" width="78" height="28" />
        </a>
        <div class="tips-div"> </div>
        </div>
        
        <div class="qrbox-div">
	         <c:if test="${not empty  recommendPath}">
	               <img src="${basePath}/lottery_chance/toEncoderQRCoder.html?url=${portal:encode(basePath.concat(recommendPath))}" width="150" height="150" />
	         </c:if>
        </div>        
    </div>  
    </div>
</div>
<script type="text/javascript">
$('#copyBtn').zclip({
    path: "${basePath }/js/zclip/ZeroClipboard.swf",
    copy: function(){
        return $.trim($('#copyBtn').attr("value"));
	} ,afterCopy:function (){
		 $(".tips-div").css("display","block");
	     $(".tips-div").fadeOut(5000,"");
	 }
});
</script>
</body>
</html>