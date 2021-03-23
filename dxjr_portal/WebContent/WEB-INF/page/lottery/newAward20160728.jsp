<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/page/common/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>新人大奖</title>
<link href="${path}/css/award/style.css" rel="stylesheet"
	type="text/css" />
<link href="${basePath}/css/award/newp/style.css" rel="stylesheet"
	type="text/css" />
<link href="${basePath}/css/award/newp/layout.css" rel="stylesheet"
	type="text/css" />
</head>
<body>
<style type="text/css">
	  @media only screen and (min-width: 321px) and (max-width: 1024px){ 
          .contnew,.ft{ width:1300px; } 
      } 
</style>
	<div class="contnew">
		<div class="main">
			<div class="btnbg2">
				<a href="${path}/member/toRegisterPage.html" class="white-c"><button class="btn01" type="button" title="立即注册"></button></a>
			</div>
			<div>
				<img src="${path}/images/img_bar_new.png" />
			</div>
			<div class="btnbg">
				<c:if test="${user!=null }">
				  <a href="${path}/dingqibao.html" class="white-c"><button class="btn02" type="button" title="立即投资"></button></a>
				</c:if>
				<c:if test="${user==null }">
				  <a href="${path}/member/toLoginPage.html" class="white-c"><button class="btn02" type="button" title="请新去登陆"></button></a>
				</c:if>
			</div>
		</div>
			<div class="main-new-bg">
			<div class="main-new2">
				<div class="pd">
					<div class="f28 white-c">
						【新人专享宝】<em class="ml20 mr10"><img
							src="${path}/images/newp/images_19.png" /></em><img
							src="${path}/images/newp/images_21.png" /> <span
							class="ml20 f24">新用户均有一次投资机会，限额10,000元</span>
					</div>
					<div class="mt30 white-c main-show">
						<table>
							<tr>
								<td style="  position:relative; "><em class="f36"
									style=" color:#fff;  ">
									<c:if test="${!empty fixBorrowNew}">
	                    				${fixBorrowNew.apr}%
	                    			</c:if>
	                    			<c:if test="${empty fixBorrowNew}">
	                    				15%
	                    			</c:if>
									</em></td>
								<td><em class="f36">10000.00元</em></td>
								<td><em class="f36">
								<c:if test="${!empty fixBorrowNew}">
                    				${fixBorrowNew.lockLimit}
                    			</c:if>
                    			<c:if test="${empty fixBorrowNew}">
                    				1
                    			</c:if>
								个月</em></td>
							</tr>
							<tr>
								<td><em class="f24">预期利率</em></td>
								<td><em class="f24">金额</em></td>
								<td><em class="f24">期限</em></td>
							</tr>
						</table>
					</div>
					<div class="mt50">
						<div class="progress">
							<p class="rl">
								<span class="bar" style="width:${scheduleStr}%;"></span>
							</p>
							<p class="tc white-c f20 mt20">进度${scheduleStr}%</p>
						</div>
					</div>
					<div class="mt50">
						<c:if test="${fixBorrowNew.status==3 and fixBorrowNew.flagJoin==2}">
                    		<div class="tb-btn2 mg f24"
								style="background:#bababa;cursor: pointer;">
								<a href="javascript:void(0);">预发中</a>
							</div>
                    	</c:if>                    	
						<c:if test="${fixBorrowNew.status==3 and fixBorrowNew.flagJoin==1}">
                   			<div class="tb-btn mg f24">
								<a href="javascript:openDingbao('${fixBorrowNew.id}');">投宝</a>
							</div>
                   		</c:if>                   	
						<c:if test="${fixBorrowNew.status >= 5 }">
							<div class="tb-btn2 mg f24">
								<a href="javascript:void(0);">已完成</a>
							</div>
						</c:if>
						<c:if test="${empty fixBorrowNew}">
                 			预发中
                 		</c:if>
					</div>
				</div>
			</div>
		</div>
	</div>
	<div class="ft"><img src="${path}/images/ft-cj_02.jpg"/></div>
</body>
<script type="text/javascript">
		//新手专区-投标		
		function openDingbao(borrowId){
			window.open("${path}/dingqibao/"+borrowId+".html"); 
		}
</script>
</html>