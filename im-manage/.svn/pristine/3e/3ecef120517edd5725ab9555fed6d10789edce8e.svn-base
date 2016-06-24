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
	/* function checkForm(){
		var uId = document.getElementById("uId").value;		
		var uName = document.getElementById("uName").value;
		if(uId){
			if(!isNaN(uId)){
				if(uId.length > 9){
					if(uId.length <32){
		            	if(uName){
		    		    	return true;//不写此返回值也行，此时就直接提交了
		    		    }else{	        
		    		        alert("uName不能为空！");
		    		        return false;
		    		    }
		            }else{
			    			alert("uId太长，最长为32位 !");
			                return false;
			 		}
				}else{
					alert("uId太短，不小于2000000000! ");
	                return false;
				}
			}else{
				alert("uId只能是数字！ ");
				return false;
			}
		}else{
			alert("uId不能为空！");
	        return false;
		}
	} */
</script>
<title>增加APP信息</title>
</head>
<body>
<form onsubmit="return checkForm()" action="addAppSysAccount.do"  method="post">
<div style="text-align:center;clear:both;">
	<c:if test="${!empty message}">
    	<font color="red" size="3" >${message}</font>
    </c:if>
</div>
   <table class="l-table-edit" cellspacing="0" cellpadding="0">
       <tbody>
       	<tr>
            <td align="right" class="l-table-edit-td">选择APPID:</td>
            <td align="left" class="l-table-edit-td" style="width: 200px;">
            	<select name = "appId" id = "appId" style="width: 179px;">
					<c:forEach items="${appIdList}" var="data">
			             <option value="${data.appId}">${data.appId}</option>
			     	</c:forEach>
				</select>
            </td>
            <td align="left"></td>
        </tr>
        <tr>
            <td align="right" class="l-table-edit-td">uId:</td>
            <td align="left" class="l-table-edit-td" style="width: 160px;">
            	<input name="uId" id="uId" style="width: 174px;" type="text" >
            </td>
            <td align="left"><font color = "red">*不可为空，全数字且大于2000000000（若多个Id，请用英文逗号“,”分开）</font></td>
        </tr>
        <tr>
            <td align="right" class="l-table-edit-td">uName:</td>
            <td align="left" class="l-table-edit-td" style="width: 160px;">
            	<input name="uName" id="uName" style="width: 174px;" type="text" >
            </td>
            <td align="left"><font color = "red">*不可为空，上传后不可修改（若多个，请与上Id对应，描述项同理）</font></td>
        </tr>
        <tr>
            <td align="right" class="l-table-edit-td">描述:</td>
            <td align="left" class="l-table-edit-td" colspan="2"> 
            <textarea class="l-textarea" name = "uDesc" id="uDesc" style="width: 400px;" rows="4" cols="100" ></textarea>
            </td> 
        </tr>
   	</tbody>
   </table>
 <br>
 &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
<input class="l-button l-button-submit" type="submit" value="提交" />
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
<input class="l-button l-button-submit" type="button" onClick="history.go(-1);" value="返  回" />
</form>
</body>
</html>