/*****************************************
작성목적	: Html Element 동작을 제어한다.
		Radio Group, ListBox, Image Button, TextBox, Control Move
*****************************************/
/*
작성목적	: text box에 입력값이 숫자일 경우에만 입력 받는다.
text box의 onkeypress 이벤트 헨들러로 추가한다.
ex) document.all.txtPrice.onkeypress = fn_CheckNumberBox
*/
function fn_CheckNumberTextBox(){
	try{
		// 허용키 : 8, 13, 27, 48 ~ 57
		if ( window.event.keyCode >= 48 && window.event.keyCode <= 57 ){
			window.event.returnValue = true;
			return;
		}
		if ( window.event.keyCode == 8 && window.event.keyCode == 13 && window.event.keyCode == 27 ){
			window.event.returnValue = true;
			return;
		}
		window.event.returnValue = false;
	} catch ( exception ) {fn_OpenErrorMessage(exception.description);}
}
/*
작성목적	: text box에 입력값이 소문자이면 대문자로 고친다.
ex) document.all.txtPrice.onkeypress = fn_CheckNumberBox
*/
function fn_ConvertToUpperCase(){
	try{
		// 소문자이면 대문자로 변경한다. ( 97(122) -> 65(90) )
		if ( window.event.keyCode >= 97 && window.event.keyCode <= 122 )
			window.event.keyCode = window.event.keyCode - 32;
	} catch (exception) {fn_OpenErrorMessage(exception.description);}	
}
/*
작성목적	: text box에 입력값이 소문자이면 대문자로 고친다.
		  ex) document.all.txtPrice.onkeypress = fn_CheckNumberBox
		  Parameter :
		  Return :
*/
function fn_ConvertToLowerCase(){
	try{
		// 소문자이면 대문자로 변경한다. ( 97(122) <- 65(90) )
		if ( window.event.keyCode >= 65 && window.event.keyCode <= 90 )
			window.event.keyCode = window.event.keyCode + 32;
	} catch (exception) {fn_OpenErrorMessage(exception.description);}	
}



/*
작성목적	: 실제 OBJECT 객체를 반환한다.
*/
function fn_GetHtmlObject(object, bChild)
{
	
	var rtnObject;
	var strTag;
	try{	
		// 기존 객체가 object인 경우 object를 반환한다.
		if(typeof(object) == "object")
			rtnObject = object;
		else		
			rtnObject = document.all[object];
		
		// 자식을 반환하는 경우 자식을 반환한다.
		if(bChild) {
			if(rtnObject.tagName == null)
			{
				strTag = rtnObject[0].tagName.toUpperCase();
			}
			else
			{			
				strTag = rtnObject.tagName.toUpperCase();
			}
			
			switch(strTag)
			{
				case "SELECT" :
					rtnObject = rtnObject.options;
					break;
				
				case "INPUT" :
				case "TEXTAREA" :
				case "DIV" :
				case "SPAN" :			
					rtnObject = rtnObject.children;
					break;								
			}
		}
		return rtnObject;
	} catch (exception) {fn_OpenErrorMessage(exception.description);}
}


/*
작성목적	: HTML 객체의 저장된 값을 가저온다.
*/
function fn_GetHtmlObjectValue(object)
{	
	var oItem;
	var strValue = "";
	var strTag;
	var strType;	
	try{				
		
		oItem = fn_GetHtmlObject(object);
		
		if(oItem != null)
		{
			if(oItem.tagName == null)
			{
				strTag = oItem[0].tagName.toUpperCase();
				if(oItem[0].getAttribute("type") != null)
					strType = oItem[0].getAttribute("type").toUpperCase();
			}
			else
			{			
				strTag = oItem.tagName.toUpperCase();
				if(oItem.getAttribute("type") != null)
					strType = oItem.getAttribute("type").toUpperCase();
			}
						
			switch(strTag)
			{
				case "INPUT" :
					switch(strType)
					{
						case "TEXT":
						case "HIDDEN":
							strValue = oItem.value;
							break;
							
						case "RADIO":							
							strValue = fn_GetRadioValue(oItem);
							break;
							
						case "CHECKBOX":							
							strValue = fn_GetCheckBoxValue(oItem);
							break;
					}
					break;
					
				case "SELECT" :
					strValue = fn_GetListBoxValue(oItem)
					break;
					
				case "TEXTAREA" :
					strValue = oItem.value;
					break;
					
				default :				
					strValue = oItem.innerHTML;
					break;								
			}
		}	
		return strValue;	
	} catch (exception) {fn_OpenErrorMessage(exception.description);}	
}

/*
작성목적	: ListBox의 선택된 항목 값을 가져온다.
*/
function fn_GetListBoxValue(selectBoxName){		
	try{	
		var oListBox = fn_GetHtmlObject(selectBoxName);
		if(oListBox.options.length > 0)		
			return oListBox[oListBox.selectedIndex].value;
		else
			return "";
	} catch (exception) {fn_OpenErrorMessage(exception.description);}	
}

