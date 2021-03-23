<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:if test="${param.userLevel == 0 }">
 	<a href="javascript:;" style="cursor:text" title="普通会员"><img src="${param.path}/images/vip_0.gif" width="16" height="16" /></a>
 </c:if>
<c:if test="${param.userLevel == 10 }">
<a href="javascript:;" style="cursor:text"   title="金牌会员"><img src="${param.path}/images/vip_10.gif" width="16" height="16" /></a>
</c:if>
<c:if test="${param.userLevel == 20 }">
<a href="javascript:;"  style="cursor:text" title="白金会员"><img src="${param.path}/images/vip_20.gif" width="16" height="16" /></a>
</c:if>
<c:if test="${param.userLevel == 30 }">
<a href="javascript:;" style="cursor:text" title="钻石会员"><img src="${param.path}/images/vip_30.gif" width="16" height="16" /></a>
</c:if>
<c:if test="${param.userLevel == 40 }">
<a href="javascript:;" style="cursor:text" title="皇冠会员"><img src="${param.path}/images/vip_40.gif" width="16" height="16" /></a>
</c:if>
<c:if test="${param.userLevel == 50 }">
<a href="javascript:;" style="cursor:text" title="金皇冠会员"><img src="${param.path}/images/vip_50.gif" width="16" height="16" /></a>
</c:if>
<c:if test="${param.userLevel == 60 }">
<a href="javascript:;" style="cursor:text" title="终身顶级会员"><img src="${param.path}/images/vip_60.gif" width="16" height="16" /></a>
</c:if>
