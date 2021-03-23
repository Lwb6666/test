<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/page/common/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ include file="/WEB-INF/page/common/public.jsp"%>
<title>注册_通过手机验证账户信息_顶玺金融</title>
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
                                                 <div class="login_box form_width" ><span><font color="#22adce">*</font>手机号码：</span>
                                                   <input type="text" name="mobile" id="mobile" class="form_span" style="color:#000;"/></div>  
                                                 </div>
                                              <div class="reacquire"><span id="clock">180秒后重新获取</span><input type="button" name="sendbtn" onclick="forsendmsg()" id="sendbtn" class="head_button" value="获取验证码"/></div>
                                            <div class="loginbox_form login_top loginbox_width">
                                                   <div class="login_box form_width" ><span><font color="#22adce">*</font>短信验证码：</span>
                                                          <input type="text"  class="form_Verify" name="activeCode" maxlength="6" id="activeCode" style="color:#000;"/><em>
                                                          
                                                          </em></div>
                                                     </div>
                                             <div class="loginbox_form login_top loginbox_width">
                                                     <input type="button" class="login_btn" name="activebtn"  onclick="formobilesubmit()" id="activebtn" value="下一步"/>
                                                 </div>
                                              <div class="loginbox_form login_top loginbox_width">
                                                     <p class="login_msg"><a href="${path }/member/toCheckMemberInfo.html">您也可以使用邮箱认证>></a></p>
                                                   </div>
                                                 
                                          </div>
                                                 
           </div>
        </div>
     
     </div>
                 
                  
                 <form action="" method="post" id="mobileform">
                 </form>           
                            
                            
           
           <!--内容结束-->

    
	<div>
		<%@ include file="/WEB-INF/page/common/footer.jsp"%>
	</div>

</body>
<script type="text/javascript">
var userName = '${userName }';
var mobile =$.trim($('#mobile').val());
var activeCode =$.trim($('#activeCode').val());
/**
 * 发送验证码
 */
function forsendmsg(){
	$('#sendbtn').attr("disabled","disabled");
	//验证手机号码
	if(verify()){
		var _load = layer.load('处理中..');
		$("#mobileform").ajaxSubmit({
		    url : '${basePath}/account/approve/mobile/sendMobailActiveByMessage.html',
		    type: "POST",
		    data:{mobile:$.trim($('#mobile').val())},
		    //dataType:'json',
		    success:function(result){
		    	$('#sendbtn').removeAttr("disabled"); 
		    	layer.close(_load);
		    	if(result.code=="1"){
		    		run();  
		    		layer.alert(result.message,1);
		    	}else if(result.code == "0"){
					layer.alert(result.message,5);		    		
		    	}else {
		    		layer.alert("发送验证码异常，请稍后再试！",5);
		    	}
		    },
			error : function() {
				$('#sendbtn').removeAttr("disabled"); 
				layer.close(_load);
				layer.msg('网络连接超时，请您稍后重试', 1, 5);
		    } 
		 });
	}else {
		//$('#sendbtn').removeAttr("disabled"); 
	}
}
/**
 * 提交表单
 */
function formobilesubmit(){
	$('#activebtn').attr("disabled","disabled");
	if(verify()){
		var activeCode = $("#activeCode").val();
		if(activeCode.length==0){
			layer.alert("验证码不能为空");
			$('#activebtn').removeAttr("disabled"); 
		}else{
			var _load = layer.load('处理中..');
			$("#mobileform").ajaxSubmit({
			   url : '${basePath}/account/approve/mobile/verificationMobailActiveCode.html',
			   type: "POST",
			   data:{userName:userName,
				   activeCode:activeCode,
				   mobile:$.trim($('#mobile').val()),
				   isUpdate:'true'
			   },
			   success:function(result){
				   $('#activebtn').removeAttr("disabled"); 
				   layer.close(_load);
			    	if(result.code=="1"){
			    		window.open("${path }/account/approve/mobile/mobailCheckSuccess.html","_self");
			    	}else if(result.code == "0"){
						layer.alert(result.message,5);		    		
			    	}else {
			    		layer.alert("发送验证码异常，请稍后再试！",5);
			    	}
			    },
				error : function() {
					layer.close(_load);
					 $('#activebtn').removeAttr("disabled"); 
					layer.msg('网络连接超时，请您稍后重试', 1, 5);
				} 
			});
		}
	}else{
		$('#activebtn').removeAttr("disabled"); 
	}
}

/**
 * 刷新过期时间
 */
var runtime=180;
function run(){
	runtime=180;
	runclock();
}
function runclock(){
	$('#sendbtn').attr("disabled","disabled");
	setTimeout(function(){
		runtime = runtime-1;
		$('#clock').html(runtime);
		if(runtime>0){
			$('#clock').html(runtime+'秒后重新获取');
			$('#sendbtn').attr("disabled",false);
			runclock();
		}else{
			runtime=180;
			$('#clock').html(runtime+'秒后重新获取');
			//$('#sendbtn').attr("disabled",true);
			$('#sendbtn').removeAttr("disabled"); 
			
		}
	},1000);
}

/**
 * 验证手机号码
 */
function verify(){
	var flag = true;
	var mobileNum = $('#mobile').val();
	mobileNum = $.trim(mobileNum);
	if(mobileNum==null || mobileNum==""){
		layer.alert("请填写手机号码。",5);
		return false;
	}else{
		var patten_mobile = new RegExp(/\d{11}/);
		if(!patten_mobile.test(mobileNum)){
			layer.alert('请输入有效手机号码。',5);
			return false;
		}
	}
	return flag;
}
</script>
</html>
