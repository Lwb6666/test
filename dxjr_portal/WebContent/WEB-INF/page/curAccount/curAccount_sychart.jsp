<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/page/common/taglib.jsp"%>

<!-- 收益明细 - 查询条件 s  -->
<div class="searchbox fl whitebg">
	<!-- 收益明细列表-查询条件 s -->
	<ul class="lb_title whitebg" style=" border-top:none;">
		<li>查询日期： <input class="inputText02" name="beginDay" id="beginDay" class="Wdate" value="${beginDay}" onClick="WdatePicker({maxDate:'#F{$dp.$D(\'endDay\')}'});"> ~ <input
			class="inputText02" name="endDay" id="endDay" class="Wdate" value="${endDay}" onClick="WdatePicker({minDate:'#F{$dp.$D(\'beginDay\')}'});">
		</li>
		<li class="lb_culi"><span class="lb_title_btn"> <a href="javascript:void(0);" class="greens" onclick="searchBtnSymx(1)">查询</a>
		</span></li>
		<li class="ml30 mdt10"><a class="textcenter pd-but mr5" id="jin7A" href="javascript:void(0);" onclick="searchBtnSymx(7)">近7天</a></li>
		<li class="mdt10"><a class="textcenter pd-but red" id="jin30A" href="javascript:void(0);" onclick="searchBtnSymx(30)">近30天</a></li>
	</ul>
	<!-- 收益明细列表-查询条件 e -->
</div>

<script type="text/javascript">

	//查询 btn:  pageNum 作为tag标记
	function searchBtnSymx(tag) {
		$("#jin7A").removeClass("red");
		$("#jin30A").removeClass("red");
		if(tag==7 || tag==30){
			$("#jin"+tag+"A").addClass("red");
		}
		if(tag == 1){
			var beginDay = $("#beginDay").val();
			var endDay = $("#endDay").val();
			if(beginDay.length == 0){
				layer.msg("开始日期不能为空!", 2, 5);
				return false;
			}
			if(endDay.length == 0){
				layer.msg("结束日期不能为空!", 2, 5);
				return false;
			}
			var start = new Date(beginDay.replace("-", "/").replace("-", "/"));
			var end = new Date(endDay.replace("-", "/").replace("-", "/"));
			if (end < start) {
				layer.msg("开始日期不能大于结束日期!", 2, 5);
				return false;
			}
			if(Number(end.getTime() / 1000) - Number(start.getTime() / 1000) > 24*60*60*31){
				layer.msg("开始日期与结束日期间隔天数不能大于31天!", 2, 5);
				return false;
			}
		}
		parent.showcharts(tag);
	}
</script>