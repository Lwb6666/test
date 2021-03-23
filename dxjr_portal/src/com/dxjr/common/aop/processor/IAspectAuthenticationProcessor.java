package com.dxjr.common.aop.processor;

import org.aspectj.lang.ProceedingJoinPoint;

import com.dxjr.common.aop.exception.AuthenticationException;

/**
 * 
 * <p>
 * Description:这里写描述<br />
 * </p>
 * @title IAsseptProcessor.java
 * @package com.dxjr.common.aop 
 * @author zhaowei
 * @version 0.1 2015年11月19日
 */
public interface IAspectAuthenticationProcessor {
	
	/**
	 * 
	 * <p>
	 * Description:这里写描述<br />
	 * </p>
	 * @author zhaowei
	 * @version 0.1 2015年11月19日
	 * @param point
	 * void
	 */
	public void beforeProcess(ProceedingJoinPoint point) throws AuthenticationException;
	
	/**
	 * 
	 * <p>
	 * Description:这里写描述<br />
	 * </p>
	 * @author zhaowei
	 * @version 0.1 2015年11月19日
	 * @param point
	 * @param proceed
	 * @return
	 * Object
	 * @throws AuthenticationException 
	 */
	public void afterProcess(ProceedingJoinPoint point, Object proceed) throws AuthenticationException;
}
