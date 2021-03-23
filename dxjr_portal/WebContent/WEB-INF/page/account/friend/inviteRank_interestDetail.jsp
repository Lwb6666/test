<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/page/common/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ include file="/WEB-INF/page/common/public.jsp"%>
<title>历史推荐共享奖_顶玺金融</title>
</head>
<body>
	<div class="myid noborder whitebg">
		<p class="safe_p">历史推荐共享奖</p>
		<table>
			<tr>
				<th>&nbsp;</th>
				<th>12月</th>
				<th>1月</th>
				<th>2月</th>
				<th>3月</th>
				<th>4月</th>
				<th>5月</th>
				<th>6月</th>
				<th>7月</th>
				<th>8月</th>
				<th>9月</th>
				<th>10月</th>
				<th>11月</th>
			</tr>
			<tr class="trcolor">
				<th>奖励金额（元）</th>
				<td>${inviteInterestDetailList[0].interest}</td>
				<td>${inviteInterestDetailList[1].interest}</td>
				<td>${inviteInterestDetailList[2].interest}</td>
				<td>${inviteInterestDetailList[3].interest}</td>
				<td>${inviteInterestDetailList[4].interest}</td>
				<td>${inviteInterestDetailList[5].interest}</td>
				<td>${inviteInterestDetailList[6].interest}</td>
				<td>${inviteInterestDetailList[7].interest}</td>
				<td>${inviteInterestDetailList[8].interest}</td>
				<td>${inviteInterestDetailList[9].interest}</td>
				<td>${inviteInterestDetailList[10].interest}</td>
				<td>${inviteInterestDetailList[11].interest}</td>
			</tr>
			<tr>
				<th>发放日期</th>
				<td><fmt:formatDate value="${inviteInterestDetailList[0].rewardIssuedTime}" pattern="MM-dd" /></td>
				<td><fmt:formatDate value="${inviteInterestDetailList[1].rewardIssuedTime}" pattern="MM-dd" /></td>
				<td><fmt:formatDate value="${inviteInterestDetailList[2].rewardIssuedTime}" pattern="MM-dd" /></td>
				<td><fmt:formatDate value="${inviteInterestDetailList[3].rewardIssuedTime}" pattern="MM-dd" /></td>
				<td><fmt:formatDate value="${inviteInterestDetailList[4].rewardIssuedTime}" pattern="MM-dd" /></td>
				<td><fmt:formatDate value="${inviteInterestDetailList[5].rewardIssuedTime}" pattern="MM-dd" /></td>
				<td><fmt:formatDate value="${inviteInterestDetailList[6].rewardIssuedTime}" pattern="MM-dd" /></td>
				<td><fmt:formatDate value="${inviteInterestDetailList[7].rewardIssuedTime}" pattern="MM-dd" /></td>
				<td><fmt:formatDate value="${inviteInterestDetailList[8].rewardIssuedTime}" pattern="MM-dd" /></td>
				<td><fmt:formatDate value="${inviteInterestDetailList[9].rewardIssuedTime}" pattern="MM-dd" /></td>
				<td><fmt:formatDate value="${inviteInterestDetailList[10].rewardIssuedTime}" pattern="MM-dd" /></td>
				<td><fmt:formatDate value="${inviteInterestDetailList[11].rewardIssuedTime}" pattern="MM-dd" /></td>
			</tr>
		</table>
	</div>
</body>
</html>