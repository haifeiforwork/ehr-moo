<%@ include file="/base/common/taglibs.jsp"%>
<%@ page language="java" errorPage="/common/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8"%>
<%@ taglib uri="/WEB-INF/tld/ikep4j.tld" prefix="ikep4j" %> 

<c:set var="preHeader"  value="ui.lightpack.meetingroom.header" /> 
<c:set var="preDetail"  value="ui.lightpack.meetingroom.detail" />
<c:set var="preButton"  value="ui.lightpack.meetingroom.button" /> 
<c:set var="preMessage" value="ui.lightpack.meetingroom.message" />
<c:set var="preSearch"  value="ui.communication.common.searchCondition" />
<%pageContext.setAttribute("crlf", "\r\n"); %>
<script type="text/javascript" language="javascript">
//<!--
	var dialogWindow;

	function fnCaller(param, dialog){
	
		dialogWindow = dialog;
	}

	// 상단에 보이게 될 리스트를 가져오는 함수
	function getList() {
		
		$jq("#searchForm").attr("action", "<c:url value='myCarReserveList.do' />");
		$jq("#searchForm")[0].submit();
	}

	// 하단에 보이게 될 상세정보를 가져오는 함수
	function getView(scheduleId) {
		if($jq("#contentsTR_"+scheduleId).css("display")=="none"){
			$jq("#contentsTR_"+scheduleId).show();
		}else{
			$jq("#contentsTR_"+scheduleId).hide();
		}

		/*
		$jq("#scheduleId").val(scheduleId);
		
		var url = "<c:url value='myCarReserveView.do' />";
		
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
		*/
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
		
		
		$jq("#btnDelete").click(function() {
			var itemIds = new Array();
			$jq("#searchForm input[name=chkScheduleId]:checked").each(function(index) { 
				itemIds.push($jq(this).val()); 
			});	
			
			if(itemIds.length > 0) {
				if(confirm("<ikep4j:message pre='${preMessage}' key='delete' />")) {
					$jq.ajax({
						url : '<c:url value="deleteReserveCar.do" />',     
						data : {
							scheduleIds : itemIds.toString()
						},     
						type : "post",     
						success : function(result) {      
							getList();
						},
						error : function(event, request, settings){
							 alert("error");
						}
					});
				}  
			} else {
				alert("<ikep4j:message pre="${preMessage}" key="selectDeleteItem" />");
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
				<input type="hidden" id="scheduleId" name="scheduleId"/>
				
				<spring:bind path="searchCondition.sortColumn">
					<input name="${status.expression}" type="hidden" value="${status.value}" title="<ikep4j:message pre='${preSearch}' key='${status.expression}'/>" />
				</spring:bind>
				<spring:bind path="searchCondition.sortType">
					<input name="${status.expression}" type="hidden" value="${status.value}" title="<ikep4j:message pre='${preSearch}' key='${status.expression}'/>" />
				</spring:bind>
				
				<!--tableTop Start-->  
				<div class="tableTop">
					<div class="tableTop_bgR"></div>
					<h2><ikep4j:message pre='${preDetail}' key='myCarReserveList' /></h2>
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
						<a id="searchBoardItemButton" name="searchBoardItemButton" href="#a" class="ic_search">
							<span><ikep4j:message pre='${preSearch}' key='search' /></span>
						</a>
					</div>
					<div class="clear"></div>	
				</div>
				<!--//tableTop End-->	
				
				<table summary="<ikep4j:message pre='${preDetail}' key='myReserveList' />">
					<caption></caption>
						<colgroup>
							<col width="3%"/>
							<col width="7%"/>
							<col width="20%"/>
							<col width="*"/>
							<col width="20%"/>
							<col width="10%"/>
							<col width="10%"/>
							<col width="10%"/>
						</colgroup>
					<thead>
						<tr>
							<th scope="col" class="textCenter">&nbsp;</th>
							<th scope="col">
								<ikep4j:message pre='${preDetail}' key='order' />
							</th>
							<th scope="col">
								<ikep4j:message pre='${preDetail}' key='reserveTime' />
							</th>
							<th scope="col">
								<a onclick="javascript:sort('TITLE', '<c:if test="${searchCondition.sortColumn eq 'TITLE'}">${searchCondition.sortType}</c:if>');"  href="#a">
									<ikep4j:message pre='${preDetail}' key='title' />
								</a>
							</th>
							<th scope="col">
								<a onclick="javascript:sort('PLACE', '<c:if test="${searchCondition.sortColumn eq 'PLACE'}">${searchCondition.sortType}</c:if>');"  href="#a">
								 	<ikep4j:message pre='${preDetail}' key='cartooletcName' />
								</a>
							</th>
							<th scope="col">
								<ikep4j:message pre='${preDetail}' key='reserveStatus' />
							</th>
							<th scope="col">
								요청자
							</th>
							<th scope="col">
								<ikep4j:message pre='${preDetail}' key='approveRequestDate' />
							</th>

						</tr>
					</thead>
					<tbody>
						<c:choose>
							<c:when test="${searchResult.emptyRecord}">
								<tr>
									<td colspan="8" class="emptyRecord">
										<ikep4j:message pre='${preSearch}' key='emptyRecord' />
									</td> 
								</tr>				        
						    </c:when>
						    <c:otherwise>
								<c:forEach var="reserve" items="${searchResult.entity}" varStatus="status">
									<tr style="cursor:pointer;" >
										<td class="textCenter">
											<input type="checkbox" id="chkScheduleId" name="chkScheduleId" value="${reserve.scheduleId}" title="checkbox"/>
							    		</td>
							    		<td class="textCenter" onclick="getView('${reserve.scheduleId}')">
							    			${reserve.num}
							    		</td>
							    		<td class="textCenter" onclick="getView('${reserve.scheduleId}')">
							    			${reserve.startDate} <br/>~ ${reserve.endDate}
							    		</td>
										<td class="textCenter" onclick="getView('${reserve.scheduleId}')">
											${reserve.title}
										</td>
										<td class="textCenter" onclick="getView('${reserve.scheduleId}')">
											${reserve.place}
										</td>
										<td class="textCenter" onclick="getView('${reserve.scheduleId}')">
											<c:choose>
											<c:when test="${reserve.approveStatus == 'A'}">
											<ikep4j:message pre='${preDetail}' key='approval' />
											</c:when>
											<c:when test="${reserve.approveStatus == 'W'}">
											<ikep4j:message pre='${preDetail}' key='wait' />
											</c:when>
											<c:when test="${reserve.approveStatus == 'S'}">
											1차승인
											</c:when>
											<c:when test="${reserve.approveStatus == 'L'}">
											2차승인
											</c:when>
											<c:when test="${reserve.approveStatus == 'R'}">
											<ikep4j:message pre='${preDetail}' key='reject' />
											</c:when>
											<c:otherwise>
											</c:otherwise>
											</c:choose>
										</td>
										<td class="textCenter" onclick="getView('${reserve.scheduleId}')">
											${reserve.registerName}
										</td>
										<td class="textCenter" onclick="getView('${reserve.scheduleId}')">
											${reserve.approveRegistDate}
										</td>
										
									</tr>
									<tr id="contentsTR_${reserve.scheduleId}" style="display:none">
										<td></td>
										<td></td>
										<td></td>
										<td style="text-align:left"><pre>${fn:replace( reserve.contents, crlf, "<br/>")}</pre></td>
										<td></td>
										<td>${reserve.rejectReason}</td>
										<td></td>
										<td></td>
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
	
			</form>
		</div>
	</div>
	<!--//blockListTable End-->
	
	
		<!--blockButton Start-->
	<div class="blockButton"> 
		<ul>
			<li><a id="btnDelete" class="button" href="#a"><span><ikep4j:message pre='${preButton}' key='delete' /></span></a></li>
		</ul>
	</div>
	<!--//blockButton End-->
</div>
