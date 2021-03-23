<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%> 
<%@ include file="/WEB-INF/page/common/taglib.jsp"%> 
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ include file="/WEB-INF/page/common/public.jsp"%>
<title>账户管理-安全中心</title>
</head>

<body>

<%@ include file="/WEB-INF/page/common/header.jsp"%>
	<div id="Bmain">
		<div class="title">
			<span class="home"><a href="${path}/">顶玺金融</a></span><span><a href="${path }/myaccount/toIndex.html">我的账户</a></span><span><a
				href="#">账户管理</a></span><span><a
				href="${path }/bankinfo/toBankCard.html">银行卡信息</a></span>
		</div>
		<!--<div id="menu_centert">-->
		<%@ include file="/WEB-INF/page/account/myAccount_leftMenu.jsp"%>
		<div class="lb_waikuang border whitebg p-r">
		
			<input type="hidden" id="c_token" name="c_token" value="${c_token}">  
			
			<div class="safetoptit">银行卡信息
			    <div class="bott" style="right:10px;top:42px;font-size: 13px">绑定或更换银行卡需与注册实名一致的<strong>储蓄卡</strong>，否则将提现失败</div>
			&nbsp;&nbsp;
            <span id="btnBankCardLock"></span>
			&nbsp;&nbsp;
			<span id="mes"></span>
			&nbsp;&nbsp;
			<span id="mes2"></span>
			&nbsp;&nbsp;
			<span id="mes3"></span>
			</div>
			<div class="bank">
				<div class="bankcard">
					<ul>
						<c:if test="${bc!=null}">    
						<li>
							<div class="bankblock pdtb12">
								<span><img src="${path }/images/bankicon/${bc.bankCode}.png" width="21" height="22"/>&nbsp;</span>
								<span class="f16"><strong>${bc.bankName}</strong><br/>
								<em class="pdl10 f12">${bc.securityCardNum}</em></span>
							</div>
						 	<p><span >
						 		<c:if test="${bc.status==0 }"><a href="javascript:toChangeCard();">点击更换</a></c:if>
						 		<c:if test="${bc.status==1 }">审核中，如需修改资料请点击&nbsp;<a  style="color: blue"   href="${path}/bankinfo/editInitUploadPics.html?isEdit=0" >修改</a></c:if>
						 		<c:if test="${bc.status==2 }">审核不通过</c:if>
						 		<c:if test="${bc.status==3 }">绑定银行卡审核中</c:if>
						 	</span></p>
						</li>
						</c:if>
						<c:if test="${bc==null}">      
						<li id="nbcard">
							<img src="${basePath }/images/wht_03.png" height="70" onclick="toAddBankCard();" style="cursor: pointer;" />
							<p><span><a href="javascript:toAddBankCard();">新增银行卡</a></span></p>
						</li>
						</c:if>
					</ul>
				</div>
				<c:if test="${bc.status==1 }">
		 			<div class="tip pink">
						<h3>温馨提示：</h3>
						<p>您的银行卡正在审核，请耐心等待！客服会在两个工作日内处理或联系。客服热线400-000-0000</p>
					</div>
				</c:if>
				<c:if test="${bc.status==2 }">
		 			<div class="tip pink">
						<h3>温馨提示：</h3>
						<p>您的银行卡未通过审核，请7天后重新申请或联系客服热线400-000-0000</p>
					</div>
				</c:if>
			</div>
			
		</div>
	</div>

	<%@ include file="/WEB-INF/page/common/footer.jsp" %>
</body>
<script type="text/javascript">
$(function(){
	var errorCode = '${errorCode}';
	if(errorCode!=''){

		if(errorCode=="-1"){
			layer.msg('请行进行实名认证！',1,5,function(){
				window.location.href="${path}/account/approve/realname/toRealNameAppro.html";
			});
		}else if(errorCode=="-3"){
			layer.msg('请先进行手机认证！',1,5,function(){
				window.location.href="${path }/account/approve/mobile/mobileforinsert.html";
			});
		}else if(errorCode=="-4"){
			layer.msg('请先设置交易密码！',1,5,function(){
				window.location.href="${path}/account/safe/toSetPayPwd.html";
			});
		}
		
	}
	
	//银行卡数量为0时，隐藏银行卡锁定按钮；
	var cardNum = '${cardNum}';
	if(cardNum==0){
		$("#btnBankCardLock").css("display","none"); 
	}
	
	//银行卡锁定时的页面设置；
	var blType = '${bankinfoLog.type }';
	var blRemark = '${bankinfoLog.remark }';
	var blAddTime = '${blAddTime }';
	
	//用户锁定| 系统锁定；
	if(blType !=""){
		if(blType==0){
			
			$("#btnBankCardLock").css("display","none"); 
// 			$("#nbcard").css("display","none"); 
			
// 			$("#mes").text(blRemark);
// 			$("#mes").css("font-size","70%");
// 			$("#mes2").text("锁定时间："+blAddTime);
// 			$("#mes2").css("font-size","70%");
// 			$("#mes3").text("温馨提示：如有问题，请联系客服");
// 			$("#mes3").css("font-size","70%");
			
		}
	}

});

