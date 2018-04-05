<%@ include file="/base/common/taglibs.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<c:set var="prefix" value="ui.portal.admin.screen.popup.readPopup"/>
<c:set var="prefixMessage" value="message.portal.admin.screen.popup.readPopup"/>

<script type="text/javascript">
<!--
(function($) {
	
	var dialog;
	
	var popupLeft = 50;
	var popupWidth = 0;
	var popupTop = 50;
	var popupWidthInterval = 25;

	var layerLeft = 50;
	var layerTop = 100;
	var layerWidth = 0;
	var layerHeight = 0;
	var layerWidthInterval = 25;
	var layerTopInterval = 47;
	
    $jq(document).ready(function() {
    	$jq("#previewButton").click(function() {
    		if(${portalPopup.windowYn == 'Y'}) {
    			if(${portalPopup.popupWidth} < 250) {
    				popupWidth = 250;
	    		} else {
	    			popupWidth = ${portalPopup.popupWidth};
	    		}
				
				//Popup type
    			iKEP.popupOpen('<c:url value="/portal/admin/screen/popup/mainPopup.do?popupId=${portalPopup.popupId}"/>', {width:popupWidth, height:${portalPopup.popupHeight}, top:popupTop, left:popupLeft, resizable:false, scrollbar:false}, 'readPopupPreView');
    			//popupLeft += popupWidth +  popupWidthInterval;
			} else {
    			if(${portalPopup.popupWidth} < 250) {
    				layerWidth = 250;
    				layerHeight = parseInt(${portalPopup.popupHeight}) + parseInt(layerTopInterval);
	    		} else {
	    			layerWidth = ${portalPopup.popupWidth};
	    			layerHeight = parseInt(${portalPopup.popupHeight}) + parseInt(layerTopInterval);
	    		}
    			//alert(dialog_${portalPopup.popupId});
				dialog = iKEP.showDialog({
					title: '${portalPopup.popupName}',
					url: '<c:url value="/portal/admin/screen/popup/mainPopup.do?popupId=${portalPopup.popupId}"/>',
					width: layerWidth,
					height: layerHeight,
					modal: false,
					scroll: "no",
					resizable:false,
					position : [layerLeft, layerTop]
				});
			}
		});
    	
    	$jq("#updateButton").click(function() {
			location.href = "<c:url value='updatePopupForm.do?popupId=${portalPopup.popupId}'/>";
		});
    	
    	$jq("#removeButton").click(function() {
			if(confirm("<ikep4j:message pre='${prefixMessage}' key='alert.removeMessage'/>")) {
				alert("<ikep4j:message pre='${prefixMessage}' key='alert.removed'/>");
				$jq("#readPopupForm")[0].submit();
			}
		});
    	
    	//className,resourceName,operationName,"권한설정 옵션"
		iKEP.readSecurity("Portal-Popup","${portalPopup.popupId}","READ","User,Group,Role,Job,Duty", 25);
    });
})(jQuery);
//-->
</script>

