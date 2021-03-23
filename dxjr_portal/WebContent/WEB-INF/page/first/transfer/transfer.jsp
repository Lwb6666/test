<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/page/common/taglib.jsp"%>
 <form action="" method="post" id="firstTransferForm">
	<input type="hidden" name="firstTenderRealId" value="${firstTransferTypeVo.id}" />
	<div class="cont-word">
    	<div class="title"><h4>直通车转让</h4><a href="javascript:void(0)" onclick="closeWin();" class="icon icon-close fr"></a></div>
        <div class="form-info-layer">
        	<div class="form-col2">
                <label for="" class="colleft02 form-lable">债权价格</label>
                <span class="fl money"><strong class="f14" id="account">${firstTransferTypeVo.bondsAccount}</strong>元</span>
            </div>

            
            <div class="form-col2">
                <label for="" class="colleft02 form-lable">认购奖励</label>
                  <input type="text" id="awards" name="awards"  value="0" onblur="changeAccountReal()" style="width:192px" class="colright form-inpyt-sm"  placeholder="不超过债权价格1%"><span class="fd-date">元</span>
            </div>
            
            <div class="form-col2">
                <label for="" class="colleft02 form-lable">转让价格</label>
                <span class="fl money"><strong class="f14" id="accountReal">${firstTransferTypeVo.bondsAccount}</strong>元</span>
            </div>
            
            
            <div class="form-col2">
                <label for="" class="colleft02 form-lable">转让管理费</label>
                <span class="fl money"><strong class="f14">${firstTransferTypeVo.manageFee}</strong>元<strong class="gary2 f14">（转让成功后扣除）</strong></span>
            </div>
            
            
            <div class="form-col2">
                <label for="" class="colleft02 form-lable">失效时间 </label>
                <input type="text"  id="validTime"  maxlength="1"   name="validTime" value="3"  style="width:192px" class="colright form-inpyt-sm"  placeholder=""><span class="fd-date">天</span>
            </div>
            
            <div class="form-col2">
                <label for="" class="colleft02 form-lable">定向密码 </label>
                <input type="password"  style="width:192px" maxlength="12" id="bidPassword"  name="bidPassword" class="colright form-inpyt-sm"  placeholder="选填">
            </div>
            
        
            <div class="form-col2">
                <label for="" class="colleft02 form-lable">验证码</label>
                <input type="password"  name="checkCode"  id="checkCode" style="width:120px" class="colright form-inpyt-sm"  placeholder="请输入验证码">	
                <img onclick="loadimage()" name="randImage" id="randImage" src="${basePath}/random.jsp" style="cursor: pointer;" border="0" align="middle" />
            </div>
            <div class="form-col2">
               <div class="f14 line32 gary2">
               <p><span class="orange2">*</span> 在应还款日不能发起债权转让</p>
               <p><span class="orange2">*</span> 在应还款前一日23:00自动撤销债权转让</p></div>
            </div>
            <div class="form-col2">
            <button type="button" class="remove" onclick="closeWin();">取消</button><button type="button" class="enter" onClick="doTransfer();">确认</button>
            </div>
         </div>
    </div> 
    </form>
<script type="text/javascript">
	/**
	*  调整转让价格
	*/
   function  changeAccountReal(){
	  var awards= $("#awards").val();
	   
	  var moneyReg = /^(([1-9]\d{0,9})|0)(\.\d{1,2})?$/;;//金额的正则表达式
	   
	   if (awards.length>0 && !moneyReg.test((awards))) {
		   layer.msg("认购奖励格式不正确", 2, 5);
		   $("#awards").focus();
		   return;
	   }
	   if(awards == ""){
		   $("#awards").val("0");
		   return;
	   }
	   var awards= parseFloat(awards);
	   
	   var bondsAccount=${firstTransferTypeVo.bondsAccount};
	   if (awards>parseFloat(bondsAccount*0.01).toFixed(2)) {
		   layer.msg("认购奖励不能大于"+parseFloat(bondsAccount*0.01).toFixed(2), 2, 5);
		   return;
	   }
	   $("#accountReal").text(parseFloat(bondsAccount-awards).toFixed(2));
   }
   	/**
	* 发起转让
	*/
   	function  doTransfer(){
	   	  changeAccountReal();	
		  var bidPassword = $("#bidPassword").val();
		  if(bidPassword.length!=0){
		     var regPassword = new RegExp("[0-9a-zA-Z]{6,12}");
		     if (!regPassword.test(bidPassword)) {
			     layer.msg("认购密码必须为字母或数字并且密码长度必须在6~12位之间", 2, 5);
			     $("#bidPassword").focus();
			     return;
		      }
		  }
		    
	     var  validTime= $("#validTime").val();
		   
	     var reg = new RegExp("[1-9]+");
		   if (!reg.test((validTime))) {
			   layer.msg("失效时间必须是正整数", 2, 5);
			   $("#validTime").focus();
			   return;
		   }
		   
		   if (!(1<=parseFloat(validTime)&&parseFloat(validTime)<=3 )) {
			   layer.msg("失效时间必须是1~3之间的正整数", 2, 5);
			   $("#validTime").focus();
			   return;
		   }
		   
		   var  checkCode= $("#checkCode").val();
		   if(checkCode==null || checkCode==""){
				layer.msg("验证码未填写！", 2, 5);
			    $("#checkCode").focus();
		        return;
			}
		   
			<%--发起转让时如果用户的直通车没有待收，且直通车满足解锁条件，提醒用户是否进行解锁操作。--%>
			var unLockYn = '${firstTransferTypeVo.unLockYn}';
			var account = '${firstTransferTypeVo.account}';
			var useMoney = '${firstTransferTypeVo.useMoney}';
			if(unLockYn=="Y" && ''!=account && ''!=useMoney && parseFloat(account)==parseFloat(useMoney)){
				if(layer.confirm("直通车可用余额等于直通车开通总额,可进行直通车解锁操作，你确认要继续发起转让吗？",function(){
					saveTransfer();
				}));	
			}else{
				saveTransfer();
			}
  }
	/**
	* 保存债让
	*/
   	function saveTransfer(){
   	   var _load = layer.load('处理中..');
	   $("#firstTransferForm").ajaxSubmit({
		   type: "POST",
		   url:"${basePath}/zhitongche/zhuanrang/dotransfer.html",
		   success:function (result){
			   if (result.code == '0') {
					layer.msg(result.message, 2, 5);
					loadimage();
				}else{
					  layer.msg(result.message,2,1,function(){
						  closeWin();
						  getTransferContent(document.getElementById(linkCanFirstTransfer),1)
					  });
				}
		   },
		   error:function (result){
			   	layer.close(_load);
				layer.msg('网络连接超时,请刷新页面或稍后重试!', 2, 5);
		   }
	   });
   	}
	/**
	 * 刷新验证码 
	 */
	function loadimage() {
		document.getElementById("randImage").src = "${basePath}/random.jsp?" + Math.random();
	}
</script>


 
   
