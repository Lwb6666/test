<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/page/common/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ include file="/WEB-INF/page/common/public.jsp"%>
<title>我的账户</title>
<style type="text/css">
.boxzh {
	overflow: hidden;
	height: 40px;
}

.boxzh  span {
	display: block;
	float: left;
	width: 22%;
}

.boxzh  span img {
	width: 30px;
}

.yuanbaouser {
	padding-top: 14px;
}

.yuanbaouser a {
	color: #06C;
}

.yuanbaouser a:hover {
	color: #039;
}

.name-user:hover .uservip {
	display: block;
}

.uservip {
	border: 1px #ccc solid;
	width: 230px;
	text-align: center;
	border-radius: 5px;
	height: 30px;
	line-height: 30px;
	display: none;
	background: #fff;
	top: 0px;
	left: 130px;
	position: absolute;
}
</style>
</head>
<body>
	<!--头部开始-->
	<%@ include file="/WEB-INF/page/common/header.jsp"%>
	<div class="clear"></div>
	<!--头部结束-->
	
	<div id="Container">
		<div id="Bmain">
			<div id="Bmain_titile">
				<div class="title">
					<span class="home">
						<a href="${path}/"> 顶玺金融 </a>
					</span>
					<span>
						<a href="${path}/myaccount/toIndex.html">我的账户</a>
					</span>
				</div>
				<div id="menu_centert">
					<%@ include file="/WEB-INF/page/account/myAccount_leftMenu.jsp"%>
					<div id="menu_right" style="display: block;">
						<div class="person_manage_info account-bg p-r widthbox" style="margin-bottom: 10px;">
							<div class="myaccount-mail" style="margin-top: -7px;">
								<p style="color: green;">
									<c:if test="${safeMap.emailApproVo.getChecked() == 1}">
										您的邮箱${safeMap.emailApproVo.securityemail }认证已成功
									 </c:if>
									<c:if test="${safeMap.emailApproVo.getChecked() != 1}">
										 您还未进行邮箱认证
		  							</c:if>
								</p>
							</div>
							<div class="myaccount-Phone" style="margin-top: -7px;">
								<p style="color: green;">
									<c:if test="${safeMap.mobileAppro.getPassed() == 1}">
										 您的手机${safeMap.mobileAppro.securitymobileNum }认证已成功  
									</c:if>
									<c:if test="${safeMap.mobileAppro.getPassed() != 1}">
										您还未进行手机认证
									 </c:if>
								</p>
							</div>
							<div class="myaccount-user" style="margin-top: -7px;">
								<p style="color: green;">
									<c:if test="${safeMap.realNameApproVo.getIsPassed() == 1}">
										实名认证已成功
								 	</c:if>
									<c:if test="${safeMap.realNameApproVo.getIsPassed() != 1}">
										您还未实名认证，认证后才可投资
								  	</c:if>
								</p>
							</div>
							<div class="account-img">
								<h1 class="bdm-line h1-height">
									<span class="f16 zhzl">账户总览</span>
									<span class="more-zd">
										<a href="javascript:toBillAccount('${portal:year(portal:currDate())}','${portal:month(portal:currDate())}');">
											<img style="margin-right: 10px;" src="${basePath}/images/account/list-icon_03.png">电子账单>
										</a>
										
										<a href="javascript:chengeUserToStock();">
											<img style="margin-right: 10px;" src="${basePath}/images/account/list-icon_03.png">登录股权系统>
										</a>
									</span>
								</h1>
							</div>
							<div class="user-bigbox box-tc ">
								<ul class="ul-box1">
									<span class="head-user">
										<a href="javascript:toAddHeadImg();">
											<c:if test="${empty memberVo.headImg}">
												<img src="${basePath}/images/main/head.png" id="portrait-user" title="编辑头像" alt="headimg" />
											</c:if>
											<c:if test="${not empty memberVo.headImg}">
												<img src="${basePath}${memberVo.headImg}" id="portrait-user" title="编辑头像" alt="headimg" />
											</c:if>
										</a>
									</span>
								</ul>
								<ul class="ul-box2" style="position: relative; padding-top: 35px;">
									<li class="name-user">
										<span style="float: left; font-size: 14px; font-weight: bolder;">${sayHello }，</span>
										<a href="${path}/accountdetail/show.html?userName=${portal:encode(portal:encode(memberVo.userNameEncrypt))}" target="_blank" style="float: left;">${memberVo.userName}</a>
										<a href="#">
											<img class="hg-img" src="${basePath}/images/vip_${userLevel }.gif" />
										</a>
										<span href="#" class="uservip">${userLevelName }，我的荣誉值：<fmt:formatNumber value="${honor }" pattern="###,###" />
										</span>
									</li>
									<li class="yuanbaouser">
										<span>我的元宝：</span>
										<a href="${path }/account/sycee.html">
											<fmt:formatNumber value="${sycee }" pattern="###,###" />
										</a>
										<a href="${path }/sycee.html" target="_blank" style="padding: 0 15px;">兑换</a>
									</li>
									<li class="progress security-1">
										<span style="float: left;">安全等级：</span>
										<span class="progress progress-bar">
											<p class="rl">
												<span class="bar" style="width:${safeMap.safeDegree}%;" title="${safeMap.safeDegree}/100"></span>
											</p>
										</span>
										<c:if test="${safeMap.safeDegree<100}">
											<a href="${basePath }/AccountSafetyCentre/safetyIndex.html" style="float: left;">
												<img src="${basePath}/images/account/prmoti_10.png" style="margin: -5px 5px 0 0;" />提升
											</a>
										</c:if>
									</li>
									<li class="myaccount-icon-td " style="overflow: hidden;">
										<span>
											<a class="user-a" href="${path }/AccountSafetyCentre/safetyIndex.html">
												<c:if test="${safeMap.realNameApproVo.getIsPassed() == 1}">
													<img class="myaccount-user-icon" src="${basePath}/images/account/vf_11.png" />
												</c:if>
												<c:if test="${safeMap.realNameApproVo.getIsPassed() != 1}">
													<img class="myaccount-user-icon" src="${basePath}/images/account/vf_01.png" />
												</c:if>
											</a>
										</span>
										<span>
											<a class="user-a" href="${path }/AccountSafetyCentre/safetyIndex.html">
												<c:if test="${safeMap.mobileAppro.getPassed() == 1}">
													<img class="myaccount-Phone-icon" src="${basePath}/images/account/vf_22.png" />
												</c:if>
												<c:if test="${safeMap.mobileAppro.getPassed() != 1}">
													<img class="myaccount-Phone-icon" src="${basePath}/images/account/vf_02.png" />
												</c:if>
											</a>
										</span>
										<span>
											<a class="user-a" href="${path }/AccountSafetyCentre/safetyIndex.html">
												<c:if test="${safeMap.emailApproVo.getChecked() == 1}">
													<img class="myaccount-mail-icon" src="${basePath}/images/account/vf_33.png" onmouseover="" onmouseout="" />
												</c:if>
												<c:if test="${safeMap.emailApproVo.getChecked() != 1}">
													<img class="myaccount-mail-icon" src="${basePath}/images/account/vf_03.png" />
												</c:if>
											</a>
										</span>
									</li>
								</ul>
								<div class="ul-box3">
									<ul class="ul-bigbox3-left1" style="margin-left: 10px;">
										<li>可用余额</li>
										<li>冻结金额</li>
										<li>账户资产总额</li>
									</ul>
									<ul class="ul-bigbox3-left2">
										<li class="font-je1 f24 red1">
											<fmt:formatNumber value="${mapCapitalInfo.use_money}" pattern="#,##0.00" />
											<span class="f14 yuanstyle">元</span>
										</li>
										<li class="font-je2 f20">
											<fmt:formatNumber value="${mapCapitalInfo.noUseMoney}" pattern="#,##0.00" />
											<span class="f14 yuanstyle">元</span>
										</li>
										<li class="font-je2 f20">
											<fmt:formatNumber
												value="${mapCapitalInfo.collection + mapCapitalInfo.use_money + mapCapitalInfo.tenderLockAccountTotal + mapCapitalInfo.transferLockAccountTotal + mapCapitalInfo.cashLockTotalMoney + mapCapitalInfo.firstFreezeAccount + mapCapitalInfo.firstUseMoney + mapCapitalInfo.cur_total+mapCapitalInfo.fixCapitalTotal+mapCapitalInfo.fixInvestNoTotal}"
												pattern="#,##0.00" />
											<span class="f14 yuanstyle">元</span>
										</li>
										<li class="user-input-box">
											<input class="head_button1 " type="button" value="充 值" onclick="topupFunction();"> <input class="head_button" type="button" value="提 现" onclick="${portal:currentUser().isFinancialUser==0?'layer.alert(\'无法操作提现\');':'withdrawFunction();'}" style="margin: 0 0 0 20px;">
										</li>
									</ul>
								</div>
							</div>
						</div>
						<div class="gonggao" style="overflow: hidden;">
							<div style="float: left;">
								<span>
									<span style="color: #666; font-weight: bold;">自动投资</span>
									是一种快捷投资方式，您可以自行选择
								</span>
								<a href="${path}/myaccount/autoInvest/autoInvestMain.html" class="btn-gg">设置自动投资</a>
							</div>
							<div style="float: right; padding-right: 15px;">
								<span style="color: #666; font-weight: bold;">推荐好友</span>
								收获意想不到的收益
								<a href="${path}/activity/invited.html" target="new" style="color: #4574c8; padding: 0 10px;">顺手捞一笔</a>
							</div>
						</div>
					</div>
					<div class="fl whitebg p-r " style="margin-top: -10px; border-top: 1px #dbdbdb solid;">
						<div style="width: 938px; background: #1c9bd2;" class="person_manage_info ">
							<ul class="xinxi xinxi-1 xinxi-2" style="height: 60px; padding-top: 20px;">
								<li>
									<p>净收益</p>
									<p class="f20" style="padding-top: 10px;">
										<fmt:formatNumber value="${mapCapitalInfo.netEaring}" pattern="#,##0.00" />
										<span class="f14 yuanstyle" style="color: #fff;">元</span>
									</p>
								</li>
								<li>
									<p>
										收益总额
										<a href="#">
											<img id="imgIn" width="17" height="17" src="${basePath}/images/account/i-white.png">
										</a>
									</p>
									<div id="divIn" class="whitebg bott textleft bd-line " style="line-height: 35px; left: 310px; top: 50px; color: rgb(85, 85, 85); padding: 10px 5px; display: none; z-index: 99999;">
										利息收入（
										<fmt:formatNumber value="${mapCapitalInfo.netMoney_yesInterstTotal }" pattern="#,##0.00" />
										元）、 罚息收入（
										<fmt:formatNumber value="${mapCapitalInfo.netMoney_receiveInterest }" pattern="#,##0.00" />
										元）、 奖励收入（
										<fmt:formatNumber value="${mapCapitalInfo.netMoney_awardTotal }" pattern="#,##0.00" />
										元） <br /> 行权金额（
										<fmt:formatNumber value="${mapCapitalInfo.netMoney_stockMoney }" pattern="#,##0.00" />
										元）、 债权转出收益（
										<fmt:formatNumber value="${mapCapitalInfo.transfer_diff }" pattern="#,##0.00" />
										元）、 活期收益（
										<fmt:formatNumber value="${mapCapitalInfo.cur_interest_total }" pattern="#,##0.00" />
										元）、定期宝收益（
										<fmt:formatNumber value="${mapCapitalInfo.fixInvest_yes_total }" pattern="#,##0.00" />
										元）
									</div>
									<p class="f20" style="padding-top: 10px;">
										<fmt:formatNumber value="${mapCapitalInfo.netMoney}" pattern="#,##0.00" />
										<span class="f14 yuanstyle" style="color: #fff;">元</span>
									</p>
								</li>
								<li>
									<p>
										费用支出
										<a href="#">
											<img id="imgOut" width="17" height="17" src="${basePath}/images/account/i-white.png">
										</a>
									</p>
									<div id="divOut" class="whitebg bott bd-line textleft" style="line-height: 35px; left: 500px; top: 50px; color: rgb(85, 85, 85); padding: 10px 5px; z-index: 1; display: none; z-index: 99999999;">
										借款利息支出（
										<fmt:formatNumber value="${mapCapitalInfo.payTotal_havaPayInterest }" pattern="#,##0.00" />
										元）、 充值费用支出（
										<fmt:formatNumber value="${mapCapitalInfo.payTotal_totalFeeMoney }" pattern="#,##0.00" />
										元）、 借款管理费支出（
										<fmt:formatNumber value="${mapCapitalInfo.payTotal_borrow_manage_fee }" pattern="#,##0.00" />
										元）、 利息管理费支出（
										<fmt:formatNumber value="${mapCapitalInfo.payTotal_inverest_fee }" pattern="#,##0.00" />
										元）、 提现费用支出（
										<fmt:formatNumber value="${mapCapitalInfo.payTotal_cashCost }" pattern="#,##0.00" />
										元）、 转可提费用支出（
										<fmt:formatNumber value="${mapCapitalInfo.payTotal_draw_deduct_fee }" pattern="#,##0.00" />
										元）、 罚息支出（
										<fmt:formatNumber value="${mapCapitalInfo.payTotal_payLateInterest }" pattern="#,##0.00" />
										元）、 VIP费用支出（
										<fmt:formatNumber value="${mapCapitalInfo.payTotal_vip_cost }" pattern="#,##0.00" />
										元）、 转让管理费支出（
										<fmt:formatNumber value="${mapCapitalInfo.payTotal_transfer_manage_fee}" pattern="#,##0.00" />
										元）、 债权认购支出（
										<fmt:formatNumber value="${mapCapitalInfo.payTotal_subscribeTransferDiff}" pattern="#,##0.00" />
										元）
									</div>
									<p class="f20" style="padding-top: 10px;">
										<fmt:formatNumber value="${mapCapitalInfo.payTotal}" pattern="#,##0.00" />
										<span class="f14 yuanstyle" style="color: #fff;">元</span>
									</p>
								</li>
								<li>
									<p></p>
									<p class="f20">
										<span class="f14 yuanstyle" style="float: right; padding-top: 10px;">
											<a href="${path }/accOpertingRecord/paymentDetails/1.html">
												<input class="account-record_button" type="button" value="资金明细">
											</a>
										</span>
									</p>
								</li>
							</ul>
						</div>
					</div>
					<div style="width: 950px; display: block; float: left;">
						<!--活期宝START-->
						<div class="fl mdt15 whitebg p-r" style="">
							<div class="title-box-1" style="border-top: 1px solid #dbdbdb;">
								<span class="f16" style="color: #1182C1;">活期生息</span>
								<a href="${path}/curAccountController/curAccountInterest.html" style="float: right; padding-right: 20px;">收益明细&nbsp;></a>
							</div>
							<div style="width: 938px;" class="person_manage_info account-gray">
								<ul class="xinxi">
									<li>
										<p>累计收益</p>
										<p class="f20 red1">
											<fmt:formatNumber value="${mapCapitalInfo.cur_interest_total}" pattern="#,##0.00" />
											<span class="f14 yuanstyle">元</span>
										</p>
									</li>
									<li>
										<p>昨日收益</p>
										<p class="f20">
											<fmt:formatNumber value="${mapCapitalInfo.cur_interest_yesterday}" pattern="#,##0.00" />
											<span class="f14 yuanstyle">元</span>
										</p>
									</li>
									<li>
										<p>活期宝总额</p>
										<p class="f20">
											<fmt:formatNumber value="${mapCapitalInfo.cur_total}" pattern="#,##0.00" />
											<span class="f14 yuanstyle">元</span>
										</p>
									</li>
									<li style="text-align: right; width: 210px; padding-top: 12px;">
										<input class="head_button" type="button" value="转 入" style="" onclick="javascript:curIn();"> <input class="head_button" style="margin-left: 15px;" type="button" value="转 出" onclick="javascript:curOut();">
									</li>
								</ul>
							</div>
						</div>
						<!--活期宝END-->
						<!--定期宝投资开始-->
						<div class="fl whitebg p-r">
							<div class="title-box-1" style="border-top: 1px solid #dbdbdb;">
								<span class="f16" style="color: #1182C1;">定期宝投资</span>
								<a href="${path}/dingqibao/myAccount.html" style="float: right; padding-right: 20px;">收益明细&nbsp;></a>
							</div>
							<div style="width: 938px;" class="person_manage_info account-gray">
								<ul class="xinxi">
									<li>
										<p>已赚利息</p>
										<p class="f20 red1">
											${retMap==null?0:retMap.repayYesAccountYes}
											<span class="f14 yuanstyle">元</span>
										</p>
									</li>
									<li>
										<p>预期收益</p>
										<p class="f20">
											${retMap==null?0:retMap.repayYesAccountNo}
											<span class="f14 yuanstyle">元</span>
										</p>
									</li>
									<li>
										<p>定期宝总额</p>
										<p class="f20">
											${retMap==null?0:retMap.capital}
											<span class="f14 yuanstyle">元</span>
										</p>
									</li>
									<li style="text-align: right; width: 210px; padding-top: 12px;"></li>
								</ul>
							</div>
						</div>
						<!--定期宝投资结束-->
						<div class="fl whitebg p-r">
							<div class="title-box-1">
								<span class="f16" style="color: #1182C1;">直通车投资</span>
								<a href="${path }/account/InvestManager/collectionindex/z.html" style="float: right; padding-right: 20px;">待收明细&nbsp;></a>
							</div>
							<div style="width: 938px;" class="person_manage_info account-gray">
								<ul class="xinxi xinxi-1">
									<li>
										<p>已赚利息</p>
										<p class="f20 red1">
											<fmt:formatNumber value="${yslx}" pattern="#,##0.00" />
											<span class="f14 yuanstyle">元</span>
										</p>
									</li>
									<li>
										<p>待收利息</p>
										<p class="f20">
											<fmt:formatNumber value="${ztcDslx}" pattern="#,##0.00" />
											<span class="f14 yuanstyle">元</span>
										</p>
									</li>
									<li>
										<p>直通车总额</p>
										<p class="f20">
											<fmt:formatNumber value="${mapCapitalInfo.firstTotal}" pattern="#,##0.00" />
											<span class="f14 yuanstyle">元</span>
										</p>
									</li>
									<li>
										<p>直通车可用余额</p>
										<p class="f20">
											<fmt:formatNumber value="${mapCapitalInfo.firstUseMoney}" pattern="#,##0.00" />
											<span class="f14 yuanstyle">元</span>
										</p>
									</li>
								</ul>
							</div>
						</div>
						<div class="fl whitebg p-r">
							<div class="title-box-1">
								<span class="f16" style="color: #1182C1;">普通标投资</span>
								<span style="font-size: 14px; color: #1182C1; float: left;"> （普通标+购买债权）</span>
								<a href="${path }/account/InvestManager/collectionindex/c.html" style="float: right; padding-right: 20px;">待收明细&nbsp;></a>
							</div>
							<div style="width: 938px;" class="person_manage_info account-gray">
								<ul class="xinxi xinxi-1">
									<li>
										<p>已赚利息</p>
										<p class="f20 red1">
											<fmt:formatNumber value="${mapInvestInfo.yesInterstTotal}" pattern="#,##0.00" />
											<span class="f14 yuanstyle">元</span>
										</p>
									</li>
									<li>
										<p>待收利息</p>
										<p class="f20">
											<fmt:formatNumber value="${mapInvestInfo.interstTotal}" pattern="#,##0.00" />
											<span class="f14 yuanstyle">元</span>
										</p>
									</li>
									<li>
										<p>待收罚息</p>
										<p class="f20">
											<fmt:formatNumber value="${mapInvestInfo.unReceiveInterest}" pattern="#,##0.00" />
											<span class="f14 yuanstyle">元</span>
										</p>
									</li>
									<li>
										<p>待收本金</p>
										<p class="f20">
											<fmt:formatNumber value="${mapInvestInfo.pttbDsbj}" pattern="#,##0.00" />
											<span class="f14 yuanstyle">元</span>
										</p>
									</li>
								</ul>
							</div>
						</div>
						<div class="fl whitebg p-r" style="margin: 0 0 20px 0;">
							<div class="title-box-1">
								<span class="f16" style="color: #1182C1;">我的融资</span>
								<a href="${path }/repayment/enterRepayMent.html" style="float: right; padding-right: 20px;">待还明细&nbsp;></a>
							</div>
							<div style="width: 938px;" class="person_manage_info account-gray">
								<ul class="xinxi xinxi-1">
									<li>
										<p>待还总额</p>
										<p class="f20 red1">
											<fmt:formatNumber value="${mapLoanInfo.repaymentAccountTotal}" pattern="#,##0.00" />
											<span class="f14 yuanstyle">元</span>
										</p>
									</li>
									<li>
										<p>待还利息</p>
										<p class="f20">
											<fmt:formatNumber value="${mapLoanInfo.waitPayInterest}" pattern="#,##0.00" />
											<span class="f14 yuanstyle">元</span>
										</p>
									</li>
									<li>
										<p>待还罚息</p>
										<p class="f20">
											<fmt:formatNumber value="${mapLoanInfo.unPayLateInterest}" pattern="#,##0.00" />
											<span class="f14 yuanstyle">元</span>
										</p>
									</li>
									<li>
										<p>待还本金</p>
										<p class="f20">
											<fmt:formatNumber value="${mapLoanInfo.unpayCapital}" pattern="#,##0.00" />
											<span class="f14 yuanstyle">元</span>
										</p>
									</li>
								</ul>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<div class="clearfix"></div>
	<jsp:useBean id="now" class="java.util.Date" />
	<fmt:formatDate value="${now}" type="both" dateStyle="long" pattern="yyyy-MM-dd" var="nowDate" />
	<!--尾部 s   -->
	<%@ include file="/WEB-INF/page/common/footer.jsp"%>
	<!--尾部 e -->
