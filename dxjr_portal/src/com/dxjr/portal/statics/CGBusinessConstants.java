/**   
 * <p>
 * Description:这里写描述<br />
 * </p>
 * @title CGBusinessConstants.java
 * @package com.dxjr.console.common.statics 
 * @author tanghaitao
 * @version 0.1 2016年5月18日
 */
package com.dxjr.portal.statics;

/**   
 * <p>
 * Description:这里写描述<br />
 * </p>
 * @title CGBusinessConstants.java
 * @package com.dxjr.console.common.statics 
 * @author tanghaitao
 * @version 0.1 2016年5月18日
 */

public interface CGBusinessConstants {

	//项目ID必须以我行分配给平台的instid作为前缀
	public static String BORROW_PIReq_ID="GCJR";
	
	//e_messges_record 关联号
	public static String  RELATENO="MGL";
	
	//报文messgesID
	public static String  MSGID="MSG";
	
	//项目基本信息登记服务<PIReq>
	public static String PIREQ="PIReq";
	
	//余额查询AQReq
	public static String AQReq="AQReq";
	
	//余额查询响应AQRes
	public static String AQRes="AQRes";
	
	//调用短信接口SSReq
	public static String SSReq="SSReq";
	
	//调用资金冻结接口
	public static String FBReq="FBReq";
	
	public static String FBRes="FBRes";
	
	//投资冻结解冻接口
	public static String UFBReq="UFBReq";
	
	//投资冻结解冻响应
	public static String  UFBRes="UFBRes";
	
	//项目投资信息登记请求
	public static String PTRReq="PTRReq";
	
	//项目投资信息登记响应
	public static String PTRRes="PTRRes";
	
	//商卡资金同步
	public static String SK_SYN="sk_syn";
	
	//平台投资冻结流水号
	public static String P2P_SERIAL_DJ="TDJ";
	
	//平台投资流水号
	public static String P2P_SERIAL_NO="T-B";
	
	//投标冻结类型
	public static String CG_TB="cg_tender_cold";

	//部分项目份额转让请求报文
	public static String PPTReq ="PPTReq";

	public static String ABSReq="ABSReq";

	//币种-人民币
	public static String CURRENCY_RMB="156";

	//服务消息类型 1：主动
	public static Integer MSG_TYPE_ONE=1;
	//服务消息类型 2：被动
	public static Integer MSG_TYPE_TWO=2;
	
	
	
	
	
	
	
	
}
