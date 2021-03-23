<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/page/common/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ include file="/WEB-INF/page/common/public.jsp"%>
<title>
	<c:if test="${member.isFinancialUser == 1 }">
		${member.userNameSecret }_用户详情
	</c:if> 
	<c:if test="${member.isFinancialUser == 0 }">
		${member.username }_用户详情
	</c:if>
</title>


</head>

<body style="background: #f9f9f9;">
	<!--头部开始-->
	<%@ include file="/WEB-INF/page/common/header.jsp"%>
	<!--头部结束-->
	<!--内容开始-->
	<div id="Container">
		<div id="Bmain">
			<div class="title"><span class="home"><a href="${path}/">顶玺金融</a></span><span>用户信息</span></div>
			<div id="rz_main" style="height: auto;">
				<div class="tbtitless">
					<div class="tb_borrow2">
						<div class="user_info">
					<c:if test="${member.headimg!=null }">
		   				<img src="${basePath}${member.headimg }" width="123" height="132" />
		   			</c:if>
		   			<c:if test="${member.headimg==null }">
		   				<img src="${basePath}/images/main/head.png" width="123" height="132" />
		   			</c:if>
						</div>
						<div class="tblenght user_rinfo">
							<table>
							
								<tr>
								    <td id="td1">
								  
								        <div style="color:red; font-size:14px;   width:60%; line-height:35px;">仅可修改一次，请谨慎操作！</div>
		                                <div>   用户名：</span><span  style="padding-left:15px;">  
		                                 <input  id="username" style=" border:1px #ccc solid; height:30px; padding-left:10px; width:100px;  " type="text" />
		                                 <a style=" font-size:14px; padding-left:10px; cursor:pointer; line-height:35px;  color:#99cc33;" onclick="finish();">完成</a></span></div>                                                           
                                         <input type="hidden" value="${member.userNameEncrypt}" id="oldname"/>
                                    </td>
									<td id="td2">用户名：
										${member.userNameSecret}
										<c:if test="${flag!=1 && isView==1}">
										<span style="padding-left:15px;">  <img src="${basePath}/images/xiugai.png"/>
										 <a style="font-size:14px; cursor:pointer;  line-height:35px;  color:#99cc33;" onclick="modify();">修改</a>
										</span>
										</c:if>
									</td>
									<td colspan="2">注册时间：${fn:substring(member.addtimeFmt, 0, 10)}</td>
								</tr>
								<tr>
									<td>会员级别： 
										<c:if test="${userlevel == 0 }">
											<img src="${basePath}/images/vip_0.gif" width="17"
												height="16" />
										</c:if> <c:if test="${userlevel == 10 }">
											<img src="${basePath}/images/vip_10.gif" width="17"
												height="16" />
										</c:if> <c:if test="${userlevel == 20 }">
											<img src="${basePath}/images/vip_20.gif" width="17"
												height="16" />
										</c:if> <c:if test="${userlevel == 30 }">
											<img src="${basePath}/images/vip_30.gif" width="17"
												height="16" />
										</c:if> <c:if test="${userlevel == 40 }">
											<img src="${basePath}/images/vip_40.gif" width="17"
												height="16" />
										</c:if> <c:if test="${userlevel == 50 }">
											<img src="${basePath}/images/vip_50.gif" width="17"
												height="16" />
										</c:if> <c:if test="${userlevel == 60 }">
											<img src="${basePath}/images/vip_60.gif" width="17"
												height="16" />
										</c:if>
									</td>
									<td colspan="2">最后登录时间：${fn:substring(member.lastlogintimeFmt, 0, 10)}</td>
								</tr>

							</table>
						</div>
					</div>
				</div>
			</div>
				<div class="tblist_title">
						<ul>
                                <li id="invest_li" class="selected"><a href="javascript:void(0);" onclick="toggleList('invest',1);">投标列表</a> </li>
                                <li id="fix_li"><a href="javascript:void(0);" onclick="toggleList('fix',1);">投宝列表</a></li>
                                <li id="borrow_li"><a href="javascript:void(0);" onclick="toggleList('borrow',1);">借款列表</a></li>
                                <li id="bbs_items_li"><a href="javascript:void(0);" onclick="toggleList('bbs_items',1);">发帖列表 </a></li>
                        </ul>
			
				<!-- <ul id="tabs">
					<li id="invest_li" class="selected"><span href="javascript:void(0);" onclick="toggleList('invest',1);">投资列表</span></li>
					<li id="borrow_li"><span href="javascript:void(0);" onclick="toggleList('borrow',1);"    >借款列表</span></li>
					<li id="bbs_items_li"><span href="javascript:void(0);" onclick="toggleList('bbs_items',1);">发帖列表 </span></li>
				</ul> -->
			</div>
			
			
			<div id="rz_main"
				style="height: auto; border-top: none; margin-top: 0;">
				<div id="down"></div>
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
/**
 * 显示左边列表
 */
function memberinfo(){
	$.ajax("${basePath}/accountdetail/memberinfo.html", {userName:userName
	}, function(data) {
		$("#tbtitless").html(data); 
	});
}



//将li中的current样式去掉
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
