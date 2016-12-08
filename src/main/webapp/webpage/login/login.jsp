<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="org.jeecgframework.core.util.SysThemesUtil,org.jeecgframework.core.enums.SysThemesEnum"%> 
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>IFRE</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="./css/style.css" rel="stylesheet" />
<link href="plug-in/skins/default.css" rel="stylesheet" />
<style>
#alertMessage{
	position: absolute;
	left: 46.5%;
    top: 53.5%;
       width: 106px;
    /* padding: 20px; */
    margin: 0px;
    padding: 10px;
    height: 11px;
    /* vertical-align: top; */
    line-height: 11px;
    text-align: center;
        background-image: linear-gradient(135deg, rgba(255, 255, 255, .05) 25%, transparent 25%,
 transparent 50%, rgba(255, 255, 255, .05) 50%, rgba(255, 255, 255, .05) 75%,
 transparent 75%, transparent);
 	    box-shadow: inset 0 -1px 0 rgba(255,255,255,.4);
    font-size: 13px;
    border: 1px solid!important;
    -webkit-animation: animate-bg 5s linear infinite;
    background-size: 40px 40px;
    text-transform: uppercase;
    bottom: 35px;
    z-index: 100001;
    cursor: pointer;
    display:none;
}

.error {
    border-color: #c43d3d!important;
    background-color: #fc4a48;
    color: #5A0000 !important;
    text-shadow: 1px 1px 1px #E64040;
}
 .text_success {
    font-size: 14px;
    text-align: center;
    margin: 0px 0 35px 0;
    line-height: 25px;
    position: absolute;
    left: 50%;
    width: 200px;
    top: 50%;
    z-index: 50;
    margin-left: -100px;
    text-transform: uppercase;
    padding: 20px 0px;
    margin-top: -100px;
    display: none;
}
#overlay {
	width:100%;
	height:100%;
	position:fixed;
	top:0;
	left:0;
	background-color:#111;
	/*	opacity:0.1;
	filter:alpha(opacity=10);*/
	z-index:1000;
	display: none;
}
#preloader {
	background: #000000 url(../images/preloader.gif) no-repeat 12px 10px;
	font-size: 11px;
	height: 20px;
	left: 50%;
	line-height: 20px;
	margin: -20px 0 0 -45px;
	padding: 10px;
	position: fixed;
	text-align: left;
	text-indent: 36px;
	top: 50%;
	width: 9em;
	z-index: 1209;
	opacity:0.8;
	filter:alpha(opacity=80);
	-moz-border-radius: 8px;
	-webkit-border-radius: 8px;
	border-radius: 8px;
	color: #FFF;
	text-shadow:none;
	display: none;
}
</style>
</head>

<body style="overflow:hidden;">
  <div id="alertMessage"></div>
  <div id="successLogin"></div>
  <div class="text_success"><img src="plug-in/login/images/loader_green.gif" alt="Please wait" /> <span><t:mutiLang langKey="common.login.success.wait"/></span></div>
<img class="loginbg" src="css/img/login_bg.png" />
<div class="loginbox" id="login">
	<h2 class="cn">
		<em><img src="css/img/login_logo.png" /></em>
		<strong>智能决策</strong>
	</h2>
	<form name="formLogin" id="formLogin" action="loginController.do?login" check="loginController.do?checkuser" method="post">
	<div class="loginform">
		<dl><input class="logininput" id="userName" name="userName" type="text" nullmsg="请输入用户名" placeholder="账号" /><s class="user_icon"></s></dl>
		<dl><input class="logininput" id="password" name="password" type="password" nullmsg="请输入密码" placeholder="密码" /><s class="password_icon"></s></dl>
		<dl>
		    <input class="logininput_code" id="randCode" name="randCode" nullmsg="请输入验证码" type="text" placeholder="验证码" />
			    <img id="randCodeImage" src="randCodeImage" />
			  <!--  <input class="randCode" name="randCode" type="hidden" id="randCode" title="" value="" nullmsg="" /> -->
		</dl>
		<dl>
			<input type="checkbox" id="on_off" name="remember" checked="ture" class="on_off_checkbox" value="0" />
            <span class="f_help"><t:mutiLang langKey="common.remember.user"/></span>
		<!-- <input type="checkbox" /> 记住我的密码</dl> -->
		<dl>
			<a class="login_btn" id="but_login" href="#none">登录系统</a>
		</dl>
	</div>
	</form>
</div>
  <div hidden="true"><t:dictSelect id="langCode" field="langCode" typeGroupCode="lang" hasLabel="false" defaultVal="zh-cn" type="hidden"></t:dictSelect></div>                        


 <script type="text/javascript" src="plug-in/jquery/jquery-1.8.3.min.js"></script>
 <script type="text/javascript" src="plug-in/jquery/jquery.cookie.js"></script>
 <script type="text/javascript" src="plug-in/login/js/jquery-jrumble.js"></script>
 <script type="text/javascript" src="plug-in/login/js/jquery.tipsy.js"></script>
 <script type="text/javascript" src="plug-in/login/js/iphone.check.js"></script>
 <script type="text/javascript" src="plug-in/lhgDialog/lhgdialog.min.js"></script>
 <script type="text/javascript" src="plug-in/login/js/login.js"></script>
 <script src="js/fun.js"></script>
<script type="text/javascript">
$(function(){
	$(".loginform dl").click(function(){
		$(this).addClass("on").siblings("dl").removeClass("on");
	})
})
</script>
</body>
</html>