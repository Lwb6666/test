<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
${param.isHome!=null?'<h2><a href="#">债权回购</a></h2>':'<h1><a href="#">债权回购</a></h1>' }
         <div class="help-title" onclick="list(9)"><a href="javascript:void(0);" id="help_title">债权回购规则<span></span></a></div>
         <div class="help-content" id="list9" style="display: none;">
            <p>对于资产抵押标、机构担保标、信用认证标在应还款当日22:00整进行债权回购本息。</p>
            <table >
                <tr>
                    <th>标的类型 </th>
                    <th>保障内容 </th>
                    <th>保障垫付说明 </th>
                </tr>
                <tr>
                    <td>信用认证标 </td>
                    <td rowspan="3">逾期当日，22:00整进行债权回购 </td>
                    <td>本金和利息 </td>
                </tr>
                <tr>
                    <td>资产抵押标 </td>
                    <td>本金和利息 </td>
                </tr>
                <tr>
                    <td>机构担保标 </td>
                    <td>本金和利息 </td>
                </tr>
                <%-- <tr>
                    <td>净值标 </td>
                    <td width="215" colspan="2">所有用户垫付本金和利息 </td>
                </tr> --%>
            </table>
            <p class="up" onclick="list(9)"><a href="javascript:void(0);" id="help_title">&nbsp;</a></p>
         </div>
         <div class="help-title" onclick="list(10)"><a href="javascript:void(0);" id="help_title">逾期罚息<span></span></a></div>
         <div class="help-content" id="list10">
            <p> 如果借款人发生逾期，在非正常情况下网站没有在还款日当天22:00及时回购逾期标的的债权本息时，则需给投资客户罚息补偿。具体补偿规则如下：</p>
            <p>（1）抵押标、信用标、担保标 给投资人的补偿金额计算公式为：（当期应还的投资本金+当期应还的利息）*补偿天数*0.1%；</p>
            <%-- <p>（2）净值标投资人的补偿金额计算公式为：（当期应还本金+当期应还利息）*补偿天数*0.2%；</p> --%>
            <p>（2）逾期罚息补偿时间：在借款用户逾期后还款当日或者在资产处置完毕当日。</p>
            <p>所有标种对于投资人来说，补偿天数是指：应还款日到债权回购日的实际天数差值。</p>
            <p class="up" onclick="list(10)"><a href="javascript:void(0);" id="help_title">&nbsp;</a></p>
         </div>
