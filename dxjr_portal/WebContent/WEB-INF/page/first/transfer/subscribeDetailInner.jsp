<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/page/common/taglib.jsp"%>
<table width="100%" border="0" cellspacing="0" cellpadding="0" class="table center tbg">
                          <tr class="tbl-title">
                            <td>投标人</td>
                            <td style="text-align: right;">投标金额</td>
                            <td>交易时间</td>
                          </tr>
                          <c:forEach items="${firstSubscribePage.result}" var="firstSubscribeDetail" varStatus="idx">
                          <tr>
                            <td  <c:if test="${portal:currentUser() != null and  portal:currentUser().userName eq firstSubscribeDetail.userName}">style="color:blue;"</c:if> ><a style="color: #333;" href="${path}/accountdetail/show.html?userName=${portal:encode(portal:encode(firstSubscribeDetail.userNameEncrypt))}" target="_blank">${portal:currentUser() != null and  portal:currentUser().userName eq firstSubscribeDetail.userName?firstSubscribeDetail.userName:firstSubscribeDetail.userNameSecret}</a></td>
                            <td style="text-align: right;"><fmt:formatNumber
										value="${firstSubscribeDetail.account}" pattern="#,##0.00" />元</td>
                            <td><fmt:formatDate
										value="${firstSubscribeDetail.addTime}" pattern="yyyy-MM-dd" /></td>
                          </tr>
                          </c:forEach> 
                     </table>

		<jsp:include page="/WEB-INF/page/common/ajaxpage.jsp">
			<jsp:param name="pageNo" value="${firstSubscribePage.pageNo}" />
			<jsp:param name="totalPage" value="${firstSubscribePage.totalPage}" />
			<jsp:param name="hasPre" value="${firstSubscribePage.hasPre}" />
			<jsp:param name="prePage" value="${firstSubscribePage.prePage}" />
			<jsp:param name="hasNext" value="${firstSubscribePage.hasNext}" />
			<jsp:param name="nextPage" value="${firstSubscribePage.nextPage}" />
		</jsp:include>		
		<style>
		  .tbg tr:nth-of-type(odd){background:#f0f7ff;}
		</style>
		<script type="text/javascript">
			/**
			 * ajax 翻页功能
			 */
			function findPage(pageNo){
				window.parent.turnSubscribeListParent(pageNo);
			}
		</script>
