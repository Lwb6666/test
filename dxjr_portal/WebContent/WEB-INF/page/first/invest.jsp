<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/page/common/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <title>顶玺金融官网</title>
	<meta name="keywords" content="P2P网贷理财" />
	<meta name="description" content="">
<%@ include file="/WEB-INF/page/common/public.jsp"%>
</head>

<body>
<!--头部开始-->
<%@ include file="/WEB-INF/page/common/header.jsp"%>
<!--头部结束-->
<!--内容开始-->
<div id="Container">
	<div id="Bmain">
		<div class="title">
				<span class="home"><a href="${path}/">顶玺金融</a></span>
				<span><a href="javascript:void(0);">我要投资</a></span>
				<span><a href="${path}/zhitongche.html"> 直通车专区</a></span>
			<div class="fr">
				<a href="${path}/toubiao.html">[普通投标]</a> 
				<a href="${path }/zhaiquan.html" >[债权转让]</a>
			</div>
	    </div>
	    <div class="tblist_title">
				<ul>
					<li class="selected"><a href="${path}/zhitongche.html">直通车开通</a>
					</li>
					<li><a href="${path}/zhitongche/zhuanrang.html">直通车转让</a>
					
					</li>
					<img style="position: relative;top: -5px;left: -35px;" src="${basePath}/images/new1.gif"/>
				</ul>
		</div>
		<div id="rz_main" style="height: 260px; margin-top: 0; border-top-style: none;">
			<input type="hidden" id="firstBorrowId" value="${firstBorrow.id }"/>	
			<div class="tbtitless">
				<div class="tbtitle">投标直通车</div>
				<div class="tb_borrow2">
					<div class="tblenght tbrlenght">
						<table>
							<tr>
								<td>开通总额</td>
								<td>预期收益</td>
							</tr>
							<tr>
								<td><em>${firstBorrow.planAccountFrontStr}</em>元</td>
								<td><em>${firstBorrow.perceivedRate}</em>%</td>
							</tr>
							<tr>
								<td>开始时间 :${firstBorrow.publishTimeStr}</td>
								<td>结束时间：<fmt:formatDate value="${firstBorrow.endTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
							</tr>
							<tr>
								<td>最低开通额度：￥${firstBorrow.lowestAccountFrontStr}</td>
								<td>最高开通额度：￥${firstBorrow.mostAccountFrontStr}</td>
							</tr>
						</table>
					</div>
					<div class="tbzdcbox">
						<div class="tbzdc" id="remaindTip">
							<c:if test="${firstBorrow.status == 3}">
							剩余时间：
							<span id="publishTime_d" class="red">0</span>天
							<span id="publishTime_h" class="red">0</span>时
							<span id="publishTime_m" class="red">0</span>分
							<span id="publishTime_s" class="red">0</span>秒
							</c:if>
						</div>
						<div class="tbzdc">
							当前可投余额：
							<em>
							<c:choose>
								<c:when test="${not empty effectiveTenderMoney}">
								<c:if test="${firstBorrow.status==5 }">
								￥<fmt:formatNumber value="0" pattern="#,##0.00" />
								</c:if>
								<c:if test="${firstBorrow.status==3 }">
								<span id="remaindAccountTip">￥<fmt:formatNumber value="${effectiveTenderMoney}" pattern="#,##0.00" /></span>
								</c:if>
								</c:when>
								<c:otherwise><a href="${path}/member/toLoginPage.html">登录后查看</a></c:otherwise>
							</c:choose>
							</em>
						</div>
						<div class="tbzdc">账户余额：
							<em>
							<c:choose>
								<c:when test="${not empty account.useMoney}">￥${account.useMoneyStr}</c:when>
								<c:otherwise><a href="${path}/member/toLoginPage.html">登录后查看</a></c:otherwise>
							</c:choose>
							</em>
						</div>
						<div class="tbzdc">
							<em><a href="${path}/account/topup/toTopupIndex.html">立即充值</a></em>
						</div>
						<div class="tbzdc">
									<input style="width:205px; height:40px; background:#999999; text-align:center; border:0px; color:#FFFFFF; font-size:14px;
										${(firstBorrow.havePassPublishTime && firstBorrow.status==5)?'display:block':'display:none'}" type="button" value="开通已满" disabled="disabled" />
										
									<label id="Stoped" ${(firstBorrow.havePassEndTime && firstBorrow.havePassPublishTime && firstBorrow.status == 3 && firstBorrow.planAccount > firstBorrow.accountYes)?"style='display:block'":"style='display:none'"}>
										<input style="width:205px; height:40px; background:#999999; text-align:center; border:0px; color:#FFFFFF; font-size:14px;" type="button" value="开通结束" disabled="disabled" />
									</label>
									
									<label id="Opening" ${(firstBorrow.havePassPublishTime && firstBorrow.status==3 && !firstBorrow.havePassEndTime)?"style='display:block'":"style='display:none'"}>
										<input class="tbbid" type="button" value="开&nbsp;通" onclick="toTender()" style="cursor:pointer;"/>
									</label>
									<label id="Waiting" style="${firstBorrow.havePassPublishTime==false && firstBorrow.havePassEndTime == false?'display:block':'display:none'}">
										<input id="remainSecond" style="width:205px; height:40px; background:#999999; text-align:center; border:0px; color:#FFFFFF; font-size:14px;" type="button" value="即将开始" disabled="disabled" />
									</label>
						</div>
					</div>
				</div>
			</div>
		</div>
		<div class="tblist_title">
			<ul>
				<li id="investDetail" class="selected"><a href="javascript:void(0);" onclick="changeTab('investDetail', '${basePath}/zhitongche/toInvestDetail.html');">功能详情</a></li>
				<li id="investRecord"><a href="javascript:void(0);" onclick="changeTab('investRecord', '${basePath}/first/tenderdetail/queryList/${firstBorrow.id}/1.html');">开通记录</a></li>
				<li id="investFAQ"><a href="javascript:void(0);" onclick="changeTab('investFAQ', '${basePath}/zhitongche/toInvestFAQ.html');">常见问题 </a></li>
				<li id="investTransferRecord"><a href="javascript:void(0);" onclick="changeTab('investTransferRecord', '${basePath}/zhitongche/zhuanrang/queryAllTransferSubscribeList/1.html');">转让记录 </a></li>
			</ul>
			<div class="tbcheck">
			</div>
		</div>
		<div id="rz_main" style="height: auto; border-top: none; margin-top: 0;">
		<div id="investTab"></div>
		</div>
	</div>
