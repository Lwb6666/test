<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/page/common/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ include file="/WEB-INF/page/common/public.jsp"%>
<title>验证当前手机_顶玺金融</title>
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
			<span><a href="${path}/myaccount/toIndex.html">我的账户</a></span>
			<span>账户管理</span>
			<span><a href="${path }/AccountSafetyCentre/safetyIndex.html">安全中心</a></span>
			<span>修改绑定手机</span>
  </div>
        <div id="menu_centert">
          <%@ include file="/WEB-INF/page/account/myAccount_leftMenu.jsp"%>  
               <div class="lb_waikuang border whitebg">
                   <div class="safetoptit">安全中心</div>     
                 <div class="safe ">
                 <div class="safebox550">
                   <dl>
						<li style ="color:#97c63a"><a style ="color:#00a7e5">温馨提示</a>：请先验证原手机号码，再去绑定新手机！</li> 
                     <dd class="currentdd"><span>手机号码：</span> 
                     <span id="curmobile">${mobileAppro.securitymobileNum }</span>
                         <input class="safe_button01" type="button" name="sendforresetbtn" id="sendforresetbtn" value="发送验证码" style="cursor:pointer;"/></dd>
                     <dd class="currentdd"><span>短信验证码：</span> <input name="resetActiveCode" maxlength="6" id="resetActiveCode"  class="inputtext"></dd>
                    
                     
                 </dl>
                
                 <div class="gg_btn"><input  type="button" name="resetActivebtn" id="resetActivebtn" value="提交" style="cursor:pointer;"/></div>
                    <c:if test="${emailApproVo.checked==null or emailApproVo.checked==0}">
                    <dd class="currentdd02 textcenter">邮箱还未认证？<a style="color:#00a7e5;" href="${path }/account/approve/email.html">去认证</a></dd>
                    </c:if>
                 </div>
                 </div>
                 <form action="" method="post" id="mobileform">
                 </form>  
</div>
           <!--我的账户左侧 结束 -->
     
<!--我的账户右侧开始 -->             
               
</div>
</div>
</div>
<%@ include file="/WEB-INF/page/common/footer.jsp" %>
</body>
<script type="text/javascript">

var mobile ='${mobileAppro.mobileNum }';
var activeCode =$.trim($('#resetActiveCode').val());
var _load; 
$(document).ready(function(){
	//绑定事件
	$('#sendforresetbtn').bind('click',sendforresetmsg);
	$('#resetActivebtn').bind('click',clickresetActivebtn);
	
	//设置页面手机号码
	var mobileNum = '${mobileAppro.securitymobileNum }';
	if(mobileNum != 'undefined' && mobileNum != ''){
		var str1 = mobileNum.substr(0,3);
		var str3 = mobileNum.substring(mobileNum.length-4);
		var str2 = "";
		for(var i=0; i<mobileNum.length-7;i++){
			str2 = str2 + "*";
		}
		$("#activatebtnmobile").html(str1+str2+str3);
		//$("#curmobile").html(str1+str2+str3);
	};
	//是否有错误信息
	var errormsg = '${errorMsg}';
	if(null!=errormsg && ""!=errormsg){
		clickresetbtn();
		layer.alert(errormsg);
	}
});
/**
 * 提交表单
 */
function clickresetActivebtn(){
	$('#resetActivebtn').attr("disabled","disabled");
	var resetActiveCode = $("#resetActiveCode").val();
	resetActiveCode = $.trim(resetActiveCode);
	if(resetActiveCode.length==0){
		layer.alert("验证码不能为空",5);
		$('#resetActivebtn').removeAttr("disabled"); 
		return;
	}
	
	_load = layer.load('处理中..');
	$("#mobileform").ajaxSubmit({
	    url : '${basePath}/account/approve/mobile/verifyCurrentUserMobile.html',
	    data:{
	    	  activeCode:resetActiveCode,
	    	  mobile:mobile
	    },
	    type: "post",
	    dataType:'json',
	    success:function(result){
	    	layer.close(_load);
	    	$('#resetActivebtn').removeAttr("disabled"); 
	    	if(result.code=="1"){
	    		window.open("${path }/account/approve/mobile/mobileforinsert.html","_self");
	    	}else{
				layer.msg(result.message,5);		    		
	    	}
	    },
		error : function() {
			layer.close(_load);
			$('#resetActivebtn').removeAttr("disabled"); 
			layer.msg('网络连接超时，请您稍后重试', 1, 5);
	    } 
	 });
}
/**
 * 发送原手机号验证码
 */
function sendforresetmsg(){
	$('#sendforresetbtn').attr("disabled","disabled");
	_load = layer.load('处理中..');
	$("#mobileform").ajaxSubmit({
	    url : '${basePath}/account/approve/mobile/sendResetMsg.html',
	    type: "POST",
	    success:function(result){
	    	layer.close(_load);
	    	$('#sendforresetbtn').removeAttr("disabled"); 
	    	if(result.code=="1"){
	    		layer.alert(result.message,1);
	    		
	    	}else{
				layer.alert(result.message,5);		    		
	    	}
	    },
		error : function() {
			layer.close(_load);
			$('#sendforresetbtn').removeAttr("disabled"); 
			layer.msg('网络连接超时，请您稍后重试', 1, 5);
	    } 
	 });	
}
/**
 * 重设手机按钮
 */
function clickresetbtn(){
	$('#resetDiv').show();
	$('#resetbtn').hide();
	
}
/**
 * 刷新过期时间
 */
var runresettime=0;
function runreset(){
	runresettime=60;
	runresetclock();
}
function runresetclock(){
	$('#sendforresetbtn').attr("disabled","disabled");
	setTimeout(function(){
		runresettime = runresettime-1;
		if(runresettime>0){
			runresetclock();
		}else{ 
			$('#sendforresetbtn').attr("disabled",false);
			runresettime=60;
		}
	},1000);
}

</script>
</html>
