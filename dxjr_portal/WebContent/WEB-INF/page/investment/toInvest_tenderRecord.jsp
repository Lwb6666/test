<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/page/common/taglib.jsp"%>
<script type="text/javascript">
 $(function(){
		$('#tip-bottom').poshytip({
			className: 'tip-yellowsimple',
			showTimeout: 1,
			alignTo: 'target',
			alignX: 'center',
			alignY: 'bottom',
			offsetX: 15,
			offsetY: 10,
			allowTipHover: false,
		});
		$(".close").click(function(){
		$(".bankcard-tip").fadeOut();
		});
	});
 </script>
	<table width="100%" border="0" cellspacing="0" cellpadding="0" class="table center tbg">
             <tr class="tbl-title">
                <td >投标人</td>
                <td style="text-align: right;">投标金额</td>
                <td >投标时间	</td>
                <td >投资方式</td>
             </tr>
              <c:forEach items="${page.result}" var="tenderRecord" varStatus="status">
             <tr>
             <c:choose>
               <c:when test="${tenderRecord.platform==2}">
                  <td class="posi-r" style="overflow:visible;">
                  <a class="listtishi2" <c:if test="${loginMember.userId == tenderRecord.userId}">style="color:blue;"</c:if>
                   <c:if test="${loginMember.userId != tenderRecord.userId}">style="color:#333;"</c:if>>                                             
                       <img src="${basePath}/images/weixin_small.png" title="微信投标"/>                    
                       </a>
                     <a href="${path}/accountdetail/show.html?userIdMD5=${tenderRecord.userIdMD5}" target="_blank">${loginMember.userId == tenderRecord.userId?tenderRecord.username:tenderRecord.userNameSecret}</a>  
                  </td>
               </c:when>
               
               <c:when test="${tenderRecord.platform==3 || tenderRecord.platform==4}">
                  <td class="posi-r" style="overflow:visible;">
                  <a class="listtishi" style="top: 12px;left: 30px;" <c:if test="${loginMember.userId == tenderRecord.userId}">style="color:blue;"</c:if>
                   <c:if test="${loginMember.userId != tenderRecord.userId}">style="color:#333;"</c:if>>
                        <img src="${basePath}/images/v5/phone.png"/>
                        <i class="displaywm" style="z-index:9999;" ><img src="${basePath}/images/v5/erweima.png"/></i>
                         </a>
                     <a href="${path}/accountdetail/show.html?userIdMD5=${tenderRecord.userIdMD5}" target="_blank">${loginMember.userId == tenderRecord.userId?tenderRecord.username:tenderRecord.userNameSecret}</a>  
                  </td>
               </c:when>
               <c:otherwise>
                <td>
                  <a   
                   <c:if test="${loginMember.userId == tenderRecord.userId}">style="color:blue;"</c:if>
                   <c:if test="${loginMember.userId != tenderRecord.userId}">style="color:#333;"</c:if>
                 href="${path}/accountdetail/show.html?userIdMD5=${tenderRecord.userIdMD5}" target="_blank">${loginMember.userId == tenderRecord.userId?tenderRecord.username:tenderRecord.userNameSecret}
                 </a>
             </td>
               </c:otherwise>
             </c:choose>
               <td style="text-align: right;"><fmt:formatNumber value="${tenderRecord.account }" pattern="#,##0.##"/>元</td>
               <td ><fmt:formatDate value="${tenderRecord.addtimeDate }" pattern="yyyy-MM-dd HH:mm:ss"/></td>
              	<c:choose>
					   <c:when test="${tenderRecord.ishowfloat==1 && tenderRecord.isCustody!= 1}">
						    <td><span>自动投标（第${tenderRecord.autotenderOrder}位）</span><span class="auto-tip">+${tenderRecord.yearApr}
                                <c:if test="${tenderRecord.activityYear == 2016 and tenderRecord.activityMonth == 7}" ><a href="${basePath}/activity/zjcg.html"></c:if>
                             <c:if test="${tenderRecord.activityYear == 2016 and tenderRecord.activityMonth == 8}" ><a href="${basePath}/activity/bayue.html"></c:if>
                                <span class="red iconfont tip-bottom"  id="tip-bottom" title="详情请点击查看活动专题页">&#xe608;</span></a></span></td>
					   </c:when>
					   <c:otherwise>
		                    <td >
                     		<c:if test="${tenderRecord.tenderType==0}">手动投标
                     		</c:if>
                     		<c:if test="${tenderRecord.tenderType==1}">
                              			自动投标（第${tenderRecord.autotenderOrder}位）
                             </c:if>
                     		<c:if test="${tenderRecord.tenderType==2 && tenderRecord.autotenderOrder != null}">
                     			<font color="red">直通车投标（第${tenderRecord.autotenderOrder}位）</font>
                     		</c:if>
                     		<c:if test="${tenderRecord.tenderType==2 && tenderRecord.autotenderOrder == null}">
                     			<a  title="第${tenderRecord.firstPeriods}期投标直通车(${tenderRecord.firstBorrowScale})"><font color='red'>第${tenderRecord.firstPeriods}期投标直通车</font>..</a>
                              </c:if> 
                     		<c:if test="${tenderRecord.tenderType==3 }">
                     			<font color='red'>定期宝(${tenderRecord.firstBorrowName})</font>
                     		</c:if>
                            <c:if test="${tenderRecord.tenderType==4}">
                     			权证人员投标
                     		</c:if> 
		             </td>
					   </c:otherwise>
			      </c:choose>
             </tr>
             </c:forEach>  
 </table>
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
<style>
.tbg tr:nth-of-type(odd){background:#f0f7ff;}
</style>
<script type="text/javascript">
function findPage(pageNum){
	searchTenderRecordList(pageNum);
}
</script>