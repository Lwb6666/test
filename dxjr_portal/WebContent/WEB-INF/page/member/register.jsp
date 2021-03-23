<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/page/common/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ include file="/WEB-INF/page/common/public.jsp"%>
<title>注册_填写账户信息_顶玺金融</title>
<link href="${basePath}/css/login.css" rel="stylesheet" type="text/css" />
</head>

<body>
	<!--头部开始-->
	<%@ include file="/WEB-INF/page/common/header.jsp"%>
	<!--头部结束-->
	
	<!--内容开始-->
     <div id="Bmain">
      
       <div class="login_main loginbg loginboxm">
       			<div class="login-r-bg"></div>
                   <div class="reg-steps">
                    <ol class="reg-step-1">
                        <li class="step-1"><span class="txt"><i class="cz_icons  dot1">1</i>填写账户信息</span></li>
                        <li class="step-2"><span class="txt"><i class="cz_icons  dot1">2</i>验证账户信息</span></li>
                        <li class="step-3"><span class="txt"><i class="cz_icons  dot1">3</i>注册成功</span></li>
                    </ol>
                </div>

			<div class="reg-info">
				已有账号，<a href="${path }/member/toLoginPage.html">立即登录</a>
			</div>
			 <form action="${basePath }/member/register.html" name="register_form"  id="register_form" method="post">
			 <input type="password" style="display: none;"/>
			<div class="right_container">
				<div class="right_content">
					<div class="loginbox_form loginbox_width">
						<div class="login_box form_width">
							<span><font color="#22adce">*</font>用户名：</span><input type="text"
								id="username" name="username" maxlength="16" value="${username}" 
								onblur="return checkMemberUserName();" 
								class="form_span" style="color:#000;"/>
						</div>
					</div>
					<div class="loginbox_form login_top loginbox_width">
						<div class="login_box form_width">
							<span><font color="#22adce">*</font>登录密码：</span><input
								type="password" autocomplete="off" id="logpassword" name="logpassword"
								maxlength="20" class="formbox"  onblur="return checkPassWord();" style="color:#000;"/>
						</div>
					</div>
					<div class="loginbox_form login_top loginbox_width">
						<div class="login_box form_width">
							<span><font color="#22adce">*</font>确认密码：</span> <input
								type="password" autocomplete="off" id="password2" name="password2" class="formbox" maxlength="20" onblur="return checkPassWordAgain();" style="color:#000;"/>
						</div>
					</div>
					<div class="loginbox_form login_top loginbox_width">
						<div class="login_box form_width">
							<span>推荐人：</span> <input
								type="text" id="inviterName" name="inviterName"
								value="${inviterName}" onblur="forbidBlank(this)" placeholder="用户名" class="formID" style="color:#000;"/>
						</div>
					</div>
					<div class="loginbox_form login_top loginbox_width">
						<div class="login_box form_width">
					<span><font color="#22adce">*</font>用户类型：</span> <em><input
								type="radio" value="1" name="isFinancialUser" checked="checked" />理财用户&nbsp;&nbsp;&nbsp;<input
								type="radio" value="0" name="isFinancialUser" />借款用户</em>
						</div>
					</div>
					<div class="loginbox_form login_top loginbox_width">
						<div class="login_box form_width">
							<span><font color="#22adce">*</font>验证码：</span> <input
								type="text" id="validatecode" name="validatecode" maxlength="4"
								class="form_Verify" style="color:#000;"/><em> <a
								href="javascript:loadimage();"><img name="randImage"
									id="randImage" src="${basePath}/random.jsp" width="91"
									height="30" /></a></em>
						</div>
					</div>
					<div class="loginbox_form login_top loginbox_width">
						<p class="login_msg">
							<input type="checkbox" id="xiyi" /><a href="javascript:agree()">同意</a>
							<a href="javascript:toBorrowXiyi();"
								title="顶玺金融网站使用条款">《顶玺金融使用条例》</a>
						</p>

					</div>
					<div class="loginbox_form login_top loginbox_width">
						<input onclick="javascript:goto_register();" type="button" name="Submit1" id="subbtn"
							class="login_btn" value="下一步"  style="cursor:pointer;"/>
					</div>

				</div>
			</div>
			</form>
		</div>
	</div>
	<!--内容结束-->
		<%@ include file="/WEB-INF/page/common/footer.jsp"%>

