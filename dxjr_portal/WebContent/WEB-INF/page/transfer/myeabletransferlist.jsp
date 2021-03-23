<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/page/common/taglib.jsp"%>

<table width="100%" border="0" cellspacing="0" cellpadding="0"  class="table center tbtr">
<thead>
   <tr class="tbl-title">
     <td>借款标题</td>
     <td>预期利率</td>
     <td>还款方式</td>
     <td>期限</td>
     <td>已还期数</td>
     <td>剩余投资本金（元）</td>
     <td>债权价格（元）</td>
     <td>下个还款日</td>
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
                  
                  
	                    <tr>
		                     <td align="left"><span class="bule"><i class="icon icon-${cls}"></i><a title="${transferVo.borrowName }"  href="${path }/toubiao/${transferVo.borrowId }.html"> <c:if test="${transferVo.parentId !=null }"><font  color='red' >[转]</font></c:if>   ${fn:substring(transferVo.borrowName,0,10)}<c:if test="${fn:length(transferVo.borrowName)>10}">..</c:if></a></span></td>
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
												<span  title="按月付息到期还本">按月付息</span> 
											</c:when>
											<c:when test="${transferVo.borrowStyle == 3}">
												<span  title="到期还本付息">到期还本</span> 
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
		                     <td>${transferVo.capital}</td>
		                     <td>${transferVo.account}<%-- ${transferVo.capital+transferVo.interest-transferVo.gainLoss}=${transferVo.capital}+${transferVo.interest}-${transferVo.gainLoss}= --%>  </td>
		                     <td><fmt:formatDate value="${transferVo.nextRepaymentDate}" pattern="yyyy-MM-dd"/> </td>
		                     <td>                   
		                     <a data-reveal-id="myModal" data-animation="fade" href="javascript:void(0);" class="btn-zq" onclick="ontransfer(${transferVo.parentId !=null?-1:0},${transferVo.tenderId});">发起转让</a>
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
<script type="text/javascript">
	$(document).ready(function(){ 
	    var color="#f0f7ff"
	    $(".tbtr tr:even,.tbtr01 tr:even,.tbtr02 tr:even,.tbtr03 tr:even,.tbtr04 tr:even,.tbtr05 tr:even,.tbtr06 tr:even,.tbtr07 tr:even,.tbtr08 tr:even,.tbtr09 tr:even,.tbtr10 tr:even,.tbtr11 tr:even,.tbtr12 tr:even,.tbtr13 tr:even,.tbtr14 tr:even,.tbtr15 tr:even,.tbtr16 tr:even,.tbtr17 tr:even").attr("bgcolor",color);//改变奇数行背景色
	})

function ontransfer(parentId,tenderId){
	$("#myModal").html("");
	/* 
	if (parentId==-1) {
		layer.msg("暂不支持债权二次转让，敬请谅解！", 2, 5);
		return;
	}
	*/
	var _load = layer.load('处理中..');
	$.ajax({
		url : '${basePath}/zhaiquan/toTransferJudge/'+tenderId+'.html',
		type : 'post',
		dataType : 'json',
		success : function(data) {
			layer.close(_load);
			if(data.code == '0'){
				$.ajax({
					url : '${basePath}/zhaiquan/toTransfer/'+tenderId+'.html',
					type : 'post',
					dataType : 'text',
					success : function(data) {
						$("#myModal").html(data);
					},
					error : function(data) {
						layer.msg('网络连接异常，请刷新页面或稍后重试！', 1, 5);
					}
				});
			}else{
				var _a = layer.alert(data.message, 8, function(){
					closeWin();
					layer.close(_a);
				});
			}
		},
		error : function(data) {
			layer.msg('网络连接异常，请刷新页面或稍后重试！', 1, 5);
		}
	});
}
	

		
// 	function ontransfer(parentId,tenderId){
	 
// 		/* 
// 		if (parentId==-1) {
// 			layer.msg("暂不支持债权二次转让，敬请谅解！", 2, 5);
// 			return;
// 		}
// 		*/
		
// 		$.layer({
// 			type : 2,
// 			title : '转让',
// 			area : [ '700px', '630px' ],
// 			offset : [ '10px', '' ],
// 			maxmin : true,
// 			iframe : {
// 				src : '${basePath}/zhaiquan/toTransfer/'+tenderId+'.html'
// 			}
// 		});
//    }
	function findPage(pageNo){
		transfer(1,pageNo);
	}
</script>          
            
            
            
            