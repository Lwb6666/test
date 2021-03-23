<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/page/common/taglib.jsp"%>
<script type="text/javascript" src="${basePath}/js/formValid.js"></script>
<!---------- ------------------------找回密码-弹层---------------------------------------------------->
<form id="findPwdForm" name="findPwdForm" action="">
  <!--弹层star--->
	<div class="cont-word">
    	<div class="title"><h4>找回登录密码</h4><a href="javascript:void(0);" onclick="closeFindLoginPwd();" class="icon icon-close fr"></a></div>
        <div class="form-info-layer">
            <div class="form-col2">
                <label for="" class="colleft02 form-lable">用户名</label>
                <input type="text"  style="width:198px" class="colright form-inpyt-sm" id="username" name="username"  dataType="Limit" min="1" max="20" maxlength="20" placeholder="请输入用户名">
            </div>
            <div class="form-col2">
                <label for="" class="colleft02 form-lable">真实姓名</label>
                <input type="text" style="width:198px" class="colright form-inpyt-sm" id="realname"  name="realname"  dataType="Limit" min="1" max="20" maxlength="20" placeholder="请输入真实姓名">
            </div>
            <div class="form-col2">
                <label for="" class="colleft02 form-lable">身份证号</label>
                <input type="text" style="width:198px" class="colright form-inpyt-sm" id="idCard"  name="idCard" dataType="Limit" min="1" max="20" maxlength="20" placeholder="请输入身份证号">
            </div>
            <div class="form-col2">
                <label for="" class="colleft02 form-lable">验证码</label>
                <input type="text"  style="width:120px" class="colright form-inpyt-sm"  id="checkcode" name="checkcode"dataType="Require" maxlength="4"  placeholder="验证码">
               <img onclick="loadimage()" class="fl" name="randImage" id="randImage" src="${basePath}/random.jsp" style="cursor: pointer;width:80px;height: 32px;" border="0" align="middle" />
            </div>
               <div class="form-col2">
            <p class="fl f14"><input name="findType" value="email" type="radio" checked="checked" /><span class="mll0">绑定邮箱找回</span></p><p class="fr f14"><input name="findType" value="mobile" type="radio"/><span class="mll0">绑定手机找回</span></p> 
            </div>
            <div class="form-col2">
            <button type="button" class="remove" onclick="closeFindLoginPwd();">取消</button><button type="button" id="submitBtn"  class="enter" onclick="save();">确定</button>
            </div>
         </div>
    </div> 
<!--弹层end--->
</form>
<script type="text/javascript">
    /**
     * 刷新验证码
     */
    function loadimage() {
        document.getElementById("randImage").src = "${basePath}/random.jsp?" + Math.random();
    }
    /**
     * 提交数据
     */
    function save(){
        //验证
      $('#submitBtn').attr("disabled","disabled");
        if($('#username').val()==''){
        	alert('用户名不能为空！');
        	return;
        }
        if($('#realname').val()==''){
        	alert('真实姓名不能为空！');
        	return;
        }
        if($('#idCard').val()==''){
        	alert('身份证不能为空！');
        	return;
        }
        if($('#checkcode').val()==''){
        	alert('验证码不能为空！');
        	return;
        } 
        //提交表单
        layer.confirm('确定提交吗？', function(){
            $("#findPwdForm").ajaxSubmit({
                url : '${basePath}/account/safe/findLoginPwd.html',
                async:false,
                type : 'post',
                dataType : 'json',
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
                    	if($('input[name="findType"]:checked').val()=='mobile'){
                    		layer.alert("登录密码重置成功,已发送至您的认证手机，为了您的账户安全请尽快修改！！" , 1, "登录密码重置",function(){
    							window.location.href="${basePath}/AccountSafetyCentre/safetyIndex.html";
    						});
                    	}
                    	if($('input[name="findType"]:checked').val()=='email'){
                    		layer.alert("登录密码重置成功,已发送至您的认证邮箱，为了您的账户安全请尽快修改！！" , 1, "登录密码重置",function(){
    							window.location.href="${basePath}/AccountSafetyCentre/safetyIndex.html";
    						});
                    	}
                    }
                },
                error : function(data) {
                    layer.msg('网络连接异常，请稍后重试！');
                    cancal();
                }
            });
        },'',function(){$('#submitBtn').removeAttr("disabled");});
    }
</script>
