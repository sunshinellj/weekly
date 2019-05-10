$(document).ready(function(){ 
	
	$("#userId").focus();
	
	if ($("#message").val() != "") {
		$(".weekly-error").show();
	} else {
		$(".weekly-error").hide();
	}
	
	$("#userId").focus(function(){
		$("#message").val("");
	});
	
	$("#userId").blur(function(){
		$(".weekly-error").hide();
	});
	
});

