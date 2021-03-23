<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/page/common/taglib.jsp"%>

<form id="form1" action="" method="post">
   <div class="searchbox fl  whitebg">
      <ul class="lb_title nobordertop">
          <li>借款标题：<input class="inputText02" name="keyword" id="keyword" value="${keyword}"></li>
          <li>应还款时间：
            <input class="inputText02" name="beginTime" id="beginTime" class="Wdate" value="${beginTime}" onClick="WdatePicker();">
            ~
            <input class="inputText02" name="endTime" id="endTime" class="Wdate" value="${endTime}" onClick="WdatePicker();">
          </li>
          <li class="lb_culi" ><span class="lb_title_btn" ><a href="javascript:;" onclick="queryRePaymentDetails(1,${status})">查询</a></span></li>                            
          <c:if test="${status == 0 }">
          <li class="lb_culi"><span class="lb_title_btn" ><a href="javascript:;" onclick="exportRepaymentRecord(${status});">导出excel</a></span></li>
      	  </c:if>
      </ul>
   </div>
   <div class="clear"></div>
   <div class="tabtoptext whitebg">
		查询区间段${status==0?'待还':'已还' }总额：<a href="#">￥<fmt:formatNumber value="${map.repaymentAccountTotal}" pattern="#,##0.00"/></a>，
		其中本金：<a href="#">￥<fmt:formatNumber value="${map.capitalTotal}" pattern="#,##0.00"/></a>，
		利息：<a href="#">￥<fmt:formatNumber value="${map.interestTotal}" pattern="#,##0.00"/></a> ，
		逾期罚息：<a href="#">￥<fmt:formatNumber value="${map.lateInterestTotal}" pattern="#,##0.00"/></a>
    </div>               
    <div class="myid whitebg">
       <!-- <div id="menu_right" > -->
         <table>
            <tr>
               <td>${status==0?'待还':'已还' }款时间</td>
               <td><a>借款标题</a></td>
               <td>周期</td>
               <td>预期利率</td>
               <td>期数</td>
               <td>还款总额</td>
               <td>应还本金</td>
               <td>应还利息</td>
               <td>逾期罚息</td>
               <c:if test="${status==0 }">
               <td>操作</td>
               </c:if>
           </tr>
           <c:forEach items="${page.result}" var="repaymentRecord" varStatus="sta" step="1">
               <tr <c:if test="${sta.index%2==0 }">class="trcolor"</c:if>>
                 <td><fmt:formatDate value="${status==0?repaymentRecord.repaymentTimeDate:repaymentRecord.repaymentTime2 }" pattern="yyyy-MM-dd"/></td>
                 <td><a href="${path}/toubiao/${repaymentRecord.borrowId}.html" title="${repaymentRecord.name }">${fn:substring(repaymentRecord.name,0,10)}<c:if test="${fn:length(repaymentRecord.name)>10}">..</c:if></a></td>
                 
                 <td width="64"><c:if test="${repaymentRecord.borrowtype==4}">秒还</c:if> <c:if
						test="${repaymentRecord.borrowtype!=4 && repaymentRecord.style != 4 }"> ${repaymentRecord.timeLimit }个月</c:if>
					<c:if test="${repaymentRecord.borrowtype!=4 && repaymentRecord.style == 4 }">${repaymentRecord.timeLimit }天</c:if>
				</td>
                 <td><c:if test="${repaymentRecord.aprStr==null}">0</c:if>${repaymentRecord.aprStr }%</td>
                 <td>
                 <c:if test="${repaymentRecord.style == 1 || repaymentRecord.style == 2}">
                 ${repaymentRecord.order}/${repaymentRecord.timeLimit }
                 </c:if>
                 <c:if test="${repaymentRecord.style != 1 && repaymentRecord.style != 2}">
                 ${repaymentRecord.order}/1
                 </c:if>
                 </td>
                 <td class="numcolor"><fmt:formatNumber value="${repaymentRecord.repaymentAccount}" pattern="#,##0.00"/></td>
                 <td class="numcolor"><fmt:formatNumber value="${repaymentRecord.capital}" pattern="#,##0.00"/></td>
                 <td class="numcolor"><fmt:formatNumber value="${repaymentRecord.interest}" pattern="#,##0.00"/></td>
                 <td class="numcolor"><fmt:formatNumber value="${repaymentRecord.lateInterest}" pattern="#,##0.00"/></td>
                 <c:if test="${status==0 }">
                 <td>
				   <c:if test="${repaymentRecord.status==0}">
				  		 <a href="javascript:repay_borrow(${repaymentRecord.id });" id="repayBorrow">立即还款</a>
				   </c:if>	                                    	
				 </td>
				 </c:if>
				</tr>
			 </c:forEach>                             
		  </table>
		<!-- </div> -->
		
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
	</div>
</form>

<script type="text/javascript">
/**翻页*/
function findPage(pageNum){
	queryRePaymentDetails(pageNum,'${status}');
}
</script>
