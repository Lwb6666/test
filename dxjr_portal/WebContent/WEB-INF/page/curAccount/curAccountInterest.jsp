<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/page/common/taglib.jsp"%>

<!doctype html>
<html>
<head>
	<title>我的账户_投资管理_活期宝_活期生息</title>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<meta name="keywords" content="顶玺金融,金融,投资,理财" />
	<meta name="description" content="顶玺金融提供安全、有担保的互联网理财投资服务。风险保证金保障！随时可赎回！上顶玺，好收益！">
	<meta name="author" content="顶玺金融" />
	<meta name="copyright" content="2014 上海顶玺金融信息服务有限公司" />
	<%@ include file="/WEB-INF/page/common/public4.jsp"%>
	<script type="text/javascript" src="${basePath}/js/highcharts/highcharts3.0.7.js" ></script>
	<script type="text/javascript" src="${basePath}/js/highcharts/themes/grid.js"></script>
	<script type="text/javascript" src="${basePath}/js/highcharts/myChart.js"></script>
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
	<div class="myacc-into clearboth">
		<div class="myacc-dqb fl">
        	<p class="fund-hqbti f18"><i class="iconfont orange2">&#xe607;</i>
        		<em>活期宝资产</em>
              	<span class="orange2 ml20">
	              	<c:if test="${retMap==null }">0.00 </c:if> 
	              	<c:if test="${retMap!=null }"><fmt:formatNumber value="${retMap.total0 }" pattern="#,##0.##" />.${retMap.total1 }</c:if>
              	</span>元
              	<a href="${path }/dingqibao.html"><span class="blue ml20 f16">提高收益</span></a></p>
                <ul class="fund-hqbList">
               		<li><em>昨日收益</em><span class="gary2">
               			<c:if test="${retMap==null }"> 0.00 </c:if>
					<c:if test="${retMap!=null }"> <fmt:formatNumber value="${retMap.interest_yesterday0 }" pattern="#,##0.##" />.${retMap.interest_yesterday1 }</c:if>元</span>
					</li>
                    <li><em>累计收益</em><span class="gary2">
                  		<c:if test="${retMap==null }">0.00 </c:if> 
                  		<c:if test="${retMap!=null }"><fmt:formatNumber value="${retMap.interest_total0 }" pattern="#,##0.##" />.${retMap.interest_total1 }</c:if>元</span>
                    </li>
                </ul>
		</div>    
        <div class="myacc-right fl">
            <p><a href="javascript:void(0)" onclick="curIn();"  class="btn btn-blue">转入</a></p>
            <p><a href="javascript:void(0);"  onclick="curOut();"   class="btn btn-border">转出</a></p>
            <p class="blue"><a href="${path}/bangzhu/23.html#listId=1" class="blue">了解活期宝</a></p>
        </div>
    </div>
</div>
<!--活期宝star-->
	<div class="wraper mt20">
     <div class="prduct-menu  background clearfix">
       	<div class="menu-tbl">
           	<ul class="col2"><li id="tab1_symx" class="active" onclick="load_tab1_symx(this)">收益明细</li><li id="tab2Zjxx" onclick="serachTab2(1,'typeFull','tagFull')">交易记录</li></ul>
        </div>
		<div class="menucont" style="clear:both">
		
           	<div class="tbl-cont tal-none" id="containerRight">
           	
           	</div>
               <div class="tbl-cont tal-none" style="display: none">
               <div class="product-deatil clearfix ">
               
	           <div class="tz-dqb2 clearfix">
	                <div class="col clearfix">
	                        <span class="fl gary2">交易类型：</span>
	                        <div class="btn-box-bg">
	                            <div class="type"><a id="typeFull" href="javascript:serachTab2(1,'typeFull','');">全部</a></div>
	                            <div class="type"><a id="type1" href="javascript:serachTab2(1,'1','');">转入</a></div>
	                            <div class="type"><a id="type2" href="javascript:serachTab2(1,'2','');">转出</a></div>
	                        </div>
	                 </div>
	                <div class="col clearfix">
	                        <span class="fl gary2">标的期限：</span>
	                        <div class="btn-box-bg">
	                        	<div class="term"><a id="tagFull" href="javascript:serachTab2(1,'','tagFull')" class="active">全部</a></div>
	                         	<div class="term">
	                            	<input type="text" id="beginDate" onClick="WdatePicker();"  class="Wdate" /> 至 
	                            	<input type="text" id="endDate" onClick="WdatePicker();"  class="Wdate" />
	                            </div>
	                            <div class="term"><a href="javascript:serachJyjlByDate();" class="active">查询</a></div>
	                            <div class="term"><a id="week" href="javascript:serachTab2(1,'','week')">近一周</a></div>
	                            <div class="term"><a id="month" href="javascript:serachTab2(1,'','month')">近一月</a></div>
	                        </div>
	                </div>
					
             </div>
             <div  id="containerZjxx">
             
			</div>
               </div>
               </div>
           </div>
      </div>
     </div>
	
	<input type="hidden" name="type" id="type"/>
	<input type="hidden" name="jyTag" id="jyTag"/>
	
	
