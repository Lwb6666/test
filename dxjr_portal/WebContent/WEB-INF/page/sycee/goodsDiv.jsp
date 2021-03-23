<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/page/common/taglib.jsp"%>
<style>
.tc-l p {
	width:330px;
}
</style>
<div class="" >
	<div class="tanchuang" style="width: 650px;">
		<div class="tc-l" style="height: 350px;width: 350px;">
			<div class="yxq-box" style="">
				<img src="${goods.imgurl }" width="240" height="151"/>
				
				<c:if test="${goods.validDay==0 }"><P  style="color:#fff;"></P></c:if>
				<c:if test="${goods.validDay>0 }"><P class="yxq" style="color:#fff;">有效期${goods.validDay }天</P></c:if>
			</div>
			<div>
				<c:if test="${not empty goods.exchangeExp and goods.exchangeExp != ''}">
					<p>兑换方法</p>
					<p style="padding-bottom: 10px;">${goods.exchangeExp }</p>
				</c:if>
				<c:if test="${not empty goods.useWay and goods.useWay != ''}">
					<p>使用方法</p>
					<p style="padding-bottom: 10px;">${goods.useWay }</p>
				</c:if>
				<c:if test="${not empty goods.useExp and goods.useExp != ''}">
					<p>使用说明</p>
					<p>${goods.useExp }</p>
				</c:if>
			</div>
		</div>
		<div class="tc-r">
			<p>
				价格：
				<strong style="color: #d64060; font-size: 20px;">
				<c:if test="${discountFlag == 1}">
				<fmt:formatNumber value="${goods.sycee }" pattern="###,###" />
				<c:set var="syceePrice" value="${goods.sycee }" />
				</c:if>
				<c:if test="${discountFlag != 1}">
				<fmt:formatNumber value="${goods.oldSycee }" pattern="###,###" />
				<c:set var="syceePrice" value="${goods.oldSycee }" />
				</c:if>
				</strong>元宝
			</p>
			<p style="padding:10px 0 0px 0;">
				数量：<strong style="color: #d64060; font-size: 20px;"><input class="inputnum" value="1" id="num" name="num" onchange="countSycee()" maxlength="3" onkeydown="onlyNum();"/></strong> 个
			</p>
			<p style="padding:10px 0 20px 0;"> 共花费：<span style="color:#d64060" id="sumSyceeSpan"><fmt:formatNumber value="${syceePrice }" pattern="###,###" /></span>元宝</p>
			<p>
			  <c:if test="${surplusSycee>=0}">
			  	<c:if test="${not empty exchangeTimes and exchangeTimes>0}">
			  		<a style="background-color:#B0B0AF">立即兑换</a>
			  	</c:if>
			  	<c:if test="${empty exchangeTimes or exchangeTimes==0}">
			  		<a id="subA" href="javascript:;" onclick="subExchange(${goods.id },'${goods.name }',this)">立即兑换</a>
			  	</c:if>
			  </c:if>
			  <c:if test="${surplusSycee<0}">
			 	 <a style="background-color:#B0B0AF;">立即兑换</a>
			  </c:if>
			</p>
			<p style="color: #999; padding-top: 30px;">
		 		兑换后剩余：
		 		<span id="surplusSyceeSpan"  >
	 			<c:if test="${surplusSycee>=0 }">
	 				<strong style="font-size: 20px;"><fmt:formatNumber value="${surplusSycee }" pattern="###,###" /></strong> 元宝
	 			</c:if>
	  			<c:if test="${surplusSycee<0}">
					元宝不足
	  			</c:if>
	  			</span>
			</p>
			<%-- 
			<a class="guanbi" onclick="$('#goodsDiv').hide();" style="cursor: pointer;">
				<img src="${path}/images/guan.png" />
			</a>
			--%>
		</div>
	</div>
</div>
<script>
function countSycee(){
	var _sum = $('#num').val()*${syceePrice};
	$('#sumSyceeSpan').html(formatMoney(_sum));
	var _userSycee=${syceePrice+surplusSycee};
	var _surplus = _userSycee-_sum;
	if(_surplus>=0){
		$('#surplusSyceeSpan').html('<strong style="font-size: 20px;">'+formatMoney(_surplus)+'</strong> 元宝');
		$('#subA').css('background-color','');
		$('#subA').attr('onclick',"subExchange(${goods.id },'${goods.name }',this)");
	}else{
		$('#surplusSyceeSpan').html('元宝不足');
		$('#subA').css('background-color','#B0B0AF');
		$('#subA').removeAttr('onclick');
	}
}
function onlyNum() {
    if(!(event.keyCode==46)&&!(event.keyCode==8)&&!(event.keyCode==37)&&!(event.keyCode==39))
    if(!((event.keyCode>=48&&event.keyCode<=57)||(event.keyCode>=96&&event.keyCode<=105)))
    event.returnValue=false;
}
</script>