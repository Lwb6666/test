<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/page/common/taglib.jsp"%>
<!---------- ------------------------自动投标-弹层---------------------------------------------------->
<form id="autoInvestForm">
<div class="toubiaobox" >
            <div class="tb-cancengbox">
              <ul >
                <li class="listbox1"><span>账户可用余额</span><span><a class="money-tz" href="#"><fmt:formatNumber value="${useMoney }" pattern="#,##0.00"/></a>元</span><a class="chongzhibtn" href="${basePath}/account/topup/toTopupIndex.html" target="_blank">充值</a></li>
                <li class="title-listbox2"> 投宝方式 </li>
                <li class="listbox3">
                  <input type="radio" checked="checked" name="autoTenderType" id="autoTenderType1"  value="1"/>
                  <span>按金额投宝</span>
                  <input class="money-text" type="text" id="tenderMoney" maxlength="10" value='${fixAutoInvest.tenderMoney }' name="tenderMoney" />
                  <span class="tz-texttishi">最低100元且为100元整数倍</span> </li>
                <li class="listbox3">
                  <input type="radio" name="autoTenderType" id="autoTenderType2"  value="2" />
                  <span>按账户余额投宝</span> <span class="tz-texttishi">余额的100元整数倍部分</span> </li>
                <li class="listbox3">
                  <input type="checkbox" id="isUseCurCB" name="isUseCurCB" value="1" checked="checked"/>
                  <input type="hidden" name="isUseCur" id="isUseCur" value="1"/>
                  <span style="color: red;">使用活期宝金额</span> </li>
                <li class="title-listbox2" style="margin-top:15px;"> 定期宝期限
                  <input style="margin-left:25px;" type="checkbox" id="fixLimit1" name="fixLimits" value="1"/>
                  <span class="yunbao">1月宝</span>
                  <input type="checkbox" id="fixLimit3" name="fixLimits" value="3" />
                  <span class="yunbao">3月宝</span>
                  <input type="checkbox"  id="fixLimit6" name="fixLimits" value="6"/>
                  <span class="yunbao">6月宝</span>  
                   <input type="checkbox"   id="fixLimit12" name="fixLimits" value="12"/>
                  <span class="yunbao">12月宝</span> 
                 </li>
                <li class="listbox3" style="margin-top:30px;"  >
                  <div style="float:left; padding-top:10px;">
                    <input type="checkbox" checked="checked" name="statusCB" value="1" id="statusCB" />
                    <input type="hidden" name="status" id="status" value="1"/>
                    <span style="color:#06C;">立即启用</span></div>
                  <span><a class="SaveSettings" href="javascript:void(0);" onclick="save();">保存设置</a></span> </li>
              </ul>
    <div class="clear"></div>
  </div>
</div>
<input type="hidden" value="${fixAutoInvest.id }" name="id"/>
<input type="hidden" value="${fixAutoInvest.uptime }" name="uptime"/>
</form>
<script type="text/javascript">
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
					autoInvestFix();
				});
		   }
	   }
   });
   var _load;
   function save(){
	      //验证数据
		 if(!validationData()){
			 
			 return;
		 }
	     //提交表单
		 layer.confirm('你将设置自动投宝', function(){
			 $("#autoInvestForm").ajaxSubmit({
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
   function validationData(){
	 
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
		 if(!$("#statusCB").is(":checked")){
			 $("#status").val('0');
		 } 
		 //赋值不适用活期宝
		 if(!$('#isUseCurCB').is(":checked")){
			 $('#isUseCur').val('0');
		 }
		 if(!mycheckbox()){
			layer.msg("至少选中一期定期宝 ", 2, 5);
			return false; 
		 }
		 return true;
   }
   //是否被选中验证有选中的return true,否return false
   function mycheckbox() {
	   var falg = 0;
	   $("input[name='fixLimits']:checkbox").each(function () {
		   if ($(this).attr("checked")) {
		    falg += 1;
	       }
	   })
	   if (falg > 0){
	    return true;
	   }else{
	    return false;
	   }
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
</script >
  
 