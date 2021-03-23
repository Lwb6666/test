package com.dxjr.portal.stockright.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.dxjr.portal.base.BaseController;
import com.dxjr.security.ShiroUser;


/**
 * 
 * <p>
 * Description:url拦截<br />
 * </p>
 * @title BaseFilter.java
 * @package com.dxjr.portal.stockright.filter 
 * @author xiaofei.li
 * @version 0.1 2016-6-6
 */
public abstract class BaseFilter extends BaseController implements Filter{

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        WebApplicationContext wac = WebApplicationContextUtils.getRequiredWebApplicationContext(filterConfig
                .getServletContext());
    }

    @Override
    public void destroy() {

    }

    protected void validateFilter(HttpServletRequest request, HttpServletResponse response, HttpSession httpSession,
            FilterChain chain) throws IOException, ServletException {
    	
       // if (httpSession == null || httpSession.isNew()) {// session非法
        	 this.session1_local_user0(request, response); 
       // }else {// session合法 
        //		ShiroUser loginuser=currentUser();
         //       if (loginuser == null ||  !loginuser.isStock()) {
         //           this.session1_local_user0(request, response); 
          //     }
           //     else { 
          //          chain.doFilter(request, response);
          //      } 
      //  }
    }

    protected abstract void session1_local_user0(HttpServletRequest request, HttpServletResponse response)
            throws IOException;
  
}
