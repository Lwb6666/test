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
                                <th width="260px">借款标题</th>
                                <th width="220px">发布时间</th>
                                <th width="100px">剩余期限</th>        
                                <th width="80px">原利率</th>
                                <th width="80px">现利率</th>
                                <th width="140px">还款方式</th>
                                <th width="80px" >系数</th>
                                <th width="110px" >转出价格</th>
                                <th width="80px">进度</th>
                                <th width="140px">状态</th>     
                                </tr>
                           
                                <c:forEach items="${page.result}" var="transfer" varStatus= "idx">
                                <c:set var="btype" value="${transfer.borrowtype }" scope="request"  />
	 
									<c:choose>   
										<c:when test="${transfer.borrowType == 1}">
											<c:set var="cls" value="xin" scope="request"  />
										</c:when>   
										<c:when test="${transfer.borrowType == 2}">
											<c:set var="cls" value="di" scope="request"  /> 
										</c:when>
										<c:when test="${transfer.borrowType == 3}">
											<c:set var="cls" value="jing" scope="request"  />  
										</c:when>
										<c:when test="${transfer.borrowType == 4}">
											<c:set var="cls" value="" scope="request"  />   
										</c:when>
										<c:when test="${transfer.borrowType == 5}">
											<c:set var="cls" value="bao" scope="request"  />     
										</c:when>
										<c:otherwise></c:otherwise> 
									</c:choose> 
	 
                                	<tr>
                                    <td><em class="${cls }"></em> 
                                    <c:if test="${null!=transfer.bidPassword && transfer.bidPassword!= ''}"><font color="#EE30A7">[密]</font></c:if> 
											<a href="javascript:;" onclick="toTransfer(${transfer.id});">
		                                		<c:if test="${transfer.borrowName==null }">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</c:if>
		                                		${transfer.subName}
		                                </a>
		                                <a href="##" title="${transfer.userNameSecret}">
		                                	<img src="${basePath}/images/user.png" width="20" height="20"/>
		                                </a>
									</td>
                               
                               		<td align="center" width="220px;"><i><fmt:formatDate value="${transfer.addTime}" pattern="yyyy-MM-dd HH:mm:ss"/></i></td>
                               		
                               		<td align="center" width="100px;">
								 		<c:if test="${transfer.borrowStyle ==1 || transfer.borrowStyle ==2}"><i>
								 			${transfer.timeLimitReal }
								 		</i>月</c:if>	
								 		<c:if test="${transfer.borrowStyle ==4 || transfer.borrowStyle ==3 }"><i>${transfer.timeLimit }</i>天</c:if>
                               		</td>
                               		
	                                <td align="center"><i><fmt:formatNumber value="${transfer.borrowApr}" pattern="#,##0.##"/></i>%</td>
	                                <td align="center"><i><fmt:formatNumber value="${transfer.nowApr}" pattern="#,##0.##"/></i>%</td>
	                                
	                                <td align="center">
		                                <c:choose>
		                                	<c:when test="${transfer.borrowStyle == 0}">
												没有限制     
											</c:when>   
											<c:when test="${transfer.borrowStyle == 1}">
												等额本息
											</c:when>   
											<c:when test="${transfer.borrowStyle == 2}">
												按月付息
											</c:when>
											<c:when test="${transfer.borrowStyle == 3}">
												到期还本
											</c:when>
											<c:when test="${transfer.borrowStyle == 4}">
												按天还款   
											</c:when>
											<c:otherwise></c:otherwise> 
										</c:choose> 
	                                </td>

									<td align="center"><i><fmt:formatNumber value="${transfer.coef}" pattern="#,##0.###"/></i></td>
									
									<td align="center" width="110px;"><i>${transfer.accountRealStr}元</i></td>
									
									<td align="center" width="80px;">
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
		                                    <DIV class="arc1"><INPUT id="percent${idx.index}" class="percent1" value="${transfer.scheduleStrNoDecimal }" type="hidden" 
		                                    default="0">	
		                                    <c:choose>
														<c:when test="${transfer.scheduleStrNoDecimal < 100}">
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
	                                	<c:if test="${transfer.status==2}">
											<input type="button" class="but bluess" value="转让中" onclick="toTransfer(${transfer.id });"/>
										</c:if>
										<c:if test="${transfer.status==4}">
											<input type="button" class="but gray" value="已转让" onclick="toTransfer(${transfer.id});"/>
										</c:if>

	                                </td>
	                                </tr>
                                </c:forEach>	
</table>
 			<c:if test="${page.result.size()==0}">
                  <center><b>没有债权转让项目</b></center>
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

function toTransfer(transferId){
	window.open("${path}/zhaiquan/"+transferId+".html"); 

};
function goToInvest(borrowId){
	window.open("${path}/toubiao/"+borrowId+".html"); 
};

</script>