/*
작성목적	: Radion 컨트롤의 이름을 문자열로 전달하면 해당 컨트롤 그룹을 검색하여
		  checked = true인 컨트롤의 value를 리턴한다.
*/
function fn_GetRadioValue(radioName){
	try{
		var iCount;
		var strValue;				
		var oRGroup = fn_GetHtmlObject(radioName);	
				
		if(oRGroup.checked != null){
			if(oRGroup.checked){
				strValue = oRGroup.value;
			}
		}
		else{
			iCount = oRGroup.length;
			for ( var i = 0 ; i < iCount ; i++ ){
				if ( oRGroup[i].checked){
					strValue = oRGroup[i].value;
					break;
				}
			}
		}
		return strValue;
	} catch (exception) {fn_OpenErrorMessage(exception.description);}	
}

/*
작성목적	: CheckBox 컨트롤의 이름을 문자열로 전달하면 해당 컨트롤 그룹을 검색하여
		  checked = true인 컨트롤의 value를 리턴한다.
*/
function fn_GetCheckBoxValue(checkName)
{
	try
	{
		var iCount;
		var arrValue = new Array();
		var strValue;
		var oCGroup = fn_GetHtmlObject(checkName);	

		var idx=0;
		if(oCGroup.checked != null)
		{
			if(oCGroup.checked)
			{
				arrValue[idx] = oCGroup.value;
			}
		}
		else
		{
			iCount = oCGroup.length;
			for ( var i = 0 ; i < iCount ; i++ )
			{
				if ( oCGroup[i].checked)
				{
					arrValue[idx] = oCGroup[i].value;
					++idx;
				}
			}
		}
		return arrValue;
	} catch (exception) {fn_OpenErrorMessage(exception.description);}	
}


/*
작성목적	: HTML 객체의 저장된 값을 설정한다.
*/
function fn_SetHtmlObjectValue(object, value)
{	
	var oItem;
	var strValue = "";
	var strTag;
	var strType;
	
	try{	
		oItem = fn_GetHtmlObject(object);	
		
		if(oItem != null)
		{
			if(oItem.tagName == null)
			{
				strTag = oItem[0].tagName.toUpperCase();
				if(oItem[0].getAttribute("type") != null)
					strType = oItem[0].getAttribute("type").toUpperCase();
			}
			else
			{			
				strTag = oItem.tagName.toUpperCase();
				if(oItem.getAttribute("type") != null)
					strType = oItem.getAttribute("type").toUpperCase();
			}
						
			switch(strTag)
			{
				case "INPUT" :
					switch(strType)
					{
						case "TEXT":
						case "HIDDEN":
							oItem.value = value;
							break;
							
						case "RADIO":							
							fn_SetRadioValue(oItem, value);
							break;
							
						case "CHECKBOX":							
							fn_SetCheckBoxValue(oItem, value);
							break;
					}
					break;
					
				case "SELECT" :
					fn_SetListBoxValue(oItem, value);
					break;
					
				case "TEXTAREA" :
					oItem.value = value;
					break;
					
				default :		
					oItem.innerHTML = value;
					break;								
			}
		}
	} catch (exception) {fn_OpenErrorMessage(exception.description);}	
}

/*
작성목적	: Select 컨트롤의 이름과 값을 문자열로 전달하면 해당 컨트롤 그룹을 검색하여
		  해당 값을 가진 컨트롤을 선택한다.
*/
function fn_SetListBoxValue(selectBoxName, sValue){		
	try{	
		var iCount;
		var oListBox = fn_GetHtmlObject(selectBoxName);		
		iCount = oListBox.length;
		for ( var i = 0 ; i < iCount ; i++ ){
			if ( oListBox[i].value == sValue ){
				oListBox[i].selected = true;
				break;
			}
		}
	} catch (exception) {fn_OpenErrorMessage(exception.description);}	
}



/*
작성목적	: Radion 컨트롤의 이름과 값을 문자열로 전달하면 해당 컨트롤 그룹을 검색하여
		  해당 값을 가진 컨트롤을 체크한다.
*/
function fn_SetRadioValue(radioName, sValue){
	try{
		var iCount;
		var oRGroup;		
		var oRGroup = fn_GetHtmlObject(radioName);
		
		iCount = oRGroup.length;
		for ( var i = 0 ; i < iCount ; i++ ){
			if ( oRGroup[i].value == sValue ){
				oRGroup[i].checked = true;
				break;
			}
		}
	} catch (exception) {fn_OpenErrorMessage(exception.description);}	
}


