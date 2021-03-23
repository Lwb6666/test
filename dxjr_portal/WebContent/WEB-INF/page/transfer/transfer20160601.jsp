<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/page/common/taglib.jsp"%>
<%@ include file="/WEB-INF/page/common/public.jsp"%>
<!--popowindow-->
<div class="sf">
	<div class="safebox550">
		<dl>
			<dd>&nbsp;</dd>

			<%-- 
	                      <dd><span><font color="#22adce">*</font>转让标题：</span><input  type="text"  id="transferName"  name="transferName"  style="width:150px"  maxlength="30"   ></dd>
	                      <dd> <span>转让内容：</span><textarea cols="40" rows="3" id="transferContent" name="transferContent" maxlength="100"></textarea> </dd>
                       --%>

			<dd>
				<span>债权价格：</span>
				<%-- ${transfer.capital}+${transfer.interest}-${transfer.gainLoss}= --%>
				<label id="account">${transfer.account}</label>元
			</dd>
			<dd>
				<span><font color="#22adce">*</font>转让系数：</span><input type="text"
					style="width: 150px" id="coef" name="coef" maxlength="5"
					value="1.000" onblur="changeAccountReal()">
				系数在0.99~1.00之间
			</dd>
			<dd>
				<span>转让价格：</span><label id="accountReal">${transfer.accountReal}</label>元
			</dd>
			<dd>
				<span>转让管理费：</span><label id="manageFee">${transfer.manageFee}</label>元
				（转让成功后扣除）
			</dd>
			<dd style="width: 550px;">
				<span><font color="#22adce">*</font>最大认购金额：</span><input type="text"
					name="mostAccount" id="mostAccount" value="${transfer.accountReal}"
					style="width: 150px">元（不低于50，不超过转让价格）
			</dd>
			<dd>
				<span><font color="#22adce">*</font>最小认购金额：</span><input type="text"
					name="lowestAccount" id="lowestAccount" value="50"
					style="width: 150px">元（不低于50）
			</dd>
			<dd>
				<span><font color="#22adce">*</font>失效时间：</span>
				<div id="divValidTime">
					<input type="text" id="validTime" maxlength="1" name="validTime"
						value="3" style="width: 150px">天
				</div>
				<div id="divValidMinute" style="display: none;">
					1天（仅限定向转让）<input type="hidden" id="validMinute" name="validMinute">
				</div>
			</dd>
			<dd>
				<span>定向密码：</span><input type="password" maxlength="12"
					id="bidPassword" name="bidPassword"
					style="width: 150px; margin-top: 10px;" onblur="changeValidTh()">(可填可不填)
			</dd>
			<dd>
				<span><font color="#22adce">*</font>验证码：</span> <input type="text"
					name="checkcode" id="checkcode" style="width: 100px"> <img
					onclick="loadimage()" name="randImage" id="randImage"
					src="${basePath}/random.jsp" style="cursor: pointer;" border="0"
					align="middle" />
			</dd>
			<!-- 
                       
                       -->
		</dl>
		<div class="gg_btn">
			<input type="button" value="确认"
				onclick="doTransfer(${transfer.tenderId},${transfer.borrowId})">
		</div>
	</div>
	<div class="tip">
		<div class="safebox550">
			<h3>温馨提示：</h3>
			<p>1.在应还款日，不可发起债权转让</p>
			<p>2.在应还款前一日23:00自动撤销债权转让</p>

		</div>
	</div>
</div>


<script type="text/javascript">

$.ready(function (){
	//修改文本框的样式
	$(".safebox550").find("input[type='text']").attr("style","width:150px;height:27px");
});


