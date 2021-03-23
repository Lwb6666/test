<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/page/common/taglib.jsp"%>
	<script type="text/javascript" src="${basePath}/js/location.js"></script>
    <script type="text/javascript" src="${basePath }/js/formValid.js?${version}"></script>
<!---------- ------------------------手机认证-弹层---------------------------------------------------->
<!--弹层star--->
	<div class="cont-word">
    	<div class="title"><h4><c:if test="${flag==0}">更换手机号</c:if><c:if test="${flag==1}">绑定手机号</c:if></h4><a href="javascript:void(0);" onclick="closeMobileAppro();" class="icon icon-close fr"></a></div>
        <div class="form-info-layer">
            <div class="form-col2">
                <label for="" class="colleft form-lable">手机号码</label>
                
                <input type="text"  name="mobile" id="mobile" maxlength="11" value="" style="width:198px" class="colright form-inpyt-sm" placeholder="请输入新的手机号码">
            </div>
        	<div class="form-col2">
                <label for="" class="colleft form-lable">短信验证码</label>
                <input type="text" name="activeCode" maxlength="6" id="activeCode" style="width:90px" class="colright form-inpyt-sm"  placeholder="请输入验证码">
                 <p class="fl pdlr10 line32" id="sendActiveCode" ><span id="sendbtn"><a>发送验证码</a></span></p>
                 <p class="fl pdlr10 line32" id="clockActiveCode" style="display: none;"><span class="bule" id="clock">180</span><span>秒后重发</span></p>
            </div>
            <div class="form-col2">
            <button type="button" class="remove" onclick="closeMobileAppro();">取消</button><button type="button" class="enter" name="activebtn" id="activebtn">确定</button>
            </div>
            <c:if test="${flag==0}">
             <div class="form-col2">
               <div class="f14 gary2">
               <p><span class="orange2">*</span> 若您原手机号码丢失，请联系QQ客服或拨打客服 热线400-000-0000</p>
              </div>
            </div>
            </c:if>
         </div>
    </div> 
     <form action="" method="post" id="mobileform"> </form>  
<!--弹层end--->
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
			    		layer.alert(result.message);	
			    		window.location.href="${basePath}/AccountSafetyCentre/safetyIndex.html";
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
	$("#sendActiveCode").hide();
	$("#clockActiveCode").show();
	$('#sendbtn').attr("disabled","disabled");
	setTimeout(function(){
		runtime = runtime-1;
		$('#clock').html(runtime);
		if(runtime>0){
			$('#clock').html(runtime);
			runclock();
		}else{
			runtime=180;
			$('#clock').html(runtime);
			$("#sendActiveCode").show();
			$("#clockActiveCode").hide();
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
