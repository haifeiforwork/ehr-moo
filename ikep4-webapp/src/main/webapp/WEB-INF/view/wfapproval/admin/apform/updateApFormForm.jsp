<%@ include file="/base/common/taglibs.jsp"%>
<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>

<%-- 메시지 관련 Prefix 선언 Start --%> 
<c:set var="preHeader"  	value="ui.wfapproval.admin.apform.view.header" />
<c:set var="preList"    	value="ui.wfapproval.admin.apform.view" />
<c:set var="preViewForm"    value="ui.wfapproval.admin.apform.view.form" />
<c:set var="preViewFormTpl" value="ui.wfapproval.admin.apform.view.formtpl" />
<c:set var="preButton"  	value="ui.wfapproval.common.button" />
<c:set var="preSearch"  	value="ui.wfapproval.common.searchCondition" />
<c:set var="preCommon"  	value="ui.wfapproval.common.code" />
<c:set var="preMessage" 	value="ui.wfapproval.common.message" />
<c:set var="preFormMessage" value="ui.wfapproval.admin.apform.form.validation" />
<c:set var="preProcessHeader"  	value="ui.wfapproval.admin.apform.process.header" />
<%-- 메시지 관련 Prefix 선언 End --%>
 
<script type="text/javascript" src="<c:url value="/base/js/ckeditor/ckeditor.js"/>"></script>
<script type="text/javascript" src="<c:url value="/base/js/ckeditor/adapters/jquery.js"/>"></script> 
<script type="text/javascript" src="<c:url value="/base/js/jquery/jquery.validate-1.8.min.js"/>"></script>

