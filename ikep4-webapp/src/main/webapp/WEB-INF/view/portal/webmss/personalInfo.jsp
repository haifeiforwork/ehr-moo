<%@ page language="java" errorPage="/common/error.jsp"  pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<div class="table_box">
	<p class="f_title" style="padding-bottom:10px">기본정보</p>
	
	<c:set var="exHeader" value="${resultHeader.EX_HEADER }"/>

	<span style="display:inline-block; .display:inline; *zoom:1"  class="list02">
		<table width="100%" border="0" cellpadding="0" cellspacing="0">
			<caption></caption>
			<colgroup>
				<col width="110px"/>
				<col width="10%"/>
				<col width="10%"/>
				<col width="10%"/>
				<col width="15%"/>
				<col width="10%"/>
				<col width="10%"/>
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
					<td colspan="2" class="list02_td"><c:out value="${exHeader.STAT1_T }"/></td>
					<td class="list02_td"><a href="#" class="board_2" name="detail">[상세보기]</a></td>
				</tr>
			</tbody>
		</table>
	</span>
</div>