package com.dxjr.common.aop.processor.config;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

import com.dxjr.common.aop.aspect.ControllerAspect;
import com.dxjr.common.aop.aspect.ControllerAspectProcessor;
import com.dxjr.common.aop.processor.DefaultTokenAuthenticationProccessor;
import com.dxjr.common.aop.processor.DuplicateSubmissionProcessor;
import com.dxjr.common.aop.processor.IAspectAuthenticationProcessor;
import com.dxjr.common.aop.processor.TransientTokenAuthenticationProccessor;
import com.google.common.collect.Lists;

/**
 * 配置Bean
 * <p>
 * Description:这里写描述<br />
 * </p>
 * 
 * @title ProcessorConfiguration.java
 * @package com.dxjr.common.aop.processor.config
 * @author zhaowei
 * @version 0.1 2016年1月18日
 */
@Configuration
public class ProcessorConfiguration {

	@Autowired
	private ControllerAspectProcessor duplicateSubmissionProcessor;
	
	/**
	 * Token认证处理类
	 * <p>
	 * Description:页面初始化前会生成一个token存储到cookie中，提交后对token进行验证<br />
	 * </p>
	 * @title DefaultTokenAuthenticationProccessor.java
	 * @package com.dxjr.common.aop.processor 
	 * @author zhaowei
	 * @version 0.1 2016年1月18日
	 */
	@Bean
	@Qualifier("defaultTokenAuthenticationProccessor")
	public IAspectAuthenticationProcessor defaultTokenAuthenticationProccessor() {
		return new DefaultTokenAuthenticationProccessor();
	}
	
	/**
	 * Token认证处理类
	 * <p>
	 * Description:在token存活有效期类内不允许重复提交<br />
	 * </p>
	 * @title TokenBasedProccessor.java
	 * @package com.dxjr.common.aop.processor 
	 * @author zhaowei
	 * @version 0.1 2015年11月19日
	 */
	@Bean
	@Qualifier("transientTokenAuthenticationProccessor")
	public IAspectAuthenticationProcessor transientTokenAuthenticationProccessor() {
		return new TransientTokenAuthenticationProccessor();
	}

	@Bean
	@DependsOn({"duplicateSubmissionProcessor"})
	public List<ControllerAspectProcessor> processors() {
		return Lists.newArrayList(duplicateSubmissionProcessor);
	}

	@Bean
	public ControllerAspect controllerAspect() {
		return new ControllerAspect();
	}

	/**
	 * 防重复提交方案处理类
	 * <p>
	 * Description:这里写描述<br />
	 * </p>
	 * @author zhaowei
	 * @version 0.1 2016年1月18日
	 * @return
	 * ControllerAspectProcessor
	 */
	@Bean
	@DependsOn({"defaultTokenAuthenticationProccessor", "transientTokenAuthenticationProccessor"})
	public ControllerAspectProcessor duplicateSubmissionProcessor() {
		DuplicateSubmissionProcessor p = new DuplicateSubmissionProcessor();
		return p;
	}

}