/*
작성목적	: CheckBox 컨트롤의 이름과 값을 문자열로 전달하면 해당 컨트롤 그룹을 검색하여
		  해당 값을 가진 컨트롤을 체크한다.
*/
function fn_SetCheckBoxValue(checkName, sValue){
	try{
		var iCount;
		var oCGroup;
		var oCGroup = fn_GetHtmlObject(checkName);
		
		iCount = oCGroup.length;
		for ( var i = 0 ; i < iCount ; i++ ){
			if(sValue == "ALLCHECKIN"){
				oCGroup[i].checked = true;
			}
			else if(sValue == "ALLCHECKOUT"){
				oCGroup[i].checked = false;
			}			
			else if ( oCGroup[i].value == sValue ){
				oCGroup[i].checked = true;
				break;
			}
		}
	} catch (exception) {fn_OpenErrorMessage(exception.description);}	
}


/*
작성목적	: ListBox에 삽입한다.
*/
function fn_AddListBoxItem(selectBoxName, sText, sValue){
	try{
		var oListBox = fn_GetHtmlObject(object);
		var oOption = document.createElement("option");
		oOption.text = sText;
		oOption.value = sValue;
		
		oListBox.add(oOption);
	} catch (exception) {fn_OpenErrorMessage(exception.description);}	
}






