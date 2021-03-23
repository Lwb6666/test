<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/page/common/taglib.jsp"%>
<div class="menucont" style="clear: both">
	<div class="tbl-cont tal-none">
		<div class="product-deatil clearfix ">
			<div class="zdtz-title">我的设置</div>
			<c:if test="${empty autoInvestConfigVoList}">
					<table width="100%" border="0" cellspacing="0" cellpadding="0" class="table center tbtr">
						<thead>
							<tr class="tbl-title">
								<td>排队号</td>
								<td>是否启用</td>
								<td>投标方式</td>
								<td>投标额度(元)</td>
								<td>最低投标金额(元)</td>
								<td>操作</td>
							</tr>
						</thead>
					 </table>
			    <div class="zdtz-none"><p>您还未设置自动投标</p>
			      <a href="javascript:void(0);" onclick="settingAuto();" data-reveal-id="setInvest" data-animation="fade" class="btn btn-blue mt20">立即设置</a>
			    </div>
            </c:if>
            <c:if test="${!empty autoInvestConfigVoList}">
			<table width="100%" border="0" cellspacing="0" cellpadding="0"
				class="table center tbtr">
				<thead>
					<tr class="tbl-title">
						<td>排队号</td>
						<td>是否启用</td>
						<td>投标方式</td>
						<td>投标额度(元)</td>
						<td>最低投标金额(元)</td>
						<td>操作</td>
					</tr>
				</thead>
				<tbody>
				 <c:forEach items="${autoInvestConfigVoList }" var="autoInvestConfigVo" varStatus="sta" step="1">
					<tr>
						<td>
						  <c:if test="${autoInvestConfigVo.rownum!=null}">第&nbsp;<font color="red">${autoInvestConfigVo.rownum}</font>&nbsp;位 </c:if>
		                  <c:if test="${autoInvestConfigVo.rownum==null}">无</c:if>
		                </td>
						<td> 
						  <c:if test="${autoInvestConfigVo.status==0 }">未启用</c:if>
		         		  <c:if test="${autoInvestConfigVo.status==1 }">已启用</c:if>
		          		</td>
						<td> 
							<c:if test="${autoInvestConfigVo.tender_type==1 }">按金额投标</c:if>
						  <c:if test="${autoInvestConfigVo.tender_type==2 }">按比例投标</c:if>
						  <c:if test="${autoInvestConfigVo.tender_type==3 }">按余额投标</c:if>
				        </td>
						<td> 
						 <c:if test="${autoInvestConfigVo.tender_type==1 }"><fmt:formatNumber value="${autoInvestConfigVo.tender_account_auto}" pattern="#,##0.00"/></c:if>
		  	         	 <c:if test="${autoInvestConfigVo.tender_type==2 }">${autoInvestConfigVo.tender_scale}%</c:if>
			        	 <c:if test="${autoInvestConfigVo.tender_type==3 }">
			        	 <c:choose>
			        	 	<c:when test="${autoInvestConfigVo.custodyFlag==1}">
			        	 	 <fmt:formatNumber value="${accountVo.useMoney}" pattern="#,##0.00"></fmt:formatNumber>
			        	 	</c:when>
			        	 	<c:when test="${autoInvestConfigVo.custodyFlag==2}">
			        	 	<fmt:formatNumber value="${accountVo.eUseMoney}" pattern="#,##0.00"></fmt:formatNumber>
			        	 	</c:when>
			        	 	<c:when test="${autoInvestConfigVo.custodyFlag==3}">
			        	 	<fmt:formatNumber value="${accountVo.eUseMoney+accountVo.useMoney}" pattern="#,##0.00"></fmt:formatNumber>
			        	 	</c:when>
			        	 	<c:otherwise>
			        	 	<fmt:formatNumber value="${accountVo.useMoney}" pattern="#,##0.00"></fmt:formatNumber>
			        	 	</c:otherwise>
			        	 </c:choose>
			        	</c:if>
		                </td>
						<td><fmt:formatNumber value="${autoInvestConfigVo.min_tender_account}" pattern="#,##0.00"/></td>
						<td>
						     <c:if test="${autoInvestConfigVo.status==0 }">
			                   <a href="javascript:void(0);" class="zdtz-btn mr10" onclick="updateAutoInvest(${autoInvestConfigVo.id},1);">启用</a>
			                 </c:if>
				              <c:if test="${autoInvestConfigVo.status==1 }">   
				                 <a href="javascript:void(0);" class="zdtz-btn mr10" onclick="updateAutoInvest(${autoInvestConfigVo.id},0);">停用</a>
				              </c:if>
				              <a href="javascript:void(0);" class="zdtz-btn mr10" data-reveal-id="setInvest" data-animation="fade" onclick="toUpdateAutoInvest('${autoInvestConfigVo.id}');">修改</a>
				              <a href="javascript:void(0);" class="zdtz-btn" onclick="del(${autoInvestConfigVo.id});">删除</a>
					</td>
					</tr>
				</c:forEach>
				</tbody>
			</table>
	    </c:if>
		</div>
		 <div class="product-deatil top-border">
             <div class="zdtz-title">自动投标记录</div>
          </div>
          <div class="tz-btablebox"></div>
