<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ page import = "com.dxjr.portal.statics.BusinessConstants" %>
<%@ include file="/WEB-INF/page/common/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>顶玺金融互联网专业的P2P网站</title>
<link href="${basePath}/css/belstar.css" rel="stylesheet" type="text/css" />
<link href="${basePath}/css/Style.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${basePath}/js/jquery-1.11.0.js" ></script>
<script type="text/javascript" src="${basePath}/js/jquery-migrate-1.2.1.js" ></script>
<script type="text/javascript" src="${basePath}/js/BigDecimal-all-last.min.js"></script>

</head>
<body>
<div id="user_mian">
      <div id="user_mian_right">
			    <div class="xbanenr">
			    <a href="#"><img src="${basePath}/images/user_banner.png" width="875" height="57" border="0" /></a>
			    </div>
			    <div class="person">
				     <div class="person_manage">我的个人信息</div>
				     <div class="person_manage_st">
				       <table width="848" border="0">
                         <tr>
                           <td width="183" rowspan="4">
	                           	<a href="javascript:headuploadFunction()">
		                		<c:if test="${null==memberVo.headimg}">
		                			<img src="${basePath}/images/headimg/default.gif" width="120" height="120" style="cursor:pointer;" title="更换头像" />
		                		</c:if>
		                		<c:if test="${null!=memberVo.headimg}">
		                			<img src="${basePath}${memberVo.headimg }" width="120" height="120" style="cursor:pointer;" title="更换头像" />
		                		</c:if>
		                		</a>
                           </td>
                           <td width="283">用户名：<a href="javascript:showMemberInfo('${memberVo.username}');"  title="${memberVo.username }">${memberVo.username}</a></td>
                           <td width="368">上次登录地址：${sessionScope.lastOnLineCounterVo.city}</td>
                         </tr>
                         <tr>
                           <td>关联人 ： 
                             <c:if test="${accountLinkmanVo == null }"><a href="javascript:toLinkman();">尚无关联人  【 绑定】</a></c:if>
                        	 <c:if test="${accountLinkmanVo != null }"><a href="javascript:toLinkman();">${accountLinkmanVo.name }</a></c:if>
                           </td>
                           <td>上次登录时间：<fmt:formatDate value="${sessionScope.lastOnLineCounterVo.logintime }" pattern="yyyy-MM-dd HH:mm:ss"></fmt:formatDate></td>
                         </tr>
                         <tr>
                           <td> 会员级别：
	                           <c:if test="${userLevel == 0 }">
	                           	<a href="javascript:toUserLevel();" title="普通会员"><img src="${basePath}/images/s_red_1.gif" width="17" height="16" /></a>
	                           </c:if>
	                           <c:if test="${userLevel == 10 }">
	                           	<a href="javascript:toUserLevel();" title="金牌会员"><img src="${basePath}/images/s_gold_1.gif" width="17" height="16" /></a>
	                           </c:if>
	                           <c:if test="${userLevel == 20 }">
	                           	<a href="javascript:toUserLevel();" title="白金会员"><img src="${basePath}/images/s_while_1.gif" width="17" height="16" /></a>
	                           </c:if>
	                           <c:if test="${userLevel == 30 }">
	                           	<a href="javascript:toUserLevel();" title="钻石会员"><img src="${basePath}/images/s_blue_1.gif" width="17" height="16" /></a>
	                           </c:if>
	                           <c:if test="${userLevel == 40 }">
	                           	<a href="javascript:toUserLevel();" title="皇冠会员"><img src="${basePath}/images/s_cap_1.gif" width="17" height="16" /></a>
	                           </c:if>
	                           <c:if test="${userLevel == 50 }">
	                           	<a href="javascript:toUserLevel();" title="金皇冠会员"><img src="${basePath}/images/s_crown_1.gif" width="17" height="16" /></a>
	                           </c:if>
                           </td>
                           <td>
                           <a href="#">
						    	<img src="${basePath}/images/zl_pass.jpg" id="realName" width="40" height="23" border="0" title="实名认证"  onclick="realnameFun();"/>
						    </a>
						    <a href="#">
						    	<img src="${basePath}/images/mail_pass.jpg"width="29" height="23" border="0" title="邮件认证" onclick="emailFunction()"/> 
						    </a>
						    <a href="#">
						    	<c:if test="${null==memberApproVo.vipPassed || memberApproVo.vipPassed!='1'}">
						    		<img src="${basePath}/images/vip.jpg" width="29" height="23" border="0" title="VIP未认证" onclick="vipFunction()"/>
						    	</c:if>
						    	<c:if test="${memberApproVo.vipPassed=='1'}">
						    		<img src="${basePath}/images/vip_pass.jpg" width="29" height="23" border="0" title="VIP认证" onclick="vipFunction()"/>
						    	</c:if>
						    </a>
						    <a href="#">
						    	<img src="${basePath}/images/ph_pass.jpg" width="27" height="23" border="0" title="手机认证" onclick="mobileFunction()"/>
						    </a>
						    <a href="#">
						    	<c:if test="${null!=uploadDocCount }">
							    	<c:if test="${uploadDocCount == 0}">
							    		<img src="${basePath}/images/pople.jpg" width="27" height="23" border="0" title="未上传资料" onclick="upFunction()"/>
							    	</c:if>
							    	<c:if test="${uploadDocCount > 0}">
							    		<img src="${basePath}/images/pople_pass.jpg" width="27" height="23" border="0" title="上传资料" onclick="upFunction()"/>
							    	</c:if>
						    	</c:if>
						    </a>
                           </td>
                         </tr>
                         <tr>
                           <td style="color:red;">可用资金：￥<span id="use_money_s"></span></td>
                           <td><c:if test="${null==memberApproVo.vipPassed || memberApproVo.vipPassed!=1}">您目前不是VIP,<a href="#" onclick="vipFunction()">点击申请成为VIP会员</a></c:if></td>
                         </tr>
                         <tr>
                           <td><a href="javascript:headuploadFunction();">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;更改头像</a></td>
                           <td></td>
                           <td>
                           <input id="user_buttom" type="button" value="充 值" onclick="topupFunction();" style="cursor:pointer;"/> 
                           <input id="user_buttom"   type="button" value="提 现" onclick="withdrawFunction();" style="cursor:pointer;"/></td>
                         </tr>
                       </table>
					 </div>
					 
					 
					 <div class="xqing">
					       <div class="person_manage"><span class="person_manage_l">账户详情</span> <span class="person_managebs"><a href="javascript:void(0);" onclick="showAccountInfo();">账户详情说明</a></span></div>
                           <div class="persons_manage" id="account_info">
						      <table width="100%" border="0">
                                <tr>
                                  <td width="50%" valign="top"><p >1、资产总额=待收总额+可用资金+冻结总额+投标直通车余额+投标直通车冻结 </p>
                                    <p >2、待收总额=待收本金+待收利息 </p>
                                    <p >3、加权待收=自注册之日起待收总额的累加/180 </p>
                                    <p >4、已赚利息：即投标已还款所赚的利息。</p>
                                    <p >5、已赚奖励=实名认证奖励+推广奖励+线下充值奖励+每月提现费奖励+论坛奖励+活动奖励</p>
                                    <p >6、活动加权=每个抵押标投标金额*期限的累加<span class="red">（2013年9月26日至2014年3月31日期间）  </span></p>
                                    <p >7、活动奖励=活动加权金额*0.5% </p>
                                    <p >8、待还总额=待还本金+待还利息 </p>
                                    <p >9、=已赚利息+待收利息+已赚奖励+已收罚息+待收罚息</p>
                                  	<p >10、支出总额=VIP支出+已付利息管理费+待付利息管理费+借款管理费+已付罚息+待付罚息+提现费用+已付利息+待付利息</p>
                                  	<p >11、净收益=-支出总额 </p>
                                  	<p >12、投资总额=累计投标资金总额（包括秒标、净值标、信用标和抵押标） </p>
                                  </td>
                                  <td width="1%" valign="top">&nbsp;</td>
                                  <td width="49%" valign="top">
                                    <p >13、待收利息：投标未还款所赚的利息 </p>
                                    <p >14、论坛奖励=每个月论坛积分兑换成可用资金的总额 </p>
                                    <p >15、净充值=充值总额-提现总额 </p>
                                    <p >16、净值额度：当没有未还款的净值标时，净值额度=资产总额；反之，净值额度=（待收本金+可用资金+投标冻结+债转冻结）*0.6-待还本息 </p>
                                    <p >17、加权待收排名得分：取前200名，按照100-（名次-1）*0.5计算得出加权待收排名得分，200名之后得分为0</p>
                                    <p >18、累计总积分排名得分：取前200名，按照100-（名次-1）*0.5计算得出累计总积分排名得分，200名之后得分为0</p>
                                    <p >19、投标直通车投标总额排名得分：取前200名，按照100-（名次-1）*0.5计算得出投标直通车投标总额排名得分，200名之后得分为0</p>
                                    <p >20、推广人数排名得分：取前200名，按照100-（名次-1）*0.5计算得出推广人数排名得分，200名之后得分为0</p>
                                    <p >21、综合得分=加权待收排名得分*0.4+累计总积分排名得分*0.3+投标直通车投标总额排名得分*0.2+推广人数排名得分*0.1</p>
                                    </td>
                                </tr>
                              </table>
						    </div>
                            <div class="xqing_list">
							   <ul>
						          <li id="accountBase_li"> <a href="javascript:void(0);" onclick="toggleAccount(this,'accountBase');">账户详情</a></li>
							       <li id="invest_li"> <a href="javascript:void(0);" onclick="toggleAccount(this,'invest');">投资统计</a></li>
							      <li id="borrow_li"> <a href="javascript:void(0);" onclick="toggleAccount(this,'borrow');">借款统计</a></li> 
							  	  <li id="shareholderRank_li"> <a href="javascript:void(0);" onclick="toggleAccount(this,'shareholderRank');">股东加权统计</a></li> 
							   </ul>
						   </div>
						   <!--账户统计信息-->
			  			   <div id="user_main_tab" class="xqing_text"> </div>
						   <!--new st-->
						   <div class="xqing_news">
						            <div class="xqing_news_left">
										<div class="xqing_news_manage">
											<span class="list_lefts">精华帖</span>
										    <span class="list_rights">回复数/阅读数</span>
										</div>
									     <ul>
