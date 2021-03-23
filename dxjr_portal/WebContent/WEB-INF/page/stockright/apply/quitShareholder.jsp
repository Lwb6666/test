<%@page import="com.dxjr.utils.DateUtils"%>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/page/common/taglib.jsp"%>
<!doctype html>
<html>
<head>
<%@ include file="/WEB-INF/page/stockright/common/public.jsp"%>
<meta charset="utf-8">
<title>我的内转账户_退出内转名册申请</title>
</head>

<body>
	<div class="cont-word">
    	
        <div class="form-info-layer">
            <div class="form-col2">
                <label for="" class="colleft form-lable"><span class="red">*</span>平台用户名</label>
                <input type="text"  style="width:198px" class="colright form-inpyt-sm" value="${userInfo.userName}" disabled placeholder="${userInfo.userName}">
            </div>
            <div class="form-col2">
                <label for="" class="colleft form-lable"><span class="red">*</span>姓名</label>
                <input type="text"  style="width:198px" class="colright form-inpyt-sm" value="${userInfo.securityRealName}" disabled placeholder="${userInfo.realName}">
            </div>
            <div class="form-col2">
                <label for="" class="colleft form-lable"><span class="red">*</span>证件号码</label>
                <input type="text"  style="width:198px" class="colright form-inpyt-sm" value="${userInfo.securityIdCardNo}" disabled placeholder="${userInfo.idCardNo}">
            </div>
            <div class="form-col2">
                <label for="" class="colleft form-lable"><span class="red">*</span>手机号码</label>
                
                <input type="text"  style="width:198px" class="colright form-inpyt-sm" value="${userInfo.securitymobileNum}" disabled placeholder="${userInfo.mobile}">
            </div>
            <div class="form-col2">
                <label for="" class="colleft form-lable"><span class="red">*</span>持有份额</label>
                
                <input type="text"  style="width:198px" class="colright form-inpyt-sm" value="${stockAccount.useStock}" disabled placeholder="${stockAccount.useStock}">
            </div>
            <input type="hidden" value="2" id="J_type"/>
            <div class="form-col2" style="margin-left: 130px;margin-top: 80px;">
            <button type="button" id="btnSaveBank" class="enter" onClick="saveApply();">确定</button>
            </div>
         </div>
    </div> 
</body>
<script type="text/javascript">
function saveApply(){
	var isProtocol=0;
	var type=$("#J_type").val();
	layer.confirm("您确认要退出内转名册吗?", function(index){ 
	$("#btnSaveBank").removeAttr("onclick");
	var _load = layer.load('处理中..');
	$.ajax({
		url : '${basePath}/stockApply/registerStock.html',
		data : {
			stockTotal:"${stockAccount.useStock}",
			isProtocol:isProtocol,
			'type' : type
		},
		type : 'post',
		dataType : 'json',
		success : function(data) {
			$("#btnSaveBank").attr("onclick","saveApply()");
			layer.close(_load);
			if (data.code == '0') {
				layer.msg(data.message, 2, 5);
				return;
			}
			if(data.code == '1'){
				layer.msg(data.message, 2, 5);
				return;
			}else {
				layer.msg('提交申请成功', 2, 1, function() {
					runtime = 0;
					window.parent.location.href = "${basePath}/stockApply/queryApplyMain/2.html";
				});
			}
		},
		error : function(data) {
			$("#btnSaveBank").attr("onclick","saveApply()");
			layer.close(_load);
			layer.msg("网络连接异常，请刷新页面或稍后重试！");
	    }
	});
	});
}
</script>
</html>
