<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/page/common/taglib.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<%@ include file="/WEB-INF/page/common/public.jsp"%>
		<link rel="stylesheet" href="${basePath}/js/thickbox/thickbox.css" type="text/css"/>
		<script type="text/javascript" src="${basePath}/js/thickbox/thickbox.js"></script>
	</head>
	<body>
		<form method="post" name="form1">
			<table border="1">
		        <c:forEach items="${page.result}" var="borrow" varStatus="sta">
		        <tr>
		          <td>${sta.index+1}</td>
		          <td><a href="" target="_blank"><span title="${borrow.name }">${fn:substring(borrow.name,0,10)}</span></a></td>
		          <td>

		          <c:if test="${borrow.repamentStatus==0}">
		          	<c:if test="${borrow.repamentWebStatus==1}">
		          		 <a href="javascript:after_repay_borrow(${borrow.id },${borrow.repamentId });" id="repayBorrow">垫付后还款</a>
		          	</c:if>
		          	<c:if test="${borrow.repamentWebStatus==0}">
		          		 <a href="javascript:repay_borrow(${borrow.id },${borrow.repamentId });" id="repayBorrow">立即还款</a>
		          	</c:if>
		          </c:if>
		          <c:if test="${borrow.repamentStatus==1}">
		          	已还款
		          </c:if>
		        </tr>
		        </c:forEach>
		     </table>
		</form>
		
		<div class="yema">
	     <div class="yema_cont">
	        <div class="yema rt">共${page.totalCount}条&nbsp;&nbsp;第${page.pageNo }页/共${page.totalPage}页
	                    <span class="z_page" style="display:none;">&nbsp;</span>
	                    <a href="javascript:turnPage(0);">首页</a>
	                    <a href="javascript:turnPage(${page.pageNo }-1);">上一页</a>
	                    <a href="javascript:turnPage(${page.pageNo }+1);">下一页</a>
	                    <a href="javascript:turnPage(${page.totalPage});">末页</a>
	                   	 第&nbsp;<input type="text" id="pageNo_search" size="10" class="dlinput1" value="${page.pageNo }"/>&nbsp;页
	                    <a href="javascript:turnPageByPageNo();">跳转</a>
	            </div>
	    </div>   
		</div>
	</body>
</html>

<script type="text/javascript">
function turnPage(pageNo){
	var totalPage = parseInt('${page.totalPage }');
	if(pageNo<=0){
		pageNo = 1;
	}
	if(pageNo>totalPage){
		pageNo = totalPage;
	}
	window.open("${path}/repayment/forrepaymenetlist/"+pageNo+".html","_self");
}

function turnPageByPageNo(){
	var pageNo = $("#pageNo_search").val();
	var totalPage = parseInt('${page.totalPage }');
	if(pageNo<=0){
		pageNo = 1;
	}
	if(pageNo>totalPage){
		pageNo = totalPage;
	}
	window.open("${path}/repayment/forrepaymenetlist/"+pageNo+".html","_self");
}

//还款
function repay_borrow(id,repaymentid){
	 var repayBorrow = document.getElementById("repayBorrow");
	
	if(layer.confirm("确定要还款吗？",function(){
		repayBorrow.href="javascript:void(0)";
		$.post("${basePath}/borrow/borrow/repayBorrow.html",{
			borrowid:id,repaymentid:repaymentid,
		},function(data){
			repayBorrow.href="javascript:repay_borrow()";
			if(data=="OK"){
				layer.alert("还款成功！" , 1, "温馨提示"); 
				window.open("${path}/repayment/forrepaymenetlist/"+pageNo+".html","_parent");
			}else{
				layer.alert(data);
			}
		});
	}));
	
	
	
}

//垫付后还款
function after_repay_borrow(id,repaymentid){
	 var repayBorrow = document.getElementById("repayBorrow");
	
	if(layer.confirm("确定要还款吗？",function(){
		repayBorrow.href="javascript:void(0)";
		$.post("${basePath}/borrow/borrow/afterrepayBorrow.html",{
			borrowid:id,repaymentid:repaymentid,
		},function(data){
			repayBorrow.href="javascript:repay_borrow()";
			if(data=="OK"){
				layer.alert("还款成功！" , 1, "温馨提示");
				window.open("${path}/repayment/forrepaymenetlist/"+pageNo+".html","_parent");
			}else{
				layer.alert(data);
			}
		});
	}));
	
	
	
}
</script>
