package com.dxjr.utils;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.ArrayUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.springframework.util.Assert;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * 切面工具
 * <p>
 * Description:这里写描述<br />
 * </p>
 * 
 * @title AspectUtil.java
 * @package com.dxjr.utils
 * @author Administrator
 * @version 0.1 2016年1月12日
 */
public class AspectUtil {

	/**
	 * 获取用户唯一请求ID
	 * <p>
	 * Description:这里写描述<br />
	 * </p>
	 * 
	 * @author Administrator
	 * @version 0.1 2016年1月12日
	 * @param point
	 * @return String
	 */
	public static String getUserBasedUniqueRequestId(ProceedingJoinPoint point) {
		HttpServletRequest request = getHttpServletRequest();
		Assert.notNull(request, "HttpServletRequest object cannot null");
		String identify = point.getSignature().toString();
		// 表示某个用户方法访问某个Controller+Method的唯一标识
		return identify += request.getSession().getId();
	}

	/**
	 * 获取唯一请求ID
	 * <p>
	 * Description:这里写描述<br />
	 * </p>
	 * 
	 * @author zhaowei
	 * @version 0.1 2016年1月19日
	 * @param point
	 * @return String
	 */
	public static String getUniqueRequestId(ProceedingJoinPoint point) {
		HttpServletRequest request = getHttpServletRequest();
		Assert.notNull(request, "HttpServletRequest object cannot null");
		String identify = point.getSignature().toString();
		// 表示访问某个Controller+Method的唯一标识
		return identify;
	}

	/**
	 * 获取指定对象
	 * <p>
	 * Description:这里写描述<br />
	 * </p>
	 * 
	 * @author Administrator
	 * @version 0.1 2016年1月12日
	 * @param point
	 * @return HttpServletRequest
	 */
	public static Object getObject(ProceedingJoinPoint point, Class<?> cls) {
		Object[] args = point.getArgs();
		if (ArrayUtils.isNotEmpty(args)) {
			for (Object arg : args) {
				if (cls.isInstance(arg)) {
					return arg;
				}
			}
		}
		
		if (cls == HttpServletRequest.class) {
			return getHttpServletRequest();
		}
		return null;
	}

	public static HttpServletRequest getHttpServletRequest() {
		return ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
	}

}
