
$(document).ready(function(){
	$("#top_btn").click(function(){if(scroll=="off") return;$("html,body").animate({scrollTop: 0}, 300);});//回顶部
	$(".menu-tbl li").click(function(){//tbl切换
		//alert("00")
		tabIndex = $(this).index();
		$(this).siblings("li").removeClass("active");
		$(this).addClass("active");
		$(".menucont").children(".tbl-cont").hide();
		$(".menucont").children(".tbl-cont").eq(tabIndex).show();
		$(".change").children(".tbl-cont").hide();//统一切换调用2016.4.7light
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
  $(this).find('ul').slideDown(200);//speed
  });
  $('li.mainlevel').mouseleave(function(){
  $(this).find('ul').slideUp(0);


});
});
$(function(){

	var nav = $('.navbar'),
		doc = $(document),
		win = $(window);

	win.scroll(function() {

		if (doc.scrollTop() > 30) {
			//alert("00")
			nav.addClass('scrolled');
		} else {
			nav.removeClass('scrolled');
		}

	});
	var nav1 = $('.topmenu'),
	doc = $(document),
	win = $(window);

win.scroll(function() {

	if (doc.scrollTop() > 30) {
		nav1.addClass('scrolled1');
	} else {
		nav1.removeClass('scrolled1');
	}

});
	win.scroll();

	/*$(".moredown").click(function(){
		$(".link-more .more").toggle("");
		$('.moredown').css('display','none'); 
		$('.moreup').css('display','inline-block'); 
		});
	$(".moreup").click(function(){
		$(".link-more .more").toggle("");
		$('.moredown').css('display',''); 
		$('.moreup').css('display','none'); 
		});*/
	$(".kan").click(function(){
		$(".showtr").toggle("");
		
		});
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
$(function(){//加入下拉菜单
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
})
 
 $(document).ready(function(){ 
            var color="#f0f7ff"
            $(".tbtr tr:even,.tbtr01 tr:even,.tbtr02 tr:even,.tbtr03 tr:even,.tbtr04 tr:even,.tbtr05 tr:even,.tbtr06 tr:even,.tbtr07 tr:even,.tbtr08 tr:even,.tbtr09 tr:even,.tbtr10 tr:even,.tbtr11 tr:even,.tbtr12 tr:even,.tbtr13 tr:even,.tbtr14 tr:even,.tbtr15 tr:even,.tbtr16 tr:even,.tbtr17 tr:even").attr("bgcolor",color);//改变奇数行背景色
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

//充值单选效果  
/*$(function(){ 
	$('.choosebox li a').click(function(){
		var thisToggle = $(this).is('.size_radioToggle') ? $(this) : $(this).prev();
		var checkBox = thisToggle.prev();
		checkBox.trigger('click');
		$('.size_radioToggle').removeClass('current');
		thisToggle.addClass('current');
		return false;

	});		
	
});*/

$(".choosebox li a").click(function(){
	var text = $(this).html();
	$(".choosetext span").html(text);
	$("#result").html("" + getSelectedValue("dress-size"));
});


//提示			
function getSelectedValue(id){
	return 
	$("#" + id).find(".choosetext span.value").html();
}

	$(function(){
			$('#tip-userpic').poshytip({
				className: 'tip-yellowsimple',
				showTimeout: 1,
				alignTo: 'target',
				alignX: 'center',
				alignY: 'bottom',
				offsetY: 50,
				allowTipHover: true,
				
			});
			$('#tip-top').poshytip({
				className: 'tip-yellowsimple',
				showTimeout: 1,
				alignTo: 'target',
				alignX: 'center',
				offsetY: 5,
				allowTipHover: false,
			});
			
			$('#tip-bottom').poshytip({
				className: 'tip-yellowsimple',
				showTimeout: 1,
				alignTo: 'target',
				alignX: 'center',
				alignY: 'bottom',
				offsetX: 15,
				offsetY: 10,
				allowTipHover: false,
				
			});
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
			$('#tip-bottom2').poshytip({
				className: 'tip-yellowsimple',
				showTimeout: 1,
				alignTo: 'target',
				alignX: 'center',
				alignY: 'bottom',
				offsetY: 10,
				allowTipHover: false,
				
			});
			$('#tip-bottom3').poshytip({
				className: 'tip-yellowsimple',
				showTimeout: 1,
				alignTo: 'target',
				alignX: 'center',
				alignY: 'bottom',
				offsetY: 10,
				allowTipHover: false,
				
			});
			$('#tip-bottom4').poshytip({
				className: 'tip-yellowsimple',
				showTimeout: 1,
				alignTo: 'target',
				alignX: 'center',
				alignY: 'bottom',
				offsetY: 10,
				allowTipHover: false,
				
			});
			$('#tip-bottom5').poshytip({
				className: 'tip-yellowsimple',
				showTimeout: 1,
				alignTo: 'target',
				alignX: 'center',
				alignY: 'bottom',
				offsetY: 10,
				allowTipHover: false,
				
			});
			$('#tip-daishou').poshytip({
				className: 'tip-yellowsimple',
				showTimeout: 1,
				alignTo: 'target',
				alignX: 'center',
				alignY: 'bottom',
				offsetY: 10,
				allowTipHover: false,
			});
			$('#tip-dongjie').poshytip({
				className: 'tip-yellowsimple',
				showTimeout: 1,
				alignTo: 'target',
				alignX: 'center',
				alignY: 'bottom',
				offsetY: 10,
				allowTipHover: false,
				
			});
			$('#tip-left').poshytip({
				className: 'tip-yellowsimple',
				showTimeout: 1,
				alignTo: 'target',
				alignX: 'right',
				alignY: 'bottom',
				offsetX: 15,
				offsetY: -45,
				allowTipHover: false,
				
			});
			$('.ico-top').poshytip({
				className: 'tip-yellowsimple',
				showTimeout: 1,
				alignTo: 'target',
				alignX: 'center',
				offsetY: 5,
				allowTipHover: false,
			});
			$('.redbao-right').poshytip({
				className: 'tip-yellow',
				showTimeout: 1,
				alignTo: 'target',
				alignX: 'right',
				offsetX: 6,
				offsetY: -25,
				allowTipHover: false,
			});



	$(".close").click(function(){
    $(".bankcard-tip").fadeOut();
});
var flag=true;
$('#hb').click(function (){
	if(flag){
		 $('.nobao').slideDown("fast");
		 $('.page').slideDown("fast");
		 $('.hb a').removeClass("arrowdown");
		 flag=false;
	}else{
		 $('.nobao').slideUp("fast");
		  $('.page').slideUp("fast");
		 $('.hb a').addClass("arrowdown");
		 flag=true;	
	}
});
		
});


