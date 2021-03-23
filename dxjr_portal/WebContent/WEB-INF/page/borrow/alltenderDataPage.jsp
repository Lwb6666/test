<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/page/common/taglib.jsp"%>

<head>
<link href="${basePath}/css/circle.css" rel="stylesheet" type="text/css" />


<script type="text/javascript" src="${basePath}/js/init.js"></script>
<script type="text/javascript" src="${basePath}/js/raphael.js"></script>
</head>
<body>
     
                               
                                <table border="0"  >
                                <tr>
                                <th width="510px">借款标题</th>
                                <th width="300px">发布时间</th>    
                                <th width="100px">信用等级</th>
                                <th width="100px">金额</th>    
                                <th width="100px">期限</th>
                                <th width="100px">利率</th>
                                <th width="140px">还款方式</th>
                                <th width="100px" >进度</th>
                                <th width="140px">状态</th>     
                                </tr>
                           
                                <c:forEach items="${page.result}" var="borrow" varStatus="idx">
                                <c:set var="btype" value="${borrow.borrowtype }" scope="request"  />
	 
									<c:choose>   
										<c:when test="${btype == 1}">
											<c:set var="cls" value="xin" scope="request"  />
										</c:when>   
										<c:when test="${btype == 2}">
											<c:set var="cls" value="di" scope="request"  /> 
										</c:when>
										<c:when test="${btype == 3}">
											<c:set var="cls" value="jing" scope="request"  />  
										</c:when>
										<c:when test="${btype == 4}">
											<c:set var="cls" value="" scope="request"  />   
										</c:when>
										<c:when test="${btype == 5}">
											<c:set var="cls" value="bao" scope="request"  />     
										</c:when>
										<c:otherwise></c:otherwise> 
									</c:choose> 
	 
                                	<tr>
                                    <td><em class="${cls }"></em> 
                                     <c:if test="${borrow.isTransfer==1}"> <font color="#EE30A7">[转]</font> </c:if>
                                    <c:if test="${null!=borrow.bidPassword && borrow.bidPassword!= ''}"><font color="#EE30A7">[密]</font></c:if> 
                                		<a href="javascript:;" onclick="${borrow.isTransfer==1?'goToTransfer':'goToInvest'}(${borrow.id});">
		                                		<c:if test="${borrow.name==null }">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</c:if>
		                                		${borrow.subName}
		                                </a>
		                                <c:if test="${borrow.isFinancialUser==0}">
		                                <a href="##" title="${borrow.userName}">
		                                	<img alt="小额贷款" src="${basePath}/images/user.png" width="20" height="20"/>
		                               	</a>
		                               	</c:if>
		                               	<c:if test="${borrow.isFinancialUser==1}">
		                               	<a href="##" title="${borrow.userNameSecret}">
		                                	<img alt="小额贷款" src="${basePath}/images/user.png" width="20" height="20"/>
		                               	</a>	
		                               	</c:if>	 
		                               	<c:if test="${borrow.areaType==1}"><font color="red">【新手专区】</font></c:if>
									</td>
						 
								<td align="center">
									<c:choose>
										<c:when test="${borrow.status==1}">${borrow.timingBorrowTimeStr}</c:when>
										<c:otherwise>
										  <c:if test="${borrow.isTransfer==0}">
										     ${borrow.publishTimeStr}
										   </c:if>
										   <c:if test="${borrow.isTransfer==1}">
										     ${borrow.addtimeStr}
										   </c:if>
										</c:otherwise>
									</c:choose>
								</td>
								
								
                              		<td align="center">
                              			<c:if test="${borrow.creditRating== 'A'}">
                              				<img src="${basePath}/images/A.gif" width="34" height="34"/>
                              			</c:if>
                              			
                              			<c:if test="${borrow.creditRating== 'B'}">
                              				<img src="${basePath}/images/B.gif" width="34" height="34"/>
                              			</c:if>
                              			
                              			<c:if test="${borrow.creditRating== 'C'}">
                              				<img src="${basePath}/images/c.gif" width="34" height="34"/>
                              			</c:if>
                              			
                              			<c:if test="${borrow.creditRating== 'D'}">
                              				<img src="${basePath}/images/dd.gif" width="34" height="34"/>
                              			</c:if>
                              		</td>
                               
	                                <td align="center" width="220px;"><i>${borrow.accountStr}</i>元</td>
	                                
	                               
	                                <td align="center">
	                                 <c:if test="${borrow.isTransfer==0}">
	                                	<c:if test="${borrow.borrowtype==4}">秒还</c:if>
								 		<c:if test="${borrow.borrowtype!=4 && borrow.style !=4 }"><i>${borrow.timeLimit }</i>月</c:if>
								 		<c:if test="${borrow.borrowtype!=4 && borrow.style ==4 }"><i>${borrow.timeLimit }</i>天</c:if>
								 	</c:if>
									 <c:if test="${borrow.isTransfer==1}">
									    <c:if test="${borrow.style==1 or borrow.style==2}">${borrow.timeLimit }月</c:if>
									    <c:if test="${borrow.style==3 or borrow.style==4}">${borrow.timeLimit }天</c:if>
									</c:if>
									
									</td>
	                                <td align="center"><i><fmt:formatNumber value="${borrow.apr}" pattern="#,##0.##"/></i>%</td>
	                                <td align="center">
		                                <c:choose>
		                                	<c:when test="${borrow.style == 0}">
												没有限制     
											</c:when>   
											<c:when test="${borrow.style == 1}">
												等额本息
											</c:when>   
											<c:when test="${borrow.style == 2}">
												<a href="##" title="按月付息到期还本">按月付息</a> 
											</c:when>
											<c:when test="${borrow.style == 3}">
												<a href="##" title="到期还本付息">到期还本</a> 
											</c:when>
											<c:when test="${borrow.style == 4}">
												按天还款   
											</c:when>
											<c:otherwise></c:otherwise> 
										</c:choose> 
	                                </td>
	                                <td align="center">
	                                	   <script type="text/javascript">
  
												initProgress();
												 function initProgress(){
												   	 $(".loading #diagram").empty();
												 
												   		var   count=  $("#percent${idx.index}").val(); 
													  
													   	if(Number(count) < 0.001){
															oo.num = 0.01;
															oo.cicleNum = count;
														}else{
															oo.num = count;
															oo.cicleNum = count;
														}
														oo.diagramDiv = "diagram${idx	.index}";
														oo.getDiv = ".get1";
														oo.init();
													   
														
	 												}
	                                 		   </script>      
	                                
	                       
                                   		    <DIV    style="width:40px; height:40px; margin:0 auto;" id="diagram${idx.index}"></DIV>
		                                    <DIV style="display: none;" class="get1">
		                                    <DIV class="arc1"><INPUT id="percent${idx.index}" class="percent1" value="${borrow.scheduleStrNoDecimal }" type="hidden" 
		                                    default="0">	
		                                    <c:choose>
														<c:when test="${borrow.scheduleStrNoDecimal < 100}">
															<INPUT class="color1" value="#00a7e5" type="hidden">
														</c:when>
														<c:otherwise>
															<INPUT class="color1" value="#94c73d" type="hidden">
														</c:otherwise>
													</c:choose> 
		                                    </DIV>
		                                    </DIV>
		                                  
                                   		 
									</td>
	                                <td align="center">
	                                <c:if test="${borrow.isTransfer==0}">
	                                	<c:if test="${borrow.status==1}">
											<input type="button" class="but" value="预发中" onclick="goToInvest(${borrow.id});"/>
										</c:if>
										<c:if test="${borrow.status==42}">
											<input type="button" class="but yellow" value="已垫付" onclick="goToInvest(${borrow.id});"/>
										</c:if>
										<c:if test="${borrow.status==2 }">
											<input type="button" class="but bluess" value="${portal:desc(100, borrow.status)}" onclick="goToInvest(${borrow.id});"/>
										</c:if>
										<c:if test="${borrow.status==4 }">
											<input type="button" class="but greens" value="${portal:desc(100, borrow.status)}" onclick="goToInvest(${borrow.id});"/>
										</c:if>
										<c:if test="${borrow.status==5 }">
											<input type="button" class="but gray" value="${portal:desc(100, borrow.status)}" onclick="goToInvest(${borrow.id});"/>
										</c:if>
									</c:if>	
										
										 <c:if test="${borrow.isTransfer==1}">
										 	<c:if test="${borrow.status==2 }">
										 	  <input type="button" class="but bluess" value="转让中" onclick="goToTransfer(${borrow.id});"/>
										 	</c:if>
										 	
										    <c:if test="${borrow.status==4 }">
										 	  <input type="button" class="but gray" value="已转让" onclick="goToTransfer(${borrow.id});"/>
										 	</c:if>
										 </c:if>
										
										
	                                </td>
	                                </tr>
                                </c:forEach>	
</table>
 			<c:if test="${page.result.size()==0}">
                  <center><b>没有可投资项目</b></center>
            </c:if>
 
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
</body>
<script type="text/javascript">
function findPage(pageNum){
	searchTransferList(pageNum);
}

function goToInvest(borrowId){
	window.open("${path}/toubiao/"+borrowId+".html"); 
};

function goToTransfer(borrowId){
	window.open("${path}/zhaiquan/"+borrowId+".html"); 
}

</script>
