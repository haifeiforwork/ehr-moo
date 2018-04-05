﻿<%--
=====================================================
* 기능 설명 : 기안문 작성
* 작성자    : approval
=====================================================
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/base/common/taglibs.jsp"%>
<%@ include file="/base/common/fileUploadControll.jsp"%><%-- 파일업로드용 Import --%>    
<c:set var="user" value="${sessionScope['ikep.user']}" />    
<%-- 메시지 관련 Prefix 선언 Start --%> 
<c:set var="preHeader"          value="ui.approval.apprForm.header" /> 
<c:set var="preList"            value="ui.approval.apprForm.list" />
<c:set var="preView"            value="ui.approval.apprForm.view" />
<c:set var="preIfm"             value="ui.approval.apprForm.iFM" /> 
<c:set var="preButton"          value="ui.approval.apprForm.button" />
<c:set var="preIfmMessage"      value="message.approval.apprForm.iFM" />
<c:set var="preViewMessage"     value="message.approval.apprForm.view" />
<c:set var="preLineMessage"		value="message.approval.apprForm.apprLine.docForm" />
<c:set var="preApprLineView"	value="ui.approval.work.apprLine.listApprLineView.view"/>

<%-- 메시지 관련 Prefix 선언 End --%>
<link rel="stylesheet" type="text/css" href="<c:url value="/base/css/approval/work/apprDoc.css"/>"/>
<script type="text/javascript" src="<c:url value="/base/js/units/approval/work/apprDoc.js"/>"></script>
<script type="text/javascript" src="<c:url value="/base/js/ckeditor/ckeditor.js"/>"></script>
<script type="text/javascript" src="<c:url value="/base/js/ckeditor/adapters/jquery.js"/>"></script>     
<%@ include file="/base/common/fileUploadControll.jsp"%><%-- 파일업로드용 Import --%>    
<script type="text/javascript">

