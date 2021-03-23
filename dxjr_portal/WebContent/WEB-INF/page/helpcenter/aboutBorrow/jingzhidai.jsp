<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/page/common/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
 <title>净值贷_顶玺金融净值贷-顶玺金融官网</title>
	<meta name="keywords" content="净值贷" />
	<meta name="description" content="顶玺金融净值贷是为出借用户资金周转方便而打造的一款特殊的借款产品,如果你想了解净值贷或者想了解更多理财产品信息，详情请登陆www.dxjr.com。">
<%@ include file="/WEB-INF/page/common/public.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link href="${basePath}/css/help.css" rel="stylesheet" type="text/css" />
</head>
<body>
	<%@ include file="/WEB-INF/page/common/header.jsp"%>
	<div id="Container">
		<div id="Bmain">
			<div class="title">
				<span class="home"><a href="${path}/">顶玺金融</a></span> <span><a
					href="${path }/bangzhu.html">帮助中心</a></span> <span>关于借款</span>
			</div>
			<div id="menu_centert">
				<%@ include file="/WEB-INF/page/helpcenter/leftMenu.jsp"%>
				<div id="helpctr">
   <div class="content1" style="display: block;">     
                                         <div class="help-content help-inline">
                                           <table border="0">
                                           <tr>
                                           <th style="background:#fd8b4d" width="25%"><h1>净值贷</h1></th>
                                           <th style="background:#fd8b4d"></th>            
                                           </tr>  
                                           <tr style="background:#e1e4e9">
                                           <td>产品介绍</td>    
                                           <td><p>净值贷是顶玺金融为出借用户资金周转方便而打造的一款特殊的借款产品</td>
                                           </tr>
                                          <tr style="background:#fafbfc">
                                           <td>申请条件</td>
                                           <td><p>2015年3月1日之前注册成为顶玺金融的出借用户</p></td>
                                           </tr>
                                           <tr style="background:#e1e4e9">
                                           <td>借款年利率</td>
                                           <td><p>6%-24%</p>
                                           </td>
                                           </tr>
                                          <tr style="background:#fafbfc">
                                           <td>借款期限 </td>
                                           <td><p>1个月</p></td>
                                           </tr>
                                           <tr style="background:#e1e4e9">
                                           <td>还款方式</td>
                                           <td><p>等额本息，按天还款</p></td>
                                           </tr>
                                            <tr>
                                           <td colspan="2"> 
                                           <div class="gg_btn">
                                           <input type="button" value="立即申请"  style="cursor: pointer;" onclick="window.location.href='${path }/rongzi/initApply.html?viewType=help';"/>
											</div>
											</td>
                                           </tr>
                                        </table>
                                        </div>                         
         
      </div>  
</div>
	
			</div>
		</div>
	</div>
	<div class="clearfix"></div>
	<%@ include file="/WEB-INF/page/common/footer.jsp"%>
<script type="text/javascript">
// changeMenu2(6,0,0);
/**
 * 后台验证返回信息
 */
if(''!='${msg}'){
	layer.alert('${msg}');
	//if(''!='${scrollToHeight}'){window.scrollTo(0,'${scrollToHeight}');}
}
</script>
</body>
</html>