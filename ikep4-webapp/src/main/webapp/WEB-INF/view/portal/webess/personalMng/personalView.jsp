<%@ include file="/base/common/taglibs.jsp"%>
<%@ page language="java" errorPage="/common/error.jsp"  pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>

<c:set var="exHeader" value="${resultHeader.EX_HEADER }"/>

<c:set var="exLst1" value="${result.EX_LST1 }"/>
<c:set var="exLst2" value="${result.EX_LST2 }"/>
<c:set var="exLst3" value="${result.EX_LST3 }"/>

<c:set var="etKonfe" value="${resultCode.ET_KONFE }"/>
<c:set var="etZzbir" value="${resultCode.ET_ZZBIR }"/>
<c:set var="etZzmar" value="${resultCode.ET_ZZMAR }"/>
<c:set var="etAstat" value="${resultCode.ET_ASTAT }"/>
<c:set var="etZzbld" value="${resultCode.ET_ZZBLD }"/>

<c:set var="headerKeySet" value="${keySet.headerKeySet }"/>
<c:set var="lst1KeySet"   value="${keySet.lst1KeySet }"/>
<c:set var="lst2KeySet"   value="${keySet.lst2KeySet }"/>
<c:set var="lst3KeySet"   value="${keySet.lst3KeySet }"/>

