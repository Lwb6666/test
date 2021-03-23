<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/page/common/taglib.jsp"%>
     <!--popowindow-->
 <form action="" method="post" id="firstTransferForm">
 <input type="hidden" name="firstTenderRealId" value="${firstTransferTypeVo.id}" />
 <div class="sf" >
         <div class="safebox550">
                   <dl>
                      <dd>
                      	  <span>债权价格：</span><label id="account">${firstTransferTypeVo.bondsAccount}</label>元
                      </dd>
                      <dd>
                      	   <span><font color="#FF0004">认购奖励：</font></span>
                           <input  type="text" style="width:150px"  id="awards" name="awards"  value="0"  
                             onblur="changeAccountReal()" ><font color="#FF0004">元</font>金额不超过债权价格的1%
                      </dd>
                      <dd>
                           <span>转让价格：</span><label id="accountReal">${firstTransferTypeVo.bondsAccount}</label>元
                      </dd>
                      <dd>
	                      <span class="tippic" onmousemove="$(this).find('div').show();" onmouseout="$(this).find('div').hide();" 
	                      			style="font-style:normal;"><img src="${basePath}/images/q].gif" width="23" height="23"/>
	                                            <div class="ptiped" style="left:75px;display: none;" >2015/6/1前免管理费<br />认购后3个月内收1%的管理费<br /> 认购3-12个月内收0.5%的管理费<br /> 认购12个月后免管理费</div>
						  	转让管理费：</span><label id="manageFee" >${firstTransferTypeVo.manageFee}</label>元 （转让成功后扣除）
                      	  
                      
                      </dd>
                      <dd>
                      	  <span><font color="#22adce">*</font>失效时间：</span>
                          <input type="text" id="validTime"  maxlength="1"   name="validTime" value="3" style="width:150px">天
                      </dd>
                      <dd>
                          <span>定向密码：</span>
                          <input type="password" maxlength="12" id="bidPassword"  name="bidPassword" style="width:150px;margin-top: 10px;">(可填可不填)
                      </dd>
                      <dd>
                          <span><font color="#22adce">*</font>验证码：</span>
                          <input type="text" name="checkCode"  id="checkCode" style="width:100px">
                          <img onclick="loadimage()" name="randImage" id="randImage" src="${basePath}/random.jsp" style="cursor: pointer;" border="0" align="middle" />
                      </dd>
                   </dl>
		     <div class="gg_btn"><input type="button" value="确认" onclick="doTransfer()"></div>
		 </div>
       <div class="tip">
            <div class="safebox550">
                      <h3>温馨提示：</h3>
                      <p>1.转让过程中不参与投标</p>
                      <p>2.在应还款日，不可发起债权转让 </p>
                      <p>3.在应还款前一日23:00自动撤销债权转让</p>
                      <p>4.有直通车投标冻结时不允许发起直通车转让</p>
                      <p>5.认购3个月内收1%管理费；认购3-12个月收0.5%管理费；认购12个月后免管理费</p>
          </div>
      </div>
   </div>
</form>       
<script type="text/javascript">

$.ready(function (){
	//修改文本框的样式
	$(".safebox550").find("input[type='text']").attr("style","width:150px;height:27px");
});

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
				    layer.close(_load);
					layer.msg(result.message, 2, 5);
					loadimage();
				}else{
					  layer.msg(result.message,2,1,function(){
						  window.parent.location.href=window.parent.location.href;
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



 
   