</div>
<div class="reveal-modal" id="zr" ></div>
<div class="reveal-modal" id="zc" ></div>
<div class="reveal-modal" id="syChart" >
	   <div class="cont-word" style="max-width:880px!important; padding-bottom:50px; overflow:hidden;">
	    	<div class="title"><h4>收益明细统计</h4><a href="javascript:void(0);" onclick="closeChat();" class="icon icon-close fr"></a></div>
	        <div class="form-info-layer" id="moneyChart">
	        </div>
	    </div> 
</div>
<%@ include file="/WEB-INF/page/common/footer.jsp"%>
	
<script type="text/javascript">
	$(function() {
		$('#tzgl').attr("class","active");
		$('#hqb').attr("class","active");
		
		load_tab1_symx("tab1_symx");
	});
	
	//关闭走势图框 
	function closeChat(){
		$('#syChart').trigger('reveal:close'); 
	}


	//根据时间查询时执行判断 交易记录
	function serachJyjlByDate(){
		var beginDay = $("#beginDate").val();
		var endDay = $("#endDate").val();
		if(beginDay == '' && endDay == ''){
			layer.msg("请选择开始日期或结束日期!");
		}
		
		if(beginDay != '' && endDay != ''){
			var start = new Date(beginDay.replace("-", "/").replace("-", "/"));
			var end = new Date(endDay.replace("-", "/").replace("-", "/"));
			if (end < start) {
				layer.msg("开始日期不能大于结束日期!", 2, 5);
				return;
			} else {
				serachTab2(1,'','DATE');
			}
		}
	}
	
	
	// 加载-资金信息
	//pageNo 页码,type：转入转出 
	function serachTab2(pageNo,type,tag) {
		var typeStr = "";  //获取交易类型值
		if(type == ""){
			typeStr = $('#type').val();
		} else {
			typeStr = type;
		}
		
		var tagStr = "";
		if(tag == ""){
			tagStr = $('jyTag').val();
		} else {
			tagStr = tag;
		}
		
		
		if(tagStr=="week"){
			$('#month').removeClass("active");
			$('#tagFull').removeClass("active");
			
			$('#beginDate').val('');
			$('#endDate').val('');
			
			$('#jyTag').val("week");
		} else if(tagStr =="month"){
			$('#week').removeClass("active");
			$('#tagFull').removeClass("active");
			
			$('#beginDate').val('');
			$('#endDate').val('');
			
			$('#jyTag').val("month");
		} else if(tagStr =="tagFull"){
			$('#month').removeClass("active");
			$('#week').removeClass("active");
			
			$('#beginDate').val('');
			$('#endDate').val('');
			
			$('#jyTag').val("tagFull");
		} else if(tagStr =="DATE"){
			$('#month').removeClass("active");
			$('#week').removeClass("active");
			$('#tagFull').removeClass("active");
			
			$('#jyTag').val('');
		}
		
		$('#'+tagStr).attr("class","active");
		
		if(typeStr=="1"){
			$('#type1').attr("class","active");
			$('#type2').removeClass("active");
			$('#typeFull').removeClass("active");
			
			$('#type').val("1");
		} else if (typeStr=="2"){
			$('#type2').attr("class","active");
			$('#type1').removeClass("active");
			$('#typeFull').removeClass("active");
			
			$('#type').val("2");
			
		} else if(typeStr="typeFull"){
			$('#typeFull').attr("class","active");
			$('#type1').removeClass("active");
			$('#type2').removeClass("active");
			
			$('#type').val("typeFull");
		}
		
		$.ajax({
			url : '${basePath}/curAccountLogController/tab2Zjxx/' + pageNo + '.html',
			data :{type:$('#type').val(),beginDay:$('#beginDate').val(),endDay:$('#endDate').val(),tag:$('#jyTag').val()},
			type : 'post',
			success : function(data) {
				$("#containerZjxx").html(data);
			},
			error : function(data) {
				layer.msg("网络连接异常，请刷新页面或稍后重试！", 1, 5);
			}
		});
	}
	
	/**
	 * 活期宝转出
	 */
	function curOut() {
		$.ajax({
			url : '${basePath}/curOut/judgeIsCanOut.html',
			data : {},
			type : 'post',
			dataType : 'json',
			success : function(data) {
				if (data.code == "1" || data.code == 1) {
					$('#zc').reveal({
	                    animation: 'fade',                  
	                    animationspeed: 300,                      
	                    closeonbackgroundclick: false,              
	                    dismissmodalclass: 'close-reveal-modal'    
	                });
					$("#zc").load( '${basePath}/curOut/enterCurOut/1.html');
				} else {
					layer.msg(data.message, 2, 5);
				}
			},
			error : function(data) {
				layer.msg('网络连接异常，请刷新页面或稍后重试！', 1, 5);
			}
		});
	}

	/**
	 * 活期宝转入
	 */
	function curIn() {
		$.ajax({
			url : '${basePath}/curInController/judgeIsCanIn.html',
			data : {},
			type : 'post',
			dataType : 'json',
			success : function(data) {
				if (data.code == "1") {	
					$('#zr').reveal({
	                    animation: 'fade',                  
	                    animationspeed: 300,                      
	                    closeonbackgroundclick: false,              
	                    dismissmodalclass: 'close-reveal-modal'    
	                });
					$("#zr").load("${basePath}/curInController/enterCurIn/1.html");
				} else {
					if ("0" == data.code || "-1" == data.code || "-2" == data.code || "-3" == data.code || "-4" == data.code || "-5" == data.code|| "-6" == data.code) {
						layer.msg(data.message, 1, 5, function() {
							if ("0" == data.code) {
								window.location.href = "${path}/member/toLoginPage.html";
							} else if ("-1" == data.code) {
								window.location.href = "${path}/AccountSafetyCentre/safetyIndex.html";
							} else if ("-2" == data.code) {
								window.location.href = "${path}/AccountSafetyCentre/safetyIndex.html";
							} else if ("-4" == data.code) {
								window.location.href = "${path}/AccountSafetyCentre/safetyIndex.html";
							} else if ("-5" == data.code) {
								window.location.href = "${path}/AccountSafetyCentre/safetyIndex.html";
							}
						});
						return;
					}
				}
			},
			error : function(data) {
				layer.msg('网络连接异常，请刷新页面或稍后重试！', 1, 5);
			}
		});
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	//收益明细- 查询btn 
	function search(pageNum, tag) {
		var dataParam = {
			beginDay : $('#beginDay').val(),
			endDay : $('#endDay').val(),
			tag : tag
		};

		$.ajax({
			url : '${basePath}/curAccountController/tab1InterestDetail/' + pageNum + '.html',
			data : dataParam,
			type : 'post',
			dataType : 'text',
			success : function(data) {
				$("#containerRight").html(data);
			},
			error : function(data) {
				layer.msg('网络连接异常，请刷新页面或稍后重试！', 1, 5);
			}
		});
	}
	
	
	//收益明细 - 分页
	function pageParent_symx(pageNo) {
		var beginDay = $("#beginDay").val();
		var endDay = $("#endDay").val();
		var tag = $('#tag').val();
		$.ajax({
			url : '${basePath}/curAccountController/tab1InterestDetail/' + pageNo + '.html',
			type : 'post',
			data : {beginDay:beginDay,endDay:endDay,tag:tag},
			dataType : 'text',
			success : function(data) {
				$("#containerRight").html(data);
			},
			error : function(data) {
				layer.msg("网络连接异常，请刷新页面或稍后重试！", 1, 5);
			}
		});
	}
	
	// 加载-收益明细
	function load_tab1_symx(obj) {
// 		$("#moneyChart").html('');
// 		processTabStyle(obj);
		var dataParam = {
			initTag : 0
		};

		$.ajax({
			url : '${basePath}/curAccountController/tab1InterestDetail/1.html',
			data : dataParam,
			type : 'post',
			success : function(data) {
				$("#containerRight").html(data);
			},
			error : function(data) {
				layer.msg("网络连接异常，请刷新页面或稍后重试！", 1, 5);
			}
		});
	}

</script>

</body>
</html>
