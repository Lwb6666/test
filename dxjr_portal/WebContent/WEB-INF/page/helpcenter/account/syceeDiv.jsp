<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
 
${param.isHome!=null?'<h2><a href="#">元宝和会员</a></h2>':'<h1><a href="#">元宝和会员</a></h1>' }
<div class="help-title" onclick="list(1)">
	<a href="###" id="help_title">元宝是什么？有什么用？<span></span></a>
</div>
<div class="help-content" id="list1" style="display: none;">
	<p>
		元宝被用来衡量顶玺投资用户对平台的贡献，也是平台对用户的回馈奖励。元宝可用来增加用户的荣誉值，也可以在元宝商城兑换各种礼品，如红包、iPhone6等，兑换后扣减相应元宝。<a style="color: #09F;" href="${path}/sycee.html">立即兑换></a>
	</p>
	<p class="up" onclick="list(1)">
		<a href="###" id="help_title">&nbsp;</a>
	</p>
</div>
<div class="help-title" onclick="list(2)">
	<a href="###" id="help_title">如何获得元宝？元宝如何计算？<span></span></a>
</div>
<div class="help-content" id="list2">
	<p>元宝有三种途径获得：每日平台待收元宝、论坛元宝和抽奖元宝</p>
	<p>(1) 每日平台待收元宝=基础元宝×会员倍率；基础元宝=当天待收总额/1000。待收元宝每天23:00发放，只发放整数部分，不满整数的 部分不计算。会员等级越高，元宝累积越快，不同等级的会员倍率如下：</p>
	<p>普通会员：1倍</p>
	<p>金牌会员：1.2倍</p>
	<p>白金会员：1.4倍</p>
	<p>钻石会员：1.6倍</p>
	<p>皇冠会员：1.8倍</p>
	<p>金皇冠会员：2.0倍</p>
	<p>终身顶级会员：2.0倍</p>
	<p>(2) 论坛元宝可由参加论坛活动获得，具体如下：</p>

	<table class="yb-table1" width="200" border="0" cellspacing="0" cellpadding="0">
		<tr>
			<th scope="col">序号</th>
			<th scope="col">&nbsp; 动作</th>
			<th scope="col">&nbsp;周期范围</th>
			<th scope="col">&nbsp;周期内最多 奖励次数</th>
			<th scope="col">元宝值</th>
		</tr>
		<tr>
			<td>1&nbsp;</td>
			<td>论坛签到 &nbsp;</td>
			<td>每天&nbsp;</td>
			<td>1&nbsp;</td>
			<td>（见备注1）&nbsp;</td>
		</tr>
		<tr>
			<td>2&nbsp;</td>
			<td>精华帖&nbsp;</td>
			<td>每天 &nbsp;</td>
			<td>1&nbsp;</td>
			<td>30(见备注2)&nbsp;</td>
		</tr>
	</table>

	<p>备注1：</p>

	<p>1）论坛签到楼层排名前30位的用户签到元宝为2*n</p>

	<p>2）论坛签到楼层排名在30位后的用户签到元宝为n</p>

	<p>3）n代表连续签到天数，最大为10</p>

	<p>备注2：</p>
	<p>1）每位论坛用户每天得到的精华帖元宝最多为30个元宝。即用户同一天如果有多个帖子被设置为精华帖，则只有第一个帖子赠送 精华帖元宝。</p>
	<p>2）每位论坛用户每月得到的精华贴元宝最多为600个元宝。</p>

    <p>(3) 抽奖元宝：通过抽奖的方式获得的元宝奖励为抽奖元宝。</p>
	<p class="up" onclick="list(2)">
		<a href="###" id="help_title">&nbsp;</a>
	</p>
</div>
<div class="help-title" onclick="list(3)">
	<a href="###" id="help_title"> 什么是荣誉值？荣誉值如何计算？<span></span></a>
</div>
<div class="help-content" id="list3">
	<p>荣誉值体现了投资用户对顶玺的贡献大小，由历史元宝累加得来，用户获得1元宝同时会获得1荣誉值</p>
	<p class="up" onclick="list(3)">
		<a href="###" id="help_title">&nbsp;</a>
	</p>
</div>
<div class="help-title" onclick="list(4)">
	<a href="###" id="help_title">会员等级如何划分？<span></span></a>
</div>
<div class="help-content" id="list4">
	<p>会员等级根据荣誉值划分，具体如下：</p>

	<p>普通会员荣誉值＜5k</p>

	<p>金牌会员荣誉值≥5k</p>

	<p>白金会员荣誉值≥2w</p>

	<p>钻石会员荣誉值≥4w</p>

	<p>皇冠会员荣誉值≥10w</p>

	<p>金皇冠会员荣誉值≥20w</p>

	<p>终身顶级会员荣誉值--</p>

	<p>注：2015年11月5日元宝系统上线之前的老用户会根据历史加权待收转换为荣誉值，以确保会员等级不变</p>
	<p class="up" onclick="list(4)">
		<a href="###" id="help_title">&nbsp;</a>
	</p>
</div>

