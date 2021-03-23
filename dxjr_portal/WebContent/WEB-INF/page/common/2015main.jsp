<%@page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/page/common/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ include file="/WEB-INF/page/common/public.jsp"%>
<%-- 和footer页面  
<link href="${basePath}/css/float.css?version=<%=version%>" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${basePath}/js/main/jquery.event.drag-1.5.min.js"></script>
<script type="text/javascript" src="${basePath}/js/main/jquery.touchSlider.js"></script>
<script type="text/javascript" src="${basePath}/js/commonutils.js"></script>
--%>
<link href="${basePath}/css/circle.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${basePath}/js/init.js"></script>
<script type="text/javascript" src="${basePath}/js/raphael.js"></script>
<style type="text/css">
.countdown-banner{ position:absolute; top:0; left:0; z-index:9999; background:url(${basePath}/images/back333.png); width:100%; height:100%; margin-bottom:-1300px; padding-bottom:1300px;  padding-top:3%; position:fixed;   }
.countdownimg{ display:block; margin:0 auto; /*  background:url(images/indeximg.png) no-repeat; */    padding-left:10%; position:relative; }
.countdownimg img{ width:95%; text-align:center;}
.closbox{     position:absolute;cursor:pointer; right:60px;*right:140px; top:25px;display:block;  /*background:url(images/ie7.png)\9; background:url(images/ie7.png)\0;   *background:url(images/ie7.png);*/       }
.closbox img{ *width:4%}
.canyu{ display:block; position:absolute;width: 306px; height:60px;  bottom: 70px; *bottom: 60px;
left: 435px;  *left:390px;   /* background:url(images/ie7.png)\9;  *background:url(images/ie7.png); border:1px red solid\0; */ }
</style>
<title>顶玺金融-中国最大的房产抵押互联网金融平台</title>
</head>
<body style="background: #fff;">
<!--头部开始-->
<%@ include file="/WEB-INF/page/common/header.jsp" %>
<div class="clear"></div>
<!--头部结束-->
<!-------------------------双旦弹层banner--------------------------->
<c:if test="${newYear==null }">
<div class="countdown-banner" style="display: none;" >
    
    <div style="position:relative; width:1000px; margin:0 auto;">
    	 
        <span class="countdownimg" > 
          <div class="closbox" ><img src="${basePath}/images/XX.png"/> </div>
          <img src="${basePath}/images/indeximg.png"/><a class="canyu" target="_blank" href="${path}/activity/doubleDan.html" >
          <img src="${basePath}/images/tacenbtn.png"/> </a></span>
    </div>
</div> 
</c:if> 
<!----------------------双旦弹层banner------------------------------>
<!--广告-->
        <div class="feat-area">
             <div class="main_visual">
                <div class="flicking_con">
                	<div class="flicking_inner">    
	                	     <!-- <a href="#">1</a>
		                    <a href="#">2</a>
		                    <a href="#">3</a>
		                    <a href="#">4</a> -->
		                         
		                    <c:forEach items="${slideshowVoList}" var="slideshow" varStatus= "idxSlide" >
		                  		<a href="#">${idxSlide.index+1}</a>
		                    </c:forEach> 
	                       
               	 </div>
            </div>
                <div class="main_image">
                  <ul>
                   <%-- <li><a href="${bbsPath}/ext/androidapp.html  "><span class="img_26"></span></a></li>
                  <li><a href="${bbsPath}/posts/67514/1"><span class="img_21"></span></a></li>
                  <li><a href="${bbsPath}/posts/67332/1"><span class="img_23"></span></a></li>
                  <li><a href="${bbsPath}/ext/activityspecial.html  "><span class="img_16"></span></a></li> --%>
                   
                   <c:forEach items="${slideshowVoList}" var="slideshow" varStatus= "idxSlide" >
                  			<li><a href="${empty slideshow.imageUrl?'javascript:;':slideshow.imageUrl}" title="${slideshow.title}" target="_blank" <c:if test="${empty slideshow.imageUrl}">style="cursor: default;"</c:if>>              			
  							<span style="background: url('${slideshow.sImage}') center top no-repeat;"></span>
                  			</a></li>
                  </c:forEach> 
                 
                  </ul>
                  <a href="javascript:;" id="btn_prev"></a> <a href="javascript:;" id="btn_next"></a>
			  </div>
</div>
<div style="text-align:center;clear:both">
</div>
</div>
<!--广告结束-->
<!--list  开始-->
<div class="margin_list" style="text-align:left;">
	<div class="margin_lists">
		<span>成交金额：<i><fmt:formatNumber value="${TotalMoney }" pattern="#,##0"/></i>万元 </span>
		<span>风险备用金总额：<i><fmt:formatNumber value="${RiskMargin }" pattern="#,##0"/></i>万元</span>
        <span>注册人数：<i>${regCount }</i>人</span>
        <span><img title="查看更多" alt="查看更多" src="${basePath}/images/main/tp.png" /><a class="more" href="${path }/chart/finance/show.html" rel="nofollow">查看更多</a></span> 
	
		<div class="demo" style="">
			<nav class="main_nav" style="">
				<ul>
					<li><a class="cd-signin" href="javascript:openVideo();" title="1分钟了解顶玺"><img src="${path}/images/spsmallicon1.png"/></a></li>
			</ul>
			</nav>
		</div>
		<div id="videoInfo"></div> 
	</div>
</div>

<!--list  结束-->
<!--安全保障 开始 -->
<div class="clear"></div>
<div class="pt_anquan">     
<div class="safe_l">  
<a class="safe-link dialog" href="${path }/bangzhu/touzi.html" target="_blank">
     <div class="safe-fig safe-fig-1"></div>
      <div class="safe_title">  轻松理财，不再烦恼</div>
   <div class="safe_txts"> 注册成为出借者，通过实名认证后，投标或加入投标直通车，获取<span class="blues">15%</span>的稳定年化收益。</div>
 </a>
 </div>
<div class="safe_l">  
  <a class="safe-link dialog" href="${path }/bangzhu/jiekuan.html" target="_blank">
       <div class="safe-fig safe-fig-2"></div>
        <div class="safe_title"> 信用借款，急您所急</div>
     <div class="safe_txts">注册成为借款者，完成信用认证，经网上发标，满标复审通过，即可<span class="blues">24小时内</span>获取资金。</div>
   </a>
   </div>
<div class="safe_l">  
<a class="safe-link dialog" href="${path }/anquan.html" target="_blank">
     <div class="safe-fig safe-fig-3"></div>
      <div class="safe_title">安全保障，放心投资</div>
   <div class="safe_txts">出借者在顶玺金融的投资<span class="blues">100%</span>适用于本金保障计划，风险备用金托管于光大银行，有效保障投资者的资金安全。</div>
 </a>
 </div>
                            
 </div>
 
<div class="clear"></div>

