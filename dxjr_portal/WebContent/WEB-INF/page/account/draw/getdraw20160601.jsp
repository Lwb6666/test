<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/page/common/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ include file="/WEB-INF/page/common/public.jsp"%>
<title>网站账户</title>
</head>
<body style="overflow-y:hidden;background:#fff;">
<div class="sf" >
    <div class="safebox550" style="width:450px;height:480px;">
     <form action="" method="post"  name="takeDrawForm" id="takeDrawForm">
     <input type="hidden" id="fee" name="fee" value="0"/>
     <div class="tipone" style="margin-top:0px;"><span>温馨提示：</span>每笔申请需要扣除千分之五的手续费。</div>
     <dl>
         <dd><span>可提金额：</span><fmt:formatNumber value="${accountVo.drawMoney}" pattern="#,##0.00" />元</dd>
         <dd><span>受限金额：</span><fmt:formatNumber value="${accountVo.noDrawMoney}" pattern="#,##0.00" />元</dd>
         <dd><span>可用余额：</span><fmt:formatNumber value="${accountVo.useMoney}" pattern="#,##0.00" />元</dd>
         <dd><span>冻结总额：</span><fmt:formatNumber value="${accountVo.noUseMoney}" pattern="#,##0.00" />元</dd>
         <dd><span>账户总额：</span><fmt:formatNumber value="${accountVo.total}" pattern="#,##0.00" />元</dd>
         <dd><span>净值额度：</span><fmt:formatNumber value="${maxAccount}" pattern="#,##0.00" />元</dd>
         <dd><span>交易密码：</span> 
           <input type="password" name="paypassword" id="paypassword" style="width:100px" />&nbsp;
         </dd>
         <dd><span>转可提金额：</span> 
           <input type="text" id="takeMoney" name="takeMoney" style="width:100px" />&nbsp;&nbsp;大于等于100元
         </dd>
         <dd><span>参考手续费：</span><label id="feeDiv">0元</label></dd>
      </dl>
      <div class="gg_btn"><input id="btnGetDraw" type="button" value="确认提交" onclick="apply();" style="cursor:pointer;"/></div>              
   	  </form>
   </div>
</div>
</body>
<script language="javascript">
$(function(){
	//金额的正则表达式
	var reg1= /^(([1-9]\d{0,9})|0)(\.\d{1,2})?$/;
	//金额框变化事件
	$("#takeMoney").change(function(){
		var takeMoney = $("#takeMoney").val();
		if(!reg1.test(takeMoney)){
			layer.msg("您输入的金额不正确！", 2, 5);
			return;
		}
		if(takeMoney != ''){
			$("#feeDiv").html("0元");
			$("#fee").val(0);
			if(takeMoney < 100){
				layer.msg("转可提金额不能小于100元！", 2, 5);
			}else{
				var fee = takeMoney*0.005;
				$("#fee").val(fee.toFixed(2));
				$("#feeDiv").html(fee.toFixed(2)+"元");
				if(fee >= 50){
					$("#feeDiv").attr("style","color:red;font-weight:bold;font-size:18px;");
				}else{
					$("#feeDiv").removeAttr("style");
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
	var takeMoney = $("#takeMoney").val();
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
	var takeMoney = $("#takeMoney").val();
	var paypassword = $("#paypassword").val();
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