/*
작성목적	: 해당 이미지 Control을 disable시키고 마우스 모양을 default로 설정한다.
*/
function fn_DisableImageButton(oImgBtn){
	try{
		if(oImgBtn == null) { return; }
		oImgBtn.disabled = true;
		oImgBtn.style.cursor = "default";
		oImgBtn.style.filter = "progid:DXImageTransform.Microsoft.Alpha(opacity=30)";
	} catch (exception) {fn_OpenErrorMessage(exception.description);}	
}
/*
작성목적	: 해당 이미지 Control을 Enable시키고 마우스 모양을 hand로 설정한다.
*/
function fn_EnableImageButton(oImgBtn){
	try{
		if(oImgBtn == null) { return; }
		oImgBtn.disabled = false;
		oImgBtn.style.cursor = "hand";
		oImgBtn.style.filter = "progid:DXImageTransform.Microsoft.Alpha(opacity=100)";
	} catch (exception) {fn_OpenErrorMessage(exception.description);}	
}
/*
작성목적	: 해당 Attribute를 갖는 모든 체크박스를 Check한다.
*/
function fn_SetAllCheck(attrValue){
	var oItem;
	try{
		for ( var i = 0 ; i < document.all.length ; i++ ) {
			oItem = document.all[i];
			if ((oItem.tagName.toUpperCase() == "INPUT")
				&& (oItem.getAttribute("type").toUpperCase() == "CHECKBOX")
				&& (oItem.getAttribute("chkgrpname") != null)
				&& (oItem.getAttribute("chkgrpname") == attrValue))
			{
				oItem.checked = true;
			}
		}
	} catch (exception) {fn_OpenErrorMessage(exception.description);}	
}
/*
작성목적	: 해당 Attribute를 갖는 모든 체크박스를 UnCheck한다.
*/
function fn_SetAllUnCheck(attrValue){
	var oItem;
	try{
		for ( var i = 0 ; i < document.all.length ; i++ ){
			oItem = document.all[i];
			if ((oItem.tagName.toUpperCase() == "INPUT")
				&& (oItem.getAttribute("type").toUpperCase() == "CHECKBOX")
				&& (oItem.getAttribute("chkgrpname") != null)
				&& (oItem.getAttribute("chkgrpname") == attrValue))
			{
				oItem.checked = false;
			}
		}
	} catch (exception) {fn_OpenErrorMessage(exception.description);}	
}
/*
작성목적	: 해당 Attribute를 갖는 모든 체크박스 중 Check된 항의 배열을 가져온다.
*/
function fn_GetCheckedItems(attrValue){
	var oItem;
	var arrReturn;
	var iCnt = 0;
	var idx = 0;
	try{
		for ( var i = 0 ; i < document.all.length ; i++ ){
			oItem = document.all[i];
			if ((oItem.tagName.toUpperCase() == "INPUT")
				&& (oItem.getAttribute("type").toUpperCase() == "CHECKBOX")
				&& (oItem.getAttribute("chkgrpname") != null)
				&& (oItem.getAttribute("chkgrpname") == attrValue)
				&& (oItem.checked == true))
			{
				iCnt++;
			}
		}
		arrReturn = new Array(iCnt);
		for ( var i = 0 ; i < document.all.length ; i++ ){
			oItem = document.all[i];
			if ((oItem.tagName.toUpperCase() == "INPUT")
				&& (oItem.getAttribute("type").toUpperCase() == "CHECKBOX")
				&& (oItem.getAttribute("chkgrpname") != null)
				&& (oItem.getAttribute("chkgrpname") == attrValue)
				&& (oItem.checked == true))
			{
				arrReturn[idx++] = oItem;
			}
		}
		return arrReturn;
	} catch (exception) {fn_OpenErrorMessage(exception.description);}	
	return null;
}
/*
작성목적	: 해당 Control을 절대 위치로 옴긴다.
*/
function fn_MoveControl(oCtrl, posTop, posLeft){
	try{
		oCtrl.style.position = "absolute";
		oCtrl.style.top = posTop;
		oCtrl.style.left = posLeft;
	} catch (exception) {fn_OpenErrorMessage(exception.description);}	
}
/*
작성목적	: CheckBox List에서 Check될때 Highlight시킨다.
			  Web Control의 Checkbox List Attribute에 onclick 이벤트로 정의한다.
*/
function fn_HlightCheckboxList(){
	var oTd = null;
	try{
		oTd = window.event.srcElement.parentNode;
		if (window.event.srcElement.checked == true) { oTd.style.backgroundColor = '#ffffcc'; }
		else { oTd.style.backgroundColor = '#ffffff'; }
	} catch (exception) {fn_OpenErrorMessage(exception.description);return false}	
	return true;
}
/*
작성목적	: CheckBox List에서 Check될때 Highlight시킨다.
			  Web Control의 Checkbox List Attribute에 onclick 이벤트로 정의한다.
*/
function fn_CheckboxListLoad(checkboxList){
	var oTb = null;
	var oTr = null;
	var oTd = null;
	var oChk = null;
	try{	
		oTb = checkboxList;
		for ( var i = 0 ; i < oTb.rows.length ; i++ ){
			oTr = oTb.rows(i);
			oTd = oTr.cells(0);
			oChk = oTd.childNodes(0);
			if ( oChk.checked == true ) { oTd.style.backgroundColor = '#ffffcc'; }
			else { oTd.style.backgroundColor = '#ffffff'; }
		}
	} catch (exception) {fn_OpenErrorMessage(exception.description);}
}
function fn_ImgOverHandler(oImg){
	var strSrc = "";
	try{
		if(oImg != null){
			strSrc = oImg.getAttribute("OverImg");
			if(strSrc != null){ oImg.src = strSrc; }
		}
	} catch (exception) {fn_OpenErrorMessage(exception.description);}
}
function fn_ImgOutHandler(oImg){
	var strSrc = "";
	try{
		if(oImg != null){
			strSrc = oImg.getAttribute("OutImg");
			if(strSrc != null) { oImg.src = strSrc; }
		}
	} catch (exception) {fn_OpenErrorMessage(exception.description);}
}
function fn_GetPageHtml(oTarget, sUrl, bAsync, bOutput, oAsyncCallFunction)
{
	var sResponse = "";
	var oXmlDom = null;
	try{
		if(oTarget == null || sUrl == null || sUrl == "") { return; }
		if(bAsync == null) { bAsync = true; }
		if(bOutput == null) { bOutput = false; }
		if(bAsync && bOutput && oAsyncCallFunction != null) { oTarget.AsyncCallFunction = oAsyncCallFunction }
		if(oTarget.oXMLHttpReq != null) { oTarget.oXMLHttpReq = null; }
		oTarget.oXMLHttpReq = new ActiveXObject("Microsoft.XMLHTTP");
		oTarget.oXMLHttpReq.onreadystatechange = function(){fn_PageHtmlResponseHandler(oTarget);};
		oTarget.oXMLHttpReq.open("POST", sUrl, bAsync);
		oTarget.bOutput = bOutput;
		oTarget.oXMLHttpReq.send(null);
		if(!(bAsync)) { return oTarget.oResultXmlDom; }
	} catch (exception) {fn_OpenErrorMessage(exception.description);}
}

function fn_PageHtmlResponseHandler(oTarget)
{
	var sResponse = "";
	var oXmlDom = null;
	try{
		if(oTarget.oXMLHttpReq.readyState == 4 && oTarget.oXMLHttpReq.status == 200){
			sResponse = unescape(oTarget.oXMLHttpReq.responseText);
			oXmlDom = new ActiveXObject("Microsoft.XMLDOM");
			oXmlDom.async = false;
			oXmlDom.loadXML(sResponse);
			if(oTarget.AsyncCallFunction != null) {
				oTarget.AsyncCallFunction(oXmlDocm);
			}
			else {
				if(!(oTarget.bOutput)){
					if(oXmlDom.selectSingleNode("//STATE").text == "S"){
						oTarget.innerHTML = oXmlDom.selectSingleNode("//HtmlData").text;
					}
					else{fn_OpenErrorMessage(oXmlDom.selectSingleNode("//HtmlData").text);}
				}
				else { oTarget.oResultXmlDom = oXmlDom; }
			}
		}
    }
    catch (exception) {fn_OpenErrorMessage(exception.description);}
}