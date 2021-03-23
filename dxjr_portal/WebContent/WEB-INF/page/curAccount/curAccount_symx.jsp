<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/page/common/taglib.jsp"%>


<div class="product-deatil clearfix ">
   	<div class="tz-dqb2 clearfix">
        <div class="col clearfix">
           <span class="fl gary2">日期：</span>
            <div class="btn-box-bg">
                <div class="term"><a <c:if test="${tag == 'all'}">class="active"</c:if> href="javascript:searchBtnSymx(1,'all')">全部</a></div>  
            	<div class="term">
                	<input type="text" class="Wdate" value="${beginDay}"  name="beginDay" id="beginDay" onClick="WdatePicker();"> 至 
                	<input type="text" class="Wdate" value="${endDay}" name="endDay" id="endDay" onClick="WdatePicker();">
                </div>
                <div class="term"><a href="javascript:searchBtnSymx(1,1)" class="active">查询</a></div>
                
                <input type="hidden" id="tag" name="tag" value="${tag }"/>
                <div class="term"><a <c:if test="${tag == 'today'}">class="active"</c:if> href="javascript:searchBtnSymx(1,'today')" >今天</a></div>
                <div class="term"><a <c:if test="${tag == 'threemonth'}">class="active"</c:if> href="javascript:searchBtnSymx(1,'threemonth')">3个月</a></div>
                <div class="term"><a <c:if test="${tag == 'sixmonth'}">class="active"</c:if> href="javascript:searchBtnSymx(1,'sixmonth')">6个月</a></div>
            </div>
           <div class="fr"><a href="javascript:void(0);" onclick="openChat();" data-reveal-id="syChart" data-animation="fade"  class="btn btn-blue">图表</a></div>	
        </div>
     </div>
	<table width="100%" border="0" cellspacing="0" cellpadding="0" class="table center tbtr">
		<thead>
			<tr class="tbl-title">
				<td>日期</td>
				<td align="left">金额(元)</td>
				<td>收益发放时间</td>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${page.result}" var="curInterestDetail"
				varStatus="sta">
				<tr>
					<td><fmt:formatDate value="${curInterestDetail.interestDate }" pattern="yyyy-MM-dd" /></td>
					<td align="left"><fmt:formatNumber value="${curInterestDetail.money }" pattern="#,##0.00" /></td>
					<td><fmt:formatDate value="${curInterestDetail.addtime}" pattern="yyyy-MM-dd HH:mm:ss" /></td>
				</tr>
			</c:forEach>
		</tbody>
	</table>

	<c:if test="${page.result==null || page.totalCount==0 }">
		<div align="center" style="height: 70px;line-height: 70px" >暂无相关数据</div>
	</c:if>

	<c:if test="${page.result!=null &&  page.totalCount!=0 }">
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
	</c:if>
</div>

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
	//打开走势图框 
	function openChat(){
		showcharts();
	}
	
	function showcharts(){
		$.ajax({
			url:"${basePath}/curAccountController/interestDetailChart.html",
			type : 'post',
			success : function(data) {
				if(data.dateList.length > 0){
					$('#moneyChart').highcharts({
						title: {
				            text: '收益明细统计',
				            x: -20 //center
				        },
				        subtitle: {
				            text: '最近'+data.dateList.length+'笔收益金额变化曲线图',
				            x: -20
				        },
				        xAxis:  [{
				        	categories: data.dateList,
				    		type:'datetime',
				    		labels:{
				    		 	format:'{value:%Y-%m-%d}',
				    			align:'left',
								rotation:45
				         	}
				        }],
				        yAxis: {
				            title: {
				                text: '金额'
				            },
				            plotLines: [{
				                value: 0,
				                width: 1,
				                color: '#908080'
				            }]
				        },
				        tooltip: {
				            valueSuffix: '元',
				            xDateFormat: '%Y-%m-%d'
				        },
				        legend: {
				            layout: 'vertical',
				            align: 'right',
				            verticalAlign: 'middle',
				            borderWidth: 0
				        },
				        series: [{
				            name: '金额',
				            color: '#058DC7',
				            type: 'spline',
				            data: data.moneyList,
				            tooltip: {
				                valueSuffix: '元'
				            }
				        }]
			    	}); 	
				}else{
					$('#moneyChart').html('<ul class="lb_title whitebg" style=" border-top:none;"><li style="width:90%; ">您近期没有收益信息</li></ul>');
				}
			},
			error : function(data) {
				layer.msg("网络连接异常，请刷新页面或稍后重试！", 1, 5);
			}
		});
	}
	
	$(document).ready(function(){ 
         var color="#f0f7ff";
         $(".tbtr tr:even,.tbtr01 tr:even,.tbtr02 tr:even,.tbtr03 tr:even,.tbtr04 tr:even,.tbtr05 tr:even,.tbtr06 tr:even,.tbtr07 tr:even,.tbtr08 tr:even,.tbtr09 tr:even,.tbtr10 tr:even,.tbtr11 tr:even,.tbtr12 tr:even,.tbtr13 tr:even,.tbtr14 tr:even,.tbtr15 tr:even,.tbtr16 tr:even,.tbtr17 tr:even").attr("bgcolor",color);//改变奇数行背景色
   	});
</script>