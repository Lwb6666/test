<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/page/common/taglib.jsp"%>
<script type="text/javascript" src="${basePath}/js/formValid.js"></script>
<!---------- ------------------------实名认证-弹层---------------------------------------------------->
<!--弹层star--->
	<div class="cont-word">
    	<div class="title"><h4>实名认证</h4><a href="javascript:void(0);" class="icon icon-close fr" onclick="closeRealNameAppro();"></a></div>
        <div class="form-info-layer">
            <div class="form-col2">
                <label for="" class="colleft form-lable">真实姓名</label>
                <input type="text"  name="realname" id="realname"  dataType="Limit" min="1" max="20" maxlength="20" style="width:198px" class="colright form-inpyt-sm" placeholder="请输入真实姓名">
            </div>
            
            <div class="form-col2">
                <label for="" class="colleft form-lable">身份证号 </label>
                <input type="text" name="idcard" id="idcard" dataType="Limit" min="1" max="20" maxlength="20" style="width:198px" class="colright form-inpyt-sm" placeholder="请输入身份证号">
            </div>
            <div class="form-col2">
               <div class="f14 gary2">
               <p><span class="orange2">*</span> 请务必填写真实的证件信息，认证成功后不可修改</p>
               <p><span class="orange2">*</span>港澳台地区用户/护照或其它证件/系统认证失败或次数超限，请采用<a onclick="setSdRealNameAppro();" data-reveal-id="sdRealNameAppro" data-animation="fade">人工认证</a></p>
              </div>
            </div>
            <div class="form-col2">
            <button type="button" class="remove" onclick="closeRealNameAppro();">取消</button><button type="button" class="enter" id="submitBtn" onclick="save();">确定</button>
            </div>
         </div>
    </div> 
<script type="text/javascript">
	$(function() {
		if ('${msg}' == 'email_mobile_no_appro') {
			layer.msg('请先进行邮箱或手机认证！', 1, 3, function(){
				var index = parent.layer.getFrameIndex(window.name);
				parent.layer.close(index);
			})
		}
		if ('${msg}' == 'realName_no_appro') {
			layer.msg('请先进行实名认证！', 1, 3, function(){
				var index = parent.layer.getFrameIndex(window.name);
				parent.layer.close(index);
			})
		}
	});

	/**
	 * 验证数据
	 */
	function verify(){
		var flag = true;
		var patten_realName = new RegExp(/^[\u0391-\uFFE5]+$/);
		var realname = $.trim($('#realname').val());
		if(realname == null || realname == ''){
			layer.alert("请输入真实姓名!");
			return false;
		}else{
			$('#realname').val(realname);
		}
		var idcard = $.trim($("#idcard").val());
		var isIDCard1= /(^\d{15}$)|(^\d{17}([0-9]|X)$)/;
		var isIDCard2= /^(\d{6})(18|19|20)?(\d{2})([01]\d)([0123]\d)(\d{3})(\d|X)+$/;

		if(idcard == null || idcard == ''){
			layer.alert("请输入身份证号!");
			return false;
		}else{
			if(!(isIDCard1.test(idcard) || isIDCard2.test(idcard))){
				layer.alert("证件号码不是身份证号码");
				return false;
			}else{
				$('#idcard').val(idcard);
			}
		}
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
				$.ajax({
					url : '${basePath}/account/approve/realname/realnameAppro.html',
					data :{realname:$("#realname").val(),idcard:$("#idcard").val()} ,
					type : 'post',
					dataType : 'text',
					beforeSend:function(){
						_load = layer.load('处理中..');
					},
					success : function(data) {
						$("#submitBtn").attr("onclick","save();");
						layer.close(_load);
						if(data=="success"){
							layer.alert("实名认证成功！" , 1, "实名认证",function(){
								window.location.href="${basePath}/AccountSafetyCentre/safetyIndex.html";
							});
						}else{
							layer.alert(data);
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


	/**
	 * 取消
	 */
	function cancal(){
		var index = parent.layer.getFrameIndex(window.name);
		parent.layer.close(index);
	}
</script>
