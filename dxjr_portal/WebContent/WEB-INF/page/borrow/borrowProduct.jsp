<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%> 
<%@ include file="/WEB-INF/page/common/taglib.jsp"%> 
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>P2P小额贷款_个人融资_我要融资-顶玺金融官网</title>
<meta name="keywords" content="个人融资, P2P小额贷款,我要融资" />
<meta name="description" content="顶玺金融是中国3A信用评级互联网理财借贷平台，如果个人或中小企业需要融资，可登陆顶玺金融官网了解详情www.dxjr.com。">
<%@ include file="/WEB-INF/page/common/public.jsp"%>
</head>
<body>
<%@ include file="/WEB-INF/page/common/header.jsp"%>
<!--内容开始-->
<div id="Container">
	<div id="Bmain">
		<div class="title">
			<span class="home"><a href="${path}/">顶玺金融</a></span>
			<span><a href="javascript:void(0);"> 我要融资</a></span>
			<span>融资产品</span>
		</div>
		<div class="tbmain">
			<div class="rzbox model-box">
				<div class="rzhead tbgreen">
					<div class="tbhead_h2">诚薪贷</div>
					<div class="rzcontent">
						<p>诚薪贷是顶玺金融为工薪阶层个人量身打造的一款借款产品。满足您个人消费需求，提高生活品质。</p>
						<p>
							<span class="green">申请条件：</span>通过顶玺金融个人信用审评
						</p>
					</div>
					<div class="rzcontent rzbolck">
						<p>预期收益:</p>
						<p class="rzincome">
							<em>14-16</em>%
						</p>
					</div>
					<div class="rzcontent rzbolck">
						<p>借款期限:</p>
						<p class="rzincome" style="border-right: none">
							<em>1-12</em>个月
						</p>
					</div>
					<div class="rzcontent">
						<p>还款方式：等额本息，到期还本付息，按月付息到期还本，按天还款。</p>
						<p style="text-align: center;">
							<a href="${path }/bangzhu/12.html#listId=1"  target="_blank">了解详细信息>></a>
						</p>
					</div>
					<div class="rzcontent">
						<input class="tbbid tbwidth" type="button" value="去借款" style="cursor: pointer;"
							onclick="javascript:window.location='${path }/salariatBorrow/initApply.html?viewType=product'" />
					</div>
				</div>
			</div>
			<div class="rzbox model-box">
				<div class="rzhead tbblue">
					<div class="tbhead_h2">诚商贷</div>
					<div class="rzcontent">
						<p>诚商贷是顶玺金融为企业量身打造的一款借款产品。满足您企业的发展需求。</p>
						<p>
							<span class="green">申请条件：</span>通过顶玺金融企业信用审评
						</p>
						<br />
					</div>
					<br />
					<div class="rzcontent rzbolck">
						<p>预期收益:</p>
						<p class="rzincome">
							<em>14-16</em>%
						</p>
					</div>
					<div class="rzcontent rzbolck">
						<p>借款期限:</p>
						<p class="rzincome" style="border-right: none">
							<em>1-12</em>个月
						</p>
					</div>
					<div class="rzcontent">
						<p>还款方式：等额本息，到期还本付息，按月付息到期还本，按天还款。</p>
						<p style="text-align: center;">
							<a href="${path }/bangzhu/12.html#listId=2" target="_blank">了解详细信息>></a>
						</p>
					</div>
					<div class="rzcontent">
						<input class="tbbid tbwidth" type="button" value="去借款" style="cursor: pointer;" 
							onclick="javascript:window.location='${path }/commerceBorrow/initApply.html?viewType=product'" />
					</div>
				</div>
			</div>
			<div class="rzbox model-box">
				<div class="rzhead">
					<div class="tbhead_h2">净值贷</div>
					<div class="rzcontent">
						<p>净值贷是顶玺金融为出借者资金周转方便而打造的一款特殊的借款产品。以出借者在顶玺金融的投资待收为担保，满足出借者个人的资金需求。</p>
						<p>
							<span class="green">申请条件：</span>2015年3月1日之前注册成为顶玺金融的出借者
						</p>
					</div>
					<div class="rzcontent rzbolck"  style="margin: 0px">
						<p>预期收益:</p>
						<p class="rzincome">
							<em>6-24</em>%
						</p>
					</div>
					<div class="rzcontent rzbolck"  style="margin: 0px" >
						<p>借款期限:</p>
						<p class="rzincome" style="border-right: none">
							<em>1</em>个月
						</p>
					</div>
					<div class="rzcontent">
						<p>还款方式：等额本息，按天还款。</p>
						<p style="text-align: center;">
							<a href="${path }/bangzhu/12.html#listId=3" target="_blank">了解详细信息>></a>
						</p>
					</div>
					<div class="rzcontent"  style="margin: 0px">
						<input class="tbbid tbwidth" type="button" value="去借款" style="cursor: pointer;"
							onclick="javasript:window.location.href='${path }/rongzi/initApply.html?viewType=product'" />
					</div>
				</div>
			</div>
		</div>
	</div>
</div>
<!--内容结束-->
<%@ include file="/WEB-INF/page/common/footer.jsp"%>

<script type="text/javascript">
/**
 * 后台验证返回信息
 */
window.onload=function(){
	if(''!='${msg}'){
		layer.msg('${msg}', 2, 5,function(){
			var alertMsg = '${msg}';
			if("还未进行实名认证！"==alertMsg){
				window.location.href="${path}/AccountSafetyCentre/safetyIndex.html";
		    }else if("还未进行邮箱认证！"==alertMsg){
				window.location.href="${path}/AccountSafetyCentre/safetyIndex.html";
			}else if("还未进行手机认证！"==alertMsg){
				window.location.href="${path}/AccountSafetyCentre/safetyIndex.html";
			}
		});
	}
}
</script>
</body>
</html>