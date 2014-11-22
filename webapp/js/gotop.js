$(function() {
var $backToTopTxt = "回到顶部", $backToTopEle = $(
'<div id="backToTop"  class="backToTop"></div>').appendTo($("body")).attr("title", $backToTopTxt).click(
function() {
$("html, body").animate({
scrollTop : 0
}, 500);
}), $backToTopFun = function() {
var st = $(document).scrollTop(), winh = $(window).height();
(st > 200) ? $backToTopEle.fadeIn(400): $backToTopEle.fadeOut(300);     
if (!window.XMLHttpRequest) {
$backToTopEle.css("top", st + winh - 166);
}
};
$(window).bind("scroll", $backToTopFun);
$(function() {
$backToTopFun();
$("#backToTop").hover(function(){
	$("#backToTop").addClass("bthover");
	},function(){
		$("#backToTop").removeClass("bthover");
	}
);
});
});




	