</div>
<!--内容结束-->
<%@ include file="/WEB-INF/page/common/footer.jsp"%>
</body>
<script type="text/javascript">
var SysSecond; 
var InterValObj;

//页面 当前时间
var addNowTime=0;
//当前循环对象
var addInterValObj;

$(function() {
	<c:if test="${firstBorrow.status == 3}">
	SysSecond = parseInt((new Date('<fmt:formatDate value="${firstBorrow.endTime}" pattern="yyyy/MM/dd HH:mm:ss"/>').getTime() / 1000) - new Date('${nowTime}').getTime() / 1000); //这里获取倒计时的起始时间 
	InterValObj = window.setInterval(SetRemainTime, 1000); //间隔函数，1秒执行
	
	//改变按钮状态
	changeBtnState();
	</c:if>
	changeTab('investDetail', '${basePath}/zhitongche/toInvestDetail.html');
	
});

function SetRemainTime() {
	if (SysSecond > 0) {
		SysSecond = SysSecond - 1;
		var second = Math.floor(SysSecond % 60); // 计算秒     
		var minute = Math.floor((SysSecond / 60) % 60); //计算分 
		var hour = Math.floor((SysSecond / 3600) % 24); //计算小时 
		var day = Math.floor((SysSecond / 3600) / 24); //计算天 
		$("#publishTime_d").text(day);
		$("#publishTime_h").text(hour);
		$("#publishTime_m").text(minute);
		$("#publishTime_s").text(second);
	} else {//剩余时间小于或等于0的时候，就停止间隔函数 
		window.clearInterval(InterValObj);
		//这里可以添加倒计时时间为0后需要执行的事件
		$("#publishTime_d").text('0');
		$("#publishTime_h").text('0');
		$("#publishTime_m").text('0');
		$("#publishTime_s").text('0');
		$("#Stoped").show();
		$("#Opening").hide();
		$("#remaindTip").hide();
		$("#remaindAccountTip").html("￥0.00");
	}
}

