<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<div id="menu_left">
       <ul id="menuul">
           <li onclick="showHideLeftItems(this);" class="current"><a href="${path }/anquan.html"><span class="icon icon8"></span>安全保障</a>
             <ul>
                    <li id="menu1-1"><a href="${path }/anquan/yewu.html">业务模式</a></li>
                    <li id="menu1-2"><a href="${path }/anquan/benjin.html">本金保障计划</a></li>
                    <li id="menu1-3"><a href="${path }/anquan/benyongjin.html">风险备用金</a></li>
                    <li id="menu1-4"><a href="${path }/anquan/huigou.html">债权回购</a></li>
                    <li id="menu1-5"><a href="${path }/anquan/jiandu.html">外部监督机制保障</a></li>
                    <li id="menu1-6"><a href="${path }/anquan/baozhang.html">内部透明运营保障</a></li>
                    <li id="menu1-7"><a href="${path }/anquan/jishu.html">独立技术保障</a></li>
                    <li id="menu1-8"><a href="${path }/anquan/fawu.html">法务保障</a></li>
                    <li id="menu1-9"><a href="${path }/anquan/jinrong.html">传统金融机构支持</a></li> 
                    <li id="menu1-10"><a href="${path }/anquan/fengkong.html">有效的风控手段</a></li> 
             </ul>
          </li>
       </ul>
</div>
<script>
/**
 * 初始化
 */
window.onload=function(){
	var moneyStrAry = new Array("anquan/yewu.html","anquan/benjin.html","anquan/benyongjin.html",
	                 "anquan/huigou.html","anquan/jiandu.html","anquan/baozhang.html","anquan/jishu.html",
	                 "anquan/fawu.html","anquan/jinrong.html","anquan/fengkong.html");
	setMenuCss(moneyStrAry,1);
}

/**
 * 设置菜单样式
 */
function setMenuCss(ary,no){
	var pathStr = window.location.pathname ;
	for(var i=0;i<ary.length;i++){
		if(pathStr.indexOf(ary[i])!=-1){
			showHideLeftItems($("#menu"+no+"-1").parent('ul').parent('li'));
			$("#menu"+no+"-"+(i+1)).addClass("sec");
		}
	}
}
/**
 * 一级菜单样式
 */
function showHideLeftItems(obj){
	$("#menuul").children('li').removeClass("current");
	$(obj).addClass("current");
	
// 	var n = $(obj).children('ul').css("display");
// 	if(n == 'block'){
// 		$("#menuul").children('li').children('ul').css("display","none");
// 		$(obj).children('ul').css("display","none");
		
// 	}else{
// 		$("#menuul").children('li').children('ul').css("display","none");
// 		$(obj).children('ul').css("display","block");
// 	}
}

</script>
