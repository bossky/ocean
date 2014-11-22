<%@page contentType="text/html; charset=UTF-8" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="content-type" content="text/html; charset=utf-8" />
	<link rel="stylesheet" href="../css/head.css" type="text/css" />
	<link rel="stylesheet" href="../css/nav.css" type="text/css" />
	<link rel="stylesheet" href="../css/table.css" type="text/css" />
	<link rel="stylesheet" href="../css/commom.css" type="text/css" />
	<script type="text/javascript" src="../js/jquery-1.8.2.min.js"></script>
	<script type="text/javascript" src="../js/common.js"></script>
	<script>
		$(function(){
			$("#navlabelmag").addClass("selected");
		});
	</script>
	<style>
	body{
		position:relative;
	}
	</style>
<title>标签管理</title>
</head>
<body>
	<jsp:include page="/head.jsp"></jsp:include>
	<jsp:include page="/nav.jsp"></jsp:include>
	<div class="comWidth clearfix mgt mh400">
		<h3>标签管理</h3>
		<div class="mgt">
			<fieldset>
				<legend>添加根标签</legend>
				<form method="post" action ="addlabel.jspx" onsubmit="return checkinput();">
					<input type="hidden" value="add" name="op"/>
					<table class="mgauto">
					<tr>
						<td width="20%" class="textright">根标签名&nbsp;:&nbsp;</td>
						<td width="80%" >
						<input class="input"   type="text" name="name" id="name" maxlength="18" />
						<span class="gray">18个字以内&nbsp;&nbsp;</span>
					<input class="button" type="submit" value="添加"/>
						<input  class="button"  type="reset" value="重置"/>&nbsp;&nbsp;
						</td>
					</tr>
				</table>
				</form>
			</fieldset>
		</div>
		<table class="tab mgt">				
			<tr class="tabhead">
				<td width="5%">序号</td>
				<td width="30%">标签名</td>
				<td width="30%">父标签</td>
				<td width="15">话题数</td>
				<td width="20">操作</td>
			</tr>
			<c:forEach items="${list}" var="l" varStatus="s">
				<tr onmouseover="$(this).css('background','#E1E1E1');"
				onmouseout="$(this).css('background','')">
					<td>${s.count+((list.page-1)*list.pageSize) }</td>
					<td><span id="${l.id }"><c:out value="${l.name}"/></span></td>
					<td><c:out value="${l.parentLabel.name }"/> </td>
					<td>${l.themeNum }</td>
					<td><a href="javascript:addchild('${l.id }');">添加子标签</a>&nbsp;&nbsp;<a href="javascript:deleteConfirm('${l.id }');">删除</a></td>
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
				</tr>
			</c:forEach>
			</c:if>
			<tr>
					<td colspan="5"><jsp:include page="../pagebar.jsp"></jsp:include></td>
			</tr>
		</table>
		<form method="post" name="form1" id="form1"  action="labellist.jspx">
		<input type="hidden" name="p" id="p"/>
		</form>
	</div>
<jsp:include page="/foot.jsp"></jsp:include>
<div id="addchildlabel"  class="login"><jsp:include page="addlabel.jspx"></jsp:include></div>
<script type="text/javascript">
//删除标签
function deleteConfirm(id,obj){
	if(confirm("确定要删除该标签,删除后子标签也会一同删除?")){
		window.location="/admin/deletelabel.jspx?id="+id;
	}
}
//添加子标签
function addchild(id){
		$("#addchildlabel").show();
		$("#parentId").val(id);
		var name=document.getElementById(id).innerHTML;
		$("#parentname").html(name);
		$("#childname").focus();
		
}

//添加根标签检测
function checkinput(){
		var name=$("#name").val();
		if($.trim(name).length==0){
			showMsg("标签名不能为空");
			return false;
		}
		if(name.length>8){
			showMsg("标签名过长");
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