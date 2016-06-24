<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="/tags" prefix="date"%>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>BS-IM-API-测试</title>
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
            var select = $("#select").val();
            var jsonParms = $("#jsonParms").val();
            var url = $("#url").val();
            var appId = $("#appId").val();
            if(jsonParms == ''|| url == ''){
                alert("请求地址或请求参数不能为空!");
                return false;
            }

            $("#maingrid").ligerGrid({
                columns : [
                    {
                        display : 'result',
                        name : 'resultData',
                        //width : 200,
                        height : 1000
//                        align : 'left'
                    }],
                width : '97%',
//                height : '100%',
                checkbox : false,
                usePager : false,
                rowHeight:60,
                tree : {
                    columnName : 'name'
                },
                url : "bsApiTest.do?select="+select+"&jsonParms="+jsonParms+"&url="+url+"&appId="+appId+"&random=" + (new Date()).valueOf(),
                method : "get",
            });
        }

        $(document).ready(function() {
        });
    </script>
</head>
<body>
<h3>BS-IM-API-测试.</h3>
<br/>
<div>
    <table>
        <tr><td>
            请求地址:<input type="text" name="url" id="url" value = "${url}" size="35"/>
        </td></tr>
        <tr><td>
            APPID:<input type="text" name="appId" id="appId" value = "${appId}"/>
        </td></tr>
        <tr><td>
            测试项:
            <select id="select" name="select">
                <option value="group/addGroup.json" selected="selected">创建群组(圈子)</option>
                <option value="group/joinGroup.json">加入群组(圈子)</option>
                <option value="group/quitGroup.json">退出群组(圈子)</option>
                <option value="group/kickGroup.json">群主踢人</option>
                <option value="group/disbandGroup.json">解散群组</option>

                <option value="user/register.json">注册用户</option>

                <option value="message/notifyMessage.json">发送消息</option>
                <option value="message/notifyCommandMsg.json">发送命令动作类消息</option>
                <option value="message/broadcastMessage.json">发送全量广播消息</option>
            </select>
        </td></tr>
        <tr><td><textarea id="jsonParms" name="jsonParms" style="width:900px;height:200px;">请求参数(JSON格式)</textarea>
        </td></tr>
        <tr><td align="right">
            <input type="button" onclick="search()" value="提交" />
        </td></tr>
    </table>
</div>
<br/>
<br/>
<h3>返回结果.</h3>
<div id="maingrid"></div>
<br/>
<br/>
<div>
    <h3>请求参数示例.</h3>
<table>
    <tr><td>创建群组(圈子):</td>
        <td>
            {
            "groupId": "643542cea444450b9efed3ac75a64fca",
            "imUserId": 1,
            "members": [
            {
            "imUserId": 2
            },
            {
            "imUserId": 3
            }
            ],
            "extra": {
            "msgBody": "xxx创建群组",
            "ext": "扩展消息",
            "nickName": "昵称",
            "groupName": "groupName"
            }
            }
        </td></tr>

    <tr><td>加入群组(圈子):</td>
        <td>
            {
            "groupId": "643542cea444450b9efed3ac75a64fca",
            "members": [
            {
            "imUserId": 2
            },
            {
            "imUserId": 3
            }
            ],
            "extra": {
            "msgBody": "xxx加入群组",
            "ext": "扩展消息",
            "nickName": "昵称",
            "imUserId": 1,
            "groupName": "groupName"
            }
            }
        </td></tr>

    <tr><td>退出群组(圈子):</td>
        <td>
            {
            "groupId": "643542cea444450b9efed3ac75a64fca",
            "imUserId": 1000006,
            "extra": {
            "msgBody": "xxx创建群组",
            "ext": "扩展消息",
            "nickName": "昵称",
            "groupName": "groupName"
            }
            }
        </td></tr>

    <tr><td>群主踢人:</td>
        <td>
            {
            "groupId": "643542cea444450b9efed3ac75a64fca",
            "imUserId": 1000006,
            "memberIds": [
            1000007,
            1000008
            ],
            "extra": {
            "msgBody": "xxx,xxx发表了违法的言论",
            "ext": "扩展消息",
            "nickName": "昵称",
            "groupName": "groupName"
            }
            }
        </td></tr>

    <tr><td>注册用户:</td>
        <td>
            {
            "useUid": 1,    //参数不需要改变
            "opt": 1,       //参数不需要改变
            "reqUser": {"uid":"1"}   //uid 值为要注册的用户id
            }
        </td></tr>

    <tr><td>发送单聊消息(或命令消息):</td>
        <td>
            {
            "senderId": 100000,
            "senderName": "senderName",
            "groupType": 1,
            "msgType":1,
            "receiveId": 100001,
            "senderReceiveMsg":false,
            "msgBody":"文案内容",
            "extra": "{\"msgData\":{\"msg\":\"内容\",\"type\":2},\"msgType\":{\"action\":\"cmd\",\"type\":1}}"
            }
        </td></tr>
    <tr><td>发送群聊消息(或命令消息):</td>
        <td>
            {
            "senderId": 100000,
            "senderName": "senderName",
            "groupType": 2,
            "msgType":1,
            "groupId": "643542cea444450b9efed3ac75a64fca",
            "groupName": "群名称",
            "msgBody":"文案内容",
            "extra": "{\"msgData\":{\"msg\":\"内容\",\"type\":2},\"msgType\":{\"action\":\"cmd\",\"type\":1}}"
            }
        </td></tr>

    <tr><td>发送全量广播消息:</td>
        <td>
            {
            "senderId": 100000,
            "senderName": "昵称",
            "isPersist":false,
            "msgBody":"文案内容",
            "extra": "{\"msgData\":{\"msg\":\"内容\",\"type\":2},\"msgType\":{\"action\":\"cmd\",\"type\":1}}"
            }
        </td></tr>

    <tr><td>解散群组:</td>
        <td>
            {
            "groupId": "643542cea444450b9efed3ac75a64fca",
            "imUserId": 1000006,
            "extra": {
            "content": "xxx解散了群组",
            "nickName": "昵称",
            "groupName": "groupName"
            }
            }
        </td></tr>
</table>
</div>
</body>
</html>