<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/page/common/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ include file="/WEB-INF/page/common/public.jsp"%>
<title>我的账户</title>
</head>

<body style="background: #f9f9f9;">
	<!--头部开始-->
	<!--头部结束-->
	<table width="700" border="1" style="margin-left: 20px; margin-top: 10px; text-align: center;">
		<tr style="line-height: 25px">
			<td colspan="4"><b>会员级别制度说明</b></td>
		</tr>
		<tr style="line-height: 25px">
			<td width="20%" bgcolor="#ededed">会员级别</td>
			<td width="20%" bgcolor="#ededed">标志</td>
			<td width="40%" bgcolor="#ededed">加权待收区间</td>
		</tr>
		<tr style="line-height: 25px">
			<td>普通会员</td>
			<td><img src="${basePath}/images/vip_0.gif" width="16" height="16" /></td>
			<td>加权待收<2W</td>
		</tr>
		<tr style="line-height: 25px">
			<td>金牌会员</td>
			<td><img src="${basePath}/images/vip_10.gif" width="16" height="16" /></td>
			<td>加权待收>=2W</td>
		</tr>
		<tr style="line-height: 25px">
			<td>白金会员</td>
			<td><img src="${basePath}/images/vip_20.gif" width="16" height="16" /></td>
			<td>加权待收>=10W</td>
		</tr>
		<tr style="line-height: 25px">
			<td>钻石会员</td>
			<td><img src="${basePath}/images/vip_30.gif" width="16" height="16" /></td>
			<td>加权待收>=20W</td>
		</tr>
		<tr style="line-height: 25px">
			<td>皇冠会员</td>
			<td><img src="${basePath}/images/vip_40.gif" width="16" height="16" /></td>
			<td>加权待收>=50W</td>
		</tr>
		<tr style="line-height: 25px">
			<td>金皇冠会员</td>
			<td><img src="${basePath}/images/vip_50.gif" width="16" height="16" /></td>
			<td>加权待收>=100W</td>
		</tr>
		<tr style="line-height: 25px">
			<td>终身顶级会员</td>
			<td><img src="${basePath}/images/vip_60.gif" width="16" height="16" /></td>
			<td></td>
		</tr>
	</table>
	<div class="user_help" style="margin-left: 20px; width: 700px;padding: 10px 0px;color: red;">
		当前加权待收：<a title="点击查看明细" class="blue" href = "javascript:showCollectionDetail('${basePath}/myaccount/report/showCollectionDetail.html')"><fmt:formatNumber value="${mapCapitalInfo.dayInterst}" pattern="￥#,##0.00"/></a>
	</div>
	<div class="user_help" style="margin-left: 20px; width: 700px;">
		说明：<br /> 1、以加权待收作为用户等级的评价标准；<br />
		2、加权待收的计算公式为：加权待收=自注册之日起待收总额的累加/180；<br /> 
		3、会员等级制度开始实施的时间为2014年3月25日；<br />
		4、终身顶级会员将享受特权，免提现手续费、免债权转让手续费，同时自动投标排名按照获取终身会员等级的时间顺序固定（假设您第一个获得终身顶级会员，那么您的自动投标排名固定为第一位）；<br />
	</div>
	<br/>	
<script type="text/javascript">
function showCollectionDetail(url){
	$.layer({
		type : 2,
		fix : false,
		shade : [0.6, '#E3E3E3' , true],
		shadeClose : true,
		border : [10 , 0.7 , '#272822', true],
		title : ['',false],
		offset : ['50px',''],
		area : ['650px','360px'],
		iframe : {src : url},
		close : function(index){
			layer.close(index);
		}
	});
}
</script>
</body>
</html>