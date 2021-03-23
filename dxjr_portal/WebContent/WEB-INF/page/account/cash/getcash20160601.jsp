<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ include file="/WEB-INF/page/common/taglib.jsp"%>

<!-- 提现 -->
<div class="tixianmain whitebg">
	<form action="" method="post" name="takeCashForm" id="takeCashForm">
		<input id="fee" type="hidden" />
		<div class="sf">
			<div class="safebox550 pdt">
				<dl>
					<dd style="width: 650px;">
						<input id="bankInfoId" type="hidden" name="bankInfoId" value="${currentBankCardVo.id }" /> <span><font color="#FF0004">*</font>选择银行卡：</span>
						<%-- 没有绑定银行卡 --%>
						<c:if test="${currentBankCardVo == null }">
							<div class="grid fl bd-bline">
								<a href="javascript:toAddBankCard();"><img src="${basePath}/images/add-btn.png" />&nbsp;<strong>添加银行卡</strong></a>
							</div>
						</c:if>
						<%-- 正常绑定银行卡 status为0 --%>
						<c:if test="${currentBankCardVo != null and currentBankCardVo.status == 0}">
							<div class="grid fl bd-line" style="width: 140px;">
								<img src="${basePath}/images/bankicon/${currentBankCardVo.bankCode}.png" /><strong>${currentBankCardVo.bankName }</strong>
								<p class="pdl10 f12">尾号${currentBankCardVo.bankCardTailNum }</p>
							</div>&nbsp;<a href="javascript:toChangeCard();">更换银行卡</a>
						</c:if>
						<%-- 银行卡审核中 --%>
						<c:if test="${currentBankCardVo != null and currentBankCardVo.status == 1}">
							<div class="fl grid bd-line mr5" style="width: 140px; line-height: 25px;">
								<img src="${basePath}/images/bankicon/${currentBankCardVo.bankCode}.png" /><strong>${currentBankCardVo.bankName }</strong>
								<p class="pdl10 f12">尾号${currentBankCardVo.bankCardTailNum }</p>
							</div>
							<div class="grid1 fl bd-line textcenter pink" style="line-height: 25px;">您的银行卡正在审核，请耐心等待！客服会在两个工作日内处理或联系。客服热线400-000-0000</div>
						</c:if>
						<%-- 银行卡被冻结 --%>
						<c:if test="${currentBankCardVo != null and currentBankCardVo.status == 2}">
							<div class="fl grid bd-line mr5" style="width: 140px; line-height: 25px;">
								<img src="${basePath}/images/bankicon/${currentBankCardVo.bankCode}.png" /><strong>${currentBankCardVo.bankName }</strong>
								<p class="pdl10 f12">尾号${currentBankCardVo.bankCardTailNum }</p>
							</div>
							<div class="grid1 fl bd-line textcenter pink" style="line-height: 25px;">您的银行卡未通过审核，请7天后重新申请或联系客服热线400-000-0000</div>
						</c:if>
					</dd>
					<dd style="color: red;">
						<span>可提金额：</span>
						<fmt:formatNumber value="${maxDrawMoney }" pattern="#,##0.00" />
						&nbsp;元
					</dd>
					<dd>
						<span>受限金额：</span>
						<fmt:formatNumber value="${accountVo.noDrawMoney }" pattern="#,##0.00" />
						&nbsp;元&nbsp;&nbsp;<input class="safe_button01" type="button" value="转可提" onclick="toDraw();" style="cursor: pointer;" />
					</dd>
					<dd>
						<span><font color="#FF0004">*</font>提现金额：</span> <input type="text" maxlength="9" id="takeMoney" name="takeMoney" />&nbsp;元 &nbsp;（100到500000之间）
					</dd>
					<dd>
						<span>提现费用：</span><label id="feeDiv">0元&nbsp;（每5万元收取2元手续费） </label>
					</dd>
					<dd>
						<span>预计到账日期：</span>1-3个工作日（双休日和法定节假日除外）之内到账
					</dd>
					<dd>
						<span><font color="#FF0004">*</font>平台交易密码：</span>
						<c:if test="${null!=nosetPaypassword }">
							<a href="${path}/account/safe/toSetPayPwd.html">请先设置交易密码</a>
						</c:if>
						<c:if test="${null==nosetPaypassword }">
							<input type="password" name="paypassword" id="paypassword" style="width: 200px" />
						</c:if>
						<a href="${path}/AccountSafetyCentre/safetyCentre/enterFindTransactionPwd.html">忘记密码</a>
					</dd>
				</dl>
				<div class="gg_btn">
					<c:if test="${currentBankCardVo == null or currentBankCardVo.status == -1}">
						<input type="button" disabled="disabled" value="提现" id="btnGetCash" style="cursor: pointer;" />
					</c:if>
					<c:if test="${currentBankCardVo != null and currentBankCardVo.status != -1}">
						<input type="button" value="提现" id="btnGetCash" style="cursor: pointer;" />
					</c:if>

				</div>
			</div>
		</div>
	</form>
	<hr class="hr clearfix" />
	<div class="tip">
		<h3>温馨提示：</h3>
		<p>1、平台禁止洗钱、信用卡套现、虚假交易等行为，一经发现并确认，将终止该账户的使用。</p>
		<p>2、充值投标回款金额提现为每笔（最低100元最高5万元，申请时可提交最高50万，但按照10笔打款并收取相应手续费 ）代银行收取2元提现手续费，<label style="color:red;">每月前三笔提现申请免提现费</label>。</p>
		<p>3、充值未投标金额申请提现需将受限金额转化为可提现金额，转化费用为总额的0.5%。但新注册用户首次充值未经投标将无法转可提。</p>
		<!-- <p>4、终身顶级会员享受免提现手续费特权。</p> -->
	</div>
</div>
<script language="javascript">
	var _load;
	$(function() {
		var reg1 = /^(([1-9]\d{0,9})|0)(\.\d{1,2})?$/;
		//金额框变化事件
		$("#takeMoney").change(function() {
			var takeMoney = $("#takeMoney").val();
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
					$("#fee").val(fee);
					$("#feeDiv").html(fee + "元");
				}
			}
		});
		$("#btnGetCash").bind("click", apply);
	});
	/**
	 * 申请提交
	 */
	function apply() {
		$("#btnGetCash").unbind("click");
		//验证提现提交的数据是否正确
		if (!validateTakeMoneyData()) {
			$("#btnGetCash").bind("click", apply);
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
													$("#btnGetCash").bind(
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
													$("#btnGetCash").bind(
															"click", apply);
													layer
															.msg(
																	"网络连接异常，请刷新页面或稍后重试！",
																	2, 5);
												}
											});
						}, function() {
							$("#btnGetCash").bind("click", apply);
						}))
			;
	}
	/**
	 * 验证提现提交的数据是否正确
	 */
	function validateTakeMoneyData() {
		//金额的正则表达式
		var reg1 = /^(([1-9]\d{0,9})|0)(\.\d{1,2})?$/;
		var takeMoney = $("#takeMoney").val();
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

		var paypassword = $("#paypassword").val();
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
							$
									.layer({
										type : 2,
										fix : false,
										shade : [ 0.6, '#E3E3E3', true ],
										shadeClose : true,
										border : [ 10, 0.7, '#272822', true ],
										title : [ '', false ],
										offset : [ '50px', '' ],
										area : [ '550px', '480px' ],
										iframe : {
											src : '${basePath}/myaccount/todrawLog/toGetDraw.html'
										},
										close : function(index) {
											window.location.href = "${path}/myaccount/cashRecord/toCashIndex.html";
											layer.close(index);
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
