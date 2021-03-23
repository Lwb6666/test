<%@page import="com.dxjr.utils.DateUtils"%>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/page/common/taglib.jsp"%>
<%
 	// 发布的版本号，每次发布版本时将此版本号加1,其主要原因是引用最新的css文件，避免缓存
    String version = "20160831"; 
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
<link href="${basePath}/css/newp/mayhd1609.css?version=<%=version%>" rel="stylesheet" type="text/css" />
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
	<div class="b-btn"><a href="${path}/member/toRegisterPage.html<c:if test="${not empty source}">?source=${source }</c:if>">立即注册</a></div>
</div>
<div class="box1">
 <div class="main"> 
    <div class="box1-list">
      <p class="f40" style="color:#035691;font-weight:700;">盈在起跑线，红包来助力</p>
    </div>
    <div class="box-text f18 yellow">
        <p class="f22">规则说明</p>
        <p>用户注册成功后即可获得1080元红包组合，每位新用户仅可领取一次。用户可进入“我的账户-账户奖励-我的红包”查看红包详情</p>
    </div>
   </div>
</div>
<div class="wrap">
     <div class="main">   
        <div class="box2">
        	<div class="register f24 red">
        	  <p class="f40" style="color:#ea5514;font-weight:700;">经验看涨，福利不断！</p>
        	  <p style=" margin-top:15px; margin-left:100px;">红包使用规则</p>
            </div>
              <table width="100%" border="0" class="box2-tl box2-table clearfix">
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
           
         <p class="f24 red" style=" margin-top:180px; margin-left:380px; font-weight:700">以上红包均不得用于【新手宝】！</p>

        </div>
	<div class="box3">
        	<div class="box3-tit f40">投资加速器，专享高收益</div>
            <div class="box3-text">
                <p style="width:300px; margin-top:60px;line-height:1.6;" class="f24 green">完美的实战经验，从投资【新手专享宝】开始！短期高收益，独享高福利！赞不赞？赚不赚？你说了算！</p>
                <p class="f22 red" style=" margin-top:40px">规则说明</p>
                <p class="f18 red" style=" margin-top:10px">1.一次投资年化为15%的新手宝资格</p>
                <p class="f18 red" style=" margin-top:5px"> 2.最高投资额度为10000元</p>
                <p class="f18 red" style=" margin-top:5px"> 3.期限一个月</p>
            </div>
      </div>
</div>
<div class="box4">
	<div class="main">
	    <div class="box4-text">
        <p class="f30" style="color:#035691;font-weight:700;" >幸运转盘转不停，实名注册更获额外惊喜</p>
        <p class="f18 green" style=" margin-top:30px;">中奖机会100%，Iphone手机，高额礼金，统统等你转回家！</p>
        <p class="f30" style="color:#ea5514; margin-top:20px; font-weight:700;">还有更多抽奖机会也别错过哦！</p>
    </div>

              <table width="100%" border="0" class="box4-tl">
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
                <td colspan="2">论坛连续签到30天即可获得抽奖机会1次</td>
              </tr>
            </table>
      <div class="footer-bg">
      <p style="text-align:center; font-size:40px;color:#fff; font-weight:700;"><a href="http://www.dxjr.com/dingqibao.html">开启财富路</a></p>
      <p style="text-align:center; font-size:16px;color:#6a3906; padding-top:35px; line-height:1.6;">免责申明：投资有风险，请谨慎操作。活动最终解释权归顶玺金融所有。<br>
有疑问请于工作日9：00-17：30咨询客服热线400-0156-676</p>
 
 </div>    
    </div>

 
    
</div>


 <div style="position:absolute;top:10px;right:100px;width:150px;height:30px;line-height:30px; background:#fff; text-align:center;"><a href="http://www.dxjr.com/" class="yellow">点击返回官网首页</a></div>



</body>

</html>
