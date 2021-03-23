<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ include file="/WEB-INF/page/common/taglib.jsp" %>

<!-- 提现 -->
<div class="tbl-cont" id="dress-size">
    <div class="cash">
        <c:if test="${isCustody == true}">
            <p><span class="cash-tit">提现至存管账户</span><span class="f18 orange">${bankInfo.custodyBindNo}</span></p>
            <p><span class="cash-tit">存管账户余额</span><span class="f18 orange"><fmt:formatNumber value="${account.eUseMoney }" pattern="#,##0.00"/>元</span></p>
            <p><span class="cash-tit">可提金额</span><span class="f18 orange"><fmt:formatNumber value="${withdrawMoney }" pattern="#,##0.00"/>元</span></p>
            <p><span class="cash-tit">提现金额</span><span class=""><input type="text" id="txtAmount" onblur="changeCzhkyye()" maxlength="12"
                       style="width:150px; padding-right:20px; margin-top:-2px;">
                <span class="yuan gray9">元</span></span></p>
            <p><span class="cash-tit">顶玺金融交易密码</span><span><c:if test="${null==nosetPaypassword }"><input type="password" name="paypassword" id="paypassword" maxlength="12"/></c:if>
			 </span><a href="${path}/AccountSafetyCentre/safetyIndex.html" class="ml20 blue">忘记密码？</a></p>

            <p><span class="cash-tit">手机号码</span><span class="f18">${bankInfo.securitymobile}</span></p>
            <p><span class="cash-tit">手机验证码</span><span><input
                    id="txtMobileCode" type="text" maxlength="6"></span><span
                    class="mll0 gray9"><a id="btnSend" class="btn-code">获取银行验证码</a></span>
            </p>
            <p style=" margin-left:160px; padding-bottom:50px;"><a id="btnTakeCash" class="btn btn-primary">立即提现</a></p>
        </c:if>

        <c:if test="${isCustody == false}">
            <!--未开通存管账户时显示-->
            <p style="height:300px;line-height:300px; text-align:center"><span> 您尚未开通银行存管账户，开通存管账户更安全，</span><a
                    id="btnOpenAccount">立即开通</a>
            </p>
        </c:if>
    </div>
    <ul class="recharge-tip gray9 clearfix">
        <li class="f16 clearfix">温馨提示</li>
        <li>顶玺金融的投资用户通过存管账户进行提现，平台不收取手续费，每日申请的提现金额不限，单笔申请最高限额为50000元。</li>
    </ul>
