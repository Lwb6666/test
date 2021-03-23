package com.dxjr.portal.first.service.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dxjr.base.entity.FirstTenderReal;
import com.dxjr.base.entity.FirstTransfer;
import com.dxjr.base.entity.FirstTransferApproved;
import com.dxjr.base.entity.FirstTransferDetail;
import com.dxjr.base.entity.FirstTransferLog;
import com.dxjr.base.mapper.BaseFirstTenderRealMapper;
import com.dxjr.common.page.Page;
import com.dxjr.common.statics.Constants;
import com.dxjr.portal.first.mapper.FirstTenderRealMapper;
import com.dxjr.portal.first.mapper.FirstTransferApprovedMapper;
import com.dxjr.portal.first.mapper.FirstTransferBorrowMapper;
import com.dxjr.portal.first.mapper.FirstTransferDetailMapper;
import com.dxjr.portal.first.mapper.FirstTransferLogMapper;
import com.dxjr.portal.first.mapper.FirstTransferMapper;
import com.dxjr.portal.first.service.FirstBorrowService;
import com.dxjr.portal.first.service.FirstTransferService;
import com.dxjr.portal.first.vo.FirstBorrowCnd;
import com.dxjr.portal.first.vo.FirstBorrowVo;
import com.dxjr.portal.first.vo.FirstTransferBorrowCnd;
import com.dxjr.portal.first.vo.FirstTransferBorrowTotalVo;
import com.dxjr.portal.first.vo.FirstTransferCancelVo;
import com.dxjr.portal.first.vo.FirstTransferCnd;
import com.dxjr.portal.first.vo.FirstTransferLaunchVo;
import com.dxjr.portal.first.vo.FirstTransferTypeCnd;
import com.dxjr.portal.first.vo.FirstTransferTypeVo;
import com.dxjr.portal.first.vo.FirstTransferVo;
import com.dxjr.portal.statics.BusinessConstants;
import com.dxjr.utils.DateUtils;
import com.dxjr.utils.MD5;

/**
 * <p>
 * Description:直通车转让业务实现类<br />
 * </p>
 * 
 * @title FirstTransferServiceImpl.java
 * @package com.dxjr.portal.first.service.impl
 * @author justin.xu
 * @version 0.1 2015年3月11日
 */
@Service
public class FirstTransferServiceImpl implements FirstTransferService {

	// 日志类取得
	Logger logger = LoggerFactory.getLogger(FirstTransferServiceImpl.class);

	@Autowired
	private FirstTransferMapper firstTransferMapper;
	@Autowired
	private FirstTransferDetailMapper firstTransferDetailMapper;
	@Autowired
	private FirstTransferApprovedMapper firstTransferApprovedMapper;
	@Autowired
	private FirstTransferLogMapper firstTransferLogMapper;
	@Autowired
	private FirstTransferBorrowMapper firstTransferBorrowMapper;
	@Autowired
	private BaseFirstTenderRealMapper baseFirstTenderRealMapper;
	@Autowired
	private FirstTenderRealMapper firstTenderRealMapper;
	@Autowired
	private FirstBorrowService firstBorrowService;

	@Override
	public Page queryPageCanTransferByCnd(FirstTransferTypeCnd firstTransferTypeCnd, Page page) throws Exception {
		// 根据条件查询直通车可转让的数量
		Integer count = firstTransferMapper.queryCanTransferCount(firstTransferTypeCnd);
		// 根据条件分页查询直通车可转让的集合
		List<FirstTransferTypeVo> firstTransferTypeList = firstTransferMapper.queryCanTransferList(firstTransferTypeCnd, page);
		page.setTotalCount(count);
		page.setResult(firstTransferTypeList);
		return page;
	}

	@Override
	public FirstTransferTypeVo queryFirstTransferTypeByCnd(Integer firstTenderRealId, Integer userId) throws Exception {
		FirstTransferTypeVo firstTransferTypeVo = new FirstTransferTypeVo();
		// 根据条件查询直通车可转让信息
		FirstTransferTypeCnd firstTransferTypeCnd = new FirstTransferTypeCnd();
		// 用户ID设定
		firstTransferTypeCnd.setUserId(userId);
		// 最终认购记录设定
		firstTransferTypeCnd.setFirstTenderRealId(firstTenderRealId);
		List<FirstTransferTypeVo> firstTransferTypeList = firstTransferMapper.queryCanTransferList(firstTransferTypeCnd);
		// 可转让信息不能空的场合
		if (null != firstTransferTypeList && firstTransferTypeList.size() == 1) {
			firstTransferTypeVo = firstTransferTypeList.get(0);
		}
		return firstTransferTypeVo;
	}

