package com.dxjr.common.aop.processor;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.servlet.ModelAndView;

import com.dxjr.common.aop.annotation.DisableDuplicateSubmission;
import com.dxjr.common.aop.exception.AuthenticationException;
import com.dxjr.common.aop.struct.AuthenticationType;
import com.dxjr.common.cache.CacheProxy;
import com.dxjr.common.cache.CacheProxyFactory;
import com.dxjr.portal.base.MessageBox;
import com.dxjr.utils.AspectUtil;

/**
 * Token认证处理类
 * <p>
 * Description:在Token存活有效期类内不允许重复提交<br />
 * </p>
 * 
 * @title TokenBasedProccessor.java
 * @package com.dxjr.common.aop.processor
 * @author zhaowei
 * @version 0.1 2015年11月19日
 */
public class TransientTokenAuthenticationProccessor extends AbstractAuthenticationProccessor {

	private final static String AUTHENTICATION_TOKEN_KEY = "version";

	@Autowired
	@Qualifier("cacheProxyFactory")
	private CacheProxyFactory cacheProxyFactory;

	public CacheProxyFactory getCacheProxyFactory() {
		return cacheProxyFactory;
	}

	@Override
	protected void onAuthenticationSuccess(ProceedingJoinPoint point) {
		CacheProxy cache = getCacheProxy();
		String identify = AspectUtil.getUserBasedUniqueRequestId(point);

		MethodSignature signature = (MethodSignature) point.getSignature();
		Method method = signature.getMethod();

		// 判断方法中是否标记禁止重复提交
		if (method.isAnnotationPresent(DisableDuplicateSubmission.class)) {
			DisableDuplicateSubmission annotation = method.getAnnotation(DisableDuplicateSubmission.class);
			// 存入标识，方便下次认证验证
			cache.put(identify, UUID.randomUUID().toString(), annotation.expiredTime());
		} else {
			// 存入标识，方便下次认证验证
			cache.put(identify, UUID.randomUUID().toString());
		}
	}

	@Override
	protected void afterAuthenticationReturning(ProceedingJoinPoint point, Object proceed) throws AuthenticationException {
		String identify = AspectUtil.getUserBasedUniqueRequestId(point);
		CacheProxy cache = getCacheProxy();
		String token = cache.get(identify);
		ModelAndView mv = proceed instanceof ModelAndView ? (ModelAndView) proceed : null;

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
			// 暂时不支持
			throw new AuthenticationException(AuthenticationType.AUTHENTICATION_NOT_SUPPORTED,
					"authentication not supported");
		}
	}

	@Override
	protected boolean doAuthentication(ProceedingJoinPoint point) throws AuthenticationException {
		String identify = AspectUtil.getUserBasedUniqueRequestId(point);
		HttpServletRequest request = AspectUtil.getHttpServletRequest();

		// 认证不支持，建议Controller方法中加入HttpServletRequest对象
		if (request == null) {
			throw new AuthenticationException(AuthenticationType.AUTHENTICATION_NOT_SUPPORTED,
					"authentication not supported");
		}
		String passedToken = request.getParameter(AUTHENTICATION_TOKEN_KEY);
		CacheProxy cache = getCacheProxy();

		// 判断token是否正确，不正确则验证失败
		if (cache.isExists(identify) && passedToken != null) {
			return cache.get(identify).equals(passedToken);
		} else if (cache.isExists(identify) && passedToken == null) {
			// 如果非第一次请求，没有带token则验证失败
			return false;
		}
		return true;
	}

	protected CacheProxy getCacheProxy() {
		if (cacheProxyFactory != null) {
			return cacheProxyFactory.getCacheProxy();
		}
		return null;
	}

}