<script  type="text/javascript">
<!--// 
	
	(function($) {
		
		var viewApFormProcessDialog;
		var viewApFormDialog;
		
		/**
		 * 프로세스 선택화면 오픈.
		 */
		f_ViewApFormProcessSearch = function () {
			
			$processId 		= $("input[name=processId]:hidden");
			$processName 	= $("input[name=processName]");
			$processType 	= $("input[name=processType]");
			$processVersion	= $("input[name=processVersion]:hidden");
			
			viewApFormProcessDialog = new iKEP.Dialog({     
				title 		: "<ikep4j:message pre="${preProcessHeader}" key="pageTitle" />",
				url 		: "searchApFormProcess.do",
				modal 		: true,
				width 		: 1200, //650,
				height 		: 600,
				params 		: {id:$processId.val(), name:$processName.val(), type:$processType.val(), ver:$processVersion.val() },
				callback	: function(result) {
						if(result){
							$processId.val(result.id);
							$processName.val(result.name);
							$processType.val(result.type);
						}
				}
			});
		};
		
		/**
		 * 양식 미리보기 화면 오픈.
		 */
		f_ViewApForm = function () {
		   	viewApFormDialog = new iKEP.Dialog({     
				title 		: $("input[name=apprTitle]").val(),
				url 		: "readApFormPreview.do?formId="+ ${apForm.formId},
				modal 		: true,
				top 		: 100,
				left 		: 100,
				width 		: 980,
				height 		: 600,
				params 		: null
			});
		};
		
		/**
		 * onload시 수행할 코드
		 */
		$(document).ready(function() {
			
			$("#apFormTab").tabs({
				selected : <c:out value="${tabSelect}" default="0" />
			});
			
			/**
			 * CK Edit Init
			 */
			$("#apprFormData").ckeditor($.extend(
				fullCkeditorConfig,
				{
					"popupTitle" 		: "<ikep4j:message key='ui.support.fileupload.header.title' />",
					extraPlugins 		: "autogrow",
					height				: 350,
				  	autoGrow_maxHeight 	: 600
				}
			));
			
			/**
			 * CK Edit Init
			 */
			/*
			CKEDITOR.replace("apprFormData", {
					fullPage 			: true
					, contentsCss 		: ["<c:url value="/base/css/common.css"/>", "<c:url value="/base/css/theme01/theme.css"/>", "<c:url value="/base/css/theme01/jquery_ui_custom.css"/>"]
				  //, skin 				: "Kama"
				  //, extraPlugins : "uicolor"
				  //, toolbar : [ [ "Bold", "Italic" ], [ "UIColor" ] ]
				  //, language: "ko"
				  , extraPlugins 		: "autogrow"
				  , autoGrow_maxHeight 	: 800
			});
			*/
			
			/**
			 * ApForm 버튼영역 실행
			 */
			$("#updateApFormButton a").click(function(){
	            switch (this.id) {
	                case "applyApFormButton":
						$("#apForm").validate(apFormValidateOptions);
						$("#apForm").attr('action', '<c:url value="/wfapproval/admin/apform/applyApForm.do"/>').submit();
						break;
	                case "listApFormButton":
	                    location.href='<c:url value="/wfapproval/admin/apform/listApForm.do"/>'
	                    break;
	                default:
	                    break;
	            }
	        });
			
			/**
		    * ApFormTpl 버튼영역 실행
		    */
			$("#updateApFormTplButton a").click(function(){
	            switch (this.id) {
	                case "applyApFormTplButton":
						$("#apFormTpl").validate(apFormTplValidateOptions);
						$("#apFormTpl").attr('action', '<c:url value="/wfapproval/admin/apform/applyApFormTpl.do"/>').submit();
						break;
					case "viewApFormTplButton":
	                    f_ViewApForm();
	                    break;
	                case "listApFormTplButton":
	                    location.href='<c:url value="/wfapproval/admin/apform/listApForm.do"/>'
	                    break;
	                default:
	                    break;
	            }
	        });
			
			$("#processSearchButton").click(function(){
				f_ViewApFormProcessSearch();
			});
			
			/**
			 * Form Validate
			 * 
			 * @param {Object} form
			 */
			var apFormValidateOptions = {
				
				debug			: false,
				//onfocusout		: false,
				//onkeyup			: true,
				//wrapper			: "li",
				//errorPlacement	: function(error, element){
				//	error.appendTo(element.parent("td").append("<br><br>"));
				//},
				focusInvalid	: true,
				submitHandler	: function(form){
					if (!confirm("<ikep4j:message pre='${preMessage}' key='save'/>")) return false;
					
					form.submit();
					return true;
	            },
			  	rules			: {
				    formClassCd		: {required:true},
				    formTypeCd		: {required:true},
				    formName		: {required:true	, maxlength:50}
			  	},
			  	messages		: {
				    formClassCd		: {
				    	required	: "<ikep4j:message pre='${preFormMessage}' key='noselect.formClassCd'/>"
				    },
				    formTypeCd: {
				      	required	: "<ikep4j:message pre='${preFormMessage}' key='noselect.formTypeCd'/>"
				    },
				    formName: {
				      	required 	: "<ikep4j:message pre='${preFormMessage}' key='mustinput.formName'/>",
						maxlength 	: "<ikep4j:message pre='${preFormMessage}' key='maxinput.formName' arguments='50'/>"
				    }
			  	}
			};
			
			/**
			 * Form Template Validate
			 * 
			 * @param {Object} form
			 */
			var apFormTplValidateOptions = {
				
				debug			: false,
				//focusInvalid	: true,
				submitHandler	: function(form){
					
					//alert($("input[name=formId]:hidden").val());
					if($("input[name=formId]:hidden").val() == ''){
						alert("<ikep4j:message pre='${preMessage}' key='saveApFormMasterCheck'/>");
						return false;
					}
					
					if (!confirm("<ikep4j:message pre='${preMessage}' key='save'/>")) return false;
					
					/* 에디터에 첨부되어 있는 이미지를 업로드한다.*/
					$("#cke_apprFormData").find("iframe").contents().find("img[name=editorImage]").each(function(index) {  
						$("#apFormTpl").append($("<input name='editorFileLinkList[" + index + "].fileId' value='" + $(this).attr("id") + "' type='hidden'/>")); 
						$("#apFormTpl").append($("<input name='editorFileLinkList[" + index + "].flag'   value='add' type='hidden'/>")); 
					});
					
					form.submit();
					return true;
	            },
			  	rules			: {
					processId		: "required",
					//processName		: "required",
					//processType		: "required",
				    mailReqCd		: "required",
				    mailEndCd		: "required",
					mailReqWayCd	: "required",
					mailEndWayCd	: "required",
					discussCd		: "required",
					apprPeriodCd	: "required",
				    apprTypeCd		: "required",
					apprDocCd		: "required",
					isApprAttach	: "required",
					apprTitle		: {required:true	, maxlength:100}
			  	},
			  	messages		: {
					processId		: {
				    	required	: "<ikep4j:message pre='${preFormMessage}' key='mustinput.processId'/>"
				    },
				    mailReqCd		: {
				    	required	: "<ikep4j:message pre='${preFormMessage}' key='noselect.mailReqCd'/>"
				    },
				    mailEndCd: {
				      	required	: "<ikep4j:message pre='${preFormMessage}' key='noselect.mailEndCd'/>"
				    },
				    mailReqWayCd: {
				      	required	: "<ikep4j:message pre='${preFormMessage}' key='noselect.mailReqWayCd'/>"
				    },
				    mailEndWayCd: {
				      	required	: "<ikep4j:message pre='${preFormMessage}' key='noselect.mailEndWayCd'/>"
				    },
				    discussCd: {
				      	required	: "<ikep4j:message pre='${preFormMessage}' key='noselect.discussCd'/>"
				    },
				    apprPeriodCd: {
				      	required	: "<ikep4j:message pre='${preFormMessage}' key='noselect.apprPeriodCd'/>"
				    },
				    apprTypeCd: {
				      	required	: "<ikep4j:message pre='${preFormMessage}' key='noselect.apprTypeCd'/>"
				    },
					apprDocCd: {
				      	required	: "<ikep4j:message pre='${preFormMessage}' key='noselect.apprDocCd'/>"
				    },
				    isApprAttach: {
				      	required	: "<ikep4j:message pre='${preFormMessage}' key='noselect.isApprAttach'/>"
				    },
				    apprTitle: {
				      	required 	: "<br><br><ikep4j:message pre='${preFormMessage}' key='mustinput.apprTitle'/>",
						maxlength 	: "<br><br><ikep4j:message pre='${preFormMessage}' key='maxinput.apprTitle' arguments='100'/>"
				    }
			  	}
			};
			
			
			
			/**
			 * CK Edit OnLoad
			 * @param {Object} ev
			 */
			/*
            CKEDITOR.on("apprFormData", function(ev){
                // Show the editor name and description in the browser status bar.
                //document.getElementById('eMessage').innerHTML = '<p>Instance <code>' + ev.editor.name + '<\/code> loaded.<\/p>';
                
                // Show this sample buttons.
                //document.getElementById('eButtons').style.visibility = '';
            });
            */
            
			
			/**
			 * CK Edit Get Content
			 */
			/*
            f_GetContents = function (){
                // Get the editor instance that you want to interact with.
                var oEditor = CKEDITOR.instances.apprFormData;
                
                // Get editor contents
                // http://docs.cksource.com/ckeditor_api/symbols/CKEDITOR.editor.html#getData
                alert(oEditor.getData());
            };
            */
			
		});
		
	})(jQuery);  

