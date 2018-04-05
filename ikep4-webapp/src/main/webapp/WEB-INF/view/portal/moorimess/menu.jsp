<%@ include file="/base/common/taglibs.jsp"%>
<%@ page language="java" errorPage="/common/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8"%>



<c:set var="essuser" value="${sessionScope['ikep.user']}" />


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
						iKEP.iFrameMenuOnclick("<c:url value='/portal/moorimess/initEssMain.do'/>?whereLink=zhr_pa_002");
					</c:when>
					<c:otherwise>
						setLicurrent('#zhr_pt_004');
						iKEP.iFrameMenuOnclick("<c:url value='/portal/moorimess/initEssMain.do'/>?whereLink=zhr_be_002");
					</c:otherwise>
				</c:choose>
			</c:if>
			<c:if test="${(essuser.essAuthCode eq 'E9')}">	
				iKEP.iFrameMenuOnclick("<c:url value='/portal/moorimess/initEssMain.do'/>?whereLink=zhr_pa_008");
			</c:if>
		</c:if>
		<c:if test="${Bigmenu eq 'evaluationMng'}">
		<c:choose>
		  	<c:when test="${essuser.essAuthCode eq 'E1'||essuser.essAuthCode eq 'E5'||isSystemAdmin}">
				iKEP.iFrameMenuOnclick("<c:url value='/portal/moorimess/initEssMain.do'/>?whereLink=zhr_hap_001");
			</c:when>
			<c:otherwise>
				setLicurrent('#zhr_hap_017');
				iKEP.iFrameMenuOnclick("<c:url value='/portal/moorimess/initEssMain.do'/>?whereLink=zhr_hap_017");
			</c:otherwise>
		</c:choose>
		</c:if>
		
		
		<c:if test="${Bigmenu eq 'manPowerMng'}">
		<c:choose>
		  	<c:when test="${(essuser.essAuthCode ne 'E8')||isSystemAdmin}">
				iKEP.iFrameMenuOnclick("<c:url value='/portal/moorimess/initEssMain.do'/>?whereLink=zhr_pd_002");
			</c:when>
			<c:otherwise>
				setLicurrent('#zhr_pa_036');
				iKEP.iFrameMenuOnclick("<c:url value='/portal/moorimess/initEssMain.do'/>?whereLink=zhr_pa_036");
			</c:otherwise>
		</c:choose>
		</c:if>
		
		
		<c:if test="${Bigmenu eq 'diligenceMng'}">
		<c:choose>
		  	<c:when test="${(essuser.essAuthCode ne 'E8')||isSystemAdmin}">
				iKEP.iFrameMenuOnclick("<c:url value='/portal/moorimess/initEssMain.do'/>?whereLink=zhr_pt_003");
			</c:when>
			<c:otherwise>
				setLicurrent('#zhr_pt_002');
				iKEP.iFrameMenuOnclick("<c:url value='/portal/moorimess/initEssMain.do'/>?whereLink=zhr_pt_002");
			</c:otherwise>
		</c:choose>
		</c:if>
		
		
		<c:if test="${Bigmenu eq 'payMng'}">iKEP.iFrameMenuOnclick("<c:url value='/portal/moorimess/initEssMain.do'/>?whereLink=zhr_py_002");</c:if>
		<c:if test="${Bigmenu eq 'personalDivsionMng'}">iKEP.iFrameMenuOnclick("<c:url value='/portal/moorimess/initEssMain.do'/>?whereLink=zhr_pd_033");</c:if>
		<c:if test="${Bigmenu eq 'organogramMng'}">iKEP.iFrameMenuOnclick("<c:url value='/portal/moorimess/initEssMain.do'/>?whereLink=zhr_pa_044");</c:if>
		<c:if test="${Bigmenu eq 'payDiligenceMng'}">iKEP.iFrameMenuOnclick("<c:url value='/portal/moorimess/initEssMain.do'/>?whereLink=zhr_py_002_k2");</c:if>

	});

	function essLink(esslink){
		document.domain ="moorim.co.kr";//밑에서 프레임 리사이징을 할때 iframe의 높이를 jquery의 contents()함수를 이용해 가져온다. 이때 같은 도메인이어야 exception나지 않는다. 
 		var regurl=/eptest.moorim.co.kr/g;
    	var cssStr="sap-client=300&sap-language=ko&style_sheet=http%3A%2F%2Feptest.moorim.co.kr%2Fikep4-webapp%2Fbase%2Fcss%2Fsap%2Fcontrols_ie6.css%3F6.0.23.0.1&sap-cssversion=6.0.23.0.0&sap-accessibility=&sap-ep-version=6.4.201005101207&sap_ep_version=6.4.201005101207&sap_ep_baseurl=http%3A%2F%2Fep.moorim.co.kr%3A50000%2Firj%2Fportal";//ok	
		if (regurl.test(location.href)) { 
			cssStr="sap-client=300&sap-language=ko&style_sheet=http%3A%2F%2Feptest.moorim.co.kr%2Fikep4-webapp%2Fbase%2Fcss%2Fsap%2Fcontrols_ie6.css%3F6.0.23.0.1&sap-cssversion=6.0.23.0.0&sap-accessibility=&sap-ep-version=6.4.201005101207&sap_ep_version=6.4.201005101207&sap_ep_baseurl=http%3A%2F%2Fep.moorim.co.kr%3A50000%2Firj%2Fportal";//ok	
		}else{
			cssStr="sap-client=300&sap-language=ko&style_sheet=http%3A%2F%2Fep.moorim.co.kr%2Fbase%2Fcss%2Fsap%2Fcontrols_ie6.css%3F6.0.23.0.1&sap-cssversion=6.0.23.0.0&sap-accessibility=&sap-ep-version=6.4.201005101207&sap_ep_version=6.4.201005101207&sap_ep_baseurl=http%3A%2F%2Fep.moorim.co.kr%3A50000%2Firj%2Fportal";//ok	
		}
	   	 
	   	 if(esslink.indexOf("?")>-1){
			 esslink=esslink+"&"+cssStr;
		 }else{
			 esslink=esslink+"?"+cssStr;
		 }
		iKEP.iFrameMenuOnclick(esslink);

	}
	

