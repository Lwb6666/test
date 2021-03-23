<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>


<!--helpctr  s -->
<!-- 活期宝 content s  -->
<h3>活期宝</h3>
<div class="help-title" onclick="list(1)">
	<a href="javascript:void(0);" id="help_title">什么是活期宝？<span></span></a>
</div>
<div class="help-content" id="curAccountExpand1">
	<p>活期宝是顶玺金融推出的一种货币基金理财产品（申购银行理财计划），帮助投资人将加入的资金享受稳定收益，每天都可赎回的投资计划。投资人加入的资金及加入资金所产生的收益，在计息后可申请部分或全部赎回。</p>
	<p class="up" onclick="list(1)">
		<a href="javascript:void(0);" id="help_title">&nbsp;</a>
	</p>
</div>

<div class="help-title" onclick="list(2)">
	<a href="javascript:void(0);" id="help_title">活期宝是否收费？<span></span></a>
</div>
<div class="help-content" id="curAccountExpand2">
	<p>加入活期宝不收取任何费用。当从活期宝中将资金转至可用余额后申请提现，需按提现规则收取相应的 <a class="blues-c" href="${path }/bangzhu/8.html#listId=4">提现费</a> 。</p>
	<p class="up" onclick="list(2)">
		<a href="javascript:void(0);" id="help_title">&nbsp;</a>
	</p>
</div>

<div class="help-title" onclick="list(3)">
	<a href="javascript:void(0);" id="help_title">如何加入活期宝？<span></span></a>
</div>
<div class="help-content" id="curAccountExpand3">
	<p>您登陆顶玺金融网站后，可在首页加入活期宝，也可在我的账户页面将可用余额转入到活期宝。</p>
	<p class="up" onclick="list(3)">
		<a href="javascript:void(0);" id="help_title">&nbsp;</a>
	</p>
</div>


<div class="help-title" onclick="list(4)">
	<a href="javascript:void(0);" id="mbj" name="mbj">具体的收益规则是什么？<span></span></a>
</div>
<div class="help-content" id="curAccountExpand4">
	<p>充入活期宝的金额，工作日15:00前加入，加入后的第1个工作日开始计算收益，次日显示收益工作日15:00后加入，加入后的第2个工作日开始计算收益，次日显示收益。节假日加入，加入后第2个工作日开始计算收益，次日显示收益。</p>
	<table class="border">
		<tr>
			<th width="392"><p>资金进入时间</p>
			<th width="163"><p>开始计算收益</p>
			<th width="155"><p>收益到账
			</td>
			</p>
		</tr>
		<tr>
			<td><p>周一15:00（含整点）至周二15:00</p></td>
			<td><p>周三</p></td>
			<td><p>周四</p></td>
		</tr>
		<tr>
			<td><p>周二15:00（含整点）至周三15:00</p></td>
			<td><p>周四</p></td>
			<td><p>周五</p></td>
		</tr>
		<tr>
			<td><p>周三15:00（含整点）至周四15:00</p></td>
			<td><p>周五</p></td>
			<td><p>周六</p></td>
		</tr>
		<tr>
			<td><p>周四15:00（含整点）至周五15:00</p></td>
			<td><p>下周一</p></td>
			<td><p>下周二</p></td>
		</tr>
		<tr>
			<td><p>周五15:00（含整点）至下周一15:00</p></td>
			<td><p>下周二</p></td>
			<td><p>下周三</p></td>
		</tr>
	</table>

	<p class="up" onclick="list(4)">
		<a href="javascript:void(0);" id="help_title">&nbsp;</a>
	</p>
</div>

<div class="help-title" onclick="list(5)">
	<a href="javascript:void(0);" id="help_title">昨日收益如何计算？<span></span></a>
</div>
<div class="help-content" id="curAccountExpand5">
	<p>昨日收益=活期宝利率/365*（活期宝账户金额-未产生收益的资金）。未产生收益的资金即收益规则的限制不产生收益资金。</p>
	<p class="up" onclick="list(5)">
		<a href="javascript:void(0);" id="help_title">&nbsp;</a>
	</p>
</div>

<div class="help-title" onclick="list(6)">
	<a href="javascript:void(0);" id="help_title">具体的转出规则是什么？<span></span></a>
</div>
<div class="help-content" id="curAccountExpand6">
	<p>1，每天限制5笔转出，每笔限制10万元。每次转出金额进入可用余额。</p>
	<p>2，当天从活期宝受限金额（受限金额是由可用余额的受限金额加入活期宝的金额）中转出的金额，做T+1（1指自然日）处理。</p>
	<p class="up" onclick="list(6)">
		<a href="javascript:void(0);" id="help_title">&nbsp;</a>
	</p>
</div>
<!-- 活期宝 content e  -->
<!--helpctr end-->

<script type="text/javascript">
	function list(i) {
		$("#curAccountExpand" + i).animate({
			height : 'toggle'
		});

	}
</script>
