<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/page/common/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ include file="/WEB-INF/page/common/public4.jsp"%>
<title>
	<c:if test="${member.isFinancialUser == 1 }">
		${member.userNameSecret }_用户详情
	</c:if> 
	<c:if test="${member.isFinancialUser == 0 }">
		${member.username }_用户详情
	</c:if>
</title>


</head>

<body>
	<!--头部开始-->
	<%@ include file="/WEB-INF/page/common/header.jsp"%>
	<!--头部结束-->
	<!--内容开始-->
    <div id="myaccount">
	<%-- <%@ include file="/WEB-INF/page/common/topmenu.jsp"%> --%>
	<div id="account-all">
	            <div class="userbar">
	            	<ul class="ul-userbar1">
						<li class="userpic">
						  <a id="tip-userpic" title="头像"  href="javascript:toAddHeadImg();">
							<c:if test="${member.headimg==null }">
								<img src="${basePath}/images/main/head.png" />
							</c:if>
							<c:if test="${member.headimg!=null }">
								<img src="${basePath}${member.headimg }" />
							</c:if>
						  </a>
						</li>
					</ul>
				   <ul class="ul-userbar2">
	                 <li class="userinfo mt10">
	                         <p id="td1"><input type="text" id="username"><a href="javascript:void(0);" onclick="finish();"><i class="iconfont blue">&#xe609;</i><span>完成</span></a><span class="orange ml20">仅有一次修改机会，请谨慎使用</span></p>
	                         <input type="hidden" value="${member.userNameEncrypt}" id="oldname"/>
	                          <p id="td2">
	                            <span class="gray3 f18">${member.userNameSecret}</span>
	                            <c:if test="${flag!=1 && isView==1}">
	                               <a href="javascript:void(0);" onclick="modify();"><i class="iconfont blue">&#xe609;</i><span>修改</span></a>
	                             </c:if>
	                          </p>
	                        <p class="f16 mt20"><span class="orange">会员等级
	                            <c:if test="${userlevel == 0 }">
								    <i class="iconfont f24 uservip1"></i>
								</c:if>
								 <c:if test="${userlevel == 10 }">
								    <i class="iconfont f24 uservip2"></i>
								</c:if>
								 <c:if test="${userlevel == 20 }">
								    <i class="iconfont f24 uservip3"></i>
								</c:if>
								 <c:if test="${userlevel == 30 }">
								    <i class="iconfont f24 uservip4"></i>
								</c:if>
								 <c:if test="${userlevel == 40 }">
								    <i class="iconfont f24 uservip5"></i>
								</c:if>
								 <c:if test="${userlevel == 50 }">
								    <i class="iconfont f24 uservip6"></i>
								</c:if>
								 <c:if test="${userlevel == 60 }">
								    <i class="iconfont f24 uservip7"></i>
								</c:if>
	                          </span><span style=" margin-left:100px;">
	                                                                上次登录时间:</span><span class="orange mal10">${fn:substring(member.lastlogintimeFmt, 0, 10)}</span><span class="mal20">注册时间:</span><span class="orange mal10">${fn:substring(member.addtimeFmt, 0, 10)}</span></p>
	          		  </li>
	               </ul>
	            </div>
	 </div>
<div class="product-wrap"><!--table-->
	<div class="grid-1100 background">
    	<div class="prduct-menu">
    	    	<div class="menu-tbl">
        	       <ul class="col4">
        	          <li id="invest_li" onclick="toggleList('invest',1);">投标列表</li>
        	          <li id="fix_li" onclick="toggleList('fix',1);">投宝列表</li>
        	          <li id="borrow_li" onclick="toggleList('borrow',1);">借款列表</li>
        	          <li id="bbs_items_li" onclick="toggleList('bbs_items',1);">发帖列表</li>
                  </ul>
               </div>
			  <div id="down"></div>
            </div>
        </div>
    </div>
</div>

	<!--内容结束-->


	<div>
		<div>
			<%@ include file="/WEB-INF/page/common/footer.jsp"%>
		</div>
</body>
<script type="text/javascript">
 
