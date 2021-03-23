<%@page import="com.dxjr.utils.DateUtils"%>
<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/page/common/taglib.jsp"%>
<!doctype html>
<html>
<head>
<%@ include file="/WEB-INF/page/common/public.jsp"%>
<meta charset="utf-8">
<title>活期宝_p2p网贷理财平台_活期宝-顶玺金融官网</title>
</head>

<body>
	<!--头部开始-->
	<%@ include file="/WEB-INF/page/common/header.jsp"%>
	<!--头部结束-->
	<div class="clear"></div>

	<!--内容开始-->
	<div id="Bmain">
		<div class="title">
			<span class="home"><a href="${path}/">顶玺金融</a></span>
			<span><a href="javascript:void(0);">我要投资</a></span>
			<span><a href="${path}/curInController.html">活期宝</a></span>
		</div>
		<form action="" method="post" id="currentForm">
		
			<input type="hidden" name="useMoney" id="useMoney" value="${account.useMoney}"/>
			<div class=" fl currents position-re h252 pdm25">
				<div class="new"></div>
				<div class="tbw bdm-line">
					<div class="tbtitle mrl50 position-re pdl30" style="font-size:17px;"><i class="icon-ssss"></i>活期宝</div>
				</div>
				<div class="current-t mdt15">
					<table>
						<tr>
							<td width="32%" class="f16 bdr-line pl85">年化收益</td>
							<td width="68%" colspan="2" class="pl115">加入金额：<strong>1元起</strong></td>
						</tr>
						<tr>
							<td rowspan="2" class="yellow-c bdr-line pl85"><em class="f60">5.6</em>%</td>
							<td colspan="2" class="pl115">加入时间：<strong>无限制</strong></td>
						</tr>
						<tr>
							<td colspan="2" class="pl115">收费方式：<strong>无任何费用</strong></td>
						</tr>
						<tr>
							<td class="f16 pl85">投资期限：<em class=" f20 yellow-c"><strong>活期</strong></em></td>
							<td colspan="2" class="green-c pl115"><strong>保障方式</strong>：<img src="${basePath}/images/safe-bao.png"/><strong>风险保证金保障</strong></td>
						</tr>
					</table>
				</div>
			</div>
			<div class="fr currents tbzdbg pd10 h252 zhye-div"> 
				<p>账户余额<br/>
				<c:choose>
					<c:when test="${not empty account.useMoney}"><em class="f20 yellow-c"><fmt:formatNumber
							value="${account.useMoney}" pattern="#,##0.00" /></em>元&nbsp;&nbsp;
						</c:when>
					<c:otherwise>
						<a class="f16 blues-c" href="${path}/member/toLoginPage.html">登录后可见</a>&nbsp;&nbsp;
					</c:otherwise>
				</c:choose>
				<input class="greens safe_button01" type="button" value="充值" onclick="topupFunction();" style="cursor:pointer;"/></p>
				<p>加入金额：<input type="text" name="tenderMoney" id="tenderMoney" autocomplete="off" onblur="changeTenderMoney()" />元</p>
				<a class="mdl60 blues" href="javascript:enterMomey();">自动输入金额</a>
				<p>交易密码：<input type="password" name="payPassword" id="payPassword" autocomplete="off"/></p>
				<p>验证码&nbsp;&nbsp;&nbsp;：<input type="text" name="checkCode" id="checkCode" size="4" maxlength="4" style="width:75px;"/>&nbsp;<img onclick="loadimage()" name="randImage" id="randImage" src="${basePath}/random.jsp"/></p>
				<p><input class="tbbid tbwidth greens " type="button" value="立即加入" onclick="joinFunction();" /></p>
			</div>
			
			<div class="tblist_title">
				<ul>
					<li id="accountIntroduce" class="selected"><a href="javascript:void(0);" onclick="changeTab('accountIntroduce', '${basePath}/curInController/toAccountIntroduce.html');"> 活期宝介绍 </a> </li>
					<li id="accountProblem"><a href="javascript:void(0);" onclick="changeTab('accountProblem', '${basePath}/curInController/toAccountProblem.html');">常见问题</a> </li>
				</ul>
			</div>
			<div id="curAccountTab"></div>
		</form>
	</div>
           <!--内容结束-->
	<%@ include file="/WEB-INF/page/common/footer.jsp"%>
	