	@Override
	public String saveFirstTransfer(FirstTransferLaunchVo firstTransferLaunchVo) throws Exception {
		String result = BusinessConstants.SUCCESS;
		// 验证发起转让的参数是否正确
		result = validateSaveFirstTransferParams(firstTransferLaunchVo);
		if (!result.equals(BusinessConstants.SUCCESS)) {
			return result;
		}

		FirstTransferCnd firstTransferCnd = new FirstTransferCnd();
		firstTransferCnd.setUserId(firstTransferLaunchVo.getUserId());
		firstTransferCnd.setTransferStatus(Constants.FIRST_TRANSFER_STATU_ING);
		firstTransferCnd.setLockedRecordYn(BusinessConstants.LOCKED_RECORD_YES);
		// 查询条件查询直通车转让信息总件数
		Integer count = firstTransferMapper.queryFirstTransferCount(firstTransferCnd);
		if (count >= 3) {
			return "最多只能有3个正在转让中的直通车";
		}

		// 锁定 最终认购记录表和对应的待收记录
		FirstTransferTypeCnd firstTransferTypeCnd = new FirstTransferTypeCnd();
		// 最终认购记录设定
		firstTransferTypeCnd.setFirstTenderRealId(firstTransferLaunchVo.getFirstTenderRealId());
		// 是否锁定记录设定
		firstTransferTypeCnd.setLockedRecordYn(BusinessConstants.LOCKED_RECORD_YES);
		// 用户ID
		firstTransferTypeCnd.setUserId(firstTransferLaunchVo.getUserId());
		// 查询直通车可转让直通车总件数
		firstTransferMapper.queryCanTransferCount(firstTransferTypeCnd);
		// 查询对应的转让数据
		FirstTransferTypeVo firstTransferTypeVo = this.queryFirstTransferTypeByCnd(firstTransferLaunchVo.getFirstTenderRealId(),
				firstTransferLaunchVo.getUserId());
		// 直通车转让关联借款的待收统计信息
		FirstTransferBorrowTotalVo firstTransferBorrowTotalVo = firstTransferMapper.queryFirstTransferBorrowTotal(firstTransferLaunchVo
				.getFirstTenderRealId());
		// 验收直通车转让数据是否正确
		result = validateFirstTransferLogic(firstTransferLaunchVo, firstTransferTypeVo, firstTransferBorrowTotalVo);
		// 判断验证信息是否成功
		if (!result.equals(BusinessConstants.SUCCESS)) {
			return result;
		}
		// 更新此笔直通车认购记录为转让中
		FirstTenderReal firstTenderReal = new FirstTenderReal();
		// 最终认购记录设定
		firstTenderReal.setId(firstTransferLaunchVo.getFirstTenderRealId());
		// 状态
		firstTenderReal.setStatus(Constants.TENDER_REAL_STATUS_TRANSFERING);
		// 更新优先投标计划最终认购记录
		baseFirstTenderRealMapper.updateEntity(firstTenderReal);
		// 保存直通车转让
		FirstTransfer firstTransfer = packageFirstTransfer(firstTransferLaunchVo, firstTransferTypeVo, firstTransferBorrowTotalVo);
		// 保存直通车转让明细
		packageFirstTransferDetail(firstTransferTypeVo, firstTransferBorrowTotalVo, firstTransfer);
		// 保存直通车所投借款标转让资金明细
		FirstTransferBorrowCnd firstTransferBorrowCnd = new FirstTransferBorrowCnd();
		firstTransferBorrowCnd.setFirstTenderRealId(firstTransferLaunchVo.getFirstTenderRealId());
		firstTransferBorrowCnd.setFirstTransferId(firstTransfer.getId());
		firstTransferBorrowCnd.setUserId(firstTransferLaunchVo.getUserId());
		firstTransferBorrowMapper.saveFirstTransferBorrowList(firstTransferBorrowCnd);
		// 保存直通车转让审核(初审通过)
		packageFirstTransferApproved(firstTransferLaunchVo, firstTransfer);
		// 保存直通车转让日志
		FirstTransferLog firstTransferLog = new FirstTransferLog(firstTransfer.getId(), firstTransferLaunchVo.getUserId(), new Date(),
				firstTransferLaunchVo.getAddip(), null, firstTransferLaunchVo.getUserName(), "新增直通车转让", Constants.FIRST_TRANSFER_LOG_TYPE_INSERT,
				Constants.SYSTEM_PORTAL, firstTransferLaunchVo.getPlatform());
		firstTransferLogMapper.saveFirstTransferLog(firstTransferLog);
		return result;
	}

