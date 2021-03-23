<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
	 ${param.isHome!=null?'<h2><a href="#">注册登录</a></h2>':'<h1><a href="#">注册登录</a></h1>' }
         <div class="help-title" onclick="list(3)"><a href="javascript:void(0);" id="help_title">如何注册平台？<span></span></a></div>
         <div class="help-content" id="list3">
         	<p>A,通过顶玺金融官网注册；</p>
			<p>B,下载【顶玺金融】手机APP注册；</p>
			<p>C,通过关注【上海顶玺金融】服务号点击【我的账户】注册；</p>
			<p>D,通过营销广告（例如：高铁、投之家、网贷之家等）扫描二维码注册。</p>
          <p class="up" onclick="list(3)"><a href="javascript:void(0);" id="help_title">&nbsp;</a></p>
         </div>        
         <div class="help-title" onclick="list(5)"><a href="javascript:void(0);" id="help_title">注册时头像必须要上传？<span></span></a></div>
         <div class="help-content" id="list5">
             <p>无需上传头像，系统默认为顶玺LOGO。</p> 
             <p class="up" onclick="list(5)"><a href="javascript:void(0);" id="help_title">&nbsp;</a></p>
         </div>        
                 <div class="help-title" onclick="list(6)"><a href="javascript:void(0);" id="help_title">注册时，进行手机验证，收不到短信怎么办？<span></span></a></div>
         <div class="help-content" id="list6">
            <p>A，请确认手机是否安装短信拦截或过滤软件；</p>
            <p>B，请确认手机是否能够正常接收短信（信号问题、欠费、停机等）；</p>
            <p>C，短信收发过程中可能会因为短信运营商问题存在延迟，请耐心等待，短信在60秒内有效；</p>
            <p>D，您还可以联系<a href="http://www.dxjr.com/onLineServices/webQQ.html" class="blue" >在线客服</a>或拨打财富热线（400-000-0000）寻求帮助。</p> 
 
             <p class="up" onclick="list(6)"><a href="javascript:void(0);" id="help_title">&nbsp;</a></p>
         </div>        
         <div class="help-title" onclick="list(7)"><a href="javascript:void(0);" id="help_title">注册时，未收到认证邮件提醒怎么办？<span></span></a></div>
         <div class="help-content" id="list7">
             <p>请您在垃圾邮件中查找，如果没有，则可能是您邮箱服务器存在问题，你可以联系客服人员进行邮箱修改。</p> 
             <p class="up" onclick="list(7)"><a href="javascript:void(0);" id="help_title">&nbsp;</a></p>
         </div>        
         <%-- <div class="help-title" onclick="list(8)"><a href="javascript:void(0);" id="help_title">如何成为VIP？<span></span></a></div>
         <div class="help-content" id="list8">
             <p>进入“我的账户”点击<a href="${path }/AccountSafetyCentre/safetyIndex.html"   class="blue"  >VIP</a>图标即可申请。</p> 
             <p class="up" onclick="list(8)"><a href="javascript:void(0);" id="help_title">&nbsp;</a></p>
         </div>  
         <div class="help-title" onclick="list(9)"><a href="javascript:void(0);" id="help_title">    VIP认证如何收费有何特权？<span></span></a></div>
         <div class="help-content" id="list9">
             <p>目前VIP的费用为10元/年；<a href="${path}/AccountSafetyCentre/safetyIndex.html" class="blue" >VIP</a>客户可享受本息保障等特权；终身顶级会员享受免VIP会员费特权，终身享受本息保障等特权。</p> 
             <p class="up" onclick="list(9)"><a href="javascript:void(0);" id="help_title">&nbsp;</a></p>
         </div> --%> 
         <!-- <div class="help-title" onclick="list(10)"><a href="javascript:void(0);" id="help_title">关联人是什么？<span></span></a></div>
         <div class="help-content" id="list10">
             <p>关联人是出借人的紧急联络人。如果出借人的资金在网站长期休眠（不提现、不投标），而网站又无法联系出借人本人时，顶玺金融客服会通过关联人来唤醒出借人账户。</p> 
             <p class="up" onclick="list(10)"><a href="javascript:void(0);" id="help_title">&nbsp;</a></p>
         </div> --> 
         <div class="help-title" onclick="list(11)"><a href="javascript:void(0);" id="help_title">   注册成功之后用户名可以修改吗？<span></span></a></div>
         <div class="help-content" id="list11">
             <p>为了您的账户安全，用户名一旦注册成功不能进行修改。</p> 
             <p class="up" onclick="list(11)"><a href="javascript:void(0);" id="help_title">&nbsp;</a></p>
         </div> 
