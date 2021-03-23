package com.dxjr.portal.first.service.impl;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dxjr.common.page.Page;
import com.dxjr.common.statics.Constants;
import com.dxjr.portal.account.vo.AccountVo;
import com.dxjr.portal.curAccount.service.CurAccountService;
import com.dxjr.portal.first.mapper.FirstTenderDetailMapper;
import com.dxjr.portal.first.mapper.FirstTenderRealMapper;
import com.dxjr.portal.first.service.FirstTenderDetailService;
import com.dxjr.portal.first.vo.FirstBorrowVo;
import com.dxjr.portal.first.vo.FirstTenderDetailCnd;
import com.dxjr.portal.first.vo.FirstTenderDetailVo;

/**
 * <p>
 * Description:优先投标计划开通明细业务实现类<br />
 * </p>
 * 
 * @title FirstTenderDetailServiceImpl.java
 * @package com.dxjr.portal.first.service.impl
 * @author justin.xu
 * @version 0.1 2014年7月15日
 */
@Service
public class FirstTenderDetailServiceImpl implements FirstTenderDetailService {

	@Autowired
	private FirstTenderDetailMapper firstTenderDetailMapper;
	@Autowired
	private FirstTenderRealMapper firstTenderRealMapper;
	@Autowired
	private CurAccountService curAccountService;

	@Override
	public Page queryPageListByCnd(FirstTenderDetailCnd firstTenderDetailCnd, Page page) throws Exception {
		Integer totalCount = firstTenderDetailMapper.queryFirstTenderDetailCount(firstTenderDetailCnd);
		page.setTotalCount(totalCount);
		List<FirstTenderDetailVo> list = firstTenderDetailMapper.queryFirstTenderDetailList(firstTenderDetailCnd, page);
		page.setResult(list);
		return page;
	}

	@Override
	public List<FirstTenderDetailVo> queryListByCnd(FirstTenderDetailCnd firstTenderDetailCnd) throws Exception {
		return firstTenderDetailMapper.queryFirstTenderDetailList(firstTenderDetailCnd);
	}

	@Override
	public List<AccountVo> queryAccountListForUpdateByFirstId(Integer firstBorrowId) {
		return firstTenderDetailMapper.queryAccountListForUpdateByFirstId(firstBorrowId);
	}

	@Override
	public Integer updateStatusByFirstBorrowId(Integer firstBorrowId, Integer status) throws Exception {
		return firstTenderDetailMapper.updateStatusByFirstBorrowId(firstBorrowId, status);
	}

