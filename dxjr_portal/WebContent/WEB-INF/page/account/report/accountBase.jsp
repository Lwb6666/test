<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>顶玺金融互联网专业的P2P网站</title>

<script type="text/javascript">
	$(function() {
       	//待收总额
		var collection = new BigDecimal('${userDetailMap.collection}');
		$("#collection").html(collection.setScale(2, MathContext.prototype.ROUND_DOWN)+'');
		//日均待收
		var dayInterst = new BigDecimal('${userDetailMap.dayInterst}');
		$("#dayInterst").html(dayInterst.setScale(2, MathContext.prototype.ROUND_DOWN)+'');   
		//投标直通车总额
		var firstTotal = new BigDecimal('${userDetailMap.firstTotal}');
		$("#first_total").html(firstTotal.setScale(2, MathContext.prototype.ROUND_DOWN)+'');	
		//投标直通车余额
		var firstUseMoney = new BigDecimal('${userDetailMap.firstUseMoney}');
		$("#first_use_money").html(firstUseMoney.setScale(2, MathContext.prototype.ROUND_DOWN)+'');
		//投标直通车冻结
		var firstFreezeAccount = new BigDecimal('${userDetailMap.firstFreezeAccount}');
		$("#firstFreezeAccount").html(firstFreezeAccount.setScale(2, MathContext.prototype.ROUND_DOWN)+'');	
		//可用资金
		var use_money = new BigDecimal('${userDetailMap.use_money}');
		$("#use_money").html(use_money.setScale(2, MathContext.prototype.ROUND_DOWN)+'');
		//投标冻结
		var tenderLockAccountTotal = new BigDecimal('${userDetailMap.tenderLockAccountTotal}');
		$("#tenderLockAccountTotal").html(tenderLockAccountTotal.setScale(2, MathContext.prototype.ROUND_DOWN)+'');
		//提现冻结
		var cashLockTotalMoney = new BigDecimal('${userDetailMap.cashLockTotalMoney}');
		$("#cashLockTotalMoney").html(cashLockTotalMoney.setScale(2, MathContext.prototype.ROUND_DOWN)+'');
		//收益总额
		var netMoney = new BigDecimal('${userDetailMap.netMoney}');
		$("#netMoney").html(netMoney.setScale(2, MathContext.prototype.ROUND_DOWN)+'');
		//支出总额
		var payTotal = new BigDecimal('${userDetailMap.payTotal}');
		$("#payTotal").html(payTotal.setScale(2, MathContext.prototype.ROUND_DOWN)+'');
		//净收益
		var netEaring = new BigDecimal('${userDetailMap.netEaring}');
		$("#netEaring").html(netEaring.setScale(2, MathContext.prototype.ROUND_DOWN)+'');

       	//资产总额
       	var zcTotal = parseFloat($("#collection").html())*1000000000000+parseFloat($("#use_money").html())*1000000000000+parseFloat($("#tenderLockAccountTotal").html())*1000000000000+parseFloat($("#cashLockTotalMoney").html())*1000000000000+parseFloat($("#firstFreezeAccount").html())*1000000000000+parseFloat($("#first_use_money").html())*1000000000000;
       	zcTotal = zcTotal/1000000000000;
       	zcTotalBigDecimal = new BigDecimal(zcTotal+"");
		$("#zcTotal").html(zcTotalBigDecimal.setScale(2, MathContext.prototype.ROUND_DOWN)+'');
	});

</script>
</head>

<body>
	<!-- 账户详情开始 -->
	<table width="875" border="1" align="center">
       <tr>
	     <td width="90" height="25" bgcolor="#ededed">资产总额</td>
	     <td width="115">￥<span id="zcTotal"></span></td>
	     <td width="90" bgcolor="#ededed">待收总额</td>
	     <td width="115">￥<span id="collection" title="${userDetailMap.collection}"></span></td>
	     <td bgcolor="#ededed">加权待收</td>
	     <td>￥<span id="dayInterst" title="${userDetailMap.dayInterst}"></span></td>	                        
	  </tr>
	  <tr>
	      <td height="25" bgcolor="#ededed">投标直通车总额</td>
	      <td>￥<span id="first_total" title="${userDetailMap.firstTotal}"></span></td>
	      <td height="25" bgcolor="#ededed">投标直通车余额</td>
	      <td>￥<span id="first_use_money" title="${userDetailMap.firstUseMoney}"></span></td>	
	      <td height="25" bgcolor="#ededed">投标直通车冻结</td>
	      <td>￥<span id="firstFreezeAccount" title="${userDetailMap.firstFreezeAccount}"></span></td>
	  </tr>
	  <tr>
	  	  <td bgcolor="#ededed" style="color:red;">可用资金</td> 
	      <td>￥<span id="use_money" title="${userDetailMap.use_money}"></span></td>
	      <td width="90" height="25" bgcolor="#ededed">投标冻结</td>
	      <td>￥<span id="tenderLockAccountTotal" title="${userDetailMap.tenderLockAccountTotal}"></span></td>
		  <td width="90" bgcolor="#ededed">提现冻结</td>
	      <td width="115">￥<span id="cashLockTotalMoney" title="${userDetailMap.cashLockTotalMoney}"></span></td>	                        	                      
	  </tr>
	  <tr>
         <td height="25" bgcolor="#ededed">收益总额</td>
         <td>￥<span id="netMoney" title="${userDetailMap.netMoney}"></span></td>
         <td bgcolor="#ededed">支出总额</td>
         <td>￥<span id="payTotal" title="${userDetailMap.payTotal}"></span></td>
         <td bgcolor="#ededed">净收益</td>
         <td>￥<span id="netEaring" title="${userDetailMap.netEaring}"></span></td>
	  </tr>
  	</table>
</body>
</html>