//]]>
</script>

<!--leftMenu Start-->
<h2 class="han">
	<c:if test="${Bigmenu eq 'personalMng'}"><a href="<c:url value='/portal/moorimess/personalMng.do'/>"><img style="padding-top:6px" src="<c:url value='/base/images/title/lmt_ess_person.gif' />" title="인사관리"/></a></c:if>
	<c:if test="${Bigmenu eq 'evaluationMng'}"><a href="<c:url value='/portal/moorimess/evaluationMng.do'/>"><img style="padding-top:6px" src="<c:url value='/base/images/title/lmt_ess_appr.gif'/>" title="평가"></a></c:if>
	<c:if test="${Bigmenu eq 'manPowerMng'}"><a href="<c:url value='/portal/moorimess/manPowerMng.do'/>"><img style="padding-top:6px"  src="<c:url value='/base/images/title/lmt_ess_develop.gif'/>" title="인재개발"></a></c:if>
	<c:if test="${Bigmenu eq 'diligenceMng'}"><a href="<c:url value='/portal/moorimess/diligenceMng.do'/>"><img style="padding-top:6px"  src="<c:url value='/base/images/title/lmt_ess_commute.gif'/>" title="근태출장"></a></c:if>
	<c:if test="${Bigmenu eq 'payMng'}"><a href="<c:url value='/portal/moorimess/payMng.do'/>"><img style="padding-top:6px"  src="<c:url value='/base/images/title/lmt_ess_pay.gif'/>" title="급여"></a></c:if>
	<c:if test="${Bigmenu eq 'personalDivsionMng'}"><a href="<c:url value='/portal/moorimess/personalDivsionMng.do'/>"><img  style="padding-top:6px" src="<c:url value='/base/images/title/lmt_ess_work.gif'/>" title="개인별 업무분장"></a></c:if>
	<c:if test="${Bigmenu eq 'organogramMng'}"><a href="<c:url value='/portal/moorimess/organogramMng.do'/>"><img  style="padding-top:6px" src="<c:url value='/base/images/title/lmt_ess_org.gif'/>" title="조직도"></a></c:if>
	<c:if test="${Bigmenu eq 'payDiligenceMng'}"><a href="<c:url value='/portal/moorimess/payDiligenceMng.do'/>"><img style="padding-top:6px" src="<c:url value='/base/images/title/lmt_ess_paydiligence.gif'/>" title="급여/근태"/></a></c:if>
