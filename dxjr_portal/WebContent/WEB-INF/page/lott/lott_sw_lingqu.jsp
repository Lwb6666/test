<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/page/common/taglib.jsp"%>
<script type="text/javascript" src="${basePath}/js/formValid.js"></script>
<div class="cont-word">
    	<div class="title"><h4>填写邮寄地址</h4><a  class="icon icon-close fr" onclick="closeWin();"></a></div>
        <div class="form-info-layer">
        	<div class="form-col3 mt10">
                <label for="" class="colleft form-lable">收件人</label>
                <input type="text" name="name" id="name"  style="width:120px" class="colright form-inpyt-sm"  placeholder="请输入您的姓名">
            </div>
            
            <div class="form-col2">
                <label for="" class="colleft form-lable">手机号码</label>
                <input type="text"  style="width:120px" name="mobile" id="mobile"  class="colright form-inpyt-sm"  placeholder="请输入您的手机号">
            </div>
            <div class="form-col2">
                <label for="" class="colleft form-lable">收件地址</label>
                <textarea style="width:200px;height:60px;" name="sw_address" id="sw_address" class="colright form-inpyt-sm"  placeholder="请输入收件详细地址"></textarea>
            </div>
            <div class="form-col2">
                <label for="" class="colleft form-lable">邮编</label>
                <input type="text"  name="sw_code" id="sw_code" style="width:120px" class="colright form-inpyt-sm"  placeholder="请输入邮编">
            </div>
            <div class="textl form-col3 gray9" style="padding:15px 13px "><i class="red"> * </i>请填写您真实有效的联系信息，以便我们为您寄送奖品</div>
            <div class="f12 textl form-col3 red" style="padding-left:13px;"></div>
            <div class="form-col2">
            <button type="button" class="remove"><a href="javascript:void(0);" onclick="closeWin();" class="icon-close">取消</a></button><button type="button" class="enter" onclick="save_sw_lingqu()">确认</button>
            </div>
         </div>
    </div> 
<script type="text/javascript">
	//提交前验证
	function validateData() {
		var mobileReg = /^1[3|5|7|8|][0-9]{9}$/;
		var sw_codeReg = /^[0-9]{6}$/;
		var name = $("#name").val();
		var mobile = $("#mobile").val();
		var sw_code = $("#sw_code").val();
		var sw_address = $("#sw_address").val();
		if (name.length == 0) {
			layer.msg("姓名未填写！", 2, 5);
			return false;
		} else if (name.length > 50) {
			layer.msg("姓名长度不能超过50个字符！", 2, 5);
			return false;
		}
		if (mobile.length == 0) {
			layer.msg("手机未填写！", 2, 5);
			return false;
		} else {
			if (!mobileReg.test(mobile)) {
				layer.msg("手机号码输入不正确！", 2, 5);
				return false;
			}
			if (mobile.length > 15) {
				layer.msg("手机长度太长！", 2, 5);
				return false;
			}
		}
		
		

		if (sw_address.length == 0) {
			layer.msg("地址未填写！", 2, 5);
			return false;
		} else {
			if (sw_address.length > 100) {
				layer.msg("地址长度太长！", 2, 5);
				return false;
			}
		}
		
		
		if (sw_code.length == 0) {
			layer.msg("邮编未填写！", 2, 5);
			return false;
		} else {
			if (!sw_codeReg.test(sw_code)) {
				layer.msg("邮编输入不正确！", 2, 5);
				return false;
			}
		}

		return true;
	}

	//实物奖品，领取提交
	function save_sw_lingqu() {
		$("#save_sw_lingqu").attr("href", "");
		if (validateData()) {
			if (layer.confirm("确定要提交吗？", function() {
				var name = $("#name").val();
				var mobile = $("#mobile").val();
				var sw_address = $("#sw_address").val();
				var sw_code = $("#sw_code").val();
				$.post("${basePath}/lottery_use_record/lingqu_submit.html", {
					name : name,
					mobile : mobile,
					sw_address : sw_address,
					sw_code : sw_code,
					lott_id : '${lott_id}'
				}, function(data) {
					if (data == "suc_hj") {
						//关闭
						layer.alert('您的领取信息已提交！', 1);
						closeWin();
						window.parent.location="${basePath}/lottery_use_record/lott_use_record.html";
					}else if(data == "fl_hj")
					{
						//关闭
						layer.alert('状态已变更，请刷新页面 ！', 8);
						closeWin();
						window.parent.location="${basePath}/lottery_use_record/lott_use_record.html";
					} 
					else {
						layer.alert('状态异常,请联系系统客服！', 8);
						closeWin();
						window.parent.location="${basePath}/lottery_use_record/lott_use_record.html";
					}
				});
			}));

		} else {
			$("#save_sw_lingqu").attr("href", "javascript:save_sw_lingqu();");
		}
		//alert('jieshu');
		
		
	}
</script>
</html>
