<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ include file="/WEB-INF/page/common/taglib.jsp"%>

      <!--我的账户右侧开始 -->                        
      <div class="tixiantit">
            <span>选择提现银行卡:</span>
            <span class="right"><a href="${path}/bankinfo/toBankCard.html" style="color:#00a7e5">银行卡管理</a></span>
      </div>

          <div class="bankcard" >
          <ul id="bankInfosUl">
          	<c:forEach items="${bankInfos }" var="bankInfo" varStatus="sta">
          		 <li ${sta.index==0?"class='bankInfoLi current'":"class='bankInfoLi'" } bankInfoId="${bankInfo.id}" onclick="selectBankInfo(this)"> 
                      <div class="bankblock"><strong>${bankInfo.bankName }</strong></div>
                      <div class="bankblock bankblockh">卡号：${bankInfo.securityCardNum}</div>
                      <div class="bankblock bankblockh">开户行行号：${bankInfo.cnapsCode}</div>
					  <div class="bankblock">
					  <%-- <a href="javascript:void(0);" style="cursor:pointer" onclick="deleteCard(this,${bankInfo.id})">
  						<font color="#005a99"><strong>删除</strong></font></a> --%>
					  </div>
              	</li>
          	</c:forEach>
            
            <c:if test="${fn:length(bankInfos)==0}">      
               <li id="nbcard">
                      <img src="${basePath }/images/wht_03.png" height="70" onclick="toAddBankCard();" style="cursor: pointer;" />
                      <p><span><a href="javascript:toAddBankCard();">新增银行卡</a></span></p>
              </li>
            </c:if>   
            </ul>
            
          </div>
         <hr class="hr clearfix" />
        
<script type="text/javascript">
$(function(){
	//银行卡锁定时，隐藏银行卡添加模块；
	var cardLock = '${cardLock}';
	if(cardLock>0){
// 		$("#nbcard").css("display","none"); 
		
	}
});

function toAddBankCard(){
	$.post("${basePath}/bankinfo/isPhoneValidated.html", {
	}, function(data) {
		if(data == 'no'){
			layer.msg('您还没有手机认证！',1,5,function(){
				window.location.href="${path }/account/approve/mobile/mobileforinsert.html";
			});
			return;
		}
		if(data == 'yes'){
					$.layer({
						type : 2,
						fix : false,
						shade : [0.6, '#E3E3E3' , true],
						shadeClose : true,
						border : [10 , 0.7 , '#272822', true],
						title : ['',false],
						offset : ['50px',''],
						area : ['1000px','600px'],
						iframe : {src : '${basePath}/bankinfo/addBankCard.html'},
						close : function(index){
							layer.close(index);
							//window.open("${path}/page/account/myInvest.jsp","_self");
						}
					});
		}
	});
}

/**
 * 选中银行
 */
function selectBankInfo(obj){
	$("#bankInfosUl").find(".bankInfoLi").removeClass("current");
	$(obj).addClass("current");
}

/**
 * 删除银行卡  
 */
function deleteCard(obj,id) {
	var _load = layer.load('处理中..');
	layer.confirm("确定要删除此银行卡吗？", function() {
		$(obj).removeAttr("onclick");
		$.ajax({
			url : "${basePath}/bankinfo/removeBankCard/"+id+".html",
			data : {},
			type : 'post',
			dataType : 'json',
			success : function(data) {
				$(obj).attr("onclick","deleteCard(this,"+id+")");
				layer.close(_load);
				if (data.code == '0') {
					layer.msg(data.message, 1, 5);
					return;
				} else {
					layer.msg('删除银行卡成功', 1, 1, function() {
						window.location.href = window.location.href;
					});
				}
			},
			error : function(data) {
				$(obj).attr("onclick","deleteCard(this,"+id+")");
				layer.close(_load);
				layer.msg("网络连接异常，请刷新页面或稍后重试！");
			}
		});
	});
}
</script>
