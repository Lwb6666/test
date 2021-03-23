<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"  %>
<%@ include file="/WEB-INF/page/common/taglib.jsp"%>
<style>
.tit_bk li{
padding:5px 0px;
}
</style>
<form action="" name="topupform" id="topupform" method="post" target="_blank">
	<input type="hidden" name="fee" id="fee"/>
	<input id="bankcode" type="hidden" name="bankcode"/>
   <div class="clear"></div>
   <div class="tixianmain whitebg">
     <div class="tit">
            <p>选择充值方式:</p>
            <ul class="tit_bk" id="ulTopupStyle" >
            	 <li><input name="onlinetype" type="radio" value="chinabank" checked="checked"/><img src="${basePath}/images/bank/wangyin.png"></li>
<%--                  <li><input name="onlinetype" type="radio" value="lianlian"  /><img src="${basePath}/images/bank/lllogo.png"></li>
 --%>                 <li><input name="onlinetype" type="radio" value="sinapay"  /><img src="${basePath}/images/bank/xinlang.png"></li>
                 <li><input name="onlinetype" type="radio" value="fuiou"  /><img src="${basePath}/images/bank/fuiou.png"></li>
                 <c:if test="${shiroUser.isFinancialUser == 0}">
                 <li><input name="onlinetype" type="radio" value="alipay" /><img src="${basePath}/images/bank/zhifubao.png"></li>
            	 </c:if>
            </ul>
      </div>
       <!-- 银行列表页面 -->
      <div class="value-added" id="bankListDiv" >
      </div>
      
      <!-- 输入充值信息 -->
      <div class="val_tab">
         <table>
            <tr>
                <th colspan="2">请输入您充值的金额</th>
              </tr>
            <tr>
                <td><span>真实姓名:</span>${realNameAppro.realName }</td>
                <td><span>充值金额:<font color="red">*</font></span>
                  <input type="text" id="pay_money" name="money" size="15" autocomplete="off"  maxlength="9" onblur="changeCzhkyye()"/></td>
              </tr>
            <tr>
              <td><span>账号余额:</span><fmt:formatNumber value="${account.useMoney }" pattern="#,##0.00" /></td>
              <td id="alipayTopupRemarkTd" style="display:none">
                  <span>充值备注:</span>
                  <input type="text" name="remark" id="unline_recharge_remark" autocomplete="off"/>
              </td>
              <td id="topupFeeTd">
              	  <span>充值费用:</span><label id="alipayTopupFeeSpan">0</label>
              </td>              
            </tr>
            <tr>
              <td><span>充值后可用余额:</span><label id="czhkyye"><fmt:formatNumber value="${account.useMoney }" pattern="#,##0.00" /></label></td>
               <td>
              </td>  
              <td id="topupEmptyTd">
              </td>              
            </tr>
         </table>
      </div>
      
     <div class="sf">
         <input class="safe_tixian" type="button" value="充值" onclick="submitForm();" id="btnTakeMoney" style="cursor:pointer;"/>
     </div>
   <hr class="hr clear" />
     <div class="tip">
         <h3>温馨提示：</h3>
            <p>1、理财用户使用网银在线、连连支付、富友支付线上充值免费，所需要的费用，由顶玺金融承担； </p>
            <p>2、充值完成后，请耐心等待页面跳转回到顶玺金融网站，请不要直接关闭窗口，如果充值金额没有到帐，请和我们的客服联系；  </p>
            <p>3、顶玺金融禁止信用卡套现、虚假交易等行为,一经发现将予以处罚,包括但不限于：限制收款、冻结账户、永久；</p>
            <p>4、使用新浪支付充值帐号，收取千分之二的充值手续费。（最低收取1分）</p>
            <p>5、中国工商银行用户建议使用IE浏览器进行充值。</p>
     </div>
   </div>
   <div class="clearfix"></div>
</form>

<script type="text/javascript">
$(function() {
	//toSinapayFun();
	toChinaBankFun();
	//toLianlianFun();
	//为充值渠道类型绑定点击事件 
	$("#ulTopupStyle").find("li").each(function(index,element){
		$(element).attr("onclick","displayRightBankPage(this)");
	});
});

/**
 * 根据选择的支付平台,显示相应的银行页面
 */
