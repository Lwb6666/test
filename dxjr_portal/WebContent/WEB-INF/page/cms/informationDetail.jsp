<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/page/common/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<title>${cmsArticle.seoTitle}</title>
	<link rel="shortcut icon" href="${basePath}/images/favicon.ico" type="image/x-icon"/>
	<meta name="keywords" content="${cmsArticle.seoKeywords}" />
	<meta name="description" content="${cmsArticle.seoDescription}">
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
        当前位置：<a style="color: #fff"  href="${path}">顶玺金融</a> > <a href="${path }/zixun.html" style="color:white;">资讯中心</a> > <a style="color: #fff"  href="${path }/${cmsArticle.urlCode }.html">${cmsArticle.channelName }</a> > ${cmsArticle.title }
       </div>
       <div class="news1">
                <div class="pic"><a href="${path}/newPeopleArea/newPeopleAreaBiao.html?source=57" ><img src="${path }/images/cms/images_03.jpg" alt="理财产品"/></a>
                <div class="mt10 pd4">
                <span class="gblue mt10 fontsize20" ><h1>${cmsArticle.title}</h1></span></div>
                <span class="green">${cmsArticle.createTimeStr2 }   分类：${cmsArticle.channelName }     来源：${cmsArticle.author }  </span>
                
           <div class="topinfo">
             <div class="rinfo color-bg">${cmsArticle.summary}</div>
           </div>
        
       	 	${cmsArticle.content}
        
         <div class="clear"></div>
         <div class="navtag"> 
              <span class="green">标签：</span>
              <span>
              <h3>
  				<c:if test="${cmsArticle.tags!=null}">
									<c:set value="${fn:split(cmsArticle.tags, ' ')}" var="tagidnames" />
									<c:forEach items="${tagidnames }" var="idname">
										<c:set value="${fn:split(idname, '-')}" var="idna" />
										<a href="${path }/tag/${idna[0]}.html"  target="_blank" title="${idna[1]}">${idna[1]}</a>
									</c:forEach>
								</c:if>            
              </h3>
              </span>
              
              <div class="fr">
                  <span class="tagpic"></span>
                  <span>分享到：</span>
                  <span>
                  <div class="bdsharebuttonbox">
                  <a href="#" class="bds_tsina" data-cmd="tsina" title="分享到新浪微博">
                  </a><a href="#" class="bds_tqq" data-cmd="tqq" title="分享到腾讯微博"></a>
                  <a href="#" class="bds_renren" data-cmd="renren" title="分享到人人网"></a>
                  <a href="#" class="bds_qzone" data-cmd="qzone" title="分享到QQ空间"></a>
                  <a href="#" class="bds_douban" data-cmd="douban" title="分享到豆瓣网"></a>
                  <a href="#" class="bds_weixin" data-cmd="weixin" title="分享到微信"></a>
                  <a href="#" class="bds_more" data-cmd="more"></a>
                  </div>
                  </span>
              </div>
           </div>
           <div class="clear"></div>
           <c:if test="${cmsArticleLast!=null or cmsArticleNext!=null}">
           <div class="green-d fl mt10 p10 w670">
             <span class="fl">
           		<c:if test="${cmsArticleLast!=null}">
          	 		上一篇：<a href="${path }/zixun/${cmsArticleLast.id}.html" title="${cmsArticleLast.title}">${fn:substring(cmsArticleLast.title, 0, 15)}${fn:length(cmsArticleLast.title) > 15 ? '..' : ''}</a>
           		</c:if>
             </span>
           	 <span class="fr">
           		<c:if test="${cmsArticleNext!=null}">
					下一篇：<a href="${path }/zixun/${cmsArticleNext.id}.html" title="${cmsArticleLast.title}">${fn:substring(cmsArticleNext.title, 0, 15)}${fn:length(cmsArticleNext.title) > 15 ? '..' : ''}</a>
				</c:if>
              </span>	
           </div>
           </c:if> 
            <div class="clear"></div>
           <div class="green-d fl mt10 p10 w670">
              免责声明：本站资讯栏目上所有原创及转载内容的版权未作特别说明的归原作者及原网站所有，文章仅代表作者本人的观点，与本网站立场无关。未经允许不得已任何形式转载及传播，如果对资讯内容的版权存在异议，请您及时联系我们。
           </div>
           <div class="clear"></div>
	       <div class="rinfo color-bg mt10">
		             <div class="pbox1 pbox1-w320 mr10">
		                <h2 class="green">相关文章</h2>
		                    <ul class="bn">
		                    <c:forEach items="${aboutArticles}" var="aboutArticle" varStatus="idxaa">
				              	<li class="bn"><a href="${path }/zixun/${aboutArticle.id}.html"  target="_blank"  title="${aboutArticle.title}"><img class="cmsdot" src="${path }/images/cms/point_13.png"/>
				              		${fn:substring(aboutArticle.title, 0, 15)}${fn:length(aboutArticle.title) > 15 ? '..' : ''}</a></li>
							</c:forEach>
		                    </ul>
		             </div>
		             <div class="pbox1 pbox1-w320 mr10">
		                <h2 class="green">常见问题</h2>
		                    <ul class="bn">
		                    <c:forEach items="${questionArticles}" var="questionArticle" varStatus="idxqa">
				              	<li class="bn"><a href="${path }/zixun/${questionArticle.id}.html"  target="_blank"  title="${questionArticle.title}">
				              		<img class="cmsdot" src="${path }/images/cms/point_13.png"/>
				              		${fn:substring(questionArticle.title, 0, 15)}${fn:length(questionArticle.title) > 15 ? '..' : ''}</a></li>
							</c:forEach>
		                    </ul>
		             </div>
           </div>
           <div class="clear"></div>
           
              <p>评论（<font color="red">${cmsCommentVo.userCount}</font>人参与，<font color="red">${cmsCommentVo.commentCount}</font>条评论）
              
              </p>
            <ul>
            	<c:forEach items="${commentList}" var="comment" varStatus= "idx">
           			<li style="border-bottom:0px; background:#f1f1f1; border-radius:5px;padding-top:5px;padding-bottom:5px;">
           				<span style="padding-right:20px; color:#3366CC;">${comment.username}</span>  
           				<span><fmt:formatDate value="${comment.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/></span>
           			</li>
           			<li style="border-bottom:0px;padding-top:5px;padding-bottom:5px;">${comment.content}</li>
           	    </c:forEach>
            </ul>
            <div class="main-centerbox pd">
            	<textarea rows="8" cols="93" id="commentCon">  </textarea>
            	<br /><br />
             	<input type="button" value="提交" id="btncommentCon" onclick="saveCommentCon();" class="login_btn" style="cursor:pointer;"/>
            
           </div>
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
    <div class="mt10"><a href="${path}/newPeopleArea/newPeopleAreaBiao.html?source=56" ><img src="${path }/images/cms/imgs_10.jpg"  alt="P2P理财"></a>
    </div>
    <div class="mt10">
       <div class="leftbox">
        <div class="bg-h">
          <h2>热门标签</h2> 
        </div>
        <c:forEach items="${cmsTagList21}" var="re21" varStatus="idx21">
			<span><a href="${path }/tag/${re21.id}.html"  target="_blank" title="${re21.name}">${re21.name}</a></span>
		</c:forEach>

        </div>
