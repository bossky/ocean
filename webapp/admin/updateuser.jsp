<%@page contentType="text/html; charset=UTF-8" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="content-type" content="text/html; charset=utf-8" />
	<script type="text/javascript" src="/js/jquery-1.8.2.min.js"></script>
	<script type="text/javascript" src="../js/My97DatePicker/WdatePicker.js"></script>
	<script type="text/javascript">
	$(function(){
		$("#closeuser").click(function(){
			$("#upduser").hide();
		});
		$("#cuser").click(function(){
			$("#upduser").hide();
		});cuser
	});
	</script>
</head>
<body>
<div class="logintext"><span class="fl"><strong>修改培训日期</strong></span><a id="closeuser" href="javascript:;"><span class="close fr"></span></a></div>
	<div class="textcenter">
		<div class="mgt50" >
		<form method="post" action="updateuser.jspx">
			<input type="hidden"  name="op"  value="update">
			<input type="hidden"  name="id"  value=""  id="udpuserid">
				<table class="mgauto">
					<tr class="h35">
						<td>用户名：</td><td class="textleft"><span id="udpusername"></span></td>
					</tr>
					<tr class="h35">
						<td>培训日期：</td>
						<td>
s						</td>
					</tr>
					<tr class="h40">
						<td colspan="2"><input class="button" type="submit" value="修改">&nbsp;&nbsp;
						<input  id="cuser"class="button" type="button" value="取消"/>
						</td>
					</tr>
				</table>
		</form>
		</div>
	</div>
</body>
</html>