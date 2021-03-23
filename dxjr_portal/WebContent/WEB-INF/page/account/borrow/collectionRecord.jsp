<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/page/common/taglib.jsp"%>
<div class="cont-word cont-w03">
    	<div class="title"><h4>待收计划</h4><a href="javascript:void(0);" onclick="closecollWin();" class="icon icon-close fr"></a></div>
        <div class="contZtc-text">
        
        <p><em>借款标题：</em><span>${fn:substring(borrowVo.name,0,7)}<c:if test="${fn:length(borrowVo.name)>7}">..</c:if> </span></p>
        <p><em>预期利率：</em><span><fmt:formatNumber value="${borrowVo.apr }" pattern="#,##0.##" />%</span></p>
        <p><em>期限：</em><span>
			<c:if test="${borrowVo.borrowtype==4}">秒还</c:if> 
				<c:if test="${borrowVo.borrowtype!=4 && borrowVo.style != 4 }">${borrowVo.timeLimit }个月</c:if>
				<c:if test="${borrowVo.borrowtype!=4 && borrowVo.style == 4 }">${borrowVo.timeLimit }天</c:if>
		</span></p>
        <p><em>投资金额：</em><span>${borrowVo.rbtAccount}元</span></p>
        <p><em>投标时间：</em><span><fmt:formatDate value="${borrowVo.addtime }" pattern="yyyy-MM-dd" /></span>
<!--         <span class="ml20">协议</span> -->
        </p>
        
        </div>
        
  <table width="100%" border="0" cellspacing="0" cellpadding="0"  class="table center tbtr03">
         <thead>
         <tr class="tbl-title">
           <td>期数</td>
           <td align="left">计划还款日</td>
           <td>还款本息和</td>
           <td>本金</td>
           <td>利息</td>
           <td>还款状态</td>
           <td>实际还款日</td>
         </tr>
         </thead>
         
         <tbody>
         <c:forEach items="${recordList }" var="record" varStatus="sta" step="1">
         <tr>
           <td>${record.order}/${record.borrowOrder}</td>
           <td align="left">${record.repayTime }</td>
           <td><fmt:formatNumber value="${record.repayAccount}" pattern="#,##0.00"/>元</td>
           <td><fmt:formatNumber value="${record.capital}" pattern="#,##0.00"/>元</td>
           <td><fmt:formatNumber value="${record.interest}" pattern="#,##0.00"/>元</td>
           <td>
           <c:if test="${record.status == 1 }">
           		已还款
           </c:if>
           <c:if test="${record.status == 0 }">
           		未还款
           </c:if>
           <c:if test="${record.status == 4 }">
               银行还款中
           </c:if>
           <c:if test="${record.status == 5 }">
               银行垫付中
           </c:if>
           </td>
           <td>${record.repayYestime }</td>
         </tr>
         </c:forEach>
         <c:if test="${recordList.size() == 0}">
             <tr>
                <td colspan="7">该借款标待收还未生成，请耐心等待...</td>
            </tr>
         </c:if>
        <!--  <tr>
           <td>---</td>
           <td align="left">--------</td>
           <td>------</td>
           <td>------</td>
           <td>----</td>
           <td>----</td>
           <td>-------</td>
         </tr> -->
         </tbody>
        </table>
    </div> 
    
<script type="text/javascript">
    function closecollWin(){ 
	  $('#userUollection').trigger('reveal:close'); 
    } 
	$(document).ready(function(){ 
	    var color="#f0f7ff"
	    $(".tbtr tr:even,.tbtr01 tr:even,.tbtr02 tr:even,.tbtr03 tr:even,.tbtr04 tr:even,.tbtr05 tr:even,.tbtr06 tr:even,.tbtr07 tr:even,.tbtr08 tr:even,.tbtr09 tr:even,.tbtr10 tr:even,.tbtr11 tr:even,.tbtr12 tr:even,.tbtr13 tr:even,.tbtr14 tr:even,.tbtr15 tr:even,.tbtr16 tr:even,.tbtr17 tr:even").attr("bgcolor",color);//改变奇数行背景色
	})	
</script>