<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/page/common/taglib.jsp"%>

                <table width="100%" border="0" cellspacing="0" cellpadding="0"  class="table center tbtr02">
		<thead>
             <tr class="tbl-title">
                <td>开通时间</td>
				<td>投标序号</td>
				<td>开通金额(元)</td>
				<td>剩余未投(元)</td>
				<td>债权价格(元)</td>
				<td>操作</td>
			</tr>
		</thead>
			<c:forEach items="${page.result}" var="firstTransferTypeVo" varStatus="sta" step="1">
				<tr>
					<td><fmt:formatDate value="${firstTransferTypeVo.addtime}" pattern="yyyy-MM-dd HH:mm:ss"></fmt:formatDate></td>
					<td>${firstTransferTypeVo.orderNum}</td>
					<td><span class="numcolor"><fmt:formatNumber value="${firstTransferTypeVo.account}" pattern="#,##0.00"/></span></td>
					<td><span class="numcolor"><fmt:formatNumber value="${firstTransferTypeVo.useMoney}" pattern="#,##0.00"/></span></td>
					<td><span class="numcolor"><fmt:formatNumber value="${firstTransferTypeVo.bondsAccount}" pattern="#,##0.00"/></span></td>
					<td>
					<a data-reveal-id="myModal" data-animation="fade" href="javascript:void(0)" class="btn-zq" onclick="ontransfer(${firstTransferTypeVo.parentId !=null?-1:0},${firstTransferTypeVo.id})">发起转让</a>
					</td>
				</tr>
			</c:forEach>
		</table>
		<c:if test="${page.result==null  || page.totalCount==0 }">
	<div align="center"  style="height: 70px;line-height: 70px">暂无相关信息</div>
	</c:if>   
	<c:if test="${page.result !=null && page.totalCount > 0 }">
		<jsp:include page="/WEB-INF/page/common/ajaxpage.jsp">
			<jsp:param name="pageNo" value="${page.pageNo}" />
			<jsp:param name="totalPage" value="${page.totalPage}" />
			<jsp:param name="hasPre" value="${page.hasPre}" />
			<jsp:param name="prePage" value="${page.prePage}" />
			<jsp:param name="hasNext" value="${page.hasNext}" />
			<jsp:param name="nextPage" value="${page.nextPage}" />
		</jsp:include>   
		</c:if>

<script type="text/javascript">
	$(document).ready(function(){ 
	    var color="#f0f7ff"
	    $(".tbtr tr:even,.tbtr01 tr:even,.tbtr02 tr:even,.tbtr03 tr:even,.tbtr04 tr:even,.tbtr05 tr:even,.tbtr06 tr:even,.tbtr07 tr:even,.tbtr08 tr:even,.tbtr09 tr:even,.tbtr10 tr:even,.tbtr11 tr:even,.tbtr12 tr:even,.tbtr13 tr:even,.tbtr14 tr:even,.tbtr15 tr:even,.tbtr16 tr:even,.tbtr17 tr:even").attr("bgcolor",color);//改变奇数行背景色
	
	    $('#beginTime').val('${beginTime}');
	    $('#endTime').val('${endTime}');
	})
	
		/**
	*  进入直通车转让页面
	*/
	function ontransfer(parentId,tenderRealId){
		$("#myModal").html("");
		
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
				$("#myModal").load('${basePath}/zhitongche/zhuanrang/toTransferIndex/'+tenderRealId+'.html');
			},
			error : function(data) {
				layer.msg("网络连接异常，请刷新页面或稍后重试！", 2, 5);
			}
		});	
		
   }
	
	
	/**
	*  进入直通车转让页面
	*/
// 	function ontransfer(parentId,tenderRealId){

// 		if (parentId==-1) {
// 			layer.msg("暂不支持直通车二次转让，敬请谅解！", 2, 5);
// 			return;
// 		}
// 		$.ajax({
// 			url : '${basePath}/zhitongche/zhuanrang/toTransferJudgTender/'+tenderRealId+'.html',
// 			data : {type:2},
// 			type : 'post',
// 			dataType : 'json',
// 			success : function(data) {
// 				if("0" == data.code){
// 					layer.msg(data.message, 2, 5);
// 					return;
// 				}
// 				$.layer({
// 					type : 2,
// 					title : '直通车转让',
// 					area : [ '850px', '600px' ],
// 					offset : [ '10px', '' ],
// 					maxmin : true,
// 					iframe : {
// 						src : '${basePath}/zhitongche/zhuanrang/toTransferIndex/'+tenderRealId+'.html'
// 					}
// 				});	
// 			},
// 			error : function(data) {
// 				layer.msg("网络连接异常，请刷新页面或稍后重试！", 2, 5);
// 			}
// 		});	
		
//    }
	
	function findPage(pageNo){
		queryCanTransferList(pageNo);
	}
	
</script>