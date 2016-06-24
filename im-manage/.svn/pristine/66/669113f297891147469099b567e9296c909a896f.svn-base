<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="/tags" prefix="date"%>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>群组信息</title>
    <script type="text/javascript" src="../js/jquery-1.2.6.pack.js"></script>
    <link href="../css/ligerui-all.css" rel="stylesheet" type="text/css" />
    <script src="../js/ligerUI/base.js" type="text/javascript"></script>
    <script src="../js/ligerUI/ligerGrid.js" type="text/javascript"></script>
    <script src="../js/ligerUI/ligerDrag.js" type="text/javascript"></script>
    <script src="../js/ligerUI/ligerResizable.js" type="text/javascript"></script>
    <script src="../js/My97DatePicker/WdatePicker.js" type="text/javascript"></script>
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
            var groupId = $("#groupId").val();
            var appId = $("#appId").val();
            var senderId = $("#senderId").val();
            var senderName = $("#senderName").val();
            var startTime = $("#startTime").val();
            var endTime = $("#endTime").val();
            /* if(groupId == ''){
                alert("groupId不能为空!");
                return false;
            } */
            if(startTime == '' || endTime == ''){
                alert("开始和结束时间不能为空!");
                return false;
            }
            $("#maingrid").ligerGrid({
                columns : [
                    {display : '群组groupId',name : 'groupId',align : 'left'},
                    {display : '群组名称',name : 'groupName',align : 'left'}, 
                    {display : '群组类型',	isAllowHide: false,				//name : 'groupType',type : 'int',align : 'left'
                        render: function (row)
                        {
                        	if(row.groupType == 1){return "<div style='margin-top: 5px;'><img height='16' width='16' src='../images/group/two.png' /></div>";}
                        	if(row.groupType == 2){return "<div style='margin-top: 5px;'><img height='16' width='16' src='../images/group/many.png' /></div>";}
                            if(row.groupType == 3){return "系统消息";}
	                    	if(row.groupType == 4){return "小秘书";}
                        }
                    },
                    {display : '内容',name : 'msgBody',align : 'left'},
                    {display : 'msgId',name : 'msgId',align : 'left'},
                    {display : '序列',name : 'msgSeqId',type : 'int',align : 'left'},
                    {display : '消息类型',	isAllowHide: false,				//name : 'msgType',type : 'int',align : 'left'
                    	//1:文本、2:语音、3:图片、4:附件、5:分享/转发(通过url)、..
                    	render: function (row)
                        {
                        	if(row.msgType == 1){return "文本";}
                        	if(row.msgType == 2){return "语音";}
                            if(row.msgType == 3){return "图片";}
	                    	if(row.msgType == 4){return "附件";}
	                    	if(row.msgType == 5){return "分享/转发";}else{return "其它";}
                        }
                    },
                    //{display : '消息url',name : 'msgUrl',align : 'left'} ,
                    {display : '发送时间',name : 'formatSendTime',width : 160,align : 'left'} ,
                    {display : '发送者id',name : 'senderId',align : 'left'},
                    {display : '发送者名称',name : 'senderName',align : 'left'}
                    ],
                width : '100%',
                height : '100%',
                allowHideColumn : false,
                checkbox : false,
                usePager : true,
                rownumbers : false,
                alternatingRow : false,
                pageSize : 10, //每页显示条数
                tree : {
                    columnName : 'name'
                },
                url : "searchGroupMsg.do?appId="+appId+"&groupId="+groupId+"&senderId="+senderId+"&senderName="+senderName+"&startTime="+startTime+"&endTime="+endTime+"&random=" + (new Date()).valueOf(),
                method : "get",
            });
        }

        $(document).ready(function() {
        });
    </script>
</head>
<body>
<h3>离线消息.</h3>
<br/>
<div>
	&nbsp;&nbsp;&nbsp;&nbsp;
	<c:if test="${type=='2'}">
        APPID：<select name = "appId" id = "appId">
					<c:forEach items="${appIdList}" var="data">
			             <option value="${data.appId}">${data.appId}</option>
			     	</c:forEach>
				</select>
    </c:if>
    <c:if test="${type=='1'}">
    	APPID列表：<input type="text" name = "appId" id = "appId" />
    </c:if>
    &nbsp;&nbsp;&nbsp;&nbsp;
    groupId：<input type="text" name="groupId" id="groupId" value = "${param.groupId}"/>
    &nbsp;&nbsp;&nbsp;&nbsp;
    发送者id：<input type="text" name="senderId" id="senderId" value = "${param.senderId}"/>
    &nbsp;&nbsp;&nbsp;&nbsp;
    发送者名称：<input type="text" name="senderName" id="senderName" value = "${param.senderName}"/>
    <br/>    
    <br/>
    &nbsp;&nbsp;&nbsp;&nbsp;
    时间从：<!-- onfocus -->
    <input type="text" id="startTime" name="startTime" class="Wdate" onClick="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd HH:mm',maxDate:'%y-%M-%d'})"/> 
    到：
    <input type="text" id="endTime" name="endTime" class="Wdate" onClick="WdatePicker({skin:'whyGreen',minDate:'#F{$dp.$D(\'startDate\')}',dateFmt:'yyyy-MM-dd HH:mm',maxDate:'%y-%M-%d'})"/> 
    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
    <input type="button" onclick="search()" value="搜索" />
</div>
<br/>
<div id="maingrid"></div>
</body>
</html>