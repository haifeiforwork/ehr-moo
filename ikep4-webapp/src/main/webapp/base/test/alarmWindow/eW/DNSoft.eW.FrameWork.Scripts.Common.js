/*
작성목적 : Web Root Path를 반환한다.
*/
function fn_GetWebServer(){
	try{
		return WEBSERVER;
	} catch(exception) {fn_OpenErrorMessage(exception.description);}
}
function fn_GetWebRoot(){
	try{
		return WEBROOT;
		//아래부분은 좀더 봐야 할듯
		var strHref = document.location.href;
		var strPath;
		var arrPath;
		if(strHref.substring(0, 4).toUpperCase() == "HTTP") {strPath = strHref.substring(7, strHref.length);}
		else {strPath = strHref.substring(8, strHref.length);}
		arrPath = strPath.split("/");
		return "/" + arrPath[1] + "/";
	} catch(exception) {fn_OpenErrorMessage(exception.description);}
}
/*
작성목적	: Document Path를 반환한다. 
*/
function fn_DocumentPath(){
	try{
		var strHref = "";
		var strPath = "";
		var arrPath = null;
		strHref = document.location.href;
		arrPath = strHref.split("/");
		for ( var i = 0 ; i < arrPath.length - 1 ; i++ ) {strPath += arrPath[i] + "/";}
		return strPath;
	} catch(exception) {fn_OpenErrorMessage(exception.description);}
}
function fn_MessageBoxStyle()
{
	return "dialogWidth:" + DIALOGWIDTH + "px;dialogHeight:" + (DIALOGHEIGHT - DIALOGSMALLHEIGHT) + "px;status=no;scroll=no";
}
/*
작성목적	: 에러 메시지 상자를 띠운다.
Parameter	: sInfo - 출력할 메시지
*/
function fn_OpenErrorMessage(strTemp){
	try{
		fn_SetCursor(false);
		var strImsi;
		if(strTemp == null) {strImsi = window.document.all.errorMessage.value;}
		else {strImsi = strTemp;}
		fn_SetProgressbar(false);
		window.showModalDialog(fn_GetWebRoot() + "Common/Message/ErrorMessage.aspx",strImsi,fn_MessageBoxStyle());
	} catch(exception){alert(exception.description);}
}

