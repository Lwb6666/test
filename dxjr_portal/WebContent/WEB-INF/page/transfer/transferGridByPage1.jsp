<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/page/common/taglib.jsp"%>
<head>
<link href="${basePath}/css/circle.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${basePath}/js/superslide.2.1.js"></script>
<script type="text/javascript" src="${basePath}/js/common.js"></script>
</head>
<body>
		<table width="100%" border="0" cellspacing="0" cellpadding="0"  class="table center tbg">
                  <tr class="tbl-title">
                    <td class="tdpdtitle30" width="16%">借款标题</td>
                    <td class="textr" width="12%">转出价格</td>
                    <td class="textr" width="10%">预期利率</td>
                    <td class="textr" width="10%">剩余期限</td>
                    <td class="textr" width="16%">发布时间</td>
                    <td class="textr" width="14%">进度</td>
                    <td class="tdtitl06" width="13%"></td>
                  </tr>
                  <c:forEach items="${page.result}" var="transfer" varStatus="idx">
                    <c:set var="btype" value="${transfer.borrowType}" scope="request"/>
                    <c:choose>   
						<c:when test="${transfer.borrowType == 1}">
							<c:set var="cls" value="icon-xin" scope="request"  />
						</c:when>   
						<c:when test="${transfer.borrowType == 2}">
							<c:set var="cls" value="icon-di" scope="request"  /> 
						</c:when>
						<c:when test="${transfer.borrowType == 3}">
							<c:set var="cls" value="icon-jing" scope="request"  />  
						</c:when>
						<c:when test="${transfer.borrowType == 4}">
							<c:set var="cls" value="icon-miao" scope="request"  />   
						</c:when>
						<c:when test="${transfer.borrowType == 5}">
							<c:set var="cls" value="icon-bao" scope="request"  />     
						</c:when>
						<c:otherwise></c:otherwise> 
				  </c:choose>
                  <tr style="position:relative;">
                    <td class="tdtitl07 bule" style="position:relative;">
                        <span  class="icon ${cls} pdr" style="color:#333;width: 21px;" ></span>
                        <span><a href="javascript:;" style="color:#333;width:83px; " onclick="toTransfer(${transfer.id});">
                         <c:if test="${transfer.borrowName==null }">&nbsp;&nbsp;&nbsp;&nbsp;</c:if>
                          ${transfer.subName}
                         </a></span>
                        <c:if test="${null!=transfer.bidPassword && transfer.bidPassword!= ''}">
                         <span style="color:#f05e13;width:45px; position:absolute;right:15px;  *right:80px;  *top:5px;">【密】</span>
                        </c:if> 
                    </td>
                     
                    <td class="textr">${transfer.accountRealStr}元</td>
                    <td class="textr"><fmt:formatNumber value="${transfer.nowApr}" pattern="#,##0.##"/>%</td>
                    <td class="textr">
                       <c:if test="${transfer.borrowStyle ==1 || transfer.borrowStyle ==2}"> ${transfer.timeLimitReal}个月</c:if>	
					   <c:if test="${transfer.borrowStyle ==4 || transfer.borrowStyle ==3 }">${transfer.timeLimit }天</c:if>
                    </td>
                    <td class="textr"><fmt:formatDate value="${transfer.addTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
                    <td class="textr" >
                        <div class="votebox fr" style="width:120px; overflow:hidden">
                            <dl class="barbox fl">
                                <dd class="barline"  style="width:60px;">
                                    <div w="${transfer.scheduleStrNoDecimal}" style="width:0px;" class="charts"></div>
                                </dd>   
                            </dl><span class="fr" >${transfer.scheduleStrNoDecimal}%</span>
                    	 </div>
                   </td>
                    <td  class="tdtitl06">
                    <c:if test="${transfer.status==2}">
                      <button type="button" class="btn-middle btn-blue" onClick="toTransfer(${transfer.id });">转让中</button>
                    </c:if>
                    <c:if test="${transfer.status==4}">
                      <button type="button" class="btn-middle btn-ywc" onClick="toTransfer(${transfer.id });">已转让</button>
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
	searchTransferList(pageNum);
}

function toTransfer(transferId){
	window.open("${path}/zhaiquan/"+transferId+".html"); 

};
function goToInvest(borrowId){
	window.open("${path}/toubiao/"+borrowId+".html"); 
};
</script>
