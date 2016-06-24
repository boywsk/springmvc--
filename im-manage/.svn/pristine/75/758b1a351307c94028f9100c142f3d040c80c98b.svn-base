<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<style type="text/css">
       body{ font-size:12px;}
    .l-table-edit {}
    .l-table-edit-td{ padding:4px;}
    .l-button-submit,.l-button-test{width:80px; float:left; margin-left:10px; padding-bottom:2px;}
    .l-verify-tip{ left:230px; top:120px;}
    #errorLabelContainer{ padding:10px; width:300px; border:1px solid #FF4466; display:none; background:#FFEEEE; color:Red;}
</style>
<script type="text/javascript">
	function check(){
		var appId = document.getElementById("appId").value;
		var appName = document.getElementById("appName").value;
		if(appId){
			if(appId.length <32){
				//var Regx = /^[^_][A-Za-z0-9_][^_]*$/;
				var Regx = /^[a-zA-Z0-9]\w{5,32}$/;
	            if (Regx.test(appId)) {
	            	if(appName){
	    		    	return true;//不写此返回值也行，此时就直接提交了
	    		    }else{	        
	    		        alert("APP名称不能为空！");
	    		        return false;
	    		    }
	            }else {
		            	alert("APPID只能是数据、字母、下划线组成，且开头、结尾不能为下划线 !");
		                return false;
		            }	
            }else{
	    			alert("APPID太长，最长为32位 !");
	                return false;
	 		}					
		}else{
			alert("APPID不能为空！");
	        return false;
		}
	}
</script>
<title>增加APP信息</title>
</head>
<body>
<form onsubmit="return check()" action="addAppInfo.do"  method="post">
<div style="text-align:center;clear:both;">
	<c:if test="${!empty message}">
    	<font color="red" size="5" >【${message}】</font>
    </c:if>
</div>
   <table class="l-table-edit" cellspacing="0" cellpadding="0">
       <tbody>
       	<tr>
            <td align="right" class="l-table-edit-td">APPID:</td>
            <td align="left" class="l-table-edit-td" style="width: 160px;">
            	<input name="appId" id="appId" style="width: 174px;" type="text" >
            </td>
            <td align="left"><font color = "red">*不可为空,上传后不可修改。最长32位！</font></td>
        </tr>
        <tr>
            <td align="right" class="l-table-edit-td">APP名称:</td>
            <td align="left" class="l-table-edit-td" style="width: 160px;">
            	<input name="appName" id="appName" style="width: 174px;" type="text" >
            </td>
            <td align="left"><font color = "red">*不可为空,上传后不可修改</font></td>
        </tr>
        <tr>
            <td align="right" class="l-table-edit-td">APP描述:</td>
            <td align="left" class="l-table-edit-td" colspan="2"> 
            <textarea class="l-textarea" name = "appDesc" id="appDesc" style="width: 400px;" rows="4" cols="100" ></textarea>
            </td> 
        </tr>
   	</tbody>
   </table>
 <br>
<input class="l-button l-button-submit" type="submit" value="提交" />
<input class="l-button l-button-test" type="button" onClick="history.go(-1);" value="返  回" />
</form>
</body>
</html>