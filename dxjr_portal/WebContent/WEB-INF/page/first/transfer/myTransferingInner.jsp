<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/page/common/taglib.jsp"%>

<!-- <div class="lb_waikuang whitebg" style="margin:1px"> -->
<!-- 	<ul class="lb_title nobordertop"> -->
<!-- 		<li class="one">开通日期： -->
<!-- 		<input class="inputText02" name="beginTime" -->
<%-- 					id="beginTime" class="Wdate" value="${beginTime}" --%>
<!-- 					onClick="WdatePicker();"> ~  -->
<!-- 		<input class="inputText02" -->
<%-- 					name="endTime" id="endTime" class="Wdate" value="${endTime}" --%>
<!-- 					onClick="WdatePicker();"> -->
<!-- 		</li> -->

<!-- 		<li class="lb_culi"> -->
<!-- 		    <span class="lb_title_btn"> -->
<!-- 		       <a href="javascript:queryTransferingList(1);">查询</a> -->
<!-- 		    </span> -->
<!-- 		</li> -->
<!-- 		<li class="tipone"> -->
<!-- 			<span>温馨提示：</span> 直通车发起转让过程中不参与投标 -->
<!-- 		</li> -->
<!-- 	</ul> -->
	<div class="myid whitebg nobordertop" id="transferDiv">

	</div>

<!-- </div> -->

<script type="text/javascript">
  $(function(){
	  queryTransferingList(1); 
  });
	 /*
	  * 查询转让中的债权列表
	  */
	 function queryTransferingList(pageNum){
		 var beginTime =$("#beginTime").val();
		 var endTime   =$("#endTime").val();
		 var timeScope=$('#timeScope').val(); //时间快捷查询
		 $.ajax({
				url : '${basePath}/zhitongche/zhuanrang/queryTransferingList/'+pageNum+'.html',
				data:{beginTime:beginTime,endTime:endTime,timeScope:timeScope},
				type : 'post',
				dataType :"html",
				success : function(result) {
					$("#transferDiv").html(result);
				},
				error : function(result) {
					layer.msg('网络连接异常，请刷新页面或稍后重试！', 1, 5);
			    }
		 });
	 }	
</script>          
            
            
            
            