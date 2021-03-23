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
<title>我要融资-借款信息填写更改_顶玺金融</title>
</head>

<body>
<%@ include file="/WEB-INF/page/common/header.jsp"%>
<!--内容开始-->
<form id="salariatForm" name="salariatForm" method="post">
<input type="password" style="display: none;" />
<div id="Container">
<div id="Bmain">

	<div class="title">
		<span class="home"><a href="${path}/">顶玺金融</a></span>
		<span><a href="${path }/myaccount/toIndex.html">我的账户</a></span>
		<span>融资管理</span>
		<span><a href="${path }/myaccount/borrowList.html">融资列表</a></span>
		<span>借款信息修改</span>
	</div>
	
	<!-- 借款信息 -->
	<div id="rz_main" >
		<div class="rztitle">借款信息</div>
		<div class="rz_borrow">
			<table>
				<tr>
					<td width="100"><font class="needFlag">*</font>借款标题：</td>
					<td><input value="${b.name }" name="name" type="text" class="rz_input" dataType="Limit" min="1" max="100" msg="借款标题不能为空，且不能超过100个字符" maxlength="100"/></td>
				</tr>
				<tr><td colspan="2">&nbsp;</td></tr>
				<tr>
					<td width="100">借款标编号：</td>
					<td>${b.contractNo }</td>
				</tr>
			</table>
		</div>
		<div class="rz_borrow">
			<div class="rz_txt" style="width: 100px;"><font class="needFlag">*</font>借款介绍：</div>
			<div class="rz_textarea" style="width: 850px;">
				<textarea name="content" id="content" cols="90" rows="8" class="textarea" dataType="Limit" min="1" max="3000" msg="借款介绍不能为空，且不能超过3000个字符" >${b.content }</textarea>
			</div>
		</div>		
		<div class="rz_borrow">
			借款标类型：${portal:desc(300, b.borrowtype)}
			 
			<input type="hidden" name="businessUserId" value="${borrowBusinessVo.userId}"/>
		</div>
		<div class="rz_borrow1">
			<div class="rz_type">
				<div id="mortgageTypeDiv" class="rz_ltype">
					<span>抵押类型：</span>
					<c:if test="${b.mortgageType == 1 }">房抵</c:if>
					<c:if test="${b.mortgageType == 2 }">车抵</c:if>
					<c:if test="${b.mortgageType == 3 }">民品抵</c:if>
					<c:if test="${b.mortgageType == null }">无</c:if>&nbsp;&nbsp;
				</div>
				<div class="rz_ltype">
					担保机构：	<c:if test="${g.name == null }">无</c:if>${g.name }
				</div>
			</div>
			<div class="rz_type1">
				<div class="rz_ltype1">
					<table border="0">
						<tr height="35">
							<td class="rz_font" width="40%">借款金额(元)：</td>
							<td  width="60%">${b.account }</td>
						</tr>
						<tr>
							<td class="rz_font">借款期限(<c:if test="${b.style == 4 }">天</c:if><c:if test="${b.style != 4 }">月</c:if>)：</td>
							<td>${b.timeLimit }</td>
						</tr>
						<tr>
							<td class="rz_font">最高投标金额(元)：</td>
							<td><c:if test="${b.mostAccount <= 0 }">${b.account }</c:if><c:if test="${b.mostAccount > 0 }">${b.mostAccount }</c:if></td>
						</tr>
						<tr>
							<td class="rz_font">还款方式：</td>
							<td>
								<c:if test="${b.style == 1 }">等额本息</c:if>
								<c:if test="${b.style == 2 }">按月付息到期还本</c:if>
								<c:if test="${b.style == 3 }">到期还本付息</c:if>
								<c:if test="${b.style == 4 }">按天还款</c:if>
							</td>
						</tr>
					</table>
				</div>
				<div class="rz_ltype1">
					<table border="0">
						<tr>
							<td class="rz_font"  width="40%">年化利率(%)：</td>
							<td  width="60%">${b.apr }</td>
						</tr>
						<tr>
							<td class="rz_font">最低投标金额(元)：</td>
							<td>${b.lowestAccount }</td>
						</tr>
						<tr>
							<td class="rz_font">有效期限(天)：</td>
							<td>${b.validTime }</td>
						</tr>
						<tr>
							<td class="rz_font">标的类型：</td>
							<td>
								<c:if test="${b.pledgeType == 1 }">新增</c:if>
								<c:if test="${b.pledgeType == 2 }">续贷</c:if>
								<c:if test="${b.pledgeType == 3 }">资产处理</c:if>
							</td>
						</tr>
					</table>
				</div>
			</div>
		</div>
	</div>
	
	<!-- 资产信息 -->
	<div id="mortgageDiv" class="rz_main1" <c:if test="${b.isMortgage==0 }">style="display: none;"</c:if>>
		<div class="rztitle">资产信息</div>
		<div class="rz_borrow1">
			<div class="rz_type1" style="border: none;">

				<div class="rz_ltype1">
					<table border="0" >
						<tr>
							<td class="rz_font"><span>是否拥有房产：</span></td>
							<td>
								<input id="hasHouse" name="hasHouse" type="radio" value="1" <c:if test="${m.hasHouse==1 }">checked="checked"</c:if> onclick="displaySet('houseLocationTr|hasHouseMortgageTr|houseAreaTr','table-row');"/> 是&nbsp;&nbsp;
								<input name="hasHouse" type="radio" value="0" <c:if test="${m.hasHouse==0 }">checked="checked"</c:if> onclick="displaySet('houseLocationTr|hasHouseMortgageTr|houseAreaTr','none');"/> 否&nbsp;&nbsp;
							</td>
						</tr>
						<tr id="houseLocationTr">
							<td class="rz_font">房产位置：</td>
							<td><input value="${m.houseLocation }" id="houseLocation" name="houseLocation" class="rz_input1" type="text" maxlength="100" /></td>
						</tr>
						<tr>
							<td class="rz_font"><span>是否拥有车产：</span></td>
							<td>
								<input id="hasCar" name="hasCar" type="radio" value="1" <c:if test="${m.hasCar==1 }">checked="checked"</c:if> onclick="displaySet('carNoTr|hasCarMortgageTr|carValueTr','table-row');"/> 是&nbsp;&nbsp;
								<input name="hasCar" type="radio" value="0" <c:if test="${m.hasCar==0 }">checked="checked"</c:if> onclick="displaySet('carNoTr|hasCarMortgageTr|carValueTr','none');"/> 否&nbsp;&nbsp;
							</td>
						</tr>
						<tr id="carNoTr">
							<td class="rz_font">车辆型号：</td>
							<td><input value="${m.carNo }" id="carNo" name="carNo" class="rz_input1" type="text" maxlength="100"/></td>
						</tr>
					</table>
				</div>
				<div class="rz_ltype1">
					<table border="0">
						<tr id="hasHouseMortgageTr">
							<td class="rz_font"><span>是否拥有房贷：</span></td>
							<td>
								<input name="hasHouseMortgage" type="radio" value="1" <c:if test="${m.hasHouseMortgage==1 }">checked="checked"</c:if> /> 是&nbsp;&nbsp;
								<input name="hasHouseMortgage" type="radio" value="0" <c:if test="${m.hasHouseMortgage==0 }">checked="checked"</c:if>/> 否&nbsp;&nbsp;
							</td>
						</tr>
						<tr id="houseAreaTr">
							<td class="rz_font">房产面积：</td>
							<td><input value="${m.houseArea }" id="houseArea" name="houseArea" class="rz_input1" type="text" maxlength="100"/></td>
						</tr>
						<tr id="hasCarMortgageTr">
							<td class="rz_font"><span>是否拥有车贷：</span></td>
							<td>
								<input name="hasCarMortgage" type="radio" value="1" <c:if test="${m.hasCarMortgage==1 }">checked="checked"</c:if> /> 是&nbsp;&nbsp;
								<input name="hasCarMortgage" type="radio" value="0" <c:if test="${m.hasCarMortgage==0 }">checked="checked"</c:if>/> 否&nbsp;&nbsp;
							</td>
						</tr>
						<tr id="carValueTr">
							<td class="rz_font">车辆价值：</td>
							<td><input value="${m.carValue }" id="carValue" name="carValue" class="rz_input1" type="text" maxlength="100"/></td>
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
									<input name="maritalStatus" type="radio" value="${o.name }" <c:if test="${ber.maritalStatus==o.name }" >checked="checked"</c:if>/>${o.value}&nbsp;&nbsp; 
								</c:forEach>
							</td>
						<tr>
							<td class="rz_font"><font class="needFlag">*</font>公司行业：</td>
							<td><input value="${ber.industry }" name="industry" class="rz_input1" type="text" dataType="Limit" min="1" max="100" msg="公司行业不能为空，且不能超过100个字符"/></td>
						</tr>
						<tr>
							<td class="rz_font"><font class="needFlag">*</font>岗位职位：</td>
							<td><input value="${ber.jobTitle }" name="jobTitle" class="rz_input1" type="text" dataType="Limit" min="1" max="100" msg="岗位职位不能为空，且不能超过100个字符"/></td>
						</tr>
						<tr>
							<td class="rz_font"><font class="needFlag">*</font>工作时间：</td>
							<td>
								<select name="workYears" class="rz_selected" dataType="Require" msg="请选择工作时间">
									<option value="">--请选择--</option>
									<c:forEach items="${workYearsOptions}" var="o">
										<option value="${o.name}" <c:if test="${ber.workYears==o.name }" >selected="selected"</c:if>>${o.value}</value>
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
										<option value="${o.name}" <c:if test="${ber.education==o.name }" >selected="selected"</c:if>>${o.value}</option>
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
										<option value="${o.name}" <c:if test="${ber.scale==o.name }" >selected="selected"</c:if>>${o.value}</option>
									</c:forEach>
								</select>
							</td>
						</tr>
						<tr>
							<td class="rz_font"><font class="needFlag">*</font>工作城市：</td>
							<td><input value="${ber.workCity }" name="workCity" class="rz_input1" type="text" dataType="Require" min="1" max="100" msg="工作城市不能为空，且不能超过100个字符"/></td>
						</tr>
					</table>
				</div>
			</div>
		</div>
	</div>

	<!-- 连带担保 -->
	<div class="rz_main1" style="height: 382px;" >
		<div class="rztitle">连带担保</div>
		<div class="rz_borrow1">
			<div class="rz_type1" style="border: none">
				<div class="rz_ltype1">
					<table border="0">
						<tr>
							<td width="200" align="right">是否有连带担保物或担保人：</td>
							<td>
								<input name="isJointGuaranty" type="radio" value="1" onclick="displaySet('jointGuarantyDiv-block');attrSet('jointGuaranty-Limit','dataType');" <c:if test="${b.isJointGuaranty == 1 }">checked="checked"</c:if> /> 有&nbsp;&nbsp; 
								<input name="isJointGuaranty" type="radio" value="0" onclick="displaySet('jointGuarantyDiv-none');attrSet('jointGuaranty-','dataType');" <c:if test="${b.isJointGuaranty == 0 }">checked="checked"</c:if> /> 无&nbsp;&nbsp; 
							</td>
						</tr>
					</table>
					<div id="jointGuarantyDiv" class="rz_borrow" style="margin:10px 0 0 19px">
                         <div class="rz_txt">担保信息：</div>
                         <div class="rz_textarea1"><textarea id="jointGuaranty" name="jointGuaranty" cols="110" rows="4" class="textarea" require="false" dataType="Limit" min="1" max="1000" msg="担保信息不能超过1000个字符">${b.jointGuaranty }</textarea></div>
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
						<a href="javascript:salariatFormSubmit();">提交修改</a>
					</div>
				</div>
			</div>
		</div>
	</div>

