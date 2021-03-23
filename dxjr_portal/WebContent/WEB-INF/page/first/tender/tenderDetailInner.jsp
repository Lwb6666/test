<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/page/common/taglib.jsp"%>
<%@ include file="/WEB-INF/page/common/taglib.jsp"%>

<div class="rz_borrow1 ">
  <div class="tbjl">
		<table >
			<tr style="background: #fff" height="40px">
				<td><div align="center"><strong>开通人</strong></div></td>
				<td><div align="center"><strong>开通金额（元）</strong></div></td>
				<td><div align="center"><strong>开通时间</strong></div></td>
			</tr>
			<c:forEach items="${tendDetailPage.result}" var="firstTenderDetail" varStatus="s">
		    <tr height="40px" id="investt${s.index }" <c:if test="${s.index%2==0}">style="background: #ecfafd"</c:if>>
				<td><div align="center" >
						<a  <c:if test="${memberVo.userId == firstTenderDetail.userId}">style="color:blue;"</c:if>   href="${path}/accountdetail/show.html?userName=${portal:encode(portal:encode(firstTenderDetail.userNameEncrypt))}"   
						     target="_blank" title="${firstTenderDetail.userNameSecret }">${memberVo.userId == firstTenderDetail.userId?firstTenderDetail.username:firstTenderDetail.userNameSecret }
						 </a>
					</div>
				</td>
				<td><div align="center" <c:if test="${memberVo.userId == firstTenderDetail.userId}">style="color:blue;"</c:if>>
					￥${firstTenderDetail.accountStr}</div>
				</td>
				<td><div align="center" <c:if test="${memberVo.userId == firstTenderDetail.userId}">style="color:blue;"</c:if>>
					${firstTenderDetail.addtimeStr } </div>
				</td>
			</tr>
			</c:forEach>
		</table>
 	</div>
 </div> 
		
		 	<jsp:include page="/WEB-INF/page/common/ajaxpage.jsp">
				<jsp:param name="pageNo" value="${tendDetailPage.pageNo}" />
				<jsp:param name="totalPage" value="${tendDetailPage.totalPage}" />
				<jsp:param name="hasPre" value="${tendDetailPage.hasPre}" />
				<jsp:param name="prePage" value="${tendDetailPage.prePage}" />
				<jsp:param name="hasNext" value="${tendDetailPage.hasNext}" />
				<jsp:param name="nextPage" value="${tendDetailPage.nextPage}" />
			</jsp:include>		
		
		<script type="text/javascript">
			/**
			 * ajax 翻页功能
			 */
			function findPage(pageNo){
				window.parent.turnFirstTenderDetailParent(pageNo);
			}
		</script>
