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
<link href="${basePath}/css/newp/mayhdaoyun.css?version=<%=version%>" rel="stylesheet" type="text/css" />
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
		</c:if><p>抢1080元</p></div>
</div>
<div class="wrap">
	<div class="main">
    	<div class="box1">
        	<div class="box1-list">
            <p class="f30" style="color:#9a6e26;font-weight:700;">盈在起跑线   先抢红包再投资</p>
            <div style="color:red; padding-top:40px;" class="f24">
            <p>中国每得一枚金牌，立即送出1000个1080元大红包给新用户先抢先得</p>
            <p>仅限8月1日至8月31日新注册用户参与</p>
            </div>
            </div>
            <div class="box-text f18 yellow">
                
            	<p>8月1日预先上线5000个1080元红包组，奥运会开幕后中国队每夺一枚金牌，再追加1000个红包组。
                <p>每位新用户仅可领取一次1080元红包组，用户可进入“我的账户-账户奖励-我的红包”查看红包详情。</p>
            </div>
        </div>
        
        <div class="box2">
        	<div class="register f24 red">
             <p class="f30" style="color:#9a6e26;font-weight:700;">收益加速  年化15%只为新手助力</p>
             <p style=" margin-top:40px;">完美的实战经验，从投资【新手专享宝】开始！</p>
             <p>短期高收益，独享高福利！</p>
             <p>赞不赞？赚不赚？你说了算！</p>
           	  <div style=" margin-top:50px; margin-left:50px;width:410px; font-size:18px;">
                	<p class="f24">规则说明</p>	
                	<p>1.一次投资年化为15%的新手宝资格</p>
                    <p>2.最高投资额度为10000元</p>
                    <p>3.期限一个月</p>	
                </div>
              <p style=" margin:75px 0 0 105px; font-size:42px;"><a href="${basePath}/dingqibao.html">抢购新手宝</a></p>
            </div>

        </div>
	<div class="box3">
        	<div class="box3-text">
            <p class="f30" style="color:#9a6e26;font-weight:700;">幸运转盘转不停，实名注册更获额外惊喜</p>
            <p style=" margin-top:25px;" class="f22 red">新手注册成功后即可获得一次抽奖机会！中奖机会100%</p>
            <p style=" margin-top:20px; font-size:36px;color:#c30d23;" >还有更多抽奖机会也别错过哦！</p>
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
                <td colspan="2">论坛连续签到30天即可获得抽奖机会1次</td>
              </tr>
            </table>
       	<span></span>
      </div>
</div>
    <div style="position:absolute;top:10px;right:100px;width:150px;height:30px;line-height:30px; background:#fff; text-align:center;"><a href="${path }/" class="yellow">点击返回官网首页</a></div>
 <div class="footer-bg">
      <p style="text-align:center; font-size:40px;color:#fff; padding-top:60px; padding-left:20px;"><a href="${path }/member/toRegisterPage.html">开启财富路</a></p>
      <p style="text-align:center; font-size:20px;color:#6a3906; padding-top:40px; padding-left:20px; line-height:1.6;">免责申明：投资有风险，请谨慎操作。活动最终解释权归顶玺金融所有。<br>
有疑问请于工作日9：00-17：30咨询客服热线400-0156-676</p>
 
 </div>  
    
</div>
</body>
</html>
