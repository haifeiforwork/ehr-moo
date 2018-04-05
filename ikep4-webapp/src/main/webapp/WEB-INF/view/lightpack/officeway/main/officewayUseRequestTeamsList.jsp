<%@ include file="/base/common/taglibs.jsp"%>
<%@ page language="java" errorPage="/common/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8"%>
<%@ taglib uri="/WEB-INF/tld/ikep4j.tld" prefix="ikep4j" %> 

<c:set var="preHeader"  value="ui.lightpack.meetingroom.header" /> 
<c:set var="preDetail"  value="ui.lightpack.meetingroom.detail" />
<c:set var="preButton"  value="ui.lightpack.meetingroom.button" /> 
<c:set var="preMessage" value="ui.lightpack.meetingroom.message" />
<c:set var="preSearch"  value="ui.communication.common.searchCondition" />

<script type="text/javascript" language="javascript">
//<!--
	var dialogWindow;

	//var excelBtnCnt = 0;
	function fnCaller(param, dialog){
	
		dialogWindow = dialog;
	}

	// 상단에 보이게 될 리스트를 가져오는 함수
	function getList() {
		
		$jq("#searchForm").attr("action", "<c:url value='/lightpack/officeway/officewayUseRequestTeamsList.do' />");
		$jq("#searchForm")[0].submit();
	}

	// 하단에 보이게 될 상세정보를 가져오는 함수
	function getView(teamId) {
		
		$jq("#teamId").val(teamId);
		
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
		$jq("#confirmButton1,#confirmButton2").click(function() {
				
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
				alert("부서를 선택해주세요.");
				return;
			}
			
			$jq('input[name=teamId]').val(tempItemId);
			$jq('input[name=status2]').val("C");
			if(confirm("선택한 부서의 요청사항을 검토완료하시겠습니까?")) {
				$jq("#regForm").attr("action", "<c:url value='/lightpack/officeway/officewayCheckBoxGroupRequest.do' />");
				$jq("#regForm")[0].submit();
			}  
		});
		
		$jq("#approveButton1,#approveButton2").click(function() {
		
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
			alert("부서를 선택해주세요.");
			return;
		}
		$jq('input[name=teamId]').val(tempItemId);
		$jq('input[name=status2]').val("A");
		if(confirm("선택한 부서의 요청사항을 승인하시겠습니까?")) {
			$jq("#regForm").attr("action", "<c:url value='/lightpack/officeway/officewayCheckBoxGroupRequest.do' />");
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
			alert("부서를 선택해주세요.");
			return;
		}
		$jq('input[name=teamId]').val(tempItemId);
		$jq('input[name=status2]').val("R");
		if(confirm("선택한 부서의 요청사항을 반려하시겠습니까?")) {
			$jq("#regForm").attr("action", "<c:url value='/lightpack/officeway/officewayCheckBoxGroupRequest.do' />");
			$jq("#regForm")[0].submit();
		}  
		});
		
		$jq("#excelButton").click(function() { 
			if(${searchResult.emptyRecord}){
				alert("검색 결과가 없습니다.");
				return false;
			}
			if("${price3}" == "0"){
				alert("다운로드할 목록이 없습니다.");
				return false;
			}
			//if(excelBtnCnt == 0){
				$jq("#searchForm").attr("action","<c:url value='/lightpack/officeway/downloadExcelOfficeway.do'/>");
				$jq("#searchForm")[0].submit();
			//	excelBtnCnt++;
			//}else{
			//	alert("조회 후 엑셀다운 받으세요");
			//}
			
			return false; 
		});
	});
//-->
</script>

