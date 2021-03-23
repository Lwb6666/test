<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ include file="/WEB-INF/page/common/taglib.jsp"%>

<!-- 提现 -->
<div class="tbl-cont" id="dress-size">
	<form action="" method="post" name="takeCashForm" id="takeCashForm">
    <input id="fee" type="hidden" />
		<div class="cash">
		<p>
			<%-- 正常绑定银行卡 status为0 --%>
			<c:if test="${currentBankCardVo != null and currentBankCardVo.status != -1}">
				<span class="cash-tit">提现至银行卡</span>
				 <c:if test="${currentBankCardVo.bankCode =='ABC'}">
				    <span class="bankpic"><img src="${basePath}/images/myaccount/banklogo/abc.gif" /></span>
				 </c:if>
				 <c:if test="${currentBankCardVo.bankCode =='BOC'}">
				    <span class="bankpic"><img src="${basePath}/images/myaccount/banklogo/boc.gif" /></span>
				 </c:if>
				 <c:if test="${currentBankCardVo.bankCode =='CCB'}">
				    <span class="bankpic"><img src="${basePath}/images/myaccount/banklogo/ccb.gif" /></span>
				 </c:if>
				 <c:if test="${currentBankCardVo.bankCode =='CEB'}">
				    <span class="bankpic"><img src="${basePath}/images/myaccount/banklogo/ceb.gif" /></span>
				 </c:if>
				 <c:if test="${currentBankCardVo.bankCode =='CIB'}">
				    <span class="bankpic"><img src="${basePath}/images/myaccount/banklogo/cib.gif" /></span>
				 </c:if>
				 <c:if test="${currentBankCardVo.bankCode =='CITIC'}">
				    <span class="bankpic"><img src="${basePath}/images/myaccount/banklogo/citic.gif" /></span>
				 </c:if>
				 <c:if test="${currentBankCardVo.bankCode =='CMB'}">
				    <span class="bankpic"><img src="${basePath}/images/myaccount/banklogo/cmb.gif" /></span>
				 </c:if>
				 <c:if test="${currentBankCardVo.bankCode =='CMBC'}">
				    <span class="bankpic"><img src="${basePath}/images/myaccount/banklogo/cmbc.gif" /></span>
				 </c:if>
				 <c:if test="${currentBankCardVo.bankCode =='GDB'}">
				    <span class="bankpic"><img src="${basePath}/images/myaccount/banklogo/gdb.gif" /></span>
				 </c:if>
				 <c:if test="${currentBankCardVo.bankCode =='HXB'}">
				    <span class="bankpic"><img src="${basePath}/images/myaccount/banklogo/hxb.gif" /></span>
				 </c:if>
				 <c:if test="${currentBankCardVo.bankCode =='ICBC'}">
				    <span class="bankpic"><img src="${basePath}/images/myaccount/banklogo/icbc.gif" /></span>
				 </c:if>
				 <c:if test="${currentBankCardVo.bankCode =='PSBC'}">
				    <span class="bankpic"><img src="${basePath}/images/myaccount/banklogo/psbc.gif" /></span>
				 </c:if>
				 <c:if test="${currentBankCardVo.bankCode =='SPDB'}">
				    <span class="bankpic"><img src="${basePath}/images/myaccount/banklogo/spdb.gif" /></span>
				 </c:if>
				  <c:if test="${currentBankCardVo.bankCode =='SZPAB'}">
				    <span class="bankpic"><img src="${basePath}/images/myaccount/banklogo/szpab.gif" /></span>
				 </c:if>
				  <span class="gray9 ml20">尾号:${currentBankCardVo.bankCardTailNum }</span><a href="${basePath}/AccountSafetyCentre/safetyIndex.html" class="ml20 blue">更换银行卡</a>
			</c:if>
		</p>
		<p>
			<span class="cash-tit">账户余额</span><span class="orange f18">${maxDrawMoney }元</span>
		</p>
		<p>
			<span class="cash-tit">受限金额</span><span><fmt:formatNumber value="${accountVo.noDrawMoney }" pattern="#,##0.00" /> 元</span><a href="javascript:void(0);" onclick="toDraw();"   class="ml20 blue">转可提</a>
		</p>
		<p>
			<span class="cash-tit">提现金额</span><span class="relative"><input type="text" id="takeMoney5" name="takeMoney"   placeholder="100到500000之间" style="width:200px; padding-right:20px; margin-top:-5px;"><span class="yuan gray9">元</span></span>
		</p>
		<p>
			<span class="cash-tit">可提费用</span><span id="feeDiv" class="">0元</span><c:if test="${isSvip=='no'}"><span class="ml20 gray9">（本月还有<strong id="freeCount"></strong>次免费提现机会）</span></c:if>
		</p>
		<p>
			<span class="cash-tit">顶玺金融交易密码</span><span>
				<c:if test="${null==nosetPaypassword }">
					<input type="password" name="paypassword" id="paypassword5" />
				</c:if>
			 </span><a href="${basePath}/AccountSafetyCentre/safetyIndex.html" class="ml20 blue">忘记密码？</a>
		</p>
		<p>
			<c:if test="${currentBankCardVo != null and currentBankCardVo.status != -1}">
			    <span class="cash-tit"></span><a  id="btnGetCash" name="btnGetCash" class="btn btn-primary">提现</a>
			</c:if>
			
		</p>
	</div>
	<ul class="recharge-tip gray9 clearfix">
						<li class="mr10 f16">温馨提示</li>
						<li>1. 平台禁止洗钱、信用卡套现、虚假交易等行为，一经发现并确认，将终止该账户的使用。<br>
                                2. 充值投标回款金额提现为每笔（最低100元最高5万元，申请时可提交最高50万，但按照10笔打款并收取相应手续费 ）代银行收取2元提现手续费，<span class="red">每月前三笔提现申请免提现费。</span><br>
                                3. 充值未投标金额申请提现需将受限金额转化为可提现金额，转化费用为总额的0.5%。但新注册用户首次充值未经投标将无法转可提。
						</li>
					</ul>
	</form>
