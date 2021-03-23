<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/page/common/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ include file="/WEB-INF/page/common/public2.jsp"%>
<link href="${basePath}/css/circle.css" rel="stylesheet" type="text/css" />
<title>直通车转让</title>
</head>

<body>
	<!--头部开始-->
	<%@ include file="/WEB-INF/page/common/header.jsp"%>
	<!--头部结束-->
 
	<!--内容开始-->
	<div class="product-wrap wrapperbanner"><!--信息star-->
	<div class="grid-1100">
    	<div class="product-deatil clearfix ">
        	<h1 class="f16">转让信息</h1>
         
            <div class="detail-col-l fl item-bd-r">
            <div class="f16 pdtt20"><span>投标直通车</span><span class="bule">
              <a href="${path}/accountdetail/show.html?userName=${portal:encode(portal:encode(firstTransfer.userNameEncrypt))}">${firstTransfer.userNameSecret}</a>
            </span></div>
            	<div class="ptb-list clearfix">
                
                	<p><span>债权价格</span><label><strong class="f20 oren"><fmt:formatNumber
										value="${firstTransfer.account}" pattern="#,##0.00" /></strong>元</label></p>
                    <p><span>转让价格</span><label><strong class="f20 oren"><fmt:formatNumber
										value="${firstTransfer.accountReal}" pattern="#,##0.00" /></strong>元</label></p>
                    <p><span>奖励</span><label><strong class="f20 oren"><fmt:formatNumber
										value="${firstTransfer.awards}" pattern="#,##0.00" /></strong>元</label></p>
         
                </div>
       		<div class="ptb-minal clearfix">
            <div class="ptb-box item-bd-b">
            <p>
            <em>发布日期 </em>
            <em><fmt:formatDate value="${firstTransfer.addtime}"
									pattern="yyyy-MM-dd" /></em>
            </p>
            <p>
            <em>剩余时间  </em>
            <em><span id="publishTime_d" >0</span>天
				<span id="publishTime_h" >0</span>时
				<span id="publishTime_m" >0</span>分
				<span id="publishTime_s" >0</span>秒
			</em>
            </p>
            
            </div>
            
            <div class="ptb-box clearfix">
            <p class="w100">
            <em>温馨提示</em>
            <em>认购转让的直通车后，资金需至少锁定6个月</em>
            </p>
          
            </div>
            </div>
            
            
             </div>
             <div class="detail-col-r fr">
             <c:if test="${firstTransfer.status==2}">
                <div class="detail-join-m">
                    <div class="clearfix pdt10">
                    <c:choose>
                   <c:when test="${not empty account.useMoney}">
                    <a href="${path}/account/topup/toTopupIndex.html" target="_blank" class="fr bd">充值</a>
                   </c:when>
                   </c:choose>
                    <span class="f14">账户余额<c:choose>
									<c:when test="${not empty account.useMoney}">
									    <strong class="oren f18 pdla">￥<fmt:formatNumber
											value="${account.useMoney}" pattern="#,##0.00" /></strong>元
                                        <div class="clearfix pdt10">
                                        <c:if test="${isExistCurAccount == true }">
                                         <input type="checkbox" name ="isUseCurMoney" checked="checked" id="isUseCurMoney" value="1" onclick="useCurMoney();" class="checkbox-btn"><span class="gary2 f14 "> 使用活期宝余额：<strong class="f14 pdla gary2"><fmt:formatNumber value="${maxCurMoney}"  pattern="#,##0.00" /></strong>元</span> </div>
								        </c:if>
								        
								    </c:when>
									<c:otherwise>
										<a href="${path}/member/toLoginPage.html?backUrl=1" >登录</a>后查看
 
									</c:otherwise>
								</c:choose> </span></div>
                                <div class="clearfix pdt10"><span class="f14">当前可投<strong class="oren f18 pdla">
                                <c:choose>
									<c:when test="${not empty effectiveAccount}"><fmt:formatNumber
											value="${effectiveAccount}" pattern="#,##0.00" />
										</c:when>
									<c:otherwise>
										 <a href="${path}/member/toLoginPage.html?backUrl=1" >登录后查看</a>
									</c:otherwise>
								</c:choose></strong>元 </span></div>
 
                    <div class="clearfix pdt26">
                        <div class="btn-box2">
                            <button type="button" class="btn btn-join f18" onclick="subscribe();" >立即认购</button>
                        </div>
                     </div>
                </div>
                </c:if>
                <c:if test="${firstTransfer.status==4}">
                <div class="detali-zrz">
                	<h5 class="f14">
                        <p class="pdtb20"><span class="f24 oren">￥<fmt:formatNumber
									value="${firstTransfer.accountReal}" pattern="#,##0.00" /></span>元<br/>认购金额</p>
                        <p class="pdtb20">满额时间 <fmt:formatDate value="${firstTransfer.successTime}"
								pattern="yyyy-MM-dd" /></p>
                    </h5>
                    <a href="#" class="btn btn-ywc f18" style="width:100%; cursor:default">已转让</a>
                </div>
                 
                </c:if>
             </div>
         </div>
    </div>
    
    
    
    
    