</h2>
<div class="left_fixed" id="leftMenu-pane">
<c:if test="${Bigmenu eq 'personalMng'}">
	<ul>
	    <c:if test="${(essuser.essAuthCode ne 'E0')||isSystemAdmin}">
	    <c:if test="${essuser.essAuthCode == 'E9'}">
	    	<li class="liFirst opened licurrent"><a href="#">개인정보</a>
			<ul>
				<li class="no_child" id="zhr_pa_008">
				<a  href="javascript:essLink('${serverLinkUrl}zhr_pa_008/default.htm?E_PERNR=${essuser.empNo}');">주소사항 조회</a>
				</li>
			</ul>
		</li>
	    </c:if>
	    <c:if test="${essuser.essAuthCode != 'E9'}">
		<li class="liFirst opened licurrent"><a href="#">개인정보</a>
			<ul>
				<li class="no_child licurrent" id="zhr_pa_002"><a  href="javascript:essLink('${serverLinkUrl}zhr_pa_002/default.htm');">발령사항 조회</a></li>
				<li class="no_child" id="zhr_pa_008"><a  href="javascript:essLink('${serverLinkUrl}zhr_pa_008/default.htm?E_PERNR=${essuser.empNo}');">주소사항 조회</a></li>	
				<li class="no_child" id="zhr_pa_012"><a  href="javascript:essLink('${serverLinkUrl}zhr_pa_012/default.htm');">학력사항 조회</a></li>
				<li class="no_child" id="zhr_pa_009"><a  href="javascript:essLink('${serverLinkUrl}zhr_pa_009/default.htm');">가족사항 조회</a></li>	
				<li class="no_child" id="zhr_pa_011"><a  href="javascript:essLink('${serverLinkUrl}zhr_pa_011/default.htm');">상벌내역 조회</a></li>	
				<li class="no_child" id="zhr_pa_007"><a  href="javascript:essLink('${serverLinkUrl}zhr_pa_007/default.htm');">외국어점수 조회</a></li>	
				<li class="no_child" id="zhr_pa_010"><a  href="javascript:essLink('${serverLinkUrl}zhr_pa_010/default.htm');">자격사항 조회</a></li>	
				<li class="no_child" id="zhr_pa_021"><a  href="javascript:essLink('${serverLinkUrl}zhr_pa_021/default.htm');">신상조회</a></li>	
				<li class="no_child" id="zhr_pa_003"><a  href="javascript:essLink('${serverLinkUrl}zhr_pa_003/default.htm');">입사전 경력 조회</a></li>	
				<c:if test="${essuser.essAuthCode != 'E8'||isSystemAdmin}">
				<li class="no_child" id="zhr_hap_009"><a  href="javascript:essLink('${serverLinkUrl}zhr_hap_009/default.htm');">평가결과 이력조회</a></li>	
				</c:if>
			</ul>
		</li>
		<li class=""><a href="#">복리후생</a>
			<ul>
				<li class="no_child" id="zhr_pt_004"><a  href="javascript:essLink('${serverLinkUrl}zhr_pt_004/default.htm');">학자금 신청/조회</a></li>
				<li class="no_child" id="zhr_be_004"><a  href="javascript:essLink('${serverLinkUrl}zhr_be_004/default.htm');">경조금 신청/조회</a></li>
				<!--  <li class="no_child" id="zhr_be_001"><a  href="javascript:essLink('${serverLinkUrl}zhr_be_001/default.htm');">건강보험증 신청/조회</a></li>-->
				<c:if test="${essuser.essAuthCode eq 'E1'||essuser.essAuthCode eq 'E2'||essuser.essAuthCode eq 'E3'||isSystemAdmin}">
				<li class="no_child" id="zhr_pa_042"><a  href="javascript:essLink('${serverLinkUrl}zhr_pa_042/default.htm');">카페테리아 신청</a></li>
				<!--<li class="no_child" id="zhr_pa_043"><a  href="javascript:essLink('${serverLinkUrl}zhr_pa_043/default.htm');">카페테리아 사용계획</a></li>-->
				</c:if>
				<li class="no_child" id="zhr_pt_006"><a  href="javascript:essLink('${serverLinkUrl}zhr_pt_006/default.htm');">이사비/부임비/파견비 신청/조회</a></li>
				<li class="no_child" id="zhr_be_003"><a  href="javascript:essLink('${serverLinkUrl}zhr_be_003/default.htm');">동호회 신청/조회</a></li>
			</ul>
		</li>
		</c:if>
		</c:if>
		<c:if test="${essuser.essAuthCode != 'E9'}">
		<li class=""><a href="#">증명서</a>
			<ul>
				<li class="no_child" id="zhr_be_002"><a  href="javascript:essLink('${serverLinkUrl}zhr_be_002/default.htm');">재직증명서 신청</a></li>
				<c:if test="${(essuser.essAuthCode ne 'E0')||isSystemAdmin}">
				<li class="no_child" id="zhr_be_005"><a  href="javascript:essLink('${serverLinkUrl}zhr_be_005/default.htm');">근로소득 원천징수영수증<br/>신청</a></li>
				</c:if>
				<li class="no_child" id="zhr_py_007"><a  href="javascript:essLink('${serverLinkUrl}zhr_py_007/default.htm');">근로소득 원천징수영수증<br/>조회</a></li>
			</ul>
		</li>
		</c:if>
	</ul>
