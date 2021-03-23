<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
 
${param.isHome!=null?'<h2><a href="#">安全认证</a></h2>':'<h1><a href="#">安全认证</a></h1>' }
         <div class="help-title" onclick="list(31)"><a href="javascript:void(0);" id="help_title">为何要实名认证？<span></span></a></div>
         <div class="help-content" id="list31">
             <p>为了确保您的账户安全，实名认证需要提供年满18周岁真实有效的身份证件，以便于系统在您提现时校验身份。</p> 
             <p class="up" onclick="list(31)"><a href="javascript:void(0);" id="help_title">&nbsp;</a></p>
         </div>        
         <div class="help-title" onclick="list(32)"><a href="javascript:void(0);" id="help_title">多个账户可否绑定同一张身份证？<span></span></a></div>
         <div class="help-content" id="list32">
             <p>为了确保您的账户安全，顶玺金融每个用户名只可绑定一个有效证件。</p> 
             <p class="up" onclick="list(32)"><a href="javascript:void(0);" id="help_title">&nbsp;</a></p>
         </div>        
         <div class="help-title" onclick="list(33)"><a href="javascript:void(0);" id="help_title">注册邮箱填错了怎么办？<span></span></a></div>
         <div class="help-content" id="list33">
             <p>您可以联系在线客服或拨打财富热线（400-000-0000）进行处理。</p> 
             <p class="up" onclick="list(33)"><a href="javascript:void(0);" id="help_title">&nbsp;</a></p>
         </div>        
         <div class="help-title" onclick="list(34)"><a href="javascript:void(0);" id="help_title">手机号填错了怎么办？<span></span></a></div>
         <div class="help-content" id="list34">
             <p>在“我的账户”>>“账户管理”>><a href="${path}/AccountSafetyCentre/safetyIndex.html" class="blue" >“安全中心”</a>进行修改设置。</p> 
             <p class="up" onclick="list(34)"><a href="javascript:void(0);" id="help_title">&nbsp;</a></p>
         </div>        
         <div class="help-title" onclick="list(35)"><a href="javascript:void(0);" id="help_title">多个账户可否绑定同一个手机号？<span></span></a></div>
         <div class="help-content" id="list35">
             <p>多个账号不可以绑定同一个手机号码。</p> 
             <p class="up" onclick="list(35)"><a href="javascript:void(0);" id="help_title">&nbsp;</a></p>
         </div>        
         <div class="help-title" onclick="list(36)"><a href="javascript:void(0);" id="help_title">交易密码是做什么用的？<span></span></a></div>
         <div class="help-content" id="list36">
             <p>投标、修改提现银行卡信息、提现时需要输入的密码，与银行卡的密码无关。为了账户安全“登录密码”与“交易密码”需设置不同。</p> 
             <p class="up" onclick="list(36)"><a href="javascript:void(0);" id="help_title">&nbsp;</a></p>
         </div> 
         <div class="help-title" onclick="list(37)"><a href="javascript:void(0);" id="help_title"> 如何找回登录密码和交易密码？<span></span></a></div>
         <div class="help-content" id="list37">
             <p>在“我的账户”>>“账户管理”>><a href="${path}/AccountSafetyCentre/safetyIndex.html" class="blue" >“安全中心”</a>进行找回。</p> 
             <p class="up" onclick="list(37)"><a href="javascript:void(0);" id="help_title">&nbsp;</a></p>
         </div>        
         <div class="help-title" onclick="list(38)"><a href="javascript:void(0);" id="help_title">如何修改密码？</a><span></span></div>
         <div class="help-content" id="list38">
             <p>在“我的账户”>>“账户管理”>><a href="${path}/AccountSafetyCentre/safetyIndex.html" class="blue" >“安全中心”</a>进行修改设置。</p> 
             <p class="up" onclick="list(38)"><a href="javascript:void(0);" id="help_title">&nbsp;</a></p>
         </div>        
         <div class="help-title" onclick="list(39)"><a href="javascript:void(0);" id="help_title">为何绑定提现银行卡？<span></span></a></div>
         <div class="help-content" id="list39">
             <p>为了提现时便于财务审核打款，绑定的银行卡必须是与注册实名一致的储蓄卡。 </p>
             <p class="up" onclick="list(39)"><a href="javascript:void(0);" id="help_title">&nbsp;</a></p>
         </div>        
     
       <div class="help-title" onclick="list(20)"><a href="javascript:void(0);" id="help_title"> 填写的银行卡信息不正确，能否提现成功？<span></span></a></div>
         <div class="help-content" id="list20">
             <p>银行信息不正确，提现不会成功，需进行修改。</p>
             <p class="up" onclick="list(20)"><a href="javascript:void(0);" id="help_title">&nbsp;</a></p>
         </div>  
 