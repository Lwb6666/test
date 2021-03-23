/**   
 * <p>
 * Description:这里写描述<br />
 * </p>
 * @title ABSReq.java
 * @package com.dxjr.common.custody.xml 
 * @author tanghaitao
 * @version 0.1 2016年6月24日
 */
package com.dxjr.common.custody.xml;

import com.thoughtworks.xstream.annotations.XStreamAsAttribute;

/**   
 * <p>
 * Description:自动投标设置报文<br />
 * </p>
 * @title ABSReq.java
 * @package com.dxjr.common.custody.xml 
 * @author tanghaitao
 * @version 0.1 2016年6月24日
 */

public class ABSReq extends BasePIReq{

	@XStreamAsAttribute  
	private String id = "ABSReq"; 
	
	private String bindSerialNo;//绑定协议号
	
	private String mobileCode;//短信验证码
	
	private String flag;//开关标识 0关闭 1开启
	
	private String extension="";//消息扩展

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getBindSerialNo() {
		return bindSerialNo;
	}

	public void setBindSerialNo(String bindSerialNo) {
		this.bindSerialNo = bindSerialNo;
	}

	public String getMobileCode() {
		return mobileCode;
	}

	public void setMobileCode(String mobileCode) {
		this.mobileCode = mobileCode;
	}

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	public String getExtension() {
		return extension;
	}

	public void setExtension(String extension) {
		this.extension = extension;
	}
	
	
}
