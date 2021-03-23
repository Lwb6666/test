<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/page/common/taglib.jsp"%>
   <table> 
         <thead>
                   <tr>
                     <th>借款标题 </th>
                     <th>年利率</th>
                     <th>还款方式</th>
                     <th>期限</th>
                     <th>期数</th>
                     <th>剩余投资本金</th>
                     <th>债权价格</th>
                     <th>下个还款日</th>
                     <th>转让操作</th>
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
                  
                  
	                    <tr id="row1"    <c:if test="${(index.index+1)%2==1}">class="trcolor parent"</c:if>    <c:if test="${(index.index+1)%2==0}"> class="parent" </c:if>>
		                     <td align="left"   ><em  class="${cls} lxnomargin"></em><a title="${transferVo.borrowName }"  href="${path }/toubiao/${transferVo.borrowId }.html"> <c:if test="${transferVo.parentId !=null }"><font  color='red' >[转]</font></c:if>   ${fn:substring(transferVo.borrowName,0,10)}<c:if test="${fn:length(transferVo.borrowName)>10}">..</c:if></a></td>
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
		                     <td><input type="button" value="转让" class="but bluess"  onclick="ontransfer(${transferVo.parentId !=null?-1:0},${transferVo.tenderId})"   ></td>
	                      </tr>
	                   </c:forEach>
	                   
                </tbody>
   
   </table>
                    
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

<script type="text/javascript">

	function ontransfer(parentId,tenderId){
	 
		/* 
		if (parentId==-1) {
			layer.msg("暂不支持债权二次转让，敬请谅解！", 2, 5);
			return;
		}
		*/
		
		$.layer({
			type : 2,
			title : '转让',
			area : [ '700px', '630px' ],
			offset : [ '10px', '' ],
			maxmin : true,
			iframe : {
				src : '${basePath}/zhaiquan/toTransfer/'+tenderId+'.html'
			}
		});
   }
	
	function findPage(pageNo){
		transfer(1,pageNo);
	}
	
	
	
	
</script>          
            
            
            
            