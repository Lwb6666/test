package com.dxjr.portal.electronbill.entity;

import java.math.BigDecimal;
import java.util.Date;

public class ElectronBill {
    private Integer id;

    private Integer userId;

    private String year;//所在年份

    private String month;//所在月份

    private BigDecimal theMonthFixIncome;//当月定期宝赚取金额

    private BigDecimal theMonthCurIncome;//当月活期宝赚取金额

    private BigDecimal theMonthBidIncome;//当月散标赚取金额

    private BigDecimal theMonthFixInvest;//当月定期宝投资金额

    private BigDecimal theMonthCurInvest;//当月活期宝投资金额

    private BigDecimal theMonthCustodyInvest;//当月存管标投资金额

    private BigDecimal theMonthNotCustodyInvest;//当月非存管标投资金额

    private BigDecimal sumFixIncomeOne;//一月宝累计赚取金额

    private BigDecimal sumFixIncomeThree;//三月宝累计赚取金额

    private BigDecimal sumFixIncomeSix;//六月宝累计赚取金额

    private BigDecimal sumFixIncomeTwelve;//十二月宝累计赚取金额

    private BigDecimal sumCurIncome;//活期宝累计赚取金额

    private BigDecimal sumCustodyIncome;//存管标累计赚取金额

    private BigDecimal sumNotCustodyIncomeOne;//非存管标累计赚取金额

    private BigDecimal theMonthFixIncomeOne;//当月一月宝赚取金额

    private BigDecimal theMonthFixIncomeThree;//当月三月宝赚取金额

    private BigDecimal theMonthFixIncomeSix;//当月六月宝赚取金额

    private BigDecimal theMonthFixIncomeTwelve;//当月十二月宝赚取金额

    private BigDecimal theMonthCustodyIncome;//当月存管标赚取金额

    private BigDecimal theMonthNotCustodyIncome;//当月非存管标赚取金额

    private BigDecimal theMonthFixCapitalOne;//当月一月宝回收本金

    private BigDecimal theMonthFixCapitalThree;//当月三月宝回收本金

    private BigDecimal theMonthFixCapitalSix;//当月六月宝回收本金

    private BigDecimal theMonthFixCapitalTwelve;//当月十二月宝回收本金

    private BigDecimal theMonthCustodyCapital;//当月存管标回收本金

    private BigDecimal theMonthNotCustody;//当月非存管标回收本金

    private BigDecimal theMonthCurCapital;//当月活期宝回收本金

    private BigDecimal theMonthFixInvestOne;//当月一月宝投资金额

    private BigDecimal theMonthFixInvestThree;//当月三月宝投资金额

    private BigDecimal theMonthFixInvestSix;//当月六月宝投资金额

    private BigDecimal theMonthFixInvestTwelve;//当月十二月宝投资金额

    private Integer theMonthAccumulatepoints;//当月获得的元宝

    private Integer sumAccumulatepoints;//累计获得的元宝

    private Integer accumulatepointsIsuse;//已经使用的元宝

    private Integer accumulatepoints;//当前持有的元宝

    private BigDecimal theMonthRedaccount;//当月获得的红包金额

    private BigDecimal sumRedaccount;//累计获得的红包金额

    private BigDecimal redaccountIsuse;//已经使用的红包金额

    private BigDecimal redaccount;//当前持有的红包金额

    private BigDecimal theMonthOtherIncome;//当月获得的其他奖励

    private BigDecimal sumOtherIncome;//累计获得的其他奖励

    private Integer theMonthLottchance;//当月已获得的抽奖机会

    private BigDecimal theMonthRecharge;//当月充值金额

    private BigDecimal theMonthCash;//当月提现金额

    private BigDecimal theMonthCashFee;//当月提现服务费

    private BigDecimal theMonthTransFee;//当月转让服务费

    private BigDecimal theMonthFixexitFee;//当月定期宝退出服务费

    private Integer addBy;//添加人

    private Date addTime;//添加时间

