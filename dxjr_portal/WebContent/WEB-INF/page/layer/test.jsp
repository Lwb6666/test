<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/page/common/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ include file="/WEB-INF/page/common/public.jsp"%>
<title>网站账户</title>
<script type="text/javascript">
var _load;
$(function(){
	$('#msgButton').on('click', function(){
	    layer.msg('信息框提示内容', 2, 1);
	});
	
	$('#alertButton').on('click', function(){
	    layer.alert('信息按钮框提示内容', 1, "提示框",function(){
	    	layer.msg("点击确认按钮成功", 2, 9);
	    });
	});
	
	$('#confirmButton').on('click', function(){
	    layer.confirm('确认删除吗？', function(){
	    	layer.alert('删除成功', 1, '提示信息');
	    }, '删除询问框',function(){
	    	layer.alert('取消删除', 8, '提示信息');
	    });
	});
	
	$('#tipsButton').on('click', function(){
		layer.tips("手机号码不能为空，请输入手机号码！" , $("#tipsButton") ,{
		    time: 3,
		    style: ['background-color:#ff0000; color:#fff;', '#ff0000']
		});
	});
	
	$('#loadButton').on('click', function(){
		_load = layer.load("处理中，请稍等...");
	});
	$('#closeButton').on('click', function(){
		layer.close(_load);
	});
	
});
</script>
</head>
<body>
<input type="button" id="msgButton" value="信息框"/>&nbsp;
<br/>
<pre>
说明：layer.msg()  信息框
layer.msg(msgText , msgTime , msgType , maxWidth);默认格式如：layer.msg("test"),默认图标数字8
四个参数，msgText：信息内容（文本），msgTime：自动关闭所需等待秒数（默认2秒），msgType：提示图标（整数，0-17的选择），maxWidth：最大宽度
</pre>

<input type="button" id="alertButton" value="信息按钮框"/>&nbsp;
<br/>
<pre>
说明：layer.alert() 信息按钮框
layer.alert(alertMsg , alertType, alertTit , alertYes);
四个参数，alertMsg：信息内容（文本），alertType：提示图标（整数，0-17的选择），alertTit：标题（文本），alertYes：按钮的回调函数
</pre>


<input type="button" id="confirmButton" value="询问框"/>&nbsp;
<br/>
<pre>
说明：layer.confirm() 询问框
layer.confirm(conMsg , conYes , conTit , conNo)；
四个参数，conMsg：信息内容（文本），conYes：按钮一回调，conTit：标题（文本），conNo：按钮二的回调	如：layer.confirm('确定删除',function(){ layer.msg('删除成功')}

<input type="button" id="tipsButton" value="提示信息浮动层"/>&nbsp
说明：layer.tips()	
layer.tips(html , follow , time , maxWidth)；
四个参数，html：信息内容（文本），follow：触发事件对应的选择器，time：自动关闭所需等待秒数，maxWidth：最大宽度

<input type="button" id="loadButton" value="加载等待中"/>&nbsp<input type="button" id="closeButton" value="关闭"/>
说明：layer.load()	
layer.load(loadTime , loadgif , loadShade);
三个参数，loadTime：自动关闭所需等待秒数，loadgif：加载图标（整数，0-3的选择），loadShade：是否遮罩（true 或 false）	如：layer.load(3);
</pre>
</body>
</html>