<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/page/common/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ include file="/WEB-INF/page/common/public.jsp"%>
<title>实名认证_顶玺金融</title>
</head>

<body style="background:#f9f9f9;">
<!--头部开始-->
<%@ include file="/WEB-INF/page/common/header.jsp" %>
<!--头部结束-->

<!--安全中心左侧开始-->
<div id="Container">
<div id="Bmain">
    <div class="title">
    <span class="home"><a href="${path}/">顶玺金融</a></span>
    <span><a href="${path }/myaccount/toIndex.html">我的账户</a></span>
    <span>账户管理</span>
    <span><a href="${path }/AccountSafetyCentre/safetyIndex.html">安全中心</a></span>
    <span>实名认证 </span>
  	</div>
	<!--安全中心左侧 开始 -->    
	<%@ include file="/WEB-INF/page/account/myAccount_leftMenu.jsp"%> 
	<!--安全中心左侧 结束 -->    
	<!--安全中心右侧开始 --> 
	<div class="lb_waikuang whitebg border" >
		<form action=""  id="realNameform" name="realNameform" method="post" enctype="multipart/form-data">
        <div class="safetoptit">安全中心</div>    
        <div class="sf">
        <dl>
       	    <dd>&nbsp;
       	    <input type="hidden" name="id" id="id" value="${realNameAppro.id}"/>
 		    <input type="hidden" name="sex" id="sex" value="${realNameAppro.sex }"/>
       	    </dd>
            <dd><span>用户名：</span>${shiroUser.userName }</dd>
            <dd><span>真实姓名：</span><input type="text" name="realName" id="realName"></dd>
            <dd><span>性别：</span>
            <input type="radio" name="sexshow" value="0" id="sexMan"/>男
            <input type="radio" name="sexshow" value="1" id="sexWoman"/>女
            </dd>
            <dd><span>民族：</span> <input  type="text" name="nation" id="nation" value="${realNameAppro.nation }"/></dd>
            <dd><span>出生日期：</span><input type="text" name="birthDay" id="birthDay" onClick="WdatePicker()" readonly="readonly">&nbsp;&nbsp;示例格式：1987-01-01</dd>
            <dd><span>证件类型：</span>
	            <select name="cardType" id="cardType">
	       			<option value="身份证" <c:if test="${realNameAppro.cardType=='身份证' }">selected="selected"</c:if>>身份证</option>
	       			<option value="港澳通行证" <c:if test="${realNameAppro.cardType=='港澳通行证' }">selected="selected"</c:if>>港澳通行证</option>
	       			<option value="护照" <c:if test="${realNameAppro.cardType=='护照' }">selected="selected"</c:if>>护照</option>
	       			<option value="其它" <c:if test="${realNameAppro.cardType=='其它' }">selected="selected"</c:if>>其它</option>
	   			</select>
            </dd>
            <dd><span>证件号码：</span><input type="text" name="idCardNo" id="idCardNo" value="${realNameAppro.idCardNo }" style="width:150px;"></dd>
            <dd><span>籍贯：</span><input type="text" name="nativePlace" id="nativePlace" value="${realNameAppro.nativePlace }" maxlength="32"></dd>
            <dd><span>证件正面上传：</span>
            <c:if test="${realNameAppro.pic1!=null}">
            <img id="img_pic1" src="${path }${realNameAppro.pic1}" width="134" height="134"/>&nbsp;&nbsp;
            </c:if>
            <input type="file" name="files" id="PIC1" size="20" style="cursor:pointer;"/>
            &nbsp;&nbsp;
            </dd>
            <dd><span>证件背面上传：</span>
            <c:if test="${realNameAppro.pic2!=null}">
            <img id="img_pic2" src="${path }${realNameAppro.pic2}" width="134" height="134"/>&nbsp;&nbsp;
            </c:if>
            <input type="file" name="files" id="PIC2" size="20" class="input_border" style="cursor:pointer;"/>&nbsp;&nbsp;
            </dd>
            <dd><font style="color:red">说明：请上传后缀为jpg、jpeg、gif、png格式的图片，图片大小不要大于1MB。</font></dd>
        </dl>
        <input class="safe_button" type="button" id="subbtn" value="确认提交" onclick="forupload();"/>
        </div> 
        <hr class="hr clear"></hr>
        <div class="tipone fl"><span>温馨提示：</span>我们将严格对用户的所有资料进行保密。</div>
        </form>
      &nbsp;
     </div>
	<!--安全中心右侧结束 -->
