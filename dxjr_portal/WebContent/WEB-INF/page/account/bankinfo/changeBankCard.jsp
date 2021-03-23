<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/page/common/taglib.jsp"%>
	<script type="text/javascript" src="${basePath}/js/location.js"></script>
    <script type="text/javascript" src="${path }/js/formValid.js?${version}"></script>
     <style type="text/css">
      .select {
	    line-height: 30px;
	    color: #333;
	    display: block;
	    text-align: left;
	}
    </style>
    <script type="text/javascript">
	$(function(){
		$('#tip-left').poshytip({
			className: 'tip-yellowsimple',
			showTimeout: 1,
			alignTo: 'target',
			alignX: 'center',
			alignY: 'bottom',
			offsetX: 15,
			offsetY: 10,
			allowTipHover: false,
		});
		$('.tip-bottom').poshytip({
			className: 'tip-yellowsimple',
			showTimeout: 1,
			alignTo: 'target',
			alignX: 'center',
			alignY: 'bottom',
			offsetX: 15,
			offsetY: 10,
			allowTipHover: false,
		});
		$(".close").click(function(){
		$(".bankcard-tip").fadeOut();
		});
		$(".close").click(function(){
		$(".bankcard-tip").fadeOut();
		});
	});
	</script>
	<form id="fm" name="fm" method="post" >
		<div class="cont-word">
    	<div class="title"><h4>解绑银行卡</h4><a onclick="closeUpDateBankCard();" class="icon icon-close fr"></a></div>
        <div class="form-info-layer">
        <div class="form-col2">
                <label for="" class="colleft form-lable">真实姓名</label>
                <input type="text"   class="colright form-inpyt-sm" id="realName" name="realName"  dataType="Require" msg="真实姓名不能为空" maxlength="20" style="width:198px" class="colright form-inpyt-sm" placeholder="请输入真实姓名">
            </div>
            <div class="form-col2">
                <label for="" class="colleft form-lable">身份证号</label>
                <input type="text"  class="colright form-inpyt-sm" id="idCard" name="idCard"  dataType="Require" msg="证件号码不能为空" maxlength="20" style="width:198px" class="colright form-inpyt-sm" placeholder="请输入身份号">
            </div>
            <div class="form-col2">
                <label for="" class="colleft form-lable">原银行卡号</label>
                <input type="text"  id="oldBankcard" name="oldBankcard"  dataType="Require" msg="原银行卡号不能为空" maxlength="20" style="width:198px" class="colright form-inpyt-sm" placeholder="请输入原银行卡号">
            </div>
            <div class="form-col2">
                <label for="" class="colleft form-lable">解绑原因</label>
                 <dl class="colright select" >
                	<select name="updateReason" id="updateReason" class="colright form-inpyt-sm" style="width: 208px;">
							<option value="原卡丢失">原卡丢失</option>
							<option value="原卡损坏">原卡损坏</option>
							<!-- <option value="卡号错误">卡号错误</option>
							<option value="其他原因">其他原因</option> -->
					</select>
                </dl>
            </div>
            <div class="form-col2">
                <label for="" class="colleft form-lable">备注</label>
                <textarea style="width:200px;height:80px;" name="remark" class="colright form-inpyt-sm" placeholder=""></textarea>
            </div>
            <div class="form-col2">
                <label for="" class="colleft form-lable">手机号</label>
                    <span class="fl mt5">${mobileAppro.securitymobileNum}<i class="iconfont orange2 tip-left"  id="tip-left"  title="请与您银行卡预留手机保持一致">&#xe608;</i></span>
                    <input type="hidden" id="phone" name="phone" style="width: 200px" dataType="Require" msg="手机号不能为空" maxlength="15" value="${mobileAppro.mobileNum }"/>
					<input type="hidden" id="bankPhone" name="bankPhone" style="width: 200px" dataType="Require" msg="手机号不能为空" maxlength="15" value="${mobileAppro.mobileNum }"/>
            </div>
            <div class="form-col2">
                <label for="" class="colleft form-lable">验证码</label>
                 <input type="text"  style="width:90px" id="activeCode" name="activeCode" dataType="Require" msg="手机验证码不能为空" maxlength="10" class="colright form-inpyt-sm"  placeholder="请输入验证码">
                 <p class="fl pdlr10 line32" id="sendActiveCode" ><span id="sendbtn"><a>发送验证码</a></span></p>
                 <p class="fl pdlr10 line32" id="clockActiveCode" style="display: none;"><span class="bule" id="clock">180</span><span>秒后重发</span></p>
            </div>
            <div class="form-col2">
               <div class="f14 gary2">
               <p><span class="orange2">*</span> 原卡正常使用情况下，不允许解绑银行卡</p>
               <p><span class="orange2">*</span> 解绑卡的次数不能超过10次</p>
   			   <p><span class="orange2">*</span> 解绑卡需等待客服审核，1-2工作日内处理或联系客服热线：400-000-0000</p>
   			   <p><span class="orange2">*</span>认证资料类型包括身份证，挂失证明等其他类型.上传最多不超过10份</p>
              </div>
            </div>
            <div class="form-col2">
            <button type="button" class="enter" id="btnSaveBank" name="btnSaveBank" onclick="saveBank();">下一步</button>
            </div>
         </div>
    </div> 
  </form>