<script type="text/javascript">
(function($){
	$(document).ready(function(){

		$("#photo").click(function(){
			var url = "<c:url value="${exHeader.PHOTO}"/>";
			var param = "menubar=no, toolbar=no, location=no, status=no, scrollbars=yes, width=367px, height=460px, top=200px, left=300px, resizable=no";
			var popup = window.open(url, null, param);

			popup.focus();
		});

		$("#btnModify").click(function(){
			$("#personalForm").find("input[name=jspType]").remove();
			$("#personalForm").append("<input type=\"hidden\" name=\"jspType\" value=\"regView\"/>");

			$("#personalForm").append("<span class=\"exHeaderInfo\"></span>");
			$("#personalForm").append("<span class=\"exLst1Info\"></span>");
			$("#personalForm").append("<span class=\"exLst2Info\"></span>");
			$("#personalForm").append("<span class=\"exLst3Info\"></span>");

			<c:forEach items="${headerKeySet}" var="key">
			$("#personalForm").find("span.exHeaderInfo").append("<input type=\"hidden\" name=\"HEADER_<c:out value='${key}'/>\" value=\"<c:out value='${exHeader[key]}'/>\"/>");
			</c:forEach>

			<c:forEach items="${lst1KeySet}" var="key">
			$("#personalForm").find("span.exLst1Info").append("<input type=\"hidden\" name=\"LST1_<c:out value='${key}'/>\" value=\"<c:out value='${exLst1[key]}'/>\"/>");
			</c:forEach>

			<c:forEach items="${lst2KeySet}" var="key">
			$("#personalForm").find("span.exLst2Info").append("<input type=\"hidden\" name=\"LST2_<c:out value='${key}'/>\" value=\"<c:out value='${exLst2[key]}'/>\"/>");
			</c:forEach>

			<c:forEach items="${lst3KeySet}" var="key">
			$("#personalForm").find("span.exLst3Info").append("<input type=\"hidden\" name=\"LST3_<c:out value='${key}'/>\" value=\"<c:out value='${exLst3[key]}'/>\"/>");
			</c:forEach>

			$("#personalForm").attr("action", "<c:url value='/portal/moorimess/personalMng/personalView.do'/>");
			$("#personalForm").submit();
		});

		$.initSet = function(){

			var myCar = "<c:out value="${exLst1.MYCAR}"/>";

			if(myCar == "X"){
				$("#myCar").attr("checked", true);
			}
		};

		$.initSet();
	});
})(jQuery);;
</script>
<style type="text/css">
.cursor-pointer{cursor:pointer;}
</style>
<form id="personalForm" name="personalForm" method="post">
<div id="wrap">
	<h1><img src="<c:url value="/base/images/ess/bullet_01.png"/>" alt=""/>개인신상 조회</h1>

	<div class="table_box">
		<p class="f_title" style="padding-bottom:10px">기본정보</p>

		<span style="display:inline-block; .display:inline; *zoom:1"  class="list02">
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
				<caption></caption>
				<colgroup>
					<col width="110px"/>
					<col width="10%"/>
					<col width="10%"/>
					<col width="10%"/>
					<col width="12%"/>
					<col width="10%"/>
					<col width="12%"/>
					<col width="10%"/>
					<col width="*"/>
				</colgroup>
				<tbody>
					<tr>
						<td rowspan="5" class="list02_td">
							<img src="<c:url value="${exHeader.PHOTO }"/>" class="cursor-pointer" width="100px" height="125px" border="0" id="photo"/>
						</td>
						<th class="list02_td_bg">사원번호</th>
						<td class="list02_td"><c:out value="${exHeader.PERNR }"/></td>
						<th class="list02_td_bg">직급/직위</th>
						<td class="list02_td"><c:out value="${exHeader.JTEXT }"/></td>
						<th class="list02_td_bg">회사</th>
						<td class="list02_td"><c:out value="${exHeader.NAME1 }"/></td>
						<th class="list02_td_bg">사업장</th>
						<td class="list02_td"><c:out value="${exHeader.LCTTX }"/></td>
					</tr>
					<tr>
						<th class="list02_td_bg">성명</th>
						<td class="list02_td"><c:out value="${exHeader.ENAME }"/></td>
						<th class="list02_td_bg">직책</th>
						<td class="list02_td"><c:out value="${exHeader.COTXT }"/></td>
						<th class="list02_td_bg">소속</th>
						<td colspan="3" class="list02_td"><c:out value="${exHeader.ORGTX }"/></td>
					</tr>
					<tr>
						<th class="list02_td_bg">직무</th>
						<td class="list02_td"><c:out value="${exHeader.STLTX }"/></td>
						<th class="list02_td_bg">포지션</th>
						<td class="list02_td"><c:out value="${exHeader.PLSTX }"/></td>
						<th class="list02_td_bg">사원구분</th>
						<td colspan="3" class="list02_td"><c:out value="${exHeader.PKTXT }"/></td>
					</tr>
					<tr>
						<th class="list02_td_bg">그룹입사일</th>
						<td class="list02_td">
							<fmt:parseDate var="dateString" value="${exHeader.A1DAT}" pattern="yyyyMMdd" />
							<fmt:formatDate value="${dateString}" pattern="yyyy-MM-dd" />
						</td>
						<th class="list02_td_bg">회사입사일</th>
						<td class="list02_td">
							<fmt:parseDate var="dateString" value="${exHeader.ENTRY}" pattern="yyyyMMdd" />
							<fmt:formatDate value="${dateString}" pattern="yyyy-MM-dd" />
						</td>
						<th class="list02_td_bg">생년월일</th>
						<td colspan="3" class="list02_td"><c:out value="${exHeader.BIRTH }"/> <c:out value="${exHeader.AGE }"/></td>
					</tr>
					<tr>
						<th class="list02_td_bg">최종승격(진)일</th>
						<td class="list02_td">
							<fmt:parseDate var="dateString" value="${exHeader.DATUM}" pattern="yyyyMMdd" />
							<fmt:formatDate value="${dateString}" pattern="yyyy-MM-dd" />
						</td>
						<th class="list02_td_bg">근속기간</th>
						<td class="list02_td"><c:out value="${exHeader.CONYR }"/></td>
						<th class="list02_td_bg">재직상태</th>
						<td colspan="3" class="list02_td"><c:out value="${exHeader.STAT1_T }"/></td>
					</tr>
				</tbody>
			</table>
		</span>
	</div>

	<!-- 상단버튼-->
	<div class="button_box">
		<a href="#" class="button_img01" id="btnModify"><span>수정</span></a>
	</div>

	<div class="table_box">
		<p class="f_title" style="padding-bottom:10px">개인신상</p>
		<span style="display:inline-block; .display:inline; *zoom:1"  class="list02">
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
				<caption></caption>
				<colgroup>
					<col width="12%"/>
					<col width="20%"/>
					<col width="12%"/>
					<col width="20%"/>
					<col width="12%"/>
					<col width="*"/>
				</colgroup>
				<tbody>
					<tr>
						<th class="list02_td_bg">주민등록번호</th>
						<td class="list02_td"><c:out value="${exLst1.REGNOX }"/></td>
						<th class="list02_td_bg">생년월일</th>
						<td class="list02_td">
							<c:forEach items="${etZzbir }" var="result">
								<c:if test="${result.KEY eq exLst1.ZZBIR}">
									(<c:out value="${result.VALUE }"/>)
								</c:if>
							</c:forEach>
							<fmt:parseDate var="dateString" value="${exLst1.GBDAT}" pattern="yyyyMMdd" />
							<fmt:formatDate value="${dateString}" pattern="yyyy-MM-dd" />
						</td>
						<th class="list02_td_bg">결혼기념일</th>
						<td class="list02_td">
							<c:forEach items="${etZzmar }" var="result">
								<c:if test="${result.KEY eq exLst1.ZZMAR}">
									(<c:out value="${result.VALUE }"/>)
								</c:if>
							</c:forEach>
							<fmt:parseDate var="dateString" value="${exLst1.FAMDT}" pattern="yyyyMMdd" />
							<fmt:formatDate value="${dateString}" pattern="yyyy-MM-dd" />
						</td>
					</tr>
					<tr>
						<th class="list02_td_bg">자택전화</th>
						<td class="list02_td"><c:out value="${exLst1.TELNR }"/></td>
						<th class="list02_td_bg">사내연락처</th>
						<td class="list02_td"><c:out value="${exLst1.ZCPTEL }"/></td>
						<th class="list02_td_bg">이동전화</th>
						<td class="list02_td"><c:out value="${exLst1.ZHPNO }"/></td>
					</tr>
					<tr>
						<th class="list02_td_bg">주거사항</th>
						<td class="list02_td">
							<c:forEach items="${etAstat }" var="result">
								<c:if test="${result.KEY eq exLst1.HOUSE}">
									<c:out value="${result.VALUE }"/>
								</c:if>
							</c:forEach>
						</td>
						<th class="list02_td_bg">취미/특기</th>
						<td class="list02_td"><c:out value="${exLst1.ZZHOB }"/></td>
						<th class="list02_td_bg">종교</th>
						<td class="list02_td">
							<c:forEach items="${etKonfe }" var="result">
								<c:if test="${result.KEY eq exLst1.KONFE}">
									<c:out value="${result.VALUE }"/>
								</c:if>
							</c:forEach>
						</td>
					</tr>
					<tr>
						<th class="list02_td_bg">차종소유여부</th>
						<td class="list02_td">
							<label for="myCar"><input type="checkbox" id="myCar" disabled/>&nbsp;소유시 체크</label>
						</td>
						<th class="list02_td_bg">차량번호</th>
						<td class="list02_td"><c:out value="${exLst1.CARNO }"/></td>
						<th class="list02_td_bg">사내 이메일</th>
						<td class="list02_td"><c:out value="${exLst1.ZEMAIL }"/></td>
					</tr>
					<tr>
						<td colspan="4" class="list02_td"></td>
						<th class="list02_td_bg">사외 이메일</th>
						<td class="list02_td"><c:out value="${exLst1.ZEMAIL2 }"/></td>
					</tr>
				</tbody>
			</table>
		</span>
	</div>

	<div class="table_box">
		<p class="f_title" style="padding-bottom:10px">신체사항</p>
		<span style="display:inline-block; .display:inline; *zoom:1"  class="list02">
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
				<caption></caption>
				<colgroup>
					<col width="12%"/>
					<col width="20%"/>
					<col width="12%"/>
					<col width="20%"/>
					<col width="12%"/>
					<col width="*"/>
				</colgroup>
				<tbody>
					<tr>
						<th class="list02_td_bg">신장</th>
						<td class="list02_td"><c:out value="${exLst2.NMF01X }"/>cm</td>
						<th class="list02_td_bg">체중</th>
						<td class="list02_td"><c:out value="${exLst2.NMF02X }"/>kg</td>
						<th class="list02_td_bg">혈액형</th>
						<td class="list02_td">
							<c:forEach items="${etZzbld }" var="result">
								<c:if test="${result.KEY eq exLst1.ZZBLD}">
									<c:out value="${result.VALUE }"/>
								</c:if>
							</c:forEach>
						</td>
					</tr>
					<tr>
						<th class="list02_td_bg">시력</th>
						<td colspan="3" class="list02_td">좌 : <c:out value="${exLst2.NMF03X }"/>, 우 : <c:out value="${exLst2.NMF04X }"/></td>
						<th class="list02_td_bg">혈압</th>
						<td class="list02_td"><c:out value="${exLst2.WTF07 }"/></td>
					</tr>
				</tbody>
			</table>
		</span>
	</div>

	<div class="table_box">
		<p class="f_title" style="padding-bottom:10px">병력사항</p>
		<span style="display:inline-block; .display:inline; *zoom:1"  class="list02">
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
				<caption></caption>
				<colgroup>
					<col width="12%"/>
					<col width="20%"/>
					<col width="12%"/>
					<col width="20%"/>
					<col width="12%"/>
					<col width="*"/>
				</colgroup>
				<tbody>
					<tr>
						<th class="list02_td_bg">역종</th>
						<td class="list02_td"><c:out value="${exLst3.ZZARTX }"/></td>
						<th class="list02_td_bg">군별</th>
						<td class="list02_td"><c:out value="${exLst3.SERTX }"/></td>
						<th class="list02_td_bg">병과</th>
						<td class="list02_td"><c:out value="${exLst3.JBTXT }"/></td>
					</tr>
					<tr>
						<th class="list02_td_bg">계급</th>
						<td class="list02_td"><c:out value="${exLst3.RKTXT }"/></td>
						<th class="list02_td_bg">복무기간</th>
						<td class="list02_td"><c:out value="${exLst3.PERIOD }"/></td>
						<th class="list02_td_bg">면제사유</th>
						<td class="list02_td"><c:out value="${exLst3.RSEXP }"/></td>
					</tr>
				</tbody>
			</table>
		</span>
	</div>
</div>
</form>
<script type="text/javascript">
try{
	document.domain = 'moorim.co.kr';
}catch(err){
	var console = window.console || {log:function(){}};
	console.log(err);
}
</script>