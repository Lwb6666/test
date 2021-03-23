<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/page/common/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ include file="/WEB-INF/page/common/public.jsp"%>
<link href="${basePath}/css/lottery/style.css?version=3" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${basePath}/js/lott/jquery.rotate.min.js"></script>
<script type="text/javascript" src="${basePath}/js/lott/jq_scroll.js"></script>
<title>抽奖页面</title>
</head>

<body>
<!--头部开始-->
<%@ include file="/WEB-INF/page/common/header.jsp" %>
<!--头部结束-->
<div class="prize-body" style="padding-top:104px">
	<div class="prize-cont">
    	<div class="activity-p fl">
        	<div class="title"><span class="prizeicon pple"></span>活动达人</div>
            <div id="chanceInfoTip" style="margin-top:10px;height:380px;overflow:hidden;"></div>
        </div>
        <div class="award-yao">
             <div class="award-button">
             <div class="award-button-txt f20 tc" id="shuomingTip">
               	您还有
               	<span id="chanceTotalNumTip2">
               	<c:if test="${chanceTotalNum == null}">N</c:if>
               	<c:if test="${chanceTotalNum != null}">${chanceTotalNum}</c:if>
               	</span>
               	次机会
             </div>
             <div class="cicles" id="pointerBtn" onclick="rotateLottery()" style="cursor: pointer;"></div>
             <img src="${basePath}/images/lottery/button_06_1.png" id="pointerImg"/>
             </div>
         </div>
        <div class="activity-p fl">
        	<div class="title"><span class="prizeicon name"></span>获奖名单</div>
           <div id="useRecordInfoTip" style="margin-top:10px;height:200px;overflow:hidden;"> </div>
            <div class="grad"><span class="prizeicon jiang22"></span>大奖公告</div>
            <div class="grad-p">
            <div id="maxGoodsInfoTip" style="margin-top:5px;height:90px;overflow:hidden;"></div>
            </div>
        </div>
    </div>
</div>
<div class="cont-body">
	<div class="cont-text">
    	<div class="text1">
        	<p>新用户首次注册成功即可赠送1次抽奖机会 。</p>
            <div class="btn-red"><a href="${path }/member/toRegisterPage.html">马上注册</a></div>
		</div>
        <div class="text2">
        	<p>每成功推荐一位好友首次投资满1000元,奖励1次抽奖机会 。</p>
            <div class="btn-red"><a href="javascript:recommendNow();">去推荐</a></div>
		</div>
        <div class="text3">
        	<p>首次投资3月宝奖励 1次 抽奖机会<br/>  
            首次投资6月宝奖励 2次 抽奖机会<br/>  
            首次投资12月宝奖励 3次 抽奖机会
            </p>
            <div class="btn-red"><a href="${path}/dingqibao.html">马上投宝</a></div>
		</div>
        <div class="text3">
        	<p>论坛连续签到${signAwardNum}天即可获赠1次抽奖机会</p>
            <div class="btn-red"><a href="${bbsPath}/">去签到</a></div>
		</div>
    </div>
</div>
<div class="activity-notice">
	<div class="activity-word">
    	<h1>活动规则</h1>
        <p>一、新人注册奖励</p>
        <p>1. 新用户注册即可获赠1次抽奖机会；</p>
        <p>2. 更多新人活动进入<a href="${path}/lottery_chance/toNewAward.html">新人活动专题页</a>查看。</p>
        <p>&nbsp;</p>
        <p>二、推荐好友奖励</p>
        <p>1. 每成功推荐1名好友，奖励推荐人1次抽奖机会。推荐成功指被推荐人在活动期间完成单笔首次投资1000元以上。（包含1000元、投资不包括活期宝）</p>
        <p>2. 更多推荐奖励规则请点击进入<a href="${path}/activity/invited.html">推荐奖励专题页</a>查看。</p>
        <p>&nbsp;</p>
        <p>三、论坛签到奖</p>
        <p>论坛连续签到${signAwardNum}天即可奖励1次抽奖机会 ，连续签到天数以论坛每天九点签到帖上的连续签到天数为依据。如果中间中断，重新从1开始计算。</p>
        <p>&nbsp;</p>
        <p>四、奖品及奖励发放规则</p>
        <p>1.凡抽中1888、iphone6的用户账户当前待收需为10万以上，否则视为无效；</p>
        <p>2.凡抽中其他现金奖项的，奖金立即发放，发放至个人账户，可前往“我的账户 > 有奖活动 > 我的奖励”中提取使用；</p>
        <p>3.所有奖项的有效期为30天，超过有效期未领取的，顶玺金融有权收回奖励。</p>
        <p>&nbsp;</p>
        <p style="text-align:right">最终解释权归于顶玺金融所有</p>
    </div>
