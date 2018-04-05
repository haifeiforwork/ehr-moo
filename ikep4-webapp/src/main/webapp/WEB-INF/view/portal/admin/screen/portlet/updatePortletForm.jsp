<%@ include file="/base/common/taglibs.jsp"%>
<%@ page language="java" errorPage="/common/error.jsp"
	pageEncoding="UTF-8" contentType="text/html;charset=utf-8"%>
<%@ taglib uri="/WEB-INF/tld/ikep4j.tld" prefix="ikep4j"%>

<c:set var="prefixMessage" value="message.portal.admin.screen.portlet.updatePortletForm"/>
<c:set var="prefixUI" value="ui.portal.admin.screen.portlet.updatePortletForm"/>

<script type="text/javascript">
		function listPortlet() {
			$jq("#listForm").attr("action","<c:url value='/portal/admin/screen/portlet/listPortlet.do'/>");
			$jq("#listForm").submit();	  
		}
		
		function readPortlet(portletId) {
			$jq("#listForm").attr("action","<c:url value='/portal/admin/screen/portlet/readPortlet.do'/>");
			$jq("#listForm").submit();	  
		}
		
		(function($) {

			/**
			 * onload시 수행할 코드
			 */
			$(document).ready(function() {
				/**
				 * 포틀릿 타입 선택
				 */
				
				if($("input[name=portletType]").val()=="HTML") {
					$("#fileTr").hide();
				}
				else {
					$("#linkTypeTr").hide();
				}
				
				if($("input[name=linkType]:checked").val()=="PortletIFrameExt") {
					$("#iframeHeightArea").show();
				}
				
				
				if($("input[name=configMode]:checked").val()==0){$("#configViewUrlTr").hide();}
				if($("input[name=maxMode]:checked").val()==0){$("#maxViewUrlTr").hide();}
				if($("input[name=moreMode]:checked").val()==0){$("#moreViewUrlTr").hide();}
				if($("input[name=helpMode]:checked").val()==0){$("#helpViewUrlTr").hide();}
				
				$("#defaultLocaleValue").focus();
				
				
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
				
				
				$("a.saveButton").click(function() { 
					$("#mainForm").trigger("submit"); 
					return false;  
			    });
				/**
				 * Validation Logic Start
				 */
				
				var validOptions = {
					rules : {
						<c:forEach var="i18nMessage" items="${portalPortlet.i18nMessageList}" varStatus="loopStatus">
						"i18nMessageList[${loopStatus.index}].itemMessage" : {
							required : function(element)
							{
								return $("input[name=portletType]").val()=="HTML";
							}
							,maxlength : 100
						}
						,
						</c:forEach>
						portletCategoryId : {
							required  : true
						}
						,
						previewImageId : {
							required  : true
						}
						,
						normalViewUrl : {
							required : true
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
						$("body").ajaxLoadStart("button");
						$jq('#portletName').val($jq('#defaultLocaleValue').val());
						$.ajax({
							url : "<c:url value='/portal/admin/screen/portlet/updatePortlet.do'/>",
							type : "post",
							data : $(form).serialize(),
							success : function(result) {
								alert("<ikep4j:message pre="${prefixMessage}" key="alert.afterSave" />");
								location.href="<c:url value='/portal/admin/screen/portlet/readPortlet.do'/>?portletId="+result.portletId;
							},
							error : function(xhr, exMessage) {		
								var errorItems = $.parseJSON(xhr.responseText).exception;
								validator.showErrors(errorItems);
							}
						});
					}
				};

				var validator = new iKEP.Validator("#mainForm", validOptions);

				/**
				 * Validation Logic End
				 */
				 
				//className,resourceName,operationName,"User,Group,Role,Job,Duty",th%
				iKEP.loadSecurity("Portal-Portlet","${portalPortlet.portletId}","READ","User,Group,Role,Job,Duty,ExpUser");
				 
				
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
			<input type="hidden" name="pageIndex" value="${searchCondition.pageIndex }"/>
			<input type="hidden" name="portletId" value="${portalPortlet.portletId}"/>
		</form:form>
		<form:form modelAttribute="portalPortlet" method="post"	name="mainForm" id="mainForm">
			<input type="hidden" name="portletId" value="${portalPortlet.portletId}"/>
			<input type="hidden" name="ownerId" value="${portalPortlet.ownerId}"/>
			<input type="hidden" name="pageIndex" value="${searchCondition.pageIndex }"/>
			<table id="mainTable" summary="<ikep4j:message pre="${prefixUI}" key="detail.summary" />">
				<caption></caption>
				<colgroup>
					<col width="10%"/>
					<col width="8%"/>
					<col width="82%"/>
				</colgroup>
				<tbody>

					<tr>
						<th scope="row" colspan="2"><form:label for="portletType" path="portletType"><ikep4j:message pre="${prefixUI}" key="detail.portletType" /></form:label></th>
						<td>
						<c:out value="${portalPortlet.portletType}"/>
						<input type="hidden" name="portletType" value="${portalPortlet.portletType}"/>
						</td>
					</tr>
					<tr id="fileTr">
						<th scope="row" colspan="2"><ikep4j:message pre="${prefixUI}" key="detail.warFileName" /></th>
						<td>
						<div>
						<a href='<c:url value="/support/fileugpload/downloadFile.do?fileId=${portalPortlet.warFileId}"/>'>${portalPortlet.warFileName}</a>
						</div>
						</td>
					</tr>
					<tr>
						<th scope="row" rowspan="${localeSize}"><form:label	for="portletName" path="portletName" cssErrorClass="error">* <ikep4j:message pre="${prefixUI}" key="detail.portletName" /></form:label></th>
						<c:forEach var="i18nMessage" items="${portalPortlet.i18nMessageList}" varStatus="loopStatus">
							<c:if test="${i18nMessage.fieldName eq 'portletName' }">
							<c:if test="${i18nMessage.index > 1}">
								<tr>
							</c:if>
									<th scope="row">*<c:out value="${i18nMessage.localeCode}"/></th>
									<td>
									<div>
									
										<input type="text" name="i18nMessageList[${loopStatus.index}].itemMessage" value="${i18nMessage.itemMessage}" size="50" <c:if test="${i18nMessage.localeCode == userLocaleCode }">id="defaultLocaleValue" </c:if> />
										<input type="hidden" name="i18nMessageList[${loopStatus.index}].localeCode" value="${i18nMessage.localeCode}" />
										<input type="hidden" name="i18nMessageList[${loopStatus.index}].fieldName" value="${i18nMessage.fieldName}" />
										<input type="hidden" name="i18nMessageList[${loopStatus.index}].messageId" value="${i18nMessage.messageId}" />
									
									</div>
									</td>
								</tr>
							</c:if>
						</c:forEach>
						<input type="hidden" name="portletName" id="portletName" value="" />
					<tr>
						<th scope="row" rowspan="${localeSize}">* <ikep4j:message pre="${prefixUI}" key="detail.portletDescription" /></th>
						<c:forEach var="i18nMessage" items="${portalPortlet.i18nMessageList}" varStatus="loopStatus">
							<c:if test="${i18nMessage.fieldName eq 'portletDesc' }">
							<c:if test="${i18nMessage.index > 1}">
								<tr>
							</c:if>
									<th scope="row">*<c:out value="${i18nMessage.localeCode}"/></th>
									<td>
									<div>
									
										<input type="text" name="i18nMessageList[${loopStatus.index}].itemMessage" value="${i18nMessage.itemMessage}" size="50"  />
										<input type="hidden" name="i18nMessageList[${loopStatus.index}].localeCode" value="${i18nMessage.localeCode}" />
										<input type="hidden" name="i18nMessageList[${loopStatus.index}].fieldName" value="${i18nMessage.fieldName}" />
										<input type="hidden" name="i18nMessageList[${loopStatus.index}].messageId" value="${i18nMessage.messageId}" />
										
									</div>
									</td>
								</tr>
							</c:if>
						</c:forEach>
					<tr>
						<th scope="row" colspan="2">* <ikep4j:message pre="${prefixUI}" key="detail.systemName" /></th>
						<td>
						<div>
						<select name="systemCode" id="systemCode">
							<c:forEach var="system" items="${portalSystemList}">
								<c:if test="${system.systemCode != '0'}">
									<option value='<c:out value="${system.systemCode}"/>' <c:if test="${system.systemCode == portalPortlet.systemCode}">selected</c:if>><c:out value="${system.systemName}"/></option>
								</c:if>
							</c:forEach>
						</select>
						</div>
						</td>
					</tr>
					<tr>
						<th scope="row" colspan="2">* <ikep4j:message pre="${prefixUI}" key="detail.portletCategoryName" /></th>
						<td>
						<div>
						<select name="portletCategoryId" id="portletCategoryId">
						</select>
						</div>
						</td>
					</tr>
					<tr id="previewClassIdTr">
						<th scope="row" colspan="2">* <ikep4j:message pre="${prefixUI}" key="detail.previewImageId" /></th>
						<td>
						<div>
						
						<input type="text" name="previewImageId" id="previewImageId" value="${portalPortlet.previewImageId}" size="20"/> 
						
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
										<a id="previewImage" class="${portalPortlet.previewImageId}"></a>
									</div>
								</div>
							</div>		
							<!--layer end-->
						</div>
						</td>
					</tr>
					<tr>
						<th scope="row" colspan="2"><form:label for="moveable" path="moveable"><ikep4j:message pre="${prefixUI}" key="detail.moveable" /></form:label></th>
						<td>
						<div>
						<input type="radio" class="radio" title="<ikep4j:message pre="${prefixUI}" key="detail.radio.possible" />" name="moveable" <c:if test="${portalPortlet.moveable == 1}">checked="checked"</c:if> value="1" /> <ikep4j:message pre="${prefixUI}" key="detail.radio.possible" />
						<input type="radio" class="radio" title="<ikep4j:message pre="${prefixUI}" key="detail.radio.impossible" />" name="moveable" <c:if test="${portalPortlet.moveable == 0}">checked="checked"</c:if> value="0" /> <ikep4j:message pre="${prefixUI}" key="detail.radio.impossible" />
						</div>
						</td>
					</tr>
					
					<tr id="normalViewUrlTr">
						<th scope="row" colspan="2">* <ikep4j:message pre="${prefixUI}" key="detail.normalViewUrl" /></th>
						<td>
						<div>
						<input type="text" name="normalViewUrl" id="normalViewUrl" value="${portalPortlet.normalViewUrl}" size="50"/>
						</div>
						</td>
					</tr>
					<tr id="linkTypeTr">
						<th scope="row" colspan="2"><ikep4j:message pre="${prefixUI}" key="detail.linkType" /></th>
						<td>
						<div>
						<span>
						<input type="radio" class="radio" title="<ikep4j:message pre="${prefixUI}" key="detail.radio.portletSimple" />" name="linkType" <c:if test="${portalPortlet.linkType eq 'PortletSimple' || empty status.value}">checked="checked"</c:if> value="PortletSimple" /> <ikep4j:message pre="${prefixUI}" key="detail.radio.portletSimple" />
						<input type="radio" class="radio" title="<ikep4j:message pre="${prefixUI}" key="detail.radio.portletIframe" />" name="linkType" <c:if test="${portalPortlet.linkType eq 'PortletIFrame'}">checked="checked"</c:if> value="PortletIFrame" /><ikep4j:message pre="${prefixUI}" key="detail.radio.portletIframe" />
						<input type="radio" class="radio" title="<ikep4j:message pre="${prefixUI}" key="detail.radio.portletIframeExt" />" name="linkType" <c:if test="${portalPortlet.linkType eq 'PortletIFrameExt'}">checked="checked"</c:if> value="PortletIFrameExt" /> <ikep4j:message pre="${prefixUI}" key="detail.radio.portletIframeExt" />
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
						<input type="radio" class="radio" title="<ikep4j:message pre="${prefixUI}" key="detail.radio.use" />" name="configMode" <c:if test="${portalPortlet.configMode == 1 || empty portalPortlet.configMode}">checked="checked"</c:if> id="configMode" value="1" /> <ikep4j:message pre="${prefixUI}" key="detail.radio.use" />
						<input type="radio" class="radio" title="<ikep4j:message pre="${prefixUI}" key="detail.radio.unuse" />" name="configMode" <c:if test="${portalPortlet.configMode == 0 }">checked="checked"</c:if> id="configMode"  value="0" /> <ikep4j:message pre="${prefixUI}" key="detail.radio.unuse" />
						</div>
						</td>
					</tr>
					<tr id="configViewUrlTr">
						<th scope="row" colspan="2">* <ikep4j:message pre="${prefixUI}" key="detail.configViewUrl" /></th>
						<td>
						<div>
						<input type="text" name="configViewUrl" id="configViewUrl" value="${portalPortlet.configViewUrl}" size="50"/>
						</div>
						</td>
					</tr>
					<tr id="maxModeTr">
						<th scope="row" colspan="2"><ikep4j:message pre="${prefixUI}" key="detail.maxMode" /> </th>
						<td>
						<div>
						<input type="radio" class="radio" title="<ikep4j:message pre="${prefixUI}" key="detail.radio.use" />" name="maxMode" <c:if test="${portalPortlet.maxMode == 1}">checked="checked"</c:if> value="1" /> <ikep4j:message pre="${prefixUI}" key="detail.radio.use" />
						<input type="radio" class="radio" title="<ikep4j:message pre="${prefixUI}" key="detail.radio.unuse" />" name="maxMode" <c:if test="${portalPortlet.maxMode == 0}">checked="checked"</c:if> value="0" /> <ikep4j:message pre="${prefixUI}" key="detail.radio.unuse" />
						</div>
						</td>
					</tr>
					<tr id="maxViewUrlTr">
						<th scope="row" colspan="2">* <ikep4j:message pre="${prefixUI}" key="detail.maxViewUrl" /></th>
						<td>
						<div>
						<input type="text" name="maxViewUrl" id="maxViewUrl" value="${portalPortlet.maxViewUrl}" size="50"/>
						</div>
						</td>
					</tr>
					<tr id="moreModeTr">
						<th scope="row" colspan="2"><ikep4j:message pre="${prefixUI}" key="detail.moreMode" /> </th>
						<td>
						<div>
						<input type="radio" class="radio" title="<ikep4j:message pre="${prefixUI}" key="detail.radio.use" />" name="moreMode" <c:if test="${portalPortlet.moreMode == 1}">checked="checked"</c:if> value="1" /> <ikep4j:message pre="${prefixUI}" key="detail.radio.use" />
						<input type="radio" class="radio" title="<ikep4j:message pre="${prefixUI}" key="detail.radio.unuse" />" name="moreMode" <c:if test="${portalPortlet.moreMode == 0}">checked="checked"</c:if> value="0" /> <ikep4j:message pre="${prefixUI}" key="detail.radio.unuse" />
						</div>
						</td>
					</tr>
					<tr id="moreViewUrlTr">
						<th scope="row" colspan="2">* <ikep4j:message pre="${prefixUI}" key="detail.moreViewUrl" /> </th>
						<td>
						<div>
						<input type="text" name="moreViewUrl" id="moreViewUrl" value="${portalPortlet.moreViewUrl}" size="50"/>
						</div>
						</td>
					</tr>
					<tr id="helpModeTr">
						<th scope="row" colspan="2"><ikep4j:message pre="${prefixUI}" key="detail.helpMode" /> </th>
						<td>
						<div>
						<input type="radio" class="radio" title="<ikep4j:message pre="${prefixUI}" key="detail.radio.use" />" name="helpMode" <c:if test="${portalPortlet.helpMode == 1}">checked="checked"</c:if> value="1" /> <ikep4j:message pre="${prefixUI}" key="detail.radio.use" />
						<input type="radio" class="radio" title="<ikep4j:message pre="${prefixUI}" key="detail.radio.unuse" />" name="helpMode" <c:if test="${portalPortlet.helpMode == 0}">checked="checked"</c:if> value="0" /> <ikep4j:message pre="${prefixUI}" key="detail.radio.unuse" />
						</div>
						</td>
					</tr>
					<tr id="helpViewUrlTr">
						<th scope="row" colspan="2">* <ikep4j:message pre="${prefixUI}" key="detail.helpViewUrl" /> </th>
						<td>
						<div>
						<input type="text" name="helpViewUrl" id="helpViewUrl" value="${portalPortlet.helpViewUrl}" size="50"/>
						</div>
						</td>
					</tr>
					<tr id="reloadMode">
						<th scope="row" colspan="2"><ikep4j:message pre="${prefixUI}" key="detail.reloadMode" /> </th>
						<td>
						<div>
						<input type="radio" class="radio" title="<ikep4j:message pre="${prefixUI}" key="detail.radio.use" />" name="reloadMode" <c:if test="${portalPortlet.reloadMode == 1}">checked="checked"</c:if> value="1" /> <ikep4j:message pre="${prefixUI}" key="detail.radio.use" />
						<input type="radio" class="radio" title="<ikep4j:message pre="${prefixUI}" key="detail.radio.unuse" />" name="reloadMode" <c:if test="${portalPortlet.reloadMode == 0}">checked="checked"</c:if> value="0" /> <ikep4j:message pre="${prefixUI}" key="detail.radio.unuse" />
						</div>
						</td>
					</tr>
					<tr>
						<th scope="row" colspan="2"><ikep4j:message pre="${prefixUI}" key="detail.removeMode" /> </th>
						<td>
						<div>
						<input type="radio" class="radio" title="<ikep4j:message pre="${prefixUI}" key="detail.radio.use" />" name="removeMode" <c:if test="${portalPortlet.removeMode == 1}">checked="checked"</c:if> value="1" /> <ikep4j:message pre="${prefixUI}" key="detail.radio.use" />
						<input type="radio" class="radio" title="<ikep4j:message pre="${prefixUI}" key="detail.radio.unuse" />" name="removeMode" <c:if test="${portalPortlet.removeMode == 0}">checked="checked"</c:if> value="0" /> <ikep4j:message pre="${prefixUI}" key="detail.radio.unuse" />
						</div>
						</td>
					</tr>
					<tr>
						<th scope="row" colspan="2"><ikep4j:message pre="${prefixUI}" key="detail.multipleMode" /> </th>
						<td>
						<div>
						<input type="radio" class="radio" title="<ikep4j:message pre="${prefixUI}" key="detail.radio.use" />" name="multipleMode" <c:if test="${portalPortlet.multipleMode == 1}">checked="checked"</c:if> value="1" /> <ikep4j:message pre="${prefixUI}" key="detail.radio.use" />
						<input type="radio" class="radio" title="<ikep4j:message pre="${prefixUI}" key="detail.radio.unuse" />"name="multipleMode" <c:if test="${portalPortlet.multipleMode == 0}">checked="checked"</c:if> value="0" /> <ikep4j:message pre="${prefixUI}" key="detail.radio.unuse" />
						</div>
						</td>
					</tr>
					<tr>
						<th scope="row" colspan="2"><ikep4j:message pre="${prefixUI}" key="detail.publicOption" /> </th>
						<td>
						<div>
						<input type="radio" class="radio" title="<ikep4j:message pre="${prefixUI}" key="detail.radio.public" />" name="publicOption" <c:if test="${portalPortlet.publicOption == 1}">checked="checked"</c:if> value="1" /> <ikep4j:message pre="${prefixUI}" key="detail.radio.public" />
						<input type="radio" class="radio" title="<ikep4j:message pre="${prefixUI}" key="detail.radio.private" />" name="publicOption" <c:if test="${portalPortlet.publicOption == 0}">checked="checked"</c:if> value="0" /> <ikep4j:message pre="${prefixUI}" key="detail.radio.private" />
						</div>
						</td>
					</tr>
					<tr>
						<th scope="row" colspan="2"><ikep4j:message pre="${prefixUI}" key="detail.headerMode" /> </th>
						<td>
						<div>
						<input type="radio" class="radio" title="<ikep4j:message pre="${prefixUI}" key="detail.radio.view" />" name="headerMode" <c:if test="${portalPortlet.headerMode == 1}">checked="checked"</c:if> value="1" /> <ikep4j:message pre="${prefixUI}" key="detail.radio.view" />
						<input type="radio" class="radio" title="<ikep4j:message pre="${prefixUI}" key="detail.radio.unview" />" name="headerMode" <c:if test="${portalPortlet.headerMode == 0}">checked="checked"</c:if> value="0" /> <ikep4j:message pre="${prefixUI}" key="detail.radio.unview" />
						</div>
						</td>
					</tr>
					<tr>
						<th scope="row" colspan="2"><ikep4j:message pre="${prefixUI}" key="detail.status" /> </th>
						<td>
						<div>
						<input type="radio" class="radio" title="<ikep4j:message pre="${prefixUI}" key="detail.radio.use" />"   name="status" <c:if test="${portalPortlet.status == 1}">checked="checked"</c:if> value="1" /> <ikep4j:message pre="${prefixUI}" key="detail.radio.use" />
						<input type="radio" class="radio" title="<ikep4j:message pre="${prefixUI}" key="detail.radio.unuse" />" name="status" <c:if test="${portalPortlet.status == 0}">checked="checked"</c:if> value="0" /> <ikep4j:message pre="${prefixUI}" key="detail.radio.unuse" />
						</div>
						</td>
					</tr>
					<tr>
						<th scope="row" colspan="2"><ikep4j:message pre="${prefixUI}" key="detail.shareYn" /> </th>
						<td>
						<div>
						<input type="radio" class="radio" title="<ikep4j:message pre="${prefixUI}" key="detail.share" />"   name="shareYn" <c:if test="${portalPortlet.shareYn == 'Y'}">checked="checked"</c:if> value="Y" /> <ikep4j:message pre="${prefixUI}" key="detail.share" />
						<input type="radio" class="radio" title="<ikep4j:message pre="${prefixUI}" key="detail.nonshared" />" name="shareYn" <c:if test="${portalPortlet.shareYn == 'N'}">checked="checked"</c:if> value="N" /> <ikep4j:message pre="${prefixUI}" key="detail.nonshared" />
						</div>
						</td>
					</tr>
					<tr>
						<th scope="row" rowspan="4"><ikep4j:message pre="${prefixUI}" key="detail.cache" /> </th>
						<th scope="row"><ikep4j:message pre="${prefixUI}" key="detail.cacheYn" /> </th>
						<td>
						<div>
						<input type="radio" class="radio" title="<ikep4j:message pre="${prefixUI}" key="detail.radio.use" />" name="cacheYn" <c:if test="${portalPortlet.cacheYn == 'Y'}">checked="checked"</c:if> value="Y" /> <ikep4j:message pre="${prefixUI}" key="detail.radio.use" />
						<input type="radio" class="radio" title="<ikep4j:message pre="${prefixUI}" key="detail.radio.unuse" />" name="cacheYn" <c:if test="${portalPortlet.cacheYn == 'N'}">checked="checked"</c:if> value="N" /> <ikep4j:message pre="${prefixUI}" key="detail.radio.unuse" />
						</div>
						</td>
					</tr>
					<tr>
						<th scope="row"><ikep4j:message pre="${prefixUI}" key="detail.cacheNameStr" /> </th>
						<td>
						<div>
						<input type="text" name="cacheNameStr" id="cacheNameStr" value="${portalPortlet.cacheNameStr}" size="50"/>
						</div>
						</td>
					</tr>
					<tr>
						<th scope="row"><ikep4j:message pre="${prefixUI}" key="detail.cacheLiveSecond" /> </th>
						<td>
						<div>
						<input type="text" name="cacheLiveSecond" id="cacheLiveSecond" value="${portalPortlet.cacheLiveSecond}" size="7"/>
						</div>
						</td>
					</tr>
					<tr>
						<th scope="row"><ikep4j:message pre="${prefixUI}" key="detail.cacheMaxCount" /> </th>
						<td>
						<div>
						<input type="text" name="cacheMaxCount" id="cacheMaxCount" value="${portalPortlet.cacheMaxCount}" size="7"/>
						</div>
						</td>
					</tr>
				</tbody>
			</table>
		</form:form>
	</div>

</div>
<!--//blockDetail End-->

<!--blockButton Start-->
<div class="blockButton">
	<ul>
		<li>
		<a class="button saveButton" href="#a"><span><ikep4j:message pre="${prefixUI}" key="button.save" /></span></a>
		<a class="button" href="javascript: readPortlet();"><span><ikep4j:message pre="${prefixUI}" key="button.cancel" /></span></a>
		<a class="button" href="javascript: listPortlet();"><span><ikep4j:message pre="${prefixUI}" key="button.list" /></span></a>
		</li>
	</ul>
</div>
<!--//blockButton End-->