<%@page import="com.dxjr.utils.DateUtils"%>
<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/page/common/taglib.jsp"%>
<!doctype html>
<html>
<head>
<meta charset="utf-8">
<title>内转系统登录申请</title>
<%@ include file="/WEB-INF/page/common/public4.jsp"%>

<link href="${basePath}/css/stock/myaccount.css" rel="stylesheet" type="text/css">
<link rel="stylesheet" href="${basePath}/iconfonts/iconfont.css">

</head>

<body>
	<%@ include file="/WEB-INF/page/common/header.jsp"%>
	<!--头部end-->
	<div id="myaccount">
		<div class="topmenu">
			<ul>
				<li class="active"><a href="${basePath}/myaccount/toIndex.html">账户总览</a></li>
				<li><a href="${basePath}/dingqibao/myAccount.html">投资管理</a></li>
				<li><a href="${basePath}/lottery_use_record/lott_use_record.html">账户奖励</a></li>
				<li><a href="${path }/AccountSafetyCentre/safetyIndex.html">安全中心</a></li>
			</ul>
		</div>
		<div class="wraper mt20">
			<!--table-->
			<div class="prduct-menu">
				<div class="menu-tbl">
					<ul class="col2">
						<li class="active">内转登录申请</li>
						<li>申请历史</li>
					</ul>
				</div>
				<div class="recharge change" style="clear: both">
					<div class="tbl-cont">
							<div class="equity">
								<div class="equity-text">
									<label><span class="red">*</span>平台用户名：</label> <em><i
										class="iconfont gary2">&#xe61f;</i></em> <input id="" type="text"
										placeholder="${userInfo.userName}" value="${userInfo.userName}" disabled>
								</div>

								<div class="equity-text">
									<label><span class="red">*</span>姓名：</label> <em><i
										class="iconfont gary2">&#xe622;</i></em> <input id="" type="text"
										placeholder="${userInfo.securityRealName}" value="${userInfo.securityRealName}" disabled>
								</div>
								
								<div class="equity-text">
									<label><span class="red">*</span>证件号码：</label> <em><i
										class="iconfont gary2">&#xe620;</i></em> <input id="" type="text"
										placeholder="${userInfo.securityIdCardNo}" value="${userInfo.securityIdCardNo}" disabled>
								</div>
								<div class="equity-text">
									<label><span class="red">*</span>手机号码：</label> <em><i
										class="iconfont gary2">&#xe621;</i></em> <input id="" type="text"
										placeholder="${userInfo.securitymobileNum}" value="${userInfo.securitymobileNum}" disabled>
								</div>
								<c:if test="${shareHolder==null}">
								<div class="equity-text">
									<label><span class="red">*</span>待收：</label> <em><i
										class="iconfont gary2">&#xe626;</i></em> <input id="" type="text"
										placeholder="${collect}元" value="${collect}元" disabled>
								</div>
								</c:if>
								
								<div class="equity-text">
									<div class="equity-check">
										<input name="isProtocol" id="J_isprotocol" class="check" type="checkbox"
											checked="checked" value="1">已阅读并同意 《<a href="javascript:toStockXiyi();"
											target="_blank">顶玺金融内转交易平台使用协议</a>》
									</div>

								</div>

								<div class="equity-btn">
								<input type="hidden" value="1" id="J_type"/>
									<button type="button" class="enter" id="btnSaveBank" onClick="saveApply();">确定</button>
								</div>
							</div>

					</div>
					<div class="tbl-cont" style="display: none;overflow:inherit !important; ">
						<div class="grid-1100"  >
							<div class="product-deatil" style="overflow:inherit !important; ">
								<div class="tz-equ clearfix">
									<div class="col clearfix">
										<span class="fl gary2">状态：</span>
										<div class="btn-box-bg">
											<div class="term">
												<div class="form-col2">
													<dl class="colright select">
														<dt id="J_status" strtusValue="">全部</dt>
														<dd>
															<ul>
																<li><a  href="javascript:selectSetVal('');">全部</a></li>
											                    <li><a  href="javascript:selectSetVal(1);">待审核</a></li>
											                    <li><a  href="javascript:selectSetVal(2);">审核通过</a></li>
											                    <li><a  href="javascript:selectSetVal(3);">审核不通过</a></li>
											                    <li><a  href="javascript:selectSetVal(-1);">已作废</a></li>
															</ul>
														</dd>
													</dl>
												</div>

											</div>
										</div>
										<span class="fl gary2">申请时间：</span>
										<div class="btn-box-bg">
											<div class="term">
												<input type="text" onclick="WdatePicker({maxDate:'#F{$dp.$D(\'J_endTime\')}'})" onClick="WdatePicker();" id="J_beginTime" > 至 <input id="J_endTime" onclick="WdatePicker({minDate:'#F{$dp.$D(\'J_beginTime\')}'})" type="text">
											<a href="javascript:serchApply();" style="line-height: 34px; width: 80px;" class="btn btn-blue f16">查询</a>
											</div>
										</div>
									</div>
								</div>
								<!--  列表初始化数据 -->
              					<div id="applyDataList"> </div>  
								
							</div>
						</div>

					</div>
				</div>
			</div>
		</div>
	</div>
	<div class="clearfix bompd60"></div>
	<%@ include file="/WEB-INF/page/common/footer.jsp"%>


