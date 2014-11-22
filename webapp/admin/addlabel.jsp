<%@page contentType="text/html; charset=UTF-8" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="content-type" content="text/html; charset=utf-8" />
	<script type="text/javascript">
	$(function(){
		//关闭弹出框
		$("#closelabel").click(function(){
			$("#addchildlabel").hide();
		});
	});
	</script>
<title>添加子标签</title>
</head>
	<body>
		<div class="logintext"><span class="fl"><strong>添加子标签</strong></span><a id="closelabel" href="javascript:;"><span class="close fr"></span></a></div>
			<div class="textcenter">
			<div class="mgt50">
			<form method="post"  action="addlabel.jspx" onsubmit="return checklabelinput();">
				<input type="hidden" value="add" name="op"/>
				<input type="hidden" value="add" name="parentId" id="parentId"/>
				<table class="mgauto">
				<tr class="h35">
					<td class="textright">父标签名:</td>
					<td class="textleft">&nbsp;
					<span id="parentname"></span>
					</td>
				</tr>
				<tr class="h35">
					<td  class="textright">子标签名:</td><td>&nbsp;<input type="text" name="name" id="childname" maxlength="18" />
					<span style="color:gray">18个字以内</span></td>
				</tr>
				
				<tr>
					<td colspan="2">
						<input class="button" type="submit" value="添加"/>&nbsp;&nbsp;
						<input class="button"  type="reset" value="重置"/>
					</td>
				</tr>
				<tr><td colspan="2"><div id="childmsg" class="failmsg">${msg}</div></td></tr>
				</table>
			</form>
		</div>
	</div>
	<script>
	//输入合法性检查
	function checklabelinput(){	
		var name=$("#childname").val();
		if($.trim(name).length==0){
			$("#childmsg").html("子标签名不能为空");
			return false;
		}
		if(name.length>18){
			$("#childmsg").html("子标签名过长");
			return false;
		}
		return true;
	}
	</script>
	</body>
</html>