<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title></title>
    <link href="plug-in/jqmetro/Style/Interacao.css" rel="stylesheet" type="text/css" />
    <link href="plug-in/jqmetro/Style/jq-metro.css" rel="stylesheet" type="text/css" />
    <script src="http://code.jquery.com/jquery-1.7.2.min.js" type="text/javascript"></script>
    <script src="plug-in/jqmetro/Script/jquery.metro-btn.js" type="text/javascript"></script>
<!--     <link href="plug-in/smartMenu/smartMenu.css" rel="stylesheet" />
	<script src="plug-in/smartMenu/jquery-smartMenu-min.js"></script> -->
    <script type="text/javascript">
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

        $(document).ready(function () {
   
			$("#metroaqui_novo").AddMetroDoubleButton('bt6', 'metro-azul', 'plug-in/jqmetro/Style/Imagem/admin.png', '模型管理', 'commonTarget("模型管理","brmsRuleTableController.do?list&prodType=1",this)');
		/* 	$("#metroaqui_novo").AddMetroDoubleWithTrailerWithBG('bt6', 'plug-in/jqmetro/Style/Imagem/fundo_metro.png', 'Button with Status Text', 'alert("Text");', 'metro-azul'); */
			$("#metroaqui_novo").AddMetroSimpleButton('bt1', 'metro-color1', 'plug-in/jqmetro/Style/Imagem/carta.png', '模型测试', 'commonTarget("模型测试","ruleTesterController.do?showTester&prodType=1",this)');
            $("#metroaqui_novo").AddMetroSimpleButton('bt2', 'metro-color5', 'plug-in/jqmetro/Style/Imagem/carta.png', '模型发布', 'commonTarget("模型发布","ruleProdController.do?jarlist&prodType=1",this)');
            $("#metroaqui_novo").AddMetroDoubleButton('bt4', 'metro-color7', 'plug-in/jqmetro/Style/Imagem/carta.png', '模型变量', 'commonTarget("模型变量","modelVarController.do?typeGroupList&prodType=1",this)');
            $("#metroaqui_novo").AddMetroSimpleButton('bt5', 'metro-laranja', 'plug-in/jqmetro/Style/Imagem/carta.png', '规则管理', 'commonTarget("规则管理","brmsRuleTableController.do?list&prodType=2",this)');
			$("#metroaqui_novo").AddMetroDoubleButton('bt1', 'metro-verde', 'plug-in/jqmetro/Style/Imagem/admin.png', '规则测试', 'commonTarget("规则测试","ruleTesterController.do?showTester&prodType=2",this)');
            $("#metroaqui_novo").AddMetroSimpleButton('bt2', 'metro-color1', 'plug-in/jqmetro/Style/Imagem/carta.png', '规则发布', 'commonTarget("规则发布","ruleProdController.do?jarlist&prodType=2",this)');
			$("#metroaqui_novo").AddMetroDoubleButton('bt1', 'metro-azul', 'plug-in/jqmetro/Style/Imagem/carta.png', '规则变量', 'commonTarget("规则变量","modelVarController.do?typeGroupList&prodType=2",this)');
			
        });
        
        function commonTarget(text,url,obj){
        	///debugger;
			if($(".c_tab ul li[tabname="+text+"]",parent.document).length<1){
				$(".c_tab li",parent.document).removeClass("on");
				var $tab = window.parent.$("<li class='on' tabname="+text+"><em>"+text+"</em><s></s></li>");
				$(".c_tab ul",parent.document).append($tab);
				$tab.smartMenu(globemenu);
			}
			else{
				$(".c_tab li",parent.document).removeClass("on");
				$(".c_tab li[tabname="+text+"]",parent.document).addClass("on");
			}
			$("iframe[name='c_frame']",parent.document).attr("src",url);
        
        }
    </script>
</head>
<body>
<!--     <div class="cabecalho">
        <img src="Style/Imagem/logo.png" alt="Interação Sistemas" height="150" />
    </div>
 -->
    <div id="metroaqui_novo" class="metro-panel"></div>
    <div id="metroaqui" class="metro-panel"></div>
	

</body>
</html>