function displayRightBankPage(obj){
	//设置选中此li下的radio为选中
	$(obj).find("[type='radio']")[0].checked=true;
	$("#alipayTopupRemarkTd").hide();
	$("#topupFeeTd").hide();
	$("#topupEmptyTd").hide();
	$("#bankListDiv").removeClass("value-added");
	var onlinetypeValue = $('input:radio[name=onlinetype]:checked').val();
	if(onlinetypeValue == 'chinabank'){
		$("#topupFeeTd").show();
		$("#topupEmptyTd").show();
		$("#bankListDiv").addClass("value-added");
		toChinaBankFun();
	}else if(onlinetypeValue == 'sinapay'){
		$("#topupFeeTd").show();
		$("#topupEmptyTd").show();
		$("#bankListDiv").addClass("value-added");
		toSinapayFun();
	}else if(onlinetypeValue == 'lianlian'){
		$("#topupFeeTd").show();
		$("#topupEmptyTd").show();
		$("#bankListDiv").addClass("value-added");
		toLianlianFun();
	}else if(onlinetypeValue == 'fuiou'){
		$("#topupFeeTd").show();
		$("#topupEmptyTd").show();
		$("#bankListDiv").addClass("value-added");
		toFuiouFun();
	}else if(onlinetypeValue == 'alipay'){
		$("#bankListDiv").removeClass("value-added");
		toAlipayFun();
		$("#alipayTopupRemarkTd").show();
	}
}

/**
 * 显示网银在线的银行页面
 */
function toChinaBankFun(){
	$.post("${basePath}/account/topup/chinabank/toIndex.html", {
	}, function(data) {
		$("#bankListDiv").html(data);
	});
}

//显示新浪支付的银行页面
function toSinapayFun(){
	$.post("${basePath}/account/topup/sinapay/toIndex.html", {
	}, function(data) {
		$("#bankListDiv").html(data);
	});
}

//显示连连支付的银行页面
function toLianlianFun(){
	$.post("${basePath}/account/topup/lianlianpay/toIndex.html", {
	}, function(data) {
		$("#bankListDiv").html(data);
	});
}

function toFuiouFun(){
	$.post("${basePath}/account/topup/fuioupay/bankList.html", {
	}, function(data) {
		$("#bankListDiv").html(data);
	});
}

/**
 * 显示支付宝的信息.
 */
function toAlipayFun(){
	var content = '<div class="val_tips red"></div>';
	$("#bankListDiv").html(content);
}

/**
 * 调整充值后可用余额
 */
function changeCzhkyye(){
	$("#alipayTopupFeeSpan").html(0);
	var useMoney = '${account.useMoney }';
	var pay_money = $("#pay_money").val();
	if(!checkFloat(pay_money)){
		return;
	}
	//保留两位小数
	$("#pay_money").val(Number(pay_money).toFixed(2));
	var czje = $("#pay_money").val();
	var czsxf = 0;//目前无充值手续费
	var onlinetypeValue = $('input:radio[name=onlinetype]:checked').val();
	//if(onlinetypeValue == 'chinabank'){
	if(onlinetypeValue == 'sinapay'){
		//给出手续费提示信息
		var chinabankpayfee  = 0.01;
		var chargefee = parseFloat(Number(czje))/1000*2;
		if(chargefee>chinabankpayfee){
			chinabankpayfee = chargefee;
		}
		czsxf = chinabankpayfee;
		var czsxfString = czsxf+"";
		var czsxfIndex = czsxfString.indexOf(".");
		if(czsxfIndex>0){
			czsxfString = czsxfString.substring(0,czsxfIndex+5);
			var czsxfthreeNum = czsxfString.substring(czsxfIndex+3);
			if(null!=czsxfthreeNum && czsxfthreeNum!="" && parseInt(czsxfthreeNum)>0){
				czsxf = parseFloat(czsxfString.substring(0,czsxfIndex+3))+parseFloat(0.01);
			}
			czsxf = czsxf.toFixed(2);
		}
		
		$("#alipayTopupFeeSpan").html(czsxf);
	}
	$("#fee").val(czsxf);
	var czhkyye = parseFloat(useMoney)+parseFloat(czje)-parseFloat(czsxf);
	$("#czhkyye").html(formatMoney(Number(czhkyye).toFixed(2)));
}
/**
 * 提交充值
 */