<!--// 
    iFC.constant.apprType[0]        = "<ikep4j:message pre='${preIfm}' key='apprType.appr'/>";
    iFC.constant.apprType[1]        = "<ikep4j:message pre='${preIfm}' key='apprType.agree'/>";
    iFC.constant.apprType[2]        = "<ikep4j:message pre='${preIfm}' key='apprType.choiceAgree'/>";
    iFC.constant.apprType[3]        = "<ikep4j:message pre='${preIfm}' key='apprType.receive'/>";
    iFC.constant.apprStatus[0]      = "<ikep4j:message pre='${preIfm}' key='apprStatus.pending'/>";
    iFC.constant.apprStatus[1]      = "<ikep4j:message pre='${preIfm}' key='apprStatus.progress'/>";
    iFC.constant.apprStatus[2]      = "<ikep4j:message pre='${preIfm}' key='apprStatus.approval'/>";
    iFC.constant.apprStatus[3]      = "<ikep4j:message pre='${preIfm}' key='apprStatus.reject'/>";
    iFC.constant.apprStatus[4]      = "<ikep4j:message pre='${preIfm}' key='apprStatus.agreement'/>";

    iFC.textKind[0].text            = "<ikep4j:message pre='${preIfm}' key='textKind.all'/>";
    iFC.textKind[1].text            = "<ikep4j:message pre='${preIfm}' key='textKind.digits'/>";
    iFC.textKind[2].text            = "<ikep4j:message pre='${preIfm}' key='textKind.number'/>";
    iFC.textKind[3].text            = "<ikep4j:message pre='${preIfm}' key='textKind.numberComma'/>";
    iFC.textKind[4].text            = "<ikep4j:message pre='${preIfm}' key='textKind.date'/>";
    iFC.textKind[5].text            = "<ikep4j:message pre='${preIfm}' key='textKind.email'/>";
    iFC.textKind[6].text            = "<ikep4j:message pre='${preIfm}' key='textKind.phone'/>";
    iFC.textKind[7].text            = "<ikep4j:message pre='${preIfm}' key='textKind.zipcode'/>";    
    
    iFC.bindData.userId.text        = "<ikep4j:message pre='${preIfm}' key='bindData.userId'/>";
    iFC.bindData.userName.text      = "<ikep4j:message pre='${preIfm}' key='bindData.userName'/>";
    iFC.bindData.jobTitleName.text  = "<ikep4j:message pre='${preIfm}' key='bindData.jobTitleName'/>";
    iFC.bindData.teamName.text      = "<ikep4j:message pre='${preIfm}' key='bindData.teamName'/>";
    
    iFC.message.required            = "<ikep4j:message pre='${preIfmMessage}' key='required'/>";
    iFC.message.maxlength           = "<ikep4j:message pre='${preIfmMessage}' key='maxlength'/>";
    iFC.message.minSize             = "<ikep4j:message pre='${preIfmMessage}' key='minSize'/>";
    iFC.message.digits              = "<ikep4j:message pre='${preIfmMessage}' key='digits'/>";
    iFC.message.number              = "<ikep4j:message pre='${preIfmMessage}' key='number'/>";
    iFC.message.numberComma         = "<ikep4j:message pre='${preIfmMessage}' key='numberComma'/>";
    iFC.message.date                = "<ikep4j:message pre='${preIfmMessage}' key='date'/>";
    iFC.message.email               = "<ikep4j:message pre='${preIfmMessage}' key='email'/>";
    iFC.message.phone               = "<ikep4j:message pre='${preIfmMessage}' key='phone'/>";
    iFC.message.zipcode             = "<ikep4j:message pre='${preIfmMessage}' key='zipcode'/>";
    iFC.message.couldntMultiRow     = "<ikep4j:message pre='${preIfmMessage}' key='couldntMultiRow'/>";
    iFC.message.notFoundResult      = "<ikep4j:message pre='${preIfmMessage}' key='notFoundResult'/>";
    iFC.message.notChecked          = "<ikep4j:message pre='${preIfm}' key='notChecked'/>";
    iFC.message.checked             = "<ikep4j:message pre='${preIfm}' key='checked'/>";
    iFC.message.approvalPopUp       = "<ikep4j:message pre='${preIfm}' key='approvalPopUp'/>";
    iFC.message.reasonPopUp         = "<ikep4j:message pre='${preIfm}' key='reasonPopUp'/>";
    iFC.message.docDeatilPopUp      = "<ikep4j:message pre='${preIfm}' key='docDeatilPopUp'/>";
    iFC.message.total               = "<ikep4j:message pre='${preIfm}' key='total'/>";
    iFC.message.remove              = "<ikep4j:message pre='${preIfm}' key='remove'/>";
    iFC.message.add                 = "<ikep4j:message pre='${preIfm}' key='add'/>";
	
	var dupLine		=	false;	// 결재선 중복여부
	var dupRcvLine	=	false;	// 수신처 중복여부
	
	(function($) {
		
		// Role(직책)을 이용한 Default 결재선지정시 해당 사용자가 없는경우 체크 
		var checkDefLine	=	0;
		var checkRcvDefLine	=	0;
		
		//- onload시 수행할 코드
		$(document).ready(function() {

		    // 메뉴선택
		    $("#apprFormLinkOfLeft").click();
			
		    //- validation
    	    var validOptions = {
                rules  : {
                    
                },
                messages : {
                    
                },    
                notice : {
                },      
    	        submitHandler : function(form) {
            
   					if(dupLine) {
   						alert("<ikep4j:message pre='${preLineMessage}' key='dupLine' />");
   						return;
   					}
   					
   					if(dupRcvLine) {
   						alert("<ikep4j:message pre='${preLineMessage}' key='dupReceiveLine' />");
   						return;
   					}
   					   					
    	            if($(form).find("input[name=apprLine]").val()=="" && $(form).find("input[name=apprDocStatus]").val()=="1"){
    	                alert("<ikep4j:message pre='${preLineMessage}' key='selectApproval' />");
    	                return;
    	            }
    	            
    	            // 결재 요청시 기안의견 팝업 호출
    	            if(!iFC.object.process && $(form).find("input[name=apprDocStatus]").val()=="1"){
    	                iFU.viewApprDocRegisterMessagePopUp();
    	                return false;
    	            }
    	            
    	            var message =  $(form).find('input[name=apprDocStatus]').val()=="0"?"<ikep4j:message pre='${preViewMessage}' key='tempSave'/>":"<ikep4j:message pre='${preViewMessage}' key='apprRequest'/>";
    	            if(confirm(message)){
    	                <c:if test="${apprForm.isApprAttach eq 1}"> 
    	                fileUploadController.upload(function(isSuccess, files) {
							if(isSuccess === true) { 
				        </c:if>
    	                
    	                //- formData 생성
    	                 var json = "";
    	                if($("#apprDocForm textarea[name=formLayoutData]").val()!=""){
    	                    json = iFU.itemToFormData(form, $(form).find("input:[name=formId]").val(), $(form).find("input:[name=apprTitle]").val());
	                        if(json=="false") return false;
                        }
                        
                        //- formLayout 초기화(layout form 객체 삭제)
                        <c:if test="${not empty apprForm.formLayoutData}">
                        $("#formLayoutDiv").empty();
                        </c:if>
                        
                        //- 에디터 사용시
                        <c:if test="${apprForm.isApprEditor eq 1}">
						var editor = $("#formEditorData").ckeditorGet();
                        editor.updateElement();
                        
                        //에디터 내 이미지 파일 링크 정보 세팅
			            createEditorFileLink($(form).attr("id"));
    	                </c:if>
    	                
    	                //- 참조자 저장
    	                iFU.setReferenceSet(form);
    	                
    	                //- 열람권한 저장
    	                iFU.setReadSet(form);
    	                
    	                //- 기결재 저장
    	                iFU.setApprRefId(form);
    	                
    	                $("body").ajaxLoadStart("button");
    	                if($("#apprDocForm textarea[name=formLayoutData]").val()!=""){
    	                    $(form).find("textarea[name=formData]").val(JSON.stringify(json));
    	                }
                        form.submit();
    	                
    	                <c:if test="${apprForm.isApprAttach eq 1}"> 
    	                    }
						});
						</c:if>
    	            }else{
    	                iFC.object.process = false;
    	                return false;
    	            }
                }
    		};	      
    		
    		//- user info
    		iFU.setUserInfo("${user.userId}", "${user.localeCode}");
    		
    		//- initialize form
    		iFU.initializeApprDocForm("#apprDocForm", "new");
    		
    		
    		//- 버튼영역 실행
        	$("#formButtonDiv a").click(function(){
        	    switch (this.id) {
        	        
        	        // 임시 저장
        	        case "createApprDocTemporayButton":
        	            $("#apprDocForm input[name=apprDocStatus]").val("0");
        			    $("#apprDocForm").trigger("submit");
        			    break;
        			
        			// 결재자 지정
        			case "addApprLineButton":
        			    var apprId			=	$("#apprDocForm input[name=apprId]").val();
                		var apprLineType	=	$("#apprDocForm input[name=apprLineType]").val();
                		
                		var items = $("#apprDocForm input[name=apprLine]").val()==""?[]:$.parseJSON($("#apprDocForm input[name=apprLine]").val());
                		<c:if test="${apprConfig.lineViewType=='0'}"> 
                		iKEP.showApprovalLine(iFU.setApprLine, items, {selectType:"all",apprId:apprId,apprLineType:apprLineType,  isAppend:false, tabs:{common:0, personal:0, collaboration:0, sns:0}});
                		</c:if>
                		<c:if test="${apprConfig.lineViewType=='1'}"> 
                		iKEP.showApprovalLine(iFU.setApprLineHeight, items, {selectType:"all",apprId:apprId,apprLineType:apprLineType,  isAppend:false, tabs:{common:0, personal:0, collaboration:0, sns:0}});
                		</c:if>			
        			    break;
        			    
        			// 결재요청
                    case "createApprDocButton":
                        $("#apprDocForm input[name=apprDocStatus]").val("1");
        			    $("#apprDocForm").trigger("submit");
        			    break;
        			    
                    // 목록
                    case "listApprDocButton":
                        $("body").ajaxLoadStart("button");
                        $("#listForm").submit();
                        break;
                    default:
                        break;
                }   
            });
    		
    		
    		//- 결재라인
		    <c:if test="${!empty apprLineList}">
		    initApprLineList();
            </c:if>
            
            //-  수신처
		    <c:if test="${!empty apprReceiveLineList}">
		    initApprReceiveLineList();
            </c:if>
            		
    		//- 참조자
		    <c:if test="${!empty apprReferenceList}">
		    initReferenceList();
		    </c:if>
		    
		    // 연계 HTML
		    if($("#apprDocForm input[name=isLinkUrl]").val()=="1" && $("#apprDocForm input[name=linkDataType]").val()=="0" && $("#apprDocForm textarea[name=formLinkedData]").val()!=""){
		        $("#formLinkedHtmlDataDiv").html($("#apprDocForm textarea[name=formLinkedData]").val()).show();
		    }
		    
    		//- formLayout
    		if($("#apprDocForm textarea[name=formLayoutData]").val()!=""){
    		    // 연계 데이터 존재시
    		    if($("#apprDocForm input[name=isLinkUrl]").val()=="1" && $("#apprDocForm input[name=linkDataType]").val()=="1" && $("#apprDocForm textarea[name=formLinkedData]").val()!=""){
    		        var layout, linked;
    		        try{
    		            layout = $.parseJSON($("#apprDocForm textarea[name=formLayoutData]").val());
    		            linked = $.parseJSON($("#apprDocForm textarea[name=formLinkedData]").val());
    		            $("#apprDocForm textarea[name=formLayoutData]").val(iFU.mergeFormLayout(layout, linked));
    		        }catch(e){
    		            alert("<ikep4j:message pre='${preViewMessage}' key='linkedDataError'/>");
    		            $("#listApprDocButton").click();
    		        }
    		    }
    		        
    		    iFC.object.formLayoutData = $.parseJSON($("#apprDocForm textarea[name=formLayoutData]").val());
    		    
    		    iFU.printForm();
    		    
    		    //- rules 셋팅 
                if(iFC.object.rules!=""){
                    validOptions.rules = $.parseJSON("{" + iFC.object.rules + "}");
                }

                //- messages 셋팅
                if(iFC.object.messages!=""){
                    validOptions.messages = $.parseJSON("{" + iFC.object.messages + "}");
                }
    		}
    		
    		//- 보존연한 사용자 설정시
    		<c:if test="${apprForm.isApprPeriod eq 0}">
    		    validOptions.rules.apprPeriodCd = {"required" : true};
    		    validOptions.messages.apprPeriodCd = {"required" : "<ikep4j:message pre='${preViewMessage}' key='apprPeriod'/>"};
    		</c:if> 
    		
    		//- formEditor
    		<c:if test="${apprForm.isApprEditor eq 1}">
    		    fullCkeditorConfig.height="250";
			    $("#formEditorData").ckeditor($.extend(fullCkeditorConfig, {"language" : "${user.localeCode}", "popupTitle" : "editor" }));
    	    </c:if> 
    	    
    	    //- file download
    		<c:if test="${not empty apprForm.fileDataList}"> 
    		var downloadOptions = {
    		   <c:if test="${not empty fileDataListJson}">files : ${fileDataListJson},</c:if>  
    		   isUpdate : false 
    		}; 
    		
    	    var fileDownloadController = new iKEP.FileController(null, "#fileDownloadDiv", downloadOptions); 
    	    </c:if> 
    	    
    	    //- file upload
    	    <c:if test="${apprForm.isApprAttach eq 1}"> 
    		var uploaderOptions = {
    		 	files : [],
    			allowFileType : "all",
    	    	isUpdate : true
    	    };  
    	    
	        var fileUploadController = new iKEP.FileController("#apprDocForm", "#fileUploadDiv", uploaderOptions);
    	    </c:if> 
    	    
            //- validator mapping
            new iKEP.Validator("#apprDocForm", validOptions);

/**
			if( checkDefLine > 0 ) {
				$("body").ajaxLoadStart("button");
				alert("<ikep4j:message pre='${preLineMessage}' key='noMatch' />");
				history.back();
			}
			
			if( checkRcvDefLine > 0 ) {
				$("body").ajaxLoadStart("button");
				alert("<ikep4j:message pre='${preLineMessage}' key='noReceiveMatch' />");
				history.back();			
			}	
**/			
			
			// 기본결재선사용여부
			if($jq("#apprDocForm").find("input[name=defLineUse]").val()=="1") {

				// 결재선 수정불가시
				if($jq("#apprDocForm").find("input[name=isDefLineUpdate]").val()=="0"){

					if( checkDefLine > 0 ) {
						alert("<ikep4j:message pre='${preLineMessage}' key='noMatchAdmin' />");
						$("#listApprDocButton").click();
					}
			
					if( checkRcvDefLine > 0 ) {
						alert("<ikep4j:message pre='${preLineMessage}' key='noReceiveMatchAdmin' />");
						$("#listApprDocButton").click();
					}            			
            
   					if(dupLine) {
   						alert("<ikep4j:message pre='${preLineMessage}' key='dupLineAdmin' />");
   						$("#listApprDocButton").click();
   					}
   					
   					if(dupRcvLine) {
   						alert("<ikep4j:message pre='${preLineMessage}' key='dupReceiveLineAdmin' />");
   						$("#listApprDocButton").click();
   					}
				}
				// 결재선 수정 가능시
				if($jq("#apprDocForm").find("input[name=isDefLineUpdate]").val()=="1"){

					if( checkDefLine > 0 ) {
						alert("<ikep4j:message pre='${preLineMessage}' key='noMatch' />");
						return;
					}
			
					if( checkRcvDefLine > 0 ) {
						alert("<ikep4j:message pre='${preLineMessage}' key='noReceiveMatch' />");
						return;	
					}            			
            
   					if(dupLine) {
   						alert("<ikep4j:message pre='${preLineMessage}' key='dupLine' />");
   						return;
   					}
   					
   					if(dupRcvLine) {
   						alert("<ikep4j:message pre='${preLineMessage}' key='dupReceiveLine' />");
   						return;
   					}            
				}				
			}
						
			
        });    
        
        //- 참조자
		<c:if test="${!empty apprReferenceList}">
		initReferenceList	=	function(){
    		// 결재문서의 결재선 정보 초기 설정
    		var items = [];
    		<c:forEach var="item" items="${apprReferenceList}">	
    		items.push({// 공통필수
    			    type            : "user",
					empNo           : "",
					id              : "${item.userId}",
					userName        : "${item.userName}",
					jobTitleName    : "${item.jobTitleName}",
					group           : "${item.groupId}",
					teamName        : "${item.teamName}",
					email           : "${item.mail}",
					mobile          : "",
					searchSubFlag   : false
    		});	
    		</c:forEach>
    		iFU.setReferenceAddress(items);
    	};
    	</c:if>	
        
        //- 결재라인
		<c:if test="${!empty apprLineList}">
		initApprLineList	=	function(){
    		// 결재문서의 결재선 정보 초기 설정
    		var items	=	[];
    		<c:forEach var="item" items="${apprLineList}">
    		if("${item.approverId}"=="")
    			checkDefLine++;
    			
    		items.push({// 공통필수
      			type			:	${item.approverType}==1?"group":"user",  		
    			apprType		:	${item.apprType},
    			id				:	"${item.approverId}",
    			code			:	"${item.approverId}",
    			userName		:	"${item.approverName}",
    			teamName		:	"${item.approverGroupName}",
    			jobTitleName	:	"${item.approverJobTitle}",	
    			jobTitleCode	:	"${item.approverJobTitleCode}",
    			approverType	:	${item.approverType},		
    			apprOrder		:	${item.apprOrder},				
    			apprStatus		:	${item.apprStatus},
    			lineModifyAuth	:	${item.lineModifyAuth},
    			docModifyAuth	:	${item.docModifyAuth},	
    			readModifyAuth	:	${item.readModifyAuth},	
    			apprDate		:	${item.apprStatus}>1?"${item.apprDate}":"",
    			name			:	${item.approverType}==1?"${item.approverGroupName}":"${item.approverName} ${item.approverJobTitle} ${item.approverGroupName}"
    		});	
    		</c:forEach>

    		<c:if test="${apprConfig.lineViewType=='0'}"> 
    		iFU.setApprLine(items);
    		</c:if>
    		<c:if test="${apprConfig.lineViewType=='1'}"> 
    		iFU.setApprLineHeight(items);
    		</c:if>    		
    	};
    	</c:if>	
    	
    	//- 수신처
    	<c:if test="${!empty apprReceiveLineList}">
    	initApprReceiveLineList	=	function(){
    		// 결재문서의 결재선 정보 초기 설정
    		var items	=	[];
    		<c:forEach var="item" items="${apprReceiveLineList}">	
    		if("${item.approverId}"=="")
    			checkRcvDefLine++;
    			    		
    		items.push({// 공통필수
    			type			:	${item.approverType}==1?"group":"user",
    			apprType		:	${item.apprType},
    			id				:	"${item.approverId}",
    			code			:	"${item.approverId}",
    			userName		:	"${item.approverName}",
    			teamName		:	"${item.approverGroupName}",
    			jobTitleName	:	"${item.approverJobTitle}",	
    			jobTitleCode	:	"${item.approverJobTitleCode}",		
    			approverType	:	${item.approverType},
    			apprOrder		:	${item.apprOrder},				
    			apprStatus		:	${item.apprStatus},
    			lineModifyAuth	:	${item.lineModifyAuth},
    			docModifyAuth	:	${item.docModifyAuth},	
    			readModifyAuth	:	${item.readModifyAuth},	
    			apprDate		:	${item.apprStatus}>1?"${item.apprDate}":""
    		});	
    		</c:forEach>
    		iFU.setApprReceiveLine(items);
    	};
    	</c:if>		
	})(jQuery);  

