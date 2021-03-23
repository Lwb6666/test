package com.dxjr.portal.tzjinterface.vo;

/**
 * 
 * @author hujianpan
 * 
 *
 */
public interface IDataPlugin {

	/**
	 * 
	 * <p>
	 * Description:格式化为json字符串<br />
	 * </p>
	 * @author 胡建盼
	 * @version 0.1 2014年12月16日
	 * @return
	 * String
	 */
	public String toJson();
	/**
	 * 
	 * <p>
	 * Description:对象是否为空<br />
	 * </p>
	 * @author 胡建盼
	 * @version 0.1 2014年12月16日
	 * @return
	 * Boolean
	 */
	public Boolean isEmpty();
	/**
	 * 
	 * <p>
	 * Description:对象是否被修改<br />
	 * </p>
	 * @author 胡建盼
	 * @version 0.1 2014年12月17日
	 * @return
	 * Boolean
	 */
	public Boolean isDirty();
}