$(function(){
	$('#td1').hide();
	$('#td2').show();
	$("#zhzl").attr("class","active"); //添加样式 
});
function modify(){
	$('#td1').show();
	$('#td2').hide();
}
function finish(){
	var username=$('#username').val();
	var oldname=$('#oldname').val();
	if(!checkMemberNameExist()){
		return;
	}
	var _load = layer.load('处理中..');
	$.ajax({
        type: "POST",
        url: "${basePath}/accountdetail/modifyUsername.html",
        async: false,
        data: {username:username,oldname:oldname},
        success: function(data) {
          layer.close(_load);
      	  if(data != null && data.code == 1){
      		   layer.msg(data.message,2,1,function(){
      			 $.ajax({
 	     			url : '${basePath }/logout.html',
 	     			type : 'post',
 	     			dataType : 'text',
 	     			success : function(data) {
 	     				window.open("${path }/member/toLoginPage.html","_self");
 	     			},
 	     			error : function(data) {
 	     				layer.msg('网络连接异常，请刷新页面或稍后重试！', 1, 5);
 	     			}
 	     		});
			 });
			}else{
				layer.alert(data.message);
			}
        }
   });
}

$('#username').focusout(function() {
	var username=$('#username').val();
	if(username==null ||username==""){
		$('#td1').hide();
		$('#td2').show();
	}
});
/**
 * 校验注册的用户名
 */
function checkMemberNameExist(){
	var username = $.trim($('#username').val());
	var reg = /^[\u4E00-\u9FA5a-zA-Z0-9-_]{2,20}$/; //用户名
	var fullNumber = /^[0-9]+$/; //数字
	var flag=false;
	if(reg.test(username) && !fullNumber.test(username) && username.length>=2 && username.length<=20){
		
		//验证用户名是否存在
	    $.ajax({
              type: "POST",
              url: "${basePath}/member/checkMemberRepeatForRegist.html",
              async: false,
              data: {username:username},
              success: function(data) {
            	if(data != null && data.code == 1){
            		flag= true;
  				}else if(data.message =='该用户名已经存在！'){
  					layer.alert(data.message);
  					flag= false;
  				}else{
  					layer.alert(data.message);
  					flag= false; 
  				}
              }
         });
	 
	}else{
		layer.alert('用户名为2~20位字母、数字或汉字');
		return false;
	}
	if(!flag){
		return flag;
	}
	if(username==null || username==""){
		layer.alert('用户名不能为空');
		return false;
	}
	
	//用户名不能包含非法字符@
	if(username.indexOf('@')!=-1){
		layer.alert('用户名不能包含非法字符@');
		return false;
	}
	
	//不能包含非法字符 
	if(haveInvalidChars(username)){
		layer.alert('用户名不能包含非法字符@');
		return false;
	}
	
	//用户名不能全为数字
	var patten_intege1 = regexEnum.intege1;
	if(patten_intege1.test(username)){
		layer.alert('用户名不能全为数字');
		return false;
	}
	
	return true;
	 
}
/**
 * 非法字符
 */
function haveInvalidChars(inString) {
	var result = false;
	var invalidChars=new Array("<","%","\"",">","~","&","?","'","\\","-","#","!");
	for(var i=0;i<invalidChars.length;i++){
		if(inString.indexOf(invalidChars[i])!=-1){
			result = true;
		}
	}
	return result;
}
//传过来的用户名
var userName='${userName}';
var pageSize =  10;//每页显示条数
var tatalPage = '${resultPages.totalPage}';
var select_type ='2';
$(function(){
	$("ul li").click(function(){
	    var num=$(this).nextAll().length, max=$("ul li").length;
	    select_type = num;
	});
	//显示左边列表
	/* memberinfo(); */
	//显示投资 列表
	if('${flagTemp}'==1){
		toggleList('fix',1); 
	}else{
		toggleList('invest',1); 
	}
});
function setlicss(){
	
	 $("#invest_li").removeAttr("class");
	$("#borrow_li").removeAttr("class");
	$("#bbs_items_li").removeAttr("class"); /**/
	$("#fix_li").removeAttr("class");
	
	$("#invest_li").children("span").removeAttr("class");
	$("#borrow_li").children("span").removeAttr("class");
	$("#bbs_items_li").children("span").removeAttr("class");
	$("#fix_li").children("span").removeAttr("class");
}
/**
 * 切换统计数据
 */
