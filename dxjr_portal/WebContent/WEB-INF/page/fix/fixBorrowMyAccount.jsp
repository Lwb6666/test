<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/page/common/taglib.jsp"%>
<!doctype html>
<html>

<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="keywords" content="顶玺金融,金融,投资,理财" />
<meta name="description" content="顶玺金融提供安全、有担保的互联网理财投资服务。100%本息担保！随时可赎回！上顶玺，好收益！">
<meta name="author" content="顶玺金融" />
<meta name="copyright" content="2014 上海顶玺金融信息服务有限公司" />

<%@ include file="/WEB-INF/page/common/public4.jsp"%>
<title>我的账户_投资管理_定期宝_定期宝</title>
</head>
<body>
<!-- header start   -->
<%@ include file="/WEB-INF/page/common/header.jsp"%>
<!-- header end     -->
<!--main-->
<div id="myaccount">
	<%@ include file="/WEB-INF/page/common/topmenu.jsp"%>
<!--banner star-->
	<%@ include file="/WEB-INF/page/common/wraper.jsp"%>
<div class="wraper mac-box mt20">
			<div class="myacc-list baifen33 clearfix">
            	<ul>
                	<li><em class="blue">定期宝总额</em>
                		<span class="orange2"> 
                			${retMap.capital} 元
						</span>
                	</li>
                    <li><em class="blue">预期收益</em><span class="orange2">${retMap.repayYesAccountNo} 元</span></li>
                    <li><em class="blue">已赚收益</em><span class="orange2">${retMap.repayYesAccountYes} 元</span></li>
                </ul>
            </div>
            
                       <div class="myacc-into clearboth">
                    <div class="myacc-dqb fl">
                    	<p class="my-hq f18"><i class="iconfont orange2">&#xe607;</i><em>1月宝 </em>
                    	<span <c:if test="${checkStyle == '1'}"> class="orange"</c:if> <c:if test="${checkStyle != '1'}"> class="gary2"</c:if>>
                    		<c:if test="${fixAccountOneRatio != 0}">${fixAccountOneRatio }% </c:if>
                    	</span>
                    	<span <c:if test="${checkStyle == '1'}"> class="orange"</c:if> <c:if test="${checkStyle != '1'}"> class="gary2"</c:if>><fmt:formatNumber value="${fixAccountOne}" pattern="#,##0.00" />元</span></p>
                        <p class="my-hq f18"><i class="iconfont orange2">&#xe607;</i><em>3月宝 </em>
                        <span <c:if test="${checkStyle == '3'}"> class="orange"</c:if> <c:if test="${checkStyle != '3'}"> class="gary2"</c:if>>
                        	<c:if test="${fixAccountThreeRatio != 0}">${fixAccountThreeRatio }% </c:if> 
                        </span>
                        <span <c:if test="${checkStyle == '3'}"> class="orange"</c:if> <c:if test="${checkStyle != '3'}"> class="gary2"</c:if>><fmt:formatNumber value="${fixAccountThree}" pattern="#,##0.00"  />元</span></p>
                        <p class="my-hq f18"><i class="iconfont orange2">&#xe607;</i><em>6月宝 </em>
                        <span <c:if test="${checkStyle == '6'}"> class="orange"</c:if> <c:if test="${checkStyle != '6'}"> class="gary2"</c:if>>
                        	<c:if test="${fixAccountSixRatio != 0}">${fixAccountSixRatio }% </c:if>  
                        </span><span <c:if test="${checkStyle == '6'}"> class="orange"</c:if> <c:if test="${checkStyle != '6'}"> class="gary2"</c:if>><fmt:formatNumber value="${fixAccountSix}" pattern="#,##0.00" />元</span></p>
                        <p class="my-hq f18"><i class="iconfont orange2">&#xe607;</i><em>12月宝 </em>
                        <span <c:if test="${checkStyle == '12'}"> class="orange"</c:if> <c:if test="${checkStyle != '12'}"> class="gary2"</c:if>>
                        	<c:if test="${fixAccountTwelRatio != 0}">${fixAccountTwelRatio }% </c:if> 
                        </span><span <c:if test="${checkStyle == '12'}"> class="orange"</c:if> <c:if test="${checkStyle != '12'}"> class="gary2"</c:if>><fmt:formatNumber value="${fixAccountTwel}" pattern="#,##0.00" />元</span></p>
					</div>
                    
                    <div class="myacc-right fl">
                    <p><a href="${path}/dingqibao.html" class="btn btn-blue">加入定期宝</a></p>
                    <p class="blue"><a href="${path}/bangzhu/24.html" class="blue">了解定期宝</a></p>
                    </div>
                </div>
