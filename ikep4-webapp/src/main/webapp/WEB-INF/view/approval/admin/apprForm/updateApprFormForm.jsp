<%--
=====================================================
* 기능 설명 : 양식수정
* 작성자    : wonchu
=====================================================
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/base/common/taglibs.jsp"%>
<%@ include file="/base/common/fileUploadControll.jsp"%><%-- 파일업로드용 Import --%>    
<c:set var="user" value="${sessionScope['ikep.user']}" />    
<%-- 메시지 관련 Prefix 선언 Start --%> 
<c:set var="preHeader"      value="ui.approval.apprForm.header" /> 
<c:set var="preList"        value="ui.approval.apprForm.list" />
<c:set var="preView"        value="ui.approval.apprForm.view" />
<c:set var="preIfm"         value="ui.approval.apprForm.iFM" />    
<c:set var="preCode"        value="ui.approval.common.code" />
<c:set var="preButton"      value="ui.approval.common.button" />
<c:set var="preViewMessage" value="message.approval.apprForm.view" />
<c:set var="preIfmMessage"  value="message.approval.apprForm.iFM" />
<%-- 메시지 관련 Prefix 선언 End --%>
<link rel="stylesheet" type="text/css" href="<c:url value="/base/css/approval/admin/apprForm.css"/>"/>
<script type="text/javascript" src="<c:url value="/base/js/units/approval/admin/apprForm.js"/>"></script>
<script type="text/javascript" src="<c:url value="/base/js/ckeditor/ckeditor.js"/>"></script>
<script type="text/javascript" src="<c:url value="/base/js/ckeditor/adapters/jquery.js"/>"></script>     
<script type="text/javascript">
<!--// 

    iFC.item.text.name              = "<ikep4j:message pre='${preIfm}' key='item.text'/>";
    iFC.item.textarea.name          = "<ikep4j:message pre='${preIfm}' key='item.textarea'/>";
    iFC.item.select.name            = "<ikep4j:message pre='${preIfm}' key='item.select'/>";
    iFC.item.radio.name             = "<ikep4j:message pre='${preIfm}' key='item.radio'/>";
    iFC.item.checkbox.name          = "<ikep4j:message pre='${preIfm}' key='item.checkbox'/>";
    iFC.item.textbox.name           = "<ikep4j:message pre='${preIfm}' key='item.textbox'/>";
    iFC.item.embed.name             = "<ikep4j:message pre='${preIfm}' key='item.embed'/>";
    iFC.item.line.name              = "<ikep4j:message pre='${preIfm}' key='item.line'/>";
    iFC.textKind[0].text            = "<ikep4j:message pre='${preIfm}' key='textKind.all'/>";
    iFC.textKind[1].text            = "<ikep4j:message pre='${preIfm}' key='textKind.digits'/>";
    iFC.textKind[2].text            = "<ikep4j:message pre='${preIfm}' key='textKind.number'/>";
    iFC.textKind[3].text            = "<ikep4j:message pre='${preIfm}' key='textKind.numberComma'/>";
    iFC.textKind[4].text            = "<ikep4j:message pre='${preIfm}' key='textKind.date'/>";
    iFC.textKind[5].text            = "<ikep4j:message pre='${preIfm}' key='textKind.email'/>";
    iFC.textKind[6].text            = "<ikep4j:message pre='${preIfm}' key='textKind.phone'/>";
    iFC.textKind[7].text            = "<ikep4j:message pre='${preIfm}' key='textKind.zipcode'/>";    
    iFC.embedKind[0].text           = "<ikep4j:message pre='${preIfm}' key='embedKind.user'/>";
    iFC.embedKind[1].text           = "<ikep4j:message pre='${preIfm}' key='embedKind.group'/>";
    iFC.embedKind[2].text           = "<ikep4j:message pre='${preIfm}' key='embedKind.date'/>";
    iFC.bindData.userId.text        = "<ikep4j:message pre='${preIfm}' key='bindData.userId'/>";
    iFC.bindData.userName.text      = "<ikep4j:message pre='${preIfm}' key='bindData.userName'/>";
    iFC.bindData.jobTitleName.text  = "<ikep4j:message pre='${preIfm}' key='bindData.jobTitleName'/>";
    iFC.bindData.teamName.text      = "<ikep4j:message pre='${preIfm}' key='bindData.teamName'/>";

    iFC.message.removeAuthUser          = "<ikep4j:message pre='${preIfmMessage}' key='removeAuthUser'/>";
    iFC.message.notFoundUser            = "<ikep4j:message pre='${preIfmMessage}' key='notFoundUser'/>";
    iFC.message.initFormLayout          = "<ikep4j:message pre='${preIfmMessage}' key='initFormLayout'/>";
    iFC.message.allowMultiItem          = "<ikep4j:message pre='${preIfmMessage}' key='allowMultiItem'/>";
    iFC.message.initLinkData            = "<ikep4j:message pre='${preIfmMessage}' key='initLinkData'/>";
    iFC.message.requireLinkedData       = "<ikep4j:message pre='${preIfmMessage}' key='requireLinkedData'/>";
    
    iFC.message.itemDiv                 = "<ikep4j:message pre='${preIfm}' key='itemDiv'/>";
    iFC.message.lineDiv                 = "<ikep4j:message pre='${preIfm}' key='lineDiv'/>";
    iFC.message.preview                 = "<ikep4j:message pre='${preIfm}' key='preview'/>";
    iFC.message.newWindow               = "<ikep4j:message pre='${preIfm}' key='newWindow'/>";
    
    iFC.message.initialize              = "<ikep4j:message pre='${preIfm}' key='initialize'/>";
    iFC.message.template                = "<ikep4j:message pre='${preIfm}' key='template'/>";
    iFC.message.history                 = "<ikep4j:message pre='${preIfm}' key='history'/>";
    iFC.message.itemPopUp               = "<ikep4j:message pre='${preIfm}' key='itemPopUp'/>";
    iFC.message.lineItemPopUp           = "<ikep4j:message pre='${preIfm}' key='lineItemPopUp'/>";
    iFC.message.muliRowPopUp            = "<ikep4j:message pre='${preIfm}' key='muliRowPopUp'/>";
    iFC.message.categoryPopUp           = "<ikep4j:message pre='${preIfm}' key='categoryPopUp'/>";
    iFC.message.templatePopUp           = "<ikep4j:message pre='${preIfm}' key='templatePopUp'/>";
    iFC.message.histroyPopUp            = "<ikep4j:message pre='${preIfm}' key='histroyPopUp'/>";
    iFC.message.reasonPopUp             = "<ikep4j:message pre='${preIfm}' key='reasonPopUp'/>";
    iFC.message.linePopUp               = "<ikep4j:message pre='${preIfm}' key='linePopUp'/>";
    iFC.message.type                    = "<ikep4j:message pre='${preIfm}' key='type'/>";
    iFC.message.merged                  = "<ikep4j:message pre='${preIfm}' key='merged'/>";
    iFC.message.required                = "<ikep4j:message pre='${preIfm}' key='required'/>";
    iFC.message.hideTitle               = "<ikep4j:message pre='${preIfm}' key='hideTitle'/>";
    iFC.message.sort                    = "<ikep4j:message pre='${preIfm}' key='sort'/>";
    iFC.message.vertical                = "<ikep4j:message pre='${preIfm}' key='vertical'/>";
    iFC.message.horizontal              = "<ikep4j:message pre='${preIfm}' key='horizontal'/>";
    iFC.message.inputType               = "<ikep4j:message pre='${preIfm}' key='inputType'/>";
    iFC.message.width                   = "<ikep4j:message pre='${preIfm}' key='width'/>";
    iFC.message.height                  = "<ikep4j:message pre='${preIfm}' key='height'/>";
    iFC.message.max                     = "<ikep4j:message pre='${preIfm}' key='max'/>";
    iFC.message.length                  = "<ikep4j:message pre='${preIfm}' key='length'/>";
    iFC.message.data                    = "<ikep4j:message pre='${preIfm}' key='data'/>";
    iFC.message.user                    = "<ikep4j:message pre='${preIfm}' key='user'/>";
    iFC.message.code                    = "<ikep4j:message pre='${preIfm}' key='code'/>";
    iFC.message.header                  = "<ikep4j:message pre='${preIfm}' key='header'/>";
    iFC.message.object                  = "<ikep4j:message pre='${preIfm}' key='object'/>";
    iFC.message.yes                     = "<ikep4j:message pre='${preIfm}' key='yes'/>";
    iFC.message.no                      = "<ikep4j:message pre='${preIfm}' key='no'/>";
    iFC.message.title                   = "<ikep4j:message pre='${preIfm}' key='title'/>";
    iFC.message.linkedJson              = "<ikep4j:message pre='${preIfm}' key='linkedJson'/>";
    iFC.message.defaultValue            = "<ikep4j:message pre='${preIfm}' key='defaultValue'/>";
	(function($) {
	    
	
		//- onload시 수행할 코드
		$(document).ready(function() {
			
			// 메뉴선택
		    $("#listApprFormLinkOfLeft").click();
		    
		    //- info validation
    	    var validInfoOptions = {
                rules  : {
                    formParentName : {
                        required : true
                    },
                    formName : {
                        required : true
                    },
                    apprDocType : {
                        required : true
                    },
                    formDesc : {
                        required : true
                    },
                    apprPeriodCd : {
                        required : true
                    },
                    isApprPeriod : {
                        required : true
                    },
                    apprSecurityType : {
                        required : true
                    },
                    startDate : {
                        required : true
                    },
                    endDate : {
                        required : true
                    }
                },
                messages : {
                    formParentName : {
                        required : "<ikep4j:message pre='${preViewMessage}' key='required'/>"
                    },
                    formName : {
                        required : "<ikep4j:message pre='${preViewMessage}' key='required'/>"
                    },
                    apprDocType : {
                        required : "<ikep4j:message pre='${preViewMessage}' key='required'/>"
                    },
                    formDesc : {
                        required : "<ikep4j:message pre='${preViewMessage}' key='required'/>"
                    },
                    apprPeriodCd : {
                        required : "<ikep4j:message pre='${preViewMessage}' key='required'/>"
                    },
                    isApprPeriod : {
                        required : "<ikep4j:message pre='${preViewMessage}' key='required'/>"
                    },
                    apprSecurityType : {
                        required : "<ikep4j:message pre='${preViewMessage}' key='required'/>"
                    },
                    isApprSecurity : {
                        required : "<ikep4j:message pre='${preViewMessage}' key='required'/>"
                    },
                    startDate : {
                        required : "<ikep4j:message pre='${preViewMessage}' key='required'/>"
                    },
                    endDate : {
                        required : "<ikep4j:message pre='${preViewMessage}' key='required'/>"
                    }
                },    
                notice : {
                },      
    	        submitHandler : function(form) {
    	            if(confirm("<ikep4j:message pre='${preViewMessage}' key='save'/>")){
    	                $("body").ajaxLoadStart("button");
    	                
    	                //- 참조자 저장
    	                iFU.setReferenceSet("#apprInfoForm");
    	                
    	                // 사용기간
    	                if($(form).find("input[name=periodUsage]:checked").val()=="1"){
    	                    $(form).find("input[name=startDateStr]").val($(form).find("input[name=startDate]").val());
    	                    $(form).find("input[name=endDateStr]").val($(form).find("input[name=endDate]").val());
    	                }else{
    	                    $(form).find("input[name=startDateStr]").remove();
    	                    $(form).find("input[name=endDateStr]").remove();
    	                }
    	                
    	                
    	                $.ajax({
        					url  : "<c:url value='/approval/admin/apprAdminForm/updateApprInfoForm.do'/>",
        					data : $(form).serialize(),
        					type : "post",
        					success : function(result) {
        			            if(result=="true"){
        			                $jq("#listForm").attr("action", "updateApprFormForm.do");
                                    $jq("#listForm").submit();
        			            }else{
        			                $("body").ajaxLoadComplete();
        			                alert("error");
        			            }
        			            
        					},
        					error : function(xhr, exMessage) {
        					    $("body").ajaxLoadComplete();
        						var errorItems = $.parseJSON(xhr.responseText).exception;
        						validator.showErrors(errorItems);
        					}
        				});
    				
    	            }else{
    	                return false;
    	            }
                }
    		}; 
    		
    		//- 사용자 정보
    		iFU.setUserInfo("${user.userId}", "${user.localeCode}");
    		
    		//- 결재 정보 초기작업
    		iFU.initializeApprInfoForm("#apprInfoForm", "edit");
    		
            //- 참조자
            var $referenceSet = $("#apprInfoForm select[name=referenceSet]").children().remove().end();
    		<c:forEach var="item" items="${apprReferenceList}">
				var item = {
					type            : "user",
					empNo           : "",
					id              : "${item.userId}",
					userName        : "${item.userName}",
					jobTitleName    : "${item.jobTitleName}",
					groupId         : "${item.groupId}",
					teamName        : "${item.teamName}",
					email           : "${item.mail}",
					mobile          : "",
					searchSubFlag   : false
				};

				$.tmpl(iKEP.template.userOption, item).appendTo($referenceSet).data("data", item);    			
    		</c:forEach>
            
            //- 참조자 카운트
            iFU.setSelectOptionCount("referenceSet", "#referenceSetSpan");
            
            //- validator mapping
            new iKEP.Validator("#apprInfoForm", validInfoOptions);
    	    
            // 결재기간변경 - rules를 제어하기 때문에 위치 변경 못함
            if($("#apprInfoForm input[name=periodUsage]:checked").val()=="0"){
                $("#apprInfoForm input[name=periodUsage]:checked").click();
            }else{
                $("#periodUsageSpan").show();
            }
    	    
		    //- validator mapping
    	    var validContentOptions = {
                rules  : {
                    linkUrl : {
                        required : true
                        //,url      : true
                    }
                },
                messages : {
                    linkUrl : {
                        required : "연계주소를 입력해주세요"
                        //,url      : "주소가 올바르지 않습니다"
                    }
                    
                },    
                notice : {
                },      
    	        submitHandler : function(form) {
    	            if($(form).find("textarea[name=formLayoutData]").val()=="" && $(form).find("input[name=isApprEditor]:checked").val()=="0"){
    	                alert("<ikep4j:message pre='${preViewMessage}' key='allowContent'/>");
    	                return false;
    	            }
    	            
    	            // 변경사유 팝업 호출
    	            if(!iFC.object.process){
    	                if($(form).find("input[name=isNewVersion]:checked").val()=="1" && $(form).find("input[name=formVersion]").val()!="0"){
    	                    iFU.viewApprFormChangeReasonPopUp();
    	                    return false;
    	                }
    	            }
    	            
    	            var msg = "";
    	            
    	            if($(form).find("input[name=isNewVersion]:checked").val()=="1" && $(form).find("input[name=formVersion]").val()!="0"){
    	                msg = "<ikep4j:message pre='${preViewMessage}' key='newVersion'/>\n\n";
    	            }else if($(form).find("input[name=isNewVersion]:checked").val()=="0"){
    	                msg = "<ikep4j:message pre='${preViewMessage}' key='editVersion'/>\n\n";
    	            }
    	            
    	            if(confirm(msg + "<ikep4j:message pre='${preViewMessage}' key='save'/>")){
    	                
    	                fileController.upload(function(isSuccess, files) {
							if(isSuccess === true) { 
								$("body").ajaxLoadStart("button");
								
								//- formLayout 초기화(layout form 객체 삭제)
    	                        $("#formLayoutDiv").empty();
    	                        
								var editor = $("#formEditorData").ckeditorGet();
                                editor.updateElement();
                                
                                //에디터 내 이미지 파일 링크 정보 세팅
					            createEditorFileLink($(form).attr("id"));
    	                        
								
								// 저장
								$.ajax({
                					url  : "<c:url value='/approval/admin/apprAdminForm/updateApprContentForm.do'/>",
                					data : $(form).serialize(),
                					type : "post",
                					success : function(result) {
                			            if(result=="true"){
                			                $jq("#listForm").attr("action", "updateApprFormForm.do");
                                            $jq("#listForm").submit();
                			            }else{
                			                $("body").ajaxLoadComplete();
                			                alert("error");
                			            }
                			            
                					},
                					error : function(xhr, exMessage) {
                					    $("body").ajaxLoadComplete();
                						var errorItems = $.parseJSON(xhr.responseText).exception;
                						validator.showErrors(errorItems);
                					}
                				});
								
							}
						});
    	                
    	            }else{
    	                return false;
    	            }
                }
    		};	      
    		
    		//- 양식내용 초기 작업
    		iFU.initializeApprContentForm("#apprContentForm");
    		
    		//- upload option
    		var uploaderOptions = {
    		 	<c:if test="${empty fileDataListJson}">files : [],</c:if> 
    		 	<c:if test="${not empty fileDataListJson}">files : ${fileDataListJson},</c:if> 
    			allowFileType : "all",
    	    	isUpdate : true
    	    };  
    	    
    	    //- fileController
	        var fileController = new iKEP.FileController("#apprContentForm", "#fileUploadDiv", uploaderOptions);
	        
	        
            //- validator mapping
            new iKEP.Validator("#apprContentForm", validContentOptions);
            
            // 연계사용여부 - rules를 제어하기 때문에 위치 변경 못함
            if($("#apprContentForm input[name=isLinkUrl]:checked").val()=="0"){
                $("#apprContentForm input[name=isLinkUrl]:checked").click();
            }else{
                $(".isLinkUrl").css("border-right-color", "#e0e0e0");
                $(".isLinkUrl0").hide();
                $(".isLinkUrl1").show();
                $("#apprContentForm").find("input[name=linkUrl]")[0].focus();
            }
        });    
       
	})(jQuery);  

	// ifrmae resize
	function resizeFrame(obj){ 
	    $jq("#" + $jq(obj).parent().attr("id")).ajaxLoadComplete();
	    obj.style.height = $jq(obj).contents().height() + "px";
	}
