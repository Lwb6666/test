<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/page/common/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ include file="/WEB-INF/page/common/public.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link href="${basePath}/css/help.css" rel="stylesheet" type="text/css" />
<title>帮助中心-关于投标_顶玺金融</title>
</head>
<body>
	<%@ include file="/WEB-INF/page/common/header.jsp"%>
	<div id="Container">
		<div id="Bmain">
			<div class="title">
				<span class="home"><a href="${path}/">顶玺金融</a></span> <span><a
					href="${path }/bangzhu.html">帮助中心</a></span> <span>关于投标</span>
			</div>
			<div id="menu_centert">
				<%@ include file="/WEB-INF/page/helpcenter/leftMenu.jsp"%>
				<div id="helpctr">
   <div class="content" style="display: block;">     
          <h3>关于投标</h3>  
         <div class="help-title" onclick="list(1)"><a href="javascript:void(0);" id="help_title">抵押标<span></span></a></div>
         <div class="help-content" id="list1">
             <p>借款人以一定的抵押物（房产或汽车等）作为担保物在顶玺金融发布借款标，抵押物经过我们专业评估后在相关部门办理抵押登记手续。借款者必须在约定期限内如数归还借款，否则顶玺金融有权处理抵押物，用以偿还和投资者约定的借款本金、利息。</p> 
             <p class="up" onclick="list(1)"><a href="javascript:void(0);" id="help_title">&nbsp;</a></p>
         </div>        
         <div class="help-title" onclick="list(2)"><a href="#" id="help_title">担保标<span></span></a></div>
         <div class="help-content" id="list2">
             <p>指顶玺金融根据借款人提供的资料进行审核，担保人以其相关资产作为担保，确保风险控制在合理的范围内。如借款人到期还款出现逾期，由担保人垫付本息还款，债权转让为担保人所有。顶玺金融将严格审核借款者发布的担保标，在额度控制和担保人资格审核上都严格要求。</p> 
             <p class="up" onclick="list(2)"><a href="javascript:void(0);" id="help_title">&nbsp;</a></p>
         </div>        
         <div class="help-title" onclick="list(3)"><a href="#" id="help_title">信用标<span></span></a></div>
         <div class="help-content" id="list3">
             <p>免抵押、免担保、纯信用的贷款标。指融资者向顶玺金融提出借款申请，我们根据借款人提供的资料进行严格的审核，对符合信用条件的借款方标注为信用标发布到顶玺金融的网贷客户端。</p> 
             <p class="up" onclick="list(3)"><a href="javascript:void(0);" id="help_title">&nbsp;</a></p>
         </div>        
         <div class="help-title" onclick="list(4)"><a href="#" id="help_title">净值标<span></span></a></div>
         <div class="help-content" id="list4">
             <p>投资人以个人的净投资作为担保，在一定额度内向顶玺金融平台发布借款标，净值标无需人工审核。净值标的净值额度为：净值额度=（待收本金+可用资金+投标冻结+债转冻结）*0.6-待还本息。（注：当账户没有待还净值标时，净值额度=资产总额）</p> 
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