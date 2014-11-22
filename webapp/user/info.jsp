<%@page contentType="text/html; charset=UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html >
<html >
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>个人信息</title>
<meta name="author" content="ourlinc" />
<meta name="copyright" content="Copyright 2008 Ourlinc,Co.,Ltd" />
<link rel="stylesheet" href="../css/head.css" type="text/css">
<link rel="stylesheet" href="../css/commom.css" type="text/css">
<link rel="stylesheet" href="../css/nav.css" type="text/css">
<link rel="stylesheet" href="../css/info.css" type="text/css">
<script type="text/javascript" src="../js/jquery-1.8.2.min.js"></script>
<script type="text/javascript" src="../js/common.js"></script>
<script>
//提交表单时检查数据的合法性
function infocheck(){
	if(0==$.trim($("#nickname").val()).length){
		showMsg("昵称不能为空!");
		return false;
	}
	if($.trim($("#signature").val()).length>200){
		showMsg("个性签名不能超过200个字!");
		return false;
	}
	if(isNaN($("#phone").val())){
		showMsg("手机号码只能为数字！");
		return false;
	}
	if(isNaN($("#QQ").val())){
		showMsg("QQ只能为数字！");
		return false;
	}
	if($("#phone").val().length>0&&$("#phone").val().length!=11){
		showMsg("手机号码长度错误！");
		return false;
	}
	return true;
}
</script>
</head>
<body>
	<jsp:include page="/head.jsp"></jsp:include>
	<jsp:include page="/nav.jsp"></jsp:include>
	<div class="comWidth clearfix mgt">
		<form method="post" action="info.jspx" onsubmit="return infocheck();" >
			<input type="hidden" value="update" name="op"/>
			<div class="accountinfo">
				<h3>账号信息</h3> 
				<div>
					<table class="infotable">
						<tr>
							<td  class="tdl">邮箱：</td><td class="tdr" >${sessionScope.user.email}</td>
							<td  class="tdl">手机：</td><td class="tdr" ><input   class="input"  type="text" id="phone"  maxlength="15"  name="phone"  value="${sessionScope.user.phone}"/></td>
							<td  class="tdl">培训日期：</td><td class="tdr" ><fmt:formatDate value="${sessionScope.user.trainingDate}" pattern='yyyy-MM-dd' /></td>
						</tr>
					</table>
				</div>
			</div>
			<div class="otherinfo mgt">
				<h3>个人信息</h3>
				<div > 
					<table class="infotable">
						<tr>
							<td class="tdl">昵称：</td><td class="tdr" ><input class="input"  maxlength="15"  id="nickname" type="text" name="nickname" value="<c:out value="${sessionScope.user.nickName}"/>" /><span class="red">&nbsp;*&nbsp;</span></td>
							<td class="tdl">QQ号：</td><td class="tdr" ><input  class="input"   maxlength="15" id="QQ" type="text" name="qqnum" value="<c:out value="${sessionScope.user.QQ}"/>"/></td>
							<td  class="tdl">最高学历：</td><td class="tdr"  ><input  class="input"  maxlength="15"  type="text" name="education" value="<c:out value="${sessionScope.user.education}"/>"/></td>
						</tr>
						<tr>
							<td class="tdl">毕业院校：</td><td class="tdr"><input class="input"  type="text" maxlength="30"  name="school" value="<c:out value="${sessionScope.user.school}"/>"/></td>
							<td class="tdl">工作职位：</td><td class="tdr"><input  class="input"  type="text" maxlength="20" name="job" value="<c:out value="${sessionScope.user.job}"/>"/></td>
						</tr>
						<tr>
							<td class="tdl signature">个性签名：</td>
							<td class="signature" colspan=5 ><textarea class="textarea tarea" id="signature"  rows="3" cols="80" name="signature"><c:out value="${sessionScope.user.signature}"/></textarea></td>
						</tr>
					</table>
				</div>
				<div class="submit">
					<input  type="submit" value="提交"   class="button" />
				</div>
			</div>
		</form>
	</div>
	<jsp:include page="/foot.jsp"></jsp:include>
	<c:if test="${param.msg!=null}">
		<script>
		showMsg("${param.msg }");
		</script>
	</c:if>
</body>
</html>