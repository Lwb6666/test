package com.dxjr.portal.stock.service.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import com.dxjr.base.entity.Account;
import com.dxjr.base.entity.AccountLog;
import com.dxjr.base.entity.Stock;
import com.dxjr.base.mapper.BaseAccountLogMapper;
import com.dxjr.base.mapper.BaseAccountMapper;
import com.dxjr.portal.account.mapper.AccountLogMapper;
import com.dxjr.portal.account.mapper.AccountNetValueMapper;
import com.dxjr.portal.account.vo.DrawMoneyToNoDrawCnd;
import com.dxjr.portal.base.MessageBox;
import com.dxjr.portal.member.mapper.MemberMapper;
import com.dxjr.portal.member.vo.MemberVo;
import com.dxjr.portal.statics.BusinessConstants;
import com.dxjr.portal.stock.mapper.StockMapper;
import com.dxjr.portal.stock.service.StockService;
import com.dxjr.security.ShiroUser;
import com.dxjr.utils.DateUtils;
import com.dxjr.utils.MD5;
import com.dxjr.utils.MoneyUtil;

/**
 * 投资人期权信息
 * <p>
 * Description:这里写描述<br />
 * </p>
 * 
 * @title StockServiceImpl.java
 * @package com.dxjr.portal.stock.service.impl
 * @author huangpin
 * @version 0.1 2014年9月11日
 */
@Service
public class StockServiceImpl implements StockService {

	@Autowired
	private StockMapper stockMapper;

	@Autowired
	private BaseAccountMapper accountMapper;

	@Autowired
	private BaseAccountLogMapper baseAccountLogMapper;

	@Autowired
	private AccountLogMapper accountLogMapper;

	@Autowired
	private MemberMapper memberMapper;

	@Autowired
	private AccountNetValueMapper accountNetValueMapper;

	/**
	 * 查询我的期权信息
	 * <p>
	 * Description:这里写描述<br />
	 * </p>
	 * 
	 * @author huangpin
	 * @version 0.1 2014年9月11日
	 * @param shiroUser
	 * @return ModelAndView
	 */
	@Override
	public ModelAndView myStockInfo(ShiroUser shiroUser) throws Exception {
		ModelAndView mv = new ModelAndView("/account/stock/myStock");
		List<Stock> list = stockMapper.getByProperty("user_id", shiroUser.getUserId() + "");
		if (list != null && list.size() > 0) {
			mv.addObject("stock", list.get(0));
		}
		BigDecimal money = accountLogMapper.haveStockMoney(shiroUser.getUserId());
		mv.addObject("haveStockMoney", MoneyUtil.fmtMoneyByDot(money));
		return mv;
	}
	/**
	 * 根据userId获取期权数据数量
	 * <p>
	 * Description:根据userId获取期权数据数量<br />
	 * </p>
	 * @author wushaoling
	 * @version 0.1 2016年5月25日
	 * @param userId
	 * @return
	 * int
	 */
	public int getCountByUserId(@Param(value = "userId") Integer userId){
		return stockMapper.getCountByUserId(userId);
	}

