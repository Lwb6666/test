<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/page/common/taglib.jsp"%>
<%@ include file="/WEB-INF/page/common/public.jsp"%>

<div class="cl_dow">
	<c:if test="${autoInvestConfig != null }">
		<input type="hidden" name="id" id="autoInvestConfigId" value="${autoInvestConfig.id}" />
		<input type="hidden" name="isUseCur" id="isUseCur" value="${autoInvestConfig.isUseCur}" />
	</c:if>
	<div class="title-items">
	     <h2><b>生效状态</b></h2>
	     <b class="line"></b>
	</div >
    <div class="autobid" style="margin-top:0; padding-bottom:60px;">
        <dl>     
        	 <dd><span>自动类型：</span>
        	 	 <select name="autoType" id="autoType" style="width:180px;" disabled="disabled">
                    <option value="1" <c:if test="${autoInvestConfig.autoType == 1}">selected="selected"</c:if>>按抵押标、担保标、信用标投标</option>
                  </select>
        	 </dd>   	 
             <dd><span>状态：</span><input id="status" type="checkbox" name="status" value="0" disabled="disabled"> 是否启用</dd>
             <dd><span>最低投标金额：</span><input id="min_tender_account" type="text" name="min_tender_account" maxlength="10" disabled="disabled">元</dd>
             <dd><span>投标方式：</span>
             	<div>
	             	<input type="radio" id="tender_type_1" name="tender_type" value="1" checked="checked" disabled="disabled"/>按金额投标 
             		<input name="tender_account_auto" type="text" maxlength="8" id="tender_account_auto" value="" disabled="disabled">元（最少50元）	             	
	             </div>
            	 <div style="padding :10px 0 10px 13.5%;"> 
            	    <input type="radio" id="tender_type_2" name="tender_type" value="2" disabled="disabled"/>按比例投标
             		<input name="tender_scale" id="tender_scale" type="text" value="" maxlength="5" disabled="disabled"/>%&nbsp;&nbsp;
                 </div>                
                 <div style="padding-left:13.5%;"> 
                	<input type="radio" id="tender_type_3" name="tender_type" value="3" disabled="disabled"/>按账户余额投标                	
                 </div>
                 <div style="padding-left:9.5%; border-top:1px #ccc dotted; width:40%; padding-top:5px; margin-top:5px;">                
                	<label style="color:red;"><input id="isUseCur_money" style="margin-left:38px;" type="checkbox" disabled="disabled" />以上所选方式金额不足时使用活期宝</label>
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
                <input name="checkbox" type="checkbox" id="borrow_style_checkbox" disabled="disabled"/>
				<select name="borrow_type" id="borrow_type" disabled="disabled">
	                <option value="1">等额本息</option>
	                <option value="2">按月付息到期还本</option>
	                <option value="3">到期还本付息 </option>
	                <option value="4">按天还款</option>
	            </select>
	            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;不选中则没有此项限制
			</dd>
			<dd>
				<span>借款期限：</span>
				<input name="timelimit_status" id="timelimit_status_0" type="checkbox" value="0" disabled="disabled"/>
				不限制期限
			</dd>
			<dd id="monthtimelimit">
			     <span>&nbsp;</span>
			     <input name="checkbox" type="checkbox" id="timelimit_status_1" disabled="disabled"/>
			    	 按月范围
			     <select name="min_time_limit" id="min_time_limit" disabled="disabled">
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
				  <select name="max_time_limit" id="max_time_limit" disabled="disabled">
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
			      <input type="checkbox" id="timelimit_status_2" disabled="disabled"/>
			  	    按天范围&nbsp;
			      <input type="text" value="" id="min_day_limit" name="min_day_limit" size="6" onkeyup="" disabled="disabled" />
               	    天~ 
                  <input type="text" value="" id="max_day_limit" name="max_day_limit" size="6" onkeyup="" disabled="disabled"/>
               	    天&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;只对一次性还本付息有效
			</dd>
			<dd><span>利率范围：</span>
			     <input type="checkbox" id="apr_status" disabled="disabled"/>
			     <input type="text" id="min_apr" name="min_apr" size="4" disabled="disabled"/>
                 % ~ 
                 <input type="text" id="max_apr" name="max_apr" size="4" disabled="disabled"/>
                 %
			 </dd>
			 <dd><span>标的类型：</span>
			    <label id="borrow_type2_status_label">
			    <input type="checkbox" id="borrow_type2_status" name="borrow_type2_status" value="0" disabled="disabled"/>抵押标&nbsp;&nbsp;
			    </label>
			    <%-- <label id="borrow_type3_status_label">
			    <input type="checkbox" id="borrow_type3_status" name="borrow_type3_status" value="0" disabled="disabled"/>净值标&nbsp;&nbsp;
			    </label> --%>
			    <label id="borrow_type1_status_label">
			    <input type="checkbox" id="borrow_type1_status" name="borrow_type1_status"  value="0" disabled="disabled"/>信用标&nbsp;&nbsp;
			    </label>
			    <label id="borrow_type5_status_label">
			    <input type="checkbox" id="borrow_type5_status" name="borrow_type5_status"  value="0" disabled="disabled"/>担保标
			    </label>
			</dd>  
        </dl>
    </div>
