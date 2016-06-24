<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="/tags" prefix="date"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>App信息列表</title>
<script type="text/javascript" src="../js/jquery-1.2.6.pack.js"></script>
<style type="text/css">
	html {
		overflow-y: scroll;
	}
	
	a.preview,a.preview:hover {
		text-decoration: none;
	}
	
	a.preview img {
		margin: 20px 10px;
	}
</style>
<script type="text/javascript">
	function search() {
		 if(event.keyCode == 13){ 
	         alert("this is a test!");
	     } 
		var uri = "listUser.do?pageNo=1";
		var uid = $("#uid").val();
		var userName = $("#userName").val();
		if(userName != ''){
			uri += "&userName=" + userName;
		}
		var name = $("#name").val();
		//alert(endTime);
		if(name != ''){
			uri += "&name=" + name;
		}
		location.href = uri;
	}
	
	function del(ids){
		if(ids == ""){
			ids = "0";
			$('[name=chk_list]:checkbox:checked').each(function(){
	            ids += "," + $(this).val();
	        });
			//alert(ids);
		}
		if(confirm("是否删除?") ){
			location.href = "delAppInfo.do?ids=" + ids;
		}else{
			return;
		}
	}
	$(function(){
	     //全选
	     $("#CheckedAll").click(function(){
	         $('[name=chk_list]:checkbox').attr("checked", this.checked);
	     });
	     $('[name=chk_list]:checkbox').click(function(){
	         //定义一个临时变量，避免重复使用同一个选择器选择页面中的元素，提升程序效率。
	         var $tmp=$('[name=items]:checkbox');
	         //用filter方法筛选出选中的复选框。并直接给CheckedAll赋值。
	         $('#CheckedAll').attr('checked', $tmp.length==$tmp.filter(':checked').length);
	     });
	});
</script>
<style type="text/css">
	body{margin:0; padding:0; font-size:12px; font-family:"宋体"; color:#000000; background-color:#f4f4f4;}
	a {
		text-decoration: none;
	}
	table {
		border: 1px;
		border-style: solid;
		border-color: gray;
		
		#font-family: "微软雅黑";
		text-align: center;
		
		margin: 0px;
		padding: 0px;
		border-collapse: collapse;
		padding: 0px;
	}
	tr:first-child {
		background-color: #AEC7E1;
	}
	tr {
		cursor: default;
	}
	td {
		#width: 100px;
		height:25px;
		border: 1px;
		border-style: solid;
		border-color: gray;
	}
	
</style>
</head>
<body>
	<h3>APP信息列表</h3>
	<%-- <div>
		APPID：<input type="text" name="name" id="name"  value = "${param.name}"/>
		APPName：<input type="text" name="userName" id="userName" value = "${param.userName}"/>
		<input type="button" onclick="javascript:search()" value="搜索" />
		<input type="button" onclick='location.href="preAddUser.do"' value="添加用户" />
	</div> --%>
	<br>
	<div>
		<table>
			<tr>
				<td width="6%">全选<input type="checkbox" name="CheckedAll" id="CheckedAll"></td>
				<td width="8%">APP名称</td>
				<td width="16%">APPID</td>
				<td width="25%">APPKey</td>
				<td width="22%">APP描述</td>
				<td width="13%">创建时间</td>
				<td width="10%">操作</td>
			</tr>
			<c:forEach var="member" items="${appInfo}" varStatus="status">
			<tr <c:if test="${status.index%2!=0 }"> style="background-color: #E5DBE2;"</c:if>>
				<td><input type="checkbox" name="chk_list" value="${member.appId}"></td>
				<td><c:out value="${member.appName}"/></td>
				<td><c:out value="${member.appId}" /></td>
				<td><c:out value="${member.appKey}" /></td>
				<td><c:out value="${member.appDesc}"/></td>
				<td>
				<date:date value="${member.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
				<a href="editAppInfo.do?appId=${member.appId}&appName=${member.appName}&appKey=${member.appKey}&appDesc=${member.appDesc}" class="del">修改</a>				
				<%-- <a href="javascript:void(0);" onclick='del(<c:out value="${member.appId}"/>);' class="del">删除</a> --%>
				</td>
			</tr>
			</c:forEach>
		</table>
	</div>
	<br>
	<div>
		<div>
		<!-- <input type="button" onclick="javascript:del('')" value="删除"/> 
		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; -->
		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		共  ${pageInfo.totalResult} 记录 
		<c:if test="${pageInfo.currentPage == 1}">
			首页
		</c:if>
		<c:if test="${pageInfo.currentPage > 1}">
		<a href="listUser.do?pageNo=1&userName=${param.userName}&name=${param.name}">首页</a>
		</c:if>
		<c:if test="${pageInfo.currentPage <= 1}">上一页</c:if>
		<c:if test="${pageInfo.currentPage > 1}">
		<a href="listUser.do?pageNo=${pageInfo.currentPage - 1}&userName=${param.userName}&name=${param.name}">上一页</a> 
		</c:if>
		<c:if test="${pageInfo.currentPage == pageInfo.totalPage}">
		下一页  
		</c:if>
		<c:if test="${pageInfo.currentPage < pageInfo.totalPage}">
		<a href="listUser.do?pageNo=${pageInfo.currentPage + 1}&userName=${param.userName}&name=${param.name}">下一页 </a>
		</c:if>
		<c:if test="${pageInfo.currentPage < pageInfo.totalPage}">
		<a href="listUser.do?pageNo=${pageInfo.totalPage}&userName=${param.userName}&name=${param.name}">末页 </a>
		</c:if>
		<c:if test="${pageInfo.currentPage == pageInfo.totalPage}">
		末页 
		</c:if>
		第 ${pageInfo.currentPage}/${pageInfo.totalPage} 页</div>
	</div>
</body>
</html>