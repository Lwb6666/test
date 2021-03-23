package com.dxjr.portal.util;

import java.io.ByteArrayInputStream;
import java.io.StringWriter;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import org.apache.log4j.Logger;

public class XmlUtils {
	private static final Logger logger = Logger.getLogger(XmlUtils.class);

	private static final String utf8 = "UTF-8";

	@SuppressWarnings("unchecked")
	public static <T> T xml2Bean(String xml, Class<T> clazz) {
		ByteArrayInputStream byteArrayInputStream = null;
		try {
			JAXBContext jaxbContext = JAXBContext.newInstance(clazz);
			Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
			byteArrayInputStream = new ByteArrayInputStream(xml.getBytes(utf8));
			return (T) unmarshaller.unmarshal(byteArrayInputStream);
		} catch (Exception e) {
			logger.error("unconverted xml[" + xml + "]", e);
		} finally {
			if (byteArrayInputStream != null) {
				try {
					byteArrayInputStream.close();
				} catch (Exception e) {
				}
			}
		}

		return null;
	}

	public static String bean2XmlNoHead(Object bean) {
		return bean2Xml(bean, true, false);
	}

	public static String bean2Xml(Object bean) {
		return bean2Xml(bean, false, false);
	}

	private static String bean2Xml(Object bean, boolean noHead, boolean isFormat) {
		if (bean == null) {
			return null;
		}
		try {
			JAXBContext jaxbContext = JAXBContext.newInstance(bean.getClass());
			Marshaller marshaller = jaxbContext.createMarshaller();
			marshaller.setProperty(Marshaller.JAXB_ENCODING, utf8);
			// 是否格式化生成xml
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, isFormat);
			// 是否省略xml头信息<?xml version="1.0" encoding="utf-8" standalone="yes"?>
			marshaller.setProperty(Marshaller.JAXB_FRAGMENT, noHead);

			StringWriter writer = new StringWriter();
			marshaller.marshal(bean, writer);
			return writer.toString();
		} catch (Exception e) {
			logger.error("unconverted bean[" + bean + "]", e);
		}
		return null;
	}
}