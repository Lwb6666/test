<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>

 
		 ${param.isHome!=null?'<h2><a href="#">收益与费用</a></h2>':'<h1><a href="#">收益与费用</a></h1>' }
		 
         <div class="help-title" onclick="list(1)"><a href="javascript:void(0);" id="help_title">投标直通车收益<span></span></a></div>
         <div class="help-content" id="interestMoneyExpand1">
             <p> 预期年化收益率根据散标利率 </p> 
             <p class="up" onclick="list(1)"><a href="javascript:void(0);" id="help_title">&nbsp;</a></p>
         </div>        
         <div class="help-title" onclick="list(2)"><a href="javascript:void(0);" id="help_title">抵押标收益<span></span></a></div>
         <div class="help-content" id="interestMoneyExpand2">
             <p>目前平台的预期年化率10%—15%。</p> 
             <p class="up" onclick="list(2)"><a href="javascript:void(0);" id="help_title">&nbsp;</a></p>
         </div>        
         <%-- <div class="help-title" onclick="list(3)"><a href="javascript:void(0);" id="help_title">净值标收益<span></span></a></div>
         <div class="help-content" id="interestMoneyExpand3">
             <p>年利率区间6%-24%，具体收益由您所投资的借款标利率确定。</p> 
             <p class="up" onclick="list(3)"><a href="javascript:void(0);" id="help_title">&nbsp;</a></p>
         </div> --%>        
         <div class="help-title" onclick="list(4)"><a href="javascript:void(0);" id="help_title">充值提现费用<span></span></a></div>
         <div class="help-content" id="interestMoneyExpand4">
                	<table class="border">
               <tr >
                 <th  style="text-align:center;padding-left: 0px;width:100px;" >类别
                   </td>
                 
                 <th  style="text-align:center;padding-left: 0px;width:100px;" >类别</td>
                 <th  style="text-align:center;padding-left: 0px;width:350px;">内容</td>
                 <th  style="text-align:center;padding-left: 0px">费用</td>
               </tr>
               <tr>
                 <td rowspan="3" style="text-align:center;">充值手续费</td>
                 <td rowspan="2" style="text-align:center;">顶玺账户</td>
                 <td style="text-align:center;">通过“京东支付、连连支付、富友支付”在线充值</td>
                 <td style="color:red;text-align:center;">免费</td>
               </tr>
               <tr>
                 <td style="text-align:center;">通过“新浪支付”充值，第三方支付平台</td>
                 <td style="text-align:center;">收取0.2%(最低收取1分)</td>
               </tr>
               <tr>
                 <td style="text-align:center;">存管账户</td>
                 <td style="text-align:center;">500元起充</td>
                 <td style="text-align:center;color:red;">免费</td>
               </tr>
               <tr style="text-align:center;">
                 <td rowspan="3">提现手续费</td>
                 <td rowspan="2">顶玺账户</td>
                 <td style="text-align:center;">投资后回款的提现</td>
                 <td style="text-align:center;">
                 2元/笔，每月前3笔免费
                 <!-- <label style="color:red;">每月前三笔免费</label>
                                              ，第4笔提现申请则按2元每笔收取 --></td>
               </tr>
               <tr style="text-align:center;">
                 <td style="text-align:center;">未投资的充值金额提现需将受限金额转化为可提，新注册用户首次充值未经投标将无法转可提。</td>
                 <td style="text-align:center;">转化费为转化总额的0.5%</td>
               </tr>
               <tr>
                 <td style="text-align:center;">存管账户</td>
                 <td style="text-align:center;">单次申请最高为50000元，日提现无限额</td>
                 <td style="text-align:center;color:red;">免费</td>
               </tr>
               <!-- <tr>
                 <td style="text-align:center;">利息管理费</td>
                 <td style="text-align:center;">2015年8月1日起所有理财用户投标回款</td>
                 <td style="text-align:center;">免利息管理费</td>
               </tr> -->
             </table>
               <!--  <p>充值费用：通过“京东支付、连连支付、富友支付”充值免手续费。通过新浪支付充值，第三方支付平台收取千分之二的手续费（最低1分）。</p>
                
                <p>提现费用：充值投标回款金额提现为每笔（最低100元最高5万元，申请时可提交最高50万，但按照10笔打款并收取相应手续费 ）代银行收取2元提现手续费，<label style="color:red;">每月前三笔提现免提现费</label>；充值未投标金额申请提现需将受限金额转化为可提现金额，转化费用为总额的0.5%。但新注册用户首次充值未经投标将无法转可提。</p> -->
               
             <p class="up" onclick="list(4)"><a href="javascript:void(0);" id="help_title">&nbsp;</a></p>
         </div>
         
<script type="text/javascript">
	function list(i) {
		$("#interestMoneyExpand" + i).animate({
			height : 'toggle'
		});

	}
</script>     