</body>
<script type="text/javascript">
var result = '${result }';
var inviterName = '${inviterName }';
$(function(){
	if(inviterName != null && ""!=inviterName){
		$("#inviterName").attr("disabled", "disabled");
	}
});
var canSubmit = true;
function checkPassWordAgain(){
	//确认密码
	var password1 = $('#logpassword').val();
	var password2 = $('#password2').val();
	if(password2==null || password2==""){
		layer.tips('确认登录密码不能为空', $('#password2'),{guide: 1, time: 3,style: ['background-color:#78BA32; color:#fff', '#78BA32'],closeBtn:[0, true]} );
		canSubmit= false;
		return false;
	}else{
		if(password2!=password1){
			layer.tips('2次登录密码输入不一致', $('#password2'),{guide: 1, time: 3,style: ['background-color:#78BA32; color:#fff', '#78BA32'],closeBtn:[0, true]} );
			canSubmit= false;
			return false;
		}
	}
}
/**
 * 校验密码
 */
function checkPassWord(){
	var password1 = $('#logpassword').val();
	if(password1==null || password1==""){
		layer.tips('登录密码不能为空', $('#logpassword'),{guide: 1, time: 3,style: ['background-color:#78BA32; color:#fff', '#78BA32'],closeBtn:[0, true]} );
		canSubmit= false;
		return false;
	}else if(password1.length<6){
		layer.tips('登录密码必须大于或等于6位', $('#logpassword'),{guide: 1, time: 3,style: ['background-color:#78BA32; color:#fff', '#78BA32'],closeBtn:[0, true]} );
		canSubmit= false;
		return false;
	}else if(password1.length>20){
		layer.tips('登录密码不能大于20位', $('#logpassword'),{guide: 1, time: 3,style: ['background-color:#78BA32; color:#fff', '#78BA32'],closeBtn:[0, true]} );
		canSubmit= false;
		return false;
	}else{
		var regx = new RegExp("^([0-9]+[A-Za-z]+|[A-Za-z]+[0-9]+)[A-Za-z0-9]*$");
		if(!regx.test(password1)){
			layer.tips('登录密码必须为数字加字母', $('#logpassword'),{guide: 1, time: 3,style: ['background-color:#78BA32; color:#fff', '#78BA32'],closeBtn:[0, true]} );
			canSubmit= false;
			return false;
	    }
	}
	
}
/**
 * 校验注册的用户名
 */
