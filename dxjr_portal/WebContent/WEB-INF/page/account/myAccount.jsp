<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/page/common/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ include file="/WEB-INF/page/common/public4.jsp"%>
<script src="${basePath }/js/myaccount/highcharts.js?version=<%=version%>"></script>
<title>顶玺金融</title>
<style type="text/css">

</style>
<script type="text/javascript">
$(function() {
	showMoneyChart();
	$("#zhzl").attr("class","active"); //添加样式 
	$(".divclose").click(function(){
	   $(".auto-div").fadeOut();
    });
	if('${isAutoInvest}'==0){
		$("#auto-div1").show();
	}else{
		$("#auto-div1").hide();
	}
});
function showMoneyChart(fixreax,tenderTotal,currTotal,useTotal){
			  $('#container').highcharts({
					chart : {
						plotBackgroundColor : null,
						plotBorderWidth : null,
						plotShadow : false,
						type : 'pie'
					},
					title : {
						enabled : false,
						text : ''
					},
					tooltip : {
						enabled : true,
						pointFormat : '{series.name}: <b>{point.percentage:.2f}%</b>'
					},
					plotOptions : {
						pie : {
							allowPointSelect : true,
							cursor : 'pointer',
							dataLabels : {
								enabled : false,
								format : '<b>{point.name}</b>: {point.percentage:.1f} %',
								style : {
									color : (Highcharts.theme && Highcharts.theme.contrastTextColor)
											|| 'black'
								}
							}
						}
					},
					series : [ {
						name : '占比',
						innerSize : '45%',
						data : [ {
							name : '定期宝',
							y : Number('${fixreax}'),
						}, {
							name : '活期宝',
							y : Number('${currTotal}'),
						}, {
							name : '投标总额',
							y : Number('${tenderTotal}'),
						}, {
							name : '可用余额',
							y : Number('${useTotal}')
						}, ]
					} ]
				});
}
</script>
</head>
<body>
	<!--头部开始-->
	<%@ include file="/WEB-INF/page/common/header.jsp"%>
	<div class="clear"></div>
	<!--头部结束-->

	<!--main-->
	<div id="myaccount">
		<%@ include file="/WEB-INF/page/common/topmenu.jsp"%>
		<div class="wraper">
			<div class="tz-detail mt20">
			   <%-- 	<c:if test="${isCustody != 1}">
				    <a href="${basePath }/AccountSafetyCentre/safetyIndex.html"><img src="${basePath}/images/myaccount/zs-banner.png" /></a>
			     </c:if> --%>
			</div>
			<!--one-->
			<div id="account-all">
				<div class="userbar">
					<ul class="ul-userbar1">
						<li class="userpic">
						  <a id="tip-userpic" href="javascript:toAddHeadImg();">
							<c:if test="${empty memberVo.headImg}">
								<img src="${basePath}/images/main/head.png"  title="编辑头像" alt="headimg" />
							</c:if>
							<c:if test="${not empty memberVo.headImg}">
								<img src="${basePath}${memberVo.headImg}"  title="编辑头像" alt="headimg" />
							</c:if>
						  </a>
						</li>
					</ul>
					<ul class="ul-userbar2">
						<li class="userinfo">
							<p>
								<a id="tip-top" title="${userLevelName}，我的荣誉值：<fmt:formatNumber value="${honor }" pattern="###,###" />" class="f18" href="${path}/accountdetail/show.html?userName=${portal:encode(portal:encode(memberVo.userNameEncrypt))}" target="_blank">
								<span class="gray3">${memberVo.userName}</span>
							    <c:if test="${userLevel == 0 }">
								    <i class="iconfont f24 uservip1"></i>
								</c:if>
								 <c:if test="${userLevel == 10 }">
								    <i class="iconfont f24 uservip2"></i>
								</c:if>
								 <c:if test="${userLevel == 20 }">
								    <i class="iconfont f24 uservip3"></i>
								</c:if>
								 <c:if test="${userLevel == 30 }">
								    <i class="iconfont f24 uservip4"></i>
								</c:if>
								 <c:if test="${userLevel == 40 }">
								    <i class="iconfont f24 uservip5"></i>
								</c:if>
								 <c:if test="${userLevel == 50 }">
								    <i class="iconfont f24 uservip6"></i>
								</c:if>
								 <c:if test="${userLevel == 60 }">
								    <i class="iconfont f24 uservip7"></i>
								</c:if>
								</a>
							</p>
							<p class="f14">
								<a href="${basePath }/lottery_use_record/lott_use_record.html?tabType=1">我的元宝<span class="oren mal20">
								         <fmt:formatNumber value="${sycee }" pattern="###,###" />
								</a></span> 
								<span
									class="blue mal20"><a href="${basePath }/sycee.html" target="_blank">兑换</a></span>
								<a href="${basePath }/lottery_use_record/lott_use_record.html?tabType=2"  id="tip-bottom" title="红包${redCount }个"><i
									class="iconfont red">&#xe600;</i></a> <a href="${basePath }/lottery_chance/info.html"  id="tip-bottom2"
									title="抽奖机会${chanceUseNum }次"><i class="iconfont red">&#xe601;</i></a>
							</p>
							<p><a  href="${basePath }/AccountSafetyCentre/safetyIndex.html">
							    <c:if test="${safeMap.mobileAppro.getPassed() == 1}">
								  <em class="iconfont blue f32 mr10 ico-top" title="已绑定手机"></em>
								</c:if>
								<c:if test="${safeMap.mobileAppro.getPassed() != 1}">
								  <em class="iconfont gray f32 mr10 ico-top" title="未绑定手机"></em>
								</c:if>
								<c:if test="${safeMap.realNameApproVo.getIsPassed() == 1}">
							     	<em class="iconfont blue f32 mr10 ico-top" title="已实名验证"></em>
							     </c:if>
							     <c:if test="${safeMap.realNameApproVo.getIsPassed()!= 1}">
							     	<em class="iconfont gray f32 mr10 ico-top" title="未实名验证"></em>
							     </c:if>
							    <c:if test="${currentBankCardVo != null and currentBankCardVo.status != -1 }">
									<em class="iconfont blue f32 mr10 ico-top" title="已绑定银行卡"></em>
								</c:if>
							    <c:if test="${currentBankCardVo == null or currentBankCardVo.status == -1 }">
									<em class="iconfont gray f32 mr10 ico-top" title="未绑定银行卡"></em>
								</c:if>
								<c:if test="${safeMap.emailApproVo.getChecked() == 1}">
									<em class="iconfont blue f32 mr10 ico-top" title="已绑定邮箱"></em>
								</c:if>
							  	<c:if test="${safeMap.emailApproVo.getChecked() != 1}">
									<em class="iconfont gray f32 mr10 ico-top" title="未绑定邮箱"></em>
								</c:if>
								<c:if test="${isCustody == 1}">
									<em class="iconfont blue f32 mr10 ico-top" title="已开通存管账户">&#xe627;</em>
								</c:if>
								<c:if test="${isCustody != 1}">
									<em class="iconfont gray f32 mr10 ico-top" title="未开通存管账户">&#xe627;</em>
								</c:if>
								</a>
							</p>
							<c:if test="${currentBankCardVo == null or currentBankCardVo.status == -1 }">
								<div class="bankcard-tip">
									<span>亲，绑定银行卡，才可以投资哦！现在就去</span><a
										href="${basePath }/AccountSafetyCentre/safetyIndex.html">绑定银行卡吧！</a><em></em><i
										class="iconfont blue close mal20 f24">&#xe606;</i>
								</div>
						   </c:if>
                      </li>
					</ul>
					<ul class="ul-userbar3">
						<div class="menu-tbl">
			        <ul class="col2">
								<li class="active" onclick="rechangeTip('linkRecharge');">普通</li>
								<li onclick="rechangeTip('linkCustody');">存管</li>
							</ul>
						</div>
                            <div class="menucont" style="clear: both">
							<div class="tbl-cont">
				                <p>
									<span>可用余额：</span><span class="orange"><fmt:formatNumber value="${accountInfo.pUseMoneyTotal}" pattern="#,##0.00" />元</span>
								</p>
								<p>
									<span>冻结金额：</span><span class="orange"><fmt:formatNumber value="${accountInfo.pFreezeTotal}" pattern="#,##0.00" />元</span>
								</p>
								<p class="center">
									<a href="${basePath }/accOpertingRecord/paymentDetails/1.html">资金明细</a>
									&nbsp;&nbsp;
									<a href="${path }/myBill/accElectronbill.html">电子账单</a>
								</p>
								 
							</div>
					   	<c:if test="${isCustody == 1}">
                        	 <div class="tbl-cont" style="display: none">
								<p>
									<span>可用余额：</span><span class="orange"><fmt:formatNumber value="${accountInfo.eUseMoneyTotal}" pattern="#,##0.00" />元</span>
								</p>
								<p>
									<span>冻结金额：</span><span class="orange"><fmt:formatNumber value="${accountInfo.eFreezeTotal}" pattern="#,##0.00" />元</span>
								</p>
								<p class="center">
									<a href="${basePath }/accOpertingRecord/paymentDetails/1.html">资金明细</a>
										&nbsp;&nbsp;
									<a href="${path }/myBill/accElectronbill.html">电子账单</a>
								</p>
							</div> 
							</c:if>
							<c:if test="${isCustody == 0}">
							    <div class="tbl-cont" style=" display:none">
	                               <p class="mt20"><span>您还未开通存管账户!</span><a href="${path }/AccountSafetyCentre/safetyIndex.html">立即开通</a></p>
	                            </div>
	                         </c:if>
                       	</div>
						</ul>
							<ul class="ul-userbar4">
							<c:if test="${isBlack!='false'}">
							<span>
								<!--<a href="javascript:chengeUserToStock();"><i class="iconfont f32 orange">&#xe61e;</i><br>内转窗口</a> -->
								<a href="http://testlz.xhgqtz.com/" target="_blank"><i class="iconfont f32 orange">&#xe61e;</i><br>内转窗口</a>
							</span>
							</c:if>
							
							<c:if test="${stockNum > 0}"> 
								<span><a href="${basePath }/stock/myStock.html"><i class="iconfont f32 red">&#xe61d;</i><br>我的期权</a></span> 
								</c:if>

						</ul>
				</div>
				<!--two-->
				<div class="moneybar">
					<ul class="mtit">
						<li class="first">
							<p class="f14 blue">账户总额</p>
							<p>
								<span class="f24 orange"><fmt:formatNumber   
												value="${accountInfo.accountTotal}"
												pattern="#,##0.00" /></span><span
									class="f14 orange">元</span>
							</p>
						</li>
						<li class="two">
							<p class="f14">净收益</p>
							<p>
								<span class="f24"><fmt:formatNumber value="${accountInfo.netEaring}" pattern="#,##0.00" /></span><span class="f14">元</span>
							</p>
						</li>
						<li class="two">
							<p class="f14">待收总额</p>
							<p id="tip-daishou"
								title="待收本金：<fmt:formatNumber value="${accountInfo.collectionCapitalTotal}" pattern="#,##0.00" />元<br>待收利息：<fmt:formatNumber value="${accountInfo.collectionInterstTotal}" pattern="#,##0.00" />元">
								<span class="f24"><fmt:formatNumber value="${accountInfo.collectionTotal}" pattern="#,##0.00" /></span><span class="f14">元</span>
							</p>
						</li>
						<li class="three"><a href="javascript:topupFunction();"
							class="btn btn-orange">充值</a>
							<a href="javascript:${portal:currentUser().isFinancialUser==0?'layer.alert(\'无法操作提现\');':'withdrawFunction();'}"
							class="btn btn-primary">提现</a></li>
					</ul>
					<div class="centermain">
						<div class="pie fl">
						<c:if test="${accountInfo.accountTotal!='0.00'}">
						 <div id="container" style="height: 200px; width: 200px;"></div>
						</c:if>
						<c:if test="${accountInfo.accountTotal=='0.00'}">
					          <div style="height: 200px; width: 200px;line-height:200px;text-align:center"><img src="${basePath}/images/myaccount/00.png" alt=""/></div>
						</c:if>
						</div>
						 <ul class="mainright fl">
							<li class="orange"><span><i class="iconfont">&#xe607;</i></span><span
								class="w1">定期宝</span><span class="w2"><c:if test="${fixreax==0}">0.00元</c:if><c:if test="${fixreax!=0}">${fixreax}%</c:if></span><span class="w3"><fmt:formatNumber
												value="${accountInfo.fixTotal}" pattern="#,##0.00" />元</span></li>
							<li class="green"><span><i class="iconfont">&#xe607;</i></span><span
								class="w1">投标总额</span><span class="w2"><c:if test="${tenderTotal==0}">0.00元</c:if><c:if test="${tenderTotal!=0}">${tenderTotal}%</c:if></span><span
								class="w3"><fmt:formatNumber value="${accountInfo.tenderTotal}" pattern="#,##0.00" />元</span></li>
							<li class="blue"><span><i class="iconfont">&#xe607;</i></span><span
								class="w1">活期宝</span><span class="w2"><c:if test="${currTotal==0}">0.00元</c:if><c:if test="${currTotal!=0}">${currTotal}%</c:if></span><span class="w3"><fmt:formatNumber value="${accountInfo.curTotal}" pattern="#,##0.00" />元</span></li>
							<li class="orange2"><span><i class="iconfont">&#xe607;</i></span><span
								class="w1" id="tip-dongjie" title="含冻结金额：<fmt:formatNumber value="${accountInfo.freezeTotal}" pattern="#,##0.00" />元">可用余额</span><span
								class="w2"><c:if test="${useTotal==0}">0.00元</c:if><c:if test="${useTotal!=0}">${useTotal}%</c:if></span><span class="w3"><fmt:formatNumber value="${accountInfo.useMoneyTotal}" pattern="#,##0.00" />元</span></li>
						</ul>
					</div>
					<div class="bottombar clearfix">
						<ul>
							<li class="one"><span class="tupiao"><img
									src="${basePath}/images/myaccount/acc-autotb.png" alt="" /></span>
							<h4>
									<span>自动投资 快捷的投资方式</span><a href="${basePath }/myaccount/autoInvest/autoInvestMain.html">设置自动投资</a>
								</h4></li>
							<li><span class="tupiao"><img
									src="${basePath}/images/myaccount/acc-tuijian.png" alt="" /></span>
							<h4>
									<span>推荐好友 收获意想不到的惊喜</span><a href="${basePath }/activity/invited.html">顺手捞一笔</a>
								</h4></li>

						</ul>
					</div>

				</div>
				<!--three-->
				 <div class="aboluo-w">
				   <%@ include file="/WEB-INF/page/account/myCollection.jsp"%>