function toggleList(type,pageNum){
	 	setlicss(); 
	 /* $("#"+type+"_li").children("span").attr("class","selected"); 
	    $("#"+type+"_li").attr("class","selected"); */
	    $("#"+type+"_li").addClass("selected");
	 if(type=='invest'){
			$("#borrow_li").removeClass("selected");
			$("#bbs_items_li").removeClass("selected");
			$("#fix_li").removeClass("selected");
		 investlist(pageNum);
	 }else if(type=='borrow'){
			$("#invest_li").removeClass("selected");
			$("#bbs_items_li").removeClass("selected");
			$("#fix_li").removeClass("selected");
		 borrowlist(pageNum);
	 }else if(type=='bbs_items'){
			$("#invest_li").removeClass("selected");
			$("#borrow_li").removeClass("selected");
			$("#fix_li").removeClass("selected");
		 bbsItemslist(pageNum);
	 }else if(type=='fix'){
			$("#invest_li").removeClass("selected");
			$("#borrow_li").removeClass("selected");
			$("#bbs_items_li").removeClass("selected");
		 fixInvestlist(pageNum);
	 }
}
/**
 * 投资 列表
 */
function investlist(pageNum){
	$.ajax({
		url : '${basePath}/accountdetail/investlist.html',
		type : 'post',
		data : {userName:userName,pageNum:pageNum,pageSize:pageSize
		},
		success : function(data) {
			$("#down").html(data);
		},
		error : function(result) {
			layer.alert("操作异常,请刷新页面或稍后重试！");
	    }
	});
	
}
/**
 * 投资 列表
 */
function fixInvestlist(pageNum){
	$.ajax({
		url : '${basePath}/accountdetail/fixInvestlist.html',
		type : 'post',
		data : {userName:userName,pageNum:pageNum,pageSize:pageSize
		},
		success : function(data) {
			$("#down").html(data);
		},
		error : function(result) {
			layer.alert("操作异常,请刷新页面或稍后重试！");
	    }
	});
	
}
/**
 * 借款 列表
 */
function borrowlist(pageNum){
	$.ajax({
		url : '${basePath}/accountdetail/borrowlist.html',
		type : 'post',
		data : {userName:userName,pageNum:pageNum,pageSize:pageSize
		},
		success : function(data) {
			$("#down").html(data);
		},
		error : function(result) {
			layer.alert("操作异常,请刷新页面或稍后重试！");
	    }
	});
}

function bbsItemslist(pageNum){
	$.ajax({
		url : '${basePath}/accountdetail/bbsItemslist.html',
		type : 'post',
		data : {userName:userName,pageNum:pageNum,pageSize:pageSize
		},
		success : function(data) {
			$("#down").html(data);
		},
		error : function(result) {
			layer.alert("操作异常,请刷新页面或稍后重试！");
	    }
	});
}

/**
 * ajax 翻页功能
 */
function findPage(pageNo){
	turnFirstTenderRealPageParent(pageNo);
}
/**
 * 优先计划列表翻页
 */
 function turnFirstTenderRealPageParent(pageNo){
	 if(select_type=='2'){
		 type = 'invest';
	 }else if(select_type=='1'){
		 type = 'borrow';
	 }else if(select_type=='0'){
		 type = 'bbs_items';
	 }
	 toggleList(type,pageNo);
}



/**
 * 翻页
 */
function turnPage(pageNum,totalPage,type){
	if(pageNum<=0){
		pageNum = 1;
	}
	if(pageNum>totalPage){
		pageNum = totalPage;
	}
	toggleList(type,pageNum);
}
/**
 * 跳转
 */
function turnPageByPageNum(type,totalPage){
	var pageNum = $("#pageNum_search").val();
	if(pageNum<=0){
		pageNum = 1;
	}
	if(pageNum>totalPage){
		pageNum = totalPage;
	}
	toggleList(type,pageNum); 
}

/**
 * 显示用户详情
 */
function showMemberInfo(userName){
	$("#memberInfouserName").val(userName);
	$("#showMemberInfoFrom").submit();
}
//会员等级说明
function toUserLevel(){
	//查看借款协议
	$.layer({
		type : 2,
		fix : false,
		shade : [0.6, '#E3E3E3' , true],
		shadeClose : true,
		border : [10 , 0.7 , '#272822', true],
		title : ['',false],
		offset : ['50px',''],
		area : ['540px','420px'],
		iframe : {src : '${basePath}/myaccount/toUserLevel.html'},
		close : function(index){
			layer.close(index);
		}
	});
}


</script>
</html>
