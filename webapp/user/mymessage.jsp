<%@page contentType="text/html; charset=UTF-8" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %> 

<!DOCTYPE html>
<html>
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
	<title>我的消息</title>
	<link rel="stylesheet" href="../css/head.css" type="text/css" />
	<link rel="stylesheet" href="../css/nav.css" type="text/css" />
	<link rel="stylesheet" href="../css/main.css" type="text/css" />
	<link rel="stylesheet" href="../css/commom.css" type="text/css" />
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
			</div>
		</div>
		<div class="theam_r fr">
		<c:if test="${list==null||list.count==0}">
			<div class="mgt50 textcenter gray">目前还没有消息...</div>
		</c:if>
		<c:if test="${list!=null&&list.count>0}">
			<h3>我的消息</h3>
				<c:forEach items="${list}" var="m">
				<c:if test="${m.type==0}">
				<c:set var="a" value='"' />
				<div class="pinglun">
				<p><a href="/author.jspx?id=${m.sender.id }" target="_blank">
				<c:out value="${m.sender.nickName }"/></a> &nbsp;&nbsp;
				评论了你的话题&nbsp;&nbsp;
				<a href="/content.jspx?id=${m.theme.id }&&op=visit" title="${fn:replace(m.theme.title,a,'&quot;')}" target="_blank">
					<c:choose>
							<c:when test="${fn:length(m.theme.title) > 30}"> 
						      <c:out value="${fn:substring(m.theme.title, 0, 30)}"/>...
						    </c:when> 
						    <c:otherwise> 
							  <c:out value=" ${m.theme.title}"/>
					 		</c:otherwise> 
					 </c:choose>
					</a>
				</p>
				<p class="art_author">评论时间：<fmt:formatDate value="${m.createDate}" pattern='yyyy-MM-dd HH:mm:ss' /></p>	
				</div>
				</c:if>
				<c:if test="${m.type==1}"> 
				<div class="huifu">
				<p><a href="/author.jspx?id=${m.sender.id }" target="_blank">
				<c:out value="${m.sender.nickName }"/></a> &nbsp;&nbsp;
				在<c:set var="a" value='"' />
				<a href="/content.jspx?id=${m.theme.id }&&op=visit" title="${fn:replace(m.theme.title,a,'&quot;')}" target="_blank">  
					<c:choose>
						<c:when test="${fn:length(m.theme.title) > 25}"> 
						      <c:out value="${fn:substring(m.theme.title, 0, 25)}"/>...
						    </c:when> 
						    <c:otherwise> 
							  <c:out value=" ${m.theme.title}"/>
							 </c:otherwise> 
					 </c:choose>
				</a>
				&nbsp;&nbsp;话题中回复了您</p>
				<p class="art_author">回复时间：<fmt:formatDate value="${m.createDate}" pattern='yyyy-MM-dd HH:mm:ss' /></p>	
				</div>
				</c:if> 
				</c:forEach>
				<form action="mymessage.jspx" method="post" name="form1" id="form1">
			<input type="hidden" name="p" id="p"/>
			</form>
			<div class="page"><jsp:include page="../pagebar.jsp"></jsp:include></div>
			</c:if>
		</div>
	</div>
	
	<jsp:include page="/foot.jsp"></jsp:include>
</body>
</html>
