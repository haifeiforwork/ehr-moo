// var $jq = jQuery.noConflict(); commoon.pack.js include

// iKEP Form Config Start ----------------------------------------------------------------------	
var iFC = { 
            mode : "",
            constant : {
                         "thColspan"    : 4,
                         "apprType"     : ["결재", "합의(필수)", "합의(선택)", "수신"],
                         "apprTypeShort": ["(필)", "(선)"],
                         "apprStatus"   : ["미결", "진행", "승인", "반려", "합의"]
                       },
                       
            object : {
                        // embed name
                        "embedItem" : null,
                        
                        // formLayoutData
                        "formLayoutData" : {},
                        
                        // formData
                        "formData" : {},
                        
                        // multiRow
                        "multiRow" : [],
                        
                        // code
                        "codeList" : [],
                        
                        // rules
                        "rules"    : "",
                        
                        // message
                        "messages" : "",
                        
                        // process
                        "process" : false
                     },
                     
            "user" : {"id"          : "",
                      "locale"      : "",
                      "registerId"  : ""   // 기안자 아이디
                     },
    
    
            // row에 추가할수 있는 item개수
            "itemCount" : {   
                            "one"   : {"value" : 1, "th" : 4, "td" : 36, "tl" : 36, "hd" : 36, "hl" : 36, "mt" : "90%"},
                            "two"   : {"value" : 2, "th" : 4, "td" : 16, "tl" : 16, "hd" : 18, "hl" : 18, "mt" : "45%"},
                            "three" : {"value" : 3, "th" : 4, "td" : 9,  "tl" : 10, "hd" : 12, "hl" : 12, "mt" : "30%"},
                            "four"  : {"value" : 4, "th" : 4, "td" : 6,  "tl" : 6,  "hd" : 9,  "hl" : 9,  "mt" : "22.5%"},
                            "five"  : {"value" : 5, "th" : 4, "td" : 4,  "tl" : 4,  "hd" : 7,  "hl" : 8,  "mt" : "18%"},
                            "six"   : {"value" : 6, "th" : 4, "td" : 3,  "tl" : 3,  "hd" : 6,  "hl" : 6,  "mt" : "15%"},
                            "line"  : {"value" : 1}
                          },            
                          
            
            // textTab kind
            "textKind" : [
                           {"value" : "all",     	"text" : "전체"},
                           {"value" : "digits", 	"text" : "숫자"},
                           {"value" : "number",  	"text" : "숫자(소수점포함)"},
                           {"value" : "numberComma","text" : "금액"},
                           {"value" : "date",    	"text" : "날짜"},
                           {"value" : "email",   	"text" : "이메일"},
                           {"value" : "phone",   	"text" : "전호번호"},
                           {"value" : "zipcode", 	"text" : "우편번호"}
                                                            
                         ],
            
            // bind data             
            "bindData" : {   
                            "userId"   :     {"value" : "", "text" : "사용자 아이디"},
                            "userName" :     {"value" : "", "text" : "사용자 명"},
                            "jobTitleName" : {"value" : "", "text" : "직책명"},
                            "teamName" :     {"value" : "", "text" : "팀명"}
                         },
                         
            // template
            "template" : {
                          "apprLineTh"      : "<th width=\"100\" height=\"25\" align=\"center\">${job}</th>",
                          "apprLineTh1"     : "<th width=\"100\" height=\"25\" align=\"center\"><a href=\"#a\" onclick=\"apprDocSubAllStatus('${apprId}');\">${job}</a></th>",
                          "apprLineTdNew"   : "<td height=\"60\" align=\"center\" valign=\"middle\">${name}</td>",
                          "apprLineTd"      : "<td height=\"60\" align=\"center\">${name}<br /><span class=\"status${classType}\">${status}</span><br />${date}</td>",
                          "apprLineTdSign"  : "<td height=\"60\" align=\"center\"><img class=\"${className}\" src=\""+iKEP.getWebAppPath()+"\/support/fileupload/downloadFile.do?fileId=${signFileId}\" title=\"${name}/${status}/${date}\"></td>",
                          "apprRefLiView"   : "<li><a href=\"#a\" onclick=\"iFU.viewApprDocPopUp('${apprId}', 'viewRelationDoc')\">${apprTitle}</a>",
                          "apprRefLiNew"    : "<li class=\"${apprId}\">${apprTitle} <img src=\"/ikep4-webapp/base/images/icon/ic_btn_delete.gif\" class=\"dateicon\" onclick=\"$jq(this).parent().remove();\"></li>"
                         },             
                        
            // template Height
            "templateHeight" : {
                          "apprLineTd"     : "<tr><td align=\"center\">${no}</td><td>${name}</td><td>${team}</td><td align=\"center\">${apprType}</td><td align=\"center\"><span class=\"status${classType}\">${apprStatus}</span></td><td align=\"center\">${date}</td></tr>",
                          "apprLineTd1"    : "<tr><td align=\"center\">${no}</td><td>${name}</td><td><a href=\"#a\" onclick=\"apprDocSubAllStatus('${apprId}');\">${team}</a></td><td align=\"center\">${apprType}</td><td align=\"center\"><span class=\"status${classType}\">${apprStatus}</span></td><td align=\"center\">${date}</td></tr>",
                          "apprLineTdSign" : "<td height=\"60\" align=\"center\"><img class=\"${className}\" src=\""+iKEP.getWebAppPath()+"\/support/fileupload/downloadFile.do?fileId=${signFileId}\" title=\"${name}/${status}/${date}\"></td>",
                         },               
            // 스타일 정의
            "style" : {
                      },
             
             // html         
             "html"  : {
                         "required"     : "<span class=\"colorPoint\">*</span>&nbsp;",
                         "dateImg"      : "<img class=\"dateicon\" src=\"/ikep4-webapp/base/images/icon/ic_cal.gif\" alt=\"Calendar\" style=\"cursor:pointer;\"/>&nbsp;",
                         "searchImg"    : "<img src=\"/ikep4-webapp/base/images/icon/ic_btn_search.gif\" alt=\"search\"  style=\"cursor:pointer;\"/>",
                         "addressImg"   : "<img src=\"/ikep4-webapp/base/images/icon/ic_btn_address.gif\" alt=\"address\"/>",
                         "deleteImg"    : "<img src=\"/ikep4-webapp/base/images/icon/ic_btn_delete.gif\" alt=\"delete\"/>",
                         "lineTitleImg" : "<img src=\"/ikep4-webapp/base/images/icon/ic_blt_01.gif\" alt=\"line\"/>",
                         "layoutTfoot"  : "<td class=\"border_none\" width=\"2.5%\" />"
                       },
            // message
            "message" : {
                            "required"          : "필수 항목입니다",
                            "maxlength"         : "자 까지 입력할 수 있습니다",
                            "minSize"           : "선택해주세요",
                            "notChecked"        : "선택하지 않음",
                            "checked"           : "선택함",
                            "digits"            : "숫자만 입력해주세요",
                            "number"            : "숫자(소수점포함)만 입력해주세요",
                            "numberComma"       : "숫자(콤마포함)만 입력해주세요",
                            "date"              : "날짜 형식이 올바르지 않습니다 (ex : 2012.02.14)",
                            "email"             : "이메일 형식이 올바르지 않습니다",
                            "phone"             : "전화번호 형식이 올바르지 않습니다 (ex : 000-000-0000)",
                            "zipcode"           : "우편번호 형식이 올바르지 않습니다 (ex : 000-000)",
                            "couldntMultiRow"   : "마지막 열은 삭제 할수 없습니다",
                            "notFoundResult"    : "검색된 결과가 없습니다",
                            "approvalPopUp"     : "결재요청",
                            "reasonPopUp"       : "변경사유",
                            "docDeatilPopUp"    : "문서 상세",
                            "total"             : "총",
                            "remove"            : "삭제",
                            "add"               : "추가"
                        }
};

// iKEP Form Config End ------------------------------------------------------------------------


/* 생성자 */
var iFU = new(function() {
})();    

// iKEP Form Common Util Start -----------------------------------------------------------------
/* 기본사용자 정보 셋팅 */
iFU.setUserInfo = function(userId, localeCode, registerId){
    if(typeof(registerId)=="undefined" || registerId == "") registerId = userId; //기안자 아이디
    iFC.user.id             = userId;
    iFC.user.locale         = localeCode;
    iFC.user.registerId     = registerId;
};

/* target을 기준으로 result 값을 구함*/
iFU.findJSonValueByKey = function(obj, target, val, result) {
    for(key in obj) {
        if(obj[key][target]==val) return obj[key][result];
    }
    return "";
};

/* select option의 size를 target에 표시함 */
iFU.setSelectOptionCount = function(name, target){
    $jq(target).html($jq("select[name=" + name + "] option").size());
};

/* 기선텍된 값인지 체크 */
iFU.hasSelectOptionValue = function(name, value) {
    var b = false;
    $jq("select[name=" + name + "] option").each(function() {
        if(value==$jq(this).val()){
            b = true;
            return false;
        }
    });  
    return b;  
};

/* json 정보에 key항목에 value값 존재여부 */
iFU.jsonHasKey = function(json, key, value){
    var b = false;
    $jq.each(json, function(){
        if(this[key]==value){
            b = true;
            return false;
        }
    });
    return b;
};

/* WCEditor 문자 복원 */
iFU.revokeWCData = function(str){
    return ((str.replaceAll("_quot_", "\"")).replaceAll("_lt_", "<")).replaceAll("_gt_", ">").replaceAll("_amp_", "&");
};

/* 기안문 출력 */
iFU.print = function(html){
    var url = iKEP.getContextRoot() + "/approval/work/apprWorkDoc/viewApprDocPrint.do";
	iKEP.popupOpen(url, {width:900, height:600, argument:{"printHTML":html}}, "printPopup");
};

/* 콤마 추가 */
iFU.addComma = function(t){
	$jq(t).val($jq(t).val().clearComma());
	var s = $jq(t).val().addComma();
	if(s!="NaN") $jq(t).val(s);
};

/* text, textbox 넓이 반환 */
iFU.getWidthByUnit = function(json, num){
    var unit = ""
    if(json.unit && json.unit=="1"){
        // td에 padding값이 있기 때문에 컬럼 갯수별로 %를 자동 계산
        var _width = parseInt(json.width);
        if(_width>10) {
            _width -= (iFC.itemCount[num].value+1);
        }
        
        unit = _width + "% !important;"
    }else{
        unit = json.width + "px;"
    }
    return unit;
}
// iKEP Form Common Util End -------------------------------------------------------------------



