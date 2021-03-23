<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/page/common/taglib.jsp"%>


<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ include file="/WEB-INF/page/common/public.jsp"%>
<title>我的奖励实物奖励</title>
</head>

<div class="searchbox fl whitebg">
	<div class="s_lott_change"
		style="height:60px;margin:0px 0px 0px 0px;border-left: 1px solid #dbdbdb; border-right: 1px solid #dbdbdb;">


		<div class="xj_jh" style="float:right;line-height:60px;">
			<span
				style="width:100px;height:30px;font-size:14px;border:0px solid red;">
				剩余 &nbsp; <span style="font-size:20px;color:red;">${chanceTotalNum}<c:if
							test="${ling==0 }">  0  </c:if></span> &nbsp;次抽奖机会
			 &nbsp;
			</span> &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; <span><a
				href="${path }/lottery_chance/info.html" >获得更多抽奖机会</a> &nbsp;</span>

		</div>
	</div>
</div>

<!--我的账户右侧  資金管理  提現明細-->
<div class="myid whitebg">

	<!-- sw_lott list s -->
	<table>
		<!-- title s -->
		<tr>
			<!-- <td width="36"><strong>序号</strong></td> -->
			<td><strong>名称</strong></td>
			<td><strong>获奖时间</strong></td>
			<td><strong>过期时间</strong></td>
			<td><strong>状态</strong></td>


			<td><strong>操作</strong></td>
			<td><strong>详情</strong></td>
		</tr>
		<!-- title e -->

		<!-- content s -->
		<c:forEach items="${lottLst}" var="lott_sw" varStatus="sta">
			<tr <c:if test="${sta.index%2==0}">class="trcolor"</c:if>>
				<td>${lott_sw.lotteryGoodsName }</td>
				<td><fmt:formatDate value="${lott_sw.addTime  }"
						pattern="yyyy-MM-dd HH:mm:ss" /></td>
				<td><fmt:formatDate value="${lott_sw.overdueTime  }"
						pattern="yyyy-MM-dd HH:mm:ss" /></td>
				<td>${lott_sw.status_z }</td>
				<td>
					<c:if test="${lott_sw.status==0 }">
						<a href="javascript:sw_lq(${lott_sw.id })" title="领取"
							class="c_oper_a1" style="color:blue;">${lott_sw.status_c }</a>
					</c:if> 
					
					<c:if test="${lott_sw.status==3 }">
						<a href="javascript:sw_lq_qrsh(${lott_sw.id })" title="确认收货"
							class="c_oper_a1" style="color:blue;">${lott_sw.status_c }</a>
					</c:if> 
					
						<c:if test="${lott_sw.status>0 && lott_sw.status!=3 }">
				           		 ${lott_sw.status_c }
				           </c:if>
			   </td>
			   
			   <td>  
				   <c:if test="${lott_sw.status!=0 }">
				 		<a href="javascript:sw_lingqu_detail('${lott_sw.id}');"  title="查看" style="color:blue;">查看
						</a>
				   </c:if>
				   <c:if test="${lott_sw.status==0 }">待领取</c:if>
			   		
			   </td>
		</c:forEach>
		<!-- content e -->

	</table>
	<!-- sw_lott list e -->

	<!-- fenye s -->
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
	<!-- fenye e -->


</div>


<script type="text/javascript">
	var pageii;


	$(function() {
		layer.close(pageii);  

	});

	//sw ling qu 
	function sw_lq(lott_id) {
		// 弹出 弹出框 
		 pageii = $.layer({
			type : 2,
			fix : false,
			shade : [ 0.6, '#E3E3E3', true ],
			shadeClose : true,
			border : [ 10, 0.7, '#272822', true ],
			title : [ '', false ],
			offset : [ '50px', '' ],
			area : [ '500px', '350px' ],
			iframe : {
				src : '${basePath}/lottery_use_record/sw_lingqu.html?lott_id='
						+ lott_id
			},
			close : function(index) {
				//右上角关闭
				findPage(1);
				layer.close(index);
			}
		});

	}
	
	//确认收货 
	function sw_lq_qrsh(lott_id)
	{
		
		// 弹出 弹出框 
		 pageii = $.layer({
			type : 2,
			fix : false,
			shade : [ 0.6, '#E3E3E3', true ],
			shadeClose : true,
			border : [ 10, 0.7, '#272822', true ],
			title : [ '', false ],
			offset : [ '50px', '' ],
			area : [ '500px', '350px' ],
			iframe : {
				src : '${basePath}/lottery_use_record/swLingquQrsh.html?lott_id='
						+ lott_id
			},
			close : function(index) {
				//右上角关闭
				findPage(1);
				layer.close(index);
			}
		});
	}
	
//实物领取 详情
function sw_lingqu_detail(id){

				$.ajax({
						url : '${basePath}/lottery_use_record/sw_lingqu_detail_pd.html?id='+id,
						data : {
							type : 1
						},
						type : 'post',
						dataType : 'json',
						success : function(data) {
							//a1 s---------------------------
							//alert('hehe-data.code = '+data.code);
							// 0 - wu - return 
							if(data.code==0)
							{
								layer.msg(data.message, 2, 5);
								return ;
							}
							//1 - you - go on
							if(data.code==1)
							{
								
									$.layer({
										type : 2,
										fix : false,
										shade : [0.6, '#E3E3E3' , true],
										shadeClose : true,
										border : [10 , 0.7 , '#272822', true],
										title : ['',false],
										offset : ['50px',''],
										area : ['500px','400px'],
										iframe : {src : '${basePath }/lottery_use_record/sw_lingqu_detail/'+id+'.html'},
										close : function(index){
											layer.close(index);
										}
									});
							}
							
							//a1 e---------------------------

						},
						error : function(data) {
							layer.msg("网络连接异常，请刷新页面或稍后重试！", 2, 5);
						}
					});

}

	/**
	 * ajax 翻页功能
	 */
	function findPage(pageNo) {
		window.parent.lott_sw_pageParent(pageNo);
	}
</script>