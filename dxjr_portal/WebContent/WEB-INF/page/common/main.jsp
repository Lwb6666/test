<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/page/common/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>顶玺金融 </title>
<%@ include file="/WEB-INF/page/common/public3.jsp" %>
<meta name="keywords" content="顶玺金融,消费金融,互联网金融,投资理财" />
<meta name="description" content="顶玺金融">
</head>
<body>
<!--春节后去掉 start-->
<!--顶部伸缩--> 
<style> 
.icon.icon-zs{ width:20px; height:20px; background-position:-220px -76px; display:inline-block; margin-right:5px;}
.header .topbar{ 
   position: inherit !important; 
} 
.navbar{ 
position: inherit !important;} 
.s-pic{width:100%; height:110px; display: none; background:url(${path}/images/v5/banner_s.jpg) #000 center 
} 
.b-pic{width:100%;height:500px; display:block ; background:url(${path}/images/v5/banner_b.jpg) center;} 

@media only screen and (min-width: 321px) and (max-width: 1024px){ 
.s-pic, .b-pic{ width:1100px; } 

} 
</style> 
<!--春节后去掉style-->
<%@ include file="/WEB-INF/page/common/header.jsp" %>
<div class="wrapperbanner" style="position:relative; margin-top:0 !important"><!--春节后去掉 end-->
	<div class="fullSlide">
        <div class="bd">
            <ul>
            	<c:forEach items="${slideshowVoList}" var="slideshow" varStatus= "idxSlide" >
            	<li style="background:url(${slideshow.sImage}) center no-repeat;"><a href="${empty slideshow.imageUrl?'javascript:;':slideshow.imageUrl}" target="_blank"></a></li>
            	</c:forEach>
            </ul>
        </div>
        <div class="hd"><ul></ul></div>
        <span class="prev"></span>
        <span class="next"></span>
	</div><!--fullSlide end-->
    <div class="head-login-grid" style="width:0px;"  >
        <div class="head-login center" style="position: absolute;top: 10px;left: 250px;"  >
            <div id="" class="login">
            	<shiro:notAuthenticated>
	                <h2>顶玺预期利率</h2>
	                <h1><strong>10</strong>%<strong>~15</strong>%</h1>
	                <h3><span>40倍</span><span style="color:#fff; padding-left:5px;">银行活期存款利息！</span></h3>
	                <button onclick="window.location='${path }/member/toRegisterPage.html';" type="button" class="btn btn-red"><span class="icon icon-money"></span>注册抽大奖</button>
	                <h6><span>已有账号？</span><a href="${path }/member/toLoginPage.html?backUrl=1">立即登录</a></h6>
                </shiro:notAuthenticated>
            	<shiro:authenticated>
	            	<div class="lg-user">
	                 	<a href="${path }/myaccount/toIndex.html"><img src="${path }${portal:currentUser().headImg }"/></a>
	                </div>
	                <h3 style="padding-top:20px;"><span style="color:#fff; font-size:14px;">您好,</span><span style="color:#fa4810; font-size:14px;padding-left:5px;">${portal:currentUser().userName }</span></h3>
	                <h3 style="padding-top:10px;"><span style="color:#fff; padding-left:5px;font-size:14px;">欢迎来到顶玺金融</span></h3>
	                <a href="${path }/myaccount/toIndex.html"><button type="button" class="btn btn-red"><span class=""></span>我的账户</button></a>
            	</shiro:authenticated>
            </div>
        </div>
    </div>
    <!--登录end-->
</div>
<div  class="head-money">
	<div class="grid-1100">
    	<h4>
        	<div class="w240 fl">成交金额：<strong><fmt:formatNumber value="${TotalMoney }" pattern="#,##0"/></strong><small class="f14 gary2">万元</small></div>
            <div class="w240 fl">风险备用金额：<strong><fmt:formatNumber value="${RiskMargin }" pattern="#,##0"/></strong><small class="f14 gary2">万元</small></div>
            <div class="w240 fl">昨日成交：<strong><fmt:formatNumber value="${yestDeal/10000 }" pattern="#,##0"/></strong><small class="f14 gary2">万元</small></div>
            <div class="w240 fl">聪明投资人：<strong><fmt:formatNumber value="${regCount }" pattern="#,##0"/></strong><small class="f14 gary2">人</small></div>
        </h4>
    </div>
