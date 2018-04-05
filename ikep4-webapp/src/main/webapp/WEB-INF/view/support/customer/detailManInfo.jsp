<%@ include file="/base/common/taglibs.jsp"%>
<%@ page language="java" errorPage="/common/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8"%>
<c:set var="prefix" value="ui.support.customer.board" />
<c:set var="preSearch" value="ui.ikep4.common.searchCondition" />
<c:set var="preButton" value="ui.lightpack.common.button" />
<c:set var="preList" value="ui.support.customer.manInfoItemList" />
<c:set var="user" value="${sessionScope['ikep.user']}" />
<%pageContext.setAttribute("crlf", "\r\n"); %>
<%pageContext.setAttribute("nbsp", " "); %>


<script type="text/javascript">

(function($) {

	
	$(document).ready( function() {
		
		$("a.deleteButton").click(function() {
			
			var url = "<c:url value='/support/customer/customerCommon/deleteManInfo.do'/>";
			
			if(confirm("삭제하시겠습니까?")){
			
				$.ajax({
					url : url,
					data :{'seqNum': '${manInfoItem.seqNum}',
						   'registerId' : '${manInfoItem.registerId}'},
					type : "get",
					success : function() {
						alert("삭제가 완료되었습니다.");
						
						location.href="<c:url value = '/support/customer/customerCommon/manInfo.do'/>";
					},
					error : function(event, request, settings) { 
						alert("error");	
					}
				});	
			}
		});
		
		$("#readerListViewButton").click( function() {  
			var url = "<c:url value='/support/customer/customerBasicInfo/listReaderView.do?id=${manInfoItem.seqNum}&divCode=PI'/>";
			
			iKEP.popupOpen(url , {width:700, height:500});
		});
		
		
		
	});
	
	
	
})(jQuery);


</script>	

<h1 class="none">컨텐츠영역</h1>

<!--tableTop Start-->
<div class="tableTop">
	<div class="tableTop_bgR"></div>
	<h2>
		<ikep4j:message pre='${prefix}' key='manInfo.pageTitle' />
	</h2>
	<div class="clear"></div>
</div>
<!--//tableTop End-->

			<div class="blockButton"> 
            <ul>           
			  <c:choose>
				 	<c:when test="${manInfoItem.registerId eq user.userId || isAdmin eq true}">
						 <li><a id="readerListViewButton" class="button" href="#a"><span>조회현황</span></a></li>
						<%-- <li><a class="button" href="<c:url value='/support/customer/customerCommon/createManInfoView.do'/>"><span><ikep4j:message pre='${prefix}' key='manInfo.add' /></span></a></li> 	
						<li><a class="button" href="<c:url value='/support/customer/customerCommon/modifyManInfoView.do?seqNum=${manInfoItem.seqNum}'/>"><span><ikep4j:message pre='${prefix}' key='manInfo.change' /></span></a></li> 
						<li><a class="button deleteButton" href="#a"><span><ikep4j:message pre='${prefix}' key='manInfo.delete' /></span></a></li>
						<li><a class="button" href="<c:url value='/support/customer/customerCommon/manInfo.do'/>"><span><ikep4j:message pre='${prefix}' key='manInfo.list' /></span></a></li> --%>
					</c:when>
					<c:otherwise>
						<li><a id="readerListViewButton" class="button" href="#a"><span>조회현황</span></a></li>
						<%-- <li><a class="button" href="<c:url value='/support/customer/customerCommon/createManInfoView.do'/>"><span><ikep4j:message pre='${prefix}' key='manInfo.add' /></span></a></li> 	
						<li><a class="button" href="<c:url value='/support/customer/customerCommon/manInfo.do'/>"><span><ikep4j:message pre='${prefix}' key='manInfo.list' /></span></a> </li> --%>
					</c:otherwise>
				</c:choose>
			</ul>
			</div> 
			<div class="clear"></div>

<!--subTitle_1 Start-->
<div class="subTitle_2 noline">
	<h3>기본정보</h3>
</div>
<!--//subTitle_1 End-->

<!--blockDetail Start-->
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
				<th scope="row"><span class="colorPoint">*</span>고객명</th>
				<td colspan="7">${manInfoItem.customerName}</td>
				<td rowspan="6" class="textCenter">
				<%-- <img src="<c:url value='/support/fileupload/downloadFile.do'/>?fileId=${manInfoItem.sajinSys}" title="<ikep4j:message pre="${prefix}" key="img.managermessageImage" />" width="110" height="90" /> --%>				
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
				<th scope="row"><span class="colorPoint">*</span>성명</th>
				<td>${manInfoItem.man}</td>
				<th scope="row">직위</th>
				<td>${manInfoItem.jobTitle}</td>
				<th scope="row">군경력</th>
				<td>${manInfoItem.army}</td>
				<th scope="row">출신지역</th>
				<td>${manInfoItem.nativeArea}</td>
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
				<td colspan="3">${manInfoItem.email}</td>
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

		<div class="blockButton"> 
           <ul>           
		  <c:choose>
			 	<c:when test="${manInfoItem.registerId eq user.userId || isAdmin eq true}">
					 
					<li><a class="button" href="<c:url value='/support/customer/customerCommon/createManInfoView.do'/>"><span><ikep4j:message pre='${prefix}' key='manInfo.add' /></span></a></li> 	
					<li><a class="button" href="<c:url value='/support/customer/customerCommon/modifyManInfoView.do?seqNum=${manInfoItem.seqNum}'/>"><span><ikep4j:message pre='${prefix}' key='manInfo.change' /></span></a></li> 
					<li><a class="button deleteButton" href="#a"><span><ikep4j:message pre='${prefix}' key='manInfo.delete' /></span></a></li>
					<li><a class="button" href="<c:url value='/support/customer/customerCommon/manInfo.do'/>"><span><ikep4j:message pre='${prefix}' key='manInfo.list' /></span></a></li>
				</c:when>
				<c:otherwise>
					
					<li><a class="button" href="<c:url value='/support/customer/customerCommon/createManInfoView.do'/>"><span><ikep4j:message pre='${prefix}' key='manInfo.add' /></span></a></li> 	
					<li><a class="button" href="<c:url value='/support/customer/customerCommon/manInfo.do'/>"><span><ikep4j:message pre='${prefix}' key='manInfo.list' /></span></a> </li>
				</c:otherwise>
			</c:choose>
		</ul>
		</div>
<div class="blockBlank_10px"></div>

</div>
