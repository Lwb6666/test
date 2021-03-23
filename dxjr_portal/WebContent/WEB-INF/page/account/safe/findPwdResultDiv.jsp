<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/page/common/taglib.jsp"%>
<%@ include file="/WEB-INF/page/common/public4.jsp"%>
<script type="text/javascript" src="${basePath}/js/formValid.js"></script>
<!---------- ------------------------找回密码结果提示-弹层---------------------------------------------------->
<div class="cont-word">
	<div class="form-info-layer">
		<p style="padding:60px 0; line-height:2">
			<span class="f16"><i class="iconfont orange2 mr10">&#xe610;</i>找回${pwdType }密码！</span>
			<br>
			<span class="f14">密码已发送到您的${findType }，为了您账户安全请尽快修改！！</span>
		</p>
	</div>
</div>
<script type="text/javascript">
	$(document).ready(function(){
		setTimeout(function(){
			window.location= '${path}/AccountSafetyCentre/safetyIndex.html';
		},2000);
	});
</script>
