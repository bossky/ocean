<%@page contentType="text/html; charset=UTF-8" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="content-type" content="text/html; charset=utf-8" />
	<script type="text/javascript" src="../js/My97DatePicker/WdatePicker.js"></script>
	<link rel="stylesheet" href="../css/head.css" type="text/css" />
	<link rel="stylesheet" href="../css/nav.css" type="text/css" />
	<link rel="stylesheet" href="../css/table.css" type="text/css" />
	<link rel="stylesheet" href="../css/commom.css" type="text/css" />
	<script type="text/javascript" src="../js/jquery-1.8.2.min.js"></script>
	<script type="text/javascript" src="../js/common.js"></script>
<title>用户管理</title>
<script>
	$(function(){
		$("#navusersmag").addClass("selected");
	});
</script>
<style>
body{
	position:relative;
}
</style>
</head>
<body>
<jsp:include page="/head.jsp"></jsp:include>
<jsp:include page="/nav.jsp"></jsp:include>
	<div class="comWidth clearfix mgt mh400">
		<h3>用户管理</h3>
		<div class="mgt">
		<fieldset>
			<legend>注册新用户</legend>
			<form method="post" action="adduser.jspx" onsubmit="return checkInput();">
				<input type="hidden" name="op" value="add"/> 
				<table class="mgauto">
					<tr class="h35">
						<td width="10%" class="textright">用户名：</td>
						<td width="40%"><input class="input"  type="text" name="username" maxlength="30" id="username"/>
						<span class="gray">最多30个字符</span>
						</td>
						<td width="10%" class="textright">密码：</td>
						<td width="40%">
							<input  class="input"  type="password" name="password" maxlength="16" id="password"/>
							<span class="gray">6到16个字符之间</span>
						</td>
					</tr>
					<tr  class="h35">
						<td class="textright">角色：</td>
						<td>
							<select name="role" class="sel">
								<c:forEach items="${rolelist}" var="r">
									<option value="${r.id}" selected>${r.name}</option>
								</c:forEach>
							</select> 
						</td>
						<td class="textright"></td>
						<td>
						</td>
					</tr>
					<tr>
						<td colspan="4"  class="textcenter">
						<input class="button" type="submit" value="提交"/>&nbsp;&nbsp;&nbsp;&nbsp;
						<input class="button" type="reset" value="重置" />
						</td>
					</tr>
				</table>
			</form>
		</fieldset>
		</div>
		<table class="tab mgt">				
			<tr class="tabhead">
				<td width="5%">序号</td>
				<td width="22%">用户名</td>
				<td width="22%">昵称</td>
				<td width="10%">角色</td>
				<td width="10%">状态</td>
				<td width="15%">操作</td>
			</tr>
			<c:forEach items="${list}" var="user"  varStatus="s">
				<tr onmouseover="$(this).css('background','#E1E1E1');"
				onmouseout="$(this).css('background','')">
						<td>${s.count+((list.page-1)*list.pageSize) }</td>
						<td><c:out value="${user.userName}"/></td>
						<td><c:out value="${user.nickName}"/></td>
						<td>
							<c:if test="${!user.admin}">普通用户 </c:if>
							<c:if test="${user.admin}">管理员 </c:if>
						</td>
						<td id="status_${user.id}"><c:if test="${user.black}"><span class="red">已拉黑</span></c:if><c:if test="${!user.black}">正常</c:if> </td>
						
						<td>
						
						<a href="javascript:upduser('${user.id}','${user.userName}','${tnd}')">修改</a>&nbsp;&nbsp;
						<span id="${user.id}">
						<c:if test="${!user.black}"><c:if test="${user.id!=sessionScope.user.id}">
							<a href="javascript:pullblack('pullblack.jspx?id=${user.id}&&op=pullblack','${user.id}');">拉黑&nbsp;&nbsp;</a> 
						</c:if>
						</c:if>
						<c:if test="${user.black}"><a href="javascript:pullblack('pullblack.jspx?id=${user.id}&&op=recover','${user.id}');">恢复&nbsp;&nbsp;</a> </c:if>
						</span>
						<a href="javascript:resetpass('${user.id}','${user.userName}')">重置密码</a>
						
						</td>
				</tr>
			</c:forEach>
			<c:if test="${(list.count-((list.page-1)*list.pageSize))<10 }" >
			<c:forEach  begin="0" end="${9-(list.count-((list.page-1)*list.pageSize)) }" varStatus="s">
				<tr>
					<td>${s.count+(list.count-((list.page-1)*list.pageSize))+((list.page-1)*list.pageSize)}</td>
					<td>&nbsp;</td>
					<td>&nbsp;</td>
					<td>&nbsp;</td>
					<td>&nbsp;</td>
					<td>&nbsp;</td>
				</tr>
			</c:forEach>
			</c:if>
			<tr>
					<td colspan="7"><jsp:include page="../pagebar.jsp"></jsp:include></td>
			</tr>
		</table>
		<form action="userlist.jspx" method="post" name="form1" id="form1">
		<input type="hidden" name="p" id="p"/>
		</form>
	</div>
<jsp:include page="/foot.jsp"></jsp:include>
<div id="upduser"  class="login"><jsp:include page="updateuser.jsp"></jsp:include></div>
<div id="resetpass"  class="login"><jsp:include page="resetpass.jsp"></jsp:include></div>
<script>
	//修改培训日期
	function upduser(id,username,trainingDate){
		$("#upduser").show();
		$("#udpuserid").val(id);
		$("#udpusername").html(username);
		$("#udptrainingDate").val(trainingDate);
	}
	
	//重置密码
	function resetpass(id,username){
		$("#resetpass").show();
		$("#resetpassid").val(id);
		$("#resetusername").html(username);
		$("#resetpassword").focus();
	}
	
	
	
	//拉黑
	function pullblack(target,id){
			if(confirm("确定要执行该操作?")){
				$.post(target,
						function(data,statusText){
							if("shield"==data){
								showMsg("屏蔽成功");
								document.getElementById(id)
								.innerHTML="<a href="+
								"\"javascript:pullblack('pullblack.jspx?id="+id+"&&op=recover','"+id+"');\">"+
								"恢复&nbsp;&nbsp;</a>"; 
								var sid="status_"+id;
								document.getElementById(sid).innerHTML="<span class='red'>已拉黑</span>";
								
							}else if("recover"==data){
								showMsg("恢复成功");
								document.getElementById(id).innerHTML="<a href="+
								"\"javascript:pullblack('pullblack.jspx?id="+id+"&&op=pullblack','"+id+"');\">"+
								"拉黑&nbsp;&nbsp;</a>";
								var sid="status_"+id;
								document.getElementById(sid).innerHTML="正常";
							}
				},"html");
		}
	}
	//输入合法性检测
	function checkInput(){
		var username=$("#username").val();
		var password=$("#password").val();
		if(0==$.trim(username).length){
			showMsg("用户名不能为空");
			return false;
		}
		if(containSpecial(username)){
			showMsg("用户名不能使用引号、空格等特殊字符");
			return false;
		}
		if(username.length<5|username.length>30){
			showMsg("用户名长度要在5到30之间");
			return false;
		}
		if(0==password.length){
			showMsg("密码不能为空");
			return false;
		}
		if(password.length<6||password.length>16){
			showMsg("密码长度只能在6和16之间");
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
