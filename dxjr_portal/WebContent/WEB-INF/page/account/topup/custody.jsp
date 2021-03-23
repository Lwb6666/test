<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ include file="/WEB-INF/page/common/taglib.jsp" %>
<style>
    .tit_bk li {
        padding: 5px 0px;
    }
</style>
<form action="" name="topupform" id="topupform" method="post" target="_blank">
    <input type="hidden" name="fee" id="fee"/>
    <input id="bankcode" type="hidden" name="bankcode"/>
    <div class="tbl-cont">
        <div class="cash">
            <c:if test="${isCustody == true}">
            <p>
                <span class="cash-tit">存管账户余额</span><span class="ml20 f18 orange">
                <fmt:formatNumber value="${account.eUseMoney }" pattern="#,##0.00" />元</span>
            </p>
            <p><span class="cash-tit">浙商存管账户</span><span class="ml20 f18 orange">${bankInfo.custodyBindNo}</span></p>
            <p>
                <span class="cash-tit">充值金额</span><span class="ml20 relative">
                <input type="text" id="txtAmount" onblur="changeCzhkyye()"
                    style="width:150px; padding-right:20px; margin-top:-2px;">
                <span class="yuan gray9">元</span></span>
                <br><span class="red">（注意：目前存管账户只能投资存管标，暂不能使用红包！）
                <a class="tip-bottom" title="&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;您好！存管账户充值只能认购平台存管标,不能认购平台的定期宝、活期宝、普通散标及债权转让；<br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;投资红包仅限定期宝手动投宝使用，存管标的暂不支持使用红包；<br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;存管账户充值当天至下一个工作日10点前，充值资金仅用于投资，10点后可以进行提现，提现无手续费，实时到账。">查看详情</a></span>
            </p>
            <p><span class="cash-tit">手机号码</span><span class="ml20 f18">${bankInfo.securitymobile}</span></p>
            <p>
                <span class="cash-tit">手机验证码</span><span class="ml20 relative"><input
                    id="txtMobileCode" type="text" maxlength="6"></span><span
                    class="mll0 gray9"><a id="btnSend" class="btn-code">获取银行验证码</a></span>
            </p>
            <p style=" margin-left:160px; padding-bottom:50px;">
                <a id="btnTopup" href="javascript:;" class="btn btn-primary">立即充值</a>
            </p>
            </c:if>
            <c:if test="${isCustody == false}">
            <!--未开通存管账户时显示-->
            <p style="height:300px;line-height:300px; text-align:center">
                <span><i class="iconfont f16 gary"></i> 您尚未开通银行存管账户，开通存管账户更安全，</span><a
                    id="btnOpenAccount">立即开通</a>
            </p>
            </c:if>
        </div>
          <ul class="recharge-tip gray9 clearfix">
                    	<li class="clearfix f16">温馨提示</li>
                    	<li>1. 顶玺金融的投资用户通过存管账户进行充值，平台不收取手续费，用户单笔充值金额500元起</li>
                        <li>2. 存管账户充值只能认购平台存管标，不能认购平台的定期宝、活期宝、普通散标及债权转让</li>
                        <li>3. 投资红包仅限定期宝手动投宝使用，存管标的暂不支持使用红包</li>
                        <li>4. 存管账户充值当天至下一个工作日10点前，充值资金仅用于投资，10点后可以进行提现，提现无手续费，实时到账</li>
          </ul>
    </div>
</form>