<%-- 									     	  <c:forEach items="${items_CategoryList}" var="item"> --%>
<%--                                               <li><div class="list"><span class="dot">·</span><a href="javascript:enterNotes(${item.id});" style="color: black" title="${item.title}">${fn:substring(item.title,0,15)}</a><i>${item.notesNum}/${item.clickNum}</i></div></li> --%>
<%-- 										 	  </c:forEach> --%>
										 </ul>
									</div>
									
									
						            <div class="xqing_news_right">
										<div class="xqing_news_manage">
											<span class="list_lefts">最新贴</span>
										    <span class="list_rights">发帖人</span>
									    </div>
                                           <ul>
<%--                                            	  <c:forEach items="${items_newList}" var="item"> --%>
<%--                                               <li><div class="list"><span class="dot">·</span><a href="javascript:enterNotes(${item.id});" style="color: black" title="${item.title}">${fn:substring(item.title,0,15)}</a><i><a href="javascript:showMemberInfo('${item.ownerName}');"  title="${item.ownerName }">${item.ownerName }</a></i></div></li> --%>
<%-- 											  </c:forEach> --%>
										 </ul>									
									</div>
						   </div>
						   <!--new off-->
					 </div>
				</div>
			 <%@ include file="/WEB-INF/page/common/footer.jsp" %>
	  </div>
   <!--right off-->
