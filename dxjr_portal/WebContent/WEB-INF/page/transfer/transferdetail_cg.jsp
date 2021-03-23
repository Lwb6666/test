<%@ page language="java" contentType="text/html; charset=utf-8"
		 pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/page/common/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<title>${bTransfer.transferName}_债权转让-顶玺金融官网</title>
	<meta name="keywords" content="${bTransfer.transferName}" />
	<meta name="description" content="顶玺金融是中国3A信用评级互联网理财借贷平台，如果你想了解更多P2P理财、网络投资理财或者资金周转借贷信息，详情请登陆www.dxjr.com。">
	<%@ include file="/WEB-INF/page/common/public4.jsp"%>
	<script type="text/javascript" src="${basePath}/js/formatMoney.js?version=<%=version%>"></script>
</head>

<body>
<%@ include file="/WEB-INF/page/common/header.jsp"%>
<form action="subscribeTransferForm" method="post" id="subscribeTransferForm">
	<!-- 当前时间 -->
	<input type="hidden" id="nowTime" value="<fmt:formatDate value="${nowTime}" pattern="yyyy/MM/dd HH:mm:ss"/>"/>
	<!-- 标时间 -->
	<input type="hidden" id="transfer_addtime_hide"
		   value="<fmt:formatDate value="${bTransfer.addTime }" pattern="yyyy/MM/dd HH:mm:ss"/>"/>
	<input type="hidden" name="useMoney" id="useMoney" value="${useMoney}"/>
	<input type="hidden" name="transferid" id="transferid" value="${bTransfer.id}"/>
	<input type="hidden" id="effectiveTenderMoney" value="${effectiveTenderMoney }"/>
	<input type="hidden" name="platform" id="platform" value="1"/>
	<input type="hidden" name="sumAccount" id="sumAccount" value="${sumAccount }"/>
	<input type="hidden" name="isCustodyMember" id="isCustodyMember" value="${isCustodyMember }"/>

	<!--我的账户左侧开始-->
	<div class="product-wrap wrapperbanner"><!--信息star-->
		<div class="grid-1100">
			<div class="product-deatil clearfix ">
				<div class="cunguan"></div>
				<h1 class="f16">转让信息</h1>
				<div class="detail-col-l fl item-bd-r">
					<div class="f16 pdtt20">
						<span>
							<i class="icon icon-zs pdr" title="银行存管"></i>
							<c:choose>
								<c:when test="${borrow.borrowtype == 1}">
									<i class="icon icon-xin pdr"></i>
								</c:when>
								<c:when test="${borrow.borrowtype == 2}">
									<i class="icon icon-di pdr"></i>
								</c:when>
								<c:when test="${borrow.borrowtype == 3}">
									<i class="icon icon-jing pdr"></i>
								</c:when>
								<c:when test="${borrow.borrowtype == 4}">
									<i class="icon icon-miao pdr"></i>
								</c:when>
								<c:when test="${borrow.borrowtype == 5}">
									<i class="icon icon-bao pdr"></i>
								</c:when>
							</c:choose>
							<label style="color: #333;">${bTransfer.transferName}</label>
						</span>&nbsp;&nbsp;
						<a
							href="${path}/accountdetail/show.html?userName=${portal:encode(portal:encode(bTransfer.userNameEncrypt))}"
							target="_blank">
							<span class="bule">${bTransfer.userNameSecret}</span>
						</a>
					</div>
					<div class="ptb-list clearfix">
						<p><span>债权价格1111</span><label><strong class="f20 oren"><fmt:formatNumber
								value="${bTransfer.account}" pattern="#,##0.##"/></strong>元</label>
				   <span class="dj-box" onmousemove="$(this).find('i').show();" onmouseout="$(this).find('i').hide();">
						<a href="#"><img src="${path}/images/v5/ztc-more.png"></a>
						<i style="display:none;">此债权的剩余价值为<fmt:formatNumber value="${bTransfer.accountSurplus}"
																			pattern="#,##0.##"/>元<br>其中剩余本金<fmt:formatNumber
								value="${bTransfer.capital}" pattern="#,##0.##"/>元<br>待收利息<fmt:formatNumber
								value="${bTransfer.accountSurplus-bTransfer.capital}" pattern="#,##0.##"/>元 </i>
				   </span>
						</p>
						<p><span>转让价格</span><label><strong class="f20 oren"><fmt:formatNumber
								value="${bTransfer.accountReal}" pattern="#,##0.##"/></strong>元</label>
					<span class="dj-box" onmousemove="$(this).find('i').show();" onmouseout="$(this).find('i').hide();">
						<a href="#"><img src="${path}/images/v5/ztc-more.png"></a>
						<i style="display:none;">转让价格=债权价格&times;转让系数</i>
					</span>
						</p>
						<p><span>转让系数</span><label><strong class="f20 oren">${bTransfer.coef}</strong></label></p>
					</div>
					<div class="ptb-minal clearfix">
						<div class="ptb-box item-bd-b">
							<p>
								<em>现利率</em>
								<em><fmt:formatNumber value="${bTransfer.currApr}" pattern="#,##0.##"/>%</em>
							</p>
							<p>
								<em>最小认购额度 </em>
								<em><fmt:formatNumber value="${bTransfer.lowestAccount}" pattern="#,##0.##"/>元</em>
							</p>
							<p>
								<em>剩余期限</em>
								<em>${bTransfer.timeLimitReal}
									<c:if test="${bTransfer.borrowStyle==1 or bTransfer.borrowStyle==2}">个月</c:if>
									<c:if test="${bTransfer.borrowStyle==3 or bTransfer.borrowStyle==4}">天</c:if></em>
							</p>
						</div>
						<div class="ptb-box clearfix">
							<p>
								<em>下一还款日</em>
								<em>
									<c:if test="${bTransfer.nextRepaymentDate == null   }">还款结束</c:if>
									<c:if test="${bTransfer.nextRepaymentDate != null   }"> <fmt:formatDate
											value="${bTransfer.nextRepaymentDate}" pattern="yyyy-MM-dd"/></c:if>
								</em>
							</p>
							<p>
								<em>发布日期</em>
								<em><fmt:formatDate value="${bTransfer.addTime}" pattern="yyyy-MM-dd"/></em>
							</p>
							<c:if test="${bTransfer.status==2}">
								<p>
									<em>剩余时间</em>
									<em id="transfer_remainingTime"></em>
								</p>
							</c:if>
						</div>
					</div>
				</div>

				<div class="detail-col-r fr">

					<c:if test="${bTransfer.status==2}">
						<div class="detail-join-m">
							<div class="clearfix pdt10">
								<span class="tit f14">剩余可投</span>
								<strong class="oren f18">
									<fmt:formatNumber value="${bTransfer.accountReal-bTransfer.accountYes}" pattern="#,##0.##"/>
								</strong>元
							</div>
							<div class="clearfix pdt10">
								<c:if test="${loginMember!=null}">
									<c:if test="${isCustody==0}">
										<div class="clearfix pdt10">
											<a href="${path}/AccountSafetyCentre/safetyIndex.html" class="fr bd">立即开通</a>
											<span class="tit f14">账户余额</span>您还没有开通存管账户
										</div>
									</c:if>
									<c:if test="${isCustody==1}">
										<div class="clearfix pdt10">
											<a target="_blank" href="${basePath}/account/topup/toTopupIndex.html" class="fr bd">充值</a>
											<span class="tit f14">账户余额</span><span class="f14 gary2"><fmt:formatNumber value="${useMoney }" pattern="#,##0.##" />元</span>
										</div>
									</c:if>
								</c:if>
								<c:if test="${loginMember==null}">
									<div class="clearfix pdt10">
										<span class="f14 gary2">账户余额<a href="${path}/member/toLoginPage.html?backUrl=1" class="pdla login">登录</a>后可见</span>
									</div>
								</c:if>
							</div>
							<div class="clearfix pdt10">
								<span class="tit f14">购买金额</span>
								<input name="tenderMoney"
									   id="pay_money"
									   class="form-inpyt-sm yuan2"
									   style="width:120px" type="text" placeholder="请输入购买金额">
							</div>
							<c:if test="${loginMember!=null && isCustody==1 && isCustodyMember==1 }">
								<div class="clearfix pdt10">
									<span class="tit f14">手机号码</span>
									<span>${mobile}</span>
								</div>
								<div class="clearfix pdt10">
									<span class="tit f14">短信验证码</span>
									<input id="mobileCode"
										   name="mobileCode"
										   class="form-inpyt-sm"
										   style="width:120px"
										   type="text" placeholder="请输入验证码">
									<a href="javascript:;" onclick="mobileCode();" id="sendbtn" style="padding:2px 0 0 5px;">获取验证码</a>
								</div>
							</c:if>
							<div class="clearfix pdt26">
								<div class="btn-box2">
									<button type="button" class="btn btn-join f18" id="btnTenderBorrow"
											onclick="goSubscribe(${bTransfer.id });">立即认购
									</button>
								</div>
							</div>
						</div>
					</c:if>

					<!-- 转让成功 -->
					<c:if test="${bTransfer.status==4}">
						<div class="detali-zrz detali-bg">
							<h5 class="f14">
								<p>加入金额<strong><fmt:formatNumber value="${bTransfer.accountYes}"
																 pattern="#,##0.##"/></strong>元</p>
								<p><span class="f32 oren">${bTransfer.tenderTimes}</span><br/>加入人次 </p>
								<p><span class="f32 oren"><fmt:formatDate value="${bTransfer.successTime}"
																		  pattern="yyyy-MM-dd"/></span><br/>满额时间</p>
							</h5>
						</div>
					</c:if>
					<c:if test="${bTransfer.status==5}">
						<div class="detali-zrz detali-bg">
							<h5 class="f14">
								<p><span class="f24 oren">已流标</span><br/>标的状态</p>
								<p><span class="f32 oren"><fmt:formatDate value="${bTransfer.cancelTime}"
																		  pattern="yyyy-MM-dd"/></span><br/>流标时间</p>
							</h5>
							<i class="gc-img-wap ylb"></i>
						</div>
					</c:if>

					<c:if test="${bTransfer.status==6}">
						<div class="detali-zrz detali-bg">
							<h5 class="f14">
								<p><span class="f24 oren">已撤标</span><br/>标的状态</p>
								<p><span class="f32 oren"><fmt:formatDate value="${bTransfer.cancelTime}"
																		  pattern="yyyy-MM-dd"/></span><br/>撤标时间</p>
							</h5>
							<i class="gc-img-wap ycb"></i>
						</div>
					</c:if>
				</div>
			</div>
		</div>
	</div>

	<div class="product-wrap"><!--信息star-->
		<div class="grid-1100">
			<div class="product-deatil clearfix ">
				<h1 class="f16">
					<small class="fr">【借款标编号】${borrow.contractNo}</small>
					原始标信息
				</h1>
				<div class="detail-col-l fl item-bd-r">
					<div class="f16 pdtt20"><span>
	          <c:choose>
				  <c:when test="${borrow.borrowtype == 1}">
					  <i class="icon icon-xin pdr"></i>
				  </c:when>
				  <c:when test="${borrow.borrowtype == 2}">
					  <i class="icon icon-di pdr"></i>
				  </c:when>
				  <c:when test="${borrow.borrowtype == 3}">
					  <i class="icon icon-jing pdr"></i>
				  </c:when>
				  <c:when test="${borrow.borrowtype == 4}">
					  <i class="icon icon-miao pdr"></i>
				  </c:when>
				  <c:when test="${borrow.borrowtype == 5}">
					  <i class="icon icon-bao pdr"></i>
				  </c:when>
			  </c:choose>
	          <a style="color: #333;" href="${path}/toubiao/${borrow.id}.html"
				 target="_blank"> ${fn:substring(borrow.name,0,25)}
              <c:if test="${fn:length(borrow.name)>25}">..</c:if> </a></span>
	          <span class="bule">
		          <a class="bule"
					 href="${path}/accountdetail/show.html?userName=${portal:encode(portal:encode(borrow.userNameEncrypt))}"
					 target="_blank">
		          <c:choose>
					  <c:when test="${borrow.borrowtype == 1 || borrow.borrowtype == 2 || borrow.borrowtype == 5}">
						  ${borrow.username }
					  </c:when>
					  <c:otherwise>
						  ${borrow.userNameSecret }
					  </c:otherwise>
				  </c:choose>
		          </a>
	          </span>
					</div>
					<ul class="item-bd-b">
						<li class="bdr percentbig"><span class="f14 gary">年化收益率</span><span
								class="oren precent line120"><fmt:formatNumber value="${borrow.apr}"
																			   pattern="#,##0.##"/><small
								class="f18 oren">%</small></span></li>
						<li class="percentbig line36 word">
							<p><span>借款期限</span><label>
								<c:if test="${borrow.borrowtype==4}"><strong>秒还</strong></c:if>
								<c:if test="${borrow.borrowtype!=4 && borrow.style !=4 }">
									<strong>${borrow.timeLimit }</strong>个月
								</c:if>
								<c:if test="${borrow.borrowtype!=4 && borrow.style ==4 }">
									<strong>${borrow.timeLimit }</strong>天
								</c:if>
							</label></p>
							<p><span>借款金额 </span><label><strong><fmt:formatNumber value="${borrow.account }"
																				  pattern="#,##0.##"/></strong>元</label>
							</p>
						</li>
					</ul>
					<ul class="detailword">
						<li style="padding-right:346px;"><h6>
							投标类型&nbsp;&nbsp;&nbsp;&nbsp;${portal:desc(300, borrow.borrowtype)}</h6></li>
						<li><h6>还款方式&nbsp;&nbsp;&nbsp;&nbsp;
							${portal:desc(400, borrow.style)}
							<c:if test="${borrow.style ==4 }">
								<font color="#F00000">（即到期一次性还本息）</font>
							</c:if>
						</h6></li>
					</ul>
				</div>
				<div class="detail-col-r fr">
					<!-- 标还款中 -->
					<c:if test="${borrow.status==4}">
						<div class="detali-zrz detali-bg">
							<h5 class="f14">
								<p>待还本息<strong><fmt:formatNumber value="${borrowDetailRepayVo.waitToPayMoney }"
																 pattern="#,##0.00"/></strong>元</p>
								<p><span class="f32 oren">${borrowDetailRepayVo.remainPeriods }</span>期<br/>剩余期数</p>
								<p><span class="f32 oren"><fmt:formatDate
										value="${borrowDetailRepayVo.repaymentTimeDate}"
										pattern="yyyy-MM-dd"/></span><br/>下一期还款日</p>
							</h5>
							<i class="gc-img-wap hkz"></i>
						</div>
					</c:if>

					<!-- 标还款中 -->
					<c:if test="${borrow.status==42}">
						<div class="detali-zrz detali-bg">
							<h5 class="f14">
								<p>垫付金额<strong><fmt:formatNumber value="${borrowDetailWebPayVo.advanceYesAccount }"
																 pattern="#,##0.00"/></strong>元</p>
								<p>第<span class="f32 oren">${borrowDetailWebPayVo.webPayOrder }</span>期<br/>垫付期数</p>
								<p><span class="f32 oren"><fmt:formatDate value="${borrowDetailWebPayVo.advanceTime}"
																		  pattern="yyyy-MM-dd"/></span><br/>垫付日期</p>
							</h5>
							<i class="gc-img-wap ydf"></i>
						</div>
					</c:if>

					<!-- 标已还款-->
					<c:if test="${borrow.status==5}">
						<div class="detali-zrz detali-bg">
							<h5 class="f14">
								<p><span class="f24 oren"><fmt:formatNumber value="${repaymentYesAccountTotal}"
																			pattern="#,##0.00"/></span>元<br/>已还本息</p>
								<p><span class="f32 oren">${borrow.tenderTimes }</span><br/>加入人次</p>
							</h5>
							<i class="gc-img-wap yhk"></i>
						</div>
					</c:if>
				</div>
			</div>
		</div>
	</div>


	<div class="product-wrap"><!--table-->
		<div class="grid-1100 background">
			<div class="prduct-menu">
				<div class="menu-tbl">
					<ul class="col4">
						<li class="active">原始标信息</li>
						<li onclick="showTab('toInvest_tenderRecord',this);searchTenderRecordList(1)">原投标记录</li>
						<li onclick="showTab('toInvest_repaymentPlan',this);searchRepaymentList(1)">还款计划</li>
						<li onclick="showTab('toInvest_SubscribeInfo',this);searchSubscribeList(1)">购买记录</li>
					</ul>
				</div>
				<div class="menucont" style="clear:both">
					<div class="tbl-cont">
						<%@ include file="/WEB-INF/page/investment/toInvest_borrowInfo.jsp" %>
					</div>

					<div class="tbl-cont" style="display:none">
						<div id="toInvest_tenderRecord"></div>
					</div>
					<div class="tbl-cont" style="display:none">
						<div id="toInvest_repaymentPlan"></div>
					</div>
					<div class="tbl-cont" style="display:none">
						<div id="toInvest_SubscribeInfo"></div>
					</div>
				</div>
			</div>
		</div>
	</div>


	<!--弹层star--->
	<div class="layer-jion" id="transfer-layer">
		<div class="cont-word clearfix">
			<div class="title"><h4>认购债权转让</h4><a href="#" class="icon icon-close fr"></a></div>
			<div class="form-info-layer">

				<div class="form-col3">
					<ul class="rengou">
						<li><span style="color:#333;"> 债权剩余价值：</span><strong>￥<fmt:formatNumber
								value="${bTransfer.accountSurplus }" pattern="#,##0.00"/></strong></li>
						<li><span style="color:#333;">剩余本金：</span><strong>￥<fmt:formatNumber
								value="${bTransfer.capital }" pattern="#,##0.00"/></strong> <span
								style="display:inline-block; width:25px;"></span><span
								style="color:#333;">待收利息：</span><strong><fmt:formatNumber
								value="${bTransfer.accountSurplus-bTransfer.capital}" pattern="#,##0.00"/></strong></li>
					</ul>
				</div>
				<div class="form-col3">
					<label for="" class="colleft form-lable">认购金额</label>
					<span class="fl money" id="jionAccount"></span>
				</div>

				<c:if test="${bTransfer.bidPassword != null && bTransfer.bidPassword != ''}">
					<div class="form-col2">
						<label for="" class="colleft form-lable">认购密码</label>
						<input id="password1" class="form-inpyt-sm1" style="width:120px;color:#999;" type="text"
							   value="请输入认购密码"/>
						<input type="password" id="bidPassword" name="bidPassword" class="form-inpyt-sm1"
							   style="display:none;width:120px;color:#999;"/> <a href="javascript:void(0);"
																				 class="fl pdlr10 line32"> </a>
					</div>
				</c:if>

				<div class="form-col2">
					<button type="button" class="remove" onClick="cancel_Transfer();">取消</button><button type="button" class="enter" id="btnSubscribeTransfer">确认</button>
				</div>
			</div>
		</div>
	</div>
	<!--弹层end--->
