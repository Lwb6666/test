<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/page/common/taglib.jsp"%>
 
            <table class="autotz-tablebox" style="width:90%;"  border="0" cellspacing="0" cellpadding="0">
              <tr>
                <th scope="col">排队号</th>
                <th scope="col">投标方式</th>
                <th scope="col">投标额度（元）</th>
                <th scope="col">投标金额（元）</th>
                <th scope="col">时间</th>
                <th scope="col">所投标的</th>
              </tr>
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
                    <!-- 实际投标金额 --> 
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
	              <tr class="zwjlbox" style="height:160px;" >
	                <td colspan="6" align="center">暂无记录</td>
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
 
</script>
