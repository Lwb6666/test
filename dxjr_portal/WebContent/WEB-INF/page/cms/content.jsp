<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>    
<%@ include file="/WEB-INF/page/common/taglib.jsp"%>    
    
 <div      class="leftwidth">
     <div class="bg-h">
       <div class="navsub">
              <ul>
                    <c:forEach  items="${cmsChannels}"  var="cmsChannel"   >
		               <li  onclick="searchArticles(${type},${cmsChannel.id})"   ><h2><a href="javascript:;"       <c:if test="${cmsChannel.id==channel.id }"> class="current"</c:if>    >${cmsChannel.name}</a></h2></li>
		            </c:forEach> 
              </ul>
       </div>
    </div>
			    <div class="pbox1 pbox1-w367">
						    <div class="centerbox bn pd">
						    <c:forEach  items="${cmsArticles}"  var="articles"  varStatus="index" >
							         <c:if test="${(index.index+1)%2==1 }">
										             <c:if test="${index.index==0 }">

														<c:if test="${not empty articles.thumbnail}">
														      <img   src="${articles.thumbnail}" class="main-pic1">
														   </c:if>
														    <c:if test="${empty articles.thumbnail}">
														      <img   src="${basePath}/images/main/head.png" class="main-pic1">
														   </c:if>									            

													    <h3 class="gblue">${fn:substring(articles.title, 0, 10)}${fn:length(articles.title) > 10 ? '..' : ''}</h3>
													    <span class="green">${articles.author }   <fmt:formatDate value="${articles.createTime}" pattern="MM-dd"/></span><br>
													    <a href="${path}/zixun/${articles.id}.html">${fn:substring(articles.summary, 0, 20)}${fn:length(articles.summary) > 20 ? '..' : ''}</a>
													    </div>
													    <div class="clear"></div>                 
													     <div class="leftbox bn pd">
												     </c:if>
												      <c:if test="${index.index!=0 }">
													            <ul>
													              <li class="col-1"><a href="${path}/zixun/${articles.id}.html"><img class="cmsdot" src="${basePath}/images/cms/point_13.png">${fn:substring(articles.title, 0, 10)}${fn:length(articles.title) > 10 ? '..' : ''}</a></li>
													              <li class="col-2"><fmt:formatDate value="${articles.createTime}" pattern="MM-dd"/></li>
													            </ul>
							                          </c:if>
									    </c:if>
								 </c:forEach>
					             
					         </div>
			            </div>
				 <div class="pbox1 pbox1-w367">
						    <div class="centerbox bn pd">
						    
						      <c:forEach  items="${cmsArticles}"  var="articles"  varStatus="index" >
							         <c:if test="${(index.index+1)%2==0 }">
										             <c:if test="${index.index==1 }">
										                  <c:if test="${not empty articles.thumbnail}">
														      <img   src="${articles.thumbnail}" class="main-pic1">
														   </c:if>
														    <c:if test="${empty articles.thumbnail}">
														      <img   src="${basePath}/images/main/head.png" class="main-pic1">
														   </c:if>
													    <h3 class="gblue">${fn:substring(articles.title, 0, 10)}${fn:length(articles.title) > 10 ? '..' : ''}</h3>
													    <span class="green">${articles.author }   <fmt:formatDate value="${articles.createTime}" pattern="MM-dd"/></span><br>
													    <a href="${path}/zixun/${articles.id}.html">${fn:substring(articles.summary, 0, 20)}${fn:length(articles.summary) > 20 ? '..' : ''}</a>
													    </div>
													    <div class="clear"></div>                 
													     <div class="leftbox bn pd">
												     </c:if>
												      <c:if test="${index.index!=1 }">
													            <ul>
													              <li class="col-1"><a href="#"><img class="cmsdot" src="${basePath}/images/cms/point_13.png">${fn:substring(articles.title, 0, 10)}${fn:length(articles.title) > 10 ? '..' : ''}</a></li>
													              <li class="col-2"><fmt:formatDate value="${articles.createTime}" pattern="MM-dd"/></li>
													            </ul>
							                          </c:if>
									    </c:if>
								 </c:forEach>
					            
					        </div>
				        </div>
      </div>
      
      <div class="rightwidth">
       <div class="leftbox bn">
        <div class="bg-h">
          <h2>${channel.name}标签</h2> 
        </div>
        <c:forEach  items="${cmsTags}"  var="tag">
           <span><a href="${path}/tag/${tag.id}.html">${tag.name}</a></span>
        </c:forEach>
		</div>
		</div>
 