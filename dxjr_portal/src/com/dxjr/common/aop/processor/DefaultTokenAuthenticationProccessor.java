package com.dxjr.common.aop.processor;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.WebUtils;

import com.dxjr.common.aop.annotation.ValidateToken;
import com.dxjr.common.aop.exception.AuthenticationException;
import com.dxjr.common.aop.struct.AuthenticationType;
import com.dxjr.common.cache.CacheProxy;
import com.dxjr.common.cache.CacheProxyFactory;
import com.dxjr.portal.base.MessageBox;
import com.dxjr.utils.AspectUtil;

/**
 * Token认证处理类
 * <p>
 * Description:页面初始化前会生成一个token存储到cookie中，提交后对token进行验证<br />
 * </p>
 * 
 * @title DefaultTokenAuthenticationProccessor.java
 * @package com.dxjr.common.aop.processor
 * @author zhaowei
 * @version 0.1 2016年1月18日
 */
public class DefaultTokenAuthenticationProccessor extends AbstractAuthenticationProccessor {

	private final static String AUTHENTICATION_TOKEN_KEY = "revision";
	private final static String AUTHENTICATION_TOKEN_HEADER_KEY = "__RequestRevision";
	
	/**
	 * 缓存失效时间
	 */
	private long cacheExpiredTime = 1800000L;

	@Autowired
	@Qualifier("cacheProxyFactory")
	private CacheProxyFactory cacheProxyFactory;

	protected CacheProxy getCacheProxy() throws AuthenticationException {
		if (cacheProxyFactory != null) {
			return cacheProxyFactory.getCacheProxy();
		}

		throw new AuthenticationException(AuthenticationType.AUTHENTICATION_CACHE_ERROR, "cahe proxy cannot null");
	}

	@Override
	protected boolean doAuthentication(ProceedingJoinPoint point) throws AuthenticationException {
		// 获取方法进行反射
		MethodSignature signature = (MethodSignature) point.getSignature();
		Method method = signature.getMethod();

		// 进行token验证
		if (method.isAnnotationPresent(ValidateToken.class)) {
			HttpServletRequest request = (HttpServletRequest) AspectUtil.getObject(point, HttpServletRequest.class);
			String token = WebUtils.findParameterValue(request, AUTHENTICATION_TOKEN_KEY);
			token = token == null ? request.getHeader(AUTHENTICATION_TOKEN_HEADER_KEY) : token;
			
			if (!StringUtils.hasLength(token)) {
				return false;
			}

			CacheProxy cache = getCacheProxy();
			if (!cache.isExists(token)) {
				return false;
			}

			// 从缓存中删除
			cache.remove(token);
		}

		return true;
	}

	@Override
	protected void onAuthenticationSuccess(ProceedingJoinPoint point) throws AuthenticationException {
		
	}

	@Override
	protected void afterAuthenticationReturning(ProceedingJoinPoint point, Object proceed)
			throws AuthenticationException {
		String token = UUID.randomUUID().toString().replaceAll("-", "");

		ModelAndView mv = proceed instanceof ModelAndView ? (ModelAndView) proceed : null;
		
		HttpServletRequest request = AspectUtil.getHttpServletRequest();

		if (mv != null) {
			// 返回值如果为ModelAndView，则加入新的token
			mv.addObject(AUTHENTICATION_TOKEN_KEY, token);
		} else if (proceed instanceof Map) {
			// 返回值如果为Map，则加入新的token
			@SuppressWarnings("unchecked")
			Map<String, Object> map = (Map<String, Object>) proceed;
			map.put(AUTHENTICATION_TOKEN_KEY, token);
		} else if (proceed instanceof MessageBox) {
			MessageBox msgBox = (MessageBox) proceed;
			msgBox.setRevision(token);
		} else {
			request.setAttribute(AUTHENTICATION_TOKEN_KEY, token);
		}

		CacheProxy cache = getCacheProxy();
		cache.put(token, token, cacheExpiredTime);
	}

	public long getCacheExpiredTime() {
		return cacheExpiredTime;
	}

	public void setCacheExpiredTime(long cacheExpiredTime) {
		this.cacheExpiredTime = cacheExpiredTime;
	}

}
