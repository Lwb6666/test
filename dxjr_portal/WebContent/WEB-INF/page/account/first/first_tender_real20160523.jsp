<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/page/common/taglib.jsp"%>
<script type="text/javascript"
	src="${basePath}/js/BigDecimal-all-last.min.js"></script>
<script type="text/javascript">
//查看借款协议
function toFirstTenderXiyi(id){
	$.layer({
		type : 2,
		fix : false,
		shade : [0.6, '#E3E3E3' , true],
		shadeClose : true,
		border : [10 , 0.7 , '#272822', true],
		title : ['',false],
		offset : ['50px',''],
		area : ['1075px','450px'],
		iframe : {src : '${basePath}/account/InvestManager/toFirstTenderXiyi/' + id + '.html'},
		close : function(index){
			layer.close(index);
		}
	});
}



 

//查看直通车转让协议
function toFirstTransferXiyi(id){
	$.layer({
		type : 2,
		fix : false,
		shade : [0.6, '#E3E3E3' , true],
		shadeClose : true,
		border : [10 , 0.7 , '#272822', true],
		title : ['',false],
		offset : ['50px',''],
		area : ['1075px','450px'],
		iframe : {src : '${basePath}/account/InvestManager/toFirstRealTransferXiyi/' + id + '.html'},
		close : function(index){
			layer.close(index);
		}
	});
}



<%--
申请解锁 
--%>
function toUnlockFirst(firstTenderRealId){
	  if(layer.confirm("你确定要解锁吗?",function(){
		$.ajax({
			url : '${basePath}/first/tenderreal/unlock/'+firstTenderRealId+'.html',
			type : 'post',
			data : {
			},
			success : function(result) {
				if (result== 'success') {
					layer.alert("解锁成功" , 1, "温馨提示");
					findPage(1);
				} else {
					layer.alert(result);
				}
			},
			error : function(result) {
				layer.alert("操作异常,请刷新页面或稍后重试！");
		    }
		});
	}));

}

/**查询处理*/
function forqueryrecord(){
	var beginTime = $("#beginTime").val();
	var endTime = $("#endTime").val();
	var status = $("#status").val();
	window.parent.loadFirstTenderPage('${basePath}/account/InvestManager/queryFirstTenderReal/1.html?status='+status+'&beginTime=' + beginTime + '&endTime=' + endTime);
}
/**查看明细*/
function queryFirstTenderDetail(id){
	$("#ztclbSpanId").append('<span><a href="#">查看明细</a></span>');
	window.parent.loadFirstTenderPage('${basePath}/account/InvestManager/queryFirstTenderDetail.html?firstTenderRealId='+id+'&pageNum=1');
}
/**查看投标*/
function queryTenderRecord(id){
	$("#ztclbSpanId").append('<span><a href="#">查看投标</a></span>');
	window.parent.loadFirstTenderPage('${basePath}/account/InvestManager/collectionFirst_record/1.html?collectionStatus=unRec&firstTenderRealId='+id);
}
 
/**翻页处理*/
function findPage(pageNo){
	var beginTime = $("#beginTime").val();
	var endTime = $("#endTime").val();
	var status = $("#status").val();
	window.parent.loadFirstTenderPage('${basePath}/account/InvestManager/queryFirstTenderReal/'+pageNo+'.html?status='+status+'&beginTime=' + beginTime + '&endTime=' + endTime);
}
/** 直通车列表 */
function queryFirstTenderRealList(stauts){
	window.parent.loadFirstTenderPage('${basePath}/account/InvestManager/queryFirstTenderReal/1.html?status='+stauts);
}
</script>
<div id="menu_column">
    <div class="men_title col2">
         <ul>
          <li class="men_li"><a href="javascript:queryFirstTenderRealList(0);">未解锁</a> </li>
          <li><a href="javascript:queryFirstTenderRealList(1);">已解锁 </a></li>
         </ul>
    </div>
</div>
<input type="hidden" name="status" id="status" value="${status}">
<div id="main_content">
<div class="myid nobordertop whitebg">
	<br/>
	<form id="collection" action="" method="post">
		<ul class="lb_title" style="border:0px">
			<li class="one">开通日期：
			<input class="inputText02" name="beginTime"
						id="beginTime" class="Wdate" value="${beginTime}"
						onClick="WdatePicker();"> ~ 
			<input class="inputText02"
						name="endTime" id="endTime" class="Wdate" value="${endTime}"
						onClick="WdatePicker();">
			</li>
	
			<li class="lb_culi"><span class="lb_title_btn"><a
					href="javascript:forqueryrecord();">查询</a></span></li>
	
	
			<div class="tipone" style="margin-top:20px;">
				<span>温馨提示：</span> 1、直通车开通满6个月后方能进行解锁申请。&nbsp;&nbsp;&nbsp;&nbsp;  2、直通车投标按序号顺序依次进行投标。
			</div>
		</ul>
	</form>

	<div class="myid" style="border:0px;border-top:1px solid #dbdbdb;">
		<table width="100%" border="0">
			<tr>
				<td>开通时间</td>
				<td>序号（投标）</td>
				<td>开通金额（元）</td>
				<td>开通余额（元）</td>
				<td>已获收益</td>
				<td>操作</td>
			</tr>
			<c:forEach items="${list}" var="firstTenderReal" varStatus="sta"
				step="1">
				<c:if test="${sta.index%2 == 0 }">
					<tr class="trcolor">
				</c:if>
				<c:if test="${sta.index%2 != 0 }">
					<tr>
				</c:if>
				<td><fmt:formatDate value="${firstTenderReal.addtime}"
						pattern="yyyy-MM-dd HH:mm:ss"></fmt:formatDate></td>
				<td><span class="tipone"><c:if test="${firstTenderReal.parentId!=null}"><font  color='red' >【直】</font></c:if>第${firstTenderReal.orderNum}位</span></td>
				<td><fmt:formatNumber value="${firstTenderReal.account}" pattern="#,##0.00"/></td>
				<td>
				<fmt:formatNumber value="${firstTenderReal.useMoney}" pattern="#,##0.00"/>
				</td>
				<td>
				<fmt:formatNumber value="${firstTenderReal.firstEarnedIncome}" pattern="#,##0.00"/>
				</td>
				<td>
					<c:if test="${null != firstTenderReal.unLockYn && 'Y'==firstTenderReal.unLockYn}">
				            <a href="javascript:toUnlockFirst('${firstTenderReal.id}');">解锁申请</a>|</c:if>
					<a class="#" href="javascript:queryTenderRecord('${firstTenderReal.id}');">查看投标</a>|
					
						<c:choose>
							<c:when test="${firstTenderReal.parentId!=null}">
								<a class="#" href="javascript:toFirstTransferXiyi('${firstTenderReal.id}');" target="_parent">查看协议</a>
							</c:when>
							<c:otherwise>
								<a class="#" href="javascript:toFirstTenderXiyi('${firstTenderReal.id}');" target="_parent">查看协议</a>
							</c:otherwise>
						</c:choose>
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
</div>
