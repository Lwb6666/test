<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/page/common/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ include file="/WEB-INF/page/common/public.jsp"%>
<title>手机认证_顶玺金融</title>
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
    <span>手机绑定 </span>
  </div>
        <div id="menu_centert">
          <%@ include file="/WEB-INF/page/account/myAccount_leftMenu.jsp"%>  
               <div class="lb_waikuang border whitebg">
                   <div class="safetoptit">安全中心</div>     
                 <div class="safe ">
                 <div class="safebox550">
                   <dl>
                     
                     <dd class="currentdd"><span>手机号码：</span> <input  class="inputtext" name="mobile" id="mobile" maxlength="11" value=""> 
                         <a id ='clock'>180秒后重新获取</a></input>
                         <input class="safe_button01" type="button" name="sendbtn" id="sendbtn" value="获取验证码" /></dd>
                     <dd class="currentdd"><span>短信验证码：</span> <input name="activeCode" maxlength="6" id="activeCode"  class="inputtext"></dd>
                    
                     
                 </dl>
                
                 <div class="gg_btn"><input  type="button" name="activebtn" id="activebtn" value="立即确认" /></div>
                    <c:if test="${emailApproVo == null or emailApproVo.checked==null or emailApproVo.checked==0}">
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
var userName = '${userName }';
var mobile =$.trim($('#mobile').val());
var activeCode =$.trim($('#activeCode').val());
var _load;
$(document).ready(function(){
	//绑定事件
	$('#activebtn').bind('click',formobilesubmit);
	$('#sendbtn').bind('click',forsendmsg);
});

/**
 * 发送验证码
 */
function forsendmsg(){
	$('#sendbtn').attr("disabled","disabled");
	//验证手机号码
	if(verify()){
		
		  _load = layer.load('处理中..');
		$("#mobileform").ajaxSubmit({
		    url : '${basePath}/account/approve/mobile/sendMobailActiveInSaftCenter.html',
		    type: "POST",
		    data:{mobile:$.trim($('#mobile').val())},
		    success:function(result){
		    	if(result.code=="1"){
		    		run();  
		    		layer.alert(result.message,1);
		    	}else{
					layer.alert(result.message);		    		
		    	}
		    	layer.close(_load);
		    	$('#sendbtn').removeAttr("disabled"); 
		    },
			error : function() {
				layer.close(_load);
				$('#sendbtn').removeAttr("disabled"); 
				layer.msg('网络连接超时，请您稍后重试', 1, 5);
		    } 
		 });
	}else{
		$('#sendbtn').removeAttr("disabled"); 
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
			 _load = layer.load('处理中..');
			$("#mobileform").ajaxSubmit({
			   url : '${basePath}/account/approve/mobile/activeMobile.html',
			   type: "POST",
			   data:{
				   activeCode:activeCode,
				   mobile:$.trim($('#mobile').val()),
				   isUpdate:'true'
			   },
			   success:function(result){
				   layer.close(_load);
				   $('#activebtn').removeAttr("disabled"); 
			    	if(result.code=="1"){
			    		window.open("${path }/account/approve/mobile/approMobileSuccess.html?mobile="+$.trim($('#mobile').val()),"_self");
			    	}else{
						layer.alert(result.message);		    		
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
var runtime=0;
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
			runclock();
		}else{
			runtime=180;
			$('#clock').html(runtime+'秒后重新获取');
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
		layer.alert("请填写手机号码。");
		return false;
	}else{
		var patten_mobile = new RegExp(/\d{11}/);
		if(!patten_mobile.test(mobileNum)){
			layer.alert('请输入有效手机号码。');
			return false;
		}
	}
	return flag;
}
</script>
</html>
