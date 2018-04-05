<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/base/common/taglibs.jsp"%>

<%@ taglib uri="/WEB-INF/tld/ikep4j.tld" prefix="ikep4j" %> 

<%-- 메시지 관련 Prefix 선언 Start --%> 
<c:set var="preApCommList"    value="ui.approval.work.apprlist" />
<c:set var="preList"    			value="ui.approval.work.aplist.listTodo" />
<c:set var="preButton"  			value="ui.approval.common.button" />
<c:set var="preSearch"  			value="ui.approval.common.searchCondition" />
<c:set var="preWorkplace"     value="ui.workflow.workplace.worklist" />
<c:set var="preWorkplaceCommon"  value="ui.workflow.workplace.common" />
 <%-- 메시지 관련 Prefix 선언 End --%>
 <script type="text/javascript" src="<c:url value="/base/js/jquery/jquery.validate-1.8.min.js"/>"></script>
<c:choose>
	<c:when test="${searchCondition.listType eq 'myRequestList'}">
		<c:set var="formActUrl"     value="/approval/work/apprlist/popListApprMyRequest.do" />
	</c:when>
	<c:when test="${searchCondition.listType eq 'myRequestListComplete'}">
		<c:set var="formActUrl"     value="/approval/work/apprlist/popListApprMyRequestCompleteSub.do" />
	</c:when>
	<c:when test="${searchCondition.listType eq 'listApprDocAllAdmin'}">
		<c:set var="formActUrl"     value="/approval/work/apprlist/listApprDocAllAdmin.do" />
	</c:when>
	<c:when test="${searchCondition.listType eq 'listApprDocAllUser'}">
		<c:set var="formActUrl"     value="/approval/work/apprlist/listApprDocAllUser.do" />
	</c:when>
	<c:when test="${searchCondition.listType eq 'listApprDocSecurity'}">
		<c:set var="formActUrl"     value="/approval/work/apprlist/listApprDocSecurity.do" />
	</c:when>
	<c:otherwise>
		<c:set var="formActUrl"     value="/approval/work/apprlist/popListApprMyRequest.do" />
	</c:otherwise>
</c:choose>	
			 
<script type="text/javascript">
<!-- 

//시작일과 종료일 체크하는 메써드 jQuery에 추가	
jQuery.validator.addMethod("greaterThan", function(value, element, params) {
	var val_Date = new Date(value.substring(0,4), value.substring(5,7), value.substring(8,10) );
	var par_Date = new Date(jQuery(params).val().substring(0,4), jQuery(params).val().substring(5,7), jQuery(params).val().substring(8,10) );
    if (!/Invalid|NaN/.test(val_Date)) {return val_Date >= par_Date;}
    return isNaN(value) && isNaN(jQuery(params).val()) || (parseFloat(value) >= parseFloat(jQuery(params).val())); 
});
jQuery.validator.addMethod("lesserThan", function(value, element, params) {
	var val_Date = new Date(value.substring(0,4), value.substring(5,7), value.substring(8,10) );
	var par_Date = new Date(jQuery(params).val().substring(0,4), jQuery(params).val().substring(5,7), jQuery(params).val().substring(8,10) );
    if (!/Invalid|NaN/.test(val_Date)) {return val_Date <= par_Date;}
    return isNaN(value) && isNaN(jQuery(params).val()) || (parseFloat(value) <= parseFloat(jQuery(params).val())); 
});

