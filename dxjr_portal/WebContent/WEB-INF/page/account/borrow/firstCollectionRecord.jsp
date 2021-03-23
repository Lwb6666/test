<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/page/common/taglib.jsp"%>
<!--我的账户右侧开始 -->
	<div class="cont-word cont-w03">
    	<div class="title"><h4></h4><a href="javascript:closefirstWin();" class="icon icon-close fr"></a></div>
        <div class="contZtc-text">
        
        <p><em>借款标题：</em><span><span>${fn:substring(borrowVo.name,0,7)}<c:if test="${fn:length(borrowVo.name)>7}">..</c:if> </span></span></p>
        <p><em>年化利率：</em><span><fmt:formatNumber value="${borrowVo.apr }" pattern="#,##0.##" />%</span></p>
        <p><em>期限：</em><span>
        		<c:if test="${borrowVo.borrowtype==4}">秒还</c:if> 
				<c:if test="${borrowVo.borrowtype!=4 && borrowVo.style != 4 }">${borrowVo.timeLimit }个月</c:if>
				<c:if test="${borrowVo.borrowtype!=4 && borrowVo.style == 4 }">${borrowVo.timeLimit }天</c:if></span></p>
        <p><em>投资金额：</em><span>${borrowVo.account}元</span></p>
        <p><em>投标时间：</em><span><fmt:formatDate value="${borrowVo.addtime }" pattern="yyyy-MM-dd" /></span>
<!--         <span class="ml20">协议</span> -->
        </p>
        
        </div>
            <div class="overh">
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
                 <c:forEach items="${CollectionRecordList}" var="collectionRecord" varStatus="sta" step="1">
                  <tr>
                    <td>${collectionRecord.order}/${collectionRecord.borrowOrder}</td>
                    <td align="left"><c:if test="${collectionRecord.parentId!=null}"><font  color='red' >【直】</font></c:if>${collectionRecord.repay_timeStr }</td>
                    <td>${collectionRecord.repayAccountStr}元</td>
                    <td>${collectionRecord.capitalStr }元</td>
                    <td>${collectionRecord.interestStr}元</td>
                    <td>
                    ${(collectionRecord.advancetimeStr==null || collectionRecord.advancetimeStr=="")?"未还款":"已还款" }
                    </td>
                    <td><c:if test="${collectionRecord.parentId!=null}"><font  color='red' >【直】</font></c:if>${(collectionRecord.advancetimeStr==null || collectionRecord.advancetimeStr=="")?"--":collectionRecord.advancetimeStr }</td>
                  </tr>
                  </c:forEach>
                  </tbody>
                 </table>
           </div>
    </div>

<script>
$(document).ready(function(){ 
    var color="#f0f7ff"
    $(".tbtr tr:even,.tbtr01 tr:even,.tbtr02 tr:even,.tbtr03 tr:even,.tbtr04 tr:even,.tbtr05 tr:even,.tbtr06 tr:even,.tbtr07 tr:even,.tbtr08 tr:even,.tbtr09 tr:even,.tbtr10 tr:even,.tbtr11 tr:even,.tbtr12 tr:even,.tbtr13 tr:even,.tbtr14 tr:even,.tbtr15 tr:even,.tbtr16 tr:even,.tbtr17 tr:even").attr("bgcolor",color);//改变奇数行背景色
})	
</script>

