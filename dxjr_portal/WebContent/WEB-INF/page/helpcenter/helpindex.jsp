<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%> 
<%@ include file="/WEB-INF/page/common/taglib.jsp"%> 
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ include file="/WEB-INF/page/common/public1.jsp"%>
<title>帮助中心_顶玺金融</title>
<meta name="keywords" content="理财产品,投资理财,网贷平台,互联网金融,金融理财,消费金融,房产抵押,投资理财" />
<meta name="description" content="顶玺金融（www.dxjr.com），一个专注于消费金融与房产抵押贷款的中国社科院网贷评级A级的理财平台。致力于为投资理财用户和贷款用户两端搭建安全、规范、透明的互联网金融平台。顶玺为您的资金保驾护航！ ">
<link href="${basePath}/css/help.css" rel="stylesheet" type="text/css" />
</head>

<body>
<%@ include file="/WEB-INF/page/common/header.jsp"%>
	<div id="Bmain">
  		  <div class="title">
       <span class="home"><a href="${path}/">顶玺金融</a></span><span><a href="${path }/bangzhu.html">帮助中心</a></span>
  </div>
 <div class="helpbox">
            <div class="helpnav">
                 <ul>
                    <li>
                        <p class="tit4"><a href="${path }/bangzhu/daohang.html">新手导航</a></p>
                        <ul>
                            <li id="menu1-1"><a href="${path }/bangzhu/1.html">了解顶玺金融</a></li>
							<li id="menu1-2"><a href="${path }/bangzhu/2.html">注册登录</a></li>
							<li id="menu1-3"><a href="${path }/bangzhu/3.html">充值提现</a></li>
							 <%--  <li id="menu1-4"><a href="${path }/help/newcomer/safe.html#1,4,6">账户安全</a></li> --%>
							<li id="menu1-5"><a href="${path }/bangzhu/4.html">如何投标</a></li>
							<li id="menu1-6"><a href="${path }/bangzhu/5.html">投资资费</a></li>
                       </ul>
                    </li>
                    <li>
                        <p class="tit1"><a href="${path }/bangzhu/touzi.html">如何投资</a></p>
                        <ul>
                            <li id="menu2-1"><a href="${path }/bangzhu/6.html">新手必读</a></li>
							<li id="menu2-2"><a href="${path }/bangzhu/7.html">产品介绍</a></li>
							<li id="menu2-3"><a href="${path }/bangzhu/8.html">收益与费用</a></li>
							<li id="menu2-4"><a href="${path }/bangzhu/9.html">投标直通车</a></li>
							<li id="menu2-5"><a href="${path }/bangzhu/10.html">官方标投资</a></li>
							<li id="menu2-6"><a href="${path }/bangzhu/11.html">债权转让</a></li>
							<li id="menu2-7"><a href="${path }/bangzhu/22.html">直通车转让</a></li>
							<li id="menu2-8"><a href="${path }/bangzhu/23.html">活期宝</a></li>
						    <li id="menu2-9"><a href="${path }/bangzhu/24.html">定期宝</a></li> 
                       </ul>
                    </li>
                    <li>
                        <p class="tit3"><a href="${path }/bangzhu/jiekuan.html">如何借款</a></p>
                        <ul>
                          <%--  	<li id="menu3-1"><a href="${path }/bangzhu/12.html">产品介绍</a></li> --%>
							<%-- <li id="menu3-2"><a href="${path }/help/borrow/fee.html#3,2,8">借款费用</a></li> --%>
								<li id="menu3-1"><a href="${path }/bangzhu/12.html">如何申请</a></li>
								<li id="menu3-4"><a href="${path }/bangzhu/14.html">申请确认</a></li>
								<li id="menu3-5"><a href="${path }/bangzhu/15.html">来司面审</a></li>
								<li id="menu3-6"><a href="${path }/bangzhu/16.html">外访调查</a></li>
								<li id="menu3-7"><a href="${path }/bangzhu/17.html">发放标的</a></li>
								<li id="menu3-8"><a href="${path }/bangzhu/18.html">放款</a></li>
                       </ul>
                    </li>
                    <li>
                        <p class="tit2"><a href="${path }/bangzhu/zhanghu.html">账户管理</a></p>
                        <ul>
                         <%-- <li id="menu4-1"><a href="${path }/help/account/reglogin.html#4,1,5">登录注册</a></li> --%> 
							<li id="menu4-2"><a href="${path }/bangzhu/19.html">账户密码</a></li>
						 <%--  <li id="menu4-3"><a href="${path }/help/account/recharge.html#4,3,5">充值</a></li> --%> 
							<li id="menu4-4"><a href="${path }/bangzhu/20.html">银行卡管理</a></li>
							<li id="menu4-5"><a href="${path }/bangzhu/21.html">安全认证</a></li>
							<li id="menu4-3"><a href="${path }/bangzhu/27.html">关于资金存管</a></li>
							<li id="menu4-6"><a href="${path }/bangzhu/26.html">元宝和会员</a></li>
                       </ul>
                    </li>
                 </ul>
            </div>
        </div> 
        <div class="helpbox">
                 <div class="top f20 bold">名词解释</div>
                 <div class="helpwords">
                     <span>A-G</span>
                     <ul class="borderbottom">
                         <li><a href="${path }/bangzhu/jieshi.html#listId=2">等额本息</a></li>   
                     </ul>
                     <span>H-N</span>
                     <ul class="borderbottom">
                         <li><a href="${path }/bangzhu/jieshi.html#listId=3">借款用户（借款人） </a></li>
                         <li><a href="${path }/bangzhu/jieshi.html#listId=5">借款服务费 </a></li>
                         <li><a href="${path }/bangzhu/jieshi.html#listId=6">借款管理费 </a></li>
                         <li><a href="${path }/bangzhu/jieshi.html#listId=7">借款利率 </a></li>
                         <li><a href="${path }/bangzhu/jieshi.html#listId=8">出借用户</a></li>
                         <li><a href="${path }/bangzhu/jieshi.html#listId=9">预期利率</a></li>
                         <li><a href="${path }/bangzhu/jieshi.html#listId=10">预期收益率 </a></li>
                     </ul>
                     <span>O-T</span>
                     <ul class="borderbottom">
                         <li><a href="${path }/bangzhu/jieshi.html#listId=11">锁定期</a></li>
                     </ul>
                     <span>U-Z</span>
                     <ul>
                         <li><a href="${path }/bangzhu/jieshi.html#listId=12">信用认证  </a></li>
                         <li><a href="${path }/bangzhu/jieshi.html#listId=13">信用报告 </a></li>
                         <li><a href="${path }/bangzhu/jieshi.html#listId=14">信用审核 </a></li>
                         <li><a href="${path }/bangzhu/jieshi.html#listId=15">信用等级（分数） </a></li>
                         <li><a href="${path }/bangzhu/jieshi.html#listId=16">信用额度 </a></li>
                         <li><a href="${path }/bangzhu/jieshi.html#listId=17">逾期 </a></li>
                         <li><a href="${path }/bangzhu/jieshi.html#listId=18">投标直通车</a></li>
                          <li><a href="${path }/bangzhu/jieshi.html#listId=1">债权回购</a></li>
                     </ul>
                 </div>
        </div>
	</div>
	
	<div>
		<%@ include file="/WEB-INF/page/common/footer.jsp"%>
	</div>
<form method="post"></form>
</body>
<script type="text/javascript">
function forPage(actionName,newparentId,newId){
	document.forms[0].action="${path }/bangzhu/show.html?pageName="+ actionName+"&newparentId="+newparentId+"&newId="+newId;
	document.forms[0].submit();
	
}
</script>
</html>