<%@ include file="/base/common/taglibs.jsp"%>
<%@ page language="java" errorPage="/common/error.jsp"  pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ taglib uri="/WEB-INF/tld/ikep4j.tld" prefix="ikep4j" %> 

<%-- 메시지 관련 Prefix 선언 Start --%> 
<c:set var="preProfileMain"  value="ui.support.profile.main" /> 
<c:set var="preGuestbook"  value="ui.support.guestbook.view.main" />

<c:set var="preButton"  value="ui.support.profile.common.button" />
<c:set var="preMsgProfile"  value="message.support.profile.main" />
<c:set var="preMsgGuestbook"  value="message.support.guestbook.view.main" />
<%-- 메시지 관련 Prefix 선언 End --%>

<script type="text/javascript" src="<c:url value="/base/js/units/support/favorite/favorite.js"/>"></script>

<input type="hidden" id="bbsHidden" name="moduleCodeList"  value="BBS" />
<input type="hidden" id="collaborationHidden" name="moduleCodeList"  value="Workspace" />
<input type="hidden" id="blogHidden" name="moduleCodeList"  value="Blog" />
<input type="hidden" id="cafeHidden" name="moduleCodeList"  value="Cafe" />
<input type="hidden" id="manualHidden" name="moduleCodeList"  value="Manual" />
<input type="hidden" id="qnaHidden" name="moduleCodeList"  value="QA" />
<input type="hidden" id="forumHidden" name="moduleCodeList"  value="Forum" />
<input type="hidden" id="ideaHidden" name="moduleCodeList"  value="Idea" />	

<div class="pr_documents">
	<h3><ikep4j:message pre='${preProfileMain}' key='documents.title'/></h3>
	<div class="more"><a onclick="goMydocument()" href="#a"><img src="<c:url value='/base/images/common/btn_more.gif' />" alt="more" /></a></div>
	<c:choose>
		<c:when test="${empty searchResult.entity}">
			<p align="center"><ikep4j:message pre='${preMsgProfile}' key='nodata'/></p>
		</c:when>
		<c:otherwise>
			<c:forEach var="favorite" items="${searchResult.entity}" varStatus="status">
			<c:if test="${status.index <= 5 }" >
			<div class="pr_documentsList ellipsis">
				<span class="cate_block_1"><span class="cate_tit_1">${favorite.module}</span></span>&nbsp;<a onclick="showDatailForProfile('${favorite.targetUrl}','${favorite.targetId}','${favorite.subId}','${ikep4j:replaceQuot(favorite.title)}')" href="#a">${favorite.title}</a><c:if test="${favorite.linereplyCount < 0 }" ><span class="colorPoint">(${favorite.linereplyCount})</span></c:if><span class="date"><ikep4j:timezone pattern="yyyy.MM.dd HH:mm:ss" date="${favorite.registDate}"/></span>
			</div>
			</c:if>
			</c:forEach>
		</c:otherwise>
	</c:choose>		
</div>