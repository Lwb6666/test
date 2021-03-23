<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
 
 ${param.isHome!=null?'<h2><a href="#">银行卡管理</a></h2>':'<h1><a href="#">银行卡管理</a></h1>' }
         <div class="help-title" onclick="list(24)"><a href="javascript:void(0);" id="help_title">提现银行卡信息填写错误怎么办？<span></span></a></div>
         <div class="help-content" id="list24">
             <p>提现银行卡信息填写错误怎么办？您可以点击：【账户管理】- <a class="blues-c" href="${path }/bankinfo/toBankCard.html" >【银行卡信息】</a> -点击【更换】，在弹出的“更换银行卡”页面提交信息，并点击下一步，更换银行卡需要等待客服审核并进行手持身份证及银行卡的视频录制。</p> 
             <p class="up" onclick="list(24)"><a href="javascript:void(0);" id="help_title">&nbsp;</a></p>
         </div>        
         <div class="help-title" onclick="list(25)"><a href="javascript:void(0);" id="help_title">如何提现？<span></span></a></div>
         <div class="help-content" id="list25">
             <p>您可以随时将您在顶玺金融账户中的可用资金申请提现到您绑定的提现银行卡账户中。 <a href="${path }/myaccount/cashRecord/toCashIndex.html" class="blue" >查看操作流程>></a>   </p>
			 <p>注意：申请提现的银行卡账号，应确保该账号的开户人姓名和您在顶玺金融网站上提供的身份证上的真实姓名一致，否则无法成功提现。</p> 
             <p class="up" onclick="list(25)"><a href="javascript:void(0);" id="help_title">&nbsp;</a></p>
         </div>        
              
         <div class="help-title" onclick="list(28)"><a href="javascript:void(0);" id="help_title">如果所填写的银行卡信息不正确，能否提现成功？<span></span></a></div>
         <div class="help-content" id="list28">
             <p>1. 如果您填写的银行卡号有误或该银行卡开户名不是您实名认证的姓名，不会审核通过，客服会联系您核对信息。</p>
             <p>2. 如果您填写的银行开户行名称、开户支行名称等信息不正确，则该笔提现在提交后可能由于信息校验不正确而审核不通过，客服会联系您核对信息。</p> 
             <p class="up" onclick="list(28)"><a href="javascript:void(0);" id="help_title">&nbsp;</a></p>
         </div>        
         <div class="help-title" onclick="list(29)"><a href="javascript:void(0);" id="help_title">提现未到账怎么办？<span></span></a></div>
         <div class="help-content" id="list29">
             <p>1. 可能是银行卡信息填写有误，客服没有联系到您，无法确认银行卡正确信息，请直接联系在线客服。</p>
             <p>2. 可能银行到账时间有所延迟，请直接联系在线客服，或拨打财富热线（400-000-0000）。</p> 
             <p class="up" onclick="list(29)"><a href="javascript:void(0);" id="help_title">&nbsp;</a></p>
         </div>        
         <div class="help-title" onclick="list(30)"><a href="javascript:void(0);" id="help_title">为什么会提现失败？<span></span></a></div>
         <div class="help-content" id="list30">
             <p>造成您取现失败的原因可能有以下几种：</p>
             <p>1，银行账号/户名错误，或是账号和户名不符；</p>
             <p>2，提现手续费过高；</p>
             <p>3，新注册用户首次充值未投标；</p>
             <p>4，使用信用卡提现。</p>
             <p>如果遇到以上情况，我们会在收到提现失败的通知后致电给您，告知原因，请您不必担心资金安全。</p> 
             <p class="up" onclick="list(30)"><a href="javascript:void(0);" id="help_title">&nbsp;</a></p>
         </div>        
 