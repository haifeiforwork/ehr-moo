<%@ page contentType="text/html; charset=utf-8"%>
<%@ include file="/base/common/taglibs.jsp"%>

<c:set var="preTitle" value="ui.portal.portlet.wiseSaying.normalView.title"/>

<c:set var="user" value="${sessionScope['ikep.user']}" />
<c:set var="portal" value="${sessionScope['ikep.portal']}" />

<c:choose>
	<c:when test="${user.localeCode == portal.defaultLocaleCode}">
		<c:set var="wiseSayingContents" value="${portletWiseSaying.wiseSayingContents}"/>
		<c:set var="writerName" value="${portletWiseSaying.writerName}"/>
	</c:when>
	<c:otherwise>
		<c:set var="wiseSayingContents" value="${portletWiseSaying.wiseSayingEnglishContents}"/>
		<c:set var="writerName" value="${portletWiseSaying.writerEnglishName}"/>
	</c:otherwise>
</c:choose>

<h2 class="none"><ikep4j:message pre="${preTitle}" key="pageTitle" /></h2> 
<div class="mtitle"><img src="<c:url value='/base/images/common/mtitle_wisesaying.gif'/>" title="<ikep4j:message pre='${preTitle}' key='pageTitle' />" /></div>
<table summary="">
	<caption></caption>
	<tbody>
		<tr>
			<td width="110">
				<!--이미지 최적화 사이즈는 110x115-->
				<img width="110px" height="115px"
					<c:choose>
						<c:when test="${!empty portletWiseSaying.imageFileId}">
							<c:choose>
								<c:when test="${portletWiseSaying.imageFileId == 1}">
									src="<c:url value='/base/images/common/img_wisesaying.gif'/>"
								</c:when>
								<c:when test="${portletWiseSaying.imageFileId == 2}">
									src="<c:url value='/base/images/common/img_wisesaying2.gif'/>"
								</c:when>
								<c:when test="${portletWiseSaying.imageFileId == 3}">
									src="<c:url value='/base/images/common/img_wisesaying3.gif'/>"
								</c:when>
								<c:when test="${portletWiseSaying.imageFileId == 4}">
									src="<c:url value='/base/images/common/img_wisesaying4.gif'/>"
								</c:when>
								<c:otherwise>
									src="<c:url value='/base/images/common/img_wisesaying5.gif'/>"
								</c:otherwise>
							</c:choose>
						</c:when>
						<c:otherwise>
							src="<c:url value='/base/images/common/img_wisesaying.gif'/>"
						</c:otherwise>
					</c:choose>
					alt="${wiseSayingContents} - ${writerName}" />
			</td>
			<td width="*">
				<c:choose>
				<c:when test="${user.localeCode == portal.defaultLocaleCode}">
				<p class="mainTop_imgtxt_01">${wiseSayingContents}</p>
				<p class="mainTop_imgtxt_03"><i>${writerName}</i></p>
				</c:when>
				<c:otherwise>
				<p class="mainTop_imgtxt_02">${wiseSayingContents}</p>
				<p class="mainTop_imgtxt_04"><i>${writerName}</i></p>
				</c:otherwise>
				</c:choose>				
			</td>
		</tr>
	</tbody>
</table>