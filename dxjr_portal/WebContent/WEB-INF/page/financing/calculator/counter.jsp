<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/page/common/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ include file="/WEB-INF/page/common/public.jsp"%>
<title>理财计算器_顶玺金融</title>
</head>

<body style="background:#f9f9f9;">
<!--头部开始-->
<%@ include file="/WEB-INF/page/common/header.jsp" %>
<!--头部结束-->
<!-- 内容开始 -->
<div id="Container">
   <div id="Bmain">
     <div id="rz_main" style="height:auto;">
                <div class="tbtitless">
                              <div class="tb_borrow2" align="center">
                              	  <form name="interestForm1" id="interestForm1" action="${basePath }/borrow/calculator/interestCal.html" method="post">
                                  <div id="counttb">
                                     <div id="counttblist" class="ct" > ${flag==1?'借款计算器':'理财计算器'} </div>
                                     <div id="counttblist">
                                           <div class="ct1">借款金额：</div>
                                           <div class="ct2"><input class="tb_input" type="text" name="money" id="money1" <c:if test="${style==1 }">value="${ interest.money}"</c:if>/>元</div>
                                     </div>
                                      <div id="counttblist">
                                           <div class="ct1"> 年化利率 ：</div>
                                           <div class="ct2"><input class="tb_input" type="text" name="rate" id="rate1" <c:if test="${style==1 }">value="${ interest.rate}"</c:if>/>%</div>
                                     </div>
                                      <div id="counttblist">
                                           <div class="ct1"> 借款期限 ：</div>
                                           <div class="ct2"><input class="tb_input" type="text" name="period" id="period1" <c:if test="${style==1 }">value="${ interest.period}"</c:if> size="10"/><span id="periodspan1">月</span></div>
                                     </div>
                                      <div id="counttblist">
                                           <div class="ct1"> 借款类型 ：</div>
                                           <div class="ct2">&nbsp;
                                           	 <input type="radio" id="category1" name="category" value="0" <c:if test="${(interest.category == '0' && style==1) or interest != null}"> checked="checked" </c:if> onclick="categoryFun1('0')"/>
							                                           按月还款&nbsp;<input type="radio" id="category2" name="category" value="1" <c:if test="${interest.category == '1'  && style==1}"> checked="checked" </c:if>  onclick="categoryFun1('1')"/>
							                                           按天还款
							               </div>
                                     </div>
                                     <div id="counttblist">
                                           <div class="ct1">还款方式：</div>
                                           <div class="ct2">
                                                <select style="width:200px; margin-top:10px;" id="style1" name="style">
                                                 	<option value="1" <c:if test="${ interest.style == '1' && style==1}"> selected="selected" </c:if> >等额本息</option>
													<option value="2" <c:if test="${ interest.style == '2' && style==1}"> selected="selected" </c:if>>按月付息到期还本</option>
													<option value="3" <c:if test="${ interest.style == '3' && style==1}"> selected="selected" </c:if>>到期还本付息</option>
                                                </select>
                                           </div>
                                     </div>
		                             <div id="counttblist" class="ct">
		                             	<c:if test="${style != 1 }">
			                              <span class="cts">年化利率：<span class="red">0</span>%</span>
			                              <span class="cts"> 月利率：<span class="blues">0</span>%</span>
			                              <span class="cts">总收：<span class="blues">0</span>元</span>
		                                </c:if>
		                                <c:if test="${style == 1 }">
			                              <span class="cts">年化利率：<span class="red"><c:if test="${interest.rate==null}">0</c:if><c:if test="${interest.rate != null}">${interest.rate}</c:if></span>%</span>
			                              <c:if test="${interest != null && interest.category==0 }">
			                              <span class="cts"> 月利率：<span class="blues"><fmt:formatNumber value="${rateP*100}" pattern="#,##0.00"/></span>%</span>
			                              </c:if>
			                              <c:if test="${interest != null && interest.category==1 }">
			                              <span class="cts"> 日利率：<span class="blues"><fmt:formatNumber value="${rateP*100}" pattern="#,##0.00"/></span>%</span>
			                              </c:if>
			                              <c:if test="${interest == null}">
			                              <span class="cts"> 日利率：<span class="blues">0</span>%</span>
			                              </c:if>
			                              <span class="cts">总收：<span class="blues"><c:if test="${interest == null}">0</c:if><c:if test="${interest != null}">${interest.money+interest.interest}</c:if></span>元</span>
		                                </c:if>
		                          	 </div>
		                          	 
                          			 <div id="counttblist jisuan1"><input class="head_button jisuan" type="button" value="开始计算" onclick="submitFun1();" style="cursor:pointer;"/></div> 
                         	</div>
                         	</form>
                         
                      </div>
                    </div>                        
                 </div>
                 
                 <c:if test="${interest != null}">
                 <div class="tblist_title">
                  <ul>
                  <li class="jisuantitle">还款明细</li> 
                  </ul>
                </div>
      			<div id="rz_main" style="height:auto; border-top:none; margin-top:0; ">
           		   <div class="rz_borrow1">
           		   	  <c:if test="${interest.style == '1' && interest.category == '0'}">
      				  <div class="rz_type" style="border:none; margin-top:20px;">
                     	 <span>每期将还本息：</span>￥${ interest.moneyInterestP}&nbsp;&nbsp;&nbsp;&nbsp;<span>每期利率：</span><fmt:formatNumber value="${interest.rateP}" pattern="#,##0.####"/>%&nbsp;&nbsp;&nbsp;&nbsp;<span>还款本息总额：</span>${interest.money + interest.interest}
                      </div>
                      </c:if>
                      
                      <c:if test="${interest.style == '2' && interest.category == '0'}">
      				  <div class="rz_type" style="border:none; margin-top:20px;">
                     	 <span>到期应还利息：</span>￥${ interest.interest}&nbsp;&nbsp;&nbsp;&nbsp;<span>还款本息总额：</span>${ interest.money+interest.interest}&nbsp;
                      </div>
                      </c:if>
                     
                     <c:if test="${(interest.style == '1' or interest.style == '2') && interest.category == '0'}">	
            	     <div class="rz_type1" style="border:none">                          
                     <div class="tbjl">
                             <table border="0">
                             <tr>
                             <td>期数 </td>
                             <td>每期还款本息 </td>
                             <td>每期还款本金 </td>
                             <td>每期利息 </td>
                             <td>余额</td>
                             </tr>  
                             <c:forEach items="${datalist }" var="data" varStatus="s">
                             <tr style="${(s.index+1)%2==1?'background:#ecfafd':'background:#fff'}">
	                             <td>${s.index+1 }</td>    
	                             <td>￥${data.costInterest } </td>
	                             <td>￥${data.cost } </td>
	                             <td>￥${data.interest }</td>
	                             <td>￥${data.balance }</td>
                             </tr>
                             </c:forEach>
                          </table>                                                                               
                          </div>
                 	 </div>
                  </c:if> 
                  
                  <c:if test="${interest.style == '3' && interest.category == '0'}">
      				  <div class="rz_type" style="border:none; margin-top:20px;">
                     	 <span>到期应还利息：</span>￥${ interest.interest}&nbsp;&nbsp;&nbsp;&nbsp;<span>还款本息总额：</span>${ interest.money+interest.interest}
                      </div>
                  </c:if>
                  
                  <c:if test="${interest.category == '1'}">
      				  <div class="rz_type" style="border:none; margin-top:20px;">
                     	 <span>到期应还利息：</span>￥${ interest.interest}&nbsp;&nbsp;&nbsp;&nbsp;<span>还款本息总额：</span>${ interest.money+interest.interest}
                      </div>
                  </c:if>
           </div>   
     	</div>
     	</c:if>
     </div>
 </div>
 <!-- 内容结束 -->
