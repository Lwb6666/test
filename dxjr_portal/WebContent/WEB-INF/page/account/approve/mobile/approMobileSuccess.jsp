<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/page/common/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ include file="/WEB-INF/page/common/public.jsp"%>
<title>手机验证成功_顶玺金融</title>
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
    <span>手机绑定 </span>
  </div>
        <div id="menu_centert">
           <%@ include file="/WEB-INF/page/account/myAccount_leftMenu.jsp"%>  
               <div class="lb_waikuang border whitebg">
                   <div class="safetoptit">安全中心</div>    
                 <div class="modify safebox450">
                 <p>手机号${mobile }绑定成功</p>
                 <dl>
                 <c:if test="${memberApproVo== null or memberApproVo.emailChecked == null or memberApproVo.emailChecked != 1 }">
                 <dd>邮箱还未验证？<a href="${path }/account/approve/email.html">去绑定</a>温馨提示：绑定邮箱方便您以后修改密码</dd>
                 </c:if>
                 </dl>
                 </div>

</div>
           <!--我的账户左侧 结束 -->
     
<!--我的账户右侧开始 -->             
               
</div>
</div>
</div>
<%@ include file="/WEB-INF/page/common/footer.jsp" %>

</body>
</html>
