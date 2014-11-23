<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %> 
<!DOCTYPE html>
<html>
<head>
	<title><c:out value="${theme.title}"/></title>
	<meta name="author" content="ourlinc" />
	<meta name="copyright" content="Copyright 2008 Ourlinc,Co.,Ltd" />
	<link rel="stylesheet" href="css/commom.css" type="text/css">
	<link rel="stylesheet" href="css/nav.css" type="text/css">
	<link rel="stylesheet" href="css/content.css" type="text/css">
	<link rel="stylesheet" href="css/head.css" type="text/css" />
	<script type="text/javascript" src="js/common.js"></script>
	<script type="text/javascript" src="js/jquery-1.8.2.min.js"></script>
	<link rel="stylesheet" href="/htmledit/themes/default/default.css" />
	<link rel="stylesheet" href="/htmledit/plugins/code/prettify.css" />
	<script charset="utf-8" src="/htmledit/kindeditor.js"></script>
	<script charset="utf-8" src="/htmledit/lang/zh_CN.js"></script>
	<script charset="utf-8" src="/htmledit/plugins/code/prettify.js"></script>
	<script>
	//随机生成标签颜色
	$(function(){
		$(".cont_lab a").each(function(){
			var a=Math.ceil(Math.round(Math.random()*5));
			var color="color"+a;
			$(this).addClass(color);
		});
	});

	</script>
	</head>
