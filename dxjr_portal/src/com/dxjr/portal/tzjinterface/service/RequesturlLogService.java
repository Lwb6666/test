package com.dxjr.portal.tzjinterface.service;

import com.dxjr.portal.tzjinterface.vo.RequesturlLogVo;
import com.dxjr.utils.exception.AppException;

public interface RequesturlLogService {
	/**
	 * 
	 * <p>
	 * Description:根据主键查询访问日志<br />
	 * </p>
	 * @author 胡建盼
	 * @version 0.1 2014年10月16日
	 * @param requesturlLogVo
	 * @return
	 * @throws AppException
	 * Integer
	 */
	public RequesturlLogVo queryRequestUrlLog(Integer id) throws AppException;
	/**
	 * 
	 * <p>
	 * Description:新增一条访问日志<br />
	 * </p>
	 * @author 胡建盼
	 * @version 0.1 2014年10月16日
	 * @param requesturlLogVo
	 * @return
	 * @throws AppException
	 * Integer
	 */
	public Integer insertRequestUrlLog(RequesturlLogVo requesturlLogVo) throws AppException;
	/**
	 * 
	 * <p>
	 * Description:修改指定的访问日志<br />
	 * </p>
	 * @author 胡建盼
	 * @version 0.1 2014年10月16日
	 * @param requesturlLogVo
	 * @return
	 * @throws AppException
	 * Integer
	 */
	public Integer updateRequestUrlLog(RequesturlLogVo requesturlLogVo) throws AppException;
	/**
	 * 
	 * <p>
	 * Description:删除指定的访问日志<br />
	 * </p>
	 * @author 胡建盼
	 * @version 0.1 2014年10月16日
	 * @param requesturlLogVo
	 * @return
	 * @throws AppException
	 * Integer
	 */
	public Integer deleteRequestUrlLog(Integer id) throws AppException;

}
