<%@ include file="/base/common/taglibs.jsp"%>
<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="user" value="${sessionScope['ikep.user']}" />
<c:set var="portal" value="${sessionScope['ikep.portal']}" />

<c:set var="preMessage"    value="message.collpack.who" />
<c:set var="preMenu"    value="ui.collpack.who.whoMenu" />
<script type="text/javascript">
      $jq(document).ready(function () {
          // Left Menu setting
          iKEP.setLeftCollaborationMenu();    
      });
</script>
<c:set var="user" value="${sessionScope['ikep.user']}" />

				<h1 class="none">left menu</h1>
				<h2><a href="main.do" style="text-decoration:none;"><ikep4j:message pre='${preMenu}' key='menuTitle' /></a></h2>
				<div class="blockButton_2"> 
					<a class="button_2" href="<c:url value='/collpack/who/formWho.do'/>"><span><img src="<c:url value='/base/images/icon/ic_dic.png' />" alt="" /><ikep4j:message pre='${preMenu}' key='menuInput' /></span></a>				
				</div>
				<div class="left_fixed">
					<div class="boxList">
						<p><ikep4j:message pre='${preMenu}' key='menuMyInput' /></p>
						<c:if test="${myInputList.recordCount != 0}">
						<a href="<c:url value='/collpack/who/main.do?mode=myInputList&amp;registerId=${user.userId}&amp;isMore=Y'/>" class="more"><img src="<c:url value='/base/images/common/btn_more.gif' />" alt="more" /></a>
						</c:if>					
					</div>
					<c:if test="${fn:length(myInputList.entity) == 0}">
						<div style="margin-top:5px;margin-left:15px;"><ikep4j:message pre='${preMessage}' key='noMyInputResult' /></div>				
					</c:if>						
					<div class="boxList_sub">
						<ul>
							<c:forEach var="myInput" items="${myInputList.entity}" varStatus="loopCount">
								<c:if test="${loopCount.count < 6}">
								    <c:set var="myInputInfo" value="${myInput.name} ${myInput.jobRankName}"/>								
									<li><a href="getWho.do?profileId=${myInput.profileId}" title="${myInput.name} ${myInput.jobRankName} ${myInput.companyName}">${myInputInfo}</a>
										<c:if test="${myInput.isNew <= 7}">
										<img src="<c:url value='/base/images/theme/theme01/basic/ic_new.gif' />" alt="new" />
										</c:if>
									</li>
								</c:if>
							</c:forEach>
						</ul>
					</div>		
					
					<div class="boxList">
						<p><ikep4j:message pre='${preMenu}' key='menuMyView' /></p>
						<c:if test="${myViewList.recordCount != 0}">
						<a href="<c:url value='/collpack/who/main.do?mode=myViewList&amp;viewId=${user.userId}&amp;isMore=Y'/>" class="more"><img src="<c:url value='/base/images/common/btn_more.gif' />" alt="more" /></a>					
						</c:if>
					</div>
					<c:if test="${fn:length(myViewList.entity) == 0}">
						<div style="margin-top:5px;margin-left:15px;"><ikep4j:message pre='${preMessage}' key='noMyViewResult' /></div>				
					</c:if>						
					<div class="boxList_sub">
						<ul>
							<c:forEach var="myView" items="${myViewList.entity}" varStatus="loopCount">
								<c:if test="${loopCount.count < 6}">
									<c:set var="myViewInfo" value="${myView.name} ${myView.jobRankName}"/>
									<li><a href="getWho.do?profileId=${myView.profileId}" title="${myView.name} ${myView.jobRankName} ${myView.companyName}">${myViewInfo}</a>
										<c:if test="${myView.isNew <= 7}">
										<img src="<c:url value='/base/images/theme/theme01/basic/ic_new.gif' />" alt="new" />
										</c:if>${myInput.companyName}
									</li>
								</c:if>
							</c:forEach>						
						</ul>
					</div>									
				</div>