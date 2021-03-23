<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/page/common/taglib.jsp"%>
<%@ include file="/WEB-INF/page/common/public.jsp"%>
<div id="menu_column">
    <div class="men_title">
         <ul>
          <li><a href="javascript:queryFirstTenderRealList();">直通车列表</a> </li>
          <li class="men_li"><a href="javascript:queryFirstTenderLogList();">直通车投标记录 </a></li>
         </ul>
    </div>
</div>
<div id="main_content">
<div class="myid nobordertop whitebg">
    <table style="">
		<tr>
			<td class="xq_currenttd" style="border-right:0px;width:130px;">序号（投标）</td>
			<td class=""><span class="numcolor">第${firstTenderLogVo.orderNum}位</span></td>
			<td class="xq_currenttd" style="border-right:0px;">开通时间</td>
			<td class="">
			<fmt:formatDate value="${firstTenderLogVo.openTime}"
						pattern="yyyy-MM-dd HH:mm:ss"></fmt:formatDate>
			</td>
			<td class="xq_currenttd" style="border-right:0px;">上车时间</td>
			<td>
			<fmt:formatDate value="${firstTenderLogVo.borrowPublishTime}"
						pattern="yyyy-MM-dd HH:mm:ss"></fmt:formatDate>
			</td>
		</tr>
	  	<tr>
	  		<td class="xq_currenttd" style="border-right:0px;">所投借款标</td>
	        <td class="">
	        	<a title ='${firstTenderLogVo.borrowName }' 
				href="${path }/toubiao/${firstTenderLogVo.borrowId}.html"
				target="_blank">${fn:substring(firstTenderLogVo.borrowName,0,10)}
				<c:if test="${fn:length(firstTenderLogVo.borrowName)>10}">..</c:if>
				</a>
	        </td>
	        <td class="xq_currenttd" style="border-right:0px;">借款标编号</td>
	        <td class="">
	        ${firstTenderLogVo.borrowContractNo }
	        </td>
	        <td class="xq_currenttd" style="border-right:0px;">发标时间</td>
			<td>
			<fmt:formatDate value="${firstTenderLogVo.borrowPublishTime}"
						pattern="yyyy-MM-dd HH:mm:ss"></fmt:formatDate>
			</td>
        </tr>
        <tr>
        	<td class="xq_currenttd" style="border-right:0px;">投标前标剩余金额</td>
	        <td><span class="numcolor"><fmt:formatNumber value="${firstTenderLogVo.remaindMoney}" pattern="#,##0.00"/></span></td>
	        <td class="xq_currenttd" style="border-right:0px;">投标前可用余额</td>
			<td class="">
			<span class="numcolor"><fmt:formatNumber value="${firstTenderLogVo.useMoney}" pattern="#,##0.00"/></span>
			</td>
			<td class="xq_currenttd" style="border-right:0px;">状态</td>
			<td class="">
				<span class="numcolor"><c:if test="${firstTenderLogVo.status==1}">投标成功</c:if></span>
				<span style="color:red;"><c:if test="${firstTenderLogVo.status==2}">投标失败</c:if></span>
			</td>
        </tr>
        <tr>
			<td class="xq_currenttd" style="border-right:0px;">投标时间</td>
			<td class="">
			<fmt:formatDate value="${firstTenderLogVo.addTime}"
						pattern="yyyy-MM-dd HH:mm:ss"></fmt:formatDate>
			</td>
			<td class="xq_currenttd" style="border-right:0px;">投标金额</td>
			<td>
			<span class="numcolor"><fmt:formatNumber value="${firstTenderLogVo.realAccount}" pattern="#,##0.00"/></span>
			</td>
			<td class="xq_currenttd" style="border-right:0px;"></td>
			<td class=""></td>
      	</tr>
      	<tr>
	    <td class="xq_currenttd" style="border-right:0px;">备注说明</td>
		<td colspan="5" align="left">
		${firstTenderLogVo.remark}
		</td>
	    </tr>
    </table>
    <div class="gg_btn"><input type="button" value="返回" onclick="backAutoInvestRecord()"/></div>
</div>
</div>
<script type="text/javascript">
function backAutoInvestRecord(){
	window.parent.loadFirstTenderPage('${basePath}/account/InvestManager/queryFirstTenderLog/'+${pageNo}+'.html');
}
</script>