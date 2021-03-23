<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/page/common/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

      <table border="0">
         <c:forEach items="${page.result}" var="new_notice">
            <tr>
		        <td class="lists_line"><a href="javascript:findDetail('${new_notice.type}','${new_notice.id}');">${fn:substring(new_notice.title,0,10)}<c:if test="${fn:length(new_notice.title)>10}">..</c:if></a></td>
		        <td class="lists_line">${new_notice.addtimeStr}</td>
		    </tr>
		 </c:forEach>
      </table>

<script type="text/javascript">

$(function(){
// 	layer.alert('${type}')
// 	if('${type}'==0){
// 		$("#notice0").addClass("selected");
// 		$("#notice1").removeClass("selected");
// 	}else if('${type}'==1){
// 		("#notice1").addClass("selected");
// 		$("#notice0").removeClass("selected");
// 	}
});

//查看详情
function findDetail(type,id){
	$.post("${basePath}/baodao/hits/"+id+".html", {
	}, function(data) {
		var typeUrl = "";
		if(type=='0'){
			typeUrl = "gonggao";
		}else if(type=='1'){
			typeUrl = "baodao";
		}
		if(data=='success'){
			window.open("${path}/"+typeUrl+"/"+id+".html","_self");
		}else{
			layer.alert("网络异常,请稍后重试...");
		}
	});
}
</script>
