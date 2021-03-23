<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/page/common/taglib.jsp"%>
<!doctype html>
<html>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="keywords" content="顶玺金融,金融,投资,理财" />
<meta name="description" content="顶玺金融提供安全、有担保的互联网理财投资服务。风险保证金保障！随时可赎回！上顶玺，好收益！">
<meta name="author" content="顶玺金融" />
<meta name="copyright" content="2014 上海顶玺金融信息服务有限公司" />
<title>我的账户_投标管理_债权转让_顶玺金融</title>
<head>
<%@ include file="/WEB-INF/page/common/public4.jsp"%>

</head>

<body>
<%@ include file="/WEB-INF/page/common/header.jsp"%>

<!--main-->
<div id="myaccount">
	<%@ include file="/WEB-INF/page/common/topmenu.jsp"%>
	<%@ include file="/WEB-INF/page/common/wraper.jsp"%>
<!--直通车star-->
	<div class="wraper mt20">
     <div class="prduct-menu  background clearfix">
        	<div class="menu-tbl">
            	<ul class="col2"><li class="active" onclick="FirstTransfer();">债权转让</li><li onclick="canFirstTransfer();">直通车转让</li></ul>
            </div>
            <div class="menucont" style="clear:both">
            	<div class="tbl-cont tal-none">
		<div class="product-deatil clearfix ">
            <div class="tz-dqb2 clearfix">
                <div class="col clearfix">
                        <span class="fl gary2">标的状态：</span>
                        <div class="btn-box-bg">
                            <div class="term"><a id="linkFirstTransfer" onclick="transfer(1,1,this);" href="javascript:void(0)">可转让债权</a></div>
                            <div class="term"><a onclick="transfer(2,1,this);" href="javascript:void(0)" >转让中债权</a></div>
                            <div class="term"><a onclick="transfer(3,1,this);" href="javascript:void(0)" >已转出债权</a></div>
                            <div class="term"><a onclick="transfer(5,1,this);" href="javascript:void(0)">转入中债权</a></div>
                            <div class="term"><a onclick="transfer(4,1,this);" href="javascript:void(0)">已转入债权</a></div>
                        </div>
                </div>
                 <div class="col clearfix">
                                 <p class="red clearfix" style=" margin-left:100px;">注：存管标暂不支持债权转让</p>
                        </div>
                 </div>
             <div id="containContentId"></div>
        </div>
                </div>
                <div class="tbl-cont tal-none" style="display: none">
				<div class="product-deatil clearfix ">
            <div class="tz-dqb2 clearfix">
                <div class="col clearfix">
                    <span class="fl gary2">标的状态：</span>
                    <div class="btn-box-bg">
                        <div class="term"><a id="linkCanFirstTransfer" onclick="getTransferContent(this,1)" href="javascript:void(0)">可转让直通车</a></div>
                        <div class="term"><a id="linkCanFirstTransfer4" onclick="getTransferContent(this,4)" href="javascript:void(0)" >已转入直通车</a></div>
                        <div class="term"><a id="linkCanFirstTransfer3" onclick="getTransferContent(this,3)" href="javascript:void(0)" >已转出直通车</a></div>
                        <div class="term"><a id="linkCanFirstTransfer2" onclick="getTransferContent(this,2)" href="javascript:void(0)" >转让中直通车</a></div>
                        <input type="hidden" value="" name="type" id="type" />
                    </div>
                </div>
                <div class="col clearfix">
	                 <span class="fl gary2">投资时间：</span>
	                 <div class="btn-box-bg">
	                     <div class="term"><a onclick="getTransferContentByDate('all',this)" href="javascript:void(0)" class="active">全部</a></div>
	                     <div class="term">
	                     	<input type="text" name="beginTime" id="beginTime" class="Wdate" onClick="WdatePicker()"> 至 
	                     	<input type="text" name="endTime" id="endTime" class="Wdate" onClick="WdatePicker()">
	                     </div>
	                     <div class="term"><button type="button" class="btn-middle btn-blue mt5" onclick="getTransferContentByDate('search',this)">查询</button></div>
	                     
	                     <div class="term"><a onclick="getTransferContentByDate('month',this)" href="javascript:void(0)" >近1月</a></div>
	                     <div class="term"><a onclick="getTransferContentByDate('threemonth',this)" href="javascript:void(0)">近3月</a></div>
	                     <div class="term"><a onclick="getTransferContentByDate('sixmonth',this)" href="javascript:void(0)">6月前</a></div>
	                     
	                     <input type="hidden" name="timeScope" id="timeScope"/>
	                     <p class="fr gary2 f14"><span class="orange2">*</span>直通车发起转让过程中不参与投标</p>
	                 </div>
				</div>
            
          
             </div>
        		<div id="containerRight"></div>
       		 </div>
                 </div>
                </div>
      </div>
     </div>