</c:if>
<c:if test="${Bigmenu eq 'evaluationMng'}">
	<ul>
		<c:if test="${essuser.essAuthCode eq 'E1'||essuser.essAuthCode eq 'E5'||isSystemAdmin}">
		<li class="liFirst opened licurrent"><a href="#">업적 평가</a>
			<ul>
				<li class="no_child licurrent" id="zhr_hap_001"><a  href="javascript:essLink('${serverLinkUrl}zhr_hap_001/default.htm');">업적 평가 목표설정</a></li>
				<li class="no_child" id="zhr_hap_004"><a  href="javascript:essLink('${serverLinkUrl}zhr_hap_004/default.htm');">업적평가 자기평가</a></li>	
			</ul>
		</li>
		<li class=""><a href="#">역량 평가</a>
			<ul>
				<li class="no_child" id="zhr_hap_011"><a  href="javascript:essLink('${serverLinkUrl}zhr_hap_011/default.htm');">역량평가 목표설정</a></li>

			</ul>
		</li>
		</c:if>
		<c:if test="${essuser.essAuthCode eq 'E2'||essuser.essAuthCode eq 'E3'||essuser.essAuthCode eq 'E6'||essuser.essAuthCode eq 'E7'||isSystemAdmin}">
		<li class=""><a href="#">5급사원 평가</a>
			<ul>
				<li class="no_child" id="zhr_hap_017"><a  href="javascript:essLink('${serverLinkUrl}zhr_hap_017/default.htm');">평가결과 FeedBack/이의제기</a></li>
	
			</ul>
		</li>
		</c:if>
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
				<li class="no_child licurrent" id="zhr_pd_002"><a  href="javascript:essLink('${serverLinkUrl}zhr_pd_002/default.htm');">직무 프로파일 조회</a></li>
				</c:if>
				<c:if test="${essuser.essAuthCode eq 'E1'||essuser.essAuthCode eq 'E5'||isSystemAdmin}">
				<li class="no_child" id="zhr_pd_003"><a  href="javascript:essLink('${serverLinkUrl}zhr_pd_003/default.htm');">직무 KPI 조회</a></li>
				</c:if>	
				<c:if test="${(essuser.essAuthCode ne 'E8')||isSystemAdmin}">
				<li class="no_child" id="zhr_pd_001"><a  href="javascript:essLink('${serverLinkUrl}zhr_pd_001/default.htm');">역량 카탈로그 조회</a></li>
				</c:if>
				<c:if test="${(essuser.essAuthCode ne 'E4')||isSystemAdmin}">
				<li class="no_child" id="zhr_pa_036"><a  href="javascript:essLink('${serverLinkUrl}zhr_pa_036/default.htm');">승격 Point 조회</a></li>
				</c:if>
				<c:if test="${essuser.essAuthCode eq 'E1'||essuser.essAuthCode eq 'E2'||essuser.essAuthCode eq 'E3'||essuser.essAuthCode eq 'E5'||essuser.essAuthCode eq 'E6'||isSystemAdmin}">
				<li class="no_child" id="zhr_pd_015"><a  href="javascript:essLink('${serverLinkUrl}zhr_pd_015/default.htm');">MR Guardianship(지도사원)</a></li>
				<li class="no_child" id="zhr_pd_036"><a  href="javascript:essLink('${serverLinkUrl}zhr_pd_036/default.htm');">MR Guardianship(후배사원)</a></li>
				</c:if>
			</ul>
		</li>
		<li class=""><a href="#">교육훈련</a>
			<ul>
				<li class="no_child" id="zhr_pd_009"><a  href="javascript:essLink('${serverLinkUrl}zhr_pd_009/default.htm');">교육 신청</a></li>
				<li class="no_child" id="zhr_pd_011"><a  href="javascript:essLink('${serverLinkUrl}zhr_pd_011/default.htm');">교육 신청내역 조회</a></li>
				<li class="no_child" id="zhr_pd_006"><a  href="javascript:essLink('${serverLinkUrl}zhr_pd_006/default.htm');">성취도 평가</a></li>
				<li class="no_child" id="zhr_pd_008"><a  href="javascript:essLink('${serverLinkUrl}zhr_pd_008/default.htm');">반응도 평가</a></li>
				<li class="no_child" id="zhr_pd_004"><a  href="javascript:essLink('${serverLinkUrl}zhr_pd_004/default.htm');">강사 평가</a></li>
				<li class="no_child" id="zhr_pd_013"><a  href="javascript:essLink('${serverLinkUrl}zhr_pd_013/default.htm');">교육이력조회</a></li>
				<li class="no_child" id="zhr_pd_016"><a  href="javascript:essLink('${serverLinkUrl}zhr_pd_016/default.htm');">역량별 교육과정 조회</a></li>
			</ul>
		</li>
		<c:if test="${essuser.essAuthCode eq 'E1'||essuser.essAuthCode eq 'E2'||essuser.essAuthCode eq 'E5'||essuser.essAuthCode eq 'E6'||isSystemAdmin}">
		<li class=""><a href="#">경력개발계획</a>
			<ul>
				<li class="no_child" id="zhr_pd_030"><a  href="javascript:essLink('${serverLinkUrl}zhr_pd_030/default.htm');">역량GAP 확인</a></li>
				<li class="no_child" id="zhr_pd_024"><a  href="javascript:essLink('${serverLinkUrl}zhr_pd_024/default.htm');">중장기 경력개발계획 작성</a></li>
				<li class="no_child" id="zhr_pd_027"><a  href="javascript:essLink('${serverLinkUrl}zhr_pd_027/default.htm');">연간 경력개발계획 작성</a></li>
			</ul>
		</li>
		</c:if>
	</ul>
