<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
 
<div id="menu_left">
	<ul id="menuul">
		<li id="firstMenu1" onclick="showHideLeftItems(this);" class="current"><a href="${path }/bangzhu/daohang.html"><span class="icon icon8"></span>新手导航</a>
			<ul>
				<li id="menu1-1"><a href="${path }/bangzhu/1.html">了解顶玺金融</a></li>
				<li id="menu1-2"><a href="${path }/bangzhu/2.html">注册登录</a></li>
				<li id="menu1-3"><a href="${path }/bangzhu/3.html">充值提现</a></li>
				  <%-- <li id="menu1-4"><a href="${path }/help/newcomer/safe.html#1,4,6">账户安全</a></li> --%>
				<li id="menu1-4"><a href="${path }/bangzhu/4.html">如何投标</a></li>
				<li id="menu1-5"><a href="${path }/bangzhu/5.html">投资资费</a></li>
			</ul>
		</li>
		<li id="firstMenu2" onclick="showHideLeftItems(this);" ><a href="${path }/bangzhu/touzi.html"><span class="icon icon1"></span>如何投资</a>
			<ul>
				<li id="menu2-1"><a href="${path }/bangzhu/6.html">新手必读</a></li>
				<li id="menu2-2"><a href="${path }/bangzhu/7.html">产品介绍</a></li>
				<li id="menu2-3"><a href="${path }/bangzhu/8.html">收益与费用</a></li>
				<li id="menu2-4"><a href="${path }/bangzhu/9.html">投标直通车</a></li>
				<li id="menu2-5"><a href="${path }/bangzhu/10.html">官方标投资</a></li>
				<li id="menu2-6"><a href="${path }/bangzhu/11.html">债权转让</a></li>
				<li id="menu2-7"><a href="${path }/bangzhu/22.html">直通车转让</a></li>
				<li id="menu2-8"><a href="${path }/bangzhu/23.html">活期宝</a></li>
				<li id="menu2-9"><a href="${path }/bangzhu/24.html">定期宝</a></li>
			</ul>
		</li>
		<li id="firstMenu3" onclick="showHideLeftItems(this);" ><a href="${path }/bangzhu/jiekuan.html"><span class="icon icon2"></span>如何借款</a>
			<ul>
				<%-- <li id="menu3-1"><a href="${path }/bangzhu/12.html">产品介绍</a></li> --%>
				<%-- <li id="menu3-2"><a href="${path }/help/borrow/fee.html#3,2,8">借款费用</a></li> --%>
				<li id="menu3-1"><a href="${path }/bangzhu/12.html">如何申请</a></li>
				<li id="menu3-3"><a href="${path }/bangzhu/14.html">申请确认</a></li>
				<li id="menu3-4"><a href="${path }/bangzhu/15.html">来司面审</a></li>
				<li id="menu3-5"><a href="${path }/bangzhu/16.html">外访调查</a></li>
				<li id="menu3-6"><a href="${path }/bangzhu/17.html">发放标的</a></li>
				<li id="menu3-7"><a href="${path }/bangzhu/18.html">放款</a></li>
			</ul>
		</li>
		<li id="firstMenu4" onclick="showHideLeftItems(this);" ><a href="${path }/bangzhu/zhanghu.html"><span class="icon icon5"></span>账户管理</a>
			<ul>
				<%-- <li id="menu4-1"><a href="${path }/help/account/reglogin.html#4,1,5">登录注册</a></li> --%>
				<li id="menu4-1"><a href="${path }/bangzhu/19.html">账户密码</a></li>
				<%-- <li id="menu4-3"><a href="${path }/help/account/recharge.html#4,3,5">充值</a></li> --%>
				<li id="menu4-2"><a href="${path }/bangzhu/20.html">银行卡管理</a></li>
				<li id="menu4-3"><a href="${path }/bangzhu/21.html">安全认证</a></li>
				<li id="menu4-5"><a href="${path }/bangzhu/27.html">关于资金存管</a></li>
				<li id="menu4-4"><a href="${path }/bangzhu/26.html">元宝和会员</a></li>
			</ul>
		</li>
		<li id="firstMenu5" onclick="showHideLeftItems(this);" ><a href="${path }/bangzhu/jieshi.html"><span class="icon icon7"></span>名词解释</a>
		</li>
		<%-- <li id="menu6"><a href="${path }/chengxindai.html"><span class="icon icon9"></span>关于借款</a></li>
		<li id="menu7"><a href="${path }/help/aboutBid/index.html#7"><span class="icon icon10"></span>关于投标</a></li> --%>
		<li id="firstMenu6" onclick="showHideLeftItems(this);" ><a href="${path }/bangzhu/qita.html"><span class="icon icon11"></span>其他问题</a>
		</li>
	</ul>
