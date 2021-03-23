<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/page/common/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ include file="/WEB-INF/page/common/public.jsp"%>
<script type="text/javascript" src="${path }/js/formValid.js?${version}"></script>
</head>
<body>
<form id="fm" name="fm" method="post" >
	<%@ include file="/WEB-INF/page/common/header.jsp"%>
	<div id="Bmain">
		<div class="title">
			<span class="home"><a href="${path}/">顶玺金融</a></span><span><a href="${path }/myaccount/toIndex.html">我的账户</a></span><span><a href="#">账户管理</a></span><span><a href="${path }/bankinfo/toBankCard.html">银行卡信息</a></span>
		  </div>
		<%@ include file="/WEB-INF/page/account/myAccount_leftMenu.jsp"%>
		<div class="lb_waikuang border whitebg">
			<div class="sfmain">
				<div class="sf">
				
					<p class="safe_p">更换银行卡</p>
					 <div class="safebox550">
               <dl>
                     <dd><font color="#FF0004">*</font> 绑定或更换银行卡需与注册实名一致的<strong>储蓄卡</strong>，否则将提现失败 </dd>
                    <dd>
								<span><font color="#FF0004">*</font>真实姓名：</span>
								<input type="text" id="realName" name="realName" style="width: 200px" dataType="Require" msg="真实姓名不能为空" maxlength="20"/>
							</dd>
							<dd>
								<span><font color="#FF0004">*</font>证件号码：</span>
								<input type="text" id="idCard" name="idCard" style="width: 200px" dataType="Require" msg="证件号码不能为空" maxlength="20"/>
							</dd>
							<dd>
								<span><font color="#FF0004">*</font>原银行卡号：</span>
								<input type="text" id="oldBankcard" name="oldBankcard" style="width: 200px" dataType="Require" msg="原银行卡号不能为空" maxlength="20"/>
							</dd>
							<dd>
								<span><font color="#FF0004">*</font>新卡开户行：</span> 
								<select class="select" id="newBankCode" name="newBankCode" style="width:207px; margin-top: 0px;box-shadow: 1px 1px 5px #e7e7e7 inset;" dataType="Require" msg="新卡开户行不能为空"></select>
							</dd>
							<dd>
								<span><font color="#FF0004">*</font>新银行卡号：</span>
								<input type="text" id="newBankcard" name="newBankcard" style="width: 200px" dataType="Require" msg="新银行卡号不能为空" maxlength="20" />
							</dd>
							<dd>
								<span><font color="#FF0004">*</font>换卡原因：</span>
								<select class="select" style="width:207px" name="updateReason" id="updateReason">
									<option value="原卡丢失">原卡丢失</option>
									<option value="原卡损坏">原卡损坏</option>
									<option value="卡号错误">卡号错误</option>
									<option value="其他原因">其他原因</option>
								</select>
							</dd>
                     <dd class="position-re"><span><font color="#FF0004"></font>备注：</span><textarea rows="4" cols="26" name="remark"></textarea><!-- <div class="grid1 fl bd-line textcenter pink" style="line-height:25px; width:235px; position:absolute; right:-150px; top:0; ">当您选择其它原因换卡，您的账户资产需低于100元，若高于100元，请适当处理您的资产总额，再进行更换卡</div> --></dd>
                     <dd>
								<span><font color="#FF0004">*</font>手机号：</span>
								<input type="hidden" id="phone" name="phone" style="width: 200px" dataType="Require" msg="手机号不能为空" maxlength="15" value="${mobileAppro.mobileNum }"/>
								<input type="hidden" id="bankPhone" name="bankPhone" style="width: 200px" dataType="Require" msg="手机号不能为空" maxlength="15" value="${mobileAppro.mobileNum }"/>
								${mobileAppro.securitymobileNum }(请与您银行卡预留手机保持一致)
							</dd>
							<dd>
								<span><font color="#FF0004">*</font>手机验证码：</span> 
								<input type="text" id="activeCode" name="activeCode" dataType="Require" msg="手机验证码不能为空" maxlength="10"/> 
								<input class="sbtn" type="button" name="sendbtn" id="sendbtn" value="发送验证码" style="cursor: pointer;" /> 
								<input class="sbtn" id="clock" type="button" name="button" value="180秒后重发" disabled="disabled" />
							</dd>
                 </dl>
                <div class="gg_btn">
							<input type="button" value="下一步" id="btnSaveBank" name="btnSaveBank" onclick="saveBank();" style="cursor: pointer;" />
						</div>             
          </div>

          </div>
          <div class="tip">
             <div class="safebox550">
						<h3>温馨提示：</h3>
						<p>1.更换卡的次数不能超过10次</p>
						<p>2.更换卡需等待客服审核，1-2工作日内处理或联系客服热线：400-000-0000</p>
						<p>3.认证资料类型包括身份证，挂失证明等其他类型.上传最多不超过10份</p>
					</div>
				</div>
			</div>
		</div>
	</div>
	<%@ include file="/WEB-INF/page/common/footer.jsp"%>
