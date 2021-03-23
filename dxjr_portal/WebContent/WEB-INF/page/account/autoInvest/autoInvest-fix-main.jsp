<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/page/common/taglib.jsp"%>
 <div class="menucont" style="clear:both">
 <div class="tbl-cont tal-none">
     <div class="product-deatil clearfix ">
            <div class="zdtz-title">我的设置</div>
			<c:if test="${empty auto}">
			    <table width="100%" border="0" cellspacing="0" cellpadding="0"  class="table center tbtr03">
                  <thead>
	                  <tr class="tbl-title">
	                    <td>排队号</td>
	                    <td>是否启用</td>
	                    <td>投宝方式</td>
	                    <td>投宝额度(元)</td>
	                    <td>定期宝期限</td>
	                    <td>操作</td>
	                  </tr>
                  </thead>
                </table>
			    <div class="zdtz-none"><p>您还未设置自动投宝</p>
			      <a href="javascript:void(0);" onclick="settingFixAuto();" data-reveal-id="setFix" data-animation="fade" class="btn btn-blue mt20">立即设置</a>
			    </div>
            </c:if>
             <c:if test="${!empty auto}">
                <table width="100%" border="0" cellspacing="0" cellpadding="0"  class="table center tbtr03">
                  <thead>
                  <tr class="tbl-title">
                    <td>排队号</td>
                    <td>是否启用</td>
                    <td>投宝方式</td>
                    <td>投宝额度(元)</td>
                    <td>定期宝期限</td>
                    <td>操作</td>
                  </tr>
                  </thead>
                  <tbody>
                  <tr>
                    <td>  
                         <c:if test="${auto.rownum!=null}">第&nbsp;<font>${auto.rownum}</font>&nbsp;位 </c:if>
				         <c:if test="${auto.rownum==null}">无</c:if>
				    </td>
                    <td>
                         <c:if test="${auto.status==0 }">未启用</c:if>
		                 <c:if test="${auto.status==1 }">已启用</c:if>
		            </td>
                    <td>  
                         <c:if test="${auto.autoTenderType==1 }">按金额投宝</c:if>
						  <c:if test="${auto.autoTenderType==2 }">按账户余额投宝</c:if> 
				   </td>
                    <td><fmt:formatNumber value="${auto.limitMoney}" pattern="#,##0.00"/></td>
                    <td>${auto.fixLimitStr }</td>
                    <td>
                        <c:if test="${auto.status==0 }"><a href="javascript:void(0);" class="zdtz-btn mr10" onclick="enabledFixBorrow('${auto.id}',1);">启用</a></c:if>
		                <c:if test="${auto.status==1 }"><a href="javascript:void(0);" class="zdtz-btn mr10" onclick="enabledFixBorrow('${auto.id}',0);">停用</a></c:if>
                    	<a href="javascript:void(0);" class="zdtz-btn mr10"  data-reveal-id="setFix" data-animation="fade" onclick="modifyFixBorrow('${auto.id}');">修改</a>
                    	<a href="javascript:void(0);"  class="zdtz-btn"  onclick="deleteFixBorrow('${auto.id}');">删除</a></p>
                  </tr>
                  </tbody>
                </table>
             </c:if>
        </div>
        <div class="product-deatil top-border">
            <div class="zdtz-title">自动投宝记录</div>
            <div class="tz-btablebox"></div>     
     </div>
     
</div>
</div>

<script type="text/javascript">
var _load;
$(function(){
	$.ajax({
		url : '${basePath}/myaccount/autoInvestFix/queryAutoFixTenterLog/0.html',
		type : 'post',
		dataType : 'html',
		success : function(data){
			$(".tz-btablebox").html(data);
		},
		error : function(data) {
			layer.msg("网络连接异常，请刷新页面或稍后重试！", 2, 5);
	    }
	});
});
//设置自动投宝功能
function settingFixAuto(){
		 
	$.ajax({
		url : '${basePath}/myaccount/autoInvestFix/investFixSeting.html',
		type : 'post',
		dataType : 'html',
		success : function(data) {
			$("#setFix").html(data);
		},
		error : function(data) {
			layer.msg('网络连接异常，请刷新页面或稍后重试！', 1, 5);
		}
	});
	 
}
//修改自动投宝
function modifyFixBorrow(id){
	$.ajax({
		url : '${basePath}/myaccount/autoInvestFix/investFixSeting.html?id='+id,
		type : 'post',
		dataType : 'html',
		success : function(data) {
			$("#setFix").html(data);
		},
		error : function(data) {
			layer.msg('网络连接异常，请刷新页面或稍后重试！', 1, 5);
		}
	});
}
//停用或启用
function enabledFixBorrow(id,status){
	
	if(layer.confirm(status == 0 ? "确认要【停用】吗？":"确认要【启用】吗？",function(){
		
		var url="${path}/myaccount/autoInvestFix/enabledFixBorrow.html?status="+status+"&id="+id;
		$.ajax({
			url : url,
			type : 'post',
			async:false,
			dataType : 'json',
			beforeSend:function(){
				 _load = layer.load('处理中..');
			}, 
			success : function(result) {
				layer.close(_load);
				if (result.code == '1') {
					layer.msg(result.message, 1, 1, function() {
						autoInvestFix();
					});
				}else  {
					layer.msg(result.message, 1, 5);
				}
			},
			error : function(result) {
				layer.msg('网络连接超时,请您稍后重试.', 1, 5);
				 
		    }
		});
		
	}));
}
//删除
function deleteFixBorrow(id){
	if(layer.confirm("确认要删除吗？",function(){
		$.ajax({
			url : '${path}/myaccount/autoInvestFix/deleteFixBorrow.html?id='+id,
			type : 'post',
			async:false,
			dataType : 'json',
			beforeSend:function(){
				 _load = layer.load('处理中..');
			}, 
			success : function(result) {
				layer.close(_load);
				if (result.code == '1') {
					layer.msg(result.message, 1, 1, function() {
						 window.location.href="${basePath}/myaccount/autoInvest/autoInvestMain.html?tab=2";
						autoInvestFix();
					});
				}else  {
					layer.msg(result.message, 1, 5);
				}
			},
			error : function(result) {
				layer.msg('网络连接超时,请您稍后重试.', 1, 5);
		    }
		});
	}));
}
</script >