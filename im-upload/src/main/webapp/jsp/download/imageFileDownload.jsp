<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>ImageFileDownload</title>
</head>
<body>
<fieldset>
     <legend> 上传单个文件  </legend>
     <form action="${pageContext.request.contextPath}/ImageDownloadServlet.do"
         method="post" enctype="multipart/form-data">         
         currentTime:<input type="text" name="currentTime" value="20160324153323">
         traceId:<input type="text" name="traceId" value="ttttttttt">
         appId:<input type="text" name="appId" value="appIdappIdappId">
         key:<input type="text" name="key" value="noKey">
         uid:<input type="text" name="uid" value="11111">
         <hr/>
                上传文件：	<input type="file" name="file">
                <br>
                <input type="submit" value="上传">
            </form>
        </fieldset>
</body>
</html>