<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ include file="/WEB-INF/page/common/taglib.jsp"%>

<link href="${basePath}/css/belstar.css" rel="stylesheet" type="text/css" />
<link href="${basePath}/css/Style.css" type="text/css" rel="stylesheet" />

<div class="zjjl_list" style="width:875">
	<form id="exportCashForm" method="post">
	 <table width="875" border="1">
         <tr>
           <td colspan="10" >
             <span class="zj">
	   			<input  type="button" style="width:80px;height:24px;cursor:pointer;" onclick="exportCashRecord();" value="导出EXCEL">
		    </span>	
	        </td>
         </tr>           
       </table>
	</form>
	<table width="875" border="1">
       <tr height="30px">
           <td width="36"><strong>序号</strong></td>
           <td width="60"><strong>总金额</strong></td>
           <td width="60"><strong>到账总额</strong></td>
	       <td width="60"><strong>手续费</strong></td>
	       <td width="120"><strong>银行</strong></td>
	       <td width="60"><strong>账户</strong></td>
	       <td width="100"><strong>申请时间</strong></td>
	       <td width="100"><strong>打款时间</strong></td>
	       <td width="80"><strong>状态</strong></td>
	       <td width="120"><strong>审核备注</strong></td>
	       <td width="60"><strong>操作</strong></td>
	   </tr>
      <c:forEach items="${page.result }" var="cashRecord" varStatus="sta">
        <tr height="30px">
           <td width="36">${sta.index + 1 }</td>
           <td width="60">￥${cashRecord.total }</td>
           <td width="60">￥${cashRecord.credited }</td>
           <td width="60">￥${cashRecord.fee }</td>
           <td width="120">${cashRecord.bank }</td>
           <td width="60">${cashRecord.accountFormat}</td>
           <td width="100" title="${cashRecord.addtimeymdhms}">${cashRecord.addtimeymd}</td>
           <td width="100" title="${cashRecord.verifyTime2ymdhms}">${cashRecord.verifyTime2ymd }</td>
           <td width="80">${cashRecord.statusStr }</td>
           <td width="120">${cashRecord.verifyRemark }</td>
           <td width="60">
           		<c:if test="${cashRecord.status==0 }">
           			<a href="javascript:cancelCash(${cashRecord.id})" title="取消提现">
           				<font color='red'>取消提现</font>
           			</a>
           		</c:if>
           	</td>
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
 * 取消提现
 */
function cancelCash(id){
	
	if(layer.confirm("确认要取消提现吗？",function(){
		$.ajax({
			url : '${basePath}/myaccount/cashRecord/cancelCash.html',
			data : {id:id},
			type : 'post',
			dataType : 'text',
			success : function(data){
				if("success"==data){
					layer.alert("取消提现成功！" , 1, "温馨提示");
				}else{
					layer.alert(data);
				}
				window.parent.toCashRecord(1);
			},
			error : function(data) {
				layer.msg('网络连接异常，请刷新页面或稍后重试！', 1, 5);
		    }
		});	
	}));
	
}
/**
 * 导出Excel
 */
function exportCashRecord(){
	 
	if(layer.confirm("你确定要导出吗?",function(){
		$("#exportCashForm").attr("action","${basePath}/cashReport/exportCashRecord.html");
		$("#exportCashForm").submit();
	}));
	
}
/**
 * ajax 翻页功能
 */
function findPage(pageNo){
	window.parent.toCashRecord(pageNo);
}
</script>
