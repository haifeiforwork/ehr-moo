<%@ include file="/base/common/taglibs.jsp"%>
<%@ page language="java" errorPage="/common/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ taglib uri="/WEB-INF/tld/ikep4j.tld" prefix="ikep4j" %> 

<c:set var="preAlert" value="message.portal.admin.screen.menu.portalMenuForm.alert"/>
<c:set var="preTitle" value="ui.portal.admin.screen.menu.portalMenuForm.title"/>						
<c:set var="preForm" value="ui.portal.admin.screen.menu.portalMenuForm.form"/>
<c:set var="preButton" value="ui.portal.admin.screen.menu.portalMenuForm.button"/>

<script type="text/javascript"> 
//<![CDATA[
(function($) {	
	
	displayInputValue = function (value) {
		
		if(value == 0) { 
			$jq("#urlTr").show();
			$jq("#urlTypeTr").show();
			$jq("#targetTr").show();
			
			$jq("#urlTypeUrl").attr("checked", true);
			$jq("#targetInner").attr("checked", true);
		} else if(value == 1){
			$jq("#urlTr").hide();
			$jq("#urlTypeTr").hide();
			$jq("#targetTr").hide();
		}
		
	};
	
	show_layer = function () {
// 		iKEP.showDialog({
// 		    title : "<ikep4j:message pre='${preTitle}' key='iconPreviewTitle' />",
// 		    url : "<c:url value='/admin/screen/menu/portalMenuIconPreView.do'/>",
// 		    width : 520, 
// 		    height : 250,
// 		    modal : true, 
// 		    resizable : false
// 		});

		var classValue = $jq("#iconId").val();
		var noImage = '<img src="<c:url value="/base/images/common/noimage_110x90.gif"/>" alt="noImage"/>';
		
		$jq("#previewArea").show();
		$jq("#previewImage").html("")
			.css("background-image", "")
			.removeClass()
			.addClass(classValue);
		if($jq("#previewImage").css("background-image") == "none") {	
			$jq("#previewImage").removeClass()
				.html(noImage);
			$jq("#classId").html(classValue);
		} else {
			$jq("#previewImage").removeClass()
				.addClass(classValue);
			$jq("#classId").html(classValue);
		}
		
	};
	
	popupSystemUrl = function () {
		
		var url = "<c:url value='/admin/screen/systemurl/popupPortalSystemUrlList.do' />";
		
		iKEP.popupOpen(url, {width:800, height:510}, "PortalSystemUrl");
		
	};
	
	getUrlValue = function() {
		
		$jq("input[name=url]").val($jq("input[name=urlInput]").val());
		
	};
	
	selfInputCheck = function () {
		
		if($jq("#selfInput").is(":checked")) {
			$jq("#url").attr("readonly", false);
		} else {
			$jq("#url").val("");
			$jq("#url").attr("readonly", true);
		}
		
	};
	
	iconPreviewClose = function() {
		
		$jq("#previewArea").hide();
		
	};
	
	$jq(document).ready(function() {
		$jq("#portalMenuForm:input:visible:enabled:first").focus();
		
		iKEP.loadSecurity("Portal-Menu", "${portalMenu.menuId}", "READ", "User,Group,Role,Job,Duty,ExpUser");
		
		var url = "";
		
		<c:choose>
		<c:when test="${!empty portalMenu.menuId}">
		url = "<c:url value='portalMenuUpdate.do' />";
		</c:when>
		<c:otherwise>
		url = "<c:url value='portalMenuCreate.do' />";
		</c:otherwise>
		</c:choose>
		
		/**
		 * Validation Logic Start
		 */
		var validOptions = {
			rules : {
				<c:forEach var="i18nMessage" items="${portalMenu.i18nMessageList}" varStatus="loopStatus">
				"i18nMessageList[${loopStatus.index}].itemMessage" : {
					required : true,
					maxlength : 100
				},
				</c:forEach>
				"description" : {
					required : true,
					maxlength : 250
				},
				"url" : {
					maxlength : 500
				},
				"iconId" : {
					maxlength : 27,
					pattern : /^[A-Za-z0-9_]*$/
				}
			},
			messages : {
				<c:forEach var="i18nMessage" items="${portalMenu.i18nMessageList}" varStatus="loopStatus">
				"i18nMessageList[${loopStatus.index}].itemMessage" : {
					required : "<ikep4j:message key='NotEmpty.portalMenu.menuName' />",
					maxlength : "<ikep4j:message key='Size.portalMenu.menuName' />"
				},
				</c:forEach>
				"description" : {
					required : "<ikep4j:message key='NotEmpty.portalMenu.description' />",
					maxlength : "<ikep4j:message key='Size.portalMenu.description' />"
				},
				"url" : {
					maxlength : "<ikep4j:message key='Size.portalMenu.url' />"
				},
				"iconId" : {
					maxlength : "<ikep4j:message key='Size.portalMenu.iconId' />",
					pattern : "<ikep4j:message key='Pattern.portalMenu.iconId' />"
				}
			},
			submitHandler : function() {
				
				$jq.ajax({
					url : url,
					data : $jq("#portalMenuForm").serialize(),
					type : "post",
					success : function(result) {
						
						if(result.action == "success") {
						
							alert("<ikep4j:message pre='${preAlert}' key='saveSuccess' />");
							
							<c:choose>
							<c:when test="${!empty portalMenu.menuId}">
							//document.location.reload();
							$currItem.children("a").html($jq("input[id=${userLocaleCode}]").val());
							
							saveAfter();
							</c:when>
							<c:otherwise>
							$currItem.remove();
							
							var menuId = result.menuId;
							var parentMenuId = result.parentMenuId;
							
							var modifyImg = "<c:url value='/base/images/icon/ic_cir_modify.png'/>";
							var deleteImg = "<c:url value='/base/images/icon/ic_cir_minus.png'/>";
							
							if(result.parentMenuId != null && result.parentMenuId != "") {
								var resizeDiv = '<div class="menuguide_resize none">'
									  + '<div class="ic_rt"><a href="#" onclick="modifyMenu(this, '+menuId+'); return false;"><img src="'+modifyImg+'" alt="modify"/></a></div>'
									  + '<div class="ic_rb"><a href="#" onclick="removeMenu(this, '+menuId+'); return false;"><img src="'+deleteImg+'" alt="delete" /></a></div>'
									  + '</div>';
								var $ul = $jq("#subMenuList_"+result.parentMenuId);
								var $li = $jq('<li/>').appendTo($ul)
										.append('<a href="#" onclick="return false;">'+$jq("input[id=${userLocaleCode}]").val()+'</a>')
										.append(resizeDiv);
							} else {
								var $ul = $jq("#topMenuList");
								var id = "subMenuList_"+menuId;		
								
								var downArrowImg = "<c:url value='/base/images/icon/ic_arb_down_2.gif'/>";
								
								var resizeDiv = '<div class="menuguide_resize none">'
											  + '<div class="ic_rt"><a href="#" onclick="modifyMenu(this, '+menuId+'); return false;"><img src="'+modifyImg+'" alt="modify"/></a></div>'
											  + '<div class="ic_rb"><a href="#" onclick="removeMenu(this, '+menuId+'); return false;"><img src="'+deleteImg+'" alt="delete" /></a></div>'
											  + '</div>';
								
								var $li = $('<li/>').appendTo($ul)
										.append('<a href="#" onclick="return false;">'+$jq("input[id=${userLocaleCode}]").val()+'</a>')
										.append(resizeDiv)
										.append('<ul id="'+id+'" class="subMenuList"/>')
										.append('<div class="addSubMenu"><span><a href="#" onclick="addSubMenu('+menuId+'); return false;">Add Menu <img src="'+downArrowImg+'" alt="" /></a></span></div>')
										.end();
								
								outBoxResize("add");
							
								makeSubMenuSortable(id);
							}
							
							saveAfter();
							</c:otherwise>
							</c:choose>
						} else {
							alert("<ikep4j:message pre='${preAlert}' key='saveFail' />");
							
							return;
						}
					},
					error : function(xhr, exMessage) {
						var errorItems = $.parseJSON(xhr.responseText).exception;
						validator.showErrors(errorItems);
					}
				});
			}
		};
		
		var validator = new iKEP.Validator("#portalMenuForm", validOptions);
	
		/**
		 * Validation Logic End
		 */
	});
	
})(jQuery);
//]]>
</script>
<form name="portalMenuForm" id="portalMenuForm" action="" method="post" >
<input type="hidden" id="menuId" name="menuId" value="<c:out value='${portalMenu.menuId}'/>" />
<input type="hidden" id="parentMenuId" name="parentMenuId" value="<c:out value='${portalMenu.parentMenuId}'/>" />
<input type="hidden" id="sortOrder" name="sortOrder" value="<c:out value='${portalMenu.sortOrder}'/>" />
<input type="hidden" id="systemCode" name="systemCode" value="${portalSystem.systemCode}" />
<input type="hidden" id="menuName" name="menuName" value="" />
<input type="hidden" id="menuType" name="menuType" value="" />
<input type="hidden" id="urlInput" name="urlInput" value="" />
<input type="hidden" id="urlType" name="urlType" value="" />
<input type="hidden" id="target" name="target" value="" />
 
