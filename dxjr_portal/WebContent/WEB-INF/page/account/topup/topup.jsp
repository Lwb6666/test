<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ include file="/WEB-INF/page/common/taglib.jsp" %>
<form action="" name="topupform" id="topupform" method="post" target="_blank">
    <input type="hidden" name="fee" id="fee"/>
   	<input id="bankcode" type="hidden" name="bankcode"/>
    <div class="tbl-cont" id="dress-size">
        <h1>选择充值方式</h1>
            <div class="content choosebox">
                <ul class="clearfix">
                    <li>
                        <a href="javascript:void(0);" class="size_radioToggle" value="chinabank" id="chinabank"><span class="value"><img
                                src="${basePath}/images/myaccount/blank/bank_04.png" alt=""/></span></a>
                    </li>
                    <li>
                        <a href="javascript:void(0);" class="size_radioToggle" value="lianlian"><span class="value"><img
                                src="${basePath}/images/blank/bank_09.png" alt=""/></span></a>
                    </li>
                    <li>
                        <a href="javascript:void(0);" class="size_radioToggle" value="sinapay"><span class="value"><img
                                src="${basePath}/images/blank/bank_10.png" alt=""/></span></a>
                    </li>
                    <li>
                        <a href="javascript:void(0);" class="size_radioToggle"  value="fuiou" ><span class="value"><img
                                src="${basePath}/images/blank/bank_11.png" alt=""/></span></a>
                    </li>
                     <c:if test="${shiroUser.isFinancialUser == 0}">
                      <li>
                        <a href="javascript:void(0);" class="size_radioToggle" value="alipay"><span class="value"><img
                                src="${basePath}/images/bank/zhifubao.png" alt=""/></span></a>
                    </li>
                    </c:if>
                </ul>
            </div>
        <h1>充值渠道</h1>
          <!-- 银行列表页面 -->
      <div  id="bankListDiv" ></div>
        <p>
           	 账户余额<span class="ml20 f18 orange"><fmt:formatNumber value="${account.useMoney }" pattern="#,##0.00" />元</span>
        </p>
        <p>
          	 充值金额<span class="ml20 relative"><input type="text" id="pay_money" name="money" style="width:150px; padding-right:20px; margin-top:-5px;" onblur="changeCzhkyye()"><span
             class="yuan gray9">元</span></span><span class="mll0 gray9">充值手续费:<label id="alipayTopupFeeSpan">0</label>元</span>
        </p>
         <p style="display:none">
          	 充值备注:<span class="ml20 relative"><input type="text" name="remark" id="unline_recharge_remark" autocomplete="off" style="width:150px; padding-right:20px; margin-top:-5px;">
        </p>
        <p style=" margin-left:115px; padding-bottom:50px;">
            <a href="javascript:submitForm();" id="btnTakeMoney" class="btn btn-primary" >充值</a>
        </p>
        <ul class="recharge-tip gray9 clearfix">
            <li class="mr10 f16">温馨提示</li>
            <li>1. 使用京东支付、连连支付、富友支付线上充值免费，所需费用由顶玺金融承担；使用新浪支付线上充值，收取千分之二的手续费<br>
                2. 充值完成后，请耐心等待页面跳转回到顶玺金融网站，请不要直接关闭窗口，如果充值金额没有到帐，请和我们的客服联系
                <br>
                3. 顶玺金融禁止信用卡套现、虚假交易等行为，一经发现将予以处罚，包括但不限于：限制收款、冻结账户、永久停止服务，并有可能影响相关信用记录
            </li>
        </ul>
    </div>
