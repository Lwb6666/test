<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ include file="/WEB-INF/page/common/taglib.jsp"%>

<script type="text/javascript">
	/**
	*  选 中li下面的radio
	*/
	function chooseNextRadio(obj){
		$("#fupaySelectedUl").find("[type='radio']")[0].checked=true;
		//设置选中此li下的radio为选中
		$(obj).find("[type='radio']")[0].checked=true;
		var imgSrc = $(obj).find("img")[0].src;
		$("#showSelectedSinapayImg").attr("src",imgSrc);
		$("#fupaySelectedUl").show();
		
		/*
		var selectedValue = $(obj).find("[type='radio']")[0].value;
		var creditBankArray = new Array("");
		var haveExists = $.inArray(selectedValue, creditBankArray);
		if(haveExists!=-1){
			$("#fupayCreditSpan").show();
		}else{
			$("#fupayCreditSpan").hide();
		}
		*/
	}
	//为li绑定事件
	$(".fupayUl").find("li").each(function(index,element){
		$(element).attr("onclick","chooseNextRadio(this)");
	});
</script>
       <p>充值渠道：</p>
       <div class="val_bk">
        <ul class="fupayUl">
        	<c:forEach items="${banks }" var="b">
			<li>
				<input name="paybank" type="radio" value="${b.name }" />&nbsp;&nbsp;
				<img src="${basePath }/images/banklogo/${b.desc }.gif" alt="${b.value }" align="middle" />
			</li>
			</c:forEach>
        </ul>
        <div class="val-bkinfo" id="fupaySelectedUl" style="display: none">
                                       选择的银行:<img id="showSelectedSinapayImg">&nbsp;
             <input type="radio" name="payChannel" value="debit" checked="checked" style="margin-left:20px;"/>借记卡
<!-- 			<span id="fupayCreditSpan"><input type="radio" name="payChannel" value="credit"/>信用卡</span> -->
        </div>
   </div>
