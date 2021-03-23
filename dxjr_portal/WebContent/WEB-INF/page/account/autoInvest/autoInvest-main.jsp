<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/page/common/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ include file="/WEB-INF/page/common/public4.jsp"%>
<title>自动投资</title>
</head>

<body>
<%@ include file="/WEB-INF/page/common/header.jsp" %>
<!--main-->
<div id="myaccount">
	<%@ include file="/WEB-INF/page/common/topmenu.jsp"%>
	<%@ include file="/WEB-INF/page/common/wraper.jsp"%>
	<div class="wraper mt20">
     <div class="prduct-menu  background clearfix">
        	<div class="menu-tbl">
        	       <ul class="col2">
	                  <li id="auto_invest_list" onclick="autoInvestList()">自动投标</li>
	                  <li id="auto_invest_record" onclick="autoInvestFix()">自动投定期宝 </li>
                  </ul>
            </div>
            <div id="main_content"></div>      
      </div>
     </div>
</div>
<!---------- ------------------------自动投标-弹层---------------------------------------------------->
<div class="reveal-modal"  id="setInvest"></div>

<!---------- ------------------------自动投宝-弹层---------------------------------------------------->
<div class="reveal-modal" id="setFix" ></div>
<%@ include file="/WEB-INF/page/common/footer.jsp" %>
</body>
<script type="text/javascript">
$(function(){
	$('#zdtz').attr("class","active");
	if('${tab}'==2){
		autoInvestFix();
	}else{
		autoInvestList();
	}

});
//自动投标页面
function autoInvestList(){
	$("#auto_invest_list").addClass("active");
	$("#add_auto_invest").removeClass("active");
	$("#auto_invest_record").removeClass("active");
	$("#autoTip").html("添加自动投标");
	$.ajax({
		url : '${basePath}/myaccount/autoInvest/autoInvestList.html',
		data :{
		} ,
		type : 'post',
		dataType : 'html',
		success : function(data){
			$("#main_content").html(data);
		},
		error : function(data) {
			layer.msg("网络连接异常，请刷新页面或稍后重试！", 2, 5);
	    }
	});
	
}
//自动投定期宝页面
function autoInvestFix(){
	$("#auto_invest_record").addClass("active");
	$("#auto_invest_list").removeClass("active");
	$("#add_auto_invest").removeClass("active");
	$.ajax({
		url : '${basePath}/myaccount/autoInvestFix/autoInvestFixMain.html',
		type : 'post',
		dataType : 'html',
		success : function(data){
			$("#main_content").html(data);
		},
		error : function(data) {
			layer.msg("网络连接异常，请刷新页面或稍后重试！", 2, 5);
	    }
	});
}
//设置自动投标功能
function settingAuto(){
	
	var _url='${basePath}/myaccount/autoInvest/autoInvestSeting.html';
	 
	$.ajax({
		url : _url,
		data : {},
		type : 'post',
		dataType : 'html',
		success : function(data) {
			$("#setInvest").html(data);
		},
		error : function(data) {
			layer.msg('网络连接异常，请刷新页面或稍后重试！', 1, 5);
		}
	});
	 
}
</script>
</html>
