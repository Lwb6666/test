<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" import="org.springframework.web.util.WebUtils,com.dxjr.portal.member.controller.MemberController,java.net.URLDecoder"%>
<%@ page import = "com.dxjr.portal.statics.BusinessConstants" %>
<%@ include file="/WEB-INF/page/common/taglib.jsp"%>
<%@ include file="/WEB-INF/page/common/public2.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>顶玺金融互联网专业的P2P网站</title>
</head>
<body>
<%
Cookie userIdCookie = WebUtils.getCookie(request, MemberController.COOKIE_LOGIN_USERID);
String userId = "";
if (userIdCookie != null) {
	userId = URLDecoder.decode(userIdCookie.getValue(),"UTF-8");
}
%>
<div class="header">
	<!--topbar-->
	<div class="topbar">
    	<div class="grid-1100">
            <div class="topbar-left"></div>
        </div>
    </div>
    <!--nav main-->
    <nav class="navbar" role="navigation">
    	<div class="grid-1100">
        	<div class="logo">
    			<a href="${basePath}/"><img src="${basePath}/images/v5/logo.png" alt="顶玺金融"/></a>
            </div>
       </div>
    </nav>
</div><!--头部end-->
<div class="gc-login wrapperbanner">
	<div class="grid-login">
    	<div class="login-background img-wap-small" >
        	<div class="form-col-ts f14 gary" style="height:16px;" >
              <i class="icon"></i><span style="color:red;"  id="tip"></span>
            </div>
        	<div class="form-col-login">
            
    		<input type="text" name="username"  id="username"  class="form-input-login" style="color:#999;"  
    		  value="<%= userId=="" ?"请输入手机号/用户名/邮箱":userId%>" onblur="if(value==''){value='请输入手机号/用户名/邮箱';$('#username').attr('style','color:#999;');}" onfocus="if(value=='请输入手机号/用户名/邮箱'){value='';$('#username').attr('style','color:#FFFFF;'); }"   />  
            	 
            </div>
            <div class="form-col-login">
               <input  id="passwordText"  class="form-input-login"  style="color:#999;" value="请输入密码"/>
               <input type="password"  id="payPassword" name="password"  class="form-input-login" style="display:none;color:#999;" /> 
            </div>
             <div class=" form-col-login" id="codeView"  style="display:none;" >
            <input type="text"  id="checkCode"   maxlength="4" class="form-input-login " style="color:#999; width:148px;" onfocus="if(value=='请输入验证码'){value='';$('#codeView').css('color','#FFFFF'); }"  value="请输入验证码"  onkeyup="$(this).css('color','#000');" onblur="if($(this).val()==''){$(this).css('color','#999');value='请输入验证码';};" /> <span style=" margin-left:5px;  ">
            <a href="javascript:loadimage();"><img  id="randImage" style="width:83px; height:38px;" src="${basePath}/random.jsp"/></a></span>
           
           
            </div>
            <div class="form-col-login">
                <a href="${path}/AccountSafetyCentre/safetyCentre/findPhone.html" class="fr bule f14">忘记密码</a>
                
                <div class="fl">
                  <input type="checkbox" id="checkboxFourInput" name="saveid" <%=((userIdCookie != null) ? "checked='true'": "")%>/>
                </div>
                <span class="f14 gary2" style="padding-left:8px;">记住用户名</span>
            </div>
            <div class="form-col-login">
            	<div class="btn-box center">
            			<input type="hidden" name="revision" id="revision" value="${revision}" />
                        <input value="立即登录" id="btnLogin" name="Submit1" onclick="submitLogin(this);" type="button" class="btn-big btn-primary" style="width:100%"/>
                </div>
            </div>
            <div class="form-col-login center">
                <span class="f14 gary2">没有账号？</span><a href="${path }/member/toRegisterPage.html" class="f14 bule">免费注册</a>
            </div>
        </div>
    </div>
</div>
<div class="simplefooter">© Copyright 2016 上海顶玺金融信息服务有限公司 版权所有 | 上海市普陀区华池路58弄新体育3楼  电话：021-00000000 |  沪ICP备13021943号-2 </div>
</body>
<style type="text/css">
@media only screen and (min-width: 321px) and (max-width: 1024px){ 
.gc-login{ width:1100px; } 
} 

</style>
<script type="text/javascript">
$(document).ready(function(){ 
	$(".form-input-login").focus(function(){ 
		if($(this).attr('id')=='passwordText'){ 
			$(this).hide(); 
			$('#payPassword').show(); 
			$('#payPassword').focus(); 
			 $("#payPassword").attr("style","color:#FFFFF;");  
		} 
	}); 
	$(".form-input-login").blur(function(){ 
		if($(this).attr('id')=='payPassword' && $(this).val()==''){ 
			$(this).hide(); 
			$('#passwordText').show(); 
			$('#passwordText').val('请输入密码'); 
			$("#passwordText").attr("style","color:#999;");
		} 
		}); 
	}); 
	
