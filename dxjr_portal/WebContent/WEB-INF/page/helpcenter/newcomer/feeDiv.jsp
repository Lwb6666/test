<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
 ${param.isHome!=null?'<h2><a href="#">投资资费</a></h2>':'<h1><a href="#">投资资费</a></h1>' }  
         <div class="help-title" onclick="list(40)"><a href="javascript:void(0);" id="help_title">投资有哪些费用？<span></span></a></div>
         <div class="help-content" id="list40">
            <%-- <p>【VIP费用】：VIP费用为10元每年；终身顶级会员享受免VIP会员费特权。</p> --%>
			<p>  理财资费说明：</p>
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
             <p class="up" onclick="list(40)"><a href="javascript:void(0);" id="help_title">&nbsp;</a></p>
         </div>        
         <div class="help-title" onclick="list(41)"><a href="javascript:void(0);" id="help_title"> 投标成功后何时开始计息？<span></span></a></div>
         <div class="help-content" id="list41">
             <p>满标后的下一个自然日开始计息。</p> 
             <p class="up" onclick="list(41)"><a href="javascript:void(0);" id="help_title">&nbsp;</a></p>
         </div> 
         
         <!-- <div class="help-title" onclick="list(42)"><a href="javascript:void(0);" id="help_title">  加权待收是什么？<span></span></a></div>
         <div class="help-content" id="list42">
             <p>加权待收是用作评定会员级别的依据。【计算公式】：加权待收=自注册之日起每天待收总额的累加/180。</p> 
             <p class="up" onclick="list(42)"><a href="javascript:void(0);" id="help_title">&nbsp;</a></p>
         </div> -->        
