<%@ include file="/base/common/taglibs.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%-- 메시지 관련 Prefix 선언 Start --%> 
<c:set var="preHeader"  value="ui.support.addressbook.header" /> 
<c:set var="prePrivate"    value="ui.support.addressbook.addrgroup.private" />
<c:set var="prePublic"  value="ui.support.addressbook.addrgroup.public" />

<%-- 메시지 관련 Prefix 선언 End --%>

<c:set var="user" value="${sessionScope['ikep.user']}" />
<c:set var="portal" value="${sessionScope['ikep.portal']}" />

<script type="text/javascript" src="<c:url value="/base/js/units/support/addressbook/addressbook.js"/>"></script>
<script type="text/javascript">
<!--
(function($) {
	
	$jq(document).ready(function() {
		$jq(".clickTitle").click(function() {
			document.location.href = "<c:url value='/support/addressbook/addressbookHome.do'/>" ;
		});
	});

	
})(jQuery);  
//-->
</script>
				
				<!--leftMenu Start-->
				<!--
				<h1 class="none"><ikep4j:message pre='${preHeader}' key='leftmenu'/></h1>
				<h2 class="clickTitle" ><ikep4j:message pre='${preHeader}' key='english.title'/></h2>
				<div class="blockButton_2 mb10"> 
					<a class="button_2" onclick="Addressbook.editPerson('','P','');" href="#a"><span><img src="<c:url value='/base/images/icon/ic_addressbook.gif'/>" alt="" /><ikep4j:message pre='${prePrivate}' key='newPerson.title'/></span></a>				
				</div>		
				<div class="addr_setting">
					<a href="#a" title="setting" onclick="Addressbook.getAddrgroupList('P')"><ikep4j:message pre='${prePrivate}' key='setting.title'/></a> 
				</div>		
				<div class="left_fixed">
					<ul>
						<li class="liFirst opened licurrent"><a href="#a"><c:choose><c:when test="${user.localeCode == portal.defaultLocaleCode}">${user.userName}</c:when><c:otherwise>${user.userEnglishName}</c:otherwise></c:choose><ikep4j:message pre='${prePrivate}' key='title'/></a>
							<ul class="boxList_child private_group">
								<li/>
							</ul>

						</li>					
						<li class="opened"><a href="#a"><ikep4j:message pre='${prePublic}' key='title'/></a>
							<ul class="boxList_child public_group">
								<li/>
							</ul>				
						</li>
						
						<c:if test="${publicManageFlag == 'true'}">
						<li class="opened"><a href="#a"><ikep4j:message pre='${prePublic}' key='administrator.title'/></a>
							<ul>
								<li class="no_child liLast"><a href="#a" onclick="Addressbook.getAddrgroupList('O');"><ikep4j:message pre='${prePublic}' key='management.title'/></a></li>
							</ul>					
						</li>
						</c:if>											
					</ul>
					<div class="planner_leftbtn">
						<span class="btn_planner_import"><a href="#a" onclick="Addressbook.getAddrbookImport();"><ikep4j:message pre='${prePrivate}' key='import.title'/></a></span>
						<span class="btn_planner_export"><a href="#a" onclick="Addressbook.getAddrbookExport();"><ikep4j:message pre='${prePrivate}' key='export.title'/></a></span>
					</div>									
				</div>
				-->								<!-- <li class="no_child"><a href="#a" onclick="Addressbook.getSocialConnection('<ikep4j:message pre='${prePrivate}' key='socialconnection.title'/>','<ikep4j:message pre='${preHeader}' key='english.title'/>|<ikep4j:message pre='${prePrivate}' key='socialconnection.title'/>');"><ikep4j:message pre='${prePrivate}' key='socialconnection.title'/></a></li> -->
								<!-- <li class="no_child"><a href="#a" onclick="Addressbook.getContactHistroy('<ikep4j:message pre='${prePrivate}' key='ContactHistory.title'/>','<ikep4j:message pre='${preHeader}' key='english.title'/>|<ikep4j:message pre='${prePrivate}' key='ContactHistory.title'/>');" ><ikep4j:message pre='${prePrivate}' key='ContactHistory.title'/></a></li> -->

