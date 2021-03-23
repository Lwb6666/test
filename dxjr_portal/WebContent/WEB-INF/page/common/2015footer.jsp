<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<link href="${basePath}/css/float.css?version=<%=version%>" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${basePath}/js/main/jquery.event.drag-1.5.min.js"></script>
<script type="text/javascript" src="${basePath}/js/main/jquery.touchSlider.js"></script>
<script type="text/javascript" src="${basePath}/js/commonutils.js"></script>
 
<div class="clearfix"></div>
<div class="footerwidth" style="height: 280px;">
<div id="footer" style="height: auto;">

	<div class="footers" style=" ">
		<span>
			<a href="${path}/jianjie.html" rel="nofollow">关于我们</a>
			<a href="${path}/jianjie/lianxi.html" rel="nofollow">联系方式</a>
			<a href="${path}/jianjie/zhaopin.html" rel="nofollow">招贤纳士</a>
			<a href="${path }/bangzhu.html" rel="nofollow">帮助中心</a>
			<a href="${path }/zhinan.html" rel="nofollow">新手指南</a>
		</span>
	</div>
	<div class="footers" style=" ">
		<span>合作伙伴：</span>
		<a href="http://www.touzhijia.cn/" title="投之家" rel="nofollow" target="_blank"><img src="${basePath }/images/images/link_18.jpg" alt="理财小知识"></a>
		<a href="http://www.cmbchina.com/" rel="nofollow" title="招商银行" target="_blank"><img src="${basePath }/images/images/link_07.png" alt="抵押贷款"/></a>
		<a href="http://bank.pingan.com/index.shtml" rel="nofollow" title="平安银行" target="_blank"><img src="${basePath }/images/images/link_09.png" alt="贷款平台"/></a>
		<a href="http://www.chinabank.com.cn/" rel="nofollow" title="网银在线" target="_blank"><img src="${basePath }/images/images/link_13.png" alt="互联网金融"/></a>
		<a href="https://www.weicaifu.com/" rel="nofollow" title="微财富" target="_blank"><img src="${basePath }/images/images/link_15.png" alt="投资理财"/></a>
		<a href="http://www.xs-pawn.com/" rel="nofollow" title="新盛典当" target="_blank"><img src="${basePath }/images/images/link_17.png" alt="抵押贷款"/></a>
		<a href="http://men.sohu.com/s2014/jinrong/index.shtml" title="金融e时代" rel="nofollow" target="_blank"><img src="${basePath }/images/images/link_16.png" alt="贷款平台"/></a>
		<a href="http://www.yicai.com/" rel="nofollow" title="第一财经" target="_blank"><img src="${basePath }/images/images/link_03.png" alt="理财小知识"/></a>
	</div>
	<div class="footers" style="width:980px;">
		<div id="friendlinks" style=" width:90%;height:30px;">
			<span>友情链接：</span>
			<a href="http://www.dxjr.com/" target="_blank">顶玺金融</a>
			<a href="http://www.wangdaizhijia.com/" rel="nofollow"  target="_blank">网贷之家</a>
			<a href="http://www.p2peye.com/forum.php" rel="nofollow" target="_blank">网贷天眼</a>
			<a href="http://www.erongtu.com/" rel="nofollow" target="_blank">融途网</a>
			<a href="http://i.youku.com/u/UMTY1OTAwMTIyMA==" rel="nofollow" target="_blank">顶玺金融优酷频道</a>
		  	<c:if test="${logotype==1}">
		    <a href="http://www.wangdaidp.com/" rel="nofollow" target="_blank">网贷点评网</a>
			</c:if>
		    <a href="http://www.itjinrong.cn/" rel="nofollow" target="_blank">互联网金融招聘社区</a>
			<%-- <a href="http://www.touzhijia.com/" target="_blank">投之家</a> --%>
			
		    <a href="http://www.gold2u.com/" target="_blank">现货黄金投资</a>
		    <a href="http://www.fminers.com/" target="_blank">P2P网贷</a>
		</div>
		<div style="padding-top:5px;padding-left: 80px;height:30px;width:82%;">	
			
			<a href="http://www.wangdaishequ.cn/" target="_blank">P2P</a>	    		    
		    		    
		    <a href="http://www.wodai.com/bbs/" target="_blank">网贷论坛</a>
		    <a href="http://www.ztrong.com/" target="_blank">投融资平台</a>
		  <!--   <a href="http://www.68rong.com/" target="_blank">宜生宝</a>	 -->	    
		 <!--    <a href="http://www.moneydai.com/" target="_blank">p2p网贷</a> -->		    
		    <a href="http://www.wdtianxia.com/" target="_blank">网贷天下</a>
		    <a href="http://www.tzg.cn/" target="_blank">p2p网贷平台</a>
		    <a href="http://www.rong360.com/licai-net/" target="_blank">互联网理财</a>
		    <a href="http://www.qhfax.com/" target="_blank">互联网理财</a>
		    <a href="http://www.anxin.com/wiki/" target="_blank">p2p网贷平台</a>
		    <a href="http://wangdaiyt.com/" target="_blank">亚投财富投资理财贷款</a>
		</div>
		<div style="padding-top:5px;padding-left: 80px;height:30px;width:82%;">
		    <a href="http://www.d3f.com/" target="_blank">众筹平台</a>
		    <a href="http://www.18link.com/" target="_blank">个人小额投资理财</a>
		    <a href="http://www.bjsudai.com/" target="_blank">房产抵押贷款</a>
		    <a href="https://www.boying.com/" target="_blank"> 博赢财富</a>
		    <a href="http://www.trust-one.com/" target="_blank">信托公司</a>
		    <a href="http://www.gangu.com/" target="_blank">南京文交所</a>
		</div>
	</div>
	<div class="footers" style="padding-top: 5px;">
	<dl class="pg-footer-focus">
   	<span>客户服务：</span>
       <dd><a class="pg-footer-icon weibo" target="_blank" title="新浪微博" href="http://weibo.com/u/3805446108" rel="nofollow"></a></dd>
       <dd><a class="pg-footer-icon weixin"   title="官方微信" href="javascript:showwenxin()" rel="nofollow"></a></dd>
       <dd><a class="pg-footer-icon mark" target="_blank" title="在线客服" href="http://www.dxjr.com/onLineServices/webQQ.html"   rel="nofollow"></a></dd>
   	</dl>
	</div>
	<div style=" position:absolute; z-index:999; width:150px; text-align:right; right:5%; top:120px; font-size:14px; line-height:26px; color:#ccc">
		服务热线<br />
		400-000-0000<br />
		9:00-18:30<br />
	</div>
