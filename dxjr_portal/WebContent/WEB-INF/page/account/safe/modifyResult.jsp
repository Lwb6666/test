<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%> 
<%@ include file="/WEB-INF/page/common/taglib.jsp"%> 
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ include file="/WEB-INF/page/common/public.jsp"%>
<title>修改密码成功_顶玺金融</title>
</head>

<body>
<%@ include file="/WEB-INF/page/common/header.jsp"%>
<div id="Container">
	<div id="Bmain">
		<div class="title">
			<span class="home"><a href="${path}/">顶玺金融</a></span>
			<span><a href="${path}/myaccount/toIndex.html">我的账户</a></span>
			<span>账户管理</span>
			<span><a href="${path }/AccountSafetyCentre/safetyIndex.html">安全中心</a></span>
			<span>修改密码</span>
		</div>
		<div id="menu_centert">
			<%@ include file="/WEB-INF/page/account/myAccount_leftMenu.jsp"%>
			<div class="lb_waikuang border whitebg">
				<div class="safetoptit">安全中心</div>
				<div class="modify safebox450">
					<p><c:if test="${type==1 }">登录</c:if><c:if test="${type==2 }">交易</c:if>密码修改成功</p>
					<dl>
						<c:if test="${mobileAppro==false }">
						<dd>
							手机还未绑定？<a href="${path }/account/approve/mobile/mobileforinsert.html">去绑定</a>温馨提示：绑定手机方便您以后修改密码
						</dd>
						</c:if>
						<c:if test="${emailAppro==false }">
						<dd>
							邮箱还未验证？<a href="${path }/account/approve/email.html">去绑定</a>温馨提示：绑定邮箱方便您以后修改密码
						</dd>
						</c:if>
						<%-- <c:if test="${vipAppro==false }">
						<dd>
							加入vip可自动投标，还不是vip？<a href="${path }/account/approve/vip/vipforinsert.html">去认证</a>
						</dd>
						<dd>
							<div class="tipone" style="padding-left: 0 !important;">
								<span>温馨提示：</span>投标时，vip用户可享受保本保息，非vip用户只保障本金
							</div>
						</dd>
						</c:if> --%>
						
					</dl>
				</div>
			</div>
			</div>
	</div>
</div>
<div class="clearfix"></div>
<%@ include file="/WEB-INF/page/common/footer.jsp"%>
</body>
</html>