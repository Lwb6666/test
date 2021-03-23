<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/page/common/taglib.jsp"%>
<script type="text/javascript" src="${basePath}/js/location.js"></script>
<script type="text/javascript" src="${path }/js/formValid.js?${version}"></script>
<div class="cont-word" style="max-width:880px!important; padding-bottom:50px; overflow:hidden;">
<div class="title"><h4>解绑银行卡</h4><a onclick="closeEditInitUploadPics();" class="icon icon-close fr"></a></div>
<div class="form-info-layer">
         <div class="sendid">
			<table>       
		          <tr style="display: none">
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
                     <tr >
                       <form  id="myfrom1"   name="myfrom1"  method="post">
                         <p>
	                         <span class="w1"><span class="red">*</span>手持本人身份证明证件照片</span>
	                         <span class="w2"><input type="file" name="files" id="files1" dataType="Require" msg="请选择要上传的文件" maxlength="180"/>
	                         <input  type="hidden"  name="picType"   value="手持本人身份证明证件照片" >
	                         </span><span><a  class="zdtz-btn mr10" name="upbtn" id="upbtn1" onclick="forupload('myfrom1')">上传</a></span>
                         </p>
                        </form>
		           </tr>
                  <c:if test="${sessionScope.updateReason =='原卡损坏' || sessionScope.updateReason =='原卡丢失' }">
	                	<tr>	
	                	     <form  id="myfrom2"   name="myfrom2"  method="post" >
		                         <p>
			                         <span class="w1"><span class="red">*</span>银行卡挂失证明</span>
			                         <span class="w2"><input type="file" name="files" id="files2" dataType="Require" msg="请选择要上传的文件" maxlength="180"/>
			                         <input  type="hidden"  name="picType"   value="银行卡挂失证明" >
			                         </span><span><a  class="zdtz-btn mr10" name="upbtn" id="upbtn2" onclick="forupload('myfrom2')">上传</a></span>
		                         </p>
	                         </form>		
						</tr>
					</c:if>  
					<c:if test="${sessionScope.updateReason !='原卡损坏' && sessionScope.updateReason !='原卡丢失' }">
	                	<tr>		
	                	     <form  id="myfrom3"   name="myfrom3"  method="post" >
		                         <p>
			                         <span class="w1"><span class="red">*</span>手持银行卡照片</span>
			                         <span class="w2"><input type="file" name="files" id="files3" dataType="Require" msg="请选择要上传的文件" maxlength="180"/>
			                         <input  type="hidden"  name="picType"   value="手持银行卡照片" >
			                         </span><span><a  class="zdtz-btn mr10" name="upbtn" id="upbtn3" onclick="forupload('myfrom3')">上传</a></span>
		                         </p>
	                         </form>			
						</tr>
					</c:if>                  
					<tr>			
					     <form  id="myfrom4"   name="myfrom4"  method="post" >
		                         <p>
			                         <span class="w1"><span class="red">*</span>其他证件证明</span>
			                         <span class="w2"><input type="file" name="files" id="files4" dataType="Require" msg="请选择要上传的文件" maxlength="180"/>
			                         <input  type="hidden"  name="picType"   value="其他证件证明" >
			                         </span><span><a  class="zdtz-btn mr10" name="upbtn" id="upbtn4" onclick="forupload('myfrom4')">上传</a></span>
		                         </p>
	                         </form>		
					</tr>    		
				</table>
				 <div class="showtxt clearfix">
				       <ul>
							<c:forEach items="${pics }" var="p" varStatus="n">
								<li>
									<img src="${path}${p.picUrl }" width="229" height="140" /><br>
									<span>${p.picType}</span><a onclick="deletePic('${n.count-1 }');">【删除】</a>
								</li>
							</c:forEach>
						</ul>
				</div>
		</div>
	    <div>
              <button type="button" class="remove" onclick="closeEditInitUploadPics();">取消</button>
              <button type="button" class="enter" id="btnSaveBank" name="btnSaveBank" onclick="subChange(this);">提交审核</button>
       </div>
    </div>
</div>
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
				showPic1();
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
		$("#myfrom4").ajaxSubmit({
			url : '${basePath}/bankinfo/editPics.html',
			type : 'post',
			dataType :"json",
			success : function(result) {
				layer.close(_load);
				if (result.code == '0') {
					layer.alert(result.message);
					$(obj).attr("onclick", "subChange(this)");
				}else{
					 layer.msg(result.message, 2, 1, function() {
						window.location.href="${basePath}/AccountSafetyCentre/safetyIndex.html";
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

function showPic1(){
	$('#bindBank3').trigger('reveal:open'); 
	$.ajax({
		url : '${basePath}/bankinfo/editInitUploadPics.html',
		data :{
		} ,
		type : 'post',
		dataType : 'html',
		success : function(data){
			$("#bindBank3").html(data);
		},
		error : function(data) {
			layer.msg("网络连接异常，请刷新页面或稍后重试！", 2, 5);
	   }
	});
}
function deletePic(id) {
		$.ajax({
			url : "${path}/bankinfo/editRemovePic1/"+id+".html",
			data :{
			} ,
			type : 'post',
			dataType :"json",
			success : function(result){
				if (result.code == '0') {
					layer.alert(result.message);
				}else{
					showPic1();
				}
			},
			error : function(data) {
				layer.msg("网络连接异常，请刷新页面或稍后重试！", 2, 5);
		   }
		});
	}
</script>
</html>