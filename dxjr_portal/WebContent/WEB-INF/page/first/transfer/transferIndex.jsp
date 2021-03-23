<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/page/common/taglib.jsp"%>
 
<!-- 	 <div class="men_title" > -->
<!--             <ul id="transferIndexUl"> -->
<%--               <li  id="li1" class="men_li"><a id="linkToTransfer" href="javascript:void(0);" onclick="getToTransfer(this,'${tenderRealId }')">转让信息</a></li> --%>
<%--               <li  id="li2" ><a href="javascript:void(0);" onclick="transferBorrowDetail(this,'${tenderRealId }',1)">债权价格详情</a></li> --%>
<!--             </ul> -->
<!-- 	  </div> -->
<!-- 	  <div class="clear"></div> -->
      <div id="transferContent"></div>
<!--       <input type="hidden" name="linkToTransfer" id="linkToTransfer" value="linkToTransfer"/> -->
 

<script type="text/javascript">
 $(document).ready(function (){ 
	 //加载可转让页面
//      getToTransfer(document.getElementById("linkToTransfer"),'${tenderRealId }');
	 getToTransfer('${tenderRealId }');
 });
 
 function getToTransfer(tenderRealId){
		//切换选项卡的时候样式处理
// 		processTransferStyle(obj);
		$.ajax({
			url : '${basePath}/zhitongche/zhuanrang/toTransfer/'+tenderRealId+'.html',
			data :{} ,
			type : 'post',
			dataType : 'text',
			success : function(data){
				$("#transferContent").html(data);
			},
			error : function(data) {
				layer.msg("网络连接异常，请刷新页面或稍后重试！", 1, 5);  // 弹出框
		    }
		});	 
}
 
 /* 查询债权转让的类型  */
//  function getToTransfer(obj,tenderRealId){
// 		//切换选项卡的时候样式处理
// 		processTransferStyle(obj);
// 		$.ajax({
// 			url : '${basePath}/zhitongche/zhuanrang/toTransfer/'+tenderRealId+'.html',
// 			data :{} ,
// 			type : 'post',
// 			dataType : 'text',
// 			success : function(data){
// 				$("#transferContent").html(data);
// 			},
// 			error : function(data) {
// 				layer.msg("网络连接异常，请刷新页面或稍后重试！", 1, 5);  // 弹出框
// 		    }
// 		});	 
//  }
 /**
 * 切换到债权价格详情
 */
 function transferBorrowDetail(obj,tenderRealId,pageNum){
		//切换选项卡的时候样式处理
		processTransferStyle(obj);
		queryTransferBorrowDetailList(tenderRealId,pageNum);

 }
 /**
 *  查询债权价格详情
 */
 function queryTransferBorrowDetailList(tenderRealId,pageNum){
		$.ajax({
			url : '${basePath}/zhitongche/transferborrow/queryCanList/'+tenderRealId+'/'+pageNum+'.html',
			data :{} ,
			type : 'post',
			dataType : 'text',
			success : function(data){
				$("#transferContent").html(data);
			},
			error : function(data) {
				layer.msg("网络连接异常，请刷新页面或稍后重试！", 1, 5);  // 弹出框
		    }
		});	 
 }
 
 /**
  * 切换选项卡的时候样式处理
  */
 function processTransferStyle(obj){
 	$("#transferIndexUl").find("li").removeClass("men_li");
 	$(obj).parent().addClass("men_li");
 }

</script>
</html>
 
	 