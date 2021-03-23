<%@ page language="java" contentType="text/html; charset=utf-8"
															 pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/page/common/taglib.jsp"%>
<!---------- ------------------------自动投标-弹层---------------------------------------------------->
<div class="toubiaobox">
<form id="autoInvestForm" method="post"> 
  <div class="tb-cancengbox">
    <ul >
      <li class="title-listbox2"> 投标金额限制 </li>
      <li class="listbox1" style="margin-top:15px;"><span>账户可用余额</span><span>
      <a class="money-tz" href="#"><fmt:formatNumber value="${accountVo.useMoney }" pattern="#,##0.00"/></a>元</span><a class="chongzhibtn" href="${basePath}/account/topup/toTopupIndex.html" target="_blank">充值</a></li>
      <li class="listbox3"> <font color="#ff0000">*</font><span style="font-size:14px;">投标方式</span>
        <input type="radio" checked="checked"  name="tender_type" id="tender_type1" value="1"/>
        <span>按金额投标</span>
        <input class="money-text" type="text" value="${autoInvestConfig.tender_account_auto }"  name="tender_account_auto"   maxlength="8" id="tender_account_auto"  />（最少50元）
      </li>
      <li class="listbox3">
        <input type="radio"  style="margin-left:70px;" name="tender_type" id="tender_type2" value="3"  />
        <span>按账户余额投标</span> </li>
      <li class="listbox3">
        <input type="checkbox" name="isUseCur" id="isUseCur_money" checked="checked" style="margin-left:70px;" value="1" />
        <span style="color:red;">使用活期宝金额</span> </li>
      <li class="listbox3"><font color="#ff0000">*</font> <span style="font-size:14px;">最低投标金额</span>
        <input class="money-text" id="min_tender_account" value="${autoInvestConfig.min_tender_account }"  name="min_tender_account" maxlength="8" type="text" />
      </li>
      <li class="title-listbox2" style="margin-top:15px;"> 标的信息限制 </li>
      <li class="listbox3"><font color="#ff0000">*</font><span style="font-size:14px;">还款方式</span>
        <input style="margin-left:20px;" type="radio" id="borrow_style_status1" checked="checked" name="borrow_style_status" value="0"/>
        <span>不限</span>
        <input  type="radio" style="margin-left:20px;" id="borrow_style_status2" name="borrow_style_status" value="1"/>
        <select class="select-box" name="borrow_type" id="borrow_type">
           <option value="1">等额本息</option>
           <option value="2">按月付息到期还本</option>
           <option value="3">到期还本付息 </option>
        </select>
      </li>
      <li class="listbox3"><font color="#ff0000">*</font><span style="font-size:14px;">借款期限</span>
        <input style="margin-left:20px;" type="radio" id="timelimit_status1" checked="checked" name="timelimit_status" value="1"/>
        <span>不限</span>
        <input  type="radio" style="margin-left:20px;" id="timelimit_status2" name="timelimit_status" value="0" />
		        <select class="select-box1" name="min_time_limit" id="min_time_limit" >
		         <option value="1">1个月</option>
		         <option value="2">2个月</option>
		         <option value="3">3个月</option>
		         <option value="4">4个月</option>
		         <option value="5">5个月</option>
		         <option value="6">6个月</option>
		         <option value="7">7个月</option>
		         <option value="8">8个月</option>
		         <option value="9">9个月</option>
		         <option value="10">10个月</option>
		         <option value="11">11个月</option>
		         <option value="12">12个月</option>
		        </select>
		        <span>—</span>
		        <select class="select-box1" name="max_time_limit" id="max_time_limit">
		          <option value="1">1个月</option>
		          <option value="2">2个月</option>
		          <option value="3">3个月</option>
		          <option value="4">4个月</option>
		          <option value="5">5个月</option>
		          <option value="6">6个月</option>
		          <option value="7">7个月</option>
		          <option value="8">8个月</option>
		          <option value="9">9个月</option>
		          <option value="10">10个月</option>
		          <option value="11">11个月</option>
		          <option value="12">12个月</option>
		        </select>
      </li>
      <li class="listbox3"> 

        <span style="font-size:14px;">&nbsp;&nbsp;利率范围</span>
        <input  type="text" id="min_apr" name="min_apr" value="${autoInvestConfig.min_apr }" class="textbox5" style="margin-left:20px;" size="4"/>
        <span>~</span>
        <input id="max_apr" name="max_apr" type="text" value="${autoInvestConfig.max_apr }" class="textbox5" size="4" />
      </li>
      <li class="title-listbox6"> </li>
      <li class="listbox3" style="margin-top:10px;"  >
        <div style="float:left; padding-top:10px;">
          <input type="checkbox" id="statusCB"  name="statusCB" <c:if test="${empty autoInvestConfig.status or autoInvestConfig.status==1}">checked="checked"</c:if> />
          <span style="color:#06C;">立即启用</span></div>
        <span><a class="SaveSettings" href="javascript:void(0);" onclick="autoInvestSumbit();" >保存设置</a></span> </li>
    </ul>
    <div class="clear"></div>
  </div>
  <input type="hidden" name="id" value="${autoInvestConfig.id}"/>
  <input type="hidden" name="status" id="status" value="0"/>
  </form>
