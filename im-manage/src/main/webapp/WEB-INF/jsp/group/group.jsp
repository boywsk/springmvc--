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
    <!-- script src="js/jquery-1.4.4.min.js" type="text/javascript"></script -->
    <script src="../js/ligerUI/base.js" type="text/javascript"></script>
    <script src="../js/ligerUI/ligerGrid.js" type="text/javascript"></script>
    <script src="../js/ligerUI/ligerDrag.js" type="text/javascript"></script>
    <script src="../js/ligerUI/ligerResizable.js" type="text/javascript"></script>
    <script src="../js/My97DatePicker/WdatePicker.js" type="text/javascript"></script>
    <script type="text/javascript">
    	var grid = null;
	    $(function() {
		    var userId = $("#userId").val();
	        var groupId = $("#groupId").val();
	        var appId = $("#appId").val();
	        $("#maingrid").ligerGrid({
	            columns : [
					{
	                    display : '群组groupId',
	                    name : 'groupId',
	                    width : '15%',
	                    align : 'left'
               		},{
					    display : '创建者昵称',
					    name : 'nickName',
					    width : '15%',
					    align : 'left'
					},{
					    display : '创建者id',
					    name : 'uid',
					    width : '10%',
					    align : 'left'
					},{                  
                    	display: '群组类型', isAllowHide: false,
                    	width : '10%',
                        render: function (type)
                        {
                        	if(type.type == 1){return "<div style='margin-top: 5px;'><img height='16' width='16' src='../images/group/two.png' /></div>";}
                        	if(type.type == 2){return "<div style='margin-top: 5px;'><img height='16' width='16' src='../images/group/many.png' /></div>";}
                            if(type.type == 3){return "系统消息";}
	                    	if(type.type == 4){return "小秘书";}
                        }
	                },{
					    display : '递增Id',
					    name : 'seq',
					    width : '10%',
					    align : 'left'
					},{
	                    display : '群创建时间',
	                    name : 'formateCreateTime',
	                    width : '20%',
	                    type : 'date',
	                    align : 'left'
	                },{
					    display : '更新时间 ',
					    name : 'formateUpdateTime',
					    width : '20%',
					    type : 'date',
					    align : 'left'
					}],
		            allowHideColumn : false,
		            checkbox : false,
		            usePager : true,
		            //pageSize : 2,
		            //pageSizeOptions:[2,4,6,8,10], //可指定每页页面大小
		            rownumbers : false,
		            alternatingRow : false,
		            tree : {
		                columnName : 'name'
		            },
	            url : "allGroupList.do?appId="+appId+"&random=" + (new Date()).valueOf(),
	            method : "get",
	
	        });
		});
	    function searchAppId() {
		    var userId = $("#userId").val();
	        var groupId = $("#groupId").val();
	        var appId = $("#appId").val();
	        var startTime = $("#startTime").val();
	        var endTime = $("#endTime").val();
	        if( appId == ""){
	        	alert("APPID不可为空！");
	        	return false;
	        }
	        $("#maingrid").ligerGrid({
	            columns : [
					{
	                    display : '群组groupId',
	                    name : 'groupId',
	                    width : '15%',
	                    align : 'left'
               		},{
					    display : '创建者昵称',
					    name : 'nickName',
					    width : '15%',
					    align : 'left'
					},{
					    display : '创建者id',
					    name : 'uid',
					    width : '10%',
					    align : 'left'
					},{                  
                    	display: '群组类型', isAllowHide: false,
                    	width : '10%',
                        render: function (type)
                        {
                        	if(type.type == 1){return "<div style='margin-top: 5px;'><img height='16' width='16' src='../images/group/two.png' /></div>";}
                        	if(type.type == 2){return "<div style='margin-top: 5px;'><img height='16' width='16' src='../images/group/many.png' /></div>";}
                            if(type.type == 3){return "系统消息";}
	                    	if(type.type == 4){return "小秘书";}
                        }
	                },{
					    display : '递增Id',
					    name : 'seq',
					    width : '10%',
					    align : 'left'
					},{
	                    display : '群创建时间',
	                    name : 'formateCreateTime',
	                    width : '20%',
	                    type : 'date',
	                    align : 'left'
	                },{
					    display : '更新时间 ',
					    name : 'formateUpdateTime',
					    width : '20%',
					    type : 'date',
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
	            url : "allGroupList.do?appId="+appId+"&startTime="+startTime+"&endTime="+endTime+"&random=" + (new Date()).valueOf(),
	            method : "get",
	        });
		}
        function searchGroup(){
            var userId = $("#userId").val();
            var groupId = $("#groupId").val();
            var appId = $("#appId").val();
            if(appId != ""){
            	if(userId == ""){
            		alert("用户ID不可为空！");
            		return false;
            	}            	
            }else{
            	alert("APPID不可为空！");
	        	return false;
            }
            $("#maingrid").ligerGrid({
                columns : [
                    {display : '群组groupId',name : 'groupId',width : '20%',align : 'left'
                    },{                    
	                    display:'群组类型',isAllowHide:false,
	                    width:'15%',
	                    render:function(type){
	                    	if(type.type == 1){return "单聊";}
	                    	if(type.type == 2){return "群聊";}
	                    	if(type.type == 3){return "系统消息";}
	                    	if(type.type == 4){return "小秘书";}
	                    }
                    },{display : '群主userId',name : 'uid',width : '15%',type : 'int',align : 'left'
                    },{display : '群组容量',name : 'capacity',width : '15%',align : 'left'
                    },{display : '群创建时间',name : 'formateCreateTime',width : '20%',type : 'date',align : 'left'
                    },{display : '群组状态',isAllowHide:false, 
                    	width : '15%',
                    	render:function(row){                    		
                    		if(row.isDele == 1){return "已删除";}else{return "正常";}
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
                url : "groupList.do?appId="+appId+"&userId="+userId+"&random=" + (new Date()).valueOf(),
                method : "get",
            });
        }
        function searchMembers(){
            var userId = $("#userId").val();
            var groupId = $("#groupId").val();
            var appId = $("#appId").val();
            if(appId != ""){
            	if(groupId == ""){
            		alert("群组ID不可为空！");
            		return false;
            	}            	
            }else{
            	alert("APPID不可为空！");
	        	return false;
            }
            $("#maingrid").ligerGrid({
                columns : [
                    {
                        display : '成员id',
                        name : 'uid',
                        width : '10%',
                        align : 'left'
                    },{
                    	display: '身份类型', isAllowHide: false,
                    	 width : '10%',
                        render: function (identity)
                        {
                            if(identity.identity == 0 ) {return "普通成员";}
                    		if(identity.identity == 1 ) {return "创建者";}
                    		if(identity.identity == 2 ) {return "管理员";}
                            return "0";
                        }
                    },{
                        display : '递增Id',
                        name : 'eq',
                        width : '10%',
                        align : 'left'
                    },{
                        display : '当前群消息seq',
                        name : 'initSeq',
                        width : '10%',
                        align : 'left'
                    },{
                        display : '读取到最大群消息seq',
                        name : 'readSeq',
                        width : '20%',
                        align : 'left'
                    },{
                        display : '加入时间',
                        name : 'formatJoinTime',
                        width : '20%',
                        type : 'date',
                        align : 'left'
                    },{
                        display : '更新时间 ',
                        name : 'formatUpdateTime',
                        width : '20%',
                        type : 'date',
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
                url : "memberList.do?appId="+appId+"&groupId="+groupId+"&random=" + (new Date()).valueOf(),
                method : "get",

            });
        }
        function searchGroupInfo(){
            var userId = $("#userId").val();
            var groupId = $("#groupId").val();
            var appId = $("#appId").val();
            if(appId != ""){
            	if(groupId == ""){
            		alert("群组ID不可为空！");
            		return false;
            	}            	
            }else{
            	alert("APPID不可为空！");
	        	return false;
            }
            $("#maingrid").ligerGrid({
                columns : [
                    {display : '群组groupId',name : 'groupId',width : '20%',align : 'left'},
                    {                    
	                    display:'群组类型',isAllowHide:false,
	                    width:'15%',
	                    render:function(type){
	                    	if(type.type == 1){return "单聊";}
	                    	if(type.type == 2){return "群聊";}
	                    	if(type.type == 3){return "系统消息";}
	                    	if(type.type == 4){return "小秘书";}
	                    }
                    },
                    {display : '群主userId',	name : 'uid',				width : '15%',type : 'int',align : 'left'},
                    {display : '群组容量',		name : 'capacity',			width : '15%',align : 'left'},
                    {display : '群创建时间',	name : 'formateCreateTime',	width : '20%',type : 'date',align : 'left'},
                    {display : '群组状态',isAllowHide:false, 
                    	width : '15%',
                    	render:function(row){                    		
                    		if(row.isDele == 1){return "已删除";}else{return "正常";}
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
                url : "getGroupInfo.do?appId="+appId+"&groupId="+groupId+"&random=" + (new Date()).valueOf(),
                method : "get",
            });
        }
        $(document).ready(function() {
        });
    </script>
</head>
<body>
<h3>查询群组信息</h3>
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
    时间从：
    <input type="text" id="startTime" width="21px" size="21px" name="startTime" class="Wdate" onfocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd HH:mm',maxDate:'%y-%M-%d'})"/> 
    到：
    <input type="text" id="endTime" width="21px" size="21px" name="endTime" class="Wdate" onfocus="WdatePicker({skin:'whyGreen',minDate:'#F{$dp.$D(\'startDate\')}',dateFmt:'yyyy-MM-dd HH:mm',maxDate:'%y-%M-%d'})"/> 
    &nbsp;&nbsp;&nbsp;&nbsp;
    <input type="button" onclick="searchAppId()" value="查询APPID中群组" />
    &nbsp;&nbsp;&nbsp;&nbsp;【时间条件仅次行有效】
    <br>
    <hr>
    &nbsp;&nbsp;&nbsp;&nbsp;在APPID的前提下查询功能：&nbsp;&nbsp;&nbsp;&nbsp;
    用户ID：<input type="text" name="userId" id="userId"  value = "${param.name}"/>
    <input type="button" onclick="searchGroup()" value="查询用户群组列表" />
    &nbsp;&nbsp;&nbsp;&nbsp;
    群组ID：<input type="text" name="groupId" id="groupId" value = "${param.userName}" size="35"/>
    <input type="button" onclick="searchMembers()" value="查询群组成员列表" />
    <input type="button" onclick="searchGroupInfo()" value="查询群组信息" />
</div>
<div id="maingrid" style="margin: 0; padding: 0"></div>
</body>
</html>