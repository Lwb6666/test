<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/page/common/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ include file="/WEB-INF/page/common/public.jsp"%>
<title>我的奖励实物领取</title>
<style type="text/css">
* {
	margin: 0;
	padding: 0;
}

.btn_hj {
	text-align: center;
	margin-top: 4px;
	background: #00a7e5;
	color: #fff;
	padding: 0 20px;
	height: 30px;
	width: 100px;
	border: 0;
	font-size: 14px;
	cursor: pointer;
	
}
</style>

</head>
<body style="overflow-y:hidden;background:#fff;">


	<div class="sf">
		<div style="width:500px;height:300px;">

			<!-- title s  -->
			<p style="font-size:14px; text-align:center; margin-top: 30px;">
				<span>请填写你的联系信息，以便我们为你寄送奖励物品</span>
			</p>
			<!-- title e  -->


			<!-- biaodan s  -->
			<dl style="margin: 0px 0px 0px -50px; line-height: 30px;">
				<dd style="margin-top: 30px;">
					<span><font color="red">*</font>姓名：</span> <input type="text"
						name="name" id="name" value="${lott_sw_lingqu.name }"
						style="width:200px" />
				</dd>
				<dd style="margin-top: 30px;">
					<span><font color="red">*</font>手机 ：</span> <input type="text"
						name="mobile" id="mobile" value="${lott_sw_lingqu.mobile }"
						style="width:200px" />
				</dd>
				<dd style="margin-top: 30px;">
					<span><font color="red">*</font>地址 ：</span> <input type="text"
						name="sw_address" id="sw_address"
						value="${lott_sw_lingqu.sw_address }" style="width:200px" />
				</dd>
				<dd style="margin-top: 30px;">
					<span><font color="red">*</font>邮编 ：</span> <input type="text"
						name="sw_code" id="sw_code" value="${lott_sw_lingqu.sw_code }"
						style="width:200px" />
				</dd>
			</dl>
			<!-- biaodan e  -->

			<!-- button  s  -->
			<div class="btn_ok_cancel"  style="margin: 0px 175px;" >
				<input type="button" value="提  交" onclick="save_sw_lingqu()"
					class="btn_hj" />
			</div>
			<!-- button  e  -->


		</div>
	</div>

</body>



<script type="text/javascript">
	$(function() {
		//alert('display-block');
		//$(".btn_hj").Attr({"display":"block"});
		
		$(".sf").click(function() {
		//layer.alert('白菜级别前端攻城师贤心', 1);
			//$(".sf").close();
			// $(".sf").toggle(); 

		});

	});

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
		$("#save_sw_lingqu").attr("href", "#");

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
					lott_id : ${lott_id}
				}, function(data) {
					if (data == "suc_hj") {
						//关闭
						layer.alert('您的领取信息已提交！', 1);
						window.parent.location.href="${path}/lottery_use_record/lott_use_record.html?id_sw="+8;
						//layer.close(pageii);  
						
					}else if(data == "fl_hj")
					{
						//关闭
						layer.alert('状态已变更，请刷新页面 ！', 8);
						//layer.close(pageii);  
					} 
					else {
						layer.alert('状态异常,请联系系统客服！', 8);
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
