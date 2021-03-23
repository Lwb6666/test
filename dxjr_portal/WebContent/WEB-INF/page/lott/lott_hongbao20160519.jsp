<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/page/common/taglib.jsp"%>


<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ include file="/WEB-INF/page/common/public.jsp"%>
<title>我的红包</title>
<script type="text/javascript"> 
$(document).ready(function(){ 
    $("tr.parent").click(function(){ //显示隐藏表格
        $(this).siblings(".child-"+this.id).toggle(); 
        }); 
    $("tr[class^=child]").toggle();
	
	  $('li.ordinary-hb').mousemove(function(){//红包显示提示
		$(this).find(".hbts-box").addClass("show");
		$(this).find('.reward-btn').show();

		//
	  });
	  $('li.ordinary-hb').mouseleave(function(){
		$(this).find('.hbts-box').removeClass("show");
		$(this).find('.reward-btn').hide();
	  	//$('.hbtishi-box').removeClass("show");
				//alert(".hbtishi-box")
	  });
		$(".reward-btn2 a").mousemove(function(){
			$(this).next(".hbxl-box").show();
			$(".hbts-box").addClass("hide");
			//$(this).nextUntil('.hbts-box').addClass("hide");
			});
		$(".reward-btn a").mousemove(function(){
			$(".hbts-box").addClass("hide");
			//$(this).nextUntil('.hbts-box').addClass("hide");
			});
		$(".reward-btn a").mouseleave(function(){
			$(".hbts-box").removeClass("hide");
			//$(this).nextUntil('.hbts-box').addClass("hide");
			});
	  $(".reward-btn2").mouseleave(function(){
			$(this).find(".hbxl-box").hide();
			$(".hbts-box").removeClass("hide");
			});
	$('li.ordinary-history').mousemove(function(){//历史红包显示提示
		$(this).find('.hbts-boxgrey').show();
	  });
	$('li.ordinary-history').mouseleave(function(){//历史红包显示提示
		$(this).find('.hbts-boxgrey').hide();
	  });
 
 }); 
var flag=true;
$('#hb').click(function (){
	if(flag){
		 $('.historyhg').slideDown("fast");
		 $('#hb').removeClass("hb-xiala");
		 $('#hb').addClass("hb-xiala1");
		 $('.yema').css('display','block');
		 flag=false;
	}else{
		 $('.historyhg').slideUp("fast");
		 $('#hb').removeClass("hb-xiala1");
		 $('#hb').addClass("hb-xiala");	
		 $('.yema').css('display','none');
		 flag=true;	
	}
});

function findPage(pageNo) {
	 window.parent.lott_hongbao_pageParent(pageNo);
}
 

