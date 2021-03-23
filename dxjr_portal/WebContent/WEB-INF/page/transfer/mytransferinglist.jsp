<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/page/common/taglib.jsp"%>
<table width="100%" border="0" cellspacing="0" cellpadding="0"  class="table center tbtr">
	<thead>
		<tr class="tbl-title" >
		  <td>借款标题</td>
		  <td>预期利率</td>
		  <td>还款方式</td>
		  <td>期限</td>
		  <td>已还期数</td>
		  <td>债权价格</td>
		  <td>转让价格 </td>
		  <td>转让系数</td>
		  <td>发布转让日期</td>
		  <td>操作</td>
		</tr>
	</thead>
	<tbody>
                   
                  <c:forEach   var="transferVo"  items="${page.result}"   varStatus="index">
	                
	                 <c:set var="btype" value="${transferVo.borrowType }" scope="page"  />
	 
									<c:choose>   
										<c:when test="${btype == 1}">
											<c:set var="cls" value="xin" scope="page"  />
										</c:when>   
										<c:when test="${btype == 2}">
											<c:set var="cls" value="di" scope="page"  /> 
										</c:when>
										<c:when test="${btype == 3}">
											<c:set var="cls" value="jing" scope="page"  />  
										</c:when>
										<c:when test="${btype == 4}">
											<c:set var="cls" value="" scope="page"  />   
										</c:when>
										<c:when test="${btype == 5}">
											<c:set var="cls" value="bao" scope="page"  />     
										</c:when>
										<c:otherwise></c:otherwise> 
									</c:choose> 
	                
	                  <tr >
	                                    <td align="left" ><span class="bule"><i class="icon icon-${cls}"></i><a  title="${transferVo.borrowName }" href="${path }/zhaiquan/${transferVo.id }.html"><font  color='red' >[转]</font>${fn:substring(transferVo.borrowName,0,10)}<c:if test="${fn:length(transferVo.borrowName)>10}">..</c:if></a></span></td>
		                                <td>${transferVo.borrowApr}%</td>
	                                    <td> 
		                                      <c:choose>
			                                	<c:when test="${transferVo.borrowStyle == 0}">
													没有限制     
												</c:when>   
												<c:when test="${transferVo.borrowStyle == 1}">
													等额本息
												</c:when>   
												<c:when test="${transferVo.borrowStyle == 2}">
													<a href="##" title="按月付息到期还本">按月付息</a> 
												</c:when>
												<c:when test="${transferVo.borrowStyle == 3}">
													<a href="##" title="到期还本付息">到期还本</a> 
												</c:when>
												<c:when test="${transferVo.borrowStyle == 4}">
													按天还款   
												</c:when>
												<c:otherwise></c:otherwise> 
											</c:choose>
	                                     </td>
					                    <td>
						                     <c:if test="${transferVo.borrowType!=4 && transferVo.borrowStyle !=4 }">${transferVo.borrowTimeLimit }月</c:if>
											 <c:if test="${transferVo.borrowType!=4 && transferVo.borrowStyle ==4 }">${transferVo.borrowTimeLimit }天</c:if>
					                     </td>
					                     <td>
					                           <c:if test="${transferVo.borrowStyle == 1 || transferVo.borrowStyle == 2}">
								                 ${transferVo.transferBeginOrder }/${transferVo.borrowOrder}
								                 </c:if>
								                 <c:if test="${transferVo.borrowStyle != 1 && transferVo.borrowStyle != 2}">
								                 ${transferVo.transferBeginOrder}/1
								               </c:if>
					                     </td>
					                     <td>${transferVo.account}</td>
					                     <td>${transferVo.accountReal}</td>
					                     <td>${transferVo.coef}</td>
					                     <td><fmt:formatDate value="${transferVo.addTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
					                     <td>
					                     <a  data-reveal-id="cancelModal" data-animation="fade" href="javascript:void(0);" onclick="showCancelTransfer(${transferVo.id})"  class="btn-zq" >取消转让 </a>
<%-- 					                     <input type="button"  id="cancelTransfer" onclick="showCancelTransfer(${transferVo.id})"    value="取消" class="but bluess"> --%>
					                     </td>
                    </tr>
                </c:forEach>
    </tbody>
    
    </table>

<c:if test="${page.result==null  || page.totalCount==0 }">
	<div align="center"  style="height: 70px;line-height: 70px">暂无相关信息</div>
</c:if>   
<c:if test="${page.result !=null && page.totalCount > 0 }">

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
                  
</c:if>
<!--弹层star--->
<div  id="cancelModal" class="reveal-modal">
	<div class="cont-word">
        <div class="form-info-layer">
        	<div class="form-col2">
              	<div class="pd20 f14">确认取消债权转让？</div>
            </div>
            <div class="form-col2">
            	<input  type="hidden" id="transferId" name="transferId"  />
            	<button type="button" class="remove icon-close">取消</button><button type="button" class="enter" onClick="cancelTransfer();">确认</button>
            </div>
         </div>
    </div> 
</div>

<!--弹层end--->





<script type="text/javascript">
$(document).ready(function(){ 
    var color="#f0f7ff"
    $(".tbtr tr:even,.tbtr01 tr:even,.tbtr02 tr:even,.tbtr03 tr:even,.tbtr04 tr:even,.tbtr05 tr:even,.tbtr06 tr:even,.tbtr07 tr:even,.tbtr08 tr:even,.tbtr09 tr:even,.tbtr10 tr:even,.tbtr11 tr:even,.tbtr12 tr:even,.tbtr13 tr:even,.tbtr14 tr:even,.tbtr15 tr:even,.tbtr16 tr:even,.tbtr17 tr:even").attr("bgcolor",color);//改变奇数行背景色
})

function closeCancelModal(){
	$('#cancelModal').trigger('reveal:close');
}

var  _loadPopIndex = 0;
 
function showCancelTransfer(transferId){
	$("#transferId").val(transferId);
// 	_loadPopIndex= $.layer({
// 		type: 1,   //0-4的选择,
// 		title:  ["请输入撤销原因" , true],
// 		closeBtn: [0 , true],
// 		area: ["350px","230px"],
// 		offset : [ '200px', '' ],
// 		page: {
// 			dom : '#cancelPop'
// 		}
// 	});
	 
}
 

function findPage(pageNo){
	transfer(2,pageNo);
}
 
 
// function closePop(){
// 	layer.close(_loadPopIndex);
// }
 
function cancelTransfer(){
	 
	/* if($.trim($("#prompt").val()).length==0){
		layer.msg('请输入撤销原因.', 1, 5);
		return ;
	} */
	 
	//,cancelRemark:$("#prompt").val()
		var  _load = layer.load('处理中..');
			$.ajax({
					url : '${basePath}/zhaiquan/cancelTransfer.html',
					data:{id:$("#transferId").val()},
					type : 'post',
					dataType :"json",
					success : function(result) {
// 						closePop();
// 						layer.close(_load);
						  if (result.code == '0') {
								parent.layer.msg(result.message, 1, 5);
								loadimage();
							}else{
								  parent.layer.msg(result.message,1,1);
								  $('#cancelModal').trigger('reveal:close');
								  transfer(2,1);
							}
						  
					 },
					error : function(result) {
// 						closePop();
// 						layer.close(_load);
						layer.msg('网络连接超时,请您稍后重试.', 1, 5);
				    }
				});
}
 
 
 
 
 
</script>
 