function submitForm(){
	changeCzhkyye();
	$("#btnTakeMoney").removeAttr("onclick");
	//验证提交表单的充值数据是否正确
	var result = validateTopupData();
	if(!result){
		$("#btnTakeMoney").attr("onclick","submitForm();");
		return;
	}
	//获得选中的支付方式
	var onlinetype = $("input[name='onlinetype'][type='radio']:checked").val();
	//网银支付
	if(onlinetype == 'chinabank'){
		$("#topupform").attr("action","${basePath}/account/topup/chinabank/send.html");
	}else if(onlinetype == 'sinapay'){
		$("#topupform").attr("action","${basePath}/account/topup/sinapay/send.html");
	}else if(onlinetype == 'lianlian'){
		$("#topupform").attr("action","${basePath}/account/topup/lianlianpay/send.html");
	}else if(onlinetype == 'fuiou'){
		$("#topupform").attr("action","${basePath}/account/topup/fuioupay/send.html");
	}
	//提交表单
	if(onlinetype == 'chinabank' || onlinetype == 'shengpay' || onlinetype == 'sinapay' || onlinetype == 'lianlian'|| onlinetype == 'fuiou' ){
		//if(onlinetype == 'chinabank'){
		if(onlinetype == 'sinapay'){
			var pay_money = $("#pay_money").val();
			//给出手续费提示信息
			var chinabankpayfee  = 0.01;
			var chargefee = parseFloat(Number(pay_money))/1000*2;
			if(chargefee>chinabankpayfee){
				chinabankpayfee = chargefee;
			}
			var czsxfString = chinabankpayfee+"";
			var czsxfIndex = czsxfString.indexOf(".");
			if(czsxfIndex>0){			
				czsxfString = czsxfString.substring(0,czsxfIndex+5);
				var czsxfthreeNum = czsxfString.substring(czsxfIndex+3);
				if(null!=czsxfthreeNum && czsxfthreeNum!="" && parseInt(czsxfthreeNum)>0){
					chinabankpayfee = parseFloat(czsxfString.substring(0,czsxfIndex+3))+parseFloat(0.01);
				}
				chinabankpayfee = chinabankpayfee.toFixed(2);
			}
			
			if(layer.confirm("您本次充值的手续费为："+chinabankpayfee+" 元，您确定要继续吗？\n新浪支付充值帐号，收取千分之二的充值手续费，最低0.01元",function(){
				layer.closeAll();
				$("#topupform").submit();
				$("#btnTakeMoney").attr("onclick","submitForm();");
			},function (){
				$("#btnTakeMoney").attr("onclick","submitForm();");
			}));
		}else{
			
		 if (onlinetype == 'chinabank') {
			//设置支付银行
			 $("#bankcode").val($("#chinabankSelectedUl").find("[name='chinabankcode']:checked").val());
		 }
			
			$("#topupform").submit();
			$("#btnTakeMoney").attr("onclick","submitForm();");
		}
	}
    if(onlinetype == 'alipay'){
		$("#topupform").ajaxSubmit({
		    url: '${basePath}/account/topup/alipay/alipayRecharge.html',
		    type: "POST",
		    success:function(msg){
		    	if(msg=="success"){
		    		layer.msg("充值申请提交成功,会在1-2个小时内进行审核处理！", 2,1);
					$("#pay_money").val('');
					$("#unline_recharge_remark").val('');
		    	}else{
		    		if(msg=="-1"){
		    			layer.msg('请行进行实名认证！',2,5,function(){
		    				window.location.href="${path}/account/approve/realname/toRealNameAppro.html";
		    			});
		    		}else if(msg=="-2"){
		    			layer.msg('请先进行邮箱认证！',2,5,function(){
		    				window.location.href="${path }/account/approve/email.html";
		    			});
		    		}else if(msg=="-3"){
		    			layer.msg('请先进行手机认证！',2,5,function(){
		    				window.location.href="${path }/account/approve/mobile/mobileforinsert.html";
		    			});
		    		}else{
		    			layer.msg(msg, 2,5);
		    		}
		    	}
				$("#btnTakeMoney").attr("onclick","submitForm();");
		    },
			error : function() {
				$("#btnTakeMoney").attr("onclick","submitForm();");
				layer.msg("网络连接超时，请您稍后重试", 2, 5);
		    } 
		 });
	 }
}
/**
 * 验证提交表单的充值数据是否正确
 */
function validateTopupData(){
	//获得选中的支付方式
	var onlinetype = $("input[name='onlinetype'][type='radio']:checked").val();
	//支付金额	
	var pay_money = $("#pay_money").val();
	if(pay_money == null || pay_money == ''){
		layer.msg("请输入充值金额", 1, 5);
		$("#pay_money").focus();
		return false;
	}
	if(!checkFloat(pay_money)){
		layer.msg("充值金额格式不对,请输入正确的充值金额！", 1, 5);
		$("#pay_money").focus();
		return false;
	}
	//如果是支付宝
	if(onlinetype == 'alipay'){
		return validateAlipayData();
		//新浪支付
	}else if(onlinetype == 'sinapay'){
		return validateSinapayData();
		//连连支付
	}else if(onlinetype == 'lianlian'){
		return validateLianlianData();
	}else if(onlinetype == 'fuiou'){
		return validateFuiouData();
		//网银在线
	}else if(onlinetype == 'chinabank'){
		return validateChinabankData();
	}else{
		return false;
	}
	return true;
}

