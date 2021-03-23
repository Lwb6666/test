<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/page/common/taglib.jsp"%>


<script type="text/javascript">
	/**
	 * ajax 翻页功能
	 */
	function findPage(pageNo) {
		window.parent.turnrechargePageParent(pageNo);
	}

	/**
	 * 导出Excel
	 */
	function exportRechargeRecord() {
		
		if(layer.confirm("你确定要导出吗?",function(){
			$("#exportRechargeForm").attr("action",
			"${basePath}/cashReport/exportRechargeRecord.html");
         	$("#exportRechargeForm").submit();
		}));
		
	}
</script>
<!--  <form id="exportRechargeForm" method="post">
<li style=" margin-left:750px; color:#FFF;width:90px; height:32px;background:#00a7e5;"><span class="title_btn" >
   <a href="javascript:exportRechargeRecord()">导出excel</a>
   </span>
   </li>
</u>
</form> -->
	<div class="myid whitebg nobordertop">
		<div class="topup-state">
			<span>充值状态：
			<select name="status" id="status" class="riqi">
			<option value="">--充值状态--</option>
			<c:forEach items="${configurationList }" var="configuration">
				<option value="${configuration.name}"
					<c:if test="${status != null && configuration.name == status }">  selected="selected"  </c:if>>
					${configuration.value}
				</option>
			</c:forEach>
			</select>
			</span>
        	<span>日期：</span>
        	<input class="riqi" type="text" name="beginTime" id="beginTime" value="${beginTime }" onClick="WdatePicker()"/>
        	<span>至</span>
        	<input class="riqi" type="text" name="endTime" id="endTime" value="${endTime }" onClick="WdatePicker()"/>
        	<input class="seach-jr" value="查询" type="button" onclick="searchrechargeRecord()"/>                 
        	<c:if test="${status == 1 || status == ''}">
        	<div class="right-cz" >
        	<span >充值总额：</span>
        	<span style="color:#C92B2E;  font-size:18px;">
        	<fmt:formatNumber value="${rechargeTotal}" pattern="#,##0.00"/>
        	</span>
        	<span style="padding-left:10px;">元</span>
        	</div>
        	</c:if>
        </div>
        
	<table width="100%" border="0">
		<tr class="firstline" align="center" valign="middle">
			<td width="100">类型</td>
			<td width="94">充值金额</td>
			<td width="64">充值银行</td>
			<td width="88">充值手续费</td>
			<td width="58">充值时间</td>
			<td width="58">充值平台</td>
			<td width="108">状态</td>
		</tr>
		<c:forEach items="${page.result}" var="rechargeRecord"
			varStatus="sta">
			<c:if test="${sta.index%2==0}">
				<tr class="trcolor">
			</c:if>
			<c:if test="${sta.index%2!=0}">
				<tr>
			</c:if>
			<td width="100"><c:if test="${rechargeRecord.type==1 }">在线充值</c:if>
				<c:if
					test="${rechargeRecord.type==2 && rechargeRecord.cardNum == '44461248@qq.com'}">支付宝充值</c:if>
				<c:if
					test="${rechargeRecord.type==2 && rechargeRecord.cardNum != '44461248@qq.com' }">线下充值</c:if>
			</td>
			<td width="94"><span  class="numcolor">￥<fmt:formatNumber value="${rechargeRecord.money }" pattern="#,##0.00"/></span></td>
			<td width="64">${rechargeRecord.payment}</td>
			<td width="88">￥<fmt:formatNumber value="${rechargeRecord.fee}" pattern="#,##0.00"/></td>
			<td width="58">${rechargeRecord.addtimeymd}</td>
			<td width="58">
			<c:if test="${rechargeRecord.type == 1 && rechargeRecord.onlinetype == 1}">网银在线</c:if>
			<c:if test="${rechargeRecord.type == 1 && rechargeRecord.onlinetype == 2}">国付宝</c:if>
			<c:if test="${rechargeRecord.type == 1 && rechargeRecord.onlinetype == 3}">盛付通</c:if>
			<c:if test="${rechargeRecord.type == 1 && rechargeRecord.onlinetype == 4}">新浪支付</c:if>
			<c:if test="${rechargeRecord.type == 1 && rechargeRecord.onlinetype == 5}">连连支付</c:if>
			<c:if test="${rechargeRecord.type == 1 && rechargeRecord.onlinetype == 6}">富友支付</c:if>
			</td>
			<td width="108"><c:if test="${rechargeRecord.status==0 }">充值审核中</c:if>
				<c:if test="${rechargeRecord.status==1 }">充值成功</c:if> <c:if
					test="${rechargeRecord.status==2 }">在线充值待付款</c:if> <c:if
					test="${rechargeRecord.status==3 }">初审成功</c:if> <c:if
					test="${rechargeRecord.status==9 }">充值失败</c:if></td>
			</tr>
		</c:forEach>
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
</div>