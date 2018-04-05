<%@ include file="/base/common/taglibs.jsp"%>
<%@ page language="java" errorPage="/common/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8"%>



<c:set var="essuser" value="${sessionScope['ikep.user']}" />


<script type="text/javascript">
//<![CDATA[
	var tempUrl = "";

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
		setInterval(function(){tempUrl = "";}, 2000);

		/*
		if(window.location.href.indexOf("meetingroom/main") > 0 ) {
			setLicurrent("#meetingRoomReserve");
		} else if (window.location.href.indexOf("myReserveList") > 0 ) {
			setLicurrent("#meetingRoomReserveList");
		}
		*/
		iKEP.setLeftMenu();
		//iKEP.iFrameMenuOnclick("<c:url value='/portal/moorimess/initEssMain.do'/>");//ESS Main 페이지를 호출하며 인증을 태운다.
		document.domain ="moorim.co.kr";
		<c:if test="${Bigmenu eq 'personalMng'}">
			<c:if test="${(essuser.essAuthCode ne 'E9')}">
				<c:choose>
				  	<c:when test="${(essuser.essAuthCode ne 'E0')||isSystemAdmin}">
				  	goPersonal('personAppoint');
					</c:when>
					<c:otherwise>
						setLicurrent('#zhr_pt_004');
						goPersonal('eduExpense');
					</c:otherwise>
				</c:choose>
			</c:if>
			<c:if test="${(essuser.essAuthCode eq 'E9')}">
			goPersonal('personAppoint');
			</c:if>
		</c:if>
		<c:if test="${Bigmenu eq 'evaluationMng'}">
		<c:choose>
		  	<c:when test="${essuser.essAuthCode eq 'E1'||essuser.essAuthCode eq 'E2'||essuser.essAuthCode eq 'E3'||essuser.essAuthCode eq 'E5'||essuser.essAuthCode eq 'E6'||essuser.essAuthCode eq 'E7'||isSystemAdmin}">
		  		setLicurrent('#zhr_hap_016');
		  		essLink('${serverLinkUrl}zhr_hap_016/default.htm');
		  	</c:when>
			<c:otherwise>
			</c:otherwise>
		</c:choose>
		</c:if>


		<c:if test="${Bigmenu eq 'manPowerMng'}">
		<c:choose>
		  	<c:when test="${(essuser.essAuthCode ne 'E8')||isSystemAdmin}">
		  	goManPower('jobProfile');
			</c:when>
			<c:otherwise>
				setLicurrent('#zhr_pa_036');
				goManPower('statusPoint');
			</c:otherwise>
		</c:choose>
		</c:if>


		<c:if test="${Bigmenu eq 'diligenceMng'}">
		<c:choose>
		  	<c:when test="${(essuser.essAuthCode ne 'E8')||isSystemAdmin}">
		  	goDiligence('holidayWork');
			</c:when>
			<c:otherwise>
				setLicurrent('#zhr_pt_002');
				goDiligence('holiday');
			</c:otherwise>
		</c:choose>
		</c:if>


		<c:if test="${Bigmenu eq 'payMng'}">goPay('paystub');</c:if>
		<c:if test="${Bigmenu eq 'organogramMng'}">goOrganogram('organizationChart');</c:if>
		<c:if test="${Bigmenu eq 'payDiligenceMng'}">goPay('paystub');</c:if>
		<c:if test="${Bigmenu eq 'personalDivsionMng'}">goPersonalDivision('personalDivision');</c:if>
	});

	function essLink(esslink){
		if(tempUrl == esslink){
			alert("조회중입니다 잠시 기다려 주세요.");
			return;
		}else{
			tempUrl = esslink;
		}
		document.domain ="moorim.co.kr";//밑에서 프레임 리사이징을 할때 iframe의 높이를 jquery의 contents()함수를 이용해 가져온다. 이때 같은 도메인이어야 exception나지 않는다.
 		var regurl=/eptest.moorim.co.kr/g;
    	var cssStr="sap-syscmd=nocookie&sap-client=300&sap-language=ko&style_sheet=http%3A%2F%2Feptest.moorim.co.kr%2Fikep4-webapp%2Fbase%2Fcss%2Fsap%2Fcontrols_ie6.css%3F6.0.23.0.1&sap-cssversion=6.0.23.0.0&sap-accessibility=&sap-ep-version=6.4.201005101207&sap_ep_version=6.4.201005101207&sap_ep_baseurl=http%3A%2F%2Fep.moorim.co.kr%3A50000%2Firj%2Fportal";//ok
		if (regurl.test(location.href)) {
			cssStr="sap-syscmd=nocookie&sap-client=300&sap-language=ko&style_sheet=http%3A%2F%2Feptest.moorim.co.kr%2Fikep4-webapp%2Fbase%2Fcss%2Fsap%2Fcontrols_ie6.css%3F6.0.23.0.1&sap-cssversion=6.0.23.0.0&sap-accessibility=&sap-ep-version=6.4.201005101207&sap_ep_version=6.4.201005101207&sap_ep_baseurl=http%3A%2F%2Fep.moorim.co.kr%3A50000%2Firj%2Fportal";//ok
		}else{
			cssStr="sap-syscmd=nocookie&sap-client=300&sap-language=ko&style_sheet=http%3A%2F%2Fep.moorim.co.kr%2Fbase%2Fcss%2Fsap%2Fcontrols_ie6.css%3F6.0.23.0.1&sap-cssversion=6.0.23.0.0&sap-accessibility=&sap-ep-version=6.4.201005101207&sap_ep_version=6.4.201005101207&sap_ep_baseurl=http%3A%2F%2Fep.moorim.co.kr%3A50000%2Firj%2Fportal";//ok
		}

	   	 if(esslink.indexOf("?")>-1){
			 esslink=esslink+"&"+cssStr;
		 }else{
			 esslink=esslink+"?"+cssStr;
		 }
		iKEP.iFrameMenuOnclick(esslink);

	}

	function goDiligence(menu){
		if(menu == 'holiday'){
			iKEP.iFrameMenuOnclick("<c:url value='/portal/moorimess/diligenceMng/holidayList.do'/>");
		}else if(menu == 'holidayWork'){
			iKEP.iFrameMenuOnclick("<c:url value='/portal/moorimess/diligenceMng/holidayWorkList.do'/>");
		}else if(menu == 'businessTrip'){
			iKEP.iFrameMenuOnclick("<c:url value='/portal/moorimess/diligenceMng/businessTripList.do'/>");
		}else if(menu == 'yearMonthPaidHoliday'){
			iKEP.iFrameMenuOnclick("<c:url value='/portal/moorimess/diligenceMng/yearMonthPaidHolidayList.do'/>");
		}else if(menu == 'leaveReinstatement'){
			iKEP.iFrameMenuOnclick("<c:url value='/portal/moorimess/diligenceMng/leaveReinstatementList.do'/>");
		}else if(menu == 'evaluation'){
			iKEP.iFrameMenuOnclick("<c:url value='/portal/moorimess/diligenceMng/evaluationList.do'/>");
		}else if(menu == 'holidayAmass'){
			iKEP.iFrameMenuOnclick("<c:url value='/portal/moorimess/diligenceMng/holidayAmassRegView.do'/>");
		}
	}

	function goPersonal(menu){
		if(menu == 'address'){
			iKEP.iFrameMenuOnclick("<c:url value='/portal/moorimess/personalMng/addressList.do'/>");
		}else if(menu == 'education'){
			iKEP.iFrameMenuOnclick("<c:url value='/portal/moorimess/personalMng/educationList.do'/>");
		}else if(menu == 'family'){
			iKEP.iFrameMenuOnclick("<c:url value='/portal/moorimess/personalMng/familyList.do'/>");
		}else if(menu == 'prizeDiscipline'){
			iKEP.iFrameMenuOnclick("<c:url value='/portal/moorimess/personalMng/prizeDisciplineList.do'/>");
		}else if(menu == 'appoint'){
			iKEP.iFrameMenuOnclick("<c:url value='/portal/moorimess/personalMng/appointList.do'/>");
		}else if(menu == 'foreignLanguage'){
			iKEP.iFrameMenuOnclick("<c:url value='/portal/moorimess/personalMng/foreignLanguageList.do'/>");
		}else if(menu == 'qualification'){
			iKEP.iFrameMenuOnclick("<c:url value='/portal/moorimess/personalMng/qualificationList.do'/>");
		}else if(menu == 'personal'){
			iKEP.iFrameMenuOnclick("<c:url value='/portal/moorimess/personalMng/personalView.do'/>");
		}else if(menu == 'career'){
			iKEP.iFrameMenuOnclick("<c:url value='/portal/moorimess/personalMng/careerList.do'/>");
		}else if(menu == 'congratulate'){
			iKEP.iFrameMenuOnclick("<c:url value='/portal/moorimess/personalMng/congratulateList.do'/>");
		}else if(menu == 'cafeteria'){
			iKEP.iFrameMenuOnclick("<c:url value='/portal/moorimess/personalMng/cafeteriaList.do'/>");
		}else if(menu == 'expense'){
			iKEP.iFrameMenuOnclick("<c:url value='/portal/moorimess/personalMng/expenseList.do'/>");
		}else if(menu == 'club'){
			iKEP.iFrameMenuOnclick("<c:url value='/portal/moorimess/personalMng/clubList.do'/>");
		}else if(menu == 'certificateEmp'){
			iKEP.iFrameMenuOnclick("<c:url value='/portal/moorimess/personalMng/certificateEmpList.do'/>");
		}else if(menu == 'certificate'){
			iKEP.iFrameMenuOnclick("<c:url value='/portal/moorimess/personalMng/certificateList.do'/>");
		}else if(menu == 'taxWithholding'){
			iKEP.iFrameMenuOnclick("<c:url value='/portal/moorimess/personalMng/taxWithholdingList.do'/>");
		}else if(menu == 'taxWithholdingView'){
			iKEP.iFrameMenuOnclick("<c:url value='/portal/moorimess/personalMng/taxWithholdingView.do'/>");
		}else if(menu == 'eduExpense'){
			iKEP.iFrameMenuOnclick("<c:url value='/portal/moorimess/personalMng/eduExpenseList.do'/>");
		}else if(menu == 'personAppoint'){
			iKEP.iFrameMenuOnclick("<c:url value='/portal/moorimess/personalMng/personAppointList.do'/>");
		}
	}

	function goPay(menu){
		if(menu == 'paystub'){
			iKEP.iFrameMenuOnclick("<c:url value='/portal/moorimess/payMng/paystubView.do'/>");
		}else if(menu == 'payEarning'){
			iKEP.iFrameMenuOnclick("<c:url value='/portal/moorimess/payMng/payEarningView.do'/>");
		}else if(menu == 'loan'){
			iKEP.iFrameMenuOnclick("<c:url value='/portal/moorimess/payMng/loanList.do'/>");
		}else if(menu == 'yearEndSettlement'){
			iKEP.iFrameMenuOnclick("<c:url value='/portal/moorimess/payMng/yearEndSettlementRegView.do'/>");
		}else if(menu == 'settlementDocument'){
			iKEP.iFrameMenuOnclick("<c:url value='/portal/moorimess/payMng/settlementDocumentView.do'/>");
		}
	}

	function goOrganogram(menu){
		if(menu == 'organizationChart'){
			iKEP.iFrameMenuOnclick("<c:url value='/portal/moorimess/organogramMng/organizationChartList.do'/>");
		}
	}

	function goManPower(menu){
		if(menu == 'jobProfile'){
			iKEP.iFrameMenuOnclick("<c:url value='/portal/moorimess/manPowerMng/jobProfileView.do'/>");
		}else if(menu == 'capaCatalog'){
			iKEP.iFrameMenuOnclick("<c:url value='/portal/moorimess/manPowerMng/capaCatalogView.do'/>");
		}else if(menu == 'statusPoint'){
			iKEP.iFrameMenuOnclick("<c:url value='/portal/moorimess/manPowerMng/statusPointList.do'/>");
		}
	}

	function goPersonalDivision(menu){
		if(menu == 'personalDivision'){
			iKEP.iFrameMenuOnclick("<c:url value='/portal/moorimess/personalDivisionMng/personalDivisionMngList.do'/>");
		}
	}

