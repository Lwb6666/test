<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>

 ${param.isHome!=null?'<h2><a href="#">如何投标</a></h2>':'<h1><a href="#">如何投标</a></h1>' }
         <div class="help-title" onclick="list(30)"><a href="javascript:void(0);" id="help_title">平台都有哪些标？ <span></span></a></div>
         <div class="help-content" id="list30">
             <p>目前平台有资产抵押标、信用认证标等。</p> 
             <p class="up" onclick="list(30)"><a href="javascript:void(0);" id="help_title">&nbsp;</a></p>
         </div>        
         <div class="help-title" onclick="list(31)"><a href="javascript:void(0);" id="help_title">平台官方标的预期利率是多少？<span></span></a></div>
         <div class="help-content" id="list31">
             <p>目前平台官方标的预期利率大于12%。</p> 
             <p class="up" onclick="list(31)"><a href="javascript:void(0);" id="help_title">&nbsp;</a></p>
         </div> 
         <div class="help-title" onclick="list(32)"><a href="javascript:void(0);" id="help_title">顶玺金融的投标方式有哪些？ <span></span></a></div>
         <div class="help-content" id="list32">
             <p>目前平台的投标方式有三种：投标直通车投标、自动投标、手动投标。<br/>
                &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;温馨提示：投标直通车投标的范围是平台大于等于20万的抵押标（房产抵押、车辆抵押）、信用标、担保标。</p> 
             <p class="up" onclick="list(32)"><a href="javascript:void(0);" id="help_title">&nbsp;</a></p>
         </div>        
         <div class="help-title" onclick="list(33)"><a href="javascript:void(0);" id="help_title">  参加投标直通车有什么好处？<span></span></a></div>
         <div class="help-content" id="list33">
             <p>投标直通车是一种投标功能，参加投标直通车的用户，会优先投官方发布的大于等于20万的抵押标（房产抵押、车辆抵押）、信用标、担保标，资金的利用率会更高。</p> 
             <p class="up" onclick="list(33)"><a href="javascript:void(0);" id="help_title">&nbsp;</a></p>
         </div>        
         <div class="help-title" onclick="list(34)"><a href="javascript:void(0);" id="help_title">如何参加直通车投标？<span></span></a></div>
         <div class="help-content" id="list34">
             <p>点击“我要投资”>>“直通车专区”，进行手动认购，最低认购金额为1000元，认购的金额为1000元的整倍数（例如1000、2000、8000…….）。</p>
             
             <p class="up" onclick="list(34)"><a href="javascript:void(0);" id="help_title">&nbsp;</a></p>
         </div>        
            
          <div class="help-title" onclick="list(36)"><a href="javascript:void(0);" id="help_title">投标直通车投标是如何排队的？<span></span></a></div>
         <div class="help-content" id="list36">
             <p>直通车投标以认购的时间为顺序依次进行投标。</p> 
             <p class="up" onclick="list(36)"><a href="javascript:void(0);" id="help_title">&nbsp;</a></p>
         </div>
                 <div class="help-title" onclick="list(37)"><a href="javascript:void(0);" id="help_title">投标直通车是怎么退出的？<span></span></a></div>
         <div class="help-content" id="list37">
             <p>投标直通车开通满6个月后的任意时间，用户可选择“我的账户”>>“投标管理”>>“直通车列表”申请手动解锁，退出时平台不收取任何费用。该用户直通车所投标回款后，本息自动进入到用户的可用资金账户。</p> 
             <p class="up" onclick="list(37)"><a href="javascript:void(0);" id="help_title">&nbsp;</a></p>
         </div>  
                 <div class="help-title" onclick="list(38)"><a href="javascript:void(0);" id="help_title">如何设置自动投标？<span></span></a></div>
         <div class="help-content" id="list38">
             <p><%-- 设置自动投标的用户必须是VIP客户。<br> --%> 
                                        设置方式：点击“我的账户”>>“投标管理”>>“自动投标”>><a href="${path}/myaccount/autoInvest/autoInvestMain.html" class="blue" >“添加自动投标”</a>。
                <br>  
                &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;温馨提示：请您认真阅读自动投标设置细则，避免操作错误。</p>
             <p class="up" onclick="list(38)"><a href="javascript:void(0);" id="help_title">&nbsp;</a></p>
         </div>  
         
         <div class="help-title" onclick="list(39)"><a href="javascript:void(0);" id="help_title">投标专区中的标，标题前显示【密】字，认购时需要输入的定向密码是什么？<span></span></a></div>
         <div class="help-content" id="list39">
             <p>官方标设置定向密码说明此标只可手动认购，密码会在标题上显示（888888）；
             <br>
       			<%-- &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;净值标的定向标密码是投资人自己设置的，说明已有指定的出借人。 --%></p>
             
             <p class="up" onclick="list(39)"><a href="javascript:void(0);" id="help_title">&nbsp;</a></p>
         </div>
                 