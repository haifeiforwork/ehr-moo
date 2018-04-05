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
		<a href="<c:url value='/servicepack/usagetracker/simpleIndex.do' /> "><span><img style="padding-top:6px" src="<c:url value='/base/images/title/lmt_analy_use.gif'/>"/></span></a>
	</h2>
	<div class="left_fixed">
		<ul>			
			<li class="liFirst licurrent"><a href="#a"><ikep4j:message pre='${preUi}' key='static.title'/></a>
				<ul>
					<li class="no_child"><a href="<c:url value='/servicepack/usagetracker/stats/simpleMenu.do' />"><ikep4j:message pre='${preUi}' key='static.menu'/></a></li>
					<li class="no_child"><a href="<c:url value='/servicepack/usagetracker/stats/simpleMobileMenu.do' />">모바일 <ikep4j:message pre='${preUi}' key='static.menu'/></a></li>
					<li class="no_child liLast"><a href="<c:url value='/servicepack/usagetracker/stats/sms.do' />">SMS 사용량 통계</a></li>	
				</ul>						
			</li>			
		</ul>
	</div>
		<!--//leftMenu End-->

 