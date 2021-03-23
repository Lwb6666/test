<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>顶玺金融互联网专业的P2P网站</title>
<script type="text/javascript">
	$(function() {
       	//投资总额
       	var investTotal = new BigDecimal('${userInvestDetailMap.investTotal}');
		$("#investTotal").html(investTotal.setScale(2, MathContext.prototype.ROUND_DOWN)+'');		
		// 已赚利息
       	var yesInterstTotal = new BigDecimal('${userInvestDetailMap.yesInterstTotal}');
		$("#yesInterstTotal").html(yesInterstTotal.setScale(2, MathContext.prototype.ROUND_DOWN)+'');
		//待收利息
       	var interstTotal = new BigDecimal('${userInvestDetailMap.interstTotal}');
		$("#interstTotal").html(interstTotal.setScale(2, MathContext.prototype.ROUND_DOWN)+'');	
		
		//待收排名
		$("#uncollectedRanking").html('${userInvestDetailMap.uncollectedRanking}');	
		// 已付利息管理费
       	var inverestfee = new BigDecimal('${userInvestDetailMap.inverestfee}');
		$("#inverestfee").html(inverestfee.setScale(2, MathContext.prototype.ROUND_DOWN)+'');			
		//待付利息管理费
       	var unpayInterest = new BigDecimal('${userInvestDetailMap.unpayInterest}');
		$("#unpayInterest").html(unpayInterest.setScale(2, MathContext.prototype.ROUND_DOWN)+'');
		
		// 已赚奖励
       	var awardTotal = new BigDecimal('${userInvestDetailMap.awardTotal}');
		$("#awardTotal").html(awardTotal.setScale(2, MathContext.prototype.ROUND_DOWN)+'');
		//已收罚息
       	var receiveInterest = new BigDecimal('${userInvestDetailMap.receiveInterest}');
		$("#receiveInterest").html(receiveInterest.setScale(2, MathContext.prototype.ROUND_DOWN)+'');	
		//待收罚息
       	var unReceiveInterest = new BigDecimal('${userInvestDetailMap.unReceiveInterest}');
		$("#unReceiveInterest").html(unReceiveInterest.setScale(2, MathContext.prototype.ROUND_DOWN)+'');		
		
		//充值总额
       	var rechangeTotalMoney = new BigDecimal('${userInvestDetailMap.rechangeTotalMoney}');
		$("#rechangeTotalMoney").html(rechangeTotalMoney.setScale(2, MathContext.prototype.ROUND_DOWN)+'');	
		//提现总额
       	var cashTotalMoney = new BigDecimal('${userInvestDetailMap.cashTotalMoney}');
		$("#cashTotalMoney").html(cashTotalMoney.setScale(2, MathContext.prototype.ROUND_DOWN)+'');	
		//净充值
       	var netRechangeTotal = new BigDecimal('${userInvestDetailMap.netRechangeTotal}');
		$("#netRechangeTotal").html(netRechangeTotal.setScale(2, MathContext.prototype.ROUND_DOWN)+'');			
	});

</script>
</head>

<body>
	<!-- 账户详情开始 -->
	<table width="875" border="1" align="center">
       <tr>
	     <td width="90" height="25" bgcolor="#ededed">投资总额</td>
	     <td width="115">￥<span id="investTotal" title="${userInvestDetailMap.investTotal}"></span></td>
	     <td width="90" bgcolor="#ededed">已赚利息</td>
	     <td width="115">￥<span id="yesInterstTotal" title="${userInvestDetailMap.yesInterstTotal}"></span></td>
	      <td bgcolor="#ededed">待收利息</td>
	      <td>￥<span id="interstTotal" title="${userInvestDetailMap.interstTotal}"></span></td>
	  </tr>
	  <tr>
	      <td height="25" bgcolor="#ededed">待收排名</td>
	      <td><span id="uncollectedRanking" title="${userInvestDetailMap.uncollectedRanking}"></span></td>
	      <td height="25" bgcolor="#ededed">已付利息管理费</td>
	      <td>￥<span id="inverestfee" title="${userInvestDetailMap.inverestfee}"></span></td>	
		  <td height="25" bgcolor="#ededed">待付利息管理费</td>
	      <td>￥<span id="unpayInterest" title="${userInvestDetailMap.unpayInterest}"></span></td>
	  </tr>
	  <tr>
	      <td height="25" bgcolor="#ededed">已赚奖励</td>
	      <td>￥<span id="awardTotal" title="${userInvestDetailMap.awardTotal}"></span></td>
	      <td height="25" bgcolor="#ededed">已收罚息</td>
	      <td>￥<span id="receiveInterest" title="${userInvestDetailMap.receiveInterest}"></span></td>
	      <td height="25" bgcolor="#ededed">待收罚息</td>
	      <td>￥<span id="unReceiveInterest" title="${userInvestDetailMap.unReceiveInterest}"></span></td>	
	  </tr>
	      <tr>
	         <td height="25" bgcolor="#ededed">充值总额</td>
	         <td>￥<span id="rechangeTotalMoney" title="${userInvestDetailMap.rechangeTotalMoney}"></span></td>
	         <td bgcolor="#ededed">提现总额</td>
	         <td>￥<span id="cashTotalMoney" title="${userInvestDetailMap.cashTotalMoney}"></span></td>
	         <td bgcolor="#ededed">净充值</td>
	         <td>￥<span id="netRechangeTotal" title="${userInvestDetailMap.netRechangeTotal}"></span></td>
	  </tr>
  	</table>
</body>
</html>
