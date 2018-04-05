<%@ include file="/base/common/taglibs.jsp"%>
<%@ page language="java" errorPage="/common/error.jsp"  pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>

<c:set var="user" value="${sessionScope['ikep.user']}" />
<%pageContext.setAttribute("crlf", "\r\n"); %>
<%pageContext.setAttribute("nbsp", " "); %>

<script type="text/javascript">
function customerGradePopup(){
	 var url = "<c:url value='/support/customer/customerBasicInfo/popupCustomerGrade.do'/>";
	 window.open(url,'customerGrade','width=800,height=490,scrollbars=1,toolbar=0'); 
	
}

function goDelete(){
	
	if(confirm("고객정보를 삭제하시겠습니까?")){
		location.href = "<c:url value='/support/customer/customerBasicInfo/deleteBasicInfo.do?id=${basicInfo.id}'/>";
	}
}
(function($){	 
	$(document).ready(function() { 
		$("#readerListViewButton").click( function() {  
			var url = "<c:url value='/support/customer/customerBasicInfo/listReaderView.do?id=${basicInfo.id}&divCode=BM'/>";
			
			iKEP.popupOpen(url , {width:700, height:500});
		});
	});    
})(jQuery); 

</script>


	<h1 class="none">컨텐츠영역</h1>
								
				<!--tableTop Start-->
				<div class="tableTop">
					<div class="tableTop_bgR"></div>
					<h2>기본정보</h2>		
					<!-- <div class="tableSearch">
						<select title="검색조건" name="tablesch_01">
							<option value="">고객</option>
                            <option value="">담당자</option>
						</select>													
						<input type="text" class="inputbox" title="inputbox" name="" value="" size="20" />
                       <a class="ic_search" id="searchBoardItemButton"  href="#a"><span>검색</span></a>                  
					</div> -->	
					<div class="clear"></div>
				</div>
	 			<!--//tableTop End-->
				<div class="blockButton"> 
                <ul>           
				 <c:choose>
				 	<c:when test="${basicInfo.registerId eq user.userId || isAdmin eq true}">
						<%-- <li><a class="button" href="<c:url value='/support/customer/customerBasicInfo/modifyBasicInfo.do?id=${basicInfo.id}&buyingCondition=${buyingCondition}&sellingCondition=${sellingCondition}'/>"><span>수정</span></a></li>
                        <li><a class="button" href="<c:url value='/support/customer/customerBasicInfo/deleteBasicInfo.do?id=${basicInfo.id}'/>"><span>삭제</span></a></li> --%>
                   	    <li><a id="readerListViewButton" class="button" href="#a"><span>조회현황</span></a></li>
                   	    <li><a class="button" href="<c:url value='/support/customer/customerBasicInfo/customerList.do'/>"><span>목록</span></a></li>
					</c:when>
					<c:otherwise>
					  <li><a id="readerListViewButton" class="button" href="#a"><span>조회현황</span></a></li>
                   	  <li><a class="button" href="<c:url value='/support/customer/customerBasicInfo/customerList.do'/>"><span>목록</span></a></li>
                   	</c:otherwise>					
				</c:choose>
				</ul>
				</div>
				<div class="clear"></div> 
                <!--subTitle_1 Start-->
				<div class="subTitle_2 noline">
					<h3>기본정보1</h3>
				</div>
				<!--//subTitle_1 End-->	

				<!--blockDetail Start-->
                <div class="blockDetail">
					<table summary="기본정보1">
						<caption></caption>
                        <colgroup>
                            <col width="15%"/>
                            <col width="35%"/>
                            <col width="15%"/>
                            <col width="35%"/>
                        </colgroup>
						<tbody>
               		  		<tr>
								<th scope="row">고객명</th>
								<td >${basicInfo.name}</td>
								<th scope="row">오너(Owner)</th>
								<td >${basicInfo.ceoName}</td>
							</tr>
							<tr>
								<th scope="row">업종(고객구분)</th>
								<td>${basicInfo.customerGubun}</td>
								<th scope="row">사업자등록번호</th>
								<td><c:if test="${!empty basicInfo.regno}">${basicInfo.regno1}-${basicInfo.regno2}-${basicInfo.regno3}</c:if></td>
							</tr>
							<tr>
								<th scope="row">상장여부</th>
								<td>${basicInfo.sangjangFlag}</td>
								<th scope="row">법인번호</th>
								<td><c:if test="${!empty basicInfo.corporationNo}">${basicInfo.corporationNo1}-${basicInfo.corporationNo2}</c:if></td>
							</tr>
                            <tr>
								<th scope="row">주거래은행</th>
								<td>${basicInfo.mainBank}</td>
								<th scope="row">창립일</th>
								<td>${basicInfo.establishDate}</td>
							</tr>
                            <tr>
								<th scope="row">종업원수</th>
								<td>${basicInfo.employeeCount}</td>
								<th scope="row">결산월</th>
								<td>${basicInfo.sattlingMonth}</td>
							</tr>
                            <tr>
								<th scope="row">우편번호</th>
								<td>${basicInfo.postNo}</td>
								<th scope="row">세분화등급</th>
								<td><a href="javascript:customerGradePopup();">${basicInfo.subdivisionGrade}</a> </td>
							</tr>
                            <tr>
								<th scope="row">주소</th>
								<td colspan="3">${basicInfo.address1} &nbsp;${basicInfo.address2} </td>
							</tr>
                            <tr>
								<th scope="row">대표전화</th>
								<td>${basicInfo.telNo}</td>
								<th scope="row">Fax</th>
								<td>${basicInfo.fax}</td>
							</tr>
							<tr>
								<th scope="row">E-Mail</th>
								<td>${basicInfo.eMail}</td>
								<th scope="row">SAP 연계현황</th>
								<td><c:choose><c:when test="${basicInfo.sapId ne null}">SAP 동기화 완료</c:when>
								<c:otherwise>SAP 동기화 필요</c:otherwise>
								</c:choose></td>
							</tr>
							<tr>
								<th scope="row">업태</th>
								<td>${basicInfo.type}</td>
								
								<th scope="row">등록자</th>
								<td>${basicInfo.registerName}</td>
							</tr>
							<tr>
								<th scope="row">종목</th>
								<td>${basicInfo.jijongType}</td>
								<th scope="row">등록일</th>
								<td><ikep4j:timezone date='${basicInfo.registDate}'/></td>
							</tr>
                            <tr>
								<th scope="row">연혁</th>
								<td colspan="3">
                                <div class="blockDetail_3">
                                    <table summary="연혁">
                                        <caption></caption>
                                        <colgroup>
                                            <col width="15%"/>
                                            <col width="85%"/>
                                        </colgroup>
                                        <tbody>
                                            <tr>
                                                <th scope="row" class="textCenter">날짜</th>
                                                <th scope="row" class="textCenter">주요 내용</th>
                                            </tr>
                                           <c:forEach var="basicHistoryItem" items="${basicHistory}" varStatus="status">
                                            <tr>
                                                <td>${basicHistoryItem.yearDate}</td>
                                                <td>${basicHistoryItem.yearContent}</td>
                                            </tr>
                                           </c:forEach>
                                           <c:if test="${empty basicHistory}" >
                                            <tr>
                                                <td></td>
                                                <td></td>
                                            </tr>
                                           </c:if>
                                        </tbody>
                                    </table>
                                </div>
                                </td>
							</tr>
                            <tr>
								<th scope="row">설비현황</th>
								<td colspan="3">
                                <div class="blockDetail_3">
                                    <table summary="설비현황">
                                        <caption></caption>
                                        <colgroup>
                                            <col width="15%"/>
                                            <col width="85%"/>
                                        </colgroup>
                                        <tbody>                                          
                                            <tr>
                                           	    <th rowspan="2" class="textCenter" scope="row">창고</th>
                                            	<td>${basicInfo.equipmentStoreName}</td>
                                            </tr>
                                            <tr>
                                            <c:set var ="equipmentStoreExplan2" value ="${fn:replace(basicInfo.equipmentStoreExplan, crlf, '<br/>')}"  />
                                                <td>${fn:replace(equipmentStoreExplan2, nbsp, "&nbsp;")}</td>
                                            </tr>
                                            <tr>
                                                <th rowspan="2"  scope="row" class="textCenter">인쇄기</th>
                                                <td>${basicInfo.equipmentPrintName}</td>
                                            </tr>
                                            <tr>
                                            <c:set var ="equipmentPrintExplan2" value ="${fn:replace(basicInfo.equipmentPrintExplan, crlf, '<br/>')}"  />
                                                <td>${fn:replace(equipmentPrintExplan2, nbsp, "&nbsp;")}</td>
                                            </tr>
                                            <tr>
                                                <th rowspan="2" scope="row" class="textCenter">기타</th>
                                                <td>${basicInfo.equipmentEtcName}</td>
                                            </tr>
                                            <tr>
                                            <c:set var ="equipmentEtcExplan2" value ="${fn:replace(basicInfo.equipmentEtcExplan, crlf, '<br/>')}"  />
                                                <td>${fn:replace(equipmentEtcExplan2, nbsp, "&nbsp;")}</td>
                                            </tr>
                                        </tbody>
                                    </table>
                                </div>
                                </td>
							</tr>
                        </tbody>
					</table>
				</div>
                <!--//blockDetail End-->
                
                <div class="blockBlank_10px"></div>		
				
				<!--subTitle_2 Start-->
				<div class="subTitle_2 noline">
					<h3>기본정보2</h3>
				</div>
				<!--//subTitle_2 End-->	
                
                <!--blockDetail Start-->
                <div class="blockDetail">
					<table summary="기본정보2">
						<caption></caption>
                        <colgroup>
                            <col width="15%"/>
                            <col width="35%"/>
                            <col width="15%"/>
                            <col width="35%"/>
                        </colgroup>
						<tbody>
               		  		<tr>
								<th scope="row">대표이사</th>
								<td>${basicInfo.director}</td>
								<th scope="row">담당 영업사원</th>
								<td>${basicInfo.businessEmployee}</td>
							</tr>
							<c:if test="${empty mainPerson}">
							  <tr>
								<th scope="row">주요임원</th>
								<td colspan="3">
                                <div class="blockDetail_3">
                                    <table summary="주요임원">
                                        <caption></caption>
                                        <colgroup>
                                            <col width="25%"/>
                                            <col width="25%"/>
                                            <col width="25%"/>
                                            <col width="25%"/>
                                        </colgroup>
                                        <tbody>
                                            <tr>
                                                <th scope="row" class="textCenter">이름</th>
                                                <th scope="row" class="textCenter">직위</th>
                                                <th scope="row" class="textCenter">전화</th>
                                                <th scope="row" class="textCenter">이메일</th>
                                            </tr>
                                            <tr>
                                                <td class="textCenter"></td>
                                                <td class="textCenter"></td>
                                                <td class="textCenter"></td>
                                                <td class="textCenter"></td>
                                            </tr>
                                        </tbody>
                                    </table>
                                </div>
                                </td>
							</tr>
							 <tr>
								<th scope="row">Key Man</th>
								<td colspan="3">
                                <div class="blockDetail_3">
                                    <table summary="Key Man">
                                        <caption></caption>
                                        <colgroup>
                                            <col width="25%"/>
                                            <col width="25%"/>
                                            <col width="25%"/>
                                            <col width="25%"/>
                                        </colgroup>
                                        <tbody>
                                            <tr>
                                                <th scope="row" class="textCenter">구분</th>
                                                <th scope="row" class="textCenter">직위</th>
                                                <th scope="row" class="textCenter">이름</th>
                                                <th scope="row" class="textCenter">연락처</th>
                                            </tr>
                                            <tr>
                                                <td class="textCenter"></td>
                                                <td class="textCenter"></td>
                                                <td class="textCenter"></td>
                                                <td class="textCenter"></td>
                                            </tr>                                     
                                        </tbody>
                                    </table>
                                </div>
                                </td>
							</tr>
							</c:if>
							<c:if test="${!empty mainPerson}">
                            <tr>
								<th scope="row">주요임원</th>
								<td colspan="3">
                                <div class="blockDetail_3">
                                    <table summary="주요임원">
                                        <caption></caption>
                                        <colgroup>
                                            <col width="25%"/>
                                            <col width="25%"/>
                                            <col width="25%"/>
                                            <col width="25%"/>
                                        </colgroup>
                                        <tbody>
                                            <tr>
                                                <th scope="row" class="textCenter">이름</th>
                                                <th scope="row" class="textCenter">직위</th>
                                                <th scope="row" class="textCenter">전화</th>
                                                <th scope="row" class="textCenter">이메일</th>
                                            </tr>
                                            <c:forEach var="mainPersonItem" items="${mainPerson}" varStatus="status">
											<c:if test="${mainPersonItem.officerFlag eq 'Y'}">
                                            <tr>
                                                <td class="textCenter">${mainPersonItem.name}</td>
                                                <td class="textCenter">${mainPersonItem.jikwe}</td>
                                                <td class="textCenter">${mainPersonItem.tel}</td>
                                                <td class="textCenter">${mainPersonItem.eMail}</td>
                                            </tr>
                                            </c:if>                                         
                                            </c:forEach>
                                        </tbody>
                                    </table>
                                </div>
                                </td>
							</tr>			
                            <tr>
								<th scope="row">Key Man</th>
								<td colspan="3">
                                <div class="blockDetail_3">
                                    <table summary="Key Man">
                                        <caption></caption>
                                        <colgroup>
                                            <col width="25%"/>
                                            <col width="25%"/>
                                            <col width="25%"/>
                                            <col width="25%"/>
                                        </colgroup>
                                        <tbody>
                                            <tr>
                                                <th scope="row" class="textCenter">구분</th>
                                                <th scope="row" class="textCenter">직위</th>
                                                <th scope="row" class="textCenter">이름</th>
                                                <th scope="row" class="textCenter">연락처</th>
                                            </tr>
                                          <c:forEach var="mainPersonItem" items="${mainPerson}" varStatus="status">
                                          <c:if test="${mainPersonItem.officerFlag eq 'N'}">
                                            <tr>
                                                <td class="textCenter">${mainPersonItem.keymanFlag}</td>
                                                <td class="textCenter">${mainPersonItem.jikwe}</td>
                                                <td class="textCenter">${mainPersonItem.name}</td>
                                                <td class="textCenter">${mainPersonItem.eMail}</td>
                                            </tr>
                                          </c:if>                                     
                                          </c:forEach>
                                        </tbody>
                                    </table>
                                </div>
                                </td>
							</tr>
							</c:if> 											
                            <tr>
								<th scope="row">인원현황</th>
								<td colspan="3">
                                <div class="blockDetail_3">
                                    <table summary="인원현황">
                                        <caption></caption>
                                        <colgroup>
                                            <col width="20%"/>
                                            <col width="20%"/>
                                            <col width="20%"/>
                                            <col width="20%"/>
                                            <col width="20%"/>
                                        </colgroup>
                                        <tbody>
                                            <tr>
                                                <th scope="row" class="textCenter">구분</th>
                                                <th scope="row" class="textCenter">임원</th>
                                                <th scope="row" class="textCenter">차/부장</th>
                                                <th scope="row" class="textCenter">과장/대리</th>
                                                <th scope="row" class="textCenter">계장/주임/사원</th>
                                            </tr>
                                            <tr>
                                                <td class="textCenter">남</td>
                                                <td class="textCenter">${personStatic.manEmployee1}</td>
                                                <td class="textCenter">${personStatic.manEmployee2}</td>
                                                <td class="textCenter">${personStatic.manEmployee3}</td>
                                                <td class="textCenter">${personStatic.manEmployee4}</td>
                                            </tr>
                                            <tr>
                                                <td class="textCenter">녀</td>
                                                <td class="textCenter">${personStatic.womanEmployee1}</td>
                                                <td class="textCenter">${personStatic.womanEmployee2}</td>
                                                <td class="textCenter">${personStatic.womanEmployee3}</td>
                                                <td class="textCenter">${personStatic.womanEmployee4}</td>
                                            </tr>
                                            <tr>
                                                <td class="textCenter">합계</td>
                                                <td class="textCenter">${personStatic.totalEmployee1}</td>
                                                <td class="textCenter">${personStatic.totalEmployee2}</td>
                                                <td class="textCenter">${personStatic.totalEmployee3}</td>
                                                <td class="textCenter">${personStatic.totalEmployee4}</td>
                                            </tr>
                                        </tbody>
                                    </table>
                                </div>
                                </td>
							</tr>
                            <tr>
								<th scope="row">주주구성</th>
								<td colspan="3">${basicInfo.stockholder}</td>
							</tr>
                            <tr>
								<th scope="row">매출액</th>
								<td>${basicInfo.sellingAmt}</td>
                                <th scope="row">영업이익</th>
								<td>${basicInfo.businessAmt}</td>
							</tr>
                            <tr>
								<th scope="row">월평균 구매량</th>
								<td>${basicInfo.monthBuying}</td>
                                <th scope="row">당사 비율</th>
								<td>${basicInfo.companyPercent}</td>
							</tr>
						 <c:if test="${!empty buyingInfoList}">
							 <tr>
								<th scope="row">구매정보</th>
								<td colspan="3">
                                <div class="blockDetail_3">
                                    <table summary="구매정보">
                                        <caption></caption>
                                        <colgroup>
                                            <col width="20%"/>
                                            <col width="15%"/>
                                            <col width="15%"/>
                                            <col width="20%"/>
                                            <col width="15%"/>
                                            <col width="15%"/>
                                        </colgroup>
                                        <tbody>
                                            <tr>
                                                <th scope="row" class="textCenter">판매조직</th>
                                                <th scope="row" class="textCenter">유통경로</th>
                                                <th scope="row" class="textCenter">제품군</th>
                                                <th scope="row" class="textCenter">고객그룹1</th>
                                                <th scope="row" class="textCenter">고객그룹2</th>
                                                <th scope="row" class="textCenter">영업담당자</th>
                                            </tr>
                                         <c:forEach var="buyingInfo" items="${buyingInfoList}" varStatus="status">
                                            <tr>
                                                <td class="textCenter">${buyingInfo.sellingGroup}</td>
                                                <td class="textCenter">${buyingInfo.channel}</td>
                                                <td class="textCenter">${buyingInfo.productLine}</td>
                                                <td class="textCenter">${buyingInfo.customerGroup2}</td>
                                                <td class="textCenter">${buyingInfo.customerGroup3}</td>
                                                <td class="textCenter">${buyingInfo.businessEmployee}</td>
                                            </tr>
                                         </c:forEach>
                                         <c:if test="${empty buyingInfoList}">
                                          	<tr>
                                                <td class="textCenter"></td>
                                                <td class="textCenter"></td>
                                                <td class="textCenter"></td>
                                            </tr>
                                         </c:if>
                                        </tbody>
                                    </table>
                                </div>
                                </td>
							</tr>
							</c:if>
							
                            <tr>
								<th scope="row">주거래선</th>
								<td colspan="3">
                                <div class="blockDetail_3">
                                    <table summary="주거래선">
                                        <caption></caption>
                                        <colgroup>
                                            <col width="15%"/>
                                            <col width="17%"/>
                                            <col width="17%"/>
                                            <col width="17%"/>
                                            <col width="17%"/>
                                            <col width="17%"/>
                                        </colgroup>
                                        <tbody>
                                            <tr>
                                            	<c:if test="${!empty mainBusiness}">
	                                               <th scope="row" rowspan="${buyingCnt}">구매</th>
	                                            </c:if>                                              
                                                <c:if test="${empty mainBusiness}">
	                                              <th scope="row" rowspan="2">구매</th>
	                                            </c:if>                                                
                                                <th scope="row" class="textCenter">업체명</th>
                                                <th scope="row" class="textCenter">월평균 구매량</th>
                                                <th scope="row" class="textCenter">담당 영업사원</th>
                                                <th scope="row" class="textCenter">주요지종</th>
                                                <th scope="row" class="textCenter">구매비율</th>
                                            </tr>
                             
                                        <c:forEach var="mainBusiness" items="${mainBusiness}" varStatus="status">
                                          <c:if test="${mainBusiness.sellingBuyingFlag == '1'}">
                                            <tr>
                                                <td class="textCenter">${mainBusiness.mainCustomer}</td>
                                                <td class="textCenter">${mainBusiness.monthAvg}</td>
                                                <td class="textCenter">${mainBusiness.chargeEmployee}</td>
                                                <td class="textCenter">${mainBusiness.mainProduct}</td>
                                                <td class="textCenter">${mainBusiness.sellingBuyingRate}</td>
                                            </tr>
										  </c:if>
										  </c:forEach>
										  <c:if test="${empty mainBusiness}">
                                             <tr>
                                                <td class="textCenter"></td>
                                                <td class="textCenter"></td>
                                                <td class="textCenter"></td>
                                                <td class="textCenter"></td>
                                                <td class="textCenter"></td>
                                             </tr>
                                           </c:if>
                                            <tr>
                                                <th scope="row">구매조건</th>
                                                <td colspan="5">${buyingCondition}</td>
                                            </tr>
                                            <tr>
                                           		<c:if test="${!empty mainBusiness}">
	                                               <th scope="row" rowspan="${sellingCnt}">판매</th>
	                                            </c:if>                                              
                                                <c:if test="${empty mainBusiness}">
	                                              <th scope="row" rowspan="2">판매</th>
	                                            </c:if>
                                                <th scope="row" class="textCenter">업체명</th>
                                                <th scope="row" class="textCenter">월평균 구매량</th>
                                                <th scope="row" class="textCenter">담당 영업사원</th>
                                                <th scope="row" class="textCenter">주요생산제품</th>
                                                <th scope="row" class="textCenter">판매비율</th>
                                            </tr>
                                          <c:forEach var="mainBusiness" items="${mainBusiness}" varStatus="status">
                                          <c:if test="${mainBusiness.sellingBuyingFlag == '2'}">
                                             <tr>
                                                <td class="textCenter">${mainBusiness.mainCustomer}</td>
                                                <td class="textCenter">${mainBusiness.monthAvg}</td>
                                                <td class="textCenter">${mainBusiness.chargeEmployee}</td>
                                                <td class="textCenter">${mainBusiness.mainProduct}</td>
                                                <td class="textCenter">${mainBusiness.sellingBuyingRate}</td>
                                            </tr>
                                           </c:if>
                                           </c:forEach>
                                           <c:if test="${empty mainBusiness}">
                                             <tr>
                                                <td class="textCenter"></td>
                                                <td class="textCenter"></td>
                                                <td class="textCenter"></td>
                                                <td class="textCenter"></td>
                                                <td class="textCenter"></td>
                                             </tr>
                                           </c:if>
                                            <tr>
                                                <th scope="row">판매조건</th>
                                                <td colspan="5">${sellingCondition}</td>
                                            </tr> 
                                        </tbody>
                                    </table>
                                </div>
                                </td>
							</tr>
                            <tr>
								<th scope="row">담당자</th>
								<td colspan="3">${basicInfo.charge}</td>
							</tr>
                            <tr>
								<th scope="row">주소</th>
								<td colspan="3">
                                <div class="blockDetail_3">
                                    <table summary="주소">
                                        <caption></caption>
                                        <colgroup>
                                            <col width="15%"/>
                                            <col width="85%"/>
                                        </colgroup>
                                        <tbody>
                                        
                                            <tr>
                                                <th scope="row" class="textCenter">본사</th>
                                                <td>${basicInfo.headOfficeAddress}</td>
                                            </tr>
                                            <tr>
                                                <th scope="row" class="textCenter">공장1</th>
                                                <td>${basicInfo.factoryAddress1}</td>
                                            </tr>
                                            <tr>
                                                <th scope="row" class="textCenter">공장2</th>
                                                <td>${basicInfo.factoryAddress2}</td>
                                            </tr>
                                        </tbody>
                                    </table>
                                </div>
                                </td>
							</tr>
                            <tr>
								<th scope="row">관계사 현황</th>
								<td colspan="3">
                                <div class="blockDetail_3">
                                    <table summary="관계사 현황">
                                        <caption></caption>
                                        <colgroup>
                                            <col width="30%"/>
                                            <col width="30%"/>
                                            <col width="40%"/>
                                        </colgroup>
                                        <tbody>
                                            <tr>
                                                <th scope="row" class="textCenter">업체명</th>
                                                <th scope="row" class="textCenter">업종</th>
                                                <th scope="row" class="textCenter">비고</th>
                                            </tr>
                                         <c:forEach var="relationCompany" items="${relationCompany}" varStatus="status">
                                            <tr>
                                                <td class="textCenter">${relationCompany.relationName}</td>
                                                <td class="textCenter">${relationCompany.type}</td>
                                                <td class="textCenter">${relationCompany.note}</td>
                                            </tr>
                                         </c:forEach>
                                         <c:if test="${empty relationCompany}">
                                          	<tr>
                                                <td class="textCenter"></td>
                                                <td class="textCenter"></td>
                                                <td class="textCenter"></td>
                                            </tr>
                                         </c:if>
                                        </tbody>
                                    </table>
                                </div>
                                </td>
							</tr>
                        </tbody>
					</table>
				</div>
				<!--//blockDetail End-->
              
                
                <div class="blockButton"> 
                <ul>           
				 <c:choose>
				 	<c:when test="${basicInfo.registerId eq user.userId || isAdmin eq true}">
						<li><a class="button" href="<c:url value='/support/customer/customerBasicInfo/modifyBasicInfo.do?id=${basicInfo.id}&buyingCondition=${buyingCondition}&sellingCondition=${sellingCondition}'/>"><span>수정</span></a></li>
                        <li><a class="button" href="javascript:goDelete();"><span>삭제</span></a></li>
                   	    <li><a class="button" href="<c:url value='/support/customer/customerBasicInfo/customerList.do'/>"><span>목록</span></a></li>
					</c:when>
					<c:otherwise>
                   	  <li><a class="button" href="<c:url value='/support/customer/customerBasicInfo/customerList.do'/>"><span>목록</span></a></li>
                   	</c:otherwise>					
				</c:choose>
				</ul>
				</div>
				<div class="clear"></div>
				
				<div class="blockBlank_10px"></div>
                        		