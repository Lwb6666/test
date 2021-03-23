<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/page/common/taglib.jsp"%>
<%@ include file="/WEB-INF/page/common/public.jsp"%>

            <div class="searchbox fl">
                  <ul class="jydate">
                            <li>交易日期:${accountOperateVO.addtimeFMT }</li>
                            <li title="${accountOperateVO.type }">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                            		交易类型:${accountOperateVO.subTypeStr }</li>
                 </ul>
                 </div>
                 <div class="myid">
                   <table >
                                  <tr class="gary">
                                    <td>交易金额(元)</td>
                                    <td>可用余额(元)</td>
                                    <td>可提金额(元)</td>
                                    <td>受限金额(元)</td>
                                    <td>直通车余额  (元)</td>
                                    <td>冻结金额(元)</td>
                                    <td>待收总额(元)</td>
                                    <td>账户总额(元)</td>
                                  </tr>
                                  <tr>
                                    <td class="numcolor" >${accountOperateVO.moneyStr }</td>
                                    <td > <a  class="numcolor">${accountOperateVO.use_moneyStr }</a></td>
                                    <td > <a  class="numcolor">${accountOperateVO.drawMoneyStr }</a></td>
                                    <td > <a  class="numcolor">${accountOperateVO.noDrawMoneyStr }</a></td>
                                    <td class="numcolor">${accountOperateVO.first_borrow_use_moneyStr }</td>
                                    <td class="numcolor">${accountOperateVO.no_use_moneyStr }</td>
                                    <td class="numcolor">${accountOperateVO.collectionStr }</td>
                                    <td class="numcolor">${accountOperateVO.totalStr }</td>
                                  </tr>
            </table>                 
                  <div class="jylx">
                                   <div class="rz_txt ">交易备注：</div>
                                   <div class="rz_textarea jybz"><textarea cols="95" rows="8" class="textarea" disabled="disabled">${accountOperateVO.remarkStr }【本次交易金额为： ${accountOperateVO.moneyStr } 元】</textarea></div>
                              </div>
           
