/**   
 * <p>
 * Description:这里写描述<br />
 * </p>
 * @title LottVo.java
 * @package com.dxjr.portal.lottery.vo 
 * @author HuangJun
 * @version 0.1 2015年4月10日
 */
package com.dxjr.portal.lottery.vo;

/**
 * <p>
 * Description:这里写描述<br />
 * </p>
 * 
 * @title LottVo.java
 * @package com.dxjr.portal.lottery.vo
 * @author HuangJun
 * @version 0.1 2015年4月10日
 */

public class LottVo {

	private String sFlag;
	/**
	 * 起始角度
	 */
	private int sAngel;
	/**
	 * 起始角度 + 默认旋转的圈数
	 */
	private int eAngel;
	/**
	 * 转动时间
	 */
	private int dtnTime;
	private String message;

	public LottVo() {
	}

	/**
	 * @return sFlag : return the property sFlag.
	 */
	public String getsFlag() {
		return sFlag;
	}

	/**
	 * @param sFlag
	 *            : set the property sFlag.
	 */
	public void setsFlag(String sFlag) {
		this.sFlag = sFlag;
	}

	/**
	 * @return sAngel : return the property sAngel.
	 */
	public int getsAngel() {
		return sAngel;
	}

	/**
	 * @param sAngel
	 *            : set the property sAngel.
	 */
	public void setsAngel(int sAngel) {
		this.sAngel = sAngel;
	}

	/**
	 * @return eAngel : return the property eAngel.
	 */
	public int geteAngel() {
		return eAngel;
	}

	/**
	 * @param eAngel
	 *            : set the property eAngel.
	 */
	public void seteAngel(int eAngel) {
		this.eAngel = eAngel;
	}

	/**
	 * @return dtnTime : return the property dtnTime.
	 */
	public int getDtnTime() {
		return dtnTime;
	}

	/**
	 * @param dtnTime
	 *            : set the property dtnTime.
	 */
	public void setDtnTime(int dtnTime) {
		this.dtnTime = dtnTime;
	}

	/**
	 * @return message : return the property message.
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * @param message
	 *            : set the property message.
	 */
	public void setMessage(String message) {
		this.message = message;
	}

}
