<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ include file="/WEB-INF/page/common/taglib.jsp" %>

<div class="cont-word cont-w03">
    <div class="title">
        <h4>交易详情</h4>
        <a class="icon icon-close fr close-reveal-modal" id="btnClose"></a></div>
    <div class="mxdiv clearfix"><span class="fl">交易日期: ${accountOperateVO.addtimeFMT } （单位：元）</span><span class="fr">交易类型:${accountOperateVO.subTypeStr }</span>
    </div>
    <table class="table center">
        <thead>
        <tr>
            <td>交易金额</td>
            <td>可用余额</td>
            <td>可提金额</td>
            <td>受限金额</td>
            <td>直通车余额</td>
            <td>冻结金额</td>
        </tr>
        </thead>
        <tbody>
        <tr>
            <td>${accountOperateVO.moneyStr }</td>
            <td>${accountOperateVO.use_moneyStr }</td>
            <td>${accountOperateVO.drawMoneyStr }</td>
            <td>${accountOperateVO.noDrawMoneyStr }</td>
            <td>${accountOperateVO.first_borrow_use_moneyStr }</td>
            <td>${accountOperateVO.no_use_moneyStr }</td>
        </tr>
        </tbody>
        <thead>
        <tr>
           <c:if test="${isCustody==1}">
            <td>存管可用</td>
            <td>存管冻结</td>
            <td>存管待收</td>
            </c:if>
            <td>待收总额</td>
            <td>账户总额</td>
            <td></td>
        </tr>
        </thead>
        <tbody>
        <tr>
          <c:if test="${isCustody==1}">
            <td>${accountOperateVO.eUseMoneyStr }</td>
            <td>${accountOperateVO.eFreezeMoneyStr }</td>
            <td>${accountOperateVO.eCollectionStr }</td>
          </c:if>
            <td>${accountOperateVO.collectionStr }</td>
            <td>${accountOperateVO.totalStr }</td>
            <td></td>
        </tr>
        </tbody>
    </table>
    <div class="mxdivtxt">
        <span class="w100">交易备注：</span>
        <span class="mxdivtxt-textarea">${accountOperateVO.remarkStr }【本次交易金额为： ${accountOperateVO.moneyStr } 元】</span>

    </div>

</div>

<script>
     $('#btnClose').on('click', function () {
         $('#myModal').trigger('reveal:close');
     });
</script>