</c:if>
<c:if test="${Bigmenu eq 'diligenceMng'}">
	<ul>
		<li class="liFirst opened licurrent"><a href="#">근태출장</a>
			<ul>
				<c:if test="${(essuser.essAuthCode ne 'E8')||isSystemAdmin}">
				<li class="no_child licurrent" id="zhr_pt_003"><a  href="javascript:essLink('${serverLinkUrl}zhr_pt_003/default.htm');">휴일 근무 신청/조회</a></li>
				</c:if>
				<li class="no_child" id="zhr_pt_002"><a  href="javascript:essLink('${serverLinkUrl}zhr_pt_002/default.htm');">휴가 신청/조회</a></li>
				<li class="no_child" id="zhr_pt_007"><a  href="javascript:essLink('${serverLinkUrl}zhr_pt_007/default.htm');">월 근태평가 Feedback</a></li>
				<li class="no_child" id="zhr_pt_005"><a  href="javascript:essLink('${serverLinkUrl}zhr_pt_005/default.htm');">연월차 적치 신청/조회</a></li>
				<li class="no_child" id="zhr_pt_010"><a  href="javascript:essLink('${serverLinkUrl}zhr_pt_010/default.htm');">연월차 내역 조회</a></li>
				<li class="no_child" id="zhr_pa_033"><a  href="javascript:essLink('${serverLinkUrl}zhr_pa_033/default.htm');">휴복직 신청/조회</a></li>
				<li class="no_child" id="zhr_pt_008"><a  href="javascript:essLink('${serverLinkUrl}zhr_pt_008/default.htm');">출장 신청/조회</a></li>
			</ul>
		</li>
	</ul>
