<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/page/common/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>1月温情升级</title>
<link href="${path}/css/activity/style.css" rel="stylesheet" type="text/css">
	<script type="text/javascript" src="${basePath }/js/jquery-1.11.0.js"></script>
	<script src="${basePath }/js/zclip/jquery.zclip.min.js"></script>
	<script>
		$(document)
				.ready(
						function() {

							var x = 0;
							$(".rewardrule-more-btn")
									.click(
											function() {
												$(".rewardrule-morecontent-div")
														.slideToggle("slow");
												if (x == 0) {
													$(".rewardrule-more-btn")
															.css("background",
																	"url(images/reward-more-icon.png) 0px 18px");
													x = 1;
												} else {
													$(".rewardrule-more-btn")
															.css("background",
																	"url(images/reward-more-icon.png) 0px 0px");
													x = 0;
												}
											});

							$(".rewardrule-close-btn").click(function() {
								$(".rewardrule-div").css("display", "none");
								$(".shadow").css("display", "none");
								$(document.body).css("overflow", "scroll");
								$(document.body).css("overflow-x", "scroll");
								$(document.body).css("overflow-y", "scroll");
							});
							$(".reward-btn").click(function() {
								$(".rewardrule-div").css("display", "block");
								$(".shadow").css("display", "block");
								$(document.body).css("overflow", "hidden");
								$(document.body).css("overflow-x", "hidden");
								$(document.body).css("overflow-y", "hidden");
								center($('.rewardrule-div'));
							});
							$(".copy-btn").click(function() {
								$(".tips-div").css("display", "block");
								$(".tips-div").fadeOut(5000, "");
							});
						});
		// 居中 
		function center(obj) {
			var screenWidth = $(window).width(), screenHeight = $(window)
					.height(); //当前浏览器窗口的 宽高   
			var scrolltop = $(document).scrollTop();//获取当前窗口距离页面顶部高度   
			var objLeft = (screenWidth - obj.width()) / 2;
			var objTop = (screenHeight - obj.height()) / 2 + scrolltop;
			obj.css("left", objLeft + "px");
			obj.css("top", objTop + "px");
			$(".shadow").css("top", "0");
		}
	</script>
</head>

<body> 
	<div class="jan-banner"></div>
	<div class="dc-main">
		<div class="dc-img">
    	<img src="${path}/images/activity/janimg.jpg"/>
        <div class="dc-reg">
            <button  type="button" class="reg-btn" onclick="location.href='${basePath}/lottery_chance/toNewAward.html'"></button>
        </div>
		</div>
		<div class="dc-info">
			<div class="dec-body">
				<div class="copy-div">
			        <c:if test="${not empty  param.code}">
			         <input class="link-input"   id="txt-link-input"  data-s-chanid="1000" value="${basePath.concat('/member/toRegisterPage.html?code=')}${param.code}" type="text">
			        </c:if>
			        <c:if test="${empty  param.code}">
				        <c:if test="${not empty  recommendPath}">
				         <input class="link-input"   id="txt-link-input"  data-s-chanid="1000" value="${basePath}${recommendPath}" type="text">
				        </c:if>
				        <c:if test="${empty  recommendPath}">
			              <input class="link-input"   id="txt-link-input"   data-s-chanid="1000" value="" type="text">
			           </c:if>
			        </c:if>   
			        
			        <a class="copy-btn btn">
			        	<img  id="copyBtn"    value="
			        	<c:if test="${not empty  param.code}"> 
			        	    ${basePath.concat('/member/toRegisterPage.html?code=')}${param.code} 
			        	</c:if>
			        	<c:if test="${ empty param.code  and not empty  recommendPath}">
			        	      ${basePath}${recommendPath}
			        	</c:if>"     src="${basePath}/images/recommendaward/copy-btn.png" width="78" height="28" />
			        </a>
						<div class="tips-div"></div>
				</div>

				<div class="qrbox-div">
					
			        <c:if test="${not empty  param.code}">
			            <img src="${basePath}/lottery_chance/toEncoderQRCoder.html?url=${basePath.concat('/member/toRegisterPage.html?code=')}${param.code}" width="150" height="150" />
			        </c:if>
			        
			         <c:if test="${empty  param.code}">
				         <c:if test="${empty  recommendPath}">
				                <!--  默认登陆二维码 -->
				               <img src="${basePath}/images/recommendaward/qr.png" width="150" height="150" />
				         </c:if>
				         
				         <c:if test="${not empty  recommendPath}">
				               <img src="${basePath}/lottery_chance/toEncoderQRCoder.html?url=${portal:encode(basePath.concat(recommendPath))}" width="150" height="150" />
				         </c:if>
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
