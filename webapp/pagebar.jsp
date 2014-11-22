<%@page contentType="text/html; charset=UTF-8" session="false"%>
<%
	/*
	 此页面属于分页输出部分，以类似<jsp:include page="../pagebar.jsp"/>方式include其，
	 在include者（页面）必须有ResultPage对象在request.getAttribute("list")中，
	 可选的，定义javascript方法goPage，传入参数为页面号，如果页面支持“?p=页号”的参数分页，不需要定义goPage方法
	 具体可例子请参看sytem/listuser.jsp页面
	 */
%>
<%
	ResultPage<?> resultPage=(ResultPage<?>)request.getAttribute("list");
	if(null != resultPage) {
%>

<%@page import="com.ourlinc.tern.ResultPage"%><script>
if("function"!=typeof(goPage)){
	var goPage=function(p){
		document.getElementById("p").value=p;
		form1.submit();
		/* var uri=location.href;
		//找到“p=”的位置进行替换
		var idx=uri.indexOf("p=");
		while(-1!=idx){
			if(0==idx) break;
			var ch=uri.charAt(idx-1);
			if('&'==ch || '?'==ch) break;
			idx=uri.indexOf("p=",idx+1);
		}
		if(-1!=idx){
			//找到“p=”后的结束位
			var end=idx+2;
			while(end<uri.length && '&'!=uri.charAt(end)){++end;}
			if(end<uri.length){
				uri=uri.substr(0,idx+2)+p+uri.substr(end);
			}else{
				uri=uri.substr(0,idx+2)+p;
			}
		}else{
			if(-1==uri.indexOf('?')){
				uri="?p="+p;
			}else{
				uri+="&p="+p;
			}
		}
		location.href=uri; */
	};
}
</script>
<%
		out.write("约" + resultPage.getCount() + "条，分" + resultPage.getPageCount() + "页");
		int i=resultPage.getPage() - resultPage.getPageSize();
		if(i < 1) i=1;
		int pp=i + (resultPage.getPageSize() << 1);
		if(pp > resultPage.getPageCount()) {
			pp=resultPage.getPageCount();
		}
		if(resultPage.getPage() > 1) {
			out.write(" <a href='javascript:goPage(" + (resultPage.getPage() - 1)
					+ ")'>上一页</a>&nbsp;");
		}
		/*if(resultPage.getPage() > resultPage.getPageSize()){
			out.write("<a href='javascript:goPage(1)'><font color=red>1</font></a>&nbsp;");
		}*/
		if(1!=resultPage.getPageCount()){
		for(; i <= pp; i++) {
			if(i == resultPage.getPage())
				out.write("<b>" + i + "</b>&nbsp;");
			else
				out.write("<a href='javascript:goPage(" + i + ")'>" + i + "</a>&nbsp;");
		}
		}
		if(resultPage.getPageCount() > (resultPage.getPageSize() + pp)){
			out.write("<a href='javascript:goPage(" + resultPage.getPageCount()
					+ ")'><font color=red>" + resultPage.getPageCount() + "</font></a>&nbsp;");
		}
		if(resultPage.getPage() < resultPage.getPageCount()){
			out.write("<a href='javascript:goPage(" + (resultPage.getPage() + 1) + ")'>下一页</a>");
		}
	}
%>