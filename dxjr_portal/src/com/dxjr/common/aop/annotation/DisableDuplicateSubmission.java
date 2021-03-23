package com.dxjr.common.aop.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 防重复提交注解
 * <p>
 * Description:这里写描述<br />
 * </p>
 * @title DisableDuplicateSubmission.java
 * @package com.dxjr.common.aop.processor 
 * @author zhaowei
 * @version 0.1 2015年11月19日
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface DisableDuplicateSubmission {
	
	public long expiredTime() default 3000L;
	
}
