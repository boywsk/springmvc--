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
					    display : '用户名称 ',
					    name : 'userName',
					    width : '10%',
					    align : 'left'
					},{
		                   display : 'App名称',
		                   name : 'appName',
		                   width : '10%',
		                   align : 'left'
		               },{
		                   display : 'AppId',
		                   name : 'appId',
		                   width : '20%',
		                   align : 'left'
		               },{
		                   display : 'AppKey',
		                   name : 'appKey',
		                   width : '20%',
		                   align : 'left'
		               },{
		                   display : '创建时间',
		                   name : 'createTime',
		                   width : '10%',
		                   align : 'left'
		               },{
		                   display : 'App描述',
		                   name : 'appDesc',
		                   width : '20%',
		                   type : 'String',
		                   align : 'left'
		               },{
		                    display: '操作', isAllowHide: false,
		                    width : '10%',
		                    render: function (row)
		                    {
		                        var html = '<a href="../online/preOnlineStatistics.do?appId=' + row.appId + '">统计</a>';
		                        html += '&nbsp;&nbsp;<a href="../app/editAppInfo.do?appId='+row.appId+'&appName='+row.appName+'&appKey='+row.appKey+'&appDesc='+row.appDesc+'">修改</a>'
		                        return html;
		                    }
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
    <script type="text/javascript">
        function searchAppInfo(){
            var url;
            var type = $("#type").val();
            var userName = $("#userName").val();
            var appId = $("#appId").val();
            var appName = $("#appName").val();            
            url = "getAppInfo.do?type='admin'&userName="+userName+"&appId="+appId+"&appName="+appName;
            $("#maingrid").ligerGrid({
	            columns : [
					{
					    display : '用户名称 ',
					    name : 'userName',
					    width : '10%',
					    align : 'left'
					},{
	                    display : 'App名称',
	                    name : 'appName',
	                    width : '10%',
	                    align : 'left'
	                },{
	                    display : 'AppId',
	                    name : 'appId',
	                    width : '20%',
	                    align : 'left'
	                },{
	                    display : 'AppKey',
	                    name : 'appKey',
	                    width : '20%',
	                    align : 'left'
	                },{
	                    display : '创建时间',
	                    name : 'createTime',
	                    width : '10%',
	                    align : 'left'
	                },{
	                    display : 'App描述',
	                    name : 'appDesc',
	                    width : '20%',
	                    type : 'String',
	                    align : 'left'
	                },{
	                    display: '操作', isAllowHide: false,
	                    width : '10%',
	                    render: function (row)
	                    {
	                        var html = '<a href="../online/preOnlineStatistics.do?appId=' + row.appId + '">统计</a>';
	                        html += '&nbsp;&nbsp;<a href="../app/editAppInfo.do?appId='+row.appId+'&appName='+row.appName+'&appKey='+row.appKey+'&appDesc='+row.appDesc+'">修改</a>'
	                        return html;
	                    }
	                }],
                allowHideColumn : false,
                checkbox : false,
                usePager : true,
                rownumbers : false,
                alternatingRow : false,
                tree : {
                    columnName : 'name'
                },
                url : url,
                method : "post",
            });
        }
        $(document).ready(function() {
        });
    </script>
</head>
<body>
<br>
<h3>APP信息列表</h3>
<br>
<div>	
	&nbsp;&nbsp;&nbsp;&nbsp;
    <c:if test="${type=='1'}">
    	<%-- 用户ID：<input type="text" name="userId" id="userId" value = "${param.userId}" size="18"/> --%>
    	用户名称：<input type="text" name="userName" id="userName" value = "${param.userName}" size="18"/>
    </c:if>
    &nbsp;&nbsp;&nbsp;&nbsp;
    APP名称：<input type="text" name="appName" id="appName" value = "${param.appName}" size="18"/>
    &nbsp;&nbsp;&nbsp;&nbsp;
    APPID：<input type="text" name="appId" id="appId" value = "${param.appId}" size="18"/>
    <input type="button" onclick="searchAppInfo()" value="查询" />&nbsp;&nbsp;&nbsp;&nbsp;
    <c:if test="${type=='2'}">
    	<input type="button" onclick='location.href="app.do"' value="添加APP信息" />
    </c:if>	    
</div>
<div id="maingrid" style="margin: 0; padding: 0"></div>
</body>
</html>