</div>

<div class="footer">
  <div class="container">
    <div class="foot-bg"></div>
    </div>
</div>
<!-- 抽奖结束 -->
</body>
<style type="text/css">
@media only screen and (min-width: 321px) and (max-width: 1024px){ 
.activity-notice,.prize-body,.header .topbar,.navbar{ width:1300px; } 
} 
</style>
<script type="text/javascript">


$(function(){
	// 加载24小时内获得抽奖机会记录
	loadChanceInfo();
	// 加载24小时内中奖记录
	loadUseRecord();
	// 加载10条最新大奖信息
	loadMaxGoods();
	// 初始化默认位置
	window.scrollTo(0,250);
});



function loadChanceInfo(){
	$.ajax({
		type : "post", //请求类型
		url : "${basePath}/lottery_chance/queryChanceInfoRecord.html",
		success : function(data) {//data 服务器返回给客户端的数据
			var content = "<ul >";
			if(data != null && data.length > 0){
				for(var i=0; i < data.length; i++)  {  
					content += "<li class='lh ' style='font-size:12x;font-family:微软雅黑;'> <span class='icon-circle'></span>";
					content += "<label title='"+data[i].username+"' style='font-size:12px;font-family:微软雅黑;'> ";
					content += data[i].username;					
					content += "参与<em class='yellow-c'>"+data[i].chanceRuleInfoName+"</em>获取了"+data[i].lotteryNum+"次抽奖";
					content += "</label>";
					content += "</li>";
				}; 
			}
			content += "</ui>";
			$("#chanceInfoTip").html(content);
			// 滚动抽奖机会记录
			if(data.length > 4){
				//startmarquee(34, 80, 0, 'chanceInfoTip');
				$("#chanceInfoTip").Scroll({line:4,speed:500,timer:3000});
			}
		},// 回调函数结束
		error : function(data) {
			
		}
	});
}



function loadUseRecord(){
	$.ajax({
		type : "post", //请求类型
		url : "${basePath}/lottery_chance/lotteryUseRecord.html", 
		success : function(data) {//data 服务器返回给客户端的数据
			var content = "<ul>";
			if(data != null && data.length > 0){
				for(var i=0; i < data.length; i++)  {  
					content += "<li class='lh' style='font-size:12px;font-family:微软雅黑;'> <span class='icon-circle'></span>";
					content += "<label title='"+data[i].username+"'>";
					content += data[i].username;				
					content += "&nbsp;&nbsp;抽到&nbsp;&nbsp;"+data[i].lotteryGoodsName;
					content += "</label>";
					content += "</li>";
				}; 
			}
			content += "</ul>";
			$("#useRecordInfoTip").html(content);
			// 滚动中奖记录
			if(data.length > 4){
				//startmarquee(34, 80, 0, 'useRecordInfoTip');
				$("#useRecordInfoTip").Scroll({line:4,speed:500,timer:3000});
			}
		},// 回调函数结束
		error : function(data) {
			
		}
	});
}



function loadMaxGoods(){
	$.ajax({
		type : "post", //请求类型
		url : "${basePath}/lottery_chance/lotteryMaxGoods.html", 
		success : function(data) {//data 服务器返回给客户端的数据
			var content = "<ul>";
			if(data != null && data.length > 0){
				for(var i=0; i < data.length; i++)  {  
					content += "<li class='lh' style='font-size:12px;font-family:微软雅黑;'> <span class='icon-circle'></span>";
					content += "恭喜";
					content += "<label title='"+data[i].username+"'>";
					if(data[i].username.length > 10){
						content += data[i].username.substring(0, 10)+"..";
					}else{
						content += data[i].username;
					}
					content += "</label>";
					if(data[i].lotteryGoodsName == 'Iphone6手机'){
						content += "抽到iphone6手机一台";
					}else if(data[i].lotteryGoodsName == '1888元现金'){
						content += "抽到1888元现金";
					}else{
						content += "抽到"+data[i].lotteryGoodsName;
					}
					content += "</li>";
				}; 
			}
			content += "</ul>";
			$("#maxGoodsInfoTip").html(content);
			// 滚动中奖记录
			if(data.length > 4){
				//startmarquee(34, 80, 0, 'useRecordInfoTip');
				$("#maxGoodsInfoTip").Scroll({line:1,speed:500,timer:3000});
			}
		},// 回调函数结束
		error : function(data) {
			
		}
	});
}

/**
 * 开始抽奖
 */
