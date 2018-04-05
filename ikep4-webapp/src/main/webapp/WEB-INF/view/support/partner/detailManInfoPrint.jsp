<%@ include file="/base/common/taglibs.jsp"%>
<%@ page language="java" errorPage="/common/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8"%>
<c:set var="prefix" value="ui.support.partner.board" />
<c:set var="preSearch" value="ui.ikep4.common.searchCondition" />
<c:set var="preButton" value="ui.lightpack.common.button" />
<c:set var="preList" value="ui.support.partner.manInfoItemList" />
<c:set var="user" value="${sessionScope['ikep.user']}" />
<%pageContext.setAttribute("crlf", "\r\n"); %>
<%pageContext.setAttribute("nbsp", " "); %>


<script type="text/javascript">

var da = (document.all) ? 1 : 0
		var pr = (window.print) ? 1 : 0
		var mac = (navigator.userAgent.indexOf("Mac") != -1);
				
		if (da && !pr && !mac) with (document) {
			writeln('<OBJECT ID="WB" WIDTH="0" HEIGHT="0" CLASSID="clsid:8856F961-340A-11D0-A96B-00C04FD705A2"></OBJECT>');
			writeln('<' + 'SCRIPT LANGUAGE="VBScript!">');
			writeln('Sub window_onunload');
			writeln(' On Error Resume Next');
			writeln(' Set WB = nothing');
			writeln('End Sub');
			writeln('Sub vbPrintPage');
			writeln(' OLECMDID_PRINT = 6');
			writeln(' OLECMDEXECOPT_DONTPROMPTUSER = 2');
			writeln(' OLECMDEXECOPT_PROMPTUSER = 1');
			writeln(' On Error Resume Next');
			writeln(' WB.ExecWB OLECMDID_PRINT, OLECMDEXECOPT_DONTPROMPTUSER');
			writeln('End Sub');
			writeln('<' + '/SCRIPT>');
		}

(function($) {

	
	$(document).ready( function() {
		
		$("a.deleteButton").click(function() {
			
			var url = "<c:url value='/support/partner/partnerCommon/deleteManInfo.do'/>";
			
			if(confirm("삭제하시겠습니까?")){
			
				$.ajax({
					url : url,
					data :{'seqNum': '${manInfoItem.seqNum}',
						   'registerId' : '${manInfoItem.registerId}'},
					type : "get",
					success : function() {
						alert("삭제가 완료되었습니다.");
						
						location.href="<c:url value = '/support/partner/partnerCommon/manInfo.do'/>";
					},
					error : function(event, request, settings) { 
						alert("error");	
					}
				});	
			}
		});
		
		$("#readerListViewButton").click( function() {  
			var url = "<c:url value='/support/partner/partnerBasicInfo/listReaderView.do?id=${manInfoItem.seqNum}&divCode=PI'/>";
			
			iKEP.popupOpen(url , {width:700, height:500});
		});
		
		printWindow();
		
	});
	
	
	
})(jQuery);

function printWindow() 
{
	//factory.printing.paperSize = "A4"
    factory.printing.header = "" //머릿글
	factory.printing.footer = "" //바닥글
	factory.printing.portrait = true //true는 세로 출력, false는 가로 출력
	factory.printing.leftMargin = 5.33 //왼쪽 여백
	factory.printing.topMargin = 22.23 //위쪽 여백
	factory.printing.rightMargin = 5.33 //오른쪽 여백
	factory.printing.bottomMargin = 4.23 //바닥 여백	
	
	factory.printing.Preview();
	setTimeout("javascript:iKEP.closePop()", 100);

}	

</script>	

<object id="factory" style="display:none" classid="clsid:1663ed61-23eb-11d2-b92f-008048fdd814"
        codebase="<c:url value="/Bin/smsx.cab#Version=6,5,439,72"/>">
</object>

<h1 class="none">컨텐츠영역</h1>

<!--tableTop Start-->
<div class="tableTop">
	<div class="tableTop_bgR"></div>
	<h2>
		거래처/인물 정보
	</h2>
	<div class="clear"></div>
</div>
<!--//tableTop End-->

<!--subTitle_1 Start-->
<div class="subTitle_2 noline">
	<h3>거래처 정보</h3>
