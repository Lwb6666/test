<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/page/common/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>[顶玺金融理财产品]个人投资理财平台_互联网金融理财产品-顶玺金融</title>
<%@ include file="/WEB-INF/page/common/public3.jsp"%>
<meta name="keywords" content="顶玺金融,理财产品,互联网理财,金融理财产品,投资理财,投资理财产品,网络理财产品" />
<meta name="description" content="顶玺金融（www.dxjr.com），一个专注于消费金融与房产抵押贷款的中国社科院网贷评级A级的理财平台。致力于为投资理财用户和贷款用户两端搭建安全、规范、透明的互联网金融平台。顶玺为您的资金保驾护航！ ">
</head>
<body>
<%@ include file="/WEB-INF/page/common/header.jsp" %>
<div class="product-wrap wrapperbanner"><!--banner star-->
	<div class="grid-1100">
        <div class="tz-detail">
            <img src="${path}/images/v5/detalibanner.png" alt="个人理财产品"/>
            <ul class="col4">
	            <li><a href="${path }/dingqibao.html" >定期宝</a></li>
	            <li><a href="${path }/curInController.html"  class="active">活期宝</a></li>
	            <li><a href="${path }/toubiao.html">散标投资</a></li>
	            <li><a href="${path }/zhaiquan.html">债权转让</a></li>
            </ul>
        </div>
	</div>
</div>
<form action="" method="post" id="currentForm">
<div class="product-wrap"><!--定期宝star-->
	<div class="grid-1100">
        <div class="product-deatil clearfix ">
            <h1 class="f16 bule">活期宝详情</h1>
            <div class="tz-hqb clearfix" style="  position:relative;    ">
                <ul class="col3">
                    <li style="width:66%; ">
		                <div class="bar-boxd" >
		                <div class="bdr bdr-new "   > <span class="f14 gary " >预期收益率</span> <span class="oren pdl54  "  >5.6 <small class="f18 oren pdl20">%</small> </div>
		                <div class="bdr "   >
		                    <div class="tz-join" style=" padding-top:20px;     " >
		                    <h6><span>起投金额</span> <span style="font-size:20px; color:#f05e13; padding-left:10px; ">1</span><small class="f14" style="color:#333; padding-left:5px;">元</small></h6>
		                    <h6 style="padding-top:15px;">加入时间 <small class="f14" style="padding-left:10px; ">无限制</small></h6>
		                  </div>
		                  </div>
		                </div>
		                <div class="new-boxhq"  >
		                <div class="qx-l"> <span>投资期限</span>
		                    <p>活期</p>
	                    </div>
		                <div class="bz-r"> <span>保障方式</span>
		                    <p>风险保证金保障</p>
	                    </div>
		              </div>
	                </li>
                    <li style="border-left:1px dashed #ccc; " >
                    	<c:if test="${not empty account.useMoney}">
                    	<div class="tz-join-m" style="border-left:1px dashed #ccc; ">
                        	<div class="clearfix pdt10">
                        		<a href="${path }/account/topup/toTopupIndex.html" class="fr bd">充值</a>
                        		<span class="f14">账户余额：<strong class="oren f18 pdlr5"><fmt:formatNumber value="${account.useMoney}" pattern="#,##0.00" /></strong>元 </span>
                        	</div>
                            <div class="clearfix pdt10">
                            	<a href="javascript:enterMomey();" class="fr">自动输入金额</a>
                            	<input name="tenderMoney" id="tenderMoney"   maxlength="20" class="form-inpyt-sm yuan" style="width:200px;color:#999;" type="text" onblur="if(value==''){value='请输入加入金额';$('#tenderMoney').attr('style','color:#999;width:200px;');}" onfocus="if(value=='请输入加入金额'){value='';$('#tenderMoney').attr('style','color:#FFFFF;width:200px;'); }"  value="请输入加入金额"/> 
                            </div>
                            <div class="clearfix pdt10">
                            <!-- 	<input name="password" id="password" autocomplete="off" maxlength="30" class="form-inpyt-sm" style="width:106px;color:#999;" type="text" value="请输入交易密码"/>
                                <input type="password"  id="payPassword" name="payPassword" autocomplete="off" maxlength="30" class="form-inpyt-sm" style="display:none;width:106px;color:#999;"/> 
                            	<input name="checkCode" id="checkCode" maxlength="4" class="form-inpyt-sm marginl10" style="width:106px;color:#999;" type="text"  onblur="if(value==''){value='请输入验证码';$('#checkCode').attr('style','color:#999;width:106px;');}" onfocus="if(value=='请输入验证码'){value='';$('#checkCode').attr('style','color:#FFFFF;width:106px;'); }"  value="请输入验证码"/>
                            	<img onclick="loadimage()" name="randImage" id="randImage" src="${basePath}/random.jsp"/> -->
                            </div>
                            <div class="clearfix pdt20">
                            	<div class="btn-box2">
                                	<button onclick="joinFunction();" type="button" class="btn btn-join f16">立即加入</button>
                                </div>
                             </div>
                        </div>
                        </c:if>
                        <c:if test="${empty account.useMoney}">
                        <div class="detail-join-m" style="  margin-left:30px">
	                    	<div class="clearfix pdt10 pd20" style="padding-top:50px;"><span class="f14 gary2"><span>账户余额</span><a href="${path}/member/toLoginPage.html?backUrl=1" class="pdla login" style="color: hsl(203, 88%, 58%); padding:0 10px;">登录</a><span>后可见</span></span> </div>
		                    	<div class="clearfix pdt38" style="padding-top:62px; ">
			                        <div class="btn-box2" style="width:315px;">
			                           	<button type="button" class="btn btn-join f16" onclick="window.location='${path}/member/toLoginPage.html?backUrl=1';">立即加入</button>
			                        </div>
		                     	</div>
	                		</div>
                		</c:if>
                    </li>
		        </ul>
             </div>
        </div>
    </div>
