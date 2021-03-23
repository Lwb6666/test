<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/page/common/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ include file="/WEB-INF/page/common/public.jsp"%>
<title>实时财务_顶玺金融</title>
<!-- 图形报表js -->
<link href="${basePath}/css/base.css?version=<%=version%>" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${basePath}/js/highcharts/highcharts3.0.7.js" ></script>
<script type="text/javascript" src="${basePath}/js/highcharts/themes/grid.js"></script>
<script type="text/javascript" src="${basePath}/js/highcharts/myChart.js"></script>
<style type="text/css">
#data{width:1100px; margin:10px auto 0 auto; background:#fff; padding-bottom:20px;}
#data h1{height:35px; background:#f0f7ff; padding-left:20px; line-height:35px; color:#333; font-size:16px;}
#data table{width:1060px; margin:20px auto 0 auto; font-size:14px;}
#data table thead td{height:40px;line-height:40px; background:#f0f7ff; font-size:14px; color:#8eb7d0; padding:0 15px;}
#data table tbody td{border:1px solid #ebebeb; padding:10px 40px;}
#data table tbody td.title{ text-align:right; color:#9dc1d6;width: 200px;}
#data table tbody td a{ color:#9dc1d6;}
#data table tbody td a:hover{ text-decoration:none; color:#9dc1d6}
#data .tipic{width:13px;height:13px; background:url(${path}/images/icon-tishi2.png) no-repeat; padding-right:5px; display:inline-block;position:relative}
#data .tipic a{widht:13px;height:13px; display:block; }
#data .preview{position:absolute;z-index:1000;min-width:180px;max-width:250px;width:atuo; padding:10px;line-height:30px;text-align:left;font-size:14px;color:rgb(255, 255, 255);background-color:rgb(120, 195, 0);border-radius:0px;opacity:1;left:-65px;top:25px;}
#data .previewin{display:block;border-width:10px;position:absolute;font-size:0;line-height:0;left:60px;top:-20px;border-style:dashed dashed solid;border-color:rgba(0, 0, 0, 0) rgba(0, 0, 0, 0) rgb(120, 195, 0);}
</style>
</head>
<body>
	<!--头部开始-->
	<%@ include file="/WEB-INF/page/common/header.jsp"%>
	<!--头部结束-->
	<div class="clear"></div>
	
	<!--数据展示-->   
	 <div id="Bmain" style="padding-top: 0px;">  
	  <!--内容部分开始--> 
			<div class="grid-1100" >
		        <div class="tz-detail" style="margin-top: 20px;">
		            <img src="${basePath}/images/v5/detalibanner.png?version=<%=version%>"/>
		            <ul><li class="center f20">顶玺金融实时财务数据</li></ul>
		        </div>
			</div>
           	 <div class="caiwubg">
		   		<!-- 金额人数数据 -->
		   		<span id="invest_cent"></span>   
		     </div>  
            <!-- 折线图 -->
            <div class="caiwublock" id="moneyPersonChart" style="width: 1100px;"> </div> 
            
            <!-- 成交分布图与待收分布图 -->
           <div id="caiwu2bg" style="width: 1100px;">
                    <div class="bingtu margin10" id="successBorrowChart"></div>
                    <div class="bingtu" id="waitReceiveChart"></div>
           </div>
          <!--数据展示结束-->  
          <!--数据表格展示--> 
  		  <div class="clear"></div>  
  

            
             <div class="caiwubg-title" style="width: 1100px;">
                  <ul>
                       <li id="week_info_li" class="men_li"><a href="javascript:void(0);" onclick="toggleChart(this,'week_info');">借款列表</a></li>
                       <li id="overdue_info_li"><a href="javascript:void(0);" onclick="toggleChart(this,'overdue_info');">逾期</a></li>
                       <li id="finish_info_li"><a href="javascript:void(0);" onclick="toggleChart(this,'finish_info');">结清</a></li> 
                  </ul>
             </div>
              <div class="clear"></div>
             <!--抵押标借款数据统计信息-->
			 <div id="borrow_main_tab"></div>
      </div>
    <!--数据表格展示结束--> 
	
 	<!--内容结束-->
	<div>
		<%@ include file="/WEB-INF/page/common/footer.jsp"%>
	</div>
	
