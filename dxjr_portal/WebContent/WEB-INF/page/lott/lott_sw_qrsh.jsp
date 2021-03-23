<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/page/common/taglib.jsp"%>
<script type="text/javascript" src="${basePath}/js/formValid.js"></script>
<div class="cont-word">
    <div class="title"><h4>确认收货</h4><a href="javascript:void(0);" class="icon icon-close fr" onclick="closeWinsh();"></a></div>
       <div class="form-info-layer">
           <div class="form-col2">
               <label for="" class="colleft form-lable">备注</label>
               <textarea style="width:200px;height:60px;" name="remark" id="remark" class="colright form-inpyt-sm"  placeholder=""></textarea>
           </div>
           <div class="form-col2">
           <button type="button" class="enter icon-close" onclick="save_sw_qrsh()">确认</button>
           </div>
        </div>
   </div> 
<script type="text/javascript">
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
					lott_id : '${lott_id}'
				}, function(data) {
					if (data == "suc_hj") {
						//关闭
						layer.alert('您的确认收货信息已提交！', 1);
						closeWinsh();
						window.parent.location="${basePath}/lottery_use_record/lott_use_record.html";
					}else if(data == "fl_hj")
					{
						//关闭
						layer.alert('状态已变更，请刷新页面 ！', 8);
						closeWinsh();
						window.parent.location="${basePath}/lottery_use_record/lott_use_record.html";
					} 
					else {
						layer.alert('状态异常,请联系系统客服！', 8);
						closeWinsh();
						window.parent.location="${basePath}/lottery_use_record/lott_use_record.html";
					}
				});
			}));

		} else {
			$("#save_sw_qrsh").attr("href", "javascript:save_sw_qrsh();");
		}
		
		
	}
</script>
</html>
