<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/page/common/taglib.jsp"%>
<%@ include file="/WEB-INF/page/common/public.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>注册_填写账户信息_顶玺金融</title>
<link href="${basePath}/css/login.css" rel="stylesheet" type="text/css" />
<style type="text/css">
.msgs1{
background:#00ade9 none repeat scroll 0 0;display:block; height:40px; line-height:40px; width:130px; border-radius:3px; margin-left:15px; float:left; text-align:center; color:#fff;  }
A:hover {
 COLOR: #ff7f24;
}
</style>
<!-- ZZOrderCode 珍岛DSP的统计代码
	/*
	* _zzot['zzsiteId'], _zzot['zzId']用默认值,不能改动
	* 用户名,用户id,其他跟踪参数尽量赋值,如果没有,可留空,如:
	* _zzot['userName'] = "";
	*/
-->
<script type="text/javascript">
var _zzot = [];
_zzot['zzsiteId'] = "hiGAA0ZMbRL";
_zzot['zzId'] = "hiGAA0ZMbRK";

_zzot['userName'] = "myName";      //用户名
_zzot['userId'] = "myid";          //用户id
_zzot['transExt'] = "otherParams"; //其他跟踪参数
(function() {
  var zz = document.createElement('script');
  zz.type = 'text/javascript';
  zz.async = true;
  zz.src = 'https:' == document.location.protocol ? 'https://ssl.tj.71360.com/api/order_v2.js' : 'http://tj.71360.com/trace/api/order_v2.js';
  var s = document.getElementsByTagName('script')[0];
  s.parentNode.insertBefore(zz, s);
})();
</script>
<!-- end ZZOrderCode -->
</head>

<body>
	<!--头部开始-->
	<%@ include file="/WEB-INF/page/common/header.jsp"%>
	<!--头部结束-->
	
	<!--内容开始-->
	<div id="Container" style="background-color:#fff;"  >
     <div id="Bmain" class="userRegiste" >
 
<div class="pcregisterd" style=" border:none;" >
    <div class="titlewinows  titlewinows1" style="width:100%;" >
        <div class="pc-l pc-l1"   >注册账户</div>
        <div class="pc-r pc-r1" style="">已有账号<a href="${basePath}/member/toLoginPage.html">立即登录</a></div>
    </div>   
    <!----------注册内容--------------------> 
   <div class="res-boxcont">
       <!----------注册内容——左--------------------> 
       <form  name="register_form"  id="register_form" method="post">
    		<div class="res-l" style="padding:40px 0px;  overflow:hidden;">
    		<input type="hidden" name="redId" id="redId" value="${redId}"/>
        	<ul style=" float:left;" style="border:1px red solid;">
            	<li class="gcdw-yes">
                <span class="res-text">用户名：</span>
                <span>
                <input class="res-input1"  onfocus="viewStyle('userTip','2~20位字母、数字或汉字，不能全为数字',3);"  type="text"  id="username" name="username" maxlength="20" value="${username}" 
								onblur="return checkMemberNameExist();"/></span>
                <span class="gcyes-icon" style="display:none; position: absolute; top:-3px; left:348px; " id="userTips"><img src="${basePath}/images/yes.png" /></span>
                <span class="tishitext"  style="display:none;position: absolute;top:0px;left:382px; " id="userTip"> </span>
                </li>
            	<li class="gcdw-yes"><span class="res-text">手机号码：</span><span>
            	<input class="res-input2" onfocus="viewStyle('phoneTip','请输入真实有效的手机号',3);"    type="text" id="mobileNum" name="mobileNum" maxlength="11" onblur="return checkMobileNum();"/></span>               
            	<span class="gcyes-icon" style="display:none; position: absolute; top:-3px; left:348px; " id="phoneTips"><img src="${basePath}/images/yes.png" /></span>
                <span class="tishitext" style="display:none;position: absolute;top:0px;left:382px; " id="phoneTip"> </span>
                </li>
            	<li class="gcdw-yes"><span class="res-text">登录密码：</span><span>
            	<input class="res-input3" onfocus="viewStyle('logpasswordTip','6~20位字母与数字组合',3);"   type="password" autocomplete="off" id="logpassword" name="logpassword"
								maxlength="20"    onblur="return checkPassWord();"/></span>
				<span class="gcyes-icon" style="display:none; position: absolute; top:-3px; left:348px; " id="logpasswordTips"><img src="${basePath}/images/yes.png" /></span>
                <span class="tishitext"  style="display:none;position: absolute;top:0px;left:382px; " id="logpasswordTip"> </span>
				</li>
            	<li class="gcdw-yes"><span class="res-text">确认密码：</span><span>
            	<input class="res-input4" type="password" onfocus="viewStyle('password2Tip','再输入一次密码',3);"    autocomplete="off" id="password2" 
            	       name="password2" maxlength="20" onblur="return checkPassWordAgain();" /></span>
            	<span class="gcyes-icon" style="display:none; position: absolute; top:-3px; left:348px; " id="password2Tips"><img src="${basePath}/images/yes.png" /></span>
                <span class="tishitext"  style="display:none;position: absolute;top:0px;left:382px; " id="password2Tip"> </span>
                </li>
            	<li class="gcdw-yes" style="    position: relative;  " ><span class="res-text">验证码：</span><span style=" width:31%; ">
            	<input class="res-input5" type="text"  style="width:90%;"  id="validatecode" name="validatecode" maxlength="4"  type="text" /></span>
            	<em> <a href="javascript:loadimage();">
            	<img name="randImage" style=" top:0px;right:318px; height:40px;    position: absolute;" id="randImage" src="${basePath}/random.jsp" width="91" height="30" /></a></em></li>
            	<li class="gcdw-yes"><span class="res-text">推荐人：</span><span>
            	<input class="res-input6" id="inviterName" maxlength="16" name="inviterName"
								value="${realName==null?inviterName:realName}" onblur="forbidBlank(this)" placeholder="推荐人手机号/用户名（选填）"   type="text" /></span>   </li>
            	<input type="hidden" value="${inviterName}" id="inviterName1"/>
            	<li  class="gcdw-yes"style=" padding-left:85px; "><span  ><input style=" border:none;float:left; width:20px;" id="xiyi" type="checkbox" checked="checked"/></span><span style="float:left; line-height:40px;">已阅读并同意
            	 
            	<a href="javascript:toBorrowXiyi();" class="sy_tk" 
								title="顶玺金融网站使用条款">《顶玺金融使用条例》</a>
            	 </span></li>
            	<li style="padding-left:84px;  "><input style="padding:0; margin-top:40px;width:278px;  border:none;" id="subbtn" onclick="javascript:next();" type="button" class="zcbtn" value="立即注册" /> </li>
            </ul>        
        </div>
         <input type="hidden" name="activeCode" id="mobileCodes">
        </form>
  
        
   </div>   
       <!----------注册内容-------------------->
</div>     
</div>

</div>
<!--验证内容开始-->

<div id="Bmain" class="userVerify" style="display:none;">
 <div class="pcregisterd" style=" border:none;" >
    <div class="titlewinows  titlewinows1" style="width:100%;" >
        <div class="pc-l pc-l1"   >注册账户</div>
        <div class="pc-r pc-r1" style="">已有账号<a href="${basePath}/member/toLoginPage.html">立即登录</a></div>
    </div>   
    <!----------注册内容--------------------> 
   <div class="res-boxcont">
       <!----------注册内容——左--------------------> 
    		<div class="res-l" style="padding:40px 0px;  overflow:hidden;">
        	<ul style=" float:left; margin-left:40px;">
  
                <li class="gcdxts" id="tips" style=" height:40px; font-size:16px; padding-top:60px;">短信验证码已发送到您的手机，请注意查收！</li>
           		<li style="padding-top:50px;" ><span class="fanhuixg" style=" font-size:14px;color:#666;">手机号码：</span><span class="telyz"></span>
           		<a href="javascript:void();" onclick="back();" class="fanhuixg">返回修改</a></li>
            	<li   style="padding-top:20px;">
                    <span class="res-text">短信验证码：</span>
                    <span style=" width:31%; "><input class="res-input5" maxlength="6" id="mobileCode" style="width:100%;" type="text" /></span>
                    <div  class="gcbtnyz" id="dyMobileButton" style="cursor:pointer"></div>
                </li>
             	<li style="padding:20px 0 50px 84px;  "><input style="padding:0; margin-top:40px;width:278px; border:none;" id="tubbtn" type="button" class="zcbtn" onclick="javascript:goto_register();" value="完成注册" /> </li>
            </ul>  
                  
        </div>
        
  
        
   </div>   
       <!----------注册内容--------------------> 

   
     
</div>
  
       
</div>
	<!--验证内容结束-->
	<!--内容结束-->
		<%@ include file="/WEB-INF/page/common/footer.jsp"%>

</body>

<script type="text/javascript">
var result = '${result}';
var inviterName = '${inviterName}';
var realName='${realName}';
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
		viewStyle("password2Tip","确认密码不能为空",2); 
		canSubmit= false;
		return false;
	}else{
		if(password2!=password1){
			viewStyle("password2Tip","两次登录密码输入不一致",2);
		 	canSubmit= false;
			return false;
		}else{
			viewStyle("password2Tip","",1);
		}
	} 
}
/* 验证手机是否唯一 */
function checkMobileNum(){
	
	var flag=false;
	
	var mobileNum = $('#mobileNum').val();

	var mobileReg = /^0?(13|15|14|18|17)[0-9]{9}$/;
	if(mobileNum==null || mobileNum==""){
		viewStyle("phoneTip","手机号码不能为空！",2);
		return false;
	}else if(!mobileReg.test(mobileNum)){
		viewStyle("phoneTip","手机号码输入不正确！",2);
		return false;
	}
	$.post("${basePath}/member/isMobileNumExist.html", 
			{mobileNum:mobileNum}, 
			function(data) {
				 
			if(data != null && data.code == 1){
				viewStyle("phoneTip","",1);
				flag=true;
			}else if(data != null && data.code == 2){
				viewStyle("phoneTip",data.message,2);
				flag=false;
			}else{
				viewStyle("phoneTip",data.message,2);
				flag=false;
			}
		}
	); 
	return flag;
}
/**
 * 校验密码
 */
