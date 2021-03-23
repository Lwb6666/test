<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/page/common/taglib.jsp"%>
<%@ include file="/WEB-INF/page/common/public.jsp"%>

<div class="cl_dow">
	<form  name="autoInvest_form" id="autoInvest_form" method="post"  action="${basePath}/myaccount/autoInvest/saveAutoInvest.html" >
	<c:if test="${autoInvestConfig != null }">
		<input type="hidden" name="id" id="autoInvestConfigId" value="${autoInvestConfig.id}" />
		<input type="hidden" name="autoType" id="autoType" value="${autoInvestConfig.autoType}" />
		<input type="hidden" name="isUseCur" id="isUseCur" value="${autoInvestConfig.isUseCur}" />
	</c:if>
	<c:if test="${autoInvestConfig == null }">		
		<input type="hidden" name="isUseCur" id="isUseCur" />
	</c:if>
	<div class="title-items">
	     <h2><b>生效状态</b></h2>
	     <b class="line"></b>
	</div >
    <div class="autobid" style="margin-top:0; padding-bottom:60px;">
        <dl>     
        	 <dd><span><font color="#ff0000">*</font>自动类型：</span>
        	 	 <select <c:if test="${autoInvestConfig == null }">name="autoType"</c:if> id="autoType" style="width:230px;" <c:if test="${autoInvestConfig != null }">disabled="disabled"</c:if>>
        	 	 	<option value="1" <c:if test="${autoInvestConfig.autoType == 1}">selected="selected"</c:if>>按抵押标、担保标、信用标投标</option>
                  </select>
        	 </dd>   	 
             <dd><span>状态：</span><input id="status" type="checkbox" name="status" value="0"> 是否启用</dd>
             <dd><span><font color="#ff0000">*</font>最低投标金额：</span><input id="min_tender_account" type="text" name="min_tender_account" maxlength="8">元</dd>
             <dd><span><font color="#ff0000">*</font>投标方式：</span>
	             <div>
	             	<input type="radio" id="tender_type_1" name="tender_type" value="1" checked="checked" />按金额投标
	             	<input name="tender_account_auto" type="text" maxlength="8" id="tender_account_auto" value="">元（最少50元）	             	
	             </div>
            	 <div style="padding :10px 0 10px 13.5%;"> 
            	    <input type="radio" id="tender_type_2" name="tender_type" value="2" />按比例投标
             		<input name="tender_scale" id="tender_scale" type="text" value="" maxlength="5" />%&nbsp;&nbsp;
                 </div>                
                 <div style="padding-left:13.5%;"> 
                	<input type="radio" id="tender_type_3" name="tender_type" value="3" />按账户余额投标                	
                 </div>
                 <div style="padding-left:9.5%; border-top:1px #ccc dotted; width:40%; padding-top:5px; margin-top:5px;">                
                	<label style="color:red;"><input id="isUseCur_money" style="margin-left:38px;" type="checkbox" checked="checked" />以上所选方式金额不足时使用活期宝</label>
                 </div>
             </dd> 
             <dd></dd>        
        </dl>
    </div>
    <div class="title-items">
        <h2><b>标的信息限制</b></h2>
        <b class="line"></b>
    </div >
    <div class="autobid">
        <dl>
			<dd>
				<span>还款方式：</span>
				<input type="hidden" id="borrow_style_status" name="borrow_style_status" value="0"/>
                <input name="checkbox" type="checkbox" id="borrow_style_checkbox" />
				<select name="borrow_type" id="borrow_type">
	                <option value="1">等额本息</option>
	                <option value="2">按月付息到期还本</option>
	                <option value="3">到期还本付息 </option>
	                <option value="4">按天还款</option>
	            </select>
	            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;不选中则没有此项限制
			</dd>
			<dd>
				<span><font color="#ff0000">*</font>借款期限：</span>
				<input name="timelimit_status" id="timelimit_status_0" type="checkbox" value="0" />
				不限制期限
			</dd>
			<dd id="monthtimelimit">
			     <span>&nbsp;</span>
			     <input name="checkbox" type="checkbox" id="timelimit_status_1" />
			    	 按月范围
			     <select name="min_time_limit" id="min_time_limit" >
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
                      ~ 
				  <select name="max_time_limit" id="max_time_limit" >
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
			     &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;只对按月还款、按季还款有效
			  </dd>
			  <dd id="daytimelimit">
			      <span>&nbsp;</span>
			      <input type="checkbox" id="timelimit_status_2"/>
			  	    按天范围&nbsp;
			      <input type="text" value="" id="min_day_limit" name="min_day_limit" size="6" onkeyup=""   />
               	    天~ 
                  <input type="text" value="" id="max_day_limit" name="max_day_limit" size="6" onkeyup=""   />
               	    天&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;只对一次性还本付息有效
			</dd>
			<dd><span>利率范围：</span>
			     <input type="checkbox" id="apr_status" />
			     <input type="text" id="min_apr" name="min_apr" size="4"   />
                 % ~ 
                 <input type="text" id="max_apr" name="max_apr" size="4"  />
                 %
			 </dd>
			 <dd><span><font color="#ff0000">*</font>标的类型：</span>
			 	<label id="borrow_type2_status_label">
			    <input type="checkbox" id="borrow_type2_status" name="borrow_type2_status" value="0" />抵押标&nbsp;&nbsp;
			    </label>
			    <%-- <label id="borrow_type3_status_label">
			    <input type="checkbox" id="borrow_type3_status" name="borrow_type3_status" value="0" />净值标&nbsp;&nbsp;
			    </label> --%>
			    <label id="borrow_type1_status_label">
			    <input type="checkbox" id="borrow_type1_status" name="borrow_type1_status"  value="0" />信用标&nbsp;&nbsp;
			    </label>
			    <label id="borrow_type5_status_label">
			    <input type="checkbox" id="borrow_type5_status" name="borrow_type5_status"  value="0" />担保标
			    </label>
			</dd>  
        </dl>
    </div>
