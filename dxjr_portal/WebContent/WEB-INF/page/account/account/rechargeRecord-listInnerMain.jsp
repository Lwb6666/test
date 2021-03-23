<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8" %>
<%@ include file="/WEB-INF/page/common/taglib.jsp" %>
<style type="text/css">
.type1{float:left;width:110px; text-align:left;}
.type1 a{ font-size:14px; padding:3px 12px; color:#666666; line-height:40px;}
.type1 a:hover,.type1 a.active{ text-decoration:none; background:#45b8ef; border-radius:2px; color:#fff;}
</style>
<script type="text/javascript">
  var  RechargeStatus;
  var  RechargeTime;
  var  Custody;
	//获取标题文本框内容
	$(document).ready(function(){
			//获取是否存管状态
			$(".type1").click(function(){ //注册click事件
				$(".type1").children('a').removeClass("active");
				$(this).siblings().removeClass("active"); //移除同辈节点的class
				$(this).children('a').addClass("active"); //给当前节点添加class
				Custody = $(this).children("a").attr("value"); //赋值
				searchrechargeRecord(); 
			});
			//获取充值状态
			$(".type").click(function(){ //注册click事件
				$(".type").children('a').removeClass("active");
				$(this).siblings().removeClass("active"); //移除同辈节点的class
				$(this).children('a').addClass("active"); //给当前节点添加class
				RechargeStatus = $(this).children("a").attr("value"); //赋值
				searchrechargeRecord(); 
			});
			//获取充值时间
			$(".term").click(function(){ //注册click事件
				$(".term").children('a').removeClass("active");
				$(this).siblings().removeClass("active"); //移除同辈节点的class
				$(this).children('a').addClass("active"); //给当前节点添加class
				RechargeTime = $(this).children("a").attr("value"); //赋值
				searchrechargeRecord(); 
			});
			rechargeRecord1();
	});
    /**
     * 默认充值记录查询
     */
    function rechargeRecord1() {
        $.ajax({
            url: '${basePath}/account/topup/toRechargeRecordInner/1.html',
            data: {
                  defaultTime:0
            },
            type: 'post',
            dataType: 'text',
            success: function (data) {
                $("#main_content1").html(data);
            },
            error: function (data) {
                layer.msg("网络连接异常，请刷新页面或稍后重试！", 1, 5);  // 弹出框
            }
        });
    }
    /**
     * 查询充值记录
     */
    function searchrechargeRecord() {
        $.ajax({
            url: '${basePath}/account/topup/toRechargeRecordInner/1.html',
            data: {
            	rechargeStatus:RechargeStatus,
                rechargeTime:RechargeTime,
                custody:Custody,
                beginTime: $("#beginTime1").val(),
                endTime: $("#endTime1").val(),
                defaultTime:1
            },
            type: 'post',
            dataType: 'text',
            success: function (data) {
                $("#main_content1").html(data);
            },
            error: function (data) {
                layer.msg("网络连接异常，请刷新页面或稍后重试！", 1, 5);  // 弹出框
            }
        });
    }

    /**
     * 充值记录列表翻页
     */
    function turnrechargePageParent(pageNo) {
        $.ajax({
            url: '${basePath}/account/topup/toRechargeRecordInner/' + pageNo + '.html',
            data: {
            	rechargeStatus:RechargeStatus,
                rechargeTime:RechargeTime,
                custody:Custody,
                beginTime: $("#beginTime1").val(),
                endTime: $("#endTime1").val(),
                defaultTime:1
            },
            type: 'post',
            dataType: 'text',
            success: function (data) {
                $("#main_content1").html(data);

            },
            error: function (data) {
                layer.msg('网络连接异常，请刷新页面或稍后重试！', 1, 5);
            }
        });
    }
    function queryByTime(){
    	$(".term").children('a').removeClass("active");
    	$.ajax({
              url: '${basePath}/account/topup/toRechargeRecordInner/1.html',
              data: {
                  rechargeStatus:RechargeStatus,
                  beginTime: $("#beginTime1").val(),
                  endTime: $("#endTime1").val(),
                  defaultTime:1
              },
              type: 'post',
              dataType: 'text',
              success: function (data) {
                  $("#main_content1").html(data);
              },
              error: function (data) {
                  layer.msg("网络连接异常，请刷新页面或稍后重试！", 1, 5);  // 弹出框
              }
          });
    }
</script>
<div class="tbl-cont">
    <div class="grid-1100">
        <div class="product-deatil clearfix ">
            <div class="tz-dqb2 clearfix">
                <div class="col clearfix">
                    <span class="fl gary2">充值总额：</span>
                    <div class="btn-box-bg">
                        <span class="orange" id="rechargeTotal"></span><span>元</span>
                    </div>
                </div>
                <div class="col clearfix">
                    <span class="fl gary2">账户类型：</span>
                    <div class="btn-box-bg">
                        <div class="type1">
                            <a href="javascript:void(0);" class="active" value="">全部</a>
                        </div>
                        <div class="type1">
                            <a href="javascript:void(0);" value="0">非存管</a>
                        </div>
                        <div class="type1">
                            <a href="javascript:void(0);" value="1">存管</a>
                        </div>
                    </div>
                </div>
                <div class="col clearfix">
                    <span class="fl gary2">充值状态：</span>
                    <div class="btn-box-bg">
                        <div class="type"><a href="javascript:void(0);" class="active" value="">全部</a></div>
                        <div class="type"><a href="javascript:void(0);" value="1">成功</a></div>
                        <div class="type"><a href="javascript:void(0);" value="9">失败</a></div>
                        <div class="type"><a href="javascript:void(0);" value="0">充值审核中</a></div>
                        <div class="type" style="width: 150px;"><a href="javascript:void(0);" value="2">在线充值待付款</a></div>
                        <div class="type"><a href="javascript:void(0);" value="3">充值初审成功</a></div>
                    </div>
                </div>
                <div class="col clearfix">
                    <span class="fl gary2">充值日期：</span>
                    <div class="btn-box-bg">
                        <div class="term"><a href="javascript:void(0);" value="7">全部</a></div>
                        <div class="term">
                            <input  type="text" name="beginTime" id="beginTime1" value="" onClick="WdatePicker()"/>
        							<span>至</span>
        					<input  type="text" name="endTime" id="endTime1" value="" onClick="WdatePicker()"/>
        					<button type="button" class="btn-middle btn-blue mt5" onclick="queryByTime();">查询</button>
                        </div>
                        <div class="term"><a href="javascript:void(0);" class="active"  value="4">今天</a></div>
                        <div class="term"><a href="javascript:void(0);" value="5" >3个月</a></div>
                        <div class="term"><a href="javascript:void(0);" value="6">6个月</a></div>
                    </div>
                </div>
            </div>
        </div>
         <div id="main_content1" style="clear:both"></div>
    </div>
</div>
