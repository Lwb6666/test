<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/page/common/taglib.jsp"%>

<script type="text/javascript">
/**翻页处理*/
function findPage(pageNo){
	window.parent.toggleList('borrow',pageNo);
}
</script>

                         <div class="rz_borrow1">
                                      <div class="tbjl">
                                           <table border="0">
				<tr>
					<td width="400">借款标题</td>
					<td width="100">借款人</td>
					<td width="100">借款金额</td>
					<td width="100">状态</td>
					<td width="100">通过时间</td>
				</tr>
				
				<c:if test="${null==resultPages.result || fn:length(resultPages.result)==0 }">
	              	  <tr>
	              	  	 <td colspan="5"><font color="red">无借款记录</font></td>
	              	  </tr>
	              </c:if>
				<c:forEach items="${resultPages.result}" var="borrowinfo"  varStatus="sta"
				step="1">
				<c:if test="${sta.index %2 ==0 }"><tr style="background:#ecfafd"></c:if>
				<c:if test="${sta.index % 2 != 0 }"><tr style="background:#fff"></c:if>
						<td><a href="${path }/toubiao/${borrowinfo.id }.html" target="_blank">${borrowinfo.name }</a></td>
			              <td>
							<c:choose>
							<c:when test="${borrowinfo.borrowtype == 1 || borrowinfo.borrowtype == 2 || borrowinfo.borrowtype == 5}">
								<a href="${path}/accountdetail/show.html?userName=${portal:encode(portal:encode(borrowinfo.userNameEncrypt))}"  target="_blank"  title="${borrowinfo.userName }">${borrowinfo.userName }</a>
							</c:when>
							<c:otherwise>		
								<a href="${path}/accountdetail/show.html?userName=${portal:encode(portal:encode(borrowinfo.userNameEncrypt))}"  target="_blank"  title="${borrowinfo.userNameSecret }">${borrowinfo.userNameSecret }</a>
							</c:otherwise>
							</c:choose>
						  </td>
			              <td> ￥<span id="zcTotal">
			              	<fmt:formatNumber value="${borrowinfo.account }" pattern="#,#00.00"/></span></td>
			              <td><img src="${basePath }/images/tongguo.png"/></td>
			              <td>${fn:substring(borrowinfo.successTimeStr, 0, 10)}</td>
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
