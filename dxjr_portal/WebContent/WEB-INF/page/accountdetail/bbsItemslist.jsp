<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/page/common/taglib.jsp"%>

<script type="text/javascript">
/**翻页处理*/
function findPage(pageNo){
	window.parent.toggleList('bbs_items',pageNo);
}

$(document).ready(function(){ 
           var color="#f0f7ff"
           $(".tbtr tr:even,.tbtr01 tr:even,.tbtr02 tr:even,.tbtr03 tr:even,.tbtr04 tr:even,.tbtr05 tr:even,.tbtr06 tr:even,.tbtr07 tr:even,.tbtr08 tr:even,.tbtr09 tr:even,.tbtr10 tr:even,.tbtr11 tr:even,.tbtr12 tr:even,.tbtr13 tr:even,.tbtr14 tr:even,.tbtr15 tr:even,.tbtr16 tr:even,.tbtr17 tr:even").attr("bgcolor",color);//改变奇数行背景色
     })

</script>
<div class="menucont" style="clear:both">
			<div class="tbl-cont">
   				<table width="100%" border="0" cellspacing="0" cellpadding="0" class="table center tbtr02">
                           <tr class="tbl-title">
                            <td>标题</td>
                            <td>发帖人</td>
                            <td>栏目</td>
                            <td>发帖时间</td>
                            <td>点击量</td>
                          </tr>
				          <c:if test="${null==resultPages.result || fn:length(resultPages.result)==0 }">
				              <tr>
				                <td colspan="5"> <div class="zdtz-none"><p>暂无记录</p></div></td>
				              </tr>
		          		  </c:if>
			               <c:if test="${!empty resultPages.result }">   
			              	 <c:forEach items="${resultPages.result}" var="itemsinfo" varStatus="sta" step="1">       
		                          <tr>
		                            <td><span><a  style="color:#35aaf2;"  href="${bbsPath }/posts/${itemsinfo.id}/1" title="${itemsinfo.title}">${fn:substring(itemsinfo.title,0,20)}<c:if test="${fn:length(itemsinfo.title)>20}">..</c:if><c:if test="${itemsinfo.isDelete==1}"><span style="color:red">(已删除)</span></c:if></a></span></td>
		                            <td><span>
		                                   <a style="color:#35aaf2;"  href="${basePath}/accountdetail/show.html?userIdMD5=${itemsinfo.userIdMD5}" target="_blank"  title="${itemsinfo.userNameSecret }">${itemsinfo.userNameSecret }</a>
		                            </span></td>
		                            <td>
									${itemsinfo.columnName}
									</td>
		                            <td><fmt:formatDate value="${itemsinfo.createTime}" type="both" pattern="yyyy-MM-dd"/></td>
		                            <td>${itemsinfo.clickNum}</td>
		                          </tr>
		                      </c:forEach>
	                       </c:if>
                     </table>       
		                   <div>
								<jsp:include page="/WEB-INF/page/common/ajaxpage.jsp">
									<jsp:param name="pageNo" value="${resultPages.pageNo}" />
									<jsp:param name="totalPage" value="${resultPages.totalPage}" />
									<jsp:param name="hasPre" value="${resultPages.hasPre}" />
									<jsp:param name="prePage" value="${resultPages.prePage}" />
									<jsp:param name="hasNext" value="${resultPages.hasNext}" />
									<jsp:param name="nextPage" value="${resultPages.nextPage}" />
								</jsp:include>
					      </div>             
		                </div>
               </div> 