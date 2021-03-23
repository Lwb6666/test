package com.dxjr.common.aop.processor;

import java.lang.reflect.Method;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.dxjr.common.aop.annotation.DisableDuplicateSubmission;
import com.dxjr.common.aop.annotation.GenerateToken;
import com.dxjr.common.aop.annotation.ValidateToken;
import com.dxjr.common.aop.aspect.ControllerAspectProcessor;
import com.dxjr.utils.AspectUtil;

/**
 * 防重复提交处理类
 * <p>
 * Description:这里写描述<br />
 * </p>
 * 
 * @title DuplicationSubmissionProcessor.java
 * @package com.dxjr.common.aop
 * @author zhaowei
 * @version 0.1 2015年11月19日
 */
public class DuplicateSubmissionProcessor implements ControllerAspectProcessor {

	@Autowired
	@Qualifier("defaultTokenAuthenticationProccessor")
	private IAspectAuthenticationProcessor defaultTokenAuthenticationProccessor;
	
	@Autowired
	@Qualifier("transientTokenAuthenticationProccessor")
	private IAspectAuthenticationProcessor transientTokenAuthenticationProccessor;

	@Override
	public Object doProcess(ProceedingJoinPoint pjp) throws Throwable {
		// 获取方法进行反射
		MethodSignature signature = (MethodSignature) pjp.getSignature();
		Method method = signature.getMethod();

		if (method.isAnnotationPresent(DisableDuplicateSubmission.class)) {
			// 禁止重复提交
			// 根据用户唯一标识进行加锁，防止同一用户同时请求多次
			String identify = AspectUtil.getUserBasedUniqueRequestId(pjp);

			synchronized (identify) {
				getTransientTokenAuthenticationProccessor().beforeProcess(pjp);
				Object proceed = pjp.proceed();
				getTransientTokenAuthenticationProccessor().afterProcess(pjp, proceed);
				return proceed;
			}
		} else if (method.isAnnotationPresent(GenerateToken.class) || method.isAnnotationPresent(ValidateToken.class)) {
			// 生成、验证token
			getDefaultTokenAuthenticationProccessor().beforeProcess(pjp);
			Object proceed = pjp.proceed();
			getDefaultTokenAuthenticationProccessor().afterProcess(pjp, proceed);
			return proceed;
		}
		return null;

	}

	public IAspectAuthenticationProcessor getDefaultTokenAuthenticationProccessor() {
		return defaultTokenAuthenticationProccessor;
	}

	public void setDefaultTokenAuthenticationProccessor(IAspectAuthenticationProcessor defaultTokenAuthenticationProccessor) {
		this.defaultTokenAuthenticationProccessor = defaultTokenAuthenticationProccessor;
	}

	public IAspectAuthenticationProcessor getTransientTokenAuthenticationProccessor() {
		return transientTokenAuthenticationProccessor;
	}

	public void setTransientTokenAuthenticationProccessor(IAspectAuthenticationProcessor transientTokenAuthenticationProccessor) {
		this.transientTokenAuthenticationProccessor = transientTokenAuthenticationProccessor;
	}

}
