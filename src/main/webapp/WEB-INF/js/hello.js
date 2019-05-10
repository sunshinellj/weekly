$(document).ready(function(){ 
    //�Ƶ��ұ�
    $('#add').click(function() {
    //��ȡѡ�е�ѡ�ɾ��׷�Ӹ�Է�
        $('#select1 option:selected').appendTo('#select2');
    });
    //�Ƶ����
    $('#remove').click(function() {
        $('#select2 option:selected').appendTo('#select1');
    });
    //ȫ���Ƶ��ұ�
    $('#add_all').click(function() {
        //��ȡȫ����ѡ��,ɾ��׷�Ӹ�Է�
        $('#select1 option').appendTo('#select2');
    });
    //ȫ���Ƶ����
    $('#remove_all').click(function() {
        $('#select2 option').appendTo('#select1');
    });
    //˫��ѡ��
    $('#select1').dblclick(function(){ //��˫���¼�
        //��ȡȫ����ѡ��,ɾ��׷�Ӹ�Է�
        $("option:selected",this).appendTo('#select2'); //׷�Ӹ�Է�
    });
    //˫��ѡ��
    $('#select2').dblclick(function(){
       $("option:selected",this).appendTo('#select1');
    });
    
    $( "#datepicker" ).datepicker({
    	appendText: '(yyyy-mm-dd)',
    	changeYear: true,
    	changeMonth: true,
    	option: $.datepicker.regional['zh-CN']
    });
    
    $.extend( $.fn.dataTable.defaults, {
        "searching": false
    } );
    
    $('#example').dataTable({
    	/*"aoColumnDefs":[
    	{"sClass":"col_class","aTargets":[0]},{"sClass":"cos_class","aTargets":[1]}]*/
    	"aoColumns": [null,
    	              {"sTitle": "<input type='checkbox' id='selectAll'></input>","sClass": "text-center"},
    	              null,
    	              null,
    	              null,
    	              null],
    	"aoColumnDefs":[{
    		"bVisible": false, 
    		"aTargets": 0,
    	},
    	{
    		"orderable":false,
    		"aTargets": 1
    	}],
    	iDisplayLength : 20
    });
    
    $('#add1').click(function(){
    	// 得到表格ID=TbData的jquery对象        
        var vTb=$("#tblData");
        // 所有的数据行有一个.CaseRow的Class,得到数据行的大小 
        var vNum=$("#tblData tr").filter(".CaseRow").size();
        if (vNum >= 15) {
        	return;
        }
        // 最后一行行号
        var lastNum = vNum - 1;
        // 表格有多少个数据行  
        var vTr=$("#tblData #tRow"+lastNum); 
        // 得到表格中的第一行数据          
        var vTrClone=vTr.clone(true);
        // 设置bottom class
        vTr.find("#add1").css("display", "none");
        vTr.find("#remove1").css("display", "none");
        // 创建第一行的副本对象vTrClone 
        vTrClone[0].id="tRow"+vNum;
        vTrClone.find("#remove1").css("display", "block");
        // 把副本单元格对象添加到表格下方    
        vTrClone.appendTo(vTb);   
        
    });
    
    $('#remove1').click(function(){
    	// 表格有多少个数据行
    	var vNum=$("#tblData tr").filter(".CaseRow").size(); 
    	if (vNum<=2) { 
    		alert('请至少留一行'); 
    		return; 
    	} 
        
    	// 得到点击的按钮对象
    	var vbtnDel = $(this); 
    	// 得到父tr对象
    	var vTr = vbtnDel.parent("tr"); 
    	if (vTr.attr("id") == "tRow0") {
    		//第一行是克隆的基础，不能删除
    		alert('第一行不能删除!');  
    		return; 
    	} else { 
    		vTr.remove(); 
        	var prevNum = vNum - 2;
            var prevTr = $("#tblData #tRow" + prevNum); 
            prevTr.find("#add1").css("display", "block");
            vTr.find("#remove1").css("display", "none");
            if (prevNum  <= 4) {
            	prevTr.find("#remove1").css("display", "none");
            } else {
            	prevTr.find("#remove1").css("display", "block");
            }
    	} 
    });
});