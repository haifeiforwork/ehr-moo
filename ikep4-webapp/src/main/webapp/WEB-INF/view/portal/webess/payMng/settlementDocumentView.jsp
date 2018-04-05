<%@ include file="/base/common/taglibs.jsp"%>
<%@ page language="java" errorPage="/common/error.jsp"  pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<script type="text/javascript" src="<c:url value="/base/js/jquery/jquery-1.6.2.min.js"/>"></script>
<script type="text/javascript">
(function($){
	$(document).ready(function(){
		$("a[name=btnWritingTipPop]").click(function(event){
			event.preventDefault();
			
			$("#popupForm").html("");
			
			var target = "writingTip";
			var param = "menubar=no, toolbar=no, location=no, status=no, scrollbars=yes, width=500px, height=650px, top=100px, left=200px, resizable=no";
			
			var popup = window.open("", target, param);
			
			$("#popupForm").html("");
			
			$("#popupForm").append("<input type=\"hidden\" name=\"helpType\" value=\""+$(this).data("type")+"\"/>");
			
			$("#popupForm").attr("action", "<c:url value='/portal/moorimess/payMng/writingTipPop.do'/>");
			$("#popupForm").attr("target", target);
			$("#popupForm").submit();
			
			popup.focus();
		});
		
		$("#btnPrint").click(function(event){
			event.preventDefault();
			
			$("div.button_box").hide();
			document.execCommand("print", false, null);
			//window.print();
			$("div.button_box").show();
		});
		
		alert("반드시 상단 도움말을 참조하여 프린트 설정 후 출력하여 주세요.");
	});
})(jQuery);
</script>
<link rel="stylesheet" type="text/css" href="<c:url value="/base/css/ess/ep_button.css"/>" />
<style>
a { color:#666; text-decoration:none; }
a:hover, a:active, a:focus { color:#999;  text-decoration:underline;}
.f_title { font-size:12px; font-weight:bold;  color:#e07a0c;}

body{   color:#000000; FONT-FAMILY: "돋움", "굴림", "Arial", "seoul", "Helvetica", "sans-serif","Tahoma"; font-size:11px; margin-top:0px; margin-left:0px; margin-right:0px; margin-bottom:0px }
td, th{  color : #000000; FONT-FAMILY: "돋움", "굴림", "Arial", "seoul", "Helvetica", "sans-serif","Tahoma"; font-size : 11px ; LINE-HEIGHT: 13pt }
stex{  color : #000000; FONT-FAMILY: "돋움", "굴림", "Arial", "seoul", "Helvetica", "sans-serif","Tahoma"; font-size : 10px ;  }
.T01_0{ BACKGROUND-COLOR: #FFFFFF; }
.T01_2{ BACKGROUND-COLOR: #E3EAEE; PADDING-LEFT: 5px; PADDING-RIGHT: 5px; BORDER-RIGHT: #ffffff 1px solid; BORDER-TOP: #ffffff 1px solid; color: #000000; } /* 테이블헤더 */
.T01_6{ BACKGROUND-COLOR: #DEE7EB; PADDING-LEFT: 5px; PADDING-RIGHT: 5px;;BORDER-RIGHT: #ffffff 1px solid; BORDER-TOP: #ffffff 1px solid; color: #000000; }
.TBL{ border-collapse:collapse; }
.line { border:1px solid #000000; }
.outline { border:2px solid #000000; }
.G { background-color:#EAEAEA; }
.F { color:#333333; }
.C { text-align:center; }
.L { text-align:left; padding-left:2px; }
.R { text-align:right; padding-right:2px; }
.P3 { padding:3px; }
.bline{ font-size: 12px; border:1 solid #000000; font-family:"돋움"; color:#616161 ;Text-align:left ; }
.line_bottom{ border:1px solid #000000; border-bottom:2px solid #000000; }
table.middle td { line-height:16px; }
</style>
<div id="wrap">
	<c:choose>
		<c:when test="${resultMap.EX_OCK ne 'X'}">
			<div class="f_title">※ 지금은 조회기간이 아닙니다.</div>
		</c:when>
		<c:otherwise>
			<div class="button_box">
				<a href="#" class="button_img01" id="btnPrint"><span>인쇄</span></a>
				<a href="#" class="button_img01" name="btnWritingTipPop" data-type="P1"><span>도움말(I.E 7)</span></a>
				<a href="#" class="button_img01" name="btnWritingTipPop" data-type="P2"><span>도움말(I.E 8)</span></a>
			</div>
			
			<br/>
			<table width="750" border="1" class="TBL outline" style="border-style:dashed" cellspacing="3" cellpadding="3">
				<tr align="center">
					<td valign="center">
						<b style="font-size:16pt;">2017년 근로소득·세액 연말정산 근로자 제출 서류 표지</b>
					</td>
				</tr>
			</table>
			<br/>
			<table width="750" height="30" border="0" cellpadding="0" cellspacing="0" class="TBL outline">
				<tr align="center">
					<td width="1%" height="1"></td>
					<td width="14%" height="1"></td>
					<td width="16%" height="1"></td>
					<td width="17%" height="1"></td>
					<td width="17%" height="1"></td>
					<td width="17%" height="1"></td>
					<td width="17%" height="1"></td>
				</tr>
				<tr>
					<td></td>
					<td colspan="6">
						<br/>
						2017년 귀속 근로소득·세액에 대한 연말정산을 수행할 시기가 다가왔습니다 <br/>
						첨부하는 연말정산 안내문을 참조하여 소득/세액공제신고서를 작성 및 관련 증빙서류와 함께 제출해주시기 바랍니다.<br/>
						<br/>
						연말정산 결과 근로소득세액의 산정은 임직원 여러분이 작성하신 내용과 제출하신 증빙서류를 기초로 하여 반영되오니, 임직원 여러분은 향후 과다공제로 인한 가산세 등의 불이익을 당하지 않도록 사전안내문을 신중하게 검토하시고 소득 및 세액공제를 신청해주시기 바랍니다.
						<br/>
						 <b> ▣ 제출서류 </b><br/>
						&nbsp;&nbsp;(1) 근로자 제출 서류 표지<br/>
						&nbsp;&nbsp;(2) 소득·세액신고서<br/>
						&nbsp;&nbsp;(3) 다음 공제를 받는 경우 해당 명세서<br/>
						&nbsp;&nbsp;&nbsp;&nbsp; - 소득공제 : 월세액, 거주자간 주택임차차입금 원리금상환액, 주택마련저축, 장기집합투자증권저축<br/>
						&nbsp;&nbsp;&nbsp;&nbsp; - 세액공제 : 연금계좌(퇴직연금, 연금저축), 의료비, 기부금<br/>
						&nbsp;&nbsp;(4) 증빙서류 (소득공제/세액공제 등)<br/>
						&nbsp;&nbsp;(5) 종전근무지 근로소득원천징수영수증 (해당자만)<br/>
						<br/>
						<%--  <b> ▣ 제출기한 : <c:out value="${resultMap.EX_YEAR}"/> 년 <c:out value="${resultMap.EX_MON_END}"/>월 <c:out value="${resultMap.EX_DAY_END}"/>일 까지 </b><br/> --%>
						 <b> ▣ 제출기한 : 2018 년 1월 26일 까지 </b><br/>
						<br/>
						 <b> ▣ 제출장소 : 각 공장 운영부 / HR운영팀 </b><br/>
						<br/>
						 *. 본 Page를 소득·세액공제신고서의 앞면에 "꼭" 첨부하여 제출해 주세요.<br/>
						 *. 부서/사번/성명/연락처/e-MAIL 을 필히 기재하시기 바랍니다.<br/>
						 *. 관련 증빙서류는 국세청 연말정산 간소화 서비스(www.yesone.go.kr) 또는 해당 기관 등에서 발급하는 소득공제용 영수증 등을 활용하시기 바랍니다.<br/>
						<br/>
					</td>
				</tr>
				<tr>
					<td class="line C" colspan="2">부서</td>
					<td class="line C"><c:out value="${resultMap.EX_PER_INFO.ZORGTX}"/></td>
					<td class="line C">사번</td>
					<td class="line C"><c:out value="${resultMap.EX_PER_INFO.PERNR}"/></td>
					<td class="line C">이름</td>
					<td class="line C"><c:out value="${resultMap.EX_PER_INFO.ENAME}"/></td>
				</tr>
				<tr>
					<td class="line C" colspan="2">연락처</td>
					<td class="line C" colspan="2"><c:out value="${resultMap.EX_PER_INFO.ZCELLP}"/></td>
					<td class="line C">E-mail</td>
					<td class="line C" colspan="2"><c:out value="${resultMap.EX_PER_INFO.ZEMAIL}"/></td>
				</tr>
			</table>
		
			<br/>
			<br style="page-break-after:always"/>
		
			<table width="750" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td valign="bottom" style="padding-left:5px; padding-bottom:1px;">[별지 제37호서식] <개정 2014.3.14></td>
					<td align="right">(1쪽)
				</tr>
			</table>
		
			<table width="750" height="30" border="0" cellpadding="0" cellspacing="0" class="TBL outline">
				<tr align="center">
					<td width="15" height="1"></td>
					<td width="30" height="1"></td>
					<td width="72" height="1"></td>
					<td width="30" height="1"></td>
					<td width="30" height="1"></td>
					<td width="30" height="1"></td>
					<td width="30" height="1"></td>
					<td width="30" height="1"></td>
					
					<td width="44" height="1"></td>
					<td width="44" height="1"></td>
					<td width="44" height="1"></td>
					<td width="44" height="1"></td>
					<td width="44" height="1"></td>
					<td width="44" height="1"></td>
					<td width="44" height="1"></td>
					<td width="44" height="1"></td>
					<td width="44" height="1"></td>
					<td width="44" height="1"></td>
					<td width="43" height="1"></td>
				</tr>
				<tr align="center" valign="bottom">
					<td colspan="19"><b style="font-size:10pt;">소득·세액 공제신고서/근로소득자 소득·세액 공제신고서(2017년 소득에 대한 연말정산용)</b></td>
				</tr>
				<tr>
					<td height="1" colspan="19"></td>
				</tr>
				<tr>
					<td valign="top" align="center">※</td>
					<td valign="top" colspan="18">
						근로소득자는 신고서에 소득·세액 공제증빙서류를 첨부하여 원천징수의무자(소속 회사 등)에게 제출하며, 원천징수의무자는
						신고서 및 첨부서류를 확인하여 근로소득 세액계산을 하고 근로소득자에게 즉시 근로소득원천징수영수증을 발급하여야
						합니다. 연말정산 시 근로소득자에게 환급이 발생하는 경우 원천징수의무자는 근로소득자에게 환급세액을 지급하여야 합니다.
					</td>
				</tr>
				<tr>
					<td colspan="3" class="line C">소득자 성명</td>
					<td colspan="6" class="line C"><c:out value="${resultMap.EX_PER_INFO.ENAME}"/>(<c:out value="${resultMap.EX_PER_INFO.PERNR}"/>)</td>
					<td colspan="3" class="line C">주민등록번호</td>
					<td colspan="10" class="line C"><c:out value="${resultMap.EX_PER_INFO.ZREGNO}"/></td>
				</tr>
				<tr>
					<td colspan="3" class="line C">근무처 명칭</td>
					<td colspan="6" class="line C"><c:out value="${resultMap.EX_PER_INFO.NAME}"/></td>
					<td colspan="3" class="line C">사업자등록번호</td>
					<td colspan="10" class="line C"><c:out value="${fn:substring(resultMap.EX_PER_INFO.STCD2, 0, 3)}"/>-<c:out value="${fn:substring(resultMap.EX_PER_INFO.STCD2, 3, 5)}"/>-<c:out value="${fn:substring(resultMap.EX_PER_INFO.STCD2, 5, 10)}"/></td>
				</tr>
				<tr>
					<td colspan="3" class="line C">세대주 여부</td>
					<c:choose>
						<c:when test="${resultMap.EX_TAX.HSHLD eq 'X'}">
							<td colspan="6" class="line C">세대주 ▣ , 세대원 □</td>
						</c:when>
						<c:otherwise>
							<td colspan="6" class="line C">세대주 □ , 세대원 ▣</td>
						</c:otherwise>
					</c:choose>
					<td colspan="3" class="line C">국 적</td>
					<td colspan="10" class="line C">대한민국 ( 국적 코드 :     )</td>
				</tr>
				<tr>
					<td colspan="3" class="line C">근무기간</td>
					<td colspan="6" class="line C"><c:out value="${fn:substring(resultMap.EX_PER_INFO.ZBEG01, 0, 4)}"/>.<c:out value="${fn:substring(resultMap.EX_PER_INFO.ZBEG01, 4, 6)}"/>.<c:out value="${fn:substring(resultMap.EX_PER_INFO.ZBEG01, 6, 8)}"/>&nbsp;~&nbsp;<c:out value="${resultMap.EX_YEAR}"/>.12.31</td>
					<td colspan="3" class="line C">감면기간</td>
					<td colspan="10" class="line C">           ~           </td>
				</tr>
				<tr>
					<td colspan="3" class="line C">거주 구분</td>
					<td colspan="6" class="line C">거주자 ▣ , 비거주자 □</td>
					<td colspan="3" class="line C">거주지국</td>
					<td colspan="10" class="line C">대한민국 ( 거주지국 코드 :     )</td>
				</tr>
				<tr>
					<td colspan="3" class="line C">인적공제 항목<br/>변동 여부</td>
					<td colspan="16" class="line C">
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					<c:choose>
						<c:when test="${resultMap.EX_TAX.PDCID eq 'X'}">
							전년과 동일 □ , 변동 ▣
						</c:when>
						<c:otherwise>
							전년과 동일 ▣ , 변동 □
						</c:otherwise>
					</c:choose>
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					※ 인적공제 항목이 전년과 동일한 경우에도 증명서류를 제출하시기 바랍니다.
					</td>
				</tr>
		
				<c:set var="rows" value="${fn:length(resultMap.ET_LST) + 3 + 2 + 1}"/>
		
				<c:if test="${rows < 20}">
					<c:set var="rows" value="20"/>
				</c:if>
		
				<tr>
					<td rowspan="${rows}" class="line C">
						<p>Ⅰ.<br/>인<br/>적<br/>공<br/>제<br/><br/>및<br/><br/>소<br/>득<br/>공<br/>제<br/><br/>명<br/>세</p>
					</td>
					<td colspan="7" class="line C">인적공제 항목</td>
					<td colspan="11" class="line C">각종 소득·세액공제 항목</td>
				</tr>
				<tr>
					<td class="line C">관계<br/>코드</td>
					<td class="line C">성 명</td>
					<td colspan="2" class="line C">기본<br/>공제</td>
					<td class="line C">경로<br/>우대</td>
					<td class="line C">출산<br/>입양</td>
					<td rowspan="2" class="line C">자료<br/>구분</td>
					<td colspan="3" class="line C">보험료<br/>(건강보험<br/>료등 포함)</td>
					<td rowspan="2" class="line C">의료비</td>
					<td rowspan="2" class="line C">교육비</td>
					<td colspan="5" class="line C">신용카드 등<br/>사용액공제</td>
					<td rowspan="2" class="line C">기부금</td>
				</tr>
				<tr>
					<td class="line C">내ㆍ외<br/>국인</td>
					<td class="line C">주민등록<br/>번 호</td>
					<td class="line C">부녀자</td>
					<td class="line C">한부모</td>
					<td class="line C">장애인</td>
					<td class="line C">6세<br/>이하</td>
					<td class="line C">건강·<br/>고용 등</td>
					<td class="line C">보장성</td>
					<td class="line C">장애인<br/>전용<br/>보장성</td>
					<td class="line C">신용<br/>카드 등</td>
					<td class="line C">직불<br/>카드 등</td>
					<td class="line C">현금<br/>영수증</td>
					<td class="line C">전통<br/>시장</td>
					<td class="line C">대중<br/>교통</td>
				</tr>
				<!-- 상단합계시작 -->
				<c:forEach items="${resultMap.ET_SUM}" var="result">
					<c:choose>
						<c:when test="${!empty result.GUBUN}">
							<tr>
								<td rowspan="2" colspan="2" class="line C"><c:out value="${result.ZNAME}"/><br/>(다자녀 : <c:out value="${resultMap.EX_CHCNT}"/> 명)</td>
								<td class="line C" colspan="2"></td>
								<td class="line C"></td>
								<td class="line C"><font style="font-size:7pt;"><c:out value="${resultMap.EX_CNT_KDBSL}"/></font></td>
								<td class="line C"><font style="font-size:7pt;">국세청</font></td>
								<td class="line R"><font style="font-size:7pt;"></font></td>
								<td class="line R"><font style="font-size:7pt;"><fmt:formatNumber value="${result.INSAMT}" groupingUsed="true"/></font></td>
								<td class="line R"><font style="font-size:7pt;"><fmt:formatNumber value="${result.INSAMT_D}" groupingUsed="true"/></font></td>
								<td class="line R"><font style="font-size:7pt;"><fmt:formatNumber value="${result.MEDAMT}" groupingUsed="true"/></font></td>
								<td class="line R"><font style="font-size:7pt;"><fmt:formatNumber value="${result.EDUAMT}" groupingUsed="true"/></font></td>
								<td class="line R"><font style="font-size:7pt;"><fmt:formatNumber value="${result.CREAMT}" groupingUsed="true"/></font></td>
								<td class="line R"><font style="font-size:7pt;"><fmt:formatNumber value="${result.CKDAMT}" groupingUsed="true"/></font></td>
								<td class="line R"><font style="font-size:7pt;"><fmt:formatNumber value="${result.CASAMT}" groupingUsed="true"/></font></td>
								<td class="line R"><font style="font-size:7pt;"><fmt:formatNumber value="${result.CCREAMT}" groupingUsed="true"/></font></td>
								<td class="line R"><font style="font-size:7pt;"><fmt:formatNumber value="${result.PCREAMT}" groupingUsed="true"/></font></td>
								<td class="line R"><font style="font-size:7pt;"><fmt:formatNumber value="${result.DONAMT}" groupingUsed="true"/></font></td>
							</tr>
						</c:when>
						<c:otherwise>
							<tr>
								<td class="line C"></td>
								<td class="line C"></td>
								<td class="line C"></td>
								<td class="line C"><font style="font-size:7pt;"><c:out value="${resultMap.EX_CHCNT6}"/></font></td>
								<td class="line C"><font style="font-size:7pt;">기타</font></td>
								<td class="line R"><font style="font-size:7pt;"><fmt:formatNumber value="${resultMap.EX_TAX.ZSUM31}" groupingUsed="true"/></font></td>
								<td class="line R"><font style="font-size:7pt;"><fmt:formatNumber value="${result.INSAMT}" groupingUsed="true"/></font></td>
								<td class="line R"><font style="font-size:7pt;"><fmt:formatNumber value="${result.INSAMT_D}" groupingUsed="true"/></font></td>
								<td class="line R"><font style="font-size:7pt;"><fmt:formatNumber value="${result.MEDAMT}" groupingUsed="true"/></font></td>
								<td class="line R"><font style="font-size:7pt;"><fmt:formatNumber value="${result.EDUAMT}" groupingUsed="true"/></font></td>
								<td class="line R"><font style="font-size:7pt;"><fmt:formatNumber value="${result.CREAMT}" groupingUsed="true"/></font></td>
								<td class="line R"><font style="font-size:7pt;"><fmt:formatNumber value="${result.CKDAMT}" groupingUsed="true"/></font></td>
								<td class="line R G"><font style="font-size:7pt;"><fmt:formatNumber value="${result.CASAMT}" groupingUsed="true"/></font></td>
								<td class="line R"><font style="font-size:7pt;"><fmt:formatNumber value="${result.CCREAMT}" groupingUsed="true"/></font></td>
								<td class="line R"><font style="font-size:7pt;"><fmt:formatNumber value="${result.PCREAMT}" groupingUsed="true"/></font></td>
								<td class="line R"><font style="font-size:7pt;"><fmt:formatNumber value="${result.DONAMT}" groupingUsed="true"/></font></td>
							</tr>
						</c:otherwise>
					</c:choose>
				</c:forEach>
				
				<!-- 상단합계끝 -->
				<!-- -------------------------------------------
				* 인적공제리스트 시작
				-------------------------------------------- -->
				<c:forEach items="${resultMap.ET_LST}" var="result">
					<c:choose>
						<c:when test="${!empty result.GUBUN}">
							<tr>
								<td class="line C"><font style="font-size:7pt;"><c:out value="${result.DPTYP}"/><font></td>
								<td height="30" class="line C"><font style="font-size:8pt;"><c:out value="${result.ZNAME}"/></font></td>
								<td class="line C" colspan="2"><font style="font-size:7pt;"><c:out value="${result.DPTIDX}"/></font></td>
								<td class="line C"><font style="font-size:7pt;"><c:out value="${result.OLDIDX}"/></font></td>
								<td class="line C"><font style="font-size:7pt;"></font></td>
								<td class="line C"><font style="font-size:7pt;">국세청</font></td>
								<td class="line R"><font style="font-size:7pt;"></font></td>
								<td class="line R"><font style="font-size:7pt;"><fmt:formatNumber value="${result.INSAMT}" groupingUsed="true"/></font></td>
								<td class="line R"><font style="font-size:7pt;"><fmt:formatNumber value="${result.INSAMT_D}" groupingUsed="true"/></font></td>
								<td class="line R"><font style="font-size:7pt;"><fmt:formatNumber value="${result.MEDAMT}" groupingUsed="true"/></font></td>
								<td class="line R"><font style="font-size:7pt;"><fmt:formatNumber value="${result.EDUAMT}" groupingUsed="true"/></font></td>
								<td class="line R"><font style="font-size:7pt;"><fmt:formatNumber value="${result.CREAMT}" groupingUsed="true"/></font></td>
								<td class="line R"><font style="font-size:7pt;"><fmt:formatNumber value="${result.CKDAMT}" groupingUsed="true"/></font></td>
								<td class="line R"><font style="font-size:7pt;"><fmt:formatNumber value="${result.CASAMT}" groupingUsed="true"/></font></td>
								<td class="line R"><font style="font-size:7pt;"><fmt:formatNumber value="${result.CCREAMT}" groupingUsed="true"/></font></td>
								<td class="line R"><font style="font-size:7pt;"><fmt:formatNumber value="${result.PCREAMT}" groupingUsed="true"/></font></td>
								<td class="line R"><font style="font-size:7pt;"><fmt:formatNumber value="${result.DONAMT}" groupingUsed="true"/></font></td>
							</tr>
						</c:when>
						<c:otherwise>
							<tr>
								<td class="line C"><font style="font-size:7pt;"><c:out value="${result.FOREN}"/></font></td>
								<c:choose>
									<c:when test="${result.SUBTY eq '0'}">
										<td class="line C G"><font style="font-size:8pt;">(근로자<br/>본인)</font><br/></td>
									</c:when>
									<c:otherwise>
										<td class="line C G"></td>
									</c:otherwise>
								</c:choose>
								<td class="line C"><font style="font-size:7pt;"><c:out value="${result.WOEIDX}"/></font></td>
								<td class="line C"><font style="font-size:7pt;"><c:out value="${result.SIGPRX}"/></font></td>
								<td class="line C"><font style="font-size:7pt;"><c:out value="${result.HNDCD}"/></font></td>
								<td class="line C"><font style="font-size:7pt;"></font></td>
								<td class="line C"><font style="font-size:7pt;">기타</font></td>
								<td class="line R"><font style="font-size:7pt;"></font></td>
								<td class="line R"><font style="font-size:7pt;"><fmt:formatNumber value="${result.INSAMT}" groupingUsed="true"/></font></td>
								<td class="line R"><font style="font-size:7pt;"><fmt:formatNumber value="${result.INSAMT_D}" groupingUsed="true"/></font></td>
								<td class="line R"><font style="font-size:7pt;"><fmt:formatNumber value="${result.MEDAMT}" groupingUsed="true"/></font></td>
								<td class="line R"><font style="font-size:7pt;"><fmt:formatNumber value="${result.EDUAMT}" groupingUsed="true"/></font></td>
								<td class="line R"><font style="font-size:7pt;"><fmt:formatNumber value="${result.CREAMT}" groupingUsed="true"/></font></td>
								<td class="line R"><font style="font-size:7pt;"><fmt:formatNumber value="${result.CKDAMT}" groupingUsed="true"/></font></td>
								<td class="line R G"></td>
								<td class="line R"><font style="font-size:7pt;"><fmt:formatNumber value="${result.CCREAMT}" groupingUsed="true"/></font></td>
								<td class="line R"><font style="font-size:7pt;"><fmt:formatNumber value="${result.PCREAMT}" groupingUsed="true"/></font></td>
								<td class="line R"><font style="font-size:7pt;"><fmt:formatNumber value="${result.DONAMT}" groupingUsed="true"/></font></td>
							</tr>
						</c:otherwise>
					</c:choose>
				</c:forEach>
				<c:forEach begin="1" end="${resultMap.EX_ADDROW}" step="1">
					<tr>
						<td class="line C"></td>
						<td class="line C"></td>
						<td class="line C" colspan="2"></td>
						<td class="line C"></td>
						<td class="line C"></td>
						<td class="line C"><font style="font-size:7pt;">국세청</font></td>
						<td class="line C"></td>
						<td class="line C"></td>
						<td class="line C"></td>
						<td class="line C"></td>
						<td class="line C"></td>
						<td class="line C"></td>
						<td class="line C"></td>
						<td class="line C"></td>
						<td class="line C"></td>
						<td class="line C"></td>
						<td class="line C"></td>
					</tr>
					<tr>
						<td class="line C"></td>
						<td class="line C"></td>
						<td class="line C"></td>
						<td class="line C"></td>
						<td class="line C"></td>
						<td class="line C"></td>
						<td class="line C"><font style="font-size:7pt;">기타</font></td>
						<td class="line C"></td>
						<td class="line C"></td>
						<td class="line C"></td>
						<td class="line C"></td>
						<td class="line C"></td>
						<td class="line C"></td>
						<td class="line C"></td>
						<td class="line C G"></td>
						<td class="line C"></td>
						<td class="line C"></td>
						<td class="line C"></td>
					</tr>
				</c:forEach>
			</table>
		
			<br style="page-break-after:always"/>
		
			<table width="750" border="0" cellspacing="0" cellpadding="0">
				<tr><td align="right">(2쪽)</tr>
			</table>
		
			<table width="750" border="0" cellpadding="0" cellspacing="0"  class="TBL outline middle">
				<tr align="center">
					<td width="42" height="1"></td>
					<td width="96" height="1"></td>
					<td width="117" height="1"></td>
					<td width="83" height="1"></td>
					<td width="64" height="1"></td>
					<td width="112" height="1"></td>
					<td width="100" height="1"></td>
					<td width="96" height="1"></td>
					<td width="104" height="1"></td>
				</tr>
				<tr align="center">
					<td colspan="2" class="line C">구분</td>
					<td colspan="3" class="line C">지출명세</td>
					<td class="line C">지출구분</td>
					<td class="line C">금 액</td>
					<td class="line C">한도액</td>
					<td class="line C">공제액</td>
				</tr>
				<tr align="center">
					<td rowspan="5" class="line C">Ⅱ.<br/>연금<br/>보험<br/>료<br/>공제</td>
					<td rowspan="5" class="line C">연금보험료<br/><br/>(국민연금,<br/>공무원연금,<br/>군인연금,<br/>교직원연금,<br/>퇴직연금 등)</td>
					<td rowspan="2" class="line C">국민연금보험료</td>
					<td colspan="2" class="line C">종(전)근무지</td>
					<td class="line C">보험료</td>
					<td class="line R"><fmt:formatNumber value="${resultMap.EX_TAX.ZSNPM}" groupingUsed="true"/></td>
					<td class="line C">전액</td>
					<td class="line C">&nbsp;</td>
				</tr>
				<tr align="center">
					<td colspan="2" class="line C">주(현)근무지</td>
					<td class="line C">보험료</td>
					<td class="line R"><fmt:formatNumber value="${resultMap.EX_TAX.NOW_SUM}" groupingUsed="true"/></td>
					<td class="line C">전액</td>
					<td class="line C">&nbsp;</td>
				</tr>
				<tr align="center">
					<td rowspan="2" class="line C">국민연금보험료<br/>외의 연금보험료</td>
					<td colspan="2" class="line C">종(전)근무지</td>
					<td class="line C">보험료</td>
					<td class="line R">0</td>
					<td class="line C">전액</td>
					<td class="line C">&nbsp;</td>
				</tr>
				<tr align="center">
					<td colspan="2" class="line C">주(현)근무지</td>
					<td class="line C">보험료</td>
					<td class="line R">0</td>
					<td class="line C">전액</td>
					<td class="line C">&nbsp;</td>
				</tr>
		
				<c:set var="ZSUM_RET" value="0"/>
				<c:set var="ZSUM_RET" value="${resultMap.EX_TAX.RETPE + resultMap.EX_TAX.FPERN}"/>
		
				<tr align="center">
					<td colspan="3" class="line C"><b>연금보험료 계</b></td>
					<td class="line C G"></td>
					<td class="line R"><fmt:formatNumber value="${resultMap.EX_TAX.ZSUM}" groupingUsed="true"/></td>
					<td class="line C G"></td>
					<td class="line C"></td>
				</tr>
				<tr align="center">
					<td rowspan="17" class="line C">Ⅲ.<br/>특<br/><br/>별<br/><br/>공<br/><br/>제</td>
					<td rowspan="5" class="line C">보험료</td>
					<td rowspan="2" class="line C">국민건강보험<br/>(노인장기요양보험<br/> 포함)</td>
					<td colspan="2" class="line C">종(전)근무지</td>
					<td class="line C">보험료</td>
					<td class="line R"><fmt:formatNumber value="${resultMap.EX_TAX.ZSMED}" groupingUsed="true"/></td>
					<td class="line C">전액</td>
					<td class="line C"></td>
				</tr>
				<tr align="center">
					<td colspan="2" class="line C">주(현)근무지</td>
					<td class="line C">보험료</td>
					<td class="line R"><fmt:formatNumber value="${resultMap.EX_TAX.NOW_4ME}" groupingUsed="true"/></td>
					<td class="line C">전액</td>
					<td class="line C"></td>
				</tr>
				<tr align="center">
					<td rowspan="2" class="line C">고용보험</td>
					<td colspan="2" class="line C">종(전)근무지</td>
					<td class="line C">보험료</td>
					<td class="line R"><fmt:formatNumber value="${resultMap.EX_TAX.ZSEIM}" groupingUsed="true"/></td>
					<td class="line C">전액</td>
					<td class="line C"></td>
				</tr>
				<tr align="center">
					<td colspan="2" class="line C">주(현)근무지</td>
					<td class="line C">보험료</td>
					<td class="line R"><fmt:formatNumber value="${resultMap.EX_TAX.NOW_4UE}" groupingUsed="true"/></td>
					<td class="line C">전액</td>
					<td class="line C"></td>
				</tr>
				<tr align="center">
					<td colspan="3" class="line C"><b>보험료 계</b></td>
					<td class="line C G"></td>
					<td class="line R"><fmt:formatNumber value="${resultMap.EX_TAX.ZSUM31}" groupingUsed="true"/></td>
					<td class="line C G"></td>
					<td class="line C"></td>
				</tr>
				<tr align="center">
					<td rowspan="12" class="line C">주택자금</td>
					<td rowspan="2" class="line C">주택임차 차입금</td>
					<td colspan="2" class="line C">대출기관 차입</td>
					<td rowspan="2" class="line C">원리금상환액</td>
					<td class="line R"><fmt:formatNumber value="${resultMap.EX_TAX.REPAY}" groupingUsed="true"/></td>
					<td rowspan="2"  class="line C">작성방법<br/>참조</td>
					<td class="line C"></td>
				</tr>
				<tr align="center">
					<td colspan="2" class="line C">거주자 차입</td>
					<td class="line R"><fmt:formatNumber value="${resultMap.EX_TAX.INDPL}" groupingUsed="true"/></td>
					<td class="line C"></td>
				</tr>
				<tr align="center">
					<td rowspan="9" class="line C">장기주택저당차입금</td>
					<td colspan="2" rowspan="3" class="line C">2011년 <br/>이전 차입분</td>
					<td class="line C">15년 미만</td>
					<td class="line R"><fmt:formatNumber value="${resultMap.EX_TAX.FPRDO}" groupingUsed="true"/></td>
					<td rowspan="9" class="line C">작성방법<br/>참조</td>
					<td rowspan="9" class="line C"></td>
				</tr>
				<tr align="center">
					<td class="line C">15년~29년</td>
					<td class="line R"><fmt:formatNumber value="${resultMap.EX_TAX.INTTL}" groupingUsed="true"/></td>
				</tr>
				<tr align="center">
					<td class="line C">30년 이상</td>
					<td class="line R"><fmt:formatNumber value="${resultMap.EX_TAX.INSLN}" groupingUsed="true"/></td>
				</tr>
				<tr align="center">
					<td colspan="2" rowspan="2" class="line C">2012년 <br/>이후 차입분<br/>(15년 이상)</td>
					<td class="line C">고정금리or<br/>비거치대출</td>
					<td class="line R"><fmt:formatNumber value="${resultMap.EX_TAX.INTFN}" groupingUsed="true"/></td>
				</tr>
				<tr align="center">
					<td class="line C">기타대출</td>
					<td class="line R"><fmt:formatNumber value="${resultMap.EX_TAX.INTOT}" groupingUsed="true"/></td>
				</tr>
				<tr align="center">
					<td rowspan="4" class="line C">2015년 <br/>이후 차입분</td>
					<td rowspan="3" class="line C">15년<br/>이상</td>
					<td class="line C">고정금리and<br/>비거치대출</td>
					<td class="line R"><fmt:formatNumber value="${resultMap.EX_TAX.INT15A}" groupingUsed="true"/></td>
				</tr>
				<tr align="center">
					<td class="line C">고정금리or<br/>비거치대출</td>
					<td class="line R"><fmt:formatNumber value="${resultMap.EX_TAX.INT15O}" groupingUsed="true"/></td>
				</tr>
				<tr align="center">
					<td class="line C">기타대출</td>
					<td class="line R"><fmt:formatNumber value="${resultMap.EX_TAX.INT15E}" groupingUsed="true"/></td>
				</tr>
				<tr align="center">
					<td class="line C">10년~15년</td>
					<td class="line C">고정금리or<br/>비거치대출</td>
					<td class="line R"><fmt:formatNumber value="${resultMap.EX_TAX.INT10O}" groupingUsed="true"/></td>
				</tr>
				<tr align="center">
					<td colspan="3" class="line C"><b>주택자금 공제액 계</b></td>
					<td class="line C G"></td>
					<td class="line R"><fmt:formatNumber value="${resultMap.EX_TAX.ZSUM41}" groupingUsed="true"/></td>
					<td class="line C G"></td>
					<td class="line C"></td>
				</tr>
				<tr>
					<td rowspan="22" class="line C F">Ⅳ.</br>그</br>밖</br>의</br>공</br>제</td>
					<td colspan="4" class="line C F">개인연금저축(2000년 이전 가입)</td>
					<td class="line C F">납입금액</td>
					<td class="line R F"><fmt:formatNumber value="${resultMap.EX_TAX.FPERP}" groupingUsed="true"/></td>
					<td class="line C F">불입액40%와<br/>72만원</td>
					<td class="line C F">&nbsp;</td>
				</tr>
				<tr>
					<td class="line C F" colspan="4">소기업ㆍ소상공인 공제부금 소득공제</td>
					<td class="line C F">불입금액</td>
					<td class="line R F"><fmt:formatNumber value="${resultMap.EX_TAX.SMBFI}" groupingUsed="true"/></td>
					<td class="line C F">작성방법 참조</td>
					<td class="line C F">&nbsp;</td>
				</tr>
				<tr align="center">
					<td rowspan="4" class="line C">주택마련<br/>저축<br/>소득공제<br/></td>
					<td colspan="3" class="line C">청약저축</td>
					<td class="line C">납입금액</td>
					<td class="line R"><fmt:formatNumber value="${resultMap.EX_TAX.HSSAV}" groupingUsed="true"/></td>
					<td class="line C F">작성방법 참조</td>
					<td class="line C"></td>
				</tr>
				<tr align="center">
					<td colspan="3" class="line C">근로자주택마련저축</td>
					<td class="line C">납입금액</td>
					<td class="line R"></td>
					<td class="line C F">작성방법 참조</td>
					<td class="line C"></td>
				</tr>
				<tr align="center">
					<td colspan="3" class="line C">주택청약종합저축</td>
					<td class="line C">납입금액</td>
					<td class="line R"><fmt:formatNumber value="${resultMap.EX_TAX.MTHSS}" groupingUsed="true"/></td>
					<td class="line C F">작성방법 참조</td>
					<td class="line C"></td>
				</tr>
				
				<c:set var="LV_SUMSS" value="${resultMap.EX_TAX.HSSAV + resultMap.EX_TAX.MTHSS + resultMap.EX_TAX.LHSSV}"/>
		
				<tr align="center">
					<td colspan="3" class="line C"><b>주택마련저축 소득공제 계</b></td>
					<td class="line C G"></td>
					<td class="line R"><fmt:formatNumber value="${LV_SUMSS}" groupingUsed="true"/></td>
					<td class="line C G"></td>
					<td class="line C"></td>
				</tr>
				<tr>
					<td rowspan="13" class="line C F" >신용카드 등<br/>사용액<br/>소득공제<br/></td>
					<td colspan="3" class="line L F">① 신용카드<br/>(전통시장ㆍ대중교통 사용분 제외)</td>
					<td class="line C F">사용금액</td>
					<td class="line R F"><fmt:formatNumber value="${resultMap.EX_TAX.CRDCD}" groupingUsed="true"/></td>
					<td class="line C F G">&nbsp;</td>
					<td class="line C F">&nbsp;</td>
				</tr>
				<tr>
					<td colspan="3" class="line L F">② 직불ㆍ선불카드<br/>(전통시장ㆍ대중교통 사용분 제외)</td>
					<td class="line C F">사용금액</td>
					<td class="line R F"><fmt:formatNumber value="${resultMap.EX_TAX.DRTCD}" groupingUsed="true"/></td>
					<td class="line C F G">&nbsp;</td>
					<td class="line C F">&nbsp;</td>
				</tr>
				<tr>
					<td colspan="3" class="line L F">③ 현금영수증<br/>(전통시장ㆍ대중교통 사용분 제외)</td>
					<td class="line C F">사용금액</td>
					<td class="line R F"><fmt:formatNumber value="${resultMap.EX_TAX.CASHE}" groupingUsed="true"/></td>
					<td class="line C F G">&nbsp;</td>
					<td class="line C F">&nbsp;</td>
				</tr>
				<tr>
					<td colspan="3" class="line L F">④ 전통시장사용분</td>
					<td class="line C F">사용금액</td>
					<td class="line R F"><fmt:formatNumber value="${resultMap.EX_TAX.CCRDCD}" groupingUsed="true"/></td>
					<td class="line C F G">&nbsp;</td>
					<td class="line C F">&nbsp;</td>
				</tr>
				<tr>
					<td colspan="3" class="line L F">⑤ 대중교통이용분</td>
					<td class="line C F">사용금액</td>
					<td class="line R F"><fmt:formatNumber value="${resultMap.EX_TAX.PCRDCD}" groupingUsed="true"/></td>
					<td class="line C F G">&nbsp;</td>
					<td class="line C F">&nbsp;</td>
				</tr>
				<tr>
					<td colspan="3" class="line C"><b>계(①+②+③+④+⑤)</b></td>
					<td class="line C F G">&nbsp;</td>
					<td class="line R F"><fmt:formatNumber value="${resultMap.EX_TAX.ZSUM52}" groupingUsed="true"/></td>
					<td class="line C F G">&nbsp;</td>
					<td class="line C F">&nbsp;</td>
				</tr>
				<%-- <tr>
					<td colspan="3" class="line L F">⑥ 본인 신용카드 등 사용금액(2014년)</td>
					<td class="line C F">사용금액</td>
					<td class="line R F"><fmt:formatNumber value="${resultMap.EX_TAX.SELFCREPP}" groupingUsed="true"/></td>
					<td class="line C F G">&nbsp;</td>
					<td class="line C F">&nbsp;</td>
				</tr>
				<tr>
					<td colspan="3" class="line L F">⑦ 본인 신용카드 등 사용금액(2015년)</td>
					<td class="line C F">사용금액</td>
					<td class="line R F"><fmt:formatNumber value="${resultMap.EX_TAX.SELFCREP}" groupingUsed="true"/></td>
					<td class="line C F G">&nbsp;</td>
					<td class="line C F">&nbsp;</td>
				</tr>
				<tr>
					<td colspan="3" class="line L F">⑧ 본인 추가공제율 사용액(2014년)</td>
					<td class="line C F">사용금액</td>
					<td class="line R F"><fmt:formatNumber value="${resultMap.EX_TAX.SELFTAXPP}" groupingUsed="true"/></td>
					<td class="line C F G">&nbsp;</td>
					<td class="line C F">&nbsp;</td>
				</tr> --%>
				<%-- <tr>
					<td colspan="3" class="line L F">⑨ 본인 추가공제율 사용액(2014년)</td>
					<td class="line C F">사용금액</td>
					<td class="line R F"><fmt:formatNumber value="${resultMap.EX_TAX.SELFTAXP}" groupingUsed="true"/></td>
					<td class="line C F G">&nbsp;</td>
					<td class="line C F">&nbsp;</td>
				</tr> --%>
				<%-- <tr>
					<td colspan="3" class="line L F">⑩ 본인 신용카드 등 사용금액(2015년)</td>
					<td class="line C F">사용금액</td>
					<td class="line R F"><fmt:formatNumber value="${resultMap.EX_TAX.SELFCREN}" groupingUsed="true"/></td>
					<td class="line C F G">&nbsp;</td>
					<td class="line C F">&nbsp;</td>
				</tr> --%>
				<%-- <tr>
					<td colspan="3" class="line L F">⑪ 본인 추가공제율 사용액(2016년 상반기)</td>
					<td class="line C F">사용금액</td>
					<td class="line R F"><fmt:formatNumber value="${resultMap.EX_TAX.SELFTAXM}" groupingUsed="true"/></td>
					<td class="line C F G">&nbsp;</td>
					<td class="line C F">&nbsp;</td>
				</tr> --%>
				<%-- <tr>
					<td colspan="3" class="line L F">⑫ 본인 추가공제율 사용액(2015년 하반기)</td>
					<td class="line C F">사용금액</td>
					<td class="line R F"><fmt:formatNumber value="${resultMap.EX_TAX.SELFTAXN}" groupingUsed="true"/></td>
					<td class="line C F G">&nbsp;</td>
					<td class="line C F">&nbsp;</td>
				</tr> --%>
				<tr>
					<td colspan="4" class="line C F">우리사주 출연금 소득공제</td>
					<td class="line C F">출연금액</td>
					<td class="line R F"><fmt:formatNumber value="${resultMap.EX_TAX.COMST}" groupingUsed="true"/></td>
					<td class="line C F">작성방법 참조</td>
					<td class="line C F">&nbsp;</td>
				</tr>
				<tr>
					<td colspan="4" class="line C F">장기집합투자증권저축</td>
					<td class="line C F">납입금액</td>
					<td class="line R F"><fmt:formatNumber value="${resultMap.EX_TAX.LTAIS}" groupingUsed="true"/></td>
					<td class="line C F">작성방법 참조</td>
					<td class="line C F">&nbsp;</td>
				</tr>
				
				<c:set var="LV_LTSFSUM" value="${resultMap.EX_TAX.LTSF1 + resultMap.EX_TAX.LTSF2 + resultMap.EX_TAX.LTSF3}"/>
		
				<tr>
					<td class="line C F" colspan="4">기타(&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;)</td>
					<td class="line C F">&nbsp;</td>
					<td class="line C F">&nbsp;</td>
					<td class="line C F">&nbsp;</td>
					<td class="line C F">&nbsp;</td>
				</tr>
			</table>
		
			<br style="page-break-after:always"/>
		
			<table width="750" border="0" cellspacing="0" cellpadding="0">
				<tr><td align="right">(3쪽)</tr>
			</table>
		
			<table width="750" border="0" cellpadding="0" cellspacing="0"  class="TBL outline middle">
				<tr align="center">
					<td width="46" height="0"></td>
					<td width="46" height="0"></td>
					<td width="44" height="0"></td>
					<td width="50" height="0"></td>
					<td width="50" height="0"></td>
					<td width="69" height="0"></td>
					<td width="78" height="0"></td>
					<td width="74" height="0"></td>
					<td width="80" height="0"></td>
					<td width="76" height="0"></td>
					<td width="70" height="0"></td>
					<td width="70" height="0"></td>
				</tr>
				<tr align="center">
					<td colspan="2" class="line C">구분</td>
					<td colspan="4" class="line C">세액감면·공제명세</td>
					<td colspan="6" class="line C">세액감면·공제명세</td>
				</tr>
				<tr>
					<td rowspan="34" class="line C F">Ⅴ.</br>세</br>액</br>감면</br>및</br>공</br>제</td>
					<td rowspan="4" class="line C F">세액<br/>감면</td>
					<td rowspan="4" class="line C F">외국인<br/>근로자</td>
					<td class="line C F">입국목적</td>
					<td colspan="8" align="center" class="line" >□ 정부간 협약 &nbsp; &nbsp; □ 기술도입계약 &nbsp; &nbsp;  □「조세특례제한법」상 감면 &nbsp; &nbsp;  □ 조세조약 상 감면</td>
				</tr>
				<tr>
					<td colspan="3" class="line C F">기술도입/근로제공일</td>
					<td class="line C F">&nbsp;</td>
					<td colspan="2" class="line C F">감면기간만료일</td>
					<td colspan="3" class="line C F">&nbsp;</td>
				</tr>
				<tr>
					<td colspan="3" class="line C F">외국인 근로소득 감면</td>
					<td class="line C F">접수일</td>
					<td colspan="2" class="line C F">&nbsp;</td>
					<td class="line C F">제출일</td>
					<td colspan="2" class="line C F">&nbsp;</td>
				</tr>
				<tr>
					<td colspan="3" class="line C F">근로소득 조세조약 상 면제</td>
					<td class="line C F">접수일</td>
					<td colspan="2" class="line C F">&nbsp;</td>
					<td class="line C F">제출일</td>
					<td colspan="2" class="line C F">&nbsp;</td>
				</tr>
				<tr align="center">
					<td rowspan="30" class="line C F">세액<br/>공제</td>
					<td colspan="4" class="line C F">공 제 종 류</td>
					<td colspan="2" class="line C F">명세</td>
					<td class="line C F">한도액</td>
					<td class="line C F">공제대상금액</td>
					<td class="line C F">공제율</td>
					<td class="line C F">공제세액</td>
				</tr>
				<tr align="center">
					<td rowspan="4" class="line C F">연금계좌</td>
					<td colspan="3" class="line C F">과학기술인공제</td>
					<td class="line C F">납입금액</td>
					<td class="line C F">&nbsp;</td>
					<td rowspan="3" class="line C F">작성방법 참조</td>
					<td class="line C F">&nbsp;</td>
					<td rowspan="4" class="line C F">12%<br/>또는<br/>15%</td>
					<td class="line C F">&nbsp;</td>
				</tr>
				<tr align="center">
					<td colspan="3" class="line C F">퇴직연금</td>
					<td class="line C F">납입금액</td>
					<td class="line R"><fmt:formatNumber value="${resultMap.EX_TAX.RETPE}" groupingUsed="true"/></td>
					<td class="line C F">&nbsp;</td>
					<td class="line C F">&nbsp;</td>
				</tr>
				<tr align="center">
					<td colspan="3" class="line C F">연금저축</td>
					<td class="line C F">납입금액</td>
					<td class="line R"><fmt:formatNumber value="${resultMap.EX_TAX.FPERN}" groupingUsed="true"/></td>
					<td class="line C F">&nbsp;</td>
					<td class="line C F">&nbsp;</td>
				</tr>
				<tr align="center">
					<td colspan="3" class="line C F"><b>연금계좌 계</b></td>
					<td class="line C G"></td>
					<td class="line R"><fmt:formatNumber value="${resultMap.EX_TAX.ZSUM53}" groupingUsed="true"/></td>
					<td class="line C G"></td>
					<td class="line C F">&nbsp;</td>
					<td class="line C F">&nbsp;</td>
				</tr>
				<tr align="center">
					<td rowspan="17" class="line C F">특별세액<br/>공제</td>
					<td rowspan="3" class="line C">보험료</td>
					<td colspan="2" class="line C">보장성</td>
					<td class="line C">보험료</td>
					<td class="line R"><fmt:formatNumber value="${resultMap.EX_TAX.FOTIS}" groupingUsed="true"/></td>
					<td class="line C">100만원</td>
					<td class="line C"></td>
					<td class="line C">12%</td>
					<td class="line C">&nbsp;</td>
				</tr>
				<tr align="center">
					<td colspan="2" class="line C">장애인전용보장성</td>
					<td class="line C">보험료</td>
					<td class="line R"><fmt:formatNumber value="${resultMap.EX_TAX.FOHIS}" groupingUsed="true"/></td>
					<td class="line C">100만원</td>
					<td class="line C"></td>
					<td class="line C">15%</td>
					<td class="line C">&nbsp;</td>
				</tr>
				<tr align="center">
					<td colspan="2" class="line C"><b>보험료 계</b></td>
					<td class="line C G"></td>
					<td class="line R"><fmt:formatNumber value="${resultMap.EX_TAX.ZSUM31_2}" groupingUsed="true"/></td>
					<td class="line C G"></td>
					<td class="line C"></td>
					<td class="line C G"></td>
					<td class="line C">&nbsp;</td>
				</tr>
				<tr align="center">
					<td rowspan="3" class="line C">의료비</td>
					<td colspan="2" class="line C">본인ㆍ65세이상ㆍ<br/>장애인ㆍ난임시술비</td>
					<td class="line C">지출액</td>
					<td class="line R"><fmt:formatNumber value="${resultMap.EX_TAX.FMDOH}" groupingUsed="true"/></td>
					<td class="line C">작성방법 참조</td>
					<td class="line C"></td>
					<td rowspan="3" class="line C">15%</td>
					<td class="line C">&nbsp;</td>
				</tr>
				<tr align="center">
					<td colspan="2" class="line C">기타 의료비</td>
					<td class="line C">지출액</td>
					<td class="line R"><fmt:formatNumber value="${resultMap.EX_TAX.FMDED}" groupingUsed="true"/></td>
					<td class="line C">작성방법 참조</td>
					<td class="line C"></td>
					<td class="line C">&nbsp;</td>
				</tr>
				<tr align="center">
					<td colspan="2" class="line C"><b>의료비 계</b></td>
					<td class="line C G"></td>
					<td class="line R"><fmt:formatNumber value="${resultMap.EX_TAX.ZSUM32}" groupingUsed="true"/></td>
					<td class="line C G"></td>
					<td class="line C"></td>
					<td class="line C">&nbsp;</td>
				</tr>
				<tr align="center">
					<td rowspan="6" class="line C">교육비</td>
					<td colspan="2" class="line C">소득자 본인</td>
					<td class="line C">공납금<br/>(대학원포함)</td>
					<td class="line R"><fmt:formatNumber value="${resultMap.EX_TAX.FCEEE}" groupingUsed="true"/></td></td>
					<td class="line C">전액</td>
					<td class="line C"></td>
					<td rowspan="6" class="line C">15%</td>
					<td class="line C">&nbsp;</td>
				</tr>
				<tr align="center">
					<td colspan="2" class="line C">취학 전(${resultMap.EX_TAX.DED_PRE_PER}명)</td>
					<td class="line C">유치원비<br/>ㆍ학원비</td>
					<td class="line R"><fmt:formatNumber value="${resultMap.EX_TAX.ZSUMELE}" groupingUsed="true"/></td>
					<td class="line C">1인당 300만원</td>
					<td class="line C"></td>
					<td class="line C">&nbsp;</td>
				</tr>
				<tr align="center">
					<td colspan="2" class="line C">초ㆍ중ㆍ고(${resultMap.EX_TAX.DED_STU_PER}명)</td>
					<td class="line C">공납금</td>
					<td class="line R"><fmt:formatNumber value="${resultMap.EX_TAX.ZSUMSCH}" groupingUsed="true"/></td>
					<td class="line C">1인당 300만원</td>
					<td class="line C"></td>
					<td class="line C">&nbsp;</td>
				</tr>
				<tr align="center">
					<td colspan="2" class="line C">대학생(${resultMap.EX_TAX.DED_UNI_PER}명)</td>
					<td class="line C">공납금</td>
					<td class="line R"><fmt:formatNumber value="${resultMap.EX_TAX.ZSUMUNI}" groupingUsed="true"/></td>
					<td class="line C">1인당 900만원</td>
					<td class="line C"></td>
					<td class="line C">&nbsp;</td>
				</tr>
				<tr align="center">
					<td colspan="2" class="line C">장애인 (${resultMap.EX_TAX.ded_HND_PER}명)</td>
					<td class="line C">특수교육비</td>
					<td class="line R"><fmt:formatNumber value="${resultMap.EX_TAX.FCEHD}" groupingUsed="true"/></td>
					<td class="line C">전액</td>
					<td class="line C"></td>
					<td class="line C">&nbsp;</td>
				</tr>
				<tr align="center">
					<td colspan="2" class="line C"><b>교육비 계</b></td>
					<td class="line C G"></td>
					<td class="line R"><fmt:formatNumber value="${resultMap.EX_TAX.ZSUM33}" groupingUsed="true"/></td>
					<td class="line C G"></td>
					<td class="line C"></td>
					<td class="line C">&nbsp;</td>
				</tr>
				<%-- <c:choose>
					<c:when test="${resultMap.EX_TAX.POLDN > 100000}">
						<c:set var="POLDN_UNDER" value="100000"/>
						<c:set var="POLDN_UP" value="${resultMap.EX_TAX.POLDN - 100000}"/>
					</c:when>
					<c:otherwise> --%>
						<c:set var="POLDN_UNDER" value="${resultMap.EX_TAX.POLDN}"/>
						<c:set var="POLDN_UP" value="${resultMap.EX_TAX.POLDN_UP}"/>
					<%-- </c:otherwise>
				</c:choose> --%>
		
				<c:set var="POLDN" value=""/>
		
				<tr align="center">
					<td rowspan="5" class="line C">기부금</td>
					<td rowspan="2" class="line C">정치자금<br/>기부금</td>
					<td class="line C">10만원 이하</td>
					<td class="line C">기부금액</td>
					<td class="line R F"><fmt:formatNumber value="${POLDN_UNDER}" groupingUsed="true"/></td>
					<td rowspan="4" class="line C">작성방법<br/>참조</td>
					<td class="line C"> </td>
					<td class="line C">100/110</td>
					<td class="line C">&nbsp;</td>
				</tr>
				<tr align="center">
					<td class="line C">10만원 초과</td>
					<td class="line C">기부금액</td>
					<td class="line R F"><fmt:formatNumber value="${POLDN_UP}" groupingUsed="true"/></td>
					<td class="line C"> </td>
					<td rowspan="4" class="line C">15%<br/>(25%)</td>
					<td class="line C">&nbsp;</td>
				</tr>
				<tr align="center">
					<td colspan="2" class="line C">법정기부금</td>
					<td class="line C">기부금액</td>
					<td class="line R"><fmt:formatNumber value="${resultMap.EX_TAX.FLGDO}" groupingUsed="true"/></td>
					<td class="line C"> </td>
					<td class="line C">&nbsp;</td>
				</tr>
				<tr align="center">
					<td colspan="2" class="line C">지정기부금</td>
					<td class="line C">기부금액</td>
					<td class="line R"><fmt:formatNumber value="${resultMap.EX_TAX.FDSDO}" groupingUsed="true"/></td>
					<td class="line C"> </td>
					<td class="line C">&nbsp;</td>
				</tr>
				<tr align="center">
					<td colspan="2" class="line C"><b>기부금 계</b></td>
					<td class="line C G"></td>
					<td class="line R"><fmt:formatNumber value="${resultMap.EX_TAX.ZSUM42}" groupingUsed="true"/></td>
					<td class="line C G"></td>
					<td class="line C">&nbsp;</td>
					<td class="line C">&nbsp;</td>
				</tr>
				<tr>
					<td colspan="4" rowspan="6" class="line C F">외국납부세액</td>
					<td class="line C F">국외원천소득</td>
					<td colspan="2" class="line C F" width="80">&nbsp;</td>
					<td colspan="2" class="line C G"></td>
					<td class="line C G"></td>
				</tr>
				<tr>
					<td class="line C F">납세액(외화)</td>
					<td colspan="2" class="line R F"><fmt:formatNumber value="${resultMap.EX_TAX.FORCT}" groupingUsed="true"/></td>
					<td colspan="2" class="line C G"></td>
					<td class="line C G"></td>
				</tr>
				<tr>
					<td class="line C F">납세액(원화)</td>
					<td colspan="2" class="line R F"><fmt:formatNumber value="${resultMap.EX_TAX.KORCR}" groupingUsed="true"/></td>
					<td colspan="2" class="line C F">-</td>
					<td class="line c F">&nbsp;</td>
				</tr>
				<tr>
					<td class="line C F">납세국명</td>
					<td colspan="2" class="line C F">${resultMap.EX_TAX.TAXCT}</td>
					<td class="line C F">납부일</td>
		
					<c:choose>
						<c:when test="${resultMap.EX_TAX.GETDT ne '00000000'}">
							<td colspan="2" class="line C F">${resultMap.EX_TAX.GETDT}</td>
						</c:when>
						<c:otherwise>
							<td colspan="2" class="line C F"></td>
						</c:otherwise>
					</c:choose>
				
				</tr>
				<tr>
					<td class="line C F">신청서제출일</td>
		
					<c:choose>
						<c:when test="${resultMap.EX_TAX.PUTDT ne '00000000'}">
							<td colspan="2" class="line C F">${resultMap.EX_TAX.PUTDT}</td>
						</c:when>
						<c:otherwise>
							<td colspan="2" class="line C F"></td>
						</c:otherwise>
					</c:choose>
			
					<td class="line C F">국외근무처</td>
					<td colspan="2" class="line C F">${resultMap.EX_TAX.ABPLA}</td>
				</tr>
				<tr>
					<td class="line C F">근무기간</td>
					<td colspan="2" class="line C F">${resultMap.EX_TAX.ABDUR}</td>
					<td class="line C F">직책</td>
					<td colspan="2" class="line C F">${resultMap.EX_TAX.ABJOB}</td>
				</tr>
				<tr>
					<td colspan="4" class="line C F">주택자금차입금이자세액공제</td>
					<td class="line C F">이자상환액</td>
					<td colspan="2" class="line R F"><fmt:formatNumber value="${resultMap.EX_TAX.FTCHI}" groupingUsed="true"/></td>
					<td class="line C F">30%</td>
					<td colspan="2" class="line C F">&nbsp;</td>
				</tr>
		
				<c:set var="LV_BEGDA">${resultMap.EX_YEAR}0101</c:set>
				<c:set var="LV_ENDDA">${resultMap.EX_YEAR}1231</c:set>
		
				<c:forEach items="${resultMap.ET_RENT}" var="result">
					<c:if test="${result.LV_ENDDA <= LV_ENDDA or result.ET_RCEND >= LV_BEGDA}">
						
						<c:choose>
							<c:when test="${result.ET_RCBEG <= LV_BEGDA}">
								<c:set var="LV_BMNTH" value="1"/>
							</c:when>
							<c:otherwise>
								<c:set var="LV_EMNTH" value="${fn:substring(result.ET_RCBEG, 4, 2)}"/>
							</c:otherwise>
						</c:choose>
		
						<c:choose>
							<c:when test="${result.ET_RCEND >= LV_ENDDA}">
								<c:set var="LV_EMNTH" value="12"/>
							</c:when>
							<c:otherwise>
								<c:set var="LV_EMNTH" value="${fn:substring(result.ET_RCEND, 4, 2)}"/>
							</c:otherwise>
						</c:choose>
		
						<c:set var="LV_MRNTD" value="${resultMap.EX_TAX.MRNTD * (LV_EMNTH - LV_BMNTH)}"/>
					</c:if>
				</c:forEach>
		
				<tr align="center">
					<td colspan="4" class="line C">월세액 세액공제</td>
					<td class="line C">지출액</td>
					<td colspan="2" class="line R"><fmt:formatNumber value="${resultMap.EX_TAX.MRNTD}" groupingUsed="true"/></td>
					<td class="line C">10%</td>
					<td colspan="2" class="line C"></td>
				</tr>
				<tr class="line">
					<td height="50" colspan="12" align="left" class="line" style="padding-bottom:7px; padding-top:7px;">
						신고인은「소득세법」제140조에 따라 위의 내용을 신고하며,<br/><strong>위 내용을 충분히 검토하였고 신고인이 알고 있는 사실
						그대로를 정확하게 적었음을 확인합니다.<br/>
						&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						</strong>${fn:substring(params.toDay, 0, 4)}&nbsp;년 &nbsp;&nbsp;&nbsp;
						${fn:substring(params.toDay, 4, 6)}&nbsp;월 &nbsp;&nbsp;&nbsp;${fn:substring(params.toDay, 6, 8)}&nbsp;일<br/>
						&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						신고인
						&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						&nbsp;&nbsp;${resultMap.EX_PER_INFO.ENAME}&nbsp;&nbsp;(서명 또는 인)
					</td>
				</tr>
				<tr><td colspan="12" class="line L F" >Ⅵ. 추가 제출 서류</td></tr>
				<tr>
					<td colspan="8" class="line L F" >1. 외국인근로자 단일세율적용신청서 제출 여부(○ 또는 ×로 적습니다)</td>
					<td colspan="4" class="line C F">제출 (&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;)</td>
				</tr>
		
				<c:set var="LV_ZSAL1" value="${resultMap.EX_PER_LAST.SAL01 + resultMap.EX_PER_LAST.BON01}"/>
				<c:set var="LV_ZINT1" value="${resultMap.EX_PER_LAST.INT01 + resultMap.EX_PER_LAST.RET01}"/>
		
				<c:set var="LV_ZSAL2" value="${resultMap.EX_PER_LAST.SAL02 + resultMap.EX_PER_LAST.BON02}"/>
				<c:set var="LV_ZINT2" value="${resultMap.EX_PER_LAST.INT02 + resultMap.EX_PER_LAST.RET02}"/>
		
				<c:set var="LV_ZSAL3" value="${resultMap.EX_PER_LAST.SAL03 + resultMap.EX_PER_LAST.BON03}"/>
				<c:set var="LV_ZINT3" value="${resultMap.EX_PER_LAST.INT03 + resultMap.EX_PER_LAST.RET03}"/>
		
				<tr>
					<td colspan="2" rowspan="4" class="line L F">2.종(전)근무지 명세</td>
					<td colspan="2" class="line C F">종전근무지명</td>
					<td colspan="2" class="line C F">사업자등록번호</td>
					<td colspan="2" class="line C F">종(전)급여총액</td>
					<td colspan="2" class="line C F">종(전)결정세액</td>
					<td colspan="2" rowspan="4" class="line L F">종(전)근무지<br/>근로소득원천징수<br/>영수증 제출(&nbsp;&nbsp;)</td>
				</tr>
				<tr>
					<td colspan="2" class="line C F">${resultMap.EX_PER_LAST.COM01}</td>
					<td colspan="2" class="line C F">${resultMap.EX_PER_LAST.BUS01}</td>
					<td colspan="2" class="line R F"><fmt:formatNumber value="${LV_ZSAL1}" groupingUsed="true"/></td>
					<td colspan="2" class="line R F"><fmt:formatNumber value="${LV_ZINT1}" groupingUsed="true"/></td>
				</tr>
				<tr>
					<td colspan="2" class="line C F">${resultMap.EX_PER_LAST.COM02}</td>
					<td colspan="2" class="line C F">${resultMap.EX_PER_LAST.BUS02}</td>
					<td colspan="2" class="line R F"><fmt:formatNumber value="${LV_ZSAL2}" groupingUsed="true"/></td>
					<td colspan="2" class="line R F"><fmt:formatNumber value="${LV_ZINT2}" groupingUsed="true"/></td>
				</tr>
				<tr>
					<td class="line C F" colspan="4" >종전근무지 계</td>
					<td colspan="2" class="line R F"><fmt:formatNumber value="${resultMap.EX_TAX.ZSAL}" groupingUsed="true"/></td>
					<td colspan="2" class="line R F"><fmt:formatNumber value="${resultMap.EX_TAX.ZINT}" groupingUsed="true"/></td>
				</tr>
				<tr>
					<td colspan="7" class="line L F">3. 연금ㆍ저축 등 소득공제 명세서 제출여부<br/>&nbsp;&nbsp;(○ 또는 X로 적습니다)</td>
					<td colspan="5" class="line L F">
						&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						제출 (&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;)<br/>
						※ 퇴직연금,연금저축,주택마련저축 등 소득
						공제를 <br/>&nbsp;&nbsp; 신청한 경우 해당 명세서를 제출하여야 합니다.
					</td>
				</tr>
				<tr>
					<td colspan="7" class="line L F">
						4. 월세액ㆍ비거주자간 주택임차차입금 원리금 상환액 <br/>
						&nbsp;&nbsp;소득공제명세 제출여부(○ 또는 X로 적습니다)
					</td>
					<td colspan="5" class="line L F">
						&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						제출 (&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;)<br/>
						※ 월세액, 거주자간 주택임차차입금 원리금 상환액 소득공제를<br/>&nbsp;&nbsp; 신청한 경우
						해당 명세서를 제출하여야 합니다.
					</td>
				</tr>
				<tr>
					<td colspan="4" class="line L F">5. 그 밖의 추가 제출 서류</td>
					<td colspan="8" class="line C F">
						①의료비지급명세서 (&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;), ②기부금명세서 (&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;),
						③소득공제 증빙서류 (&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;)
					</td>
				</tr>
				<tr>
					<td height="50" colspan="12" class="line" style="padding-bottom:7px; padding-top:7px; padding-left:10px;">
						※ 근로소득자 참고사항<br/>
						&nbsp;&nbsp;1. 종(전)근무지 근로소득을 합산하여 신고하지 아니하는 경우에는 종합소득세 신고를 하여야 하며,	신고하지 아니한 경우 가산세 <br/>
						&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;부과 등 불이익이 따릅니다.<br/>
						&nbsp;&nbsp;2. 현 근무지의 연금보험료ㆍ국민건강보험료 및 고용보험료 등은 신고인이 작성하지 아니하여도 됩니다.<br/>
						&nbsp;&nbsp;3. 공제금액란은 근로소득자가 원천징수의무자에게 제출하는 경우 적지아니할 수 있습니다.
					</td>
				</tr>
			</table>
		
			<br style="page-break-after:always"/>
		
			<c:if test="${!empty resultMap.ET_RETPE or !empty resultMap.ET_PENSAVE or !empty resultMap.ET_CHUNG or !empty resultMap.ET_FUND or !empty resultMap.ET_LTAIS or !empty resultMap.ET_LOANE5 or !empty resultMap.ET_LOANE6 or !empty resultMap.ET_RENT}">
				<!-- -------------------------------------------
				* 4페이지(연금저축공제 명세서)
				-------------------------------------------- -->
				<table width="750" height="30" border="0" cellpadding="0" cellspacing="0" class="TBL outline">
					<tr align="center">
						<td width="180" height="1"></td>
						<td width="180" height="1"></td>
						<td width="130" height="1"></td>
						<td width="130" height="1"></td>
						<td width="130" height="1"></td>
					</tr>
					<tr height="50" align="center" valign="center">
						<td colspan="5"><b style="font-size:12pt;">연금ㆍ저축 등 소득·세액 공제 명세서</b></td>
					</tr>
					<tr>
						<td colspan="5" class="line L" align="right">1. 인적사항</td>
					</tr>
					<tr>
						<td class="line L">①상&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;호</td>
						<td class="line L">${resultMap.EX_PER_INFO.NAME}</td>
						<td class="line L">②사업자등록번호</td>
						<td colspan="2" class="line L">${fn:substring(resultMap.EX_PER_INFO.STCD2, 0, 3)}-${fn:substring(resultMap.EX_PER_INFO.STCD2, 3, 5)}-${fn:substring(resultMap.EX_PER_INFO.STCD2, 5, 10)}</td>
					</tr>
					<tr>
						<td class="line L">③성&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;명</td>
						<td class="line L">${resultMap.EX_PER_INFO.ENAME}(${resultMap.EX_PER_INFO.PERNR})</td>
						<td class="line L">④주민등록번호</td>
						<td colspan="2" class="line L">${resultMap.EX_PER_INFO.ZREGNO}</td>
					</tr>
					
					<tr>
						<td class="line L">⑤주&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;소</td>
						<td colspan="4" class="line L">
							${resultMap.EX_PER_INFO.ZADDRESS}&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							(전화번호 : ${resultMap.EX_PER_INFO.OFPHN})
						</td>
					</tr>
					<tr>
						<td class="line L">⑥사업장소재지</td>
						<td colspan="4" class="line L">
							${resultMap.EX_PER_INFO.ZADDRESS_LINE}&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							(전화번호 : 02-3485-1500)
						</td>
					</tr>
					<tr>
						<td valign="bottom" colspan="5" class="line L" align="right">2. 연금계좌 세액공제</td>
					</tr>
					<tr>
						<td valign="bottom" colspan="5" class="line L" align="right">1) 퇴직연금 계좌<br>* 퇴직연금계좌에 대한 명세를 작성합니다.</td>
					</tr>
					<tr>
						<td class="line C">퇴직연금구분</td>
						<td class="line C">금융기관</td>
						<td class="line C">계좌번호</td>
						<td class="line C">납입금액</td>
						<td class="line C">공제금액</td>
					</tr>
			
					<c:set var="L_PNAME" value=""/>
					
					<c:forEach items="${resultMap.ET_RETPE}" var="result">
						<c:if test="${result.ET_PNSTY ne '92'}">
							<c:forEach items="${resultMap.ET_T7KR5CT}" var="code">
								<c:if test="${code.INFTY eq '0881' and code.SUBTY eq result.EXPTY and code.PNSTY eq result.ET_PNSTY}">
									<c:set var="L_PNAME" value="${code.PNSTX}"/>
								</c:if>
							</c:forEach>
							<tr>
								<td class="line C">${L_PNAME}</td>
								<td class="line C">${result.ET_INSNM}</td>
								<td class="line C">${result.ET_ACCNO}</td>
								<td class="line R"><fmt:formatNumber value="${result.ETNAM}" groupingUsed="true"/></td>
								<td class="line L"></td>
							</tr>
						</c:if>
					</c:forEach>
			
					<tr>
						<td class="line L">&nbsp;</td>
						<td class="line L">&nbsp;</td>
						<td class="line L">&nbsp;</td>
						<td class="line L">&nbsp;</td>
						<td class="line L">&nbsp;</td>
					</tr>
					<tr>
						<td valign="bottom" colspan="5" class="line L" align="right">2) 연금저축 계좌<br>* 연금저축계좌에 대한 명세를 작성합니다.</td>
					</tr>
					<tr>
						<td class="line C">연금저축구분</td>
						<td class="line C">금융기관</td>
						<td class="line C">계좌번호</td>
						<td class="line C">납입금액</td>
						<td class="line C">공제금액</td>
					</tr>
			
					<c:forEach items="${resultMap.ET_PENSAVE}" var="result">
						<c:forEach items="${resultMap.ET_T7KR5CT}" var="code">
							<c:if test="${code.INFTY eq '0881' and code.SUBTY eq result.EXPTY and code.PNSTY eq result.ET_PNSTY}">
								<c:set var="L_PNAME" value="${code.PNSTX}"/>
							</c:if>
						</c:forEach>
			
						<tr>
							<td class="line C">${L_PNAME}</td>
							<td class="line C">${result.ET_INSNM}</td>
							<td class="line C">${result.ET_ACCNO}</td>
							<td class="line R"><fmt:formatNumber value="${result.ETNAM}" groupingUsed="true"/></td>
							<td class="line L"></td>
						</tr>
					</c:forEach>
			
					<c:forEach items="${resultMap.ET_RETPE}" var="result">
						<c:if test="${result.ET_PNSTY eq '92'}">
							<c:set var="L_PNAME" value="연금저축"/>
							<tr>
								<td class="line C">${L_PNAME}</td>
								<td class="line C">${result.ET_INSNM}</td>
								<td class="line C">${result.ET_ACCNO}</td>
								<td class="line R"><fmt:formatNumber value="${result.ETNAM}" groupingUsed="true"/></td>
								<td class="line L"></td>
							</tr>
						</c:if>
					</c:forEach>
					
					<tr>
						<td class="line L">&nbsp;</td>
						<td class="line L">&nbsp;</td>
						<td class="line L">&nbsp;</td>
						<td class="line L">&nbsp;</td>
						<td class="line L">&nbsp;</td>
					</tr>
					<tr>
						<td valign="bottom" colspan="5" class="line L" align="right">3. 주택마련저축 소득공제<br>* 주택마련저축 소득공제에 대한 명세를 작성합니다.</td>
					</tr>
					<tr>
						<td class="line C">저축구분</td>
						<td class="line C">금융기관</td>
						<td class="line C">계좌번호</td>
						<td class="line C">납입금액</td>
						<td class="line C">공제금액</td>
					</tr>
					
					<c:forEach items="${resultMap.ET_CHUNG}" var="result">
			
						<c:forEach items="${resultMap.ET_T7KR5CT}" var="code">
							<c:if test="${code.INFTY eq '0881' and code.SUBTY eq result.EXPTY and code.PNSTY eq result.ET_PNSTY}">
								<c:set var="L_PNAME" value="${code.PNSTX}"/>
							</c:if>
						</c:forEach>
			
						<tr>
							<td class="line C">${L_PNAME}</td>
							<td class="line C">${result.ET_INSNM}</td>
							<td class="line C">${result.ET_ACCNO}</td>
							<td class="line R"><fmt:formatNumber value="${result.ETNAM}" groupingUsed="true"/></td>
							<td class="line L"></td>
						</tr>
					</c:forEach>
				
					<tr>
						<td class="line L">&nbsp;</td>
						<td class="line L">&nbsp;</td>
						<td class="line L">&nbsp;</td>
						<td class="line L">&nbsp;</td>
						<td class="line L">&nbsp;</td>
					</tr>
					<tr>
						<td valign="bottom" colspan="5" class="line L" align="right">4. 장기집합투자증권저축 소득공제<br>* 장기집합투자증권저축 소득공제에 대한 명세서를 작성합니다.</td>
					</tr>
					<tr>
						<td colspan="2" class="line C">금융회사 등</td>
						<td class="line C">계좌번호</td>
						<td class="line C">납입금액</td>
						<td class="line C">공제금액</td>
					</tr>
			
					<c:forEach items="${resultMap.ET_LTAIS}" var="result">
						
						<c:forEach items="${resultMap.ET_T7KR5CT}" var="code">
							<c:if test="${code.INFTY eq '0881' and code.SUBTY eq result.EXPTY and code.PNSTY eq result.ET_PNSTY}">
								<c:set var="L_PNAME" value="${code.PNSTX}"/>
							</c:if>
						</c:forEach>
			
						<tr>
							<td colspan="2" class="line C">${result.ET_INSNM}</td>
							<td class="line C">${result.ET_ACCNO}</td>
							<td class="line R"><fmt:formatNumber value="${result.ETNAM}" groupingUsed="true"/></td>
							<td class="line L"></td>
						</tr>
					</c:forEach>
				
					<tr>
						<td colspan="2" class="line L">&nbsp;</td>
						<td class="line L">&nbsp;</td>
						<td class="line L">&nbsp;</td>
						<td class="line L">&nbsp;</td>
					</tr>
					<tr class="line">
						<td height="50" colspan="5" align="left" class="line" style="padding-bottom:7px; padding-top:7px;">
					※ 작성방법<br>
					1. 퇴직연금ㆍ연금저축ㆍ주택마련저축ㆍ장기집합투자증권저축 공제를 받는 소득자에 대해서는 해당 소득·세액 공제에 대한 명세를 작성하여야 합니다.<br>
					2. 퇴직연금계좌에서 퇴직연금구분란은 퇴직연금(DC,IRP)·과학기술인공제회로 구분하여 적습니다.<br>
					3. 연금저축계좌에서 연금저축구분란은 개인연금저축과 연금저축으로 구분하여 적습니다.<br>
					4. 주택마련저축 공제의 저축구분란은 청약저축, 주택청약종합저축, 장기주택마련저축 및 근로자주택마련저축으로 구분하여 적습니다.<br>
					5. 공제금액란은 근로소독자가 적지 아니할 수 있습니다.<br>
					</td>
					</tr>
				</table>
				<br style="page-break-after:always"/>
				<table width="750" height="30" border="0" cellpadding="0" cellspacing="0" class="TBL outline">
					<tr align="center">
						<td width="90" height="1"></td>
						<td width="90" height="1"></td>
						<td width="90" height="1"></td>
						<td width="80" height="1"></td>
						<td width="80" height="1"></td>
						<td width="80" height="1"></td>
						<td width="80" height="1"></td>
						<td width="80" height="1"></td>
						<td width="80" height="1"></td>
					</tr>
					<tr height="50" align="center" valign="center">
						<td colspan="9"><b style="font-size:12pt;">[<c:if test="${resultMap.EX_TAX.MRNTD > 0}">X</c:if>] 월세액 ㆍ[<c:if test="${resultMap.EX_TAX.INDPL > 0}">X</c:if>] 거주자 간 주택임차차입금 원리금 상환액 소득ㆍ세액공제 명세서</b></td>
					</tr>
					<tr>
						<td colspan="9" class="line L" align="right">1. 인적사항</td>
					</tr>
					<tr>
						<td class="line L">①상&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;호</td>
						<td colspan="3" class="line L">${resultMap.EX_PER_INFO.NAME}</td>
						<td class="line L" colspan="2">②사업자등록번호</td>
						<td colspan="3" class="line L">${fn:substring(resultMap.EX_PER_INFO.STCD2, 0, 3)}-${fn:substring(resultMap.EX_PER_INFO.STCD2, 3, 5)}-${fn:substring(resultMap.EX_PER_INFO.STCD2, 5, 10)}</td>
					</tr>
					<tr>
						<td class="line L">③성&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;명</td>
						<td colspan="3" class="line L">${resultMap.EX_PER_INFO.ENAME}(${resultMap.EX_PER_INFO.PERNR})</td>
						<td class="line L" colspan="2">④주민등록번호</td>
						<td colspan="3" class="line L">${resultMap.EX_PER_INFO.ZREGNO}</td>
					</tr>
					
					<tr>
						<td class="line L">⑤주&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;소</td>
						<td colspan="8" class="line L">
							${resultMap.EX_PER_INFO.ZADDRESS}&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							(전화번호 : ${resultMap.EX_PER_INFO.OFPHN})
						</td>
					</tr>
					<tr>
						<td class="line L">⑥사업장소재지</td>
						<td colspan="8" class="line L">
							${resultMap.EX_PER_INFO.ZADDRESS_LINE}&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							(전화번호 : 02-3485-1500)
						</td>
					</tr>
					<tr>
						<td valign="bottom" colspan="9" class="line L" align="right">2. 월세액 세액공제 명세
						<br>※⑨주택유형 구분코드 - 단독주택:1, 다가구:2, 다세대주택:3, 연립주택:4, 아파트:5, 오피스텔:6, 고시원:7, 기타:8
						<br>※⑫계약서상 임대차계약기간 - 개시일과 종료일은 예시와 같이 기재 (예시) 2013.01.01.
						</td>
					</tr>
					<tr>
						<td class="line C" rowspan="2">⑦ 임대인 성명(상호)</td>
						<td class="line C" rowspan="2">⑧ 주민등록번호(사업자번호)</td>
						<td class="line C" rowspan="2">⑨ 주택유형</td>
						<td class="line C" rowspan="2">⑩주택계약면적(m2)</td>
						<td class="line C" rowspan="2">⑪ 임대차계약서 상 주소지</td>
						<td class="line C" colspan="2">⑫ 계약서상 임대차 계약기간</td>
						<td class="line C" rowspan="2">⑬ 연간 월세액(원)</td>
						<td class="line C" rowspan="2">⑭ 세액공제액(원)</td>
					</tr> 
					<tr>
						<td class="line C">게시일</td>
						<td class="line C">종료일</td>
					</tr>
					<c:forEach items="${resultMap.ET_RENT}" var="result">
					<tr>
						<td class="line C">${result.ET_LDNAM}</td>
						<td class="line C">${result.ET_LDREG}</td>
						<td class="line C">${result.HOUTY}</td>
						<td class="line C">${result.HOSTX}</td>
						<td class="line C">${result.ET_ADDRE}</td>
						<td class="line C">
						<fmt:parseDate var="dateString1" value="${result.ET_RCBEG}" pattern="yyyyMMdd" />
						<fmt:formatDate value="${dateString1}" pattern="yyyy.MM.dd" />
						</td>
						<td class="line C">
						<fmt:parseDate var="dateString2" value="${result.ET_RCEND}" pattern="yyyyMMdd" />
						<fmt:formatDate value="${dateString2}" pattern="yyyy.MM.dd" />
						</td>
						<td class="line R"><fmt:formatNumber value="${result.ETOAM}" groupingUsed="true"/></td>
						<td class="line R"></td>
					</tr>
					</c:forEach>
					<tr>
						<td class="line C">&nbsp;</td>
						<td class="line C">&nbsp;</td>
						<td class="line C">&nbsp;</td>
						<td class="line C">&nbsp;</td>
						<td class="line C">&nbsp;</td>
						<td class="line C">&nbsp;</td>
						<td class="line C">&nbsp;</td>
						<td class="line R">&nbsp;</td>
						<td class="line R">&nbsp;</td>
					</tr>
					<tr>
						<td valign="bottom" colspan="9" class="line L" align="right">3. 거주자 간 주택임차차입금 원리금 상환액 소득공제 명세</td>
					</tr>
					<tr>
						<td valign="bottom" colspan="9" class="line L" align="right"> 1) 금전소비대차 계약내용</td>
					</tr>
					<tr>
						<td class="line C" rowspan="2">⑮ 대주(貸主)</td>
						<td class="line C" rowspan="2">⑯주민등록번호</td>
						<td class="line C" rowspan="2" colspan="2">⑰ 금전소비대차 계약기간</td>
						<td class="line C" rowspan="2">⑱ 차입금 이자율</td>
						<td class="line C" colspan="3">원리금 상환액</td>
						<td class="line C" rowspan="2">㉒공제금액</td>
					</tr> 
					<tr>
						<td class="line C">⑲ 계</td>
						<td class="line C">⑳ 원금</td>
						<td class="line C">㉑이자</td>
					</tr>
					<c:forEach items="${resultMap.ET_LOANE6}" var="result">
					<tr>
						<td class="line C">${result.ET_LDNAM}</td>
						<td class="line C">${result.ET_LDREG}</td>
						<td class="line C" colspan="2">
						<fmt:parseDate var="dateString1" value="${result.ET_RCBEG}" pattern="yyyyMMdd" />
						<fmt:formatDate value="${dateString1}" pattern="yyyy.MM.dd" />~
						<fmt:parseDate var="dateString2" value="${result.ET_RCEND}" pattern="yyyyMMdd" />
						<fmt:formatDate value="${dateString2}" pattern="yyyy.MM.dd" />
						</td>
						<td class="line C">${result.ET_INRAT}</td>
						<td class="line R"><fmt:formatNumber value="${result.ETNAM+result.ETOAM}" groupingUsed="true"/></td>
						<td class="line R"><fmt:formatNumber value="${result.ETNAM}" groupingUsed="true"/></td>
						<td class="line R"><fmt:formatNumber value="${result.ETOAM}" groupingUsed="true"/></td>
						<td class="line R"></td>
					</tr>
					</c:forEach>
					<tr>
						<td class="line C">&nbsp;</td>
						<td class="line C">&nbsp;</td>
						<td class="line C" colspan="2">&nbsp;</td>
						<td class="line C">&nbsp;</td>
						<td class="line R">&nbsp;</td>
						<td class="line R">&nbsp;</td>
						<td class="line R">&nbsp;</td>
						<td class="line R">&nbsp;</td>
					</tr>
					<tr>
						<td valign="bottom" colspan="9" class="line L" align="right"> 2) 임대차 계약내용
						<br>※ ㉕주택유형 구분코드 - 단독주택:1, 다가구:2, 다세대주택:3, 연립주택:4, 아파트:5, 오피스텔:6, 고시원:7, 기타:8
						<br>※ ㉘계약서상 임대차계약기간 - 개시일과 종료일은 예시와 같이 기재 (예시) 2013.01.01.
						</td>
					</tr>
					<tr>
						<td class="line C" rowspan="2">㉓임대인 성명(상호)</td>
						<td class="line C" rowspan="2">㉔주민등록번호(사업자번호)</td>
						<td class="line C" rowspan="2">㉕주택유형</td>
						<td class="line C" rowspan="2">㉖주택계약면적(m2)</td>
						<td class="line C" rowspan="2" colspan="2">㉗임대차계약서 상 주소지</td>
						<td class="line C" colspan="2">㉘계약서 상 임대차 계약기간</td>
						<td class="line C" rowspan="2">㉙전세보증금(원)</td>
					</tr> 
					<tr>
						<td class="line C">게시일</td>
						<td class="line C">종료일</td>
					</tr>
					<c:forEach items="${resultMap.ET_LOANE5}" var="result">
					<tr>
						<td class="line C">${result.ET_LDNAM}</td>
						<td class="line C">${result.ET_LDREG}</td>
						<td class="line C">${result.HOUTY}</td>
						<td class="line C">${result.HOSTX}</td>
						<td class="line C" colspan="2">${result.ET_ADDRE}</td>
						<td class="line C">
						<fmt:parseDate var="dateString1" value="${result.ET_RCBEG}" pattern="yyyyMMdd" />
						<fmt:formatDate value="${dateString1}" pattern="yyyy.MM.dd" />
						</td>
						<td class="line C">
						<fmt:parseDate var="dateString2" value="${result.ET_RCEND}" pattern="yyyyMMdd" />
						<fmt:formatDate value="${dateString2}" pattern="yyyy.MM.dd" />
						</td>
						<td class="line R"><fmt:formatNumber value="${result.ETNAM}" groupingUsed="true"/></td>
					</tr>
					</c:forEach>
					<tr>
						<td class="line C">&nbsp;</td>
						<td class="line C">&nbsp;</td>
						<td class="line C">&nbsp;</td>
						<td class="line C">&nbsp;</td>
						<td class="line C" colspan="2">&nbsp;</td>
						<td class="line C">&nbsp;</td>
						<td class="line C">&nbsp;</td>
						<td class="line R">&nbsp;</td>
					</tr>
					<tr class="line">
						<td height="50" colspan="9" align="left" class="line" style="padding-bottom:7px; padding-top:7px;">
					※ 작성방법<br>
					1. 월세액 공제나 거주자 간 주택임차자금 차입금 원리금 상환액 공제를 받는 근로소득자에 대해서는 해당 소득공제에 대한 명세를 작성해야 합니다.<br>
					2. 해당 임대차 계약별로 연간 합계인 월세액ㆍ원리금상환액과 공제금액을 적으며, 공제금액이 "0"인 경우에는 적지 않습니다.<br>
					3. ⑨,㉕주택유형은 단독주택, 다가구주택, 다세대주택, 연립주택, 아파트, 오피스텔, 고시원, 기타 중에서 해당되는 주택유형의 구분코드를 적습니다.<br>
					4. ㉙전세보증금은 과세기간 종료일(12.31.) 현재의 전세보증금을 적습니다.<br>
						</td>
					</tr>
				</table>
			</c:if>
		</c:otherwise>
	</c:choose>
	

	
</div>
</form>
<form id="popupForm" name="popupForm" method="post">
</form>
<script type="text/javascript">
try{
	document.domain = 'moorim.co.kr';
}catch(err){
	var console = window.console || {log:function(){}};
	console.log(err);
}
</script>