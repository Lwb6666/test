<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/page/common/taglib.jsp"%>
<%@ include file="/WEB-INF/page/common/public.jsp"%>

<div class="searchbox fl whitebg">

	<div class="s_lott_change"
		style="height:60px;margin:0px 0px 0px 0px; border-left: 1px solid #dbdbdb; border-right: 1px solid #dbdbdb; ">

		<div class="xj_ye" style="float:left;line-height:60px;padding-left: 30px;">
			<span>可用余额: ￥<fmt:formatNumber value="${lottVo.useMoney}"
					pattern="#,##0.00" /> <c:if test="${lottVo==null }">  0  </c:if> 元
			</span>
		</div>

		<div class="xj_jh" style="float:right;line-height:60px;">
			<span
				style="width:100px;height:30px;font-size:14px;border:0px solid red;">
				剩余 &nbsp; <span style="font-size:20px;color:red;">${chanceTotalNum}
				<c:if test="${ling==0}">0</c:if></span> &nbsp;次抽奖机会
			&nbsp;
			</span> &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; <span><a
				href="${path }/lottery_chance/info.html">获得更多抽奖机会</a> &nbsp;</span>
		</div>
	</div>
</div>

<!--我的账户右侧  資金管理  提現明細-->
<div class="myid whitebg">

	<!-- sw_lott list s -->
	<table>
		<!-- title s -->
		<tr>
			<td><strong>金额</strong></td>
			<td><strong>获奖时间</strong></td>
			<td><strong>状态</strong></td>
			<td><strong>操作</strong></td>
		</tr>
		<!-- title e -->

		<!-- content s -->
		<c:forEach items="${lottLst}" var="lottLst_xj" varStatus="sta">
			<tr id="tr_oper"
				<c:if test="${sta.index%2==0}">class="trcolor"</c:if>>
				<td class="numcolor">￥<fmt:formatNumber
						value="${lottLst_xj.awardMoney}" pattern="#,##0.00" />
				</td>
				<td><fmt:formatDate value="${lottLst_xj.addTime  }"
						pattern="yyyy-MM-dd HH:mm:ss" /></td>
				<td>${lottLst_xj.status_z }</td>
				<td class="c_oper"><c:if test="${lottLst_xj.status==0 }">
						<a
							href="javascript:xj_sh(${lottLst_xj.id },${lottLst_xj.awardMoney })"
							title="使用" class="c_oper_a" style="color:blue;">${lottLst_xj.status_c }</a>
					</c:if> <c:if test="${lottLst_xj.status>0 }">
			           		 ${lottLst_xj.status_c }
			           </c:if></td>
		</c:forEach>
		<!-- content e -->

	</table>
	<!-- sw_lott list e -->



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


</div>

<script type="text/javascript">
	$(function() {
		

	});

	//使用奖励
	function xj_sh(id, mny) {
		//alert(id);
		$.ajax({
			url : '${basePath}/lottery_use_record/lott_xj_sh.html',
			data : {
				id : id
			},
			type : 'post',
			dataType : 'json',
			success : function(data) {
				if ("1" == data.code) {
					layer.msg("您已将" + mny + "元现金奖励充入可用余额！", 2, 1);
				} else {
					layer.msg(data.message, 2, 5);
				}
				findPage(1);
			},
			error : function(data) {
				layer.msg("网络连接异常，请刷新页面或稍后重试！", 1, 5);
			}
		});
	}

	/**
	 * ajax 翻页功能
	 */
	function findPage(pageNo) {
		window.parent.lott_xj_pageParent(pageNo);
	}
</script>