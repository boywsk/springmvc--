<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="/tags" prefix="date"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>用户列表</title>
<link href="../css/ligerui-all.css" rel="stylesheet" type="text/css">
<script src="../js/jquery-1.4.4.min.js" type="text/javascript"></script>
<script src="../js/ligerUI/base.js" type="text/javascript"></script>
<script src="../js/ligerUI/ligerGrid.js" type="text/javascript"></script>
<style>
	.inputText4 {
		background: none repeat scroll 0 0 #F5F7FD;
		border: 1px solid #B8BFE9;
		padding: 5px 7px;
		width: 160px;
		vertical-align: middle;
		height: 13px;
		margin: 0;
		list-style: none outside none;
	}
</style>
</head>
<script type="text/javascript">
	var grid = null;
	$(function() {
		//alert('sss');
		grid = $("#maingrid4").ligerGrid({
			columns : [ {
				display : '服务器ip',
				name : 'serverIp',
				align : 'left',
				width : 120
			}, {
				display : '服务器端口',
				name : 'serverPort',
				minWidth : 60
			}, {
				display : '服务类型',
				name : 'serverTypeName',
				width : 120,
			}, {
				display : '状态',
				name : 'statusDesc',
				width : 120,
			}, {
				display : 'CPU使用率',
				name : 'cpuRate',
				width : 120,
			}, {
				display : '内存使用率',
				name : 'memRate',
				minWidth : 140
			}, {
				display : '上报时间',
				name : 'formateTime',
				minWidth : 140
			},{
                    display: '操作', isAllowHide: false,
                    render: function (row)
                    {
                        //var html = '<a href="#" onclick="onedit(\'' + row.serverKey + '\',' + row.serverType + ')">统计</a>';
                        var html = '<a href="preServerStatistics.do?serverKey=' + row.serverKey + "&serverType=" +  row.serverType + '">统计</a>';
                        //html += '&nbsp;&nbsp;<a href="../online/preOnlineStatistics.do?appId=TEST_APP_ID">在线统计</a>';
                        return html;
                    }
                }
			],
			pageSize : 10,
			rownumbers : true,
			//usePager : false,
			pageSizeOptions : [10],
			//where : f_getWhere(),
			//data : $.extend(true, {}, CustomersData),
			width : '99%',
			//height : '95%'
			url : "logicList.do?random=" + (new Date()).valueOf(),
	        method : "get",
		});

		//$("#pageloading").hide();
	});
	function f_search() {
		//grid.options.data = $.extend(true, {}, CustomersData);
		//grid.loadData(f_getWhere());
		var serverIp = $("#txtKey").val();
		grid = $("#maingrid4").ligerGrid({
			columns : [ {
				display : '服务器ip',
				name : 'serverIp',
				align : 'left',
				width : 120
			}, {
				display : '服务器端口',
				name : 'serverPort',
				minWidth : 60
			},{
				display : '服务类型',
				name : 'serverTypeName',
				width : 120,
			}, {
				display : '状态',
				name : 'statusDesc',
				width : 120,
			},{
				display : 'CPU使用率',
				name : 'cpuRate',
				width : 120,
			}, {
				display : '内存使用率',
				name : 'memRate',
				minWidth : 140
			}, {
				display : '上报时间',
				name : 'formateTime',
				minWidth : 140
			}  ],
			pageSize : 10,
			rownumbers : true,
			usePager : false,
			pageSizeOptions : [],
			where : f_getWhere(),
			//data : $.extend(true, {}, CustomersData),
			width : '99%',
			//height : '95%'
			url : "logicList.do?serverIp="+ serverIp + "&random=" + (new Date()).valueOf(),
	        method : "get",
		});
	}
	function f_getWhere() {
		if (!grid)
			return null;
		var clause = function(rowdata, rowindex) {
			var key = $("#txtKey").val();
			return rowdata.serverIp.indexOf(key) > -1;
		};
		return clause;
	}
	
	function onedit(serverKey, type) {
        alert("您选中的serverKey是:" + serverKey + ";type:" + type);
    }
</script>

<div id="searchbar" style="margin:5px; padding: 0">
	服务器ip：<input id="txtKey" type="text" class="inputText4">
			<input id="btnOK" style="width: 60px;height: 28px" type="button" value="查  询" onclick="f_search()">
</div>

<div id="maingrid4" style="margin: 0; padding: 0"></div>
<div style="display: none;">
	<!-- g data total ttt -->
</div>
</body>
</html>