<%@ include file="/WEB-INF/page/common/footer.jsp" %>
</body>
<script type="text/javascript">
$(function() {
	var category = '${interest.category}';
	var style ='${style}';
	if(category == '1' && style =='1'){
		$("#style1").attr("disabled","disabled");
		$("#periodspan1").html('天');
	}
	if(category == '1' && style =='2'){
		$("#style2").attr("disabled","disabled");
		$("#periodspan2").html('天');
	}
});

function categoryFun1(categoryValue){
	if(categoryValue == '0'){
		$("#periodspan1").html('月');
		$("#style1").removeAttr("disabled");
	}else{
		$("#periodspan1").html('天');
		$("#style1").get(0).selectedIndex=2;
		$("#style1").attr("disabled","true");
	}
}

function categoryFun2(categoryValue){
	if(categoryValue == '0'){
		$("#periodspan2").html('月');
		$("#style2").removeAttr("disabled");
	}else{
		$("#periodspan2").html('天');
		$("#style2").get(0).selectedIndex=2;
		$("#style2").attr("disabled","true");
	}
}

function submitFun1(){
	var msg = "";
	//金额的正则表达式
	var reg1= /^(([1-9]\d{0,9})|0)(\.\d{1,2})?$/;
	var pattern= /^[0-9]+$/;
	var money1 = $("#money1").val();
	if(money1.length==0){
		msg = msg + "-借款金额不能为空! \n";
	}else{
		if(!reg1.test(money1)){
			msg = msg + "-借款金额不是正确的金额！\n";
		}
		if(Number(money1) <= 0 ){
			msg = msg + "-借款金额必须大于0！\n";
		}
	}
	
	var rate1 = $("#rate1").val();
	if(rate1.length==0){
		msg = msg + "-年化利率不能为空！\n";
	}else{
		if(!reg1.test(rate1)){
			msg = msg + "-年化利率不是正确的浮点数！\n";
		}
		if(Number(rate1) <= 0 ){
			msg = msg + "-年化利率必须大于0！\n";
		}
	}
	
	var period1 = $("#period1").val();
	if(period1.length==0){
		msg = msg + "-借款期限不能为空！\n";
	}else{
		if(!pattern.test(period1)){
			msg = msg + "-借款期限不是正整数！\n";
		}
		if(Number(period1) <= 0 ){
			msg = msg + "-借款期限必须大于0！\n";
		}
	}
	if(msg !=""){
		layer.alert(msg);
	}else{
		$("#interestForm1").submit();
	}
}

