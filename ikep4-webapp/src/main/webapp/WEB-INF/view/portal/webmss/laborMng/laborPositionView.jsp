<%@ include file="/base/common/taglibs.jsp"%>
<%@ page language="java" errorPage="/common/error.jsp"  pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<script type="text/javascript">
(function($){
	$(document).ready(function(){
		$("#btnBack").click(function(){
			$("#laborPositionForm").attr("method", "post");
			$("#laborPositionForm").attr("action", "<c:url value='/portal/moorimmss/laborMng/laborPositionList.do'/><c:out value='${params.searchParam}' escapeXml='false'/>");
			$("#laborPositionForm").submit();
		});
	});
})(jQuery);
</script>
<form id="laborPositionForm" name="laborPositionForm" method="post" action="" >
<div id="wrap">
	<h1><img src="<c:url value="/base/images/ess/bullet_01.png"/>" alt=""/>노조 직책자 현황 조회</h1>

	<!-- 상단버튼-->
	<div class="button_box">
		<a href="#" class="button_img01" id="btnBack"><span>뒤로</span></a>
	</div>
	
	<c:set var="exDlist" value="${resultMap.EX_DLIST }"/>
	
	<div class="table_box">
		<p class="f_title">개인신상내역</p>
		<span style="display:inline-block; .display:inline; *zoom:1"  class="list01">
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
				<caption></caption>
				<colgroup>
					<col width="10%"/>
					<col width="40%"/>
					<col width="10%"/>
					<col width="40%"/>
				</colgroup>
				<tbody>
					<tr>
						<th class="list03_td_bg">부서</th>
						<td class="list03_td">
							<c:out value="${exDlist.STEXT }"/>
						</td>
						<th class="list03_td_bg">직급</th>
						<td class="list03_td">
							<c:out value="${exDlist.JIKWI }"/>
						</td>
					</tr>
					<tr>
						<th class="list03_td_bg">성명</th>
						<td class="list03_td">
							<c:out value="${exDlist.ENAME }"/>
						</td>
						<th class="list03_td_bg">E-MAIL</th>
						<td class="list03_td">
							<c:out value="${exDlist.CMAIL }"/>
						</td>
					</tr>
					<tr>
						<th class="list03_td_bg">입사일자</th>
						<td class="list03_td">
							<fmt:parseDate var="dateString" value="${exDlist.ENTDAT}" pattern="yyyyMMdd" />
							<fmt:formatDate value="${dateString}" pattern="yyyy-MM-dd" />
						</td>
						<th class="list03_td_bg">Position</th>
						<td class="list03_td">
							<c:out value="${exDlist.PLSTX }"/>
						</td>
					</tr>
					<tr>
						<th class="list03_td_bg">최종승격일</th>
						<td class="list03_td">
							<fmt:parseDate var="dateString" value="${exDlist.A2DAT}" pattern="yyyyMMdd" />
							<fmt:formatDate value="${dateString}" pattern="yyyy-MM-dd" />
						</td>
						<th class="list03_td_bg">근무조</th>
						<td class="list03_td">
							<c:out value="${exDlist.RTEXT }"/>
						</td>
					</tr>
					<tr>
						<th class="list03_td_bg">주민등록번호</th>
						<td class="list03_td">
							<c:out value="${fn:substring(exDlist.ZREGNO,0,6) }"/>-*******
						</td>
						<th class="list03_td_bg">생년월일</th>
						<td class="list03_td">
							<c:out value="${exDlist.BIRTH }"/>
						</td>
					</tr>
					<tr>
						<th class="list03_td_bg">종교</th>
						<td class="list03_td">
							<c:out value="${exDlist.KTEXT }"/>
						</td>
						<th class="list03_td_bg">혈액형</th>
						<td class="list03_td">
							<c:out value="${exDlist.ZZBLD }"/>
						</td>
					</tr>
					<tr>
						<th class="list03_td_bg">현주소</th>
						<td class="list03_td" colspan="3">
							<c:out value="${exDlist.ZADDRS }"/>
						</td>
					</tr>
					<tr>
						<th class="list03_td_bg">연락처</th>
						<td class="list03_td" colspan="3">
							<c:if test="${!empty exDlist.TELNR }">
								(자택) : <c:out value="${exDlist.TELNR }"/>
							</c:if>
							<c:if test="${!empty exDlist.ZHPNO }">
								(핸드폰) : <c:out value="${exDlist.ZHPNO }"/>
							</c:if>
						</td>
					</tr>
					<tr>
						<th class="list03_td_bg">결혼기념일</th>
						<td class="list03_td">
							<fmt:parseDate var="dateString" value="${exDlist.FAMDT}" pattern="yyyyMMdd" />
							<fmt:formatDate value="${dateString}" pattern="yyyy-MM-dd" />
						</td>
						<th class="list03_td_bg">특기</th>
						<td class="list03_td">
							<c:out value="${exDlist.ZZHOB }"/>
						</td>
					</tr>
					<tr>
						<th class="list03_td_bg">출신고교</th>
						<td class="list03_td">
							<c:out value="${exDlist.HSTEXT }"/>
						</td>
						<th class="list03_td_bg">출신대학</th>
						<td class="list03_td">
							<c:out value="${exDlist.USTEXT }"/>
						</td>
					</tr>
					<tr>
						<th class="list03_td_bg">노조직책</th>
						<td class="list03_td">
							<c:out value="${exDlist.LABTX }"/>
						</td>
						<td class="list03_td"></td>
						<td class="list03_td"></td>
					</tr>
				</tbody>
			</table>
		</span>
	</div>

	<div class="table_box">
		<p class="f_title">주석</p>
		<span style="display:inline-block; .display:inline; *zoom:1"  class="list01">
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
				<caption></caption>
				<colgroup>
					<col width="10%"/>
					<col width="*"/>
				</colgroup>
				<tbody>
					<tr>
						<th class="list03_td_bg">노조이력</th>
						<td class="list03_td">
							<textarea rows="8" name="comment" style="width:95%"><c:out value="${exDlist.LABHT }"/></textarea>
						</td>
					</tr>
				</tbody>
			</table>
		</span>
	</div>

	
</div>
</form>
<form id="ajaxForm" name="ajaxForm" method="post">
</form>
<script type="text/javascript">
try{
	document.domain = 'moorim.co.kr';
}catch(err){
	var console = window.console || {log:function(){}};
	console.log(err);
}
</script>