function checkPassWord(){
	var password1 = $('#logpassword').val();
	if(password1==null || password1==""){
		viewStyle("logpasswordTip","登录密码不能为空",2);
		canSubmit= false;
		return false;
	}else if(password1.length<6||password1.length>20){
		viewStyle("logpasswordTip","登录密码必须在6-20位",2);
	 	canSubmit= false;
		return false;
	}else{
		var regx = new RegExp("^([0-9]+[A-Za-z]+|[A-Za-z]+[0-9]+)[A-Za-z0-9]*$");
		if(!regx.test(password1)){
			viewStyle("logpasswordTip","登录密码必须为数字加字母",2);
		    canSubmit= false;
			return false;
	    }else{
	    	viewStyle("logpasswordTip","",1);
	    }
	}
	
}
//显示样式函数
function viewStyle(type,text,flag){
	 
	if(flag==1){//输入正确
		$('#'+type).css("display","none");
		$('#'+type+"s").css("display","block");
	}else if (flag==2){//输入用户名重复，用户名不规则
		$('#'+type).css("display","block");
		$('#'+type+"s").css("display","none");
		$('#'+type).addClass("textno");
		$('#'+type).html(text);
		setTimeout(function(){
			$('#'+type).css("display","none");
		},3000)
	}else if(flag==3){//提示
		$('#'+type).removeClass("textno");
		$('#'+type).css("display","block");
		$('#'+type+"s").css("display","none");
		$('#'+type).html(text);
	}
	
}
/**
 * 校验注册的用户名
 */