<div class="gg_btn"><input id="autoInvest_btn" type="button" value="提交" onclick="autoInvestSumbit();" style="cursor:pointer;"/></div>
</form>
<div class="tip">
	<h3>温馨提示：</h3>
	<p>1、当按金额投标时，系统会根据您设置的投标金额来进行投标，且投标金额必须大于等于单笔最低投标额度，否则无法投标。 </p>
	<p>2、当按比例投标时，系统会根据您设置的投标比例*标的借款金额来进行投标， 且该金额必须大于等于单笔最低投标额度，否则无法投标。 </p>
	<p>3、当按账户余额投标时，系统会根据您当前的余额来进行投标， 且余额必须大于等于单笔最低投标额度，否则无法投标。  </p>
	<p>4、借款期限必须选中一项，当选中不限定借款期限范围时，则其余两项不可选</p>
</div>
</div>
<script type="text/javascript">
$(function(){
	$("#auto_invest_add").addClass("current");
	var id = '${autoInvestConfig.id}';
	if(id>0){
		var status = '${autoInvestConfig.status}';
		if(status == '1'){
			$("#status").attr("checked",true);
		}
		
		$("#min_tender_account").val('${autoInvestConfig.min_tender_account}');
		
		var tender_type = '${autoInvestConfig.tender_type}';
		var is_use_cur = '${autoInvestConfig.isUseCur}';
		if(tender_type=='1'){
			$("#tender_type_1").attr("checked",true);
			$("#tender_account_auto").val('${autoInvestConfig.tender_account_auto}');
			$("#tender_scale").val('');
			$("#tender_scale").attr("disabled","disabled");			
		}else if(tender_type=='2'){
			$("#tender_type_2").attr("checked",true);
			$("#tender_scale").val(new Number('${autoInvestConfig.tender_scale}'));
			$("#tender_account_auto").val('');
			$("#tender_account_auto").attr("disabled","disabled");			
		}else if(tender_type=='3'){
			$("#tender_type_3").attr("checked",true);
			$("#tender_account_auto").val('');
			$("#tender_scale").val('');
			$("#tender_account_auto").attr("disabled","disabled");
			$("#tender_scale").attr("disabled","disabled");			
		}
		if(is_use_cur=='1'){
			$("#isUseCur_money").attr("checked",true);			
		}else{
			$("#isUseCur_money").attr("checked",false);			
		}
		
		var borrow_type = '${autoInvestConfig.borrow_type}';
		if(borrow_type > 0){
			$("#borrow_style_checkbox").attr("checked",true);
			$("#borrow_type").val(borrow_type);
		}
		borrowType(borrow_type);
		var timelimit_status = '${autoInvestConfig.timelimit_status}';
		if(timelimit_status == '1'){
			$("#timelimit_status_0").attr("checked",true);
			
			$("#timelimit_status_1").attr("checked",false);
			$("#timelimit_status_1").attr("disabled","disabled");
			$("#timelimit_status_2").attr("checked",false);
			$("#timelimit_status_2").attr("disabled","disabled");
			
			$("#min_time_limit").attr("disabled","disabled");
			$("#max_time_limit").attr("disabled","disabled");
			$("#min_day_limit").attr("disabled","disabled");
			$("#max_day_limit").attr("disabled","disabled");
		}
		
		var min_time_limit = '${autoInvestConfig.min_time_limit}';
		if(min_time_limit > 0){
			$("#timelimit_status_1").attr("checked",true);
			$("#min_time_limit").val(min_time_limit);
			$("#max_time_limit").val('${autoInvestConfig.max_time_limit}');
		}else{
			$("#min_time_limit").attr("disabled","disabled");
			$("#max_time_limit").attr("disabled","disabled");
		}
		var min_day_limit = '${autoInvestConfig.min_day_limit}';
		if(min_day_limit > 0){
			$("#timelimit_status_2").attr("checked",true);
			$("#min_day_limit").val(min_day_limit);
			$("#max_day_limit").val('${autoInvestConfig.max_day_limit}');
		}else{
			$("#min_day_limit").attr("disabled","disabled");
			$("#max_day_limit").attr("disabled","disabled");
		}
		var min_apr = '${autoInvestConfig.min_apr}';
		if(min_apr > 0){
			$("#apr_status").attr("checked",true);
			$("#min_apr").val(min_apr);
			$("#max_apr").val('${autoInvestConfig.max_apr}');
		}else{
			$("#min_apr").attr("disabled","disabled");
			$("#max_apr").attr("disabled","disabled");
		}
		var borrow_type1_status = '${autoInvestConfig.borrow_type1_status}';
		if(borrow_type1_status == '1'){
			$("#borrow_type1_status").attr("checked",true);
		}
		var borrow_type2_status = '${autoInvestConfig.borrow_type2_status}';
		if(borrow_type2_status == '1'){
			$("#borrow_type2_status").attr("checked",true);
		}
		var borrow_type3_status = '${autoInvestConfig.borrow_type3_status}';
		if(borrow_type3_status == '1'){
			$("#borrow_type3_status").attr("checked",true);
		}
		var borrow_type5_status = '${autoInvestConfig.borrow_type5_status}';
		if(borrow_type5_status == '1'){
			$("#borrow_type5_status").attr("checked",true);
		}
	}else{
		$("#borrow_type").attr("disabled","disabled");
		$("#min_time_limit").attr("disabled","disabled");
		$("#max_time_limit").attr("disabled","disabled");
		$("#min_day_limit").attr("disabled","disabled");
		$("#max_day_limit").attr("disabled","disabled");
		$("#min_apr").attr("disabled","disabled");
		$("#max_apr").attr("disabled","disabled");
		$("#tender_type_1").attr("checked",true);
		$("#tender_account_auto").val('${autoInvestConfig.tender_account_auto}');
		$("#tender_scale").val('');
		$("#tender_scale").attr("disabled","disabled");
	}
	
	//自动类型
	if($("#autoType").val() == 1){
		$("#borrow_type3_status_label").attr("style","display:none");
		$("#borrow_type")[0].length = 0; 
		$("#borrow_type")[0].add(new Option("等额本息",1));
		$("#borrow_type")[0].add(new Option("按月付息到期还本",2));
		$("#borrow_type")[0].add(new Option("到期还本付息",3));
		$("#borrow_type")[0].add(new Option("按天还款",4));
		if('${autoInvestConfig.borrow_type}' == '1'){
			$("#borrow_type")[0].options[0].setAttribute("selected","selected");  
		}
		if('${autoInvestConfig.borrow_type}' == '2'){
			$("#borrow_type")[0].options[1].setAttribute("selected","selected");  
		}
		if('${autoInvestConfig.borrow_type}' == '3'){
			$("#borrow_type")[0].options[2].setAttribute("selected","selected");  
		}
		if('${autoInvestConfig.borrow_type}' == '4'){
			$("#borrow_type")[0].options[3].setAttribute("selected","selected");  
		}
	}
	
	//自动投标方式 
	$("input[name='tender_type']").change(function(){
		var tender_type = $(this).val();
		if(tender_type == 1){
			$("#tender_scale").val('');
			$("#tender_scale").attr("disabled","disabled");
			$("#tender_account_auto").removeAttr("disabled");			
		}else if(tender_type == 2){
			$("#tender_scale").removeAttr("disabled");
			$("#tender_account_auto").val('');
			$("#tender_account_auto").attr("disabled","disabled");			
		}else if(tender_type == 3){
			$("#tender_account_auto").val('');
			$("#tender_scale").val('');
			$("#tender_account_auto").attr("disabled","disabled");
			$("#tender_scale").attr("disabled","disabled");			
		}
	});
	
	//还款方式复选框
	$("#borrow_style_checkbox").change(function(){
		if($(this).is(":checked")){
			$("#borrow_type").removeAttr("disabled");
			borrowType($("#borrow_type").val());
		}else{
			$("#borrow_type1_status").removeAttr("disabled");
			$("#borrow_type2_status").removeAttr("disabled");
			$("#borrow_type5_status").removeAttr("disabled");
			$("#monthtimelimit").removeAttr("style");
			$("#daytimelimit").removeAttr("style");
			$("#borrow_type").attr("disabled","disabled");
		}
	});
	
	//还款方式下拉框
	$("#borrow_type").change(function(){
		$("#borrow_style_checkbox").attr("checked",true);
		borrowType($("#borrow_type").val());
	});
	
	//无限制借款周期复选框
	$("#timelimit_status_0").change(function(){
		if($(this).is(":checked")){
			$("#timelimit_status_1").attr("checked",false);
			$("#timelimit_status_1").attr("disabled","disabled");
			$("#timelimit_status_2").attr("checked",false);
			$("#timelimit_status_2").attr("disabled","disabled");
			
			$("#min_time_limit").attr("disabled","disabled");
			$("#max_time_limit").attr("disabled","disabled");
			$("#min_day_limit").attr("disabled","disabled");
			$("#max_day_limit").attr("disabled","disabled");
		}else{
			$("#timelimit_status_1").removeAttr("disabled");
			$("#timelimit_status_2").removeAttr("disabled");
		}
	});
	
	//按月借款周期复选框
	$("#timelimit_status_1").change(function(){
		if($(this).is(":checked")){
			$("#min_time_limit").removeAttr("disabled","disabled");
			$("#max_time_limit").removeAttr("disabled","disabled");
		}else{
			$("#min_time_limit").attr("disabled","disabled");
			$("#max_time_limit").attr("disabled","disabled");
		}
	});
	
	//按天借款周期复选框
	$("#timelimit_status_2").change(function(){
		if($(this).is(":checked")){
			$("#min_day_limit").removeAttr("disabled","disabled");
			$("#max_day_limit").removeAttr("disabled","disabled");
		}else{
			$("#min_day_limit").attr("disabled","disabled");
			$("#max_day_limit").attr("disabled","disabled");
		}
	});
	
	//利率复选框
	$("#apr_status").change(function(){
		if($(this).is(":checked")){
			$("#min_apr").removeAttr("disabled","disabled");
			$("#max_apr").removeAttr("disabled","disabled");
		}else{
			$("#min_apr").attr("disabled","disabled");
			$("#max_apr").attr("disabled","disabled");
		}
	});
});

