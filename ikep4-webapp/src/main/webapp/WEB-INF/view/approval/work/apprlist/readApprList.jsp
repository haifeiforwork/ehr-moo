<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/base/common/taglibs.jsp"%>

<%@ taglib uri="/WEB-INF/tld/ikep4j.tld" prefix="ikep4j" %> 
<c:set var="portal" value="${sessionScope['ikep.portal']}" />
<c:set var="user" value="${sessionScope['ikep.user']}" />
<%-- 메시지 관련 Prefix 선언 Start --%> 
<c:set var="preApCommList"    		value="ui.approval.work.apprlist" />
<c:set var="preButton"  			value="ui.approval.common.button" />
<c:set var="preSearch"  			value="ui.approval.common.searchCondition" />
 <%-- 메시지 관련 Prefix 선언 End --%>
 <script type="text/javascript" src="<c:url value="/base/js/jquery/jquery.validate-1.8.min.js"/>"></script>
<c:choose>
	<c:when test="${searchCondition.listType eq 'listApprTodo'}">
		<c:set var="formActUrl"     value="/approval/work/apprlist/listApprTodo.do" />
	</c:when>
	<c:when test="${searchCondition.listType eq 'listApprComplete'}">
		<c:set var="formActUrl"     value="/approval/work/apprlist/listApprComplete.do" />
	</c:when>
	<c:when test="${searchCondition.listType eq 'listCompleteAppr'}">
		<c:set var="formActUrl"     value="/approval/work/apprlist/listCompleteAppr.do" />
	</c:when>
	<c:when test="${searchCondition.listType eq 'listApprAgreement'}">
		<c:set var="formActUrl"     value="/approval/work/apprlist/listApprAgreement.do" />
	</c:when>
	<c:when test="${searchCondition.listType eq 'listApprIntegrate'}">
		<c:set var="formActUrl"     value="/approval/work/apprlist/listApprIntegrate.do" />
	</c:when>
	<c:when test="${searchCondition.listType eq 'listApprDeptRec'}">
		<c:set var="formActUrl"     value="/approval/work/apprlist/listApprDeptRec.do" />
	</c:when>
	<c:when test="${searchCondition.listType eq 'listApprUserRec'}">
		<c:set var="formActUrl"     value="/approval/work/apprlist/listApprUserRec.do" />
	</c:when>
	<c:when test="${searchCondition.listType eq 'listApprDeptIntegrate'}">
		<c:set var="formActUrl"     value="/approval/work/apprlist/listApprDeptIntegrate.do" />
	</c:when>
	<c:otherwise>
		<c:set var="formActUrl"     value="/approval/work/apprlist/listApprTodo.do" />
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
	
	/* 문서 상세정보 팝업 */
	detailViewPop = function(apprId,entrustUserId, listType){

		listType = '${searchCondition.listType}';
		var url ="";
		if(entrustUserId != "") {
			url = "/approval/work/apprWorkDoc/viewApprDoc.do?&popupYn=true&apprId=" + apprId + "&listType=" + listType + "&entrustUserId="+entrustUserId;
		}else {
			url = "/approval/work/apprWorkDoc/viewApprDoc.do?&popupYn=true&apprId=" + apprId + "&listType=" + listType;
		}
	    iKEP.showDialog({     
			title 		: "<ikep4j:message pre='${preApCommList}' key='sub.docDetailTitle' />",
	    	url 		: iKEP.getContextRoot() + url,
	    	modal 		: true,
	    	width 		: 800,
	    	height 		: 500,
	    	callback	: function(result) {
			    
			}
		});
	};
	
	//문서 결재 정보 조회
	getViewApprLine = function(apprId) {
		
		var $divContext = $jq("#"+apprId+"_${user.userId}"),
			$trRow = $jq("#apprLineTr"+apprId+"_${user.userId}");
		
		if($divContext.is(":hidden")) {
			
			$jq("#listTable").ajaxLoadStart("button");
			
			$jq("tr[id^=apprLineTr]").each(function() { 
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
				.error(function(event, request, settings) { 
					alert("error"); 
					$jq("#listTable").ajaxLoadComplete();
				});
		}else {
			$divContext.empty();
			$trRow.css("display","block");
			$divContext.hide();
			$trRow.hide();
		}
		
	};
	
	//문서 상세 정보
	getApprDetail = function(apprId,entrustUserId) {
		$("#searchForm input[name=apprId]:hidden").val(apprId);
		$("#searchForm input[name=entrustUserId]:hidden").val(entrustUserId);
		$("#searchForm").attr("action","<c:url value='/approval/work/apprWorkDoc/viewApprDoc.do'/>");
		$("#searchForm").submit(); 
	};
	
	/**
	 * 위임 설정 화면 오픈.
	 */
	 entrustPopup = function(entrustId, event){
		
		expertPopupClose();
		$el = $jq(event.target);
		var pos = $el.position();
		
		var $f = $jq('#expertMessage').css({left:pos.left+10, top:pos.top+137});
		$f.show();
		
		$jq("#expertMessage").ajaxLoadStart("button");
		
		$.post("<c:url value='/approval/work/config/updateUnitEntrustForm.do'/>", {entrustId	:	entrustId})
		.success(function(result) {
			$jq('#entrustMainLayer').append(result);
			$jq("#expertMessage").ajaxLoadComplete();
		})
		.error(function(event, request, settings) { alert("error"); });
	};
	
	//위임화면 close
	 expertPopupClose = function(){
		
		var $f= $jq('#expertMessage');
		$jq('#entrustLayer').remove();
		$f.hide();
	};
	
	/**
	 * onload시 수행할 코드
	 */
	$(document).ready(function() {
		
		//left 메뉴
		<c:choose>
			<c:when test="${searchCondition.listType eq 'listApprTodo'}">
				$jq("#apprTodoOfLeft").click();
			</c:when>
			<c:when test="${searchCondition.listType eq 'listApprComplete'}">
				$jq("#apprCompleteOfLeft").click();
			</c:when>
			<c:when test="${searchCondition.listType eq 'listCompleteAppr'}">
				$jq("#completeApprOfLeft").click();
			</c:when>
			<c:when test="${searchCondition.listType eq 'listApprAgreement'}">
				$jq("#apprAgreementOfLeft").click();
			</c:when>
			<c:when test="${searchCondition.listType eq 'listApprIntegrate'}">
				$jq("#apprIntegrateOfLeft").click();
			</c:when>
			<c:when test="${searchCondition.listType eq 'listApprDeptRec'}">
				$jq("#apprDeptRecOfLeft").click();
			</c:when>
			<c:when test="${searchCondition.listType eq 'listApprUserRec'}">
				$jq("#apprUserRecOfLeft").click();
			</c:when>
			<c:when test="${searchCondition.listType eq 'listApprDeptIntegrate'}">
				$jq("#apprDeptIntegrateOfLeft").click();
			</c:when>
			<c:otherwise>
				$jq("#apprTodoOfLeft").click();
			</c:otherwise>
		</c:choose>	
		
		$("#searchStartDate").datepicker().next().eq(0).click(function() { $("#searchStartDate").datepicker("show"); });
		$("#searchEndDate").datepicker().next().eq(0).click(function() { $("#searchEndDate").datepicker("show"); });
		
		//검색
		$("#searchApListButton").click(function() {
			$("input[name=pageIndex]").val('1');
			$("#searchForm").submit(); 
			return false; 
		});
		
		//전체 선택
		$("#allCheck").click(function() { 
			$("#searchForm input[name=chk_apprId]").attr("checked", $(this).is(":checked"));  
		});  
		
		//선택한 문서 결재 일괄결재
		$("#allApproval").click(function() {  
			var apprIds = new Array();
			var entrustUserIds = new Array();

			$("#searchForm input[name=chk_apprId]").each(function(index) { 
				if($(this).is(":checked")) {
					apprIds.push($(this).val());
					entrustUserIds.push($("input[name=entrustUserIds]").eq(index).val());
				}
			});		

			if(apprIds == "") {
				alert("<ikep4j:message pre='${preApCommList}' key='checkApproval' />");
				return;
			}
			
			dialog = new iKEP.showDialog({     
				title 		: "<ikep4j:message pre='${preApCommList}' key='sub.approvalTitle' />",
				url 		: "<c:url value='/approval/work/apprLine/updateMultiApprovalForm.do'/>?apprIds="+apprIds+"&entrustUserIds="+entrustUserIds,
				modal 		: true,
				width 		: 700,
				height 		: 300,
				params 		: {apprIds:apprIds.toString()},
				callback : function(result) {
					$("body").ajaxLoadStart("button");
					var str = result.split(":");				
					if(str[0] == "OK") {
						alert('<ikep4j:message pre="${preApCommList}" key="completeApproval" arguments="'+str[2]+','+str[1]+'"/>');
						location.href="<c:url value='/approval/work/apprlist/listApprTodo.do'/>?listType=listApprTodo";
					}
				}
			});
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
		
	});
})(jQuery);  
//-->
</script>
			
<h1 class="none"><ikep4j:message pre='${preApCommList}.pageTitle' key='${searchCondition.listType}' /></h1>

<!--pageTitle Start-->
<div id="pageTitle">
	<h2><ikep4j:message pre='${preApCommList}.pageTitle' key='${searchCondition.listType}' /></h2>
	<%-- <div id="pageLocation">
		<ul>
			<li class="liFirst"><ikep4j:message pre="${preHeader}" key="pageLocation.1depth" /></li>
			<li><ikep4j:message pre="${preHeader}" key="pageLocation.2depth" /></li>
			<li><ikep4j:message pre="${preHeader}" key="pageLocation.3depth" /></li>
			<li class="liLast"><ikep4j:message pre="${preHeader}" key="pageLocation.4depth" /></li>
		</ul>
	</div> --%>
</div>
<!--//pageTitle End-->

<div id="expertMessage" class="process_layer" style="position: absolute;top:0; right:0; width:300px; z-index:99; display: none;">
	<div class="process_layer_t">
		<ikep4j:message key='ui.approval.work.entrust.form.entrustTitle'/>
		<a href="#a" onclick="expertPopupClose();return false;" title="<ikep4j:message pre='${preButton}' key='close'/>"><img src="<c:url value="/base/images/icon/ic_close_layer.gif"/>" alt="<ikep4j:message pre='${preButton}' key='close'/>" /></a>
	</div>
	<div id="entrustMainLayer">
	
	</div>
</div>
<!--blockSearch Start-->
<form id="searchForm" method="post" action="<c:url value='${formActUrl}' />">
	<spring:bind path="searchCondition.apprId">
		<input name="${status.expression}" type="hidden" value="" title="hidden" />
	</spring:bind>
	<spring:bind path="searchCondition.listType">
		<input name="${status.expression}" type="hidden" value="${status.value}" title="hidden" />
	</spring:bind>
	<spring:bind path="searchCondition.linkType">
		<input name="${status.expression}" type="hidden" value="${status.value}" title="hidden" />
	</spring:bind>
	<spring:bind path="searchCondition.entrustUserId">
		<input name="${status.expression}" type="hidden" value="${status.value}" title="hidden" />
	</spring:bind>
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
					<c:when test="${searchCondition.listType eq 'listApprDeptRec' or searchCondition.listType eq 'listApprUserRec'}">
					<tr>
						<c:choose>
							<c:when test="${searchCondition.listType eq 'listApprDeptRec'}">
								<spring:bind path="searchCondition.searchApprDocType">
								<th scope="row" width="5%"><ikep4j:message pre='${preSearch}' key='${status.expression}' /></th>
								<td width="25%">
									<select title="<ikep4j:message pre='${preSearch}' key='${status.expression}' />" name="${status.expression}">
										<c:forEach var="apCode" items="${docTypeList}">
											<option value="${apCode.key}" <c:if test="${apCode.key eq status.value}">selected="selected"</c:if>>
												<c:choose>
													<c:when test="${apCode.key eq '0'}">
														<ikep4j:message pre='${preSearch}' key='apprDocType2' />
													</c:when>
													<c:when test="${apCode.key eq '1'}">
														<ikep4j:message pre='${preSearch}' key='apprDocType1' />
													</c:when>
													<c:otherwise>
														<spring:message code="${apCode.value}" />
													</c:otherwise>
												</c:choose>
											</option>
										</c:forEach>
									</select>
								</td>
								</spring:bind>
							</c:when>
							<c:when test="${searchCondition.listType eq 'listApprUserRec'}">
								<spring:bind path="searchCondition.searchFormName">
								<th scope="row" width="5%"><ikep4j:message pre='${preSearch}' key='${status.expression}' /></th>
								<td width="25%">
									<input type="text" class="inputbox" title="<ikep4j:message pre='${preSearch}' key='${status.expression}' />" name="${status.expression}" value="${status.value}" size="35" />
								</td>
								</spring:bind>
							</c:when>
						</c:choose>
						<spring:bind path="searchCondition.searchApprDocStatus">
						<th scope="row" width="5%"><ikep4j:message pre='${preSearch}' key='${status.expression}' /></th>
						<td width="25%">
							<select title="<ikep4j:message pre='${preSearch}' key='${status.expression}' />" name="${status.expression}">
								<c:forEach var="apCode" items="${docStatusList}">
									<c:if test="${apCode.key ne '0' and apCode.key ne '4'}">
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
					<c:choose>
						<c:when test="${searchCondition.listType ne 'listApprUserRec'}">
							<spring:bind path="searchCondition.searchFormName">
							<th scope="row" width="5%"><ikep4j:message pre='${preSearch}' key='${status.expression}' /></th>
							<td width="25%">
								<input type="text" class="inputbox" title="<ikep4j:message pre='${preSearch}' key='${status.expression}' />" name="${status.expression}" value="${status.value}" size="35" />
							</td>
							</spring:bind>
						</c:when>
						<c:when test="${searchCondition.listType eq 'listApprUserRec'}">
							<th scope="row" width="5%">
								<c:choose>
									<c:when test="${searchCondition.listType eq 'listApprDeptRec' or searchCondition.listType eq 'listApprUserRec'}">
										<ikep4j:message pre='${preSearch}' key='searchRecDate' />
									</c:when>
									<c:otherwise>
										<ikep4j:message pre='${preSearch}' key='apprReqDate' />		
									</c:otherwise>
								</c:choose>
							</th>
							<td width="25%">
								<spring:bind path="searchCondition.searchStartDate">
									<input type="text" class="inputbox" id="${status.expression}" name="${status.expression}" title="<ikep4j:message pre='${preSearch}' key='${status.expression}' />" value="${status.value}" size="10" /> <img class="dateicon" src="/ikep4-webapp/base/images/icon/ic_cal.gif" alt="달력" /> ~
								</spring:bind>
								<spring:bind path="searchCondition.searchEndDate">
									<input type="text" class="inputbox" id="${status.expression}" name="${status.expression}" title="<ikep4j:message pre='${preSearch}' key='${status.expression}' />" value="${status.value}" size="10" /> <img class="dateicon" src="/ikep4-webapp/base/images/icon/ic_cal.gif" alt="달력" />
								</spring:bind>							
							</td>
						</c:when>
					</c:choose>
					<c:choose>
						<c:when test="${searchCondition.listType eq 'listApprComplete'}">
							<spring:bind path="searchCondition.searchApprDocStatus">
							<th scope="row" width="5%"><ikep4j:message pre='${preSearch}' key='${status.expression}' /></th>
							<td width="25%">
								<select title="<ikep4j:message pre='${preSearch}' key='${status.expression}' />" name="${status.expression}">
									<c:forEach var="apCode" items="${docStatusList}">
										<c:if test="${apCode.key ne '2' and apCode.key ne '4'}">
											<option value="${apCode.key}" <c:if test="${apCode.key eq status.value}">selected="selected"</c:if>><spring:message code="${apCode.value}" /></option>
										</c:if>
									</c:forEach>
								</select>
							</td>
							</spring:bind>
						</c:when>
						<c:otherwise>
							<spring:bind path="searchCondition.searchUserName">
							<th scope="row" width="5%"><ikep4j:message pre='${preSearch}' key='searchUserId' /></th>
							<td width="25%">
								<input type="text" class="inputbox" title="<ikep4j:message pre='${preSearch}' key='searchUserId' />" name="searchUserName" id="searchUserName" value="${status.value}" size="35" />&nbsp;
							</td>
							</spring:bind>
						</c:otherwise>
					</c:choose>
				</tr>
				<tr>
					<c:choose>
						<c:when test="${searchCondition.listType ne 'listApprUserRec'}">
							<th scope="row" width="5%">
								<c:choose>
									<c:when test="${searchCondition.listType eq 'listApprDeptRec' or searchCondition.listType eq 'listApprUserRec'}">
										<ikep4j:message pre='${preSearch}' key='searchRecDate' />
									</c:when>
									<c:otherwise>
										<ikep4j:message pre='${preSearch}' key='apprReqDate' />		
									</c:otherwise>
								</c:choose>
							</th>
							<td width="25%">
								<spring:bind path="searchCondition.searchStartDate">
									<input type="text" class="inputbox" id="${status.expression}" name="${status.expression}" title="<ikep4j:message pre='${preSearch}' key='${status.expression}' />" value="${status.value}" size="10" /> <img class="dateicon" src="/ikep4-webapp/base/images/icon/ic_cal.gif" alt="달력" /> ~
								</spring:bind>
								<spring:bind path="searchCondition.searchEndDate">
									<input type="text" class="inputbox" id="${status.expression}" name="${status.expression}" title="<ikep4j:message pre='${preSearch}' key='${status.expression}' />" value="${status.value}" size="10" /> <img class="dateicon" src="/ikep4-webapp/base/images/icon/ic_cal.gif" alt="달력" />
								</spring:bind>							
							</td>
						</c:when>
					</c:choose>					
					<c:choose>
						<c:when test="${searchCondition.listType eq 'listApprDeptIntegrate'}">
							<spring:bind path="searchCondition.searchGroupId">
							<th scope="row"><ikep4j:message pre='${preSearch}' key='${status.expression}'/></th>
							<td width="25%">
							<select name="${status.expression}" title='<ikep4j:message pre='${preSearch}' key='${status.expression}' />'>
								<c:forEach var="item" items="${groupList}">
									<option value="${item.groupId}" <c:if test="${item.groupId eq status.value}">selected="selected"</c:if>><c:if test="${user.localeCode eq 'ko'}">${item.groupName}</c:if><c:if test="${user.localeCode ne 'ko'}">${item.groupEnglishName}</c:if></option>
								</c:forEach> 
							</select>
							</td>
							</spring:bind>
						</c:when>
						<c:when test="${searchCondition.listType eq 'listApprComplete'}">
							<spring:bind path="searchCondition.searchUserName">
							<th scope="row" width="5%"><ikep4j:message pre='${preSearch}' key='searchUserId' /></th>
							<td width="25%">
								<input type="text" class="inputbox" title="<ikep4j:message pre='${preSearch}' key='searchUserId' />" name="searchUserName" id="searchUserName" value="${status.value}" size="35"/>&nbsp;
							</td>
							</spring:bind>
						</c:when>
						<c:otherwise>
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
					<c:when test="${searchCondition.listType eq 'listApprDeptIntegrate'}">
						<tr>		
							<spring:bind path="searchCondition.searchApprDocNo">
							<th scope="row" width="5%"><ikep4j:message pre='${preSearch}' key='searchApprDocNo'/></th>
							<td width="25%">								
								<input type="text" class="inputbox" title="<ikep4j:message pre='${preSearch}' key='searchApprDocNo'/>" name="${status.expression}" value="${status.value}" size="35" />
							</td>		
							</spring:bind>			
							<spring:bind path="searchCondition.searchApprTitle">
							<th scope="row" width="5%"><ikep4j:message pre='${preSearch}' key='apprTitle'/></th>
							<td width="25%">								
								<input type="text" class="inputbox" title="<ikep4j:message pre='${preSearch}' key='apprTitle'/>" name="${status.expression}" value="${status.value}" size="35" />
							</td>		
							</spring:bind>		
						</tr>
					</c:when>
					<c:when test="${searchCondition.listType eq 'listApprComplete'}">
						<tr>					
							<spring:bind path="searchCondition.searchApprTitle">
							<th scope="row" width="5%"><ikep4j:message pre='${preSearch}' key='apprTitle'/></th>
							<td colspan="3">								
								<input type="text" class="inputbox" title="<ikep4j:message pre='${preSearch}' key='apprTitle'/>" name="${status.expression}" value="${status.value}" size="35" />
							</td>		
							</spring:bind>		
						</tr>
					</c:when>
				</c:choose>
				<c:if test="${searchCondition.listType eq 'listApprAgreement' or searchCondition.listType eq 'listApprIntegrate'}">
				<tr>					
					<spring:bind path="searchCondition.searchApprDocNo">
					<th scope="row" width="5%"><ikep4j:message pre='${preSearch}' key='searchApprDocNo'/></th>
					<td colspan="3">								
						<input type="text" class="inputbox" title="<ikep4j:message pre='${preSearch}' key='searchApprDocNo'/>" name="${status.expression}" value="${status.value}" size="35" />
					</td>		
					</spring:bind>		
				</tr>
				</c:if>
				<c:if test="${searchCondition.listType eq 'listCompleteAppr'}">
				<tr>					
					<spring:bind path="searchCondition.searchApprDocNo">
					<th scope="row" width="5%"><ikep4j:message pre='${preSearch}' key='searchApprDocNo'/></th>
					<td width="25%">								
						<input type="text" class="inputbox" title="<ikep4j:message pre='${preSearch}' key='searchApprDocNo'/>" name="${status.expression}" value="${status.value}" size="35" />
					</td>		
					</spring:bind>
					<spring:bind path="searchCondition.searchlistSortType">
					<th scope="row" width="5%"><ikep4j:message pre='${preSearch}' key='${status.expression}' /></th>
					<td width="25%">
						<select title="<ikep4j:message pre='${preSearch}' key='${status.expression}' />" name="${status.expression}">
							<c:forEach var="apCode" items="${sortTypeList}">
								<option value="${apCode.key}" <c:if test="${apCode.key eq status.value}">selected="selected"</c:if>><spring:message code="${apCode.value}" /></option>
							</c:forEach>
						</select>
					</td>
					</spring:bind>		
				</tr>
				</c:if>										
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

<h1 class="none">
  <ikep4j:message pre='${preApCommList}.pageTitle' key='${searchCondition.listType}' />
</h1>

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
			<div class="totalNum"><ikep4j:message pre='${preSearch}' key='pageCount.info' /><span> ${searchResult.recordCount}</span></div>
			<div align="right">
				<c:choose>
					<c:when test="${searchCondition.listType eq 'listApprComplete'}">
						<img style="vertical-align:text-top;" src="<c:url value='/base/images/icon/signal_01.png'/>" alt="<ikep4j:message pre='${preApCommList}' key='display.progress'/>" /><ikep4j:message pre='${preApCommList}' key='display.progress'/>
						<img style="vertical-align:text-top;" src="<c:url value='/base/images/icon/signal_03.png'/>" alt="<ikep4j:message pre='${preApCommList}' key='display.reject'/>" /><ikep4j:message pre='${preApCommList}' key='display.reject'/>
					</c:when>
					<c:when test="${searchCondition.listType eq 'listApprDeptRec' or searchCondition.listType eq 'listApprUserRec'}">
						<img style="vertical-align:text-top;" src="<c:url value='/base/images/icon/signal_06.png'/>" alt="<ikep4j:message pre='${preApCommList}' key='display.wait'/>" /><ikep4j:message pre='${preApCommList}' key='display.wait'/>
						<img style="vertical-align:text-top;" src="<c:url value='/base/images/icon/signal_01.png'/>" alt="<ikep4j:message pre='${preApCommList}' key='display.progress'/>" /><ikep4j:message pre='${preApCommList}' key='display.progress'/>
						<img style="vertical-align:text-top;" src="<c:url value='/base/images/icon/signal_02.png'/>" alt="<ikep4j:message pre='${preApCommList}' key='display.complete'/>" /><ikep4j:message pre='${preApCommList}' key='display.complete'/>
						<img style="vertical-align:text-top;" src="<c:url value='/base/images/icon/signal_03.png'/>" alt="<ikep4j:message pre='${preApCommList}' key='display.reject'/>" /><ikep4j:message pre='${preApCommList}' key='display.reject'/>
						<c:if test="${searchCondition.listType eq 'listApprDeptRec' or searchCondition.listType eq 'listApprUserRec'}">
						<img style="vertical-align:text-top;" src="<c:url value='/base/images/icon/signal_07.png'/>" alt="<ikep4j:message pre='${preApCommList}' key='display.return1'/>" /><ikep4j:message pre='${preApCommList}' key='display.return1'/>
						</c:if>
					</c:when>
				</c:choose>
			</div>			
		</div>			
		<div class="clear"></div>
	</div>
	<!--//tableTop End-->
	<table summary="<ikep4j:message pre='${preApCommList}.pageTitle' key='${searchCondition.listType}' />" id="listTable">
		<caption></caption>
		<colgroup>
		<c:choose>
			<c:when test="${searchCondition.listType eq 'listApprDeptRec'}">
				<col width="5%"/>
				<col width="8%"/>
				<col width="8%"/>
				<c:if test="${searchCondition.searchApprDocStatus eq '6'}"><col width="29%"/></c:if>
				<c:if test="${searchCondition.searchApprDocStatus ne '6'}"><col width="20%"/></c:if>
				<col width="9%"/>
				<col width="10%"/>
				<c:if test="${searchCondition.searchApprDocStatus ne '6'}"><col width="8%"/></c:if>
				<col width="15%"/>
				<col width="8%"/>
			</c:when>
			<c:when test="${searchCondition.listType eq 'listApprUserRec'}">
				<col width="5%"/>
				<col width="8%"/>
				<col width="8%"/>
				<col width="37%"/>
				<col width="9%"/>
				<col width="10%"/>
				<col width="15%"/>
				<col width="8%"/>
			</c:when>
			<c:when test="${searchCondition.listType eq 'listApprTodo'}">
				<col width="5%"/>
				<col width="5%"/>
				<col width="8%"/>
				<col width="8%"/>
				<col width="40%"/>
				<col width="9%"/>
				<col width="15%"/>
				<col width="10%"/>
			</c:when>
			<c:when test="${searchCondition.listType eq 'listCompleteAppr'}">
				<col width="13%"/>
				<col width="8%"/>
				<col width="8%"/>
				<col width="8%"/>
				<col width="25%"/>
				<col width="8%"/>
				<col width="15%"/>
				<col width="15%"/>
			</c:when>
			<c:otherwise>
				<c:choose>
					<c:when test="${searchCondition.listType eq 'listApprAgreement' or searchCondition.listType eq 'listApprIntegrate' or searchCondition.listType eq 'listApprDeptIntegrate'}">
						<col width="13%"/>
					</c:when>
					<c:otherwise>
						<col width="5%"/>
					</c:otherwise>
				</c:choose>	
				<col width="8%"/>
				<col width="8%"/>
				<c:choose>
					<c:when test="${searchCondition.listType ne 'listApprAgreement' or searchCondition.listType ne 'listApprIntegrate' or searchCondition.listType ne 'listApprDeptIntegrate'}">
						<col width="32%"/>
					</c:when>
					<c:otherwise>
						<col width="40%"/>
					</c:otherwise>
				</c:choose>	
				<col width="9%"/>
				<col width="15%"/>
				<col width="15%"/>
				</colgroup>
			</c:otherwise>
		</c:choose>
		<thead>
			<tr>
				<c:if test="${searchCondition.listType eq 'listApprTodo'}">
				<th scope="col">
					<input name="allCheck" id="allCheck" class="checkbox" title="checkbox" type="checkbox" value="" />
				</th>
				</c:if>
				<th scope="col">
					<c:choose>
						<c:when test="${searchCondition.listType eq 'listApprAgreement' or searchCondition.listType eq 'listCompleteAppr' or searchCondition.listType eq 'listApprIntegrate' or searchCondition.listType eq 'listApprDeptIntegrate'}">
							<ikep4j:message pre='${preSearch}' key='searchApprDocNo'/>
						</c:when>
						<c:otherwise>
							<ikep4j:message pre='${preSearch}' key='number' />
						</c:otherwise>
					</c:choose>
				</th>
				<c:if test="${searchCondition.listType eq 'listCompleteAppr'}">
					<th scope="col"><ikep4j:message pre='${preSearch}' key='searchlistSortType' /></th>
				</c:if>				
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
				<th scope="col"><ikep4j:message pre='${preSearch}' key='searchUserId' /></th>
				<th scope="col">
				    <c:choose>
			 			<c:when test="${searchCondition.listType eq 'listApprTodo'}">
							<a onclick="f_Sort('apprReqDate', '<c:if test="${searchCondition.sortColumn eq 'apprReqDate'}">${searchCondition.sortType}</c:if>');"  href="#a">
							<ikep4j:message pre='${preSearch}' key='apprReqDate' />&nbsp;&nbsp;
							</a>
							<c:choose>
							    <c:when test="${searchCondition.sortColumn eq 'apprReqDate' and searchCondition.sortType eq 'DESC'}"><img alt="desc" src="<c:url value='/base/images/icon/ic_tablesort_down.gif'/>"></c:when>
							    <c:when test="${searchCondition.sortColumn eq 'apprReqDate' and searchCondition.sortType eq 'ASC'}"><img alt="asc" src="<c:url value='/base/images/icon/ic_tablesort_up.gif'/>"></c:when>
						    </c:choose>
			 			</c:when>
			 			<c:when test="${searchCondition.listType eq 'listApprDeptRec' or searchCondition.listType eq 'listApprUserRec'}">
			 				<ikep4j:message pre='${preSearch}' key='searchGroupName' />
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
				</th>
				<c:if test="${searchCondition.listType eq 'listApprDeptRec'}">
					<c:if test="${searchCondition.searchApprDocStatus ne '6'}"><th scope="col"><ikep4j:message pre='${preSearch}' key='recUserId' /></th></c:if>
				</c:if>
				<th scope="col">
					<c:choose>
			 			<c:when test="${searchCondition.listType eq 'listApprComplete'}">
							<ikep4j:message pre='${preSearch}' key='searchApprDocStatus' />
						</c:when>
						<c:when test="${searchCondition.listType eq 'listApprAgreement' or searchCondition.listType eq 'listCompleteAppr' or searchCondition.listType eq 'listApprIntegrate' or searchCondition.listType eq 'listApprDeptIntegrate'}">
							<a onclick="f_Sort('apprEndDate', '<c:if test="${searchCondition.sortColumn eq 'apprEndDate'}">${searchCondition.sortType}</c:if>');"  href="#a">
							<ikep4j:message pre='${preSearch}' key='apprEndDate' />&nbsp;&nbsp;
							</a>
							<c:choose>
							    <c:when test="${searchCondition.sortColumn eq 'apprEndDate' and searchCondition.sortType eq 'DESC'}"><img alt="desc" src="<c:url value='/base/images/icon/ic_tablesort_down.gif'/>"></c:when>
							    <c:when test="${searchCondition.sortColumn eq 'apprEndDate' and searchCondition.sortType eq 'ASC'}"><img alt="asc" src="<c:url value='/base/images/icon/ic_tablesort_up.gif'/>"></c:when>
						    </c:choose>
						</c:when>
						<c:when test="${searchCondition.listType eq 'listApprDeptRec' or searchCondition.listType eq 'listApprUserRec'}">
							<a onclick="f_Sort('apprReqDate', '<c:if test="${searchCondition.sortColumn eq 'apprReqDate'}">${searchCondition.sortType}</c:if>');"  href="#a">
							<ikep4j:message pre='${preSearch}' key='searchRecDate' />&nbsp;&nbsp;
							</a>
							<c:choose>
							    <c:when test="${searchCondition.sortColumn eq 'apprReqDate' and searchCondition.sortType eq 'DESC'}"><img alt="desc" src="<c:url value='/base/images/icon/ic_tablesort_down.gif'/>"></c:when>
							    <c:when test="${searchCondition.sortColumn eq 'apprReqDate' and searchCondition.sortType eq 'ASC'}"><img alt="asc" src="<c:url value='/base/images/icon/ic_tablesort_up.gif'/>"></c:when>
						    </c:choose>
			 			</c:when>
			 			<c:otherwise>
							<ikep4j:message pre='${preSearch}' key='detail' />
			 			</c:otherwise>
					</c:choose>		
				</th>
				<c:if test="${searchCondition.listType eq 'listApprDeptRec' or searchCondition.listType eq 'listApprUserRec'}">
				<th scope="col">
					<ikep4j:message pre='${preSearch}' key='searchApprDocStatus' />
				</th>
				</c:if>
			</tr>
		</thead> 
		<tbody>
			<c:choose>
			    <c:when test="${searchResult.emptyRecord}">
					<tr>
						<c:choose>
						    <c:when test="${searchCondition.listType eq 'listApprAgreement' or searchCondition.listType eq 'listApprComplete' or searchCondition.listType eq 'listApprIntegrate' or searchCondition.listType eq 'listApprDeptIntegrate'}">
						    	<td colspan="7" class="emptyRecord"><ikep4j:message pre='${preSearch}' key='emptyRecord' /></td>
						    </c:when>
						    <c:when test="${searchCondition.listType eq 'listCompleteAppr'}">
						    	<td colspan="8" class="emptyRecord"><ikep4j:message pre='${preSearch}' key='emptyRecord' /></td>
						    </c:when>
						    <c:otherwise>
						    	<c:if test="${searchCondition.searchApprDocStatus ne '6'}"><td colspan="9" class="emptyRecord"><ikep4j:message pre='${preSearch}' key='emptyRecord' /></td></c:if>
						    	<c:if test="${searchCondition.searchApprDocStatus eq '6'}"><td colspan="8" class="emptyRecord"><ikep4j:message pre='${preSearch}' key='emptyRecord' /></td></c:if>
						    </c:otherwise>
					    </c:choose>
					</tr>
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
							<c:if test="${searchCondition.listType eq 'listApprTodo'}">
							<td>
								<c:if test="${aplist.apprType eq '0'}">
									<input type="checkbox" name="chk_apprId" id="chk_apprId" value="${aplist.apprId}" title="checkbox">
									<c:if test="${aplist.approvalId != user.userId}">
									<input type="hidden" name="entrustUserIds" id="entrustUserIds" value="${aplist.approvalId}" title="hidden">
									</c:if>
									<c:if test="${aplist.approvalId == user.userId}">
									<input type="hidden" name="entrustUserIds" id="entrustUserIds" value="" title="hidden">
									</c:if>
								</c:if>
							</td>
							</c:if>
							<td>
								<c:choose>
									<c:when test="${searchCondition.listType eq 'listApprAgreement' or searchCondition.listType eq 'listCompleteAppr' or searchCondition.listType eq 'listApprIntegrate' or searchCondition.listType eq 'listApprDeptIntegrate'}">
										<div class="ellipsis" title="${aplist.apprDocNo}" style="cursor:default;">${aplist.apprDocNo}</div>
									</c:when>
									<c:otherwise>
										${(searchCondition.recordCount-(searchCondition.pagePerRecord*(searchCondition.pageIndex-1))-i.count)+1}
									</c:otherwise>
								</c:choose>
							</td>
							<c:if test="${searchCondition.listType eq 'listCompleteAppr'}">
								<td>
									<c:if test="${aplist.listSortType eq 'AA'}"><ikep4j:message pre='${preSearch}' key='apprAl2' /></c:if>
									<c:if test="${aplist.listSortType eq 'AL'}"><ikep4j:message pre='${preSearch}' key='apprAl' /></c:if>
									<c:if test="${aplist.listSortType eq 'AR'}"><ikep4j:message pre='${preSearch}' key='apprAr' /></c:if>
								</td>
							</c:if>	
							<td>
								<c:choose>
									<c:when test="${searchCondition.listType eq 'listApprDeptRec' or searchCondition.listType eq 'listApprUserRec'}">
										<c:if test="${aplist.apprDocType eq '0'}"><ikep4j:message pre='${preSearch}' key='apprDocType2' /></c:if>
										<c:if test="${aplist.apprDocType eq '1'}"><ikep4j:message pre='${preSearch}' key='apprDocType1' /></c:if>
									</c:when>
									<c:otherwise>
										<c:if test="${aplist.apprDocType eq '0'}"><ikep4j:message pre='${preSearch}' key='apprDocType0' /></c:if>
										<c:if test="${aplist.apprDocType eq '1'}"><ikep4j:message pre='${preSearch}' key='apprDocType1' /></c:if>
									</c:otherwise>
								</c:choose>
							</td>
							<td>${aplist.codeName}</td>
							<td class="textLeft">
								<div class="ellipsis">
									<c:if test="${aplist.approvalId ne user.userId}">
									<c:choose>
										<c:when test="${searchCondition.listType eq 'listApprTodo' or searchCondition.listType eq 'listApprComplete' or searchCondition.listType eq 'listCompleteAppr'}">
											<c:if test="${aplist.entrustId ne null}"><span><a href="#a"  onclick="entrustPopup('${aplist.entrustId}', event)"><img src="<c:url value='/base/images/icon/ic_entrust.png'/>" alt="entrust" /></a></span></c:if>
										</c:when>
									</c:choose>
									</c:if>
									<c:if test="${aplist.approvalId ne user.userId}">
									<a href="#a" onclick="getApprDetail('${aplist.apprId}','${aplist.approvalId}');" title="${aplist.apprTitle}">${aplist.apprTitle}</a>
									</c:if>
									<c:if test="${aplist.approvalId eq user.userId}">
									<a href="#a" onclick="getApprDetail('${aplist.apprId}','');" title="${aplist.apprTitle}">${aplist.apprTitle}</a>
									</c:if>
								</div>
							</td>
							<td><span class="name"><a href="#a" onclick="iKEP.showUserContextMenu(this, '${aplist.registerId}', 'bottom')">${aplist.registerName}</a></span></td>
							<td>
								<c:choose>
									<c:when test="${searchCondition.listType eq 'listApprDeptRec' or searchCondition.listType eq 'listApprUserRec'}">
										${aplist.apprGroupName}
									</c:when>
									<c:otherwise>
										<ikep4j:timezone pattern="yyyy.MM.dd HH:mm" date="${aplist.apprReqDate}"/>
									</c:otherwise>
								</c:choose>
							</td>
							<c:if test="${searchCondition.listType eq 'listApprDeptRec'}">
								<c:if test="${searchCondition.searchApprDocStatus ne '6'}"><td><a href="#a" onclick="iKEP.showUserContextMenu(this, '${aplist.recRegisterId}', 'bottom')">${aplist.recRegisterName}</a></td></c:if>
							</c:if>
							<td>
								<c:choose>
									<c:when test="${searchCondition.listType eq 'listApprAgreement' or searchCondition.listType eq 'listCompleteAppr' or searchCondition.listType eq 'listApprDeptRec' or searchCondition.listType eq 'listApprUserRec'}">
										<ikep4j:timezone pattern="yyyy.MM.dd HH:mm" date="${aplist.apprEndDate}"/>
									</c:when>
									<c:when test="${searchCondition.listType eq 'listApprTodo'}">
										<c:if test="${aplist.approvalId ne user.userId}">
										<a href="#a" onclick="detailViewPop('${aplist.apprId}','${aplist.approvalId}');" title="${aplist.apprTitle}"><img src="<c:url value='/base/images/icon/ic_view_list_on.gif'/>" alt="detail" /></a>
										</c:if>
										<c:if test="${aplist.approvalId eq user.userId}">
										<a href="#a" onclick="detailViewPop('${aplist.apprId}','');" title="${aplist.apprTitle}"><img src="<c:url value='/base/images/icon/ic_view_list_on.gif'/>" alt="detail" /></a>
										</c:if>
									</c:when>
									<c:when test="${searchCondition.listType eq 'listApprIntegrate' or searchCondition.listType eq 'listApprDeptIntegrate'}">
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
<%-- 											<c:otherwise> --%>
<%-- 												<a href="#a" onclick="getViewApprLine('${aplist.apprId}');"><img src="<c:url value='/base/images/icon/signal_03.png'/>" alt="<ikep4j:message pre='${preApCommList}' key='display.return'/>" /></a> --%>
<%-- 											</c:otherwise> --%>
										</c:choose>
									</c:otherwise>
								</c:choose>	
							</td>
							<c:if test="${searchCondition.listType eq 'listApprDeptRec' or searchCondition.listType eq 'listApprUserRec'}">
								<td>
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
									</c:choose>
								</td>
							</c:if>							
						</tr>
						<c:choose>
							<c:when test="${searchCondition.listType eq 'listApprComplete'}">
								<tr style="display:none;" id="apprLineTr${aplist.apprId}_${user.userId}">
									<td colspan="7">
										<div id="${aplist.apprId}_${user.userId}" style="display:none;">
										</div>							
									</td>
								</tr>
							</c:when>
							<c:when test="${searchCondition.listType eq 'listApprDeptRec'}">
								<tr style="display:none;" id="apprLineTr${aplist.apprId}_${user.userId}">
									<td colspan="<c:if test="${searchCondition.searchApprDocStatus ne '6'}">9</c:if><c:if test="${searchCondition.searchApprDocStatus eq '6'}">8</c:if>">
										<div id="${aplist.apprId}_${user.userId}" style="display:none;">
										</div>							
									</td>
								</tr>
							</c:when>
							<c:when test="${searchCondition.listType eq 'listApprUserRec'}">
								<tr style="display:none;" id="apprLineTr${aplist.apprId}_${user.userId}">
									<td colspan="8">
										<div id="${aplist.apprId}_${user.userId}" style="display:none;">
										</div>							
									</td>
								</tr>
							</c:when>
						</c:choose>	
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
<c:if test="${searchCondition.listType eq 'listApprTodo' and isOverall eq '1'}">
	<div class="clear"></div>
	
	<!--blockButton Start-->
	<div class="blockButton"> 
		<ul>
			<c:if test="${isOverall=='1'}">
			<li><a id="allApproval"   class="button" href="#a"><span><ikep4j:message pre='${preSearch}' key='allApproval' /></span></a></li>
			</c:if>
		</ul>
	</div>
	<!--//blockButton End-->	
</c:if>