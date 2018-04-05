<%@ include file="/base/common/taglibs.jsp"%>
<%@ page language="java" errorPage="/common/error.jsp"
	pageEncoding="UTF-8" contentType="text/html;charset=utf-8"%>
<%@ taglib uri="/WEB-INF/tld/ikep4j.tld" prefix="ikep4j"%>

<c:set var="prefixMessage" value="message.portal.admin.screen.portlet.createPortletForm"/>
<c:set var="prefixUI" value="ui.portal.admin.screen.portlet.createPortletForm"/>


<script type="text/javascript">
	function listPortlet() {
		$jq("#listForm").attr("action","<c:url value='/portal/admin/screen/portlet/listPortlet.do'/>");
		$jq("#listForm").submit();	  
	}
	
	function createPortlet() {
		$jq('#portletName').val($jq('#defaultLocaleValue').val());
		$jq("#mainForm").attr("action","<c:url value='/portal/admin/screen/portlet/createPortlet.do'/>");
		$jq("body").ajaxLoadStart("button");
		$jq("#mainForm").submit();
	} 

	(function($) {

		/**
		 * onload시 수행할 코드
		 */
		$(document).ready(function() {
			/**
			 * Form Control Start
			 */
			$("#fileTr").hide();
			$("#configViewUrlTr").hide();
			$("#maxViewUrlTr").hide();
			$("#moreViewUrlTr").hide();
			$("#helpViewUrlTr").hide();
			$("#defaultLocaleValue").focus();

			$("input[name=portletType]").click(function() {
				if($(this).val() != "HTML") {
					$("#fileTr").show();
					$("tr[id^='localeTr']").hide();
					$("#normalViewUrlTr").hide();$("#normalViewUrl").val("");
					$("#configModeTr").hide();
					$("#configViewUrlTr").hide();$("#configViewUrl").val("");$("input[name=configMode]")[1].checked = "checked";
					$("#maxModeTr").hide();
					$("#maxViewUrlTr").hide();$("#maxViewUrl").val("");$("input[name=maxMode]")[1].checked = "checked";
					$("#moreModeTr").hide();
					$("#moreViewUrlTr").hide();$("#moreViewUrl").val("");$("input[name=moreMode]")[1].checked = "checked";
					$("#helpModeTr").hide();
					$("#helpViewUrlTr").hide();$("#helpViewUrl").val("");$("input[name=helpMode]")[1].checked = "checked";
					$("#linkTypeTr").hide(); $("input[name=linkType]")[0].checked = "checked"; 
					$("#iframeHeightArea").hide();
					$('input[name$="itemMessage"]').val("temp");
					$("input[name='normalViewUrl']").val('temp');
				}
				else { 
					$("#fileTr").hide();
					$("tr[id^='localeTr']").show();
					$("#normalViewUrlTr").show();
					$("#configModeTr").show(); 	if($("input[name=configMode]:checked").val()==1){$("#configViewUrlTr").show();}
					$("#maxModeTr").show();		if($("input[name=maxMode]:checked").val()==1){$("#maxViewUrlTr").show();}
					$("#moreModeTr").show();	if($("input[name=moreMode]:checked").val()==1){$("#moreViewUrlTr").show();}
					$("#helpModeTr").show();	if($("input[name=helpMode]:checked").val()==1){$("#helpViewUrlTr").show();}
					$("#linkTypeTr").show();

					$("input[name$='itemMessage']").each(
						function(){
							if($(this).val()=='temp')
								$(this).val('');
						}
					);
					$("input[name='normalViewUrl']").val('');
				}
			});
			
			
			$("input[name=configMode]").click(function() {
				if($(this).val()==1) {
					$("#configViewUrlTr").show();
				}
				else {
					$("#configViewUrlTr").hide();
				}
			});
			
			$("input[name=maxMode]").click(function() {
				if($(this).val()==1) {
					$("#maxViewUrlTr").show();
				}
				else {
					$("#maxViewUrlTr").hide();
				}
			});
			
			$("input[name=moreMode]").click(function() {
				if($(this).val()==1) {
					$("#moreViewUrlTr").show();
				}
				else {
					$("#moreViewUrlTr").hide();
				}
			});
			
			$("input[name=helpMode]").click(function() {
				if($(this).val()==1) {
					$("#helpViewUrlTr").show();
				}
				else {
					$("#helpViewUrlTr").hide();
				}
			});
			
			$("input[name=linkType]").click(function() {
				if($(this).val()=='PortletIFrameExt') {
					$("#iframeHeightArea").show();
				}
				else {
					$("#iframeHeightArea").hide();
				}
			});
			
			$("select[name=systemCode]").change(function() {

				   $("#portletCategoryId option").remove();
			       $.get('<c:url value="/portal/admin/screen/portletCategory/listPortletCategory.do"/>?systemCode='+$(this).val())
			       .success(function(data) {
			    	   <spring:bind path="portalPortlet.portletCategoryId">
			    	   for(var i=0; i<data.length; i++){
						   $("#portletCategoryId").get(0).options[i] = new Option(data[i].portletCategoryName,data[i].portletCategoryId);
						   if(data[i].portletCategoryId == '${status.value}')
						   {
							   $("#portletCategoryId").get(0).options[i].selected = true;
						   }
					   }
			    	   </spring:bind>
			    	   if(data.length==0)
			    	   {
			    		   $("#portletCategoryId").get(0).options[i] = new Option('-- <ikep4j:message pre="${prefixUI}" key="detail.noGroup" /> --','');
			    	   }
						
			       }) 
			       .error(function(event, request, settings) { alert("error"); });
			});
			
			var defalutPreviewId = 'img_portlet_01';
			
			$("#previewImageId").val(defalutPreviewId);
			
			/* Preview 이미지 Start */
			$("#btnPreview").click(function() {
				$("#previewArea").show();
				$("#previewImage").removeClass();
				$("#previewImage").addClass($("#previewImageId").val())
			});
			
			$("#btnDefaultPreview").click(function() {
				$("#previewArea").show();
				$("#previewImage").removeClass();
				$("#previewImage").addClass(defalutPreviewId);
				$("#previewImageId").val(defalutPreviewId);
			});
			
			$("#previewImageId").keyup(function(e) {
				if(e.keyCode == 13) {
					$("#btnPreview").click();
				}
			});
			
			$("#btnPreviewClose").click(function() {
				$("#previewArea").hide();
			});
			
			/* Preview 이미지 End*/
			
			
			
			//페이지 로딩시 이벤트 발생
			$("input[name=portletType]:checked").click()
			$('#systemCode').trigger('change');

			/**
			 * Form Control End
			 */
			
			/**
			 * Validation Logic Start
			 */
			 
			 $("a.saveButton").click(function() { 
					$("#mainForm").trigger("submit"); 
					return false;  
			 });
			
			var validOptions = {
				rules : {
					<c:forEach var="i18nMessage" items="${portalPortlet.i18nMessageList}" varStatus="loopStatus">
					"i18nMessageList[${loopStatus.index}].itemMessage" : {
						required : function(element)
						{
							return $("input[name=portletType]:checked").val()=="HTML";
						}
						,maxlength : 100
					}
					,
					</c:forEach>
					file : {
						required : function(element) 
						{
							return $("input[name=portletType]:checked").val()!="HTML";
						}
						,
						accept : "war"
					}
					,
					portletCategoryId : {
						required  : true
					}
					,
					previewImageId : {
						required  : true
					}
					,
					normalViewUrl : {
						required : function(element)
						{
							return $("input[name=portletType]:checked").val()=="HTML";
						}
						,maxlength : 1000
					}, 
					configViewUrl :	{
						required : function(element)
						{
							return $("input[name=configMode]:checked").val()==1;
						}
						,maxlength : 1000
					},
					maxViewUrl :	{
						required : function(element)
						{
							return $("input[name=maxMode]:checked").val()==1;
						}
						,maxlength : 1000
					},
					moreViewUrl :	{
						required : function(element)
						{
							return $("input[name=moreMode]:checked").val()==1;
						}
						,maxlength : 1000
					},
					helpViewUrl :	{
						required : function(element)
						{
							return $("input[name=helpMode]:checked").val()==1;
						}
						,maxlength : 1000
					},
					iframeHeight :	{
						required : function(element)
						{
							return $("input[name=linkType]:checked").val()=='PortletIFrameExt';
						}
						,range : [0, 1000]

					},
					cacheLiveSecond :	{
						digits : true

					},
					cacheMaxCount :	{
						digits : true

					}
				},
				messages : {
					<c:forEach var="i18nMessage" items="${portalPortlet.i18nMessageList}" varStatus="loopStatus">
					"i18nMessageList[${loopStatus.index}].itemMessage" : {
						required : "<ikep4j:message key="NotEmpty.portalPortlet.locale" />"
						,maxlength : "<ikep4j:message key="Size.portalPortlet.locale" />"
					}
					,
					</c:forEach>
					file :	{
						required : "<ikep4j:message key="NotEmpty.portalPortlet.file" />"
						,accept : "<ikep4j:message key="Pattern.portalPortlet.file" />"
					}
					,
					portletCategoryId : {
						required  : "<ikep4j:message key="NotEmpty.portalPortlet.portletCategoryId" />"
					}
					,
					previewImageId : {
						required  : "<ikep4j:message key="NotEmpty.portalPortlet.previewImageId" />"
					}
					,
					normalViewUrl : {
						required : "<ikep4j:message key="NotEmpty.portalPortlet.normalViewUrl" />"
						,maxlength : "<ikep4j:message key="Size.portalPortlet.normalViewUrl" />"
						
					},
					configViewUrl : {
						required : "<ikep4j:message key="NotEmpty.portalPortlet.configViewUrl" />"
						,maxlength : "<ikep4j:message key="Size.portalPortlet.configViewUrl" />"
					},
					maxViewUrl :	{
						required : "<ikep4j:message key="NotEmpty.portalPortlet.maxViewUrl" />"
						,maxlength : "<ikep4j:message key="Size.portalPortlet.maxViewUrl" />"
					},
					moreViewUrl :	{
						required : "<ikep4j:message key="NotEmpty.portalPortlet.moreViewUrl" />"
						,maxlength : "<ikep4j:message key="Size.portalPortlet.moreViewUrl" />"
					},
					helpViewUrl :	{
						required : "<ikep4j:message key="NotEmpty.portalPortlet.helpViewUrl" />"
						,maxlength : "<ikep4j:message key="Size.portalPortlet.helpViewUrl" />"
					},
					iframeHeight :	{
						required : "<ikep4j:message key="NotEmpty.portalPortlet.iframeHeight" />"
						,range : "<ikep4j:message key="Range.portalPortlet.iframeHeight" />"
					},
					cacheLiveSecond :	{
						digits : "<ikep4j:message pre="${prefixMessage}" key="alert.digits" />"
					},
					cacheMaxCount :	{
						digits : "<ikep4j:message pre="${prefixMessage}" key="alert.digits" />"
					}
				}
				,
				notice : {
					previewImageId : "<ikep4j:message key="Notice.portalPortlet.checkbox" />",
					iframeHeight : {
						direction : "left",
						message : "<ikep4j:message pre="${prefixUI}" key="detail.iframeDescription" />"
					}
				}
				,   
				
			    submitHandler : function(form) { 
			    	$('#portletName').val($jq('#defaultLocaleValue').val());
					$("body").ajaxLoadStart("button");
					form.submit();
			    }
			};


			var validator = new iKEP.Validator("#mainForm", validOptions);

			/**
			 * Validation Logic End
			 */
			 
			 //D000002
			 //className,resourceName,operationName,"User,Group,Role,Job,Duty",th%
			 iKEP.loadSecurity("Portal-Portlet","","READ","User,Group,Role,Job,Duty,ExpUser");
		});
	})(jQuery); 
	

	</script>
	<link type="text/css" rel="stylesheet" href="<c:url value="${cssPath}"/>" />
	
