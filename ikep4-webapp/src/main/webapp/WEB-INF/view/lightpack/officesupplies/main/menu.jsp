<%@ include file="/base/common/taglibs.jsp"%>
<%@ page language="java" errorPage="/common/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8"%>

<c:set var="preMenu"  value="ui.lightpack.officesupplies.menu" />
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
		iKEP.iFrameMenuOnclick("<c:url value='/lightpack/officesupplies/officesuppliesUseRequestMyList.do'/>");
		
		
	});
	function goOfficeSuppliesUseRequestForm(){
		 iKEP.iFrameMenuOnclick("<c:url value='/lightpack/officesupplies/officesuppliesUseRequestForm.do'/>");
	}
	
	function goOfficeSuppliesUseRequestMyListUpdateForm(){
		 iKEP.iFrameMenuOnclick("<c:url value='/lightpack/officesupplies/officesuppliesUseRequestMyListUpdateForm.do'/>");
	}

	function goOfficeSuppliesUseRequestMyList(){
		 iKEP.iFrameMenuOnclick("<c:url value='/lightpack/officesupplies/officesuppliesUseRequestMyList.do'/>");
	}
	
	function goOfficeSuppliesUseRequestTeamList(){
		 iKEP.iFrameMenuOnclick("<c:url value='/lightpack/officesupplies/officesuppliesUseRequestTeamList.do'/>");
	}
	
	function goOfficeSuppliesUseRequestTeamListPayment(){
		 iKEP.iFrameMenuOnclick("<c:url value='/lightpack/officesupplies/officesuppliesUseRequestTeamListPayment.do'/>");
	}
	
	function goOfficeSuppliesUseRequestOtherTeamList(){
		 iKEP.iFrameMenuOnclick("<c:url value='/lightpack/officesupplies/officesuppliesUseRequestOtherTeamList.do'/>");
	}
	
	function goOfficeSuppliesUseRequestStatistics(){
		 iKEP.iFrameMenuOnclick("<c:url value='/lightpack/officesupplies/officesuppliesUseRequestStatistics.do'/>");
	}
	
	function goOfficeSuppliesUseRequestTeamStatistics(){
		 iKEP.iFrameMenuOnclick("<c:url value='/lightpack/officesupplies/officesuppliesUseRequestTeamStatistics.do'/>");
	}
	
	function goOfficeSuppliesUseRequestTeamListUpdateForm(){
		 iKEP.iFrameMenuOnclick("<c:url value='/lightpack/officesupplies/officesuppliesUseRequestTeamListUpdateForm.do'/>");
	}
	
	function goOfficeSuppliesUseRequestTeamsList(){
		 iKEP.iFrameMenuOnclick("<c:url value='/lightpack/officesupplies/officesuppliesUseRequestTeamsList.do'/>");
	}

	function goOfficeSuppliesUseRequestList(){
		 iKEP.iFrameMenuOnclick("<c:url value='/lightpack/officesupplies/officesuppliesUseRequestList.do'/>");
	}
	
	function goOfficeSuppliesUseRequestAllList(){
		 iKEP.iFrameMenuOnclick("<c:url value='/lightpack/officesupplies/officesuppliesUseRequestAllList.do'/>");
	}
	
	function roleList(){
		 iKEP.iFrameMenuOnclick("<c:url value='/lightpack/officesupplies/roleList.do'/>");
	}
	
	function goEditPeriodForm(){
		iKEP.iFrameMenuOnclick("<c:url value='/lightpack/officesupplies/editPeriodForm.do'/>");
	}
	
	function goEditExceptForm(){
		iKEP.iFrameMenuOnclick("<c:url value='/lightpack/officesupplies/exceptOfficesuppliesList.do'/>");
	}
	
	function goEditCategoryForm(){
		iKEP.iFrameMenuOnclick("<c:url value='/lightpack/officesupplies/editCategory.do'/>");
	}
	
	function goEditReasonForm(){
		iKEP.iFrameMenuOnclick("<c:url value='/lightpack/officesupplies/editReason.do'/>");
	}
	
	function goEditTeamAuthForm(){
		iKEP.iFrameMenuOnclick("<c:url value='/lightpack/officesupplies/OfficesuppliesTeamAuthList.do'/>");
	}
	
	function goOfficeway(){
		var url = "http://www.officeway.co.kr";
		
		iKEP.popupOpen(url , {width:1000, height:700});
	}
	
	
