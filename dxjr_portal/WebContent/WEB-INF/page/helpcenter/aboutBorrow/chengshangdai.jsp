<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/page/common/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
     <title>诚商贷_p2p平台借贷_p2p融资借贷-顶玺金融官网</title>
	<meta name="keywords" content="诚商贷" />
	<meta name="description" content="顶玺金融诚商贷适用于在当地工商行政管理部门登记的企业法人、合伙人以及工商户。顶玺金融p2p借贷平台操作简单，融资快捷，是你借款首选的借贷平台，详情请登陆www.dxjr.com。">

<%@ include file="/WEB-INF/page/common/public.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link href="${basePath}/css/help.css" rel="stylesheet" type="text/css" />
</head>
<body>
	<%@ include file="/WEB-INF/page/common/header.jsp"%>
	<div id="Container">
		<div id="Bmain">
			<div class="title">
				<span class="home"><a href="${path}/">顶玺金融</a></span> <span><a
					href="${path }/bangzhu.html">帮助中心</a></span> <span>关于借款</span>
			</div>
			<div id="menu_centert">
				<%@ include file="/WEB-INF/page/helpcenter/leftMenu.jsp"%>
				<div id="helpctr">
   <div class="content1" style="display: block;">     
           
                                        <div class="help-content help-inline">
                                          <table border="0">
                                           <tr>
                                           <th style="background:#23acce" width="25%"><h1>诚商贷</h1></th>
                                           <th style="background:#23acce"></th>            
                                           </tr>  
                                           <tr style="background:#e1e4e9">
                                           <td>产品介绍</td>    
                                           <td><p>诚商贷-适用于在当地工商行政管理部门登记的企业法人、合伙人以及工商户</p></td>
                                           </tr>
                                           <tr style="background:#fafbfc">
                                           <td>申请条件</td>
                                           <td><p>1、具备完全民事行为能力</p>
                                               <p>2、有当地市范围内户口</p>
                                               <p>3、有固定的经营场地或地址</p>
                                               <p>4、经营合法，有稳定、足够的经营收入作为第一还款来源</p>
                                               <p>5、能提供合法、足值、易于变现的抵（质）押物</p>
                                               <p>6、在公司指定银行开立存款账户</p>
                                           </td>
                                           </tr>
                                          <tr style="background:#e1e4e9">
                                           <td>借款年利率</td>
                                           <td><p>大于12%</p></td>
                                           </tr>
                                           <tr style="background:#fafbfc">
                                           <td>借款期限</td>
                                           <td><p>单笔流动资金贷款时间不超过一年</p></td>
                                           </tr>
                                           <tr style="background:#e1e4e9">
                                           <td>还款方式</td>
                                           <td><p>等额本息，按月付息到期还本，到期还本付息，按天还款</p></td>
                                           </tr>
                                           <tr class="help-dott">
                                           <td rowspan="4"><font size="+1">必要申请资料</font></td>
                                           <td>
                                           <p><span>1、</span>身份认证（请提供下面两项资料）</p>
                                            <div class="help-width">
                                            <div class="help-contain"><img src="${basePath}/images/img20.png" width="129" height="71"/><span>本人身份证原件的正反两面照片</span></div>  
                                            <div class="help-contain"><img src="${basePath}/images/img21_03.png" width="129" height="71"/><span>本人手持身份证的正面头部照</span></div>
                                           </td>
                                           </tr>
                                           <tr class="help-dott">
                                           <td>
                                               <p><span>2、</span>信用报告（可去当地银行打印，部分地区可登录个人信息服务平台）</p>
                                               <div class="help-width">
                                                <div class="help-contain help-tx"><img src="${basePath}/images/img21_07.png" width="86" height="105"/></div>
                                                <div class="help-contain help-tw">个人信用报告（连接全国各地征信中心联系方式查询和个人信息服务平台）<br />
                                                信用报告有效期为15天，您必须在15天内提供所有必要的认证材料</div>  
                                              
                                            </td>
                                            </tr>
                                            <tr>
                                            <td>
                                           <p><span>3、</span>工作认证（请提供下面任意一项资料）</p>
                                          <div class="help-width">
                                            <div class="help-contain"><img src="${basePath}/images/img23.png" width="129" height="71"/><span>营业执照正、副本需营业时间满一年</span></div>  
                                            <div class="help-contain"><img src="${basePath}/images/img22.png" width="129" height="71"/><span>经营场地租赁合同90天内的租金发票或水电单据</span></div> 
                                            </td>
                                            <tr class="help-dott">
                                           <td>
                                           <p><span>4、</span>收入认证（请提供下面任意一项资料）</p>
                                            <div class="help-width">
                                            <div class="help-contain"><img src="${basePath}/images/img21_22.png" width="129" height="71"/><span>银行流水单 可体现经营状况的最近6个月对公或个人流水</span></div>  
                                            <div class="help-contain"><img src="${basePath}/images/img21_24.png" width="129" height="71"/><span>网银电脑截屏可体现工资项的最近6个月的网银截屏</span></div>
                                           </td>
                                           </tr>
                                            <tr>
                                           <td colspan="2"> <div class="gg_btn">
                                          <input type="button" value="立即申请"  style="cursor: pointer;" onclick="window.location.href='${path }/commerceBorrow/initApply.html?viewType=help';"/>
                                           </div></td>
                                           </tr>
                                        </table> 
                                        </div>
                                                                  
         
      </div>  
</div>
	
			</div>
		</div>
	</div>
	<div class="clearfix"></div>
	<%@ include file="/WEB-INF/page/common/footer.jsp"%>
<script type="text/javascript">
// changeMenu2(6,0,0);
/**
 * 后台验证返回信息
 */
if(''!='${msg}'){
	layer.alert('${msg}');
	if(''!='${scrollToHeight}'){window.scrollTo(0,'${scrollToHeight}');}
}
</script>
</body>
</html>