<div class="bigbox-as">
	<div class="hot-bao-box">
			<!-- 新手标 -->
			<%-- 
    		<div class="hot-l-box">
            		<div class="new-titile-l"><span class="sm-title-l">每日不定时开放</span></div>
                    <div class="new-hotpic"><img src="${basePath}/images/new-index-dqb.png"/></div>
                    <ul class="content-bigbox" style="   position: relative;">
                    	<li class="pd-li" style="  padding-right:11%;"><p class="shouyi ">
                    	<fmt:formatNumber value="${borrowNew.apr}" pattern="#,##0.#"/><span class="baifenhao">%</span></p><p class="biaoti-bt">年化收益</p></li>
                        <li class="line-left" style="margin-top:10px;"></li>
                    	<li class="pd-yue"><p class="ue-yue f14">期限${borrowNew.timeLimit}个月</p>
	                    	<p class="f16">剩余金额
	                    	<span class="f26 bigje">
	                    		<fmt:formatNumber value="${borrowNew.account/10000}" pattern="#,##0.#"/>万
	                    	</span>
	                    	</p>
                    	</li>
                    	<li class="pd-btn" >
                    	<c:if test="${borrowNew.status==1 or borrowNew.status==2}">
                    	<input class="ljjr-btn-style f16"  type="button" value="立即加入" onclick="goToInvest(${borrowNew.id});"/>
                    	</c:if>
                    	<c:if test="${borrowNew.status > 2}">
                    	<input class="ljjr-btn-style f16" type="button" value="敬请期待" onclick="goToInvest(${borrowNew.id});"/>
                    	</c:if>
                    	</li>
                    </ul>
            </div>
            --%>
            <div class="hot-l-box" >
            		<div class="new-titile-new new-bao"><span class="sm-title-l">每日不定时开放</span></div>
                    <div class="new-hotpic"><img title="新手宝" alt="新手宝" src="${basePath}/images/new-index-dqb.png"/></div>
                    <ul class="content-bigbox" style="position:relative;  ">
                    	<li class="pd-li" style="   position:relative; padding-left:30px; width:165px;  padding-right:15px;">
                    		<p class="shouyi dqb-shouyi  pd-li-zj" style="padding-right:0px;color:#339933;">
                    			<c:if test="${!empty fixBorrowNew}">
                    				<fmt:formatNumber value="${fixBorrowNew.apr}" pattern="#,##0.#"/><span class="baifenhao" >%</span>
                    			</c:if>
                    			<c:if test="${empty fixBorrowNew}">
                    				<fmt:formatNumber value="15" pattern="#,##0.#"/><span class="baifenhao" >%</span>
                    			</c:if>
                    		</p>
                    		<p class="biaoti-bt">年化收益</p>
                    	</li>
                        <li class="line-left" style="margin-top:10px;"></li>
                        <c:if test="${!empty fixBorrowNew }">
                			<li class="pd-yue" style=" "><p class="f16" style="margin-bottom:10px; ">剩余金额<span class="f18 " style="padding:0 0  0 10px;   ">￥<fmt:formatNumber value="${fixBorrowNew.planAccount-fixBorrowNew.accountYes }" pattern="#,##0.##" /></span> </p><p class="ue-yue f14">期限 ${fixBorrowNew.lockLimit} 个月</p></li>
                		</c:if>
                		<c:if test="${empty fixBorrowNew }">
                			<li class="pd-yue" style=" "><p class="f16" style="margin-bottom:10px; ">剩余金额<span class="f18 " style="padding:0 0  0 10px;   ">￥0</span> </p><p class="ue-yue f14">期限 1 个月</p></li>
                		</c:if>                    	
                    	<li class="pd-btn" style="position:absolute; right:30px; top:10px;" >
                    		<c:if test="${fixBorrowNew.status==3 and fixBorrowNew.flagJoin==1}">
                    			<input class="jqqd-btn-a f16" type="button" value="立即加入" onclick="openDingbao('${fixBorrowNew.id}');"/>
                    		</c:if>
                    		<c:if test="${fixBorrowNew.status==3 and fixBorrowNew.flagJoin==2}">
	                    		<input class="ljjr-btn-style f16" type="button" value="敬请期待" onclick="openDingbao('${fixBorrowNew.id}');"/>
	                    	</c:if>
	                    	<c:if test="${fixBorrowNew.status==5}">
	                    		<input class="jqqd-btn-d f16" type="button" value="收益中" onclick="openDingbao('${fixBorrowNew.id}');"/>
	                    	</c:if>
	                    	<c:if test="${empty fixBorrowNew}">
	                    		<input class="ljjr-btn-style f16" type="button" value="敬请期待"/>
	                    	</c:if>
                    	</li>
                    </ul>
            </div>
            
            
            <!-- 活期宝 -->
            <div class="hot-r-box" >
            		<div class="new-titile-r"><span class="sm-title-r">每日复利，随存随取  </span><a style="font-size:14px; padding-left:30px;" href="${bbsPath}/ext/hqbGonglue.html" style="padding-right:30px;">活期宝投资攻略</a></div>
                     <ul class="content-bigbox">
                    	<li class="pd-li "><p class="shouyi-a fontcolor-1" >5.6<span class="baifenhao">%</span></p><p class="biaoti-bt">年化收益</p></li>
                        <li class="line-left" style="margin-top:10px;"></li>
                    	<li class="pd-yue"><p class="f16 " style="padding-top:10px; width:122px;">加入金额<span class="f26 bigje fontcolor-1">1</span>元起</p></li>
                    	<li class="pd-btn-1" ><input class="ljjr-btn-style-a f16" type="button" value="立即加入" onclick="enterCur()"/></li>
                    </ul>
            </div>

			<!-- 定期宝 -->
    		<div class="hot-l-box hot-zj-box" style="border-bottom:none;">
            		<div class="new-titile-l new-titile-zj"><span class="sm-title-l sm-title-zj">收益稳定，定期回款，省时省心
					</span><span class="mr-time f14">每日 09:00，15:00，20:00 定时开放</span><span ><a class="qbdqb" href="${path}/dingqibao.html">全部定期宝</a></span></div>
                    <div class="new-hotpic"><img alt="新手标" src="${basePath}/images/new-index-dqb.png"/></div>
                    
                    <ul class="content-bigbox content-bigbox-a" style="border-bottom:1px #ff9933 solid; position: relative;">
                   	 	<li class="pd-li" style="   position:relative; padding-left:30px; width:130px;">
                   	 	<p class="shouyi dqb-shouyi  pd-li-zj" style="padding-right:0px;">
                   	 	<c:if test="${fixBorrow1Vo.apr>8}">8</c:if><c:if test="${fixBorrow1Vo.apr<=8}"><fmt:formatNumber value="${fixBorrow1Vo.apr}" pattern="#,##0.#"/></c:if><span class="baifenhao">%</span>
                   	 	<c:if test="${fixBorrow1Vo.apr>8}">
                   	 	<span class="bbose"><fmt:formatNumber value="${fixBorrow1Vo.apr-8}" pattern="#,##0.#"/>%</span>
                   	 	</c:if>
                   	 	</p>
                   	 	<p class="biaoti-bt">年化收益</p>
                   	 	</li>
						<li class="pd-yue" style="padding-right:35px;"><p><span class="f20 bigje" style="color:#996633;margin-left:10px;">${fixBorrow1Vo.lockLimit}个月</span></p><p class="ue-yue f14" style="text-align:right;">期限</p></li>
                        <li class="line-left" style="margin-top:10px;"></li>
                        <li style="padding:16px 0 0 30px;">
                        	<p class="f16" style="padding-bottom:20px;">定期宝（${fixBorrow1Vo.contractNo}）</p>
                            <div  class="f16 bar-box">
                         		<div  class="f16" style="float:left; ">当前进度</div>  
                                <div class="rl-a" style=" float:left;"> 
                                <span class="bar-a" style="width:${fixBorrow1Vo.scheduleStrNoDecimal}%"></span></div>
                                <span class="f16" style="padding-left:10px;"><fmt:formatNumber value="${fixBorrow1Vo.scheduleStrNoDecimal}" pattern="#,##0"></fmt:formatNumber>%</span>
                            </div>
                        </li>
                        <li  class="syje" style="text-align:left;">
                        		<div class="f14 time-back1" style="text-indent:20px;">
	                        	<c:if test="${fixBorrow1Vo.statusId=='2'}">
	                        		 <span id="dingqibao1relase"  style="  "></span>  
	                            </c:if>
                        		</div>
                                <div class="f16" style="  ">&nbsp;
                             	 <input id="dingqibao1relaseButton" class="jqqd-btn-a" style="position: absolute;right:3%; margin-top:35px;  ${fixBorrow1Vo.statusId == 1  ?'':'display:none;'}" type="button" value="加入中" onclick="openDingbao('${fixBorrow1Vo.id}');"/>
                                 <input id="dingqibao2relaseButton" class="jqqd-btn-b" style="position: absolute;right:3%; margin-top:35px;  ${fixBorrow1Vo.statusId == 2  ?'':'display:none;'}" type="button" value="敬请期待" onclick="openDingbao('${fixBorrow1Vo.id}');"/>
                                 <input id="dingqibao3relaseButton" class="jqqd-btn-c" style="position: absolute;right:3%; margin-top:35px; ${fixBorrow1Vo.statusId == 3  ?'':'display:none;'}" type="button" value="收益中" onclick="openDingbao('${fixBorrow1Vo.id}');"/>
                                 <input id="dingqibao4relaseButton" class="jqqd-btn-e" style="position: absolute;right:3%; margin-top:35px; ${fixBorrow1Vo.statusId == 4  ?'':'display:none;'}" type="button" value="已退出" onclick="openDingbao('${fixBorrow1Vo.id}');"/>
                                </div>
                                <div style=" font-size:16px; position: absolute;bottom:30px;right:17%; overflow: hidden; float:left;"> 剩余金额<span style="padding:20px 10px 0 10px;">￥<fmt:formatNumber value='${fixBorrow1Vo.planAccount - fixBorrow1Vo.accountYes}' pattern="#,##0.##"></fmt:formatNumber></span></div>
                        </li>
                    </ul>
                    
                    <c:if test="${fixBorrow3Vo != null}">
                     <ul class="content-bigbox content-bigbox-a" style="border-bottom:1px #ff9933 solid;position: relative;">
                    	<li class="pd-li" style="   position:relative; padding-left:30px; width:130px;">
	                    	<p class="shouyi dqb-shouyi  pd-li-zj" style="padding-right:0px;">
	                    		<c:if test="${fixBorrow3Vo.apr>9}">9</c:if><c:if test="${fixBorrow3Vo.apr<=9}"><fmt:formatNumber value="${fixBorrow3Vo.apr}" pattern="#,##0.#"/></c:if><span class="baifenhao">%</span>
		                   	 	<c:if test="${fixBorrow3Vo.apr>9}">
		                   	 		<span class="bbose"><fmt:formatNumber value="${fixBorrow3Vo.apr-9}" pattern="#,##0.#"/>%</span>
		                   	 	</c:if>
	                    	</p>
                    		<p class="biaoti-bt">年化收益</p>
                    	</li>
						<li class="pd-yue" style="padding-right:35px;"><p><span class="f20 bigje" style="color:#996633;margin-left:10px;">${fixBorrow3Vo.lockLimit}个月</span></p><p class="ue-yue f14" style="text-align:right;">期限</p></li>

                        <li class="line-left" style="margin-top:10px;"></li>
                        <li style="padding:16px 0 0 30px;">
                        	<p class="f16" style="padding-bottom:20px;">定期宝（${fixBorrow3Vo.contractNo}）</p>
                            <div  class="f16 bar-box">
                         		<div  class="f16" style="float:left; ">当前进度</div> 
                         		<div class="rl-a" style=" float:left;"> 
                                <span class="bar-a" style="width:${fixBorrow3Vo.scheduleStrNoDecimal}%"></span>
                                </div>
                                <span class="f16" style="padding-left:10px;"><fmt:formatNumber value="${fixBorrow3Vo.scheduleStrNoDecimal}" pattern="#,##0"></fmt:formatNumber>%</span>
                            </div>
                        
                        </li>
                        <li  class="syje" style="text-align:left;">
                        		<div class="f14 time-back1" style="text-indent:20px;">
	                        	<c:if test="${fixBorrow3Vo.statusId=='2'}">
	                        		 <span id="dingqibao3relase"  style="  "></span>  
	                            </c:if>
                        		</div>
                                <div class="f16" style="  ">&nbsp;
                             	 <input id="dingqibao31relaseButton" class="jqqd-btn-a" style="position: absolute;right:3%; margin-top:35px;  ${fixBorrow3Vo.statusId == 1  ?'':'display:none;'}" type="button" value="加入中" onclick="openDingbao('${fixBorrow3Vo.id}');"/>
                                 <input id="dingqibao32relaseButton" class="jqqd-btn-b" style="position: absolute;right:3%; margin-top:35px;  ${fixBorrow3Vo.statusId == 2  ?'':'display:none;'}" type="button" value="敬请期待" onclick="openDingbao('${fixBorrow3Vo.id}');"/>
                                 <input id="dingqibao33relaseButton" class="jqqd-btn-c" style="position: absolute;right:3%; margin-top:35px; ${fixBorrow3Vo.statusId == 3  ?'':'display:none;'}" type="button" value="收益中" onclick="openDingbao('${fixBorrow3Vo.id}');"/>
                                 <input id="dingqibao34relaseButton" class="jqqd-btn-e" style="position: absolute;right:3%; margin-top:35px; ${fixBorrow3Vo.statusId == 4  ?'':'display:none;'}" type="button" value="已退出" onclick="openDingbao('${fixBorrow3Vo.id}');"/>
                                </div>
                                <div style=" font-size:16px; position: absolute;bottom:30px;right:17%; overflow: hidden; float:left;"> 剩余金额<span style="padding:20px 10px 0 10px;">￥<fmt:formatNumber value='${fixBorrow3Vo.planAccount - fixBorrow3Vo.accountYes}' pattern="#,##0.##"></fmt:formatNumber></span></div>
                        </li>
                    </ul>
                    </c:if>
                    
                    <c:if test="${fixBorrow6Vo != null}">
                     <ul class="content-bigbox content-bigbox-a" style="border-bottom:1px #ff9933 solid;position: relative;">
                    	<li class="pd-li" style="   position:relative; padding-left:30px; width:130px;">
	                    	<p class="shouyi dqb-shouyi  pd-li-zj" style="padding-right:0px;">
	                    		<c:if test="${fixBorrow6Vo.apr>10}">10</c:if><c:if test="${fixBorrow6Vo.apr<=10}"><fmt:formatNumber value="${fixBorrow6Vo.apr}" pattern="#,##0.#"/></c:if><span class="baifenhao">%</span>
		                   	 	<c:if test="${fixBorrow6Vo.apr>10}">
		                   	 		<span class="bbose" style="position:absolute;top:5px; right:-15%;"><fmt:formatNumber value="${fixBorrow6Vo.apr-10}" pattern="#,##0.#"/>%</span>
		                   	 	</c:if>
	                    	</p>
                    		<p class="biaoti-bt">年化收益</p>
                    	</li>
						<li class="pd-yue" style="padding-right:35px;margin-left:10px;"><p><span class="f20 bigje" style="color:#996633;">${fixBorrow6Vo.lockLimit}个月</span></p><p class="ue-yue f14" style="text-align:right;">期限</p></li>

                        <li class="line-left" style="margin-top:10px;"></li>
                        <li style="padding:16px 0 0 30px;">
                        	<p class="f16" style="padding-bottom:20px;">定期宝（${fixBorrow6Vo.contractNo}）</p>
                            <div  class="f16 bar-box">
                         		<div  class="f16" style="float:left; ">当前进度</div> 
                         		<div class="rl-a" style=" float:left;"> 
                                <span class="bar-a" style="width:${fixBorrow6Vo.scheduleStrNoDecimal}%"></span>
                                </div>
                                <span class="f16" style="padding-left:10px;"><fmt:formatNumber value="${fixBorrow6Vo.scheduleStrNoDecimal}" pattern="#,##0"></fmt:formatNumber>%</span>
                            </div>
                        
                        </li>
                        <li  class="syje" style="text-align:left;">
                        		<div class="f14 time-back1" style="text-indent:20px;">
	                        	<c:if test="${fixBorrow6Vo.statusId=='2'}">
	                        		 <span id="dingqibao6relase"  style="  "></span>  
	                            </c:if>
                        		</div>
                                <div class="f16" style="  ">&nbsp;
                             	 <input id="dingqibao61relaseButton" class="jqqd-btn-a" style="position: absolute;right:3%; margin-top:35px;  ${fixBorrow6Vo.statusId == 1  ?'':'display:none;'}" type="button" value="加入中" onclick="openDingbao('${fixBorrow6Vo.id}');"/>
                                 <input id="dingqibao62relaseButton" class="jqqd-btn-b" style="position: absolute;right:3%; margin-top:35px;  ${fixBorrow6Vo.statusId == 2  ?'':'display:none;'}" type="button" value="敬请期待" onclick="openDingbao('${fixBorrow6Vo.id}');"/>
                                 <input id="dingqibao63relaseButton" class="jqqd-btn-c" style="position: absolute;right:3%; margin-top:35px; ${fixBorrow6Vo.statusId == 3  ?'':'display:none;'}" type="button" value="收益中" onclick="openDingbao('${fixBorrow6Vo.id}');"/>
                                 <input id="dingqibao64relaseButton" class="jqqd-btn-e" style="position: absolute;right:3%; margin-top:35px; ${fixBorrow6Vo.statusId == 4  ?'':'display:none;'}" type="button" value="已退出" onclick="openDingbao('${fixBorrow6Vo.id}');"/>
                                </div>
                                <div style=" font-size:16px; position: absolute;bottom:30px;right:17%; overflow: hidden; float:left;"> 剩余金额<span style="padding:20px 10px 0 10px;">￥<fmt:formatNumber value='${fixBorrow6Vo.planAccount - fixBorrow6Vo.accountYes}' pattern="#,##0.##"></fmt:formatNumber></span></div>
                        </li>
                    </ul>
                    </c:if>
                    
                    <c:if test="${fixBorrow12Vo != null}">
                     <ul class="content-bigbox content-bigbox-a" style="border-bottom:1px #ff9933 solid;position: relative;">
                    	<li class="pd-li" style="   position:relative; padding-left:30px; width:130px;">
	                    	<p class="shouyi dqb-shouyi  pd-li-zj" style="padding-right:0px;">
	                    		<c:if test="${fixBorrow12Vo.apr>11}">11</c:if><c:if test="${fixBorrow12Vo.apr<=11}"><fmt:formatNumber value="${fixBorrow12Vo.apr}" pattern="#,##0.#"/></c:if><span class="baifenhao">%</span>
		                   	 	<c:if test="${fixBorrow12Vo.apr>11}">
		                   	 		<span class="bbose" style="position:absolute;top:5px; right:-15%;"><fmt:formatNumber value="${fixBorrow12Vo.apr-11}" pattern="#,##0.#"/>%</span>
		                   	 	</c:if>
	                    	</p>
                    		<p class="biaoti-bt">年化收益</p>
                    	</li>
						<li class="pd-yue" style="padding-right:35px;margin-left:10px;"><p><span class="f20 bigje" style="color:#996633;">${fixBorrow12Vo.lockLimit}个月</span></p><p class="ue-yue f14" style="text-align:right;">期限</p></li>

                        <li class="line-left" style="margin-top:10px;"></li>
                        <li style="padding:16px 0 0 30px;">
                        	<p class="f16" style="padding-bottom:20px;">定期宝（${fixBorrow12Vo.contractNo}）</p>
                            <div  class="f16 bar-box">
                         		<div  class="f16" style="float:left; ">当前进度</div> 
                         		<div class="rl-a" style=" float:left;"> 
                                <span class="bar-a" style="width:${fixBorrow12Vo.scheduleStrNoDecimal}%"></span>
                                </div>
                                <span class="f16" style="padding-left:10px;"><fmt:formatNumber value="${fixBorrow12Vo.scheduleStrNoDecimal}" pattern="#,##0"></fmt:formatNumber>%</span>
                            </div>
                        
                        </li>
                        <li  class="syje" style="text-align:left;">
                        		<div class="f14 time-back1" style="text-indent:20px;">
	                        	<c:if test="${fixBorrow12Vo.statusId=='2'}">
	                        		 <span id="dingqibao6relase"  style="  "></span>  
	                            </c:if>
                        		</div>
                                <div class="f16" style="  ">&nbsp;
                             	 <input id="dingqibao61relaseButton" class="jqqd-btn-a" style="position: absolute;right:3%; margin-top:35px;  ${fixBorrow12Vo.statusId == 1  ?'':'display:none;'}" type="button" value="加入中" onclick="openDingbao('${fixBorrow12Vo.id}');"/>
                                 <input id="dingqibao62relaseButton" class="jqqd-btn-b" style="position: absolute;right:3%; margin-top:35px;  ${fixBorrow12Vo.statusId == 2  ?'':'display:none;'}" type="button" value="敬请期待" onclick="openDingbao('${fixBorrow12Vo.id}');"/>
                                 <input id="dingqibao63relaseButton" class="jqqd-btn-c" style="position: absolute;right:3%; margin-top:35px; ${fixBorrow12Vo.statusId == 3  ?'':'display:none;'}" type="button" value="收益中" onclick="openDingbao('${fixBorrow12Vo.id}');"/>
                                 <input id="dingqibao64relaseButton" class="jqqd-btn-e" style="position: absolute;right:3%; margin-top:35px; ${fixBorrow12Vo.statusId == 4  ?'':'display:none;'}" type="button" value="已退出" onclick="openDingbao('${fixBorrow12Vo.id}');"/>
                                </div>
                                <div style=" font-size:16px; position: absolute;bottom:30px;right:17%; overflow: hidden; float:left;"> 剩余金额<span style="padding:20px 10px 0 10px;">￥<fmt:formatNumber value='${fixBorrow12Vo.planAccount - fixBorrow12Vo.accountYes}' pattern="#,##0.##"></fmt:formatNumber></span></div>
                        </li>
                    </ul>
                    </c:if>
            </div>
            
           <DIV style="width:110px; height:110px;" id="diagram"></DIV>            
           
	</div>
