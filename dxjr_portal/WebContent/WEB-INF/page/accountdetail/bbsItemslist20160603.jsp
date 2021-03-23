<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/page/common/taglib.jsp"%>

<script type="text/javascript">
/**翻页处理*/
function findPage(pageNo){
	window.parent.toggleList('bbs_items',pageNo);
}
</script>

                         <div class="rz_borrow1  ">
                                      <div class="tbjl">
                                           <table border="0">
                                           <tr>
                                           <td>标题</td>
                                           <td>发帖人</td>
                                           <td>栏目</td>
                                           <td>发帖时间</td>
                                           <td>点击量</td>
                                           </tr>
				<c:if test="${null==resultPages.result || fn:length(resultPages.result)==0 }">
	              	  <tr>
	              	  	 <td colspan="5"><font color="red">无发帖记录</font></td>
	              	  </tr>
	              </c:if>
	              <c:forEach items="${resultPages.result}" var="itemsinfo" varStatus="sta"
				step="1">
				<c:if test="${sta.index %2 ==0 }"><tr style="background:#ecfafd"></c:if>
				<c:if test="${sta.index % 2 != 0 }"><tr style="background:#fff"></c:if>
						<td>
						
			              <a href="${bbsPath }/posts/${itemsinfo.id}/1" title="${itemsinfo.title}">
	                      ${fn:substring(itemsinfo.title,0,20)}<c:if test="${fn:length(itemsinfo.title)>20}">..</c:if>
	                      
	                      <c:if test="${itemsinfo.isDelete==1}">
	                      <span style="color:red">(已删除)</span>
	                      </c:if>
	                      
	                      
	                      </a>
			              </td>
			              <td><a href="${path}/accountdetail/show.html?userName=${portal:encode(portal:encode(itemsinfo.userNameEncrypt))}" target="_blank"  title="${itemsinfo.userNameSecret }">${itemsinfo.userNameSecret }</a></td>
			              <td>${itemsinfo.columnName}</td>
			              <td><fmt:formatDate value="${itemsinfo.createTime}" type="both" pattern="yyyy-MM-dd"/></td>
			              <td>${itemsinfo.clickNum}</td>
		              </tr>
				</c:forEach>
                         
                                        </table>                                                                               
                                        </div>
                                <!--yema st-->
                                      <div class="yema">
                                                <div class="yema_cont">
                                                    <div class="yema rt">
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
                                      <!--yema off-->
                         </div>                                            
