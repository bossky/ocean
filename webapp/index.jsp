<%@page contentType="text/html; charset=UTF-8" session="true"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %> 
<!DOCTYPE html >
<html >
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>首页</title>
<meta name="author" content="ourlinc" />
<meta name="copyright" content="Copyright 2008 Ourlinc,Co.,Ltd" />
<script type="text/javascript" src="/js/jquery-1.8.2.min.js"></script>
<link rel="stylesheet" href="css/commom.css" type="text/css">
<link rel="stylesheet" href="css/head.css" type="text/css">
<link rel="stylesheet" href="css/nav.css" type="text/css">
<link rel="stylesheet" href="css/main.css" type="text/css">
<script type="text/javascript" src="js/common.js"></script>
<script type="text/javascript" src="js/jquery-1.8.2.min.js"></script>
<script>
$(function(){
	$(".labs li").hover(function(){
			$(".labs li").not(":first").children("a").removeClass();
			$(this).children("a").addClass("labselected");
		},
		function(){
			$(".labs li").not(":first").children("a").removeClass();
		}
	);
	
	$(".art_lab a").each(function(){
		var a=Math.ceil(Math.round(Math.random()*5));
			var color="color"+a;
			$(this).addClass(color);
	});
	
	$("#navindex").addClass("selected");
});
</script>
</head>
<body>
	<jsp:include page="head.jsp"></jsp:include>
	<jsp:include page="nav.jsp"></jsp:include>
	<div class="comWidth clearfix">
		<div class="mgt">
			<div class="fl labels">
				<ul id="labs" class="labs">
				<li ><a class="labselected" href="themes.jspx" ><span>最新发表</span></a></li>
				<c:forEach items="${labelList}" var="l">
				<li >
					<a  class="labsa"   href="themes.jspx?labelId=${l.id }">
						<span><c:out value="${l.name}"/>(${l.themeNum })</span>
					</a>
				</li>
				</c:forEach>
				<c:if test="${labelList.count>10 }">
				<li >
					<a href="labels.jspx">
						<span>更多标签</span>
					</a>
				</li>
				</c:if>
				</ul>
			</div>
			<div class="fr articles">
			<c:if test="${themeList==null||themeList.count==0}">
					<div class="textcenter gray mgt50">没有发表的话题...</div>
			</c:if>
			<c:forEach items="${themeList}" var="t">
				<div class="art">	
					<c:set var="a" value='"' />
					<h3 class="art_title" title="${fn:replace(t.title,a,'&quot;')}">
					<a href="content.jspx?id=${t.id }&&op=visit"><c:out value="${t.title }"/></a></h3>
					<p class="art_author">作者：
					<a href="author.jspx?id=${t.user.id }" target="_blank"><c:out value="${t.user.nickName}"/> </a>
					&nbsp;
					发表时间：<fmt:formatDate value="${t.createDate}" pattern='yyyy-MM-dd HH:mm:ss' /></p>
					<p class="art_cont">
					<c:choose> 
					    <c:when test="${fn:length(t.noHTMLContent) > 200}"> 
					     ${fn:substring(t.noHTMLContent, 0, 200)}...
					    </c:when> 
					    <c:otherwise> 
					   	${t.noHTMLContent}
					    </c:otherwise> 
					</c:choose> 
					
   					</p>
						<p class="art_btm clearfix">
						<span class="art_lab fl">
							<c:forEach items="${t.labelList }" var="l" begin="0" end="2">
							<a  class="" href="themes.jspx?labelId=${l.id }" ><c:out value="${l.name}"/></a>
							</c:forEach>
							<c:if test="${fn:length(t.labelList)>3}">
							<span>...</span>
							</c:if>
						</span>
						<span class="art_read fr">
						<span class="art_Num">
						浏览(${t.viewNum})&nbsp;
						评论(${t. commentsNum})&nbsp;
						收藏(${t.collectNum })&nbsp;
						赞(${t.praiseNum})
						</span>
						<a href="content.jspx?id=${t.id }&&op=visit">阅读全文</a></span>
					</p>
				</div>
		</c:forEach>
		<c:if test="${themeList.count>10}">
			<div class="page"><a  href="themes.jspx">更多话题</a></div>
		</c:if>
		</div>
		</div>
	</div>
	<jsp:include page="foot.jsp"></jsp:include>
		<c:if test="${param.msg!=null}">
		<script>
		showMsg("${param.msg }");
		</script>
		</c:if>

</body>
</html>