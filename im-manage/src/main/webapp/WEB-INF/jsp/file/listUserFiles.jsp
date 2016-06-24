<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
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
		grid = $("#maingrid4").ligerGrid({
			columns : [
						{display : '用户id', 	name : 'uid',		align : 'left',width : '10%'},
						{display : '创建时间',	name : 'createTime',align : 'left',width : '15%'},
						{display : '文件名称',	name : 'fileName',	align : 'left',width : '20%'},
						{display : '文件路径',	name : 'filePath',	align : 'left',width : '30%'},
						{display : '详细',	name : 'filePath',	align : 'left',width : '5%',
							render:function(row){
								var html = '<a href="../file/display.do?filePath=' + row.filePath + '">详细</a>';
								return html;
							}
						
						},
						{display : '显示',	isAllowHide:false,width:'20%',
						     render: function (row)
						     {
						    	 if(checkVoice(row.filePath)){
						    		 return "请手动下载";
						    	 }else if(checkVedio(row.filePath)){
						    		 var extStart = url.lastIndexOf(".")+1;
									 var fileType = url.substring(extStart,row.filePath.length);
									 var location = row.filePath.replace("."+fileType,"_img.jpg");
						    		 return "<img height='90' width='90'  src='" + location + "' />";
						    	 }else{
						    		 return "<img height='90' width='90'  src='" + row.filePath + "' />";
						    	 }
						         
						     }
						}],
			width : '100%',
			fixedCellHeight :false,
            allowHideColumn : false,
            checkbox : false,
            usePager : true,
            rownumbers : false,
            alternatingRow : false,
			width : '99%',
			url : "listUserFiles.do?searchType=t_avatar_url&random=" + (new Date()).valueOf(),
	        method : "get",
		});
	});
	function checkVoice(url){
		var url = url.toLowerCase()
		if(url.indexOf("wav")>-1) return true;
		if(url.indexOf("amr")>-1) return true;
	}
	function checkVedio(url){
		var allowtype =  ["mp4","3gp","avi","swf","flv","mov","asf","mkv","rm","wav","mpg","rmvb"];
		var extStart = url.lastIndexOf(".")+1;
	    var fileType = url.substring(extStart,url.length).toLowerCase();//toUpperCase()
	    if ($.inArray(fileType,allowtype) == -1){
	        return false;
	    }else{
	    	return true;
	    }
	}
	function tUserInfoSearch(){
		var uid = $("#uid").val();
		var searchType=$("#searchType").val();
		var startTime=$("#startTime").val();
		var endTime=$("#endTime").val();
		grid = $("#maingrid4").ligerGrid({
			columns : [
						{display : '用户id', 	name : 'uid',		align : 'left',width : '10%'},
						{display : '创建时间',	name : 'createTime',align : 'left',width : '15%'},
						{display : '文件名称',	name : 'fileName',	align : 'left',width : '20%'},
						{display : '文件路径',	name : 'filePath',	align : 'left',width : '30%'},
						{display : '详细',	name : 'filePath',	align : 'left',width : '5%',
							render:function(row){
								var html = '<a href="../file/display.do?filePath=' + row.filePath + '">详细</a>';
								return html;
							}
						},						
						{display : '显示',	isAllowHide:false,width:'20%',
							render: function (row)
						     {
						    	 if(checkVoice(row.filePath)){
						    		 return "请手动下载";
						    	 }else if(checkVedio(row.filePath)){
						    		 var extStart = row.filePath.lastIndexOf(".")+1;
									 var fileType = row.filePath.substring(extStart,row.filePath.length);
									 var location = row.filePath.replace("_vedio."+fileType,"_img.jpg");
						    		 return "<img height='90' width='90'  src='" + location + "' />";
						    	 }else{
						    		 return "<img height='90' width='90'  src='" + row.filePath + "' />";
						    	 }
						     }
						}],
			width : '100%',
			fixedCellHeight :false,
            allowHideColumn : false,
            checkbox : false,
            usePager : true,
            rownumbers : false,
            alternatingRow : false,
			width : '99%',
			url : "listUserFiles.do?uid="+uid+"&searchType="+searchType+"&startTime="+startTime+"&endTime="+endTime+"&random=" + (new Date()).valueOf(),
	        method : "get",
		});
	}
	$(document).ready(function() {
    });
</script>
<body>
<div>
	&nbsp;&nbsp;&nbsp;&nbsp;
	用户ID：<input id="uid" name="uid" type="text" class="inputText4">
	&nbsp;&nbsp;&nbsp;&nbsp;
	查询类型：
	<select name = "searchType" id = "searchType">
    	<option value="t_avatar_url">头像</option>
    	<option value="t_image_url">图片</option>
    	<option value="t_voice_url">语音</option>
    	<option value="t_vedio_url">视频</option>
	</select>
	&nbsp;&nbsp;&nbsp;&nbsp;
   	 时间范围：
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