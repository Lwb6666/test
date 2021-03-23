<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/page/common/taglib.jsp"%>
<div class="product-deatil clearfix ">
            <div class="tz-dqb2 clearfix">
                <div class="col clearfix"><span class="fl gary2">是否存管：</span>
                  <div class="btn-box-bg">
                        <div class="type">
                            <a <c:if test="${custody== '' || custody == null }"> class="active"</c:if> href="javascript:queryrecord('receivedCollection_record','nochange','all');">全部</a>
                        </div>
                        <div class="type">
                            <a <c:if test="${custody == '0' }"> class="active"</c:if> href="javascript:queryrecord('receivedCollection_record','nochange','0');">非存管</a>
                        </div>
                        <div class="type">
                            <a <c:if test="${custody == '1' }"> class="active"</c:if> href="javascript:queryrecord('receivedCollection_record','nochange','1');">存管</a>
                        </div>
                        <input type="hidden" value="${custody }" name="custody" id="custody" />
                    </div>
                </div>
               <div class="col clearfix">
                        <span class="fl gary2">待收状态：</span>
                        <div class="btn-box-bg">
                             <div class="term"><a href="javascript:forqueryrecord('unCollection_record');" >待收明细</a></div>
                            <div class="term"><a class="active" href="javascript:queryrecord('receivedCollection_record','nochange');" >已收明细</a></div>
                        </div>
                </div>
                
                <div class="col clearfix">
                        <span class="fl gary2">收款时间：</span>
                        <div class="btn-box-bg">
                            <div class="term"><a <c:if test="${timeScope == 'all' || timeScope == '' || timeScope== null}"> class="active"</c:if> href="javascript:queryrecord('receivedCollection_record','all');">全部</a></div>
                            <div class="term">
	                            <input type="text" name="beginTime" id="beginTime" class="Wdate" value="${beginTime}" onClick="WdatePicker();"> 至 
	                            <input type="text" name="endTime" id="endTime" class="Wdate" value="${endTime}" onClick="WdatePicker();">
                            </div>
                            <div class="term"><a href="javascript:queryrecord('receivedCollection_record');" class="active" >查询</a></div>
                            
                            <input type="hidden" id="timeScope" name="timeScope" value="${timeScope }"/>
                            <div class="term"><a <c:if test="${timeScope == 'month'}" >class="active"</c:if> href="javascript:queryrecord('receivedCollection_record','month');" >近1月</a></div>
                            <div class="term"><a <c:if test="${timeScope == 'threemonth'}" >class="active"</c:if> href="javascript:queryrecord('receivedCollection_record','threemonth');">近3月</a></div>
                            <div class="term"><a <c:if test="${timeScope == 'sixmonth'}" >class="active"</c:if> href="javascript:queryrecord('receivedCollection_record','sixmonth');">近6月</a></div>

                        </div>
			</div>
            
            <div class="col clearfix">
                     <div class="ztc-list">
                     	<p><em>查询区间段待收总额：</em><span class="orange2">${collectionStatisticVo.totalSumFmt } 元</span></p>
                        <p><em>其中本金：</em><span class="orange2">${collectionStatisticVo.capitalSumFmt }  元</span></p>
                        <p><em>利息：</em><span class="orange2">${collectionStatisticVo.interestSumFmt }  元</span></p>
                        <p><em>逾期罚息：</em><span class="orange2">${collectionStatisticVo.lateInterestSumFmt } 元</span></p>
                     </div>
                     </div>

             </div>
             
                <table width="100%" border="0" cellspacing="0" cellpadding="0"  class="table center tbtr">
                  <thead>
                  <tr class="tbl-title">
                    <td>实收款时间</td>
                    <td align="left">借款标题</td>
                    <td>预期收益</td>
                    <td>周期</td>
                    <td>期数</td>
                    <td>应收总额</td>
                    <td>实收总额</td>
                    <td>应收本金</td>
                    <td>应收利息</td>
                    <td>逾期罚息</td>
                    <td>协议</td>
                  </tr>
                  </thead>
                  <tbody>
                  <c:forEach items="${CollectionRecordList}" var="collectionRecord" varStatus="sta" step="1">
                  <c:set var="borrow" value="${borrowMap[collectionRecord.tenderId]}"></c:set>
	                  <tr>
	                    <td>
	                    	${collectionRecord.advancetimeStr }
	                    </td>
	                    <td align="left"><c:if test="${collectionRecord.iscustody == 1 }"><i class="icon icon-zs" title="银行存管"></i></c:if>
	                    	<c:if test="${collectionRecord.parentId!=null}"><font  color='red' >[转]</font></c:if><a title='${collectionRecord.name }' href="${path }/toubiao/${borrow.id}.html" target="_blank">${fn:substring(collectionRecord.name,0,10)}<c:if test="${fn:length(collectionRecord.name)>10}">..</c:if></a>
						</td>
						<c:choose>
						   <c:when test="${collectionRecord.ishowfloat==1 && collectionRecord.iscustody != 1}">
						     	<td width="90"><span>${collectionRecord.apr }%</span> <span style="position:absolute; color:red; font-size:12px">+${collectionRecord.yearApr}%</span></td>
						   </c:when>
						   <c:otherwise>
						   	   <td>${collectionRecord.apr }%</td>
						   </c:otherwise>
						</c:choose>
	                    <td><c:if test="${borrow.borrowtype==4}">秒还</c:if> <c:if
						test="${borrow.borrowtype!=4 && borrow.style != 4 }"> ${collectionRecord.timeLimit }个月</c:if>
					<c:if test="${borrow.borrowtype!=4 && borrow.style == 4 }">${collectionRecord.timeLimit }天</c:if></td>
	                    <td>${collectionRecord.order}/${collectionRecord.borrowOrder}</td>
	                    <td>${collectionRecord.repayAccountStr}</td>
	                    <td>
	                    <a href="javascript:void(0)" class=".tip-bottom" title="当前实收总额[${collectionRecord.getYesAccountStr}]  = 实收本金[${collectionRecord.capitalStr }]  + 实收利息[${collectionRecord.yesInterestStr }]  - 利息管理费[${collectionRecord.inverestFeeStr }]">
                    		${collectionRecord.getYesAccountStr}
                    </a>
	                    
					</td>
	                    <td>${collectionRecord.capitalStr }</td>
	                    <td>${collectionRecord.interestStr} </td>
	                    <td><fmt:formatNumber value="${collectionRecord.lateInterest}" pattern="#,##0.00" /></td>
	                    <td>
							<c:if test="${collectionRecord.parentId != null and collectionRecord.firstTenderRealId == null}">
								<a href="javascript:toTransferXiyi('${collectionRecord.id}');">查看</a>
								<a href="javascript:toTransferXiyiDownLoad('${collectionRecord.id}');">下载</a>
							</c:if>
							<c:if test="${collectionRecord.parentId != null and collectionRecord.firstTenderRealId != null}">
								<a href="javascript:toFirstTransferXiyi('${collectionRecord.id}');">查看</a>
								<a href="javascript:toFirstTransferXiyiDownLoad('${collectionRecord.id}');">下载</a>
							</c:if>
							<c:if test="${collectionRecord.parentId == null && borrow.borrowtype != 4 }">
								<a href="javascript:toBorrowXiyi('${collectionRecord.borrowId}');">查看 </a>
								<a href="javascript:toBorrowXiyiDownLoad('${collectionRecord.borrowId}');">下载 </a>
							</c:if> 
							<c:if test="${collectionRecord.parentId == null && borrow.borrowtype == 4 }">
		              			无
		        			</c:if>
						</td>
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
        
        <script type="text/javascript">
        $(document).ready(function(){ 
            var color="#f0f7ff"
            $(".tbtr tr:even,.tbtr01 tr:even,.tbtr02 tr:even,.tbtr03 tr:even,.tbtr04 tr:even,.tbtr05 tr:even,.tbtr06 tr:even,.tbtr07 tr:even,.tbtr08 tr:even,.tbtr09 tr:even,.tbtr10 tr:even,.tbtr11 tr:even,.tbtr12 tr:even,.tbtr13 tr:even,.tbtr14 tr:even,.tbtr15 tr:even,.tbtr16 tr:even,.tbtr17 tr:even").attr("bgcolor",color);//改变奇数行背景色
     	})
		/**
	 * hujianpan
	 * 查看借款协议
	 */
	function toBorrowXiyi(id) {
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
				src : '${basePath }/account/InvestManager/toBorrowXiyi/' + id
						+ '.html'
			},
			close : function(index) {
				layer.close(index);
			}
		});
	}
	
	/**
	 * 
	 * 下载借款协议
	 */
	function toBorrowXiyiDownLoad(id) {
		url = '${basePath }/account/InvestManager/toBorrowXiyiDownLoad/' + id+ '.html';
		layer.confirm("你确定要下载吗?",function (){
			$("#collection").attr("action", url);
			$("#collection").submit();
			layer.msg("下载成功!", 2, 9);
		});
	}
	
	// 查看债权转让协议
	function toTransferXiyi(collectionId){
		//查看借款协议
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
				src : '${basePath }/account/InvestManager/toTransferXiyi/' + collectionId
						+ '.html'
			},
			close : function(index) {
				layer.close(index);
			}
		});
	}
	
	//下载债权转让协议
	function toTransferXiyiDownLoad(collectionId){
		url = '${basePath }/account/InvestManager/toTransferXiyiDownLoad/' + collectionId+ '.html';
		layer.confirm("你确定要下载吗?",function (){
			$("#collection").attr("action", url);
			$("#collection").submit();
			layer.msg("下载成功!", 2, 9);
		});
	}
	
	//查看直通车转让协议
	function toFirstTransferXiyi(collectionId){
		//查看借款协议
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
				src : '${basePath }/account/InvestManager/toFirstTransferXiyi/' + collectionId + '/0.html'
			},
			close : function(index) {
				layer.close(index);
			}
		});
	}
	
	//下载- 普待-已收-转让
	function toFirstTransferXiyiDownLoad(collectionId){
		url = '${basePath }/account/InvestManager/toFirstTransferXiyiDownLoad/' + collectionId + '/0.html';
		layer.confirm("你确定要下载吗?",function (){
			$("#collection").attr("action", url);
			$("#collection").submit();
			layer.msg("下载成功!", 2, 9);
		});
	}
	

	/**
	 * 导出Excel
	 */
	function exportToExcel(a) {
		exportCollectionRecord();
	}

	function exportCollectionRecord(url) {
		url = '${basePath}/account/InvestManager/exportCollectionRecord/1/0.html';
		layer.confirm("你确定要导出吗?",function (){
			$("#collection").attr("action", url);
			$("#collection").submit();
			layer.alert("导出成功！",1);
		});
	}
	
	/**
	 * 查询
	 */
	function forqueryrecord(actionName){
		window.parent.search(actionName,0,0,1);
	}
	
	/**
	 * 按钮查询
	 */
	function queryrecord(actionName,scopeTime,custody ){
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
		
		window.parent.search(actionName, 1, 0, 1);
	}
	
	
	/**翻页*/
	function findPage(pageNo) {
		window.parent.turnCollectionPageParent('receivedCollection_record', pageNo, 1, 0);
	}
	/**
	 * hujianpan
	 * 切换统计数据,即已收和待收页签的切换
	 */
	function toggleListInner(type){
		window.parent.toggleList(type);
	}
        </script>