//]]>
</script>

<!--leftMenu Start-->
<h2 class="han">
	<c:if test="${Bigmenu eq 'personalMng'}"><a href="goPersonal('personAppoint');"><img style="padding-top:6px" src="<c:url value='/base/images/title/lmt_ess_person.gif' />" title="인사관리"/></a></c:if>
	<c:if test="${Bigmenu eq 'evaluationMng'}"><a href=""><img style="padding-top:6px" src="<c:url value='/base/images/title/lmt_ess_appr.gif'/>" title="평가"></a></c:if>
	<c:if test="${Bigmenu eq 'manPowerMng'}"><a href="javascript:goManPower('jobProfile');"><img style="padding-top:6px"  src="<c:url value='/base/images/title/lmt_ess_develop.gif'/>" title="인재개발"></a></c:if>
	<c:if test="${Bigmenu eq 'diligenceMng'}"><a href="javascript:goDiligence('holidayWork');"><img style="padding-top:6px"  src="<c:url value='/base/images/title/lmt_ess_commute.gif'/>" title="근태출장"></a></c:if>
	<c:if test="${Bigmenu eq 'payMng'}"><a href="javascript:goPay('paystub');"><img style="padding-top:6px"  src="<c:url value='/base/images/title/lmt_ess_pay.gif'/>" title="급여"></a></c:if>
	<c:if test="${Bigmenu eq 'personalDivsionMng'}"><a href="javascript:goPersonalDivision('personalDivision');"><img  style="padding-top:6px" src="<c:url value='/base/images/title/lmt_ess_work.gif'/>" title="개인별 업무분장"></a></c:if>
	<c:if test="${Bigmenu eq 'organogramMng'}"><a href="javascript:goOrganogram('organizationChart');"><img  style="padding-top:6px" src="<c:url value='/base/images/title/lmt_ess_org.gif'/>" title="조직도"></a></c:if>
	<c:if test="${Bigmenu eq 'payDiligenceMng'}"><a href="javascript:goPay('paystub');"><img style="padding-top:6px" src="<c:url value='/base/images/title/lmt_ess_paydiligence.gif'/>" title="급여/근태"/></a></c:if>
