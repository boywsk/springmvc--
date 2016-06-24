<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <title>My JSP 'fileupload.jsp' starting page</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
  </head>
	<body>
		<form action="upload.do?uid=10000015&type=compress&thumb=thumb" enctype="multipart/form-data" method="post" >
			<input type="file" name="file1"><br/>
			<input type="file" name="file2"><br/>
			<input type="submit" value="提交"/>
		</form>
	</body>
</html>
