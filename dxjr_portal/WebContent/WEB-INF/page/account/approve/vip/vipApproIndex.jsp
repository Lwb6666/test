<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/page/common/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ include file="/WEB-INF/page/common/public.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>顶玺金融互联网专业的P2P网站</title>

</head>

<body>
<div id="user_mian">
<div id="user_mian_right">
	<div class="zjjl">
        <ul>
			<li id="viphref"><a href="javascript:vipApproveFun();" id="vip">VIP认证</a></li>
		</ul>
   </div>
   <div class="zjjl_list" id="user_right_main">
   </div>
</div>
</div>

<script type="text/javascript">
function vipApproveFun(){
	//提醒用户是否进入VIP页面
	location.href="${path}/account/approve/vip/vipforinsert.html";
	/* $.ajax({
		url : '${basePath}/account/approve/vip/checkVipAppro.html',
		data : {},
		type : 'post',
		success : function(data){
			if(data=="true"){
				location.href="${path}/account/approve/vip/vipforinsert.html";
				//window.open("${path}/account/approve/vip/vipforinsert.html","_self");
			}else if(data=="false"){
				if(confirm("申请VIP，可保本保息，一年费用为10元。确认要进入吗？")){
					$.post("${basePath}/account/approve/vip/vipforinsert.html", {
					}, function(data) {
						$("#user_right_main").html(data);
					});
				}
			}else{
				layer.alert(data);
			}
		},
		error : function(data) {
			layer.msg('网络连接异常，请刷新页面或稍后重试！', 1, 5);
		}
	});	 */
}


</script>
</body>
</html>
