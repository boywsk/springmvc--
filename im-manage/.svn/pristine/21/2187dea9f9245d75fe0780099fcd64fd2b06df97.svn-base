<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="/tags" prefix="date"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>菜单编辑</title>
<script type="text/javascript" src="../js/jquery-1.2.6.pack.js"></script>
<style type="text/css">
	html {
		overflow-y: scroll;
	}
</style>
<script type="text/javascript">
	$(document).ready(function() {
		$("#name").bind("keyup",function(){
			var name = $("#name").val();
			if(name.length >= 0) {
				$("#nameErr").hide(); 
			}
		});
	});

	function addUser() {
		var name = $("#name").val();
		if(name.length <= 0) {
			$("#nameErr").show();
			return;
		}
		
		//return;
		document.getElementById("addMenuForm").submit();
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
	<h3>&nbsp;&nbsp;菜单编辑.</h3>
	<div style="padding-left: 200px;padding-top: 30px">
		<form method="post" id="addMenuForm" action="addMenu.do">
			<input type="hidden" name="id" value="${menu.id}"> 
			<input type="hidden" name="pid" value="${param.pid}"> 
			<table>
				<c:if test="${!empty error}">
					<tr height="30px">
						<td>&nbsp;&nbsp;</td>
						<td><font color="red">${error}</font></td>
					</tr>
			    </c:if>
			    <c:if test="${pMenu != null }">
			    <tr height="30px">
					<td>上层菜单：</td>
					<td><input type="text" readonly="readonly" name="userName" id="userName" value="${pMenu.name}"/></td>
				</tr>
				</c:if>
				<tr height="30px">
					<td><em style="color: red;padding-right: 5px;">*</em>名称：</td>
					<td><input type="text" name="name" id="name" value="${menu.name}"/><em id="nameErr" class="emStyle">不能为空！</em></td>
				</tr>
				<tr height="30px">
					<td>地址：</td>
					<td><input type="text" name="url" id="url" value="${menu.url}"/></td>
				</tr>
			</table>
		</form>
		<table width="250px">
			<tr height="30px">
				<td align="center">
					<br/>
					<input type="button" onclick="javascript:addUser()" value="提  交" />
					<input type="button" onClick="document.getElementById('addMenuForm').reset();" value="重  置" />
					<input type="button" onClick="history.go(-1);" value="返  回" />
				</td>
			</tr>
		</table>
	</div>
	<br>
</body>
</html>