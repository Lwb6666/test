<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/page/common/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ include file="/WEB-INF/page/common/public.jsp"%>
<title>上传头像</title>

</head>
<body style="overflow-y:hidden;background:#fff;">
<form action=""  id="form2" name="form2" method="post"  enctype="multipart/form-data">
<div class="sf">
	<div style="width:450px;height:300px;">
	 <dd style="font-size:22px; color:#000; margin-top: 100px; text-align: center;">请上传你网站的头像</dd> 
	 <dd> <span>&nbsp;</span> </dd>
	 <dd>说明：请上传后缀为jpg、jpeg、gif、png格式的图片，图片大小不要大于1MB</dd>
	  <dl>
	      <dd> <span>&nbsp;</span> </dd>
	      <dd style="text-align: center;"><input type="file" name="headimg" id="headimg" size="20" class="input_border" style="cursor:pointer;"/>  </dd>
	  </dl>
	  <div class="gg_btn" style="text-align: left;"><input type="button" id="foruploadBtn" value="提交" onclick="forupload()" style="cursor:pointer;"/></div>              
	</div>
	<input type="hidden" name="revision" id="revision" value="${revision }" />
</div>
</form>
</body>
<script type="text/javascript">
function verify(){
	var flag = true;
	var headimg = $('#headimg').val();
	if(headimg==null || headimg==""){
		layer.alert("请选择头像！");
		flag = false;
		return;
	}
	return flag;
}
$(function(){
});




function forupload(){
	$('#foruploadBtn').attr("disabled","disabled");
	if(verify()){
		var _load = layer.load('处理中..');
		$("#form2").ajaxSubmit({
		    url : '${basePath}/myaccount/saveHeadImg.html',
		    type: "POST",
		    dataType:'json',
		    success:function(result){
		    	$('#foruploadBtn').removeAttr("disabled"); 
		    	layer.close(_load);
		    	if(result.code=='1'){
		    		layer.alert(result.message , 1, "温馨提示");
		    		window.parent.refreshMyAccount();
		    	}else{
		    		layer.alert(result.message);
		    		$('#revision').val(result.revision);
		    	}
		    },
			error : function(result) {
				$('#foruploadBtn').removeAttr("disabled"); 
				layer.close(_load);
				layer.alert("网络连接超时，请您稍后重试！");
				$('#revision').val(result.revision);
			},
			statusCode: {
				10001: function() {
					layer.msg('请不要频繁请求，稍后再试！', 1, 5);
				}
			}
		 });
	}else{
		$('#foruploadBtn').removeAttr("disabled"); 
	}
}
</script>
 
</html>
