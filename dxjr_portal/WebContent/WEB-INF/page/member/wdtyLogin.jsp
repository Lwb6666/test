<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" import="org.springframework.web.util.WebUtils,com.dxjr.portal.member.controller.MemberController,java.net.URLDecoder"%>
<%@ page import = "com.dxjr.portal.statics.BusinessConstants" %>
<%@ include file="/WEB-INF/page/common/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ include file="/WEB-INF/page/common/public.jsp"%>
<title>顶玺金融互联网专业的P2P网站</title>
<link href="${basePath}/css/login.css" rel="stylesheet" type="text/css" />
</head>
<body>
<%
Cookie userIdCookie = WebUtils.getCookie(request, MemberController.COOKIE_LOGIN_USERID);
String userId = "";
if (userIdCookie != null) {
	userId = URLDecoder.decode(userIdCookie.getValue(),"UTF-8");
}
%>

<%@ include file="/WEB-INF/page/common/header.jsp"%>
<div id="Container">
<div id="Bmain">
<div id="gc-login">
	<div class="left-wdtybg"></div>
	<div class="right_bg">		
		<div class="loginbox_form">
		<c:if test="${message != null or message != '' }">
		<span>&nbsp;&nbsp;&nbsp;<a style="color:red;">${message }</a></span>
		</c:if>
		    <input type="hidden" name="signkey" id="signkey" value="${signkey }" />
		    <input type="hidden" name="datas" id="datas" value="${datas }" />		    
			<div class="login_box">			    
				<div class="left_input_ts" id="login_tip" style="display:none;">请填写用户名</div>
				<input name="gcjrUsername" id="gcjrUsername" type="text" class="form_span" value="<%= userId=="" ?"请输入用户名/邮箱":userId%>" 
				onfocus="return clean_default('gcjrUsername');" onblur="return set_default('gcjrUsername');"/>
				<p class="placeholder" style="display: none;">请输入用户名/邮箱</p>
				<span class="showErrors" id="userNameTip" style="display:none"><font color="red" id="userNameTipText"></font></span>
			</div>
		</div>
		<div class="loginbox_form login_top">
			<div class="login_box ">
				<input type="password" name="password" id="password" class="formbox" value="" title="请输入密码" />
				<span class="showErrors" id="password1Tip"><font color="red" id="password1TipText"></font></span>
			</div>
		</div>
		<div class="loginbox_form login_top">
			<div class="login_box1">
				<span class="remenber">
					<input type="checkbox" id="saveid" name="saveid" <%=((userIdCookie != null) ? "checked='true'": "")%> 
						onclick="setCookie('<%=MemberController.COOKIE_LOGIN_USERID%>','',-1);"/>记住用户名 
				</span> 
				<span class="right_password"><a href="${path}/AccountSafetyCentre/safetyCentre/enterFindLoginPwd.html">忘记密码</a></span> 
			</div>
		</div>
		<div class="loginbox_form login_top" align="center">
			<input id="btnLogin" name="Submit1" onclick="submitLogin(this);" type="button" class="login_btn" value="立即登录" style="cursor: pointer;"/>
		</div>
		<div class="loginbox_form login_top">
			<p class="login_msg">
				没有账号？<a href="${path }/member/toRegisterPage.html"> 免费注册</a>
			</p>
		</div>
		
	</div>
</div>
</div>
</div>
<%@ include file="/WEB-INF/page/common/footer.jsp"%>

</body>
<script type="text/javascript">

function clean_default(element_name)
{
    if((element_name=='gcjrUsername') && ($('#gcjrUsername').val()=='请输入用户名/邮箱')){
		 $('#gcjrUsername').val('');
	}
/*     if((element_name=='password') && ($('#password').val()=='请输入密码')){
		 $('#password').val('');
		 $("#password").attr("type",'password');
	} */
	return true;
}

function set_default(element_name){

	if((element_name=='gcjrUsername')&&($('#gcjrUsername').val()=='')){
		 $('#gcjrUsername').val('请输入用户名/邮箱');
	}
/* 	if((element_name=='password')&&($('#password').val()=='')){
		 $('#password').val('请输入密码');
		 $("#password").attr("type",'text');
	} */
	
	return true;
}




/**
 * 设置cookie
 */
function setCookie(name, value, days, domain) {
	var expires = "";
	var _domain = '';
	if(days) {
		var date = new Date();
		date.setTime(date.getTime() + (days * 86400000));
		expires = "; expires=" + date.toGMTString();
	}
	if(domain) {
		_domain = 'domain='+domain;
	}
	document.cookie = name + "=" + value + expires + "; path=/;"+_domain;
}

	$(document).ready(function() {
		//回车事件
		document.onkeydown = function(e) {
			var ev = document.all ? window.event : e;
			if (ev.keyCode == 13) {
				if (typeof ($("#btnLogin").attr("onclick")) != "undefined") {
					submitLogin(document.getElementById("btnLogin"));
				}
			}
		}
	});
	/**
	 * 登录
	 */
	function submitLogin(obj) {
		$(obj).removeAttr("onclick");		
		var gcjrUsername = $.trim($("#gcjrUsername").val());		
		var passwd = $("#password").val();		
		var saveid = null;
		var cookieusername = encodeURI(gcjrUsername);
		var signkey = $("#signkey").val();
		var datas = $("#datas").val();		
		if (gcjrUsername == "" || gcjrUsername == null) {
			$("#login_tip").html("请输入用户名！");
			$("#login_tip").removeAttr("style");
			$(obj).attr("onclick", "submitLogin(this)");
			return;
		} else if ($("#password").val() == "" || $("#password").val() == null) {
			$("#login_tip").html("请输入密码！");
			$("#login_tip").removeAttr("style");
			$(obj).attr("onclick", "submitLogin(this)");
			return;
		} else {
			$.ajax({
				url : '${basePath }/wdty/api/wdtyLogin.html',
				async: false,
				data : {
					usernamep : gcjrUsername,
					passwd : passwd,					
					cookieusername : cookieusername,
					signkey : signkey,
					datas : datas
				},
				type : 'post',
				dataType : 'json',
				success : function(data) {
					$(obj).attr("onclick", "submitLogin(this)");
					if (data.code == '0') {
						$("#login_tip").html(data.message);
						$("#login_tip").removeAttr("style");						
					} else if(data.code == '1') {
						var borrowId=data.borrowId;
						if(borrowId != null && borrowId != ''){
							window.open("${path}/toubiao/"+borrowId+".html", "_self");//跳转到借款标详细信息
						}
						$("#login").attr("disabled", "disabled");
					}else if(data.code == '2'){
						var borrowId=data.borrowId;
						if(borrowId != null && borrowId != ''){
							window.open("${path}/zhaiquan/"+borrowId+".html", "_self");//跳转到债权标详细信息
						}
						$("#login").attr("disabled", "disabled");
					}else if(data.code == '3'){
						var borrowId=data.borrowId;
						if(borrowId != null && borrowId != ''){
							window.open("${path}/dingqibao/"+borrowId+".html", "_self");//跳转到定期宝详细信息
						}
						$("#login").attr("disabled", "disabled");
					}else {
						layer.alert(data.message);
					}
				},
				error : function(data) {
					$(obj).attr("onclick", "submitLogin(this)");
					layer.msg('网络连接异常，请刷新页面或稍后重试！', 1, 5);
				}
			});
		}
	}

	/**
	 * 刷新验证码 
	 */
	/* function loadimage() {
		document.getElementById("randImage").src = "${basePath}/random.jsp?"
				+ Math.random();
	} */
</script>
</html>