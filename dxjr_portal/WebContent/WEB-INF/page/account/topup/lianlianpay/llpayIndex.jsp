<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ include file="/WEB-INF/page/common/taglib.jsp"%>

<script type="text/javascript">
$('.choosebox1 li a').click(function(){
	var thisToggle = $(this).is('.size_radioToggle1') ? $(this) : $(this).prev();
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
				  <a href="javascript:void(0);" class="size_radioToggle1" value="01020000" ><span class="value"><img
		                                src="${basePath }/images/myaccount/banklogo/icbc.gif" alt="中国工商银行"/></span></a>
			</li>	
			<li>
				  <a href="javascript:void(0);" class="size_radioToggle1" value="01050000" ><span class="value"><img
		                                src="${basePath }/images/myaccount/banklogo/ccb.gif" alt="中国建设银行"/></span></a>
			</li>
			 <li>
				  <a href="javascript:void(0);" class="size_radioToggle1" value="03080000"><span class="value"><img
		                                src="${basePath }/images/myaccount/banklogo/cmb.gif" alt="招商银行"/></span></a>
			</li>
			<li> 
			  <a href="javascript:void(0);" class="size_radioToggle1" value="03010000"  ><span class="value"><img
		                                src="${basePath }/images/myaccount/banklogo/comm.gif"  alt="交通银行"/></span></a>
			</li>  
			  <li class="last">
				  <a href="javascript:void(0);" class="size_radioToggle1" value="01040000" ><span class="value"><img
		                                src="${basePath }/images/myaccount/banklogo/boc.gif" alt="中国银行"/></span></a>
			</li>
			<li>
				  <a href="javascript:void(0);" class="size_radioToggle1" value="03030000" ><span class="value"><img
		                               src="${basePath }/images/myaccount/banklogo/ceb.gif" alt="中国光大银行"/></span></a>
			</li>   
			<%-- <li>
				  <a href="javascript:void(0);" class="size_radioToggle1"  value="03050000"><span class="value"><img
		                                src="${basePath }/images/myaccount/banklogo/cmbc.gif" alt="中国民生银行"/></span></a>
			</li> --%>
			<%-- <li>
				  <a href="javascript:void(0);" class="size_radioToggle1"  value="03090000"><span class="value"><img
		                                src="${basePath }/images/myaccount/banklogo/cib.gif" alt="兴业银行"/></span></a>
			</li> --%>
			<li>
				  <a href="javascript:void(0);" class="size_radioToggle1" value="03020000" ><span class="value"><img
		                                src="${basePath }/images/myaccount/banklogo/citic.gif" alt="中信银行"/></span></a>
			</li>
			<li>
				  <a href="javascript:void(0);" class="size_radioToggle1" value="03060000"><span class="value"><img
		                               src="${basePath }/images/myaccount/banklogo/gdb.gif" alt="广发银行"/></span></a>
			</li>	
			<li>
				  <a href="javascript:void(0);" class="size_radioToggle1" value="03100000" ><span class="value"><img
		                                src="${basePath }/images/myaccount/banklogo/spdb.gif" alt="上海浦东发展银行"/></span></a>
			</li>
			<li class="last">
				  <a href="javascript:void(0);" class="size_radioToggle1" value="03070000" ><span class="value"><img
		                                src="${basePath }/images/myaccount/banklogo/szpab.gif" alt="平安银行"/></span></a>
			</li>	
			<li>
				  <a href="javascript:void(0);" class="size_radioToggle1"  value="03040000" ><span class="value"><img
		                                src="${basePath }/images/myaccount/banklogo/hxb.gif" alt="华夏银行"/></span></a>
			</li>
			<li>
				  <a href="javascript:void(0);" class="size_radioToggle1" value="04083320" ><span class="value"><img
		                                src="${basePath }/images/myaccount/banklogo/nbcb.gif" alt="宁波银行"/></span></a>
			</li>
			<li>
				  <a href="javascript:void(0);" class="size_radioToggle1" value="03200000"><span class="value"><img
		                                src="${basePath }/images/banklogo/hkbea.gif" alt="东亚银行"/></span></a>
			</li>
			<li>
				  <a href="javascript:void(0);" class="size_radioToggle1" value="04012900"><span class="value"><img
		                                src="${basePath }/images/myaccount/banklogo/bos.gif" alt="上海银行"/></span></a>
			</li>
			<li class="last">
				  <a href="javascript:void(0);" class="size_radioToggle1" value="01000000"  ><span class="value"><img
		                                src="${basePath }/images/myaccount/banklogo/psbc.gif" alt="中国邮政储蓄银行"/></span></a>
			</li> 
			<li>
				  <a href="javascript:void(0);" class="size_radioToggle1" value="04243010"><span class="value"><img
		                               src="${basePath }/images/myaccount/banklogo/njcb.gif" alt="南京银行"/></span></a>
			</li>	
			<li>
				  <a href="javascript:void(0);" class="size_radioToggle1" value="04031000"><span class="value"><img
		                                src="${basePath }/images/myaccount/banklogo/bccb.gif" alt="北京银行"/></span></a>
			</li>	
			<li>
				  <a href="javascript:void(0);" class="size_radioToggle1" value="03170000"><span class="value"><img
		                                src="${basePath }/images/banklogo/cbhb.gif" alt="渤海银行"/></span></a>
			</li>
			<li class="last">
				  <a href="javascript:void(0);" class="size_radioToggle1" value="64296510"><span class="value"><img
		                                src="${basePath}/images/myaccount/banklogo/bocd.gif"  alt="成都银行"/></span></a>
			</li>	
        </ul>
          <input type="hidden" name="payChannel" value="debit" />
       </div>
