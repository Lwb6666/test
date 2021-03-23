<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/page/common/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<title>顶玺金融</title>
	<link rel="shortcut icon" href="${basePath}/images/favicon.ico" type="image/x-icon"/>
	<meta name="keywords" content="顶玺金融,金融,投资,理财" />
	<meta name="description" content="顶玺金融提供安全、有担保的互联网理财投资服务。风险保证金保障！随时可赎回！上顶玺，好收益！">
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
        当前位置：<a style="color: #fff"  href="${path}">顶玺金融</a> > <a href="${path }/zixun.html" style="color:white;">资讯中心</a> > TAG标签
       </div>
       <div class="news1">
                <div class="pic">
                    <div class="leftbox fl bn">
                            <div class="bg-h">
                              <h2>热门标签</h2>
                            </div>
                     			<c:forEach items="${cmsTagList}" var="re" varStatus="idx">
								<span><h3><a href="${path }/tag/${re.id}.html"  target="_blank" title="${re.name}" >${re.name}</a></h3></span>

								</c:forEach>
               </div>
               <div class="clear"></div>
               <div class="leftbox fl bn">
                            <div class="bg-h">
                              <h2>理财标签</h2> 
                            </div>
                            <c:forEach items="${cmsTagList2}" var="re2" varStatus="idx2">
								<span><h3><a href="${path }/tag/${re2.id}.html"  target="_blank" title="${re2.name}" >${re2.name}</a></h3></span>
							</c:forEach>
                                                   
               </div>
               <div class="clear"></div>
               <div class="leftbox fl bn">
                            <div class="bg-h">
                              <h2>贷款标签</h2> 
                            </div>
                            <c:forEach items="${cmsTagList3}" var="re3" varStatus="idx3">
								<span><h3><a href="${path }/tag/${re3.id}.html"  target="_blank" title="${re3.name}" >${re3.name}</a></h3></span>
							</c:forEach>                       
               </div>

         <div class="clear"></div>

         </div>
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
						<a href="${path }/zixun/${hotsCmsArticles1.id}.html"  target="_blank" title="${hotsCmsArticles1.title}" >
						<h2 class="gblue">
						${fn:substring(hotsCmsArticles1.title, 0, 14)}${fn:length(hotsCmsArticles1.title) > 14 ? '..' : ''}
						</h2></a>
						${hotsCmsArticles1.summary}
					</div>
					<div class="point"></div>
					<ul>
						<c:forEach items="${hotsCmsArticles8}" var="ho8" varStatus="idx8">
						<li><img class="cmsdot" src="${path }/images/cms/point_13.png" />
						<a href="${path }/zixun/${ho8.id}.html"  target="_blank" title="${ho8.title}" >
						${fn:substring(ho8.title, 0, 16)}${fn:length(ho8.title) > 16 ? '..' : ''}
						</a></li>
						</c:forEach>
					</ul>
      </div>
</div>
<div class="mt10"><a href="${path}/newPeopleArea/newPeopleAreaBiao.html?source=56" ><img src="${path }/images/cms/imgs_10.jpg"  alt="P2P理财"></a>
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