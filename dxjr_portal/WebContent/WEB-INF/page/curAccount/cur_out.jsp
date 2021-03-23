<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/page/common/taglib.jsp"%>
<form action="" method="post" name="curOutForm" id="curOutForm">
<input type="hidden" name="targetFrame" id="targetFrame" value="${targetFrame}"/>

<div class="cont-word">
    	<div class="title"><h4>转出</h4><a href="javascript:void(0);" onclick="closeCurOut();" class="icon icon-close fr"></a></div>
        <div class="form-info-layer">
        	<div class="form-col3">
                <label for="" class="colleft form-lable">可转出金额</label>
                <span class="fl money"><strong class="oren f14"><fmt:formatNumber value="${maxAccount}" pattern="#,##0.00"></fmt:formatNumber></strong>元</span>
            </div>
            <div class="form-col2">
                <label for="" class="colleft form-lable">转出金额</label>
                
                <input type="text" name="account" id="account" style="width:120px" class="colright form-inpyt-sm" placeholder="请输入金额"><a href="javascript:enterMomey();" class="fl pdlr10 line32">全额转出</a>
            </div>
        	<div class="form-col2">
                <label for="" class="colleft form-lable">交易密码</label>
                <input type="password" name="paypassword" id="paypassword" style="width:120px" class="colright form-inpyt-sm"  placeholder="输入交易密码"><a href="${basePath}/AccountSafetyCentre/safetyIndex.html"  class="fl pdlr10 line32">忘记交易密码</a>
            </div>
            <div class="form-col2">
            <button type="button" class="remove" onclick="closeCurOut();">取消</button><button type="button" class="enter" onClick="saveCurOut();">立即转出</button>
            </div>
         </div>
    </div> 
    
    
    <script type="text/javascript">
var _load;
/**
 * 点击自动输入金额触发事件
 */
function enterMomey(){
	if(${null==portal:currentUser()}){
		 layer.msg("请先登录", 1, 5,function(){
			 parent.window.location.href="${path}/member/toLoginPage.html";
		 });
		 return;
	}
	if(${portal:hasRole("borrow")}){
		parent.layer.msg("您是借款用户,不能进行此操作", 1, 5);
		 return;
	}
	var useMoney = Number('${maxAccount}');
	$("#account").val(useMoney);
}

function validateData(){
	//金额的正则表达式
	var zfdsReg = /^(([1-9]\d{0,9})|0)(\.\d{1,2})?$/;
	var reg= /^\d+[\.]?\d{0,2}$/g;
	if(new Number('${curOutCount}') >= 5){
		parent.layer.msg("当天转出笔数已达到5笔，无法再进行转出!", 2, 5);
		return false;
	}
	if(new Number('${currentHourNum}') >= 0 && new Number('${currentHourNum}') < 4){
		parent.layer.msg("耐心等待一会，系统正在努力结算！", 2, 5);
		return false;
	}
	
	var account = $("#account").val();
	if(account == null || account == ""){
		parent.parent.layer.msg("转出金额未填写!", 2, 5);
		return false;
	}
	if(!zfdsReg.test(account)){
		parent.layer.msg("转出金额输入有误!", 2, 5);
		return false;
	}
	if(account == 0){
		parent.layer.msg("转出金额不能为 0!", 2, 5);
		return false;
	}
	if(!reg.test(account)){
		parent.layer.msg("转出金额只能保留2位小数!", 2, 5);
		return false;
	}
	if(new Number(account) > 100000){
		parent.layer.msg("转出金额不能超过10万元", 2, 5);
		return false;
	}
	if(account > new Number('${maxAccount}')){
		if(new Number('${maxAccount}') > 0){
			parent.layer.msg("转出金额不能超过${maxAccount}元", 2, 5);
		}else{
			parent.layer.msg("可转出金额为0元", 2, 5);
		}
		return false;
	}
	
	var paypassword = $("#paypassword").val();
	if(paypassword == null || paypassword == ""){
		parent.layer.msg("交易密码未填写!", 2, 5);
		return false;
	}
	return true;
}
/**
 * 活期宝转出到可用余额
 */
function saveCurOut(){
	$("#save_curOut").removeAttr("onclick");
	if(validateData()){
		if(parent.layer.confirm("确定要转出吗？",function(){
			_load = parent.layer.load('处理中..');
			//提交后台
			$("#curOutForm").ajaxSubmit({
				url : '${basePath }/curOut/saveCurOut.html',
				type : "POST",
				success : function(data) {
					layer.close(_load);
					$("#save_curOut").attr("onclick", "saveCurOut()");
					if(data.code=="1"){
						parent.layer.alert('此次交易成功转出'+$("#account").val()+'元到可用余额', 1, "温馨提示", function(){
							if($("#targetFrame").val() == 1){
								window.open("${path}/curAccountController/curAccountInterest.html","_parent");
							}else{
								window.open("${path}/myaccount/toIndex.html","_parent");
							}
						});
					}else{
						parent.layer.msg(data.message, 2, 5);
					}
				},
				error : function(data) {
					layer.close(_load);
					$("#save_curOut").attr("onclick", "saveCurOut()");
					parent.layer.msg("网络连接超时，请您稍后重试", 3, 5);
				}
			});
		}, function() {
			$("#save_curOut").attr("onclick", "saveCurOut()");
		}));
		 
	}else{
		$("#save_curOut").attr("onclick", "saveCurOut()");
	}	
}

/**
 * 点击取消按钮
 */
function closeCurOut(){
	$('#zc').trigger('reveal:close'); 
}
</script>
    </form>