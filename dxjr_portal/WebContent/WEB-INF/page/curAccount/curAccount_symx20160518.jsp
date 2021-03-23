<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/page/common/taglib.jsp"%>

<!-- 收益明细 - 查询条件 s  -->
<div class="searchbox fl whitebg">

	<!-- 收益明细列表-查询条件 s -->
	<ul class="lb_title whitebg" style=" border-top:none;">
		<li>查询日期： 
		<input class="inputText02" name="beginDay" id="beginDay" class="Wdate" value="${beginDay}" onClick="WdatePicker();"> ~ 
		<input class="inputText02" name="endDay" id="endDay" class="Wdate" value="${endDay}" onClick="WdatePicker();">
		</li>
		
		<li class="lb_culi">
		<span class="lb_title_btn"> 
			<a href="javascript:void(0);" class="greens" onclick="searchBtnSymx(1,1)">查询</a>
		</span>
		</li>
		<li class="ml30 mdt10"><a class="textcenter pd-but mr5 red" href="javascript:void(0);" onclick="searchBtnSymx(1,7)">近7天</a></li>
		<li class="mdt10"><a class="textcenter pd-but" href="javascript:void(0);" onclick="searchBtnSymx(1,30)">近30天</a></li>
	</ul>
	<!-- 收益明细列表-查询条件 e -->

</div>
<!-- 收益明细 - 查询条件 e  -->

<!-- 收益明细 - 列表  s  -->
<div class="myidCur whitebg">

	<!-- 收益明细列表 s -->
	<table>
		<tr>
			<td>日期</td>
			<td>金额（元）</td>
			<td>发放时间</td>
		</tr>

		<c:forEach items="${page.result}" var="curInterestDetail" varStatus="sta">
			<tr <c:if test="${sta.index%2==0}">class="trcolor"</c:if>>
				<td><fmt:formatDate value="${curInterestDetail.interestDate }" pattern="yyyy-MM-dd" /></td>
				<td class="numcolor"><fmt:formatNumber value="${curInterestDetail.money }" pattern="#,##0.00" /></td>
				<td><fmt:formatDate value="${curInterestDetail.addtime}" pattern="yyyy-MM-dd HH:mm:ss" /></td>
			</tr>
		</c:forEach>
	</table>

	<c:if test="${page.result==null || page.totalCount==0 }">
		<span style="padding-left:350px;display:block;line-height: 70px;"> 您还没有收益信息 </span>
	</c:if>

	<!-- 收益明细列表 e -->

	<c:if test="${page.result!=null &&  page.totalCount!=0 }">
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

	/**
	 * ajax 翻页功能
	 */
	function findPage(pageNo) {
		window.parent.pageParent_symx(pageNo);
	}

	//查询 btn:  pageNum 作为tag标记
	function searchBtnSymx(pageNum, tag) {
		var beginDay = $("#beginDay").val();
		var endDay = $("#endDay").val();
		var start = new Date(beginDay.replace("-", "/").replace("-", "/"));
		var end = new Date(endDay.replace("-", "/").replace("-", "/"));
		if (end < start) {
			layer.msg("开始日期不能大于结束日期!", 2, 5);
			return false;
		}
		else
		{
			window.parent.search(pageNum, tag);
		}
	}
</script>