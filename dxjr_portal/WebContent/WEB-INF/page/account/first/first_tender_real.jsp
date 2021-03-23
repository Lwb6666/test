<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/page/common/taglib.jsp"%>
<div class="product-deatil clearfix ">
<div class="tz-dqb2 clearfix">
    <div class="col clearfix">
            <span class="fl gary2">开通状态：</span>
            <div class="btn-box-bg">
                <div class="term"><a href="javascript:forqueryrecord('all');"  <c:if test="${status == ''|| status == null }"> class="active" </c:if> >全部</a></div>
                <div class="term"><a href="javascript:forqueryrecord('0');"   <c:if test="${status == '0'}">class="active"</c:if>>未解锁</a></div>
                <div class="term"><a href="javascript:forqueryrecord('1');" <c:if test="${status == '1'}">class="active"</c:if>>已解锁（含已转让）</a></div>
            </div>
    </div>
	<div class="col clearfix">
       <span class="fl gary2">开通时间：</span>
       <div class="btn-box-bg">
           <div class="term"><a href="javascript:forqueryrecord('','all');" <c:if test="${timeScope == null || timeScope == ''}">class="active"</c:if> >全部</a></div>
           <div class="term">
            <input type="hidden" name="status" id="status" value="${status }">
          	<input type="hidden" name="timeScope" id="timeScope" value="${timeScope }"/>
        				<input type="text" name="beginTime" id="beginTime" class="Wdate" value="${beginTime}" onClick="WdatePicker();"> 
        				 至 <input type="text" name="endTime" id="endTime" class="Wdate" value="${endTime}" onClick="WdatePicker();">
           </div>
	       <div class="term"><a class="active" href="javascript:forqueryrecord();">查询</a></div>
          	
           <div class="term"><a href="javascript:forqueryrecord('','month');" <c:if test="${timeScope == 'month'}">class="active"</c:if> >近1月</a></div>
           <div class="term"><a href="javascript:forqueryrecord('','sixmonth');" <c:if test="${timeScope == 'sixmonth'}">class="active"</c:if> >近6月</a></div>
           <div class="term"><a href="javascript:forqueryrecord('','sixmonthago');"  <c:if test="${timeScope == 'sixmonthago'}">class="active"</c:if> >6月前</a></div>
       </div>
	</div>
</div>
             
                <table width="100%" border="0" cellspacing="0" cellpadding="0"  class="table center notr">
                  <thead>
                  <tr class="tbl-title">
                    <td>开通时间</td>
                    <td align="left">投标序号</td>
                    <td>开通金额（元）</td>
                    <td>剩余未投（元）</td>
                    <td>已获收益（元）</td>
                    <td>状态</td>
                    <td>操作</td>
                  </tr>
                  </thead>
                  <tbody>
                  <c:forEach items="${list}" var="firstTenderReal" varStatus="sta" step="1">
                  <tr>
                    <td><fmt:formatDate value="${firstTenderReal.addtime}" pattern="yyyy-MM-dd HH:mm:ss"></fmt:formatDate></td>
                    <td align="left"><c:if test="${firstTenderReal.parentId!=null}"><font  color='red' >【直】</font></c:if>第${firstTenderReal.orderNum}位</td>
                    <td><fmt:formatNumber value="${firstTenderReal.account}" pattern="#,##0.00"/></td>
                    <td><fmt:formatNumber value="${firstTenderReal.useMoney}" pattern="#,##0.00"/> </td>
                    <td><fmt:formatNumber value="${firstTenderReal.firstEarnedIncome}" pattern="#,##0.00"/></td>
                    <td><c:if test="${firstTenderReal.status eq '0'}">未解锁</c:if><c:if test="${firstTenderReal.status eq '1'}">已解锁</c:if></td>
                    <td class="blue">
                    	<c:if test="${null != firstTenderReal.unLockYn && 'Y'==firstTenderReal.unLockYn}"> 
                    		<a href="javascript:toUnlockFirst('${firstTenderReal.id}');">解锁申请</a>
                    	</c:if>
					
						<c:choose>
							<c:when test="${firstTenderReal.parentId!=null}">
								<a class="#" href="javascript:toFirstTransferXiyi('${firstTenderReal.id}');" target="_parent">协议</a>
							</c:when>
							<c:otherwise>
								<a class="#" href="javascript:toFirstTenderXiyi('${firstTenderReal.id}');" target="_parent">协议</a>
							</c:otherwise>
						</c:choose>
						<a class="#" href="javascript:showTenderBorrow('${firstTenderReal.id}');">详情</a>
						
<%--                     	<a class="#" href="javascript:queryTenderRecord('${firstTenderReal.id}');">详情</a> --%>
                    </td>
                  </tr>
                  <tr class="showtr" id="${firstTenderReal.id}">
                  </tr>
                  </c:forEach>
                  </tbody>
                </table>
<c:if test="${page.result==null  || page.totalCount==0 }">
	<div align="center"  style="height: 70px;line-height: 70px">暂无相关信息</div>