</div>
    
<div class="clear"></div>

<!--投资列表 开始 -->
<div class="tbztcr" id="borrowList">
	<div class="tbztcr" style="padding-left:0px;">
                                <h3 >投标列表</h3>
                                  <table border="0"  >
                                <tr>
                                <th width="680px">借款标题</th>
                                <th width="300px">发布时间</th>    
                                <th width="100px">信用等级</th>
                                <th width="100px">金额</th>   
                                <th width="100px">期限</th>
                                <th width="140px">利率</th>
                                <th width="140px">还款方式</th>
                                <th width="100px" >进度</th>
                                <th width="140px">状态</th>     
                                </tr>
                           
                                <c:forEach items="${mixedBorrowList.result}" var="borrow" varStatus= "idx" >
                                <c:set var="btype" value="${borrow.borrowtype }" scope="request"  />
									<c:choose>   
										<c:when test="${btype == 1}">
											<c:set var="cls" value="xin" scope="request"  />
										</c:when>   
										<c:when test="${btype == 2}">
											<c:set var="cls" value="di" scope="request"  /> 
										</c:when>
										<c:when test="${btype == 3}">
											<c:set var="cls" value="jing" scope="request"  />  
										</c:when>
										<c:when test="${btype == 4}">
											<c:set var="cls" value="" scope="request"  />   
										</c:when>
										<c:when test="${btype == 5}">
											<c:set var="cls" value="bao" scope="request"  />     
										</c:when>
										<c:otherwise></c:otherwise> 
									</c:choose> 
	 
                                	<tr style="">
                                    <td style="position: relative;width: 28%;"><em class="${cls }"></em>
                                    <c:if test="${null!=borrow.bidPassword && borrow.bidPassword != ''}"><font color="#EE30A7">[密]</font></c:if> 
                                		<a href="javascript:;"    onclick="goToInvest(${borrow.id});">
		                                		<c:if test="${borrow.name==null }">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</c:if>
		                                		${borrow.subName}
		                               			 		
		                                </a> 
		                               
		                               	<c:if test="${borrow.isFinancialUser==1 }">
			                               	<a href="##" title="${borrow.userNameSecret}">
			                               	<img src="${basePath}/images/user.png" width="20" height="20"/>
			                               	</a>
		                               	</c:if>
		                               
		                               	<c:if test="${borrow.isFinancialUser==0 }">
		                               		<a href="##" title="${borrow.userName}">
			                               	<img src="${basePath}/images/user.png" width="20" height="20"/>
			                               	</a>
		                               	</c:if>
		                               	<c:if test="${borrow.areaType==1 }">
				                               <c:if test="${borrow.id==add5}">
													   <span style="color:red;">【518新人专享】</span>
											   </c:if>
											    <c:if test="${! (borrow.id==add5 ) }">
				                                  	<span style="color:red;">【新人专享】</span>
				                               	</c:if>
		                               	</c:if>	
		                               	 <c:if test="${borrow.id==add2}">
													   <span style="color:red;">【518活动专享】</span>
											   </c:if>
		                               	
		                               	 <c:if test="${borrow.id==add5 or borrow.id==add2}">
											    <div class="index-seal"> <img src="${path }/images/seal.png"/></div>
										  </c:if>
										  <c:if test="${borrow.apr == '15.71'}">
												<span style="color:red;">【股市暖心】</span>
										   </c:if>
									</td>
						 
								<td align="center">
									<c:choose>
										<c:when test="${borrow.status==1}">${borrow.timingBorrowTimeStr}</c:when>
										<c:otherwise>${borrow.publishTimeStr}</c:otherwise>
									</c:choose>
								</td>
								
								
                              		<td align="center">
                              			<c:if test="${borrow.creditRating== 'A'}">
                              				<img src="${basePath}/images/A.gif" width="34" height="34"/>
                              			</c:if>
                              			
                              			<c:if test="${borrow.creditRating== 'B'}">
                              				<img src="${basePath}/images/B.gif" width="34" height="34" alt="理财产品"/>
                              			</c:if>
                              			
                              			<c:if test="${borrow.creditRating== 'C'}">
                              				<img src="${basePath}/images/c.gif" width="34" height="34"/>
                              			</c:if>
                              			
                              			<c:if test="${borrow.creditRating== 'D'}">
                              				<img src="${basePath}/images/dd.gif" width="34" height="34"/>
                              			</c:if>
                              		</td>
                               
	                                <td align="center" width="220px;"><i><fmt:formatNumber value="${borrow.account}" pattern="#,##0.00"></fmt:formatNumber></i>元</td>
	                                <td align="center">
		                                	<c:if test="${borrow.borrowtype==4}">秒还</c:if>
									 		<c:if test="${borrow.borrowtype!=4 && borrow.style !=4 }"><i>${borrow.timeLimit }</i>月</c:if>
									 		<c:if test="${borrow.borrowtype!=4 && borrow.style ==4 }"><i>${borrow.timeLimit }</i>天</c:if>
									</td>
	                                <td align="center" style="position: relative;">
	                                	<c:if test="${borrow.areaType==1 && ! (borrow.id==add5 )}">
	                                		<c:if test="${borrow.apr > 15}">
		                                  		<i><fmt:formatNumber value="${borrow.apr-3}" pattern="#,##0.##"/></i>%<span style="color:red;"><em>+3</em>%</span>	
		                                  	</c:if> 
		                                  	<c:if test="${borrow.apr <= 15}">
		                                  		<i><fmt:formatNumber value="${borrow.apr}" pattern="#,##0.##"/></i>%	
		                                  	</c:if>                            	 
		                               	</c:if>	
		                                 <c:if test="${borrow.id==add5}">
										      <i><fmt:formatNumber value="${borrow.apr-5}" pattern="#,##0.##"/></i>%<span style="color:red;"><em>+5</em>%</span> 
										   </c:if> 
										    <c:if test="${borrow.id==add2}">
										     <i> <fmt:formatNumber value="${borrow.apr-2}" pattern="#,##0.##"/></i>%<span style="color:red;"><em>+2</em>%</span> 
										   </c:if> 
										    <c:if test="${! (borrow.id==add5 or borrow.id==add2 or borrow.apr == '15.71') and borrow.areaType!=1 }">
										     <i> <fmt:formatNumber value="${borrow.apr}" pattern="#,##0.##"/></i>%  
										 	</c:if> 
										 	<c:if test="${borrow.apr == '15.71'}">
										     <i> <fmt:formatNumber value="${borrow.apr-0.71}" pattern="#,##0.##"/></i>%
					   							<div style=" height:25px;color:red;position: absolute; background: red;color: #fff;line-height: 25px;  top:-5px;right:-30%; border-radius: 5px;">+0.71%</div>
										    </c:if>
										    
	                                </td>
	                                <td align="center">
		                                <c:choose>
		                                	<c:when test="${borrow.style == 0}">
												没有限制     
											</c:when>   
											<c:when test="${borrow.style == 1}">
												等额本息
											</c:when>   
											<c:when test="${borrow.style == 2}">
												<a href="##" title="按月付息到期还本">按月付息</a> 
											</c:when>
											<c:when test="${borrow.style == 3}">
												<a href="##" title="到期还本付息">到期还本</a> 
											</c:when>
											<c:when test="${borrow.style == 4}">
												按天还款   
											</c:when>
											<c:otherwise></c:otherwise>
										</c:choose> 
	                                </td>
	                                
	                                <td align="center"> 
                                   		    <DIV style="width:40px; height:40px; margin:0 auto;" class="investDiagramCls" id="diagram${idx.index}"></DIV>
		                                    <DIV style="display: none;" class="get1">
		                                    <DIV class="arc1"><INPUT id="percent${idx.index}" class="investPercentCls" value="${borrow.scheduleStrNoDecimal }" type="hidden" 
		                                    default="0">	
		                                    <c:choose>
														<c:when test="${borrow.scheduleStrNoDecimal < 100}">
															<INPUT class="color1" value="#00a7e5" type="hidden">
														</c:when>
														<c:otherwise>
															<INPUT class="color1" value="#94c73d" type="hidden">
														</c:otherwise>
													</c:choose> 
		                                    </DIV>
		                                    </DIV>
                                    </td>
	                                 
									 
	                                <td align="right">
		                                	<c:if test="${borrow.status==1}">
												<input type="button" class="but" value="预发中" onclick="goToInvest(${borrow.id});"/>
											</c:if>
											<c:if test="${borrow.status==42}">
												<input type="button" class="but yellow" value="已垫付" onclick="goToInvest(${borrow.id});"/>
											</c:if>
											<c:if test="${borrow.status==2 }">
												<input type="button" class="but bluess" value="${portal:desc(100, borrow.status)}" onclick="goToInvest(${borrow.id});"/>
											</c:if>
											<c:if test="${borrow.status==4 }">
												<input type="button" class="but greens" value="${portal:desc(100, borrow.status)}" onclick="goToInvest(${borrow.id});"/>
											</c:if>
											<c:if test="${borrow.status==5 }">
												<input type="button" class="but gray" value="${portal:desc(100, borrow.status)}" onclick="goToInvest(${borrow.id});"/>
											</c:if>
										 
	                                </td>
	                                </tr>
                                </c:forEach>	
