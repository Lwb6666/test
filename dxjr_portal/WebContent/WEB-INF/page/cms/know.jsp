<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
    <%@ include file="/WEB-INF/page/common/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">    
<html>
<head>
	<title>${cmsChannel.seoTitle}</title>   
	<link rel="shortcut icon" href="${path }/images/favicon.ico" type="image/x-icon">
	<meta http-equiv="X-UA-Compatible" content="IE=8">
	<meta name="keywords" content="${cmsChannel.seoKeywords}">
	<meta name="description" content="${cmsChannel.seoDescription}">
	<meta name="generator" content="顶玺金融">
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
        当前位置：<a style="color: #fff"  href="${path}">顶玺金融</a> &gt;<a style="color: #fff"  href="${path}/zixun.html">资讯中心</a> &gt;  <a style="color: #fff"  href="${path}/baike.html" >知识百科</a>
       </div>    
       <div class="centerbox w100">
       <div class="topinfo">
             <table>
				             <tr>
				             <td><h1 class="infogreen">知识百科</h1></td>
				             <td class="rinfo rinfo-w">${cmsChannel.desc }</td>
				             </tr>
             </table>
         </div>
          <div class="leftbox bn">
                            <div class="bg-h">
                              <h2>按字母检索</h2> 
                            </div>
                            <c:forEach  items="${initials}"  var="initials">
                               <span><a href="${path }/baike-${initials}.html">${initials}</a></span>
                           </c:forEach>                 
               </div>
               <div  id="pediaentrieslist"   class="navtag p10"> 
                   <jsp:include page="pediaentriesContent.jsp"></jsp:include>
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
           <c:forEach  items="${newsNoticeVos}"  var="newsNoticeVo">
		            <ul>
		              <li class="col-1"><a   rel="nofollow" target="_blank" class="white" href="${path }/gonggao/${newsNoticeVo.id}.html"  title="${newsNoticeVo.title}"><img class="cmsdot" src="${basePath}/images/cms/point_13.jpg">${fn:substring(newsNoticeVo.title, 0, 10)}${fn:length(newsNoticeVo.title) > 10 ? '..' : ''}</a></li>
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

  $(document).ready(function (){
	  <c:if test="${requestScope.initai!=null}" >
	  		$('.leftbox.bn').find('span:contains(${requestScope.initai})').css("background","#bbffaa");
	  </c:if>
  });
  

function goToInvest(borrowId){
	window.open("${path}/toubiao/"+borrowId+".html"); 
};


function changeStyle(initais){
	 $('.leftbox.bn').find('span:contains('+initais+')').css("background","#bbffaa");
	 $('.leftbox.bn').find('span:not(:contains('+initais+'))').removeAttr("style");
}

function searchEntries(initais){
	changeStyle(initais);
	 $.ajax({
			url : '${basePath}/baike/searchEntries/'+initais+'.html',
			dataType :"html",
			success : function(result) {
				if (result.length>0) {
				   $("#pediaentrieslist").html(result);
				}else{
					layer.msg('加载失败', 1, 5);
				} 
			},
			error : function(result) {
				layer.msg('网络连接超时,请您稍后重试.', 1, 5);
		    }
		});
	
}


//去开通直通车
function toInvestFirst(id){
	window.open("${path}/zhitongche/"+id+".html","_blank");
}



</script>
</html>