	@Override
	public Page queryFirstTransferByCnd(FirstTransferCnd firstTransferCnd, Page page) throws Exception {
		// 防止SQL注入,重新设定orderBy和OrderType
		// orderBy设定
		if (!"addTime".equals(firstTransferCnd.getOrderBy()) && !"accountReal".equals(firstTransferCnd.getOrderBy())
				&& !"awardMoney".equals(firstTransferCnd.getOrderBy()) && !"awardApr".equals(firstTransferCnd.getOrderBy())) {
			firstTransferCnd.setOrderBy("");
		}
		// orderType设定
		if (!"asc".equals(firstTransferCnd.getOrderType()) && !"desc".equals(firstTransferCnd.getOrderType())) {
			firstTransferCnd.setOrderType("asc");
		}
		// 转让状态为空的场合
		if (firstTransferCnd.getTransferStatus() == null) {
			firstTransferCnd.setHasTransferStatus("hasTransfer");
		}

		// 直通车转让总件数取得
		Integer totalCount = firstTransferMapper.queryFirstTransferCount(firstTransferCnd);
		// 直通车转让总件数设定
		page.setTotalCount(totalCount);
		// 直通车转让信息查询
		List<FirstTransferVo> list = firstTransferMapper.queryFirstTransfer(firstTransferCnd, page);
		// 直通车转让信息设定
		page.setResult(list);
		return page;
	}

	@Override
	public FirstTransferVo queryTransferById(Integer transferId) throws Exception {
		return firstTransferMapper.queryTransferById(transferId);
	}

	@Override
	public Page queryMyFirstTransferList(FirstTransferCnd firstTransferCnd, Page page) throws Exception {
		// 查询条件查询直通车转让信息总件数
		Integer count = firstTransferMapper.queryFirstTransferCount(firstTransferCnd);
		// 分页查询我的账号下直通车转让信息的集合
		List<FirstTransferVo> firstTransferList = firstTransferMapper.queryMyFirstTransferList(firstTransferCnd, page);
		page.setTotalCount(count);
		page.setResult(firstTransferList);
		return page;
	}

	@Override
	public Page queryMyFirstTransferedList(FirstTransferCnd firstTransferCnd, Page page) throws Exception {
		// 查询条件查询直通车转让信息总件数
		Integer count = firstTransferMapper.queryMyFirstTransferedCount(firstTransferCnd);
		// 分页查询我的账号下直通车转让信息的集合
		List<FirstTransferVo> firstTransferList = firstTransferMapper.queryMyFirstTransferedList(firstTransferCnd, page);
		page.setTotalCount(count);
		page.setResult(firstTransferList);
		return page;
	}

	@Override
	public String saveCancelFirstTransfer(FirstTransferCancelVo firstTransferCancelVo) throws Exception {
		String result = BusinessConstants.SUCCESS;
		FirstTransferCnd firstTransferCnd = new FirstTransferCnd();
		firstTransferCnd.setUserId(firstTransferCancelVo.getUserId());
		firstTransferCnd.setLockedRecordYn(BusinessConstants.LOCKED_RECORD_YES);
		firstTransferCnd.setId(firstTransferCancelVo.getFirstTransferId());
		// 查询直通车转让信息
		List<FirstTransferVo> list = firstTransferMapper.queryFirstTransfer(firstTransferCnd);
		if (null == list || list.size() == 0) {
			return "未找到直通车转让数据!";
		}
		FirstTransferVo firstTransferVo = list.get(0);
		// 判断状态是否为转让中
		if (!firstTransferVo.getStatus().equals(Constants.FIRST_TRANSFER_STATU_ING)) {
			return "直通车数据非转让中,请确认!";
		}
		// 更新转让的状态为被撤销
		FirstTransfer firstTransfer = new FirstTransfer();
		// 直通车转让ID
		firstTransfer.setId(firstTransferVo.getId());
		firstTransfer.setStatus(Constants.FIRST_TRANSFER_CANCEL);
		// 最后修改人
		firstTransfer.setLastUpdateName(firstTransferCancelVo.getUserName());
		// 直通车转让状态
		firstTransferMapper.updateFirstTransferStatus(firstTransfer);
		// 记录撤消日志
		FirstTransferLog firstTransferLog = new FirstTransferLog(firstTransferVo.getId(), firstTransferVo.getUserId(), new Date(),
				firstTransferCancelVo.getAddIp(), null, firstTransferCancelVo.getUserName(), "取消直通车转让", Constants.FIRST_TRANSFER_LOG_TYPE_CANCEL,
				Constants.SYSTEM_PORTAL, firstTransferCancelVo.getPlatform());
		firstTransferLogMapper.saveFirstTransferLog(firstTransferLog);
		// 更新最终认购记录的状态为未失效
		FirstTenderReal firstTenderReal = new FirstTenderReal();
		// 最终认购记录Id设定
		firstTenderReal.setId(firstTransferVo.getFirstTenderRealId());
		// 状态设定
		firstTenderReal.setStatus(Constants.TENDER_REAL_STATUS_AVAILABLE);
		// 更新优先投标计划最终认购记录
		baseFirstTenderRealMapper.updateEntity(firstTenderReal);
		return result;
	}

