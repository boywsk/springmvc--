<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="/tags" prefix="date"%>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>AppServer-API-测试</title>
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
                url : "appApiTest.do?select="+select+"&jsonParms="+jsonParms+"&url="+url+"&random=" + (new Date()).valueOf(),
                method : "get",
            });
        }

        $(document).ready(function() {
        });
    </script>
</head>
<body>
<h3>AppServer-API-测试.</h3>
<br/>
<div>
    <table>
        <tr><td>
            请求地址:<input type="text" name="url" id="url" value = "${url}" size="35"/>
        </td></tr>
        <tr><td>
            测试项:
            <select id="select" name="select">
                <option value="group/addGroup.json" selected="selected">创建群组</option>
                <option value="group/editGroup.json">修改群信息</option>
                <option value="group/kickGroup.json">群组踢人</option>
                <option value="group/quitGroup.json">退出群组</option>
                <option value="group/joinGroup.json">加入群组</option>
                <option value="group/inviteGroup.json">邀请入群</option>
                <option value="group/auditMember.json">审核群成员</option>
                <option value="group/editMemberMark.json">修改备注</option>
                <option value="group/listGroupMember.json">获取群成员</option>
                <option value="group/listGroup.json">获取群列表</option>
                <!--<option value="group/addManager.json">添加管理员</option>
                <option value="group/delManager.json">删除管理员</option>-->
                <option value="group/getGroup.json">获取群组信息</option>
                <option value="group/shieldGroup.json">设置屏蔽群消息</option>
                <option value="group/sickiesGroup.json">设置群置顶</option>

                <option value="friend/addFriend.json">添加好友</option>
                <option value="friend/auditFriend.json">好友审核</option>
                <option value="friend/delFriend.json">删除好友</option>
                <option value="friend/editMark.json">对好友备注</option>
                <option value="friend/listFriend.json">获取好友列表</option>

                <option value="im/getQRCodeImage.json">获取二维码</option>

                <option value="user/login.json">用户登录</option>
                <option value="user/registerUser.json">注册用户</option>
                <option value="user/updateToken.json">更新用户token</option>
                <option value="user/getUserInfo.json">获取用户信息</option>
                <option value="user/findUser.json">查询用户接口</option>

                <option value="friendGroup/addFriendGroup.json">发表朋友圈说说</option>
                <option value="friendGroup/delFriendGroup.json">删除朋友圈说说</option>
                <option value="friendGroup/personalFriendGroup.json">查看个人自己发布过的说说历史</option>
                <option value="friendGroup/listFriendGroup.json">查看所有好友发布过的朋友圈</option>
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
    <tr><td>创建群组:</td>
        <td>
            {
            "uid": 1729,
            "nickName": "hello1",
            "groupName": "test123",
            "desc": "群描述",
            "avatar": "群头像的地址",
            "isAudit": 1,
            "capacity": 200,
            "members": [{"uid":3,"imUserId":3,"userName":"tom1"},{"uid":4,"imUserId":3,"userName":"tom2"}]
            }
    </td></tr>

    <tr><td>修改群信息:</td>
        <td>
            {
            "uid":10000,
            "nickName": "小李",
            "groupId":"643542cea444450b9efed3ac75a64fca",
            "groupName":"把群修改得试试",
            "desc":"设置一下群的描述",
            "avatar":"设置下群的头像",
            "isAudit":1,
            "capacity":200
            }
        </td></tr>

    <tr><td>退出群组:</td>
        <td>
            {
            "uid":1000006,
            "nickName":"tom2",
            "groupId":"643542cea444450b9efed3ac75a64fca"
            }
        </td></tr>
    <tr><td>群组踢人:</td>
        <td>
            {
            "uid":1000006,
            "nickName": "小李",
            "members": [{"uid":3,"userName":"tom1"},{"uid":4,"userName":"tom2"}],
            "groupId":"643542cea444450b9efed3ac75a64fca"
            }
        </td></tr>
    <tr><td>加入群组:</td>
        <td>
            {
            "groupId":"643542cea444450b9efed3ac75a64fca",
            "uid":1000006,
            "nickName":"tom"
            }
        </td></tr>
    <tr><td>邀请入群:</td>
        <td>
            {
            "groupId":"643542cea444450b9efed3ac75a64fca",
            "uid":1000006,
            "nickName":"tom",
            "members":[{"uid":3,"imUserId":3,"userName":"tom"}]
            }
        </td></tr>
    <tr><td>审核群成员:</td>
        <td>
            {
            "groupId":"643542cea444450b9efed3ac75a64fca",
            "uid":1000006,
            "members":[{"uid":3,"userName":"tom"}],
            "status":1
            }
        </td></tr>
    <tr><td>修改备注:</td>
        <td>
            {
            "groupId":"643542cea444450b9efed3ac75a64fca",
            "uid":1000006,
            "nickName": "小李",
            "markedUid":1000007,
            "mark":"213123123"
            }
        </td></tr>
    <tr><td>获取群成员:</td>
        <td>
            {
            "groupId":"643542cea444450b9efed3ac75a64fca",
            "uid":1000006
            }
        </td></tr>
    <tr><td>获取群列表:</td>
        <td>
            {
            "uid":1000006
            }
        </td></tr>

    <!--<tr><td>添加管理员:</td>
        <td>
            {
            "groupId":"643542cea444450b9efed3ac75a64fca",
            "uid":1000006,
            "memberIds":[1000007, 1000008]
            }
        </td></tr>
    <tr><td>删除管理员:</td>
        <td>
            {
            "groupId":"643542cea444450b9efed3ac75a64fca",
            "uid":1000006,
            "memberIds":[1000007, 1000008]
            }
        </td></tr>-->
    <tr><td>获取群组信息:</td>
        <td>
            {
            "groupId":"643542cea444450b9efed3ac75a64fca",
            "uid":1000006
            }
        </td></tr>

    <tr><td>设置屏蔽群消息:</td>
        <td>
            {
            "groupId":"643542cea444450b9efed3ac75a64fca",
            "uid":1000006,
            "isShield":1
            }
        </td></tr>

    <tr><td>设置群置顶:</td>
        <td>
            {
            "groupId":"643542cea444450b9efed3ac75a64fca",
            "uid":1000006,
            "stickies":1
            }
        </td></tr>

    <tr><td>添加好友:</td>
        <td>
            {
            "uid":1000006,
            "nickName":"tom",
            "friendUid":1000007
            }
        </td></tr>

    <tr><td>好友审核:</td>
        <td>
            {
            "uid":1000006,
            "friendUid":1000007,
            "status":2
            }
        </td></tr>

    <tr><td>删除好友:</td>
        <td>
            {
            "uid":1000006,
            "friendUid":1000007
            }
        </td></tr>
    <tr><td>对好友备注:</td>
        <td>
            {
            "uid":1000006,
            "friendUid":1000007,
            "mark":"213123123"
            }
        </td></tr>
    <tr><td>获取好友列表:</td>
        <td>
            {
            "uid":1000006
            }
        </td></tr>
    <tr><td>获取二维码:</td>
        <td>
            {
            "type":1,
            "id":"5656c5fec0b5fa1d3cfcf130"
            }
        </td></tr>

    <tr><td>用户登录:</td>
        <td>
            {
            "phoneNumber": "150111111111",
            "password": "password"
            }
        </td></tr>

    <tr><td>注册用户:</td>
        <td>
            {
            "phoneNumber": "150111111111",
            "userName": "小李",
            "password": "password",
            "nickName": "john",
            "avatar": "avatar",
            "gender": 0,
            "region": "北京",
            "birthday": 1223,
            "autograph": "autograph",
            "mobileClientId": 0,
            "pcClientId": 0
            }
        </td></tr>

    <tr><td>更新用户token:</td>
        <td>
            {
            "id": 3,
            "imUserId": 0
            }
        </td></tr>

    <tr><td>获取用户信息:</td>
        <td>
            {
            "id":3
            }
        </td></tr>
    <tr><td>查询用户:</td>
    <td>
        {
        "phoneNumber": "150111111111"
        }
    </td></tr>

    <tr><td>发表朋友圈说说:</td>
        <td>
            {
            "uid": 69,
            "nickName": "tom",
            "contentType":1,
            "content": "小李",
            "extra": "http://localhost:8080"
            }
        </td></tr>

    <tr><td>删除朋友圈说说:</td>
        <td>
            {
            "id":1,
            "uid": 69
            }
        </td></tr>

    <tr><td>查看个人自己发布过的说说历史:</td>
        <td>
            {
            "uid": 69
            }
        </td></tr>

    <tr><td>查看所有好友发布过的朋友圈:</td>
        <td>
            {
            "uid": 69,
            "beforeDay": 1,
            "endTime":0
            }
        </td></tr>
</table>
</div>
</body>
</html>