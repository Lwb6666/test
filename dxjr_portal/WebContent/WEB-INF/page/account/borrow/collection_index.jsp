<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/page/common/taglib.jsp"%>

<!doctype html>
<html>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="keywords" content="顶玺金融,金融,投资,理财" />
<meta name="description" content="顶玺金融提供安全、有担保的互联网理财投资服务。风险保证金保障！随时可赎回！上顶玺，好收益！">
<meta name="author" content="顶玺金融" />
<meta name="copyright" content="2014 上海顶玺金融信息服务有限公司" />
<head>
<%@ include file="/WEB-INF/page/common/public4.jsp"%>

<title>我的账户_普通待收_顶玺金融</title>
</head>
<body>
<%@ include file="/WEB-INF/page/common/header.jsp"%>
<!--main-->
<div id="myaccount">
<%@ include file="/WEB-INF/page/common/topmenu.jsp"%>
<!--banner star-->
<%@ include file="/WEB-INF/page/common/wraper.jsp"%>

<!--直通车star-->
	<div class="wraper mt20">
     <div class="prduct-menu  background clearfix">
        	<div class="menu-tbl">
            	<ul class="col2">
            		<li class="active" onclick="toggleList('un');">直通车待收</li>
            		<li onclick="loadFirstTenderPage('${basePath}/account/InvestManager/queryFirstTenderReal/1.html?status=0')">直通车列表</li>
            	</ul>
            </div>
            <div class="menucont" style="clear:both"> 
            	<div class="tbl-cont tal-none" id="menu_left"></div>
             	<div class="tbl-cont tal-none" id="menu_right" style="display: none"></div>
           </div>
      </div>
     </div>
</div>


<div class="reveal-modal"  id="firstUserUollection"></div>
        
<!--浮动-->
	<!-- foot start -->
	<%@ include file="/WEB-INF/page/common/footer.jsp"%>
	<!-- foot end -->
	
	<script type="text/javascript">
		var collection_type = '${collection_type}';
		
		$('#tzgl').attr("class",'active');
		$('#ztc').attr("class",'active');
		
		
		var collection_type = '${collection_type}';
		/**
		 * hujianpan
		 */
		$(function(){
			/**跳转至普通待收单据*/
			if('c' == collection_type){
				loadCollectionPage('${basePath}/account/InvestManager/unCollection_record/0/0/1.html');
			}else {/*collection_type == 'z'  跳转至直通车待收单据页面*/
				loadCollectionPage('${basePath}/account/InvestManager/unCollection_record/0/1/1.html');
			}
		});
		
		/*
		 * hujianpan
		 * 加载待收记录 
		 */
		function loadCollectionPage(url){
			$.ajax({
				url : url,
				data : {},
				type : 'post',
				dataType : 'text',
				success : function(data){
					$("#menu_left").html(data);
					
				},
				error : function(data) {
					layer.msg('网络连接异常，请刷新页面或稍后重试！', 1, 5);
			    }
			});
			
		}
		/**查询功能*/
		 function search(actionName,status,collection_type,pageNo){
			 $.ajax({
				 	url : '${basePath}/account/InvestManager/'+actionName+'/'+status+'/'+collection_type+'/'+pageNo+'.html',
					data : {
							beginTime:$('#beginTime').val(),
							endTime:$('#endTime').val(),
							keyword:$('#keyword').val(),
							timeScope:$('#timeScope').val()
						},
					type : 'post',
					dataType : 'text',
					success : function(data){
						$("#menu_left").html(data);
					},
					error : function(data) {
						layer.msg('网络连接异常，请刷新页面或稍后重试！', 1, 5);
				    }
				});	
		}

		/**
		 * hujianpan
		 * 翻页功能
		 */
		function turnCollectionPageParent(actionName,pageNo,status,collection_type){
			
			$.ajax({
				url : '${basePath}/account/InvestManager/'+actionName+'/'+status+'/'+collection_type+'/'+pageNo+'.html',
				data : {
					beginTime:$('#beginTime').val(),
					endTime:$('#endTime').val(),
					keyword:$('#keyword').val(),
					timeScope:$('#timeScope').val()},
				type : 'post',
				dataType : 'text',
				success : function(data){
					$("#menu_left").html(data);
					
				},
				error : function(data) {
					layer.msg('网络连接异常，请刷新页面或稍后重试！', 1, 5);
			    }
			});
		}
		/**
		 * hujianpan
		 * 切换统计数据,即已收和待收页签的切换
		 */
		function toggleList(type){
			$("#menu_right").html('');
			var url_local = '${basePath}/account/InvestManager';
			 if(collection_type=='c' ){		//普通待收页面
				 if(type == 'un'){			//普通待收记录
					  loadCollectionPage(url_local+'/unCollection_record/0/0/1.html');
				 }else {					//普通已收记录
					  loadCollectionPage(url_local+'/receivedCollection_record/1/0/1.html');
				 }
				 
				 
			 }else if(collection_type=='z'){//直通车待收
				 if(type == 'un'){			//直通车待收记录
					  loadCollectionPage(url_local+'/unCollection_record/0/1/1.html');
				 }else{						//直通车已收记录
					  loadCollectionPage(url_local+'/receivedCollection_record/1/1/1.html');
				 }
				
			 }
		}

		

		/**
		 * hujianpan
		 * 切换统计数据,即已收和待收页签的切换
		 */
// 		function toggleList(firstTenderRealId,collectionStatus){
// 			 $("#un").removeClass("men_li");
// 			 $("#rec").removeClass("men_li");
// 				 if(collectionStatus == 'unRec'){			
// 					 $("#un").attr("class",'men_li');
// 					 loadFirstTenderPage('${basePath}/account/InvestManager/collectionFirst_record/1.html?firstTenderRealId='+firstTenderRealId+'&collectionStatus='+collectionStatus);
// 				 }else {					
// 					 $("#rec").attr("class",'men_li');
// 					 loadFirstTenderPage('${basePath}/account/InvestManager/collectionFirst_record/1.html?firstTenderRealId='+firstTenderRealId+'&collectionStatus='+collectionStatus);
// 				 }
// 		}

		//加载投标直通车列表

		function loadFirstTenderPage(url){
			$("#menu_left").html('');
			$.ajax({
				url : url,
				data : {},
				type : 'post',
				dataType : 'text',
				success : function(data){
					$("#menu_right").html(data);
				},
				error : function(data) {
					layer.msg('网络连接异常，请刷新页面或稍后重试！', 1, 5);
			    }
			});
			
		}

		</script>
</body>
</html>
