<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/page/common/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ include file="/WEB-INF/page/common/public.jsp"%>
<title>股东加权排名</title>
</head>
<body>
<div class="caiwubg"> 
<table>
    <tr>
		<th height="38">用户名</th>
		<c:if test="${type=='1' }">
	    <th>加权待收排名</th>
	    <th>加权待收排名得分</th>
	    <th>加权待收</th>
	    </c:if>
	    <c:if test="${type=='2' }">
	    <th>累计总积分排名</th>
	    <th>累计总积分排名得分</th>
	    <th>累计总积分</th>
	    </c:if>
	    <c:if test="${type=='3' }">
	    <th>投标直通车总额排名</th>
	    <th>投标直通车总额排名得分</th>
	    <th>投标直通车总额</th>
	    </c:if>
	    <c:if test="${type=='4' }">
	    <th>推广人数排名</th>
	    <th>推广人数排名得分</th>
	    <th>推广人数</th>
	    </c:if>
	    <c:if test="${type=='5' }">
	    <th>综合排名</th>
	    <th>综合得分</th>
	    </c:if>
    </tr>
    <c:forEach items="${shareholderRankList}" var="shareholderRank" varStatus="sta" step="1">
	  <tr align="center">
	  	<td><a href="${path}/accountdetail/show.html?userName=${portal:encode(portal:encode(shareholderRank.username))}" target="_blank"
	  	 title="${shareholderRank.username }">${shareholderRank.username }</a></td>
	   	<c:if test="${type=='1' }">
	   	<td>${shareholderRank.dayInterstRank }</td>
	   	<td>${shareholderRank.dayInterstScore }</td>
	   	<td><fmt:formatNumber value="${shareholderRank.dayInterst}" pattern="#,##0.00"/></td>
	   	</c:if>
	   	<c:if test="${type=='2' }">
	   	<td>${shareholderRank.accumulatepointsRank }</td>
	   	<td>${shareholderRank.accumulatepointsScore }</td>
	   	<td>${shareholderRank.accumulatepoints }</td>
	   	</c:if>
	   	<c:if test="${type=='3' }">
	   	<td>${shareholderRank.firstTenderTotalRank }</td>
	   	<td>${shareholderRank.firstTenderTotalScore }</td>
	   	<td>${shareholderRank.firstTenderTotal }</td>
	   	</c:if>
	   	<c:if test="${type=='4' }">
	   	<td>${shareholderRank.extensionNumberRank }</td>
	   	<td>${shareholderRank.extensionNumberScore }</td>
	   	<td>${shareholderRank.extensionNumber }</td>
	   	</c:if>
	   	<c:if test="${type=='5' }">
	   	<td>${shareholderRank.totalRank }</td>
	   	<td>${shareholderRank.totalScore }</td>
	   	</c:if>
	  </tr>
	  </c:forEach>
</table>
</div>
<!-- 显示用户详情表单 -->
<form action="${basePath}/newdxjr/accountdetail/show.html"  id="showMemberInfoFrom" target="_blank" method="post">
	<input type="hidden" name="username" id="memberInfoUsername"/>
</form>
</body>
<script type="text/javascript">
/**
 * 显示用户详情
 */
function showMemberInfo(username){
	$("#memberInfoUsername").val(username);
	$("#showMemberInfoFrom").submit();
}
</script>
</html>
