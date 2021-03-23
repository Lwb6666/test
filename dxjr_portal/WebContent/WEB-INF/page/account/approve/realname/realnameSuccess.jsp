<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/page/common/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ include file="/WEB-INF/page/common/public.jsp"%>
<title>实名认证成功_顶玺金融</title>
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
	<div class="lb_waikuang border whitebg">
        <div class="safetoptit">安全中心</div>   
        <div class="modify safebox450">
		    <p>实名认证成功</p>
        </div>
        <div class="safe">
	        <div class="safebox520">
	        <dl>
	            <dd class="currentdd">
	           		 <span>真实姓名：</span><span id="realName" style="text-align: left;">${realNameApproVo.realName }</span>
	            </dd>
	            <br/>
	            <dd class="currentdd">
	           		 <span>身份证号：</span><span id="idCardNo" style="text-align: left;">${realNameApproVo.idCardNo }</span>
	            </dd>
	        </dl>
	        </div>
        </div>
	</div>            
	<!--安全中心右侧结束 -->
</div>
</div>
<%@ include file="/WEB-INF/page/common/footer.jsp" %>
</body>
<script type="text/javascript">
$(function(){
	var realName = '${realNameApproVo.realName }';
	if(realName != 'undefined' && realName != ''){
		var str1 = realName.substring(realName.length-1);
		var str2 = "";
		for(var i=0;i< realName.length-1;i++){
			str2 = str2 + "*";
		}
		$("#realName").html(str2+str1);
	};
	//证件号码
	var idCardNo = '${realNameApproVo.idCardNo}';
	if(idCardNo != 'undefined' && idCardNo != ''){
		var str1 = idCardNo.substr(0,2);
		var str2 = "";
		for(var i=0; i<idCardNo.length-2;i++){
			str2 = str2 + "*";
		}
		$("#idCardNo").html(str1+str2);
	};
});

/**
* 提交表单
*/
function forsubmit(){
	if(verify()){
		$.ajax({
			url : '${basePath}/account/approve/realname/realnameAppro.html',
			data :{realname:$("#realname").val(),idcard:$("#idcard").val()} ,
			type : 'post',
			dataType : 'text',
			success : function(data){
				if(data=="success"){
		    		layer.alert("申请成功" , 1, "温馨提示");
		    		window.open("${path}/account/approve/realname/toRealNameAppro.html","_parent");
		    	}else{
					layer.alert(data);		    		
		    	}
			},
			error : function(data) {
				layer.msg('网络连接异常，请刷新页面或稍后重试！', 1, 5);
		    }
		});
	}
}
/**
 * 验证数据 
 */
function verify(){
	var flag = true;
	var realname = $("#realname").val();
	if(realname == null || realname == ''){
		layer.alert("请输入真实姓名!");
		return false;
	}
	var idcard = $("#idcard").val();
	if(idcard == null || idcard == ''){
		layer.alert("请输入身份证号!");
		return false;
	}
	return flag;
}

</script>
</html>
	        