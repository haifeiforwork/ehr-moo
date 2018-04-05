<%@ page language="java" errorPage="/base/common/error.jsp"  pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ include file="/base/common/taglibs.jsp"%>
<%@ taglib uri="/WEB-INF/tld/ikep4j.tld" prefix="ikep4j" %> 
<c:set var="sessionPortal" value="${sessionScope['ikep.portal']}" />
<c:set var="sessionUser" value="${sessionScope['ikep.user']}" />

<%-- 메시지 관련 Prefix 선언 Start --%> 
<c:set var="preTap"    	value="ui.socialpack.microblogging.tap" />
<c:set var="preButton"  value="ui.socialpack.microblogging.button" />
<c:set var="preLink"  	value="ui.socialpack.microblogging.link" /> 
<c:set var="preLabel" 	value="ui.socialpack.microblogging.label" />
<c:set var="preMessage"	value="ui.socialpack.microblogging.message" /> 
<%-- 메시지 관련 Prefix 선언 End --%>

	
	<!--popup_contents Start-->
	<div id="popup_contents">
		
		<!--blockDetail Start-->
		<div class="blockDetail">
			<table class="blockDetail" style="height:100px;">
				<tbody>
					<tr>
						<td colspan="3">
							<div style="padding-bottom:4px;">
								<p><c:out value="${mblog.contents}" escapeXml="false"/></p>
							</div>									
						</td>
					</tr>			
				</tbody>
			</table>
		</div>
		<!--//blockDetail End-->
	</div>
	<!--//popup_contents End-->
