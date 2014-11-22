<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<script type="text/javascript" src="/js/jquery-1.8.2.min.js"></script>
<script type="text/javascript" src="/js/gotop.js"></script>
<link rel="stylesheet" href="/css/gotop.css" type="text/css">
<link rel="stylesheet" href="/css/login.css" type="text/css">
<script>
$(function(){
	$("#close").click(function(){
		$("#overlay").hide();
		$("#login").hide();
	});	
	$("#overlay").click(function(){
		$("#overlay").hide();
		$("#login").hide();
	});
	if(${'login'==param.op}){
		login();
		$("#url").val('${param.url}')
	}
});
//弹出登陆框的方法
function login(){
	$("#overlay").show();
	$("#login").show();
	$(".username").focus();
}

</script>
</head>
<body>
	<div class="topnav">
		<div class="comWidth">
			<div class="fl">
			<c:if test="${sessionScope.user!=null}">
			您好
			<a href="/user/mytheme.jspx"><c:out value="${sessionScope.user.nickName }"/></a> 
			，欢迎回来 Ocean ，一起技术交流！
			</c:if>
			</div>
			<div class="fr">
			<c:choose> 
			<c:when test="${sessionScope.user!=null}"> 
			<a href="/user/addtheme.jspx">发表话题&nbsp;</a>
			<c:if test="${sessionScope.user.newMessageNum==0}">
			<a href="/user/mymessage.jspx">我的消息</a>
			</c:if>
			<c:if test="${sessionScope.user.newMessageNum!=0}">
			<a href="/user/mymessage.jspx"><span class="red">我的消息(${sessionScope.user.newMessageNum})</span></a>
			</c:if>
			&nbsp;
			<a href="/user/mytheme.jspx">我的话题&nbsp;</a>
			<a href="/user/mycollecttheme.jspx">我的收藏&nbsp;</a>
			<a href="/user/info.jspx">个人信息&nbsp;</a>
			<a href="/user/updatepass.jspx">修改密码&nbsp;</a>
			<a href="/logout.jspx">退出</a>
			</c:when> 
			<c:otherwise> 
			<a  href="javascript:login();">登录</a>
			</c:otherwise> 
			</c:choose> 
			</div>
		</div>
	</div>
	<div id="overlay" class="overlay"></div>
	<div id="login"  class="login"><jsp:include page="login.jsp"></jsp:include></div>
</body>
</html>