function checkMemberNameExist(){
	
	var username = $.trim($('#username').val());
	var reg = /^[\u4E00-\u9FA5a-zA-Z0-9-_]{2,20}$/; //用户名
	var fullNumber = /^[0-9]+$/; //数字
	
	if(reg.test(username) && !fullNumber.test(username) && username.length>=2 && username.length<=20){
		$.post("${basePath}/member/checkMemberRepeatForRegist.html", 
				{username:username}, 
				function(data) {
				if(data != null && data.code == 1){
					viewStyle("userTip","",1);
					return true;
				}else if(data.message =='该用户名已经存在！'){
					viewStyle("userTip",data.message,2);
					return false;
				}else{
					viewStyle("userTip",data.message,2);
					return false; 
				}
			}
		);
	}else{
		viewStyle("userTip",'用户名为2~20位字母、数字或汉字',2);
		return false;
	}
	if(username==null || username==""){
		viewStyle("userTip",'用户名不能为空',2);
		return false;
	}
	//验证用户名是否存在
	$.post("${basePath}/member/checkMemberContainSensitiveForRegist.html", 
			{username:username}, 
			function(data) {
			if(data != null && data.code != 1){
				viewStyle("userTip",data.message,2);
				return false;
			}
		}
	);
	//用户名不能包含非法字符@
	if(username.indexOf('@')!=-1){
		viewStyle("userTip",'用户名不能包含非法字符',2);
		return false;
	}
	
	//不能包含非法字符 
	if(haveInvalidChars(username)){
		viewStyle("userTip",'用户名不能包含非法字符',2);
		return false;
	}
	
	//用户名不能全为数字
	var patten_intege1 = regexEnum.intege1;
	if(patten_intege1.test(username)){
		viewStyle("userTip",'用户名不能是纯数字，请重新输入',2);
		return false;
	}
	
	return true;
	 
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
			next();
		}
	}
});
//发送短信code
function sendPhoneCode(){
	   var mobileNum=$('#mobileNum').val();
	   $.post("${basePath}/member/sendMobileCode.html", {mobileNum:mobileNum}, 
				function(data) {
					if(data != null && data.code != 1){
						layer.alert(data.message,5);	
					}else{
						$('#tips').addClass('gcdxts');
						$('#tips').html('短信验证码已发送到您的手机，请注意查收！');
						 
					} 
		});
	   
}
var temp;
var t;
//发送短信验证加载
function sendMessage(){
	
	var validCode=false;
    var currentTime=180;
    var sendCode=$('#dyMobileButton');
    sendCode.html(currentTime+" 秒后重新发送");
     temp=setInterval(function name() {
    	currentTime--;
    	sendCode.html(currentTime+" 秒后重新发送");
    	if (currentTime==0) {
    		$('#tips').html('');
			$('#tips').removeClass('gcdxts');
		    clearInterval(temp);
		    sendCode.html("重新获取短信验证码");
			validCode=true;
			sendCode.addClass("msgs1");
			sendCode.removeClass("gcbtnyz");
			 
			}
	}, 1000);
	//获取短信验证码
	
	$("#dyMobileButton").click (function  () {
		var time=180;
		var code=$(this);
		if (validCode) {
			sendPhoneCode();
			validCode=false;
			code.html(time+"秒后重新发送");
			code.addClass("gcbtnyz");
			code.removeClass("msgs1");
		  t=setInterval(function  () {
			time--;
			code.html(time+" 秒后重新发送");
			if (time==0) {
			$('#tips').html('');
			$('#tips').removeClass('gcdxts');	
		    clearInterval(t);
			code.html("重新获取短信验证码");
			code.addClass("msgs1");
			code.removeClass("gcbtnyz");
			validCode=true;
			}
		},1000)
		}
	});
	 
}
//返回
function back(){
	 temp=clearInterval(temp);
	 t=clearInterval(t);
	 $('.userRegiste').css("display","block");
	 $('.userVerify').css("display","none");
	 $('#validatecode').val('');
	 $('#tips').addClass('gcdxts');
	 $('#tips').html('短信验证码已发送到您的手机，请注意查收！');
}

