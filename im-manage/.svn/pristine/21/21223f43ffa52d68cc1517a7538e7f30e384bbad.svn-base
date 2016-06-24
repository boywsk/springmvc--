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
	var grid = null;
	$(function() {
		var appId = $("#appId").val();
		grid = $("#maingrid4").ligerGrid({
			columns : [{
				display : '用户id',
				name : 'uid',
				align : 'left',
				width : '10%'
			},{
				display : 'thirdUid',
				name : 'thirdUid',
				align : 'left',
				width : '10%'
			},{
				display : 'token',
				name : 'token',
				align : 'left',
				width : '22%'
			},{
				display : 'tokenExpires',
				name : 'tokenExpires',
				align : 'left',
				width : '10%'
			},{
				display : '设备类型', isAllowHide: false,
				align : 'left',
            	width : '10%',
                render: function (list)
                {
                	switch(parseInt(list.clientId))
                	{
                	case 10:{return "ios";break;}
                	case 11:{return "android";break;}
                	case 12:{return "wp";break;}
                	case 20:{return "pc";break;}
                	case 21:{return "mac";break;}
                	case 22:{return "ubuntu";break;}
                	case 23:{return "linux";break;}
                	case 24:{return "unix";break;}
                	case 25:{return "ipad";break;}
                	default:{return "undefined";break;}
                	}
                }
			},{
				display : 'clientType',  isAllowHide: false,
				align : 'left',
				width : '10%',
				render: function(list){
					switch(parseInt(list.clientType)){
					case 0: {return "IM"; break;}
					case 1: {return "push"; break;}
					default: {return "undefined"; break;}
					}
				}
			},{
				display : 'apnsToken',
				name : 'apnsToken',
				align : 'left',
				width : '10%'
			},{
				display : '创建时间',
				name : 'createTime',
				align : 'left',
				width : '12%'
			},{
				display : '修改时间',
				name : 'updateTime',
				align : 'left',
				width : '12%'
			}],
			width : '100%',
            allowHideColumn : false,
            checkbox : false,
            usePager : true,
            rownumbers : false,
            alternatingRow : false,
            pageSize : 10, //每页显示条数
            pageSizeOptions : [10],
			width : '99%',
			url : "listUser.do?appId="+appId+"&random=" + (new Date()).valueOf(),
	        method : "get",
		});
	});
	function tUserInfoSearch(){
		var appId = $("#appId").val();
		var uId = $("#uId").val();
		var startTime = $("#startTime").val();
		var endTime = $("#endTime").val();
		if(appId == null){
			alert("APPID不可为空！");
			return false;
		}
		grid = $("#maingrid4").ligerGrid({
			columns : [{
				display : '用户id',
				name : 'uid',
				align : 'left',
				width : '10%'
			},{
				display : 'thirdUid',
				name : 'thirdUid',
				align : 'left',
				width : '10%'
			},{
				display : 'token',
				name : 'token',
				align : 'left',
				width : '22%'
			},{
				display : 'tokenExpires',
				name : 'tokenExpires',
				align : 'left',
				width : '10%'
			},{
				display : '设备类型', isAllowHide: false,
				align : 'left',
            	width : '10%',
                render: function (clientId)
                {
                	switch(parseInt(clientId.clientId))
                	{
                	case 10:{return "ios";break;}
                	case 11:{return "android";break;}
                	case 12:{return "wp";break;}
                	case 20:{return "pc";break;}
                	case 21:{return "mac";break;}
                	case 22:{return "ubuntu";break;}
                	case 23:{return "linux";break;}
                	case 24:{return "unix";break;}
                	case 25:{return "ipad";break;}
                	default:{return "undefined";break;}
                	}
                }
			},{
				display : 'clientType',  isAllowHide: false,
				align : 'left',
				width : '10%',
				render: function(list){
					switch(parseInt(list.clientType)){
					case 0: {return "IM"; break;}
					case 1: {return "push"; break;}
					default: {return "undefined"; break;}
					}
				}
			},{
				display : 'apnsToken',
				name : 'apnsToken',
				align : 'left',
				width : '10%'
			},{
				display : '创建时间',
				name : 'createTime',
				align : 'left',
				width : '12%'
			},{
				display : '修改时间',
				name : 'updateTime',
				align : 'left',
				width : '12%'
			}],
			width : '100%',
            allowHideColumn : false,
            checkbox : false,
            usePager : true,
            rownumbers : false,
            alternatingRow : false,
            pageSize : 10, //每页显示条数
            //pageSizeOptions : [10],
			width : '99%',
			url : "listUser.do?appId="+appId+"&uId="+uId+"&startTime="+startTime+"&endTime="+endTime+"&random="+ (new Date()).valueOf(),
	        method : "get",
		});}
	function f_search() {
		var nickName = $("#txtKey").val();
		grid = $("#maingrid4").ligerGrid({
			columns : [ {
				display : '用户id',
				name : 'uid',
				align : 'left',
				width : 120
			}, {
				display : '昵称',
				name : 'nickName',
				minWidth : 100
			}, {
                display: '状态', isAllowHide: false,
                 render: function (row)
                 {
                     if(row.status) {
                     	return "<div style='margin-top: 5px;'><img height='16' width='16' src='../images/user_online.png' alt='在线'/></div>";
                     }
                     return "<div style='margin-top: 5px;'><img height='16' width='16' src='../images/user_offline.png' alt='离线'/></div>";
                 }
             }, {
				display : '设备',
				name : 'deviceName',
				width : 320,
				align : 'left'
			}, {
				display : '设备Token',
				name : 'deviceToken',
				minWidth : 120
			}, {
				display : '创建时间',
				name : 'formateCreateTime',
				minWidth : 100
			}, {
				display : '修改时间',
				name : 'formateUpdateTime',
				minWidth : 100
			}  ],
			pageSize : 10,
			rownumbers : true,
			pageSizeOptions : [10],
			width : '99%',
			url : "searchUser.do?nickName="+ nickName + "&random=" + (new Date()).valueOf(),
	        method : "get",
		});
	}
	$(document).ready(function() {
    });
</script>
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
	用户ID：<input id="uId" name="uId" type="text" class="inputText4">
	&nbsp;&nbsp;&nbsp;&nbsp;
    注册时间：
    <input type="text" id="startTime" name="startTime" class="Wdate" onfocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd',maxDate:'%y-%M-%d'})"/> 
    到：
    <input type="text" id="endTime" name="endTime" class="Wdate" onfocus="WdatePicker({skin:'whyGreen',minDate:'#F{$dp.$D(\'startDate\')}',dateFmt:'yyyy-MM-dd',maxDate:'%y-%M-%d'})"/> 
    &nbsp;&nbsp;&nbsp;&nbsp;
	<input id="btnOK" style="width: 60px;height: 28px" type="button" value="查  询" onclick="tUserInfoSearch()">
</div>
<div id="maingrid4" style="margin: 0; padding: 0"></div>
<div style="display: none;">
</div>
</body>
</html>