</div>
</div>
<%@ include file="/WEB-INF/page/common/footer.jsp" %>
</body>
<script type="text/javascript">
$(document).ready(function(){
	//是否通过
	var isPassed = '${realNameAppro.isPassed}';
	if(!(null==isPassed || ""==isPassed || isPassed==-1)){
		hiddenSub();
	}
	
	<%--设置页面通过和不通过显示的格式--%>
	var sex = '${realNameAppro.sex}';
	//设置性别值
	if(sex == 1){
		$('#sexWoman').attr("checked","checked");
	}else{
		$('#sexMan').attr("checked","checked");
	}
	//真实姓名
	var realName = '${realNameAppro.realName}';
	if(isPassed == '1'){
		if(realName != "undefined" && realName != ""){
			var str1 = realName.substring(realName.length-1);
			var str2 = "";
			for(var i=0;i< realName.length-1;i++){
				str2 = str2 + "*";
			}
			$("#realName").val(str2+str1);
		}
		$("#PIC1_div").hide();
		$("#PIC2_div").hide();
	}else{
		if(realName != 'undefined' && realName != ''){
			$("#realName").val(realName);
		}
		//审核不通过时，隐藏图片，让用户从新上传！
		if(isPassed == '-1'){
			$("#img_pic1").hide();
			$("#img_pic2").hide();
		}

	}
	
	//出生日期
	var birthDay = '${realNameAppro.birthDay}';
	if(isPassed == '1'){
		if(birthDay != 'undefined' && birthDay != ''){
			var str1 = birthDay.substr(0,4);
			var str2 = " **-**";
			$("#birthDay").val(str1+str2);
		};
	}else{
		if(birthDay != 'undefined' && birthDay != ''){
			$("#birthDay").val(birthDay);
		}
	}
	
	//证件号码
	var idCardNo = '${realNameAppro.idCardNo}';
	if(isPassed == '1'){
		if(idCardNo != 'undefined' && idCardNo != ''){
			var str1 = idCardNo.substr(0,2);
			var str2 = "";
			for(var i=0; i<idCardNo.length-2;i++){
				str2 = str2 + "*";
			}
			$("#idCardNo").val(str1+str2);
		};
	}else{
		if(idCardNo != 'undefined' && idCardNo != ''){
			$("#idCardNo").val(idCardNo);
		}
	}
});
/**
 * 上传文件并提交表单
 */
function forupload(){
	$("#subbtn").removeAttr("onclick");
	//设置性别
	var temp=document.getElementsByName("sexshow");
	for (var i=0;i<temp.length;i++){
		//遍历Radio
		if(temp[i].checked){
			$('#sex').val(temp[i].value);
			break;
		}
	}
	//验证
	if(verify()){
		$("#realNameform").ajaxSubmit({
		    url : '${basePath}/account/approve/realname/saveOrUpdate.html',
		    type: "POST",
		    //dataType:'String',
		    success:function(result){
		    	if(result == 'success'){
		    		layer.alert("提交成功！" , 1, "温馨提示",function(){
		    			window.open("${path}/account/approve/realname/display.html","_self");
		    		});
		    	}else{
		    		layer.alert(result);
		    	}
		    	$("#subbtn").attr("onclick","forupload();");
		    },
			error : function() {
				layer.msg('网络连接超时，请您稍后重试', 1, 5);
				$("#subbtn").attr("onclick","forupload();");
		    } 
		 });
	}else{
		$("#subbtn").attr("onclick","forupload();");
	}
}
/**
 * 隐藏用户信息
 */
