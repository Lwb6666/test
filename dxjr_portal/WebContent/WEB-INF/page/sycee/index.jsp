<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" import="org.springframework.web.util.WebUtils,com.dxjr.portal.member.controller.MemberController,java.net.URLDecoder"%>
<%@ page import = "com.dxjr.portal.statics.BusinessConstants" %>
<%@ include file="/WEB-INF/page/common/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ include file="/WEB-INF/page/common/public1.jsp"%>
<link href="${basePath}/css/help1.css?version=<%=version%>" rel="stylesheet" type="text/css" />
<title>[顶玺金融]元宝商城兑换大礼都在这里-顶玺金融</title>
<meta name="keywords" content="理财产品,消费金融,互联网理财,金融理财,房产抵押,理财产品,网络理财" />
<meta name="description" content="顶玺金融（www.dxjr.com），一个专注于消费金融与房产抵押贷款的中国社科院网贷评级A级的理财平台。致力于为投资理财用户和贷款用户两端搭建安全、规范、透明的互联网金融平台。顶玺为您的资金保驾护航！ ">
<style type="text/css">
/**********************************元宝商城——限时折扣*****************************************/
.box-integral{position:relative; padding:5px 0; margin-top:3px;    }
.integral-inpt{width:50px;width:53px\0; position:absolute;top:12px; left: 12px; line-height:15px; text-align:center; padding:3px; color:#fff; border-radius:2px;  font-size:14px; background:red  ;}
.integral-yj{font-size:12px; padding-left:5px; position:absolute\0; left:0px\0; bottom:8px\0; *position:absolute ; *left:0px ; *bottom:8px ;  text-decoration:line-through;}
</style>
<script type="text/javascript" src="${basePath}/js/formValid.js"></script>
<%
Cookie userIdCookie = WebUtils.getCookie(request, MemberController.COOKIE_LOGIN_USERID);
String userId = "";
if (userIdCookie != null) {
	userId = URLDecoder.decode(userIdCookie.getValue(),"UTF-8");
}
%>
</head>
<body style="background-color: white;">
<%@ include file="/WEB-INF/page/common/header.jsp"%>

<!--积分商城—主要内容-->
<div class="integral-windows">
	<div class="banner-landing">
		<div class="banner-l">
			<img src="${path}/images/zk-banner.jpg" alt="个人投资理财" />
		</div>
		
		<%-- 未登录 --%>
		<c:if test="${empty userSycee }">
   		<div class="landing-r" id="unloginDiv"> 
   			<a href="#" onclick="$('#loginFm').show();$('#unloginDiv').hide();" class="landing-top">立即登录</a> 
   			<a href="${path }/member/toRegisterPage.html" class="res-bt ressc"  target="_blank" >免费注册</a> 
   		</div>
   		
   		<%-- 登录表单 --%>
   		<form id="loginFm" name="loginFm" method="post" style="display: none;">
		<div class="landing-r landing-two" style="" >
			<div style="width: 224px; margin: 0 auto;">
				<input class="landing-box" type="text" name="username"  id="username" placeholder="手机号/用户名/邮箱" value="<%=userId %>" style="color: black;" maxlength="30"/>
			</div>
			<div style="width: 224px; margin: 0 auto; margin-top: 15px;">
				<input class="landing-box landing-password" type="password" name="passwd" id="passwd" placeholder="登录密码" style="color: black;" maxlength="30"/>
			</div>
			 <div style="width:224px; margin:0 auto; margin-top:15px;display:none;"  id="codeView"  >
		      	<i class="code fr"><a href="javascript:loadimage();"><img  id="randImage" width="93px" height="42" src="${basePath}/random.jsp"/></a></i>
		        <input class="landing-box landing-code" width="131px" name="checkCode" maxlength="4" value="验证码" onfocus="if(value=='验证码'){value=''}" onblur="if(value==''){value='验证码'}"/>
            </div>
			<div class="wj-password"   >
				<div class="ckbod-l">
					<label> <input type="checkbox" id="saveidCB" name="saveidCB" <%=((userIdCookie != null) ? "checked='true'": "")%>/> 记住我 </label>
				</div>
				<div class="wj-psword-r">
					<a href="${path }/AccountSafetyCentre/safetyCentre/findPhone.html">忘记密码</a>
				</div>
			</div>
			<div class="tishino" id="loginTip"  style="margin-top:0"></div>
			<input type="hidden" name="revision" id="revision" value="${revision}" />
			<a href="javascript:login();" class="landing-top" style="margin-top: 15px;">立即登录</a>
			<div class="tishino reslj"> 
				没有账号？<a href="${path }/member/toRegisterPage.html" target="_blank">立即注册</a>
			</div>
		</div>
		<input type="hidden" name="CSRFToken" id="CSRFToken" value="${csrf}" />
		</form>
		</c:if>
		
		<%-- 已登录 --%>
		<c:if test="${not empty userSycee }">
		<div class="landing-r landing-res">
			<div class="user-top" >
				<div class="user-l" >
					<a><img src="${path }/${userHeadImg }" /></a>
				</div>
				<div class="user-r" >
					<p class="user-r-top">我的元宝</p>
					<a href="${path }/account/sycee.html">
						<p class="user-yuanbao">
							<fmt:formatNumber value="${userSycee }" pattern="###,###" />
						</p>
					</a>
					<p style=" margin-left:75px;color:#000">
						<a href="javascript:void(0);"  
						onclick="<c:if test="${not empty addr }">$('#showAddr').show();</c:if><c:if test="${empty addr }">$('#inputAddr').show();</c:if>">
						填写联系方式
						</a>
					</p>
				</div>
			</div>
			<div class="touzi">
				<a href="${path }/dingqibao.html" target="_blank">去投资</a>
			</div>
			<div class="touzi qiandao">
				<c:if test="${signItem == 0}"><a onclick="layer.msg('签到帖还未发出',1,5);" style="cursor: pointer;">去签到</a></c:if>
				<c:if test="${signItem != 0}"><a href="${bbsPath }/posts/${signItem }/1" target="_blank">去签到</a></c:if>
			</div>
			<div class="touzi touzi yzsm">
				<a href="${path }/bangzhu/26.html" target="_blank">元宝详细说明</a>
			</div>
		</div>
		</c:if>
	</div>
	<div class="content-integral">
		<div class="integral-l">
			<img src="${path}/images/integral-l.png" alt="个人理财产品" />
		</div>
		<div class="integral-r">
			<%-- 线上商品 --%>
			<c:forEach items="${onlineGoods }" var="g">
			
			<c:if test="${discountFlag == 1 && g.discount<10}">
			<div class="dh-box"  > 
            <a class="jifen-box"  > <img src="${g.imgurl }"/>
             <div class="integral-inpt" style="width: 65px;" >限时<fmt:formatNumber value="${g.discount }" pattern="##.#" />折</div>  
              <div class="box-md1 box-integral"     >
                <div  class="box-mdl"   >
                    <span style="display:inline; padding-left:5px; float:left;">兑换价格:</span> 
                    <span style="float:left; padding-left:0px; display:inline;">
                         <strong style="color:#d64060; padding:0px; ">${g.sycee }</strong>元宝
                    </span> 
                    <div class="integral-yj" style="">原价:${g.oldSycee }元宝</div>
                 </div>
                 <input style="float:right; width:76px;" type="button" value="立即兑换" onclick="toChange(${g.sycee },${g.id })"/>
              </div>
              </a> 
          	</div>
			</c:if>
			<c:if test="${discountFlag != 1 or g.discount==10}">
			<div class="dh-box">
				<a class="jifen-box" style="position: relative;"  >
					<img src="${g.imgurl }" width="240" height="151" alt="${g.name }" title="${g.name }"/>
					<div class="box-md1">
						<div class="box-mdl">
							<span>兑换价格：</span>
							<span>
								<strong style="color: #d64060;">${g.oldSycee }</strong>元宝
							</span>
						</div>
						<input type="button" value="立即兑换" onclick="toChange(${g.sycee },${g.id })"/>
					</div>
				</a>
			</div>
			</c:if>
			</c:forEach>
			<div class="dh-box">
					<img src="${path}/images/more-cp.png" />
			</div>
		</div>
	</div>
	<div class="content-integral" style="">
		<div class="integral-l">
			<img src="${path}/images/integral-2.png" alt="个人理财产品" />
		</div>
		<ul class="integral-r">
			<%-- 线下商品 --%>
			<c:forEach items="${offlineGoods }" var="g">
			<c:if test="${discountFlag == 1 && g.discount<10}">
			<li class="dh-box"  > 
	           	<a class="jifen-box"  > <img src="${g.imgurl }"/>
	             	<div class="integral-inpt" style="width: 65px;" >限时<fmt:formatNumber value="${g.discount }" pattern="##.#" />折</div>  
	              	<div class="box-md1 box-integral"     >
	                <div  class="box-mdl"   >
	                    <span style="display:inline; padding-left:5px; float:left;">兑换价格:</span> 
	                    <span style="float:left; padding-left:0px; display:inline;">
	                     	<strong style="color:#d64060; padding:0px; ">${g.sycee }</strong>元宝
	                    </span> 
	                    <div class="integral-yj" style="">原价:${g.oldSycee }元宝</div>
	                 </div>
	                 <input style="float:right; width:76px;" type="button" value="立即兑换" onclick="toChange(${g.sycee },${g.id })"/>
	              	</div>
	            </a> 
          	</li>
			</c:if>
			<c:if test="${discountFlag != 1 or g.discount==10}">
			<li class="dh-box">
				<a class="jifen-box" style="position: relative;">
					<img src="${g.imgurl }" width="240" height="151" alt="${g.name }" title="${g.name }"/>
					<div class="box-md1">
						<div class="box-mdl">
							<span>兑换价格：</span>
							<span>
								<strong style="color: #d64060;">${g.oldSycee }</strong>元宝
							</span>
						</div>
						<input type="button" value="立即兑换" onclick="toChange(${g.sycee },${g.id })"/>
					</div>
				</a>
			</li>
			</c:if>
			</c:forEach>
			<li class="dh-box">
				<img src="${path}/images/more-cp.png" alt="敬请期待"/>
			</li>
		</ul>
	</div>
</div>
</div>
<!--积分商城—主要内容-->

	<div class="t-windows"  style="display: none" id="inputAddr">
	   <form id="AddrFm" name="AddrFm" method="post" >   
 	   <div class="openwin" >
			<p class="openwin-top"><span class="title">填写您的联系方式</span></p>
            <div class="openwin-main">
              <p style="color:#999">
              	请填写您真实有效的联系信息，以便我们为您寄送奖品<br />
               	获得话费和电影券的用户可不填收件地址
              </p>
              <p style="margin-top:10px;"><span class="leftw">收件人：</span><input type="text" name="name" id="name" value="${addr.name }" dataType="Require"  maxlength="10" msg="收件人不能为空"/></p>
            <p><span class="leftw">手机号码：</span><input type="text"  name="phone" id="phone"  value="${addr.phone }" dataType="Integer|Require" min="11" max="11" maxlength="11" msg="手机号不能为空且必须为数字"/></p>
            <p><span class="leftw">收件地址：</span><input type="text"  name="address" id="address"  value="${addr.address }"  min="0" max="50" maxlength="50" msg="输入地址过长"/></p>
            <p><span class="leftw">邮编：</span><input type="text" name="zipCode" id="zipCode"  value="${addr.zipCode }"  maxlength="6" msg="请输入正确的邮编" /></p>
            <!--  <p class="warning">地址输入有误！</p> -->
            <p class="textcenter"  style="margin-top:20px;"><a  class="openwin-btn2" onclick="$('#inputAddr').hide(); window.location='${path}/sycee.html';">取消</a> 
            <a href="javascript:void(0);" onclick="addSub();"  class="openwin-btn1">提交</a></p>
            </div>
            <a class="guanbi"  onclick="$('#inputAddr').hide(); window.location='${path}/sycee.html';"><img src="${path }/images/guan.png"/></a>
 		</div>
 		</form>
    </div>
    
	<div class="t-windows"  style="display: none" id="updateAddr">
	   <form id="updateAddrFm" name="AddrFm" method="post" >   
 	   <div class="openwin" >
			<p class="openwin-top"><span class="title">填写您的联系方式</span></p>
            <div class="openwin-main">
              <p style="color:#999">
              	请填写您真实有效的联系信息，以便我们为您寄送奖品<br />
               	获得话费和电影券的用户可不填收件地址
              </p>
              <p style="margin-top:10px;"><span class="leftw">收件人：</span><input type="text" name="name" id="updateName" value="" dataType="Require"  maxlength="10" msg="收件人不能为空"/></p>
            <p><span class="leftw">手机号码：</span><input type="text"  name="phone" id="updatePhone"  value="" dataType="Integer|Require" min="11" max="11" maxlength="11" msg="手机号不能为空且必须为数字"/></p>
            <p><span class="leftw">收件地址：</span><input type="text"  name="address" id="updateAddress"  value=""  min="0" max="50" maxlength="50" msg="输入地址过长"/></p>
            <p><span class="leftw">邮编：</span><input type="text" name="zipCode" id="updateZipCode"  value=""  maxlength="6" msg="请输入正确的邮编" /></p>
            <!--  <p class="warning">地址输入有误！</p> -->
            <p class="textcenter"  style="margin-top:20px;"><a  class="openwin-btn2" onclick="$('#updateAddr').hide();">取消</a> 
            <a href="javascript:void(0);" onclick="updateAddr();"  class="openwin-btn1">提交</a></p>
            </div>
            <a class="guanbi"  onclick="$('#updateAddr').hide();"><img src="${path }/images/guan.png"/></a>
 		</div>
 		</form>
    </div>
    
    
    <div class="t-windows" id="showAddr" style="display: none;">   
	<div class="openwin" >
		<p class="openwin-top"><span class="title">我的联系方式</span></p>
 	    <div class="openwin-main">
			<p style="margin-top:20px;"><span class="leftw">收件人：</span>${addr.name }</p>
            <p><span class="leftw">手机号码：</span>${addr.phone }</p>
            <p><span class="leftw">收件地址：</span>${addr.address }&nbsp;</p>
            <p><span class="leftw">邮编：</span>${addr.zipCode }</p>
            <p class="textcenter"  style="margin-top:20px;"><a href="javascript:void(0);" onclick="updateAddress()" class="openwin-btn1">修改</a></p>
		</div>
        <a class="guanbi" href="javascript:void(0);" onclick="$('#showAddr').hide();"><img src="${path }/images/guan.png"/></a>
 	</div>
    </div>
    
    
<!--弹层—主要内容-->
<div id="goodsDiv" style="display: none;"></div>

<div class="clearfix"></div>
<%@ include file="/WEB-INF/page/common/footer.jsp"%>
<style type="text/css">
@media only screen and (min-width: 321px) and (max-width: 1024px){ 
.activity-notice,.prize-body,.header .topbar,.navbar,.gc-footer{ width:1100px; } 
} 
</style>
<script type="text/javascript">
$(function(){
	if('${counter}'>2){
		$('#codeView').css('display','block');
	}
});
/**
 * 去兑换
 */
function toChange(sycee,goodsId){
	var userSycee='${userSycee}';
	if(userSycee==''||userSycee==''){
		layer.msg('请先登录',2,5);return;
	}
	//查询商品详情
	$.ajax({
		url : '${basePath}/sycee/goods/'+goodsId+'.html',
		data : {},
		type : 'post',
		dataType : 'text',
		success : function(data) {
			$.layer({
			    type : 1,
			    title : false,
			    fix : false,
			    skin: 'layui-layer-rim', //加上边框
			    offset:['50px' , ''],
			    area : ['650','370px'],
			    page : {html : data}
			});
		},
		error : function(data) {
			layer.msg('网络连接异常，请刷新页面或稍后重试！', 1, 5);
		}
	});
}
/**
 * 提交兑换
 */
function subExchange(goodsId,goodsName,obj){
	$(obj).removeAttr("onclick");
	var _num = $('#num').val();
	if(_num==''||_num<1){
		layer.alert("兑换数量有误");
		return;
	}
	layer.confirm('您将兑换：'+goodsName+'，数量：'+_num, function(){
		$.ajax({
			url : '${basePath}/sycee/exchange/goods/'+goodsId+'.html?num='+_num,
			data : {},
			type : 'post',
			dataType : 'json',
			success : function(data) {
				if(data.code=='0'){
					layer.alert(data.message);
				}else{
					if(data.message=='choujiang'){
						$.ajax({
							url : "${path}/lottery_chance/queryLotteryChanceUseNumTotal.html",
							type : 'post',
							success : function(data){
								$("#chanceTotalNumTip").attr("style","display:;");
								$("#chanceTotalNumTip").html("<a href='${path }/lottery_chance/info.html' rel='nofollow'>(<em class='orange-c'>"+data+"</em>)</a>");
							}
						});
						$.layer({
							title:'兑换成功',
							dialog:{
								type:1,
								msg:'恭喜！您已使用元宝成功兑换'+goodsName+'，iPhone6、1888元大奖等你带走！',
							 	btns:2,
							 	btn: ['去抽奖','我知道了'],
							 	yes: function(index){
							 		window.location='${path}/lottery_chance/info.html';
							 	},
							 	no:function(index){
							 		//$('.xubox_layer').hide();
							 		//$('.xubox_shade').hide();
							 		window.location='${path}/sycee.html';
							 	}
							}
						 });
					}else{
						layer.msg(data.message,2,1,function(){
							if(data.revision==2){
								 $("#inputAddr").show();
							}else{
								window.location='${path}/sycee.html';
							}
						});
					}
				}
			},
			error : function(data) {
				layer.msg('网络连接异常，请刷新页面或稍后重试！', 1, 5);
			}
		});
	});
	$(obj).attr("onclick","javascript:subExchange("+goodsId+",'"+goodsName+"',this);");
}
/**
 * 登录
 */
function login(){
	if($('#username').val()==''){
		layer.msg('手机号/用户名/邮箱不能为空',1,5);
		return;
	}
	if($('#passwd').val()==''){
		layer.msg('登录密码不能为空',1,5);
		return;
	}
	var _load;
	var _saveid=0;
	if($("#saveidCB")[0].checked==true){
		_saveid=1;
	}
	$("#loginFm").ajaxSubmit({
	    url: '${path }/member/login.html',
	    type: "POST",
	    data:{cookieusername:encodeURI($('#username').val()),saveid:_saveid},
	    beforeSend:function(){
	    	_load = layer.load('登录中..');
	    },
	    success:function(data){
	    	loadimage();
	    	layer.close(_load);
	    	if (data.code == '0') {
				$("#loginTip").html(data.message);
				$('#revision').val(data.revision);
				return;
			}else if(data.code == '3'){
				if(parseInt(data.counter)>2){
				 $('#codeView').css('display','block');
				}
				$("#loginTip").html(data.message);
				$('#revision').val(data.revision);
				return;
			}
	    	if(data.code == '1') {
			    window.location='${path}/sycee.html';
			}else if(data.code == '2'){
				window.open("${basePath}/AccountSafetyCentre/safetyIndex.html","_self");//前往注册认证界面
			}
	    },
		error : function(data) {
			layer.close(_load);
			layer.msg("网络连接超时，请您稍后重试", 2, 5);
			$('#revision').val(data.revision);
	    },
	    statusCode: {
			10001: function() {
				layer.msg('请不要频繁请求，稍后再试！', 1, 5);
			}
		}
	 });
}


/**
 * 提交联系方式
 */
function addSub(){
	if (Validator.Validate('AddrFm',4)==false){
		 $("#inputAddr").hide();
		 return; 
	}    	
	$("#AddrFm").ajaxSubmit({
	    url: '${basePath }/sycee/address/add.html',
	    type: "post",	    
	    success:function(data){
	    	if(data.code == '1') {
	    		$("#inputAddr").hide();
	    		layer.msg(data.message, 1, 1, function() {
	    			window.location='${path}/sycee.html';
				});
			}else{
				layer.alert(data.message);
			}
	    },
		error : function(data) {
			layer.close(_load);
			layer.msg("网络连接超时，请您稍后重试", 2, 5);
	    }
	 });
}
function updateAddr(){
	if (Validator.Validate('updateAddrFm',4)==false){
		 $("#updateAddr").hide();
		 return; 
	}    	
	$("#updateAddrFm").ajaxSubmit({
	    url: '${basePath }/sycee/address/add.html',
	    type: "post",	    
	    success:function(data){
	    	if(data.code == '1') {
	    		$("#inputAddr").hide();
	    		layer.msg(data.message, 1, 1, function() {
	    			window.location='${path}/sycee.html';
				});
			}else{
				layer.alert(data.message);
			}
	    },
		error : function(data) {
			layer.close(_load);
			layer.msg("网络连接超时，请您稍后重试", 2, 5);
	    }
	 });
}

function updateAddress(){
	$.ajax({
		url : '${basePath}/sycee/updateAddress.html',
		data : {},
		type : 'post',
		dataType : 'json',
		success : function(data) {
			$('#showAddr').hide();
			$('#updateAddr').show();
			$('#updateName').val(data.name);
			$('#updatePhone').val(data.phone);
			$('#updateAddress').val(data.address);
			$('#updateZipCode').val(data.zipCode);
		},
		error : function(data) {
			layer.msg('网络连接异常，请刷新页面或稍后重试！', 1, 5);
		}
	});
	
}
/**
 * 刷新验证码 
 */
 function loadimage() {
	document.getElementById("randImage").src = "${basePath}/random.jsp?"
			+ Math.random();
}  

</script>
</body>
</html>