function rotateLottery(){
	$('#pointerBtn').removeAttr("onclick");
	$("#shuomingTip").attr("style","display:none;");
	$.ajax({
		type : "post", //请求类型
		url : "${basePath}/lottery_chance/lotteryDraw.html", //请求地址  ${basePath}/myaccount/cashRecord/cancelCash.html
		success : function(data) {//data 服务器返回给客户端的数据
			if(data.sFlag == 'success'){
				rotateTo(data.sAngel,data.eAngel,data.dtnTime, data.message, data.sourceTypeName, data.position, data.awardNum, data.awardType);
			}else{
				$('#pointerBtn').attr("onclick","rotateLottery()");
				$("#shuomingTip").removeAttr("style");
				layer.msg(data.message, 2, 5,function(){
					if(data.message=='请先登录！'){
						window.location.href="${path}/member/toLoginPage.html?backUrl=1";
					}
				});
			}
			//alert(data.sAngel+'-'+data.eAngel+'-'+data.dtnTime+'-'+data.message+'-'+data.sFlag);   
		},// 回调函数结束
		error : function(data) {
			$('#pointerBtn').attr("onclick","rotateLottery()");
			$("#shuomingTip").removeAttr("style");
			layer.msg('网络连接异常，请刷新页面或稍后重试！', 1, 5);
		}
	});
	
	//-------点击，绑定事件，在开始随机 end
	//---------------单独封装一个选择方法 start 
	var rotateTo = function(sAngel,eAngel, dtnTime, message, sourceTypeName, position, awardNum, awardType) {
		$("#pointerImg").rotate({  //rotate() 方法旋转画布的坐标系统
			//旋转角度，以弧度计。
			//如需将角度转换为弧度，请使用 degrees*Math.PI/180 公式进行计算。
			//实例：如需旋转 5 度，可规定下面的公式：5*Math.PI/180。
			angel : sAngel, //起始角度
			animateTo : eAngel,  //起始角度 + 默认旋转的圈数
			duration : dtnTime,   //转动时间
			callback : function() { //转动结束，回调函数执行操作
				//默认让它(图片元素)停止一次
				//alert('抽奖结果:'+message);
				rotateResult(message, sourceTypeName, position, awardNum, awardType);
				$("#pointerImg").stopRotate();  //获取对象，停止旋转动画
				$("#pointerImg").rotate(0);     //变换角度为0
			}
		},'slow');
	};
}

/**
 * 抽奖结果
 */
function rotateResult(message, sourceTypeName, position, awardNum, awardType){
	var content;
	var href = "javascript:void(0);";
	if(new Number(awardType) == 1){
		href = "${path}/lottery_use_record/lott_use_record.html";
	}
	if(new Number(awardType) == 2){
		href = "${path}/lottery_use_record/lott_use_record.html";
	}
	if(new Number(awardType) == 4 || awardType ==6 ){
		href = "${path}/lottery_use_record/lott_use_record.html";
	}
	if(new Number(awardType) == 5){
		href = "${path}/lottery_use_record/lott_use_record.html";
	}
	if(awardNum == 'undefined' || awardNum == ''){
		awardNum = 0;
	}
	if(position == 7){
		content = "<p class='red f28 tc pt30'>恭喜您</p><p class='red f16 tc'>获得" + message+"</p>";
	}else{
		content = "<p class='red f28 tc pt30'>恭喜您</p><p class='red f16 tc'>获得" + message+"</p>";
	}
	$.layer({
	    type: 1,
	    title: false,
	    area: ['auto', 'auto'],
	    border: [0], //去掉默认边框
	    shade: [0], //去掉遮罩
	    //closeBtn: [0, false], //去掉默认关闭按钮
	    //shift: 'center', //从左动画弹出
	    page: {
	        html: "<div class='pop-window1 p-r'>"+content+"<div class='pop-txt1 f14 white'><a href='"+href+"'>去账户查看奖品</a></div><div class='pop-txt f14 white'>来自活动：" + sourceTypeName + "</div></div>"
	    },
	    
	    close : function(index){
			layer.close(index);
			$("#chanceTotalNumTip2").html(awardNum);
			$("#chanceTotalNum").val(awardNum);
			$('#pointerBtn').attr("onclick","rotateLottery()");
			$("#shuomingTip").removeAttr("style");
		}
	});
}

/**
 *马上推荐
 */
function recommendNow(){
	var userName = '${portal:currentUser().userName}';
	if(userName != null && userName != ''){
		window.open("${path}/activity/invited.html","_self");
	}else{
		layer.alert('您当前未登录，请先登录！',5,
				function(index) {
			window.open("${path}/member/toLoginPage.html?backUrl=1","_self");
        });
	}
}

</script>
</html>
