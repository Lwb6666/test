<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ include file="/WEB-INF/page/common/taglib.jsp"%>

<script type="text/javascript">
	/**
	*  选 中li下面的radio
	*/
	function chooseNextRadio(obj){
		$("#sinapaySelectedUl").find("[type='radio']")[0].checked=true;
		//设置选中此li下的radio为选中
		$(obj).find("[type='radio']")[0].checked=true;
		var imgSrc = $(obj).find("img")[0].src;
		$("#showSelectedSinapayImg").attr("src",imgSrc);
		$("#sinapaySelectedUl").show();
		var selectedValue = $(obj).find("[type='radio']")[0].value;
		var selectedAlt = $(obj).find("[type='radio']").attr("credit");
		//设置值
		if("undefined"!=typeof(selectedAlt)){
			$("#sinapayCreditSpan").show();
		}else{
			$("#sinapayCreditSpan").hide();
		}
		 
	}
	//为li绑定事件
	$(".sinapayUl").find("li").each(function(index,element){
		var _id = $(element).attr("id");
		if(_id=='BCCBLI'||_id=='COMMLI'){
			return;
		}
		$(element).attr("onclick","chooseNextRadio(this)");
	});
</script>
       <p>充值渠道：</p>
       <div class="val_bk">
        <ul class="sinapayUl">
			<li>
				<input name="paybank" type="radio" value="ICBC"   credit=""/>&nbsp;&nbsp;
				<img src="${basePath }/images/banklogo/icbc.gif" alt="中国工商银行" align="middle" />
			</li>	
			<li>
				<input name="paybank" type="radio"  value="CCB"  credit="" />&nbsp;&nbsp;
				<img src="${basePath }/images/banklogo/ccb.gif" alt="中国建设银行" align="middle" />
			</li>
<!-- 			<li> -->
<!-- 				<input name="paybank" type="radio" value="ABC"  credit=""  />&nbsp;&nbsp; -->
<%-- 				<img src="${basePath }/images/banklogo/abc.gif" alt="中国农业银行" align="middle" /> --%>
<!-- 			</li>	 -->
			<li>
				<input name="paybank" type="radio" value="CMBC"  credit=""/>&nbsp;&nbsp;
				<img src="${basePath }/images/banklogo/cmbc.gif" alt="中国民生银行" align="middle" />
			</li>
			<li>
				<input name="paybank" type="radio" value="HXB" />&nbsp;&nbsp;
				<img src="${basePath }/images/banklogo/hxb.gif" alt="华夏银行" align="middle" />
			</li>		
			<li>
				<input name="paybank" type="radio" value="SZPAB"/>&nbsp;&nbsp;
				<img src="${basePath }/images/banklogo/szpab.gif" alt="平安银行" align="middle" />
			</li>	
			<span onclick="layer.alert('接新浪支付的通知，交通银行、北京银行网银充值即日起暂时停止提供服务');">
			<li id="BCCBLI">
				<input name="paybank" type="radio" value="BCCB"  disabled="disabled"/>&nbsp;&nbsp;
				<img src="${basePath }/images/banklogo/bccb.gif" alt="北京银行" align="middle" >
			</li>
			</span>
			<li>
				<input name="paybank" type="radio" value="BOC" credit="" />&nbsp;&nbsp;
				<img src="${basePath }/images/banklogo/boc.gif" alt="中国银行" align="middle" />
			</li>	
			<li>
				<input name="paybank" type="radio" value="CITIC"  credit=""/>&nbsp;&nbsp;
				<img src="${basePath }/images/banklogo/citic.gif" alt="中信银行" align="middle" />
			</li>	
			<li>
				<input name="paybank" type="radio" value="SPDB" />&nbsp;&nbsp;
				<img src="${basePath }/images/banklogo/spdb.gif" alt="上海浦东发展银行" align="middle" />
			</li>
			<li>
				<input name="paybank" type="radio" value="PSBC" credit=""/>&nbsp;&nbsp;
				<img src="${basePath }/images/banklogo/psbc.gif" alt="邮政储蓄银行" align="middle" />
			</li>		
			<li>
				<input name="paybank" type="radio" value="CMB"  credit=""/>&nbsp;&nbsp;
				<img src="${basePath }/images/banklogo/cmb.gif" alt="招商银行" align="middle" />
			</li>
			<span onclick="layer.alert('接新浪支付的通知，交通银行、北京银行网银充值暂时停止提供服务');">
			<li id="COMMLI">
				<input name="paybank" type="radio" value="COMM" credit="" disabled="disabled"/>&nbsp;&nbsp;
				<img src="${basePath }/images/banklogo/comm.gif" alt="交通银行" align="middle" />
			</li>  	
		    </span>	
			<li>
				<input name="paybank" type="radio" value="BOS" />&nbsp;&nbsp;
				<img src="${basePath }/images/banklogo/bos.jpg" alt="上海银行" align="middle" />
			</li>  		
			<li>
				<input name="paybank" type="radio" value="CEB"  credit=""/>&nbsp;&nbsp;
				<img src="${basePath }/images/banklogo/ceb.gif" alt="光大银行" align="middle" />
			</li> 
			<li>
				<input name="paybank" type="radio" value="GDB" credit=""/>&nbsp;&nbsp;
				<img src="${basePath }/images/banklogo/gdb.gif" alt="广东发展银行" align="middle" />
			</li> 
			<li>
				<input name="paybank" type="radio" value="CIB" credit=""/>&nbsp;&nbsp;
				<img src="${basePath }/images/banklogo/cib.gif" alt="兴业银行" align="middle" />
			</li>
        </ul>
        <div class="val-bkinfo" id="sinapaySelectedUl" style="display: none">
                                       选择的银行:<img id="showSelectedSinapayImg">&nbsp;
             <input type="radio" name="payChannel" value="debit" checked="checked" style="margin-left:20px;"/>借记卡
			<!-- <span id="sinapayCreditSpan"><input type="radio" name="payChannel" value="credit"/>信用卡</span> -->
        </div>
   </div>