</h2>
<div class="left_fixed" id="leftMenu-pane">
<c:if test="${Bigmenu eq 'personalMng'}">
	<ul>
	    <c:if test="${(essuser.essAuthCode ne 'E0')||isSystemAdmin}">
	    <c:if test="${essuser.essAuthCode == 'E9'}">
	    	<li class="liFirst opened licurrent"><a href="#">개인정보</a>
			<ul>
				<li class="no_child" id="zhr_pa_008"><a  href="javascript:goPersonal('address')">주소사항 조회</a></li>
				<li class="no_child" id="zhr_pa_021"><a href="javascript:goPersonal('personal')");">신상조회</a></li>
			</ul>
		</li>
	    </c:if>
	    <c:if test="${essuser.essAuthCode != 'E9'}">
		<li class="liFirst opened licurrent"><a href="#">개인정보</a>
			<ul>
				<li class="no_child" id="zhr_es_001"><a  href="javascript:goPersonal('personAppoint')">전사발령 조회</a></li>
				<li class="no_child" id="zhr_pa_002"><a  href="javascript:goPersonal('appoint')">발령사항 조회</a></li>
				<li class="no_child" id="zhr_pa_008"><a  href="javascript:goPersonal('address')">주소사항 조회</a></li>
				<li class="no_child" id="zhr_pa_012"><a  href="javascript:goPersonal('education')">학력사항 조회</a></li>
				<li class="no_child" id="zhr_pa_009"><a  href="javascript:goPersonal('family')">가족사항 조회</a></li>
				<li class="no_child" id="zhr_pa_011"><a  href="javascript:goPersonal('prizeDiscipline')");">상벌내역 조회</a></li>
				<li class="no_child" id="zhr_pa_007"><a  href="javascript:goPersonal('foreignLanguage')");">외국어점수 조회</a></li>
				<li class="no_child" id="zhr_pa_010"><a  href="javascript:goPersonal('qualification')");">자격사항 조회</a></li>
				<li class="no_child" id="zhr_pa_021"><a href="javascript:goPersonal('personal')");">신상조회</a></li>
				<li class="no_child" id="zhr_pa_003"><a href="javascript:goPersonal('career')");">입사전 경력 조회</a></li>
				<c:if test="${essuser.essAuthCode != 'E8'||isSystemAdmin}">
				</c:if>
				
			</ul>
		</li>
		<li class=""><a href="#">복리후생</a>
			<ul>
				<li class="no_child" id="zhr_pt_004"><a  href="javascript:goPersonal('eduExpense');">학자금 신청/조회</a></li>
				<li class="no_child" id="zhr_be_004"><a  href="javascript:goPersonal('congratulate');">경조금 신청/조회</a></li>
				<!--  <li class="no_child" id="zhr_be_001"><a  href="javascript:essLink('${serverLinkUrl}zhr_be_001/default.htm');">건강보험증 신청/조회</a></li>-->
				<c:if test="${essuser.essAuthCode eq 'E1'||essuser.essAuthCode eq 'E2'||essuser.essAuthCode eq 'E3'||essuser.essAuthCode eq 'E7'||isSystemAdmin}">
				<li class="no_child" id="zhr_pa_042"><a href="javascript:goPersonal('cafeteria')">카페테리아 신청</a></li>
				<!--<li class="no_child" id="zhr_pa_043"><a  href="javascript:essLink('${serverLinkUrl}zhr_pa_043/default.htm');">카페테리아 사용계획</a></li>-->
				</c:if>
				<li class="no_child" id="zhr_pt_006"><a  href="javascript:goPersonal('expense');">이사비/부임비/파견비 신청/조회</a></li>

				<li class="no_child" id="zhr_be_003"><a  href="javascript:goPersonal('club');">동호회 신청/조회</a></li>
			</ul>
		</li>
		</c:if>
		</c:if>
		<c:if test="${essuser.essAuthCode != 'E9'}">
		<li class=""><a href="#">증명서</a>
			<ul>
				<li class="no_child" id="zhr_be_002"><a  href="javascript:goPersonal('certificate');">제증명서 신청/조회</a></li>
				<li class="no_child" id="zhr_be_002"><a  href="javascript:goPersonal('certificateEmp');">재직증명서 신청</a></li>
				<c:if test="${(essuser.essAuthCode ne 'E0')||isSystemAdmin}">
				<li class="no_child" id="zhr_be_005"><a  href="javascript:goPersonal('taxWithholding');">근로소득 원천징수영수증신청</a></li>
				</c:if>
				<li class="no_child" id="zhr_py_007"><a  href="javascript:goPersonal('taxWithholdingView');">근로소득 원천징수영수증조회</a></li>
			</ul>
		</li>
		</c:if>
	</ul>
