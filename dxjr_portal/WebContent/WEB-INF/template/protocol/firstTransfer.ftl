<#setting number_format="#,##0.00">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
</head>

<body style="margin:0;padding:0;height:100%;width:100%;font-family:Microsoft YaHei,'微软雅黑',SimSun, Arial,Helvetica,Tahoma;color:#555; background:#fff;_background-attachment:fixed; font-size:13px;">
<!--内容开始-->
<div style="padding:10px 10px; margin:0 auto;">
<h5 style="margin:0;padding:0;text-align:center; padding:20px 0 20px 0; font-size:18px;">“投标直通车”服务计划转让协议</h5>
<div style="margin:0;padding:0;">
    <p style="margin:0;padding:0;padding:0px 10px 10px 0px; line-height:2em;overflow-x:hidden; "><strong>甲方（转让人）: &nbsp; </strong>顶玺金融注册用户名： <span style="text-decoration:underline">${transferUsername}</span></p>
    <p style="margin:0;padding:0;padding:0px 10px 10px 0px; line-height:2em;overflow-x:hidden; "><strong>乙方（受让人）: &nbsp; </strong>顶玺金融注册用户名： <span style="text-decoration:underline">${subscribeUsername}</span></p>
    <p style="padding:0px 10px 10px 0px; line-height:2em;overflow-x:hidden; ">鉴于甲乙双方均为上海顶玺金融信息服务有限公司拥有的<span style="letter-spacing:0.1px;"> www.dxjr.com </span>网站（以下简称”顶玺金融网站”）的注册用户，并已仔细阅读、充分理解并愿意遵守顶玺金融网站上有关“投标直通车”服务计划下整体债权转让的各项规则，并将据此承担相应法律责任。现甲方欲通过顶玺金融网站向乙方转让通过投标直通车功能购买的债权及剩余资金，经协商一致，甲乙双方达成以下协议，以资共同遵守。</p>
    <p style="margin:0;padding:0;padding:0px 10px 10px 0px; line-height:2em;overflow-x:hidden; "><strong>第一条&nbsp;转让信息</strong></p>
    <p style="margin:0;padding:0;padding:0px 10px 10px 0px; line-height:2em;overflow-x:hidden; ">1、投标直通车服务计划的相关信息</p>
    <table style="margin:0;padding:0;margin: 0px auto 20px;border-collapse: collapse;border: 1px solid #838383;width:90%;font-size: 12px;">
      <tr>
	  	<td style="margin:0;padding:0;border: 1px solid #838383; padding:10px 4px;" width="100px;" align="center">投标直通车服务计划的开通时间</td>
	  	<td style="margin:0;padding:0;border: 1px solid #838383; padding:10px 4px;" width="100px;" align="center">${firstTransfer.realAddtime?string('yyyy年MM月dd日')}</td>
      </tr>
      <tr>
	  	<td style="margin:0;padding:0;border: 1px solid #838383; padding:10px 4px;" width="100px;" align="center">排队号</td>
	  	<td style="margin:0;padding:0;border: 1px solid #838383; padding:10px 4px;" width="100px;" align="center">${firstTransfer.realOrdernum?string("###0")}</td>
      </tr>
      <tr>
	  	<td style="margin:0;padding:0;border: 1px solid #838383; padding:10px 4px;" width="100px;" align="center">债权转让标的债权价格总额（A）</td>
	  	<td style="margin:0;padding:0;border: 1px solid #838383; padding:10px 4px;" width="100px;" align="center">${borrowAccountSum}</td>
      </tr>
      <tr>
	  	<td style="margin:0;padding:0;border: 1px solid #838383; padding:10px 4px;" width="100px;" align="center">剩余现金（B）</td>
	  	<td style="margin:0;padding:0;border: 1px solid #838383; padding:10px 4px;" width="100px;" align="center">${firstTransfer.realUseMoney}</td>
      </tr>
      <tr>
	  	<td style="margin:0;padding:0;border: 1px solid #838383; padding:10px 4px;" width="100px;" align="center">给予受让方奖励（C）</td>
	  	<td style="margin:0;padding:0;border: 1px solid #838383; padding:10px 4px;" width="100px;" align="center">${firstTransfer.awards}</td>
      </tr>
      <tr>
	  	<td style="margin:0;padding:0;border: 1px solid #838383; padding:10px 4px;" width="100px;" align="center">直通车转让价格（D）</td>
	  	<td style="margin:0;padding:0;border: 1px solid #838383; padding:10px 4px;" width="100px;" align="center">${firstTransfer.accountReal}</td>
      </tr>
    </table>
    <p style="margin:0;padding:0;padding:0px 10px 10px 0px; line-height:2em;overflow-x:hidden; ">&nbsp;&nbsp;&nbsp;&nbsp;直通车转让价格的计算方式：D=A+B-C</p>
    <p style="margin:0;padding:0;padding:0px 10px 10px 0px; line-height:2em;overflow-x:hidden; ">2、债权标的信息</p>
    <table style="margin:0;padding:0;margin: 0px auto 20px;border-collapse: collapse;border: 1px solid #838383;width:90%;font-size: 12px;">
      <tr>
      <td style="margin:0;padding:0;border: 1px solid #838383; padding:10px 4px;" width="50px;" align="center">序号</td>
      <td style="margin:0;padding:0;border: 1px solid #838383; padding:10px 4px;" width="150px;" align="center">借款标题</td>
      <td style="margin:0;padding:0;border: 1px solid #838383; padding:10px 4px;" width="155px;" align="center">发布时间</td>
      <td style="margin:0;padding:0;border: 1px solid #838383; padding:10px 4px;" width="100px;" align="center">还款方式</td>	
      <td style="margin:0;padding:0;border: 1px solid #838383; padding:10px 4px;" width="70px;" align="center">周期</td>
      <td style="margin:0;padding:0;border: 1px solid #838383; padding:10px 4px;" width="70px;" align="center">期限</td>
      <td style="margin:0;padding:0;border: 1px solid #838383; padding:10px 4px;" width="130px;" align="center">利率</td>
      <td style="margin:0;padding:0;border: 1px solid #838383; padding:10px 4px;" width="130px;" align="center">转让价格</td>
      </tr>
      <#if firstTransferBorrowList?exists>
      <#list firstTransferBorrowList as firstTransferBorrow>
	  <tr>
        <td style="margin:0;padding:0;border: 1px solid #838383; text-align:left; padding:10px 4px;"><label style="color: red">${(firstTransferBorrow_index+1)?string.number}</label></td>
        <td style="margin:0;padding:0;border: 1px solid #838383; text-align:left; padding:10px 4px;"><label style="color: red">${firstTransferBorrow.borrowName}</label></td>
        <td style="margin:0;padding:0;border: 1px solid #838383; text-align:left; padding:10px 4px;"><label style="color: red;letter-spacing:0.1px;">${firstTransferBorrow.publishTimeDate?string('yyyy-MM-dd')}</label></td>
        <td style="margin:0;padding:0;border: 1px solid #838383; text-align:left; padding:10px 4px;">
	        <label style="color: red">
	        	<#if firstTransferBorrow.borrowStyle == 0>
					没有限制
				<#elseif firstTransferBorrow.borrowStyle == 1>
					等额本息
				<#elseif firstTransferBorrow.borrowStyle == 2>
					按月付息
				<#elseif firstTransferBorrow.borrowStyle == 3>
					到期还本
				<#elseif firstTransferBorrow.borrowStyle == 4>
					按天还款 
				</#if>
	        </label>
        </td>
        <td style="margin:0;padding:0;border: 1px solid #838383; text-align:left; padding:10px 4px;">
	        <label style="color: red">
	        	<#if firstTransferBorrow.borrowtype == 4>
					秒还
				<#elseif firstTransferBorrow.borrowtype != 4 && firstTransferBorrow.borrowStyle != 4>
					${firstTransferBorrow.borrowTimeLimit?string.number }月
				<#elseif firstTransferBorrow.borrowtype != 4 && firstTransferBorrow.borrowStyle == 4>
					${firstTransferBorrow.borrowTimeLimit?string.number }天
				</#if>
	        </label>
        </td>
        <td style="margin:0;padding:0;border: 1px solid #838383; text-align:left; padding:10px 4px;"><label style="color: red">${firstTransferBorrow.startOrder?string.number }/${firstTransferBorrow.borrowOrder?string.number}</label></td>
        <td style="margin:0;padding:0;border: 1px solid #838383; text-align:left; padding:10px 4px;"><label style="color: red">${firstTransferBorrow.borrowApr?string}%</label></td>
        <td style="margin:0;padding:0;border: 1px solid #838383; text-align:left; padding:10px 4px;"><span style="color:red;display:block;float:left;">￥</span><label style="color:red;float:left;">${firstTransferBorrow.account}</label></td>
	  </tr>
	  </#list>
	  </#if>
    </table>
    <p style="margin:0;padding:0;padding:0px 10px 10px 0px; line-height:2em;overflow-x:hidden; "><strong>第二条&nbsp;转让条件及流程</strong></p>
    <p style="margin:0;padding:0;padding:0px 10px 10px 0px; line-height:2em;overflow-x:hidden; ">1、甲乙双方均需为顶玺金融网站的注册用户；在遵守顶玺金融网站上有关投标直通车服务计划转让的各项规则的前提下，双方通过自行或授权其他方通过顶玺金融网站进行有关投标直通车服务计划下债权及剩余资金的整体转让。</p>
    <p style="margin:0;padding:0;padding:0px 10px 10px 0px; line-height:2em;overflow-x:hidden; ">2、甲方欲进行转让，必须将其以投标直通车服务计划下购买的全部债权标的及账户内的剩余现金余额一并转让，不得拆分；乙方作为受让方必须一次性支付相应对价，不得分期付款。</p>
    <p style="margin:0;padding:0;padding:0px 10px 10px 0px; line-height:2em;overflow-x:hidden; ">3、甲方欲转让本次服务计划需在顶玺金融网站上提出申请，待顶玺金融审核通过后方可进行转让，除非发生以下几种情况：</p>
    <p style="margin:0;padding:0;padding:0px 10px 10px 0px; line-height:2em;overflow-x:hidden; ">&nbsp;&nbsp;&nbsp;&nbsp;A当直通车所投的借款标在提前还款时，系统扫描撤销债权转让；</p>
    <p style="margin:0;padding:0;padding:0px 10px 10px 0px; line-height:2em;overflow-x:hidden; ">&nbsp;&nbsp;&nbsp;&nbsp;B当直通车所投的借款标的第二日为应还款日时，系统扫描撤销债权转让；</p>
    <p style="margin:0;padding:0;padding:0px 10px 10px 0px; line-height:2em;overflow-x:hidden; ">&nbsp;&nbsp;&nbsp;&nbsp;C在转让过程中，甲方撤销转让申请；</p>
    <p style="margin:0;padding:0;padding:0px 10px 10px 0px; line-height:2em;overflow-x:hidden; ">&nbsp;&nbsp;&nbsp;&nbsp;D乙方的户余额不足。</p>
    <p style="margin:0;padding:0;padding:0px 10px 10px 0px; line-height:2em;overflow-x:hidden; ">4、甲方欲转让债权，需按照顶玺金融网站上的投标直通车服务计划转让规则支付转让费用，即购买时起3个月内转让的按直通车开通价格1%支付转让费用，3个月以后转让的按直通车开通价格的0.5%支付转让费用。当乙方支付合理对价时，系统扣除转让费用后将转让价款支付到甲方账户中。投标直通车服务计划转让成功时起，乙方成为该投标直通车服务计划下的服务用户，按照甲方原有顺位享有权利承担义务。</p>
    <p style="margin:0;padding:0;padding:0px 10px 10px 0px; line-height:2em;overflow-x:hidden; ">5、在转让期间，甲方账户直通车余额不参与直通车自动投标。</p>
    <p style="margin:0;padding:0;padding:0px 10px 10px 0px; line-height:2em;overflow-x:hidden; "><strong>第三条&nbsp;承诺与保证</strong></p>
    <p style="margin:0;padding:0;padding:0px 10px 10px 0px; line-height:2em;overflow-x:hidden; ">1、甲方保证其所转让的本服务计划下认购的全部债权及剩余现金是其合法拥有，拥有完全的所有权。若因所有权归属等问题而引起的纠纷，甲方应自行解决。否则，甲方应承担由此而引起的所有经济和法律责任。    </p>
    <p style="margin:0;padding:0;padding:0px 10px 10px 0px; line-height:2em;overflow-x:hidden; ">2、甲方应保证按照顶玺金融网站关于投标直通车服务计划转让规则中的相关规定按时足额缴纳转让费用。</p>
    <p style="margin:0;padding:0;padding:0px 10px 10px 0px; line-height:2em;overflow-x:hidden; "> 3、乙方应确保其所用于受让本服务计划下认购的全部债权及剩余现金的资金来源合法。若因资金归属问题而引起的纠纷，乙方应自行解决并承担相应的责任。    </p>
    <p style="margin:0;padding:0;padding:0px 10px 10px 0px; line-height:2em;overflow-x:hidden; "> 4、乙方的锁定期从转让成功之日起开始计算，锁定期为6个月。6个月后方可申请解锁。    </p>
    <p style="margin:0;padding:0;padding:0px 10px 10px 0px; line-height:2em;overflow-x:hidden; "> 5、乙方自转让成功之日起成为投标直通车服务计划的用户，顶玺金融的《“投标直通车”服务计划协议》开始对乙方生效。    </p>
    <p style="margin:0;padding:0;padding:0px 10px 10px 0px; line-height:2em;overflow-x:hidden; "><strong>第四条&nbsp;违约责任</strong></p>
    <p style="margin:0;padding:0;padding:0px 10px 10px 0px; line-height:2em;overflow-x:hidden; ">1、甲乙双方应严格履行本协议所约定的义务，非经双方协商一致，任何一方不得随意解除，否则应承担相应的违约责任。 </p>
    <p style="margin:0;padding:0;padding:0px 10px 10px 0px; line-height:2em;overflow-x:hidden; ">2、甲乙双方同意，若一方违反本协议的约定，致使对方遭受损失的，违约方须向守约方赔偿守约方因此遭受的一切损失； 双方均有过错的，应根据双方的过错程度，分别承担各自的违约责任。 </p>
    <p style=" position:absolute;right:10%;z-index:999;"><img src="${contextPath}/images/tuzhang.png"/></p>
    <p style="margin:0;padding:0;padding:0px 10px 10px 0px; line-height:2em;overflow-x:hidden; "><strong>第五条&nbsp;法律适用及争议解决</strong></p>
    <p style="margin:0;padding:0;padding:0px 10px 10px 0px; line-height:2em;overflow-x:hidden; ">1、本合同的签订、履行、终止、解释均适用中华人民共和国法律。若本合同中任何条款因现行法律被视为无效的，该条款的无效不影响其他条款的效力。</p>
    <p style="margin:0;padding:0;padding:0px 10px 10px 0px; line-height:2em;overflow-x:hidden; ">2、本合同在履行过程中，如发生任何争执或纠纷，双方应友好协商解决；若协商不成，任何一方可向行为发生地所在地人民法院提起诉讼。</p>
    <p style="margin:0;padding:0;padding:0px 10px 10px 0px; line-height:2em;overflow-x:hidden; "><strong>第六条&nbsp;合同效力</strong></p>
    <p style="margin:0;padding:0;padding:0px 10px 10px 0px; line-height:2em;overflow-x:hidden; ">1、甲乙双方均认可并接受本协议约定的内容，且顶玺金融网站审核通过时，本协议成立并生效。</p>
    <p style="margin:0;padding:0;padding:0px 10px 10px 0px; line-height:2em;overflow-x:hidden; ">2、本协议及其修改及补充协议均通过顶玺金融网站以电子文本形式制成，可有一份或者多份并且每一份均具有同等法律效力。甲乙双方共同委托顶玺金融在其专有服务器上保管本协议，并认可该形式电子合同的效力。</p>
    <p style="margin:0;padding:0;padding:0px 10px 10px 0px; line-height:2em;overflow-x:hidden; ">3、若本协议中的任何条款因现行法律法规的修改而无效，则该条款的无效不影响其他条款的效力。</p>
</div>
<!--内容结束-->
</div>
</body>
</html>