(function($) {

	//주의 : 글로벌 함수는 아래와 같이 글로벌 변수에 무명 함수를 assign해서 생성한다.
	
	/**
	 * 정렬 조건 변경 함수
	 * 
	 * @param {Object} sortColumn
	 * @param {Object} sortType
	 */

	 f_Sort = function(sortColumn, sortType) {
		$("input[name=sortColumn]").val(sortColumn);
		
		if(sortType == '') sortType = 'ASC';
		
		if( sortType == 'ASC') {
			$("input[name=sortType]").val('DESC');	
		} else {
			$("input[name=sortType]").val('ASC');
		}
		
		$("#searchApListButton").click();
	};
	
	getViewApprLine = function(apprId) {
		
		var $divContext = $jq("#"+apprId),
			$trRow = $jq("#apprLineTr"+apprId);
		
		var $divContext = $jq("#"+apprId),
		$trRow = $jq("#apprLineTr"+apprId);
	
		if($divContext.is(":hidden")) {
			
			$jq("#listTable").ajaxLoadStart("button");
			
			$jq("tr[id^=apprLineTr]^").each(function() { 
				var rowApprid = $(this).find("div").attr("id");
				
				$jq("#"+rowApprid).empty();
				$jq("#apprLineTr"+rowApprid).css("display","block");
				$jq("#"+rowApprid).hide();
				$jq("#apprLineTr"+rowApprid).hide();
			});
			
			$.post("<c:url value='/approval/work/apprLine/listApprLineInfo.do'/>", {apprId	:	apprId})
				.success(function(result) {
					$divContext.append(result);
					$trRow.css("display","");
					$divContext.show();
					$jq("#listTable").ajaxLoadComplete();
				})
				.error(function(event, request, settings) { alert("error");$jq("#listTable").ajaxLoadComplete(); });
		}else {
			$divContext.empty();
			$trRow.css("display","block");
			$divContext.hide();
			$trRow.hide();
		}
		
	};
	
	setUser = function(data) {
		var name="";
		var userId="";
		
		$jq.each(data, function() {
			name = $jq.trim(this.userName);
			userId = $jq.trim(this.id);	
		})
		$jq("#userName").val(name);
		$jq("#searchUserId").val(userId);
	};
	
	clearUser = function(targetId) {  //alert(iKEP.debug($jq("#userName").val()));
			$jq("#userName").val("");
			$jq("#searchUserId").val("");
	};
	
	getApprDetail = function(apprId) {
		//location.href="<c:url value='/approval/work/apprWorkDoc/viewApprDoc.do'/>?apprId="+apprId+"&listType=${searchCondition.listType}";
		
		iKEP.showDialog({     
			title 		: "문서 상세",
	    	url 		: iKEP.getContextRoot() + "/approval/work/apprWorkDoc/viewApprDoc.do?&popupYn=true&apprId=" + apprId + "&listType=listApprIntegrate",
	    	modal 		: true,
	    	width 		: 850,
	    	height 		: 500,
	    	callback	: function(result) {
			    
			}
		});
	};
	
	setSecurity = function(val) {
		var apprIds = new Array();
		var confirmMsg = "", alertMsg="";
		$("#searchForm input[name=apprId]:checked").each(function(index) { 
			apprIds.push($(this).val()); 
		});		

		if(val == "0") {
			confirmMsg = "<ikep4j:message pre='${preApCommList}' key='confirmClearSecurity' />";
			alertMsg = "<ikep4j:message pre='${preApCommList}' key='checkSecurity' />";
		}else if(val == "1") {
			confirmMsg = "<ikep4j:message pre='${preApCommList}' key='confirmSecurity' />";
			alertMsg = "<ikep4j:message pre='${preApCommList}' key='checkClearSecurity' />";
		}
		
		if(apprIds == "") {
			alert(alertMsg);
			return;
		}
		if(confirm(confirmMsg)) {
			$.post("<c:url value='/approval/work/apprlist/updateSetSecurity.do'/>", {"apprIds" : apprIds.toString(),"isHidden" : val }) 
			.success(function(result) {  
				if(result == "OK") {
					location.href="<c:url value='/approval/work/apprlist/listApprDocSecurity.do'/>";
				}
			});
		} 
	};
	
	/**
	 * onload시 수행할 코드
	 */
	$(document).ready(function() {
		<c:choose>
			<c:when test="${searchCondition.listType eq 'myRequestList'}">
				$jq("#apprMyrequestOfLeft").click();
			</c:when>
			<c:when test="${searchCondition.listType eq 'myRequestListComplete'}">
				$jq("#apprMyRequestCompleteOfLeft").click();
			</c:when>
			<c:when test="${searchCondition.listType eq 'listApprDocAllAdmin'}">
				$jq("#apprDocAllAdminLinkOfLeft").click();
			</c:when>
			<c:when test="${searchCondition.listType eq 'listApprDocAllUser'}">
				$jq("#apprDocAllUserOfLeft").click();
			</c:when>
			<c:when test="${searchCondition.listType eq 'listApprDocSecurity'}">
				$jq("#apprDocSecurityLinkOfLeft").click();
			</c:when>
			<c:otherwise>
				$jq("#apprMyrequestOfLeft").click();
			</c:otherwise>
		</c:choose>	
		
		//입력박스에 값을 입력하고 EnterKey를 눌렀을때 이벤트 바인딩
		$jq('#userName').keypress( function(event) { 
				iKEP.searchUser(event, $jq('#userNameIsMulti').val(), setUser); 
		});
		
		//검색버튼을 클릭했을때 이벤트 바인딩
		$jq('#userSearchBtn').click( function() { 
				$jq('#userName').trigger("keypress");
		});

    	//삭제 버튼을 클릭했을때 이벤트 바인딩
		$jq('#userRemoveBtn').click(function(event) {
				$jq("#userName").val("");
				$jq("#searchUserId").val("");
		});
		
		$("#searchStartDate").datepicker().next().eq(0).click(function() { $("#searchStartDate").datepicker("show"); });
		$("#searchEndDate").datepicker().next().eq(0).click(function() { $("#searchEndDate").datepicker("show"); });
		
		$("#searchApListButton").click(function() {
			$("input[name=pageIndex]").val('1');
			$("#searchForm").submit(); 
			return false; 
		});
		
		/**
		 * 페이징 버튼
		 */
		$("#pageIndex").click(function() {
			$("#searchForm").submit(); 
			return false; 
		});
		
		$("select[name=pagePerRecord]").change(function(){
            $("#pageIndex").click();
        });
 
		$("#listApButton").click(function() {   
			location.href='listApTempSearch.do';
		});
		
		$("#createApButton").click(function() {   
			location.href='createApForm.do';
		});
		
		$("#allCheck").click(function() { 
			$("#searchForm input[name=apprId]").attr("checked", $(this).is(":checked"));  
		});  
		
	});
})(jQuery);  
//-->
</script>
			

		<!--blockSearch Start-->
		<form id="searchForm" method="post" action="<c:url value='${formActUrl}' />">
			<input name="apprIds" type="hidden" value="" title="hidden" />
			<spring:bind path="searchCondition.sortColumn">
			<input name="${status.expression}" type="hidden" value="${status.value}" title="<ikep4j:message pre='${preSearch}' key='${status.expression}'/>" />
			</spring:bind> 		
			<spring:bind path="searchCondition.sortType">
			<input name="${status.expression}" type="hidden" value="${status.value}" title="<ikep4j:message pre='${preSearch}' key='${status.expression}'/>" />
			</spring:bind> 
		
		<div class="blockSearch">
			<div class="corner_RoundBox03">
				<table summary="<ikep4j:message pre='${preApCommList}.pageTitle' key='${searchCondition.listType}' />">
					<caption></caption>
					<tbody>
						<c:choose>
							<c:when test="${searchCondition.listType eq 'listApprDocAllAdmin' or searchCondition.listType eq 'listApprDocAllUser' or searchCondition.listType eq 'listApprDocSecurity'}">
							<tr>
								<spring:bind path="searchCondition.searchApprDocType">
								<th scope="row" width="5%"><ikep4j:message pre='${preSearch}' key='${status.expression}' /></th>
								<td width="25%">
									<select title="<ikep4j:message pre='${preSearch}' key='${status.expression}' />" name="${status.expression}">
										<c:forEach var="apCode" items="${docTypeList}">
											<option value="${apCode.key}" <c:if test="${apCode.key eq status.value}">selected="selected"</c:if>><spring:message code="${apCode.value}" /></option>
										</c:forEach>
									</select>
								</td>
								</spring:bind>
								<spring:bind path="searchCondition.searchApprDocStatus">
								<th scope="row" width="5%"><ikep4j:message pre='${preSearch}' key='${status.expression}' /></th>
								<td width="25%">
									<select title="<ikep4j:message pre='${preSearch}' key='${status.expression}' />" name="${status.expression}">
										<c:forEach var="apCode" items="${docStatusAllList}">
											<c:if test="${searchCondition.listType eq 'listApprDocAllUser'}">
												<c:if test="${apCode.key ne '0'}">
												<option value="${apCode.key}" <c:if test="${apCode.key eq status.value}">selected="selected"</c:if>><spring:message code="${apCode.value}" /></option>
												</c:if>
											</c:if>
											<c:if test="${searchCondition.listType ne 'listApprDocAllUser'}">
												<option value="${apCode.key}" <c:if test="${apCode.key eq status.value}">selected="selected"</c:if>><spring:message code="${apCode.value}" /></option>
											</c:if>
										</c:forEach>
									</select>
								</td>
								</spring:bind>
							</tr>
							</c:when>
						</c:choose>
						<tr>
							<spring:bind path="searchCondition.searchFormName">
							<th scope="row" width="5%"><ikep4j:message pre='${preSearch}' key='${status.expression}' /></th>
							<td width="25%">
								<input type="text" class="inputbox" title="<ikep4j:message pre='${preSearch}' key='${status.expression}' />" name="${status.expression}" value="${status.value}" size="35" />
							</td>
							</spring:bind>
							<c:choose>
							<c:when test="${searchCondition.listType eq 'listApprDocAllAdmin' or searchCondition.listType eq 'listApprDocAllUser' or searchCondition.listType eq 'listApprDocSecurity'}">
								<spring:bind path="searchCondition.searchUserId">
								<th scope="row" width="5%"><ikep4j:message pre='${preSearch}' key='${status.expression}' /></th>
								<td width="25%">
									<input type="text" class="inputbox" title="<ikep4j:message pre='${preSearch}' key='${status.expression}' />" name="userName" id="userName" value="<c:out value="${searchUserName}"/>" size="10" readonly="readonly"/>&nbsp;
									<a class="button_ic valign_bottom" href="#"><span><img src="/ikep4-webapp/base/images/icon/ic_btn_search.gif" name="userSearchBtn" id="userSearchBtn" alt="" />Search</span></a>
								  &nbsp;
									<a class="button_ic valign_bottom" href="#"><span><img src="/ikep4-webapp/base/images/icon/ic_btn_delete.gif"  name="userRemoveBtn" id="userRemoveBtn" alt="" />Delete</span></a>
									<input type="hidden" name="userNameIsMulti" id="userNameIsMulti" value="N" />
									<input type="hidden" name="searchUserId" id="searchUserId" value="${status.value}" />
								</td>
								</spring:bind>
							</c:when>
							<c:when test="${searchCondition.listType eq 'myRequestList' or searchCondition.listType eq 'myRequestListComplete'}">
								<th scope="row" width="5%"><ikep4j:message pre='${preSearch}' key='apprReqDate' /></th>
								<td width="25%">
									<spring:bind path="searchCondition.searchStartDate">
										<input type="text" class="inputbox" id="${status.expression}" name="${status.expression}" title="<ikep4j:message pre='${preSearch}' key='${status.expression}' />" value="${status.value}" size="10" /> <img class="dateicon" src="/ikep4-webapp/base/images/icon/ic_cal.gif" alt="달력" /> ~
									</spring:bind>
									<spring:bind path="searchCondition.searchEndDate">
										<input type="text" class="inputbox" id="${status.expression}" name="${status.expression}" title="<ikep4j:message pre='${preSearch}' key='${status.expression}' />" value="${status.value}" size="10" /> <img class="dateicon" src="/ikep4-webapp/base/images/icon/ic_cal.gif" alt="달력" />
									</spring:bind>							
								</td>
							</c:when>
							<c:otherwise>
								
							</c:otherwise>
							</c:choose>
						</tr>
						<tr>
							<c:choose>
							<c:when test="${searchCondition.listType eq 'myRequestListComplete' }">
								<spring:bind path="searchCondition.searchApprDocNo">
								<th scope="row" width="5%"><ikep4j:message pre='${preSearch}' key='searchApprDocNo'/></th>
								<td width="25%">								
									<input type="text" class="inputbox" title="<ikep4j:message pre='${preSearch}' key='searchApprDocNo'/>" name="${status.expression}" value="${status.value}" size="35" />
								</td>		
								</spring:bind>	
								<spring:bind path="searchCondition.searchApprTitle">
								<th scope="row"><ikep4j:message pre='${preSearch}' key='apprTitle'/></th>
								<td width="25%">								
									<input type="text" class="inputbox" title="<ikep4j:message pre='${preSearch}' key='apprTitle'/>" name="${status.expression}" value="${status.value}" size="35" />
								</td>		
								</spring:bind>
							</c:when>
							<c:when test="${searchCondition.listType eq 'myRequestList'}">
								<spring:bind path="searchCondition.searchApprTitle">
								<th scope="row"><ikep4j:message pre='${preSearch}' key='apprTitle'/></th>
								<td colspan="3">								
									<input type="text" class="inputbox" title="<ikep4j:message pre='${preSearch}' key='apprTitle'/>" name="${status.expression}" value="${status.value}" size="35" />
								</td>		
								</spring:bind>
							</c:when>
							<c:otherwise>
								<th scope="row" width="5%"><ikep4j:message pre='${preSearch}' key='apprReqDate' /></th>
								<td width="25%">
									<spring:bind path="searchCondition.searchStartDate">
										<input type="text" class="inputbox" id="${status.expression}" name="${status.expression}" title="<ikep4j:message pre='${preSearch}' key='${status.expression}' />" value="${status.value}" size="10" /> <img class="dateicon" src="/ikep4-webapp/base/images/icon/ic_cal.gif" alt="달력" /> ~
									</spring:bind>
									<spring:bind path="searchCondition.searchEndDate">
										<input type="text" class="inputbox" id="${status.expression}" name="${status.expression}" title="<ikep4j:message pre='${preSearch}' key='${status.expression}' />" value="${status.value}" size="10" /> <img class="dateicon" src="/ikep4-webapp/base/images/icon/ic_cal.gif" alt="달력" />
									</spring:bind>							
								</td>
								<spring:bind path="searchCondition.searchApprTitle">
								<th scope="row"><ikep4j:message pre='${preSearch}' key='apprTitle'/></th>
								<td width="25%">								
									<input type="text" class="inputbox" title="<ikep4j:message pre='${preSearch}' key='apprTitle'/>" name="${status.expression}" value="${status.value}" size="35" />
								</td>		
								</spring:bind>
							</c:otherwise>
							</c:choose>					
						</tr>	
						<c:choose>
							<c:when test="${searchCondition.listType eq 'listApprDocAllUser' or searchCondition.listType eq 'listApprDocAllAdmin'}">
							<tr>
								<spring:bind path="searchCondition.searchApprDocNo">
								<th scope="row" width="5%"><ikep4j:message pre='${preSearch}' key='searchApprDocNo'/></th>
								<td colspan="3">								
									<input type="text" class="inputbox" title="<ikep4j:message pre='${preSearch}' key='searchApprDocNo'/>" name="${status.expression}" value="${status.value}" size="35" />
								</td>		
								</spring:bind>	
							</tr>
							</c:when>
							<c:when test="${searchCondition.listType eq 'listApprDocSecurity' }">
							<tr>
								<spring:bind path="searchCondition.searchApprDocNo">
								<th scope="row" width="5%"><ikep4j:message pre='${preSearch}' key='searchApprDocNo'/></th>
								<td width="25%">								
									<input type="text" class="inputbox" title="<ikep4j:message pre='${preSearch}' key='searchApprDocNo'/>" name="${status.expression}" value="${status.value}" size="35" />
								</td>		
								</spring:bind>	
								<spring:bind path="searchCondition.searchIsHidden">
								<th scope="row" width="5%"><ikep4j:message pre='${preSearch}' key='${status.expression}' /></th>
								<td width="25%">
									<select title="<ikep4j:message pre='${preSearch}' key='${status.expression}' />" name="${status.expression}">
										<c:forEach var="apCode" items="${docSecurityList}">
											<option value="${apCode.key}" <c:if test="${apCode.key eq status.value}">selected="selected"</c:if>><spring:message code="${apCode.value}" /></option>
										</c:forEach>
									</select>
								</td>
								</spring:bind>
							</tr>
							</c:when>
						</c:choose>							
					</tbody>
				</table>
				<div class="searchBtn">
					<a id="searchApListButton" name="searchApListButton" href="#a"><img src="<c:url value='/base/images/theme/theme01/basic/btn_search.gif' />" alt="검색" /></a>
				</div>
				
				<div class="l_t_corner"></div>
				<div class="r_t_corner"></div>
				<div class="l_b_corner"></div>
				<div class="r_b_corner"></div>
			</div>
		</div>	
		<!--//blockSearch End-->
		
		<!--blockListTable Start-->
		<div class="blockListTable">
		
			<!--tableTop Start-->
			<div class="tableTop">
				<div class="tableTop_bgR"></div> 
				<div class="listInfo">
					<spring:bind path="searchCondition.pagePerRecord">
					<select name="${status.expression}" title='<ikep4j:message pre='${preSearch}' key='${status.expression}' />'>
						<c:forEach var="commonCode" items="${commonCode}">
							<option value="${commonCode.key}" <c:if test="${commonCode.key eq status.value}">selected="selected"</c:if>>${commonCode.value}</option>
						</c:forEach> 
					</select> 
					</spring:bind>
					<div class="totalNum"><ikep4j:message pre='${preWorkplaceCommon}' key='pageAll' /><span> ${searchResult.recordCount}</span></div>
					<div align="right">
						<c:choose>
							<c:when test="${searchCondition.listType eq 'listApprDocAllAdmin' or searchCondition.listType eq 'listApprDocAllUser' or searchCondition.listType eq 'listApprDocSecurity'}">
								<img style="vertical-align:text-top;" src="<c:url value='/base/images/icon/signal_01.png'/>" alt="<ikep4j:message pre='${preApCommList}' key='display.progress'/>" /><ikep4j:message pre='${preApCommList}' key='display.progress'/>
								<img style="vertical-align:text-top;" src="<c:url value='/base/images/icon/signal_02.png'/>" alt="<ikep4j:message pre='${preApCommList}' key='display.complete'/>" /><ikep4j:message pre='${preApCommList}' key='display.complete'/>
								<img style="vertical-align:text-top;" src="<c:url value='/base/images/icon/signal_03.png'/>" alt="<ikep4j:message pre='${preApCommList}' key='display.reject'/>" /><ikep4j:message pre='${preApCommList}' key='display.reject'/>
								<img style="vertical-align:text-top;" src="<c:url value='/base/images/icon/signal_04.png'/>" alt="<ikep4j:message pre='${preApCommList}' key='display.return'/>" /><ikep4j:message pre='${preApCommList}' key='display.return'/>
								<img style="vertical-align:text-top;" src="<c:url value='/base/images/icon/signal_07.png'/>" alt="<ikep4j:message pre='${preApCommList}' key='display.return1'/>" /><ikep4j:message pre='${preApCommList}' key='display.return1'/>
								<img style="vertical-align:text-top;" src="<c:url value='/base/images/icon/signal_06.png'/>" alt="<ikep4j:message pre='${preApCommList}' key='display.wait'/>" /><ikep4j:message pre='${preApCommList}' key='display.wait'/>
								<c:if test="${searchCondition.listType ne 'listApprDocAllUser' }">
								<img style="vertical-align:text-top;" src="<c:url value='/base/images/icon/signal_00.png'/>" alt="<ikep4j:message pre='${preApCommList}' key='display.temp'/>" /><ikep4j:message pre='${preApCommList}' key='display.temp'/>
								</c:if>
							</c:when>
							<c:when test="${searchCondition.listType eq 'myRequestList'}">
								<img style="vertical-align:text-top;" src="<c:url value='/base/images/icon/signal_01.png'/>" alt="<ikep4j:message pre='${preApCommList}' key='display.progress'/>" /><ikep4j:message pre='${preApCommList}' key='display.progress'/>
							</c:when>
						</c:choose>
						<c:if test="${searchCondition.listType eq 'listApprDocSecurity' }">
							&nbsp;&nbsp;&nbsp;&nbsp; 
							<img style="vertical-align:text-top;" src="<c:url value='/base/images/icon/ic_btn_key.gif'/>" alt="<ikep4j:message pre='${preApCommList}' key='display.setSecurity'/>" /><ikep4j:message pre='${preApCommList}' key='display.setSecurity'/>
							<img style="vertical-align:text-top;" src="<c:url value='/base/images/icon/ic_document.png'/>" alt="<ikep4j:message pre='${preApCommList}' key='display.clearSecurity'/>" /><ikep4j:message pre='${preApCommList}' key='display.clearSecurity'/>
							
						</c:if>
					</div>			
				</div>			
				<div class="clear"></div>
			</div>
			<!--//tableTop End-->
			<table summary="<ikep4j:message pre='${preApCommList}.pageTitle' key='${searchCondition.listType}' />" id="listTable">
				<caption></caption>
				<colgroup>
				<c:choose>
					<c:when test="${searchCondition.listType eq 'listApprDocAllAdmin' or searchCondition.listType eq 'listApprDocAllUser'}">
						<col width="13%"/>
						<col width="10%"/>
						<col width="10%"/>
						<col width="32%"/>
						<col width="10%"/>
						<col width="15%"/>
						<col width="10%"/>
					</c:when>
					<c:when test="${searchCondition.listType eq 'listApprDocSecurity'}">
						<col width="5%"/>
						<col width="10%"/>
						<col width="10%"/>
						<col width="8%"/>
						<col width="26%"/>
						<col width="10%"/>
						<col width="15%"/>
						<col width="8%"/>
						<col width="8%"/>
					</c:when>
					<c:when test="${searchCondition.listType eq 'myRequestListComplete'}">
						<col width="2%"/>
						<col width="15%"/>
						<col width="10%"/>
						<col width="8%"/>
						<col width="50%"/>
						<col width="15%"/>
					</c:when>
					<c:otherwise>
						<col width="5%"/>
						<col width="10%"/>
						<col width="10%"/>
						<col width="40%"/>
						<col width="15%"/>
						<col width="15%"/>
					</c:otherwise>
				</c:choose>
				</colgroup>
				<tbody>
					<tr>
						<th scope="col"><input name="allCheck" id="allCheck" class="checkbox" title="checkbox" type="checkbox" value="" /></th>
						<th scope="col">
							<c:choose>
								<c:when test="${searchCondition.listType eq 'myRequestListComplete' or searchCondition.listType eq 'listApprDocAllAdmin' or searchCondition.listType eq 'listApprDocAllUser' or searchCondition.listType eq 'listApprDocSecurity'}">
									<ikep4j:message pre='${preSearch}' key='apprDocNo' />
								</c:when>
								<c:otherwise>
									<ikep4j:message pre='${preWorkplace}' key='number' />
								</c:otherwise>
							</c:choose>
						</th>
						<th scope="col"><ikep4j:message pre='${preSearch}' key='apprDocType' /></th>
						<th scope="col"><ikep4j:message pre='${preSearch}' key='codeName' /></th>
						<th scope="col">
							<a onclick="f_Sort('apprTitle', '<c:if test="${searchCondition.sortColumn eq 'apprTitle'}">${searchCondition.sortType}</c:if>');"  href="#a">
								<ikep4j:message pre='${preSearch}' key='apprTitle' />&nbsp;&nbsp;
							</a>
							<c:choose>
							    <c:when test="${searchCondition.sortColumn eq 'apprTitle' and searchCondition.sortType eq 'DESC'}"><img alt="desc" src="<c:url value='/base/images/icon/ic_tablesort_down.gif'/>"></c:when>
							    <c:when test="${searchCondition.sortColumn eq 'apprTitle' and searchCondition.sortType eq 'ASC'}"><img alt="asc" src="<c:url value='/base/images/icon/ic_tablesort_up.gif'/>"></c:when>
						    </c:choose>
						</th>
						<c:choose>
							<c:when test="${searchCondition.listType eq 'listApprDocAllAdmin' or searchCondition.listType eq 'listApprDocAllUser' or searchCondition.listType eq 'listApprDocSecurity'}">
								<th scope="col"><ikep4j:message pre='${preSearch}' key='registUserId' /></th>
							</c:when>
						</c:choose>
						<th scope="col">
						    <c:choose>
						 			<c:when test="${searchCondition.listType ne 'myRequestList' or searchCondition.listType eq 'myRequestListComplete'}">
										<a onclick="f_Sort('apprReqDate', '<c:if test="${searchCondition.sortColumn eq 'apprReqDate'}">${searchCondition.sortType}</c:if>');"  href="#a">
										<ikep4j:message pre='${preSearch}' key='apprReqDate' />&nbsp;&nbsp;
										</a>
										<c:choose>
										    <c:when test="${searchCondition.sortColumn eq 'apprReqDate' and searchCondition.sortType eq 'DESC'}"><img alt="desc" src="<c:url value='/base/images/icon/ic_tablesort_down.gif'/>"></c:when>
										    <c:when test="${searchCondition.sortColumn eq 'apprReqDate' and searchCondition.sortType eq 'ASC'}"><img alt="asc" src="<c:url value='/base/images/icon/ic_tablesort_up.gif'/>"></c:when>
									    </c:choose>
						 			</c:when>
						 			<c:otherwise>
						 				<a onclick="f_Sort('apprReqDate', '<c:if test="${searchCondition.sortColumn eq 'apprReqDate'}">${searchCondition.sortType}</c:if>');"  href="#a">
										<ikep4j:message pre='${preSearch}' key='apprReqDate' />&nbsp;&nbsp;
										</a>
										<c:choose>
										    <c:when test="${searchCondition.sortColumn eq 'apprReqDate' and searchCondition.sortType eq 'DESC'}"><img alt="desc" src="<c:url value='/base/images/icon/ic_tablesort_down.gif'/>"></c:when>
										    <c:when test="${searchCondition.sortColumn eq 'apprReqDate' and searchCondition.sortType eq 'ASC'}"><img alt="asc" src="<c:url value='/base/images/icon/ic_tablesort_up.gif'/>"></c:when>
									    </c:choose>
						 			</c:otherwise>
								</c:choose>					
							</a>
						</th>
						<%-- <th scope="col">
							<c:choose>
								<c:when test="${searchCondition.listType eq 'myRequestListComplete'}">
									<a onclick="f_Sort('apprEndDate', '<c:if test="${searchCondition.sortColumn eq 'apprEndDate'}">${searchCondition.sortType}</c:if>');"  href="#a">
									<ikep4j:message pre='${preSearch}' key='apprEndDate' />&nbsp;&nbsp;
									</a>
									<c:choose>
									    <c:when test="${searchCondition.sortColumn eq 'apprEndDate' and searchCondition.sortType eq 'DESC'}"><img alt="desc" src="<c:url value='/base/images/icon/ic_tablesort_down.gif'/>"></c:when>
									    <c:when test="${searchCondition.sortColumn eq 'apprEndDate' and searchCondition.sortType eq 'ASC'}"><img alt="asc" src="<c:url value='/base/images/icon/ic_tablesort_up.gif'/>"></c:when>
								    </c:choose>
								</c:when>
								<c:otherwise>
									<ikep4j:message pre='${preSearch}' key='searchApprDocStatus' />
								</c:otherwise>
							</c:choose>
						</th> --%>
						<c:choose>
							<c:when test="${searchCondition.listType eq 'listApprDocSecurity'}">
								<th scope="col"><ikep4j:message pre='${preSearch}' key='searchApprDocSecurity' /></th>
							</c:when>
						</c:choose>
					</tr>
					<c:choose>
					    <c:when test="${searchResult.emptyRecord}">
					    	<c:choose>
								<c:when test="${searchCondition.listType eq 'listApprDocAllAdmin' or searchCondition.listType eq 'listApprDocAllUser' }">
									<tr>
										<td colspan="6" class="emptyRecord"><ikep4j:message pre='${preSearch}' key='emptyRecord' /></td>
									</tr>
								</c:when>
								<c:when test="${searchCondition.listType eq 'listApprDocSecurity'}">
									<tr>
										<td colspan="9" class="emptyRecord"><ikep4j:message pre='${preSearch}' key='emptyRecord' /></td>
									</tr>
								</c:when>
								<c:otherwise>
									<tr>
										<td colspan="6" class="emptyRecord"><ikep4j:message pre='${preSearch}' key='emptyRecord' /></td>
									</tr>
								</c:otherwise>
							</c:choose>
					    </c:when>
					    <c:otherwise>
							<c:forEach var="aplist" items="${searchResult.entity}" varStatus="i">
								<c:choose>
						 			<c:when test="${i.count % 2 == 0}">
						 				<c:set var="className" value="bgWhite"/>
						 			</c:when>
						 			<c:otherwise>
						 				<c:set var="className" value="bgSelected"/>
						 			</c:otherwise>
						 		</c:choose>  
								<tr class="${className}">
									<td>
										<input type="checkbox" name="apprId" id="apprId" value="${aplist.apprId}" title="checkbox">
										<div class="none">
						    				<span class="data_apprTitle">${aplist.apprTitle}</span>
						    			</div>
									</td>
									<td>
										<c:choose>
											<c:when test="${searchCondition.listType eq 'myRequestListComplete' or searchCondition.listType eq 'listApprDocAllAdmin' or searchCondition.listType eq 'listApprDocAllUser' or searchCondition.listType eq 'listApprDocSecurity'}">
												${aplist.apprDocNo}&nbsp;
											</c:when>
											<c:otherwise>
												${(searchCondition.recordCount-(searchCondition.pagePerRecord*(searchCondition.pageIndex-1))-i.count)+1}
											</c:otherwise>
										</c:choose>
									</td>
									<td>
										<c:if test="${aplist.apprDocType eq '0'}"><ikep4j:message pre='${preSearch}' key='apprDocType0' /></c:if>
										<c:if test="${aplist.apprDocType eq '1'}"><ikep4j:message pre='${preSearch}' key='apprDocType1' /></c:if>
									</td>
									<td>${aplist.codeName}</td>
									<td class="textLeft">
										<a href="#a" onclick="getApprDetail('${aplist.apprId}');" title="${aplist.apprTitle}">
											<div class="ellipsis">${aplist.apprTitle}</div>
										</a>
									</td>
									<c:choose>
										<c:when test="${searchCondition.listType eq 'listApprDocAllAdmin' or searchCondition.listType eq 'listApprDocAllUser' or searchCondition.listType eq 'listApprDocSecurity'}">
											<td><span class="name"><a href="#a" onclick="iKEP.showUserContextMenu(this, '${aplist.registerId}', 'bottom')">${aplist.registerName}</a></span></td>
										</c:when>
									</c:choose>
									<td><ikep4j:timezone pattern="yyyy.MM.dd HH:mm" date="${aplist.apprReqDate}"/></td>
									<%-- <td>
										<c:choose>
										<c:when test="${searchCondition.listType eq 'myRequestListComplete'}">
											<ikep4j:timezone pattern="yyyy.MM.dd HH:mm" date="${aplist.apprEndDate}"/>
										</c:when>
										<c:otherwise>
											<c:choose>
											<c:when test="${aplist.apprDocStatus == '1'}">
												<a href="#a" onclick="getViewApprLine('${aplist.apprId}');"><img src="<c:url value='/base/images/icon/signal_01.png'/>" alt="<ikep4j:message pre='${preApCommList}' key='display.progress'/>" /></a>
											</c:when>
											<c:when test="${aplist.apprDocStatus == '2'}">
												<a href="#a" onclick="getViewApprLine('${aplist.apprId}');"><img src="<c:url value='/base/images/icon/signal_02.png'/>" alt="<ikep4j:message pre='${preApCommList}' key='display.complete'/>" /></a>
											</c:when>
											<c:when test="${aplist.apprDocStatus == '3'}">
												<a href="#a" onclick="getViewApprLine('${aplist.apprId}');"><img src="<c:url value='/base/images/icon/signal_03.png'/>" alt="<ikep4j:message pre='${preApCommList}' key='display.reject'/>" /></a>
											</c:when>
											<c:when test="${aplist.apprDocStatus == '4'}">
												<a href="#a" onclick="getViewApprLine('${aplist.apprId}');"><img src="<c:url value='/base/images/icon/signal_04.png'/>" alt="<ikep4j:message pre='${preApCommList}' key='display.return'/>" /></a>
											</c:when>
											<c:when test="${aplist.apprDocStatus == '6'}">
												<img src="<c:url value='/base/images/icon/signal_06.png'/>" alt="<ikep4j:message pre='${preApCommList}' key='display.wait'/>" />
											</c:when>
											<c:when test="${aplist.apprDocStatus == '7'}">
												<a href="#a" onclick="getViewApprLine('${aplist.apprId}');"><img src="<c:url value='/base/images/icon/signal_07.png'/>" alt="<ikep4j:message pre='${preApCommList}' key='display.return1'/>" /></a>
											</c:when>
											<c:when test="${aplist.apprDocStatus == '0'}">
												<a href="#a" onclick="getViewApprLine('${aplist.apprId}');"><img src="<c:url value='/base/images/icon/signal_00.png'/>" alt="<ikep4j:message pre='${preApCommList}' key='display.temp'/>" /></a>
											</c:when>
										</c:choose>
										</c:otherwise>
										</c:choose>
									</td> --%>
									<c:choose>
										<c:when test="${searchCondition.listType eq 'listApprDocSecurity'}">
											<td>
												<c:choose>
													<c:when test="${aplist.isHidden == '0'}">
														<img src="<c:url value='/base/images/icon/ic_document.png'/>" alt="<ikep4j:message pre='${preApCommList}' key='display.clearSecurity'/>" />
													</c:when>
													<c:when test="${aplist.isHidden == '1'}">
														<img src="<c:url value='/base/images/icon/ic_btn_key.gif'/>" alt="<ikep4j:message pre='${preApCommList}' key='display.setSecurity'/>" />
													</c:when>
												</c:choose>
											</td>
										</c:when>
									</c:choose>	
								</tr>
								<tr style="display:none;" id="apprLineTr${aplist.apprId}">
									<c:choose>
										<c:when test="${searchCondition.listType eq 'listApprDocAllAdmin' or searchCondition.listType eq 'listApprDocAllUser' or searchCondition.listType eq 'listApprDocSecurity'}">
											<td colspan="7">
												<div id="${aplist.apprId}" style="display:none;">
												</div>							
											</td>
										</c:when>
										<c:otherwise>
											<td colspan="6">
												<div id="${aplist.apprId}" style="display:none;">
												</div>							
											</td>
										</c:otherwise>
									</c:choose>
								</tr>
							</c:forEach>				      
					    </c:otherwise> 
					</c:choose>  
				</tbody>
			</table>
			<!--Page Numbur Start--> 
			<spring:bind path="searchCondition.pageIndex">
			<ikep4j:pagination searchButtonId="${status.expression}" pageIndexInput="${status.expression}" searchCondition="${searchCondition}" />
			<input  id="${status.expression}" name="${status.expression}" type="hidden" value="${status.value}" title="<ikep4j:message pre='${preSearch}' key='${status.expression}'/>" />
			</spring:bind> 
			<!--//Page Numbur End-->
			
		</div>
		</form>
		<!--//blockListTable End-->	

