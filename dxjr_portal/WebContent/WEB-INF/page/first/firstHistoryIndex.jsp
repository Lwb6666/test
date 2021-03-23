<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/page/common/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ include file="/WEB-INF/page/common/public.jsp"%>
<title>投标直通车_历史列表_顶玺金融</title>
</head>

<body style="background:#f9f9f9;">
<!--头部开始-->
<%@ include file="/WEB-INF/page/common/header.jsp" %>
<!--头部结束-->

<!--内容开始-->
<div id="Container">
     <div id="Bmain">
         <div class="tblist_title">
            <ul>
                <li class="selected"><a href="">历史列表</a> </li>
            </ul>
         </div>
         <form method="post" id="firstBorrowForm">
         <div id="rz_main" style="height:auto; border-top:none; margin-top:0; ">
              <div class="rz_borrow1">
                <div class="rz_type1" style="border:none">
                	<!-- 直通车历史列表 -->
                    <div class="tbjl" id="divFirstHistoryTable">
                                                                                   
                    </div>
                </div>
             </div>                                            
         </div>
         </form>
    </div>
</div>
<!--内容结束-->
<%@ include file="/WEB-INF/page/common/footer.jsp" %>
</body>

<script type="text/javascript">
$(document).ready(function(){
	searchFirstHistorys();
});

/**
 * 查询优先计划投标列表
 */
function searchFirstHistorys(){
   // 优先计划列表翻页查询
	turnFirstPageParent(1);
}
/**
 * 优先计划列表翻页
 */
 function turnFirstPageParent(pageNo){
		$("#firstBorrowForm").ajaxSubmit({
		    url : '${basePath }/zhitongche/searchFirstHistorys/'+pageNo+'.html',
		    type: "POST",
		    success:function(data){
				$("#divFirstHistoryTable").html(data);
		    },
			error : function() {
				layer.msg("查询直通车历史列表超时，请您稍后重试", 1, 5);
		    } 
		 });
}
</script>
</html>