</div>
<!--//subTitle_1 End-->
<div class="blockDetail" style="border: 2px solid #000000;width:99.6%;">
					<table summary="상담이력 상세조회" >
						<caption></caption>
                        <colgroup>
                             <col width="10%"/>
	                         <col width="15%"/>
	                         <col width="10%"/>
	                         <col width="15%"/>
	                         <col width="10%"/>
	                         <col width="15%"/>
	                         <col width="10%"/>
	                         <col width="15%"/>
                        </colgroup>
						<tbody>
							<tr>
                                <th scope="row">거래처명</th>
								<td colspan="3">${manInfoItem.partnerName}</td>
								<th scope="row">대표자</th>
								<td colspan="3">${manInfoItem.ceoName}</td>
							</tr>
							<tr>
                                <th scope="row">업태</th>
								<td colspan="3">${manInfoItem.category}</td>
								<th scope="row">사업자번호</th>
								<td colspan="3">${manInfoItem.businessNo}</td>
							</tr>
							<tr>
                                <th scope="row">종목</th>
								<td colspan="3">${manInfoItem.sector}</td>
								<th scope="row">법인번호</th>
								<td colspan="3">${manInfoItem.corporationNo}</td>
							</tr>
							<tr>
                                <th scope="row">우편번호</th>
								<td colspan="3">${manInfoItem.zipNo}</td>
								<th scope="row">담당자</th>
								<td colspan="3">${manInfoItem.keyMan}</td>
							</tr>
							<tr>
								<th scope="row">주소</th>
								<td colspan="3">${manInfoItem.address}</td>
								<th scope="row">연락처</th>
								<td colspan="3">${manInfoItem.contacts}</td>
							</tr>
							<tr>
                                <th scope="row">대표전화</th>
								<td colspan="3">${manInfoItem.mainPhone}</td>
								<th scope="row">Fax</th>
								<td colspan="3">${manInfoItem.fax}</td>
							</tr>
							<tr>
								<th scope="row">E-mail</th>
								<td colspan="3">${manInfoItem.email}</td>
								<th scope="row">SAP ID</th>
								<td colspan="3">${manInfoItem.sapId}</td>
							</tr>
							<tr>
								<th scope="row">구분</th>
								<td colspan="3">
								<c:choose>
									<c:when test="${manInfoItem.purpose == 'CR01'}">
									주원료
									</c:when>
									<c:when test="${manInfoItem.purpose == 'CR02'}">
									부원료
									</c:when>
									<c:when test="${manInfoItem.purpose == 'CR03'}">
									기자재
									</c:when>
									<c:when test="${manInfoItem.purpose == 'CR04'}">
									포장재
									</c:when>
									<c:when test="${manInfoItem.purpose == 'CR05'}">
									펄프판매
									</c:when>
									<c:when test="${manInfoItem.purpose == 'CR07'}">
									펠릿
									</c:when>
									<c:when test="${manInfoItem.purpose == 'CR06'}">
									기타
									</c:when>
									<c:otherwise></c:otherwise>
								</c:choose>
								</td>
								<th scope="row">등록자</th>
								<td colspan="3">${manInfoItem.registerName}</td>
							</tr>
							<tr>
								<th scope="row">품목</th>
								<td colspan="3">${manInfoItem.items}</td>
								<th scope="row">등록일</th>
								<td colspan="3"><fmt:formatDate pattern="yyyy.MM.dd" value="${manInfoItem.registDate}"/></td>
							</tr>
                        </tbody>
					</table>
				</div>
				<div class="blockBlank_50px"></div>
<!--blockDetail Start-->
<div class="subTitle_2 noline">
	<h3>인물 정보</h3>
</div>
<div class="blockDetail">
	<table summary="기본정보">
		<caption></caption>
		<colgroup>
			<col width="9%" />
			<col width="12%" />
			<col width="9%" />
			<col width="12%" />
			<col width="11%" />
			<col width="12%" />
			<col width="9%" />
			<col width="12%" />
			<col width="14%" />
		</colgroup>
		<tbody>
			<tr>
				<th scope="row"></span>대표자 성명</th>
				<td>${manInfoItem.ceoName}</td>
				<th scope="row">직위</th>
				<td>${manInfoItem.jobTitle}</td>
				<th scope="row">군경력</th>
				<td>${manInfoItem.army}</td>
				<th scope="row">출신지역</th>
				<td>${manInfoItem.nativeArea}</td>
				<td rowspan="5" class="textCenter">
				<c:choose>
				<c:when test="${manInfoItem.sajinSys == null || manInfoItem.sajinSys ==''}">
					<img src="<c:url value='/base/images/common/noimage_110x90.gif'/>" title="인물 사진" width="110" height="90"/>
				</c:when>				 
				<c:otherwise>
					<img src="<c:url value='/support/fileupload/downloadFile.do'/>?fileId=${manInfoItem.sajinSys}" title="인물 사진" width="110" height="90" />
				</c:otherwise>
				</c:choose>
				</td>
			</tr>
			<tr>
				<th scope="row">전화</th>
				<td>${manInfoItem.phone}</td>
				<th scope="row">휴대전화</th>
				<td>${manInfoItem.cellPhone}</td>
				<th scope="row">생년월일</th>
				<td>${manInfoItem.birthday}</td>
				<th scope="row">종교</th>
				<td>${manInfoItem.religion}</td>
			</tr>
			<tr>
				<th scope="row">이메일</th>
				<td colspan="3">${manInfoItem.ceoEmail}</td>
				<th scope="row">흡연여부</th>
				<td>${manInfoItem.cigarette}</td>
				<th scope="row">취미</th>
				<td>${manInfoItem.hobby}</td>
			</tr>
			<tr>
				<th scope="row">주소</th>
				<td colspan="5">${manInfoItem.homeAddress}</td>
				<th scope="row">주량</th>
				<td>${manInfoItem.drink}</td>
			</tr>
			<tr>
				<th scope="row">출신고교</th>
				<td>${manInfoItem.nativeHighschool}</td>
				<th scope="row">출신대학</th>
				<td>${manInfoItem.nativeUniversity}</td>
				<th scope="row">출신교(기타)</th>
				<td colspan="3">${manInfoItem.nativeEtcSchool}</td>
			</tr>
		</tbody>
	</table>
