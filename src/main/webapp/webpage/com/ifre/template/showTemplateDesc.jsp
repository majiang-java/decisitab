<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
 <%@include file="/context/mytags.jsp"%>
 <!DOCTYPE html>
<html lang="zh-cn">
  <head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Bootstrap 101 Template</title>

    <!-- Bootstrap -->
    <link href="plug-in/bootstrap-3.3.5-dist/css/bootstrap.min.css" rel="stylesheet">
     <link href="plug-in/bootstrap-3.3.5-dist/css/bootstrap-theme.min.css" rel="stylesheet">
 	<link href="plug-in/bootstrap-3.3.5-dist/css/docs.min.css" rel="stylesheet">
    <!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
      <script src="http://cdn.bootcss.com/html5shiv/3.7.2/html5shiv.min.js"></script>
      <script src="http://cdn.bootcss.com/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->
    
    <style>
    	body {
       font-family:  Microsoft Yahei !important;
       }
       .bs-callout-info, .bs-callout-info p ,.bs-callout-info title,.bs-callout-info *{
        background:#efefef !important;
       }
    </style>
  </head>
  <body>
    <div class="container bs-docs-container" id="top">
    	<div class="row" id="have">
    		<div class="col-md-9" role="main"  id="mainnav">
    			
    		</div>
    		<div class="col-md-3" style="height:100%">
    			<div class="bs-docs-sidebar hidden-print hidden-xs hidden-sm affix-top"  role="complementary">
    				<ul class="nav bs-docs-sidenav" id="leftnav">
    				 
    				</ul>
    				<a class="back-to-top" href="#top">返回顶部</a>
    			</div>
    		</div>
    		
    	
    	</div>
    	<div class="row" id="unhave" style="display:none">
    		
    	</div>
    
    </div>

    <!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
    <script src="http://cdn.bootcss.com/jquery/1.11.1/jquery.min.js"></script>
    <!-- Include all compiled plugins (below), or include individual files as needed -->
    <script src="plug-in/bootstrap-3.3.5-dist/js/bootstrap.min.js"></script>
    <script src="js/sticky-kit.min.js"></script>
    <script type="text/javascript">
    	var data = ${data};
    	$(function(){
    		$("#have .col-md-3").stick_in_parent();
    		if(data){
    			var $leftnav = $("#leftnav"),$mainnav = $("#mainnav");
    			$leftnav.empty();
    			$mainnav.empty();
    			$.each(data,function(i,n){
    				
    				if(n.acitve){
    					$leftnav.append("<li data-id='"+n.id+"' class='active'><a href='#"+n.id+"'>"+n.typename+"</a></li>");
    				}else{
    					$leftnav.append("<li data-id='"+n.id+"'><a href='#"+n.id+"'>"+n.typename+"</a></li>");
    				}
    				var $select = $("<div class='bs-docs-section'></div>");
    				var $h1 = $("<h1 id="+n.id+" class='page-header'>"+n.typename+"</h1>");
    				$select.append($h1);
    				$select.append("<div class='bs-callout bs-callout-info'>"+n.remark+"</div>");
    				$mainnav.append($select);
    			});
    		}else{
    			$("#hava").hide();
    			$("#unhave").show();
    		}
    		
    		
    		$("#leftnav").on("click","li",function(){
    			var $this = $(this);
    			$this.siblings().removeClass("active");
    			$this.addClass("active");
    			$("#typeid",self.parent.frames[1].document).val($this.data("id"));
    			
    		});
    	});
    	
    </script>
  </body>
</html>