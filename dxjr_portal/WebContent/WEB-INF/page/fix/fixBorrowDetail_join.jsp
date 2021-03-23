<%@page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/page/common/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>顶玺金融官网</title>
<meta name="keywords" content="p2p网络投资理财" />
<meta name="description" content="顶玺金融">
<%@ include file="/WEB-INF/page/common/public2.jsp"%>
<script type="text/javascript" src="${basePath}/js/formatMoney.js?version=<%=version%>"></script>
</head>

<body>
<!--头部开始-->
<%@ include file="/WEB-INF/page/common/header.jsp"%>

<form action="" id="tenderFixBorrowForm">
<input id="publishTime" type="hidden" value="${fixBorrowVo.publishTime}">
<!-- 当前时间 -->
<input type="hidden" id="nowTime" value="<fmt:formatDate value="<%=new Date()%>" pattern="yyyy/MM/dd HH:mm:ss"/>" />
<!-- 标时间 -->
<input type="hidden" id="endTime" value="<fmt:formatDate value="${fixBorrowVo.endTime}" pattern="yyyy/MM/dd HH:mm:ss"/>" />
<div class="product-wrap wrapperbanner"><!--定期宝star-->
	<div class="grid-1100">
    	<div class="product-deatil clearfix">
        	<h1 class="f16">定期宝【${fixBorrowVo.lockLimit}月宝】${fixBorrowVo.contractNo}
        	<c:if test="${fixBorrowVo.areaType==1}"><img src="${basePath}/images/new-icon.png?version=<%=version%>"/></c:if>
        	</h1>
            <div class="detail-col-l fl item-bd-r">
            	<ul class="item-bd-b">
            		<li class="bdr percentbig"><span class="f14 gary line200">预期收益率</span><span class="oren precent"><fmt:formatNumber value="${fixBorrowVo.apr}" pattern="#,##0.##"/> <small class="f18 oren">%</small></span></li>
                	<li class="percentbig line36 word">
                    	<p><span>项目期限</span><label><strong>${fixBorrowVo.lockLimit}</strong>个月</label></p>
                        <p><span>开放金额 </span><label><strong><fmt:formatNumber value="${fixBorrowVo.planAccount}" pattern="#,##0.##" /></strong>元</label></p>
                        <div class="precent-detail"><span class="fl f14">加入进度</span>
                        <div class="clearboth">
                        <div class="votebox" style="width:220px; display:inline-block;">
                            <dl class="barbox" style="margin-top:18px;">
                                <dd class="barline"  style="width:140px;">
                                    <div w="${scheduleStrNoDecimal}" style="width:0px;" class="charts"></div>
                                </dd>   
                            </dl><span class="f14" style="padding-left:5px; color:#999;margin-top:8px;">${scheduleStrNoDecimal}%</span>
                         </div>
                     	</div>
                        </div>
                   </li>
                </ul>
                <ul class="detailword">
                	<li style="padding-right:268px;"><h6>保障方式&nbsp;&nbsp;&nbsp;&nbsp;<span class="bule">风险保证金保障</span></h6></li>
                	<li><h6><label id="remainingTime"></label></h6></li>
                </ul>
             </div>
             <div class="detail-col-r fr">
                <div class="detail-join-m">
                
                    <div class="clearfix pdt10"><span class="f14">剩余可投<strong class="oren f18 pdla">
	                    <fmt:formatNumber value="${fixBorrowVo.planAccount-fixBorrowVo.accountYes}" pattern="#,##0.##" />
						<input type="hidden" id="remainAccount" value="${fixBorrowVo.planAccount-fixBorrowVo.accountYes }">
						</strong>元 </span>
					</div>
					
                    <div class="clearfix pdt10 pd20">
	                    <shiro:authenticated>
	                    <a href="${path}/account/topup/toTopupIndex.html" class="fr bd">充值</a>
	                    <span class="f14 gary2">账户余额
	                    <strong class="f14 pdla gary2">
	                    <fmt:formatNumber value="${useMoney }" pattern="#,##0.##" />
	                    </strong>元</span>
	                    <input type="hidden" id="userId" value="hasEnter">	
						<input id="userRemainMoney" type="hidden" value="${userRemainMoney}" />	
	                    </shiro:authenticated> 
           
	                    <shiro:notAuthenticated>
	                    <span class="f14 gary2">账户余额<a href="${path}/member/toLoginPage.html?backUrl=1" class="pdla login">登录</a>后可见</span>
	                    <input type="hidden" id="userId" value="noEnter">
	                    </shiro:notAuthenticated>
                    </div>
                    
                    <div class="clearfix mt5"><span class="gary2 f14 fl">使用红包&nbsp;&nbsp;</span>
                       <c:if test="${empty reds or fixBorrowVo.areaType==1}">
                           <span class="gary2 f14 fl">&nbsp;&nbsp;&nbsp;无可用红包</span>
	                       <input type="hidden" name="redId" id="redId" value="0"/>
	                   </c:if>
	                    <c:if test="${not empty reds and fixBorrowVo.areaType!=1}"> 
	                        <c:forEach items="${reds }" var="r" begin="0" end="0">
			                   <input type="hidden" name="redId" id="redId" value="${r.id}"/>
			                </c:forEach>
		                 <span class="fl ml10"><dl class="select select-sl" >
		                          <c:forEach items="${reds }" var="r" begin="0" end="0">
                                        <c:choose>
                                          <c:when test="${minRedMoney*100<(fixBorrowVo.planAccount-fixBorrowVo.accountYes)}">
			                                <dt>
											<fmt:formatNumber value="${r.money }" pattern="###" />元 
											(满<fmt:formatNumber value="${r.money*100 }" pattern="###" />元可用)
											</dt>
											</c:when>
											<c:otherwise>
												<dt>不使用</dt>
											</c:otherwise>
										</c:choose>
								   </c:forEach>
				                <dd>
				                   <ul>
				                  	<c:forEach items="${reds }" var="r">
				                    <li><a href="javascript:setRedsValues(${r.id });">
				                    <fmt:formatNumber value="${r.money }" pattern="###" />元 
									(满<fmt:formatNumber value="${r.money*100 }" pattern="###" />元可用)
									</a>
									</li>
				                    </c:forEach>
				                    <li><a href="javascript:setRedsValues(0);">不使用</a></li>
				                  </ul>
				                </dd>
				              </dl>
				               <c:forEach items="${reds }" var="r"><input id="redHid${r.id }" type="hidden" value="${r.money*100 }" /></c:forEach>
		                 </span>
		                </c:if>
                   </div>
            
                    <shiro:authenticated>
                    <c:if test="${isExistCurAccount == true }">
                    <div class="clearfix pdt10"><input name="isUseCurMoney" id="isUseCurMoney" class="checkbox-btn" type="checkbox" value="0" onclick="useCurMoney();" checked="checked"><span class="gary2 f14 ">使用活期余额：<strong class="f14 pdla gary2"><fmt:formatNumber value="${maxCurMoney}"  pattern="#,##0.00" /></strong>元</span> </div>
                    </c:if>
                    </shiro:authenticated>
                    
                    <div class="clearfix pdt10">
                    <input type="hidden" id="effectiveTenderMoney" value="${effectiveTenderMoney }"/>
                    <span class="f14 pdra">加入金额</span><input id="tenderMoney" name="tenderMoney" class="form-inpyt-sm yuan2" style="width:160px;color:#999;" type="text" onblur="if(value==''){value='请输入加入金额';$('#tenderMoney').attr('style','color:#999;width:160px;');}" onfocus="if(value=='请输入加入金额'){value='';$('#tenderMoney').attr('style','color:#FFFFF;width:160px;'); }" value="请输入加入金额"/><a href="javascript:enterMomey()" style="padding:2px 0 0 10px;">全额加入</a> 
                    </div>
                    <div class="clearfix pdt38">
                    	<c:if test="${isToTender == true}">
                        <div class="btn-box2">
                            <button type="button" class="btn btn-join f18" onclick="validateToJionData()">立即加入</button>
                        </div>
                        </c:if>
                        <c:if test="${isToTender == false}">
                        <div class="btn-box">
                         	<a href="javascript:void(0);" class="btn btn-tmbg f18" style="width:100%; cursor:default">立即加入</a>
                    	</div>
                    	</c:if>
                     </div>
                </div>
             </div>
         </div>
    </div>
