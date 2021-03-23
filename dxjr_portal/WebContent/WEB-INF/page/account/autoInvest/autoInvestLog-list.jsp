<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/page/common/taglib.jsp"%>
	<table width="100%" border="0" cellspacing="0" cellpadding="0"
		class="table center tbtr02">
		<thead>
			<tr class="tbl-title">
				<td>排队号</td>
				<td>投标方式</td>
				<td>投标额度(元)</td>
				<td>投标金额(元)</td>
				<td>时间</td>
				<td>所投标的</td>
			</tr>
		</thead>
		<tbody>
		   <c:if test="${!empty page.result }">
              <c:forEach items="${page.result}" var="autoInvestConfigRecord" varStatus="sta" step="1">
			   <tr>
                <td>
                   <c:if test="${autoInvestConfigRecord.rownum!=null}">第&nbsp;<font color="red">${autoInvestConfigRecord.rownum}</font>&nbsp;位</c:if>
			       <c:if test="${autoInvestConfigRecord.rownum==null}">无</c:if>
                </td>
                <td> 
                   <c:if test="${autoInvestConfigRecord.tender_type==1 }">按金额投标</c:if>
				   <c:if test="${autoInvestConfigRecord.tender_type==2 }">按比例投标</c:if>
				   <c:if test="${autoInvestConfigRecord.tender_type==3 }">按余额投标</c:if>
                </td>
                <td>
                   <!-- 可投投标金额 -->
                   <c:if test="${autoInvestConfigRecord.tender_type==1 }">
			    	    <fmt:formatNumber value="${autoInvestConfigRecord.tender_account_auto}" pattern="#,##0.00"></fmt:formatNumber>
				   </c:if>
				   <c:if test="${autoInvestConfigRecord.tender_type==3 }">
				   		 <fmt:formatNumber value="${autoInvestConfigRecord.tender_record_accout}" pattern="#,##0.00"/>
				   </c:if>
                </td>
                <td>
                   <fmt:formatNumber value="${autoInvestConfigRecord.tender_record_accout}" pattern="#,##0.00"></fmt:formatNumber>
                </td>
                <td><fmt:formatDate value="${autoInvestConfigRecord.addtime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
                <td>  
                <c:if test="${autoInvestConfigRecord.borrowId != null}">
			      <a href="${path}/toubiao/${autoInvestConfigRecord.borrowId}.html" title="${autoInvestConfigRecord.borrowName }" target="_blank">${fn:substring(autoInvestConfigRecord.borrowName,0,10)}<c:if test="${fn:length(autoInvestConfigRecord.borrowName)>10}">..</c:if></a>
			    </c:if>
	            </td>
              </tr>
			  </c:forEach>
           </c:if>
            <c:if test="${empty page.result }">
	              <tr>
	                <td colspan="6"> <div class="zdtz-none"><p>暂无记录</p></div></td>
	              </tr>
            </c:if>
		</tbody>
	</table>
	 <c:if test="${!empty page.result }">
	<div>
		<jsp:include page="/WEB-INF/page/common/ajaxpage.jsp">
		<jsp:param name="pageNo" value="${page.pageNo}" />
		<jsp:param name="totalPage" value="${page.totalPage}" />
		<jsp:param name="hasPre" value="${page.hasPre}" />
		<jsp:param name="prePage" value="${page.prePage}" />
		<jsp:param name="hasNext" value="${page.hasNext}" />
		<jsp:param name="nextPage" value="${page.nextPage}" />
		</jsp:include>
	</div>
	  </c:if>
	<div class="zdtz-text clearboth">
		<dl>
			<dt>温馨提示</dt>
			<dd>
				<p>1、用户只能添加1条自动投标规则，当启用规则时，将自动从最后一名开始排队，系统根据排队号来自动投标。</p>
				<p>2、当您启用规则在90天内未发生任何自动投标行为，系统会自动将您已启用的自动投标规则重新排队。</p>
				<p>3、当借款标满标的最后一笔投标是自动投标，且该投标金额小于该用户设定的自动投标金额时，则该用户的自动投标设置不参与重新排队。</p>
				<p>4、修改自动投标条件，不会改变自动投标的排队号。</p>
			</dd>
		</dl>
    </div>
<script type="text/javascript">
 
/**
 * ajax 翻页功能
 */
function findPage(pageNo){
	$.ajax({
		url : '${basePath}/myaccount/autoInvest/queryAutoTenterLog/10.html?pageNum='+pageNo,
		data :{
		 }
		 ,
		type : 'post',
		dataType : 'text',
		success : function(data){
			$(".tz-btablebox").html(data);
			
		},
		error : function(data) {
			layer.msg("网络连接异常，请刷新页面或稍后重试！", 2, 5);
	    }
	});
}
$(function(){ 
	var color="#f0f7ff" 
	$(".tbtr tr:even,.tbtr01 tr:even,.tbtr02 tr:even,.tbtr03 tr:even,.tbtr04 tr:even,.tbtr05 tr:even,.tbtr06 tr:even,.tbtr07 tr:even,.tbtr08 tr:even,.tbtr09 tr:even,.tbtr10 tr:even,.tbtr11 tr:even,.tbtr12 tr:even,.tbtr13 tr:even,.tbtr14 tr:even,.tbtr15 tr:even,.tbtr16 tr:even,.tbtr17 tr:even").attr("bgcolor",color);//改变奇数行背景色 
	});
</script>