</div>
</div>
<input type="hidden" value="${b.id }" name="id" />
</form>
<!--内容结束-->
<%@ include file="/WEB-INF/page/common/footer.jsp"%>

<script type="text/javascript">

/**
 * 初始化
 */
$(document).ready(function(){
	if('${m.hasCar}'=='0'){
		displaySet('carNoTr|hasCarMortgageTr|carValueTr','none');
	}
	
	if('${m.hasHouse}'=='0'){
		displaySet('houseLocationTr|hasHouseMortgageTr|houseAreaTr','none');
	}
	
	if('${b.isJointGuaranty}'=='0'){
		displaySet('jointGuarantyDiv-none');attrSet('jointGuaranty-','dataType');
	}
	
});



/**
 * 表单提交
 */
function salariatFormSubmit(){
	//验证
	if (Validator.Validate('salariatForm',1)==false) return;
	
	//提交
	layer.confirm("确定提交吗？",function(){
		var _load = layer.load('处理中..');
		$("#salariatForm").ajaxSubmit({
			url : '${basePath}/salariatBorrow/editBorrow.html',
			type : 'post',
			dataType :"json",
			success : function(result) {
				layer.close(_load);
				if (result.code == '0') {
					parent.layer.alert(result.message);
					loadimage();
				}else{
					parent.layer.msg(result.message, 1, 1, function() {
						window.parent.location.href = '${path}/myaccount/borrowList.html';
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