</body>
<script type="text/javascript">
var status;
var beginTime;
var endTime;
window.onload=function(){
	serchApply();
};
function selectSetVal(val){
	$("#J_status").attr("strtusValue",val);
}
function saveApply(){
	var isProtocol;
	var type=$("#J_type").val();
	if($('#J_isprotocol').is(':checked')) {
		isProtocol=1;
	}else{
		layer.msg("请阅读并同意协议！");
    	 return false;
    }
	$("#btnSaveBank").removeAttr("onclick");
	var _load = layer.load('处理中..');
	$.ajax({
		url : '${basePath}/stockApply/registerStock.html',
		data : {
			'isProtocol' : isProtocol,
			'type' : type
		},
		type : 'post',
		dataType : 'json',
		success : function(data) {
			$("#btnSaveBank").attr("onclick","saveApply()");
			layer.close(_load);
			if (data.code == '0') {
				layer.msg(data.message, 2, 5);
				return;
			}else if(data.code == '1'){
				location.href="${path}/stockAccount/accountIndex.html";
			}else{
				layer.msg('提交申请成功,请耐心等待！ ', 2, 1, function() {
					runtime = 0;
					window.parent.location.href = window.parent.location.href;
				});
			}
		},
		error : function(data) {
			$("#btnSaveBank").attr("onclick","saveApply()");
			layer.close(_load);
			layer.msg("网络连接异常，请刷新页面或稍后重试！");
	    }
	});
}

function serchApply(){
	status = $("#J_status").attr("strtusValue");
	beginTime = $("#J_beginTime").val();
	endTime = $("#J_endTime").val();
	searchApplyList(1);
}
function searchApplyList(pageNum) {
	$.ajax({
		url : '${basePath}/stockApply/queryApplyList.html',
		data : {
			pageNum : pageNum,
			pageSize : 10,
			type:1,
			'status' : status,
			'beginTime':beginTime,
			'endTime':endTime
		},
		type : 'post',
		dataType : 'text',
		success : function(data) {
			$("#applyDataList").html(data);
		},
		error : function(data) {
			layer.msg("网络连接异常，请刷新页面或稍后重试！");
		}
	});
}

function searchApprove(applyId,type) {
	$.layer({
		type : 2,
		fix : false,
		shade : [ 0.6, '#E3E3E3', true ],
		shadeClose : true,
		border : [ 10, 0.7, '#272822', true ],
		title : [ '', false ],
		offset : [ '50px', '' ],
		area : [ '500px', '450px' ],
		iframe : {
			src : '${basePath}/stockApply/queryApplyDetail.html?applyId='+applyId+'&type='+type
		},
		close : function(index) {
			layer.close(index);
		}
	});
}

function toStockXiyi() {
	$.layer({
		type : 2,
		fix : false,
		shade : [ 0.6, '#E3E3E3', true ],
		shadeClose : true,
		border : [ 10, 0.7, '#272822', true ],
		title : [ '', false ],
		offset : [ '50px', '' ],
		area : [ '500px', '450px' ],
		iframe : {
			src : '${basePath }/stockApply/toStockXiyi.html'
		},
		close : function(index) {
			layer.close(index);
		}
	});
}
</script>
</html>
