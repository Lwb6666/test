<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ include file="/WEB-INF/page/common/taglib.jsp"%>

<script type="text/javascript">
	/**
	*  选 中li下面的radio
	*/
	function chooseChinabankRadio(obj){
		$("#chinabankdebit").removeAttr("value");
		$("#chinabankcredit").removeAttr("value");
		
		$("#chinabankSelectedUl").find("[type='radio']")[0].checked=true;
		//设置选中此li下的radio为选中
		$(obj).find("[type='radio']")[0].checked=true;
		var imgSrc = $(obj).find("img")[0].src;
		$("#showSelectedChinabankImg").attr("src",imgSrc);
		$("#chinabankSelectedUl").show();
		var selectedValue = $(obj).find("[type='radio']")[0].value;
		var selectedAlt = $(obj).find("[type='radio']").attr("credit");
		//设置值
		$("#chinabankdebit").val(selectedValue);
		if("undefined"!=typeof(selectedAlt)){
			$("#chinabankcredit").val(selectedAlt);
			$("#chinabankCreditSpan").show();
		}else{
			$("#chinabankCreditSpan").hide();
		}
	}
	//为li绑定事件
	$(".chinabankUl").find("li").each(function(index,element){
		$(element).attr("onclick","chooseChinabankRadio(this)");
	});
</script>
</head>

<body>
   <p>充值渠道：</p>
   <div class="val_bk">
   <ul class="chinabankUl">
	<li>
		<input name="paybank" type="radio" value="1025"  />&nbsp;&nbsp;
		<img src="${basePath }/images/banklogo/icbc.gif" alt="中国工商银行" align="middle" />
	</li>	
	<li>
		<input name="paybank" type="radio" value="3080"  />&nbsp;&nbsp;
		<img src="${basePath }/images/banklogo/cmb.gif" alt="招商银行" align="middle" />
	</li>
	<li>
		<input name="paybank" type="radio" value="1051"  />&nbsp;&nbsp;
		<img src="${basePath }/images/banklogo/ccb.gif" alt="中国建设银行" align="middle" />
	</li>
	<li>
		<input name="paybank" type="radio" value="103"  />&nbsp;&nbsp;
		<img src="${basePath }/images/banklogo/abc.gif" alt="中国农业银行" align="middle" />
	</li>
	<li>
		<input name="paybank" type="radio" value="104"  />&nbsp;&nbsp;
		<img src="${basePath }/images/banklogo/boc.gif" alt="中国银行" align="middle" />
	</li>
	
	<li  style="display: none"  > 
	<input name="paybank" type="radio" value="3407" />&nbsp;&nbsp; 
		<img src="${basePath }/images/banklogo/comm.gif" alt="交通银行" align="middle" />
	</li>  
	  
	<li>
		<input name="paybank" type="radio" value="311"  />&nbsp;&nbsp;
		<img src="${basePath }/images/banklogo/hxb.gif" alt="华夏银行" align="middle" />
	</li>
	<li> 
		<input name="paybank" type="radio" value="309"  />&nbsp;&nbsp;
		<img src="${basePath }/images/banklogo/cib.gif" alt="兴业银行" align="middle" />
	</li>       
	<li>
		<input name="paybank" type="radio" value="326"  />&nbsp;&nbsp;
		<img src="${basePath }/images/banklogo/bos.jpg" alt="上海银行" align="middle" />
	</li>	
	<li>
		<input name="paybank" type="radio" value="305"  />&nbsp;&nbsp;
		<img src="${basePath }/images/banklogo/cmbc.gif" alt="中国民生银行" align="middle" />
	</li>
	<li>
		<input name="paybank" type="radio" value="3061"  />&nbsp;&nbsp;
		<img src="${basePath }/images/banklogo/gdb.gif" alt="广东发展银行" align="middle" />
	</li>	
	<li>
		<input name="paybank" type="radio" value="307"  />&nbsp;&nbsp;
		<img src="${basePath }/images/banklogo/szpab.gif" alt="平安银行" align="middle" />
	</li>		
	<li>
		<input name="paybank" type="radio" value="314"  />&nbsp;&nbsp;
		<img src="${basePath }/images/banklogo/spdb.gif" alt="上海浦东发展银行" align="middle" />
	</li>
	<li>
		<input name="paybank" type="radio" value="313"  />&nbsp;&nbsp;
		<img src="${basePath }/images/banklogo/citic.gif" alt="中信银行" align="middle" />
	</li>
	<li>
		<input name="paybank" type="radio" value="312"  />&nbsp;&nbsp;
		<img src="${basePath }/images/banklogo/ceb.gif" alt="光大银行" align="middle" />
	</li>     
	<li>
		<input name="paybank" type="radio" value="316"/>&nbsp;&nbsp;
		<img src="${basePath }/images/banklogo/njcb.gif" alt="南京银行" align="middle" />
	</li>	
	<li>
		<input name="paybank" type="radio" value="3230" />&nbsp;&nbsp;
		<img src="${basePath }/images/banklogo/psbc.gif" alt="邮政储蓄银行" align="middle" />
	</li> 
	<li>
		<input name="paybank" type="radio" value="324"  />&nbsp;&nbsp;
		<img src="${basePath }/images/banklogo/hccb.gif" alt="杭州银行" align="middle" />
	</li>	
	<li>
		<input name="paybank" type="radio" value="302" />&nbsp;&nbsp;
		<img src="${basePath }/images/banklogo/nbcb.gif" alt="宁波银行" align="middle" />
	</li>
	<li>
		<input name="paybank" type="radio" value="310" />&nbsp;&nbsp;
		<img src="${basePath }/images/banklogo/bccb.gif" alt="北京银行" align="middle" />
	</li>	

	<li>
		<input name="paybank" type="radio" value="342"/>&nbsp;&nbsp;
		<img src="${basePath}/images/banklogo/cqnsyh.gif" alt="重庆农商银行" align="middle" />
	</li>	
	<li>
		<input name="paybank" type="radio" value="343"/>&nbsp;&nbsp;
		<img src="${basePath}/images/banklogo/srcb.gif" alt="上海农商行" align="middle" />
	</li>	
	<li>
		<input name="paybank" type="radio" value="335"/>&nbsp;&nbsp;
		<img src="${basePath}/images/banklogo/bjrcb.gif" alt="北京农商行" align="middle" />
	</li>	
	<li>
		<input name="paybank" type="radio" value="3341"  />&nbsp;&nbsp;
		<img src="${basePath}/images/banklogo/qdccb.gif" alt="青岛银行" align="middle" />
	</li>	
	<li>
		<input name="paybank" type="radio" value="336"/>&nbsp;&nbsp;
		<img src="${basePath}/images/banklogo/bocd.png" alt="成都银行" align="middle" />
	</li>	
</ul>
        <div class="val-bkinfo" id="chinabankSelectedUl" style="display: none">
			选择的银行：<img id="showSelectedChinabankImg" align="middle" />
			<input type="radio" checked="checked" style="margin-left:20px;" id="chinabankdebit" name="chinabankcode"/>借记卡
			<span id="chinabankCreditSpan"><input type="radio" id="chinabankcredit" name="chinabankcode"/>信用卡</span>
        </div>
 </div>        
