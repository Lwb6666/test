<%@page import="com.dxjr.utils.DateUtils"%>
<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/page/common/taglib.jsp"%>
<!doctype html>
<html>
<head>
<%@ include file="/WEB-INF/page/stockright/common/public.jsp"%>
<meta charset="utf-8">
<title>我的内转账户_份额转让</title>

<body>
<div class="main-offsetHeight">
	<!-- header start   -->
	<%@ include file="/WEB-INF/page/stockright/common/header.jsp"%>
	<!-- header end     -->

	<!-- main start	 -->
	<div class="product-wrap">
		<div class="grid-1100">
			<div class="product-deatil clearfix ">
				<h1 class="f16 blue">份额转让</h1>
				<div class="buy clearboth">
					<div class="buy-l">
						<div class="buy-border">
							<table width="100%" border="0" cellspacing="0" cellpadding="0"
								class="table center tbtr">
								<thead>
									<tr class="tbl-title">
										<td>类型</td>
										<td>价格（元）</td>
										<td>份额（份）</td>
									</tr>
								</thead>

								<tbody>
									<c:set value="${fn:length(sellerList)}" var="indexval"/>
									<c:forEach items="${sellerList}"  var="seller" varStatus="sta">
									<tr>
									
										<td>转让${indexval -sta.index}</td>
										<td>${seller.price}</td>
										<td><fmt:formatNumber value="${seller.residueAmount}" pattern="#,#00"/></td>
									</tr>
									</c:forEach>
								</tbody>
							</table>
						</div>
						<p class="buy-sk mt20 center">最新成交价：<c:if test="${dealRecord[0]==null}">2.00</c:if> <c:if test="${dealRecord[0]!=null}">${dealRecord[0].turnoverPrice}</c:if>元/份</p>
						<div class="buy-border mt20">
							<table width="100%" border="0" cellspacing="0" cellpadding="0"
								class="table center tbtr">
								<thead>
									<tr class="tbl-title">
										<td>类型</td>
										<td>价格（元）</td>
										<td>份额（份）</td>
									</tr>
								</thead>

								<tbody>
									<c:forEach items="${buyerList}" var="buyer" varStatus="sta">
									<tr>
										<td>认购${sta.index+1}</td>
										<td>${buyer.price}</td>
										<td><fmt:formatNumber value="${buyer.residueAmount}" pattern="#,#00"/></td>
									</tr>
									</c:forEach>
									
								</tbody>
							</table>
						</div>
					</div>
					<div class="buy-r">
						<div class="buy-info">
							<div>
								<label>转让价格：</label> 
								<input type="hidden" value="请输入${minPrice}~${maxPrice}" id="J_dfuValuesPrice"/>
								<input class="form-inpyt-sm yuan2 J_inputcheck" id="J_priceval"
									style="width: 193px;color: #999" type="text" onFocus="if(value==defaultValue){value='';this.style.color='#000'}" onBlur="if(!value){value=defaultValue; this.style.color='#999'}"  value="请输入${minPrice}~${maxPrice}"/> <i
									id="tip-bottom" title="计划转让单价!<br/>价格必须在${minPrice}~${maxPrice}之间！" class="iconfont fr">&#xe60a;</i>
							</div>
							<div class="buy-jl border-bottom">
								<label>转让份额：</label> 
								<input type="hidden" value="请输入${stockIncrease}整数倍" id="J_dfuValuesAmount"/>
								<input class="form-inpyt-sm yuan2 J_inputcheck" id="J_amountval"
									style="width: 193px;color: #999" type="text"
									onFocus="if(value==defaultValue){value='';this.style.color='#000'}" onBlur="if(!value){value=defaultValue; this.style.color='#999'}"  value="请输入${stockIncrease}整数倍"/>
								<i id="tip-bottom2" title="计划转让份额!<br/>份额必须以${stockIncrease}的倍数增加!" class="iconfont fr">&#xe60a;</i>
							</div>
							<div class="buy-jl">
								<label class="bule">转让总价：</label> <span class="buy-jk"><strong id="J_stockCountPrice">0.00</strong><strong
									class="gary2">元</strong></span> 
									<i id="tip-bottom3" title="计划转让份额总价！<br/>份额总价=转让价格*转让份额" class="iconfont fr"></i>
							</div>
							<div class="buy-jl">
								<label class="bule">交易服务费：</label> <span class="buy-jk"><strong id="J_serverPrice">0.00</strong><strong class="gary2">元</strong></span> <i id="tip-bottom4"
									title="计划扣除服务费！<br/>交易服务费=份额总价*${sellerRate}" class="iconfont fr">&#xe60a;</i>
							</div>
							<div class="buy-jl">
								<label class="bule">总金额：</label> <span class="buy-jk"><strong id="J_totalPrice">0.00</strong><strong
									class="gary2">元</strong></span> <i id="tip-bottom5"
									title="计划交易完成之后到账总金额！<br/>总金额=份额总价-交易服务费" class="iconfont fr">&#xe60a;</i>
							</div>
							<div class="buy-jl  ml10">
								<a href="javascript:saveSeller();" id="btnSaveBank" class="btn btn-join f18">卖出</a>
							</div>
						</div>
						<div class="balance buy-border mt20">
							<h3>我的份额及可用余额</h3>
							<input type="hidden" value="${account.total}" id="J_stockaccountTotal"/>
							<input type="hidden" value="${account.useStock}" id="J_stockuseStock"/>
							<ul>
								<li>当前持有份额（份）：
								<c:if test="${account.total==null}">/</c:if> 
								<c:if test="${account.total!=null && account.total==0}">0</c:if> 
								<c:if test="${account.total!=null && account.total>0}"><fmt:formatNumber value="${account.total}" pattern="#,#00"/></c:if> 
								</li>
								<li>当前可用份额（份）：
								<c:if test="${account.useStock==null}">/</c:if> 
								<c:if test="${account.total!=null && account.useStock==0}">0</c:if> 
								<c:if test="${account.useStock!=null && account.useStock>0}"><fmt:formatNumber value="${account.useStock}" pattern="#,#00"/></c:if> 
								</li>
								<li>当前冻结份额（份）：
								<c:if test="${account.noUseStock==null}">/</c:if> 
								<c:if test="${account.total!=null && account.noUseStock==0}">0</c:if> 
								<c:if test="${account.noUseStock!=null && account.noUseStock>0}"><fmt:formatNumber value="${account.noUseStock}" pattern="#,#00"/></c:if> 
								</li>
								<li>账户可用余额（元）：${accountVo.useMoneyStr}</li>
							</ul>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>

	<!-- main end	 -->

	<!-- footer start   -->
	<%@ include file="/WEB-INF/page/stockright/common/footer.jsp"%>
	<!-- footer end     -->
