<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<link href="${basePath}/js/ueditor/themes/default/css/umeditor.css" type="text/css" rel="stylesheet" />
<script type="text/javascript" src="${basePath}/js/ueditor/umeditor.config.js"></script>
<script type="text/javascript" src="${basePath}/js/ueditor/umeditor.js"></script>
<script type="text/javascript" src="${basePath}/js/ueditor/lang/zh-cn/zh-cn.js"></script>


<script type="text/javascript">
  <c:if test="${empty isNoteDetail}">
	 $(document).ready(function (){
		 var  style= $(".edui-body-container").attr("style"); 
		 $(".edui-body-container").attr("style",style+"height:120px;width:98%");
		 //解决最大化问题 
		 var  style1= $(".edui-container").attr("style");
		 $(".edui-container").attr("style",style1+"width:98%");
	})
</c:if> 
	 
	 
	 
</script>