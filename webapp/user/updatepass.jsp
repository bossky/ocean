<%@page contentType="text/html; charset=UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html >
<html >
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>修改密码</title>
	<meta name="author" content="ourlinc" />
	<meta name="copyright" content="Copyright 2008 Ourlinc,Co.,Ltd" />
	<link rel="stylesheet" href="../css/head.css" type="text/css">
	<link rel="stylesheet" href="../css/commom.css" type="text/css">
	<link rel="stylesheet" href="../css/nav.css" type="text/css">
	<link rel="stylesheet" href="../css/info.css" type="text/css">
	<script type="text/javascript" src="/js/jquery-1.8.2.min.js"></script>
	<script type="text/javascript" src="/js/common.js"></script>
</head>
<body>
	<jsp:include page="/head.jsp"></jsp:include>
	<jsp:include page="/nav.jsp"></jsp:include>
	<div class="comWidth clearfix mgt">
	<h3 class="textcenter">修改密码</h3>
		<div class="patab textcenter">
			<form method="post" action="updatepass.jspx" onsubmit="return checkInput();">
				<input type="hidden" value="${sessionScope.user.id}" name="id">
				<input type="hidden" value="update" name="op">
				<table class="passtable">
					<tr class="h30">
						<td class="textright">用户名：</td>
						<td class="textleft"><c:out value="${sessionScope.user.userName}"/></td>
					</tr>
					<tr  class="h30">
						<td class="textright">原密码：</td><td><input class="input"  id="oldpass" type="password" name="oldpass"/></td>
					</tr>
					<tr  class="h30">
						<td class="textright">新密码：</td><td><input class="input"  id="newpass"  type="password" name="newpass"/></td>
					</tr>
					<tr  class="h30">
						<td class="textright">确认新密码：</td><td><input class="input"  id="renewpass"  type="password" name="renewpass"/></td>
					</tr>
					<tr class="h30"><td colspan=2><span class="gray">提示：密码长度要在6到16之间</span></td></tr>
					<tr>
						<td colspan=2 class="subt"><input type="submit" value="提交" class="button"></td>
					</tr>
				</table>
			</form>
		</div>
		
	</div>
	<jsp:include page="/foot.jsp"></jsp:include>
	<script>
	//提交表单时检查数据合法性
	function checkInput(){
		var oldpass=$("#oldpass").val();
		var newpass=$("#newpass").val();
		var renewpass=$("#renewpass").val();
		if(oldpass.length==0){
			showMsg("请输入原密码");
			return false
		}
		 if(newpass.length==0){
			showMsg("请输入新密码");
			return false;
		}
		 if(renewpass.length==0){
			showMsg("请输入重复新密码");
			return false;
		}
		if(newpass.length<6||newpass.length>16){
			showMsg("新密码长度只能在6到16位之间");
			return false;
		}
		if(renewpass.length<6||renewpass.length>16){
			showMsg("重复密码长度只能在6到16位之间");
			return false;
		}
		if(newpass!=renewpass){
			showMsg("新密码和重复密码不相同");
			return false;
		}
		return true;
	}
	<c:if test="${param.msg!=null}">
	showMsg("${param.msg }");
	</c:if>
	</script>
</body>
</html>