<!--pageTitle Start-->
<div id="pageTitle">
	<h2><ikep4j:message pre='${prefixUI}' key='header.title' /></h2>
</div>
<!--//pageTitle End-->


<!--blockDetail Start-->
<div class="blockDetail">

	<div id="viewDiv">
		<form:form  method="post"	name="listForm" id="listForm">
			<input type="hidden" id="pageIndex" name="pageIndex" value="${searchCondition.pageIndex }"/>
		</form:form> 
		<form method="post"	name="mainForm" id="mainForm" enctype="multipart/form-data" action="<c:url value='/portal/admin/screen/portlet/createPortlet.do'/>">
			<input type="hidden" id="pageIndex" name="pageIndex" value="${searchCondition.pageIndex }"/>
			<table id="mainTable" summary="<ikep4j:message pre="${prefixUI}" key="detail.summary" />">
				<caption></caption>
				<colgroup>
					<col width="10%"/>
					<col width="8%"/>
					<col width="82%"/>
				</colgroup>
				<tbody>
					<tr>
						<th scope="row" colspan="2"><ikep4j:message pre="${prefixUI}" key="detail.portletType" /></th>
						<td >
						<spring:bind path="portalPortlet.portletType">
						<input type="radio" class="radio" title="${status.expression}" name="${status.expression}" <c:if test="${status.value eq 'HTML' || empty status.value}">checked="checked"</c:if> value="HTML" /> HTML
						<input type="radio" class="radio" title="${status.expression}" name="${status.expression}" <c:if test="${status.value eq 'JSR168'}">checked="checked"</c:if> value="JSR168"/> JSR168
						<input type="radio" class="radio" title="${status.expression}" name="${status.expression}" <c:if test="${status.value eq 'JSR286'}">checked="checked"</c:if> value="JSR286"/> JSR286
						</spring:bind>
						</td>
					</tr>
					<tr id="fileTr">
						<th scope="row" colspan="2">* <ikep4j:message pre="${prefixUI}" key="detail.warFileName" /></th>
						<td>
						<div><input type="file" name="file" id="file" size="50"/></div>
						</td>
					</tr>
					
					<tr id="localeTr1">
						<th scope="row" rowspan="${localeSize}" >* <ikep4j:message pre="${prefixUI}" key="detail.portletName" /></th>
						<c:forEach var="i18nMessage" items="${portalPortlet.i18nMessageList}" varStatus="loopStatus">
						<c:if test="${i18nMessage.fieldName eq 'portletName' }">
						<c:if test="${i18nMessage.index > 1}">
							<tr id="localeTr1${i18nMessage.index}" >
						</c:if>
								<th scope="row">*<c:out value="${i18nMessage.localeCode}"/></th>
								<td>
								<div>
								<spring:bind path="portalPortlet.i18nMessageList[${loopStatus.index}].itemMessage">
									<input type="text" name="i18nMessageList[${loopStatus.index}].itemMessage" value="${i18nMessage.itemMessage}" size="50"  <c:if test="${i18nMessage.localeCode == userLocaleCode }">id="defaultLocaleValue" </c:if> />
									<input type="hidden" name="i18nMessageList[${loopStatus.index}].localeCode" value="${i18nMessage.localeCode}" />
									<input type="hidden" name="i18nMessageList[${loopStatus.index}].fieldName" value="${i18nMessage.fieldName}" />
									<label for="${status.expression}" class="serverError"> ${status.errorMessage}</label>
								</spring:bind>
								</div>
								</td>
							</tr>
						</c:if>
						</c:forEach>
						<input type="hidden" name="portletName" id="portletName" value="" />
					<!-- Sample -->
					<tr id="localeTr2">
						<th scope="row" rowspan="${localeSize}">* <ikep4j:message pre="${prefixUI}" key="detail.portletDescription" /></th>
						<c:forEach var="i18nMessage" items="${portalPortlet.i18nMessageList}" varStatus="loopStatus">
						<c:if test="${i18nMessage.fieldName eq 'portletDesc' }">
						<c:if test="${i18nMessage.index > 1}">
							<tr id="localeTr2${i18nMessage.index}">
						</c:if>
								<th scope="row">*<c:out value="${i18nMessage.localeCode}"/></th>
								<td>
								<div>
								<spring:bind path="portalPortlet.i18nMessageList[${loopStatus.index}].itemMessage">
									<input type="text" name="i18nMessageList[${loopStatus.index}].itemMessage"  value="${i18nMessage.itemMessage}" size="50" />
									<input type="hidden" name="i18nMessageList[${loopStatus.index}].localeCode" value="${i18nMessage.localeCode}" />
									<input type="hidden" name="i18nMessageList[${loopStatus.index}].fieldName" value="${i18nMessage.fieldName}" />
									<label for="${status.expression}" class="serverError"> ${status.errorMessage}</label>
								</spring:bind>
								</div>
								</td>
							</tr>
						</c:if>
						</c:forEach>
					<!-- Sample -->
					
					<tr>
						<th scope="row" colspan="2">* <ikep4j:message pre="${prefixUI}" key="detail.systemName" /></th>
						<td>
						<div>
						<spring:bind path="portalPortlet.systemCode">
						<select name="${status.expression}" id="${status.expression}">
							<c:forEach var="system" items="${portalSystemList}">
								<c:if test="${system.systemCode != '0'}">
									<option value='<c:out value="${system.systemCode}"/>' <c:if test="${system.systemCode eq status.value}">selected</c:if>><c:out value="${system.systemName}"/></option>
								</c:if>
							</c:forEach>
						</select>
						<label for="${status.expression}" class="serverError"> ${status.errorMessage}</label>
						</spring:bind>
						</div>
						</td>
					</tr>
					<tr>
						<th scope="row" colspan="2">* <ikep4j:message pre="${prefixUI}" key="detail.portletCategoryName" /></th>
						<td>
						<div>
						<spring:bind path="portalPortlet.portletCategoryId">
						<select name="${status.expression}" id="${status.expression}">
						</select>
						<label for="${status.expression}" class="serverError"> ${status.errorMessage}</label>
						</spring:bind>
						</div>
						</td>
					</tr>
					<tr id="previewClassIdTr">
						<th scope="row" colspan="2">* <ikep4j:message pre="${prefixUI}" key="detail.previewImageId" /></th>
						<td>
						<div>
						<spring:bind path="portalPortlet.previewImageId">
						<input type="text"  name="${status.expression}" id="${status.expression}" value="" size="20"/> 
						<label for="${status.expression}" class="serverError"> ${status.errorMessage}</label>
						</spring:bind>
						<a class="button_s" href="#a" id="btnPreview">
							<span><ikep4j:message pre="${prefixUI}" key="button.preview" /></span>
						</a>
						<a class="button_s" href="#a" id="btnDefaultPreview">
							<span><ikep4j:message pre="${prefixUI}" key="detail.previewIdDefault" /></span>
						</a>
							<!--layer start-->
							<div id="previewArea" class="process_layer" style="position:absolute;min-width:150px;margin-top:10px;z-index:99;;display:none">
								<div class="process_layer_t">
									<ikep4j:message pre="${prefixUI}" key="button.preview" />
									<a href="#a" id="btnPreviewClose"><img src="<c:url value="/base/images/icon/ic_close_layer.gif"/>" alt="Close" /></a>
								</div>
								<div>
									<div style="margin-top:10px;"> 
										<a id="previewImage" class="img_portlet_01"></a>
									</div>
								</div>
							</div>		
							<!--layer end-->	
						</div>
						</td>
					</tr>
					<tr>
						<th scope="row" colspan="2"><ikep4j:message pre="${prefixUI}" key="detail.moveable" /></th>
						<td>
						<div>
						<spring:bind path="portalPortlet.moveable">
						<input type="radio" class="radio" title="<ikep4j:message pre="${prefixUI}" key="detail.radio.possible" />" name="${status.expression}" <c:if test="${status.value == 1}">checked="checked"</c:if> value="1" /> <ikep4j:message pre="${prefixUI}" key="detail.radio.possible" />
						<input type="radio" class="radio" title="<ikep4j:message pre="${prefixUI}" key="detail.radio.impossible" />" name="${status.expression}" <c:if test="${status.value == 0}">checked="checked"</c:if> value="0" /> <ikep4j:message pre="${prefixUI}" key="detail.radio.impossible" />
						</spring:bind>
						</div>
						</td>
					</tr>
					
					<tr id="normalViewUrlTr">
						<th scope="row" colspan="2">* <ikep4j:message pre="${prefixUI}" key="detail.normalViewUrl" /></th>
						<td>
						<div>
						<spring:bind path="portalPortlet.normalViewUrl">
						<input type="text" name="${status.expression}" id="${status.expression}" value="${status.value}" size="50"/>
						<label for="${status.expression}" class="serverError"> ${status.errorMessage}</label>
						</spring:bind>
						</div>
						</td>
					</tr>
					<tr id="linkTypeTr">
						<th scope="row" colspan="2"><ikep4j:message pre="${prefixUI}" key="detail.linkType" /></th>
						<td>
						<div>
						<span>
						<spring:bind path="portalPortlet.linkType">
						<input type="radio" class="radio" title="<ikep4j:message pre="${prefixUI}" key="detail.radio.portletSimple" />" name="${status.expression}" <c:if test="${status.value eq 'PortletSimple' || empty status.value}">checked="checked"</c:if> value="PortletSimple" /> <ikep4j:message pre="${prefixUI}" key="detail.radio.portletSimple" />
						<input type="radio" class="radio" title="<ikep4j:message pre="${prefixUI}" key="detail.radio.portletIframe" />" name="${status.expression}" <c:if test="${status.value eq 'PortletIFrame'}">checked="checked"</c:if> value="PortletIFrame" /> <ikep4j:message pre="${prefixUI}" key="detail.radio.portletIframe" />
						<input type="radio" class="radio" title="<ikep4j:message pre="${prefixUI}" key="detail.radio.portletIframeExt" />" name="${status.expression}" <c:if test="${status.value eq 'PortletIFrameExt'}">checked="checked"</c:if> value="PortletIFrameExt" /> <ikep4j:message pre="${prefixUI}" key="detail.radio.portletIframeExt" />
						</spring:bind>
						</span>
						<span id="iframeHeightArea" style="padding-left:20px;display:none">
						<ikep4j:message pre="${prefixUI}" key="detail.iframeHeight" /> : 
						<spring:bind path="portalPortlet.iframeHeight">
						<input type="text" name="${status.expression}" id="${status.expression}" value="${status.value}" size="4"/> px
						<label for="${status.expression}" class="serverError"> ${status.errorMessage}</label>
						</spring:bind>
						</span>
						</div>
						</td>
					</tr>
					<tr id="configModeTr">
						<th scope="row" colspan="2"><ikep4j:message pre="${prefixUI}" key="detail.configMode" /> </th>
						<td>
						<div>
						<spring:bind path="portalPortlet.configMode">
						<input type="radio" class="radio" title="<ikep4j:message pre="${prefixUI}" key="detail.radio.use" />" name="${status.expression}" <c:if test="${status.value == 1 || empty status.value}">checked="checked"</c:if> id="configMode" value="1" /> <ikep4j:message pre="${prefixUI}" key="detail.radio.use" />
						<input type="radio" class="radio" title="<ikep4j:message pre="${prefixUI}" key="detail.radio.unuse" />" name="${status.expression}" <c:if test="${status.value == 0 }">checked="checked"</c:if> id="configMode"  value="0" /> <ikep4j:message pre="${prefixUI}" key="detail.radio.unuse" />
						</spring:bind>
						</div>
						</td>
					</tr>
					<tr id="configViewUrlTr">
						<th scope="row" colspan="2">* <ikep4j:message pre="${prefixUI}" key="detail.configViewUrl" /></th>
						<td>
						<div>
						<spring:bind path="portalPortlet.configViewUrl">
						<input type="text" name="${status.expression}" id="${status.expression}" value="${status.value}" size="50"/>
						<label for="${status.expression}" class="serverError"> ${status.errorMessage}</label>
						</spring:bind>
						</div>
						</td>
					</tr>
					<tr id="maxModeTr">
						<th scope="row" colspan="2"><ikep4j:message pre="${prefixUI}" key="detail.maxMode" /> </th>
						<td>
						<div>
						<spring:bind path="portalPortlet.maxMode">
						<input type="radio" class="radio" title="<ikep4j:message pre="${prefixUI}" key="detail.radio.use" />" name="${status.expression}" <c:if test="${status.value == 1}">checked="checked"</c:if> value="1" /> <ikep4j:message pre="${prefixUI}" key="detail.radio.use" />
						<input type="radio" class="radio" title="<ikep4j:message pre="${prefixUI}" key="detail.radio.unuse" />" name="${status.expression}" <c:if test="${status.value == 0}">checked="checked"</c:if> value="0" /> <ikep4j:message pre="${prefixUI}" key="detail.radio.unuse" />
						</spring:bind>
						</div>
						</td>
					</tr>
					<tr id="maxViewUrlTr">
						<th scope="row" colspan="2">* <ikep4j:message pre="${prefixUI}" key="detail.maxViewUrl" /></th>
						<td>
						<div>
						<spring:bind path="portalPortlet.maxViewUrl">
						<input type="text" name="${status.expression}" id="${status.expression}" value="${status.value}" size="50"/>
						<label for="${status.expression}" class="serverError"> ${status.errorMessage}</label>
						</spring:bind>
						</div>
						</td>
					</tr>
					<tr id="moreModeTr">
						<th scope="row" colspan="2"><ikep4j:message pre="${prefixUI}" key="detail.moreMode" /> </th>
						<td>
						<div>
						<spring:bind path="portalPortlet.moreMode">
						<input type="radio" class="radio" title="<ikep4j:message pre="${prefixUI}" key="detail.radio.use" />" name="${status.expression}" <c:if test="${status.value == 1}">checked="checked"</c:if> value="1" /> <ikep4j:message pre="${prefixUI}" key="detail.radio.use" />
						<input type="radio" class="radio" title="<ikep4j:message pre="${prefixUI}" key="detail.radio.unuse" />" name="${status.expression}" <c:if test="${status.value == 0}">checked="checked"</c:if> value="0" /> <ikep4j:message pre="${prefixUI}" key="detail.radio.unuse" />
						</spring:bind>
						</div>
						</td>
					</tr>
					<tr id="moreViewUrlTr">
						<th scope="row" colspan="2">* <ikep4j:message pre="${prefixUI}" key="detail.moreViewUrl" /> </th>
						<td>
						<div>
						<spring:bind path="portalPortlet.moreViewUrl">
						<input type="text" name="${status.expression}" id="${status.expression}" value="${status.value}" size="50"/>
						<label for="${status.expression}" class="serverError"> ${status.errorMessage}</label>
						</spring:bind>
						</div>
						</td>
					</tr>
					<tr id="helpModeTr">
						<th scope="row" colspan="2"><ikep4j:message pre="${prefixUI}" key="detail.helpMode" /> </th>
						<td>
						<div>
						<spring:bind path="portalPortlet.helpMode">
						<input type="radio" class="radio" title="<ikep4j:message pre="${prefixUI}" key="detail.radio.use" />" name="${status.expression}" <c:if test="${status.value == 1}">checked="checked"</c:if> value="1" /> <ikep4j:message pre="${prefixUI}" key="detail.radio.use" />
						<input type="radio" class="radio" title="<ikep4j:message pre="${prefixUI}" key="detail.radio.unuse" />" name="${status.expression}" <c:if test="${status.value == 0}">checked="checked"</c:if> value="0" /> <ikep4j:message pre="${prefixUI}" key="detail.radio.unuse" />
						</spring:bind>
						</div>
						</td>
					</tr>
					<tr id="helpViewUrlTr">
						<th scope="row" colspan="2">* <ikep4j:message pre="${prefixUI}" key="detail.helpViewUrl" /> </th>
						<td>
						<div>
						<spring:bind path="portalPortlet.helpViewUrl">
						<input type="text" name="${status.expression}" id="${status.expression}" value="${status.value}" size="50"/>
						<label for="${status.expression}" class="serverError"> ${status.errorMessage}</label>
						</spring:bind>
						</div>
						</td>
					</tr>
					<tr id="reloadMode">
						<th scope="row" colspan="2"><ikep4j:message pre="${prefixUI}" key="detail.reloadMode" /> </th>
						<td>
						<div>
						<spring:bind path="portalPortlet.reloadMode">
						<input type="radio" class="radio" title="<ikep4j:message pre="${prefixUI}" key="detail.radio.use" />" name="${status.expression}" <c:if test="${status.value == 1}">checked="checked"</c:if> value="1" /> <ikep4j:message pre="${prefixUI}" key="detail.radio.use" />
						<input type="radio" class="radio" title="<ikep4j:message pre="${prefixUI}" key="detail.radio.unuse" />" name="${status.expression}" <c:if test="${status.value == 0}">checked="checked"</c:if> value="0" /> <ikep4j:message pre="${prefixUI}" key="detail.radio.unuse" />
						</spring:bind>
						</div>
						</td>
					</tr>
					<tr>
						<th scope="row" colspan="2"><ikep4j:message pre="${prefixUI}" key="detail.removeMode" /> </th>
						<td>
						<div>
						<spring:bind path="portalPortlet.removeMode">
						<input type="radio" class="radio" title="<ikep4j:message pre="${prefixUI}" key="detail.radio.use" />" name="${status.expression}" <c:if test="${status.value == 1}">checked="checked"</c:if> value="1" /> <ikep4j:message pre="${prefixUI}" key="detail.radio.use" />
						<input type="radio" class="radio" title="<ikep4j:message pre="${prefixUI}" key="detail.radio.unuse" />" name="${status.expression}" <c:if test="${status.value == 0}">checked="checked"</c:if> value="0" /> <ikep4j:message pre="${prefixUI}" key="detail.radio.unuse" />
						</spring:bind>
						</div>
						</td>
					</tr>
					<tr>
						<th scope="row" colspan="2"><ikep4j:message pre="${prefixUI}" key="detail.multipleMode" /> </th>
						<td>
						<div>
						<spring:bind path="portalPortlet.multipleMode">
						<input type="radio" class="radio" title="<ikep4j:message pre="${prefixUI}" key="detail.radio.use" />" name="${status.expression}" <c:if test="${status.value == 1}">checked="checked"</c:if> value="1" /> <ikep4j:message pre="${prefixUI}" key="detail.radio.use" />
						<input type="radio" class="radio" title="<ikep4j:message pre="${prefixUI}" key="detail.radio.unuse" />"name="${status.expression}" <c:if test="${status.value == 0}">checked="checked"</c:if> value="0" /> <ikep4j:message pre="${prefixUI}" key="detail.radio.unuse" />
						</spring:bind>
						</div>
						</td>
					</tr>
					<tr>
						<th scope="row" colspan="2"><ikep4j:message pre="${prefixUI}" key="detail.publicOption" /> </th>
						<td>
						<div>
						<spring:bind path="portalPortlet.publicOption">
						<input type="radio" class="radio" title="<ikep4j:message pre="${prefixUI}" key="detail.radio.public" />" name="${status.expression}" <c:if test="${status.value == 1}">checked="checked"</c:if> value="1" /> <ikep4j:message pre="${prefixUI}" key="detail.radio.public" />
						<input type="radio" class="radio" title="<ikep4j:message pre="${prefixUI}" key="detail.radio.private" />" name="${status.expression}" <c:if test="${status.value == 0}">checked="checked"</c:if> value="0" /> <ikep4j:message pre="${prefixUI}" key="detail.radio.private" />
						</spring:bind>
						</div>
						</td>
					</tr>
					<tr>
						<th scope="row" colspan="2"><ikep4j:message pre="${prefixUI}" key="detail.headerMode" /> </th>
						<td>
						<div>
						<spring:bind path="portalPortlet.headerMode">
						<input type="radio" class="radio" title="<ikep4j:message pre="${prefixUI}" key="detail.radio.view" />" name="${status.expression}" <c:if test="${status.value == 1}">checked="checked"</c:if> value="1" /> <ikep4j:message pre="${prefixUI}" key="detail.radio.view" />
						<input type="radio" class="radio" title="<ikep4j:message pre="${prefixUI}" key="detail.radio.unview" />" name="${status.expression}" <c:if test="${status.value == 0}">checked="checked"</c:if> value="0" /> <ikep4j:message pre="${prefixUI}" key="detail.radio.unview" />
						</spring:bind>
						</div>
						</td>
					</tr>
					<tr>
						<th scope="row" colspan="2"><ikep4j:message pre="${prefixUI}" key="detail.status" /> </th>
						<td>
						<div>
						<spring:bind path="portalPortlet.status">
						<input type="radio" class="radio" title="<ikep4j:message pre="${prefixUI}" key="detail.radio.use" />"   name="${status.expression}" <c:if test="${status.value == 1}">checked="checked"</c:if> value="1" /> <ikep4j:message pre="${prefixUI}" key="detail.radio.use" />
						<input type="radio" class="radio" title="<ikep4j:message pre="${prefixUI}" key="detail.radio.unuse" />" name="${status.expression}" <c:if test="${status.value == 0}">checked="checked"</c:if> value="0" /> <ikep4j:message pre="${prefixUI}" key="detail.radio.unuse" />
						</spring:bind>
						</div>
						</td>
					</tr>
					<tr>
						<th scope="row" colspan="2"><ikep4j:message pre="${prefixUI}" key="detail.shareYn" /> </th>
						<td>
						<div>
						<spring:bind path="portalPortlet.shareYn">
						<input type="radio" class="radio" title="<ikep4j:message pre="${prefixUI}" key="detail.share" />"   name="${status.expression}" <c:if test="${status.value == 'Y'}">checked="checked"</c:if> value="Y" /> <ikep4j:message pre="${prefixUI}" key="detail.share" />
						<input type="radio" class="radio" title="<ikep4j:message pre="${prefixUI}" key="detail.nonshared" />" name="${status.expression}" <c:if test="${status.value == 'N'}">checked="checked"</c:if> value="N" /> <ikep4j:message pre="${prefixUI}" key="detail.nonshared" />
						</spring:bind>
						</div>
						</td>
					</tr>
					<tr>
						<th scope="row" rowspan="4"><ikep4j:message pre="${prefixUI}" key="detail.cache" /> </th>
						<th scope="row"><ikep4j:message pre="${prefixUI}" key="detail.cacheYn" /> </th>
						<td>
						<div>
						<spring:bind path="portalPortlet.cacheYn">
						<input type="radio" class="radio" title="<ikep4j:message pre="${prefixUI}" key="detail.radio.use" />" name="${status.expression}" <c:if test="${status.value == 'Y'}">checked="checked"</c:if> value="Y" /> <ikep4j:message pre="${prefixUI}" key="detail.radio.use" />
						<input type="radio" class="radio" title="<ikep4j:message pre="${prefixUI}" key="detail.radio.unuse" />" name="${status.expression}" <c:if test="${status.value == 'N'}">checked="checked"</c:if> value="N" /> <ikep4j:message pre="${prefixUI}" key="detail.radio.unuse" />
						</spring:bind>
						</div>
						</td>
					</tr>
					<tr>
						<th scope="row"><ikep4j:message pre="${prefixUI}" key="detail.cacheNameStr" /> </th>
						<td>
						<div>
						<spring:bind path="portalPortlet.cacheNameStr">
						<input type="text" name="${status.expression}" id="${status.expression}" value="" size="50"/>
						<label for="${status.expression}" class="serverError"> ${status.errorMessage}</label>
						</spring:bind>
						</div>
						</td>
					</tr>
					<tr>
						<th scope="row"><ikep4j:message pre="${prefixUI}" key="detail.cacheLiveSecond" /> </th>
						<td>
						<div>
						<spring:bind path="portalPortlet.cacheLiveSecond">
						<input type="text" name="${status.expression}" id="${status.expression}" value="" size="7"/>
						<label for="${status.expression}" class="serverError"> ${status.errorMessage}</label>
						</spring:bind>
						</div>
						</td>
					</tr>
					<tr>
						<th scope="row"><ikep4j:message pre="${prefixUI}" key="detail.cacheMaxCount" /> </th>
						<td>
						<div>
						<spring:bind path="portalPortlet.cacheMaxCount">
						<input type="text" name="${status.expression}" id="${status.expression}" value="" size="7"/>
						<label for="${status.expression}" class="serverError"> ${status.errorMessage}</label>
						</spring:bind>
						</div>
						</td>
					</tr>
				</tbody>
			</table>
			<div id="securityArea"></div>
		</form>
	</div>
</div>
<!--//blockDetail End-->

<!--blockButton Start-->
<div class="blockButton">
	<ul>
		<li>
		<a class="button saveButton" href="#a"><span><ikep4j:message pre="${prefixUI}" key="button.save" /></span></a>
		<a class="button" href="javascript: listPortlet();"><span><ikep4j:message pre="${prefixUI}" key="button.list" /></span></a>
		</li>
	</ul>
</div>
<!--//blockButton End-->