</div>
<script type="text/javascript">
 $(function(){
	 var id = '${autoInvestConfig.id}';
	 if(id>0){
		 var tender_type='${autoInvestConfig.tender_type}';
		 if(tender_type=='3'){
			 $("#tender_account_auto").attr("disabled","disabled");
			 $("#tender_account_auto").val('');
			 $("#tender_type2").attr("checked",true);
			 $("#tender_type1").attr("checked",false);
		 }else if(tender_type=='2'){
			 layer.msg('您当前的投标方式是【按比例投标】，该投标方式已失效，请重新设置',2,3);
		 }else{
			 $("#tender_type1").attr("checked",true);
			 $("#tender_type2").attr("checked",false);
		 } 
		 var is_use_cur = '${autoInvestConfig.isUseCur}';
		 if(is_use_cur=='1'){
			$("#isUseCur_money").attr("checked",true);			
		 }else{
			$("#isUseCur_money").attr("checked",false);			
		 }
		 var timelimit_status='${autoInvestConfig.timelimit_status}';
		 if(timelimit_status=='1'){
			 $("#timelimit_status1").attr("checked",true);
			 $("#timelimit_status2").attr("checked",false);
			 $("#min_time_limit").attr("disabled","disabled");
			 $("#max_time_limit").attr("disabled","disabled");	
			 $("#min_time_limit").val("1");
			 $("#max_time_limit").val("1");
		 }else{
			 $("#timelimit_status1").attr("checked",false);
			 $("#timelimit_status2").attr("checked",true);
			 $("#min_time_limit").val("${autoInvestConfig.min_time_limit}");
			 $("#max_time_limit").val("${autoInvestConfig.max_time_limit}");
		 }
		 var borrow_style_status='${autoInvestConfig.borrow_type}';
		 
		 if(borrow_style_status=='0'){
			 $("#borrow_style_status1").attr("checked",true);
			 $("#borrow_style_status2").attr("checked",false);
			 $("#borrow_type").attr("disabled","disabled");
		 }else{
			 $("#borrow_style_status1").attr("checked",false);
			 $("#borrow_style_status2").attr("checked",true);
			 $("#borrow_type").val(borrow_style_status);
		 }
		 
		 
	 }else{
		 $("#min_time_limit").attr("disabled","disabled");
		 $("#max_time_limit").attr("disabled","disabled");
		 $("#borrow_type").attr("disabled","disabled");
	 }
 });
