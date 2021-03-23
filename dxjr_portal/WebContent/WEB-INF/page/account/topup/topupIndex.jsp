<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ include file="/WEB-INF/page/common/taglib.jsp" %>
<!doctype html>
<html>
<head>
    <%@ include file="/WEB-INF/page/common/public4.jsp" %>
    <title>我的帐户_充值_顶玺金融</title>
</head>

<body>
<!--头部开始-->
<%@ include file="/WEB-INF/page/common/header.jsp" %>
<!--头部结束-->
<div class="clear"></div>
<!--头部结束-->
<div id="myaccount">
    <div class="topmenu">
        <ul>
            <li class="active"><a href="${basePath}/myaccount/toIndex.html">账户总览</a></li>
            <li><a href="${basePath}/dingqibao/myAccount.html">投资管理</a></li>
            <li><a href="${basePath}/lottery_use_record/lott_use_record.html">账户奖励</a></li>
            <li><a href="${path }/AccountSafetyCentre/safetyIndex.html">安全中心</a></li>
        </ul>
    </div>
    <div class="wraper mt20">
        <!--table-->
        <div class="prduct-menu">
            <div class="menu-tbl">
                <ul class="col3">
                    <li class="active" id="linkRecharge" onclick="loadRechargeInner(this)">非存管充值 </li>
                    <c:if test="${cardType == null or cardType == '' or cardType == 1}">
                        <li id="linkCustody" onclick="loadCustodyInner(this)">存管充值</li>
                    </c:if>
                    <li id="rechargeRecord" onclick="javascript:rechargeRecord();">充值记录</li>
                </ul>
            </div>
            <div id="main_content" class="recharge change" style="clear:both">
            </div>
        </div>
    </div>
</div>

<%@ include file="/WEB-INF/page/common/footer.jsp" %>
</body>
<script type="text/javascript">
    $(function () {
        var errorCode = '${errorCode}';
        if (errorCode != '') {
            if (errorCode == "-1") {
                layer.msg('请先进行手机或邮箱认证！', 2, 5, function () {
                    window.location.href = "${path}/AccountSafetyCentre/safetyIndex.html";
                });
            } else if (errorCode == "-2") {
                layer.msg('请先进行实名认证！', 2, 5, function () {
                	 window.location.href = "${path}/AccountSafetyCentre/safetyIndex.html";
                });
            } else if (errorCode == "-3") {
                layer.msg('请先添加银行卡！', 2, 5, function () {
                	 window.location.href = "${path}/AccountSafetyCentre/safetyIndex.html";
                });
            }else if(errorCode=="-4"){
            	 layer.alert('您的银行卡正在审核中，请耐心等待！客服会在两个工作日内处理或联系客服热线400-000-0000');
            	 //加载充值页面
                 loadRechargeInner(document.getElementById("linkRecharge"));
    		}else if(errorCode=="-5"){
    			 layer.alert('您的银行卡未通过审核，请7天后重新申请或联系客服热线400-000-0000');
            	 //加载充值页面
                 loadRechargeInner(document.getElementById("linkRecharge"));
    		} 
            return;
        }
        if('${tip}' == "linkRecharge"){
        	cssDiv();
        	$("#linkRecharge").addClass("active");
        	//加载非存管充值
            loadRechargeInner(document.getElementById("linkRecharge"));
        }else if('${tip}' == "linkCustody"){
        	cssDiv();
        	$("#linkCustody").addClass("active");
        	//加载存管充值
            loadCustodyInner(document.getElementById("linkCustody"));
        }else{
        	cssDiv();
        	$("#linkRecharge").addClass("active");
        	//加载非存管充值
            loadRechargeInner(document.getElementById("linkRecharge"));
        }
        
    });
    /**
     * 充值页面加载
     */
    function loadRechargeInner(obj) {
        $.ajax({
            url: '${basePath}/account/topup/toTopupInner.html',
            type: 'post',
            success: function (data) {
                $("#main_content").html(data);
            },
            error: function (data) {
                layer.msg("网络连接异常，请刷新页面或稍后重试！", 1, 5);
            }
        });
    }
    /**
     * 资金存管充值页面加载
     */
    function loadCustodyInner(obj) {
        window.clearTimeout(window.tt);
        $.ajax({
            url: '${basePath}/account/topup/toCustodyInner.html',
            type: 'post',
            success: function (data) {
                $("#main_content").html(data);
            },
            error: function (data) {
                layer.msg("网络连接异常，请刷新页面或稍后重试！", 1, 5);
            }
        });
    }
    /**
     * 默认充值记录查询
     */
    function rechargeRecord() {
        $.ajax({
            url: '${basePath}/account/topup/toRechargeRecordInnerMain.html',
            type: 'post',
            dataType: 'text',
            success: function (data) {
                $("#main_content").html(data);
            },
            error: function (data) {
                layer.msg("网络连接异常，请刷新页面或稍后重试！", 1, 5);  // 弹出框
            }
        });
    }
    
    function cssDiv(){
    	$("#linkRecharge").removeClass("active");
    	$("#linkCustody").removeClass("active");
    	$("#rechargeRecord").removeClass("active");
    }
</script>
</html>
