<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>    
<%@ include file="/WEB-INF/page/common/taglib.jsp"%> 
       
        <c:forEach  items="${transferlist}"  var="transfer" >
            <ul>
              <li class="col-1"><a   rel="nofollow" target="_blank" class="gblue" href="${path}/zhaiquan/${transfer.id}.html">${transfer.borrowName}</a></li>
              <li class="col-2">
	               <input type="button" class="but bluess" value="转让中" onclick="toTransfer(${transfer.id});">
              </li>
            </ul>
        </c:forEach>    
              
<script>
	function toTransfer(transferId){
		window.open("${path}/zhaiquan/"+transferId+".html"); 
	}
</script>