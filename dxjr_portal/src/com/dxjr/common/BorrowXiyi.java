package com.dxjr.common;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.dxjr.base.entity.Borrow;
import com.dxjr.portal.borrow.vo.TenderRecordVo;
import com.dxjr.utils.DateUtils;
import com.dxjr.utils.MoneyUtil;

/**
 * 
 * @author Rocky.J
 * @date 2013-5-16
 */
public final class BorrowXiyi {

	/**
	 * 获取非推荐标的借款标协议.
	 * 
	 * @param request
	 * @param borrow
	 * @param list
	 * @param userName
	 * @return
	 */
	public static String getBorrowXiyi(HttpServletRequest request, Borrow borrow, List<TenderRecordVo> list, String userName) {
		if (null != borrow && borrow.getBorrowtype() == 1) { // 推荐标
			return getBorrowXiyiForTJ(request, borrow, list, userName);
		} else if (null == borrow) {
			return "查询协议失败！";
		}
		// 借款期限
		String time_limit = "";
		if (borrow.getBorrowtype() == 4) {
			time_limit = "秒还";
		} else {
			if (borrow.getStyle() == 4) {
				time_limit = borrow.getTimeLimit() + "天";
			} else {
				time_limit = borrow.getTimeLimit() + "个月";
			}
		}
		// 还款方式
		String style = "";
		if (borrow.getStyle() == 1) {
			style = "等额本息";
		} else if (borrow.getStyle() == 2) {
			style = "按月付息到期还本";
		} else if (borrow.getStyle() == 3) {
			style = "到期还本付息";
		} else if (borrow.getStyle() == 4) {
			style = "按天到期还款";
		}
		// 到期日
		String endDateStr = "";
		String endDateStr2 = "";
		if (borrow.getStyle() == 4) {
			Date success_date = DateUtils.parse(DateUtils.timeStampToDate(borrow.getSuccessTime(), DateUtils.YMD_DASH), DateUtils.YMD_DASH);
			Date endDate = DateUtils.dayOffset(success_date, borrow.getTimeLimit());
			endDateStr = DateUtils.format(endDate, DateUtils.YMD_DASH);
			endDateStr2 = DateUtils.format(endDate, DateUtils.YMD_NYRSH);
		} else {
			Date success_date = DateUtils.parse(DateUtils.timeStampToDate(borrow.getSuccessTime(), DateUtils.YMD_DASH), DateUtils.YMD_DASH);
			Date endDate = DateUtils.monthOffset(success_date, borrow.getTimeLimit());
			endDateStr = DateUtils.format(endDate, DateUtils.YMD_DASH);
			endDateStr2 = DateUtils.format(endDate, DateUtils.YMD_NYRSH);
		}
		BigDecimal tenderAccountTotal = BigDecimal.ZERO;
		BigDecimal repaymentAccountTotal = BigDecimal.ZERO;
		StringBuilder sb = new StringBuilder();
		sb.append("<div align='center' style='font-size:15.5pt;font-family:'宋体';font-weight:bold;'>借款协议</div><br/>");
		sb.append("<div align='center'><span style='font-size:14pt;font-family:'arial''>借款协议号</span>：<font color='red'>" + borrow.getContractNo()
				+ "</font> &nbsp;&nbsp;<span style='font-size:14pt;font-family:'arial''>签订日期</span>：<font color='red'>" + DateUtils.timeStampToDate(borrow.getSuccessTime(), DateUtils.YMD_DASH)
				+ "</font></div><br/>");
		sb.append("<div style='margin-left: 50px;'>");

		sb.append("<span style='line-height:20px'><span style='font-weight:bold;line-height:20px;font-size: 12px;'>甲方（出借人）：</span><br/>");

		sb.append("<table width='100%' border='1' cellpadding='0' cellspacing='0' bordercolor='#000000'>"
				+ "<tr style='font-weight:bold;font-size: 12px;' height='20px' align='center'><td width='15%'>&nbsp;出借人（网站ID）</td><td width='15%'>借出金额</td><td width='15%'>借款期限</td><td width='10%'>年化利率</td><td width='15%'>还款方式</td>");
		if (borrow.getStyle() == 1) { // 等额本息
			sb.append("<td width='15%'>结清还款日</td><td width='15%'>每月应还本息</td></tr>");
		} else {
			sb.append("<td width='15%'>到期还款日</td><td width='15%'>到期应收本息</td></tr>");
		}

		for (TenderRecordVo tenderRecordVo : list) {
			tenderAccountTotal = tenderAccountTotal.add(tenderRecordVo.getAccount().setScale(2, BigDecimal.ROUND_DOWN));
			repaymentAccountTotal = repaymentAccountTotal.add(tenderRecordVo.getRepaymentAccount().setScale(2, BigDecimal.ROUND_DOWN));
			sb.append("<tr style='font-size: 12px;color:red;' align='center' height='20px'><td  style='border-color:#000;'>" + tenderRecordVo.getUsername() + "</td><td  style='border-color:#000;'>￥"
					+ MoneyUtil.insertComma(String.valueOf(tenderRecordVo.getAccount().setScale(2, BigDecimal.ROUND_DOWN))) + "</td><td  style='border-color:#000;'>" + time_limit
					+ "</td><td  style='border-color:#000;'>" + borrow.getApr() + "%</td><td  style='border-color:#000;'>" + style + "</td><td>" + endDateStr + "</td>");

			if (borrow.getStyle() == 1) { // 等额本息
				sb.append("<td  style='border-color:#000;'>￥"
						+ MoneyUtil.insertComma(String.valueOf(tenderRecordVo.getRepaymentAccount().divide(new BigDecimal(String.valueOf(borrow.getTimeLimit())), 2, BigDecimal.ROUND_DOWN)))
						+ "</td></tr>");
			} else {
				sb.append("<td  style='border-color:#000;'>￥" + MoneyUtil.insertComma(String.valueOf(tenderRecordVo.getRepaymentAccount().setScale(2, BigDecimal.ROUND_DOWN))) + "</td></tr>");
			}
		}
		sb.append("<tr style='font-size: 12px;color:red;' align='center' height='20px'><td  style='border-color:#000;'>&nbsp;总计</td><td style='border-color:#000;'>￥"
				+ MoneyUtil.insertComma(String.valueOf(tenderAccountTotal.setScale(2, BigDecimal.ROUND_DOWN)))
				+ "</td><td  style='border-color:#000;'></td><td style='border-color:#000;'></td><td style='border-color:#000;'></td><td style='border-color:#000;'></td><td style='border-color:#000;'>￥"
				+ MoneyUtil.insertComma(String.valueOf(repaymentAccountTotal.setScale(2, BigDecimal.ROUND_DOWN))) + "</td></tr>");
		sb.append("</table><br/>");

		sb.append("<span style='font-size: 12px;'>");
		sb.append("<span style='font-weight:bold;font-size: 12px;line-height:20px;'>乙方（借款人）：顶玺金融用户名：<font color='red'>" + userName + "</font><br/>");
		sb.append("丙方（见证人）：上海顶玺金融信息服务有限公司（联系地址：上海市普陀区华池路58弄新体育3楼）<br/>");
		sb.append("鉴于</span>：<br/>");
		sb.append("1、丙方是一家在上海合法成立并有效存续的有限责任公司，拥有www.dxjr.com 网站（以下简称“该网站”）的经营权，该公司具有提供金融信息服务、数据处理服务及投资咨询服务等；<br/>");
		sb.append("2、乙方为该网站的注册会员，并承诺其提供给丙方的信息是完全真实的；<br/>");
		sb.append("3、甲方承诺对本协议涉及的借款资金具有完全的支配能力，是其自有闲散资金，为其合法所得；并承诺其提供给丙方的信息是完全真实的；<br/>");
		sb.append("4、乙方有借款需求，甲方亦同意借款，双方有意成立借贷关系。<br/>");
		sb.append("<span style='font-weight:bold;font-size: 12px;line-height:20px;'>各方经协商一致，于<font color='red'> " + DateUtils.timeStampToDate(borrow.getSuccessTime(), DateUtils.YMD_DASH)
				+ "</font> 签订如下协议，共同遵照履行</span>：<br/>");
		sb.append("<span style='font-weight:bold;font-size: 12px;line-height:20px;'>第一条 借款基本信息</span><br/>");

		sb.append("<table width='80%' border='1' cellpadding='0' cellspacing='0' bordercolor='#000000'>"
				+ "<tr style='font-size: 12px;' align='center' height='20px'><td wdith='30%'>&nbsp;借款标题</td><td wdith='70%' style='color:red;border-color:#000;'>" + borrow.getName()
				+ "（各出借人借款本金数额详见本协议文首表格）</td></tr>" + "<tr style='font-size: 12px;' align='center' height='20px'><td>&nbsp;借款本金数额</td><td style='color:red;border-color:#000;'>"
				+ MoneyUtil.insertComma(String.valueOf(borrow.getAccount().setScale(2, BigDecimal.ROUND_DOWN))) + "</td></tr>");
		if (borrow.getStyle() == 1) { // 等额本息
			sb.append("<tr style='font-size: 12px;' align='center'><td>&nbsp;每月应还本息</td><td style='color:red;border-color:#000;'>"
					+ MoneyUtil.insertComma(String.valueOf(repaymentAccountTotal.divide(new BigDecimal(String.valueOf(borrow.getTimeLimit())), 2, BigDecimal.ROUND_DOWN))) + "</td></tr>");
		} else {
			sb.append("<tr style='font-size: 12px;' align='center'><td>&nbsp;到期偿还本息金额</td><td style='color:red;border-color:#000;'>"
					+ MoneyUtil.insertComma(String.valueOf(repaymentAccountTotal.setScale(2, BigDecimal.ROUND_DOWN))) + "</td></tr>");
		}
		sb.append("<tr style='font-size: 12px;' align='center' height='20px'><td>&nbsp;还款期限</td><td style='color:red;border-color:#000;'>" + time_limit + "</td></tr>");
		if (borrow.getStyle() == 1) { // 等额本息
			sb.append("<tr style='font-size: 12px;' align='center' height='20px'><td>&nbsp;结清还款日</td><td style='color:red;border-color:#000;'>" + endDateStr + "</td></tr>");
		} else {
			sb.append("<tr style='font-size: 12px;' align='center' height='20px'><td>&nbsp;还款日</td><td style='color:red;border-color:#000;'>" + endDateStr + "</td></tr>");
		}
		sb.append("</table><br/>");

		sb.append("<span style='font-weight:bold;font-size: 12px;line-height:20px;'>第二条 各方权利和义务</span><br/>");
		sb.append("1、 甲方应按合同约定的借款期限起始日期将足额的借款本金支付给乙方；<br/>");
		sb.append("2、 甲方享有其所出借款项所带来的利息收益；<br/>");
		sb.append("3、 如乙方违约，甲方有权要求丙方提供其已获得的乙方信息，丙方应当提供；<br/>");
		sb.append("4、 甲方应主动缴纳由利息收益所需要交纳的税费；<br/>");
		sb.append("5、甲方应根据其会员等级向丙方支付一定利息管理费，该费用在甲方获得乙方利息时自动扣除，<br/>");
		sb.append("具体收费列表如下：<br/>");
		sb.append("普通会员收取10%的利息管理费<br/>");
		sb.append("金牌会员收取9%的利息管理费<br/>");
		sb.append("白金会员收取8%的利息管理费<br/>");
		sb.append("钻石会员收取7%的利息管理费<br/>");
		sb.append("皇冠会员收取6%的利息管理费<br/>");
		sb.append("金皇冠会员收取5%的利息管理费<br/>");
		sb.append("<span style='font-weight:bold;font-size: 12px;line-height:20px;'><u>乙方权利和义务</u></span><br/>");
		sb.append("1、 乙方必须按期足额向甲方偿还本金和利息；<br/>");
		sb.append("2、 乙方必须按期足额向丙方支付借款管理费用；<br/>");
		sb.append("3、 乙方承诺所借款项不用于任何违法用途；<br/>");
		sb.append("4、 乙方应确保其提供的信息和资料的真实性，不得提供虚假信息或隐瞒重要事实；<br/>");
		sb.append("5、 乙方有权了解其在丙方的抵押资料评审进度及结果；<br/>");
		sb.append("6、 乙方不得将本协议项下的任何权利义务转让给任何其他个人或企业。<br/>");
		sb.append("<span style='font-weight:bold;font-size: 12px;line-height:20px;'><u>丙方的权利和义务</u></span><br/>");
		sb.append("1、甲乙双方同意丙方有权代甲方每期收取甲方出借款项所对应的乙方每期偿还的本息，代收后按照甲方的要求进行处置。<br/>");
		sb.append("2、甲方同意向乙方出借相应款项时，已委托丙方在本协议生效时将该笔借款直接划付至乙方账户。<br/>");
		sb.append("3、甲乙双方同意丙方有权代甲方在有必要时对乙方进行其所借款的违约提醒及催收工作，包括但不限于电话通知、发律师函、对乙方提起诉讼等。甲方在此确认明确委托丙方为其进行以上工作，并授权丙方可以将此工作委托给其他方进行。乙方对前述委托的提醒、催收事项已明确知晓并应积极配合。<br/>");
		sb.append("4、丙方有权按月向乙方收取双方约定的借款管理费，并在有必要时对乙方进行违约提醒及催收工作，包括但不限于电话通知、发律师函、对乙方提起诉讼等。 丙方有权将此违约提醒及催收工作委托给本协议外的其他方进行。<br/>");
		sb.append("5、丙方接受甲乙双方的委托行为所产生的法律后果由相应委托方承担。如因乙方或甲方或其他方（包括但不限于技术问题）造成的延误或错误，丙方不承担任何责任。<br/>");
		sb.append("6、丙方应对甲方和乙方的信息及本协议内容保密；如任何一方违约，或因相关权力部门要求（包括但不限于法院、仲裁机构、金融监管机构等），丙方有权披露。<br/>");
		sb.append("7、丙方根据本协议对乙方进行违约提醒及催收工作时，可在其认为必要时进行上门催收提醒，即丙方派出人员（至少2名）至乙方披露的住所地或经常居住地（联系地址）处催收和进行违约提醒，同时向乙方发送催收通知单，乙方应当签收，乙方不签收的，不影响上门催收提醒的进行。丙方采取上门催收提醒的，乙方应当向丙方支付上门提醒费用，收费标准为每次人民币1000.00元，此外，乙方还应向丙方支付进行上门催收提醒服务的差旅费（包括但不限于交通费、食宿费等）。<br/>");
		sb.append("<span style='font-weight:bold;font-size: 12px;line-height:20px;'>第三条 借款管理费及居间服务费</span><br/>");
		sb.append("1、 在本协议中，“借款管理费”和“居间服务费”是指因丙方为乙方提供资产咨询、评估、还款提醒、账户管理、还款特殊情况沟通、本金保障等系列资产评估相关服务（统称“资产评估服务”）而由乙方支付给丙方的报酬。<br/>");
		sb.append("2、 对于丙方向乙方提供的一系列资产评估服务，乙方同意在借款成功时向丙方支付本协议第一条约定借款本金总额的一定比例作为居间服务费，该“居间服务费”由乙方授权并委托丙方在丙方根据本协议规定的“丙方的权利和义务”第2款规定向乙方划付出借本金时从本金中予以扣除，即视为乙方已缴纳。<br/>");
		sb.append("本条所称的“借款成功时”系指本协议签署日。<br/>");
		sb.append("3、如乙方和丙方协商一致调整借款管理费和居间服务费时，无需经过甲方同意。<br/>");
		sb.append("<span style='font-weight:bold;font-size: 12px;line-height:20px;'>第四条 违约责任</span><br/>");
		sb.append("1、协议各方均应严格履行合同义务，非经各方协商一致或依照本协议约定，任何一方不得解除本协议。<br/>");
		sb.append("2、任何一方违约，违约方应承担因违约使得其他各方产生的费用和损失，包括但不限于调查、诉讼费、律师费等，应由违约方承担。如违约方为乙方的，甲方有权立即解除本协议，并要求乙方立即偿还未偿还的本金、利息、罚息、违约金。此时，乙方还应向丙方支付所有应付的借款管理费。如本协议提前解除时，乙方在www.dxjr.com网站的账户里有任何余款的，丙方有权按照本协议第四条第3项的清偿顺序将乙方的余款用于清偿，并要求乙方支付因此产生的相关费用。<br/>");
		sb.append("3、乙方的每期还款均应按照如下顺序清偿：<br/>");
		sb.append("（1）根据本协议产生的其他全部费用；<br/>");
		sb.append("（2）逾期罚息<br/>");
		sb.append("（3）逾期借款管理费<br/>");
		sb.append("（4）拖欠的利息；<br/>");
		sb.append("（5）拖欠的本金；<br/>");
		sb.append("4、乙方应严格履行还款义务，如乙方逾期还款，则应按照下述条款向甲方支付逾期罚息，<br/>");
		sb.append("罚息总额 =( 逾期本息总额+逾期借款管理费)×罚息利率0.2%×逾期天数；<br/>");
		sb.append("出借人和借款人均同意，如借款人逾期 (抵押标第10天垫付，推荐标第10天垫付，净值标第3天垫付。具体请参考网站最新垫付规则：https://www.dxjr.com/safe/index.html)在网站垫付前仍未清偿借款本息,则出借人将本协议项下的债权转让给本网站合作商；转让价格为不超过借款人逾期仍未偿还的借款本息（具体金额视出借人在本网站的会员级别不同而有所不同），本网站合作商向出借人支付借款人逾期仍未偿还的本金即可取得出借人在本协议项下的债权；本网站合作商将依法自行承担清收费用，向借款人追收借款本金、利息、逾期罚息等，坏账风险由本网站合作商承担。但是，本网站合作商作为独立的营利性组织机构在查实借入者和出借者合伙诈骗的情况下可拒绝受让出借人在本协议项下的债权，本网站将不遗余力追究相关人员的法律责任。<br/>");
		sb.append(" 5、本网站对逾期仍未还款的借款人收取逾期罚息作为催收费用、采取多种方式进行催收、将借款人的相关信息对外公开或列入“不良信用记录”或采取法律措施等各项行为，均只是本网站根据本协议为出借人提供的一种服务，该等服务的法律后果均由借款人和出借人自行承担，并不表示本网站对该借款本息之清偿承担任何担保责任或追收责任。<br/>");
		sb.append("<span style='font-weight:bold;font-size: 12px;line-height:20px;'>第五条 提前还款</span><br/>");
		sb.append("1、双方同意,若借款人出现如下任何一种情况,则本协议项下的全部借款自动提前到期,借款人在收到本网站发出的借款提前到期通知后，应立即清偿全部本金、利息、逾期利息及根据本协议产生的其他全部费用：  <br/>");
		sb.append("(1)借款人因任何原因逾期支付任何一期还款超过30天的；<br/>");
		sb.append("(2)借款人的工作单位、职务或住所变更后，未在30天内通知本网站；<br/>");
		sb.append("(3)借款人发生影响其清偿本协议项下借款的其他不利变化，未在30天内通知本网站。<br/>");
		sb.append("2、双方同意,借款人有权提前清偿全部借款而不承担任何的违约责任(借款超过1日不足1个月者利息按足月计算)。<br/>");
		sb.append("3、本借款协议中的每一出借人与借款人之间的借款均是相互独立的,一旦借款人逾期未归还借款本息,任何一出借人有权单独对该出借人未收回的借款本息向借款人追索或者提起诉讼。<br/>");
		sb.append("4、任何形式的提前还款不影响丙方向乙方收取在本协议第三条中说明的居间服务费。<br/>");
		sb.append("<span style='font-weight:bold;font-size: 12px;line-height:20px;'>第六条 法律及争议解决</span><br/>");
		sb.append("本协议的签订、履行、终止、解释均适用中华人民共和国法律，如因本协议发生争议，甲、乙、丙三方一致同意由丙方所在地上海虹口区人民法院管辖。<br/>");
		sb.append("<span style='font-weight:bold;font-size: 12px;line-height:20px;'>第七条 附则</span><br/>");
		sb.append("1、本协议采用电子文本形式制成，并永久保存在丙方为此设立的专用服务器上备查，各方均认可该形式的协议效力。<br/>");
		sb.append("2、本协议自文本最终生成之日生效。<br/>");
		sb.append("3、本协议签订之日起至借款全部清偿之日止，乙方或甲方有义务在下列信息变更三日内提供更新后的信息给丙方：本人、本人的家庭联系人及紧急联系人、工作单位、居住地址、住所电话、手机号码、电子邮箱、银行账户的变更。若因任何一方不及时提供上述变更信息而带来的损失或额外费用应由该方承担。<br/>");
		sb.append("4、如果本协议中的任何一条或多条违反适用的法律法规，则该条将被视为无效，但该无效条款并不影响本协议其他条款的效力。<br/></span>");
		sb.append("</span>");
		if (request != null) {
			String path = request.getContextPath();
			String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
			sb.append("<div align='right'><img src='" + basePath + "/images/CompanyElectronicChapter.gif'/></div>");
		} else {
			sb.append("<div align='right'><img src='https://www.dxjr.com/images/CompanyElectronicChapter.gif'/></div>");
		}
		sb.append("</div>");
		return sb.toString();
	}