//]]>
</script>

<!--leftMenu Start-->
<h2 class="han">
	<a href="<c:url value='/lightpack/officesupplies/officesuppliesUseRequestMenuView.do'/>">
		<span><img style="vertical-align:bottom;" src="<c:url value='/base/images/title/lmt_coll.gif'/>"/></span>
		<font size="4" style="font-weight: bold;color:#4374D9;font-family: 맑은 고딕;vertical-align: bottom;">사무용품신청</font>
	</a>
</h2>
<div class="left_fixed" id="leftMenu-pane">
	<ul>
		<li class="liFirst opened licurrent"><a href="#">사무용품 신청</a>
			<ul>
				<!-- <li class="no_child" id="officesuppliesUseRequestForm">
					<a  href="javascript:goOfficeSuppliesUseRequestForm();">
						사무용품 신청
					</a>
				</li> -->
				<li class="no_child licurrent" id="officesuppliesUseRequestMyList">
					<a  href="javascript:goOfficeSuppliesUseRequestMyList();">
						나의 신청/현황
					</a>
				</li>
				<c:if test="${oftlRole || oftrRole}">
				<li class="no_child" id="officesuppliesUseRequestTeamListPayment">
					<a  href="javascript:goOfficeSuppliesUseRequestTeamListPayment();">
						팀 상신/결재
					</a>
				</li>
				</c:if>
				<li class="no_child" id="officesuppliesUseRequestTeamList">
					<a  href="javascript:goOfficeSuppliesUseRequestTeamList();">
						팀 내역 조회
					</a>
				</li>
				<li class="no_child" id="officesuppliesUseRequestTeamStatistics">
					<a  href="javascript:goOfficeSuppliesUseRequestTeamStatistics();">
						팀/월별 조회
					</a>
				</li>
				<c:if test="${ofmlRole || ofmrRole}">
				<li class="no_child" id="officesuppliesUseRequestTeamsList">
					<a  href="javascript:goOfficeSuppliesUseRequestTeamsList();">
						(전체)팀별 상신/결재
					</a>
				</li>
				<li class="no_child" id="officesuppliesUseRequestOtherTeamList">
					<a  href="javascript:goOfficeSuppliesUseRequestOtherTeamList();">
						(전체)팀별 내역 조회
					</a>
				</li>
				<li class="no_child" id="officesuppliesUseRequestStatistics">
					<a  href="javascript:goOfficeSuppliesUseRequestStatistics();">
						(전체)팀별/월별 조회
					</a>
				</li>
				</c:if>
				<!-- <li class="no_child" id="officesuppliesUseRequestMyListUpdateForm">
					<a  href="javascript:goOfficeSuppliesUseRequestMyListUpdateForm();">
						사무용품 신청 수정
					</a>
				</li> -->
				
				<!-- <li class="no_child licurrent" id="officesuppliesUseRequestTeamListUpdateForm">
					<a  href="javascript:goOfficeSuppliesUseRequestTeamListUpdateForm();">
						팀 신청 현황 수정
					</a>
				</li> -->
				
				
				
				<%-- <c:if test="${user.leader == '1'}">
				<li class="no_child" id="officesuppliesUseRequestList">
					<a  href="javascript:goOfficeSuppliesUseRequestList();">
						나의 결재 현황
					</a>
				</li>
				</c:if>
				<c:if test="${officesuppliesrole}">
				<li class="no_child" id="officesuppliesUseRequestAllList">
					<a  href="javascript:goOfficeSuppliesUseRequestAllList();">
						예외 신청 현황
					</a>
				</li>
				</c:if> --%>
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
				<!-- <li><a href="javascript:goEditCategoryForm();">품목 분류 관리</a></li> -->
				<li><a href="javascript:goEditReasonForm();">사유 관리</a></li>
				<li><a href="javascript:goEditTeamAuthForm();">부서 권한 관리</a></li>
			</ul>						
		</li>
		</c:if>
	</ul>
</div>
