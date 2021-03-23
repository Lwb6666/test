<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/page/common/taglib.jsp"%>

	<div id="rz_main" style="height:auto; border-top:none; margin-top:0; ">
		<div class="rz_borrow1">
			<div class="tb_ztype">
				<table border="0">
					<tr>
						<th> 交易时间</th>
						<th> 债权卖出者</th>
						<th> 债权买入者</th>
						<th>交易金额</th>
						<th> 债权详情</th>
					</tr>
					<c:forEach items="${page.result}" var="firstTransferVo" varStatus="sta" step="1">
						<tr <c:if test="${sta.index%2 == 0 }">class="trcolor"</c:if>>
							<td><fmt:formatDate value="${firstTransferVo.successTime}" pattern="yyyy-MM-dd HH:mm:ss"></fmt:formatDate></td>
							<td align="center">
								<a href="${path}/accountdetail/show.html?userName=${portal:encode(portal:encode(firstTransferVo.userNameEncrypt))}" target="_blank">
									${firstTransferVo.userNameSecret}
								</a>
							</td>
							<td align="center">
								<a href="${path}/accountdetail/show.html?userName=${portal:encode(portal:encode(firstTransferVo.subscribeUserNameEncrypt))}" target="_blank">
								${firstTransferVo.subscribeUserNameSecret}
								</a>
							</td>
							<td><span class="numcolor"><fmt:formatNumber value="${firstTransferVo.transactionAccount}" pattern="#,##0.00"/></span></td>
							<td><a href="javascript:void(0);" onclick="toTransfer(${firstTransferVo.id});">查看详情</a></td>
						</tr>
					</c:forEach>
				</table>
				</div>
		</div> 
	</div>
		<jsp:include page="/WEB-INF/page/common/ajaxpage.jsp">
			<jsp:param name="pageNo" value="${page.pageNo}" />
			<jsp:param name="totalPage" value="${page.totalPage}" />
			<jsp:param name="hasPre" value="${page.hasPre}" />
			<jsp:param name="prePage" value="${page.prePage}" />
			<jsp:param name="hasNext" value="${page.hasNext}" />
			<jsp:param name="nextPage" value="${page.nextPage}" />
		</jsp:include>   
<script type="text/javascript">
	
	function findPage(pageNo){
		turnFirstTransferedList(pageNo);
	};
	
	/**
	 * 直通车转让详细跳转
	 */
	function toTransfer(transferId){
		window.open("${basePath}/zhitongche/zhuanrang/queryTransferById/"+transferId+".html"); 

	};
	
</script>