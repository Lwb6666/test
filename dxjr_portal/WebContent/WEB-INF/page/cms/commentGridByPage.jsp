<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/page/common/taglib.jsp"%>

<head>
<link href="${basePath}/css/circle.css" rel="stylesheet" type="text/css" />


<script type="text/javascript" src="${basePath}/js/init.js"></script>
<script type="text/javascript" src="${basePath}/js/raphael.js"></script>
</head>
<body>
	<c:set value="${page.result[0]}" var="comment" />
    	<input type="hidden" name="articleId" id="articleId" value="${comment.articleId}"/>
                               
                                <table border="0"  >
                                <tr>
                                <th width="260px">评论内容</th>
                                <th width="220px">评论人</th>
                                <th width="100px">评论时间</th>             
                                </tr>
                           		
                                <c:forEach items="${page.result}" var="comment" varStatus= "idx">

                                	<tr>
                                	<td align="center" width="110px;"><i>${comment.content}</i></td>
                                	<td align="center" width="110px;"><i>${comment.username}</i></td>
                               		<td align="center" width="220px;"><i><fmt:formatDate value="${comment.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/></i></td>     
									
	                                </tr>
                                </c:forEach>	
</table>
 			<c:if test="${page.result.size()==0}">
                  <center><b>没有评论内容</b></center>
            </c:if>
 
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
</body>
<script type="text/javascript">
function findPage(pageNum){
	var articleId = $("#articleId").val();
	getComment(pageNum,articleId);
}

</script>
