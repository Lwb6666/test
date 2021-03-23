package com.dxjr.portal.base;

import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.httpclient.HttpClient;
import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.ModelAndView;

import com.dxjr.portal.member.service.BlackListSevice;
import com.dxjr.security.ShiroUser;
import com.dxjr.utils.CSRFTokenManager;

public abstract class BaseController {
	private static final Logger logger = Logger.getLogger(BaseController.class);
	@Autowired
	private BlackListSevice blackListSevice;

	// @InitBinder
	// public void initBinder(WebDataBinder binder) {
	// binder.registerCustomEditor(Date.class, new
	// CustomDateEditor(DateUtils.getFormat(DateUtils.YMD_DASH), true)); //
	// true:允许输入空值,false:不能为空值
	// }

	protected ModelAndView forword(String viewName) {
		return new ModelAndView(viewName);
	}

	protected ModelAndView forword(String viewName, String modelName, Object modelObject) {
		return new ModelAndView(viewName, modelName, modelObject);
	}

	protected ModelAndView forword(String viewName, Map<String, ?> modelMap) {
		return new ModelAndView(viewName, modelMap);
	}

	protected ModelAndView redirect(String viewName) {
		return new ModelAndView("redirect:" + viewName);
	}

	protected ModelAndView redirect(String viewName, String modelName, Object modelObject) {
		return new ModelAndView("redirect:" + viewName, modelName, modelObject);
	}

	protected ModelAndView redirect(String viewName, Map<String, ?> modelMap) {
		return new ModelAndView("redirect:" + viewName, modelMap);
	}

	protected HttpServletRequest currentRequest() {
		RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
		return ((ServletRequestAttributes) requestAttributes).getRequest();
	}

	protected HttpSession currentSession() {
		return currentRequest().getSession();
	}

	protected ShiroUser currentUser() {
		return (ShiroUser) SecurityUtils.getSubject().getPrincipal();
	}

	protected boolean isLogin() {
		// return
		// !CollectionUtils.isEmpty(SecurityUtils.getSubject().getPrincipals());
		return SecurityUtils.getSubject().isAuthenticated();
	}

	protected boolean hasRole(String role) {
		return SecurityUtils.getSubject().hasRole(role);
	}

	protected boolean hasAnyRoles(String... roles) {
		Subject subject = SecurityUtils.getSubject();
		if (!subject.isAuthenticated()) {
			return false;
		}
		for (String role : roles) {
			if (subject.hasRole(role)) {
				return true;
			}
		}
		return false;
	}

	// @ExceptionHandler({ Exception.class })
	// public ModelAndView exception(Exception e) {
	// stackTraceError(logger, e);
	// return forword("/error/defaultError");
	// }

	protected void stackTraceError(Logger logger, Throwable e) {
		logger.error(null, e);
	}

	protected HttpClient getClient() {
		return new HttpClient();
	}

	public boolean judgeBlackByType(int type) throws Exception {
		ShiroUser shiroUser = currentUser();
		if (blackListSevice.judgeBlackByUserIdAndType(shiroUser.getUserId(), type)) {
			return true;
		}
		return false;
	}

	public boolean judgeUser() {
		ShiroUser shiroUser = currentUser();
		if (shiroUser.getUserName().equals("sadfsaag") || shiroUser.getUserName().equals("sadfsaag1") || shiroUser.getUserName().equals("sadfsaag2") || shiroUser.getUserName().equals("sadfsaag3")
				|| shiroUser.getUserName().equals("sadfsaag4")) {
			return true;
		}
		return false;
	}
 
	protected boolean isValidCsrfHeaderToken() {
		if (currentRequest().getHeader("__RequestVerificationToken") == null|| currentSession().getAttribute(CSRFTokenManager.CSRF_TOKEN_FOR_SESSION_ATTR_NAME) == null
              || !this.currentRequest().getHeader("__RequestVerificationToken").equals(currentSession().getAttribute(CSRFTokenManager.CSRF_TOKEN_FOR_SESSION_ATTR_NAME).toString())) {
            return false;
        }
        return true;
    }
}