$(document).ready(function(){
	$.extend($.fn.dataTable.defaults, {
		"searching" : false
	});
	$('#reportList').dataTable({
		"oLanguage" : {
			"sZeroRecords" : "没有您要搜索的内容",
			"sLengthMenu" : "每页显示  10 条记录",
			"sInfo": "当前显示 _START_ 到 _END_ 条，共 _TOTAL_ 条记录",
			"sInfoEmpty": "显示 0 到 0 条记录",
			"sInfoFiltered": "数据表中共为 _MAX_ 条记录",
            "oPaginate": {
                "sFirst": "首页",
                "sPrevious": "上一页",
                "sNext": "下一页",
                "sLast": "末页"
            }
		},
		"aoColumnDefs" : [ {
			"orderable" : false,
			"aTargets" : [2,3,4]
		} ],
		"aaSorting" : [ 0 ],
		iDisplayLength : 10
	});
	$("#datepicker5").datepicker({
		dateFormat : 'yy/mm/dd'

	});
	$("#datepicker6").datepicker({
		dateFormat : 'yy/mm/dd'
	});
	$("#datepicker3").datepicker({
		dateFormat : 'yy/mm/dd'

	});
	$("#datepicker4").datepicker({
		dateFormat : 'yy/mm/dd'
	});
	$("#datepicker1").datepicker({
		dateFormat : 'yy/mm/dd'

	});
	$("#datepicker2").datepicker({
		dateFormat : 'yy/mm/dd'
	});
	$(function() {
		$("#divLoginWindow").hide();
		$("#divEditWindow").hide();
		var screenwidth, screenheight, mytop, getPosLeft, getPosTop;
		screenwidth = $(window).width();
		screenheight = $(window).height();
		// 获取滚动条距顶部的偏移
		mytop = $(document).scrollTop();
		// 计算弹出层的left
		getPosLeft = screenwidth / 2 - 200;
		// 计算弹出层的top
		getPosTop = screenheight / 2 - 150;
		// css定位弹出层
		$("#divLoginWindow").css({
			"left" : getPosLeft,
			"top" : getPosTop
		});
		$("#divEditWindow").css({
			"left" : getPosLeft,
			"top" : getPosTop
		});
		// 当浏览器窗口大小改变时
		$(window).resize(function() {
			screenwidth = $(window).width();
			screenheight = $(window).height();
			mytop = $(document).scrollTop();
			getPosLeft = screenwidth / 2 - 200;
			getPosTop = screenheight / 2 - 150;
			$("#divLoginWindow").css({
				"left" : getPosLeft,
				"top" : getPosTop + mytop
			});
			$("#divEditWindow").css({
				"left" : getPosLeft,
				"top" : getPosTop + mytop
			});
		});
		// 点击关闭按钮
		//注意方法是这样调用的
		$("#closeBtn").click(function(){
			closeWindow();
		});
		$("#closeEditBtn").click(function(){
			closeEditWindow();
		});
		$('#saveBtn').click(function() {

			var startDate =  new Date(Date.parse($("#datepicker3").val()));
			if(startDate.getDay()==1) {

				if (checkEndTime()) {

					if (!checkSpaceTime()) {
						alert("时间间隔不能大于两周！");
					}
					else {
						$("#searchEvent").attr("action", "saveEvent");
						$("#searchEvent").submit();
						closeWindow();
					}
				}
				else{
					alert("结束时间必须晚于开始时间！");
				}
			}
			else{
				alert("开始日期请选择周一");
			}
		});
		$('#saveEditBtn').click(function() {

			var startDate =  new Date(Date.parse($("#datepicker5").val()));

			if(startDate.getDay()==1){
				if(checkEndTime()) {

					if (!checkSpaceTime()) {
						alert("时间间隔不能大于两周！");
					}
					else {
						$("#searchEvent").attr("action", "saveEditEvent");
						$("#searchEvent").submit();
						closeEditWindow();
					}
				}
				else{
					alert("结束时间必须晚于开始时间！");
				}
			}
			else{
				alert("开始日期请选择周一");
			}
		});
		//清空按钮
		$("#clearBtn").click(function(){
			$("#divLoginWindow").find("textarea").val("");
		});

		//判断开始日期是否是周一
		$("#datepicker3").change(function(){

			var startDate =  new Date(Date.parse($("#datepicker3").val()));
			if(startDate.getDay()!=1){
				alert("开始日期请选择周一");
			}
		});
		//判断修改页面开始日期是否是周一
		//判断开始日期是否是周一
		$("#datepicker5").change(function(){

			var startDate =  new Date(Date.parse($("#datepicker5").val()));
			if(startDate.getDay()!=1){
				alert("开始日期请选择周一");
			}
		});
		//返回
		$('#return').click(function() {
			var contextPath = $("#contextPath").val() + "/returnSearch_reportList";
			 window.location.href = contextPath;
		});
	});
	//获取鼠标点击修改的那一行的开始、结束日期、事件说明

	$("#reportList tr").find('td:last').prev().click(function(){
		var startTime = $(this).parents("tr").children("td:eq(0)").text();
		var endTime =  $(this).parents("tr").children("td:eq(1)").text();
		var comment =  $(this).parents("tr").children("td:eq(2)").text();

		if (startTime != ""){
			$("#deleteEventStartDate").val(startTime);
			$("#deleteEventEndDate").val(endTime);
			$("#divEditTable").find("input").get(0).setAttribute("value",startTime);
			$("#divEditTable").find("input").get(1).setAttribute("value",endTime);
			$("#eventEditTextArea").val(comment);
		}
	});
	//点击删除按钮
	$("#reportList tr").find('td:last').click(function(){
		if (confirm("确定要删除该事件吗")) {
			var startTime = $(this).parents("tr").children("td:eq(0)").text();
			var endTime = $(this).parents("tr").children("td:eq(1)").text();
			if (startTime != "") {
				$("#deleteEventStartDate").val(startTime);
				$("#deleteEventEndDate").val(endTime);
				$("#searchEvent").attr("action", "deleteEvent");
				$("#searchEvent").submit();
			}
		}
	});

});

