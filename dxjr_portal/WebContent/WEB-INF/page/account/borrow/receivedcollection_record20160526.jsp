<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/page/common/taglib.jsp"%>
<script type="text/javascript">
	/**
	 * hujianpan
	 * 查看借款协议
	 */
	function toBorrowXiyi(id) {
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
				src : '${basePath }/account/InvestManager/toBorrowXiyi/' + id
						+ '.html'
			},
			close : function(index) {
				layer.close(index);
			}
		});
	}
	
	/**
	 * 
	 * 下载借款协议
	 */
	function toBorrowXiyiDownLoad(id) {
		url = '${basePath }/account/InvestManager/toBorrowXiyiDownLoad/' + id+ '.html';
		layer.confirm("你确定要下载吗?",function (){
			$("#collection").attr("action", url);
			$("#collection").submit();
			layer.msg("下载成功!", 2, 9);
		});
	}
	
	// 查看债权转让协议
	function toTransferXiyi(collectionId){
		//查看借款协议
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
				src : '${basePath }/account/InvestManager/toTransferXiyi/' + collectionId
						+ '.html'
			},
			close : function(index) {
				layer.close(index);
			}
		});
	}
	
	//下载债权转让协议
	function toTransferXiyiDownLoad(collectionId){
		url = '${basePath }/account/InvestManager/toTransferXiyiDownLoad/' + collectionId+ '.html';
		layer.confirm("你确定要下载吗?",function (){
			$("#collection").attr("action", url);
			$("#collection").submit();
			layer.msg("下载成功!", 2, 9);
		});
	}
	
	//查看直通车转让协议
	function toFirstTransferXiyi(collectionId){
		//查看借款协议
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
				src : '${basePath }/account/InvestManager/toFirstTransferXiyi/' + collectionId + '/0.html'
			},
			close : function(index) {
				layer.close(index);
			}
		});
	}
	
	//下载- 普待-已收-转让
	function toFirstTransferXiyiDownLoad(collectionId){
		url = '${basePath }/account/InvestManager/toFirstTransferXiyiDownLoad/' + collectionId + '/0.html';
		layer.confirm("你确定要下载吗?",function (){
			$("#collection").attr("action", url);
			$("#collection").submit();
			layer.msg("下载成功!", 2, 9);
		});
	}
	

	/**
	 * 导出Excel
	 */
	function exportToExcel(a) {
		exportCollectionRecord();
	}

	function exportCollectionRecord(url) {
		url = '${basePath}/account/InvestManager/exportCollectionRecord/1/0.html';
		//if();
		layer.confirm("你确定要导出吗?",function (){
			$("#collection").attr("action", url);
			$("#collection").submit();
			layer.alert("导出成功！",1);
		});
		 
	}
	/**
	 * hujianpan
	 * 查询功能
	 */
	function forqueryrecord(actionName) {
		window.parent.search(actionName, 1, 0, 1);
	}
	/**翻页*/
	function findPage(pageNo) {
		window.parent.turnCollectionPageParent('receivedCollection_record',
				pageNo, 1, 0);
	}
	/**
	 * hujianpan
	 * 切换统计数据,即已收和待收页签的切换
	 */
	function toggleListInner(type){
		window.parent.toggleList(type);
	}
</script>
<div class="gonggao" style="margin-bottom:10px;margin-top:-5px;"><span> 没时间亲自投标？开启自动投标后，顶玺金融帮您轻松理财</span><a href="${path}/myaccount/autoInvest/autoInvestMain.html" class="btn-gg">自动投标</a></div>
<div class="men_title col2">
	<ul>
		<li id="un" class=""><a href="javascript:toggleListInner('un');">待收明细</a></li>
		<li id="rec" class="men_li"><a href="javascript:toggleListInner('rec');">已收明细 </a></li>
	</ul>
