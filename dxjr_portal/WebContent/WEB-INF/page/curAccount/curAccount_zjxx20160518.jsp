<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/page/common/taglib.jsp"%>

<!-- 资金信息 查询条件 s -->

<div class="searchbox fl whitebg">

	<ul class="lb_title whitebg" style=" border-top:none;">

		<li>类型： <select id="select_type" class="bd-line" style=" width:102px;height:33px">
				<option value="">--请选择--</option>
				<c:forEach items="${loadMap }" var="loadMapVar">
					<option value="${loadMapVar.key}">${loadMapVar.value}</option>
				</c:forEach>
		</select>

		</li>

		<li>查询日期： <input class="inputText02" name="beginDay" id="beginDay" class="Wdate" value="${beginDay}" onClick="WdatePicker();"> ~ <input
			class="inputText02" name="endDay" id="endDay" class="Wdate" value="${endDay}" onClick="WdatePicker();">
		</li>
		<li class="lb_culi"><span class="lb_title_btn"> <a href="javascript:searchBtnZjxx(1);" class="greens">查询</a>
		</span></li>
	</ul>

</div>

<!-- 资金信息 查询条件 e -->

<!-- 资金信息 列表 s -->
<div class="myidCur whitebg">

	<!-- 资金信息 列表- fenye s -->
	<table>
		<tr>
			<td><div>活期宝资产 （元）</div></td>
			<td>类型</td>
			<td>金额（元）</td>
			<td>日期</td>
		</tr>
		<c:forEach items="${page.result}" var="curAccountLog" varStatus="sta">
			<tr <c:if test="${sta.index%2==0}">class="trcolor"</c:if>>
				<td><span class="numcolor"> <fmt:formatNumber value="${curAccountLog.total } " pattern="#,##0.00" />
				</span></td>
				<td>${curAccountLog.type_z }</td>
				<td class="numcolor"><fmt:formatNumber value="${curAccountLog.money } " pattern="#,##0.00" /></td>
				<td><fmt:formatDate value="${curAccountLog.addtime }" pattern="yyyy-MM-dd HH:mm:ss" /></td>
			</tr>
		</c:forEach>

	</table>
	<c:if test="${page.result==null  || page.totalCount==0 }">
		<span style="padding-left:350px;display:block;line-height: 70px;"> 您还没有资金信息 </span>
	</c:if>
	<!-- 资金信息 列表- fenye e -->

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
<!-- 资金信息 列表 e -->
<script type="text/javascript">
	var selectIndex;
	$(function() {
		$(document).ready(function() {
			$("#select_type").get(0).selectedIndex = selectIndex;
			$("#select_type").change(function() {
				SelectChange();
			});
		});
	});

	//获取下拉框选中项的index属性值
	function SelectChange() {
		selectIndex = $("#select_type").get(0).selectedIndex;
	}

	/**
	 * ajax 翻页功能
	 */
	function findPage(pageNo) {
		window.parent.pageParent_zjxx(pageNo);
	}

	//资金信息-查询 btn: 
	function searchBtnZjxx(pageNum) {
		var beginDay = $("#beginDay").val();
		var endDay = $("#endDay").val();
		var start = new Date(beginDay.replace("-", "/").replace("-", "/"));
		var end = new Date(endDay.replace("-", "/").replace("-", "/"));
		if (end < start) {
			layer.msg("开始日期不能大于结束日期!", 2, 5);
			return false;
		} else {
			window.parent.search_zjxx(pageNum);
		}
	}
</script>