</div>
<script type="text/javascript">

    var msg = '${msg}';
    if (msg != null && msg != '') {
        layer.msg(msg, 1, 5);
    }

    if ('${isBlack}' == 'true') {
        window.parent.location.href = '${path}/myaccount/toIndex.html';
    }

    $(function () {
        $('#btnSend').on('click', getCode);
        $('#btnTakeCash').on('click', takeCash);
        $('#btnOpenAccount').on('click', function () {
            window.location.href = '${path }/AccountSafetyCentre/safetyIndex.html';
        });
    });

    /**
     * 获取验证码
     */
    function getCode() {
        var $btnSend = $('#btnSend');
        $btnSend.unbind('click', getCode);
        //支付金额
        var $txtAmount = $('#txtAmount');
        var txtAmount = $txtAmount.val();
        if (txtAmount == null || txtAmount == '') {
            layer.msg("请输入提现金额", 1, 5);
            $txtAmount.focus();
            $btnSend.bind('click', getCode);
            return false;
        }
        var $paypassword = $('#paypassword');
        var paypassword = $paypassword.val();
        if (paypassword == null || paypassword == '') {
            layer.msg("请输入交易密码", 1, 5);
            $paypassword.focus();
            $btnSend.bind('click', getCode);
            return false;
        }
        if (Number(txtAmount) <= 0) {
            layer.msg("提现金额必须大于0元！", 1, 5);
            $("#txtAmount").focus();
            $btnSend.bind('click', getCode);
            return false;
        }
        if (!checkFloat(txtAmount)) {
            layer.msg("提现金额格式不对,请输入正确的提现金额！", 1, 5);
            $("#txtAmount").focus();
            $btnSend.bind('click', getCode);
            return false;
        }
        if (Number(txtAmount) > Number('${withdrawMoney}')) {
            layer.msg("提现金额不能大于可提金额！", 1, 5);
            $("#txtAmount").focus();
            $btnSend.bind('click', getCode);
            return false;
        }
        var _load = layer.load('处理中..');
        $.ajax({
            url: '${basePath}/account/czbank/sendMessage/' + 5 + '.html',
            type: 'post',
            dataType: "json",
            success: function (result) {
                layer.close(_load);
                if (result.code == '0') {
                    layer.msg(result.message, 1, 5);
                    $btnSend.bind('click', getCode);
                } else {
                    layer.msg('发送成功', 1, 1);
                    changeTime();
                }
            },
            error: function (result) {
                layer.close(_load);
                layer.msg('网络连接超时,请您稍后重试.', 1, 5);
                $btnSend.bind('click', getCode);
            }
        });
    }


    /**
     * 提现
     */
    function takeCash() {
        $('#btnTakeCash').unbind('click', takeCash);
        //支付金额
        var txtAmount = $("#txtAmount").val();
        var txtMobileCode = $("#txtMobileCode").val();
        if (txtAmount == null || txtAmount == '') {
            layer.msg("请输入提现金额", 1, 5);
            $("#txtAmount").focus();
            $('#btnTakeCash').bind('click', takeCash);
            return false;
        }
        if (Number(txtAmount) <= 0) {
            layer.msg("提现金额必须大于0元！", 1, 5);
            $("#txtAmount").focus();
            $('#btnTakeCash').bind('click', takeCash);
            return false;
        }
        if (!checkFloat(txtAmount)) {
            layer.msg("提现金额格式不对,请输入正确的提现金额！", 1, 5);
            $("#txtAmount").focus();
            $('#btnTakeCash').bind('click', takeCash);
            return false;
        }
        if (Number(txtAmount) > Number('${withdrawMoney}')) {
            layer.msg("提现金额不能大于可提金额！", 1, 5);
            $("#txtAmount").focus();
            $('#btnTakeCash').bind('click', takeCash);
            return false;
        }
        var $paypassword = $('#paypassword');
        var paypassword = $paypassword.val();
        if (paypassword == null || paypassword == '') {
            layer.msg("请输入交易密码", 1, 5);
            $paypassword.focus();
            $('#btnTakeCash').bind('click', takeCash);
            return false;
        }
        if (txtMobileCode == null || txtMobileCode == '') {
            layer.msg("请输入验证码", 1, 5);
            $("#txtMobileCode").focus();
            $('#btnTakeCash').bind('click', takeCash);
            return false;
        }
        var _load = layer.load('处理中..');
        $.ajax({
            url: '${basePath}/account/czbank/saveTakeCash.html',
            type: 'post',
            dataType: "json",
            data: {
                amount: txtAmount,
                mobileCode: txtMobileCode,
                paypassword: paypassword
            },
            success: function (result) {
                layer.close(_load);
                if (result.code == '0') {
                    layer.msg(result.message, 1, 5);
                    $('#btnTakeCash').bind('click', takeCash);
                } else {
                    window.open('${basePath}/account/czbank/takeCashSuccess.html', '_self');
                }
            },
            error: function (result) {
                layer.close(_load);
                layer.msg('网络连接超时,请您稍后重试.', 1, 5);
                $('#btnTakeCash').bind('click', takeCash);
            }
        });
    }

    var runtime = 180;
    //定时刷新时间
    function changeTime() {
        runtime = 180;
        runclock();
    }
    function runclock() {
        window.tt = setTimeout(function () {
            runtime = runtime - 1;
            var $btnSend = $('#btnSend');
            if (runtime > 0) {
                $btnSend.html(runtime + '秒后重新获取');
                $btnSend.addClass("btn-codey").removeClass("btn-code");
                runclock();
            } else {
                runtime = 180;
                $btnSend.html('获取验证码');
                $btnSend.bind('click', getCode);
                $btnSend.addClass("btn-code").removeClass("btn-codey");
            }
        }, 1000);
    }

    /**
     * 调整提现后可用余额
     */
    function changeCzhkyye() {
        $txtAmount = $('#txtAmount');
        var txtAmount = $txtAmount.val();
        if (!checkFloat(txtAmount)) {
            return;
        }
        //保留两位小数
        $txtAmount.val(Number(txtAmount).toFixed(2));
    }
</script>
