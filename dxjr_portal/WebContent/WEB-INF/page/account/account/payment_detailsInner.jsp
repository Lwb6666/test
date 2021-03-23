<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8" %>
<%@ include file="/WEB-INF/page/common/taglib.jsp" %>
<div class="product-deatil mt20">
    <form action="" method="post" id="payMentForm">
        <div class="tz-dqb2 clearfix">
            <div class="col clearfix">
                <span class="fl gary2">账户类型：</span>
                <input type="hidden" value="${accountType}" id="isCustody" name="isCustody">
                <div class="btn-box-bg">
                    <div class="type"><a data-type="-1"
                                         class="<c:if test="${accountType == '' || accountType == null}">active </c:if>account-type">全部</a>
                    </div>
                    <div class="type"><a data-type="0"
                                         class="<c:if test="${accountType == '0'}">active </c:if>account-type">非存管</a>
                    </div>
                    <div class="type"><a data-type="1"
                                         class="<c:if test="${accountType == '1'}">active </c:if>account-type">存管</a>
                    </div>
                </div>
            </div>
            <%--
           <div class="col clearfix">
                   <span class="fl gary2">交易类型：</span>
                   <div class="btn-box-bg">
                       <div class="type"><a href="#" class="active">不限</a></div>
                       <div class="type"><a href="#">充值</a></div>
                       <div class="type"><a href="#">提现</a></div>
                       <div class="type"><a href="#">投资</a></div>
                       <div class="type"><a href="#">回款</a></div>
                       <div class="type"><a href="#">奖励</a></div>
                       <div class="type"><a href="#">费用支出</a></div>
                       <div class="type"><a href="#">其他</a></div>
                   </div>
            </div>
            --%>
            <div class="col clearfix">
                <span class="fl gary2">交易日期：</span>
                <div class="btn-box-bg">
                    <div class="term"><a class="<c:if test="${timeType == '' || timeType == null}">active</c:if>"
                                         href="javascript:$('#startTime').val('');$('#endTime').val('');timeType = '';paymentDetailQuery();">全部</a>
                    </div>
                    <div class="term">
                        <input type="text" class="Wdate" onClick="WdatePicker()" name="startTime" id="startTime"
                               value="${startTime }">
                        至
                        <input type="text" class="Wdate" id="endTime" name="endTime" onClick="WdatePicker()"
                               value="${endTime }">
                        <button type="button" class="btn-middle btn-blue mt5"
                                onclick="timeType = '';paymentDetailQuery()">查询
                        </button>
                        <button type="button" class="btn-middle btn-blue mt5" onclick="exportPaymentDetails()">导出excel
                        </button>
                    </div>
                    <div class="term"><a href="javascript:dateChange('lastsevendays')"
                                         class="<c:if test="${timeType == 'lastsevendays'}">active</c:if>">近1周</a></div>
                    <div class="term"><a href="javascript:dateChange('recentmonth')"
                                         class="<c:if test="${timeType == 'recentmonth'}">active</c:if>">近1月</a></div>
                    <div class="term"><a href="javascript:dateChange('recent3month')"
                                         class="<c:if test="${timeType == 'recent3month'}">active</c:if>">近3月</a></div>
                </div>
            </div>

        </div>
    </form>
    <table class="table tbtr center">
        <thead>
        <tr>
            <td>时间</td>
            <td>交易类型</td>
            <td>交易金额</td>
            <td>资金总额</td>
            <td>备注</td>
            <td>查看</td>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${listUAVO}" var="vavo" varStatus="sta" step="1">
            <tr <c:if test="${sta.index % 2 > 0}">bgcolor="#f0f7ff"</c:if>>
                <td align="left">${vavo.addtimeFMT }</td>
                <td><a class="tip-bottom" title="${vavo.type}">${fn:substring(vavo.type,0,5)}<c:if
                        test="${fn:length(vavo.type)>5}">..</c:if></a></td>
                <td>${vavo.moneyStr }元</td>
                <td>${vavo.totalStr }元</td>
                <td align="right">
                        ${fn:substring(vavo.remarkStr,0,16)}
                        <c:if test="${fn:length(vavo.remarkStr)>16}">..</c:if>
                    <c:if test="${vavo.idType == 0 or vavo.idType == 2}">
                        <c:if test="${not empty vavo.borrowId}">
                            (借款标：<a title='${vavo.borrowName }' href="${path}/toubiao/${vavo.borrowId}.html" target="_blank"> ${fn:substring(vavo.borrowName,0,10)}
                            <c:if test="${fn:length(vavo.borrowName)>10}">..</c:if></a>)
                        </c:if>
                    </c:if>
                    <c:if test="${vavo.idType == 6}">
                        <c:if test="${not empty vavo.borrowId && vavo.remarkStr!='定期宝流宝' && vavo.remarkStr!='定期宝撤宝'}">
                            (<a title='${vavo.borrowName }' href="${path}/dingqibao/${vavo.borrowId}.html" target="_blank">详情</a>)
                        </c:if>
                    </c:if>
                </td>
                <td><a class="btn-showmodal" href="javascript:test(${vavo.id})">查看</a></td>
            </tr>
        </c:forEach>
        </tbody>
    </table>

    <div class="yema">
        <div class="yema_cont">
            <div class="yema rt">
                <jsp:include page="/WEB-INF/page/common/ajaxpage.jsp">
                    <jsp:param name="pageNo" value="${page.pageNo}"/>
                    <jsp:param name="totalPage" value="${page.totalPage}"/>
                    <jsp:param name="hasPre" value="${page.hasPre}"/>
                    <jsp:param name="prePage" value="${page.prePage}"/>
                    <jsp:param name="hasNext" value="${page.hasNext}"/>
                    <jsp:param name="nextPage" value="${page.nextPage}"/>
                </jsp:include>
            </div>
        </div>
    </div>
