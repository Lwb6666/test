<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/page/common/taglib.jsp"%>
   <form action="" method="post"  name="takeDrawForm" id="takeDrawForm">
     <input type="hidden" id="fee" name="fee" value="0"/>
	<div class="cont-word">
    	<div class="title"><h4>受限金额转可提</h4><a href="javascript:void(0);" onclick="closeWin();" class="icon icon-close fr"></a></div>
        <div class="form-info-layer">
        	<div class="form-col3">
                <label for="" class="colleft form-lable">受限金额</label>
                <span class="fl money"><strong class="oren f14"><fmt:formatNumber value="${accountVo.noDrawMoney}" pattern="#,##0.00" /></strong>元</span>
            </div>
            
        	<div class="form-col2">
                <label for="" class="colleft form-lable">转可提金额</label>
                <input type="text"  id="takeMoney4" name="takeMoney"  style="width:120px" class="colright form-inpyt-sm" value="" placeholder="请输入转可提金额"><a class="fl pdlr10 line32">元</a>
            </div>
            <div class="form-col2">
                <label for="" class="colleft form-lable">交易密码</label>
                <input type="password" name="paypassword" id="paypassword4" style="width:120px" class="colright form-inpyt-sm"  placeholder="请输入交易密码"/>
            </div>
        	<div class="form-col3">
                <label for="" class="colleft form-lable">手续费</label>
                <span class="fl money" id="feeDiv1">0元</span>
            </div>
            
             <div class="textl form-col3 gray9" style="padding:10px 13px "><i class="red"> * </i>每笔申请需收取千分之五的手续费</div>
           <!--   <div class="f12 textl form-col3 red" style="padding-left:13px;"><i class="icon error"></i>请输入交易密码</div> -->
            <div class="form-col2">
            <button type="button" onclick="closeWin();" class="remove">取消</button><button type="button" class="enter" id="btnGetDraw" onclick="apply();" >确认</button>
            </div>
         </div>
    </div> 
    </form>
<!--弹层end--->
<script language="javascript">
function closeWin(){ 
	$('#judgeToDraw1').trigger('reveal:close'); 
	} 

$(function(){
	//金额的正则表达式
	var reg1= /^(([1-9]\d{0,9})|0)(\.\d{1,2})?$/;
	//金额框变化事件
	$("#takeMoney4").change(function(){
		var takeMoney = $("#takeMoney4").val();
		if(!reg1.test(takeMoney)){
			layer.msg("您输入的金额不正确！", 2, 5);
			return;
		}
		if(takeMoney != ''){
			$("#feeDiv1").html("0元");
			$("#fee").val(0);
			if(takeMoney < 100){
				layer.msg("转可提金额不能小于100元！", 2, 5);
			}else{
				var fee = takeMoney*0.005;
				$("#fee").val(fee.toFixed(2));
				$("#feeDiv1").html(fee.toFixed(2)+"元");
				if(fee >= 50){
					$("#feeDiv1").attr("style","color:red;font-weight:bold;font-size:18px;");
				}else{
					$("#feeDiv1").removeAttr("style");
				}
			}
		} 
	});
});

/**
* 申请提交
*/
function apply(){
	$("#btnGetDraw").removeAttr("onclick");
	//验证提现提交的数据是否正确
	if(!validateTakeMoneyData()){
		$("#btnGetDraw").attr("onclick","apply();");
		return;
	}
	var fee = $("#fee").val();
	var takeMoney = $("#takeMoney4").val();
 if(layer.confirm("您的转可提金额为："+takeMoney+"元，手续费为："+fee+"元，确认要转可提吗？",function(){
		$("#takeDrawForm").ajaxSubmit({
			   url : '${basePath }/myaccount/todrawLog/saveTodrawLog.html',
			   type: "POST",
				success : function(data){
					if(data=="success"){
						layer.alert('转可提成功！', 1, "温馨提示",function(){
							window.location.reload();
				    	});
					}else{
						layer.alert(data, 5);
					}
					$("#btnGetDraw").attr("onclick","apply();");
				},
				error : function(data) {
					layer.msg("网络连接异常，请刷新页面或稍后重试！", 2, 5);
					$("#btnGetDraw").attr("onclick","apply();");
			    }
		    });
	},function(){
		$("#btnGetDraw").attr("onclick","apply();");
	}));
	 
}
/**
 * 验证转可提提交的数据是否正确
 */
function validateTakeMoneyData(){
	//金额的正则表达式
	var reg1= /^(([1-9]\d{0,9})|0)(\.\d{1,2})?$/;
	var takeMoney = $("#takeMoney4").val();
	var paypassword = $("#paypassword4").val();
	if(paypassword.length==0){
		layer.msg("交易密码不能为空！", 2, 5);
		return false;
	}
	if(takeMoney.length==0){
		layer.msg("转可提金额不能为空！", 2, 5);
		return false;
	}
	if(!reg1.test(takeMoney)){
		layer.msg("转可提金额输入不正确！", 2, 5);
		return false;
	}
	if(takeMoney < 100){
		layer.msg("转可提金额必须大于￥100！", 2, 5);
		return false;
	}
	var noDrawMoney = Number('${accountVo.noDrawMoney}');
	if(takeMoney > noDrawMoney){
		if(noDrawMoney <= 100){
			layer.msg("您的最大转可提现金额额度为0元，无法转可提。", 2, 5);
		}else{
			layer.msg("转可提金额必须小于￥"+formatMoney(noDrawMoney), 2, 5);
		}
		return false;
	}
	return true;
}
</script>
</html>