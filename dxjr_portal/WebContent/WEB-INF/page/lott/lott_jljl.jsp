<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/page/common/taglib.jsp"%>
<script type="text/javascript">
	$(function(){
		$('#tip-bottom2').poshytip({
			className: 'tip-yellowsimple',
			showTimeout: 1,
			alignTo: 'target',
			alignX: 'center',
			alignY: 'bottom',
			offsetX: 15,
			offsetY: 10,
			allowTipHover: false,
		});
		$(".close").click(function(){
		$(".bankcard-tip").fadeOut();
		});
		$("#copeUrl").html('${basePath}${recommendPath}');
	});
     function findPage(pageNo) {
    	var redId=$("#redId").val();
    	var isRecommendSuccess=$("#isRecommendSuccess").val();
	    window.parent.lott_jljl_pageParent(pageNo,isRecommendSuccess,redId);
	 }
     function copyToClipboard(txt) {   
    		if(window.clipboardData == undefined){
    			$("#copetext").html('您的浏览器不支持，请手动复制！');
    			return;
    		}
    		if(window.clipboardData) {   
    	        window.clipboardData.clearData();   
    	        window.clipboardData.setData("Text", txt);  
    	        $("#copetext").html('复制链接成功！快去推荐给朋友吧~~');
    		} else if(navigator.userAgent.indexOf("Opera") != -1) {   
    		     window.location = txt;   
    		} else if (window.netscape) {  
    		     try {   
    		          netscape.security.PrivilegeManager.enablePrivilege("UniversalXPConnect");   
    		     } catch (e) {   
    		    	 layer.alert("被浏览器拒绝！\n请在浏览器地址栏输入'about:config'并回车\n然后将'signed.applets.codebase_principal_support'设置为'true'");   
    		     }   
    		     var clip = Components.classes['@mozilla.org/widget/clipboard;1'].createInstance(Components.interfaces.nsIClipboard);   
    		     if (!clip)   
    		          return;   
    		     var trans = Components.classes['@mozilla.org/widget/transferable;1'].createInstance(Components.interfaces.nsITransferable);   
    		     if (!trans)   
    		          return;   
    		     trans.addDataFlavor('text/unicode');   
    		     var str = Components.classes["@mozilla.org/supports-string;1"].createInstance(Components.interfaces.nsISupportsString);   
    		     var copytext = txt;   
    		     str.data = copytext;   
    		     trans.setTransferData("text/unicode",str,copytext.length*2);   
    		     var clipid = Components.interfaces.nsIClipboard;   
    		     if (!clip)   
    		          return false;   
    		     clip.setData(trans,null,clipid.kGlobalClipboard);  
    		     $("#copetext").html('复制链接成功！快去推荐给朋友吧~~');
    		}      
    	}
