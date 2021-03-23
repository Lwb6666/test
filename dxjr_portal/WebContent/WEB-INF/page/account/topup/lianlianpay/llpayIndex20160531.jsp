<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ include file="/WEB-INF/page/common/taglib.jsp"%>

<script type="text/javascript">
	/**
	*  选 中li下面的radio
	*/
	function chooseNextRadio(obj){
		$("#llpaySelectedUl").find("[type='radio']")[0].checked=true;
		//设置选中此li下的radio为选中
		$(obj).find("[type='radio']")[0].checked=true;
		var imgSrc = $(obj).find("img")[0].src;
		$("#showSelectedSinapayImg").attr("src",imgSrc);
		$("#llpaySelectedUl").show();
		var selectedValue = $(obj).find("[type='radio']")[0].value;
		var creditBankArray = new Array("");
		var haveExists = $.inArray(selectedValue, creditBankArray);
		if(haveExists!=-1){
			$("#llpayCreditSpan").show();
		}else{
			$("#llpayCreditSpan").hide();
		}
	}
	//为li绑定事件
	$(".llpayUl").find("li").each(function(index,element){
		$(element).attr("onclick","chooseNextRadio(this)");
	});
</script>
       <p>充值渠道：</p>
       <div class="val_bk">
        <ul class="llpayUl">
			<li>
				<input name="paybank" type="radio" value="01020000" />&nbsp;&nbsp;
				<img src="${basePath }/images/banklogo/icbc.gif" alt="中国工商银行" align="middle" />
			</li>	
			<li>
				<input name="paybank" type="radio"  value="01050000" />&nbsp;&nbsp;
				<img src="${basePath }/images/banklogo/ccb.gif" alt="中国建设银行" align="middle" />
			</li>
<!-- 			<li> -->
<!-- 				<input name="paybank" type="radio" value="01030000"/>&nbsp;&nbsp; -->
<%-- 				<img src="${basePath }/images/banklogo/abc.gif" alt="中国农业银行" align="middle" /> --%>
<!-- 			</li> -->
			<li>
				<input name="paybank" type="radio" value="03080000" />&nbsp;&nbsp;
				<img src="${basePath }/images/banklogo/cmb.gif" alt="招商银行" align="middle" />
			</li>
			<li>
				<input name="paybank" type="radio" value="03010000" />&nbsp;&nbsp;
				<img src="${basePath }/images/banklogo/comm.gif" alt="交通银行" align="middle" />
			</li> 	
			<li>
				<input name="paybank" type="radio" value="01040000" />&nbsp;&nbsp;
				<img src="${basePath }/images/banklogo/boc.gif" alt="中国银行" align="middle" />
			</li>			
			<li>
				<input name="paybank" type="radio" value="03030000" />&nbsp;&nbsp;
				<img src="${basePath }/images/banklogo/ceb.gif" alt="光大银行" align="middle" />
			</li>					
			<li>
				<input name="paybank" type="radio" value="03050000" />&nbsp;&nbsp;
				<img src="${basePath }/images/banklogo/cmbc.gif" alt="中国民生银行" align="middle" />
			</li>
			<li>
				<input name="paybank" type="radio" value="03020000"/>&nbsp;&nbsp;
				<img src="${basePath }/images/banklogo/citic.gif" alt="中信银行" align="middle" />
			</li>	
			<li>
				<input name="paybank" type="radio" value="03060000" />&nbsp;&nbsp;
				<img src="${basePath }/images/banklogo/gdb.gif" alt="广东发展银行" align="middle" />
			</li>			
			<li>
				<input name="paybank" type="radio" value="03100000" />&nbsp;&nbsp;
				<img src="${basePath }/images/banklogo/spdb.gif" alt="上海浦东发展银行" align="middle" />
			</li>						
			<li>
				<input name="paybank" type="radio" value="03070000"/>&nbsp;&nbsp;
				<img src="${basePath }/images/banklogo/szpab.gif" alt="平安银行" align="middle" />
			</li>	
			<li>
				<input name="paybank" type="radio" value="03040000" />&nbsp;&nbsp;
				<img src="${basePath }/images/banklogo/hxb.gif" alt="华夏银行" align="middle" />
			</li>		
			<li>
				<input name="paybank" type="radio" value="04083320"/>&nbsp;&nbsp;
				<img src="${basePath }/images/banklogo/nbcb.gif" alt="宁波银行" align="middle" />
			</li>
			<li>
				<input name="paybank" type="radio" value="03200000"/>&nbsp;&nbsp;
				<img src="${basePath }/images/banklogo/hkbea.gif" alt="东亚银行" align="middle" />
			</li> 
			<li>
				<input name="paybank" type="radio" value="04012900" />&nbsp;&nbsp;
				<img src="${basePath }/images/banklogo/bos.jpg" alt="上海银行" align="middle" />
			</li>  	
			<li>
				<input name="paybank" type="radio" value="01000000" />&nbsp;&nbsp;
				<img src="${basePath }/images/banklogo/psbc.gif" alt="邮政储蓄银行" align="middle" />
			</li>
			<li>
				<input name="paybank" type="radio" value="04243010"/>&nbsp;&nbsp;
				<img src="${basePath }/images/banklogo/njcb.gif" alt="南京银行" align="middle" />
			</li>	
			<li>
				<input name="paybank" type="radio" value="04031000" />&nbsp;&nbsp;
				<img src="${basePath }/images/banklogo/bccb.gif" alt="北京银行" align="middle" >
			</li>
			<li>
				<input name="paybank" type="radio" value="03170000"/>&nbsp;&nbsp;
				<img src="${basePath }/images/banklogo/cbhb.gif" alt="渤海银行" align="middle" />
			</li>	
			<li>
				<input name="paybank" type="radio" value="64296510"/>&nbsp;&nbsp;
				<img src="${basePath}/images/banklogo/bocd.png" alt="成都银行" align="middle" />
			</li>			
        </ul>
        <div class="val-bkinfo" id="llpaySelectedUl" style="display: none">
                                       选择的银行:<img id="showSelectedSinapayImg">&nbsp;
             <input type="radio" name="payChannel" value="debit" checked="checked" style="margin-left:20px;"/>借记卡
			<span id="llpayCreditSpan"><input type="radio" name="payChannel" value="credit"/>信用卡</span>
        </div>
   </div>