//还款方式处理
function borrowType(borrow_type){
	if(borrow_type == '0'){
		$("#borrow_type").attr("disabled","disabled");
	}else if(borrow_type == '1'){  //等额本息
		$("#borrow_type1_status").removeAttr("disabled");
		$("#borrow_type2_status").removeAttr("disabled");
		$("#borrow_type3_status").removeAttr("disabled");
		$("#borrow_type5_status").removeAttr("disabled");
		
		$("#timelimit_status_2").attr("checked",false);
		$("#min_day_limit").attr("disabled","disabled");
		$("#max_day_limit").attr("disabled","disabled");
		$("#daytimelimit").attr("style","display:none;");
		$("#min_day_limit").val("");
		$("#max_day_limit").val("");
		
		$("#monthtimelimit").removeAttr("style");
	}else if(borrow_type == '2'){  //按月付息到期还本
		$("#borrow_type3_status").attr("checked",false);
		$("#borrow_type3_status").attr("disabled","disabled");
		
		$("#borrow_type1_status").removeAttr("disabled");
		$("#borrow_type2_status").removeAttr("disabled");
		$("#borrow_type5_status").removeAttr("disabled");
	
		$("#timelimit_status_2").attr("checked",false);
		$("#min_day_limit").attr("disabled","disabled");
		$("#max_day_limit").attr("disabled","disabled");
		$("#min_day_limit").val("");
		$("#max_day_limit").val("");
		$("#daytimelimit").attr("style","display:none;");
		$("#monthtimelimit").removeAttr("style");
	}else if(borrow_type == '3'){  //到期还本付息
		$("#borrow_type3_status").attr("checked",false);
		$("#borrow_type3_status").attr("disabled","disabled");
		
		$("#borrow_type1_status").removeAttr("disabled");
		$("#borrow_type2_status").removeAttr("disabled");
		$("#borrow_type5_status").removeAttr("disabled");
		
		$("#timelimit_status_2").attr("checked",false);
		$("#min_day_limit").attr("disabled","disabled");
		$("#max_day_limit").attr("disabled","disabled");
		$("#min_day_limit").val("");
		$("#max_day_limit").val("");
		$("#daytimelimit").attr("style","display:none;");
		$("#monthtimelimit").removeAttr("style");
	}else if(borrow_type == '4'){  //按天还款
		$("#borrow_type1_status").removeAttr("disabled");
		$("#borrow_type2_status").removeAttr("disabled");
		$("#borrow_type3_status").removeAttr("disabled");
		$("#borrow_type5_status").removeAttr("disabled");
		
		$("#timelimit_status_1").attr("checked",false);
		$("#min_time_limit").attr("disabled","disabled");
		$("#max_time_limit").attr("disabled","disabled");
		$("#daytimelimit").removeAttr("style");
		$("#monthtimelimit").attr("style","display:none;");
	}
}
function autoInvestSumbit(){
	$("#autoInvest_btn").attr("onclick","#");
	var msg = "";
	if(verify()){
		if($("#status").is(":checked")){
			$("#status").val(1);
		}
		if($("#borrow_style_checkbox").is(":checked")){
			$("#borrow_style_status").val(1);
			var borrow_type = $("#borrow_type").val();
			if(borrow_type == 4){
				$("#min_time_limit").val(0);
				$("#max_time_limit").val(0);
			}else{
				$("#min_day_limit").val(0);
				$("#max_day_limit").val(0);
			}
		}else{
			$("#borrow_style_status").val(0);
		}
		if($("#timelimit_status_0").is(":checked")){
			$("#timelimit_status_0").val(1);
			$("#min_time_limit").val(0);
			$("#max_time_limit").val(0);
			$("#min_day_limit").val(0);
			$("#max_day_limit").val(0);
		}
		if(!$("#timelimit_status_1").is(":checked")){
			$("#min_time_limit").val(0);
			$("#max_time_limit").val(0);
		}
		if(!$("#timelimit_status_2").is(":checked")){
			$("#min_day_limit").val(0);
			$("#max_day_limit").val(0);
		}
		if(!$("#apr_status").is(":checked")){
			$("#min_apr").val('');
			$("#man_apr").val('');
		}
		if($("#borrow_type1_status").is(":checked")){
			$("#borrow_type1_status").val(1);
		}
		if($("#borrow_type2_status").is(":checked")){
			$("#borrow_type2_status").val(1);
		}
		if($("#borrow_type3_status").is(":checked")){
			$("#borrow_type3_status").val(1);
		}
		if($("#borrow_type4_status").is(":checked")){
			$("#borrow_type4_status").val(1);
		}
		if($("#borrow_type5_status").is(":checked")){
			$("#borrow_type5_status").val(1);
		}
		if($("#isUseCur_money").is(":checked")){
			$("#isUseCur").val(1);
		}else{
			$("#isUseCur").val(0);
		}		
		$("#autoInvest_form").submit();
	}else{
		$("#autoInvest_btn").attr("onclick","autoInvestSumbit();");
	}
}

