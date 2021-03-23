<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/page/common/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
  	<title>贺新春迎元宵活动-顶玺金融官网</title>
	<meta name="keywords" content="关于顶玺金融" />
	<meta name="description" content="顶玺金融是专业的互联网金融公司，成立于2013年，总部位于国际金融中心上海。以其自主研发的技术平台和规范、透明的运作方式，成为成长最快、口碑最佳的平台之一。关于顶玺金融详情请登陆www.dxjr.com。">
	<link href="${basePath}/css/activity/2016newYearStyle.css" rel="stylesheet" type="text/css" />
	<script type="text/javascript" src="${basePath }/js/jquery-1.11.0.js"></script>
<style type="text/css">
@media only screen and (min-width: 321px) and (max-width: 1024px){ 
.jan-banner,.dc-info{ width:1300px; } 
} 
</style>
<script> 
$(document).ready(function(){
 
var x = 0;
$(".rewardrule-more-btn").click(function(){
$(".rewardrule-morecontent-div").slideToggle("slow");
if(x==0){
$(".rewardrule-more-btn").css("background","url(${path}/images/2016newYear/reward-more-icon.png) 0px 18px");
x=1;
}
else{
$(".rewardrule-more-btn").css("background","url(${path}/images/2016newYear/reward-more-icon.png) 0px 0px");
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
</script>	
</head>

<body>
<div class="jan-banner"  >
 <div class="haoyoubox">
<div class="kbox"  >
 

</div>
<div class="xc-btn">	
    <a href="http://www.dxjr.com/lottery_chance/info.html"><img src="${path}/images/2016newYear/xc-btn.png"/></a>
</div>

</div>

 

</div>
<div class="dc-main">
    <div class="dc-info">    
    	<div class="dqb-ranking">
        	<ul>
            	<li style="line-height:50px;"><span>昵称</span><span style="text-align:right;">投资金额</span></li>
            	<c:forEach items="${newYearList}" var="newYear" varStatus="sta">
					<c:if test="${sta.index<5}">
						<c:if test="${sta.index<3}">
							<li style="color:#ea5513;"><span>${sta.index+1 }. <a href="${path}/accountdetail/show.html?userName=${portal:encode(portal:encode(newYear.userNameEncrypt))}&flagTemp=1" target="_blank" style="color:red;">${newYear.username }</a> </span><span style="text-align:right;"><fmt:formatNumber value="${newYear.invertTotal }" pattern="#,##0" /> </span></li>
						</c:if>
						<c:if test="${sta.index>=3}">
							<li><span>${sta.index+1 }. <a href="${path}/accountdetail/show.html?userName=${portal:encode(portal:encode(newYear.userNameEncrypt))}&flagTemp=1" target="_blank" style="color:black;">${newYear.username }</a> </span><span style="text-align:right;"><fmt:formatNumber value="${newYear.invertTotal }" pattern="#,##0" /> </span></li>
						</c:if>
					</c:if>
				</c:forEach>
            </ul>        
        	<ul>
            	<li style="line-height:50px;"><span>昵称</span><span style="text-align:right;">投资金额</span></li>
            	<c:forEach items="${newYearList}" var="newYear" varStatus="sta">
					<c:if test="${sta.index>=5}">
						<li><span>${sta.index+1 }. <a href="${path}/accountdetail/show.html?userName=${portal:encode(portal:encode(newYear.userNameEncrypt))}&flagTemp=1" target="_blank" style="color:black;">${newYear.username }</a> </span><span style="text-align:right;"><fmt:formatNumber value="${newYear.invertTotal }" pattern="#,##0" /> </span></li>
					</c:if>
				</c:forEach>            	
            </ul>
        </div>
     </div>
</div>
</body>
</html>