</div> 
</div>
</div>
</div>
	<div class="clearfix"></div> <jsp:useBean id="now" class="java.util.Date" />
	<fmt:formatDate value="${now}" type="both" dateStyle="long" pattern="yyyy-MM-dd" var="nowDate" />
	<!--尾部 s   -->
	<%@ include file="/WEB-INF/page/common/footer.jsp"%>
	<!--尾部 e -->
	<div id="auto-div1" style="display: none;">
		<div class="auto-div">
			<div class="picbg">
		    	    <a href="${basePath }/myaccount/autoInvest/autoInvestMain.html" class="openbtn"></a>
	                <a class="divclose"><i class="iconfont">&#xe62a;</i></a>
		    </div>
		</div>
  </div>
</body>
<script type="text/javascript">
	var tip = "linkRecharge";
	function rechangeTip(tipName){
		tip = tipName;
	}
	/**
	 * 进入充值页面
	 */
	function topupFunction() {
		window.location.href = "${path}/account/topup/toTopupIndex.html?tip="+tip;
	}

	/**
	 * 进入提现页面
	 */
	function withdrawFunction() {
		window.location.href = "${path}/myaccount/cashRecord/toCashIndex.html";
	}

	/* 更改头像 */
	function toAddHeadImg() {
		$.layer({
			type : 2,
			fix : false,
			shade : [ 0.6, '#E3E3E3', true ],
			shadeClose : true,
			border : [ 10, 0.7, '#272822', true ],
			title : [ '', false ],
			offset : [ '50px', '' ],
			area : [ '500px', '400px' ],
			iframe : {
				src : '${basePath}/myaccount/addHeadImg.html'
			},
			close : function(index) {
				layer.close(index);
			}
		});
	}

	//上传成功后，刷新
	function refreshMyAccount() {
		location.href = "${path}/myaccount/toIndex.html";
	}


	function chengeUserToStock() {
		$.ajax({
			url : '${basePath}/stockApply/checkUserInfo.html',
			type : 'post',
			dataType : 'text',
			success : function(data) {
				var jsonobj = eval('(' + data + ')');
				if (jsonobj.status == 3) {
					location.href = "${path}/stockAccount/accountIndex.html";
				}else if (jsonobj.status == 2) {
					location.href = "${path}/stockApply/inRegister.html";
				}else if(jsonobj.status == 4){
					layer.msg(jsonobj.message, 2, 5);
				}else if(jsonobj.status==-1){
					location.href = "${path}/member/toLoginPage.html";
				}else if(jsonobj.status==1){
					layer.msg(jsonobj.message, 2, 5);
					layer.confirm(jsonobj.message,
							function() {
								location.href = "${path}/AccountSafetyCentre/safetyIndex.html";
							});
					
					
				}
			},
			error : function(data) {
				layer.msg('网络连接异常，请刷新页面或稍后重试！', 1, 5);
			}
		});
	}
	function format (number) {
	    return (number.toFixed(2) + '').replace(/\d{1,3}(?=(\d{3})+(\.\d*)?$)/g, '$&,');
	}
	 function getmyCollect(dateTime){
		 $.ajax({
				url : '${basePath}/myaccount/dayCollect/'+dateTime+'.html',
				data : {},
				type : 'post',
				dataType : 'json',
				success : function(data) {
					if(data.type==1){
						if(data.totalCollect>0){
							$("#collection").show();
							$("#collection1").hide();
							$("#collection2").hide();
							if(data.totalYesCollect>0){
								$("#collect").show(); 
								$("#daycollect").html(data.totalCollectStr);
								$("#borrowcollect").html(data.borrowCollectStr);
								$("#fixcollect").html(data.fixCollectStr);
								$("#yescollect").show();
								$("#dayyescollect").html(data.totalYesCollectStr);
								$("#borrowyescollect").html(data.borrowYesCollectStr);
								$("#fixyescollect").html(data.fixYesCollectStr);
							}else{
								$("#collect").show(); 
								$("#yescollect").hide();
								$("#daycollect").html(data.totalCollectStr);
								$("#borrowcollect").html(data.borrowCollectStr);
								$("#fixcollect").html(data.fixCollectStr);
							}
						}else{
							if(data.totalYesCollect>0){
								$("#yescollect").show();
								$("#collect").hide();
								$("#dayyescollect").html(data.totalYesCollectStr);
								$("#borrowyescollect").html(data.borrowYesCollectStr);
								$("#fixyescollect").html(data.fixYesCollectStr);
							}else{
								$("#collection").hide(); 
								$("#collection1").show(); 
								$("#collection2").hide();
							}
							
						}
					}
					else if(data.type==2){
						if(data.totalYesCollect>0){
							$("#collection").show();
							$("#collection1").hide();
							$("#collection2").hide();
							if(data.totalCollect>0){
								$("#yescollect").show(); 
							 	$("#collect").show(); 
								$("#daycollect").html(data.totalCollectStr);
								$("#borrowcollect").html(data.borrowCollectStr);
								$("#fixcollect").html(data.fixCollectStr);
							 	$("#dayyescollect").html(data.totalYesCollectStr);
								$("#borrowyescollect").html(data.borrowYesCollectStr);
								$("#fixyescollect").html(data.fixYesCollectStr);
							}else{
								$("#yescollect").show(); 
								$("#collect").hide(); 
								$("#dayyescollect").html(data.totalYesCollectStr);
								$("#borrowyescollect").html(data.borrowYesCollectStr);
								$("#fixyescollect").html(data.fixYesCollectStr);
							}
						}else{
							if(data.totalCollect>0){
								$("#collection").show(); 
								$("#collection2").hide(); 
								$("#collection1").hide();
								$("#collect").show(); 
								$("#yescollect").hide();
								$("#daycollect").html(data.totalCollectStr);
								$("#borrowcollect").html(data.borrowCollectStr);
								$("#fixcollect").html(data.fixCollectStr);
							}else{
								$("#collection").hide(); 
								$("#collection2").show(); 
								$("#collection1").hide();
							}
						}
					}
					else if(data.type==3){  
						$("#collect").hide(); 
						$("#yescollect").hide();
					if(data.totalCollect>0){
						$("#collection").show(); 
						$("#collection1").hide(); 
						$("#collection2").hide();
						if(data.totalYesCollect>0){
							$("#collect").show(); 
						 	$("#yescollect").show(); 
							$("#daycollect").html(data.totalCollectStr);
							$("#borrowcollect").html(data.borrowCollectStr);
							$("#fixcollect").html(data.fixCollectStr);
							$("#dayyescollect").html(data.totalYesCollectStr);
							$("#borrowyescollect").html(data.borrowYesCollectStr);
							$("#fixyescollect").html(data.fixYesCollectStr);
						}else{
							$("#yescollect").hide(); 
						 	$("#collect").show(); 
						 	$("#daycollect").html(data.totalCollectStr);
							$("#borrowcollect").html(data.borrowCollectStr);
							$("#fixcollect").html(data.fixCollectStr);
						}
					  }else{
						    if(data.totalYesCollect>0){
						    	$("#yescollect").show(); 
							 	$("#collect").hide(); 
							 	$("#collection1").hide(); 
								$("#collection2").hide();
								$("#dayyescollect").html(data.totalYesCollectStr);
								$("#borrowyescollect").html(data.borrowYesCollectStr);
								$("#fixyescollect").html(data.fixYesCollectStr);
						    }else{
						    	$("#collection1").show(); 
								$("#collection2").hide(); 
								$("#collection").hide();
						    }
						   
					  }
					}else{
						$("#collect").hide(); 
						$("#yescollect").hide();
					}
				},
				error : function(data) {
					layer.msg('网络连接异常，请刷新页面或稍后重试！', 1, 5);
				}
			});
	} 
	function getjjrszModelByYear(year){
		jjrmodeltimelist=[];
		jjrmodelztlist=[];
		$.ajax({
			url : '${basePath}/myaccount/collect/'+year+'.html',
			async: false,
			data : {},
			type : 'post',
			dataType : 'json',
			success : function(data) {
				  if(data.length!=0){
					  for(var i=0;i<data.length;i++){
						  jjrmodeltimelist.push(data[i].collectTime); 
						  jjrmodelztlist.push(data[i].type); 
						}
				  }
			},
			error : function(data) {
				layer.msg('网络连接异常，请刷新页面或稍后重试！', 1, 5);
			}
		});
	}
</script>
</html>
