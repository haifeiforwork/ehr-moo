<%@ include file="/base/common/taglibs.jsp"%>
<%@ page language="java" errorPage="/common/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8"%>
<%@ taglib uri="/WEB-INF/tld/ikep4j.tld" prefix="ikep4j" %> 

<c:set var="preHeader"  value="ui.lightpack.meetingroom.header" /> 
<c:set var="preDetail"  value="ui.lightpack.meetingroom.detail" />
<c:set var="preButton"  value="ui.lightpack.meetingroom.button" /> 
<c:set var="preMessage" value="ui.lightpack.meetingroom.message" />
<c:set var="preSearch"  value="ui.communication.common.searchCondition" />

<c:set var="tmpStatus"  value="0" />
<c:forEach var="officesupplies" items="${searchResult.entity}" varStatus="status">
	<c:if test="${officesupplies.status1 == 'T'}">
		<c:set var="tmpStatus"  value="1" />
	</c:if>
</c:forEach>

<script type="text/javascript" language="javascript">
//<!--
	var dialogWindow;

	function fnCaller(param, dialog){
	
		dialogWindow = dialog;
	}

	// 상단에 보이게 될 리스트를 가져오는 함수
	function getList() {
		
		$jq("#searchForm").attr("action", "<c:url value='/collpack/collaboration/board/boardCommon/listConsultationRequestView.do' />");
		$jq("#searchForm")[0].submit();
	}
	

	// 하단에 보이게 될 상세정보를 가져오는 함수
	function getView(officesuppliesId) {
		
		$jq("#officesuppliesId").val(officesuppliesId);
		
		var url = "<c:url value='/lightpack/officesupplies/officesuppliesUseRequestView.do' />";
		
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
		
		$jq("#saveButton").click(function() {
				
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
			$jq('input[name=officesuppliesId]').val(tempItemId);
			$jq('input[name=status1]').val("S");
			if(confirm("선택한 사무용품을 신청하시겠습니까?")) {
				$jq("#regForm").attr("action", "<c:url value='/lightpack/officesupplies/officesuppliesCheckBoxUseRequest.do' />");
				$jq("#regForm")[0].submit();
			}  
		});
	});
//-->
</script>

<div id="reserveDiv">

	<!--blockListTable Start-->
	<div class="blockListTable">
		<div id="listDiv">
			<form id="searchForm" name="searchForm" method="post" onsubmit="getList(); return false;" action="">
				<input type="hidden" id="officesuppliesId" name="officesuppliesId"/>
				
				<spring:bind path="searchCondition.sortColumn">
					<input name="${status.expression}" type="hidden" value="${status.value}" title="<ikep4j:message pre='${preSearch}' key='${status.expression}'/>" />
				</spring:bind>
				<spring:bind path="searchCondition.sortType">
					<input name="${status.expression}" type="hidden" value="${status.value}" title="<ikep4j:message pre='${preSearch}' key='${status.expression}'/>" />
				</spring:bind>
				
				<!--tableTop Start-->  
				<div class="tableTop">
					<div class="tableTop_bgR"></div>
					<h2>상담 신청 현황</h2>
					<div class="listInfo"> 
						<spring:bind path="searchCondition.pagePerRecord">  
							<select name="${status.expression}" title='<ikep4j:message pre='${preSearch}' key='${status.expression}' />' onchange="javascript:getList()">
								<c:forEach var="code" items="${boardCode.pageNumList}">
									<option value="${code.key}" <c:if test="${code.key eq status.value}">selected="selected"</c:if>>${code.value}</option>
								</c:forEach> 
							</select> 
						</spring:bind>
						<div class="totalNum">
							${searchResult.pageIndex}/ ${searchResult.pageCount} <ikep4j:message pre='${preSearch}' key='page' /> 
							( <ikep4j:message pre='${preSearch}' key='pageCount.info' /><span> ${searchResult.recordCount}</span>)
						</div>
					</div>
					<div class="tableSearch"> 
					
						<input id="startPeriod" name="startPeriod" type="text" class="inputbox datepicker" value="${startPeriod}" size="10" title="<ikep4j:message pre='${preDetail}' key='date' />" readonly="readonly" /> 
						<img class="dateicon" src="<c:url value='/base/images/icon/ic_cal.gif'/>" alt="<ikep4j:message pre='${preDetail}' key='calendar' />" />
						~
						<input id="endPeriod" name="endPeriod" type="text" class="inputbox datepicker" value="${endPeriod}" size="10" title="<ikep4j:message pre='${preDetail}' key='date' />" readonly="readonly" /> 
						<img class="dateicon" src="<c:url value='/base/images/icon/ic_cal.gif'/>" alt="<ikep4j:message pre='${preDetail}' key='calendar' />" />
						<%-- <spring:bind path="searchCondition.searchColumn">  
                             <select name="${status.expression}" title='<ikep4j:message pre='${preSearch}' key='${status.expression}' />'>
                                 <option value="productNo" <c:if test="${'code' eq status.value}">selected="selected"</c:if> >제품번호</option>
                                 <option value="productName" <c:if test="${'title' eq status.value}">selected="selected"</c:if> >품목</option>
                             </select>   
                         </spring:bind>      
                         <spring:bind path="searchCondition.searchWord">                     
                             <input name="${status.expression}" value="${status.value}" type="text" class="inputbox" title='<ikep4j:message pre='${preSearch}' key='${status.expression}' />'  size="20" />
                         </spring:bind> --%>
                         &nbsp;&nbsp;
						<a id="searchBoardItemButton" name="searchBoardItemButton" href="#a" class="ic_search">
							<span><ikep4j:message pre='${preSearch}' key='search' /></span>
						</a>
					</div>
					<div class="clear"></div>	
				</div>
				<!--//tableTop End-->	
				
				<table>
					<caption></caption>
						<colgroup>
							<col width="20%" />
							<col width="20%" />
							<col width="20%" />
							<col width="20%" />
							<col width="20%" />
						</colgroup>
					<thead>
						<tr>
							<th scope="col">신청일</th>
							<th scope="col">신청자</th>
							<th scope="col">상담시간1</th>
							<th scope="col">상담시간2</th>
							<th scope="col">상담시간3</th>
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
								<c:forEach var="officesupplies" items="${searchResult.entity}" varStatus="status">
									<tr>
										<td class="textCenter">
											${officesupplies.appointmentDay}
										</td>
										<td class="textCenter">
											${officesupplies.registerName}
										</td>
										<td class="textCenter">
											${officesupplies.appointmentHour1}:${officesupplies.appointmentMinute1}
										</td>
										<td class="textCenter">
											${officesupplies.appointmentHour2}:${officesupplies.appointmentMinute2}
										</td>
										<td class="textCenter">
											${officesupplies.appointmentHour3}:${officesupplies.appointmentMinute3}
										</td>
									</tr>
								</c:forEach>
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
				<div class="blockButton"> 
				<ul>
					<c:if test="${periodCheck}">
						<li><a id='createBoardItemButton1' class='button' href="<c:url value='/lightpack/officesupplies/officesuppliesUseRequestForm.do'/>"><span>작성</span></a></li>
					</c:if>
					<c:if test="${tmpStatus == '1'}">
						<c:if test="${periodCheck}">
							<li><a id="saveButton" class="button" href="#a"><span>신청</span></a></li>
						</c:if>
						<li><a id='updateButton' class='button' href='<c:url value='/lightpack/officesupplies/officesuppliesUseRequestMyListUpdateForm.do'/>'><span>수정</span></a></li>
					</c:if>
				</ul>
			</div> 
			</form>
		</div>
	</div>
	<!--//blockListTable End-->
</div>
