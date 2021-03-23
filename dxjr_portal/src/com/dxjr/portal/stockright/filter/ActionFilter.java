package com.dxjr.portal.stockright.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * 
 * <p>
 * Description:url拦截器<br />
 * </p>
 * @title ActionFilter.java
 * @package com.dxjr.portal.stockright.filter 
 * @author xiaofei.li
 * @version 0.1 2016-6-6
 */

public class ActionFilter extends BaseFilter {

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		final HttpServletRequest servletRequest = (HttpServletRequest) request;
		final HttpServletResponse servletResponse = (HttpServletResponse) response;
		HttpSession httpSession = servletRequest.getSession(false);

		String path = servletRequest.getRequestURI();
		//股权模块拦截器
		if(path.matches(".*/stockSeller/.*") || path.matches(".*/stockDeal/.*") || path.matches(".*/stockBuyer/.*")
				|| path.matches(".*/stockApply/.*")|| path.matches(".*/stockAccount/.*")){
			
		//	if(path.endsWith("/checkUserInfo.html") ||  path.endsWith("/accountIndex.html")){
			//	chain.doFilter(servletRequest, servletResponse);
			//}else{
				super.validateFilter(servletRequest, servletResponse, httpSession, chain);
			//}
		}else{
			chain.doFilter(servletRequest, servletResponse);
		}

	}

	protected void session1_local_user0(HttpServletRequest request, HttpServletResponse response) throws IOException {
		response.sendRedirect(request.getContextPath() + "/common/404");
	}
 
	public void destroy() {

	}

}