	@Override
	public BigDecimal getMaxeffectiveMoney(FirstBorrowVo firstBorrow, Integer userId, BigDecimal money, AccountVo account) throws Exception {
		// 获取已投金额
		BigDecimal tendered = BigDecimal.ZERO;
		if (firstBorrow.getStatus() == Constants.FIRST_BORROW_STATUS_APPROVE_PASS) { // 开通中
			// 查询该用户的开通记录
			FirstTenderDetailCnd firstTenderDetailCnd = new FirstTenderDetailCnd();
			firstTenderDetailCnd.setFirstBorrowId(firstBorrow.getId());
			firstTenderDetailCnd.setUserId(userId);
			List<FirstTenderDetailVo> list = firstTenderDetailMapper.queryFirstTenderDetailList(firstTenderDetailCnd);
			if (list != null && list.size() > 0) {
				for (FirstTenderDetailVo btr : list) {
					tendered = tendered.add(new BigDecimal(btr.getAccount()));
				}
			}
		}
		if (firstBorrow.getStatus() == Constants.FIRST_BORROW_STATUS_SUCCESS_APPROVE_PASS) { // 上车
			Integer accountTotal = firstTenderRealMapper.findAccountTotalByUserIdAndFirstBorrowId(firstBorrow.getId(), userId);
			if (accountTotal != null) {
				tendered = tendered.add(new BigDecimal(accountTotal));
			}
		}
		// 目前所剩余额
		BigDecimal syje = new BigDecimal(0);
		if (firstBorrow.getStatus() == 3) { // 开通中
			syje = new BigDecimal(firstBorrow.getPlanAccount()).subtract(new BigDecimal(firstBorrow.getAccountYes()));
		}
		if (firstBorrow.getStatus() == 5 && firstBorrow.getPlanAccount() > firstBorrow.getRealAccount()) { // 再次上车
			syje = new BigDecimal(firstBorrow.getPlanAccount()).subtract(new BigDecimal(firstBorrow.getRealAccount()));
		}
		BigDecimal useMoney = account.getUseMoney();
		BigDecimal max = new BigDecimal(firstBorrow.getMostAccount());
		// 用户可再投金额
		max = max.subtract(tendered);

		BigDecimal min = new BigDecimal(firstBorrow.getLowestAccount());

		BigDecimal result1 = min;
		BigDecimal result2 = max;
		// 是否还有可以投直通车金额
		boolean isNoMoreMoney = false;
		if (max.compareTo(BigDecimal.ZERO) == 0) {
			result1 = BigDecimal.ZERO;
			result2 = BigDecimal.ZERO;
			isNoMoreMoney = true;
		}

		if (!isNoMoreMoney) {
			boolean isfinal = false;
			if (syje.compareTo(min) < 0) {
				isfinal = true;
			}
			if ((money.subtract(syje)).compareTo(new BigDecimal("0.01")) < 0) {
				isfinal = true;
			}
			if (result2.compareTo(BigDecimal.ZERO) < 0) {
				result2 = BigDecimal.ZERO;
				result1 = BigDecimal.ZERO;
			} else {
				if (result2.compareTo(BigDecimal.ZERO) == 0) {
					max = syje;
				}

				if (useMoney.compareTo(max) >= 0) {
					if (max.compareTo(syje) >= 0) {
						result2 = syje;
					} else if (max.compareTo(syje) < 0) {
						if (max.compareTo(min) < 0) {
							result1 = BigDecimal.ZERO;
							result2 = BigDecimal.ZERO;
						} else {
							result2 = max;
						}
					}
				} else if (useMoney.compareTo(max) < 0) {
					result2 = useMoney;
					if (useMoney.compareTo(min) >= 0) {
						if (useMoney.compareTo(syje) >= 0) {
							result2 = syje;
						} else if (useMoney.compareTo(syje) < 0) {
							result2 = useMoney;
						}
					} else if (useMoney.compareTo(min) < 0) {
						if (syje.compareTo(min) >= 0) {
							result1 = BigDecimal.ZERO;
							result2 = BigDecimal.ZERO;
						} else if (syje.compareTo(min) < 0) {
							isfinal = true;
							if (useMoney.compareTo(syje) >= 0) {
								result1 = syje;
								result2 = syje;
							} else if (useMoney.compareTo(syje) < 0) {
								result1 = BigDecimal.ZERO;
								result2 = BigDecimal.ZERO;
							}
						}
					}
				}

			}

			if (isfinal) {
				if (result1.compareTo(result2) > 0) {
					result1 = result2;
				}
			} else {
				if (result1.compareTo(result2) > 0) {
					result1 = BigDecimal.ZERO;
					result2 = BigDecimal.ZERO;
				}
			}

			// if(isfinal){
			result1 = result1.setScale(2, BigDecimal.ROUND_DOWN);
			result2 = result2.setScale(2, BigDecimal.ROUND_DOWN);
			// }

		}
		return result2;
	}