<div class="gg_btn"><input id="autoInvest_btn" type="button" value="修改" onclick="toUpdateAutoInvest(${autoInvestConfig.id});" style="cursor:pointer;"/></div>
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
			$("#tender_scale").val('${autoInvestConfig.tender_scale}');
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
	}

	//自动类型
	if($("#autoType").val() == 1){
		$("#borrow_type1_status_label").attr("disabled","disabled");
		$("#borrow_type2_status_label").attr("disabled","disabled");
		$("#borrow_type5_status_label").attr("disabled","disabled");
		
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
});

//还款方式处理
function borrowType(borrow_type){
	if(borrow_type == '0'){
		$("#borrow_type").attr("disabled","disabled");
	}else if(borrow_type == '1'){
		$("#borrow_type1_status").removeAttr("disabled");
		$("#borrow_type2_status").removeAttr("disabled");
		$("#borrow_type3_status").removeAttr("disabled");
		$("#borrow_type5_status").removeAttr("disabled");
		
		$("#timelimit_status_2").attr("checked",false);
		$("#min_day_limit").attr("disabled","disabled");
		$("#max_day_limit").attr("disabled","disabled");
		$("#daytimelimit").attr("style","display:none;");
		
		$("#monthtimelimit").removeAttr("style");
	}else if(borrow_type == '2'){
		$("#borrow_type3_status").attr("checked",false);
		$("#borrow_type3_status").attr("disabled","disabled");
		
		$("#borrow_type1_status").removeAttr("disabled");
		$("#borrow_type2_status").removeAttr("disabled");
		$("#borrow_type5_status").removeAttr("disabled");
	
		$("#timelimit_status_2").attr("checked",false);
		$("#min_day_limit").attr("disabled","disabled");
		$("#max_day_limit").attr("disabled","disabled");
		$("#daytimelimit").attr("style","display:none;");
		$("#monthtimelimit").removeAttr("style");
	}else if(borrow_type == '3'){
		$("#borrow_type3_status").attr("checked",false);
		$("#borrow_type3_status").attr("disabled","disabled");
		
		$("#borrow_type1_status").removeAttr("disabled");
		$("#borrow_type2_status").removeAttr("disabled");
		$("#borrow_type5_status").removeAttr("disabled");
		
		$("#timelimit_status_2").attr("checked",false);
		$("#min_day_limit").attr("disabled","disabled");
		$("#max_day_limit").attr("disabled","disabled");
		$("#daytimelimit").attr("style","display:none;");
		$("#monthtimelimit").removeAttr("style");
	}else if(borrow_type == '4'){
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

/**
 * 修改自动投标 
 */
 function toUpdateAutoInvest(id){
	 $("#autoTip").html("修改自动投标");
	 $("#add_auto_invest").addClass("men_li");
	 $("#auto_invest_list").removeClass("men_li");
	 $("#auto_invest_record").removeClass("men_li");
	 $.ajax({
			url : '${basePath}/myaccount/autoInvest/toAutoInvestUpdate/'+id+'.html',
			data :{
			} ,
			type : 'post',
			dataType : 'text',
			success : function(data){
				$("#main_content").html(data);
			},
			error : function(data) {
				layer.msg('网络连接异常，请刷新页面或稍后重试！', 1, 5);
		    }
	});
}
</script>