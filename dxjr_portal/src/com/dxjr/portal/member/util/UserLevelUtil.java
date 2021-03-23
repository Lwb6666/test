package com.dxjr.portal.member.util;

import java.math.BigDecimal;

import com.dxjr.portal.statics.BusinessConstants;

/**
 * <p>
 * Description:用户等级工具类<br />
 * </p>
 * 
 * @title UserLevelUtil.java
 * @package com.dxjr.portal.member.util
 * @author justin.xu
 * @version 0.1 2014年5月14日
 */
public class UserLevelUtil {
	/**
	 * <p>
	 * Description:根据金额找出用户等级<br />
	 * </p>
	 * 
	 * @author justin.xu
	 * @version 0.1 2014年5月14日
	 * @param money
	 * @return String
	 */
	public static String queryUserLevelByMoney(BigDecimal money) {
		String result = BusinessConstants.MEMBER_LEVEL_NULL;// 初始等级
		if (null == money) {
			return result;
		}
		// 用户区间集合
		String[] MEMBER_LEVEL_MONEY_RANGE = BusinessConstants.MEMBER_LEVEL_MONEY_RANGE;
		// 用户等级集合
		String[] MEMBER_LEVEL_RANGE = BusinessConstants.MEMBER_LEVEL_RANGE;
		Integer sequence = 0;
		for (int i = 0; i < MEMBER_LEVEL_MONEY_RANGE.length; i++) {
			String[] beginEndStr = MEMBER_LEVEL_MONEY_RANGE[i].split("-");
			if (beginEndStr.length == 2) {
				// 区间等级比如0-200,实际为0-(200-1)
				if (money.compareTo(new BigDecimal(beginEndStr[0])) >= 0
						&& money.compareTo(new BigDecimal(beginEndStr[1])
								.subtract(new BigDecimal(1))) <= 0) {
					sequence = i;
					break;
				}
			} else if (money.compareTo(new BigDecimal(beginEndStr[0])) >= 0) {
				sequence = i;
				break;
			}
		}
		result = MEMBER_LEVEL_RANGE[sequence];
		return result;
	}
}
