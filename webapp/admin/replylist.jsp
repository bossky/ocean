<%@page contentType="text/html; charset=UTF-8" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %> 
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
			$("#navrpmag").addClass("selected");
		});
	</script>
<title>回复管理</title>
</head>
<body>
	<jsp:include page="/head.jsp"></jsp:include>
	<jsp:include page="/nav.jsp"></jsp:include>
	<div class="comWidth clearfix mgt mh400">
		<h3>回复管理</h3>
		<div class="mgt">
			<fieldset>
				<legend>查询回复</legend>
				<div class="textcenter">
				<form action="replylist.jspx" method="post" name="form1" id="form1">
					<input  type="hidden" name="p" id="p"/>
					用户名&nbsp;:&nbsp;<input class="input" type="text" name="userName" value="<c:out value="${userName }"/>"/>&nbsp;&nbsp;
					所属话题&nbsp;:&nbsp;<input  class="input" type="text" name="themeTitle" value="<c:out value="${themeTitle }"/>">&nbsp;&nbsp;
					<input   class="button"   type="submit" value="搜索"/>
				</form>
			</div>
			</fieldset>
		</div>
		<table class="tab mgt" >				
			<tr class="tabhead">
				<td width="5%">序号</td>
				<td>所属话题</td>
				<td>回复内容</td>
				<td>用户名</td>
				<td>发表时间</td>
				<td>状态</td>
				<td>操作</td>
			</tr>
		<c:forEach items="${list}" var="r" varStatus="s">
				<tr onmouseover="$(this).css('background','#E1E1E1');"
				onmouseout="$(this).css('background','')">
					<td>${s.count+((list.page-1)*list.pageSize) }</td>
					<c:set var="a" value='"' />
					<td title="${fn:replace(r.theme.title,a,'&quot;')}">
					<div class="hiddendiv mgauto w120" >
					<a href="/content.jspx?id=${r.theme.id}"><c:out value="${r.theme.title}"/></a>
					</div></td>
					<td>
						<c:set var="a" value='"' />
						<div class="hiddendiv mgauto w230" title="${r.noHTMLContent}" >
							${r.noHTMLContent}
						</div>
					</td>
					
					<td>
					<div class="hiddendiv mgauto w120" >
					<c:out value="${r.replyUser.userName}"/>
					</div>
					</td>
					<td><fmt:formatDate value="${r.createDate}" pattern='yyyy-MM-dd HH:mm:ss' /></td>
					<td id="status_${r.id}">
						<c:if test="${r.shield}"><span class="red">已屏蔽</span></c:if>
						<c:if test="${!r.shield}">正常</c:if>
					</td>
					<td>
						<span id="${r.id }">
						<c:if test="${r.shield}">
						<a href="javascript:shield('shieldreply.jspx?id=${r.id }&&op=recover','${r.id}');">恢复</a> </c:if>
						<c:if test="${!r.shield}">
						<a href="javascript:shield('shieldreply.jspx?id=${r.id }&&op=shield','${r.id}');">屏蔽</a> </c:if>
						</span>
					</td>
				</tr>
			</c:forEach>
			<c:if test="${(list.count-((list.page-1)*list.pageSize))<10 }" >
			<c:forEach  begin="0" end="${9-(list.count-((list.page-1)*list.pageSize)) }" varStatus="s">
				<tr>
					<td>${s.count+(list.count-((list.page-1)*list.pageSize))+((list.page-1)*list.pageSize)}</td>
					<td></td>
					<td></td>
					<td></td>
					<td></td>
					<td></td>
					<td></td>
				</tr>
			</c:forEach>
			</c:if>
			<tr>
					<td colspan="7"><jsp:include page="../pagebar.jsp"></jsp:include></td>
			</tr>
		</table>
	</div>
<jsp:include page="/foot.jsp"></jsp:include>
<script>
//屏蔽、恢复回复
	function shield(target,id){
		if(confirm("确定要执行该操作?")){
		$.post(target,
				function(data,statusText){
					if("shield"==data){
						showMsg("屏蔽成功");
						document.getElementById(id)
						.innerHTML="<a href="+
						"\"javascript:shield('shieldreply.jspx?id="+id+"&&op=recover','"+id+"');\">"+
						"恢复</a>"; 
						var sid="status_"+id;
						document.getElementById(sid).innerHTML="<span class='red'>已屏蔽</span>";
						
					}else if("recover"==data){
						showMsg("恢复成功");
						document.getElementById(id).innerHTML="<a href="+
						"\"javascript:shield('shieldreply.jspx?id="+id+"&&op=shield','"+id+"');\">"+
						"屏蔽</a>";
						var sid="status_"+id;
						document.getElementById(sid).innerHTML="正常";
					}
			},"html");
	}
	}
	</script>
</body>
</html>