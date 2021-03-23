<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/page/common/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>资金周转_投资理财平台_p2p网络投资理财-顶玺金融官网</title>
<meta name="keywords" content="资金周转,投资理财平台,p2p网络投资理财" />
<meta name="description" content="顶玺金融是中国3A信用评级互联网理财借贷平台，如果你想了解更多P2P理财、网络投资理财或者资金周转借贷信息，详情请登陆www.dxjr.com。">
<%@ include file="/WEB-INF/page/common/public2.jsp"%>

</head>

<body>
<!--头部开始-->
<%@ include file="/WEB-INF/page/common/header.jsp"%>
<input id="publishTime" type="hidden" value="<fmt:formatDate value="${fixBorrowVo.publishTime}" pattern="yyyy/MM/dd HH:mm:ss"/>">
<!-- 标时间 -->
<input type="hidden" id="endTime" value="<fmt:formatDate value="${fixBorrowVo.endTime}" pattern="yyyy/MM/dd HH:mm:ss"/>" />
<!-- 当前时间 -->
<input type="hidden" id="nowTime" value="<fmt:formatDate value="${nowTime }" pattern="yyyy/MM/dd HH:mm:ss"/>" />
<div class="product-wrap wrapperbanner"><!--定期宝star-->
	<div class="grid-1100">
    	<div class="product-deatil clearfix ">
        	<h1 class="f16">定期宝【${fixBorrowVo.lockLimit}月宝】${fixBorrowVo.contractNo}
        	<c:if test="${fixBorrowVo.areaType==1}"><img src="${basePath}/images/new-icon.png?version=<%=version%>"/></c:if>
        	</h1>
            <div class="detail-col-l fl item-bd-r">
            	<ul class="item-bd-b">
                	<li class="bdr percentbig"><span class="f14 gary line200">预期收益率</span><span class="oren precent"><fmt:formatNumber value="${fixBorrowVo.apr}" pattern="#,##0.##"/></span><small class="f18 oren">%</small></li>
                	<li class="percentbig line36 word">
                    	<p><span>项目期限</span><label><strong>${fixBorrowVo.lockLimit}</strong>个月</label></p>
                        <p><span>开放金额 </span><label><strong><fmt:formatNumber value="${fixBorrowVo.planAccount}" pattern="#,##0.##" /></strong>元</label></p>
                   </li>
                </ul>
                <ul class="detailword">
                	<li style="padding-right:288px;"><h6>保障方式&nbsp;&nbsp;&nbsp;&nbsp;<span class="bule">风险保证金保障</span></h6></li>
                </ul>
             </div>
             <div class="detail-col-r fr">
             	<div class="detali-bg detali-syz">
                	
                    <div class="pd30">
                	<h5 class="f14 line32">距离开放加入时间还剩</h5>
                    <h2><span class="f24" id="remainingTime">&nbsp;&nbsp;</span></h2>
					<div class="btn-box">
                         <a href="javascript:void(0);" class="btn btn-tmbg f18" style="width:100%; cursor:default">敬请期待</a>
                    </div>
                    </div>
                    
                    <i class="gc-img-wap syz"></i>
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
<%@ include file="/WEB-INF/page/common/footer.jsp"%>
</body>

<script type="text/javascript">
	/**秒数*/
	var SysSecond;
	/**循环计算倒计时*/
	var InterValObj;

	$(function() {
		var borrowStatus = '${fixBorrowVo.joinStatus}';
		var nowTime = $("#nowTime").val();
		/**预发标,发标剩余时间*/
		if (borrowStatus == "0") {
			var publishTime = $("#publishTime").val();
			//这里获取倒计时的起始时间 
			SysSecond = parseFloat(new Date(publishTime).getTime() / 1000)
					- parseFloat(new Date(nowTime).getTime() / 1000);
			//SysSecond = parseInt((new Date('<fmt:formatDate value="${firstBorrow.endTime}" pattern="yyyy/MM/dd HH:mm:ss"/>').getTime() / 1000) - new Date('${nowTime}').getTime() / 1000); //这里获取倒计时的起始时间 
			//剩余时间
			InterValObj = window.setInterval(SetRemainTime, 1000); //间隔函数，1秒执行 
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
			if (day > 0) {
				$("#remainingTime").html(
						'<strong>' + day + '</strong>天<strong>' + hour
								+ '</strong>时<strong>' + minute
								+ '</strong>分<strong>' + second + '</strong>秒');

			}else if (hour > 0) {
				$("#remainingTime").html(
						'<strong>' + hour
								+ '</strong>时<strong>' + minute
								+ '</strong>分<strong>' + second + '</strong>秒');

			}else if (minute > 0) {
				$("#remainingTime").html(
						'<strong>' + minute
								+ '</strong>分<strong>' + second + '</strong>秒');

			} else {
				$("#remainingTime").html(
						'<strong>' + second + '</strong>秒');
			}

		} else {//剩余时间小于或等于0的时候，就停止间隔函数 
			window.clearInterval(InterValObj);
			//这里可以添加倒计时时间为0后需要执行的事件 
			//$("#remainingTime").html("0天0时0分0秒");
			window.location.reload();
		}
	}
	$(document).ready(function() {
		//searchFixBorrowListStart(1);
	});

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
	 *  show 协议
	 */
	function toShowFixXiyi(id) {
		// 判断用户是否已登录
		if(${null==portal:currentUser()}){
			 layer.msg("请先登录");
			 return;
		}
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
				src : '${basePath }/dingqibao/toShowFixXiyi/' + id + '.html'
			},
			close : function(index) {
				layer.close(index);
			}
	   	});
	}

		//进度条设置
		$("#bar").css("width","0px");
	   var nowWidth = parseFloat(0);
	   //宽度要不能大于进度条的总宽度
	   if(nowWidth<=300){
	    $("#bar").css("width",nowWidth+"px");
	   }
	   
	 	//页面加载首次调用
	   searchFixTenderDetailList(1,${fixBorrowVo.id});
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
</script>
</html>