</div>
<div class="main-security">
	<div class="security grid-1100">
    	<ul>
        	<li><a href="${path}/bangzhu.html"><span class="icon icon-an fl"></span><h4><span>专业</span>自主研发风控系统</h4></a></li>
            <li><a href="${path}/bangzhu.html"><span class="icon icon-safe fl"></span><h4><span>安全</span>第三方监管</h4><h4>交易数据受第三方实时监控</h4></a></li>
            <li><a href="${path}/bangzhu.html"><span class="icon icon-trap fl"></span><h4><span>透明</span>全面数据披露</h4></a></li>
            <li onclick="openVideo()" style="border-right:none;cursor: pointer;"><span class="icon icon-rideo fl"></span><h4><span style="padding-top:20px;">一分钟了解顶玺金融</span></h4></li>
        </ul>
    </div>
</div>
<!-- 新手专享宝  start -->
<div class="product-wrap clearfix">
    <div class="newbao">
    	<div class="newbaotit f24">新手专享宝</div>
        <div class="newbao-con clearfix">
             <div class="listd">
                <p class=" mt9 f20 gary2">每日不定时开放</p>
                <p class="mt5 gary3">ID: ${fixBorrowNew.contractNo }</p>
              </div>
             <div class="listd ml10">
                <p><span class="f36 oren"><fmt:formatNumber value="${fixBorrowNew.apr}" pattern="#,##0.#" /></span>%</p>
                <p class="mt10"><span class="f25 blue">预期收益</span></p>
              </div>
             <div class="listd ml10">
                <p><span class="f36 oren">${fixBorrowNew.lockLimit}</span>个月</p>
                <p class="mt10"><span class="f25 blue">期限</span></p>
              </div>
             <div class="listd">
                <p>
                	<dl class="newbaoline">
                        <dd class="barline" style="width:212px" title="${fixBorrowNew.scheduleStrNoDecimal}%">
                            <div w="${fixBorrowNew.scheduleStrNoDecimal}" style="width:0px;" class="charts"></div>
                        </dd>
                    </dl>
                </p>
                <p class="mt21"><span class="f25 blue">进度:${fixBorrowNew.scheduleStrNoDecimal}%</span></p>
              </div>
              <div class="listd">
                <p class="f24 oren mt5"><fmt:formatNumber value="${fixBorrowNew.planAccount }" pattern="#,##0.##"/>元</p>
                <p class="mt9"><span class="f25 blue">开放金额</span></p>
              </div>
              <div class="listd btn-box">
              	<a href="${path}/dingqibao/${fixBorrowNew.id}.html" class="btn btn-primary mt9" style=" margin-left:50px">
					<c:if test="${fixBorrowNew.status==3 and fixBorrowNew.flagJoin==1}">立即加入</c:if>
					<c:if test="${fixBorrowNew.status==3 and fixBorrowNew.flagJoin==2}">敬请期待</c:if>
					<c:if test="${fixBorrowNew.status==5}">收益中</c:if>
					<c:if test="${empty fixBorrowNew}">敬请期待</c:if>
				</a>
              </div>
        </div>
        <div class="newbao-tequan">
        	<div class="lefttit f32">三大特权</div>
            <div class="tequan">
            	<span style="padding-left:50px; margin-top:18px; font-size:18px; color:#999; display:inline-block;">专享<span class="f24 oren">15%</span>超高收益</span>
            	<span style="padding-left:120px; margin-top:18px; font-size:18px; color:#999; display:inline-block;"><span class="f24 oren">1080元</span>新手红包 </span>
            	<span style="padding-left:125px; margin-top:0px; font-size:18px; color:#999; display:inline-block;"><span class="oren">(100%中奖)</span><p>抽奖机会一次</p> </span>
            
            </div>
        </div>
    </div>
