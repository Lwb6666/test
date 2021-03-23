<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/page/common/taglib.jsp"%>
<%-- <%@ include file="/WEB-INF/page/common/public.jsp"%>
<link href="${basePath}/css/help.css?version=<%=version%>" rel="stylesheet" type="text/css" /> --%>

<style type="text/css">
.notice li {width:100%;position:relative;display:block;border-bottom:1px dashed #dbdbdb;}
.notice li a{font-size:15px;color:#999; font-size:16px;  background:url(${basePath}/images/list.gif) left no-repeat; padding-left:20px;line-height:2.5em; }
.notice li a:hover{color:#00a7e5;}
.notice li:hover{background:url(${basePath}/images/list.gif) #fbfbfb left no-repeat; display:inline-block;  }
.notice span{position:absolute;right:0;font-size:12px; padding:4px 4px 0 0;}
</style>
<div id="about">
	<div class="notice">
    <!--list-->
        <ul>
        	<c:forEach items="${page.result}" var="new_notice">
        		<c:if test="${new_notice.type==0}">
        		   <li><a href="${path}/gonggao/${new_notice.id}.html">${fn:substring(new_notice.title,0,30)}<c:if test="${fn:length(new_notice.title)>30}">..</c:if></a><span>${new_notice.addtimeStr}</span></li>
        	    </c:if>
         		<c:if test="${new_notice.type==1}">
        		   <li><a href="${path}/baodao/${new_notice.id}.html">${fn:substring(new_notice.title,0,30)}<c:if test="${fn:length(new_notice.title)>30}">..</c:if></a><span>${new_notice.addtimeStr}</span></li>
        	    </c:if>       	
            <%-- <li><a href="javascript:findDetail('${new_notice.type}','${new_notice.id}');">${new_notice.title}</a><span>${new_notice.addtimeStr}</span></li> --%>
            </c:forEach>
            
            <c:if test="${page.result.size()==0}">
                  <div style="text-align:center;"  ><b>没有搜索到记录</b></div>  
            </c:if>
            
        </ul>
    <!--list end-->
    </div>
    
	<!-- 翻页 -->            
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
 <script>
 $(function(){
	 addCss();
 });
function addCss(){
	removeCss();
	if('${type}'=='0'){
		$("#gonggao").addClass("sec");
	}else{
		$("#baodao").addClass("sec");
	}
}
function removeCss(){
	$("#introduction").removeClass("sec");
	$("#team").removeClass("sec");
	$("#baodao").removeClass("sec");
	$("#gonggao").removeClass("sec");
	$("#contact").removeClass("sec");
}
</script>
 