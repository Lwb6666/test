<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/page/common/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">


			   <jsp:useBean id="now" class="java.util.Date" />     
			   <fmt:formatDate value="${now}" type="both"  pattern="yyyy-MM-dd" var="nowDate"/> 
                          <table border="0">
                          <tr>
                          <td width="20%">标题</td>
                          <td width="20%">待收金额</td>
                          <td width="20%">待收日期</td>
                         <!--  <td>状态</td> -->
                          </tr>
                          <c:if test="${not empty CollectionRecordList}">
                          <c:forEach items="${CollectionRecordList}" var="collectionRecord" varStatus="sta" step="1">
                          		<tr>
                          		  <td  class="lists_line"><a title="${collectionRecord.name }" href="${path }/toubiao/${collectionRecord.borrowId}.html" target="_blank">${fn:substring(collectionRecord.name,0,5)}<c:if test="${fn:length(collectionRecord.name)>5}">..</c:if></a></td>
		                          <td    class="lists_line">${collectionRecord.repayAccountStr }</td>
		                          <td   name="repaymenttd" class="lists_line">${collectionRecord.repay_timeStr }</td>
		                         <%--  <td  width="105" class="lists_line">
		                          		 <c:if test="${collectionRecord.status==1}">
										已还款
										</c:if>
										<c:if test="${collectionRecord.status==2}">
										垫付完成
										</c:if>
										<c:if test="${collectionRecord.status==3}">
										已还款
										</c:if>
										<c:if test="${collectionRecord.status==0}">
											<c:if test="${collectionRecord.lateDays>0}">
												逾期中
											</c:if>
											<c:if test="${collectionRecord.lateDays==0}">
												未还款
											</c:if>
										</c:if>
								  </td> --%>
		                        </tr>                     
                          </c:forEach>
                          </c:if>
                          <c:if test="${empty CollectionRecordList}">
                          	<tr><td colspan="3" style="text-align: center;"><span class="blue">无待收记录</span></td></tr>
                          </c:if>
                          </table>
<script type="text/javascript">
 $(document).ready(function (){
	 var  nowDate=   '${nowDate}'; 
	    $("td[name=repaymenttd]").each(function () {
	    	 if($(this).text()==nowDate){
	    		 $(this).parent().attr("style","color:red");
	    		 $(this).parent().find("span").attr("style","color:red");
	    		 $(this).parent().find("a").attr("style","color:red");
	    	 }else{
	    		if( (new Date($(this).text())-new Date(nowDate))/1000/60/60/24==1){
	    			 $(this).parent().attr("style","color:#ffaa29");
	    			 $(this).parent().find("span").attr("style","color:#ffaa29");
	    			 $(this).parent().find("a").attr("style","color:#ffaa29");
	    		}
	    	 } 
	    });
 });
</script>
