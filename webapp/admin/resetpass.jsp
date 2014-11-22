<%@page contentType="text/html; charset=UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html >
<html >
<head>
	<meta http-equiv="content-type" content="text/html; charset=utf-8" />
	<script type="text/javascript" src="../js/jquery-1.8.2.min.js"></script>
	<script type="text/javascript">
	$(function(){
		//关闭弹出框
		$("#closeresetpass").click(function(){
			$("#resetpass").hide();
		});
	});
	</script>

</head>
<body>

<div class="logintext"><span class="fl"><strong>重置密码</strong></span><a id="closeresetpass" href="javascript:;"><span class="close fr"></span></a></div>
	<div class="textcenter">
		<div class="mgt50">
			<form method="post" action="resetpass.jspx" onsubmit="return checkresetInput();">
				<input type="hidden" value="" name="id" id="resetpassid">
				<input type="hidden" value="resetpass" name="op">
				<table class="passtable mgauto">
					<tr class="h35">
						<td   class="textright">用户名：</td>
						<td  class="textleft"><span id="resetusername"></span></td>
					</tr>
					<tr>
						<td class="tdl">重置密码：</td><td>
						<input type="password" name="password" maxlength="16" id="resetpassword"/><br/>
						<span class="gray">密码长度只能在6至16之间</span>
						</td>
					</tr>
					<tr>
						<td colspan=2 class="subt"><input class="button mgt" type="submit" value="提交">
						</td>
					</tr>
					<tr><td colspan="2"><div id="resetmsg" class="failmsg">${msg}</div></td></tr>
				</table>
			</form>
		</div>
	</div>
	<script type="text/javascript">
	//输入合法性检查
	function checkresetInput(){
		var password=$("#resetpassword").val();
		if(0==password.length){
			$("#resetmsg").html("密码不能为空");
			return false;
		}
		if(password.length<6||password.length>16){
			$("#resetmsg").html("密码长度只能6到16之间");
			return false;
		}
		return true;
	}
	</script>
</body>
</html>