	/**
	 * 判断输入金额是否有效金额
	 * 
	 * @param borrow
	 * @param user
	 * @param money
	 * @return
	 */
	public BigDecimal isEffectiveMoney(FirstBorrowVo firstBorrow, Integer userId, BigDecimal money, AccountVo account) throws Exception {
		// 获取已投金额
		BigDecimal tendered = BigDecimal.ZERO;
		if (firstBorrow.getStatus() == Constants.FIRST_BORROW_STATUS_APPROVE_PASS) { // 开通中
			// 查询该用户的开通记录
			FirstTenderDetailCnd firstTenderDetailCnd = new FirstTenderDetailCnd();
			firstTenderDetailCnd.setFirstBorrowId(firstBorrow.getId());
			firstTenderDetailCnd.setUserId(userId);
			List<FirstTenderDetailVo> list = firstTenderDetailMapper.queryFirstTenderDetailList(firstTenderDetailCnd);
			if (list != null && list.size() > 0) {
				for (FirstTenderDetailVo btr : list) {
					tendered = tendered.add(new BigDecimal(btr.getAccount()));
				}
			}
		}
		if (firstBorrow.getStatus() == Constants.FIRST_BORROW_STATUS_SUCCESS_APPROVE_PASS) { // 上车
			Integer accountTotal = firstTenderRealMapper.findAccountTotalByUserIdAndFirstBorrowId(firstBorrow.getId(), userId);
			if (accountTotal != null) {
				tendered = tendered.add(new BigDecimal(accountTotal));
			}
		}
		// 标目前所剩余额
		// 目前所剩余额
		BigDecimal syje = new BigDecimal(0);
		if (firstBorrow.getStatus() == 3) { // 开通中
			syje = new BigDecimal(firstBorrow.getPlanAccount()).subtract(new BigDecimal(firstBorrow.getAccountYes()));
		}
		if (firstBorrow.getStatus() == 5 && firstBorrow.getPlanAccount() > firstBorrow.getRealAccount()) { // 再次上车
			syje = new BigDecimal(firstBorrow.getPlanAccount()).subtract(new BigDecimal(firstBorrow.getRealAccount()));
		}
		BigDecimal useMoney = account.getUseMoney();
		BigDecimal max = new BigDecimal(firstBorrow.getMostAccount());
		if (max.compareTo(BigDecimal.ZERO) == 0) {
			max = syje;
		} else {
			max = max.subtract(tendered);
		}
		BigDecimal min = new BigDecimal(firstBorrow.getLowestAccount());
		BigDecimal inputMoney = money;

		BigDecimal returnValue = BigDecimal.ZERO;

		BigDecimal result1 = min;
		BigDecimal result2 = max;

		boolean isNoMoreMoney = false;
		if (max.compareTo(BigDecimal.ZERO) == 0) {
			result1 = BigDecimal.ZERO;
			result2 = BigDecimal.ZERO;
			isNoMoreMoney = true;
		}

		if (!isNoMoreMoney) {

			boolean isfinal = false;

			if (syje.compareTo(min) < 0) {
				isfinal = true;
			}

			if ((money.subtract(syje)).compareTo(new BigDecimal("0.01")) < 0) {
				isfinal = true;
			}

			if (result2.compareTo(BigDecimal.ZERO) < 0) {
				result2 = BigDecimal.ZERO;
				result1 = BigDecimal.ZERO;
			} else {
				if (result2.compareTo(BigDecimal.ZERO) == 0) {
					max = syje;
				}

				if (useMoney.compareTo(max) >= 0) {
					if (max.compareTo(syje) >= 0) {
						result2 = syje;
					} else if (max.compareTo(syje) < 0) {
						if (max.compareTo(min) < 0) {
							result1 = BigDecimal.ZERO;
							result2 = BigDecimal.ZERO;
						} else {
							result2 = max;
						}
					}
				} else if (useMoney.compareTo(max) < 0) {
					result2 = useMoney;
					if (useMoney.compareTo(min) >= 0) {
						if (useMoney.compareTo(syje) >= 0) {
							result2 = syje;
						} else if (useMoney.compareTo(syje) < 0) {
							result2 = useMoney;
						}
					} else if (useMoney.compareTo(min) < 0) {
						if (syje.compareTo(min) >= 0) {
							result1 = BigDecimal.ZERO;
							result2 = BigDecimal.ZERO;
						} else if (syje.compareTo(min) < 0) {
							isfinal = true;
							if (useMoney.compareTo(syje) >= 0) {
								result1 = syje;
								result2 = syje;
							} else if (useMoney.compareTo(syje) < 0) {
								result1 = BigDecimal.ZERO;
								result2 = BigDecimal.ZERO;
							}
						}
					}
				}
			}

			if (isfinal) {
				if (result1.compareTo(result2) > 0) {
					result1 = result2;
				}
			} else {
				if (result1.compareTo(result2) > 0) {
					result1 = BigDecimal.ZERO;
					result2 = BigDecimal.ZERO;
				}
			}

			if (isfinal) {
				result1 = result1.setScale(2, BigDecimal.ROUND_UP);
				result2 = result2.setScale(2, BigDecimal.ROUND_UP);
			}
			if (result1.compareTo(BigDecimal.ZERO) != 0 && result2.compareTo(BigDecimal.ZERO) != 0) {
				if (inputMoney.compareTo(result1) >= 0 && inputMoney.compareTo(result2) <= 0) {
					returnValue = inputMoney;
				}
			}

		}
		return returnValue;
	}

	@Override
	public Page queryFirstTenderDetail(FirstTenderDetailCnd firstTenderDetailCnd, Page page) throws Exception {
		page.setTotalCount(firstTenderDetailMapper.countFirstTenderDetail(firstTenderDetailCnd));
		page.setResult(firstTenderDetailMapper.queryFirstTenderDetail(firstTenderDetailCnd, page));
		return page;
	}

	@Override
	public Integer updateRealIdByFirstBorrowId(Integer firstBorrowId) throws Exception {
		return firstTenderDetailMapper.updateRealIdByFirstBorrowId(firstBorrowId);
	}

