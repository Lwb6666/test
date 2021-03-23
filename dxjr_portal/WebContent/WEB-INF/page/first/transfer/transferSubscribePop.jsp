<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/page/common/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ include file="/WEB-INF/page/common/public.jsp"%>
<title>网站账户</title>
</head>
<body style="overflow-y: hidden; background: #fff;">
	<div class="sf" style="margin-left:10px;">
	
		<div class="safebox450 sfbox" style="padding:23px 0 0 33px;">
		<form action="" method="post" id="subscribeTransferForm">
			<input type="hidden" name="accountReal" id="accountReal" value="${firstTransfer.accountReal }"/>
			<input type="hidden" id="useMoney" value="${account.useMoney }"/>
			<input type="hidden" name="transferId" id="transferId" value="${firstTransfer.id}"/>
			<input type="hidden" name="platform" id="platform" value="1"/>
			<dl>
				<dd>
					<span>转让价格：</span>￥
					<fmt:formatNumber value="${firstTransfer.accountReal}"
						pattern="#,##0.00" />
					&nbsp;
				</dd>
				<dd>
					<span>账户余额：</span>￥
					<fmt:formatNumber value="${account.useMoney}" pattern="#,##0.00" />
					&nbsp;<font color="#00a7e5"><a
						href="${path}/account/topup/toTopupIndex.html" target="_parent"><font
							color="#00a7e5">我要充值</font></a></font>
				</dd>
				<c:if test="${isExistCurAccount == true }">
				<dd><span>活期宝可用余额：¥</span><fmt:formatNumber value="${maxCurMoney}"  pattern="#,##0.00" />&nbsp;&nbsp;
				<input type="checkbox" name = "isUseCurMoney" id="isUseCurMoney" value="0" onclick="useCurMoney();" checked="checked"/><a href="javascript:useCurMoney();">使用活期宝</a>
				</dd>
				</c:if>
				<dd>
					<span>交易密码：</span>
					<input type="password" name="payPassword" id="payPassword" autocomplete="off" style="width: 100px" />&nbsp;
				</dd>
				<c:if test="${null!=firstTransfer.bidPassword && firstTransfer.bidPassword!= ''}">
				
					<dd>
						<span>认购密码：</span>
						<input type="password" name="bidPassword" id="bidPassword" autocomplete="off" style="width: 100px" />&nbsp;
					</dd>
				</c:if>
				<dd>
					<span>验证码：</span> <input type="text" name="checkCode"
						id="checkCode" size="4" maxlength="4" style="width: 80px;" />&nbsp;
				</dd>
				<dd>
					<span>&nbsp;</span> <a href="javascript:loadimage();"> <img
						name="randImage" id="randImage" src="${basePath}/random.jsp"
						style="float: left;" border="0" align="middle" />
					</a>
				</dd>
				<dd>
					<span>&nbsp;</span> <input type="button" value="确认认购"
						onclick="tender_borrow();" id="btnTenderBorrow"
						style="cursor: pointer; background: #00a7e5; color: #fff; padding: 0 20px; height: 30px; border: 0; font-size: 14px; display: block;" />
				</dd>
			</dl>
		</form>
		</div>
	</div>
	<br/>
	<c:if test='${isExistCurAccount == true }'>
		<div class="tishiyu" style="">温馨提示：<br/>勾选“使用活期宝”后，当您可用余额不足时，系统会使用您活期宝可用余额投资。</div>
	</c:if>
</body>		

<script type="text/javascript">

	/**
	 * 购买
	 */
	function tender_borrow() {
		if (!validateTenderData()) {
			return;
		}
		
		var pop = $.layer({
		    shade: [0],
		    area: ['200px','auto'],
		    offset : ['50px',''],
		    dialog: {
		        msg: '确定要购买吗？',
		        btns: 2,
		        type: 4,
		        btn: ['确定','取消'],
		        yes: function(){
		        	$("#btnTenderBorrow").removeAttr("onclick");
					$("#subscribeTransferForm").ajaxSubmit({
						url : '${basePath }/zhitongche/subscribe.html',
						type : "POST",
						success : function(msg) {
							layer.close(pop);
							if (msg.code == "1") {
								parent.layer.msg("购买成功！",1, 1);
								window.parent.location.href = "${basePath}/zhitongche/zhuanrang/queryTransferById/${firstTransfer.id}.html";
							} else {
								loadimage();
								if (msg.message != '') {
									parent.layer.msg(msg.message,2,5);
								}
								$("#btnTenderBorrow").attr("onclick","tender_borrow()");
							}
						},
						error : function() {
							$("#btnTenderBorrow").attr("onclick","tender_borrow()");
							parent.layer.msg("网络连接超时，请您稍后重试",2, 5);
							layer.close(pop);
						}
					});
		        }, no: function(){
		        	$("#btnTenderBorrow").attr("onclick","tender_borrow()");
		        }
		    }
		}); 
	}
	/**
	 * 刷新验证码
	 */
	function loadimage() {
		document.getElementById("randImage").src = "${basePath}/random.jsp?"
				+ Math.random();
	}
	/**
	 * 验证开通数据
	 */
	function validateTenderData() {
		var msg = "";
		var hidAccountReal = Number($("#accountReal").val());
		var hidUseMoney = Number($("#useMoney").val());
		var payPassword = $("#payPassword").val();
		var checkCode = $("#checkCode").val();
		var maxCurMoney = Number("${maxCurMoney}");
		if($("#isUseCurMoney").val() == 1){
			if(hidAccountReal > new Number(hidUseMoney + maxCurMoney)){
				msg = msg + "-账户余额不足,请充值！<br/>";
			}
		}else{
			if (hidAccountReal > hidUseMoney) {
				msg = msg + "-账户余额不足,请充值！<br/>";
			}
		}
		if (payPassword == null || payPassword == "") {
			msg = msg + "-交易密码未填写！<br/>";
		}
		if (checkCode == null || checkCode == "") {
			msg = msg + "-验证码未填写！<br/>";
		}
		if (msg != "") {
			parent.layer.msg(msg, 2, 5);
			return false;
		}
		return true;
	}
	
	/**
	 * 使用活期宝金额
	 */
	function useCurMoney(){
		var isUseCurMoney = $("#isUseCurMoney").val();
		if(isUseCurMoney == 0){
			$("#isUseCurMoney").val(1);
			$("#isUseCurMoney").attr("checked","checked");
		}else{
			$("#isUseCurMoney").val(0);
			$("#isUseCurMoney").removeAttr("checked");
		}
	}
	
	$(function() {
		useCurMoney();//2015.12.18 默认勾选活期宝
	});
//-->
</script>
</html>