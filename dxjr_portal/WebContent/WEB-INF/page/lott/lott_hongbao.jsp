<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/page/common/taglib.jsp"%>

<script type="text/javascript">
     function findPage(pageNo) {
    	var awardType=$("#awardType").val();
	    window.parent.lott_hongbao_pageParent(pageNo,awardType);
     }
   //使用奖励
 	function xj_sh(id, mny) {
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
</script>
</head>
<div class="reward change" style="clear: both">
	<div class="tbl-cont" id="dress-size">
		<div class="tz-dqb2 clearfix">
			<div class="col clearfix">
				<span class="fl gary2">奖励类型：</span>
				<input type="hidden" id="awardType" value="${awardType}"/>
				<div class="btn-box-bg">
					<div class="type" id="all">
						<a href="javascript:queryRewardRecord(0)">全部</a>
					</div>
					<div class="type" id="yuanbao">
						<a href="javascript:queryRewardRecord(6)">元宝</a>
					</div>
					<div class="type" id="hongbao">
						<a href="javascript:queryRewardRecord(5)">红包</a>
					</div>
					<div class="type" id="cash">
						<a href="javascript:queryRewardRecord(1)">现金</a>
					</div>
					<div class="type" id="shiwu">
						<a href="javascript:queryRewardRecord(2)">实物</a>
					</div>
					<div class="type" id="choujiang">
						<a href="javascript:queryRewardRecord(4)">抽奖机会</a>
					</div>
				</div>
			</div>
			<div class="col clearfix">
				<span class="gray3 f16">可用抽奖机会 <span class="orange bold">${chanceTotalNum}</span>
					次
				</span><a href="${path }/lottery_chance/info.html" class="ml40">111111获得更多抽奖机会</a>
			</div>

		</div>
		<table width="100%" border="0" cellspacing="0" cellpadding="0"
			class="table center tbtr">
			<thead>
				<tr class="tbl-title">
					<td>获奖时间</td>
					<td>奖品</td>
					<td>来源</td>
					<td>状态/操作</td>
				</tr>
			<thead>
			<tbody>
			 <c:if test="${page.totalPage>0}">
			 <c:forEach items="${lottLst}" var="reward">
				<tr>
					    <td><fmt:formatDate value="${reward.rewardTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
						<td>${reward.rewardType}</td>
						<td>${reward.rewardSource}</td>
						<td>
						<c:choose>
						  <c:when test="${reward.rewardopert=='使用'}">
						  	 <a href="javascript:xj_sh(${reward.id },${reward.rewardMoney })" title="使用" class="c_oper_a" style="color:blue;">${reward.rewardopert}</a>
						  </c:when>
						  <c:when test="${reward.rewardopert=='领取'}">
					        <a href="javascript:void(0);" title="领取" onclick="showlingqu(${reward.id })"; data-reveal-id="lingqushiwu" data-animation="fade" class="c_oper_a1" style="color:blue;">${reward.rewardopert }</a>
						  </c:when>
						   <c:when test="${reward.rewardopert=='确认收货'}">
					        <a href="javascript:void(0);" onclick="showshouhuo(${reward.id })"; title="确认收货"  data-reveal-id="lingqush" data-animation="fade"  class="c_oper_a1" style="color:blue;">${reward.rewardopert }</a>
						  </c:when>
						  <c:otherwise>
						        ${reward.rewardStatus}
						  </c:otherwise>
						</c:choose>
						</td>
				</tr>
		   </c:forEach>
		   </c:if>
			<tbody>
		</table>
<c:if test="${page.result==null  || page.totalCount==0 }">
	<div align="center"  style="height: 70px;line-height: 70px">暂无相关信息</div>
</c:if> 
<c:if test="${page.result !=null &&  page.totalCount >0 }">
		<div>
			<div>
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
</c:if>		
</div>
</div>
<script>
$(document).ready(function(){ 
    var color="#f0f7ff"
    $(".tbtr tr:even,.tbtr01 tr:even,.tbtr02 tr:even,.tbtr03 tr:even,.tbtr04 tr:even,.tbtr05 tr:even,.tbtr06 tr:even,.tbtr07 tr:even,.tbtr08 tr:even,.tbtr09 tr:even,.tbtr10 tr:even,.tbtr11 tr:even,.tbtr12 tr:even,.tbtr13 tr:even,.tbtr14 tr:even,.tbtr15 tr:even,.tbtr16 tr:even,.tbtr17 tr:even").attr("bgcolor",color);//改变奇数行背景色
})	

function showshouhuo(id){
	$.ajax({
	    url: '${basePath}/lottery_use_record/swLingquQrsh.html',
		data :{
			lott_id:id
		} ,
		type : 'post',
		dataType : 'html',
		success : function(data){
			$("#lingqush").html(data);
		},
		error : function(data) {
			layer.msg("网络连接异常，请刷新页面或稍后重试！", 2, 5);
	    }
	});
}
function showlingqu(id){
		$.ajax({
		    url: '${basePath}/lottery_use_record/sw_lingqu.html',
			data :{
				lott_id:id
			} ,
			type : 'post',
			dataType : 'html',
			success : function(data){
				$("#lingqushiwu").html(data);
			},
			error : function(data) {
				layer.msg("网络连接异常，请刷新页面或稍后重试！", 2, 5);
		    }
		});
	}
</script>
<!--弹层end--->