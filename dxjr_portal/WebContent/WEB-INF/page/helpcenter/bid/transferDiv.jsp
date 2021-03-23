<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
 
 ${param.isHome!=null?'<h2><a href="#">债权转让</a></h2>':'<h1><a href="#">债权转让</a></h1>' }
         <div class="help-title" onclick="managemoney(47)"><a href="javascript:void(0);" id="help_title">什么是债权转让？<span></span></a></div>
         <div class="help-content" id="managemoney47">
             <p>指债权持有人通过顶玺金融债权转让平台将债权挂出，将所持有的债权转让给购买人的操作。</p> 
             <p class="up" onclick="managemoney(47)"><a href="javascript:void(0);" id="help_title">&nbsp;</a></p>
         </div>        
         <div class="help-title" onclick="managemoney(48)"><a href="javascript:void(0);" id="help_title">什么样的债权可以转让？<span></span></a></div>
         <div class="help-content" id="managemoney48">
             <p>借款标处在还款中状态可发起转让。逾期的借款标不可以转让。借款标处于提前还款或第二日是应还款日时，系统自动扫描所有转让标的，并将它们自动流标。</p> 
             <p class="up" onclick="managemoney(48)"><a href="javascript:void(0);" id="help_title">&nbsp;</a></p>
         </div>        
         <div class="help-title" onclick="managemoney(49)"><a href="javascript:void(0);" id="help_title">如何转出债权？<span></span></a></div>
         <div class="help-content" id="managemoney49">
             <p>当您持有的债权处于可转让状态时，您可以打开
             	【我的账户】--【债权转让】--【债权转出】--<a href="${path}/zhaiquan/totransfercontainer.html" class="blue" >【可转让债权】</a>
            	 页面进行债权转让操作。
            </p> 
             <p class="up" onclick="managemoney(49)"><a href="javascript:void(0);" id="help_title">&nbsp;</a></p>
         </div>        
         <div class="help-title" onclick="managemoney(50)"><a href="javascript:void(0);" id="help_title">如何买入债权？<span></span></a></div>
         <div class="help-content" id="managemoney50">
             <p>您可以在
             	【我要投资】--<a href="${path}/zhaiquan.html" class="blue" >【债权转让】</a>
            	 页面进行债权的购买操作。
             </p> 
             <p class="up" onclick="managemoney(50)"><a href="javascript:void(0);" id="help_title">&nbsp;</a></p>
         </div>        
         <div class="help-title" onclick="managemoney(51)"><a href="javascript:void(0);" id="help_title">买入的债权收益是多少？<span></span></a></div>
         <div class="help-content" id="managemoney51">
             <p>具体收益由您所投资的转让债权标的利率及出让人设置的转让系数确定。</p> 
             <p class="up" onclick="managemoney(51)"><a href="javascript:void(0);" id="help_title">&nbsp;</a></p>
         </div>        
         <div class="help-title" onclick="managemoney(52)"><a href="javascript:void(0);" id="help_title">转让管理费如何收取？<span></span></a></div>
         <div class="help-content" id="managemoney52">
             <p>债权转让的费用为转让管理费。平台向转出人收取，不向购买人收取任何费用。 转让管理费金额为剩余未还本金*转让管理费率，转让管理费率自2016年7月1日起按0.5%收取，具体金额以债权转让页面显示为准。债权转让管理费在成交后直接从成交金额中扣除，不成交平台不向用户收取转让管理费。
			 <!-- 特别说明：终身顶级会员享受免债权转让管理费特权 --> </p> 
             <p class="up" onclick="managemoney(52)"><a href="javascript:void(0);" id="help_title">&nbsp;</a></p>
         </div> 
         <div class="help-title" onclick="managemoney(53)"><a href="javascript:void(0);" id="help_title">什么情况下债权的价值会被锁定？<span></span></a></div>
         <div class="help-content" id="managemoney53">
             <p>在转让发起到结束的过程中，债权价格会处于锁定状态，金额不随时间发生变化。</p> 
             <p class="up" onclick="managemoney(53)"><a href="javascript:void(0);" id="help_title">&nbsp;</a></p>
         </div>        
         <div class="help-title" onclick="managemoney(54)"><a href="javascript:void(0);" id="help_title">什么情况下购买债权会失败？<span></span></a></div>
         <div class="help-content" id="managemoney54">
            <p>a，当借款标的在提前还款时，系统扫描撤销债权转让；</p>
             <p>b，当借款标的第二日为应还款日时，系统扫描撤销债权转让；</p> 
             <p>c，债权转让方在转让过程中，撤销发布的债权(撤标)；</p> 
             <p>d，您账户余额不足。</p> 
             <p class="up" onclick="managemoney(54)"><a href="javascript:void(0);" id="help_title">&nbsp;</a></p>
         </div>
       
         <div class="help-title" onclick="managemoney(56)"><a href="javascript:void(0);" id="help_title">债权价格是如何计算的？<span></span></a></div>
         <div class="help-content" id="managemoney56">
             <p><strong>a </strong>，按天还款和到期还本付息</p>

			 <p><img src="${basePath}/images/gs1.png"/></p>
             <p>V ：债权价格</p>
             <p>Ar ：剩余未还本金</p>
             <p>D ：应计利息天数</p>
             <p>Dh ：总利息天数</p>
             <p>b% ：年利率</p>
             <p>R ：转让方投标时的会员等级对应的利息管理费百分比</p>
             <p><img src="${basePath}/images/gs2.png"/></p>
             <p>d<sub>now</sub> ：发布转让日期</p>
             <p>d<sub>due</sub> ：满标日期</p>
             <p>b% ：年利率</p>
             <p><strong>b</strong>，按月付息到期还本和等额本息</p>
             <p><img src="${basePath}/images/gs3.png"/></p>
             <p>D’ ：当期应计利息天数</p>
             <p>D<sub>h</sub>’：当期总利息天数</p>
             <p><img src="${basePath}/images/gs4.png"/></p>
             <p>d''<sub>due</sub>：上一期还款对应的应还款日期</p>
             <p>d'<sub>due</sub>：下一期还款对应的应还款日期</p>

             <p class="up" onclick="managemoney(56)"><a href="javascript:void(0);" id="help_title">&nbsp;</a></p>
         </div>        
         <div class="help-title" onclick="managemoney(57)"><a href="javascript:void(0);" id="help_title">债权的转让价格是如何确定的？<span></span></a></div>
         <div class="help-content" id="managemoney57">
             <p><img src="${basePath}/images/gs5.png"/></p> 
             <p>F ：转让价格</p> 
             <p>x ：转让系数(x范围在0.99-1.01之间,例如0.99、0.991、1)</p> 
             <p class="up" onclick="managemoney(57)"><a href="javascript:void(0);" id="help_title">&nbsp;</a></p>
         </div>        
         <div class="help-title" onclick="managemoney(58)"><a href="javascript:void(0);" id="help_title">什么叫折价转让？<span></span></a></div>
         <div class="help-content" id="managemoney58">
             <p>债权转出人在出售债权时，选择在债权价格上打折，给予债权购买人折扣的让利行为。即债权转出人将转让系数设置在1以下。</p> 
             <p class="up" onclick="managemoney(58)"><a href="javascript:void(0);" id="help_title">&nbsp;</a></p>
         </div>        
         <div class="help-title" onclick="managemoney(59)"><a href="javascript:void(0);" id="help_title">什么叫溢价转让？<span></span></a></div>
         <div class="help-content" id="managemoney59">
             <p>债权转出人在出售债权时，选择在债权价格上溢价出售，赚取债权购买人溢出的差价。即债权转出人将转让系数设置在1以上。</p> 
             <p class="up" onclick="managemoney(59)"><a href="javascript:void(0);" id="help_title">&nbsp;</a></p>
         </div> 
         <div class="help-title" onclick="managemoney(60)"><a href="javascript:void(0);" id="help_title">债权转让有哪些注意事项？<span></span></a></div>
         <div class="help-content" id="managemoney60">
             <p>a、所有债权转让仅限于理财用户之间互相转让；</p>
             <p>b、转让方不能认购自己转让的债权；</p>
             <p>c，债权处在转让中状态，债权价格不随时间发生变化。</p> 
             <p>d，转让价格不得低于50元，否则无法发起转让。</p> 
             <p class="up" onclick="managemoney(60)"><a href="javascript:void(0);" id="help_title">&nbsp;</a></p>
         </div>        
       
       <div class="help-title" onclick="managemoney(61)"><a href="javascript:void(0);" id="help_title">债权转让中的标，标题前显示【密】字，认购时需要输入的定向密码是什么？<span></span></a></div>
         <div class="help-content" id="managemoney61">
             <p>债权转让中的标定向密码是转让方自己设置的，说明已有指定的转入方。</p>
             <p class="up" onclick="managemoney(61)"><a href="javascript:void(0);" id="help_title">&nbsp;</a></p>
         </div> 
 