// iKEP Form Layout Process Util Start ----------------------------------------------------------
/* 기안서 작성 */
iFU.initializeApprDocForm = function(form, mode) {
    iFC.mode = mode;
    
    $jq("#apprLine0Tr").hide();
    $jq("#apprLine1Tr").hide();
    
    if($jq(form).find("input[name=defLineUse]").val()=="1" && $jq(form).find("input[name=isDefLineUpdate]").val()=="0"){
        $jq("#addApprLineButton").hide();
        $jq("#addApprReceiveLineButton").hide();
    }
    
    //- 협조공문시에만 수신처를 보여줌
    if($jq(form).find("input[name=apprDocType]").val()=="0"){
        $jq("#apprReceiveLineTr").hide();
    };
    
    // 참조자 주소록 찾기 버튼
    $jq("#referenceAddressButton").click(function(){
        var items = [];
	    $jq(form).find("select[name=referenceSet]").children().each(function() {
		    items.push($jq(this).data("data"));
	    });
	    
        iKEP.showAddressBook(mode=="view"?setReferenceAddress:iFU.setReferenceAddress, items, {selectType:"user", isAppend:false, selectMaxCnt:100, tabs:{common:1}});
    });
    
    // 열람권한 주소록 찾기 버튼
    $jq("#readAddressButton").click(function(){
        var items = [];
	    $jq(form).find("select[name=readSet]").children().each(function() {
		    items.push($jq(this).data("data"));
	    });
        iKEP.showAddressBook(mode=="view"?setReadAddress:iFU.setReadAddress, items, {selectType:"all", isAppend:false, selectMaxCnt:100, tabs:{common:1}});
    });
    
	// 수신처 지정 팝업
	$jq("#addApprReceiveLineButton").click(function() {
		var apprId	= $jq(form).find("input[name=apprId]:hidden").val();
		var entrustUserId = $jq(form).find("input[name=entrustUserId]:hidden").val();
		var items   = $jq(form).find("input[name=apprReceiveLine]").val()==""?[]:$jq.parseJSON($jq(form).find("input[name=apprReceiveLine]").val());
		iKEP.showApprovalLine(iFU.setApprReceiveLine, items, {selectType:"all",apprId:apprId, apprType:3, entrustUserId:entrustUserId, isAppend:false, tabs:{common:0, personal:0, collaboration:0, sns:0}});			
	});
	
	// 기결재 문서 첨부 팝업
	$jq("#addApprMyRequestCompleteButton").click(function() {
		iKEP.showApprMyRequestComplete(iFU.setApprMyRequestComplete);
	});
};

iFU.initApprLine = function(){
    
    $jq("#apprLine0TTr").children("th:not(:first)").remove();
    $jq("#apprLine0BTr").children().remove();
    $jq("#apprLine0Tr").hide();
    
    $jq("#apprLine1TTr").children("th:not(:first)").remove();
    $jq("#apprLine1BTr").children().remove();
	$jq("#apprLine1Tr").hide();
};

//- 현재 문서 결재라인 가로표시
iFU.setApprLine = function(result){
    iFU.initApprLine();
	$jq("input[name=apprLine]").val("");
	
	var apprId = $jq("input[name=apprId]").val();
	var parentApprId = $jq("input[name=parentApprId]").val();
	var items	=	[];
	var approverId	=	[];
	var apprType0Cnt = 0;
	var apprType1Cnt = 0;
	
	var apprType = 0;	
	
	if(parentApprId==null || parentApprId=="")
		className ="tooltip_approvalInfo_right";
	else
		className ="tooltip_approvalInfo";
	
	// 재기안여부
	var apprCopy = false;
	if($jq("input[name=mode]").length>0 && $jq("input[name=mode]").val()=="copy"){
	    apprCopy = true;
	}
		
	$jq.each(result, function(idx) {
	    var job, name, date;
		var apprTypeShort="";
	    if(this.apprType==0){
	        job = this.jobTitleName;
	        name = this.userName;
	        apprType = 0;
	        apprType0Cnt++;
	    }else{	        
        	job  = this.type=='group' ? this.teamName : this.jobTitleName;
	        name = this.type=='group' ? this.endUser : this.userName;
	        apprType = 1;
	        apprType1Cnt++;
	        if(this.apprType==1)
	        	apprTypeShort=iFC.constant.apprTypeShort[0];
	        else if(this.apprType==2)
	        	apprTypeShort=iFC.constant.apprTypeShort[1];
	    }
	    
	    if(this.apprStatus>0 && apprId !='' && this.type=='group' && IS_MESSAGE_OPEN) {
			$jq("#apprLine" + apprType + "TTr").append($jq.tmpl(iFC.template.apprLineTh1, {"job" : job,"apprId":apprId}));
	    } else {
			$jq("#apprLine" + apprType + "TTr").append($jq.tmpl(iFC.template.apprLineTh, {"job" : job}));	    
	    }
	    if(iFC.mode=="new" || apprCopy)
	        $jq("#apprLine" + apprType + "BTr").append($jq.tmpl(iFC.template.apprLineTdNew, {"name" : name}));
	    else if(this.signFileId!="" && this.signFileId!=undefined)
	        $jq("#apprLine" + apprType + "BTr").append($jq.tmpl(iFC.template.apprLineTdSign, {"name" : name, "className" : className, "signFileId": this.signFileId, "classType" : this.apprStatus, "status" : iFC.constant.apprStatus[this.apprStatus]+apprTypeShort, "date" : this.apprDate}));	    	
	    else
	        $jq("#apprLine" + apprType + "BTr").append($jq.tmpl(iFC.template.apprLineTd, {"name" : name, "classType" : this.apprStatus, "status" : iFC.constant.apprStatus[this.apprStatus]+apprTypeShort, "date" : this.apprDate}));

		items.push({
    				type			:	this.type,
    				apprType		:	this.apprType,
    				id				:	this.id,	
    				code			:	this.id,							
    				userName		:	this.userName,
    				teamName		:	this.teamName,
    				jobTitleName	:	this.jobTitleName,			
    				approverType	:	this.type=="group"?1:0,
    				apprOrder		:	this.apprOrder,				
    				apprStatus		:	this.apprStatus,
    				lineModifyAuth	:	this.lineModifyAuth,
    				docModifyAuth	:	this.docModifyAuth,	
    				readModifyAuth	:	this.readModifyAuth,	
    				apprDate		:	this.apprDate,
    				name			:	this.type=="group"?iFC.constant.apprType[this.apprType]+" "+this.userName +" "+this.jobTitleName:iFC.constant.apprType[this.apprType]+" "+this.name,
    	    		signFileId		:	this.signFileId		
        });
        
        approverId[idx]=this.id;
	});

   	// 결재선 중복 ID 체크
   	if(approverId.length != approverId.unique().length){
		dupLine	=	true;
   	} else {
   		dupLine	=	false;
   	}	

	if(apprType0Cnt>0){
	    $jq("#apprLine0TTr").find(":last-child").addClass("last");
	    $jq("#apprLine0BTr").find(":last-child").addClass("last");
	    $jq("#apprLine0Tr").show();
	}
	
	if(apprType1Cnt>0){
	    $jq("#apprLine1TTr").find(":last-child").addClass("last");
	    $jq("#apprLine1BTr").find(":last-child").addClass("last");
	    $jq("#apprLine1Tr").show();
	}

	if(items.length>0){
	    $jq("input[name=apprLine]").val(JSON.stringify(items));
    }
};


//- 현재 문서 결재라인 세로표시
iFU.setApprLineHeight = function(result){
    $jq("#apprLineListBody").empty();
	$jq("input[name=apprLine]").val("");
	
	var apprId = $jq("input[name=apprId]").val();
	var parentApprId = $jq("input[name=parentApprId]").val();
	var items	=	[];
	var approverId	=	[];

	if(parentApprId==null || parentApprId=="")
		className ="tooltip_approvalInfo_right";
	else
		className ="tooltip_approvalInfo";
	
	// 재기안여부
	var apprCopy = false;
	if($jq("input[name=mode]").length>0 && $jq("input[name=mode]").val()=="copy"){
	    apprCopy = true;
	}
		
	$jq.each(result, function(idx) {
	    var job, name, date;

	   if(this.apprType==0){
	        name = this.userName; 
	    }else{	       
	    	if(this.endUser ==undefined )
	    		this.endUser="";
	        name = this.type=='group' ? this.endUser +"("+this.teamName+")"  : this.userName;
	    }
 
	    if(this.apprStatus>0 && apprId !='' && this.type=='group' && IS_MESSAGE_OPEN) {
			$jq("#apprLineListBody").append($jq.tmpl(iFC.templateHeight.apprLineTd1, {"no" : this.apprOrder,"name":name+" "+this.jobTitleName,"team":this.teamName,"apprType":iFC.constant.apprType[this.apprType],"apprStatus":iFC.constant.apprStatus[this.apprStatus],"date":this.apprDate,"classType" : this.apprStatus,"apprId":apprId}));
	    } else {
			$jq("#apprLineListBody").append($jq.tmpl(iFC.templateHeight.apprLineTd,  {"no" : this.apprOrder,"name":name+" "+this.jobTitleName,"team":this.teamName,"apprType":iFC.constant.apprType[this.apprType],"apprStatus":iFC.constant.apprStatus[this.apprStatus],"classType" : this.apprStatus,"date":this.apprDate}));	    
	    }
 
		items.push({
    				type			:	this.type,
    				apprType		:	this.apprType,
    				id				:	this.id,	
    				code			:	this.id,							
    				userName		:	this.userName,
    				teamName		:	this.teamName,
    				jobTitleName	:	this.jobTitleName,			
    				approverType	:	this.type=="group"?1:0,
    				apprOrder		:	this.apprOrder,				
    				apprStatus		:	this.apprStatus,
    				lineModifyAuth	:	this.lineModifyAuth,
    				docModifyAuth	:	this.docModifyAuth,	
    				readModifyAuth	:	this.readModifyAuth,	
    				apprDate		:	this.apprDate,
    				name			:	this.type=="group"?iFC.constant.apprType[this.apprType]+" "+this.userName +" "+this.jobTitleName:iFC.constant.apprType[this.apprType]+" "+this.name,
    	    		signFileId		:	this.signFileId		
        });
        
        approverId[idx]=this.id;
	});

   	// 결재선 중복 ID 체크
   	if(approverId.length != approverId.unique().length){
		dupLine	=	true;
   	} else {
   		dupLine	=	false;
   	}	
 

	if(items.length>0){
	    $jq("input[name=apprLine]").val(JSON.stringify(items));
    }
};

