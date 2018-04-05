<%@ include file="/base/common/taglibs.jsp"%>
<%@ page language="java" errorPage="/common/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8"%>

<c:set var="prefix" value="message.portal.main.listUser"/>

<c:set var="user" value="${sessionScope['ikep.user']}" />

<script type="text/javascript">
//<![CDATA[

    (function($){
		$(document).ready(function() {

			iKEP.setLeftMenu();

		});
    })(jQuery);

	function goPerformance(menu) {
		if(menu == 'OBJECTIVE_REVIEW') {
			iKEP.iFrameMenuOnclick("<c:url value='/portal/evaluation/performance/objectiveReviewList.do'/>");
		} else if(menu == 'OBJECTIVE_REVIEW2') {
			iKEP.iFrameMenuOnclick("<c:url value='/portal/evaluation/performance/objectiveReview2List.do'/>");
		} else if(menu == 'OBJECTIVE_HSS') {
			iKEP.iFrameMenuOnclick("<c:url value='/portal/evaluation/performance/objectiveHSS.do'/>");
		} else {
			iKEP.iFrameMenuOnclick("<c:url value='/portal/evaluation/performance/objectiveList.do?action="+menu+"'/>");
		}
	}

	function goCompetence(menu) {
		if(menu == 'COMPETENCE_REVIEW') {
			iKEP.iFrameMenuOnclick("<c:url value='/portal/evaluation/competence/competenceReviewList.do'/>");
		} else if(menu == 'COMPETENCE_REVIEW2') {
			iKEP.iFrameMenuOnclick("<c:url value='/portal/evaluation/competence/competenceReview2List.do'/>");
		} else if(menu == 'COMPETENCE_HSS') {
			iKEP.iFrameMenuOnclick("<c:url value='/portal/evaluation/competence/competenceHSS.do'/>");
		} else if(menu == 'COMPETENCE_FEEDBACK_MSS' || menu == 'COMPETENCE_FEEDBACK_ESS') {
			iKEP.iFrameMenuOnclick("<c:url value='/portal/evaluation/competence/competenceFeedBackList.do?action="+menu+"'/>");
		} else {
			iKEP.iFrameMenuOnclick("<c:url value='/portal/evaluation/competence/competenceList.do?action="+menu+"'/>");
		}
	}

	function goMultiside(menu) {
		if(menu == 'MULTISIDE_DIAGNOSIS') {
			iKEP.iFrameMenuOnclick("<c:url value='/portal/evaluation/multiside/multisideDiagnosisList.do?action="+menu+"'/>");
		} else if(menu == 'MULTISIDE_FEEDBACK') {
			iKEP.iFrameMenuOnclick("<c:url value='/portal/evaluation/multiside/multisideFeedbackList.do'/>");
		}
	}

	function goIdp(menu) {
		if(menu == 'IDP_HSS') {
			iKEP.iFrameMenuOnclick("<c:url value='/portal/evaluation/idp/idpHSS.do'/>");
		} else {
			iKEP.iFrameMenuOnclick("<c:url value='/portal/evaluation/idp/idpList.do?action="+menu+"'/>");
		}
	}

	function goPromotion(menu) {
		iKEP.iFrameMenuOnclick("<c:url value='/portal/evaluation/promotion/promotionList.do?action="+menu+"'/>");
	}

	function goTechnical(menu) {
		if(menu == 'EVALUATION') {
			iKEP.iFrameMenuOnclick("<c:url value='/portal/evaluation/technical/technicalList.do?action="+menu+"'/>");
		} else if(menu == 'REVIEW') {
			iKEP.iFrameMenuOnclick("<c:url value='/portal/evaluation/technical/technicalReviewList.do?action="+menu+"'/>");
		} else if(menu == 'HSS') {
			iKEP.iFrameMenuOnclick("<c:url value='/portal/evaluation/technical/technicalHSS.do?action="+menu+"'/>");
		}
	}

	function goPrivilege(menu) {
		if(menu == 'EVALUATION') {
			iKEP.iFrameMenuOnclick("<c:url value='/portal/evaluation/privilege/privilegeList.do?action="+menu+"'/>");
		} else if(menu == 'HSS') {
			iKEP.iFrameMenuOnclick("<c:url value='/portal/evaluation/privilege/privilegeHSS.do?action="+menu+"'/>");
		}
	}

	function goPosition() {
		iKEP.iFrameMenuOnclick("<c:url value='/portal/evaluation/position/positionList.do'/>");
	}

	function goEtc(menu) {
		iKEP.iFrameMenuOnclick("<c:url value='/portal/evaluation/etc/retrieve.do?action="+menu+"'/>");
	}