</div>

<!-- 显示用户详情表单 -->
<form action="${basePath}/newdxjr/accountdetail/show.html"  id="showMemberInfoFrom" target="_blank" method="post">
	<input type="hidden" name="username" id="memberInfoUsername"/>
</form>
</body>
<script type="text/javascript">
$(function(){
	//可用资金
	var use_money_s = new BigDecimal('${accountVo.useMoney}');
	$("#use_money_s").html(use_money_s.setScale(2, MathContext.prototype.ROUND_DOWN)+'');
	//默认统计账号详情
	accountBase();
	// 是否显示数据名称说明
	showAccountInfo();
});
/**
 * 进入实名认证页面
 */
function realnameFun(){
	window.location.href = "${path}/account/approve/realname/display.html";
}
/**
 * 进入VIP认证页面
 */
function vipFunction(){
	window.location.href = "${path}/account/approve/vip/toVipIndex.html";
}
/**
 * 更换头像
 */
function headuploadFunction(){
	window.location.href = "${path}/newdxjr/approve/headupload.html";
}
/**
 * 进入手机认证页面
 */
function mobileFunction(){
	window.location.href = "${path}/account/approve/mobile/mobileforinsert.html";
}
/**
 * 进入邮箱认证页面
 */
function emailFunction(){
	window.location.href = "${path}/account/approve/email.html";
}
/**
 * 进入充值页面
 */