</div>
<input type="hidden" name="useMoney" id="useMoney" value="${account.useMoney}"/>
</form>
<div class="product-wrap"><!--定期宝star-->
	<div class="grid-1100 tz-hqb-bg" style="margin-top:60px">
    	<div class="hqb-word01">
        	<h2>1元起投，随存随取</h2>
            <p>期限无限制，投资更灵活</p>
            <p>随时可赎，无需任何费用</p>
            <p>单日最多可转出5笔，单笔转出限额可达10万元</p>
        </div>
        <div class="hqb-word02">
        	<h2>高收益，理财功能更多样</h2>
            <p>预期收益5.6%，是余额宝的1.4倍，活期存款的16倍</p>
            <p>随时用活期宝资金申购顶玺金融其它理财产品，享受最高15%的超高收益</p>
            <p>从此资金不再站岗，万元投资，单月收益47元</p>
        </div>
        <div class="hqb-word03">
        	<h2>资金安全有保障</h2>
            <p>低风险的货币基金理财产品（申购银行理财计划）</p>
            <p>活期宝资金享受本息保障</p>
        </div>
     </div>
     <div class="grid-1100">
     <div class="prduct-menu2 clearfix">
        	<div class="menu-tbl">
            	<ul class="col2"><li class="active">活期宝介绍</li><li>常见问题</li></ul>
            </div>
            <div class="menucont" style="clear:both">
            	<div class="tbl-cont">
                	<div class="hqb-js">
                    	<div class="fl title">名称</div>
                        <div class="fl word">活期宝</div>
                    </div>
                    <div class="hqb-js">
                    	<div class="fl title">业务介绍</div>
                        <div class="fl word">活期宝是顶玺金融推出的一款货币基金理财产品（申购银行理财计划），帮助投资人将加入的资金享受稳定收益，每天都可赎回的投资计划。投资人加入的资金及加入资金所产生的收益，在计息后可申请部分或全部赎回。</div>
                    </div>
                    <div class="hqb-js clearfix">
                    	<div class="fl title">预期收益</div>
                        <div class="fl word">5.6%</div>
                    </div>
                    <div class="hqb-js">
                    	<div class="fl title">期限</div>
                        <div class="fl word">不定期，计息中金额可以随时申请赎回。</div>
                    </div>
                    <div class="hqb-js">
                    	<div class="fl title">加入规则</div>
                        <div class="fl word">起购金额1元起</div>
                    </div>
                    <div class="hqb-js">
                    	<div class="fl title">加入费用</div>
                        <div class="fl word">无任何费用</div>
                    </div>
                    <div class="hqb-js">
                    	<div class="fl title">利息计算规则</div>
                        <div class="fl word">活期宝利率/365*（活期宝账户金额-未产生收益的资金）。未产生收益的资金即收益规则的限制不产生收益资金。</div>
                    </div>
                    <div class="hqb-js">
                    	<div class="fl title">赎回规则</div>
                        <div class="fl word">每天限制5笔转出，每笔限制10万元；转出的资金当日不计息。<a href="${path }/bangzhu/23.html#listId=6" class="bule">《详细赎回规则》</a></div>
                    </div>
                    <div class="hqb-js">
                    	<div class="fl title">收益规则</div>
                        <div class="fl word">工作日15:00前加入，加入后的第1个工作日开始计算收益，次日显示收益。工作日15:00后加入，加入后的第2个工作日开始计算收益，次日显示收益。节假日加入，加入后第2个工作日开始计算收益，次日显示收益。<a href="${path }/bangzhu/23.html#listId=4" class="bule">《详细收益规则》</a></div>
                    </div>
                </div>
                <div class="tbl-cont" style="display: none">
                	<div class="hqb-js2">
                    	<h6 class="f14">1. 活期宝有什么优点？</h6>
                        <p>加入活期宝后，可享受5.6%的预期收益，远高于银行活期存款利率 ;同时还提高了资金流动性与灵活性，真正达到资金收益最大化。</p>
                    </div>
                    <div class="hqb-js2">
                    	<h6 class="f14">2. 活期宝是否收费？</h6>
                        <p>加入活期宝不收取任何费用。当从活期宝中将资金转至可用余额后申请提现，需按提现规则收取相应的提现费 。</p>
                    </div>
                    <div class="hqb-js2">
                    	<h6 class="f14">3. 为什么活期宝中有金额，但活期生息到账为零？</h6>
                        <p>根据收益计算公式，您的收益由活期宝资产总额决定，如果您活期宝资产较少，计算出来的收益可能不足一分钱，不足一分钱的收益将直接舍去。建议您活期宝资产保持在100元以上。</p>
                    </div>
                    <div class="hqb-js2">
                    	<h6 class="f14">4. 加入活期宝后，是否影响提现？</h6>
                        <p>加入活期宝后，对提现没有任何影响。活期宝的目的是让投资人获取收益最大化，同时不影响资金流动性需求。</p>
                    </div>
                    <div class="hqb-js2">
                    	<h6 class="f14">5. 如何使用活期宝资产投资？</h6>
                        <p>投资时直接使用账户可用余额进行投资，若账户可用余额不足，可选择活期宝资产投资。</p>
                    </div>
                 </div>
                </div>
      </div>
     </div>