</div>

<div class="product-wrap"><!--定期宝介绍/常见问题-->
	<div class="grid-1100 background">
    	<div class="prduct-menu">
        	<div class="menu-tbl">
            	<ul class="col2"><li class="active">定期宝介绍</li><li>加入记录</li></ul>
            </div>
            <div class="menucont" style="clear:both">
            	<div class="tbl-cont">
                	<table width="100%" border="0" cellspacing="0" cellpadding="0"  class="table textl tbtr01">
                      <tr>
                        <td width="155px" class="textr gray3">名称</td>
                        <td >${fixBorrowVo.name}</td>
                      </tr>
                      <tr height="74px">
                        <td class="textr gray3" >介绍<br/>&nbsp;</td>
                        <td>定期宝是顶玺金融推出的对风险保证金保障的借款项目进行自动投资及到期自动退出的理财计划。出借人出借的本息将在投资期限到达后一次性返回其账户，更好的满足了出借人的需求。</td>
                      </tr>
                      <tr>
                        <td class="textr gray3" >投资范围</td>
                        <td>资产抵押标、机构担保标、信用认证标</td>
                      </tr>
                      <tr>
                        <td class="textr gray3" >期限</td>
                        <td>${fixBorrowVo.lockLimit}个月</td>
                      </tr>
                      <tr>
                        <td class="textr gray3" >预期收益</td>
                        <td>${fixBorrowVo.apr}%</td>
                      </tr>
                      <tr>
                        <td class="textr gray3" >加入条件</td>
                        <td>
                        <c:if test="${fixBorrowVo.areaType==1}">
						  100元至10000元，且是100元的整数倍递增
						</c:if>
						<c:if test="${fixBorrowVo.areaType!=1}">
						  100元起，且是100元的整数倍递增
						</c:if>
                        </td>
                      </tr>
                      <tr>
                        <td class="textr gray3" >到期退出方式</td>
                        <td>系统将通过债权转让自动完成退出（您所持债权出售完成的具体时间，视债权转让市场交易情况而定）。</td>
                      </tr>
                      <tr>
                        <td class="textr gray3" >保障方式</td>
                        <td>风险保证金保障</td>
                      </tr>
                      <tr>
                        <td class="textr gray3" >协议</td>
                        <td>点击	 <a href="javascript:toShowFixXiyi('${fixBorrowVo.id}');" class="bule">查看协议</a></td>
                      </tr>
                    </table>
                </div>
                <div class="tbl-cont" style="display:none">
                	<div id="divforjoin"></div>
                </div>
            </div>
        </div>
    </div>
