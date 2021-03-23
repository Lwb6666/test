<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/page/common/taglib.jsp"%>

<head>
<link href="${basePath}/css/circle.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${basePath}/js/superslide.2.1.js"></script>
<script type="text/javascript" src="${basePath}/js/common.js"></script>
</head>
<body>
		<table width="100%" border="0" style="vertical-align:middle; text-align:center;" cellspacing="0" cellpadding="0"  class="table center tbg">
                  <tr class="tbl-title">
                    <td class="tdpdtitle30" width="25%">借款标题</td>
                    <td class="textr"  width="12%" >借款金额</td>
                    <td class="textr"  width="9%">预期利率</td>
                    <td class="textr"  width="9%">期限</td>
                    <td class="textr"  width="12%">还款方式</td>
                    <td class="textr" width="15%">进度</td>
                    <td class="tdtitl06"  width="13%"></td>
                  </tr>
                  <c:forEach items="${page.result}" var="borrow" varStatus= "idx">
                  <c:set var="btype" value="${borrow.borrowtype }" scope="request"  />
                  <c:choose>   
						<c:when test="${btype == 1}">
							<c:set var="cls" value="icon-xin" scope="request"  />
						</c:when>   
						<c:when test="${btype == 2}">
							<c:set var="cls" value="icon-di" scope="request"  /> 
						</c:when>
						<c:when test="${btype == 3}">
							<c:set var="cls" value="icon-jing" scope="request"  />  
						</c:when>
						<c:when test="${btype == 4}">
							<c:set var="cls" value="icon-miao" scope="request"  />   
						</c:when>
						<c:when test="${btype == 5}">
							<c:set var="cls" value="icon-bao" scope="request"  />     
						</c:when>
						<c:otherwise></c:otherwise> 
				  </c:choose>
                  <tr >
                    <td class="tdtitl07 bule" style="vertical-align:middle; text-align:left;">
                         <c:if test="${borrow.isCustody==1}">
                            <span class="icon icon-zs pdr bule">
                         </c:if>
                         </span><span class="icon ${cls} pdr"></span>
                         <c:if test="${null!=borrow.bidPassword && borrow.bidPassword!= ''}"><font color="#EE30A7">[密]</font></c:if> 
                           		<a href="javascript:;"  style="color: #333;"   onclick="goToInvest(${borrow.id});">
                             		<c:if test="${borrow.name==null }">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</c:if>
                             		${borrow.subName}
                             </a>
                              
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
                    <td class="textr"> 
                       ${borrow.accountStr}元
                    </td>
                    <td class="textr">
                        <c:if test="${borrow.id==add5}">
					      <fmt:formatNumber value="${borrow.apr-5}" pattern="#,##0.##"/>%<span style="color:red;"><em>+5</em>%</span> 
					    </c:if> 
					    <c:if test="${borrow.id==add2}">
					       <fmt:formatNumber value="${borrow.apr-2}" pattern="#,##0.##"/> %<span style="color:red;"><em>+2</em>%</span> 
					    </c:if> 
					    <c:if test="${! (borrow.id==add5 or borrow.id==add2 or borrow.apr == '15.71') }">
					       <fmt:formatNumber value="${borrow.apr}" pattern="#,##0.##"/> %  
					    </c:if> 
                        <c:if test="${borrow.apr == '15.71'}">
					      <fmt:formatNumber value="${borrow.apr-0.71}" pattern="#,##0.##"/> %
					     <div style=" height:25px;color:red;position: absolute; background: red;color: #fff;line-height: 25px;  top:-5px;right:-30%; border-radius: 5px;">+0.71%</div>
						</c:if> 
                    </td>
                    <td class="textr">
	                    <c:if test="${borrow.borrowtype==4}">秒还</c:if>
				 		<c:if test="${borrow.borrowtype!=4 && borrow.style !=4 }"> ${borrow.timeLimit } 个月</c:if>
				 		<c:if test="${borrow.borrowtype!=4 && borrow.style ==4 }"> ${borrow.timeLimit } 天</c:if>
                    </td>
                    <td class="textr" >
	                    <c:choose>
		                    <c:when test="${borrow.style == 0}">
								没有限制     
							</c:when>   
							<c:when test="${borrow.style == 1}">
								等额本息
							</c:when>   
							<c:when test="${borrow.style == 2}">
								<a href="javascript:void(0);" style="color: #333;" title="按月付息到期还本">按月付息</a> 
							</c:when>
							<c:when test="${borrow.style == 3}">
								<a href="javascript:void(0);"  style="color: #333;" title="到期还本付息">到期还本</a> 
							</c:when>
							<c:when test="${borrow.style == 4}">
								按天还款   
							</c:when>
							<c:otherwise></c:otherwise> 
						</c:choose> 
                    </td>
                    <td class="textr">
                        <div class="votebox fr" style="width:120px;overflow:hidden;">
                            <dl class="barbox fl">
                                <dd class="barline" style="width:60px;">
                                    <div w="${borrow.scheduleStrNoDecimal}" style="width:0px;" class="charts"></div>
                                </dd>   
                            </dl><span   class="fr">${borrow.scheduleStrNoDecimal}%</span>
                    	 </div>
                   </td>
                   <td  class="tdtitl06">
                        
                        <c:if test="${borrow.status==1}">
							<input type="button"  class="btn-middle btn-ywc" value="敬请期待" onclick="goToInvest(${borrow.id});"/>
						</c:if>
						<c:if test="${borrow.status==42}">
							<input type="button" class="btn-middle btn-ywc" value="已垫付" onclick="goToInvest(${borrow.id});"/>
						</c:if>
						<c:if test="${borrow.status==2 }">
							<input type="button" class="btn-middle btn-blue" value="投标中" onclick="goToInvest(${borrow.id});"/>
						</c:if>
						<c:if test="${borrow.status==4 }">
							<input type="button" class="btn-middle btn-yfz" value="还款中" onclick="goToInvest(${borrow.id});"/>
						</c:if>
						<c:if test="${borrow.status==5 }">
							<input type="button" class="btn-middle btn-ywc" value="还款结束"  onclick="goToInvest(${borrow.id});"/>
						</c:if>
						<c:if test="${borrow.status==6 }">
							<input type="button" class="btn-middle btn-yfz" value="银行复审中"  onclick="goToInvest(${borrow.id});"/>
						</c:if>
						<c:if test="${borrow.status==7 }">
							<input type="button" class="btn-middle btn-yfz" value="流标复审中"  onclick="goToInvest(${borrow.id});"/>
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
<style>
.tbg tr:nth-of-type(odd){background:#f0f7ff;}
</style>
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
};
</script>