	@Override
	public Page queryMyFirstTransferSubscribeList(FirstTransferCnd firstTransferCnd, Page page) throws Exception {
		// 直通车认购信息总件数
		Integer count = firstTransferMapper.queryMyFirstTransferSubscribeCount(firstTransferCnd);
		// 直通车认购信息
		List<FirstTransferVo> firstTransferList = firstTransferMapper.queryMyFirstTransferSubscribeList(firstTransferCnd, page);
		page.setTotalCount(count);
		page.setResult(firstTransferList);
		return page;
	}

	/**
	 * <p>
	 * Description:保存直通车转让审核(初审通过)<br />
	 * </p>
	 * 
	 * @author justin.xu
	 * @version 0.1 2015年3月16日
	 * @param firstTransferLaunchVo
	 * @param firstTransfer
	 * @throws Exception
	 *             void
	 */
	private void packageFirstTransferApproved(FirstTransferLaunchVo firstTransferLaunchVo, FirstTransfer firstTransfer) throws Exception {
		FirstTransferApproved firstTransferApproved = new FirstTransferApproved();
		// 直通车转让ID
		firstTransferApproved.setFirstTransferId(firstTransfer.getId());
		// 审核状态
		firstTransferApproved.setStatus(Constants.FIRST_TRANSFER_APPROVED_ONE_PASS);
		// 审核人
		firstTransferApproved.setVerifyUser(-1);
		// ip
		firstTransferApproved.setVerifyIp(firstTransferLaunchVo.getAddip());
		// 审核时间
		firstTransferApproved.setVerifyTime(new Date());
		// 审核备注
		firstTransferApproved.setVerifyRemark("系统自动初审通过");
		// 系统
		firstTransferApproved.setSystem(Constants.SYSTEM_AUTO);
		// 平台来源
		firstTransferApproved.setPlatform(firstTransferLaunchVo.getPlatform());
		// 保存审核信息
		firstTransferApprovedMapper.saveFirstTransferApproved(firstTransferApproved);
	}

	/**
	 * <p>
	 * Description:保存直通车转让明细<br />
	 * </p>
	 * 
	 * @author justin.xu
	 * @version 0.1 2015年3月16日
	 * @param firstTransferTypeVo
	 * @param firstTransferBorrowTotalVo
	 * @param firstTransfer
	 * @throws Exception
	 *             void
	 */
	private void packageFirstTransferDetail(FirstTransferTypeVo firstTransferTypeVo, FirstTransferBorrowTotalVo firstTransferBorrowTotalVo,
			FirstTransfer firstTransfer) throws Exception {
		// 直通车转让明细类
		FirstTransferDetail firstTransferDetail = new FirstTransferDetail();
		// 直通车转让ID
		firstTransferDetail.setFirstTransferId(firstTransfer.getId());
		// 借款标ID
		firstTransferDetail.setFirstBorrowId(firstTransferTypeVo.getFirstBorrowId());
		// 可用余额
		firstTransferDetail.setFirstUseMoney(firstTransferTypeVo.getUseMoney());
		// 直通车待收本金
		firstTransferDetail.setFirstCollectionCapital(new BigDecimal(firstTransferTypeVo.getAccount()).subtract(firstTransferTypeVo.getUseMoney()));
		// 直通车开通时间
		firstTransferDetail.setFirstTenderTime(firstTransferTypeVo.getAddtime());
		// 转让管理费的月数
		firstTransferDetail.setManageFeeMonth(firstTransferTypeVo.getManageFeeMonth());
		// 转让管理费的比率
		firstTransferDetail.setManageFeeRatio(firstTransferTypeVo.getManageFeeRatio());
		// 添加时间
		firstTransferDetail.setAddtime(new Date());
		// 直通车所投标待收总利息
		firstTransferDetail.setFirstRepayInterest(firstTransferBorrowTotalVo.getFirstRepayInterest());
		// 借款标个数
		firstTransferDetail.setBorrowCount(firstTransferBorrowTotalVo.getBorrowCount());
		// 保存直通车详细
		firstTransferDetailMapper.saveFirstTransferDetail(firstTransferDetail);
	}

