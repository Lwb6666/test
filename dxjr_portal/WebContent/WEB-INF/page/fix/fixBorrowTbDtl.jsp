<%@page import="com.dxjr.utils.DateUtils"%>
<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/page/common/taglib.jsp"%>


<!doctype html>
<html>
<head>
<%@ include file="/WEB-INF/page/common/public.jsp"%>
<meta charset="utf-8">
<title>我的账户_投资管理_定期宝_定期宝</title>
 

</head>

<body>

	<!-- header start   -->
	<%@ include file="/WEB-INF/page/common/header.jsp"%>
	<!-- header end     -->



	<!--   活期生息 s -->

	<div id="Bmain">
		<!-- 导航 s  -->
		<div class="title">
			<span class="home"> <a href="${path}/">顶玺金融</a></span> <span>
				<a href="${path}/myaccount/toIndex.html">我的账户</a>
			</span> <span><a href="#">投资管理</a></span> <span><a
				href="${path}/dingqibao/myAccount.html">定期宝</a></span>
		</div>
		<!-- 导航 e  -->


		<!-- 导航下内容 s  -->
		<div id="menu_centert">

			<!--我的账户左侧 开始 -->
			<%@ include file="/WEB-INF/page/account/myAccount_leftMenu.jsp"%>
			<!--我的账户左侧 结束 -->

 <div class="lb_waikuang border whitebg" style="margin-bottom:0px;">
      <div class="safetoptit">投标信息<a class="return-a"  onclick="javascript:history.back();" >返回</a></div>
      <div class="safe">
        <ul class="table-titlebox" style=" border-bottom:1px #ccc solid;">
          <li style="width:25%; text-align:center;">借款标题</li>
          <li  style="width:25%; text-align:center;"> 投资本金（元）</li>
          <li style="width:25%; text-align:center;">投标时间</li>
          <li  style="width:25%; text-align:center;">状态</li>
        </ul>
        <div class="tb_ztype" style="padding-top:30px;">
          <table border="0" class="table-boxwidth">
          
          <c:forEach items="${page.result}" var="fixBorrowJoinDetail" varStatus="sta">
							<tr <c:if test="${sta.index%2==0}">class="dqb-tr1color"</c:if>
							<c:if test="${sta.index%2!=0}">class="dqb-tr2color"</c:if>
							 >
								  <td style="width:25%; text-align:center;">  
								  	<a href="${path}/toubiao/${fixBorrowJoinDetail.borrowId}.html">${fixBorrowJoinDetail.borrowName }</a>	 	
								  </td>
								<td style="width:25%; text-align:center;">  <fmt:formatNumber value="${fixBorrowJoinDetail.account }" pattern="#,##0.00" />  </td>
								
								 <td style="width:25%; text-align:center;">
								     <fmt:formatDate value="${fixBorrowJoinDetail.addTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
								  </td>
								<td  style="width:25%; text-align:center;">   ${fixBorrowJoinDetail.tenderStatusStr }  </td>
							</tr>
						</c:forEach>
          
          </table>
        </div>
      </div>
    </div>
 
    <c:if test="${page.result!=null }">
		<!-- fenye s -->
	<div class="yema">
		<div class="yema_cont">
			<div class="yema rt">
				<jsp:include page="/WEB-INF/page/common/ajaxpage.jsp">
					<jsp:param name="pageNo" value="${page.pageNo}" />
					<jsp:param name="totalPage" value="${page.totalPage}" />
					<jsp:param name="hasPre" value="${page.hasPre}" />
					<jsp:param name="prePage" value="${page.prePage}" />
					<jsp:param name="hasNext" value="${page.hasNext}" />
					<jsp:param name="nextPage" value="${page.nextPage}" />
				</jsp:include>

			</div>
		</div>
	</div>
	<!-- fenye e -->


</c:if>
    
    <!--我的账户左侧 结束 --> 
    
    <!--我的账户右侧开始 --> 
    
  </div>
</div>



	<!-- ps s -->
	<div class="clearfix"></div>
	<!-- ps e -->


	<!-- foot start -->
	<%@ include file="/WEB-INF/page/common/footer.jsp"%>
	<!-- foot end -->




</body>

<script type="text/javascript">
$(function() {
	//alert('init');
//	load_tb();
});



function findPage(pageNo) {
	
	alert('${path}/dingqibao/tbDetail/' +'${fixBorrowId }'+'/'+ pageNo+".html");
	window.location.href ='${path}/dingqibao/tbDetail/' +'${fixBorrowId }'+'/'+ pageNo+".html";
	
}

</script>


</html>
