<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ include file="/WEB-INF/page/common/taglib.jsp"%>

<script type="text/javascript">
$('.choosebox1 li a').click(function(){
	var thisToggle = $(this).is('.size_radioToggle1') ? $(this) : $(this).prev();
	var _id = thisToggle.attr("id");
	if(_id=='BCCBLI'||_id=='COMMLI'){
		return;
	}
	var checkBox = thisToggle.prev();
	checkBox.trigger('click');
	$('.size_radioToggle1').removeClass('current');
	thisToggle.addClass('current');
	RechargeBank=thisToggle.attr("value"); 
});		
</script>
</head>
<body>
         <div class="content choosebox1">
          <ul class="clearfix">
			<li>
				  <a href="javascript:void(0);" class="size_radioToggle1" value="ICBC"   ><span class="value"><img
		                                src="${basePath }/images/myaccount/banklogo/icbc.gif" alt="中国工商银行"/></span></a>
			</li>	
			<li>
				  <a href="javascript:void(0);" class="size_radioToggle1" value="CCB" ><span class="value"><img
		                                src="${basePath }/images/myaccount/banklogo/ccb.gif" alt="中国建设银行"/></span></a>
			</li>
			<li>
				  <a href="javascript:void(0);" class="size_radioToggle1" value="CMBC"  ><span class="value"><img
		                                src="${basePath }/images/myaccount/banklogo/cmbc.gif" alt="中国民生银行"/></span></a>
			</li>
			<li>
				  <a href="javascript:void(0);" class="size_radioToggle1" value="HXB"   ><span class="value"><img
		                                src="${basePath }/images/myaccount/banklogo/hxb.gif" alt="华夏银行"/></span></a>
			</li>
			<li class="last">
				  <a href="javascript:void(0);" class="size_radioToggle1" value="SZPAB" ><span class="value"><img
		                                src="${basePath }/images/myaccount/banklogo/szpab.gif" alt="平安银行"/></span></a>
			</li>	
			<span onclick="layer.alert('接新浪支付的通知，交通银行、北京银行京东充值即日起暂时停止提供服务');">
			<li>
				  <a href="javascript:void(0);" class="size_radioToggle1"  id="BCCBLI" value="BCCB"><span class="value"><img
		                                src="${basePath }/images/myaccount/banklogo/bccb.gif" alt="北京银行"/></span></a>
			</li>	
			</span>
			<li>
				  <a href="javascript:void(0);" class="size_radioToggle1" value="BOC" ><span class="value"><img
		                                src="${basePath }/images/myaccount/banklogo/boc.gif" alt="中国银行"/></span></a>
			</li>
			<li>
				  <a href="javascript:void(0);" class="size_radioToggle1" value="CITIC"  ><span class="value"><img
		                                src="${basePath }/images/myaccount/banklogo/citic.gif" alt="中信银行"/></span></a>
			</li>
			<li>
				  <a href="javascript:void(0);" class="size_radioToggle1" value="SPDB" ><span class="value"><img
		                                src="${basePath }/images/myaccount/banklogo/spdb.gif" alt="上海浦东发展银行"/></span></a>
			</li>
			<li class="last">
				  <a href="javascript:void(0);" class="size_radioToggle1" value="PSBC"  ><span class="value"><img
		                                src="${basePath }/images/myaccount/banklogo/psbc.gif" alt="中国邮政储蓄银行"/></span></a>
			</li> 
			<li>
				  <a href="javascript:void(0);" class="size_radioToggle1" value="CMB" ><span class="value"><img
		                                src="${basePath }/images/myaccount/banklogo/cmb.gif" alt="招商银行"/></span></a>
			</li>
			
			<span onclick="layer.alert('接新浪支付的通知，交通银行、北京银行京东充值暂时停止提供服务');">
			<li> 
			  <a href="javascript:void(0);" class="size_radioToggle1" id="COMMLI" value="COMM" ><span class="value"><img
		                                src="${basePath }/images/myaccount/banklogo/comm.gif"  alt="交通银行"/></span></a>
			</li>  
			</span>
			<li>
				  <a href="javascript:void(0);" class="size_radioToggle1" value="BOS"  ><span class="value"><img
		                                src="${basePath }/images/myaccount/banklogo/bos.gif" alt="上海银行"/></span></a>
			</li>	
			<li>
				  <a href="javascript:void(0);" class="size_radioToggle1" value="CEB"><span class="value"><img
		                               src="${basePath }/images/myaccount/banklogo/ceb.gif" alt="中国光大银行"/></span></a>
			</li>  
			<li class="last">
				  <a href="javascript:void(0);" class="size_radioToggle1" value="GDB" ><span class="value"><img
		                               src="${basePath }/images/myaccount/banklogo/gdb.gif" alt="广发银行"/></span></a>
			</li>	   
			<li class="last"> 
				  <a href="javascript:void(0);" class="size_radioToggle1" value="CIB" ><span class="value"><img
		                                src="${basePath }/images/myaccount/banklogo/cib.gif" alt="兴业银行"/></span></a>
			</li>       
        </ul>
        <input type="hidden" name="payChannel" value="debit" />
       </div>
