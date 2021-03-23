<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/page/common/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ include file="/WEB-INF/page/common/public.jsp"%>
<title>VIP认证_顶玺金融</title>
</head>

<body style="background:#f9f9f9;">
<!--头部开始-->
<%@ include file="/WEB-INF/page/common/header.jsp" %>
<!--头部结束-->

<!--安全中心左侧开始-->
<div id="Container">
<div id="Bmain">
    <div class="title">
    	<span class="home"><a href="${path}/">顶玺金融</a></span>
			<span><a href="${path}/myaccount/toIndex.html">我的账户</a></span>
			<span>账户管理</span>
			<span><a href="${path }/AccountSafetyCentre/safetyIndex.html">安全中心</a></span>
			<span>VIP认证</span>
  	</div>
	<!--安全中心左侧 开始 -->    
	<%@ include file="/WEB-INF/page/account/myAccount_leftMenu.jsp"%> 
	<!--安全中心左侧 结束 -->    
	<!--安全中心右侧开始 --> 
	<div class="lb_waikuang border whitebg">
        <div class="safetoptit">安全中心</div>   
        <div class="safe">
	        <div class="safebox520">
                    <dl>
                        <dd class="currenttext"><div class="tipone"><span>温馨提示：</span>建议理财用户申请VIP（VIP年费用为10元）。
                        <c:if test="${indateTip != ''}">
		            	 <label style="margin-left:100px;">${indateTip}</label>&nbsp;
		            	</c:if>
                        </div>
                        </dd>
                        <dd class="currentdd"><span>交易密码：</span>
                        	<c:if test="${null!=nosetPaypassword }">
                        		<a href="${path}/account/safe/toSetPayPwd.html">请先设置交易密码</a>
                        	</c:if>
                        	<c:if test="${null==nosetPaypassword }">
                        		<input type="password" class="inputtext" name="paypassword" id="paypassword">
                        	</c:if>
                        </dd>
                        <dd class="currentdd"> <span>&nbsp;</span> <input type="checkbox" name="checkvip">同意<a href="javascript:toVipXiyi();" title="顶玺金融网站使用条款">《vip协议》</a></dd>
                        <dd class="currentdd" ><span>&nbsp;</span> <input id="btnVIPAppro" type="button" value="立即申请" class="safe_button01"  onclick="forsubmit();"/></dd>
                    </dl>
                 </div>
        </div>
	</div>            
	<!--安全中心右侧结束 -->
</div>
</div>
<%@ include file="/WEB-INF/page/common/footer.jsp" %>
</body>
<script type="text/javascript">
$(function(){
});

function toVipXiyi(){
	//查询顶玺金融VIP会员使用条例
	$.layer({
		type : 2,
		fix : false,
		shade : [0.6, '#E3E3E3' , true],
		shadeClose : true,
		border : [10 , 0.7 , '#272822', true],
		title : ['',false],
		offset : ['50px',''],
		area : ['500px','450px'],
		iframe : {src : '${basePath }/account/approve/vip/toVipXiyi.html'},
		close : function(index){
			layer.close(index);
		}
	});
}

/**
* 提交表单
*/
function forsubmit(){
	$("#btnVIPAppro").removeAttr("onclick");
	if(verify()){
		if(layer.confirm("VIP年费用为10元,您是否继续 ？",function(){
			var paypassword = $("#paypassword").val();
			$.ajax({
				url : '${basePath}/account/approve/vip/saveOrUpdate.html',
				data :{paypassword:paypassword} ,
				type : 'post',
				dataType : 'text',
				success : function(data){
					if(data=="success"){
			    		layer.alert("申请成功" , 1, "温馨提示",function(){
			    			window.open("${path}/account/approve/vip/vipforinsert.html","_parent");
			    		});
			    	}else{
			    		if(data == '交易密码未设置，请先设置交易密码！'){
			    			layer.msg(data, 2, 5,function(){
						    	location.href="${path}/account/safe/toSetPayPwd.html";
					    	});
			    		}else{
			    			layer.alert(data);	
			    		}
			    	}
					$("#btnVIPAppro").attr("onclick","forsubmit();");
				},
				error : function(data) {
					layer.msg('网络连接异常，请刷新页面或稍后重试！', 1, 5);
					$("#btnVIPAppro").attr("onclick","forsubmit();");
			    }
			});
		},function(){
			$("#btnVIPAppro").attr("onclick","forsubmit();");
		}));
		
	}else{
		$("#btnVIPAppro").attr("onclick","forsubmit();");
	}
}
/**
 * 验证数据 
 */
function verify(){
	var flag = true;
	var paypassword = $("#paypassword").val();
	if(paypassword == null || paypassword == ''){
		layer.alert("请输入交易密码!");
		return false;
	}
	 if(!$("input[name='checkvip']").attr("checked")){
		 layer.alert("您未勾选VIP协议");
		 return false;
	 }
	return flag;
}

</script>
</html>
