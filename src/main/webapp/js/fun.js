var target = {};
$(function(){
	
	
	windowfit();
	/*页面加载时 调用自适应宽高函数*/
	
	$(".right_control").click(function(){
		$(".c_right").toggleClass("right_show");
		windowfit();
	})
	/*右部缩放*/
	
	$(".top_control").click(function(){
		$(".top").toggleClass("top_hidden");
		windowfit();
	})
	
	$(".left_control").click(function(){
		$(".c_left").toggleClass("left_hidden");
		windowfit();
	})
	/*左部缩放*/
	
/*	$(".left_nav li").click(function(){
		$(".left_nav li").removeClass("on");
		$(this).addClass("on");
		nid = $(this).find("a").attr("id");
		ntext = $(this).text();
		target.name = ntext;
		$(".c_tab li").removeClass("on");
		if($(".c_tab ul li[tabid="+nid+"]").length<1){
			$(".c_tab ul").append("<li class='on' tabid="+nid+"><em onclick='c_tab(this)'>"+ntext+"</em><s onclick='closePage(this)'></s></li>")
		}
		else{
			$(".c_tab li").removeClass("on");
			$(".c_tab li[tabid="+nid+"]").addClass("on");
		}
	})*/
	/*左侧导航函数*/
	
	$(".top_tab li").click(function(){
		$(this).addClass("on").siblings("li").removeClass("on");
		num = $(".top_tab li").index($(this));
		$(".left_nav ul").eq(num).addClass("on").siblings("ul").removeClass("on");
	})
	/*顶部tab函数*/

})
/*function c_tab(obj){
	var cid = $(obj).parents("li").attr("tabname");
	$(".left_nav li a[id="+cid+"]").click();
	target.name = cid;
	$("iframe[name='c_frame']").attr("src",$(".left_nav li a[id="+cid+"]").attr("href"));
	
}
中部tab函数

function closePage(obj){
	$(obj).parents("li").remove();
	var cid = $(obj).parents("li").attr("tabname")
	if(cid == target.name) {
		$(".c_tab li").eq(0).find("em").click();
	}
	$(".left_nav li").removeClass("on");
}*/
/*关闭中部tab*/

$(window).resize(function(){ windowfit(); })
/*浏览器调整时 调用自适应宽高函数*/

function windowfit(){
	topHeight = $(".top").height();
	footHeight = $(".foot").height();
	windowHeight = $(window).height();
	tabHeight = $(".c_tab").height();
	cHeight = windowHeight-topHeight-footHeight;
	$(".container").height(cHeight);
	$(".c_con").height(cHeight-tabHeight);
	
	leftWidth = $(".c_left").width();
	rightWidth = $(".c_right").width();
	windowWidth = $(window).width();
	cWidth = windowWidth-leftWidth-rightWidth;
	$(".c_main").width(cWidth-20);
}
/*自适应宽高函数*/

function showpop(obj){
	$(obj).show();
}
function closepop(obj){
	$(obj).parents(".pop").hide();
}
/*弹出层*/