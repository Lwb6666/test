<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/page/common/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ include file="/WEB-INF/page/common/public.jsp"%>
<link href="${basePath}/css/help.css?version=<%=version%>" rel="stylesheet" type="text/css" />
<title>我的账户-有奖活动_我的元宝_顶玺金融</title>
<style>
.yb-hover{}
.yb-hover:hover .yb-shuoming{ display:block;}
.yb-shuoming{ border-radius:5px; display:none;   background:url(${path}/images/yb-shuomingbg.png) no-repeat ; width:98%;  height:70px; line-height:17px;  padding:18px 15px 10px 10px; position:absolute; top:90px; font-size:12px; }
</style>
</head>

<body>
<%@ include file="/WEB-INF/page/common/header.jsp"%>
<div id="Container">
	<div id="Bmain">
		<div class="title">
			<span class="home"><a href="${path}/">顶玺金融</a></span> 
			<span><a href="${path }/myaccount/toIndex.html">我的账户</a></span> 
			<span>有奖活动</span> 
			<span>我的元宝</span>
		</div>
		<div id="menu_centert">
			<%@ include file="/WEB-INF/page/account/myAccount_leftMenu.jsp"%>

			<div id="helpctr">
				<div class="yuanbaobox">
					<ul>
						<li style="position:relative;padding-left:1%;">
							<div>
								<em><fmt:formatNumber value="${sycee }" pattern="###,###" /></em><span style="color: #999;">元宝</span>
							</div>
							<div class="yb-hover">
								<span>我的工资</span> 
								<a href="#" style="font-size: 16px;"><img src="${path}/images/hover1.png" /></a>
								<span class="yb-shuoming" style="font-size:12px;">工资以元宝形式发放。元宝由平台待收元宝、论坛活动元宝和抽奖元宝等组成，可用来在元宝商城兑换商品。</span>
							</div> 
						</li>
						<li>
							<div>
								<em><fmt:formatNumber value="${lastdaySycee }" pattern="###,###" /></em><span style="color: #999;">元宝</span>
							</div>
							<div class="yb-hover">
								<span>昨日工资</span> 
							</div>
						</li>
						<li>
							<div>
								<em><fmt:formatNumber value="${honor }" pattern="###,###" /></em><span> </span>
							</div>
							<div class="yb-hover">
								<span>我的荣誉值</span> </a>
							</div>
						</li>
						<div class="clear"></div>
					</ul>
					<div>
						<div class="title-yb">
							<span>
								<em>元宝记录</em>
							 	<c:if test="${ul.o_userLevel>0 }">* 您是尊贵的${ul.levelName }，享有${ul.o_sycee_ratio }倍待收元宝收入特权！</c:if>
							 </span> 
							<a href="${path }/bangzhu/26.html" style="color: #00a7e5;">如何获得更多元宝？</a>
						</div>
						<div class="yb-table">
							<div id="syceeDiv"><%@ include file="/WEB-INF/page/account/sycee/syceeList.jsp"%></div>
							<div class="wxts">
								<p style="font-size: 16px; color: #333;">元宝规则</p>
								<p>1. 元宝可从论坛活动、平台待收和抽奖等不同渠道获得，荣誉值是历史元宝的累计</p>
								<p>2. 平台待收元宝计算公式为：基础元宝×会员倍率，基础元宝=当天23:00前待收总额/1000</p>
								<p>3. 如有任何疑问，请点此<a href="${path }/bangzhu/26.html" style="color: #00a7e5;">查看详细规则</a>或致电客服热线<span style="color: red;">400-000-0000</span></p>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>
<div class="clearfix"></div>
<%@ include file="/WEB-INF/page/common/footer.jsp"%>

<script type="text/javascript">
findPage(1);
</script>
</body>
</html>

	