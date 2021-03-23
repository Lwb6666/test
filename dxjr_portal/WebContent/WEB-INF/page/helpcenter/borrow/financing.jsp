<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/page/common/taglib.jsp"%>
<%@ include file="/WEB-INF/page/common/public3.jsp"%>
<%@ include file="/WEB-INF/page/common/addr.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script type="text/javascript" src="${path}/js/formValid.js"></script>
<title>[顶玺金融融资服务]融资借款平台_如何申请个人借款-顶玺金融</title>
<meta name="keywords" content="顶玺金融融资,如何申请个人借款,个人借款流程,融资借款平台,网贷平台" />
<meta name="description" content="顶玺金融（www.dxjr.com），一个专注于消费金融与房产抵押贷款的中国社科院网贷评级A级的理财平台。致力于为投资理财用户和贷款用户两端搭建安全、规范、透明的互联网金融平台。投资理财用户可通过顶玺金融官网官方网站进行散标投资、定期宝、活期宝、购买债权转让等方式进行投资获得稳定收益。">
</head>
<body>
	<%@ include file="/WEB-INF/page/common/header.jsp"%>
	<div class="product-wrap wrapperbanner">
		<!--融资页面star-->
		<div class="grid-1100">
			<div class="product-deatil clearfix ">
				<h1 class="f16 bule">我要融资</h1>
				<div class="gc-rz clearfix">
					<div class="gc-img-wap infobanner"></div>
					<ul class="col5">
						<li class="pl15">填写融资信息</li>
						<li class="pl130">理财客户专<br />员联系您
						</li>
						<li class="pl130">提交相关资料</li>
						<li class="pl130">风控审核</li>
						<li class="pl150">审核通过<br />放款
						</li>
					</ul>
				</div>
			</div>
		</div>
	</div>
	<div class="product-wrap">
		<!--融资页面star-->
	<form id="fm" name="fm" method="post">
		<div class="grid-1100">
			<div class="product-deatil clearfix ">
				<div class="form-info">
					<div class="form-col">
						<label for="" class="colleft form-lable">融资金额<i>*</i></label> <input
							type="text" id="financeMoney" maxlength="5" dataType="Require|Money" name="account"    style="color:#999;" class="colright form-inpyt-big"  msg="请输入融资金额|输入融资金额范围在1-100000"  onblur="if(value==''){value='输入金额';$('#financeMoney').attr('style','color:#999');}" onfocus="if(value=='输入金额'){value='';$('#financeMoney').attr('style','color:#FFFFF');}" value="输入金额" placeholder="输入金额">
					         <div style="line-height:45px; float:left; padding-left:10px;">万元</div>
					</div>
					<div class="form-col">
						<label for="" class="colleft form-lable">融资期限<i>*</i></label> <input
							type="text" id="apr" maxlength="2" name="timeLimit" dataType="Require|Number"  style="color:#999;"  class=" colright form-inpyt-big" msg="请输入融资期限|融资期限只能输入数字" onblur="if(value==''){value='输入期限';$('#apr').attr('style','color:#999');}" onfocus="if(value=='输入期限'){value='';$('#apr').attr('style','color:#FFFFF');}" value="输入期限" placeholder="输入期限">
							<div style="line-height:45px; float:left; padding-left:10px;">月</div>
					</div>
					<div class="form-col">
						<label for="" class="colleft form-lable">联系人<i>*</i></label> <input
							type="text" style="color:#999;"  class="colright form-inpyt-big" id="name" maxlength="15" name="name" dataType="Require" msg="请输入联系人"  onblur="if(value==''){value='输入联系人姓名';$('#name').attr('style','color:#999');}" onfocus="if(value=='输入联系人姓名'){value='';$('#name').attr('style','color:#FFFFF');}" value="输入联系人姓名" placeholder="输入联系人姓名">
					</div>
					<div class="form-col">
						<label for="" if="tel" name="tel" class="colleft form-lable">联系电话<i>*</i></label> <input
							type="text" style="color:#999;"  class="colright form-inpyt-big" id="tel" name="tel"  dataType="Require" msg="请输入联系电话 " maxlength="15" onblur="if(value==''){value='输入手机号码';$('#tel').attr('style','color:#999');};" onfocus="if(value=='输入手机号码'){value='';$('#tel').attr('style','color:#FFFFF');}" value="输入手机号码" placeholder="输入手机号码">
					</div>
					<div class="form-col">
						<label for="" class="colleft form-lable">QQ号</label> <input
							type="text" style="color:#999;"  class="colright form-inpyt-big" name="qq" id="qqNum" require="false" maxlength="15" dataType="Number" msg="请输入合法的QQ号码"   onblur="if(value==''){value='输入qq';$('#qqNum').attr('style','color:#999');}" onfocus="if(value=='输入qq'){value='';$('#qqNum').attr('style','color:#FFFFF');}" value="输入qq" placeholder="输入qq">
					</div>
					<div class="form-col">
						<label for="" class="colleft form-lable" style="padding-top: 2px;">所在地区<i>*</i></label> 
						<div class="rzfrom-r">
						       <p id="residenceProvince"  style="color: #333;font-size: 14px;padding-top: 13px ;">上海市</p>
						       <input type="hidden" name="provinceCode" value="310000"/>
						</div>
						<div class="rzfrom-r" style="padding-left: 20px;">
							<select id="residenceCity" name="cityCode" size="1" style="height:30px;margin-top: 7px;"
								style="width: 100px;" operator="GT" to="0">
								<option value="">--请选择--</option>
							</select>
						</div>
						<div class="rzfrom-r" style="padding-left: 20px;">
							<select id="residenceDistrict" name="districtCode" size="1" style="height:30px;margin-top: 7px;"
								style="width: 100px;" operator="GT" to="0">
								<option value="">--请选择--</option>
							</select>
						</div>
					</div>
					<div class="form-col">
						<label for="" class="colleft form-lable">抵押类型<i>*</i></label> 
				            <input class="inputwidht" checked="checked"   style="width:16px;width:18px\0; border:1px red solid;border:none;border:none\0; " type="radio" name="mortgageType" value="1" />
				            <label class="form-lable"> 房产抵押</label>
				            <input   style="width:16px;width:18px\0;border:none;border:none\0; " type="radio" name="mortgageType" value="2" />
				            <label class="form-lable"> 车产</label>
				           <!--  <input   style="width:16px;width:18px\0;border:none;border:none\0; " type="radio" name="mortgageType" value="3" />
				            <label class="form-lable"> 民品（黄金、首饰等）</label> -->
					</div>
					<div class="form-col">
						<label for="" class="colleft form-lable">验证码<i>*</i></label> <input  style="color:#999;" 
							type="text" class="colright form-inpyt-big" id="validatecode" name="validatecode" maxlength="4"
						dataType="Require" msg="请输入验证码" onblur="if(value==''){value='输入验证码';$('#validatecode').attr('style','color:#999');}" onfocus="if(value=='输入验证码'){value='';$('#validatecode').attr('style','color:#FFFFF');}" value="输入验证码" placeholder="输入验证码">
						&nbsp;&nbsp;<a href="javascript:loadimage();"><img name="randImage" id="randImage" src="${basePath}/random.jsp" width="91" height="30" style="margin-bottom: 6px;"/></a> 
						&nbsp;&nbsp;<a href="javascript:loadimage();" style="font-size: 14px; line-height: 40px; color: #388BCF;">看不清，换一张</a>
					</div>
					<div class="form-col pd40">
						<div class="btn-box pl100">
							<input class="btn-big btn-primary" id="apply" type="button"
								onclick="FinanceApply();" value="立即提交" />
						</div>
					</div>
				</div>
				<div class="form-info-ts">
					<table width="100%" border="0" cellspacing="0" cellpadding="0">
						<tr>
							<td width="440px"><span class="icon rzti01"></span>温馨提示</td>
							<td width="500px"><span class="icon rzti02"></span>如何放款</td>
							<td><span class="icon rzti03"></span>融资专线</td>
						</tr>
						<tr>
							<td>因公司风控要求<br />目前仅接入上海地区<br />抵押借款业务
							</td>
							<td>符合条件的融资用户<br />我们会在2小时内与您联系<br />快速放款！
							</td>
							<td>185 1615 5599<br />185 1609 2693
							</td>
						</tr>
					</table>

				</div>
			</div>
		</div>
		  <input type="hidden" id="provinceName" name="provinceName"/>
          <input type="hidden" id="cityName" name="cityName"/>
          <input type="hidden" id="districtName" name="districtName"/>
	</form>
	</div>
	<div class="clearfix"></div>
	<%@ include file="/WEB-INF/page/common/footer.jsp"%>