//自动输入投标金额
function enterMomey(){
 // $("#mostAccount").val($("#accountReal").text());
}


   function  changeAccountReal(){
	   var coef= $("#coef").val();
	   
	   if (coef.length==0) {
			layer.msg("转让系数不能为空", 2, 5);
			 $("#coef").focus();
			return;
	   }
	   
	   var reg = new RegExp("^[0-9][.]{0,1}[0-9]{0,3}$");
	   
	   if (!reg.test((coef))) {
		   layer.msg("转让系数格式不正确", 2, 5);
		   $("#coef").focus();
		   return;
	   }
	   
	   var fcoef= parseFloat(coef);
	   
	   if (!(0.99<=fcoef&&fcoef<=1.00)) {
		   layer.msg("转让系数必须在0.99~1.00之间", 2, 5);
		   $("#coef").focus();
		   return;
	   }
	   
	   var account= $("#account").text();
	   $("#accountReal").text(round(fcoef*parseFloat(account)));
	   $("#mostAccount").val(round(fcoef*parseFloat(account)));
		  
   }
    
   

   function  round(number){
	    return   number.toFixed(2); //   Math.round(number*100)/100;
   }
   


	function  doTransfer(tranderId,borrowId){
		
		/*  var  transferName =$("#transferName").val();
		    if ($.trim(transferName).length==0) {
				   layer.msg("转让标题不能为空", 2, 5);
				   $("#transferName").focus();
				   return;
		 } */
	 
		//最大认购金额
		var reg = new RegExp("[1-9]+");
	   
       var  mostAccount= $("#mostAccount").val();
		
	   if (!reg.test((mostAccount))) {
		   layer.msg("最大认购金额必须是大于50的数字", 2, 5);
		   $("#mostAccount").focus();
		   return;
	   }
	   
	   if (parseFloat(mostAccount)<50) {
		   layer.msg("最大认购金额不得低于50", 2, 5);
		   $("#mostAccount").focus();
		   return;
	   }
	   
	  if(!matchMoney(mostAccount,"最大认购金额")){
		  return;
	  } 
	   
	   
	   if (parseFloat(mostAccount)>parseFloat($("#accountReal").text())) {
		   layer.msg("最大认购金额不能超过转让价格", 2, 5);
		   $("#mostAccount").focus();
		   return;
	   }
	   
	   
		
	   
	   var  lowestAccount= $("#lowestAccount").val();
	   
	   if (!reg.test((lowestAccount))) {   
		   layer.msg("最小金额输入不合法", 2, 5);
		   $("#lowestAccount").focus();
		   return;
	   }
	   
	   if (50 > parseFloat(lowestAccount)  ) {
		   layer.msg("最小认购金额不得低于50", 2, 5);
		   $("#lowestAccount").focus();
		   return;
	   }
	   
	   if (parseFloat(mostAccount)< parseFloat(lowestAccount)  ) {
		   layer.msg("最小认购金额必须是小于等于最大认购金额", 2, 5);
		   $("#lowestAccount").focus();
		   return;
	   }
		
	   
	   if(!matchMoney(lowestAccount,"最小认购金额")){
			  return;
		  } 
	   
	   
	   
	  var    bidPassword = $("#bidPassword").val();
	  if(bidPassword.length!=0){
	   var regPassword = new RegExp("[0-9a-zA-Z]{6,12}");
	    if (!regPassword.test(bidPassword)) {
		   layer.msg("认购密码必须为字母或数字并且密码长度必须在6~12位之间", 2, 5);
		    $("#bidPassword").focus();
		    return;
       	   }      		
	  }
	    
     var  validTime= $("#validTime").val();
	   
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
	   
	    
	   
	   var  checkcode= $("#checkcode").val();
	   if(checkcode==null || checkcode==""){
			layer.msg("验证码未填写！", 2, 5);
		    $("#checkcode").focus();
	        return;
		}
	    
	   var  transferContent=$("#transferContent").val();
	   var _load = layer.load('处理中..');
	   $.ajax({
		   type: "POST",
		   url:"${basePath}/zhaiquan/dotransfer.html",
		   data:{mostAccount:mostAccount,lowestAccount:lowestAccount,
			   validTime:validTime,bidPassword:$("#bidPassword").val(),checkcode:checkcode,
			   // transferName:transferName,transferContent:transferContent,
			   tenderId:tranderId,borrowId:borrowId,account:$("#account").text(),
			   coef:$("#coef").val(),validMinute:$("#validMinute").val()},
		   dataType:"json",
		   success:function (result){
			   if (result.code == '0') {
				   layer.close(_load);
					parent.layer.msg(result.message, 1, 5);
					loadimage();
				}else{
					  parent.layer.msg(result.message,1,1);
						window.parent.location.href=window.parent.location.href;
				}
		   },
		   error:function (result){
			   layer.close(_load);
				layer.msg('网络连接超时,请您稍后重试.', 1, 5);
		   }
	   });
		
  }
	
	/**
	 * 刷新验证码 
	 */
	function loadimage() {
		document.getElementById("randImage").src = "${basePath}/random.jsp?" + Math.random();
	}
	
	
	function matchMoney(pay_money,msg){
		var reg= /^\d+[\.]?\d{0,2}$/g;
			var zfdsReg = /^(([1-9]\d{0,9})|0)(\.\d{1,2})?$/;;//金额的正则表达式
			if(pay_money==null || pay_money==""){
				 layer.msg(msg+"金额未填写！", 2, 5);
				 return false;
			}else if(!zfdsReg.test(pay_money)){
				  layer.msg(msg+"输入金额有误！", 2, 5);
				 return false;
			}else if(pay_money==0){
				  layer.msg(msg+"金额不能为0", 2, 5);
					 return false;
			}else if(!reg.test(pay_money)){
				     layer.msg(msg+"只能保留2位小数", 2, 5);
					 return false;
			}
		
		return true;
	}
	/**
	 * 改变失效时间
	 */
	function changeValidTh(){
		var bidPassword = $("#bidPassword").val();
	    if(bidPassword!=null && bidPassword!=""){			
		    $("#divValidMinute").show();
		    $("#divValidTime").hide();
		}else{
			$("#divValidMinute").hide();
		    $("#divValidTime").show();
		}
	}
	
	
	

</script>