</div>

<!--弹层star--->
<div class="layer-jion" id="tender-layer">
	<div class="cont-word">
    	<div class="title"><h4>加入定期宝</h4><a href="#" class="icon icon-close fr"></a></div>
        <div class="form-info-layer">
        	<div class="form-col3">
                <label for="" class="colleft form-lable">加入金额</label>
                <span class="fl money" id="jionAccount"></span>
            </div>
            <c:if test="${fixBorrowVo.bidPassword != null && fixBorrowVo.bidPassword != ''}">	
            <div class="form-col2">
                <label for="" class="colleft form-lable">认购密码</label>
                 <input  id="bidPassword"  class="form-inpyt-sm" style="float:left; width:120px;color:#999;" type="text" value="请输入认购密码"/>
                 <input type="password"  id="bidPayPassword" name="bidPassword"  class="form-inpyt-sm"  style="display:none;width:120px;color:#999;"/> <a href="javascript:void(0);" class="fl pdlr10 line32"> </a>
            </div> 
            </c:if>
     <%--    	<div class="form-col2">
                <label for="" class="colleft form-lable">交易密码</label>
                 <input  id="password"  class="form-inpyt-sm" style="float:left; width:120px;color:#999;" type="text" value="请输入交易密码"/>
                 <input type="password"  id="payPassword" name="payPassword"  class="form-inpyt-sm"  style="display:none;width:120px;color:#999;"/><a href="${path}/AccountSafetyCentre/safetyCentre/enterFindTransactionPwd.html" class="fl pdlr10 line32">忘记密码</a>
            </div> --%>
       <%--      <div class="form-col2">
                <label for="" class="colleft form-lable">验证码</label>
                <input type="text" name="checkCode" id="checkCode" style="width:120px;color:#999;" class="colright form-inpyt-sm"  onblur="if(value==''){value='验证码';$('#checkCode').attr('style','color:#999;width:120px;');}" onfocus="if(value=='验证码'){value='';$('#checkCode').attr('style','color:#FFFFF;width:120px;'); }" value="验证码" maxlength="4">
                <a href="javascript:loadimage();" class="fl" style="float:right;padding-right: 45px;padding-top: 10px;">
					<img name="randImage" id="randImage" 
					src="${basePath}/random.jsp" style="float: left;" border="0"
					align="middle" />
				</a>
            </div> --%>
            <div class="form-col3">
               <div class="f14 line32">
               <input id="xieyi" name="xieyi" type="checkbox" checked="checked"><span>我已阅读并同意</span>
               <a href="javascript:toShowFixXiyi('${fixBorrowVo.id}');">《顶玺金融定期宝投资协议》</a></div>
            </div>
            
            <!-- <div class="f12 textl form-col3 red" style="padding-left:13px;"><i class="icon error"></i>请输入验证码</div> -->
            
            <div class="form-col2">
            <button type="button" class="remove" onClick="cancel_firxBorrow();">取消</button><button type="button" class="enter" id="btnTenderBorrow">确认</button>
            </div>
         </div>
    </div> 
</div>

<div class="layer-jion" style="display:none;" id="tenderResult-layer">
    <div class="cont-word" >
    	<div class="sucess-info">
            <p>投资成功！恭喜你已经投资<b><label id="totalDiv"></label></b>元<br/>
           	 本次投资使用红包<b><label id="redsDiv"></label></b>元，实际使用账户余额<b><label id="realDiv"></label></b>元
            </p>
            <div class="form-col2">
            	<button type="button" class="enter" style="width:120px" onclick="cancel_tenderResult();">我知道了</button>
        	</div>
        </div>
	</div>
