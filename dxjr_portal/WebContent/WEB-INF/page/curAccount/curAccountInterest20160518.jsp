<%@page import="com.dxjr.utils.DateUtils"%>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/page/common/taglib.jsp"%>
<!doctype html>
<html>
<head>
<%@ include file="/WEB-INF/page/common/public.jsp"%>
<meta charset="utf-8">
<title>我的账户_投资管理_活期宝_活期生息</title>
<!-- 图形报表js -->
<script type="text/javascript" src="${basePath}/js/highcharts/highcharts3.0.7.js" ></script>
<script type="text/javascript" src="${basePath}/js/highcharts/themes/grid.js"></script>
<script type="text/javascript" src="${basePath}/js/highcharts/myChart.js"></script>
</head>

<body>

	<!-- header start   -->
	<%@ include file="/WEB-INF/page/common/header.jsp"%>
	<!-- header end     -->


	<!--   活期生息 s -->

	<div id="Bmain">
		<!-- 导航 s  -->
		<div class="title">
			<span class="home"> <a href="${path}/">顶玺金融</a></span> <span> <a href="${path}/myaccount/toIndex.html">我的账户</a>
			</span> <span><a href="#">投资管理</a></span> <span><a href="${path}/curAccountController/curAccountInterest.html">活期生息</a></span>
		</div>
		<!-- 导航 e  -->

		<!-- 导航下内容 s  -->
		<div id="menu_centert">

			<!--我的账户左侧 开始 -->
			<%@ include file="/WEB-INF/page/account/myAccount_leftMenu.jsp"%>
			<!--我的账户左侧 结束 -->


			<!--我的账户右侧 -活期生息 s -->
			<div class="lb-waikuang whitebg">
				<div class="searchbox fl">
					<div class="lb_title f20 p-r">
						活期生息
						<div class="bott" style="right:10px;top:30px;">
							<a class="but greens textcenter pd-but" href="javascript:curIn();">转入</a>
							<a class="but greens textcenter pd-but mr5" href="javascript:curOut();">转出</a> 
						</div>
					</div>
				</div>
				<div class="fl w848 ow bd-line">
					<div class="fl current-t w600 mdt15">
						<table>
							<tr>
								<td class="green-c bdr-line pl50"><em class="f26"> <c:if test="${retMap==null }">
										0</em> .00 </c:if> <c:if test="${retMap!=null }">
										<fmt:formatNumber value="${retMap.interest_total0 }" pattern="#,##0.##" />
										</em> .${retMap.interest_total1 } 
								</c:if></td>
								<td class="yellow-c bdr-line pl50"><em class="f50"> <c:if test="${retMap==null }"> 0 </em> .00 </c:if> <c:if test="${retMap!=null }">
										<fmt:formatNumber value="${retMap.interest_yesterday0 }" pattern="#,##0.##" />
										</em> .${retMap.interest_yesterday1 }
								</c:if></td>
								<td class="green-c pl50"><em class="f26"> <c:if test="${retMap==null }">
											0</em> .00 </c:if> <c:if test="${retMap!=null }">
										<fmt:formatNumber value="${retMap.total0 }" pattern="#,##0.##" />
										</em> .${retMap.total1 } 
								</c:if></td>
							</tr>

							<tr>
								<td class="f16 pl50">累计收益（元）</td>
								<td class="f16 pl50">昨日收益（元）</td>
								<td class="f16 pl50">活期宝资产（元）</td>
							</tr>


						</table>
					</div>
					<div class="tbproject pdl-10">
						<table>
							<tr>
								<td><a class="blues-cc" href="${path }/bangzhu/23.html#listId=1">什么是活期宝？</a></td>
							</tr>
							<tr>
								<td><a class="blues-cc" href="${path }/bangzhu/23.html#listId=5">昨日收益如何计算？</a></td>
							</tr>
							<tr>
								<td><a class="blues-cc" href="${path}/bangzhu/23.html#listId=4">具体收益规则是什么？</a></td>
							</tr>
						</table>
					</div>

				</div>
			</div>
			<!--我的账户右侧 -活期生息 e -->


			<!--我的账户右侧 - 收益明细tab s -->
			<div class="lb-waikuang">
				<div class="men_title col3">
					<ul id="topupInnerDiv">
						<li class="men_li"><a id="tab1_symx" href="javascript:void(0);" onclick="load_tab1_symx(this)">收益明细</a></li>
						<li><a id="tab2Zjxx" href="javascript:void(0);" onclick="load_tab2Zjxx(this)">资金信息</a></li>
						<li><a id="syChart" href="javascript:void(0);">收益折线图</a></li>
					</ul>
				</div>
				<div class="clear"></div>
				<div id="containerRight"></div>
				<div class="clear"></div>
				<div id="moneyChart"></div>
			</div>
			<!--我的账户右侧 - 收益明细tab e -->
		</div>
		<!-- 导航下内容 e  -->

	</div>

	<!--   活期生息 e -->
	<!-- ps s -->
	<div class="clearfix"></div>
	<!-- ps e -->
	<!-- foot start -->
	<%@ include file="/WEB-INF/page/common/footer.jsp"%>
	<!-- foot end -->

