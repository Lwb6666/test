<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<div class="header">
	<!--topbar-->
	<div class="topbar">
    	<div class="grid-1100">
            <div class="topbar-left">
            	<div style="float:left; margin-left:px; color:#888; line-height:28px; padding-left:16px; font-size:14px; background:url(${basePath}/images/stock/telicon.png) no-repeat 0 2px; "> 客服电话400-0156-676 </div>
            </div>
            <div class="topbar-right">
                <ul class="fr f14">

					<shiro:authenticated>
                	<li class="topmenu-gray"><a href="${path }/myaccount/toIndex.html" style="color:#f05e13; padding-right:0px;">${portal:currentUser().userName }</a><a href="javascript:logout();">退出</a><span>|</span></li>
                	</shiro:authenticated>

                	<li class="topmenu-gray"><a href="${path}/" style="color:#f05e13;">返回官网首页</a><span>|</span> </li>
                    <li class="topmenu-blue"><a href="${path}/sycee.html">元宝商城</a><span>|</span><a href="${path }/lottery_chance/info.html">抽大奖</a><span>|</span></li>
                    <li class="topmenu-gray"><a href="${path}/bangzhu.html">帮助中心</a><span>|</span><a href="${path }/zixun.html">顶玺资讯</a></li>
                </ul>
            </div>
        </div>
    </div>
    <!--nav main-->
    <div class="navbar" role="navigation">
    	<div class="grid-1100">
        	<div class="logo">
    			<a href="${path }/stockAccount/accountIndex.html"><img src="${basePath}/images/stock/logo.png" alt="顶玺金融"/></a>
            </div>
            <div class="share-nav">
            	<ul>
                	<li id="J_menyselected1"><a href="${path }/stockAccount/accountIndex.html"><i class="iconfont">&#xe608;</i><span>账户总览</span></a></li>
                    <li id="J_menyselected2"><a href="${path }/stockBuyer/toBuyerIndex.html"><i class="iconfont">&#xe600;</i><span>份额认购</span></a></li>
                    <li id="J_menyselected3"><a href="${path }/stockSeller/sellerIndex.html"><i class="iconfont">&#xe601;</i><span>份额转让</span></a></li>
                    <li id="J_menyselected4"><a href="${path }/stockSeller/toEntrustMain.html"><i class="iconfont">&#xe609;</i><span>挂单记录</span></a></li>
                </ul>
            </div>
       </div>
    </div>
</div><!--头部end-->
<style type="text/css">
@media only screen and (min-width: 321px) and (max-width: 1024px){ 
.activity-notice,.prize-body,.header .topbar,.navbar{ width:1100px; } 
} 
</style>
<script type="text/javascript">
function logout() {
	layer.confirm("确认退出?" , function (){
		$.ajax({
			url : '${basePath }/logout.html',
			type : 'post',
			dataType : 'text',
			success : function(data) {
				window.open("${path }/","_self");
			},
			error : function(data) {
				layer.msg('网络连接异常，请刷新页面或稍后重试！', 1, 5);
			}
		});
	});
}
</script>

<%@ include file="/WEB-INF/page/common/seo.jsp" %>