</div>
<!--定期宝star-->
<div class="product-wrap clearfix">
	<div class="grid-1100">
    	<div class="gc-col-l center">
        	<div class="new-bar2">
                <div class="gc-img-wap text-new2"></div>
            	<div class="gc-img-wap picinfo2"></div>
                <div class="time"><span style="color:#fff;">每日9、11、13、15、17点定时开放</span></div>
            </div>
         </div>
         <div class="new-m">
         	<div class="title item-bd-b" style="padding:12px 20px 8px;"><a href="${path }/dingqibao.html" class="fr bule" style="cursor: pointer;">更多&gt;&gt;</a><em class="icon icon-time"></em>定期宝</div>
        	<ul class="huoqb">
        		<c:forEach items="${fixList }" var="f" varStatus="n">
            	<li>
                	<div class="new-product2">
                		<div class="item-cf">
                        	<div class="fl item-from-l"  >
                                <div class="item-box" >
                                	<div>
                                        <span class="lefttitle-in f14">预期收益</span>
                                        <span class="righttitle-in">
                                            <p class="item-szl ">${n.count+7 }</p>
                                            <p class="item-szr f18 mal10">%</p>
                                            <p class="item-baifen">+<fmt:formatNumber value="${f.apr-(n.count+7) }" pattern="#.##"/>%</p>
                                        </span> 
                                    </div>
                                </div>
                            </div>
                            <div class="votebox fl item-from-m">
                            	<h5 >项目期限<strong>${f.lockLimit}</strong>个月 </h5>
                                <dl class="barbox">
                                    <dd class="barline" style="width:160px">
                                        <div w="${f.scheduleStrNoDecimal}" style="width:0px;" class="charts"></div>
                                    </dd>
                                </dl><span>${f.scheduleStrNoDecimal}%</span>
                                <h5 class="clearfix">剩余可投余额：<strong class="pdl"><fmt:formatNumber value='${f.planAccount-f.accountYes}' pattern="###,###.##" /></strong>元</h5>
                            </div>
                            <div class="fr item-from-r  textr" style="position:relative;">
                            	<!-- 当前时间 -->
								<input id="nowTime" type="hidden"  value="${nowTime}" />
                            	<c:if test="${f.statusId == 2}">
                            	<input id="joinStatus${f.lockLimit}" type="hidden"  value="${f.joinStatus}"/>
                            	<input id="publishTime${f.lockLimit}" type="hidden" value="<fmt:formatDate value="${f.publishTime}" pattern="yyyy/MM/dd HH:mm:ss"/>">
                            	<div style="position:absolute;top:10px;right:0;width:190px;"><h6 class="gary2"><span>距离发布还有</span><span class="oren" id="remainingTime${f.lockLimit}">&nbsp;&nbsp;</span></h6></div>
                            	</c:if>
                            	<div class="btn-box pdtop30">
                           		<c:if test="${f.statusId == 1}">
                           			<a href="${path}/dingqibao/${f.id}.html" class="btn btn-primary" style="cursor: pointer;">立即加入</a>
                           		</c:if>
                           		<c:if test="${f.statusId == 2}">
                           			<a href="${path}/dingqibao/${f.id}.html" class="btn btn-jqqq" style="cursor: pointer;">敬请期待</a>
                           		</c:if>
                           		<c:if test="${f.statusId == 3}">
                           			<a href="${path}/dingqibao/${f.id}.html" class="btn btn-gcsyz" style="cursor: pointer;">收益中</a>
                           		</c:if> 
                           		<c:if test="${f.statusId == 4}">
                           			<a href="${path}/dingqibao/${f.id}.html" class="btn btn-jqqq" style="cursor: pointer;">已退出</a>
                           		</c:if>
                           		</div>
                            </div>
                        </div>
            		</div>
                </li>
                </c:forEach>
              </ul>
        </div>
        <div class="new-right">
            <div class="notice-home">
                <h5><a href="${path }/gonggao.html" class="fr f12 bule" style="cursor: pointer;">更多>></a>顶玺公告</h5>
                <ul>
                	<c:forEach items="${noticePage.result}" var="n" >
                    <li><a href="${path}/gonggao/${n.id}.html" title="${n.title }">${fn:substring(n.title,0,16)}<c:if test="${fn:length(n.title)>16}">..</c:if></a></li>
                    </c:forEach>
                </ul>
            </div>
            <div class="new-product4 mt9" style=" background:#fff; padding:10px 20px 20px 20px;">
            			<div class="title">
                        	<p><span class="fr">灵活又便捷</span>活期宝</p>
                        </div>
                		<div class="item-cf clearfix">
                        	<div class="fl" style=" width:60%;">
                   			  <div class="item-box " >
                                	<div  >
                                        <span class="lefttitle-in f14">预期收益</span>
                                        <span class="righttitle-in  " >
                                            <p class="item-szl " style=" width:40%;">5.6</p>
                                            <p class="item-szr f18">%</p>
                                        </span> 
                                    </div>
                                </div>  
                            </div>
                            <div class="fr">
                            	<div class="item-box">
                                	<h6> <strong>1</strong>元起投，低门槛</h6> 
                                </div>
                            </div>
                        </div>
                        <div  class="item-cf clearfix" style="padding-top:20px;  ">
                            <div class="fl item-box">每日复利，随存随取</div>
                            <div class="fr">
                            	<div class="btn-box">
                                	<a href="${path }/curInController.html" class="btn btn-primary">立即加入</a>
                                </div>
                            </div>
                        </div>
            		</div>
                    <div class="glue"> <a class="" href="${bbsPath }/ext/hqbGonglue.html" style="text-decoration:none;" target="_blank">活期宝投资攻略>></a></div>
        </div>
    </div>