</form>
<script type="text/javascript">
var  RechargeStyle;
var  RechargeBank;
//充值单选效果  
$(function(){
	$('#chinabank').addClass('current');
	 toChinaBankFun();
	 RechargeStyle="chinabank";
	$('.choosebox li a').click(function(){
		var thisToggle = $(this).is('.size_radioToggle') ? $(this) : $(this).prev();
		var checkBox = thisToggle.prev();
		checkBox.trigger('click');
		$('.size_radioToggle').removeClass('current');
		thisToggle.addClass('current');
		RechargeStyle=thisToggle.attr("value");
		 if (RechargeStyle == 'chinabank') {
	            toChinaBankFun();
	        } else if (RechargeStyle == 'sinapay') {
	            toSinapayFun();
	        } else if (RechargeStyle == 'lianlian') {
	            toLianlianFun();
	        } else if (RechargeStyle == 'fuiou') {
	            toFuiouFun();
	        } else if (RechargeStyle == 'alipay') {
	            toAlipayFun();
	        }
	   });		
	});
    /**
     * 显示网银在线的银行页面
     */
    function toChinaBankFun() {
        $.post("${basePath}/account/topup/chinabank/toIndex.html", {}, function (data) {
            $("#bankListDiv").html(data);
        });
    }

    //显示新浪支付的银行页面
    function toSinapayFun() {
        $.post("${basePath}/account/topup/sinapay/toIndex.html", {}, function (data) {
            $("#bankListDiv").html(data);
        });
    }

    //显示连连支付的银行页面
    function toLianlianFun() {
        $.post("${basePath}/account/topup/lianlianpay/toIndex.html", {}, function (data) {
            $("#bankListDiv").html(data);
        });
    }

    function toFuiouFun() {
        $.post("${basePath}/account/topup/fuioupay/bankList.html", {}, function (data) {
            $("#bankListDiv").html(data);
        });
    }

    /**
     * 显示支付宝的信息.
     */
    function toAlipayFun() {
        var content = '<div class="val_tips red"></div>';
        $("#bankListDiv").html(content);
    }

     /**
     * 调整充值后可用余额
     */
      function changeCzhkyye() {
        $("#alipayTopupFeeSpan").html(0);
        var useMoney = '${account.useMoney }';
        var pay_money = $("#pay_money").val();
        if (!checkFloat(pay_money)) {
            return;
        }
        //保留两位小数
        $("#pay_money").val(Number(pay_money).toFixed(2));
        var czje = $("#pay_money").val();
        var czsxf = 0;//目前无充值手续费
        var onlinetypeValue = RechargeStyle;
        //if(onlinetypeValue == 'chinabank'){
        if (onlinetypeValue == 'sinapay') {
            //给出手续费提示信息
            var chinabankpayfee = 0.01;
            var chargefee = parseFloat(Number(czje)) / 1000 * 2;
            if (chargefee > chinabankpayfee) {
                chinabankpayfee = chargefee;
            }
            czsxf = chinabankpayfee;
            var czsxfString = czsxf + "";
            var czsxfIndex = czsxfString.indexOf(".");
            if (czsxfIndex > 0) {
                czsxfString = czsxfString.substring(0, czsxfIndex + 5);
                var czsxfthreeNum = czsxfString.substring(czsxfIndex + 3);
                if (null != czsxfthreeNum && czsxfthreeNum != "" && parseInt(czsxfthreeNum) > 0) {
                    czsxf = parseFloat(czsxfString.substring(0, czsxfIndex + 3)) + parseFloat(0.01);
                }
                czsxf = czsxf.toFixed(2);
            }

            $("#alipayTopupFeeSpan").html(czsxf);
        }
        $("#fee").val(czsxf);
        var czhkyye = parseFloat(useMoney) + parseFloat(czje) - parseFloat(czsxf);
        $("#czhkyye").html(formatMoney(Number(czhkyye).toFixed(2))); 
    } 
    /**
     * 提交充值
     */
    function submitForm() {
        changeCzhkyye(); 
        $("#btnTakeMoney").removeAttr("onclick");
        //验证提交表单的充值数据是否正确
        var result = validateTopupData();
        if (!result) {
            $("#btnTakeMoney").attr("onclick", "submitForm();");
            return;
        }
        //获得选中的支付方式
        var onlinetype = RechargeStyle;
        //网银支付
        if (onlinetype == 'chinabank') {
            $("#topupform").attr("action", "${basePath}/account/topup/chinabank/send.html");
        } else if (onlinetype == 'sinapay') {
            $("#topupform").attr("action", "${basePath}/account/topup/sinapay/send.html");
        } else if (onlinetype == 'lianlian') {
            $("#topupform").attr("action", "${basePath}/account/topup/lianlianpay/send.html");
        } else if (onlinetype == 'fuiou') {
            $("#topupform").attr("action", "${basePath}/account/topup/fuioupay/send.html");
        }
        //提交表单
        if (onlinetype == 'chinabank' || onlinetype == 'shengpay' || onlinetype == 'sinapay' || onlinetype == 'lianlian' || onlinetype == 'fuiou') {
            //if(onlinetype == 'chinabank'){
            if (onlinetype == 'sinapay') {
                var pay_money = $("#pay_money").val();
                //给出手续费提示信息
                var chinabankpayfee = 0.01;
                var chargefee = parseFloat(Number(pay_money)) / 1000 * 2;
                if (chargefee > chinabankpayfee) {
                    chinabankpayfee = chargefee;
                }
                var czsxfString = chinabankpayfee + "";
                var czsxfIndex = czsxfString.indexOf(".");
                if (czsxfIndex > 0) {
                    czsxfString = czsxfString.substring(0, czsxfIndex + 5);
                    var czsxfthreeNum = czsxfString.substring(czsxfIndex + 3);
                    if (null != czsxfthreeNum && czsxfthreeNum != "" && parseInt(czsxfthreeNum) > 0) {
                        chinabankpayfee = parseFloat(czsxfString.substring(0, czsxfIndex + 3)) + parseFloat(0.01);
                    }
                    chinabankpayfee = chinabankpayfee.toFixed(2);
                }

                if (layer.confirm("您本次充值的手续费为：" + chinabankpayfee + " 元，您确定要继续吗？\n新浪支付充值帐号，收取千分之二的充值手续费，最低0.01元", function () {
                            layer.closeAll();
                            $("#topupform").submit();
                            $("#btnTakeMoney").attr("onclick", "submitForm();");
                        }, function () {
                            $("#btnTakeMoney").attr("onclick", "submitForm();");
                        })); 
            } else {

                if (onlinetype == 'chinabank') {
                    //设置支付银行
                    $("#bankcode").val(RechargeBank);
                }
                $("#topupform").submit();
                $("#btnTakeMoney").attr("onclick", "submitForm();");
            }
        }
        if (onlinetype == 'alipay') {
            $("#topupform").ajaxSubmit({
                url: '${basePath}/account/topup/alipay/alipayRecharge.html',
                type: "POST",
                success: function (msg) {
                    if (msg == "success") {
                        layer.msg("充值申请提交成功,会在1-2个小时内进行审核处理！", 2, 1);
                        $("#pay_money").val('');
                        $("#unline_recharge_remark").val('');
                    } else {
                        if (msg == "-1") {
                            layer.msg('请行进行实名认证！', 2, 5, function () {
                                window.location.href = "${path}/AccountSafetyCentre/safetyIndex.html";
                            });
                        } else if (msg == "-2") {
                            layer.msg('请先进行邮箱认证！', 2, 5, function () {
                                window.location.href = "${path}/AccountSafetyCentre/safetyIndex.html";
                            });
                        } else if (msg == "-3") {
                            layer.msg('请先进行手机认证！', 2, 5, function () {
                                window.location.href = "${path}/AccountSafetyCentre/safetyIndex.html";
                            });
                        } else {
                            layer.msg(msg, 2, 5);
                        }
                    }
                    $("#btnTakeMoney").attr("onclick", "submitForm();");
                },
                error: function () {
                    $("#btnTakeMoney").attr("onclick", "submitForm();");
                    layer.msg("网络连接超时，请您稍后重试", 2, 5);
                }
            });
        }
    }
    /**
     * 验证提交表单的充值数据是否正确
     */
    function validateTopupData() {
        //获得选中的支付方式
        var onlinetype = RechargeStyle;
        //支付金额
        var pay_money = $("#pay_money").val();
        if (pay_money == null || pay_money == '') {
            layer.msg("请输入充值金额", 1, 5);
            $("#pay_money").focus();
            return false;
        }
        if (!checkFloat(pay_money)) {
            layer.msg("充值金额格式不对,请输入正确的充值金额！", 1, 5);
            $("#pay_money").focus();
            return false;
        }
        //如果是支付宝
        if (onlinetype == 'alipay') {
            return validateAlipayData();
            //新浪支付
        } else if (onlinetype == 'sinapay') {
            return validateSinapayData();
            //连连支付
        } else if (onlinetype == 'lianlian') {
            return validateLianlianData();
        } else if (onlinetype == 'fuiou') {
            return validateFuiouData();
            //网银在线
        } else if (onlinetype == 'chinabank') {
            return validateChinabankData();
        } else {
            return false;
        }
        return true;
    }

    /**
     * 验证支付宝支付提交的数据是否正确
     */
    function validateAlipayData() {
        //支付金额
        var pay_money = $("#pay_money").val();
        if (Number(pay_money) <= 0) {
            layer.msg("充值金额必须大于0元！", 1, 5);
            $("#pay_money").focus();
            return false;
        }
       var remark = $("#unline_recharge_remark").val();
        if (remark.length > 500) {
            layer.msg("充值备注长度不能超过500个字符！", 1, 5);
            $("#unline_recharge_remark").focus();
            return false;
        }
        return true;
    }
    /**
     * 验证新浪支付 的数据是否正确
     */
    function validateSinapayData() {
        var paybank = RechargeBank;
        if (paybank == null || paybank == '') {
            layer.msg("请选择需要支付的银行", 1, 5);
            $("#btnTakeMoney").attr("onclick", "submitForm();");
            return false;
        }
        //设置支付银行
        $("#bankcode").val(paybank);
        var pay_money = $("#pay_money").val();
        if (parseFloat(pay_money) < 0.01) {
            layer.msg("充值金额必须大于或等于0.01元", 1, 5);
            $("#btnTakeMoney").attr("onclick", "submitForm();");
            return false;
        }
        if (parseFloat(pay_money) > 1000000) {
            layer.msg("充值金额必须小于100万元", 1, 5);
            $("#btnTakeMoney").attr("onclick", "submitForm();");
            return false;
        }
        return true;
    }
    /**
     * 验证连连支付 的数据是否正确
     */
    function validateLianlianData() {
        var paybank = RechargeBank;
        if (paybank == null || paybank == '') {
            layer.msg("请选择需要支付的银行", 1, 5);
            $("#btnTakeMoney").attr("onclick", "submitForm();");
            return false;
        }
        //支付金额
        var pay_money = $("#pay_money").val();
        if (parseFloat(pay_money) < 2) {
            layer.msg("充值金额必须大于或等于2元", 1, 5);
            $("#btnTakeMoney").attr("onclick", "submitForm();");
            return false;
        }
        if (parseFloat(pay_money) > 1000000) {
            layer.msg("充值金额必须小于100万元", 1, 5);
            $("#btnTakeMoney").attr("onclick", "submitForm();");
            return false;
        }
        //设置支付银行
        $("#bankcode").val(paybank);
        return true;
    }

    function validateFuiouData() {
        var paybank =RechargeBank;
        if (paybank == null || paybank == '') {
            layer.msg("请选择需要支付的银行", 1, 5);
            $("#btnTakeMoney").attr("onclick", "submitForm();");
            return false;
        }
        //支付金额
        var pay_money = $("#pay_money").val();
        if (parseFloat(pay_money) < 0.01) {
            layer.msg("充值金额必须大于或等于2元", 1, 5);
            $("#btnTakeMoney").attr("onclick", "submitForm();");
            return false;
        }
        if (parseFloat(pay_money) > 1000000) {
            layer.msg("充值金额必须小于100万元", 1, 5);
            $("#btnTakeMoney").attr("onclick", "submitForm();");
            return false;
        }
        //设置支付银行
        $("#bankcode").val(paybank);
        return true;
    }

    /**
     * 验证网银在线 的数据是否正确
     */
    function validateChinabankData() {
        //支付金额
        var pay_money = $("#pay_money").val();
        var paybank = RechargeBank;
        if (paybank == null || paybank == '') {
            layer.msg("请选择需要支付的银行", 1, 5);
            $("#btnTakeMoney").attr("onclick", "submitForm();");
            return false;
        }
        if (parseFloat(pay_money) < 0.02) {
            layer.msg("充值金额必须大于或等于0.02元", 1, 5);
            $("#btnTakeMoney").attr("onclick", "submitForm();");
            return false;
        }
        return true;
    }
    /**
     * 判断充值金额是否为非负浮点数
     */
    function checkFloat(pay_money) {
        var re = /^(([0-9]+\.[0-9]*[1-9][0-9]*)|([0-9]*[1-9][0-9]*\.[0-9]+)|([0-9]*[1-9][0-9]*))$/;//判断浮点数为非负浮点数
        if (!re.test(pay_money)) {
            return false;
        } else {
            return true;
        }
    }

</script>