</body>
<script type="text/javascript">
$(function() {
	// 当页面加载完之后 执行 给id=“imgIn” 的标签添加一个 click 事件
	$("#imgIn").bind("mouseenter", function() {
		$("#divIn").show(200);

	}).bind("mouseout", function() {
		$("#divIn").hide(200);
	});

	$("#imgOut").bind("mouseenter", function() {
		$("#divOut").show(200);

	}).bind("mouseout", function() {
		$("#divOut").hide(200);
	});
	$(".myaccount-mail-icon").hover(function(){
	    $(".myaccount-mail").fadeIn();
	    },function(){
	    $(".myaccount-mail").fadeOut();
	});

	$(".myaccount-Phone-icon").hover(function(){
	    $(".myaccount-Phone").fadeIn();
	    },function(){
	    $(".myaccount-Phone").fadeOut();
	});


	 $(".myaccount-user-icon").hover(function(){
	    $(".myaccount-user").fadeIn();
	    },function(){
	    $(".myaccount-user").fadeOut();
	}); 

});

/**
 * 进入充值页面
 */
function topupFunction(){
	window.location.href = "${path}/account/topup/toTopupIndex.html";
}


/**
 * 进入提现页面
 */
function withdrawFunction(){
	window.location.href = "${path}/myaccount/cashRecord/toCashIndex.html";
}

