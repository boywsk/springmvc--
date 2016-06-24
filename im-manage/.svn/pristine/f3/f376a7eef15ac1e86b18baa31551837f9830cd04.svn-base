<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="/tags" prefix="date"%>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>APP信息</title>
    <script type="text/javascript" src="../js/jquery-1.2.6.pack.js"></script>
    <link href="../css/ligerui-all.css" rel="stylesheet" type="text/css" />
    <script src="../js/ligerUI/base.js" type="text/javascript"></script>
    <script src="../js/ligerUI/ligerGrid.js" type="text/javascript"></script>
    <script src="../js/ligerUI/ligerDrag.js" type="text/javascript"></script>
    <script src="../js/ligerUI/ligerResizable.js" type="text/javascript"></script>
    <script type="text/javascript">
	    $(function() {
	        $("#maingrid").ligerGrid({
	            columns : [
					{
					    display : '用户Id',
					    name : 'uId',
					    width : 100,
					    align : 'left'
					},{
	                    display : 'App名称',
	                    name : 'appName',
	                    width : 100,
	                    align : 'left'
	                },{
	                    display : 'AppId',
	                    name : 'appId',
	                    width : 260,
	                    align : 'left'
	                },{
	                    display : 'AppKey',
	                    name : 'appKey',
	                    width : 260,
	                    align : 'left'
	                },{
	                    display : '创建时间',
	                    name : 'createTime',
	                    width : 140,
	                    align : 'left'
	                },{
	                    display : 'App描述',
	                    name : 'appDesc',
	                    width : 300,
	                    type : 'String',
	                    align : 'left'
	                }],
	            allowHideColumn : false,
	            checkbox : false,
	            usePager : true,
	            rownumbers : false,
	            alternatingRow : false,
	            tree : {
	                columnName : 'name'
	            },
	            url : "getAppInfo.do",
	            method : "post",
	
	        });
		});
        $(document).ready(function() {
        });
    </script>
</head>
<body>
<br>
<h3>APP信息管理</h3>
<br>
<div id="maingrid" style="margin: 0; padding: 0"></div>
</body>
</html>