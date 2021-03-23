<#setting number_format="#,##0.00">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
</head>

<body style="margin:0;padding:0;height:100%;width:100%;font-family:Microsoft YaHei,'微软雅黑',SimSun, Arial,Helvetica,Tahoma;color:#555; background:#fff;_background-attachment:fixed; font-size:12px;">
<!--内容开始-->
<div style="padding:10px 10px; margin:0 auto;">
<h5 style="margin:0;padding:0;text-align:center; padding:20px 0 20px 0; font-size:18px;">债权转让协议</h5>
<div style="margin:0;padding:0;">
    <p style="margin:0;padding:0;padding:0px 10px 10px 0px; line-height:2em;overflow-x:hidden; "><strong>甲方（转让人）:</strong>顶玺金融注册用户名： <span style="text-decoration:underline">${bTransferVo.userName}</span></p>
    <p style="margin:0;padding:0;padding:0px 10px 10px 0px; line-height:2em;overflow-x:hidden; "><strong>乙方（受让人）:</strong>顶玺金融注册用户名：</p>
    <table style="margin:0;padding:0;margin: 0px auto 20px;border-collapse: collapse;border: 1px solid #838383;width:90%;font-size: 12px;">
     <tr>
	 <td style="margin:0;padding:0;border: 1px solid #838383; text-align:left; padding:10px 4px;" width="50%">顶玺金融注册用户（&lt;受让人名单&gt;详见附件一）</td>
     <td style="margin:0;padding:0;border: 1px solid #838383; text-align:left; padding:10px 4px;" width="50%">债权转让金额</td>
     </tr>
     <tr>
     <td style="margin:0;padding:0;border: 1px solid #838383; text-align:left; padding:10px 4px;">总计: &nbsp; <span style="text-decoration:underline">${tenderTimes?string}</span> &nbsp;名</td>
     <td style="margin:0;padding:0;border: 1px solid #838383; text-align:left; padding:10px 4px;">总计：<span style="text-decoration:underline">${bTransferVo.accountReal}</span></td>
     </tr>
    </table>
    <p style="padding:0px 10px 10px 0px; line-height:2em;overflow-x:hidden; ">鉴于甲乙双方均为上海顶玺金融信息服务有限公司拥有的<span style="letter-spacing:0.1px;"> www.dxjr.com </span>网站（以下简称”顶玺金融网站”）的注册用户，并已仔细阅读、充分理解并愿意遵守顶玺金融网站上有关债权转让的各项规则，并将据此承担相应法律责任。现甲方欲通过顶玺金融网站向乙方转让债权，经协商一致，甲乙双方达成以下债权转让协议，以资共同遵守。</p>
     
    <p style="margin:0;padding:0;padding:0px 10px 10px 0px; line-height:2em;overflow-x:hidden; "><strong>第一条&nbsp;债权转让相关信息</strong></p>
    <p style="margin:0;padding:0;padding:0px 10px 10px 0px; line-height:2em;overflow-x:hidden; ">1、原始债权标的信息</p>
    
    <table style="margin:0;padding:0;margin: 0px auto 20px;border-collapse: collapse;border: 1px solid #838383;width:90%;font-size: 12px;">
      <tr>
	      <td style="margin:0;padding:0;border: 1px solid #838383; padding:10px 4px;" width="100px;" align="center">借款人顶玺金融网站注册用户名</td>
	      <td style="margin:0;padding:0;border: 1px solid #838383; padding:10px 4px;" width="100px;" align="center">${bTransferVo.borrowUserName}</td>
	      <td style="margin:0;padding:0;border: 1px solid #838383; padding:10px 4px;" width="70px;" align="center">借款合同编号</td>
	      <td style="margin:0;padding:0;border: 1px solid #838383; padding:10px 4px;" width="65px;" align="center">${borrowVo.contractNo}</td>	
      </tr>
      <tr>
	      <td style="margin:0;padding:0;border: 1px solid #838383; padding:10px 4px;" width="100px;" align="center">借款本金金额</td>
	      <td style="margin:0;padding:0;border: 1px solid #838383; padding:10px 4px;" width="100px;" align="center">${bTransferVo.borrowAccountToChinese}</td>
	      <td style="margin:0;padding:0;border: 1px solid #838383; padding:10px 4px;" width="70px;" align="center">借款期限</td>
	      <td style="margin:0;padding:0;border: 1px solid #838383; padding:10px 4px;" width="65px;" align="center">${bTransferVo.borrowTimeLimitStr}个月,${bTransferVo.borrowTime}~${bTransferVo.repayTime}</td>	
      </tr>
      <tr>
	      <td style="margin:0;padding:0;border: 1px solid #838383; padding:10px 4px;" width="100px;" align="center">借款预期利率</td>
	      <td style="margin:0;padding:0;border: 1px solid #838383; padding:10px 4px;" width="100px;" align="center">${bTransferVo.borrowApr?string}%</td>
	      <td style="margin:0;padding:0;border: 1px solid #838383; padding:10px 4px;" width="70px;" align="center">借款用途</td>
	      <td style="margin:0;padding:0;border: 1px solid #838383; padding:10px 4px;" width="65px;" align="center">${bTransferVo.borrowUse}</td>	
      </tr>
      <tr>
	      <td style="margin:0;padding:0;border: 1px solid #838383; padding:10px 4px;" width="100px;" align="center">还款方式</td>
	      <td style="margin:0;padding:0;border: 1px solid #838383; padding:10px 4px;" width="100px;" align="center">${bTransferVo.borrowStyleStr}</td>
	      <td style="margin:0;padding:0;border: 1px solid #838383; padding:10px 4px;" width="70px;" align="center">还款日</td>
	      <td style="margin:0;padding:0;border: 1px solid #838383; padding:10px 4px;" width="65px;" align="center">${bTransferVo.repayTimeStr}</td>	
      </tr>
      
      </table>
    
    <p style="margin:0;padding:0;padding:0px 10px 10px 0px; line-height:2em;overflow-x:hidden; ">2、转让的债权标的信息</p>
    
    <table style="margin:0;padding:0;margin: 0px auto 20px;border-collapse: collapse;border: 1px solid #838383;width:90%;font-size: 12px;">
      <tr>
	      <td style="margin:0;padding:0;border: 1px solid #838383; padding:10px 4px;" width="100px;" align="center">标的债权价值</td>
	      <td style="margin:0;padding:0;border: 1px solid #838383; padding:10px 4px;" width="100px;" align="center">${bTransferVo.account?string}</td>
	      <td style="margin:0;padding:0;border: 1px solid #838383; padding:10px 4px;" width="70px;" align="center">转让对价</td>
	      <td style="margin:0;padding:0;border: 1px solid #838383; padding:10px 4px;" width="65px;" align="center">${bTransferVo.accountReal?string}</td>	
      </tr>
      <tr>
	      <td style="margin:0;padding:0;border: 1px solid #838383; padding:10px 4px;" width="100px;" align="center">转让系数</td>
	      <td style="margin:0;padding:0;border: 1px solid #838383; padding:10px 4px;" width="100px;" align="center">${bTransferVo.coefStr}</td>
	      <td style="margin:0;padding:0;border: 1px solid #838383; padding:10px 4px;" width="70px;" align="center">转让日期</td>
	      <td style="margin:0;padding:0;border: 1px solid #838383; padding:10px 4px;" width="65px;" align="center">${bTransferVo.successTime?string('yyyy年MM月dd日')}</td>	
      </tr>
      <tr>
	      <td style="margin:0;padding:0;border: 1px solid #838383; padding:10px 4px;" width="100px;" align="center">剩余还款期数</td>
	      <td style="margin:0;padding:0;border: 1px solid #838383; padding:10px 4px;" width="100px;" align="center">${bTransferVo.remainPeriod}期</td>
	      <td style="margin:0;padding:0;border: 1px solid #838383; padding:10px 4px;" width="70px;" align="center">债权转让费用</td>
	      <td style="margin:0;padding:0;border: 1px solid #838383; padding:10px 4px;" width="65px;" align="center">${bTransferVo.manageFee}</td>	
      </tr>
     </table>
    
    <p style="margin:0;padding:0;padding:0px 10px 10px 0px; line-height:2em;overflow-x:hidden; "><strong>第二条&nbsp;债权转让条件</strong></p>
    <p style="margin:0;padding:0;padding:0px 10px 10px 0px; line-height:2em;overflow-x:hidden; ">1、甲乙双方均需为顶玺金融网站的注册用户。</p>
    <p style="margin:0;padding:0;padding:0px 10px 10px 0px; line-height:2em;overflow-x:hidden; ">2、转让债权时，甲方持有该标的债权当日及标的债权到期还款日当日不得转让。</p>
    <p style="margin:0;padding:0;padding:0px 10px 10px 0px; line-height:2em;overflow-x:hidden; "><strong>第三条&nbsp;债权转让流程</strong></p>
    <p style="margin:0;padding:0;padding:0px 10px 10px 0px; line-height:2em;overflow-x:hidden; ">1、甲乙双方同意并确认，双方在遵守顶玺金融网站上有关债权转让各项规则的前提下，在顶玺金融网站上进行债权转让和受让方购买操作等方式确认签署本协议。</p>
    <p style="margin:0;padding:0;padding:0px 10px 10px 0px; line-height:2em;overflow-x:hidden; ">2、甲乙双方接受本协议且顶玺金融网站审核通过时，本协议立即成立。协议成立的同时乙方不可撤销地授权顶玺金融自行或委托第三方支付机构将本协议约定的转让对价划转给甲方，甲方同意将其应缴纳给顶玺金融的债权转让管理费由顶玺金融自行或委托第三方支付机构直接在乙方支付的对价款中予以扣除。上述债权转让对价划转完成后即视为本协议生效且债权转让成功。</p>
    <p style="margin:0;padding:0;padding:0px 10px 10px 0px; line-height:2em;overflow-x:hidden; ">3、债权转让完成后，顶玺金融可根据实际情况将转让事项以电子邮件或站内信息的形式通知与标的债权对应的借款人。</p>
    <p style="margin:0;padding:0;padding:0px 10px 10px 0px; line-height:2em;overflow-x:hidden; ">4、自标的债权转让成功时起，协议双方以本协议为依据形成真实、合法、有效的债权转让行为。乙方成为原借款合同项下的债权人，任何法定或借款合同约定的与之相关的权利及义务随本协议的生效一并由乙方享有及承担。</p>
    <p style="margin:0;padding:0;padding:0px 10px 10px 0px; line-height:2em;overflow-x:hidden; "><strong>第四条&nbsp;承诺与保证</strong></p>
    <p style="margin:0;padding:0;padding:0px 10px 10px 0px; line-height:2em;overflow-x:hidden; ">1、甲方保证其所转让的债权是其合法拥有，拥有完全的所有权。若因所有权归属等问题而引起的纠纷，甲方应自行解决。否则，甲方应承担由此而引起的所有经济和法律责任。</p>
    <p style="margin:0;padding:0;padding:0px 10px 10px 0px; line-height:2em;overflow-x:hidden; ">2、甲方应保证按照顶玺金融网站债权转让规则中的相关规定向顶玺金融按时足额支付债权转让管理费。</p>
    <p style="margin:0;padding:0;padding:0px 10px 10px 0px; line-height:2em;overflow-x:hidden; ">3、乙方应确保其所用于购买受让债权标的的资金来源合法。若因资金归属问题而引起的纠纷，乙方应自行解决并承担相应的责任。    </p>
    <p style="margin:0;padding:0;padding:0px 10px 10px 0px; line-height:2em;overflow-x:hidden; "><strong>第五条&nbsp;违约责任</strong></p>
    <p style="margin:0;padding:0;padding:0px 10px 10px 0px; line-height:2em;overflow-x:hidden; ">1、甲乙双方应严格履行本协议所约定的义务，非经双方协商一致，任何一方不得随意解除，否则应承担相应的违约责任。 </p>
    <p style="margin:0;padding:0;padding:0px 10px 10px 0px; line-height:2em;overflow-x:hidden; ">2、甲乙双方同意，若一方违反本协议的约定，致使对方遭受损失的，违约方须向守约方赔偿守约方因此遭受的一切损失；双方均有过错的，应根据双方的过错程度，分别承担各自的违约责任。 </p>
    <p style="margin:0;padding:0;padding:0px 10px 10px 0px; line-height:2em;overflow-x:hidden; "><strong>第六条&nbsp;法律适用及争议解决</strong></p>
    <p style="margin:0;padding:0;padding:0px 10px 10px 0px; line-height:2em;overflow-x:hidden; ">1、本协议的签订、效力、解释、履行、修改、终止均适用中华人民共和国法律。</p>
    <p style="margin:0;padding:0;padding:0px 10px 10px 0px; line-height:2em;overflow-x:hidden; ">2、本协议在履行过程中，如发生任何争执或纠纷，双方应友好协商解决；若协商不成，任何一方可向上海顶玺金融信息服务有限公司住所地人民法院提起诉讼。 </p>
    <p style=" position:absolute;right:10%;z-index:999;"><img src="${contextPath}/images/tuzhang.png"/></p>
    <p style="margin:0;padding:0;padding:0px 10px 10px 0px; line-height:2em;overflow-x:hidden; "><strong>第七条&nbsp;其他</strong></p>
    <p style="margin:0;padding:0;padding:0px 10px 10px 0px; line-height:2em;overflow-x:hidden; ">1、甲乙双方对本协议的修改及补充经双方一致签署确认后具有与本协议同等的法律效力，是本协议的重要组成部分，具有与本协议同等的法律效力。</p>
    <p style="margin:0;padding:0;padding:0px 10px 10px 0px; line-height:2em;overflow-x:hidden; ">2、若本协议中的任何条款因现行法律法规的修改而无效，则该条款的无效不影响其他条款的效力。</p>
    <p style="margin:0;padding:0;padding:0px 10px 10px 0px; line-height:2em;overflow-x:hidden; ">3、除本协议上下文另有定义外，本协议项下的用语及定义均与顶玺金融网站有关规则中的定义相同。若有冲突，则以本协议为准。</p>
    <p style="margin:0;padding:0;padding:0px 10px 10px 0px; line-height:2em;overflow-x:hidden; ">4、本协议及其修改或补充均通过顶玺金融网站以电子文本形式制成，可有一份或者多份并且每一份均具有同等法律效力。甲乙双方共同委托顶玺金融在其专有服务器上保管本协议，并认可该形式电子合同的效力。</p>
	<p style="margin:0;padding:0;padding:0px 10px 10px 10px; line-height:2em;overflow-x:hidden; ">附件一：受让人名单</p>
      <table style="margin:0;padding:0;margin: 0px auto 20px;border-collapse: collapse;border: 1px solid #838383;width:90%;font-size: 12px;">
      <tr>
      <td style="margin:0;padding:0;border: 1px solid #838383; padding:10px 4px;" width="100px;" align="center">受让人ID</td>
      <td style="margin:0;padding:0;border: 1px solid #838383; padding:10px 4px;" width="100px;" align="center">认购金额</td>
      <td style="margin:0;padding:0;border: 1px solid #838383; padding:10px 4px;" width="70px;" align="center">剩余还款期数</td>
      <td style="margin:0;padding:0;border: 1px solid #838383; padding:10px 4px;" width="65px;" align="center">预期利率</td>	
      <td style="margin:0;padding:0;border: 1px solid #838383; padding:10px 4px;" width="130px;" align="center">还款方式</td>
      <td style="margin:0;padding:0;border: 1px solid #838383; padding:10px 4px;" width="140px;" align="center">应还款日</td>
      <td style="margin:0;padding:0;border: 1px solid #838383; padding:10px 4px;" width="140px;" align="center">到期应收本息</td>
      </tr>
      <#if bsubscribeVoList?exists>
      <#list bsubscribeVoList as bsubscribeVo>
	  <tr>
        <td style="margin:0;padding:0;border: 1px solid #838383; text-align:left; padding:10px 4px;"><label style="color: red">
      	  <#if userId == bsubscribeVo.userId>
	  				${bsubscribeVo.userName}
		  <#else>
		  			${bsubscribeVo.userNameSecret}
		  </#if>
        </label></td>
        <td style="margin:0;padding:0;border: 1px solid #838383; text-align:left; padding:10px 4px;"><span style="color:red;display:block;float:left;">￥</span><label style="color:red;font-size:12px;float:left;">${bsubscribeVo.account}</label></td>
        <td style="margin:0;padding:0;border: 1px solid #838383; text-align:left; padding:10px 4px;"><span style="color:red;float:left;">${bTransferVo.remainPeriod}</span><label style="color:red;float:left;">期</label></td>
        <td style="margin:0;padding:0;border: 1px solid #838383; text-align:left; padding:10px 4px;"><label style="color: red">${bTransferVo.borrowApr?string}%</label></td>
        <td style="margin:0;padding:0;border: 1px solid #838383; text-align:left; padding:10px 4px;"><label style="color: red">${bTransferVo.borrowStyleStr}</label></td>
        <td style="margin:0;padding:0;border: 1px solid #838383; text-align:left; padding:10px 4px;">
       		<span style="letter-spacing:0.1px;color:red;">${borrowVo.endTimeDate?string('yyyy-MM-dd')}</span>
        </td>
        <td style="margin:0;padding:0;border: 1px solid #838383; text-align:left; padding:10px 4px;"><span style="color:red;display:block;float:left;">￥</span><label style="color:red;font-size:12px;float:left;">${bsubscribeVo.repaymentAccount?string}</label></td>
	  </tr>
	  </#list>
	  </#if>
      <tr>
      <td style="margin:0;padding:0;border: 1px solid #838383; text-align:left; padding:10px 4px;"><div style="text-align:center;"><label style="color: red">总计</label></div></td>
      <td style="margin:0;padding:0;border: 1px solid #838383; text-align:left; padding:10px 4px;"><span style="color:red;display:block;float:left;">￥</span><label style="color:red;font-size:12px;float:left;">${sumAccount}</label></td>
      <td style="margin:0;padding:0;border: 1px solid #838383; text-align:left; padding:10px 4px;"></td>
      <td style="margin:0;padding:0;border: 1px solid #838383; text-align:left; padding:10px 4px;"></td>	
      <td style="margin:0;padding:0;border: 1px solid #838383; text-align:left; padding:10px 4px;"></td>
      <td style="margin:0;padding:0;border: 1px solid #838383; text-align:left; padding:10px 4px;"></td>
      <td style="margin:0;padding:0;border: 1px solid #838383; text-align:left; padding:10px 4px;"><span style="color:red;display:block;float:left;">￥</span><label style="color:red;font-size:12px;float:left;">${sumRepayAccount}</label></td>
      </tr>
      </table>
</div>
<!--内容结束-->
</div>
</body>
</html>
