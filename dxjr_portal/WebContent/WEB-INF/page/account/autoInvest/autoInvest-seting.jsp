<%@ page language="java" contentType="text/html; charset=utf-8"
															 pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/page/common/taglib.jsp"%>
<!---------- ------------------------自动投标-弹层---------------------------------------------------->
<script type="text/javascript">
	$(function(){
		$('#tip-bottom').poshytip({
			className: 'tip-yellowsimple',
			showTimeout: 1,
			alignTo: 'target',
			alignX: 'center',
			alignY: 'bottom',
			offsetX: 15,
			offsetY: 10,
			allowTipHover: false,
		});
		$(".close").click(function(){
		$(".bankcard-tip").fadeOut();
		});
		$("#copeUrl").html('${basePath}${recommendPath}');
	});
</script>
<form id="autoInvestForm" method="post"> 
	<div class="cont-word cont-w02">
    	<div class="title"><h4>自动投标设置<span class="red"></span></h4><a  onclick="closeWin();" class="icon icon-close fr"></a></div>
        <div class="form-info-layer">
         	<div class="form-col2">
        	  <label class="colleft02 form-lable">投标类型</label>
        	  <span class="fl mt5">
	        	  <input  type="checkbox"  id="fcg" onclick="iscg()"><em>非存管</em>
	              <c:if test="${eUserInfo!=null}">
	               <input  type="checkbox"  id="cg" onclick="iscg()"> <em>存管</em>
	              </c:if>
	              <c:if test="${eUserInfo==null}">
	               <input  type="checkbox" disabled="disabled" id="cg"> <em>存管</em>
	              </c:if>
	             
	              <input type="hidden" name="custodyFlag" id="custodyFlag" value="${autoInvestConfig.custodyFlag}">
	               <c:if test="${eUserInfo==null}">
	               <a href="${path}/AccountSafetyCentre/safetyIndex.html" class="blue ml20">立即开通存管账户</a>
	               </c:if>
              </span>
            </div> 
        	<div class="form-col2">
                <label for="" class="colleft02 form-lable">账户可用余额</label>
                <span class="fl money"><strong class="f14 orange2" id="useMoney">
               	<c:choose>
               		<c:when test="${autoInvestConfig.custodyFlag==1}">
               		<fmt:formatNumber value="${accountVo.useMoney}" pattern="#,##0.00"/></strong>
               		</c:when>
               		<c:when test="${autoInvestConfig.custodyFlag==2}">
               		<fmt:formatNumber value="${accountVo.eUseMoney }" pattern="#,##0.00"/></strong>
               		</c:when>
               		<c:when test="${autoInvestConfig.custodyFlag==3}">
               		<fmt:formatNumber value="${accountVo.useMoney+accountVo.eUseMoney }" pattern="#,##0.00"/></strong>
               		</c:when>
               		<c:otherwise>
               		<fmt:formatNumber value="${accountVo.useMoney+accountVo.eUseMoney }" pattern="#,##0.00"/></strong>
               		</c:otherwise>
               	</c:choose>
                	元<a href="${basePath}/account/topup/toTopupIndex.html" target="_blank" class="blue ml20">充值</a></span>
            </div>
            <div class="form-col2">
                <label for="" class="colleft02 form-lable">投标方式 </label>
                <div class="form-box fl">
	               <p class="f14 clearboth">
		                <span class="fl mr20">  <input type="radio" checked="checked"  name="tender_type" id="tender_type1" value="1"/><em>按金额投标</em></span>
		                <span class="fl relative">
		                <input  style="width:150px; padding-right:20px; margin-top:-5px;" class="colright form-inpyt-sm"  placeholder="" value="${autoInvestConfig.tender_account_auto }"  name="tender_account_auto"   maxlength="8" id="tender_account_auto"  /><span class="yuan gray9">元</span></span>
	               </p>
	               <p class="f14 mt10">
	               		 <input type="radio"  name="tender_type" id="tender_type2" value="3"  /><em>按账户余额投标</em></span>
	               </p>
	               <div class="dqb"> 
	               <p class="f14 mt10">
	                    <span><input type="checkbox" name="isUseCur" id="isUseCur_money" checked="checked"/> <em >使用活期宝金额<i class="iconfont yellow" id="tip-bottom" title="仅适用于非存管账户投标">&#xe608;</i></em></span>
	               </p>
	               </div>
                </div>
            </div>
         <!--    <div class="form-col2">
                <label for="" class="colleft02 form-lable">还款方式</label>
                <div class="form-hkfs fl f14">
	                	<span class="fl"><input  type="radio" id="borrow_style_status1" checked="checked" name="borrow_style_status" value="0"/><em>不限  </em></span>
	                    <span class="fl ml20">
		                    <strong class="fl"><input  type="radio"  id="borrow_style_status2" name="borrow_style_status" value="1"/></strong>
			                    <dl class="colright select select-sl mll0" >
					               <select  name="borrow_type" id="borrow_type">
							           <option value="1">等额本息</option>
							           <option value="2">按月付息到期还本</option>
							           <option value="3">到期还本付息 </option>
							        </select>
				              </dl>
		              </span>
                </div>
            </div> -->
            <!-- 还款方式默认不限 -->
            <input  type="hidden" id="borrow_style_status1" checked="checked" name="borrow_style_status" value="0"/>
            <div class="form-col2">
                <label for="" class="colleft02 form-lable">借款期限</label>
                <div class="form-hkfs fl f14">
                	<span class="fl">
                		<input  type="radio" id="timelimit_status1" checked="checked" name="timelimit_status" value="1" /><em>不限  </em>
                		<font color="red">(借款标最高期限可达36个月)</font>
                	</span>
                	<br/><br/>
                    <span class="fl ml0">
                    <strong class="fl"><input  type="radio"  id="timelimit_status2" name="timelimit_status" value="0" /></strong>
                    <dl class="colright select select-sl02 mll0" >
		                 <select  name="min_time_limit" id="min_time_limit" >
					        <c:forEach var="m" begin="1" end="36" >
	               				<option value="${m }">${m }个月</option>
	               			</c:forEach>
		       			 </select>
		              </dl>
             	  <strong class="fl mll0 mr10 f14">至</strong>
                 <dl class="colright select select-sl02 mll0" >
	               <select class="select-box1" name="max_time_limit" id="max_time_limit">
	               		<c:forEach var="m" begin="1" end="36" >
	               			<option value="${m }">${m }个月</option>
	               		</c:forEach>
		         </select>
                </dl>
              </span>
                </div>
            </div>
          <%--   <div class="form-col2">
                <label for="" class="colleft02 form-lable">利率范围</label>
                <span class="fl relative">
                    <input  type="text" id="min_apr" name="min_apr" value="${autoInvestConfig.min_apr }" style="width:50px; padding-right:20px; margin-top:-5px;" class="colright form-inpyt-sm"  placeholder="" size="4"/><span class="yuan gray9">%</span></span>
                <strong class="fl mll0 mr10 f14">至</strong>
                <span class="fl relative">
                    <input id="max_apr" name="max_apr" type="text" value="${autoInvestConfig.max_apr }" style="width:50px; padding-right:20px; margin-top:-5px;" class="colright form-inpyt-sm"  placeholder="" size="4" /><span class="yuan gray9">%</span></span>
             </div> --%>
            <!-- 利率范围默认为 -->
            <input  type="hidden" id="min_apr" name="min_apr" value="1" />
            <input  type="hidden" id="max_apr" name="max_apr" value="24" />
          <%--   <div class="form-col2">
                <label for="" class="colleft02 form-lable">最低投标金额</label>
                <span class="fl relative"><input style="width:164px; padding-right:20px; margin-top:-5px;" class="colright form-inpyt-sm"  placeholder="" id="min_tender_account" value="${autoInvestConfig.min_tender_account }"  name="min_tender_account" maxlength="8" type="text" /> <span class="yuan gray9">元</span></span>
            </div> --%>
            <!-- 最低投标默认为 -->
              <input  type="hidden" id="min_tender_account" name="min_tender_account" value="50.00" />
            <c:if test="${eUserInfo!=null}">
            <div id="mr">
             <div class="form-col2">
                <label for="" class="colleft02 form-lable">手机号码</label>
                 <span class="fl money"><strong class="f14">${mobile}</strong></span>
            </div>
             <div class="form-col2">
                <label for="" class="colleft02 form-lable">短信验证码</label>
                <span class="fl relative"> <input type="text" name="mobileCode" id="mobileCode"  style="width:120px" class="colright form-inpyt-sm"  placeholder="请输入验证码"><p class="fl pdlr10 line32"><span><a href="javascript:;" onclick="mobileCode();" id="sendbtn" style="padding:2px 0 0 5px;">获取银行验证码</a></span></p></span>
            </div> 
            </div>
            </c:if>
            <div class="form-col2">
                <label for="" class="colleft02 form-lable">启用状态</label>
                <span class="fl relative" style="margin-top: 5px;"><input type="checkbox" id="statusCB"  name="statusCB" <c:if test="${empty autoInvestConfig.status or autoInvestConfig.status==1}">checked="checked"</c:if> />
                                      <span style="padding-top: 3px;">立即启用</span>  </span>
            </div>
            <div class="form-col2">
            <button type="button" class="remove icon-close"  onclick="closeWin();">取消</button><button type="button" class="enter" onClick="javascript:autoInvestSumbit();">确认</button>
            </div>
         </div>
    </div> 
	<input type="hidden" name="id" value="${autoInvestConfig.id}"/>
    <input type="hidden" name="status" id="status" value="0"/>
	</form>