</c:if>
<c:if test="${Bigmenu eq 'evaluationMng'}">
	<ul>
		<c:if test="${essuser.essAuthCode eq 'E1'||essuser.essAuthCode eq 'E2'||essuser.essAuthCode eq 'E3'||essuser.essAuthCode eq 'E5'||essuser.essAuthCode eq 'E6'||essuser.essAuthCode eq 'E7'||isSystemAdmin}">
		<li class=""><a href="#">평가결과</a>
			<ul>
				<li class="no_child" id="zhr_hap_016"><a  href="javascript:essLink('${serverLinkUrl}zhr_hap_016/default.htm');">평가결과 조회</a></li>
			</ul>
		</li>
		</c:if>
	</ul>
</c:if>
<c:if test="${Bigmenu eq 'manPowerMng'}">
	<ul>
		<li class="liFirst opened licurrent"><a href="#">인재개발</a>
			<ul>
				<c:if test="${(essuser.essAuthCode ne 'E8')||isSystemAdmin}">
				<li class="no_child" id="zhr_pd_002"><a  href="javascript:goManPower('jobProfile');">직무 프로파일 조회</a></li>
				</c:if>
				<c:if test="${essuser.essAuthCode eq 'E1'||essuser.essAuthCode eq 'E5'||isSystemAdmin}">
				</c:if>
				<c:if test="${(essuser.essAuthCode ne 'E8')||isSystemAdmin}">
				<li class="no_child" id="zhr_pd_001"><a  href="javascript:goManPower('capaCatalog');">역량 카탈로그 조회</a></li>
				</c:if>
				<c:if test="${(essuser.essAuthCode ne 'E4')||isSystemAdmin}">
				<li class="no_child" id="zhr_pa_036"><a  href="javascript:goManPower('statusPoint');">승격 Point 조회</a></li>
				</c:if>
				<c:if test="${essuser.essAuthCode eq 'E1'||essuser.essAuthCode eq 'E2'||essuser.essAuthCode eq 'E3'||essuser.essAuthCode eq 'E5'||essuser.essAuthCode eq 'E6'||isSystemAdmin}">
				</c:if>
			</ul>
		</li>
		<c:if test="${essuser.essAuthCode eq 'E1'||essuser.essAuthCode eq 'E2'||essuser.essAuthCode eq 'E5'||essuser.essAuthCode eq 'E6'||isSystemAdmin}">
		</c:if>
	</ul>
