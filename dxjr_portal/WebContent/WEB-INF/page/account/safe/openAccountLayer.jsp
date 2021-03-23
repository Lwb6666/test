<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ include file="/WEB-INF/page/common/taglib.jsp" %>

<!--弹层star--->
<div id="myModal" class="reveal-modal layer-join">
    <div class="cont-word">
        <div class="form-info-layer">
            <div class="form-col2" style=" padding:40px 0;">

                <p class="f16"> 平台正在为您开通资金存管账户，开通成功之后请点击完成按钮</p>

            </div>

            <div class="form-col2">
                <button type="button" class="remove" id="btnClose">关闭</button><button type="button" class="enter" id="btnOk">完成</button>
            </div>

        </div>
    </div>
</div>
<!--弹层end--->
<script>
    $(function () {
        $('#btnOk').on('click', function () {
            $.ajax({
                type: "POST",
                async: false,
                url: "${basePath}/account/czbank/validateAccount.html",
                success: function (data) {
                    if (data != null && data.code == 0) {
                        layer.msg('开通成功', 1, 1, function() {
                            window.location.reload();
                        });
                    } else {
                        layer.alert('开通失败', 5);
                        $('#myModal').trigger('reveal:close');
                    }
                }
            });
        });

        $('#btnClose').on('click', function () {
            $('#myModal').trigger('reveal:close');
        });

        var clicked = false;
        $('#btnOpenAccount').on('click', function () {
            if (clicked) {
                return;
            }
            clicked = true;
            var flag = false;
            $.ajax({
                type: "POST",
                async: false,
                url: "${basePath}/account/czbank/validateAccount.html",
                success: function (data) {
                    if (data != null && data.code == 0) {
                        layer.msg(data.message, 1, 5);
                    } else {
                        $('#myModal').reveal({
                            animation: 'fade',                   //fade, fadeAndPop, none
                            animationspeed: 300,                       //how fast animtions are
                            closeonbackgroundclick: false,              //if you click background will modal close?
                            dismissmodalclass: 'close-reveal-modal'    //the class of a button or element that will close an open modal
                        });
                        flag = true;
                    }
                    clicked = false;
                }
            });
            if (flag) {
                window.open("${basePath}/account/czbank/dispatcher.html", '_blank');
            }
        })
    });
</script>