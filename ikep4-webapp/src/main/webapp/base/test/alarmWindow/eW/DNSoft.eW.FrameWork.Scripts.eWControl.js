function fn_ChkAll_OnClick(GridID, Column, ChkBox) {
	var gridid = GridID.id;
	var grid = igtbl_getGridById(gridid);
	var ChkBoxCtrl = eval(ChkBox);
	if(ChkBoxCtrl.checked == true){
		for(i=0;i<grid.Rows.length;i++){
			var row = grid.Rows.getRow(i);
			var cell = row.getCell(Column);
			var column = cell.Column;
			if(row.getCellByColumn(column).isEditable() == true){
				row.getCellByColumn(column).setValue(true);
			}
		}
	}
	else{
		for(i=0;i<grid.Rows.length;i++){
			var row = grid.Rows.getRow(i);
			var cell = row.getCell(Column);
			var column = cell.Column;
			if(row.getCellByColumn(column).isEditable() == true){
				row.getCellByColumn(column).setValue(false);
			}
		}
	}
}

function fn_GridCellLink_onClick(linkMethod, linkURL, linkParam, modalFeature, frameName){
	if(linkMethod == "Modal"){
		fn_OpenModalDialog(linkURL + "?" + linkParam, modalFeature);
	}
	else if(linkMethod == "Frame"){
		var oFrame;
		oFrame = eval("parent." + frameName);
		oFrame.location.href = linkURL + "?" + linkParam;
	}
	else if(linkMethod == "Self"){
		self.location.href = linkURL + "?" + linkParam;
	}
}