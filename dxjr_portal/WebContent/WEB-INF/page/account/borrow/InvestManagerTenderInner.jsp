<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/page/common/taglib.jsp"%>
<div class="product-deatil clearfix ">
	<div class="tz-dqb2 clearfix">
		<div class="col clearfix">
		    <span class="fl gary2">是否存管：</span>
		    <div class="btn-box-bg">
		        <div class="type">
		            <a <c:if test="${custody== '' || custody == null }"> class="active"</c:if> href="javascript:queryTendering('','all')">全部</a>
		        </div>
		        <div class="type">
		            <a <c:if test="${custody == '0' }"> class="active"</c:if> href="javascript:queryTendering('','0')">非存管</a>
		        </div>	
		        <div class="type">
		            <a <c:if test="${custody == '1' }"> class="active"</c:if> href="javascript:queryTendering('','1')">存管</a>
		        </div>
		         <input type="hidden" value="${custody }" name="custody" id="custody" />
		    </div>
		</div>
		<div class="col clearfix">
			<span class="fl gary2">标的状态：</span>
			<div class="btn-box-bg">
			    <div class="term"><a <c:if test="${borrowStatus == null || borrowStatus == ''}" > class="active"</c:if> href="javascript:queryTendering('nochange','','all'); ">全部</a></div>
			    <div class="term"><a <c:if test="${borrowStatus == 'sucess'}" >class="active"</c:if>  href="javascript:queryTendering('nochange','','sucess');" >收益中</a></div>
			    <div class="term"><a <c:if test="${borrowStatus == 'underway'}" >class="active"</c:if>  href="javascript:queryTendering('nochange','','underway');" >投标中</a></div>
			    <div class="term"><a <c:if test="${borrowStatus == 'end'}" >class="active"</c:if> href="javascript:queryTendering('nochange','','end');" >已结清（含已转让）</a></div><!-- 5 -->
  		         <input type="hidden" value="${borrowStatus }" name="borrowStatus" id="borrowStatus" />
			</div>
		</div>
             
		<div class="col clearfix">
			<span class="fl gary2">投资时间：</span>
				<div class="btn-box-bg">
				    <div class="term"><a <c:if test="${timeScope == 'all' || timeScope == '' || timeScope== null}"> class="active"</c:if>  href="javascript:queryTendering('all');">全部</a></div>
				    <div class="term">
				    	<input type="text" name="beginTime" id="beginTime" class="Wdate" value="${beginTime}" onClick="WdatePicker();"> 至 
				    	<input type="text" name="endTime" id="endTime" class="Wdate" value="${endTime}" onClick="WdatePicker();">
				    </div>
				    <div class="term"><a href="javascript:queryTendering();" class="active" >查询</a></div>
				    
				    <div class="term"><a <c:if test="${timeScope == 'month'}" >class="active"</c:if> href="javascript:queryTendering('month');" >近1月</a></div>
				    <div class="term"><a <c:if test="${timeScope == 'threemonth'}" >class="active"</c:if> href="javascript:queryTendering('threemonth');">近3月</a></div>
				    <div class="term"><a <c:if test="${timeScope == 'sixmonth'}" >class="active"</c:if> href="javascript:queryTendering('sixmonth');">近6月</a></div>
				    
                    <input type="hidden" id="timeScope" name="timeScope" value="${timeScope }"/>				    
				</div>
		</div>            
	</div>
             