</div>

 
<!--定期宝star-->
	<div class="wraper mt20">
        <div class="product-deatil clearfix ">
            <div class="tz-dqb2 clearfix">
                <div class="col clearfix">
                        <span class="fl gary2">状态：</span>
                        <div class="btn-box-bg">
                            <div class="btn-box4"><a id="tab2Syz" href="javascript:SearchChangeByOption('tab2Syz','','1');">收益中</a></div>
                            <div class="btn-box4"><a id="tab1Jrz" href="javascript:SearchChangeByOption('tab1Jrz','','1');">加入中</a></div>
                            <div class="btn-box4"><a id="tab4Tcz" href="javascript:SearchChangeByOption('tab4Tcz','','1');">退出中</a></div>
                            <div class="btn-box4"><a id="tab3Ytc" href="javascript:SearchChangeByOption('tab3Ytc','','1');">已退出</a></div>
                        </div>
                 </div>
                <div class="col clearfix clearfix1">
                        <span class="fl gary2">期限：</span>
                        <div class="btn-box-bg">
                            <div class="btn-box4"><a id="FULL" href="javascript:SearchChangeByOption('','FULL','1')">全部</a></div>
                            <div class="btn-box4"><a id="1" href="javascript:SearchChangeByOption('','1','1')">1月宝</a></div>
							<div class="btn-box4"><a id="3" href="javascript:SearchChangeByOption('','3','1')">3月宝</a></div>
                             <div class="btn-box4"><a id="6" href="javascript:SearchChangeByOption('','6','1')">6月宝</a></div>
                            <div class="btn-box4"><a id="12" href="javascript:SearchChangeByOption('','12','1')" >12月宝</a></div>
                        </div>
                </div>
                
             </div>
             <div id="containerRight"></div>
             <input type="hidden" id="state"  name="state"/>
             <input type="hidden" id="product"  name="product"/>
        </div>
        
    </div>

</div>
	<!-- foot start -->
	<%@ include file="/WEB-INF/page/common/footer.jsp"%>
	<!-- foot end -->
	
