<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/page/common/taglib.jsp"%>


	<div class="searchbox fl whitebg">
		    <div class="s_lott_change" style="height:60px;margin:0px 0px 0px 0px;border-left: 1px solid #dbdbdb; border-right: 1px solid #dbdbdb; ">
		        
		        
		        <div class="xj_jh" style="float:right;line-height:60px;">
		        	<span style="width:100px;height:30px;font-size:14px;border:0px solid red;"> 
		    		剩余 &nbsp; <span style="font-size:20px;color:red;">${chanceTotalNum} <c:if test="${ling==0 }">   0  </c:if></span>  &nbsp;次抽奖机会 &nbsp;
		    		</span>
		    		&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; 
		    		<span><a  href="${path }/lottery_chance/info.html" >获得更多抽奖机会</a> &nbsp;</span>
		        
		        </div>
		    </div>
	</div>
	
<!--我的账户右侧  資金管理  提現明細--> 
<div class="myid whitebg">

			<!-- jljl_lott list s -->
            <table > 
           		 <!-- title s -->
		   		 <tr>    
		            <td ><strong>奖品</strong></td>
		            <td ><strong>获奖时间</strong></td>
		            <td ><strong>领取/使用时间</strong></td>
		            <td><strong>所属活动</strong></td>
		            <td ><strong>状态</strong></td>
		         </tr>
		         <!-- title e -->   
		         
		       <!-- content s -->   
		         <c:forEach items="${lottLst}" var="lott_jljl" varStatus="sta">
			         <tr <c:if test="${sta.index%2==0}">class="trcolor"</c:if>>
			             <td>${lott_jljl.lotteryGoodsName}</td>
			             <td><fmt:formatDate value="${lott_jljl.addTime  }" pattern="yyyy-MM-dd HH:mm:ss"/></td>
			             <td><fmt:formatDate value="${lott_jljl.drawTime  }" pattern="yyyy-MM-dd HH:mm:ss"/></td>
			             <td>${lott_jljl.name}</td>
			          	<td>${lott_jljl.status_z}</td>
			           
		         </c:forEach>
                <!-- content e -->    
           
            </table>
            <!-- jljl_lott list e -->
            
            
            
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
	
	
</div>


<script type="text/javascript">
/**
 * ajax 翻页功能
 */
function findPage(pageNo){
	window.parent.lott_jljl_pageParent(pageNo);
}

</script>