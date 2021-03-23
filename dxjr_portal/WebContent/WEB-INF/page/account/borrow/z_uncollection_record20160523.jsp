<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/page/common/taglib.jsp"%>
<script type="text/javascript"
	src="${basePath}/js/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript">
/**
 * hujianpan
 * //查看借款协议
 */
function toBorrowXiyi(id){
	$.layer({
		type : 2,
		fix : false,
		shade : [0.6, '#E3E3E3' , true],
		shadeClose : true,
		border : [10 , 0.7 , '#272822', true],
		title : ['',false],
		offset : ['50px',''],
		area : ['1075px','450px'],
		iframe : {src : '${basePath }/account/InvestManager/toBorrowXiyi/'+id+'.html'},
		close : function(index){
			layer.close(index);
		}
	});
}

//下载-直-借款-待收
function toBorrowXiyiDownLoad(id){

	url = '${basePath }/account/InvestManager/toBorrowXiyiDownLoad/'+id+'.html';
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
			src : '${basePath }/account/InvestManager/toFirstTransferXiyi/' + collectionId
					+ '/1.html'
		},
		close : function(index) {
			layer.close(index);
		}
	});
}

//下载-直-债权转让协议
function toFirstTransferXiyiDownLoad(collectionId){
	url = '${basePath }/account/InvestManager/toFirstTransferXiyiDownLoad/' + collectionId + '/1.html';
	layer.confirm("你确定要下载吗?",function (){
		$("#collection").attr("action", url);
		$("#collection").submit();
		layer.msg("下载成功!", 2, 9);
	});
}

/**
 * 导出Excel
 */
function exportToExcel(a){
	exportCollectionRecord();
}

function exportCollectionRecord(url){
	url = '${basePath}/account/InvestManager/exportCollectionRecord/0/1.html';
	//url = "${path}/account/InvestManager/exportCollectionRecord/"+status+"/"+type_collection+".html";
	
	if(layer.confirm("你确定要导出吗?",function(){
		/* var type_collection = collectionType();
		var status = statusType(); */
		$("#collection").attr("action",url);
		$("#collection").submit();
		layer.alert("导出成功！",1);
	}));
	 
}
/**查询按钮*/
function forqueryrecord(actionName){
	window.parent.search(actionName,0,1,1);
}
/**翻页*/
function findPage(pageNo){
	window.parent.turnCollectionPageParent('unCollection_record',pageNo,0,1);
}
/**
 * hujianpan
 * 切换统计数据,即已收和待收页签的切换
 */
function toggleListInner(type){
	window.parent.toggleList(type);
}
</script>
<!--我的账户右侧开始 -->
<div class="men_title col2">
	<ul>
		<li id="un" class="men_li"><a href="javascript:toggleListInner('un');">待收明细</a></li>
		<li id="rec"  class=""><a href="javascript:toggleListInner('rec');">已收明细 </a></li>
	</ul>
</div>
	<form id="collection" action="" method="post">
		<div class="searchbox fl  whitebg">
			<ul class="lb_title nobordertop">
				<li>借款人：<input class="inputText02" name="keyword" id="keyword"
					value="${keyword}"></li>
				<li>应收款时间： 
					<input class="inputText02" name="beginTime" id="beginTime" class="Wdate" value="${beginTime}" onClick="WdatePicker();"> ~ 
					<input class="inputText02" name="endTime" id="endTime" class="Wdate" value="${endTime}" onClick="WdatePicker();">
				</li>
				<li class="lb_culi"><span class="lb_title_btn">
					<a href="javascript:forqueryrecord('unCollection_record');">查询</a></span></li>
				<li class="lb_culi"><span class="lb_title_btn"><a
						href="javascript:exportToExcel('');">导出excel</a></span></li>
			</ul>
		</div>
	</form>
	<div class="clear"></div>
	<div class="tabtoptext whitebg">
		查询区间段待收总额： <a href="#">￥${collectionStatisticVo.totalSumFmt }</a>
		，其中本金：<a href="#">￥${collectionStatisticVo.capitalSumFmt }</a> ，利息：<a
			href="#">￥${collectionStatisticVo.interestSumFmt }</a> ，逾期罚息：<a
			href="#">￥${collectionStatisticVo.lateInterestSumFmt }</a>
	</div>
	<div class="myid whitebg">
		<table border="0">
			<tr >
				<td>应收款时间</td>
				<td>借款标题</a></td>
				<td>周期</td>
				<td>年化利率</td>
				<td>期数</td>
				<td>收款总额</td>
				<td>应收本金</td>
				<td>应收利息</td>
				<td>逾期罚息</td>
				<td>协议</td>
			</tr>
			<c:forEach items="${CollectionRecordList}" var="collectionRecord"
				varStatus="sta" step="1">
				<c:if test="${sta.index%2==0}">
					<tr class="trcolor">
				</c:if>
				<c:if test="${sta.index%2!=0}">
					<tr>
				</c:if>
				<c:set var="borrow" value="${borrowMap[collectionRecord.tenderId]}"></c:set>
				<%-- <td>${sta.index + 1 }</td> --%>
				<td width="100">${collectionRecord.repay_timeStr }</td>
				<td width="154"><c:if test="${collectionRecord.parentId!=null}"><font  color='red' >【直】</font></c:if><a title = '${collectionRecord.name }' 
					href="${path }/toubiao/${borrow.id}.html"
					target="_blank">${fn:substring(collectionRecord.name,0,10)}
					<c:if test="${fn:length(collectionRecord.name)>10}">..</c:if>
					</a></td>
				<td width="64"><c:if test="${borrow.borrowtype==4}">秒还</c:if> <c:if
						test="${borrow.borrowtype!=4 && borrow.style != 4 }"> ${collectionRecord.timeLimit }个月</c:if>
					<c:if test="${borrow.borrowtype!=4 && borrow.style == 4 }">${collectionRecord.timeLimit }天</c:if>
				</td>
				<td width="88">${collectionRecord.apr }%</td>
				<td width="64">${collectionRecord.order}/${collectionRecord.borrowOrder}</td>
				<td width="108" class="numcolor">${collectionRecord.repayAccountStr}
					元</td>
				<td width="96" class="numcolor">${collectionRecord.capitalStr }元</td>
				<td width="88" class="numcolor">${collectionRecord.interestStr}元</td>
				<td width="68" class="numcolor"><fmt:formatNumber
						value="${collectionRecord.lateInterest}" pattern="#,##0.00" /> 元</td>
				<td width="46">
					<c:if test="${collectionRecord.parentId != null}">
						<a href="javascript:toFirstTransferXiyi('${collectionRecord.id}');">查看</a>
						<a href="javascript:toFirstTransferXiyiDownLoad('${collectionRecord.id}');">下载</a>
					</c:if>
					<c:if test="${collectionRecord.parentId == null && borrow.borrowtype != 4 }">
						<a href="javascript:toBorrowXiyi('${collectionRecord.borrowId}');">查看
						<a href="javascript:toBorrowXiyiDownLoad('${collectionRecord.borrowId}');">下载</a>
					</a>
					</c:if> <c:if test="${collectionRecord.parentId == null && borrow.borrowtype == 4 }">
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