	/**
	 * 判断输入金额是否有效金额
	 * 
	 * @param borrow
	 * @param user
	 * @param money
	 * @return
	 */
	@Override
	public BigDecimal isEffectiveMoneyForTenderReal(FirstBorrowVo firstBorrow, Integer userId, BigDecimal money, AccountVo account) throws Exception {
		// 获取已投金额
		BigDecimal tendered = BigDecimal.ZERO;
		Integer accountTotal = firstTenderRealMapper.findAccountTotalByUserIdAndFirstBorrowId(firstBorrow.getId(), userId);
		if (accountTotal != null) {
			tendered = tendered.add(new BigDecimal(accountTotal));
		}
		// 标目前所剩余额
		BigDecimal syje = new BigDecimal(firstBorrow.getPlanAccount()).subtract(new BigDecimal(firstBorrow.getRealAccount()));
		BigDecimal useMoney = account.getUseMoney();
		// 如果可使用活期宝金额，则可用金额加上活期宝可转出最大金额
		if (firstBorrow.getIsUseCurMoney() != null && firstBorrow.getIsUseCurMoney().equals("1")) { // 可使用活期宝
			try {
				BigDecimal maxCurUseMoney = curAccountService.getMaxUseMoneyByNow(userId);
				useMoney = useMoney.add(maxCurUseMoney);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		BigDecimal max = new BigDecimal(firstBorrow.getMostAccount());
		if (max.compareTo(BigDecimal.ZERO) == 0) {
			max = syje;
		} else {
			max = max.subtract(tendered);
		}
		BigDecimal min = new BigDecimal(firstBorrow.getLowestAccount());
		BigDecimal inputMoney = money;

		BigDecimal returnValue = BigDecimal.ZERO;

		BigDecimal result1 = min;
		BigDecimal result2 = max;

		boolean isNoMoreMoney = false;
		if (max.compareTo(BigDecimal.ZERO) == 0) {
			result1 = BigDecimal.ZERO;
			result2 = BigDecimal.ZERO;
			isNoMoreMoney = true;
		}

		if (!isNoMoreMoney) {

			boolean isfinal = false;

			if (syje.compareTo(min) < 0) {
				isfinal = true;
			}

			if ((money.subtract(syje)).compareTo(new BigDecimal("0.01")) < 0) {
				isfinal = true;
			}

			if (result2.compareTo(BigDecimal.ZERO) < 0) {
				result2 = BigDecimal.ZERO;
				result1 = BigDecimal.ZERO;
			} else {
				if (result2.compareTo(BigDecimal.ZERO) == 0) {
					max = syje;
				}

				if (useMoney.compareTo(max) >= 0) {
					if (max.compareTo(syje) >= 0) {
						result2 = syje;
					} else if (max.compareTo(syje) < 0) {
						if (max.compareTo(min) < 0) {
							result1 = BigDecimal.ZERO;
							result2 = BigDecimal.ZERO;
						} else {
							result2 = max;
						}
					}
				} else if (useMoney.compareTo(max) < 0) {
					result2 = useMoney;
					if (useMoney.compareTo(min) >= 0) {
						if (useMoney.compareTo(syje) >= 0) {
							result2 = syje;
						} else if (useMoney.compareTo(syje) < 0) {
							result2 = useMoney;
						}
					} else if (useMoney.compareTo(min) < 0) {
						if (syje.compareTo(min) >= 0) {
							result1 = BigDecimal.ZERO;
							result2 = BigDecimal.ZERO;
						} else if (syje.compareTo(min) < 0) {
							isfinal = true;
							if (useMoney.compareTo(syje) >= 0) {
								result1 = syje;
								result2 = syje;
							} else if (useMoney.compareTo(syje) < 0) {
								result1 = BigDecimal.ZERO;
								result2 = BigDecimal.ZERO;
							}
						}
					}
				}
			}

			if (isfinal) {
				if (result1.compareTo(result2) > 0) {
					result1 = result2;
				}
			} else {
				if (result1.compareTo(result2) > 0) {
					result1 = BigDecimal.ZERO;
					result2 = BigDecimal.ZERO;
				}
			}

			if (isfinal) {
				result1 = result1.setScale(2, BigDecimal.ROUND_UP);
				result2 = result2.setScale(2, BigDecimal.ROUND_UP);
			}
			if (result1.compareTo(BigDecimal.ZERO) != 0 && result2.compareTo(BigDecimal.ZERO) != 0) {
				if (inputMoney.compareTo(result1) >= 0 && inputMoney.compareTo(result2) <= 0) {
					returnValue = inputMoney;
				}
			}

		}
		return returnValue;
	}

	@Override
	public BigDecimal getMaxeffectiveMoneyForTenderReal(FirstBorrowVo firstBorrow, Integer userId, BigDecimal money, AccountVo account)
			throws Exception {
		// 获取已投金额
		BigDecimal tendered = BigDecimal.ZERO;
		Integer accountTotal = firstTenderRealMapper.findAccountTotalByUserIdAndFirstBorrowId(firstBorrow.getId(), userId);
		if (accountTotal != null) {
			tendered = tendered.add(new BigDecimal(accountTotal));
		}
		// 标目前所剩余额
		BigDecimal syje = new BigDecimal(firstBorrow.getPlanAccount()).subtract(new BigDecimal(firstBorrow.getRealAccount()));

		BigDecimal useMoney = account.getUseMoney();
		// 如果可使用活期宝金额，则可用金额加上活期宝可转出最大金额
		if (firstBorrow.getIsUseCurMoney() != null && firstBorrow.getIsUseCurMoney().equals("1")) { // 可使用活期宝
			try {
				BigDecimal maxCurUseMoney = curAccountService.getMaxUseMoneyByNow(userId);
				useMoney = useMoney.add(maxCurUseMoney);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		BigDecimal max = new BigDecimal(firstBorrow.getMostAccount());
		// 用户可再投金额
		max = max.subtract(tendered);

		BigDecimal min = new BigDecimal(firstBorrow.getLowestAccount());

		BigDecimal result1 = min;
		BigDecimal result2 = max;
		// 是否还有可以投直通车金额
		boolean isNoMoreMoney = false;
		if (max.compareTo(BigDecimal.ZERO) == 0) {
			result1 = BigDecimal.ZERO;
			result2 = BigDecimal.ZERO;
			isNoMoreMoney = true;
		}

		if (!isNoMoreMoney) {
			boolean isfinal = false;
			if (syje.compareTo(min) < 0) {
				isfinal = true;
			}
			if ((money.subtract(syje)).compareTo(new BigDecimal("0.01")) < 0) {
				isfinal = true;
			}
			if (result2.compareTo(BigDecimal.ZERO) < 0) {
				result2 = BigDecimal.ZERO;
				result1 = BigDecimal.ZERO;
			} else {
				if (result2.compareTo(BigDecimal.ZERO) == 0) {
					max = syje;
				}

				if (useMoney.compareTo(max) >= 0) {
					if (max.compareTo(syje) >= 0) {
						result2 = syje;
					} else if (max.compareTo(syje) < 0) {
						if (max.compareTo(min) < 0) {
							result1 = BigDecimal.ZERO;
							result2 = BigDecimal.ZERO;
						} else {
							result2 = max;
						}
					}
				} else if (useMoney.compareTo(max) < 0) {
					result2 = useMoney;
					if (useMoney.compareTo(min) >= 0) {
						if (useMoney.compareTo(syje) >= 0) {
							result2 = syje;
						} else if (useMoney.compareTo(syje) < 0) {
							result2 = useMoney;
						}
					} else if (useMoney.compareTo(min) < 0) {
						if (syje.compareTo(min) >= 0) {
							result1 = BigDecimal.ZERO;
							result2 = BigDecimal.ZERO;
						} else if (syje.compareTo(min) < 0) {
							isfinal = true;
							if (useMoney.compareTo(syje) >= 0) {
								result1 = syje;
								result2 = syje;
							} else if (useMoney.compareTo(syje) < 0) {
								result1 = BigDecimal.ZERO;
								result2 = BigDecimal.ZERO;
							}
						}
					}
				}

			}

			if (isfinal) {
				if (result1.compareTo(result2) > 0) {
					result1 = result2;
				}
			} else {
				if (result1.compareTo(result2) > 0) {
					result1 = BigDecimal.ZERO;
					result2 = BigDecimal.ZERO;
				}
			}

			// if(isfinal){
			result1 = result1.setScale(2, BigDecimal.ROUND_DOWN);
			result2 = result2.setScale(2, BigDecimal.ROUND_DOWN);
			// }

		}
		return result2;
	}

	@Override
	public Integer seletAccountTotalByUserId(Integer userId) throws Exception {
		return firstTenderDetailMapper.seletAccountTotalByUserId(userId);
	}
}