	/**
	 * 现金行权
	 * <p>
	 * Description:这里写描述<br />
	 * </p>
	 * 
	 * @author huangpin
	 * @version 0.1 2016年9月27日
	 * @param payPwd
	 * @param addIp
	 * @param shiroUser
	 * @return
	 * @throws Exception MessageBox
	 */
	@Override
	public MessageBox updateStockMoney(String payPwd, String exerciseIp, ShiroUser shiroUser) throws Exception {
		int userId = shiroUser.getUserId();

		// 验证交易密码
		payPwd = MD5.toMD5(payPwd);
		MemberVo mem = memberMapper.queryMemberByloginname(shiroUser.getUserName());
		if (!mem.getPaypassword().equals(payPwd)) {
			return MessageBox.build("0", "交易密码错误，请重新输入。");
		}

		Stock stock = stockMapper.queryByUserIdForUpdate(userId);
		if (stock != null) {
			if (stock.getStatus() == 2) {
				return MessageBox.build("0", "现金行权失败：您已进行过现金行权。");
			}

			stock.setExerciseIp(exerciseIp);
			stock.setExerciseTime(new Date().getTime());

			BigDecimal stockMoney = getMoney(stock.getStockNum());
			if (stockMoney.compareTo(new BigDecimal(0)) == 0) {
				return MessageBox.build("0", "现金行权失败：未在行权时间内操作。");
			}
			stock.setStockMoney(stockMoney);

			// 更新期权状态
			stockMapper.updateStockMoney(stock);

			// 更改账户可提，可用，总额
			Account account = accountMapper.queryByUserIdForUpdate(userId);
			stockMapper.updateAccount(stockMoney, userId);

			// 记录账户金额变动日志
			account = accountMapper.queryById(account.getId());
			AccountLog accountLog = new AccountLog();
			accountLog.setUserId(userId);
			accountLog.setAddip(exerciseIp);
			accountLog.setAddtime(new Date().getTime() / 1000 + "");
			accountLog.setMoney(stockMoney);
			accountLog.setTotal(account.getTotal());
			accountLog.setDrawMoney(account.getDrawMoney());
			accountLog.setNoDrawMoney(account.getNoDrawMoney());
			accountLog.setUseMoney(account.getUseMoney());
			accountLog.setNoUseMoney(account.getNoUseMoney());
			accountLog.setCollection(account.getCollection());
			accountLog.setFirstBorrowUseMoney(account.getFirstBorrowUseMoney());
			accountLog.setToUser(-1);
			accountLog.setType("stock_to_money");
			accountLog.setRemark("现金行权操作，原有期权： " + stock.getStockNum() + " 份，现金行权获得金额：" + stockMoney + " 元，现有期权 0 份。");
			baseAccountLogMapper.insertEntity(accountLog);

			// 判断用户的可提金额是否大于净值额度，如果大于，转入受限金额
			saveDrawMoneyToNoDraw(exerciseIp, userId);

			return MessageBox.build("1", "现金行权成功，原有期权： " + stock.getStockNum() + " 份，现金行权获得金额：" + stockMoney + " 元，现有期权 0 份。");
		} else {
			return MessageBox.build("0", "现金行权失败：您暂无期权。");
		}
	}

	/**
	 * 获取行权金额
	 * <p>
	 * Description:这里写描述<br />
	 * </p>
	 * 
	 * @author huangpin
	 * @version 0.1 2014年9月11日
	 * @param money
	 * @return BigDecimal
	 */
	public BigDecimal getMoney(long stockNum) {
		long now = new Date().getTime();
		// now = DateUtils.parse("2017-09-26 23:59:59",
		// "yyyy-MM-dd HH:mm:ss").getTime();

		long begin = DateUtils.parse("2014-09-27", "yyyy-MM-dd").getTime();
		long end = DateUtils.parse("2015-09-27", "yyyy-MM-dd").getTime();

		if (now >= begin && now < end) {
			return new BigDecimal(stockNum);
		}

		begin = DateUtils.parse("2015-09-27", "yyyy-MM-dd").getTime();
		end = DateUtils.parse("2016-09-27", "yyyy-MM-dd").getTime();
		if (now >= begin && now < end) {
			return new BigDecimal(stockNum * 2);
		}

		begin = DateUtils.parse("2016-09-27", "yyyy-MM-dd").getTime();
		end = DateUtils.parse("2017-09-27", "yyyy-MM-dd").getTime();
		if (now >= begin && now < end) {
			return new BigDecimal(stockNum * 3);
		}
		return new BigDecimal(0);
	}

	/**
	 * <p>
	 * Description:判断用户的可提金额是否大于净值额度，如果大于，转入受限金额<br />
	 * </p>
	 * 
	 * @author justin.xu
	 * @version 0.1 2014年10月25日
	 * @param exerciseIp
	 * @param userId
	 * @throws Exception void
	 */
	private void saveDrawMoneyToNoDraw(String exerciseIp, int userId) throws Exception {
		DrawMoneyToNoDrawCnd drawMoneyToNoDrawCnd = new DrawMoneyToNoDrawCnd();
		drawMoneyToNoDrawCnd.setUserid(userId);
		drawMoneyToNoDrawCnd.setNetmoneytype(BusinessConstants.NET_TYPE_STOCK_TO_MONEY);
		drawMoneyToNoDrawCnd.setAddip(exerciseIp);
		drawMoneyToNoDrawCnd.setAccountlogType("net_draw_to_nodraw_first_stock_money");
		drawMoneyToNoDrawCnd.setAccountlogRemark("现金行权之后,可提金额大于净值额度，可提金额转入受限金额。");
		accountNetValueMapper.dealDrawmoneyToNodraw(drawMoneyToNoDrawCnd);
	}
	/*
	 * public static void main(String[] args) {
	 * System.out.println(DateUtils.parse("2014-9-30 23:59:59",
	 * "yyyy-MM-dd HH:mm:ss").getTime() / 1000); }
	 */
}
