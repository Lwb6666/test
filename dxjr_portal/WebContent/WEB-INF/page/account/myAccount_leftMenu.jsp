<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/page/common/taglib.jsp"%>
 <div id="menu_left">
    <ul id="menuul">
       	<li onclick="showHideLeftItems(this);" class="current"><a href="${path}/myaccount/toIndex.html"><span class="icon icon1"></span>我的账户</a></li>
       	<li onclick="showHideLeftItems(this);" id="LEFT_MENU_ZJ"><a href="javascript:;"><span class="icon icon2"></span>资金管理</a>
			<ul style="display:none">
              <li id="menu2-1"><a href="${path}/accOpertingRecord/paymentDetails/1.html" >资金明细</a></li>
              <li id="menu2-2"><a href="${path}/account/topup/toTopupIndex.html">充值</a></li>
              <li id="menu2-3"><a href="${path}/myaccount/cashRecord/toCashIndex.html">提现</a></li>
           	</ul>
       	</li>
       	
       	<li onclick="showHideLeftItems(this);"><a href="javascript:;"><span class="icon icon3"></span>投资管理</a>
           <ul style="display:none">
          	  <li id="menu3-6"><a href="${path}/curAccountController/curAccountInterest.html">活期宝</a></li>
              <li id="menu3-7"><a href="${path}/dingqibao/myAccount.html">定期宝</a></li>
              <li id="menu3-1"><a href="${path}/account/InvestManager/queryTendeIndex.html" >投标列表</a></li>
              <li id="menu3-2"><a href="${path}/account/InvestManager/collectionindex/c.html" >普通待收</a></li>
              <li id="menu3-3"><a href="${path}/account/InvestManager/collectionindex/z.html" >直通车待收</a></li>
              <li id="menu3-4"><a href="${path}/account/InvestManager/firsttenderindex.html" >直通车列表</a></li> 
              <li id="menu3-5"><a href="${path}/myaccount/autoInvest/autoInvestMain.html">自动投资</a></li>
            
           </ul>
       	</li>
       	
        <li onclick="showHideLeftItems(this);"><a href="javascript:;"><span class="icon icon3"></span>债权转让</a>
           <ul style="display:none">
               <li id="menu8-1"><a href="${path}/zhaiquan.html">债权转入</a></li>
              <li id="menu8-2"><a href="${path}/zhaiquan/totransfercontainer.html">债权转出</a></li>
              <li id="menu8-3"><a href="${path}/zhaiquan.html?type=first">直通车转入</a></li>
              <li id="menu8-4"><a href="${path}/zhitongche/zhuanrang/tocontainer.html">直通车转出</a></li>
           </ul>
       	</li>
       	
       	
       	
       	<li onclick="showHideLeftItems(this);" id="LEFT_MENU_RZ"><a href="javascript:;"><span class="icon icon4"></span>融资管理</a>
       		<ul style="display:none">
              <li id="menu4-1"><a href="${path }/repayment/enterRepayMent.html" id="LEFT_MENU_RZ_REPAYMENT" onclick="resetRepayment();">待还列表</a></li>
              <li id="menu4-2"><a href="${path }/myaccount/toTendering.html" id="LEFT_MENU_RZ_RENDERING">正在招标中</a></li>
              <li id="menu4-3"><a href="${path }/myaccount/borrowList.html" id="LEFT_MENU_RZ_RENDERING">融资列表</a></li>
           </ul>
       	</li>
       	<li onclick="showHideLeftItems(this);"><a href="javascript:;"><span class="icon icon5"></span>账户管理</a>
       		<ul style="display:none">
              <li id="menu5-1"><a href="${path}/AccountSafetyCentre/safetyIndex.html">安全中心</a></li>
              <li id="menu5-2"><a href="${path}/bankinfo/toBankCard.html">银行卡信息</a></li>
            </ul>
       	</li>
       	<li onclick="showHideLeftItems(this);">
       	<a href="javascript:;"><span class="icon icon6"></span>有奖活动<c:if test="${redDot!=null}"><img class="redDot" src="${basePath}/images/notice.png" /></c:if></a>
       		<ul style="display:none">
       			<li id="menu6-1" >
       			   <a href="${path}/lottery_use_record/lott_use_record.html" >我的奖励 <c:if test="${redDot!=null}"><img class="redDot" src="${basePath}/images/notice.png"  /></c:if> </a>
       			</li>
       			<li id="menu6-4"><a href="${path}/account/sycee.html">我的元宝</a></li>
				<li id="menu6-2"><a href="${path}/myaccount/friend/friendList/1.html">推荐统计</a></li>
				<li id="menu6-3"><a href="${path}/myaccount/friend/inviteRank.html">推荐排名</a></li>
			</ul>
       	</li>
		<li onclick="showHideLeftItems(this);"><a href="javascript:;"><span class="icon icon6"></span>期权管理</a>
       		<ul style="display:none">
				<li id="menu7-1"><a href="${path}/stock/myStock.html">我的期权</a></li>
			</ul>
       	</li>
   </ul>
   <div ><a href="${path}/myaccount/autoInvest/autoInvestMain.html" title="自动投资"><img style="margin-top:15px;" src="${path}/images/small-banner.png"/></a></div>
   <div ><a href="${path}/activity/invited.html" title="推荐有奖"><img style="margin-top:15px;" src="${path}/images/smallbannerleft.jpg"/></a></div>
