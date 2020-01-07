;
aftree = (function($){
	$(function(){
	 	$("div.aftree").delegate("i.plus",'click',function(){
	 		var ul = $(this).removeClass("plus").addClass('minus').parent('li').children("ul");
	 		ul.slideDown();
	 	});
	 	$("div.aftree").delegate("i.minus",'click',function(){
	 		var ul = $(this).removeClass("minus").addClass('plus').parent('li').children("ul");
	 		ul.slideUp();
	 	});
	 	
	 	//初始化时关闭所有plus类的兄弟ul节点 并打开input的checked=true的节点
	 	var inittree = function(){
	 		var input = $("div.aftree").find(":input:checked[layer!='1']");
	 		input.each(function(i){
	 			var d =  $(this).parents("li");
	 			var a = d.filter("div.aftree li").children('i');
	 			a.removeClass("plus").addClass("minus");	
	 			
	 		});
	 		$("i.plus").nextAll("ul").hide();
	 		$("i.minus").nextAll("ul").show();
	 		
	 		
	 	}
	 	inittree();
	 	
	 	
	 });
	
	
	
})(jQuery);

	