<script type="text/javascript">
$(function(){
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
});
    if ('${isBlack}' == 'true') {
        window.parent.location.href = '${path}/myaccount/toIndex.html';
    }

    $(function () {
        $('#btnSend').on('click', getCode);
        $('#btnTopup').on('click', topup);
        $('#btnOpenAccount').on('click', function () {
            window.location.href = '${path }/AccountSafetyCentre/safetyIndex.html';
        });
    });

    /**
     * 获取验证码
     */
    function getCode() {
        $('#btnSend').unbind('click', getCode);
        //支付金额
        var txtAmount = $("#txtAmount").val();
        if (txtAmount == null || txtAmount == '') {
            layer.msg("请输入充值金额", 1, 5);
            $("#txtAmount").focus();
            $('#btnSend').bind('click', getCode);
            return false;
        }
        if (Number(txtAmount) <= 0) {
            layer.msg("充值金额必须大于0元！", 1, 5);
            $("#txtAmount").focus();
            $('#btnSend').bind('click', getCode);
            return false;
        }
        if (!checkFloat(txtAmount)) {
            layer.msg("充值金额格式不对,请输入正确的充值金额！", 1, 5);
            $("#txtAmount").focus();
            $('#btnSend').bind('click', getCode);
            return false;
        }
        var  _load = layer.load('处理中..');
        $.ajax({
            url : '${basePath}/account/czbank/sendMessage/' + 4 + '.html',
            type : 'post',
            dataType :"json",
            success : function(result) {
                layer.close(_load);
                if (result.code == '0') {
                    layer.msg(result.message, 1, 5);
                    $('#btnSend').bind('click', getCode);
                }else{
                    layer.msg('发送成功',1,1);
                    changeTime();
                }
            },
            error : function(result) {
                layer.close(_load);
                layer.msg('网络连接超时,请您稍后重试.', 1, 5);
                $('#btnSend').bind('click', getCode);
            }
        });
    }


    /**
     * 充值
     */
    function topup() {
        $('#btnTopup').unbind('click', topup);
        //支付金额
        var txtAmount = $("#txtAmount").val();
        var txtMobileCode = $("#txtMobileCode").val();
        if (txtAmount == null || txtAmount == '') {
            layer.msg("请输入充值金额", 1, 5);
            $("#txtAmount").focus();
            $('#btnTopup').bind('click', topup);
            return false;
        }
        if (Number(txtAmount) <= 0) {
            layer.msg("充值金额必须大于0元！", 1, 5);
            $("#txtAmount").focus();
            $('#btnTopup').bind('click', topup);
            return false;
        }
        if (!checkFloat(txtAmount)) {
            layer.msg("充值金额格式不对,请输入正确的充值金额！", 1, 5);
            $("#txtAmount").focus();
            $('#btnTopup').bind('click', topup);
            return false;
        }
        if (txtMobileCode == null || txtMobileCode == '') {
            layer.msg("请输入验证码", 1, 5);
            $("#txtMobileCode").focus();
            $('#btnTopup').bind('click', topup);
            return false;
        }
        var  _load = layer.load('处理中..');
        $.ajax({
            url : '${basePath}/account/czbank/topup.html',
            type : 'post',
            dataType :"json",
            data: {
                amount: txtAmount,
                mobileCode: txtMobileCode
            },
            success : function(result) {
                layer.close(_load);
                if (result.code == '0') {
                    layer.msg(result.message, 1, 5);
                    $('#btnTopup').bind('click', topup);
                }else{
                    window.open('${basePath}/account/czbank/topupSuccess.html', '_self');
                }
            },
            error : function(result) {
                layer.close(_load);
                layer.msg('网络连接超时,请您稍后重试.', 1, 5);
                $('#btnTopup').bind('click', topup);
            }
        });
    }

    var runtime=180;
    //定时刷新时间
    function changeTime(){
        runtime=180;
        runclock();
    }
    function runclock(){
        window.tt = setTimeout(function(){
            runtime = runtime-1;
            var $btnSend = $('#btnSend');
            if(runtime>0){
                $btnSend.html(runtime+'秒后重新获取');
                $btnSend.addClass("btn-codey").removeClass("btn-code");
                runclock();
            }else{
                runtime=180;
                $btnSend.html('获取验证码');
                $btnSend.bind('click', getCode);
                $btnSend.addClass("btn-code").removeClass("btn-codey");
            }
        },1000);
    }

    /**
     * 调整充值后可用余额
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
