<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/page/common/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ include file="/WEB-INF/page/common/public.jsp"%>
<script type="text/javascript" src="${path }/js/formValid.js?${version}"></script>
</head>
<body>
		<%@ include file="/WEB-INF/page/common/header.jsp"%>
		<div id="Bmain">
			<div class="title">
				<span class="home"><a href="${path}/">顶玺金融</a></span><span><a href="${path }/myaccount/toIndex.html">我的账户</a></span><span><a href="#">账户管理</a></span><span><a href="${path }/bankinfo/toBankCard.html">银行卡信息</a></span>
			</div>
			<%@ include file="/WEB-INF/page/account/myAccount_leftMenu.jsp"%>
			<div class="lb_waikuang border whitebg">
				<div class="sfmain">
					<div class="bank">
						<div class="rz_borrow" style="width: 800px;">
							<table>
							 <tr   style="display: none">
								 <form  style="display: none"   id="myfrom0"   name="myfrom0"  method="post" >
									 <table style="display: none">
									 <tr>
									   <td class="f4"><font color="#FF0004">*</font></td>
			                                 <td width="30%">手持本人身份证明证件照片</td>
									 	<td > <input type="file" name="files" id="files" dataType="Require" msg="请选择要上传的文件" maxlength="180"/>
									    <input  type="hidden"  name="picType"   value="1" >
									  <input class="sbtn" type="button" name="upbtn" id="upbtn" onclick="forupload('myfrom0')" value="上传" /></td>
									  </tr>
									  </table>
								</form>
						    </tr>
								  <tr>
                                 <td width="25%" class="f4">认证资料类型：</td>
                                  <td width="25%">&nbsp;</td>
                                 </tr>
                      <tr >
                                 
					 <form  id="myfrom1"   name="myfrom1"  method="post" >
						 <table >
						 <tr>
                                 <td width="30%"><font color="#FF0004">*</font>手持本人身份证明证件照片</td>
						 	<td > <input  class="rz_input mr5" type="file" name="files" id="files" dataType="Require" msg="请选择要上传的文件" maxlength="180"/>
						    <input  type="hidden"  name="picType"   value="手持本人身份证明证件照片" >
						  <input class="rz_searchbtn" type="button" name="upbtn" id="upbtn" onclick="forupload('myfrom1')" value="上传" /></td>
						  </tr>
						  </table>
					</form>
			     </tr>
                         <c:if test="${sessionScope.updateReason =='原卡损坏' || sessionScope.updateReason =='原卡丢失' }">
               <tr>			
                     <form  id="myfrom2"   name="myfrom2" method="post" >
						 <table  class="modify-table">
						 <tr>
                                 <td width="30%"><font color="#FF0004">*</font>银行卡挂失证明</td>
						 	<td > <input  class="rz_input mr5" type="file" name="files" id="files" dataType="Require" msg="请选择要上传的文件" maxlength="180"/>
						    <input  type="hidden"  name="picType"   value="银行卡挂失证明" >
						  <input  class="rz_searchbtn" type="button" name="upbtn" id="upbtn" onclick="forupload('myfrom2')" value="上传" /></td>
						  </tr>
						  </table>
					</form>
					</tr>
					</c:if>           
                             
                             <tr>			
                      <form  id="myfrom3"   name="myfrom3" method="post" >
						 <table  class="modify-table">
						 <tr>
                                 <td width="30%"><font color="#FF0004">*</font>手持新银行卡照片</td>
						 	<td > <input class="rz_input mr5" type="file" name="files" id="files" dataType="Require" msg="请选择要上传的文件" maxlength="180"/>
						    <input  type="hidden"  name="picType"   value="手持新银行卡照片" >
						  <input class="rz_searchbtn" type="button" name="upbtn" id="upbtn" onclick="forupload('myfrom3')" value="上传" /></td>
						  </tr>
						  </table>
					</form>
					</tr>
					
						<tr>			
                    <form  id="myfrom4"   name="myfrom4" method="post" >
						 <table  >
						 <tr>
                                 <td width="30%">其他证件证明</td>
						 	<td > <input type="file"  class="rz_input mr5" name="files" id="files" dataType="Require" msg="请选择要上传的文件" maxlength="180"/>
						    <input  type="hidden"  name="picType"   value="其他证件证明" >
						  <input class="rz_searchbtn" type="button" name="upbtn" id="upbtn" onclick="forupload('myfrom4')" value="上传" /></td>
						  </tr>
						  </table>
					</form>
					</tr>    		
	
							</table>
						</div>
						
						<div class="rz_borrow1" style="width: 800px;">
							<div class="rz_type1" style="border-top: 1px solid #dbdbdb; width: 800px;">
								<ul>
									<c:forEach items="${pics }" var="p" varStatus="n">
									<li class="rz_ltypetxt" >
										<img src="${path}${p.picUrl }" width="229" height="140" />
										<span>${p.picType}<a  href="${path}/bankinfo/editRemovePic/${n.count-1 }.html"   >【删除】</a></span>
									
									</li>
									</c:forEach>
								</ul>
							</div>
							<div class="rz_borrow" style="width: 800px;">
								<div class="gg_btn">
									<input class="fl" type="button" value="提交审核" onclick="subChange(this)" />
									<input type="button" value="返回" onclick="javascript:window.history.back();" />
								</div>
							</div>
						</div>
						
						
					</div>
					<div class="fl tip">
					<div class="safebox550">
						<h3>温馨提示：</h3>
						<p>1.更换卡的次数不能超过10次</p>
						<p>2.更换卡需等待客服审核，1-2工作日内处理或联系客服热线：400-000-0000</p>
						<p>3.认证资料类型包括身份证，挂失证明等其他类型.上传最多不超过10份</p>
					</div>
				</div>
			</div>
		</div>
		<%@ include file="/WEB-INF/page/common/footer.jsp"%>
</body>
<script type="text/javascript">
function forupload(form){
	//验证
	if (Validator.Validate(form,4)==false) return;
	
	//提交
	var _load = layer.load('处理中..');
	$("#"+form).ajaxSubmit({
		url : '${basePath}/bankinfo/uploadPics.html',
		type : 'post',
		dataType :"json",
		success : function(result) {
			layer.close(_load);
			if (result.code == '0') {
				layer.alert(result.message);
			}else{
				window.parent.location.href = '${path}/bankinfo/editInitUploadPics.html';
			}
		},
		error : function(result) {
			layer.close(_load);
			layer.msg('网络连接超时,请您稍后重试.', 1, 5);
	    }
	});
}	
function subChange(obj){
	$(obj).removeAttr("onclick");
	//提交
	layer.confirm("确定提交吗？", function() {
		var _load = layer.load('处理中..');
		$("#myfrom3").ajaxSubmit({
			url : '${basePath}/bankinfo/editPics.html',
			type : 'post',
			dataType :"json",
			success : function(result) {
				layer.close(_load);
				if (result.code == '0') {
					layer.alert(result.message, 1, 5);
					$(obj).attr("onclick", "subChange(this)");
				}else if (result.code == '2') {
					layer.alert(result.message);
					window.parent.location.href = '${basePath}/bankinfo/initChangeCard.html';
				}else{
					layer.msg(result.message, 2, 1, function() {
						window.parent.location.href = '${basePath}/bankinfo/toBankCard.html';
					});
				}
			},
			error : function(result) {
				layer.close(_load);
				layer.msg('网络连接超时,请您稍后重试.', 1, 5);
				$(obj).attr("onclick", "subChange(this)");
		    }
		});
	},function (){
		$(obj).attr("onclick", "subChange(this)");
	});	
}
</script>
</html>