function checkMemberNameExist(){
	var username = $.trim($('#username').val());
	if(username.length>=2 && username.length<=16){
		//验证用户名是否存在
		$.post("${basePath}/member/checkMemberRepeatForRegist.html", 
				{username:username}, 
				function(data) {
				if(data != null && data.code == 1){
				}else{
					layer.tips(data.message, $('#username'),{guide: 1, time: 3,style: ['background-color:#78BA32; color:#fff', '#78BA32'],closeBtn:[0, true]} );
					canSubmit=false;
				}
			}
		);
	}else{
		layer.tips('用户名长度应该位于2~16位之间！', $('#username'),{guide: 1, time: 3,style: ['background-color:#78BA32; color:#fff', '#78BA32'],closeBtn:[0, true]} );
		canSubmit= false;
	}
	if(username==null || username==""){
		 layer.tips('用户名不能为空', $('#username'),{guide: 1, time: 3,style: ['background-color:#78BA32; color:#fff', '#78BA32'],closeBtn:[0, true]} );
		 canSubmit= false;
		 return false;
	}
	
	//用户名不能包含非法字符@
	if(username.indexOf('@')!=-1){
		layer.tips('用户名不能包含非法字符@', $('#username'),{guide: 1, time: 3,style: ['background-color:#78BA32; color:#fff', '#78BA32'],closeBtn:[0, true]} );
		canSubmit= false;
		return false;
	}
	
	//不能包含非法字符 
	if(haveInvalidChars(username)){
		layer.tips('用户名不能包含非法字符', $('#username'),{guide: 1, time: 3,style: ['background-color:#78BA32; color:#fff', '#78BA32'],closeBtn:[0, true]} );
		canSubmit= false;
		return false;
	}
	
	//用户名不能全为数字
	var patten_intege1 = regexEnum.intege1;
	if(patten_intege1.test(username)){
		layer.tips('用户名不能全为数字', $('#username'),{guide: 1, time: 3,style: ['background-color:#78BA32; color:#fff', '#78BA32'],closeBtn:[0, true]} );
		canSubmit= false;
		return false;
	}
}


/*  */

function checkMemberUserName(){
	 var  constainSensitive=checkMemberContainSensitive();
	if (constainSensitive) {
		if (checkMemberNameExist()) {
			return  true;
		}
	}
	return false;
}



 
function checkMemberContainSensitive(){
	 var username = $.trim($('#username').val());
	 var  result=false;
	 $.ajax({ 
         type : "post", 
         url : "${basePath}/member/checkMemberContainSensitiveForRegist.html", 
         data : {username:username}, 
         async : false, 
         success : function(data){ 
        	 if(data != null && data.code == 1){
        		 result=true;
 			}else{
 				layer.tips(data.message, $('#username'),{guide: 1, time: 3,style: ['background-color:#78BA32; color:#fff', '#78BA32'],closeBtn:[0, true]} );
 				 result=false;
 			}
        	 
           } 
         }); 
	  return  result;
	
}





/**
 * 同意
 */
function agree(){
	 
	var ischeck =$("#xiyi").is(":checked");
	if(ischeck){
		$("#xiyi").attr("checked",false);
	}else{
		$("#xiyi").attr("checked",true);
	}
	
}

function toBorrowXiyi() {
	//查看借款协议 ${path }/member/toXiyi.html?keepThis=true&TB_iframe=true&amp;height=500&width=450
	$.layer({
		type : 2,
		fix : false,
		shade : [ 0.6, '#E3E3E3', true ],
		shadeClose : true,
		border : [ 10, 0.7, '#272822', true ],
		title : [ '', false ],
		offset : [ '50px', '' ],
		area : [ '500px', '450px' ],
		iframe : {
			src : '${basePath }/member/toXiyi.html'
		},
		close : function(index) {
			layer.close(index);
		}
	});
}

//同步方法
$.ajaxSetup({ 
    async : false 
}); 
$(document).ready(function(){
	//回车事件
	document.onkeydown = function(e){ 
		var ev = document.all ? window.event : e; 
		if(ev.keyCode==13) {
			goto_register();
		}
	}
});
/**
 * 注册方法
 */
