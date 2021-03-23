<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ include file="/WEB-INF/page/common/taglib.jsp"%>

<link href="${basePath}/css/belstar.css" rel="stylesheet" type="text/css" />
<link href="${basePath}/css/Style.css" type="text/css" rel="stylesheet" />

<div class="zjjl_list" style="width:875">
	<form id="exportRechargeForm" method="post">
	 <table width="875" border="1">
         <tr>
           <td colspan="10" >
             <span class="zj">
	   			<input  type="button" style="width:80px;height:24px;cursor:pointer;" onclick="exportRechargeRecord();" value="导出EXCEL"/>
		    </span>	
	        </td>
         </tr>           
       </table>
	</form>
	<table width="875" border="1">
	      <tr>
	        <td width="36"><strong>序号</strong></td>
	        <td width="100"><strong>类型</strong></td>
	        <td width="100"><strong>充值金额</strong></td>
	        <td width="100"><strong>充值银行</strong></td>
	        <td width="100"><strong>充值手续费</strong></td>
	        <td width="100"><strong>充值时间</strong></td>
	        <td width="100"><strong>状态</strong></td>
	       </tr>
	       <c:forEach items="${page.result }" var="rechargeRecord" varStatus="sta">
		       <tr>
		        <td width="36">${sta.index + 1 }</td>
		        <td width="100">${rechargeRecord.typeStr }</td>
		        <td width="100">￥${rechargeRecord.money }</td>
		        <td width="100">${rechargeRecord.payment}</td>
		        <td width="100">￥${rechargeRecord.fee}</td>
		        <td width="100">${rechargeRecord.addtimeymd}</td>
		        <td width="100">${rechargeRecord.statusStr} </td>
		       </tr>
	       </c:forEach>
	 </table>
	 	<jsp:include page="/WEB-INF/page/common/ajaxpage.jsp">
			<jsp:param name="pageNo" value="${page.pageNo}" />
			<jsp:param name="totalPage" value="${page.totalPage}" />
			<jsp:param name="hasPre" value="${page.hasPre}" />
			<jsp:param name="prePage" value="${page.prePage}" />
			<jsp:param name="hasNext" value="${page.hasNext}" />
			<jsp:param name="nextPage" value="${page.nextPage}" />
		</jsp:include>
 </div>
<script type="text/javascript">
/**
 * 导出Excel
 */
function exportRechargeRecord(){
	
	if(layer.confirm("你确定要导出吗?",function(){
		$("#exportRechargeForm").attr("action","${basePath}/cashReport/exportRechargeRecord.html");
		$("#exportRechargeForm").submit();
	}));
	
	
}
/**
 * ajax 翻页功能
 */
function findPage(pageNo){
	window.parent.toRechargeRecord(pageNo);
}
</script>
