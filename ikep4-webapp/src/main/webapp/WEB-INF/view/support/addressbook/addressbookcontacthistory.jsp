<%@ include file="/base/common/taglibs.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%-- 메시지 관련 Prefix 선언 Start --%> 
<c:set var="preHeader"  value="ui.support.addressbook.header" /> 
<c:set var="prePrivate"    value="ui.support.addressbook.addrgroup.private" />
<c:set var="prePublic"  value="ui.support.addressbook.addrgroup.public" />

<c:set var="preContactHist"  value="ui.support.addressbook.ContactHistory" />
<c:set var="preButton"  value="ui.support.addressbook.ContactHistory.button" />
<c:set var="preMessage"  value="message.support.addressbook.ContactHistory" />

<c:set var="preSumMessage"  value="message.support.addressbook.summary" />
<%-- 메시지 관련 Prefix 선언 End --%>

<script type="text/javascript" src="<c:url value="/base/js/units/support/addressbook/addressbook.js"/>"></script>
<script type="text/javascript">
<!--
(function($) {
		
	$jq(document).ready(function() {
		
		// 화면 로딩시 각각 페이지 호출 시작
		Addressbook.getLeftMenuView();
		Addressbook.getPrivateAddrgroupView('');
		Addressbook.getPublicAddrgroupView('');
		// 화면 로딩시 각각 페이지 호출 종료
		
		var firstDraw =  true;
		Addressbook.getContactHistroyList('${userId}','post','true');
		
		// 스크롤이 맨 하단에 위치했을 때 해당하는 탭의 다음 내용을 자동조회한다.
		$(window).scroll(function(){
			if  ($(window).scrollTop() == $(document).height() - $(window).height())
			{
				if(window.scrollFlag) return;
				window.scrollFlag = true; 
				
				//iKEP.debug("firstDraw2:"+firstDraw);
				if(!firstDraw){
					Addressbook.getContactHistroyList('${userId}','pre','false');
				}
				firstDraw =  false;
				window.scrollFlag = false; 
			}
		});
	});
	
})(jQuery);  
//-->
</script>

		<!--blockMain Start-->
		<div id="blockMain">

			<!--leftMenu Start-->
			<div id="leftMenu">
			</div>	
			<!--//leftMenu End-->
				
			<!--mainContents Start-->
			<div id="mainContents" >
						
				<h1 class="none"><ikep4j:message pre='${preHeader}' key='contents'/></h1>
				
				<!--pageTitle Start-->
				<div id="pageTitle">
				<h2><c:out value="${addrgroupName}"/></h2>

					<div id="pageLocation">
						<c:if test="${listNavi != null}">
						<ul>
							<c:forEach var="navi" items="${listNavi}" varStatus="status">
								<c:if test="${(status.index == 0) }">
								<li class="liFirst"><c:out value="${navi.naviTitle}"/></li>
								</c:if>
								<c:if test="${(status.index > 0) && (status.index+1 < listNaviSize) }">
								<li><c:out value="${navi.naviTitle}"/></li>
								</c:if>
								<c:if test="${(status.index > 0) && (status.index+1 == listNaviSize) }">
								<li class="liLast"><c:out value="${navi.naviTitle}"/></li>
								</c:if>
							</c:forEach>
						</ul>
						</c:if>
					</div>
				</div>
				<!--//pageTitle End-->
	
				<div id="listbody">
				<!--blockListTable Start-->
				<div class="blockListTable">
					<table summary="<ikep4j:message pre='${preSumMessage}' key='addrManageTable'/>">
						<caption></caption>
						<thead>
							<tr>
								<th width="20%" class="textCenter" scope="col"><ikep4j:message pre='${preContactHist}' key='targetUser'/></th>
								<th width="40%" class="textCenter" scope="col"><ikep4j:message pre='${preContactHist}' key='contactType'/></th>
								<th width="25%" class="textCenter" scope="col"><ikep4j:message pre='${preContactHist}' key='contactDate'/></th>
								<th width="15%" class="textCenter" scope="col"><ikep4j:message pre='${preContactHist}' key='contactAction'/></th>
							</tr>
						</thead>
						<tbody id="contacthist">
						</tbody>

					</table>		
				
				</div>
				<!--//blockListTable End-->	
										
				<!--blockButton Start-->
				<div class="blockButton_3"> 
					<a href="#a" onclick="Addressbook.getContactHistroyList('${userId}','pre','false');" class="button_3"><span><ikep4j:message pre='${preButton}' arguments="10" key='more'  /><img alt="" src="<c:url value='/base/images/icon/ic_more_ar.gif' />"></span></a>
				</div>
				<!--//blockButton End-->
				</div>
				
			</div>
			<!--//mainContents End-->
			<div class="clear"></div>
		</div>
		<!--//blockMain End-->
				