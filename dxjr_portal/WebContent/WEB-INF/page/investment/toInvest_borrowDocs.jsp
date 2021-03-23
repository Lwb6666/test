<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/page/common/taglib.jsp"%>
<%@ include file="/WEB-INF/page/common/public.jsp"%>
		<div class="rz_borrow1" >
					<c:forEach items="${accountUploadDocVos}" var="o">
						<div style="margin:0 auto;text-align: center;width: 100%;height: 100%">
							<img  src="${basePath}/${o.docPath}"  />
						</div>
					</c:forEach>
		</div>
