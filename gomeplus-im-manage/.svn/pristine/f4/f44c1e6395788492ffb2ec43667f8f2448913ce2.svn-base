<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="/tags" prefix="date"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>菜单列表</title>
<script type="text/javascript" src="../js/jquery-1.2.6.pack.js"></script>
<style type="text/css">
	body{margin:0; padding:0; font-size:12px; font-family:"宋体"; color:#000000; background-color:#f4f4f4;}
	a {
		text-decoration: none;
	}
	table {
		border: 1px;
		border-style: solid;
		border-color: gray;
		
		#font-family: "微软雅黑";
		text-align: center;
		
		margin: 0px;
		padding: 0px;
		border-collapse: collapse;
		padding: 0px;
	}
	tr:first-child {
		background-color: #AEC7E1;
	}
	tr {
		cursor: default;
	}
	td {
		#width: 100px;
		height:25px;
		border: 1px;
		border-style: solid;
		border-color: gray;
	}
	
</style>
<script type="text/javascript">
	function orderUp(id, pid) {
		//alert($("#" + id).prev("tr").find("td:first").html());
		
		var nearId = $("#" + id).prev("tr").find("td:first").html();
		var uri = "menuOrder.do?id=" + id + "&nearId=" + nearId + "&pid=" + pid;
		//alert(uri);
		
		location.href = uri;
		//alert($("#" + id).next("tr").find("td:first").html());
		
		
		//alert($("#" + id).parent().parent().next("tr").html(1));
		 //var txt = $("#" + id).next("tr").eq(0).html();
		
		 //   alert('数据:'+txt);
		//alert(id); .parent().next().attr("id")
		//var tr = $("#" + id).parent("tr").prev("tr").html();
		//alert(tr.html());
	}
	
	function orderDown(id, pid) {
		var nearId = $("#" + id).next("tr").find("td:first").html();
		var uri = "menuOrder.do?id=" + id + "&nearId=" + nearId + "&pid=" + pid;
		//alert(uri);
		
		location.href = uri;
	}
</script>
</head>
<body>
	<h3>菜单列表.</h3>
	<div>
		<input type="button" onclick='location.href="preAddMenu.do?pid=${param.pid}"' value="添加菜单" />
		<c:if test="${param.pid > 0 }">
			<input type="button" onclick="location.href='listMenu.do'" value="返  回" />
		</c:if>
	</div>
	<br>
	<div>
		<table>
			<tr>
			<td width="5%">id</td><td width="8%">名称</td><td width="15%">url</td><td width="8%">操作</td>
			</tr>
			<c:forEach var="member" items="${menus}" varStatus="status">
			<tr id="${member.id}" <c:if test="${status.index%2!=0 }"> style="background-color: #E5DBE2;"</c:if>>
				<td><c:out value="${member.id}" /></td>
				<td><c:out value="${member.name}" /></td>
				<td align="left"><c:out value="${member.url}"/></td>
				<td>
					<a href="preAddMenu.do?id=${member.id}&pid=${member.pid}" class="del">修改</a>
					<a href="listMenu.do?pid=${member.id}" class="del">子菜单</a>
					
					<c:if test="${!status.first}">
						<a href="#"  onclick="javascript:orderUp(${member.id},${member.pid})" class="del">向上</a>
					</c:if>
					<c:if test="${status.first}">
						向上
					</c:if>
					
					<c:if test="${!status.last}">
						<a href="#"  onclick="javascript:orderDown(${member.id},${member.pid})" class="del">向下</a>
					</c:if>
					<c:if test="${status.last}">
						向下
					</c:if>
					
					<a href="delMenu.do?id=${member.id}&pid=${member.pid}" class="del">删除</a>
				</td>
			</tr>
			</c:forEach>
		</table>
	</div>
	<br>
	<div>
		<div>
			
		</div>
	</div>
</body>
</html>