function topupFunction(){
	window.location.href = "${path}/account/topup/toTopupMain/topup.html";
}
/**
 * 进入提现页面
 */
function withdrawFunction(){
	window.location.href = "${path}/account/topup/toTopupMain/getcash.html";
}
/**
 * 进入关联人页面
 */
function toLinkman(){
	
}
/**
 * 进入上传资料页面
 */
function upFunction(){
	window.location.href = "${path}/newdxjr/approve/uploaddoc.html";
}

/**
 * 四个统计选项将li中的current样式去掉
 */
function setlicss(){
	$("#accountBase_li").children("a").removeAttr("style");
	$("#invest_li").children("a").removeAttr("style");
	$("#borrow_li").children("a").removeAttr("style");
	$("#shareholderRank_li").children("a").removeAttr("style");
}
/**
 * 切换统计数据
 */
function toggleAccount(obj,type){
	//四个统计选项将li中的current样式去掉
	 setlicss();
	 $("#"+type+"_li").children("a").attr("style","background:#ffaa29; color:#fff;");
	 if(type=='accountBase'){
		 accountBase();
	 }else if(type=='invest'){
		 investInfo();
	 }else if(type=='borrow'){
		 borrowInfo();
	 }else if(type=='shareholderRank'){
		 shareholderRank();
	 }
}
/**
 * 统计账户详情
 */
function accountBase(){
	$("#accountBase_li").children("a").attr("style","background:#ffaa29; color:#fff;");
	$.post("${basePath}/myaccount/report/accountBase.html", {
	}, function(data) {
		$("#user_main_tab").html(data); 
	});
}
/**
 * 统计投资详情
 */
function investInfo(){
	$.post("${basePath}/myaccount/report/investInfo.html", {
	}, function(data) {
		$("#user_main_tab").html(data); 
	});
}
/**
 * 统计借款详情
 */
function borrowInfo(){
	$.post("${basePath}/myaccount/report/borrowInfo.html", {
	}, function(data) {
		$("#user_main_tab").html(data); 
	});
}
/**
 * 股东加权统计
 */
function shareholderRank(){
	$.post("${basePath}/myaccount/report/shareholderRankInfo.html", {
	}, function(data) {
		$("#user_main_tab").html(data); 
	});
}
/**
 * 显示用户详情
 */
function showMemberInfo(username){
	$("#memberInfoUsername").val(username);
	$("#showMemberInfoFrom").submit();
}

/**
 * 是否显示数据名称说明
 */
 function showAccountInfo(){
	 var dataNameDesc =$("#account_info");
	 if(dataNameDesc[0].style.display=="none"){
		 dataNameDesc.show();
	 }else{
		 dataNameDesc.hide();
	 }
}
</script>
</html>
