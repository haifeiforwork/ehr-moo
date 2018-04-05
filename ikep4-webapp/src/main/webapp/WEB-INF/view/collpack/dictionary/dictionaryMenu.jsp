<%@ include file="/base/common/taglibs.jsp"%>
<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="preSub"    value="ui.collpack.dictionary.dictionaryMenu" />
<c:set var="preMessage"    value="message.collpack.dictionary" />

<script type="text/javascript">
      $jq(document).ready(function () {
          // Left Menu setting
    	  iKEP.setLeftMenu(); 
      });
</script>
<c:set var="user" value="${sessionScope['ikep.user']}" />
<c:set var="requestUrl" value="${requestScope['javax.servlet.forward.request_uri']}"/>

				<h1 class="none">left menu</h1>
				<h2><a href="main.do" style="text-decoration:none;">Corporate Vocabulary</a></h2>
				<div class="blockButton_2"> 
					<a class="button_2" href="<c:url value='/collpack/dictionary/formDictionary.do?viewDictionaryId=${viewDictionaryId }'/>"><span><img src="<c:url value='/base/images/icon/ic_corporate.png' />" alt="" />용어등록</span></a>				
				</div>
				<div class="left_fixed">
					<div class="boxList">
						<p><ikep4j:message pre='${preSub}' key='menuMyInput' /></p>
						<c:if test="${myInputList.recordCount != 0}">
						<a href="<c:url value='/collpack/dictionary/getMyDictionarys.do?mode=myInputList&amp;dictionaryId=&amp;registerId=${user.userId}&amp;isMore=Y'/>" class="more"><img src="<c:url value='/base/images/common/btn_more.gif' />" alt="more" /></a>
						</c:if>					
					</div>
					<c:if test="${fn:length(myInputList.entity) == 0}">
						<div style="margin-top:5px;margin-left:15px;"><ikep4j:message pre='${preMessage}' key='noMyInputResult' /></div>				
					</c:if>						
					<div class="boxList_sub">
						<ul>
							<c:forEach var="myInput" items="${myInputList.entity}" varStatus="loopCount">
								<c:if test="${loopCount.count < 6}">
								<li><a href="getDictionary.do?wordId=${myInput.wordId}" title="[${myInput.dictionaryName}] ${myInput.wordName }">${ikep4j:cutString(myInput.wordName,22,"..")}</a>
									<c:if test="${myInput.isNew <= 7}">
									<img src="<c:url value='/base/images/theme/theme01/basic/ic_new.gif' />" alt="new" />
									</c:if>
								</li>
								</c:if>
							</c:forEach>
						</ul>
					</div>		
					
					<div class="boxList">
						<p><ikep4j:message pre='${preSub}' key='menuMyView' /></p>
						<c:if test="${myViewList.recordCount != 0}">
						<a href="<c:url value='/collpack/dictionary/getMyDictionarys.do?mode=myViewList&amp;dictionaryId=&amp;viewId=${user.userId}&amp;isMore=Y'/>" class="more"><img src="<c:url value='/base/images/common/btn_more.gif' />" alt="more" /></a>					
						</c:if>
					</div>
					<c:if test="${fn:length(myViewList.entity) == 0}">
						<div style="margin-top:5px;margin-left:15px;"><ikep4j:message pre='${preMessage}' key='noMyViewResult' /></div>				
					</c:if>						
					<div class="boxList_sub">
						<ul>
							<c:forEach var="myView" items="${myViewList.entity}" varStatus="loopCount">
								<c:if test="${loopCount.count < 6}">
								<li><a href="getDictionary.do?wordId=${myView.wordId}" title="[${myView.dictionaryName }] ${myView.wordName }">${ikep4j:cutString(myView.wordName,22,"..")}</a>
									<c:if test="${myView.isNew <= 7}">
									<img src="<c:url value='/base/images/theme/theme01/basic/ic_new.gif' />" alt="new" />
									</c:if>
								</li>
								</c:if>
							</c:forEach>						
						</ul>
					</div>									
					<c:if test="${isAdmin == true}">
					<ul>			
						<li class="opened"><a href="#a">Administrator</a>
							<ul>
								<li class="no_child liLast <c:if test="${fn:contains(requestUrl,'dictionaryAdminList.do')}"> licurrent</c:if>"><a href="<c:url value='/collpack/dictionary/dictionaryAdminList.do'/>"><ikep4j:message pre='${preSub}' key='menuAdmin' /></a></li>
							</ul>
						</li>					
					</ul>
					</c:if>
				</div>