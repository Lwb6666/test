<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/page/common/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ include file="/WEB-INF/page/common/public2.jsp"%>
<title>${news_notice_detail.title}-${type=='0'?'平台公告':'媒体报道'}</title>
</head>

<body style="background:#f9f9f9;">
<!--头部开始-->
<%@ include file="/WEB-INF/page/common/header.jsp" %>
<!--头部结束-->
<div class="aboutus-bigbox">
	<%@ include file="/WEB-INF/page/index/about-leftmain.jsp"%>
	<div class="newsBox">
    	<div class="newsText">
        	<h1>${news_notice_detail.title}</h1>
            <h2><span>作者：${news_notice_detail.author }</span><span>文章来源：${news_notice_detail.source }</span><span>点击数：${news_notice_detail.hits+1 }</span><span>更新时间：${news_notice_detail.updatetimeStr}</span></h2>
      		<div class="newsMost">
            	${news_notice_detail.context }
        	</div>
        </div>
    </div>
</div>
<div class="clearfix"></div>
<%@ include file="/WEB-INF/page/common/footer.jsp" %>
</body>
<script>
 $(function(){
	 addCss();
 });
function addCss(){
	removeCss();
	if('${type}'=='0'){
		$("#gonggao").addClass("active");
	}else{
		$("#baodao").addClass("active");
	}
}
function removeCss(){
	$("#introduction").removeClass("normal");
	$("#course").removeClass("normal");
	$("#team").removeClass("normal");
	$("#data").removeClass("normal");
	$("#supervise").removeClass("normal");
	$("#baodao").removeClass("normal");
	$("#gonggao").removeClass("normal");
	$("#contact").removeClass("normal");
}
</script>
</html>
