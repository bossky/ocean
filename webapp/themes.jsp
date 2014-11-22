<%@page contentType="text/html; charset=UTF-8" session="true"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %> 
<!DOCTYPE html >
<html >
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>所有话题</title>
<meta name="author" content="ourlinc" />
<meta name="copyright" content="Copyright 2008 Ourlinc,Co.,Ltd" />
<script type="text/javascript" src="/js/jquery-1.8.2.min.js"></script>
<link rel="stylesheet" href="css/head.css" type="text/css">
<link rel="stylesheet" href="css/commom.css" type="text/css">
<link rel="stylesheet" href="css/nav.css" type="text/css">
<link rel="stylesheet" href="css/main.css" type="text/css">
<script>
$(function(){
	$(".labs li").hover(function(){
		$(".labs li").css("background","");
		$(this).css("background","#F6F6F6");
	});
	$(".art_lab a").each(function(){
		var a=Math.ceil(Math.round(Math.random()*5));
			var color="color"+a;
			$(this).addClass(color);
	});
	$("#navtheme").addClass("selected");
});
</script>
</head>
<body>
	<jsp:include page="head.jsp"></jsp:include>
	<jsp:include page="nav.jsp"></jsp:include>
	<div class="comWidth clearfix">
		<div class="mgt">
			<div class="fl labels">
				<ul class="labs">
				<li  ><a id="time" class="labselected" href="themes.jspx"><span >最新发表</span></a></li>
				<li ><a id="comments" href="javascript:queryByType('comments');"><span>最多评论</span></a></li>
				<li ><a  id="view" href="javascript:queryByType('view');"><span>最多浏览</span></a></li>
				<li ><a id="collcet" href="javascript:queryByType('collcet')"><span>最多收藏</span></a></li>
				<li ><a  id="praise" href="javascript:queryByType('praise')"><span>最多人赞</span></a></li>
				<c:forEach items="${labelList}" var="l">
				<li >
					<a id="${l.id }"  href="javascript:queryByLabelId('${l.id }')"  >
						<span><c:out value="${l.name}"/>(${l.themeNum })</span>
					</a>
				</li>
				</c:forEach>
				</ul>
			</div>
			<div class="fr articles">
			<c:if test="${list==null||list.count==0}">
					<div class="textcenter gray mgt50">没有发表的话题...</div>
			</c:if>
			<c:forEach items="${list}" var="t">
				<div class="art">
					<h3 class="art_title" >
					<c:set var="a" value='"' />
					<a href="content.jspx?id=${t.id }&&op=visit" title="${fn:replace(t.title,a,'&quot;')}" >
					<c:out value="${t.title }"/></a></h3>
					<p class="art_author">
					作者：<a href="author.jspx?id=${t.user.id }" target="_blank"><c:out value="${t.user.nickName}"/></a>&nbsp;
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
							<a href="themes.jspx?labelId=${l.id }"><c:out value="${l.name}"/></a>
							</c:forEach>
							<c:if test="${fn:length(t.labelList)>3}">
							<span>...</span>
							</c:if>
						</span>
						<span class="art_read fr">
						<span class="art_Num">浏览(${t.viewNum})&nbsp;
						评论(${t. commentsNum})&nbsp;
						收藏(${t.collectNum })&nbsp;
						赞(${t.praiseNum } )</span>
						<a href="content.jspx?id=${t.id }&&op=visit" >阅读全文</a></span>
					</p>
				</div>
		</c:forEach>
		<form action="themes.jspx" method="post" name="form1" id="form1">
			<input type="hidden" name="p" id="p"/>
			<input type="hidden" name="type" id="type" value="${type }"/>
			<input type="hidden" name="labelId" id="labelId" value="${labelId }"/>
		</form>
			<c:if test="${list!=null&&list.count>0}">
				<div class="page"><jsp:include page="pagebar.jsp"></jsp:include></div>
			</c:if>
		</div>
		</div>
	</div>

	<jsp:include page="foot.jsp"></jsp:include>
	<script>
	//根据类型排序
	function queryByType(type){
		$("#type").val(type);
		$("#labelId").val('');
		form1.submit();
	}
	//根据标签查询
	function queryByLabelId(id){
		$("#labelId").val(id);
		$("#type").val('');
		form1.submit();
	}
	<c:if test="${null!=type&&fn:length(type)>0 }">
		document.getElementById('time').setAttribute("class","");
		document.getElementById('${type }').setAttribute("class","labselected");
	</c:if>
	<c:if test="${null!=labelId&&fn:length(labelId)>0  }">
		document.getElementById('time').setAttribute("class","");
		document.getElementById('${labelId}').setAttribute("class","labselected");
	</c:if>
	
	</script>
</body>
</html>