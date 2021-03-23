package com.dxjr.security;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.PathMatchingFilter;
import org.apache.shiro.web.util.WebUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MonitorFilter extends PathMatchingFilter {
	private static final Logger log = LoggerFactory.getLogger(MonitorFilter.class);
	
	private static final String MONITOR_TOKEN = "monitorToken";
	private static final String PARAM_TOKEN = "token";
	
	// token
	private String token;
	
	public boolean onPreHandle(ServletRequest request, ServletResponse response, Object mappedValue) throws Exception {
		if (this.token == null || this.token.length() == 0) {
			return true;
		}
		
		try {
			String token = WebUtils.getCleanParam(request, PARAM_TOKEN);
			if (this.token.equalsIgnoreCase(token)) {
				saveToken(token);
			}
			
			if (this.token.equalsIgnoreCase(getToken(request))) {
				return true;
			}
			
			clearToken(request);
			WebUtils.issueRedirect(request, response, "/error/404");
		} catch (Exception e) {
			log.error("monitor filter exception.", e);
		}
		return false;
	}
	
	private static void saveToken(String token) {
		Subject subject = SecurityUtils.getSubject();
		Session session = subject.getSession();
		session.setAttribute(MONITOR_TOKEN, token);
	}

	private static void clearToken(ServletRequest request) {
		Subject subject = SecurityUtils.getSubject();
		Session session = subject.getSession();
		session.removeAttribute(MONITOR_TOKEN);
	}

	private static String getToken(ServletRequest request) {
		String token = null;
		Subject subject = SecurityUtils.getSubject();
		Session session = subject.getSession(false);
		if (session != null) {
			token = (String) session.getAttribute(MONITOR_TOKEN);
		}
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}
}