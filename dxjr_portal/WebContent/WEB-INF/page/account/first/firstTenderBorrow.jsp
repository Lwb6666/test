<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/page/common/taglib.jsp"%>
<td colspan="9">
	<c:if test="${empty tenderRecordList}">
		 <table class="showtable">
	        <thead>
		      <tr>
		        <td>投标时间</td>
		        <td>借款标题</td>
		        <td>预期利率</td>
		        <td>期限</td>
		        <td>投资金额</td>
		        <td>状态</td>
		        <td>查看</td>
		      </tr>
		    </thead>
		     <tbody>
		      <tr>
		        <td colspan="7">暂无记录</td>
		      </tr>
			 </tbody>
      </table>
	</c:if>
<c:if test="${!empty tenderRecordList}">
	<table class="showtable">
	        <thead>
		      <tr>
		        <td>投标时间</td>
		        <td>借款标题</td>
		        <td>预期利率</td>
		        <td>期限</td>
		        <td>投资金额</td>
		        <td>状态</td>
		        <td>查看</td>
		      </tr>
		    </thead>
		     <tbody>
			 <c:forEach items="${tenderRecordList }" var="tenderRecord" varStatus="sta" step="1">
		      <tr>
		        <td>${tenderRecord.addtime }</td>
		        <td>${fn:substring(tenderRecord.firstBorrowName,0,7)}<c:if test="${fn:length(tenderRecord.firstBorrowName)>7}">..</c:if></td>
		        <td><fmt:formatNumber value="${tenderRecord.apr }" pattern="#,##0.##" />%</td>
		        <td><c:if test="${tenderRecord.borrowtype==4}">秒还</c:if> 
					<c:if test="${tenderRecord.borrowtype!=4 && tenderRecord.style != 4 }">${tenderRecord.timeLimit }个月</c:if>
					<c:if test="${tenderRecord.borrowtype!=4 && tenderRecord.style == 4 }">${tenderRecord.timeLimit }天</c:if></td>
		        <td>${tenderRecord.account}</td>
		        <td><c:if test="${tenderRecord.status == 4}">收益中</c:if>
		        	<c:if test="${tenderRecord.status == 5}">已结束</c:if>
		        </td>
		        <td><a href="javascript:void(0);" data-reveal-id="firstUserUollection" data-animation="fade" onclick="collectionFirstRecordInfo(${realId },${tenderRecord.borrowId });">查看</a></td>
		      </tr>
		      </c:forEach>
			 </tbody>
      </table>
</c:if> 
</td>

<script type="text/javascript">
function closefirstWin(){
	$('#firstUserUollection').trigger('reveal:close'); 
}
function collectionFirstRecordInfo(id,borrowId){
	$.ajax({
		url : '${basePath}/account/InvestManager/collectionFirstRecordInfo.html',
		data : {
			firstTenderRealId : id,
			borrowId:borrowId
		},
		type : 'post',
		dataType : 'html',
		success : function(data) {
			$("#firstUserUollection").html(data);
		},
		error : function(data) {
			layer.msg('网络连接异常，请刷新页面或稍后重试！', 1, 5);
		}
	});
}
</script>