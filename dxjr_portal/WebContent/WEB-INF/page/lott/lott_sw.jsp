<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/page/common/taglib.jsp"%>
<script type="text/javascript">
$(document).ready(function(){
var flag=true;
$('#hb').click(function (){
	if(flag){
		 $('.nobao').slideDown("fast");
		 $('.page').slideDown("fast");
		 $('.hb a').removeClass("arrowdown");
		 flag=false;
	}else{
		 $('.nobao').slideUp("fast");
		  $('.page').slideUp("fast");
		 $('.hb a').addClass("arrowdown");
		 flag=true;	
	}
});
		
});
function findPage(pageNo) {
	 window.parent.lott_sw_pageParent(pageNo);
}
</script>
</head>
<div class="reward change" style="clear: both">
	<div class="tbl-cont">
                    <h1>我的可用红包<a href="${basePath }/activity/redActivity.html" target="_blank" class="f12 mll0">红包攻略</a><a href="${basePath}/sycee.html" target="_blank" class="f12 mll0 fr">元宝换红包</a></h1>
                      <c:if test="${openRedSize>0}">
                      <c:forEach items="${openRedAccounts}" var="red" varStatus="i">
                        <c:if test="${i.index%4==0}"><ul class="redbao okbao"></c:if>
                            <c:if test="${red.redType!=1930}">
									<c:if test="${red.status==2}">
									    <li <c:if test="${i.index%4==3}">class='mr0'</c:if>>
                                              <p class="center f60 white">${red.moneyInt}<span class="f36">元</span></p>
                                              <p class="center mt20"><a href="${basePath}/dingqibao.html;">立即使用</a> </p>
					                          <p class="white mt30 f12">
								                                        到期日期：<fmt:formatDate value="${red.endTime}" type="date" dateStyle="default"/><br>
							                	 红包来源：${red.redSource }
											<br>
							                                         开启条件：${red.openCondition}
							                </p>
					                      </li>
									</c:if>
								</c:if>
								<c:if test="${red.redType==1930}">
									 <c:if test="${red.status==1}">
	                                      <li <c:if test="${i.index%4==3}">class='mr0'</c:if>>
	                                     	<p class="center f60 white">${red.moneyInt}<span class="f36">元</span></p>
	                                     	<p class="center mt20"><a href="${basePath}/lottery_use_record/lott_use_record.html?tabType=3&redId=${red.id}">我要推荐</a></p>
                                       		<p class="white mt30 f12"> 到期日期：<fmt:formatDate value="${red.endTime}" type="date" dateStyle="default"/></br>
                                                                                                               红包来源：${red.redSource }</br>
                                                                                                              开启条件：推荐一位新用户注册且新用户在30天内投资满￥1000元</p>
	                                     </li>
									</c:if>
									<c:if test="${red.status==2}">
									      <li <c:if test="${i.index%4==3}">class='mr0'</c:if>>
                                              <p class="center f60 white">${red.moneyInt}<span>元</span></p>
                                              <p class="center mt20"><a href="${basePath}/dingqibao.html">立即使用</a> </p>
								              <p class="white mt30 f12">到期日期：</span><fmt:formatDate value="${red.endTime}" type="date" dateStyle="default"/></br>
								                                    红包来源：${red.redSource }</br>
								                                   使用条件：${red.openCondition}</p>
					                      </li>
									</c:if>
								</c:if>
                        <c:if test="${i.index%4==3}"></ul></c:if>
                        </c:forEach>
                        <c:if test="${fn:length(openRedAccounts)%4!=0}"> </ul></c:if>
                        </c:if>
                        <c:if test="${openRedSize==0}">
                           <div class="zdtz-none"><p>暂无可用红包</p></div>
                        </c:if>
                    <div class="clearfix"></div>
                    <h1 class="hb">我的历史红包<a  class="f12 fr f18 iconfont arrowdown" id="hb">&#xe612;</a></h1>   
                       <c:if test="${totalCount>0}">   
                           <c:forEach items="${redList}" var="red" varStatus="j">
                            <c:if test="${j.index%4==0}"><ul class="redbao nobao"></c:if>
                            <c:if test="${red.status==5}">
									    <li <c:if test="${j.index%4==3}">class='mr0'</c:if>>
                                              <p class="center f60 white">${red.moneyInt}<span class="f36">元</span></p>
                                              <p class="center mt20"><a>已过期</a> </p>
					                          <p class="white mt30 f12">
								                                        过期日期：<fmt:formatDate value="${red.endTime}" pattern="yyyy-MM-dd"/></br>
							                	 红包来源：
											     	 ${red.redSource }
							                </p>
					                      </li>
									</c:if>
									  <c:if test="${red.status==3}">
									    <li <c:if test="${j.index%4==3}">class='mr0'</c:if>>
                                              <p class="center f60 white">${red.moneyInt}<span class="f36">元</span></p>
                                              <p class="center mt20"><a>已冻结</a> </p>
					                          <p class="white mt30 f12">
								                                        冻结日期：<fmt:formatDate value="${red.freezeTime}" pattern="yyyy-MM-dd"/></br>
							                	 红包来源：
											     	 ${red.redSource }
							                </p>
					                      </li>
									</c:if>
									  <c:if test="${red.status==4}">
									    <li <c:if test="${j.index%4==3}">class='mr0'</c:if>>
                                              <p class="center f60 white">${red.moneyInt}<span class="f36">元</span></p>
                                              <p class="center mt20"><a>已使用</a> </p>
					                          <p class="white mt30 f12">
								                                        使用日期：<fmt:formatDate value="${red.useTime}" pattern="yyyy-MM-dd"/></br>
							                	 红包来源：
											     	 ${red.redSource }
							                </p>
					                      </li>
									</c:if>
                             <c:if test="${j.index%4==3}"></ul></c:if>
                             </c:forEach>
                             <c:if test="${fn:length(redList)%4!=0}"> </ul></c:if>
                             </c:if>  
							<div class="page" style="display: none;">
						<c:if test="${page.result==null  || page.totalCount==0 }">
                             <div class="zdtz-none"><p>暂无历史红包</p></div>
                        </c:if>
                        <c:if test="${page.result !=null &&  page.totalCount >0 }">
								<jsp:include page="/WEB-INF/page/common/ajaxpage.jsp">
									<jsp:param name="pageNo" value="${page.pageNo}" />
									<jsp:param name="totalPage" value="${page.totalPage}" />
									<jsp:param name="hasPre" value="${page.hasPre}" />
									<jsp:param name="prePage" value="${page.prePage}" />
									<jsp:param name="hasNext" value="${page.hasNext}" />
									<jsp:param name="nextPage" value="${page.nextPage}" />
								</jsp:include>
						</c:if>
							</div>
			              <ul class="recharge-tip gray9 clearfix">
			                   	<li class="mr10 f16">温馨提示</li>
			                   	<li>1. 红包可用于投资定期宝时抵扣您一定金额的投资本金<br>
			                        2. 红包暂不支持除定期宝外的其它项目<br>
			                        3. 每个红包有最低投资金额要求，满足使用条件后方可使用<br>
			                        4. 每次投资只能使用一个红包，单个红包不可拆分使用<br>
			                        5. 如有任何疑问，请致电客服热线<span class="blue f18">400-0156-676</span>
			                    </li>
			             </ul>                 
                </div>
</div>
<script>
$(document).ready(function(){ 
    var color="#f0f7ff"
    $(".tbtr tr:even,.tbtr01 tr:even,.tbtr02 tr:even,.tbtr03 tr:even,.tbtr04 tr:even,.tbtr05 tr:even,.tbtr06 tr:even,.tbtr07 tr:even,.tbtr08 tr:even,.tbtr09 tr:even,.tbtr10 tr:even,.tbtr11 tr:even,.tbtr12 tr:even,.tbtr13 tr:even,.tbtr14 tr:even,.tbtr15 tr:even,.tbtr16 tr:even,.tbtr17 tr:even").attr("bgcolor",color);//改变奇数行背景色
})	
</script>