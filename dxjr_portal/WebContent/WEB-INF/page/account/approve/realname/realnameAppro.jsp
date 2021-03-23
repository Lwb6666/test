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
	<div class="lb_waikuang border whitebg">
        <div class="safetoptit">安全中心</div>   
        <div class="safe">
	        <div class="safebox520">
	        <dl>
	            <dd class="currenttext">
	            	<div class="tipone"><span>温馨提示：</span>实名认证成功后，不可修改。港澳台地区用户请采用<a href="${path}/account/approve/realname/display.html">人工认证</a>。

	            	</div>
	            </dd>
	            <dd class="currentdd">
	           		 <span>真实姓名：</span><input type="text" class="inputtext" name="realname" id="realname">
	            </dd>
	            <dd class="currentdd">
	           		 <span>身份证号：</span><input type="text" class="inputtext" name="idcard" id="idcard">
	            </dd>
	        </dl>
	       
	       	<div class="gg_btn" ><input id="btnRealNameAppro" type="button" value="立即确认" onclick="forsubmit();"/></div>
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

});

/**
* 提交表单
*/
function forsubmit(){
	$("#btnRealNameAppro").removeAttr("onclick");
	if(verify()){
		$.ajax({
			url : '${basePath}/account/approve/realname/realnameAppro.html',
			data :{realname:$("#realname").val(),idcard:$("#idcard").val()} ,
			type : 'post',
			dataType : 'text',
			success : function(data){
				if(data=="success"){
					layer.alert("申请成功！" , 1, "温馨提示",function(){
						window.open("${path}/account/approve/realname/toRealNameAppro.html","_parent");
		    		});
		    	}else{
					layer.alert(data);		    		
		    	}
				$("#btnRealNameAppro").attr("onclick","forsubmit();");
			},
			error : function(data) {
				layer.msg('网络连接异常，请刷新页面或稍后重试！', 1, 5);
				$("#btnRealNameAppro").attr("onclick","forsubmit();");
		    }
		});
	}else{
		$("#btnRealNameAppro").attr("onclick","forsubmit();");
	}
}
/**
 * 验证数据 
 */
function verify(){
	var flag = true;
	var patten_realName = new RegExp(/^[\u0391-\uFFE5]+$/);
	var realname = $.trim($('#realname').val());
	if(realname == null || realname == ''){
		layer.alert("请输入真实姓名!");
		return false;
	}else{
		$('#realname').val(realname);
	}
	var idcard = $.trim($("#idcard").val());
	var isIDCard1= /(^\d{15}$)|(^\d{17}([0-9]|X)$)/;
	var isIDCard2= /^(\d{6})(18|19|20)?(\d{2})([01]\d)([0123]\d)(\d{3})(\d|X)+$/;
	
	if(idcard == null || idcard == ''){
		layer.alert("请输入身份证号!");
		return false;
	}else{
		if(!(isIDCard1.test(idcard) || isIDCard2.test(idcard))){
			layer.alert("证件号码不是身份证号码");
			return false;
		}else{
			$('#idcard').val(idcard);
		}
	}
	return flag;
}

</script>
</html>
