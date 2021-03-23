<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/page/common/taglib.jsp"%>
        <table width="100%" border="0" cellspacing="0" cellpadding="0"  class="table center tbtr04">
            <thead>
            <tr class="tbl-title">
              <td>排队号</td>
              <td>投标方式</td>
              <td>投宝额度(元)</td>
              <td>投宝金额(元)</td>
              <td>定期宝期限</td>
              <td>投宝时间</td>
              <td>定期宝编号</td>
            </tr>
            </thead>
            <tbody>
	           <c:if test="${!empty page.result }">
	              <c:forEach items="${page.result}" var="autoFixBorrow" varStatus="sta" step="1">
		            <tr>
		              <td> 
		                <c:if test="${autoFixBorrow.rownum!=null}">第&nbsp;<font>${autoFixBorrow.rownum}</font>&nbsp;位</c:if>
			            <c:if test="${autoFixBorrow.rownum==null}">无</c:if>
			          </td>
		              <td>   
		                 <c:if test="${ autoFixBorrow.autoTenderType==1}">按金额投宝</c:if>
	                     <c:if test="${ autoFixBorrow.autoTenderType==2}">账户余额</c:if>
	                  </td>
		              <td><fmt:formatNumber value="${autoFixBorrow.limitMoney}" pattern="#,##0.00"></fmt:formatNumber></td>
		              <td><fmt:formatNumber value="${autoFixBorrow.autoTenderMoney}" pattern="#,##0.00"></fmt:formatNumber></td>
		              <td>${autoFixBorrow.fixType}月宝</td>
		              <td><fmt:formatDate value="${autoFixBorrow.addtime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
		              <td><a href="${basePath}/dingqibao/${autoFixBorrow.fixId}.html" target="_blank"><span class="blue">${autoFixBorrow.fixNo }</span></a></td>
		            </tr>
		            </c:forEach>
	            </c:if>
	            <c:if test="${empty page.result }">
	              <tr>
	               	     <td colspan="7"> <div class="zdtz-none"><p>暂无记录</p></div></td>
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
		      	<p>1、用户只能添加1条自动投宝规则，当启用规则时，将自动从最后一名开始排队，系统根据排队号来自动投宝。</p>
		          <p>2、当您启用规则在90天内未发生任何自动投宝行为，系统会自动将您已启用的自动投宝规则重新排队。</p>
		          <p>3、当定期宝满宝的最后一笔投资是自动投定期宝，且投宝金额小于该用户设定金额时，则该用户的自动投定期宝设置不参与重新排队。</p>
		          <p>4、修改自动投定期宝条件和发生流宝情况时，都不会改变自动投定期宝的排名。</p>
		      </dd>
		  </dl>
		</div>
<script type="text/javascript">
 
/**
 * ajax 翻页功能
 */
function findPage(pageNo){
	$.ajax({
		url : '${basePath}/myaccount/autoInvestFix/queryAutoFixTenterLog/'+pageNo+'.html',
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
