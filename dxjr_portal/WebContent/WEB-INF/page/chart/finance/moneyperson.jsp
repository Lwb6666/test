<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!--数据展示-->  
<div id="data">
  <h1>平台披露数据</h1>
    <table>
  <tbody>
    <tr>
      <td class="title">交易总额</td>
      <td><fmt:formatNumber value="${dateMoneyVo.transactionAmount }" pattern="#,##0.00"/>元</td>
      <td class="title">交易总笔数 </td>
      <td>${dateMoneyVo.transactionNumber }笔 </td>
    </tr>
    <tr>
      <td class="title">借款人数量 </td>
      <td>${dateMoneyVo.borrowersNumber }位 </td>
      <td class="title">投资人数量 </td>
      <td>${dateMoneyVo.investorsNumber }位 </td>
    </tr>
    <tr>
      <td class="title">人均累计借款额度 </td>
      <td><fmt:formatNumber value="${dateMoneyVo.cumulativeAmount }" pattern="#,##0.00"/>元 </td>
      <td class="title">笔均借款额度 </td>
      <td><fmt:formatNumber value="${dateMoneyVo.averageAmount }" pattern="#,##0.00"/>元 </td>
    </tr>
    <tr>
      <td class="title">人均累计投资额度</td>
      <td><fmt:formatNumber value="${dateMoneyVo.investmentAmount }" pattern="#,##0.00"/>元 </td>
      <td class="title">笔均投资额度 </td>
      <td><fmt:formatNumber value="${dateMoneyVo.investmentAverageAmount }" pattern="#,##0.00"/>元</td>
    </tr>
    <tr>
      <td class="title"><div class="tipic" onmousemove="$('#loanBalance').show();" onmouseout="$('#loanBalance').hide();"> <div class="preview" id="loanBalance" style="display:none;"><i class="previewin" ></i> 上线至今，所有未偿还本金总额（利息和逾期本金除外）。当期贷款余额=前期贷款余额+本期新增交易额-本期偿还贷款本金总额（还款总金额本金部分）-本期发生逾期总金额 </div></div>贷款余额
       </td>
      <td><fmt:formatNumber value="${dateMoneyVo.loanBalance }" pattern="#,##0.00"/>元 </td>
      <td class="title"><div class="tipic" onmousemove="$('#largestAccounted').show();" onmouseout="$('#largestAccounted').hide();"> <div class="preview" id="largestAccounted" style="display:none;"><i class="previewin" id="largestAccounted"></i>当期最大借款人贷款额占平台总贷款余额的比值</div></div>最大单户借款余额占比</td>
      <td>${dateMoneyVo.largestAccounted }%</td>
    </tr>
    <tr>
      <td class="title"><div class="tipic" onmousemove="$('#tenlargestAccounted').show();" onmouseout="$('#tenlargestAccounted').hide();"><div class="preview" id="tenlargestAccounted" style="display:none;"><i class="previewin" id="tenlargestAccounted"></i>当期最大10位借款人贷款额占平台总贷款余额的比值</div></div>最大10户借款余额占比</td>
       <td>${dateMoneyVo.tenlargestAccounted }%</td>
      <td class="title">平均满标时间 </td>
      <td>${dateMoneyVo.meanFullTime } </td>
    </tr>
    <tr>
      <td class="title">累计违约率 </td>
      <td>${dateMoneyVo.cumulativeRate }%</td>
      <td class="title">平台项目逾期率 </td>
       <td>${dateMoneyVo.platformProjectRate }%</td>
    </tr>
    <tr>
      <td class="title">近三月项目逾期率 </td>
       <td>${dateMoneyVo.platformProjectThreeRate }%</td>
      <td class="title">借款逾期金额 </td>
      <td><fmt:formatNumber value="${dateMoneyVo.borrowingAmount }" pattern="#,##0.00"/>元</td>
    </tr>
    <tr>
      <td class="title"><div class="tipic" onmousemove="$('#compensatoryAmount').show();" onmouseout="$('#compensatoryAmount').hide();"><div class="preview" id="compensatoryAmount" style="display:none;"><i class="previewin" id="compensatoryAmount"></i>累计投资人应收但未收的偿付本息总额</div></div>代偿金额</td>
      <td><fmt:formatNumber value="${dateMoneyVo.compensatoryAmount }" pattern="#,##0.00"/>元</td>
      <td class="title"><div class="tipic" onmousemove="$('#borrowingRate').show();" onmouseout="$('#borrowingRate').hide();"><div class="preview" id="borrowingRate" style="display:none;"><i class="previewin" id="borrowingRate"></i>上线至今，累计逾期本金金额与贷款总额比值</div></div>借贷逾期率</td>
     <td>${dateMoneyVo.borrowingRate }%</td>
    </tr>
    <tr>
      <td class="title"><div class="tipic" onmousemove="$('#debtBadRate').show();" onmouseout="$('#debtBadRate').hide();"><div class="preview" id="debtBadRate" style="display:none;"><i class="previewin" id="debtBadRate"></i>上线至今，累计坏账本金金额与贷款总额比值</div></div>借贷坏账率</td>
       <td>${dateMoneyVo.debtBadRate }%</td>
      <td class="title">当年成交额</td>
      <td><fmt:formatNumber value="${dateMoneyVo.yearTransactionAmount }" pattern="#,##0.00"/>元 </td>
    </tr>
     <tr>
      <td class="title">当年成交笔数</td>
      <td>${dateMoneyVo.yearTransactionCount }笔</td>
      <td class="title">代偿笔数 </td>
      <td>${dateMoneyVo.compensatoryCount }笔</td>
    </tr>
  </tbody>
</table>

</div>
