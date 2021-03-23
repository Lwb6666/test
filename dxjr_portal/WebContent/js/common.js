

$(document).ready(function(){
	$("#top_btn").click(function(){if(scroll=="off") return;$("html,body").animate({scrollTop: 0}, 300);});//回顶部
	$(".menu-tbl li").click(function(){//tbl切换
		//alert("00")
		tabIndex = $(this).index();
		$(this).siblings("li").removeClass("active");
		$(this).addClass("active");
		$(".menucont").children(".tbl-cont").hide();
		$(".menucont").children(".tbl-cont").eq(tabIndex).show();
		});
	$(".menu-left li").click(function(){//tbl切换
		//alert("00")
		tabIndex = $(this).index();
		$(this).siblings("li").removeClass("active");
		$(this).addClass("active");
		$(".change").children(".tbl-cont").hide();
		$(".change").children(".tbl-cont").eq(tabIndex).show();
		});
 $(".fullSlide").hover(function(){//首页轮播
		$(this).find(".prev,.next").stop(true, true).fadeTo("show", 0.5)
	},
	function(){
		$(this).find(".prev,.next").fadeOut()
	});
	$(".fullSlide").slide({
		titCell: ".hd ul",
		mainCell: ".bd ul",
		effect: "fold",
		autoPlay: true,
		autoPage: true,
		trigger: "click",
		startFun: function(i) {
			var curLi = jQuery(".fullSlide .bd li").eq(i);
			if ( !! curLi.attr("_src")) {
				curLi.css("background-image", curLi.attr("_src")).removeAttr("_src")
			}
		}
	});
  $('li.mainlevel').mousemove(function(){//menu nav
  $(this).find('ul').slideDown();//you can give it a speed
  });
  $('li.mainlevel').mouseleave(function(){
  $(this).find('ul').slideUp("fast");


});
  
  
  var nav2 = $('.aboutus-menu-box'),
	doc = $(document),
	win = $(window);

win.scroll(function() {

	if (doc.scrollTop() > 330) {
		//alert("00")
		nav2.addClass('scrolled2');
	} else {
		nav2.removeClass('scrolled2');
	}

});

win.scroll();
  
});

$(document).ready(function(e) {//弹层
    /*$(".btn-box2 .btn-join").click(function(){
		$(".layer-jion").show();
		$(".cont-word .contls-box").animate({top:"20px"});
		});*/
	$(".icon-close").click(function(){
		$(".layer-jion").hide();
		$(".cont-word").animate({top:"0px"});
		})
});
//$(document).ready(function(e) {
//   $("ul.gc-item li").hover(function(){
//		if($(this).prop("className")=="calculator"){
//			$(this).children(".gc-calculator").show();
//		}else{
//			$(this).children(".tool-item-u").show();
//			//$(this).children("img.shows").hide();
//			$(this).children(".tool-item-u").animate({marginRight:'0px'},'slow'); 
//		}
//	},function(){
//			
//			$(this).children(".gc-calculator").hide();
//			});
//	 $(".tool-item2").mouseleave(function(){
//		 //alert("00")
//		$(this).children(".gc-calculator").animate({right:"10px"}).hide();
//		});
//		$(".colse").click(function(){
//				$(".gc-calculator").hide();
//				});
//});

$(document).ready(function(e) {
    function animate(){
	$(".charts").each(function(i,item){
		var a=parseInt($(item).attr("w"));
		$(item).animate({
			width: a+"%"
		},2000);
	});
}
animate();
});
/*$(function(){//加入下拉菜单
	$(".select").each(function(){
		var s=$(this);
		var z=parseInt(s.css("z-index"));
		var dt=$(this).children("dt");
		var dd=$(this).children("dd");
		var _show=function(){dd.slideDown(200);dt.addClass("cur");s.css("z-index",z+1);};   //展开效果
		var _hide=function(){dd.slideUp(200);dt.removeClass("cur");s.css("z-index",z);};    //关闭效果
		dt.click(function(){dd.is(":hidden")?_show():_hide();});
		dd.find("a").click(function(){dt.html($(this).html());_hide();});     //选择效果（如需要传值，可自定义参数，在此处返回对应的“value”值 ）
		$("body").click(function(i){ !$(i.target).parents(".select").first().is(s) ? _hide():"";});
	})
})*/
 
 $(document).ready(function(){ 
            var color="#f0f7ff"
            $(".tbtr tr:even,.tbtr02 tr:even,.tbtr01 tr:even,.tbtr03 tr:even,.tbtr04 tr:even,.tbtr05 tr:even,.tbtr06 tr:even,.tbtr07 tr:even,.tbtr08 tr:even,.tbtr09 tr:even,.tbtr10 tr:even,.tbtr11 tr:even,.tbtr12 tr:even,.tbtr13 tr:even,.tbtr14 tr:even,.tbtr15 tr:even,.tbtr16 tr:even,.tbtr17 tr:even").attr("bgcolor",color);//改变奇数行背景色
      })


//返回按钮显示隐藏
$(window).bind("scroll",function() { 
var temp = '200';

//判断往下滚 
if ($(document).scrollTop() > temp ) { //如果大于这个高度就显示
flag = false; 
$('#top_btn').slideDown();
} 
if ($(document).scrollTop() <= temp )  { //如果小于这个高度则不显示 
flag = true; 
$('#top_btn').slideUp();

} 
})