</body>
<script type="text/javascript">
$(function(){
	//金融人数数据
	moneyPersonData();
});
/*
* 成交分布图
*/
function showSuccessBorrowChart(){
	$.get("${basePath}/chart/finance/successBorrowChart.html",
		 function(data) {
			myChart("successBorrowChart",data,function(event){
			});
			setChartData(data);
		 }
	);
}
/**
* 待收分布图
*/
function showWaitReceiveChart(){
	$.get("${basePath}/chart/finance/waitReceiveChart.html",
		 function(data) {
			myChart("waitReceiveChart",data,function(event){
			});
			setChartData(data);
		 }
	);
}

/**
 * 实时累计金额人数折线图
 */
 function showMoneyPersonChart(){
	$.get("${basePath}/chart/finance/moneyPersonChart.html",
		function(data) {
			var moneyPersonChart = new Highcharts.Chart({
		        chart: {
		            renderTo : "moneyPersonChart"
		        },
		        title: {
		            text: '待收金额、总成交金额和投资者人数变化曲线图'
		        },
		        subtitle: {
		            //text: 'Source: WorldClimate.com'
		        },
		        xAxis: [{
		            categories: data.chartDate,
		    		type:'datetime',
		    		labels:{
		    		 	format:'{value:%Y-%m-%d}',
		    			align:'left',
						rotation:45
		         	}
		        }],
		        yAxis: [{ // Primary yAxis
		            labels: {
		                format: '{value:,f}万元',
		            },
		            title: {
		                text: '金额（万元）',
		            },
		            min:0,
		            tickInterval:30000
		        }, { // Secondary yAxis
		            title: {
		                text: '投资者人数',
		                style: {
		                    color: '#058DC7'
		                }
		            },
		            labels: {
		                format: '{value} 位',
		                style: {
		                    color: '#058DC7'
		                }
		            },
		            min:0,
		            opposite: true
		        }],
		        tooltip: {
		        	xDateFormat: '%Y-%m-%d',
		        },
		        series: [{
		            name: '投资者人数',
		            color: '#50B432',
		            type: 'spline',
		            yAxis: 1,
		            data: data.investPersons,
		            tooltip: {
		                valueSuffix: ' 位'
		            }
	
		        }, {
		            name: '总成交金额',
		            color: '#058DC7',
		            type: 'spline',
		            data: data.borrowMoney,
		            tooltip: {
		                valueSuffix: '万元'
		            }
		        },{
		            name: '待收金额',
		            color: '#ED561B',
		            type: 'spline',
		            data:data.waitReceiveMoney,
		            tooltip: {
		                valueSuffix: '万元'
		            }
		        }]
		    });
		}
	);
}

 /**
  * 金额人数数据
  */
 function moneyPersonData(){
 	$.post("${basePath}/chart/finance/moneyPersonData.html", {}, 
     	function(data) {
 			$("#invest_cent").html(data); 
 			// 实时累计金额人数折线图
 		    showMoneyPersonChart();
 			//成交分布图
 			showSuccessBorrowChart();
 		   	//待收分布图
 		    showWaitReceiveChart();
 			weekInfo(); 
 	});
 	
 }
 
 /**
  * 是否显示数据名称说明
  */
  function showDataNameDesc(){
 	 var dataNameDesc =$("#dataNameDesc");
 	 if(dataNameDesc[0].style.display=="none"){
 		 dataNameDesc.show();
 	 }else{
 		 dataNameDesc.hide();
 	 }
 }