<form id="readPopupForm" method="post" action="<c:url value='removePopup.do'/>" >
	<input type="hidden" name="popupId" value="${portalPopup.popupId}" title="popupId" />
	<!--pageTitle Start-->
	<div id="pageTitle">
		<h2><ikep4j:message pre="${prefix}" key="pageTitle" /></h2>
	</div>
	<!--//pageTitle End-->
	
	<div class="blockDetail">
		<table summary="<ikep4j:message pre='${prefix}' key='summary'/>">
			<caption></caption>
			<tbody>
				<tr>
					<th width="18%" scope="row" nowrap="nowrap"><ikep4j:message pre='${prefix}' key='popupName'/></th>
					<td width="82%">
						${portalPopup.popupName}				
					</td>
				</tr>
				<tr>
					<th scope="row" nowrap="nowrap"><ikep4j:message pre='${prefix}' key='popupWidth'/></th>
					<td>
						${portalPopup.popupWidth}							
					</td>
				</tr>
				<tr>
					<th scope="row" nowrap="nowrap"><ikep4j:message pre='${prefix}' key='popupHeight'/></th>
					<td>
						${portalPopup.popupHeight}						
					</td>
				</tr>
				<tr>
					<th scope="row" nowrap="nowrap"><ikep4j:message pre='${prefix}' key='useDate'/></th>
					<td>
						${portalPopup.popupStartDate} ~ ${portalPopup.popupEndDate}
					</td>
				</tr>
				<tr>
					<th scope="row" nowrap="nowrap"><ikep4j:message pre='${prefix}' key='isUse'/></th>
					<td>
						<c:choose>
							<c:when test="${portalPopup.popupActive == 1}">
								<ikep4j:message pre='${prefix}' key='use'/>
							</c:when>
							<c:otherwise>
								<ikep4j:message pre='${prefix}' key='noUse'/>
							</c:otherwise>
						</c:choose>
					</td>
				</tr>
				<tr>
					<th scope="row" nowrap="nowrap"><ikep4j:message pre='${prefix}' key='howtopopup'/></th>
					<td>
						<c:choose>
							<c:when test="${portalPopup.windowYn == 'Y'}">
								<ikep4j:message pre='${prefix}' key='newWindow'/>
							</c:when>
							<c:otherwise>
								<ikep4j:message pre='${prefix}' key='layer'/>
							</c:otherwise>
						</c:choose>
					</td>
				</tr>				
				<tr>
					<th scope="row" nowrap="nowrap"><ikep4j:message pre='${prefix}' key='bgImage'/></th>
					<td>
						<c:choose>
							<c:when test="${portalPopup.popupTheme == 'N' || empty portalPopup.popupTheme}">
								<ikep4j:message pre='${prefix}' key='noUse'/>
							</c:when>
							<c:otherwise>
								<c:choose>
									<c:when test="${portalPopup.popupTheme == '1'}">
										<img width="30" height="30" alt="Popup Theme Image1" src="<c:url value='/base/images/common/skinDesign_05.gif'/>" />
									</c:when>
									<c:when test="${portalPopup.popupTheme == '2'}">
										<img width="30" height="30" alt="Popup Theme Image2" src="<c:url value='/base/images/common/skinDesign_07.gif'/>" />
									</c:when>
									<c:when test="${portalPopup.popupTheme == '3'}">
										<img width="30" height="30" alt="Popup Theme Image3" src="<c:url value='/base/images/common/skinDesign_04.gif'/>" />
									</c:when>
									<c:when test="${portalPopup.popupTheme == '4'}">
										<img width="30" height="30" alt="Popup Theme Image4" src="<c:url value='/base/images/common/skinDesign_03.gif'/>" />
									</c:when>
								</c:choose>
							</c:otherwise>
						</c:choose>
					</td>
				</tr>
				<tr>
					<th scope="row"><ikep4j:message pre='${prefix}' key='popupType'/></th>
					<td>
						<c:choose>
							<c:when test="${portalPopup.popupType == 'C'}">
								<ikep4j:message pre='${prefix}' key='popupType.contents'/>
							</c:when>
							<c:otherwise>
								<ikep4j:message pre='${prefix}' key='popupType.url'/>
							</c:otherwise>
						</c:choose>
					</td>
				</tr>
				<c:choose>
					<c:when test="${portalPopup.popupType == 'C'}">
						<tr>
							<th scope="row" nowrap="nowrap"><ikep4j:message pre='${prefix}' key='contents'/></th>
							<td style="line-height: normal;">
						        <spring:htmlEscape defaultHtmlEscape="false"> 
									<spring:bind path="portalPopup.contents">
										${status.value}
									</spring:bind>
				    		    </spring:htmlEscape> 
							</td>
						</tr>
					</c:when>
					<c:otherwise>
						<tr>
							<th scope="row" nowrap="nowrap"><ikep4j:message pre='${prefix}' key='popupUrl'/></th>
							<td>
								${portalPopup.popupUrl}
							</td>
						</tr>
					</c:otherwise>
				</c:choose>
			</tbody>
		</table>
		<div id="securityArea"></div>
	</div>
	<!--//blockDetail End-->
	
	<!--tableBottom Start-->
	<div class="tableBottom">										
		<!--blockButton Start-->
		<div class="blockButton"> 
			<ul>
				<li><a id="previewButton" class="button" href="#a"><span><ikep4j:message pre='${prefix}' key='button.preview'/></span></a></li>
				<li><a id="updateButton" class="button" href="#a"><span><ikep4j:message pre='${prefix}' key='button.modify'/></span></a></li>
				<li><a id="removeButton" class="button" href="#a"><span><ikep4j:message pre='${prefix}' key='button.remove'/></span></a></li>
				<li><a class="button" href="<c:url value='listPopup.do'/>"><span><ikep4j:message pre='${prefix}' key='button.list'/></span></a></li>
			</ul>
		</div>
		<!--//blockButton End-->
	</div>
</form>