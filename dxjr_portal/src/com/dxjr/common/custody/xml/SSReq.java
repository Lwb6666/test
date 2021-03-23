/**   
 * <p>
 * Description:这里写描述<br />
 * </p>
 * @title SSReq.java
 * @package com.dxjr.common.custody.xml 
 * @author tanghaitao
 * @version 0.1 2016年5月21日
 */
package com.dxjr.common.custody.xml;

import com.thoughtworks.xstream.annotations.XStreamAsAttribute;

/**   
 * <p>
 * Description:发送短信报文<br />
 * </p>
 * @title SSReq.java
 * @package com.dxjr.common.custody.xml 
 * @author tanghaitao
 * @version 0.1 2016年5月21日
 */

public class SSReq extends BasePIReq{

	@XStreamAsAttribute  
	private String id = "SSReq"; 
	
	private String Mobile;//手机号码
	
	/**
	 *  1:绑定浙商e卡
	    2:资金冻结
	    4:充值
	    5:提现
	    6:转让
		7:自动投标设置
		8:解绑
		9:修改手机号码
	 */
	private String SmsType;//短信类型
	
	
	private String extension;//消息扩展


	public String getId() {
		return id;
	}


	public void setId(String id) {
		this.id = id;
	}


	public String getMobile() {
		return Mobile;
	}


	public void setMobile(String mobile) {
		Mobile = mobile;
	}


	public String getSmsType() {
		return SmsType;
	}


	public void setSmsType(String smsType) {
		SmsType = smsType;
	}


	public String getExtension() {
		return extension;
	}


	public void setExtension(String extension) {
		this.extension = extension;
	}
	
	
	
}
