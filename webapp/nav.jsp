<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>

</head>
<body>
	<div class="comWidth">
		<!-- banner -->
		<div id="banner"><h1><a href="/">Bossky</a></h1></div>

		<!-- nav -->
		<div class="nav mgt">
			<ul>
				<li ><a id="navindex"  href="/" ><span>主页</span></a></li>
				<li><a   id="navlabel" href="/labels.jspx"><span>标签浏览</span></a></li>
				<li><a   id="navtheme" href="/themes.jspx"><span>所有话题</span></a></li>
				<c:if test="${sessionScope.user.admin}">
				<li><a id="navusersmag" href="/admin/userlist.jspx"><span>用户管理</span></a></li>
				<li><a id="navlabelmag"  href="/admin/labellist.jspx"><span>标签管理</span></a></li>
				<li><a  id="navthememag" href="/admin/themelist.jspx"><span>话题管理</span></a></li>
				<li><a id="navcmtmag" href="/admin/commentlist.jspx"><span>评论管理</span></a></li>
				<li><a id="navrpmag"  href="/admin/replylist.jspx"><span>回复管理</span></a></li>
				</c:if>
				
			</ul>
		</div>
	</div>
</body>
</html>