/*
작성목적	: 작업정보 상자를 띠운다.
Parameter	: sInfo - 출력할 메시지
*/
function fn_OpenInformation(sInfo){
	try{
		sInfo = sInfo.replace(/&lt;/g, "<").replace(/&gt;/g, ">").replace(/&nbsp;/g, " ").replace(/<br>/g, "\n").replace(/<br\/>/g, "\n").replace(/<br \/>/g, "\n").replace(/<b>/g, "").replace(/<\/b>/g, "").replace(/<b\/>/g, "").replace(/<font color=\"red\">/g, "").replace(/<\/font>/g, "").replace(/<span class=\"txtTipToint\">/g, "").replace(/<\/span>/g, "");
		alert(sInfo);
		//window.showModalDialog( fn_GetWebRoot() + "Common/Message/InformationMessage.aspx", sInfo, fn_MessageBoxStyle());
	} catch(exception) {fn_OpenErrorMessage(exception.description);}
}

/*
작성목적	: 질문 상자를 띠운다.
Parameter	: sInfo - 출력할 메시지
Return		: "ok", "cancel"
*/
function fn_OpenConfirm(sInfo){
	try{
		var args = new Object();
		args.win = window;
		args.dlgtype= "Q";
		sInfo = sInfo.replace(/&lt;/g, "<").replace(/&gt;/g, ">").replace(/&nbsp;/g, " ").replace(/<br>/g, "\n").replace(/<br\/>/g, "\n").replace(/<br \/>/g, "\n").replace(/<b>/g, "").replace(/<\/b>/g, "").replace(/<b\/>/g, "").replace(/<font color=\"red\">/g, "").replace(/<\/font>/g, "").replace(/<span class=\"txtTipToint\">/g, "").replace(/<\/span>/g, "");
		var res = confirm(sInfo);//window.showModalDialog( fn_GetWebRoot() + "Common/Message/ConfirmMessage.aspx", sInfo,fn_MessageBoxStyle());
		return res;
	} catch(exception) {fn_OpenErrorMessage(exception.description);}
}
/*
작성목적	: 팝업창을 띠운다.
Parameter	: sUrl - 띠울 URL
			  sFrame - 띠울 Frame
			  sFeature - 창 속성
*/
function fn_OpenDialog(sUrl, sFrame, sFeature)
{
	return window.open(sUrl, sFrame, sFeature);	
}
/*
작성목적	: "답장,전체답장,전달"아이콘 클릭 시 Frame.aspx의 'ContentFrame'에서 페이지가 열린다. 
Parameter	: sUrl - 띠울 URL
			  sFrame - 띠울 Frame
			  sFeature - 창 속성
*/
function fn_LinkDialog(sUrl, sFrame, sFeature)
{
//        var getPath;
//        getPath=fn_GetWebRoot() + sUrl + sFrame + sFeature;
//        window.parent.frames['ContentFrame'].document.location.href  = getPath;
	window.parent.frames['ContentFrame'].document.location.href = sUrl;	
	
}
/*
작성목적	: 팝업창을 띠운다.
Parameter	: sUrl - 띠울 URL
			  sFeature - 창 속성
*/
function fn_OpenModalDialog(sUrl,sParam, sFeature){
	try{
		var strReturn = "";
		if(sFeature != null) {strReturn = window.showModalDialog( fn_GetWebRoot() + "Common/Dialog/ModalDialog.html?" + sUrl, sParam, sFeature);}
		else {strReturn = window.showModalDialog( fn_GetWebRoot() + "Common/Dialog/ModalDialog.html?" + sUrl, null, sParam);}
		return strReturn;
	} catch(exception){fn_OpenErrorMessage(exception.description);}
}
/*
작성목적	: 파라메터로 지정한 위치에 모달 카렌더를 띠운다.
Parameter	: oCtrl - 선택된 날짜가 리턴될 컨트롤 이름
			  posTop, posLeft = 위치
Return		: 날짜 ( YYYY-MM-DD )
*/
function fn_OpenModalCalendar(posTop, posLeft, posX, posY){
	var nWidth = 260;
	var nHeight = 240;
	try{
		if(window.event != null){
			if(posX == null) { posX = "R"; }
			if(posY == null) { posY = "T"; }
			if(posX != null){
				if(posX == "L") {posLeft = window.event.screenX;}
				else if(posX == "R") {posLeft = window.event.screenX - nWidth;}
			}
			if(posY != null){
				if(posY == "T") {posTop = window.event.screenY;}
				else if(posY == "B") {posTop = window.event.screenY - nHeight;}
			}
		}
		var strOpenUrl = fn_GetWebRoot() + "Common/Dialog/ModalCalendar.html";
		var strReturn = fn_OpenModalDialog(strOpenUrl, window, "dialogTop:" + posTop + "px;dialogLeft:" + posLeft + "px;dialogWidth:" + nWidth.toString() + "px;dialogHeight:" + nHeight.toString() + "px;status:no;help:no;scroll:no;resizable:no");
		if (strReturn == null) {return "";}
		else {return strReturn;}
	} catch ( exception ){return "";}
}
/*
작성목적	: 문자열을 숫자로 변환한다.
Parameter	: sNum - 숫자 문자열
Return		: 숫자
*/
function fn_GetInt(sNum){
	try{
		for(var i = 0 ; i < sNum ; i++ ){
			if ( sNum.substring(0, 1) == 0 ) {sNum = sNum.substring(1, sNum.length);}
			else {return parseInt(sNum);}
		}
		return parseInt(sNum);
	} catch (exception) {}	
}
/*
작성목적	: 숫자를 2자리 문자열로 변환한다.
Parameter	: iNum - 2자리 이하 숫자
Return		: 2자리 숫자 문자열
*/
function fn_LeadingZero(iNum){
	var strReturn;
	try{
		if ( iNum < 10 ) {strReturn = "0" + iNum;}
		else {strReturn = "" + iNum;}
	} catch (exception) {}
	return strReturn;
}
/*
작성목적	: PostBack을 일으킨다
Parameter	: targetForm - Submit 대상 폼
			  eventTarget - Target Element
			  eventArgs - 이벤트 파라메터
*/
function fn_RaisePostBack(targetForm, eventTarget, eventArgs) {
	try{
		targetForm.__EVENTTARGET.value = eventTarget.split("$").join(":");
		targetForm.__EVENTARGUMENT.value = eventArgs;
		targetForm.submit();
	} catch (exception) {fn_OpenErrorMessage(exception.description);}
}
/*
작성목적	: 문자열 길이제한을 체크한다.
Parameter	: strAddr - 전자우편 주소
			  ex)"홍길동"<nallari@interdev.co.kr>;"sicc"<nallari@interdev.co.kr>,nallari@interdev.co.kr;
*/
function fn_CheckEmailAddress(strAddr) {
	var arrAddr;
	var arrMatch;
	var strEmail;
	if (strAddr.length == 0) {return true;}
	arrAddr = strAddr.replace(/,/, ";").split(";");
	for (var i = 0; i < arrAddr.length; i++) {
		arrMatch = arrAddr[i].match(/^([^<>]*)<([^<>]+)>$/);
		if (arrMatch == null) {strEmail = arrAddr[i];}
		else {strEmail = arrMatch[2];}
		if(strEmail != null&&strEmail!=""){
			if (fn_CheckEmailSub(strEmail) == false) { return false; }
		}
	} 
	return true;
}
/*
작성목적	: e-mail 주소 계정 및 도메인을 체크한다.
			  외부에서 직접 호출하지 말고 fn_CheckEmailAddress 로부터 
			  호출받아 사용된다.
Parameter	: strEmail - 전자우편 주소
*/
function fn_CheckEmailSub(strEmail) {	
	//var arrMatch = strEmail.match(/^(\".*\"|[A-Za-z0-9_-]([A-Za-z0-9_-]|[\+\.])*)@(\[\d{1,3}(\.\d{1,3}){3}]|[A-Za-z0-9][A-Za-z0-9_-]*(\.[A-Za-z0-9][A-Za-z0-9_-]*)+)$/);
	// * 2009.09.16 mailalias에 아포스트로피(')도 포함하도록 수정
	var arrMatch = strEmail.match(/^(\".*\"|[A-Za-z0-9_-]([A-Za-z0-9_-]|[\+\.\'])*)@(\[\d{1,3}(\.\d{1,3}){3}]|[A-Za-z0-9][A-Za-z0-9_-]*(\.[A-Za-z0-9][A-Za-z0-9_-]*)+)$/);
	
	if (arrMatch == null) {return false;}
	var arrIP = arrMatch[2].match(/^\[(\d{1,3})\.(\d{1,3})\.(\d{1,3})\.(\d{1,3})\]$/);
	if (arrIP != null) {
		for (var i = 1; i <= 4; i++) {
			if (arrIP[i] > 255) {return false;}
   		}
	}
	return true;
}
/*
작성목적	: 문자열 길이제한을 체크한다.
Parameter	: str - 입력문자
			  limit - 한계개수
*/
function fn_CheckStringLength(str, limit){
	if( str.length >= limit ) event.returnValue=false;
}
/*
작성목적	: 특수문자만 체크하여 입력된다.
*/
function fn_CheckSpecialString(){
	try{
		//소문자 || 대문자 || 숫자
		if ( (window.event.keyCode >= 97 && window.event.keyCode <= 122 ) || (window.event.keyCode >= 65 && window.event.keyCode <= 90 ) || (window.event.keyCode >= 48 && window.event.keyCode <= 57)  )
			event.returnValue=false;
	} catch (exception){}
}
/*
작성목적	: 나모 웹에디터컨트롤에서 생성한 HTML을 인코딩한다
*/
function fn_encodeHtml(html){
	try{
		var encodedHtml;
		encodedHtml = escape(html);
		encodedHtml = encodedHtml.replace(/\//g,"%2F");
		encodedHtml = encodedHtml.replace(/\?/g,"%3F");
		encodedHtml = encodedHtml.replace(/=/g,"%3D");
		encodedHtml = encodedHtml.replace(/&/g,"%26");
		encodedHtml = encodedHtml.replace(/@/g,"%40");
		return encodedHtml;
	} catch(exception){fn_OpenErrorMessage(exception.description);}
}
/*
작성목적	: 나모 웹에디터컨트롤로 HTML 을 로드하기 위해서 디코딩한다
*/		
function fn_decodeHtml(html){
	try{
		var decodeHtml;
		decodeHtml = unescape(html);
		return decodeHtml;
	}catch(exception) {fn_OpenErrorMessage(exception.description);}
}
/*
작성목적	: 메뉴 롤오버
Parameter   : menuIcon, menuText
*/		
var bMenuClick = false;
var oMenuIconSelected = null;
var oMenuTextSelected = null;

function fn_MenuOver(menuIcon, menuText){
	menuIcon.background = menuIcon.bgOnOver;
	menuText.background = menuText.bgOnOver;
}
function fn_MenuOut(menuIcon,menuText){
	if (bMenuClick && oMenuIconSelected == menuIcon) {
		menuIcon.background = menuIcon.bgOnClick;
		menuText.background = menuText.bgOnClick;
	} else {
		menuIcon.background = menuIcon.bgOnOut;
		menuText.background = menuText.bgOnOut;
	}
}
function fn_MenuClick(menuIcon,menuText){
	menuIcon.background = menuIcon.bgOnClick;
	menuText.background = menuText.bgOnClick;
	if (oMenuIconSelected != null) {
		oMenuIconSelected.background = oMenuIconSelected.bgOnOut;
		oMenuTextSelected.background = oMenuTextSelected.bgOnOut;
	}
	oMenuIconSelected = menuIcon;
	oMenuTextSelected = menuText;
	bMenuClick = true;
}
/*
작성목적	: 메뉴가 5개 이상일 경우 스크롤 기능 자동 추가
Parameter   : htr, direction [U(Up)/D(Down)], himg
*/		
var nStartIndex = 1;
var nEndIndex = 5;
var oTimerID = null;;
var bTimerRunning = false;
function fn_StartMenuScroll(htr, direction, himg, htabc){
	fn_StopMenuScroll(himg, htabc);
	oTimerID = setInterval("fn_MenuScroll('" + htr + "', '" + direction + "', document.all." + himg.id + ", " + htabc.id + ")", 500);
	bTimerRunning = true;
}
function fn_StopMenuScroll(himg, htabc){
	if(bTimerRunning) {
		himg.src = himg.srcOnOut;
		htabc.background = htabc.bgOnOut;
		clearInterval(oTimerID);
	}
	bTimerRunning = false;
}
function fn_MenuScroll(htr, direction, himg, htabc){
	var nOldStartIndex = nStartIndex;
	var nOldEndIndex = nEndIndex;
	var oTemp;	
	try {
		himg.src = himg.srcOnOver;
		htabc.background = htabc.bgOnOver;
		switch (direction){
			case "U":
				if(nStartIndex > 1){
					nStartIndex--;
					nEndIndex--;
					oTemp = eval(htr + nStartIndex);
					oTemp.style.display = "";
					oTemp = eval(htr + nOldEndIndex);
					oTemp.style.display = "none";
				}
				break;
			case "D":
				if(eval(htr + (nEndIndex+1))){
					nStartIndex++;
					nEndIndex++;
					oTemp = eval(htr + nEndIndex);
					oTemp.style.display = "";
					oTemp = eval(htr + nOldStartIndex);
					oTemp.style.display = "none";
				}
				break;
		}
	} catch (exception) {}
}
/*
작성목적	: MenuFrame, ContentFrame에 각 페이지를 로딩
Parameter   : menuUrl(MenuFrame URL), contentUrl(ContentFrame URL)
*/		
function fn_SetMenuContent(menuUrl, contentUrl) {
	try {
		parent.MenuFrame.document.location = menuUrl;
		parent.ContentFrame.document.location = contentUrl;
	} catch (exception) {}
}
/*
작성목적	: Xml받아오기
Parameter   : sPage 요청URL
*/		
function fn_GetXmlDomDocument(sPage){
	var oXmlDoc = null;
	var oXMLHttpReq = new ActiveXObject("Microsoft.XMLHTTP");
	oXMLHttpReq.onreadystatechange = fn_handleStateChange;
	oXMLHttpReq.open("POST", sPage, false);
	oXMLHttpReq.send(null);
	oXmlDoc = new ActiveXObject("Microsoft.XMLDOM");
	oXmlDoc.async = false;
	oXmlDoc.loadXML(unescape(oXMLHttpReq.responseText));
	return oXmlDoc;
}

function fn_handleStateChange() { }

/*
작성목적	: SubSystemCode 가져옴 : document.location.href기반 DB아님
Return	    : SubSystemCode
*/
function fn_SubSystemCode()
{
	var strHref = document.location.href;
	var subSystemType;
	var nWebRootLength = fn_GetWebRoot().length ;
	
	if ( strHref.substring(0, 4).toUpperCase() == "HTTP" ) 
	{
		subSystemType = strHref.substring( 7, strHref.length );
	}
	else 
	{
		subSystemType = strHref.substring( 8, strHref.length );
	}
	
	subSystemType = subSystemType.toUpperCase();
	subSystemType = subSystemType.substring( subSystemType.indexOf( fn_GetWebRoot().toUpperCase() ) + nWebRootLength,subSystemType.length );
	subSystemType = subSystemType.substring( 0,subSystemType.indexOf("/") );
	
	return subSystemType;
}

/*
작성목적	: 해당메세지 가져오기
Return	    : 메세지배열컬렉션
*/
function fn_Msg(messageID, subSystemType, parameters){
	try{
		//메세지 받아오기
		var strTemp;
		var arrTemp;
		if(subSystemType == null || subSystemType == "") {arrTemp = fn_MsgS(messageID);}
		else {arrTemp = fn_MsgT(subSystemType,messageID);}
		//메세지 분기
		if(arrTemp != null){
			strTemp = arrTemp[3];
			if(parameters != null){ //파라미터가 없는경우{	
				for(var i = 0 ; i < parameters.length ; i++) {strTemp = strTemp.replace("{" + i + "}", parameters[i]);}
			}
			switch(arrTemp[2]){
				case "01":
					fn_OpenInformation(strTemp);// + "|^|" + arrTemp[4]);
					break;
				case "02":
					return fn_OpenConfirm(strTemp);// + "|^|" + arrTemp[4]);
					break;
				case "03":
					fn_OpenErrorMessage(strTemp + "|^|" + arrTemp[4]);
					break;
			}
		}
	} catch(exception) {fn_OpenErrorMessage(exception.description);}
}
/*
작성목적	: 해당메세지 가져오기
Return	    : 메세지배열컬렉션
*/
function fn_MsgS( messageID )
{
	try
	{
/*
		var strHref = document.location.href;
		var subSystemType;
		var nWebRootLength = fn_GetWebRoot().length ;
		
		if ( strHref.substring(0, 4).toUpperCase() == "HTTP" ) {subSystemType = strHref.substring(7, strHref.length);}
		else {subSystemType = strHref.substring(8, strHref.length);}
		subSystemType = subSystemType.toUpperCase();
		subSystemType = subSystemType.substring(subSystemType.indexOf(fn_GetWebRoot().toUpperCase())+nWebRootLength,subSystemType.length);
		subSystemType = subSystemType.substring(0,subSystemType.indexOf("/"));
*/	
		var subSystemType = fn_SubSystemCode();
		
		return fn_MsgT(subSystemType,messageID);
	} 
	catch( exception ) 
	{
		fn_OpenErrorMessage(exception.description);
	}
}
/*
작성목적	: 해당메세지 가져오기
Return	    : 메세지배열컬렉션
*/
function fn_MsgT(subSystemType,messageID){
	try{
		var strTemp = fn_GetWebRoot() + "eManage/Message/Msg.aspx?SubSystemType=" + subSystemType + "&MessageID=" + messageID;
		var oXmlDoc = new ActiveXObject("Microsoft.XMLDOM");
			oXmlDoc = fn_GetXmlDomDocument(strTemp);
		var oElemList = oXmlDoc.getElementsByTagName("ROOT")		
		var arrTemp = new Array(5)
			arrTemp[0]		= oElemList.item(0).childNodes(0).text;//SubSystemType
			arrTemp[1]		= oElemList.item(0).childNodes(1).text;//MessageID
			arrTemp[2]		= oElemList.item(0).childNodes(2).text;//MessageType
			arrTemp[3]		= oElemList.item(0).childNodes(3).text;//DisplayMessage
			arrTemp[4]		= oElemList.item(0).childNodes(4).text;//SummaryMessage
		return arrTemp;
	} catch(exception){fn_OpenErrorMessage(exception.description);}
}
/*
작성목적	: 해당Dictionary 가져오기
Return	    : Dictionary 컬렉션
*/
function fn_Dic(dictionaryList){
	try{
		var strImsi = replaceAll(dictionaryList,",","^");
		var strTemp = fn_GetWebRoot() + "eManage/Message/Dic.aspx?Dic=" + strImsi;
		var oXmlDoc = new ActiveXObject("Microsoft.XMLDOM");
			oXmlDoc = fn_GetXmlDomDocument(strTemp);
		var oElemList = oXmlDoc.getElementsByTagName("ROOT")		
		var strJamsi = oElemList.item(0).childNodes(0).text;//Dictionary
		var arrTemp;
		strJamsi = strJamsi.substring(0,strJamsi.length - 1);
		arrTemp = strJamsi.split('^');
		return arrTemp;
	} catch(exception){fn_OpenErrorMessage(exception.description);}
}
/*
작성목적	: 사용자 정보보기(새창)
*/
function replaceAll(oldStr,findStr,repStr) {
	try{
		var srchNdx = 0;  // srchNdx will keep track of where in the whole line of oldStr are we searching.
		var newStr = "";  // newStr will hold the altered version of oldStr.
		while (oldStr.indexOf(findStr,srchNdx) != -1) {// As long as there are strings to replace, this loop will run. 
			newStr += oldStr.substring(srchNdx,oldStr.indexOf(findStr,srchNdx)); // Put it all the unaltered text from one findStr to the next findStr into newStr.
			newStr += repStr; // Instead of putting the old string, put in the new string instead. 
			srchNdx = (oldStr.indexOf(findStr,srchNdx) + findStr.length); // Now jump to the next chunk of text till the next findStr.           
		}
		newStr += oldStr.substring(srchNdx,oldStr.length); // Put whatever's left into newStr.             
		return newStr;
	} catch(exception) {fn_OpenErrorMessage(exception.description);}
}
/*
작성목적	: 겸직자의 부서선택창을 Open한다.			  
*/
function fn_openSelectDept(posX, posY){
	var nPosX = 0;
	var nPosY = 0;
	var nWidth = 330;
	var nHeight = 230;
	var strURL = "";
	try{
		if(posX == null || (posX != "L" && posX != "R")) {posX = "L";}
		if(posY == null || (posY != "T" && posY != "B")) {posY = "T";}
		if(posX == "L") {nPosX = window.event.screenX;}
		else {nPosX = window.event.screenX - nWidth;}
		if(posY == "T") {nPosY = window.event.screenY;}
		else {nPosY = window.event.screenY - nHeight;}
		var strURL = fn_GetWebRoot() + "eManage/MenuPopup/DeptSelect.aspx";
		fn_OpenModalDialog(strURL, null, "dialogLeft:" + nPosX + ";dialogTop:" + nPosY + ";dialogHeight:"+nHeight+"px;dialogWidth:"+nWidth+"px;status:no;resizable:no;help:no;scroll=no");
	} catch(exception) {fn_OpenErrorMessage(exception.description);}
}
/*
작성목적	: 부서, 사용자 동시 선택 조직도 open
*/
function fn_OpenDeptUserOrgMap(strUserCount, strDeptCount, strSearchOption){
	var nWidth = 975;
	var nHeight = 680;
	var strURL = "";
	var strReturn = "";
	try{
		if(strUserCount == null) {strUserCount = "";}
		if(strDeptCount == null) {strDeptCount = "";}
		if(strSearchOption == null) {strSearchOption = "ALL";}
		strURL = fn_GetWebRoot() + "eManage/OrgMapNew/Dept_UserOrgMap.aspx?SelUserCon=" + strUserCount + "&SelDeptCon=" + strDeptCount + "&SOpt=" + strSearchOption;
		strReturn = fn_OpenModalDialog(strURL, null, "dialogHeight:"+nHeight+"px;dialogWidth:"+nWidth+"px;status:no;resizable:no;help:no;scroll=no");
	} catch (exception) {fn_OpenErrorMessage(exception.description);}
	return strReturn;
}
/*
작성목적	: 사용자 정보보기(새창)
*/
function fn_showUserInfo(strUserID, strEditYn){
	if (strUserID == "") {fn_Msg("0004", "00");}
	else{
		var nWidth = 650;
		var nHeight = 402;
		var winName = "PropertyUserInfo";
		var url = fn_GetWebRoot() + "eManage/OrgMapNew/OrgMapUserInfo.aspx?UserID="+ strUserID;
		var winl = (screen.availWidth - nWidth) / 2;
		var wint = (screen.availHeight - nHeight) / 2;
		var settings  ='height=' + nHeight + ',width=' + nWidth + ',top=' + wint + ',left=' + winl + ',scrollbars=no,resizable=no';
		try{
			if(strEditYn == null) {strEditYn = "";}
			url = url + "&EditYN=" + strEditYn;
			win=window.open(url,winName,settings);
			if(parseInt(navigator.appVersion) >= 4){win.window.focus();}
			return true;
		} catch (exception) {fn_OpenErrorMessage(exception.description);}
	}
}
/*
작성목적	: 사용자 정보보기(모달창)
*/
function fn_showUserInfoModal(strUserID, strEditYn) {
	if (strUserID == ""){fn_Msg("0004", "00");}
	else{
		var nWidth = 650;
		var nHeight = 427;
		var url = fn_GetWebRoot() + "eManage/OrgMapNew/OrgMapUserInfo.aspx?UserID="+ strUserID;
		var rgParams = new Array();
		try{
			rgParams["Opener"] = window;
			if(strEditYn == null){strEditYn = "";}
			url = url + "&EditYN=" + strEditYn;
			fn_OpenModalDialog(url, rgParams, "dialogHeight:"+nHeight+"px;dialogWidth:"+nWidth+"px;status:no;resizable:no;help:no;scroll=no");
		} catch (exception) {fn_OpenErrorMessage(exception.description);}
	}
}
/*
작성목적	: 조직도를 모달창으로 오픈
*/
function fn_openOrgMap(strPharam, strCheckType, strUserCount, strSearchOption){
	var nWidth = 820;
	var nHeight = 562;
	var strURL = "";
	var strReturn = "";
	var rgParams = new Array();
	try{
		rgParams["Opener"] = window;
		if(strUserCount == null) {strUserCount = "";}
		if(strSearchOption == null) {strSearchOption = "ALL";}
		if(strPharam != null && strPharam.toUpperCase() == "VIEW"){
			nWidth = 870;
			nHeight = 608;
			strURL = fn_GetWebRoot() + "eManage/OrgMapNew/UserOrgMap_Read.aspx?SOpt=" + strSearchOption;
		}
		else {strURL = fn_GetWebRoot() + "eManage/OrgMapNew/UserOrgMap.aspx?SelUserCon=" + strUserCount + "&SOpt=" + strSearchOption;}
		strReturn = fn_OpenModalDialog(strURL, rgParams, "dialogHeight:"+nHeight+"px;dialogWidth:"+nWidth+"px;status:no;resizable:no;help:no;scroll=no");
	} catch (exception) {fn_OpenErrorMessage(exception.description);}
	return strReturn;
}
/*
작성목적	: 메일 조직도를 오픈한다.
*/
function fn_openMailOrgMap(strUserCount){
	var nWidth = 870;
	var nHeight = 672;
	var strURL = "";
	var strReturn = "";
	try{
		if(strUserCount == null) {strUserCount = "";}
		strURL = fn_GetWebRoot() + "eManage/OrgMapNew/MailOrgMap.aspx?SelUserCon=" + strUserCount;
		strReturn = fn_OpenModalDialog(strURL, null, "dialogHeight:"+nHeight+"px;dialogWidth:"+nWidth+"px;status:no;resizable:no;help:no;scroll=no");
	} catch (exception) {fn_OpenErrorMessage(exception.description);}
	return strReturn;
}
/*
작성목적	: 메일 조직도를 오픈한다.
*/
function fn_openMailOrgMapNew(strUserCount, strSearchOption){
	var nWidth = 870;
	var nHeight = 633;
	var strURL = "";
	var strReturn = "";
	try{
		if(strUserCount == null) {strUserCount = "";}
		if(strSearchOption == null) {strSearchOption = "ALL";}
		strURL = fn_GetWebRoot() + "eManage/OrgMapNew/newMailOrgMap.aspx?SelUserCon=" + strUserCount + "&SOpt=" + strSearchOption;
		strReturn = fn_OpenModalDialog(strURL, null, "dialogHeight:"+nHeight+"px;dialogWidth:"+nWidth+"px;status:no;resizable:no;help:no;scroll=no");
	} catch (exception) {fn_OpenErrorMessage(exception.description);}
	return strReturn;
}
/*
작성목적	: 메일 조직도를 오픈한다.
*/
function fn_openMailOrgMapNew(strUserCount, strSearchOption){
	var nWidth = 870;
	var nHeight = 633;
	var strURL = "";
	var strReturn = "";
	try{
		if(strUserCount == null) {strUserCount = "";}
		if(strSearchOption == null) {strSearchOption = "ALL";}
		strURL = fn_GetWebRoot() + "eManage/OrgMapNew/newMailOrgMap.aspx?SelUserCon=" + strUserCount + "&SOpt=" + strSearchOption;
		strReturn = fn_OpenModalDialog(strURL, null, "dialogHeight:"+nHeight+"px;dialogWidth:"+nWidth+"px;status:no;resizable:no;help:no;scroll=no");
	} catch (exception) {fn_OpenErrorMessage(exception.description);}
	return strReturn;
}
/*
작성목적	: 부서 조직도를 모달창으로 오픈
*/
function fn_openOrgMapDeptLst(strType, strDeptCount){
	var nWidth = 820;
	var nHeight = 562;
	var strURL = "";
	var strReturn = "";
	try{
		if(strDeptCount == null) {strDeptCount = "";}
		strURL = fn_GetWebRoot() + "eManage/OrgMapNew/DeptOrgMap.aspx?SelDeptCon=" + strDeptCount;
		strReturn = fn_OpenModalDialog(strURL, null, "dialogHeight:"+nHeight+"px;dialogWidth:"+nWidth+"px;status:no;resizable:no;help:no;scroll=no");
	} catch (exception) {fn_OpenErrorMessage(exception.description);}
	return strReturn;
}
/*
작성목적	: 조회용 조직도를 모달창으로 오픈
*/
function fn_openOrgMapViewModal(){
	var nWidth = 870;
	var nHeight = 608;
	var strURL = "";
	var rgParams = new Array();
	try{
		rgParams["Opener"] = window;
		strURL = fn_GetWebRoot() + "eManage/OrgMapNew/UserOrgMap_Read.aspx";
		fn_OpenModalDialog(strURL, rgParams, "dialogHeight:"+nHeight+"px;dialogWidth:"+nWidth+"px;status:no;resizable:no;help:no;scroll=no");
	} catch (exception) {fn_OpenErrorMessage(exception.description);}
}
function fn_openOrgMapViewModalless(strSearchOption){
	var nWidth = 870;
	var nHeight = 583;
	var winName = "PropertyUserOrgMapRead";
	var url = "";
	var winl = 0;
	var wint = 0;
	var settings = "";
	try{
		if(strSearchOption == null) {strSearchOption = "ALL";}
		url = fn_GetWebRoot() + "eManage/OrgMapNew/UserOrgMap_Read.aspx?strType=2&SOpt=" + strSearchOption;
		winl = (screen.availWidth - nWidth) / 2;
		wint = (screen.availHeight - nHeight) / 2;
		settings  ='height=' + nHeight + ',width=' + nWidth + ',top=' + wint + ',left=' + winl + ',scrollbars=no,resizable=no';
		win=window.open(url,winName,settings);
		if(parseInt(navigator.appVersion) >= 4){win.window.focus();}
		return true;
	} catch (exception) {fn_OpenErrorMessage(exception.description);}
}
function fn_openOrgMapViewModalless_Main(strSearchOption,strCloumn,strValue){
	var nWidth = 870;
	var nHeight = 583;
	var winName = "PropertyUserOrgMapRead";
	var url = "";
	var winl = 0;
	var wint = 0;
	var settings = "";
	try{
		if(strSearchOption == null) {strSearchOption = "ALL";}
		url = fn_GetWebRoot() + "eManage/OrgMapNew/UserOrgMap_Read.aspx?strType=1&SOpt=" + strSearchOption + "&strValue=" + escape(strValue) + "&strCloumn=" + escape(strCloumn);
		winl = (screen.availWidth - nWidth) / 2;
		wint = (screen.availHeight - nHeight) / 2;
		settings  ='height=' + nHeight + ',width=' + nWidth + ',top=' + wint + ',left=' + winl + ',scrollbars=no,resizable=no';
		win=window.open(url,winName,settings);
		if(parseInt(navigator.appVersion) >= 4){win.window.focus();}
		return true;
	} catch (exception) {fn_OpenErrorMessage(exception.description);}
}
/*
작성목적	: 부서 조직도를 모달창으로 오픈
*/
function fn_openOrgMapDept(strType, strDeptCount){
	var nWidth = 820;
	var nHeight = 562;
	var strURL = "";
	var strReturn = "";
	try{
		if(strDeptCount == null) {strDeptCount = "";}
		strURL = fn_GetWebRoot() + "eManage/OrgMapNew/DeptOrgMap.aspx?SelDeptCon=" + strDeptCount;
		strReturn = fn_OpenModalDialog(strURL, null, "dialogHeight:"+nHeight+"px;dialogWidth:"+nWidth+"px;status:no;resizable:no;help:no;scroll=no");
	} catch (exception){fn_OpenErrorMessage(exception.description);}
	return strReturn;
}
/*
작성목적	: 관리자 Help 창을 띄운다.
Parameter :jumpUrl - main frame에 나오는 도움말 링크
*/
function fn_openAdminHelp(jumpUrl){
	var nWidth = 950;
	var nHeight = 500;
	var w = 950; 
	var h = 500;
	var winName = "_Help";
	if (jumpUrl != "") {var url = "http://ekpbld.lottechem.com" + jumpUrl;}
	else {var url = fn_GetWebRoot() + "common/help/admin/";}
	var winl = (screen.width-w)/2;
	var wint = (screen.height-h)/2;
	var settings = 'height=' + nHeight + ',width=' + nWidth + ',top=' + wint + ',left=' + winl + ',scrollbars=no,resizable=no';
	try{
		win = window.open(url,winName,settings);
		if(parseInt(navigator.appVersion) >= 4){win.window.focus();}
		return true;
	} catch (exception) {fn_OpenErrorMessage(exception.description);}	
}
/*
작성목적	: 사용자 Help 창을 띄운다.
Parameter :jumpUrl - main frame에 나오는 도움말 링크
*/
function fn_openHelp(jumpUrl){
	var nWidth = 950;
	var nHeight = 500;
	var w = 950; 
	var h = 500;
	var winName = "_Help";
	if (jumpUrl != "") {var url = "http://ekpbld.lottechem.com" + fn_GetWebRoot() + jumpUrl;}
	else {var url = fn_GetWebRoot() + "common/help/user/";}
	var winl = (screen.width-w)/2;
	var wint = (screen.height-h)/2;
	var settings = 'height=' + nHeight + ',width=' + nWidth + ',top='+wint + ',left=' + winl + ',scrollbars=no,resizable=no';
	try{
		win = window.open(url,winName,settings);
		if(parseInt(navigator.appVersion) >= 4){win.window.focus();}
		return true;
	} catch (exception) {fn_OpenErrorMessage(exception.description);}	
}
/*
작성목적	: 폴더권한정보를 보거나 셋팅할때 쓰인다.
Return		: bool
*/
function fn_openFolderAuthSetting(rgParams, folderID, userID, isEditMode) {
	var nWidth = 320;
	var nHeight = 360;
	if (isEditMode == true) {var sUrl = fn_GetWebRoot() + "eManage/Auth/FolderAuthSetting.aspx?id=" + folderID+ "&edit=true";}
	else {var sUrl = fn_GetWebRoot() + "eManage/Auth/FolderAuthSetting.aspx?id=" + folderID+ "&edit=false";}
	var strReturn = "";
	var sFeature = "dialogHeight:"+nHeight+"px;dialogWidth:"+nWidth+"px;status:no;resizable:no;help:no;scroll=no";
	try{
		strReturn = fn_OpenModalDialog(sUrl, rgParams, sFeature);
	} catch(exception) {fn_OpenErrorMessage(exception.description);}
	return strReturn;
}
/*
작성목적	: 개체권한정보를 보거나 셋팅할때 쓰인다.
Return		: bool
*/
function fn_openObjectAuthSetting(rgParams, objectGuid, userID, isEditMode) {
	var nWidth = 320;
	var nHeight = 340;
	if (isEditMode == true) {var sUrl = fn_GetWebRoot() + "eManage/Auth/ObjectAuthSetting.aspx?id=" + objectGuid + "&edit=true";}
	else {var sUrl = fn_GetWebRoot() + "eManage/Auth/ObjectAuthSetting.aspx?id=" + objectGuid + "&edit=false";}
	var strReturn = "";
	var sFeature = "dialogHeight:"+nHeight+"px;dialogWidth:"+nWidth+"px;status:no;resizable:no;help:no;scroll=no";
	try{
		strReturn = fn_OpenModalDialog(sUrl, rgParams, sFeature);		
	} catch (exception) {fn_OpenErrorMessage(exception.description);}
	return strReturn;
}
/*
작성목적	: 개체권한정보를 보거나 셋팅할때 쓰인다.(문서관리용)
*/
function fn_openObjectAuthSettingForEdms(rgParams, objectGuid, userID, isEditMode) {
	var nWidth = 320;
	var nHeight = 340;
	if (isEditMode == true) {var sUrl = fn_GetWebRoot() + "eManage/Auth/ObjectAuthSettingForEdms.aspx?id=" + objectGuid + "&edit=true";}
	else {var sUrl = fn_GetWebRoot() + "eManage/Auth/ObjectAuthSettingForEdms.aspx?id=" + objectGuid + "&edit=false";}
	var strReturn = "";
	var sFeature = "dialogHeight:"+nHeight+"px;dialogWidth:"+nWidth+"px;status:no;resizable:no;help:no;scroll=no";
	try{
		strReturn = fn_OpenModalDialog(sUrl, rgParams, sFeature);		
	} catch (exception) {fn_OpenErrorMessage(exception.description);}
	return strReturn;
}
/*
작성목적	: 그룹이나 사용자를 검색한다.
Return : bool
*/
function fn_openSearchUserNGroup(rgParams, isUser, isGroup) {
	var nWidth = 300;
	var nHeight = 260;
	var strUser, strGroup;
	strUser = isUser ? "true" : "false";
	strGroup = isGroup ? "true" : "false";
	var sUrl = fn_GetWebRoot() + "eManage/Auth/SearchMembers.aspx?user=" + strUser + "&group=" + strGroup;
	var vaReturn;
	var strResult = "";
	var sFeature = "dialogHeight:"+nHeight+"px;dialogWidth:"+nWidth+"px;status:no;resizable:no;help:no;scroll=no";
	try{
		vaReturn = fn_OpenModalDialog(sUrl, rgParams, sFeature);
		strResult = rgParams["result"];
	}catch (exception) {fn_OpenErrorMessage(exception.description);}
	return strResult;
}
/*
작성목적	: 주소 가지고 오기
Return :주소SEQ와 주소 스트링
*/
function fn_GetAddress(nTemp){
	try{
		var url = fn_GetWebRoot() + "Common/ZipCode.aspx";
		var strTemp = fn_OpenModalDialog(url, "","dialogWidth:600px;dialogHeight:265px;status=no;scroll=no")
		if(strTemp != null){
			var arrTemp = strTemp.split('^');
			if(nTemp == null) {return arrTemp[0] + ' ' + arrTemp[1];}
			else {return arrTemp;}
		}
	} catch (exception) {fn_OpenErrorMessage(exception.description);}
}
/*
내용 : String 의 공백을 모두 제거한다.
*/	
function fn_Trim(sourceString){
	var strResult ;
	strResult = sourceString.replace(/\s/g,"");
	return strResult;
}
/*
내용 : String 의 양쪽공백을 모두 제거한다.
*/
function fn_RLTrim(strSource){
	return strSource.replace(/(^\s*)|(\s*$)/g, "");
}
function fn_SetProgressbar(visible, posTop, posLeft, message){
	var oBody;
	var strTemp;
	var oTemp;
	var strWebRoot = fn_GetWebRoot();
	try {
		oTemp = document.all["divProgress"];
		if(oTemp != null){
			if(visible){
				oTemp.style.display = "block";
				oTemp.all["progressMessage"].innerHTML = message;
				fn_MoveControl(oTemp,posTop, posLeft);
			}
			else{oTemp.style.display = "none";}
		}
	}catch(exception) {fn_OpenErrorMessage(exception.description);}
}
function fn_SetSmallProgressbar(visible, posTop, posLeft, message){
	var oBody;
	var strTemp;
	var oTemp;
	var strWebRoot = fn_GetWebRoot();
	try{
		oTemp = document.all["divSmallProgress"];
		if(oTemp != null){
			if(visible){
				oTemp.style.display = "block";
				oTemp.all["progressMessage"].innerHTML = message;
				fn_MoveControl(oTemp,posTop, posLeft);
			}
			else {oTemp.style.display = "none";}
		}
	}catch(exception) {fn_OpenErrorMessage(exception.description);}
}

// 스크립트 동적 추가
function fn_ScriptInclude(path, type) {
    var oScript;
    var strType = "text/javascript";        
        
    try{		
		oScript = document.createElement('script'); 
		oScript.setAttribute('type', (type == null || type == "") ?  strType : type); 
		oScript.setAttribute('src', path); 
		document.getElementsByTagName("head")[0].appendChild(oScript);		
    }catch(exception) {fn_OpenErrorMessage(exception.description);}
}

// GUID값 추출하기
function fn_GetGUID() {
    try{		
        var result, i, j;
		result = '';
		for(j=0; j<32; j++) {
			if( j == 8 || j == 12|| j == 16|| j == 20)
				result = result + '-';
				
			i = Math.floor(Math.random()*16).toString(16).toUpperCase();
			result = result + i;
		}
		return result
    }catch(exception) {fn_OpenErrorMessage(exception.description);}
}

// Parametor로 전달할 Dictionary 클래스를 관리한다.
function DictionaryClass() {
    var m_dictionary = new ActiveXObject("Scripting.Dictionary"); 
	
	var m_arrDicKeyArr;
	var m_arrDicArr;
	var m_nDicCnt;
	
    this.Append = Append;
    this.GetString = GetString;
    this.GetXml = GetXml;
    
	// Dic에 Key, Value를 추가한다.    
    function Append(key, val, valType) {
		try{
			var strKey=key;
			var strVal=val;
			var strValType=valType;
			
			//key 값이 없는 경우 리턴
			if(strKey == null) {
				fn_OpenErrorMessage("Dictionary Key값이 없습니다.");
				return;
			}
			// null인 경우 공백을 삽입
			if(strVal == null) { strVal=""; }
			
			// value 타입이 있는 경우
			if(strValType != null) {
				switch(strValType.toUpperCase())
				{
					case "CDATA":
						strVal= "<![CDATA[" + strVal + "]]>";
						break;
				}
			}
			
			// 이미 key 값이 있는 경우 값을 Update 한다.
			if(m_dictionary.Exists(strKey)) {
				m_dictionary.Item(strKey);// = strVal;
			} else {
				m_dictionary.add(strKey, strVal);
			}
		}catch(exception) {fn_OpenErrorMessage(exception.description);}
    }
    
     // 현재 Dic의 내용을 배열로 저장한다.
    function SetDicToArray(oDic) {
		try{
			m_arrDicKeyArr = new VBArray(m_dictionary.Keys()).toArray();
			m_arrDicArr =  new VBArray(m_dictionary.Items()).toArray();
			m_nDicCnt = m_dictionary.Count;			
		}catch(exception) {fn_OpenErrorMessage(exception.description);}
    }  

    // Dic을 문자열로 반환한다.
    function GetString() {
		var params=""
		try{
			// 배열로 저장
			SetDicToArray();
			for (var i=0; i<m_nDicCnt; i++){
				params += m_arrDicKeyArr[i] + "=" + m_arrDicArr[i] + "&";
			}
			Init();
			return params.substring(0, params.length -1);
		}catch(exception) {fn_OpenErrorMessage(exception.description);}
    }
    
    // Dic을 XMl로 반환한다.
    function GetXml() {
		var params=""
		try{
			// 배열로 저장
			SetDicToArray();
			for (var i=0; i<m_nDicCnt; i++){
				params += "<" + m_arrDicKeyArr[i] + ">" + m_arrDicArr[i] + "</" + m_arrDicKeyArr[i] + ">";
			}
			Init();
			return "<ROOT>" + params + "</ROOT>";
		}catch(exception) {fn_OpenErrorMessage(exception.description);}
    }  
    
    // Dic을 초기화 한다.
    function Init() {
		try{
			if(m_dictionary.Count > 0) m_dictionary.RemoveAll();
			m_arrDicKeyArr = null;
			m_arrDicArr = null;
			m_nDicCnt = null;
		}catch(exception) {fn_OpenErrorMessage(exception.description);}
    }  
}