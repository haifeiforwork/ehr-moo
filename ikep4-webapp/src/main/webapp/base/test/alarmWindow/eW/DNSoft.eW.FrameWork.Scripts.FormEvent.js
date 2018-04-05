/*****************************************
작성목적	: 공통 폼 속성 및 이벤트 설정
작 성 자	: 유승호( (주) 인터데브 )
최초작성일	: 2004.01.07
*****************************************/

/*****************************************
작성목적	: 화면 처리 ASPX가 클라이언트에 Load된 후 실행해야 될 로직 처리
작 성 자	: 유승호( (주) 인터데브 )
최초작성일	: 2004.01.07
*****************************************/
function fn_WindowOnLoad(changeDomain){
    	
	try{
	    
	    // 포탈과 연동을 위해 Document.Domain을 일치 시킨다.(2008.03.13 김학진)
	    // Message, Confirm, Information은 제외(Open Modal에서 window.dialogArguments 전달이 않됨)
	    if(changeDomain != false)
	    {
			var docDomain = document.domain;	    
			docDomain = docDomain.substr(docDomain.indexOf(".") + 1);	    
			document.domain = docDomain;	
	    }
	
		// 1. 에러 메시지가 있는 경우 팝업 출력
		if ( document.all.errorMessage.value.length > 0 )
			fn_OpenErrorMessage(document.all.errorMessage.value);
		
		// 2. Information이 있는 경우 팝업 출력
		if ( document.all.informationMessage.value.length > 0 )
			fn_OpenInformation(document.all.informationMessage.value);
		
		// 3. Confirm이 있는 경우 팝업 출력
		if ( document.all.confirmMessage.value.length > 0 )
			return fn_OpenConfirm(document.all.confirmMessage.value);
		
		// 4. 팝업창 닫기
		var strClose = "";
		strClose = document.all.winClosed.value;
		if ( strClose == "closed" ){
			if ( document.all.winClosedReturn.value.length > 0 )
					window.returnValue = document.all.winClosedReturn.value;
			window.close();
			return;
		}
		// 5. 백스페이스로 이전 페이지로 가는 것을 막는다.
		document.onkeydown = fn_PreventNavigateBack;
		
		// 6. 오른쪽 마우스 처리
		document.oncontextmenu = fn_PreventRightMouse;
	}
	catch(exception){fn_OpenErrorMessage(exception.description);}
	
	try{
		// 7. 폼 Unload 확인
		document.onbeforeunload = fn_ClosingCheck;
		
		// 8. 사용자 정의 폼 로드 함수 호출
		FormLoad();
	}
	catch(exception){} //위두함수에는 exception처리가 있으면 안됨.. 함수가 존재하지 않을수도 있기땜시...
}

/*****************************************
작성목적	: Window_OnUnLoad 이벤트 발생전에 발생되는 이벤트, 페이지 닫기 취소를 할 수 있다.
		  OnBeforeUnLoad 이벤트에 별도의 이벤트 핸들러를 연결하여 처리한다.
		  ex) window.onbeforeunload = fnClosingChecker;
작 성 자	: 유승호( (주) 인터데브 )
최초작성일	: 2004.01.07
*****************************************/
function fn_ClosingCheck(){
	try{
		var strMsg = FormBeforeUnLoad();
		if ( strMsg.length > 0 ){
			strMsg = "\n" + strMsg;
			return strMsg;
		}
	}
	catch ( exception ){}
}

/*****************************************
작성목적	: 텍스트 박스 이외는 backspace 입력을 제한한다.
작 성 자	: 유승호( (주) 인터데브 )
최초작성일	: 2004.01.07
*****************************************/
function fn_PreventNavigateBack(){
	//작업은 하지만.... "뒤로"버튼을 누르면.. 우짜면 되지????
	//메뉴자체를 없애든지 해야할듯하네.... 메뉴자체를 없애면 또 반항할 무리들이 많을낀데... 고민스럽따..-_-
	var strTagType;
	if ( window.event.keyCode == 8 ){
		if ( window.event.srcElement.tagName.toUpperCase() == "INPUT" ){
			strTagType = window.event.srcElement.getAttribute("type").toUpperCase();
			if ( strTagType == "TEXT"){
				window.event.returnValue = true;
				return;
			}
		}
		else if( window.event.srcElement.tagName.toUpperCase() == "TEXTAREA" ){
			window.event.returnValue = true;
			return;
		}
		else{
			// 일단 TextArea에서의 벡스페이스가 않되기 때문에 이 기능을 막는다. - 정기남			window.event.returnValue = false;
		}
	}
	else{
		window.event.returnValue = true;
	}
}

/*****************************************
작성목적	: 오른쪽 마우스 이벤틀를 막는다.
작 성 자	: 유승호( (주) 인터데브 )
최초작성일	: 2004.01.07
*****************************************/
function fn_PreventRightMouse(){
	//window.event.returnValue = false;	
}

/*****************************************
작성목적	: 오른쪽 마우스 이벤틀를 막는다.
작 성 자	: 유승호( (주) 인터데브 )
최초작성일	: 2004.01.07
*****************************************/
function fn_SetCursor(toggle){
	//var nTemp = document.all.length;
	var oTemp = document.body;
	if(oTemp != null)
	{
		if(toggle){
			oTemp.style.cursor = "wait";
		}
		else{
			oTemp.style.cursor = "default";
		}
	}
}

/*****************************************
작성목적	: 그리드에서 LinkCell에 마우스 올렸을때, TR에 스타일 지정하기작 성 자	: 유승호( (주) 인터데브 )
최초작성일	: 2004.01.07
*****************************************/
function fn_GridLinkCellStyleMouseOver(oThis,strTemp){
	var oTemp;
	oTemp = oThis;
	for(i = 0 ; i < 10 ; i++){
		oTemp = oTemp.parentElement;
		if(oTemp.tagName.toUpperCase() == 'TR'){
			oTemp.className = strTemp;
			return;
		}
	}
}