</body>

<script type="text/javascript">
	$(function() {
		//load_tab1_symx(document.getElementById("tab1_symx"));
		load_tab3Chartxx($("#syChart"));
		
		$("#syChart").click(function(){
			load_tab3Chartxx(this);
		});
	});
	
	// 加载-收益折线图
	function load_tab3Chartxx(obj) {
		processTabStyle(obj);
		var dataParam = {
			initTag : 0
		};

		$.ajax({
			url:"${basePath}/curAccountController/toInterestDetailChart.html",
			data : dataParam,
			type : 'post',
			success : function(data) {
				$("#containerRight").html(data);
			},
			error : function(data) {
				layer.msg("网络连接异常，请刷新页面或稍后重试！", 1, 5);
			}
		});
		showcharts(0);
	}
	
	
	
	function showcharts(tag){
		var dataParam;
		if(tag > 0){
			dataParam = {
				beginDay : $('#beginDay').val(),
				endDay : $('#endDay').val(),
				tag : tag
			};
		}else{
			dataParam = {
				beginDay : $('#beginDay').val(),
				endDay : $('#endDay').val(),
				initTag : 0
			};
		}
		
		$.ajax({
			url:"${basePath}/curAccountController/interestDetailChart.html",
			data : dataParam,
			type : 'post',
			success : function(data) {
				$("#beginDay").val(data.beginDay);
				$("#endDay").val(data.endDay);
				if(data.dateList.length > 0){
					$('#moneyChart').highcharts({
						title: {
				            text: '收益明细统计',
				            x: -20 //center
				        },
				        subtitle: {
				            text: '最近'+data.dateList.length+'笔收益金额变化曲线图',
				            x: -20
				        },
				        xAxis:  [{
				        	categories: data.dateList,
				    		type:'datetime',
				    		labels:{
				    		 	format:'{value:%Y-%m-%d}',
				    			align:'left',
								rotation:45
				         	}
				        }],
				        yAxis: {
				            title: {
				                text: '金额'
				            },
				            plotLines: [{
				                value: 0,
				                width: 1,
				                color: '#908080'
				            }]
				        },
				        tooltip: {
				            valueSuffix: '元',
				            xDateFormat: '%Y-%m-%d'
				        },
				        legend: {
				            layout: 'vertical',
				            align: 'right',
				            verticalAlign: 'middle',
				            borderWidth: 0
				        },
				        series: [{
				            name: '金额',
				            color: '#058DC7',
				            type: 'spline',
				            data: data.moneyList,
				            tooltip: {
				                valueSuffix: '元'
				            }
				        }]
			    	}); 	
				}else{
					$('#moneyChart').html('<ul class="lb_title whitebg" style=" border-top:none;"><li style="width:90%; margin-left:40%;">您还没有收益信息</li></ul>');
				}
			},
			error : function(data) {
				layer.msg("网络连接异常，请刷新页面或稍后重试！", 1, 5);
			}
		});
	}

	// 加载-收益明细
	function load_tab1_symx(obj) {
		$("#moneyChart").html('');
		processTabStyle(obj);
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

	// 加载-资金信息
	function load_tab2Zjxx(obj) {
		$("#moneyChart").html('');
		//切换选项卡的时候样式处理
		processTabStyle(obj);
		$.ajax({
			url : '${basePath}/curAccountLogController/tab2Zjxx/1.html',
			type : 'post',
			success : function(data) {
				$("#containerRight").html(data);
			},
			error : function(data) {
				layer.msg("网络连接异常，请刷新页面或稍后重试！", 1, 5);
			}
		});
	}
	


	//收益明细 - 分页
	function pageParent_symx(pageNo) {
		var beginDay = $("#beginDay").val();
		var endDay = $("#endDay").val();
		$.ajax({
			url : '${basePath}/curAccountController/tab1InterestDetail/' + pageNo + '.html',
			type : 'post',
			data : {beginDay:beginDay,endDay:endDay},
			dataType : 'text',
			success : function(data) {
				$("#containerRight").html(data);

			},
			error : function(data) {
				layer.msg("网络连接异常，请刷新页面或稍后重试！", 1, 5);
			}
		});
	}

	//资金信息 - 分页
	function pageParent_zjxx(pageNo) {
		$.ajax({
			url : '${basePath}/curAccountLogController/tab2Zjxx/' + pageNo + '.html',
			type : 'post',
			dataType : 'text',
			success : function(data) {
				$("#containerRight").html(data);

			},
			error : function(data) {
				layer.msg("网络连接异常，请刷新页面或稍后重试！", 1, 5);
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

	//资金信息- 查询btn 
	function search_zjxx(pageNum) {
		var dataParam = {
			beginDay : $('#beginDay').val(),
			endDay : $('#endDay').val(),
			type : $("#select_type option:selected").val()
		};
		$.ajax({
			url : '${basePath}/curAccountLogController/tab2Zjxx/' + pageNum + '.html',
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

	/**
	 * 切换选项卡的时候样式处理
	 */
	function processTabStyle(obj) {
		$("#topupInnerDiv").find("li").removeClass("men_li");
		$(obj).parent().addClass("men_li");
	}

	/**
	 * 活期宝转出
	 */
	function curOut() {
		$.ajax({
			url : '${basePath}/judgeIsCanOut.html',
			data : {},
			type : 'post',
			dataType : 'json',
			success : function(data) {
				if (data.code == "1" || data.code == 1) {
					$.layer({
						type : 2,
						fix : false,
						shade : [ 0.6, '#E3E3E3', true ],
						shadeClose : true,
						border : [ 1, 0.2, '#272822', true ],
						title : [ '', false ],
						offset : [ '150px', '' ],
						area : [ '500px', '300px' ],
						iframe : {
							src : '${basePath}/enterCurOut/1.html'
						},
						close : function(index) {
							window.open("${path}/curAccountController/curAccountInterest.html", "_self");
							layer.close(index);
						}
					});
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
					$.layer({
						type : 2,
						fix : false,
						shade : [ 0.6, '#E3E3E3', true ],
						shadeClose : true,
						border : [ 1, 0.2, '#272822', true ],
						title : [ '', false ],
						offset : [ '150px', '' ],
						area : [ '550px', '250px' ],
						iframe : {
							src : '${basePath}/curInController/enterCurIn/1.html'
						},
						close : function(index) {
							window.open("${path}/curAccountController/curAccountInterest.html", "_self");
							layer.close(index);
						}
					});
				} else {
					if ("0" == data.code || "-1" == data.code || "-2" == data.code || "-3" == data.code || "-4" == data.code || "-5" == data.code) {
						layer.msg(data.message, 1, 5, function() {
							if ("0" == data.code) {
								window.location.href = "${path}/member/toLoginPage.html";
							} else if ("-1" == data.code) {
								window.location.href = "${path}/AccountSafetyCentre/safetyIndex.html";
							} else if ("-2" == data.code) {
								window.location.href = "${path}/account/approve/realname/toRealNameAppro.html";
							} else if ("-4" == data.code) {
								window.location.href = "${path}/account/safe/toSetPayPwd.html";
							} else if ("-5" == data.code) {
								window.location.href = "${path}/bankinfo/toBankCard.html";
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

</script>
</html>
