<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="/tags" prefix="date"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>密码编辑</title>
<script type="text/javascript" src="../js/jquery-1.2.6.pack.js"></script>
<style type="text/css">
	html {
		overflow-y: scroll;
	}
</style>
<script type="text/javascript">
	$(document).ready(function() {
		$("#password").bind("keyup",function() {
			var password = $("#password").val();
			if(password.length >= 6) {
				$("#passwordErr").hide(); 
			} else {
				$("#passwordErr").show(); 
			}
		});
		
		$("#newPassword").bind("keyup ",function() {
			var newPassword = $("#newPassword").val();
			//alert(username.length);
			if(newPassword.length >= 6) {
				$("#newPasswordErr").hide(); 
			} else {
				$("#newPasswordErr").show(); 
			}
		});
		$("#confPassword").bind("keyup",function() {
			var confPassword = $("#confPassword").val();
			if(confPassword.length >= 6) {
				$("#confPasswordErr").hide(); 
			} else {
				$("#confPasswordErr").show();
			}
			//var newPassword = $("#newPassword").val();
			//alert(confPassword);
			//if(newPassword == confPassword) {
			//	$("#confPasswordErr2").hide(); 
			//}
		});
		
		$("#confPassword").bind("keyup",function() {
			var confPassword = $("#confPassword").val();
			var newPassword = $("#newPassword").val();
			//alert(confPassword);
			if(newPassword == confPassword) {
				$("#confPasswordErr2").hide(); 
			} else {
				$("#confPasswordErr2").show();
			}
		});
	});

	function editPassword() {
		var password = $("#password").val();
		if(password.length < 6) {
			$("#passwordErr").show();
			return;
		}
		
		var newPassword = $("#newPassword").val();
		if(newPassword.length < 6) {
			$("#newPasswordErr").show();
			return;
		}
		
		var confPassword = $("#confPassword").val();
		if(confPassword.length < 6) {
			$("#confPasswordErr").show();
			return;
		}
		
		if(confPassword != newPassword) {
			$("#confPasswordErr2").show();
			return;
		}
		
		//return;
		document.getElementById("editPassword").submit();
	}
</script>
<style type="text/css">
	body{margin:0; padding:0; font-size:12px; font-family:"宋体"; color:#000000; background-color:#f4f4f4;}
	a {
		text-decoration: none;
	}
	.emStyle{color: red;padding-left: 5px;display:none}
</style>
</head>
<body>
	<h3>&nbsp;&nbsp;密码编辑.</h3>
	<div style="padding-left: 200px;padding-top: 30px">
		<form method="post" id="editPassword" action="editPassword.do">
			<input type="hidden" name="id" value="${user.id}"> 
			<table>
				<c:if test="${!empty error}">
					<tr height="30px">
						<td>&nbsp;&nbsp;</td>
						<td><font color="red">${error}</font></td>
					</tr>
			    </c:if>
			    <tr height="30px">
					<td><em style="color: red;padding-right: 5px;">*</em>旧 密 码：</td>
					<td><input type="text" name="password" id="password" value="${password}"/><em id="passwordErr" class="emStyle">最小长度为6！</em></td>
				</tr>
				<tr height="30px">
					<td><em style="color: red;padding-right: 5px;">*</em>新 密 码：</td>
					<td><input type="text" name="newPassword" id="newPassword" value="${newPassword}"/><em id="newPasswordErr" class="emStyle">最小长度为6！</em></td>
				</tr>
				<tr height="30px">
					<td><em style="color: red;padding-right: 5px;">*</em>密码确认：</td>
					<td>
						<input type="text" name="confPassword" id="confPassword"/>
						<em  id="confPasswordErr" class="emStyle">最小长度为6！</em>
						<em  id="confPasswordErr2" class="emStyle">确认密码不一致！</em>
					</td>
				</tr>
			</table>
		</form>
		<table width="250px">
			<tr height="30px">
				<td align="center">
					<br/>
					<input type="button" onclick="javascript:editPassword()" value="提  交" />
					<input type="button" onClick="document.getElementById('editPassword').reset();" value="重  置" />
					<!-- input type="button" onClick="history.go(-1);" value="返  回" / -->
				</td>
			</tr>
		</table>
	</div>
	<br>
</body>
</html>