</div>

<script>
/**
 * 初始化
 */
window.onload=function(){
// 	var aboutBorrowStrAry = new Array("jingzhidai.html","chengshangdai.html","jingzhidai.html");
// 	setMenuCss(moneyStrAry,1);

    //设置一级菜单
    var firstMenu = new Array("bangzhu/daohang.html","bangzhu/touzi.html","bangzhu/jiekuan.html","bangzhu/zhanghu.html",
    		                "bangzhu/jieshi.html","bangzhu/qita.html");
	setMenuCss(firstMenu,0);

    var daohangAry = new Array("bangzhu/1.html","bangzhu/2.html","bangzhu/3.html","bangzhu/4.html","bangzhu/5.html");
	setMenuCss(daohangAry,1);
	
    var touziAry = new Array("bangzhu/6.html","bangzhu/7.html","bangzhu/8.html","bangzhu/9.html","bangzhu/10.html","bangzhu/11.html","bangzhu/22.html","bangzhu/23.html","bangzhu/24.html");
	setMenuCss(touziAry,2);
	
    var jiekuanAry = new Array("bangzhu/12.html","bangzhu/13.html","bangzhu/14.html","bangzhu/15.html","bangzhu/16.html","bangzhu/17.html"
    						  ,"bangzhu/18.html");
	setMenuCss(jiekuanAry,3);
	
    var zhanghuAry = new Array("bangzhu/19.html","bangzhu/20.html","bangzhu/21.html","bangzhu/26.html","bangzhu/27.html");
	setMenuCss(zhanghuAry,4);
	
	//设置流动条 
	var pathStr = window.location.pathname ;
	if(pathStr.indexOf("chengxindai.html")!=-1){
		window.scrollTo(0,1200);//诚薪贷
	}
	if(pathStr.indexOf("chengshangdai.html")!=-1){
		window.scrollTo(0,1200);//诚商贷
	}else if(pathStr.indexOf("jingzhidai.html")!=-1){
		window.scrollTo(0,0);//净值贷
	}
	//处理#
	processHash();
}

/**
 * 设置菜单样式
 */
function setMenuCss(ary,no){
	var pathStr = window.location.pathname ;
	for(var i=0;i<ary.length;i++){
		if(pathStr.indexOf(ary[i])!=-1){
			if(no==0){//一级菜单
				showHideLeftItems($("#firstMenu"+(i+1)));
			    return;
			}
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
	
	$("#menuul").children('li').children('ul').css("display","none");
	var uls = $(obj).children('ul');
	if(uls.length==0){
		return;
	}
	var n = $(obj).children('ul').css("display");
	if(n == 'block'){
		$(obj).children('ul').css("display","none");
	}else{
		$(obj).children('ul').css("display","block");
	}
}

/**
 * 处理#值
 */
function processHash(){
	var hashStr = window.location.hash;
	if(hashStr.indexOf("#")==-1){
		return;
	}
	var begin = hashStr.indexOf("listId");
	if(begin!=-1){
		var listId = hashStr.subString(begin+7,hashStr.length);
		if(listId<100){
			list(listId);
			if(listId>7&&listId<13){
				window.scrollTo(0,400);
			}else if(listId>=13&&listId<20){
				window.scrollTo(0,500);
			}
		}
	}
}
</script>
