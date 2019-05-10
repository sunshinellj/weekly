$(document).ready(function() {

	
	$(".check").change(function(){
		checkValueRecord();
	});
	
	/**
	 * 创建周报按钮,这个方法是好的
	 */

	$('#new').click(function() {
		var contextPath = $("#contextPath").val() + "/newReport";
		window.location.href = contextPath;
	});
	/* 提交查询表单 */
	$('#search').click(function() {
		$("#searchReport").attr("action", "doSearch_reportList");
		$("#searchReport").submit();

	});

	$("#datepicker1").datepicker({
		dateFormat : 'yy/mm/dd'

	});
	$("#datepicker2").datepicker({
		dateFormat : 'yy/mm/dd'
	});
	
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
			"aTargets" : [ 0, 5, 6, 7 ]
		} ],
		//"aaSorting" : [ 0 ],
		iDisplayLength : 10
	});

	$("#logout").click(function(){
		if(confirm("您确认要退出周报系统吗？")){
			$("#searchReport").attr("action","doLogout");
			$("#searchReport").submit();
		}
		else
		{
			//do nothing
		}
	})
});

/* 根据番号删除周报 */
function doDelete(fileNo) {
	if (confirm("确定要删除该条周报吗")) {
		document.getElementById("fileId").setAttribute("value", fileNo);
		document.getElementById("searchReport").setAttribute("action",
				"delete_report");
		document.getElementById("searchReport").submit();
	} else {

	}

};
/**
 * 链接到修改周报页面
 */
function doReportEdit(fileNo) {
	document.getElementById("fileId").setAttribute("value", fileNo);
	document.getElementById("searchReport")
			.setAttribute("action", "editReport");
	document.getElementById("searchReport").submit();
};

/**
 * 链接到对比周报页面
 */
function doReportCompare(fileNo) {
	document.getElementById("fileId").setAttribute("value", fileNo);
	document.getElementById("searchReport").setAttribute("action",
			"compareReport");
	document.getElementById("searchReport").submit();
	
};
/* 周报列表页面实现全选功能 */
function doCheck() {
	var checkBoxes = getByClass("check");
	var checkOrder = document.getElementById("checkOrder");
	for (var i = 0; i < checkBoxes.length; i++) {
		if (checkOrder.checked) {
			checkBoxes[i].checked = true;
			// alert(checkBoxes[i].value);
		} else {
			checkBoxes[i].checked = false;
		}
	}
	checkValueRecord();
};
/* 通过class获取对象 */
function getByClass(sClass) {
	var aResult = [];
	var aEle = document.getElementsByTagName('*');
	for (var i = 0; i < aEle.length; i++) {
		/* 当className相等时添加到数组中 */
		if (aEle[i].className == sClass) {
			aResult.push(aEle[i]);
		}
	}
	return aResult;
};

function doInitUpload() {
	var contextPath = $("#contextPath").val() + "/initUpload";
	window.location.href = contextPath;
};

function doReportGuide() {
	var contextPath = $("#contextPath").val() + "/initReportGuide";
	window.location.href = contextPath;
};
// 合并周报
function doCombine() {
	// 判断复选框是否被选中
	var flag = false;
	var fileNos = document.getElementById("fileNos").value.toString().split(',');
	for (var i = 0; i < fileNos.length; i++) {
		//如果有不为空，说明复选框有被选中的
		if (fileNos[i] != "")
			flag = true;
	}
	
	if (!flag) {
		alert("请选中至少一条数据");
	} else {
		$("#searchReport").attr("action", "combine");
		$("#searchReport").submit();
	}
};
//实现记录多页复选框选中的值，并提交
//记录页面中选中的番号值
var CHECK_FILE = new Array();
var NUM = 0;
function checkValueRecord() {
	var checkBoxes = getByClass("check");
	// 遍历本页面的所有复选框
	for (var i = 0; i < checkBoxes.length; i++) {
		// 如果某个框被选中
		if (checkBoxes[i].checked == true) {
			var flag = true;
			// 检查选中框中是否有已经存入CHECK_FILE中的，如果有，则不再存放
			for (var j = 0; j < NUM; j++) {
				if (checkBoxes[i].value == CHECK_FILE[j])
					flag = false;
			}
			if (flag) {
				CHECK_FILE[NUM] = checkBoxes[i].value;
				NUM++;
			}
		} else {
			// 检查该框的值是否已经被存入CHECK_FILE，如果有，将该值赋空
			for (var j = 0; j < NUM; j++) {
				if (checkBoxes[i].value == CHECK_FILE[j])
					CHECK_FILE[j] = "";
			}
		}
	}
	document.getElementById('fileNos').value = CHECK_FILE;
}
