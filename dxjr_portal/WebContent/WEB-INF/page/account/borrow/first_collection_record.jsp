<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/page/common/taglib.jsp"%>

<title>我的账户_直通车列表_查看投标_顶玺金融</title>
<link href="${basePath}/css/style.css" rel="stylesheet" type="text/css" />
<script type="text/javascript">
	/**
	 * ajax 翻页功能
	 */
	function findPage(pageNo) {
		var firstTenderRealId = $("#firstTenderRealId").val();
		var n = $('#rec').attr("class");
		var collectionStatus = 'unRec';
		if(n== 'men_li'){
			collectionStatus = 'un';
		}else {
			collectionStatus = 'unRec';
		}
		window.parent
				.loadFirstTenderPage('${basePath}/account/InvestManager/collectionFirst_record/'
						+ pageNo + '.html?firstTenderRealId=' + firstTenderRealId+"&collectionStatus=" + collectionStatus);
	}
	/**
	 * hujianpan
	 * 查看借款协议
	 */
	function toTenderBorrowXiyi(id) {
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
	
	//下载 - 借款 -直- 未还款 
	function toTenderBorrowXiyiDownLoad(id) {
		url = '${basePath }/account/InvestManager/toBorrowXiyiDownLoad/' + id
						+ '.html';
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
	
	//下载-直-转让协议
	function toFirstTransferXiyiDownLoad(collectionId){
		url = '${basePath }/account/InvestManager/toFirstTransferXiyiDownLoad/' + collectionId
						+ '/1.html';
		layer.confirm("你确定要下载吗?",function (){
			$("#collection").attr("action", url);
			$("#collection").submit();
			layer.msg("下载成功!", 2, 9);
		});
	}
	
	/**
	 * hujianpan
	 * 切换统计数据,即已收和待收页签的切换
	 */
	function toggleListInner(firstTenderRealId,collectionStatus){
		window.parent.toggleList(firstTenderRealId,collectionStatus);
	}
	var tab;
	$(function(){
		tab = '${recSelect }';
		if(tab== 'unRec'){
			$("#un").attr("class",'men_li');
		}else{
			$("#rec").attr("class",'men_li');
		}
		
	});
</script>

<!--我的账户右侧开始 -->
<div class="men_title">
	<ul>
		<li id="un"  class=""><a href="javascript:toggleListInner('${firstTenderRealId}','unRec');">未还款列表</a></li>
		<li id="rec" class=""><a href="javascript:toggleListInner('${firstTenderRealId}','rec');">已还款列表</a></li>
	</ul>
</div>
<div class="myid whitebg"  style="border-top:none;">
 <form id="collection"  action="" method="post" > 
		<table border="0">
			<input type="hidden" size="12" name="firstTenderRealId"
				id="firstTenderRealId" value="${firstTenderRealId}" />
			<tr>
			 	<c:if test="${recSelect == 'unRec'}">
			 	<td width="123">应收款时间</td>
			 	</c:if>
				<c:if test="${recSelect != 'unRec'}">
			 	<td width="123">实收款时间</td>
			 	</c:if>
				
				<td width="74">借款标题</td>
				<td width="50">周期</td>
				<td width="65">预期利率</td>
				<td width="40">期数</td>
				<td width="84">收款总额(元)</td>
				<td width="84">应收本金(元)</td>
				<td width="84">应收利息(元)</td>
				<td width="95">逾期罚息(元)</td>
				<td width="55">状态</td>
				<td width="45">协议</td>

			</tr>
			<c:forEach items="${CollectionRecordList}" var="collectionRecord"
				varStatus="sta" step="1">
				<c:if test="${sta.index%2 == 0 }">
					<tr class="trcolor">
				</c:if>
				<c:if test="${sta.index%2 != 0 }">
					<tr>
				</c:if>

				<c:set var="borrow" value="${borrowMap[collectionRecord.tenderId]}"></c:set>
				<%-- <td>${sta.index + 1 }</td> --%>
				<c:if test="${recSelect == 'unRec'}">
				<td><c:if test="${collectionRecord.parentId!=null}"><font  color='red' >【直】</font></c:if>${collectionRecord.repay_timeStr }</td>
				</c:if>
				<c:if test="${recSelect != 'unRec'}">
				<td><c:if test="${collectionRecord.parentId!=null}"><font  color='red' >【直】</font></c:if>${(collectionRecord.advancetimeStr==null || collectionRecord.advancetimeStr=="")?"--":collectionRecord.advancetimeStr }</td>
				</c:if>
				<td width="64"><a title='${collectionRecord.name }'
					href="${path }/toubiao/${borrow.id}.html"
					target="_blank">${fn:substring(collectionRecord.name,0,7)}..</a></td>
				<td><c:if test="${borrow.borrowtype==4}">秒还</c:if> <c:if
						test="${borrow.borrowtype!=4 && borrow.style != 4 }"> ${collectionRecord.timeLimit }个月</c:if>
					<c:if test="${borrow.borrowtype!=4 && borrow.style == 4 }">${collectionRecord.timeLimit }天</c:if>
				</td>
				<td>${collectionRecord.apr }%</td>
				<%-- <td><a href="${path}/zhitongche.html?id=${collectionRecord.firstBorrowId}">第${collectionRecord.first_periods}期</a></td> --%>
				<td width="64">${collectionRecord.order}/${collectionRecord.borrowOrder}</td>
				<td><span class="numcolor">${collectionRecord.repayAccountStr}
						</span></td>
				<td><span class="numcolor">${collectionRecord.capitalStr }</span></td>
				<td><span class="numcolor">${collectionRecord.interestStr}</span></td>
				<td><span class="numcolor">${collectionRecord.lateInterestStr}
						</span></td>

				<%--          <td>${borrow.borrowType==1?"信用标":(borrow.borrowType==2?"抵押标":(borrow.borrowType==3?"净值标":(borrow.borrowType==4?"秒标":(borrow.borrowType==5?"担保标":"信用标")))) }</td>

          
          
          <td> ${collectionRecord.tenderTimeStr }</td>
 --%>

				<td width="45"><c:if test="${collectionRecord.status==1}">
			已还款
			</c:if> <c:if test="${collectionRecord.status==2}">
			垫付完成
			</c:if> <c:if test="${collectionRecord.status==3}">
			已还款
			</c:if> <c:if test="${collectionRecord.status==0}">
						<c:if test="${collectionRecord.lateDays>0}">
					逾期中
				</c:if>
						<c:if test="${collectionRecord.lateDays==0}">
					未还款
				</c:if>
					</c:if></td>
				<td width="45"><c:if test="${borrow.borrowtype!=4 }">
					<c:if test="${collectionRecord.parentId != null}">
						<a href="javascript:toFirstTransferXiyi('${collectionRecord.id}');">查看</a>
						<a href="javascript:toFirstTransferXiyiDownLoad('${collectionRecord.id}');">下载</a>
					</c:if>
					<c:if test="${collectionRecord.parentId == null}">
						<a href="javascript:toTenderBorrowXiyi('${collectionRecord.borrowId}');">查看</a>
						<a href="javascript:toTenderBorrowXiyiDownLoad('${collectionRecord.borrowId}');">下载</a>
					</c:if>
					</c:if> <c:if test="${borrow.borrowtype == 4 }">
		              	无
		            </c:if>
         		 </td>
				</tr>
			</c:forEach>
		</table>
</form>

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



