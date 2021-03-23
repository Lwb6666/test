<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%> 
<%@ include file="/WEB-INF/page/common/taglib.jsp"%>
<table>
	<tr>
		<td width="120">用户名</td>
		<td width="160">注册时间</td>
		<td>实名认证</td>
		<td>手机认证</td>
		<td>邮箱认证</td>
		<%-- <td>vip认证</td> --%>
		<%-- <td>充值条件</td> --%>
		<td>赠送金额</td>
		<td>推荐成功时间</td>
	</tr>
	<c:forEach items="${page.result}" var="i" varStatus="status">
	<tr ${status.count % 2 == 0 ? 'class="trcolor"' : ''}>
		<td><a href="${path}/accountdetail/show.html?userId=${i.invitedUserid}"  target="_blank">${i.invitedUsername}</a></td>
		<td><fmt:formatDate value="${i.registerTime}" pattern="yyyy-MM-dd" /></td>
		<td><img src="${basePath}/images/${i.realnamePassed == 1 ? 'turth.gif' : 'fouth.gif'}" /></td>
		<td><img src="${basePath}/images/${i.mobilePassed == 1 ? 'turth.gif' : 'fouth.gif'}" /></td>
		<td><img src="${basePath}/images/${i.emailPassed == 1 ? 'turth.gif' : 'fouth.gif'}" /></td>
		<%-- <td><img src="${basePath}/images/${i.vipPassed == 1 ? 'turth.gif' : 'fouth.gif'}" /></td> --%>
		<%-- <td><img src="${basePath}/images/${i.rechargePassed == 1 ? 'turth.gif' : 'fouth.gif'}" /></td> --%>
		<td>
		<!-- 上线之前的赠送现金仍按原有显示，上线之后才有的则显示红包金额 -->
		 <c:if test="${i.isViewCash=='1'}">
		  <c:if test="${i.awardMoney!=null}">
		    <fmt:formatNumber value="${i.awardMoney}" pattern="#,##"/>元红包
		   </c:if>
		 </c:if>
		  <c:if test="${i.isViewCash=='0'}">
		    ${i.awardMoney}
		 </c:if>
		</td>
		<td><fmt:formatDate value="${i.inviteSuccessTime}" pattern="yyyy-MM-dd" /></td>
	</tr>
	</c:forEach>
	<tr>
		<td colspan="9">
			<jsp:include page="/WEB-INF/page/common/ajaxpage.jsp">
				<jsp:param name="pageNo" value="${page.pageNo}" />
				<jsp:param name="totalPage" value="${page.totalPage}" />
				<jsp:param name="hasPre" value="${page.hasPre}" />
				<jsp:param name="prePage" value="${page.prePage}" />
				<jsp:param name="hasNext" value="${page.hasNext}" />
				<jsp:param name="nextPage" value="${page.nextPage}" />
			</jsp:include>
		</td>
	</tr>
</table>
<script>
function findPage(pageNum) {
	$.ajax({
		url : '${basePath}/myaccount/friend/toFriendGeneralize/' + pageNum + '.html',
		type : 'post',
		dataType : 'text',
		success : function(data) {
			$("#bodyDiv").html(data);
		},
		error : function(data) {
			layer.msg("网络连接异常，请刷新页面或稍后重试！", 1, 5);
	    }
	});
}
</script>