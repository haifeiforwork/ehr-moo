<%@ include file="/base/common/taglibs.jsp"%>
<%@ page language="java" errorPage="/common/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8"%>
<%@ taglib uri="/WEB-INF/tld/ikep4j.tld" prefix="ikep4j" %> 

<c:set var="preHeader"  value="ui.lightpack.meetingroom.header" /> 
<c:set var="preDetail"  value="ui.lightpack.meetingroom.detail" />
<c:set var="preButton"  value="ui.lightpack.meetingroom.button" /> 
<c:set var="preMessage" value="ui.lightpack.meetingroom.message" />
<c:set var="preSearch"  value="ui.communication.common.searchCondition" />

<c:set var="tmpStatus1"  value="0" />
<c:set var="tmpStatus2"  value="0" />
<c:forEach var="officeway" items="${searchResult.entity}" varStatus="status">
	<c:if test="${officeway.status1 == 'S' && empty officeway.status2}">
		<c:set var="tmpStatus1"  value="1" />
	</c:if>
	<c:if test="${officeway.status2 == 'C'}">
		<c:set var="tmpStatus2"  value="1" />
	</c:if>
</c:forEach>

<script type="text/javascript" language="javascript">
//<!--
	var dialogWindow;

	function fnCaller(param, dialog){
	
		dialogWindow = dialog;
	}
	
	function updateFormView(officewayId) {
		$jq("#officewayId").val(officewayId);
		$jq("#searchForm").attr("action", "<c:url value='/lightpack/officeway/officewayUseRequestAdminUpdateForm.do' />");
		$jq("#searchForm")[0].submit();
	}

	// 상단에 보이게 될 리스트를 가져오는 함수
	function getList() {
		
		$jq("#searchForm").attr("action", "<c:url value='/lightpack/officeway/officewayUseRequestOtherTeamList.do' />");
		$jq("#searchForm")[0].submit();
	}

	// 하단에 보이게 될 상세정보를 가져오는 함수
	function getView(officewayId) {
		
		$jq("#officewayId").val(officewayId);
		
		var url = "<c:url value='/lightpack/officeway/officewayUseRequestView.do' />";
		
		$jq.ajax({     
			
			url : url,    
			data : $jq("#searchForm").serialize(),
			type : "post",     
			loadingElement : {
				
				container : "#reserveDiv"
			},
			success : function(result) {       
				
				$jq("#reserveDiv").html(result);
			},
			error : function(event, request, settings) { 
				
				alert("error"); 
			}
		}); 
	}
	
	// 소팅을 위한 함수
	function sort(sortColumn, sortType) {
		
		$jq("input[name=sortColumn]").val(sortColumn);
		
		if( sortType == 'ASC') {
			
			$jq("input[name=sortType]").val('DESC');	
		} else {
			
			$jq("input[name=sortType]").val('ASC');
		}
		
		getList();
	};
	
	// 페이지 로딩시 실행하는 jQuery 코드로 list와 view를 불러온다.
	$jq(document).ready(function() {
		//left menu setting
		//$jq("#callManageOfLeft").click();
		
		$jq("#newFormButton").click(function() {
			
		});
		
		$jq("#deleteButton").click(function() {
			
			deleteForm();
		});
		
		$jq("#searchBoardItemButton").click(function() {  
			
			getList();
		});
		
		// 백스페이스 방지(input, select 제외)
		$jq(document).keydown(function(e) {
			
			var element = e.target.nodeName.toLowerCase();
			
			if (element != 'input') {
				
			    if (e.keyCode === 8) {
			    	
			        return false;
			    }
			}
		});
		
		$jq("#startPeriod").datepicker().next().eq(0).click(function() { 
			
			$jq(this).prev().eq(0).datepicker("show"); 
		});
		
		$jq("#endPeriod").datepicker().next().eq(0).click(function() { 
			
			$jq(this).prev().eq(0).datepicker("show"); 
		});
		
		$jq("#checkboxAllItem").click(function() { 
			$jq("#searchForm input[name=checkboxItem]").attr("checked", $jq(this).is(":checked"));  
		});  
		$jq("#confirmButton").click(function() {
				
			var itemIds = new Array();
			var tempItemId = "";
			$jq("#searchForm input[name=checkboxItem]:checked").each(function(index) { 
				if(tempItemId == ""){
					tempItemId = $jq(this).val(); 
				}else{
					tempItemId = tempItemId+"|"+$jq(this).val(); 
				}
			});	
			if(tempItemId == ""){
				alert("사무용품을 선택해주세요.");
				return;
			}
			$jq('input[name=officewayId]').val(tempItemId);
			$jq('input[name=status2]').val("C");
			if(confirm("선택한 사무용품을 검토 완료하시겠습니까?")) {
				$jq("#regForm").attr("action", "<c:url value='/lightpack/officeway/officewayManageCheckBoxUseRequest.do' />");
				$jq("#regForm")[0].submit();
			}  
		});
		
		$jq("#approveButton").click(function() {
		
		var itemIds = new Array();
		var tempItemId = "";
		$jq("#searchForm input[name=checkboxItem]:checked").each(function(index) { 
			if(tempItemId == ""){
				tempItemId = $jq(this).val(); 
			}else{
				tempItemId = tempItemId+"|"+$jq(this).val(); 
			}
		});		
		if(tempItemId == ""){
			alert("사무용품을 선택해주세요.");
			return;
		}
		$jq('input[name=officewayId]').val(tempItemId);
		$jq('input[name=status2]').val("A");
		if(confirm("선택한 사무용품을 승인하시겠습니까?")) {
			$jq("#regForm").attr("action", "<c:url value='/lightpack/officeway/officewayManageCheckBoxUseRequest.do' />");
			$jq("#regForm")[0].submit();
		}  
		});
		
		$jq("#rejectButton").click(function() {
		
		var itemIds = new Array();
		var tempItemId = "";
		$jq("#searchForm input[name=checkboxItem]:checked").each(function(index) { 
			if(tempItemId == ""){
				tempItemId = $jq(this).val(); 
			}else{
				tempItemId = tempItemId+"|"+$jq(this).val(); 
			}
		});		
		if(tempItemId == ""){
			alert("사무용품을 선택해주세요.");
			return;
		}
		$jq('input[name=officewayId]').val(tempItemId);
		$jq('input[name=status2]').val("R");
		if(confirm("선택한 사무용품을 반려하시겠습니까?")) {
			$jq("#regForm").attr("action", "<c:url value='/lightpack/officeway/officewayManageCheckBoxUseRequest.do' />");
			$jq("#regForm")[0].submit();
		}  
		});
		
		$jq("#excelButton").click(function() { 
			if(${searchResult.emptyRecord}){
				alert("검색 결과가 없습니다.");
				return false;
			}
			$jq("#searchForm").attr("action","<c:url value='/lightpack/officeway/downloadExcelOfficewayOtherTeam.do'/>");
			$jq("#searchForm")[0].submit();
		return false; 
		});
		$jq("a.btn_list").click(function() {
			$jq("#listForm").submit();
		});
	});
