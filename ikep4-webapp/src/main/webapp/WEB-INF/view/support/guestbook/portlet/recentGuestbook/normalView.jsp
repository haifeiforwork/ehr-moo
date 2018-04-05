<%@ page language="java" errorPage="/base/common/error.jsp"  pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ include file="/base/common/taglibs.jsp"%>
<%@ taglib uri="/WEB-INF/tld/ikep4j.tld" prefix="ikep4j" %>

<%-- 메시지 관련 Prefix 선언 Start --%> 
<c:set var="portletList" value="ui.portal.portlet.wiseSaying.popupPortletWiseSayingList"/>

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
	// 방명록 이동
	goGuestbook_${portletConfigId} = function() {
		document.location.href = "<c:url value='/portal/main/listUserMain.do?rightFrameUrl=/support/guestbook/listGuestbook.do'/>" ;
	};
})(jQuery);  
//-->
</script>

<c:set var="user" value="${sessionScope['ikep.user']}" />
<c:set var="portal" value="${sessionScope['ikep.portal']}" />

<div id="${portletConfigId}" class="tableList_1">
	<table summary="<ikep4j:message pre='${preProfileMain}' key='profile.contentsArea'/>">
		<caption></caption>
		<colgroup>
            <col width="*"/>
            <col width="15%"/>
            <col width="10%"/>
        </colgroup>
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
							<td scope="row" width="*" class="t_po1"><div class="ellipsis"><a href="#a" onclick="goGuestbook_${portletConfigId}();" >${guestbookList.contents}</a></div></th>
							<td class="textCenter">
								<div class="ellipsis">									
									<c:choose>										
										<c:when test="${user.localeCode == portal.defaultLocaleCode}">
											<a href="#a" onclick="iKEP.showUserContextMenu(this, '${guestbookList.registerId}', 'bottom')" title="${guestbookList.guestbookUserName} ${guestbookList.registerJobTitleName}">
												${guestbookList.guestbookUserName} 
											</a>
										</c:when>
										<c:otherwise>
											<a href="#a" onclick="iKEP.showUserContextMenu(this, '${guestbookList.registerId}', 'bottom')" title="${guestbookList.guestbookUserEngName} ${guestbookList.registerJobTitleEngName}">
												${guestbookList.guestbookUserEngName}
											</a>
										</c:otherwise>										
									</c:choose>
								</div>
							</td>
							<td class="textRight" width="30"><span class="date"><ikep4j:timezone pattern="MM.dd" date="${guestbookList.registDate}"/></span></td>
						</tr>
					</c:forEach>
				</c:otherwise>	
			</c:choose>																																							
		</tbody>
	</table>							
	<div class="l_b_corner"></div><div class="r_b_corner"></div>
</div>