</div>
</div>
	<div  style="display: none" id="imgShowId">
			 	<table   >
					<tr><td><img alt="顶玺金融" id="imgId" src="${basePath }/images/weixin.jpg"></td><td title="顶玺金融"><img alt="顶玺金融" id="imgId" src="${basePath }/images/wxservicenum.jpg"></td></tr>
					<tr  align="center"><td><strong>订阅号：gcjr-sh</strong></td><td><strong>服务号：guochengjinrong</strong></td></tr>
				</table>
	</div>
<div class="footer_info" style="background-color: white;">
© Copyright 2016 上海顶玺金融信息服务有限公司 版权所有 | 
上海市普陀区华池路58弄3楼&nbsp;  
电话：021-00000000 | 
沪ICP备00000000号
<br/>
<div class="beian-box">
<div class="beian-l">
<a href="https://www.sgs.gov.cn/lz/licenseLink.do?method=licenceView&entyId=1atr5hendjiu232trv8vb6qred7d9ymsgc9oes185d55bs1gd2" rel="nofollow">
	<img src="${basePath}/images/icon_gs.png" alt="上海工商" border="0"/>
</a>
</div>
<div class="beian-r">
<a href="http://www.beian.gov.cn/portal/registerSystemInfo?recordcode=31010902103103" rel="nofollow">
	<img src="${basePath}/images/picp_bg.png" alt="沪公网备" border="0"/>