<script type="text/javascript">
    var timestamp = Date.parse(new Date());
    
	$(function() {
		//默认第一次加载   收益中
		$('#state').val("tab2Syz");
		$('#tab2Syz').attr("class","active");
		$('#FULL').attr("class","active");
		
		SearchChangeByOption("tab2Syz","FULL",'1');
	});
	
	$(function() {
		$("#tzgl").attr("class","active");
		$("#dqb").attr("class","active");  //添加样式 
	});
	
	function SearchChangeByOption(state,product,pageNo){
		var stateStr = "";
		if(state==""){
			stateStr = $('#state').val();  //获取状态值
		} else {
			stateStr = state;
		}
		
		var productStr = ""; 
		if(product==""){
			productStr = $('#product').val();  //获取定期宝
		} else {
			productStr = product;
		}
		
		if(product == "FULL" || productStr == "FULL"){
			productStr = "";
		}
		if(stateStr == "tab1Jrz"){  //加入中
			search_jrz(pageNo,productStr);
			$('#tab3Ytc').removeClass("active");
			$('#tab2Syz').removeClass("active");
			$('#tab4Tcz').removeClass("active");
		} else if(stateStr == "tab3Ytc"){  //已退出
			search_ytc(pageNo,productStr);
			$('#tab1Jrz').removeClass("active");
			$('#tab2Syz').removeClass("active");
			$('#tab4Tcz').removeClass("active");
		} else if(stateStr =="tab2Syz"){  //收益中
			search_syz(pageNo,productStr);
			$('#tab1Jrz').removeClass("active");
			$('#tab3Ytc').removeClass("active");
			$('#tab4Tcz').removeClass("active");
		} else if(stateStr =="tab4Tcz"){  //退出中
			search_tcz(pageNo,productStr);
			$('#tab1Jrz').removeClass("active");
			$('#tab2Syz').removeClass("active");
			$('#tab3Ytc').removeClass("active");
		}
		
			$('#'+stateStr).addClass("active");
		
			if(product == "FULL"){
				$('#FULL').addClass("active");
				$('#1').removeClass("active");
				$('#3').removeClass("active");
				$('#6').removeClass("active");
				$('#12').removeClass("active");
			} else {
				if (productStr == "1"){
					$('#FULL').removeClass("active");
					$('#3').removeClass("active");
					$('#6').removeClass("active");
					$('#12').removeClass("active");
				} else if(productStr == "3"){
					$('#FULL').removeClass("active");
					$('#1').removeClass("active");
					$('#6').removeClass("active");
					$('#12').removeClass("active");
				} else if(productStr == "6"){
					$('#FULL').removeClass("active");
					$('#1').removeClass("active");
					$('#3').removeClass("active");
					$('#12').removeClass("active");
				} else if(productStr == "12"){
					$('#FULL').removeClass("active");
					$('#1').removeClass("active");
					$('#3').removeClass("active");
					$('#6').removeClass("active");
				}
					
				$('#'+productStr).addClass("active");
			}
			
			if(state != ""){
				$('#state').val(state);
			}
			
			if(product != ""){
				$('#product').val(product);
			}
		}
	
	//收益明细- 查询btn   ----加入中
	function search_jrz(pageNum,tag) {
		//alert('查询btn = '+ pageNum+' tag = '+ tag);
		var dataParam = {
			tag : tag
		};

		$.ajax({
			url : '${basePath}/dingqibao/tab1JrzDetail/' + pageNum + '.html',
			data : {tag:tag,t:timestamp},
			type : 'post',
			dataType : 'text',
			
			async: false,
			success : function(data) {
				$("#containerRight").html(data);

			},
			error : function(data) {
				layer.msg('网络连接异常，请刷新页面或稍后重试！', 1, 5);
			}
		});
	 
	}

	//收益明细- 查询btn  ------------收益中 
	function search_syz(pageNum, tag) {
		//alert('查询btn = '+ pageNum+' tag = '+ tag);
		$.ajax({
			url : '${basePath}/dingqibao/tab2SyzDetail/' + pageNum + '.html',
			data : {tag:tag,t:timestamp},
			type : 'post',
			dataType : 'text',
			async: false,
			success : function(data) {
				$("#containerRight").html(data);
			},
			error : function(data) {
				layer.msg('网络连接异常，请刷新页面或稍后重试！', 1, 5);
			}
		});
	}
	
	//资金信息- 查询btn   -------------已退出
	function search_ytc(pageNum, tag) {
		//alert('查询btn = '+ pageNum+' tag = '+ tag);
		var dataParam = {
			tag : tag
		};
		$.ajax({
			url : '${basePath}/dingqibao/tab3TczDetail/' + pageNum + '.html',
			data : {tag:tag,t:timestamp},
			type : 'post',
			dataType : 'text',
			async: false,
			success : function(data) {
				//alert('btn-zjxx1');
				$("#containerRight").html(data);

			},
			error : function(data) {
				layer.msg('网络连接异常，请刷新页面或稍后重试！', 1, 5);
			}
		});
		}
	
	// 退出中明细
	function search_tcz(pageNum, tag) {
		//alert('查询btn = '+ pageNum+' tag = '+ tag);
		var dataParam = {
			tag : tag
		};
		$.ajax({
			url : '${basePath}/dingqibao/tab4TczDetail/' + pageNum + '.html',
			data : {tag:tag,t:timestamp},
			type : 'post',
			dataType : 'text',
			async: false,
			success : function(data) {
				//alert('btn-zjxx1');
				$("#containerRight").html(data);

			},
			error : function(data) {
				layer.msg('网络连接异常，请刷新页面或稍后重试！', 1, 5);
			}
		});
		}
	
	/**
	 * 申请退出
	 * @param fixTenderRealId
	 */
	function checkExit(fixTenderRealId, obj){
		if(fixTenderRealId == null || fixTenderRealId == ''){
			layer.msg("最终认购记录不能为空!", 2, 5);
		}
		var _load = layer.load('处理中..');
		$.ajax({
			url : '${basePath}/dingqibao/exit/checkExit.html',
			data : {
				fixTenderRealId : fixTenderRealId
			},
			type : 'post',
			dataType : 'json',
			success : function(data) {
				layer.close(_load);
				if(data.code == "1"){
					$("#applyExitButton").unbind("click");
					$("#applyExitButton").bind("click", applyExit);
					var resultMap = data.result;
					$("#account").text(resultMap.accountStr);
					$("#interestReal").text(resultMap.interestRealStr);
					$("#serviceRate").text(resultMap.serviceRate);
					$("#serviceFee").text(resultMap.serviceFeeStr);
					$("#realMoney").text(resultMap.realMoneyStr);
					$("#calcDays").text(resultMap.calcDays);
					$("#fixDays").text(resultMap.fixDays);
					$('#out').reveal({
						animation: 'fade',
						animationspeed: 300,
						closeonbackgroundclick: false,
						dismissmodalclass: 'close-reveal-modal'
					});
					$("#ApplyExitId").val(fixTenderRealId);
				}else{
					layer.alert(data.message);
				}
			},
			error : function(data) {
				layer.close(_load);
				layer.msg("网络连接异常，请刷新页面或稍后重试！", 2, 5);
			}
		});
	}

	/**
	 * 申请退出
	 * @param fixTenderRealId
	 */
	function applyExit(){
		$("#applyExitButton").unbind("click");
		var fixTenderRealId = $("#ApplyExitId").val();
		if(fixTenderRealId == null || fixTenderRealId == ''){
			layer.msg("最终认购记录不能为空!", 2, 5);
		}
		var _load = layer.load('处理中..');
		$.ajax({
			url : '${basePath}/dingqibao/exit/applyExit.html',
			data : {
				fixTenderRealId : fixTenderRealId,
				platform : 1
			},
			type : 'post',
			dataType : 'json',
			success : function(data) {
				layer.close(_load);
				if(data.code == "1"){
					layer.alert(data.message, 1, "提示信息", function(){
						$("#ApplyExitId").val("");
						window.location.reload();
					});
				}else{
					$("#applyExitButton").unbind("click");
					$("#applyExitButton").bind("click", applyExit);
					layer.alert(data.message);
				}
			},
			error : function(data) {
				layer.close(_load);
				$("#applyExitButton").unbind("click");
				$("#applyExitButton").bind("click", applyExit);
				layer.msg("网络连接异常，请刷新页面或稍后重试！", 2, 5);
			}
		}); 
	}
	function closeOut(){
		$('#closeOut').trigger('reveal:close');
		$("#ApplyExitId").val("");
	}
