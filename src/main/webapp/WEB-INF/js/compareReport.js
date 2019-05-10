$(document).ready(function(){
	
	 document.body.style.overflowX='hidden';
	
	var preCount= $(".preTitle").length;
	var count =$(".title").length;
	
	//改变第二部分textarea不同时的颜色
	var oldTarea = document.getElementById("oldTextarea");
	var newTarea = document.getElementById("newTextarea");
	//去掉回车、换行、空格
	var oldTareaValue = (oldTarea.value).replace(/[ ]/g,"").replace(/[\r\n]/g,"");
	var newTareaValue = (newTarea.value).replace(/[ ]/g,"").replace(/[\r\n]/g,"");
	
	if(oldTareaValue==newTareaValue){
		//alert("相同");
	}
	else if(newTarea.value==""){
		oldTarea.style.color="blue";
	}else{
		newTarea.style.color="red";

	}
	
	for(var i =0,j=0;i<preCount,j<count;i++,j++){
		
		
		
		var oldTable = document.getElementById("oldTable"+i);
		var newTable = document.getElementById("newTable"+j);

		var trCount= oldTable.rows.length;
		
		
		var newtrCount= newTable.rows.length;
		
		var nCount =newtrCount;
		var oCount =trCount;
		
		
		
		var preTitleValue = $(".preTitle").get(i).textContent;
		var titleValue =$(".title").get(j).textContent;
		//截取"、"后面的二级提纲内容
		var preIndex = preTitleValue.indexOf("、");
		var newIndex = titleValue.indexOf("、");
		var slipPreTitleValue =preTitleValue.substr(preIndex + 1,preTitleValue.length);
		var slipTitleValue = titleValue.substr(newIndex + 1,titleValue.length);
		//判断上周项目个数比本周项目个数少的情况
		if(j+1<count){
			if((slipPreTitleValue==($(".title").get(j+1).textContent).substr(preIndex + 1,preTitleValue.length))&&slipTitleValue!=$(".preTitle").get(j-1).textContent.substr(newIndex + 1,titleValue.length)){
				
				$("#createPreTable"+(j-1)).after('<p class="preTitle" id="createP" style="color:blue">***********上周缺少的项目*************</p>');
				
				$("#createP").after('<table class="main" style="width: 100%; table-layout: fixed" id="createTable"></table>');
				
				var cCount =newtrCount;
				while(cCount>0){
					var createTable = document.getElementById("createTable");
					var newRow =createTable.insertRow(0);
					var newCell =newRow.insertCell(0);
					newCell.innerHTML=" ";
					newCell.height=103.333;
					cCount--;
				}
				newTable = document.getElementById("newTable"+(j+1));
				newtrCount = newTable.rows.length;
				nCount =newtrCount;
				j++;
			}
		}
		//判断本周周报项目个数比上周项目个数少的情况
		if(i+1<preCount){
			if((slipTitleValue==$(".preTitle").get(i+1).textContent.substr(newIndex + 1,titleValue.length))&&slipPreTitleValue!=$(".title").get(j-1).textContent.substr(preIndex + 1,preTitleValue.length)){
				
				$("#createTable"+(i-1)).after('<p class="preTitle" id="createP" style="color:red">***********本周缺少的项目*************</p>');
				
				$("#createP").after('<table class="main" style="width: 100%; table-layout: fixed" id="createTable"></table>');
				
				var cCount =trCount;
				while(cCount>0){
					var createTable = document.getElementById("createTable");
					var newRow =createTable.insertRow(0);
					var newCell =newRow.insertCell(0);
					newCell.innerHTML=" ";
					newCell.height=103.333;
					cCount--;
				}
				oldTable = document.getElementById("oldTable"+(i+1));
				trCount = oldTable.rows.length;
				oCount =trCount;
				i++;
			}
		}
		if(trCount>=newtrCount)
			var index =trCount-newtrCount;
		else
			var index = newtrCount-trCount;
		//上周项目多的情况，本周相应项目补空格并对齐
		while(index>0){
			if(trCount>=newtrCount){
				var newRow =newTable.insertRow(nCount);
				var newCell =newRow.insertCell(0);
				
				newCell.innerText=" ";
				newCell.height=103.333;
				nCount++;
				index--;
			}
		//本周项目多的情况，上周相应项目补空格并对齐
			else{
				var newRow =oldTable.insertRow(oCount);
				var newCell =newRow.insertCell(0);
				newCell.innerHTML=" "
				newCell.height=103.333;
				oCount++;
				index--;
			}
		}
		
		//将本周和上周项目下不同的内容标上颜色，上周蓝色标记，本周红色标记
		if(trCount>=newtrCount){
			for(var m=0;m<newtrCount;m++){
				var tdOldValue = $("#oldTable"+i).find("textarea").get(m);
		
				var tdNewValue = $("#newTable"+j).find("textarea").get(m);
				
				var tdOldAreaValue = (tdOldValue.value).replace(/[ ]/g,"").replace(/[\r\n]/g,"");
				var tdNewAreaValue = (tdNewValue.value).replace(/[ ]/g,"").replace(/[\r\n]/g,"");
				if(tdOldAreaValue==tdNewAreaValue){
					//alert("相同");
				}
				else{
					tdNewValue.style.color="red";
				}
			}
			for(var k =newtrCount;k<trCount;k++){
				var tdOldValue = $("#oldTable"+i).find("textarea").get(k);
				tdOldValue.style.color="blue";
			}
		}
		else{
			for(var n=0;n<trCount;n++){
				var tdOldValue = $("#oldTable"+i).find("textarea").get(n);
		
				var tdNewValue = $("#newTable"+j).find("textarea").get(n);
				
				var tdOldAreaValue = (tdOldValue.value).replace(/[ ]/g,"").replace(/[\r\n]/g,"");
				var tdNewAreaValue = (tdNewValue.value).replace(/[ ]/g,"").replace(/[\r\n]/g,"");
				
				if(tdOldAreaValue==tdNewAreaValue){
					//alert("相同");
				}
				else{
					tdNewValue.style.color="red";
				}
			}
			for(var k =trCount;k<newtrCount;k++){
				var tdNewValue = $("#newTable"+j).find("textarea").get(k);
				tdNewValue.style.color="red";
			}
		}
	}	
	
	
	
});

function doReturnCompare(){
	
	window.history.back();
};