</div>
</div>
</div>
<!--right end-->
<!--foot-->
<%@include file="/WEB-INF/page/common/footer.jsp"%>
<!--foot end-->
</body>

<script type="text/javascript">
	function saveCommentCon() {
		
		if(${null==portal:currentUser()}){
			 layer.msg("请先登录", 1, 5,function(){
				 window.location.href="${path}/member/toLoginPage.html";
			 });
			 return;
		 }
		
		var artId = "${cmsArticle.id }";
		var commentCon = $.trim($('#commentCon').val());
		
		if(commentCon.length<6){
			layer.msg('评论内容不能为空或长度小于6!',2,5);
			return false;
		}
		if(commentCon.length>1000){
			layer.msg('评论内容长度不能大于1000!',2,5);
			return false;
		}
		
		$("#btncommentCon").removeAttr("onclick");
		var _load = layer.load('处理中..');
		$.ajax({
			url : '${basePath}/saveComment.html',
			data : {
				'id' : artId,
				'commentCon' : commentCon
			},
			type : 'post',
			dataType : 'json',
			success : function(data) {
				$("#btncommentCon").attr("onclick","saveCommentCon()");
				layer.close(_load);
				if (data.code == '0') {
					layer.msg(data.message, 1, 5);
					return;
				} else {
					layer.msg('评论成功', 1, 1, function() {
						runtime = 0;
						window.parent.location.href = window.parent.location.href;	
					});
				}
			},
			error : function(data) {
				$("#btncommentCon").attr("onclick","saveCommentCon()");
				layer.close(_load);
				layer.msg("网络连接异常，请刷新页面或稍后重试！");
		    }
		});
	}
	
</script>

<script>window._bd_share_config={"common":{"bdSnsKey":{},"bdText":"","bdMini":"2","bdMiniList":false,"bdPic":"","bdStyle":"0","bdSize":"24"},"share":{},"image":{"viewList":["tsina","tqq","renren","qzone","douban","weixin"],"viewText":"分享到：","viewSize":"16"},"selectShare":{"bdContainerClass":null,"bdSelectMiniList":["tsina","tqq","renren","qzone","douban","weixin"]}};with(document)0[(getElementsByTagName('head')[0]||body).appendChild(createElement('script')).src='http://bdimg.share.baidu.com/static/api/js/share.js?v=89860593.js?cdnversion='+~(-new Date()/36e5)];</script>

</html>