<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" import="org.springframework.web.util.WebUtils,com.dxjr.portal.member.controller.MemberController,java.net.URLDecoder"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page import = "com.dxjr.portal.statics.BusinessConstants" %>
<%@ include file="/WEB-INF/page/common/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>顶玺金融互联网专业的P2P网站</title>
<link href="${basePath}/css/Style.css" type="text/css" rel="stylesheet" />
<link href="${basePath}/css/belstar.css" rel="stylesheet" type="text/css" />

<script type="text/javascript" src="${basePath}/js/jquery-1.11.0.js" ></script>
<script type="text/javascript" src="${basePath}/js/jquery-migrate-1.2.1.js" ></script>
<script type="text/javascript">
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
		_domain = 'domain='+domain
	}
	document.cookie = name + "=" + value + expires + "; path=/;"+_domain;
}
</script>
</head>
<body>
<%
Cookie userIdCookie = WebUtils.getCookie(request, MemberController.COOKIE_LOGIN_USERID);
String userId = "";
if (userIdCookie != null) {
	userId = URLDecoder.decode(userIdCookie.getValue(),"UTF-8");
}
%>
<div id="head_outer">   
<div id="outer_header">
      <div class="logo"><a href="${path}/<%=BusinessConstants.TOP_HOME_ADDRESS%>" title="顶玺金融-用心呵护您的财富，分享成长的快乐！"><img src="${basePath}/images/logo.png" width="212" height="83" border="0" /></a></div>
      <div class="topmenulist">
          <div class="dl"><a href="${path }/home/regist.html"> 注册</a>
          <div class="photo1" > 财富热线：400-000-0000</div></div>           
      </div>
  </div>
</div><!--head off-->

<!--内容-->
                <div id="login_bg">
                      <div class="mainLogin">
                 <div id="login_left">
                                <div class="left_title">会员登录</div>
                                <div class="left_input_cw">
                                       <div class="left_input_ts" id="login_tip" style="display:none;">请填写用户名</div>
                                </div>	
       							<div class="left_input">
                                 <span class="input_span">用户名：</span> 
                                    <div class="input" ><input type="text" name="username" id="username" value="<%=userId%>" class="input1"/></div>  
                                 	<span class="showErrors" id="userNameTip" style="display:none"><font color="red" id="userNameTipText"></font></span>
                                 </div>
       							<div class="left_input">
                                 <span class="input_span">密&nbsp;&nbsp;&nbsp;码：</span> 
                                    <div class="input_key" ><input type="password" name="password" id="password" class="input1"/></div>  
                                 	<span class="showErrors" id="password1Tip"><font color="red" id="password1TipText"></font></span>	
                                 </div>
     							<div class="left_input">
                                   <span class="input_span">验证码：</span>
                                      <div class="input_yanzheng" ><input type="text" name="checkcode" id="checkcode" maxlength="4" class="input2"/></div>
                                      <div class="yanzhengma">
                                      		<a href="javascript:loadimage();"> 
												<img name="randImage" id="randImage" src="${basePath}/random.jsp" style="float: left;" border="0" align="middle" /> 
											</a>
                                      </div> 
                                 </div>
                                 <div class="left_remeber">
                                   &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<INPUT TYPE="checkbox" id="saveid" name="saveid" <%=((userIdCookie != null) ? "checked='true'": "")%> 
	   								  onclick="setCookie('<%=MemberController.COOKIE_LOGIN_USERID%>','',-1);"> 记住用户名 &nbsp;&nbsp;<span class="ico2"><a href="${path}/AccountSafetyCentre/safetyCentre/enterFindLoginPwd.html">找回密码</a></span>
                   				 </div>
       							<div class="left_input" style="margin-left:20px;">
                                  <div><a id="btnLogin" name="Submit1" tabindex="1" onclick="submitLogin(this);" href="javascript:void(0);" style="cursor: pointer;">登&nbsp;&nbsp;录</a></div>
                         		</div> 
            </div>
 			<!--结束-->              
</div> 
</div>
<%@ include file="/WEB-INF/page/common/footer.jsp" %>
</body>
<script type="text/javascript">
$(document).ready(function(){
	//回车事件
	document.onkeydown = function(e){ 
		var ev = document.all ? window.event : e; 
		if(ev.keyCode==13) {
			if(typeof($("#btnLogin").attr("onclick"))!="undefined"){
				submitLogin(document.getElementById("btnLogin"));
			}
		}
	}
});
/**
 * 登录
 */
function submitLogin(obj){
	$(obj).removeAttr("onclick");
	var username = $.trim($("#username").val());
	var passwd = $("#password").val();
	var checkCode = $("#checkcode").val();
	var saveidChecked=$("#saveid")[0].checked;
	var saveid = null;
	var cookieusername=encodeURI(username);
	if(saveidChecked){
		saveid = 1;
	}	
	if(username==""||username==null){
		$("#login_tip").html("请输入用户名/邮箱！");
		$("#login_tip").removeAttr("style");
		$(obj).attr("onclick","submitLogin(this)");
		return;
	}else if($("#password").val()=="" || $("#password").val()==null){
		$("#login_tip").html("请输入密码！");
		$("#login_tip").removeAttr("style");
		$(obj).attr("onclick","submitLogin(this)");
		return;
	}else if(checkCode=="" || checkCode==null){
		$("#login_tip").html("请输入验证码！");
		$("#login_tip").removeAttr("style");
		$(obj).attr("onclick","submitLogin(this)");
		return;
	}else{
		$.ajax({
			url : '${basePath }/member/login.html',
			data : {username:username,passwd:passwd,checkCode:checkCode,saveid:saveid,cookieusername:cookieusername},
			type : 'post',
			dataType : 'text',
			success : function(data){
				$(obj).attr("onclick","submitLogin(this)");
				if(data=="success"){
					//window.open("${path}/newdxjr/myaccount/myAccountIndexForinsert.html","_self");//新版系统我的账户
					window.open("${path}/page/islogin.jsp","_self");//新版系统我的账户
					layer.alert("登录成功！" , 1, "温馨提示");
					$("#login").attr("disabled","disabled");
				}else{
					$("#login_tip").html(data);
					$("#login_tip").removeAttr("style");
					loadimage();
				}
			},
			error : function(data) {
				$(obj).attr("onclick","submitLogin(this)");
				layer.msg('网络连接异常，请刷新页面或稍后重试！', 1, 5);
		    }
		});	
	}
}

/**
 * 刷新验证码 
 */
function loadimage() {
	document.getElementById("randImage").src = "${basePath}/random.jsp?" + Math.random();
}
</script>
</html>