window.requestAnimFrame = (function () {
  return window.requestAnimationFrame ||
      window.webkitRequestAnimationFrame ||
      window.mozRequestAnimationFrame ||
      window.oRequestAnimationFrame ||
      window.msRequestAnimationFrame ||
      function (callback, element) {
        window.setTimeout(callback, 15);
      };
})();
if (!news) var news = {};
news.changeAnime = function (domJq, now_pos, targetUpdate, excuPos) {

  var win_jq = $(window),
      is_excu = false,
      target = targetUpdate(),
      excu = function () {
        //目标位置
        target = targetUpdate();

        if (is_excu) return;

        callBakeExcu();
      };

  excu.now_pos = now_pos;

  function callBakeExcu() {

    is_excu = true;

    var go_len = .2 * (target - excu.now_pos);

    excu.now_pos += go_len;

    if (Math.abs(target - excu.now_pos) < 1) {
      excuPos(target);
      is_excu = false;

      return;
    }

    excuPos(excu.now_pos);

    window.requestAnimFrame(callBakeExcu);
  }


  return excu;
};
if (!news.module) news.module = {};
news.module.sideFollow = function (side_follow) {

  var win_jq = $(window);

  var excu = news.changeAnime(side_follow, parseFloat(side_follow.css('top')), function () {
    var min_val = win_jq.scrollTop() + 20;

    var tv = win_jq.height() / 2 + min_val - 100;

    if (tv < min_val) tv = min_val;

    return tv;

  }, function (v) {
    side_follow.css('top', v);
  });

  excu();
  win_jq.scroll(excu);
  win_jq.resize(excu);
};

news.module.sfHandle = function (side_follow) {
  var chils = side_follow.children();
   /*  chils.click(function () {
	    var w = 155, h = 38, curr = $(this);
	 
	    switch (chils.index(curr)) {
	      case 4:
	    	if($(this).attr("style").indexOf("160")!=-1){
	    		w = 36;
		   	    h = 38;	
	    	}else{
	    	    w = 160;
	   	        h = 198;
	    	}
	        break;
	      default:
	        return;
	        break;
	    }
	    curr.stop().animate({ width: w, height: h });
  });*/
  chils.hover(function () {
	  side_follow.css('width','255px');
    var w = 38, h = 38, curr = $(this);
    switch (chils.index(curr)) {
      case 0:
        w = 220;
        break;
      case 1:
        w = 255;
        break;
      case 2:
        w = 190;
        break;
      case 3:
        w = 190;
        break;
      default:
        return;
        break;
    }
    curr.stop().animate({ width: w, height: h });
  }, function () {
	  side_follow.css('width','36px');
    var w = 38, h = 38, curr = $(this);
    switch (chils.index(this)) {
      case 1: case 2: case 3:
        w = 36;
        break;
      case 0:
        w = 36;
        h = 38;
        break;
      default:
        return;
        break;
    }
    curr.stop().animate({ width: w, height: h });

  });

  //回顶部 效果
  (function () {
    var w = $(window),
        go_top_dom = chils.eq(4);

    w.scroll(function () {

      if (w.scrollTop() > 300) {

        go_top_dom.css({
          height: 36,
          top: -36
        });
      }
      else {
        go_top_dom.css({
          height: 0,
          top: 0
        });
      }
    });

    var excu = news.changeAnime(w, 0, function () {
      return 0;
    }, function (v) {
      w.scrollTop(v);
    });

    go_top_dom.click(function () {
      excu.now_pos = w.scrollTop();
      excu();
    });
  }());
};
$(function () {  
$(".footer_main").after("<div style='top: 50%; margin-top:-129px; z-index:10000;' id='side_follow'><div style='width: 36px; height: 38px; overflow: hidden;' class='sf_phone'>" +
"<p>400-039-3606</p></div><div style='overflow: hidden; width: 36px; height: 38px;' class='sf_email'><p><a href='mailto:market@chengxindai.com'>market@chengxindai.com</a>" +
"</p></div><div style='width: 36px; height: 38px; overflow: hidden;' class='sf_qqgroup'>" +
"<p>客服：<a target='_blank' href='http://wpa.b.qq.com/cgi/wpa.php?ln=1&key=XzkzODAxMjgyOF8xNTE5OTZfNDAwODgyMTgwMl8yXw'>4008821802</a>" +
"</p></div><div style='overflow: hidden; width: 36px; height: 38px;' class='sf_weibo'><p>" +
"<a href='http://e.weibo.com/yesmyloan' target='_blank'>新浪微博@狮王金融</a></p></div><div style='height: 0px; top: 0px;' class='sf_gotop'></div>"+
"</div>");
$(".footer_main").after('<div id="weixin_box"><a class="weixin_close" onclick="closeWeixin()" title="关闭二维码" href="javascript:void(0)"></a><img width title="扫一扫，关注狮王金融" src="/wdzj/images/front/h_weixin.jpg"></div>');
  //news.module.sideFollow($('#side_follow'));
  news.module.sfHandle($('#side_follow'));
});

//微信
function closeWeixin(){
$('#weixin_box').hide();
}
