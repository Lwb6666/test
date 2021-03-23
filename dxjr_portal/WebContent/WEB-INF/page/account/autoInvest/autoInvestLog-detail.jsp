<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/page/common/taglib.jsp"%>
<%@ include file="/WEB-INF/page/common/public.jsp"%>
<div class="myid nobordertop whitebg">
	<input type="hidden" id="autoType" value="${autoInvestConfigRecord.autoType }"/>
	<input type="hidden" id="addtime" value="<fmt:formatDate value="${autoInvestConfigRecord.addtime}" pattern="yyyy/MM/dd HH:mm:ss"/>"/>
    <table style="">
		<tr>
			<td class="xq_currenttd" style="border-right:0px;">编号</td>
			<td class="">${autoInvestConfigRecord.auto_tender_id}</td>
			<td class="xq_currenttd" style="border-right:0px;">是否启用</td>
			<td class="">
			<c:if test="${autoInvestConfigRecord.status==0 }">未启用</c:if>
			<c:if test="${autoInvestConfigRecord.status==1 }">已启用</c:if>
			</td>
			<td class="xq_currenttd" style="border-right:0px;">排队号</td>
			<td>
			<c:if test="${autoInvestConfigRecord.rownum!=null}">第&nbsp;<span class="xq_sumber">${autoInvestConfigRecord.rownum}</span>&nbsp;位</c:if>
			<c:if test="${autoInvestConfigRecord.rownum==null}">无</c:if>
			</td>
		</tr>
	  	<tr>
	  		<td class="xq_currenttd" style="border-right:0px;">投标额度</td>
	        <td class="">
	        <c:if test="${autoInvestConfigRecord.record_type==2 }">
	        <fmt:formatNumber value="${autoInvestConfigRecord.tender_record_accout}" pattern="#,##0.00"></fmt:formatNumber>
		    </c:if>
		    <c:if test="${autoInvestConfigRecord.record_type !=2 }">
		   		<c:if test="${autoInvestConfigRecord.tender_type==1 }">${autoInvestConfigRecord.tender_account_auto}</c:if>
				<c:if test="${autoInvestConfigRecord.tender_type==2 }">${autoInvestConfigRecord.tender_scale}%</c:if>
				<c:if test="${autoInvestConfigRecord.tender_type==3 }"><fmt:formatNumber value="${accountVo.useMoney}" pattern="#,##0.00"></fmt:formatNumber></c:if>
			</c:if>
	        </td>
	        <td class="xq_currenttd" style="border-right:0px;">投标方式</td>
	        <td class="">
	        <c:if test="${autoInvestConfigRecord.tender_type==1 }">按金额投标</c:if>
			<c:if test="${autoInvestConfigRecord.tender_type==2 }">按比例投标</c:if>
			<c:if test="${autoInvestConfigRecord.tender_type==3 }">按余额投标</c:if>
	        </td>
	        <td class="xq_currenttd" style="border-right:0px;">自动类型</td>
			<td>
			<c:if test="${autoInvestConfigRecord.autoType==1 && autoInvestConfigRecord.addtimeStamp < 1434535200}">按抵押标、担保标投标</c:if>
		  	<c:if test="${autoInvestConfigRecord.autoType==1 && autoInvestConfigRecord.addtimeStamp >= 1434535200}"><span title="按抵押标、担保标、信用标投标">按抵押标、担保标、信..</span></c:if>
			<c:if test="${autoInvestConfigRecord.autoType==2 && autoInvestConfigRecord.addtimeStamp < 1433088000}">按净值标、信用标投标</c:if>
			<c:if test="${autoInvestConfigRecord.autoType==2 && autoInvestConfigRecord.addtimeStamp >= 1433088000}">按信用标投标</c:if>
			<c:if test="${autoInvestConfigRecord.autoType==null || autoInvestConfigRecord.autoType==''}">历史类型</c:if>
			</td>
        </tr>
        <tr>
        	<td class="xq_currenttd" style="border-right:0px;">最低投标金额</td>
	        <td><fmt:formatNumber value="${autoInvestConfigRecord.min_tender_account}" pattern="#,##0.00"/></td>
	        <td class="xq_currenttd" style="border-right:0px;">借款期限</td>
			<td class="">
			<c:if test="${autoInvestConfigRecord.timelimit_status==1}">无限定</c:if>
			<c:if test="${autoInvestConfigRecord.timelimit_status==0 && autoInvestConfigRecord.min_time_limit > 0}">按月：${autoInvestConfigRecord.min_time_limit}~${autoInvestConfigRecord.max_time_limit}个月<br/></c:if>
			<c:if test="${autoInvestConfigRecord.timelimit_status==0 && autoInvestConfigRecord.min_day_limit > 0 && (autoInvestConfigRecord.borrow_type1_status == 1 || autoInvestConfigRecord.borrow_type3_status == 1)}">按天：${autoInvestConfigRecord.min_day_limit}~${autoInvestConfigRecord.max_day_limit}天</c:if>
			</td>
			<td class="xq_currenttd" style="border-right:0px;">还款方式</td>
			<td class="">
			<c:if test="${autoInvestConfigRecord.borrow_type==0 }">无限定</c:if>
			<c:if test="${autoInvestConfigRecord.borrow_type==1 }">等额本息</c:if>
		 	<c:if test="${autoInvestConfigRecord.borrow_type==2 }">按月付息到期还本</c:if>
		 	<c:if test="${autoInvestConfigRecord.borrow_type==3 }">到期还本付息</c:if>
			<c:if test="${autoInvestConfigRecord.borrow_type==4 }">按天还款</c:if>
			</td>
        </tr>
        <tr>
			<td class="xq_currenttd" style="border-right:0px;">标的类型</td>
			<td class="">
			<c:if test="${autoInvestConfigRecord.borrow_type3_status==1}">净值标<br/></c:if>
			<c:if test="${autoInvestConfigRecord.borrow_type2_status==1}">抵押标<br/></c:if>
			<c:if test="${autoInvestConfigRecord.borrow_type1_status==1}">信用标<br/></c:if>
			<c:if test="${autoInvestConfigRecord.borrow_type5_status==1}">担保标</c:if>
			</td>
			<td class="xq_currenttd" style="border-right:0px;">年化收益率</td>
			<td>
			<c:if test="${autoInvestConfigRecord.min_apr==null && autoInvestConfigRecord.max_apr==null}">无限定</c:if>
			<c:if test="${autoInvestConfigRecord.min_apr!=null && autoInvestConfigRecord.min_apr > 0}">${autoInvestConfigRecord.min_apr }%~${autoInvestConfigRecord.max_apr }%</c:if>
			</td>
			<td class="xq_currenttd" style="border-right:0px;">修改时间</td>
			<td class="">
			<fmt:formatDate value="${autoInvestConfigRecord.addtime}" pattern="yyyy-MM-dd HH:mm:ss"/>
			</td>
      	</tr>
      	<tr>
      		<td class="xq_currenttd" style="border-right:0px;">记录类型</td>
			<td>
			<c:if test="${autoInvestConfigRecord.record_type==1}">修改<br/></c:if>
			<c:if test="${autoInvestConfigRecord.record_type==2}">投标成功<br/></c:if>
			<c:if test="${autoInvestConfigRecord.record_type==3}">删除</c:if>
			</td>
			<td class="xq_currenttd" style="border-right:0px;">所投标类型</td>
			<td>${autoInvestConfigRecord.borrowTypeStr}</td>
			<td class="xq_currenttd" style="border-right:0px;">所投标</td>
			<td>
			<c:if test="${autoInvestConfigRecord.borrowId != null}">
		      <a href="${path}/toubiao/${autoInvestConfigRecord.borrowId}.html" title="${autoInvestConfigRecord.borrowName }" target="_blank">${fn:substring(autoInvestConfigRecord.borrowName,0,20)}<c:if test="${fn:length(autoInvestConfigRecord.borrowName)>20}">..</c:if></a>
		    </c:if>
			</td>
      	</tr>
      	<tr>
	    <td class="xq_currenttd" style="border-right:0px;">备注</td>
		<td colspan="5">
		${autoInvestConfigRecord.remark}
		</td>
	    </tr>
    </table>
    <div class="gg_btn"><input type="button" value="返回" onclick="backAutoInvestRecord()"/></div>
</div>
<script type="text/javascript">
function backAutoInvestRecord(){
	$("#auto_invest_record").addClass("men_li");
	$("#auto_invest_list").removeClass("men_li");
	$("#add_auto_invest").removeClass("men_li");
	$("#autoTip").html("添加自动投标");
	$.ajax({
		url : '${basePath}/myaccount/autoInvest/queryAutoTenterLog/10.html?pageNum='+'${pageNo}',
		data :{
		} ,
		type : 'post',
		dataType : 'text',
		success : function(data){
			$("#main_content").html(data);
		},
		error : function(data) {
			layer.msg("网络连接异常，请刷新页面或稍后重试！", 2, 5);
	    }
	});
}
</script>