</div>
<%@ include file="/WEB-INF/page/common/footer.jsp" %>
</body>
<script type="text/javascript">
/**
 * 点击自动输入金额触发事件
 */
function enterMomey(){
	if(${null==portal:currentUser()}){
		 layer.msg("请先登录", 1, 5,function(){
			 window.location.href="${path}/member/toLoginPage.html";
		 });
		 return;
	}
	if(${portal:hasRole("borrow")}){
		 layer.msg("您是借款用户,不能进行此操作", 1, 5);
		 return;
	}
	var useMoney = Number($("#useMoney").val());
	$("#tenderMoney").val(useMoney);
}
/**
*  加入金额光标移开触发事件
*/
function  changeTenderMoney(){
	if(${null==portal:currentUser()}){
		 layer.msg("请先登录", 1, 5,function(){
			 window.location.href="${path}/member/toLoginPage.html";
		 });
		 return;
	}
	if(${portal:hasRole("borrow")}){
		 layer.msg("您是借款用户,不能进行此操作", 1, 5);
		 return;
	}
	var payMoney= $.trim($("#tenderMoney").val());
	$("#tenderMoney").val(payMoney);
	var moneyReg = /^(([1-9]\d{0,9})|0)(\.\d*)?$/;//金额的正则表达式
	if (payMoney.length>0 && !moneyReg.test((payMoney))) {
		layer.msg("加入金额格式不正确", 2, 5);
		$("#tenderMoney").focus();
		return;
	}else{
		//2位小数金额的正则表达式
		moneyReg = /^(([1-9]\d{0,9})|0)(\.\d{1,2})?$/;
		if (payMoney.length>0 && !moneyReg.test((payMoney))) {
			layer.msg("输入金额小数不能大于2位", 2, 5);
			$("#tenderMoney").focus();
			return;
		}
	}
	if(payMoney == ""){
		$("#tenderMoney").focus();
		return;
	}
	var useMoney = parseFloat($("#useMoney").val());
	if (parseFloat(payMoney) > useMoney) {
		layer.msg("账户余额小于加入金额，不能加入", 2, 5);
		return;
	}
	if(parseFloat(payMoney) < 1){
		layer.msg("加入金额不能小于1元", 2, 5);
		return;
	}
	/* $("#payPassword").focus(); */
}
/* $(document).ready(function(){ 
	$(".form-inpyt-sm").focus(function(){ 
		if($(this).attr('id')=='password'){ 
			$(this).hide(); 
			$('#payPassword').show(); 
			$('#payPassword').focus(); 
			 $("#payPassword").attr("style","color:#FFFFF;width:106px;");  
		} 
	}); 
	$(".form-inpyt-sm").blur(function(){ 
		if($(this).attr('id')=='payPassword' && $(this).val()==''){ 
			$(this).hide(); 
			$('#password').show(); 
			$('#password').val('请输入交易密码'); 
			$("#password").attr("style","color:#999;width:106px;");
		} 
		}); 
	});   */