// 원본 초기화 ( 복사본 문서에서 조회 )
iFU.initParentApprLine = function(){
    
    $jq("#apprLine2TTr").children("th:not(:first)").remove();
    $jq("#apprLine2BTr").children().remove();
    $jq("#apprLine2Tr").hide();
    
    $jq("#apprLine3TTr").children("th:not(:first)").remove();
    $jq("#apprLine3BTr").children().remove();
	$jq("#apprLine3Tr").hide();
};

//- 원본 결재라인 가로
iFU.setParentApprLine = function(result){
    iFU.initParentApprLine();
	
	var apprId = $jq("input[name=apprId]").val();
	var items	= [];
	var apprType0Cnt = 0;
	var apprType1Cnt = 0;
	
	var apprType = 0;	
	
	var className ="tooltip_approvalInfo_right";
	
	$jq.each(result, function() {
	    var job, name, date;
 
	    if(this.apprType==0){
	        job = this.jobTitleName;
	        name = this.userName;
	        apprType = 2;
	        apprType0Cnt++;
	    }else{	        
        	job  = this.type=='group' ? this.teamName : this.jobTitleName;
	        name = this.type=='group' ? this.endUser : this.userName;
	        apprType = 3;
	        apprType1Cnt++;
	    }
	    if(this.apprStatus>0 && apprId !='' && this.type=='group' && IS_MESSAGE_OPEN) {
			$jq("#apprLine" + apprType + "TTr").append($jq.tmpl(iFC.template.apprLineTh1, {"job" : job,"apprId":apprId}));
	    } else {
			$jq("#apprLine" + apprType + "TTr").append($jq.tmpl(iFC.template.apprLineTh, {"job" : job}));	    
	    }
	    if(iFC.mode=="new")
	        $jq("#apprLine" + apprType + "BTr").append($jq.tmpl(iFC.template.apprLineTdNew, {"name" : name}));
	    else if(this.signFileId!="" && this.signFileId!=undefined)
	        $jq("#apprLine" + apprType + "BTr").append($jq.tmpl(iFC.template.apprLineTdSign, {"name" : name, "className" : className, "signFileId": this.signFileId, "classType" : this.apprStatus, "status" : iFC.constant.apprStatus[this.apprStatus], "date" : this.apprDate}));	    	
	    else
	        $jq("#apprLine" + apprType + "BTr").append($jq.tmpl(iFC.template.apprLineTd, {"name" : name, "classType" : this.apprStatus, "status" : iFC.constant.apprStatus[this.apprStatus], "date" : this.apprDate}));

		items.push({
    				type			:	this.type,
    				apprType		:	this.apprType,
    				id				:	this.id,	
    				code			:	this.id,							
    				userName		:	this.userName,
    				teamName		:	this.teamName,
    				jobTitleName	:	this.jobTitleName,			
    				approverType	:	this.type=="group"?1:0,
    				apprOrder		:	this.apprOrder,				
    				apprStatus		:	this.apprStatus,
    				lineModifyAuth	:	this.lineModifyAuth,
    				docModifyAuth	:	this.docModifyAuth,	
    				readModifyAuth	:	this.readModifyAuth,	
    				apprDate		:	this.apprDate,
    				name			:	this.type=="group"?iFC.constant.apprType[this.apprType]+" "+this.userName +" "+this.jobTitleName:iFC.constant.apprType[this.apprType]+" "+this.name,
    				signFileId		:	this.signFileId		
        });
	});
	
	if(apprType0Cnt>0){
	    $jq("#apprLine2TTr").find(":last-child").addClass("last");
	    $jq("#apprLine2BTr").find(":last-child").addClass("last");
	    $jq("#apprLine2Tr").show();
	}
	
	if(apprType1Cnt>0){
	    $jq("#apprLine3TTr").find(":last-child").addClass("last");
	    $jq("#apprLine3BTr").find(":last-child").addClass("last");
	    $jq("#apprLine3Tr").show();
	}


};

//- 원본 결재라인 세로
iFU.setParentApprLineHeight = function(result){
    $jq("#apprLineListParentBody").empty();
	
	var apprId = $jq("input[name=apprId]").val();
	var items	= [];

	
	$jq.each(result, function() {
	    var job, name, date;
 
	    if(this.apprType==0){
	        name = this.userName;
	    }else{	        
	      	if(this.endUser ==undefined )
	    		this.endUser="";
	        name = this.type=='group' ? this.endUser : this.userName;
	    }
 
	    if(this.apprStatus>0 && apprId !='' && this.type=='group' && IS_MESSAGE_OPEN) {
			$jq("#apprLineListParentBody").append($jq.tmpl(iFC.templateHeight.apprLineTd1, {"no" : this.apprOrder,"name":name+" "+this.jobTitleName,"team":this.teamName,"apprType":iFC.constant.apprType[this.apprType],"apprStatus":iFC.constant.apprStatus[this.apprStatus],"date":this.apprDate,"classType" : this.apprStatus,"apprId":apprId}));
	    } else {
			$jq("#apprLineListParentBody").append($jq.tmpl(iFC.templateHeight.apprLineTd,  {"no" : this.apprOrder,"name":name+" "+this.jobTitleName,"team":this.teamName,"apprType":iFC.constant.apprType[this.apprType],"apprStatus":iFC.constant.apprStatus[this.apprStatus],"classType" : this.apprStatus,"date":this.apprDate}));	    
	    }
 
		items.push({
    				type			:	this.type,
    				apprType		:	this.apprType,
    				id				:	this.id,	
    				code			:	this.id,							
    				userName		:	this.userName,
    				teamName		:	this.teamName,
    				jobTitleName	:	this.jobTitleName,			
    				approverType	:	this.type=="group"?1:0,
    				apprOrder		:	this.apprOrder,				
    				apprStatus		:	this.apprStatus,
    				lineModifyAuth	:	this.lineModifyAuth,
    				docModifyAuth	:	this.docModifyAuth,	
    				readModifyAuth	:	this.readModifyAuth,	
    				apprDate		:	this.apprDate,
    				name			:	this.type=="group"?iFC.constant.apprType[this.apprType]+" "+this.userName +" "+this.jobTitleName:iFC.constant.apprType[this.apprType]+" "+this.name,
    				signFileId		:	this.signFileId		
        });
	});

};

// 수신처
iFU.setApprReceiveLine = function(result){
	$jq("#apprReceiveLineInfoDiv").empty();
	$jq("input[name=apprReceiveLine]").val("");
	$jq("input[name=isApprReceive]").val("0");
	
	var items	    =	[];
	var approverId	=	[];
	var receiveLen  = result.length;
	var receiveText = "";
	
	$jq.each(result, function(idx) {
		if(this.type=='user') {
			if(receiveLen==idx+1) {					
				receiveText+=this.userName+" "+this.jobTitleName+" "+this.teamName;					
			} else {
				receiveText+=this.userName+" "+this.jobTitleName+" "+this.teamName+", ";
			}
		} else {
			if(receiveLen==idx+1) {
				receiveText+=this.teamName;
			} else {
				receiveText+=this.teamName+", ";
			}
		}
		
		items.push({
    				type			:	this.type,
    				apprType		:	this.apprType,
    				id				:	this.id,	
    				code			:	this.id,					
    				userName		:	this.userName,
    				teamName		:	this.teamName,
    				jobTitleName	:	this.jobTitleName,			
    				approverType	:	this.type=="group"?1:0,
    				apprOrder		:	this.apprOrder,				
    				apprStatus		:	this.apprStatus,
    				lineModifyAuth	:	this.lineModifyAuth,
    				docModifyAuth	:	this.docModifyAuth,	
    				readModifyAuth	:	this.readModifyAuth,	
    				apprDate		:	this.apprDate,
    				name			:	iFC.constant.apprType[this.apprType]+" "+this.teamName
		});	
		approverId[idx]=this.id;
	});
	
   	// 수신처 중복 ID 체크
   	if(approverId.length != approverId.unique().length){
		dupRcvLine	=	true;
   	} else {
   		dupRcvLine	=	false;
   	}
	
	if(items.length>0){
	    $jq("input[name=apprReceiveLine]").val(JSON.stringify(items));
	    $jq("input[name=isApprReceive]").val("1");
    }
    
	$jq("#apprReceiveLineInfoDiv").text(receiveText);
};

/* 기결재 문서 첨부 */
iFU.setApprMyRequestComplete = function(result) {
	$jq(result).each(function() {
	    if(iFC.mode=="new"){
	        if(!iFU.hasApprRefId(this.apprId)){
		        $jq("#apprRefInfoUl").append($jq.tmpl(iFC.template.apprRefLiNew, this));
		    }
	    }else if(iFC.mode=="view"){
	        $jq("#apprRefInfoUl").append($jq.tmpl(iFC.template.apprRefLiView, this));
	    }else if(iFC.mode=="edit"){
	        if($jq("#readDiv").length>0) {
	            $jq("#apprRefInfoUl").append($jq.tmpl(iFC.template.apprRefLiView, this));
	        }else{
	            if(!iFU.hasApprRefId(this.apprId)){
    		        $jq("#apprRefInfoUl").append($jq.tmpl(iFC.template.apprRefLiNew, this));
    		    }
	        }
	    }
	});
};

/* 기첨부 체크 */
iFU.hasApprRefId = function(apprId){
    var b = false;
    $jq("#apprRefInfoUl").children().each(function() {
        if($jq(this).hasClass(apprId)) {
            b = true;
            return false;
        }
    });
    return b;
};

/* 기결제 저장 */
iFU.setApprRefId = function(form){
    var result = "";
    $jq("#apprRefInfoUl").children().each(function() {
        if(result!="") result +=",";
        result += $jq(this).attr("class");
    });
    
    $jq(form).find("input[name=apprRefId]").val(result);
};

/* 참조자 */
iFU.setReferenceDiv = function() {
    $jq("#referenceDiv").empty();
    var result="";
    $jq("select[name=referenceSet] option").each(function() {
        if(result!="") result += ", ";
        result += $jq(this).text();
    });
    
    $jq("#referenceDiv").html(result);
};

/* 참조자 */
iFU.setReferenceSet = function(form) {
    var result="";
    $jq(form).find("select[name=referenceSet] option").each(function() {
        
        if(result!="") result += ";";
        result += $jq(this).val() + "," + $jq(this).data("data").group;
    });
    
    $jq(form).find("input[name=referenceId]").val(result);
};