<table width="100%" border="0" cellspacing="0" cellpadding="0"  class="table center tbtr02">
	<thead>
		<tr class="tbl-title">
			<td>投标时间</td>
			<td align="left">借款标题</td>
			<td>预期收益</td>
			<td>期限</td>
			<td>投资金额</td>
			<td>状态</td>
			<td>操作</td>
		</tr>
	</thead>
    <tbody>        
  		<c:forEach items="${borrowlist}" var="borrow" varStatus="sta" step="1">          
		<tr>
		  	<td><fmt:formatDate value="${borrow.addtime }" pattern="yyyy-MM-dd" />  </td>
			<td align="left">
			<c:if test="${borrow.isCustody == 1}">
				<i class="icon icon-zs pdr" title="银行存管"></i>
			</c:if>
			<a target="_blank" href="${path }/toubiao/${borrow.id}.html">${fn:substring(borrow.name,0,7)}<c:if test="${fn:length(borrow.name)>7}">..</c:if></a><c:if test="${borrow.tenderType==1}"><img src="${basePath}/images/account/zdicon.png"/></c:if></td>
			<c:choose>
			   <c:when test="${borrow.ishowfloat==1 && borrow.isCustody != 1}">
				    <td><span><fmt:formatNumber value="${borrow.apr }" pattern="#,##0.##" />%</span> <span style="position:absolute; color:red; font-size:12px">+${borrow.yearApr}%</span></td>
			   </c:when>
			   <c:otherwise>
                  <td><fmt:formatNumber value="${borrow.apr }" pattern="#,##0.##" />%</td>
			   </c:otherwise>
			</c:choose>
			<td>
				<c:if test="${borrow.borrowtype==4}">秒还</c:if> 
				<c:if test="${borrow.borrowtype!=4 && borrow.style != 4 }">${borrow.timeLimit }个月</c:if>
				<c:if test="${borrow.borrowtype!=4 && borrow.style == 4 }">${borrow.timeLimit }天</c:if>
			 </td>
			<td>${borrow.rbtAccount} </td>
		  <td><c:if test="${borrow.status == 2}">投标中</c:if>
		      <c:if test="${borrow.status == 4}">收益中</c:if>
		      <c:if test="${borrow.status == 6}">满标银行复审中</c:if>
		      <c:if test="${(borrow.status == 5||borrow.status == 42) && borrow.tenderStatus == 2}">已结清</c:if>
			  <c:if test="${(borrow.status == 5||borrow.status == 42) && borrow.tenderStatus == 1}">收益中</c:if>
		  </td>
		    <td><a href="javascript:void(0);"  data-reveal-id="userUollection" data-animation="fade"  onclick="openWin(${borrow.id },${borrow.recordId });" class="blue mll0">查看待收</a> </td>
		</tr>
		</c:forEach>
    </tbody>                 
</table>
          
          
                
<c:if test="${page.result==null  || page.totalCount==0 }">
	<div align="center"  style="height: 70px;line-height: 70px">暂无相关信息</div>
</c:if>    
<c:if test="${page.result !=null && page.totalCount > 0 }">
		<div class="yema">
			<div class="yema_cont">
				<div class="yema rt">
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
</c:if>
		
        </div>
        
        
	 <div class="layer-jion" style="display:none" id="ds">
	</div>
<script type="text/javascript">
	$(document).ready(function(){ 
	    var color="#f0f7ff"
	    $(".tbtr tr:even,.tbtr01 tr:even,.tbtr02 tr:even,.tbtr03 tr:even,.tbtr04 tr:even,.tbtr05 tr:even,.tbtr06 tr:even,.tbtr07 tr:even,.tbtr08 tr:even,.tbtr09 tr:even,.tbtr10 tr:even,.tbtr11 tr:even,.tbtr12 tr:even,.tbtr13 tr:even,.tbtr14 tr:even,.tbtr15 tr:even,.tbtr16 tr:even,.tbtr17 tr:even").attr("bgcolor",color);//改变奇数行背景色
	})	
    /**
     * hujianpan
     * 切换统计数据,即已收和待收页签的切换
     */
    function toggleListInner(borrowStatus){
    	window.parent.toggleList(borrowStatus);
    }
    /**
     * ajax 翻页功能
     */
    function findPage(pageNo) {
    	window.parent.searchQueryTender(pageNo);
    }

    //查询
    function queryTendering(scopeTime,custody,status){
    	if(scopeTime != '' && scopeTime != null && scopeTime != "nochange"){
			$('#timeScope').val(scopeTime);
		} else if(scopeTime == '' || scopeTime == null){
			$('#timeScope').val("");
		}
    	
		if(custody != null && custody != '' && custody != 'all'){
			$('#custody').val(custody);
		} else if (custody == 'all'){
			$('#custody').val('');
		}
		
		if(status != null && status != '' && status != 'all'){
			$('#borrowStatus').val(status);
		} else if (status == 'all'){
			$('#borrowStatus').val('');
		}
		
    	var pageNum = 1;
    	window.parent.searchQueryTender(pageNum);
    }
    
    /*打开待收弹出层  borrowId(借款标id) recordId 投标记录ID */
  function openWin(borrowId,tenderId){
//     	$("#div_id").load("${basePath}/account/InvestManager/searchRecordList");
		$.ajax({
			url : '${basePath}/account/InvestManager/searchRecordList.html',
			data : {
				borrowId : borrowId,
				tenderId : tenderId,
			},
			type : 'post',
			dataType : 'html',
			success : function(data) {
				$("#userUollection").html(data);
			},
			error : function(data) {
				layer.msg('网络连接异常，请刷新页面或稍后重试！', 1, 5);
			}
		});
    }

</script>