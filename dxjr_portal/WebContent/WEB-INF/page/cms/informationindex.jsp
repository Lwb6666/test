<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>顶玺资讯_互联网理财平台-顶玺金融</title>
<link rel="shortcut icon" href="images/favicon.ico" type="image/x-icon">
<meta name="keywords" content="顶玺资讯理财平台,互联网理财,消费金融,互联网理财产品,房产抵押,小额贷款平台">
<meta name="description" content="顶玺金融（www.dxjr.com），一个专注于消费金融与房产抵押贷款的中国社科院网贷评级A级的理财平台。致力于为投资理财用户和贷款用户两端搭建安全、规范、透明的互联网金融平台。投资理财用户可通过顶玺金融官网官方网站进行散标投资、定期宝、活期宝、购买债权转让等方式进行投资获得稳定收益。">
<meta name="generator" content="顶玺金融">
<meta name="author" content="顶玺金融">
<meta http-equiv="X-UA-Compatible" content="IE=8">
<meta name="copyright" content="2014 上海顶玺金融信息服务有限公司">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
</head>
<body>
	<%@include file="cmshead.jsp"%>
	<link href="${basePath}/css/circle.css" rel="stylesheet" type="text/css" />
	<script type="text/javascript" src="${basePath}/js/init.js"></script>
	<script type="text/javascript" src="${basePath}/js/raphael.js"></script>
	<script type="text/javascript" src="${basePath}/js/jquery.touchSliderzc.js"></script>
	<div class="clear"></div>

	<div id="bbs-hub">
		<!--left-->
		<div class="main-left mr15">
			<div class="feat-area">
				<div class="main_visual">

					<%--  <div class="flicking_con">
	                	<div class="flicking_inner">
	                	<c:forEach  items="${topsCmsArticles}"   var="cmsArticle"  varStatus="index">
	                      <a href="#">${index.index+1}</a>
	                    </c:forEach>
	                  	 </div>
	                   </div> 
	            --%>

					<div class="main_image">
						<ul>
							<c:forEach items="${topsCmsArticles}" var="cmsArticle" varStatus="index">
								<li><a title="${cmsArticle.title}" href="${path}/zixun/${cmsArticle.id}.html" target="_blank"><span
										<c:if test="${not empty cmsArticle.thumbnail}">
										       style="background: url('${cmsArticle.thumbnail}') center top no-repeat;position:relative;"
										   </c:if>
										<c:if test="${empty cmsArticle.thumbnail}">
										     style="background: url('${path}/images/cms/cmssmalllogo.jpg') center top no-repeat;position:relative;"
										   </c:if>>
									</span>
										<div style="width: 310px; height: 50px; position: absolute; bottom: 38px; left: 0; background: #74b000; color: #fff; font-size: 16px; text-align: center; z-index: 999; line-height: 50px;">
											${fn:substring(cmsArticle.title, 0, 15)}${fn:length(cmsArticle.title) > 15 ? '..' : ''}</div> 
											</a></li>
							</c:forEach>
						</ul>
						<a href="javascript:;" id="btn_prev"></a> <a href="javascript:;" id="btn_next"></a>
					</div>
				</div>
				<div style="text-align: center; clear: both"></div>
			</div>
			<div class="main-k mt10">
				<div class="bg-h">
					<h2>知识百科</h2>
					<a class="bg-more" target="_blank" href="${path}/baike.html">更多&gt;&gt;</a>
				</div>
				<div class="pl15">
					<c:forEach items="${cmsPediaEntrys}" var="cmsPediaEntry">
						<span class="mr10"><a href="${path}/baike/${cmsPediaEntry.id}.html" title="${cmsPediaEntry.name}">${cmsPediaEntry.name}</a></span>
					</c:forEach>
				</div>
			</div>
		</div>
		<!--left end-->
		<!--center-->
		<div class="main-center">
			<div class="main-k">
				<div class="bg-h">
					<h2>热门文章</h2>
				</div>

				<c:forEach items="${hotsCmsArticles}" var="hotsCmsArticle" begin="0" end="1">
					<div class="pl15 mt10">
						<a title="${hotsCmsArticle.title}" href="${path}/zixun/${hotsCmsArticle.id}.html" target="_blank"><h3 class="tcenter gblue">${hotsCmsArticle.title}</h3></a><a href="${path}/zixun/${hotsCmsArticle.id}.html"
							title="${hotsCmsArticle.summary}" target="_blank">${fn:substring(hotsCmsArticle.summary, 0, 40)}${fn:length(hotsCmsArticle.summary) > 40 ? '..' : ''}</a>
					</div>
					<div class="point"></div>
				</c:forEach>
				<div class="main-centerbox pdnone">
					<ul>
						<c:forEach items="${hotsCmsArticles}" var="cmsArticleVo" begin="2">
							<li><span><a href="${path }/${cmsArticleVo.urlCode}.html">${cmsArticleVo.channelName}</a></span><a title="${cmsArticleVo.title}" href="${path}/zixun/${cmsArticleVo.id}.html" title="${cmsArticleVo.title}"
								target="_blank">${fn:substring(cmsArticleVo.title, 0, 16)}${fn:length(cmsArticleVo.title) > 16 ? '..' : ''}</a> <label style="float: right;"><fmt:formatDate value="${cmsArticleVo.createTime}"
										pattern="yyyy-MM-dd" /></label></li>
						</c:forEach>
					</ul>
				</div>
			</div>
		</div>

		<!--center end-->
		<!--right-->
		<div class="rightwidth">
			<jsp:include page="tabcontent.jsp"></jsp:include>

			<div class="greens mt10">
				<div class="leftbox">
					<h2 class="white pdl10">顶玺最新公告</h2>
					<c:forEach items="${newsNoticeVos}" var="newsNoticeVo">
						<ul>
							<li class="col-1"><a target="_blank" rel="nofollow" title="${newsNoticeVo.title}" class="white" href="${path }/gonggao/${newsNoticeVo.id}.html"><img class="cmsdot" alt="${newsNoticeVo.title }"
									src="${basePath}/images/cms/point_13.jpg">${fn:substring(newsNoticeVo.title, 0, 10)}${fn:length(newsNoticeVo.title) > 10 ? '..' : ''}</a></li>
							<li class="col-2 white">${newsNoticeVo.addtimeStr}</li>
						</ul>
					</c:forEach>
				</div>
			</div>
		</div>

		<!--right end-->
		<div class="clear"></div>
		<!--center-->
		<div id="licaiContent" class="centerbox mt10">
			<div id class="leftwidth">
				<div class="bg-h">
					<div class="navsub">


						<div style="width: 100%" id="licaiTable">
							<c:forEach items="${indexCmsChannels}" var="cmsChannel" varStatus="index">
								<c:if test="${(index.index+1)%7==1}">
									<ul id="${(index.index+1)}licaiTabletr" <c:if test="${(index.index+1)!=1}">  style="display:none" </c:if>>
										<li><div onclick="showTr('licaiTable',${(index.index+1-7)})" <c:if test="${(index.index+1)==1}">  style="display:none" </c:if> class="curson pd10">
												<img src="${path}/images/cms/arrow_d.png" />
											</div></li>
								</c:if>
								<li width="15%"><h2>
										<a href="${path}/${cmsChannel.urlCode}.html">${cmsChannel.name}</a>
									</h2></li>
								<c:if test="${(index.index+1)%7==0 }">
									<li><div class="curson pd10" onclick="showTr('licaiTable',${(index.index+2)})">
											<img src="${path}/images/cms/arrow_e.png" />
										</div></li>
									</ul>
								</c:if>
							</c:forEach>
						</div>




					</div>
				</div>


				<div class="pbox1 pbox1-w367">
					<div class="centerbox bn pd">
						<c:forEach items="${moneyManagementCmsArticles}" var="articles" varStatus="index">
							<c:if test="${(index.index+1)%2==1 }">
								<c:if test="${index.index==0 }">
									<a href="${path}/zixun/${articles.id}.html" title="${articles.title }"> <c:if test="${not empty articles.thumbnail}">
											<img src="${articles.thumbnail}" class="main-pic1" alt="${articles.title }" title="${articles.title }">
										</c:if> <c:if test="${empty articles.thumbnail}">
											<img src="${basePath}/images/cms/cmssmalllogo.jpg" class="main-pic1" title="${articles.title }">
										</c:if>
									</a>
									<a href="${path}/zixun/${articles.id}.html" title="${articles.title }" target="_blank">
										<h3 class="gblue">${fn:substring(articles.title, 0, 12)}${fn:length(articles.title) > 12 ? '..' : ''}</h3>
									</a>
									<span class="green">${articles.author } <fmt:formatDate value="${articles.createTime}" pattern="MM-dd" /></span>
									<br>
									<a href="${path}/zixun/${articles.id}.html" title="${articles.summary }" target="_blank">${fn:substring(articles.summary, 0, 18)}${fn:length(articles.summary) > 18 ? '..' : ''}</a>
					</div>
					<div class="clear"></div>
					<div class="leftbox bn pd">
						</c:if>
						<c:if test="${index.index!=0 }">
							<ul>
								<li class="col-3"><a title="${articles.title }" target="_blank" href="${path}/zixun/${articles.id}.html"><img class="cmsdot" src="${basePath}/images/cms/point_13.png">${fn:substring(articles.title, 0, 18)}${fn:length(articles.title) > 18 ? '..' : ''}</a></li>
								<li class="col-4"><fmt:formatDate value="${articles.createTime}" pattern="MM-dd" /></li>
							</ul>
						</c:if>
						</c:if>
						</c:forEach>
					</div>
				</div>

				<div class="pbox1 pbox1-w367">
					<div class="centerbox bn pd">
						<c:forEach items="${moneyManagementCmsArticles}" var="articles" varStatus="index">
							<c:if test="${(index.index+1)%2==0 }">
								<c:if test="${index.index==1 }">
									<a href="${path}/zixun/${articles.id}.html" title="${articles.title }"> <c:if test="${not empty articles.thumbnail}">
											<img src="${articles.thumbnail}" class="main-pic1">
										</c:if> <c:if test="${empty articles.thumbnail}">
											<img src="${basePath}/images/cms/cmssmalllogo.jpg" class="main-pic1">
										</c:if>
									</a>
									<a href="${path}/zixun/${articles.id}.html" title="${articles.title }" target="_blank"><h3 class="gblue">${fn:substring(articles.title, 0, 12)}${fn:length(articles.title) > 12 ? '..' : ''}</h3></a>
									<span class="green">${articles.author } <fmt:formatDate value="${articles.createTime}" pattern="MM-dd" /></span>
									<br>
									<a href="${path}/zixun/${articles.id}.html" title="${articles.summary }" target="_blank">${fn:substring(articles.summary, 0, 18)}${fn:length(articles.summary) > 18 ? '..' : ''}</a>
					</div>
					<div class="clear"></div>
					<div class="leftbox bn pd">
						</c:if>
						<c:if test="${index.index!=1 }">
							<ul>
								<li class="col-3"><a target="_blank" title="${articles.title }" href="${path}/zixun/${articles.id}.html"><img class="cmsdot" src="${basePath}/images/cms/point_13.png">${fn:substring(articles.title, 0, 18)}${fn:length(articles.title) > 18 ? '..' : ''}</a></li>
								<li class="col-4"><fmt:formatDate value="${articles.createTime}" pattern="MM-dd" /></li>
							</ul>
						</c:if>
						</c:if>
						</c:forEach>
					</div>
				</div>

			</div>

			<!--center right-->
			<div class="rightwidth">
				<div class="leftbox bn">
					<div class="bg-h">
						<h2>理财标签</h2>
					</div>
					<c:forEach items="${cmsTags }" var="tag">
						<span><a href="${path}/tag/${tag.id}.html" title="${tag.name}">${tag.name}</a></span>
					</c:forEach>
				</div>
			</div>
			<!--center right end-->
		</div>
		<!--center end-->
		<div class="mt10">
			<a href="${path}/newPeopleArea/newPeopleAreaBiao.html?source=55" title="互联网金融"><img alt="互联网金融" src="${basePath}/images/cms/imgs_11.jpg"></a>
		</div>
		<!--center-->
		<div id="daikuanContent" class="centerbox mt10">
			<div class="leftwidth">
				<div class="bg-h">
					<div class="navsub">



						<div style="width: 100%" id="daikuanTable">
							<c:forEach items="${loanCmsChannels}" var="cmsChannel" varStatus="index">
								<c:if test="${(index.index+1)%7==1}">
									<ul id="${(index.index+1)}daikuanTabletr" <c:if test="${(index.index+1)!=1}">  style="display:none" </c:if>>
										<li><div onclick="showTr('daikuanTable',${(index.index+1-7)})" <c:if test="${(index.index+1)==1}">  style="display:none" </c:if> class="curson pd10">
												<img src="${path}/images/cms/arrow_d.png" />
											</div></li>
								</c:if>
								<li><h2>
										<a href="${path}/${cmsChannel.urlCode}.html">${cmsChannel.name}</a>
									</h2></li>
								<c:if test="${(index.index+1)%7==0 }">
									<li><div onclick="showTr('daikuanTable',${(index.index+2)})" class="curson pd10">
											<img src="${path}/images/cms/arrow_e.png" />
										</div></li>
									</ul>
								</c:if>
							</c:forEach>
						</div>




					</div>
				</div>
				<div class="pbox1 pbox1-w367">
					<div class="centerbox bn pd">
						<c:forEach items="${loanCmsArticles}" var="articles" varStatus="index">
							<c:if test="${(index.index+1)%2==1 }">
								<c:if test="${index.index==0 }">
									<a href="${path}/zixun/${articles.id}.html"> <c:if test="${not empty articles.thumbnail}">
											<img src="${articles.thumbnail}" class="main-pic1">
										</c:if> <c:if test="${empty articles.thumbnail}">
											<img src="${basePath}/images/cms/cmssmalllogo.jpg" class="main-pic1">
										</c:if>
									</a>
									<a href="${path}/zixun/${articles.id}.html" title="${articles.title }" target="_blank"><h3 class="gblue">${fn:substring(articles.title, 0, 12)}${fn:length(articles.title) > 12 ? '..' : ''}</h3></a>
									<span class="green">${articles.author } <fmt:formatDate value="${articles.createTime}" pattern="MM-dd" /></span>
									<br>
									<a target="_blank" title="${articles.summary}" href="${path}/zixun/${articles.id}.html">${fn:substring(articles.summary, 0, 18)}${fn:length(articles.summary) > 18 ? '..' : ''}</a>
					</div>
					<div class="clear"></div>
					<div class="leftbox bn pd">
						</c:if>
						<c:if test="${index.index!=0 }">
							<ul>
								<li class="col-3"><a target="_blank" title="${articles.title }" href="${path}/zixun/${articles.id}.html"><img class="cmsdot" src="${basePath}/images/cms/point_13.png">${fn:substring(articles.title, 0, 18)}${fn:length(articles.title) > 18 ? '..' : ''}</a></li>
								<li class="col-4"><fmt:formatDate value="${articles.createTime}" pattern="MM-dd" /></li>
							</ul>
						</c:if>
						</c:if>
						</c:forEach>

					</div>
				</div>
				<div class="pbox1 pbox1-w367">
					<div class="centerbox bn pd">
						<c:forEach items="${loanCmsArticles}" var="articles" varStatus="index">
							<c:if test="${(index.index+1)%2==0 }">
								<c:if test="${index.index==1 }">
									<a href="${path}/zixun/${articles.id}.html"> <c:if test="${not empty articles.thumbnail}">
											<img src="${articles.thumbnail}" class="main-pic1">
										</c:if> <c:if test="${empty articles.thumbnail}">
											<img src="${basePath}/images/cms/cmssmalllogo.jpg" class="main-pic1">
										</c:if>
									</a>
									<a href="${path}/zixun/${articles.id}.html" title="${articles.title }" target="_blank"><h3 class="gblue">${fn:substring(articles.title, 0, 12)}${fn:length(articles.title) > 12 ? '..' : ''}</h3></a>
									<span class="green">${articles.author } <fmt:formatDate value="${articles.createTime}" pattern="MM-dd" /></span>
									<br>
									<a target="_blank" title="${articles.summary}" href="${path}/zixun/${articles.id}.html">${fn:substring(articles.summary, 0, 18)}${fn:length(articles.summary) > 18 ? '..' : ''}</a>
					</div>
					<div class="clear"></div>
					<div class="leftbox bn pd">
						</c:if>
						<c:if test="${index.index!=1 }">
							<ul>
								<li class="col-3"><a target="_blank" title="${articles.title }" href="${path}/zixun/${articles.id}.html"><img class="cmsdot" src="${basePath}/images/cms/point_13.png">${fn:substring(articles.title, 0, 18)}${fn:length(articles.title) > 18 ? '..' : ''}</a></li>
								<li class="col-4"><fmt:formatDate value="${articles.createTime}" pattern="MM-dd" /></li>
							</ul>
						</c:if>
						</c:if>
						</c:forEach>

					</div>
				</div>
			</div>

			<!--center right-->
			<div class="rightwidth">
				<div class="leftbox bn">
					<div class="bg-h">
						<h2>贷款标签</h2>
					</div>
					<c:forEach items="${loanCmsTags}" var="tag">
						<span><a href="${path}/tag/${tag.id}.html" title="${tag.name}">${tag.name}</a></span>
					</c:forEach>
				</div>
			</div>
			<!--center right end-->
		</div>
		<!--center end-->
		<div class="mt10">
			<a href="${path}/newPeopleArea/newPeopleAreaBiao.html?source=55" title="互联网金融"><img alt="互联网金融" src="${basePath}/images/cms/imgs_11.jpg"></a>
		</div>
		<div class="mt10">
			<div class="leftbox fl pbox1-w320 mr10">
				<div class="bg-h">
					<h2>投资攻略</h2>
				</div>

				<c:forEach items="${InvestmentStrategyCmsArticles }" var="article" varStatus="index">
					<c:if test="${index.index==0 }">
						<div class="pl15 pd4">
							<a href="${path}/zixun/${article.id}.html" target="_blank"><h3 class="gblue">${article.title}</h3></a><a target="_blank" title="${article.summary }" href="${path}/zixun/${article.id}.html">${fn:substring(article.summary, 0, 40)}${fn:length(article.summary) > 40 ? '..' : ''}</a>
						</div>
					</c:if>
					<c:if test="${index.index==1 }">
						<div class="point"></div>
						<div class="pl15 pd10">
							<a href="${path}/zixun/${article.id}.html" target="_blank"><h3 class="gblue">${article.title}</h3></a><a target="_blank" title="${article.summary }" href="${path}/zixun/${article.id}.html">${fn:substring(article.summary, 0, 40)}${fn:length(article.summary) > 40 ? '..' : ''}</a>
						</div>
					</c:if>
					<c:if test="${index.index==2 }">
						<ul>
							<li><img class="cmsdot" src="${basePath}/images/cms/point_13.png"><a target="_blank" title="${article.title}" href="${path}/zixun/${article.id}.html">${fn:substring(article.title, 0, 18)}${fn:length(article.title) > 18 ? '..' : ''}</a></li>
					</c:if>
					<c:if test="${index.index!=0 && index.index!=1 && index.index!=2}">
						<li><img class="cmsdot" src="${basePath}/images/cms/point_13.png"><a target="_blank" title="${article.title}" href="${path}/zixun/${article.id}.html">${fn:substring(article.title, 0, 18)}${fn:length(article.title) > 18 ? '..' : ''}</a></li>
					</c:if>

				</c:forEach>
				</ul>

			</div>
			<div class="leftbox fl pbox1-w320 mr10">
				<div class="bg-h">
					<h2>政策法规</h2>
				</div>

				<c:forEach items="${regulationsCmsArticles }" var="article" varStatus="index">
					<c:if test="${index.index==0 }">
						<div class="pl15 pd4">
							<a href="${path}/zixun/${article.id}.html" target="_blank"><h3 class="gblue">${article.title}</h3></a><a target="_blank" title="${article.summary}" href="${path}/zixun/${article.id}.html">${fn:substring(article.summary, 0, 40)}${fn:length(article.summary) > 40 ? '..' : ''}</a>
						</div>
					</c:if>
					<c:if test="${index.index==1 }">
						<div class="point"></div>
						<div class="pl15 pd10">
							<a href="${path}/zixun/${article.id}.html" target="_blank"><h3 class="gblue">${article.title}</h3></a><a target="_blank" title="${article.summary}" href="${path}/zixun/${article.id}.html">${fn:substring(article.summary, 0, 40)}${fn:length(article.summary) > 40 ? '..' : ''}</a>
						</div>
					</c:if>
					<c:if test="${index.index==2 }">
						<ul>
							<li><img class="cmsdot" src="${basePath}/images/cms/point_13.png"><a title="${article.title}" target="_blank" href="${path}/zixun/${article.id}.html">${fn:substring(article.title, 0, 18)}${fn:length(article.title) > 18 ? '..' : ''}</a></li>
					</c:if>
					<c:if test="${index.index!=0 && index.index!=1 && index.index!=2}">
						<li><img class="cmsdot" src="${basePath}/images/cms/point_13.png"><a title="${article.title}" target="_blank" href="${path}/zixun/${article.id}.html">${fn:substring(article.title, 0, 18)}${fn:length(article.title) > 18 ? '..' : ''}</a></li>
					</c:if>

				</c:forEach>
				</ul>



			</div>
			<div class="leftbox fl pbox1-w320">
				<div class="bg-h">
					<h2>常见问题</h2>
				</div>
				<ul>

					<c:forEach items="${faqCmsArticles }" var="article" varStatus="index">
						<li><font color="#74b000">${index.index+1}.</font><a target="_blank" title="${article.title}" href="${path}/zixun/${article.id}.html">${fn:substring(article.title, 0, 18)}${fn:length(article.title) > 18 ? '..' : ''}</a></li>
					</c:forEach>

				</ul>
			</div>
		</div>
	</div>

	<div class="clearfix"></div>
	<%@ include file="/WEB-INF/page/common/footer.jsp"%>

   
	<script type="text/javascript">
	$(document).ready(function () {
		$(".main_visual").hover(function(){
			$("#btn_prev,#btn_next").fadeIn();
			},function(){
			$("#btn_prev,#btn_next").fadeOut();
			});
		$dragBln = false;
		$(".main_image").touchSlider({
			flexible : true,
			speed : 200,
			btn_prev : $("#btn_prev"),
			btn_next : $("#btn_next"),
			paging : $(".flicking_con a"),
			counter : function (e) {
				$(".flicking_con a").removeClass("on").eq(e.current-1).addClass("on");
			}
		});
		$(".main_image").bind("mousedown", function() {
			$dragBln = false;
		});
		$(".main_image").bind("dragstart", function() {
			$dragBln = true;
		});
		$(".main_image a").click(function() {
			if($dragBln) {
				return false;
			}
		});
		timer = setInterval(function() { $("#btn_next").click();}, 5000);
		$(".main_visual").hover(function() {
			clearInterval(timer);
		}, function() {
			timer = setInterval(function() { $("#btn_next").click();}, 5000);
		});
		$(".main_image").bind("touchstart", function() {
			clearInterval(timer);
		}).bind("touchend", function() {
			timer = setInterval(function() { $("#btn_next").click();}, 5000);
		});
		 
		$("#licaiTable").find("ul:last").find("li:last").find("img").remove();
		$("#daikuanTable").find("ul:last").find("li:last").find("img").remove();
	});
	
    //去开通直通车
	function toInvestFirst(id){
		window.open("${path}/zhitongche/"+id+".html","_blank");
	}
	   
    function  showTr(type,id){
	      	$("#"+type).find("ul").each(function (){
	      		if($(this).attr("id")!=id+type+"tr"){
	      			$(this).attr("style","display:none");
	      		}else{
	      			$(this).attr("style","display:block");
	      		}
	      		
	      	});
    }
    
	    
	</script>
</body>

</html>