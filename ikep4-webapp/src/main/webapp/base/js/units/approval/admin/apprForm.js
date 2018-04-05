// var $jq = jQuery.noConflict(); commoon.pack.js include

// iKEP apprForm Config Start ----------------------------------------------------------------------
var iFC = { 
            "object" : {
                        // item Div
                        "editItem" : null,
                        
                        // action item Div
                        "actionItem" : null,
                        
                        // over action item status Div
                        "overActionItem" : false,
                        
                        // action row Div
                        "actionRow" : null,
                        
                        // formLayoutData
                        "formLayoutData" : {},
                        
                        // process
                        "process" : false,
                        
                        // popup
                        "popup" : null,
                        
                        // jsonViewer
                        "jsonViewer" : null
                     },
            
            // 사용자 정보         
            "user" : {"id"          : "",
                      "locale"      : "",
                      "registerId"  : ""   // 기안자 아이디
                     },
    
            // item 정의
            "item" : {  
                        "text"     : {"name" : "텍스트",       "seq" : 0, "tabId" : "0", "icon" : "ic_appr_input.png"},
                        "textarea" : {"name" : "텍스트박스",   "seq" : 0, "tabId" : "1", "icon" : "ic_appr_textarea.png"},
                        "select"   : {"name" : "셀렉트",       "seq" : 0, "tabId" : "2", "icon" : "ic_appr_select.png"},
                        "radio"    : {"name" : "라디오",       "seq" : 0, "tabId" : "3", "icon" : "ic_appr_radio.png"},
                        "checkbox" : {"name" : "체크박스",     "seq" : 0, "tabId" : "4", "icon" : "ic_appr_checkbox.png"},
                        "textbox"  : {"name" : "글상자",       "seq" : 0, "tabId" : "5", "icon" : "ic_appr_layer.png"},
                        "embed"    : {"name" : "외부링크",     "seq" : 0, "tabId" : "6", "icon" : "ic_appr_exlink.png"},
                        "line"     : {"name" : "구분선",       "seq" : 0, "tabId" : "-1", "icon" : "ic_appr_line.png"}
                     },
    
            // row에 추가할수 있는 item개수
            "itemCount" : {   
                            "one"   : {"value" : 1, "prev" : "N/A",   "next" : "two"},
                            "two"   : {"value" : 2, "prev" : "one",   "next" : "three"},
                            "three" : {"value" : 3, "prev" : "two",   "next" : "four"},
                            "four"  : {"value" : 4, "prev" : "three", "next" : "five"},
                            "five"  : {"value" : 5, "prev" : "four",  "next" : "six"},
                            "six"   : {"value" : 6, "prev" : "five",  "next" : "N/A"},
                            "line"  : {"value" : 1, "prev" : "N/A",   "next" : "N/A"}
                          },            
                          
            // 멀티 허용 개체
            "allowMultiItem" : ["text", "select"],
                          
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
            
            // embedTab kind
            "embedKind" : [
                           {"value" : "user",    "text" : "사용자"},
                           {"value" : "group",   "text" : "그룹"},
                           {"value" : "date",    "text" : "날짜"}
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
                          "textboxKind"    : "<a class=\"button_ic valign_bottom\" href=\"#a\" onclick=\"addCkHtml('#${name}#')\"><span>${text}</span></a>&nbsp;&nbsp;",
                          "itemIcon"       : "<img style=\"vertical-align:top\" src=\"/ikep4-webapp/base/images/icon/${icon}\" /> <span>${name}</span>&nbsp;&nbsp;",
                          "itemButton"     : "<a href=\"#a\" onclick=\"iFU.addRow(${len}, '${num}')\" title=\"${num}\"><img src=\"/ikep4-webapp/base/images/icon/ic_appr_${icon}.gif\" /></a>&nbsp;",
                          "smallButton"    : "<a class=\"button_ic valign_bottom\" href=\"#a\" onclick=\"${func}\"><span>${name}</span></a>&nbsp;&nbsp;"                          
                         },             
                        
            
            // 스타일 정의
            "style" : {
                        "overColor"  : "#e7e7e7",
                        "hasColor"   : "#aac5f3",
                        "linkColor"  : "#d4eca5",
                        "errColor"   : "#ffcea1"
                      },
             
             // html         
             "html"  : {
                         "itemSplit"    : "&nbsp;&nbsp;<span style=\"color:gray\">|</span>&nbsp;&nbsp;&nbsp;",
                         "divClear"     : "<div class=\"clear\"></div>"
                       },
                       
             // message
             "message" : {
                            "itemDiv"           : "항목 추가",
                            "lineDiv"           : "구분선 설정",
                            "preview"           : "미리보기",
                            "initialize"        : "초기화",
                            "config"            : "양식설정",
                            "template"          : "서식",
                            "history"           : "버전이력",
                            "linkedJson"        : "연계JSON생성",
                            "initFormLayout"    : "초기화 하시겠습니까?",
                            "removeAuthUser"    : "담당자 정보를 삭제하시 겠습니까?",
                            "notFoundUser"      : "검색된 사용자가 없습니다!",
                            "allowMultiItem"    : "텍스트 와 셀렉트만이 멀티Row로 설정 할수 없습니다",
                            "initLinkData"      : "양식 항목의 연계여부도 미사용으로 변경됩니다?\n\n변경하시겠습니까?",
                            "requireLinkedData" : "연계할 항목을 설정해주세요!",
                            "itemPopUp"         : "폼 항목 선택",
                            "lineItemPopUp"     : "구분선 추가",
                            "muliRowPopUp"      : "멀티Row 제목 추가",
                            "categoryPopUp"     : "양식 유형",
                            "templatePopUp"     : "내 서식",
                            "histroyPopUp"      : "버전이력",
                            "reasonPopUp"       : "변경사유",
                            "linePopUp"         : "결재선 지정",
                            
                            "type"              : "타입",
                            "merged"            : "병합",
                            "required"          : "필수",
                            "hideTitle"         : "제목표시",
                            "sort"              : "정렬",
                            "vertical"          : "수직",
                            "horizontal"        : "수평",
                            "inputType"         : "입력타입",
                            "width"             : "넓이",
                            "height"            : "높이",
                            "max"               : "최대",
                            "length"            : "자",
                            "data"              : "데이터",
                            "user"              : "사용자",
                            "code"              : "코드",
                            "header"            : "해더표시",
                            "object"            : "개체",
                            "yes"               : "예",
                            "no"                : "아니요",
                            "title"             : "제목",
                            "defaultValue"      : "기본값"
                        }        
};
// iKEP apprForm Config End ------------------------------------------------------------------------


/* 생성자 */
var iFU = new(function() {
})();    

// iKEP apprForm Common Util Start -----------------------------------------------------------------
/* 기본사용자 정보 셋팅 */
iFU.setUserInfo = function(userId, localeCode, registerId){
    if(typeof(registerId)=="undefined" || registerId == "") registerId = userId;
    iFC.user.id             = userId;
    iFC.user.locale         = localeCode;
    iFC.user.registerId     = registerId;
};

