<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ include file="/WEB-INF/page/common/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<script type="text/javascript">
	var contextPath = '${path}';
</script>
<script type="text/javascript" src="${basePath}/js/jquery-1.11.0.js" ></script>
<script type="text/javascript" src="${basePath}/js/jquery-migrate-1.2.1.js" ></script>
<script type="text/javascript" src="${basePath}/js/BigDecimal-all-last.min.js"></script>
<script type="text/javascript" src="${basePath}/js/thickbox/thickbox.js" ></script>
<link href="${basePath}/js/thickbox/thickbox.css" rel="stylesheet" type="text/css" />

<title>顶玺金融互联网专业的P2P网站</title>
<script type="text/javascript">
	$(function() {
		//加权待收
		if(${shareholderRank != null}){
			//待收总额
	       	var day_interst = new BigDecimal('${shareholderRank.dayInterst}');
			$("#day_interst").html("￥"+day_interst.setScale(2, MathContext.prototype.ROUND_DOWN)+'');
		}
		if(${map != null}){
			var apr = Number(${map.apr});
			if(apr < 90){
				$("#map_tip").attr("style","display:none;");
			}
		}
	});
</script>
</head>

<body>
	<table width="875" border="1" align="center">
       <tr>
	     <td height="25" bgcolor="#ededed" style="width:90px;">加权待收</td>
	     <td style="width:115px;"><span id="day_interst"></span>
	     </td>
	     <td bgcolor="#ededed" style="width:90px;">加权待收排名</td>
	     <td style="width:115px;">
	      <c:if test="${shareholderRank.dayInterstRank != null}">${shareholderRank.dayInterstRank}</c:if>
	      <c:if test="${shareholderRank.dayInterstRank == null}">无</c:if>
	      <span style="float:right;">
	      	   <a href="${path}/myaccount/report/shareholderRank/1.html?keepThis=true&TB_iframe=true&amp;height=510&width=640" title="加权待收排名" class="thickbox">更多..</a>
	       &nbsp;</span>
	     </td>
	     <td width="150" bgcolor="#ededed" style="width:150px;">加权待收排名得分</td>
	     <td style="width:115px;">
	     <c:if test="${shareholderRank.dayInterstScore != null}">${shareholderRank.dayInterstScore}</c:if>
	      <c:if test="${shareholderRank.dayInterstScore == null}">无</c:if>
	     </td>	                        
	  </tr>
	  <tr>
	      <td height="25" bgcolor="#ededed">累计总积分</td>
	      <td>
	      <c:if test="${shareholderRank.accumulatepoints != null}">${shareholderRank.accumulatepoints}</c:if>
          <c:if test="${shareholderRank.accumulatepoints == null}">无</c:if>
	      </td>
	      <td height="25" bgcolor="#ededed">累计总积分排名</td>
	      <td>
	      <c:if test="${shareholderRank.accumulatepointsRank != null}">${shareholderRank.accumulatepointsRank}</c:if>
	      <c:if test="${shareholderRank.accumulatepointsRank == null}">无</c:if>
	      <span style="float:right;">
	      	<a href="${path}/myaccount/report/shareholderRank/2.html?keepThis=true&TB_iframe=true&amp;height=510&width=640" title="累计总积分排名" class="thickbox">更多..</a>
	      	&nbsp;
	      </span>
	      </td>	
	      <td height="25" bgcolor="#ededed">累计总积分排名得分</td>
	      <td>
	      <c:if test="${shareholderRank.accumulatepointsScore != null}">${shareholderRank.accumulatepointsScore}</c:if>
	      <c:if test="${shareholderRank.accumulatepointsScore == null}">无</c:if>
	      </td>
	  </tr>
	  <tr>
	  	  <td bgcolor="#ededed">投标直通车总额</td> 
	      <td>
	      <c:if test="${shareholderRank.firstTenderTotal != null}">${shareholderRank.firstTenderTotal}</c:if>
	      <c:if test="${shareholderRank.firstTenderTotal == null}">0.00</c:if>
	      </td>
	      <td height="25" bgcolor="#ededed">投标直通车总额排名</td>
	      <td>
	      <c:if test="${shareholderRank.firstTenderTotalRank != null}">${shareholderRank.firstTenderTotalRank}</c:if>
	      <c:if test="${shareholderRank.firstTenderTotalRank == null}">无</c:if>
	      <span style="float:right;">
	      	<a href="${path}/myaccount/report/shareholderRank/3.html?keepThis=true&TB_iframe=true&amp;height=510&width=640" title="投标直通车总额排名" class="thickbox">更多..</a>
	      &nbsp;</span>
	      </td>
		  <td bgcolor="#ededed">投标直通车总额排名得分</td>
	      <td>
	      <c:if test="${shareholderRank.firstTenderTotalScore != null}">${shareholderRank.firstTenderTotalScore}</c:if>
	      <c:if test="${shareholderRank.firstTenderTotalScore == null}">无</c:if>
	      </td>	                        	                      
	  </tr>
	  <tr>
	  	  <td bgcolor="#ededed">推广人数</td> 
	      <td>
	      <c:if test="${shareholderRank.extensionNumber != null}">${shareholderRank.extensionNumber}</c:if>
	      <c:if test="${shareholderRank.extensionNumber == null}">0</c:if>
	      </td>
	      <td height="25" bgcolor="#ededed">推广人数排名</td>
	      <td>
	      <c:if test="${shareholderRank.extensionNumberRank != null}">${shareholderRank.extensionNumberRank}</c:if>
	      <c:if test="${shareholderRank.extensionNumberRank == null}">无</c:if>
	      <span style="float:right;">
	      	<a href="${path}/myaccount/report/shareholderRank/4.html?keepThis=true&TB_iframe=true&amp;height=510&width=640" title="推广人数排名" class="thickbox">更多..</a>
	      &nbsp;</span>
	      </td>
		  <td bgcolor="#ededed">推广人数排名得分</td>
	      <td>
	      <c:if test="${shareholderRank.extensionNumberScore != null}">${shareholderRank.extensionNumberScore}</c:if>
	      <c:if test="${shareholderRank.extensionNumberScore == null}">无</c:if>
	      </td>	                        	                      
	  </tr>
	  <tr>
         <td height="25" bgcolor="#ededed" style="color:red;">综合得分</td>
         <td>
         <c:if test="${shareholderRank.totalScore != null}">${shareholderRank.totalScore}</c:if>
         <c:if test="${shareholderRank.totalScore == null}">无</c:if>
         </td>
         <td bgcolor="#ededed" style="color:red;">综合排名</td>
         <td>
         <c:if test="${shareholderRank.totalRank != null}">${shareholderRank.totalRank}</c:if>
         <c:if test="${shareholderRank.totalRank == null}">无</c:if>
         <span style="float:right;">
         <a href="${path}/myaccount/report/shareholderRank/5.html?keepThis=true&TB_iframe=true&amp;height=510&width=640" title="综合排名" class="thickbox">更多..</a>
         &nbsp;</span>
         </td>
         <td bgcolor="#ededed"></td>
         <td></td>
	  </tr>
  	</table>
  	<c:if test="${map !=null}">
  		<div id="map_tip" style="color:red;line-height:40px;">说明：您的资产总额减仓幅度已达到${map.apr}%，无法参与股东加权排名。（今天0点时的资产总额：${map.day_total }，历史最大资产总额：${map.max_total }）</div>
  	</c:if>
</body>
</html>
