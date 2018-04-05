<%@ page language="java" errorPage="/base/common/error.jsp"  pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ include file="/base/common/taglibs.jsp"%>
<%@ taglib uri="/WEB-INF/tld/ikep4j.tld" prefix="ikep4j" %>

<%-- 메시지 관련 Prefix 선언 Start --%> 
<c:set var="portletList" value="ui.socialpack.socialblog.portlet.list"/>
<c:set var="preManageStat" value="ui.socialpack.socialblog.management.statistics" />

<c:set var="preBlogCommon"  value="ui.socialpack.socialblog.common.webstandard" /> 
<c:set var="preMsgBlogVisit"  value="message.socialpack.socialblog.common.socialBlogVisit" />
<%-- 메시지 관련 Prefix 선언 End --%>

<% 
	pageContext.setAttribute("lf", "\n"); 
%>

<script type="text/javascript">
<!--

(function($) {
	
	$jq(document).ready(function() {
		
		$jq("#save_${portletConfigId}").click(function() {
			var reloadUrl = '<c:url value="/socialpack/socialblog/portlet/recentVisitor/normalView.do"/>?portletId=${portletId}';
			var listSize = $jq("#listCount_${portletConfigId}").val();
			//portletConfigId, 프로퍼티네임(최대 10Byte), 프로퍼티 값(최대 50Byte),설정 후 이동할 url : 없으면 이동하지 않음.
			PortletSimple.setListCount('${portletConfigId}', listSize, reloadUrl, '${portletId}');
		}); 
		 
	});
	
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
						<a id="save_${portletConfigId}" class="button" href="#a"><span><ikep4j:message pre='${portletList}' key='button.create' /></span></a>
					</div>
				</div>
				
				<div class="poEdit">
					<div>
						<ikep4j:message pre='${preManageStat}' key='visitor.totalVisitCount' /> : <c:out value='${totalVisitCount}'/> <ikep4j:message pre='${preManageStat}' key='visitor.personCount' />
					</div>
				</div>
				
				<div id="${portletConfigId}" class="tableList_1">
				<table summary="<ikep4j:message pre='${preBlogCommon}' key='content'/>">
					<caption></caption>
					<tbody>
						<c:choose>
						    <c:when test="${resultSocialBlogVisit == null}">
								<tr>
									<td width="100%" class="emptyRecord"><ikep4j:message pre='${preMsgBlogVisit}' key='emptyRecord'/></td> 
								</tr>				        
						    </c:when>
						    <c:otherwise>
								<c:forEach var="socialBlogVisit" items="${resultSocialBlogVisit}">
									<tr>
										<td class="textLeft" width="*">
											<div class="ellipsis"><a href="#a" onclick="iKEP.showUserContextMenu(this, '${recentCommentList.registerId}', 'bottom')"><c:choose><c:when test="${user.localeCode == portal.defaultLocaleCode}"><c:out value="${socialBlogVisit.userName}"/></c:when><c:otherwise><c:out value="${socialBlogVisit.userEnglishName}"/></c:otherwise></c:choose> <c:choose><c:when test="${user.localeCode == portal.defaultLocaleCode}">${socialBlogVisit.visitorJobTitleName}</c:when><c:otherwise>${socialBlogVisit.visitorJobTitleEngName}</c:otherwise></c:choose></a></div></a>
										</td>	
										<td class="textCenter" width="55"><div class="ellipsis"><c:choose><c:when test="${user.localeCode == portal.defaultLocaleCode}">${socialBlogVisit.visitorTeamName}</c:when><c:otherwise>${socialBlogVisit.visitorTeanEngName}</c:otherwise></c:choose></div></td>
										<td class="textRight" width="30"><span class="date"><ikep4j:timezone pattern="MM.dd" date="${socialBlogVisit.visitDate}"/></span></td>
									</tr>
								</c:forEach>
							</c:otherwise>	
						</c:choose>																																							
					</tbody>
				</table>
				<div class="l_b_corner"></div><div class="r_b_corner"></div>
				</div>
	</div>