<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%> 
<%@ include file="/WEB-INF/page/common/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ include file="/WEB-INF/page/common/public.jsp"%>
<title>P2P小额贷款_个人融资_我要融资-顶玺金融官网</title>
<meta name="keywords" content="个人融资, P2P小额贷款,我要融资" />
<meta name="description" content="顶玺金融是中国3A信用评级互联网理财借贷平台，如果个人或中小企业需要融资，可登陆顶玺金融官网了解详情www.dxjr.com。">
<link href="${basePath}/css/borrow.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${basePath}/js/util.js"></script>
<script type="text/javascript" src="${basePath}/js/formValid.js"></script>
</head>
<body>
<%@ include file="/WEB-INF/page/common/header.jsp"%>
<!--内容开始-->
<form id="netValueForm" name="netValueForm" method="post">
<input type="password" style="display: none;" />
<div id="Container" >
	<div id="Bmain">
		<div class="title">
			<span class="home"><a href="${path}/">顶玺金融</a></span>
			<span><a href="javascript:void(0);"> 我要融资</a></span>
			<span><a href="javascript:void(0);"> 融资产品</a></span>
			<span>净值贷借款信息填写</span>
		</div>
		<div id="rz_main" style="height: 700px;">
			<div class="rztitle">借款信息</div>
			<div class="rz_borrow">
				<table>
					<tr>
						<td colspan="2" class="rz_borrowbg">我的净值额度：￥<fmt:formatNumber value="${netMoneyLimit }" pattern="#,##0.00"/></td>
					</tr>
				</table>
			</div>
			<div class="rz_borrow">
				<table>
					<tr>
						<td  width="100"><font class="needFlag">*</font>借款标题：</td>
						<td><input name="name" type="text" class="rz_input" dataType="Limit" min="1" max="100" msg="借款标题不能为空，且不能超过100个字符" maxlength="100"/></td>
					</tr>
				</table>
			</div>
			 
			<div class="rz_borrow1">
				<div class="rz_type1"
					style="margin: 10px 0; border-top: 1px solid #dbdbdb;">
					<div class="rz_ltype1">
						<table border="0">
							<tr height="35">
								<td class="rz_font"><font class="needFlag">*</font>借款金额(元)：</td>
								<td><input value="500" id="account" name="account" class="rz_input1" type="text" dataType="Money|Range" min="500" max="${netMoneyLimit }" msg="借款金额格式有误|借款金额不能少于500元,且不能大于您的净值额度" maxlength="14"/></td>
							</tr>
							<tr>
							
							<td class="rz_font"><font class="needFlag">*</font>还款方式：</td>
								<td>
									<select id="style" name="style" class="rz_selected" onchange="setTimeLimit()">
										<c:forEach items="${netValueOptions}" var="o">
											<option value="${o.name}"     <c:if test="${o.name==4}"> selected </c:if>         >${o.value}</option>
										</c:forEach>
									</select>
								</td>
								
							</tr>
							<tr>
								<td class="rz_font">最高投标金额：</td>
								<td><input id="mostAccount" name="mostAccount" class="rz_input1" type="text" require="false" dataType="Money|Range" msg="最高投标金额格式有误|最高投标金额不能小于最低投标金额，且不能大于借款金额" maxlength="16"/></td>
							</tr>
							<tr>
								
								<td class="rz_font"><font class="needFlag">*</font>借款期限：</td>
								<td>
									<select id="timeLimit" name="timeLimit" class="rz_selected" dataType="Require" msg="请选择借款期限">
										<c:forEach begin="1" end="1" var="o" step="1">
											<option value="${o }">${o }个月</option>
										</c:forEach>
									</select>
									<input id="timeLimitDay" name="timeLimitDay" class="rz_input1" require="false"   placeholder="借款期限必须为：1到30天"     dataType="Integer|Range" min="1" max="30" maxlength="2" msg="借款期限只能为整数|借款期限必须在1~30天内" style="display:none; "/>
								</td>
								
								
								
							</tr>
						</table>
					</div>
					<div class="rz_ltype1">
						<table border="0">
							<tr>
								<td class="rz_font"><font class="needFlag">*</font>年化利率(%)：</td>
								<td><input value="6" name="apr" class="rz_input1" type="text" dataType="Range|Money" min="6" max="24" msg="年化利率必须在6~24之间|年化利率格式有误"/></td>
							</tr>
							<tr>
								<td class="rz_font"><font class="needFlag">*</font>最低投标金额：</td>
								<td><input value="50" id="lowestAccount" name="lowestAccount" class="rz_input1" type="text" dataType="Money|Range" min="50" msg="最低投标金额格式有误|最低投标金额不能少于50元，且不能大于借款金额"/></td>
							</tr>
							<%-- <tr>
								<td class="rz_font"><font class="needFlag">*</font>借款用途：</td>
								<td><input name="borrowUse" class="rz_input1" type="text" dataType="Limit" min="1" max="100" msg="借款用途不能为空，且不能超过100个字符"/></td>
							</tr> --%>
							<tr>
								<td class="rz_font"><font class="needFlag">*</font>有效期限(天)：</td>
								<td>
									<input value="3" name="validTime" class="rz_input1" type="text" dataType="Integer|Range" min="1" max="3" msg="有效期限只能为整数|有效期限必须在1~3天内" maxlength="1" />
								</td>
							</tr>
							<tr>
								<td class="rz_font">定向密码：</td>
								<td><input name="bidPassword" class="rz_input1" type="password" require="false" dataType="Character|Limit" min="6" max="12" msg="投标密码必须为字母或数字|投标密码长度必须在6~12位之间" maxlength="12"/></td>
							</tr>
						</table>
					</div>
				</div>
				<div class="rz_borrow">
				<table>
					<tr>
						<td width="75"><font class="needFlag">*</font>验证码：</td>
						<td>
							<input type="text" name="checkcode" id="checkcode" maxlength="4" dataType="Require" msg="请输入验证码" class="rz_input1" />
						</td>
						<td style="padding-left: 10px;">
							<a href="javascript:loadimage();" id="captchaImage"> 
								<img name="randImage" id="randImage" src="${basePath}/random.jsp" style="float: left;" border="0" align="middle" /> 
							</a>
						</td>
					</tr>
				</table>
				</div>
				<div class="rz_borrow">
					<div class="rz_btn">
						<a id="netValueBtn" href="javascript:netValueFormSubmit();">提交申请</a>
					</div>
				</div>
			</div>

		</div>
	</div>

