<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%> 
<%@ include file="/WEB-INF/page/common/taglib.jsp"%> 
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ include file="/WEB-INF/page/common/public.jsp"%>
<link href="${basePath}/css/borrow.css" rel="stylesheet" type="text/css"/>
<script type="text/javascript" src="${basePath}/js/formValid.js"></script>
<title>我要融资-诚薪贷借款信息填写-认证资料上传_顶玺金融</title>
</head>
<body>
<%@ include file="/WEB-INF/page/common/header.jsp"%>
<!--内容开始-->
<div id="Container">
<div id="Bmain">
	
<div class="title">
	<span class="home"><a href="${path}/">顶玺金融</a></span>
	<span><a href="javascript:void(0);"> 我要融资</a></span>
	<span><a href="javascript:void(0);"> 融资产品</a></span>
	<span><c:if test="${productType==1}">诚薪贷</c:if><c:if test="${productType==2}">诚商贷</c:if>借款信息填写认证资料上传</span>
</div>
	
<form id="fm" name="fm" method="post" enctype="multipart/form-data">
	<div id="rz_main">
		<div class="rztitle">认证资料上传</div>
		<c:if test="${pledgeType == 2 }">
			<div class="rz_borrow">
				<table style="padding-bottom: 20px;">
					<tr>
						<td>使用该用户历史标资料：</td>
						<td>
							<select id="oldBorrowId" name="oldBorrowId" class="rz_search" style="width: 280px;" >
								<option value="">--请选择--</option>
								<c:forEach items="${beforeList }" var="o">
									<option value="${o.id }">${o.name }【借款标编号：${o.contractNo }】</option>
								</c:forEach>
							</select>
							<span class="rz_searchbtn"><a name="upbtn" id="upbtn" href="javascript:useOldPics()">上传</a></span>
						</td>
					</tr>
				</table>
			</div>
		</c:if>
		<div class="rz_borrow">
			<table style="padding-bottom: 20px;">
				<tr>
					<td>证件类型：</td>
					<td>
						<select id="style" name="style" class="rz_search" dataType="Require" msg="请选择证件类型" >
							<option value="">--请选择--</option>
							<c:forEach items="${styleOptions }" var="o">
								<option value="${o.name }">${o.value }</option>
							</c:forEach>
						</select>
					</td>
					<td>
						<input type="file" name="files" id="files" class="rz_input" multiple="multiple" dataType="Require" msg="请选择要上传的文件" maxlength="180"/>
						&nbsp;<span class="rz_searchbtn"><a name="upbtn" id="upbtn" href="javascript:forupload()">上传</a></span>
					</td>
				</tr>
			</table>
			<br/>
			<table >
				<tr>
					<td><font class="needFlag">*说明：请上传后缀为jpg,jpeg,gif,png格式的图片，可同时上传5张。</font></td>
					<td><span class="rz_searchbtn" style="width: 140px;"><a name="upbtn" id="upbtn" href="javascript:delAllDoc()">删除全部资料</a></span></td>
				</tr>
			</table>
			
		</div>

		<div class="rz_borrow1">
			<div class="rz_type1" style="border-top: 1px solid #dbdbdb;">
				<ul>
					<c:forEach items="${list}" var="o">
						<li class="rz_ltypetxt">
							<img src="${basePath}${o.docPath}" width="229" height="140" />
							<span>类型：${o.styleLabel }<a href="javascript:delDoc(${o.id })">【删除】</a></span>
						</li>
					</c:forEach>
				</ul>
			</div>
		</div>
	</div>
<input type="hidden" name="borrowId" value="${borrowId }" />
</form>
</div>                   
</div>
<!--内容结束-->
<%@ include file="/WEB-INF/page/common/footer.jsp"%>

<script type="text/javascript">
function useOldPics(){
	//验证
	if ($("#oldBorrowId").val()==''){
		alert('请选择历史标');
		return false;
	} 
	
	//提交
	var _load = layer.load('处理中..');
	layer.confirm("确认上传吗?" , function (){
		$("#fm").ajaxSubmit({
			url : '${basePath}/salariatBorrow/uploadOldBorrowInfos.html',
			type : 'post',
			dataType :"json",
			success : function(result) {
				layer.close(_load);
				if (result.code == '0') {
					parent.layer.msg(result.message, 1, 5);
				}else{
					parent.layer.msg(result.message, 1, 1, function() {
						window.parent.location.href = '${path}/salariatBorrow/initUpload.html?borrowId=${borrowId}';
					});
				}
			},
			error : function(result) {
				layer.close(_load);
				layer.msg('网络连接超时,请您稍后重试.', 1, 5);
		    }
		});
	});
}

function forupload(){
	//验证
	if (Validator.Validate('fm',1)==false) return;
	
	//提交
	var _load = layer.load('处理中..');
	$("#fm").ajaxSubmit({
		url : '${basePath}/salariatBorrow/uploadBorrowInfos.html',
		type : 'post',
		dataType :"json",
		success : function(result) {
			layer.close(_load);
			if (result.code == '0') {
				parent.layer.msg(result.message, 1, 5);
			}else{
				parent.layer.msg(result.message, 1, 1, function() {
					window.parent.location.href = '${path}/salariatBorrow/initUpload.html?borrowId=${borrowId}';
				});
			}
		},
		error : function(result) {
			layer.close(_load);
			layer.msg('网络连接超时,请您稍后重试.', 1, 5);
	    }
	});

}


//删除上传资料
function delDoc(docId) {
	
	if(layer.confirm("确定要删除吗？",function(){
		var _load = layer.load('处理中..');
		var _url = "${basePath }/salariatBorrow/deleteDoc.html?borrowId=${borrowId}&docId="+docId;
		$.ajax({
			url : _url,
			type:'post',
			dataType :"json",
			success : function(result) {
				layer.close(_load);
				if (result.code == '0') {
					parent.layer.msg(result.message, 1, 5, function() {
						
					});
					return;
				}else{
					parent.layer.msg(result.message, 1, 1, function() {
						window.parent.location.href = '${path}/salariatBorrow/initUpload.html?borrowId=${borrowId}';
					});
				}
				
			},
			error : function(result) {
				layer.close(_load);
				layer.msg('网络连接超时,请您稍后重试.', 1, 5);
		    }
		});
	}));
	
}

//删除全部资料
function delAllDoc(docId) {
	
	if(layer.confirm("确定要删除全部资料吗？",function(){
		var _load = layer.load('处理中..');
		var _url = "${basePath }/salariatBorrow/deleteAllDoc/${borrowId}.html";
		$.ajax({
			url : _url,
			type:'post',
			dataType :"json",
			success : function(result) {
				layer.close(_load);
				if (result.code == '0') {
					parent.layer.msg(result.message, 1, 5, function() {
						
					});
					return;
				}else{
					parent.layer.msg(result.message, 1, 1, function() {
						window.parent.location.href = '${path}/salariatBorrow/initUpload.html?borrowId=${borrowId}';
					});
				}
				
			},
			error : function(result) {
				layer.close(_load);
				layer.msg('网络连接超时,请您稍后重试.', 1, 5);
		    }
		});
	}));
	
}
</script>
</body>
</html>