/* 열람권한 */
iFU.setReadDiv = function() {
    $jq("#readDiv").empty();
    var result="";
    $jq("select[name=readSet] option").each(function() {
        if(result!="") result += ", ";
        result += $jq(this).text();
    });
    $jq("#readDiv").html(result);
};

/* 열람권한 */
iFU.setReadSet = function(form) {
    var result="", readType="", groupId="";
    $jq(form).find("select[name=readSet] option").each(function() {
        
        if(result!="") result += ";";
        
        if($jq(this).data("data").type=="user"){
            readType = "0";
            groupId =  $jq(this).data("data").group;
        }else{
            readType = "1";
            groupId =  $jq(this).data("data").code;
        }
        
        
        result += readType + "," +$jq(this).val() + "," + groupId;
    });
    
    $jq(form).find("input[name=readId]").val(result);
};

/* 수신참조 정보 추가 by 주소록 */
iFU.setReferenceAddress = function(data, b) {   
	var $sel = $jq("select[name=referenceSet]").children().remove().end();
	if(typeof(b)=="undefined") b = false;
	var c;
	$jq.each(data, function() {
	    if(!b) c = this.id == iFC.user.id; //참조자 선택시 본인은 선택 못함
	    else c = false; // 기존참조자 입력시

	    if(!c){
		    $jq.tmpl(iKEP.template.userOption, this).appendTo($sel).data("data", this);
        }
    });
    
    if($jq("#referenceSetSpan").length>0){
        iFU.setSelectOptionCount("referenceSet", "#referenceSetSpan");
    }
    
    if($jq("#referenceDiv").length>0) iFU.setReferenceDiv();
};

/* 열람권한 정보 추가 by 주소록 */
iFU.setReadAddress = function(data, b) {   
	var $sel = $jq("select[name=readSet]").children().remove().end();
	if(typeof(b)=="undefined") b = false;
	var c;
	$jq.each(data, function() {
		if(this.type=="group") {
		    $jq.tmpl(iKEP.template.groupOption, this).appendTo($sel).data("data", this);
		}else {
		    if(!b) c = this.id == iFC.user.id; //열람권한 선택시 본인은 선택 못함
	        else c = false; // 기존열람권한 입력시
	        
		    if(!c){
		        $jq.tmpl(iKEP.template.userOption, this).appendTo($sel).data("data", this);
		    }
		}
    });
    
    if($jq("#readDiv").length>0) iFU.setReadDiv();
};

/* 열람권한 정보 추가 by 주소록 */
iFU.setGroupName = function(t){
    $jq("input[name=apprGroupName]").val($jq(t).find("option:selected").text());
};

/* 그룹 정보를 구한다 */
iFU.getGroup = function(code, name, callback) {
	$jq.post(iKEP.getContextRoot()+"/approval/admin/apprAdminForm/getGroup.do", {code:code})
	.success(function(data) {
		switch(data.groupName) {
			case "isEmpty" : callback({}, name); break;
			default : 
			            callback({
			                type:"group", 
			                code:data.groupId, 
			                name:data.groupName, 
			                parent:data.parentGroupId,
			                groupTypeId:data.groupTypeId,
			                searchSubFlag:false
			                }, name);
		}
	})
	.error(function(event, request, settings) { alert("error"); });	
};

/* 사용자 정보를 구한다 */
iFU.getUser = function(id, name, callback) {
    
	$jq.post(iKEP.getContextRoot()+"/approval/admin/apprAdminForm/getUser.do", {id:id})
	.success(function(data) {
		switch(data.userName) {
			case "isEmpty" : callback({}, name); break;
			default :
						callback({
				            type:"user",
							id:data.userId,
							empNo:data.empNo,
							name:data.userName + " " + data.jobTitleName,
							userName:data.userName,
							jobTitleName:data.jobTitleName,
							group:data.groupId,
							teamName:data.teamName,
							email:data.mail,
							mobile:data.mobile,
							searchSubFlag:false
						    }, name);
		}
	})
	.error(function(event, request, settings) { alert("error"); });	
};

/* 코드 정보를 구한다 */
iFU.getCode = function(json, callback) {
	$jq.ajaxSetup({async:false});
	$jq.post(iKEP.getContextRoot()+"/approval/admin/apprAdminForm/ajaxListCode.do", {codeId:json.codeId})
	.success(function(data) {
		callback(data, json);
	})
	.error(function(event, request, settings) { alert("error"); });	
};

/* 양식의 중복되지 않는 코드아이디를 구하고 코드 정보를 조회한다 */
iFU.checkFormCodeId = function(){
    // 중복방지
    var codeSet = [];
    for(var i=0;i<iFC.object.formLayoutData.data.length;i++){
        for(var j=0;j<iFC.object.formLayoutData.data[i].items.length;j++){
            if(iFC.object.formLayoutData.data[i].items[j].codeId){
                if(!iFU.jsonHasKey(codeSet, "codeId", iFC.object.formLayoutData.data[i].items[j].codeId)){
                    codeSet.push({"codeId" : iFC.object.formLayoutData.data[i].items[j].codeId});
                    iFU.getCode({"codeId" : iFC.object.formLayoutData.data[i].items[j].codeId}, iFU.setCodeList);
                }
            }
        }
    }
};

/* key값의 바인드 데이터 조회 */
iFU.checkBindData = function(){
    for(var i=0;i<iFC.object.formLayoutData.data.length;i++){
        for(var j=0;j<iFC.object.formLayoutData.data[i].items.length;j++){
            if(iFC.object.formLayoutData.data[i].items[j].type=="textbox" ){
                var regexp = /#[a-zA-Z]+#/g;
                var temp = "";
                while ((match = regexp.exec(iFC.object.formLayoutData.data[i].items[j].html)) !== null ) {
                    temp = match.toString().replaceAll("#", "");
                    iFU.setBindData(temp);
                }

            }
        }
    }
};

// bind  value setting
iFU.setBindData = function(key){
    //중복체크
    if(iFC.bindData[key].value!="") return;
    
    // 사용자 정보 조회
    if(key=="userId" || key=="userName" || key=="jobTitleName" || key=="teamName"){
        // 등록:session userId, 수정, 보기:기안자 아이디
        var id = iFC.mode=="new"?iFC.user.id:iFC.user.registerId;
        iFU.getBindUserData(id, key);
    }
};

/* 사용자 정보를 구한다 */
iFU.getBindUserData = function(id, key) {
    $jq.ajaxSetup({async:false});
	$jq.post(iKEP.getContextRoot()+"/approval/admin/apprAdminForm/getUser.do", {id:id})
	.success(function(data) {
		iFC.bindData[key].value = data[key];
		//alert(JSON.stringify(data));
	})
	.error(function(event, request, settings) { alert("error"); });	
};

/* 조회된 코드정보 저장한다 */
iFU.setCodeList = function(data, json){
    var _data = [];
    
    $jq.each(data, function(){
        _data.push({"text" : this.codeName, "value" : this.codeId});
    });
    
    iFC.object.codeList.push({"codeId" : json.codeId, codeSet :  _data});
};

/* 조회된 코드정보를 item에 추가 */
iFU.setItemCode = function(data, json){
    if(data.length==0) return;
    
    var html = "", sort;
    
    if(json.type=="select") html += "<select name=\"" + json.name + "\">";
    $jq.each(data, function() {
        if(json.type=="select"){
            html += "<option value=\"" + this.codeId + "\">" + this.codeName + "</option>";
        }else if(json.type=="radio"){
            sort    = json.sort == "H"?"&nbsp;&nbsp;":"<br/>";
            html += "<input type=\"radio\" class=\"radio\" name=\"" + json.name + "\" value=\"" + this.codeId + "\" />" + this.codeName + sort;
        }else if(json.type=="checkbox"){
            sort    = json.sort == "H"?"&nbsp;&nbsp;":"<br/>";
            html += "<input type=\"checkbox\" class=\"checkbox\" name=\"" + json.name + this.codeId + "\" value=\"" + this.codeId + "\" />" + this.codeName + sort;
        }
           
    });
    if(json.type=="select") html += "</select>";
    
    $jq("#" + json.name + "Span").html(html);
};

