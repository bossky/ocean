<%@page contentType="text/html; charset=UTF-8" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %> 
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
	<title>我的话题</title>
	<link rel="stylesheet" href="../css/commom.css" type="text/css" />
	<link rel="stylesheet" href="../css/head.css" type="text/css" />
	<link rel="stylesheet" href="../css/nav.css" type="text/css" />
	<link rel="stylesheet" href="../css/main.css" type="text/css" />
	<script type="text/javascript" src="/js/jquery-1.8.2.min.js"></script>
	<script type="text/javascript" src="/js/common.js"></script>
	<script>
		//随机生成标签颜色 
		$(function(){
			$(".art_lab a").each(function(){
			var a=Math.ceil(Math.round(Math.random()*5));
				var color="color"+a;
				$(this).addClass(color);
			});
		});
	</script>
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
				<div class="mgt50 textcenter gray">您目前还没有发表过话题...</div>
			</c:if>
			<c:if test="${list!=null&&list.count>0}">
			<h3>我的话题</h3>
			<c:forEach items="${list}" var="t">
			<div class="art">
				<h3 class="art_title">
				<c:set var="a" value='"' />
				<a href="/content.jspx?id=${t.id }&&op=visit" title="${fn:replace(t.title,a,'&quot;')}"  target="_blank"><c:out value="${t.title}"/></a></h3>
				<p class="art_author">作者：
				<a href="/author.jspx?id=${t.user.id }"><c:out value="${t.user.nickName }"/> </a>
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
						<a href="/themes.jspx?labelId=${l.id }"><c:out value="${l.name}"/></a>
						</c:forEach>
						<c:if test="${fn:length(t.labelList)>3}">
						<span>...</span>
						</c:if>
					</span>
						<span class="art_read fr">
						<span class="art_Num">浏览(${t.viewNum})&nbsp;
						评论(${t. commentsNum})&nbsp;
						收藏(${t.collectNum })&nbsp;
						赞(${t.praiseNum })</span>
						<a href="/content.jspx?id=${t.id }&&op=visit" target="_blank">阅读全文</a>	
						<a href="javascript:showLabel('${t.id }','${t.labelStr}');" >调整标签</a></span>				
				</p>
			</div>
			</c:forEach>
			<form action="mytheme.jspx" method="post" name="form1" id="form1">
				<input type="hidden" name="p" id="p" value="${list.page }"/>
				<input type="hidden" name="id" id="t_id" value=""/>	
				<input type="hidden" name="op" id="op" value=""/>	
				<jsp:include page="selectlabel.jspx"/>
			</form>
			
			<div class="page"><jsp:include page="../pagebar.jsp"></jsp:include></div>
			</c:if>
		</div>
	</div>
	<jsp:include page="/foot.jsp"></jsp:include>
	<script>
	
	//显示标签选择框
	function showLabel(id,label){
		if("null"!=label&&0!=$.trim(label).length){
			label=label.substring(1,label.length-1);
			labelArr=label.split(",");
			for(var i=0;i<labelArr.length;i++){
				document.getElementById($.trim(labelArr[i])).checked =true;	
			}
		}
		$("#overlayInLabel").show();
		$("#labelDiv").show();
		$("#t_id").val(id);
		$("#op").val("update");
		
	}
	//隐藏标签选择框
	function hideLabel(){
		$("#overlayInLabel").hide();
		$("#labelDiv").hide();
		$("#t_id").val("");
		$("#op").val("");
	}
	$(function(){
		$("#confirmButton").click(function(){
			form1.submit();
		});
	});
	<c:if test="${msg!=null}">
	showMsg("${msg }");
	</c:if>
	<c:if test="${param.msg!=null}">
	showMsg("${param.msg }");
	</c:if>
	</script>
</body>
</html>
