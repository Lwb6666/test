<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/page/common/taglib.jsp"%>
<script type="text/javascript" src="${basePath}/js/formValid.js"></script>
<!---------- ------------------------修改登录密码-弹层---------------------------------------------------->
<form id="loginPwdForm" name="loginPwdForm" action="">
	<div class="cont-word">
    	<div class="title"><h4>修改登录密码</h4><a href="javascript:void(0);" onclick="closeUpdateLoginPwd();" class="icon icon-close fr"></a></div>
        <div class="form-info-layer">
            <div class="form-col2">
                <label for="" class="colleft02 form-lable">当前登录密码</label>
                <input type="password"  style="width:198px" class="colright form-inpyt-sm" placeholder="请输入原登录密码"
                id="oldPwd" name="oldPwd"  dataType="Limit" min="1"  msg="当前密码不能为空">
            </div>
            <div class="form-col2">
                <label for="" class="colleft02 form-lable">新登录密码</label>
                <input type="password" style="width:198px" class="colright form-inpyt-sm" placeholder="请输入新登录密码"
                id="newPwd" name="newPwd"  dataType="Limit" min="6" max="20" msg="新密码不能为空，且长度在6~20之间" maxlength="20" >
            </div>
            <div class="form-col2">
                <label for="" class="colleft02 form-lable">确认新密码</label>
                <input type="password" style="width:198px" class="colright form-inpyt-sm" placeholder="确认新密码"
                id="reNewPwd" name="reNewPwd" dataType="Repeat" to="newPwd" msg="两次密码输入不一致" maxlength="20">
            </div>
            <div class="form-col2">
               <div class="f14 gary2">
               <p><span class="orange2">*</span> 请确保登录密码与交易密码不同</p>
               <p><span class="orange2">*</span> 必须为字母与数字的组合，不少于6位不多余20位</p>
              </div>
            </div>
            <div class="form-col2">
            <button type="button" class="remove" onclick="closeUpdateLoginPwd();">取消</button><button type="button" class="enter" id="loginPwdFormSubmitbtn"  onclick="loginPwdFormSubmit();">确定</button>
            </div>
         </div>
    </div> 
</form>
<script type="text/javascript">
	/**
	 * 表单提交
	 */
	function loginPwdFormSubmit(){
		//验证
		$('#loginPwdFormSubmitbtn').attr("disabled","disabled");
		if (Validator.Validate('loginPwdForm',1)==false) {
			$('#loginPwdFormSubmitbtn').removeAttr("disabled");
			return;
		}
		if (!checkPassWord()) {
			$('#loginPwdFormSubmitbtn').removeAttr("disabled");
			return;
		}
		//提交
		if(layer.confirm("确定提交吗？",function(){
					var _load = layer.load('处理中..');
					$("#loginPwdForm").ajaxSubmit({
						url : '${basePath}/account/safe/modifyLoginPwd.html',
						type : 'post',
						dataType :"json",
						success : function(result) {
							layer.close(_load);
							if (result.code == '0') {
								parent.layer.msg(result.message, 1, 5);
								$('#loginPwdFormSubmitbtn').removeAttr("disabled");
							}else{
								layer.alert("登录密码修改成功！" , 1, "温馨提示",function(){
	    							window.location.href="${basePath}/AccountSafetyCentre/safetyIndex.html";
	    						});
							}
						},
						error : function(result) {
							layer.close(_load);
							layer.msg('网络连接超时,请您稍后重试.', 1, 5);
						}
					});
				},'',function(){$('#loginPwdFormSubmitbtn').removeAttr("disabled");}));


	}



	/**
	 * 校验密码
	 */
	function checkPassWord(){
		var password1 = $('#newPwd').val();
		if(password1==null || password1==""){
			layer.msg('登录密码不能为空',1, 5);
			return false;
		}else if(password1.length<6){
			layer.msg('登录密码必须大于或等于6位', 1, 5);
			return false;
		}else if(password1.length>20){
			layer.msg('登录密码不能大于20位', 1, 5 );
			return false;
		}else{
			var regx = new RegExp("^([0-9]+[A-Za-z]+|[A-Za-z]+[0-9]+)[A-Za-z0-9]*$");
			if(!regx.test(password1)){
				layer.msg('登录密码必须为数字加字母', 1, 5 );
				return false;
			}
		}

		return true;

	}

	/**
	 * 取消
	 */
	function cancal(){
		var index = parent.layer.getFrameIndex(window.name);
		parent.layer.close(index);
	}
</script>