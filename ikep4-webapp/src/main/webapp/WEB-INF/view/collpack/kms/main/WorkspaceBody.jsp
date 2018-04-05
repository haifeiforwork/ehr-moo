<%@ page language="java" errorPage="/base/common/error.jsp"	pageEncoding="UTF-8" contentType="text/html;charset=utf-8"%>

<%@ include file="/base/common/taglibs.jsp"%>

<%-- 메시지 관련 Prefix 선언 Start --%> 
<c:set var="prefix"  value="message.collpack.collaboration.workspace.main" />
<%-- 메시지 관련 Prefix 선언 End --%>

<%-- 세션정보 선언 Start --%> 
<c:set var="portal" value="${sessionScope['ikep.portal']}" />
<c:set var="user" value="${sessionScope['ikep.user']}" />
<%-- 세션정보 선언 End --%>


<script type="text/javascript" src="<c:url value="/base/js/portlet.js"/>"></script>
<script type="text/javascript"> 
(function($) {
	$jq(document).ready(function() {
		
		/***var mainMenu = $jq("#topMenu");
		var menuItems = mainMenu.children("ul").children("li");
		var width = (menuItems.length * $jq(menuItems[0]).outerWidth());
		mainMenu.css({width:width + "px"});
		
		menuItems.bind("mouseenter mouseleave", function(event) {
			var el = event.target;
			switch(el.tagName.toLowerCase()) {
				case "ul" :
				case "a" : el = $jq(el).parent(); break;
				case "span" : el = $jq(el).parent().parent(); break;
				default : el = $jq(el);
			}
			el.children("ul").toggle();
		});
		
		$jq.each(menuItems.children("ul"), function() {
			var subMenu = $jq(this);
			subMenu.css({left:((subMenu.parent().outerWidth() - subMenu.outerWidth()) / 2 + 1)+"px"});
		});**/
		
		
		// 포틀릿 좌측 배치
		getPortletLeftView();
		getPortletRightView();	
		//iKEP.setMainMenu();
		iKEP.iFrameContentResize(); 
	});
	
	getPortletLeftView = function() {
		<c:forEach var="layoutList" items="${workspacePortletLayoutList}"  varStatus="status">
		<c:if test="${layoutList.colIndex==1}">
			
			<c:if test="${!empty layoutList.boardId}">
			var url ="<c:url value="${layoutList.workspacePortlet.viewUrl}"/>?workspaceId=${workspace.workspaceId}&boardId=${layoutList.boardId}&portletLayoutId=${layoutList.portletLayoutId}";
			</c:if>
			<c:if test="${empty layoutList.boardId}">
			var url ="<c:url value="${layoutList.workspacePortlet.viewUrl}"/>?workspaceId=${workspace.workspaceId}&portletLayoutId=${layoutList.portletLayoutId}";
			</c:if>		
			$jq("<div/>").load(url)
	    	.error(function(event, request, settings) { alert("error"); })
	    	.appendTo("#portlet_left<c:out value='${layoutList.rowIndex}'/>");

		</c:if>
		</c:forEach>
	};
	getPortletRightView = function() {
		
		<c:forEach var="layoutList" items="${workspacePortletLayoutList}"  varStatus="status">
		<c:if test="${layoutList.colIndex==2}">
			<c:if test="${!empty layoutList.boardId}">
			var url="<c:url value="${layoutList.workspacePortlet.viewUrl}"/>?workspaceId=${workspace.workspaceId}&boardId=${layoutList.boardId}&portletLayoutId=${layoutList.portletLayoutId}";
			</c:if>
			<c:if test="${empty layoutList.boardId}">
			var url="<c:url value="${layoutList.workspacePortlet.viewUrl}"/>?workspaceId=${workspace.workspaceId}&portletLayoutId=${layoutList.portletLayoutId}";
			</c:if>
			$jq("<div/>").load(url)
    		.error(function(event, request, settings) { alert("error"); })
    		.appendTo("#portlet_right_<c:out value='${layoutList.rowIndex}'/>");
			
		</c:if>
		</c:forEach>	
	};
})(jQuery);
</script>

<h1 class="none"><ikep4j:message pre="${prefix}" key="contents.pageTitle" /></h1>

<!--pageTitle Start-->
<div id="pageTitle" class="TeamColl" style="border:1px solid:#000000">
	<div class="TeamColl_member">
		<div class="member_1"><ikep4j:message pre="${prefix}" key="contents.sysopName" /> : <a href="#a" onclick="iKEP.showUserContextMenu(this, '${workspace.sysopId}', 'bottom');">
			<c:choose>
			<c:when test="${user.localeCode == portal.defaultLocaleCode}">
				${workspace.sysopName} ${workspace.jobTitleName}
			</c:when>
			<c:otherwise>
				${workspace.sysopEnglishName} ${workspace.jobTitleEnglishName}
			</c:otherwise>
			</c:choose>		
		</a></div>
		<div class="member_2"><ikep4j:message pre="${prefix}" key="contents.memberCount" /> : <span class="colorPoint">${workspace.memberCount}</span>
		<span class="member_num" style="font-weight:thin">(<ikep4j:message pre="${prefix}" key="contents.member" /> : ${workspace.memberCount-workspace.associateMemberCount}, <ikep4j:message pre="${prefix}" key="contents.associate" /> : ${workspace.associateMemberCount})</span></div>
	</div>
	
	
</div>
<!--//pageTitle End-->
<div class="clear"></div>
<!--TeamColl_img Start-->
<div class="TeamColl_img">
	<c:if test="${empty workspace.introduction}">
	<div class="TeamColl_img_info">
		<div>
			<img src="<c:url value='/base/images/common/title_teamcollaboration.png'/>" alt="Team Collaboration" />
		</div>
		<p>${workspace.typeDescription}</p>
	</div>
	<div class="TeamColl_img_ex">
		<img src="<c:url value='/base/images/common/img_teamcoll.gif'/>" alt="" />
	</div>
	</c:if>
	
	<c:if test="${not empty workspace.introduction}">
	<div>
	    <spring:htmlEscape defaultHtmlEscape="false"> 
		<spring:bind path="workspace.introduction">
			<p>${status.value}</p>
		</spring:bind> 
        </spring:htmlEscape>
	</div>
	</c:if>	
</div>
<!--//TeamColl_img End-->



<!--blockLeft Start-->
<div id="blockLeft" class="blockLeft">	
	<c:forEach var="layoutList" items="${workspacePortletLayoutList}"  varStatus="status">
	<c:if test="${layoutList.colIndex==1}">	
		<div id="portlet_left<c:out value='${layoutList.rowIndex}'/>"></div>
	</c:if>
	</c:forEach>	
</div>
<!--//blockLeft End-->

<!--blockRight Start-->
<div id="blockRight" class="blockRight">
	<c:forEach var="layoutList" items="${workspacePortletLayoutList}"  varStatus="status">
	<c:if test="${layoutList.colIndex==2}">	
		<div id="portlet_right_<c:out value='${layoutList.rowIndex}'/>"></div>
	</c:if>
	</c:forEach>
</div>
<!--//blockRight End-->