function goto_register(){
	
	//验证数据
	 if(!checkData()){
		return;
	} 
	    //禁用链接
	    //$("#subbtn").removeAttr("href");
	    $('#subbtn').attr("disabled","disabled");
	    var _load = layer.load('处理中..');
		$("#register_form").ajaxSubmit({
			url : '${basePath }/member/registMemberInfoCollect.html',
			data:{inviterName:'${inviterName }'},
			type : 'post',
			dataType:'json',
			success : function(result) {
				loadimage();
				if(result.code=="1"){
					//layer.alert("注册成功！赶紧去邮箱进行邮箱认证吧！",8);
					if(layer.confirm("注册成功！赶紧去进行认证吧！",function(){
						//启用链接
						$('#subbtn').removeAttr("disabled");
						layer.close(_load);
						window.open("${path }/member/toCheckMemberInfo.html","_self");
					}));
				}else if(result.code=="2"){
					 $.layer({
						    shade: [0],
						    area: ['auto','auto'],
						  /*   closeBtn: [1, false], */
						    title: '温馨提示',
						    fadeIn: 800,
						    dialog: {
						        msg: result.message,
						        btns: 1,                    
						        type: 7,
						        btn: ['确定'],
						        yes: function(){
						        	window.open("${path }/home/index.html","_self");
						        }
						    },
					        end: function(){
					        	window.open("${path }/home/index.html","_self");
					        }
						});
					 
				}else{
					 layer.msg(result.message, 1, 5);
					 $('#subbtn').removeAttr("disabled");
					 layer.close(_load);
				}
			},
			error : function(result) {
				 loadimage();
				 layer.msg("网络连接异常，请刷新后重试", 1, 5);
				 $('#subbtn').removeAttr("disabled");
				 layer.close(_load);
		    }
		});
	}
/**
 * 刷新验证码 
 */
function loadimage() {
	document.getElementById("randImage").src = "${basePath}/random.jsp?" + Math.random();
}
/**
 * 验证数据
 */
