<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="/tags" prefix="date"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>密码编辑</title>
<script type="text/javascript" src="../js/jquery-1.2.6.pack.js"></script>
<style type="text/css">
	html {
		overflow-y: scroll;
	}
</style>
<script type="text/javascript">
	function editAppInfo() {
		var appName = $("#appName").val();
		var appKey = $("#appKey").val();
		var appDesc = $("#appDesc").val();
		if(appName){			
           	if(appKey){
           		return true;		    	
   		    }else{	        
   		        alert("appKey名称不能为空！");
   		        return false;
   		    }		
		}else{
			alert("appName不能为空！");
	        return false;
		}
	}
</script>
<style type="text/css">
	body{margin:0; padding:0; font-size:12px; font-family:"宋体"; color:#000000; background-color:#f4f4f4;}
	a {
		text-decoration: none;
	}
	.emStyle{color: red;padding-left: 5px;display:none}
</style>
</head>
<body>
	<h3>&nbsp;&nbsp;修改APP信息</h3>
	<div style="padding-left: 200px;padding-top: 30px">
		<form method="get" id="editeditAppInfo" onsubmit="editAppInfo()" action="updateAppInfo.do">
			<input type="hidden" name="appId" id="appId" value="${appInfo.appId}"/>
			<table>
				<c:if test="${!empty message}">
					<tr height="30px">
						<td>&nbsp;&nbsp;</td>
						<td><font color="red">${message}</font></td>
					</tr>
			    </c:if>
			    <tr height="30px">
					<td><em style="color: red;padding-right: 5px;">*</em>APPID：</td>
					<td><input type="text" style="width: 300px;"  disabled value="${appInfo.appId}"/></td>					
				</tr>
				<tr height="30px">
					<td><em style="color: red;padding-right: 5px;">*</em>APP名称：</td>
					<td><input type="text" name="appName" id="appName" style="width: 300px;" value="${appInfo.appName}"/></td>
				</tr>
				<tr height="30px">
					<td><em style="color: red;padding-right: 5px;">*</em>APPKey：</td>
					<td><input type="text" name="appKey" id="appKey" style="width: 300px;" value="${appInfo.appKey}"/></td>
				</tr>				
				<tr>
		            <td align="right" class="l-table-edit-td">APP描述:</td>
		            <td align="left" class="l-table-edit-td" colspan="2"> 
		            <textarea class="l-textarea" name = "appDesc" id="appDesc" style="width: 400px;" rows="4" cols="100" >${appInfo.appDesc }</textarea>
		       	</td> 
        </tr>
		</table>		
		<table style="width:250px">
			<tr height="30px">
				<td align="center">
					<br/>
					<input type="submit" value="提  交" />
					<input type="button" onClick="history.go(-1);" value="返  回" />
				</td>
			</tr>
		</table>
		</form>
	</div>
	<br>
</body>
</html>