</body>
<script type="text/javascript">
$(function(){  
	menuSelect(3);
}); 

function format (num) {
    return num.replace(/\d{1,3}(?=(\d{3})+(\.\d*)?$)/g, '$&,');
}
function appQuitShareholder() {
	$.layer({
		type : 2,
		fix : false,
		shade : [ 0.6, '#E3E3E3', true ],
		shadeClose : true,
		border : [ 10, 0.7, '#272822', true ],
		title : [ '退出内转名册申请', true ],
		offset : [ '50px', '' ],
		area : [ '500px', '450px' ],
		iframe : {
			src : '${basePath}/stockApply/quitShareholder.html'
		},
		close : function(index) {
			layer.close(index);
		}
	});
}
$(".J_inputcheck").keyup(function(){
	$(this).val($(this).val().replace(/[^\d.]/g,""));  //清除“数字”和“.”以外的字符
	$(this).val($(this).val().replace(/^\./g,""));  //验证第一个字符是数字而不是.
	$(this).val($(this).val().replace(/\.{2,}/g,".")); //只保留第一个. 清除多余的.
	$(this).val($(this).val().replace(".", "$#$").replace(/\./g, "").replace("$#$", ".").replace(/^(\-)*(\d+)\.(\d).*$/, '$1$2.$3'));//(\d\d)两位小数
	var price = $("#J_priceval").val();
	var amount = $("#J_amountval").val();
	var dfuAmount = $("#J_dfuValuesAmount").val();
	var dfuPrice = $("#J_dfuValuesPrice").val();
	if(price!='' && amount!='' && dfuAmount!=amount && dfuPrice!=price){
		$("#J_stockCountPrice").html(format((parseFloat(price)*parseFloat(amount)).toFixed(2)));
		$("#J_serverPrice").html(format((parseFloat(price)*parseFloat(amount)*parseFloat('${sellerRate}')).toFixed(2)));
		$("#J_totalPrice").html(format(((parseFloat(price)*parseFloat(amount))-(parseFloat(price)*parseFloat(amount)*parseFloat('${sellerRate}'))).toFixed(2)));
	}else{
		$("#J_stockCountPrice").html('0.00');
		$("#J_serverPrice").html('0.00');
		$("#J_totalPrice").html('0.00');
	}
});

