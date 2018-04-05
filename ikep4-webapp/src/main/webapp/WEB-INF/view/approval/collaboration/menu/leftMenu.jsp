<%@ page language="java" errorPage="/common/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>

<%@ include file="/base/common/taglibs.jsp"%>
<script type="text/javascript" src="<c:url value="/base/js/portlet.js"/>"></script>
<%-- 메시지 관련 Prefix 선언 Start --%> 
<c:set var="preTitle"  value="ui.approval.collaboration.menu.title" />
<c:set var="preMessage"  value="ui.approval.collaboration.message" />
<%-- 메시지 관련 Prefix 선언 End --%>

<%-- 세션정보 선언 Start --%> 
<c:set var="portal" value="${sessionScope['ikep.portal']}" />
<c:set var="user" value="${sessionScope['ikep.user']}" />
<%-- 세션정보 선언 End --%>
<script type="text/javascript">
<!--
(function($) {

	topScroll = function(){ 
		$jq(window).scrollTop(0);
	};
	
	resizeIframe = function() { 
		iKEP.iFrameContentResize();  
	};
	
	var callbackCheckPermission = function( _p) {
		
		var isUserAuthMgntAdmin = _p.isUserAuthMgntAdmin || 'false';
		if( isUserAuthMgntAdmin == true) {
		
			$(".userAuthMgntArea").show();
		}
	};
	
	$jq(document).ready(function() {
		
		iKEP.setLeftMenu();
		
		// 좌측메뉴 권한 확인
		$jq.ajax({
			url : iKEP.getContextRoot() + "/approval/collaboration/ajaxCheckPermissionMenu.do",
			type : "post",
			data : {},
			success : function(data, textStatus, jqXHR) {
				callbackCheckPermission( data);
			},
			error : function(jqXHR, textStatus, errorThrown) {
				alert( "<ikep4j:message pre='${preMessage}' key='permission.error'/>");
			}
		});
		
		
	});
})(jQuery);

//-->
</script>
	<!--leftMenu Start-->
	<h1 class="none"><ikep4j:message pre='${preTitle}' key='title' /></h1>
	<h2 class="han">
		<a href="<c:url value='/approval/collaboration/userauthmgnt/listUserAuthMgntView.do'/>">
			<span><img style="vertical-align:bottom;" src="<c:url value='/base/images/title/lmt_coll.gif'/>"/></span>
			<font size="4" style="font-weight: bold;color:#4374D9;font-family: 맑은 고딕;vertical-align: bottom;">부서 간 협업</font>
		</a>
	</h2>
	<div style="height:10px;">&nbsp;</div>
	<!-- 사용자 권한 관리 -->
	<div class="left_fixed none userAuthMgntArea">
		<ul>
			<li class="opened"><a href="#a"><ikep4j:message pre='${preTitle}' key='userauthmgnt' /></a>
				<ul>
					<li><a href="<c:url value="/approval/collaboration/userauthmgnt/listUserAuthMgntView.do"/>" ><ikep4j:message pre='${preTitle}' key='userauthmgnt' /></a></li>				
				</ul>						
			</li>
			
		</ul>
	</div>
	<!-- 신제품 개발의뢰 내역 -->
	<div class="left_fixed">
		<ul>
			<li class="opened"><a href="#a"><ikep4j:message pre='${preTitle}' key='npd' /></a>
				<ul>
					<li><a href="<c:url value="/approval/collaboration/npd/listNewProductDevView.do"/>" ><ikep4j:message pre='${preTitle}' key='npd.req' /></a></li>				
				</ul>						
				<ul>
					<li><a href="<c:url value="/approval/collaboration/proposal/listProposalView.do"/>" ><ikep4j:message pre='${preTitle}' key='npd.proposal' /></a></li>				
				</ul>						
			</li>
		</ul>
	</div>
	<!-- 구매시험의뢰 -->
	<div class="left_fixed">
		<ul>
			<li class="opened"><a href="#a"><ikep4j:message pre='${preTitle}' key='testreq.main' /></a>
				<ul>
					<li><a href="<c:url value="/approval/collaboration/testreq/listTestRequestView.do"/>" ><ikep4j:message pre='${preTitle}' key='testreq.sub' /></a></li>				
				</ul>						
			</li>
		</ul>
	</div>
	<!-- 법무관리 -->
	<div class="left_fixed">
		<ul>
			<li class="opened"><a href="#a"><ikep4j:message pre='${preTitle}' key='lam' /></a>
				<ul>
					<li><a href="<c:url value="/approval/collaboration/legal/apprContractList.do"/>" ><ikep4j:message pre='${preTitle}' key='lam.contractreview' /></a></li>				
				</ul>						
				<ul>
					<li><a href="<c:url value="/approval/collaboration/jobReq/jobReqList.do"/>" ><ikep4j:message pre='${preTitle}' key='lam.jobreqreview' /></a></li>				
				</ul>	
			</li>
		</ul>
	</div>
	<!-- 수상자료관리 -->
	<div class="left_fixed">
		<ul>
			<li class="opened"><a href="#a">수상자료관리</a>
				<ul>
					<li><a href="<c:url value="/lightpack/award/awardItem/listAwardItemView.do"/>?awardId=100002474279" >수상자료관리</a></li>				
				</ul>	
			</li>
		</ul>
	</div>