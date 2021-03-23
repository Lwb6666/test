<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/page/common/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ include file="/WEB-INF/page/common/public.jsp"%>
<!--直通车列表 内容开始--> 
<div id="rz_main" style="margin-top:0;">
	<div class="tbztcr">
     <span class="top">
     <h3>直通车列表&nbsp;&nbsp;</h3>
     </span>
        <div class="clear"> </div>
        <table>
         <tr>
          <th>直通车标题</th>
          <th>期数</th>
          <th>计划总额</th>
          <th>加入条件</th>
          <th>预期收益</th>
          <th>开始时间</th>             
          <th>进度</th>
          <th>状态</th>     
         </tr>
         <c:forEach items="${page.result}" var="firstBorrowVo" varStatus= "idx" >
         <tr>
          <td><a href="javascript:toInvestFirst(${firstBorrowVo.id});">${firstBorrowVo.name }</a><c:if test="${firstBorrowVo.status==3}"><img src="${basePath}/images/new.gif"/></c:if></td>
          <td align="center">第${firstBorrowVo.periods }期</td>
          <td align="center"><fmt:formatNumber value="${firstBorrowVo.planAccount}" pattern="#,##0"></fmt:formatNumber></span>元</td>
          <td align="center"><fmt:formatNumber value="${firstBorrowVo.lowestAccount}" pattern="#,##0"></fmt:formatNumber>元</td>
          <td align="center">${firstBorrowVo.perceivedRate }%</td>
          <td align="center"><fmt:formatDate value="${firstBorrowVo.publishTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
          <td align="center">
          	 <DIV style="width:40px; height:40px; margin:0 auto;" class="investDiagramCls" id="diagram${idx.index}"></DIV>
             <DIV style="display: none;" class="get1">
             <DIV class="arc1">
            	<INPUT id="percent${idx.index}" class="investPercentCls" value="${firstBorrowVo.percent }" type="hidden" default="0">	
           		<c:choose>
             	<c:when test="${firstBorrowVo.percent < 100}">
				<INPUT class="color1" value="#00a7e5" type="hidden">
				</c:when>
				<c:otherwise>
				<INPUT class="color1" value="#94c73d" type="hidden">
				</c:otherwise>
				</c:choose>
             </DIV>
             </DIV>
          </td>
          <td align="center">
       	    <c:if test="${firstBorrowVo.status==3 && firstBorrowVo.publishTime > nowTime }">
          	<input class="but gray" type="button" value="即将开通" onclick="toInvestFirst(${firstBorrowVo.id});"/>
          	</c:if>
          	<c:if test="${(firstBorrowVo.status==3 && firstBorrowVo.publishTime <= nowTime) || (firstBorrowVo.status==5 && firstBorrowVo.planAccount > firstBorrowVo.realAccount)}">
          	<input class="but bluess" type="button" value="开通未满" onclick="toInvestFirst(${firstBorrowVo.id});"/>
          	</c:if>
          	<c:if test="${firstBorrowVo.status==5 && firstBorrowVo.planAccount == firstBorrowVo.realAccount }">
          	<input class="but greens" type="button" value="开通已满" onclick="toInvestFirst(${firstBorrowVo.id});"/>
          	</c:if>
          </td>
         </tr>
         </c:forEach>
         <c:if test="${fn:length(page.result) == 0}">
         <tr>
         	<td colspan="8" align="center"><b>没有可开通的直通车</b></td>
         </tr>
         </c:if>
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
</div>
<!--直通车列表 内容结束-->
<script type="text/javascript">
$(function(){
	initProgress();
});
function initProgress(){
	var investDiagram = $(".investDiagramCls");
 	if(investDiagram.length>0){
		$(investDiagram).each(function(index,element){
	   		var  count=  $("#percent"+index).val(); 
	   		if(Number(count) < 0.001){
				oo.num = 0.01;
				oo.cicleNum = count;
			}else{
				oo.num = count;
				oo.cicleNum = count;
			}
	   		oo.diagramDiv = "diagram"+index;
			oo.getDiv = ".get1";
			oo.init();
	 	});
 	}
}
//去开通直通车
function toInvestFirst(id){
	window.open("${path}/zhitongche/"+id+".html","_parent");
}

/**
 * ajax 翻页功能
 */
function findPage(pageNo){
	window.parent.turnPageParent(pageNo);
}
</script>