//]]>
</script>

<!--leftMenu Start-->
<h2 class="han">
	<c:if test="${Bigmenu eq 'performance'}">
		<a href="<c:url value='/portal/evaluation/performance.do'/>">
			<span><img style="vertical-align:bottom;" src="<c:url value='/base/images/title/lmt_coll.gif'/>"/></span>
			<font size="4" style="font-weight: bold;color:#4374D9;font-family: 맑은 고딕;vertical-align: bottom;">업적평가</font>
		</a>
	</c:if>
	<c:if test="${Bigmenu eq 'competence'}">
		<a href="<c:url value='/portal/evaluation/competence.do'/>">
			<span><img style="vertical-align:bottom;" src="<c:url value='/base/images/title/lmt_coll.gif'/>"/></span>
			<font size="4" style="font-weight: bold;color:#4374D9;font-family: 맑은 고딕;vertical-align: bottom;">역량평가</font>
		</a>
	</c:if>
	<c:if test="${Bigmenu eq 'multiside'}">
		<a href="<c:url value='/portal/evaluation/multiside.do'/>">
			<span><img style="vertical-align:bottom;" src="<c:url value='/base/images/title/lmt_coll.gif'/>"/></span>
			<font size="4" style="font-weight: bold;color:#4374D9;font-family: 맑은 고딕;vertical-align: bottom;">다면진단</font>
		</a>
	</c:if>
	<c:if test="${Bigmenu eq 'idp'}">
		<a href="<c:url value='/portal/evaluation/idp.do'/>">
			<span><img style="vertical-align:bottom;" src="<c:url value='/base/images/title/lmt_coll.gif'/>"/></span>
			<font size="4" style="font-weight: bold;color:#4374D9;font-family: 맑은 고딕;vertical-align: bottom;">IDP</font>
		</a>
	</c:if>
	<c:if test="${Bigmenu eq 'promotion'}">
		<a href="<c:url value='/portal/evaluation/promotion.do'/>">
			<span><img style="vertical-align:bottom;" src="<c:url value='/base/images/title/lmt_coll.gif'/>"/></span>
			<font size="4" style="font-weight: bold;color:#4374D9;font-family: 맑은 고딕;vertical-align: bottom;">사무기술직승격</font>
		</a>
	</c:if>
	<c:if test="${Bigmenu eq 'technical'}">
		<a href="<c:url value='/portal/evaluation/technical.do'/>">
			<span><img style="vertical-align:bottom;" src="<c:url value='/base/images/title/lmt_coll.gif'/>"/></span>
			<font size="4" style="font-weight: bold;color:#4374D9;font-family: 맑은 고딕;vertical-align: bottom;">기능직평가</font>
		</a>
	</c:if>
	<c:if test="${Bigmenu eq 'privilege'}">
		<a href="<c:url value='/portal/evaluation/privilege.do'/>">
			<span><img style="vertical-align:bottom;" src="<c:url value='/base/images/title/lmt_coll.gif'/>"/></span>
			<font size="4" style="font-weight: bold;color:#4374D9;font-family: 맑은 고딕;vertical-align: bottom;">별정직평가</font>
		</a>
	</c:if>
	<c:if test="${Bigmenu eq 'position'}">
		<a href="<c:url value='/portal/evaluation/position.do'/>">
			<span><img style="vertical-align:bottom;" src="<c:url value='/base/images/title/lmt_coll.gif'/>"/></span>
			<font size="4" style="font-weight: bold;color:#4374D9;font-family: 맑은 고딕;vertical-align: bottom;">팀원포지션변경</font>
		</a>
	</c:if>
	<c:if test="${Bigmenu eq 'etc'}">
		<a href="<c:url value='/portal/evaluation/etc.do'/>">
			<span><img style="vertical-align:bottom;" src="<c:url value='/base/images/title/lmt_coll.gif'/>"/></span>
			<font size="4" style="font-weight: bold;color:#4374D9;font-family: 맑은 고딕;vertical-align: bottom;">기타</font>
		</a>
	</c:if>
