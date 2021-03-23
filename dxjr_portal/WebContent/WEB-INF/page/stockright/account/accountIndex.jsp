<%@page import="com.dxjr.utils.DateUtils"%>
<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/page/common/taglib.jsp"%>
<!doctype html>
<html>
<head>
<%@ include file="/WEB-INF/page/stockright/common/public.jsp"%>
<meta charset="utf-8">
<title>我的内转账户</title>
<body>
<div class="main-offsetHeight">
	<!-- header start   -->
	<%@ include file="/WEB-INF/page/stockright/common/header.jsp"%>
	<!-- header end     -->


	<div class="product-wrap">
		<div class="grid-1100">
			<div class="product-deatil clearfix ">

				<div class="overview-term">
					<div class="overview-left">
						<div class="overview-tx clearboth">
						
						<a id="tip-userpic" href="javascript:toAddHeadImg();">
							<c:if test="${empty memberVo.headImg}">
								<img src="${basePath}/images/main/head.png"  title="编辑头像" alt="headimg" />
							</c:if>
							<c:if test="${not empty memberVo.headImg}">
								<img src="${basePath}${memberVo.headImg}"  title="编辑头像" alt="headimg" />
							</c:if>
						  </a>
							<p>
								<span>欢迎您</span>${portal:currentUser().userName}
							</p>

						</div>
						<div class="overview-text">
							<p class="overview-money">
								可用余额：<span class="yellow">${accountVo.useMoneyStr}元</span>
							</p>
							<p class="overview-an">
								<a href="${path }/stockBuyer/toBuyerIndex.html" class="btn btn-orange">认购</a> <a
									href="${path }/stockSeller/sellerIndex.html"
									class="btn btn-blue">转让</a>
							</p>
							<p class="overview-jr">
								<a href="${path }/stockApply/queryApplyMain/2.html">● 内转名册申请记录>></a><a
									href="${path }/stockApply/queryApplyMain/1.html">● 登录申请历史>></a>
							</p>
						</div>
					</div>

					<div class="overview-tlb">
						<ul class="ove-bule clearboth">
							<li><p>持有份额（份）</p>
								<p>
								<c:if test="${account==null}">/</c:if> 
								
								<c:if test="${account!=null && account.total > 0}"><fmt:formatNumber value="${account.total}" pattern="#,#00"/></c:if>
								<c:if test="${account!=null && account.total <= 0}">${account.total}</c:if>
								</p>
							</li>
							<li><p>可用份额（份）</p>
								<p><c:if test="${account==null}">/</c:if>
								<c:if test="${account!=null && account.useStock > 0}"><fmt:formatNumber value="${account.useStock}" pattern="#,#00"/></c:if>
								<c:if test="${account!=null && account.useStock <= 0}">${account.useStock}</c:if>
								
								
								</p></li>
							<li><p>冻结份额（份）</p>
								<p><c:if test="${account==null}">/</c:if>
								<c:if test="${account!=null && account.noUseStock > 0}"><fmt:formatNumber value="${account.noUseStock}" pattern="#,#00"/></c:if>
								<c:if test="${account!=null && account.noUseStock <= 0}">${account.noUseStock}</c:if>
								
								</p></li>
						</ul>
						<ul class="ove-green clearboth">
							<li><p>累计买入份额（份）</p>
								<p><c:if test="${account==null}">/</c:if>
								
								<c:if test="${account!=null && sumBuyer.totalAmount > 0}"><fmt:formatNumber value="${sumBuyer.totalAmount}" pattern="#,#00"/></c:if>
								<c:if test="${account!=null && sumBuyer.totalAmount <= 0}">${sumBuyer.totalAmount}</c:if>
								
								</p></li>
							<li><p>买入均价（元/份）</p>
								<p>
								<c:if test="${account==null}">/</c:if>
								<c:if test="${account!=null}">
								<c:if test="${sumBuyer.totalMoney>0 && sumBuyer.totalAmount>0}">
									<fmt:formatNumber value="${sumBuyer.totalMoney / sumBuyer.totalAmount}" pattern="0.00"/>
								</c:if>
								<c:if test="${sumBuyer.totalMoney==0 || sumBuyer.totalAmount==0}">
									0.00
								</c:if>
								</c:if>
								</p></li>
							<li><p>累计买入总金额（元）</p>
								<p><c:if test="${account==null}">/</c:if>
								
								<c:if test="${account!=null && sumBuyer.totalMoney > 0}"><fmt:formatNumber value="${sumBuyer.totalMoney}" pattern="#,#00.00"/></c:if>
								<c:if test="${account!=null && sumBuyer.totalMoney <= 0}">${sumBuyer.totalMoney}</c:if>
								
						</ul>
						<ul class="ove-gray clearboth">
							<li><p>累计卖出份额（份）</p>
								<p><c:if test="${account==null}">/</c:if>
								
								
								<c:if test="${account!=null && sumSeller.totalAmount > 0}"><fmt:formatNumber value="${sumSeller.totalAmount}" pattern="#,#00"/></c:if>
								<c:if test="${account!=null && sumSeller.totalAmount <= 0}">${sumSeller.totalAmount}</c:if>
								</p></li>
							<li><p>卖出均价（元/份）</p>
								<p>
								<c:if test="${account==null}">/</c:if>
								<c:if test="${account!=null}">
								<c:if test="${sumSeller.totalMoney>0 && sumSeller.totalAmount>0}">
									${sumSeller.totalMoney / sumSeller.totalAmount}
								</c:if>
								<c:if test="${sumSeller.totalMoney==0 || sumSeller.totalAmount==0}">
									0.00
								</c:if>
								</c:if>
								</p></li>
							<li><p>累计卖出总金额（元）</p>
								<p><c:if test="${account==null}">/</c:if>
								<c:if test="${account!=null && sumSeller.totalMoney > 0}"><fmt:formatNumber value="${sumSeller.totalMoney}" pattern="#,#00.00"/></c:if>
								<c:if test="${account!=null && sumSeller.totalMoney <= 0}">${sumSeller.totalMoney}</c:if>
								</p></li>
						</ul>
					</div>

				</div>



			</div>

		</div>

	</div>


	<div class="product-wrap">
		<div class="grid-1100">
			<div class="product-deatil clearfix ">
				<div class="overview-title f16">
					<h3 class="fl">挂单记录</h3>
					<a href="${path }/stockSeller/toEntrustMain.html" class="overview-btn fr">更多记录</a>
				</div>
				<table width="100%" border="0" cellspacing="0" cellpadding="0"
					class="table center tbtr">
					<thead>
						<tr class="tbl-title">
							<td>挂单编号</td>
							<td>挂单类别</td>
							<td>委托时间</td>
							<td>委托价格(元)</td>
							<td>委托挂单份额(份)</td>
							<td>已成交份额(份)</td>
							<td>未成交份额(份)</td>
							<td>状态</td>
							<td>操作</td>
						</tr>
					</thead>

					<tbody>
						<c:forEach items="${entrustList}" var="entrustList">
							<tr>
								<td>${entrustList.entrustCode}</td>
								<td><c:if test="${entrustList.entrustType==1}">认购</c:if> <c:if test="${entrustList.entrustType==2}">转让</c:if></td>
								<td><fmt:formatDate value="${entrustList.addtime}" pattern="yyyy-MM-dd HH:mm:ss" /></td>
								<td>${entrustList.price}</td>
								<td>
								 <c:choose>
									<c:when test="${entrustList.amount>0}"><fmt:formatNumber value="${entrustList.amount}" pattern="#,#00"/></c:when>
									<c:when test="${entrustList.amount<=0}">0</c:when>
								</c:choose>
								</td>
								<td>
								 <c:choose>
									<c:when test="${entrustList.dealAmount>0}"><fmt:formatNumber value="${entrustList.dealAmount}" pattern="#,#00"/></c:when>
									<c:when test="${entrustList.dealAmount<=0}">0</c:when>
								</c:choose>
								</td>
								<td>
								<c:choose>
									<c:when test="${entrustList.residueAmount>0}"><fmt:formatNumber value="${entrustList.residueAmount}" pattern="#,#00"/></c:when>
									<c:when test="${entrustList.residueAmount<=0}">0</c:when>
								</c:choose>
								</td>
								<td>
								<c:if test="${entrustList.status==1}">已挂单</c:if>
								<c:if test="${entrustList.status==2}">部分成交</c:if>
								<c:if test="${entrustList.status==3}">全部成交</c:if>
								<c:if test="${entrustList.status==-1}">已撤销</c:if>
								</td>
								<td><a href="${basePath}/stockSeller/findEntrustDetail/${entrustList.id}.html">详情</a></td>
							</tr>
						</c:forEach>
						<c:if test="${fn:length(entrustList)<= 0}">
						<tr>
							<td colspan="9">暂无数据</td>
						</tr>	
						</c:if>

					</tbody>
				</table>

			</div>
		</div>
	</div>
</div>
	<!-- footer start   -->
	<%@ include file="/WEB-INF/page/stockright/common/footer.jsp"%>
	<!-- footer end     -->
</body>
<script type="text/javascript">
$(function(){  
	menuSelect(1);
}); 
/* 更改头像 */
function toAddHeadImg(){
		$.layer({
			type : 2,
			fix : false,
			shade : [0.6, '#E3E3E3' , true],
			shadeClose : true,
			border : [10 , 0.7 , '#272822', true],
			title : ['',false],
			offset : ['50px',''],
			area : ['500px','400px'],
			iframe : {src : '${basePath}/myaccount/addHeadImg.html'},
			close : function(index){
				layer.close(index); 
				window.location.reload();
			}
		});
}
//上传成功后，刷新
function refreshMyAccount() {
	location.href = "${path}/stockAccount/accountIndex.html";
}
</script>
</html>
