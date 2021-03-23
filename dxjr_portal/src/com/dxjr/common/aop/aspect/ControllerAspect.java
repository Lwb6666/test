package com.dxjr.common.aop.aspect;

import java.util.List;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Controller切面
 * <p>
 * Description:这里写描述<br />
 * </p>
 * 
 * @title DuplicateSubmissionAspect.java
 * @package com.dxjr.aop.aspect
 * @author zhaowei
 * @version 0.1 2015年11月18日
 */
@Aspect
public class ControllerAspect {

	private List<ControllerAspectProcessor> processors;

	@Pointcut("execution(public * com.dxjr.portal.*.controller..*Controller.*(..))")
	public void controllerPointcut() {
	}

	/**
	 * 环绕方法
	 * <p>
	 * Description:这里写描述<br />
	 * </p>
	 * 
	 * @author zhaowei
	 * @version 0.1 2015年11月19日
	 * @param pjp
	 * @return
	 * @throws Throwable
	 *             Object
	 */
	@Around("controllerPointcut()")
	public Object around(ProceedingJoinPoint pjp) throws Throwable {
		for (ControllerAspectProcessor p : getProcessors()) {
			pjp.getTarget();
			if (p != null) {
				Object o = p.doProcess(pjp);

				if (o != null) {
					return o;
				}
			}
		}

		return pjp.proceed();
	}

	public List<ControllerAspectProcessor> getProcessors() {
		return processors;
	}

	@Autowired
	public void setProcessors(List<ControllerAspectProcessor> processors) {
		this.processors = processors;
	}

}
