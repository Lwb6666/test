<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/page/common/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ include file="/WEB-INF/page/common/public.jsp"%>
<title>推荐排名_顶玺金融</title>
</head>
<body>
	<div>
		<%@ include file="/WEB-INF/page/common/header.jsp"%>
		<div>
			<div id="Bmain">
				<div class="title">
					<span class="home"><a href="${path}/">顶玺金融</a></span>
					<span><a href="${path}/myaccount/toIndex.html">我的账户</a></span>
					<span>市场活动</span>
					<span>推荐竞技</span>
				</div>
				<div id="menu_centert">
					<%@ include file="/WEB-INF/page/account/myAccount_leftMenu.jsp"%>
					<div class="lb_waikuang whitebg border">
						<div style="padding:10px 50px; margin:auto;"><img src="${basePath}/images/tip.png"> 注：数据非实时，于每日凌晨1点前更新。</div>
						<div class="title-items">
							<h2>
								<b>活动期间推荐成功人数</b>
							</h2>
							<b class="line"></b>
						</div>
						<div class="myid noborder whitebg">
							<table>
								<tr>
									<td>当月推荐成功人数</td>
									<td>
										${empty inviteNumRankCurMonth.num ? 0 : inviteNumRankCurMonth.num}人
										<a class="blue" href="javascript:;" onclick="show('${basePath}/myaccount/friend/inviteNumDetailList/m/1.html');">（查看人数）</a>
									</td>
									<td>名次</td>
									<td>
										第${empty inviteNumRankCurMonth.rank ? ' - ' : inviteNumRankCurMonth.rank}名
										<a class="blue" href="javascript:;" onclick="show('${basePath}/myaccount/friend/inviteNumRankList/m/1.html');">（查看排名）</a>
									</td>
									<td>上月推荐成功人数竞技奖</td>
									<td> 
									<fmt:formatNumber value="${empty issuedRewardLastMonth ? 0  : issuedRewardLastMonth}" pattern="#,##0.00"/>
									元</td>
								</tr>
								<tr>
									<td>当季推荐成功人数</td>
									<td>
										${empty inviteNumRankCurQuarter.num ? 0 : inviteNumRankCurQuarter.num}人
										<a class="blue" href="javascript:;" onclick="show('${basePath}/myaccount/friend/inviteNumDetailList/q/1.html');">（查看人数）</a>
									</td>
									<td>名次</td>
									<td>
										第${empty inviteNumRankCurQuarter.rank ? ' - ' : inviteNumRankCurQuarter.rank}名
										<a class="blue" href="javascript:;" onclick="show('${basePath}/myaccount/friend/inviteNumRankList/q/1.html');">（查看排名）</a>
									</td>
									<td>上季推荐成功人数竞技奖</td>
									<td>
									 <fmt:formatNumber value="${empty issuedRewardLastQuarter ? 0 : issuedRewardLastQuarter}" pattern="#,##0.00"/>
									元</td>
								</tr>
								<tr>
									<td>年度推荐成功人数</td>
									<td>
										${empty inviteNumRankYear.num ? 0 : inviteNumRankYear.num}人
										<a class="blue" href="javascript:;" onclick="show('${basePath}/myaccount/friend/inviteNumDetailList/y/1.html');">（查看人数）</a>
									</td>
									<td>名次</td>
									<td>
										第${empty inviteNumRankYear.rank ? ' - ' : inviteNumRankYear.rank}名
										<a class="blue" href="javascript:;" onclick="show('${basePath}/myaccount/friend/inviteNumRankList/y/1.html');">（查看排名）</a>
									</td>
									<td>年度推荐成功人数竞技奖</td>
									<td>
									<fmt:formatNumber value="${empty issuedRewardYear ? 0  : issuedRewardYear}" pattern="#,##0.00"/>
									元</td>
								</tr>
							</table>
							<div class="title-items">
								<h2>
									<b>活动期间推荐共享奖</b>
								</h2>
								<b class="line"></b>
							</div>
							<div class="myid noborder whitebg">
								<table>
									<tr>
										<td>上月推荐共享奖</td>
										<td>
											<fmt:formatNumber value="${empty inviteInterestRankLastMonth.interest ? 0 : inviteInterestRankLastMonth.interest}" pattern="#,##0.00" />元 
											<a class="blue" href="javascript:;" onclick="inviteInterestDetailList();">（查看历史推荐共享奖）</a>
										</td>
										<td>名次</td>
										<td>
											第${empty inviteInterestRankLastMonth.rank ? ' - ' : inviteInterestRankLastMonth.rank}名
											<a class="blue" href="javascript:;" onclick="show('${basePath}/myaccount/friend/inviteInterestRankList/m/1.html');">（查看排名）</a>
										</td>
									</tr>
									<tr>
										<td>累积推荐共享奖</td>
										<td>
											<fmt:formatNumber value="${empty inviteInterestRankYear.interest ? 0 : inviteInterestRankYear.interest}" pattern="#,##0.00" />元 
											<a class="blue" href="javascript:;" onclick="inviteInterestDetailList();">（查看历史推荐共享奖）</a>
										</td>
										<td>名次</td>
										<td>
											第${empty inviteInterestRankYear.rank ? ' - ' : inviteInterestRankYear.rank}名
											<a class="blue" href="javascript:;" onclick="show('${basePath}/myaccount/friend/inviteInterestRankList/y/1.html');">（查看排名）</a>
										</td>
									</tr>
								</table>
							</div>
						</div>
					</div>
				</div>
				<%@ include file="/WEB-INF/page/common/footer.jsp"%>
			</div>
		</div>
	</div>
</body>
<script type="text/javascript">
function inviteInterestDetailList() {
	$.layer({
		type : 2,
		fix : false,
		shade : [0.6, '#E3E3E3' , true],
		shadeClose : true,
		border : [10 , 0.7 , '#272822', true],
		title : ['',false],
		offset : ['100px',''],
		area : ['850px','185px'],
		iframe : {src : '${basePath}/myaccount/friend/inviteInterestDetailList.html'},
		close : function(index){
			layer.close(index);
		}
	});
}

function show(_url) {
	$.layer({
		type : 2,
		fix : false,
		shade : [0.6, '#E3E3E3' , true],
		shadeClose : true,
		border : [10 , 0.7 , '#272822', true],
		title : ['',false],
		offset : ['50px',''],
		area : ['600px','350px'],
		iframe : {src : _url},
		close : function(index){
			layer.close(index);
		}
	});
}
</script>
</html>