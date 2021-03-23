package com.dxjr.portal.borrow.service.impl;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import javax.jws.WebService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dxjr.common.page.Page;
import com.dxjr.portal.account.service.AccountService;
import com.dxjr.portal.account.vo.AccountVo;
import com.dxjr.portal.borrow.entity.TenderRecord;
import com.dxjr.portal.borrow.mapper.TenderRecordMapper;
import com.dxjr.portal.borrow.service.BorrowService;
import com.dxjr.portal.borrow.service.TendRecordService;
import com.dxjr.portal.borrow.vo.BorrowVo;
import com.dxjr.portal.borrow.vo.TenderRecordVo;
import com.dxjr.portal.curAccount.service.CurAccountService;
import com.dxjr.portal.tender.vo.BTenderRecordCnd;
import com.dxjr.portal.tender.vo.BTenderRecordVo;

@Service
@WebService
public class TendRecordServiceImpl implements TendRecordService {

	@Autowired
	private TenderRecordMapper tenderRecordMapper;

	@Autowired
	private BorrowService borrowService;

	@Autowired
	private AccountService accountService;

	@Autowired
	private CurAccountService curAccountService;

	/**
	 * 
	 * <p>
	 * Description:得到最大有效金额<br />
	 * </p>
	 * 
	 * @author zhanghao
	 * @version 0.1 2014年5月23日
	 * @param borrow
	 * @param user
	 * @param money
	 * @param account
	 * @return BigDecimal
	 */
	public BigDecimal getMaxeffectiveMoney(BorrowVo borrow, Integer userId, BigDecimal money, AccountVo account) {
		List<TenderRecord> list = tenderRecordMapper.selectByUserIdAndBorrowId(userId, borrow.getId());
		BigDecimal tendered = BigDecimal.ZERO;
		for (TenderRecord btr : list) {
			tendered = tendered.add(btr.getAccount());
		}
		// 标目前所剩余额
		BigDecimal syje = borrow.getAccount().subtract(borrow.getAccountYes());
		BigDecimal useMoney = account.getUseMoney();
		// 如果可使用活期宝金额，则可用金额加上活期宝可转出最大金额
		if (borrow.getIsUseCurMoney() != null && borrow.getIsUseCurMoney().equals("1")) { // 可使用活期宝
			try {
				BigDecimal maxCurUseMoney = curAccountService.getMaxUseMoneyByNow(userId);
				useMoney = useMoney.add(maxCurUseMoney);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		BigDecimal max = borrow.getMostAccount();
		if (max.compareTo(BigDecimal.ZERO) == 0) {
			max = syje;
		} else {
			max = max.subtract(tendered);
		}

		BigDecimal min = borrow.getLowestAccount();
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

			// if(isfinal){
			result1 = result1.setScale(2, BigDecimal.ROUND_DOWN);
			result2 = result2.setScale(2, BigDecimal.ROUND_DOWN);
			// }
			System.out.println("最小=" + result1);
			System.out.println("最大=" + result2);

			if (result1.compareTo(BigDecimal.ZERO) != 0 && result2.compareTo(BigDecimal.ZERO) != 0) {
				if (inputMoney.compareTo(result1) >= 0 && inputMoney.compareTo(result2) <= 0) {
					returnValue = inputMoney;
				}
			}
		}

		return result2;
	}
	
	
	
	
	/**
	 * 
	 * <p>
	 * Description:得到最大有效金额<br />
	 * </p>
	 * 
	 * @author zhanghao
	 * @version 0.1 2014年5月23日
	 * @param borrow
	 * @param user
	 * @param money
	 * @param account
	 * @return BigDecimal
	 */
	public BigDecimal getCGMaxeffectiveMoney(BorrowVo borrow, Integer userId, BigDecimal money, AccountVo account) {
		List<TenderRecord> list = tenderRecordMapper.selectByUserIdAndBorrowId(userId, borrow.getId());
		BigDecimal tendered = BigDecimal.ZERO;
		for (TenderRecord btr : list) {
			tendered = tendered.add(btr.getAccount());
		}
		// 标目前所剩余额
		BigDecimal syje = borrow.getAccount().subtract(borrow.getAccountYes());
		BigDecimal useMoney = account.geteUseMoney();
		
		
		BigDecimal max = borrow.getMostAccount();
		if (max.compareTo(BigDecimal.ZERO) == 0) {
			max = syje;
		} else {
			max = max.subtract(tendered);
		}

		BigDecimal min = borrow.getLowestAccount();
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

			// if(isfinal){
			result1 = result1.setScale(2, BigDecimal.ROUND_DOWN);
			result2 = result2.setScale(2, BigDecimal.ROUND_DOWN);
			// }
			System.out.println("最小=" + result1);
			System.out.println("最大=" + result2);

			if (result1.compareTo(BigDecimal.ZERO) != 0 && result2.compareTo(BigDecimal.ZERO) != 0) {
				if (inputMoney.compareTo(result1) >= 0 && inputMoney.compareTo(result2) <= 0) {
					returnValue = inputMoney;
				}
			}
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
	public BigDecimal isEffectiveMoney(BorrowVo borrow, Integer userId, BigDecimal money, AccountVo account) {
		List<TenderRecord> list = tenderRecordMapper.selectByUserIdAndBorrowId(userId, borrow.getId());
		BigDecimal tendered = BigDecimal.ZERO;
		for (TenderRecord btr : list) {
			tendered = tendered.add(btr.getAccount());
		}
		System.out.println("list.size()=" + list.size());

		System.out.println("tendered=" + tendered);
		// 标目前所剩余额
		BigDecimal syje = borrow.getAccount().subtract(borrow.getAccountYes());
		BigDecimal useMoney = account.getUseMoney();
		// 如果可使用活期宝金额，则可用金额加上活期宝可转出最大金额
		if (borrow.getIsUseCurMoney() != null && borrow.getIsUseCurMoney().equals("1")) {
			try {
				BigDecimal maxCurUseMoney = curAccountService.getMaxUseMoneyByNow(userId);
				useMoney = useMoney.add(maxCurUseMoney);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		BigDecimal max = borrow.getMostAccount();
		if (max.compareTo(BigDecimal.ZERO) == 0) {
			max = syje;
		} else {
			max = max.subtract(tendered);
		}
		BigDecimal min = borrow.getLowestAccount();
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
			System.out.println("最小=" + result1);
			System.out.println("最大=" + result2);
			if (result1.compareTo(BigDecimal.ZERO) != 0 && result2.compareTo(BigDecimal.ZERO) != 0) {
				if (inputMoney.compareTo(result1) >= 0 && inputMoney.compareTo(result2) <= 0) {
					returnValue = inputMoney;
				}
			}
		}
		return returnValue;
	}

	@Override
	public List<TenderRecord> selectByUserIdAndBorrowId(Integer userId, Integer borrowId) {
		return tenderRecordMapper.selectByUserIdAndBorrowId(userId, borrowId);
	}

	@Override
	public TenderRecord queryTenderRecordForAutoTender(int borrowId, int userId, Integer autotenderOrder) {
		return tenderRecordMapper.queryTenderRecordForAutoTender(userId, borrowId, autotenderOrder);
	}

	@Override
	public int updateByPrimaryKeySelective(TenderRecord record) {
		// TODO Auto-generated method stub
		return tenderRecordMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public Page queryTenderRecordByBorrowId(Integer borrowId, Page p) throws Exception {
		Integer totalCount = tenderRecordMapper.queryTenderRecordCountByBorrowId(borrowId);
		p.setTotalCount(totalCount);
		List<TenderRecordVo> list = tenderRecordMapper.queryTenderRecordPage(borrowId, p);
		p.setResult(list);
		return p;
	}

	public int queryTenderRecordCountByBorrowId(Map<String, Object> map) throws Exception {
		int borrowId = Integer.parseInt(map.get("borrowId").toString());
		return tenderRecordMapper.queryTenderRecordCountByBorrowId(borrowId);
	}

	@Override
	public BigDecimal getEffectMoney(Integer borrowId, Integer userId, BigDecimal money) throws Exception {
		return tenderRecordMapper.getEffectMoney(borrowId, userId, money);
	}

	@Override
	public BigDecimal getTenderAccountYesByBorrowId(Integer borrowId) throws Exception {
		return tenderRecordMapper.getTenderAccountYesByBorrowId(borrowId);
	}

	@Override
	public List<BTenderRecordVo> queryTenderListByCnd(BTenderRecordCnd bTenderRecordCnd) throws Exception {
		return tenderRecordMapper.queryTenderListByCnd(bTenderRecordCnd);
	}

	@Override
	public Integer getTenderRecordCountByUserId(Integer userId) throws Exception {
		return tenderRecordMapper.getTenderRecordCountByUserId(userId);
	}

	@Override
	public Integer getTenderSuccessCountByUserId(Integer userId) throws Exception {
		return tenderRecordMapper.getTenderSuccessCountByUserId(userId);
	}

	@Override
	public Integer getTenderPowderCountByUserId(Integer userId) throws Exception {
		return tenderRecordMapper.getTenderPowderCountByUserId(userId);
	}

	@Override
	public TenderRecordVo queryTenderRecordLastByUserIdAndAccount(Integer borrowId, Integer userId, BigDecimal account) throws Exception {
		return tenderRecordMapper.queryTenderRecordLastByUserIdAndAccount(borrowId, userId, account);
	}
	
	@Override
	public List<TenderRecord> queryTenderBorrow(Integer realId) {
		// TODO Auto-generated method stub
		return tenderRecordMapper.queryTenderBorrow(realId);
	}
}
