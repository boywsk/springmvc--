<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="/tags" prefix="date"%>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>在线用户信息</title>
    <script type="text/javascript" src="../js/jquery-1.2.6.pack.js"></script>
    <link href="../css/ligerui-all.css" rel="stylesheet" type="text/css" />
    <!-- script src="js/jquery-1.4.4.min.js" type="text/javascript"></script -->
    <script src="../js/ligerUI/base.js" type="text/javascript"></script>
    <script src="../js/ligerUI/ligerGrid.js" type="text/javascript"></script>
    <script src="../js/ligerUI/ligerDrag.js" type="text/javascript"></script>
    <script src="../js/ligerUI/ligerResizable.js" type="text/javascript"></script>
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
        function search(){
            var userId = $("#userId").val();
            if(userId == ''){
                alert("用户ID不能为空！");
                return false;
            }

            $("#maingrid").ligerGrid({
                columns : [
                    {
                        display : '用户ID',
                        name : 'userId',
                        //width : 150,
                        align : 'left'
                    },
                    {
                        display : '用户设备Id',
                        name : 'clientId',
                        //width : 250,
                        align : 'left'
                    }, {
                        display : '连接服务器',
                        name : 'ipAndPort',
                        //width : 200,
                        align : 'left'
                    }],
                width : '97%',
                height : '97%',
                allowHideColumn : false,
                checkbox : false,
                usePager : false,
                rownumbers : false,
                alternatingRow : false,
                tree : {
                    columnName : 'name'
                },
                url : "getOnlineUserList.do?userId="+userId+"&random=" + (new Date()).valueOf(),
                method : "get",

            });
        }

        $(document).ready(function() {
        });
    </script>
</head>
<body>
<h3>在线用户信息</h3>
<div>
    用户ID：<input type="text" name="userId" id="userId"  value = "${param.userId}"/>
    <input type="button" onclick="search()" value="查询用户在线信息" />
</div>
<div id="maingrid"></div>
</body>
</html>