package com.dxjr.common.aop.processor;

import java.lang.reflect.Method;
import java.util.UUID;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.dxjr.common.aop.annotation.DisableDuplicateSubmission;
import com.dxjr.common.aop.exception.AuthenticationException;
import com.dxjr.common.cache.CacheProxy;
import com.dxjr.common.cache.CacheProxyFactory;
import com.dxjr.utils.AspectUtil;

/**
 * 默认的认证处理方法，主要为了保证第一次请求未完成时不允许第二次请求
 * <p>
 * Description:此方法无法防止二次请求<br />
 * Token认证方式请见 {@link com.dxjr.common.aop.processor.TransientTokenAuthenticationProccessor}
 * </p>
 * @title TokenBasedProccessor.java
 * @package com.dxjr.common.aop.processor 
 * @author zhaowei
 * @version 0.1 2015年11月19日
 */
public class DefaultAuthenticationProccessor extends AbstractAuthenticationProccessor {
	
	@Autowired
	@Qualifier("cacheProxyFactory")
	private CacheProxyFactory cacheProxyFactory;

	public CacheProxyFactory getCacheProxyFactory() {
		return cacheProxyFactory;
	}

	@Override
	protected void onAuthenticationSuccess(ProceedingJoinPoint point) throws AuthenticationException {
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
		CacheProxy cache = getCacheProxy();
		String identify = AspectUtil.getUserBasedUniqueRequestId(point);
		
		// 任务执行结束，删除缓存中的数据
		if (cache.isExists(identify)) {
			cache.remove(identify);
		}
	}

	@Override
	protected boolean doAuthentication(ProceedingJoinPoint point) throws AuthenticationException {
		CacheProxy cache = getCacheProxy();
		String identify = AspectUtil.getUserBasedUniqueRequestId(point);
		
		// 验证缓存中是否存在请求标识
		if (cache.isExists(identify)) {
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
