
$(function(){
	var globe = null;
	var target = {};
	var globemenu = [[
		                 {
		                     text: "关闭全部标签",
		                     func: function(){
		                        $(this).siblings().remove();
		                        $(this).remove();
		                        var cid = $(this).parents("li").attr("tabname")
		                		if(cid == target.name) {
		                			$(".c_tab li").eq(0).find("em").click();
		                		}
		                     }
		                 },
		                 {
		                     text: "关闭其他标签",
		                     func: function(){
		                    	$(this).siblings().remove();
		                    	var cid = $(this).parents("li").attr("tabname")
		                		if(cid == target.name) {
		                			$(".c_tab li").eq(0).find("em").click();
		                		}
		                     }
		                 }
		               
		             ],[
		                {
		                     text: "关闭左侧标签",
		                     func: function(){
		                    	 $(this).prevAll().remove();
		                    	 var cid = $(this).parents("li").attr("tabname")
		                 		if(cid == target.name) {
		                 			$(".c_tab li").eq(0).find("em").click();
		                 		}
		                     }
		                 },
		                 {
		                     text: "关闭右侧标签",
		                     func: function(){
		                    	 $(this).nextAll().remove();
		                    	 var cid = $(this).parents("li").attr("tabname")
		                 		if(cid == target.name) {
		                 			$(".c_tab li").eq(0).find("em").click();
		                 		}
		                     }
		                 }
		                ]];

	$.post("loginController.do?getMenuData",function(res){
		globe = res;
		if(res.success){
			$("#top_tab").empty();
			$("#left_nav").empty();
			var html = '';
			var lefthmtl = '';
		    var list = res.obj;
		    var x = 1;
		    for(var  i =0 ;i <list.length;i++){
		    	var temp = list[i];
		    	var subList = temp.subMenu;
		    	
		    	html += '<li><s class="item'+(i+1)+'"></s><em>'+temp.name+'</em></li>';
		    	lefthmtl += '<ul name='+temp.name+'>';
		    	for(var j = 0; j < subList.length; j++){
		    		lefthmtl += '<li class="clearfix"><a id="'+subList[j].name+'" href="'+subList[j].url+'" target="c_frame"><s class="item'+x+'"></s><em>'+subList[j].name+'</em></a></li>';
		    		x++;
		    	}
		    	lefthmtl+="</ul>";
		    }
		    
		    $("#top_tab").append(html);
		    $("#left_nav").append(lefthmtl);
		    
		}else{
			location.href="loginController.do?login";
		}
		
		$(".top_tab li").eq(0).click();
	/*	
		$(".left_nav li").eq(0).click();
		$(".c_tab li").eq(0).find("em").click();*/
		var $tab = $("<li class='on'  id='firstPage'  tabname='首页'><em>首页</em></li>");
		$(".c_tab ul").append($tab);
		$("iframe[name='c_frame']").attr("src","fileController.do?mainPage");
		/*页面初始化布局*/
		
	/*	$(".sub_table tbody tr:odd").addClass("color");
		$(".pop_table1 tr:even").addClass("color");*/
		
	},"json");
	
	
	$("#top_tab").delegate("li","click",function(){
		
		var $thisEm = $(this).find("em")
		$(this).addClass("on").siblings("li").removeClass("on");
		var text =  $thisEm.text();
		target.name = text;
		var $ul = $("#left_nav").find("ul");
		$ul.each(function(){
			if(text === $(this).attr("name")){
				$(this).addClass("on").siblings("ul").removeClass("on");;
			}
		});
	});
	
	$("#left_nav").delegate("li","click",function(){
			$(".left_nav li").removeClass("on");
			$(this).addClass("on");
			ntext = $(this).text();
			target.name = ntext;
			$(".c_tab li").removeClass("on");
			if($(".c_tab ul li[tabname="+ntext+"]").length<1){
				//$(".c_tab ul").append("<li class='on' tabname="+ntext+"><em>"+ntext+"</em><s></s></li>")
				var $tab = $("<li class='on' tabname="+ntext+"><em>"+ntext+"</em><s></s></li>");
				$(".c_tab ul").append($tab);
				//var data = [{text:"关闭全部标签",func:function(){}}, {text:"关闭左边其他标签",func:function(){}}, {text:"关闭其他标签",func:function(){}}];
				$tab.smartMenu(globemenu);
			}
			else{
				$(".c_tab li").removeClass("on");
				$(".c_tab li[tabname="+ntext+"]").addClass("on");
			}
			windowfit();
	});
	$(".c_tab").delegate("#firstPage","click",function(){
		$(".c_tab li").removeClass("on");
		$(this).addClass("on");
		$("iframe[name='c_frame']").attr("src","fileController.do?mainPage");
	});

	$(".c_tab").delegate("li","hover",function(event){
		  //console.log(event.type);
		  if (event.type == 'mouseenter') {
			     $(this).find("s").show();
		    } else {
		    	 $(this).find("s").hide();
		    }
		
	});
	
	$(".c_tab").delegate("em","click",function(){
		var cid = $(this).parents("li").attr("tabname");
		$(".left_nav li a[id="+cid+"]").click();
		target.name = cid;
		$("iframe[name='c_frame']").attr("src",$(".left_nav li a[id="+cid+"]").attr("href"));
		
	});
	
	$(".c_tab").delegate("s","click",function(){
		$(this).parents("li").remove();
		var cid = $(this).parents("li").attr("tabname")
		if(cid == target.name) {
			$(".c_tab li").eq(0).find("em").click();
		}else{
			$(".c_tab li").eq(0).find("em").click();
		}
		$(".left_nav li").removeClass("on");
	});

});


$(function(){
 
    
    
    var createLayer = function(orig,layerc){
 	   var layer =  $("#"+orig).layer({
 	        target:layerc
 	    });
 	    $("#"+layerc).hover(function(){
 	        $(this).addClass("hover");
 	    },function(){
 	        $(this).removeClass("hover");
 	        layer.closeLayer();
 	    });
 	    $("#"+orig).hover(function(){
 	       layer.showLayer();
 	    },function(){
 	        setTimeout(function(){
 	            //console($("#layer").hasClass("hover"));
 	            if(!$("#"+layerc).hasClass("hover")){
 	                layer.closeLayer();
 	            }
 	        },500);
 	    });
 }
    
    createLayer("kzmbMenu","layout_north_kzmbMenu");
    createLayer("zxMenu","layout_north_zxMenu");
});