</form>
<div class="clearfix bompd60"></div>
<%@ include file="/WEB-INF/page/common/footer.jsp" %>
</body>
<style type="text/css">
	.form-inpyt-sm1 {
		float: left;
		padding-left: 6px;
		font-size: 14px;
		*line-height: 32px;
		line-height: 32px;
		height: 32px;
		color: #333;
		background-color: #fff;
		background-image: none;
		border: 1px solid #ccc;
		-webkit-box-shadow: inset 0 1px 1px rgba(0, 0, 0, .075);
		box-shadow: inset 0 1px 1px rgba(0, 0, 0, .075);
		-webkit-transition: border-color ease-in-out .15s, -webkit-box-shadow ease-in-out .15s;
		-o-transition: border-color ease-in-out .15s, box-shadow ease-in-out .15s;
		transition: border-color ease-in-out .15s, box-shadow ease-in-out .15s
	}

</style>


<script type="text/javascript">
	$(document).ready(function () {
		$(".form-inpyt-sm1").focus(function () {
			if ($(this).attr('id') == 'password1') {
				$(this).hide();
				$('#bidPassword').show();
				$('#bidPassword').focus();
				$("#bidPassword").attr("style", "color:#FFFFF;width:120px;");
			}
		});
		$(".form-inpyt-sm1").blur(function () {
			if ($(this).attr('id') == 'bidPassword' && $(this).val() == '') {
				$(this).hide();
				$('#password1').show();
				$('#password1').val('请输入认购密码');
				$("#password1").attr("style", "color:#999;width:120px;");
			}
		});
	});
	/**秒数*/
	var SysSecond;
	/**循环计算倒计时*/
	var InterValObj;

	$(function () {
		var borrowStatus = '${bTransfer.status}';
		var nowTime = $("#nowTime").val();
		/**预发标,发标剩余时间*/
		if (borrowStatus == "2") {
			var validTime = '${bTransfer.validTime}';
			var borrow_addtime_hide = $("#transfer_addtime_hide").val();
			var validMinute = '${bTransfer.validMinute}';
			var bidPassword = '${bTransfer.bidPassword}';
			//这里获取倒计时的起始时间
			if (bidPassword != null && bidPassword != "") {
				SysSecond = parseFloat(new Date(borrow_addtime_hide).getTime() / 1000 + 60 * parseFloat(validMinute)) - parseFloat(new Date(nowTime).getTime() / 1000);
			} else {
				SysSecond = parseFloat(new Date(borrow_addtime_hide).getTime() / 1000 + 3600 * 24 * parseFloat(validTime)) - parseFloat(new Date(nowTime).getTime() / 1000);
			}
			//剩余时间
			InterValObj = window.setInterval(SetRemainTime, 1000); //间隔函数，1秒执行
		}
	});
	/**
	 * 倒计时
	 */
	function SetRemainTime() {
		if (SysSecond > 0) {
			SysSecond = SysSecond - 1;
			var second = Math.floor(SysSecond % 60);             // 计算秒
			var minute = Math.floor((SysSecond / 60) % 60);      //计算分
			var hour = Math.floor((SysSecond / 3600) % 24);      //计算小时
			var day = Math.floor((SysSecond / 3600) / 24);        //计算天
			$("#transfer_remainingTime").html('<span class="oren">' + day + '</span>天<span class="oren">' + hour + '</span>时<span class="oren">' + minute + '</span>分<span class="oren">' + second + '</span>秒');
		} else {//剩余时间小于或等于0的时候，就停止间隔函数
			window.clearInterval(InterValObj);
			//这里可以添加倒计时时间为0后需要执行的事件
			$("#transfer_remainingTime").html("<span class='oren'>0</span>天<span class='oren'>0</span>时<span class='oren'>0</span>分<span class='oren'>0</span>秒");
		}
	}

	/**
	 * 切换样式
	 */
	function showTab(id, obj) {
		$(".tab").css("display", "none");
		$("#" + id).css("display", "block");

		$(".tblist_title ul li").each(function () {
			$(this).removeClass("selected");
		});

		$(obj).parent().addClass("selected");
	}
	/**
	 * 显示投标记录
	 */
	function searchTenderRecordList(pageNum) {
		$.ajax({
			url: '${basePath}/tender/searchTenderRecordList/${borrow.id}/' + pageNum + '.html',
			type: 'post',
			dataType: 'text',
			success: function (data) {
				$("#toInvest_tenderRecord").html(data);
			},
			error: function (data) {
				layer.msg('网络连接异常，请刷新页面或稍后重试！', 1, 5);
			}
		});
	}
	/**
	 * 显示还款计划
	 */
	function searchRepaymentList(pageNum) {
		$.ajax({
			url: '${basePath}/repayment/searchRepaymentList/${borrow.id}/' + pageNum + '.html',
			type: 'post',
			dataType: 'text',
			success: function (data) {
				$("#toInvest_repaymentPlan").html(data);

			},
			error: function (data) {
				layer.msg('网络连接异常，请刷新页面或稍后重试！', 1, 5);
			}
		});
	}


	/**
	 * 显示购买记录
	 */
	function searchSubscribeList(pageNum) {
		$.ajax({
			url: '${basePath}/zhaiquan/searchSubscribes/${bTransfer.id}/' + pageNum + '.html',
			type: 'post',
			dataType: 'text',
			success: function (data) {
				$("#toInvest_repaymentPlan").html('');
				$("#toInvest_SubscribeInfo").html(data);
			},
			error: function (data) {
				layer.msg('网络连接异常，请刷新页面或稍后重试！', 1, 5);
			}
		});
	}

	/**
	 * 认购方法
	 */
	function goSubscribe(tid) {
		if (${null==portal:currentUser()}) {
			layer.msg("请先登录", 1, 5, function () {
				window.location.href = "${path}/member/toLoginPage.html?backUrl=1";
			});
			return;
		}
		if (${portal:hasRole("borrow")}) {
			layer.msg("您是借款用户,不能进行此操作", 1, 5);
			return;
		}
		$.ajax({
			url: '${basePath}/tender/judgTender.html',
			data: {type: 3},
			type: 'post',
			dataType: 'json',
			success: function (data) {
				if ("0" == data.code || "-1" == data.code || "-2" == data.code || "-3" == data.code || "-4" == data.code || "-5" == data.code) {
					layer.msg(data.message, 1, 5, function () {
						if ("-2" == data.code) {
							window.location.href = "${path}/AccountSafetyCentre/safetyIndex.html";
						} else if ("-1" == data.code) {
							window.location.href = "${path}/AccountSafetyCentre/safetyIndex.html";
						} else if ("-4" == data.code) {
							window.location.href = "${path}/AccountSafetyCentre/safetyIndex.html";
						} else if ("-5" == data.code) {
							window.location.href = "${path}/AccountSafetyCentre/safetyIndex.html";
						}
					});
					return;
				}
				else if ("-99" == data.code) {
					return;
				}
				else {
					goSubscribeValidateData();
				}

			},
			error: function (data) {
				layer.msg("网络连接异常，请刷新页面或稍后重试！", 2, 5);
			}
		});
	}

	function goSubscribeValidateData() {

		var msg = "";
		var pay_money = Number($("#pay_money").val());
		var alsoNeed = Number("${alsoNeed}");
		var useMoney = Number($("#useMoney").val());
		var lowestAccount = Number("${bTransfer.lowestAccount}");
		var mostAccount = Number("${bTransfer.mostAccount}");
		var sumAccount = Number("${sumAccount}");
		var isCustodyMember =$("#isCustodyMember").val();
		var reg = /^\d+[\.]?\d{0,2}$/g;
		var zfdsReg = /^(([1-9]\d{0,9})|0)(\.\d{1,2})?$/;
		;//金额的正则表达式
		var numOrAbcReg = /^[A-Za-z0-9]+$/;

		if (isCustodyMember == null || isCustodyMember != 1) {
			msg = msg + "还未开通存管账户，不能进行存管债转认购操作！<br/>";
		}

		if (pay_money == null || pay_money == "") {
			msg = msg + "-购买金额未填写！<br/>";
		}
		if (pay_money == null || pay_money == "") {
			msg = msg + "-购买金额未填写！<br/>";
		} else if (!zfdsReg.test(pay_money)) {
			msg = msg + "-输入金额有误！<br/>";
		} else if (pay_money == 0) {
			msg = msg + "-购买金额不能为0！<br/>";
		} else if (!reg.test(pay_money)) {
			msg = msg + "-只能保留2位小数！<br/>";
		}
		if (alsoNeed != pay_money) {
			msg = msg + "-认购存管债转时，购买金额必须等于债转金额！<br/>";
		}
		if (pay_money > mostAccount) {
			msg = msg + "-购买金额不能大于最大认购额度！<br/>";
		}

		if (Number((pay_money + sumAccount).toFixed(2)) > mostAccount) {
			msg = msg + "-累计购买金额不能大于最大认购额度！<br/>";
		}

		if (alsoNeed != 0 && pay_money > alsoNeed) {
			msg = msg + "-剩余可认购金额为" + alsoNeed + "！<br/>";
		}

		if (useMoney < lowestAccount && useMoney < alsoNeed) {
			msg = msg + "-您的余额不足！<br/>";
		}
		if (useMoney < pay_money) {
			msg = msg + "-账户余额小于购买金额！<br/>";
		}

		var mobileCode=$("#mobileCode").val();
		if(mobileCode.length<=0){
			layer.msg("请输入短信验证码", 1, 5);
			return;
		}

		if (msg != "") {
			layer.msg(msg, 2, 5);
			return false;
		}
		$("#transfer-layer").show();
		$("#jionAccount").html('<strong class="oren f14">' + formatMoney(pay_money) + '</strong><font color="#000">元</font>');
		$("#btnSubscribeTransfer").bind('click', subscribe_transfer);
		return true;
	}


	function cancel_Transfer() {
		$("#transfer-layer").hide();
		/* $("#payPassword").val('');
		 $("#checkCode").val(''); */
		$("#bidPassword").val('');
	}


	/**
	 * 认购
	 */
	function subscribe_transfer() {
		if (!validateData()) {
			return;
		}

		var mobileCode=$("#mobileCode").val();
		if(mobileCode.length<=0){
			layer.msg("请输入短信验证码", 2, 5);
			return false;
		}

		if (layer.confirm("确定要认购吗？", function () {
					$("#btnSubscribeTransfer").unbind('click');
					$("#subscribeTransferForm").ajaxSubmit({
						url: '${basePath}/custody/zhaiquan/subscribeTransfer.html',
						type: "POST",
						success: function (data) {
							$("#btnSubscribeTransfer").bind('click', subscribe_transfer);
							if (data.code == "1") {
								layer.msg("认购成功！", 1, 1);
								window.parent.location.href = "${path}/custody/zhaiquan/${bTransfer.id}.html";
							} else {
								if (data.message != '') {
									layer.msg(data.message, 2, 5);
								}
							}
						},
						error: function () {
							$("#btnSubscribeTransfer").bind('click', subscribe_transfer);
							layer.msg("网络连接超时，请您稍后重试", 2, 5);
						}
					});
				}));
	}

	/**
	 * 验证认购数据  useMoney
	 */
	function validateData() {
		var msg = "";
		var pay_money = Number($("#pay_money").val());
		var alsoNeed = Number("${alsoNeed}");
		var useMoney = Number($("#useMoney").val());
		var lowestAccount = Number("${bTransfer.lowestAccount}");
		var mostAccount = Number("${bTransfer.mostAccount}");
		var sumAccount = Number("${sumAccount}");
		var reg = /^\d+[\.]?\d{0,2}$/g;
		var zfdsReg = /^(([1-9]\d{0,9})|0)(\.\d{1,2})?$/;//金额的正则表达式
		var numOrAbcReg = /^[A-Za-z0-9]+$/;
		var isCustodyMember =$("#isCustodyMember").val();
		if (isCustodyMember == null || isCustodyMember != 1) {
			msg = msg + "还未开通存管账户，不能进行存管债转认购操作！<br/>";
		}
		if (alsoNeed != pay_money) {
			msg = msg + "-认购存管债转时，购买金额必须等于债转金额！<br/>";
		}

		if (pay_money == null || pay_money == "") {
			msg = msg + "-购买金额未填写！<br/>";
		} else if (!zfdsReg.test(pay_money)) {
			msg = msg + "-输入金额有误！<br/>";
		} else if (pay_money == 0) {
			msg = msg + "-购买金额不能为0！<br/>";
		} else if (!reg.test(pay_money)) {
			msg = msg + "-只能保留2位小数！<br/>";
		} else {
			if (pay_money < lowestAccount) {
				if (alsoNeed != pay_money) {
					msg = msg + "-认购存管债转时，购买金额必须等于债转金额！<br/>";
				}
			}
		}
		if (pay_money > mostAccount) {
			msg = msg + "-购买金额不能大于最大认购额度！<br/>";
		}

		if (Number((pay_money + sumAccount).toFixed(2)) > mostAccount) {
			msg = msg + "-累计购买金额不能大于最大认购额度！<br/>";
		}

		if (alsoNeed != 0 && pay_money > alsoNeed) {
			msg = msg + "-剩余可认购金额为" + alsoNeed + "！<br/>";
		}

		if (useMoney < lowestAccount && useMoney < alsoNeed) {
			msg = msg + "-您的余额不足！<br/>";
		}
		if (useMoney < pay_money) {
			msg = msg + "-账户余额小于购买金额！<br/>";
		}

		if ('${!empty bTransfer.bidPassword}' == 'true') {
			var bidPassword = $("#bidPassword").val();
			if (bidPassword == null || bidPassword == "") {
				msg = msg + "-认购密码未填写！<br/>";
			}
			if (!numOrAbcReg.test(bidPassword)) {
				msg = msg + "-认购密码只能为数字或字母！<br/>";
			}
		}

		if (msg != "") {
			layer.msg(msg, 2, 5);
			return false;
		}
		return true;
	}

	//获取短信验证码
	function mobileCode() {

		if(!validateBeforeMobile()){
			return;
		}
		$("#subscribeTransferForm").ajaxSubmit({
			url: "${basePath}/custody/zhaiquan/sendMobileCode.html",
			type: "post",
			dataType: "json",
			success: function (data) {
				if (data.code == "0") {
					layer.msg(data.message, 2, 5);
				} else if (data.code == "1") {
					layer.alert(data.message, 1);
					run();
				}
			},
			error: function (data) {
				layer.msg("网络连接异常，请刷新页面或稍后重试！", 2, 5);
			}

		});

	}

	/**
	 * 刷新过期时间
	 */
	var runtime = 180;
	function run() {
		runtime = 180;
		runclock();
	}
	function runclock() {
		$('#sendbtn').removeAttr("onclick");
		setTimeout(function () {
			runtime = runtime - 1;
			if (runtime > 0) {
				$('#sendbtn').html(runtime + "秒后获取");
				runclock();
			} else {
				runtime = 180;
				$('#sendbtn').html('获取验证码');
				$('#sendbtn').attr("onclick", "mobileCode()");
			}
		}, 1000);
	}

	function validateBeforeMobile() {
		if (${null==portal:currentUser()}) {
			layer.msg("请先登录", 1, 5, function () {
				window.location.href = "${path}/member/toLoginPage.html?backUrl=1";
			});
			return;
		}
		if (${portal:hasRole("borrow")}) {
			layer.msg("您是借款用户,不能进行此操作", 1, 5);
			return;
		}


		var msg = "";
		var pay_money = Number($("#pay_money").val());
		var alsoNeed = Number("${alsoNeed}");
		var useMoney = Number($("#useMoney").val());
		var lowestAccount = Number("${bTransfer.lowestAccount}");
		var mostAccount = Number("${bTransfer.mostAccount}");
		var sumAccount = Number("${sumAccount}");
		var reg = /^\d+[\.]?\d{0,2}$/g;
		var zfdsReg = /^(([1-9]\d{0,9})|0)(\.\d{1,2})?$/;//金额的正则表达式
		var numOrAbcReg = /^[A-Za-z0-9]+$/;
		var isCustodyMember =$("#isCustodyMember").val();
		if (isCustodyMember == null || isCustodyMember != 1) {
			msg = msg + "还未开通存管账户，不能进行存管债转认购操作！<br/>";
		}
		if (alsoNeed != pay_money) {
			msg = msg + "-认购存管债转时，购买金额必须等于债转金额！<br/>";
		}

		if (pay_money == null || pay_money == "") {
			msg = msg + "-购买金额未填写！<br/>";
		} else if (!zfdsReg.test(pay_money)) {
			msg = msg + "-输入金额有误！<br/>";
		} else if (pay_money == 0) {
			msg = msg + "-购买金额不能为0！<br/>";
		} else if (!reg.test(pay_money)) {
			msg = msg + "-只能保留2位小数！<br/>";
		} else {
			if (pay_money < lowestAccount) {
				if (alsoNeed != pay_money) {
					msg = msg + "-认购存管债转时，购买金额必须等于债转金额！<br/>";
				}
			}
		}
		if (pay_money > mostAccount) {
			msg = msg + "-购买金额不能大于最大认购额度！<br/>";
		}

		if (Number((pay_money + sumAccount).toFixed(2)) > mostAccount) {
			msg = msg + "-累计购买金额不能大于最大认购额度！<br/>";
		}

		if (alsoNeed != 0 && pay_money > alsoNeed) {
			msg = msg + "-剩余可认购金额为" + alsoNeed + "！<br/>";
		}

		if (useMoney < lowestAccount && useMoney < alsoNeed) {
			msg = msg + "-您的余额不足！<br/>";
		}
		if (useMoney < pay_money) {
			msg = msg + "-账户余额小于购买金额！<br/>";
		}

		if (msg != "") {
			layer.msg(msg, 2, 5);
			return false;
		}
		return true;

	}

</script>