</a>
</div>
</div>
</div>
<!--右边浮动-->
<div id="side_follow" style="top: 50%; margin-top: -129px; z-index: 10000; width: 36px;">
<div class="sf_phone" style="width: 36px; height: 38px; overflow: hidden;">
<p title="请输入您的联系方式，我们会在稍后联系您为您服务.">
	<span><input type="text" id="feedbackMobileNum" style=" border:1px solid #dbdbdb;margin-top:8px; width:120px;" value="" placeholder="400-000-0000"/></span>&nbsp;&nbsp;
   <span><input type="button" class="but1" value="提交" onclick="feedback();"/></span>
</p>
</div>
<div class="sf_email" style="overflow: hidden; width: 36px; height: 38px;">
<p>
<a href="mailto:marketing@dxjr.com" rel="nofollow">marketing@dxjr.com</a>
</p>
</div>
<div class="sf_qqgroup" style="width: 36px; height: 38px; overflow: hidden;">
<p>
<a href="http://www.dxjr.com/onLineServices/webQQ.html" target="_blank" rel="nofollow">
客服QQ（在线咨询）</a>
</p>
</div>
<div class="sf_weibo" style="overflow: hidden; width: 36px; height: 38px;">
<p>
<a target="_blank" href="http://weibo.com/u/3805446108" rel="nofollow">新浪微博@顶玺金融</a>
</p>
</div>
<div class="sf_gotop" style="height: 0px; top: 0px;"></div>
</div>

<script type="text/javascript">
 
	
	$(document).ready(function(){
		if('${logotype}' != '1'){
			$("a[name=needfollow]").each(function (){ 
				  $(this).attr("rel","nofollow");
			}) ;
		}
	});
	 
	function showwenxin(){
	   	$.layer({
			type: 1,   //0-4的选择,
			title:  ["官方微信" , true],
			closeBtn: [0 , true],
			area: [550,345],
			offset : [ '100px', '' ],
			page: {
				dom : '#imgShowId'
			} 
		});
		
	}

	function changeFeed(){
		var mobileNum = $("#feedbackMobileNum").val();
		if(mobileNum == '400-000-0000'){
			$("#feedbackMobileNum").val('');
			$("#feedbackMobileNum").attr("style","border:1px solid #dbdbdb;margin-top:8px; width:120px;color:#000;");
			layer.tips('请输入您的手机号码，我们客服会及时与您联系，谢谢！', "#feedbackMobileNum", {
			    time: 3,
			    style: ['background-color:#ff0000; color:#fff;top:-20px;', '#ff0000']
			});
			return false;
		}
	}
	  
	function feedback(){
		var mobileNum = $("#feedbackMobileNum").val();
		if(mobileNum == null || mobileNum == ''){
			layer.msg("请输入您的手机号码，我们客服会及时与您联系，谢谢！", 2, 5);
			return false;
		}
		if(mobileNum == '400-000-0000'){
			layer.msg("请输入您的手机号码，我们客服会及时与您联系，谢谢！", 2, 5);
			// $("#feedbackMobileNum").val('');
			$("#feedbackMobileNum").attr("style","border:1px solid #dbdbdb;margin-top:8px; width:120px;color:#000;");
			return false;
		}
		var mobileReg = /^1[3|5|7|8|][0-9]{9}$/;
		if(!mobileReg.test(mobileNum)){
			layer.msg("手机号码输入不正确！", 2, 5);
			return false;
		}
		$.ajax({
			url : '${basePath}/feedback/feedback.html',
			data :{
				mobileNum:mobileNum
			} ,
			type : 'post',
			dataType : 'text',
			success : function(data){
				if(data == 'success'){
					layer.msg('提交成功！', 1, 1,function(){
						location.href="${path}/home/index.html";
			    	});
					//layer.alert('提交成功！' , 1, "温馨提示");
				}else{
					layer.msg(data, 1, 5);
				}
			},
			error : function(data) {
				layer.msg("网络连接异常，请刷新页面或稍后重试！", 1, 5);
		    }
		});
	}

</script>



 

