<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/page/common/taglib.jsp"%>
<script type="text/javascript" src="${basePath}/js/formValid.js"></script>
<script type="text/javascript" src="${basePath }/js/formValidatorRegex.js"></script>
<!---------- ------------------------邮箱认证-弹层---------------------------------------------------->
<form id="mailform" name="mailform" action="">
	<div class="cont-word">
    	<div class="title"><h4>绑定邮箱</h4><a href="javascript:void(0);" onclick="closeEmailAppro();"  class="icon icon-close fr"></a></div>
        <div class="form-info-layer">
            <div class="form-col2">
                <label for="" class="colleft form-lable">邮箱地址</label>
                <input type="text"  style="width:198px" name="email" id="realemail" class="colright form-inpyt-sm" placeholder="请输入邮箱地址">
            </div>
            <div class="form-col2">
               <div class="f14 gary2">
               <p><span class="orange2">*</span> 请至邮箱查收邮件，点击链接验证邮箱</p>
              </div>
            </div>
            <div class="form-col2">
            <button type="button" class="remove" onclick="closeEmailAppro();">取消</button><button type="button" class="enter" name="activatebtn" id="activatebtn" onclick="save();" >确定</button>
            </div>
         </div>
    </div> 
</form>
<script type="text/javascript">
	/**
	 * 验证邮箱
	 */
	function verify(){
		var flag = true;
		//邮箱
		var patten_email = regexEnum.email;
		var email = $.trim($('#realemail').val());
		$('#realemail').val(email);
		if(email==null || email==""){
			layer.alert("请填写电子邮件。");
			return false;
		}else{
			if(!patten_email.test(email)){
				layer.alert("请输入有效电子邮件。");
				return false;
			}
		}
		//验证邮箱
		$.ajax({
			url : '${basePath}/member/checkMemberRepeat.html',
			data : {email:email},
			type : 'post',
			async: false,
			dataType : 'text',
			success : function(data){
				if(data != null && data != 'success'){
					layer.alert('电子邮箱已经存在');
					flag=false;
				}
			},
			error : function(data) {
				flag=false;
				layer.msg('网络连接异常，请刷新页面或稍后重试！', 1, 5);
			}
		});
		return flag;
	}

	/**
	 * 提交表单
	 */
	function save(){
		//验证表单
		if(verify()){
			//提交表单
			layer.confirm('确定提交吗？', function(){
				$("#mailform").ajaxSubmit({
					url : '${basePath}/account/approve/emailVerify.html',
					type: "POST",
					beforeSend:function(){
						_load = layer.load('处理中..');
					},
					success : function(data) {
						$("#submitBtn").attr("onclick","save();");
						layer.close(_load);
						if (data.code=="1") {
							layer.alert("发送邮件成功" , 1, "邮箱验证",function(){
								window.location.href="${basePath}/AccountSafetyCentre/safetyIndex.html";
							});
						} else if(data.code == "0"){
							layer.msg(data.message, 1, 5);
						}else{
							layer.msg("发送邮件异常，请稍后再试！");
						}
					},
					error : function(data) {
						$("#submitBtn").attr("onclick","save();");
						layer.msg('网络连接异常，请稍后重试！');
					}
				});
			},'',function(){$("#submitBtn").attr("onclick","save();");});

		}

	}
</script>