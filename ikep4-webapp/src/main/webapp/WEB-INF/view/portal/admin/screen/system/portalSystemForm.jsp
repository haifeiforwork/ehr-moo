<%@ include file="/base/common/taglibs.jsp"%>
<%@ page language="java" errorPage="/common/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ taglib uri="/WEB-INF/tld/ikep4j.tld" prefix="ikep4j" %> 

<c:set var="preAlert" value="message.portal.admin.screen.system.portalSystemForm.alert"/>					
<c:set var="preForm" value="ui.portal.admin.screen.system.portalSystemForm.form"/>
<c:set var="preButton" value="ui.portal.admin.screen.system.portalSystemForm.button"/>

<script type="text/javascript">
//<![CDATA[
(function($) {

	$jq(document).ready(function() {
		$jq("#portalSystemForm:input:visible:enabled:first").focus();
		
		$jq("a[name=cancelSystemButton]").click(function() { 
			getForm();
		});
		
		
		$jq("a[name=createSystemButton]").click(function() {  
			
			if(tempCurrItem == null) {
				alert("<ikep4j:message pre='${preAlert}' key='chooseAndClick' />");
			}
			
			if($jq("#codeCheck").val() == "success") {
				if($jq("#codeValue").val() == $jq("#systemCode").val()) {
					$jq("input[name=systemName]").val($jq("input[id=${userLocaleCode}]").val());
					$jq("input[name=systemType]").val($jq("input[name=systemTypeRadio]:radio:checked").val());
					$jq("input[name=target]").val($jq("input[name=${targetRadio}]").val());
					$jq("input[name=parentSystemCode]").val($jq.parseJSON(tempCurrItem.attr("data")).code);
					
					if($jq("input[name='systemTypeRadio']:radio:checked").val() != "CATEGORY") {
						$jq("input[name=urlType]").val($jq("input[name=urlTypeRadio]:radio:checked").val());
						$jq("input[name=target]").val($jq("input[name=targetRadio]:radio:checked").val());
					} else {
						$jq("input[name=urlType]").val("");
						$jq("input[name=url]").val("");
						$jq("input[name=target]").val("");
					}
					
					$jq("#portalSystemForm").trigger("submit");
				} else {
					alert("<ikep4j:message pre='${preAlert}' key='recheckSystemCode' />");
				}
			} else {
				alert("<ikep4j:message pre='${preAlert}' key='checkSystemCode' />");
			}
		});
		
		$jq("a[name=updateSystemButton]").click(function() {  
			
			if(($jq("#codeCheck").val() == "success" && ($jq("#codeValue").val() == $jq("#systemCode").val())) 
					|| ($jq("#systemCode").val() == $jq("#oldSystemCode").val())) {
				$jq("input[name=systemName]").val($jq("input[id=${userLocaleCode}]").val());
				$jq("input[name=systemType]").val($jq("input[name=systemTypeRadio]:radio:checked").val());
				
				if($jq("input[name='systemTypeRadio']:radio:checked").val() != "CATEGORY") {
					$jq("input[name=urlType]").val($jq("input[name=urlTypeRadio]:radio:checked").val());
					$jq("input[name=target]").val($jq("input[name=targetRadio]:radio:checked").val());
				} else {
					$jq("input[name=urlType]").val("");
					$jq("input[name=url]").val("");
					$jq("input[name=target]").val("");
				}
				
				$jq("#portalSystemForm").trigger("submit");
			} else {
				alert("<ikep4j:message pre='${preAlert}' key='checkSystemCode' />");
			}
		}); 
		
		
		
		iKEP.loadSecurity("Portal-System", "${portalSystem.systemCode}", "READ", "User,Group,Role,Job,Duty,ExpUser");
		
		var url = "";
		
		<c:choose>
		<c:when test="${!empty portalSystem.systemCode}">
		url = "<c:url value='portalSystemUpdate.do' />";
		</c:when>
		<c:otherwise>
		url = "<c:url value='portalSystemCreate.do' />";
		</c:otherwise>
		</c:choose>
		
		/**
		 * Validation Logic Start
		 */
		var validOptions = {
			rules : {
				<c:forEach var="i18nMessage" items="${portalSystem.i18nMessageList}" varStatus="loopStatus">
				"i18nMessageList[${loopStatus.index}].itemMessage" : {
					required : true,
					maxlength : 100
				},
				</c:forEach>
				"className" : {
					englishName : true
				},
				"systemCode" : {
					required : true,
					maxlength : 13
				},
				"description" : {
					maxlength : 250
				},
				"url" : {
					maxlength : 500
				}
			},
			messages : {
				<c:forEach var="i18nMessage" items="${portalSystem.i18nMessageList}" varStatus="loopStatus">
				"i18nMessageList[${loopStatus.index}].itemMessage" : {
					required : "<ikep4j:message key='NotEmpty.portalSystem.systemName' />",
					maxlength : "<ikep4j:message key='Size.portalSystem.systemName' />"
				},
				</c:forEach>
				"className" : {
					englishName : "<ikep4j:message key='Alert.portalSystem.className.englishName' />"
				},
				"systemCode" : {
					required : "<ikep4j:message key='NotEmpty.portalSystem.systemCode' />",
					maxlength : "<ikep4j:message key='Size.portalSystem.systemCode' />"
				},
				"description" : {
					maxlength : "<ikep4j:message key='Size.portalSystem.description' />"
				},
				"url" : {
					maxlength : "<ikep4j:message key='Size.portalSystem.url' />"
				}
			},
			submitHandler : function() {
				
				$jq.ajax({
					url : url,
					data : $jq("#portalSystemForm").serialize(),
					type : "post",
					success : function(result) {
						alert("<ikep4j:message pre='${preAlert}' key='saveSuccess' />");
						
						<c:choose>
						<c:when test="${!empty portalSystem.systemCode}">
						var systemCode = $jq("#systemCode").val();
						
						$jq("#portalSystemTree").jstree("refresh", tempParent);
						$jq("#portalSystemTree").jstree("open_node", tempParent.children(), false, false);
						
						getSystem(systemCode);
						</c:when>
						<c:otherwise>
						var systemCode = $jq("#systemCode").val();
						
						if(($jq(tempCurrItem).attr("class")=="jstree-closed jstree-last") || ($jq(tempCurrItem).attr("class")=="jstree-closed")) {
							//노드가 한번도 열리지 않은 경우 노드 생성 수행하지 않음
							$jq("#portalSystemTree").jstree("open_node", tempCurrItem, false, false);
						} else { 
							//노드가 한번이라도 열렸던 경우 아래 코드 수행
							$jq("#portalSystemTree").jstree("refresh", tempCurrItem);
							$jq("#portalSystemTree").jstree("open_node", tempCurrItem.children(), false, false);
						}
						
						//방금 생성한 노드를 바로 수정하는 경우를 대비하여 tempParent를 새로 매핑한다.
						//하지 않으면 tempParent가 undefined로 에러 발생
						tempParent = tempCurrItem;
						
						getSystem(systemCode);
						</c:otherwise>
						</c:choose>
					},
					error : function(xhr, exMessage) {
						var errorItems = $.parseJSON(xhr.responseText).exception;
						validator.showErrors(errorItems);
					}
				});
			}
		};
		
		var validator = new iKEP.Validator("#portalSystemForm", validOptions);
	
		/**
		 * Validation Logic End
		 */
	});
})(jQuery);
//]]>
</script>

