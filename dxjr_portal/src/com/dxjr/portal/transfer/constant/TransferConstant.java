package com.dxjr.portal.transfer.constant;

public interface TransferConstant {
	/**
	 * 最小转让系数
	 * 
	 * @fields COEFMIN
	 */
	double COEFMIN = 0.99;
	/**
	 * 最大转让系数
	 * 
	 * @fields COEFMax
	 */
	double COEFMAX = 1.00;
	/**
	 * 利息管理率
	 * @fields MANAGERFREERATA
	 */
	double MANAGERFREERATA = 0.003;
	/**
	 * 新利息管理率
	 * @fields MANAGERFREERATANEW
	 */
	double MANAGERFREERATANEW = 0.005;
	/**
	 * 转让标题长度
	 * 
	 * @fields TransferNameLength
	 */
	int TRANSFERNAMELENGTH = 30;

	/**
	 * 转让内容长度
	 * 
	 * @fields TransferContentLength
	 */
	int TRANSFERCONTENTLENGTH = 100;

	/**
	 * 一年天数 TODO 如果代收表中的利息是按照360天那么下面的应该改成360天，如果代收表中的利息是按照365天计算那么下面的就是365天
	 * 
	 * @fields YEARDAYS
	 */
	int YEARDAYS = 360;

}
