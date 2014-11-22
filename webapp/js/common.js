// 在指定位置显示div
function ShowDiv(div, x, y, w) {
	div.style.visibility="visible";
	div.style.display="block";
	w = parseInt(w);
	if(!isNaN(w)){
		div.style.width=w+"px";
	}
	
	if(null!=x && null!=y){
		div.style.position="absolute";
		div.style.top = y + "px";
		div.style.left = x + "px";
	}
	// 水平居中
	else if(null==x && null!=y){
		div.style.position="absolute";
		div.style.top = y + "px";
		div.style.left=	"30%";
	}
	else if(null==x&&null==y){
		var clientXy = clientXY();
		var scrollXy = scrollXY();
		div.style.position="absolute";
		div.style.left=clientXy.x/2 + scrollXy.x - div.offsetWidth/2 +"px";
		div.style.top=clientXy.y/2 + scrollXy.y - div.offsetHeight/2 + "px";
	}
	// div.style.backgroundColor = "#e0e0e0";
	// div.style.border = "1px #b0b0b0 solid";
}
function ShowDiv_Width(div, x, y, width){
	ShowDiv(div, x, y,width);
}
/**
 * 创建Http对象
 * @returns
 */
function createXMLHttpRequest(){
	var xhr;
	try{
		xhr=new ActiveXObject("Msxml2.XMLHTTP");
	}catch(e){
		try{
			xhr=new ActiveXObject("Microsoft.XMLHTTP");
		}catch(e){
			xhr=new XMLHttpRequest();
		}
	}
	return xhr;
}
/**
 * 显示提示信息
 * @param msg
 */
function showMsg(msg){
	var show=$("<div class='prompt'></div>");
	$("body").append(show);
	show.html(msg);
	show.fadeIn();
	show.fadeOut(4000);
	
}

//检查是否包含特殊字符
function containSpecial( s )   
{   
    var containSpecial = RegExp(/[(\ )(\~)(\!)(\#)(\￥)(\$)(\%)(\^)(\&)(\*)(\()(\))(\-)\+)(\=)(\[)(\])(\{)(\})(\|)(/\)(\;)(\:)(\')(\")(\,)(/\)(\<)(\>)(\?)(\)]+/);   
    return ( containSpecial.test(s) );   
}  




