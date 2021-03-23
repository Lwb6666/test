<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/page/common/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ include file="/WEB-INF/page/common/public.jsp"%>
<table >
                                  <tr>
                                    <td>  开始时间  </td>
                                    <td> 借款用途 </td>
                                    <td> 借款标类型 </td>
                                    <td> 借款金额</td>
                                    <td>年化利率</td>
                                    <td> 进度 </td>
                                    <td> 状态</td>
                                  </tr>
                            <c:forEach items="${page.result}" var="borrow" varStatus="sta" step="1">
                                  
	                                  <tr class="${sta.index%2==0?'trcolor':'firstthird'}" >
	                                    <td><fmt:formatDate value="${borrow.addtime }" pattern="yyyy.MM.dd"/></td>
	                                    <td><a href="#">${fn:substring(borrow.name,0,10)}<c:if test="${fn:length(borrow.name)>10}">..</c:if></a></td>
	                                    <td>${portal:desc(300, borrow.borrowtype)}</td>
	                                    <td>￥${borrow.accountStr}</td>
	                                    <td><fmt:formatNumber value="${borrow.apr }" pattern="#,##0.##"/>%</td>
	                                    <td class="numcolor">${borrow.scheduleStr}%</td>
	                                    <td class="numcolor">${portal:desc(100, borrow.status)}
	                                    &nbsp;&nbsp;<a href="javascript:calcelBorrow(${borrow.id});">撤标</a>&nbsp;&nbsp;<a href="javascript:void(0);" onclick="advanceFullBorrow(${borrow.id},this);">提前满标</a>
	                                    </td>
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