<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/page/common/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>我的账户_资金记录_顶玺金融</title>
<%@ include file="/WEB-INF/page/common/public4.jsp"%>
</head>
<body style="background: #f9f9f9;">
	<!--头部开始-->
	<%@ include file="/WEB-INF/page/common/header.jsp"%>
	<!--头部结束-->
	
	<div id="myaccount">
	<%@ include file="/WEB-INF/page/common/topmenu.jsp"%>
	<div class="product-wrap">
	<div class="grid-1100">
        <div class="product-deatil clearfix ">
            <h1 class="f16 bule">资金明细</h1>
              <ul class="topcol3">
	              <li><span class="f20 orange"><fmt:formatNumber value="${account.total }" pattern="${moneyFmt }"/>元</span><br>
	               		 资金总额<em class="iconfont yellow tip-bottom" title="非存管账户余额：<fmt:formatNumber value="${account.total-(account.eUseMoney+account.eFreezeMoney+account.eCollection) }" pattern="${moneyFmt }"/>元<br>存管账户总额：<fmt:formatNumber value="${account.eUseMoney+account.eFreezeMoney+account.eCollection }" pattern="${moneyFmt }"/>元<br>资金总额不包含定期宝和活期宝"></em></li>
	              <li><span class="f20 orange"><fmt:formatNumber value="${account.useMoney+account.eUseMoney }" pattern="${moneyFmt }"/>元</span><br>可用余额<i class="iconfont yellow tip-bottom"  title="非存管账户余额：<fmt:formatNumber value="${account.useMoney }" pattern="${moneyFmt }"/>元<br>存管账户总额：<fmt:formatNumber value="${account.eUseMoney }" pattern="${moneyFmt }"/>元">&#xe608;</i></li>
	              <li><span class="f20 orange"><fmt:formatNumber value="${account.noUseMoney+account.eFreezeMoney }" pattern="${moneyFmt }"/>元</span><br>冻结金额<i class="iconfont yellow tip-bottom"  title="非存管账户余额：<fmt:formatNumber value="${account.noUseMoney }" pattern="${moneyFmt }"/>元<br>存管账户总额：<fmt:formatNumber value="${account.eFreezeMoney }" pattern="${moneyFmt }"/>元">&#xe608;</i></li>
            </ul>
		</div>
		<div id="menu_right" style=""></div>
	</div>
	</div>
	</div>
	

	<div class="clearfix"></div>
	<%@ include file="/WEB-INF/page/common/footer.jsp"%>
	
	<!--设置弹层star--->
<div id="myModal" class="reveal-modal"></div>

<!--弹层end--->


</body>
<script type="text/javascript">
	$(function() {
		paymentinfo();
	});
	/**
	 * 显示账户交易明细
	 */

	var startTime = '${startTime}';
	var endTime = '${endTime}';
	var type = '${type}';
    var accountType = '${accountType}';
    var timeType = '${timeType}';
	/* var pageNum = '${pageNum}'; */
	function paymentinfo() {

		$.ajax({
			url : '${basePath}/accOpertingRecord/showdetail.html',
			data : {
				startTime : startTime,
				endTime : endTime,
				type : type
			},
			type : 'post',
			dataType : 'text',
			success : function(data) {
				$("#menu_right").html(data);

			},
			error : function(data) {
				layer.msg('网络连接异常，请刷新页面或稍后重试！', 1, 5);
			}
		});
	}
	/**搜索*/
	function search(pageNum, accountType, timeType) {
		$.ajax({
			url : '${basePath}/accOpertingRecord/searchpaymentDetails/' + pageNum
					+ '.html',
			data : {
				startTime : $('#startTime').val(),
				endTime : $('#endTime').val(),
				type : $('#type').val(),
                accountType: accountType,
                timeType: timeType
			},
			type : 'post',
			dataType : 'text',
			success : function(data) {
				$("#menu_right").html(data);

			},
			error : function(data) {
				layer.msg('网络连接异常，请刷新页面或稍后重试！', 1, 5);
			}
		});
	}
</script>
</html>