//-->
</script>
<h1 class="none">contnet area</h1>

<!--pageTitle Start-->
<div id="pageTitle">
	<h2><ikep4j:message pre='${preView}' key='apprDocNew'/></h2>
</div>
<!--//pageTitle End-->

<div id="guideConFrame">
	<div class="blockBlank_10px"></div>
	<!--blockDetail Start-->
	<div id="formButtonDiv" class="blockButton btnline_appr" >  <!-- class 추가 -->
		<ul>
		    <li><a id="createApprDocTemporayButton"     class="button" href="#a"><span><ikep4j:message pre='${preButton}' key='createApprDocTemporay'/></span></a></li>
		    <li><a id="addApprLineButton"               class="button" href="#a"><span><ikep4j:message pre='${preButton}' key='addApprLine'/></span></a></li>
		    <li><a id="createApprDocButton"             class="button" href="#a"><span><ikep4j:message pre='${preButton}' key='createApprDoc'/></span></a></li>
			<li><a id="listApprDocButton"               class="button" href="#a"><span><ikep4j:message pre='${preButton}' key='listApprDoc'/></span></a></li>
		</ul>
	</div>
	
	<c:if test="${apprConfig.lineViewType=='0'}"> 
	<table width="100%" border="0" cellspacing="0" cellpadding="0">
        <tr>
            <td colspan="2" align="center" class="font_style1 pt20">${apprForm.formName}</td> <!-- class 추가 -->
        </tr>
        <tr id="apprLine0Tr">
            <td colspan="2" align="right">
                <table border="0" cellspacing="0" cellpadding="0" class="table_style">
                    <tr id="apprLine0TTr">
                        <th width="25" height="100" rowspan="2" align="center" class="first"><ikep4j:message pre='${preView}' key='apprTh' htmlEscape='fasle' /></th>
                    </tr>
                    <tr id="apprLine0BTr">
                    </tr>
                </table>
            </td>
        </tr>
        <tr>
            <td colspan="2" align="right">&nbsp;</td>
        </tr>
        <tr id="apprLine1Tr">
            <td colspan="2" align="right">
                <table border="0" cellspacing="0" cellpadding="0" class="table_style">
                    <tr id="apprLine1TTr">
                        <th width="25" height="100" rowspan="2" align="center" class="first"><ikep4j:message pre='${preView}' key='agreeTh' htmlEscape='fasle'/></th>
                    </tr>
                    <tr id="apprLine1BTr">
                    </tr>
                </table>
            </td>
        </tr>
        <tr>
            <td colspan="2" align="right">&nbsp;</td>
        </tr>
	</table>
	</c:if>
	<c:if test="${apprConfig.lineViewType=='1'}"> 
		<!--blockListTable Start-->
		<div class="Approval_1">
		
			<table summary="<ikep4j:message pre='${preView}' key='subLine.summary' />">
				<caption></caption>
				<col style="width: 10%;"/>
				<col />
				<col style="width: 20%;"/>
				<col style="width: 12%;"/>
				<col style="width: 12%;"/>
				<col style="width: 15%;"/>
				
				<thead>
					<tr>				
						<th class="textCenter" scope="row"><ikep4j:message pre='${preApprLineView}' key='line.no' /></th>
						<th class="textCenter" scope="row"><ikep4j:message pre='${preApprLineView}' key='line.approver' /></th>
						<th class="textCenter" scope="row"><ikep4j:message pre='${preApprLineView}' key='line.dept' /></th>
						<th class="textCenter" scope="row"><ikep4j:message pre='${preApprLineView}' key='line.apprType' /></th>
						<th class="textCenter" scope="row"><ikep4j:message pre='${preApprLineView}' key='line.status' /></th>
						<th class="textCenter" scope="row"><ikep4j:message pre='${preApprLineView}' key='line.apprDate' /></th>
					</tr>
				</thead>
				
				<tbody id="apprLineListBody" class="table_style">
				
				</tbody>
			</table>				
			<div class="clear"></div>	
		</div>			
	</c:if>
	
	<div class="Approval_1"> <!-- class 수정 -->
    <form id="apprDocForm" name="apprDocForm" method="post" action="createApprDoc.do">
        
        <!-- 시스템 -->
        <spring:bind path="apprForm.systemId">
        <input type="hidden" name="${status.expression}" value="${status.value}"/>
        </spring:bind>
        <!-- 문서아이디 -->
        <spring:bind path="apprDoc.apprId">
        <input type="hidden" name="${status.expression}" value="${status.value}"/>
        </spring:bind>        
        <!-- 폼아이디 -->
        <spring:bind path="apprForm.formId">
        <input type="hidden" name="${status.expression}" value="${status.value}"/>
        </spring:bind>
        
        <!-- 폼버전 -->
        <spring:bind path="apprForm.formVersion">
        <input type="hidden" name="${status.expression}" value="${status.value}"/>
        </spring:bind>
        
        <!-- 기안자 직책 -->
        <spring:bind path="apprDoc.registerJobTitle">
        <input type="hidden" name="${status.expression}" value="${user.jobTitleName}"/>
        </spring:bind>
        
        <!-- 문서보안설정 -->
        <spring:bind path="apprForm.apprDocType">
        <input type="hidden" name="${status.expression}" value="${status.value}"/>
        </spring:bind>
        
        <!-- 양식 -->
        <spring:bind path="apprForm.formLayoutData">
        <textarea name="${status.expression}" style="display:none;">${status.value}</textarea>
        </spring:bind>
        
        <!-- 연계 양식 -->
        <spring:bind path="apprDoc.formLinkedData">
        <textarea name="${status.expression}" style="display:none;">${status.value}</textarea>
        </spring:bind>
        
        <!-- 연계 여부 -->
        <spring:bind path="apprForm.isLinkUrl">
        <input type="hidden" name="${status.expression}" value="${status.value}"/>
        </spring:bind>
        
        <!-- 연계 데이터 타입 0:html, 1:json -->
        <spring:bind path="apprForm.linkDataType">
        <input type="hidden" name="${status.expression}" value="${status.value}"/>
        </spring:bind>

        <!-- 양식 데이터 -->
        <spring:bind path="apprForm.formData">
        <textarea name="${status.expression}" style="display:none;"></textarea>
        </spring:bind>
        
        <!-- 문서유형(0:순차,1:병렬) -->
        <spring:bind path="apprDoc.apprLineType">
        <input type="hidden" name="${status.expression}" value="${status.value}"/>
        </spring:bind>
        
        <!-- 기본 결재 라인 아이디 -->
        <spring:bind path="apprForm.defLineId">
        <input type="hidden" name="${status.expression}" value="${status.value}" />
        </spring:bind>
        
        <!-- 기본결재라인사용여부 -->
        <spring:bind path="apprForm.defLineUse">
        <input type="hidden" name="${status.expression}" value="${status.value}" />
        </spring:bind>
        
        <!-- 기본결재라인 수정가능여부 -->
        <spring:bind path="apprForm.isDefLineUpdate">
        <input type="hidden" name="${status.expression}" value="${status.value}" />
        </spring:bind>
        
        <!-- 결재라인 -->
        <spring:bind path="apprDoc.apprLine">
        <input type="hidden" name="${status.expression}"/>
        </spring:bind>
        
        <!-- 수신처 -->
        <spring:bind path="apprDoc.apprReceiveLine">
        <input type="hidden" name="${status.expression}"/>
        </spring:bind>
        
        <!-- 문서상태 -->
        <spring:bind path="apprDoc.apprDocStatus">
        <input type="hidden" name="${status.expression}" value="1"/>
        </spring:bind>
        
        <!-- 수신처 사용유무 (0:사용안함,1:사용) -->
        <spring:bind path="apprDoc.isApprReceive">
        <input type="hidden" name="${status.expression}" value="0" />
        </spring:bind>
        
        <!-- 리스트 타입 -->
        <spring:bind path="apprDoc.listType">
        <input type="hidden" name="${status.expression}" value="listApprForm"/>
        </spring:bind>
        
        <!-- 기안자 결재요청 시 등록 의견 -->
        <spring:bind path="apprDoc.registerMessage">
        <input type="hidden" name="${status.expression}" />
        </spring:bind>
        
        <!-- 기결재문서 첨부 -->
        <spring:bind path="apprDoc.apprRefId">
        <input type="hidden" name="${status.expression}" />
        </spring:bind>
        
        <!-- 모드 -->
        <spring:bind path="apprDoc.mode">
        <input type="hidden" name="${status.expression}" value="new"/>
        </spring:bind>
        
        <!-- linkType -->
        <spring:bind path="apprDoc.linkType">
        <input type="hidden" name="${status.expression}" value="${status.value}"/>
        </spring:bind>

		<table summary="">
			<caption></caption>
			<tbody>
			    <tr>
					<th width="15%" scope="row"><ikep4j:message pre='${preView}' key='apprNo'/></th>
					<td width="35%"></td>
					<th width="15%" scope="row"><ikep4j:message pre='${preView}' key='apprReqDate'/></th>
					<td width="35%">${today}</td>
				</tr>
				<tr>
					<th scope="row"><ikep4j:message pre='${preView}' key='registerName'/></th>
					<td>${user.userName}</td>
					<th scope="row"><ikep4j:message pre='${preView}' key='apprGroupId'/></th>
					<td>
			    <c:if test="${groupSize == 1}">
			        <!-- 기안 부서코드 -->
				    <spring:bind path="apprDoc.apprGroupId">
                        <input type="hidden" name="${status.expression}" value="${user.groupId}"/>
                    </spring:bind>
                    ${user.teamName}
                </c:if>
                <c:if test="${groupSize > 1}">
                    <spring:bind path="apprDoc.apprGroupId">
                        <select name="${status.expression}" onchange="iFU.setGroupName(this);" title="<ikep4j:message pre='${preView}' key='${status.expression}'/>">
                        <c:forEach var="item" items="${groupList}">
                            <option value="${item.groupId}" <c:if test="${item.groupId eq user.groupId}">selected="selected"</c:if>><c:if test="${user.localeCode eq 'ko'}">${item.groupName}</c:if><c:if test="${user.localeCode ne 'ko'}">${item.groupEnglishName}</c:if></option>    
                        </c:forEach>   
                        </select> 
                    </spring:bind>
                </c:if>    
					
					<!-- 기안 부서명 -->
                    <spring:bind path="apprDoc.apprGroupName">
                        <input type="hidden" name="${status.expression}" value="${user.teamName}"/>
                    </spring:bind>    
			        </td>
				</tr>
				<tr>
					<th scope="row"><ikep4j:message pre='${preView}' key='apprSecurity'/></th>
					<td>
					<c:if test="${apprForm.isApprSecurity eq 0}">
    					<spring:bind path="apprForm.apprSecurityType">
    					    <input type="radio" class="radio" value="0" name="${status.expression}" <c:if test="${status.value eq 0}">checked="checked"</c:if> title="<ikep4j:message pre='${preView}' key='gerneral'/>" /> <ikep4j:message pre='${preView}' key='gerneral'/>
                            <input type="radio" class="radio" value="1" name="${status.expression}" <c:if test="${status.value eq 1}">checked="checked"</c:if> title="<ikep4j:message pre='${preView}' key='security'/>" /> <ikep4j:message pre='${preView}' key='security'/>
    				    </spring:bind>
				    </c:if>  
				    <c:if test="${apprForm.isApprSecurity eq 1}">	    
    					<spring:bind path="apprForm.apprSecurityType">
        					<input type="hidden" name="${status.expression}" value="${status.value}" />
    					    <c:if test="${status.value eq 0}"><ikep4j:message pre='${preView}' key='gerneral'/></c:if>
    					    <c:if test="${status.value eq 1}"><ikep4j:message pre='${preView}' key='security'/></c:if>
    				    </spring:bind>
    			    </c:if>   
					</td>
					<th scope="row"><ikep4j:message pre='${preView}' key='apprPeriodCd'/></th>
					<td>
				    <c:if test="${apprForm.isApprPeriod eq 0}">	    
    					<spring:bind path="apprForm.apprPeriodCd">
    					<select name="${status.expression}">
    					    <option value=""><ikep4j:message pre='${preView}' key='select'/></option>
    					    <c:forEach var="item" items="${apprPeriodList}">
    					    <option value="${item.codeId}" <c:if test="${item.codeId eq status.value}">selected="selected"</c:if>>${item.codeName}</option>    
    					    </c:forEach>
    				    </select>    
    				    </spring:bind>
    			    </c:if>
    			    <c:if test="${apprForm.isApprPeriod eq 1}">	    
    					<spring:bind path="apprForm.apprPeriodCd">
    					<input type="hidden" name="${status.expression}" value="${status.value}" />
					    <c:forEach var="item" items="${apprPeriodList}">
					        <c:if test="${item.codeId eq status.value}">${item.codeName}</c:if>
					    </c:forEach>    				    
    				    </spring:bind>
    			    </c:if>
			        </td>
				</tr>
				<tr>
					<th scope="row"><ikep4j:message pre='${preView}' key='referenceId'/> <a id="referenceAddressButton" href="#a"><img src="/ikep4-webapp/base/images/icon/ic_btn_plus.gif" style="vertical-align:middle"></a></th>
					<td>
    					<spring:bind path="apprDoc.referenceId">
    					<input type="hidden" name="${status.expression}" />
    					</spring:bind>
    					<spring:bind path="apprDoc.referenceSet">
    					<select name="${status.expression}" class="inputbox w100" size="3" multiple="multiple"></select>
    					</spring:bind>
				    </td>
					<th scope="row"><ikep4j:message pre='${preView}' key='readId'/> <a id="readAddressButton" href="#a"><img src="/ikep4-webapp/base/images/icon/ic_btn_plus.gif" style="vertical-align:middle"></a></th>
					<td>
					    <spring:bind path="apprDoc.readId">
    					<input type="hidden" name="${status.expression}" />
    					</spring:bind>
    					<spring:bind path="apprDoc.readSet">
    					<select name="${status.expression}" class="inputbox w100" size="3" multiple="multiple"></select>
    					</spring:bind>
				    </td>
		        </tr>
		        <tr id="apprReceiveLineTr">
				    <th scope="row"><ikep4j:message pre='${preView}' key='apprReceive'/> <a id="addApprReceiveLineButton" href="#a"><img src="/ikep4-webapp/base/images/icon/ic_btn_plus.gif" style="vertical-align:middle" /></a></th>
					<td colspan="3" id="apprReceiveLineInfoDiv"></td>
				</tr>
				<tr>
				    <th scope="row"><ikep4j:message pre='${preView}' key='apprRefInfo'/> <a id="addApprMyRequestCompleteButton" href="#a"><img src="/ikep4-webapp/base/images/icon/ic_btn_plus.gif" style="vertical-align:middle"></a></th>
					<td colspan="3">
				        <ul id="apprRefInfoUl">
				        </ul>
				    </td>
				</tr>
				<tr>
				    <th scope="row"><ikep4j:message pre='${preView}' key='apprTitle'/></th>
					<td colspan="3">
				    <spring:bind path="apprDoc.apprTitle">
				    <c:if test="${apprForm.isApprTitle eq 0}">        
    			        <input type="text" name="${status.expression}" value="${apprForm.formName}" class="inputbox w80" />
    			    </c:if>
    			    <c:if test="${apprForm.isApprTitle eq 1}">
    			        <input type="hidden" name="${status.expression}" value="${apprForm.formName}" />${apprForm.formName}
    			    </c:if>    
    			    </spring:bind>
				    </td>
				</tr>
				<c:if test="${not empty apprForm.formGuide}">
				<tr>
					<th scope="row"><ikep4j:message pre='${preView}' key='formGuide'/></th>
					<td colspan="3">
						<%pageContext.setAttribute("newLineChar", "\n");%>
						${fn:replace(apprForm.formGuide, newLineChar, "<br />")}
			        </td>
				</tr>		
		        </c:if>
	        </tbody>
	    </table>
	    <div id="formLinkedHtmlDataDiv" style="border: 1px solid #e0e0e0;" class="mt10 padding10 none"></div>
		<c:if test="${not empty apprForm.formLayoutData}">
		<div id="formLayoutDiv" style="border-top: 1px solid #e0e0e0;" class="mt10"> <!-- class 추가 -->
			<table id="layoutTable" summary="<ikep4j:message pre='${preView}' key='basicInfo'/>" width="100%">
				<caption></caption>
				<tbody>
				</tbody>
				<tfoot>
				</tfoot>
			</table>
		</div>
		</c:if>
		<c:if test="${apprForm.isApprEditor eq 1}">
		<div class="border_t1">
            <spring:bind path="apprForm.formEditorData">
	        <textarea id="${status.expression}" name="${status.expression}" class="inputbox w100 fullEditor">${status.value}</textarea>
            </spring:bind>
	    </div>
		</c:if>
		<c:if test="${not empty apprForm.fileDataList}">
			<div id="fileDownloadDiv" class="mt10"></div>
		</c:if>
		<c:if test="${apprForm.isApprAttach eq 1}">
			<div id="fileUploadDiv" class="mt10"></div>
		</c:if>
	</div>
	<!--//blockDetail End-->