</script>

<!--申请退出start--->
<div id="out" class="reveal-modal">
	<div class="cont-word">
		<div class="title"><h4>申请提前退出</h4><a href="#" id="closeOut" onclick="closeOut()" class="icon icon-close fr"></a></div>
		<div class="form-info-layer" style=" font-size:14px; line-height:2;">

			<p>您通过此定期宝的投资本金为<span class="red" id="account">XXX</span>元</p>
			<p>截止当前已产生利息天数<span class="green" id="calcDays">XXX</span>天，定期宝总天数<span class="green" id="fixDays">XXX</span>天</p>
			<p>截止当前为您产生收益为<span class="red" id="interestReal">XXX</span>元</p>
			<p>申请退出成功将扣除投资本金<span class="green" id="serviceRate">XXX</span>%的服务费<span class="green" id="serviceFee">XXX</span>元</p>
			<p>最终到到帐金额为<span class="red f18" id="realMoney">XXX</span>元</p>
			<p class="red f18">请确认是否退出？</p>

			<div class="form-col2">
				<button type="button" onclick="closeOut()" class="remove icon-close fl">取消</button>
				<button id="applyExitButton" type="button" class="enter">确认退出</button>
			</div>
		</div>
	</div>
</div>
<input id="ApplyExitId" type="hidden" value="">
<!--弹层end--->
</body>
</html>