/* 更改头像 */
function toAddHeadImg(){
		$.layer({
			type : 2,
			fix : false,
			shade : [0.6, '#E3E3E3' , true],
			shadeClose : true,
			border : [10 , 0.7 , '#272822', true],
			title : ['',false],
			offset : ['50px',''],
			area : ['500px','400px'],
			iframe : {src : '${basePath}/myaccount/addHeadImg.html'},
			close : function(index){
				layer.close(index); 
			}
		});
}

//上传成功后，刷新
 function refreshMyAccount(){
	location.href="${path}/myaccount/toIndex.html";
}
 
//会员等级说明
function toUserLevel(){
	//查看借款协议
	$.layer({
		type : 2,
		fix : false,
		shade : [0.6, '#E3E3E3' , true],
		shadeClose : true,
		border : [10 , 0.7 , '#272822', true],
		title : ['',false],
		offset : ['50px',''],
		area : ['750px','480px'],
		iframe : {src : '${basePath}/myaccount/toUserLevel.html'},
		close : function(index){
			layer.close(index);
		}
	});
}

/**
 * 活期宝转出
 */
function curOut() {
	$.ajax({
				url : '${basePath}/curOut/judgeIsCanOut.html',
				data : {},
				type : 'post',
				dataType : 'json',
				success : function(data) {
					if (data.code == "1" || data.code == 1) {
						$.layer({
									type : 2,
									fix : false,
									shade : [ 0.6, '#E3E3E3', true ],
									shadeClose : true,
									border : [ 1, 0.2, '#272822', true ],
									title : [ '', false ],
									offset : [ '150px', '' ],
									area : [ '500px', '300px' ],
									iframe : {
										src : '${basePath}/curOut/enterCurOut/2.html'
									},
									close : function(index) {
										window.open("${path}/myaccount/toIndex.html","_self");
										layer.close(index);
									}
								});
					} else {
						layer.msg(data.message, 2, 5);
					}
				},
				error : function(data) {
					layer.msg('网络连接异常，请刷新页面或稍后重试！', 1, 5);
				}
			});
}

