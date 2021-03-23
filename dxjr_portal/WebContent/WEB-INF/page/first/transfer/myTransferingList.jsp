<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/page/common/taglib.jsp"%>

<table width="100%" border="0" cellspacing="0" cellpadding="0"  class="table center tbtr02">
		<thead>
          <tr class="tbl-title">
				<td>开通时间</td>
				<td>投标序号</td>
				<td>转让价格</td>
				<td>奖励</td>
				<td>发布转让日期</td>
				<td>转让操作</td>
			</tr>
		</thead>
			<c:forEach items="${page.result}" var="firstTransferVo" varStatus="sta" step="1">
				<tr>					
					<td><fmt:formatDate value="${firstTransferVo.firstOpenTime}" pattern="yyyy-MM-dd HH:mm:ss"></fmt:formatDate></td>
					<td><a href="javascript:toTransfer(${firstTransferVo.id});">${firstTransferVo.ordernum}</a></td>
					<td><span class="numcolor"><fmt:formatNumber value="${firstTransferVo.accountReal}" pattern="#,##0.00"/></span></td>
					<td><span class="numcolor"><fmt:formatNumber value="${firstTransferVo.awards}" pattern="#,##0.00"/></span></td>
					<td><fmt:formatDate value="${firstTransferVo.addtime}" pattern="yyyy-MM-dd HH:mm:ss"></fmt:formatDate></td>
					<td>
						<a data-reveal-id="cancelModal" data-animation="fade" href="javascript:void(0);" onclick="showCancelTransfer('${firstTransferVo.id}')"  class="btn-zq">取消转让 </a>
<%-- 						<input type="button" value="取消" class="but bluess"  onclick="cancelTransfer(this,'${firstTransferVo.id}')"   > --%>
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


<div  id="cancelModal" class="reveal-modal">
	<div class="cont-word">
        <div class="form-info-layer">
        	<div class="form-col2">
              	<div class="pd20 f14">确认取消直通车转让？</div>
            </div>
            <div class="form-col2">
            	<input  type="hidden" id="transferId" name="transferId"  />
            	<button type="button" class="remove icon-close" onclick="closeCancelModal();">取消</button><button type="button" class="enter" onClick="cancelTransfer();">确认</button>
            </div>
         </div>
    </div> 
</div>
<script type="text/javascript">
$(document).ready(function(){ 
    var color="#f0f7ff"
    $(".tbtr tr:even,.tbtr01 tr:even,.tbtr02 tr:even,.tbtr03 tr:even,.tbtr04 tr:even,.tbtr05 tr:even,.tbtr06 tr:even,.tbtr07 tr:even,.tbtr08 tr:even,.tbtr09 tr:even,.tbtr10 tr:even,.tbtr11 tr:even,.tbtr12 tr:even,.tbtr13 tr:even,.tbtr14 tr:even,.tbtr15 tr:even,.tbtr16 tr:even,.tbtr17 tr:even").attr("bgcolor",color);//改变奇数行背景色
    
    $('#beginTime').val('${beginTime}');
    $('#endTime').val('${endTime}');
})

function showCancelTransfer(transferId){
	$("#transferId").val(transferId);
}

function closeCancelModal(){
	$('#cancelModal').trigger('reveal:close');
}
	/**
	*  取消直通车转让
	*/
	function cancelTransfer(){
		_load = layer.load('处理中..');
		var transferId= $("#transferId").val();
		$.ajax({
		    url : '${basePath}/zhitongche/zhuanrang/cancelTransfer/'+transferId+'.html',
		    data:{},
		    type: "POST",
		    success:function(msg){
		    	var message = msg.code=="0"?msg.message:"取消直通车转让成功";
		    	var iconIndex = msg.code=="0"?5:1;
		    	layer.msg(message, 2, iconIndex,function(){
		    		$('#cancelModal').trigger('reveal:close');
			    	//重新查询
		    		window.queryTransferingList(1);
		    	});
		    },
			error : function() {
// 				layer.close(_load);
// 				$(obj).attr("onclick","cancelTransfer(this,'"+transferId+"'");
				layer.msg("网络连接超时，请您刷新页面或稍后重试!", 2, 5);
		    } 
		 });
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