</c:if>
<c:if test="${Bigmenu eq 'payMng'}">
	<ul>
		<li class="liFirst opened licurrent"><a href="#">급여</a>
			<ul>
				<c:if test="${(essuser.essAuthCode ne 'E4')||isSystemAdmin}">
				<li class="no_child licurrent" id="zhr_py_002"><a  href="javascript:essLink('${serverLinkUrl}zhr_py_002/default.htm');">임금명세서</a></li>
				<li class="no_child" id="zhr_py_004"><a  href="javascript:essLink('${serverLinkUrl}zhr_py_004/default.htm');">급여실적 조회</a></li>
				</c:if>
				<c:if test="${((essuser.essAuthCode ne 'E0') && (essuser.essAuthCode ne 'E4')) ||isSystemAdmin}">
				<li class="no_child" id="zhr_py_003"><a  href="javascript:essLink('${serverLinkUrl}zhr_py_003/default.htm');">연말정산 소득공제 신청</a></li>
				<li class="no_child" id="zhr_py_006"><a  href="javascript:essLink('${serverLinkUrl}zhr_py_006/default.htm');">소득공제 신고서 조회</a></li>
				<li class="no_child" id="zhr_py_001"><a  href="javascript:essLink('${serverLinkUrl}zhr_py_001/default.htm');">대출내역 조회</a></li>
				<li class="no_child" id="zhr_py_008"><a  href="javascript:essLink('${serverLinkUrl}zhr_py_008/default.htm');">중도정산 신청</a></li>
				</c:if>
			</ul>
		</li>
	</ul>
</c:if>
<c:if test="${Bigmenu eq 'personalDivsionMng'}">
	<ul>
		<li class="liFirst opened licurrent"><a href="#">개인별 업무분장</a>
			<ul>
				<li class="no_child licurrent" id="zhr_py_008"><a  href="javascript:essLink('${serverLinkUrl}zhr_pd_033/default.htm');">개인별 업무분장 작성</a></li>
			</ul>
		</li>
	</ul>
</c:if>
<c:if test="${Bigmenu eq 'organogramMng'}">
	<ul>
		<li class="liFirst opened licurrent"><a href="#">조직도</a>
			<ul>
				<li class="no_child licurrent" id="zhr_pa_044"><a  href="javascript:essLink('${serverLinkUrl}zhr_pa_044/default.htm');">조직도</a></li>
			</ul>
		</li>
	</ul>
</c:if>
<c:if test="${Bigmenu eq 'payDiligenceMng'}">
	<ul>
		<li class="liFirst opened licurrent"><a href="#">급여/근태</a>
			<ul>
				<li class="no_child licurrent" id="zhr_py_002"><a  href="javascript:essLink('${serverLinkUrl}zhr_py_002/default.htm?E_PERNR=${essuser.empNo}');">개인임금명세서 조회</a></li>
				<li class="no_child" id="zhr_py_004"><a  href="javascript:essLink('${serverLinkUrl}zhr_py_004/default.htm?E_PERNR=${essuser.empNo}');">급여실적 조회</a></li>
				<li class="no_child" id="zhr_pt_011"><a  href="javascript:essLink('${serverLinkUrl}zhr_pt_011/default.htm?E_PERNR=${essuser.empNo}');">월 근태현황 조회</a></li>
				<li class="no_child" id="zhr_py_003"><a  href="javascript:essLink('${serverLinkUrl}zhr_py_003/default.htm?E_PERNR=${essuser.empNo}');">연말정산 소득공제 신청</a></li>
				<li class="no_child" id="zhr_py_006"><a  href="javascript:essLink('${serverLinkUrl}zhr_py_006/default.htm?E_PERNR=${essuser.empNo}');">소득공제신고서 조회</a></li>
				<li class="no_child" id="zhr_pt_010"><a  href="javascript:essLink('${serverLinkUrl}zhr_pt_010/default.htm');">연월차 내역 조회</a></li>
				<li class="no_child" id="zhr_py_007"><a  href="javascript:essLink('${serverLinkUrl}zhr_py_007/default.htm?E_PERNR=${essuser.empNo}');">근로소득 원천징수영수증<br/>조회</a></li>
			</ul>
		</li>
	</ul>
</c:if>
</div>