</div>
<!--定期宝end-->

<!--债权star-->
<div class="product-wrap clearfix">
	<div class="grid-1100">
    	<div class="gc-col-l center">
        	<div class="new-bar3">
                <div class="gc-img-wap text-new3"></div>
            	<div class="gc-img-wap picinfo"></div>
                <div class="time"><a href="${path }/toubiao.html" style="cursor: pointer;">查看更多>></a></div>
            </div>
         </div>
         <div class="gc-col-m">
         	<table class="table  tbtr">
		    <thead>
		        <tr>
		          <td>借款标题</td>
		          <td>发布时间</td>
		          <td align="right">金额</td>
		          <td>预期利率</td>
		          <td>期限</td>
		          <td>还款方式</td>
		          <td align="right">进度</td>
		          <td>&nbsp;</td>
		        </tr>
		    </thead>
		    <tbody>
		    	<c:forEach items="${mixedBorrowList.result}" var="b" varStatus="idx" end="7" >
		        <tr onclick="window.location='${path}/toubiao/${b.id}.html';" style="cursor: pointer;">
		          <td align="left">
		           <c:if test="${b.isCustody==1 }">
		           <span class="icon icon-zs"></span>
		          </c:if>
		          	<span class="icon 
			          	<c:if test="${b.borrowtype==1 }">icon-xin</c:if>
			          	<c:if test="${b.borrowtype==2 }">icon-di</c:if>
	                	<c:if test="${b.borrowtype==5 }">icon-bao</c:if> pdr">
	                </span>
	                  <c:if test="${b.isCustody==1 }">
		             	${fn:substring(b.subName,0,2)}<c:if test="${fn:length(b.subName)>2}">..</c:if>
		          	 </c:if>
		          	  <c:if test="${b.isCustody!=1 }">
		             	${fn:substring(b.subName,0,4)}<c:if test="${fn:length(b.subName)>4}">..</c:if>
		          	 </c:if>
          		  </td>
		          <td>
		          <c:if test="${b.status==1}">
		           <fmt:parseDate value="${b.timingBorrowTimeStr}" pattern="yyyy-MM-dd HH:mm:ss" var="publishDate"></fmt:parseDate>
          		   <fmt:formatDate value="${publishDate}" pattern="MM-dd HH:mm" ></fmt:formatDate>
		          </c:if>
		          <c:if test="${b.status!=1}">
		           <fmt:parseDate value="${b.publishTimeStr}" pattern="yyyy-MM-dd HH:mm:ss" var="publishDate"></fmt:parseDate>
          		   <fmt:formatDate value="${publishDate}" pattern="MM-dd HH:mm" ></fmt:formatDate>
		          </c:if>
		          </td>
		          <td align="right"><fmt:formatNumber value="${b.account}" pattern="#,##0.00" />元</td>
		          <td><fmt:formatNumber value="${b.apr}" pattern="#,##0.##"/>%</td>
		          <td>${b.timeLimit }个月</td>
		          <td>
						<c:choose>
		                    <c:when test="${b.style == 0}">
								没有限制     
							</c:when>   
							<c:when test="${b.style == 1}">
								等额本息
							</c:when>   
							<c:when test="${b.style == 2}">
								<a href="javascript:void(0);" style="color: #333;" title="按月付息到期还本">按月付息</a> 
							</c:when>
							<c:when test="${b.style == 3}">
								<a href="javascript:void(0);"  style="color: #333;" title="到期还本付息">到期还本</a> 
							</c:when>
							<c:when test="${b.style == 4}">
								按天还款   
							</c:when>
							<c:otherwise></c:otherwise> 
						</c:choose> 
				  </td>
		          <td>
		          	<div class="votebox fr" style="width:40px; overflow:hidden">
		                <%-- <dl class="barbox fl">
		                    <dd class="barline"  style="width:60px;">
		                        <div w="${b.scheduleStrNoDecimal}" style="width:0px;" class="charts"></div>
		                    </dd>   
		                </dl> --%><span class="per fr">${b.scheduleStrNoDecimal}%</span>
		          	</div>
		          </td>
		          <td>
		          	<c:if test="${b.status==1}">
						<button type="button" class=" btn-small btn-yfz">预发中</button>
					</c:if>
					<c:if test="${b.status==42}">
						<button type="button" class=" btn-small btn-ywc">已垫付</button>
					</c:if>
					<c:if test="${b.status==2 }">
						<button type="button" class=" btn-small btn-blue">${portal:desc(100, b.status)}</button>
					</c:if>
					<c:if test="${b.status==4 }">
						<button type="button" class=" btn-small btn-gcsyz">${portal:desc(100, b.status)}</button>
					</c:if>
					<c:if test="${b.status==5 }">
						<button type="button" class=" btn-small btn-ywc">${portal:desc(100, b.status)}</button>
					</c:if>
					<c:if test="${b.status==6 }">
						<button type="button" class=" btn-small btn-gcsyz"><span style="font-size:12px;">银行复审中</span></button>
					</c:if>
		          </td>
		        </tr>
		        </c:forEach>
			</tbody>
			</table>
		</div>
	</div>