/**
 * 活期宝转入
 */
function curIn() {
	$.ajax({
		url : '${basePath}/curInController/judgeIsCanIn.html',
		data : {},
		type : 'post',
		dataType : 'json',
		success : function(data) {
			if (data.code == "1") {
				$.layer({
					type : 2,
					fix : false,
					shade : [ 0.6, '#E3E3E3', true ],
					shadeClose : true,
					border : [ 1, 0.2, '#272822', true ],
					title : [ '', false ],
					offset : [ '150px', '' ],
					area : [ '550px', '250px' ],
					iframe : {
						src : '${basePath}/curInController/enterCurIn/2.html'
					},
					close : function(index) {
						window.open("${path}/myaccount/toIndex.html","_self");
						layer.close(index);
					}
				});
			} else {
				if("0"==data.code || "-1"==data.code || "-2"==data.code || "-3"==data.code || "-4"==data.code || "-5"==data.code){
					layer.msg(data.message, 1, 5,function(){
						if("0"==data.code){
							window.location.href="${path}/member/toLoginPage.html";
						}else if("-1"==data.code){
					    	window.location.href="${path}/AccountSafetyCentre/safetyIndex.html";
					    }else if("-2"==data.code){
					    	window.location.href="${path}/account/approve/realname/toRealNameAppro.html";
					    }else if("-4"==data.code){
					    	window.location.href="${path}/account/safe/toSetPayPwd.html";
					    }else if("-5"==data.code){
					    	window.location.href="${path}/bankinfo/toBankCard.html";
					    }
					});
					return;
				}
			}
		},
		error : function(data) {
			layer.msg('网络连接异常，请刷新页面或稍后重试！', 1, 5);
		}
	});
}