</div>
<!--弹层end--->
<input id="msgFlag" type="hidden" />
</form>
<%@ include file="/WEB-INF/page/common/footer.jsp"%>
</body>
<style type="text/css">
	.select-sl dt{ width:190px; background: url(../images/v5/select-down.png) right no-repeat; }
	.select dd{ z-index:99999;}
	.select-sl dd ul{ width:176px; }
	.product-deatil .detail-col-r ul{ padding:3px 10px!important; overflow:hidden; }
	.product-deatil .detail-col-r ul>li{ line-height:25px !important; }
	.select-sl dd ul a{color:#333;}
	.select-sl dd ul a:hover{color:red; background:none!important;}
	.product-deatil .detail-col-r ul>li:last-child{ border-top:1px solid #ccc !important;}
</style>
<script type="text/javascript">
	/**秒数*/
	var SysSecond;
	/**循环计算倒计时*/
	var InterValObj;
	
	$(function(){//加入下拉菜单
		$(".select").each(function(){
			var s=$(this);
			var z=parseInt(s.css("z-index"));
			var dt=$(this).children("dt");
			var dd=$(this).children("dd");
			var _show=function(){dd.slideDown(200);dt.addClass("cur");s.css("z-index",z+1);};   //展开效果
			var _hide=function(){dd.slideUp(200);dt.removeClass("cur");s.css("z-index",z);};    //关闭效果
			dt.click(function(){dd.is(":hidden")?_show():_hide();});
			dd.find("a").click(function(){dt.html($(this).html());_hide();});     //选择效果（如需要传值，可自定义参数，在此处返回对应的“value”值 ）
			$("body").click(function(i){ !$(i.target).parents(".select").first().is(s) ? _hide():"";});
		});
		/* $(".form-inpyt-sm").focus(function(){ 
			if($(this).attr('id')=='password'){ 
				$(this).hide(); 
				$('#payPassword').show(); 
				$('#payPassword').focus(); 
				$("#payPassword").attr("style","color:#FFFFF;width:120px;float:left;");  
			} 
		}); 
		$(".form-inpyt-sm").blur(function(){ 
			if($(this).attr('id')=='payPassword' && $(this).val()==''){ 
				$(this).hide(); 
				$('#password').show(); 
				$('#password').val('请输入交易密码'); 
				$("#password").attr("style","color:#999;width:120px;float:left;");
			} 
			}); */
		$(".form-inpyt-sm").focus(function(){ 
		if($(this).attr('id')=='bidPassword'){ 
			$(this).hide(); 
			$('#bidPayPassword').show(); 
			$('#bidPayPassword').focus(); 
			$("#bidPayPassword").attr("style","color:#FFFFF;width:120px;float:left;");  
		} 
	}); 
	$(".form-inpyt-sm").blur(function(){ 
		if($(this).attr('id')=='bidPayPassword' && $(this).val()==''){ 
			$(this).hide(); 
			$('#bidPassword').show(); 
			$('#bidPassword').val('请输入认购密码'); 
			$("#bidPassword").attr("style","color:#999;width:120px;float:left;");
		} 
		}); 
	});

	$(function() {
		useCurMoney();//2015.12.18 默认勾选活期宝
		var lockStatus = '${fixBorrowVo.lockStatus}';
		var nowTime = $("#nowTime").val();
		/**可认购标,可认购剩余时间*/
		//if (lockStatus == 0) {
			var endTime = $("#endTime").val();
			//这里获取倒计时的起始时间 
			SysSecond = parseFloat(new Date(endTime).getTime() / 1000)
					- parseFloat(new Date(nowTime).getTime() / 1000);
			if(SysSecond>0){
				//剩余时间
				InterValObj = window.setInterval(SetRemainTime, 1000); //间隔函数，1秒执行 
			}else{
				//$("#btnJoin").attr("disabled","disabled");
			}
			
		//}
		//如果可投金额小于最小红包使用金额，默认显示不适用红包，并将红包ID置0
		if(Number('${minRedMoney*100}')>Number('${fixBorrowVo.planAccount-fixBorrowVo.accountYes}')){
			setRedsValues(0);
		}

	});
	/**
	 * 倒计时
	 */
	function SetRemainTime() {
		if (SysSecond > 0) {
			SysSecond = SysSecond - 1;
			var second = Math.floor(SysSecond % 60); // 计算秒     
			var minute = Math.floor((SysSecond / 60) % 60); //计算分 
			var hour = Math.floor((SysSecond / 3600) % 24); //计算小时 
			var day = Math.floor((SysSecond / 3600) / 24); //计算天 
			$("#remainingTime").html('剩余时间&nbsp;&nbsp;&nbsp;&nbsp;<span>'+day+'</span>'
							+ '天' + '<span>'+hour+'</span>'
							+ '时' + '<span>'+minute+'</span>'
							+ '分' + '<span>'+second+'</span>'
							+ '秒');
		} else {//剩余时间小于或等于0的时候，就停止间隔函数 
			window.clearInterval(InterValObj);
			//这里可以添加倒计时时间为0后需要执行的事件 
			$("#remainingTime").html("剩余时间&nbsp;&nbsp;&nbsp;&nbsp;0天0时0分0秒");
			//window.location.reload();
		}
	}
	$(document).ready(function() {
		//searchFixBorrowListStart(1);
	});

	/**
	 *  show 协议
	 */
	function toShowFixXiyi(id) {
		// 判断用户是否已登录
		var userId = $("#userId").val();
		if(userId=='hasEnter'){
			var msg = "";
			//加入金额
			var tenderMoney = $("#tenderMoney").val();
			if(isNaN(tenderMoney)){
				tenderMoney=0;
			}
			/* var zfdsReg = /^[1-9]\d*$/;//金额的正则表达式 正整数 
			if(tenderMoney!=null){
				if(tenderMoney=='请输入100元的整数倍'){
					tenderMoney = 0;
					msg = "";
				}else{
					if(!zfdsReg.test(tenderMoney)){
					msg = msg + "-开通金额必须是正整数！<br/>";
				    }else if(parseInt(tenderMoney)<100 || parseInt(tenderMoney) % 100 !=0){
					msg = msg + "-开通金额必须是百元的整数倍！<br/>";
				    }
				}
			}
			if(msg!=""){
				layer.msg(msg,2,5);
				return false;
			} */
		   $.layer({
			type : 2,
			fix : false,
			shade : [ 0.6, '#E3E3E3', true ],
			shadeClose : true,
			border : [ 10, 0.7, '#272822', true ],
			title : [ '', false ],
			offset : [ '50px', '' ],
			area : [ '1075px', '450px' ],
			iframe : {
				src : '${basePath }/dingqibao/toShowFixXiyi/' + id
						+ '.html?money='+tenderMoney
			},
			close : function(index) {
				layer.close(index);
			}
		});
		 }else {
				layer.msg("请先登录");
		 }
	}	
	function searchFixBorrowListStart(pageNum) {
		$.ajax({
			url : '${basePath}/dingqibao/queryFixBorrowList.html',
			data : {
				pageNum : pageNum,
				pageSize : 10
			},
			type : 'post',
			dataType : 'text',
			success : function(data) {
				$("#fixBorrowList").html(data);
			},
			error : function(data) {
				alert("网络连接异常，请刷新页面或稍后重试！");
			}
		});
	}
	

	
	/**
	 * 开通
	 */
	function tender_fixborrow(){
	    if(!validateTenderData()){
	    	return;
	    }
		if(layer.confirm("确定要开通吗？",function(index){
			layer.close(index);
			$("#btnTenderBorrow").unbind('click');
			$("#tenderFixBorrowForm").ajaxSubmit({
			    url : '${basePath }/dingqibao/tender/${fixBorrowVo.id}.html',
			    type: "POST",
			    success:function(msg){
			    	$("#btnTenderBorrow").bind('click', tender_fixborrow);
			    	if(msg.code=="1"){
			    		cancel_firxBorrow();
			    		var _redId = $('#redId').val();
			    		if(_redId>0){
			    			var redMoney=$('#redHid'+_redId).val()/100;
			    			var tenderMoney=parseInt($("#tenderMoney").val())+parseInt(redMoney);
		    				if(parseInt(tenderMoney)<100 || parseInt(tenderMoney) % 100 !=0){
		    					tenderMoney = Number($("#tenderMoney").val());
		    				}else{
		    					if(parseInt($("#tenderMoney").val())==100 || parseInt($("#tenderMoney").val()) % 100 ==0){
		    						tenderMoney = Number($("#tenderMoney").val());
		    						var _redUseMoney = parseInt($('#redHid'+_redId).val());
		    						if(tenderMoney<_redUseMoney){
		    							tenderMoney = tenderMoney+redMoney;
		    						}
		    					}
		    					 var remainAccount =$("#remainAccount").val();
		    					if(Number(remainAccount)<tenderMoney){
		    						tenderMoney = Number($("#tenderMoney").val());
		    					}
		    				}
				    		var useAccountMoney=tenderMoney-parseInt(redMoney);
				    	    $("#totalDiv").html(formatMoney(tenderMoney));
				    	    $("#redsDiv").html(formatMoney(redMoney));
				    	    $("#realDiv").html(formatMoney(useAccountMoney));
				    	    $("#tenderResult-layer").show();
				    	    $("#msgFlag").val(msg.message);
				    	    
			    		}else{
			    			if(msg.message=='加入成功'){
			    				layer.msg(msg.message, 2, 1,function(){
				    				window.parent.location.href="${path }/dingqibao/${fixBorrowVo.id}.html";
				    			});
			    			}else{
			    				$.layer({
									title:'抽奖提醒',
									area: ['450px','220px'],  
									dialog:{
										type:1,
										msg:msg.message,
									 	btns:1,
									 	btn: ['我知道了'],
									 	yes: function(index){
									 		window.parent.location.href="${path }/dingqibao/${fixBorrowVo.id}.html";
									 	}
									}
								 });
			    			}
			    		}
			    	}else{
			    		if(msg.message != ''){
			    			layer.msg(msg.message, 2, 5);
			    		}
			    		
			    	}
			    },
				error : function() {
					$("#btnTenderBorrow").bind('click', tender_fixborrow);
					layer.msg("网络连接超时，请您稍后重试", 2, 5);
			    } 
			 });
		}));
	}
	
	/**
	 * 验证立即加入
	 */
	function validateToJionData(){
		if(${null==portal:currentUser()}){
			 layer.msg("请先登录", 1, 5,function(){
				 window.location.href="${path}/member/toLoginPage.html?backUrl=1";
			 });
			 return;
		}
		if(${portal:hasRole("borrow")}){
			 layer.msg("您是借款用户,不能进行此操作", 1, 5);
			 return;
		}
		var msg = "";
 		var tenderMoney = Number($("#tenderMoney").val());
 		//剩余可投金额
		var remainAccount =$("#remainAccount").val();
 		var _redId = $('#redId').val(); 
		if(_redId>0&&tenderMoney>0){
			var redMoney=$('#redHid'+_redId).val()/100;
			tenderMoney = tenderMoney+redMoney;
			if(parseInt(tenderMoney)<100 || parseInt(tenderMoney) % 100 !=0){
					tenderMoney = Number($("#tenderMoney").val());
			}else{
				if(parseInt($("#tenderMoney").val())==100 || parseInt($("#tenderMoney").val()) % 100 ==0){
					tenderMoney = Number($("#tenderMoney").val());
					var _redUseMoney = parseInt($('#redHid'+_redId).val());
					if(tenderMoney<_redUseMoney){
						tenderMoney = tenderMoney+redMoney;
					}
				}
				if(Number(remainAccount)<tenderMoney){
					tenderMoney = Number($("#tenderMoney").val());
				}
			}
		}
	/* 	var payPassword = $("#payPassword").val(); */
	/* 	var checkCode = $("#checkCode").val(); */
		//最大可投金额
		var userRemainMoney =$("#userRemainMoney").val();
		
		//账户余额
		var useMoney = Number($("#useMoney").val());
		var zfdsReg = /^[1-9]\d*$/;//金额的正则表达式 正整数 
		//最低开通金额
		var lowestAccount = Number("${fixBorrowVo.lowestAccount}");
		//最高开通金额
		var mostAccount = Number("${fixBorrowVo.mostAccount}");
		//用户的有效开通金额
		var effectiveTenderMoney = Number($("#effectiveTenderMoney").val());
		if($("#tenderMoney").val()==""|| $("#tenderMoney").val()=="请输入100元的整数倍"){
			msg = msg + "-开通金额未填写！<br/>";
		}else if(!zfdsReg.test(tenderMoney)){
			msg = msg + "-开通金额必须是正整数！<br/>";
		}else if((parseInt($("#tenderMoney").val())<100 || parseInt($("#tenderMoney").val()) % 100 !=0)&&(parseInt(tenderMoney)<100 || parseInt(tenderMoney) % 100 !=0)){
			   msg = msg + "-开通金额必须是百元的整数倍！<br/>";
		}else {
			if(lowestAccount > effectiveTenderMoney){
				if(tenderMoney!=effectiveTenderMoney){
					msg = msg + "-有效开通金额为"+ effectiveTenderMoney +"元！<br/>";
				}
			}else if(tenderMoney < lowestAccount){
				msg = msg + "-开通金额不能少于"+ parseInt(lowestAccount/100)*100 +"元！<br/>";
			}else if(tenderMoney > effectiveTenderMoney){
				msg = msg + "-开通金额不能大于"+effectiveTenderMoney+"元！<br/>";
			}
		}
		//投标金额与剩余可投金额
		if(remainAccount!= 0 && tenderMoney > remainAccount){	
			if(mostAccount!=0 && remainAccount>mostAccount){
				msg = msg + "-你可开通的最大金额为"+parseInt(mostAccount/100)*100+"元！<br/>";
			}else{
				msg = msg + "-你可开通的最大金额为"+parseInt(remainAccount/100)*100+"元！<br/>";
			}
		}
		
		var areaType = $("#areaType").val();
		if(areaType==1){
			if(parseInt(tenderMoney)>10000){
				msg = msg + "-新手宝开通金额不能大于10000！<br/>";
			}
		}		
		//验证红包投资金额
 		var _redId = $('#redId').val(); 
		if(_redId>0 && tenderMoney>0){
			var _redUseMoney = parseInt($('#redHid'+_redId).val());
			if(tenderMoney<_redUseMoney){
				msg = msg +"-投资金额不满足红包使用条件！<br/>";
			}
		} 
		
		if(msg!=""){
			layer.msg(msg,2,5);
			return false;
		}
		$("#tender-layer").show();
		//$(".cont-word").animate({top:"20px"});
		if(_redId>0&&tenderMoney>0){
			var redMoney=$('#redHid'+_redId).val()/100;
	        $("#jionAccount").html('<strong class="oren f14">'+formatMoney(tenderMoney)+'</strong><font color="#000">元(含红包<strong class="oren f14">'+redMoney+'</strong><font color="#000">元)</font>');
		}else{
	        $("#jionAccount").html('<strong class="oren f14">'+formatMoney(tenderMoney)+'</strong><font color="#000">元</font>');
		}
		$("#btnTenderBorrow").bind('click', tender_fixborrow);
		return true;
	}
	
	
	
	/**
	 * 验证开通数据
	 */
	function validateTenderData(){
		if(${null==portal:currentUser()}){
			 layer.msg("请先登录", 1, 5,function(){
				 window.location.href="${path}/member/toLoginPage.html?backUrl=1";
			 });
			 return;
		}
		if(${portal:hasRole("borrow")}){
			 layer.msg("您是借款用户,不能进行此操作", 1, 5);
			 return;
		}
		var msg = "";
		//剩余可投金额
		var remainAccount =$("#remainAccount").val();
		var tenderMoney = Number($("#tenderMoney").val());
 		var _redId = $('#redId').val(); 
		if(_redId>0&&tenderMoney>0){
			var redMoney=$('#redHid'+_redId).val()/100;
			tenderMoney = tenderMoney+redMoney;
			if(parseInt(tenderMoney)<100 || parseInt(tenderMoney) % 100 !=0){
				tenderMoney = Number($("#tenderMoney").val());
			}else{
				if(parseInt($("#tenderMoney").val())==100 || parseInt($("#tenderMoney").val()) % 100 ==0){
					tenderMoney = Number($("#tenderMoney").val());
					var _redUseMoney = parseInt($('#redHid'+_redId).val());
					if(tenderMoney<_redUseMoney){
						tenderMoney = tenderMoney+redMoney;
					}
				}
				if(Number(remainAccount)<tenderMoney){
					tenderMoney = Number($("#tenderMoney").val());
				}
			}
		}
		/**var payPassword = $("#payPassword").val(); */
		/* var checkCode = $("#checkCode").val(); */
		//最大可投金额
		var userRemainMoney =$("#userRemainMoney").val();
		
		//账户余额
		var useMoney = Number($("#useMoney").val());
		var zfdsReg = /^[1-9]\d*$/;//金额的正则表达式 正整数 
		//最低开通金额
		var lowestAccount = Number("${fixBorrowVo.lowestAccount}");
		//最高开通金额
		var mostAccount = Number("${fixBorrowVo.mostAccount}");
		//用户的有效开通金额
		var effectiveTenderMoney = Number($("#effectiveTenderMoney").val());
		if($("#tenderMoney").val()==""|| $("#tenderMoney").val()=="请输入100元的整数倍"){
			msg = msg + "-开通金额未填写！<br/>";
		}else if(!zfdsReg.test(tenderMoney)){
			msg = msg + "-开通金额必须是正整数！<br/>";
		}else if((parseInt($("#tenderMoney").val())<100 || parseInt($("#tenderMoney").val()) % 100 !=0)&&(parseInt(tenderMoney)<100 || parseInt(tenderMoney) % 100 !=0)){
				   msg = msg + "-开通金额必须是百元的整数倍！<br/>";
		}else {
			if(lowestAccount > effectiveTenderMoney){
				if(tenderMoney!=effectiveTenderMoney){
					msg = msg + "-有效开通金额为"+ effectiveTenderMoney +"元！<br/>";
				}
			}else if(tenderMoney < lowestAccount){
				msg = msg + "-开通金额不能少于"+ parseInt(lowestAccount/100)*100 +"元！<br/>";
			}else if(tenderMoney > effectiveTenderMoney){
				msg = msg + "-开通金额不能大于"+effectiveTenderMoney+"元！<br/>";
			}
		}
		//投标金额与剩余可投金额
		if(remainAccount!= 0 && tenderMoney > remainAccount){			
			if(mostAccount!=0 && remainAccount>mostAccount){
				msg = msg + "-你可开通的最大金额为"+parseInt(mostAccount/100)*100+"元！<br/>";
			}else{
				msg = msg + "-你可开通的最大金额为"+parseInt(remainAccount/100)*100+"元！<br/>";
			}
		}
		
		var areaType = $("#areaType").val();
		if(areaType==1){
			if(parseInt(tenderMoney)>10000){
				msg = msg + "-新手宝开通金额不能大于10000！<br/>";
			}
		}
		if('${fixBorrowVo.bidPassword}' != null && '${fixBorrowVo.bidPassword}' != ''){
		 	var bidPayPassword = $("#bidPayPassword").val();
		 	 if(bidPayPassword == null || bidPayPassword == ""){
					msg = msg + "-认购密码未填写！<br/>";
				} 
		}
		/* if(payPassword == null || payPassword == ""){
			msg = msg + "-交易密码未填写！<br/>";
		} */
	/* 	if(checkCode==null || checkCode==""){
			msg = msg + "-验证码未填写！<br/>";
		} */
		if(!$("#xieyi").is(":checked")){
			msg = msg + "-请先阅读协议并选择已阅读协议！<br/>";
		}
		
		//验证红包投资金额
 		/* var _redId = $('#redId').val(); 
		var _tenderMoney = $("#tenderMoney").val();
		if(_redId>0 && _tenderMoney>0){
			var _redUseMoney = parseInt($('#redHid'+_redId).val());
			if(_tenderMoney<_redUseMoney){
				msg = msg +"-投资金额不满足红包使用条件！<br/>";
			}
		} */
		
		
		if(msg!=""){
			layer.msg(msg,2,5);
			return false;
		}
		return true;
	}
	
	//进度条设置
		$("#bar").css("width","0px");
		var accountYes = parseFloat(${fixBorrowVo.accountYes});
		var planAccount = parseFloat(${fixBorrowVo.planAccount});
		var nowWidth = Math.floor(accountYes/planAccount*300);
		//宽度要不能大于进度条的总宽度
	   if(nowWidth<=300){
	    $("#bar").css("width",nowWidth+"px");
	   }
	   
	   
	 	//页面加载首次调用
	   searchFixTenderDetailList(1,${fixBorrowVo.id});
	   
	   //加入记录查询
	   function searchFixTenderDetailList(pageNum,fixBorrowId) {
			$.ajax({
				url : '${basePath}/dingqibao/queryFixTenderDetailList.html',
				data : {
					pageNum : pageNum,
					pageSize : 10,
					fixBorrowId:fixBorrowId
				},
				type : 'post',
				dataType : 'text',
				success : function(data) {
					$("#divforjoin").html(data);
				},
				error : function(data) {
					alert("网络连接异常，请刷新页面或稍后重试！");
				}
			}); 
	   }
	   
	   //去充值
	   function toTopupIndex(){
		   window.location.href="${path}/account/topup/toTopupIndex.html";
	   }
	   
	   /**
	    * 使用活期宝金额
	    */
	   function useCurMoney(){
	   	var isUseCurMoney = $("#isUseCurMoney").val();
	   	if(isUseCurMoney == 0){
	   		$("#isUseCurMoney").val(1);
	   		$("#isUseCurMoney").attr("checked","checked");
	   	}else{
	   		$("#isUseCurMoney").val(0);
	   		$("#isUseCurMoney").removeAttr("checked");
	   	}
	   	$.ajax({
	   		url : '${basePath }/dingqibao/findEffectiveTenderMoney/${fixBorrowVo.id}.html',
	   		data : {isUseCurMoney:$("#isUseCurMoney").val()},
	   		type : 'post',
	   		dataType : 'json',
	   		success : function(data){
	   			var _redId = $('#redId').val();
				if(_redId > 0){
					var redMoney=$('#redHid'+_redId).val()/100;
					$("#effectiveTenderMoney").val(Number(data)+redMoney);
				}else{
					$("#effectiveTenderMoney").val(data);
				}
	   		},
	   		error : function(data) {
	   			layer.msg("网络连接异常，请刷新页面或稍后重试！", 2, 5);
	   		}
	   	});
	   }
	   
	   /**
	    * 使用活期宝金额
	    */
	   function useCurMoneyRed(){
	   	$.ajax({
	   		url : '${basePath }/dingqibao/findEffectiveTenderMoney/${fixBorrowVo.id}.html',
	   		data : {isUseCurMoney:$("#isUseCurMoney").val()},
	   		type : 'post',
	   		dataType : 'json',
	   		success : function(data){
	   			var _redId = $('#redId').val();
				if(_redId > 0){
					var redMoney=$('#redHid'+_redId).val()/100;
					$("#effectiveTenderMoney").val(Number(data)+redMoney);
				}else{
					$("#effectiveTenderMoney").val(data);
				}
	   		},
	   		error : function(data) {
	   			layer.msg("网络连接异常，请刷新页面或稍后重试！", 2, 5);
	   		}
	   	});
	   }
	  //自动输入加入金额
	  function enterMomey(){
	   	$.ajax({
	   		url : '${basePath }/dingqibao/findEffectiveTenderMoney/${fixBorrowVo.id}.html',
	   		data : {isUseCurMoney:$("#isUseCurMoney").val()},
	   		type : 'post',
	   		dataType : 'json',
	   		success : function(data){
	   			if(data != null){
	   				var effectiveTenderMoney = data;
	   				if(effectiveTenderMoney<=0){
	   					layer.msg("您的金额不符合要求，不能开通。",2,5);
	   				}else{
	   					$("#tenderMoney").val(parseInt(effectiveTenderMoney/100)*100);
	   				}
	   			}else{
	   				layer.msg("您的金额不符合要求，不能开通。",2,5);
	   			}
	   		},
	   		error : function(data) {
	   			layer.msg("网络连接异常，请刷新页面或稍后重试！", 2, 5);
	   		}
	   	});
	  }
	  
	  function setRedsValues(id){
		  $("#redId").val(id);
	      useCurMoneyRed();
	  }
	  
	  function cancel_firxBorrow(){
		  $("#tender-layer").hide();
		  $("#bidPayPassword").val('');
		  //$(".cont-word").animate({top:"0px"});
	/* 	  $("#payPassword").val(''); */
	/* 	  $("#checkCode").val(''); */
		  
	  }
	  
	  function cancel_tenderResult(){
		  var _flag = $("#msgFlag").val();
		  $("#tenderResult-layer").hide();
		  if(_flag!='加入成功'){
  	    	$.layer({
					title:'抽奖提醒',
					area: ['450px','220px'],  
					dialog:{
						type:1,
						msg:_flag,
					 	btns:1,
					 	btn: ['我知道了'],
					 	yes: function(index){
					 		window.parent.location.href="${path }/dingqibao/${fixBorrowVo.id}.html";
					 	}
					}
				 });
  	    }else{
  	    	window.parent.location.href="${path }/dingqibao/${fixBorrowVo.id}.html";
  	    }
		  
	  }
</script>
</html>
