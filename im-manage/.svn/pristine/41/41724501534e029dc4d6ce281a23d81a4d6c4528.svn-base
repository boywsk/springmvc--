<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="/tags" prefix="date"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html>
<head>
<title></title>
<link href="../css/ligerui-all.css" rel="stylesheet" type="text/css" />
<link href="../css/style.css" rel="stylesheet" type="text/css" />
<script src="../js/jquery-1.4.4.min.js" type="text/javascript"></script>
<script src="../js/ligerUI/base.js" type="text/javascript"></script>
<script src="../js/ligerUI/ligerLayout.js" type="text/javascript"></script>
<script src="../js/ligerUI/ligerGrid.js" type="text/javascript"></script>
<script src="../js/ligerUI/ligerTab.js" type="text/javascript"></script>
<script src="../js/ligerUI/ligerDialog.js" type="text/javascript"></script>
<script src="../js/ligerUI/ligerTree.js" type="text/javascript"></script>
<script src="../js/ligerUI/ligerAccordion.js" type="text/javascript"></script>
<script src="../js/ligerUI/ligerDrag.js" type="text/javascript"></script>
<script type="text/javascript">
	var navtab = null;
	$(function() {
		$("#layout1").ligerLayout({
			leftWidth: 200,
			heightDiff: -32,
			allowLeftResize: false
		});
		
        //$("#tab1").ligerTab();
        //navtab = $("#navtab1").ligerGetTabManager();
		//navtab = $("#navtab1").ligerTab(); 
		navtab = $("#framecenter").ligerTab({
	        height: "100%",
	        showSwitchInTab: true,
	        showSwitch: true,
	        dblClickToClose: true//,
	        //onAfterSelectTabItem: f_onAfterSelectTabItem
	    });
		
		$("#accordion1").ligerAccordion({
			//height : 300
			changeHeightOnResize: true
		});
	});
	
	function f_onAfterSelectTabItem(tabid) {
		navtab.reload(tabid);
		//alert(tabid);
	}
	
	function f_addTab(tabid, text, url, sessId) {
		if(url.indexOf('?') >= 0) {
			url += "&sid=" + sessId;
		} else {
			url += "?sid=" + sessId;
		}
		navtab.addTabItem({
            tabid: tabid,
            text: text,
            url: url
        });
    }
</script>
</head>
<body style="padding:0px;background:#EAEEF5;">
	<div id="pageloading"></div>
	<div id="topmenu" class="l-topmenu">
		<div class="l-topmenu-logo">&nbsp;&nbsp;IM管理后台</div>
		<div class="l-topmenu-welcome">
			<!-- a href="index.aspx" class="l-link2">服务器版本</a> <span class="space">|</span>
			<a href="https://me.alipay.com/daomi" class="l-link2" target="_blank">捐赠</a>
			<span class="space">|</span -->
			<span style="color: white;padding-right: 30px">[<c:out value="${user.name}"></c:out>；你好！]</span>
			<a href="../login/logout.do" class="l-link2">退出</a>
		</div>
	</div>
	<div id="layout1">
		<div position="left" title="主要菜单">
			<div id="accordion1"> 
				<c:forEach var="member" items="${menus}">
					<div title="${member.name}">
						<ul id="sub-menu">
							<c:forEach var="child" items="${member.children}">
								<li><a href="javascript:f_addTab('${child.id}','${child.name}','${child.url}','${sessionID}')">${child.name}</a></li>
							</c:forEach>
						</ul>
					</div>
				</c:forEach>
				<!--div title="其他" style="padding: 10px">其他内容</div -->
			</div>
		</div>

		<div id="framecenter" position="center">
			<div tabid="home" title="我的主页" lselected="true" style="height: 500px">
				<iframe frameborder="0" name="showmessage" src="../main.jsp"></iframe>
			</div>
		</div>
	</div>
	
	<div style="height:32px; line-height:32px; text-align:center;">
            Copyright © www.gome.com.cn
    </div>
    <div style="display:none"></div>
</body>
</html>