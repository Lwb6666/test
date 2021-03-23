<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ page import="java.util.Calendar"%>
<%@ include file="/WEB-INF/page/common/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta charset="utf-8">
<title>顶玺金融-电子账单</title>
<%@ include file="/WEB-INF/page/common/public4.jsp"%>
<script src="${basePath }/js/myaccount/highcharts.js?version=<%=version%>"></script>
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
							y : Number('${electronBill.theMonthFixIncome}'),
						}, {
							name : '活期宝',
							y : Number('${electronBill.theMonthCurIncome}'),
						}, {
							name : '散标',
							y : Number('${electronBill.theMonthBidIncome}'),
						}, ]
					} ]
				});
			  $('#container2').highcharts({
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
							y : Number('${electronBill.theMonthFixInvest}'),
						}, {
							name : '活期宝',
							y : Number('${electronBill.theMonthCurInvest}'),
						}, {
							name : '存管标',
							y : Number('${electronBill.theMonthCustodyInvest}'),
						},{
							name : '非存管标',
							y : Number('${electronBill.theMonthNotCustodyInvest}'),
						},]
					} ]
				});
}
</script>
</head>
<body>
	<!--head -->
	<!--头部开始-->
	<%@ include file="/WEB-INF/page/common/header.jsp"%>
	<!--head off-->
	<div id="myaccount">
	<%@ include file="/WEB-INF/page/common/topmenu.jsp"%>
    <div class="wraper">

        <div class="product-deatil mt20 clearfix" style="position:relative;">
            <h1 class="f16 bule">月度账单</h1>
            <div class="bill-all"  style="right:270px;">
                <dl class="colright select select-sl02 ml10" >
                  <dt>${resMap.year}年</dt>
                    <dd>
                      <ul>
                      		<c:forEach items="2020,2019,2018,2017,2016" var="y">
                      		<c:if test="${resMap.currYear>=y}">     
						 	<li><a href="javascript:postwith('${path}/myBill/accElectronbill.html', {year:${y },month:${resMap.month}})">${y }年</a></li>
                      		</c:if>
                      		</c:forEach>
                      </ul>
                    </dd>
                </dl>
            </div>
            <div class="bill-all" style="right:170px;">
                <dl class="colright select select-sl02 ml10" >
                   <dt>${resMap.month}月</dt>
                    <dd>
                      <ul>
						 <c:if test="${resMap.year < resMap.currYear||resMap.currMonth<=1}">
						 	<c:forEach  begin="1" end="12" step="1" var="month">
						 		<li><a href="javascript:postwith('${path}/myBill/accElectronbill.html', {year:${resMap.year},month:${month}})">${month}月</a></li>
						 	</c:forEach>
						 </c:if>
						 <c:if test="${resMap.year >= resMap.currYear&&resMap.currMonth>1}">
						 	<c:forEach  begin="1" end="${resMap.currMonth-1}"  var="month">
						 		<li><a href="javascript:postwith('${path}/myBill/accElectronbill.html', {year:${resMap.year},month:${month}})">${month}月</a></li>
						 	</c:forEach>
						 </c:if>
                      </ul>
                    </dd>  
                </dl>
            </div>
            <!--  <div style=" position:absolute;right:10px;top:15px;" class="aboluo-btn03"><a href="##">下载电子账单</a></div>-->
            <div class="bill">
                <div class="bill-left">
                    <h2>当月已赚取金额<span class="orange"><fmt:formatNumber   
												value="${electronBill.theMonthFixIncome+electronBill.theMonthCurIncome+electronBill.theMonthBidIncome}"
												pattern="#,##0.00" />元</span></h2>
                    <div class="pie fl">
                    	<c:if test="${electronBill.theMonthFixIncome!='0.00'||electronBill.theMonthCurIncome!='0.00'||electronBill.theMonthBidIncome!='0.00'}">
                        <div id="container" style="height: 210px; width: 210px;"></div>
                        </c:if>
                        <c:if test="${electronBill.theMonthFixIncome=='0.00'&&electronBill.theMonthCurIncome=='0.00'&&electronBill.theMonthBidIncome=='0.00'}">
					    <div style="height: 210px; width: 210px;line-height:210px;text-align:center"><img src="${basePath}/images/myaccount/zd00.png" alt=""/></div>
						</c:if>  
                    </div>
                    <ul class="fl">
                        <li class="orange"><span><i class="iconfont">&#xe607;</i></span><span class="ml10">定期宝：</span><span class="w3"><fmt:formatNumber   
												value="${electronBill.theMonthFixIncome}"
												pattern="#,##0.00" />元</span></li>
                        <li class="blue"><span><i class="iconfont">&#xe607;</i></span><span class="ml10">活期宝：</span><span class="w3"><fmt:formatNumber   
												value="${electronBill.theMonthCurIncome}"
												pattern="#,##0.00" />元</span></li>
                        <li class="green"><span><i class="iconfont">&#xe607;</i></span><span class="ml10">散标：</span><span class="w3"><fmt:formatNumber   
												value="${electronBill.theMonthBidIncome}"
												pattern="#,##0.00" />元</span></li>
                    </ul>
                </div>
              <div class="bill-right">
                    <h2>当月投资金额<span class="orange"><fmt:formatNumber   
												value="${electronBill.theMonthFixInvest+electronBill.theMonthCurInvest
                    +electronBill.theMonthCustodyInvest+electronBill.theMonthNotCustodyInvest}"
												pattern="#,##0.00" />元</span></h2>
	                    <div class="pie fl">
	                    	 <c:if test="${electronBill.theMonthFixInvest!='0.00'||electronBill.theMonthCurInvest
                   			 	!='0.00'||electronBill.theMonthCustodyInvest!='0.00'||electronBill.theMonthNotCustodyInvest!='0.00'}">
	                        	<div id="container2" style="height: 210px; width: 210px;"></div>
	                         </c:if>
	                        <c:if test="${electronBill.theMonthFixInvest=='0.00'&&electronBill.theMonthCurInvest=='0.00'&&
		                   	 	electronBill.theMonthCustodyInvest=='0.00'&&electronBill.theMonthNotCustodyInvest=='0.00'}">
		                   	 <div style="height: 210px; width: 210px;line-height:210px;text-align:center"><img src="${basePath}/images/myaccount/zd00.png" alt=""/></div>
                    		</c:if>
	                    </div>
                    <ul class="fl">
                        <li class="orange"><span><i class="iconfont">&#xe607;</i></span><span class="ml10">定期宝：</span><span class="w3"><fmt:formatNumber   value="${electronBill.theMonthFixInvest}"  pattern="#,##0.00" />元</span></li>
                        <li class="blue"><span><i class="iconfont">&#xe607;</i></span><span class="ml10">活期宝：</span><span class="w3"><fmt:formatNumber   value="${electronBill.theMonthCurInvest}"  pattern="#,##0.00" />元</span></li>
                        <li class="green"><span><i class="iconfont">&#xe607;</i></span><span class="ml10">存管标：</span><span class="w3"><fmt:formatNumber   value="${electronBill.theMonthCustodyInvest}"  pattern="#,##0.00" />元</span></li>
                        <li class="orange2"><span><i class="iconfont">&#xe607;</i></span><span class="ml10">非存管标：</span><span class="w3"><fmt:formatNumber   value="${electronBill.theMonthNotCustodyInvest}"  pattern="#,##0.00" />元</span></li>
                    </ul>
                </div>
                <div class="clearfix"></div>
                <div class="tab clearfix">
                    <table width="100%" border="0">
                        <thead>
                            <tr>
                                <td>产品</td>
                                <td>累计已赚取金额</td>
                                <td>当月已赚金额</td>
                                <td>当月收回本金</td>
                                <td>当月新增投资</td>
                            </tr>
                        </thead>
                        <tbody class="first"> 
                            <tr>
                                <td class="title">1月宝 </td>
                                <td><fmt:formatNumber   value="${electronBill.sumFixIncomeOne}"  pattern="#,##0.00" />元 </td>
                                <td><fmt:formatNumber   value="${electronBill.theMonthFixIncomeOne}"  pattern="#,##0.00" />元 </td>
                                <td><fmt:formatNumber   value="${electronBill.theMonthFixCapitalOne}"  pattern="#,##0.00" />元 </td>
                                <td><fmt:formatNumber   value="${electronBill.theMonthFixInvestOne}"  pattern="#,##0.00" />元 </td>
                            </tr>
                            <tr>
                                <td class="title">3月宝 </td>
                                <td><fmt:formatNumber   value="${electronBill.sumFixIncomeThree}"  pattern="#,##0.00" />元 </td>
                                <td><fmt:formatNumber   value="${electronBill.theMonthFixIncomeThree}"  pattern="#,##0.00" />元 </td>
                                <td><fmt:formatNumber   value="${electronBill.theMonthFixCapitalThree}"  pattern="#,##0.00" />元 </td>
                                <td><fmt:formatNumber   value="${electronBill.theMonthFixInvestThree}"  pattern="#,##0.00" />元 </td>
                            </tr>
                            <tr>
                                <td class="title">6月宝 </td>
                                <td><fmt:formatNumber   value="${electronBill.sumFixIncomeSix}"  pattern="#,##0.00" />元 </td>
                                <td><fmt:formatNumber   value="${electronBill.theMonthFixIncomeSix}"  pattern="#,##0.00" />元 </td>
                                <td><fmt:formatNumber   value="${electronBill.theMonthFixCapitalSix}"  pattern="#,##0.00" />元 </td>
                                <td><fmt:formatNumber   value="${electronBill.theMonthFixInvestSix}"  pattern="#,##0.00" />元 </td>
                            </tr>
                            <tr>
                                <td class="title">12月宝 </td>
                               <td><fmt:formatNumber   value="${electronBill.sumFixIncomeTwelve}"  pattern="#,##0.00" />元 </td>
                                <td><fmt:formatNumber   value="${electronBill.theMonthFixIncomeTwelve}"  pattern="#,##0.00" />元 </td>
                                <td><fmt:formatNumber   value="${electronBill.theMonthFixCapitalTwelve}"  pattern="#,##0.00" />元 </td>
                                <td><fmt:formatNumber   value="${electronBill.theMonthFixInvestTwelve}"  pattern="#,##0.00" />元 </td>
                            </tr>
                        </tbody>
                        <tbody class="second">
                            <tr>
                                <td class="title">存管标 </td>
                                <td><fmt:formatNumber   value="${electronBill.sumCustodyIncome}"  pattern="#,##0.00" />元 </td>
                                <td><fmt:formatNumber   value="${electronBill.theMonthCustodyIncome}"  pattern="#,##0.00" />元 </td>
                                <td><fmt:formatNumber   value="${electronBill.theMonthCustodyCapital}"  pattern="#,##0.00" />元 </td>
                                <td><fmt:formatNumber   value="${electronBill.theMonthCustodyInvest}"  pattern="#,##0.00" />元 </td>
                            </tr>
                            <tr>
                                <td class="title">非存管标 </td>
                                <td><fmt:formatNumber   value="${electronBill.sumNotCustodyIncomeOne}"  pattern="#,##0.00" />元 </td>
                                <td><fmt:formatNumber   value="${electronBill.theMonthNotCustodyIncome}"  pattern="#,##0.00" />元 </td>
                                <td><fmt:formatNumber   value="${electronBill.theMonthNotCustody}"  pattern="#,##0.00" />元 </td>
                                <td><fmt:formatNumber   value="${electronBill.theMonthNotCustodyInvest}"  pattern="#,##0.00" />元 </td>
                            </tr>
                        </tbody>
                        <tbody class="three">
                            <tr>
                                <td class="title">活期宝 </td>
                                <td><fmt:formatNumber   value="${electronBill.sumCurIncome}"  pattern="#,##0.00" />元 </td>
                                <td><fmt:formatNumber   value="${electronBill.theMonthCurIncome}"  pattern="#,##0.00" />元 </td>
                                <td><fmt:formatNumber   value="${electronBill.theMonthCurCapital}"  pattern="#,##0.00" />元 </td>
                                <td><fmt:formatNumber   value="${electronBill.theMonthCurInvest}"  pattern="#,##0.00" />元 </td>
                            </tr>
                        </tbody>
                        <thead>
                            <tr>
                                <td class="title2">合计</td>
                                <td>
                                <fmt:formatNumber   value="${electronBill.sumFixIncomeOne+electronBill.sumFixIncomeThree+electronBill.sumFixIncomeSix
                                +electronBill.sumFixIncomeTwelve+electronBill.sumCustodyIncome+electronBill.sumNotCustodyIncomeOne+electronBill.sumCurIncome}"  pattern="#,##0.00" />
                                                                                                元 </td>
                                <td><fmt:formatNumber   value="${electronBill.theMonthFixIncomeOne+electronBill.theMonthFixIncomeThree+electronBill.theMonthFixIncomeSix+
                                electronBill.theMonthFixIncomeTwelve+electronBill.theMonthCustodyIncome+electronBill.theMonthNotCustodyIncome+electronBill.theMonthCurIncome}"  pattern="#,##0.00" />
                              	  元 </td>
                                <td>
                                <fmt:formatNumber   value="${electronBill.theMonthFixCapitalOne+electronBill.theMonthFixCapitalThree+electronBill.theMonthFixCapitalSix+
                                electronBill.theMonthFixCapitalTwelve+electronBill.theMonthCustodyCapital+electronBill.theMonthNotCustody+electronBill.theMonthCurCapital}"  pattern="#,##0.00" />元 </td>
                                <td><fmt:formatNumber   value="${electronBill.theMonthFixInvestOne+electronBill.theMonthFixInvestThree+electronBill.theMonthFixInvestSix+
                                electronBill.theMonthFixInvestTwelve+electronBill.theMonthCustodyInvest+electronBill.theMonthNotCustodyInvest+electronBill.theMonthCurInvest}"  pattern="#,##0.00" />
                               	 元 </td>
                            </tr>
                        </thead>
                    </table>
               </div>
            </div>
	  </div>
        <div class="product-deatil mt10 clearfix" >
        	 <ul class="billcol3">
              <li class="one">
              	<h6>元宝</h6>
                <ul>
                	<li><span>当月获得:</span><span class="orange"><fmt:formatNumber   value="${electronBill.theMonthAccumulatepoints}"  pattern="#,##0" />元宝</span></li>
                	<li><span>累计获得:</span><span class="orange"><fmt:formatNumber   value="${electronBill.sumAccumulatepoints}"  pattern="#,##0" />元宝</span></li>
                	<li><span>累计已使用:</span><span class="orange"><fmt:formatNumber   value="${electronBill.accumulatepointsIsuse}"  pattern="#,##0" />元宝</span></li>
                	<%--<li><span>当前持有:</span><span class="orange"><fmt:formatNumber   value="${electronBill.accumulatepoints}"  pattern="#,##0" />元宝</span></li>--%>
                </ul>
              </li>
              <li class="two">
              	<h5>红包金额</h5>
                <ul>
                	<li><span>当月获得:</span><span class="orange"><fmt:formatNumber   value="${electronBill.theMonthRedaccount}"  pattern="#,##0.00" />元</span></li>
                	<li><span>累计获得:</span><span class="orange"><fmt:formatNumber   value="${electronBill.sumRedaccount}"  pattern="#,##0.00" />元</span></li>
                	<li><span>累计已使用:</span><span class="orange"><fmt:formatNumber   value="${electronBill.redaccountIsuse}"  pattern="#,##0.00" />元</span></li>
                	<%--<li><span>当前持有:</span><span class="orange"><fmt:formatNumber   value="${electronBill.redaccount}"  pattern="#,##0.00" />元</span></li>--%>
                </ul>
              </li>
              <li class="three">
              	<h4>其他奖励</h4>
                <ul>
                	<li><span>当月获得:</span><span class="orange"><fmt:formatNumber   value="${electronBill.theMonthOtherIncome}"  pattern="#,##0.00" />元</span></li>
                	<li><span>累计获得:</span><span class="orange"><fmt:formatNumber   value="${electronBill.sumOtherIncome}"  pattern="#,##0.00" />元</span></li>
                	<li><span>抽奖机会:</span><span class="orange">${electronBill.theMonthLottchance}次<i class="iconfont blue tip-bottom" title="当月已获得抽奖次数">&#xe608;</i></span></li>
                	<li>&nbsp;</li>
                </ul>
              </li>
            </ul>
        
        </div>
      <div class="product-deatil mt10 clearfix" >
        	<ul class="billcol2">
                <li class="one"><span class=""><i class="iconfont f36 blue" >&#xe62b;</i></span><h4><span>当月充值金额</span><fmt:formatNumber   value="${electronBill.theMonthRecharge}"  pattern="#,##0.00" />元</h4></li>
                <li class="one"><span class=""><i class="iconfont f36 blue" >&#xe62c;</i></span><h4><span>当月提现金额</span><fmt:formatNumber   value="${electronBill.theMonthCash}"  pattern="#,##0.00" />元</h4></li>
            </ul>
        
        </div>
        <div class="product-deatil mt10 clearfix" >
          <h1 class="f16 bule">费用支出</h1>
          <div class="bill-pay">
             	<div class="contbox">
             	  <p class="f20"><span>支出合计：</span><span class="orange"><fmt:formatNumber   value="${electronBill.theMonthCashFee+electronBill.theMonthTransFee+electronBill.theMonthFixexitFee}"  pattern="#,##0.00" />元</span></p>
             	  <p class="mt20 f16"><span class="w240">当月提现服务费：<span class="orange"><fmt:formatNumber   value="${electronBill.theMonthCashFee}"  pattern="#,##0.00" />元</span></span><span class="w240 ml40">
             		  当月债权转让服务费：<span class="orange"><fmt:formatNumber   value="${electronBill.theMonthTransFee}"  pattern="#,##0.00" />元</span></span><span class="ml40 w240">
             	  	定期宝退出服务费：<span class="orange"><fmt:formatNumber   value="${electronBill.theMonthFixexitFee}"  pattern="#,##0.00" />元</span></span></p>
                </div>
             </div>
        </div>

  </div>
</div>
	
	<%@ include file="/WEB-INF/page/common/footer.jsp"%>
	
</body>
<script type="text/javascript">
  function postwith(to, p) { 
		  var myForm = document.createElement("form");   
		  myForm.method = "post";           
		  myForm.action = to;    
		  for ( var k in p)  {  var myInput = document.createElement("input");
		  myInput.setAttribute("name", k);           
		  myInput.setAttribute("value", p[k]);       
		  myForm.appendChild(myInput);          
	  }     
	  document.body.appendChild(myForm);      
	  myForm.submit();                         
	  document.body.removeChild(myForm);  
	}
</script>
</html>
