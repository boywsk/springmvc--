<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="/tags" prefix="date"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>用户列表</title>
	<script src="../js/jquery-1.4.4.min.js" type="text/javascript"></script>
	<script type="text/javascript" src="../js/jquery-1.2.6.pack.js"></script>
    <link href="../css/ligerui-all.css" rel="stylesheet" type="text/css" />
    <script src="../js/ligerUI/base.js" type="text/javascript"></script>
    <script src="../js/ligerUI/ligerGrid.js" type="text/javascript"></script>
    <script src="../js/ligerUI/ligerDrag.js" type="text/javascript"></script>
    <script src="../js/ligerUI/ligerResizable.js" type="text/javascript"></script>
	<script src="../js/My97DatePicker/WdatePicker.js" type="text/javascript"></script>
</head>
<script type="text/javascript">
	$(function() {
	    $("#maingrid").ligerGrid({
	        columns : [
				{display : '用户id',	name : 'uId',	align : 'left',	width : '20%'},
				{display : 'key',	name : 'key',	align : 'left',	width : '60%'},
				{display : 'value',	name : 'value',	align : 'left',	width : '20%'}
				],
			width : '100%',
            allowHideColumn : false,
            checkbox : false,
            usePager : true,
            rownumbers : false,
            alternatingRow : false,
	        method : "get",
	    });
	});
	function apnsInfoSearch(){
		var appId = $("#appId").val();
		var uId = $("#uId").val();
		if(appId == null){
			alert("APPID不可为空！");
			return false;
		}
		grid = $("#maingrid").ligerGrid({
			columns : [
			{display : '用户id',	name : 'uId',	align : 'left',	width : '20%'},
			{display : 'key',	name : 'key',	align : 'left',	width : '60%'},
			{display : 'value',	name : 'value',	align : 'left',	width : '20%'}
			],
			width : '100%',
            allowHideColumn : false,
            checkbox : false,
            usePager : true,
            rownumbers : false,
            alternatingRow : false,
			url : "getLoginInfo.do?appId="+appId+"&uId="+uId,
	        method : "get",
		});}
	$(document).ready(function() {
    });
</script>
<div>
	&nbsp;&nbsp;&nbsp;&nbsp;
	<%-- <c:if test="${type=='2'}">
        APPID：<select name = "appId" id = "appId">
					<c:forEach items="${appIdList}" var="data">
			             <option value="${data.appId}">${data.appId}</option>
			     	</c:forEach>
				</select>
    </c:if>
    <c:if test="${type=='1'}">
    	APPID列表：<input type="text" name = "appId" id = "appId" />
    </c:if> --%>
    APPID列表：<input type="text" name = "appId" id = "appId" />
    &nbsp;&nbsp;&nbsp;&nbsp;
	用户ID：<input id="uId" name="uId" type="text" class="inputText4">
	&nbsp;&nbsp;&nbsp;&nbsp;
	<input style="width: 60px;height: 28px" type="button" value="查  询" onclick="apnsInfoSearch()">
</div>
<div id="maingrid" style="margin: 0; padding: 0"></div>
<div style="display: none;">
</div>
</body>
</html>