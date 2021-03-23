<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%> 
<%@ include file="/WEB-INF/page/common/taglib.jsp"%> 
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ include file="/WEB-INF/page/common/public.jsp"%>
<title>我要投标-投标产品介绍_顶玺金融</title>
</head>
<body>
<%@ include file="/WEB-INF/page/common/header.jsp"%>
 <div id="Bmain">
	<div class="title">
		<span class="home"><a href="${path}/">顶玺金融</a></span>
		<span><a href="javascript:void(0);">我要投资</a></span>
		<span>投标产品</span>
	</div>
    <div class="tbmain">
      <div class="tbbox model-box">
           <div class="tbhead">
                 <div class="tbhead_h2">资产抵押标</div>
                      <div class="tbcontent">资产抵押标是指借款者以房、车等实物为抵押物的借款标的。<br/><br/><br/></div>
                      <div class="tbimg"><img src="${basePath}/images/img9.jpg" width="67" height="67" /></div>
                      <div class="tbcontent">
                            <p>预期收益:</p>
                            <p class="tbincome"><em>14-16</em>%</p>
                      </div>
                      <div class="tbcontent">
                      <p>锁定期限：<span class="green">1-12</span>个月</p>
                      <p>投资金额：<span class="green">50</span>元起投</p>
                      </div>
                      <div class="tbcontent">
                       <input class="tbbid" type="button" name="2" value="去投标" style="cursor: pointer;"/>
                      </div>
           </div>
      </div>
     
     
      <div class="tbbox model-box">
            <div class="tbhead tbblue">
                 <div class="tbhead_h2">机构担保标</div>
                      <div class="tbcontent">机构担保标是指顶玺金融的合作机构为相应的借款承担连带保证责任的借款标的。<br/><br/></div>
                      <div class="tbimg"><img src="${basePath}/images/img12.jpg" width="70" height="67" /></div>
                      <div class="tbcontent">
                            <p>预期收益:</p>
                            <p class="tbincome"><em>14-16</em>%</p>
                      </div>
                      <div class="tbcontent">
                      <p>锁定期限：<span class="green">1~12</span>个月</p>
                      <p>投资金额：<span class="green">50</span>元起投</p>
                      </div>
                      <div class="tbcontent">
              				<input class="tbbid" type="button" name="5" value="去投标" style="cursor: pointer;" />
                      </div>
           </div>
      </div>
       
        <div class="tbbox model-box">
           <div class="tbhead tbgreen">
                 <div class="tbhead_h2">信用认证标</div>
                      <div class="tbcontent">信用认证标是顶玺金融对借款用户的个人信用资质进行全面审核通过后，授予其一定额度而允许用户在线发布的借款标的。</div>
                      <div class="tbimg"><img src="${basePath}/images/img10.jpg" width="70" height="67" /></div>
                      <div class="tbcontent">
                            <p>预期收益:</p>
                            <p class="tbincome"><em>14-16</em>%</p>
                      </div>
                      <div class="tbcontent">
                      <p>锁定期限：<span class="green">1-12</span>个月</p>
                      <p>投资金额：<span class="green">50</span>元起投</p>
                      </div>
                      <div class="tbcontent">
                      <input class="tbbid" type="button" name="1" value="去投标" style="cursor: pointer;"/>
                      </div>
           </div>
      </div>
       
       <%-- <div class="tbbox model-box">
            <div class="tbhead tbyellow">
                 <div class="tbhead_h2">净值标</div>
                      <div class="tbcontent">净值标是指顶玺金融的出借者以其在顶玺投资的待收作为担保的借款标的。<br/><br/></div>
                      <div class="tbimg"><img src="${basePath}/images/img11.jpg" width="67" height="67" /></div>
                      <div class="tbcontent">
                            <p>预期收益:</p>
                            <p class="tbincome"><em>6-24</em>%</p>
                      </div>
                      <div class="tbcontent">
                      <p>锁定期限：<span class="green">0~12</span>个月</p>
                      <p>投资金额：<span class="green">50</span>元起投</p>
                      </div>
                      <div class="tbcontent">
                      <input   class="tbbid"     type="button" name="3" value="去投标"     style="cursor: pointer;"/>
                      </div>
           </div>
      </div> --%>                                    
 
</div>
</div>               

<%@ include file="/WEB-INF/page/common/footer.jsp"%>
</body>
  
</html>


<script type="text/javascript">
 
  
	//获取标题文本框内容
	$(document).ready(function(){
		  $(".tbbid").click(function(){
			  var borrowType = $(this).attr("name"); //赋值
			  
			  if (borrowType==3) {
				 window.location.href='${path}/jingzhibiao.html';
				 return ;
			 }
				toTender(borrowType);  
		  });
		});
	
 function toTender(borrowType){
	 location.href="${path}/toubiao.html?borrowType="+borrowType;
 }
	
	
	
	
</script>