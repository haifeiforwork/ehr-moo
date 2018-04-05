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

	function fnCaller(param, dialog){
	
		dialogWindow = dialog;
	}

	// 상단에 보이게 될 리스트를 가져오는 함수
	function getList() {
		
		$jq("#searchForm").attr("action", "<c:url value='/lightpack/usb/usbUseRequestAllList.do' />");
		$jq("#searchForm")[0].submit();
	}

	// 하단에 보이게 될 상세정보를 가져오는 함수
	function getView(usbId) {
		
		$jq("#usbId").val(usbId);
		
		var url = "<c:url value='/lightpack/usb/usbUseRequestView.do' />";
		
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
		
		
		$jq("#startPeriod").datepicker().next().eq(0).click(function() { 
			
			$jq(this).prev().eq(0).datepicker("show"); 
		});
		
		$jq("#endPeriod").datepicker().next().eq(0).click(function() { 
			
			$jq(this).prev().eq(0).datepicker("show"); 
		});
		
		$jq("#btn_reject_ok").click(function() {
			
			reject();
		});
		
		$jq("#btn_reject_cancel").click(function() {
			$jq("#reject-dialog").dialog("close");
		});
		
	});
	
	function assign(usbId) {
		
			$jq("#usbId").val(usbId);
			$jq("#searchForm").attr("action", "<c:url value='/lightpack/usb/usbApproveUseConfirm.do' />");
			$jq("#searchForm")[0].submit();
	};
	
	function rejectForm(usbId) {
		
		$jq("#usbId").val(usbId);
		$jq("#reject-dialog").dialog({
			
			modal:true,
			resizable : false,
			title : "<ikep4j:message pre='${preDetail}' key='rejectReason' />",
			open : function() { $jq("textarea", this).val(""); },
			close: function(e, ui) {
				
				//$jq("#btn_reject_cancel").unbind("click");
				$jq("#btn_reject_ok").unbind("click");
			}
		});
	}
	
	function reject() {
		
		var contents = $jq("#contents").val();
		//alert(contents);
		$jq("#rejectReason").val(contents);
		
		$jq("#searchForm").attr("action", "<c:url value='/lightpack/usb/usbRejectUse.do' />");
		$jq("#searchForm")[0].submit();
	}
//-->
</script>

<div id="reserveDiv">

	<!--blockListTable Start-->
	<div class="blockListTable">
		<div id="listDiv">
			<form id="searchForm" name="searchForm" method="post" onsubmit="getList(); return false;" action="">
				<input type="hidden" id="usbId" name="usbId"/>
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
					<h2>보안 예외 현황</h2>
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
					<div class="tableSearch"> 
						<input id="startPeriod" name="startPeriod" type="text" class="inputbox datepicker" value="${startPeriod}" size="10" title="<ikep4j:message pre='${preDetail}' key='date' />" readonly="readonly" /> 
						<img class="dateicon" src="<c:url value='/base/images/icon/ic_cal.gif'/>" alt="<ikep4j:message pre='${preDetail}' key='calendar' />" />
						~
						<input id="endPeriod" name="endPeriod" type="text" class="inputbox datepicker" value="${endPeriod}" size="10" title="<ikep4j:message pre='${preDetail}' key='date' />" readonly="readonly" /> 
						<img class="dateicon" src="<c:url value='/base/images/icon/ic_cal.gif'/>" alt="<ikep4j:message pre='${preDetail}' key='calendar' />" />
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
							<col width="18%"/>
							<col width="15%"/>
							<col width="*"/>
							<col width="8%"/>
							<col width="10%"/>
							<col width="10%"/>
							<col width="10%"/>
						</colgroup>
					<thead>
						<tr>
							<th scope="col">
								기간
							</th>
							<th scope="col">
								신청 유형
							</th>
							<th scope="col">
								사유
							</th>
							<th scope="col">
								부서
							</th>
							<th scope="col">
								신청자
							</th>
							<th scope="col">
								신청일
							</th>
							<th scope="col">
								상태
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
								<c:forEach var="usb" items="${searchResult.entity}" varStatus="status">
									<tr>
							    		<td class="textCenter">
							    			${usb.startDate} ~ 
							    			<c:if test="${usb.foreverYn=='1'}">영구</c:if>
							    			<c:if test="${usb.foreverYn!='1'}">${usb.endDate}</c:if>
							    		</td>
							    		<td class="textCenter" >
											<c:choose>
												<c:when test="${usb.reqType=='01'}">
												USB 허용
												</c:when>
												<c:when test="${usb.reqType=='02'}">
												워터마킹 해제
												</c:when>
												<c:when test="${usb.reqType=='03'}">
												ECM 다운로드 권한
												</c:when>
												<c:when test="${usb.reqType=='04'}">
												ECM 로컬 편집 권한
												</c:when>
												<c:otherwise></c:otherwise>
											</c:choose>
										</td>
										<td class="textCenter">
											<a href="<c:url value='/lightpack/usb/usbUseRequestAllView.do?usbId=${usb.usbId}'/>">
											${usb.requestReason}</a>
										</td>
										<td class="textCenter">
											${usb.groupName}
										</td>
										<td class="textCenter">
											${usb.registerName}
											<c:if test="${!empty usb.jobTitleName}">${usb.jobTitleName}</c:if>
											<c:if test="${empty usb.jobTitleName}">${usb.jobDutyName}</c:if>
										</td>
										<td class="textCenter">
											<fmt:formatDate value="${usb.registDate}" pattern="yyyy.MM.dd"/>
										</td>
										<td class="textCenter">
											<c:choose>
											<c:when test="${usb.approveStatus == 'A'}">
											<ikep4j:message pre='${preDetail}' key='approval' />
											<a class="button_ic" id="assignButton" href="#a" onclick="assign('${usb.usbId}'); return false;"><span>확인</span></a>
											</c:when>
											<c:when test="${usb.approveStatus == 'S'}">
												<ikep4j:message pre='${preDetail}' key='wait' />
											</c:when>
											<c:when test="${usb.approveStatus == 'R'}">
											<ikep4j:message pre='${preDetail}' key='reject' />
											</c:when>
											<c:when test="${usb.approveStatus == 'C'}">
											<ikep4j:message pre='${preDetail}' key='approval' />
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
				
				<input type="hidden" name="tempEquipmentId" id="tempEquipmentId" value="" />
	
			</form>
		</div>
	</div>
	<!--//blockListTable End-->
	<div id="reject-dialog" title="Dialog Title" class="none">
		<p><ikep4j:message pre='${preMessage}' key='rejectReason' /></p>
		<div>
			<table style="width:274px;">
				<tr>
					<td>
						<textarea id="contents" name="contents" class="w100" rows="5"></textarea>
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
