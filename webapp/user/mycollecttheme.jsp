<%@page contentType="text/html; charset=UTF-8" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %> 
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
	<title>我的收藏</title>
	<link rel="stylesheet" href="../css/commom.css" type="text/css" />
	<link rel="stylesheet" href="../css/head.css" type="text/css" />
	<link rel="stylesheet" href="../css/nav.css" type="text/css" />
	<link rel="stylesheet" href="../css/main.css" type="text/css" />
	<script type="text/javascript" src="../js/jquery-1.8.2.min.js"></script>
	<script type="text/javascript" src="../js/common.js"></script>
</head>
<body>
<jsp:include page="/head.jsp"></jsp:include>
<jsp:include page="/nav.jsp"></jsp:include>
<div class="comWidth clearfix mgt">
		<div class="theam_l fl">
			<div class="theam_user">
				<p>昵称：<c:out value="${sessionScope.user.nickName}"/></p>
				<p>QQ：<c:out value="${sessionScope.user.QQ}"/></p>
				<p>邮箱：<c:out value="${sessionScope.user.email}"/></p>
				<p>培训日期：<fmt:formatDate value="${sessionScope.user.trainingDate}" pattern='yyyy-MM-dd' /></p>
			</div>
		</div>
		<div class="theam_r fr">
			<c:if test="${list==null||list.count==0}">
				<div class="mgt50 textcenter gray">您目前还没有收藏过话题...</div>
			</c:if>
			<c:if test="${list!=null&&list.count>0}">
			<h3>我的收藏</h3>
			<c:forEach items="${list}" var="c">
			
			<div class="art">
				<h3 class="art_title">
				<c:set var="a" value='"' />
				<a href="/content.jspx?id=${c.theme.id }&&op=visit" title="${fn:replace(t.title,a,'&quot;')}" target="_blank">
				<c:out value="${c.theme.title}"/>
				</a>
				</h3>
				
				<p class="art_author">作者：
				<a href="/author.jspx?id=${c.theme.user.id }" target="_blank">
				<c:out value="${c.theme.user.nickName } "/></a>
				发表时间：<fmt:formatDate value="${c.theme.createDate}" pattern='yyyy-MM-dd HH:mm:ss' />
				</p>
				<p class="art_cont">
				</p>
				<p class="art_btm clearfix">
				收藏于：<fmt:formatDate value="${c.createDate}" pattern='yyyy-MM-dd HH:mm:ss' />			
				&nbsp;&nbsp;
				<span class="art_read fr">
				<a href="/content.jspx?id=${c.theme.id }&&op=visit" target="_blank">阅读全文</a>
				<a href="cannalcollcet.jspx?id=${c.theme.id}">取消收藏</a>&nbsp;&nbsp;
				</span>
				</p>
			</div>
			</c:forEach>
			<form action="mycollecttheme.jspx" method="post" name="form1" id="form1">
			<input type="hidden" name="p" id="p"/>
			</form>
			<div class="page"><jsp:include page="../pagebar.jsp"></jsp:include></div>
			</c:if>
		</div>
	</div>
	<jsp:include page="/foot.jsp"></jsp:include>
	<script>
	<c:if test="${param.msg!=null}">
	showMsg("${param.msg }");
	</c:if>
	</script>
</body>
</html>
