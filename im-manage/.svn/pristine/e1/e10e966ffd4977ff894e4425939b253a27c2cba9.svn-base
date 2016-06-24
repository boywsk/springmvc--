<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="/tags" prefix="date"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>管理后台</title>
<script type="text/javascript" src="../js/jquery-1.2.6.pack.js"></script>
<style>
html, body
{
    height: 100%;
}
body
{
    font: 12px 'Lucida Sans Unicode', 'Trebuchet MS', Arial, Helvetica;    
    margin: 0;
    background-color: #d9dee2;
    background-image: -webkit-gradient(linear, left top, left bottom, from(#ebeef2), to(#d9dee2));
    background-image: -webkit-linear-gradient(top, #ebeef2, #d9dee2);
    background-image: -moz-linear-gradient(top, #ebeef2, #d9dee2);
    background-image: -ms-linear-gradient(top, #ebeef2, #d9dee2);
    background-image: -o-linear-gradient(top, #ebeef2, #d9dee2);
    background-image: linear-gradient(top, #ebeef2, #d9dee2);    
}

/*--------------------*/

#login
{
    background-color: #fff;
    background-image: -webkit-gradient(linear, left top, left bottom, from(#fff), to(#eee));
    background-image: -webkit-linear-gradient(top, #fff, #eee);
    background-image: -moz-linear-gradient(top, #fff, #eee);
    background-image: -ms-linear-gradient(top, #fff, #eee);
    background-image: -o-linear-gradient(top, #fff, #eee);
    background-image: linear-gradient(top, #fff, #eee);  
    height: 240px;
    width: 400px;
    margin: -150px 0 0 -230px;
    padding: 30px;
    position: absolute;
    top: 50%;
    left: 50%;
    z-index: 0;
    -moz-border-radius: 3px;
    -webkit-border-radius: 3px;
    border-radius: 3px;  
    -webkit-box-shadow:
          0 0 2px rgba(0, 0, 0, 0.2),
          0 1px 1px rgba(0, 0, 0, .2),
          0 3px 0 #fff,
          0 4px 0 rgba(0, 0, 0, .2),
          0 6px 0 #fff,  
          0 7px 0 rgba(0, 0, 0, .2);
    -moz-box-shadow:
          0 0 2px rgba(0, 0, 0, 0.2),  
          1px 1px   0 rgba(0,   0,   0,   .1),
          3px 3px   0 rgba(255, 255, 255, 1),
          4px 4px   0 rgba(0,   0,   0,   .1),
          6px 6px   0 rgba(255, 255, 255, 1),  
          7px 7px   0 rgba(0,   0,   0,   .1);
    box-shadow:
          0 0 2px rgba(0, 0, 0, 0.2),  
          0 1px 1px rgba(0, 0, 0, .2),
          0 3px 0 #fff,
          0 4px 0 rgba(0, 0, 0, .2),
          0 6px 0 #fff,  
          0 7px 0 rgba(0, 0, 0, .2);
}

#login:before
{
    content: '';
    position: absolute;
    z-index: -1;
    border: 1px dashed #C0D7EA;
    top: 5px;
    bottom: 5px;
    left: 5px;
    right: 5px;
    -moz-box-shadow: 0 0 0 1px #fff;
    -webkit-box-shadow: 0 0 0 1px #fff;
    box-shadow: 0 0 0 1px #fff;
}

/*--------------------*/

h1
{
    /*text-shadow: 0 1px 0 rgba(255, 255, 255, .7), 0px 2px 0 rgba(0, 0, 0, .5);*/
    text-transform: uppercase;
    text-align: center;
    color: #478FCA;
    margin: 0 0 30px 0;
    letter-spacing: 4px;
    font: normal 26px/1 Verdana, Helvetica;
    position: relative;
}

h1:after, h1:before
{
    background-color: #67AB3F;
    content: "";
    height: 1px;
    position: absolute;
    top: 15px;
    width: 120px;   
}

h1:after
{ 
    background-image: -webkit-gradient(linear, left top, right top, from(#67AB3F), to(#67AB3F));
    background-image: -webkit-linear-gradient(left, #67AB3F, #67AB3F);
    background-image: -moz-linear-gradient(left, #67AB3F, #67AB3F);
    background-image: -ms-linear-gradient(left, #67AB3F, #67AB3F);
    background-image: -o-linear-gradient(left, #67AB3F, #67AB3F);
    background-image: linear-gradient(left, #67AB3F, #67AB3F);      
    right: 0;
}

h1:before
{
    background-image: -webkit-gradient(linear, right top, left top, from(#67AB3F), to(#67AB3F));
    background-image: -webkit-linear-gradient(right, #67AB3F, #67AB3F);
    background-image: -moz-linear-gradient(right, #67AB3F, #67AB3F);
    background-image: -ms-linear-gradient(right, #67AB3F, #67AB3F);
    background-image: -o-linear-gradient(right, #67AB3F, #67AB3F);
    background-image: linear-gradient(right, #67AB3F, #67AB3F);
    left: 0;
}

/*--------------------*/


/*--------------------*/

#inputs input
{
    background: #f1f1f1 url(../images/login-sprite.png) no-repeat;
    padding: 15px 15px 15px 30px;
    margin: 0 0 10px 0;
    width: 353px; /* 353 + 2 + 45 = 400 */
    border: 1px solid #ccc;
    -moz-border-radius: 5px;
    -webkit-border-radius: 5px;
    border-radius: 5px;
    -moz-box-shadow: 0 1px 1px #ccc inset, 0 1px 0 #fff;
    -webkit-box-shadow: 0 1px 1px #ccc inset, 0 1px 0 #fff;
    box-shadow: 0 1px 1px #ccc inset, 0 1px 0 #fff;
}

#username
{
    background-position: 5px -2px !important;
}

#password
{
    background-position: 5px -52px !important;
}

#inputs input:focus
{
    background-color: #fff;
    border-color: #e8c291;
    outline: none;
    -moz-box-shadow: 0 0 0 1px #e8c291 inset;
    -webkit-box-shadow: 0 0 0 1px #e8c291 inset;
    box-shadow: 0 0 0 1px #e8c291 inset;
}

/*--------------------*/
#actions
{
    margin: 25px 0 0 0;
}

#submit
{		
    background-color: #2283C5;
    background-image: -webkit-gradient(linear, left top, left bottom, from(#2283C5), to(#2283C5));
    background-image: -webkit-linear-gradient(top, #2283C5, #2283C5);
    background-image: -moz-linear-gradient(top, #2283C5, #2283C5);
    background-image: -ms-linear-gradient(top, #2283C5, #2283C5);
    background-image: -o-linear-gradient(top, #2283C5, #2283C5);
    background-image: linear-gradient(top, #2283C5, #2283C5);
    
    -moz-border-radius: 3px;
    -webkit-border-radius: 3px;
    border-radius: 3px;
    
    text-shadow: 0 1px 0 rgba(255,255,255,0.5);
    
     -moz-box-shadow: 0 0 1px rgba(0, 0, 0, 0.3), 0 1px 0 rgba(255, 255, 255, 0.3) inset;
     -webkit-box-shadow: 0 0 1px rgba(0, 0, 0, 0.3), 0 1px 0 rgba(255, 255, 255, 0.3) inset;
     box-shadow: 0 0 1px rgba(0, 0, 0, 0.3), 0 1px 0 rgba(255, 255, 255, 0.3) inset;    
    
    border-width: 1px;
    border-style: solid;
    border-color: #045E9F #045E9F #045E9F #045E9F;

    float: left;
    height: 35px;
    padding: 0;
    width: 400px;
    cursor: pointer;
    font: bold 15px Arial, Helvetica;
    color: #FFFFFF;
}

#submit:hover,#submit:focus
{		
    background-color: #045E9F;
    background-image: -webkit-gradient(linear, left top, left bottom, from(#045E9F), to(#045E9F));
    background-image: -webkit-linear-gradient(top, #045E9F, #045E9F);
    background-image: -moz-linear-gradient(top, #045E9F, #045E9F);
    background-image: -ms-linear-gradient(top, #045E9F, #045E9F);
    background-image: -o-linear-gradient(top, #045E9F, #045E9F);
    background-image: linear-gradient(top, #045E9F, #045E9F);
}	

#submit:active
{		
    outline: none;
   
     -moz-box-shadow: 0 1px 4px rgba(0, 0, 0, 0.5) inset;
     -webkit-box-shadow: 0 1px 4px rgba(0, 0, 0, 0.5) inset;
     box-shadow: 0 1px 4px rgba(0, 0, 0, 0.5) inset;		
}

#submit::-moz-focus-inner
{
  border: none;
}

#actions a
{
    color: #3151A2;    
    float: right;
    line-height: 35px;
    margin-left: 10px;
}

/*--------------------*/

#back
{
    display: block;
    text-align: center;
    position: relative;
    top: 60px;
    color: #999;
}
</style>

<script type="text/javascript">
	$(document).ready(function() {
		$("#username").bind("keypress",function(){
			var username = $("#username").val();
			if(username.length > 0) {
				$("#nullUserName").hide(); 
			}
		});
		$("#password").bind("keypress",function(){
			var password = $("#password").val();
			if(password.length > 0) {
				$("#nullPassword").hide(); 
			}
		});
	});
	
	function onSubmit(){
		//alert('sss');
		var username = $("#username").val();
		if(username.length <= 0) {
			$("#nullUserName").show();
			return;
		}
		var password = $("#password").val();
		if(password.length <= 0) {
			$("#nullPassword").show();
			return;
		}		
		
		//return;
		
		document.getElementById("logonForm").submit();
	}
</script>
</head>

<body onkeydown="if (event.keyCode==13) {document.getElementById('submit').click();}">
<div id="login">
	<h1>管理后台</h1>
	<form method="post" id="logonForm" action="logon.do">
	    <div id="inputs">
	        <input id="username" name="username" type="text"/>   
	        <input id="password" name="password" type="password"/>
	    </div>
	</form>
	<div id="actions">
		<input type="button" onclick="onSubmit()" id="submit" value="登  录"/>
	</div>
</div>

<div style="text-align:center;clear:both;">
	<c:if test="${!empty error}">
    	<font color="red" size="5" >【${error}】</font>
    </c:if>
	<br />
	<span id="nullUserName" style="color: red;display:none">登录名不能为空！</span>
	<br/>
	<span id="nullPassword" style="color: red;display:none;">密码不能为空！</span>
</div>
</body>
</html>
