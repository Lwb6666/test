<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/page/common/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ include file="/WEB-INF/page/common/public.jsp"%>
<title>我的账户_投标管理_债权转让_顶玺金融</title>

</head>

<body>
<!--头部开始-->
<%@ include file="/WEB-INF/page/common/header.jsp"%>
<!--头部结束-->
<!--我的账户左侧开始-->
<div id="Container">
<div id="Bmain">
  <div class="title">
    <span class="home"><a href="${path}/">顶玺金融</a></span><span><a href="${path }/myaccount/toIndex.html">我的账户</a></span><span><a href="#">债权转让</a></span><span><a href="javascript:;">债权转出</a></span>
  </div>
		<div id="menu_centert">
				<div id="menu_left">
					<%@ include file="/WEB-INF/page/account/myAccount_leftMenu.jsp"%>
				</div>  
           <!--我的账户左侧 结束 -->
     
<!--我的账户右侧开始 -->         
 <div  class="lb_waikuang">	 
	 <div class="men_title col5">
            <ul>
              <li  id="li1" class="men_li"><a  href="javascript:transfer(1,1);changeStyle(1)">可转让债权</a></li>
              <li  id="li2" ><a href="javascript:transfer(2,1);changeStyle(2)">转让中债权</a></li>
              <li  id="li3"><a href="javascript:transfer(3,1);changeStyle(3)">已转出债权</a></li>
              <li  id="li5"><a href="javascript:transfer(5,1);changeStyle(5)">转入中债权</a></li>
              <li  id="li4"><a href="javascript:transfer(4,1);changeStyle(4)">已转入债权</a></li>
            </ul>
	  </div>
	  
	  <div class="searchbox fl  whitebg">
	          <ul class="lb_title nobordertop">
	                    <li>借款标题：  <input class="inputText02"  id="title"    ></li>
		                    <li>标的类型：
		                              <select class="inputText01"  id="subjectType"   >
		                                  <option value="">全部</option>
			                              <option value="2">资产抵押标</option>
			                              <option value="5">机构担保标</option>
			                              <option value="1">信用认证标</option>
			                              <option value="3">净值标</option>
		                             </select>
		                    </li>
	                    <li class="lb_culi"><span class="lb_title_btn"><a   id="searchId" onclick="transfer(1,1)"   style="cursor: pointer;" >查询</a></span></li>                                          
	          </ul>
	  </div>
	    
	    
	  <div class="myid whitebg nobordertop"  id="containContentId"    >
	   
	  
	  </div>
	
</div>
</div>
</div>
<div class="clearfix"></div>
	<div>
		<%@ include file="/WEB-INF/page/common/footer.jsp"%>
	</div>
</body>
 

<script type="text/javascript">

 $(document).ready(function (){
	 transfer(1,1);
 });
 
 /* 查询债权转让的类型  */
 function   transfer(type,pageNum){
	 $("#containContentId").html("");
	 $.ajax({
			url : '${basePath}/zhaiquan/mytransferlist/'+type+'/'+pageNum+'.html',
			data:{type:type,title:$("#title").val(),subjectType:$("#subjectType").val()},
			type : 'post',
			dataType :"html",
			success : function(result) {
				if (result.length>0) {
				   $("#containContentId").html(result);
				   $("#searchId").attr("onclick","transfer("+type+","+pageNum+")");
				}else{
					layer.msg('加载失败', 1, 5);
				} 
			},
			error : function(result) {
				layer.msg('网络连接超时,请您稍后重试.', 1, 5);
		    }
		});
 }

 function  changeStyle(type){
	   //修改样式
	   $(".men_title ul li").each(function (){$(this).removeAttr("class")});
	   $("#li"+type).attr("class","men_li");
 }
 
 
  

</script>
</html>
 
	 