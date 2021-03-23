<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8" %>
<%@ include file="/WEB-INF/page/common/taglib.jsp" %>
<script type="text/javascript">
	   /**
     * ajax 翻页功能
     */
    function findPage(pageNo) {
        window.parent.turnrechargePageParent(pageNo);
    }
    $(function(){ 
    	var color="#f0f7ff" 
    	$(".tbtr tr:even,.tbtr01 tr:even,.tbtr02 tr:even,.tbtr03 tr:even,.tbtr04 tr:even,.tbtr05 tr:even,.tbtr06 tr:even,.tbtr07 tr:even,.tbtr08 tr:even,.tbtr09 tr:even,.tbtr10 tr:even,.tbtr11 tr:even,.tbtr12 tr:even,.tbtr13 tr:even,.tbtr14 tr:even,.tbtr15 tr:even,.tbtr16 tr:even,.tbtr17 tr:even").attr("bgcolor",color);//改变奇数行背景色
    	$("#rechargeTotal").html('${rechargeTotal}');
    	$("#beginTime1").val('${beginTime}');
    	$("#endTime1").val('${endTime}');
    	});
    
</script>
            <table width="100%" border="0" cellspacing="0" cellpadding="0" class="table center tbtr02">
                <tr class="tbl-title">
                    <td>
                	        申请时间
                    </td>
                    <td align="right">
                   	     充值金额(元)
                    </td>
                    <td>
                    	充值平台
                    </td>
                    <td>
                    	    手续费(元)
                    </td>
                    <td>
                     	   成功时间
                    </td>
                    <td>
                  	      状态
                    </td>
                </tr>
                <c:forEach items="${page.result}" var="rechargeRecord">
                <tr>
                    <td>
                        ${rechargeRecord.addtimeymd}
                    </td>
                    <td align="right">
                      <fmt:formatNumber value="${rechargeRecord.money }" pattern="#,##0.00"/>
                    </td>
                    <td>
                        <span class="orange">
							<c:if test="${rechargeRecord.type == 1 && rechargeRecord.onlinetype == 1}">京东支付</c:if>
							<c:if test="${rechargeRecord.type == 1 && rechargeRecord.onlinetype == 2}">国付宝</c:if>
							<c:if test="${rechargeRecord.type == 1 && rechargeRecord.onlinetype == 3}">盛付通</c:if>
							<c:if test="${rechargeRecord.type == 1 && rechargeRecord.onlinetype == 4}">新浪支付</c:if>
							<c:if test="${rechargeRecord.type == 1 && rechargeRecord.onlinetype == 5}">连连支付</c:if>
							<c:if test="${rechargeRecord.type == 1 && rechargeRecord.onlinetype == 6}">富友支付</c:if>
							<c:if test="${rechargeRecord.type == 1 && rechargeRecord.onlinetype == 8}">浙商存管支付</c:if>
                         </span>
                    </td>
                    <td>
                        <fmt:formatNumber value="${rechargeRecord.fee}" pattern="#,##0.00"/>
                    </td>
                    <td>
                    <c:if test="${rechargeRecord.status==1 }">
                        ${rechargeRecord.verifyTime2ymd}
                     </c:if>
                    </td>
                    <td>
                        	<c:if test="${rechargeRecord.status==0 }">充值审核中</c:if>
							<c:if test="${rechargeRecord.status==1 }">充值成功</c:if> 
							<c:if test="${rechargeRecord.status==2 }">在线充值待付款</c:if>
						    <c:if test="${rechargeRecord.status==3 }">初审成功</c:if> 
							<c:if test="${rechargeRecord.status==9 }">充值失败</c:if>
                    </td>
                </tr>
                </c:forEach>
            </table>
        	<!-- 翻页 -->
        	  <c:if test="${!empty page.result}">
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