//借款期限
$("#timelimit_status1").change(function(){
	if($(this).is(":checked")){
		$("#min_time_limit").attr("disabled","disabled");
		$("#max_time_limit").attr("disabled","disabled");	 
	}
});
$("#timelimit_status2").change(function(){
	if($(this).is(":checked")){
		$("#min_time_limit").removeAttr("disabled");
		$("#max_time_limit").removeAttr("disabled"); 
	} 
});
//还款方式
$("#borrow_style_status1").change(function(){
	if($(this).is(":checked")){
		$("#borrow_type").attr("disabled","disabled");
	}
});
$("#borrow_style_status2").change(function(){
	if($(this).is(":checked")){
		$("#borrow_type").removeAttr("disabled");
	}
});
//投标方式
$("#tender_type1").change(function(){
	if($(this).is(":checked")){
		$("#tender_account_auto").removeAttr("disabled");
		if('${autoInvestConfig.tender_account_auto}'>0){
		$("#tender_account_auto").val('${autoInvestConfig.tender_account_auto}');
		}
	}
});
$("#tender_type2").change(function(){
	if($(this).is(":checked")){
		$("#tender_account_auto").attr("disabled","disabled");
		$("#tender_account_auto").val('');
	}
});
//加载提示
var _load ;
 
 function autoInvestSumbit(){
	 //验证数据
	 if(!validationData()){
		 
		 return;
	 }
	 
	 //提交表单
	 layer.confirm('你将设置自动投标', function(){
		 $("#autoInvestForm").ajaxSubmit({
				url : '${basePath}/myaccount/autoInvest/addAutoInvest.html',
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
							autoInvestList();
						});
					}
				},
				error : function(data) {
					layer.msg('网络连接异常，请刷新页面或稍后重试！', 1, 5);
				}
			});
		});  
 }
 //验证数据
 function validationData(){
	    //金额的正则表达式
		var reg= /^(([1-9]\d{0,9})|0)(\.\d{1,2})?$/;
		var pattern= /^[0-9]+$/;
		//最低投标额度额度
		var min_tender_account = $("#min_tender_account").val();
		if(min_tender_account.length==0){
			layer.msg("单笔最低投标额度不能为空！", 2, 5);
			return false;
		}else{
			if(!reg.test(min_tender_account)){
				layer.msg("单笔最低投标额度不是正确的金额，提示：小数位只能为2位！", 2, 5);
				return false;
			}
			
		}
		//投标金额验证
		var tender_account_auto = $("#tender_account_auto").val();
		if($('#tender_type1').is(":checked")){
			if(tender_account_auto.length==0){
				layer.msg("投标金额不能为空！", 2, 5);
				return false;
			}else{
				if(!reg.test(tender_account_auto)){
					layer.msg("投标金额不是正浮点数，提示：小数位只能为2位！", 2, 5);
					return false;
				}else{
					if(Number(tender_account_auto) < 50){
						layer.msg("投标金额不能小于￥50！", 2, 5);
						return false;
					}
					if(Number(tender_account_auto) < Number(min_tender_account)){
						layer.msg("投标金额不能小于单笔最低投标额度！", 2, 5);
						return false;
					} 
				}
			}
		} 
		
		//借款期限验证
		var min_time_limit = $("#min_time_limit").val();
		var max_time_limit = $("#max_time_limit").val();
		if(parseInt(max_time_limit) < parseInt(min_time_limit)){
			layer.msg("最大借款月数不能小于最短借款月数！", 2, 5);
			return false;
		} 
		//检验
		var min_apr = $("#min_apr").val();
		var max_apr = $("#max_apr").val();

		if(!(max_apr.length == 0 && min_apr.length==0)){
			
			if(min_apr.length==0){
				layer.msg("最小年化收益率不能为空！", 2, 5);
				return false;
			}else{
				if(!reg.test(min_apr)){
					layer.msg("最小年化收益率不是正整数！", 2, 5);
					return false;
				}else{
					if(Number(min_apr) <= 0){
						layer.msg("最小年化收益率不能小于0%！", 2, 5);
						return false;
					}
				}
			}
			 
			if(max_apr.length == 0){
				layer.msg("最大年化收益率不能为空！", 2, 5);
				return false;
			}else{
				if(!reg.test(max_apr)){
					layer.msg("最大年化收益率不是正整数！", 2, 5);
					return false;
				}else{
					if(Number(max_apr) > 24){
						layer.msg("最大年化收益率不能大于24%！", 2, 5);
						return false;
					}
					if(Number(max_apr) < Number(min_apr)){
						layer.msg("最大年化收益率不能小于最小年化收益率！", 2, 5);
						return false;
					}
				}
			}
		}
		 //赋值启用值
		 if($("#statusCB").is(":checked")){
			 $("#status").val(1);
		 }else{
			 $("#status").val(0);
		 }
		 //赋值启用
		 if(!$('#isUseCur_money').is(":checked")){
			 $('#isUseCur_money').val(0);
		 }
	    return true;
 }
</script>          
<!---------- ------------------------自动投标-弹层---------------------------------------------------->  