<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/page/common/taglib.jsp"%>

		<table width="100%" border="0">
		<thead>
			<tr>
				<th>序号(投标)</th>
				<th>开通时间</th>
				<th>转让价格</th>
				<th>奖励</th>
				<th>发布转让日期</th>
				<th>转让操作</th>
			</tr>
		</thead>
			<c:forEach items="${page.result}" var="firstTransferVo" varStatus="sta" step="1">
				<tr <c:if test="${sta.index%2 == 0 }">class="trcolor"</c:if>>
					<td><a href="javascript:toTransfer(${firstTransferVo.id});">${firstTransferVo.ordernum}</a></td>
					<td><fmt:formatDate value="${firstTransferVo.firstOpenTime}" pattern="yyyy-MM-dd HH:mm:ss"></fmt:formatDate></td>
					<td><span class="numcolor"><fmt:formatNumber value="${firstTransferVo.accountReal}" pattern="#,##0.00"/></span></td>
					<td><span class="numcolor"><fmt:formatNumber value="${firstTransferVo.awards}" pattern="#,##0.00"/></span></td>
					<td><fmt:formatDate value="${firstTransferVo.addtime}" pattern="yyyy-MM-dd HH:mm:ss"></fmt:formatDate></td>
					<td><input type="button" value="取消" class="but bluess"  onclick="cancelTransfer(this,'${firstTransferVo.id}')"   >
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
	*  取消直通车转让
	*/
	function cancelTransfer(obj,transferId){
			if(layer.confirm("确定要取消直通车转让吗？",function(){
				_load = layer.load('处理中..');
				$(obj).removeAttr("onclick");
				$.ajax({
				    url : '${basePath}/zhitongche/zhuanrang/cancelTransfer/'+transferId+'.html',
				    data:{},
				    type: "POST",
				    success:function(msg){
				    	layer.close(_load);
				    	var message = msg.code=="0"?msg.message:"取消直通车转让成功";
				    	var iconIndex = msg.code=="0"?5:1;
				    	layer.msg(message, 2, iconIndex,function(){
					    	//重新查询
				    		window.queryTransferingList(1);
				    	});
				    },
					error : function() {
						layer.close(_load);
						$(obj).attr("onclick","cancelTransfer(this,'"+transferId+"'");
						layer.msg("网络连接超时，请您刷新页面或稍后重试!", 2, 5);
				    } 
				 });	
		}));
    }
	
	function findPage(pageNo){
		window.queryTransferingList(pageNo);
	}
	
	/**
	 * 直通车转让详细跳转
	 */
	function toTransfer(transferId){
		window.open("${basePath}/zhitongche/zhuanrang/queryTransferById/"+transferId+".html"); 

	};
	
</script>