</table>
<div class="tbztcrb"><a href="${path}/toubiao.html" rel="nofollow">查看更多理财项目</a></div>
</div>
</div>
<!--投资列表 结束 -->
<div class="tbztcr1">
<span>最新公告</span>
<ul id="notice_0">
	<c:forEach items="${noticePage.result}" var="new_notice">
		<li class="tb_line">
			<p class="tblist_icon"><a href="${path}/gonggao/${new_notice.id}.html">${new_notice.title }</a></p>
			<p class="tblist_date">${new_notice.addtimeStr }</p>
		</li>	 
	</c:forEach>
</ul>
<div class="tbztcrb"><a href="${path }/gonggao.html">查看更多公告</a></div>
</div>
<div class="mtbox" style=" text-align:left;">
   	<div class="mttitle" style="">媒体报道</div>
	<ul style=" height:155px; overflow:hidden;">
		<li class="boxtopindex">
			<a target="_blank" rel="nofollow" href="http://sh.xinhuanet.com/2015-10/27/c_134754903.htm?from=singlemessage&isappinstalled=0"><img alt="投资理财" src="${path}/images/mticon3.png"/></a>
			<a target="_blank" rel="nofollow" href="http://bgimg.ce.cn/xwzx/gnsz/gdxw/201508/26/t20150826_6329617.shtml"><img alt="抵押贷款" src="${path}/images/mticon7.png"/></a>			
			<a target="_blank" rel="nofollow" onclick="_hmt.push(['_trackEvent', 'imagelink', 'click', 'Linode'])"  href="http://v.ifeng.com/news/finance/201507/01cdf3af-eafb-48f9-8696-7dd8ffa5c053.shtml"><img title="理财小知识" alt="理财小知识" src="${path}/images/mingsheng_10.png"/></a>
           	<a target="_blank" rel="nofollow" onclick="_hmt.push(['_trackEvent', 'imagelink', 'click', 'Linode'])"  href="http://v.qq.com/page/x/x/n/x0154z5xgxn.html"><img alt="抵押贷款" src="${path}/images/mticon1.png"/></a>
            <a target="_blank" rel="nofollow" href="http://v.youku.com/v_show/id_XOTQ5NDkyMDIw.html"><img alt="互联网金融" src="${path}/images/mticon2.png"/></a>
       	</li>
		<li class="boxtopindex">
		    <a target="_blank" rel="nofollow" href="http://www.sh.chinanews.com/news/20150428/836597.html"><img alt="投资理财" src="${path}/images/mticon4.png"/></a>
			<a target="_blank" rel="nofollow" href="http://www.labour-daily.cn/ldb/node41/node2151/20150730/n47090/n47097/u1ai241824.html?from=singlemessage&isappinstalled=0 "><img alt="贷款平台" src="${path}/images/mticon10.png"/></a>
            <a target="_blank" rel="nofollow" href="http://www.shbiz.com.cn/Item/253594.aspx"><img alt="贷款平台" src="${path}/images/mticon6.png"/></a>
            <a target="_blank" rel="nofollow" href="http://news.163.com/14/1017/16/A8P8KC9U00014SEH.html"><img alt="互联网金融" src="${path}/images/mticon5.png"/></a>
            <a target="_blank" rel="nofollow" href="http://finance.china.com/fin/lc/201501/29/6363687.html"><img alt="理财小知识" src="${path}/images/mticon9.png"/></a>
		</li>
	</ul>
