<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/page/common/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ include file="/WEB-INF/page/common/public.jsp"%>
<title>邮箱验证_顶玺金融</title>
</head>


<body>
<!--头部开始-->
<%@ include file="/WEB-INF/page/common/header.jsp" %>
<!--头部结束-->
<!--我的账户左侧开始-->
<div id="Container">
<div id="Bmain">
  <div class="title">
    <span class="home"><a href="${path}/">顶玺金融</a></span>
    <span><a href="${path }/myaccount/toIndex.html">我的账户</a></span>
    <span>账户管理</span>
    <span><a href="${path }/AccountSafetyCentre/safetyIndex.html">安全中心</a></span>
    <span>邮箱验证</span>
  </div>
        <div id="menu_centert">
<%@ include file="/WEB-INF/page/account/myAccount_leftMenu.jsp"%> 
               <div class="lb_waikuang border whitebg">
                   <div class="safetoptit">安全中心</div>     
                 <div class="safe">
                 <div class="safebox450">
                 <dl>
                     <form action="" method="post" id="mailform">
                     <dd class="currentdd01">邮箱地址：<span> <input name="email" id="realemail"  class="inputtext">
                      <input class="safe_button01" type="button" name="activatebtn" id="activatebtn"
                      onclick="formailsubmit();" 
                       value="发送验证邮件" /></span></dd>
                     <dd class="currentdd02 textcenter">未收到邮件？<a id="sendEmailAgain" href="javascript:sendEmailVerifyLinkAgain();" >请重新发送邮件</a></dd>
                     <c:if test="${mobileAppro.passed == null or mobileAppro.passed!=1}">
                    <dd class="currentdd02 textcenter">手机还未认证？<a style="color:#00a7e5;" href="${path }/account/approve/mobile/mobileforinsert.html">去认证</a></dd>
                    </c:if>
                    </form>
                     
                 </dl>
                
                 <div class="gg_btn"></div>
                 </div>
                 </div>

                 
                
                 
          
 
</div>
     
<!--我的账户右侧开始 -->             
               
</div>
</div>
</div>
<%@ include file="/WEB-INF/page/common/footer.jsp" %>
</body>
<script type="text/javascript">
var _load;
$(document).ready(function(){
	var checked = '${emailApproVo.checked}';
	var email = '${emailApproVo.email}';
	if(checked!=1){
		$('#activatebtnmsg').html("您的邮箱还没通过认证：");
	}else{
		$('#activatebtnmsg').html("您的邮箱已经通过认证：");
	}
	if(checked == '1'){
		$('#emaildiv').hide();
		if(email != 'undefined' && email != ''){
			var str1 = email.substr(0,2);
			var index = email.indexOf("@");
			var str3 = email.substring(index);
			var str2 = "";
			for(var i=0;i< index-2;i++){
				if(i == index){
					break;
				}
				str2 = str2 + "*";
			}
			$("#activatebtnmail").html(str1+str2+str3);
		};
	}else{
		$('#emaildiv').show();
		if(email != 'undefined' && email != ''){
			$("#realemail").val(email);
		}
	}
});
/**
 * 验证邮箱
 */
function verify(){
	var flag = true;
	//邮箱
	var patten_email = regexEnum.email;
	var email = $.trim($('#realemail').val());
	$('#realemail').val(email);
	if(email==null || email==""){
		layer.alert("请填写电子邮件。");
		return false;
	}else{
		if(!patten_email.test(email)){
			layer.alert("请输入有效电子邮件。");
			return false;
		}
	}
	//验证邮箱
	$.ajax({
		url : '${basePath}/member/checkMemberRepeat.html',
		data : {email:email},
		type : 'post',
		async: false, 
		dataType : 'text',
		success : function(data){
			if(data != null && data != 'success'){
				layer.alert('电子邮箱已经存在');
				flag=false;
			}
		},
		error : function(data) {
			flag=false;
			layer.msg('网络连接异常，请刷新页面或稍后重试！', 1, 5);
	    }
	});
	return flag;
}
/**
 * 重新发送邮箱验证链接
 */
function sendEmailVerifyLinkAgain(){
	 $('#sendEmailAgain').attr("disabled","disabled");
	 _load = layer.load('处理中..');
	$.ajax({
		url : '${basePath }/account/approve/sendEmailVerifyLinkAgain.html',
		type : 'post',
		data : {again:'true'},
		success : function(result) {
			$('#sendEmailAgain').removeAttr("disabled");
			layer.close(_load);
			if (result.code=="1") {
	    		window.open("${path }/account/approve/safeCenterEmailVerifySuccess.html","_self");
	    		$('#emaildiv').hide();
			} else if(result.code == "0"){
				layer.msg(result.message, 1, 5);
			}else{
				layer.msg("发送邮件异常，请稍后再试！", 1, 5);
			}
		},
		error : function(result) {
			$('#sendEmailAgain').removeAttr("disabled");
			layer.close(_load);
			layer.msg("操作异常,请刷新页面或稍后重试！");
	    }
	});
}
function formailsubmit(){
	$('#activatebtn').attr("disabled","disabled");
	if(verify()){
		//提交
		  _load = layer.load('处理中..');
		$("#mailform").ajaxSubmit({
		    url : '${basePath}/account/approve/emailVerify.html',
		    type: "POST",
		    success:function(result){
		    	layer.close(_load);
		    	if(result.code=='1'){
		    		$('#activatebtn').removeAttr("disabled");
		    		window.open("${path }/account/approve/safeCenterEmailVerifySuccess.html","_self");
		    		$('#emaildiv').hide();
		    	}else if(result.code=='0'){
		    		$('#activatebtn').removeAttr("disabled");
		    		layer.alert(result.message);
		    	}else {
		    		$('#activatebtn').removeAttr("disabled");
		    		layer.msg('邮件发送异常，请您稍后重试', 1, 5);
		    	}
		    },
			error : function() {
				layer.close(_load);
				$('#activatebtn').removeAttr("disabled");
				layer.msg('网络连接超时，请您稍后重试', 1, 5);
		    } 
		 });
	}else{
		$('#activatebtn').removeAttr("disabled");
	}
}

</script>
</html>
