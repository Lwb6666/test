<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ page import = "com.dxjr.portal.statics.BusinessConstants" %>
<%@ include file="/WEB-INF/page/common/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>顶玺金融互联网专业的P2P网站</title>
<link href="${basePath}/css/Style.css" type="text/css" rel="stylesheet" />
<link href="${basePath}/css/belstar.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${basePath}/js/jquery-1.11.0.js" ></script>
<script type="text/javascript" src="${basePath}/js/jquery-migrate-1.2.1.js" ></script>
<script type="text/javascript" src="${basePath}/js/jquery.form.js"></script>
</head>
<body>
<div id="user_mian_right">
	<div class="zjjl_list">
	   <form id="tenderRealForm" action="" method="post">
	      <table width="875" border="1">
	        <tr>
	          <td colspan="15" ><span class="zj">
	                              投标直通车标题关键字:
	            <input type="text" size="12" name="firstBorrowName" id="firstBorrowName"/>
				<input type="button" onclick="searchList();" value="搜索" style="width:60px;height:24px;cursor:pointer;"/>
	          </span> 
	         </td>
	        </tr>
	       </table>
       </form>
       <div id="tendRealResult"></div>
    </div>
</div>
</body>
<script type="text/javascript">

$(document).ready(function(){
	//默认执行查询方法
	searchList();
});

/**
 * 查询直通车列表
 */
function searchList(){
	// 优先计划列表翻页查询
	turnFirstTenderRealPageParent(1);
}
/**
 * 优先计划列表翻页
 */
 function turnFirstTenderRealPageParent(pageNo){
		$("#tenderRealForm").ajaxSubmit({
		    url : '${basePath }/first/tenderreal/queryList/'+pageNo+'.html',
		    type: "POST",
		    success:function(data){
				$("#tendRealResult").html(data);
		    },
			error : function() {
				layer.msg('网络连接超时，请您稍后重试', 1, 5);
		    } 
		 });
}
</script>
</html>