</div>
</form>
<!--内容结束-->
<%@ include file="/WEB-INF/page/common/footer.jsp"%>

<script type="text/javascript">
/**
 * 初始化
 */
$(document).ready(function(){
	setTimeLimit();
});

/**
 * 显示借款期限
 */
function setTimeLimit(){
	var way = $("#style").val();
	if(way==4){//按天
		displaySet('timeLimitDay-block|timeLimit-none');
		attrSet('timeLimitDay-true|timeLimit-false','require');
	}else{
		displaySet('timeLimitDay-none|timeLimit-block');
		attrSet('timeLimitDay-false|timeLimit-true','require');
		$("#timeLimitDay").val('');
	}
}

/**
 * 表单提交
 */
function netValueFormSubmit(){
	document.getElementById('netValueBtn').href = 'javascript:void(0);';
	//验证
	attrSet('mostAccount-min-'+$("#lowestAccount").val()+'|mostAccount-max-'+$("#account").val()+'|lowestAccount-max-'+$("#account").val());
	if (Validator.Validate('netValueForm',1)==false) {
		document.getElementById('netValueBtn').href = 'javascript:netValueFormSubmit();';
		return;
	}
	
	$("#netValueForm").ajaxSubmit({
		url : '${basePath}/rongzi/getUserNetMoneyLimitForFullBorrow.html',
		type : 'post',
		dataType :"json",
		success : function(result) {
			if (result.code == '1') {
				//提交
				layer.confirm(result.message,function(){
					var _load = layer.load('处理中..');
					$("#netValueForm").ajaxSubmit({
						url : '${basePath}/rongzi/applyNetValueBorrow.html',
						type : 'post',
						dataType :"json",
						success : function(result) {
							document.getElementById('netValueBtn').href = 'javascript:netValueFormSubmit();';
							layer.close(_load);
							if (result.code == '0') {
								parent.layer.alert(result.message);
								loadimage();
							}else if(result.code == '-99'){
								if(result.message != ''){
									parent.layer.msg(result.message, 1, 5/* , function() {
										window.location='${path}/global/borrow/borrowProduct.html';
									} */);
								}
							}else{
								parent.layer.msg(result.message, 1, 1/* , function() {
									window.location='${path}/global/borrow/borrowProduct.html';
								} */);
							}
							
						},
						error : function(result) {
							document.getElementById('netValueBtn').href = 'javascript:netValueFormSubmit();';
							layer.close(_load);
							layer.msg('网络连接超时,请您稍后重试.', 1, 5);
					    },end:function(){
					    	document.getElementById('netValueBtn').href = 'javascript:netValueFormSubmit();';
					    }
					});
				},'',function(){
					document.getElementById('netValueBtn').href = 'javascript:netValueFormSubmit();';
				});
			}else{
				if(result.message != ''){
					parent.layer.msg(result.message, 1, 5/* , function() {
						window.location='${path}/global/borrow/borrowProduct.html';
					} */);
				}
			}
		},
		error : function(result) {
			document.getElementById('netValueBtn').href = 'javascript:netValueFormSubmit();';
			layer.close(_load);
			layer.msg('网络连接超时,请您稍后重试.', 1, 5);
	    },end:function(){
	    	document.getElementById('netValueBtn').href = 'javascript:netValueFormSubmit();';
	    }
	},'',function(){
		document.getElementById('netValueBtn').href = 'javascript:netValueFormSubmit();';
	});
	document.getElementById('netValueBtn').href = 'javascript:netValueFormSubmit();';
}
/**
 * 刷新验证码 
 */
function loadimage() {
	document.getElementById("randImage").src = "${basePath}/random.jsp?" + Math.random();
}
</script>
</body>
</html>