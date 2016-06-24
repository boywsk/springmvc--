<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="/tags" prefix="date"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>用户编辑</title>
<script type="text/javascript" src="../js/jquery-1.2.6.pack.js"></script>
<style type="text/css">
	html {
		overflow-y: scroll;
	}
</style>
<script type="text/javascript">
	$(document).ready(function() {
		$("#userName").bind("keypress ",function(){
			var username = $("#userName").val();
			//alert(username.length);
			if(username.length >= 0) {
				$("#userNameErr").hide(); 
			}
		});
		$("#name").bind("keypress",function(){
			var name = $("#name").val();
			if(name.length >= 0) {
				$("#nameErr").hide(); 
			}
		});
		$("#password").bind("keypress",function(){
			var password = $("#password").val();
			if(password.length >= 0) {
				$("#passwordErr").hide(); 
			}
		});
	});

	function addUser() {
		var username = $("#userName").val();
		if(username.length <= 0) {
			$("#userNameErr").show();
			return;
		}
		
		var nickName = $("#nickName").val();
		if(nickName.length <= 0) {
			$("#nameErr").show();
			return;
		}
		
		var password = $("#password").val();
		if(password.length < 6) {
			$("#passwordErr").show();
			return;
		}
		
		var phoneNumber = $("#phoneNumber").val();
		if(phoneNumber.length != 11) {
			$("#phoneErr").show();
			return;
		}
		//return;
		document.getElementById("addUserForm").submit();
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
	<h3>&nbsp;&nbsp;用户编辑.</h3>
	<div style="padding-left: 200px;padding-top: 30px">
		<form method="post" id="addUserForm" action="addUser.do">
			<table>
				<c:if test="${!empty error}">
					<tr height="30px">
						<td>&nbsp;&nbsp;</td>
						<td><font color="red">${error}</font></td>
					</tr>
			    </c:if>
			    <tr height="30px">
					<td><em style="color: red;padding-right: 5px;">*</em>用户名：</td>
					<td><input type="text" <c:if test="${param.userName != null }">readonly="readonly"</c:if> name="userName" id="userName" value="${user.userName}"/><em id="userNameErr" class="emStyle">不能为空！</em></td>
				</tr>
				<tr height="30px">
					<td><em style="color: red;padding-right: 5px;">*</em>姓&nbsp;&nbsp;名：</td>
					<td><input type="text" name="nickName" id="nickName" value="${user.nickName}"/><em id="nameErr" class="emStyle">不能为空！</em></td>
				</tr>
				<tr height="30px">
					<td><em style="color: red;padding-right: 5px;">*</em>手机号：</td>
					<td><input type="text" name="phoneNumber" id="phoneNumber" value="${user.phoneNumber}"/><em id="phoneErr" class="emStyle">请输入合法手机号！</em></td>
				</tr>
				<tr height="30px">
					<td><em style="color: red;padding-right: 5px;">*</em>密&nbsp;&nbsp;码：</td>
					<td><input type="text" name="password" id="password" value="${user.password}"/><em  id="passwordErr" class="emStyle">最小长度为6！</em></td>
				</tr>
			</table>
		</form>
		<table width="250px">
			<tr height="30px">
				<td align="center">
					<br/>
					<input type="button" onclick="javascript:addUser()" value="提  交" />
					<input type="button" onClick="document.getElementById('addUserForm').reset();" value="重  置" />
					<input type="button" onClick="history.go(-1);" value="返  回" />
				</td>
			</tr>
		</table>
	</div>
	<br>
</body>
</html>