<script type="text/javascript">
//自动投标弹框回显
$(function(){
	 var id = '${autoInvestConfig.id}';
	 var eUserInfo='${eUserInfo.bindNo}';
	 if(id>0){
		 
		 var custodyFlag='${autoInvestConfig.custodyFlag}';
		 if(custodyFlag=='1'){
			 $("#fcg").attr("checked",true);
			 $("#cg").attr("checked",false);
			 $("#mr").attr("style","display: none");
			 $(".dqb").attr("style","display:");
		 }else if(custodyFlag=='2'){
			 $("#fcg").attr("checked",false);
			 $("#cg").attr("checked",true);
			 $("#mr").attr("style","display:");
			 $(".dqb").attr("style","display:none");
		 }else if(custodyFlag=='3'){
			 $("#fcg").attr("checked",true);
			 $("#cg").attr("checked",true);
			 $("#mr").attr("style","display:");
			 $(".dqb").attr("style","display:");
		 }else{
			 $("#fcg").attr("checked",true);
			 $(".dqb").attr("style","display:");
			 $("#custodyFlag").val(1);
		 }
		 
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
		 
		 $("#fcg").attr("checked",true);
		 $("#mr").attr("style","display:");
		 $(".dqb").attr("style","display:");
		 if(eUserInfo.length>0){
			 $("#cg").attr("checked",true);
			 $("#custodyFlag").val(3);
		 }else{
			 $("#cg").attr("checked",false);
			 $("#custodyFlag").val(1);
			 
		 }
		 
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
	 
	 var _msg="您将设置自动投标";
	 if($("#timelimit_status1").attr("checked")=='checked'){
		 _msg+='，借款期限为【不限】，确定提交吗?';
	 }
	 
	 //提交表单
	 layer.confirm(_msg, function(){
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
							closeWin();
							window.location.href="${basePath}/myaccount/autoInvest/autoInvestMain.html";
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
		
		if($("#fcg").is(":checked")==false && $("#cg").is(":checked")==false){
			layer.msg("请选择投标类型！", 2, 5);
			return false;
		}
		var cf= $("#custodyFlag").val();
		if(cf==2 || cf==3){
			var mobileCode=$("#mobileCode").val();
			if(mobileCode.length<=0){
				layer.msg("请输入短信验证码！", 2, 5);
				return false;
			}
		}
		
	
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
			 $('#isUseCur_money').val("0");
		 }else{
			 $('#isUseCur_money').val("1");
		 }
	    return true;
}
function closeWin(){ 
	$('#setInvest').trigger('reveal:close'); 
	window.location.href="${basePath}/myaccount/autoInvest/autoInvestMain.html";
	} 
	
	
	

	function iscg(){
		if($("#fcg").is(":checked")==true && $("#cg").is(":checked")==true){
			$("#custodyFlag").val(3);
			$("#mr").attr("style","display:");
			$(".dqb").attr("style","display:");
			 var money="${accountVo.eUseMoney+accountVo.useMoney}";
			 $("#useMoney").html(money);
		}else if($("#cg").is(":checked")==true){
			$("#custodyFlag").val(2);
			$("#mr").attr("style","display:");
			$(".dqb").attr("style","display:none");
			 $("#isUseCur_money").attr("checked",false);
			 var money="${accountVo.eUseMoney}";
			 $("#useMoney").html(money);
		}else if($("#fcg").is(":checked")==true){
			$("#custodyFlag").val(1);
			$("#mr").attr("style","display: none");
			$(".dqb").attr("style","display:");
			 var money="${accountVo.useMoney}";
			 $("#useMoney").html(money);
		}else{
			$("#custodyFlag").val("");
			$("#mr").attr("style","display: none");
			$(".dqb").attr("style","display:none");
			 var money="${accountVo.eUseMoney+accountVo.useMoney}";
			 $("#useMoney").html(money);
		}
	}
	
	
	
	//获取短信验证码
	 function mobileCode(){
		 $('#sendbtn').removeAttr("onclick");
		 if(${portal:hasRole("borrow")}){
			 layer.msg("您是借款用户,不能进行此操作", 1, 5);
			 return;
		 };
		
		 var isCustody="${eUserInfo}";	 
		 if(isCustody==null){
			 layer.msg("您未开通存管账户,不能进行此操作", 1, 5);
			 return;
		 }
		 
			 $.ajax({
					url:"${basePath}/myaccount/autoInvest/sendMobileCode.html",
					type:"post",
					dataType:"json",
					success:function(data){
						if(data.code=="0"){
							$('#sendbtn').attr("onclick","mobileCode()");
						  layer.msg(data.message, 2, 5);
						}else if(data.code=="1"){
							layer.alert(data.message,1);
							run();
						}
					},
					error:function(data){
						$('#sendbtn').attr("onclick","mobileCode()");
						layer.msg("网络连接异常，请刷新页面或稍后重试！", 2, 5);
					}
					
				});
		 } 
	
	
	 /**
	  * 刷新过期时间
	  */
	 var runtime=180;
	 function run(){
	 	runtime=180;
	 	runclock();
	 }
	 function runclock(){
		 $('#sendbtn').removeAttr("onclick");
	 	setTimeout(function(){
	 		runtime = runtime-1;
	 		if(runtime>0){
	 			$('#sendbtn').html(runtime+"秒后获取");
	 			runclock();
	 		}else{
	 			runtime=180;
	 			$('#sendbtn').html('获取验证码');
	 			$('#sendbtn').attr("onclick","mobileCode()");
	 		}
	 	},1000);
	 }
	
</script>  
        
<!---------- ------------------------自动投标-弹层---------------------------------------------------->  