function checkData(){
	var flag = true;
	//隐藏验证信息
/* 	$(".showTip").hide();
	$(".tishi").show();
	$(".showTipText").each(function(index,obj){
		$(obj).html('');
	}); */
	//用户名
	var username = $.trim($('#username').val());
	$('#username').val(username);
	if(username==null || username==""){
		/* $("#userName_tishi").hide();
		$('#userNameTipText').html('用户名不能为空');
		$('#userNameTip').show();  */
		 layer.alert('用户名不能为空',5);
		return false;
	}
	
	//用户名不能包含非法字符@
	if(username.indexOf('@')!=-1){
		/* $("#userName_tishi").hide();
		$('#userNameTipText').html('用户名不能包含非法字符@');
		$('#userNameTip').show();  */
		layer.alert('用户名不能包含非法字符@',5);
		return false;
	}
	
	//不能包含非法字符 
	if(haveInvalidChars(username)){
		/* $("#userName_tishi").hide();
		$('#userNameTipText').html('用户名不能包含非法字符');
		$('#userNameTip').show(); */ 
		layer.alert('用户名不能包含非法字符',5);
		return false;
	}
	
	//用户名不能全为数字
	var patten_intege1 = regexEnum.intege1;
	if(patten_intege1.test(username)){
		/* $("#userName_tishi").hide();
		$('#userNameTipText').html('用户名不能全为数字');
		$('#userNameTip').show(); */ 
		layer.alert('用户名不能全为数字',5);
		return false;
	}
	
	//邮箱
	/* var patten_email = regexEnum.email;
	var email = $.trim($('#email').val());
	$('#email').val(email);
	if(email==null || email==""){
		$("#email_tishi").hide();
		$('#emailTipText').html('电子邮件不能为空');
		$('#emailTip').show(); 
		return false;
	}else{
		if(!patten_email.test(email)){
			$("#email_tishi").hide();
			$('#emailTipText').html('请输入有效电子邮件');
			$('#emailTip').show(); 
			return false;
		}
	} */
	//密码
	var password1 = $('#logpassword').val();
	if(password1==null || password1==""){
		/* $("#password1_tishi").hide();
		$('#password1TipText').html('登录密码不能为空');
		$('#password1Tip').show();  */
		layer.alert('登录密码不能为空',5);
		return false;
	}else if(password1.length<6){
		/* $("#password1_tishi").hide();
		$('#password1TipText').html('登录密码必须大于或等于6位');
		$('#password1Tip').show();  */
		layer.alert('登录密码必须大于或等于6位',5);
		return false;
	}else if(password1.length>20){
		/* $("#password1_tishi").hide();
		$('#password1TipText').html('登录密码不能大于12位');
		$('#password1Tip').show(); */
		layer.alert('登录密码不能大于20位',5);
		return false;
	}else{
		var regx = new RegExp("^([0-9]+[A-Za-z]+|[A-Za-z]+[0-9]+)[A-Za-z0-9]*$");
		if(!regx.test(password1)){
			/* $("#password1_tishi").hide();
			$('#password1TipText').html('登录密码必须为数字加字母');
			$('#password1Tip').show(); */ 
			layer.alert('登录密码必须为数字加字母',5);
			return false;
	    }
	}
	//确认密码
	var password2 = $('#password2').val();
	if(password2==null || password2==""){
		/* $('#password2TipText').html('确认登录密码不能为空');
		$('#password2Tip').show();  */
		layer.alert('确认登录密码不能为空',5);
		return false;
	}else{
		if(password2!=password1){
			/* $('#password2TipText').html('2次登录密码输入不一致');
			$('#password2Tip').show(); */
			layer.alert('2次登录密码输入不一致',5);
			return false;
		}
	}
	/* //qq
	var qq = $.trim($('#qq').val());
	if(qq.length>0){
		var regx = regexEnum.qq;
		if(!regx.test(qq)){
			$('#qqTipText').html('QQ号码格式不正确');
			$('#qqTip').show(); 
			return false;
	    }
	} */
	//验证码
	var validatecode = $('#validatecode').val();
	if(validatecode==null || validatecode==""){
		/* $('#validatecodeTipText').html('验证码不能为空');
		$('#validatecodeTip').show(); */
		layer.alert('验证码不能为空',5);
		return false;
	}
	//是否阅读
	if(!$("#xiyi").is(":checked")){
		/* $('#xiyiTipText').html('请选择已阅读条例');
		$('#xiyiTip').attr("style", "float:right;margin-top:-8px;"); */
		layer.alert('请先阅读条例并选择已阅读条例',5);
		return false;
	}
	//验证用户名是否存在
	$.post("${basePath}/member/checkMemberRepeatForRegist.html", 
			{username:username}, function(data) {
			if(data != null && data.code != 1){
				/* $("#userName_tishi").hide();
				$('#userNameTipText').html('用户名已经存在');
				$('#userNameTip').show();  */
				layer.alert('用户名已经存在',5);
				flag=false;
			}
		}
	);
	//验证用户名包含敏感词
	$.post("${basePath}/member/checkMemberContainSensitiveForRegist.html", 
			{username:username}, function(data) {
			if(data != null && data.code != 1){
				layer.alert(data.message,5);
				flag=false;
			}
		}
	);
	
	
	
	if(!flag){return false;}
/* 	//验证邮箱
	$.post("${basePath}/member/checkMemberRepeat.html", {email:email},
			 function(data) {
				if(data != null && data != 'success'){
					$("#email_tishi").hide();
					$('#emailTipText').html('电子邮箱已经存在');
					$('#emailTip').show(); 
					flag=false;
				}
			}
	);
	if(!flag){return false;} */
	//验证信用人
	var inviterName = $.trim($("#inviterName").val());
	if(inviterName.length>0){
		$.post("${basePath}/member/isInviterNameExist.html", {inviterName:inviterName}, 
				function(data) {
					if(data != null && data.code != 1){
						/* $('#inviterNameTipText').html('信用人用户名不存在');
						$('#inviterNameTip').show();  */
						layer.alert('推荐人用户名不存在',5);
						flag=false;
					}
		});
	}
	if(!flag){return false;}

	return flag;
}
/**
 * 非法字符
 */
function haveInvalidChars(inString) {
	var result = false;
	var invalidChars=new Array("<","%","\"",">","~","&","?","'","\\","-","#","!");
	for(var i=0;i<invalidChars.length;i++){
		if(inString.indexOf(invalidChars[i])!=-1){
			result = true;
		}
	}
	return result;
} 
</script>
</html>