</div>
</div>

<script type="text/javascript">
$(function(){
	
	$.ajax({
		url : '${basePath}/myaccount/autoInvest/queryAutoTenterLog/10.html',
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
var _load;
/**
 * 启用或停用自动投标规则
 */
function updateAutoInvest(id,status){
	$.ajax({
		url : '${path}/myaccount/autoInvest/judegOverTen.html?id='+id,
		type : 'post',
		async:false,
		dataType : 'json',
		success : function(result) {
			if (result.code == '1') {
			if(layer.confirm(status == 0 ? "确认要【停用】吗？":"确认要【启用】吗？",function(){
				var url="${path}/myaccount/autoInvest/updateAutoInvest.html?id="+id+"&status="+status;
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
								autoInvestList();
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
			}else if(result.code == '2'){
				layer.msg(result.message, 1, 5);
			}else{
				layer.msg(result.message, 1, 5);
			}
	},
	error : function(result) {
		layer.msg('网络连接超时,请您稍后重试.', 1, 5);
		 
    }
});
}
//删除自动投标
function del(id){
	 
	 $.ajax({
			url : '${path}/myaccount/autoInvest/judegOverTen.html?id='+id,
			type : 'post',
			async:false,
			dataType : 'json',
			success : function(result) {
				 
				if (result.code == '1') {
					if(layer.confirm("确认要删除吗？",function(){
						$.ajax({
							url : '${path}/myaccount/autoInvest/delAutoInvest/'+id+'.html',
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
									    window.location.href="${basePath}/myaccount/autoInvest/autoInvestMain.html?tab=1";
										autoInvestList();
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
				}else  {
					layer.msg(result.message, 1, 5);
				}
			},
			error : function(result) {
				layer.msg('网络连接超时,请您稍后重试.', 1, 5);
				 
		    }
		});
}
//修改自动投标
function toUpdateAutoInvest(id){
	 
	 $.ajax({
			url : '${path}/myaccount/autoInvest/judegOverTen.html?id='+id,
			type : 'post',
			async:false,
			dataType : 'json',
			success : function(result) {
				if (result.code == '1') {
					$.ajax({
						url : '${basePath}/myaccount/autoInvest/autoInvestSeting.html?id='+id,
						type : 'post',
						dataType : 'html',
						success : function(data) {
							$("#setInvest").html(data);
						},
						error : function(data) {
							layer.msg('网络连接异常，请刷新页面或稍后重试！', 1, 5);
						}
					});
				}else  {
					layer.msg(result.message, 1, 5);
				}
			},
			error : function(result) {
				layer.msg('网络连接超时,请您稍后重试.', 1, 5);
				 
		    }
		});
}
</script>