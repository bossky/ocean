<%@page contentType="text/html; charset=UTF-8" session="false"%>
<%@page import="com.ourlinc.tern.ResultPage" %>
<%@page import="com.bossky.ocean.theme.Label" %>
<%@page import="java.util.*" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html >
<html >
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>标签汇</title>
<meta name="author" content="ourlinc" />
<meta name="copyright" content="Copyright 2008 Ourlinc,Co.,Ltd" />
<link rel="stylesheet" href="css/head.css" type="text/css">
<link rel="stylesheet" href="css/commom.css" type="text/css">
<link rel="stylesheet" href="css/nav.css" type="text/css">
<link rel="stylesheet" href="css/main.css" type="text/css">
<link rel="stylesheet" type="text/css" href="css/label.css" />
<script type="text/javascript" src="js/jquery-1.8.2.min.js"></script>
<script type="text/javascript">
	//随机生成标签颜色
	$(function(){
		$(".tree .onetree").each(function(){
			var a=Math.ceil(Math.round(Math.random()*5));
			var color="color"+a;
			$(this).find("a").addClass(color);
		});
		$("#navlabel").addClass("selected");
		
	});
</script>
</head>
<body>
	<jsp:include page="head.jsp"></jsp:include>
	<jsp:include page="nav.jsp"></jsp:include>
	<div class="comWidth clearfix mh500 mgt">
			<c:if test="${empty labelList}">
				<div class="mgt50 textcenter gray">还没有建立标签...</div>
			</c:if>
			<c:if test="${!empty labelList}">
			<h3>结构式<span style="color:gray;font-weight:normal;font-size:14px;">&nbsp;&nbsp;(提示：树形结构最多只显示4层)</span></h3>
			
			<div class="tree  clearfix" >
			<c:forEach items="${labelList}" var="l">
				<c:if test="${null==l.parentId }">
				<div class="onetree">
					<a class="boot" href="themes.jspx?labelId=${l.id }">
					<c:out value="${l.name}"/>(${l.themeNum })</a>
					<c:if test="${null!=l.childList }">
					<ul class='child'>
					<c:forEach items="${l.childList }" var="c">
						<li><a class='boot' href='themes.jspx?labelId=${c.id }'>
							<div class='hxian'></div>${c.name}(${c.themeNum })</a>
									<c:if test="${null!=c.childList }">
									<ul class='child'>
									<c:forEach items="${c.childList }" var="cc">
									<li><a class='boot' href='themes.jspx?labelId=${cc.id }'>
									<div class='hxian'></div>${cc.name}(${cc.themeNum })</a>
										<c:if test="${null!=cc.childList }">
										<ul class='child'>
										<c:forEach items="${cc.childList }" var="ccc">
										<li><a class='boot' href='themes.jspx?labelId=${ccc.id }'>
										<div class='hxian'></div>${ccc.name}(${ccc.themeNum })</a>
										</li>
										</c:forEach>
										</ul>
										</c:if>
									</li>
									</c:forEach>
									</ul>
									</c:if>
						</li>
					</c:forEach>
					</ul>
					</c:if>
				</div>
				</c:if>
			</c:forEach>
			</div>
			<h3>砖墙式</h3>
			<div class="brick mgt">
			<c:forEach items="${labelList}" var="l">
				<a href="themes.jspx?labelId=${l.id }" ><c:out value="${l.name}"/>(${l.themeNum })</a>
				</c:forEach>
		 	</div>
		 	</c:if>
			<%--
			<div class="tree mgt">
			<% java.util.List<Label> rp=(java.util.List<Label>)request.getAttribute("labelList");
			for(Label l:rp){
				if(null==l.getParentLabel()){
					%>
					<div class="onetree">
						<a class="boot" href="themes.jspx?labelId=<%=l.getId()%>" target="_blank">
						<%=htmlspecialchars(l.getName()) %>(<%=l.getThemeNum()%>)</a>
						<%=printChild(l)%>
					</div>
					<%
				}
			}%> 
	
		<%!	
		private  String printChild(Label label){
			StringBuilder sb=new StringBuilder();
			try{
				
				if(null!=label.getChildList()&&0!=label.getChildList().size()){ 
					sb.append("<ul class='child'>");
					for(Label child:label.getChildList()){
						sb.append("<li>");
						sb.append("<a class='boot' href='themes.jspx?labelId="+child.getId()+"' target='_blank'>"+
						"<div class='hxian'></div>"+htmlspecialchars(child.getName())+
						"("+child.getThemeNum()+")</a>");
						sb.append(printChild(child));
						sb.append("</li>");
					}
					sb.append("</ul>");
				}
			}catch(Exception e){
				
			}
			return sb.toString();
		}
		%>
		<%!
			private String htmlspecialchars(String str) {
				str = str.replaceAll("&", "&amp;");
				str = str.replaceAll("<", "&lt;");
				str = str.replaceAll(">", "&gt;");
				str = str.replaceAll("\"", "&quot;");
				return str;
			}
		%>
		</div>
		 --%>
	
	</div>
	<jsp:include page="foot.jsp"></jsp:include>
</body>
</html>