$(function(){
	
	<%
	if(userIdCookie!=null){
	%>
	 $('.checkboxFour label').css('background-position','-192px -28px');
	 $('#checkboxFourInput').attr('checked','checked');
	<%
	}else{
	%>
	 $('.checkboxFour label').css('background-position','-218px -28px');
	 $('#checkboxFourInput').attr('checked',false);
	<%  
	}
	%>
	if($('#username').val() !='请输入手机号/用户名/邮箱'){
		$('#username').css('color','#000');
	}
	if('${counter}'>2){
		$('#codeView').css('display','block');
	}
	
});
function clean_default(element_name)
{   
    if((element_name=='username') && ($('#username').val()=='请输入手机号/用户名/邮箱')){
	     $('#username').css('color','#000');
		 $('#username').val('');
    }
    if($('#username').val()!='请输入手机号/用户名/邮箱'){
    	$('#username').css('color','#000');
    }else{
		 $('#username').css('color','#ddd');
	}
    
	return true;
}

function set_default(element_name){

	if((element_name=='username')&&($('#username').val()=='')){
		 $('#username').val('请输入手机号/用户名/邮箱');
	}
	if($('#username').val() !='请输入手机号/用户名/邮箱'){
		$('#username').css('color','#000');
	}else{
        $('#username').css('color','#ddd');
	}
	return true;
}
 

function checkBoxView(){
	if ($('.checkboxFour label').css('background-position').indexOf('218')!=-1) {
		$('.checkboxFour label').css('background-position','-192px -28px');
	}else{
		$('.checkboxFour label').css('background-position','-218px -28px');
	}
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
		var _load;
		$(obj).unbind("click");
		var username = $.trim($("#username").val());
		var passwd = $("#payPassword").val();
		var checkCode = $("#checkCode").val();
		var saveidChecked = $("#checkboxFourInput")[0].checked;
		var saveid = null;
		var cookieusername = encodeURI(username);
		 
		if (saveidChecked) {
			saveid = 1;
		}else{
			saveid = 0;
		}
        
	   	if (username == "" || username == null || username == '请输入手机号/用户名/邮箱') {
			$('.form-col-ts').children('i').addClass('error');
			$("#tip").html("请您输入手机号/用户名/邮箱");
			$(obj).bind("click", "submitLogin(this)");
			return;
		}  
		if (passwd == "" || passwd == null) {
			$('.form-col-ts').children('i').addClass('error');
			$("#tip").html("请您输入账号密码");
			$(obj).bind("click", "submitLogin(this)");
			return;
		}  
		 
		$.ajax({
			url : '${basePath }/member/login.html',
			data : {
				username : username,
				passwd : passwd,
				checkCode : checkCode,
				saveid : saveid,
				cookieusername : cookieusername,
				backUrl: '${backUrl}',
				revision: $('#revision').val()
			},
			type : 'post',
			dataType : 'json',
			beforeSend:function(){
				_load = layer.load('登录中..');
			},
			success : function(data,status) {
				layer.close(_load);
				loadimage();
				$(obj).bind("click", "submitLogin(this)");
				if (data.code == '0') {
					$('.form-col-ts').children('i').addClass('error');
					$("#tip").html(data.message);
					$('#revision').val(data.revision);
				} else if(data.code == '3'){//登陆失败
					 
					if(parseInt(data.counter)>2){
					  $('#codeView').css('display','block');
					}
					$('.form-col-ts').children('i').addClass('error');
					$("#tip").html(data.message);
					$('#revision').val(data.revision);
				}else if(data.code == '1') {
				    window.open("${path}/myaccount/toIndex.html", "_self");//新版系统我的账户
					$("#login").attr("disabled", "disabled");
				}
				else if(data.code == '2'){
					window.open("${basePath}/AccountSafetyCentre/safetyIndex.html","_self");//前往注册认证界面
					$("#login").attr("disabled", "disabled");
				}else if(data.code == '8'){
					window.location.href=data.message; //跳转到登陆前需要认证的页面
				}
			},
			error : function(data) {
			 
				$(obj).bind("click", "submitLogin(this)");
				layer.msg('网络连接异常，请刷新页面或稍后重试！', 1, 5);
				$('#revision').val(data.revision);
			},
			statusCode: {
				10001: function() {
					layer.msg('请不要频繁请求，稍后再试！', 1, 5);
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
</script>
<%@ include file="/WEB-INF/page/common/seo.jsp" %>
</html>