<div id="reserveDiv">

	<!--blockListTable Start-->
	<div class="blockListTable">
		<div id="listDiv">
			<form id="regForm" name="regForm" method="post" action="">
				<input type="hidden" name="status2" value=""/> 
				<input type="hidden" name="teamId" value=""/> 
			</form>
			<form id="searchForm" name="searchForm" method="post" onsubmit="getList(); return false;" action="">
				<input type="hidden" id="teamId" name="teamId"/>
				
				<spring:bind path="searchCondition.sortColumn">
					<input name="${status.expression}" type="hidden" value="${status.value}" title="<ikep4j:message pre='${preSearch}' key='${status.expression}'/>" />
				</spring:bind>
				<spring:bind path="searchCondition.sortType">
					<input name="${status.expression}" type="hidden" value="${status.value}" title="<ikep4j:message pre='${preSearch}' key='${status.expression}'/>" />
				</spring:bind>
				
				<!--tableTop Start--> 
				<div class="tableTop">
					<div class="tableTop_bgR"></div>
					<h2>(전체)팀별 상신/결재</h2>
					<div class="listInfo"> 
						<%-- <spring:bind path="searchCondition.pagePerRecord">  
							<select name="${status.expression}" title='<ikep4j:message pre='${preSearch}' key='${status.expression}' />' onchange="javascript:getList()">
								<c:forEach var="code" items="${boardCode.pageNumList}">
									<option value="${code.key}" <c:if test="${code.key eq status.value}">selected="selected"</c:if>>${code.value}</option>
								</c:forEach> 
							</select> 
						</spring:bind> --%>
					</div>
					<div class="tableSearch"> 
						<%-- <spring:bind path="searchCondition.startYear">
		                    <select title="${status.expression}" name="${status.expression}">                               		
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
                        </spring:bind> --%>
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
						<%-- <spring:bind path="searchCondition.searchStatus">
						<select name="${status.expression}" onchange="getList();">
							<option value="" <c:if test="${'' eq status.value}">selected="selected"</c:if>>전체</option>
							<option value="MA" <c:if test="${'MA' eq status.value}">selected="selected"</c:if>>담당부서 확정</option>
							<option value="MR" <c:if test="${'MR' eq status.value}">selected="selected"</c:if>>담당부서 반려</option>
							<option value="MC" <c:if test="${'MC' eq status.value}">selected="selected"</c:if>>담당부서 상신</option>
							<option value="TA" <c:if test="${'TA' eq status.value}">selected="selected"</c:if>>팀장 승인</option>
						</select>
						</spring:bind> --%>
						<%-- <a id="searchBoardItemButton" name="searchBoardItemButton" href="#a" class="ic_search">
							<span><ikep4j:message pre='${preSearch}' key='search' /></span>
						</a> --%>
						<%-- <a href="#a" id="excelButton"><img src="<c:url value='/base/images/theme/theme01/basic/btn_excel.gif'/>" alt="" /></a> --%>
					</div>
					<div class="clear"></div>	
				</div>
				<div class="nblockButton"> 
					<c:if test="${ofmrRole}">
					<a id="confirmButton1" class="button_img01" href="#a"><span>상신</span></a>
					</c:if>
					<c:if test="${ofmlRole}">
					<a id="approveButton1" class="button_img01" href="#a"><span>확정</span></a>
					<!-- <li><a id="rejectButton" class="button" href="#a"><span>반려</span></a></li> -->
					</c:if>
			</div> 
				<!--//tableTop End-->	
				<%-- <c:if test="${ofmrRole}">
				<span style="float:right;">※ 상신은 팀장 승인 선택 검색 후 가능합니다.</span>
				</c:if>
				<c:if test="${ofmlRole}">
				<span style="float:right;">※ 확정, 반려는 담당부서 상신 선택 검색 후 가능합니다.</span>
				</c:if> --%>
				<table summary="<ikep4j:message pre='${preDetail}' key='myReserveList' />">
					<caption></caption>
						<colgroup>
							<col width="5%" />
							<col width="10%" />
							<col width="15%" />
							<col width="*" />
							<col width="10%" />
						</colgroup>
					<thead>
						<tr>
							<th scope="col"><input id="checkboxAllItem" class="checkbox" title="checkbox" type="checkbox" value="" /></th> 
							<th scope="col" style="border-left:1px solid #e0e0e0;">신청월</th>
							<th scope="col" style="border-left:1px solid #e0e0e0;">부서</th>
							<th scope="col" style="border-left:1px solid #e0e0e0;">품목건수</th>
							<th scope="col" style="border-left:1px solid #e0e0e0;">금액</th>
						</tr>
					</thead>
					<tbody>
						<c:choose>
							<c:when test="${searchResult.emptyRecord}">
								<tr>
									<td colspan="5" class="emptyRecord">
										<ikep4j:message pre='${preSearch}' key='emptyRecord' />
									</td> 
								</tr>				        
						    </c:when>
						    <c:otherwise>
								<c:forEach var="officeway" items="${searchResult.entity}" varStatus="status">
									<tr>
										<td class="textCenter">
											<c:if test="${ofmlRole && searchCondition.searchStatus == 'MC' && officeway.price2 != '0'}">
												<input name="checkboxItem" class="checkbox" title="checkbox" type="checkbox" value="${officeway.teamId}" /> 
											</c:if>
											<c:if test="${ofmrRole && searchCondition.searchStatus == 'TA' && officeway.price2 != '0'}">
												<input name="checkboxItem" class="checkbox" title="checkbox" type="checkbox" value="${officeway.teamId}" /> 
											</c:if>
										</td>
										<td class="textCenter" style="border-left:1px solid #e0e0e0;">
											<a href="<c:url value='/lightpack/officeway/officewayUseRequestOtherTeamListPayment.do?teamId=${officeway.teamId}'/>" >${nowYear}.${nowMonth}</a>
										</td>
										<td class="textCenter" style="border-left:1px solid #e0e0e0;">
											<a href="<c:url value='/lightpack/officeway/officewayUseRequestOtherTeamListPayment.do?teamId=${officeway.teamId}'/>" > ${officeway.teamName}</a>
										</td>
										<td class="textCenter" title="${officeway.productName}" style="border-left:1px solid #e0e0e0;">
											<a href="<c:url value='/lightpack/officeway/officewayUseRequestOtherTeamListPayment.do?teamId=${officeway.teamId}'/>"  ><u>${officeway.productCnt}</u></a>
										</td>
										<td class="textRight" style="border-left:1px solid #e0e0e0;">
											<a href="<c:url value='/lightpack/officeway/officewayUseRequestOtherTeamListPayment.do?teamId=${officeway.teamId}'/>" ><fmt:formatNumber value="${officeway.price2}" type="number" /></a>
										</td>
									</tr>
								</c:forEach>
								<tr style="background-color:#FAED7D;color:black;font-weight:bold;">
									<td class="textCenter" colspan="4">
										합계
									</td>
									<td class="textRight" style="border-left:1px solid #e0e0e0;">
										<fmt:formatNumber value="${price3}" type="number" />
									</td>
								</tr>
						    </c:otherwise>
						</c:choose>
					</tbody>
				</table>
				 <div class="blockBlank_10px"></div>
				<div class="nblockButton"> 
					<c:if test="${ofmrRole}">
					<a id="confirmButton2" class="button_img01" href="#a"><span>상신</span></a>
					</c:if>
					<c:if test="${ofmlRole}">
					<a id="approveButton2" class="button_img01" href="#a"><span>확정</span></a>
					<!-- <li><a id="rejectButton" class="button" href="#a"><span>반려</span></a></li> -->
					</c:if>
			</div> 
			</form>
		</div>
	</div>
	<!--//blockListTable End-->
</div>