</form>
</body>
<script type="text/javascript">
	$(function() {
		$('#sendbtn').bind('click', forsendmsg);
	 	populateBank();
	});
	
	//银行列表
	function populateBank() {
		var _load = layer.load('处理中..');
		var bank = $("#newBankCode");
		bank.empty(); 
		bank.append($("<option>").val('').text(''));
		$.ajax({
			url : '${basePath}/bankinfo/queryBankList.html',
			data : {'district' : ''},
			type : 'post',
			dataType :"json",
			success : function(result) {
				layer.close(_load);
				$.each(result, function(index, item) {
					bank.append($("<option>").val(item.bankCode).text(item.bankName));
				});
				//populateBranch();
			},
			error : function(result) {
				layer.close(_load);
				layer.msg('网络连接超时,请您稍后重试.', 1, 5);
				//populateBranch();
		    }
		});
	}
	
	//下一步：验证手机短信
	function checkMobile() {
		var result = false;
		var activeCode = $("#activeCode").val();
		if (activeCode == '') {
			layer.msg("验证码不能为空");
		} else {
			$.ajax({
				url : '${basePath}/account/topup/checkMobileMsg.html',
				data : {
					activeCode : activeCode
				},
				type : 'post',
				dataType : 'json',
				async : false,
				success : function(data) {
					if (data.code == '1') {
						result = true;
						// layer.msg(data.message);
					} else if (data.code == '0') {
						layer.alert(data.message, 1);
					}
				},
				error : function(data) {
					layer.msg('网络连接异常，请刷新页面或稍后重试！', 1, 5);
				}
			});
		}
		return result;
	}
	
	//发送短信
	function forsendmsg() {
	 	var phone = $("#phone").val();
		if(phone == ''){
			layer.msg("-手机号不能为空！\n");
			return;
		}
		$.post("${basePath}/bankinfo/sendMsg.html", {'phone' : phone}, function(result) {
			if (result.code == '0') {
				layer.msg(result.message);
			} else {
				run();
			}
		});
	}
	var runtime = 0;
	function run() {
		runtime = 180;
		runclock();
	}
	function runclock() {
		$('#sendbtn').attr("disabled", "disabled");
		setTimeout(function() {
			runtime = runtime - 1;
			$('#clock').val(runtime + "秒后重发");
			if (runtime > 0) {
				runclock();
			} else {
				$('#clock').val('180秒后重发');
				$('#sendbtn').removeAttr("disabled");
				runtime = 180;
			}
		}, 1000);
	}

/**
 * 下一步
 */
function saveBank() {
	if (Validator.Validate('fm',4)==false) return;

	$("#btnSaveBank").removeAttr("onclick");
	var _load = layer.load('处理中..');
	$("#fm").ajaxSubmit({
		url : '${path}/bankinfo/subChange.html',
		type : 'post',
		dataType :"json",
		success : function(result) {
			layer.close(_load);
			//window.location.href='${path}/bankinfo/initUploadPics.html';
			if (result.code == '0') {
				layer.alert(result.message);
				$('#btnSaveBank').bind('click', saveBank);
			}else{
				window.location.href='${path}'+result.message+'.html';
			}
		},error : function() {
			layer.msg('网络连接超时,请您稍后重试.', 1, 5);
	    }
	});
}
</script>
</html>