<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/page/common/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ include file="/WEB-INF/page/common/public.jsp"%>
<title>我的奖励_实物奖励_确认收货</title>
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
				<span>您的实物奖励已派发，请根据实际情况点击确认收货</span>
			</p>
			<!-- title e  -->


			<!-- 确认收货  s  -->
			<dl style="margin: 0px 0px 0px -50px; line-height: 30px;">
				<dd style="margin-top: 30px;">
					
					<span>
						<font color="red">*</font>备注：
					</span> 
					
					<input type="text"
						name="remark" id="remark" value="${lott_sw_qrsh.remark }"
						style="width:200px" />
						
				</dd>
				
				
			</dl>
			<!-- 确认收货  e  -->

			<!-- button  s  -->
			<div class="btn_qrsh"  style="margin: 0px 175px;" >
				<input type="button" value="确认收货" onclick="save_sw_qrsh()"
					class="btn_hj" />
			</div>
			<!-- button  e  -->


		</div>
	</div>

</body>



<script type="text/javascript">
	$(function() {
		
		$(".sf").click(function() {

		});

	});

	//提交前验证
	function validateData() {
		var remark = $("#remark").val();
		if (remark.length == 0) {
			layer.msg("备注未填写！", 2, 5);
			return false;
		} else if (remark.length > 99) {
			layer.msg("备注长度太长！", 2, 5);
			return false;
		}
		return true;
	}

	//确认收货-提交
	function save_sw_qrsh() {

		if (validateData()) {
			if (layer.confirm("确定收货吗?", function() {
				var remark = $("#remark").val();
				$.post("${basePath}/lottery_use_record/lingquQrsh.html", {
					remark : remark,
					lott_id : ${lott_id}
				}, function(data) {
					if (data == "suc_hj") {
						//关闭
						layer.alert('您的确认收货信息已提交！', 1);
						window.parent.location.href="${path}/lottery_use_record/lott_use_record.html?id_sw="+8;
						
					}else if(data == "fl_hj")
					{
						//关闭
						layer.alert('状态已变更，请刷新页面 ！', 8);
					} 
					else {
						layer.alert('状态异常,请联系系统客服！', 8);
					}
				});
			}));

		} else {
			$("#save_sw_qrsh").attr("href", "javascript:save_sw_qrsh();");
		}
		
		
	}
</script>
</html>
