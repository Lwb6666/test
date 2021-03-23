<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/page/common/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>[顶玺金融理财产品]个人投资理财平台_互联网金融理财产品-顶玺金融</title>
<%@ include file="/WEB-INF/page/common/public3.jsp"%>
<meta name="keywords" content="网络投资理财,互联网金融理财,金融理财,网上投资理财,金融理财产品,网络理财产品" />
<meta name="description" content="顶玺金融（www.dxjr.com），一个专注于消费金融与房产抵押贷款的中国社科院网贷评级A级的理财平台。致力于为投资理财用户和贷款用户两端搭建安全、规范、透明的互联网金融平台。顶玺为您的资金保驾护航！ ">
<link href="${basePath}/css/circle.css" rel="stylesheet" type="text/css" />
</head>

<body>
<!--头部开始-->
<%@ include file="/WEB-INF/page/common/header.jsp" %>
<!--头部结束-->
<div class="clear"></div>
<!--内容开始-->
<div class="product-wrap wrapperbanner"><!--banner star-->
	<div class="grid-1100">
        <div class="tz-detail">
            <img src="${basePath}/images/v5/detalibanner.png" alt="个人理财产品"/>
            <ul class="col4">
            <ul class="col4"><li><a href="${basePath}/dingqibao.html" >定期宝</a></li>
            <li><a href="${basePath}/curInController.html">活期宝</a></li>
            <li><a href="${basePath}/toubiao.html"  >散标投资</a></li>
            <li><a href="${basePath}/zhaiquan.html"  class="active">债权转让</a></li></ul>
            </ul>
        </div>
	</div>
</div>
<div class="product-wrap"><!--定期宝star-->
	<div class="grid-1100">
        <div class="product-deatil clearfix ">
            <h1 class="f16 bule">
            <a href="javascript:void(0);" id="borrow" class="deatil" value="1">普通标转让</a>
            <a href="javascript:void(0);" id="zhitongche" class="deatil" value="2" >直通车转让</a></h1>
            <div class="tz-dqb3 clearfix">
                <div class="col clearfix">
                        <span class="fl gary2">标的状态：</span>
                        <div class="btn-box-bg">
                            <div class="btn-box4 repayType"><a href="javascript:void(0);" id="noLimit" value="" >不限</a></div>
                            <div class="btn-box4 repayType"><a href="javascript:void(0);" id="transfering"  class="active" value="2">转让中</a></div>
                            <div class="btn-box4 repayType"><a href="javascript:void(0);" id="transfered" value="4">已转让</a></div>
                        </div>
                 </div>
                
             </div>
             <div id="dataList">
              
             </div>
             
        </div>
         
    </div>
</div>	 
 <div class="clearfix bompd20" style="height:100px;"></div>
<%@ include file="/WEB-INF/page/common/footer.jsp" %>
</body>
<script type="text/javascript">
	var BorrowType;
	var RepayType;
	var TransferStatus=2;
	var RemainingTerm;
	var OrderBy;
	var orderType = '';
	var OrderName;

	$(document).ready(function() {
	 
		if('${type}'=='first'){
			$('#zhitongche').addClass("active");
			searchTransferFirstList(1);
		}else{
			$('#borrow').addClass("active");
		    loadTransferList(1);
		}
		//获取标的状态
		$(".repayType").click(function(){ //注册click事件
			 
			$(".repayType").children('a').removeClass("active");
			$(this).siblings().removeClass("active"); //移除同辈节点的class
			$(this).children('a').addClass("active"); //给当前节点添加class
		 	TransferStatus = $(this).children("a").attr("value"); //赋 
			if($('.product-deatil .active').attr("value")=='1'){
				searchTransferList(1);
			}else{
				searchTransferFirstList(1);
			}
		});
		$('.product-deatil .deatil').click(function(){
		
			$(this).siblings().removeClass("active"); 
			$(this).addClass("active"); //给当前节点添加class
			if($(this).attr("value")=='1'){
				$("#noLimit").removeClass("active");
				$("#transfered").removeClass("active");
				$("#transfering").addClass("active"); 
				TransferStatus=2;
				searchTransferList(1);
			}else{ 
				$("#transfering").removeClass("active");
				$("#transfered").removeClass("active");
				$("#noLimit").addClass("active"); 
				TransferStatus=null;
				searchTransferFirstList(1);
			}
		});
		
	 
	});
    
	function searchTransferList(pageNum) {
	
		$.ajax({
			url : '${basePath}/queryTransferList1.html',
			data : {
				pageNum : pageNum,
				pageSize : 10,
				'borrowType' : BorrowType,
				'transferStatus' : TransferStatus,
				'remainingTerm' : RemainingTerm,
				'repayType' : RepayType,
				'orderBy' : OrderBy,
				'orderType' : orderType
			},
			type : 'post',
			dataType : 'text',
			success : function(data) {
				$('#dataList').empty();
				$("#dataList").html(data);
			},
			error : function(data) {
				alert("网络连接异常，请刷新页面或稍后重试！");
			}
		});
	}

	function loadTransferList(pageNum) {
		$.ajax({
			url : '${basePath}/queryTransferList1.html',
			data : {
				pageNum : pageNum,
				pageSize : 10,
				'borrowType' : BorrowType,
				'transferStatus' : TransferStatus,
				'remainingTerm' : RemainingTerm,
				'repayType' : RepayType,
				'orderBy' : OrderBy,
				'orderType' : orderType
			},
			type : 'post',
			dataType : 'text',
			success : function(data) {
				$('#dataList').empty();
				$("#dataList").html(data);
			},
			error : function(data) {
				alert("网络连接异常，请刷新页面或稍后重试！");
			}
		});
	}
	/**
	 * 查询直通车转让信息
	 */
	function searchTransferFirstList(pageNum) {
        
		$.ajax({
			url : '${basePath}/zhitongche/zhuanrang/queryTransferList1/'
					+ pageNum + '.html',
			data : {'transferStatus' : TransferStatus},
			type : 'post',
			dataType : 'text',
			success : function(data) {
				$('#dataList').empty();
				$("#dataList").html(data);
			},
			error : function(data) {
				alert("网络连接异常，请刷新页面或稍后重试！");
			}
		});
	}
</script>
</html>