</body>
<script type="text/javascript">

	$(function() {
		changeTab('accountIntroduce', '${basePath}/curInController/toAccountIntroduce.html');
		
	});


	/**
	 * 进入充值页面
	 */
	function topupFunction(){
		window.location.href = "${path}/account/topup/toTopupIndex.html";
	}
	
	/**
	 * 点击自动输入金额触发事件
	 */
	function enterMomey(){
		if(${null==portal:currentUser()}){
			 layer.msg("请先登录", 1, 5,function(){
				 window.location.href="${path}/member/toLoginPage.html";
			 });
			 return;
		}
		if(${portal:hasRole("borrow")}){
			 layer.msg("您是借款用户,不能进行此操作", 1, 5);
			 return;
		}
		var useMoney = Number($("#useMoney").val());
		$("#tenderMoney").val(useMoney);
	}
	
	/**
	*  加入金额光标移开触发事件
	*/
	function  changeTenderMoney(){
		if(${null==portal:currentUser()}){
			 layer.msg("请先登录", 1, 5,function(){
				 window.location.href="${path}/member/toLoginPage.html";
			 });
			 return;
		}
		if(${portal:hasRole("borrow")}){
			 layer.msg("您是借款用户,不能进行此操作", 1, 5);
			 return;
		}
		var payMoney= $.trim($("#tenderMoney").val());
		$("#tenderMoney").val(payMoney);
		var moneyReg = /^(([1-9]\d{0,9})|0)(\.\d*)?$/;//金额的正则表达式
		if (payMoney.length>0 && !moneyReg.test((payMoney))) {
			layer.msg("加入金额格式不正确", 2, 5);
			$("#tenderMoney").focus();
			return;
		}else{
			//2位小数金额的正则表达式
			moneyReg = /^(([1-9]\d{0,9})|0)(\.\d{1,2})?$/;
			if (payMoney.length>0 && !moneyReg.test((payMoney))) {
				layer.msg("输入金额小数不能大于2位", 2, 5);
				$("#tenderMoney").focus();
				return;
			}
			
		}
		if(payMoney == ""){
			//$("#tenderMoney").val("0");
			$("#tenderMoney").focus();
			return;
		}
		var useMoney = parseFloat($("#useMoney").val());
		if (parseFloat(payMoney) > useMoney) {
			layer.msg("账户余额小于加入金额，不能加入", 2, 5);
			return;
		}
		if(parseFloat(payMoney) < 1){
			layer.msg("加入金额不能小于1元", 2, 5);
			return;
		}
		$("#payPassword").focus();
	}
	
	/**
	 * 点击进入金额按钮触发事件
	 */
	function joinFunction(){
		if(${null==portal:currentUser()}){
			 layer.msg("请先登录", 1, 5,function(){
				 window.location.href="${path}/member/toLoginPage.html";
			 });
			 return;
		}
		if(${portal:hasRole("borrow")}){
			 layer.msg("您是借款用户,不能进行此操作", 1, 5);
			 return;
		}
		var payMoney= $.trim($("#tenderMoney").val());
		$("#tenderMoney").val(payMoney);
		var moneyReg = /^(([1-9]\d{0,9})|0)(\.\d*)?$/;//金额的正则表达式
		if (payMoney.length>0 && !moneyReg.test((payMoney))) {
			layer.msg("加入金额格式不正确", 2, 5);
			$("#tenderMoney").focus();
			return;
		}else{
			//2位小数金额的正则表达式
			moneyReg = /^(([1-9]\d{0,9})|0)(\.\d{1,2})?$/;
			if (payMoney.length>0 && !moneyReg.test((payMoney))) {
				layer.msg("输入金额小数不能大于2位", 2, 5);
				$("#tenderMoney").focus();
				return;
			}
			
		}
		if(payMoney == ""){
			//$("#tenderMoney").val("0");
			layer.msg("加入金额未填写", 2, 5);
			$("#tenderMoney").focus();
			return;
		}
		var useMoney = parseFloat($("#useMoney").val());
		if (parseFloat(payMoney) > useMoney) {
			layer.msg("账户余额小于加入金额，不能加入", 2, 5);
			return;
		}
		if(parseFloat(payMoney) < 1){
			layer.msg("加入金额不能小于1元", 2, 5);
			return;
		}
		var msg = "";
		var payPassword = $("#payPassword").val();
		var checkCode = $("#checkCode").val();
		if (payPassword == null || payPassword == "") {
			layer.msg("交易密码未填写！", 2, 5);
			return;
		}
		if (checkCode == null || checkCode == "") {
			layer.msg("验证码未填写！", 2, 5);
			return;
		}
		if(layer.confirm("你确认把"+$("#tenderMoney").val()+"元（"+numToChinese($("#tenderMoney").val())+"）"+"加入到活期宝吗？",function(){
			saveCurrent();
		}));
		
		
	}
	
	/**
	* 活期宝加入按钮触发事件
	*/
	function saveCurrent(){
   	   var _load = layer.load('处理中..');
	   $("#currentForm").ajaxSubmit({
		   type: "POST",
		   url:"${basePath}/curInController/doCurrentIn.html",
		   success:function (data){
				if("0"==data.code || "-1"==data.code || "-2"==data.code || "-3"==data.code || "-4"==data.code || "-5"==data.code){
					layer.msg(data.message, 1, 5,function(){
						if("0"==data.code){
							window.location.href="${path}/member/toLoginPage.html";
						}else if("-1"==data.code){
					    	window.location.href="${path}/AccountSafetyCentre/safetyIndex.html";
					    }else if("-2"==data.code){
					    	window.location.href="${path}/account/approve/realname/toRealNameAppro.html";
					    }else if("-4"==data.code){
					    	window.location.href="${path}/account/safe/toSetPayPwd.html";
					    }else if("-5"==data.code){
					    	window.location.href="${path}/bankinfo/toBankCard.html";
					    }
					});
					return;
				}
				if("-99" == data.code){
					return;
				}
				if (data.code == '99' || data.code == '-6') {
					layer.close(_load);
					layer.msg(data.message, 2, 5);
					loadimage();
				}else{
					layer.close(_load);
					layer.alert("此次交易成功转入"+$("#tenderMoney").val()+"元（"+numToChinese($("#tenderMoney").val())+"）"+"到活期宝" ,1, "温馨提示",function(){
						window.location.href="${path}/curInController.html";
		    		});
				}
		   },
		   error:function (result){
				layer.close(_load);
				layer.msg('网络连接超时,请刷新页面或稍后重试!', 2, 5);
		   }
	   });
   	}
	
	/**
	 * 刷新验证码
	 */
	function loadimage() {
		document.getElementById("randImage").src = "${basePath}/random.jsp?"
				+ Math.random();
		$("#checkCode").val("");
	}
	
	/**
	 * 点击tab触发事件
	 */
	function changeTab(objName, _url) {
		$.ajax({
			url : _url,
			data : {},
			type : 'post',
			dataType : 'html',
			success : function(data) {
				var obj = $('#' + objName);
				obj.attr("class", 'selected');
				obj.prevAll().removeAttr("class");
				obj.nextAll().removeAttr("class");
				$("#curAccountTab").html(data);
			},
			error : function(data) {
				layer.msg('网络连接异常，请刷新页面或稍后重试！', 1, 5);
			}
		});
	}
	
	function numToChinese(Num){ 
		for(i=Num.length-1;i>=0;i--) { 
			Num = Num.replace(",","") 
			Num = Num.replace(" ","") 
		} 
		Num = Num.replace("￥","") 
		if(isNaN(Num)) { 
			layer.msg("金额格式不正确。", 2, 5);
			return; 
		} 
		part = String(Num).split("."); 
		newchar = ""; 
		for(i=part[0].length-1;i>=0;i--){ 
			tmpnewchar = "" 
			perchar = part[0].charAt(i); 
			switch(perchar){ 
			case "0": tmpnewchar="零" + tmpnewchar ;break; 
			case "1": tmpnewchar="壹" + tmpnewchar ;break; 
			case "2": tmpnewchar="贰" + tmpnewchar ;break; 
			case "3": tmpnewchar="叁" + tmpnewchar ;break; 
			case "4": tmpnewchar="肆" + tmpnewchar ;break; 
			case "5": tmpnewchar="伍" + tmpnewchar ;break; 
			case "6": tmpnewchar="陆" + tmpnewchar ;break; 
			case "7": tmpnewchar="柒" + tmpnewchar ;break; 
			case "8": tmpnewchar="捌" + tmpnewchar ;break; 
			case "9": tmpnewchar="玖" + tmpnewchar ;break; 
			} 
			switch(part[0].length-i-1){ 
			case 0: tmpnewchar = tmpnewchar +"元" ;break; 
			case 1: if(perchar!=0)tmpnewchar= tmpnewchar +"拾" ;break; 
			case 2: if(perchar!=0)tmpnewchar= tmpnewchar +"佰" ;break; 
			case 3: if(perchar!=0)tmpnewchar= tmpnewchar +"仟" ;break; 
			case 4: tmpnewchar= tmpnewchar +"万" ;break; 
			case 5: if(perchar!=0)tmpnewchar= tmpnewchar +"拾" ;break; 
			case 6: if(perchar!=0)tmpnewchar= tmpnewchar +"佰" ;break; 
			case 7: if(perchar!=0)tmpnewchar= tmpnewchar +"仟" ;break; 
			case 8: tmpnewchar= tmpnewchar +"亿" ;break; 
			case 9: tmpnewchar= tmpnewchar +"拾" ;break; 
		} 
			newchar = tmpnewchar + newchar; 
		} 
		if(Num.indexOf(".")!=-1){ 
			if(part[1].length > 2) { 
				part[1] = part[1].substr(0,2) 
			} 
			for(i=0;i<part[1].length;i++){ 
				tmpnewchar = "" 
				perchar = part[1].charAt(i) 
				switch(perchar){ 
				case "0": tmpnewchar="零" + tmpnewchar ;break; 
				case "1": tmpnewchar="壹" + tmpnewchar ;break; 
				case "2": tmpnewchar="贰" + tmpnewchar ;break; 
				case "3": tmpnewchar="叁" + tmpnewchar ;break; 
				case "4": tmpnewchar="肆" + tmpnewchar ;break; 
				case "5": tmpnewchar="伍" + tmpnewchar ;break; 
				case "6": tmpnewchar="陆" + tmpnewchar ;break; 
				case "7": tmpnewchar="柒" + tmpnewchar ;break; 
				case "8": tmpnewchar="捌" + tmpnewchar ;break; 
				case "9": tmpnewchar="玖" + tmpnewchar ;break; 
				} 
				if(i==0)tmpnewchar =tmpnewchar + "角"; 
				if(i==1)tmpnewchar = tmpnewchar + "分"; 
				newchar = newchar + tmpnewchar; 
			} 
		} 
		while(newchar.search("零零") != -1) 
		newchar = newchar.replace("零零", "零"); 
		newchar = newchar.replace("零亿", "亿"); 
		newchar = newchar.replace("亿万", "亿"); 
		newchar = newchar.replace("零万", "万"); 
		newchar = newchar.replace("零元", "元"); 
		newchar = newchar.replace("零角", ""); 
		newchar = newchar.replace("零分", ""); 
		if (newchar.charAt(newchar.length-1) == "元" || newchar.charAt(newchar.length-1) == "角") 
		newchar = newchar+"整" 
		return newchar; 
	}
	
	
</script>


</html>
