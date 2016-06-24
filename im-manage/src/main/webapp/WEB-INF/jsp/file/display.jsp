<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Insert title here</title>
</head>
<script type="text/javascript"> 
function filePath(adres){
	document.getElementById('filePath').src = adres;
}
</script>
<body>
<input style="width: 60px;height: 28px" type="button" onClick="history.go(-1);" value="返  回" />
<br><hr>
	<c:if test="${type=='avatar'}">
		<input style="width: 60px;height: 28px" type="button" value="小头像" onclick="filePath('${smallAvatarPath}')">
		<input style="width: 60px;height: 28px" type="button" value="大头像" onclick="filePath('${largeAvatarPath}')">
		<br>
		<img id="filePath" src=${smallAvatarPath}>
	</c:if>
	<c:if test="${type=='image'}">
		<input style="width: 60px;height: 28px" type="button" value="小 图" onclick="filePath('${smallimagePath}')">
		<input style="width: 60px;height: 28px" type="button" value="中 图" onclick="filePath('${middleimagePath}')">
		<input style="width: 60px;height: 28px" type="button" value="大 图" onclick="filePath('${originimagePath}')">
		<br>
    	<img id="filePath" src=${smallimagePath}>
	</c:if>
	<c:if test="${type=='vedio'}">
	    	APPID列表：<input type="text" name = "appId" id = "appId" />
	</c:if>
	<c:if test="${type=='voice'}">
	    	APPID列表：<input type="text" name = "appId" id = "appId" />
	</c:if>
</body>
</html>