<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%> 
<%@ include file="/WEB-INF/page/common/taglib.jsp"%> 
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ include file="/WEB-INF/page/common/public.jsp"%>
<%@ include file="/WEB-INF/page/common/ueditor.jsp"%>
<link href="${basePath}/css/borrow.css" rel="stylesheet" type="text/css"/>
<script type="text/javascript" src="${basePath}/js/util.js"></script>
<script type="text/javascript" src="${basePath}/js/formValid.js"></script>
<title>我要融资-诚商贷借款信息填写_顶玺金融</title>
</head>

<body>
<%@ include file="/WEB-INF/page/common/header.jsp"%>
<!--内容开始-->
<form id="commerceForm" name="commerceForm" method="post">
<input type="password" style="display: none;" />
<div id="Container">
<div id="Bmain">

	<div class="title">
		<span class="home"><a href="${path}/">顶玺金融</a></span>
		<span><a href="javascript:void(0);"> 我要融资</a></span>
		<span><a href="javascript:void(0);"> 融资产品</a></span>
		<span><a href="">诚商贷借款信息填写</a></span>
	</div>
                           
	<!-- 借款信息 -->
	<div id="rz_main" style="height: 680px;">
		<div class="rztitle">借款信息</div>
		<div class="rz_borrow">
			<table>
				<tr>
					<td width="100"><font class="needFlag">*</font>借款标题：</td>
					<td><input name="name" type="text" class="rz_input" dataType="Limit" min="1" max="100" msg="借款标题不能为空，且不能超过100个字符" maxlength="100"/></td>
				</tr>
				<tr><td colspan="2">&nbsp;</td></tr>
				<tr>
					<td width="100"><font class="needFlag">*</font>借款标编号：</td>
					<td><input name="contractNo" class="rz_input" type="text" dataType="Limit" min="1" max="30" msg="借款单编号不能为空，且不能超过30个字符"/></td>
				</tr>
			</table>
		</div>
		<div class="rz_borrow">
			<div class="rz_txt" style="width: 100px;"><font class="needFlag">*</font>借款介绍：</div>
			<div class="rz_textarea" style="width: 850px;">
				<textarea name="content" id="content" cols="90" rows="8" class="textarea" dataType="Limit" min="1" max="3000" msg="借款介绍不能为空，且不能超过3000个字符" ></textarea>
			</div>
		</div>
		<div class="rz_borrow">
			<span><font class="needFlag">*</font>&nbsp;&nbsp;是否抵押：</span> 
			<input id="isMortgage" name="isMortgage" type="radio" value="1" checked="checked" onclick="displaySet('mortgageDiv|mortgageTypeDiv','block');showRepayWays();"/>有抵押&nbsp;&nbsp; 
			<input name="isMortgage" type="radio" value="0" onclick="displaySet('mortgageDiv|mortgageTypeDiv','none');showRepayWays();"/>无抵押
			
			<input type="hidden" id="businessUserId" name="businessUserId" value="0"/>
		</div>
		<div class="rz_borrow1" >
			<div class="rz_type">
				<div id="mortgageTypeDiv" class="rz_ltype">
					<span>抵押类型：</span> 
					<input name="mortgageType" type="radio" value="1" checked="checked" />房抵&nbsp;&nbsp;
					<input name="mortgageType" type="radio" value="2" />车抵&nbsp;&nbsp; 
					<input name="mortgageType" type="radio" value="3" />民品抵
				</div>
				<div class="rz_ltype">
					<span><input id="isGuarantyCB" name="isGuarantyCB" type="checkbox" onclick="showRepayWays();" /></span>是否机构担保 
					<select id="guarantyOrganization" name="guarantyOrganization" msg="请选择担保机构">
						<option value="">--请选择担保机构--</option>
						<c:forEach items="${organizationOptions }" var="o">
							<option value="${o.id }">${o.name }</option>
						</c:forEach>
					</select>
				</div>
			</div>
			<div class="rz_type1">
				<div class="rz_ltype1">
					<table border="0">
						<tr height="35">
							<td class="rz_font"><font class="needFlag">*</font>借款金额(元)：</td>
							<td><input id="account" name="account" class="rz_input1" type="text" dataType="Money|Compare" to="500" operator="GE" msg="借款金额格式有误|借款金额不能少于500元" maxlength="16"/></td>
						</tr>
						<tr>
							<td class="rz_font"><font class="needFlag">*</font>借款期限：</td>
							<td>
								<input id="timeLimit" name="timeLimit" class="rz_input1" require="true"   placeholder="借款期限必须为：1到99"     dataType="Integer|Range" min="1" max="99"  maxlength="2" msg="借款期限只能为整数|借款期限必须在1~99内" />
							</td>
						</tr>
						<tr>
							<td class="rz_font">最高投标金额(元)：</td>
							<td><input id="mostAccount" name="mostAccount" class="rz_input1" type="text" require="false" dataType="Money|Range" msg="最高投标金额格式有误|最高投标金额不能小于最低投标金额，且不能大于借款金额" maxlength="16"/></td>
						</tr>
						<tr>
							<td class="rz_font"><font class="needFlag">*</font>还款方式：</td>
							<td>
								<div id="repaymentOptionsDiv">
									<select id="repaymentSel" name="style" class="rz_selected">
										<c:forEach items="${repaymentStyleOptions}" var="o">
											<option value="${o.name}">${o.value}</option>
										</c:forEach>
									</select>
								</div>
							</td>
						</tr>
						<tr>
							<td class="rz_font">定向密码：</td>
							<td><input id="bidPassword" name="bidPassword" class="rz_input1" type="password" require="false" dataType="Character|Limit" min="6" max="12" msg="投标密码必须为字母或数字|投标密码长度必须在6~12位之间" maxlength="12"/></td>
						</tr>
					</table>
				</div>
				<div class="rz_ltype1">
					<table border="0">
						<tr>
							<td class="rz_font"><font class="needFlag">*</font>年化利率(%)：</td>
							<td><input name="apr" value="6" class="rz_input1" type="text" dataType="Range|Money" min="6" max="24" msg="年化利率必须在6~24之间|年化利率格式有误"/></td>
						</tr>
						<tr>
							<td class="rz_font"><font class="needFlag">*</font>最低投标金额(元)：</td>
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
							<td class="rz_font"><font class="needFlag">*</font>标的类型：</td>
							<td>
								<select name="pledgeType" id="pledgeType" class="rz_selected">
									<option value="1">新增</option>
									<option value="2">续贷</option>
									<option value="3">资产处理</option>						
								</select>
							</td>
						</tr>
					</table>
				</div>
			</div>
		</div>
	</div>
	
	<!-- 资产信息 -->
	<div id="mortgageDiv" class="rz_main1">
		<div class="rztitle">资产信息</div>
		<div class="rz_borrow1">
			<div class="rz_type1" style="border: none;">

				<div class="rz_ltype1">
					<table border="0" >
						<tr>
							<td class="rz_font"><span>是否拥有房产：</span></td>
							<td>
								<input id="hasHouse" name="hasHouse" type="radio" value="1" checked="checked" onclick="displaySet('houseLocationTr|hasHouseMortgageTr|houseAreaTr','table-row');"/> 是&nbsp;&nbsp;
								<input name="hasHouse" type="radio" value="0" onclick="displaySet('houseLocationTr|hasHouseMortgageTr|houseAreaTr','none');"/> 否&nbsp;&nbsp;
							</td>
						</tr>
						<tr id="houseLocationTr">
							<td class="rz_font">房产位置：</td>
							<td><input id="houseLocation" name="houseLocation" class="rz_input1" type="text" maxlength="100" /></td>
						</tr>
						<tr>
							<td class="rz_font"><span>是否拥有车产：</span></td>
							<td>
								<input id="hasCar" name="hasCar" type="radio" value="1" checked="checked" onclick="displaySet('carNoTr|hasCarMortgageTr|carValueTr','table-row');"/> 是&nbsp;&nbsp;
								<input name="hasCar" type="radio" value="0" onclick="displaySet('carNoTr|hasCarMortgageTr|carValueTr','none');"/> 否&nbsp;&nbsp;
							</td>
						</tr>
						<tr id="carNoTr">
							<td class="rz_font">车辆型号：</td>
							<td><input id="carNo" name="carNo" class="rz_input1" type="text" maxlength="100"/></td>
						</tr>
					</table>
				</div>
				<div class="rz_ltype1">
					<table border="0">
						<tr id="hasHouseMortgageTr">
							<td class="rz_font"><span>是否拥有房贷：</span></td>
							<td>
								<input name="hasHouseMortgage" type="radio" value="1" checked="checked" /> 是&nbsp;&nbsp;
								<input name="hasHouseMortgage" type="radio" value="0" /> 否&nbsp;&nbsp;
							</td>
						</tr>
						<tr id="houseAreaTr">
							<td class="rz_font">房产面积：</td>
							<td><input id="houseArea" name="houseArea" class="rz_input1" type="text" maxlength="100"/></td>
						</tr>
						<tr id="hasCarMortgageTr">
							<td class="rz_font"><span>是否拥有车贷：</span></td>
							<td>
								<input name="hasCarMortgage" type="radio" value="1" checked="checked" /> 是&nbsp;&nbsp;
								<input name="hasCarMortgage" type="radio" value="0" /> 否&nbsp;&nbsp;
							</td>
						</tr>
						<tr id="carValueTr">
							<td class="rz_font">车辆价值：</td>
							<td><input id="carValue" name="carValue" class="rz_input1" type="text" maxlength="100"/></td>
						</tr>
					</table>

				</div>
			</div>
		</div>
	</div>
	
	<!-- 个人信息 -->
	<div class="rz_main1" style="height: 322px;">
		<div class="rztitle">个人信息</div>
		<div class="rz_borrow1">
			<div class="rz_type1" style="border: none">
				<div class="rz_ltype1">
					<table border="0">
						<tr height="35">
							<td class="rz_font">用户名：</td>
							<td>${loginMemName}</td>
						</tr>
						<tr>
							<td class="rz_font">出生日期：</td>
							<td>${loginMemBirthDay}</td>
						</tr>
						<tr>
							<td class="rz_font"><font class="needFlag">*</font>婚姻状况：</td>
							<td>
								<c:forEach items="${maritalStatusOptions}" var="o" varStatus="n">
									<input name="maritalStatus" type="radio" value="${o.name }" <c:if test="${n.count==1 }" >checked="checked"</c:if>/>${o.value }&nbsp;&nbsp; 
								</c:forEach>
							</td>
						<tr>
							<td class="rz_font"><font class="needFlag">*</font>公司行业：</td>
							<td><input name="industry" class="rz_input1" type="text" dataType="Limit" min="1" max="100" msg="公司行业不能为空，且不能超过100个字符"/></td>
						</tr>
						<tr>
							<td class="rz_font"><font class="needFlag">*</font>岗位职位：</td>
							<td><input name="jobTitle" class="rz_input1" type="text" dataType="Limit" min="1" max="100" msg="岗位职位不能为空，且不能超过100个字符"/></td>
						</tr>
						<tr>
							<td class="rz_font"><font class="needFlag">*</font>工作时间：</td>
							<td>
								<select name="workYears" class="rz_selected" dataType="Require" msg="请选择工作时间">
									<option value="">--请选择--</option>
									<c:forEach items="${workYearsOptions}" var="o">
										<option value="${o.name}">${o.value}</option>
									</c:forEach>
								</select>
							</td>
						</tr>
					</table>
				</div>
				<div class="rz_ltype1">
					<table border="0">
						<tr>
							<td class="rz_font">性别：</td>
							<td>${loginMemGender }</td>
						</tr>
						<tr>
							<td class="rz_font">籍贯：</td>
							<td>${loginMemNativePlace }</td>
						</tr>
						<tr>
							<td class="rz_font"><font class="needFlag">*</font>学历：</td>
							<td>
								<select name="education" class="rz_selected" dataType="Require" msg="请选择学历">
									<option value="">--请选择--</option>
									<c:forEach items="${educationOptions}" var="o">
										<option value="${o.name}">${o.value}</option>
									</c:forEach>
								</select>
							</td>
						</tr>
						<tr>
							<td class="rz_font"><font class="needFlag">*</font>公司规模：</td>
							<td>
								<select name="scale" class="rz_selected" dataType="Require" msg="请选择公司规模">
									<option value="">--请选择--</option>
									<c:forEach items="${scaleOptions}" var="o">
										<option value="${o.name}">${o.value}</option>
									</c:forEach>
								</select>
							</td>
						</tr>
						<tr>
							<td class="rz_font"><font class="needFlag">*</font>工作城市：</td>
							<td><input name="workCity" class="rz_input1" type="text" dataType="Require" min="1" max="100" msg="工作城市不能为空，且不能超过100个字符"/></td>
						</tr>
					</table>
				</div>
			</div>
		</div>
	</div>

	<!-- 连带担保 -->
	<div class="rz_main1" style="height: 382px;">
		<div class="rztitle">连带担保</div>
		<div class="rz_borrow1">
			<div class="rz_type1" style="border: none">
				<div class="rz_ltype1">
					<table border="0">
						<tr>
							<td width="200" align="right">是否有连带担保物或担保人：</td>
							<td>
								<input name="isJointGuaranty" type="radio" value="1" onclick="displaySet('jointGuarantyDiv-block');attrSet('jointGuaranty-Limit','dataType');" checked="checked" /> 有&nbsp;&nbsp; 
								<input name="isJointGuaranty" type="radio" value="0" onclick="displaySet('jointGuarantyDiv-none');attrSet('jointGuaranty-','dataType');" /> 无&nbsp;&nbsp; 
							</td>
						</tr>
					</table>
					<div id="jointGuarantyDiv" class="rz_borrow" style="margin:10px 0 0 19px">
                         <div class="rz_txt">担保信息：</div>
                         <div class="rz_textarea1"><textarea id="jointGuaranty" name="jointGuaranty" cols="110" rows="4" class="textarea" require="false" dataType="Limit" min="1" max="1000" msg="担保信息不能超过1000个字符"></textarea></div>
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
							<a href="javascript:loadimage();"> 
								<img name="randImage" id="randImage" src="${basePath}/random.jsp" style="float: left;" border="0" align="middle" /> 
							</a>
						</td>
					</tr>
				</table>
				</div>
				<div class="rz_borrow" style="margin: 40px 0">
					<div class="rz_btn">
						<a id="commerceBtn" href="javascript:commerceFormSubmit();">申请并继续</a>
					</div>
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
    showRepayWays();
    if("checked"!=$('#isMortgage').attr('checked')){
    	displaySet('mortgageDiv|mortgageTypeDiv','none');
    }
});