/* Row에 해당하는 양식 HTML를 생성 */
iFU.getRowHtml = function(num, json) {
    
    var tr = "<tr>";
    var td = 0;
    var rs = "";
    var rd = ""; // 필수 표시여부(t:title, o:object, n:none)
    var ct = iFU.getMergedCount(json); // merge count
    var tg = ""; // header
    var ta = ""; // th 정렬
    var tm = ""; // textbox merged count
    var ht = false; //hideTitle 체크로 td colspan 재정의 여부 
    
    // num 재계산
    if(ct!=0){
        var n = iFC.itemCount[num].value-ct;
        $jq.each(iFC.itemCount, function(key){
            if(this.value==n){
                num = key;
                return false;
            }
        });
    }
    
    // hideTitle이 정의된 row colspan 재정의 여부
    if(!(num=="one" || num=="line")){
        var _ht = 0;
        for(var i=0;i<json.length;i++){
            
            if(json[i].hideTitle=="1"){
                _ht++;
            }
        }
        
        // 첫번째 td를 제외한 나머지 td가 hideTitle=1로 설정 되었을경우
        if(_ht+1==iFC.itemCount[num].value){
            ht = true;
        }
    }
    
    // colsapn 재정의시
    var tdHl = 0, tdHd = 0;
    if(ht && iFC.object.formLayoutData.config.thColspan){
        var thColspan = parseInt(iFC.object.formLayoutData.config.thColspan);
        if(thColspan!=iFC.constant.thColspan){
            var gap = thColspan - iFC.constant.thColspan;
            if(gap<0){
                tdHl = gap;
            }else{
                tdHd = Math.floor(gap/iFC.itemCount[num].value);
                tdHl = (gap%iFC.itemCount[num].value)+tdHd;
            }
        }
    }
    
    $jq.each(json, function(i){
        if(this.type=="line"){
            var tlt = this.title!=""? iFC.html.lineTitleImg + " <b>"+this.title + "</b>" : "";
            tr += "<td colspan=\"24\" height=\"" + this.height + "\" style=\"border-top:none;border-left-color:#ffffff;border-right-color:#ffffff;vertical-align:bottom;\">" + tlt + " </td>";
            tr += "<td align=\"right\" colspan=\"16\" height=\"" + this.height + "\" style=\"border-top:none;border-left-color:#ffffff;border-right-color:#ffffff;vertical-align:bottom;\">" + this.description + " </td>";
        }else{
            // 필수 여부 표시
            if(this.required && this.required=="1" && iFC.mode!="view"){
                if(this.hideTitle=="1" || this.merged=="1" || (json[i+1] && json[i+1].hideTitle =="1") || (json[i+1] && json[i+1].merged =="1" && json[i+1].type!="textbox" )){
                    rd = "o";
                }else{
                    rd = "t";
                }
            }else{
                rd="n";
            }
            
            // 헤더 설정 여부
            if(this.type=="textbox" && this.header=="1"){
                tg = "th";
                ta = " style=\"text-align:center\"";
            }else{
                tg = "td";
                ta = "";
            }
            
            // text, textarea 넓이
            if((iFC.mode=="new" || iFC.mode=="edit") && (this.type=="text" || this.type=="textarea")){
                this.styleWidth = iFU.getWidthByUnit(this, num);
            }
            
            if(iFC.mode=="new") rs = iFU.jsonToItemNew(this);
            else if(iFC.mode=="edit") rs = iFU.jsonToItemEdit(this);
            else if(iFC.mode=="view") rs = iFU.jsonToItemView(this);
            
            // validation 체크시 스타일 깨짐 방지
            rs ="<span>" + rs + "</span>";
            
            if(this.merged=="1"){
                tr += this.title + (rd=="o"?"&nbsp;" + iFC.html.required:"") + rs;
            }else{
                // td colspan
                if(ht){
                    if(iFU.isLastTd(i, json)){
                        td = iFC.itemCount[num].hl - tdHl;
                    }else{
                        td = iFC.itemCount[num].hd - tdHd;
                    }
                    
                }else{
                    if(iFU.isLastTd(i, json)){
                        td = iFC.itemCount[num].tl;
                    }else{
                        td = iFC.itemCount[num].td;
                    }
                }
                
                // 제목숨기기
                if(this.hideTitle=="1"){
                    if(!ht) td = td +iFC.itemCount[num].th;
                }else{
                    tr += "<th colspan=\"" + iFC.itemCount[num].th +"\">" + (rd=="t"?iFC.html.required:"") + this.title + "</th>";
                }
                
                // textbox merge
                tm = iFU.getTextboxMergedCount(i, json);
                if(tm>0){
                    td = (tm * iFC.itemCount[num].th) + ((tm+1) * iFC.itemCount[num].td);
                }
                
                tr += "<" + tg + ta + " colspan=\"" + td +"\">" + (rd=="o"?"&nbsp;" + iFC.html.required:"") + rs;
            }
            
            if(json[i+1] && json[i+1].merged=="1") {
                // next merged item   
            }else{
                tr+= "</" + tg + ">";
            }
            iFU.setValidOptions(this);
        }
    });
    tr +="</tr>";
    return tr;
};

/* 병합된 item수를 구한다 */
iFU.getMergedCount = function(json) {
    var cnt = 0;
    for(var i=0;i<json.length;i++){
        if(json[i].type!="textbox" && json[i].merged=="1") cnt++;
    }
    return cnt;
};

/* textbox 병합된 item수를 구한다 */
iFU.getTextboxMergedCount = function(n, json) {
    var cnt = 0;
    for(var i=n+1;i<json.length;i++){
        if(json[i].type=="textbox" && json[i].merged=="1") cnt++;
        else break;
    }
    return cnt;
};

/* 마지막 td여부 */
iFU.isLastTd = function(n, json) {
    var b = true;
    for(var i=n+1;i<json.length;i++){
        if(json[i].merged=="0") return false;
    }
    return b;
};

/* validOption 설정 */
iFU.setValidOptions = function(json) {
    var _rules="", _messages="";
    if(json.required && json.required=="1"){
        
        if(json.type=="embed" && json.maxCount>1){
            _rules    = "\"" + json.name + "\":{\"minSize\":1";
            _messages = "\"" + json.name + "\":{\"minSize\":\"" + iFC.message.minSize + "\"";
        }else{
            _rules    = "\"" + json.name + "\":{\"required\":true";
            _messages = "\"" + json.name + "\":{\"required\":\"" + iFC.message.required + "\"";
        }
    }
    
    if(json.maxlength){
        if(_rules!="") {
            _rules += ", \"maxlength\":" + json.maxlength;
        }else{
            _rules = "\"" + json.name + "\":{\"maxlength\":" + json.maxlength;
        }
        
        if(_messages!="") {
            _messages += ", \"maxlength\":\"" + json.maxlength + iFC.message.maxlength + "\"";
        }else {
            _messages = "\"" + json.name + "\":{\"maxlength\":\"" + json.maxlength + iFC.message.maxlength + "\"";
        }
    }
    
    if(json.type=="text" && json.kind!="all" && iFU.jsonHasKey(iFC.textKind, "value", json.kind)){
        if(_rules!="") {
            _rules += ", \"" + json.kind + "\": true";
        }else{
            _rules = "\"" + json.name + "\":{\""+ json.kind +"\": true";
        }
        
        if(_messages!="") {
            _messages += ", \"" + json.kind + "\":\"" + iFC.message[json.kind] + "\"";
        }else {
            _messages = "\"" + json.name + "\":{\"" + json.kind + "\":\"" + iFC.message[json.kind] + "\"";
        }
    }
    
    if(_rules!="") {
        if(iFC.object.rules!="") iFC.object.rules += ",";
        iFC.object.rules += _rules + "}";
    }
    
    if(_messages!="") {
        if(iFC.object.messages!="") iFC.object.messages += ",";
        iFC.object.messages += _messages + "}";
    }
};

/* MultiRow에 해당하는 양식 HTML를 생성 */
iFU.getMultiRowHtml = function(num, multi, json) {
    var pos = iFC.object.multiRow.length;
    var rs = "";
    var rd = true;  // 필수 표시여부
    var headTr = "";
    var aw; // array width
    var th = 4;
    var td = 36;
    
    if(multi.width && multi.width!=""){
        aw = multi.width.split(",");
    }else{
        aw = "";
    }

    if(iFC.object.formLayoutData.config && iFC.object.formLayoutData.config.thColspan){
        th = parseInt(iFC.object.formLayoutData.config.thColspan);
        td = 40-th;
    }
    
    if(multi.title==""){
        headTr = "<tr><td colspan=\"40\" style=\"padding:0;border:none;\"><table id=\"multiTable" + pos + "\" width=\"100%\"><tr>";
    }else{
        headTr = "<tr><th colspan=\"" + th + "\" style=\"padding:0;border:none;\">" + multi.title + "</th><td colspan=\"" + td + "\"><table style=\"border-top:1px #e0e0e0 solid;table-layout:fixed;\" id=\"multiTable" + pos + "\" width=\"100%\"><tr>";
    }
    
    // head 생성
    var wt = "";
    $jq.each(json, function(i){
        wt = aw!=""&&aw[i]?parseInt(aw[i])+"%":iFC.itemCount[num].mt;
        rd = this.required && this.required=="1"?true:false;
        headTr += "<th width=\"" + wt + "\" style=\"text-align:center;\">"+ (rd?iFC.html.required:"") + this.title + "</th>";
        if(iFC.mode!="view") iFU.setValidOptions(this);
    });
    
    wt = aw!=""&&aw[aw.length]?parseInt(aw[aw.length])+"%":"10%";
    if(iFC.mode!="view") headTr += "<th width=\"" + wt + "\" align=\"center\" style=\"text-align:center;\"><a class=\"button_ic valign_bottom\" href=\"#a\" onclick=\"iFC.addMultiRow(" + pos + ");\"><span>" + iFC.message.add + "</span></a></th></tr>";
    else headTr += "</tr>";
        
    // New Row template
    var contentTr = "";
    
    if(iFC.mode!="view"){
        contentTr = "<tr>";
        $jq.each(json, function(){
            // text, textarea 넓이
            if(this.type=="text"){
                this.styleWidth = iFU.getWidthByUnit(this, num);
            }
            
            contentTr += "<td><div>" + iFU.jsonToItemNew(this) + "</div></td>";
        });
        contentTr += "<td align=\"center\"><a class=\"button_ic valign_bottom\" href=\"#a\" onclick=\"iFC.removeMultiRow(this);\"><span>" + iFC.message.remove + "</span></a></td></tr>";
        iFC.object.multiRow[pos]={"html" : contentTr, "count" : multi.count};
    }
    
    var mContentTr = "";
    if(iFC.mode == "new"){
        for(var i=0;i<multi.count;i++) mContentTr += contentTr;
    }else if(iFC.mode == "view" || iFC.mode == "edit"){
        var values = iFU.getFormDataValue(json[0].type, json[0].name);
        
        for(var i=0;i<values.length;i++){
            mContentTr += "<tr>";
            $jq.each(json, function(){
                if(iFC.mode == "view"){
                    if(this.type=="text"){
                        this.styleWidth = iFU.getWidthByUnit(this, num);
                    }
                    mContentTr += "<td>" + iFU.jsonToItemView(this, i) + "</td>";
                }else{
                    mContentTr += "<td><div>" + iFU.jsonToItemEdit(this, i) + "</div></td>";
                }
            });
            if(iFC.mode == "edit"){
                mContentTr += "<td align=\"center\"><a class=\"button_ic valign_bottom\" href=\"#a\" onclick=\"iFC.removeMultiRow(this);\"><span>" + iFC.message.remove + "</span></a></td></tr>";
            }else{
                mContentTr += "</tr>";
            }
        }
    }
    return headTr + mContentTr + "</table></td></tr>";
};

/* MultiRow 추가 */
iFC.addMultiRow = function(pos){
    $jq("#multiTable" + pos).append(iFC.object.multiRow[pos].html);
};

/* MultiRow 삭제 */
iFC.removeMultiRow = function(obj){
    if($jq(obj).parentsUntil("table").children("tr").length<=2){
        alert(iFC.message.couldntMultiRow);
        return;
    }
    
    $jq(obj).parent().parent().remove();
};