</div>
<div id="myModal" class="reveal-modal"></div>
<%@ include file="/WEB-INF/page/common/footer.jsp"%>
	<script>
	 function getTransferContentByDate(timeScope,_this){
		 if(timeScope=="search"){
			 $(_this).parent().siblings().children('a').removeClass("active");
			 $('#timeScope').val('');
		 } else {
			 $(_this).attr("class","active").parent().siblings().children('a').removeClass("active");
			 $('#timeScope').val(timeScope);
			 $('#beginTime').val('');
			 $('#endTime').val('');
		 }
		 
		 var type = $('#type').val();
		 if(type=='' || type == null || type == '1'){
			 getTransferContent($('#linkCanFirstTransfer'),1);
		 } else if(type=='2'){
			 getTransferContent($('#linkCanFirstTransfer2'),2);
		 } else if(type=='3'){
			 getTransferContent($('#linkCanFirstTransfer3'),3);
		 } else if(type=='4'){
			 getTransferContent($('#linkCanFirstTransfer4'),4);
		 }
	 }	
	 
	 /*第一次加载直通车转让*/
	 function canFirstTransfer(){
		 getTransferContent(document.getElementById("linkCanFirstTransfer"),1)
	 }

	 /* 查询债权转让的类型  */
	 function getTransferContent(_this,type){
		$(_this).attr("class","active").parent().siblings().children('a').removeClass("active");
	 	$("#containContentId").html("");
	 	$("#containerRight").html("");
		
	 	$('#type').val(type);
		 
	 	var beginTime = $('#beginTime').val();  //开始时间
	 	var endTime = $('#endTime').val();  //结束时间
		$.ajax({
			url : '${basePath}/zhitongche/zhuanrang/queryTypePage.html',
			data :{type:type} ,
			type : 'post',
			dataType : 'text',
			success : function(data){
				$("#containerRight").html(data);
			},
			error : function(data) {
				layer.msg("网络连接异常，请刷新页面或稍后重试！", 1, 5);  // 弹出框
		    }
		});	 
	 }
	
	//关闭层
	function closeWin(){
		$('#myModal').trigger('reveal:close');
	}
	
	$(function(){
		$('#tzgl').attr("class","active");
		$('#zqzr').attr("class","active");
		
		transfer(1,1,document.getElementById("linkFirstTransfer"));
	})
	/*第一次加载债权转让*/
	function FirstTransfer(){
		transfer(1,1,document.getElementById("linkFirstTransfer"));
	}
	 
	 /* 查询债权转让的类型  */
	 function transfer(type,pageNum,_this){
		 $(_this).attr("class","active").parent().siblings().children('a').removeClass("active");
		 
		 $("#containContentId").html("");
		 $("#containerRight").html("");
		 $.ajax({
				url : '${basePath}/zhaiquan/mytransferlist/'+type+'/'+pageNum+'.html',
				data:{type:type,title:$("#title").val(),subjectType:$("#subjectType").val()},
				type : 'post',
				dataType :"html",
				success : function(result) {
					if (result.length>0) {
					   $("#containContentId").html(result);
// 					   $("#searchId").attr("onclick","transfer("+type+","+pageNum+")");
					}else{
						layer.msg('加载失败', 1, 5);
					} 
				},
				error : function(result) {
					layer.msg('网络连接超时,请您稍后重试.', 1, 5);
			    }
			});
	 }
	 
	
	</script>
</body>
</html>
