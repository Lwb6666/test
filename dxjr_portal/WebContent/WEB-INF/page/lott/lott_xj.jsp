<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/page/common/taglib.jsp"%>
<script type="text/javascript">
	 $(function(){
		$('#tip-bottom').poshytip({
			className: 'tip-yellowsimple',
			showTimeout: 1,
			alignTo: 'target',
			alignX: 'center',
			alignY: 'bottom',
			offsetX: 15,
			offsetY: 10,
			allowTipHover: false,
		});
		$(".close").click(function(){
		$(".bankcard-tip").fadeOut();
		});
	});
	function findPage(pageNo) {
		 window.parent.lott_xj_pageParent(pageNo);
	}
	 
</script>
</head>
<div class="reward change" style="clear: both">
	<div class="tbl-cont" id="dress-size">
		<!--    <div class="tbl-cont"> -->
		<div class="product-wrap">
			<div class="grid-1100">
				<div class="product-deatil clearfix ">
					<ul class="topcol3">
						<li><span class="f20 orange"><fmt:formatNumber value="${sycee }" pattern="###,###" /></span><span
							class="f14 gray3"> 元宝</span><br>我的工资<i class="iconfont yellow" style="cursor: pointer" id="tip-bottom" title="工资以元宝形式发放。元宝由平台待收元宝和论坛活动元宝组成，可用来在元宝商城兑换商品">&#xe608;</i></li>
						<li><span class="f20 orange"><fmt:formatNumber value="${lastdaySycee }" pattern="###,###" /></span><span
							class="f14 gray3"> 元宝</span><br>昨日工资</li>
						<li><span class="f20 orange"><fmt:formatNumber value="${honor }" pattern="###,###" /></span><span
							class="f14 gray3"> 元宝</span><br>我的荣誉值</li>
					</ul>
				</div>

				<div class="product-deatil clearfix ">
					<div class="tz-dqb2 clearfix">
						<div class="col clearfix">
							<span class="gray3 f16">投资待收送元宝 </span><a href="${basePath }/dingqibao.html" class="ml10">去投资></a>
							<span class="gray3 ml40 f16">论坛互动送元宝 </span><a href="##"
								class="ml10"><c:if test="${signItem == 0}"><a onclick="layer.msg('签到帖还未发出',1,5);" style="cursor: pointer;">去签到</a></c:if>
				                             <c:if test="${signItem != 0}"><a href="${bbsPath }/posts/${signItem }/1" target="_blank">去签到</a></c:if>></a> <span class="fr"><span class="red">*</span>
								您是尊贵的${ul.levelName }，享有${ul.o_sycee_ratio }倍待收元宝收入特权！</span>
						</div>
					</div>
				</div>
			</div>
		</div>
		<table width="100%" border="0" cellspacing="0" cellpadding="0"
			class="table center tbtr">
			<thead>
				<tr class="tbl-title">
					<td>时间</td>
					<td>收入</td>
					<td>支出</td>
					<td>结余元宝</td>
					<td>结余荣誉</td>
					<td>明细</td>
				</tr>
			<thead>
			<tbody>
				<c:if test="${page.totalPage>0}">
	            <c:forEach items="${page.result}" var="s" varStatus="status">
					<tr>
						<td ><fmt:formatDate value="${s.gainDate }" pattern="yyyy-MM-dd HH:mm:ss"></fmt:formatDate></td>
						<td ><fmt:formatNumber value="${s.accumulatePoints>=0?s.accumulatePoints:'' }" pattern="###,###" /><c:if test="${s.accumulatePoints<0 }">--</c:if></td>
						<td ><fmt:formatNumber value="${s.accumulatePoints<0?s.accumulatePoints*-1:'' }" pattern="###,###" /><c:if test="${s.accumulatePoints>=0 }">--</c:if></td>
						<td ><fmt:formatNumber value="${s.sycee }" pattern="###,###" /></td>
						<td ><fmt:formatNumber value="${s.honor }" pattern="###,###" /></td>
						<td style="text-align: right; padding-right: 15px;" >${s.detail }</td>
					</tr>
				</c:forEach>
				</c:if>
			<tbody>
		</table>
<c:if test="${page.result==null  || page.totalCount==0 }">
	<div align="center"  style="height: 70px;line-height: 70px">暂无相关信息</div>
</c:if>

<c:if test="${page.result !=null &&  page.totalCount >0 }">
<div>
	<div>
		<jsp:include page="/WEB-INF/page/common/ajaxpage.jsp">
			<jsp:param name="pageNo" value="${page.pageNo}" />
			<jsp:param name="totalPage" value="${page.totalPage}" />
			<jsp:param name="hasPre" value="${page.hasPre}" />
			<jsp:param name="prePage" value="${page.prePage}" />
			<jsp:param name="hasNext" value="${page.hasNext}" />
			<jsp:param name="nextPage" value="${page.nextPage}" />
		</jsp:include>
	</div>
</div>
</c:if>
	</div>
</div>
<script>
$(document).ready(function(){ 
    var color="#f0f7ff"
    $(".tbtr tr:even,.tbtr01 tr:even,.tbtr02 tr:even,.tbtr03 tr:even,.tbtr04 tr:even,.tbtr05 tr:even,.tbtr06 tr:even,.tbtr07 tr:even,.tbtr08 tr:even,.tbtr09 tr:even,.tbtr10 tr:even,.tbtr11 tr:even,.tbtr12 tr:even,.tbtr13 tr:even,.tbtr14 tr:even,.tbtr15 tr:even,.tbtr16 tr:even,.tbtr17 tr:even").attr("bgcolor",color);//改变奇数行背景色
})	
</script>