$("#J_priceval").blur(function(){
	var minPrice = ${minPrice};
	var maxPrice = ${maxPrice};
	var val = $(this).val();
	if(val!=null && val!=''){
		if (parseFloat(minPrice)>parseFloat(val) || parseFloat(val)>parseFloat(maxPrice)){
			layer.msg('价格必须在'+minPrice+'~'+maxPrice+'之间！', 2, 5);
			$(this).val(""); 
			$("#J_stockCountPrice").html('0.00');
			$("#J_serverPrice").html('0.00');
			$("#J_totalPrice").html('0.00');
			$(this).focus();
		}
	}
	
});

$("#J_amountval").blur(function(){ 
		var val; 
		var useStock=$("#J_stockuseStock").val();
		var stockIncrease=${stockIncrease};
		var dfuAmount = $("#J_dfuValuesAmount").val();
		val = $(this).val();
		if(val!=null && val!='' && val!=dfuAmount){
			if(0 >= Number(val) ){
				layer.msg('转让份额必须大于0！', 2, 5);
				$(this).val(""); 
				$("#J_stockCountPrice").html('0.00');
				$("#J_serverPrice").html('0.00');
				$("#J_totalPrice").html('0.00');
				// $(this).focus();
			}
			if(Number(val) > Number(useStock)){
				layer.msg('转让份额不能大于您的可用份额！',2, 5);
				$(this).val(""); 
				$("#J_stockCountPrice").html('0.00');
				$("#J_serverPrice").html('0.00');
				$("#J_totalPrice").html('0.00');
				// $(this).focus();
			}
			var rem = val % stockIncrease;
			if (rem != 0){
				layer.msg('转让份额必须以'+stockIncrease+'倍数增加！', 2, 5);
				$(this).val(""); 
				$("#J_stockCountPrice").html('0.00');
				$("#J_serverPrice").html('0.00');
				$("#J_totalPrice").html('0.00');
				// $(this).focus();
			}
		}
		
});


function saveSeller(){
	var price = $("#J_priceval").val();
	var dfuPrice = $("#J_dfuValuesPrice").val();
	var amount = $("#J_amountval").val();
	var dfuAmount = $("#J_dfuValuesAmount").val();
	if(price=='' || price == null || price==dfuPrice){
		layer.msg('转让价格不能为空！', 2, 5);
		return;
	}
	if(amount=='' || amount == null || amount==0 || amount==dfuAmount){
		layer.msg('转让份额不能为空或必须大于0！', 2, 5);
		return;
		
	}
	
	layer.confirm("您确认以每份"+price+"元,转让"+amount+"份额吗?", function(index){ 
	$("#btnSaveBank").removeAttr("onclick");
	var _load = layer.load('处理中..');
	$.ajax({
		url : '${basePath}/stockDeal/sellerApplyEntrust.html',
		data : {
			'amount' : amount,
			'price' : price
		},
		type : 'post',
		dataType : 'json',
		success : function(data) {
			$("#btnSaveBank").attr("onclick","saveSeller()");
			layer.close(_load);
			if (data.code == '00000') {
				layer.msg('转让成功', 2, 1, function() {
					runtime = 0;
					location.href = "${basePath}/stockSeller/toEntrustMain.html";
				});
			}else if(data.code == '60000'){
				layer.confirm(data.message, function(index){ 
					layer.close(index);
					appQuitShareholder();
					
				});
			} else {
				layer.msg(data.message,2, 5);
				return;
			};
		},
		error : function(data) {
			$("#btnSaveBank").attr("onclick","saveSeller()");
			layer.close(_load);
			layer.msg("网络连接异常，请刷新页面或稍后重试！", 2, 5);
	    }
	});
	});
}
</script>
</html>