<body>
	<jsp:include page="head.jsp"></jsp:include>
	<jsp:include page="nav.jsp"></jsp:include>
	<div class="comWidth clearfix">
		<div class="cont_main mgt">
			<div class="cont_title">
				<h3><c:out value="${theme.title}"/></h3>
				<p class="cont_author">
					作者：<a href="author.jspx?id=${theme.user.id }" target="_blank"><c:out value="${theme.user.nickName }"/> </a> &nbsp;&nbsp;
					发表时间：<fmt:formatDate value="${theme.createDate}" pattern='yyyy-MM-dd HH:mm:ss' /> &nbsp;&nbsp;
					浏览(${theme.viewNum })&nbsp;
					<span>
					评论(${theme.commentsNum })&nbsp;
					</span>
					<span>
					收藏(<span id="collect">${theme.collectNum }</span>)&nbsp;
					</span>
					<span>
					赞(<span id="praise">${theme.praiseNum }</span>)&nbsp;&nbsp;
					</span>
					
				</p>
				<p class="cont_lab">
					<c:forEach items="${theme.labelList }" var="l">
							<a class=""  href="themes.jspx?labelId=${l.id }" target="_blank"><c:out value="${l.name}"/></a>
					</c:forEach>
				</p>
				
			</div>
			<div class="content">
			${theme.content }
			</div>
			<!--操作start-->
			<div class="operate textcenter">
					<c:if test="${!theme.shield}">
					<span id="collectspan">
					<c:if test="${isCollected}">
					<span  onclick="operation('cannalCollect')" style="cursor:pointer">
					<span class="icon_clt"></span>取消收藏</span>
					</c:if>
					<c:if test="${!isCollected}">
					<span  onclick="operation('collect')" style="cursor:pointer">
					<span class="icon_clt"></span>收藏</span>
					</c:if>
					</span>&nbsp;&nbsp;&nbsp;
					<span id="praisespan">
					<c:if test="${isPraised}">
					<span style="cursor:pointer"><span class="icon_zan"></span>已赞</span>
					</c:if>
					<c:if test="${!isPraised}">
					<span onclick="operation('praise')" style="cursor:pointer">
					<span class="icon_zan"></span>赞</span>
					</c:if>
					</span>&nbsp;&nbsp;&nbsp;
					</c:if>
					<c:if test="${ sessionScope.user.admin}">
					<c:if test="${ theme.shield}">
					<span onclick="shield('admin/shieldtheme.jspx?id=${theme.id }&&op=recover')" style="cursor:pointer">	<span class="icon_recover"></span>恢复</span>
					</c:if>
					<c:if test="${!theme.shield}">
					<span onclick="shield('admin/shieldtheme.jspx?id=${theme.id }&&op=shield')" style="cursor:pointer"><span class="icon_shield"></span>屏蔽</span>
					</c:if>
					</c:if>
			</div>
			<!--操作end-->
			<c:if test="${!theme.shield}">
			<div class="comment">
				<h4></h4>
				<div class="cmt">
					<ul>
					<c:forEach items="${theme.commentsList }" var="c">
							<li>
							<p class="clearfix">
								<span class="fl">评论人：
								<a href="author.jspx?id=${c.commentator.id }" target="_blank" id="${c.id}">
								<c:out value="${c.commentator.nickName }"/> 
								</a>
								&nbsp;&nbsp;
								评论时间：<fmt:formatDate value="${c.createDate}" pattern='yyyy-MM-dd HH:mm:ss' /> 
								</span>
								<c:if test="${!c.shield}">
								<a  class="fr comments"  href="javascript:reply('${c.id}')">回复评论</a>
								</c:if>
								<c:if test="${ sessionScope.user.admin}">
								<c:if test="${ c.shield}">
								<a  class="fr" href="javascript:shield('admin/shieldcomments.jspx?id=${c.id }&&op=recover')" 
								style="cursor:pointer">恢复&nbsp;&nbsp;</a>
								</c:if>
								<c:if test="${!c.shield}">
								<a  class="fr" href="javascript:shield('admin/shieldcomments.jspx?id=${c.id }&&op=shield')" 
								style="cursor:pointer">屏蔽&nbsp;&nbsp;</a>
								</c:if>
								</c:if>
							</p>
							<p class="cmt_content">${c.content}</p>
							<c:if test="${c.replyList!=null&&fn:length(c.replyList)!=0&&!c.shield}">
							<div class="reply">
								<div class="jt"></div>
							
								<ul>
									<c:forEach items="${c.replyList }" var="r">
									<li>
										<p>
											
											<a href="author.jspx?id=${r.replyUser.id }" target="_blank" id="${r.id }">
											<c:out value="${r.replyUser.nickName }"/>
											</a>
											<c:if test="${null!=r.replayerTarget }" >
											&nbsp;回复&nbsp;
											<a href="author.jspx?id=${r.replayerTarget.replyUser.id }" target="_blank" >
											<c:out value="${r.replayerTarget.replyUser.nickName}"/>
											</a>
											</c:if>:
											
												<c:if test="${ !c.shield}">
												<a class="fr r" href="javascript:replyAgain('${r.id}')">回复</a>
												</c:if>
												<c:if test="${ sessionScope.user.admin}">
												<c:if test="${ r.shield}">
												<a  class="fr" href="javascript:shield('admin/shieldreply.jspx?id=${r.id }&&op=recover')" 
												style="cursor:pointer">恢复&nbsp;&nbsp;</a>
												</c:if>
												<c:if test="${!r.shield}">
												<a  class="fr" href="javascript:shield('admin/shieldreply.jspx?id=${r.id }&&op=shield')" 
												style="cursor:pointer">屏蔽&nbsp;&nbsp;</a>
												</c:if>
												</c:if>
										</p>
										<p class="cmt_content">
											${r.content}
										</p>
										<span style="color:gray;font-size:12px"><fmt:formatDate value="${r.createDate}" pattern='yyyy-MM-dd HH:mm:ss' /> </span>
									</li>
									</c:forEach>
								</ul>
								<a href="javascript:reply('${c.id}')">
								我来也说一句</a>
								</div>
							</c:if>
						</li>	
					</c:forEach>
					</ul>
					
				</div>
			</div>
		</c:if>
		</div>
		<c:if test="${ !theme.shield}">
		<div class="publish mgt">
			<div class="pub_text">
			发表评论：<span id="replayName"></span>
			</div>
		
			<form action="user/commentstheme.jspx" method="post" name="form1">
			<input type="hidden" name="op" id='op' value="comments"/>
			<input type="hidden" name="commentsId" id="commentsId" value=""/>
			<input type="hidden" name="replyId" id="replyId" value=""/>
			<input type="hidden" name="themeId" value="${theme.id }"/>
			<textarea rows="10" cols="100" name="content" id="content" style="width:40px">
			</textarea>
			<br/>
			<input type="button" name="send" value="提交评论" class="button"/>
			</form>
		</div>
		</c:if>
	</div>
	<jsp:include page="foot.jsp"></jsp:include>
		<script>
		//初始化HTML编辑器
		KindEditor.ready(function(K) {
			var editor1 = K.create('textarea[name="content"]', {
				allowPreviewEmoticons : false,
				allowImageUpload : false,
				cssPath : '/htmledit/plugins/code/prettify.css',
				uploadJson : '/file/upload.jspx',
				fileManagerJson : '/file/manager.jspx',
				allowFileManager : true,
				items : [
							'fontname', 'fontsize', '|', 'forecolor', 'hilitecolor', 'bold', 'italic', 'underline',
							'removeformat', '|', 'justifyleft', 'justifycenter', 'justifyright', 'insertorderedlist',
							'insertunorderedlist', '|', 'emoticons', 'link']

			});
			prettyPrint();
			K('input[name=send]').click(function(e) {
				checkInput(editor1.html());
			});
			$(".comments").click(function(){
				editor1.focus();
				$(document).scrollTop($(document.body).height()); 
			});
			$(".r").click(function(){
				editor1.focus();
				$(document).scrollTop($(document.body).height()); 
			});
		
	});
	//回复评论的方法
	function reply(id,name){
		$("#commentsId").val(id);
		$("#op").val("replay")
		var name=document.getElementById(id).innerHTML;
		var img="<img src='/image/cancel.png'  class='pub_cancel'  onclick='cancelReplay()'/>";
		$("#replayName").html("<div class='pub_replayman'>"+
				"<span class='pub_color'>回复</span>&nbsp;&nbsp;<strong>"
				+name+"</strong>&nbsp;"+img+"</div>");
	}
	//回复 回复的方法
	function replyAgain(id){
		$("#replyId").val(id);
		$("#op").val("replyAgain")
		var name=document.getElementById(id).innerHTML;
		var img="<img src='/image/cancel.png'  class='pub_cancel'  onclick='cancelReplay()'/>";
		$("#replayName").html("<div class='pub_replayman'>"+
				"<span class='pub_color'>回复</span>&nbsp;&nbsp;<strong>"
				+name+"</strong>&nbsp;"+img+"</div>");
	}
	//取消回复
	function cancelReplay(){
		$("#commentsId").val("");
		$("#replayId").val("");
		$("#op").val("comments")
		$("#replayName").html('');
	}
	//收藏、取消收藏或赞话题的方法，op表示执行的操作
	function operation(op){
		<c:if test="${sessionScope.user==null }">
		showMsg('请先登陆');
		return ;
		</c:if>
		//Ajax执行操作
		$.post("user/operationtheme.jspx?id=${theme.id }&&op="+op,
				function(data,statusText){
					var jsonData = eval("("+data+")")
		 			var result=jsonData.root[0].result;
            		var num=  jsonData.root[0].value;
            		var str="";
					if("collect"==result){
						$("#collect").html(num);
						var v="<span onclick=\"operation('cannalCollect')\" style=\"cursor:pointer\"><span class=\"icon_clt\"></span>取消收藏</span>";
						$("#collectspan").html(v);
						str="收藏成功";
					}else if("praise"==result){
						$("#praise").html(num);
						var v="<span style='cursor:pointer'><span class='icon_zan'></span>已赞</span>";
						$("#praisespan").html(v)
						str="赞成功";
					}else if("cannalCollect"==result){
						$("#collect").html(num);
						var v="<span onclick=\"operation('collect')\" style=\"cursor:pointer\"><span class=\"icon_clt\"></span>收藏</span>";
						$("#collectspan").html(v);
						str="取消收藏成功";
					}else if("selfcollect"==result){
						str="收藏失败,无法收藏自己发表的文章";
					}else if("selfpraise"==result){
						str="对不起,无法赞自己的文章";
					}else if("isCollected"==result){
						str="收藏失败,你已经收藏了该文章,请不要重复收藏";
					}else if("isPraised"==result){
						str="您已经赞过该文章了,请不要重复赞";
					}else if("nologin"==result){
						str="请先登陆";
					}else if("null"==result){
						str="没有找到应该文章";
					}else{
						str="未知错误";
					}
					showMsg(str);
			},"html");
	}
	//屏蔽或恢复话题
	function shield(target){
		$.post(target,
				function(data,statusText){
					if("shield"==data){
						showMsg("屏蔽成功");
						window.location='/content.jspx?id=${theme.id}&&msg=屏蔽成功';
					}else if("recover"==data){
						showMsg("恢复成功");
						window.location='/content.jspx?id=${theme.id}&&msg=恢复成功';
					}
					
			},"html");
	}
	//提交表单时的检查数据合法性
	function checkInput(html){
		<c:if test="${sessionScope.user==null }">
		showMsg('请先登陆');
			return;
		</c:if>
		if($.trim(html).length==0){
			showMsg("内容不能为空");
			return;
		}else{
			$("#content").val(html);
			form1.submit();
		}
	
	}
	
	<c:if test="${param.msg!=null}">
	$(function(){
		showMsg("${param.msg }");
	});
	</c:if>
	</script>
	

</body>
</html>