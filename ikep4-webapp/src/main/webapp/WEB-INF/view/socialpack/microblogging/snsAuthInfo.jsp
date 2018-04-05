
<%@ include file="/base/common/taglibs.jsp"%>

<c:set var="sessionUser" value="${sessionScope['ikep.user']}" />

<input type="hidden" id="twitterAccount" 	value = "${sessionUser.twitterAccount}">
<input type="hidden" id="twitterAuthCode" 	value = "${sessionUser.twitterAuthCode}">
<input type="hidden" id="facebookAuthCode" 	value = "${sessionUser.facebookAuthCode}">