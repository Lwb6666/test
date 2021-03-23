<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/page/common/taglib.jsp"%>

	<div class="myid whitebg nobordertop">
		<div class="topup-state">
			<span>提现状态：
			<select name="status" id="status" class="riqi">
				<option value="">--所有类型--</option>
				<c:forEach items="${configurationList }" var="configuration">
					<option value="${configuration.name}"
						<c:if test="${configuration.name == status }">  selected="selected"  </c:if>>
						${configuration.value}
					</option>
				</c:forEach>
			</select>
			</span>
        	<span>日期：</span>
        	<input class="riqi" type="text" name="beginTime" id="beginTime" value="${beginTime }" onClick="WdatePicker()"/>
        	<span>至</span>
        	<input class="riqi" type="text" name="endTime" id="endTime" value="${endTime }" onClick="WdatePicker()"/>
        	<input class="seach-jr" value="查询" type="button" onclick="searchcashRecord()"/>                 
        	<c:if test="${status == 2 || status == ''}">
        	<div class="right-cz" >
        	<span >提现总额：</span>
        	<span style="color:#C92B2E;  font-size:18px;">
        	<fmt:formatNumber value="${cashTotal}" pattern="#,##0.00"/>
        	</span>
        	<span style="padding-left:10px;">元</span>
        	</div>
        	</c:if>
        </div>
	
    <table > 
    <tr>    
           <!-- <td width="36"><strong>序号</strong></td> -->
           <td ><strong>总金额</strong></td>
           <td ><strong>到账总额</strong></td>
           <td ><strong>手续费</strong></td>
           <td ><strong>银行</strong></td>
           <td ><strong>账户</strong></td>
           <td ><strong>申请时间</strong></td>
           <td ><strong>处理时间</strong></td>
           <td ><strong>状态</strong></td>
           <td ><strong>处理备注</strong></td>
           <td ><strong>操作</strong></td>
         </tr>
         <c:forEach items="${page.result }" var="cashRecord" varStatus="sta">
         <tr <c:if test="${sta.index%2==0}">class="trcolor"</c:if>>
           <td class="numcolor">￥<fmt:formatNumber value="${cashRecord.total }" pattern="#,##0.00"/></td>
           <td class="numcolor">￥<fmt:formatNumber value="${cashRecord.credited }" pattern="#,##0.00"/></td>
           <td >￥<fmt:formatNumber value="${cashRecord.fee }" pattern="#,##0.00"/></td>
           <td >${cashRecord.bank }</td>
           <td id="${cashRecord.id}Account">${cashRecord.accountFormat }</td>
           <td>${cashRecord.addtimeymd }</td>
           <td>
               <c:if test="${cashRecord.status==-1 || cashRecord.status==1 || cashRecord.status==3}">
           		  <fmt:formatDate value="${cashRecord.verifyTimeDate }" pattern="yyyy-MM-dd"/>
           	   </c:if>
               <c:if test="${cashRecord.status==2 || cashRecord.status==5}">
           		  <fmt:formatDate value="${cashRecord.verifyTime2Date }" pattern="yyyy-MM-dd"/>
           	   </c:if> 
               <c:if test="${cashRecord.status==4}">
           		  <fmt:formatDate value="${cashRecord.verifyTime3Date }" pattern="yyyy-MM-dd"/>
           	   </c:if>           	             	   
           </td>
           <td>${cashRecord.statusStr}</td>
           <td>
               <c:if test="${cashRecord.status==-1 || cashRecord.status==1 || cashRecord.status==3}">
               	  <span title="${cashRecord.verifyRemark }">
               	     ${fn:substring(cashRecord.verifyRemark,0,8)}
     				<c:if test="${fn:length(cashRecord.verifyRemark)>8}">..</c:if>
           		  </span>
           	   </c:if>
               <c:if test="${cashRecord.status==2 || cashRecord.status==5}">
               	  <span title="${cashRecord.verifyRemark2 }">
               	     ${fn:substring(cashRecord.verifyRemark2,0,8)}
     				<c:if test="${fn:length(cashRecord.verifyRemark2)>8}">..</c:if>
           		  </span>               
           	   </c:if> 
               <c:if test="${cashRecord.status==4}">
               	  <span title="${cashRecord.verifyRemark3 }">
               	     ${fn:substring(cashRecord.verifyRemark3,0,8)}
     				<c:if test="${fn:length(cashRecord.verifyRemark3)>8}">..</c:if>
           		  </span>               
           	   </c:if>           	
           </td>
           <td><c:if test="${cashRecord.status==0 }"><a href="javascript:cancelCash(${cashRecord.id})" title="取消提现"><font color='red'>取消提现</font></a></c:if></td>
         

         </c:forEach>
                                  
           
            </table>

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


<script type="text/javascript">
/**
 * ajax 翻页功能
 */
function findPage(pageNo){
	window.parent.turncashPageParent(pageNo);
}

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
 * 取消提现
 */
function cancelCash(id){
	if(layer.confirm("确认要取消提现吗？",function(){
		$.ajax({
			url : '${basePath}/myaccount/cashRecord/cancelCash.html',
			data : {id:id},
			type : 'post',
			dataType : 'json',
			success : function(data){
				if("1"==data.code){
					layer.msg("取消提现成功！", 2, 1);
				}else{
					layer.msg(data.message, 2, 5);
				}
				findPage(1);
			},
			error : function(data) {
				layer.msg("网络连接异常，请刷新页面或稍后重试！", 1, 5);
			}
		});	
	}));
}

</script>