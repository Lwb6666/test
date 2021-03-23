<%@ page language="java" contentType="text/html; charset=utf-8"
		 pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/page/common/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<%@ include file="/WEB-INF/page/common/public4.jsp"%>
<title>账户管理_安全中心_顶玺金融</title>
</head>

<body>
	<!--头部开始-->
	<%@ include file="/WEB-INF/page/common/header.jsp"%>
	<div class="clear"></div>
	<!--头部结束-->
	<div id="myaccount">
	<%@ include file="/WEB-INF/page/common/topmenu.jsp"%>

		<div id="account-all">
			<div class="tz-detail mt20">
			  	<%-- <c:if test="${!isCustody}">
				  <img src="${basePath}/images/myaccount/zs-banner.png"/>
				</c:if> --%>
			</div>
			<div class="userbar mt20">
				<ul class="ul-userbar1">
					<li class="userpic">
						<a id="tip-userpic" href="javascript:void(0)" style="cursor: default">
						<c:if test="${empty member.headimg}">
								<img src="${basePath}/images/main/head.png"/>
							</c:if>
							<c:if test="${not empty member.headimg}">
								<img src="${basePath}${member.headimg}"/>
							</c:if>
							</a>
					</li>
				</ul>
				<ul class="ul-userbar2">
					<li class="userinfo mt10">
						<p><span class="gray3 f18">${member.username}</span></p>
						<p class="f16 mt20">
							<span>安全等级:</span>
							<span class="orange mal10">${safeLevel}</span>
							<span style=" margin-left:100px;">上次登录时间:</span>
							<span class="orange mal10"><fmt:formatDate pattern="yyyy-MM-dd" value="${onLineVo.logintime}" /></span>
							<span class="mal20">注册时间:</span>
							<span class="orange mal10">${logintime}</span></p>
					</li>
				</ul>
			</div>
		</div>

		<!--Start wraper-->
		<div class="wraper mt20">
			<div class="prduct-menu  background">
				<div class="menucont">
					<div class="tbl-cont">
						<table width="100%" border="0" cellspacing="0" cellpadding="0"  class="Safety-table mt20">
							<tr>
								<td>登录密码</td>
								<td class="gary2">用户登录时使用</td>
							    <c:if test="${member.logpassword != null}">
									<td></td>
									<td align="center" class="orange2"><i class="iconfont f18 mr10">&#xe610;</i>已设置</td>
									<td align="right">
										<a href="javascript:void(0);" onclick="findLoginPwd();" data-reveal-id="findLoginPwd" data-animation="fade">找回</a>
	                                    <a href="javascript:void(0);" onclick="updateLoginPwd();" data-reveal-id="updateLoginPwd" data-animation="fade">修改</a>
								   </td>
								</c:if>
								<c:if test="${member.logpassword== null}">
									<td></td>
									<td align="center" class="orange2">未设置</td>
									<td align="right">
											 <a class="sbtn">您是怎么进来的？</a></span>
								   </td>
								</c:if>
							</tr>

							<tr class="bgblue">
								<td rowspan="4" style="border-right:1px solid #e6e6e6;">绑定银行卡</td>
								<td class="gary2">真实姓名 </td>
								<td>
									<c:if test="${realNameApproVo.isPassed==1}">
										${realNameApproVo.securityRealName}
									</c:if>
								</td>
								<td rowspan="4" align="center" class="orange2"  style="border-right:1px solid #e6e6e6;border-left:1px solid #e6e6e6;">
								     <c:choose>
								       <c:when test="${realNameApproVo.isPassed==1 and mobileAppro.passed ==1 and currentBankCardVo != null and currentBankCardVo.status !=-1}">
								           <em class="iconfont f18 mr10"></em>已设置
								       </c:when>
								       <c:otherwise>
								                                     未设置
								       </c:otherwise>
								    </c:choose>
								</td>
								<td rowspan="2" align="right">
									<c:if test="${realNameApproVo.isPassed==null or realNameApproVo.isPassed!=1}">
									    <c:if test="${realNameApproVo.isPassed==null}">
										  <a href="javascript:void(0);"   onclick="toRealNameAppro();">新增</a>
									    </c:if>
									    <c:if test="${realNameApproVo.isPassed!=null and realNameApproVo.isPassed==-1}">
										  <a href="javascript:void(0);"   onclick="toRealNameAppro();">新增</a>
									    </c:if>
									    <c:if test="${realNameApproVo.isPassed!=null and realNameApproVo.isPassed==0}">
										  <a href="javascript:void(0);" onclick="setSdRealNameAppro();" data-reveal-id="sdRealNameAppro" data-animation="fade">审核中</a>
									    </c:if>
									</c:if>
									<c:if test="${realNameApproVo.isPassed!=null and realNameApproVo.isPassed ==1}">
									    已设置
									</c:if>
								</td>
							</tr>
							<tr class="bgblue">
								<td class="gary2">身份证号 </td>
								<td>
									<c:if test="${realNameApproVo.isPassed==1}">
										${realNameApproVo.securityIdCardNo }
									</c:if>
								</td>
							</tr>
							<tr class="bgblue">
								<td class="gary2">手机号码 </td>
								<td>
									<c:if test="${mobileAppro.passed == 1}">
										${mobileAppro.securitymobileNum }
									</c:if>
								</td>
								<td align="right">
								    <c:if test="${mobileAppro.passed==null or mobileAppro.passed!=1}">
									    	<a href="javascript:void(0);" data-reveal-id="mobileAppro" data-animation="fade"  onclick="toMobileAppro();">新增</a>
									</c:if>
									<c:if test="${mobileAppro.passed==1}">
									    	<a href="javascript:void(0);" data-reveal-id="mobileAppro" data-animation="fade"  onclick="toMobileAppro();">修改</a>
									</c:if>
								</td>
							</tr>
							<tr class="bgblue">
								<td class="gary2">银行卡号</td>
								<td>
									<c:if test="${currentBankCardVo != null or currentBankCardVo.status != -1 }">
										${currentBankCardVo.securityCardNum }
									</c:if>
								</td>
								<td align="right">
					              <c:if test="${currentBankCardVo == null or currentBankCardVo.status == -1 }">
								  	  <a href="javascript:void(0);" onclick="toAddBankCard();">新增</a>
								  </c:if>
								   <c:if test="${currentBankCardVo != null and currentBankCardVo.status == 0 }">
                                       <a href="javascript:void(0);" onclick="toUpDateBankCard();">解绑</a>								 
                                   </c:if>
                                   <c:if test="${currentBankCardVo != null and currentBankCardVo.status == 1 }">审核中，如需修改资料请点击&nbsp;<a   data-reveal-id="bindBank3" data-animation="fade" onclick="editInitUploadPics();"  >修改</a></c:if>
                                  <c:if test="${currentBankCardVo != null and currentBankCardVo.status == 2 }"> 审核未通过，请7天后再申请或联系客服</c:if>
                                   <c:if test="${currentBankCardVo != null and currentBankCardVo.status == 3 }"> 审核中  </c:if>
								</td>
							</tr>
							<tr>
								<td>交易密码</td>
								<td class="gary2">交易密码只用于投标、提现</td>
								<td>
									<c:if test="${member.paypassword != null}">
										
									</c:if>
								</td>
								<c:if test="${member.paypassword != null}">
									<td align="center" class="orange2">
											<i class="iconfont f18 mr10">&#xe610;</i>已设置
									</td>
								</c:if>
								<c:if test="${member.paypassword == null}">
									<td align="center" class="gary2">
										未设置
									</td>
								</c:if>
								<td align="right">
									<c:if test="${member.paypassword != null}">
								        <a href="javascript:void(0);" onclick="findPayPwd();" data-reveal-id="findPayPwd" data-animation="fade">找回</a>
	                                    <a href="javascript:void(0);" onclick="updatePayPwd();" data-reveal-id="updatePayPwd" data-animation="fade">修改</a>
									</c:if>
									<c:if test="${member.paypassword == null}">
										<a href="javascript:void(0);" onclick="setPayPwd();" data-reveal-id="setPayPwd" data-animation="fade">新增</a>
									</c:if>
								</td>
							</tr>
							<tr>
							<td>邮箱验证</td>
							<td class="gary2">邮箱可以收到借款协议和找回密码 </td>
							<td>
								<c:if test="${emailApproVo.checked==1}">
									${emailApproVo.securityemail }
								</c:if>
							</td>
							<c:if test="${emailApproVo.checked==1}">
								<td align="center" class="orange2">
									<i class="iconfont f18 mr10">&#xe610;</i>已设置
								</td>
							</c:if>
							<c:if test="${emailApproVo.checked==null or emailApproVo.checked==0}">
								<td align="center" class="gary2">
										未设置
								</td>
							</c:if>
							<td align="right">
								<c:if test="${emailApproVo.checked==null or emailApproVo.checked==0}">
										<a href="javascript:void(0);" onclick="setEmailAppro();" data-reveal-id="emailAppro" data-animation="fade">新增</a>
								</c:if>
							</td>
						</tr>
							<tr>
								<td>存管账户</td>
								<td class="gary2">开通存管账户更安全 </td>
								<td></td>
                                <c:if test="${isCustody == true}">
                                    <td align="center" class="orange2">
                                        <i class="iconfont f18 mr10">&#xe610;</i>已开通</td>
                                        <td align="right"><a id="btnLogin" href="https://e.czbank.com/APPINSPECT/zxLogon.jsp" target="_blank">立即登录</a></td>
                                </c:if>
                                <c:if test="${isCustody == false}">
                                    <td align="center" class="gary2">未开通</td>
                                    <td align="right"><a id="btnOpenAccount">立即开通</a></td>
                                </c:if>
							</tr>
						</table>
					</div>
				</div>
			</div>
		</div>
	</div>
	  <!-- 弹层start -->
	  <!-- 实名认证 -->
	   <div class="reveal-modal"  id="realNameAppro"></div>
	     <!-- 绑定银行卡-->
	   <div class="reveal-modal"  id="bindBank"></div>
	   <!-- 手机绑定及更换-->
	   <div class="reveal-modal"  id="mobileAppro"></div>
	    <!-- 找回登录密码-->
	   <div class="reveal-modal"  id="findLoginPwd"></div>
	   <!-- 修改登录密码-->
	   <div class="reveal-modal"  id="updateLoginPwd"></div>
	   <!-- 设置交易密码-->
	   <div class="reveal-modal"  id="setPayPwd"></div>
	    <!-- 找回交易密码-->
	   <div class="reveal-modal"  id="findPayPwd"></div>
	   <!-- 修改交易密码-->
	   <div class="reveal-modal"  id="updatePayPwd"></div>
	    <!-- 设置邮箱-->
	   <div class="reveal-modal"  id="emailAppro"></div>
	      <!-- 手动实名认证-->
	   <div class="reveal-modal"  id="sdRealNameAppro"></div>
	      <!-- 解绑银行卡第一步-->
	   <div class="reveal-modal"  id="bindBank1"></div>
	       <!-- 解绑银行卡第二步-->
	   <div class="reveal-modal"  id="bindBank2"></div>
	      <!-- 修改上传的图片-->
	    <div class="reveal-modal"  id="bindBank3"></div>
        <%@ include file="/WEB-INF/page/account/safe/openAccountLayer.jsp"%>
        <!-- 弹层end -->
         <%@ include file="/WEB-INF/page/common/footer.jsp"%>
