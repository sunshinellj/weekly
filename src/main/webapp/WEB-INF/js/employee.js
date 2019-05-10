$(document).ready(function () {
    //添加方法
    $.validator.addMethod("hasNumber", function (value, element, params) {
        if (params) {
            //不含数字
            if (!(/\d/ig.test(value))) {
                //不含标点等特殊字符
                var re = /[，,。./、《<>》？?;；:：’'“"\[\]\{\}\{\}\【\】}~~！!@@#￥$%%……^&&*(（）)-——+=.]/g;
                if (!re.test(value)) {
                    return true;
                }
            }
        }
    }, "输入的名字不合法");
    //表单元素的验证
    $("#employee").validate({
        rules: {
            employeeId: {
                required: true,
                maxlength: 6,
                digits: true
            },
            employeeName: {
                required: true,
                maxlength: 10,
                hasNumber: true
            }

        },
        messages: {
            employeeId: {
                required: "请输入员工号",
                maxlength: "最多只能输入6位",
                digits: "请输入整数"
            },
            employeeName: {
                required: "请输入员工姓名",
                maxlength: "最多只能输入10位"
            }
        }
    });

    $.extend($.fn.dataTable.defaults, {
        "searching": false
    });

    $('#employeeList').dataTable({
        "oLanguage": {
            "sZeroRecords": "没有您要搜索的内容",
            "sLengthMenu": "每页显示  10 条记录",
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
        "aoColumnDefs": [{
            "orderable": false,
            "aTargets": [3, 4]
        }],
        iDisplayLength: 10
    });

    //点击查询
    $("#search").click(function () {
        $("#employee").attr("action", "doSearchEmployee");
        $("#employee").submit();
    });

    //重载页面时会执行
    $("#divLoginWindow").hide();
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
        "left": getPosLeft,
        "top": getPosTop
    });
    // 当浏览器窗口大小改变时
    $(window).resize(function () {
        screenwidth = $(window).width();
        screenheight = $(window).height();
        mytop = $(document).scrollTop();
        getPosLeft = screenwidth / 2 - 200;
        getPosTop = screenheight / 2 - 150;
        $("#divLoginWindow").css({
            "left": getPosLeft,
            "top": getPosTop + mytop
        });
    });
    // 点击关闭按钮
    //注意方法是这样调用的
    $("#closeBtn").click(function () {
        closeWindow();
    });
    $('#saveBtn').click(function () {
        $("#employee").attr("action", "addEmployee");
        $("#employee").submit();
        //获取页面上的错误信息
        var errors = $(".error").text();
        if (errors == "") {
            closeWindow();
        }
    });

    //清空按钮
    $("#clearBtn").click(function () {
        $("#divLoginWindow").find("input[type='text']").val("");
    });

    //返回
    $('#return').click(function () {
        var contextPath = $("#contextPath").val() + "/returnSearch_reportList";
        window.location.href = contextPath;
    });

});

function doAddEmployee() {
    $("#flag").val("add");
    //动态设置弹出窗口的位置
    showTip();
    // 获取页面文档的高度
    var docheight = $(document).height();
    // 追加一个层，使背景变灰
    $("body").append("<div id='grey'></div>");
    $("#grey").css({
        "opacity": "0.3",
        "height": docheight
    });
    return false;
};

//在鼠标显示一个层
function showTip() {
    var div = document.getElementById('divLoginWindow'); // 将要弹出的层
    $("#divLoginWindow").toggle("fast");
    div.style.display = "block"; // div初始状态是不可见的，设置可为可见
};

//关闭窗口
function closeWindow() {

    $("#divLoginWindow").toggle("fast");
    // 删除变灰的层
    $("#grey").remove();
    //清空数据
    $("#divLoginWindow").find("input[type='text']").val("");
    //去除只读属性
    $("#employeeId").removeAttr("readonly");
    //删除错误提示信息
    $("label.error").remove();
    return false;

};

$(".edit").click(function () {
    //给flag赋值
    $("#flag").val("edit");
    var userId = $(this).parents("tr").children("td:eq(0)").text();
    var userName = $(this).parents("tr").children("td:eq(1)").text();
    var groupId = $(this).parents("tr").children("td:eq(5)").text();
    var authority = $(this).parents("tr").children("td:eq(6)").text();
    $("#employeeId").val(userId);
    $("#employeeName").val(userName);
    $("#groupId").val(groupId);
    $("#authority").val(authority);
    //设置员工号只读
    $("#employeeId").attr("readonly", "readonly");
    //动态设置弹出窗口的位置
    showTip();

    // 获取页面文档的高度
    var docheight = $(document).height();
    // 追加一个层，使背景变灰
    $("body").append("<div id='grey'></div>");
    $("#grey").css({
        "opacity": "0.3",
        "height": docheight
    });
    return false;
});

$(".delete").click(function () {
    if (confirm("确定要删除该条信息吗")) {
        var userNo = $(this).parents("tr").children("td:eq(0)").text();
        $("#userNo").val(userNo);
        var loginUserId=$("#loginUserId").val();
        if(userNo==loginUserId){
            alert("你不能删除当前登录用户");
        }else{
            $("#employee").attr("action", "deleteUser");
            $("#employee").submit();
        }
    }

});


