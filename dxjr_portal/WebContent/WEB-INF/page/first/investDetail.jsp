<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<div class="tbuser">直通车介绍</div>
	<div class="rz_borrow1 tbhr">
		<div class="tbinfo">
			<p>投标直通车是顶玺金融推出的一款自动投标系统。目的是简化投资者的投标流程，提高投资者的资金使用效率。让投资者从复杂的投标流程中解放出来，收获轻松理财的快乐。达到投资者、借款者和平台三方共赢的目标！</p>
			 
		</div>
	</div>
	<div class="rz_borrow1 tbhr">
		<div id="icon">
			<div id="icon_block">
				<div class="icon_t"></div>
				<div class="icon_font">
					加入总人次
					<div class="icon_font1">
						<fmt:formatNumber value="${fristMapData.firstTotalCount}" pattern="#,##0" /><font style="font-size: 12px">次</font>
					</div>
				</div>
				<div class="iconfont" onmousemove="$(this).find('div').show();" onmouseout="$(this).find('div').hide();">
					<div class="poptip-content" style="display: none;">所有直通车开通成功人次总和</div>
				</div>
			</div>
			<div id="icon_block">
				<div class="icon_t1"></div>
				<div class="icon_font">
					直通车总额(￥)
					<div class="icon_font1">
						<fmt:formatNumber value="${fristMapData.firstTotalAccount}" pattern="#,##0" /><font style="font-size: 12px">万元</font>
					</div>
				</div>
				<div class="iconfont" onmousemove="$(this).find('div').show();" onmouseout="$(this).find('div').hide();">
					<div class="poptip-content" style="display: none;">当前直通车锁定总金额</div>
				</div>
			</div>
			<div id="icon_block">
				<div class="icon_t2"></div>
				<div class="icon_font">
					为用户累计赚取
					<div class="icon_font1">
						<fmt:formatNumber value="${fristMapData.firstTotalInterst/10000}" pattern="#,##0" /><font style="font-size: 12px">万元</font>
					</div>
				</div>
				<div class="iconfont" onmousemove="$(this).find('div').show();" onmouseout="$(this).find('div').hide();">
					<div class="poptip-content" style="display: none;">所有用户通过直通车金额投标赚取的已收利息+待收利息总和</div>
				</div>
			</div>
			<div id="icon_block">
				<div class="icon_t3"></div>
				<div class="icon_font">
					资金利用率
					<div class="icon_font1">${fristMapData.firstAccountRate}%</div>
				</div>
				<div class="iconfont" onmousemove="$(this).find('div').show();" onmouseout="$(this).find('div').hide();">
					<div class="poptip-content" style="display: none;">
						1 -（减去） 每日累计直通车余额/每日累计直通车总额*100%<br />
						每日直通车总额 = 截止到每日23：00之前的所有开通成功总额 -（减去） 截止到每日23：00之前的所有开通结束总额【包括自动结束+手动结束】。（23：00时间可修改）。<br />
						每日直通车余额 = 截止到每日 23：00之间之前的所有直通车的未投标金额。<br />
						每日累计直通车总额 = 每日直通车总额的累加。<br />
						每日累计直通车余额 = 每日直通车余额的累加。
					</div>
				</div>
			</div>
			<div id="icon_block" style="border-right: none;">
				<div class="icon_t4"></div>
				<div class="icon_font">
					为用户自动投标
					<div class="icon_font1">
						<fmt:formatNumber value="${fristMapData.firstTenderCount}" pattern="#,##0" /><font style="font-size: 12px">次</font>
					</div>
				</div>
				<div class="iconfont" onmousemove="$(this).find('div').show();" onmouseout="$(this).find('div').hide();">
					<div class="poptip-content1" style="display: none;">所有投标成功的抵押标、信用标、担保标中用直通车金额投标次数总和</div>
				</div>
			</div>
		</div>
	</div>
	<div class="tbuser">直通车原理加入流程</div>
	<div class="rz_borrow1">
		<div class="tblct"></div>
	</div>