/* json 정보를 select option 추가 */
iFU.jsonToSelectOption = function(obj, json, def){
    if(typeof(def)=="undefined") def = "";
    var selected = "";
    $jq.each(json, function(){
        selected = def == this.value?" selected=\"selected\"":"";
        $jq(obj).append("<option value=\"" + this.value + "\"" + selected + ">" + this.text + "</option>");
    });
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

/* json 정보에 key항목의 값이 value와 일치시 target값 리턴 */
iFU.jsonGetValueByKey = function(json, key, value, target){
    var b = "";
    $jq.each(json, function(){
        if(this[key]==value){
            b = this[target];
            return false;
        }
    });
    return b;
};

/* 예/아니요 */
iFU.yesNo = function(v){
    return v=="0"?iFC.message.no:iFC.message.yes;
};

/* 배열에 value값 존재여부 */
iFU.hasArrayValue = function(obj, value){
    if(!obj.length) return false;
    for(var i=0;i<obj.length;i++){
        if(obj[i]==value) return true;
    }
    return false;
};

/* WCEditor 문자 변환 */
iFU.convertWCData = function(str){
    return ((str.replaceAll("\"", "_quot_")).replaceAll("<", "_lt_")).replaceAll(">", "_gt_").replaceAll("&", "_amp_");
};

/* WCEditor 문자 복원 */
iFU.revokeWCData = function(str){
    return ((str.replaceAll("_quot_", "\"")).replaceAll("_lt_", "<")).replaceAll("_gt_", ">").replaceAll("_amp_", "&");
};

/* WCEditor 특수문자로 변환 */
iFU.changeWCData = function(str){
    return ((str.replaceAll("_quot_", "&quot;")).replaceAll("_lt_", "&lt;")).replaceAll("_gt_", "&gt;").replaceAll("_amp_", "&");
};
// iKEP apprForm Common Util End -------------------------------------------------------------------



// iKEP Form Management Util Start --------------------------------------------------------------
/* 결재정보 */
iFU.initializeApprInfoForm = function(form, mode) {
    
    if(mode=="new"){
        // 탭 생성
        $jq("#formTabDiv").tabs({ disabled:[1]});
    }else{
        // 탭 생성
        $jq("#formTabDiv").tabs();
       
        // 최초 양식내용 등록시
        if($jq(form).find("input[name=formVersion]").val()=="0"){
            $jq(form).find("input[name=usage]:input[value=1]").attr("disabled", true);
        }
        
        // 사용기간설정
        $jq(form).find("input[name=periodUsage]").click(function() {
            if($jq(this).val()=="1"){
                $jq("#periodUsageSpan").show();
                $jq(form).find("input[name=startDate]").rules("add", {"required" : true});
                $jq(form).find("input[name=endDate]").rules("add", {"required" : true});
            }else{
                $jq("#periodUsageSpan").hide();
                $jq(form).find("input[name=startDate]").rules("remove");
                $jq(form).find("input[name=endDate]").rules("remove");
            }
        });   
        
        // 양식 사용일
        $jq("#startDate").datepicker().next().eq(0).click(function() { $jq("#startDate").datepicker("show"); });
        $jq("#endDate").datepicker().next().eq(0).click(function() { $jq("#endDate").datepicker("show"); });
    }
    
    // 카테고리 선택 버튼
    $jq("#categoryButton").click(function(){
        iFU.searchFormCategoryPopUp();
    });
    
    // 카테고리 입력창
    $jq(form).find("input[name=formParentName]").click(function(){
        $jq("#categoryButton").click();
    });
    
    // 시스템 변경
    $jq(form).find("select[name=systemId]").change(function() {
        $jq(form).find("input[name=defLineId]").val("");
        iFU.getApprDefLine(form);
    });    
    
    // 결재유형 변경
    $jq(form).find("input[name=apprDocType]").click(function() {
        $jq(form).find("input[name=defLineId]").val("");
        iFU.getApprDefLine(form);
    });    
    
    // default 결재 Div
    $jq("#dfLineDiv").hide();
    
    // 결재선 수정여부
    $jq("#isDefLineUpdateTr").hide();
    
    // default 결재 라인 변경
    $jq(form).find("select[name=defLineSet]").change(function(){        
        if($jq(this).val()==""){
            $jq(form).find("input[name=defLineUse]").val("0");
            $jq(form).find("input[name=defLineId]").val("");
            $jq("#dfLineDiv").hide();
            $jq("#isDefLineUpdateTr").hide();
        }else{
            $jq(form).find("input[name=defLineUse]").val("1");
            $jq(form).find("input[name=defLineId]").val($jq(this).val());
            $jq("#dfLineDiv").show().empty().ajaxLoadStart("button");
            $jq("#isDefLineUpdateTr").show();
    		$jq.post(iKEP.getContextRoot()+"/approval/admin/apprDefLine/previewDefLineView.do", {defLineId:$jq(this).val()})
        	.success(function(data) {
        	    $jq("#dfLineDiv").show().html(data).ajaxLoadComplete();
        	    
        	})
	        .error(function(event, request, settings) { alert("error");$jq("#dfLineDiv").ajaxLoadComplete();});	
        }
    });
    
    iFU.getApprDefLine(form);
    
    // 담당자 정보 사용 유무
    $jq(form).find("input[name=authUse]").click(function() {
        if($jq(this).val()=="1"){
            $jq("#authUserDiv").show();
        }else{
            $jq("#authUserDiv").hide();
        }
    });   
    
    // 담당자 정보 Div
    $jq(form).find("input[name=authUse]:checked").click();
    
    // 담당자 찾기 버튼
    $jq('#authUserName').keypress( function(event) {
	    iKEP.searchUser(event, "N", iFU.setAuthUserSearch);
    });
    
    $jq("#authUserSearchButton").click(function(){
        $jq('#authUserName').trigger("keypress");
    });
    
    // 담당자 주소록 찾기 버튼
    $jq("#authUserAddressButton").click(function(){
        iKEP.showAddressBook(iFU.setAuthUserAddress, [], {selectType:"user", isAppend:false, selectMaxCnt:1, tabs:{common:1}});
    });
        
    // 담당자 삭제 버튼
    $jq("#authUserDeleteButton").click(function(){
        iFU.removeAuthUser();
    });
    
    // 참조자 찾기 버튼
    $jq(form).find("input[name=referenceName]").keypress( function(event) {
	    iKEP.searchUser(event, "Y", iFU.setReferenceSearch);
    });
    
    $jq("#referenceSearchButton").click(function(){
        $jq(form).find("input[name=referenceName]").trigger("keypress");
    });
    
    // 참조자 주소록 찾기 버튼
    $jq("#referenceAddressButton").click(function(){
        var items = [];
	    $jq(form).find("select[name=referenceSet]").children().each(function() {
		    items.push($jq(this).data("data"));
	    });
        iKEP.showAddressBook(iFU.setReferenceAddress, items, {selectType:"user", isAppend:false, selectMaxCnt:100, tabs:{common:1}});
    });
    
    // 참조자 삭제 버튼
    $jq("#referenceDeleteButton").click(function(){
        $jq(form).find("select[name=referenceSet] option:selected").remove();
        iFU.setSelectOptionCount("referenceSet", "#referenceSetSpan");
    });
    
    // 버튼영역 실행
	$jq("#formButtonDiv a").click(function(){
	    switch (this.id) {
            case "saveButton":
			    $jq(form).trigger("submit");
			    break;
            case "listButton":
                if($jq("#listForm").length>0){
                    $jq("body").ajaxLoadStart("button");
                    $jq("#listForm").submit();
                }else{
                    location.href = "listApprForm.do";
                }
                break;
            default:
                break;
        }   
    });
    
};

/* 양식내용 */
iFU.initializeApprContentForm = function(form) {
    // 최초 양식내용 등록시
    if($jq(form).find("input[name=formVersion]").val()=="0"){
        $jq(form).find("input[name=isNewVersion]:input[value=1]").attr("checked", true);
        $jq(form).find("input[name=isNewVersion]:input[value=0]").attr("disabled", true);
        $jq(form).find("input[name=isApprEditor]:input[value=1]").attr("checked", true);
    }else{
        $jq(form).find("input[name=isNewVersion]:input[value=0]").attr("checked", true);
    }

    // 연계사용여부
    $jq(form).find("input[name=isLinkUrl]").click(function() {
        if($jq(this).val()=="1"){
            $jq(form).find("input[name=linkUrl]").rules("add", {"required" : true});
            $jq(".isLinkUrl").css("border-right-color", "#e0e0e0");
            $jq(".isLinkUrl0").hide();
            $jq(".isLinkUrl1").show();
            $jq(form).find("input[name=linkUrl]")[0].focus();
            $jq(form).find("input[name=linkDataType]:checked").click();
        }else{
            // 연계 데이터가 JSON시
            if($jq(form).find("input[name=linkDataType]:checked").val()=="1"){
                iFU.initLinkData();
            }
            
            $jq(form).find("input[name=linkUrl]").rules("remove");
            $jq(".isLinkUrl").css("border-right-color", "#ffffff");
            $jq(".isLinkUrl1").hide();
            $jq(".isLinkUrl0").show();
            $jq("#rowPerItemDiv  a:eq(5)").hide();
        }
    });   
    
    // 연계데이터타입
    $jq(form).find("input[name=linkDataType]").click(function() {
        if($jq(this).val()=="0"){
            iFU.initLinkData("linkDataType");
        }else{
            $jq("#rowPerItemDiv  a:eq(5)").show();
        }
    });
    
    // tab
    $jq("#layoutDiv").tabs({selected : 0, select : function(event, ui){return iFU.checkPreviwer(ui.index);}});
    
    // CK Edit Init
    fullCkeditorConfig.height="300";
    $jq("#formEditorData").ckeditor($jq.extend(fullCkeditorConfig, {"language" : iFC.user.locale, "popupTitle" : "editor" }));

    // rowActionDiv 처리
    $jq("#rowActionDiv").mouseover(function(){
        $jq(this).show();
    }).mouseout(function(){
        $jq(this).hide();
    });
    
    // itemActionDiv 처리
    $jq("#itemActionDiv").mouseover(function(){
        $jq(this).show();
        iFC.object.overActionItem = true;
    }).mouseout(function(){
        $jq(this).hide();
        iFC.object.overActionItem = false;
    });
    
    // drag N drop
    $jq("#formLayoutDiv").sortable({
        update: function(event, ui) {iFU.checkAutoPreviwer();}
    });
    
    // 항목추가 버튼
	$jq.each(iFC.itemCount, function(key){
	    var json = {"len" : this.value, "num" : key};
	    if(key=="line") json.icon = "line";
	    else json.icon = "item" + this.value;
	    
	    $jq("div.appr_formlayoutbtn").append($jq.tmpl(iFC.template.itemButton, json));
    });  
    
    // 아이콘 설명
	$jq.each(iFC.item, function(key){
	    $jq("#itemIconDiv").append($jq.tmpl(iFC.template.itemIcon, {"name" : this.name, "icon" : this.icon}));
    });  
    
    // 미리보기, 초기화
    //$jq("#rowPerItemDiv").append($jq.tmpl(iFC.template.smallButton, {"name" : "미리보기", "func" : "iFU.previewDocLayout('0');"}));
    $jq("#rowPerItemDiv").append($jq.tmpl(iFC.template.smallButton, {"name" : iFC.message.preview, "func" : "iFU.checkAutoPreviwer(true);"}));
    $jq("#rowPerItemDiv").append($jq.tmpl(iFC.template.smallButton, {"name" : iFC.message.preview+"(" + iFC.message.newWindow + ")", "func" : "iFU.previewDocLayout('1');"}));
    $jq("#rowPerItemDiv").append($jq.tmpl(iFC.template.smallButton, {"name" : iFC.message.initialize, "func" : "iFU.initFormLayout();"}));
    $jq("#rowPerItemDiv").append($jq.tmpl(iFC.template.smallButton, {"name" : iFC.message.template, "func" : "iFU.listApprFormDocTemplatePopUp(0);"}));
    $jq("#rowPerItemDiv").append($jq.tmpl(iFC.template.smallButton, {"name" : iFC.message.history, "func" : "iFU.listApprFormHistoryPopup(" + $jq(form).find("input[name=formId]").val() + ");"}));
    $jq("#rowPerItemDiv").append($jq.tmpl(iFC.template.smallButton, {"name" : iFC.message.linkedJson, "func" : "iFU.makeFormLinkedData();"}));
    $jq("#rowPerItemDiv").append($jq.tmpl(iFC.template.smallButton, {"name" : iFC.message.config, "func" : "iFU.setFormLayoutConfigPopUp();"}));
    
    // 기양식 생성
    if($jq(form).find("textarea[name=formLayoutData]").val()!=""){
        iFU.formLayoutDisplay("show");
        iFU.makeFormLayout(form);
    }else{
        iFU.formLayoutDisplay("hide");
    }
    
    // 서식
	$jq("#listApprFormDocTemplateButton").click(function(){
	    iFU.listApprFormDocTemplatePopUp(1);
    });
    
    // 버튼영역 실행
	$jq("#formContentButtonDiv a").click(function(){
	    switch (this.id) {
            case "saveContentButton":
                if(!iFU.isLayoutFormItemDiv()) return;
                var layout = iFU.itemToLayoutFormData();
                
                if(layout.data.length==0) $jq(form).find("[name=formLayoutData]").val("");
                else $jq(form).find("[name=formLayoutData]").val(JSON.stringify(layout));
                
			    $jq(form).trigger("submit");
			    break;
            case "listContentButton":
                $jq("body").ajaxLoadStart("button");
                $jq("#listForm").submit();
                break;
            default:
                break;
        }   
    });
};

/* 연계JONS데이터 초기화 여부 */
iFU.initLinkData = function(flag){
    if(iFU.hasLinkedFormItemDiv()){
        if(confirm(iFC.message.initLinkData)){
            iFU.changeItemJsonLinked();
            $jq("#rowPerItemDiv  a:eq(5)").hide(); //연계JSON생성 버튼 hide
            $jq("#apprContentForm input[name=linkDataType]:input[value=0]").attr("checked", true);
        }else{
            if(typeof(flag)=="undefined")
                $jq("#apprContentForm input[name=isLinkUrl]:input[value=1]").attr("checked", true);
            else
                $jq("#apprContentForm input[name=linkDataType]:input[value=1]").attr("checked", true);
                
            return;
        }
    }else{
        $jq("#rowPerItemDiv  a:eq(5)").hide(); //연계JSON생성 버튼 hide
    }
};


/* iframe 미리보기 */
iFU.checkPreviwer = function(index){
    if(index==1){
        if(!iFU.isLayoutFormItemDiv()) {            
            return false;
        }else{
            // refresh
            $jq("#previewerA").ajaxLoadStart("button");
            $jq("#apprPreviewForm").attr("target", "previwerIframe");
            $jq("#apprPreviewForm input[name=mode]").val("2");
            $jq("#apprPreviewForm input[name=formLayoutData]").val(JSON.stringify(iFU.itemToLayoutFormData()));
            $jq("#apprPreviewForm").attr("action", iKEP.getContextRoot() + "/approval/admin/apprAdminDoc/createApprDocLayout.do");
            $jq("#apprPreviewForm").submit();
        }
    }
    return true;
};

/* iframe 미리보기 */
iFU.checkAutoPreviwer = function(b){
    // empty
    if(!iFU.hasLayoutFormFloatDiv()){
        if(iFC.object.popup!=null && iFC.object.popup && !iFC.object.popup.closed){
            iFC.object.popup.close();
        }
        $jq("#autoViewerA").hide();
        return;
    }

    // 버튼 클릭시 b=true;
    if(typeof(b)=="undefined") b = false;
    
    // 팝업창 새로고침
    if(iFC.object.popup!=null && !b && iFC.object.popup && !iFC.object.popup.closed){
    	iFU.previewDocLayout('1');
    }
    
    if(b){
        // 미리보기 숨김
        if($jq("#autoViewerA").css("display")!="none") {
            $jq("#autoViewerA").hide();
            return;
        }
    }else{
        // 미리보기 숨김
        if($jq("#autoViewerA").css("display")=="none") return;
    }
    
    if(!iFU.isLayoutFormItemDiv(b)) return false;
    if($jq("#autoViewerA").css("display")=="none") $jq("#autoViewerA").show();
    
    $jq("#autoViewerA").ajaxLoadStart("button");    
    $jq("#apprPreviewForm").attr("target", "autoPreviwerIframe");
    $jq("#apprPreviewForm input[name=mode]").val("2");
    $jq("#apprPreviewForm input[name=formLayoutData]").val(JSON.stringify(iFU.itemToLayoutFormData()));
    $jq("#apprPreviewForm").attr("action", iKEP.getContextRoot() + "/approval/admin/apprAdminDoc/createApprDocLayout.do");
    $jq("#apprPreviewForm").submit();
        
};

/* formLayout 생성 */
iFU.makeFormLayout = function(form){
    var config = {};
    
    //- 기존 item 셋팅
    if($jq(form).find("textarea[name=formLayoutData]").val()!=""){
        iFC.object.formLayoutData = $jq.parseJSON($jq(form).find("textarea[name=formLayoutData]").val());
        
        //- layout config 
        if(iFC.object.formLayoutData.config){
            config = iFC.object.formLayoutData.config;
        }
        
        //- layout 생성
        $jq.each(iFC.object.formLayoutData.data, function(){
            iFU.addRow(this.items.length, this.type, this);
        });
        
        //- sequence 셋팅
        $jq.each(iFC.object.formLayoutData.itemSeq, function(){
            iFC.item[this.id].seq = this.seq;
        });
    }
    
    // layout 설정정보 저장
    $jq("#formLayoutDiv").data("config", config);
};

/* ck init */
iFU.ckApprFormResize= function(h){
    var editor = $jq("#formEditorData").ckeditorGet();
    editor.resize("100%", h, true );
};

/* 미리보기 */
iFU.previewDocLayout = function(mode){
    if(!iFU.isLayoutFormItemDiv()) return;
    iFU.createApprDocLayoutPopUp(iFU.itemToLayoutFormData(), $jq("#formEditorData").ckeditorGet().getData(), mode);
};

/* 초기화 */
iFU.initFormLayout = function(){
    if(confirm(iFC.message.initFormLayout)){
        $jq("#formLayoutDiv").empty();
		if($jq("#apprContentForm textarea[name=formLayoutDataTemp]").val()==""){
		    
		    iFU.formLayoutDisplay("hide");
            $jq.each(iFC.item, function(){
                this.seq = 0;
            });        
        }else{
            $jq("#apprContentForm textarea[name=formLayoutData]").val($jq("#apprContentForm textarea[name=formLayoutDataTemp]").val());
		    iFU.makeFormLayout("#apprContentForm");		        
        }
        
        $jq("#apprContentForm")[0].reset();
        
        // 연계정보
        if($jq("#apprContentForm input[name=isLinkUrl]:checked").val()=="1"){
            $jq(".isLinkUrl").css("border-right-color", "#e0e0e0");
            $jq(".isLinkUrl0").hide();
            $jq(".isLinkUrl1").show();
        }else{
            $jq("#apprContentForm input[name=isLinkUrl]:checked").click();
        }
        
        iFU.changeMakerTab();
        iFU.checkAutoPreviwer();
    }
};

/* 디폴트 결재라인 */
iFU.getApprDefLine = function(form) {
    var defLineType = $jq(form).find("input[name=apprDocType]:checked").val();
    var defLineId   = $jq(form).find("input[name=defLineId]").val();
    var systemId    = $jq(form).find("select[name=systemId]").val();
    
    $jq.get(iKEP.getContextRoot()+ "/approval/admin/apprDefLine/getApprDefLine.do",{"defLineType":defLineType, "systemId":systemId},
		function(data) {
			$jq("#defLineSet option:eq(0)").nextAll().remove();	
			$jq.each(data, function() {	
				$jq("<option/>").attr("value",this.defLineId).text(this.defLineName).appendTo("#defLineSet");
			});
			
            $jq("#defLineSet > option[value='" + defLineId + "']").attr("selected",true);
            $jq(form).find("select[name=defLineSet]").change();
            $jq(form).find("select[name=defLineSet] option:odd").css("background", "#f9f9f9");
		}
	);

	return false; 
};
		
/* 담당자 정보 삭제 */
iFU.removeAuthUser = function() {
    if($jq("input[name=authUserId]").val()!=""){
        if(confirm(iFC.message.removeAuthUser)){
            $jq("#apprInfoForm input[name=authUserId]").val("");
            $jq("#apprInfoForm input[name=authUserName]").val("");
        }
    }
};

/* 담당자 정보 추가 by 주소록 */
iFU.setAuthUserAddress = function(data){
    $jq.each(data, function() {
        $jq("#apprInfoForm input[name=authUserName]").val(this.name);
        $jq("#apprInfoForm input[name=authUserId]").val(this.id);
    });
};

/* 담당자 정보 추가 by 검색 */
iFU.setAuthUserSearch = function(data) {
    if(data == "") {
		alert(iFC.message.notFoundUser);
        $jq("#apprInfoForm input[name=authUserName]").val("");
		return;
    }   
    
    $jq(data).each(function(index) {
	   $jq("#apprInfoForm input[name=authUserName]").val(data[index].userName + " " + data[index].jobTitleName);
       $jq("#apprInfoForm input[name=authUserId]").val(data[index].id);
    });
};

/* 수신참조 정보 추가 by 검색 */
iFU.setReferenceSearch = function(data) {
	if(data == "") {
		alert(iFC.message.notFoundUser);
		 $jq("#apprInfoForm input[name=referenceName]").val("");
		return;
    }   
	var $select = $jq("#apprInfoForm select[name=referenceSet]");
	$jq(data).each(function(index) {
	    if(!iFU.hasSelectOptionValue("referenceSet", data[index].id)){
		    $jq.tmpl(iKEP.template.userOption, this).appendTo($select).data("data", this);
		}
	});	
	iFU.setSelectOptionCount("referenceSet", "#referenceSetSpan");
};

/* 수신참조 정보 추가 by 주소록 */
iFU.setReferenceAddress = function(data) {   
	var $jqsel = $jq("#apprInfoForm select[name=referenceSet]").children().remove().end();
	$jq.each(data, function() {
		$jq.tmpl(iKEP.template.userOption, this).appendTo($jqsel).data("data", this);
    });
    
    if($jq("#referenceSetSpan").length>0){
        iFU.setSelectOptionCount("referenceSet", "#referenceSetSpan");
    }
};

/* select option을 스트링으로 변환 */
iFU.setReferenceSet = function(form) {
    var result="";
    $jq(form).find("select[name=referenceSet] option").each(function() {
        if(result!="") result += ";";
        result += $jq(this).val() + "," + $jq(this).data("data").group;
    });
    
    $jq(form).find("input[name=referenceId]").val(result);
};
// iKEP Form Management Util Start --------------------------------------------------------------



// iKEP Form Layout Wizard Util Start -----------------------------------------------------------
/* item을 포함한 Div 여부 */
iFU.isLayoutFormItemDiv = function(warning) {
    if(typeof(warning)=="undefined") warning = true;
    
    var process   = true;
    $jq("#formLayoutDiv div[class$='-float']").each(function() {
        if($jq(this).find("textarea").length==0){
            if(warning) $jq(this).css("background-color", iFC.style.errColor);
            process = false;
            return false;
        }
    }); 
    
    return process;
};

/* float Div 가지고 있는지 여부 */
iFU.hasLayoutFormFloatDiv = function() {
    return $jq("#formLayoutDiv div[class$='-float']").length>0;
};

/* 연계 설정 여부 */
iFU.hasLinkedFormItemDiv = function() {
    var process   = false;
    $jq("#formLayoutDiv div[class$='-float']").each(function() {
        if($jq(this).data("background") && $jq(this).data("background")==iFC.style.linkColor){
            process = true;
            return false;
        }
    }); 
    
    return process;
};

/* 연계여부 초기화 */
iFU.changeItemJsonLinked = function() {
    var item;
    $jq("#formLayoutDiv div[class$='-float']").each(function() {
        if($jq(this).data("background") && $jq(this).data("background")==iFC.style.linkColor){
            item = $jq.parseJSON($jq(this).find("textarea").val());
            item.linked = "0";
            $jq(this).find("textarea").val(JSON.stringify(item));
            $jq(this).css("background-color", iFC.style.hasColor);
            $jq(this).data("background", iFC.style.hasColor);
        }
    }); 
};

/* 연계데이터 생성 */
iFU.makeFormLinkedData = function(){
    // 연계 item이 없을시
    if(!iFU.hasLinkedFormItemDiv()){
        alert(iFC.message.requireLinkedData);
        return;
    }
    
    var formLinkedData = {"formId" : $jq("#apprContentForm input[name=formId]").val(), "data":[]};
    
    // 연계 JSON생성
    var item;
    $jq("#formLayoutDiv div[class$='-float']").each(function() {
        if($jq(this).data("background") && $jq(this).data("background")==iFC.style.linkColor){
            item = $jq.parseJSON($jq(this).find("textarea").val());
            var json = {"type" : item.type, "name" : item.name};
            
            if(json.type=="textarea") {
                json.value = item.template;
            } else if(json.type=="textbox") {
                json.value = iFU.changeWCData(item.html);    
            } else if(json.type=="select" || json.type=="radio" || json.type=="checkbox"){
                json.value = item.options; 
                
                if(item.defaultValue){
                    json.defaultValue = item.defaultValue;
                }
            } else {
                json.value = "";
            }    
            formLinkedData.data.push(json);
        }
    }); 
    
    // jsonViewer check
    if(iFC.object.jsonViewer != null) iFC.object.jsonViewer.close();
	iFC.object.jsonViewer = window.open("about:blank", "jsonViewer");

    // make new window
	var s="<title>PREVIEW</title>\r\n";
	s=s+"<link rel=\"stylesheet\" type=\"text/css\" media=\"screen\" href=\"/ikep4-webapp/base/css/common.css\" />\r\n";
	s=s+"<table border=0 cellspacing=0 cellpadding=0 width=100% height=100%><tr><td valign=top style='border:1px solid #aaa'>\r\n";
	s=s+"<table border=0 cellspacing=0 cellpadding=0 width=95% style=padding-left:20px;><tr><td>&nbsp;</td></tr><tr height=30><td><a href='http://jsonviewer.stack.hu/' target='_blank'><b>JSON Viewer</b> 로 데이터 구조 보기</a></td></tr><tr><td valign=top><br>\r\n";
	s=s+JSON.stringify(formLinkedData);
	s=s+"</td></tr></table></td></tr></table>\r\n";

	iFC.object.jsonViewer.document.open();
	iFC.object.jsonViewer.document.write(s);
	iFC.object.jsonViewer.document.close();    
};

/* layout JSON 생성 */
iFU.itemToLayoutFormData = function() {
    var layout  = { formId  : $jq("#apprContentForm input[name=formId]").val(),
                    title   : $jq("#apprInfoForm input[name=formName]").val(),
                    config  : $jq("#formLayoutDiv").data("config"),
                    itemSeq : [],
                    data    : []
                  };
    
    // sequence 생성
    $jq.each(iFC.item, function(key){
        layout.itemSeq.push({"id" : key, "seq" : this.seq});
    }); 
    
    var n=0, className="";
    $jq("#formLayoutDiv div").each(function() {
        
        // 레이아웃 클래스
        className = iFU.getLayoutClass(this);
        
        if(className!=""){
            // multiRow
            if($jq(this).hasClass("multiRow")){
                layout.data[n] = {"type" : className, "multi" : $jq(this).data("data"), "items" :[]};
            }else{
                layout.data[n] = {"type" : className, "items" :[]};
            }
            
            // items
            $jq(this).children().each(function() {
                if($jq(this).find("textarea").length>0) layout.data[n].items.push($jq.parseJSON($jq(this).find("textarea").val()));
            }); 
            
            n++;
        }           
    }); 
    
    return layout;
};

/* layout item class를 반환 */
iFU.getLayoutClass = function(obj) {
    var v = "";
    $jq.each(iFC.itemCount, function(key){
        if($jq(obj).hasClass(key)) {
            v=key;
            return false;
        }
    });
    return v;
};

/* formLayout mode */
iFU.formLayoutDisplay = function(v){
    if(v=="show"){
        $jq("#formLayoutDiv").show();
        $jq("#emptyLayoutDiv").hide();
        $jq("#layoutDiv").tabs("enable", 1);   //미리보기 탭 disable
        $jq("#rowPerItemDiv  a:lt(3)").show();
        $jq("#rowPerItemDiv  a:eq(6)").show(); //양식설정
    }else if(v=="hide"){
        $jq("#formLayoutDiv").hide();
        $jq("#emptyLayoutDiv").show();
        $jq("#layoutDiv").tabs("disable", 1);  //미리보기 탭 enable
        $jq("#rowPerItemDiv  a:lt(2)").hide();
        
        if($jq.trim($jq("#apprContentForm textarea[name=formLayoutDataTemp]").val())==""){
            $jq("#rowPerItemDiv  a:eq(2)").hide(); // 초기화버튼
        }
        
        $jq("#rowPerItemDiv  a:eq(6)").hide(); //양식설정
    }
};

/* items row 추가 */
iFU.addRow = function(len, num, json){
    iFU.formLayoutDisplay("show");
    
    //- Class Div 생성
    var o = document.createElement("div");
    
    // 선택 메시지
    var msg;
    if(num=="line"){
        msg = "lineDiv";
    }else{
        msg = "itemDiv";
        
        //- multiRow
        if(typeof(json)!="undefined" && json.multi && json.multi.count){
            $jq(o).addClass("multiRow");
            $jq(o).data("data", json.multi);
        }else{
            $jq(o).addClass("noMultiRow");
        }
    }
                    
    $jq(o).mouseover(function(){ // row over
        if(iFC.object.overActionItem) return;
        
        $jq("#rowActionMultiLi").show();
        $jq("#rowActionNoMultiLi").show();
        $jq("#rowActionResizeLi").show();
        $jq("#rowActionAddItemLayoutLi").show();
        $jq("#rowActionUpRowLi").hide();
        $jq("#rowActionDownRowLi").hide();
        
        if(!$jq(o).hasClass("noMultiRow")) $jq("#rowActionMultiLi").hide();
        if(!$jq(o).hasClass("multiRow")) $jq("#rowActionNoMultiLi").hide();
        if($jq(o).hasClass("line")) {
            $jq("#rowActionAddItemLayoutLi").hide();
            $jq("#rowActionResizeLi").hide();
        }
        if($jq(o).hasClass("six")) $jq("#rowActionAddItemLayoutLi").hide();
        
        if($jq(o).prevAll().size()>0) $jq("#rowActionUpRowLi").show();
        if($jq(o).nextAll().size()>0) $jq("#rowActionDownRowLi").show();
        
        $jq("#rowActionDiv").css("top", ($jq(this).position().top+10) + "px").css("left", $jq(this).position().left + ($jq(this).width() + 0) + "px").show();
        iFC.object.actionRow = this;
    }).mouseout(function(){ // row out
        $jq("#rowActionDiv").hide();
    });
    
    for(var i=1;i<=len;i++){
        //- Item Div 생성
        var c = document.createElement("div");
        $jq(c).click(function(){
            iFC.object.editItem = this;
            if(num=="line"){
                iFU.viewApprFormLineItemPopUp();
            }else{
                iFU.viewApprFormItemMainPopUp();
            }
        }).mouseover(function(){
            if(iFC.object.overActionItem) return;
            
            $jq("#itemActionInfoItemLi").hide();
            $jq("#itemActionLeftItemLi").hide();
            $jq("#itemActionRightItemLi").hide();
            
            // default
            var _width = 6;
            
            // inform icon
            if($jq(this).find("textarea").length>0){
                $jq("#itemActionInfoItemLi").show();
                _width = _width+13;
            }
            
            // left icon
            if($jq(this).prevAll().size()>0) {
                $jq("#itemActionLeftItemLi").show();
                _width = _width+13;
            }
            
            // right icon
            if($jq(this).nextAll().size()>1) {
                $jq("#itemActionRightItemLi").show();
                _width = _width+13;
            }
            
            $jq(this).css("background-color", iFC.style.overColor);
            $jq("#itemActionDiv").css("top", ($jq(this).position().top+0) + "px").css("left", $jq(this).position().left + ($jq(this).width() - _width) + "px").show();
            iFC.object.actionItem = this;
        }).mouseout(function(){
            if(iFC.object.overActionItem) return;
            if($jq(this).find("textarea").length==0) {
                $jq(this).css("background-color", "");
            } else {
                $jq(this).css("background-color", $jq(this).data("background"));
            }
            
            $jq("#itemActionDiv").hide();
        });
        
        if(typeof(json)=="undefined"){ // new column
            $jq(o).append($jq(c).addClass(num + "-float").html("<p><div class=\"ellipsis\" style=\"text-align:center\">" + iFC.message[msg] + "</div></p>"));
        }else{ // edit column
            var backcolor = json.items[i-1].linked && json.items[i-1].linked=="1"?iFC.style.linkColor:iFC.style.hasColor;
            $jq(o).append($jq(c).data("background", backcolor).addClass(num + "-float").css("background-color", backcolor).html(iFU.getPreviewHtml(json.items[i-1])));
        }
        
        // div clear
        if(i==len) $jq(o).append(iFC.html.divClear);
    }
    
    $jq("#formLayoutDiv").append($jq(o).addClass(num));
    iFU.changeMakerTab();
};

/* multiRow 여부 판별후 설정 팝업 호출 */
iFU.toMultiRow = function(){
    var process = true;
    $jq(iFC.object.actionRow).children("div[class$='-float']").each(function() {
        if($jq(this).find("textarea").length!=0){
            var json = $jq.parseJSON($jq(this).children("textarea").val());
            
            if(!iFU.hasArrayValue(iFC.allowMultiItem, json.type)){
                alert(iFC.message.allowMultiItem);
                process = false;
                return false;
            }        
        }else{
            $jq(this).css("background-color", iFC.style.errColor);
            process = false;
            return false;
        }
    }); 
    
    if(!process) return;
    
    iFU.viewApprFormMultiRowItemPopUp();
    
};

/* item 추가 */
iFU.newItem = function(){
    var currentclass = iFU.getLayoutClass(iFC.object.actionRow); // 추가전 class
    var nextClass    = iFC.itemCount[currentclass].next; // 추가후 class
    if(nextClass=="N/A") return;
    
    $jq(iFC.object.actionRow).removeClass(currentclass);
    $jq(iFC.object.actionRow).addClass(nextClass);
    
    $jq(iFC.object.actionRow).children().each(function(){
		if($jq(this).hasClass(currentclass+"-float")){
		    $jq(this).removeClass(currentclass+"-float");
            $jq(this).addClass(nextClass+"-float");
        }
	});
    
    // column 생성
    var c = document.createElement("div");
    $jq(c).click(function(){
        iFC.object.editItem = $jq(this);
        iFU.viewApprFormItemMainPopUp();
    }).mouseover(function(){
        if(iFC.object.overActionItem) return;
        
        $jq("#itemActionInfoItemLi").hide();
        $jq("#itemActionLeftItemLi").hide();
        $jq("#itemActionRightItemLi").hide();
            
         var _width = 6;
            
        if(typeof(json)!="undefined"){
            $jq("#itemActionInfoItemLi").show();
            _width = _width+13;
        }
        
        if($jq(this).prevAll().size()>0) {
            $jq("#itemActionLeftItemLi").show();
            _width = _width+13;
        }
        
        if($jq(this).nextAll().size()>1) {
            $jq("#itemActionRightItemLi").show();
            _width = _width+13;
        }
        
        $jq(this).css("background-color", iFC.style.overColor);
        $jq("#itemActionDiv").css("left", $jq(this).position().left + ($jq(this).width() - _width) + "px").show();
        iFC.object.actionItem = this;
    }).mouseout(function(){
        if(iFC.object.overActionItem) return;
        if($jq(this).find("textarea").length==0) $jq(this).css("background-color", "");
        else $jq(this).css("background-color", $jq(this).data("background"));
    });
    
    $jq(iFC.object.actionRow).children("div.clear").before($jq(c).addClass(nextClass + "-float").html("<p>" + iFC.message.itemDiv + "</p>"));
};

/* item 삭제 */
iFU.removeItem = function(){
    $jq("#itemActionDiv").hide();
    var p = $jq(iFC.object.actionItem).parent();
    
    var currentclass = iFU.getLayoutClass(p);
    var prevClass    = iFC.itemCount[currentclass].prev;
    
    // previous item이 없을시 row 삭제
    if(prevClass=="N/A") {
        if(p.parent().children().length==1) iFU.formLayoutDisplay("hide");
        $jq(p).remove();
        return;
    }
    
    $jq(iFC.object.actionRow).removeClass(currentclass);
    $jq(iFC.object.actionRow).addClass(prevClass);
    
    $jq(p).children().each(function(){
		if($jq(this).hasClass(currentclass+"-float")){
		    $jq(this).removeClass(currentclass+"-float");
            $jq(this).addClass(prevClass+"-float");
        }
	});
    
    $jq(iFC.object.actionItem).remove();
    iFU.checkAutoPreviwer();
};

/* item left */
iFU.leftItem = function(){
    if($jq(iFC.object.actionItem).prevAll().size()>0){
        $jq(iFC.object.actionItem).insertBefore($jq(iFC.object.actionItem).first().prev());
        $jq("#itemActionDiv").hide();
        iFU.checkAutoPreviwer();
    }
};

/* item right */
iFU.rightItem = function(){
    if($jq(iFC.object.actionItem).nextAll().size()>0){
        $jq(iFC.object.actionItem).insertAfter($jq(iFC.object.actionItem).last().next());
        $jq("#itemActionDiv").hide();
        iFU.checkAutoPreviwer();
    }
};

/* multiRow 설정 */
iFU.setMultiRow = function(json){
    $jq(iFC.object.actionRow).removeClass("noMultiRow");
    $jq(iFC.object.actionRow).addClass("multiRow");
    $jq(iFC.object.actionRow).data("data", json);
    iFU.checkAutoPreviwer();
};

/* multiRow 해제 */
iFU.toNoMultiRow = function(){
    $jq(iFC.object.actionRow).removeClass("multiRow");
    $jq(iFC.object.actionRow).addClass("noMultiRow");
    iFU.checkAutoPreviwer();
};

/* row 삭제 */
iFU.removeRow = function(){
    if($jq(iFC.object.actionRow).parent().children().length==1) iFU.formLayoutDisplay("hide");
    $jq("#rowActionDiv").hide();
    $jq(iFC.object.actionRow).remove();
    iFU.checkAutoPreviwer();
};

/* row up */
iFU.upRow = function(){
    if($jq(iFC.object.actionRow).prevAll().size()>0){
        $jq(iFC.object.actionRow).insertBefore($jq(iFC.object.actionRow).first().prev());
        $jq("#rowActionDiv").hide();
    }
    iFU.checkAutoPreviwer();
};

/* row down */
iFU.downRow = function(){
    if($jq(iFC.object.actionRow).nextAll().size()>0){
        $jq(iFC.object.actionRow).insertAfter($jq(iFC.object.actionRow).last().next());
        $jq("#rowActionDiv").hide();
    }
    iFU.checkAutoPreviwer();
};

/* item 추가 */
iFU.addItem = function(item){    
    var backcolor = item.linked && item.linked=="1"?iFC.style.linkColor:iFC.style.hasColor;
    $jq(iFC.object.editItem).data("background", backcolor).css("background-color", backcolor).html(iFU.getPreviewHtml(item));
    iFU.checkAutoPreviwer();
};    

/* item영역에 item json 추가 */
iFU.getPreviewHtml = function(json) {
    if(json.name=="") {
        json.name  = iFU.getItemName(json.type);
    }
    
    var html = "<textarea name=\"" + json.name  + "JsonData\" style=\"display:none;\">" + JSON.stringify(json) + "</textarea>";
    return html += "<p><div class=\"ellipsis\" style=\"text-align:center;\">&nbsp;<img style=\"vertical-align:top;\" src=\"/ikep4-webapp/base/images/icon/" + iFC.item[json.type].icon + "\" /> " + json.title + "</div></p>";
};

/* 중복되지 않게 item 이름 생성 */
iFU.getItemName = function(type) {
    iFC.item[type].seq++;
    return type + iFC.item[type].seq;
};

/* 양식항목작성탭 이동 */
iFU.changeMakerTab = function(){
    if($jq("#layoutDiv").tabs("option", "selected")!=0){
        $jq("#layoutDiv").tabs({selected : 0});
    }
};  
// iKEP Form Layout Wizard Util End -------------------------------------------------------------


// iKEP Form PopUp Util Start ------------------------------------------------------------------
/* form item 선택 */
iFU.viewApprFormItemMainPopUp = function(){
    var merged = $jq(iFC.object.editItem).prevAll().size()>0 ? true:false;
    var linked = $jq("#apprContentForm input[name=isLinkUrl]:checked").val()=="1" && $jq("#apprContentForm input[name=linkDataType]:checked").val()=="1" ? true:false;
    var mode, json, multi;
    var _height = 600;
    if(merged) _height += 26;
    if(linked) _height += 26;
    
    if($jq(iFC.object.editItem).find("textarea").length==0){
        mode = "new";
        json = {};
    }else{
        mode = "edit";
        json = $jq.parseJSON($jq(iFC.object.editItem).children("textarea").val());
    }
    
    multi = $jq(iFC.object.editItem).parent().hasClass("multiRow");
    
    iKEP.showDialog({     
		title 		: iFC.message.itemPopUp,
		url 		: iKEP.getContextRoot() + "/approval/admin/apprAdminForm/viewApprFormItemMain.do",
		modal 		: true,
		width 		: 650,
		height 		: _height,
		params 		: {"mode" : mode, "merged" : merged, "json" : json, "multi" : multi, "linked" : linked},
		callback	: function(result) {
		    iFU.addItem(result);
		}
	});
};

/* 양식 미리보기 */
iFU.createApprDocLayoutPopUp = function(formLayoutData, formEditorData, mode){
    if(mode=="0"){
        iKEP.showDialog({     
    		title 		: formLayoutData.title,
    		url 		: iKEP.getContextRoot() + "/approval/admin/apprAdminDoc/createApprDocLayout.do?mode=" + mode,
    		modal 		: true,
    		width 		: 900,
    		height 		: 600,
    		params 		: {"formLayoutData" : formLayoutData, "formEditorData" : formEditorData},
    		callback	: function(result) {}
    	});
    }else if(mode=="1"){
        var url = iKEP.getContextRoot() + "/approval/admin/apprAdminDoc/createApprDocLayout.do?mode=" + mode;
	    var options = {
    		windowTitle : formLayoutData.title,
    		documentTitle : formLayoutData.title,
    		width:880, height:600, modal:true,
    		argument:{"formLayoutData" : formLayoutData, "formEditorData" : formEditorData},
    		callback : function(result) {
    			iKEP.debug(result);
    		}
    	};
    	iFC.object.popup = iKEP.portletPopupOpen(url, options, "popupViewer");
    	//iKEP.portletPopupOpen(url, options, "popupViewer");
    }
};

/* item line 타이틀 수정 */
iFU.viewApprFormLineItemPopUp = function(){
    var mode, json;
    if($jq(iFC.object.editItem).find("textarea").length==0){
        mode = "new";
        json = {};
    }else{
        mode = "edit";
        json = $jq.parseJSON($jq(iFC.object.editItem).children("textarea").val());
    }
    iKEP.showDialog({     
		title 		: iFC.message.lineItemPopUp,
    	url 		: iKEP.getContextRoot() + "/approval/admin/apprAdminForm/viewApprFormItem.do?itemId=line",
    	modal 		: true,
    	width 		: 400,
    	height 		: 210,
    	params 		: {"mode" : mode, "json" : json},
    	callback	: function(result) {
		    iFU.addItem(result);
		}
	});
};

/* configration */
iFU.setFormLayoutConfigPopUp = function(){
    if(!iFU.isLayoutFormItemDiv()) return;
    
    iKEP.showDialog({     
		title 		: iFC.message.config,
    	url 		: iKEP.getContextRoot() + "/approval/admin/apprAdminForm/viewApprFormItem.do?itemId=config",
    	modal 		: true,
    	width 		: 400,
    	height 		: 200,
    	params 		: $jq("#formLayoutDiv").data("config"),
    	callback	: function(result) {
		    $jq("#formLayoutDiv").data("config", result);
		    iFU.checkAutoPreviwer();
		}
	});
};

/* multiRow 타이틀 */
iFU.viewApprFormMultiRowItemPopUp = function(){
    var title="", count="1", width="";
    
    if($jq(iFC.object.actionRow).data("data")){
        title = $jq(iFC.object.actionRow).data("data").title;
        count = $jq(iFC.object.actionRow).data("data").count;
        if($jq(iFC.object.actionRow).data("data").width){
            width = $jq(iFC.object.actionRow).data("data").width;
        }
    }
    
    iKEP.showDialog({     
		title 		: iFC.message.muliRowPopUp,
    	url 		: iKEP.getContextRoot() + "/approval/admin/apprAdminForm/viewApprFormItem.do?itemId=multiRow",
    	modal 		: true,
    	width 		: 420,
    	height 		: 210,
    	params 		: {"title" : title, "count" : count, "width" : width},
    	callback	: function(result) {
		    iFU.setMultiRow(result);
		}
	});
};

/* 양식 유형 카테고리 */
iFU.searchFormCategoryPopUp = function(){
    iKEP.showDialog({     
		title 		: iFC.message.categoryPopUp,
		url 		: iKEP.getContextRoot() + "/approval/admin/apprAdminForm/viewApprFormCategory.do",
		modal 		: true,
		width 		: 250,
		height 		: 300,
		callback	: function(result) {
	        $jq("#apprInfoForm input[name=formParentId]").val(result.codeId);
	        $jq("#apprInfoForm input[name=formParentName]").val(result.codeName);
	    }
	});
};

/* 서식 */
iFU.listApprFormDocTemplatePopUp = function(templateType){
    var editorData;

    if(templateType==0){
        if(!iFU.isLayoutFormItemDiv()) return;
        var layout = iFU.itemToLayoutFormData();
        if(layout.data.length==0) editorData = "";
        else editorData = JSON.stringify(layout);
    }else{
        editorData = $jq("#formEditorData").ckeditorGet().getData();
    }
    
    iKEP.showDialog({     
		title 		: iFC.message.templatePopUp,
		url 		: iKEP.getContextRoot() + "/approval/admin/apprFormDocTemplate/listApprFormDocTemplate.do?templateType=" + templateType,
		modal 		: true,
		width 		: 500,
		height 		: 400,
		params 		: {"editorData" : editorData},
		callback	: function(result) {
		    if(result.templateType=="0"){
		        iFU.formLayoutDisplay("show");
		        $jq("#formLayoutDiv").empty();
		        $jq("#apprContentForm textarea[name=formLayoutData]").val(result.editorData);
		        iFU.makeFormLayout("#apprContentForm");
		        iFU.changeMakerTab();
		        iFU.checkAutoPreviwer();
		    }else{
		        $jq("#formEditorData").ckeditorGet().setData(result.editorData);
		    }
	    }
	});
};

/* 버전이력 */
iFU.listApprFormHistoryPopup = function(formId) {

	 dialog = new iKEP.showDialog({     
		title 		: iFC.message.histroyPopUp,
		url 		: iKEP.getContextRoot() + "/approval/admin/apprAdminForm/listApprFormHistory.do?formId="+formId,
		modal 		: true,
		width 		: 600,
		height 		: 500,
		params 		: {formId:formId},
		callback : function(result) {
		}
	});
	 
};

/* 변경사유 */
iFU.viewApprFormChangeReasonPopUp = function(){
    
    iKEP.showDialog({     
		title 		: iFC.message.reasonPopUp,
    	url 		: iKEP.getContextRoot() + "/approval/work/apprWorkDoc/viewApprDocChangeReason.do",
    	modal 		: true,
    	width 		: 400,
    	height 		: 250,
    	callback	: function(result) {
		    $jq("#apprContentForm input[name=changeReason]").val(result);
		    iFC.object.process = true;
		    $jq("#saveContentButton").click();
		}
	});
};

/* 라인 생성 */
iFU.addLinePopUp = function(){
    
    var controlType  = "ORG";
	var controlTabType = "1:0:0:0";
	var selectType     = "ALL";
	var defLineType    = $jq("input[name=apprDocType]:checked").val();
	var systemId       = $jq("select[name=systemId]").val();
	var param = "?systemId=" + systemId + "&defLineType=" + defLineType + "&controlType=" + controlType + "&controlTabType=" + controlTabType + "&selectType=" + selectType;
	
    iKEP.showDialog({     
		title 		: iFC.message.linePopUp,
    	url 		: iKEP.getContextRoot() + "/approval/admin/apprDefLine/createPopupView.do" + param,
    	modal 		: true,
    	width 		: 1200,
    	height 		: 600,
    	callback	: function(result) {
		    $jq("input[name=defLineUse]").val("0");
            $jq("input[name=defLineId]").val(result);
            iFU.getApprDefLine("#apprInfoForm");
		}
	});
};
// iKEP Form PopUp Util End --------------------------------------------------------------------


// approval tooltip info start ------------------------------------------------------------------
iFU.showApprTooltipInfo = function(isShow, target, info, direction) {	//info : name, rank, team
	var $container = null;
	
	if(!iFU.showApprTooltipInfo.prototype.container) {
		$container = jQuery('<div style="top:0; left:0;z-index:2000;" class="infoLayer none"/>')
			.append('<div class="corner_RoundBox12">' +
					'<div style="padding-bottom:3px;margin-bottom:3px;border-bottom:1px dotted #828282;"><span class="name" ></span></div><div><span class="team"></span></div>' +
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
		$container.find("span.name").html(info.title).end()
			.find("span.team").html(info.detail);
		
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
			var info = {"title":"", "detail":""};
			var json = $jq.parseJSON($jq(iFC.object.actionItem).children("textarea").val());
			
		    info.title = "<b>" + iFC.message.title + "</b> : " + json.title;
            
            if(json.type=="text"){
                info.detail = "<b>" + iFC.message.type + "</b> : " + iFC.item[json.type].name + "<br/><b>" + iFC.message.merged + "</b> : " + iFU.yesNo(json.merged) + "<br/><b>" + iFC.message.required + "</b> : " + iFU.yesNo(json.required) +  "<br/><b>" + iFC.message.hideTitle + "</b> : " + iFU.yesNo(json.hideTitle) + "<br/><b>" + iFC.message.inputType + "</b>  : " + iFU.jsonGetValueByKey(iFC.textKind, "value", json.kind, "text") + "</br><b>" + iFC.message.width + "</b> : " + json.width + "px<br/><b>" + iFC.message.max + "</b> : " + json.maxlength + iFC.message.length + "<br/><b>" + iFC.message.defaultValue + "</b> : " + (json.defaultValue?json.defaultValue:"");
            }else if(json.type=="textarea"){
                info.detail = "<b>" + iFC.message.type + "</b> : " + iFC.item[json.type].name + "<br/><b>" + iFC.message.merged + "</b> : " + iFU.yesNo(json.merged) + "<br/><b>" + iFC.message.required + "</b> : " + iFU.yesNo(json.required) +  "<br/><b>" + iFC.message.hideTitle + "</b> : " + iFU.yesNo(json.hideTitle) + "</br><b>" + iFC.message.width + "</b> : " + json.width + "px<br/><b>" + iFC.message.height + "</b> : " + json.height + "px";
            }else if(json.type=="select"){
                info.detail = "<b>" + iFC.message.type + "</b> : " + iFC.item[json.type].name + "<br/><b>" + iFC.message.merged + "</b> : " + iFU.yesNo(json.merged) + "<br/><b>" + iFC.message.required + "</b> : " + iFU.yesNo(json.required) +  "<br/><b>" + iFC.message.hideTitle + "</b> : " + iFU.yesNo(json.hideTitle) + "<br/><b>" + iFC.message.data + "</b>  : " + (json.dataType=="0"?iFC.message.user:iFC.message.code);
            }else if(json.type=="radio"){
                info.detail = "<b>" + iFC.message.type + "</b> : " + iFC.item[json.type].name + "<br/><b>" + iFC.message.merged + "</b> : " + iFU.yesNo(json.merged) + "<br/><b>" + iFC.message.required + "</b> : " + iFU.yesNo(json.required) +  "<br/><b>" + iFC.message.hideTitle + "</b> : " + iFU.yesNo(json.hideTitle) + "<br/><b>" + iFC.message.sort + "</b> : " + (json.sort=="H"?iFC.message.horizontal:iFC.message.vertical) +  "<br/><b>" + iFC.message.data + "</b>  : " + (json.dataType=="0"?iFC.message.user:iFC.message.code);
            }else if(json.type=="checkbox"){
                info.detail = "<b>" + iFC.message.type + "</b> : " + iFC.item[json.type].name + "<br/><b>" + iFC.message.merged + "</b> : " + iFU.yesNo(json.merged) + "<br/><b>" + iFC.message.hideTitle + "</b> : " + iFU.yesNo(json.hideTitle) + "<br/><b>" + iFC.message.sort + "</b> : " + (json.sort=="H"?iFC.message.horizontal:iFC.message.vertical) +  "<br/><b>" + iFC.message.data + "</b>  : " + (json.dataType=="0"?iFC.message.user:iFC.message.code);
            }else if(json.type=="textbox"){
                info.detail = "<b>" + iFC.message.type + "</b> : " + iFC.item[json.type].name + "<br/><b>" + iFC.message.merged + "</b> : " + iFU.yesNo(json.merged) + "<br/><b>" + iFC.message.required + "</b> : " + iFU.yesNo(json.required) +  "<br/><b>" + iFC.message.hideTitle + "</b> : " + iFU.yesNo(json.hideTitle) + "<br/><b>" + iFC.message.header + "</b> : " + iFU.yesNo(json.header);
            }else if(json.type=="embed"){
                info.detail = "<b>" + iFC.message.type + "</b> : " + iFC.item[json.type].name + "<br/><b>" + iFC.message.merged + "</b> : " + iFU.yesNo(json.merged) + "<br/><b>" + iFC.message.required + "</b> : " + iFU.yesNo(json.required) +  "<br/><b>" + iFC.message.hideTitle + "</b> : " + iFU.yesNo(json.hideTitle) + "<br/><b>" + iFC.message.object + "</b> : " + iFU.jsonGetValueByKey(iFC.textKind, "value", json.kind, "text");
            }else if(json.type=="line"){
                info.detail = "<b>" + iFC.message.type + "</b> : " + iFC.item[json.type].name + "<br/><b>" + iFC.message.merged + "</b> : " + json.description + "<br/><b>" + iFC.message.height + "</b> : " + json.height + "px";
            }
			
			iFU.showApprTooltipInfo(1, this, info, "right");
		} else {
			iFU.showApprTooltipInfo(0);
		}
	});
})(jQuery);
// approval content page initialize process end ----------------------------------------------