<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/page/common/taglib.jsp"%>
<table width="100%" border="0" cellspacing="0" cellpadding="0" class="table center tbg">
                          <tr class="tbl-title">
                            <td  width="30%">债权人</td>
                            <td  width="30%">交易时间</td>
                            <td  style="text-align: right;width:30%;">投标金额</td>
                          </tr>
                          <c:forEach   var="subscribeVo"  items="${page.result}"   varStatus="index">    
                          <tr>
                           <td>
                           <a       
                                <c:if test="${portal:currentUser() != null and  portal:currentUser().userName eq subscribeVo.userName}">style="color:blue;"</c:if>   
                                    href="${path}/accountdetail/show.html?userName=${portal:encode(portal:encode(subscribeVo.userNameEncrypt))}" target="_blank">${portal:currentUser() != null and  portal:currentUser().userName eq subscribeVo.userName?subscribeVo.userName:subscribeVo.userNameSecret} 
   									<c:if test="${subscribeVo.isTransfer==1 and subscribeVo.firstTenderRealId == null}"><font  color='red' >[转]</font></c:if>
                           			<c:if test="${subscribeVo.firstTenderRealId != null and subscribeVo.firstTenderRealId != '' and subscribeVo.firstStatus==3}"><font  color='red' >[车转]</font></c:if>
                           			<c:if test="${subscribeVo.isTransfer==2 and (subscribeVo.firstTenderRealId == '' or subscribeVo.firstTenderRealId == null)}"><font  color='red' >[定转]</font></c:if>
                           			<c:if test="${subscribeVo.isTransfer==3 and (subscribeVo.firstTenderRealId == '' or subscribeVo.firstTenderRealId == null)}"><font  color='red' >[定]</font></c:if>
                           </a>
                           
                           </td >
                           <td     <c:if test="${portal:currentUser() != null and  portal:currentUser().userName eq subscribeVo.userName}">style="color:blue;"</c:if>   ><fmt:formatDate value="${subscribeVo.addTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
                           <td style="text-align: right;" <c:if test="${portal:currentUser() != null and  portal:currentUser().userName eq subscribeVo.userName}">style="color:blue;"</c:if>><fmt:formatNumber value="${subscribeVo.account}" type="currency" pattern="#,##0.##"/>元</td>

                          </tr>
                          </c:forEach> 
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
<style>
.tbg tr:nth-of-type(odd){background:#f0f7ff;}
} 
</style>
<script type="text/javascript">
function findPage(pageNum){
	searchtransferinfo(pageNum);
}
</script>