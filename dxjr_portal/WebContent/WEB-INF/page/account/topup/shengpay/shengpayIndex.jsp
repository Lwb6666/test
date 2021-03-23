<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ include file="/WEB-INF/page/common/taglib.jsp"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>顶玺金融互联网专业的P2P网站</title>
</head>
<script type="text/javascript">
	/**
	*  选 中li下面的radio
	*/
	function chooseNextRadio(obj){
		//设置选中此li下的radio为选中
		$(obj).find("[type='radio']")[0].checked=true;
	}
	//为li绑定事件
	$(".shengpayUl").find("li").each(function(index,element){
		$(element).attr("onclick","chooseNextRadio(this)");
	});
</script>
<body>
	<ul class="bank fix shengpayUl">
		<li>
			<input name="paybank" type="radio" value="ICBC" />&nbsp;&nbsp;
			<img src="${basePath }/images/banklogo/icbc.gif" alt="中国工商银行" align="middle" />
		</li>	
		<li>
			<input name="paybank" type="radio"  value="CCB" />&nbsp;&nbsp;
			<img src="${basePath }/images/banklogo/ccb.gif" alt="中国建设银行" align="middle" />
		</li>
		<li>
			<input name="paybank" type="radio" value="ABC"/>&nbsp;&nbsp;
			<img src="${basePath }/images/banklogo/abc.gif" alt="中国农业银行" align="middle" />
		</li>
		<li>
			<input name="paybank" type="radio" value="CMBC" />&nbsp;&nbsp;
			<img src="${basePath }/images/banklogo/cmbc.gif" alt="中国民生银行" align="middle" />
		</li>
		<li>
			<input name="paybank" type="radio" value="SZPAB"/>&nbsp;&nbsp;
			<img src="${basePath }/images/banklogo/szpab.gif" alt="平安银行" align="middle" />
		</li>	
		<li>
			<input name="paybank" type="radio" value="BCCB" />&nbsp;&nbsp;
			<img src="${basePath }/images/banklogo/bccb.gif" alt="北京银行" align="middle" >
		</li>
		<li>
			<input name="paybank" type="radio" value="BOC" />&nbsp;&nbsp;
			<img src="${basePath }/images/banklogo/boc.gif" alt="中国银行" align="middle" />
		</li>	
		<li>
			<input name="paybank" type="radio" value="CITIC"/>&nbsp;&nbsp;
			<img src="${basePath }/images/banklogo/citic.gif" alt="中信银行" align="middle" />
		</li>	
		<li>
			<input name="paybank" type="radio" value="SPDB" />&nbsp;&nbsp;
			<img src="${basePath }/images/banklogo/spdb.gif" alt="上海浦东发展银行" align="middle" />
		</li>
		<li>
			<input name="paybank" type="radio" value="CMB" />&nbsp;&nbsp;
			<img src="${basePath }/images/banklogo/cmb.gif" alt="招商银行" align="middle" />
		</li>	
		<li>
			<input name="paybank" type="radio" value="COMM" />&nbsp;&nbsp;
			<img src="${basePath }/images/banklogo/comm.gif" alt="交通银行" align="middle" />
		</li>  	
	
		<li>
			<input name="paybank" type="radio" value="BOS" />&nbsp;&nbsp;
			<img src="${basePath }/images/banklogo/bos.jpg" alt="上海银行" align="middle" />
		</li>  		
		<li>
			<input name="paybank" type="radio" value="CEB" />&nbsp;&nbsp;
			<img src="${basePath }/images/banklogo/ceb.gif" alt="光大银行" align="middle" />
		</li> 
		<li>
			<input name="paybank" type="radio" value="GDB" />&nbsp;&nbsp;
			<img src="${basePath }/images/banklogo/gdb.gif" alt="广东发展银行" align="middle" />
		</li> 
		<li>
			<input name="paybank" type="radio" value="NBCB" />&nbsp;&nbsp;
			<img src="${basePath }/images/banklogo/nbcb.gif" alt="宁波银行" align="middle" />
		</li>  	
		<li>
			<input name="paybank" type="radio" value="HKBEA"/>&nbsp;&nbsp;
			<img src="${basePath }/images/banklogo/hkbea.gif" alt="东亚银行" align="middle" />
		</li> 
		<li>
			<input name="paybank" type="radio" value="NJCB" />&nbsp;&nbsp;
			<img src="${basePath }/images/banklogo/njcb.gif" alt="南京银行" align="middle" />
		</li>	 
		<li>
			<input name="paybank" type="radio" value="CBHB"/>&nbsp;&nbsp;
			<img src="${basePath }/images/banklogo/cbhb.gif" alt="渤海银行" align="middle" />
		</li>	
		<li>
			<input name="paybank" type="radio" value="BOCD" />&nbsp;&nbsp;
			<img src="${basePath }/images/banklogo/bocd.png" alt="成都银行" align="middle" />
		</li>
	</ul>
</body>
</html>