<form name="portalSystemForm" id="portalSystemForm" action="" method="post" >
<input type="hidden" id="oldSystemCode" name="oldSystemCode" value="${portalSystem.systemCode}"/>
<input type="hidden" id="parentSystemCode" name="parentSystemCode" value="${portalSystem.parentSystemCode}"/>
<input type="hidden" id="systemType" name="systemType" value="" />
<input type="hidden" id="sortOrder" name="sortOrder" value="${portalSystem.sortOrder}" />
<input type="hidden" id="urlType" name="urlType" value="" />
<input type="hidden" id="urlInput" name="urlInput" value="" />
<input type="hidden" id="target" name="target" value="" />
<input type="hidden" id="childCount" name="childCount" value="${childCount}"/>
<input type="hidden" id="codeCheck" name="codeCheck" value=""/>
<input type="hidden" id="codeValue" name="codeValue" value=""/>
<input type="hidden" id="addrGroupType" name="addrGroupType" value=""/>
<input type="hidden" id="groupTypeCount" name="groupTypeCount" value="0"/>
<input type="hidden" id="systemName" name="systemName" value="" />
	
	<div class="blockDetail">
		<table id="mainTable" summary="<ikep4j:message pre='${preForm}' key='tableSummary' />">
			<caption></caption>
			<colgroup>
				<col width="10%"/>
				<col width="8%"/>
				<col width="82%"/>
			</colgroup>
			<tbody>
				<tr>
					<th colspan="2">
						<span class="colorPoint">*</span><ikep4j:message pre="${preForm}" key="systemCode" />
					</th>
					<td class="textLeft">
						<div>
							<input id="systemCode" name="systemCode" type="text" class="inputbox" style="width:30%" value="${portalSystem.systemCode}" <c:if test="${childCount > 0}">readonly="readonly"</c:if> />
							<a href="#" class="button_ic" onclick="checkSystemCode(); return false;" title="<ikep4j:message pre='${preButton}' key='checkDuplicate' />">
								<span><ikep4j:message pre="${preButton}" key="checkDuplicate" /></span>
							</a>
						</div>
					</td>
				</tr>
				
				<!--systemName Start-->
				<tr>
					<th rowspan="${localeSize}">
						<span class="colorPoint">*</span><ikep4j:message pre="${preForm}" key="systemName" />
					</th>
					<c:forEach var="i18nMessage" items="${portalSystem.i18nMessageList}" varStatus="loopStatus">
					<c:if test="${i18nMessage.fieldName eq 'systemName' }">
					<c:if test="${i18nMessage.index > 1}">
				<tr>
					</c:if>
					<th scope="row">
						<span class="colorPoint">*</span><c:out value="${i18nMessage.localeCode}"/>
					</th>
					<td>
						<div>
						<spring:bind path="portalSystem.i18nMessageList[${loopStatus.index}].itemMessage">
							<input type="text" id="${i18nMessage.localeCode}" name="i18nMessageList[${loopStatus.index}].itemMessage" value="${i18nMessage.itemMessage}" class="inputbox w100" />
							<input type="hidden" name="i18nMessageList[${loopStatus.index}].localeCode" value="${i18nMessage.localeCode}" />
							<input type="hidden" name="i18nMessageList[${loopStatus.index}].fieldName" value="${i18nMessage.fieldName}" />
							<input type="hidden" name="i18nMessageList[${loopStatus.index}].messageId" value="${i18nMessage.messageId}" />
							<label for="${status.expression}" class="serverError"> ${status.errorMessage}</label>
						</spring:bind>
						</div>
					</td>
				</tr>
					</c:if>
					</c:forEach>
				<!--//systemName End-->
				
				<!--//className Start-->
				<tr>
					<th colspan="2">
						<ikep4j:message pre="${preForm}" key="className" />
					</th>
					<td class="textLeft">
						<div>
						<spring:bind path="portalSystem.className">
							<input id="${status.expression}" name="${status.expression}" type="text" class="inputbox w100" value="${portalSystem.className}" />
							<label for="${status.expression}" class="serverError">${status.errorMessage}</label>
						</spring:bind>
						<input type="hidden" id="oldClassName" name="oldClassName" value="${portalSystem.className}" />
						</div>
						<div class="tdInstruction">
							<ikep4j:message pre="${preForm}" key="classNameDesc" />
						</div>
					</td>
				</tr>
				<!--//className End-->
				
				<!--description Start-->	
				<tr>
					<th colspan="2">
						<ikep4j:message pre="${preForm}" key="description" />
					</th>
					<td class="textLeft">
						<div>
						<spring:bind path="portalSystem.description">
							<input id="${status.expression}" name="${status.expression}" type="text" class="inputbox w100" value="${portalSystem.description}" />
							<label for="${status.expression}" class="serverError">${status.errorMessage}</label>
						</spring:bind>
						</div>
					</td>
				</tr>
				<!--//description End-->
				
				<!--mainView Start-->
				<tr>
					<th colspan="2">
						<ikep4j:message pre="${preForm}" key="mainView" />
					</th>
					<td class="textLeft">
						<input type="radio" id="mainViewYes" name="mainView" class="radio" value="1" <c:choose><c:when test="${empty portalSystem.systemCode}">checked="checked"</c:when><c:when test="${!empty portalSystem.systemCode && portalSystem.mainView == 1}">checked="checked"</c:when><c:otherwise></c:otherwise></c:choose> />
						<lable for="mainViewYes"><ikep4j:message pre="${preForm}" key="yes" /></lable>
						<input type="radio" id="mainViewNo" name="mainView" class="radio" value="0" <c:if test="${!empty portalSystem.systemCode && portalSystem.mainView == 0}">checked="checked"</c:if> />
						<lable for="mainViewNo"><ikep4j:message pre="${preForm}" key="no" /></lable>
					</td>
				</tr>
				<!--//mainView End-->
				
				<!--systemType Start-->
				<tr>
					<th colspan="2">
						<ikep4j:message pre="${preForm}" key="systemType" />
					</th>
					<td class="textLeft">
						<input type="radio" id="systemTypeItem" name="systemTypeRadio" class="radio" value="ITEM" <c:choose><c:when test="${empty portalSystem.systemCode}">checked="checked"</c:when><c:when test="${!empty portalSystem.systemCode && portalSystem.systemType == 'ITEM'}">checked="checked"</c:when><c:otherwise></c:otherwise></c:choose> onclick="displayInputValue(0);" />
						<lable for="systemTypeItem"><ikep4j:message pre="${preForm}" key="link" /></lable>
						<input type="radio" id="systemTypeCategory" name="systemTypeRadio" class="radio" value="CATEGORY" <c:if test="${!empty portalSystem.systemCode && portalSystem.systemType == 'CATEGORY'}">checked="checked"</c:if> onclick="displayInputValue(1);" />
						<lable for="systemTypeCategory"><ikep4j:message pre="${preForm}" key="category" /></lable>
					</td>
				</tr>
				<!--//systemType End-->
				
				<!--urlType Start-->
				<tr id="urlTypeTr" <c:if test="${!empty portalSystem.systemCode && portalSystem.systemType == 'CATEGORY'}">style="display:none;"</c:if>>
					<th colspan="2">
						<ikep4j:message pre="${preForm}" key="urlType" />
					</th>
					<td class="textLeft">
						<input type="radio" class="radio" title="<ikep4j:message pre='${preForm}' key='url' />" id="urlTypeUrl" name="urlTypeRadio" value="URL" <c:choose><c:when test="${empty portalSystem.systemCode || empty portalSystem.urlType}">checked="checked"</c:when><c:when test="${!empty portalSystem.systemCode && portalSystem.urlType == 'URL'}">checked="checked"</c:when><c:otherwise></c:otherwise></c:choose> />
						<label for="urlTypeUrl"><ikep4j:message pre="${preForm}" key="url" /></label>&nbsp;
						<input type="radio" class="radio" title="<ikep4j:message pre='${preForm}' key='javascript' />" id="urlTypeJavascript" name="urlTypeRadio" value="JAVASCRIPT" <c:if test="${!empty portalSystem.systemCode && portalSystem.urlType == 'JAVASCRIPT'}">checked="checked"</c:if> />
						<label for="urlTypeJavascript"><ikep4j:message pre="${preForm}" key="javascript" /></label>
					</td>
				</tr>
				<!--//urlType End-->
				
				<!--url Start-->
				<tr id="urlTr" <c:if test="${!empty portalSystem.systemCode && portalSystem.systemType == 'CATEGORY'}">style="display:none;"</c:if>>
					<th colspan="2">
						<ikep4j:message pre="${preForm}" key="url" />
					</th>
					<td class="textLeft">
						<div>
						<spring:bind path="portalSystem.url">
							<input id="url" name="url" type="text" class="inputbox" style="width:80%" value="${portalSystem.url}" />
							<label for="url" class="serverError"> ${status.errorMessage}</label>
						</spring:bind>
						<a href="#" class="button_ic" onclick="popupSystemUrl(); return false;" title="<ikep4j:message pre='${preButton}' key='example' />">
							<span><ikep4j:message pre="${preButton}" key="example" /></span>
						</a>
						</div>
					</td>
				</tr>
				<!--//url End-->
				
				<!--target Start-->
				<tr	id="targetTr" <c:if test="${!empty portalSystem.systemCode && portalSystem.systemType == 'CATEGORY'}">style="display:none;"</c:if>>
					<th scope="col"	colspan="2">
						<ikep4j:message pre="${preForm}" key="target" />
					</th>
					<td class="textLeft">
						<div>
							<input type="radio" id="targetWindow" name="targetRadio" class="radio" value="WINDOW" <c:if test="${!empty portalSystem.systemCode && portalSystem.target == 'WINDOW'}">checked="checked"</c:if> />
							<label for="targetWindow"><ikep4j:message pre="${preForm}" key="window" /></label>&nbsp;
							<input type="radio" id="targetInner" name="targetRadio" class="radio" value="INNER" <c:choose><c:when test="${empty portalSystem.systemCode}">checked="checked"</c:when><c:when test="${!empty portalSystem.systemCode && portalSystem.target == 'INNER'}">checked="checked"</c:when><c:when test="${!empty portalSystem.systemCode && empty portalSystem.target && portalSystem.systemType == 'CATEGORY'}">checked="checked"</c:when><c:otherwise></c:otherwise></c:choose> />
							<label for="targetInner"><ikep4j:message pre="${preForm}" key="inner" /></label>
						</div>
						<div class="tdInstruction">
							<ikep4j:message pre="${preForm}" key="targetDesc" />
						</div>
					</td>
				</tr>
				<!--//target End-->
				
			</tbody>
		</table>
		
		<div class="blockBlank_10px"></div>
		
		<!--blockButton Start-->
		<div class="blockButton" style="clear:both;">
			<ul>
				<c:choose>
				<c:when test="${empty portalSystem.systemCode}">
				<li><a name="createSystemButton" class="button" href="#" onclick="return false;"><span><ikep4j:message pre="${preButton}" key="create" /></span></a></li>
				<li><a name="cancelSystemButton" class="button" href="#" onclick="return false;"><span><ikep4j:message pre="${preButton}" key="cancel" /></span></a></li>
				</c:when>
				<c:otherwise>
				<c:if test="${portalSystem.systemCode != portalSystem.parentSystemCode}">
				<li><a name="updateSystemButton" class="button" href="#" onclick="return false;"><span><ikep4j:message pre="${preButton}" key="update" /></span></a></li>
				</c:if>
				</c:otherwise>
				</c:choose>
				
			</ul>
		</div>
		<!--//blockButton End-->
	</div>
</form>