function toAddBankCard(){
	$.post("${basePath}/bankinfo/isPhoneValidated.html", {
	}, function(data) {
		if(data == 'no'){
			layer.alert('您还没有手机认证！');
		}else if(data == 'yes'){
					$.layer({
						type : 2,
						fix : false,
						shade : [0.6, '#E3E3E3' , true],
						shadeClose : true,
						border : [10 , 0.7 , '#272822', true],
						title : ['',false],
						offset : ['50px',''],
						area : ['1000px','600px'],
						iframe : {src : '${basePath}/bankinfo/addBankCard.html'},
						close : function(index){
							layer.close(index);
							//window.open("${path}/page/account/myInvest.jsp","_self");
						}
					});
		}
	});
}

// function toChangeBankCard(){
// 	$.layer({
// 		type : 2,
// 		fix : false,
// 		shade : [0.6, '#E3E3E3' , true],
// 		shadeClose : true,
// 		border : [10 , 0.7 , '#272822', true],
// 		title : ['',false],
// 		offset : ['50px',''],
// 		area : ['1000px','600px'],
// 		iframe : {src : '${basePath }/bankinfo/initChangeCard.html'},
// 		close : function(index){
// 			layer.close(index);
// 			//window.open("${path}/page/account/myInvest.jsp","_self");
// 		}
// 	});
// }


function bankCardLock() {
	var c_token = $("#c_token").val();
	
	layer.confirm("确定锁定新增银行卡功能？", function() {
		
		$("#btnBankCardLock").removeAttr("onclick");

		$.ajax({
			url : "${basePath}/bankinfo/lockBankCard.html",
			data : {'c_token':c_token},
			type : 'post',
			success : function(data) {
				if (data.code=="1") {
// 					$("#nbcard").css("display","none"); 
					$("#btnBankCardLock").css("display","none"); 
					
					$("#mes").text("新增银行卡功能已手动锁定");
					$("#mes").css("font-size","70%");

					layer.msg(data.message, 1, 1);
					
				} else {
					layer.msg(data.message, 1, 5);
					$("#btnBankCardLock").attr("onclick","bankCardLock()");
				}
			},
			error : function(data) {
				$("#btnBankCardLock").attr("onclick","bankCardLock()");
				layer.msg('网络连接异常，请刷新页面或稍后重试！', 1, 5);
			}
		});

	});
}
/**
 * 删除银行卡  
 */
function deleteCard(obj,id) {
	var _load = layer.load('处理中..');
	layer.confirm("确定要删除此银行卡吗？", function() {
		$(obj).removeAttr("onclick");
		$.ajax({
			url : "${basePath}/bankinfo/removeBankCard/"+id+".html",
			data : {},
			type : 'post',
			dataType : 'json',
			success : function(data) {
				$(obj).attr("onclick","deleteCard(this,"+id+")");
				layer.close(_load);
				if (data.code == '0') {
					layer.msg(data.message, 1, 5);
					return;
				} else {
					layer.msg('删除银行卡成功', 1, 1, function() {
						window.location.href = window.location.href;
					});
				}
			},
			error : function(data) {
				$(obj).attr("onclick","deleteCard(this,"+id+")");
				layer.close(_load);
				layer.msg("网络连接异常，请刷新页面或稍后重试！");
			}
		});
	});
}

function toChangeCard(){
	$.ajax({
		url : "${basePath}/bankinfo/initChangeCard.html",
		data : {},
		type : 'post',
		dataType : 'json',
		success : function(data) {
			if (data.code == '0') {
				layer.alert(data.message);
				return;
			} else {
				window.location.href = '${basePath}/'+data.message;
			}
		},
		error : function(data) {
			layer.msg("网络连接异常，请刷新页面或稍后重试！");
		}
	});
}

</script>
</html>