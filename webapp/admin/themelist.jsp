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
<title>话题管理</title>
<script>
		$(function(){
			$("#navthememag").addClass("selected");
		});
	</script>
</head>
<body>
	<jsp:include page="/head.jsp"></jsp:include>
	<jsp:include page="/nav.jsp"></jsp:include>
	<div class="comWidth clearfix mgt mh400">
		<h3 >话题管理</h3>
		<div class="mgt">
			<fieldset>
				<legend>查询话题</legend>
				<div class="textcenter">
				<form action="themelist.jspx" method="post" name="form1" id="form1">
				
					用户名:&nbsp;<input class="input"  type="text" name="userName"
					 value="<c:out value="${userName }"/>"/>
					&nbsp;&nbsp;
					标签:
					<select name="labelId"  class="sel">
					<option value=""></option>
					<c:forEach items="${labelList}" var="l">
					<c:choose> 
					<c:when test="${l.id==labelId }"> 
					<option value="${l.id}" selected><c:out value="${l.name}"/></option>		    
					</c:when> 
					<c:otherwise> 
					<option value="${l.id}"><c:out value="${l.name}"/></option>
					</c:otherwise> 
					</c:choose> 
					</c:forEach>
					</select>
					&nbsp;&nbsp;
					<input  class="button" type="submit" value="搜索" onclick="$('#p').val('')"/>
					<input type="hidden" name="id" id="t_id" value=""/>	
					<input type="hidden" name="op" id="op" value=""/>	
					<input type="hidden" name="p" id="p" value="${list.page}"/>
					<jsp:include page="/user/selectlabel.jspx"/>
				</form>
				</div>
			</fieldset>
		</div>
		
		
		<table class="tab mgt">				
			<tr class="tabhead">
				<td width="5%">序号</td>
				<td>话题名称</td>
				<td>标签</td>
				<td>用户名</td>
				<td>发表时间</td>
				<td>状态</td>
				<td>操作</td>
			</tr>
			<c:forEach items="${list}" var="l" varStatus="s">
				<tr onmouseover="$(this).css('background','#E1E1E1');"
				onmouseout="$(this).css('background','')">
					<td>${s.count+((list.page-1)*list.pageSize) }</td>
					<c:set var="a" value='"' />
					<td align="left" title="${fn:replace(l.title,a,'&quot;')}">
					<div class="hiddendiv w230 mgauto">
					<a href="/content.jspx?id=${l.id}">
					<c:out value="${l.title}"/></a>
					</div></td>
					<td>
					<div class="hiddendiv w230 mgauto">
						<c:forEach items="${l.labelList}" var="lab">
							<c:out value="${lab.name}"/>&nbsp;&nbsp;
						</c:forEach>
					</div>
					</td>
					<td>
					<div class="hiddendiv mgauto w120" >
					<c:out value="${l.user.userName}"/>
					</div>
					</td>
					<td><fmt:formatDate value="${l.createDate}" pattern='yyyy-MM-dd HH:mm:ss' /></td>
					<td id="status_${l.id }">
						<c:if test="${l.shield}"><span  class="red">已屏蔽</span></c:if>
						<c:if test="${!l.shield}">正常</c:if>
					</td>
					<td>
						<a href="javascript:showLabel('${l.id }','${l.labelStr}');">调整标签</a>&nbsp;&nbsp;
						<span id="${l.id }">
						<c:if test="${l.shield}">
						<a href="javascript:shield('shieldtheme.jspx?id=${l.id }&&op=recover','${l.id }');" >恢复</a> 
						</c:if>
						<c:if test="${!l.shield}">
						<a href="javascript:shield('shieldtheme.jspx?id=${l.id }&&op=shield','${l.id }');">屏蔽</a> 
						</c:if>
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
//屏蔽、恢复话题的方法
function shield(target,id){
	if(confirm("确定要执行该操作?")){
		$.post(target,
					function(data,statusText){
						if("shield"==data){
							showMsg("屏蔽成功");
							document.getElementById(id)
							.innerHTML="<a href="+
							"\"javascript:shield('shieldtheme.jspx?id="+id+"&&op=recover','"+id+"');\">"+
							"恢复</a>"; 
							var sid="status_"+id;
							document.getElementById(sid)
							.innerHTML="<span class='red'>已屏蔽</span>"; 
							
						}else if("recover"==data){
							showMsg("恢复成功");
							document.getElementById(id).innerHTML="<a href="+
							"\"javascript:shield('shieldtheme.jspx?id="+id+"&&op=shield','"+id+"');\">"+
							"屏蔽</a>";
							var sid="status_"+id;
							document.getElementById(sid)
							.innerHTML="正常"; 
						}
						//window.location='/admin/themelist.jspx';
				},"html");
	}
}
//显示标签选择框
function showLabel(id,label){
	if("null"!=label&&0!=$.trim(label).length){
		label=label.substring(1,label.length-1);
		labelArr=label.split(",");
		for(var i=0;i<labelArr.length;i++){
			document.getElementById($.trim(labelArr[i])).checked =true;	
		}
	}
	$("#overlayInLabel").show();
	$("#labelDiv").show();
	$("#t_id").val(id);
	$("#op").val("update");
}
//隐藏标签选择框
function hideLabel(){
	$("#overlayInLabel").hide();
	$("#labelDiv").hide();
	$("#t_id").val("");
	$("#op").val("");
}
$(function(){
	$("#confirmButton").click(function(){
		form1.submit();
	});
});
<c:if test="${param.msg!=null}">
showMsg("${param.msg }");
</c:if>
<c:if test="${msg!=null}">
showMsg("${msg }");
</c:if>
</script>
</body>
</html>