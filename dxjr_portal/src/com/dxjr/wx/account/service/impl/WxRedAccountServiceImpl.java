package com.dxjr.wx.account.service.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dxjr.common.page.Page;
import com.dxjr.wx.account.mapper.WxRedAccountMapper;
import com.dxjr.wx.account.service.WxRedAccountService;
import com.dxjr.wx.account.vo.WxRedAccountVo;

/**
 * 红包业务实现类
 * <p>
 * Description:红包业务实现类<br />
 * </p>
 * 
 * @title RedAccountServiceImpl.java
 * @package com.dxjr.wx.account.service.impl
 * @author wushaoling
 * @version 0.1 2016年5月18日
 */
@Service
public class WxRedAccountServiceImpl implements WxRedAccountService{
	public Logger logger = Logger.getLogger(WxRedAccountServiceImpl.class);
	
	@Autowired
	private WxRedAccountMapper redAccountMapper;
	
   /**
    * 查询可使用红包
    * <p>
    * Description:根据用户Id查询可使用红包<br />
    * </p>
    * @author wushaoling
    * @date 2016年5月18日
    * @param userId
    * @return list 红包list 
    * @throws Exception
    * 
    */
	@Override
	public List<WxRedAccountVo> queryOpenRed(int userId) throws Exception {
		// TODO Auto-generated method stub
		return redAccountMapper.queryOpenRed(userId);
	}
	
	/**
	* 查询不可使用红包
	* <p>
	* Description:根据用户ID查询不可使用红包<br />
	* </p>
	* @author wushaoling
	* @date 2016年5月18日
	* @param userId
	* @return list 不可使用红包list 
	* @throws Exception
	*/
	@Override
	public List<WxRedAccountVo> queryByExpiredRed(int userId)
			throws Exception {
		// TODO Auto-generated method stub
		return redAccountMapper.queryByExpiredRed(userId);
	}
	
	/**
    * 查询可使用红包数量
    * <p>
    * Description:查询可使用红包数量<br />
    * </p>
    * @author wushaoling
    * @date 2016年5月18日
    * @param userId
    * @return int 可使用红包数量
    * @throws Exception
	*/
	@Override
	public int queryOpenRedCount(Integer userId) throws Exception {
		// TODO Auto-generated method stub
		return redAccountMapper.queryOpenRedCount(userId);
	}

	@Override
	public Page queryOpenRedByPage(WxRedAccountVo wxRedAccountVo, Page page) {
		logger.info("红包分页查询开始，page为："+page);
		int totalCount = 0;
		List<WxRedAccountVo> retLst = null;
		try {
			totalCount = redAccountMapper.queryTotalRed(wxRedAccountVo);
			 //判断总记录数大于0
			if(totalCount>0)
		    {
		    	page.setTotalCount(totalCount);
		    }
			retLst = redAccountMapper.queryRedByPage(wxRedAccountVo,page);
			//判断列表对象不为空且列表包含的记录数大于0
			if(retLst!=null && retLst.size()>0)
			{
				page.setResult(retLst);
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		logger.info("红包分页查询结束，总条数为："+page.getTotalCount()+"/n 结果返回为:"+page.getResult().toString());
		return page;
	}

	@Override
	public Page queryCloseRedByPage(WxRedAccountVo wxRedAccountVo, Page page) {
		logger.info("不可用红包分页查询开始，page为："+page);
		int totalCount = 0;
		List<WxRedAccountVo> retLst = null;
		try {
			totalCount = redAccountMapper.queryTotalCloseRed(wxRedAccountVo);
			 //判断总记录数大于0
			if(totalCount>0)
		    {
		    	page.setTotalCount(totalCount);
		    }
			retLst = redAccountMapper.queryCloseRedByPage(wxRedAccountVo,page);
			//判断列表对象不为空且列表包含的记录数大于0
			if(retLst!=null && retLst.size()>0)
			{
				page.setResult(retLst);
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		logger.info("不可用红包分页查询结束，总条数为："+page.getTotalCount()+"/n 结果返回为:"+page.getResult().toString());
		return page;
	}
}