</div>


<script type="text/javascript">

    $(function () {
        $('.tip-bottom').poshytip({
            className: 'tip-yellowsimple',
            showTimeout: 1,
            alignTo: 'target',
            alignX: 'center',
            alignY: 'bottom',
            offsetX: 15,
            offsetY: 10,
            allowTipHover: false,
        });
        $(".close").click(function () {
            $(".bankcard-tip").fadeOut();
        });
    });


    var startTime = '${startTime}';
    var endTime = '${endTime}';
    var type = '${type}';
    var accountType = '${accountType}';
    var timeType = '${timeType}';

    /**
     * ajax 翻页功能
     */
    function findPage(pageNo) {
        turnFirstTenderRealPageParent(pageNo);
    }
    /**
     * 优先计划列表翻页
     */
    function turnFirstTenderRealPageParent(pageNum) {
        window.parent.search(pageNum,accountType, timeType);
    }

    var dateFormat = function () {
        var token = /d{1,4}|m{1,4}|yy(?:yy)?|([HhMsTt])\1?|[LloSZ]|"[^"]*"|'[^']*'/g,
                timezone = /\b(?:[PMCEA][SDP]T|(?:Pacific|Mountain|Central|Eastern|Atlantic) (?:Standard|Daylight|Prevailing) Time|(?:GMT|UTC)(?:[-+]\d{4})?)\b/g,
                timezoneClip = /[^-+\dA-Z]/g,
                pad = function (val, len) {
                    val = String(val);
                    len = len || 2;
                    while (val.length < len) val = "0" + val;
                    return val;
                };
        // Regexes and supporting functions are cached through closure
        return function (date, mask, utc) {
            var dF = dateFormat;
            // You can't provide utc if you skip other args (use the "UTC:" mask prefix)
            if (arguments.length == 1 && Object.prototype.toString.call(date) == "[object String]" && !/\d/.test(date)) {
                mask = date;
                date = undefined;
            }
            // Passing date through Date applies Date.parse, if necessary
            date = date ? new Date(date) : new Date;
            if (isNaN(date)) throw SyntaxError("invalid date");
            mask = String(dF.masks[mask] || mask || dF.masks["default"]);
            // Allow setting the utc argument via the mask
            if (mask.slice(0, 4) == "UTC:") {
                mask = mask.slice(4);
                utc = true;
            }

            var _ = utc ? "getUTC" : "get",
                    d = date[_ + "Date"](),
                    D = date[_ + "Day"](),
                    m = date[_ + "Month"](),
                    y = date[_ + "FullYear"](),
                    H = date[_ + "Hours"](),
                    M = date[_ + "Minutes"](),
                    s = date[_ + "Seconds"](),
                    L = date[_ + "Milliseconds"](),
                    o = utc ? 0 : date.getTimezoneOffset(),
                    flags = {
                        d: d,
                        dd: pad(d),
                        ddd: dF.i18n.dayNames[D],
                        dddd: dF.i18n.dayNames[D + 7],
                        m: m + 1,
                        mm: pad(m + 1),
                        mmm: dF.i18n.monthNames[m],
                        mmmm: dF.i18n.monthNames[m + 12],
                        yy: String(y).slice(2),
                        yyyy: y,
                        h: H % 12 || 12,
                        hh: pad(H % 12 || 12),
                        H: H,
                        HH: pad(H),
                        M: M,
                        MM: pad(M),
                        s: s,
                        ss: pad(s),
                        l: pad(L, 3),
                        L: pad(L > 99 ? Math.round(L / 10) : L),
                        t: H < 12 ? "a" : "p",
                        tt: H < 12 ? "am" : "pm",
                        T: H < 12 ? "A" : "P",
                        TT: H < 12 ? "AM" : "PM",
                        Z: utc ? "UTC" : (String(date).match(timezone) || [""]).pop().replace(timezoneClip, ""),
                        o: (o > 0 ? "-" : "+") + pad(Math.floor(Math.abs(o) / 60) * 100 + Math.abs(o) % 60, 4),
                        S: ["th", "st", "nd", "rd"][d % 10 > 3 ? 0 : (d % 100 - d % 10 != 10) * d % 10]
                    };
            return mask.replace(token, function ($0) {
                return $0 in flags ? flags[$0] : $0.slice(1, $0.length - 1);
            });
        };
    }();

    // Some common format strings
    dateFormat.masks = {
        "default": "ddd mmm dd yyyy HH:MM:ss",
        shortDate: "m/d/yy",
        mediumDate: "mmm d, yyyy",
        longDate: "mmmm d, yyyy",
        fullDate: "dddd, mmmm d, yyyy",
        shortTime: "h:MM TT",
        mediumTime: "h:MM:ss TT",
        longTime: "h:MM:ss TT Z",
        isoDate: "yyyy-mm-dd",
        isoTime: "HH:MM:ss",
        isoDateTime: "yyyy-mm-dd'T'HH:MM:ss",
        isoUtcDateTime: "UTC:yyyy-mm-dd'T'HH:MM:ss'Z'"
    };

    // Internationalization strings
    dateFormat.i18n = {
        dayNames: [
            "Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat",
            "Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"
        ],
        monthNames: [
            "Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec",
            "January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"
        ]
    };

    // For convenience...
    Date.prototype.format = function (mask, utc) {
        return dateFormat(this, mask, utc);
    };

    function dateChange(part) {
        var beginTime;
        var now = new Date();
        var month = now.getMonth();
        var year = now.getFullYear();
        var day = now.getDate();
        var wday = now.getDay;

        function unsetTime(thisdate) {
            thisdate.setUTCHours(0);
            thisdate.setUTCMinutes(0);
            thisdate.setUTCSeconds(0);
        }

        switch (part) {
                //当月
            case "thismonth" :
                unsetTime(now);
                now.beginTime = now.format("yyyy-mm-dd", now.setDate(1));
                now.endTime = now.format("yyyy-mm-dd", now.setMonth(month + 1));
                break;
                //上个月
            case "lastmonth" :
                unsetTime(now);
                now.setMonth(month - 1);
                now.beginTime = now.format("yyyy-mm-dd", now.setDate(1));
                now.endTime = now.format("yyyy-mm-dd", now.setMonth(month));
                break;
                //最近7天
            case "lastsevendays" :
                now.beginTime = now.format("yyyy-mm-dd", now.setDate(day - 7));
                now.endTime = now.format("yyyy-mm-dd", now.setDate(day));
                break;
                //最近一个月
            case "recentmonth" :
                now.endTime = now.format("yyyy-mm-dd", now.setDate(day));
                now.beginTime = now.format("yyyy-mm-dd", now.setDate(day - 30));
                break;
                //最近三个月
            case "recent3month" :
                now.endTime = now.format("yyyy-mm-dd", now.setDate(day));
                now.beginTime = now.format("yyyy-mm-dd", now.setDate(day - 90));
                break;
            default:
                now.endTime = null;
                now.beginTime = null;
                break;
        }
        $('#startTime').val(now.beginTime);
        $('#endTime').val(now.endTime);
        timeType = part;
        paymentDetailQuery();
    }

    Date.prototype.dateChange = function (part) {
        return dateChange(part);
    };

    //查询
    function paymentDetailQuery() {
        var pageNum = 1;
        window.parent.search(pageNum, accountType, timeType);
    }
    function test(accountId) {
        $.ajax({
            url: '${basePath}/accOpertingRecord/showPaymentDetail.html?accountId=' + accountId,
            type: 'post',
            dataType: 'text',
            success: function (data) {
                $("#myModal").html(data);
            },
            error: function (data) {
                layer.msg('网络连接异常，请刷新页面或稍后重试！', 1, 5);
            }
        });
    }
    $(function () {
        $('.account-type').on('click', function (evt) {
            var $target = $(evt.currentTarget);
            var type = $target.data("type");
            var pageNum = 1;
            if (parseInt(type) >= 0) {
                accountType = type;
            } else {
                accountType = '';
            }
            $("#isCustody").val(accountType);
            window.parent.search(pageNum, accountType, timeType);
        });
        $('.btn-showmodal').on('click', function (evt) {
            $('#myModal').reveal({
                animation: 'fade',                   //fade, fadeAndPop, none
                animationspeed: 300,                       //how fast animtions are
                closeonbackgroundclick: false,              //if you click background will modal close?
                dismissmodalclass: 'close-reveal-modal'    //the class of a button or element that will close an open modal
            });
        });
    });
    function exportPaymentDetails() {
//      var stime=$('#startTime').val();
//        var endtime=$('#endTime').val();
//        if(stime==null || stime==''){
//            layer.msg("请选择交易开始日期开始",1,5);
//            return ;
//        }else{
//            if(endtime==null || endtime==''){
//                layer.msg("请选择交易结束日期",1,5);
//                return ;
//            }
//        }
//        var date1=new Date(Date.parse(stime.replace(/-/g, "/")));
//        var date2=new Date(Date.parse(endtime.replace(/-/g, "/")));
//        var differenc =date2.getTime()-date1.getTime();
//        if(differenc<0){
//            layer.msg("结束日期不能小于开始日期",1,5);
//            return ;
//        }
//       var month= date2.getMonth()-date1.getMonth();
//        if(month<=2){

        $("#payMentForm").ajaxSubmit({
            url : '${basePath}/accOpertingRecord/export/paymentDetailsCount.html',
            type : 'post',
            dataType : 'json',
            success:function (data) {
                if(data.code=='1'){
                    $("#payMentForm").attr("action",
                            "${basePath}/accOpertingRecord/export/paymentDetails.html");
                    $("#payMentForm").submit();
                }else{
                    layer.msg(data.message,1,5);
                }
            },
            error:function (data) {
                layer.msg('网络连接异常，请稍后重试！');
            }
        })

//        }else{
//            layer.msg("结束日期开始日期最多相差3个月",1,5);
//            return ;
//        }

    }

</script>