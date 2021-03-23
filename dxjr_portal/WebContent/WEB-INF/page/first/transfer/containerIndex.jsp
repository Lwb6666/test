<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/page/common/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ include file="/WEB-INF/page/common/public.jsp"%>
<title>我的账户_直通车转让_顶玺金融</title>

</head>

<body>
<!--头部开始-->
<%@ include file="/WEB-INF/page/common/header.jsp"%>
<!--头部结束-->
<!--我的账户左侧开始-->
<div id="Container">
<div id="Bmain">
  <div class="title">
    <span class="home"><a href="${path}/">顶玺金融</a></span><span><a href="${path }/myaccount/toIndex.html">我的账户</a></span><span>债权转让</span><span><a href="${path}/zhitongche/zhuanrang/tocontainer.html">直通车转出</a></span>
  </div>
		<div id="menu_centert">
				<div id="menu_left">
					<%@ include file="/WEB-INF/page/account/myAccount_leftMenu.jsp"%>
				</div>  
           <!--我的账户左侧 结束 -->
     
<!--我的账户右侧开始 -->         
 <div  class="lb_waikuang">	 
	 <div class="men_title col4">
            <ul id="firstTransferUl">
              <li  id="li1" class="men_li"><a id="linkCanFirstTransfer" href="javascript:void(0);" onclick="getTransferContent(this,1)">可转让直通车</a></li>
              <li  id="li2" ><a href="javascript:void(0);" onclick="getTransferContent(this,2)">转让中直通车</a></li>
              <li  id="li3"><a href="javascript:void(0);" onclick="getTransferContent(this,3)">已转出直通车</a></li>
              <li  id="li4"><a href="javascript:void(0);" onclick="getTransferContent(this,4)">已转入直通车</a></li>
            </ul>
	  </div>
	  
	  <div class="clear"></div>
      <div id="containerRight" ></div>
</div>
</div>
</div>
<div class="clearfix"></div>
</div>
<%@ include file="/WEB-INF/page/common/footer.jsp"%>
</body>
 

<script type="text/javascript">
 $(document).ready(function (){ 
	 //加载可转让页面
     getTransferContent(document.getElementById("linkCanFirstTransfer"),1);
 });
 
 /* 查询债权转让的类型  */
 function getTransferContent(obj,type){
		//切换选项卡的时候样式处理
		processTabStyle(obj);
		$.ajax({
			url : '${basePath}/zhitongche/zhuanrang/queryTypePage.html',
			data :{type:type
			} ,
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

 /**
  * 切换选项卡的时候样式处理
  */
 function processTabStyle(obj){
 	$("#firstTransferUl").find("li").removeClass("men_li");
 	$(obj).parent().addClass("men_li");
 }

</script>
</html>
 
	 