/* 연계 데이터 merge */
iFU.mergeFormLayout = function(layout, linked) {
    for(var i=0;i<layout.data.length;i++){
        for(var j=0;j<layout.data[i].items.length;j++){
            if(layout.data[i].items[j].linked && layout.data[i].items[j].linked=="1"){
                for(var k=0;k<linked.data.length;k++){
                    if(layout.data[i].items[j].type==linked.data[k].type && layout.data[i].items[j].name==linked.data[k].name){
                        if(layout.data[i].items[j].type=="text") {
                            if(linked.data[k].value){
                                layout.data[i].items[j].defaultValue = linked.data[k].value;
                            }
                        } else if(layout.data[i].items[j].type=="textarea") {
                            if(linked.data[k].value){
                                layout.data[i].items[j].template = linked.data[k].value;
                            }
                        } else if(layout.data[i].items[j].type=="textbox") {
                            if(linked.data[k].value){
                                layout.data[i].items[j].html = linked.data[k].value;
                            }
                        } else if(layout.data[i].items[j].type=="select" || layout.data[i].items[j].type=="radio" || layout.data[i].items[j].type=="checkbox"){
                            if(linked.data[k].value){
                                layout.data[i].items[j].options = linked.data[k].value;
                            }
                            if(layout.data[i].items[j].defaultValue){
                                layout.data[i].items[j].defaultValue = linked.data[k].defaultValue;
                            }
                        } 
                        
                        continue;
                    }
                }
            }
        }
    }
    
    return JSON.stringify(layout);
};

/* <p>가 하나일때 병합시 줄바꿈 처리 */
iFU.checkDescription = function(desc) {
    if(desc.matchCount("_lt_p_gt_")==1){
        desc = desc.replaceAll("_lt_p_gt_", "").replaceAll("_lt_/p_gt_", "");
    }
    return iFU.revokeWCData(desc);
};

/* 입력 item 생성 */
iFU.jsonToItemNew = function(json) {
    var html="";
    var evt="";
    if(json.type == "text"){
    	var value = json.defaultValue?json.defaultValue:"";
    	if(json.kind=="numberComma"){
    		evt = " onkeyup=\"iFU.addComma(this);\"";
    	}
        html = "<input type=\"" + json.type + "\" name=\"" + json.name + "\" value=\"" + value + "\" class=\"inputbox\" style=\"width:" + json.styleWidth + "\"" + evt + " />";
    }else if(json.type == "textarea"){
        html = "<textarea name=\"" + json.name + "\" style=\"width:" + json.styleWidth + "height:" + json.height + "px;\"/></textarea>";
    }else if(json.type == "select"){
        if(json.dataType=="0"){
            json.options = iFU.findJSonValueByKey(iFC.object.codeList, "codeId", json.codeId, "codeSet");
        }
        
        html = "<select name=\"" + json.name + "\">";
        var selected;
        var value;
        $jq.each(json.options, function(i){
            // 첫번째 항목은 선택 할 수 없음(require=1)경우 첫번째 항목의 값을 빈값으로 셋팅
            value = json.required=="1" && i==0?"":this.value;
            selected = json.defaultValue == this.value?" selected=\"selected\"":"";
            html += "<option value=\"" + value + "\"" + selected + ">" + this.text + "</option>";
        });
        html += "</select>"; 
        
    }else if(json.type == "radio"){
        if(json.dataType=="0"){
            json.options = iFU.findJSonValueByKey(iFC.object.codeList, "codeId", json.codeId, "codeSet");
        }
        
        var checked, sort;
        $jq.each(json.options, function(){
            checked = json.defaultValue == this.value?" checked=\"checked\"":"";
            sort    = json.sort == "H"?"&nbsp;&nbsp;":"<br/>";
            html += "<input type=\"radio\" class=\"radio\" name=\"" + json.name + "\" value=\"" + this.value + "\"" + checked + "/>" + this.text + sort;
        });
        
    }else if(json.type == "checkbox"){

        if(json.dataType=="0"){
            json.options = iFU.findJSonValueByKey(iFC.object.codeList, "codeId", json.codeId, "codeSet");
        }
        
        var checked, sort;
        $jq.each(json.options, function(){
            checked = this.checked=="1"?" checked=\"checked\"":"";
            sort    = json.sort == "H"?"&nbsp;&nbsp;":"<br/>";
            html += "<input type=\"checkbox\" class=\"checkbox\" name=\"" + json.name + this.value + "\"" + checked + "/>" + this.text + sort;
        });
        
    }else if(json.type=="textbox"){
        html = iFU.convertTextboxKind(iFU.revokeWCData(json.html));
    }else if(json.type=="embed"){
        if(json.kind=="user" || json.kind=="group"){
            if(json.maxCount=="1"){
                html += "<input type=\"hidden\" name=\"" + json.name + "\"/><input type=\"text\" style=\"width:100px;\" class=\"inputbox\" name=\"" + json.name + "Value\" />";
                html += "&nbsp;<a id=\"" + json.name + "SearchButton\" class=\"button_ic valign_bottom\" href=\"#a\"><span>" + iFC.html.searchImg + "Search</span></a>";    
                html += "&nbsp;&nbsp;<a id=\"" + json.name + "AddressButton\" class=\"button_ic valign_bottom\" href=\"#a\"><span>" + iFC.html.addressImg + "Address</span></a>";
            }else{
                html += "<input type=\"text\" class=\"inputbox\" style=\"width:100px;\" name=\"" + json.name + "Value\" />";
                html += "&nbsp;<a id=\"" + json.name + "SearchButton\" class=\"button_ic valign_bottom\" href=\"#a\"><span>" + iFC.html.searchImg + "Search</span></a>";    
                html += "&nbsp;&nbsp;<a id=\"" + json.name + "AddressButton\" class=\"button_ic valign_bottom\" href=\"#a\"><span>" + iFC.html.addressImg + "Address</span></a><br/><div class=\"blockBlank_5px\"></div>";
                html += "<select class=\"inputbox w50\" multiple=\"multiple\" size=\"3\" name=\"" + json.name + "\" /></select>"; 
                html += " <a id=\"" + json.name + "DeleteButton\" class=\"button_ic valign_bottom\" href=\"#a\"><span>" + iFC.html.deleteImg + "Delete</span></a>";
                html += "<span class=\"valign_bottom\">&nbsp;&nbsp;&nbsp;(" + iFC.message.total + " : <span id=\"" + json.name + "MaxCountSpan\">0</span>/" +  + json.maxCount + ")</span>";
            }
        }else if(json.kind=="date"){
            html = "<input type=\"text\" class=\"inputbox\" name=\"" + json.name + "\"  id=\"" + json.name + "\" value=\"\" title=\"date\" size=\"9\" /> " + iFC.html.dateImg;
        }
    }else{
        html = "N/A";
    }
    
    if(json.description && json.description!=""){
        var desc = iFU.checkDescription(json.description);
        
        if(desc.substring(0, 3).toLowerCase()=="<p>"){
            html = desc.substring(0, 3) + html + " " + desc.substring(3)
        }else{
            html += " " + desc;
        }
    }
    
    return html;
};

/* 입력 item 수정 */
iFU.jsonToItemEdit = function(json, index) {
    var html="";
    var evt="";
    var value = iFU.getFormDataValue(json.type, json.name, index);
    if(value=="N/A") return;
    
    if(json.type == "text"){
        if(json.kind=="numberComma"){
    		evt = " onkeyup=\"iFU.addComma(this);\"";
    	}
        html = "<input type=\"" + json.type + "\" name=\"" + json.name + "\" class=\"inputbox\" style=\"width:" + json.styleWidth + "\"  value=\"" + value + "\"" + evt + " />";
    }else if(json.type == "textarea"){
        html = "<textarea name=\"" + json.name + "\" style=\"width:" + json.styleWidth + "height:" + json.height + "px;\"/></textarea>";
    }else if(json.type == "select"){
        if(json.dataType=="0"){
            json.options = iFU.findJSonValueByKey(iFC.object.codeList, "codeId", json.codeId, "codeSet");
        }
        
        html = "<select name=\"" + json.name + "\">";
        
        var value2;
        var selected;
        $jq.each(json.options, function(i){
            value2 = json.required=="1" && i==0?"":this.value;
            selected = value == value2?" selected=\"selected\"":"";
            html += "<option value=\"" + value2 + "\"" + selected + ">" + this.text + "</option>";
        });
        
    }else if(json.type == "radio"){
        if(json.dataType=="0"){
            json.options = iFU.findJSonValueByKey(iFC.object.codeList, "codeId", json.codeId, "codeSet");
        }
        
        var checked, sort;
        $jq.each(json.options, function(){
            checked = value == this.value?" checked=\"checked\"":"";
            sort    = json.sort == "H"?"&nbsp;&nbsp;":"<br/>";
            html += "<input type=\"radio\" name=\"" + json.name + "\" value=\"" + this.value + "\"" + checked + "/>" + this.text + sort;
        });
     
    }else if(json.type == "checkbox"){

        if(json.dataType=="0"){
            json.options = iFU.findJSonValueByKey(iFC.object.codeList, "codeId", json.codeId, "codeSet");
        }
        
        var checked, sort;
        for(var i=0;i<json.options.length;i++){
            var v = "";
            for(var j=0;j<value.length;j++){
                if(json.options[i].value==value[j].name){
                    v = value[j].value;
                    break;
                }
            }
            
            checked = v=="1"?" checked=\"checked\"":"";
            sort    = json.sort == "H"?"&nbsp;&nbsp;":"<br/>";
            html += "<input type=\"checkbox\" class=\"checkbox\" name=\"" + json.name + json.options[i].value + "\"" + checked + "/>" + json.options[i].text + sort;
        }
        
    }else if(json.type=="textbox"){
        html = iFU.convertTextboxKind(iFU.revokeWCData(json.html));
    }else if(json.type=="embed"){
        if(json.kind=="user" || json.kind=="group"){
            if(json.maxCount=="1"){
                html += "<input type=\"hidden\" name=\"" + json.name + "\"/><input type=\"text\" style=\"width:100px;\" class=\"inputbox\" name=\"" + json.name + "Value\" />";
                html += "&nbsp;<a id=\"" + json.name + "SearchButton\" class=\"button_ic valign_bottom\" href=\"#a\"><span>" + iFC.html.searchImg + "Search</span></a>";    
                html += "&nbsp;&nbsp;<a id=\"" + json.name + "AddressButton\" class=\"button_ic valign_bottom\" href=\"#a\"><span>" + iFC.html.addressImg + "Address</span></a>";
            }else{
                html += "<input type=\"text\" class=\"inputbox\" style=\"width:100px;\" name=\"" + json.name + "Value\" />";
                html += "&nbsp;<a id=\"" + json.name + "SearchButton\" class=\"button_ic valign_bottom\" href=\"#a\"><span>" + iFC.html.searchImg + "Search</span></a>";    
                html += "&nbsp;&nbsp;<a id=\"" + json.name + "AddressButton\" class=\"button_ic valign_bottom\" href=\"#a\"><span>" + iFC.html.addressImg + "Address</span></a><br/><div class=\"blockBlank_5px\"></div>";
                html += "<select class=\"inputbox w50\" multiple=\"multiple\" size=\"3\" name=\"" + json.name + "\" /></select>"; 
                html += "<a id=\"" + json.name + "DeleteButton\" class=\"button_ic valign_bottom\" href=\"#a\"><span>" + iFC.html.deleteImg + "Delete</span></a>";
                html += "<span class=\"valign_bottom\">&nbsp;&nbsp;&nbsp;(" + iFC.message.total + " : <span id=\"" + json.name + "MaxCountSpan\">0</span>/" +  + json.maxCount + ")</span>";
            }
        }else if(json.kind=="date"){
            html = "<input type=\"text\" class=\"inputbox\" name=\"" + json.name + "\"  id=\"" + json.name + "\" value=\"" + value + "\" title=\"date\" size=\"9\" /> " + iFC.html.dateImg;
        }
    }else{
        html = "N/A";
    }
    
    if(json.description && json.description!=""){
        var desc = iFU.checkDescription(json.description);
        if(desc.substring(0, 3).toLowerCase()=="<p>"){
            html = desc.substring(0, 3) + html + " " + desc.substring(3)
        }else{
            html += " " + iFU.revokeWCData(json.description);
        }
    }
        
    return html;
};

