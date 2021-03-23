<%@page import="com.dxjr.utils.DateUtils"%>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/page/common/taglib.jsp"%>
<%
 	// 发布的版本号，每次发布版本时将此版本号加1,其主要原因是引用最新的css文件，避免缓存
    String version = "20160523"; 
	response.setHeader("Cache-Control","no-store"); 
	response.setHeader("Pragrma","no-cache"); 
	response.setDateHeader("Expires",0); 
%>
<!doctype html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge"/>
<title>新人特惠_互联网投资理财新人专享_顶玺金融</title>
<meta name="keywords" content="互联网金融理财,投资理财,金融理财,理财产品,顶玺金融,互联网理财" />
<meta name="description" content="顶玺金融（www.dxjr.com），一个专注于消费金融与房产抵押贷款的中国社科院网贷评级A级的理财平台。致力于为投资理财用户和贷款用户两端搭建安全、规范、透明的互联网金融平台。投资理财用户可通过顶玺金融官网官方网站进行散标投资、定期宝、活期宝、购买债权转让等方式进行投资获得稳定收益。">
<meta charset="utf-8">
<link href="${basePath}/css/newp/mayhd.css?version=<%=version%>" rel="stylesheet" type="text/css" />
<script type='text/javascript'>
window._CWiQ = window._CWiQ || [];
window.BX_CLIENT_ID = 37362; // 帐号ID
(function() {
var c = document.createElement('script')
,p = 'https:'==document.location.protocol;
c.type = 'text/javascript';
c.async = true;
c.src = (p?'https://':'http://')+'whisky.ana.biddingx.com/boot/0';
var h = document.getElementsByTagName('script')[0];
h.parentNode.insertBefore(c, h);
})();
</script>

<!-- ZZCode -->
<script type="text/javascript">
var _zzsiteid="hiGAA0ZMbRL";
var _zzid = "hiGAA0ZMbRK";
(function() {
  var zz = document.createElement('script');
  zz.type = 'text/javascript';
  zz.async = true;
  zz.src = 'https:' == document.location.protocol ? 'https://ssl.tj.71360.com/api/trace.js' : 'http://tj.71360.com/trace/api/trace.js';
  var s = document.getElementsByTagName('script')[0];
  s.parentNode.insertBefore(zz, s);
})();
</script>
<!-- end ZZCode -->
</head>

<body>
<div class="banner">
	<div class="b-btn">
		<c:if test="${source == null || source == ''}">
			<a href="${path}/member/toRegisterPage.html">立即注册</a>
		</c:if>
		<c:if test="${source != null && source != ''}">
			<a href="${path}/member/toRegisterPage.html?source=${source}">立即注册</a>
		</c:if>
	<p>开启财富路</p></div>
</div>
<div class="wrap">
	<div class="main">
    	<div class="box1">
        	<div class="box1-list f22 green">
            <p>完美的实战经验，从投资</p>
            <p>【新手专享宝】开始！</p>
            <p>短期高收益，独享高福利！</p>
            <p>赞不赞？赚不赚？你说了算！</p>
            </div>
            <div class="box-text f18 yellow">
            	<p>1.一次投资预期收益为15%的新手宝资格</p>
                <p>2.最高投资额度为10000元</p>
                <p>3.期限一个月</p>
            </div>
        </div>
        
        <div class="box2">
        	<div class="register f18 green">
            	<p>新手注册成功，均可获得总价值为100元的红包组合大礼；</p>
                <p>红包组合分为20元红包、30元红包和50元红包</p>
            </div>
            <div class="box2-hb">
            	<h3>红包使用规则</h3>
                <table width="100%" border="0" class="box2-table">
                  <tr>
                    <td>红包面额</td>
                    <td>红包使用条件</td>
                    <td>投资标的期限</td>
                  </tr>
                  <tr>
                    <td>20元</td>
                    <td>投资2000（含）元以上</td>
                    <td>全场通用</td>
                  </tr>
                  <tr>
                    <td>30元</td>
                    <td>投资3000（含）元以上</td>
                    <td>3月（含）以上定期宝</td>
                  </tr>
                  <tr>
                    <td>50元</td>
                    <td>投资5000（含）元以上</td>
                    <td>6月（含）以上定期宝</td>
                  </tr>
                </table>
                <p>以上红包均不得用于【新手宝】！</p>

            </div>
        </div>
	<div class="box3">
        	<div class="box3-text f18 green">
            	新手注册成功后即可获得一次抽奖机会！中奖机会100%<br/>
Iphone手机，高额礼金，统统等你转回家！
            </div>
            <table width="100%" border="0" class="box3-tl box2-table">
              <tr>
                <td>首投定期宝3月宝</td>
                <td>即可获得1次抽奖机会</td>
              </tr>
              <tr>
                <td>首投定期宝6月宝</td>
                <td>即可获得2次抽奖机会</td>
              </tr>
              <tr>
                <td>首投定期宝12月宝</td>
                <td>即可获得3次抽奖机会</td>
              </tr>
              <tr>
                <td colspan="2">论坛连续签到10天即可获得抽奖机会1次</td>
              </tr>
            </table>

            
       	<span></span>
      </div>
      <p style=" margin-top:100px; text-align:center;"><a href="${path }/member/toRegisterPage.html"><img src="${path}/images/newp/open.png" width="283" height="94" alt=""/></a></p>
    </div>
    <div style="position:absolute;top:10px;right:100px;width:150px;height:30px;line-height:30px; background:#fff; text-align:center;"><a href="${path }/">点击返回官网首页</a></div>
 <div class="footer-bg">
 	<div class = "footer-bg-pic"></div>
 </div>
</div>
</body>

</html>
