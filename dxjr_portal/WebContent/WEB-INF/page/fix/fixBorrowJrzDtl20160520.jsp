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

 <div class="lb-waikuang whitebg dqb-top-div">
      <div class="searchbox fl">
        <div class="dqb-lb_title f26 p-r">
        
        定期宝 <span class="f14">${fixTenderDetailVo.lockLimit}个月</span> <span class="f14">（${fixTenderDetailVo.contractNo}）</span> <a href="javascript:toFixBorrowDetail(${fixTenderDetailVo.fixBorrowId});" class="f14" style="color:#3199cc;">查看详情</a> <a class="dqb-back-btn" onclick="javascript:history.back();">返 回</a> 
       <a href="javascript:toShowFixXiyi('${fixTenderDetailVo.fixBorrowId}');" class="f14 dqb-service-a" style="color:#0000ff;">查看定期宝服务协议</a> </div>
      </div>
      <div class="fl w848 ow bd-line" style="height:155px;">
        <div class="fl" style="margin: 10px 0 10px 0;width:100%;">
          <div class="dqb-column-div">
            <ul>
              <li style="line-height:40px;">年化收益</li>
              <li style="font-size: 24px;line-height: 40px;"><fmt:formatNumber value="${fixTenderDetailVo.apr}" pattern="##.##"/>%</li>
            </ul>
            <ul>
              <li style="line-height:40px;">加入金额</li>
              <li style="line-height:40px;">
              <fmt:formatNumber value="${fixTenderDetailVo.account }"
						pattern="#,##0.00" />元
               </li>
            </ul>
          </div>
          <div class="dqb-line"></div>
          <div class="dqb-column-div">
            <ul>
              <li style="line-height:40px;">已赚利息</li>
              <li><span style="font-size: 24px;line-height: 30px;color:red;">0.00</span>元</li>
            </ul>
            <ul>
              <li style="line-height:40px;">预计收益</li>
              <li style="line-height:40px;">${fixTenderDetailVo.yqsyStr}元
            </ul>
          </div>
         
          <div class="dqb-status dqb-status-gray" style="background:#b3d775; color:#fff;">加入中</div>
        </div>
      </div>
    </div>
			<!--我的账户右侧 -活期生息 s -->
    <!--我的账户右侧开始 -->


			<!--我的账户右侧 -活期生息 e -->

<div class="lb-waikuang" >
      <div class="men_title" style="background:#FFF; border:1px #dbdbdb solid;">
        <div class="tbtitle title-xg" style=" ">加入明细</div>
      </div>
      <div class=" dqb-myid whitebg nobordertop dqb-tbxx">
        <table class="dqb-table-title">
          <tr>
            <td style="width:50%;">加入金额（元）</td>
            <td style="width:50%">加入时间</td>
          </tr>
        </table>
        <table>
          <tbody>
            	<c:forEach items="${page.result}" var="fixBorrowJoinDetail" varStatus="sta">
						<tr <c:if test="${sta.index%2==0}">class="dqb-tr1color"</c:if>
							<c:if test="${sta.index%2!=0}">class="dqb-tr2color"</c:if>
							 >
								 <td style="width:50%;">
								 <fmt:formatNumber value="${fixBorrowJoinDetail.account }" pattern="#,##0.00" />
								   	<c:if test="${fixBorrowJoinDetail.tenderType==1}">
								          <img src="${basePath}/images/account/zdicon.png?version=<%=version%>"/>
					                    </c:if>	
								 </td>
								<td   style="width:50%;">   ${fixBorrowJoinDetail.addTimeStr }  </td>
							</tr>
						</c:forEach>
          </tbody>
        </table>
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

function findPage(pageNo) {
	window.location.href ='${path}/dingqibao/detailJrz/' +'${fixTenderDetailVo.fixBorrowId }'+'/'+ pageNo+".html";
	
}
/**
 *  show 协议
 */
function toShowFixXiyi(id) {
	$.layer({
		type : 2,
		fix : false,
		shade : [ 0.6, '#E3E3E3', true ],
		shadeClose : true,
		border : [ 10, 0.7, '#272822', true ],
		title : [ '', false ],
		offset : [ '50px', '' ],
		area : [ '1075px', '450px' ],
		iframe : {
			src : '${basePath }/dingqibao/toShowFixXiyi/' + id
					+ '.html'
		},
		close : function(index) {
			layer.close(index);
		}
	});
}
/**
 * 定期宝详细
 */
function toFixBorrowDetail(fixBorrowId){
	window.open("${basePath}/dingqibao/"+fixBorrowId+".html"); 
};

</script>


</html>