function verify(){
	//金额的正则表达式
	var reg= /^(([1-9]\d{0,9})|0)(\.\d{1,2})?$/;
	var pattern= /^[0-9]+$/;
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
	var tender_type = '';
	$("input[name='tender_type']").each(function(){
		if($(this).is(":checked")){
			tender_type = $(this).val();
		}
	});
	if(tender_type == '1'){
		var tender_account_auto = $("#tender_account_auto").val();
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
		$("#tender_scale").val(0.00);
		
	}else if(tender_type == '2'){
		var tender_scale = $("#tender_scale").val();
		if(tender_scale.length==0){
			layer.msg("按比例投标的比例不能为空！", 2, 5);
			return false;
		}else{
			if(!reg.test(tender_scale)){
				layer.msg("按比例投标的比例不是正确的数字，提示：小数位只能为2位！", 2, 5);
				return false;
			}else{
				if(Number(tender_scale) <= 0 || Number(tender_scale) > 100){
					layer.msg("按比例投标的比例必须大于0%且小于等于100%", 2, 5);
					return false;
				}
			}
		}
	}else if(tender_type == '3'){
		//var use_money = '${account.use_money}';
		$("#tender_scale").val(0.00);
		/* if(Number(use_money) < Number(min_tender_account)){
			msg = msg + "-您的可用余额小于单笔最低投标额度！\n";
		} */
	}else{
		layer.msg("没有选择投标方式！", 2, 5);
		return false;
	}

	//按月限定借款周期
	if($("#timelimit_status_1").is(":checked")){
		var min_time_limit = $("#min_time_limit").val();
		var max_time_limit = $("#max_time_limit").val();
		if(parseInt(max_time_limit) < parseInt(min_time_limit)){
			layer.msg("最大借款月数不能小于最短借款月数！", 2, 5);
			return false;
		} 
	}
	
	//按天限定借款周期
	if($("#timelimit_status_2").is(":checked")){
		var min_day_limit = $("#min_day_limit").val();
		if(min_day_limit.length==0){
			layer.msg("最短借款天数不能为空！", 2, 5);
			return false;
		}else{
			if(!pattern.test(min_day_limit)){
				layer.msg("最短借款天数不是正整数！", 2, 5);
				return false;
			}else{
				if(parseInt(min_day_limit) < 1){
					layer.msg("最短借款天数不能小于1天！", 2, 5);
					return false;
				}
			}
		}
		var max_day_limit = $("#max_day_limit").val();
		if(max_day_limit.length==0){
			layer.msg("最长借款天数不能为空！", 2, 5);
			return false;
		}else{
			if(!pattern.test(max_day_limit)){
				layer.msg("最长借款天数不是正整数！", 2, 5);
				return false;
			}else{
				if(parseInt(max_day_limit) > 90){
					layer.msg("最长借款天数不能大于90天！", 2, 5);
					return false;
				}
				if(parseInt(max_day_limit) < parseInt(min_day_limit)){
					layer.msg("最长借款天数不能小于最短借款天数！", 2, 5);
					return false;
				}
			}
		}
	} 
	
	//收益率复选框
	if($("#apr_status").is(":checked")){
		var min_apr = $("#min_apr").val();
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
		var max_apr = $("#max_apr").val();
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
	
	if(!$("#timelimit_status_0").is(":checked") && !$("#timelimit_status_1").is(":checked") && !$("#timelimit_status_2").is(":checked")){
		layer.msg("借款期限必须选择一项！", 2, 5);
		return false;
	}
	if(!$("#borrow_type1_status").is(":checked") && !$("#borrow_type2_status").is(":checked") && !$("#borrow_type3_status").is(":checked") && !$("#borrow_type5_status").is(":checked")){
		layer.msg("标类型必须选择一项！", 2, 5);
		return false;
	}
	return true;
}
</script>