</c:if>     
<c:if test="${page.result !=null && page.totalCount>0 }">                    

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
        
        

        <script >
      //查看借款协议
        function toFirstTenderXiyi(id){
        	$.layer({
        		type : 2,
        		fix : false,
        		shade : [0.6, '#E3E3E3' , true],
        		shadeClose : true,
        		border : [10 , 0.7 , '#272822', true],
        		title : ['',false],
        		offset : ['50px',''],
        		area : ['1075px','450px'],
        		iframe : {src : '${basePath}/account/InvestManager/toFirstTenderXiyi/' + id + '.html'},
        		close : function(index){
        			layer.close(index);
        		}
        	});
        }
        //查看直通车转让协议
        function toFirstTransferXiyi(id){
        	$.layer({
        		type : 2,
        		fix : false,
        		shade : [0.6, '#E3E3E3' , true],
        		shadeClose : true,
        		border : [10 , 0.7 , '#272822', true],
        		title : ['',false],
        		offset : ['50px',''],
        		area : ['1075px','450px'],
        		iframe : {src : '${basePath}/account/InvestManager/toFirstRealTransferXiyi/' + id + '.html'},
        		close : function(index){
        			layer.close(index);
        		}
        	});
        }
        <%--
        申请解锁 
        --%>
        function toUnlockFirst(firstTenderRealId){
        	  if(layer.confirm("你确定要解锁吗?",function(){
        		$.ajax({
        			url : '${basePath}/first/tenderreal/unlock/'+firstTenderRealId+'.html',
        			type : 'post',
        			data : {
        			},
        			success : function(result) {
        				if (result== 'success') {
        					layer.alert("解锁成功" , 1, "温馨提示");
        					findPage(1);
        				} else {
        					layer.alert(result);
        				}
        			},
        			error : function(result) {
        				layer.alert("操作异常,请刷新页面或稍后重试！");
        		    }
        		});
        	}));

        }

        /**查询处理*/
        function forqueryrecord(sta,scopeTime){
        	if(sta != null && sta != ''){  //赋值状态 
        		if(sta == 'all'){
        			$('#status').val('');
        		} else {
        			$('#status').val(sta);
        		}
        	}
        	
        	if(scopeTime != null && scopeTime != ''){
        		if(scopeTime == 'all'){
        			$("#beginTime").val('');
        			$("#endTime").val('');
        			$('#timeScope').val('');
        		} else {
        			$('#timeScope').val(scopeTime);
        		}
        	}
        	var beginTime = $("#beginTime").val();
        	var endTime = $("#endTime").val();
        	var status = $("#status").val();
        	var timeScope = $('#timeScope').val();
        	
        	window.parent.loadFirstTenderPage('${basePath}/account/InvestManager/queryFirstTenderReal/1.html?status='+status+'&beginTime=' + beginTime + '&endTime=' + endTime+'&timeScope='+timeScope);
        }
        /**查看明细*/
        function queryFirstTenderDetail(id){
        	$("#ztclbSpanId").append('<span><a href="#">查看明细</a></span>');
        	window.parent.loadFirstTenderPage('${basePath}/account/InvestManager/queryFirstTenderDetail.html?firstTenderRealId='+id+'&pageNum=1');
        }
        /**查看投标*/
        function queryTenderRecord(id){
        	$("#ztclbSpanId").append('<span><a href="#">查看投标</a></span>');
        	window.parent.loadFirstTenderPage('${basePath}/account/InvestManager/collectionFirst_record/1.html?collectionStatus=unRec&firstTenderRealId='+id);
        }
        
        
        
         
        /**翻页处理*/
        function findPage(pageNo){
        	var beginTime = $("#beginTime").val();
        	var endTime = $("#endTime").val();
        	var status = $("#status").val();
        	window.parent.loadFirstTenderPage('${basePath}/account/InvestManager/queryFirstTenderReal/'+pageNo+'.html?status='+status+'&beginTime=' + beginTime + '&endTime=' + endTime);
        }
        /** 直通车列表 */
        function queryFirstTenderRealList(stauts){
        	window.parent.loadFirstTenderPage('${basePath}/account/InvestManager/queryFirstTenderReal/1.html?status='+stauts);
        }
        
        function showTenderBorrow(id){
        	jQuery(function(){
       		 	$("tr[class^='showtr']").each(function(i) {
       			 	$(this).css("display","none");
       		 	});
        	}); 
        	$.ajax({
    			url : '${basePath}/account/InvestManager/showTenderBorrow.html',
    			data : {
    				realId : id
    			},
    			type : 'post',
    			dataType : 'text',
    			success : function(data) {
    				$("#"+id).html(data);
    				$("#"+id).css('display','table-row'); 
    			},
    			error : function(data) {
    				layer.msg('网络连接异常，请刷新页面或稍后重试！', 1, 5);
    			}
    		});
        }
        </script>