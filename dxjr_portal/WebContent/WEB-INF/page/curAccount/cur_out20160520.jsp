<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/page/common/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ include file="/WEB-INF/page/common/public.jsp"%>
<title>转出_活期宝_我的账号</title>
</head>
<body style="overflow-y:hidden;background:#fff;">
<form action="" method="post" name="curOutForm" id="curOutForm">
<input type="hidden" name="targetFrame" id="targetFrame" value="${targetFrame}"/>
<div class="sf">
	  <p style="font-size:12px; color:#666666; text-align:center; padding:20px 0; ">
	  	温馨提示：每天限制5笔转出，每笔限制10万元
	  </p>
	  <dl style="padding: 0px 0;">
	  	  <dd><span>可转出金额 ：</span><fmt:formatNumber value="${maxAccount}" pattern="#,##0.00"></fmt:formatNumber></dd>
	      <dd><span><font color="red">*</font>金额 ：</span> <input type="text" name="account" id="account" style="width:150px"/>
	      <a style="color:blue" href="javascript:enterMomey();">自动输入金额</a>
	      </dd>
	      <dd><span><font color="red">*</font>交易密码 ：</span> <input type="password" name="paypassword" id="paypassword" style="width:150px" />
	      	<a href="${path}/AccountSafetyCentre/safetyCentre/enterFindTransactionPwd.html" style="color:blue" target="_parent">忘记密码？</a></dd>
	      <dd style="float: right;"><span></span><a href="${path}/bangzhu/23.html?#listId=6" style="color:blue" target="_parent">查看详细转出规则</a></dd>
	  </dl>
	  <dl style="padding: 0px 0;">
		  	<dd>
			  	<div class="gg_btn">
				  	<table>
				  		<tr>
				  			<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
				  			<td><input id="save_curOut" type="button" value="提交" onclick="saveCurOut()" style="cursor:pointer;"/></td>
				  			<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
				  			<td><input type="button" value="取消" onclick="closeCurIn()" /></td>
				  		</tr>
				  	</table>
			  	</div>
		  	</dd>
	  </dl>
</div>
</form>
</body>
<script type="text/javascript">
var _load;
/**
 * 点击自动输入金额触发事件
 */
function enterMomey(){
	if(${null==portal:currentUser()}){
		 layer.msg("请先登录", 1, 5,function(){
			 parent.window.location.href="${path}/member/toLoginPage.html";
		 });
		 return;
	}
	if(${portal:hasRole("borrow")}){
		parent.layer.msg("您是借款用户,不能进行此操作", 1, 5);
		 return;
	}
	var useMoney = Number('${maxAccount}');
	$("#account").val(useMoney);
}

function validateData(){
	//金额的正则表达式
	var zfdsReg = /^(([1-9]\d{0,9})|0)(\.\d{1,2})?$/;
	var reg= /^\d+[\.]?\d{0,2}$/g;
	if(new Number('${curOutCount}') >= 5){
		parent.layer.msg("当天转出笔数已达到5笔，无法再进行转出!", 2, 5);
		return false;
	}
	if(new Number('${currentHourNum}') >= 0 && new Number('${currentHourNum}') < 4){
		parent.layer.msg("耐心等待一会，系统正在努力结算！", 2, 5);
		return false;
	}
	
	var account = $("#account").val();
	if(account == null || account == ""){
		parent.parent.layer.msg("转出金额未填写!", 2, 5);
		return false;
	}
	if(!zfdsReg.test(account)){
		parent.layer.msg("转出金额输入有误!", 2, 5);
		return false;
	}
	if(account == 0){
		parent.layer.msg("转出金额不能为 0!", 2, 5);
		return false;
	}
	if(!reg.test(account)){
		parent.layer.msg("转出金额只能保留2位小数!", 2, 5);
		return false;
	}
	if(new Number(account) > 100000){
		parent.layer.msg("转出金额不能超过10万元", 2, 5);
		return false;
	}
	if(account > new Number('${maxAccount}')){
		if(new Number('${maxAccount}') > 0){
			parent.layer.msg("转出金额不能超过${maxAccount}元", 2, 5);
		}else{
			parent.layer.msg("可转出金额为0元", 2, 5);
		}
		return false;
	}
	
	var paypassword = $("#paypassword").val();
	if(paypassword == null || paypassword == ""){
		parent.layer.msg("交易密码未填写!", 2, 5);
		return false;
	}
	return true;
}
/**
 * 活期宝转出到可用余额
 */
function saveCurOut(){
	$("#save_curOut").removeAttr("onclick");
	if(validateData()){
		if(parent.layer.confirm("确定要转出吗？",function(){
			_load = parent.layer.load('处理中..');
			//提交后台
			$("#curOutForm").ajaxSubmit({
				url : '${basePath }/curOut/saveCurOut.html',
				type : "POST",
				success : function(data) {
					layer.close(_load);
					$("#save_curOut").attr("onclick", "saveCurOut()");
					if(data.code=="1"){
						parent.layer.alert('此次交易成功转出'+$("#account").val()+'元到可用余额', 1, "温馨提示", function(){
							if($("#targetFrame").val() == 1){
								window.open("${path}/curAccountController/curAccountInterest.html","_parent");
							}else{
								window.open("${path}/myaccount/toIndex.html","_parent");
							}
						});
					}else{
						parent.layer.msg(data.message, 2, 5);
					}
				},
				error : function(data) {
					layer.close(_load);
					$("#save_curOut").attr("onclick", "saveCurOut()");
					parent.layer.msg("网络连接超时，请您稍后重试", 3, 5);
				}
			});
		}, function() {
			$("#save_curOut").attr("onclick", "saveCurOut()");
		}));
		 
	}else{
		$("#save_curOut").attr("onclick", "saveCurOut()");
	}	
}

/**
 * 点击取消按钮
 */
function closeCurIn(){
	if($("#targetFrame").val() == 1){
		parent.window.open("${path}/curAccountController/curAccountInterest.html","_self");
	}else{
		window.open("${path}/myaccount/toIndex.html","_self");
	}
	//当你在iframe页面关闭自身时
	var index = parent.layer.getFrameIndex(window.name);
	parent.layer.close(index);
}
</script>
</html>