</div>

 
<div class="product-wrap"><!--table-->
	<div class="grid-1100 background">
    	<div class="prduct-menu">
        	<div class="menu-tbl">
            	<ul class="col2">
            	<li class="active" onclick="turnSubscribeList('${basePath}/zhitongche/subscribe/querySubscribeList/${firstTransfer.id}/1.html');">购买记录</li>
            	<li onclick="turnFirstTransferedList('${basePath}/zhitongche/transferborrow/queryTransferBorrowList/${firstTransfer.id}/1.html');">相关标的信息</li></ul>
            </div>
            <div class="menucont" style="clear:both">
            	<div class="tbl-cont" id="recordTab">
                 	
                </div>
                <div class="tbl-cont" id="recordTab2" style="display:none">
                
                </div>
                
                
            </div>
        </div>
    </div>
</div>

<!--弹层star--->
<form  action="" method="post" id="subscribeTransferForm">
<div class="layer-jion">
	<div class="cont-word">
    	<div class="title"><h4>认购直通车转让</h4><a href="#" class="icon icon-close fr"></a></div>
        <div class="form-info-layer">
        	<div class="form-col3" style="display:none;">
            <style>
            	.rengoubox li{ font-size:14px; color:#333; line-height:30px; text-align:left; padding-left:15px;}
				.rengoubox li strong{color:#1b9cd3;}
            </style>
            
            <ul class="rengoubox">
                 <li>  <span> 债权剩余价值：</span><strong>￥13,435,10</strong></li>
                 <li><span>剩余本金:</span><strong>￥13,435,10</strong><span style="display:inline-block; width:30px;"></span><span>待收利息：</span><strong>13,435,10</strong></li>
            
            </ul>       
            </div>
        	<div class="form-col3">
                <label for="" class="colleft form-lable">转让价格</label>
                <span class="fl money"><strong class="oren f14"><fmt:formatNumber
										value="${firstTransfer.accountReal}" pattern="#,##0.00" /></strong>元</span>
            </div>
            <div class="form-col3">
                <label for="" class="colleft form-lable">账户余额</label>
                <span class="fl money"><strong class="oren f14"><fmt:formatNumber
										value="${account.useMoney}" pattern="#,##0.00" /></strong>元</span>
            </div>
       <%--  	<div class="form-col2">
                <label for="" class="colleft form-lable">交易密码</label>
                <input  id="password"  class="form-inpyt-sm" style="float:left; width:120px;color:#999;" type="text" value="请输入交易密码"/>
                <input type="password"  id="payPassword" name="payPassword"  class="form-inpyt-sm"  style="display:none;width:120px;color:#999;"/><a href="${path}/AccountSafetyCentre/safetyCentre/enterFindTransactionPwd.html" target="_blank" class="fl pdlr10 line32">忘记密码</a>
            </div> --%>
            <c:if test="${null!=firstTransfer.bidPassword && firstTransfer.bidPassword!= ''}">
            <div class="form-col2">
                <label for="" class="colleft form-lable">认购密码</label>
                <input  id="password1"  class="form-inpyt-sm1" style="width:120px;color:#999;" type="text" value="请输入认购密码"/>
                <input type="password"  id="bidPassword" name="bidPassword"  class="form-inpyt-sm1"  style="display:none;width:120px;color:#999;"/>
            </div>
            </c:if>
        <%--     <div class="form-col2">
                <label for="" class="colleft form-lable">验证码</label>
                <input type="text"  name="checkCode" id="checkCode" maxlength="4"  style="width:120px;color:#999;" class="colright form-inpyt-sm"  onblur="if(value==''){value='验证码';$('#checkCode').attr('style','color:#999;width:120px;');}" onfocus="if(value=='验证码'){value='';$('#checkCode').attr('style','color:#FFFFF;width:120px;'); }" value="验证码" >
                <a href="javascript:loadimage();" class="fl" style="float:right;padding-right: 45px;padding-top: 10px;"><img name="randImage" id="randImage" 
					src="${basePath}/random.jsp"/></a>
            </div> --%>
            
            <div class="form-col2">
            <button type="button" class="remove">取消</button><button type="button" class="enter"  id="btnTenderBorrow" onClick="tender_borrow();">确认</button>
            </div>
         </div>
    </div> 
</div>

<input type="hidden" name ="isUseCurMoney"  id="isUseCurMoney1" value="0" />
<input type="hidden" name="accountReal" id="accountReal" value="${firstTransfer.accountReal }"/>
<input type="hidden" id="useMoney" value="${account.useMoney }"/>
<input type="hidden" name="transferId" id="transferId" value="${firstTransfer.id}"/>
<input type="hidden" name="hidFirstTransferId" id="hidFirstTransferId" value="${firstTransfer.id}"/>
<input type="hidden" name="platform" id="platform" value="1"/>
</form>
<!--弹层end--->
<div class="clearfix bompd60"></div>
<%@ include file="/WEB-INF/page/common/footer.jsp"%>
</body>
<style type="text/css">
.form-inpyt-sm1 {
    float:left;
	padding-left:6px;
	font-size: 14px;
	*line-height:32px;
	line-height:32px;
	height:32px;
	color:#333;
	background-color: #fff;
	background-image: none;
	border: 1px solid #ccc;
	-webkit-box-shadow: inset 0 1px 1px rgba(0,0,0,.075);
	box-shadow: inset 0 1px 1px rgba(0,0,0,.075);
	-webkit-transition: border-color ease-in-out .15s, -webkit-box-shadow ease-in-out .15s;
	-o-transition: border-color ease-in-out .15s, box-shadow ease-in-out .15s;
	transition: border-color ease-in-out .15s, box-shadow ease-in-out .15s
}

</style>
<script type="text/javascript">
/* $(document).ready(function(){ 
	$(".form-inpyt-sm").focus(function(){ 
		if($(this).attr('id')=='password'){ 
			$(this).hide(); 
			$('#payPassword').show(); 
			$('#payPassword').focus(); 
			$("#payPassword").attr("style","color:#FFFFF;width:120px;float:left;");  
		} 
	}); 
	$(".form-inpyt-sm").blur(function(){ 
		if($(this).attr('id')=='payPassword' && $(this).val()==''){ 
			$(this).hide(); 
			$('#password').show(); 
			$('#password').val('请输入交易密码'); 
			$("#password").attr("style","color:#999;width:120px;float:left;");
		} 
		}); 
	});  */
$(document).ready(function(){ 
	$(".form-inpyt-sm1").focus(function(){ 
		if($(this).attr('id')=='password1'){ 
			$(this).hide(); 
			$('#bidPassword').show(); 
			$('#bidPassword').focus(); 
			$("#bidPassword").attr("style","color:#FFFFF;width:120px;");  
		} 
	}); 
	$(".form-inpyt-sm1").blur(function(){ 
		if($(this).attr('id')=='bidPassword' && $(this).val()==''){ 
			$(this).hide(); 
			$('#password1').show(); 
			$('#password1').val('请输入认购密码'); 
			$("#password1").attr("style","color:#999;width:120px;");
		} 
		}); 
	});  
	
	var SysSecond; 
	var InterValObj;
	
	//页面 当前时间
	var addNowTime=0;
	//当前循环对象
	var addInterValObj;

	/**
	 * 页面初始化
	 */
	$(function() {
		 
		turnSubscribeListParent(1);
		
		<c:if test="${firstTransfer.status == 2}">
		SysSecond = parseInt((new Date('${endTime}').getTime() / 1000) - new Date('${nowTime}').getTime() / 1000); //这里获取倒计时的起始时间 
		SetRemainTime();
		InterValObj = window.setInterval(SetRemainTime, 1000); //间隔函数，1秒执行

		</c:if>
		
		$('.remove').click(function(){
			$(".layer-jion").hide();
			$(".cont-word").animate({top:"0px"});
		});
		
		useCurMoney();//2015.12.18 默认勾选活期宝
	});
	 
	/**
	 * tab点击功能
	 */
	function SetRemainTime() {
		if (SysSecond > 0) {
			SysSecond = SysSecond - 1;
			var second = Math.floor(SysSecond % 60); // 计算秒     
			var minute = Math.floor((SysSecond / 60) % 60); //计算分 
			var hour = Math.floor((SysSecond / 3600) % 24); //计算小时 
			var day = Math.floor((SysSecond / 3600) / 24); //计算天 
			$("#publishTime_d").text(day);
			$("#publishTime_h").text(hour);
			$("#publishTime_m").text(minute);
			$("#publishTime_s").text(second);
		} else {//剩余时间小于或等于0的时候，就停止间隔函数 
			window.clearInterval(InterValObj);
			//这里可以添加倒计时时间为0后需要执行的事件
			$("#publishTime_d").text('0');
			$("#publishTime_h").text('0');
			$("#publishTime_m").text('0');
			$("#publishTime_s").text('0');
		}
	}
	
	/**
	 * tab点击功能
	 */
	function turnSubscribeList(_url) {
		$.ajax({
			url : _url,
			data : {},
			type : 'post',
			dataType : 'html',
			success : function(data) {
				$("#recordTab").html(data);
			},
			error : function(data) {
				layer.msg('网络连接异常，请刷新页面或稍后重试！', 1, 5);
			}
		});
	}
	function turnFirstTransferedList(_url) {
		$.ajax({
			url : _url,
			data : {},
			type : 'post',
			dataType : 'html',
			success : function(data) {
				$("#recordTab2").html(data);
			},
			error : function(data) {
				layer.msg('网络连接异常，请刷新页面或稍后重试！', 1, 5);
			}
		});
	}
	
	/**
	 * 购买记录翻页
	 */
	function turnSubscribeListParent(pageNo){
		var firstTransferId = $("#hidFirstTransferId").val();
		turnSubscribeList('${basePath}/zhitongche/subscribe/querySubscribeList/'+firstTransferId+'/'+pageNo+'.html');
	}
	
	/**
	 * 债权价格详情翻页
	 */
	function turnFirstTransferedListParent(pageNo){
		
		turnFirstTransferedList('${basePath}/zhitongche/transferborrow/queryTransferBorrowList/'+firstTransferId+'/'+pageNo+'.html');
	}
	function subscribe(){
	 
		var firstTransferId = $("#hidFirstTransferId").val();
		 
		if(${null==portal:currentUser()}){
			 layer.msg("请先登录", 1, 5,function(){
				 window.location.href="${path}/member/toLoginPage.html?backUrl=1";
			  });
			 return;
		 }
		 if(${portal:hasRole("borrow")}){
			 layer.msg("您是借款用户,不能进行此操作", 1, 5);
			 return;
		 }
		 if (SysSecond <= 0) {
			layer.msg("购买时间已到期,无法购买", 1, 5);
			return;
		 }
		 $.ajax({
				url : '${basePath}/tender/judgTender.html',
				data : {type:2,transferId:firstTransferId},
				type : 'post',
				dataType : 'json',
				success : function(data) {
					if("0"==data.code || "-1"==data.code || "-2"==data.code || "-3"==data.code || "-4"==data.code || "-5"==data.code){
						layer.msg(data.message, 1, 5,function(){
							if("-1"==data.code){
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
					if("-7"==data.code){
						layer.msg(data.message, 2, 5);
						return;
					}
								
				},
				error : function(data) {
					layer.msg("网络连接异常，请刷新页面或稍后重试！", 2, 5);
				}
			});
		   if(!validateTenderData()){
			 return ;
		   }
		    $(".layer-jion").show();
			$(".cont-word .contls-box").animate({top:"20px"});
		/* 	$('#checkCode').val('');
			$('#bidPassword').val('');
			$('#payPassword').val(''); */
	}
	/**
	 * 购买功能
	 */
	function toSubscribe() {
		var firstTransferId = $("#hidFirstTransferId").val();
		if(${null==portal:currentUser()}){
			 layer.msg("请先登录", 1, 5,function(){
				 window.location.href="${path}/member/toLoginPage.html?backUrl=1";
			 });
			 return;
		 }
		 if(${portal:hasRole("borrow")}){
			 layer.msg("您是借款用户,不能进行此操作", 1, 5);
			 return;
		 }
		 if (SysSecond <= 0) {
			layer.msg("购买时间已到期,无法购买", 1, 5);
			return;
		 }
		 $.ajax({
				url : '${basePath}/tender/judgTender.html',
				data : {type:2,transferId:firstTransferId},
				type : 'post',
				dataType : 'json',
				success : function(data) {
					if("0"==data.code || "-1"==data.code || "-2"==data.code || "-3"==data.code || "-4"==data.code || "-5"==data.code){
						layer.msg(data.message, 1, 5,function(){
							if("-1"==data.code){
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
					if("-7"==data.code){
						layer.msg(data.message, 2, 5);
						return;
					}
					$.layer({
						type : 2,
						fix : false,
						shade : [0.6, '#E3E3E3' , true],
						shadeClose : true,
						border : [10 , 0.7 , '#272822', true],
						title : ['',false],
						offset : ['100px',''],
						area : ['570px','410px'],
						iframe : {src : '${basePath}/zhitongche/zhuanrang/queryTransferPopById/${firstTransfer.id}.html'},
						close : function(index) {
							window.location.href = "${basePath}/zhitongche/zhuanrang/queryTransferById/${firstTransfer.id}.html";
							layer.close(index);
						}
					});			
				},
				error : function(data) {
					layer.msg("网络连接异常，请刷新页面或稍后重试！", 2, 5);
				}
			});	
	}
	/**
	 * 使用活期宝金额
	 */
	function useCurMoney(){
		var isUseCurMoney = $("#isUseCurMoney").val();
		var saveidChecked = $("#isUseCurMoney")[0].checked; 
		 
		if(!saveidChecked){
			$("#isUseCurMoney1").val(1);
			$("#isUseCurMoney").attr("checked",true);
		}else{
			$("#isUseCurMoney1").val(0);
			$("#isUseCurMoney").attr("checked",'');
		}
	}
	/**
	 * 验证开通数据
	 */
	function validateTenderData() {
		var msg = "";
		var hidAccountReal = Number($("#accountReal").val());
		var hidUseMoney = Number($("#useMoney").val());
		var maxCurMoney = Number("${maxCurMoney}");
		 
		if(${isExistCurAccount}){
		 
			if($("#isUseCurMoney")[0].checked){
				$('#isUseCurMoney1').val('1');
			}else{
				$('#isUseCurMoney1').val('0');
			}
		}
		if($("#isUseCurMoney1").val() == 1){
			if(hidAccountReal > new Number(hidUseMoney + maxCurMoney)){
				msg = msg + "-账户余额不足,请充值！<br/>";
			}
		}else{
			if (hidAccountReal > hidUseMoney) {
				msg = msg + "-账户余额不足,请充值！<br/>";
			}
		}
		if (msg != "") {
			parent.layer.msg(msg, 2, 5);
			return false;
		}
		return true;
	}
	/**
	 * 验证开通数据
	 */
	function validateTenderData2() {
		var msg = "";
		var hidAccountReal = Number($("#accountReal").val());
		var hidUseMoney = Number($("#useMoney").val());
	/* 	var payPassword = $("#payPassword").val(); */
	/* 	var checkCode = $("#checkCode").val(); */
		var maxCurMoney = Number("${maxCurMoney}");
		if(${isExistCurAccount}){
			if($("#isUseCurMoney")[0].checked){
				$('#isUseCurMoney1').val('1');
			}else{
				$('#isUseCurMoney1').val('0');
			}
		}	
		if($("#isUseCurMoney1").val() == 1){
			if(hidAccountReal > new Number(hidUseMoney + maxCurMoney)){
				msg = msg + "-账户余额不足,请充值！<br/>";
			}
		}else{
			if (hidAccountReal > hidUseMoney) {
				msg = msg + "-账户余额不足,请充值！<br/>";
			}
		}
		/* if (payPassword == null || payPassword == "") {
			msg = msg + "-交易密码未填写！<br/>";
		} */
		/* if (checkCode == null || checkCode == "") {
			msg = msg + "-验证码未填写！<br/>";
		} */
		if (msg != "") {
			parent.layer.msg(msg, 2, 5);
			return false;
		}
		return true;
	}
	/**
	 * 购买
	 */
	function tender_borrow() {
		if (!validateTenderData2()) {
			return;
		}
		if(${isExistCurAccount}){
			if($("#isUseCurMoney")[0].checked){
				$('#isUseCurMoney1').val('1');
			}else{
				$('#isUseCurMoney1').val('0');
			}
		}
		var pop = $.layer({
		    shade: [0],
		    area: ['200px','auto'],
		    offset : ['250px',''],
		    dialog: {
		        msg: '确定要购买吗？',
		        btns: 2,
		        type: 4,
		        btn: ['确定','取消'],
		        yes: function(){
		        	$("#btnTenderBorrow").removeAttr("onclick");
					$("#subscribeTransferForm").ajaxSubmit({
						url : '${basePath }/zhitongche/subscribe.html',
						type : "POST",
						success : function(msg) {
							layer.close(pop);
							if (msg.code == "1") {
								parent.layer.msg("购买成功！",1, 1);
								window.parent.location.href = "${basePath}/zhitongche/zhuanrang/queryTransferById/${firstTransfer.id}.html";
							} else {
								if (msg.message != '') {
									parent.layer.msg(msg.message,2,5);
								}
								$("#btnTenderBorrow").attr("onclick","tender_borrow()");
							}
						},
						error : function() {
							$("#btnTenderBorrow").attr("onclick","tender_borrow()");
							parent.layer.msg("网络连接超时，请您稍后重试",2, 5);
							$(".layer-jion").hide();
							$(".cont-word").animate({top:"0px"});
						}
					});
		        }, no: function(){
		        	$("#btnTenderBorrow").attr("onclick","tender_borrow()");
		        }
		    }
		}); 
	}
</script>
</html>

