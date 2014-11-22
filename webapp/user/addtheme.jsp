<%@page contentType="text/html; charset=UTF-8" session="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>发表话题</title>
<meta name="author" content="ourlinc" />
<meta name="copyright" content="Copyright 2008 Ourlinc,Co.,Ltd" />
<link rel="stylesheet" href="/css/head.css" type="text/css"/>
<link rel="stylesheet" href="/css/commom.css" type="text/css"/>
<link rel="stylesheet" href="/css/nav.css" type="text/css"/>
<link rel="stylesheet" href="/css/main.css" type="text/css"/>
<link rel="stylesheet" href="/htmledit/themes/default/default.css" />
<link rel="stylesheet" href="/htmledit/plugins/code/prettify.css" />
<script charset="utf-8" src="/htmledit/kindeditor.js"></script>
<script charset="utf-8" src="/htmledit/lang/zh_CN.js"></script>
<script charset="utf-8" src="/htmledit/plugins/code/prettify.js"></script>
<script type="text/javascript" src="/js/jquery-1.8.2.min.js"></script>
<script type="text/javascript" src="/js/common.js"></script>
<style type="text/css">
.content{
	padding:10px;
}
.title{
	color:#444;
	border:1px solid #ccc;
	padding:5px 10px;
	box-shadow:0px 1px 0px #fff;
    -moz-box-shadow:0px 1px 0px #fff;
    -webkit-box-shadow:0px 1px 0px #fff;
    margin:5px 5px 0px 0px;
  	width:400px;
}
.title:focus{
	background-color:#FFFAF0;
	border:1px solid #7A67EE;
}
</style>
<script>
	//随机生成标签颜色
	$(function(){
		$(".art_lab a").each(function(){
			var a=Math.ceil(Math.round(Math.random()*5));
			var color="color"+a;
			$(this).addClass(color);
		});
	});

	</script>
</head>
<body>
<jsp:include page="/head.jsp"></jsp:include>
<jsp:include page="/nav.jsp"></jsp:include>
<div class="comWidth clearfix">
<div class="mgt">
<div class="content" style="padding-left:50px">
<form method="post"  action="addtheme.jspx" id="form" name="form">
<input type="hidden" value="add" name="op"/>
<div>
标题：<input type="text" name="title" class="title" id="title" maxlength="60" /><span style="color:gray">60个字以内</span>
</div>
<br/>
<div>标签：<a href="javascript:showLabel();">选择</a>&nbsp;&nbsp;
<span style="color:gray">系统将根据您的选择进行更细致的归类。</span>
</div>
<div id="show" class="art_lab" style="width:100%;padding:0 10px"></div>
<div class=" clearfix"></div>
<div id="edit_div" class="edit_div">
<textarea name="content" id="content" cols="100" rows="8" style="width:800px;height:500px;visibility:hidden;">
</textarea>
</div>
<div id="preview_div"></div>
<br/>
<input type="button" name="send" value="发表话题" class="button"/>
<input type="button" name="preview" id="preview" value="预览" class="button"/>
<jsp:include page="selectlabel.jspx" />
</form>
</div>
</div>
</div>
<jsp:include page="/foot.jsp"></jsp:include>
<script>
//初始化HTML编辑器
KindEditor.ready(function(K) {
	var editor1 = K.create('textarea[name="content"]', {
			cssPath : '/htmledit/plugins/code/prettify.css',
			uploadJson : '/file/upload.jspx',
			fileManagerJson : '/file/manager.jspx',
			allowFileManager : true
	});
	//提交表单时执行checkInput方法
	K('input[name=send]').click(function(e) {
		checkInput(editor1.html());
	});
	//点击预览时执行preview方法
	K('input[name=preview]').click(function(e) {
		preview(editor1.html());
	});
	prettyPrint();
	});
//检查表单的数据合法性
function checkInput(v){
	var title=$.trim($("#title").val());
	var content=$.trim(v);
	if(title.length==0){
		showMsg("请输入标题");
	}else if($("#title").val().length>60){
		showMsg("标题过长")
	}else if(content.length==0){
		showMsg("请输入内容");
	}else{
		$("#content").val(content);
		form.submit();
	}
}
//预览内容
function preview(html){
	if("取 消"==$("#preview").val()){
		$("#preview_div").css('display','none');
		$("#edit_div").css('display','');
		$("#preview").val("预 览");
	}else{
		$("#preview_div").html(html);
		$("#preview_div").css('display','');
		$("#edit_div").css('display','none');
		$("#preview").val("取 消");
	}

}
//显示标签选择框
function showLabel(){
	$("#overlayInLabel").show();
	$("#labelDiv").show();
}
//隐藏标签选择框
function hideLabel(){
	$("#overlayInLabel").hide();
	$("#labelDiv").hide();
}
$(function(){
//选择标签后的确定事件
$("#confirmButton").click(function(){
	$("#show").html("");
	$(".label").each(function(){
		if($(this).find("input[name=label]").attr("checked")){
			var name=$(this).children("span").html();
			var a=Math.ceil(Math.round(Math.random()*5));
			var color="color"+a;//随机样式
			$("#show").append("<a href='#' class='"+color+"'>"+name+"</a>&nbsp;&nbsp;&nbsp;&nbsp;")
		};
	});
	hideLabel();
});
});
<c:if test="${null!=param.msg }">
showMsg('${param.msg }')
</c:if>
</script>



</body>
</html>