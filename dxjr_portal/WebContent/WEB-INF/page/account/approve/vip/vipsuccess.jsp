<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/page/common/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ include file="/WEB-INF/page/common/public.jsp"%>
<title>VIP认证成功_顶玺金融</title>
</head>

<body style="background:#f9f9f9;">
<!--头部开始-->
<%@ include file="/WEB-INF/page/common/header.jsp" %>
<!--头部结束-->

<!--安全中心左侧开始-->
<div id="Container">
<div id="Bmain">
    <div class="title">
    <span class="home"><a href="${path}/">顶玺金融</a></span>
    <span><a href="${path }/myaccount/toIndex.html">我的账户</a></span>
    <span>账户管理</span>
    <span><a href="${path }/AccountSafetyCentre/safetyIndex.html">安全中心</a></span>
    <span>VIP认证 </span>
  	</div>
	<!--安全中心左侧 开始 -->    
	<%@ include file="/WEB-INF/page/account/myAccount_leftMenu.jsp"%> 
	<!--安全中心左侧 结束 -->    
	<!--安全中心右侧开始 --> 
	<div class="lb_waikuang border whitebg">
        <div class="safetoptit">安全中心</div>    
  		<div class="modify safebox450">
		    <p>VIP申请成功</p>
		    <dl>
		    <dd><div class="tipone" style=" padding-left:0 !important;"><span>温馨提示：</span>${indateTip}</div></dd>
           </dl>
        </div>

	</div>            
	<!--安全中心右侧结束 -->
</div>
</div>
<%@ include file="/WEB-INF/page/common/footer.jsp" %>
</body>
<script type="text/javascript">

</script>
</html>
