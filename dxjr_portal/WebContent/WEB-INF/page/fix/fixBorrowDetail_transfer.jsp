<%@page import="java.util.Date"%>
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
<input id="publishTime" type="hidden" value="${fixBorrowVo.publishTime}">
<!-- 当前时间 -->
<input type="hidden" id="nowTime" value="<fmt:formatDate value="<%=new Date()%>" pattern="yyyy/MM/dd HH:mm:ss"/>" />
				
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
                        <div class="precent-detail"><span class="fl f14">加入进度</span>
                        <div class="clearboth">
                        <div class="votebox" style="width:220px; display:inline-block;">
                            <dl class="barbox" style="margin-top:18px;">
                                <dd class="barline"  style="width:140px;">
                                    <div w="100" style="width:0px;" class="charts"></div>
                                </dd>   
                            </dl><span class="f14" style="padding-left:5px; color:#999;margin-top:8px;">100%</span>
                         </div>
                     	</div>
                        </div>
                   </li>
                </ul>
                <ul class="detailword">
                	<li style="padding-right:288px;"><h6>保障方式&nbsp;&nbsp;&nbsp;&nbsp;<span class="bule">风险保证金保障</span></h6></li>
                </ul>
             </div>
             <div class="detail-col-r fr">
             	<div class="detali-syz">
                	<div class="pd30">
                	<h5 class="f14 line32">距离退出还有</h5>
                    <h2><span class="f72">${fixBorrowVo.endDays}</span><small>天</small></h2>
					<div class="btn-box">
                         <a href="javascript:void(0);" class="btn btn-gcsyz f18" style="width:100%; cursor:default">收益中</a>
                    </div>
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
<%@ include file="/WEB-INF/page/common/footer.jsp"%>
</body>

<script type="text/javascript">
//进度条设置
$("#bar").css("width","0px");
var nowWidth = parseFloat(300);
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
</script>
</html>
