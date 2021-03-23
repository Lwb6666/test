<%@page import="com.dxjr.utils.DateUtils"%>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/page/common/taglib.jsp"%>
<!doctype html>
<html>
<head>
<%@ include file="/WEB-INF/page/stockright/common/public.jsp"%>
<meta charset="utf-8">
<title>我的内转账户_成交记录详情</title>
</head>
<body>
<div class="main-offsetHeight">
	<!-- header start   -->
	<%@ include file="/WEB-INF/page/stockright/common/header.jsp"%>
	<!-- header end     -->
	
<!-- main start	 -->
<div class="product-wrap">
	<div class="grid-1100">
    	<div class="product-deatil clearfix ">
        	<h1 class="f16">交易状态：<c:if test="${deal.status==1}">交易处理中</c:if> <c:if test="${deal.status==2}">交易完成</c:if><span class="blue"></span></h1>
          <div class="transaction">
         
          <div class="line">
            <i class="gou1"><img src="${basePath}/images/stock/gou.png" alt=""/></i>
            <span class="line01"></span>
            <i class="gou2"><img src="${basePath}/images/stock/gou.png" alt=""/></i>
            <c:if test="${deal.status==1}">
            <span class="line02 line-gray"></span>
             <i class="gou3"><img src="${basePath}/images/stock/gou-gray.png" alt=""/></i>
            </c:if>
            <c:if test="${deal.status==2}">
            <span class="line02"></span>
             <i class="gou3"><img src="${basePath}/images/stock/gou.png" alt=""/></i>
            </c:if>
            
            </div>
            <div class="line-text clearfix">
            	<ul>
                	<li><p class="f16">挂单</p><p>
                		<c:if test="${entrustId==sellerEntrust.id}"><fmt:formatDate value="${sellerEntrust.addtime}" pattern="yyyy-MM-dd HH:mm:ss"/></c:if>
                	<c:if test="${entrustId==buyerEntrust.id}"><fmt:formatDate value="${buyerEntrust.addtime}" pattern="yyyy-MM-dd HH:mm:ss"/></c:if>
                	</p></li>
                	<li>
                	  <p class="f16">成交</p>
               	    <p><fmt:formatDate value="${deal.addtime}" pattern="yyyy-MM-dd HH:mm:ss"/></p></li>
                	<li>
                	  <p class="f16">完成交易</p>
                	  <c:if test="${deal.status==1}">
                	  	<p>预计结算:<fmt:formatDate value="${deal.expecttime}" pattern="yyyy-MM-dd"/> 04:00:00</p>
                	  </c:if>
                	  <c:if test="${deal.status==2}">
                	  	<p><fmt:formatDate value="${deal.updatetime}" pattern="yyyy-MM-dd HH:mm:ss"/></p>
                	  </c:if>
               	   </li>
                </ul>
            </div>
          
            <div class="tab-jy">
               <table class="table">
                    <thead>
                      <tr class="tbl-title">
                        <td>&nbsp;</td>
                        <td>转让方</td>
                        <td>受让方</td>
                      </tr>
                      </thead>
                      
                      <tbody>
                      <tr>
                        <td class="tdbg">挂单编号</td>
                        <td>${sellerEntrust.entrustCode }</td>
                        <td>${buyerEntrust.entrustCode }</td>
                      </tr>
                      <tr>
                        <td class="tdbg">委托类型</td>
                        <td>
            			<c:choose>
						<c:when test="${sellerEntrust.entrustType==1 }">认购</c:when>
						<c:when test="${sellerEntrust.entrustType==2 }">转让</c:when>
						</c:choose>            
                        </td>
                        <td>
                        <c:choose>
						<c:when test="${buyerEntrust.entrustType==1 }">认购</c:when>
						<c:when test="${buyerEntrust.entrustType==2 }">转让</c:when>
						</c:choose> 
                        </td>
                      </tr>
                      <tr>
                        <td class="tdbg">委托时间</td>
                        <td><fmt:formatDate value="${sellerEntrust.addtime}" pattern="yyyy-MM-dd HH:mm:ss"/> </td>
                        <td><fmt:formatDate value="${buyerEntrust.addtime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
                      </tr>
                      <tr>
                        <td class="tdbg">委托价格</td>
                        <td>${sellerEntrust.price}元/份</td>
                        <td>${buyerEntrust.price}元/份</td>
                      </tr>
                      <tr>
                        <td class="tdbg">委托份额</td>
                        <td><fmt:formatNumber value="${sellerEntrust.amount}" pattern="#,#00"/>份</td>
                        <td><fmt:formatNumber value="${buyerEntrust.amount}" pattern="#,#00"/>份</td>
                      </tr>
                      <tr>
                        <td class="tdbg">成交时间</td>
                        <td colspan="2"><fmt:formatDate value="${deal.addtime}" pattern="yyyy-MM-dd HH:mm:ss"/> </td>
                      </tr>
                      <tr>
                        <td class="tdbg">成交价格</td>
                        <td colspan="2">${deal.turnoverPrice}元/份 </td>
                      </tr>
                      <tr>
                        <td class="tdbg">成交份额</td>
                        <td colspan="2"> <fmt:formatNumber value="${deal.turnoverAmount}" pattern="#,#00"/>份 </td>
                      </tr>
                      <tr>
                        <td class="tdbg">成交总价</td>
                        <td colspan="2"><fmt:formatNumber value="${deal.turnoverTotalPrice}" pattern="#,#00.00"/>元 </td>
                      </tr>
                      <tr>
                        <td class="tdbg">交易服务费</td>
                        <td> ${deal.sellerFee}元</td>
                        <td>${deal.buyerFee}元 </td>
                      </tr>
                      <tr>
                        <td class="tdbg">成交类型</td>
                       <%--  
                         <td colspan="2">
                         	<c:if test="${deal.dealType==1 }">主动转让成交</c:if>
                         	<c:if test="${deal.dealType==2 }">主动认购成交</c:if>
                         </td> --%>
                        
                        <td>
                        	<c:if test="${deal.dealType==1 }">主动转让成交</c:if>
                        	<c:if test="${deal.dealType==2 }">被动转让成交</c:if>
                       	</td>
                        <td>
                        	<c:if test="${deal.dealType==1 }">被动认购成交</c:if>
                        	<c:if test="${deal.dealType==2 }">主动认购成交</c:if>
                        </td>
                        
                      </tr>
                      </tbody>
                 </table>
            
            </div>
            </div>
         </div>
	</div>
</div>
<!-- main end	 -->
</div>
	<!-- footer start   -->
	<%@ include file="/WEB-INF/page/stockright/common/footer.jsp"%>
	<!-- footer end     -->
</body>
<script type="text/javascript">

</script>
</html>
