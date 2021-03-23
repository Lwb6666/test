<%@page import="com.dxjr.utils.DateUtils"%>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/page/common/taglib.jsp"%>
<!doctype html>
<html>
<head>
<%@ include file="/WEB-INF/page/stockright/common/public.jsp"%>
<meta charset="utf-8">
<title>我的内转账户_退出内转名册申请</title>
</head>

<body>
<div class="main-offsetHeight">
	<!-- header start   -->
	<%@ include file="/WEB-INF/page/stockright/common/header.jsp"%>
	<!-- header end     -->
	
<!-- main start	 -->
<div class="product-wrap">
<input type="hidden" value="2" id="J_type"/>
	<div class="grid-1100">
        <div class="product-deatil clearfix ">
            <h1 class="f16 bule">内转名册申请列表</h1>
            <div class="record clearboth">
             	<div class="record-box"><span class="fl">状态：</span>
                 <div class="form-col2 fl">
                <dl class="colright select" >
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
                <div class="record-box"><span class="fl">申请时间：</span><input class="form-inpyt-sm" value="" onclick="WdatePicker({maxDate:'#F{$dp.$D(\'J_endTime\')}'})" id="J_beginTime" style="width:100px" type="text">  至  <input class="form-inpyt-sm" value="" id="J_endTime" onclick="WdatePicker({minDate:'#F{$dp.$D(\'J_beginTime\')}'})" style="width:100px" type="text"></div>
             	<div class="record-box">
             	<a href="javascript:serchApply();" class="btn btn-blue f16">查询</a>
             	<c:if test="${account!=null && account.useStock>=50000}">
             	<a href="javascript:appQuitShareholder();" class="btn btn-blue f16">退出申请</a>
             	</c:if>
             	
             	</div>
              </div>
               <!--  列表初始化数据 -->
              <div id="applyDataList" style="min-height: 150px;"> </div>   
        </div>
    </div>
</div>
</div>
<!--弹层end--->
<!-- main end	 -->
	<!-- footer start   -->
	<%@ include file="/WEB-INF/page/stockright/common/footer.jsp"%>
	<!-- footer end     -->
</body>
<script type="text/javascript">
var status;
var type;
var beginTime;
var endTime;
$(function(){  
	serchApply();
});
function selectSetVal(val){
	$("#J_status").attr("strtusValue",val);
}

function serchApply(){
	status = $("#J_status").attr("strtusValue");
	type = $("#J_type").val();
	beginTime = $("#J_beginTime").val();
	endTime = $("#J_endTime").val();
	findPage(1);
}
function findPage(pageNum) {
	$.ajax({
		url : '${basePath}/stockApply/queryApplyList.html',
		data : {
			pageNum : pageNum,
			pageSize : 10,
			'status' : status,
			'type' : type,
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

function appQuitShareholder() {
	$.layer({
		type : 2,
		fix : false,
		shade : [ 0.6, '#E3E3E3', true ],
		shadeClose : true,
		border : [ 10, 0.7, '#272822', true ],
		title : [ '退出内转名册申请', true ],
		offset : [ '50px', '' ],
		area : [ '500px', '450px' ],
		iframe : {
			src : '${basePath}/stockApply/quitShareholder.html'
		},
		close : function(index) {
			layer.close(index);
		}
	});
}
</script>
</html>
