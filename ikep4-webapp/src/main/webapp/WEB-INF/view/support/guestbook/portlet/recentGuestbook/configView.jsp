<%@ page language="java" errorPage="/base/common/error.jsp"  pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ include file="/base/common/taglibs.jsp"%>
<%@ taglib uri="/WEB-INF/tld/ikep4j.tld" prefix="ikep4j" %>

<%-- 메시지 관련 Prefix 선언 Start --%> 
<c:set var="portletList" value="ui.support.guestbook.portlet.list"/>

<c:set var="preProfileMain"  value="ui.support.profile.main" /> 
<c:set var="preGuestbook"  value="ui.support.guestbook.view.main" />

<c:set var="preButton"  value="ui.support.profile.common.button" />
<c:set var="preMsgProfile"  value="message.support.profile.main" />
<c:set var="preMsgGuestbook"  value="message.support.guestbook.view.main" />
<%-- 메시지 관련 Prefix 선언 End --%>

<% 
	pageContext.setAttribute("lf", "\n"); 
%>

<script type="text/javascript" src="<c:url value='/base/js/units/support/guestbook/guestbook.js'/>"></script>
<script type="text/javascript">
<!--

(function($) {
	
	$jq(document).ready(function() {
		
		$jq("#save_${portletConfigId}").click(function() {
			var reloadUrl = '<c:url value="/support/guestbook/portlet/recentGuestbook/normalView.do"/>?portletId=${portletId}';
			var listSize = $jq("#listCount_${portletConfigId}").val();
			//portletConfigId, 프로퍼티네임(최대 10Byte), 프로퍼티 값(최대 50Byte),설정 후 이동할 url : 없으면 이동하지 않음.
			PortletSimple.setListCount('${portletConfigId}', listSize, reloadUrl, '${portletId}');
		}); 
		 
	});
	
	
	// 방명록 이동
	goGuestbook = function() {
		document.location.href = "<c:url value='/portal/main/listUserMain.do?rightFrameUrl=/support/guestbook/listGuestbook.do'/>" ;
	};
	
})(jQuery);  
//-->
</script>

<c:set var="user" value="${sessionScope['ikep.user']}" />
<c:set var="portal" value="${sessionScope['ikep.portal']}" />
<div id="${portletConfigId}">
				<div class="poEdit">
					<div>
						<ikep4j:message pre='${portletList}' key='Count' /> :
						<select title="<ikep4j:message pre='${portletList}' key='Count' />" id="listCount_${portletConfigId}">
							<option value="10" <c:if test="${listSize eq '10'}">selected="selected"</c:if>><ikep4j:message pre='${portletList}' key='Count10' /></option>
							<option value="7" <c:if test="${listSize eq '7'}">selected="selected"</c:if>><ikep4j:message pre='${portletList}' key='Count7' /></option>
							<option value="5" <c:if test="${listSize eq '5'}">selected="selected"</c:if>><ikep4j:message pre='${portletList}' key='Count5' /></option>
							<option value="3" <c:if test="${listSize eq '3'}">selected="selected"</c:if>><ikep4j:message pre='${portletList}' key='Count3' /></option>
						</select>
					</div>
					
					<div class="textRight">
						<a id="save_${portletConfigId}" class="button_s" href="#a"><span><ikep4j:message pre='${portletList}' key='button.create' /></span></a>
					</div>
				</div>
				

				<div id="${portletConfigId}" class="tableList_1">
				<table summary="<ikep4j:message pre='${preProfileMain}' key='profile.contentsArea'/>">
					<caption></caption>
					<tbody>
						<c:choose>
						    <c:when test="${searchResult.emptyRecord}">
								<tr>
									<td width="100%" class="emptyRecord"><ikep4j:message pre='${preMsgGuestbook}' key='${status.expression}emptyRecord'/></td> 
								</tr>				        
						    </c:when>
						    <c:otherwise>
								<c:forEach var="guestbookList" items="${searchResult.entity}">
									<tr>
										<td scope="row" width="*"><div class="ellipsis"><a href="#a" onclick="goGuestbook();" >${guestbookList.contents}</a></div></th>
										<td class="textCenter" width="55"><div class="ellipsis"><a href="#a" onclick="iKEP.showUserContextMenu(this, '${guestbookList.registerId}', 'bottom')"><c:choose><c:when test="${user.localeCode == portal.defaultLocaleCode}"><c:out value="${guestbookList.guestbookUserName}"/></c:when><c:otherwise><c:out value="${guestbookList.guestbookUserEngName}"/></c:otherwise></c:choose> <c:choose><c:when test="${user.localeCode == portal.defaultLocaleCode}"><c:out value="${guestbookList.registerJobTitleName}"/></c:when><c:otherwise><c:out value="${guestbookList.registerJobTitleEngName}"/></c:otherwise></c:choose></a></div></td>
										<td class="textRight" width="30"><span class="date"><ikep4j:timezone pattern="MM.dd" date="${guestbookList.registDate}"/></span></td>
									</tr>
								</c:forEach>
							</c:otherwise>	
						</c:choose>																																							
					</tbody>
				</table>							
				<div class="l_b_corner"></div><div class="r_b_corner"></div>
				</div>
</div>