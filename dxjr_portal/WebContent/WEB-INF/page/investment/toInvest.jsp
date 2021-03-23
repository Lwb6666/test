<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/page/common/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
  <title>${borrow.name}<c:if test="${borrow.pledgeType == 1}">(新增)</c:if><c:if test="${borrow.pledgeType == 2}">(续贷)</c:if><c:if test="${borrow.pledgeType == 3}">(资产处理)</c:if>_投标专区-顶玺金融官网</title>
	<meta name="keywords" content="${borrow.name}" />
	<meta name="description" content="">

	<c:choose>
	 	<c:when test="${borrowIsCustody==1}">
	 	<%@ include file="/WEB-INF/page/common/public4.jsp"%>
	 	</c:when>
	 	<c:otherwise>
	 	<%@ include file="/WEB-INF/page/common/public2.jsp"%>
	 	</c:otherwise>
	 </c:choose>

</head>

<body>
	<!--头部开始-->
	<%@ include file="/WEB-INF/page/common/header.jsp"%>
	<!--头部结束-->
	<div class="clear"></div>
	<!--内容开始-->
	<div class="product-wrap"><!--信息star-->
	 <c:choose>
	 	<c:when test="${borrowIsCustody==1}">
	 	<%@ include file="/WEB-INF/page/investment/toCGInvest_borrowDetail.jsp"%>
	 	</c:when>
	 	<c:otherwise>
	 	<%@ include file="/WEB-INF/page/investment/toInvest_borrowDetail.jsp"%>
	 	</c:otherwise>
	 </c:choose>
	  
    </div>
<div class="product-wrap"><!--table-->
	<div class="grid-1100 background">
    	<div class="prduct-menu">
        	<div class="menu-tbl">
            	<ul class="col5">
            	<li class="active">借款详情</li>
            	<li onclick="searchTenderRecordList(1);">投标记录</li>
            	<li onclick="searchRepaymentList(1);">还款计划</li>
            	<li onclick="searchtransferinfo(1);">债权信息</li>
            	<li onclick="searchBorrowSubscribes(1);">转让记录</li></ul>
            </div>
            <div class="menucont" style="clear:both">
                <!-- 借款详情start -->
            	<div  id="toInvest_borrowInfo" class="tbl-cont">
                	<%@ include file="/WEB-INF/page/investment/toInvest_borrowInfo.jsp"%>
                </div>
                <!-- 借款详情end -->
                <!-- 投标记录详情start -->
                <div id="toInvest_tenderRecord"  class="tbl-cont" style="display:none">
                	 
                </div>
                <!-- 投标记录详情end -->
                <div id="toInvest_repaymentPlan"   class="tbl-cont" style="display:none">
                    
                </div>
                <div id="toInvest_transferInfo"  class="tbl-cont" style="display:none">
                    
                </div>
                <div id="toInvest_transferRecord"  class="tbl-cont" style="display:none">
                    
                </div>
            </div>
        </div>
    </div>
</div>	 
<div class="clearfix bompd60"></div>
<!--内容结束-->
<%@ include file="/WEB-INF/page/common/footer.jsp"%>
</body>
<script type="text/javascript">


$(function (){
	   var  content= $.trim($("#tbinfo-borrowContentid").text())+"详情请登陆www.dxjr.com";
	  $("meta[name='description']").attr("content",content);
});

/**
 * 显示投标记录
 */
function searchTenderRecordList(pageNum){
	 
	$.ajax({
		url : '${basePath}/tender/searchTenderRecordList/${borrow.id}/'+pageNum+'.html',
		type : 'post',
		dataType : 'text',
		success : function(data){
			$("#toInvest_tenderRecord").html(data);
		},
		error : function(data) {
			layer.msg('网络连接异常，请刷新页面或稍后重试！', 1, 5);
	    }
	});
}
/**
 * 显示还款计划
 */
function searchRepaymentList(pageNum){
	$.ajax({
		url : '${basePath}/repayment/searchRepaymentList/${borrow.id}/'+pageNum+'.html',
		type : 'post',
		dataType : 'text',
		success : function(data){
			$("#toInvest_repaymentPlan").html(data);
			
		},
		error : function(data) {
			layer.msg('网络连接异常，请刷新页面或稍后重试！', 1, 5);
	    }
	});
}

<%-- 暂时注释掉--%>
/**
 * 债权信息
 */
function searchtransferinfo(pageNum){
	$.ajax({
		url : '${basePath}/zhaiquan/transferinfo/${borrow.id}/'+pageNum+'.html',
		type : 'post',
		data:{status:'${borrow.status}'},
		dataType : 'text',
		success : function(data){
			$("#toInvest_transferInfo").html(data);
			
		},
		error : function(data) {
			layer.msg('网络连接异常，请刷新页面或稍后重试！', 1, 5);
	    }
	});
}



/**
 * 转让记录
 */
function searchBorrowSubscribes(pageNum){
	$.ajax({
		url : '${basePath}/zhaiquan/searchBorrowSubscribes/${borrow.id}/'+pageNum+'.html',
		type : 'post',
		dataType : 'text',
		success : function(data){
			$("#toInvest_transferRecord").html(data);
			
		},
		error : function(data) {
			layer.msg('网络连接异常，请刷新页面或稍后重试！', 1, 5);
	    }
	});
}



</script>
</html>
