<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/page/common/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ include file="/WEB-INF/page/common/public.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link href="${basePath}/css/help.css" rel="stylesheet" type="text/css" />
<title>帮助中心-名词管理_顶玺金融</title>
</head>
<body>
	<%@ include file="/WEB-INF/page/common/header.jsp"%>
	<div id="Container">
		<div id="Bmain">
			<div class="title">
				<span class="home"><a href="${path}/">顶玺金融</a></span> <span><a
					href="${path }/bangzhu.html">帮助中心</a></span> <span>名词解释</span>
			</div>
			<div id="menu_centert">
				<%@ include file="/WEB-INF/page/helpcenter/leftMenu.jsp"%>
		<div id="helpctr">		
		<div class="top">
        <div class="f18">A-B-C-D-E-F-G-H-I-J-K-L-M-N-O-P-Q-R-S-T-U-V-W-X-Y-Z</div>
    </div>
<div class="content" style="display: block;">     
          <h2>A - G</h2>  
         <div class="help-title" onclick="list(2)"><a href="javascript:void(0);" id="help_title">等额本息</a></div>
         <div class="help-content" id="list2">
             <p>等额本息还款法是一种被广泛采用的还款方式。在还款期内，每月偿还同等数额的借款(包括本金和利息)。借款人每月还款额中的本金比重逐月递增、利息比重逐月递减。</p>
            <p class="up" onclick="list(2)"><a href="javascript:void(0);" id="help_title">&nbsp;</a></p>
         </div>
         <div class="help-title" onclick="list(19)"><a href="javascript:void(0);" id="help_title">定向密码</a></div>
         <div class="help-content" id="list19">
             <p>1.官方标设置定向密码说明此标只可手动认购，密码会在标题上显示（888888）；</p>
             <%-- <p>2.净值标的定向标密码是借款人自己设置的，说明已有指定的出借人；</p> --%>
             <p>2.债权转让中的标定向密码是转让方自己设置的，说明已有指定的转入方。 </p>
            <p class="up" onclick="list(19)"><a href="javascript:void(0);" id="help_title">&nbsp;</a></p>
         </div>
         
         <h2>H - N</h2>
         <div class="help-title" onclick="list(3)"><a href="javascript:void(0);" id="help_title">借款用户（借款人）<span></span></a></div>
         <div class="help-content" id="list3">
            <p>已经或准备在网站上进行借款活动的用户称为借款用户。凡22-55周岁以上的中国大陆地区公民，都可以成为借款用户。</p> 
             <p class="up" onclick="list(3)"><a href="javascript:void(0);" id="help_title">&nbsp;</a></p>
         </div>        
         <!-- <div class="help-title" onclick="list(4)"><a href="javascript:void(0);" id="help_title">加权待收</a></div>
         <div class="help-content" id="list4">
             <p>加权待收=自注册之日起待收总额的累加/180 ，作为判定会员等级的依据。</p> 
             <p class="up" onclick="list(4)"><a href="javascript:void(0);" id="help_title">&nbsp;</a></p>
         </div> -->        
               
         <div class="help-title" onclick="list(6)"><a href="javascript:void(0);" id="help_title">借款管理费<span></span></a></div>
         <div class="help-content" id="list6">
             <p>顶玺金融将按照借款人的借款期限，每月向借款人收取其借款本金的一定比例作为借款管理费。</p> 
             <p class="up" onclick="list(6)"><a href="javascript:void(0);" id="help_title">&nbsp;</a></p>
         </div>        
         <div class="help-title" onclick="list(7)"><a href="javascript:void(0);" id="help_title">借款利率</a></div>
         <div class="help-content" id="list7">
             <p>借款利率是指借款申请人为了获得借款而愿意支付给出借人的预期利率。请注意，在目前的等额本息、每月还款的还款方式下，用户实际支付的年利息低于借款本金x预期利率。</p> 
             <p class="up" onclick="list(7)"><a href="javascript:void(0);" id="help_title">&nbsp;</a></p>
         </div>        
         <div class="help-title" onclick="list(8)"><a href="javascript:void(0);" id="help_title">出借用户</a></div>
         <div class="help-content" id="list8">
             <p>已经或准备在网站上进行资金出借活动并完成了实名认证、手机号码绑定和提现密码设置的用户称为出借用户。</p> 
             <p class="up" onclick="list(8)"><a href="javascript:void(0);" id="help_title">&nbsp;</a></p>
         </div>        
         <div class="help-title" onclick="list(9)"><a href="javascript:void(0);" id="help_title">预期利率<span></span></a></div>
         <div class="help-content" id="list9">
             <p>预期利率是把真实利率换算成以年为单位的利率。</p> 
             <p class="up" onclick="list(9)"><a href="javascript:void(0);" id="help_title">&nbsp;</a></p>
         </div>
         <div class="help-title" onclick="list(10)"><a href="javascript:void(0);" id="help_title">预期收益率<span></span></a></div>
         <div class="help-content" id="list10">
             <p>指投资人对应一年投资所获的收益率。</p> 
             <p class="up" onclick="list(10)"><a href="javascript:void(0);" id="help_title">&nbsp;</a></p>
         </div>        
          <h2>O - T</h2>    
         <div class="help-title" onclick="list(11)"><a href="javascript:void(0);" id="help_title">锁定期<span></span></a></div>
         <div class="help-content" id="list11">
             <p>投标直通车具有锁定期限制，锁定期6个月之内您不可操作退出投标直通车，锁定期满6个月后您可以手动申请解锁。</p> 
             <p class="up" onclick="list(11)"><a href="javascript:void(0);" id="help_title">&nbsp;</a></p>
         </div>  
          <h2>U - Z</h2>    
         <div class="help-title" onclick="list(12)"><a href="javascript:void(0);" id="help_title">信用认证<span></span></a></div>
         <div class="help-content" id="list12">
             <p>申请借款的用户需要根据不同的产品提交相应的信用认证材料，经过顶玺金融审核后获取相应的信用等级及借款额度。所需提供的认证资料分为必要认证资料和可选认证资料。相关阅读： <a href="${path}/bangzhu/14.html" class="blue">必要认证资料和可选认证资料有哪些？</a>   </p>             <p class="up" onclick="list(12)"><a href="javascript:void(0);" id="help_title">&nbsp;</a></p>
         </div>        
         <div class="help-title" onclick="list(13)"><a href="javascript:void(0);" id="help_title">信用报告</a></div>
         <div class="help-content" id="list13">
             <p>个人信用报告是由中国人民银行出具，全面记录个人信用活动，反映个人信用基本状况的文件。本报告是顶玺金融了解用户信用状况的一个重要参考资料。</p> 
             <p class="up" onclick="list(13)"><a href="javascript:void(0);" id="help_title">&nbsp;</a></p>
         </div>        
         <div class="help-title" onclick="list(14)"><a href="javascript:void(0);" id="help_title">信用审核<span></span></a></div>
         <div class="help-content" id="list14">
             <p>信用审核是指：申请借款的用户根据不同的产品填写借款信息，包括个人信息、家庭信息、工作信息、资产信息、信用信息，并提交相应的信用认证材料，随后信审部门综合评估借款人的个人、家庭、工作、资产、信用情况，最终根据借款人的整体信用资质给出相应的信用分数、信用等级及借款额度。</p> 
             <p class="up" onclick="list(14)"><a href="javascript:void(0);" id="help_title">&nbsp;</a></p>
         </div>        
         <div class="help-title" onclick="list(15)"><a href="javascript:void(0);" id="help_title">信用等级（分数）<span></span></a></div>
         <div class="help-content" id="list15">
             <p>信用等级由信用分数转化而来，每个信用等级都对应的信用分数范围，信用分数和信用等级是借款人的信用属性，也是出借人判断借款人违约风险的重要依据之一。通常来讲借款人信用等级越高，其违约率越低，相应的借款成功率越高。</p>
             <p class="up" onclick="list(15)"><a href="javascript:void(0);" id="help_title">&nbsp;</a></p>
         </div>  
         <div class="help-title" onclick="list(16)"><a href="javascript:void(0);" id="help_title">信用额度<span></span></a></div>
         <div class="help-content" id="list16">
             <p>用户的信用额度是在通过顶玺金融审核员对所提供材料的审核后获得的，既是借款人单笔借款的上限也是借款者累积尚未还清借款的上限。</p> 
             <p class="up" onclick="list(16)"><a href="javascript:void(0);" id="help_title">&nbsp;</a></p>
         </div>  
         <div class="help-title" onclick="list(17)"><a href="javascript:void(0);" id="help_title">逾期<span></span></a></div>
         <div class="help-content" id="list17">
             <p>指借款用户未按协议约定时间进行足额还款，此时标的状态为逾期。相关阅读： <a href="${path}/bangzhu/18.html" class="blue">逾期还款</a></p> 
             <p class="up" onclick="list(17)"><a href="javascript:void(0);" id="help_title">&nbsp;</a></p>
         </div>  
         <div class="help-title" onclick="list(18)"><a href="javascript:void(0);" id="help_title">投标直通车<span></span></a></div>
         <div class="help-content" id="list18">
             <p>是指以投资顶玺金融平台现有信贷产品（资产抵押标等）为基础的稳健、安全、便捷性好，透明度高的投标计划。加入投标直通车的资金将优先于平台普通用户的资金，根据计划设定的分散投资原则对顶玺金融平台产品进行优先投资。<br />
             </p> 
             <p class="up" onclick="list(18)"><a href="javascript:void(0);" id="help_title">&nbsp;</a></p>
         </div>  
         <div class="help-title" onclick="list(1)"><a href="javascript:void(0);" id="help_title">债权回购</a></div>
         <div class="help-content" id="list1" style="display: none;">
            <p>逾期的债权根据顶玺金融本金保障计划，由顶玺金融“风险备用金”先行回购未到期的本金和利息，回购完成后债权转移至顶玺金融名下。</p>
            <p class="up" onclick="list(1)"><a href="javascript:void(0);" id="help_title">&nbsp;</a></p>
         </div>
 </div>    
 </div>

				<script type="text/javascript">
					function list(i) {
						$("#list" + i).animate({
							height : 'toggle'
						});
					}
					$("#menu5").addClass("current");
				</script>
			</div>
		</div>
	</div>
	<div class="clearfix"></div>
	<%@ include file="/WEB-INF/page/common/footer.jsp"%>
</body>
</html>