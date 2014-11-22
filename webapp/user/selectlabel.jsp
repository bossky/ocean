<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div id="labelDiv"  class="labelDiv" >
<table class="labelTable">
<tr><td colspan="8" align="left" style="width:600px">系统将根据您的选择进行更细致的归类。
<input type="button" value="确定"  id="confirmButton" class="button"  />
<input type="button" value="关闭" onclick="hideLabel();"   class="button" />
</td></tr>
<tr>
<c:forEach items="${labelList}" var="l" varStatus="s">
<td  align="left">
<div class="label"> 
<input type="checkbox" name="label" value="${l.id}" id="${l.id }" />
<span><c:out value="${l.name}"/></span>
</div>
</td>
<c:if test="${s.count%4==0 }">
<c:out value="</tr>" escapeXml="false"/>
</c:if>
</c:forEach>
</tr>
</table>
</div>
<div id="overlayInLabel" class="overlay"></div>	
