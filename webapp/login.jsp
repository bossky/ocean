<%@page contentType="text/html; charset=UTF-8" session="false"%>

<!DOCTYPE html >
<html >
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>用户登录</title>
<meta name="author" content="ourlinc" />
<meta name="copyright" content="Copyright 2008 Ourlinc,Co.,Ltd" />
<script>

$(function(){
	//提交表单时的检查数据合法性
	$("#loginbtn").click(function(){
		if(""==$(".username").val()){
			$(".failmsg").text("用户名不能为空");
			$(".username").focus();
			return false;
		}
		if(""==$(".pass").val()){
			$(".failmsg").text("密码不能为空");
			$(".pass").focus();
			return false;
		}
		var values={username:$(".username").val(),password:$(".pass").val(),op:"login"};
		$.post("login.jspx",values,function(data){
			if("isEmpty"==data){
				$(".failmsg").text("用户名和密码不能为空!");
			}else if("failuser"==data){
				$(".failmsg").text("用户名不存在!");
				$(".username").attr("value","");
				$(".pass").attr("value","");
				$(".username").focus();
			}
			else if("failpass"==data){
				$(".failmsg").text("密码错误!");
				$(".pass").attr("value","");
				$(".pass").focus();
			}
			else if("success"==data){
				  location.href=$("#url").val();
			}
		},'html');
	});
	
	$("#close").click(function(){
		$("#overlay").hide();
		$("#login").hide();
	});
});
</script>
</head>
<body>
<div class="logintext"><span class="fl"><strong>用户登录</strong></span><a id="close" href="javascript:;"><span class="close fr"></span></a></div>
<div class="textcenter">
<form method="post" action="">
	<div class="loginbox">
		<input type="hidden" name="url" value="/" id="url"/>
		<div class="userbox"><span class="icon-user"></span><input  maxlength="30"  type="text" class="username" name="username"></div>
		<div><span class="icon-pass"></span><input type="password" class="pass" name="password"></div>
	</div>
	<input id="loginbtn"  type="submit" onclick="return false;" value="登陆"/>
	<div class="failmsg "></div>
</form>
</div>
</body>
</html>