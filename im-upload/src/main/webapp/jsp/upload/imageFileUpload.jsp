<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Servlet3.0实现图片文件上传</title>
</head>
<body>
        <fieldset>
            <legend>
                上传单个图片文件
            </legend>
            <!-- 文件上传时必须要设置表单的enctype="multipart/form-data"-->
            <form action="${pageContext.request.contextPath}/ImageUploadServlet.do"
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
        <%-- <hr />
        <fieldset>
            <legend>
                上传多个文件
            </legend>
            <!-- 文件上传时必须要设置表单的enctype="multipart/form-data"-->
            <form action="${pageContext.request.contextPath}/UploadServlet"
                method="post">
                 <!-- enctype="multipart/form-data" -->
                <input type="hidden" name="uid" value="4321">
                <br>
                上传文件：
                <input type="file" name="file1">
                <br>
                上传文件：
                <input type="file" name="file2">
                <br>
                <input type="submit" value="上传">
            </form>
        </fieldset> --%>
    </body>
</html>