/**
 * 点击进入金额按钮触发事件
 */
function joinFunction(){
	changeTenderMoney();
	if(${null==portal:currentUser()}){
		 layer.msg("请先登录", 1, 5,function(){
			 window.location.href="${path}/member/toLoginPage.html";
		 });
		 return;
	}
	if(${portal:hasRole("borrow")}){
		 layer.msg("您是借款用户,不能进行此操作", 1, 5);
		 return;
	}
	var payMoney= $.trim($("#tenderMoney").val());
	$("#tenderMoney").val(payMoney);
	var moneyReg = /^(([1-9]\d{0,9})|0)(\.\d*)?$/;//金额的正则表达式
	if (payMoney.length>0 && !moneyReg.test((payMoney))) {
		layer.msg("加入金额格式不正确", 2, 5);
		$("#tenderMoney").focus();
		return;
	}else{
		//2位小数金额的正则表达式
		moneyReg = /^(([1-9]\d{0,9})|0)(\.\d{1,2})?$/;
		if (payMoney.length>0 && !moneyReg.test((payMoney))) {
			layer.msg("输入金额小数不能大于2位", 2, 5);
			$("#tenderMoney").focus();
			return;
		}
		
	}
	if(payMoney == ""){
		//$("#tenderMoney").val("0");
		layer.msg("加入金额未填写", 2, 5);
		$("#tenderMoney").focus();
		return;
	}
	var useMoney = parseFloat($("#useMoney").val());
	if (parseFloat(payMoney) > useMoney) {
		layer.msg("账户余额小于加入金额，不能加入", 2, 5);
		return;
	}
	if(parseFloat(payMoney) < 1){
		layer.msg("加入金额不能小于1元", 2, 5);
		return;
	}
	var msg = "";
/* 	var payPassword = $("#payPassword").val(); */
/* 	var checkCode = $("#checkCode").val(); */
	/* if (payPassword == null || payPassword == "") {
		layer.msg("交易密码未填写！", 2, 5);
		return;
	} */
	/* if (checkCode == null || checkCode == "") {
		layer.msg("验证码未填写！", 2, 5);
		return;
	} */
	var re = /(\d{1,3})(?=(\d{3})+(?:$|\.))/g;  
	if(layer.confirm("你确认把"+$("#tenderMoney").val().replace(re, "$1,")+"元加入到活期宝吗？",function(){
		saveCurrent();
	}));
}
/**
* 活期宝加入按钮触发事件
*/
function saveCurrent(){
	   var _load = layer.load('处理中..');
   $("#currentForm").ajaxSubmit({
	   type: "POST",
	   url:"${basePath}/curInController/doCurrentIn.html",
	   success:function (data){
			if("0"==data.code || "-1"==data.code || "-2"==data.code || "-3"==data.code || "-4"==data.code || "-5"==data.code){
				layer.msg(data.message, 1, 5,function(){
					if("0"==data.code){
						window.location.href="${path}/member/toLoginPage.html";
					}else if("-1"==data.code){
				    	window.location.href="${path}/AccountSafetyCentre/safetyIndex.html";
				    }else if("-2"==data.code){
				    	window.location.href="${path}/AccountSafetyCentre/safetyIndex.html";
				    }else if("-4"==data.code){
				    	window.location.href="${path}/AccountSafetyCentre/safetyIndex.html";
				    }else if("-5"==data.code){
				    	window.location.href="${path}/AccountSafetyCentre/safetyIndex.html";
				    }
				});
				return;
			}
			if("-99" == data.code){
				return;
			}
			if (data.code == '99' || data.code == '-6') {
				layer.close(_load);
				layer.msg(data.message, 2, 5);
			}else{
				layer.close(_load);
				var re=/(\d{1,3})(?=(\d{3})+(?:$|\.))/g;
				layer.alert("此次交易成功转入"+$("#tenderMoney").val().replace(re, "$1,")+"元到活期宝" ,1, "温馨提示",function(){
					window.location.href="${path}/curInController.html";
	    		});
			}
	   },
	   error:function (result){
			layer.close(_load);
			layer.msg('网络连接超时,请刷新页面或稍后重试!', 2, 5);
	   }
   });
	}


</script>