<%@ include file="/base/common/taglibs.jsp"%>
<%@ page language="java" errorPage="/common/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ taglib uri="/WEB-INF/tld/ikep4j.tld" prefix="ikep4j" %> 

<c:set var="preAlert" value="message.portal.admin.screen.quickmenu.portalQuickMenuForm.alert"/>					
<c:set var="preForm" value="ui.portal.admin.screen.quickmenu.portalQuickMenuForm.form"/>
<c:set var="preButton" value="ui.portal.admin.screen.quickmenu.portalQuickMenuForm.button"/>

<script type="text/javascript"> 
//<![CDATA[
(function($) {
	
	$jq(document).ready(function() {
		$jq("#portalQuickMenuForm:input:visible:enabled:first").focus();
		
		iKEP.loadSecurity("Portal-QuickMenu", "${portalQuickMenu.quickMenuId}", "READ", "User,Group,Role,Job,Duty,ExpUser");
		
		<c:if test="${action == 'add' || action == 'modify'}">
		var url = "";
		
		<c:choose>
		<c:when test="${!empty portalQuickMenu.quickMenuId}">
		url = "<c:url value='portalQuickMenuUpdate.do' />";
		</c:when>
		<c:otherwise>
		url = "<c:url value='portalQuickMenuCreate.do' />";
		</c:otherwise>
		</c:choose>
		
		/**
		 * Validation Logic Start
		 */
		var validOptions = {
			rules : {
				<c:forEach var="i18nMessage" items="${portalQuickMenu.i18nMessageList}" varStatus="loopStatus">
				"i18nMessageList[${loopStatus.index}].itemMessage" : {
					required : true,
					maxlength : 100
				},
				</c:forEach>
				"moreUrl" : {
					maxlength : 500
				},
				"countUrl" : {
					maxlength : 500
				},
				"iconId" : {
					maxlength : 27,
					pattern : /^[A-Za-z0-9_]*$/
				}
			},
			messages : {
				<c:forEach var="i18nMessage" items="${portalQuickMenu.i18nMessageList}" varStatus="loopStatus">
				"i18nMessageList[${loopStatus.index}].itemMessage" : {
					required : "<ikep4j:message key='NotEmpty.portalQuickMenu.quickMenuName' />",
					maxlength : "<ikep4j:message key='Size.portalQuickMenu.quickMenuName' />"
				},
				</c:forEach>
				"moreUrl" : {
					maxlength : "<ikep4j:message key='Size.portalQuickMenu.moreUrl' />"
				},
				"countUrl" : {
					maxlength : "<ikep4j:message key='Size.portalQuickMenu.countUrl' />"
				},
				"iconId" : {
					maxlength : "<ikep4j:message key='Size.portalQuickMenu.iconId' />",
					pattern : "<ikep4j:message key='Pattern.portalQuickMenu.iconId' />"
				}
			},
			submitHandler : function() {
				
				$jq.ajax({
					url : url,
					data : $jq("#portalQuickMenuForm").serialize(),
					type : "post",
					success : function(result) {
						alert("<ikep4j:message pre='${preAlert}' key='saveSuccess' />");
						
						<c:choose>
						<c:when test="${!empty portalQuickMenu.quickMenuId}">
						$currItem = $jq("#quickMenu_${portalQuickMenu.quickMenuId}");
						$currItem.children("a").html($jq("input[id=${userLocaleCode}]").val());
						
						saveAfter(result);
						</c:when>
						<c:otherwise>
						$currItem.remove();
						outBoxResize("remove");
						
						var quickMenuId = result.quickMenuId;
						var quickMenuName = result.quickMenuName;
						
						$aTag = '<li id="quickMenu_'+quickMenuId+'"><a href="#" onclick="modifyMenu(this, '+quickMenuId+'); return false;">'+quickMenuName+'</a></li>';
						$jq("#topMenuList").append($aTag);
						
						//makeSubMenuSortable(id);
						
						saveAfter(result.quickMenuId);
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
		
		var validator = new iKEP.Validator("#portalQuickMenuForm", validOptions);
	
		/**
		 * Validation Logic End
		 */
		</c:if>
	});
	
})(jQuery);
//]]>
</script>
<form name="portalQuickMenuForm" id="portalQuickMenuForm" action="" method="post" >
<input type="hidden" id="quickMenuId" name="quickMenuId" value="<c:out value='${portalQuickMenu.quickMenuId}'/>" />
<input type="hidden" id="sortOrder" name="sortOrder" value="<c:out value='${portalQuickMenu.sortOrder}'/>" />
<input type="hidden" id="urlInput" name="urlInput" value="" />
<input type="hidden" id="quickMenuName" name="quickMenuName" value="" />
 
<div class="blockDetail">
	<table id="mainTable" summary="<ikep4j:message pre='${preForm}' key='tableSummary' />">
		<caption></caption>
		<colgroup>
			<col width="10%"/>
			<col width="8%"/>
			<col width="82%"/>
		</colgroup>
		<tbody>
			<!--quickMenuName Start-->
			<tr>
				<th scope="row" rowspan="${localeSize}">
					<span class="colorPoint">*</span><ikep4j:message pre="${preForm}" key="iconName" />
				</th>
				<c:forEach var="i18nMessage" items="${portalQuickMenu.i18nMessageList}" varStatus="loopStatus">
				<c:if test="${i18nMessage.fieldName eq 'quickMenuName' }">
				<c:if test="${i18nMessage.index > 1}">
			<tr>
				</c:if>
				<th scope="row">
					<span class="colorPoint">*</span><c:out value="${i18nMessage.localeCode}"/>
				</th>
				<td>
					<div>
					<spring:bind path="portalQuickMenu.i18nMessageList[${loopStatus.index}].itemMessage">
						<input type="text" id="${i18nMessage.localeCode}" name="i18nMessageList[${loopStatus.index}].itemMessage" value="${i18nMessage.itemMessage}" class="inputbox w100" />
						<input type="hidden" name="i18nMessageList[${loopStatus.index}].localeCode" value="${i18nMessage.localeCode}" />
						<input type="hidden" name="i18nMessageList[${loopStatus.index}].fieldName" value="${i18nMessage.fieldName}" />
						<input type="hidden" name="i18nMessageList[${loopStatus.index}].messageId" value="${i18nMessage.messageId}" />
					</spring:bind>
					</div>
				</td>
			</tr>
				</c:if>
				</c:forEach>
			<!--//quickMenuName End-->
			
			<!--moreUrlType Start-->
			<tr>
				<th colspan="2" scope="row">
					<ikep4j:message pre="${preForm}" key="moreUrlType" />
				</th>
				<td>
					<input type="radio" class="radio" title="<ikep4j:message pre='${preForm}' key='url' />" id="moreUrlTypeUrl" name="moreUrlType" value="URL" <c:choose><c:when test="${empty portalQuickMenu.quickMenuId}">checked="checked"</c:when><c:when test="${!empty portalQuickMenu.quickMenuId && portalQuickMenu.moreUrlType == 'URL'}">checked="checked"</c:when><c:otherwise></c:otherwise></c:choose> />
					<label for="moreUrlTypeUrl"><ikep4j:message pre="${preForm}" key="url" /></label>&nbsp;
					<input type="radio" class="radio" title="<ikep4j:message pre='${preForm}' key='javascript' />" id="moreUrlTypeJavascript" name="moreUrlType" value="JAVASCRIPT" <c:if test="${!empty portalQuickMenu.quickMenuId && portalQuickMenu.moreUrlType == 'JAVASCRIPT'}">checked="checked"</c:if> />
					<label for="moreUrlTypeJavascript"><ikep4j:message pre="${preForm}" key="javascript" /></label>
				</td>
			</tr>
			<!--//moreUrlType End-->
			
			<!-- moreUrl Start -->
			<tr>
				<th colspan="2" scope="row">
					<ikep4j:message pre="${preForm}" key="moreUrl" />
				</th>
				<td>
					<div>
						<input type="text" id="moreUrl" name="moreUrl" class="inputbox w70" title="More URL" value="${portalQuickMenu.moreUrl}" readonly="readonly" />&nbsp;
						<input type="checkbox" id="selfInput" name="selfInput" class="checkbox valign_middle" title="<ikep4j:message pre='${preForm}' key='directInput' />" value="" onclick="selfInputCheck();" /><ikep4j:message pre="${preForm}" key="directInput" />&nbsp;
						<a href="#" class="button_s" onclick="popupSystemUrl(); return false;"><span><ikep4j:message pre="${preButton}" key="example" /></span></a>
					</div>
					<div class="tdInstruction">
						<ikep4j:message pre="${preForm}" key="moreUrlDesc" />
					</div>
				</td>
			</tr>
			<!--// moreUrl End -->
			
			<!--target Start-->
			<tr>
				<th colspan="2" scope="row">
					<ikep4j:message pre="${preForm}" key="target" />
				</th>
				<td>
					<input type="radio" id="targetInner" name="target" class="radio" title="<ikep4j:message pre='${preForm}' key='inner' />" value="INNER" <c:choose><c:when test="${empty portalQuickMenu.quickMenuId}">checked="checked"</c:when><c:when test="${!empty portalQuickMenu.quickMenuId && portalQuickMenu.target == 'INNER'}">checked="checked"</c:when><c:otherwise></c:otherwise></c:choose> />
					<label for="targetInner"><ikep4j:message pre="${preForm}" key="inner" /></label>&nbsp;
					<input type="radio" id="targetWindow" name="target" class="radio" title="<ikep4j:message pre='${preForm}' key='window' />" value="WINDOW" <c:if test="${!empty portalQuickMenu.quickMenuId && portalQuickMenu.target == 'WINDOW'}">checked="checked"</c:if> />
					<label for="targetWindow"><ikep4j:message pre="${preForm}" key="window" /></label>
				</td>
			</tr>
			<!--//target End-->
			
			<!--count Start-->
			<tr>
				<th colspan="2" scope="row">
					<span class="colorPoint">*</span><ikep4j:message pre="${preForm}" key="count" />
				</th>
				<td>
					<input type="radio" id="countYes" name="count" class="radio" title="<ikep4j:message pre='${preForm}' key='yes' />" value="1" <c:choose><c:when test="${empty portalQuickMenu.quickMenuId}">checked="checked"</c:when><c:when test="${!empty portalQuickMenu.quickMenuId && portalQuickMenu.count == 1}">checked="checked"</c:when><c:otherwise></c:otherwise></c:choose> onclick="displayInputValue(1);" />
					<label for="countYes"><ikep4j:message pre="${preForm}" key="yes" /></label>&nbsp;
					<input type="radio" id="countNo" name="count" class="radio" title="<ikep4j:message pre='${preForm}' key='no' />" value="0" <c:if test="${!empty portalQuickMenu.quickMenuId && portalQuickMenu.count == 0}">checked="checked"</c:if> onclick="displayInputValue(0);" />
					<label for="countNo"><ikep4j:message pre="${preForm}" key="no" /></label>
					<div class="tdInstruction">
						<ikep4j:message pre="${preForm}" key="countDesc" />
					</div>
				</td>
			</tr>
			<!--//count End-->
			
			<!--intervalTime Start-->
			<tr id="intervalTimeTr" <c:if test="${!empty portalQuickMenu.quickMenuId && portalQuickMenu.count == 0}">style="display:none;"</c:if>>
				<th colspan="2" scope="row">
					<ikep4j:message pre="${preForm}" key="intervalTime" />
				</th>
				<td>
					<select id="intervalTime" name="intervalTime">
						<option value="0" <c:if test="${!empty portalQuickMenu.quickMenuId && portalQuickMenu.intervalTime == 0}">selected="selected"</c:if>><ikep4j:message pre="${preForm}" key="noUse" /></option>
						<option value="1" <c:if test="${!empty portalQuickMenu.quickMenuId && portalQuickMenu.intervalTime == 1}">selected="selected"</c:if>>1<ikep4j:message pre="${preForm}" key="min" /></option>
						<option value="2" <c:if test="${!empty portalQuickMenu.quickMenuId && portalQuickMenu.intervalTime == 2}">selected="selected"</c:if>>2<ikep4j:message pre="${preForm}" key="min" /></option>
						<option value="3" <c:if test="${!empty portalQuickMenu.quickMenuId && portalQuickMenu.intervalTime == 3}">selected="selected"</c:if>>3<ikep4j:message pre="${preForm}" key="min" /></option>
						<option value="4" <c:if test="${!empty portalQuickMenu.quickMenuId && portalQuickMenu.intervalTime == 4}">selected="selected"</c:if>>4<ikep4j:message pre="${preForm}" key="min" /></option>
						<option value="5" <c:if test="${!empty portalQuickMenu.quickMenuId && portalQuickMenu.intervalTime == 5}">selected="selected"</c:if>>5<ikep4j:message pre="${preForm}" key="min" /></option>
					    <option value="10" <c:if test="${!empty portalQuickMenu.quickMenuId && portalQuickMenu.intervalTime == 10}">selected="selected"</c:if>>10<ikep4j:message pre="${preForm}" key="min" /></option>
                        <option value="15" <c:if test="${!empty portalQuickMenu.quickMenuId && portalQuickMenu.intervalTime == 15}">selected="selected"</c:if>>15<ikep4j:message pre="${preForm}" key="min" /></option>
                        <option value="30" <c:if test="${!empty portalQuickMenu.quickMenuId && portalQuickMenu.intervalTime == 30}">selected="selected"</c:if>>30<ikep4j:message pre="${preForm}" key="min" /></option>
					</select>
				</td>
			</tr>
			<!--//intervalTime End-->
			
			<!--countUrl Start-->
			<tr id="countUrlTr" <c:if test="${!empty portalQuickMenu.quickMenuId && portalQuickMenu.count == 0}">style="display:none;"</c:if>>
				<th colspan="2" scope="row">
					<ikep4j:message pre="${preForm}" key="countUrl" />
				</th>
				<td>
					<div>
						<input id="countUrl" name="countUrl" type="text" class="inputbox w100" title="<ikep4j:message pre='${preForm}' key='countUrl' />" value="${portalQuickMenu.countUrl}" />
					</div>
				</td>
			</tr>
			<!--//countUrl End-->
			
			<!--iconId Start-->
			<tr>
				<th colspan="2" scope="row">
					<ikep4j:message pre="${preForm}" key="iconId" />
				</th>
				<td>	
					<div>				
						<input id="iconId" name="iconId" type="text" class="inputbox w70" title="<ikep4j:message pre='${preForm}' key='iconId' />" value="${portalQuickMenu.iconId}" />
						<a href="#" class="button_s" onclick="show_layer(); return false;"><span><ikep4j:message pre="${preButton}" key="preview" /></span></a>
						<!--layer start-->
						<div id="previewArea" class="process_layer" style="position:absolute; min-width:120px; margin-top:10px; z-index:99; display:none;">
							<div class="process_layer_t">
								<ikep4j:message pre="${preButton}" key="preview" />
								<a href="#" id="btnPreviewClose" onclick="iconPreviewClose(); return false;">
									<img src="<c:url value='/base/images/icon/ic_close_layer.gif'/>" alt="Close" />
								</a>
							</div>
							<div class="subTitle_2 noline" style="padding:1px 5px;">
								<h3><ikep4j:message pre="${preForm}" key="classId" /> : <span id="classId"></span></h3>
							</div>
							<div id="previewDiv" style="text-align:center; padding:0px 5px 2px 5px;">	
								<ul>
									<li>							
										<a id="previewImage" href="#" onclick="return false;" class=""></a>
									</li>
								</ul>
							</div>
						</div>
						<!--layer end-->
					</div>
					<div class="tdInstruction">
						<ikep4j:message pre="${preForm}" key="iconIdDesc" />
					</div>
				</td>
			</tr>
			<!--//iconId End-->
																																				
		</tbody>
	</table>
</div>
<!--//blockDetail End-->

</form>
					
<c:if test="${action == 'add' || action == 'modify'}">						
<!--blockButton Start-->
<div class="blockButton"> 
	<ul>
		<li><a id="saveMenuButton" class="button" href="#" onclick="saveMenu(); return false;"><span><ikep4j:message pre="${preButton}" key="save" /></span></a></li>
		<c:if test="${action == 'modify'}">
		<li><a id="removeMenuButton" class="button" href="#" onclick="removeMenu('${portalQuickMenu.quickMenuId}'); return false;"><span><ikep4j:message pre="${preButton}" key="delete" /></span></a></li>
		</c:if>
		<li><a id="cancelMenuButton" class="button" href="#" onclick="cancelMenu(); return false;"><span><ikep4j:message pre="${preButton}" key="cancel" /></span></a></li>
	</ul>
</div>
<!--//blockButton End-->
</c:if>