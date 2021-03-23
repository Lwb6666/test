<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/page/common/taglib.jsp"%>
<div class="myid whitebg nobordertop" style="padding-bottom:10px;border-bottom: 0px;" > 
          <div class="auto-tzbox mgtop">
            <div class="auto-tztitle" > 我的设置 </div>
          </div>
          <c:if test="${empty autoInvestConfigVoList}">
            <div class="none-boxsj">
            <p>你还未设置自动投标</p>
            <a href="javascript:void(0);" onclick="settingAuto();">立即设置</a> </div>
          </c:if>
          <c:if test="${!empty autoInvestConfigVoList}">
          <ul class="table-autotb ">
            <c:forEach items="${autoInvestConfigVoList }" var="autoInvestConfigVo" varStatus="sta" step="1">
            <li class="autotb-box1">
              <p class="ptitle">排队号</p>
              <p class="text-bg">
                  <c:if test="${autoInvestConfigVo.rownum!=null}">第&nbsp;<font color="red">${autoInvestConfigVo.rownum}</font>&nbsp;位 </c:if>
		          <c:if test="${autoInvestConfigVo.rownum==null}">无</c:if>
              </p>
            </li>
            <li class="autotb-box1">
              <p class="ptitle">是否启用</p>
              <p class="text-bg">
                  <c:if test="${autoInvestConfigVo.status==0 }">未启用</c:if>
		          <c:if test="${autoInvestConfigVo.status==1 }">已启用</c:if>
              </p>
            </li>
            <li class="autotb-box1">
              <p class="ptitle">投标方式</p>
              <p class="text-bg">
                  <c:if test="${autoInvestConfigVo.tender_type==1 }">按金额投标</c:if>
				  <c:if test="${autoInvestConfigVo.tender_type==2 }">按比例投标</c:if>
				  <c:if test="${autoInvestConfigVo.tender_type==3 }">按余额投标</c:if>
              </p>
            </li>
            <li class="autotb-box2">
              <p class="ptitle">投标额度（元）</p>
              <p class="text-bg">
                 <c:if test="${autoInvestConfigVo.tender_type==1 }"><fmt:formatNumber value="${autoInvestConfigVo.tender_account_auto}" pattern="#,##0.00"/></c:if>
	  	         <c:if test="${autoInvestConfigVo.tender_type==2 }">${autoInvestConfigVo.tender_scale}%</c:if>
		         <c:if test="${autoInvestConfigVo.tender_type==3 }"><fmt:formatNumber value="${accountVo.useMoney}" pattern="#,##0.00"></fmt:formatNumber></c:if>
              </p>
            </li>
            <li  class="autotb-box2">
              <p class="ptitle">最低投标金额(元)</p>
              <p class="text-bg">
                 <fmt:formatNumber value="${autoInvestConfigVo.min_tender_account}" pattern="#,##0.00"/>
              </p>
            </li>
            <li  class="autotb-box3" style="border:none;">
              <p class="ptitle" style="  text-align:center;">操作</p>
              <p class="text-bg">
              
              <c:if test="${autoInvestConfigVo.status==0 }">
                 <a href="javascript:void(0);" onclick="updateAutoInvest(${autoInvestConfigVo.id},1);">启用</a>
              </c:if>
              <c:if test="${autoInvestConfigVo.status==1 }">   
                 <a href="javascript:void(0);" onclick="updateAutoInvest(${autoInvestConfigVo.id},0);">停用</a>
              </c:if>
	              <a href="javascript:void(0);" onclick="toUpdateAutoInvest(${autoInvestConfigVo.id});">修改</a>
	              <a href="javascript:void(0);" onclick="del(${autoInvestConfigVo.id});">删除</a>
	          </p>    
            </li>
            </c:forEach>
          </ul>
          </c:if>
          <!---------- ---------------------------------------------------------------------------->
          <div class="auto-tzbox mgtop">
            <div class="auto-tztitle "> 自动投标记录 </div>
          </div>
          <div class="tz-btablebox">
                   
          </div>
          <div class="hbtishi" style="padding-bottom:10px;padding-top:10px;width: 700px;">
            <p style="font-size:16px;   background:url(${basePath}/images/icon-tishi.png) no-repeat 0px 10px; text-indent:25px; color:#009dd9; line-height:35px;"> 温馨提示：</p>
            <p>1、用户只能添加1条自动投标规则，当启用规则时，将自动从最后一名开始排队，系统根据排队号来自动投标。</p>
            <p>2、当您启用规则在90天内未发生任何自动投标行为，系统会自动将您已启用的自动投标规则重新排队。</p>
            <p>3、当借款标满标的最后一笔投标是自动投标，且该投标金额小于该用户设定的自动投标金额时，则该用户的自动投标设置不参与重新排队。</p>
            <p>4、修改自动投标条件，不会改变自动投标的排队号。</p>
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
							$.layer({
							    type : 1,
							    title : '修改自动投标',
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