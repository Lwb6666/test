<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/page/common/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ include file="/WEB-INF/page/common/public.jsp"%>
<title>实物领取详情</title>
<style type="text/css">
* {
	margin: 0;
	padding: 0;
}

body {
	font-size: 16px;
	font-family: "微软雅黑"
}

.c_box {
	width: 450px;
	height: 300px;
	margin: 0 auto;
	text-align: left;
	margin: 30px;
}

.c_box  .c_tile{
border: 0px solid green;
margin: 20px 0;

}

.c_box .c_content{
border: 0px solid blue;
margin: 20px 0;
padding:10px 0px 10px 0px ;

}


.c_content  span{
  margin: 0px 0px 0px 20px;
  font-size: 15px;
  font-family: "微软雅黑";

}
</style>

</head>
<body style="overflow-y:hidden;background:#fff;">
	<!-- c_box s  -->   
	<div class="c_box">   
	
		<!-- title s  -->
		<div class="c_tile">
			 <h3> 顶玺金融 - 我的奖励 - 实物奖励领取详细信息 </h3> 
		</div>
	    <!-- title e  -->	
		
		<!-- c_content  s -->	  
		<div class="c_content">
				<p>
				<div ><span >联系人:&nbsp; ${lottVo.contact }</span></div>
				<p>
				<div><span >联系电话 : ${lottVo.mobile }</span></div>
				<p>
				<div><span >联系地址: ${lottVo.address }</span></div>
				<p>
				<div><span >邮编:&nbsp;&nbsp;  ${lottVo.postcode }</span></div>
				<p>
				<div><span >快递公司: ${lottVo.express_company }</span></div>
				<p>
				<div><span >快递单号: ${lottVo.express_code }</span></div>
				<p>
				
				<div><span >奖品名称: ${lottVo.lotteryGoodsName }</span></div>
				<p>
				
				<div><span >获奖时间:  <fmt:formatDate value="${lottVo.addTime }" pattern="yyyy-MM-dd HH:mm:ss"/> </span> </div>
				<p>
				
				<div><span >状态:&nbsp;&nbsp; ${lottVo.status_z}</span></div> 
				<p>
				
				<div><span >备注:&nbsp;&nbsp; ${lottVo.remark }</span></div> 
				<p>
				
				
		</div>
		<!-- c_content  e -->	
		
	</div>
	<!-- c_box e  -->

</body>



<script type="text/javascript">
	$(function() {

	});
</script>
</html>
