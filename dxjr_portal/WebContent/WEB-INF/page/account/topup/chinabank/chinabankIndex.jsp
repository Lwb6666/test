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
				  <a href="javascript:void(0);" class="size_radioToggle1" value="1025"  ><span class="value"><img
		                                src="${basePath }/images/myaccount/banklogo/icbc.gif" alt="中国工商银行"/></span></a>
			</li>	
			<li>
				  <a href="javascript:void(0);" class="size_radioToggle1" value="3080"><span class="value"><img
		                                src="${basePath }/images/myaccount/banklogo/cmb.gif" alt="招商银行"/></span></a>
			</li>
			<li>
				  <a href="javascript:void(0);" class="size_radioToggle1" value="1051" ><span class="value"><img
		                                src="${basePath }/images/myaccount/banklogo/ccb.gif" alt="中国建设银行"/></span></a>
			</li>
			<li>
				  <a href="javascript:void(0);" class="size_radioToggle1" value="103" ><span class="value"><img
		                                src="${basePath }/images/myaccount/banklogo/abc.gif" alt="中国农业银行"/></span></a>
			</li>
			<%-- <li class="last">
				  <a href="javascript:void(0);" class="size_radioToggle1" value="104" ><span class="value"><img
		                                src="${basePath }/images/myaccount/banklogo/boc.gif" alt="中国银行"/></span></a>
			</li> --%>
			<li  style="display: none"  > 
			  <a href="javascript:void(0);" class="size_radioToggle1" value="3407" ><span class="value"><img
		                                src="${basePath }/images/myaccount/banklogo/comm.gif"  alt="交通银行"/></span></a>
			</li>  
			  
			<li class="last">
				  <a href="javascript:void(0);" class="size_radioToggle1" value="311"  ><span class="value"><img
		                                src="${basePath }/images/myaccount/banklogo/hxb.gif" alt="华夏银行"/></span></a>
			</li>
			<li> 
				  <a href="javascript:void(0);" class="size_radioToggle1" value="309" ><span class="value"><img
		                                src="${basePath }/images/myaccount/banklogo/cib.gif" alt="兴业银行"/></span></a>
			</li>       
			<li>
				  <a href="javascript:void(0);" class="size_radioToggle1" value="326" ><span class="value"><img
		                                src="${basePath }/images/myaccount/banklogo/bos.gif" alt="上海银行"/></span></a>
			</li>
			<!-- 民生银行暂时关闭
			<li>
				  <a href="javascript:void(0);" class="size_radioToggle1" value="305" ><span class="value"><img
		                                src="${basePath }/images/myaccount/banklogo/cmbc.gif" alt="中国民生银行"/></span></a>
			</li> -->	
			<li>
				  <a href="javascript:void(0);" class="size_radioToggle1" value="3061" ><span class="value"><img
		                               src="${basePath }/images/myaccount/banklogo/gdb.gif" alt="广发银行"/></span></a>
			</li>	
			<li>
				  <a href="javascript:void(0);" class="size_radioToggle1" value="307" ><span class="value"><img
		                                src="${basePath }/images/myaccount/banklogo/szpab.gif" alt="平安银行"/></span></a>
			</li>		
			<li class="last">
				  <a href="javascript:void(0);" class="size_radioToggle1" value="314" ><span class="value"><img
		                                src="${basePath }/images/myaccount/banklogo/spdb.gif" alt="上海浦东发展银行"/></span></a>
			</li>
			<%-- <li>
				  <a href="javascript:void(0);" class="size_radioToggle1" value="313" ><span class="value"><img
		                                src="${basePath }/images/myaccount/banklogo/citic.gif" alt="中信银行"/></span></a>
			</li> --%>
			<li>
				  <a href="javascript:void(0);" class="size_radioToggle1" value="312"><span class="value"><img
		                               src="${basePath }/images/myaccount/banklogo/ceb.gif" alt="中国光大银行"/></span></a>
			</li>     
			<li>
				  <a href="javascript:void(0);" class="size_radioToggle1" value="316"><span class="value"><img
		                               src="${basePath }/images/myaccount/banklogo/njcb.gif" alt="南京银行"/></span></a>
			</li>	
			<li>
				  <a href="javascript:void(0);" class="size_radioToggle1" value="3230" ><span class="value"><img
		                                src="${basePath }/images/myaccount/banklogo/psbc.gif" alt="中国邮政储蓄银行"/></span></a>
			</li> 
			<li>
				  <a href="javascript:void(0);" class="size_radioToggle1" value="324" ><span class="value"><img
		                                src="${basePath }/images/myaccount/banklogo/hccb.gif" alt="杭州银行"/></span></a>
			</li>	
			<li class="last">
				  <a href="javascript:void(0);" class="size_radioToggle1" value="302" ><span class="value"><img
		                                src="${basePath }/images/myaccount/banklogo/nbcb.gif" alt="宁波银行"/></span></a>
			</li>
			<li>
				  <a href="javascript:void(0);" class="size_radioToggle1" value="310"><span class="value"><img
		                                src="${basePath }/images/myaccount/banklogo/bccb.gif" alt="北京银行"/></span></a>
			</li>	
		
			<li>
				  <a href="javascript:void(0);" class="size_radioToggle1" value="342"><span class="value"><img
		                                src="${basePath}/images/myaccount/banklogo/cqnsyh.gif"  alt="重庆农村商业银行"/></span></a>
			</li>	
			<li>
				  <a href="javascript:void(0);" class="size_radioToggle1" value="343"><span class="value"><img
		                               src="${basePath}/images/myaccount/banklogo/srcb.gif" alt="上海农村商业银行"/></span></a>
			</li>	
			<li>
				  <a href="javascript:void(0);" class="size_radioToggle1" value="335"><span class="value"><img
		                               src="${basePath}/images/myaccount/banklogo/bjrcb.gif" alt="北京农村商业银行"/></span></a>
			</li>	
			<li class="last">
				  <a href="javascript:void(0);" class="size_radioToggle1" value="3341"><span class="value"><img
		                               src="${basePath}/images/myaccount/banklogo/qdccb.gif" alt="青岛银行"/></span></a>
			</li>	
			<li class="last">
				  <a href="javascript:void(0);" class="size_radioToggle1" value="336"><span class="value"><img
		                                src="${basePath}/images/myaccount/banklogo/bocd.gif"  alt="成都银行"/></span></a>
			</li>	
        </ul>
        <input type="hidden"  id="chinabankdebit" name="chinabankcode"/>
       </div>
