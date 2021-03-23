<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/page/common/taglib.jsp"%>
 <table class="autotz-tablebox" style="width:94%;"  border="0" cellspacing="0" cellpadding="0">
              <tr>
                <th scope="col">排队号</th>
                <th scope="col">投宝方式</th>
                <th scope="col">投宝额度（元）</th>
                <th scope="col">投宝金额（元）</th>
                <th scope="col">定期宝期限</th>
                <th scope="col">投宝时间</th>
                <th scope="col">定期宝编号</th>
              </tr>
              <c:if test="${!empty page.result }">
                <c:forEach items="${page.result}" var="autoFixBorrow" varStatus="sta" step="1">
                   <tr>
	                <td>
                        <c:if test="${autoFixBorrow.rownum!=null}">第&nbsp;<font color="red">${autoFixBorrow.rownum}</font>&nbsp;位</c:if>
			            <c:if test="${autoFixBorrow.rownum==null}">无</c:if>
                    </td>
	                <td>
	                    <c:if test="${ autoFixBorrow.autoTenderType==1}">按金额投宝</c:if>
	                    <c:if test="${ autoFixBorrow.autoTenderType==2}">账户余额</c:if>
	                </td>
	                <td>
					     <fmt:formatNumber value="${autoFixBorrow.limitMoney}" pattern="#,##0.00"></fmt:formatNumber> 
                    </td>
	                <td><fmt:formatNumber value="${autoFixBorrow.autoTenderMoney}" pattern="#,##0.00"></fmt:formatNumber></td>
	                <td>${autoFixBorrow.fixType}月宝</td>
	                <td><fmt:formatDate value="${autoFixBorrow.addtime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
	                <td><a href="${basePath}/dingqibao/${autoFixBorrow.fixId}.html" target="_blank">${autoFixBorrow.fixNo }</a></td>
                  </tr>
                </c:forEach>
              </c:if>
               <c:if test="${empty page.result }">
	              <tr class="zwjlbox" style="height:160px;">
	                <td colspan="7" align="center">暂无记录</td>
	              </tr>
              </c:if>
              </table>
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
	      
	  
 
 <!---------- ---------------------------------------------------------------------------->        
          
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
 
</script>