</form>
<form id="editorFileUploadParameter" action="null" method="post"> 
    <spring:bind path="apprDoc.apprId">
    <input type="hidden" name="${status.expression}" value="${status.value}"/>
    </spring:bind>
    <input name="interceptorKey"  value="ikep4.system" type="hidden"/> 
</form>	     
<form id="listForm" name="listForm" method="post" action="<c:url value="/approval/work/apprWorkForm/listApprForm.do"/>">
    <spring:bind path="searchCondition.topFormParentId">
    <input type="hidden" name="${status.expression}" value="${status.value}" />
    </spring:bind>
    <spring:bind path="searchCondition.formParentId">
    <input type="hidden" name="${status.expression}" value="${status.value}" />
    </spring:bind>
    <spring:bind path="searchCondition.formId">
    <input type="hidden" name="${status.expression}" value="${status.value}" />
    </spring:bind>
    <spring:bind path="searchCondition.actionType">
    <input type="hidden" name="${status.expression}" value="${status.value}" />
    </spring:bind>
    <spring:bind path="searchCondition.sortColumn">
    <input type="hidden" name="${status.expression}" value="${status.value}" />
    </spring:bind>
    <spring:bind path="searchCondition.sortType">
    <input type="hidden" name="${status.expression}" value="${status.value}" />
    </spring:bind>
    <spring:bind path="searchCondition.usage">
    <input type="hidden" name="${status.expression}" value="${status.value}"/>
    </spring:bind>
    <spring:bind path="searchCondition.searchWord">
    <input type="hidden" name="${status.expression}" value="${status.value}"/>
    </spring:bind>
    <spring:bind path="searchCondition.pageIndex">
    <input type="hidden" name="${status.expression}" value="${status.value}"/>
    </spring:bind>
    <spring:bind path="searchCondition.pagePerRecord">
    <input type="hidden" name="${status.expression}" value="${status.value}"/>
    </spring:bind>
    <spring:bind path="searchCondition.linkType">
    <input type="hidden" name="${status.expression}" value="${status.value}"/>
    </spring:bind>
</form>   
</div>
<div class="blockBlank_10px"></div>
<div class="blockBlank_20px"></div>