<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
 <%@ include file="/WEB-INF/page/common/taglib.jsp"%>    
 <!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<title>${cmsPediaEntry.seoTitle}</title>
	<link rel="shortcut icon" href="${path }/images/favicon.ico" type="image/x-icon">
	<meta name="keywords" content="${cmsPediaEntry.seoKeywords}">
	<meta name="description" content="${cmsPediaEntry.seoDescription}">
	<meta name="generator" content="顶玺金融">
	<meta http-equiv="X-UA-Compatible" content="IE=8">
	<meta name="author" content="顶玺金融">
	<meta name="copyright" content="2014 上海顶玺金融信息服务有限公司">
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
</head>
<body>
<%@include file="cmshead.jsp"%>
<div class="clear"></div>

 <div id="bbs-hub">
<!--left-->
    <div class="leftwidth nomagrin">
      <div class="bg-blue fff">
        当前位置：<a style="color: #fff" href="${path}">顶玺金融</a> &gt;<a style="color: #fff"  href="${path}/zixun.html">资讯中心</a> &gt; <a style="color: #fff" href="${path}/baike.html" >知识百科</a> &gt; ${cmsPediaEntry.name}
       </div>
       <div class="news1">
                <div class="pic">
                  <div class="mt10 pd4">
                <h1 class="gblue mt10">${cmsPediaEntry.name}</h1></div>
                ${cmsPediaEntry.desc}
        <div class="clear"></div>
             <div class="rinfo color-bg mt10">
                    <div class="leftbox fl bn pbox1-w650">
                            <div class="bg-h">
                              <h2>${cmsPediaEntry.name}相关标签</h2> 
                            </div>
	                             <c:if test="${fn:length(cmsTags)==0}">
							  		  没有查询到相关标签
							  	 </c:if>
							  	 <c:if test="${fn:length(cmsTags)!=0}">
		                            <c:forEach items="${cmsTags}"  var="cmstag" >
		                               <span><h3><a href="${path}/tag/${cmstag.id}.html">${cmstag.name}</a></h3></span>
		                           </c:forEach>
	                            </c:if>
               </div>
             </div>
         <div class="clear"></div>
             <div class="rinfo color-bg mt10">
             <div class="pbox1 pbox1-w320 mr10 pdl10">
                <div class="bg-h">
                   <h2>${cmsPediaEntry.name}相关文章</h2>
                   </div>
                 <ul class="bn">
	                 <c:if test="${fn:length(cmsArticles)==0}">
				  		  没有查询到相关文章
				  	 </c:if>
				  	 <c:if test="${fn:length(cmsArticles)!=0}">
	                 
			               <c:forEach  items="${cmsArticles}"  var="articles"  varStatus="index" >
					          <c:if test="${(index.index+1)%2==1 }">
						            <li class="bn"><a href="${path}/zixun/${articles.id}.html"><img class="cmsdot" src="${basePath}/images/cms/point_13.png">${fn:substring(articles.title, 0, 15)}${fn:length(articles.title) > 15 ? '..' : ''}</a></li>  
							  </c:if>
							 </c:forEach>
	              </ul>
	                 </c:if>   
             </div>
             <div class="pbox1 pbox1-w320 mr10">
                <h2>&nbsp;</h2>
                    <ul class="bn">
	                     <c:forEach  items="${cmsArticles}"  var="articles"  varStatus="index" >
					          <c:if test="${(index.index+1)%2==0 }">
						            <li class="bn"><a href="${path}/zixun/${articles.id}.html"><img class="cmsdot" src="${basePath}/images/cms/point_13.png">${fn:substring(articles.title, 0, 15)}${fn:length(articles.title) > 15 ? '..' : ''}</a></li>  
							  </c:if>
							 </c:forEach>
                    </ul>
             </div>
           </div>
           <div class="clear"></div>
           
  
         </div>
       </div>
    </div>
<!--left end-->
<!--right-->
    <div class="rightwidth">
       <jsp:include page="tabcontent.jsp"></jsp:include>
       
  <div class="mt10">
       <div class="leftbox">
        <div class="bg-h">
          <h2>知识百科</h2> 
        </div>
           <c:forEach  items="${initials}"  var="initial">
                <span><a href="${path }/baike-${initial}.html">${initial}</a></span>
           </c:forEach>   
        </div>
</div>

<div class="greens mt10">
       <div class="leftbox">
         <h2 class="white">顶玺最新公告</h2>
            <c:forEach  items="${newsNoticeVos}"  var="newsNoticeVo">
		            <ul>
		              <li class="col-1"><a class="white"   rel="nofollow" href="${path }/gonggao/${newsNoticeVo.id}.html"  title="${newsNoticeVo.title}"><img class="cmsdot" src="${basePath}/images/cms/point_13.jpg">${fn:substring(newsNoticeVo.title, 0, 10)}${fn:length(newsNoticeVo.title) > 10 ? '..' : ''}</a></li>
		              <li class="col-2 white">${newsNoticeVo.addtimeStr}</li>
		            </ul>
	         </c:forEach> 
        </div>
</div>
     
    <div class="mt10"><a href="${path}/newPeopleArea/newPeopleAreaBiao.html?source=56" ><img alt="P2P理财" src="${basePath}/images/cms/imgs_10.jpg"></a>
    </div>
    
</div>
</div>

<div class="clearfix"></div>
<%@ include file="/WEB-INF/page/common/footer.jsp"%>
</body>

<script type="text/javascript">
function goToInvest(borrowId){
	window.open("${path}/toubiao/"+borrowId+".html"); 
};

//去开通直通车
function toInvestFirst(id){
	window.open("${path}/zhitongche/"+id+".html","_blank");
}
  

</script>
</html>