function toggleChart(obj,type){
	 setlicss();
	 $("#"+type+"_li").addClass("men_li");
	 if(type=='week_info'){
		 weekInfo();
	 }else if(type=='overdue_info'){
		 overdueInfo();
	 }else if(type=='finish_info'){
		 finishInfo();
	 }
}

/**
 * 本周内详情
 */
function weekInfo(){
	$("#week_info_li").addClass("men_li");
	$.ajax({
		url : '${basePath}/chart/finance/weekInfo/10.html',
		data :{
		} ,
		type : 'post',
		dataType : 'text',
		success : function(data){
			$("#borrow_main_tab").html(data); 
			$("#pageNum_search").val("");
		},
		error : function(data) {
			layer.msg("网络连接异常，请刷新页面或稍后重试！", 2, 5);
	    }
	});
}

//按搜索条件搜索
function search(){
	var search_type = $("#search_type").val();
	$.ajax({
		url : '${basePath}/chart/finance/weekInfo/10.html',
		data :{search_type:search_type
		} ,
		type : 'post',
		dataType : 'text',
		success : function(data){
			$("#borrow_main_tab").html(data);
		},
		error : function(data) {
			layer.msg("网络连接异常，请刷新页面或稍后重试！", 2, 5);
	    }
	});
}

/**
 * ajax 翻页功能
 */
function findPage(pageNo){
	turnPage(pageNo);
}

function turnPage(pageNum){
	var totalPage = $("#totalPage").val();
	if(pageNum<=0){
		pageNum = 1;
	}
	if(parseInt(pageNum)>parseInt(totalPage)){
		pageNum = totalPage;
	}
	var type=$("#type").val();
	var search_type = $("#search_type").val();
	$.ajax({
		url : "${basePath}/chart/finance/"+type+"/10.html",
		data :{pageNum:pageNum,search_type:search_type
		} ,
		type : 'post',
		dataType : 'text',
		success : function(data){
			$("#borrow_main_tab").html(data); 
			$("#pageNum_search").val("");
		},
		error : function(data) {
			layer.msg("网络连接异常，请刷新页面或稍后重试！", 2, 5);
	    }
	});
}
function turnPageByPageNo(){
	var pageNum = $("#pageNo_search").val();
	var totalPage = $("#totalPage").val();
	if(pageNum<=0){
		pageNum = 1;
	}
	if(parseInt(pageNum) > parseInt(totalPage)){
		pageNum = totalPage;
	}
	$.ajax({
		url : "${basePath}/chart/finance/"+type+"/10.html",
		data :{pageNum:pageNum,search_type:search_type
		} ,
		type : 'post',
		dataType : 'text',
		success : function(data){
			$("#borrow_main_tab").html(data); 
		},
		error : function(data) {
			layer.msg("网络连接异常，请刷新页面或稍后重试！", 2, 5);
	    }
	});
}

/**
 * 逾期详情
 */
function overdueInfo(){
	$.ajax({
		url : "${basePath}/chart/finance/overdueInfo/10.html",
		data :{
		} ,
		type : 'post',
		dataType : 'text',
		success : function(data){
			$("#borrow_main_tab").html(data); 
			$("#pageNum_search").val("");
		},
		error : function(data) {
			layer.msg("网络连接异常，请刷新页面或稍后重试！", 2, 5);
	    }
	});
}
/**
 * 结清详情
 */
function finishInfo(){
	$.ajax({
		url : "${basePath}/chart/finance/finishInfo/10.html",
		data :{
		} ,
		type : 'post',
		dataType : 'text',
		success : function(data){
			$("#borrow_main_tab").html(data); 
			$("#pageNum_search").val("");
		},
		error : function(data) {
			layer.msg("网络连接异常，请刷新页面或稍后重试！", 2, 5);
	    }
	});
}
//将li中的current样式去掉
function setlicss(){
	$("#week_info_li").removeClass("men_li");
	$("#overdue_info_li").removeClass("men_li");
	$("#finish_info_li").removeClass("men_li");
}
</script>
</html>
