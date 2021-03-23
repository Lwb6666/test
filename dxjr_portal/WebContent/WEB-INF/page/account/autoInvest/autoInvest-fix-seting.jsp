<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/page/common/taglib.jsp"%>
<!---------- ------------------------自动投标-弹层---------------------------------------------------->
<form id="autoInvestForm1">
	<div class="cont-word cont-w02">
    	<div class="title"><h4>自动投宝设置</h4><a  onclick="closeFixWin();" class="icon icon-close fr"></a></div>
        <div class="form-info-layer">
        	<div class="form-col2">
                <label for="" class="colleft02 form-lable">账户可用余额</label>
                <span class="fl money"><strong class="f14 orange2"><fmt:formatNumber value="${useMoney }" pattern="#,##0.00"/></strong>元<a href="${basePath}/account/topup/toTopupIndex.html" target="_blank" class="blue ml20">充值</a></span>
            </div>
            <div class="form-col2">
                <label for="" class="colleft02 form-lable">投标方式 </label>
                <div class="form-box fl">
               <p class="f14 clearboth">
               <span class="fl mr20">
                  <input type="radio" checked="checked" name="autoTenderType" id="autoTenderType1"  value="1"/> <em>按金额投标</em>
                 </span><span class="fl relative">
                 <input style="width:150px; padding-right:20px; margin-top:-5px;" class="colright form-inpyt-sm"  placeholder="" type="text" id="tenderMoney" maxlength="10" value='${fixAutoInvest.tenderMoney }' name="tenderMoney" />
                 <span class="yuan gray9">元</span></span>
               </p>
               <p class="f14 mt10">
               <span><input type="radio" name="autoTenderType" id="autoTenderType2"  value="2" /> <em>按账户余额投标</em></span>
               </p>
               <p class="f14 mt10">
               <span> 
                  <input type="checkbox" id="isUseCurCB" name="isUseCurCB" value="1" checked="checked"/>
                  <input type="hidden" name="isUseCur" id="isUseCur" value="1"/> 
                  <em>使用活期宝金额</em>
              </span>
               </p>
                </div>
            </div>
            <div class="form-col2">
                <label for="" class="colleft02 form-lable">定期宝期限</label>
                <div class="form-checkbox fl f14">
                	<span><input  type="checkbox" id="fixLimit1" name="fixLimits" value="1"/><em>1月宝</em></span>
                	<span class="ml20"><input type="checkbox" id="fixLimit3" name="fixLimits" value="3"/> <em>3月宝</em></span>
                    <span class="ml20"><input type="checkbox" id="fixLimit6" name="fixLimits" value="6"/> <em>6月宝</em></span>
                    <span class="ml20"><input type="checkbox" id="fixLimit12" name="fixLimits" value="12"/> <em>12月宝</em></span>
                </div>
            </div>
            <div class="form-col2">
                <label for="" class="colleft02 form-lable">启用状态</label>
                <div class="form-checkbox fl f14">
                    <input type="hidden" name="status" id="status1" value="1"/>
                    <span><input type="checkbox" checked="checked" name="statusCB" value="1" id="statusCB1" /><em>立即启用</em></span>
                </div>
            </div>
            <div class="form-col2">
            <button type="button" class="remove icon-close" onclick="closeFixWin();">取消</button><button type="button" class="enter" onclick="save();">确认</button>
            </div>
         </div>
    </div>
    <input type="hidden" value="${fixAutoInvest.id }" name="id"/>
    <input type="hidden" value="${fixAutoInvest.uptime }" name="uptime"/>
    </form> 
<script type="text/javascript">


//自动投宝弹框回显
$(function(){
	   if('${fixAutoInvest.id }'>0){
		   //投宝方式 
		   var  autoTenderType='${fixAutoInvest.autoTenderType}';
		   if(autoTenderType=='1'){
		     $("#autoTenderType1").attr("checked",true);
			 $("#autoTenderType2").attr("checked",false);
		   }else{
		     $("#tenderMoney").attr("disabled","disabled");
			 $("#tenderMoney").val('');
			 $("#autoTenderType1").attr("checked",false);
			 $("#autoTenderType2").attr("checked",true);
		   }
		   //宝期限
		   var  fixLimit= '${fixAutoInvest.fixLimit}';
		   var arr=fixLimit.replace(/\[/g, "").split("]"); 
		   for(var i=0;i<arr.length;i++){
			   $('#fixLimit'+arr[i]).attr("checked",true);
		   }
		   if('${fixAutoInvest.isUseCur}'==1){	
			   $('#isUseCurCB').attr("checked",true);
		   }else{
			   $('#isUseCurCB').attr("checked",false);
		   }
		   if('${fixAutoInvest.status}'==1){
			   $('#statusCB').attr("checked",true);
		   }else{
			   $('#statusCB').attr("checked",false);
		   }
		   
	   }else{
		   if('${msg}'!=''){
			   layer.msg('${msg}',2,5,function(){
				    layer.closeAll('page'); //关闭所有页面层
				    $("#setFix").hide();				   
					autoInvestFix();
				});
		   }
	   }
});
var _load;
function save(){
	      //验证数据
		 if(!validationData1()){
			 
			 return;
		 }
	     //提交表单
		 layer.confirm('你将设置自动投宝', function(){
			 $("#autoInvestForm1").ajaxSubmit({
					url : '${basePath}/myaccount/autoInvestFix/addAutoInvestFix.html',
					async:false,
					type : 'post',
					dataType : 'json',
					beforeSend:function(){
						 _load = layer.load('处理中..');
					},
					success : function(data) {
						layer.close(_load);
						if(data.code=='0'){
							layer.msg(data.message,2,5);
						}else{
							layer.msg(data.message,2,1,function(){
								layer.closeAll('page'); //关闭所有页面层
								closeFixWin();
								autoInvestFix();
							});
						}
					},
					error : function(data) {
						layer.msg('网络连接异常，请刷新页面或稍后重试！', 1, 5);
					}
				});
			});  
}
function validationData1(){
	 
	   if($('#autoTenderType1').is(":checked")){
		     var re = /^[0-9]*[0-9]$/i; //校验是否为数字
		     var tenderMoney=$('#tenderMoney').val();
		     
			 if(re.test(tenderMoney)){
				 if(tenderMoney%100==0 && tenderMoney/100>0){
					 $('#tenderMoney').val(tenderMoney);
				 }else{
					 layer.msg("请输入100的整数倍", 2, 5);
					 $('#tenderMoney').val('');
					 return false;
				 }
				 
			 } else{
				 layer.msg("最低100元且为100元整数倍", 2, 5);
				 return false;
			 }
		}
	     
		 //赋值不启用
		 if(!$("#statusCB1").is(":checked")){
			 $("#status1").val('0');
		 } 
		 //赋值不适用活期宝
		 if(!$('#isUseCurCB').is(":checked")){
			 $('#isUseCur').val('0');
		 }
		 return true;
}


//投标方式
$("#autoTenderType1").change(function(){
	if($(this).is(":checked")){
		$("#tenderMoney").removeAttr("disabled");
		if('${fixAutoInvest.tenderMoney}'>0){
		$("#tenderMoney").val('${fixAutoInvest.tenderMoney}');
		}
	}
});
$("#autoTenderType2").change(function(){
	if($(this).is(":checked")){
		$("#tenderMoney").attr("disabled","disabled");
		$("#tenderMoney").val('');
	}
});

function closeFixWin(){ 
	$('#setFix').trigger('reveal:close'); 
	} 
</script >
  
 