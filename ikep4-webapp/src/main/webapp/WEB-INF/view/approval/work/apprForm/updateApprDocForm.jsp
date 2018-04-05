<%--
=====================================================
* 기능 설명 : 기안문 수정
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
<c:set var="preListMessage"     value="message.approval.apprForm.list" />
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
	
	var IS_MESSAGE_OPEN=true; // 협조공문 의견공개여부
	
	<c:if test="${!empty apprDoc.parentApprId}">//복사본
		IS_MESSAGE_OPEN=false;
	</c:if>
	<c:if test="${empty apprDoc.parentApprId}">//원본
		<c:if test="${apprDoc.apprDocType==1 && apprConfig.isMessageOpen=='0'}">
		IS_MESSAGE_OPEN=false;
		</c:if>	
	</c:if>	
	
	(function($) {
		// Role(직책)을 이용한 Default 결재선지정시 해당 사용자가 없는경우 체크 
		var checkDefLine	=	0;
		var checkRcvDefLine	=	0;
		
		//- onload시 수행할 코드
		$(document).ready(function() {
			
			// 메뉴선택
		    <c:choose>
				<c:when test="${apprDoc.listType eq 'tempList'}">
					$jq("#apprTempOfLeft").click();
				</c:when>
				<c:when test="${apprDoc.listType eq 'rejectList'}">
					$jq("#apprRejectOfLeft").click();
				</c:when>
				<c:when test="${apprDoc.listType eq 'listApprTodo'}">
					$jq("#apprTodoOfLeft").click();
				</c:when>
				<c:when test="${apprDoc.listType eq 'listApprIntegrate'}">
					$jq("#apprIntegrateOfLeft").click();
				</c:when>
				<c:when test="${apprDoc.listType eq 'listApprDeptIntegrate'}">
					$jq("#apprDeptIntegrateOfLeft").click();
				</c:when>
				<c:when test="${apprDoc.listType eq 'myRequestListComplete'}">
					$jq("#apprMyRequestCompleteOfLeft").click();
				</c:when>
			</c:choose>
		   
			//- validation
    	    var validOptions = {
                rules  : {
                    
                },
                messages : {
                    
                },    
                notice : {
                },      
    	        submitHandler : function(form) {
    	            var mode = $(form).find("input[name=mode]").val();
    	            
    	            if(mode!="content"){
        	            if($(form).find("input[name=apprLine]").val()=="" && $(form).find("input[name=apprDocStatus]").val()=="1"){
        	                alert("<ikep4j:message pre='${preLineMessage}' key='selectApproval' />");
        	                return;
        	            }
        	        }
    	            
    	            if(mode!="content"){
        	            // 결재 요청시 기안의견 팝업 호출
        	            if(!iFC.object.process && $(form).find("input[name=apprDocStatus]").val()=="1"){
        	                iFU.viewApprDocRegisterMessagePopUp();
        	                return false;
        	            }
    	            }else{
    	                // 변경사유 팝업 호출
    	                if(!iFC.object.process){
        	                iFU.viewApprDocChangeReasonPopUp();
        	                return false;
        	            }
    	            }
    	            
    	            var message =  "";
    	            
    	            if(mode=="all" || mode=="copy"){
    	                message = $(form).find('input[name=apprDocStatus]').val()=="0" || $(form).find('input[name=apprDocStatus]').val()=="4"?"<ikep4j:message pre='${preViewMessage}' key='save'/>":"<ikep4j:message pre='${preViewMessage}' key='apprRequest'/>";
    	            } else if(mode=="content"){
    	                message = "<ikep4j:message pre='${preListMessage}' key='edit'/>";
    	            }else{
    	                return false;
    	            }
    	                
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
    	                
    	                if(mode!="content"){
        	                 //- 참조자 저장
        	                iFU.setReferenceSet(form);
        	                
        	                //- 열람권한 저장
        	                iFU.setReadSet(form);
        	                
        	                //- 기결재 저장
    	                    iFU.setApprRefId(form);
    	                }
    	                
    	                $("body").ajaxLoadStart("button");
    	                if($("#apprDocForm textarea[name=formLayoutData]").val()!=""){
    	                    $(form).find("textarea[name=formData]").val(JSON.stringify(json));
    	                }
    	                
    	                // 재기안시
    	                if($(form).find("input[name=mode]").val()=="copy"){
    	                    $(form).find("input[name=apprId]").val("");
    	                    $(form).attr("action", "createApprDoc.do");
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
    		iFU.setUserInfo("${user.userId}", "${user.localeCode}", "${apprDoc.registerId}");
    		
    		//- initialize form
    		iFU.initializeApprDocForm("#apprDocForm", "edit");
    		
    		//- 버튼영역 실행
        	$("#formButtonDiv a").click(function(){
        	    switch (this.id) {
        	        
        	        // 저장
        	        case "updateApprDocTemporayButton":
        			    $("#apprDocForm").trigger("submit");
        			    break;
        			
        			// 결재자 지정
        			case "addApprLineButton":
        			    <c:if test="${apprDoc.mode eq 'copy'}">
        			    var apprId			=	"";
        			    </c:if>
        			    <c:if test="${apprDoc.mode ne 'copy'}">
        			    var apprId			=	$("#apprDocForm input[name=apprId]").val();
        			    </c:if>
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
        			    
                    // 문서 컨텐츠 저장
                    case "updateApprDocContentButton":
                        checkApprDocStatus(); 
                        break;
                        
                    // 취소
	                case "cancelApprDocButton":
	                    location.href = iKEP.getContextRoot() + "/approval/work/apprWorkDoc/viewApprDoc.do?apprId=" + $("#apprDocForm input[name=apprId]").val() + "&listType=" + $("#apprDocForm input[name=listType]").val() + "&entrustUserId=" + $("#apprDocForm input[name=entrustUserId]").val() + "&linkType=" + $("#apprDocForm input[name=linkType]").val();
                    
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
		    
		    //- 열람권한
		    <c:if test="${!empty apprReadGroupList || !empty apprReadUserList}">
		    initReadeList();
		    </c:if>
		    
		    //- 기결재 첨부
		    <c:if test="${!empty apprRelationsList}">
		    initRelationsList();
		    </c:if>
		    
		    // 연계 HTML
		    if($("#apprDocForm input[name=isLinkUrl]").val()=="1" && $("#apprDocForm input[name=linkDataType]").val()=="0" && $("#apprDocForm textarea[name=formLinkedData]").val()!=""){
		        $("#formLinkedHtmlDataDiv").html($("#apprDocForm textarea[name=formLinkedData]").val()).show();
		    }
		    
		    //- formLayout
    		if($("#apprDocForm textarea[name=formLayoutData]").val()!=""){
    		    iFC.object.formLayoutData =  $.parseJSON($("#apprDocForm textarea[name=formLayoutData]").val());
	            iFC.object.formData       =  $.parseJSON($("#apprDocForm textarea[name=formData]").val());
	            iFU.printForm();	
    		    
    		    //- rules 셋팅 
                if(iFC.object.rules!=""){
                    validOptions.rules = $jq.parseJSON("{" + iFC.object.rules + "}");
                }

                //- messages 셋팅
                if(iFC.object.messages!=""){
                    validOptions.messages = $jq.parseJSON("{" + iFC.object.messages + "}");
                }
    		}
    		
    		
	        //- formEditor
    		<c:if test="${apprForm.isApprEditor eq 1}">
    		    fullCkeditorConfig.height="250";
			    $("#formEditorData").ckeditor($jq.extend(fullCkeditorConfig, {"language" : "${user.localeCode}", "popupTitle" : "editor" }));
    	    </c:if> 
    	    
    	    //- file download
    		<c:if test="${not empty apprForm.fileDataList}"> 
    		var downloadOptions = {
    		   <c:if test="${not empty formFileDataListJson}">files : ${formFileDataListJson},</c:if>  
    		   isUpdate : false 
    		}; 
    		
    	    var fileDownloadController = new iKEP.FileController(null, "#fileDownloadDiv", downloadOptions); 
    	    </c:if> 
    	    
    	    //- file upload
    	    <c:if test="${apprForm.isApprAttach eq 1}"> 
    		var uploaderOptions = {
    		    // 재기안시 기첨부된 파일은 포함하지 않는다
    		 	<c:if test="${not empty docFileDataListJson && apprDoc.mode ne 'copy'}">files : ${docFileDataListJson},</c:if>
    			allowFileType : "all",
    	    	isUpdate : true
    	    };  
    	    
	        var fileUploadController = new iKEP.FileController("#apprDocForm", "#fileUploadDiv", uploaderOptions);
    	    </c:if> 
    	    
    	    //- validator mapping
            new iKEP.Validator("#apprDocForm", validOptions);
            
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
    	
    	//- 열람권한
		<c:if test="${!empty apprReadGroupList || !empty apprReadUserList}">
		initReadeList	=	function(){
    		// 결재문서의 결재선 정보 초기 설정
    		var items = [];
    		<c:forEach var="item" items="${apprReadGroupList}">	
    		items.push({// 공통필수
    			    type            : "group", 
	                code            : "${item.groupId}", 
	                name            : "${item.groupName}", 
	                parent          : "${item.parentGroupId}",
	                groupTypeId     : "${item.groupTypeId}",
	                searchSubFlag   : false
    		});	
    		</c:forEach>
    		
    		<c:forEach var="item" items="${apprReadUserList}">	
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
    		iFU.setReadAddress(items);
    	};
    	</c:if>	
    	
    	//- 기결재 첨부
		<c:if test="${!empty apprRelationsList}">
		initRelationsList	=	function(){
    		// 기결재 정보 초기 설정
    		var items = [];
    		<c:forEach var="item" items="${apprRelationsList}">	
    		items.push({
					apprId          : "${item.apprId}",
					apprTitle       : "${item.apprTitle}"
    		});	
    		</c:forEach>
    		iFU.setApprMyRequestComplete(items);
    	};
    	</c:if>	
        
        
        //- 결재라인
		<c:if test="${!empty apprLineList}">
		initApprLineList	=	function(){
    		// 결재문서의 결재선 정보 초기 설정
    		var items = [];
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
    			<c:if test="${apprDoc.mode eq 'copy'}">
    			apprStatus		:	0,
    			</c:if>
    			<c:if test="${apprDoc.mode ne 'copy'}">
    			apprStatus		:	${item.apprStatus},
    			</c:if>
    			lineModifyAuth	:	${item.lineModifyAuth},
    			docModifyAuth	:	${item.docModifyAuth},	
    			readModifyAuth	:	${item.readModifyAuth},	
    			<c:if test="${apprDoc.mode eq 'copy'}">
    			apprDate		:	"",
    			</c:if>
    			<c:if test="${apprDoc.mode ne 'copy'}">
    			apprDate		:	${item.apprStatus}>1?"<ikep4j:timezone pattern="MM.dd HH:mm" date="${item.apprDate}"/>":"",
    			</c:if>
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
    		var items = [];
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
    			approverType	:	${item.approverType},
    			apprOrder		:	${item.apprOrder},
    			<c:if test="${apprDoc.mode eq 'copy'}">
    			apprStatus		:	0,
    			</c:if>
    			<c:if test="${apprDoc.mode ne 'copy'}">
    			apprStatus		:	${item.apprStatus},
    			</c:if>
    			lineModifyAuth	:	${item.lineModifyAuth},
    			docModifyAuth	:	${item.docModifyAuth},	
    			readModifyAuth	:	${item.readModifyAuth},	
    			<c:if test="${apprDoc.mode eq 'copy'}">
    			apprDate		:	""
    			</c:if>
    			<c:if test="${apprDoc.mode ne 'copy'}">
    			apprDate		:	${item.apprStatus}>1?"<ikep4j:timezone pattern="MM.dd HH:mm" date="${item.apprDate}"/>":""
    			</c:if>
    		});	
    		</c:forEach>
    		iFU.setApprReceiveLine(items);
    	};
    	</c:if>    
    	
    	// 문서상태 체크
    	checkApprDocStatus = function(){
    	    $.post(iKEP.getContextRoot()+"/approval/work/apprWorkDoc/ajaxReadApprDoc.do", {apprId:$("#apprDocForm input[name=apprId]").val()})
        	.success(function(data) {
        	    if(data.apprDocStatus=="1"){
        	        $("#apprDocForm").trigger("submit");
        	    }else{
        	        alert("<ikep4j:message pre='${preViewMessage}' key='checkApprDocStatus'/>");
        	        $("body").ajaxLoadStart("button");
        	        location.href="<c:url value='/approval/work/apprlist/listApprTodo.do'/>?listType=listApprTodo";
        	    }
        	        	
        	})
        	.error(function(event, request, settings) { alert("error"); });	
    	};
	})(jQuery);  

//-->
</script>
<h1 class="none">contnet area</h1>

<!--pageTitle Start-->
<div id="pageTitle">
<c:choose>
    <c:when test="${apprDoc.mode eq 'copy' || (apprDoc.listType eq 'rejectList' && apprDoc.mode eq 'all')}">
	    <h2><ikep4j:message pre='${preView}' key='apprDocRetry'/></h2>
    </c:when>
    <c:otherwise>
        <h2><ikep4j:message pre='${preView}' key='apprDocEdit'/></h2>
    </c:otherwise>
</c:choose>            
</div>
<!--//pageTitle End-->

<div id="guideConFrame">
	<div class="blockBlank_10px"></div>
	<!--blockDetail Start-->
	<div id="formButtonDiv" class="blockButton"> 
		<ul>
	    <c:if test="${apprDoc.mode eq 'all'}">
	        <c:if test="${apprDoc.apprDocStatus eq 0}">
	        <li><a id="updateApprDocTemporayButton" class="button" href="#a"><span><ikep4j:message pre='${preButton}' key='saveApprDoc'/></span></a></li>
	        </c:if>
        </c:if>
        <c:if test="${apprDoc.mode eq 'content'}">
		    <li><a id="updateApprDocContentButton"         class="button" href="#a"><span><ikep4j:message pre='${preButton}' key='saveApprDoc'/></span></a></li>
        </c:if>
        <c:if test="${apprDoc.mode eq 'all' || apprDoc.mode eq 'copy'}">
            <li><a id="addApprLineButton"           class="button" href="#a"><span><ikep4j:message pre='${preButton}' key='addApprLine'/></span></a></li>
		    <li><a id="createApprDocButton"         class="button" href="#a"><span><ikep4j:message pre='${preButton}' key='createApprDoc'/></span></a></li>
        </c:if>
        <li><a id="cancelApprDocButton"           class="button" href="#a"><span><ikep4j:message pre='${preButton}' key='cancelApprDoc'/></span></a></li>
		</ul>
	</div>
	
	<c:if test="${apprConfig.lineViewType=='0'}"> 
	<!--  결재선 지정 -->
	<table width="100%" border="0" cellspacing="0" cellpadding="0">
        <tr>
            <td height="60" colspan="2" align="center" class="font_style1">${apprForm.formName}</td>
        </tr>
        <tr id="apprLine0Tr">
            <td colspan="2" align="right">
                <table border="0" cellspacing="0" cellpadding="0" class="table_style">
                    <tr id="apprLine0TTr">
                        <th width="25" height="100" rowspan="2" align="center" class="first"><ikep4j:message pre='${preView}' key='apprTh' htmlEscape='fasle'/></th>
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
	
	<div class="blockDetail">
    <form id="apprDocForm" name="apprDocForm" method="post" action="updateApprDoc.do">
        
        <!-- 시스템 -->
        <spring:bind path="apprForm.systemId">
        <input type="hidden" name="${status.expression}" value="${status.value}"/>
        </spring:bind>
        
        <!-- 폼아이디 -->
        <spring:bind path="apprDoc.formId">
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
        
        <!-- 문서아이디 -->
        <spring:bind path="apprDoc.apprId">
        <input type="hidden" name="${status.expression}" value="${status.value}"/>
        </spring:bind>
        
        <!-- 결재문서 식별번호 -->
        <spring:bind path="apprDoc.apprDocNo">
        <input type="hidden" name="${status.expression}" value="${status.value}"/>
        </spring:bind>
        
        <!-- 모드 -->
        <spring:bind path="apprDoc.mode">
        <input type="hidden" name="${status.expression}" value="${status.value}"/>
        </spring:bind>
        
        <!-- 문서버전 -->
        <spring:bind path="apprDoc.docVersion">
        <input type="hidden" name="${status.expression}" value="${status.value}"/>
        </spring:bind>
        
        <!-- 문서보안설정 -->
        <spring:bind path="apprForm.apprDocType">
        <input type="hidden" name="${status.expression}" value="${status.value}"/>
        </spring:bind>
        
        <!-- 양식 -->
        <spring:bind path="apprDoc.formLayoutData">
        <textarea name="${status.expression}" style="display:none;">${status.value}</textarea>
        </spring:bind>
        
        <!-- 연계 양식 -->
        <spring:bind path="apprDoc.formLinkedData">
        <textarea name="${status.expression}" style="display:none;">${status.value}</textarea>
        </spring:bind>
        
        <!-- 양식 연계여부 -->
        <spring:bind path="apprForm.isLinkUrl">
        <input type="hidden" name="${status.expression}" value="${status.value}"/>
        </spring:bind>
        
        <!-- 양식 데이터 타입 0:html, 1:json -->
        <spring:bind path="apprForm.linkDataType">
        <input type="hidden" name="${status.expression}" value="${status.value}"/>
        </spring:bind>
        
        <!-- 양식 데이터 -->
        <spring:bind path="apprDoc.formData">
        <textarea name="${status.expression}" style="display:none;">${status.value}</textarea>
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
        <input type="hidden" name="${status.expression}" value="${status.value}"/>
        </spring:bind>
        
        <!-- 수신처 사용유무 (0:사용안함,1:사용) -->
        <spring:bind path="apprDoc.isApprReceive">
        <input type="hidden" name="${status.expression}" value="0" />
        </spring:bind>
        
        <!-- 리스트 타입 -->
        <spring:bind path="apprDoc.listType">
        <input type="hidden" name="${status.expression}" value="${status.value}"/>
        </spring:bind>
        
        <!-- 링크 타입 -->
        <spring:bind path="apprDoc.linkType">
        <input type="hidden" name="${status.expression}" value="${status.value}"/>
        </spring:bind>
        
        <!-- 기안자 결재요청 시 등록 의견 -->
        <spring:bind path="apprDoc.registerMessage">
        <input type="hidden" name="${status.expression}" />
        </spring:bind>
        
        <!-- 변경사유 -->
        <spring:bind path="apprDoc.changeReason">
        <input type="hidden" name="${status.expression}" />
        </spring:bind>
        
         <!-- 기결재문서 첨부 -->
        <spring:bind path="apprDoc.apprRefId">
        <input type="hidden" name="${status.expression}"/>
        </spring:bind>
        
        <!-- 위임자 정보 -->
        <spring:bind path="apprDoc.entrustUserId">
        <input type="hidden" name="${status.expression}" value="${status.value}"/>
        </spring:bind>
        
		<table summary="기본정보">
			<caption></caption>
			<tbody>
			    <tr>
					<th width="15%" scope="row"><ikep4j:message pre='${preView}' key='apprNo'/></th>
					<td width="35%"></td>
					<th width="15%" scope="row"><ikep4j:message pre='${preView}' key='apprReqDate'/></th>
					<td width="35%">
					<c:choose>
                        <c:when test="${apprDoc.mode eq 'copy' || (apprDoc.listType eq 'rejectList' && apprDoc.mode eq 'all')}">
	                        ${today}
                        </c:when>
                        <c:otherwise>
                            <ikep4j:timezone pattern="yyyy-MM-dd" date="${apprDoc.apprReqDate}"/>
                        </c:otherwise>
                    </c:choose>      
			        </td>
				</tr>
				<tr>
					<th scope="row"><ikep4j:message pre='${preView}' key='registerName'/></th>
					<td>${apprDoc.registerName}</td>
					<th scope="row"><ikep4j:message pre='${preView}' key='apprGroupId'/></th>
					<td>
				<c:choose>
				    <c:when test="${apprDoc.mode ne 'content'}">    
				        <c:if test="${groupSize == 1}">
			            <!-- 기안 부서코드 -->
				            <spring:bind path="apprDoc.apprGroupId">
                                <input type="hidden" name="${status.expression}" value="${status.value}"/>
                            </spring:bind>
                        </c:if>
                        <c:if test="${groupSize > 1}">
                            <spring:bind path="apprDoc.apprGroupId">
                                <select name="${status.expression}" onchange="iFU.setGroupName(this);" title="<ikep4j:message pre='${preView}' key='${status.expression}'/>">
                                <c:forEach var="item" items="${groupList}">
                                    <option value="${item.groupId}" <c:if test="${item.groupId eq status.value}">selected="selected"</c:if>><c:if test="${user.localeCode eq 'ko'}">${item.groupName}</c:if><c:if test="${user.localeCode ne 'ko'}">${item.groupEnglishName}</c:if></option>    
                                </c:forEach>   
                                </select> 
                            </spring:bind>
                        </c:if>    
                    </c:when>  
    				<c:otherwise>  
    				    <spring:bind path="apprDoc.apprGroupId">
                            <input type="hidden" name="${status.expression}" value="${status.value}"/>
                        </spring:bind>
				        ${apprDoc.apprGroupName}
					</c:otherwise>
                </c:choose>	 
					<!-- 기안 부서명 -->
                    <spring:bind path="apprDoc.apprGroupName">
                        <input type="hidden" name="${status.expression}" value="${status.value}"/>
                    </spring:bind>    	    
					</td>
				</tr>
				<tr>
					<th scope="row"><ikep4j:message pre='${preView}' key='apprSecurity'/></th>
					<td>
                    <c:choose>
					    <c:when test="${(apprDoc.mode ne 'content') && apprForm.isApprSecurity eq 0}">
        					<spring:bind path="apprDoc.apprSecurityType">
        					    <input type="radio" class="radio" value="0" name="${status.expression}" <c:if test="${status.value eq 0}">checked="checked"</c:if> title="<ikep4j:message pre='${preView}' key='gerneral'/>" /> <ikep4j:message pre='${preView}' key='gerneral'/>
                                <input type="radio" class="radio" value="1" name="${status.expression}" <c:if test="${status.value eq 1}">checked="checked"</c:if> title="<ikep4j:message pre='${preView}' key='security'/>" /> <ikep4j:message pre='${preView}' key='security'/>
        				    </spring:bind>
    				    </c:when>  
    				    <c:otherwise>  
        					<spring:bind path="apprDoc.apprSecurityType">
            					<input type="hidden" name="${status.expression}" value="${status.value}" />
        					    <c:if test="${status.value eq 0}"><ikep4j:message pre='${preView}' key='gerneral'/></c:if>
    					        <c:if test="${status.value eq 1}"><ikep4j:message pre='${preView}' key='security'/></c:if>			    
        				    </spring:bind>
        			    </c:otherwise>
                    </c:choose>	 
				    </td>
					<th scope="row"><ikep4j:message pre='${preView}' key='apprPeriodCd'/></th>
					<td>
					<c:choose>
					    <c:when test="${(apprDoc.mode ne 'content') && apprForm.isApprPeriod eq 0}">
        					<spring:bind path="apprDoc.apprPeriodCd">
        					    <select name="${status.expression}">
            					    <option value=""><ikep4j:message pre='${preView}' key='select'/></option>
            					    <c:forEach var="item" items="${apprPeriodList}">
            					    <option value="${item.codeId}" <c:if test="${item.codeId eq status.value}">selected="selected"</c:if>>${item.codeName}</option>    
            					    </c:forEach>
            				    </select>    
        				    </spring:bind>
    				    </c:when>  
    				    <c:otherwise>  
        					<spring:bind path="apprDoc.apprPeriodCd">
        					<input type="hidden" name="${status.expression}" value="${status.value}" />
    					    <c:forEach var="item" items="${apprPeriodList}">
    					        <c:if test="${item.codeId eq status.value}">${item.codeName}</c:if>
    					    </c:forEach>    				    
        				    </spring:bind>
        			    </c:otherwise>
                    </c:choose>	     
					</td>
				</tr>
				<tr>
					<th scope="row"><ikep4j:message pre='${preView}' key='referenceId'/> <a id="referenceAddressButton" href="#a" <c:if test="${apprDoc.mode eq 'content'}">style="display:none"</c:if>><img src="/ikep4-webapp/base/images/icon/ic_btn_plus.gif" style="vertical-align:middle"></a></th>
					<td>
					    <c:if test="${apprDoc.mode eq 'content'}">
					    <div id="referenceDiv"></div>
					    </c:if>
					    <spring:bind path="apprDoc.referenceId">
    					<input type="hidden" name="${status.expression}" />
    					</spring:bind>
    					<spring:bind path="apprDoc.referenceSet">
    					<select name="${status.expression}" class="inputbox w100" size="3" multiple="multiple" <c:if test="${apprDoc.mode eq 'content'}">style="display:none"</c:if>></select>
    					</spring:bind>
				    </td>
					<th scope="row"><ikep4j:message pre='${preView}' key='readId'/> <a id="readAddressButton" href="#a" <c:if test="${apprDoc.mode eq 'content'}">style="display:none"</c:if>><img src="/ikep4-webapp/base/images/icon/ic_btn_plus.gif" style="vertical-align:middle"></a></th>
					<td>
					    <c:if test="${apprDoc.mode eq 'content'}">
					    <div id="readDiv"></div>
					    </c:if>
					    <spring:bind path="apprDoc.readId">
    					<input type="hidden" name="${status.expression}" />
    					</spring:bind>
    					<spring:bind path="apprDoc.readSet">
    					<select name="${status.expression}" class="inputbox w100" size="3" multiple="multiple" <c:if test="${apprDoc.mode eq 'content'}">style="display:none"</c:if>></select>
    					</spring:bind>
				    </td>
		        </tr>
		        <tr id="apprReceiveLineTr">
				    <th scope="row"><ikep4j:message pre='${preView}' key='apprReceive'/> <a id="addApprReceiveLineButton" href="#a" <c:if test="${apprDoc.mode eq 'content'}">style="display:none"</c:if>><img src="/ikep4-webapp/base/images/icon/ic_btn_plus.gif" style="vertical-align:middle" /></a></th>
					<td colspan="3" id="apprReceiveLineInfoDiv"></td>
				</tr>
				<tr>
				    <th scope="row"><ikep4j:message pre='${preView}' key='apprRefInfo'/> <a id="addApprMyRequestCompleteButton" href="#a" <c:if test="${apprDoc.mode eq 'content'}">style="display:none"</c:if>><img src="/ikep4-webapp/base/images/icon/ic_btn_plus.gif" style="vertical-align:middle"></a></th>
					<td colspan="3">
			            <ul id="apprRefInfoUl">
				        </ul>
			        </td>
				</tr>
				<tr>
				    <th scope="row"><ikep4j:message pre='${preView}' key='apprTitle'/></th>
					<td colspan="3">
					<spring:bind path="apprDoc.apprTitle">
		            <c:choose>
			        <c:when test="${(apprDoc.mode ne 'content') && apprForm.isApprTitle eq 0}">
    			        <input type="text" name="${status.expression}" value="${status.value}" class="inputbox w80" />
    			    </c:when>  
    		        <c:otherwise>  
    			        <input type="hidden" name="${status.expression}" value="${status.value}" />${status.value}
    			     </c:otherwise>
                    </c:choose>	 
    			    </spring:bind>    
			        </td>
				</tr>
				<c:if test="${not empty apprForm.formGuide}"> 
                <tr>
                    <th scope="row"><ikep4j:message pre='${preView}' key='formGuide'/></th>
			        <td colspan="3">
			            <% pageContext.setAttribute("newLineChar", "\n"); %>
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
            <spring:bind path="apprDoc.formEditorData">
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
</div>
<div class="blockBlank_10px"></div>
<div class="blockBlank_20px"></div>