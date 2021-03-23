<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/page/common/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ include file="/WEB-INF/page/common/public.jsp"%>
<title>我的账户_普通待收_顶玺金融</title>

</head>

<body style="background: #f9f9f9;">
	<!--头部开始-->
	<%@ include file="/WEB-INF/page/common/header.jsp"%>
	<!--头部结束-->
	<!--我的账户左侧开始-->
	<div id="Container">
		<div id="Bmain">
			<div class="title">
				<span class="home"><a href="${path}/">顶玺金融</a></span>
				<span><a href="${path }/myaccount/toIndex.html">我的账户</a></span>
				<span>投资管理</span>
				<span>  <A  href="${path }/account/InvestManager/collectionindex/${collection_type == 'c' ?'c.html':'z.html'}" > ${ collection_type == 'c' ?'普通待收记录':'直通车待收记录'} </A>     </span>
			</div>

			<div id="menu_centert">
				<div id="menu_left">
					<%@ include file="/WEB-INF/page/account/myAccount_leftMenu.jsp"%>
				</div>
				<!--我的账户左侧 结束 -->

				<!--我的账户右侧开始 -->
				<div class="lb_waikuang">
					
					<div id="menu_right"><div class="clear"></div></div>
					
				</div>

			</div>


		</div>
	</div>
	<div class="clearfix"></div>
	<%@ include file="/WEB-INF/page/common/footer.jsp"%>
</body>
<script type="text/javascript">
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
			$("#menu_right").html(data);
			
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
				keyword:$('#keyword').val()
				},
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
			keyword:$('#keyword').val()},
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
/**
 * hujianpan
 * 切换统计数据,即已收和待收页签的切换
 */
function toggleList(type){
	//$("#un").attr("class", "");
	//$("#rec").attr("class", "");
	 $("#un").removeClass("active");
	 $("#rec").removeClass("active");
	var url_local = '${basePath}/account/InvestManager';
	 if(collection_type=='c' ){		//普通待收页面
		 if(type == 'un'){			//普通待收记录
			 // $("#un").addClass("men_li");
			 $("#un").attr("class",'active');
			  loadCollectionPage(url_local+'/unCollection_record/0/0/1.html');
		 }else {					//普通已收记录
			 // $("#rec").addClass("men_li");
			 $("#rec").attr("class",'active');
			  loadCollectionPage(url_local+'/receivedCollection_record/1/0/1.html');
		 }
		 
		 
	 }else if(collection_type=='z'){//直通车待收
		 if(type == 'un'){			//直通车待收记录
			 // $("#un").addClass("men_li");
			 $("#un").attr("class",'active');
			  loadCollectionPage(url_local+'/unCollection_record/0/1/1.html');
		 }else{						//直通车已收记录
			 // $("#rec").addClass("men_li");
			 $("#rec").attr("class",'active');
			  loadCollectionPage(url_local+'/receivedCollection_record/1/1/1.html');
		 }
		
	 }
}



</script>

</html>
