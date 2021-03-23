<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/page/common/taglib.jsp"%>
<%-- <%@ include file="/WEB-INF/page/common/public.jsp"%> --%>

<input type="hidden" id="type" value="weekInfo"/>
<input type="hidden" id="pageNum" value="${page.pageNo}"/>
<input type="hidden" id="totalPage" value="${page.totalPage}"/>
<input type="hidden" id="totalCount" value="${page.totalCount}"/>
<style type="text/css">
#invest-cent{width:1078px; padding:10px;;border-left:1px solid #dbdbdb; border-right:1px solid #dbdbdb; background:#fff; margin:0;}
</style>
<div id="invest-cent"> 
   <select name="search_type" id="search_type" style="width: 100px; margin-right:10px; " onchange="search();">
      <option value="week" <c:if test="${search_type==null || search_type == 'week'}">selected="selected"</c:if>>本周内</option>
	  <option value="month" <c:if test="${search_type == 'month'}">selected="selected"</c:if>>本月内</option>
	  <option value="quarter" <c:if test="${search_type == 'quarter'}">selected="selected"</c:if>>本季度内</option>
	  <option value="year" <c:if test="${search_type == 'year'}">selected="selected"</c:if>>本年度内</option> 
   </select>
   <span class="zi1" >
     <font color="red">
     <c:if test="${search_type==null or search_type == 'week'}">本周内</c:if>
     <c:if test="${search_type == 'month'}">本月内</c:if>
     <c:if test="${search_type == 'quarter'}">本季度内</c:if>
     <c:if test="${search_type == 'year'}">本年度内</c:if>
     </font>
     <%-- <span>应归还款额：</span><font color="red">￥<fmt:formatNumber value="${repaymentChartVoMap.repaymentAccount}" pattern="#,##0.00"/></font>(<span>其中已归还：</span><font color="red">￥<fmt:formatNumber value="${repaymentChartVoMap.yesRepaymentAccount}" pattern="#,#00.00"/></font>，<span>未归还：</span><font color="red">￥<fmt:formatNumber value="${repaymentChartVoMap.noRepaymentAccount}" pattern="#,#00.00"/></font>)
   </span> --%>
   
    <span>应归还款额：</span><font color="red"><c:if test = "${repaymentChartVoMap.repaymentAccount != 0}">￥<fmt:formatNumber value="${repaymentChartVoMap.repaymentAccount}" pattern="#,##0.00"/></c:if><c:if test = "${repaymentChartVoMap.repaymentAccount == 0}">￥0</c:if></font>
     (<span>其中已归还：</span><font color="red"><c:if test = "${repaymentChartVoMap.yesRepaymentAccount != 0}">￥<fmt:formatNumber value="${repaymentChartVoMap.yesRepaymentAccount}" pattern="#,#00.00"/></c:if><c:if test = "${repaymentChartVoMap.yesRepaymentAccount  == 0}">￥0</c:if></font>，<span>未归还：</span><font color="red"><c:if test = "${repaymentChartVoMap.noRepaymentAccount != 0}">￥<fmt:formatNumber value="${repaymentChartVoMap.noRepaymentAccount}" pattern="#,#00.00"/></c:if><c:if test = "${repaymentChartVoMap.noRepaymentAccount  == 0}">￥0</c:if></font>)
   </span>
   
</div>

<div class="caiwubg"> 
  	<table class="nobordertop">
       <tr>
       <th style="text-align: center;height:38px;" >借款标题</th>
       <th style="text-align: center;">类型</th>
       <th style="text-align: center;">借款人</th>
       <th style="text-align: center;">期数</th>
       <th style="text-align: center;">应还本金</th>
       <th style="text-align: center;">应还直通车金额</th>
       <th style="text-align: center;">应还利息</th>
       <th style="text-align: center;">应还罚息</th>
       <th style="text-align: center;">应还日期</th>
       <th style="text-align: center;">还款状态</th>
      </tr>
      <c:forEach items="${page.result}" var="repaymentChartVo" varStatus="s">
      <tr <c:if test="${s.index%2==1}">bgcolor="#ecfafd"</c:if>>
       <td><a href="${path }/toubiao/${repaymentChartVo.borrowId}.html" title="${repaymentChartVo.borrowName}">${fn:substring(repaymentChartVo.borrowName,0,12)}<c:if test="${fn:length(repaymentChartVo.borrowName)>12}">..</c:if></a></td>
       <td align="center">
       <c:if test="${repaymentChartVo.borrowType==1}">信用标</c:if>
       <c:if test="${repaymentChartVo.borrowType==2}">抵押标</c:if>
       <c:if test="${repaymentChartVo.borrowType==3}">净值标</c:if>
       <c:if test="${repaymentChartVo.borrowType==5}">担保标</c:if>
       </td>
       <td align="center"><a href="${path}/accountdetail/show.html?userName=${portal:encode(portal:encode(repaymentChartVo.userNameEncrypt))}"  target="_blank" title="${repaymentChartVo.userName}">${repaymentChartVo.userName}</a></td>
       <td align="center">
    		<c:if test="${repaymentChartVo.style == 1 || repaymentChartVo.style == 2}">
            ${repaymentChartVo.order}/${repaymentChartVo.timeLimit }
            </c:if>
            <c:if test="${repaymentChartVo.style != 1 && repaymentChartVo.style != 2}">
            ${repaymentChartVo.order}/1
            </c:if>
       </td>
       <td align="center"> <font color="#00a7e5">￥<fmt:formatNumber value="${repaymentChartVo.capital}" pattern="#,##0.00"/></font></td>
       <td align="center"> <font color="#00a7e5">
       	  <c:if test="${repaymentChartVo.firstAccount != null}">￥<fmt:formatNumber value="${repaymentChartVo.firstAccount}" pattern="#,##0.00"/></c:if>
	      <c:if test="${repaymentChartVo.firstAccount == null}">￥0.00</c:if>
       </font>
       </td>
       <td align="center"> <font color="#00a7e5">￥<fmt:formatNumber value="${repaymentChartVo.interest}" pattern="#,##0.00"/></font></td>
       <td align="center"> <font color="#00a7e5">￥<fmt:formatNumber value="${repaymentChartVo.lateInterest}" pattern="#,##0.00"/></font></td>
       <td align="center"> ${repaymentChartVo.repaymentTimeStr}</td>
       <td align="center"><font color="#00a7e5">
       	  <c:if test="${repaymentChartVo.status == 0 && repaymentChartVo.webstatus == 0}">未还款</c:if>
          <c:if test="${repaymentChartVo.status == 0 && repaymentChartVo.webstatus == 1}">已垫付</c:if>
          <c:if test="${repaymentChartVo.status == 1}">
          <%-- ${repaymentChartVo.repaymentYestimeStr} --%>
          	已还款
          </c:if>
       </font></td>
      </tr>
      </c:forEach>
   </table>
</div>

<!-- 翻页 -->            
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