</div>
<!--顶玺资讯star-->
<div class="product-wrap clearfix">
	<div class="grid-1100 information">
    	<h4 class="f14">顶玺动态</h4>
    	<%-- 
    	<div class="notice-gc fl">
        	<h5 class="f12 gary bold"><a href="${path }/gonggao.html" class="fr f12">更多>></a>顶玺公告</h5>
        	<ul>
        		<c:forEach items="${noticePage.result}" var="n" end="5">
					<li><a href="${path}/gonggao/${n.id}.html" title="${n.title }">${fn:substring(n.title,0,15)}<c:if test="${fn:length(n.title)>15}">..</c:if></a></li>
				</c:forEach>
            </ul>
        </div>
        --%>
        <div class="comp-gc fl">
        	<%-- <h5 class="f12 gary bold">公司动态</h5>--%>
            <ul class="cmp">
            	<c:forEach items="${dongtaiList }" var="d" varStatus="n">
            	<li <c:if test="${n.count==4 }">style="margin-right:0px"</c:if>>
            		<a href="${d.imageUrl}" target="_blank" title="${d.title }" style="cursor: pointer;"><div class="out-img">
            		<c:if test="${d.sImage=='http://www.dxjr.com//attached/image/portal/20160128182534.jpg' }">
            			<img class="img-radius" src="${d.sImage}" width="222px" alt="个人投资产品"/>
            		</c:if>
            		<c:if test="${d.sImage=='http://www.dxjr.com//attached/image/portal/20160128182505.jpg' }">
            			<img class="img-radius" src="${d.sImage}" width="222px" alt="个人投资理财"/>
            		</c:if>
            		<c:if test="${d.sImage=='http://www.dxjr.com//attached/image/portal/20160128182451.jpg' }">
            			<img class="img-radius" src="${d.sImage}" width="222px" alt="房产抵押贷款"/>
            		</c:if>
            		<c:if test="${d.sImage!='http://www.dxjr.com//attached/image/portal/20160128182534.jpg' and d.sImage!='http://www.dxjr.com//attached/image/portal/20160128182505.jpg' and d.sImage!='http://www.dxjr.com//attached/image/portal/20160128182451.jpg'}">
            			<img class="img-radius" src="${d.sImage}" width="222px" alt="个人投资理财"/>
            		</c:if>            		
            		</div></a>
            		<h6 class="f14 lh30" title="${d.title }"><a href="${d.imageUrl}" target="_blank" class="sydt-text">${fn:substring(d.title,0,15)}<c:if test="${fn:length(d.title)>15}">..</c:if></a></h6>
            	</li>
           		</c:forEach>
            </ul>
        </div>
    </div>