//-->
</script>

<h1 class="none"><ikep4j:message pre="${preHeader}" key="title" /></h1>

<!--pageTitle Start-->
<div id="pageTitle">
	<h2><ikep4j:message pre="${preHeader}" key="pageTitle" /></h2> 
	<div id="pageLocation">
		<ul>
			<li class="liFirst"><ikep4j:message pre="${preHeader}" key="pageLocation.1depth" /></li>
			<li><ikep4j:message pre="${preHeader}" key="pageLocation.2depth" /></li>
			<li><ikep4j:message pre="${preHeader}" key="pageLocation.3depth" /></li>
			<li class="liLast"><ikep4j:message pre="${preHeader}" key="pageLocation.4depth" /></li>
		</ul>
	</div>
</div>
<!--//pageTitle End-->
				
<div id="guideConFrame">

	<!--tab Start-->		
	<div id="apFormTab" class="iKEP_tab">
	    <ul>
	        <li><a href="#tabs-1"><ikep4j:message pre="${preViewForm}" key="subtitle" /></a></li>
	        <li><a href="#tabs-2"><ikep4j:message pre="${preViewFormTpl}" key="subtitle" /></a></li>
	    </ul>
	    <div class="tab_con">
	    
	    	<!--양식 기본정보-->
	        <div id="tabs-1">
	        	<div class="blockBlank_10px"></div>
				
				<!--subTitle_2 Start-->
				<div class="subTitle_2 noline">
					<h3><ikep4j:message pre="${preViewForm}" key="subtitle" /></h3>
				</div>
				<!--//subTitle_2 End-->
				
	        	<!--blockDetail Start-->
				<div class="blockDetail">
				
				<form id="apForm" name="apForm" modelAttribute="apForm" method="post">
					
					<spring:bind path="apForm.formId">
					<input name="${status.expression}" type="hidden" value="${status.value}" title="<ikep4j:message pre='${preViewForm}' key='${status.expression}'/>" />
					</spring:bind>
					
					<table summary="<ikep4j:message pre="${preViewForm}" key="subtitle" />">
						<caption></caption>
						<col style="width: 18%;"/>
						<col style="width: 82%;"/>
						<tbody>
							<tr>
								<spring:bind path="apForm.formClassCd">
								<th scope="row"><ikep4j:message pre='${preViewForm}' key='${status.expression}' /></th>
								<td>
									<select name="${status.expression}" title="<ikep4j:message pre='${preViewForm}' key='${status.expression}' />">
										<option value="">선택</option>
										<c:forEach var="apCode" items="${listFormClassCd}">
											<option value="${apCode.codeId}" <c:if test="${apCode.codeId eq status.value}">selected="selected"</c:if>>${apCode.codeName}</option>
										</c:forEach>
									</select>
								</td>
								</spring:bind>
							</tr>
							<tr>
								<spring:bind path="apForm.formTypeCd">
								<th scope="row"><ikep4j:message pre='${preViewForm}' key='${status.expression}' /></th>
								<td>
									<select name="${status.expression}" title="<ikep4j:message pre='${preViewForm}' key='${status.expression}' />">
										<option value="">선택</option>
										<c:forEach var="apCode" items="${listFormTypeCd}">
											<option value="${apCode.codeId}" <c:if test="${apCode.codeId eq status.value}">selected="selected"</c:if>>${apCode.codeName}</option>
										</c:forEach>
									</select>
								</td>
								</spring:bind>
							</tr>
							<tr>
								<spring:bind path="apForm.formName">
								<th scope="row"><ikep4j:message pre='${preViewForm}' key='${status.expression}' /></th>
								<td>
									<input name="${status.expression}" value="${status.value}" type="text" class="inputbox" title='<ikep4j:message pre='${preViewForm}' key='${status.expression}' />'  size="80" />
								</td>
								</spring:bind>
							</tr>
							<tr>
								<spring:bind path="apForm.isUse">
								<th scope="row"><ikep4j:message pre='${preViewForm}' key='${status.expression}' /></th>
								<td>
									<label>
										<input 	type="radio" class="radio" title="<ikep4j:message pre='${preCommon}' key='use'/>" 
												name="${status.expression}" value="Y" 
												<c:if test="${status.value eq 'Y'}">checked</c:if> /><ikep4j:message pre='${preCommon}' key='use'/>
									</label>&nbsp;&nbsp;
									<label>
										<input 	type="radio" class="radio" title="<ikep4j:message pre='${preCommon}' key='unuse'/>" 
												name="${status.expression}" value="N" 
												<c:if test="${status.value eq 'N'}">checked</c:if>/><ikep4j:message pre='${preCommon}' key='unuse'/>
									</label>
								</td>
								</spring:bind>
							</tr>
							<tr>
								<spring:bind path="apForm.formDesc">
								<th scope="row"><ikep4j:message pre='${preViewForm}' key='${status.expression}' /></th>
								<td>
									<textarea 	name="${status.expression}" cols="100" rows="8" class="tabletext valid"
												title="<ikep4j:message pre='${preViewForm}' key='${status.expression}' />">${status.value}</textarea>
								</td>
								</spring:bind>
							</tr>														
						</tbody>
					</table>
				</form>
				</div>
				<!--//blockDetail End-->
				
				<!--blockButton Start-->
				<div id="updateApFormButton" class="blockButton"> 
					<ul>
						<li><a id="applyApFormButton"   class="button" href="#"><span><ikep4j:message pre='${preButton}' key='save'/></span></a></li>
						<li><a id="listApFormButton" 	class="button" href="#"><span><ikep4j:message pre='${preButton}' key='list'/></span></a></li>
					</ul>
				</div>
				<!--//blockButton End-->
	        </div>
	        
	        <!--양식 상세정보-->
	        <div id="tabs-2">
	        
				<form id="apFormTpl" name="apFormTpl" modelAttribute="apFormTpl" method="post">
				
				<spring:bind path="apFormTpl.formId">
				<input name="${status.expression}" type="hidden" value="${status.value}"/>
				</spring:bind>
				
				<div class="blockBlank_10px"></div>
				
				<!--subTitle_2 Start-->
				<div class="subTitle_2 noline">
					<h3><ikep4j:message pre="${preViewFormTpl}" key="subtitle0" /></h3>
				</div>
				<!--//subTitle_2 End-->
				
	        	<!--blockDetail Start 결재선 설정-->
				<div class="blockDetail">
				
					<table summary="<ikep4j:message pre="${preViewFormTpl}" key="subtitle0"/>">
						<caption></caption>
						<tbody>
							<tr>
								<th width="18%" scope="row"><ikep4j:message pre='${preViewFormTpl}' key='processId' /></th>
								<td width="82%">
									<spring:bind path="apFormTpl.processId">
										<input name="${status.expression}" type="hidden" value="${status.value}"/>
									</spring:bind>
									<spring:bind path="apFormTpl.processName">
										<input 	type="text" class="inputbox" title="<ikep4j:message pre='${preViewFormTpl}' key='${status.expression}' />" 
												name="${status.expression}" value="${status.value}" size="30" readonly/>
									</spring:bind>
									<spring:bind path="apFormTpl.processType">
										<input 	type="text" class="inputbox" title="<ikep4j:message pre='${preViewFormTpl}' key='${status.expression}' />" 
												name="${status.expression}" value="${status.value}" size="15" readonly/>
									</spring:bind>
									<spring:bind path="apFormTpl.processVersion">
										<input name="${status.expression}" type="hidden" value="${status.value}"/>
									</spring:bind>
									<!--
									<a id="processSearchButton" class="button" href="#"><span><ikep4j:message pre='${preButton}' key='search'/></span></a>
									-->
									<a id="processSearchButton" class="button" href="#">
										<img align="absmiddle" src="<c:url value="/base/images/theme/theme01/basic/btn_search.gif"/>" alt="<ikep4j:message pre='${preButton}' key='search' />" />
									</a>
								</td>
							</tr>
						</tbody>
					</table>
				</div>
				<!--//blockDetail End-->
				
	        	<div class="blockBlank_10px"></div>
				<!--subTitle_2 Start-->
				<div class="subTitle_2 noline">
					<h3><ikep4j:message pre="${preViewFormTpl}" key="subtitle1"/></h3>
				</div>
				<!--//subTitle_2 End-->
				
	        	<!--결재설정 blockDetail Start-->
				<div class="blockDetail">
						
					<table summary="<ikep4j:message pre="${preViewFormTpl}" key="subtitle1"/>">
						<caption></caption>
						<tbody>
							<tr>
								<spring:bind path="apFormTpl.mailReqCd">
								<th width="18%" scope="row"><ikep4j:message pre='${preViewFormTpl}' key='${status.expression}' /></th>
								<td width="32%">
									<select name="${status.expression}" title="<ikep4j:message pre='${preViewFormTpl}' key='${status.expression}' />">
										<option value=""><ikep4j:message pre="${preSearch}" key="select"/></option>
										<c:forEach var="apCode" items="${listMailReqCd}">
											<option value="${apCode.codeId}" <c:if test="${apCode.codeId eq status.value}">selected="selected"</c:if>>${apCode.codeName}</option>
										</c:forEach>
									</select>
								</td>
								</spring:bind>
								<spring:bind path="apFormTpl.mailReqWayCd">
								<th scope="row"><ikep4j:message pre='${preViewFormTpl}' key='${status.expression}' /></th>
								<td>
									<select name="${status.expression}" title="<ikep4j:message pre='${preViewFormTpl}' key='${status.expression}' />">
										<option value=""><ikep4j:message pre="${preSearch}" key="select"/></option>
										<c:forEach var="apCode" items="${listMailReqWayCd}">
											<option value="${apCode.codeId}" <c:if test="${apCode.codeId eq status.value}">selected="selected"</c:if>>${apCode.codeName}</option>
										</c:forEach>
									</select>
								</td>
								</spring:bind>
							</tr>
							<tr>
								<spring:bind path="apFormTpl.mailEndCd">
								<th width="18%" scope="row"><ikep4j:message pre='${preViewFormTpl}' key='${status.expression}' /></th>
								<td width="32%">
									<select name="${status.expression}" title="<ikep4j:message pre='${preViewFormTpl}' key='${status.expression}' />">
										<option value=""><ikep4j:message pre="${preSearch}" key="select"/></option>
										<c:forEach var="apCode" items="${listMailEndCd}">
											<option value="${apCode.codeId}" <c:if test="${apCode.codeId eq status.value}">selected="selected"</c:if>>${apCode.codeName}</option>
										</c:forEach>
									</select>
								</td>
								</spring:bind>
								<spring:bind path="apFormTpl.mailEndWayCd">
								<th scope="row"><ikep4j:message pre='${preViewFormTpl}' key='${status.expression}' /></th>
								<td>
									<select name="${status.expression}" title="<ikep4j:message pre='${preViewFormTpl}' key='${status.expression}' />">
										<option value=""><ikep4j:message pre="${preSearch}" key="select"/></option>
										<c:forEach var="apCode" items="${listMailEndWayCd}">
											<option value="${apCode.codeId}" <c:if test="${apCode.codeId eq status.value}">selected="selected"</c:if>>${apCode.codeName}</option>
										</c:forEach>
									</select>
								</td>
								</spring:bind>
							</tr>
							<tr>
								<th scope="row"><ikep4j:message pre='${preViewFormTpl}' key='apprConfig' /></th>
								<td>
									<spring:bind path="apFormTpl.isAppr">
									<label>
										<input 	type="checkbox" class="checkbox" name="${status.expression}" value="Y"
												title="<ikep4j:message pre='${preViewFormTpl}' key='${status.expression}' />" 
												<c:if test="${status.value eq 'Y'}">checked</c:if>/>
										<ikep4j:message pre='${preViewFormTpl}' key='${status.expression}' />
									</label>&nbsp;&nbsp;
									</spring:bind>
									<spring:bind path="apFormTpl.isDiscuss">
									<label>
										<input 	type="checkbox" class="checkbox" name="${status.expression}" value="Y"
												title="<ikep4j:message pre='${preViewFormTpl}' key='${status.expression}' />" 
												<c:if test="${status.value eq 'Y'}">checked</c:if>/>
										<ikep4j:message pre='${preViewFormTpl}' key='${status.expression}' />
									</label>
									</spring:bind>
								</td>
								<spring:bind path="apFormTpl.discussCd">
								<th scope="row"><ikep4j:message pre='${preViewFormTpl}' key='${status.expression}' /></th>
								<td>
									<select name="${status.expression}" title="<ikep4j:message pre='${preViewFormTpl}' key='${status.expression}' />">
										<option value=""><ikep4j:message pre="${preSearch}" key="select"/></option>
										<c:forEach var="apCode" items="${listDiscussCd}">
											<option value="${apCode.codeId}" <c:if test="${apCode.codeId eq status.value}">selected="selected"</c:if>>${apCode.codeName}</option>
										</c:forEach>
									</select>
								</td>
								</spring:bind>
							</tr>
							<tr>
								<spring:bind path="apFormTpl.apprLineCnt">
								<th scope="row"><ikep4j:message pre='${preViewFormTpl}' key='${status.expression}' /></th>
								<td>
									<select name="${status.expression}" title="<ikep4j:message pre='${preViewFormTpl}' key='${status.expression}' />">
										<c:forEach var="idx" begin="1" end="15" step="1">
											<option value="${idx}" <c:if test="${idx eq status.value}">selected="selected"</c:if>>${idx}</option>
										</c:forEach>
									</select>
								</td>
								</spring:bind>
								<spring:bind path="apFormTpl.discussLineCnt">
								<th scope="row"><ikep4j:message pre='${preViewFormTpl}' key='${status.expression}' /></th>
								<td>
									<select name="${status.expression}" title="<ikep4j:message pre='${preViewFormTpl}' key='${status.expression}' />">
										<c:forEach var="idx" begin="1" end="15" step="1">
											<option value="${idx}" <c:if test="${idx eq status.value}">selected="selected"</c:if>>${idx}</option>
										</c:forEach>
									</select>
								</td>
								</spring:bind>
							</tr>
						</tbody>
					</table>
				</div>
				<!--//blockDetail End-->
				
				<div class="blockBlank_10px"></div>
				
				<!--subTitle_2 Start-->
				<div class="subTitle_2 noline">
					<h3><ikep4j:message pre="${preViewFormTpl}" key="subtitle2"/></h3>
				</div>
				<!--//subTitle_2 End-->
				
				<!--문서설정 blockDetail Start-->
				<div class="blockDetail">
					<table summary="<ikep4j:message pre="${preViewFormTpl}" key="subtitle2"/>">
						<caption></caption>
						<tbody>
							<tr>
								<spring:bind path="apFormTpl.apprPeriodCd">
								<th width="18%" scope="row"><ikep4j:message pre='${preViewFormTpl}' key='${status.expression}' /></th>
								<td width="32%">
									<select name="${status.expression}" title="<ikep4j:message pre='${preViewFormTpl}' key='${status.expression}' />">
										<option value=""><ikep4j:message pre="${preSearch}" key="select"/></option>
										<c:forEach var="apCode" items="${listApprPeriodCd}">
											<option value="${apCode.codeId}" <c:if test="${apCode.codeId eq status.value}">selected="selected"</c:if>>${apCode.codeName}</option>
										</c:forEach>
									</select>
								</td>
								</spring:bind>
								<spring:bind path="apFormTpl.isApprPeriod">
								<th width="18%" scope="row"><ikep4j:message pre='${preViewFormTpl}' key='${status.expression}' /></th>
								<td width="32%">
									<select name="${status.expression}" title="<ikep4j:message pre='${preViewFormTpl}' key='${status.expression}' />">
										<c:forEach var="code" items="${commonCode.useAdminSelectList}">
											<option value="${code.key}" <c:if test="${code.key eq status.value}">selected="selected"</c:if>>
												<spring:message code="${code.value}"/>
											</option>
										</c:forEach>
									</select>
								</td>
								</spring:bind>
							</tr>
							<tr>
								<spring:bind path="apFormTpl.apprDocCd">
								<th scope="row"><ikep4j:message pre='${preViewFormTpl}' key='${status.expression}' /></th>
								<td>
									<c:forEach var="apCode" items="${listApprDocCd}">
										<label>
											<input type="radio" class="radio" title="${apCode.codeName}" name="${status.expression}"
													value="${apCode.codeId}" <c:if test="${apCode.codeId eq status.value}">checked</c:if>/>${apCode.codeName}
										</label>&nbsp;&nbsp;
									</c:forEach>
								</td>
								</spring:bind>
								<spring:bind path="apFormTpl.isApprDoc">
								<th scope="row"><ikep4j:message pre='${preViewFormTpl}' key='${status.expression}' /></th>
								<td>
									<select name="${status.expression}" title="<ikep4j:message pre='${preViewFormTpl}' key='${status.expression}' />">
										<c:forEach var="code" items="${commonCode.useAdminSelectList}">
											<option value="${code.key}" <c:if test="${code.key eq status.value}">selected="selected"</c:if>>
												<spring:message code="${code.value}"/>
											</option>
										</c:forEach>
									</select>
								</td>
								</spring:bind>
							</tr>
							<tr>
								<spring:bind path="apFormTpl.apprTypeCd">
								<th scope="row"><ikep4j:message pre='${preViewFormTpl}' key='${status.expression}' /></th>
								<td>
									<select name="${status.expression}" title="<ikep4j:message pre='${preViewFormTpl}' key='${status.expression}' />">
										<option value=""><ikep4j:message pre="${preSearch}" key="select"/></option>
										<c:forEach var="apCode" items="${listFormTypeCd}">
											<option value="${apCode.codeId}" <c:if test="${apCode.codeId eq status.value}">selected="selected"</c:if>>${apCode.codeName}</option>
										</c:forEach>
									</select>
								</td>
								</spring:bind>
								<spring:bind path="apFormTpl.isApprType">
								<th scope="row"><ikep4j:message pre='${preViewFormTpl}' key='${status.expression}' /></th>
								<td>
									<select name="${status.expression}" title="<ikep4j:message pre='${preViewFormTpl}' key='${status.expression}' />">
										<c:forEach var="code" items="${commonCode.useAdminSelectList}">
											<option value="${code.key}" <c:if test="${code.key eq status.value}">selected="selected"</c:if>>
												<spring:message code="${code.value}"/>
											</option>
										</c:forEach>
									</select>
								</td>
								</spring:bind>
							</tr>
							<tr>
								<spring:bind path="apFormTpl.isNoneForm">
								<th scope="row"><ikep4j:message pre='${preViewFormTpl}' key='${status.expression}' /></th>
								<td>
									<label>
										<input 	type="radio" class="radio" title="<ikep4j:message pre='${preCommon}' key='use'/>" 
												name="${status.expression}" value="Y" 
												<c:if test="${status.value eq 'Y'}">checked</c:if>/><ikep4j:message pre='${preCommon}' key='use'/>
									</label>&nbsp;&nbsp;
									<label>
										<input 	type="radio" class="radio" title="<ikep4j:message pre='${preCommon}' key='unuse'/>" 
												name="${status.expression}" value="N" 
												<c:if test="${status.value eq 'N'}">checked</c:if>/><ikep4j:message pre='${preCommon}' key='unuse'/>
									</label>
								</td>
								</spring:bind>
								<th scope="row"></th>
								<td></td>
							</tr>
						</tbody>
					</table>
				</div>
				<!--//blockDetail End-->
				
				<div class="blockBlank_10px"></div>
				
				<!--subTitle_2 Start-->
				<div class="subTitle_2 noline">
					<h3><ikep4j:message pre="${preViewFormTpl}" key="subtitle3"/></h3>
				</div>
				<!--//subTitle_2 End-->
				
				<!--양식내용 blockDetail Start-->
				<div class="blockDetail">
					<table summary="<ikep4j:message pre="${preViewFormTpl}" key="subtitle3"/>">
						<caption></caption>
						<tbody>
							<tr>
								<th width="18%" scope="row"><ikep4j:message pre='${preViewFormTpl}' key='apprTitle' /></th>
								<td width="82%">
									<spring:bind path="apFormTpl.isApprTitle">	
									<input 	type="checkbox" class="checkbox" name="${status.expression}" value="Y"
											title="<ikep4j:message pre='${preViewFormTpl}' key='${status.expression}' />" 
											<c:if test="${'Y' eq status.value}">checked</c:if>/>
									<ikep4j:message pre='${preViewFormTpl}' key='${status.expression}' />
									</spring:bind>
									<spring:bind path="apFormTpl.apprTitle">
									<input 	type="text" class="inputbox" title="<ikep4j:message pre='${preViewFormTpl}' key='${status.expression}' />" 
											name="${status.expression}" value="${status.value}" size="100"/>
									</spring:bind>
								</td>
							</tr>
							<tr>
								<spring:bind path="apFormTpl.isApprAttach">
								<th scope="row"><ikep4j:message pre='${preViewFormTpl}' key='${status.expression}' /></th>
								<td>
									<label>
										<input 	type="radio" class="radio" title="<ikep4j:message pre='${preCommon}' key='use'/>" 
												name="${status.expression}" value="Y" 
												<c:if test="${status.value eq 'Y'}">checked</c:if>/><ikep4j:message pre='${preCommon}' key='use'/>
									</label>&nbsp;&nbsp;
									<label>
										<input 	type="radio" class="radio" title="<ikep4j:message pre='${preCommon}' key='unuse'/>" 
												name="${status.expression}" value="N" 
												<c:if test="${status.value eq 'N'}">checked</c:if>/><ikep4j:message pre='${preCommon}' key='unuse'/>
									</label>
								</td>
								</spring:bind>
							</tr>
							<tr>
								<spring:bind path="apFormTpl.apprFormData">
								<!--
								<th scope="row"><ikep4j:message pre='${preViewFormTpl}' key='${status.expression}' /></th>
								-->
								<td colspan="2">
									<textarea 	id="${status.expression}" 
												name="${status.expression}"
												class="inputbox w100 fullEditor"
												cols="" rows="5"
												title="<ikep4j:message pre='${preViewFormTpl}' key='${status.expression}' />">${status.value}</textarea>
								</td>
								</spring:bind>
							</tr>
						</tbody>
					</table>
				</div>
				<!--//blockDetail End-->
				
				</form>
			
				<!--blockButton Start-->
				<div id="updateApFormTplButton" class="blockButton"> 
					<ul>
						<li><a id="applyApFormTplButton"	class="button" href="#"><span><ikep4j:message pre='${preButton}' key='save'/></span></a></li>
						<li><a id="viewApFormTplButton" 	class="button" href="#"><span><ikep4j:message pre='${preButton}' key='preview'/></span></a></li>
						<li><a id="listApFormTplButton" 	class="button" href="#"><span><ikep4j:message pre='${preButton}' key='list'/></span></a></li>
					</ul>
				</div>
				<!--//blockButton End-->
	        </div>
			
	    </div>
		
	</div>
	<!--//tab End-->
	
</div>