</div>
<div>
<%@ include file="/WEB-INF/page/common/footer.jsp" %>
</div>

</body>

<script type="text/javascript">
//页面 当前时间
var addNowTime=0;
var isQuitLoopZhitongche = false;
var isQuitLoopDingqibao1 = false;
var isQuitLoopDingqibao3 = false;
//当前循环对象
var addInterValObj;
var SysSecond; 
$(document).ready(function () {
    $('.closbox').click(function(){
    	$('.countdown-banner').hide();
    });
	$(".main_visual").hover(function(){
		$("#btn_prev,#btn_next").fadeIn();
		},function(){
		$("#btn_prev,#btn_next").fadeOut();
		});
	$dragBln = false;
	$(".main_image").touchSlider({
		flexible : true,
		speed : 200,
		btn_prev : $("#btn_prev"),
		btn_next : $("#btn_next"),
		paging : $(".flicking_con a"),
		counter : function (e) {
			$(".flicking_con a").removeClass("on").eq(e.current-1).addClass("on");
		}
	});
	$(".main_image").bind("mousedown", function() {
		$dragBln = false;
	});
	$(".main_image").bind("dragstart", function() {
		$dragBln = true;
	});
	$(".main_image a").click(function() {
		if($dragBln) {
			return false;
		}
	});
	timer = setInterval(function() { $("#btn_next").click();}, 5000);
	$(".main_visual").hover(function() {
		clearInterval(timer);
	}, function() {
		timer = setInterval(function() { $("#btn_next").click();}, 5000);
	});
	$(".main_image").bind("touchstart", function() {
		clearInterval(timer);
	}).bind("touchend", function() {
		timer = setInterval(function() { $("#btn_next").click();}, 5000);
	});
	initFirstProgress();
	initProgress();
	//页面加载后，jquery中，判断日期，控制某个div的显隐
	changeBtnState();
	var addNowTime = new Date('${nowTime}').getTime()/1000;
	
	if(parseInt(addNowTime) > parseInt(1436544000)){ // 2015-07-11
		$("#awardApr1").hide(); 
		$("#awardAprPic").attr("class", "jiang");
		$("#awardAprPic").html("");
	}
	
	if(parseInt(addNowTime) > parseInt(1438358400)){ // 2015-08-01
		$("#awardApr").hide();
		$("#awardAprPic").hide();
	}
	// 2015-12-24 00:00:00至 2016-01-24 59:59:59显示双旦活动弹出层
	if(parseInt(addNowTime) > parseInt(1450886400) && parseInt(addNowTime) <parseInt(1453651199)){ 
		$('.countdown-banner').show();
	}
	if(addNowTime >= 1441036800){ // 2015-09-01
		$("#awardTipOne").hide();
	}else{
		$("#awardTipOne").show();
	}
	
});

