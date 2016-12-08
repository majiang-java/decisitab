<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<link href="plug-in/bootstrap/css/bootstrap.min.css" type="text/css" rel="stylesheet" />
<link href="plug-in/handsontable/handsontable.full.css" type="text/css" rel="stylesheet" />
<script src="plug-in/jquery/jquery-1.8.3.min.js"></script>
<script src="plug-in/bootstrap/js/bootstrap.min.js"></script>
<script src="plug-in/handsontable/handsontable.full.js"></script>
<style type="text/css">
	body{
		background-color: #f5f5f5;
	}
	.form-actions{
		margin-top:0px;
		margin-bottom:0px;
	}
</style>
</head>
<body>
<div>
	<div class="form-actions">
	  <button id="save" class="btn btn-primary">保存</button>
	  <button id="cancel" type="button" class="btn">取消</button>
	  <div id="errorMsg"></div>
	</div>
	<input type="hidden" id="tableid" value="${id }"> 
    <div id="example"></div>
  </div>


<script>

$(function(){
	$(window.parent.document).find(".ui_buttons").hide();
	var hot;
	var head;
	var body;
	var rightFlag;
	var view;
	var save = $("#save")[0];
	var id = '${id}';
	var mergeddata;
	var mergedCreater = [];
	$.getJSON("brmsRuleTableController.do?getDecitableData",{id:id},
		function(data) {
			
 			var contact = JSON.parse(data);  
 			var obj =  JSON.parse(contact.obj);
 			head = obj.head; 
			body = obj.body;
			mergeddata = obj.merged;
			
			var premergedData = mergeddata[0];
			mergedCreater = createMergeArea(premergedData);
		
			rightFlag = contact.attributes.decitableHead;
			//if(rightFlag){
			body = head.concat(body);
			//}
			var totel = head.concat(body);
			var container = document.getElementById('example');
			var width = totel[0].length;
			var height = totel.length;
			console.log(mergedCreater);
			
		   hot = new Handsontable(container, {
				startRows : width,
				startCols : height,
				rowHeaders : true,
				colHeaders : true,
				minSpareRows : 1,
				contextMenu : true,
				//readOnly:true,
				data : body,
				mergeCells:mergedCreater
			
			});
		   if(!rightFlag){
		   setTimeout(function(){
			   var $mainrows = $(".ht_master").find(".wtHolder").find(".htCore").find("tbody").find("tr");
			   var $leftrows = $(".ht_clone_left").find(".wtHolder").find(".htCore").find("tbody").find("tr");
			   for(var i = 0; i<7; i++){
				   $($mainrows[i]).hide();
				   $($leftrows[i]).hide();
			   }
			   var tempNum = 0;
			   var td7 =  $($mainrows[7]).find("td");
			   td7.each(function(c){
				   var tempText = $(this).text();
				 
				   if(tempText=="贷款类型"||tempText=="模型类型"){
					   tempNum = c;
				   }
			   });
		
			   $mainrows.each(function(){
				   $($(this).find("td")[tempNum]).hide();
			   });
			   
			   var topTr = $(".ht_clone_top").find("table.htCore").find("tr").find("th");
			   var topMaster = $(".ht_master").find("table.htCore").find("thead").find("tr").find("th");
			   $(topTr[tempNum]).hide();
			   $(topMaster[tempNum]).hide();
			   
		   },50);
		   }

		});
		
		Handsontable.Dom.addEvent(save, 'click', function() {
		  // save all cell's data
		  console.log(JSON.stringify(hot.mergeCells.mergedCellInfoCollection));
		  var ccp =  hot.getData();
		  ccp.pop();
		  var curdata = JSON.stringify( ccp);
		  var mergedata = JSON.stringify(getMergedArea());
		  console.log("mergedata"+mergedata);
		  $.post('brmsRuleTableController.do?saveDecitable', {id: id,data: curdata,mergedata:mergedata}, function (res) {
			    var response = JSON.parse(res);
				
			    if (response.success === true) {
			    	$("#errorMsg").addClass("alert").addClass("alert-success").text(response.msg);
			    	//$(windows.parent.document).find(".ui_close").click();
			    	var win = frameElement.api.opener;
					win.location.reload();
			    }
			    else {
			    	$("#errorMsg").addClass("alert").addClass("alert-error").text(response.msg);
			    }
		  });
		});
	
		$("#cancel").click(function(){
			var win = frameElement.api.opener;
			win.location.reload();
		});
		
		function createMergeArea(premergedData){
			for(var i = 0; i < premergedData.length; i++){
				var obc = {};
				var reader = $.parseJSON(premergedData[i]);
				obc.row = reader.firstRow;
				obc.col = reader.firstColumn;
				obc.rowspan = reader.lastRow-reader.firstRow+1;
				obc.colspan = reader.lastColumn-reader.firstColumn+1;
				mergedCreater.push(obc);
		    }
			return mergedCreater;
		}
		
	 	function getMergedArea(){
	 		 console.log(JSON.stringify(hot.mergeCells.mergedCellInfoCollection));
	 		 var array = hot.mergeCells.mergedCellInfoCollection;
	 		 var mergeData = [];
	 		 for(var i = 0; i< array.length; i ++){
	 			 var mege = {};
	 		//	obc.row = reader.firstRow;
			//	obc.col = reader.firstColumn;
			//	obc.rowspan = reader.lastRow-reader.firstRow+1;
			//	obc.colspan = reader.lastColumn-reader.firstColumn+1;
	 			 mege.firstRow = array[i].row;
	 			 mege.firstColumn = array[i].col;
	 			 mege.lastRow = array[i].row + array[i].rowspan - 1;
	 			 mege.lastColumn = array[i].col + array[i].colspan - 1;
	 			 mergeData.push(mege);
	 		 }
	 		 return mergeData;
		} 
	});


</script>
</body>
</html>