//-->
</script>
<h1 class="none">contnet area</h1>

<!--pageTitle Start-->
<div id="pageTitle">
	<h2><ikep4j:message pre='${preHeader}' key='title.update'/></h2>
</div>
<!--//pageTitle End-->

<div id="guideConFrame" style="width:900px;">
	<!--tab Start-->		
	<div id="formTabDiv" class="iKEP_tab">
	    <ul>
	        <li><a href="#tabs-1"><ikep4j:message pre='${preView}' key='tabOne'/></a></li>
	        <li><a href="#tabs-2"><ikep4j:message pre='${preView}' key='tabTwo'/></a></li>
	    </ul>
	    <div class="tab_con">
	    
	    	<!--양식 기본정보-->
	        <div id="tabs-1">
	        <form id="apprInfoForm" name="apprInfoForm" method="post" action="updateApprInfoForm.do">
	            <spring:bind path="apprForm.formId">
                <input type="hidden" name="${status.expression}" value="${status.value}"/>
                </spring:bind>
                <spring:bind path="apprForm.formVersion">
                <input type="hidden" name="${status.expression}" value="${status.value}"/>
                </spring:bind>
	        	<div class="blockBlank_10px"></div>
				
				<!--subTitle_2 Start-->
				<div class="subTitle_2 noline">
					<h3><ikep4j:message pre='${preView}' key='basicInfo'/><span class="colorPoint">*</span></h3>
				</div>
				<!--//subTitle_2 End-->
				
	        	<!--blockDetail Start-->
				<div class="blockDetail">
					<table summary="<ikep4j:message pre='${preView}' key='basicInfo'/>">
						<caption></caption>
						<tbody>
						    <tr>
						        <th width="15%" scope="row"><ikep4j:message pre='${preView}' key='systemId'/></th>
						        <td width="35%">
						        <spring:bind path="apprForm.systemId">
									<select name="${status.expression}" title="<ikep4j:message pre='${preView}' key='${status.expression}'/>">
									    <c:forEach var="item" items="${apprSystemList}">
									    <option value="${item.systemId}" <c:if test="${item.systemId eq status.value}">selected="selected"</c:if>>${item.systemName}</option>    
									    </c:forEach>
								    </select>
							    </spring:bind> 
						        </td>
								<th width="15%" scope="row"><ikep4j:message pre='${preView}' key='formParentName'/></th>
								<td width="35%"><div>
								    <spring:bind path="apprForm.formParentId">
								    <input type="hidden" name="${status.expression}" value="${status.value}" />
									</spring:bind>
									<spring:bind path="apprForm.formParentName">
									<input type="text"   name="${status.expression}" value="${status.value}" class="inputbox w20" readonly="readonly" title="<ikep4j:message pre='${preView}' key='formParentName'/>" />
									</spring:bind>
                                    <a id="categoryButton" class="button_ic" href="#a"><span><img src="<c:url value="/base/images/icon/ic_btn_subgroup.gif"/>" alt="">Category</span></a>
								</div></td>
							</tr>
							<tr>
								<th width="15%" scope="row"><ikep4j:message pre='${preList}' key='formName'/></th>
								<td width="35%"><div>
								    <spring:bind path="apprForm.formName">
									<input type="text" name="${status.expression}" value="${status.value}" class="inputbox w100" title="<ikep4j:message pre='${preList}' key='${status.expression}'/>"/>
									</spring:bind>
    							</div></td>
					            <th scope="row" width="15%"><ikep4j:message pre='${preList}' key='apprDocType'/></th>
								<td width="35%">
								    <spring:bind path="apprForm.apprDocType">
									<input type="radio" name="${status.expression}" class="radio" value="0" <c:if test="${status.value eq 0}">checked="checked"</c:if> title="<ikep4j:message pre='${preList}' key='apprDocType0'/>" /> <ikep4j:message pre='${preList}' key='apprDocType0'/>
                                    <input type="radio" name="${status.expression}" class="radio" value="1" <c:if test="${status.value eq 1}">checked="checked"</c:if> title="<ikep4j:message pre='${preList}' key='apprDocType1'/>" /> <ikep4j:message pre='${preList}' key='apprDocType1'/>
                                    </spring:bind>
								</td>
			                </tr>
					            <th scope="row" width="15%"><ikep4j:message pre='${preView}' key='usage'/></th>
								<td width="35%">
								    <spring:bind path="apprForm.usage">
									<input type="radio" name="${status.expression}" class="radio" value="1" <c:if test="${status.value eq 1}">checked="checked"</c:if> title="<ikep4j:message pre='${preCode}' key='use'/>" /> <ikep4j:message pre='${preCode}' key='use'/>
                                    <input type="radio" name="${status.expression}" class="radio" value="0" <c:if test="${status.value eq 0}">checked="checked"</c:if> title="<ikep4j:message pre='${preCode}' key='unuse'/>" /> <ikep4j:message pre='${preCode}' key='unuse'/>
                                    </spring:bind>
								</td>
								<th scope="row" width="15%"><ikep4j:message pre='${preView}' key='periodUsage'/></th>
								<td width="35%">
								    <spring:bind path="apprForm.periodUsage">
									<input type="radio" name="${status.expression}" class="radio" value="1" <c:if test="${status.value eq 1}">checked="checked"</c:if> title="<ikep4j:message pre='${preCode}' key='use'/>" /> <ikep4j:message pre='${preCode}' key='use'/>
                                    <input type="radio" name="${status.expression}" class="radio" value="0" <c:if test="${status.value eq 0}">checked="checked"</c:if> title="<ikep4j:message pre='${preCode}' key='unuse'/>" /> <ikep4j:message pre='${preCode}' key='unuse'/>
                                    </spring:bind>
                                    <span id="periodUsageSpan" class="none"><br />
                                        <spring:bind path="apprForm.startDateStr">
									    <input type="hidden" name="${status.expression}" value="" />
									    </spring:bind>
                                        <spring:bind path="apprForm.endDateStr">
									    <input type="hidden" name="${status.expression}" value="" />
									    </spring:bind>
                                        
                                        <spring:bind path="apprForm.startDate">
                                        <input type="text" class="inputbox" id="${status.expression}" name="${status.expression}" title="<ikep4j:message pre='${preView}' key='startDate'/>" value="<ikep4j:timezone pattern="yyyy.MM.dd" date="${apprForm.startDate}"/>" size="10" /> <img class="dateicon" src="/ikep4-webapp/base/images/icon/ic_cal.gif" alt="calendar" /> ~
									    </spring:bind>
									    <spring:bind path="apprForm.endDate">
									    <input type="text" class="inputbox" id="${status.expression}" name="${status.expression}" title="<ikep4j:message pre='${preView}' key='endDate'/>" value="<ikep4j:timezone pattern="yyyy.MM.dd" date="${apprForm.endDate}"/>" size="10" /> <img class="dateicon" src="/ikep4-webapp/base/images/icon/ic_cal.gif" alt="calendar" />
									    </spring:bind>
                                    </span>
								</td>
							</tr>
							<tr>
								<th scope="row"><ikep4j:message pre='${preView}' key='formDesc'/></th>
								<td colspan="3"><div>
								    <spring:bind path="apprForm.formDesc">
									<textarea name="${status.expression}" class="inputbox w100" rows="3" title="<ikep4j:message pre='${preView}' key='${status.expression}'/>">${status.value}</textarea>
									</spring:bind>
								</div></td>
							</tr>
						</tbody>
					</table>
				</div>
				<!--//blockDetail End-->
				
				<!--subTitle_2 Start-->
				<div class="subTitle_2 noline">
					<h3><ikep4j:message pre='${preView}' key='apprConfig'/><span class="colorPoint">*</span></h3>
				</div>
				<!--//subTitle_2 End-->
				
	        	<!--blockDetail Start-->
				<div class="blockDetail">
					<table summary="<ikep4j:message pre='${preView}' key='apprConfig'/>">
						<caption></caption>
						<tbody>
							<tr>
								<th width="15%" scope="row"><ikep4j:message pre='${preView}' key='isApprTitle'/></th>
								<td width="35%">
								    <spring:bind path="apprForm.isApprTitle">
									<input type="radio" name="${status.expression}" class="radio" value="1" <c:if test="${status.value eq 1}">checked="checked"</c:if> title="<ikep4j:message pre='${preCode}' key='use'/>" /> <ikep4j:message pre='${preCode}' key='use'/>
                                    <input type="radio" name="${status.expression}" class="radio" value="0" <c:if test="${status.value eq 0}">checked="checked"</c:if> title="<ikep4j:message pre='${preCode}' key='unuse'/>" /> <ikep4j:message pre='${preCode}' key='unuse'/>
                                    </spring:bind>
								</td>
								<th width="15%" scope="row"><ikep4j:message pre='${preView}' key='isApprAttach'/></th>
								<td width="35%">
								    <spring:bind path="apprForm.isApprAttach">
    							    <input type="radio" class="radio" name="${status.expression}" value="1" <c:if test="${status.value eq 1}">checked="checked"</c:if> title="<ikep4j:message pre='${preCode}' key='use'/>" /> <ikep4j:message pre='${preCode}' key='use'/>
                                    <input type="radio" class="radio" name="${status.expression}" value="0" <c:if test="${status.value eq 0}">checked="checked"</c:if> title="<ikep4j:message pre='${preCode}' key='unuse'/>" /> <ikep4j:message pre='${preCode}' key='unuse'/>
								    </spring:bind>
								</td>
							</tr>
							<tr>
								<th width="15%" scope="row"><ikep4j:message pre='${preView}' key='apprPeriodCd'/></th>
								<td width="35%">
								    <spring:bind path="apprForm.apprPeriodCd">
									<select name="${status.expression}" title="<ikep4j:message pre='${preView}' key='${status.expression}'/>">
									    <option value="">선택</option>
									    <c:forEach var="item" items="${apprPeriodList}">
									    <option value="${item.codeId}" <c:if test="${item.codeId eq status.value}">selected="selected"</c:if>>${item.codeName}</option>    
									    </c:forEach>
								    </select>
								    </spring:bind>
								</td>
								<th width="15%" scope="row"><ikep4j:message pre='${preView}' key='isApprPeriod'/></th>
								<td width="35%">
								    <spring:bind path="apprForm.isApprPeriod">
    							    <input type="radio" class="radio" name="${status.expression}" value="0" <c:if test="${status.value eq 0}">checked="checked"</c:if> title="<ikep4j:message pre='${preCode}' key='user'/>"> <ikep4j:message pre='${preCode}' key='user'/>
                                    <input type="radio" class="radio" name="${status.expression}" value="1" <c:if test="${status.value eq 1}">checked="checked"</c:if> title="<ikep4j:message pre='${preCode}' key='admin'/>"> <ikep4j:message pre='${preCode}' key='admin'/>
								    </spring:bind>
								</td>
							</tr>
							<tr>
								<th scope="row"><ikep4j:message pre='${preView}' key='apprSecurityType'/></th>
								<td>
								    <spring:bind path="apprForm.apprSecurityType">
								        <input type="radio" class="radio" value="0" name="${status.expression}" <c:if test="${status.value eq 0}">checked="checked"</c:if> title="<ikep4j:message pre='${preView}' key='gerneral'/>"> <ikep4j:message pre='${preView}' key='gerneral'/>
                                        <input type="radio" class="radio" value="1" name="${status.expression}" <c:if test="${status.value eq 1}">checked="checked"</c:if> title="<ikep4j:message pre='${preView}' key='security'/>"> <ikep4j:message pre='${preView}' key='security'/>
								    </spring:bind>
								</td>
								<th scope="row"><ikep4j:message pre='${preView}' key='isApprSecurity'/></th>
								<td>
								    <spring:bind path="apprForm.isApprSecurity">
    							    <input type="radio" class="radio" value="0" name="${status.expression}" <c:if test="${status.value eq 0}">checked="checked"</c:if> title="<ikep4j:message pre='${preCode}' key='user'/>"> <ikep4j:message pre='${preCode}' key='user'/>
                                    <input type="radio" class="radio" value="1" name="${status.expression}" <c:if test="${status.value eq 1}">checked="checked"</c:if> title="<ikep4j:message pre='${preCode}' key='admin'/>"> <ikep4j:message pre='${preCode}' key='admin'/>
								    </spring:bind>
								</td>
							</tr>
						</tbody>
					</table>
				</div>
				<!--//blockDetail End-->
				
				<!--subTitle_2 Start-->
				<div class="subTitle_2 noline" style="position:relative">
					<h3><ikep4j:message pre='${preView}' key='apprLineSet'/><span class="colorPoint">*</span></h3>
					<a class="button_ic valign_bottom" href="#a" onclick="iFU.addLinePopUp();" style="position:absolute;top:-4px;right:6px;"><span><img src="<c:url value="/base/images/icon/ic_btn_assign.gif"/>" alt="" style="vertical-align:middle;"><ikep4j:message pre='${preView}' key='apprLineSet'/></span></a>
				</div>
				<!--//subTitle_2 End-->
				
	        	<!--blockDetail Start-->
				<div class="blockDetail">
					<table summary="<ikep4j:message pre='${preView}' key='apprLineSet'/>">
						<caption></caption>
						<tbody>
						    <tr id="isDefLineUpdateTr">
								<th width="15%" scope="row"><ikep4j:message pre='${preView}' key='isDefLineUpdate'/></th>
								<td width="85%"><div>
								    <spring:bind path="apprForm.isDefLineUpdate">
    							    <input type="radio" class="radio" name="${status.expression}" value="1" <c:if test="${status.value eq 1}">checked="checked"</c:if> title="<ikep4j:message pre='${preCode}' key='use'/>" /> <ikep4j:message pre='${preCode}' key='use'/>
                                    <input type="radio" class="radio" name="${status.expression}" value="0" <c:if test="${status.value eq 0}">checked="checked"</c:if> title="<ikep4j:message pre='${preCode}' key='unuse'/>" /> <ikep4j:message pre='${preCode}' key='unuse'/>
								    </spring:bind>
							    </div></td>
				            </tr>
							<tr>
								<th width="15%" scope="row"><ikep4j:message pre='${preView}' key='defLineSet'/></th>
								<td width="85%"><div>
								    <spring:bind path="apprForm.defLineUse">
								    <input type="hidden"  name="${status.expression}" value="${status.value}" />
								    </spring:bind>
								    <spring:bind path="apprForm.defLineId">
								    <input type="hidden"  name="${status.expression}" value="${status.value}" />
								    </spring:bind>
								    <spring:bind path="apprForm.defLineSet">
									<select id="${status.expression}" name="${status.expression}" class="inputbox w100" size="7" title="<ikep4j:message pre='${preView}' key='${status.expression}'/>"">
							            <option value=""><ikep4j:message pre='${preView}' key='blankDefLineSet'/></option>
							        </select>
							        <div id="dfLineDiv" style="margin-top:10px;"></div>
									</spring:bind>
								</div></td>
							</tr>
						</tbody>
					</table>
				</div>
				
				<!--subTitle_2 Start-->
				<div class="subTitle_2 noline">
					<h3><ikep4j:message pre='${preView}' key='addInfo'/></h3>
				</div>
				<!--//subTitle_2 End-->
				
	        	<!--blockDetail Start-->
				<div class="blockDetail">
					<table summary="<ikep4j:message pre='${preView}' key='addInfo'/>">
						<caption></caption>
						<tbody>
							
							<tr>
								<th width="15%" scope="row"><ikep4j:message pre='${preView}' key='authUse'/></th>
								<td width="85%">
								    <spring:bind path="apprForm.authUse">
    							    <input type="radio" class="radio" name="${status.expression}" value="1" <c:if test="${status.value eq 1}">checked="checked"</c:if> title="<ikep4j:message pre='${preCode}' key='use'/>"> <ikep4j:message pre='${preCode}' key='use'/>
                                    <input type="radio" class="radio" name="${status.expression}" value="0" <c:if test="${status.value eq 0}">checked="checked"</c:if> title="<ikep4j:message pre='${preCode}' key='unuse'/>"> <ikep4j:message pre='${preCode}' key='unuse'/>
								    </spring:bind>
								    <div id="authUserDiv">
    									<spring:bind path="apprForm.authUserId">
    									<input type="hidden" name="${status.expression}" value="${status.value}" />
    									</spring:bind>
    									<spring:bind path="apprForm.authUserName">
    									<input type="text" id="${status.expression}" name="${status.expression}" value="${status.value}" class="inputbox w20" />
    									</spring:bind>
    									<a id="authUserSearchButton"  class="button_ic" href="#a"><span><img src="<c:url value="/base/images/icon/ic_btn_search.gif"/>" alt="">Search</span></a>
    									<a id="authUserAddressButton" class="button_ic" href="#a"><span><img src="<c:url value="/base/images/icon/ic_btn_address.gif"/>" alt="">Address</span></a>
    									<a id="authUserDeleteButton"  class="button_ic" href="#a"><span><img src="<c:url value="/base/images/icon/ic_btn_delete.gif"/>" alt="">Delete</span></a>
								    </div>
								</td>
							</tr>
							
							<tr>
								<th width="15%" scope="row"><ikep4j:message pre='${preView}' key='referenceId'/></th>
								<td width="85%">
								    <spring:bind path="apprForm.referenceId">
									<input type="hidden" name="${status.expression}" />
									</spring:bind>
									<spring:bind path="apprForm.referenceName">
									<input type="text" name="${status.expression}" value="" class="inputbox w20" style="vertical-align:bottom;" />
									</spring:bind>
									<a id="referenceSearchButton" class="button_ic" href="#a"><span><img src="<c:url value="/base/images/icon/ic_btn_search.gif"/>" alt="">Search</span></a>
									<a id="referenceAddressButton" class="button_ic" href="#a"><span><img src="<c:url value="/base/images/icon/ic_btn_address.gif"/>" alt="">Address</span></a>
									<div class="blockBlank_5px"></div>
									<spring:bind path="apprForm.referenceSet">
									<select name="${status.expression}" class="inputbox w50" size="5" multiple="multiple"></select>
									</spring:bind>
									<a id="referenceDeleteButton" class="button_ic" href="#a" style="vertical-align:bottom"><span><img src="<c:url value="/base/images/icon/ic_btn_delete.gif"/>" alt="">Delete</span></a> <span style="vertical-align:bottom;">&nbsp;&nbsp;(total <span id="referenceSetSpan" style="vertical-align:bottom;">0</span>)</span>
								</td>
							</tr>
						</tbody>
					</table>
				</div>
				<div id="formButtonDiv" class="blockButton"> 
    				<ul>
    					<li><a id="saveButton"      class="button" href="#a"><span><ikep4j:message pre='${preButton}' key='save'/></span></a></li>
    					<li><a id="listButton" 	    class="button" href="#a"><span><ikep4j:message pre='${preButton}' key='list'/></span></a></li>
    				</ul>
    			</div>
				<!--//blockDetail End-->
	        </form>
	        </div>
	    
	        <!--양식 상세정보-->
	        <div id="tabs-2">
	        <form id="apprContentForm" name="apprContentForm" method="post" action="updateApprContentForm.do">    
	            <spring:bind path="apprForm.formId">
                <input type="hidden" name="${status.expression}" value="${status.value}"/>
                </spring:bind>	            
                <spring:bind path="apprForm.formVersion">
                <input type="hidden" name="${status.expression}" value="${status.value}"/>
                </spring:bind>
	            <spring:bind path="apprForm.formLayoutData">
                <textarea name="${status.expression}" style="display:none;">${status.value}</textarea>
                <textarea name="${status.expression}Temp" style="display:none;">${status.value}</textarea>
                </spring:bind>
                <!-- 변경사유 -->
                <spring:bind path="apprForm.changeReason">
                <input type="hidden" name="${status.expression}" />
                </spring:bind>
	            <div class="blockDetail Approval_tb mt5">
    	            <table summary="form">
    					<caption></caption>
    					<tbody>
    					    <tr>
    							<th width="15%" scope="row"><ikep4j:message pre='${preView}' key='newVersion'/></th>
    							<td width="35%">
    							    <spring:bind path="apprForm.isNewVersion">
    							    <input type="radio" class="radio" name="${status.expression}" value="1" title="<ikep4j:message pre='${preView}' key='create'/>" /> <ikep4j:message pre='${preView}' key='create'/>
                                    <input type="radio" class="radio" name="${status.expression}" value="0" title="<ikep4j:message pre='${preView}' key='notcreate'/>" /> <ikep4j:message pre='${preView}' key='notcreate'/>
                                    </spring:bind>
    							</td>
    							<th width="15%" scope="row"><ikep4j:message pre='${preView}' key='editor'/></th>
    							<td width="35%">
    							    <spring:bind path="apprForm.isApprEditor">
    							    <input type="radio" class="radio" name="${status.expression}" value="1" <c:if test="${status.value eq 1}">checked="checked"</c:if> title="<ikep4j:message pre='${preCode}' key='use'/>" /> <ikep4j:message pre='${preCode}' key='use'/>
                                    <input type="radio" class="radio" name="${status.expression}" value="0" <c:if test="${status.value eq 0}">checked="checked"</c:if> title="<ikep4j:message pre='${preCode}' key='unuse'/>" /> <ikep4j:message pre='${preCode}' key='unuse'/>
                                    </spring:bind>
    							</td>
    						</tr>
    						<tr>
    							<th width="15%" scope="row"><ikep4j:message pre='${preView}' key='isLinkUrl'/></th>
    							<td width="35%" class="isLinkUrl">
    							    <spring:bind path="apprForm.isLinkUrl">
    							    <input type="radio" class="radio" name="${status.expression}" value="1" <c:if test="${status.value eq 1}">checked="checked"</c:if> title="<ikep4j:message pre='${preCode}' key='use'/>" /> <ikep4j:message pre='${preCode}' key='use'/>
                                    <input type="radio" class="radio" name="${status.expression}" value="0" <c:if test="${status.value eq 0}">checked="checked"</c:if> title="<ikep4j:message pre='${preCode}' key='unuse'/>" /> <ikep4j:message pre='${preCode}' key='unuse'/>
                                    </spring:bind>
                                </td>
    							<td width="15%" style="border-right-color:#ffffff;" class="isLinkUrl0 none"></td>
    							<td width="35%" class="isLinkUrl0 none"></td>
    							<th width="15%" class="isLinkUrl1"><ikep4j:message pre='${preView}' key='linkDataType'/></th>
    							<td width="35%" class="isLinkUrl1">
                                    <spring:bind path="apprForm.linkDataType">
                                    <input type="radio" class="radio" name="${status.expression}" value="0" <c:if test="${status.value eq 0}">checked="checked"</c:if> title="HTML" /> HTML    
    							    <input type="radio" class="radio" name="${status.expression}" value="1" <c:if test="${status.value eq 1}">checked="checked"</c:if> title="JSON" /> JSON
                                    </spring:bind>
    							</td>
    						</tr>
    						<tr class="isLinkUrl1">
    							<th width="15%"><ikep4j:message pre='${preView}' key='linkUrl'/></th>
    							<td width="85%" colspan="3"><div>
                                    <spring:bind path="apprForm.linkUrl">
                                    <input type="text" name="${status.expression}" value="${status.value}" class="inputbox w100" title="<ikep4j:message pre='${preView}' key='linkUrl'/>" />
                                    </spring:bind>
    							</div></td>
    						</tr>
    		            </tbody>
    		        </table>
    		        <table summary="" style="table-layout:fixed;">
    					<caption></caption>
    					<tbody>
    					    <tr height="20"><td width="15%" class="border_none"></td><td width="85%" class="border_none"></td></tr>    													
    						<tr>
    							<td colspan="2" style="border:none; padding:0px;">
    							    <div id="autoViewerA" style="margin-top:23px;padding-left:10px;padding-right:48px;border: 1px solid #DDD;width:842px;display:none;">
                                        <iframe id="autoPreviwerIframe" name="autoPreviwerIframe" width="100%" scrolling="no" frameborder="0" onload="resizeFrame(this);"></iframe>
                                    </div>    
    							    <div id="layoutDiv" class="iKEP_tab_s">
                                        <ul>
                                			<li><a href="#makerA"><ikep4j:message pre='${preView}' key='formLayout'/></a></li>
                                			<li><a href="#previewerA"><ikep4j:message pre='${preView}' key='preview'/></a></li>
                                		</ul>
                                        <div id="rowPerItemDiv" class="btn"></div>
        							    <div class="tab_conbox">
        							        <div id="makerA">
        							            <div class="mt5">
        							                <div class="appr_formlayoutbtn" style="width:25%;float:left;"></div>
        							                <div id="itemIconDiv" style="paddingwidth:70%;float:left;text-align:left;"></div>
        							                <div style="width:1%;clear:both"></div>
        							            </div>
                							    <div id="formLayoutDiv" class="none"></div>
                							    <div id="emptyLayoutDiv" class="appr_itemnone none">- <ikep4j:message pre='${preViewMessage}' key='newItem'/> -</div>
                							    <div class="appr_formlayoutbtn mt5"></div>
                							    <div id="rowActionDiv">
                							        <ul>
                							            <li id="rowActionNoMultiLi"><a href="#a" onclick="iFU.toNoMultiRow();"><ikep4j:message pre='${preView}' key='clearMulti'/></a><li>
                							            <li id="rowActionMultiLi"><a href="#a" onclick="iFU.toMultiRow();"><ikep4j:message pre='${preView}' key='multi'/></a></li>
                							            <li id="rowActionAddItemLayoutLi"><a href="#a" onclick="iFU.newItem();"><ikep4j:message pre='${preView}' key='newItem'/></a></li>
                                                        <li><a href="#a" onclick="iFU.removeRow();"><ikep4j:message pre='${preView}' key='remove'/></a></li>
                                                        <li id="rowActionUpRowLi"><a href="#a" onclick="iFU.upRow();"><ikep4j:message pre='${preView}' key='up'/></a></li>
                                                        <li id="rowActionDownRowLi"><a href="#a" onclick="iFU.downRow();"><ikep4j:message pre='${preView}' key='down'/></a></li>
                                                    </ul>
                                                </div>
                                                <div id="itemActionDiv">
                							        <ul>
                							            <li id="itemActionInfoItemLi"><a href="#a" onclick="iFU.leftItem();"><img src="<c:url value="/base/images/icon/ic_appr_que.gif" />" class="tooltip_approvalInfo_right"></a></li>
                                                        <li id="itemActionLeftItemLi"><a href="#a" onclick="iFU.leftItem();"><img src="<c:url value="/base/images/icon/ic_appr_left.gif"/>"></a></li>
                                                        <li id="itemActionRightItemLi"><a href="#a" onclick="iFU.rightItem();"><img src="<c:url value="/base/images/icon/ic_appr_right.gif"/>"></a></li>
                                                        <li><a href="#a" onclick="iFU.removeItem();"><img src="<c:url value="/base/images/icon/ic_appr_close.gif"/>"></a></li>
                                                    </ul>
                                                </div>
                                            </div>
                                            <div id="previewerA" style="width:840px">
                                                <iframe id="previwerIframe" name="previwerIframe" width="100%" scrolling="no" frameborder="0" onload="resizeFrame(this);"></iframe>    
                                            </div>
                                        </div>
                                    </div>
    							</td>
    						</tr>
    						<tr height="25"><td colspan="2" class="border_none"></td></tr>
    						<tr>
    							<td colspan="2" style="border-left:none;border-right:none;" align="right">
    							    <a id="listApprFormDocTemplateButton" class="button_ic valign_bottom" href="#a"><span><ikep4j:message pre='${preIfm}' key='template'/></span></a>&nbsp;&nbsp;
    					            <select onchange="iFU.ckApprFormResize(this.value);">
    					                <option value="100">100</option>
    					                <option value="200">200</option>
    					                <option value="300" selected="selected">300</option>
    					                <option value="500">500</option>
    					                <option value="800">800</option>
    					                <option value="1000">1000</option>
    					            </select>     
    					        </td>
    						</tr>	
    						<tr>
    							<td colspan="2">
    							    <spring:bind path="apprForm.formEditorData">
    							    <textarea id="${status.expression}" name="${status.expression}" class="inputbox w100 fullEditor">${status.value}</textarea>
    							    </spring:bind>
    							</td>
    						</tr>
    						<tr>
    						    <th width="15%"><ikep4j:message pre='${preView}' key='formGuide'/></th>
    						    <td width="85%">
    						        <spring:bind path="apprForm.formGuide">
									<textarea name="${status.expression}" class="inputbox w100" rows="3">${status.value}</textarea>
									</spring:bind>
    						    </td>
    					    </tr>
    						<tr>
    							<td colspan="2">
    							    <div id="fileUploadDiv" class="mt10"></div>
    							</td>
    						</tr>
    					</tbody>
    				</table>
    	        </div>
    	        <!--blockButton Start-->
    			<div id="formContentButtonDiv" class="blockButton"> 
    				<ul>
    					<li><a id="saveContentButton"   class="button" href="#a"><span><ikep4j:message pre='${preButton}' key='save'/></span></a></li>
    					<li><a id="listContentButton"   class="button" href="#a"><span><ikep4j:message pre='${preButton}' key='list'/></span></a></li>
    				</ul>
    			</div>
    			<!--//blockButton End-->
	        </form>
	        </div>
	        <!--blockButton Start-->
			
			<!--//blockButton End-->
	    </div>                              
	</div>
	<form id="editorFileUploadParameter" action="null" method="post"> 
	    <spring:bind path="apprForm.formId">
        <input type="hidden" name="${status.expression}" value="${status.value}"/>
        </spring:bind>
	    <input name="interceptorKey"  value="ikep4.system" type="hidden"/> 
    </form>
    <form id="apprPreviewForm" name="apprPreviewForm" method="post"> 
	    <spring:bind path="apprForm.formLayoutData">
	    <input type="hidden" name="${status.expression}" />
        </spring:bind>
        <spring:bind path="apprForm.formEditorData">
	    <input type="hidden" name="${status.expression}" />
	    </spring:bind>
	    <spring:bind path="apprForm.mode">
	    <input type="hidden" name="${status.expression}" value="" />
	    </spring:bind>
    </form>
    <form id="listForm" name="listForm" method="post" action="listApprForm.do">
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
    </form>
	<!--//tab End-->
</div>