</div>
<script language="javascript">
	var _load;
	$(function() {
		var cashedCount = new Number('${getCashedCount}');
		var freeCount = 0; //免费次数
		if(cashedCount < 3){
			freeCount = 3 - cashedCount;
		}
		$("#freeCount").html(freeCount);
		
		var reg1 = /^(([1-9]\d{0,9})|0)(\.\d{1,2})?$/;
		//金额框变化事件
		$("#takeMoney5").change(function() {
			var takeMoney = $("#takeMoney5").val();
			if (!reg1.test(takeMoney)) {
				return;
			}
			if (takeMoney != '') {
				$("#feeDiv").html("0元");
				$("#fee").val(0);
				if (takeMoney < 100) {
					layer.msg("提现金额不能小于100元", 2, 5);
				} else if (takeMoney > 500000) {
					layer.msg("提现金额不能大于500000元", 2, 5);
				}
				if ('${isSvip}' == 'yes') {
					$("#fee").val(0);
					$("#feeDiv").html("0元");
				} else {
					var fee = Math.ceil(Number(takeMoney) / 50000) * 2;
					var cashedCount = new Number('${getCashedCount}');
					var freeCount = 0; //免费次数
					if(cashedCount < 3){
						freeCount = 3 - cashedCount;
					}
					if(fee > Number(freeCount*2)){
						fee = fee - Number(freeCount*2);
					}else{
						fee = 0;
					}
					$("#freeCount").html(freeCount);
					$("#fee").val(fee);
					$("#feeDiv").html(fee + "元");
				}
			}
		}); 
		$("a[name='btnGetCash']").bind("click", apply);
	});
	/**
	 * 申请提交
	 */
	function apply() {
		$("a[name='btnGetCash']").unbind("click");
		//验证提现提交的数据是否正确
		if (!validateTakeMoneyData()) {
			$("a[name='btnGetCash']").bind("click", apply);
			return;
		}
		var fee = $("#fee").val();
		var remark = "您的手续费为：" + fee + "元，确认要提现吗？";
		if(fee == 0){
			remark = "确认要提现吗？";
		}
		if (layer
				.confirm(remark,
						function() {
							_load = layer.load('处理中..');
							//提交后台
							$("#takeCashForm")
									.ajaxSubmit(
											{
												url : '${basePath }/myaccount/cashRecord/saveTakeCash.html',
												type : "POST",
												success : function(data) {
													layer.close(_load);
													$("a[name='btnGetCash']").bind(
															"click", apply);
													if (data == "success") {
														layer
																.msg(
																		"提现申请成功！等待财务审核！",
																		1,
																		1,
																		function() {
																			window.location.href = "${path}/myaccount/cashRecord/toCashIndex.html";
																		});
													} else {
														if (data != '') {
															layer.msg(data, 2,
																	5);
														}
													}
												},
												error : function(data) {
													layer.close(_load);
													$("a[name='btnGetCash']").bind(
															"click", apply);
													layer
															.msg(
																	"网络连接异常，请刷新页面或稍后重试！",
																	2, 5);
												}
											});
						}, function() {
							$("a[name='btnGetCash']").bind("click", apply);
						}))
			;
	}
	/**
	 * 验证提现提交的数据是否正确
	 */
	function validateTakeMoneyData() {
		//金额的正则表达式
		var reg1 = /^(([1-9]\d{0,9})|0)(\.\d{1,2})?$/;
		var takeMoney = $("#takeMoney5").val();
		if (takeMoney.length == 0) {
			layer.msg("提现金额不能为空！", 2, 5);
			return false;
		}
		if (!reg1.test(takeMoney)) {
			layer.msg("提现金额不是正确的金额！", 2, 5);
			return false;
		}
		takeMoney = Number(takeMoney);
		if (takeMoney < 100) {
			layer.msg("提现金额必须大于￥100！", 2, 5);
			return false;
		}
		if (takeMoney > 500000) {
			layer.msg("提现金额必须小于￥500000！", 2, 5);
			return false;
		}
		//比较是否大于最大提现额度
		var canTakeMoney = Number('${accountVo.drawMoney}').toFixed(2);
		if (canTakeMoney < 100) {
			layer.msg("可提余额不足￥100！", 2, 5);
			return false;
		}
		if (takeMoney > canTakeMoney) {
			layer.msg("提现金额不能超过￥" + canTakeMoney, 2, 5);
			return false;
		}

		var remainMoney = takeMoney % 50000;
		if (remainMoney > 0 && remainMoney <= 2) {
			layer.msg("尾数提现金额必须大于2元,请确认!", 2, 5);
			return false;
		}

		var paypassword = $("#paypassword5").val();
		if (paypassword.length == 0) {
			layer.msg("交易密码不能为空！", 2, 5);
			return false;
		}
		//查找嵌套页面中是否有选择的银行卡
		/* if($("#bankInfosUl").find(".current").length!=1){
			layer.msg("请选择提现银行！", 2, 5);
			return false;
		} */

		return true;
	}

	function toDraw() {
		$
				.ajax({
					url : '${basePath}/myaccount/todrawLog/judgeToDraw.html',
					data : {},
					type : 'post',
					dataType : 'json',
					success : function(result) {
						if (result.code == '1') {
							$('#judgeToDraw1').reveal({
			                    animation: 'fade',                  
			                    animationspeed: 300,                      
			                    closeonbackgroundclick: false,              
			                    dismissmodalclass: 'close-reveal-modal'    
			                });
							$.ajax({
								url : '${basePath}/myaccount/todrawLog/toGetDraw.html',
								data :{
								} ,
								type : 'post',
								dataType : 'html',
								success : function(data){
									$("#judgeToDraw1").html(data);
								},
								error : function(data) {
									layer.msg("网络连接异常，请刷新页面或稍后重试！", 2, 5);
							    }
							});
						} else {
							layer.msg(result.message, 1, 5);
						}
					},
					error : function(data) {
						layer.msg("网络连接异常，请刷新页面或稍后重试！", 1, 5); // 弹出框
					}
				});
	}
	function toChangeCard() {
		$.ajax({
			url : "${basePath}/bankinfo/initChangeCard.html",
			data : {},
			type : 'post',
			dataType : 'json',
			success : function(data) {
				if (data.code == '0') {
					layer.alert(data.message);
					return;
				} else {
					window.location.href = '${basePath}/' + data.message;
				}
			},
			error : function(data) {
				layer.msg("网络连接异常，请刷新页面或稍后重试！");
			}
		});
	}
	/* 添加银行卡 */
	function toAddBankCard() {
		$.post("${basePath}/bankinfo/isPhoneValidated.html", {},
				function(data) {
					if (data == 'no') {
						layer.alert('您还没有手机认证！');
					} else if (data == 'yes') {
						$.layer({
							type : 2,
							fix : false,
							shade : [ 0.6, '#E3E3E3', true ],
							shadeClose : true,
							border : [ 10, 0.7, '#272822', true ],
							title : [ '', false ],
							offset : [ '50px', '' ],
							area : [ '1000px', '600px' ],
							iframe : {
								src : '${basePath}/bankinfo/addBankCard.html'
							},
							close : function(index) {
								layer.close(index);
							}
						});
					}
				});
	}
</script>
