<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
 
${param.isHome!=null?'<h2><a href="#">新手必读</a></h2>':'<h1><a href="#">新手必读</a></h1>' }  
         <div class="help-title" onclick="managemoney(1)"><a href="javascript:void(0);" id="help_title">我可以投资吗?<span></span></a></div>
         <div class="help-content" id="managemoney1" style="display: none;">
            <p>可以。年满18周岁，具有完全民事权利能力和民事行为能力，可以在顶玺金融网站上进行注册、完成实名认证、绑定银行卡，成为出借人。</p>
            <p class="up" onclick="managemoney(1)"><a href="javascript:void(0);" id="help_title">&nbsp;</a></p>
         </div>
         <div class="help-title" onclick="managemoney(2)"><a href="javascript:void(0);" id="help_title">哪种投资方式更适合我?<span></span></a></div>
         <div class="help-content" id="managemoney2">
            <table class="border">
                                  <tr >
                                    <th  style="text-align:center;padding-left: 0px" >投资方式</td>
                                    <th  style="text-align:center;padding-left: 0px">收益</td>
                                    <th  style="text-align:center;padding-left: 0px">起投金额</td>
                                    <th  style="text-align:center;padding-left: 0px">最短投资期限</td>
                                    <th  style="text-align:center;padding-left: 0px">特点</td>
                                  </tr>
                                  <tr>
                                    <td>投标直通车</td>
                                    <td>投标直通车根据散标利率<br>（预期年化收益率）</td>
                                    <td>1000元</td>
                                    <td>6个月</td>
                                    <td>自动循环投标、轻松高收益</td>
                                  </tr>
                                  <tr>
                                    <td>资产抵押标（车辆、房产等）</td>
                                    <td>>12%</td>
                                    <td>50元</td>
                                    <td>1个月</td>
                                    <td>自动投标、门槛低</td>
                                  </tr>
                                  <tr>
                                    <td>信用认证标</td>
                                    <td>>12%</td>
                                    <td>50元</td>
                                    <td>6个月</td>
                                    <td>自动投标、门槛低</td>
                                  </tr>
                                <!--   <tr>
                                    <td>机构担保标</td>
                                    <td>>12%</td>
                                    <td>50元</td>
                                    <td>1个月</td>
                                    <td>自动投标、门槛低</td>
                                  </tr> -->
                                  <%-- <tr>
                                    <td>净值标</td>
                                    <td>6%-24%</td>
                                    <td>50元</td>
                                    <td>1天</td>
                                    <td>自动投标、门槛低</td>
                                  </tr> --%>
                                </table>

            <p class="up" onclick="managemoney(2)"><a href="javascript:void(0);" id="help_title">&nbsp;</a></p>
         </div>
         <div class="help-title" onclick="managemoney(3)"><a href="javascript:void(0);" id="help_title">如何进行投资?<span></span></a></div>
         <div class="help-content" id="managemoney3">
            <p> 请您按照以下步骤进行投资：</p>
        
             <p>  1，在顶玺金融网站上点击注册，完成邮箱认证、手机认证、实名认证<%-- 、vip认证 --%>等认证；</p>
            
             <p>   2，账户充值；</p>
            
             <p>   3，浏览“我要投标“选择自己感兴趣的标的进行投资；</p>
            
             <p>   4，确认投资，投资成功。</p>
         
         
     


    
             <p class="up" onclick="managemoney(3)"><a href="javascript:void(0);" id="help_title">&nbsp;</a></p>
         </div>        
         <div class="help-title" onclick="managemoney(4)"><a href="javascript:void(0);" id="help_title">投资期限有多久?<span></span></a></div>
         <div class="help-content" id="managemoney4">
             <p>目前平台的投资产品期限为1-24个月，您可以根据您的实际投资情况选择。</p> 
             <p class="up" onclick="managemoney(4)"><a href="javascript:void(0);" id="help_title">&nbsp;</a></p>
         </div>        
  <!--        <div class="help-title" onclick="managemoney(5)"><a href="javascript:void(0);" id="help_title">投资是否有额外费用?<span></span></a></div>
         <div class="help-content" id="managemoney5">
         	<p>  理财资费说明：</p>
         	<table class="border">
               <tr >
                 <th  style="text-align:center;padding-left: 0px;width:100px;" >类别</td>
                 <th  style="text-align:center;padding-left: 0px;width:350px;">内容</td>
                 <th  style="text-align:center;padding-left: 0px">费用</td>
               </tr>
               <tr>
                 <td rowspan="2" style="text-align:center;">充值手续费</td>
                 <td style="text-align:center;">通过“京东支付、连连支付、富友支付”在线充值</td>
                 <td style="color:red;text-align:center;">免费</td>
               </tr>
               <tr>
                 <td style="text-align:center;">通过“新浪支付”充值，第三方支付平台</td>
                 <td style="text-align:center;">收取0.2%(最低收取1分)</td>
               </tr>
               <tr style="text-align:center;">
                 <td rowspan="2">提现手续费</td>
                 <td style="text-align:center;">充值投标回款金额申请提现(单笔100元~5万元)</td>
                 <td style="text-align:center;"><label style="color:red;">每月前三笔免费</label>，第4笔提现申请则按2元每笔收取</td>
               </tr>
               <tr>
                 <td style="text-align:center;">充值未投标金额可申请转可提<br/>（注：新注册用户首次充值未经投标将无法转可提）</td>
                 <td style="text-align:center;">转可提费为0.5%+2元提现费</td>
               </tr>
               <tr>
                 <td style="text-align:center;">利息管理费</td>
                 <td style="text-align:center;">2015年8月1日起所有理财用户投标回款</td>
                 <td style="text-align:center;">免利息管理费</td>
               </tr>
             </table>
             <p class="up" onclick="managemoney(5)"><a href="javascript:void(0);" id="help_title">&nbsp;</a></p>
         </div>    -->     
         <div class="help-title" onclick="managemoney(6)"><a href="javascript:void(0);" id="help_title">什么是到期还本息?<span></span></a></div>
         <div class="help-content" id="managemoney6">
             <p>即借款到期后，借款人一次还清本金与利息。</p> 
             <p class="up" onclick="managemoney(6)"><a href="javascript:void(0);" id="help_title">&nbsp;</a></p>
         </div>
         
         <div class="help-title" onclick="managemoney(7)"><a href="javascript:void(0);" id="help_title">什么是按月付息到期还本？<span></span></a></div>
         <div class="help-content" id="managemoney7">
             <p>指借款人在借款到期日一次性归还贷款本金，利息按月归还。</p> 
             <p class="up" onclick="managemoney(7)"><a href="javascript:void(0);" id="help_title">&nbsp;</a></p>
         </div>        
 