</script>
</head>
<div class="reward change" style="clear: both">
	<div class="tbl-cont">
		<div class="product-wrap">
			<div class="grid-1100">
				<div class="product-deatil clearfix ">
					<ul class="topcol4">
						<li><span class="f20 orange">${inviterMember}</span><span
							class="f14 orange"> 人</span><br>推荐用户数</li>
						<li><span class="f20 orange">${inviterSuccess}</span><span
							class="f14 orange"> 人</span><br>推荐成功人数</li>
						<li><span class="f20 orange"><fmt:formatNumber value="${inviterReward}" pattern="#,##0.00"/></span><span
							class="f14 orange"> 元</span><br>推荐奖励总额 <i
							class="iconfont yellow" id="tip-bottom2"
							title="红包：<fmt:formatNumber value="${inviterRedReward}" pattern="#,##0.00"/>元<br>现金：<fmt:formatNumber value="${inviterReward-inviterRedReward}" pattern="#,##0.00"/>元">&#xe608;</i></li>
						<li><span class="f20 orange"><fmt:formatNumber value="${inviterMoney}" pattern="#,##0.00"/></span><span
							class="f14 orange"> 元</span><br>分享收益奖</li>
					</ul>
				</div>
	            <div class="product-deatil">
                       <h1><span>邀请好友</span> <a href="${basePath}/activity/invited.html" target="_blank" style="color:red;">有钱大家赚 最高奖励150现金</a></h1>
                         <ul class="share bdsharebuttonbox">
                           <li> <a title="复制链接" href="javascript:void(0);" onclick="copyToClipboard('${basePath}${recommendPath}');" data-reveal-id="div-copyok" data-animation="fade" class="bds_copy iconfont f36">&#xe617;</a>复制链接</li>
                           <li> <a title="分享到新浪微博"  class="bds_tsina iconfont f36" data-cmd="tsina" >&#xe618;</a>新浪微博 </li>
                           <li> <a title="分享到微信"  class="bds_weixin iconfont f36" data-cmd="weixin" >&#xe616;</a>微信好友 </li>
                           <li> <a title="分享到QQ好友"  class="bds_sqq iconfont f36" data-cmd="sqq" >&#xe614;</a>QQ好友 </li>
                           <li> <a title="分享到QQ空间"  class="bds_qzone iconfont f36" data-cmd="qzone" >&#xe619;</a>QQ空间 </li>
                           <li> <a title="分享到腾讯微博"  class="bds_tqq iconfont f36" data-cmd="tqq" >&#xe615;</a>腾讯微博 </li>
                        </ul>
                  </div>
				<div class="tz-dqb2 mt20 clearfix"
					style="border-top: 20px solid #ecf0f1;">
					<div class="col clearfix">
						<span class="fl gary2">推荐状态：</span>
						<div class="btn-box-bg">
						    <input type="hidden" id="redId" value="${redId}">
						    <input type="hidden" id="isRecommendSuccess" value="${isRecommendSuccess}">
							<div class="type" id="InviterSuccess">
								<a href="javascript:queryInviter(1,${redId});">推荐成功</a>
							</div>
							<div class="type" id="InviterFail">
							    <a href="javascript:queryInviter(2,${redId});" >推荐未成功</a>
							</div>
						</div>
					</div>
				</div>
				<table class="table tbtr03 center">
					<thead>
						<tr>
							<td>被推荐人</td>
							<td>注册时间</td>
							<td>实名认证</td>
							<td>手机认证</td>
							<td>获得奖励</td>
						<!-- 	<td>分享收益</td> -->
							<td>推荐成功时间</td>
						</tr>
					</thead>
					<tbody>
					   <c:if test="${page.totalPage>0}">
						<c:forEach items="${page.result}" var="i" varStatus="status">
							<tr>  
								<td><a href="${path}/accountdetail/show.html?userId=${i.invitedUserid}"  target="_blank">${i.invitedUsername}</a></td>
								<td><fmt:formatDate value="${i.registerTime}" pattern="yyyy-MM-dd" /></td>
								<td>
									<c:if test="${i.realnamePassed == 1}">
									    <i class="iconfont green f24">&#xe61b;</i>
									</c:if>
									<c:if test="${i.realnamePassed != 1}">
									    <i class="iconfont red f24">&#xe61a;</i>
									</c:if>
								</td>
								<td>
								    <c:if test="${i.mobilePassed == 1}">
									    <i class="iconfont green f24">&#xe61b;</i>
									</c:if>
									<c:if test="${i.mobilePassed != 1}">
									    <i class="iconfont red f24">&#xe61a;</i>
									</c:if>
								</td>
                                <td>
								<!-- 上线之前的赠送现金仍按原有显示，上线之后才有的则显示红包金额 -->
								 <c:if test="${i.isViewCash=='1'}">
								  <c:if test="${i.awardMoney!=null}">
								    <fmt:formatNumber value="${i.awardMoney}" pattern="#,##"/>元红包
								   </c:if>
								 </c:if>
								  <c:if test="${i.isViewCash=='0'}">
								    ${i.awardMoney}
								 </c:if>
								</td>
							<%-- 	<td>${i.rewardMonry}</td> --%>
								<td><fmt:formatDate value="${i.inviteSuccessTime}" pattern="yyyy-MM-dd" /></td>
							</tr>	
						</c:forEach>
						</c:if>
					</tbody>
				</table>
<c:if test="${page.result==null  || page.totalCount==0 }">
	<div align="center"  style="height: 70px;line-height: 70px">暂无相关信息</div>
</c:if>
<c:if test="${page.result !=null &&  page.totalCount >0 }">
	<div>
		<jsp:include page="/WEB-INF/page/common/ajaxpage.jsp">
			<jsp:param name="pageNo" value="${page.pageNo}" />
			<jsp:param name="totalPage" value="${page.totalPage}" />
			<jsp:param name="hasPre" value="${page.hasPre}" />
			<jsp:param name="prePage" value="${page.prePage}" />
			<jsp:param name="hasNext" value="${page.hasNext}" />
			<jsp:param name="nextPage" value="${page.nextPage}" />
		</jsp:include>
	</div>
</c:if>
			</div>
		</div>
	</div>
</div>
<script>window._bd_share_config = {"common": {"bdSnsKey": {}, "bdText": "${basePath}${recommendPath}", "bdMini": "2", "bdMiniList": false, "bdPic": "http://www.internetke.com/uploads/allimg/131026/1-1310261J0270-L.jpg", "bdStyle": "0", "bdSize": "24"}, "share": {}};with (document) 0[(getElementsByTagName('head')[0] || body).appendChild(createElement('script')).src = 'http://bdimg.share.baidu.com/static/api/js/share.js?v=89860593.js?cdnversion='+ ~(-new Date() / 36e5)];</script>
<script>
$(document).ready(function(){ 
    var color="#f0f7ff"
    $(".tbtr tr:even,.tbtr01 tr:even,.tbtr02 tr:even,.tbtr03 tr:even,.tbtr04 tr:even,.tbtr05 tr:even,.tbtr06 tr:even,.tbtr07 tr:even,.tbtr08 tr:even,.tbtr09 tr:even,.tbtr10 tr:even,.tbtr11 tr:even,.tbtr12 tr:even,.tbtr13 tr:even,.tbtr14 tr:even,.tbtr15 tr:even,.tbtr16 tr:even,.tbtr17 tr:even").attr("bgcolor",color);//改变奇数行背景色
})	
</script>