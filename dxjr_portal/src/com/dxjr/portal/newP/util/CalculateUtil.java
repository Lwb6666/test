/**   
 * <p>
 * Description:这里写描述<br />
 * </p>
 * @title CalculateUtil.java
 * @package com.dxjr.portal.newP.util 
 * @author HuangJun
 * @version 0.1 2015年4月9日
 */
package com.dxjr.portal.newP.util;

import java.math.BigDecimal;

/**   
 * <p>
 * Description:这里写描述<br />
 * </p>
 * @title CalculateUtil.java
 * @package com.dxjr.portal.newP.util 
 * @author HuangJun
 * @version 0.1 2015年4月9日
 */

public class CalculateUtil {
	
	
	/**  
	* 提供（相对）精确的除法运算。当发生除不尽的情况时，由scale参数指  
	* 定精度，以后的数字四舍五入。  
	* @param v1 被除数  
	* @param v2 除数  
	* @param scale 表示表示需要精确到小数点以后几位。  
	* @return 两个参数的商  
	*/  
	public static double calculate_chufa(double v1,double v2,int scale){   
	if(scale<0){   
	throw new IllegalArgumentException(   
	"The scale must be a positive integer or zero");   
	}   
	BigDecimal b1 = new BigDecimal(Double.toString(v1));   
	BigDecimal b2 = new BigDecimal(Double.toString(v2));   
	return b1.divide(b2,scale,BigDecimal.ROUND_HALF_UP).doubleValue();   
	} 
	
	
	
	public static boolean CompareBigDecimal(BigDecimal n ,BigDecimal m)
	{
		
		boolean isT = false;
		m.setScale(2,BigDecimal.ROUND_HALF_DOWN);
		n.setScale(2,BigDecimal.ROUND_HALF_DOWN);
		isT  = m.equals(n) ;
		return isT;
		
	}
	
	

	

}