</div>
<!--//blockDetail End-->

<div class="blockBlank_10px"></div>

<!--subTitle_2 Start-->
<div class="subTitle_2 noline">
	<h3>경력</h3>
</div>
<!--//subTitle_2 End-->

<!--blockDetail Start-->
<div class="blockDetail">
	<table summary="경력">
		<caption>경력</caption>
		<colgroup>
			<col width="30%" />
			<col width="30%" />
			<col width="40%" />
		</colgroup>
		<tbody>
			<tr>
				<th scope="row" class="textCenter">주요경력</th>
				<th scope="row" class="textCenter">이전경력</th>
				<th scope="row" class="textCenter">조직 내 주요 업무</th>
			</tr>

			<c:choose>
				<c:when test="${fn:length(manCareer) eq '0'}">
					<tr>
						<td colspan="3" class="emptyRecord"><ikep4j:message
								pre='${preList}' key='career.emptyRecord' /></td>
					</tr>
				</c:when>
				<c:otherwise>
					<c:forEach var="manCareer" items="${manCareer}" varStatus="status">
						<tr>
							<td>${manCareer.mainCareer}</td>
							<td>${manCareer.preCareer}</td>
							<td>${manCareer.mainBusiness}</td>
						</tr>
					</c:forEach>
				</c:otherwise>
			</c:choose>
		</tbody>
	</table>
	<table>
		<caption></caption>
		<colgroup>
			<col width="30%" />
			<col width="30%" />
			<col width="40%" />
		</colgroup>
		<tbody>
			<tr>
				<th scope="row" class="textCenter">입사일</th>
				<th scope="row" class="textCenter">퇴사일</th>
				<th scope="row" class="textCenter">부서이동</th>
			</tr>
			<c:forEach var="manCareer" items="${manCareer}" varStatus="status">
			<c:if test="${status.index==0}">
				<tr>
					<td>${manCareer.joinDate}</td>
					<td>${manCareer.leaveDate}</td>
					<td>${manCareer.moveDate}</td>
				</tr>
			</c:if>
			</c:forEach>
		</tbody>
	</table>
</div>
<!--//blockDetail End-->
<div class="blockBlank_10px"></div>

<!--subTitle_2 Start-->
<div class="subTitle_2 noline">
	<h3>가족사항</h3>
</div>
<!--//subTitle_2 End-->

<!--blockDetail Start-->
<div class="blockDetail">
	<table summary="가족사항">
		<caption>가족사항</caption>
		<colgroup>
			<col width="50%" />
			<col width="50%" />
		</colgroup>
		<tbody>
			<tr>
				<th scope="row" class="textCenter">관계</th>
				<th scope="row" class="textCenter">성명</th>
			</tr>

			<c:choose>
				<c:when test="${fn:length(manFamily) eq '0'}">
					<tr>
						<td colspan="2" class="emptyRecord"><ikep4j:message
								pre='${preList}' key='family.emptyRecord' /></td>
					</tr>
				</c:when>
				<c:otherwise>
					<c:forEach var="manFamily" items="${manFamily}" varStatus="status">
						<tr>
							<td class="textCenter">${manFamily.familyRelation}</td>
							<td class="textCenter">${manFamily.familyName}</td>
						</tr>
					</c:forEach>
				</c:otherwise>
			</c:choose>
		</tbody>
	</table>
</div>
<!--//blockDetail End-->
<div class="blockBlank_10px"></div>

<!--subTitle_2 Start-->
<div class="subTitle_2 noline">
	<h3>성격 및 특이사항</h3>
</div>
<!--//subTitle_2 End-->

<!--blockDetail Start-->
<div class="blockDetail">
	<table summary="성격 및 특이사항">
		<caption></caption>
		<colgroup>
			<col width="100%" />
		</colgroup>
		<tbody>
			<c:choose>
				<c:when test="${manInfoItem.etc eq ''}">
					<tr>
						<td colspan="2" class="emptyRecord"><ikep4j:message
								pre='${preList}' key='emptyRecord' /></td>
					</tr>
				</c:when>
				<c:otherwise>

					<tr>
					<c:set var ="etc2" value ="${fn:replace(manInfoItem.etc, crlf, '<br/>')}"  />
						<td>${fn:replace(etc2, nbsp, "&nbsp;")}</td>
					</tr>

				</c:otherwise>
			</c:choose>
		</tbody>
	</table>
</div>
<!--//blockDetail End-->


</div>
