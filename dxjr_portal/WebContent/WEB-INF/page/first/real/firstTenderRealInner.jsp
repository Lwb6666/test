<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ include file="/WEB-INF/page/common/taglib.jsp"%>

		 <table width="875" border="1">
	        <tr>
	          <td width="5%"><strong>序号</strong></td>
	          <td width="20%"><strong>投标直通车标题</strong></td>
	          <td width="15%"><strong>计划金额</strong></td>
	          <td width="10%"><strong>开通金额</strong></td>
	          <td width="10%"><strong>开通余额</strong></td>
	          <td width="8%"><strong>所占比例</strong></td>
	          <td width="8%"><strong>状态</strong></td>
	          <td width="24%"><strong>操作</strong></td>
	        </tr>
			<c:forEach items="${firstTenderRealPage.result}" var="firstTenderReal" varStatus="sta">
		        <tr>
		          <td>${sta.index + 1 }</td>
		          <td><a href="${path }/zhitongche//toInvest/${firstTenderReal.firstBorrowId}.html" target="_blank"><span title="${firstTenderReal.firstBorrowName }">${fn:substring(firstTenderReal.firstBorrowName,0,14)}<c:if test="${fn:length(firstTenderReal.firstBorrowName)>14 }">..</</c:if></span></a></td>
		          <td>${firstTenderReal.planAccount}元</td>
		          <td>${firstTenderReal.account}元</td>
		          <td>${firstTenderReal.useMoneyStr}元</td>
		          <td><fmt:formatNumber value="${firstTenderReal.rate*100}" pattern="#,##0.00"/>%</td>
		          <td>${firstTenderReal.statusStr}</td>
		          <td>
		           	<a href="${path}/newdxjr/first/tender/detail/queryFirstTenderDetail/10.html?firstBorrowId=${firstTenderReal.firstBorrowId}" target="_parent">查看明细</a>
		           	<a href="${path}/newdxjr/account/borrowManager/collectionFirst_record/10.html?firstBorrowId=${firstTenderReal.firstBorrowId}" target="_parent">查看投标</a>
		           	<a href="javascript:toFirstTenderXiyi('${firstTenderReal.firstBorrowId}');" target="_parent">查看协议</a>
		           	<c:if test="${(null!=firstTenderReal.unLockYn) && (firstTenderReal.unLockYn == 'Y')}">
		           		<a href="javascript:toUnlockFirst('${firstTenderReal.firstBorrowId}');">申请解锁</a>
		           	</c:if>
		          </td>
		        </tr>
			</c:forEach>
		</table>
		 	<jsp:include page="/WEB-INF/page/common/ajaxpage.jsp">
				<jsp:param name="pageNo" value="${firstTenderRealPage.pageNo}" />
				<jsp:param name="totalPage" value="${firstTenderRealPage.totalPage}" />
				<jsp:param name="hasPre" value="${firstTenderRealPage.hasPre}" />
				<jsp:param name="prePage" value="${firstTenderRealPage.prePage}" />
				<jsp:param name="hasNext" value="${firstTenderRealPage.hasNext}" />
				<jsp:param name="nextPage" value="${firstTenderRealPage.nextPage}" />
			</jsp:include>		
		
		<script type="text/javascript">
			/**
			 * ajax 翻页功能
			 */
			function findPage(pageNo){
				window.parent.turnFirstTenderRealPageParent(pageNo);
			}
			<%--
			     申请解锁 
			--%>
			function toUnlockFirst(firstBorrowId){
				
				if(layer.confirm("你确定要解锁吗?",function(){
					$.ajax({
						url : '${basePath}/first/tenderreal/unlock/'+firstBorrowId+'.html',
						type : 'post',
						data : {
						},
						success : function(result) {
							if (result== 'success') {
								layer.alert("解锁成功" , 1, "温馨提示");
								findPage(1);
							} else {
								layer.alert(result);
							}
						},
						error : function(result) {
							layer.alert("操作异常,请刷新页面或稍后重试！");
					    }
					});
				}));
				 
				
			}
		</script>
