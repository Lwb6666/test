<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/page/common/taglib.jsp"%>
<%@ include file="/WEB-INF/page/common/public2.jsp"%>

<table width="100%" border="0" cellspacing="0" cellpadding="0" class="table center tbtr02">
   <tr class="tbl-title">
     <td class="tdtitl04">投资人</td>
     <td class="tdpdtitle3">加入金额</td>
     <td class="tdpdtitle3">加入时间</td>
   </tr>
   <c:forEach items="${page.result}" var="tenderDetail" varStatus="sta">
   <tr>
     <td class="tdtitl04" style="overflow:visible;">
         <c:if test="${loginMember.userId == tenderDetail.userId}">
         	<c:if test="${tenderDetail.redMoney>0}">
			    <a class="listtishi" style="padding-left: 35px;" href="${path}/accountdetail/show.html?userIdMD5=${tenderDetail.userIdMD5}" target="_blank"  >
     	 		<img src="${path}/images/v5/hb.png" style="padding-top:15px;"/><p class="displaywm1 f14" >使用红包:<fmt:formatNumber value="${tenderDetail.redMoney }" pattern="#,##0.##" />元</p>
     	 		</a>	
         	</c:if> 
         	 <c:if test="${tenderDetail.platForm == 3 or tenderDetail.platForm == 4}"> 
			   <a class="listtishi" style="padding-left: 35px;" href="${path}/accountdetail/show.html?userIdMD5=${tenderDetail.userIdMD5}" target="_blank"  >
			 	<img src="${basePath}/images/v5/phone.png" style="padding-top:15px;padding-left: 0px;"/>
	         	<i class="displaywm" style="z-index:9999;padding-left: 35px;" ><img src="${basePath}/images/v5/erweima.png"/></i>
	           </a>
	         </c:if>
			<a style="color: blue;"  href="${path}/accountdetail/show.html?userIdMD5=${tenderDetail.userIdMD5}" target="_blank">
			   ${tenderDetail.userName}
			</a>
		 </c:if> 
		 <c:if test="${loginMember.userId != tenderDetail.userId}">
			  <c:if test="${tenderDetail.platForm == 3 or tenderDetail.platForm == 4}"> 
			  	<a class="listtishi" style="padding-left: 35px;" href="${path}/accountdetail/show.html?userIdMD5=${tenderDetail.userIdMD5}" target="_blank"  >
			 	<img src="${basePath}/images/v5/phone.png" style="padding-top:15px;padding-left: 0px;"/>
	         	<i class="displaywm" style="z-index:9999;padding-left: 35px;" ><img src="${basePath}/images/v5/erweima.png"/></i>
	            </a>
	         </c:if>
	          <a  href="${path}/accountdetail/show.html?userIdMD5=${tenderDetail.userIdMD5}" target="_blank"  >
	            ${tenderDetail.userNameSecret}
	         </a>
		 </c:if>
     </td>
     <td class="tdtitl05">
     <fmt:formatNumber value="${tenderDetail.account }" pattern="#,##0.##" />元 
     </td>
     <td class="tdtitl05"><fmt:formatDate value="${tenderDetail.addTime }" pattern="yyyy-MM-dd HH:mm:ss"/></td>
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
/**
 * ajax 翻页功能
 */
function findPage(pageNum){
	searchFixTenderDetailList(pageNum,${fixBorrowId});
}


</script>