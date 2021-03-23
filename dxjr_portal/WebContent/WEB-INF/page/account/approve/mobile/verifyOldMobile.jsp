<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/page/common/taglib.jsp"%>
	<script type="text/javascript" src="${basePath}/js/location.js"></script>
    <script type="text/javascript" src="${basePath }/js/formValid.js?${version}"></script>
<!---------- ------------------------手机认证-弹层---------------------------------------------------->
<!--弹层star--->
	<div class="cont-word">
    	<div class="title"><h4>更换手机号</h4><a href="javascript:void(0);" onclick="closeMobileAppro();" class="icon icon-close fr"></a></div>
        <div class="form-info-layer">
            <div class="form-col2">
                <label for="" class="colleft form-lable">手机号码</label>
                 <span class="fl money" id="curmobile" ><strong class="f14">${mobileAppro.securitymobileNum }</strong></span>
            </div>
        	<div class="form-col2">
                <label for="" class="colleft form-lable">短信验证码</label>
                <input type="text" name="resetActiveCode" maxlength="6" id="resetActiveCode"  style="width:90px" class="colright form-inpyt-sm"  placeholder="请输入验证码">
                 <p class="fl pdlr10 line32" id="sendActiveCode1"><span name="sendforresetbtn" id="sendforresetbtn"><a>发送验证码</a></span></p>
                 <p class="fl pdlr10 line32" id="clockActiveCode1" style="display: none;"><span name="sendforresetbtn1" id="sendforresetbtn1"><a>发送验证码</a></span></p>
            </div>
            <div class="form-col2">
            <button type="button" class="remove" onclick="closeMobileAppro();">取消</button><button type="button" class="enter" name="resetActivebtn" id="resetActivebtn">确定</button>
            </div>
         </div>
    </div> 
     <form action="" method="post" id="mobileform"> </form>  
<!--弹层end--->
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
	    		layer.msg(result.message,5);
	    		toMobileAppro();
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
	    		runreset();
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
	$("#sendActiveCode1").hide();
	$("#clockActiveCode1").show();
	$('#sendforresetbtn').attr("disabled","disabled");
	setTimeout(function(){
		runresettime = runresettime-1;
		if(runresettime>0){
			runresetclock();
		}else{ 
			$("#sendActiveCode1").show();
			$("#clockActiveCode1").hide();
			$('#sendforresetbtn').attr("disabled",false);
			runresettime=60;
		}
	},1000);
}

</script>
</html>
