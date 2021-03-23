<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/page/common/taglib.jsp"%>
<%@ include file="/WEB-INF/page/common/public.jsp"%>
<script type="text/javascript">
	$(document).ready(function() {
		$(".warpred").mousemove(function(){
			$(this).next(".wraplayer").show();
		 }).mouseleave(function(){
			 $(this).next(".wraplayer").hide();
		 });
	});
</script>

<!-- 收益明细列表-查询条件 s -->
<ul class="dqb-lb_title whitebg" id="topupInnerDivDynic"
	style="border-top: none;">

	<li><span id="quanbu" class="dqb-lb_title_checked"
		style="margin: 0 15px 0 5px" onclick="searchBtn_tcz(1,'',this)">全
			部</span></li>
	<li><span id="month1" onclick="searchBtn_tcz(1,1,this)">1个月</span></li>
	<li><span id="month3" onclick="searchBtn_tcz(1,3,this)">3个月</span></li>
	<li><span id="month6" onclick="searchBtn_tcz(1,6,this)">6个月</span></li>
	<li><span id="month12" onclick="searchBtn_tcz(1,12,this)">12个月</span></li>
</ul>
<!-- 收益明细列表-查询条件 e -->

</div>
<!-- 收益明细 - 查询条件 e  -->

<!-- 收益明细 - 列表  s  -->
<div class=" dqb-myid whitebg nobordertop" style="margin-bottom: 20px;">
	<!-- 收益明细列表 s -->
	<table>
		<tr>
			<td style="width: 36%">名称</td>
			<td>年化收益</td>
			<td>加入金额（元）</td>
			<td>已赚收益（元）</td>
			<td>投标次数（次）</td>
			<td>退出日期</td>
		</tr>

		<c:forEach items="${page.result}" var="fixBorrowJoinDetail"
			varStatus="sta">
			<tr <c:if test="${sta.index%2==0}">class="dqb-tr1color"</c:if>
				<c:if test="${sta.index%2!=0}">class="dqb-tr2color"</c:if>>

				<td><a style="color: #329acd;"
					href="${path }/dingqibao/detailTcz/${fixBorrowJoinDetail.fixBorrowId }.html">
						定期宝<span style="padding: 0 0 0 15px;">${fixBorrowJoinDetail.lockLimit}个月</span>（${fixBorrowJoinDetail.contractNo}）
						<c:if test="${fixBorrowJoinDetail.redMoney>0}">
							<div class="warpbd">
								<span class="warpred" style="cursor: pointer;"></span> <span
									class="wraplayer">使用<fmt:formatNumber
										value="${fixBorrowJoinDetail.redMoney }" pattern="#,##0.##" />元红包
								</span>
							</div>
						</c:if>
				</a> <c:if test="${fixBorrowJoinDetail.areaType==1}">
						<img src="${basePath}/images/new-icon.png?version=<%=version%>" />
					</c:if></td>
				<td style="width: 12%"><fmt:formatNumber
						value="${fixBorrowJoinDetail.apr }" pattern="##.##" />%</td>

				<td style="width: 14%"><fmt:formatNumber
						value="${fixBorrowJoinDetail.account }" pattern="#,##0" /> <c:if
						test="${fixBorrowJoinDetail.tenderType==1}">
						<img src="${basePath}/images/account/zdicon.png?version=<%=version%>" />
					</c:if></td>
				<td style="width: 14%">${fixBorrowJoinDetail.hasReturnMoneyStr }
				</td>

				<td style="width: 14%"><a style="color: #329acd;"
					href="${path }/dingqibao/tbDetail/${fixBorrowJoinDetail.fixBorrowId }/1.html">
						${fixBorrowJoinDetail.cnt } </a></td>
				<td><fmt:formatDate value="${fixBorrowJoinDetail.lockEndTime }"
						pattern="yyyy-MM-dd" /></td>
			</tr>
		</c:forEach>
	</table>
	<input name="lockstyle" type="hidden" id="lockstyle" value="${tag}"></input>
	<c:if test="${page.result==null }">
		<span style="padding-left: 350px; display: block;"> 没有加入信息 </span>
	</c:if>

	<!-- 收益明细列表 e -->


	<c:if test="${page.result!=null }">
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
		<!-- fenye e -->


	</c:if>



</div>
<!-- 收益明细 - 列表  e  -->

<script type="text/javascript">
	$(function() {

	});

	/**
	 * ajax 翻页功能
	 */
	function findPage(pageNo) {
		var lockstyle = $("#lockstyle").val();
		window.parent.pageParent_tcz(pageNo, lockstyle);
	}

	//查询 btn:  pageNum 作为tag标记
	function searchBtn_tcz(pageNum, tag, obj) {
		$("#lockstyle").val(tag);
		window.parent.search_tcz(pageNum, tag, obj);
	}
</script>