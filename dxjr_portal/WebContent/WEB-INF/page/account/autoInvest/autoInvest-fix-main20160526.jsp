<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/page/common/taglib.jsp"%>
<div class="myid whitebg nobordertop" style="padding-bottom:50px;" >
        
          
          	<div class="auto-tzbox mgtop">
            <div class="auto-tztitle "> 我的设置 </div>
            </div>
           <c:if test="${empty auto}">
            <div class="none-boxsj">
            <p>你还未设置自动投定期宝</p>
            <a href="javascript:void(0);" onclick="settingFixAuto();">立即设置</a> </div>
           </c:if>
           <c:if test="${!empty auto}">
           <ul class="table-autotb">
                	<li class="autotb-box1">
                    	<p class="ptitle">排队号</p>
                    	<p class="text-bg">
		                  <c:if test="${auto.rownum!=null}">第&nbsp;<font color="red">${auto.rownum}</font>&nbsp;位 </c:if>
				          <c:if test="${auto.rownum==null}">无</c:if>
                        </p>
                    </li>
                	<li class="autotb-box1">
                    	<p class="ptitle">是否启用</p>
                    	<p class="text-bg">
                    	 <c:if test="${auto.status==0 }">未启用</c:if>
		                 <c:if test="${auto.status==1 }">已启用</c:if>
                    	</p>
                    </li>
                	<li class="autotb-box1">
                    	<p class="ptitle">投宝方式</p>
                    	<p class="text-bg">
                    	  <c:if test="${auto.autoTenderType==1 }">按金额投宝</c:if>
						  <c:if test="${auto.autoTenderType==2 }">按账户余额投宝</c:if>
                    	</p>
                    </li>
                	<li class="autotb-box2">
                    	<p class="ptitle">投宝额度（元）</p>
                    	<p class="text-bg">
                    	  <fmt:formatNumber value="${auto.limitMoney}" pattern="#,##0.00"/>
                    	</p>
                    </li>
                	<li  class="autotb-box2">
                    	<p class="ptitle">定期宝期限</p>
                    	<p class="text-bg"> 
                    		<span style="color:#666; margin-right:4px;">${auto.fixLimitStr }</span>
                    	</p>
                    </li>
                	<li  class="autotb-box3" style="border:none;">
                    	<p class="ptitle" style="  text-align:center;">操作</p>
                    	<p class="text-bg">
                    	 <c:if test="${auto.status==0 }"><a href="javascript:void(0);" onclick="enabledFixBorrow('${auto.id}',1);">启用</a></c:if>
		                 <c:if test="${auto.status==1 }"><a href="javascript:void(0);" onclick="enabledFixBorrow('${auto.id}',0);">停用</a></c:if>
                    	<a href="javascript:void(0);" onclick="modifyFixBorrow('${auto.id}');">修改</a>
                    	<a href="javascript:void(0);" onclick="deleteFixBorrow('${auto.id}');">删除</a></p>
                    </li>
                
                </ul>
            </c:if>
          
           
          
          	<div class="auto-tzbox mgtop">
            <div class="auto-tztitle "> 自动投宝记录 </div>
          </div>
          
            <div class="tz-btablebox">
            
            </div>          
 
          <div class="hbtishi">
            <p style="font-size:16px;   background:url(${basePath}/images/icon-tishi.png) no-repeat 0px 10px; text-indent:25px; color:#009dd9; line-height:35px;"> 温馨提示：</p>
            <p>1、用户只能添加1条自动投宝规则，当启用规则时，将自动从最后一名开始排队，系统根据排队号来自动投宝。</p>
            <p>2、当您启用规则在90天内未发生任何自动投宝行为，系统会自动将您已启用的自动投宝规则重新排队。</p>
            <p>3、当定期宝满宝的最后一笔投资是自动投定期宝，且投宝金额小于该用户设定金额时，则该用户的自动投定期宝设置不参与重新排队。</p>
            <p>4、修改自动投定期宝条件和发生流宝情况时，都不会改变自动投定期宝的排名。</p>
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
			$.layer({
			    type : 1,
			    title : '设置自动投定期宝',
			    fix : false,
			    offset:['50px' , ''],
			    area : ['680','510'],//横坐标纵坐标，
			    page : {html : data}
			});
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
			$.layer({
			    type : 1,
			    title : '修改 自动投定期宝',
			    fix : false,
			    offset:['50px' , ''],
			    area : ['680','510'],//横坐标纵坐标，
			    page : {html : data}
			});
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