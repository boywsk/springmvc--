<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="/tags" prefix="date"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
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
		grid = $("#maingrid5").ligerGrid({
			columns : [
						{display : '日期', 	name : 'countDate',	align : 'left',width : '20%'},
						{display : '头像统计',	name : 'avatar',	align : 'left',width : '20%'},
						{display : '图片统计',	name : 'image',		align : 'left',width : '20%'},
						{display : '语音统计',	name : 'voice',		align : 'left',width : '20%'},
						{display : '视频统计',	name : 'vedio',		align : 'left',width : '20%'}
						],
			width : '100%',
			fixedCellHeight :false,
            allowHideColumn : false,
            checkbox : false,
            usePager : true,
            rownumbers : false,
            alternatingRow : false,
			width : '100%',
			url : "preStatisticsList.do?random=" + (new Date()).valueOf(),
	        method : "get",
	       });
	});	
	function tSearch(){
		var uid = $("#uid").val();
		var searchType=$("#searchType").val();
		var startDate=$("#startDate").val();
		var endDate=$("#endDate").val();
		grid = $("#maingrid5").ligerGrid({
			columns : [
						{display : '日期', 	name : 'countDate',	align : 'left',width : '20%'},
						{display : '头像统计',	name : 'avatar',	align : 'left',width : '20%'},
						{display : '图片统计',	name : 'image',		align : 'left',width : '20%'},
						{display : '语音统计',	name : 'voice',		align : 'left',width : '20%'},
						{display : '视频统计',	name : 'vedio',		align : 'left',width : '20%'}
						],
			width : '100%',
			fixedCellHeight :false,
            allowHideColumn : false,
            checkbox : false,
            usePager : true,
            rownumbers : false,
            alternatingRow : false,
			width : '100%',
			url : "preStatisticsList.do?startDate="+startDate+"&endDate="+endDate+"&random=" + (new Date()).valueOf(),
	        method : "get",
		});
	}
	$(document).ready(function() {
    });
</script>
<body>
<div>	
	&nbsp;&nbsp;&nbsp;&nbsp;
   	 时间范围：
    <input type="text" id="startDate" name="startDate" class="Wdate" onfocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd',maxDate:'%y-%M-%d'})"/> 
    	到：
    <input type="text" id="endDate" name="endDate" class="Wdate" onfocus="WdatePicker({skin:'whyGreen',minDate:'#F{$dp.$D(\'startDate\')}',dateFmt:'yyyy-MM-dd',maxDate:'%y-%M-%d'})"/> 
    &nbsp;&nbsp;&nbsp;&nbsp;
	<input style="width: 60px;height: 28px" type="button" value="查  询" onclick="tSearch()">
</div>
<div id="maingrid5" style="margin: 0; padding: 0"></div>
<div style="display: none;">
</div>
</body>
</html>