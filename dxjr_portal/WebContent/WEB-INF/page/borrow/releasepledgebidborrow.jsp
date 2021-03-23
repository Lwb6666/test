<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/page/common/taglib.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<%@ include file="/WEB-INF/page/common/public.jsp"%>
	</head>
	<body>
	<form method="post" name="form1">
	<table>
		<tr>
			<td>借款金额</td>
			<td><input type="text" name="account" id="account" value="500" maxlength="12" size="20"/></td>
			<td>年化利率</td>
			<td><input type="text" name="apr" id="apr" size="20" maxlength="5" value="6"/>%</td>
		</tr>
		<tr>
			<td>还款方式</td>
			<td>
				<select name="style" id="style" onchange="ChangeStyle(this.value)">
					<c:forEach items="${borrowRepayTypeList}" var="borrowRepayType">
						<option value="${borrowRepayType.name}">${borrowRepayType.value}</option>
					</c:forEach>
				</select>
			</td>
			<td>借款期限</td>
			<td>
				<span id="monthlimit" style="display:block">
					<select name="timeLimitmonth" id="timeLimitmonth">
						<c:forEach items="${borrowLimitMonthList}" var="borrowLimitMonth">
							<option value="${borrowLimitMonth.name}">${borrowLimitMonth.value}</option>
						</c:forEach>
					</select>
				</span>
				<span id="daylimit" style="display:none">
					<input type="text" name="timeLimitday" id="timeLimitday" size="20" maxlength="2"/>天
					<font color="red">1天到90天为整数</font>
				</span>
				
			</td>
		</tr>
		<tr>
			<td>最低投标额度</td>
			<td>
				<input type="text" name="lowestAccount" id="lowestAccount" size="20" value="50"/>
			</td>
			<td>最高投标额度</td>
			<td>
				<input type="text" name="mostAccount" id="mostAccount" size="20" value="0"/>
			</td>
		</tr>
		<tr>
			<td>投标密码</td>
			<td>
				<input type="password" name="bidPassword" id="bidPassword" size="20" maxlength="12"/>
			</td>
			<td>有效期限</td>
			<td>
				<input type="text" name="validTime" id="validTime" size="20" maxlength="3" value="3"/>天
			</td>
		</tr>
		<tr>
			<td>借款用途</td>
			<td>
				<select name="use" id="use">
					<c:forEach items="${purposeList}" var="purpose">
						<option value="${purpose.name}">${purpose.value}</option>
					</c:forEach>
				</select>
			</td>
			<td>抵押标类型</td>
			<td>
				<select name="pledgeType" id="pledgeType">
					<c:forEach items="${pledgeBidTypeList}" var="pledgeBidType">
						<option value="${pledgeBidType.name}">${pledgeBidType.value}</option>
					</c:forEach>
				</select>
			</td>
		</tr>
		<tr>
			<td>标题：</td>
			<td colspan="3"><input type="text" name="name" id="name" size="60" maxlength="100"/></td>
		</tr>
		<tr>
			<td colspan="4">
				<textarea rows="10" cols="90" name="content" id="content" style="width:1048px;"></textarea>
			</td>
		</tr>
		<tr>
			<td><input type="button" name="btn" onclick="insertBorrow()" value="提交"/></td>
			<td></td>
			<td></td>
			<td></td>
		</tr>
	</table>
	<input type="hidden" name="timeLimit" id="timeLimit"/>
	</form>
	</body>
</html>

<script type="text/javascript">
function insertBorrow(){
	if(!checkData()){
		return;
	}
	document.forms[0].action="${basePath}/borrow/pledgebid/insertPledgeBorrow.html";
	document.forms[0].submit();
}

function ChangeStyle(value){
	if(value=='${paymonth}' || value=='${payinterestmonth}'){
		document.getElementById('monthlimit').style.display='block';
		document.getElementById('daylimit').style.display='none';
	}else if(value=='${payday}'){
		document.getElementById('monthlimit').style.display='none';
		document.getElementById('daylimit').style.display='block';
	}
}

