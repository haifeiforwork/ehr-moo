

<%@ include file="/base/common/taglibs.jsp"%>
<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="preSub"    value="ui.support.poll.sub" />
<c:set var="preList"   value="ui.support.poll.list" />
<script type="text/javascript">
      $jq(document).ready(function () {
          // Left Menu setting
          iKEP.setLeftMenu();    
      });
</script>

<c:set var="requestUrl" value="${requestScope['javax.servlet.forward.request_uri']}"/>

				<h1 class="none">Left Menu</h1>		
				<h2><a href="<c:url value='/support/poll/pollList.do'/>" style="text-decoration:none;"><span><img style="padding-top:6px" src="<c:url value='/base/images/title/lmt_comm_poll.gif'/>"/></span></a></h2>
				<!--blockButton_2 Start-->
				<div class="blockButton_2"> 				
					<a class="button_2" href="<c:url value='/support/poll/formPoll.do'/>"><span><img src="<c:url value='/base/images/icon/ic_poll.gif' />" width="16" height="16" alt=""/> <ikep4j:message pre='${preSub}' key='addPoll' /></span></a>				
				</div>
				<!--//blockButton_2 End-->					

				<div class="left_fixed">
					<ul>			
						<li class="liFirst no_child <c:if test="${fn:contains(requestUrl,'pollList.do') && param.isComplete == 1}"> licurrent</c:if>"><a href="<c:url value='/support/poll/pollList.do?isComplete=1'/>"><ikep4j:message pre='${preSub}' key='applyPoll' /></a></li>
						<li class="no_child <c:if test="${fn:contains(requestUrl,'pollList.do') && param.isComplete == 0}"> licurrent</c:if>"><a href="<c:url value='/support/poll/pollList.do?isComplete=0'/>"><ikep4j:message pre='${preList}' key='myPoll' /></a></li>
						<c:if test="${isAdmin == true}">		
						<li class="opened"><a href="#a">Administration</a>
							<ul>
								<li class="no_child liLast <c:if test="${fn:contains(requestUrl,'pollAdminList.do')}"> licurrent</c:if>"><a href="<c:url value='/support/poll/pollAdminList.do'/>"><ikep4j:message pre='${preSub}' key='adminPoll' /></a></li>
							</ul>		
						</li>							
						</c:if>
					</ul>
				</div>				