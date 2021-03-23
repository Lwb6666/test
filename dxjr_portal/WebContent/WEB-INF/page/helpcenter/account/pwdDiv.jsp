<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>

${param.isHome!=null?'<h2><a href="#">账户密码</a></h2>':'<h1><a href="#">账户密码</a></h1>' }
         <div class="help-title" onclick="list(10)"><a href="javascript:void(0);" id="help_title">密码类别<span></span></a></div>
         <div class="help-content" id="list10">
             <p>登录密码</p>
             <p>登录顶玺金融账号时，需要输入的密码。</p>
             <p>交易密码</p>
             <p>投标、修改提现银行卡信息、提现时需要输入的密码，与银行卡的密码无关。为了账户安全“登录密码”与“交易密码”需设置不同。</p> 
             <p class="up" onclick="list(10)"><a href="javascript:void(0);" id="help_title">&nbsp;</a></p>
         </div>        
         <div class="help-title" onclick="list(11)"><a href="javascript:void(0);" id="help_title">登录密码<span></span></a></div>
         <div class="help-content" id="list11">
             <p>在“我的账户”>>“账户管理”>>“安全中心”进行修改或找回。</p> 
             <p class="up" onclick="list(11)"><a href="javascript:void(0);" id="help_title">&nbsp;</a></p>
         </div>        
         <div class="help-title" onclick="list(12)"><a href="javascript:void(0);" id="help_title">交易密码<span></span></a></div>
         <div class="help-content" id="list12">
             <p>在“我的账户”>>“账户管理”>>“安全中心”进行修改或找回。</p> 
             <p class="up" onclick="list(12)"><a href="javascript:void(0);" id="help_title">&nbsp;</a></p>
         </div>        
         <div class="help-title" onclick="list(15)"><a href="javascript:void(0);" id="help_title">找回密码方式<span></span></a></div>
         <div class="help-content" id="list15">
             <p>A，通过邮箱找回。 </p> 
             <p>B，通过手机找回。 </p> 
             <p>温馨提示：如果无法找回，你可以联系在线客服或拨打财富热线（400-000-0000）寻求帮助。 </p> 
             
             <p class="up" onclick="list(15)"><a href="javascript:void(0);" id="help_title">&nbsp;</a></p>
         </div>  