function doAddEvent() {
	//动态设置弹出窗口的位置
	showTip();
	// 获取页面文档的高度
	var docheight = $(document).height();
	// 追加一个层，使背景变灰
	$("body").append('<div id=\'grey\'></div>');
	$("#grey").css({
		"opacity" : "0.3",
		"height" : docheight
	});
	return false;
}
//关闭窗口
function closeWindow() {
	$("#divLoginWindow").toggle("fast"); // //hide();
	// 删除变灰的层
	$("#grey").remove();

	return false;
}
function closeEditWindow() {
	$("#divEditWindow").toggle("fast"); // //hide();
	// 删除变灰的层
	$("#grey").remove();

	return false;
}
//获取鼠标点击的位置
//在鼠标显示一个层，该层的内空为div2的内容
function showTip() {
	var div = document.getElementById('divLoginWindow'); // 将要弹出的层
	$("#divLoginWindow").toggle("fast");
	div.style.display = "block"; // div初始状态是不可见的，设置可为可见
}
function showEditTip() {
	var div = document.getElementById('divEditWindow'); // 将要弹出的层
	$("#divEditWindow").toggle("fast");
	div.style.display = "block"; // div初始状态是不可见的，设置可为可见
}
function doEventEdit() {

	//动态设置弹出窗口的位置
	showEditTip();
	// 获取页面文档的高度
	var docheight = $(document).height();
	// 追加一个层，使背景变灰
	$("body").append('<div id=\'grey\'></div>');
	$("#grey").css({
		"opacity" : "0.3",
		"height" : docheight
	});
	return false;
}
//检查结束日期是否小于开始日期
function checkEndTime(){
	var startTime = $("#datepicker3").val();
	var start=new Date(startTime.replace("-", "/"));
	var endTime=$("#datepicker4").val();
	var end=new Date(endTime.replace("-", "/"));
	var editStartTime=$("#datepicker5").val();
	var editStart=new Date(editStartTime.replace("-", "/"));
	var editEndTime=$("#datepicker6").val();
	var editEnd=new Date(editEndTime.replace("-", "/"));
	return !(end < start || editEnd < editStart);

}

//检查间隔时间是否小于2周
function checkSpaceTime(){
	var startDate =  new Date(Date.parse($("#datepicker3").val()));
	var endDate =  new Date(Date.parse($("#datepicker4").val()));

	var startEditDate =  new Date(Date.parse($("#datepicker5").val()));
	var endEditDate =  new Date(Date.parse($("#datepicker6").val()));
	return !(((endDate - startDate) / (1000 * 60 * 60 * 24)) >= 14 || ((endEditDate - startEditDate) / (1000 * 60 * 60 * 24)) >= 14);

}