/* 조회 item 생성 */
iFU.jsonToItemView = function(json, index) {
    var html="";
    var value = iFU.getFormDataValue(json.type, json.name, index);
    if(value=="N/A") return;
    
    if(json.type == "text"){
        html = "<span>" + value + "</span>";
    }else if(json.type == "textarea"){
        html =  "<span>" + value.replaceAll("\n", "<br/>") + "</span>";
    }else if(json.type == "select"){
        var selectedText="";
        for(var i=0;i<json.options.length;i++){
            if(value == json.options[i].value){
                selectedText = json.options[i].text;
                break;
            }
        }
        html = "<span>" + selectedText + "</span>";
    }else if(json.type == "radio"){
        var checkedText="";
        for(var i=0;i<json.options.length;i++){
            if(value == json.options[i].value){
                checkedText = json.options[i].text;
                break;
            }
        }
        html = "<span>" + checkedText + "</span>";         
    }else if(json.type == "checkbox"){
        var checked = "";
        for(var i=0;i<json.options.length;i++){
            var v = "", p = "";
            for(var j=0;j<value.length;j++){
                if(json.options[i].value==value[j].name){
                    v = value[j].value;
                    break;
                }
            }
            
            if(v=="0") p="<span style=\"color:red\">" + iFC.message.notChecked + "</span>";
            else if(v=="1") p="<span style=\"color:blue\">" + iFC.message.checked + "</span>";
            
            if(checked!="") checked+="<br/>";
            checked += json.options[i].text + " : " + p;
        }

        html = checked;
    }else if(json.type=="textbox"){
        html = iFU.convertTextboxKind(iFU.revokeWCData(json.html));
    }else if(json.type=="embed"){
        if(json.kind=="user" || json.kind=="group"){
            html += "<span id=\"" + json.name + "\"></span>";
        }else{
            html = "<span>" + value + "</span>";
        }
    }else{
        html = "N/A";
    }
   
    return html;
};

/* textbox 태그 */
iFU.convertTextboxKind = function(html) {
    var html2=html;
    var regexp = /#[a-zA-Z]+#/g;
    var temp = "";
    
    while ((match = regexp.exec(html)) !== null ) {
        temp = match.toString().replaceAll("#", "");
        if(iFC.bindData[temp]){
            html2 = html2.replaceAll(match, iFC.bindData[temp].value);
        }
    }
    
    return html2;
};

/* form layout 생성후 작업 */
iFU.checkFormLayoutData = function(json){
    $jq.each(json, function(){
        if(this.type=="textarea"){
            // template 데이터가 있을 시
            var val = iFC.mode=="new"?this.template:iFU.getFormDataValue(this.type, this.name);
            $jq("textarea[name=" + this.name + "]").val(val);
        }else if(this.type=="embed"){
            if(this.kind=="user" || this.kind=="group"){
                var t = this;
                $jq("input[name=" + this.name + "Value]").keypress( function(event) {
            	    iFC.object.embedItem = t;
            	    if(t.kind=="user"){
            	        iKEP.searchUser(event, t.maxCount>1?"Y":"N", iFU.setEmbedSearch);
            	    }else{
            	        iKEP.searchGroup(event, t.maxCount>1?"Y":"N", iFU.setEmbedSearch);
            	    }
                });
                
                $jq("#" + this.name + "SearchButton").click(function(){
                    $jq("input[name=" + t.name + "Value]").trigger("keypress");
                });
                
                // Address
                $jq("#" + this.name + "AddressButton").click( function() {
                    var items = [];
                   
                    if(t.maxCount!="1"){
    			        $jq("select[name=" + t.name + "]").children().each(function() {
                            items.push($jq(this).data("data"));
    			        });
    			    }
			        iFC.object.embedItem = t;
			        iKEP.showAddressBook(iFU.setEmbedAddress, items, {selectType:t.kind, isAppend:false, selectMaxCnt:t.maxCount, tabs:{common:1}});
			    });
			    
			    // Delete
			    if($jq("#" + this.name + "DeleteButton").length>0){
                    $jq("#" + this.name + "DeleteButton").click( function() {
                        $jq("select[name=" + t.name + "] option:selected").remove();
                        iFU.setSelectOptionCount(t.name, "#" + t.name + "MaxCountSpan");
                    });
                }
		    }else if(this.kind=="date"){
	            $jq("#" + this.name).datepicker().next().eq(0).click(function() { 
                    $jq(this).prev().eq(0).datepicker("show");  
                });
            }
          
        }
        /*
        }else if(this.codeId){
            var sort = this.sort?this.sort:"";
            iFU.getCode(this, iFU.setItemCode);
        }
        */
            
    });
};

/* 사용자 부서 조회 */
iFU.setEmbedSearch = function(data) {
     if(data == "") {
		alert(iFC.message.notFoundResult);
		 $jq("input[name=" + iFC.object.embedItem.name + "Value]").val("");
		return;
    } 
    
	$jq(data).each(function() {
	    if(iFC.object.embedItem.maxCount=="1"){
	        if(iFC.object.embedItem.kind=="user"){
	            $jq("input[name=" + iFC.object.embedItem.name + "Value]").val(this.userName + " " + this.jobTitleName);
                $jq("input[name=" + iFC.object.embedItem.name + "]").val(this.id);
            }else{
                $jq("input[name=" + iFC.object.embedItem.name + "Value]").val(this.name);
                $jq("input[name=" + iFC.object.embedItem.name + "]").val(this.code);
            }
	    }else{ 
	        if(iFC.object.embedItem.kind=="user"){
	            if(!iFU.hasSelectOptionValue(iFC.object.embedItem.name, this.id)){
		            $jq.tmpl(iKEP.template.userOption, this).appendTo($jq("select[name=" + iFC.object.embedItem.name + "]")).data("data", this);
		        }
		            
		    }else{
		        if(!iFU.hasSelectOptionValue(iFC.object.embedItem.name, this.code)){
		            $jq.tmpl(iKEP.template.groupOption, this).appendTo($jq("select[name=" + iFC.object.embedItem.name + "]")).data("data", this);
		        }
		    }
		    
		    iFU.setSelectOptionCount(iFC.object.embedItem.name, "#" + iFC.object.embedItem.name + "MaxCountSpan");
	    }
	});	
};

/* 사용자, 부서정보 추가 */
iFU.setEmbedAddress = function(data){
    if($jq("input[name=" + iFC.object.embedItem.name + "]").attr("type")){
        $jq.each(data, function() {
            var val = this.type=="user"?this.id:this.code;
            $jq("input[name=" + iFC.object.embedItem.name + "]").val(val);
            $jq("input[name=" + iFC.object.embedItem.name + "Value]").val(this.name);
        });
    
    }else{
        var $sel = $jq("select[name=" + iFC.object.embedItem.name + "]").children().remove().end();
		$jq.each(data, function() {
			$jq.tmpl(this.type=="user"?iKEP.template.userOption:iKEP.template.groupOption, this)
			.appendTo($sel)
			.data("data", this);
	    });
	    
	    iFU.setSelectOptionCount(iFC.object.embedItem.name, "#" + iFC.object.embedItem.name + "MaxCountSpan");
    }
};

/* 해당item의 값을 조회 */
iFU.getFormDataValue = function(type, name, index) {
    
    if(typeof(index)=="undefined") index = "N/A";
    if(type=="textbox") return "";
    for(var i=0;i<iFC.object.formData.data.length;i++){
        if(iFC.object.formData.data[i].type == type && iFC.object.formData.data[i].name == name){
            return index=="N/A"?iFC.object.formData.data[i].value:iFC.object.formData.data[i].value[index];
        }
    }
    return "N/A";  
};

/* form layout 생성후 embed값추가 */
iFU.checkEmbed = function(){
    for(var i=0;i<iFC.object.formData.data.length;i++){
        if(iFC.object.formData.data[i].type == "embed"){
            for(var j=0;j<iFC.object.formData.data[i].value.length;j++){
                if(iFC.object.formData.data[i].value[j]=="") continue;
                if(iFC.object.formData.data[i].kind=="user"){
                    iFU.getUser(iFC.object.formData.data[i].value[j], iFC.object.formData.data[i].name, iFU.jsonToEmbedHtml);
                }else if(iFC.object.formData.data[i].kind=="group"){
                    iFU.getGroup(iFC.object.formData.data[i].value[j], iFC.object.formData.data[i].name, iFU.jsonToEmbedHtml);
                }
            }
            
        }
    }
};

/* embed html 생성 */
iFU.jsonToEmbedHtml = function(json, name){
    if(iFC.mode=="view"){
        var html = $jq("#" + name).html();
        $jq("#" + name).html(html + json.name + "<br/>");
    }else{
        if($jq("input[name=" + name + "]").length==0){
            $jq.tmpl(json.type=="user"?iKEP.template.userOption:iKEP.template.groupOption, json)
    				.appendTo($jq("select[name=" + name + "]"))
    				.data("data", json);
            iFU.setSelectOptionCount(name, "#" + name + "MaxCountSpan");
        }else{
            $jq("input[name=" + name + "Value]").val(json.name);
            $jq("input[name=" + name + "]").val(json.type=="user"?json.id:json.code);
        }
        
            
    }
};

