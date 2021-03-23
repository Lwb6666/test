
<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/page/common/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ include file="/WEB-INF/page/common/public.jsp"%>
<title>注册成功_顶玺金融</title>
<link href="${basePath}/css/login.css" rel="stylesheet" type="text/css" />
</head>

<body>
<!--头部开始-->
	<%@ include file="/WEB-INF/page/common/header.jsp"%>
	<!--头部结束-->
<!--内容开始-->
     <div id="Bmain">
       <div class="login_main loginbg loginboxm">
                   <div class="reg-steps">
                    <ol class="reg-step-3 step-3">
                        <li class="step-1"><span class="txt"><i class="cz_icons  dot1">1</i>填写账户信息</span></li>
                        <li class="step-2"><span class="txt"><i class="cz_icons  dot1">2</i>验证账户信息</span></li>
                        <li class="step-3"><span class="txt"><i class="cz_icons  dot1">3</i>注册成功</span></li>
                    </ol>    
                </div>
                <c:if test="${(portal:currentUser()) != null }">
                 <div class="reg-info">已登录！</div>
                </c:if>
                <c:if test="${(portal:currentUser()) == null }">
                 <div class="reg-info">已有账号，<a href="${path}/member/toLoginPage.html">立即登录</a></div>
                </c:if>
               
                
                    
                                        <div class="right_container"> 
                                          <div class="right_content">
                                            <div class="loginbox_form login_top loginbox_width">
                                              <p class="login_msg"><img src="${basePath }/images/tu_03.png" width="52" height="52"/>
                                             ${ userName == null ?"激活失败":userName} 
                                              </p>
                                            </div>
                                            <div class="loginbox_form login_top loginbox_width">
                                            </div>
                                          </div>
                                                 
           </div>
        </div>
     
     </div>
                 
                  
                            
                            
                            
           
           <!--内容结束-->

    
	<div>
		<%@ include file="/WEB-INF/page/common/footer.jsp"%>
	</div>

</body>
<script type="text/javascript">
/**
 * 进入充值页面
 */
function topupFunction(){
	window.location.href = "${path}/account/topup/toTopupIndex.html";
}
</script>
</html>
