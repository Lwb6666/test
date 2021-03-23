<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>

 ${param.isHome!=null?'<h2><a href="#">充值提现</a></h2>':'<h1><a href="#">充值提现</a></h1>' }  
         <div class="help-title" onclick="list(12)"><a href="javascript:void(0);" id="help_title">平台都有哪些充值渠道 ？<span></span></a></div>
         <div class="help-content" id="list12">
             <p>目前平台的充值渠道有：新浪支付、连连支付、京东支付、富友支付。</p> 
             <p class="up" onclick="list(12)"><a href="javascript:void(0);" id="help_title">&nbsp;</a></p>
         </div>        
         <div class="help-title" onclick="list(13)"><a href="javascript:void(0);" id="help_title">充值会不会收手续费？<span></span></a></div>
         <div class="help-content" id="list13">
             <p>顶玺账户：通过“京东支付、连连支付、富友支付”在线充值免充值手续费；通过新浪支付充值，第三方支付平台将收取千分之二的充值手续费，最低收取1分。</p> 
                                   <p>存管账户：当日充值无上限。无充值手续费。</p> 
             <p class="up" onclick="list(13)"><a href="javascript:void(0);" id="help_title">&nbsp;</a></p>
         </div> 
                <div class="help-title" onclick="list(14)"><a href="javascript:void(0);" id="help_title">如何开通网上银行？<span></span></a></div>
         <div class="help-content" id="list14">
             <p>目前所有商业银行都支持个人网银业务，您只需要携带有效身份证件，到开户行当地任意营业网点，即可申请开通网上银行业务；您还可以到商业银行官网查看个人网上银行详细信息。</p> 
             <p class="up" onclick="list(14)"><a href="javascript:void(0);" id="help_title">&nbsp;</a></p>
         </div>        
         <div class="help-title" onclick="list(16)"><a href="javascript:void(0);" id="help_title">充值有没有限额？<span></span></a></div>
         <div class="help-content" id="list16">
             <p>充值限额是由银行支付限额、第三方支付平台支付限额和用户自己设定的支付限额三者共同决定，限额取三者最小值。
             例如：某银行网银用户（办理U盾）的每笔支付限额是100万，但是该银行和<a href="http://help.weibopay.com/pay"  class="blue" >新浪支付</a>的协议规定用户使用该银行网银的每笔支付限额为30万，
             然而用户自己设定的每笔支付限额为10万，那么用户每次可以充值的金额为10万。 新浪支付与平台的限额详情<a href="${bbsPath }/posts/66039/1" class="blue" >请查看</a></p>
             
             <p class="up" onclick="list(16)"><a href="javascript:void(0);" id="help_title">&nbsp;</a></p>
         </div>        
                 <div class="help-title" onclick="list(17)"><a href="javascript:void(0);" id="help_title">充值没到账怎么办？<span></span></a></div>
         <div class="help-content" id="list17">
             <p>充值有时会延迟到账，请您耐心等一下！ 您还可以联系<a href="http://www.dxjr.com/onLineServices/webQQ.html"  class="blue" >在线客服</a>或拨打财富热线(400-000-0000)寻求帮助。</p> 
             <p class="up" onclick="list(17)"><a href="javascript:void(0);" id="help_title">&nbsp;</a></p>
         </div> 
                 <div class="help-title" onclick="list(18)"><a href="javascript:void(0);" id="help_title">申请提现后多久可以到账？<span></span></a></div>
         <div class="help-content" id="list18">
             <p>顶玺账户：周一至周五17：00前申请的提现到账时间是T+1个工作日，周末及节假日不处理提现。</p> 
             <p>存管账户：单次申请最高为50000元，提现当天到账，周末及节假日均可提现。</p> 
             <p class="up" onclick="list(18)"><a href="javascript:void(0);" id="help_title">&nbsp;</a></p>
         </div>
                 <div class="help-title" onclick="list(19)"><a href="javascript:void(0);" id="help_title">提现银行卡信息填写错误怎么办？<span></span></a></div>
         <div class="help-content" id="list19">
             <p>您可以联系在线客服或拨打财富热线（400-000-0000）进行处理。</p> 
             <p class="up" onclick="list(19)"><a href="javascript:void(0);" id="help_title">&nbsp;</a></p>
         </div>   