/* 양식 생성 */
iFU.printForm = function(){
    
    // code setting
    iFU.checkFormCodeId();
    iFU.checkBindData();
    
    // table setting
    //$jq("#layoutTitle > h3").html(iFC.object.formLayoutData.title);
    $jq("#layoutTitle > h3").html("&nbsp;");
    
    // base td 생성
    var tfootHtml = "<tr>";
    for(var i=0;i<40;i++){
        tfootHtml += iFC.html.layoutTfoot;
    }
    tfootHtml += "</tr>";
    
    $jq("#layoutTable > tfoot").html(tfootHtml);
    $jq("#layoutTable").css("table-layout", "fixed");
    
    // form config
    if(iFC.object.formLayoutData.config){
        // 필수여부 표시안함
        if(iFC.object.formLayoutData.config.required && iFC.object.formLayoutData.config.required=="0"){
            iFC.html.required = "";
        }
        
        // th colspan
        if(iFC.object.formLayoutData.config.thColspan){
            var thColspan = parseInt(iFC.object.formLayoutData.config.thColspan);
            if(thColspan!=iFC.constant.thColspan){
                var th = thColspan - iFC.constant.thColspan;
                var td = iFC.constant.thColspan - thColspan;
                
                // colspan 재개산
                $jq.each(iFC.itemCount, function(){
                    this.th = this.th + th;
                    this.td = this.td + td;
                    this.tl = this.tl + td;
                });
            }
        }
    }
    
    // form layout
    $jq.each(iFC.object.formLayoutData.data, function(i){
        if(i==0){
            if(this.type=="line"){
                $jq("#layoutTable").parent().css("border-top", "none");
            }
        }
        
        // 예전 데이터엔 multi속성이 필수 였기 때문에 예외처리
        if(this.multi && this.multi.count){
            $jq("#layoutTable > tbody").append(iFU.getMultiRowHtml(this.type, this.multi, this.items));
        }else{
            $jq("#layoutTable > tbody").append(iFU.getRowHtml(this.type, this.items));
        }
    });

    if(iFC.mode=="new" || iFC.mode=="edit"){
        // form layout check
        $jq.each(iFC.object.formLayoutData.data, function(){
            iFU.checkFormLayoutData(this.items);
        });
    }
    
    if(iFC.mode=="view" || iFC.mode=="edit"){
        // embed check
        iFU.checkEmbed();
    }
};

/* form data 생성 */
iFU.itemToFormData = function(form, formId, apprTitle){
    var json  = { formId : "",
                  apprTitle : "",
                  data   : []
                };
    
    json.formId = formId;
    json.apprTitle = apprTitle;
    
    var item;
    for(var i=0;i<iFC.object.formLayoutData.data.length;i++){
        for(var j=0;j<iFC.object.formLayoutData.data[i].items.length;j++){
            // textbox는 skip
            if(iFC.object.formLayoutData.data[i].items[j].type=="textbox" || iFC.object.formLayoutData.data[i].items[j].type=="line") continue;
            //- checkbox는 name + option value 값으로 이름이 만들어짐
            if(!form[iFC.object.formLayoutData.data[i].items[j].name] && iFC.object.formLayoutData.data[i].items[j].type!="checkbox") {
                alert("Validation Error " + iFC.object.formLayoutData.data[i].items[j].name);
                return "false";
            }
            
            item  = iFC.object.formLayoutData.data[i].items[j];
            
            var data   = {"type" : "", "name" : "", "value" : null};
            data.type = item.type;
            data.name = item.name;
            
            // text, select 개체만 multiRow 속성을 가질수 있음
            if(data.type=="text" || data.type=="select"){
                if(iFC.object.formLayoutData.data[i].multi && iFC.object.formLayoutData.data[i].multi.count){
                    data.multi = "1";
                    data.value = [];
                    $jq(form).find("[name=" + data.name + "]").each(function() {
                        data.value.push($jq(this).val());    
                    });
                }else{
                    data.multi = "0";
                    data.value = $jq(form).find("[name=" + data.name + "]").val();
                }
            }else if(data.type=="radio"){
                data.value = $jq(form).find("input[name=" + data.name + "]:checked").val();
            }else if(data.type=="checkbox"){
                data.value = [];
                $jq.each(item.options, function(key){
                    data.value.push({"name" : this.value, "value" : $jq(form).find("input[name=" + data.name + this.value + "]").attr("checked")?"1":"0"});
                });	                        
            }else if(data.type=="embed"){
                data.kind = item.kind;
                if(data.kind=="user" || data.kind=="group"){
                    data.value = [];
                    if((form[data.name].tagName).toUpperCase()=="INPUT"){
                        data.value.push($jq(form).find("[name=" + data.name + "]").val());
                    }else{
                        $jq(form).find("select[name=" + data.name + "] option").each(function() {
                            data.value.push($jq(this).val());
                        });      
                    }
                }else{
                    data.value = $jq(form).find("[name=" + data.name + "]").val();
                }
            } else {
               data.value = $jq(form).find("[name=" + data.name + "]").val();
            }
            
            json.data.push(data);
        }

    }    
    
    return json;
};
// iKEP Form Layout Process Util End ----------------------------------------------------------



// iKEP Form PopUp Util Start ------------------------------------------------------------------
/* 기안의견 */
iFU.viewApprDocRegisterMessagePopUp = function(){
    
    iKEP.showDialog({     
		title 		: iFC.message.approvalPopUp,
    	url 		: iKEP.getContextRoot() + "/approval/work/apprWorkDoc/viewApprDocRegisterMessage.do",
    	modal 		: true,
    	width 		: 400,
    	height 		: 250,
    	callback	: function(result) {
		    $jq("input[name=registerMessage]").val(result);
		    iFC.object.process = true;
		    $jq("#createApprDocButton").click();
		}
	});
};
/* 기안의견-부서/수신처 문서 */
iFU.viewChildApprDocRegisterMessagePopUp = function(){
    
    iKEP.showDialog({     
		title 		: iFC.message.approvalPopUp,
    	url 		: iKEP.getContextRoot() + "/approval/work/apprWorkDoc/viewApprDocRegisterMessage.do",
    	modal 		: true,
    	width 		: 400,
    	height 		: 250,
    	callback	: function(result) {
		    $jq("input[name=registerMessage]").val(result);
		    iFC.object.process = true;
		    $jq("#createChildApprDocButton").click();
		}
	});
};
/* 변경사유 */
iFU.viewApprDocChangeReasonPopUp = function(){

    iKEP.showDialog({     
		title 		: iFC.message.reasonPopUp,
    	url 		: iKEP.getContextRoot() + "/approval/work/apprWorkDoc/viewApprDocChangeReason.do",
    	modal 		: true,
    	width 		: 400,
    	height 		: 250,
    	callback	: function(result) {
		    $jq("input[name=changeReason]").val(result);
		    iFC.object.process = true;
		    $jq("#updateApprDocContentButton").click();
		}
	});
};

/* 상세 */
iFU.viewApprDocPopUp = function(apprId, listType){
    var relApprId = $jq("#apprDocForm input[name=apprId]").val();
    iKEP.showDialog({     
		title 		: iFC.message.docDeatilPopUp,
    	url 		: iKEP.getContextRoot() + "/approval/work/apprWorkDoc/viewApprDoc.do?&popupYn=true&relApprId=" + relApprId + "&apprId=" + apprId + "&listType=" + listType,
    	modal 		: true,
    	width 		: 850,
    	height 		: 500,
    	callback	: function(result) {
		    
		}
	});
};
// iKEP Form PopUp Util End --------------------------------------------------------------------

// approval tooltip info start ------------------------------------------------------------------
iFU.showApprTooltipInfo = function(isShow, target, info, direction) {	//info : name, rank, team
	var $container = null;
	
	if(!iFU.showApprTooltipInfo.prototype.container) {
		$container = jQuery('<div style="top:0; left:0;" class="infoLayer none"/>')
			.append('<div class="corner_RoundBox10">' +
					'<div><span class="name"></span> &nbsp;<span class="team"></span></div>' +
					'<div class="ar"></div>' +
					'<div class="l_t_corner"></div>' +
					'<div class="r_t_corner"></div>' +
					'<div class="l_b_corner"></div>' +
					'<div class="r_b_corner"></div>' +		
				'</div>')
			.appendTo(document.body);

		iFU.showApprTooltipInfo.prototype.container = $container;
	}
	
	$container = iFU.showApprTooltipInfo.prototype.container;
	if(isShow) {
		$container.find("span.name").html(info.name + " " + info.rank).end()
			.find("span.team").html(info.apprTime);
		
		var clickItem = jQuery(target);
		var position = clickItem.offset();	// offset()
		
		var offsetTop = direction == "bottom" ?  clickItem.outerHeight() + 8 : $container.outerHeight()*-1 - 8;
		var offsetLeft = direction == "right" ?  $container.outerWidth()-clickItem.outerWidth() : 0;
		$container.show().css({
			left:(position.left-offsetLeft)+"px",
			top:(position.top+offsetTop)+"px"
		});
		
		if(direction == "right") {
			$container.find("div.ar").css("left", ($container.width()-25)+"px");
		} else {
			$container.find("div.ar").css("left", "10px");
		}
	} else {
		$container.hide();
	}
};
iFU.showApprTooltipInfo.prototype.container;
//approval tooltip info end ------------------------------------------------------------------

// approval content page initialize process start --------------------------------------------
(function($){
	$("body").delegate("img.tooltip_approvalInfo, img.tooltip_approvalInfo_right", "hover", function(event) {
		if(event.type == "mouseenter") {
			var info = $(this).data("info");
			if(!info) {
				var arrInfo = $(this).attr("title").split("/");
				info = {name:arrInfo[0], rank:arrInfo[1], apprTime:arrInfo[2]};
				$(this).data("info", info);
				$(this).removeAttr("title");
			}
			iFU.showApprTooltipInfo(1, this, info, $(this).hasClass("tooltip_approvalInfo_right") ? "right" : "left");
		} else {
			iFU.showApprTooltipInfo(0);
		}
	});
})(jQuery);
// approval content page initialize process end ----------------------------------------------



// dup ApprLine Check
// 배열요소 중복제거		
Array.prototype.unique=function() {
	var newArray=[], len=this.length;
	label:for(var i=0; i<len; i++) {
		for(var j=0; j<newArray.length; j++)
			if(newArray[j]==this[i]) 
				continue label;
		newArray[newArray.length] = this[i];
	}
	return newArray;
};