</div>
	<form id="collection" action="" method="post">
		<div class="searchbox fl  whitebg">
			<ul class="lb_title nobordertop">
				<li>借款人：<input class="inputText02" name="keyword" id="keyword"
					value="${keyword}"></li>
				<li>实收款时间： <input class="inputText02" name="beginTime"
					id="beginTime" class="Wdate" value="${beginTime}"
					onClick="WdatePicker();"> ~ <input class="inputText02"
					name="endTime" id="endTime" class="Wdate" value="${endTime}"
					onClick="WdatePicker();"></li>
				<li class="lb_culi"><span class="lb_title_btn"><a
						href="javascript:forqueryrecord('receivedCollection_record');">查询</a></span></li>
				<li class="lb_culi"><span class="lb_title_btn"><a
						href="javascript:exportToExcel('');">导出excel</a></span></li>
			</ul>
		</div>
	</form>
	<div class="clear"></div>
	<div class="tabtoptext whitebg">
		查询区间段已收总额： <a href="#">￥${collectionStatisticVo.totalSumFmt }</a>
		，其中本金：<a href="#">￥${collectionStatisticVo.capitalSumFmt }</a> ，利息：<a
			href="#">￥${collectionStatisticVo.interestSumFmt }</a> ，逾期罚息：<a
			href="#">￥${collectionStatisticVo.lateInterestSumFmt }</a>
	</div>

	<div class="myid whitebg">
		<table border="0">
			<tr >
				<td>实收款时间</td>
				<td>借款标题</td>
				<td>周期</td>
				<td>年化利率</td>
				<td>期数</td>
				<td>收款总额</td>
				<td>实收总额</td>
				<td>应收本金</td>
				<td>应收利息</td>
				<td>逾期罚息</td>
				<td>协议</td>
			</tr>
			<c:forEach items="${CollectionRecordList}" var="collectionRecord" varStatus="sta" step="1">
				<c:if test="${sta.index%2==0}">
					<tr class="trcolor">
				</c:if>
				<c:if test="${sta.index%2!=0}">
					<tr>
				</c:if>
				<c:set var="borrow" value="${borrowMap[collectionRecord.tenderId]}"></c:set>
				<%-- <td>${sta.index + 1 }</td> --%>
				<td width="100">${collectionRecord.advancetimeStr }</td>
				<td width="154"> 
					<c:if test="${collectionRecord.parentId!=null}">
						<font  color='red' >[转]</font>
					</c:if>  
					<a title = '${collectionRecord.name }' 
						href="${path }/toubiao/${borrow.id}.html"
						target="_blank">
						${fn:substring(collectionRecord.name,0,7)}
					<c:if test="${fn:length(collectionRecord.name)>7}">..</c:if>
					</a>
				</td>
				<td width="64"><c:if test="${borrow.borrowtype==4}">秒还</c:if> <c:if
						test="${borrow.borrowtype!=4 && borrow.style != 4 }"> ${collectionRecord.timeLimit }个月</c:if>
					<c:if test="${borrow.borrowtype!=4 && borrow.style == 4 }">${collectionRecord.timeLimit }天</c:if>
				</td>
				<td width="88">${collectionRecord.apr }%</td>
				<td width="64">${collectionRecord.order}/${collectionRecord.borrowOrder}</td>
				<td width="98" class="numcolor">${collectionRecord.repayAccountStr}元</td>
				<td width="98" class="numcolor">
					<div style="float:center;cursor:pointer; position:relative;" onmousemove="$(this).find('div').show();" onmouseout="$(this).find('div').hide();">
					${collectionRecord.getYesAccountStr}元
					<div class="poptip-content" style="display: none;" >
             		 当前实收总额[<span class="color">${collectionRecord.getYesAccountStr}</span>] 
             		 = 实收本金[<span class="color">${collectionRecord.capitalStr }</span>] 
             		 + 实收利息[<span class="color">${collectionRecord.yesInterestStr }</span>]  
             		 - 利息管理费[<span class="color">${collectionRecord.inverestFeeStr }</span>]  
					</div>
					</div>
				</td>
				<td width="96" class="numcolor">${collectionRecord.capitalStr }元</td>
				<td width="88" class="numcolor">${collectionRecord.interestStr}元</td>
				<td width="68"  class="numcolor"><fmt:formatNumber value="${collectionRecord.lateInterest}" pattern="#,##0.00" /> 元</td>
				<td width="46">
					<c:if test="${collectionRecord.parentId != null and collectionRecord.firstTenderRealId == null}">
						<a href="javascript:toTransferXiyi('${collectionRecord.id}');">查看</a>
						<a href="javascript:toTransferXiyiDownLoad('${collectionRecord.id}');">下载</a>
					</c:if>
					<c:if test="${collectionRecord.parentId != null and collectionRecord.firstTenderRealId != null}">
						<a href="javascript:toFirstTransferXiyi('${collectionRecord.id}');">查看</a>
						<a href="javascript:toFirstTransferXiyiDownLoad('${collectionRecord.id}');">下载</a>
					</c:if>
					<c:if test="${collectionRecord.parentId == null && borrow.borrowtype != 4 }">
						<a href="javascript:toBorrowXiyi('${collectionRecord.borrowId}');">查看
						</a>
						<a href="javascript:toBorrowXiyiDownLoad('${collectionRecord.borrowId}');">下载 </a>
					</c:if> 
					<c:if test="${collectionRecord.parentId == null && borrow.borrowtype == 4 }">
              			无
        			</c:if>
			</td>
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