</h2>
<div class="left_fixed" id="leftMenu-pane">
<c:if test="${Bigmenu eq 'performance'}">
	<ul>
		<li class="liFirst opened licurrent"><a href="#">업적평가</a>
			<ul>
				<c:if test="${A01}">
				<li class="no_child" id="zhr_pe_001"><a  href="javascript:goPerformance('OBJECTIVE_SETTING');">목표수립</a></li>
				</c:if>
				<c:if test="${A02}">
				<li class="no_child" id="zhr_pe_002"><a  href="javascript:goPerformance('OBJECTIVE_AGREEMENT');">목표합의</a></li>
				</c:if>
				<c:if test="${A01}">
				<li class="no_child" id="zhr_pe_003"><a  href="javascript:goPerformance('OBJECTIVE_MIDDLECHECK');">중간점검</a></li>
				</c:if>
				<c:if test="${A02}">
				<li class="no_child" id="zhr_pe_004"><a  href="javascript:goPerformance('OBJECTIVE_FEEDBACK');">중간점검 Feedback</a></li>
				</c:if>
				<c:if test="${A01}">
				<li class="no_child" id="zhr_pe_005"><a  href="javascript:goPerformance('OBJECTIVE_PERFORMANCE');">실적작성</a></li>
				</c:if>
				<c:if test="${A02||A03}">
				<li class="no_child" id="zhr_pe_006"><a  href="javascript:goPerformance('OBJECTIVE_EVALUATION');">평가자 평가</a></li>
				</c:if>
				<c:if test="${A04||A05||A06}">
				<li class="no_child" id="zhr_pe_007"><a  href="javascript:goPerformance('OBJECTIVE_REVIEW');">1차 Review</a></li>
				</c:if>
				<li class="no_child" id="zhr_pe_008"><a  href="javascript:goPerformance('OBJECTIVE_REVIEW2');">2차 Review</a></li>
				<c:if test="${isSystemAdmin}">
				<li class="no_child" id="zhr_pe_009"><a  href="javascript:goPerformance('OBJECTIVE_HSS');">평가표조회</a></li>
				</c:if>
			</ul>
		</li>
	</ul>
</c:if>
<c:if test="${Bigmenu eq 'competence'}">
	<ul>
		<li class="liFirst opened licurrent"><a href="#">역량평가</a>
			<ul>
				<c:if test="${A06}">
				<li class="no_child" id="zhr_pe_001"><a  href="javascript:goCompetence('COMPETENCE_SETUP')">직무역량 선정</a></li>
				</c:if>
				<c:if test="${A07}">
				<li class="no_child" id="zhr_pe_002"><a  href="javascript:goCompetence('COMPETENCE_AGREEMENT')">직무역량 합의</a></li>
				</c:if>
				<c:if test="${A07||A08}">
				<li class="no_child" id="zhr_pe_003"><a  href="javascript:goCompetence('COMPETENCE_EVALUATION')">평가자 평가</a></li>
				</c:if>
				<c:if test="${A09||A10}">
				<li class="no_child" id="zhr_pe_004"><a  href="javascript:goCompetence('COMPETENCE_REVIEW')">1차 Review</a></li>
				</c:if>
				<li class="no_child" id="zhr_pe_008"><a  href="javascript:goCompetence('COMPETENCE_REVIEW2');">2차 Review</a></li>
				<c:if test="${isSystemAdmin}">
				<li class="no_child" id="zhr_pe_005"><a  href="javascript:goCompetence('COMPETENCE_HSS')">평가표조회</a></li>
				</c:if>
				<li class="no_child" id="zhr_pe_006"><a  href="javascript:goCompetence('COMPETENCE_FEEDBACK_MSS')">구성원 역량평가 Feedback</a></li>
				<li class="no_child" id="zhr_pe_007"><a  href="javascript:goCompetence('COMPETENCE_FEEDBACK_ESS')">역량평가 Feedback</a></li>
			</ul>
		</li>
	</ul>
