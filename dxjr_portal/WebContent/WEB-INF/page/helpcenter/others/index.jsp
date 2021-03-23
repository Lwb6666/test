<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/page/common/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ include file="/WEB-INF/page/common/public.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link href="${basePath}/css/help.css" rel="stylesheet" type="text/css" />
<title>帮助中心-其他问题_顶玺金融</title>
</head>
<body>
	<%@ include file="/WEB-INF/page/common/header.jsp"%>
	<div id="Container">
		<div id="Bmain">
			<div class="title">
				<span class="home"><a href="${path}/">顶玺金融</a></span> <span><a
					href="${path }/bangzhu.html">帮助中心</a></span> <span>其他问题</span>
			</div>
			<div id="menu_centert">
				<%@ include file="/WEB-INF/page/helpcenter/leftMenu.jsp"%>
				<div id="helpctr">
   <div class="content" style="display: block;">     
          <h2>其他问题</h2>  
         <div class="help-title" onclick="list(1)"><a href="#" id="help_title">投监会何时成立的？如何参选？<span></span></a></div>
         <div class="help-content" id="list1">
             <p>第一届投资者监督委员会成立于2014年3月22日，投资者参选采用自愿报名的方式，在顶玺金融论坛发布竞选帖子。主要介绍投资者本人基本情况、对理想网贷平台的要求与展望、对投资者监督委员会成员责任和义务的认识、如果当选应该如何更好开展工作等内容。  </p> 
             <p class="up" onclick="list(1)"><a href="javascript:void(0);" id="help_title">&nbsp;</a></p>
         </div>        
         <div class="help-title" onclick="list(2)"><a href="#" id="help_title">投资人入股需要哪些条件？<span></span></a></div>
         <div class="help-content" id="list2">
              <p>投资人入股需要参照加权待收、论坛累计积分、投标直通车、推荐新人用户数的权重指标取值计算的，具体可参考国城论坛公告顶玺金融入股方案修正版<a href="${bbsPath}/posts/66156/1" class="blue" >查看</a> 。</p> 
             <p class="up" onclick="list(2)"><a href="javascript:void(0);" id="help_title">&nbsp;</a></p>
         </div>        
         <div class="help-title" onclick="list(3)"><a href="#" id="help_title">关于风险保证金的托管？<span></span></a></div>
         <div class="help-content" id="list3">
             <p>为了更好的透明和规范运作，顶玺金融将风险准备金委托招商银行股份有限公司上海分行托管，由招商银行单独开设托管账户，进行专户管理; 您可以到<a href="${path}/gonggao/58.html" class="blue" >“网站公告”</a>里了解一下。</p> 
             <p class="up" onclick="list(3)"><a href="javascript:void(0);" id="help_title">&nbsp;</a></p>
         </div>        
         <div class="help-title" onclick="list(4)"><a href="#" id="help_title">产调具体做什么？<span></span></a></div>
         <div class="help-content" id="list4">
             <p>拉产调主要是为了查询房屋交易，抵押，过户前对房屋所有权证上的权利人、坐落地址、建筑面积等基本信息，是否有存在抵押、查封等权利限制的情况进行查询。</p> 
             <p class="up" onclick="list(4)"><a href="javascript:void(0);" id="help_title">&nbsp;</a></p>
         </div>        
      
     
      </div>  
</div>
				

				<script type="text/javascript">
					function list(i) {
						$("#list" + i).animate({
							height : 'toggle'
						});
					}
				</script>
			</div>
		</div>
	</div>
	<div class="clearfix"></div>
	<%@ include file="/WEB-INF/page/common/footer.jsp"%>
</body>
</html>