/**
 * VIP认证
 */
function vipFunction(){
	//提醒用户是否进入VIP页面
	$.ajax({
		url : '${basePath}/account/approve/vip/checkVipAppro.html',
		data : {},
		type : 'post',
		dataType:'text',
		success : function(data){
			if(data=='true'){
				window.open("${path}/account/approve/vip/vipforinsert.html","_parent");
			}else if(data=='false'){
				$.ajax({
					url : '${basePath}/appro/findAppro.html',
					data : {},
					type : 'post',
					success : function(data){
						if(data == null || (data.emailChecked != 1 && data.mobilePassed != 1)){
							layer.msg("请先进行邮箱或手机认证！", 2, 5);
						}else if(data.namePassed != 1){
							layer.msg("请先进行实名认证！", 2, 5);
						}else{
							if(layer.confirm("申请VIP，可保本保息，年费为10元。确认要进入吗？",function(){
								window.open("${path}/account/approve/vip/vipforinsert.html","_parent");
							}));
						}
					},
					error : function(data) {
						layer.msg("网络连接异常，请刷新页面或稍后重试！", 2, 5);
					}
				});	
			}else{
				layer.alert(data);
			}
		},
		error : function(data) {
			layer.msg('网络连接异常，请刷新页面或稍后重试！', 1, 5);
		}
	});	
}

function toBillAccount(year,month){
	var numYear=parseInt(year);
	var numMonth=parseInt(month);
	if(numMonth==1){
		numYear=numYear-1;
		numMonth=12;
	}else{
		numMonth=numMonth-1;
	}	
	location.href="${path}/bill/"+numYear+"/"+numMonth+".html";
}

function chengeUserToStock(){
	//提醒用户是否进入VIP页面
	$.ajax({
		url : '${basePath}/stockApply/checkUserInfo.html',
		type : 'post',
		dataType:'text',
		success : function(data){
			var jsonobj=eval('('+data+')');
			if(jsonobj.status==3){
				location.href="${path}/stockAccount/accountIndex.html";
			}else if(jsonobj.status==1){
				if(layer.confirm(jsonobj.message,function(){
					location.href="${path}/stockApply/inRegister.html";
				}));
			}else if(jsonobj.status==2){
				layer.msg(jsonobj.message, 1, 5);
			}
		},
		error : function(data) {
			layer.msg('网络连接异常，请刷新页面或稍后重试！', 1, 5);
		}
	});	
}

</script>
</html>