</c:if>
<c:if test="${Bigmenu eq 'multiside'}">
	<ul>
		<li class="liFirst opened licurrent"><a href="#">다면진단</a>
			<ul>
				<c:if test="${A12}">
				<li class="no_child" id="zhr_pe_001"><a  href="javascript:goMultiside('MULTISIDE_DIAGNOSIS')">진단</a></li>
				</c:if>
				<c:if test="${A12}">
				<li class="no_child" id="zhr_pe_002"><a  href="javascript:goMultiside('MULTISIDE_FEEDBACK')">Feedback</a></li>
				</c:if>
			</ul>
		</li>
	</ul>
</c:if>
<c:if test="${Bigmenu eq 'idp'}">
	<ul>
		<li class="liFirst opened licurrent"><a href="#">IDP</a>
			<ul>
				<li class="no_child" id="zhr_pe_001"><a  href="javascript:goIdp('IDP_SETTING')">IDP 수립</a></li>
				<c:if test="${user.mssAuthCode == 'M1'||user.mssAuthCode == 'M2'||user.mssAuthCode == 'M3'||user.mssAuthCode == 'M4'}">
				<li class="no_child" id="zhr_pe_002"><a  href="javascript:goIdp('IDP_AGREEMENT')">IDP 합의</a></li>
				</c:if>
				<c:if test="${isSystemAdmin}">
				<li class="no_child" id="zhr_pe_003"><a  href="javascript:goIdp('IDP_HSS')">조회</a></li>
				</c:if>
			</ul>
		</li>
	</ul>
</c:if>
<c:if test="${Bigmenu eq 'promotion'}">
	<ul>
		<li class="liFirst opened licurrent"><a href="#">사무기술직승격</a>
			<ul>
				<li class="no_child" id="zhr_pe_001"><a  href="javascript:goPromotion('GOAL_SETTING_LIST')">도전과제등록 </a></li>
			</ul>
		</li>
	</ul>
</c:if>
<c:if test="${Bigmenu eq 'technical'}">
	<ul>
		<li class="liFirst opened licurrent"><a href="#">기능직평가</a>
			<ul>
				<c:if test="${A13}">
				<li class="no_child" id="zhr_pe_001"><a href="javascript:goTechnical('EVALUATION')">평가자평가</a></li>
				</c:if>
				<c:if test="${A14}">
				<li class="no_child" id="zhr_pe_002"><a href="javascript:goTechnical('REVIEW')">Review</a></li>
				</c:if>
				<c:if test="${isSystemAdmin}">
				<li class="no_child" id="zhr_pe_003"><a href="javascript:goTechnical('HSS')">평가표조회</a></li>
				</c:if>
			</ul>
		</li>
	</ul>
</c:if>
<c:if test="${Bigmenu eq 'privilege'}">
	<ul>
		<li class="liFirst opened licurrent"><a href="#">별정직평가</a>
			<ul>
				<c:if test="${A15}">
				<li class="no_child" id="zhr_pe_001"><a href="javascript:goPrivilege('EVALUATION')">평가자평가</a></li>
				</c:if>
				<c:if test="${isSystemAdmin}">
				<li class="no_child" id="zhr_pe_002"><a  href="javascript:goPrivilege('HSS')">조회</a></li>
				</c:if>
			</ul>
		</li>
	</ul>
</c:if>
<c:if test="${Bigmenu eq 'position'}">
	<ul>
		<li class="liFirst opened licurrent"><a href="#">팀원포지션변경</a>
			<ul>
				<c:if test="${user.mssAuthCode == 'M1'||user.mssAuthCode == 'M3'}">
				<li class="no_child" id="zhr_pe_001"><a href="javascript:goPosition()">팀원포지션변경</a></li>
				</c:if>
			</ul>
		</li>
	</ul>
</c:if>
<c:if test="${Bigmenu eq 'etc'}">
	<ul>
		<li class="liFirst opened licurrent"><a href="#">기타</a>
			<ul>
				<li class="no_child" id="zhr_pe_001"><a href="javascript:goEtc('JIKMU')">직무 프로파일</a></li>
				<li class="no_child" id="zhr_pe_002"><a href="javascript:goEtc('QUALI')">역량 카탈로그</a></li>
				<li class="no_child" id="zhr_pe_008"><a  href="javascript:goCompetence('COMPETENCE_REVIEW2');">2차 Review</a></li>
			</ul>
		</li>
	</ul>
</c:if>
</div>