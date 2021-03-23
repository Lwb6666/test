<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/page/common/taglib.jsp"%>
<script type="text/javascript" src="${basePath}/js/formValid.js"></script>
<!---------- ------------------------设置交易密码-弹层---------------------------------------------------->
<form id="payPwdForm" name="payPwdForm"  method="">
	<div class="cont-word">
    	<div class="title"><h4>设置交易密码</h4><a href="javascript:void(0);" onclick="closeSetPayPwd();" class="icon icon-close fr"></a></div>
        <div class="form-info-layer">
            <div class="form-col2">
                <label for="" class="colleft02 form-lable">交易密码</label>
                <input type="password"  style="width:198px" class="colright form-inpyt-sm" placeholder="请输入交易密码"
                id="newPwd" name="newPwd" dataType="Limit" min="6" max="12" msg="密码不能为空，且长度在6~12之间" maxlength="12">
            </div>
            <div class="form-col2">
                <label for="" class="colleft02 form-lable">确认交易密码</label>
                <input type="password" style="width:198px" class="colright form-inpyt-sm" placeholder="确定交易密码"
                id="reNewPwd" name="reNewPwd" dataType="Repeat" to="newPwd" msg="两次密码输入不一致" maxlength="12">
            </div>
            <div class="form-col2">
               <div class="f14 gary2">
               <p><span class="orange2">*</span> 请确保交易密码和登录密码不同</p>
               <p><span class="orange2">*</span> 建议采用字母和数字组合，不少于6位不多于12位</p>
              </div>
            </div>
            <div class="form-col2">
            <button type="button" class="remove"  onclick="closeSetPayPwd();" >取消</button><button type="button" class="enter" id="submitBtn"  onclick="save();">确定</button>
            </div>
         </div>
    </div> 
</form>
<script type="text/javascript">

	/**
	 * 提交数据
	 */
	function save(){

		//验证
		$('#submitBtn').attr("disabled","disabled");
		if (Validator.Validate('payPwdForm',1)==false) {
			$('#submitBtn').removeAttr("disabled");
			return;
		}

		//提交表单
		layer.confirm('确定提交吗？', function(){
			$("#payPwdForm").ajaxSubmit({
				url : '${basePath}/account/safe/setPayPwd.html',
				type : 'post',
				dataType :"json",
				beforeSend:function(){
					_load = layer.load('处理中..');
				},
				success : function(data) {
					$('#submitBtn').removeAttr("disabled");
					layer.close(_load);
					if(data.code=='0'){
						layer.msg(data.message,2,5);
						loadimage();
					}else{
						layer.msg(data.message,2,1,function(){
							window.location= '${path}/AccountSafetyCentre/safetyIndex.html';
						});
					}
				},
				error : function(data) {
					layer.msg('网络连接异常，请稍后重试！');
					cancal();
				}
			});
		},'',function(){$('#submitBtn').removeAttr("disabled");});
	}

	/**
	 * 取消
	 */
	function cancal(){
		var index = parent.layer.getFrameIndex(window.name);
		parent.layer.close(index);
	}
</script>
