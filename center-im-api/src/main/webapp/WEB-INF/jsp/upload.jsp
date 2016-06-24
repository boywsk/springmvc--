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
	<h2>uploadImage test:</h2>
	<form action="/center-im-api/upload/uploadImage.json" method="post" enctype="multipart/form-data">
		<label for="file">file:</label>
		<input type="file" name="file" id="file" />
		token:<input type="text" name="token" id="token"  value = ""/>
		uid:<input type="text" name="uid" id="uid"  value = ""/>
		originalImage:<input type="text" name="originalImage" id="originalImage"  value = ""/>
		<input type="submit" name="submit" value="Submit" />
	</form>
	<h2>uploadVoice test:</h2>
	<form action="/center-im-api/upload/uploadVoice.json" method="post" enctype="multipart/form-data">
		<label for="file">file:</label>
		<input type="file" name="file" id="file2" />
		token:<input type="text" name="token" id="token2"  value = ""/>
		uid:<input type="text" name="uid" id="uid2"  value = ""/>
		<input type="submit" name="submit" value="Submit" />
	</form>
	<h2>uploadVedio test:</h2>
	<form action="/upload/uploadVideo.json" method="post" enctype="multipart/form-data">
		<label for="file">file:</label>
		<input type="file" name="file" id="file3" />
		token:<input type="text" name="token" id="token3"  value = ""/>
		uid:<input type="text" name="uid" id="uid3"  value = ""/>
		<input type="submit" name="submit" value="Submit" />
	</form>
	</body>
</html>