</div>
<div class="product-wrap clearfix" style="*margin-bottom:20px;"><!--合作伙伴star-->
	<div class="grid-1100 medpeo">
    	<h5 class="f14 textl">合作伙伴</h5>
        <div class="bank-link2">
        	<div class="swap_pic">
                <div class="box">
                    <ul class="pics" id="pics">
                        <li>
                        	<a  class="bank-img1"  href="http://www.touzhijia.cn/"  rel="nofollow" title="投之家" target="_blank"></a>
							<a  class="bank-img2"  href="http://www.cmbchina.com/" rel="nofollow" title="招商银行" target="_blank"></a>
							<a  class="bank-img3"  href="http://bank.pingan.com/index.shtml" rel="nofollow" title="平安银行" target="_blank"></a>
							<a  class="bank-img4"  href="http://www.chinabank.com.cn/" rel="nofollow" title="京东支付" target="_blank"></a>
							<a  class="bank-img6"  href="http://www.lianlianpay.com/"  rel="nofollow" title="连连支付" target="_blank"></a>
							<a  class="bank-img5"  href="https://www.weicaifu.com/"  rel="nofollow" title="微财富" target="_blank"></a>
							<a  class="bank-img7"  href="http://www.yicai.com/"  rel="nofollow" title="第一财经" target="_blank"></a>
                        </li>
                    </ul>
                </div>
			</div>
        </div>
    </div>
</div>
<%@ include file="/WEB-INF/page/common/footer.jsp" %>
</body>
<script type="text/javascript">

/**秒数*/
var SysSecond;
/**循环计算倒计时*/
var InterValObj;
 $(function() {
     daoJiShi(1);
	 daoJiShi(3);
	 daoJiShi(6); 
	 daoJiShi(12);
}); 
 
 function daoJiShi(id){
	    var borrowStatus = $("#joinStatus"+id).val();
		var nowTime = $("#nowTime").val();
		/**预发标,发标剩余时间*/
		if (borrowStatus == "0") {
			var publishTime = $("#publishTime"+id).val();
			//这里获取倒计时的起始时间 
			SysSecond = parseFloat(new Date(publishTime).getTime() / 1000)
					- parseFloat(new Date(nowTime).getTime() / 1000);
			//SysSecond = parseInt((new Date('<fmt:formatDate value="${firstBorrow.endTime}" pattern="yyyy/MM/dd HH:mm:ss"/>').getTime() / 1000) - new Date('${nowTime}').getTime() / 1000); //这里获取倒计时的起始时间 
			//剩余时间  IE不兼容SetRemainTime（id）
			if(id==1){
				InterValObj = window.setInterval(SetRemainTime1, 1000); //间隔函数，1秒执行
			}
			if(id==3){
				InterValObj = window.setInterval(SetRemainTime3, 1000); //间隔函数，1秒执行
			}
			if(id==6){
				InterValObj = window.setInterval(SetRemainTime6, 1000); //间隔函数，1秒执行
			}
			if(id==12){
				InterValObj = window.setInterval(SetRemainTime12, 1000); //间隔函数，1秒执行
			}
		}
 }
 
/**
 * 倒计时
 */
