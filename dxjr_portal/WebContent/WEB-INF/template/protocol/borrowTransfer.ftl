<#setting number_format="#,##0.00">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
</head>

<body style="margin:0;padding:0;height:100%;width:100%;font-family:Microsoft YaHei,'微软雅黑',SimSun, Arial,Helvetica,Tahoma;color:#555; background:#fff;_background-attachment:fixed; font-size:13px;">
<!--内容开始-->
<div style="padding:10px 10px; margin:0 auto;">
<h5 style="margin:0;padding:0;text-align:center; padding:20px 0 20px 0; font-size:18px;">债权转让协议</h5>
<div style="margin:0;padding:0;">
    <p style="margin:0;padding:0;padding:0px 10px 10px 0px; line-height:2em;overflow-x:hidden; "><strong>甲方（债权人）: &nbsp; </strong>顶玺金融注册用户名： <span style="text-decoration:underline">${firstTransferVo.userName}</span></p>
    <p style="margin:0;padding:0;padding:0px 10px 10px 0px; line-height:2em;overflow-x:hidden; "><strong>乙方（受让人）: &nbsp; </strong>顶玺金融注册用户名：</p>
    <table style="margin:0;padding:0;margin: 0px auto 20px;border-collapse: collapse;border: 1px solid #838383;width:90%;font-size: 12px;">
     <tr>
	 <td style="margin:0;padding:0;border: 1px solid #838383; text-align:left; padding:10px 4px;" width="50%">顶玺金融注册用户（&lt;受让人名单&gt;详见附件一）</td>
     <td style="margin:0;padding:0;border: 1px solid #838383; text-align:left; padding:10px 4px;" width="50%">债权转让金额</td>
     </tr>
     <tr>
     <td style="margin:0;padding:0;border: 1px solid #838383; text-align:left; padding:10px 4px;">总计: &nbsp; <span style="text-decoration:underline">1</span> &nbsp; 名</td>
     <td style="margin:0;padding:0;border: 1px solid #838383; text-align:left; padding:10px 4px;">总计： &nbsp; <span style="text-decoration:underline">${firstTransferBorrowVo.account}</span></td>
     </tr>
    </table>
    <p style="padding:0px 10px 10px 0px; line-height:2em;overflow-x:hidden; ">鉴于甲乙双方均为上海顶玺金融信息服务有限公司拥有的<span style="letter-spacing:0.1px;"> www.dxjr.com </span>网站（以下简称”顶玺金融网站”）的注册用户，并已仔细阅读、充分理解并愿意遵守顶玺金融网站上有关债权转让的各项规则，并将据此承担相应法律责任。现甲方欲通过顶玺金融网站向乙方转让债权，经协商一致，甲乙双方达成以下债权转让协议，以资共同遵守。</p>
     
    <p style="margin:0;padding:0;padding:0px 10px 10px 0px; line-height:2em;overflow-x:hidden; "><strong>第一条&nbsp;债权转让相关信息</strong></p>
    <p style="margin:0;padding:0;padding:0px 10px 10px 0px; line-height:2em;overflow-x:hidden; ">1、债权标的信息</p>
    <p style="margin:0;padding:0;padding:0px 10px 10px 0px; line-height:2em;overflow-x:hidden; ">(1)债务人（借款人）顶玺金融网站注册用户名：<span style="text-decoration:underline">${firstTransferVo.userName}</span></p>
    <p style="margin:0;padding:0;padding:0px 10px 10px 0px; line-height:2em;overflow-x:hidden; ">(2)借款标题 ：<span style="text-decoration:underline">${firstTransferBorrowVo.borrowName}</span></p>
    <p style="margin:0;padding:0;padding:0px 10px 10px 0px; line-height:2em;overflow-x:hidden; ">(3)借款协议编号 ：<span style="text-decoration:underline">${borrowVo.contractNo}</span></p>
    <p style="margin:0;padding:0;padding:0px 10px 10px 0px; line-height:2em;overflow-x:hidden; ">(4)借款本金数额 ：人民币（大写）<span style="text-decoration:underline">${borrowAccountToChinese}</span></p>
    <p style="margin:0;padding:0;padding:0px 10px 10px 0px; line-height:2em;overflow-x:hidden; ">(5)借款预期利率 ：<span style="text-decoration:underline">${firstTransferBorrowVo.borrowApr?string}%</span></p>
    <p style="margin:0;padding:0;padding:0px 10px 10px 0px; line-height:2em;overflow-x:hidden; ">(6)还款方式 ：<span style="text-decoration:underline">${firstTransferBorrowVo.borrowStyleStr}</span></p>
    <p style="margin:0;padding:0;padding:0px 10px 10px 0px; line-height:2em;overflow-x:hidden; ">2、转让的债权标的信息</p>
    <p style="margin:0;padding:0;padding:0px 10px 10px 0px; line-height:2em;overflow-x:hidden; ">(1)债权转让价值：<span style="text-decoration:underline">${firstTransferBorrowVo.account?string}</span></p>
    <p style="margin:0;padding:0;padding:0px 10px 10px 0px; line-height:2em;overflow-x:hidden; ">(2)转让系数 ：<span style="text-decoration:underline">${firstTransferVo.coef}</span></p>
    <p style="margin:0;padding:0;padding:0px 10px 10px 0px; line-height:2em;overflow-x:hidden; ">(3)转让日期 ：<span style="text-decoration:underline;letter-spacing:0.1px;">${firstTransferVo.successTime?string('yyyy-MM-dd')}</span></p>
    <p style="margin:0;padding:0;padding:0px 10px 10px 0px; line-height:2em;overflow-x:hidden; ">(4)剩余还款期数 ：<span style="text-decoration:underline">${firstTransferBorrowVo.remainPeriod}期</span></p>
    <p style="margin:0;padding:0;padding:0px 10px 10px 0px; line-height:2em;overflow-x:hidden; ">(5)债权转让费用 ：<span style="text-decoration:underline">${firstTransferVo.manageFee}</span></p>
    <p style="margin:0;padding:0;padding:0px 10px 10px 0px; line-height:2em;overflow-x:hidden; "><strong>第二条&nbsp;债权转让条件及流程</strong></p>
    <p style="margin:0;padding:0;padding:0px 10px 10px 0px; line-height:2em;overflow-x:hidden; ">1、甲乙双方均需为顶玺金融网站的注册用户；在遵守顶玺金融网站上有关债权转让的各项规则的前提下，双方通过自行或授权其他方通过顶玺金融网站进行债权转让。</p>
    <p style="margin:0;padding:0;padding:0px 10px 10px 0px; line-height:2em;overflow-x:hidden; ">2、甲方欲转让债权需在顶玺金融网站上提出书面申请，待顶玺金融审核通过后方可进行转让。</p>
    <p style="margin:0;padding:0;padding:0px 10px 10px 0px; line-height:2em;overflow-x:hidden; ">3、自债权转让成功时起，乙方成为原借款合同项下的债权人，享有该合同约定的权利并承担相应的义务。</p>
    <p style="margin:0;padding:0;padding:0px 10px 10px 0px; line-height:2em;overflow-x:hidden; "><strong>第三条&nbsp;承诺与保证</strong></p>
    <p style="margin:0;padding:0;padding:0px 10px 10px 0px; line-height:2em;overflow-x:hidden; ">1、甲方保证其所转让的债权是其合法拥有，拥有完全的所有权。若因所有权归属等问题而引起的纠纷，甲方应自行解决。否则，甲方应承担由此而引起的所有经济和法律责任。    </p>
    <p style="margin:0;padding:0;padding:0px 10px 10px 0px; line-height:2em;overflow-x:hidden; ">2、甲方应保证按照顶玺金融网站债权转让规则中的相关规定按时足额缴纳债权转让费用。</p>
    <p style="margin:0;padding:0;padding:0px 10px 10px 0px; line-height:2em;overflow-x:hidden; "> 3、乙方应确保其所用于受让债权的资金来源合法。若因资金归属问题而引起的纠纷，乙方应自行解决并承担相应的责任。    </p>
    <p style="margin:0;padding:0;padding:0px 10px 10px 0px; line-height:2em;overflow-x:hidden; "><strong>第四条&nbsp;违约责任</strong></p>
    <p style="margin:0;padding:0;padding:0px 10px 10px 0px; line-height:2em;overflow-x:hidden; ">1、甲乙双方应严格履行本协议所约定的义务，非经双方协商一致，任何一方不得随意解除，否则应承担相应的违约责任。 </p>
    <p style="margin:0;padding:0;padding:0px 10px 10px 0px; line-height:2em;overflow-x:hidden; ">2、甲乙双方同意，若一方违反本协议的约定，致使对方遭受损失的，违约方须向守约方赔偿守约方因此遭受的一切损失；双方均有过错的，应根据双方的过错程度，分别承担各自的违约责任。 </p>
    <p style="margin:0;padding:0;padding:0px 10px 10px 0px; line-height:2em;overflow-x:hidden; "><strong>第五条&nbsp;法律适用及争议解决</strong></p>
    <p style="margin:0;padding:0;padding:0px 10px 10px 0px; line-height:2em;overflow-x:hidden; ">1、本合同的签订、履行、终止、解释均适用中华人民共和国法律。若本合同中任何条款因现行法律被视为无效的，该条款的无效不影响其他条款的效力。</p>
    <p style="margin:0;padding:0;padding:0px 10px 10px 0px; line-height:2em;overflow-x:hidden; ">2、本合同在履行过程中，如发生任何争执或纠纷，双方应友好协商解决；若协商不成，任何一方可向丙方所在地人民法院提起诉讼。 </p>
    <p style=" position:absolute;right:10%;z-index:999;"><img src="${contextPath}/images/tuzhang.png"/></p>
    <p style="margin:0;padding:0;padding:0px 10px 10px 0px; line-height:2em;overflow-x:hidden; "><strong>第六条&nbsp;合同效力</strong></p>
    <p style="margin:0;padding:0;padding:0px 10px 10px 0px; line-height:2em;overflow-x:hidden; ">1、甲乙双方均认可并接受本协议约定的内容，且顶玺金融网站审核通过甲方转让其债权时，本协议成立；待甲方收到乙方转让价款时本协议即时生效。</p>
    <p style="margin:0;padding:0;padding:0px 10px 10px 0px; line-height:2em;overflow-x:hidden; ">2、本协议及其修改及补充协议均通过顶玺金融网站以电子文本形式制成，可有一份或者多份并且每一份均具有同等法律效力。甲乙双方共同委托顶玺金融在其专有服务器上保管本协议，并认可该形式电子合同的效力。</p>
    <p style="margin:0;padding:0;padding:0px 10px 10px 0px; line-height:2em;overflow-x:hidden; ">3、若本协议中的任何条款因现行法律法规的修改而无效，则该条款的无效不影响其他条款的效力。</p>
	<p style="margin:0;padding:0;padding:0px 10px 10px 10px; line-height:2em;overflow-x:hidden; ">附件一：受让人名单</p>
      <table style="margin:0;padding:0;margin: 0px auto 20px;border-collapse: collapse;border: 1px solid #838383;width:90%;font-size: 12px;">
      <tr>
      <td style="margin:0;padding:0;border: 1px solid #838383; padding:10px 4px;" width="100px;" align="center">受让人ID</td>
      <td style="margin:0;padding:0;border: 1px solid #838383; padding:10px 4px;" width="100px;" align="center">债权价格</td>
      <td style="margin:0;padding:0;border: 1px solid #838383; padding:10px 4px;" width="70px;" align="center">剩余还款期数</td>
      <td style="margin:0;padding:0;border: 1px solid #838383; padding:10px 4px;" width="65px;" align="center">预期利率</td>	
      <td style="margin:0;padding:0;border: 1px solid #838383; padding:10px 4px;" width="130px;" align="center">还款方式</td>
      <td style="margin:0;padding:0;border: 1px solid #838383; padding:10px 4px;" width="140px;" align="center">应还款日</td>
      <td style="margin:0;padding:0;border: 1px solid #838383; padding:10px 4px;" width="140px;" align="center">到期应收本息</td>
      </tr>
	  <tr>
        <td style="margin:0;padding:0;border: 1px solid #838383; text-align:left; padding:10px 4px;"><label style="color: red">
       		    <#if userId == firstTransferVo.userIdVo>
		  				 ${firstTransferVo.subscribeUsername}
				<#else>
			  			${firstTransferVo.subscribeUserNameSecret}
			  	</#if>
        </label></td>
        <td style="margin:0;padding:0;border: 1px solid #838383; text-align:left; padding:10px 4px;"><span style="color:red;display:block;float:left;">￥</span><label style="color: red;float:left;">${firstTransferBorrowVo.account}</label></td>
        <td style="margin:0;padding:0;border: 1px solid #838383; text-align:left; padding:10px 4px;"><label style="color: red">${firstTransferBorrowVo.remainPeriod}期</label></td>
        <td style="margin:0;padding:0;border: 1px solid #838383; text-align:left; padding:10px 4px;"><label style="color: red">${firstTransferBorrowVo.borrowApr?string}%</label></td>
        <td style="margin:0;padding:0;border: 1px solid #838383; text-align:left; padding:10px 4px;"><label style="color: red">${firstTransferBorrowVo.borrowStyleStr}</label></td>
        <td style="margin:0;padding:0;border: 1px solid #838383; text-align:left; padding:10px 4px;"><label style="color: red">${borrowVo.endTimeDate?string('yyyy-MM-dd')}</label></td>
        <td style="margin:0;padding:0;border: 1px solid #838383; text-align:left; padding:10px 4px;"><span style="color:red;display:block;float:left;">￥</span><label style="color: red;float:left;">${firstTransferBorrowVo.borrowCollectionAccount}</label></td>
	  </tr>
      </table>
</div>
<!--内容结束-->
</div>
</body>
</html>
