<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html >
<html>
<head>
<t:base type="jquery,easyui,DatePicker,tools,autocomplete"></t:base>
<title><t:mutiLang langKey="jeect.platform"/></title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="./css/style.css" rel="stylesheet" />
<link href="plug-in/smartMenu/smartMenu.css" rel="stylesheet" />
<script src="plug-in/smartMenu/jquery-smartMenu-min.js"></script>
<script src="js/fun.js"></script>
<script src="js/main.js"></script>
<script src="js/simpleLayer.js"></script>
</head>

<body>
     <div id="layout_north_kzmbMenu" style="width: 100px; display: none;">
               <div onclick="openwindow('<t:mutiLang langKey="common.profile"/>','userController.do?userinfo')">
                   <t:mutiLang langKey="common.profile"/>
               </div>
               <div class="menu-sep"></div>
               <div onclick="add('<t:mutiLang langKey="common.change.password"/>','userController.do?changepassword','',550,200)">
                   <t:mutiLang langKey="common.change.password"/>
               </div>
               <div class="menu-sep"></div>
               <div onclick="openwindow('<t:mutiLang langKey="common.ssms.getSysInfos"/>','tSSmsController.do?getSysInfos')">
                   <t:mutiLang langKey="common.ssms.getSysInfos"/>
               </div>
               <div class="menu-sep"></div>
                <div onclick="add('<t:mutiLang langKey="common.change.style"/>','userController.do?changestyle','',550,200)">
                    <t:mutiLang langKey="common.change.style"/>
                </div>
                <div class="menu-sep"></div>
               <div onclick="clearLocalstorage()">
         		 	<t:mutiLang langKey="common.clear.localstorage"/>
     			 </div>
           </div>
           <%-- <div id="layout_north_zxMenu" style="width: 100px; display: none;">
               <div onclick="exit('loginController.do?logout','<t:mutiLang langKey="common.exit.confirm"/>',1);">
                   <t:mutiLang langKey="common.exit"/>
               </div>
           </div> --%>
<div class="top">
	<h1 class="logo"><img src="css/img/logo.png" /><em><%@include file="/webpage/main/head.jsp"%></em></h1>
	<ul class="top_info clearfix">
		<li><t:mutiLang langKey="common.user"/>：${userName }</li>
		<li><t:mutiLang langKey="current.org"/>：${currentOrgName }</li>
		<li><t:mutiLang langKey="common.role"/>：${roleName }</li>
		<li><a href="#none" id="kzmbMenu" class="clearfix"><i class="control_panel"></i><em>控制面板</em><s></s></a></li>
		<li id="top_info"><div onclick="exit('loginController.do?logout','<t:mutiLang langKey="common.exit.confirm"/>',1);" id="zxMenu" class="clearfix"><i class="user_cancel"></i><em>注销</em><s></s></div></li>
	</ul>
	
	<div  class="top_tab" title="<t:mutiLang langKey="common.navegation"/>">
	   <ul id="top_tab">
		<li><s class="item1"></s><em>产品管理</em></li>
		<li><s class="item2"></s><em>决策管理</em></li>
		<li><s class="item3"></s><em>接口管理</em></li>
		<li><s class="item4"></s><em>系统管理</em></li> 
		</ul>
	</div>
	<a class="top_control" href="#none"></a>
</div>
<div class="container clearfix">
	<div class="c_left">
		<a class="left_control" href="#none"></a>
		<div class="leftbox">
			<h3>导航菜单</h3>
			<div class="left_nav" id="left_nav">
				<ul>
					<li class="clearfix"><a id="p1" href="page1.html" target="c_frame"><s class="item1"></s><em>机构管理</em></a></li>
					<li class="clearfix"><a id="p2" href="page2.html" target="c_frame"><s class="item2"></s><em>产品</em></a></li>
					<li class="clearfix"><a id="p3" href="page3.html" target="c_frame"><s class="item3"></s><em>决策产品</em></a></li>
					<li class="clearfix"><a id="p4" href="page4.html" target="c_frame"><s class="item4"></s><em>决策包</em></a></li>
					<li class="clearfix"><a id="p5" href="page5.html" target="c_frame"><s class="item5"></s><em>业务对象</em></a></li>
				</ul>
				<ul>
					<li class="clearfix"><a id="p6" href="page6.html" target="c_frame"><s class="item8"></s><em>决策方案</em></a></li>
					<li class="clearfix"><a id="p7" href="page7.html" target="c_frame"><s class="item9"></s><em>测试验证</em></a></li>
					<li class="clearfix"><a id="p8" href="page8.html" target="c_frame"><s class="item10"></s><em>决策打包</em></a></li>
				</ul>
				<ul>
					<li class="clearfix"><a id="p9" href="page9.html" target="c_frame"><s class="item6"></s><em>渠道来源</em></a></li>
					<li class="clearfix"><a id="p10" href="page10.html" target="c_frame"><s class="item7"></s><em>通讯日志</em></a></li>
				</ul>
				<ul>
					<li class="clearfix"><a href="#none"><s></s><em>未知</em></a></li>
				</ul>
			</div>
		</div>
	</div>
	<div class="c_main">
		<div class="c_tab">
			<ul class="clearfix">
				
			</ul>
		</div>
		<div class="c_con">
			<iframe name="c_frame" class="c_frame" src="" frameborder="0"></iframe>
		</div>
	</div>
	<div class="c_right" style="width: 1px; display: none;">
		<a class="right_control" href="#none"></a>
		<div class="rightbox">
		</div>
	</div>
</div>
<div class="foot"><%@include file="/webpage/main/foot.jsp"%></div>
</body>
</html>

