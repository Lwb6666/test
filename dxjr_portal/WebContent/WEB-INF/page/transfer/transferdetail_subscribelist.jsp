<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/page/common/taglib.jsp"%>
<%@ include file="/WEB-INF/page/common/public2.jsp"%>
 
<table width="100%" border="0" cellspacing="0" cellpadding="0" class="table center tbtr02">
     <tr class="tbl-title">
       <td>投标人</td>
       <td style="text-align: right;">投标金额</td>
       <td>交易时间</td>
     </tr>
     <c:forEach   var="subscribeVo"  items="${page.result}"   varStatus="index">
     <tr>
        <c:choose>
               <c:when test="${subscribeVo.platform==3 || subscribeVo.platform==4}">
		           <td class="posi-r" style="overflow:visible;">
		            <a class="listtishi" style="padding-left: 30px;" <c:if test="${portal:currentUser() != null and  portal:currentUser().userName eq subscribeVo.userName}">style="color:blue;"</c:if> 
			       href="${path}/accountdetail/show.html?userName=${portal:encode(portal:encode(subscribeVo.userNameEncrypt))}" target="_blank">
			         <img src="${basePath}/images/v5/phone.png"/>
		                   <div class="displaywm" style="z-index:9999;left: -28px;" ><img src="${basePath}/images/v5/erweima.png"/></div>
			       ${portal:currentUser() != null and  portal:currentUser().userName eq subscribeVo.userName?subscribeVo.userName:subscribeVo.userNameSecret}
			       </a>
		            </td>
            </c:when>
            <c:otherwise>
                 <td><span class="bule">
		          <a <c:if test="${portal:currentUser() != null and  portal:currentUser().userName eq subscribeVo.userName}">style="color:blue;"</c:if> href="${path}/accountdetail/show.html?userName=${portal:encode(portal:encode(subscribeVo.userNameEncrypt))}" target="_blank">${portal:currentUser() != null and  portal:currentUser().userName eq subscribeVo.userName?subscribeVo.userName:subscribeVo.userNameSecret}</a></span>
		         </td>
            </c:otherwise>
       </c:choose>
       <td  style="text-align: right;"><fmt:formatNumber value="${subscribeVo.account}" pattern="#,##0.##"/>元</td>
       <td><fmt:formatDate value="${subscribeVo.addTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
     </tr>
     </c:forEach>
</table>

<div class="yema"> 
    <div class="yema_cont">
        <div class="yema rt">
            <jsp:include page="/WEB-INF/page/common/ajaxpage.jsp">
				<jsp:param name="pageNo" value="${page.pageNo}" />
				<jsp:param name="totalPage" value="${page.totalPage}" />
				<jsp:param name="hasPre" value="${page.hasPre}" />
				<jsp:param name="prePage" value="${page.prePage}" />
				<jsp:param name="hasNext" value="${page.hasNext}" />
				<jsp:param name="nextPage" value="${page.nextPage}" />
			</jsp:include>
        </div>
    </div>  
</div>
 
 <script type="text/javascript">
function findPage(pageNum){
	searchSubscribeList(pageNum);
}
</script>
