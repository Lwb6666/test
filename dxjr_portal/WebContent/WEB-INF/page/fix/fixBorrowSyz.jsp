<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/page/common/taglib.jsp"%>
<script type="text/javascript">
   $(function(){
		$('.redbao-right').poshytip({
			className: 'tip-yellow',
			showTimeout: 1,
			alignTo: 'target',
			alignX: 'right',
			offsetX: 6,
			offsetY: -25,
			allowTipHover: false,
		});
   });

</script>
<table width="100%" border="0" cellspacing="0" cellpadding="0"  class="table center tbtr05">
                <thead>
                  <tr class="tbl-title">
                    <td align="left" class="tdtitl07">名称</td>
                    <td align="right">预期利率</td>
                    <td align="right">加入金额（元）</td>
                    <td align="right">预期收益(元)</td>
                    <td>投标次数</td>
                    <td>到期日期</td>
					<td>操作</td>
                  </tr>
                  </thead>
                  <tbody>
                  <c:forEach items="${page.result}" var="fixBorrowJoinDetail" varStatus="sta">
	                  <tr>
	                    <td align="left" class="tdtitl07">
	                    	<a href="${path }/dingqibao/detailSyz/${fixBorrowJoinDetail.fixBorrowId }.html" class="blue">定期宝<span style="padding: 0 0 0 15px;">${fixBorrowJoinDetail.lockLimit}个月</span>【${fixBorrowJoinDetail.contractNo}】
	                    	<c:if test="${fixBorrowJoinDetail.redMoney>0}">
							<span class="redbao-right" title='使用<fmt:formatNumber value="${fixBorrowJoinDetail.redMoney }" pattern="#,##0.##" />元红包'><img src="${path }/images/wrapred.png" alt=""/></span>
							</c:if>
						</a>
	                    	 <c:if test="${fixBorrowJoinDetail.areaType==1}">
								<img src="${basePath}/images/new-icon.png" />
							</c:if>
	                    </td>
	                    <td align="right">
	                    <fmt:formatNumber value="${fixBorrowJoinDetail.apr}" pattern="##.##" />%
	                    <c:if test="${fixBorrowJoinDetail.floatApr > 0}">
	                    <span style="position:absolute; left:100px; top:10px;background:#f05e13; color:#fff;height:15px; line-height:15px;padding:0 4px;border-radius: 2px;z-index: 9999;">
	                    +<fmt:formatNumber value="${fixBorrowJoinDetail.floatApr}" pattern="##.##" />%
	                    </span>
	                    </c:if> 
	                    </td>
	                    <td align="right" <c:if test="${fixBorrowJoinDetail.floatApr > 0}">title="加息金额：<fmt:formatNumber value="${fixBorrowJoinDetail.floatCapital}" pattern="#,##0" /> "</c:if>>
	                        <c:if test="${fixBorrowJoinDetail.tenderType==1}">
								<img src="${basePath}/images/account/zdicon.png" />
							</c:if>  
	                    	<fmt:formatNumber value="${fixBorrowJoinDetail.account }" pattern="#,##0" /> 
						</td>
	                    <td align="right" <c:if test="${fixBorrowJoinDetail.floatApr > 0}">title="加息利息：<fmt:formatNumber value="${fixBorrowJoinDetail.floatInterest}" pattern="#,##0.00" /> "</c:if>>${fixBorrowJoinDetail.yqsyStr } </td>
	                    <td class="blue">
	                    	${fixBorrowJoinDetail.cnt }
	                    </td>
	                    <td><fmt:formatDate value="${fixBorrowJoinDetail.lockEndTime }" pattern="yyyy-MM-dd"/></td>
						<td><c:if test="${fixBorrowJoinDetail.lockLimit>1 && fixBorrowJoinDetail.status==0}"><a href="javascript:void(0);" onclick="checkExit(${fixBorrowJoinDetail.id}, this)" >申请退出</a></c:if>
							<c:if test="${fixBorrowJoinDetail.status==2}"><span class="blue">退出中</span></c:if>
						</td>
	                  </tr>
                  </c:forEach>
                  </tbody>
                 
                </table>

<c:if test="${page.result==null  || page.totalCount==0 }">
	<div align="center"  style="height: 70px;line-height: 70px">暂无相关信息</div>
</c:if>   
          
		<!-- fenye s -->  
<c:if test="${page.result !=null && page.totalCount>0 }">          
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
</c:if>


			<script type="text/javascript">
		/**
		 * ajax 翻页功能
		 */
		function findPage(pageNo) {
			var product = $('#product').val();
			var state = $('#state').val();
			
			SearchChangeByOption(state,product,pageNo)
		}


		 $(document).ready(function(){ 
	            var color="#f0f7ff"
	            $(".tbtr tr:even,.tbtr01 tr:even,.tbtr02 tr:even,.tbtr03 tr:even,.tbtr04 tr:even,.tbtr05 tr:even,.tbtr06 tr:even,.tbtr07 tr:even,.tbtr08 tr:even,.tbtr09 tr:even,.tbtr10 tr:even,.tbtr11 tr:even,.tbtr12 tr:even,.tbtr13 tr:even,.tbtr14 tr:even,.tbtr15 tr:even,.tbtr16 tr:even,.tbtr17 tr:even").attr("bgcolor",color);//改变奇数行背景色
	      })

	</script>