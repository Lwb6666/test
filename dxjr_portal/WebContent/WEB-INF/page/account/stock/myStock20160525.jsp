<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/page/common/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ include file="/WEB-INF/page/common/public.jsp"%>
<title>我的账户-期权管理-我的期权_顶玺金融</title>
</head>
<body>
<%@ include file="/WEB-INF/page/common/header.jsp"%>
<div id="Container">
<div id="Bmain">
	<div class="title">
		<span class="home"><a href="${path}/">顶玺金融</a></span>
     	<span><a href="${path }/myaccount/toIndex.html">我的账户</a></span>
     	<span>期权管理</span>
     	<span>我的期权</span>
	</div>
	<div id="menu_centert">
	<%@ include file="/WEB-INF/page/account/myAccount_leftMenu.jsp"%>
	<div class="lb_waikuang" >
	<div class="myid whitebg">
		<c:if test="${stock!=null }">
		<table border="0">
			<tr class="trcolor">
				<td>期权排名</td>
				<td>期权数量(份)</td>
				<td>期权获得时间</td>
				<td>现金行权金额</td>
				<td>现金行权时间</td>
				<td>状态</td>
				<td id="optTd">操作</td>
			</tr>
			<tr>
				<td>${stock.rank }</td>
				<td class="numcolor">${stock.stockNum }</td>
				<td>${stock.addTimeStr }</td>
				<td class="numcolor"><fmt:formatNumber value="${stock.stockMoney }" pattern="￥#,##0.00"/></td>
				<td>${stock.exerciseTimeStr }</td>
				<td>${stock.statusStr }</td>
				<td id="exerciseTd"><a href="javascript:exerciseByMoney()" >现金行权</a></td>
			</tr>
		</table>
		
		<div class="tip">
			 <p><span class="red">注：期权持有期间，资产总额不能少于<b>${haveStockMoney }</b>元。</span></p>
             <h3> 温馨提示：</h3>
             <p>1、所有行权均不允许部分行权,只能一次性全部行权。 </p>
             <p>2、2014年9月27日-2015年9月26日期间任何时间进行现金行权，行权价格为1元/份。</p>
             <p>3、2015年9月27日-2016年9月26日期间任何时间进行现金行权，行权价格为2元/份。</p>
             <p>4、2016年9月27日-2017年9月26日期间任何时间进行现金行权，行权价格为3元/份。</p>
             <p>5、更多行权说明请参见<a href="http://bbs.dxjr.com/posts/65924/1" target="_blank">【顶玺金融投资人入股实施细则】</a>。</p>
        </div>
		</c:if>
		<c:if test="${stock==null }">
		<table border="0"><tr class="trcolor"><td>您暂无期权</td></tr></table>
		</c:if>
		
	</div>
	</div>
	</div>
</div>
</div>
<div class="clearfix"></div>
<%@ include file="/WEB-INF/page/common/footer.jsp"%>
<script type="text/javascript">
/**
 * 现金行权按钮显示
 */
$(function(){
	if('${stock.status }'=='2'){
		$('#optTd').css("display","none");
		$('#exerciseTd').css("display","none");
	}
});
/**
 * 现金行权
 */
function exerciseByMoney(){
	layer.prompt({title: '请输入交易密码',type: 1}, function(pwd){
		
		layer.confirm("确认立即现金行权吗?", function(){
			$.ajax({
				url : '${basePath}/stock/exerciseStock.html',
				type : 'post',
				data: {payPwd:pwd},
				dataType : 'json',
				success : function(msg){
					if(msg.code=="1"){
						$.layer({
							dialog: { type: 1, msg: msg.message },
							closeBtn: false,
							yes: function(index){window.location.reload();}
						});
						
			    	}else{
			    		$.layer({
							dialog: { type: 5, msg: msg.message },
							closeBtn: false,
							yes: function(index){exerciseByMoney();}
						});
			    	}
				},
				error : function(data) {
					layer.msg("网络连接异常，请刷新页面或稍后重试！",1,5);
			    }
			});
		});
		
	});
}
</script>
</body>
</html>