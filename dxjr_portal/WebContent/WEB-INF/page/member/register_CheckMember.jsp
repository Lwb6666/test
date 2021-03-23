
<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/page/common/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ include file="/WEB-INF/page/common/public.jsp"%>
<title>注册_通过邮箱验证账户信息_顶玺金融</title>
<link href="${basePath}/css/login.css" rel="stylesheet" type="text/css" />
</head>

<body>
	<!--头部开始-->
	<%@ include file="/WEB-INF/page/common/header.jsp"%>
	<!--头部结束-->
	<!--内容开始-->
	<div id="Bmain">
		<div class="login_main loginbg loginboxm">
                   <div class="reg-steps">
                    <ol class="reg-step-2">
                        <li class="step-1"><span class="txt"><i class="cz_icons  dot1">1</i>填写账户信息</span></li>
                        <li class="step-2"><span class="txt"><i class="cz_icons  dot1">2</i>验证账户信息</span></li>
                        <li class="step-3"><span class="txt"><i class="cz_icons  dot1">3</i>注册成功</span></li>
                    </ol>    
                </div>
                <c:if test="${(portal:currentUser()) != null }">
                 <div class="reg-info">已登录！</div>
                </c:if>
                <c:if test="${(portal:currentUser()) == null }">
                 <div class="reg-info">已有账号，<a href="${path}/member/toLoginPage.html">立即登录</a></div>
                </c:if>

			<div class="right_container">
				<div class="right_content">
					<div class="loginbox_form loginbox_width">
						<div class="login_box form_width">
							<span><font color="#22adce">*</font>邮箱地址：</span> <input
								id="email" name="email" maxlength="50"  type="text" class="form_email" style="color:#000;" value="${currentEmail }"/>
						</div>
					</div>
					<div class="reacquire">
						<span></span>
					</div>
					<div class="loginbox_form login_top loginbox_width">
						<p class="login_msg">
							未收到邮件？<a id="sendEmailAganBtn"  href="javascript:sendEmailVerifyLinkAgain();">重新发送邮件</a>
						</p>
					</div>
					<div class="loginbox_form login_top loginbox_width">
						<input type="button" id="nextOpertingBtn" class="login_btn" onclick="javascript:nextOperting();" value="下一步" />
					</div>
					<div class="loginbox_form login_top loginbox_width">
						<p class="login_msg">
							<a href="javascript:toMobailCheck();">您也可以使用手机认证</a>
						</p>
					</div>
				</div>

			</div>
		</div>

	</div>






	<!--内容结束-->


	<div>
		<%@ include file="/WEB-INF/page/common/footer.jsp"%>
	</div>

</body>
<script type="text/javascript">
var userName = '${userName }';


function toMobailCheck(){
	$.ajax({
        type: "POST",
        url: "${basePath}/account/approve/mobile/isUserMobileBinded.html",
        async: false,        
        success: function(data) {
      	  if(data != null && data == '0'){   
      			layer.alert("当前用户手机异常",5);       			
		  }else if(data != null && data == '1'){
			   layer.alert("当前用户不能为空",5); 
		  }else if(data != null && data == '2'){
			   layer.alert("当前用户手机已绑定",5); 
		  }else{			   
			   window.open("${path }/account/approve/mobile/toMobailCheckMemberInfo.html","_self");
		  }      	
        }
   });	
}
/**
 * 下一步
 */
 function nextOperting(){
	 sendEmailVerifyLink();
}
 

/**
 * 发送邮箱验证链接
 */
function sendEmailVerifyLink(){
	 $('#nextOpertingBtn').attr("disabled","disabled");
	//验证数据
	if(!chenkEmail()){
		$('#nextOpertingBtn').removeAttr("disabled"); 
		return;
	}
	var email = $.trim($('#email').val());
	var _load = layer.load('处理中..');
	$.ajax({
		url : '${basePath }/member/sendEmailVerifyLink.html',
		type : 'post',
		data : {email:email
		},
		success : function(result) {
			$('#nextOpertingBtn').removeAttr("disabled"); 
			layer.close(_load);
			if (result.code=="1") {
				window.open("${path }/member/toEmailAppro.html","_self");
			} else if(result.code == "0" ){
					layer.msg(result.message, 1, 5);
					
			}else{
					layer.msg("发送邮件异常，请稍后再试！", 1, 5);
			}
		},
		error : function(result) {
			$('#nextOpertingBtn').removeAttr("disabled"); 
			layer.close(_load);
			layer.msg("操作异常,请刷新页面或稍后重试！");
	    }
	});
}
/**
 * 重新发送邮箱验证链接
 */
function sendEmailVerifyLinkAgain(){
	document.getElementById('sendEmailAganBtn').href = 'javascript:void(0);';
	var email = $.trim($('#email').val());
	var _load = layer.load('处理中..');
	$.ajax({
		url : '${basePath }/member/sendEmailVerifyLink.html',
		type : 'post',
		data : {email:email,again:'true'
		},
		success : function(result) {
			document.getElementById('sendEmailAganBtn').href = 'javascript:sendEmailVerifyLinkAgain();';
			layer.close(_load);
			if (result.code=="1") {
				window.open("${path }/member/toEmailAppro.html","_self");
			} else if(result.code == "0"){
				layer.msg(result.message, 1, 5);
			}else{
				layer.msg("发送邮件异常，请稍后再试！", 1, 5);
			}
		},
		error : function(result) {
			document.getElementById('sendEmailAganBtn').href = 'javascript:sendEmailVerifyLinkAgain();';
			layer.close(_load);
			layer.msg("操作异常,请刷新页面或稍后重试！");
	    }
	});
}

/**
 * 校验邮箱是否有效
 */
function chenkEmail(){
	var flag = true;
	//邮箱
	 var patten_email = regexEnum.email;
	var email = $.trim($('#email').val());
	$('#email').val(email);
	if(email==null || email==""){
		layer.alert('电子邮件不能为空',5);
		return false;
	}else{
		if(!patten_email.test(email)){
			layer.alert('请输入有效电子邮件',5); 
			return false;
		}
	}
	return flag;
}
</script>
</html>
