package com.dxjr.common.aop.aspect;

import org.aspectj.lang.ProceedingJoinPoint;

/**
 * 
 * <p>
 * Description:这里写描述<br />
 * </p>
 * @title ControllerAspectProcessor.java
 * @package com.dxjr.common.aop.aspect 
 * @author zhaowei
 * @version 0.1 2016年1月18日
 */
public interface ControllerAspectProcessor {

	public Object doProcess(ProceedingJoinPoint pjp) throws Throwable;
}