/**
 * 注册方法
 */
function next(){
	 
	//验证数据
	if(!checkData()){
		return;
	} 
	$('#subbtn').attr("disabled","disabled");
	var _load = layer.load('处理中..');
	$("#register_form").ajaxSubmit({
		url : '${basePath }/member/registVerify.html',
		data:{inviterName:'${inviterName}'},
		type : 'post',
		dataType:'json',
		success : function(result) {
			loadimage();
			if(result.code=="1"){
			     sendMessage();
			     var phone=$("#mobileNum").val();
			     var phone_style=phone.substr(0,3)+"  "+phone.substr(3,4)+"  "+phone.substr(7,4);
			     $('.telyz').html(phone_style);
				 $('#subbtn').removeAttr("disabled");
				 $('.userRegiste').css("display","none");
				 $('.userVerify').css("display","block");
				 $('#tips').addClass('gcdxts');
				 $('#tips').html('短信验证码已发送到您的手机，请注意查收！');
				 layer.close(_load);
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
	 
    if(!checkMemberNameExist()){
    	return false;
    }
    var username=$('#username').val();
	//验证手机号码
	var mobileNum = $('#mobileNum').val();
	
	if(!checkMobileNum()){
        return false;
	}
	//密码
	var password1 = $('#logpassword').val();
	if(password1==null || password1==""){
		layer.alert('登录密码不能为空',5);
		return false;
	}else if(password1.length<6){
		layer.alert('登录密码必须大于或等于6位',5);
		return false;
	}else if(password1.length>20){
		 
		layer.alert('登录密码不能大于20位',5);
		return false;
	}else{
		var regx = new RegExp("^([0-9]+[A-Za-z]+|[A-Za-z]+[0-9]+)[A-Za-z0-9]*$");
		if(!regx.test(password1)){
			layer.alert('登录密码必须为数字加字母',5);
			return false;
	    }
	}
	//确认密码
	var password2 = $('#password2').val();
	if(password2==null || password2==""){
		layer.alert('确认登录密码不能为空',5);
		return false;
	}else{
		if(password2!=password1){
			layer.alert('2次登录密码输入不一致',5);
			return false;
		}
	}
	//验证码
	var validatecode = $('#validatecode').val();
	if(validatecode==null || validatecode==""){
		layer.alert('验证码不能为空',5);
		return false;
	}
	//是否阅读
	 
	if(!$("#xiyi").is(":checked")){
		layer.alert('请先阅读条例并选择已阅读条例',5);
		return false;
	}
	 
	//验证用户名是否存在
	$.post("${basePath}/member/checkMemberRepeatForRegist.html", 
			{username:username}, function(data) {
			if(data != null && data.code != 1){
				layer.alert('用户名已经存在',5);
				flag=false;
			}
		}
	);
	 
	if(!flag){return false;}
	//验证用户名包含敏感词
	$.post("${basePath}/member/checkMemberContainSensitiveForRegist.html", 
			{username:username}, function(data) {
			if(data != null && data.code != 1){
				layer.alert(data.message,5);
				flag=false;
			}
		}
	);
	 var inviterName ;
	//验证信用人
	if(realName==null||realName==''){
	   inviterName = $.trim($("#inviterName").val());
	}else{
	   inviterName = $.trim($("#inviterName1").val());
	}
	if(inviterName.length>0){
		$.post("${basePath}/member/isInviterNameExist.html", {inviterName:inviterName}, 
				function(data) {
					if(data != null && data.code != 1){
						layer.alert(data.message,5);
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
/**
 * 注册方法
 */
function goto_register(){
	    
	   var mobileCode= $.trim($("#mobileCode").val());
	   
	   if(mobileCode==null || mobileCode ==""){
	 	   layer.alert("手机验证码不能为空",5);
	 	   return;
	    } 
	   var source='${linkSourceValue}';
	   if(source=='46'){
		 	//珍岛DSP的统计代码
		   _CWiQ.push(['_trackReg', 1]);
	   }
	    //禁用链接
	    $('#tubbtn').attr("disabled","disabled");
	    $('#mobileCodes').val(mobileCode);
	    var _load = layer.load('处理中..');
		$("#register_form").ajaxSubmit({
			url : '${basePath }/member/registMemberInfo.html',
			data:{inviterName:'${inviterName }'},
			type : 'post',
			dataType:'json',
			success : function(result) {
				layer.close(_load);
				if(result.code=="1"){
					//启用链接
					$('#tubbtn').removeAttr("disabled");
					var username = $.trim($('#username').val());
					var url="${path}/user/registeSuccessPage.html?username="+username;
					window.open(encodeURI(encodeURI(url)),"_self");
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
					 $('#tubbtn').removeAttr("disabled");
					 layer.close(_load);
				}
			},
			error : function(XMLHttpRequest, textStatus, errorThrown) {
				 
				 $('#tubbtn').removeAttr("disabled");
				 layer.close(_load);
		    }
		});
	}
</script>
</html>