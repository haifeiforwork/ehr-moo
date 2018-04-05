<%@ include file="/base/common/taglibs.jsp"%>
<%@ page language="java" errorPage="/common/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8"%>
<%@ taglib uri="/WEB-INF/tld/ikep4j.tld" prefix="ikep4j" %> 

<c:set var="preHeader"  value="ui.lightpack.meetingroom.header" /> 
<c:set var="preDetail"  value="ui.lightpack.meetingroom.detail" />
<c:set var="preButton"  value="ui.lightpack.meetingroom.button" /> 
<c:set var="preMessage" value="ui.lightpack.meetingroom.message" />
<c:set var="preSearch"  value="ui.communication.common.searchCondition" />
<c:set var="user" value="${sessionScope['ikep.user']}" />
<script type="text/javascript" language="javascript">
//<!--

	// 상단에 보이게 될 리스트를 가져오는 함수
	function getList() {
		$jq("#searchForm").attr("action", "<c:url value='approveList.do' />");
		$jq("#searchForm")[0].submit();
	}

	// 하단에 보이게 될 상세정보를 가져오는 함수
	function getView(scheduleId) {
		
		$jq("#scheduleId").val(scheduleId);
		
		var url = "<c:url value='approveView.do' />";
		
		$jq.ajax({     
			
			url : url,    
			data : $jq("#searchForm").serialize(),
			type : "post",     
			loadingElement : {
				
				container : "#approveDiv"
			},
			success : function(result) {       
				
				$jq("#approveDiv").html(result);
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
	
	function assign(scheduleId, scheduleTitle) {
		
		
		if(confirm("<ikep4j:message pre='${preMessage}' key='assignConfirm' />")) {
			
			$jq("#scheduleId").val(scheduleId);
			$jq("#scheduleTitle").val(scheduleTitle);
			$jq("#searchForm").attr("action", "<c:url value='approveUse.do' />");
			$jq("#searchForm")[0].submit();
		}
	};
	
	function firstAssign(scheduleId ,scheduleTitle) {
		
		
		if(confirm("<ikep4j:message pre='${preMessage}' key='assignConfirm' />")) {
			
			$jq("#scheduleId").val(scheduleId);
			$jq("#scheduleTitle").val(scheduleTitle);
			$jq("#searchForm").attr("action", "<c:url value='approveFirstUse.do' />");
			$jq("#searchForm")[0].submit();
		}
	};
	
	function secondAssign(scheduleId ,scheduleTitle) {
		
		
		if(confirm("<ikep4j:message pre='${preMessage}' key='assignConfirm' />")) {
			
			$jq("#scheduleId").val(scheduleId);
			$jq("#scheduleTitle").val(scheduleTitle);
			$jq("#searchForm").attr("action", "<c:url value='approveSecondUse.do' />");
			$jq("#searchForm")[0].submit();
		}
	};
	
	function rejectForm(scheduleId, scheduleTitle) {
		
		$jq("#scheduleId").val(scheduleId);
		$jq("#scheduleTitle").val(scheduleTitle);
		$jq("#reject-dialog").dialog({
			
			modal:true,
			resizable : false,
			title : "<ikep4j:message pre='${preDetail}' key='rejectReason' />",
			open : function() { $jq("textarea", this).val(""); },
			close: function(e, ui) {
				
				$jq("#btn_reject_cancel").unbind("click");
				$jq("#btn_reject_ok").unbind("click");
			}
		});
	}
	
	function reject() {
		
		var contents = $jq("#contents").val();
		//alert(contents);
		$jq("#rejectReason").val(contents);
		
		$jq("#searchForm").attr("action", "<c:url value='rejectUse.do' />");
		$jq("#searchForm")[0].submit();
	}
	
	// 페이지 로딩시 실행하는 jQuery 코드로 list와 view를 불러온다.
	$jq(document).ready(function() {
		//left menu setting
		//$jq("#callManageOfLeft").click();
		
		$jq("#btn_reject_ok").click(function() {
			
			reject();
		});
		
		$jq("#btn_reject_cancel").click(function() {
			
			$jq("#reject-dialog").dialog("close");
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
			
			if (element != 'input' && element != 'textarea') {
				
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
	});

//-->
</script>

<div id="approveDiv">

	<!--blockListTable Start-->
	<div class="blockListTable">
		<div id="listDiv">
			<form id="searchForm" name="searchForm" method="post" onsubmit="getList(); return false;" action="">
				<input type="hidden" id="roomOrTool" name="roomOrTool" value="room"/>
				<input type="hidden" id="scheduleId" name="scheduleId"/>
				<input type="hidden" id="scheduleTitle" name="scheduleTitle" value=""/>
				<input type="hidden" id="rejectReason" name="rejectReason"/>
	
				<spring:bind path="searchCondition.sortColumn">
					<input name="${status.expression}" type="hidden" value="${status.value}" title="<ikep4j:message pre='${preSearch}' key='${status.expression}'/>" />
				</spring:bind>
				<spring:bind path="searchCondition.sortType">
					<input name="${status.expression}" type="hidden" value="${status.value}" title="<ikep4j:message pre='${preSearch}' key='${status.expression}'/>" />
				</spring:bind>
				
				<!--tableTop Start-->  
				<div class="tableTop">
					<div class="tableTop_bgR"></div>
					<h2><ikep4j:message pre='${preDetail}' key='reserveAgreement' /></h2>
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
						<spring:bind path="searchCondition.searchType">  
							<select name="${status.expression}" title="<ikep4j:message pre='${preDetail}' key='reserveStatus' />">
								<option value="ALL" <c:if test="${'ALL' eq status.value}">selected="selected"</c:if> >
									<ikep4j:message pre='${preDetail}' key='all' />
								</option>
								<option value="A" <c:if test="${'A' eq status.value}">selected="selected"</c:if> >
									<ikep4j:message pre='${preDetail}' key='approval' />
								</option>
								<option value="W" <c:if test="${'W' eq status.value}">selected="selected"</c:if> >
									<ikep4j:message pre='${preDetail}' key='wait' />
								</option>
								<option value="R" <c:if test="${'R' eq status.value}">selected="selected"</c:if> >
									<ikep4j:message pre='${preDetail}' key='reject' />
								</option>
							</select>	
						</spring:bind>
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
							<col width="10%"/>
							<col width="20%"/>
							<col width="*"/>
							<col width="19%"/>
							<col width="10%"/>
							<col width="10%"/>
							<col width="14%"/>
						</colgroup>
					<thead>
						<tr>
							<th scope="col">
								<a onclick="javascript:sort('SORT_ORDER', '<c:if test="${searchCondition.sortColumn eq 'SORT_ORDER'}">${searchCondition.sortType}</c:if>');"  href="#a">
									<ikep4j:message pre='${preDetail}' key='order' />
								</a>
							</th>
							<th scope="col">
								<ikep4j:message pre='${preDetail}' key='meetingTime' />
							</th>
							<th scope="col">
								<a onclick="javascript:sort('TITLE', '<c:if test="${searchCondition.sortColumn eq 'TITLE'}">${searchCondition.sortType}</c:if>');"  href="#a">
									<ikep4j:message pre='${preDetail}' key='title' />
								</a>
							</th>
							<th scope="col">
								<a onclick="javascript:sort('PLACE', '<c:if test="${searchCondition.sortColumn eq 'PLACE'}">${searchCondition.sortType}</c:if>');"  href="#a">
								 	<ikep4j:message pre='${preDetail}' key='meetingRoomName' />
								</a>
							</th>
							<th scope="col">
								요청자
							</th>
							<th scope="col">
								<ikep4j:message pre='${preDetail}' key='approveRequestDate' />
							</th>
							<th scope="col">
								<ikep4j:message pre='${preDetail}' key='reserveStatus' />
							</th>
						</tr>
					</thead>
					<tbody>
						<c:choose>
							<c:when test="${searchResult.emptyRecord}">
								<tr>
									<td colspan="7" class="emptyRecord">
										<ikep4j:message pre='${preSearch}' key='emptyRecord' />
									</td> 
								</tr>				        
						    </c:when>
						    <c:otherwise>
								<c:forEach var="approve" items="${searchResult.entity}" varStatus="status">
									<tr>
							    		<td class="textCenter" style="cursor:pointer;" onclick="getView('${approve.scheduleId}')">
							    			${approve.num}
							    		</td>
							    		<td class="textCenter" style="cursor:pointer;" onclick="getView('${approve.scheduleId}')">
							    			${approve.startDate} 
							    			<br/>
							    			~ ${approve.endDate}
							    		</td>
										<td class="textCenter" style="cursor:pointer;" onclick="getView('${approve.scheduleId}')">
											${approve.title}
										</td>
										<td class="textCenter" style="cursor:pointer;" onclick="getView('${approve.scheduleId}')">
											${approve.place}
										</td>
										<td class="textCenter">
											${approve.registerName}
										</td>
										<td class="textCenter">
											${approve.approveRegistDate}
										</td>
										<td class="textCenter">
											<c:choose>
												<c:when test="${approve.approveStatus == 'A'}">
													<ikep4j:message pre='${preDetail}' key='approval' />
												</c:when>
												<c:when test="${approve.approveStatus == 'W'}">
													<c:choose>
														<c:when test="${approve.isManager == 1}">
															<c:choose>
																<c:when test="${approve.managerId == user.userId || approve.subManagerId == user.userId || approve.lastManagerId == user.userId}">
																	<a class="button_ic" id="assignButton" href="#a" onclick="assign('${approve.scheduleId}','${approve.title}'); return false;"><span><ikep4j:message pre='${preDetail}' key='approval' /></span></a>
																	<a class="button_ic" id="rejectButton" href="#a" onclick="rejectForm('${approve.scheduleId}','${approve.title}'); return false;"><span><ikep4j:message pre='${preDetail}' key='reject' /></span></a>
																</c:when>
																<c:otherwise>
																	<ikep4j:message pre='${preDetail}' key='wait' />
																</c:otherwise>
															</c:choose>
														</c:when>
														<c:otherwise>
															<ikep4j:message pre='${preDetail}' key='wait' />
														</c:otherwise>
													</c:choose>
												</c:when>
												<c:when test="${approve.approveStatus == 'R'}">
													<ikep4j:message pre='${preDetail}' key='reject' />
												</c:when>
												<c:otherwise>
												</c:otherwise>
											</c:choose>
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
				
				<input type="hidden" name="tempEquipmentId" id="tempEquipmentId" value=""	/>
	
			</form>
		</div>
	</div>
	
	<div id="reject-dialog" title="Dialog Title" class="none">
		<p><ikep4j:message pre='${preMessage}' key='rejectReason' /></p>
		<div>
			<table style="width:274px;">
				<tr>
					<td>
						<textarea id="contents" name="contents" class="w100" title="<ikep4j:message pre='${preDetail}' key='rejectReason' />" cols="" rows="5"></textarea>
					</td>
				</tr>
			</table>
		</div>
		<div style="height:10px;"></div>
		<div class="blockButton"> 
			<ul>
				<li><a id="btn_reject_ok" href="#a" class="button"><span><ikep4j:message pre='${preButton}' key='reject' /></span></a></li>
				<li><a id="btn_reject_cancel" href="#a" class="button"><span><ikep4j:message pre='${preButton}' key='cancel' /></span></a></li>
			</ul>
		</div>
	</div>
</div>
<!--//blockListTable End-->