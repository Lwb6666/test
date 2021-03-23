<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>    
<%@ include file="/WEB-INF/page/common/taglib.jsp"%>    
  <div    class="leftbox">
   
        <div class="bg-h bg pdl20">  
          <a name="tab"  id="2"   class="fl mr10"   href="javascript:;"  onmouseenter="tabData(2)"   ><h2>投标</h2></a> 
          <a name="tab"  id="3"   class="fl"   href="javascript:;"   onmouseenter="tabData(3)"  ><h2>债转</h2></a>
          <a class="bg-more"  id="moreId"     rel="nofollow"  href="${path}/zhitongche.html">更多&gt;&gt;</a>
        </div>
        
        <div  id="tabData" >
	            
        </div>      
        </div>
        
        
 <script type="text/javascript">
 
 
 $(function (){
	 tabData(2);
 });
 
 
 function  tabData (type){
	 $.ajax({
			url : '${basePath}/searchTab/'+type+'.html',
			dataType :"html",
			success : function(result) {
				if (result.length>0) {
				   $("#tabData").html(result);
				}else{
					layer.msg('加载失败', 1, 5);
				} 
			},
			error : function(result) {
				layer.msg('网络连接超时,请您稍后重试.', 1, 5);
		    }
		});
	 $("a[name='tab']").each(function (){
		 if($(this).attr("id")==type){
			 $(this).addClass("tab");
		 }else{
			 $(this).removeClass("tab");
		 } 
	 });
	 var  href ="${path}/";
	 if (type==1) {
		 href+="zhitongche.html";
	 }if (type==2) {
		 href+="toubiao.html";
	 }if (type==3) {
		 href+="zhaiquan.html";
	 }
	$("#moreId").attr("href",href);
	 
 }
 
 function toInvestFirst(id){
		window.open("${path}/zhitongche/"+id+".html","_blank");
	}

	function goToInvest(borrowId){
		window.open("${path}/toubiao/"+borrowId+".html"); 
	};
</script>     
        
        