    private String remark;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year == null ? null : year.trim();
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month == null ? null : month.trim();
    }

    public BigDecimal getTheMonthFixIncome() {
        return theMonthFixIncome;
    }

    public void setTheMonthFixIncome(BigDecimal theMonthFixIncome) {
        this.theMonthFixIncome = theMonthFixIncome;
    }

    public BigDecimal getTheMonthCurIncome() {
        return theMonthCurIncome;
    }

    public void setTheMonthCurIncome(BigDecimal theMonthCurIncome) {
        this.theMonthCurIncome = theMonthCurIncome;
    }

    public BigDecimal getTheMonthBidIncome() {
        return theMonthBidIncome;
    }

    public void setTheMonthBidIncome(BigDecimal theMonthBidIncome) {
        this.theMonthBidIncome = theMonthBidIncome;
    }

    public BigDecimal getTheMonthFixInvest() {
        return theMonthFixInvest;
    }

    public void setTheMonthFixInvest(BigDecimal theMonthFixInvest) {
        this.theMonthFixInvest = theMonthFixInvest;
    }

    public BigDecimal getTheMonthCurInvest() {
        return theMonthCurInvest;
    }

    public void setTheMonthCurInvest(BigDecimal theMonthCurInvest) {
        this.theMonthCurInvest = theMonthCurInvest;
    }

    public BigDecimal getTheMonthCustodyInvest() {
        return theMonthCustodyInvest;
    }

    public void setTheMonthCustodyInvest(BigDecimal theMonthCustodyInvest) {
        this.theMonthCustodyInvest = theMonthCustodyInvest;
    }

    public BigDecimal getTheMonthNotCustodyInvest() {
        return theMonthNotCustodyInvest;
    }

    public void setTheMonthNotCustodyInvest(BigDecimal theMonthNotCustodyInvest) {
        this.theMonthNotCustodyInvest = theMonthNotCustodyInvest;
    }

    public BigDecimal getSumFixIncomeOne() {
        return sumFixIncomeOne;
    }

    public void setSumFixIncomeOne(BigDecimal sumFixIncomeOne) {
        this.sumFixIncomeOne = sumFixIncomeOne;
    }

    public BigDecimal getSumFixIncomeThree() {
        return sumFixIncomeThree;
    }

    public void setSumFixIncomeThree(BigDecimal sumFixIncomeThree) {
        this.sumFixIncomeThree = sumFixIncomeThree;
    }

    public BigDecimal getSumFixIncomeSix() {
        return sumFixIncomeSix;
    }

    public void setSumFixIncomeSix(BigDecimal sumFixIncomeSix) {
        this.sumFixIncomeSix = sumFixIncomeSix;
    }

    public BigDecimal getSumFixIncomeTwelve() {
        return sumFixIncomeTwelve;
    }

    public void setSumFixIncomeTwelve(BigDecimal sumFixIncomeTwelve) {
        this.sumFixIncomeTwelve = sumFixIncomeTwelve;
    }

    public BigDecimal getSumCurIncome() {
        return sumCurIncome;
    }

    public void setSumCurIncome(BigDecimal sumCurIncome) {
        this.sumCurIncome = sumCurIncome;
    }

    public BigDecimal getSumCustodyIncome() {
        return sumCustodyIncome;
    }

    public void setSumCustodyIncome(BigDecimal sumCustodyIncome) {
        this.sumCustodyIncome = sumCustodyIncome;
    }

    public BigDecimal getSumNotCustodyIncomeOne() {
        return sumNotCustodyIncomeOne;
    }

    public void setSumNotCustodyIncomeOne(BigDecimal sumNotCustodyIncomeOne) {
        this.sumNotCustodyIncomeOne = sumNotCustodyIncomeOne;
    }

    public BigDecimal getTheMonthFixIncomeOne() {
        return theMonthFixIncomeOne;
    }

    public void setTheMonthFixIncomeOne(BigDecimal theMonthFixIncomeOne) {
        this.theMonthFixIncomeOne = theMonthFixIncomeOne;
    }

    public BigDecimal getTheMonthFixIncomeThree() {
        return theMonthFixIncomeThree;
    }

    public void setTheMonthFixIncomeThree(BigDecimal theMonthFixIncomeThree) {
        this.theMonthFixIncomeThree = theMonthFixIncomeThree;
    }

    public BigDecimal getTheMonthFixIncomeSix() {
        return theMonthFixIncomeSix;
    }

    public void setTheMonthFixIncomeSix(BigDecimal theMonthFixIncomeSix) {
        this.theMonthFixIncomeSix = theMonthFixIncomeSix;
    }

    public BigDecimal getTheMonthFixIncomeTwelve() {
        return theMonthFixIncomeTwelve;
    }

    public void setTheMonthFixIncomeTwelve(BigDecimal theMonthFixIncomeTwelve) {
        this.theMonthFixIncomeTwelve = theMonthFixIncomeTwelve;
    }

    public BigDecimal getTheMonthCustodyIncome() {
        return theMonthCustodyIncome;
    }

    public void setTheMonthCustodyIncome(BigDecimal theMonthCustodyIncome) {
        this.theMonthCustodyIncome = theMonthCustodyIncome;
    }

    public BigDecimal getTheMonthNotCustodyIncome() {
        return theMonthNotCustodyIncome;
    }

    public void setTheMonthNotCustodyIncome(BigDecimal theMonthNotCustodyIncome) {
        this.theMonthNotCustodyIncome = theMonthNotCustodyIncome;
    }

    public BigDecimal getTheMonthFixCapitalOne() {
        return theMonthFixCapitalOne;
    }

    public void setTheMonthFixCapitalOne(BigDecimal theMonthFixCapitalOne) {
        this.theMonthFixCapitalOne = theMonthFixCapitalOne;
    }

    public BigDecimal getTheMonthFixCapitalThree() {
        return theMonthFixCapitalThree;
    }

    public void setTheMonthFixCapitalThree(BigDecimal theMonthFixCapitalThree) {
        this.theMonthFixCapitalThree = theMonthFixCapitalThree;
    }

    public BigDecimal getTheMonthFixCapitalSix() {
        return theMonthFixCapitalSix;
    }

    public void setTheMonthFixCapitalSix(BigDecimal theMonthFixCapitalSix) {
        this.theMonthFixCapitalSix = theMonthFixCapitalSix;
    }

    public BigDecimal getTheMonthFixCapitalTwelve() {
        return theMonthFixCapitalTwelve;
    }

    public void setTheMonthFixCapitalTwelve(BigDecimal theMonthFixCapitalTwelve) {
        this.theMonthFixCapitalTwelve = theMonthFixCapitalTwelve;
    }

    public BigDecimal getTheMonthCustodyCapital() {
        return theMonthCustodyCapital;
    }

    public void setTheMonthCustodyCapital(BigDecimal theMonthCustodyCapital) {
        this.theMonthCustodyCapital = theMonthCustodyCapital;
    }

    public BigDecimal getTheMonthNotCustody() {
        return theMonthNotCustody;
    }

    public void setTheMonthNotCustody(BigDecimal theMonthNotCustody) {
        this.theMonthNotCustody = theMonthNotCustody;
    }

    public BigDecimal getTheMonthCurCapital() {
        return theMonthCurCapital;
    }

    public void setTheMonthCurCapital(BigDecimal theMonthCurCapital) {
        this.theMonthCurCapital = theMonthCurCapital;
    }

    public BigDecimal getTheMonthFixInvestOne() {
        return theMonthFixInvestOne;
    }

    public void setTheMonthFixInvestOne(BigDecimal theMonthFixInvestOne) {
        this.theMonthFixInvestOne = theMonthFixInvestOne;
    }

    public BigDecimal getTheMonthFixInvestThree() {
        return theMonthFixInvestThree;
    }

    public void setTheMonthFixInvestThree(BigDecimal theMonthFixInvestThree) {
        this.theMonthFixInvestThree = theMonthFixInvestThree;
    }

    public BigDecimal getTheMonthFixInvestSix() {
        return theMonthFixInvestSix;
    }

    public void setTheMonthFixInvestSix(BigDecimal theMonthFixInvestSix) {
        this.theMonthFixInvestSix = theMonthFixInvestSix;
    }

    public BigDecimal getTheMonthFixInvestTwelve() {
        return theMonthFixInvestTwelve;
    }

    public void setTheMonthFixInvestTwelve(BigDecimal theMonthFixInvestTwelve) {
        this.theMonthFixInvestTwelve = theMonthFixInvestTwelve;
    }

    public Integer getTheMonthAccumulatepoints() {
        return theMonthAccumulatepoints;
    }

    public void setTheMonthAccumulatepoints(Integer theMonthAccumulatepoints) {
        this.theMonthAccumulatepoints = theMonthAccumulatepoints;
    }

    public Integer getSumAccumulatepoints() {
        return sumAccumulatepoints;
    }

    public void setSumAccumulatepoints(Integer sumAccumulatepoints) {
        this.sumAccumulatepoints = sumAccumulatepoints;
    }

    public Integer getAccumulatepointsIsuse() {
        return accumulatepointsIsuse;
    }

    public void setAccumulatepointsIsuse(Integer accumulatepointsIsuse) {
        this.accumulatepointsIsuse = accumulatepointsIsuse;
    }

    public Integer getAccumulatepoints() {
        return accumulatepoints;
    }

    public void setAccumulatepoints(Integer accumulatepoints) {
        this.accumulatepoints = accumulatepoints;
    }

    public BigDecimal getTheMonthRedaccount() {
        return theMonthRedaccount;
    }

    public void setTheMonthRedaccount(BigDecimal theMonthRedaccount) {
        this.theMonthRedaccount = theMonthRedaccount;
    }

    public BigDecimal getSumRedaccount() {
        return sumRedaccount;
    }

    public void setSumRedaccount(BigDecimal sumRedaccount) {
        this.sumRedaccount = sumRedaccount;
    }

    public BigDecimal getRedaccountIsuse() {
        return redaccountIsuse;
    }

    public void setRedaccountIsuse(BigDecimal redaccountIsuse) {
        this.redaccountIsuse = redaccountIsuse;
    }

    public BigDecimal getRedaccount() {
        return redaccount;
    }

    public void setRedaccount(BigDecimal redaccount) {
        this.redaccount = redaccount;
    }

    public BigDecimal getTheMonthOtherIncome() {
        return theMonthOtherIncome;
    }

    public void setTheMonthOtherIncome(BigDecimal theMonthOtherIncome) {
        this.theMonthOtherIncome = theMonthOtherIncome;
    }

    public BigDecimal getSumOtherIncome() {
        return sumOtherIncome;
    }

    public void setSumOtherIncome(BigDecimal sumOtherIncome) {
        this.sumOtherIncome = sumOtherIncome;
    }

    public Integer getTheMonthLottchance() {
        return theMonthLottchance;
    }

    public void setTheMonthLottchance(Integer theMonthLottchance) {
        this.theMonthLottchance = theMonthLottchance;
    }

    public BigDecimal getTheMonthRecharge() {
        return theMonthRecharge;
    }

    public void setTheMonthRecharge(BigDecimal theMonthRecharge) {
        this.theMonthRecharge = theMonthRecharge;
    }

    public BigDecimal getTheMonthCash() {
        return theMonthCash;
    }

    public void setTheMonthCash(BigDecimal theMonthCash) {
        this.theMonthCash = theMonthCash;
    }

    public BigDecimal getTheMonthCashFee() {
        return theMonthCashFee;
    }

    public void setTheMonthCashFee(BigDecimal theMonthCashFee) {
        this.theMonthCashFee = theMonthCashFee;
    }

    public BigDecimal getTheMonthTransFee() {
        return theMonthTransFee;
    }

    public void setTheMonthTransFee(BigDecimal theMonthTransFee) {
        this.theMonthTransFee = theMonthTransFee;
    }

    public BigDecimal getTheMonthFixexitFee() {
        return theMonthFixexitFee;
    }

    public void setTheMonthFixexitFee(BigDecimal theMonthFixexitFee) {
        this.theMonthFixexitFee = theMonthFixexitFee;
    }

    public Integer getAddBy() {
        return addBy;
    }

    public void setAddBy(Integer addBy) {
        this.addBy = addBy;
    }

    public Date getAddTime() {
        return addTime;
    }

    public void setAddTime(Date addTime) {
        this.addTime = addTime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }
}