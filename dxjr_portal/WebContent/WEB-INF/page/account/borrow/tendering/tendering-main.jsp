<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/page/common/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ include file="/WEB-INF/page/common/public.jsp"%>
<title>我的账户</title>
</head>
<body style="background:#f9f9f9;">
<!--头部开始-->
<%@ include file="/WEB-INF/page/common/header.jsp" %>
<!--头部结束-->
<!--我的账户左侧开始-->
<div id="Container">
<div id="Bmain">
  <div id="Bmain_titile">
  	<div class="title"><span class="home"><a href="${path}/">顶玺金融</a></span><span><a href="${path}/myaccount/toIndex.html">我的账户</a></span><span> 融资管理</span><span><a href="${path }/myaccount/toTendering.html">正在招标中</a></span></div>
        <div id="menu_centert">     
	        <%@ include file="/WEB-INF/page/account/myAccount_leftMenu.jsp"%>
	        <div id="main_content"></div>
		</div>
	</div>
  </div>
</div>
<div class="clearfix"></div>
<div>
<%@ include file="/WEB-INF/page/common/footer.jsp" %>
</div>
</body>
<script type="text/javascript">
var _load;
$(function(){
	queryTendering(1,10);
});

/**
 * ajax 翻页功能
 */
function findPage(pageNo){
	queryTendering(pageNo, 10);
}

//正在招标中
function queryTendering(pageNum, pageSize){
	
	var borrowName = $("#borrowName").attr("value");
	var beginTime = $("#beginTime").attr("value");
	var endTime = $("#endTime").attr("value");
	$.ajax({
		url : '${basePath}/myaccount/queryTendering.html',
		data :{
			pageNum:pageNum,pageSize:pageSize,status:status,borrowName:borrowName,beginTime:beginTime,endTime:endTime
		} ,
		type : 'post',
		dataType : 'text',
		success : function(data){
			$("#main_content").html(data);
		},
		error : function(data) {
			layer.msg('网络连接异常，请刷新页面或稍后重试！', 1, 5);
	    }
	});
}

/**
 * 撤标
 */
function calcelBorrow(id,obj){
	$(obj).removeAttr("onclick");
	if(layer.confirm("确定要撤标吗？",function(){
		_load = layer.load('处理中..');
		$.ajax({
			url : '${basePath}/borrow/borrow/cancelBorrow.html',
			data :{borrowid:id
			} ,
			type : 'post',
			dataType : 'text',
			success : function(data){
				$(obj).attr("onclick","calcelBorrow("+id+",this);");
				layer.close(_load);
				var iconIndex = 5;
				if(data == '撤标成功！'){
					iconIndex = 1;
				}
				layer.msg(data, 1, iconIndex,function(){
			    	//重新查询
					queryTendering(1,10);
		    	});
			},
			error : function(data) {
				layer.close(_load);
				$(obj).attr("onclick","calcelBorrow("+id+",this);");
				alert("网络连接异常，请刷新页面或稍后重试！");
		    }
		});
	},"温馨提示",function(){
		$(obj).attr("onclick","calcelBorrow("+id+",this);");
	}));
}

/**
 * 提前满标
 */
function advanceFullBorrow(id,obj){
	$(obj).removeAttr("onclick");
	if(layer.confirm("确定要提前满标吗？",function(){
		_load = layer.load('处理中..');
		$.ajax({
			url : '${basePath}/borrow/borrow/advanceFullBorrow.html',
			data :{borrowid:id
			} ,
			type : 'post',
			dataType : 'text',
			success : function(data){
				layer.close(_load);
				$(obj).attr("onclick","advanceFullBorrow("+id+",this);");
				var iconIndex = 5;
				if(data == '提前满标成功！'){
					iconIndex = 1;
				}
				layer.msg(data, 2, iconIndex,function(){
			    	//重新查询
					queryTendering(1,10);
		    	});
			},
			error : function(data) {
				layer.close(_load);
				$(obj).attr("onclick","advanceFullBorrow("+id+",this);");
				alert("网络连接异常，请刷新页面或稍后重试！");
		    }
		});
	},function(){
		$(obj).attr("onclick","advanceFullBorrow("+id+",this);");
	}));
}
</script>
</html>