</c:if>
<c:if test="${Bigmenu eq 'diligenceMng'}">
	<ul>
		<li class="liFirst opened licurrent"><a href="#">근태출장</a>
			<ul>
				<c:if test="${(essuser.essAuthCode ne 'E8')||isSystemAdmin}">
				<li class="no_child licurrent" id="zhr_pt_003"><a  href="javascript:goDiligence('holidayWork');">휴일 근무 신청/조회</a></li>
				</c:if>
				<li class="no_child" id="zhr_pt_002"><a href="javascript:goDiligence('holiday');">휴가 신청/조회</a></li>
				<li class="no_child" id="zhr_pt_007"><a  href="javascript:goDiligence('evaluation');">월 근태평가 Feedback</a></li>
				<li class="no_child" id="zhr_pt_005"><a  href="javascript:goDiligence('holidayAmass');">연월차 적치 신청/조회</a></li>
				<li class="no_child" id="zhr_pt_010"><a  href="javascript:goDiligence('yearMonthPaidHoliday')">연월차 내역 조회</a></li>
				<li class="no_child" id="zhr_pa_033"><a  href="javascript:goDiligence('leaveReinstatement');">휴복직 신청/조회</a></li>
				<li class="no_child" id="zhr_pt_008"><a href="javascript:goDiligence('businessTrip');">출장 신청/조회</a></li>
			</ul>
		</li>
	</ul>