</body>
<style type="text/css">
.rzfrom-r {
	font-size: 14px;
	color: #999;
	float: left;
	text-align: left;
}

.rzfrom-r input {
	border: 1px #ccc solid;
	height: 34px;
	width: 210px;
	padding-left: 10px;
	color: #666;
	*line-height: 34px;
}

.rzfrom-r select {
	width: 102px;
	padding-left: 5px;
	height: 40px;
	border: 1px #ccc solid;
	border-radius: 3px;
}
</style>
<script type="text/javascript">
	$("#financeMoney").blur(function() {
		var account = $('#financeMoney').val();

		if (account == null || account == "") {
			layer.tips('融资金额不能为空', $('#financeMoney'), {
				guide : 1,
				time : 3,
				style : [ 'background-color:#78BA32; color:#fff', '#78BA32' ],
				closeBtn : [ 0, true ]
			});
			return false;
		}
	});

	$("#apr").blur(function() {
		var account = $('#apr').val();

		if (account == null || account == "") {
			layer.tips('融资期限不能为空', $('#apr'), {
				guide : 1,
				time : 3,
				style : [ 'background-color:#78BA32; color:#fff', '#78BA32' ],
				closeBtn : [ 0, true ]
			});
			return false;
		}

		if (account > 99 || account < 1) {
			layer.tips('融资期限范围在1-99', $('#apr'), {
				guide : 1,
				time : 3,
				style : [ 'background-color:#78BA32; color:#fff', '#78BA32' ],
				closeBtn : [ 0, true ]
			});
			return false;
		}
	});

	$("#name").blur(function() {
		var account = $('#name').val();

		if (account == null || account == "") {
			layer.tips('联系人不能为空', $('#name'), {
				guide : 1,
				time : 3,
				style : [ 'background-color:#78BA32; color:#fff', '#78BA32' ],
				closeBtn : [ 0, true ]
			});
			return false;
		}
	});

	$("#tel").blur(function() {
		var account = $('#tel').val();

		if (account == null || account == "") {
			layer.tips('联系电话不能为空', $('#tel'), {
				guide : 1,
				time : 3,
				style : [ 'background-color:#78BA32; color:#fff', '#78BA32' ],
				closeBtn : [ 0, true ]
			});
			return false;
		}
	});
	function FinanceApply() {
	 	if (Validator.Validate('fm', 4) == false) {
			return;
		} 
		var residenceCity = $("#residenceCity").find("option:selected").text();
				if (residenceCity=="--请选择--") {
					layer.tips('所在地区不能为空', $('#residenceCity'), {
						guide : 1,
						time : 3,
						style : [ 'background-color:#78BA32; color:#fff', '#78BA32' ],
						closeBtn : [ 0, true ]
					});
					return false;
				}
				var residenceCity = $("#residenceDistrict").find("option:selected").text();
				if (residenceCity=="--请选择--") {
					layer.tips('所在地区不能为空', $('#residenceDistrict'), {
						guide : 1,
						time : 3,
						style : [ 'background-color:#78BA32; color:#fff', '#78BA32' ],
						closeBtn : [ 0, true ]
					});
					return false;
				}
		pinJieAddr('residenceProvince', 'residenceCity',
				'residenceDistrict');
		var validatecode = $('#validatecode').val();
		$('#apply').attr("disabled", "disabled");
		var _load = layer.load('处理中..');
		
		$("#fm").ajaxSubmit({
			url : '${basePath }/financing/financingApply.html',
			type : 'post',
			dataType:'json',
			success : function(result) {
				loadimage();
				if(result.code=="1"){
					if(layer.msg("您的贷款申请已提交，顶玺金融的客服会尽快联系您，请保持您的手机畅通",2,1,function(){
						//启用链接
						$('#apply').removeAttr("disabled");
					/* 	$('#fm')[0].reset();  */
						layer.close(_load);
						window.parent.location.href="${basePath }/bangzhu/25.html";
					}));
				}else{
					 layer.msg(result.message, 1, 5);
					 $('#apply').removeAttr("disabled");
					 layer.close(_load);
				}
			},
			error : function(result) {
				 loadimage();
				 layer.msg("网络连接异常，请刷新后重试", 1, 5);
				 $('#apply').removeAttr("disabled");
				 layer.close(_load);
		    }
		}); 
	}
	$(function() {
		//初始化省市区
		initPC('residenceProvince', 'residenceCity', 'residenceDistrict');
	});
	/**
	 * 刷新验证码 
	 */
	function loadimage() {
		document.getElementById("randImage").src = "${basePath}/random.jsp?"
				+ Math.random();
	}
</script>
</html>