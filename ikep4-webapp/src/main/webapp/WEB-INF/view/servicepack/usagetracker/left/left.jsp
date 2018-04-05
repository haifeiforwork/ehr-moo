<%@ page language="java" contentType="text/html; charset=UTF-8"pageEncoding="UTF-8"%>
<%@ include file="/base/common/taglibs.jsp"%>

<c:set var="preUi" value="ui.servicepack.usagetracker.menu.left" />
<c:set var="user" value="${sessionScope['ikep.user']}" />

<script type="text/javascript">

	$jq(document).ready(function(){

		iKEP.setLeftMenu();
	     
	 });
	


	viewPopUpProfile = function(targetUserId){
		iKEP.goProfilePopupMain(targetUserId);
		//var pageURL = "<c:url value='/support/profile/getProfilePopup.do' />" + "?targetUserId=" + targetUserId;
		//iKEP.popupOpen( pageURL , {width:800, height:280 , callback:function(result) {} });
	}
	
</script>

	<!--leftMenu Start-->
	<h1 class="none">레프트메뉴</h1>
	<h2>
		<a href="<c:url value='/servicepack/usagetracker/index.do' /> "><span><img style="padding-top:6px" src="<c:url value='/base/images/title/lmt_analy_use.gif'/>"/></span></a>
	</h2>
	<div class="left_fixed">
		<ul>			
			<li class="liFirst licurrent"><a href="#a"><ikep4j:message pre='${preUi}' key='static.title'/></a>
				<ul>
					<li class="no_child"><a href="<c:url value='/servicepack/usagetracker/stats/login.do' />"><ikep4j:message pre='${preUi}' key='static.login'/></a></li>
					<li class="no_child"><a href="<c:url value='/servicepack/usagetracker/stats/menu.do' />"><ikep4j:message pre='${preUi}' key='static.menu'/></a></li>
					<li class="no_child"><a href="<c:url value='/servicepack/usagetracker/stats/browser.do' />"><ikep4j:message pre='${preUi}' key='static.browse'/></a></li>
				</ul>					
			</li>			
			<li class="opened"><a href="#a"><ikep4j:message pre='${preUi}' key='useage.title'/></a>
				<ul>
					<li class="no_child"><a href="<c:url value='/servicepack/usagetracker/rank/login.do' />"><ikep4j:message pre='${preUi}' key='useage.login'/></a></li>
					<li class="no_child"><a href="<c:url value='/servicepack/usagetracker/rank/menu.do' />"><ikep4j:message pre='${preUi}' key='useage.menu'/></a></li>
					<li class="no_child liLast"><a href="<c:url value='/servicepack/usagetracker/rank/portlet.do' />"><ikep4j:message pre='${preUi}' key='useage.portlet'/></a></li>
				</ul>
			</li>
			
			<c:if test="${isAdmin}">
			<li class="opened"><a href="#a"><ikep4j:message pre='${preUi}' key='response.title'/></a>
				<ul>
					<li class="no_child"><a href="<c:url value='/servicepack/usagetracker/resTimeUrl/list.do' />"><ikep4j:message pre='${preUi}' key='response.manage'/></a></li>
					<li class="no_child"><a href="<c:url value='/servicepack/usagetracker/resTimeLog/list.do' />"><ikep4j:message pre='${preUi}' key='response.stat'/></a></li>
				</ul>
			</li>
			<li class="opened"><a href="#a">Administration</a>
				<ul>
					<li class="no_child"><a href="<c:url value='/servicepack/usagetracker/menu/list.do' />"><ikep4j:message pre='${preUi}' key='admin.menu'/></a></li>
					<li class="no_child liLast"><a href="<c:url value='/servicepack/usagetracker/logconfig/read.do' />"><ikep4j:message pre='${preUi}' key='admin.log'/></a></li>
				</ul>
			</li>
			</c:if>					
		</ul>
	</div>
		<!--//leftMenu End-->

 