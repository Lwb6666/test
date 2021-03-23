package com.dxjr.security;

import static com.dxjr.utils.HttpTookit.getRealIpAddr;
import static org.apache.shiro.web.util.WebUtils.toHttp;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.PathMatchingFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.dxjr.portal.member.service.MemberService;
import com.dxjr.portal.member.vo.LoginCnd;

public class SsoFilter extends PathMatchingFilter {
	private static final Logger log = LoggerFactory.getLogger(SsoFilter.class);

	private CookieRetrievingCookieGenerator cookieRetrievingCookieGenerator;
	@Autowired
	private MemberService memberService;

	public boolean onPreHandle(ServletRequest request, ServletResponse response, Object mappedValue) throws Exception {
		return onAccessDenied(request, response, mappedValue);
	}

	protected boolean onAccessDenied(ServletRequest request, ServletResponse response, Object mappedValue) {
		try {
			AuthenticationToken token = createToken(request, response);
			if (token != null) {
				Subject subject = getSubject(request, response);
				
				subject.login(token);

				ShiroUser shiroUser = (ShiroUser) subject.getPrincipal();
				LoginCnd loginCnd = new LoginCnd();
				loginCnd.setUserId(shiroUser.getUserId());
				loginCnd.setUserName(shiroUser.getUserName());
				loginCnd.setIp(getRealIpAddr(toHttp(request)));
				loginCnd.setSessionId(toHttp(request).getSession().getId());
				loginCnd.setPlatform(shiroUser.getPlatform());
				memberService.saveLogin(loginCnd);

				log.info("login via sso... [username : " + shiroUser.getUserName() + "]");
			}
		} catch (Exception e) {
			log.error("sso filter exception.", e);
			logout(request, response);
			removeCookie(request, response);
		}
		return true;
	}

	protected AuthenticationToken createToken(ServletRequest request, ServletResponse response) {
		String ticket = cookieRetrievingCookieGenerator.retrieveCookieValue(toHttp(request));
		Subject subject = getSubject(request, response);

		if (ticket == null || ticket.length() == 0) {
			if (subject.isAuthenticated()) {
				logout(request, response);
			}
			return null;
		}

		SsoToken ssoToken = new SsoToken(ticket);
		String cookieUserIdMD5 = (String) ssoToken.getUserIdMD5();
		String shiroUserIdMD5 = subject.isAuthenticated() ? ((ShiroUser) subject.getPrincipal()).getUserIdMD5() : null;
		if (shiroUserIdMD5 != null && shiroUserIdMD5.equals(cookieUserIdMD5)) {
			return null;
		}

		return ssoToken;
	}

	protected void logout(ServletRequest request, ServletResponse response) {
		try {
			getSubject(request, response).logout();
		} catch (Exception e) {
		}
	}

	protected void removeCookie(ServletRequest request, ServletResponse response) {
		try {
			cookieRetrievingCookieGenerator.removeCookie(toHttp(response));
		} catch (Exception e) {
		}
	}

	protected Subject getSubject(ServletRequest request, ServletResponse response) {
		return SecurityUtils.getSubject();
	}

	public void setCookieRetrievingCookieGenerator(CookieRetrievingCookieGenerator cookieRetrievingCookieGenerator) {
		this.cookieRetrievingCookieGenerator = cookieRetrievingCookieGenerator;
	}
}