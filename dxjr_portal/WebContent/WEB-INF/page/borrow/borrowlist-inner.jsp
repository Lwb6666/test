<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/page/common/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<%@ include file="/WEB-INF/page/common/public.jsp"%>
	</head>

<body>
    <c:forEach items="${page.result}" var="borrowVo">
    <div id="borrowListMid">
		<div class="borrowList borrowStyle">
         <div class="borrowPhoto">
         	<a href="#" onclick="goToInvest(${borrowVo.id})">
			<c:if test="${borrowVo.headimg != null }">
			<img src="${borrowVo.headimg }" width="63" height="62" />
			</c:if>
			<c:if test="${borrowVo.headimg == null }">
			<img src="${basePath}/images/default.gif" width="63" height="62" />
			</c:if>
			</a>
         </div>
         <div class="borrowTitle red h30 l30">
         <a href="#" onclick="goToInvest(${borrowVo.id});" title="${borrowVo.name}">${fn:substring(borrowVo.name,0,8)}<c:if test="${fn:length(borrowVo.name)>8}">..</c:if></a>
         <c:if test="${borrowVo.borrowtype==1}">
			<img src="${basePath}/images/tj.jpg" width="18" height="20" />
		 </c:if>
		 <c:if test="${borrowVo.borrowtype==2}">
			<img src="${basePath}/images/y.gif" width="18" height="20" />
		 </c:if>
		 <c:if test="${borrowVo.borrowtype==3}">
			<img src="${basePath}/images/z.gif" width="18" height="20" />
		 </c:if>
		 <c:if test="${borrowVo.borrowtype==4}">
			<img src="${basePath}/images/m.gif" width="18" height="20" />
		 </c:if>
         <div class="borrowLv h30 l35">借款人：<a href="#" title="${borrowVo.userName }">${fn:substring(borrowVo.userName,0,5)}<c:if test="${fn:length(borrowVo.userName)>5}">..</c:if></a></div></div>
         <div class="borrowAmount h35 l30">借款期限：
         <c:if test="${borrowVo.borrowtype==4}">秒还</c:if>
		 <c:if test="${borrowVo.borrowtype!=4 && borrowVo.style !=4 }">${borrowVo.timeLimit }月</c:if>
		 <c:if test="${borrowVo.borrowtype!=4 && borrowVo.style ==4 }">${borrowVo.timeLimit }天</c:if>
         <div class="borrowLv h30 l35">类型：${portal:desc(300, borrowVo.borrowtype)}</div></div>
         <div class="borrowAmount h35 l30">金额：￥
         <fmt:formatNumber value="${borrowVo.account }" pattern="#,###.##"></fmt:formatNumber>
         <div class="borrowLv h30 l35">利率：<span class="org">${borrowVo.apr }%</span></div></div>
         <div class="borrowAmount h35 l30">${borrowVo.styleStr}
         <div class="borrowPro h30 l35">
         <div class="investLoadbg">
         	<div class="investLoadMain" style="width:${borrowVo.scheduleStr}%; max-width:64px; overflow:hidden;"></div>
         </div>
         <div class="loading3">${borrowVo.scheduleStr}%</div>
         </div>
         </div>
         <div class="borrowAmount h35 l30">
         <c:if test="${borrowVo.borrowtype == 2 and borrowVo.status==1 and borrowVo.timingBorrowTime != null and borrowVo.timingBorrowTime != '' }">
         <font color="red">${borrowVo.timingBorrowTimeStr}</font>
         </c:if>
         <c:if test="${borrowVo.status>1}">
         ${borrowVo.publishTimeStr}
         </c:if>
         <div class="borrowLv h30 l35">
         <c:if test="${borrowVo.repaymentTime!=null}">${borrowVo.repaymentTimeStr}</c:if>
         <c:if test="${borrowVo.repaymentTime==null}">无</c:if>
         </div>
         </div>           
		 <div class="borrowAmount h35 l30">${borrowVo.tenderTimes}次
         <div class="borrowLv h30 l35">${borrowVo.contractNo}</div></div>
         <c:if test="${borrowVo.status==2 || borrowVo.status==3 || borrowVo.status==4 || borrowVo.status==42}">  
		 <div class="borrowYes h30 l35">
		 	<c:if test="${borrowVo.status==2}">
				<a href="#" onclick="goToInvest(${borrowVo.id})" class="borrowYes">投标中</a>
			</c:if>
			<c:if test="${borrowVo.status==3}">
				<a href="#" onclick="goToInvest(${borrowVo.id})" class="borrowYes">复审中</a>
			</c:if>
			<c:if test="${borrowVo.status==4}">
				<a href="#" onclick="goToInvest(${borrowVo.id})" class="borrowYes">还款中</a>
			</c:if>
			<c:if test="${borrowVo.status==42}">
				<a href="#" onclick="goToInvest(${borrowVo.id})" class="borrowYes">已垫付</a>
			</c:if>
		 </div>
		 </c:if>
		 <c:if test="${borrowVo.status==5}">
		 <div class="borrowNo h30 l35"><a href="#" onclick="goToInvest(${borrowVo.id})" class="borrowNo">还款结束</a></div>
		 </c:if>
		 <c:if test="${borrowVo.status==1 and borrowVo.timingBorrowTime != null and borrowVo.timingBorrowTime != '' }">
		 <div class="borrowNo h30 l35"><a href="#" class="borrowNo">即将开始</a></div>
		 </c:if>
        </div> 
      </div>
     </c:forEach>
       <!--yema st-->
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
<script>
$(function(){
});

function turnPage(pageNo){
	var totalPage = parseInt('${page.totalPage }');
	if(pageNo<=0){
		pageNo = 1;
	}
	if(pageNo>totalPage){
		pageNo = totalPage;
	}
	window.parent.turnPageParent(pageNo);
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
	window.parent.turnPageParent(pageNo);
}
</script>
</html>