</c:if>
<c:if test="${Bigmenu eq 'payMng'}">
	<ul>
		<li class="liFirst opened licurrent"><a href="#">급여</a>
			<ul>
				<c:if test="${(essuser.essAuthCode ne 'E4')||isSystemAdmin}">
				<li class="no_child licurrent" id="zhr_py_002"><a  href="javascript:goPay('paystub');">임금명세서</a></li>
				<li class="no_child" id="zhr_py_004"><a  href="javascript:goPay('payEarning');">급여실적 조회</a></li>
				</c:if>
				<c:if test="${((essuser.essAuthCode ne 'E0') && (essuser.essAuthCode ne 'E4')) ||isSystemAdmin}">
				<li class="no_child" id="zhr_py_003"><a  href="javascript:goPay('yearEndSettlement')">연말정산 소득공제 신청</a></li>
				<li class="no_child" id="zhr_py_006"><a  href="javascript:goPay('settlementDocument');">소득공제 신고서 조회</a></li>
				<li class="no_child" id="zhr_py_001"><a  href="javascript:goPay('loan');">대출내역 조회</a></li>
				</c:if>
			</ul>
		</li>
	</ul>
</c:if>
<c:if test="${Bigmenu eq 'personalDivsionMng'}">
	<ul>
		<li class="liFirst opened licurrent"><a href="#">개인별 업무분장</a>
			<ul>
				<li class="no_child" id="zhr_py_008"><a  href="javascript:goPersonalDivision('personalDivision')">개인별 업무분장 작성</a></li>
			</ul>
		</li>
	</ul>
</c:if>
<c:if test="${Bigmenu eq 'organogramMng'}">
	<ul>
		<li class="liFirst opened licurrent"><a href="#">조직도</a>
			<ul>
				<li class="no_child" id="zhr_pa_044"><a  href="javascript:goOrganogram('organizationChart')">조직도</a></li>
			</ul>
		</li>
	</ul>
</c:if>
<c:if test="${Bigmenu eq 'payDiligenceMng'}">
	<ul>
		<li class="liFirst opened licurrent"><a href="#">급여/근태</a>
			<ul>
				<li class="no_child licurrent" id="zhr_py_002"><a  href="javascript:goPay('paystub');">개인임금명세서 조회</a></li>
				<li class="no_child" id="zhr_py_004"><a  href="javascript:goPay('payEarning');">급여실적 조회</a></li>
				<li class="no_child" id="zhr_pt_011"><a  href="javascript:goDiligence('evaluation');">월 근태현황 조회</a></li>
				<li class="no_child" id="zhr_pt_010"><a  href="javascript:goDiligence('yearMonthPaidHoliday')">연월차 내역 조회</a></li>
				<li class="no_child" id="zhr_py_003"><a  href="javascript:goPay('yearEndSettlement')">연말정산 소득공제 신청</a></li>
				<li class="no_child" id="zhr_py_006"><a  href="javascript:goPay('settlementDocument');">소득공제 신고서 조회</a></li>
				<li class="no_child" id="zhr_py_007"><a  href="javascript:goPersonal('taxWithholdingView');">근로소득 원천징수영수증<br/>조회</a></li>
			</ul>
		</li>
	</ul>
</c:if>
</div>