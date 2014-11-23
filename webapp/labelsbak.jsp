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
	$(function(){
		$(".tree .onetree").each(function(){
			var a=Math.ceil(Math.round(Math.random()*5));
			var color="color"+a;
			$(this).find("a").addClass(color);
		});
	});
</script>
</head>
<body>
	<jsp:include page="head.jsp"></jsp:include>
	<jsp:include page="nav.jsp"></jsp:include>
	<div class="comWidth clearfix mh500 mgt">
		<div class="brick">
			<c:forEach items="${labelList}" var="l">
				<a href="themes.jspx?labelId=${l.id }"><c:out value="${l.name}"/>(${l.themeNum })</a>
				</c:forEach>
		</div>
			<div class="tree mgt">
			<% ResultPage<Label> rp=(ResultPage<Label>)request.getAttribute("labelList");
			rp.gotoPage(1);
			for(Label l:rp){
				if(null==l.getParentLabel()){
					%>
					<div class="onetree">
						<a class="boot" href="themes.jspx?labelId=<%=l.getId()%>">
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
				
				if(null!=label.getChildList()||0!=label.getChildList().size()){ 
					sb.append("<ul class='child'>");
					for(Label child:label.getChildList()){
						sb.append("<li>");
						sb.append("<a class='boot' href='themes.jspx?labelId="+child.getId()+"'>"+
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
	</div>
	<jsp:include page="foot.jsp"></jsp:include>
</body>
</html>