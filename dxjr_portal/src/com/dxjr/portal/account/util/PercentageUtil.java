package com.dxjr.portal.account.util;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

import com.dxjr.portal.account.vo.Percentage;

public class PercentageUtil {
	public static Percentage getPercentage(BigDecimal total, BigDecimal one, BigDecimal two, BigDecimal three, BigDecimal four) {
		Percentage percentage = new Percentage();

		List<BigDecimal> list = new ArrayList<BigDecimal>();
		list.add(total);list.add(one);list.add(two);list.add(three);list.add(four);
		List<BigDecimal> list2 = new ArrayList<BigDecimal>(list.size());
		list2.add(new BigDecimal(100));
		BigDecimal left = new BigDecimal(100);
		for (int i = 1; i < list.size(); i++) {
			if (null!=list.get(i)&&list.get(i).compareTo(BigDecimal.ZERO)==0) {
				list2.add(BigDecimal.ZERO);
			} else {
				BigDecimal bi = list.get(i).divide(total, 4, RoundingMode.FLOOR).multiply(new BigDecimal(100)).setScale(2, RoundingMode.FLOOR);
				list2.add(bi);
				left = left.subtract(bi);
			}
		}
		for (int i = 1; i < list2.size(); i++) {
			if (null!=left&&left.compareTo(BigDecimal.ZERO)!=0 && null!=list2.get(i)&&list2.get(i).compareTo(BigDecimal.ZERO)!=0) {
				list2.set(i, list2.get(i).add(left));
				left = BigDecimal.ZERO;
			}
		}
		percentage.setFixreax(list2.get(1));
		percentage.setTenderTotal(list2.get(2));
		percentage.setCurrTotal(list2.get(3));
		percentage.setUseTotal(list2.get(4));
		return percentage;
	}
  }

