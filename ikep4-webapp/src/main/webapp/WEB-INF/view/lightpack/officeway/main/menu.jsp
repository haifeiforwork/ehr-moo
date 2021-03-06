<%@ include file="/base/common/taglibs.jsp"%>
<%@ page language="java" errorPage="/common/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8"%>

<c:set var="preMenu"  value="ui.lightpack.officeway.menu" />
<c:set var="user" value="${sessionScope['ikep.user']}" />

<script type="text/javascript">
//<![CDATA[

	
	function setLicurrent(el) {
		var $el = el;
		if(typeof el === "string") {
			$el = $jq(el);
		}
		clearCurrent();
		$el.addClass("licurrent");
		$el.parents("li.opened", "#leftMenu-pane").addClass("licurrent");
	}
	
	function clearCurrent() {
		$jq("#leftMenu-pane li").removeClass("licurrent");
	}
	
	$jq(document).ready(function() {
		
		iKEP.setLeftMenu();
		iKEP.iFrameMenuOnclick("<c:url value='/lightpack/officeway/officewayUseRequestMyList.do'/>");
		
		
	});
	function goOfficeWayUseRequestForm(){
		 iKEP.iFrameMenuOnclick("<c:url value='/lightpack/officeway/officewayUseRequestForm.do'/>");
	}
	
	function goOfficeWayUseRequestMyListUpdateForm(){
		 iKEP.iFrameMenuOnclick("<c:url value='/lightpack/officeway/officewayUseRequestMyListUpdateForm.do'/>");
	}

	function goOfficeWayUseRequestMyList(){
		 iKEP.iFrameMenuOnclick("<c:url value='/lightpack/officeway/officewayUseRequestMyList.do'/>");
	}
	
	function goOfficeWayUseRequestTeamList(){
		 iKEP.iFrameMenuOnclick("<c:url value='/lightpack/officeway/officewayUseRequestTeamList.do'/>");
	}
	
	function goOfficeWayUseRequestTeamListPayment(){
		 iKEP.iFrameMenuOnclick("<c:url value='/lightpack/officeway/officewayUseRequestTeamListPayment.do'/>");
	}
	
	function goOfficeWayUseRequestOtherTeamList(){
		 iKEP.iFrameMenuOnclick("<c:url value='/lightpack/officeway/officewayUseRequestOtherTeamList.do'/>");
	}
	
	function goOfficeWayUseRequestStatistics(){
		 iKEP.iFrameMenuOnclick("<c:url value='/lightpack/officeway/officewayUseRequestStatistics.do'/>");
	}
	
	function goOfficeWayUseRequestTeamStatistics(){
		 iKEP.iFrameMenuOnclick("<c:url value='/lightpack/officeway/officewayUseRequestTeamStatistics.do'/>");
	}
	
	function goOfficeWayUseRequestTeamListUpdateForm(){
		 iKEP.iFrameMenuOnclick("<c:url value='/lightpack/officeway/officewayUseRequestTeamListUpdateForm.do'/>");
	}
	
	function goOfficeWayUseRequestTeamsList(){
		 iKEP.iFrameMenuOnclick("<c:url value='/lightpack/officeway/officewayUseRequestTeamsList.do'/>");
	}

	function goOfficeWayUseRequestList(){
		 iKEP.iFrameMenuOnclick("<c:url value='/lightpack/officeway/officewayUseRequestList.do'/>");
	}
	
	function goOfficeWayUseRequestAllList(){
		 iKEP.iFrameMenuOnclick("<c:url value='/lightpack/officeway/officewayUseRequestAllList.do'/>");
	}
	
	function roleList(){
		 iKEP.iFrameMenuOnclick("<c:url value='/lightpack/officeway/roleList.do'/>");
	}
	
	function goEditPeriodForm(){
		iKEP.iFrameMenuOnclick("<c:url value='/lightpack/officeway/editPeriodForm.do'/>");
	}
	
	function goEditExceptForm(){
		iKEP.iFrameMenuOnclick("<c:url value='/lightpack/officeway/exceptOfficewayList.do'/>");
	}
	
	function goEditCategoryForm(){
		iKEP.iFrameMenuOnclick("<c:url value='/lightpack/officeway/editCategory.do'/>");
	}
	
	function goEditReasonForm(){
		iKEP.iFrameMenuOnclick("<c:url value='/lightpack/officeway/editReason.do'/>");
	}
	
	function goEditTeamAuthForm(){
		iKEP.iFrameMenuOnclick("<c:url value='/lightpack/officeway/OfficewayTeamAuthList.do'/>");
	}
	
	function goOfficeway(){
		var url = "http://www.officeway.co.kr";
		
		//iKEP.popupOpen(url , {width:1000, height:700});
		window.open(url,'officeway','width=1000,height=700,scrollbars=1,resizable=yes,toolbar=1,menubar=1'); 
	}
	
	
//]]>
</script>

<!--leftMenu Start-->
<h2 class="han">
	<a href="<c:url value='/lightpack/officeway/officewayUseRequestMenuView.do'/>">
		<span><img style="vertical-align:bottom;" src="<c:url value='/base/images/title/lmt_coll.gif'/>"/></span>
		<font size="4" style="font-weight: bold;color:#4374D9;font-family: 맑은 고딕;vertical-align: bottom;">사무용품신청</font>
	</a>
</h2>
<div class="left_fixed" id="leftMenu-pane">
	<ul>
		<li class="liFirst opened licurrent"><a href="#">사무용품 신청</a>
			<ul>
				<li class="no_child licurrent" id="officewayUseRequestMyList">
					<a  href="javascript:goOfficeWayUseRequestMyList();">
						나의 신청/현황
					</a>
				</li>
				<li class="no_child" id="officewayUseRequestTeamList">
					<a  href="javascript:goOfficeWayUseRequestTeamList();">
						팀 내역 조회
					</a>
				</li>
				<!-- <li class="no_child" id="officewayUseRequestTeamStatistics">
					<a  href="javascript:goOfficeWayUseRequestTeamStatistics();">
						팀/월별 조회
					</a>
				</li> -->
				<li class="no_child" id="officewayUseRequestStatistics">
					<a  href="javascript:goOfficeWayUseRequestStatistics();">
						팀별/월별 조회
					</a>
				</li>
				<c:if test="${ofmlRole || ofmrRole}">
				<li class="no_child" id="officewayUseRequestTeamsList">
					<a  href="javascript:goOfficeWayUseRequestTeamsList();">
						(전체)팀별 상신/결재
					</a>
				</li>
				<li class="no_child" id="officewayUseRequestOtherTeamList">
					<a  href="javascript:goOfficeWayUseRequestOtherTeamList();">
						(전체)팀별 내역 조회
					</a>
				</li>
				
				</c:if>
				<li class="no_child" id="officeway">
					<a  href="javascript:goOfficeway();">
						오피스웨이 바로가기
					</a>
				</li>
			</ul>
		</li>
		<c:if test="${ofmlRole || ofmrRole}">
		<li class="closed"><a href="#a">Administrator</a>
			<ul style="display:none">
                <li><a href="javascript:roleList();">권한관리</a></li>
				<li><a href="javascript:goEditPeriodForm();">신청 기간 관리</a></li>
				<li><a href="javascript:goEditExceptForm();">신청 제외 품목 관리</a></li>
				<li><a href="javascript:goEditReasonForm();">사유 관리</a></li>
				<li><a href="javascript:goEditTeamAuthForm();">부서 관리</a></li>
			</ul>						
		</li>
		</c:if>
	</ul>
</div>