/**
 * 显示还款方式,担保机构控制
 */
function showRepayWays(){
	if("checked"==$('#isGuarantyCB').attr('checked')){
		attrSet('guarantyOrganization-Require','dataType');
		document.getElementById("guarantyOrganization").disabled=false;
	}else{
		document.getElementById("guarantyOrganization").disabled=true; 
		attrSet('guarantyOrganization-','dataType');
	}
}


/**
 * 表单提交
 */
function commerceFormSubmit(){
	//验证
	attrSet('mostAccount-min-'+$("#lowestAccount").val()+'|mostAccount-max-'+$("#account").val()+'|lowestAccount-max-'+$("#account").val());
	if (Validator.Validate('commerceForm',1)==false) return;
	
	//提交
	layer.confirm("确定提交吗？",function(){
		var _load = layer.load('处理中..');
		$("#commerceForm").ajaxSubmit({
			url : '${basePath}/commerceBorrow/applyCommerceBorrow.html',
			type : 'post',
			dataType :"json",
			success : function(result) {
				layer.close(_load);
				if (result.code == '0') {
					parent.layer.alert(result.message);
					loadimage();
				}else{
					var codeAry = result.code.split('|');
					parent.layer.msg(result.message, 1, 1, function() {
						//去上传资料的页面
						window.parent.location.href = '${path}/salariatBorrow/initUpload.html?borrowId='+codeAry[1];
					});
				}
				
			},
			error : function(result) {
				layer.close(_load);
				layer.msg('网络连接超时,请您稍后重试.', 1, 5);
		    }
		});
	});
}

/**
 * 刷新验证码 
 */
function loadimage() {
	document.getElementById("randImage").src = "${basePath}/random.jsp?" + Math.random();
}

var um = UM.getEditor('content', {
	initialFrameWidth:1047,
	initialFrameHeight:120
});
</script>
</body>
</html>