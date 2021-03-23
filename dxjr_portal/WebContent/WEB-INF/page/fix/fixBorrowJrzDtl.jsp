<%@page import="com.dxjr.utils.DateUtils"%>
<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/page/common/taglib.jsp"%>

<!doctype html>
<html>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="keywords" content="顶玺金融,金融,投资,理财" />
<meta name="description"
	content="顶玺金融提供安全、有担保的互联网理财投资服务。风险保证金保障！随时可赎回！上顶玺，好收益！">
<meta name="author" content="顶玺金融" />
<meta name="copyright" content="2014 上海顶玺金融信息服务有限公司" />
<%@ include file="/WEB-INF/page/common/public4.jsp"%>
<head>
<title>顶玺金融</title>
</head>

<body>

	<%@ include file="/WEB-INF/page/common/header.jsp"%>
	<!--头部end-->

	<!--main-->
	<div id="myaccount">

		<%@ include file="/WEB-INF/page/common/topmenu.jsp"%>


		<!--banner star-->
		<%@ include file="/WEB-INF/page/common/wraper.jsp"%>



		<div class="wraper mac-box mt20">
			<div class="myacc-list baifen33 clearfix">
				<span class="fr mr20"><a href="javascript:toShowFixXiyi('${fixTenderDetailVo.fixBorrowId}');"> 查看定期宝服务协议</a> <a href="javascript:history.back();"
					class="btn-small btn-blue">返回</a></span> <span class="blue f20 ml40">定期宝</span>
				<span>${fixTenderDetailVo.lockLimit}个月
					（${fixTenderDetailVo.contractNo}） <a href="javascript:toFixBorrowDetail(${fixTenderDetailVo.fixBorrowId});">查看详情</a>
				</span>
			</div>

			<div class="myacc-into clearboth">
				<div class="myacc-dqb fl">
					<p class="my-hq">
						<em>年化收益</em><span class="f20">
						<fmt:formatNumber value="${fixTenderDetailVo.apr}" pattern="##.##"/>%</span>
					</p>
					<p class="my-hq">
						<em>已赚利息</em><span class="f20 red">0.00</span><strong
							class="gary2">元</strong>
					</p>
					<p class="my-hq">
						<em>加入金额</em><span>
						<fmt:formatNumber value="${fixTenderDetailVo.account }" pattern="#,##0.00" />元</span>
					</p>
					<p class="my-hq">
						<em>预计收益</em><span>${fixTenderDetailVo.yqsyStr}元</span>
					</p>
				</div>

				<div class="myacc-right fl">
					<div class="zhuantai f24">加入中</div>
				</div>
			</div>
		</div>

		<!--活期宝star-->
		<div class="wraper mt20">
			<div class="prduct-menu  background clearfix">
				<div class="menu-tbl">
					<ul class="col2">
						<li class="active">加入明细</li>
					</ul>
				</div>
				<div class="menucont">
					<div class="tbl-cont">
						<p>&nbsp;</p>
						<div id="tab1tb" class="product-deatil">
							<table width="100%" border="0" cellspacing="0" cellpadding="0" class="table center tbtr02">
		<thead>
		<tr class="tbl-title">
			<td>加入金额(元)</td>
			<td>加入时间</td>
		</tr>
	</thead>
	<tbody>
		<c:forEach items="${page.result}" var="fixBorrowJoinDetail" varStatus="sta">
			<tr>
				<td><fmt:formatNumber value="${fixBorrowJoinDetail.account }" pattern="#,##0.00" />
					<c:if test="${fixBorrowJoinDetail.tenderType==1}">
			          <img src="${basePath}/images/account/zdicon.png"/>
                    </c:if>	
				</td>
				<td>
					<fmt:formatDate value="${fixBorrowJoinDetail.addTime}" pattern="yyyy-MM-dd HH:mm:ss" />
				</td>
			</tr>
		</c:forEach>

	</tbody>
</table>

<c:if test="${page.totalPage == 0}">
	<div align="center">暂无数据</div>
</c:if>

		<!-- fenye s -->  
		<div class="yema">
			<div class="yema_cont">
				<div class="yema rt">
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
		</div>
							
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	
		<!--浮动-->
	<div class="clearfix bompd20"></div>
	
	<%@ include file="/WEB-INF/page/common/footer.jsp"%>


<script type="text/javascript">
$(function(){
	$("#tzgl").attr("class","active");  //添加样式 
	$("#dqb").attr("class","active");  //添加样式 

})
function findPage(pageNo) {
	window.location.href ='${path}/dingqibao/detailJrz/' +'${fixTenderDetailVo.fixBorrowId }'+'/'+ pageNo+".html";
	
}
/**
 *  show 协议
 */
function toShowFixXiyi(id) {
	$.layer({
		type : 2,
		fix : false,
		shade : [ 0.6, '#E3E3E3', true ],
		shadeClose : true,
		border : [ 10, 0.7, '#272822', true ],
		title : [ '', false ],
		offset : [ '50px', '' ],
		area : [ '1075px', '450px' ],
		iframe : {
			src : '${basePath }/dingqibao/toShowFixXiyi/' + id + '.html'
		},
		close : function(index) {
			layer.close(index);
		}
	});
}
/**
 * 定期宝详细
 */
function toFixBorrowDetail(fixBorrowId){
	window.open("${basePath}/dingqibao/"+fixBorrowId+".html"); 
};

</script>

	
</body>
</html>