/**
 * 验证支付宝支付提交的数据是否正确
 */
function validateAlipayData(){
	//支付金额	
	var pay_money = $("#pay_money").val();
	if(Number(pay_money) <= 0){
		layer.msg("充值金额必须大于0元！", 1, 5);
		$("#pay_money").focus();
		return false;
	}
	var remark = $("#unline_recharge_remark").val();
	if(remark.length > 500){
		layer.msg("充值备注长度不能超过500个字符！", 1, 5);
		$("#unline_recharge_remark").focus();
		return false;
	}
	return true;
}
/**
 * 验证新浪支付 的数据是否正确
 */
function validateSinapayData(){
	var paybank = $(".sinapayUl").find("[name='paybank']:checked").val();
	if(paybank == null || paybank == ''){
		layer.msg("请选择需要支付的银行", 1, 5);
		$("#btnTakeMoney").attr("onclick","submitForm();");
		return false;
	}
	//设置支付银行
	$("#bankcode").val(paybank);
	var pay_money = $("#pay_money").val();
	if(parseFloat(pay_money) < 0.01){
		layer.msg("充值金额必须大于或等于0.01元", 1, 5);
		$("#btnTakeMoney").attr("onclick","submitForm();");
		return false;
	}
	if(parseFloat(pay_money)>1000000){
		layer.msg("充值金额必须小于100万元", 1, 5);
		$("#btnTakeMoney").attr("onclick","submitForm();");
		return false;
	}
	return true;
}
/**
 * 验证连连支付 的数据是否正确
 */
function validateLianlianData(){
	var paybank = $(".llpayUl").find("[name='paybank']:checked").val();
	if(paybank == null || paybank == ''){
		layer.msg("请选择需要支付的银行", 1, 5);
		$("#btnTakeMoney").attr("onclick","submitForm();");
		return false;
	}
	//支付金额	
	var pay_money = $("#pay_money").val();
	if(parseFloat(pay_money) < 2){
		layer.msg("充值金额必须大于或等于2元", 1, 5);
		$("#btnTakeMoney").attr("onclick","submitForm();");
		return false;
	}
	if(parseFloat(pay_money)>1000000){
		layer.msg("充值金额必须小于100万元", 1, 5);
		$("#btnTakeMoney").attr("onclick","submitForm();");
		return false;
	}
	//设置支付银行
	$("#bankcode").val(paybank);
	return true;
}

function validateFuiouData(){
	var paybank = $(".fupayUl").find("[name='paybank']:checked").val();
	if(paybank == null || paybank == ''){
		layer.msg("请选择需要支付的银行", 1, 5);
		$("#btnTakeMoney").attr("onclick","submitForm();");
		return false;
	}
	//支付金额	
	var pay_money = $("#pay_money").val();
	if(parseFloat(pay_money) < 0.01){
		layer.msg("充值金额必须大于或等于2元", 1, 5);
		$("#btnTakeMoney").attr("onclick","submitForm();");
		return false;
	}
	if(parseFloat(pay_money)>1000000){
		layer.msg("充值金额必须小于100万元", 1, 5);
		$("#btnTakeMoney").attr("onclick","submitForm();");
		return false;
	}
	//设置支付银行
	$("#bankcode").val(paybank);
	return true;
}

/**
 * 验证网银在线 的数据是否正确
 */
function validateChinabankData(){
	//支付金额	
	var pay_money = $("#pay_money").val();
	var paybank = $(".chinabankUl").find("[name='paybank']:checked").val();
	if(paybank == null || paybank == ''){
		layer.msg("请选择需要支付的银行", 1, 5);
		$("#btnTakeMoney").attr("onclick","submitForm();");
		return false;
	}
	if(parseFloat(pay_money)<0.02){
		layer.msg("充值金额必须大于或等于0.02元", 1, 5);
		$("#btnTakeMoney").attr("onclick","submitForm();");
		return false;
	}
	return true;
}
/**
 * 判断充值金额是否为非负浮点数
 */
function checkFloat(pay_money){
	var re = /^(([0-9]+\.[0-9]*[1-9][0-9]*)|([0-9]*[1-9][0-9]*\.[0-9]+)|([0-9]*[1-9][0-9]*))$/;//判断浮点数为非负浮点数 
    if (!re.test(pay_money))
    {
        return false;
    }else{
    	return true;
    }
}
</script>
