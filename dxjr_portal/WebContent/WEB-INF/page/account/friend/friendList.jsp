<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%> 
<%@ include file="/WEB-INF/page/common/taglib.jsp"%> 
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ include file="/WEB-INF/page/common/public.jsp"%>
<title>我要推广_顶玺金融</title>
</head>
<body>
<div >
<%@ include file="/WEB-INF/page/common/header.jsp"%>
<div>
	<div id="Bmain">
		<div class="title">
			<span class="home"><a href="${path}/">顶玺金融</a></span>
			<span><a href="${path }/myaccount/toIndex.html">我的账户</a></span>
			<span>市场活动</span>
			<span>推荐人数</span>
		</div>
		<div id="menu_centert">
			<%@ include file="/WEB-INF/page/account/myAccount_leftMenu.jsp"%>
			<div class="lb_waikuang whitebg border">
				<ul class="menu_up">
					<li class="one">推荐用户数：${page.totalCount}个</li>
					<!--  <li class="two">获得赠送金额：<fmt:formatNumber value="${empty sumMoney ? 0 : sumMoney}" pattern="#,##0.00"/>元</li>-->
				</ul>

				<div class="copy">
					<div class="copy_textarea" style="padding-right: 10px;">
						<textarea id="copyText" name="copyText" cols="65" rows="3" class="textarea" readonly="readonly">${path}${recommendPath}</textarea>
					</div>
					<div class="copy_button">
						<input id="user_buttom" type="button" value="复制代码" onclick="copyToClipboard('${path}${recommendPath}')" style="cursor: pointer;"/>
					</div>
				</div>

				<div class="title-items">
					<h2>
						<b>推广记录</b>
					</h2>
					<b class="line"></b>
				</div>
				<div class="myid noborder whitebg" id="bodyDiv">
					<%@ include file="/WEB-INF/page/account/friend/listDiv.jsp"%>
				</div>
			</div>
		</div>
	</div>
</div>
<%@ include file="/WEB-INF/page/common/footer.jsp"%>
</div>
<script type="text/javascript">
function copyToClipboard(txt) {   
	if(window.clipboardData == undefined){
		layer.alert('您的浏览器不支持，请手动复制。');
		return;
	}
	if(window.clipboardData) {   
        window.clipboardData.clearData();   
        window.clipboardData.setData("Text", txt);   
        layer.msg("复制成功！",1,1); 
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
	     layer.msg("复制成功！",1,1);
	}      
}
</script>
</body>
</html>