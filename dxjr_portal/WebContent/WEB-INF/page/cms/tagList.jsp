<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ include file="/WEB-INF/page/common/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<title><c:if test="${page.pageNo!=1}">第${page.pageNo}页_</c:if>${cmstag.name}</title>
	<link rel="shortcut icon" href="${basePath}/images/favicon.ico" type="image/x-icon"/>
	<meta name="keywords" content="${cmstag.name}" />
	<meta name="description" content="顶玺金融（dxjr.com）资讯频道为您提供专业的${cmstag.name}相关文章，帮助您
	对${cmstag.name}相关知识进行全面的了解。更多内容，尽在顶玺金融官网！">
	<meta name="generator" content="顶玺金融" />
	<meta name="author" content="顶玺金融" />
	<meta name="copyright" content="2014 上海顶玺金融信息服务有限公司" />
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
</head>
<body>
<!--head -->
<!--头部开始-->
<%@include file="/WEB-INF/page/cms/cmshead.jsp"%>
<!--head off-->
<div class="clear"></div>
<div id="bbs-hub">
<!--left-->
    <div class="leftwidth nomagrin">
      <div class="bg-blue fff">
        当前位置：<a style="color: #fff"  href="${path}">顶玺金融</a> > <a href="${path }/zixun.html" style="color:white;">资讯中心</a> > <a href="${path }/tag.html" style="color:white;">TAG标签</a> > ${cmstag.name}
       </div>
         
       <div class="news1">

           <ul>
				<c:forEach items="${page.result}" var="re" varStatus="idx">
					<li>
					<a href="${path }/zixun/${re.id}.html"  target="_blank" title="${re.title}"><h2 class="gblue">${re.title}</h2></a>
					${re.summary}
					<br />
               		<span class="green">${re.createTimeStr2}   <strong>${re.channelName}</strong>
               		    标签：
               		    <c:if test="${re.tags!=null}">
									<c:set value="${fn:split(re.tags, ' ')}" var="tagidnames" />
									<c:forEach items="${tagidnames }" var="idname">
										<c:set value="${fn:split(idname, '-')}" var="idna" />
										<a href="${path }/tag/${idna[0]}.html"  target="_blank" title="${idna[1]}">${idna[1]}</a>
									</c:forEach>
								</c:if>
               		    </span>
               		</li>
				</c:forEach>
               
           </ul>
           <c:if test="${page.result[0]!=null }">
           <div class="yema">
					<div class="yema_cont">
						<div class="yema rt">
							<jsp:include page="/WEB-INF/page/common/page.jsp">
								
								<jsp:param name="url" value="${path}/tag/${tagId }-#pageNo#.html" />
								
								<jsp:param name="pageNo" value="${page.pageNo}" />
								<jsp:param name="totalPage" value="${page.totalPage}" />
								<jsp:param name="hasPre" value="${page.hasPre}" />
								<jsp:param name="prePage" value="${page.prePage}" />
								<jsp:param name="hasNext" value="${page.hasNext}" />
								<jsp:param name="nextPage" value="${page.nextPage}" />
								<jsp:param name="type" value="1" />							
							</jsp:include>
						</div>
					
					</div>
				</div>
			</c:if>
			
       </div>
    </div>
<!--left end-->
<!--right-->
    <div class="rightwidth">
    <jsp:include page="tabcontent.jsp"></jsp:include>

	<div class="greens mt10">
       <div class="leftbox">
         <h2 class="white">顶玺最新公告</h2>
            <ul>
						<c:forEach items="${newsNoticeVos}" var="newsNoticeVo" varStatus="idxnn">
						<li class="col-1"><a class="white" href="${path }/gonggao/${newsNoticeVo.id}.html" rel="nofollow" title="${newsNoticeVo.title}"  target="_blank">
							<img class="cmsdot" src="${path }/images/cms/point_13.jpg">
							${fn:substring(newsNoticeVo.title, 0, 10)}${fn:length(newsNoticeVo.title) > 10 ? '..' : ''}</a>
						</li>
		              	<li class="col-2 white">${newsNoticeVo.addtimeStr}</li>
						</c:forEach>
					</ul>

        </div>
</div>
    <div class="mt10">
       <div class="leftbox">
        <div class="bg-h">
          <h2>热门文章</h2>
        </div>
        <div class="pl15">
						<a href="${path }/zixun/${hotsCmsArticles1.id}.html"  target="_blank" title="${hotsCmsArticles1.title}"><h2 class="gblue">
						${fn:substring(hotsCmsArticles1.title, 0, 14)}${fn:length(hotsCmsArticles1.title) > 14 ? '..' : ''}
						</h2></a>
						${hotsCmsArticles1.summary}
					</div>
					<div class="point"></div>
					<ul>
						<c:forEach items="${hotsCmsArticles8}" var="ho8" varStatus="idx8">
						<li><img class="cmsdot" src="${path }/images/cms/point_13.png" /><a href="${path }/zixun/${ho8.id}.html"  target="_blank" title="${ho8.title}">
						${fn:substring(ho8.title, 0, 16)}${fn:length(ho8.title) > 16 ? '..' : ''}
						</a></li>
						</c:forEach>
					</ul>
            
        </div>
</div>
    <div class="mt10"><a href="${path}/newPeopleArea/newPeopleAreaBiao.html?source=56" ><img src="${path }/images/cms/imgs_10.jpg"  alt="P2P理财"/></a>
    </div>
    <div class="mt10">
       <div class="leftbox">
        <div class="bg-h">
          <h2>热门标签</h2> 
        </div>
           <c:forEach items="${cmsTagList}" var="re21" varStatus="idx21">
								<span><a href="${path }/tag/${re21.id}.html"  target="_blank" title="${re21.name}">${re21.name}</a></span>
						</c:forEach>

        </div>
</div>
</div>
</div>
<!--right end-->
<div class="clearfix"></div>
<!--foot-->
<%@include file="/WEB-INF/page/common/footer.jsp"%>
<!--foot end-->

</body>

</html>