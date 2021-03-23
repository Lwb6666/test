<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/page/common/taglib.jsp"%>
<%@ include file="/WEB-INF/page/common/public.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>注册_填写账户信息_顶玺金融</title>
<link rel="stylesheet" type="text/css" href="${basePath}/css/layout.css"  />
<link rel="Stylesheet" type="text/css" href="${basePath}/css/loginDialog.css"/>
<link rel="stylesheet" 	href="${basePath}/css/style-as.css" media="screen">
<link href="${basePath}/css/login.css" rel="stylesheet" type="text/css" />
 <style type="text/css">
 body {
  
color: #555;
background: url(../images/background.png) no-repeat;
background: url(../images/backgroundb.png) no-repeat;
background: url(../images/backgrounda.png) no-repeat\0;
font-size: 13px;
}
.msgs1{
background:#00ade9 none repeat scroll 0 0;display:block; height:40px; line-height:40px; width:110px; border-radius:3px; margin-left:-2px; float:left; text-align:center; color:#fff;  }
A:hover {
 COLOR: #ff7f24;
}
.gcbtnyz{display:block; width:110px; line-height:40px;  color:#fff; height:40px; border-radius:3px; margin-left:-2px; float:left; text-align:center;}
 </style>
 <script type="text/javascript">
 //显示样式
 function viewStyle(type,tip,flag){
	   if(flag==1){
		   $('#'+type).show();
		   $('#'+type).html(tip);
		   setTimeout(function(){
				$('#'+type).css("display","none");
		   },3000)
	   }else{
		   $('#'+type).html();
	   }
 }

 //验证注册
 function validate_register() {
	    var flag=true;
		// 用户名
		var username = $.trim($('#username').val());
		if (username==null || username=="") {
			viewStyle("userTip","用户名不能为空",1);
			return false;
		}
		//用户名不能包含非法字符@
		if (username.indexOf('@') != -1) {
			viewStyle("userTip","用户名不能包含非法字符@",1); 
			return false ;
		}
		 
		//不能包含非法字符 
		if (haveInvalidChars(username)) {
			viewStyle("userTip","用户名不能包含非法字符",1); 
			return  false;
		}
		//用户名不能全为数字
		var patten_intege1 = regexEnum.intege1;
		if (patten_intege1.test(username)) {
			viewStyle("userTip","用户名不能全为数字",1);
			return false;
		}
		var reg = /^[\u4E00-\u9FA5a-zA-Z0-9-_]{2,20}$/; //用户名
		var fullNumber = /^[0-9]+$/; //数字
		if( !reg.test(username) || fullNumber.test(username) || username.length<2 || username.length>20){
			viewStyle("userTip",'用户名为2~20字母、数字或汉字 ',1);
		    return false; 
		}
		//验证用户名是否存在
	    $.ajax({
              type: "POST",
              url: "${basePath}/member/checkMemberRepeatForRegist.html",
              async: false,
              data: {username:username},
              success: function(data) {
            	  if(data != null && data.code == 1){
						flag=true;
					}else{
						viewStyle("userTip",data.message,1);
						flag=false;
				 
					}
              }
         });
		 
	    if(!flag){
	    	return flag;
	    }
	  
		//手机
		var mobileNum = $('#mobileNum').val();
		mobileNum = $.trim(mobileNum);
		 
		if(mobileNum==null || mobileNum==""){
			viewStyle("phoneTip","请填写手机号码。",1);
			return false;
		}else{
			var mobileReg = /^0?(13|15|14|18|17)[0-9]{9}$/;
			if(!mobileReg.test(mobileNum)){
				viewStyle("phoneTip","请输入有效手机号码。",1);
				return false;
			} 
		}

		//验证手机号码是否唯一
		$.ajax({
              type: "POST",
              url: "${basePath}/member/isMobileNumExist.html",
              async: false,
              data: {mobileNum:mobileNum},
              success: function(data) {
            	  if(data != null && data.code == 1){
						flag=true;
					}else{
						viewStyle("phoneTip",data.message,1);
						flag=false;
						 
					}
              }
         });
		if(!flag){
	    	return flag;
	    }
		// 密码
		var password1 = $('#logpassword').val();
		 
		if (password1==null || password1=="") {
			viewStyle("passwordTip","登录密码不能为空",1);
			return false;
		}
		if (password1.length < 6) {
			viewStyle("passwordTip","登录密码必须大于或等于6位",1);
			return false;
		}
		if (password1.length > 20) {
			viewStyle("passwordTip","登录密码不能大于20位",1);
			return false;
		}
		var regx = new RegExp("^([0-9]+[A-Za-z]+|[A-Za-z]+[0-9]+)[A-Za-z0-9]*$");
		if (!regx.test(password1)) {
			viewStyle("passwordTip","密码必须为数字加字母",1);
			return false;
		}
	 
		// 验证码
		var validatecode = $('#validatecode').val();
		
		if (validatecode == '') {
			viewStyle("codeTip","验证码不能为空",1);
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
						viewStyle("codeTip",data.message,1);
					}
             }
        });
		if(!flag){
		    	return flag;
		}
		var ischeck =$("#xiyi").is(":checked");
		if(!ischeck){
			layer.alert('同意协议方可注册！',5);
			return false;
		}
		 
		return true;
	}
 function next(){
	 
	 if(!validate_register()){
		 loadimage();
		 return;
	 }
	 sendMessage();
	 var phone=$("#mobileNum").val();
     var phone_style=phone.substr(0,3)+"  "+phone.substr(3,4)+"  "+phone.substr(7,4);
	 $('#telphone').html(phone_style);
	 $('#register').css("display","none");
	 $('#sendMessage').css("display","block");
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
       var temp=setInterval(function name() {
       	currentTime--;
       	sendCode.html(currentTime+" 秒后重新发送");
       	if (currentTime==0) {
   		    clearInterval(temp);
   		    sendCode.html("重新获取验证码");
   			validCode=true;
   			sendCode.removeClass("gcbtnyz");
   			sendCode.addClass("msgs1");   
   			$('#tip1').hide();
   			$('#tip2').hide();
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
   			$('#tip1').show();
   			$('#tip2').show();
   		var t=setInterval(function  () {
   			time--;
   			code.html(time+" 秒后重新发送");
   			if (time==0) {
   		    clearInterval(t);
   			code.html("重新获取验证码");
   			code.addClass("msgs1");
   			code.removeClass("gcbtnyz");
   			validCode=true;
   			$('#tip1').hide();
   			$('#tip2').hide();
   			}
   		},1000)
   		}
   	});
   	 
   }
   //协议
   function toBorrowXiyi() {
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
   //验证
   function haveInvalidChars(inString) {
		var invalidChars = new Array("<", "%", "\"", ">", "~", "&", "?", "'", "\\", "-", "#", "!");
		for (var i = 0; i < invalidChars.length; i++) {
			if (inString.indexOf(invalidChars[i]) >= 0) {
				return true;
			}
		}
		return false;
	}
   //刷新验证码 
   function loadimage() {
   	  document.getElementById("randImage").src = "${basePath}/random.jsp?" + Math.random();
   }
   function confirm_message(){
	   var flag=true;
	   var mobileCode=$('#mobileCode').val();
	   if(mobileCode==null || mobileCode==""){
		   layer.alert("手机验证码不能为空",5);
		   return false;
	   }
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
   //完成注册
   function goto_register(){
	     
	    $('#registerHref').attr("onclick", "");
		if (!confirm_message()) {
			$('#registerHref').attr("onclick","goto_register();");
			return;
		}
	   var mobileNum=$('#mobileNum').val();
	   var username = encodeURI($.trim($('#username').val()));
		$.ajax({
			type : 'post',
			url : '${basePath}/member/registMemberInfoCollectJson.html',
			data : {
				'username' : username,
				'logpassword' : $('#logpassword').val(),
				'validatecode' : $('#validatecode').val(),
				'mobileNum':mobileNum,
				'source' : '34'
			},
			dataType : 'json',
			success : function(result) {
				if (result.code == "1") {
					var username = $.trim($('#username').val());
					var url="${path}/user/memberSuccess.html?username="+username;
					window.open(encodeURI(encodeURI(url)),"_self");
				} else {
					layer.msg(result.message, 1, 5);
					$('#registerHref').attr("onclick", "goto_register();");
				}
			},
			error : function(XMLHttpRequest, textStatus, errorThrown) {
				   alert(XMLHttpRequest.readyState + XMLHttpRequest.status +   XMLHttpRequest.responseText);
				layer.msg("网络异常,请稍后再试.", 1, 5);
				$('#registerHref').attr("onclick", "goto_register();");
		    }
		});
   }
   //是否显示验证码，密码框获得、失去焦点时、点击"显示密码"按钮是触发
   function passView(){
	if($("#logpassword").val()==null ||$("#logpassword").val()==''){
		return;
	}   
   	if($("#logpassword").attr("type")=="password"){
   		$("#logpassword").attr("type","text");
   	}else{
   		$("#logpassword").attr("type","password");
   	}
   }
 </script>
</head>

<body>
<!--head --> 
<!--头部开始-->

<div id="header">
  <div class="inner">
    <div class="logo"> <a href="#"><img alt="顶玺金融" src="${basePath}/images/logo.png" width="160" height="72"></a> </div>
    <div class="tel">全国免费咨询热线:<span color="#1ca9dc">400-000-0000</span></div>
    <div class="jrgc"><a href="${basePath }">进入顶玺金融</a></div>
  </div>
</div>
<!--head off-->
 
<div class="bigbox-back" style="width:99%\0; background-size:110%\0;" > 
  <!---------------------------注册内容——开始---------------------------------------->
  <div class="loding-box" id="register">
    <div class="loding-content" name="tc-a">
      <h2><img style="margin-top:15px;" src="${basePath}/images/kszc.png"/></h2>
      <ul class="loding-windows">
        <li>
          <div class="name-style">用户名<span  class="tishi" id="userTip"> </span></div>
          <div>
            <input class="text-btn" onblur="" type="text" name="username" maxlength="20" style="font-size:14px;  margin-left:0px;" id="username" placeholder="请输入字母,数字或中文" >
          </div>
        </li>
        
        <li style="  paddding:0px;">
          <div class="name-style">手机号码 <span class="tishi" id="phoneTip"> </span></div>
          <div>
            <input class="text-btn-b" type="text" maxlength="11" name="mobileNum" style=""  id="mobileNum" placeholder="请输入常用有效手机">
          </div>
        </li>
        
		<li>
          <div class="name-style">登录密码<span class="tishi" id="passwordTip"></span></div>
          <div style="position:relative;">
            <input class="text-btn-a" type="password" name="logpassword" placeholder="请输入字6-20位的字符"  id="logpassword">
            
            <a href="javascript:passView();" style="position:absolute; top:7px; right:10px;"><img src="${basePath}/images/xs.png"/></a>
          </div>
          
          
          
        </li>        
        <li>
          <div class="name-style">验证码<span class="tishi" id="codeTip"> </span></div>
          <div style="overflow:hidden;">
            <input style="float:left;     border-radius: 0px;" id="validatecode"   class="text-btn-c" type="text" name="user" >
            <span class="yzm">
           <em> <a href="javascript:loadimage();">
            	<img name="randImage"   id="randImage" src="${basePath}/random.jsp" width="80" height="26" /></a></em>
           </span></div>
        </li>
        <li style="padding:10px 0;">
          <input type="checkbox" id="xiyi" checked="checked">
          <span class="ck-text" style="cursor:pointer;" onclick="toBorrowXiyi();">我已阅读并同意《顶玺金融条款》</span> </li>
        <li>
          <div class="registered-box" onclick="next();">立即注册</div>
        </li>
      </ul>
    </div>
  </div>
  <!---------------------------注册内容——结束----------------------------------------> 
   <!---------------------------验证内容——开始---------------------------------------->
  <div class="loding-box" style="display:none;" id="sendMessage" >
    <div class="loding-content" name="tc-a">
      <h2><img style="margin-top:15px;" src="${basePath}/images/kszc.png"/></h2>
      <ul class="loding-windows">
        <li class="ks-ts">
	    <div style="line-height:20px;height:20px;" id="tip1">短信验证码已发送到您的手机</div>
	    <div style="line-height:20px;height:20px;" id="tip2">请注意查收！</div>  
        </li>
        <li class="ks-ts" style="  ">
        <span style="font-size:14px; line-height:28px;">手机号码</span>
        <span style="padding-left:10px;  font-size:20px;" id="telphone"></span>
        </li>
        <li class="ks-ts">
          
          <span class="ks-l"  style="font-size:14px;">短信验证码</span> 
          <span  class="ks-r"> <input class="ks-btn" type="text" maxlength="6" id="mobileCode" ></span>
           
        </li>
 
        <li class="ks-tb" style="padding:0px 0 35px 80px;"> 
        <a  class="gcbtnyz" id="dyMobileButton" href="#"></a></li>
        <li>
          <a  id="registerHref" href="javascript:void(0);" onclick="goto_register();" class="registered-box">完成注册</a>
        </li>
      </ul>
      
      
      
    </div>
  </div>
  <!---------------------------验证内容——结束----------------------------------------> 
    
  <!---------------------------三块内容——开始---------------------------------------->
  <div class="content-top bigboxwk" >
    <ul class="top-dl">
      <li class="img-pic"></li>
      <li class="text-nr">
        <p class="text-title"><span style="color:#C30D22; ">100%</span>本息保障</p>
        <p>没有任何一位投资人在顶玺金融遭受损失</p>
        <p style=" ">风险备用金100%第三方托管</p>
      </li>
    </ul>
    <ul class="top-dl">
      <li class="img-pic img-pic1"></li>
      <li class="text-nr">
        <p class="text-title"><span style="color:#C30D22; ">最透明</span>的p2p平台</p>
        <p>平台运营数据实时访问，投监会随时查标</p>
        <p style=" ">网贷之家透明度评级排行前5</p>
      </li>
    </ul>
    </ul>
    <ul class="top-dl">
      <li class="img-pic img-pic2"></li>
      <li class="text-nr">
        <p class="text-title"><span style="color:#C30D22; ">13年</span>房抵风控经验</p>
        <p>脱胎于老牌资产管理公司</p>
        <p style=" ">经营最稳健的典当业务</p>
      </li>
    </ul>
  </div>
  <!---------------------------三块内容——结束----------------------------------------> 
  
  </div>
  <script type="text/javascript">
	$(function ($) {
		//弹出登录
		$("#example").hover(function () {
			$(this).stop().animate({
				opacity: '1'
			}, 600);
		}, function () {
			$(this).stop().animate({
				opacity: '1'
			}, 1000);
		}).on('click', function () {
			$("body").append("<div id='mask'></div>");
			$("#mask").addClass("mask").fadeIn("slow");
			$("#LoginBox").fadeIn("slow");
		});
/*		//
		//按钮的透明度
		$("#loginbtn").hover(function () {
			$(this).stop().animate({
				opacity: '1'
			}, 600);
		}, function () {
			$(this).stop().animate({
				opacity: '1'
			}, 1000);
		});*/
		//文本框不允许为空---按钮触发
		$("#loginbtn").on('click', function () {
			var txtName = $("#txtName").val();
			var txtPwd = $("#txtPwd").val();
			if (txtName == "" || txtName == undefined || txtName == null) {
				if (txtPwd == "" || txtPwd == undefined || txtPwd == null) {
					$(".warning").css({ display: 'block' });
				}
				else {
					$("#warn").css({ display: 'block' });
					$("#warn2").css({ display: 'none' });
				}
			}
			else {
				if (txtPwd == "" || txtPwd == undefined || txtPwd == null) {
					$("#warn").css({ display: 'none' });
					$(".warn2").css({ display: 'block' });
				}
				else {
					$(".warning").css({ display: 'none' });
				}
			}
		});
		//文本框不允许为空---单个文本触发
		$("#txtName").on('blur', function () {
			var txtName = $("#txtName").val();
			if (txtName == "" || txtName == undefined || txtName == null) {
				$("#warn").css({ display: 'block' });
			}
			else {
				$("#warn").css({ display: 'none' });
			}
		});
		$("#txtName").on('focus', function () {
			$("#warn").css({ display: 'none' });
		});
		//
		$("#txtPwd").on('blur', function () {
			var txtName = $("#txtPwd").val();
			if (txtName == "" || txtName == undefined || txtName == null) {
				$("#warn2").css({ display: 'block' });
			}
			else {
				$("#warn2").css({ display: 'none' });
			}
		});
		$("#txtPwd").on('focus', function () {
			$("#warn2").css({ display: 'none' });
		});
		//关闭
		$(".close_btn").hover(function () { $(this).css({ color: 'black' }) }, function () { $(this).css({ color: '#999' }) }).on('click', function () {
			$("#LoginBox").fadeOut("fast");
			$("#mask").css({ display: 'none' });
		});
	});
	</script> 
  
  <!-----------------弹层；登录窗口结束-----------------------> 
  
  <!---------------------------主要内容——开始---------------------------------------->
  <div class="bigboxwk">
    <div class="title-hot-a" style="border-left:5px #c30d22 solid;"><span class="text-r">截止目前为止，已经出现<strong>十天3部iPhone6(64G),3次1888元</strong>的记录哦，实名实姓可查看。祝您好运！</span></div>
    <ul class="new-th">
      <li class="ct-l"> <a href="http://www.dxjr.com/lottery_chance/info.html
" class="look-s">去瞅瞅谁中奖了</a> </li>
      <li style="float:right;"><img src="${basePath}/images/back-pic2.png"/></li>
    </ul>
    <div class="title-hot-b" ><span class="text-r" style="color:#ee7916; *padding-left:570px;">我们不是收益率最高的<strong>，但可能是安全平台中收益率最高的！</strong></div>
    <ul class="new-th new-lccp">
      <li><img src="${basePath}/images/gd-back.png"/></li>
    </ul>
    <div class="title-hot-c" style="border-left:5px #619432 solid;"><span class="text-r" style="color:#619432; *padding-left:830px;">我们只用数据说话!</div>
    <ul class="ptsj">
      <li class="ptsj-box" >
        <p>累计投资金额</p>
        <p class="ptsj-text" style="">${map.TotalMoney}0000<span class="f14" style=" color:#00a0e8;">元</span></p>
      </li>
      <li class="ptsj-box-a" >
        <p>累计为客户赚取收益 </p>
        <p class="ptsj-text" style="">${map.investerNetMoney}0000<span class="f14" style=" color:#00a0e8;">元</span></p>
      </li>
      <li class="ptsj-box-b" >
        <p>投直通车总额 </p>
        <p class="ptsj-text" style="*margin-top:-15px;">${map.firstTotalAccountMoney}0000<span class="f14" style=" color:#00a0e8;">元</span></p>
      </li>
    </ul>















			
            
            
            
       
<div style="width:100%; ">
			

				<a href="#" style="cursor:pointer; width:38%;  padding:5% 0;  *padding:8% 0; height:80px; display:block; margin:0 auto; ">
                		<img src="${basePath}/images/btn-kq.png"/>
				</a>
 
</div>


            

    <div class="bigboxwk tishi-bottom" >
      <p style=" *line-height:30px;">*温馨提示：因顶玺金融在业内的信誉，透明，风险控制方面形成的口碑，近期被大量其他平台进行推广冒用。<br/>搜索“顶玺金融”时，请认准www.dxjr.com为顶玺唯一官网网址</p>
<!--      <p style=" *line-height:15px;"></p>-->
    </div>
    <ul class="bottom-text" style="padding-bottom:15%;  *width:100%;" >
      <li style="margin-left:80px !important;">
        <p style=" *line-height:25px;">社科院、新华网联合评估权威认证<br/>顶玺金融被评选为“中国网贷A级企业”</p>
    <!--    <p  style=" *line-height:10px;margin-top:0px\9;"></p>-->
      </li>
      <li style="margin-left:110px;">
        <p style=" *line-height:25px; ">顶玺金融被评为<br/>“年度P2P平台最佳潜力奖”</p>
      <!--  <p style=" *line-height:10px;margin-top:0px\9; "></p>-->
      </li>
      <li style="margin-left:100px;">
        <p style=" *line-height:25px;">第一财经独家专题报道<br/>独家冠名第一财经《金融译时</p>
<!--        <p  style=" *line-height:10px;margin-top:0px\9; ">代》</p>
-->      </li>
    </ul>
  </div>
  
  <!---------------------------主要内容——结束----------------------------------------> 
  
</div>
<div class="clear"></div>
 

</body>
</html>