	/**
	 * <p>
	 * Description:保存直通车转让信息<br />
	 * </p>
	 * 
	 * @author justin.xu
	 * @version 0.1 2015年3月16日
	 * @param firstTransferLaunchVo
	 * @param firstTransferTypeVo
	 * @param firstTransferBorrowTotalVo
	 * @return
	 * @throws Exception
	 *             FirstTransfer
	 */
	private FirstTransfer packageFirstTransfer(FirstTransferLaunchVo firstTransferLaunchVo, FirstTransferTypeVo firstTransferTypeVo,
			FirstTransferBorrowTotalVo firstTransferBorrowTotalVo) throws Exception {
		FirstBorrowCnd firstBorrowCnd = new FirstBorrowCnd();
		// 优先投标计划ID
		firstBorrowCnd.setId(firstTransferTypeVo.getFirstBorrowId());
		// 查询优先投标计划
		FirstBorrowVo firstBorrowVo = firstBorrowService.queryFirstBorrowByCnd(firstBorrowCnd);

		FirstTransfer firstTransfer = new FirstTransfer();
		// 最终认购记录ID
		firstTransfer.setFirstTenderRealId(firstTransferLaunchVo.getFirstTenderRealId());
		// 直通车期数
		firstTransfer.setFirstPeriods(firstBorrowVo.getPeriods());
		// 直通车预期收益
		firstTransfer.setFirstPerceivedRate(firstBorrowVo.getPerceivedRate());
		// 投资本金[A]
		firstTransfer.setTenderCapital(new BigDecimal(firstTransferTypeVo.getAccount()));
		// 应得利息
		if (firstTransferTypeVo.getInterest() == null) {
			firstTransfer.setInterest(BigDecimal.ZERO);
		} else {
			firstTransfer.setInterest(firstTransferTypeVo.getInterest());
		}
		// 利息的利息
		if (firstTransferTypeVo.getInterestDiff() == null) {
			firstTransfer.setInterestDiff(BigDecimal.ZERO);
		} else {
			firstTransfer.setInterestDiff(firstTransferTypeVo.getInterestDiff());
		}
		// 债权价格
		firstTransfer.setAccount(firstTransferTypeVo.getBondsAccount());
		// 奖励
		if (null == firstTransferLaunchVo.getAwards()) {
			firstTransfer.setAwards(new BigDecimal("0"));
		} else {
			firstTransfer.setAwards(firstTransferLaunchVo.getAwards());
		}
		// 转让价格
		firstTransfer.setAccountReal(firstTransferTypeVo.getBondsAccount().subtract(firstTransfer.getAwards()));
		// 转让系数
		firstTransfer.setCoef(firstTransfer.getAccountReal().divide(firstTransfer.getAccount(), 3, BigDecimal.ROUND_FLOOR));
		// 转让管理费
		firstTransfer.setManageFee(firstTransferTypeVo.getManageFee());
		// 所得的利息管理费
		firstTransfer.setInterestManageFee(new BigDecimal("0"));
		// 有效时间(天)
		firstTransfer.setValidTime(firstTransferLaunchVo.getValidTime());
		// 债权转让标题
		firstTransfer.setTransferName(firstBorrowVo.getName());
		// 转让人ID
		firstTransfer.setUserId(firstTransferLaunchVo.getUserId());
		// 添加时间
		firstTransfer.setAddtime(new Date());
		// 结束时间
		firstTransfer.setEndTime(DateUtils.dayOffset(firstTransfer.getAddtime(), firstTransfer.getValidTime()));
		// 认购密码(MD5)
		if (!StringUtils.isEmpty(firstTransferLaunchVo.getBidPassword())) {
			firstTransfer.setBidPassword(MD5.toMD5(firstTransferLaunchVo.getBidPassword()));
		}
		// 状态
		firstTransfer.setStatus(Constants.FIRST_TRANSFER_STATU_ING);
		// 平台来源
		firstTransfer.setPlatform(firstTransferLaunchVo.getPlatform());
		// 实际认购人待收总利息=为未还款的总利息-转让人占用的利息+利息的利息
		firstTransfer.setActualInterest(firstTransferBorrowTotalVo.getFirstRepayInterest().subtract(firstTransfer.getInterest())
				.add(firstTransfer.getInterestDiff()));
		// 保存直通车转让信息
		firstTransferMapper.saveFirstTransfer(firstTransfer);
		return firstTransfer;
	}