function toTender() {
	 if(${null==portal:currentUser()}){
		 layer.msg("请先登录", 1, 5,function(){
			 window.location.href="${path}/member/toLoginPage.html";
		 });
		 return;
	 }
	 if(${portal:hasRole("borrow")}){
		 layer.msg("您是借款用户,不能进行此操作", 1, 5);
		 return;
	 };
	$.ajax({
		url : '${basePath}/tender/judgTender.html',
		data : {type:2},
		type : 'post',
		dataType : 'json',
		success : function(data) {
			if("0"==data.code || "-1"==data.code || "-2"==data.code || "-3"==data.code || "-4"==data.code || "-5"==data.code){
				 
				layer.msg(data.message, 1, 5,function(){
					if("-1"==data.code){
				    	window.location.href="${path}/AccountSafetyCentre/safetyIndex.html";
				    }else if("-2"==data.code){
				    	window.location.href="${path}/AccountSafetyCentre/safetyIndex.html";
				    }else if("-4"==data.code){
				    	window.location.href="${path}/AccountSafetyCentre/safetyIndex.html";
				    }else if("-5"==data.code){
				    	window.location.href="${path}/AccountSafetyCentre/safetyIndex.html";
				    }
				});
				return;
			}
			if("-99" == data.code){
				return;
			}
			 
			$.layer({
				type : 2,
				fix : false,
				shade : [0.6, '#E3E3E3' , true],
				shadeClose : true,
				border : [10 , 0.7 , '#272822', true],
				title : ['',false],
				offset : ['50px',''],
				area : ['550px','480px'],
				iframe : {src : '${basePath}/zhitongche/toTender/${firstBorrow.id}.html'},
				close : function(index) {
					window.location.href = "${path}/zhitongche/${firstBorrow.id}.html";
					layer.close(index);
				}
			});			
		},
		error : function(data) {
			layer.msg("网络连接异常，请刷新页面或稍后重试！", 2, 5);
		}
	});	
}

function changeTab(objName, _url) {
	$.ajax({
		url : _url,
		data : {},
		type : 'post',
		dataType : 'html',
		success : function(data) {
			var obj = $('#' + objName);
			obj.attr("class", 'selected');
			obj.prevAll().removeAttr("class");
			obj.nextAll().removeAttr("class");
			$("#investTab").html(data);
		},
		error : function(data) {
			layer.msg('网络连接异常，请刷新页面或稍后重试！', 1, 5);
		}
	});
}

/**
 * 开通记录翻页
 */
function turnFirstTenderDetailParent(pageNo){
	var firstBorrowId = $("#firstBorrowId").val();
	changeTab('investRecord', '${basePath}/first/tenderdetail/queryList/'+firstBorrowId+'/'+pageNo+'.html');
}

/**
 * 转让记录翻页
 */
function turnFirstTransferedList(pageNo){
	changeTab('investTransferRecord', '${basePath}/zhitongche/zhuanrang/queryAllTransferSubscribeList/'+pageNo+'.html');
}

/**
 * 改变按钮状态
 */
function changeBtnState(){
	//当前时间
	var nowTime = '${nowTime}';
	var publishTimeStr = '<fmt:formatDate value="${firstBorrow.publishTime}" pattern="yyyy/MM/dd HH:mm:ss"/>';
	addInterValObj = window.setInterval(function (){setOpenTime(nowTime,publishTimeStr);}, 1000); //间隔函数，1秒执行
}

/**
 * 设置启动开通时间
 */
function setOpenTime(nowTime,publishTimeStr) {
	//是否结束循环
	var isQuitLoop = true;
	//时间加1秒
	if(addNowTime>0){
		addNowTime = addNowTime+1;
	}else{
		addNowTime = new Date(nowTime).getTime()/1000+1;
	}
	//循环发布时间
    	var publishTime  = new Date(publishTimeStr).getTime()/1000;
    	var endTimeStr = '<fmt:formatDate value="${firstBorrow.endTime}" pattern="yyyy/MM/dd HH:mm:ss"/>';
		var endTime = new Date(endTimeStr).getTime()/1000;
		var status = ${firstBorrow.status};
		//如果有状态为3并且当前时间大于发布时间的继续循环
		if(status==3 && parseInt(addNowTime)<parseInt(publishTime) && parseInt(addNowTime) < parseInt(endTime)){
			isQuitLoop = false;
		}
    	if(parseInt(addNowTime)>=parseInt(publishTime)){
    		 if(status==3){
    			 if(parseInt(addNowTime) <= parseInt(endTime)){
    				 //显示开通中
            		 $("#Opening").show();
            		 //显示即将开始
        			 $("#Waiting").hide();
    			 }
    		 }
    	}else{
	   		 if(status==3){
	   			 if(parseInt(addNowTime) <= parseInt(endTime)){
		    		 //隐藏开通中
		    		 $("#Opening").hide();
					 //显示即将开始
					 $("#Waiting").show();
					 var remainSecond = parseInt(publishTime)-parseInt(addNowTime);
					 if(remainSecond<=1800){
						 if(remainSecond<=59){
							 $("#remainSecond").val(remainSecond+"秒");
						 }else{
							$("#remainSecond").val(parseInt(remainSecond/60)+"分"+remainSecond%60+"秒");						 
						 }
					 }
	   			 }
			 }
    	}
	//停止循环
	if(isQuitLoop){
		window.clearInterval(addInterValObj);
	}
} 

</script>