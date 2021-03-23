
<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/page/common/taglib.jsp"%>
<script type="text/javascript"
	src="${basePath}/js/My97DatePicker/WdatePicker.js"></script>
<div class="gonggao" style="margin-bottom:10px;margin-top:-5px;"><span> 没时间亲自投标？开启自动投标后，顶玺金融帮您轻松理财</span><a href="${path}/myaccount/autoInvest/autoInvestMain.html" class="btn-gg">自动投资</a></div>
	<div class="men_title col2">
	<ul>
		<li id="un" class=""><a href="javascript:toggleListInner('underway');">投标中的列表</a></li>
		<li id="rec" class="men_li"><a href="javascript:toggleListInner('sucess');">投标成功列表</a></li>
	</ul>
</div>
	<div class="searchbox fl whitebg">
		<ul class="lb_title"  style="border-top:none;">
			<li>借款标题：<input type="text" class="inputText02"
				name="borrowName" id="borrowName" value="${borrowName}"></li>
			<li>开始时间：<input class="inputText02" name="beginTime"
				id="beginTime" class="Wdate" value="${beginTime}"
				onClick="WdatePicker();"> ~ <input class="inputText02"
				name="endTime" id="endTime" class="Wdate" value="${endTime}"
				onClick="WdatePicker();"></li>
			<li class="lb_culi"><span class="lb_title_btn"><a
					href="javascript:;" onclick="queryTendering()">查询</a></span></li>
		</ul>
	</div>
	<div class="myid whitebg">
		<table>
			<tr>
				<td>投标时间</td>
				<td>投标金额</td>
				<td>借款标题</td>
				<td>借款标类型</td>
				<td>借款金额</td>
				<td>预期利率</td>
				<td>进度</td>
			</tr>
			<c:forEach items="${borrowlist}" var="borrow" varStatus="sta"
				step="1">
				<c:if test="${sta.index%2==0}">
					<tr class="trcolor">
				</c:if>
				<c:if test="${sta.index%2!=0}">
					<tr >
				</c:if>
				<td><fmt:formatDate value="${borrow.addtime }"
						pattern="yyyy-MM-dd" /></td>
				<td>￥${borrow.rbtAccount}</td>	
				<td><a target="_blank" href="${path }/toubiao/${borrow.id}.html">${fn:substring(borrow.name,0,7)}<c:if
							test="${fn:length(borrow.name)>7}">..</c:if></a>
							<c:if test="${borrow.tenderType==1}">
								<img src="${basePath}/images/account/zdicon.png"/>
							</c:if>	
							</td>
				<td>${portal:desc(300, borrow.borrowtype)}</td>
				<td>￥${borrow.accountStr}</td>
				<td><fmt:formatNumber value="${borrow.apr }" pattern="#,##0.##" />%</td>
				<td class="numcolor">${borrow.scheduleStr}%</td>
				</tr>
			</c:forEach>

		</table>

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
<script type="text/javascript">
/**
 * hujianpan
 * 切换统计数据,即已收和待收页签的切换
 */
function toggleListInner(borrowStatus){
	window.parent.toggleList(borrowStatus);
}
/**
 * ajax 翻页功能
 */
function findPage(pageNo) {
	window.parent.search(pageNo,'sucess');
}

//查询
function queryTendering(){
	var pageNum = 1;
	window.parent.search(pageNum,'sucess');
}
</script>