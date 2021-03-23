<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%> 
<%@ include file="/WEB-INF/page/common/taglib.jsp"%> 
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ include file="/WEB-INF/page/common/public.jsp"%>
<script type="text/javascript" src="${basePath}/js/formValid.js"></script>
<link href="${basePath}/css/login.css" rel="stylesheet" type="text/css" />
<style type="text/css">
.msgs1{
background:#00ade9 none repeat scroll 0 0;display:block; height:32px; line-height:35px; width:30%; border-radius:3px; margin-left:9px; float:left; text-align:center; color:#fff;  }
.gcbtnyz{display:block; width:30%; line-height:35px;  color:#fff; height:32px; border-radius:3px; margin-left:9px; float:left; text-align:center;}

</style>
<title>找回登录密码_顶玺金融</title>
<script type="text/javascript">
var temp;//计时器变量
  $(function(){
	  $('.phone').show();
	  $('.email').hide();
  });
  function findPhone(){
	  clearInterval(temp);
	  $('.phone').show();
	  $('.email').hide();
	  $('#phoneOne').css('display','block');
	  $('#phoneThree').css('display','none');
	  $('#phoneTwo').css('display','none');
	  $('#reset1').trigger("click");
  }
  function findEmail(){
	  clearInterval(temp);
	  $('.email').show();
	  $('.phone').hide();
	  $('#reset2').trigger("click");
  }
  function back(){
	  var code=$('#dyMobileButton');
	  code.addClass("gcbtnyz");
	  code.removeClass("msgs1");
	  clearInterval(temp);
	  $('#phoneOne').show();
	  $('#phoneTwo').css('display','none');
  }
  function next_one(){
	 
	  if(!chekcPhone()){
		  return;
	  }
	  sendMessage();
	  var phone=$("#mobileNum").val();
	  var phone_style=phone.substr(0,3)+"  "+phone.substr(3,4)+"  "+phone.substr(7,4);
	  $('#tip').html(phone_style);
	  $('#phoneOne').hide();
	  $('#phoneTwo').css('display','block');
  }
  function next_two(){
	  if(!confirm_message()){
		  return;
	  }
	  $('#phoneTwo').css('display','none');
	  $('#phoneThree').css('display','block');
  }
  //最终提交
  function next_three(){
	   
	    $('#three').attr("disabled","disabled");
		if (Validator.Validate('findPwdForm',1)==false) {
			$('#three').removeAttr("disabled");
			return;
		}
		$('#mobile').val($('#mobileNum').val());
		if(layer.confirm("确定提交吗？",function(){
	  		var _load = layer.load('处理中..');
	  		$("#findPwdForm").ajaxSubmit({
	  			url : '${basePath}/account/safe/findLoginPasswd.html',
	  			type : 'post',
	  			dataType :"json",
	  			success : function(result) {
	  				$('#three').removeAttr("disabled");
	  				layer.close(_load);
	  				if (result.code == '0') {
	  					parent.layer.msg(result.message, 1, 5);
	  				}else{
	  					layer.msg('验证通过，已发送重置密码至您的手机，请用重置密码登录后再修改登录密码',3,1,function(){
	  						window.open("${basePath}/member/toLoginPage.html","_self");//跳转到登陆页面
	  						});
	  				}
	  			},
	  			error : function(result) {
	  				$('#three').removeAttr("disabled");
	  				layer.close(_load);
	  				layer.msg('网络连接超时,请您稍后重试.', 1, 5);
	  		    }
	  		});
	  	},'',function(){$('#three').removeAttr("disabled");}));
  }
  //邮箱验证第一步
  function next_four(){
	    var flag=true;
	    $('#four').attr("disabled","disabled");
		if (Validator.Validate('emailForm',1)==false) {
			$('#four').removeAttr("disabled");
			return;
		}

  		var _load = layer.load('处理中..');
  		$("#emailForm").ajaxSubmit({
  			url : '${basePath}/account/safe/confrimIdCard.html',
  			type : 'post',
  			async: false,
  			dataType :"json",
  			success : function(result) {
  				$('#four').removeAttr("disabled");
  				layer.close(_load);
  				if (result.code == '0') {
  					parent.layer.msg(result.message, 1, 5);
  					flag=false;
  				}
  			},
  			error : function(result) {
  				$('#four').removeAttr("disabled");
  				flag=false;
  				layer.close(_load);
  				layer.msg('网络连接超时,请您稍后重试.', 1, 5);
  		    }
  		});
	   
	  if(!flag){
		  return;
	  }	
	  $('#emailOne').hide();
	  $('#emailTwo').css('display','block');
  }
  //发送邮件
  function next_five(){
	  var flag=true;
	  $('#five').attr("disabled","disabled");
	  if (Validator.Validate('form',1)==false) {
			$('#five').removeAttr("disabled");
			return;
	  }
	  var email=$('#email').val();
	  var realname=$('#realname').val();
	  var idCard=$('#idCard').val();
      var validatecode=$('#code').val();
      if (validatecode == '') {
 		 layer.alert("验证码不能为空");
 		 $('#five').removeAttr("disabled");
 		 return false;
 	   } 
	 	//验证码是否正确
	 	 $.ajax({
	             type: "POST",
	             url: "${basePath}/member/isValiteCode.html",
	             async: false,
	             data: {validatecode:validatecode},
	             success: function(data) {
	           	  if(data != null && data.code == 1){
	 					flag=true;
	 				}else{
	 					flag=false;
	 					layer.alert(data.message);
	 					$('#five').removeAttr("disabled");
	 				}
	             }
	        });
  	   if(!flag){
  	    	return ;
  	   }
  	  var _load = layer.load('处理中..');
      $.post("${basePath}/account/safe/findLoginPasswdByEmail.html", {email:email,realname:realname,idCard:idCard}, 
		function(data) {
    	    layer.close(_load);
			if(data != null && data.code != 1){
				layer.alert(data.message,5); 
				$('#five').removeAttr("disabled");
			}else{
				layer.msg('验证通过，已发送重置密码至您的邮箱，请用重置密码登录后再修改登录密码',3,1,function(){
					window.open("${basePath}/member/toLoginPage.html","_self");//跳转到登陆页面
					});
			} 
	  });
  }
  //发送短信code
  function sendPhoneCode(){
  	   var mobileNum=$('#mobileNum').val();
  	   $.post("${basePath}/member/sendMobileCode.html", {mobileNum:mobileNum}, 
  				function(data) {
  					if(data != null && data.code != 1){
  						layer.alert(data.message,5); 
  					}else{
  						layer.alert("发送短信成功，请注意查收！",1);
  					} 
  		});
  	   
  }
  
  //发送短信验证加载
  function sendMessage(){
  	
  	  var validCode=false;
      var currentTime=180;
      sendPhoneCode();
      var sendCode=$('#dyMobileButton');
      sendCode.html(currentTime+" 秒后重新发送");
      temp=setInterval(function name() {
      	currentTime--;
      	sendCode.html(currentTime+" 秒后重新发送");
      	if (currentTime==0) {
  		    clearInterval(temp);
  		    sendCode.html("重新获取验证码");
  			validCode=true;
  			sendCode.removeClass("gcbtnyz");
  			sendCode.addClass("msgs1");   			 
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
  		var t=setInterval(function  () {
  			time--;
  			code.html(time+" 秒后重新发送");
  			if (time==0) {
  		    clearInterval(t);
  			code.html("重新获取验证码");
  			code.addClass("msgs1");
  			code.removeClass("gcbtnyz");
  			validCode=true;
  			}
  		},1000)
  		}
  	});
  	 
  }
  function chekcPhone(){
    var  flag=true;
    //手机
	var mobileNum = $('#mobileNum').val();
	if(mobileNum==null || mobileNum=="" || mobileNum=="请输入常用有效手机"){
		layer.alert("请填写手机号码。");
		return false;
	}else{
		var patten_mobile = new RegExp(/\d{11}/);
		if(!patten_mobile.test(mobileNum)){
			layer.alert("请输入有效手机号码。");
			return false;
		} 
	}
	// 验证码
	var validatecode = $('#validatecode').val();
	
	if (validatecode == '') {
		layer.alert("验证码不能为空");
		return false;
	} 
	//验证码是否正确
	 $.ajax({
            type: "POST",
            url: "${basePath}/member/findMobileByOne.html",
            async: false,
            data: {validatecode:validatecode,mobileNum:mobileNum},
            success: function(data) {
          	  if(data != null && data.code == 1){
					flag=true;
				}else{
					flag=false;
					layer.alert(data.message);
				}
            }
       });
	if(!flag){
	    	return flag;
	}
	return true;
  }
  //手机验证码
  function confirm_message(){
	   var mobileCode=$('#mobileCode').val();
	   if(mobileCode==""){
		   layer.alert("请输入短信验证码！");
		   return false;
	   }
	   var flag=true;
	   $.ajax({
            type: "POST",
            url: "${basePath}/member/isMobileCode.html",
            async: false,
            data: {mobileNum:$('#mobileNum').val(),mobileCode:$('#mobileCode').val()},
            success: function(data) {
          	  if(data != null && data.code != 1){
          		  layer.alert(data.message,5);
				  flag=false;
				} 
            }
       });
	  return flag;
  }
   

  /**
   * 刷新验证码 
   */
  function loadimage() {
  	document.getElementById("randImage").src = "${basePath}/random.jsp?" + Math.random();
  }
  function loadimage1() {
	document.getElementById("randImage1").src = "${basePath}/random.jsp?" + Math.random();
  }
 
</script>
</head>

<body>
<%@ include file="/WEB-INF/page/common/header.jsp"%>

<form id="findPwdForm">
<div id="Bmain"  class="phone">
        
       <div class="login_main loginbg" style="margin-top:20px; border-top:1px #ccc solid;">
       
       <div class="login_tit loginbg" style=" margin:0; border:none; margin:-50px 0 50px 0; border-bottom:#ccc solid 1px; position:relative;">
       <span class="zh1" style="z-index: 0;">手机找回密码</span>
       <span class="zh2" style=" "><a  onclick="findEmail();" style="cursor: pointer;">邮箱找回  <img src="${basePath}/images/gd.png"/></a></span>
       </div>
            <!-- 第一步 -->   
            <div class="login_mainbox " style="" id="phoneOne" > 
                <dl style=" width:335px; margin:0 auto; padding-left:15px; ">
                  <dd><span><font color="#22adce"></font> 手机号码：</span> 
                    <input style="padding-left:2%" maxlength="11" id="mobileNum" name="mobileNum" type=text></dd>
                  <dd><span><font color="#22adce"> </font>验证码：</span> 
                    <input style="width:26%; padding-left:2%;"id="validatecode" maxlength="4" type=text><a href="javascript:loadimage();" style="margin-left:5px;"><img  id="randImage" src="${basePath}/random.jsp"/></a></dd>
                    <dd></dd>  
                  <dd> <input type="button" id="one" onclick="next_one();" style="width:80%; margin-left:30px; cursor:pointer;" class="login_btn" value="下一步"/></dd>  
                    <input name="findType" value="mobile" type="hidden"/>
             
                  <dd></dd>
                </dl>
         <div style=" text-align:center; "> <img src="${basePath}/images/tip.png" width="16" height="16"/>&nbsp;若以上方法仍无法找回密码，请联系客服400-0156-676</div>

           </div>
           <!-- 第二步 -->
            <div class="login_mainbox "  id="phoneTwo" style="display:none;"> 
                <dl style=" width:335px; margin:0 auto; padding-left:15px; ">
 
                     <dd style="  overflow:hidden;"><span><font color="#22adce"></font> 
                                                  手机号码：</span><span style="font-size:14px; padding:0 10px; "id="tip"> </span>
                      <a href="#" style="color:#00a7e5; display:none; margin-left:20px; " onclick="back();" >返回修改</a> </dd>

                           <style>
                           	
                           </style>
                    
                  <dd style="overflow:hidden; width:600px; padding-top:10px; "><span><font color="#22adce">  </font>短信验证码：</span> 
                    <input style="width:17%; float:left; padding-left:2%;" id="mobileCode" maxlength="6" type="text"><div class="gcbtnyz"  id="dyMobileButton"></div><!-- <a class="passwore-btncf passwore-btncf1"  href="#" style=" ">重新发送</a>--></dd>
 
                   <dd></dd>  
                  
                  <dd> <input type="button" id="two" onclick="next_two();" "width:80%; margin-left:30px; cursor:pointer;" class="login_btn" value="下一步"/></dd>  
                  <dd></dd>
                  <dd></dd>
                </dl>
            <div style=" text-align:center; "> <img src="${basePath}/images/tip.png" width="16" height="16"/>&nbsp;若以上方法仍无法找回密码，请联系客服400-000-0000</div>

           </div>
           <!-- 第三步 -->
        
            <div class="login_mainbox " id="phoneThree" style="display:none;"> 
                <dl style=" width:335px; margin:0 auto; padding-left:15px; ">
                  <dd><span><font color="#22adce"> </font> 真实姓名：</span> 
                    <input style="padding-left:2%" name="realname" type=text dataType="Limit" min="1" max="20" msg="请输入真实姓名" maxlength="20" ></dd>
                  <dd><span><font color="#22adce"> </font> 身份证号：</span> 
                    <input style="padding-left:2%" name="idCard" type=text dataType="Limit" min="1" max="20" msg="请输入身份证" maxlength="20"></dd>
                    
                   <dd></dd>  
                  <dd> <input type="button" onclick="next_three();" id="three" style="width:80%; margin-left:30px; cursor:pointer;" class="login_btn" value="提交"/></dd>  
                  <dd></dd>
                  <dd></dd>
                </dl>
            <div style=" text-align:center; "> <img src="${basePath}/images/tip.png" width="16" height="16"/>&nbsp;若以上方法仍无法找回密码，请联系客服400-000-0000</div>

           </div>
           <input type="reset" id="reset1" style="display: none;" />
           
        </div>
     
     </div>
</form>
 
     <!-- 邮箱找回密码 -->
     <div id="Bmain" class="email" >
         
       <div class="login_main loginbg boxzh" style="">
       
       <div class="login_tit loginbg boxzh2" style=" ">
       <span class="yxzh1" style="z-index: 0;">邮箱找回密码</span><span style="float:right; font-size:16px; padding-right:15px; padding-top:10px; "><a  onclick="findPhone();"  style="cursor: pointer; ">手机找回 <img src="${basePath}/images/gd.png"/></a></span></div>
       <form id="emailForm">
            <!-- 第一步 -->
            <div class="login_mainbox " id="emailOne" > 
                <dl style=" width:335px; margin:0 auto; padding-left:15px; ">
                  <dd><span>真实姓名：</span> 
                    <input style="padding-left:10px;" type=text name="realname" id="realname" dataType="Limit" min="1" max="20" msg="请输入真实姓名" maxlength="20"></dd>
                    <dd><span> 身份证号：</span> 
                    <input style="padding-left:10px;" type=text name="idCard" id="idCard"  dataType="Limit" min="1" max="20" msg="请输入身份证" maxlength="20"></dd> 
                  <dd></dd>
                  <dd> <input type="button" id="four" onclick="next_four();" style="width:83%; cursor:pointer;margin-left:30px;" class="login_btn" value="下一步"/></dd>  
                   
                  <dd></dd>
                </dl>
               <div style=" text-align:center; "> <img src="${basePath}/images/tip.png" width="16" height="16"/>&nbsp;若以上方法仍无法找回密码，请联系客服400-000-0000</div>
                 <input type="reset" id="reset2" style="display: none;" />
           </div>
         </form>
            <!-- 第二步 -->
            <form id="form"> 
             <div class="login_mainbox" id="emailTwo" style="display:none;"> 
                <dl style=" width:335px; margin:0 auto; padding-left:15px; ">
                  <dd><span>邮箱：</span> 
                    <input style="padding-left:10px;" id="email" dataType="Email" msg="请输入有效的邮箱号码"  type=text></dd>
                    <dd><span>验证码：</span> 
                    <input style="padding-left:10px; width:30%;" id="code" maxlength="4" type=text><a style="margin-left:5px;" href="javascript:loadimage1();"><img  id="randImage1" src="${basePath}/random.jsp"/></a></dd>
      
                  <dd></dd>
                  <dd> <input type="button" id="five" onclick="next_five();" style="width:83%; cursor:pointer;margin-left:30px;" class="login_btn" value="提交"/></dd>  
                  <dd></dd>
                  <dd></dd>
                </dl>
                  <div style="text-align:center;"> <img src="${basePath}/images/tip.png" width="16" height="16"/>&nbsp;若以上方法仍无法找回密码，请联系客服400-0156-676</div>
                  <input type="reset" id="reset2" style="display: none;" />
           </div>
           </form>
        </div>
       
     </div>
    
<%@ include file="/WEB-INF/page/common/footer.jsp"%>
</body>
</html>