	/**
	 * 
	 * <p>
	 * Description:推荐标借款协议<br />
	 * </p>
	 * 
	 * @author yangshijin
	 * @version 0.1 2014年4月24日
	 * @param request
	 * @param borrow
	 * @param list
	 * @param userName
	 * @return String
	 */
	public static String getBorrowXiyiForTJ(HttpServletRequest request, Borrow borrow, List<TenderRecordVo> list, String userName) {
		// 借款期限
		String time_limit = "";
		if (borrow.getBorrowtype() == 4) {
			time_limit = "秒还";
		} else {
			if (borrow.getStyle() == 4) {
				time_limit = borrow.getTimeLimit() + "天";
			} else {
				time_limit = borrow.getTimeLimit() + "个月";
			}
		}
		// 还款方式
		String style = "";
		if (borrow.getStyle() == 1) {
			style = "等额本息";
		} else if (borrow.getStyle() == 2) {
			style = "按月付息到期还本";
		} else if (borrow.getStyle() == 3) {
			style = "到期还本付息";
		} else if (borrow.getStyle() == 4) {
			style = "按天到期还款";
		}
		// 到期日
		String endDateStr = "";
		String endDateStr2 = "";
		if (borrow.getStyle() == 4) {
			Date success_date = DateUtils.parse(DateUtils.timeStampToDate(borrow.getSuccessTime(), DateUtils.YMD_DASH), DateUtils.YMD_DASH);
			Date endDate = DateUtils.dayOffset(success_date, borrow.getTimeLimit());
			endDateStr = DateUtils.format(endDate, DateUtils.YMD_DASH);
			endDateStr2 = DateUtils.format(endDate, DateUtils.YMD_NYRSH);
		} else {
			Date success_date = DateUtils.parse(DateUtils.timeStampToDate(borrow.getSuccessTime(), DateUtils.YMD_DASH), DateUtils.YMD_DASH);
			Date endDate = DateUtils.monthOffset(success_date, borrow.getTimeLimit());
			endDateStr = DateUtils.format(endDate, DateUtils.YMD_DASH);
			endDateStr2 = DateUtils.format(endDate, DateUtils.YMD_NYRSH);
		}
		BigDecimal tenderAccountTotal = BigDecimal.ZERO;
		BigDecimal repaymentAccountTotal = BigDecimal.ZERO;
		StringBuilder sb = new StringBuilder();
		sb.append("<div align='center' style='font-size:15.5pt;font-family:'宋体';font-weight:bold;'>推荐标借款协议</div><br/>");
		sb.append("<div align='center'><span style='font-size:14pt;font-family:'arial''>借款协议号</span>：<font color='red'>" + borrow.getContractNo()
				+ "</font> &nbsp;&nbsp;<span style='font-size:14pt;font-family:'arial''>签订日期</span>：<font color='red'>" + DateUtils.timeStampToDate(borrow.getSuccessTime(), DateUtils.YMD_DASH)
				+ "</font></div><br/>");
		sb.append("<div style='margin-left: 50px;'>");

		sb.append("<span style='line-height:20px'><span style='font-weight:bold;line-height:20px;'>甲方（出借人）：</span><br/>");

		sb.append("<table width='100%' border='1' cellpadding='0' cellspacing='0' bordercolor='#000000'>"
				+ "<tr style='font-weight:bold;font-size: 12px;' height='20px' align='center'><td width='15%'>&nbsp;出借人（网站ID）</td><td width='15%'>借出金额</td><td width='15%'>借款期限</td><td width='10%'>年化利率</td><td width='15%'>还款方式</td>");
		if (borrow.getStyle() == 1) { // 等额本息
			sb.append("<td width='15%'>结清还款日</td><td width='15%'>每月应还本息</td></tr>");
		} else {
			sb.append("<td width='15%'>到期还款日</td><td width='15%'>到期应收本息</td></tr>");
		}

		for (TenderRecordVo tenderRecordVo : list) {
			tenderAccountTotal = tenderAccountTotal.add(tenderRecordVo.getAccount().setScale(2, BigDecimal.ROUND_DOWN));
			repaymentAccountTotal = repaymentAccountTotal.add(tenderRecordVo.getRepaymentAccount().setScale(2, BigDecimal.ROUND_DOWN));
			sb.append("<tr style='font-size: 12px;color:red;' align='center' height='20px'><td style='border-color:#000;'>" + tenderRecordVo.getUsername() + "</td><td style='border-color:#000;'>￥"
					+ MoneyUtil.insertComma(String.valueOf(tenderRecordVo.getAccount().setScale(2, BigDecimal.ROUND_DOWN))) + "</td><td style='border-color:#000;'>" + time_limit
					+ "</td><td style='border-color:#000;'>" + borrow.getApr() + "%</td><td style='border-color:#000;'>" + style + "</td><td>" + endDateStr + "</td>");

			if (borrow.getStyle() == 1) { // 等额本息
				sb.append("<td style='border-color:#000;'>￥"
						+ MoneyUtil.insertComma(String.valueOf(tenderRecordVo.getRepaymentAccount().divide(new BigDecimal(String.valueOf(borrow.getTimeLimit())), 2, BigDecimal.ROUND_DOWN)))
						+ "</td></tr>");
			} else {
				sb.append("<td style='border-color:#000;'>￥" + MoneyUtil.insertComma(String.valueOf(tenderRecordVo.getRepaymentAccount().setScale(2, BigDecimal.ROUND_DOWN))) + "</td></tr>");
			}
		}
		sb.append("<tr style='font-size: 12px;color:red;' align='center' height='20px'><td style='border-color:#000;'>&nbsp;总计</td><td style='border-color:#000;'>￥"
				+ MoneyUtil.insertComma(String.valueOf(tenderAccountTotal.setScale(2, BigDecimal.ROUND_DOWN)))
				+ "</td><td style='border-color:#000;'></td><td style='border-color:#000;'></td><td style='border-color:#000;'></td style='border-color:#000;'><td style='border-color:#000;'></td><td style='border-color:#000;'>￥"
				+ MoneyUtil.insertComma(String.valueOf(repaymentAccountTotal.setScale(2, BigDecimal.ROUND_DOWN))) + "</td></tr>");
		sb.append("</table><br/>");

		sb.append("<span style='font-size: 12px;'>");
		if (Long.parseLong(borrow.getSuccessTime()) < Long.parseLong(DateUtils.date2TimeStamp("2014-07-05"))) {
			sb.append("备注  1：如果借款期限为6个月，利率也为年化22.4% <br/>");
		} else {
			sb.append("备注  1：如果借款期限为6个月，利率也为年化20% <br/>");
		}

		sb.append("备注  2：利息为未扣利息管理费<br/> ");
		sb.append("<span style='font-weight:bold;font-size: 12px;line-height:20px;'>乙方（借款人）：顶玺金融用户名：<font color='red'>" + userName + "</font><br/>");
		sb.append("丙方（见证人）：上海顶玺金融信息服务有限公司（联系地址：上海市普陀区华池路58弄新体育3楼）<br/>");
		sb.append("鉴于</span>：<br/>");
		sb.append("1、丙方是一家在上海合法成立并有效存续的有限责任公司，拥有www.dxjr.com 网站（以下简称“该网站”）的经营权，该公司具有提供金融信息服务、数据处理服务及投资咨询服务等；<br/>");
		sb.append("2、乙方为该网站的注册会员，并承诺其提供给丙方的信息是完全真实的；<br/>");
		sb.append("3、甲方承诺对本协议涉及的借款资金具有完全的支配能力，是其自有闲散资金，为其合法所得；并承诺其提供给丙方的信息是完全真实的；<br/>");
		sb.append("4、乙方有借款需求，甲方亦同意借款，双方有意成立借贷关系。<br/>");
		sb.append("<span style='font-weight:bold;font-size: 12px;line-height:20px;'>各方经协商一致，于<font color='red'> " + DateUtils.timeStampToDate(borrow.getSuccessTime(), DateUtils.YMD_DASH)
				+ "</font> 签订如下协议，共同遵照履行</span>：<br/>");
		sb.append("<span style='font-weight:bold;font-size: 12px;line-height:20px;'>第一条 借款基本信息</span><br/>");

		sb.append("<table width='80%' border='1' cellpadding='0' cellspacing='0' bordercolor='#000000'>"
				+ "<tr style='font-size:12px;' align='center' height='20px'><td wdith='30%'>&nbsp;借款标题</td><td wdith='70%' style='color:red;border-color:#000;'>" + borrow.getName()
				+ "（各出借人借款本金数额详见本协议文首表格）</td></tr>" + "<tr style='font-size: 12px;' align='center' height='20px'><td>&nbsp;借款本金数额</td><td style='color:red;border-color:#000;'>"
				+ MoneyUtil.insertComma(String.valueOf(borrow.getAccount().setScale(2, BigDecimal.ROUND_DOWN))) + "</td></tr>");
		if (borrow.getStyle() == 1) { // 等额本息
			sb.append("<tr style='font-size: 12px;' align='center'><td>&nbsp;每月应还本息</td><td style='color:red;border-color:#000;'>"
					+ MoneyUtil.insertComma(String.valueOf(repaymentAccountTotal.divide(new BigDecimal(String.valueOf(borrow.getTimeLimit())), 2, BigDecimal.ROUND_DOWN))) + "</td></tr>");
		} else {
			sb.append("<tr style='font-size: 12px;' align='center'><td>&nbsp;到期偿还本息金额</td><td style='color:red;border-color:#000;'>"
					+ MoneyUtil.insertComma(String.valueOf(repaymentAccountTotal.setScale(2, BigDecimal.ROUND_DOWN))) + "</td></tr>");
		}
		sb.append("<tr style='font-size: 12px;' align='center' height='20px'><td>&nbsp;还款期限</td><td style='color:red;border-color:#000;'>" + time_limit + "</td></tr>");
		if (borrow.getStyle() == 1) { // 等额本息
			sb.append("<tr style='font-size: 12px;' align='center' height='20px'><td>&nbsp;结清还款日</td><td style='color:red;border-color:#000;'>" + endDateStr + "</td></tr>");
		} else {
			sb.append("<tr style='font-size: 12px;' align='center' height='20px'><td>&nbsp;还款日</td><td style='color:red;border-color:#000;'>" + endDateStr + "</td></tr>");
		}
		sb.append("</table><br/>");

		sb.append("<span style='font-weight:bold;font-size: 12px;line-height:20px;'>第二条 各方权利和义务</span><br/>");
		sb.append("1、 甲方应按合同约定的借款期限起始日期将足额的借款本金支付给乙方；<br/>");
		sb.append("2、 甲方享有其所出借款项所带来的利息收益；<br/>");
		sb.append("3、 如乙方违约，甲方有权要求丙方提供其已获得的乙方信息，丙方应当提供；<br/>");
		sb.append("4、 甲方应主动缴纳由利息收益所需要交纳的税费；<br/>");
		sb.append("5、甲方应根据其会员等级向丙方支付一定利息管理费，该费用在甲方获得乙方利息时自动扣除，<br/>");
		sb.append("具体收费列表如下：<br/>");
		sb.append("普通会员收取10%的利息管理费<br/>");
		sb.append("金牌会员收取9%的利息管理费<br/>");
		sb.append("白金会员收取8%的利息管理费<br/>");
		sb.append("钻石会员收取7%的利息管理费<br/>");
		sb.append("皇冠会员收取6%的利息管理费<br/>");
		sb.append("金皇冠会员收取5%的利息管理费<br/>");

		sb.append("<span style='font-weight:bold;font-size: 12px;line-height:20px;'><u>乙方权利和义务</u></span><br/>");
		sb.append("1、 乙方必须按期足额向甲方偿还本金和利息；<br/>");
		sb.append("2、 乙方必须按期足额向丙方支付借款管理费用；<br/>");
		sb.append("3、 乙方承诺所借款项不用于任何违法用途；<br/>");
		sb.append("4、 乙方应确保其提供的信息和资料的真实性，不得提供虚假信息或隐瞒重要事实；<br/>");
		sb.append("5、 乙方有权了解其在丙方的抵押资料评审进度及结果；<br/>");
		sb.append("6、 乙方不得将本协议项下的任何权利义务转让给任何其他个人或企业。<br/>");
		sb.append("<span style='font-weight:bold;font-size: 12px;line-height:20px;'><u>丙方的权利和义务</u></span><br/>");
		sb.append("1、甲乙双方同意丙方有权代甲方每期收取甲方出借款项所对应的乙方每期偿还的本息，代收后按照甲方的要求进行处置。<br/>");
		sb.append("2、甲方同意向乙方出借相应款项时，已委托丙方在本协议生效时将该笔借款直接划付至乙方账户。<br/>");
		sb.append("3、甲乙双方同意丙方有权代甲方在有必要时对乙方进行其所借款的违约提醒及催收工作，包括但不限于电话通知、发律师函、对乙方提起诉讼等。甲方在此确认明确委托丙方为其进行以上工作，并授权丙方可以将此工作委托给其他方进行。乙方对前述委托的提醒、催收事项已明确知晓并应积极配合。<br/>");
		sb.append("4、丙方有权按月向乙方收取双方约定的借款管理费，并在有必要时对乙方进行违约提醒及催收工作，包括但不限于电话通知、发律师函、对乙方提起诉讼等。 丙方有权将此违约提醒及催收工作委托给本协议外的其他方进行。<br/>");
		sb.append("5、丙方接受甲乙双方的委托行为所产生的法律后果由相应委托方承担。如因乙方或甲方或其他方（包括但不限于技术问题）造成的延误或错误，丙方不承担任何责任。<br/>");
		sb.append("6、丙方应对甲方和乙方的信息及本协议内容保密；如任何一方违约，或因相关权力部门要求（包括但不限于法院、仲裁机构、金融监管机构等），丙方有权披露。<br/>");
		sb.append("7、丙方根据本协议对乙方进行违约提醒及催收工作时，可在其认为必要时进行上门催收提醒，即丙方派出人员（至少2名）至乙方披露的住所地或经常居住地（联系地址）处催收和进行违约提醒，同时向乙方发送催收通知单，乙方应当签收，乙方不签收的，不影响上门催收提醒的进行。丙方采取上门催收提醒的，乙方应当向丙方支付上门提醒费用，收费标准为每次人民币1000.00元，此外，乙方还应向丙方支付进行上门催收提醒服务的差旅费（包括但不限于交通费、食宿费等）。<br/>");
		sb.append("8、甲、乙、丙三方商议，可保留调整利率的权力。<br/>");

		sb.append("<span style='font-weight:bold;font-size: 12px;line-height:20px;'>第三条 借款管理费及居间服务费</span><br/>");
		sb.append("1、 在本协议中，“借款管理费”和“居间服务费”是指因丙方为乙方提供资产咨询、评估、还款提醒、账户管理、还款特殊情况沟通、本金保障等系列资产评估相关服务（统称“资产评估服务”）而由乙方支付给丙方的报酬。<br/>");
		sb.append("2、 对于丙方向乙方提供的一系列资产评估服务，乙方同意在借款成功时向丙方支付本协议第一条约定借款本金总额的一定比例作为居间服务费，该“居间服务费”由乙方授权并委托丙方在丙方根据本协议规定的“丙方的权利和义务”第2款规定向乙方划付出借本金时从本金中予以扣除，即视为乙方已缴纳。<br/>");
		sb.append("本条所称的“借款成功时”系指本协议签署日。<br/>");
		sb.append("3、如乙方和丙方协商一致调整借款管理费和居间服务费时，无需经过甲方同意。<br/>");
		sb.append("<span style='font-weight:bold;font-size: 12px;line-height:20px;'>第四条 违约责任</span><br/>");
		sb.append("1、协议各方均应严格履行合同义务，非经各方协商一致或依照本协议约定，任何一方不得解除本协议。<br/>");
		sb.append("2、任何一方违约，违约方应承担因违约使得其他各方产生的费用和损失，包括但不限于调查、诉讼费、律师费等，应由违约方承担。如违约方为乙方的，甲方有权立即解除本协议，并要求乙方立即偿还未偿还的本金、利息、罚息、违约金。此时，乙方还应向丙方支付所有应付的借款管理费。如本协议提前解除时，乙方在www.dxjr.com网站的账户里有任何余款的，丙方有权按照本协议第四条第3项的清偿顺序将乙方的余款用于清偿，并要求乙方支付因此产生的相关费用。<br/>");
		sb.append("3、乙方的每期还款均应按照如下顺序清偿：<br/>");
		sb.append("（1）根据本协议产生的其他全部费用；<br/>");
		/* sb.append("（2）逾期罚息<br/>"); */
		sb.append("（2）逾期借款管理费<br/>");
		sb.append("（3）拖欠的利息；<br/>");
		sb.append("（4）拖欠的本金；<br/>");
		// sb.append("罚息总额 =( 逾期本息总额+逾期借款管理费)×罚息利率0.2%×逾期天数；<br/>");
		sb.append("  如借款人逾期，丙方当天晚上22点垫付当月的本息，剩余月份只垫付本金。（具体请参考网站最新垫付规则：https://www.dxjr.com/safe/index.html)在网站垫付前仍未清偿借款本息,则出借人将本协议项下的债权转让给本网站合作商；转让价格为不超过借款人逾期仍未偿还的借款本息（具体金额视出借人在本网站的会员级别不同而有所不同），本网站合作商向出借人支付借款人逾期仍未偿还的本金即可取得出借人在本协议项下的债权；本网站合作商将依法自行承担清收费用，向借款人追收借款本金、利息、逾期罚息等，坏账风险由本网站合作商承担。但是，本网站合作商作为独立的营利性组织机构在查实借入者和出借者合伙诈骗的情况下可拒绝受让出借人在本协议项下的债权，本网站将不遗余力追究相关人员的法律责任。<br/>");
		sb.append("4、本网站对逾期仍未还款的借款人收取逾期罚息作为催收费用、采取多种方式进行催收、将借款人的相关信息对外公开或列入“不良信用记录”或采取法律措施等各项行为，均只是本网站根据本协议为出借人提供的一种服务，该等服务的法律后果均由借款人和出借人自行承担，并不表示本网站对该借款本息之清偿承担任何担保责任或追收责任。<br/>");
		sb.append("<span style='font-weight:bold;font-size: 12px;line-height:20px;'>第五条 提前还款</span><br/>");
		sb.append("1、双方同意,若借款人出现如下任何一种情况,则本协议项下的全部借款自动提前到期,借款人在收到本网站发出的借款提前到期通知后，应立即清偿全部本金、利息、逾期利息及根据本协议产生的其他全部费用：  <br/>");
		sb.append("(1)借款人因任何原因逾期支付任何一期还款超过30天的；<br/>");
		sb.append("(2)借款人的工作单位、职务或住所变更后，未在30天内通知本网站；<br/>");
		sb.append("(3)借款人发生影响其清偿本协议项下借款的其他不利变化，未在30天内通知本网站。<br/>");
		sb.append("2、双方同意,借款人有权提前清偿全部借款而不承担任何的违约责任(如果乙方借款在当月提前结清但超过1日不足1个月者，本息按足月付给甲方，剩余月份为未还本金*1%罚息发给甲方；如果乙方提前整月结清而没有跨天，乙方给甲方的本息按实际发生月份本息加剩余月份未还本金*1%的罚息给付)。<br/>");
		sb.append("3、本借款协议中的每一出借人与借款人之间的借款均是相互独立的,一旦借款人逾期未归还借款本息,任何一出借人有权单独对该出借人未收回的借款本息向借款人追索或者提起诉讼。<br/>");
		sb.append("4、任何形式的提前还款不影响丙方向乙方收取在本协议第三条中说明的居间服务费。<br/>");
		sb.append("<span style='font-weight:bold;font-size: 12px;line-height:20px;'>第六条 法律及争议解决</span><br/>");
		sb.append("本协议的签订、履行、终止、解释均适用中华人民共和国法律，如因本协议发生争议，甲、乙、丙三方一致同意由丙方所在地上海虹口区人民法院管辖。<br/>");
		sb.append("<span style='font-weight:bold;font-size: 12px;line-height:20px;'>第七条 附则</span><br/>");
		sb.append("1、本协议采用电子文本形式制成，并永久保存在丙方为此设立的专用服务器上备查，各方均认可该形式的协议效力。<br/>");
		sb.append("2、本协议自文本最终生成之日生效。<br/>");
		sb.append("3、本协议签订之日起至借款全部清偿之日止，乙方或甲方有义务在下列信息变更三日内提供更新后的信息给丙方：本人、本人的家庭联系人及紧急联系人、工作单位、居住地址、住所电话、手机号码、电子邮箱、银行账户的变更。若因任何一方不及时提供上述变更信息而带来的损失或额外费用应由该方承担。<br/>");
		sb.append("4、如果本协议中的任何一条或多条违反适用的法律法规，则该条将被视为无效，但该无效条款并不影响本协议其他条款的效力。<br/></span>");
		sb.append("</span>");
		if (request != null) {
			String path = request.getContextPath();
			String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
			sb.append("<div align='right'><img src='" + basePath + "/images/CompanyElectronicChapter.gif'/></div>");
		} else {
			sb.append("<div align='right'><img src='https://www.dxjr.com/images/CompanyElectronicChapter.gif'/></div>");
		}

		sb.append("</div>");
		return sb.toString();
	}
}
