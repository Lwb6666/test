package com.dxjr.utils;

import java.io.StringWriter;
import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.map.MappingJsonFactory;
import org.codehaus.jackson.type.TypeReference;

/**
 * JSON转换处理工具类
 * 
 * @created 2011-03-08 09:40:08
 */
public class JsonUtils {
	/**
	 * Object对象转换为JSON格式
	 * 
	 * 例如List对象、JavaBean对象、JavaBean对象数组、Map对象、List Map对象
	 * 
	 * @param object
	 *            传入的Object对象
	 * @return object的JSON格式字符串
	 */
	public static String toJson(Object object) {

		// Jackson方式转换为Json
		MappingJsonFactory f = new MappingJsonFactory();
		StringWriter sw = new StringWriter();

		try {
			JsonGenerator generator = f.createJsonGenerator(sw);
			generator.writeObject(object);
			generator.close();

		} catch (Exception e) {
			return "";
		}
		return sw.toString();

	}

	/**
	 * JSON转换为Object对象
	 * 
	 * @param jsonString
	 *            JSON字符串
	 * @param c
	 *            要转换成的类的类型
	 * @return Object对象
	 */
	public static <T> T jsonToObject(String jsonString, Class<T> c) {

		if (jsonString == null || "".equals(jsonString)) {
			return null;

		} else {

			// Jackson方式将Json转换为对象
			MappingJsonFactory f = new MappingJsonFactory();
			try {
				JsonParser parser = f.createJsonParser(jsonString);
				return parser.readValueAs(c);

			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
		}
	}

	/**
	 * 数组格式JSON串转换为ObjectList对象
	 * 
	 * @param <T>
	 * @param jsonString
	 *            JSON字符串
	 * @param tr
	 *            TypeReference,例如: new TypeReference< List<Album> >(){}
	 * @return ObjectList对象
	 */
	public static <T> T jsonToGenericObject(String jsonString,
			TypeReference<T> tr) {

		if (jsonString == null || "".equals(jsonString)) {
			return null;

		} else {

			// Jackson方式将Json转换为对象
			MappingJsonFactory f = new MappingJsonFactory();
			try {
				JsonParser parser = f.createJsonParser(jsonString);
				return parser.readValueAs(tr);

			} catch (Exception e) {
				return null;
			}
		}
	}
}