function submitFun2(){
	var msg = "";
	//金额的正则表达式
	var reg1= /^(([1-9]\d{0,9})|0)(\.\d{1,2})?$/;
	var pattern= /^[0-9]+$/;
	var money2 = $("#money2").val();
	if(money2.length==0){
		msg = msg + "-借款金额不能为空! \n";
	}else{
		if(!reg1.test(money2)){
			msg = msg + "-借款金额不是正确的金额！\n";
		}
		if(Number(money2) <= 0 ){
			msg = msg + "-借款金额必须大于0！\n";
		}
	}
	
	var rate2 = $("#rate2").val();
	if(rate2.length==0){
		msg = msg + "-年化利率不能为空！\n";
	}else{
		if(!reg1.test(rate2)){
			msg = msg + "-年化利率不是正确的浮点数！\n";
		}
		if(Number(rate2) <= 0 ){
			msg = msg + "-年化利率必须大于0！\n";
		}
	}
	
	var period2 = $("#period2").val();
	if(period2.length==0){
		msg = msg + "-借款期限不能为空！\n";
	}else{
		if(!pattern.test(period2)){
			msg = msg + "-借款期限不是正整数！\n";
		}
		if(Number(period2) <= 0 ){
			msg = msg + "-借款期限必须大于0！\n";
		}
	}
	if(msg !=""){
		layer.alert(msg);
	}else{
		$("#interestForm2").submit();
	}
}
</script>
</html>
