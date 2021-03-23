<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/page/common/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ include file="/WEB-INF/page/common/public.jsp"%>
<title>网站账户</title>
</head>
<body style="overflow-y:hidden;background:#fff;">
<div class="sf">
	<div style="width:500px;height:300px;">
	  <p style="font-size:16px; text-align:center;  padding:10px 0; font-weight:700;"><b>关联人</b></p>
	  <dl>
	      <dd><span><font color="red">*</font>姓名 ：</span> <input type="text" name="name" id="name" value="${accountLinkman.name }" style="width:200px"/></dd>
	      <dd><span><font color="red">*</font>手机 ：</span> <input type="text" name="mobile" id="mobile" value="${accountLinkman.mobile }" style="width:200px" /></dd>
	      <dd><span><font color="red">*</font>邮箱 ：</span> <input type="text" name="email" id="email" value="${accountLinkman.email }" style="width:200px" /></dd>
	      <dd><span><font color="red">*</font>关系 ：</span> <input type="text" name="relationship" id="relationship" value="${accountLinkman.relationship }" style="width:200px" /></dd>
	      <dd><span><font color="red">*</font>验证码 ：</span> <input type="text" name="checkCode" id="checkCode" style="width:100px" />&nbsp;&nbsp;<a href="javascript:loadimage();"><img name="randImage" id="randImage" src="${basePath}/random.jsp" border="0" align="middle" /></a></dd>
	  </dl>
	  <div class="gg_btn"><input type="button" value="提交" onclick="save_linkman()" style="cursor:pointer;"/></div>              
	</div>
</div>
</body>
<script type="text/javascript">
$(function() {
	loadimage();
});
function loadimage() {
	var random = Math.random();
	document.getElementById("randImage").src = "${basePath}/random.jsp?" + random;
}
function validateData(){
	var emailReg = /\w+([-+.]\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*/;
	var mobileReg = /^1[3|5|7|8|][0-9]{9}$/;
	var name = $("#name").val();
	var mobile = $("#mobile").val();
	var email = $("#email").val();
	var relationship = $("#relationship").val();
	var checkCode = $("#checkCode").val();
	if(name.length==0){
		layer.msg("姓名未填写！", 2, 5);
		return false;
	}else if(name.length > 50){
		layer.msg("姓名长度不能超过50个字符！", 2, 5);
		return false;
	}
	
	if(mobile.length==0){
		layer.msg("手机未填写！", 2, 5);
		return false;
	}else{
		if(!mobileReg.test(mobile)){
			layer.msg("手机号码输入不正确！", 2, 5);
			return false;
		}
		if(mobile.length>50){
			layer.msg("手机长度不能超过50个字符！", 2, 5);
			return false;
		}
	}
	
	if(email.length==0){
		layer.msg("邮箱未填写！", 2, 5);
		return false;
	}else{
		if(!emailReg.test(email)){
			layer.msg("邮箱输入不正确！", 2, 5);
			return false;
		}
		if(emailReg.length > 100){
			layer.msg("邮箱长度不能超过100个字符！", 2, 5);
			return false;
		}
	}
	
	if(relationship.length==0){
		layer.msg("关系未填写！", 2, 5);
		return false;
	}else if(relationship.length > 50){
		layer.msg("关系长度不能超过50个字符！", 2, 5);
		return false;
	}
	if(checkCode==null || checkCode==""){
		layer.msg("验证码未填写！", 2, 5);
		return false;
	}
	return true;
}
//投标
function save_linkman(){
	$("#save_linkman").attr("href","#");
	if(validateData()){
		if(layer.confirm("确定要提交吗？",function(){
			var name = $("#name").val();
			var mobile = $("#mobile").val();
			var email = $("#email").val();
			var relationship = $("#relationship").val();
			var checkCode = $("#checkCode").val();
			$.post("${basePath}/myaccount/accoutlinkman/saveOrUpdateLinkman.html",{
				name:name,mobile:mobile,email:email,relationship:relationship,checkCode:checkCode
			},function(data){
				if(data=="OK,Rocky.J!"){
					layer.msg("提交成功！", 2, 1);
				}else{
					layer.msg("验证码输入错误！", 2, 5);
					$("#save_linkman").attr("href","javascript:save_linkman();");
					var random = Math.random();
					document.getElementById("randImage").src = "${basePath}/random.jsp?" + random;
				}
			});
		}));
		 
	}else{
		$("#save_linkman").attr("href","javascript:save_linkman();");
	}	
}
</script>
</html>
