$(document).ready(function() {

	var agent = navigator.userAgent.toLowerCase();
	if(agent.indexOf("msie 8.0")>0||agent.indexOf("msie 7.0")> 0){
		$('#filebtn').hide();
	}
		var width=(document.body.scrollWidth)*0.01;
	var height=document.body.scrollHeight*0.01;
	$('#bottom').css("left", width);
	$('#bottom').css("bottom", height);
	
	$(".lablePartBox").show();
	$(".lablePointBox").show();
	$(".textPartBox").hide();
	$(".textPointBox").hide();
	$('#saveTemplate').hide();
	$('#editTemplate').hide();
	
	$('#saveTemplate').click(function() {
		
		$("#saveTemplateForm").attr("action", "saveTemplate");
		$("#saveTemplateForm").submit();
	});
	$(function() {
		$('#myfile').change(function() {
			$('#filetxt').val($('#myfile').val());
		});
	});
});

function doReturn(){
	var contextPath = $("#contextPath").val() + "/returnSearch_reportList";
	 window.location.href = contextPath;
};


function doReturnUpload(){
	var contextPath = $("#contextPath").val() + "/initUpload";
	 window.location.href = contextPath;
};
function doEdit(){
	$('#saveTemplate').show();
	$('#editTemplate').hide();
	$(".lablePartBox").hide();
	$(".lablePointBox").hide();
	$(".textPartBox").show();
	$(".textPointBox").show();
};


