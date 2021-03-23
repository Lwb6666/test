<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/page/common/taglib.jsp"%>


<script type="text/javascript">
	var startTime = '${startTime}';
	var endTime = '${endTime}';
	var type = '${type}';
	/* var pageNum = '${pageNum}'; */

	/**
	 * ajax 翻页功能
	 */
	function findPage(pageNo) {
		turnFirstTenderRealPageParent(pageNo);
	}
	/**
	 * 优先计划列表翻页
	 */
	function turnFirstTenderRealPageParent(pageNum) {
		window.parent.search(pageNum);
	}

	/**
	 * 导出Excel
	 */
	function exportPaymentDetails() {
		var type = $("#payMentForm").find("[id=type]").val();
		if (null == type || "" == type) {
			var startTime = $("#payMentForm").find("[id=startTime]").val();
			var endTime = $("#payMentForm").find("[id=endTime]").val();
			//比较日期，必须是30天之内（含）
			startTime = startTime.replace(/-/g, "/");
			endTime = endTime.replace(/-/g, "/");
			startTime = new Date(startTime);
			endTime = new Date(endTime);

			var days = endTime.getTime() - startTime.getTime();
			var time = parseInt(days / (1000 * 60 * 60 * 24));
			if (isNaN(time) || time<0 || time>30) {
				layer.alert("请选择日期段或类型，如果选择日期段，天数在30天以内！");
				return;
			}
		}

		//设置类型名字
		$("#payMentForm").find("[id=typeName]").val(
				$("#exportPaymentForm").find("[id=type]").find(
						"option:selected").text());

		$("#payMentForm").attr("action",
				"${basePath}/accOpertingRecord/export/paymentDetails.html");
		$("#payMentForm").submit();
	}
	//查询
	function paymentDetailQuery() {
		var pageNum = 1;
		window.parent.search(pageNum);
	}
	function test(accountId){
		$.layer({
			type : 2,
			fix : false,
			shade : [0.6, '#E3E3E3' , true],
			shadeClose : true,
			border : [10 , 0.7 , '#272822', true],
			title : ['',false],
			offset : ['50px',''],
			area : ['850px','400px'],
			iframe : {src : '${basePath}/accOpertingRecord/showPaymentDetail.html?accountId='+accountId},
			close : function(index){
				layer.close(index);
				//window.open("${path}/page/account/myInvest.jsp","_self");
			}
		});
	}
	
</script>
<!--我的账户右侧开始  账户交易明细-->
<div class="lb_waikuang whitebg">
	<form id="payMentForm" action="" method="post">
		<div class="searchbox fl whitebg">

			<ul class="lb_title">
				<li>交易日期： <input class="inputText02" onClick="WdatePicker()"
					name="startTime" id="startTime" value="${startTime }"> ~ <input
					class="inputText02" id="endTime" name="endTime"
					onClick="WdatePicker()" value="${endTime }">
				</li>
				<li>交易类型：<select class="inputText01" name="type" id="type">
						<option value="">--所有类型--</option>
						<c:forEach items="${configurationList }" var="configuration">
							<option value="${configuration.name}"
								<c:if test="${configuration.name == type }">  selected="selected"  </c:if>>
								${configuration.value}</option>
						</c:forEach>
				</select>
				</li>
				<li class="lb_culi"><span class="lb_title_btn"><a
						href="javascript:paymentDetailQuery()">查询</a></span></li>
				<li class="lb_culi"><span class="lb_title_btn"><a
						href="javascript:exportPaymentDetails()">导出excel</a></span></li>
			</ul>
		</div>
	</form>



	<div class="myid">
		<table>
			<tr>
				<td width="120">交易日期</td>
				<td width="65">交易金额(元)</td>
				<td width="65">可用余额(元)</td>
				<td width="70">账户总额(元)</td>
				<td width="300">交易备注</td>
			</tr>

			<c:forEach items="${listUAVO}" var="vavo" varStatus="sta" step="1">
				<c:if test="${sta.index%2==0}">
					<tr class="trcolor" onclick="test(${vavo.id});" style="cursor: pointer;">
				</c:if>
				<c:if test="${sta.index%2!=0}">
					<tr onclick="test(${vavo.id});" style="cursor: pointer;">
				</c:if>

				<%-- <td width="6">${sta.index + 1 }</td>  --%>
				<td >${vavo.addtimeFMT }</td>
				<td class="numcolor" id="${sta.index}vavo_money">${vavo.moneyStr }</td>
				<td class="numcolor" id="${sta.index}vavo_use_money">${vavo.use_moneyStr}</td>
				<td class="numcolor" id="${sta.index}vavo_total">${vavo.totalStr }</td>
				<%-- <td id="${sta.index}vavo_money">${vavo.moneyStr }</td> --%>

				<td title ='${vavo.remarkStr}' ><a >
				${fn:substring(vavo.remarkStr,0,16)}
				<c:if test="${fn:length(vavo.remarkStr)>16}">..</c:if>
				</a>
				 <c:if test="${vavo.idType == null}">
				     
				 </c:if>
				 <c:if test="${vavo.idType == 0 or vavo.idType == 2}">
				     <c:if test="${not empty vavo.borrowId}">
           					(借款标：<a title='${vavo.borrowName }'
							href="${path}/toubiao/${vavo.borrowId}.html"
							target="_blank"> ${fn:substring(vavo.borrowName,0,10)} <c:if
								test="${fn:length(vavo.borrowName)>10}">..</c:if>

						    </a>)
           		      </c:if>
				 </c:if>
				 
				 <c:if test="${vavo.idType == 6}">
				     <c:if test="${not empty vavo.borrowId && vavo.remarkStr!='定期宝流宝' && vavo.remarkStr!='定期宝撤宝'}">
           					(<a title='${vavo.borrowName }'
							href="${path}/dingqibao/${vavo.borrowId}.html"
							target="_blank">详情

						    </a>)
           		      </c:if>
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

</div>
