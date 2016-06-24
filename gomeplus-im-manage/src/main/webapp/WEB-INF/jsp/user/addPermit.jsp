<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="/tags" prefix="date"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html>
<head>
<META HTTP-EQUIV="pragma" CONTENT="no-cache"> 
<title>用户授权</title>
<link href="../css/ligerui-all.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="../js/jquery-1.2.6.pack.js"></script>
<!-- script src="js/jquery-1.4.4.min.js" type="text/javascript"></script -->
<script src="../js/ligerUI/base.js" type="text/javascript"></script>
<script src="../js/ligerUI/ligerGrid.js" type="text/javascript"></script>
<script src="../js/ligerUI/ligerDrag.js" type="text/javascript"></script>
<script src="../js/ligerUI/ligerResizable.js" type="text/javascript"></script>
<style type="text/css">
	html {
		overflow-y: scroll;
	}
	body{margin:0; padding:0; font-size:12px; font-family:"宋体"; color:#000000; background-color:#f4f4f4;}
	a {
		text-decoration: none;
	}
	.emStyle{color: red;padding-left: 5px;display:none}
	.compress {
		color: rgb(51, 51, 51);
		font-size: 1.35em;
		padding-left: 0.2em;
		padding-right: 0.2em;
		float: left;
	}
</style>

<script type="text/javascript">
	
	var checkedCustomer = [];
	var manager;
	$(function() {
		window['g'] = manager = $("#maingrid").ligerGrid({
			columns : [ {
				display : '名称',
				name : 'name',
				width : 250,
				align : 'left'
			}, {
				display : '标示',
				name : 'id',
				id : 'id1',
				width : 250,
				type : 'int',
				align : 'left'
			},

			{
				display : '地址',
				name : 'url',
				width : 250,
				align : 'left'
			} ],
			//width : '100%',
			//height : '97%',
			allowHideColumn : false,
			checkbox : true,
			usePager : false,
			rownumbers : false,
			alternatingRow : false,
			tree : {
				columnName : 'name'
			},
			url : "listMenu.do?uid=${uid}&random=" + (new Date()).valueOf(),
			method : "get",
			isChecked : f_isChecked,
			//onCheckRow : f_onCheckRow,
			onSelectRow : f_onSelectRow,
			onUnSelectRow: f_onUnSelectRow,
			onAfterShowData: f_onAfterShowData//,
			//onBeforeShowData: f_onBeforeShowData
		});
		//var tr = manager.getRow("maingrid|2|r1001");
		//var trs = $("#maingrid|2|r1001");
		//trs.addClass(" l-selected");
		//alert(tr);
	});

	function f_isChecked(rowdata) {
		//alert(JSON.stringify(rowdata));
		//alert(rowdata.selected);
		//alert(rowdata['__hasChildren']);
		if (rowdata.selected == 1) {
			//alert(rowdata.id);
			//var rowParent = manager.getParent(rowdata);
			//var domId = manager._getRowDomId(rowParent,false);
			//alert(window['g']);
			return true;
		}

		return false;
	}

	//function f_onCheckRow(checked, data) {
		//if (checked) {
		//alert(data.id);
		//}
		//if (checked) addCheckedCustomer(data.CustomerID);
		//else removeCheckedCustomer(data.CustomerID);
	//}

	function f_onSelectRow(rowdata, rowindex, rowDomElement) {
		checkedCustomer.push(rowdata.id);
		$("#menu_ids").val(checkedCustomer);
		//alert(manager);
		//var td = document.getElementById("maingrid|2|r1001");
		//alert(rowdata.id);
		//alert(JSON.stringify(rowdata));

		//if (rowdata['__hasChildren']) {
			//var rowParent = manager.getParent(rowdata);
			//var domId = manager._getRowDomId(rowdata, false);
			//parentSelect(domId);
			//alert(rowdata['__hasChildren']);
			//var pTrId = "maingrid|2|" + rowindex;
			//var tr = document.getElementById(domId);
			//var tr = $("#maingrid|2|r1001");
			//$(".l-grid-row");
			//alert($(".l-grid-row").html());
			//tr.className="l-grid-row l-selected";

			//checkedCustomer.push(domId);
			//alert(pTrId);
		//}
	}
	
	function f_onUnSelectRow(rowdata, rowindex, rowDomElement) {
		for(var i = 0; i < checkedCustomer.length; i++) {
			if(checkedCustomer[i] == rowdata.id) {
				checkedCustomer.splice(i,1);
			}
		}
		$("#menu_ids").val(checkedCustomer);
		//alert(checkedCustomer);
	}
	
	function f_onAfterShowData(rowdata) {
		//alert(checkedCustomer);
		$("#menu_ids").val(checkedCustomer);
		//for(var i = 0;i < checkedCustomer.length; i++) {
		//	var tr = document.getElementById(checkedCustomer[i]);
		//	tr.className="l-grid-row l-selected";
		//}
		//alert(JSON.stringify(rowdata));
		//alert($("#menu_ids").val());
	}
	
	//function f_onBeforeShowData(rowdata) {
		//alert(JSON.stringify(rowdata));
	//}
	
	$(document).ready(function() {
		
		//alert(checkedCustomer);
		//for(var i = 0;i < checkedCustomer.length; i++) {
		//	var tr = document.getElementById(checkedCustomer[i]);
		//alert(tr.innerHTML);
		//	tr.className="l-grid-row l-selected";
		//if(checkedCustomer[i] == CustomerID) return i;
		// }

		//alert(manager);
		//$(".btn1").click(function() {
		//	$("p").slideToggle();
		//});
		//var td = $("#maingrid|2|r1001");
		//var td = document.getElementById("maingrid|2|r1001"); 
		//alert(td.innerHTML);
		//var clazz = td.getAttribute("class");
		//td.className="l-grid-row l-selected";
		//alert(td.className);
	});
</script>
</head>
<body  style="padding:4px">
    <div id="maingrid"></div> 
<div>

<table width="100%">
	<tr align="center" height="30px">
		<td style="padding-left: 3px;">
			<form method="post" id="permitForm" action="addPermit.do">
				<input id="menu_ids" name="menusIds" value="" type="hidden"/>
				<input id="uid" name="uid" value="${uid}" type="hidden"/>
				<input class="compress" type="submit" value="提 交" />
				<input class="compress" type="button" onClick="history.go(-1);" value="返 回" />
			</form>
		</td>
	</tr>
</table>
</div>
</body>
</html>