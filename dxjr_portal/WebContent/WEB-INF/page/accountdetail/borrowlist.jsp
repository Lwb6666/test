<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/page/common/taglib.jsp"%>

<script type="text/javascript">
/**翻页处理*/
function findPage(pageNo){
	window.parent.toggleList('borrow',pageNo);
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
                             <td>借款标题</td>
                             <td>借款人</td>
                           <!--   <td align="right">投标金额</td> -->
                             <td>状态</td>
                             <td >通过时间</td>
                          </tr>
				          <c:if test="${null==resultPages.result || fn:length(resultPages.result)==0 }">
				              <tr>
				                <td colspan="5"> <div class="zdtz-none"><p>暂无记录</p></div></td>
				              </tr>
		          		  </c:if>
			               <c:if test="${!empty resultPages.result }">   
			              		<c:forEach items="${resultPages.result}" var="borrowinfo"  varStatus="sta" step="1">   
		                          <tr>
		                            <td><span><a  style="color:#35aaf2;"  href="${basePath }/toubiao/${borrowinfo.id }.html" target="_blank">${borrowinfo.name }</a></span></td>
		                            <td><span>
		                                  <c:choose>
												<c:when test="${borrowinfo.borrowtype == 1 || borrowinfo.borrowtype == 2 || borrowinfo.borrowtype == 5}">
													<a style="color:#35aaf2;" href="${basePath}/accountdetail/show.html?userIdMD5=${borrowinfo.userIdMD5}"  target="_blank"  title="${borrowinfo.userName }">${borrowinfo.userName }</a>
												</c:when> 
												<c:otherwise>		
													<a style="color:#35aaf2;" href="${basePath}/accountdetail/show.html?userIdMD5=${borrowinfo.userIdMD5}"  target="_blank"  title="${borrowinfo.userNameSecret }">${borrowinfo.userNameSecret }</a>
												</c:otherwise>
											</c:choose>
		                            </span></td>
<%-- 		                            <td align="right"><fmt:formatNumber value="${borrowinfo.account }" pattern="#,#00.00"/></td>
 --%>		                            <td><i class="iconfont f20 green">&#xe610;</i></td>
		                            <td>${fn:substring(borrowinfo.successTimeStr, 0, 10)}</td>
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