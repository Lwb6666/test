<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/page/common/taglib.jsp"%>

		<table width="100%" border="0">
		<thead>
			<tr>
				<th>序号（投标）</th>
				<th>开通时间</th>
				<th>开通金额</th>
				<th>开通余额</th>
				<th>债权价格</th>
				<th>转让操作</th>
			</tr>
		</thead>
			<c:forEach items="${page.result}" var="firstTransferTypeVo" varStatus="sta" step="1">
				<tr <c:if test="${sta.index%2 == 0 }">class="trcolor"</c:if>>
					<td>${firstTransferTypeVo.orderNum}</td>
					<td><fmt:formatDate value="${firstTransferTypeVo.addtime}" pattern="yyyy-MM-dd HH:mm:ss"></fmt:formatDate></td>
					<td><span class="numcolor"><fmt:formatNumber value="${firstTransferTypeVo.account}" pattern="#,##0.00"/></span></td>
					<td><span class="numcolor"><fmt:formatNumber value="${firstTransferTypeVo.useMoney}" pattern="#,##0.00"/></span></td>
					<td><span class="numcolor"><fmt:formatNumber value="${firstTransferTypeVo.bondsAccount}" pattern="#,##0.00"/></span></td>
					<td><input type="button" value="转让" class="but bluess"  onclick="ontransfer(${firstTransferTypeVo.parentId !=null?-1:0},${firstTransferTypeVo.id})"   >
					</td>
				</tr>
			</c:forEach>
		</table>
		
		<jsp:include page="/WEB-INF/page/common/ajaxpage.jsp">
			<jsp:param name="pageNo" value="${page.pageNo}" />
			<jsp:param name="totalPage" value="${page.totalPage}" />
			<jsp:param name="hasPre" value="${page.hasPre}" />
			<jsp:param name="prePage" value="${page.prePage}" />
			<jsp:param name="hasNext" value="${page.hasNext}" />
			<jsp:param name="nextPage" value="${page.nextPage}" />
		</jsp:include>   

<script type="text/javascript">
	/**
	*  进入直通车转让页面
	*/
	function ontransfer(parentId,tenderRealId){

		if (parentId==-1) {
			layer.msg("暂不支持直通车二次转让，敬请谅解！", 2, 5);
			return;
		}
		$.ajax({
			url : '${basePath}/zhitongche/zhuanrang/toTransferJudgTender/'+tenderRealId+'.html',
			data : {type:2},
			type : 'post',
			dataType : 'json',
			success : function(data) {
				if("0" == data.code){
					layer.msg(data.message, 2, 5);
					return;
				}
				$.layer({
					type : 2,
					title : '直通车转让',
					area : [ '850px', '600px' ],
					offset : [ '10px', '' ],
					maxmin : true,
					iframe : {
						src : '${basePath}/zhitongche/zhuanrang/toTransferIndex/'+tenderRealId+'.html'
					}
				});	
			},
			error : function(data) {
				layer.msg("网络连接异常，请刷新页面或稍后重试！", 2, 5);
			}
		});	
		
   }
	
	function findPage(pageNo){
		queryCanTransferList(pageNo);
	}
	
</script>