//-->
</script>

<div id="reserveDiv">

	<!--blockListTable Start-->
	<div class="blockListTable">
		<div id="listDiv">
			<form id="listForm" name="listForm" method="post" action="<c:url value='/lightpack/officeway/officewayUseRequestTeamsList.do'/>" ></form>
			<form id="regForm" name="regForm" method="post" action="">
				<input type="hidden" name="status2" value=""/> 
				<input type="hidden" name="officewayId" value=""/> 
			</form>
			<form id="searchForm" name="searchForm" method="post" onsubmit="getList(); return false;" action="">
				<input type="hidden" id="officewayId" name="officewayId"/>
				
				<spring:bind path="searchCondition.sortColumn">
					<input name="${status.expression}" type="hidden" value="${status.value}" title="<ikep4j:message pre='${preSearch}' key='${status.expression}'/>" />
				</spring:bind>
				<spring:bind path="searchCondition.sortType">
					<input name="${status.expression}" type="hidden" value="${status.value}" title="<ikep4j:message pre='${preSearch}' key='${status.expression}'/>" />
				</spring:bind>
				
				<!--tableTop Start-->  
				<div class="tableTop" style="padding:3px;">
					<div class="tableTop_bgR"></div>
					<h2>(전체)팀별 내역 조회</h2>
					
					<div class="tableSearch"> 
						<spring:bind path="searchCondition.teamId">
		                    <select  name="${status.expression}">
		                    	<option value="">전체</option>
								<c:forEach var="officewayTeam" items="${officewayUseTeamList}" varStatus="status">
									<option value="${officewayTeam.teamId}" <c:if test="${searchCondition.teamId eq officewayTeam.teamId || teamId eq officewayTeam.teamId}">selected="selected"</c:if>>${officewayTeam.teamName}</option>
								</c:forEach>
							</select>
						</spring:bind>
						&nbsp;&nbsp;&nbsp;&nbsp;
						<spring:bind path="searchCondition.startYear">
		                    <select title="${status.expression}" name="${status.expression}" >                               		
			                    <c:set var="compareYear" value="${searchCondition.startYear}" />
			                    <c:if test="${empty compareYear}">
			                    	<c:set var="compareYear" value="${nowYear}" />
			                    </c:if>
						        <c:forEach var="year" begin="2016" end="${nowYear}" step="1">
						        	<option value="${year}" <c:if test="${year eq compareYear}">selected="selected"</c:if>>${year}</option>
						        </c:forEach>
						        <option value="${compareYear+1}">${compareYear+1}</option>
					        </select>			                       	
                        </spring:bind> 
                        <spring:bind path="searchCondition.startMonth">
		                    <select title="${status.expression}" name="${status.expression}">                               		
			                    <c:set var="compareMonth" value="${searchCondition.startMonth}" />
			                    <c:if test="${empty compareMonth}">
			                    	<c:set var="compareMonth" value="${nowMonth}" />
			                    </c:if>
						        <option value="01" <c:if test="${'01' eq compareMonth}">selected="selected"</c:if>>1</option>
						        <option value="02" <c:if test="${'02' eq compareMonth}">selected="selected"</c:if>>2</option>
						        <option value="03" <c:if test="${'03' eq compareMonth}">selected="selected"</c:if>>3</option>
						        <option value="04" <c:if test="${'04' eq compareMonth}">selected="selected"</c:if>>4</option>
						        <option value="05" <c:if test="${'05' eq compareMonth}">selected="selected"</c:if>>5</option>
						        <option value="06" <c:if test="${'06' eq compareMonth}">selected="selected"</c:if>>6</option>
						        <option value="07" <c:if test="${'07' eq compareMonth}">selected="selected"</c:if>>7</option>
						        <option value="08" <c:if test="${'08' eq compareMonth}">selected="selected"</c:if>>8</option>
						        <option value="09" <c:if test="${'09' eq compareMonth}">selected="selected"</c:if>>9</option>
						        <option value="10" <c:if test="${'10' eq compareMonth}">selected="selected"</c:if>>10</option>
						        <option value="11" <c:if test="${'11' eq compareMonth}">selected="selected"</c:if>>11</option>
						        <option value="12" <c:if test="${'12' eq compareMonth}">selected="selected"</c:if>>12</option>
					        </select>			                       	
                        </spring:bind>
                        ~
                        <spring:bind path="searchCondition.endYear">
		                    <select title="${status.expression}" name="${status.expression}">                               		
			                    <c:set var="compareYear" value="${searchCondition.endYear}" />
			                    <c:if test="${empty compareYear}">
			                    	<c:set var="compareYear" value="${nowYear}" />
			                    </c:if>
						        <c:forEach var="year" begin="2016" end="${nowYear}" step="1">
						        	<option value="${year}" <c:if test="${year eq compareYear}">selected="selected"</c:if>>${year}</option>
						        </c:forEach>
						        <option value="${compareYear+1}">${compareYear+1}</option>
					        </select>			                       	
                        </spring:bind> 
                        <spring:bind path="searchCondition.endMonth">
		                    <select title="${status.expression}" name="${status.expression}">                               		
			                    <c:set var="compareMonth" value="${searchCondition.endMonth}" />
			                    <c:if test="${empty compareMonth}">
			                    	<c:set var="compareMonth" value="${nowMonth}" />
			                    </c:if>
						        <option value="01" <c:if test="${'01' eq compareMonth}">selected="selected"</c:if>>1</option>
						        <option value="02" <c:if test="${'02' eq compareMonth}">selected="selected"</c:if>>2</option>
						        <option value="03" <c:if test="${'03' eq compareMonth}">selected="selected"</c:if>>3</option>
						        <option value="04" <c:if test="${'04' eq compareMonth}">selected="selected"</c:if>>4</option>
						        <option value="05" <c:if test="${'05' eq compareMonth}">selected="selected"</c:if>>5</option>
						        <option value="06" <c:if test="${'06' eq compareMonth}">selected="selected"</c:if>>6</option>
						        <option value="07" <c:if test="${'07' eq compareMonth}">selected="selected"</c:if>>7</option>
						        <option value="08" <c:if test="${'08' eq compareMonth}">selected="selected"</c:if>>8</option>
						        <option value="09" <c:if test="${'09' eq compareMonth}">selected="selected"</c:if>>9</option>
						        <option value="10" <c:if test="${'10' eq compareMonth}">selected="selected"</c:if>>10</option>
						        <option value="11" <c:if test="${'11' eq compareMonth}">selected="selected"</c:if>>11</option>
						        <option value="12" <c:if test="${'12' eq compareMonth}">selected="selected"</c:if>>12</option>
					        </select>			                       	
                        </spring:bind>
                        &nbsp;&nbsp;&nbsp;&nbsp;
						<%-- <input id="startPeriod" name="startPeriod" type="text" class="inputbox datepicker" value="${startPeriod}" size="10" title="<ikep4j:message pre='${preDetail}' key='date' />" readonly="readonly" /> 
						<img class="dateicon" src="<c:url value='/base/images/icon/ic_cal.gif'/>" alt="<ikep4j:message pre='${preDetail}' key='calendar' />" />
						~
						<input id="endPeriod" name="endPeriod" type="text" class="inputbox datepicker" value="${endPeriod}" size="10" title="<ikep4j:message pre='${preDetail}' key='date' />" readonly="readonly" /> 
						<img class="dateicon" src="<c:url value='/base/images/icon/ic_cal.gif'/>" alt="<ikep4j:message pre='${preDetail}' key='calendar' />" /> --%>
						<%-- <spring:bind path="searchCondition.searchType">  
							<select name="${status.expression}" title="<ikep4j:message pre='${preDetail}' key='classification' />">
								<option value="ALL" <c:if test="${'ALL' eq status.value}">selected="selected"</c:if> >
									<ikep4j:message pre='${preDetail}' key='all' />
								</option>
								<option value="PERS" <c:if test="${'PERS' eq status.value}">selected="selected"</c:if> >
									<ikep4j:message pre='${preDetail}' key='directReserve' />
								</option>
								<option value="PART" <c:if test="${'PART' eq status.value}">selected="selected"</c:if> >
									<ikep4j:message pre='${preDetail}' key='relationReserve' />
								</option>
							</select>	
						</spring:bind> --%>
						<spring:bind path="searchCondition.searchStatus">
						<select name="${status.expression}">
							<option value="" <c:if test="${'' eq status.value}">selected="selected"</c:if>>전체</option>
							<option value="MA" <c:if test="${'MA' eq status.value}">selected="selected"</c:if>>담당부서 확정</option>
							<option value="MR" <c:if test="${'MR' eq status.value}">selected="selected"</c:if>>담당부서 반려</option>
							<option value="MC" <c:if test="${'MC' eq status.value}">selected="selected"</c:if>>담당부서 상신</option>
							<option value="TA" <c:if test="${'TA' eq status.value}">selected="selected"</c:if>>신청</option>
						</select>
						</spring:bind>
						&nbsp;&nbsp;&nbsp;&nbsp;
						<spring:bind path="searchCondition.searchColumn">  
                             <select name="${status.expression}" title='<ikep4j:message pre='${preSearch}' key='${status.expression}' />'>
                                 <option value="productNo" <c:if test="${'code' eq status.value}">selected="selected"</c:if> >제품번호</option>
                                 <option value="productName" <c:if test="${'title' eq status.value}">selected="selected"</c:if> >품목</option>
                             </select>   
                         </spring:bind>      
                         <spring:bind path="searchCondition.searchWord">                     
                             <input name="${status.expression}" value="${status.value}" type="text" class="inputbox" title='<ikep4j:message pre='${preSearch}' key='${status.expression}' />'  size="20" />
                         </spring:bind>
						<%-- <a id="searchBoardItemButton" name="searchBoardItemButton" href="#a" class="ic_search">
							<span><ikep4j:message pre='${preSearch}' key='search' /></span>
						</a> --%>
						&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						<%-- <a href="#a" id="excelButton"><img src="<c:url value='/base/images/theme/theme01/basic/btn_excel.gif'/>" alt="" /></a> --%>
					</div>
					<div class="clear"></div>	
				</div>
				<!--//tableTop End-->	
				<div class="listInfo"> 
						<%-- <spring:bind path="searchCondition.pagePerRecord">  
							<select name="${status.expression}" title='<ikep4j:message pre='${preSearch}' key='${status.expression}' />' onchange="javascript:getList()">
								<c:forEach var="code" items="${boardCode.pageNumList}">
									<option value="${code.key}" <c:if test="${code.key eq status.value}">selected="selected"</c:if>>${code.value}</option>
								</c:forEach> 
							</select> 
						</spring:bind> --%>
						<div class="totalNum">
							${searchResult.pageIndex}/ ${searchResult.pageCount} <ikep4j:message pre='${preSearch}' key='page' /> 
							( <ikep4j:message pre='${preSearch}' key='pageCount.info' /><span> ${searchResult.recordCount}</span>)
						</div>
					</div>
					<div class="nblockButton"> 
						<a id='searchBoardItemButton' class='button_img01' href="#a"><span>조회</span></a>&nbsp;
								<a id='excelButton' class='button_img01' href="#a"><span><img src="<c:url value='/base/images/icon/ico_bullet_excel.png'/>" /> 엑셀다운로드</span></a>
						
					</div>
				<table>
					<caption></caption>
						<colgroup>
							<!-- <col width="3%" /> -->
							<col width="5%" />
							<!-- <col width="7%" /> -->
							<col width="8%" />
							<col width="*" />
							<col width="7%" />
							<col width="7%" />
							<col width="5%" />
							<col width="5%" />
							<col width="5%" />
							<col width="7%" />
							<col width="5%" />
							<col width="10%" />
							<col width="10%" />
						</colgroup>
					<thead>
						<tr>
							<!-- <th scope="col"><input id="checkboxAllItem" class="checkbox" title="checkbox" type="checkbox" value="" /></th>  -->
							<th scope="col">신청월</th>
							<th scope="col" style="border-left:1px solid #e0e0e0;">부서</th>
							<!-- <th scope="col" style="border-left:1px solid #e0e0e0;">분류</th> -->
							<th scope="col" style="border-left:1px solid #e0e0e0;">품목</th>
							<th scope="col" style="border-left:1px solid #e0e0e0;">제품번호</th>
							<th scope="col" style="border-left:1px solid #e0e0e0;">사유</th>
							<th scope="col" style="border-left:1px solid #e0e0e0;">수량</th>
							<th scope="col" style="border-left:1px solid #e0e0e0;">단위</th>
							<th scope="col" style="border-left:1px solid #e0e0e0;">단가</th>
							<th scope="col" style="border-left:1px solid #e0e0e0;">금액</th>
							<th scope="col" style="border-left:1px solid #e0e0e0;">사용자</th>
							<th scope="col" style="border-left:1px solid #e0e0e0;">비고</th>
							<th scope="col" style="border-left:1px solid #e0e0e0;">상태</th>
						</tr>
					</thead>
					<tbody>
						<c:choose>
							<c:when test="${searchResult.emptyRecord}">
								<tr>
									<td colspan="12" class="emptyRecord">
										<ikep4j:message pre='${preSearch}' key='emptyRecord' />
									</td> 
								</tr>				        
						    </c:when>
						    <c:otherwise>
								<c:forEach var="officeway" items="${searchResult.entity}" varStatus="status">
									<tr>
										<%-- <td class="textCenter">
										<c:if test="${ofmlRole && officeway.status1 == 'A' && officeway.status2 == 'C'}">
										<input name="checkboxItem" class="checkbox" title="checkbox" type="checkbox" value="${officeway.officewayId}" /> 
										</c:if>
										<c:if test="${ofmrRole && officeway.status1 == 'A' && empty officeway.status2}">
										<input name="checkboxItem" class="checkbox" title="checkbox" type="checkbox" value="${officeway.officewayId}" /> 
										</c:if>
										</td> --%>
										<td class="textCenter">
											<fmt:formatDate value="${officeway.registDate}" pattern="yyyy.MM" />
										</td>
										<td class="textCenter" style="border-left:1px solid #e0e0e0;">
											${officeway.teamName}
										</td>
										<%-- <td class="textCenter" style="border-left:1px solid #e0e0e0;">
											${officeway.categoryName1}
										</td> --%>
										<td class="textCenter" style="border-left:1px solid #e0e0e0;">
											<c:if test="${ofmrRole || ofmlRole}">
												<a href="javascript:updateFormView(${officeway.officewayId});">${officeway.productName}</a>
											</c:if>
											<c:if test="${!ofmrRole && !ofmlRole}">
												${officeway.productName}
											</c:if>
										</td>
										<td class="textCenter" style="border-left:1px solid #e0e0e0;">
											${officeway.productNo}
										</td>
										<td class="textCenter" style="border-left:1px solid #e0e0e0;">
											${officeway.categoryName2}
										</td>
										<td class="textCenter" style="border-left:1px solid #e0e0e0;">
											${officeway.amount1}
										</td>
										<td class="textCenter" style="border-left:1px solid #e0e0e0;">
											${officeway.unit}
										</td>
										<td class="textRight" style="border-left:1px solid #e0e0e0;">
											<fmt:formatNumber value="${officeway.price1}" type="number" />
										</td>
										<td class="textRight" style="border-left:1px solid #e0e0e0;">
											<fmt:formatNumber value="${officeway.price2}" type="number" />
										</td>
										<td class="textCenter" style="border-left:1px solid #e0e0e0;">
											${officeway.registerName}
										</td>
										<td class="textCenter" style="border-left:1px solid #e0e0e0;">
											${officeway.remark}
										</td>
										<td class="textCenter" style="border-left:1px solid #e0e0e0;">
											<c:choose>
												<c:when test="${officeway.status1 == 'S' && empty officeway.status2}">
													신청
												</c:when>
												<c:when test="${officeway.status1 == 'S' && officeway.status2 == 'C'}">
													담당부서 상신 
												</c:when>
												<c:when test="${officeway.status1 == 'S' && officeway.status2 == 'A'}">
													담당부서 확정
												</c:when>
												<c:when test="${officeway.status1 == 'S' && officeway.status2 == 'R'}">
													담당부서 반려
												</c:when>
											</c:choose>
										</td>
									</tr>
								</c:forEach>
								<tr style="background-color:#FAED7D;color:black;font-weight:bold;">
									<td class="textCenter" colspan="8">
										소계
									</td>
									<td class="textRight" style="border-left:1px solid #e0e0e0;">
										<fmt:formatNumber value="${price4}" type="number" />
									</td>
									<td colspan="3" style="border-left:1px solid #e0e0e0;"></td>
								</tr>
								<tr style="background-color:#FAED7D;color:black;font-weight:bold;">
									<td class="textCenter" colspan="8">
										합계
									</td>
									<td class="textRight" style="border-left:1px solid #e0e0e0;">
										<fmt:formatNumber value="${price5}" type="number" />
									</td>
									<td colspan="3" style="border-left:1px solid #e0e0e0;"></td>
								</tr>
						    </c:otherwise>
						</c:choose>
					</tbody>
				</table>
				
				<!--Page Number Start--> 
				<spring:bind path="searchCondition.pageIndex">
					<ikep4j:pagination searchButtonId="searchBoardItemButton" pageIndexInput="${status.expression}" searchCondition="${searchCondition}" />
					<input id="${status.expression}" name="${status.expression}" type="hidden" value="${status.value}" title="<ikep4j:message pre='${preSearch}' key='${status.expression}'/>" />
				</spring:bind> 
				<!--//Page Number End-->
				
				<input type="hidden" name="tempEquipmentId" id="tempEquipmentId" value="" />
				<%-- <div class="blockButton"> 
				<ul>
					<c:if test="${periodCheck}">
						<c:if test="${ofmrRole && tmpStatus1 == '1'}">
						<li><a id="confirmButton" class="button" href="#a"><span>상신</span></a></li>
						<li><a id="rejectButton" class="button" href="#a"><span>반려</span></a></li>
						<c:if test="${!empty teamId}">
						<li><a id='updateButton' class='button' href='<c:url value='/lightpack/officeway/officewayUseRequestOtherTeamListUpdateForm.do?teamId=${teamId}'/>'><span>수정</span></a></li>
						</c:if>
						</c:if>
						<c:if test="${ofmlRole && tmpStatus2 == '1'}">
						<li><a id="approveButton" class="button" href="#a"><span>확정</span></a></li>
						<li><a id="rejectButton" class="button" href="#a"><span>반려</span></a></li>
						</c:if>
					</c:if>
					<li><a class="button btn_list" href="#a"><span>목록</span></a></li>
				</ul>
			</div>  --%>
			</form>
		</div>
	</div>
	<!--//blockListTable End-->
</div>