</script>
</head>
<!--我的账户右侧  資金管理  提現明細-->
<div class="searchbox fl whitebg"  >
					 
					<div class="searchbox fl whitebg"></div>
					<div class="myid whitebg nobordertop" style="padding-bottom: 50px;">

						<div class="hbtitle">
							<div class="box-l">我的可用红包</div>
							<div class="hbtitle-l">
								<a href="${basePath }/activity/redActivity.html" target="_blank" style="color: #fc7844;">红包攻略</a>
							</div>
							<div class="hbtitle-r" style="padding-top: 3px;">
								<a href="${basePath}/sycee.html">元宝换红包</a>
							</div>
						</div>
						<div class="rewardbigbox">
                            <c:forEach items="${openRedAccounts}" var="red" varStatus="i">
	                            <ul>
	                             <c:if test="${red.redType==1930}">
	                                <c:if test="${red.status==1}">
	                                     <li class="ordinary-hb  ordinary-hbtq">
	                                     	<div class="money-top">${red.money}<span>元</span></div>
	                                     	<div class="reward-btn2"><a href="${basePath}/myaccount/friend/friendList/1.html?redId=${red.id}">我要推荐</a></div>
	                                        <div class="hbts-box">
	                                       		<span class="org_bot_cor_2"> </span>
	                                       		<p><span> 到期日期：</span><fmt:formatDate value="${red.endTime}" type="date" dateStyle="default"/></p>
	                                            <p><span> 红包来源：</span>${red.redSource }</p>
	                                            <p><span> 开启条件：</span>推荐一位新用户注册且新用户在30天内投资满￥1000元</p>
               							    </div>
	                                     </li>
									</c:if>
									<c:if test="${red.status==2}">
									     <li class="ordinary-hb ordinary-hbtq">
                                              <div class="money-top">${red.money}<span>元</span></div>
                                              <div class="reward-btn2"><a href="${basePath}/dingqibao.html;">立即使用</a> </div>
					                          <div class="hbts-box">
					                                <span class="org_bot_cor_2"></span>
								                	<p><span> 到期日期：</span><fmt:formatDate value="${red.endTime}" type="date" dateStyle="default"/></p>
								                	<p><span> 红包来源：</span><span>${red.redSource }</span></p>
								                <p><span> 使用条件：</span>
												<span>单笔投资满${red.money*100 }元的定期宝可用</span></p>
					                          </div>
					                      </li>
									</c:if>
								</c:if>
								<c:if test="${red.redType!=1930}">
									<c:if test="${red.status==2}">
									     <li class="ordinary-hb">
                                              <div class="money-top">${red.money}<span>元</span></div>
                                              <div class="reward-btn"><a href="${basePath}/dingqibao.html;">立即使用</a> </div>
					                          <div class="hbts-box">
					                          		<span class="org_bot_cor_2"> </span>
								                	<p><span> 到期日期：</span><fmt:formatDate value="${red.endTime}" type="date" dateStyle="default"/></p>
								                	<p><span> 红包来源：</span>
								                	 <c:choose>
												     <c:when test="${red.redType==1980 && red.money=='10.00'}">
												       <span>推荐成功奖励（1000元档）</span>
												     </c:when>
												      <c:when test="${red.redType==1980 && (red.money=='30.00' ||red.money=='20.00' )}">
												      <span>推荐成功奖励（10000元档）</span>
												     </c:when>
												      <c:when test="${red.redType==1970 && red.money=='20.00'}">
												       <span>注册成功奖励</span>
												      </c:when>
												       <c:when test="${red.redType==1970 && (red.money=='30.00' || red.money=='50.00')}">
												       <span>首投1000奖励</span>
												     </c:when>
												     <c:otherwise>
												     	<span>${red.redSource }</span>
												     </c:otherwise>
												 </c:choose>
												</p>
								                <p><span> 使用条件：</span>
												<c:if test="${red.redType==1980}">
													<span>单笔投资满${red.money*100 }元的定期宝可用</span>
												 </c:if>
												<c:if test="${red.redType==1910}">
													<span>单笔投资满${red.money*100 }元的6月宝、12月宝可用</span>
												 </c:if>
												<c:if test="${red.redType==1950}">
												   <c:if test="${red.money=='10.00'}">
													<span>单笔投资满${red.money*100 }元的定期宝可用</span>
												   </c:if>
												  <c:if test="${red.money=='20.00'}">
													<span>单笔投资满${red.money*100 }元的3月宝、6月宝、12月宝可用</span>
												   </c:if>
												   <c:if test="${red.money=='50.00'}">
													<span>单笔投资满${red.money*100 }元的6月宝、12月宝可用</span>
												   </c:if>
												 </c:if>
												<c:if test="${red.redType==1960}">
												   <c:if test="${red.money=='10.00'}">
													<span>单笔投资满${red.money*100 }元的定期宝可用</span>
												   </c:if>
												  <c:if test="${red.money=='20.00'}">
													<span>单笔投资满${red.money*100 }元的3月宝、6月宝、12月宝可用</span>
												   </c:if>
												   <c:if test="${red.money=='50.00' || red.money=='200.00'}">
													<span>单笔投资满${red.money*100 }元的6月宝、12月宝可用</span>
												   </c:if>
												 </c:if>
												 <c:if test="${red.redType==1970}">
												   <c:if test="${red.money=='20.00'}">
													<span>单笔投资满${red.money*100 }元的定期宝可用</span>
												   </c:if>
												  <c:if test="${red.money=='30.00'}">
													<span>单笔投资满${red.money*100 }元的3月宝、6月宝、12月宝可用</span>
												   </c:if>
												   <c:if test="${red.money=='50.00'}">
													<span>单笔投资满${red.money*100 }元的6月宝、12月宝可用</span>
												   </c:if>
												 </c:if>
												 <c:if test="${red.redType==1990}">
												   <c:if test="${red.money=='10.00'}">
													<span>单笔投资满${red.money*100 }元的定期宝可用</span>
												   </c:if>
												  <c:if test="${red.money=='50.00'}">
													<span>单笔投资满${red.money*100 }元的6月宝、12月宝可用</span>
												   </c:if>
												   <c:if test="${red.money=='200.00'}">
													<span>单笔投资满${red.money*100 }元的12月宝可用</span>
												   </c:if>
												 </c:if>
												 <c:if test="${red.redType==2000}">
												   <c:if test="${red.money=='30.00'}">
													<span>单笔投资满${red.money*100 }元的6月宝、12月宝可用</span>
												   </c:if>
												 </c:if>
								                </p>
					                          </div>
					                      </li>
									</c:if>
								</c:if>
								</ul>
                            </c:forEach>
							<div class="clear"></div>
						</div>
				        <div class="hbtitle" >
                         <div class="box-l">我的历史红包</div>
                         <div class="hbtitle-l"  ><a href="#" style="color:#fc7844;"> </a> </div>
                       	<div class="hbtitle-r" style="padding-top: 3px;">
							<div class="hb-xiala" id="hb" style="cursor: pointer;" > </div>
						</div>
                        </div>
						<div class="rewardbigbox  historyhg" style="display:none;padding-top:20px;">
							<ul>
							    <c:forEach items="${redList}" var="red">
							        <c:if test="${red.redType!=1930}">
							          <li class="ordinary-history">
							           <c:if test="${red.status==4}">
	                                      <div class="statebtn">已使用</div>
	                                   </c:if>
	                                    <c:if test="${red.status==5}">
	                                      <div class="statebtn">已过期</div>
	                                   </c:if>
	                                    <c:if test="${red.status==3}">
	                                      <div class="statebtn">已冻结</div>
	                                   </c:if>
	                                      <div class="money-top money-top1">${red.money}<span>元</span></div>
	                                     <!--  <div class="reward-btn"><a href="#"> </a></div> -->
	                                      <div class="hbts-boxgrey">
		                                      <span class="org_bot_cor_2"> </span>
		                	                  <p>
		                	                   <c:if test="${red.status==5}">
		                	                  <span> 过期时间：</span><fmt:formatDate value="${red.endTime}" type="both"/>
		                	                   </c:if>
		                	                   <c:if test="${red.status==4}">
		                	                      <span> 使用时间：</span><fmt:formatDate value="${red.useTime}" type="both"/>
		                	                   </c:if>
		                	                    <c:if test="${red.status==3}">
		                	                      <span> 冻结时间：</span><fmt:formatDate value="${red.freezeTime}" type="both"/>
		                	                   </c:if>
		                	                  </p>
		                	                  <p><span> 红包来源：</span>
		                	                   <c:choose>
												     <c:when test="${red.redType==1980 && red.money=='10.00'}">
												       <span>推荐成功奖励（1000元档）</span>
												     </c:when>
												    <c:when test="${red.redType==1980 && (red.money=='30.00' ||red.money=='20.00' )}">
												      <span>推荐成功奖励（10000元档）</span>
												     </c:when>
												      <c:when test="${red.redType==1970 && red.money=='20.00'}">
												       <span>注册成功奖励</span>
												      </c:when>
												       <c:when test="${red.redType==1970 && (red.money=='30.00' || red.money=='50.00')}">
												       <span>首投1000奖励</span>
												     </c:when>
												     <c:otherwise>
												     	<span>${red.redSource }</span>
												     </c:otherwise>
												 </c:choose>
		                	                  </p>
		                	              </div>
                                       </li>
									</c:if>
							       <c:if test="${red.redType==1930}">
							          <li class="ordinary-history ordinary-historytq">
							           <c:if test="${red.status==4}">
	                                      <div class="statebtn">已使用</div>
	                                   </c:if>
	                                    <c:if test="${red.status==5}">
	                                      <div class="statebtn">已过期</div>
	                                   </c:if>
	                                    <c:if test="${red.status==3}">
	                                      <div class="statebtn">已冻结</div>
	                                   </c:if>
	                                      <div class="money-top money-top1">${red.money}<span>元</span></div>
	                                     <!--  <div class="reward-btn"><a href="#"> </a></div> -->
	                                      <div class="hbts-boxgrey">
		                                      <span class="org_bot_cor_2"> </span>
		                	                   <p>
		                	                  <c:if test="${red.status==5}">
		                	                  <span> 过期时间：</span><fmt:formatDate value="${red.endTime}" type="both"/>
		                	                   </c:if>
		                	                   <c:if test="${red.status==4}">
		                	                      <span> 使用时间：</span><fmt:formatDate value="${red.useTime}" type="both"/>
		                	                   </c:if>
		                	                    <c:if test="${red.status==3}">
		                	                      <span> 冻结时间：</span><fmt:formatDate value="${red.freezeTime}" type="both"/>
		                	                   </c:if>
		                	                   </p>
		                	                  <p><span> 红包来源：</span>${red.redSource}</p>
		                	              </div>
                                       </li>
									</c:if>
                                 </c:forEach> 
							</ul>
						</div>
     <div class="yema" style="display:none;"> 
		<div class="pagebigbox"  style="clear:both">
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
		</div>
	</div>
        <div class="hbtishi">
            <p style="font-size:16px; background:url(images/icon-tishi.png) no-repeat 0px 10px; text-indent:25px; color:#009dd9; line-height:35px;"> 温馨提示：</p>
			<p>1、红包可用于投资定期宝时抵扣您一定金额的投资本金</p>
			<p>2、红包暂不支持除定期宝外的其它项目</p>
			<p>3、每个红包有最低投资金额要求，满足使用条件后方可使用</p>
			<p>4、每次投资只能使用一个红包，单个红包不可拆分使用</p>
			<p>5、如有任何疑问，请致电客服热线400-0156-676</p>
		</div>
	</div>
</div>
 			
				