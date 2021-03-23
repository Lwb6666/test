package com.dxjr.portal.freemarker.service.impl;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import com.dxjr.portal.freemarker.service.BaseFreemarkerService;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

/**
 * <p>
 * Description: 模板文件 操作BLL <br />
 * </p>
 * 
 * @title BaseFreemarkerServiceImpl.java
 * @package com.dxjr.portal.freemarker.service.impl
 * @author HuangJun
 * @version 0.1 2015年6月9日
 */
@Service
public class BaseFreemarkerServiceImpl implements BaseFreemarkerService {
	@Autowired
	private FreeMarkerConfigurer freeMarkerConfigurer;

	/**
	 * 得到协议内容 (non-Javadoc)
	 * 
	 * @see com.dxjr.portal.freemarker.service.BaseFreemarkerService#generateEmailContentByFreeMarker(java.lang.String,
	 *      java.util.Map)
	 */
	@Override
	public String generateEmailContentByFreeMarker(String templateName, Map<String, Object> map) throws Exception {
		Configuration configuration = freeMarkerConfigurer.getConfiguration();
		Template t = configuration.getTemplate(templateName);
		return FreeMarkerTemplateUtils.processTemplateIntoString(t, map);
	}

	/**
	 * 通过模版生成html文件 (non-Javadoc)
	 * 
	 * @see com.dxjr.base.freemarker.service.BaseFreemarkerService#createHtml(java.lang.String,
	 *      java.util.Map, java.lang.String)
	 */
	@Override
	public void createHtml(String templateName, Map<String, Object> map, String targetPath) throws Exception {
		// 创建存放临时文件路径
		File file = new File(targetPath);
		if (file.exists()) {
		} else {
			file.getParentFile().mkdir();
		}
		
		Configuration configuration = freeMarkerConfigurer.getConfiguration();
		configuration.setDefaultEncoding("UTF-8");
		Template t = null;
		try {
			t = configuration.getTemplate(templateName, "UTF-8");
		} catch (IOException e2) {
			e2.printStackTrace();
		}
		FileOutputStream fos = null;
		Writer writer = null;
		try {
			// 写文件
			fos = new FileOutputStream(new File(targetPath));
			writer = new BufferedWriter(new OutputStreamWriter(fos, "UTF-8"));
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}
		try {
			// 把map内容输出到指定路径html
			t.process(map, writer);
		} catch (TemplateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		finally{
			writer.close();
			fos.flush();
			fos.close();
			fos = null;
		}
		
	}

}
