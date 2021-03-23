<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/page/common/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ include file="/WEB-INF/page/common/public.jsp"%>
<script type="text/javascript" src="${basePath}/js/formValid.js"></script>
<title>账户管理_安全中心_顶玺金融</title>
</head>

<body>
	<%@ include file="/WEB-INF/page/common/header.jsp"%>
	<div id="Container">
		<div id="Bmain">
			<div class="title">
				<span class="home"><a href="${path}/">顶玺金融</a></span> <span><a
					href="${path}/myaccount/toIndex.html">我的账户</a></span> <span>账户管理</span> <span><a
					href="${path }/AccountSafetyCentre/safetyIndex.html">安全中心</a></span>
			</div>
			<div id="menu_centert">
				<%@ include file="/WEB-INF/page/account/myAccount_leftMenu.jsp"%>
				<div class="lb_waikuang border whitebg">
					<div class="safetoptit">安全中心</div>
					<div class="safetab">

						<table>
							<tr>
								<td class="safetabtit bg1">实名认证</td>
								<c:if test="${realNameApproVo.isPassed==1}">
									<td id="idCardNoId">${realNameApproVo.securityIdCardNo }</td>
									<td>${realNameApproVo.securityRealName}</td>
								</c:if>
								<c:if test="${realNameApproVo.isPassed!=1}">
									<td>未认证</td>
									<td><a class="sbtn" href="javascript:toRealAppro();">设置</a></td>
								</c:if>

								<td>只有通过实名认证，才能提现</td>
							</tr>

							<tr>
								<td class="safetabtit bg2">邮箱验证</td>
								<c:if
									test="${emailApproVo.checked==null or emailApproVo.checked==0}">
									<td text-align="left"><a style="color: #F00;">未设置</a></td>
									<td><a class="sbtn"
										href="${path }/account/approve/email.html">设置</a></td>
								</c:if>
								<c:if test="${emailApproVo.checked== -1}">
									<td text-align="left"><a style="color: #F00;">审核失败</a></td>
									<td><a class="sbtn"
										href="${path }/account/approve/email.html">重新设置</a></td>
								</c:if>
								<c:if test="${emailApproVo.checked==1}">
									<td id="emailId" text-align="left"><a style="color: #F00;">${emailApproVo.securityemail }</a>
									</td>
									<td>已设置</td>
								</c:if>
								<td>邮箱可以收到借款协议和找回密码</td>
							</tr>

							<tr>
								<td class="safetabtit bg3">手机绑定</td>
								<c:if
									test="${mobileAppro.passed == null or mobileAppro.passed!=1}">
									<td>未绑定</td>
									<td><a class="sbtn"
										href="${path }/account/approve/mobile/mobileforinsert.html">设置</a></td>
								</c:if>
								<c:if test="${mobileAppro.passed == 1}">
									<td id="mobileNumId">${mobileAppro.securitymobileNum }</td>
									<td><a class="sbtn"
										href="${path }/account/approve/mobile/mobileforinsert.html">修改</a></td>
								</c:if>
								<td>手机可找回密码并接收顶玺信息</td>
							</tr>
							<tr>
								<td class="safetabtit bg4">登录密码</td>
								<c:if test="${memberVo.logpassword != null}">
									<td>已设置</td>
									<td><span><a class="sbtn border"
											href="${path}/AccountSafetyCentre/safetyCentre/enterResetLoginPwd.html">修改</a>&nbsp;|
											<a class="sbtn"
											href="${path}/AccountSafetyCentre/safetyCentre/enterFindLoginPwd.html">找回</a></span>
									</td>
								</c:if>
								<c:if test="${memberVo.logpassword == null}">
									<td>未设置</td>
									<td><span> <a class="sbtn" href="#">您是怎么进来的？</a></span></td>
								</c:if>
								<td>用户登录时使用</td>
							</tr>
							<tr>
								<td class="safetabtit bg5">交易密码</td>
								<c:if test="${memberVo.paypassword != null}">
									<td>已设置</td>
									<td><span><a class="sbtn"
											href="${path}/AccountSafetyCentre/safetyCentre/enterResetTransactionPwd.html">修改</a>&nbsp;|
											<a class="sbtn"
											href="${path}/AccountSafetyCentre/safetyCentre/enterFindTransactionPwd.html">找回</a></span></td>
								</c:if>
								<c:if test="${memberVo.paypassword == null}">
									<td>未设置</td>
									<td><span> <a class="sbtn"
											href="${path}/account/safe/toSetPayPwd.html">设置</a></span></td>
								</c:if>
								<td>交易密码只用于投标、提现</td>
							</tr>
							<%-- <tr>
								<td class="safetabtit bg6">vip认证</td>
								<c:if test="${vipApproVo == null  or vipApproVo.passed!=1}">
									<td><a style="color: #F00;">未设置</a></td>
									<td><a class="sbtn" href="javascript:toVipAppro();">设置</a>
									</td>
								</c:if>
								<c:if test="${vipApproVo.passed == 1}">
									<td><a style="color: #F00;">已认证</a></td>
									<td><a class="sbtn"
										href="${path }/account/approve/vip/vipforinsert.html">查看</a></td>
								</c:if>
								<td>投标时，vip用户可享受保本保息，非vip用户只保障本金</td>
							</tr> --%>
							<tr>
								<td class="safetabtit bg7">银行卡信息</td>
								<td><c:if
										test="${currentBankCardVo == null or currentBankCardVo.status == -1 }">
										<a style="color: #F00;">未设置</a>
									</c:if> <c:if
										test="${currentBankCardVo != null or currentBankCardVo.status != -1 }">
                    	${currentBankCardVo.securityCardNum }
                    </c:if></td>
								<td><c:if
										test="${currentBankCardVo == null or currentBankCardVo.status == -1 }">
										<a class="sbtn" href="javascript:toAddBankCard();">设置</a>
									</c:if> <c:if
										test="${currentBankCardVo != null and currentBankCardVo.status == 0 }">
										<a class="sbtn" href="javascript:toChangeCard();">更换</a>
									</c:if> <c:if
										test="${currentBankCardVo != null and currentBankCardVo.status == 1 }">
                    	审核中
                    </c:if> <c:if
										test="${currentBankCardVo != null and currentBankCardVo.status == 2 }">
                    	审核未通过
                    </c:if></td>
								<td>用户提现</td>
							</tr>
						</table>
					</div>
					<c:if
						test="${currentBankCardVo != null and currentBankCardVo.status == 1 }">
						<div class="tip pink">
							<h3>温馨提示：</h3>
							<p>您的银行卡正在审核，请耐心等待！客服会在两个工作日内处理或联系。客服热线400-000-0000</p>
						</div>
					</c:if>
					<c:if
					 test="${currentBankCardVo != null and currentBankCardVo.status == 2 }">
                    	<div class="tip pink">
							<h3>温馨提示：</h3>
							<p>您的银行卡未通过审核，请7天后重新申请或联系客服热线400-000-0000</p>
						</div>
                    </c:if>


				</div>
			</div>
		</div>
	</div>
	<div class="clearfix">
		<div>
			<%@ include file="/WEB-INF/page/common/footer.jsp"%>
		</div>
	</div>

	<script type="text/javascript">
		$(function() {
			if ('${msg}' == 'email_mobile_no_appro') {
				layer.alert('请先进行邮箱或手机认证！', 5, "温馨提示");
			}
			if ('${msg}' == 'realName_no_appro') {
				layer.alert('请先进行实名认证！', 5, "温馨提示");
			}
		});
		//去实名认证
		function toRealAppro() {
			$
					.ajax({
						url : '${basePath}/appro/findAppro.html',
						data : {},
						type : 'post',
						success : function(data) {
							if (data == null
									|| (data.emailChecked != 1 && data.mobilePassed != 1)) {
								layer.msg("请先进行邮箱或手机认证！", 2, 5);
							} else {
								location.href = "${path }/account/approve/realname/toRealNameAppro.html";
							}
						},
						error : function(data) {
							layer.msg("网络连接异常，请刷新页面或稍后重试！", 2, 5);
						}
					});
		}

		//去VIP认证
		function toVipAppro() {
			$
					.ajax({
						url : '${basePath}/appro/findAppro.html',
						data : {},
						type : 'post',
						success : function(data) {
							if (data == null
									|| (data.emailChecked != 1 && data.mobilePassed != 1)) {
								layer.msg("请先进行邮箱或手机认证！", 2, 5);
							} else if (data.namePassed != 1) {
								layer.msg("请先进行实名认证！", 2, 5);
							} else {
								location.href = "${path }/account/approve/vip/vipforinsert.html";
							}
						},
						error : function(data) {
							layer.msg("网络连接异常，请刷新页面或稍后重试！", 2, 5);
						}
					});
		}
		function toChangeCard(){
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
						window.location.href = '${basePath}/'+data.message;
					}
				},
				error : function(data) {
					layer.msg("网络连接异常，请刷新页面或稍后重试！");
				}
			});
		}
		/* 添加银行卡 */
		function toAddBankCard() {
			$.post("${basePath}/bankinfo/vaild.html", {}, function(result) {
				if (result.code == '-1') {
					layer.msg(result.message, 1, 5, function() {
						window.location.href="${path}/account/safe/toSetPayPwd.html";
					});
				} else if (result.code == '-2') {
					layer.msg(result.message, 1, 5, function() {
						window.location.href="${path }/account/approve/mobile/mobileforinsert.html";
					});
				} else if (result.code == '-3') {
					layer.msg(result.message, 1, 5, function() {
						window.location.href="${path}/account/approve/realname/toRealNameAppro.html";
					});
				} else if (result.code == '1') {
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
				} else {
					layer.msg("网络连接异常，请刷新页面或稍后重试！", 1, 5);
				}
			});
		}
	</script>
</body>
</html>