function SetRemainTime1() {
	if (SysSecond > 0) {
		SysSecond = SysSecond - 1;
		var second = Math.floor(SysSecond % 60); // 计算秒     
		var minute = Math.floor((SysSecond / 60) % 60); //计算分 
		var hour = Math.floor((SysSecond / 3600) % 24); //计算小时 
		var day = Math.floor((SysSecond / 3600) / 24); //计算天 
		if (day > 0) {
			$("#remainingTime1").html(
					 day + '天' + hour
							+ '时' + minute
							+ '分' + second + '秒');

		}else if (hour > 0) {
			$("#remainingTime1").html(
					 hour
							+ '时' + minute
							+ '分' + second + '秒');

		}else if (minute > 0) {
			$("#remainingTime1").html(
					 minute
							+ '分' + second + '秒');

		} else {
			$("#remainingTime1").html(
					 second + '秒');
		}

	} else {//剩余时间小于或等于0的时候，就停止间隔函数 
		window.clearInterval(InterValObj);
		//这里可以添加倒计时时间为0后需要执行的事件 
		//$("#remainingTime").html("0天0时0分0秒");
		window.location.reload();
	}
}
/**
 * 倒计时
 */
function SetRemainTime3() {
	if (SysSecond > 0) {
		SysSecond = SysSecond - 1;
		var second = Math.floor(SysSecond % 60); // 计算秒     
		var minute = Math.floor((SysSecond / 60) % 60); //计算分 
		var hour = Math.floor((SysSecond / 3600) % 24); //计算小时 
		var day = Math.floor((SysSecond / 3600) / 24); //计算天 
		if (day > 0) {
			$("#remainingTime3").html(
					 day + '天' + hour
							+ '时' + minute
							+ '分' + second + '秒');

		}else if (hour > 0) {
			$("#remainingTime3").html(
					 hour
							+ '时' + minute
							+ '分' + second + '秒');

		}else if (minute > 0) {
			$("#remainingTime3").html(
					 minute
							+ '分' + second + '秒');

		} else {
			$("#remainingTime3").html(
					 second + '秒');
		}

	} else {//剩余时间小于或等于0的时候，就停止间隔函数 
		window.clearInterval(InterValObj);
		//这里可以添加倒计时时间为0后需要执行的事件 
		//$("#remainingTime").html("0天0时0分0秒");
		window.location.reload();
	}
}
/**
 * 倒计时
 */
function SetRemainTime6() {
	if (SysSecond > 0) {
		SysSecond = SysSecond - 1;
		var second = Math.floor(SysSecond % 60); // 计算秒     
		var minute = Math.floor((SysSecond / 60) % 60); //计算分 
		var hour = Math.floor((SysSecond / 3600) % 24); //计算小时 
		var day = Math.floor((SysSecond / 3600) / 24); //计算天 
		if (day > 0) {
			$("#remainingTime6").html(
					 day + '天' + hour
							+ '时' + minute
							+ '分' + second + '秒');

		}else if (hour > 0) {
			$("#remainingTime6").html(
					 hour
							+ '时' + minute
							+ '分' + second + '秒');

		}else if (minute > 0) {
			$("#remainingTime6").html(
					 minute
							+ '分' + second + '秒');

		} else {
			$("#remainingTime6").html(
					 second + '秒');
		}

	} else {//剩余时间小于或等于0的时候，就停止间隔函数 
		window.clearInterval(InterValObj);
		//这里可以添加倒计时时间为0后需要执行的事件 
		//$("#remainingTime").html("0天0时0分0秒");
		window.location.reload();
	}
}
/**
 * 倒计时
 */
function SetRemainTime12() {
	if (SysSecond > 0) {
		SysSecond = SysSecond - 1;
		var second = Math.floor(SysSecond % 60); // 计算秒     
		var minute = Math.floor((SysSecond / 60) % 60); //计算分 
		var hour = Math.floor((SysSecond / 3600) % 24); //计算小时 
		var day = Math.floor((SysSecond / 3600) / 24); //计算天 
		if (day > 0) {
			$("#remainingTime12").html(
					 day + '天' + hour
							+ '时' + minute
							+ '分' + second + '秒');

		}else if (hour > 0) {
			$("#remainingTime12").html(
					 hour
							+ '时' + minute
							+ '分' + second + '秒');

		}else if (minute > 0) {
			$("#remainingTime12").html(
					 minute
							+ '分' + second + '秒');

		} else {
			$("#remainingTime12").html(
					 second + '秒');
		}

	} else {//剩余时间小于或等于0的时候，就停止间隔函数 
		window.clearInterval(InterValObj);
		//这里可以添加倒计时时间为0后需要执行的事件 
		//$("#remainingTime").html("0天0时0分0秒");
		window.location.reload();
	}
}
</script>