function checkData(){
	var reg1= /^(([1-9]\d{0,9})|0)(\.\d{1,2})?$/;
	var account = $('#account').val();
	//借款金额
	var account = $.trim($('#account').val());
	if(account==null || account==""){
		layer.alert("借款金额不能为空。");
		return false;
	}
	if(!reg1.test(account)){
		layer.alert("借款金额不是正确的金额。");
		return false;
	}
	if(account<500){
		layer.alert("借款金额必须大于等于500。");
		return false;
	}
	
	var apr = $.trim($('#apr').val());
	if(apr.length==0){
		layer.alert("年化利率不能为空。");
		return false;
	}
	
	if(!reg1.test(apr)){
		layer.alert("年化利率必须大于等于6并且小于等于24。");
		return false;
	}
	
	if(Number(apr)<6 || Number(apr)>24){
		layer.alert("年化利率必须大于等于6并且小于等于24。");
		return false;
	}
	
	var time_limit = null;
	var style = $.trim($("#style").val());
	if(style=='${paymonth}'){
		time_limit = $("#timeLimitmonth").val();
		if(!reg1.test(time_limit)){
			layer.alert("借款期限必须为数字。");
			return false;
		}
	}else if(style=='${payday}'){
		time_limit = $("#timeLimitday").val();
		if(!reg1.test(time_limit)){
			layer.alert("借款期限必须为数字。");
			return false;
		}
		if(Number(time_limit)>90 || Number(time_limit)<1){
			layer.alert("借款期限必须在1~90天之间。");
			return false;
		}
	}else if(style=='${payinterestmonth}'){
		time_limit = $("#timeLimitmonth").val();
		if(!reg1.test(time_limit)){
			layer.alert("借款期限必须为数字。");
			return false;
		}
	}
	$("#timeLimit").val(time_limit);
	if($("#timeLimit").val()=='' || $("#timeLimit").val==null){
		layer.alert("借款期限不能为空。");
		return false;
	}
	
	
	var lowest_account = $.trim($('#lowestAccount').val());
	if(lowest_account.length == 0){
		layer.alert("最低投标额度不能为空。");
		return false;
	}
	if(!reg1.test(lowest_account)){
		layer.alert("最低投标额度不是正确的金额。");
		return false;
	}
	if(Number(lowest_account) > Number(account)){
		layer.alert("最低投标额度不能大于借款金额。");
		return false;
	}
	if(Number(lowest_account) < 50){
		layer.alert("最低投标额度不能小于￥50。");
		return false;
	}
	
	var most_account = $.trim($('#mostAccount').val());
	if(most_account.length == 0){
		layer.alert("最高投标额度不能为空。");
		return false;
	}
	if(!reg1.test(most_account)){
		layer.alert("最高投标额度不是正确的金额。");
		return false;
	}
	if(Number(lowest_account) > Number(most_account) && Number(most_account) != 0){
		layer.alert("最高投标额度不能小于最低投标额度。");
		return false;
	}
	if(Number(most_account) > Number(account)){
		layer.alert("最高投标额度不能大于借款金额。");
		return false;
	}
	

	var validTime = $.trim($('#validTime').val());
	if(validTime.length==0){
		layer.alert("有效期限不能为空。");
		return false;
	}
	var pattern= /^[0-9]+$/;
	if(!pattern.test(validTime)){
		layer.alert("有效期限必须填写正整数。");
		return false;
	}
	if(validTime.length>4){
		layer.alert("有效期限长度少能超过4为数字。");
		return false;
	}
	
	var name = $.trim($('#name').val());
	if(name=="" || name==null){
		layer.alert("借款标题未填写。");
		return false;
	}
	if(name.length >100){
		layer.alert("借款标题长度不能超过100个字符。");
		return false;
	}
	
	var content = $.trim($("#content").val());
	if(content.length > 1000){
		layer.alert("详细说明长度不能超过1000个字符。");
		return false;
	}
	
	var bid_password = $("#bidPassword").val();
	if(bid_password.length>0){
		var reg2 = /^[a-zA-Z0-9]+$/;
		if(!reg2.test(bid_password)){
			layer.alert("投标密码必须为字母或数字。");
			return false;
		}
		if(bid_password.length < 6){
			layer.alert("投标密码长度必须大于或等于6位。");
			return false;
		}else if(bid_password.length > 12){
			layer.alert("投标密码长度不能大于或等于12位。");
			return false;
		}
	}
	return true;
}
</script>
