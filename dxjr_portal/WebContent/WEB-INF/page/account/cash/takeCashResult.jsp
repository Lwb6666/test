<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ include file="/WEB-INF/page/common/taglib.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <%@ include file="/WEB-INF/page/common/public4.jsp" %>
    <link href="${basePath}/css/404.css" rel="stylesheet" type="text/css"/>
    <title>提现成功_顶玺金融</title>
</head>

<body style="background:#f9f9f9;">
<!--头部开始-->
<%@ include file="/WEB-INF/page/common/header.jsp" %>
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
    <div class="recharge-suc"><!--table-->
        <p><i class="iconfont f100 blue">&#xe611;</i><span class="f16 orange ml20">恭喜您！提现成功啦！</span></p>
        <p><span class="mr20">返回：</span><a href="${basePath}/myaccount/toIndex.html">账户总览&nbsp;&nbsp;&nbsp;&nbsp;</a></p>
    </div>

    <%@ include file="/WEB-INF/page/common/footer.jsp" %>
</body>
</html>
