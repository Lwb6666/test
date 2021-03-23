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
                                <th width="580px">借款标题</th>
                                <th width="300px">发标时间</th>    
                                <th width="100px">信用等级</th>
                                <th width="100px">金额</th>    
                                <th width="100px">期限</th>
                                <th width="120px">利率</th>
                                <th width="140px">还款方式</th>
                                <th width="100px" >进度</th>
                                <th width="140px">状态</th>     
                                </tr>
                           
                                <c:forEach items="${page.result}" var="borrow" varStatus= "idx">
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
                                  
                                    <c:if test="${null!=borrow.bidPassword && borrow.bidPassword!= ''}"><font color="#EE30A7">[密]</font></c:if> 
                                		<a href="javascript:;"    onclick="goToInvest(${borrow.id});">
		                                		<c:if test="${borrow.name==null }">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</c:if>
		                                		${borrow.subName}
		                                </a>
		                                <c:choose>
											<c:when test="${btype == 1 || btype == 2 || btype == 5}">
												<a href="##" title="${borrow.userName}">
		                                			<img src="${path}/images/user.png" width="20" height="20"/>
		                               			 </a>
											</c:when>
											<c:otherwise>
												<a href="##" title="${borrow.userNameSecret}">
		                                			<img src="${path}/images/user.png" width="20" height="20"/>
		                               			 </a>
											</c:otherwise>
										</c:choose>
										<c:if test="${borrow.areaType==1}">
										    	<c:if test="${borrow.id==add5}">
													   <font color="red">【518新人专享】</font>
											   </c:if>
											    <c:if test="${! (borrow.id==add5 ) }">
				                                  	<font color="red">【新人专享】</font>
				                               	</c:if>
										</c:if>
										<c:if test="${borrow.id==add2}">
													   <font color="red">【518活动专享】</font>
											   </c:if>
										  <c:if test="${borrow.id==add5 or borrow.id==add2}">
											    <i class="index-seal"></i>
										  </c:if>
										  <c:if test="${borrow.apr == '15.71'}">
												<span style="color:red;">【股市暖心】</span>
										   </c:if>
									</td>
						 
								<td align="center">
									<c:choose>
										<c:when test="${borrow.status==1}">${borrow.timingBorrowTimeStr}</c:when>
										<c:otherwise>${borrow.publishTimeStr}</c:otherwise>
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
	                                	<c:if test="${borrow.borrowtype==4}">秒还</c:if>
								 		<c:if test="${borrow.borrowtype!=4 && borrow.style !=4 }"><i>${borrow.timeLimit }</i>月</c:if>
								 		<c:if test="${borrow.borrowtype!=4 && borrow.style ==4 }"><i>${borrow.timeLimit }</i>天</c:if>
									</td>
	                                <td align="center" style="position: relative;">
	                                
	                                   <c:if test="${borrow.id==add5}">
									      <i><fmt:formatNumber value="${borrow.apr-5}" pattern="#,##0.##"/></i>%<span style="color:red;"><em>+5</em>%</span> 
									   </c:if> 
									    <c:if test="${borrow.id==add2}">
									     <i> <fmt:formatNumber value="${borrow.apr-2}" pattern="#,##0.##"/></i>%<span style="color:red;"><em>+2</em>%</span> 
									   </c:if> 
									    <c:if test="${! (borrow.id==add5 or borrow.id==add2 or borrow.apr == '15.71') }">
									     <i> <fmt:formatNumber value="${borrow.apr}" pattern="#,##0.##"/></i>%  
									   </c:if> 
	                                	<c:if test="${borrow.apr == '15.71'}">
									     <i> <fmt:formatNumber value="${borrow.apr-0.71}" pattern="#,##0.##"/></i>%
									     <div style=" height:25px;color:red;position: absolute; background: red;color: #fff;line-height: 25px;  top:-5px;right:-30%; border-radius: 5px;">+0.71%</div>
									    </c:if> 
	                               </td>
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
	<c:if test="${isDeault !=null}">
	   searchInvestListForIsDefault(pageNum);
	</c:if>
	<c:if test="${isDeault ==null}">
	  searchInvestList(pageNum);
	</c:if>
	
}

function goToInvest(borrowId){
	window.open("${path}/toubiao/"+borrowId+".html"); 
//    	window.open('http://chat.looyuoms.com/chat/chat/p.html?c=20000293&f=10042100&g=10047252&r=%23params%3A姓名%2C游客', '_blank', 'height=544, width=644,toolbar=no,scrollbars=no,menubar=no,status=no');
        

// 	$.post("${basePath}/member/isPayPasswordExist.html", {
// 	}, function(data) {
// 		if(data == 'notlogin'){
// 			alert('您还没有登录,请先登录！');
// 			window.location.href = "${path }/home/login.html";
// 		}else if(data == 'nopaypassword'){
// 			alert('您还没有设置交易密码,请先设置交易密码！');
// 			$("#modifyPasswd").click();
// 		}else if(data == 'success'){
// 			window.location.href = "${path }/newdxjr/investment/invest/toInvest.html?id="+borrowId;
// 		}
// 	});
};
</script>
