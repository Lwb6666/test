<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ page import="java.util.Calendar"%>
<%@ include file="/WEB-INF/page/common/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta charset="utf-8">
<title>${year}年${month}月-电子对账单-顶玺金融</title>
<%@ include file="/WEB-INF/page/common/public.jsp"%>
<link href="${basePath}/css/bill/style.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${basePath}/js/highcharts/highcharts3.0.7.js"></script>
<script type="text/javascript" src="${basePath}/js/highcharts/themes/yellow.js"></script>
<script type="text/javascript" src="${basePath}/js/highcharts/myChart.js"></script>
</head>
<body>
	<!--head -->
	<!--头部开始-->
	<%@ include file="/WEB-INF/page/common/header.jsp"%>
	<!--head off-->
	<div class="all-page"></div>
	<div class="clearfix"></div>
	<div class="content">
		<div class="c-title"></div>
		<div class="user-info pl30 pt20 bdm">
			<em><shiro:principal property="userName" /></em><span class="ml10"><em class="black_1">${year}</em>年<em class="black_1">${month}</em>月我在顶玺金融的成就</span>
			<div class="ui-bill-month ui-list-month f16">
				<p onclick="showHideItems();">
					${year}年${month}月（${month}月1日-${month}月${portal:lastday(year, month)}日）<span class="arrow-down arrow-down-white"></span>
				</p>
				<ul id="historyperiod" style="display: none; list-style: none;">
					<%
						Calendar c = Calendar.getInstance();
						int year = c.get(Calendar.YEAR);
						int month = c.get(Calendar.MONTH) + 1;
						for (int i = 0; i < 12; i++) {
							if ((month - 1) <= 0) {
								year = year - 1;
								month = (month) + 12;
							}
							if (year < 2015) {
								break;
							}
							c.set(Calendar.YEAR, year);
							c.set(Calendar.MONTH, (--month) - 1);
							int lastDay = c.getActualMaximum(Calendar.DAY_OF_MONTH);
					%>
					<li><a href="${path}/bill/<%=year%>/<%=month%>.html"> <%=year%>年<%=month%>月（<%=month%>月1日-<%=month%>月<%=lastDay%>日）
					</a></li>
					<%
						}
					%>
				</ul>
			</div>
		</div>
		<div class="pt20 pl30 fl w97 bdm pb10">
			<div class="interest-title img1">
				<span class="fr f16">近一年利息收入(单位：元）</span>
			</div>
			<div class="record">
				<div class="pic1 mt20 fl"></div>
				<div class="fl pd20">
					<em class="green_1 f36"> ${accountStatement.interst}</em>元
					<p>利息收入</p>
					<em class="orange_1 f24">${accountStatement.totalInterst}</em>元
					<p>累计利息收入</p>
				</div>
			</div>
			<div class="fr pd20 bdl pdt">
				<div class="fl chart" id="interstChart"></div>
			</div>
		</div>
		<div class="pt20 pl30 fl w97 bdm pb10">
			<div class="interest-title img2"></div>
			<div class="pd20">
				<div class="fn-clear ui-bill-info bdm1">
					<dl class="fl ui-bill-total pl30">
						<dd class="fn-clear pb10 bdr">
							<p>
								<em class="orange_1">${accountStatement.lateInterest}</em>元
							</p>
							<p>罚息收入</p>
							<p>
								<em class="orange_1">${accountStatement.awardMoney}</em>元
							</p>
							<p>奖励收入</p>
							<p>
								<em class="orange_1">${accountStatement.stockMoney}</em>元
							</p>
							<p>行权金额</p>

							<p>
								<em class="orange_1">${accountStatement.transferDiff}</em>元
							</p>
							<p>债权转出收益</p>

							<p>
								<em class="orange_1">${curAccountMap.cur_interest_total}</em>元
							</p>
							<p>活期收益</p>
                            <p>
								<em class="orange_1">${accountStatement.fixInterest}</em>元
							</p>
							<p>定期宝收益</p>

						</dd>
					</dl>
					<dl class="fl ui-bill-total  pl30">
						<dd class="fn-clear pb10">
							<p>
								<em class="orange_1">${accountStatement.interestRepay}</em>元
							</p>
							<p>借款利息支出</p>
							<p>
								<em class="orange_1">${accountStatement.rechargeFee}</em>元
							</p>
							<p>充值费用支出</p>

							<p>
								<em class="orange_1">${accountStatement.borrowManageFee}</em>元
							</p>
							<p>借款管理费支出</p>

							<p>
								<em class="orange_1">${curAccountMap.payTotal_subscribeTransferDiff}</em>元
							</p>
							<p>债权认购支出</p>

						</dd>
					</dl>
					<dl class="fl ui-bill-total  pl30">
						<dd class="fn-clear pb10">
							<p>
								<em class="orange_1">${accountStatement.interestCost}</em>元
							</p>
							<p>利息管理费支出</p>
							<p>
								<em class="orange_1">${accountStatement.cashCost}</em>元
							</p>
							<p>提现费用支出</p>
							<p>
								<em class="orange_1">${accountStatement.drawDeductFee}</em>元
							</p>
							<p>转可提费用支出</p>
						</dd>
					</dl>
					<dl class="fl ui-bill-total  pl30">
						<dd class="fn-clear pb10">
							<p>
								<em class="orange_1">${accountStatement.lateInterest}</em>元
							</p>
							<p>罚息支出</p>
							<p>
								<em class="orange_1">${accountStatement.vipCost}</em>元
							</p>
							<p>VIP费用支出</p>
							<p>
								<em class="orange_1">${accountStatement.transferManageFee}</em>元
							</p>
							<p>转让管理费支出</p>
						</dd>
					</dl>
				</div>
				<div class="fn-clear ui-bill-info bdm1">
					<dl class="fl ui-bill-total pl30">
						<dd class="fn-clear pb10 bdr">
							<p>
								<strong><em class="orange_1">${accountStatement.otherTotalIncome}</em>元</strong>
							</p>
							<p>
								<strong>其他收入合计</strong>
							</p>
						</dd>
					</dl>
					<dl class="fl ui-bill-total  pl30">
						<dd class="fn-clear pb10">
							<p>
								<strong><em class="orange_1">${accountStatement.totalExpenditure}</em>元</strong>
							</p>
							<p>
								<strong>支出合计</strong>
							</p>
						</dd>
					</dl>
				</div>
			</div>
			<div class="m-bill">
				<c:if test="${accountStatement.netIncome < 1000}">
					<img src="${basePath}/images/accountbill/img16.jpg">
				</c:if>
				<c:if test="${accountStatement.netIncome >= 1000 && accountStatement.netIncome < 5000}">
					<img src="${basePath}/images/accountbill/img15.jpg">
				</c:if>
				<c:if test="${accountStatement.netIncome < 10000 && accountStatement.netIncome >= 5000}">
					<img src="${basePath}/images/accountbill/img17.jpg">
				</c:if>
				<c:if test="${accountStatement.netIncome > 10000}">
					<img src="${basePath}/images/accountbill/img14.jpg">
				</c:if>
				<div class="num">
					<Div class="fl f16 tex-r">
						<h5>净收入</h5>
						<p>
							<em class="f24 green_1">${accountStatement.netIncome}</em>元
						</p>
					</Div>
					<div class="fl symbol">=</div>
					<Div class="fl f16 tex-r">
						利息收入
						<p>
							<em class="f24 orange_1">${accountStatement.interst}</em>元
						</p>
					</Div>
					<div class="fl symbol">+</div>
					<Div class="fl f16 tex-r">
						其他收入合计
						<p>
							<em class="f24 orange_1">${accountStatement.otherTotalIncome}</em>元
						</p>
					</Div>
					<div class="fl symbol">-</div>
					<Div class="fl f16 tex-r">
						支出合计
						<p>
							<em class="f24 orange_1">${accountStatement.totalExpenditure}</em>元
						</p>
					</Div>

				</div>

			</div>
		</div>
		<div class="pt20 pl30 fl w97 bdm pb10 tc">

			<div style="float: left;" id="creditItemPercent"></div>
			<div style="float: right;" id="debitItemPercent"></div>

		</div>
		<div class="pt20 pl30 fl w97 bdm pb10">
			<div class="interest-title img3"></div>
			<div class="pd20">
				<div class="fn-clear ui-bill-info">
					<dl class="fl ui-bill-total pl30">
						<dd class="fn-clear pb10">
							<p>
								<em class="orange_1 f20">${accountStatement.tenderCount}</em>笔
							</p>
							<p>当月累计投标笔数</p>
						</dd>
					</dl>
					<dl class="fl ui-bill-total pl30">
						<dd class="fn-clear pb10">
							<p>
								<em class="orange_1">${accountStatement.avgApr}</em>%
							</p>
							<p>平均预期收益</p>
						</dd>
					</dl>
					<dl class="fl ui-bill-total pl30">
						<dd class="fn-clear pb10">
							<p>
								<em class="orange_1">${accountStatement.tenderAccount}</em>元
							</p>
							<p>当月累计投标金额</p>
						</dd>
					</dl>
					<dl class="fl ui-bill-total pl30">
						<dd class="fn-clear pb10">
							<p>
								<em class="orange_1">${accountStatement.totalTenderAccount}</em>元
							</p>
							<p>历史累计投标金额</p>
						</dd>
					</dl>
				</div>
				<p class="pl30 f16 mt10">
					<c:if test="${accountStatement.tenderRate >= 80}">
						 当月投标金额超过了<em class="orange_1 f20">${accountStatement.tenderRate}%</em>的人，您不愧是理财专家
					</c:if>
					<c:if test="${accountStatement.tenderRate > 60 && accountStatement.tenderRate < 80}">
						 当月投标金额超过了<em class="orange_1 f20">${accountStatement.tenderRate}%</em>的人，您不愧是理财高手
					</c:if>
					<c:if test="${accountStatement.tenderRate >= 30 && accountStatement.tenderRate < 60}">
						当月投标金额超过了<em class="orange_1 f20">${accountStatement.tenderRate}%</em>的人，您不愧是理财达人
					</c:if>
					<c:if test="${accountStatement.tenderRate < 30}">
						当月投标金额超过了<em class="orange_1 f20">${accountStatement.tenderRate}%</em>的人，您是理财新手
					</c:if>
					<span class="ml20"> <img
						src="${basePath}/images/accountbill/img_rate_<fmt:formatNumber value='${(accountStatement.tenderRateDouble - accountStatement.tenderRateDouble % 10) / 10}' maxFractionDigits='0' pattern='#'/>.png">
					</span>
				</p>
			</div>
		</div>
		<div class="pt20 pl30 fl w97 bdm pb10">
			<div class="interest-title img4"></div>
			<div class="pd20">
				<div class="fn-clear ui-bill-info">
					<c:if test="${month<11}">
						<dl class="fl ui-bill-total pl30">
							<dd class="fn-clear pb10">
								<p>
									当月累计积分：<em class="orange_1 f20">${accountStatement.bbsPoint}</em>分
								</p>
								<p>
									当月工资：<em class="orange_1 f20">${accountStatement.bbsPointMoney}</em>元
								</p>
							</dd>
						</dl>
					</c:if>
					<dl class="fl ui-bill-total pl30">
						<dd class="fn-clear pb10">
							<p>
								当月发帖数：<em class="orange_1 f20">${accountStatement.bbsItems}</em>帖
							</p>
							<p>
								当月累计回帖数：<em class="orange_1 f20">${accountStatement.bbsNotes}</em>次
							</p>
						</dd>
					</dl>
					<dl class="fl ui-bill-total pl30">
						<dd class="fn-clear pb10">
							<p>
								当月累计被回帖数：<em class="orange_1 f20">${accountStatement.bbsBeNotes}</em>次
							</p>
							<p>
								当月精华帖：<em class="orange_1 f20">${accountStatement.bbsCategoryItems}</em>篇
							</p>
						</dd>
					</dl>
				</div>
				<p class="pl30 f16 mt10">
					<c:if test="${accountStatement.bbsPoint == 0}">
						<i class="f24">真是太糟糕了！</i>您在论坛活跃度太低了，活跃指数
						<span class="ml20"><img src="${basePath}/images/accountbill/img_1.png"></span>
					</c:if>
					<c:if test="${accountStatement.bbsPoint > 0 && accountStatement.bbsPoint < 100}">
						<i class="f24">真是太糟糕了！</i>您在论坛活跃度太低了，活跃指数
						<span class="ml20"><img src="${basePath}/images/accountbill/img_2.png"></span>
					</c:if>
					<c:if test="${accountStatement.bbsPoint >= 100 && accountStatement.bbsPoint < 200}">
						<i class="f24">表现一般！</i>您在论坛的活跃度需大力提升，活跃指数
						<span class="ml20"><img src="${basePath}/images/accountbill/img_3.png"></span>
					</c:if>
					<c:if test="${accountStatement.bbsPoint >= 200 && accountStatement.bbsPoint < 300}">
						<i class="f24">表现不错！</i>您在论坛已经游鱼得水，活跃指数
						<span class="ml20"><img src="${basePath}/images/accountbill/img_4.png"></span>
					</c:if>
					<c:if test="${accountStatement.bbsPoint >= 300 && accountStatement.bbsPoint < 400}">
						<i class="f24">太棒了！</i>您拉动了整个论坛的气氛，活跃指数
						<span class="ml20"><img src="${basePath}/images/accountbill/img_5.png"></span>
					</c:if>
					<c:if test="${accountStatement.bbsPoint >= 400}">
						<i class="f24">奇迹出现了！</i>您的表现超越了我们的想象，活跃指数
						<span class="ml20"><img src="${basePath}/images/accountbill/img_6.png"></span>
					</c:if>
				</p>
			</div>
		</div>
		<div class="w97 pb10">
			<c:if test="${accountStatement.totalInterst >= 120000}">
				<img src="${basePath}/images/accountbill/img_49.jpg" />
			</c:if>
			<c:if test="${accountStatement.totalInterst >= 90000 && accountStatement.totalInterst < 120000}">
				<img src="${basePath}/images/accountbill/img_48.jpg" />
			</c:if>
			<c:if test="${accountStatement.totalInterst >= 60000 && accountStatement.totalInterst < 90000}">
				<img src="${basePath}/images/accountbill/img_47.jpg" />
			</c:if>
			<c:if test="${accountStatement.totalInterst >= 30000 && accountStatement.totalInterst < 60000}">
				<img src="${basePath}/images/accountbill/img_50.jpg" />
			</c:if>
			<c:if test="${accountStatement.totalInterst < 30000}">
				<img src="${basePath}/images/accountbill/img_51.jpg" />
			</c:if>
		</div>
	</div>
	<%@ include file="/WEB-INF/page/common/footer.jsp"%>
</body>
<script type="text/javascript">
	$(function() {
		showInterstChart();
		showCreditItemPercent();
		showDebitItemPercent();
	});

	function showDebitItemPercent() {
		$.get("${basePath}/bill/showDebitItemPercent/${year}/${month}.html", function(data) {
			var chartWidth = 533;
			var chartHight = 359;
			var chartText = '支出各项占比（%）';
			var xCategories = data['xCategories'];
			var xText = data['xText'];
			var yText = data['yText'];
			var seriesName = data['seriesName'];
			var chartType = data['chartType'];
			var functionCode = function(event) {
			};
			chart = new Highcharts.Chart({
				chart : {
					plotBackgroundColor : null,
					plotBorderWidth : null,
					plotShadow : false,
					renderTo : 'debitItemPercent',
					width : chartWidth,
					hight : chartHight,
					type : chartType
				},
				credits : {
					enabled : false
				},
				title : {
					text : chartText
				},
				tooltip : {
					pointFormat : '{series.name}: <b>{point.percentage:.1f}%</b>'
				},
				plotOptions : {
					pie : {
						size : '90%',
						allowPointSelect : true,
						cursor : 'pointer',
						dataLabels : {
							enabled : false
						},
						showInLegend : true
					}
				},
				series : [ {
					type : chartType,
					name : '占比',
					data : data
				} ]
			});
			setChartData(data);
		});
	}
	function showCreditItemPercent() {
		$.get("${basePath}/bill/showCreditItemPercent/${year}/${month}.html", function(data) {
			var chartWidth = 533;
			var chartHight = 359;
			var chartText = '收入各项占比（%）';
			var xCategories = data['xCategories'];
			var xText = data['xText'];
			var yText = data['yText'];
			var seriesName = data['seriesName'];
			var chartType = data['chartType'];
			var functionCode = function(event) {
			};
			chart = new Highcharts.Chart({
				chart : {
					plotBackgroundColor : null,
					plotBorderWidth : null,
					plotShadow : false,
					renderTo : 'creditItemPercent',
					width : chartWidth,
					hight : chartHight,
					type : chartType
				},
				credits : {
					enabled : false
				},
				title : {
					text : chartText
				},
				tooltip : {
					pointFormat : '{series.name}: <b>{point.percentage:.1f}%</b>'
				},
				plotOptions : {
					pie : {
						size : '90%',
						allowPointSelect : true,
						cursor : 'pointer',
						dataLabels : {
							enabled : false
						},
						showInLegend : true
					}
				},
				series : [ {
					type : chartType,
					name : '占比',
					data : data
				} ]
			});
			setChartData(data);
		});
	}

	function showInterstChart() {
		$.get("${basePath}/bill/showInterst/${year}/${month}.html", function(data) {
			var chartWidth = 600;
			var chartHight = 231;
			var chartText = '';
			var xCategories = data['xCategories'];
			var xText = data['xText'];
			var yText = data['yText'];
			var seriesName = data['seriesName'];
			var chartType = data['chartType'];
			var functionCode = function(event) {
			};

			chart = new Highcharts.Chart({
				chart : {
					renderTo : 'interstChart',
					width : chartWidth,
					hight : chartHight,
					type : chartType
				},
				credits : {
					enabled : false
				},
				title : {
					text : chartText
				},
				xAxis : {
					categories : xCategories
				},
				yAxis : {
					title : {
						text : ''
					}
				},
				plotOptions : {
					dataLabels : {
						enabled : false
					},
					column : {
						// 允许线性图上的数据点进行点击
						allowPointSelect : true,
						// 数据点的点击事件
						events : {
							click : functionCode
						},
						// 当具体的数据点被点击时的事件响应函数。如果不需要事件响应，可以删除。
						point : {
							events : {
								click : functionCode
							}
						},
						// 是否在图注中显示。
						showInLegend : true,
						// 调整图像顺序关系
						zIndex : 3
					}
				},
				series : [ {
					type : chartType,
					name : '利息收入',
					data : data
				} ]
			});
			setChartData(data);
		});
	}
	function showHideItems() {
		var n = $("#historyperiod").css("display");
		if (n == 'block') {
			$("#historyperiod").css("display", "none");
		} else {
			$("#historyperiod").css("display", "block");
		}
	}
</script>
</html>
