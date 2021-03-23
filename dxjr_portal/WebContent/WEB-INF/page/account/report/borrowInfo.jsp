<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>顶玺金融互联网专业的P2P网站</title>

<script type="text/javascript">
	$(function() {
       	//借款总额
       	var borrowTotal = new BigDecimal('${userBorrowDetail.borrowTotal}');
		$("#borrowTotal").html(borrowTotal.setScale(2, MathContext.prototype.ROUND_DOWN)+'');		
		// 待还总额
       	var repaymentAccountTotal = new BigDecimal('${userBorrowDetail.repaymentAccountTotal}');
		$("#repaymentAccountTotal").html(repaymentAccountTotal.setScale(2, MathContext.prototype.ROUND_DOWN)+'');
		// 借款管理费
       	var borrowManageFee = new BigDecimal('${userBorrowDetail.borrowManageFee}');
		$("#borrowManageFee").html(borrowManageFee.setScale(2, MathContext.prototype.ROUND_DOWN)+'');	
		
		// 已还总额
       	var paidTotal = new BigDecimal('${userBorrowDetail.paidTotal}');
		$("#paidTotal").html(paidTotal.setScale(2, MathContext.prototype.ROUND_DOWN)+'');
		//待还本金
       	var unpayCapital = new BigDecimal('${userBorrowDetail.unpayCapital}');
		$("#unpayCapital").html(unpayCapital.setScale(2, MathContext.prototype.ROUND_DOWN)+'');	
		//已付利息
       	var havaPayInterest = new BigDecimal('${userBorrowDetail.havaPayInterest}');
		$("#havaPayInterest").html(havaPayInterest.setScale(2, MathContext.prototype.ROUND_DOWN)+'');
		
		//待还利息
       	var waitPayInterest = new BigDecimal('${userBorrowDetail.waitPayInterest}');
		$("#waitPayInterest").html(waitPayInterest.setScale(2, MathContext.prototype.ROUND_DOWN)+'');	
		//已付罚息
       	var payLateInterest = new BigDecimal('${userBorrowDetail.payLateInterest}');
		$("#payLateInterest").html(payLateInterest.setScale(2, MathContext.prototype.ROUND_DOWN)+'');	
		//待付罚息
       	var unPayLateInterest = new BigDecimal('${userBorrowDetail.unPayLateInterest}');
		$("#unPayLateInterest").html(unPayLateInterest.setScale(2, MathContext.prototype.ROUND_DOWN)+'');
		
		//提现手续费
       	var takeCashFee = new BigDecimal('${userBorrowDetail.takeCashFee}');
		$("#takeCashFee").html(takeCashFee.setScale(2, MathContext.prototype.ROUND_DOWN)+'');	
		//VIP费用
       	var vipCost = new BigDecimal('${userBorrowDetail.vipCost}');
		$("#vipCost").html(vipCost.setScale(2, MathContext.prototype.ROUND_DOWN)+'');	
		//净值额度
       	var netMoneyLimit = new BigDecimal('${userBorrowDetail.netMoneyLimit}');
		$("#netMoneyLimit").html(netMoneyLimit.setScale(2, MathContext.prototype.ROUND_DOWN)+'');	
		
	});
</script>
</head>

<body>
	<table width="875" border="1" align="center">
       <tr>
	     <td width="90" height="25" bgcolor="#ededed">借款总额</td>
	     <td width="115">￥<span id="borrowTotal" title="${userBorrowDetail.borrowTotal}"></span></td>
	     <td bgcolor="#ededed">待还总额</td>
	     <td>￥<span id="repaymentAccountTotal" title="${userBorrowDetail.repaymentAccountTotal}"></span></td>
	     <td bgcolor="#ededed">借款管理费</td>
	     <td>￥<span id="borrowManageFee" title="${userBorrowDetail.borrowManageFee}"></span></td>	                        
	  </tr>
	  <tr>
	      <td height="25" bgcolor="#ededed">已还总额</td>
	      <td>￥<span id="paidTotal" title="${userBorrowDetail.paidTotal}"></span></td>
	      <td height="25" bgcolor="#ededed">待还本金</td>
	      <td>￥<span id="unpayCapital" title="${userBorrowDetail.unpayCapital}"></span></td>	
	      <td width="90" height="25" bgcolor="#ededed">已付利息</td>
	      <td>￥<span id="havaPayInterest" title="${userBorrowDetail.havaPayInterest}"></span></td>
	  </tr>
	  <tr>	      
		  <td width="90" bgcolor="#ededed">待还利息</td>
	      <td width="115">￥<span id="waitPayInterest" title="${userBorrowDetail.waitPayInterest}"></span></td>	                        	                      
	      <td height="25" bgcolor="#ededed">已付罚息</td>
	      <td width="115">￥<span id="payLateInterest" title="${userBorrowDetail.payLateInterest}"></span></td>
	      <td width="90" bgcolor="#ededed">待付罚息</td>
	      <td>￥<span id="unPayLateInterest" title="${userBorrowDetail.unPayLateInterest}"></span></td>
	  </tr>
	  <tr>	      
	      <td bgcolor="#ededed">提现手续费</td>
	      <td>￥<span id="takeCashFee" title="${userBorrowDetail.takeCashFee}"></span></td>

	         <td height="25" bgcolor="#ededed">VIP费用</td>
	         <td>￥<span id="vipCost" title="${userBorrowDetail.vipCost}"></span></td>
	         <td bgcolor="#ededed">净值额度</td>
	         <td>￥<span id="netMoneyLimit" title="${userBorrowDetail.netMoneyLimit}"></span></td>
	  </tr>	                      
  	</table>
</body>
</html>
