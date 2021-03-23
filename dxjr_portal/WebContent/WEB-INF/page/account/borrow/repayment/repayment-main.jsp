<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/page/common/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ include file="/WEB-INF/page/common/public.jsp"%>
<title>我的账户-融资管理-待还列表-顶玺金融</title>
</head>

<body style="background:#f9f9f9;">
<!--头部开始-->
<%@ include file="/WEB-INF/page/common/header.jsp" %>
<!--头部结束-->
<!--待还列表开始-->
<div id="Container">
<div id="Bmain">
  <div id="Bmain_titile">
     <div class="title"><span class="home"><a href="${path}/">顶玺金融</a></span>
     <span><a href="${path}/myaccount/toIndex.html">我的账户</a></span>
     <span>融资管理</span>
     <span> <a href="${path }/repayment/enterRepayMent.html">待还列表</a></span>
     </div>
     <div id="menu_centert">     
     <%@ include file="/WEB-INF/page/account/myAccount_leftMenu.jsp"%>
     <div class="lb_waikuang" >
         <div class="men_title col2">
               <ul>
                   <li id="repay_no"><a href="javascript:;" onclick="queryRePaymentDetails(1,0)">待还明细</a> </li>
                   <li id="repay_yes"><a href="javascript:;" onclick="queryRePaymentDetails(1,1)">已还明细 </a></li>
               </ul>
         </div>
         <div id="main_content"></div>
	 </div>
</div>
</div>
</div>
</div>
<div class="clearfix"></div>
<div>
<%@ include file="/WEB-INF/page/common/footer.jsp" %>
</div>
</body>
<script type="text/javascript">
var _load;
$(function(){
	queryRePaymentDetails(1,0);
});



//待还列表
function queryRePaymentDetails(pageNum,status){	
	if(status == 0){
		$("#repay_no").addClass("men_li");
		$("#repay_yes").removeClass("men_li");
	}else{
		$("#repay_yes").addClass("men_li");
		$("#repay_no").removeClass("men_li");
	}
	var keyword = $("#keyword").attr("value");
	var beginTime = $("#beginTime").attr("value");
	var endTime = $("#endTime").attr("value");
	$.ajax({
		url : '${basePath }/repayment/queryRepaymentDetails.html',
		data :{
			pageNum:pageNum,status:status,keyword:keyword,beginTime:beginTime,endTime:endTime
		} ,
		type : 'post',
		dataType : 'text',
		success : function(data){
			$("#main_content").html(data);
		},
		error : function(data) {
			layer.msg('网络连接异常，请刷新页面或稍后重试！', 1, 5);
	    }
	});
}


/**
 * 导出Excel
 */
function exportRepaymentRecord(status){
	 
	if(layer.confirm("你确定要导出吗?",function(){
		var keyword = $("#keyword").attr("value");
		var beginTime = $("#beginTime").attr("value");
		var endTime = $("#endTime").attr("value");
		
		var para = "status=" + status + "&&" + "keyword=" + keyword + "&&" + "beginTime=" + beginTime + "&&" + "endTime=" + endTime;
		
		$("#form1").attr("action","${basePath }/repayment/exportRepaymentRecord.html?" + para);
		$("#form1").submit();
		layer.alert("导出成功！",1);
	}));
	
}

/**
 * 立即还款
 */
function repay_borrow(repaymentid){	
	 var repayBorrow = document.getElementById("repayBorrow");
	if(layer.confirm("确定要还款吗？",function(){
		_load = layer.load('处理中..');
		repayBorrow.href="javascript:void(0)";
		$.ajax({
		    url : '${basePath}/borrow/borrow/repayBorrow.html',
		    data:{repaymentid:repaymentid},
		    type: "POST",
		    success:function(msg){
		    	layer.close(_load);
		    	var message = msg.code=="0"?msg.message:"还款成功";
		    	var iconIndex = msg.code=="0"?5:1;
		    	layer.msg(message, 2, iconIndex,function(){
			    	//重新查询
			    	queryRePaymentDetails(1,0);
		    	});
		    },
			error : function() {
				layer.close(_load);
				repayBorrow.href="javascript:repay_borrow("+repaymentid+")";
				layer.msg("网络连接超时，请您稍后重试", 2, 5);
		    } 
		 });	
	}));
}

	
</script>
</html>