function hiddenSub(){
	$('#subbtn').hide();
	$('#realName').attr("disabled","disabled");
	$('#nation').attr("disabled","disabled");
	$('#birthDay').attr("disabled","disabled");
	$('#cardType').attr("disabled","disabled");
	$('#idCardNo').attr("disabled","disabled");
	$('#nativePlace').attr("disabled","disabled");
	$("input[name='sexshow']").attr("disabled","disabled");
	$('#PIC1').css("display","none");
	$('#PIC2').css("display","none");
}

/**
 * 显示用户详情
 */
function showMemberInfo(username){
	$("#memberInfoUsername").val(username);
	$("#showMemberInfoFrom").submit();
}
/**
 * 验证信息
 */
function verify(){
	var patten_realName = new RegExp(/^[\u0391-\uFFE5]+$/);
	var realName = $.trim($('#realName').val());
	if(realName==null || realName==""){
		layer.alert("请填写真实姓名。");
		return false;
	}else{
		$('#realName').val(realName);
	}
	var nation = $.trim($('#nation').val());
	if(nation==null || nation==""){
		layer.alert("请填写民族。");
		return false;
	}else{
		if(!patten_realName.test(nation)){
			layer.alert("民族必须为中文。");
			return false;
		}else{
			$('#nation').val(nation);
		}
	}
	var birthDay = $('#birthDay').val();
	if(birthDay==null || birthDay==""){
		layer.alert("请填写出生日期。");
		return false;
	}
	var cardType = $.trim($("#cardType").val());
	var idCardNo = $('#idCardNo').val();
	if(idCardNo==null || idCardNo==""){
		layer.alert("请填写证件号码。");
		return false;
	}else{
		$('#idCardNo').val(idCardNo);
	}
	if(cardType == '身份证'){
		var isIDCard1= /(^\d{15}$)|(^\d{17}([0-9]|X)$)/;
		var isIDCard2= /^(\d{6})(18|19|20)?(\d{2})([01]\d)([0123]\d)(\d{3})(\d|X)+$/;
		if(!(isIDCard1.test(idCardNo) || isIDCard2.test(idCardNo))){
			layer.alert("证件号码不是身份证号码");
			return false;
		}
		if(idCardNo.length==15){
			var birthDate = "19"+idCardNo.substr(6,6);
			if(birthDay.replace(/-/g, "") != birthDate){
				layer.alert("出生日期与证件号码不匹配");
				return false;
			}
		}
		if(idCardNo.length==18){
			var birthDate = idCardNo.substr(6,8);
			if(birthDay.replace(/-/g, "") != birthDate){
				layer.alert("出生日期与证件号码不匹配");
				return false;
			}
		}
	}else if(cardType == '港澳通行证'){
		if((idCardNo.length < 9) || (idCardNo.length > 11)){
			layer.alert("港澳通行证长度为9位或11位，且以字母开头。");
			return false;
		}
	}else if(cardType == '护照'){
		if((idCardNo.length < 7) || (idCardNo.length > 12)){
			layer.alert("护照长度为7到12位。");
			return false;
		}
	}
	
	var nativePlace = $.trim($('#nativePlace').val());
	if(nativePlace==null || nativePlace==""){
		layer.alert("请填写籍贯。");
		return false;
	}else{
		if(!patten_realName.test(nativePlace)){
			layer.alert("籍贯必须为中文。");
			return false;
		}else{
			$('#nativePlace').val(nativePlace);
		}
	} 
	var pic1 = $('#PIC1').val();
	if(pic1.length==0){
		layer.alert("请选择证件正面照片。");
		return false;
	}
	var pic2 = $('#PIC2').val();
	if(pic2.length==0){
		layer.alert("请选择证件背面照片。");
		return false;
	}

	return true;
}
</script>
</html>
