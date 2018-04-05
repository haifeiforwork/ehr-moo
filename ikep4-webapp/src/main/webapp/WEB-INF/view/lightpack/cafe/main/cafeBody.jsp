<%@ page language="java" errorPage="/base/common/error.jsp"	pageEncoding="UTF-8" contentType="text/html;charset=utf-8"%>

<%@ include file="/base/common/taglibs.jsp"%>

<%-- 메시지 관련 Prefix 선언 Start --%> 
<c:set var="prefix"  value="message.lightpack.cafe.cafe.main" />
<%-- 메시지 관련 Prefix 선언 End --%>

<%-- 세션정보 선언 Start --%> 
<c:set var="portal" value="${sessionScope['ikep.portal']}" />
<c:set var="user" value="${sessionScope['ikep.user']}" />
<%-- 세션정보 선언 End --%>


<script type="text/javascript" src="<c:url value="/base/js/portlet.js"/>"></script>
<script type="text/javascript"> 
<!-- 

(function($) {
	$jq(document).ready(function() {
		
		var mainMenu = $jq("#topMenu");
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
		});
		
	});
	
	getPortletLeftView = function() {
		<c:forEach var="layoutList" items="${cafePortletLayoutList}"  varStatus="status">
		<c:if test="${layoutList.colIndex==1}">
			
			<c:if test="${!empty layoutList.boardId}">
			var url ="<c:url value="${layoutList.cafePortlet.viewUrl}"/>?cafeId=${cafe.cafeId}&boardId=${layoutList.boardId}&portletLayoutId=${layoutList.portletLayoutId}";
			</c:if>
			<c:if test="${empty layoutList.boardId}">
			var url ="<c:url value="${layoutList.cafePortlet.viewUrl}"/>?cafeId=${cafe.cafeId}&portletLayoutId=${layoutList.portletLayoutId}";
			</c:if>			

			$jq.ajax({     
				url : url,     
				data :  "",     
				type : "get",     
				loadingElement : {container:"#portlet_left_<c:out value='${layoutList.rowIndex}'/>"},
				success : function(result) {  
					$jq("#portlet_left_<c:out value='${layoutList.rowIndex}'/>").append(result);
				},
				error : function(event, request, settings) { alert("error"); }
			});  
		
		</c:if>
		</c:forEach>
	};
	
	getPortletRightView = function() {
		<c:forEach var="layoutList" items="${cafePortletLayoutList}"  varStatus="status">
		<c:if test="${layoutList.colIndex==2}">
			<c:if test="${!empty layoutList.boardId}">
			var url="<c:url value="${layoutList.cafePortlet.viewUrl}"/>?cafeId=${cafe.cafeId}&boardId=${layoutList.boardId}&portletLayoutId=${layoutList.portletLayoutId}";
			</c:if>
			<c:if test="${empty layoutList.boardId}">
			var url="<c:url value="${layoutList.cafePortlet.viewUrl}"/>?cafeId=${cafe.cafeId}&portletLayoutId=${layoutList.portletLayoutId}";
			</c:if>
			
			$jq.ajax({     
				url : url,     
				data :  "",     
				type : "get",     
				loadingElement : {container:"#portlet_right_<c:out value='${layoutList.rowIndex}'/>"},
				success : function(result) {  
					$jq("#portlet_right_<c:out value='${layoutList.rowIndex}'/>").append(result);
				},
				error : function(event, request, settings) { alert("error"); }
			});  
			
		</c:if>
		</c:forEach>	
	};
	
	$jq(document).ready(function() {
		
		getPortletLeftView();
		getPortletRightView();	
	});
	
})(jQuery);

//-->
</script>

		<h1 class="none"><ikep4j:message pre="${prefix}" key="contents.pageTitle" /></h1>
		
		<!--pageTitle Start-->
		<div id="pageTitle" class="TeamColl" style="border:1px solid:#000000">
			<div class="TeamColl_member">
				<div class="member_1"><ikep4j:message pre="${prefix}" key="contents.sysopName" /> : 
					<a href="#a" onclick="javascript:iKEP.showUserContextMenu(this, '${cafe.sysopId}', 'bottom')" title="${cafe.sysopName}">${cafe.sysopName}</a> 
				</div>
				<div class="member_1"><ikep4j:message pre="${prefix}" key="contents.memberCount" /> : <span class="colorPoint">${cafe.memberCount}</span></div>
				<div class="member_1"><ikep4j:message pre="${prefix}" key="contents.items" /> : ${cafe.boardItemCount}<a href="#a">
				</a></div>
				<div class="member_1"><ikep4j:message pre="${prefix}" key="contents.openDate" /> : <ikep4j:timezone pattern="yyyy.MM.dd" date="${cafe.openDate}"/><a href="#a">
				</a></div>
			</div>
			
		</div>
		<!--//pageTitle End-->
		
		<div class="clear"></div>
		<!--TeamColl_img Start-->
		<div class="TeamColl_img">
			<c:if test="${empty cafe.cafeIntroduction}">
			<div class="TeamColl_img_info">
				<div>
					<img src="<c:url value='/base/images/common/title_cafe.png'/>" alt="Team Collaboration" />
				</div>
				<p>${cafe.description}</p>
			</div>
			<div class="TeamColl_img_ex">
				<img src="<c:url value='/base/images/common/img_cafe.gif'/>" alt="" />
			</div>
			</c:if>
			
			<c:if test="${not empty cafe.cafeIntroduction}">
			<div>
			    <spring:htmlEscape defaultHtmlEscape="false"> 
				<spring:bind path="cafe.cafeIntroduction">
					<p>${status.value}</p>
				</spring:bind> 
		        </spring:htmlEscape>
			</div>
			</c:if>	
		</div>
		<!--//TeamColl_img End-->
		
		
		
		<!--blockLeft Start-->
		<div id="blockLeft" class="blockLeft">	
			<c:forEach var="layoutList" items="${cafePortletLayoutList}"  varStatus="status">
			<c:if test="${layoutList.colIndex==1}">	
				<div id="portlet_left_<c:out value='${layoutList.rowIndex}'/>" style="min-height:50px"></div>
			</c:if>
			</c:forEach>	
		</div>
		<!--//blockLeft End-->
		
		<!--blockRight Start-->
		<div id="blockRight" class="blockRight">
			<c:forEach var="layoutList" items="${cafePortletLayoutList}"  varStatus="status">
			<c:if test="${layoutList.colIndex==2}">	
				<div id="portlet_right_<c:out value='${layoutList.rowIndex}'/>" style="min-height:50px"></div>
			</c:if>
			</c:forEach>
		</div>
		<!--//blockRight End-->

