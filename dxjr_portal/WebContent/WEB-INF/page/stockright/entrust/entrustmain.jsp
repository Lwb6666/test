<%@page import="com.dxjr.utils.DateUtils"%>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/page/common/taglib.jsp"%>
<!doctype html>
<html>
<head>
<%@ include file="/WEB-INF/page/stockright/common/public.jsp"%>
<meta charset="utf-8">
<title>我的内转账户_挂单记录</title>
</head>
<body>
<div class="main-offsetHeight">
	<!-- header start   -->
	<%@ include file="/WEB-INF/page/stockright/common/header.jsp"%>
	<!-- header end     -->
	
<!-- main start	 -->
<div class="product-wrap">
	<div class="grid-1100">
        <div class="product-deatil clearfix ">
            <h1 class="f16 bule">挂单记录</h1>
            <div class="record clearboth">
             	<div class="record-box"><span class="fl">挂单类型：</span>
                 <div class="form-col2 fl">
                <dl class="colright select" >
                <dt id="J_entrustType" typeValue="">全部</dt>
                <dd>
                  <ul>
                    <li><a  href="javascript:selectSetVal('');">全部</a></li>
                    <li><a  href="javascript:selectSetVal(1);">认购</a></li>
                    <li><a  href="javascript:selectSetVal(2);">转让</a></li>
                  </ul>
                </dd>
              </dl>
              </div>
                
                </div>
                <div class="record-box"><span class="fl">状态：</span>
                 <div class="form-col2 fl">
                <dl class="colright select" >
                <dt id="J_entrustStatus" strtusValue="">全部</dt>
                <dd>
                  <ul> 
                  	<li><a  href="javascript:selectStatusSetVal('');">全部</a></li>
                    <li><a href="javascript:selectStatusSetVal(1)">已挂单</a></li>
                    <li><a href="javascript:selectStatusSetVal(2)">部分成交</a></li>
                    <li><a href="javascript:selectStatusSetVal(3)">全部成交</a></li>
                    <li><a href="javascript:selectStatusSetVal(-1)">已撤销</a></li>
                  </ul>
                </dd>
              </dl>
              </div>
                
                </div>
                <div class="record-box"><span class="fl">申请时间：</span>
                <input class="form-inpyt-sm" onclick="WdatePicker({maxDate:'#F{$dp.$D(\'J_endTime\')}'})" name="startTime" id="J_beginTime" style="width:100px" type="text">  
                	至 
                <input class="form-inpyt-sm" name="endTime" id="J_endTime" onclick="WdatePicker({minDate:'#F{$dp.$D(\'J_beginTime\')}'})" style="width:100px" type="text"></div>
                <div class="record-box">
                <a href="javascript:serchEntrust()" class="btn btn-blue f16">查询</a>
               <!--  <a href="#" class="btn btn-orange f16">买入</a> 
                <a href="#" class="btn btn-blue f16">卖出</a> -->
                
                </div>
             </div>
             
             <div id="J_entrustDataList" style="min-height: 150px;">
             	
             </div>
        </div>
        
    </div>
</div>
</div>
<!-- main end	 -->
	
	<!-- footer start   -->
	<%@ include file="/WEB-INF/page/stockright/common/footer.jsp"%>
	<!-- footer end     -->
</body>
<script type="text/javascript">
var entrustType;
var status;
var beginTime;
var endTime;
$(function(){  
	menuSelect(4);
	serchEntrust();
});
function serchEntrust(){
	entrustType = $("#J_entrustType").attr("typeValue");
	status = $("#J_entrustStatus").attr("strtusValue");
	beginTime = $("#J_beginTime").val();
	endTime = $("#J_endTime").val();
	findPage(1);
}
function findPage(pageNum) {
	$.ajax({
		url : '${basePath}/stockSeller/queryPageList.html',
		data : {
			pageNum : pageNum,
			entrustType:entrustType,
			status:status,
			startDate:beginTime,
			endDate:endTime
		},
		type : 'post',
		dataType : 'text',
		success : function(data) {
			$("#J_entrustDataList").html(data);
		},
		error : function(data) {
			layer.msg("网络连接异常，请刷新页面或稍后重试！", 2, 5);
		}
	});
}

//撤单 
function revokeEntrust(entrustId){
	layer.confirm("确定要撤销挂单?", function(index){ 
		$.ajax({
			url : '${basePath}/stockSeller/revokeEntrust.html',
			data : {
				id : entrustId
			},
			type : 'post',
			dataType : 'text',
			success : function(data) {
				var jsonData = eval("("+data+")" );
				if(jsonData.code=='00000'){
					layer.msg("撤单成功！", 2, 1);
					serchEntrust();
				}else{
					layer.msg(jsonData.message,2, 5);
				}
			},
			error : function(jsonData) {
				layer.msg("网络连接异常，请刷新页面或稍后重试！", 2, 5);
			}
		});
		
	});
}

function selectSetVal(val){
	$("#J_entrustType").attr("typeValue",val);
}
function selectStatusSetVal(val){
	$("#J_entrustStatus").attr("strtusValue",val);
}
</script>
</html>
