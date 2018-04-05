<%@ include file="/base/common/taglibs.jsp"%>
<%@ page language="java" errorPage="/common/error.jsp"
	pageEncoding="UTF-8" contentType="text/html;charset=utf-8"%>
<%@ taglib uri="/WEB-INF/tld/ikep4j.tld" prefix="ikep4j"%>

<c:set var="prefixMessage" value="message.portal.admin.screen.portlet.readPortlet"/>
<c:set var="prefixUI" value="ui.portal.admin.screen.portlet.readPortlet"/>

<c:set var="portletPortlet" value="${portletPortlet}"/>
	<script type="text/javascript">
		function updatePortletForm() {
			$jq("#mainForm").attr("action","<c:url value='/portal/admin/screen/portlet/updatePortletForm.do'/>");
			$jq("#mainForm").submit();
		}
		
		function removePortlet() {
			if(confirm("<ikep4j:message pre="${prefixMessage}" key="alert.remove" />")){
				var mainForm = document.mainForm;
				
				mainForm.action = '<c:url value="/portal/admin/screen/portlet/deletePortlet.do"/>';
				
				mainForm.submit();
			}
		}
		
		function listPortlet() {
			$jq("#mainForm").attr("action","<c:url value='/portal/admin/screen/portlet/listPortlet.do'/>");
			$jq("#mainForm").submit();
		}
		
		(function($) {

			/**
			 * onload시 수행할 코드
			 */
			$(document).ready(function() {
				
				//left menu setting
				$jq("#portletMangerOfLeft").click();
				
				if('${portalPortlet.configMode}'=='0'){$("#configViewUrlTr").hide();}
				if('${portalPortlet.maxMode}'=='0'){$("#maxViewUrlTr").hide();}
				if('${portalPortlet.moreMode}'=='0'){$("#moreViewUrlTr").hide();}
				if('${portalPortlet.helpMode}'=='0'){$("#helpViewUrlTr").hide();}
				
				/* Preview 이미지 Start */
				$("#btnPreview").click(function() {
					$("#previewArea").show();
					$("#previewImage").removeClass();
					$("#previewImage").addClass('${portalPortlet.previewImageId}')
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
				
				//D000002
				 //className,resourceName,operationName,"User,Group,Role,Job,Duty",th%
				 iKEP.readSecurity("Portal-Portlet","${portalPortlet.portletId}","READ","User,Group,Role,Job,Duty");
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
<form method="post"	name="mainForm" id="mainForm" >
<input type="hidden" id="portletId" name="portletId" value="${portalPortlet.portletId}"/>
<input type="hidden" id="pageIndex" name="pageIndex" value="${searchCondition.pageIndex }"/>
</form>
	<div id="viewDiv">
			<table id="mainTable" summary="<ikep4j:message pre="${prefixUI}" key="detail.summary" />">
				<caption></caption>
				<colgroup>
					<col width="10%"/>
					<col width="8%"/>
					<col width="82%"/>
				</colgroup>
				<tbody>
					<tr>
						<th scope="row" colspan="2" ><ikep4j:message pre="${prefixUI}" key="detail.portletType" /></th>
						<td>
						${portalPortlet.portletType}
						</td>
					</tr>
					<c:if test="${portalPortlet.portletType != 'HTML' && portalPortlet.portletType != 'JSON' }">
					<tr id="fileDiv">
						<th scope="row" colspan="2"><ikep4j:message pre="${prefixUI}" key="detail.warFileName" /></th>
						<td>
						<a href='<c:url value="/support/fileupload/downloadFile.do?fileId=${portalPortlet.warFileId}"/>'>${portalPortlet.warFileName}</a>
						</td>
					</tr>
					</c:if>
					<tr>
						<th scope="row" rowspan="${localeSize}"><ikep4j:message pre="${prefixUI}" key="detail.portletName" /></th>
						<c:forEach var="i18nMessage" items="${portalPortlet.i18nMessageList}" varStatus="loopStatus">
						<c:if test="${i18nMessage.fieldName eq 'portletName' }">
						<c:if test="${i18nMessage.index > 1}">
							<tr>
						</c:if>
								<th scope="row"><c:out value="${i18nMessage.localeCode}"/></th>
								<td>
									${i18nMessage.itemMessage}
								</td>
							</tr>
						</c:if>
						</c:forEach>
					<tr>
						<th scope="row" rowspan="${localeSize}"><ikep4j:message pre="${prefixUI}" key="detail.portletDescription" /></th>
						<c:forEach var="i18nMessage" items="${portalPortlet.i18nMessageList}" varStatus="loopStatus">
						<c:if test="${i18nMessage.fieldName eq 'portletDesc' }">
						<c:if test="${i18nMessage.index > 1}">
							<tr>
						</c:if>
								<th scope="row"><c:out value="${i18nMessage.localeCode}"/></th>
								<td>
									${i18nMessage.itemMessage}
								</td>
							</tr>
						</c:if>
						</c:forEach>
				
					
					<tr>
						<th scope="row"  colspan="2"><ikep4j:message pre="${prefixUI}" key="detail.systemName" /></th>
						<td>
						${portalPortlet.systemName}
						</td>
					</tr>
					<tr>
						<th scope="row"colspan="2" ><ikep4j:message pre="${prefixUI}" key="detail.portletCategoryName" /></th>
						<td>
						${portalPortlet.portletCategoryName}
						</td>
					</tr>
					<tr id="previewClassIdTr">
						<th scope="row" colspan="2"><ikep4j:message pre="${prefixUI}" key="detail.previewImageId" /></th>
						<td>
						${portalPortlet.previewImageId}
						<a class="button_s" href="#a" id="btnPreview"><span><ikep4j:message pre="${prefixUI}" key="button.preview" /></span></a>
							<!--layer start-->
							<div id="previewArea" class="process_layer" style="position:absolute;min-width:150px;margin-top:10px;z-index:99;display:none">
								<div class="process_layer_t">
									<ikep4j:message pre="${prefixUI}" key="button.preview" />
									<a href="#a" id="btnPreviewClose"><img src="<c:url value="/base/images/icon/ic_close_layer.gif"/>" alt="Close" /></a>
								</div>
								<div>
									<div style="margin-top:10px;"> 
										<a id="previewImage" ></a>
									</div>
								</div>
							</div>		
							<!--layer end-->
						</td>
					</tr>
					<tr>
						<th scope="row" colspan="2"><ikep4j:message pre="${prefixUI}" key="detail.moveable" /></th>
						<td>
						<c:choose>
						<c:when test="${portalPortlet.moveable==1}">
						<ikep4j:message pre="${prefixUI}" key="detail.radio.possible" />
						</c:when>
						<c:otherwise>
						<ikep4j:message pre="${prefixUI}" key="detail.radio.impossible" />
						</c:otherwise>
						</c:choose>
						</td>
					</tr>
					
					<tr>
						<th scope="row" colspan="2"><ikep4j:message pre="${prefixUI}" key="detail.linkType" /></th>
						<td>
						<span>
						<c:choose>
						<c:when test="${portalPortlet.linkType eq 'PortletSimple'}">
						<ikep4j:message pre="${prefixUI}" key="detail.radio.portletSimple" />
						</c:when>
						<c:when test="${portalPortlet.linkType eq 'PortletIFrame'}">
						<ikep4j:message pre="${prefixUI}" key="detail.radio.portletIframe" />
						</c:when>
						<c:otherwise>
						<ikep4j:message pre="${prefixUI}" key="detail.radio.portletIframeExt" />
						</c:otherwise>
						</c:choose>
						</span><span padding-left:20px><c:if test="${portalPortlet.linkType eq 'PortletIFrameExt'}"> (<ikep4j:message pre="${prefixUI}" key="detail.iframeHeight" /> : <c:out value="${portalPortlet.iframeHeight }"/>px)</c:if></span>
						</td>
					</tr>
					<tr>
						<th scope="row" colspan="2"><ikep4j:message pre="${prefixUI}" key="detail.normalViewUrl" /></th>
						<td>
						${portalPortlet.normalViewUrl}
						</td>
					</tr>
					<tr id="configViewMode">
						<th scope="row" colspan="2"><ikep4j:message pre="${prefixUI}" key="detail.configMode" /> </th>
						<td>
						<c:choose>
						<c:when test="${portalPortlet.configMode==1}">
						<ikep4j:message pre="${prefixUI}" key="detail.radio.use" />
						</c:when>
						<c:otherwise>
						<ikep4j:message pre="${prefixUI}" key="detail.radio.unuse" />
						</c:otherwise>
						</c:choose>
						</td>
					</tr>
					<tr id="configViewUrlTr">
						<th scope="row" colspan="2"><ikep4j:message pre="${prefixUI}" key="detail.configViewUrl" /></th>
						<td>
						${portalPortlet.configViewUrl}
						</td>
					</tr>
					<tr id="maxMode">
						<th scope="row" colspan="2"><ikep4j:message pre="${prefixUI}" key="detail.maxMode" /></th>
						<td>
						<c:choose>
						<c:when test="${portalPortlet.maxMode==1}">
						<ikep4j:message pre="${prefixUI}" key="detail.radio.use" />
						</c:when>
						<c:otherwise>
						<ikep4j:message pre="${prefixUI}" key="detail.radio.unuse" />
						</c:otherwise>
						</c:choose>
						</td>
					</tr>
					<tr id="maxViewUrlTr">
						<th scope="row" colspan="2"><ikep4j:message pre="${prefixUI}" key="detail.maxViewUrl" /></th>
						<td>
						${portalPortlet.maxViewUrl}
						</td>
					</tr>
					<tr id="moreMode">
						<th scope="row" colspan="2"><ikep4j:message pre="${prefixUI}" key="detail.moreMode" /> </th>
						<td>
						<c:choose>
						<c:when test="${portalPortlet.moreMode==1}">
						<ikep4j:message pre="${prefixUI}" key="detail.radio.use" />
						</c:when>
						<c:otherwise>
						<ikep4j:message pre="${prefixUI}" key="detail.radio.unuse" />
						</c:otherwise>
						</c:choose>
						</td>
					</tr>
					<tr id="moreViewUrlTr">
						<th scope="row" colspan="2"><ikep4j:message pre="${prefixUI}" key="detail.moreViewUrl" /></th>
						<td>
						${portalPortlet.moreViewUrl}
						</td>
					</tr>
					<tr id="helpMode">
						<th scope="row" colspan="2"><ikep4j:message pre="${prefixUI}" key="detail.helpMode" /> </th>
						<td>
						<c:choose>
						<c:when test="${portalPortlet.helpMode==1}">
						<ikep4j:message pre="${prefixUI}" key="detail.radio.use" />
						</c:when>
						<c:otherwise>
						<ikep4j:message pre="${prefixUI}" key="detail.radio.unuse" />
						</c:otherwise>
						</c:choose>
						</td>
					</tr>
					<tr id="helpViewUrlTr">
						<th scope="row" colspan="2"><ikep4j:message pre="${prefixUI}" key="detail.helpViewUrl" /></th>
						<td>
						${portalPortlet.helpViewUrl}
						</td>
					</tr>
					<tr id="reloadMode">
						<th scope="row" colspan="2"><ikep4j:message pre="${prefixUI}" key="detail.reloadMode" /> </th>
						<td>
						<c:choose>
						<c:when test="${portalPortlet.reloadMode==1}">
						<ikep4j:message pre="${prefixUI}" key="detail.radio.use" />
						</c:when>
						<c:otherwise>
						<ikep4j:message pre="${prefixUI}" key="detail.radio.unuse" />
						</c:otherwise>
						</c:choose>
						</td>
					</tr>
					<tr>
						<th scope="row" colspan="2"><ikep4j:message pre="${prefixUI}" key="detail.removeMode" /> </th>
						<td>
						<c:choose>
						<c:when test="${portalPortlet.removeMode==1}">
						<ikep4j:message pre="${prefixUI}" key="detail.radio.use" />
						</c:when>
						<c:otherwise>
						<ikep4j:message pre="${prefixUI}" key="detail.radio.unuse" />
						</c:otherwise>
						</c:choose>
						</td>
					</tr>
					<tr>
						<th scope="row" colspan="2"><ikep4j:message pre="${prefixUI}" key="detail.multipleMode" /> </th>
						<td>
						<c:choose>
						<c:when test="${portalPortlet.multipleMode==1}">
						<ikep4j:message pre="${prefixUI}" key="detail.radio.use" />
						</c:when>
						<c:otherwise>
						<ikep4j:message pre="${prefixUI}" key="detail.radio.unuse" />
						</c:otherwise>
						</c:choose>
						</td>
					</tr>
					<tr>
						<th scope="row" colspan="2"><ikep4j:message pre="${prefixUI}" key="detail.publicOption" /></th>
						<td>
						<c:choose>
						<c:when test="${portalPortlet.publicOption==1}">
						<ikep4j:message pre="${prefixUI}" key="detail.radio.public" />
						</c:when>
						<c:otherwise>
						<ikep4j:message pre="${prefixUI}" key="detail.radio.private" />
						</c:otherwise>
						</c:choose>
						</td>
					</tr>
					<tr>
						<th scope="row" colspan="2"><ikep4j:message pre="${prefixUI}" key="detail.headerMode" /> </th>
						<td>
						<c:choose>
						<c:when test="${portalPortlet.headerMode==1}">
						<ikep4j:message pre="${prefixUI}" key="detail.radio.view" />
						</c:when>
						<c:otherwise>
						<ikep4j:message pre="${prefixUI}" key="detail.radio.unview" />
						</c:otherwise>
						</c:choose>
						</td>
					</tr>
					<tr>
						<th scope="row" colspan="2"><ikep4j:message pre="${prefixUI}" key="detail.status" /> </th>
						<td>
						<c:choose>
						<c:when test="${portalPortlet.status==1}">
						<ikep4j:message pre="${prefixUI}" key="detail.radio.use" />
						</c:when>
						<c:otherwise>
						<ikep4j:message pre="${prefixUI}" key="detail.radio.unuse" />
						</c:otherwise>
						</c:choose>
						</td>
					</tr>
					<tr>
						<th scope="row" colspan="2"><ikep4j:message pre="${prefixUI}" key="detail.shareYn" /> </th>
						<td>
						<c:choose>
						<c:when test="${portalPortlet.shareYn=='Y'}">
						<ikep4j:message pre="${prefixUI}" key="detail.share" />
						</c:when>
						<c:otherwise>
						<ikep4j:message pre="${prefixUI}" key="detail.nonshared" />
						</c:otherwise>
						</c:choose>
						</td>
					</tr>
					<tr>
						<th scope="row" rowspan="4"><ikep4j:message pre="${prefixUI}" key="detail.cache" /> </th>
						<th scope="row"><ikep4j:message pre="${prefixUI}" key="detail.cacheYn" /> </th>
						<td>
						<c:choose>
						<c:when test="${portalPortlet.cacheYn=='Y'}">
						<ikep4j:message pre="${prefixUI}" key="detail.radio.use" />
						</c:when>
						<c:otherwise>
						<ikep4j:message pre="${prefixUI}" key="detail.radio.unuse" />
						</c:otherwise>
						</c:choose>
						</td>
					</tr>
					<tr>
						<th scope="row"><ikep4j:message pre="${prefixUI}" key="detail.cacheNameStr" /> </th>
						<td>
							${portalPortlet.cacheNameStr}
						</td>
					</tr>
					<tr>
						<th scope="row"><ikep4j:message pre="${prefixUI}" key="detail.cacheLiveSecond" /> </th>
						<td>
							${portalPortlet.cacheLiveSecond}
						</td>
					</tr>
					<tr>
						<th scope="row"><ikep4j:message pre="${prefixUI}" key="detail.cacheMaxCount" /> </th>
						<td>
							${portalPortlet.cacheMaxCount}
						</td>
					</tr>
				</tbody>

			</table>
	</div>

</div>
<!--//blockDetail End-->

<!--blockButton Start-->
<div class="blockButton">
	<ul>
		<li>
		<a class="button" href="javascript: updatePortletForm();"><span><ikep4j:message pre="${prefixUI}" key="button.update" /></span></a>
		<a class="button" href="javascript: removePortlet();"><span><ikep4j:message pre="${prefixUI}" key="button.delete" /></span></a>
		<a class="button" href="javascript: listPortlet();"><span><ikep4j:message pre="${prefixUI}" key="button.list" /></span></a>
		</li>
	</ul>
</div>
<!--//blockButton End-->