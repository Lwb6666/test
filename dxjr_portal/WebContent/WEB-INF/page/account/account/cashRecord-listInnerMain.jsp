<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/page/common/taglib.jsp"%>
<style type="text/css">
.type1{float:left;width:110px; text-align:left;}
.type1 a{ font-size:14px; padding:3px 12px; color:#666666; line-height:40px;}
.type1 a:hover,.type1 a.active{ text-decoration:none; background:#45b8ef; border-radius:2px; color:#fff;}
</style>
<div class="tbl-cont">
	<div class="grid-1100">
		<div class="product-deatil clearfix ">
			<div class="tz-dqb2 clearfix">
				<div class="col clearfix">
					<span class="fl gary2">提现总额：</span>
					<div class="btn-box-bg">
						<span class="orange" id="cashTotal"></span><span>元</span>
					</div>
				</div>
				<div class="col clearfix">
					<span class="fl gary2">账户类型：</span>
					<div class="btn-box-bg">
						<div class="type1">
							<a href="javascript:void(0);" class="active" value="">全部</a>
						</div>
						<div class="type1">
							<a href="javascript:void(0);" value="0">非存管</a>
						</div>
						<div class="type1">
							<a href="javascript:void(0);" value="1">存管</a>
						</div>
					</div>
				</div>
				<div class="col clearfix">
					<span class="fl gary2">提现状态：</span>
					<div class="btn-box-bg">
						<div class="type"><a href="javascript:void(0);" class="active" value="">全部</a></div>
						<div class="type"><a href="javascript:void(0);" value="0">提现申请</a></div>
						<div class="type"><a href="javascript:void(0);" value="1">审核通过</a></div>
						<div class="type"><a href="javascript:void(0);" value="-1">审核失败</a></div>
						<div class="type"><a href="javascript:void(0);" value="2">打款成功</a></div>
						<div class="type"><a href="javascript:void(0);" value="3">取消提现</a></div>
						<div class="type"><a href="javascript:void(0);" value="5">打款失败</a></div>
						<div class="type"><a href="javascript:void(0);" value="4">提现作废</a></div>
					</div>
				</div>
				<div class="col clearfix">
					<span class="fl gary2">提现日期：</span>
					<div class="btn-box-bg">
						<div class="term"><a href="javascript:void(0);" value="6">全部</a></div>
						 <div class="term">
							 <input  type="text" name="beginTime" id="beginTime2" value="" onClick="WdatePicker()"/>
							 <span>至</span>
	        				 <input  type="text" name="endTime" id="endTime2" value="" onClick="WdatePicker()"/>
	        				 <button type="button" class="btn-middle btn-blue mt5" onclick="queryByTime1();">查询</button>
	        			 </div>
						<div class="term"><a href="javascript:void(0);" class="active" value="7">今天</a></div>
						<div class="term"><a href="javascript:void(0);" value="8">3个月</a></div>
						<div class="term"><a href="javascript:void(0);" value="9">6个月</a></div>
				</div>
			</div>
			<div id="main_content2" class="recharge change" style="clear: both">
			</div>
		</div>
	</div>
	<script type="text/javascript">
	var  CashStatus;
	  var  CashTime;
	  var  Custody;
		//获取标题文本框内容
		$(document).ready(function(){
				//获取是否存管状态
				$(".type1").click(function(){ //注册click事件
					$(".type1").children('a').removeClass("active");
					$(this).siblings().removeClass("active"); //移除同辈节点的class
					$(this).children('a').addClass("active"); //给当前节点添加class
					Custody = $(this).children("a").attr("value"); //赋值
					searchcashRecord1(); 
				});
				//获取提现状态
				$(".type").click(function(){ //注册click事件
					$(".type").children('a').removeClass("active");
					$(this).siblings().removeClass("active"); //移除同辈节点的class
					$(this).children('a').addClass("active"); //给当前节点添加class
					CashStatus = $(this).children("a").attr("value"); //赋值
					searchcashRecord1(); 
				});
				//获取提现时间
				$(".term").click(function(){ //注册click事件
					$(".term").children('a').removeClass("active");
					$(this).siblings().removeClass("active"); //移除同辈节点的class
					$(this).children('a').addClass("active"); //给当前节点添加class
					CashTime = $(this).children("a").attr("value"); //赋值
					searchcashRecord1(); 
				});
				loadcashRecord2();
		});
		/**
		 * 默认加载全部状态和当天的记录
		 */
		function loadcashRecord2() {
			$.ajax({
				url : '${basePath}/account/topup/toCashRecordInner/1.html',
				data : {
					defaultTime:0
				},
				type : 'post',
				dataType : 'text',
				success : function(data) {
					$("#main_content2").html(data);

				},
				error : function(data) {
					layer.msg("网络连接异常，请刷新页面或稍后重试！", 1, 5);
				}
			});
		}
		/**
		 * 提现记录查询
		 */
		function searchcashRecord1(){
			$.ajax({
				url : '${basePath}/account/topup/toCashRecordInner/1.html',
				data :{
					cashStatus:CashStatus,
					cashTime:CashTime,
					 custody:Custody,
	                beginTime: $("#beginTime2").val(),
	                endTime: $("#endTime2").val(),
	                defaultTime:1
				} ,
				type : 'post',
				dataType : 'text',
				success : function(data){
					$("#main_content2").html(data);
				},
				error : function(data) {
					layer.msg("网络连接异常，请刷新页面或稍后重试！", 1, 5);
			    }
			});
		}
		/**
		 * 提现记录翻页
		 */
		 function turncashPageParent(pageNo){
			 $.ajax({
					url : '${basePath}/account/topup/toCashRecordInner/'+pageNo+'.html',
					data :{
						cashStatus:CashStatus,
						cashTime:CashTime,
						 custody:Custody,
		                beginTime: $("#beginTime2").val(),
		                endTime: $("#endTime2").val(),
		                defaultTime:1
					 }
					 ,
					type : 'post',
					dataType : 'text',
					success : function(data){
						$("#main_content2").html(data);
					},
					error : function(data) {
						layer.msg("网络连接异常，请刷新页面或稍后重试！", 1, 5);
				    }
				});
		}
		  function queryByTime1(){
		    	$(".term").children('a').removeClass("active");
		    	$.ajax({
					url : '${basePath}/account/topup/toCashRecordInner/1.html',
					data :{
						cashStatus:CashStatus,
		                beginTime: $("#beginTime2").val(),
		                endTime: $("#endTime2").val(),
		                defaultTime:1
					} ,
					type : 'post',
					dataType : 'text',
					success : function(data){
						$("#main_content2").html(data);
					},
					error : function(data) {
						layer.msg("网络连接异常，请刷新页面或稍后重试！", 1, 5);
				    }
				});
		    }
	</script>