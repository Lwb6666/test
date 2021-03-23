<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%> 
<%@ include file="/WEB-INF/page/common/taglib.jsp"%> 
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ include file="/WEB-INF/page/common/public.jsp"%>
<link href="${basePath}/css/login.css" rel="stylesheet" type="text/css" />
<title>找回密码成功_顶玺金融</title>
</head>

<body>
<%@ include file="/WEB-INF/page/common/header.jsp"%>
<div id="Container">
	<div id="gc-login">
		<div class="login_tit loginbg">找回${pwdType }密码</div>
		<div class="login_main loginbg">
			<div class="login_mainbox">
				<dl>
					<dt>
					<img src="${basePath }/images/tu_03.png"/>
						密码已发送到您的${findType }，
						为了您账户安全请尽快修改！
					</dt>
				</dl>
			</div>
		</div>
	</div>
</div>
<%@ include file="/WEB-INF/page/common/footer.jsp"%>
</body>
</html>