</div>

<script type="text/javascript">

/* $(function(){
	//选中菜单，设置样式
	var firstMenu = '${ACCOUNT_FIRST_MENU}';
	var secondMenu = '${ACCOUNT_SECOND_MENU}';
	if(""!=firstMenu){
		showHideLeftItems(document.getElementById(firstMenu));
		$("#"+secondMenu).attr("style","color:#fff ;width:131px; background:#00a7e5;display:block;");
	}
}); */

var globalRepaymentStatus = 0;

function resetRepayment(){
	$("#keyword").attr("value","");
	$("#beginTime").attr("value","");
	$("#endTime").attr("value","");
}

/**
 * 初始化
 */
window.onload=function(){
	var moneyStrAry = new Array("accOpertingRecord/paymentDetails","topup/toTopupIndex","cashRecord/toCashIndex");
	setMenuCss(moneyStrAry,2);
	
 
	
	var bidStrAry = new Array("InvestManager/queryTendeIndex","InvestManager/collectionindex/c","InvestManager/collectionindex/z","InvestManager/firsttenderindex","autoInvest/autoInvestMain","curAccountInterest","myAccount");

	setMenuCss(bidStrAry,3);
	
	var tenderStrAry = new Array("repayment/enterRepayMent","myaccount/toTendering","myaccount/borrowList");
	setMenuCss(tenderStrAry,4);
	
	var accountStrAry = new Array("/AccountSafetyCentre/","bankinfo");
	setMenuCss(accountStrAry,5);
	
	var accountStrAry2 = new Array("account/approve");
	setMenuCss(accountStrAry2,5);
	
	var marketStrAry = new Array("lott_use_record","friend/friendList", "friend/inviteRank","account/sycee");
	setMenuCss(marketStrAry,6);
	
	var stockStrAry = new Array("stock/myStock");
	setMenuCss(stockStrAry,7);
	
	var otransferAry = new Array("zhaiquan.html","zhaiquan/totransfercontainer.html","zhitongche/zhuanrang.html","zhitongche/zhuanrang/tocontainer.html");
	setMenuCss(otransferAry,8);
	
	
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
	
	var n = $(obj).children('ul').css("display");
	if(n == 'block'){
		$("#menuul").children('li').children('ul').css("display","none");
		$(obj).children('ul').css("display","none");
		
	}else{
		$("#menuul").children('li').children('ul').css("display","none");
		$(obj).children('ul').css("display","block");
	}
	
	/* $(obj).children('ul').css("display","block");
	$(obj).siblings().children('ul').css("display","none");
	$(obj).children('ul').find('a').removeAttr("style"); */
}
</script>

