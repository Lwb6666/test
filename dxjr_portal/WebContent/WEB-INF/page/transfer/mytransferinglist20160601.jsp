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
                     <th>债权价格</th>
                     <th>转让价格 </th>
                     <th>转让系数</th>
                     <th>发布转让日期</th>
                     <th>操作</th>
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
	                
	                  <tr id="row1"    <c:if test="${(index.index+1)%2==1}">class="trcolor parent"</c:if>  <c:if test="${(index.index+1)%2==0}"> class="parent"</c:if>  >
	                                    <td align="left" ><em class="${cls} lxnomargin"></em><a  title="${transferVo.borrowName }" href="${path }/zhaiquan/${transferVo.id }.html"><font  color='red' >[转]</font>${fn:substring(transferVo.borrowName,0,10)}<c:if test="${fn:length(transferVo.borrowName)>10}">..</c:if></a></td>
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
					                     <td><input type="button"   id="cancelTransfer"   onclick="showCancelTransfer(${transferVo.id})"    value="取消" class="but bluess"></td>
                    </tr>
                </c:forEach>
    </tbody>
    
    </table>
                             <!--popowindow-->
                        <div class="sf"></div>
                        <div class="tip"></div>


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
                  


<div  id="cancelPop"  style="display: none"  >
 <div > 
	 <input  type="hidden" id="transferId" name="transferId"  />
	 <textarea  id="prompt"  rows="5" cols="40" style="margin: 20px;"  ></textarea> 
 </div>
 <span    >
	 <input style="cursor:pointer; margin-left: 70px;"   class="head_button"  onclick="cancelTransfer()"  value="确定"></input>
	 <input style="cursor:pointer;"   class="head_button" value="取消" onclick="closePop()" ></input>
 </span> 
</div>




<script type="text/javascript">
var  _loadPopIndex = 0;
 
function showCancelTransfer(transferId){
	$("#transferId").val(transferId);
	_loadPopIndex= $.layer({
		type: 1,   //0-4的选择,
		title:  ["请输入撤销原因" , true],
		closeBtn: [0 , true],
		area: ["350px","230px"],
		offset : [ '200px', '' ],
		page: {
			dom : '#cancelPop'
		}
	});
	 
}
 

function findPage(pageNo){
	transfer(2,pageNo);
}
 
 
function closePop(){
	layer.close(_loadPopIndex);
}
 
function cancelTransfer(){
	 
	/* if($.trim($("#prompt").val()).length==0){
		layer.msg('请输入撤销原因.', 1, 5);
		return ;
	} */
	 
		var  _load = layer.load('处理中..');
			$.ajax({
					url : '${basePath}/zhaiquan/cancelTransfer.html',
					data:{id:$("#transferId").val(),cancelRemark:$("#prompt").val()},
					type : 'post',
					dataType :"json",
					success : function(result) {
						closePop();
						layer.close(_load);
						  if (result.code == '0') {
								parent.layer.msg(result.message, 1, 5);
								loadimage();
							}else{
								  parent.layer.msg(result.message,1,1);
								  transfer(2,1);
							}
						  
					 },
					error : function(result) {
						closePop();
						layer.close(_load);
						layer.msg('网络连接超时,请您稍后重试.', 1, 5);
				    }
				});
		     
		
	 
	
}
 
 
 
 
 
</script>
 