</body>
<script type="text/javascript">
	$(function(){
		if('${result}'=='恭喜您通过了邮箱认证！'){
			layer.alert("${email}邮箱验证成功" , 1, "邮箱验证",function(){
				window.location.href="${basePath}/AccountSafetyCentre/safetyIndex.html";
			});
		}
		if('${result}'!='' && '${result}'!='success'){
			layer.alert("${result}" , 1, "邮箱验证",function(){
				window.location.href="${basePath}/AccountSafetyCentre/safetyIndex.html";
			});
		}
		$("#aqzx").attr("class","active"); //添加样式 
	})
	/* 添加银行卡 */
	function closeWin(){ 
		$('#bindBank').trigger('reveal:close'); 
		} 
	
	function toAddBankCard() {
	  $.post("${basePath}/bankinfo/vaild.html", {}, function(result) {
			if (result.code == '-1') {
				layer.msg('请先设置交易密码！',1,5,function(){});
			} else if (result.code == '-2') {
				layer.msg('请先设置手机认证！',1,5,function(){});
			} else if (result.code == '-3') {
				layer.msg('请先设置实名认证！',1,5,function(){});
			} else if (result.code == '1') {
				$('#bindBank').reveal({
                    animation: 'fade',                  
                    animationspeed: 300,                      
                    closeonbackgroundclick: false,              
                    dismissmodalclass: 'close-reveal-modal'    
                });
				$.ajax({
				    url: '${basePath}/bankinfo/addBankCard.html',
					data :{
					} ,
					type : 'post',
					dataType : 'html',
					success : function(data){
						$("#bindBank").html(data);
					},
					error : function(data) {
						layer.msg("网络连接异常，请刷新页面或稍后重试！", 2, 5);
				    }
				});
			} else {
				layer.msg("网络连接异常，请刷新页面或稍后重试！", 1, 5);
			}
		});  
	}
	//实名认证
	function closeRealNameAppro(){ 
		$('#realNameAppro').trigger('reveal:close'); 
		} 
	function toRealNameAppro(){
		$.ajax({
			url : '${basePath}/appro/findAppro.html',
			data : {},
			type : 'post',
			success : function(data) {
				if (data == null
						|| (data.emailChecked != 1 && data.mobilePassed != 1)) {
					layer.msg("请先进行邮箱或手机认证！", 2, 5);
				} else {
					$('#realNameAppro').reveal({
	                    animation: 'fade',                  
	                    animationspeed: 300,                      
	                    closeonbackgroundclick: false,              
	                    dismissmodalclass: 'close-reveal-modal'    
	                });
					$.ajax({
					    url: '${basePath}/account/approve/realname/toRealNameAppro.html',
						data :{
						} ,
						type : 'post',
						dataType : 'html',
						success : function(data){
							 $("#realNameAppro").html(data); 
						},
						error : function(data) {
							layer.msg("网络连接异常，请刷新页面或稍后重试！", 2, 5);
					    }
					});
				}
			},
			error : function(data) {
				layer.msg("网络连接异常，请刷新页面或稍后重试！", 2, 5);
			}
		});
	}
	//手机认证
	function closeMobileAppro(){ 
		$('#mobileAppro').trigger('reveal:close'); 
		} 
	function toMobileAppro(){
		$.ajax({
			url : '${basePath}/account/approve/mobile/mobileforinsert.html',
			data :{
			} ,
			type : 'post',
			dataType : 'html',
			success : function(data){
				$("#mobileAppro").html(data);
			},
			error : function(data) {
				layer.msg("网络连接异常，请刷新页面或稍后重试！", 2, 5);
		    }
		});
	}
	//找回登录密码
	function closeFindLoginPwd(){ 
		$('#findLoginPwd').trigger('reveal:close'); 
		} 
	function findLoginPwd(){
		$.ajax({
			url : '${basePath}/AccountSafetyCentre/safetyCentre/enterFindLoginPwdDiv.html',
			data :{
			} ,
			type : 'post',
			dataType : 'html',
			success : function(data){
				$("#findLoginPwd").html(data);
			},
			error : function(data) {
				layer.msg("网络连接异常，请刷新页面或稍后重试！", 2, 5);
		    }
		});
	}
	//修改登录密码
	function closeUpdateLoginPwd(){ 
		$('#updateLoginPwd').trigger('reveal:close'); 
		} 
	function updateLoginPwd(){
		$.ajax({
			url : '${basePath}/AccountSafetyCentre/safetyCentre/enterResetLoginPwd.html',
			data :{
			} ,
			type : 'post',
			dataType : 'html',
			success : function(data){
				$("#updateLoginPwd").html(data);
			},
			error : function(data) {
				layer.msg("网络连接异常，请刷新页面或稍后重试！", 2, 5);
		    }
		});
	}
	//设置交易密码
	function closeSetPayPwd(){ 
		$('#setPayPwd').trigger('reveal:close'); 
		} 
	function setPayPwd(){
		$.ajax({
			url : '${basePath}/account/safe/toSetPayPwd.html',
			data :{
			} ,
			type : 'post',
			dataType : 'html',
			success : function(data){
				$("#setPayPwd").html(data);
			},
			error : function(data) {
				layer.msg("网络连接异常，请刷新页面或稍后重试！", 2, 5);
		    }
		});
	}
	//找回交易密码
	function closeFindPayPwd(){ 
		$('#findPayPwd').trigger('reveal:close'); 
		} 
	function findPayPwd(){
		$.ajax({
			url : '${basePath}/AccountSafetyCentre/safetyCentre/enterFindTransactionPwd.html',
			data :{
			} ,
			type : 'post',
			dataType : 'html',
			success : function(data){
				$("#findPayPwd").html(data);
			},
			error : function(data) {
				layer.msg("网络连接异常，请刷新页面或稍后重试！", 2, 5);
		    }
		});
	}
	
	//修改交易密码
	function closeUpdatePayPwd(){ 
		$('#updatePayPwd').trigger('reveal:close'); 
		} 
	function updatePayPwd(){
		$.ajax({
			url : '${basePath}/AccountSafetyCentre/safetyCentre/enterResetTransactionPwd.html',
			data :{
			} ,
			type : 'post',
			dataType : 'html',
			success : function(data){
				$("#updatePayPwd").html(data);
			},
			error : function(data) {
				layer.msg("网络连接异常，请刷新页面或稍后重试！", 2, 5);
		    }
		});
	}
	
	//邮箱认证
	function closeEmailAppro(){ 
		$('#emailAppro').trigger('reveal:close'); 
		} 
	function setEmailAppro(){
		$.ajax({
			url : '${basePath}/account/approve/emailDiv.html',
			data :{
			} ,
			type : 'post',
			dataType : 'html',
			success : function(data){
				$("#emailAppro").html(data);
			},
			error : function(data) {
				layer.msg("网络连接异常，请刷新页面或稍后重试！", 2, 5);
		    }
		});
	}
	//手动实名认证
	function closeSdRealNameAppro(){ 
		$('#sdRealNameAppro').trigger('reveal:close'); 
		} 
	function setSdRealNameAppro(){
		closeRealNameAppro();
		$.ajax({
			url : '${basePath}/account/approve/realname/display.html',
			data :{
			} ,
			type : 'post',
			dataType : 'html',
			success : function(data){
				$("#sdRealNameAppro").html(data);
			},
			error : function(data) {
				layer.msg("网络连接异常，请刷新页面或稍后重试！", 2, 5);
		    }
		});
	}
	
	//解绑银行卡
	function closeUpDateBankCard(){ 
		$('#bindBank1').trigger('reveal:close'); 
		} 
	function toUpDateBankCard(){
		$.ajax({
			url : "${basePath}/bankinfo/initChangeCard.html",
			data : {},
			type : 'post',
			dataType : 'json',
			success : function(data) {
				if (data.code == '0') {
					layer.msg(data.message,1,5,function(){});
				} else {
					$('#bindBank1').reveal({
	                    animation: 'fade',                  
	                    animationspeed: 300,                      
	                    closeonbackgroundclick: false,              
	                    dismissmodalclass: 'close-reveal-modal'    
	                });
					$.ajax({
						url : '${basePath}/'+data.message,
						data :{
						} ,
						type : 'post',
						dataType : 'html',
						success : function(data){
							$("#bindBank1").html(data);
						},
						error : function(data) {
							layer.msg("网络连接异常，请刷新页面或稍后重试！", 2, 5);
					    }
					});
				}
			},
			error : function(data) {
				layer.msg("网络连接异常，请刷新页面或稍后重试！");
			}
		});
	}
	
	function closeEditInitUploadPics(){ 
		$('#bindBank3').trigger('reveal:close'); 
		} 
	function editInitUploadPics(){
		$.ajax({
			url : "${path}/bankinfo/editInitUploadPics.html?isEdit=0",
			data :{
			} ,
			type : 'post',
			dataType : 'html',
			success : function(data){
				$("#bindBank3").html(data);
			},
			error : function(data) {
				layer.msg("网络连接异常，请刷新页面或稍后重试！", 2, 5);
		    }
		});
	}
</script>
</html>