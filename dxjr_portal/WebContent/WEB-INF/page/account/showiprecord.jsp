<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/page/common/taglib.jsp"%>
  <div>
	 <table  align="center" border="1"  style="border-spacing: 0px;">
		  <th>ip</th>
		  <th>地点</th>
		  <th>登陆时间</th>
		  <c:forEach    items="${page.result}"   var="result"    >
		  <tr align="center">
			  <td width="30%">${result.addip}</td>
			  <td    width="35%">${result.province==null?'无':result.province}-${result.city==null?'无':result.city}</td>
			  <td width="35%"> <fmt:formatDate value="${result.logintime}" pattern="yyyy-MM-dd HH:mm:ss"/>  </td>
		  </tr>
		  </c:forEach>
	 </table>
	 </div>