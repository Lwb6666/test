package com.dxjr.common.aop.processor;

import org.aspectj.lang.ProceedingJoinPoint;

import com.dxjr.common.aop.exception.AuthenticationException;
import com.dxjr.common.aop.struct.AuthenticationType;

/**
 * 
 * <p>
 * Description:这里写描述<br />
 * </p>
 * 
 * @title AbstractAuthenticationProccessor.java
 * @package com.dxjr.common.aop.processor
 * @author zhaowei
 * @version 0.1 2015年11月19日
 */
public abstract class AbstractAuthenticationProccessor implements IAspectAuthenticationProcessor {
	
	private Object lock = new Object();

	/**
	 * 抽象的认证方法，由子类实现
	 * <p>
	 * Description:这里写描述<br />
	 * </p>
	 * 
	 * @author Administrator
	 * @version 0.1 2016年1月12日
	 * @param identify
	 * @param point
	 * @return
	 * @throws AuthenticationException
	 *             boolean
	 */
	abstract protected boolean doAuthentication(ProceedingJoinPoint point) throws AuthenticationException;

	/**
	 * 认证成功处理方法
	 * <p>
	 * Description:这里写描述<br />
	 * </p>
	 * 
	 * @author Administrator
	 * @version 0.1 2016年1月12日
	 * @param identify
	 * @param args
	 * @throws AuthenticationException
	 *             void
	 */
	abstract protected void onAuthenticationSuccess(ProceedingJoinPoint point) throws AuthenticationException;

	/**
	 * 认证成功并且业务逻辑处理完毕
	 * <p>
	 * Description:这里写描述<br />
	 * </p>
	 * 
	 * @author Administrator
	 * @version 0.1 2016年1月12日
	 * @param identify
	 * @param mv
	 * @param proceed
	 * @throws AuthenticationException
	 *             void
	 */
	abstract protected void afterAuthenticationReturning(ProceedingJoinPoint point, Object proceed)
			throws AuthenticationException;

	@Override
	public void beforeProcess(ProceedingJoinPoint point) throws AuthenticationException {
		synchronized(lock) {
			// 进行认证，通过则继续处理
			if (!doAuthentication(point)) {
				throw new AuthenticationException(AuthenticationType.AUTHENTICATION_FAILED, "authentication failed");
			}
		}
		
		// 成功认证处理
		this.onAuthenticationSuccess(point);
	}

	@Override
	public void afterProcess(ProceedingJoinPoint point, Object proceed) throws AuthenticationException {
		this.afterAuthenticationReturning(point, proceed);
	}

}
