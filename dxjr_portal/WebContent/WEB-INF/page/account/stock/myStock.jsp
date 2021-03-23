<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/page/common/taglib.jsp"%>
<!doctype html>
<html>
<head>
<meta name="keywords" content="顶玺金融,金融,投资,理财" />
<meta name="description" content="顶玺金融提供安全、有担保的互联网理财投资服务。100%本息担保！随时可赎回！上顶玺，好收益！">
<meta name="generator" content="顶玺金融" />
<meta name="author" content="顶玺金融" />
<meta name="copyright" content="2014 上海顶玺金融信息服务有限公司" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<%@ include file="/WEB-INF/page/common/public4.jsp"%>
<title>我的账户-期权管理-我的期权_顶玺金融</title>
</head>

<body>
<%@ include file="/WEB-INF/page/common/header.jsp"%>
<div id="myaccount">
<%@ include file="/WEB-INF/page/common/topmenu.jsp"%>

<div class="product-wrap"><!--定期宝star-->
	<div class="grid-1100">
           <div class="product-deatil" style=" padding-bottom:50px;">
            <div class="tz-dqb2 clearfix">
                <span class="blue ml20 f16">我的期权</span>
             </div>
             <c:if test="${stock!=null }">
                <table class="table tbtr center">
                 <thead>
                  <tr>
                    <td>期权排名</td>
                    <td>期权数量</td>
                    <td>期权获得时间</td>
                    <td>现金行权金额</td>
                    <td>现金行权时间</td>
                    <td>状态</td>
                    <c:if test="${stock.status != 2 }">
                    	<td>操作</td>
                    </c:if>
                  </tr>
                  </thead>
                  <tbody>
                  
                  <tr>
                    <td>${stock.rank }</td>
                    <td>${stock.stockNum }</td>
                    <td>${stock.addTimeStr }</td>
                    <td><fmt:formatNumber value="${stock.stockMoney }" pattern="￥#,##0.00"/></td>
                    <td>${stock.exerciseTimeStr }</td>
                    <td>${stock.statusStr }</td>
                    <c:if test="${stock.status != 2 }">
                    <td>
                   		<a href="javascript:openWin();">现金行权</a>
                    </td>
                    </c:if>
                  </tr>
                  
                  </tbody>
                  
                 </table>
                 </c:if>
                 
                  <ul class="recharge-tip gray9 clearfix">
                      <p class="f16 red">注：期权持有期间，资产总额不能少于${haveStockMoney }元</p>
                   			<li class="mr10 f16">温馨提示</li>
                   			<li>1. 所有行权均不允许部分行权，只能一次性全部行权。<br>
                           		2. 2014年9月27日——2015年9月26日期间任何时候进行现金行权，行权价格为1元/份。<br>
                           		3. 2015年9月27日——2016年9月26日期间任何时候进行现金行权，行权价格为2元/份。<br>
                           		4. 2016年9月27日——2017年9月26日期间任何时候进行现金行权，行权价格为3元/份。<br>
                           <span>5.  更多行权说明请参见</span><a href="http://bbs.dxjr.com/posts/65924/1" class="blue">《顶玺金融投资人入股实施细则》</a>
                       </li>
                 </ul>
       </div>
      </div>
    </div>
</div>


<%@ include file="/WEB-INF/page/common/footer.jsp"%>


<!--弹层start--->
<div class="layer-jion" style="display:none;" id="xjqq">
	<div class="cont-word">
    	<div class="title">
    	  <h4>请输入交易密码</h4>
   	    <a href="javascript:closeWin();" class="icon icon-close fr"></a></div>
        <div class="form-info-layer">

            
       	  <div class="form-col2" style=" padding:30px 0">
                <input type="password" style="width:200px" class="center form-inpyt-sm" id="pwd" name="pwd" placeholder="请输入交易密码">
<!--                 <input type="password" name="payPassword" id="payPassword" style="width:120px" class="colright form-inpyt-sm"  placeholder="输入交易密码"> -->
                
            </div>
            
            <div class="form-col2"><button type="button" class="remove" onclick="javascript:closeWin();">取消</button><button type="button" class="enter" onClick="javascript:exerciseByMoney();">确认</button>
            </div>
      </div>
    </div> 
</div>
<!--弹层end--->

</body>
<script type="text/javascript">
$(function(){
	$("#zhzl").attr("class","active");
});
	//打开弹出层
	function openWin(){
		$("#xjqq").css("display", "block"); 
	}
	
	function exerciseByMoney(){
		var pwd = $('#pwd').val();
		if(pwd=="" ||pwd == null){
			layer.msg("请输入交易密码");
			return;
		}
		layer.confirm("确认立即现金行权吗?", function(){
			$.ajax({
				url : '${basePath}/stock/exerciseStock.html',
				type : 'post',
				data: {payPwd:pwd},
				dataType : 'json',
				success : function(msg){
					
					if(msg.code=="1"){
						$.layer({
							dialog: { type: 1, msg: msg.message },
							closeBtn: false,
							yes: function(index){window.location.reload();}
						});
						
			    	}else{
			    		$.layer({
							dialog: { type: 5, msg: msg.message },
							closeBtn: false
// 							yes: function(index){exerciseByMoney();}
						});
			    	}
				},
				error : function(data) {
					layer.msg("网络连接异常，请刷新页面或稍后重试！",1,5);
			    }
			});
		});
	}
	//关闭
	function closeWin(){
		$("#xjqq").css("display", "none");
	}
</script>
</html>
