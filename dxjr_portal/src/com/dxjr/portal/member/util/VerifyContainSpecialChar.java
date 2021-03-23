package com.dxjr.portal.member.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;

import com.dxjr.portal.statics.BusinessConstants;
/**
 * <p>
 * Description:正则表达式工具类<br />
 * </p>
 * 
 * @title VerifyContainSpecialChar.java
 * @package com.dxjr.portal.member.util
 * @author hujianpan
 * @version 0.1 2014年9月18日
 */
public class VerifyContainSpecialChar {
	/**
	 * 
	 * <p>
	 * Description:判断是否包含特殊字符<br />
	 * </p>
	 * @author 胡建盼
	 * @version 0.1 2014年9月18日
	 * @param inputStr
	 * @return
	 * Boolean
	 */
	 public static Boolean isContainSpecialChars (String inputStr){
		 //为空直接返回false
		 if(!StringUtils.isEmpty(inputStr)){
			 //不包含特殊字符也返回true
			  Pattern p=Pattern.compile(BusinessConstants.PETTERN_REGEX_NO_SPECIALCHARS);
			  Matcher m=p.matcher(inputStr);
			  Boolean isContain = m.matches();
			  return isContain;
		 }else {
			 return false;
		 }

	 }

}