<div class="blockDetail">
	<table id="mainTable" summary="<ikep4j:message pre='${preForm}' key='tableSummary' />">
		<caption></caption>
		<colgroup>
			<col width="10%"/>
			<col width="8%"/>
			<col width="82%"/>
		</colgroup>
		<tbody>
			<!--menuName Start-->
			<tr>
				<th scope="row" rowspan="${localeSize}">
					<span class="colorPoint">*</span><ikep4j:message pre="${preForm}" key="menuName" />
				</th>
				<c:forEach var="i18nMessage" items="${portalMenu.i18nMessageList}" varStatus="loopStatus">
				<c:if test="${i18nMessage.fieldName eq 'menuName' }">
				<c:if test="${i18nMessage.index > 1}">
			<tr>
				</c:if>
				<th scope="row">
					<span class="colorPoint">*</span><c:out value="${i18nMessage.localeCode}"/>
				</th>
				<td>
					<div>
					<spring:bind path="portalMenu.i18nMessageList[${loopStatus.index}].itemMessage">
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
			<!--//menuName End-->
			
			<!--description Start-->	
			<tr>
				<th colspan="2" scope="col">
					<span class="colorPoint">*</span><ikep4j:message pre="${preForm}" key="description" />
				</th>
				<td class="textLeft">
					<div>
						<input type="text" id="description" name="description" class="inputbox w100" value="${portalMenu.description}" />
					</div>
				</td>
			</tr>
			<!--//description End-->
			
			<!--menuType Start-->
			<tr>
				<th colspan="2" scope="col">
					<ikep4j:message pre="${preForm}" key="menuType" />
				</th>
				<td>
					<input type="radio" class="radio" title="<ikep4j:message pre='${preForm}' key='link' />" id="menuTypeItem" name="menuTypeRadio" value="ITEM" <c:choose><c:when test="${empty portalMenu.menuId}">checked="checked"</c:when><c:when test="${!empty portalMenu.menuId && portalMenu.menuType == 'ITEM'}">checked="checked"</c:when><c:otherwise></c:otherwise></c:choose> onclick="displayInputValue(0);" />
					<label for="menuTypeItem"><ikep4j:message pre="${preForm}" key="link" /></label>&nbsp;
					<input type="radio" class="radio" title="<ikep4j:message pre='${preForm}' key='category' />" id="menuTypeCategory" name="menuTypeRadio" value="CATEGORY" <c:if test="${!empty portalMenu.menuId && portalMenu.menuType == 'CATEGORY'}">checked="checked"</c:if> onclick="displayInputValue(1);" />
					<label for="menuTypeCategory"><ikep4j:message pre="${preForm}" key="category" /></label>
				</td>
			</tr>
			<!--//menuType End-->
			
			<!--urlType Start-->
			<tr id="urlTypeTr" <c:if test="${!empty portalMenu.menuId && portalMenu.menuType == 'CATEGORY'}">style="display:none;"</c:if>>
				<th colspan="2" scope="col">
					<ikep4j:message pre="${preForm}" key="urlType" />
				</th>
				<td>
					<input type="radio" class="radio" title="URL" id="urlTypeUrl" name="urlTypeRadio" value="URL" <c:choose><c:when test="${empty portalMenu.menuId}">checked="checked"</c:when><c:when test="${!empty portalMenu.menuId && portalMenu.urlType == 'URL'}">checked="checked"</c:when><c:otherwise></c:otherwise></c:choose> />
					<label for="urlTypeUrl"><ikep4j:message pre="${preForm}" key="url" /></label>&nbsp;
					<input type="radio" class="radio" title="JAVASCRIPT" id="urlTypeJavascript" name="urlTypeRadio" value="JAVASCRIPT" <c:if test="${!empty portalMenu.menuId && portalMenu.urlType == 'JAVASCRIPT'}">checked="checked"</c:if> />
					<label for="urlTypeJavascript"><ikep4j:message pre="${preForm}" key="javascript" /></label>
				</td>
			</tr>
			<!--//urlType End-->
			
			<!--url Start-->
			<tr id="urlTr" <c:if test="${!empty portalMenu.menuId && portalMenu.menuType == 'CATEGORY'}">style="display:none;"</c:if>>
				<th colspan="2" scope="col">
					<ikep4j:message pre="${preForm}" key="url" />
				</th>
				<td>
					<div>
						<input type="text" id="url" name="url" class="inputbox w70" title="URL" value="${portalMenu.url}" readonly="readonly" />&nbsp;
						<input type="checkbox" id="selfInput" name="selfInput" class="checkbox valign_middle" title="<ikep4j:message pre='${preForm}' key='directInput' />" value="" onclick="selfInputCheck();" /><ikep4j:message pre="${preForm}" key="directInput" />&nbsp;
						<a href="#" class="button_s" onclick="popupSystemUrl(); return false;"><span><ikep4j:message pre="${preButton}" key="example" /></span></a>
					</div>
				</td>
			</tr>
			<!--//url End-->
			
			<!--target Start-->
			<tr	id="targetTr" <c:if test="${!empty portalMenu.menuId && portalMenu.menuType == 'CATEGORY'}">style="display:none;"</c:if>>
				<th colspan="2" scope="col">
					<ikep4j:message pre="${preForm}" key="target" />
				</th>
				<td>
					<input type="radio" id="targetInner" name="targetRadio" class="radio" title="<ikep4j:message pre='${preForm}' key='inner' />" value="INNER" <c:choose><c:when test="${empty portalMenu.menuId}">checked="checked"</c:when><c:when test="${!empty portalMenu.menuId && portalMenu.target == 'INNER'}">checked="checked"</c:when><c:otherwise></c:otherwise></c:choose> />
					<label for="targetInner"><ikep4j:message pre="${preForm}" key="inner" /></label>&nbsp;
					<input type="radio" id="targetWindow" name="targetRadio" class="radio" title="<ikep4j:message pre='${preForm}' key='window' />" value="WINDOW" <c:if test="${!empty portalMenu.menuId && portalMenu.target == 'WINDOW'}">checked="checked"</c:if> />
					<label for="targetWindow"><ikep4j:message pre="${preForm}" key="window" /></label>
				</td>
			</tr>
			<!--//target End-->
			
			<!--iconId Start-->
			<tr id="previewTr" <c:if test="${!empty portalMenu.parentMenuId}">style="display:none"</c:if>>
				<th colspan="2" scope="col">
					<ikep4j:message pre="${preForm}" key="menuIcon" />
				</th>
				<td>
					<div>
						<input id="iconId" name="iconId" type="text" class="inputbox w70" title="ICON" value="<c:out value='${portalMenu.iconId}' default='iconMenu_01'/>" />
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
							<div id="topMenu" style="width:120px; text-align:center;">
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
						<ikep4j:message pre="${preForm}" key="menuIconDesc1" /><br/>
						<ikep4j:message pre="${preForm}" key="menuIconDesc2" />
					</div>
				</td>
			</tr>
			<!--//iconId End-->
																																					
		</tbody>
	</table>
</div>
<!--//blockDetail End-->

</form>
						
<!--blockButton Start-->
<div class="blockButton"> 
	<ul>
		<li><a class="button" href="#" onclick="saveMenu(); return false;"><span><ikep4j:message pre="${preButton}" key="save" /></span></a></li>
		<li><a class="button" href="#" onclick="cancelMenu(); return false;"><span><ikep4j:message pre="${preButton}" key="cancel" /></span></a></li>
	</ul>
</div>	
<!--//blockButton End-->