function SetRemainTime() {
	if (SysSecond > 0) {
		SysSecond = SysSecond - 1;
		$("#countdown").html("&nbsp;距离本次结束时间："+formatTime(SysSecond));
	} else {//剩余时间小于或等于0的时候，就停止间隔函数 
		window.clearInterval(InterValObj);
		$("#countdown").html("&nbsp;本次开通结束");
	}
}

function goToInvest(borrowId){
	window.open("${path}/toubiao/"+borrowId+".html"); 
}


function goToTransfer(borrowId){
	window.open("${path}/zhaiquan/"+borrowId+".html"); 
}


    function initFirstProgress(){
   	 $(".loading #diagram").empty();
   	var count = $(".get").find(".arc").find(".percent").val();
		if(Number(count) < 0.001){
			o.num = 0.01;
			o.cicleNum = count;
		}else{
			o.num = count;
			o.cicleNum = count;
		}
		var firstStatus = $(".get").find(".arc").find(".firstStatus").val();
		o.status = firstStatus;
		o.diagramDiv = "diagram";
		o.getDiv = ".get";
		o.init(); 
    }
	 /**
	 * create circle for invest list
	 */
	 function initProgress(){
		 var investDiagram = $(".investDiagramCls");
		 if(investDiagram.length>0){
			 $(investDiagram).each(function(index,element){
			   	 $(".loading #"+element.id).empty();
			   		var   count=  $("#percent"+index).val(); 
				   	if(Number(count) < 0.001){
						oo.num = 0.01;
						oo.cicleNum = count;
					}else{
						oo.num = count;
						oo.cicleNum = count;
					}
					oo.diagramDiv = "diagram"+index;
					oo.getDiv = ".get1";
					oo.init();
			 });
		 }
	 }
    
	 /**
	  * 下一场倒计时开始计时
	  */
	 function changeBtnState(){
		//当前时间
	 	var nowTime = '${nowTime}';
	 	/**var publishTimeStr = '<fmt:formatDate value="${firstBorrow.publishTime}" pattern="yyyy/MM/dd HH:mm:ss"/>';
	 	if(typeof(addInterValObj)!="undefined"){
	 		window.clearInterval(addInterValObj);
	 	}
	 	addInterValObj = window.setInterval(function (){setOpenTime(nowTime,publishTimeStr);}, 1000); //间隔函数，1秒执行*/
	 
	 	var dingqiBao1PublishTimeStr = '<fmt:formatDate value="${fixBorrow1Vo.publishTime}" pattern="yyyy/MM/dd HH:mm:ss"/>';
	 	var dingqiBao3PublishTimeStr = '<fmt:formatDate value="${fixBorrow3Vo.publishTime}" pattern="yyyy/MM/dd HH:mm:ss"/>';
	 	var dingqiBao6PublishTimeStr = '<fmt:formatDate value="${fixBorrow6Vo.publishTime}" pattern="yyyy/MM/dd HH:mm:ss"/>';
	 	var dingqiBao12PublishTimeStr = '<fmt:formatDate value="${fixBorrow12Vo.publishTime}" pattern="yyyy/MM/dd HH:mm:ss"/>';
	 	if(typeof(addDingBao1InterValObj)!="undefined"){
	 		window.clearInterval(addDingBao1InterValObj);
	 	}
	 	if(typeof(addDingBao3InterValObj)!="undefined"){
	 		window.clearInterval(addDingBao3InterValObj);
	 	}	 	
	 	if(typeof(addDingBao6InterValObj)!="undefined"){
	 		window.clearInterval(addDingBao6InterValObj);
	 	}
	 	if(typeof(addDingBao12InterValObj)!="undefined"){
	 		window.clearInterval(addDingBao12InterValObj);
	 	}
	 	
		addDingBao1InterValObj = window.setInterval(function (){setOpenDing1Time(nowTime,dingqiBao1PublishTimeStr);}, 1000); //间隔函数，1秒执行
	 	
	 	addDingBao3InterValObj = window.setInterval(function (){setOpenDing3Time(nowTime,dingqiBao3PublishTimeStr);}, 1000); //间隔函数，1秒执行
	 	
	 	addDingBao6InterValObj = window.setInterval(function (){setOpenDing6Time(nowTime,dingqiBao6PublishTimeStr);}, 1000); //间隔函数，1秒执行
	 	
	 	addDingBao12InterValObj = window.setInterval(function (){setOpenDing12Time(nowTime,dingqiBao12PublishTimeStr);}, 1000); //间隔函数，1秒执行
	 }
	 
	 /**
	  * 设置启动开通时间
	  
	 function setOpenTime(nowTime,publishTimeStr) {
		//是否结束循环
	 	var isQuitLoop = true;
	 	//时间加1秒
	 	if(addNowTime>0){
	 		addNowTime = addNowTime+1;
	 		isQuitLoopZhitongche = true;
	 	}else{
	 		addNowTime = new Date(nowTime).getTime()/1000+1;
	 		isQuitLoopZhitongche = true;
	 	}
	 	//循环发布时间
     	var publishTime  = new Date(publishTimeStr).getTime()/1000;
     	var endTimeStr = '<fmt:formatDate value="${firstBorrow.endTime}" pattern="yyyy/MM/dd HH:mm:ss"/>';
 		var endTime = new Date(endTimeStr).getTime()/1000;
 		var status = ${firstBorrow.status};
 		//如果有状态为3并且当前时间大于发布时间的继续循环
 		if(status==3 && parseInt(addNowTime) < parseInt(publishTime) && parseInt(addNowTime) < parseInt(endTime)){
 			 isQuitLoop = false;
			 var remainSecond = parseInt(publishTime)-parseInt(addNowTime);
			 $("#countdown").html("&nbsp;距离下次开通时间："+formatTime(remainSecond));
 		}else{
 			if(status == 5 || parseInt(addNowTime) > parseInt(endTime)){  //开通结束
 				$("#countdown").html("&nbsp;本次开通结束");
 				$("#starting").attr("style","position: absolute;right:3.5%;bottom:30.5%;display:none;");
 				$("#stoped").attr("style","position: absolute;right:3.5%;bottom:30.5%;");
 			}
 			if(status==3 && parseInt(addNowTime) >= parseInt(publishTime)){
 				SysSecond = parseInt(endTime - addNowTime); //这里获取倒计时的起始时间 
 	 			InterValObj = window.setInterval(SetRemainTime, 1000); //间隔函数，1秒执行
 				$("#stoped").attr("style","position: absolute;right:3.5%;bottom:30.5%;display:none;");
 				$("#starting").attr("style","position: absolute;right:3.5%;bottom:30.5%;");
 			}
 			if(parseInt(addNowTime) > parseInt(endTime)){
 				$("#starting").attr("style","position: absolute;right:3.5%;bottom:30.5%;display:none;");
 				$("#stoped").attr("style","position: absolute;right:3.5%;bottom:30.5%;");
 			}
 		}
	 	//停止循环
	 	if(isQuitLoop){
	 		isQuitLoopZhitongche = false;
	 		window.clearInterval(addInterValObj);
	 	}
	 } */
	 
	 /**
	  * 设置启动开通时间
	  */
	 function setOpenDing1Time(nowTime,publishTimeStr) {
		//是否结束循环
	 	var isQuitLoop = true;
	 	//时间加1秒
	 	if(!isQuitLoopZhitongche){
	 		if(addNowTime>0){
		 		addNowTime = addNowTime+1;
		 		isQuitLoopDingqibao1 = true;
		 	}else{
		 		addNowTime = new Date(nowTime).getTime()/1000+1;
		 		isQuitLoopDingqibao1 = true;
		 	}
	 	}
	 	
	 	//循环发布时间
     	var publishTime  = new Date(publishTimeStr).getTime()/1000;
	 	//循环发布时间
 		var status = '${fixBorrow1Vo.statusId}';
 		//如果有状态为2并且当前时间大于发布时间的继续循环
 		if(status==2 && parseInt(addNowTime) < parseInt(publishTime)){
 			 isQuitLoop = false;
			 var remainSecond = parseInt(publishTime)-parseInt(addNowTime);
			 $("#dingqibao1relase").html("&nbsp;距离发布还有："+formatTime(remainSecond));
 		}else{
 			if(status==2 && parseInt(addNowTime) >= parseInt(publishTime)){
 			
 				$("#dingqibao1relaseButton").attr("style","position: absolute;right:3%; margin-top:35px;");
 				$("#dingqibao2relaseButton").attr("style","position: absolute;right:3%; margin-top:35px;display:none;");
 			}
 			
 			 $("#dingqibao1relase").html("");
 		} 
	 	//停止循环
	 	if(isQuitLoop){
	 		isQuitLoopDingqibao1 = false;
	 		window.clearInterval(addDingBao1InterValObj);
	 	}
	 } 
	 
	 
	 /**
	  * 设置启动开通时间
	  */
	 function setOpenDing3Time(nowTime,publishTimeStr) {
		//是否结束循环
	 	var isQuitLoop = true;
	 	//时间加1秒
	 	if( !isQuitLoopZhitongche && !isQuitLoopDingqibao1){
	 		if(addNowTime>0){
		 		addNowTime = addNowTime+1;
		 		isQuitLoopDingqibao3 = true;
		 	}else{
		 		addNowTime = new Date(nowTime).getTime()/1000+1;
		 		isQuitLoopDingqibao3 = true;
		 	}
	 	}

	 	//循环发布状态
 		var status = '${fixBorrow3Vo.statusId}';
 	 	//循环发布时间
     	var publishTime  = new Date(publishTimeStr).getTime()/1000;
 		//如果有状态为2并且当前时间大于发布时间的继续循环
 		if(status==2 && parseInt(addNowTime) < parseInt(publishTime)){
 			 isQuitLoop = false;
			 var remainSecond = parseInt(publishTime)-parseInt(addNowTime);
			 $("#dingqibao3relase").html("&nbsp;距离发布还有："+formatTime(remainSecond));
 		} else{
 			if(status==2 && parseInt(addNowTime) >= parseInt(publishTime)){
 			
 				$("#dingqibao31relaseButton").attr("style","position: absolute;right:3%; margin-top:35px;");
 				$("#dingqibao32relaseButton").attr("style","position: absolute;right:3%; margin-top:35px;display:none;");
 			}
 			
 			 $("#dingqibao3relase").html("");
 		} 
	 	//停止循环
	 	if(isQuitLoop){
	 		isQuitLoopDingqibao3 = false;
	 		window.clearInterval(addDingBao3InterValObj);
	 	}
	 } 
	 
	 /**
	  * 设置启动开通时间
	  */
	 function setOpenDing6Time(nowTime,publishTimeStr) {
		//是否结束循环
	 	var isQuitLoop = true;
	 	//时间加1秒
	 	if( !isQuitLoopZhitongche && !isQuitLoopDingqibao1 && !isQuitLoopDingqibao3){
	 		if(addNowTime>0){
		 		addNowTime = addNowTime+1;
		 	}else{
		 		addNowTime = new Date(nowTime).getTime()/1000+1;
		 	}
	 	}

	 	//循环发布状态
 		var status = '${fixBorrow6Vo.statusId}';
 	 	//循环发布时间
     	var publishTime  = new Date(publishTimeStr).getTime()/1000;
 		//如果有状态为2并且当前时间大于发布时间的继续循环
 		if(status==2 && parseInt(addNowTime) < parseInt(publishTime)){
 			 isQuitLoop = false;
			 var remainSecond = parseInt(publishTime)-parseInt(addNowTime);
			 $("#dingqibao6relase").html("&nbsp;距离发布还有："+formatTime(remainSecond));
 		} else{
 			if(status==2 && parseInt(addNowTime) >= parseInt(publishTime)){
 			
 				$("#dingqibao61relaseButton").attr("style","position: absolute;right:3%; margin-top:35px;");
 				$("#dingqibao62relaseButton").attr("style","position: absolute;right:3%; margin-top:35px;display:none;");
 			}
 			
 			 $("#dingqibao6relase").html("");
 		} 
	 	//停止循环
	 	if(isQuitLoop){
	 		window.clearInterval(addDingBao6InterValObj);
	 	}
	 }
	 
	 /**
	  * 设置启动开通时间
	  */
	 function setOpenDing12Time(nowTime,publishTimeStr) {
		//是否结束循环
	 	var isQuitLoop = true;
	 	//时间加1秒
	 	if( !isQuitLoopZhitongche && !isQuitLoopDingqibao1 && !isQuitLoopDingqibao3 && !isQuitLoopDingqibao6){
	 		if(addNowTime>0){
		 		addNowTime = addNowTime+1;
		 	}else{
		 		addNowTime = new Date(nowTime).getTime()/1000+1;
		 	}
	 	}

	 	//循环发布状态
 		var status = '${fixBorrow12Vo.statusId}';
 	 	//循环发布时间
     	var publishTime  = new Date(publishTimeStr).getTime()/1000;
 		//如果有状态为2并且当前时间大于发布时间的继续循环
 		if(status==2 && parseInt(addNowTime) < parseInt(publishTime)){
 			 isQuitLoop = false;
			 var remainSecond = parseInt(publishTime)-parseInt(addNowTime);
			 $("#dingqibao12relase").html("&nbsp;距离发布还有："+formatTime(remainSecond));
 		} else{
 			if(status==2 && parseInt(addNowTime) >= parseInt(publishTime)){
 			
 				$("#dingqibao121relaseButton").attr("style","position: absolute;right:3%; margin-top:35px;");
 				$("#dingqibao122relaseButton").attr("style","position: absolute;right:3%; margin-top:35px;display:none;");
 			}
 			
 			 $("#dingqibao12relase").html("");
 		} 
	 	//停止循环
	 	if(isQuitLoop){
	 		window.clearInterval(addDingBao12InterValObj);
	 	}
	 }
	 
    /*
     * 秒转化成天时分秒
     */
	function formatTime(s) {
	   var mi = 60;
	   var hh = mi * 60;
	   var dd = hh * 24;
	   var day = Math.floor(s / dd);
	   var hour = Math.floor((s - day * dd) / hh);
	   var minute = Math.floor((s - day * dd - hour * hh) / mi);
	   var second = (s - day * dd - hour * hh - minute * mi);
	   var result = "";
       if(day > 0) {
    	   result = result + day+"天";
       }
       if(hour > 0) {
    	   result = result + hour+"小时";
       }
       if(minute > 0) {
    	   result = result + minute+"分";
       }
       if(second > 0) {
    	   result = result + second+"秒";
       }
	   return result;
	};
	
	/** 打开视频  */
	function openVideo(){
		var content = '<object type="application/x-shockwave-flash" data="http://player.youku.com/player.php/sid/XMTI4MDA2ODI3Ng==/v.swf" width="600px" height="420px" id="youku-player">'
			+ '<param name="allowFullScreen" value="true">'
			+ '<param name="allowScriptAccess" value="always">'
			+ '<param name="movie" value="http://player.youku.com/player.php/sid/XMTI4MDA2ODI3Ng==/v.swf">'
			+ '<param name="flashvars" value="imglogo=&amp;paid=0&amp;partnerId=0d3ce735f648f788&amp;isAutoPlay=true&amp;styleid=0">'
			+ '</object>';
			$.layer({
		    type: 1,
		    title: false,
		    area: ['auto', 'auto'],
		    border: [0], //去掉默认边框
		    shade: [0], //去掉遮罩
		    //closeBtn: [0, false], //去掉默认关闭按钮
		    //shift: 'center', //从左动画弹出
		    offset: ['20%',''], 
		    page: {
		        html: content
		    },
		    close : function(index){
				layer.close(index);
			}
		});
	}
	
	function enterCur(){
		window.open("${path}/curInController.html", "_self");
	}
	
	function toZhitongche(){
		window.open("${path}/zhitongche/${firstBorrow.id}.html", "_self");
	}
	
	//打开定期宝页面
	function openDingbao(borrowId){
		window.open("${path}/dingqibao/"+borrowId+".html"); 
	}
</SCRIPT>
</html>