<script type="text/javascript">
	$(function() {
		$('#sendbtn').bind('click', forsendmsg);
	});
	//发送短信
	function forsendmsg() {
	 	var phone = $("#phone").val();
		if(phone == ''){
			layer.msg("-手机号不能为空！\n");
			return;
		}
		$.post("${basePath}/bankinfo/sendMsg.html", {'phone' : phone}, function(result) {
			if (result.code == '0') {
				layer.msg(result.message);
			} else {
				layer.msg('短信发送成功', 1, 1);
				run();
			}
		});
	}
	var runtime = 0;
	function run() {
		runtime = 180;
		runclock();
	}
	function runclock() {
		$("#sendActiveCode").hide();
		$("#clockActiveCode").show();
		$('#sendbtn').attr("disabled", "disabled");
		setTimeout(function() {
			runtime = runtime - 1;
			$('#clock').html(runtime);
			if (runtime > 0) {
				runclock();
			} else {
				$('#clock').html('180');
				$("#sendActiveCode").show();
				$("#clockActiveCode").hide();
				$('#sendbtn').removeAttr("disabled");
				runtime = 180;
			}
		}, 1000);
	}

function saveBank() {
	if (Validator.Validate('fm',4)==false) return;
	$("#btnSaveBank").removeAttr("onclick");
	var _load = layer.load('处理中..');
	$("#fm").ajaxSubmit({
		url : '${path}/bankinfo/subChange.html',
		type : 'post',
		dataType :"json",
		success : function(result) {
			layer.close(_load);
			if (result.code == '0') {
				layer.alert(result.message);
				$("#btnSaveBank").attr("onclick","saveBank();");
			}else{
				closeUpDateBankCard();
				UpDateBankCard();
			}
		},error : function() {
			layer.msg('网络连接超时,请您稍后重试.', 1, 5);
	    }
	});
}
function UpDateBankCard(){
	$('#bindBank2').reveal({
	    animation: 'fade',                   
	    animationspeed: 300,                       
	    closeonbackgroundclick: false,            
	    dismissmodalclass: 'close-reveal-modal'    
	});
	$.ajax({
		url : '${basePath}/bankinfo/initUploadPics.html',
		data :{
		} ,
		type : 'post',
		dataType : 'html',
		success : function(data){
			$("#bindBank2").html(data);
		},
		error : function(data) {
			layer.msg("网络连接异常，请刷新页面或稍后重试！", 2, 5);
	   }
	});
}
//解绑银行卡
function closeUpDateBankCard1(){ 
	$('#bindBank2').trigger('reveal:close'); 
}
</script>
</html>