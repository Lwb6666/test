<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/page/common/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ include file="/WEB-INF/page/common/public.jsp"%>
<title>邮箱验证成功_顶玺金融</title>
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
    <span>邮箱验证 </span>
  </div>
        <div id="menu_centert">
           <%@ include file="/WEB-INF/page/account/myAccount_leftMenu.jsp"%>  
               <div class="lb_waikuang border whitebg">
                   <div class="safetoptit">安全中心</div>    
                 <div class="modify safebox450">
                  <c:if test="${email == null }">
                 	<p style="color:red;">${result}</p>
                 </c:if>
                  <c:if test="${email != null }">
                 	<p>${email }邮箱验证成功</p>
                 </c:if>
                 <dl>
                <c:if test="${memberApproVo== null or memberApproVo.mobilePassed == null or memberApproVo.mobilePassed != 1 }">
                 <dd>手机还未绑定？<a href="${path }/account/approve/mobile/mobileforinsert.html" >去绑定</a>温馨提示：绑定手机方便您以后修改密码</dd>
                 </c:if>
                  <c:if test="${ memberApproVo.mobilePassed == 1 }">
                 <dd>手机已绑定！ 温馨提示：绑定邮箱方便您以后修改密码</dd>
                 </c:if>
                 </dl>
                 </div>
</div>
</div>
</div>
</div>
<%@ include file="/WEB-INF/page/common/footer.jsp" %>
</body>
</html>