	/**
	 * <p>
	 * Description:验证直通车转让业务数据是否正确<br />
	 * </p>
	 * 
	 * @author justin.xu
	 * @version 0.1 2015年3月16日
	 * @param firstTransferLaunchVo
	 * @param firstTransferTypeVo
	 * @return
	 * @throws Exception
	 *             String
	 */
	private String validateFirstTransferLogic(FirstTransferLaunchVo firstTransferLaunchVo, FirstTransferTypeVo firstTransferTypeVo,
			FirstTransferBorrowTotalVo firstTransferBorrowTotalVo) throws Exception {
		String result = BusinessConstants.SUCCESS;
		if (null == firstTransferTypeVo) {
			// 包含：发起的此笔转让记录有投标冻结时不允许发起直通车转让
			return "直通车转让数据已变更，请确认";
		}
		// 二次债转
		// if (firstTransferTypeVo.getParentId() != null) {
		// return "目前不支持直通车二次转让";
		// }
		if (null != firstTransferLaunchVo.getAwards()) {
			BigDecimal canAwards = firstTransferTypeVo.getBondsAccount().multiply(new BigDecimal("0.01")).setScale(2, RoundingMode.HALF_UP);
			if (firstTransferLaunchVo.getAwards().compareTo(canAwards) > 0) {
				return "奖励金额不能超过债权价格的1%【" + canAwards + "元】,请确认！";
			}
		}
		// 判断开通金额与(待收直通车本金+直通车可用余额)是否一致，不一致给出提示
		if (new BigDecimal(firstTransferTypeVo.getAccount()).compareTo(firstTransferBorrowTotalVo.getFirstRepayCapital().add(
				firstTransferTypeVo.getUseMoney())) != 0) {
			return "直通车开通金额不等于(直通车待收本金+直通车可用余额),请联系客服!";
		}
		return result;
	}

	/**
	 * <p>
	 * Description:验证发起转让的参数是否正确<br />
	 * </p>
	 * 
	 * @author justin.xu
	 * @version 0.1 2015年3月15日
	 * @param firstTransferLaunchVo
	 * @return String
	 */
	private String validateSaveFirstTransferParams(FirstTransferLaunchVo firstTransferLaunchVo) {
		if (null == firstTransferLaunchVo || null == firstTransferLaunchVo.getFirstTenderRealId()) {
			return "参数异常，请确认!";
		}

		if (null == firstTransferLaunchVo.getCheckCode() || null == firstTransferLaunchVo.getSessionCheckCode()
				|| !firstTransferLaunchVo.getCheckCode().equals(firstTransferLaunchVo.getSessionCheckCode())) {
			return "验证码不正确";
		}

		if (null == firstTransferLaunchVo.getValidTime()) {
			return "失效时间不能够为空";
		}

		if (!(1 <= firstTransferLaunchVo.getValidTime().intValue() && firstTransferLaunchVo.getValidTime().intValue() <= 3)) {
			return "失效时间必须在1~3之间";
		}

		if (firstTransferLaunchVo.getAwards() != null && firstTransferLaunchVo.getAwards().compareTo(BigDecimal.ZERO) < 0) {
			return "认购奖励不能小于0";
		}
		String bidPassword = firstTransferLaunchVo.getBidPassword();
		if (!StringUtils.isEmpty(bidPassword)) {
			if (!bidPassword.matches("[0-9a-zA-Z]{6,12}")) {
				return "认购密码必须为字母或数字并且密码长度必须在6~12位之间";
			}
		}
		return BusinessConstants.SUCCESS;
	}

}
