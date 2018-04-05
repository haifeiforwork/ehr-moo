<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/base/common/taglibs.jsp"%> 

<%-- 메시지 관련 Prefix 선언 Start --%> 
<c:set var="prefix">ui.lightpack.todo.listOrderView</c:set>
<c:set var="messagePrefix">ui.lightpack.todo.message</c:set>
<c:set var="buttonPrefix">ui.lightpack.todo.button</c:set>
<c:set var="preSearch">ui.lightpack.todo.common.searchCondition</c:set>
<c:set var="menuPrefix">ui.lightpack.todo.subMenu</c:set>
<c:set var="preCommon">ui.lightpack.todo.common</c:set>

 <%-- 메시지 관련 Prefix 선언 End --%>
	
<c:set var="user" value="${sessionScope['ikep.user']}" />
<c:set var="portal" value="${sessionScope['ikep.portal']}" />

<script type="text/javascript">
(function($) { 
	$jq(document).ready(function(){	
		// 메뉴 카운트 변경
		window.parent.setCountMyTodo("${countMyTodo}");

		$jq("#date3").datepicker().next().eq(0).click(function() { 
			$jq(this).prev().eq(0).datepicker("show"); 
		});
		$jq("#date4").datepicker().next().eq(0).click(function() { 
			$jq(this).prev().eq(0).datepicker("show"); 
		});	
		
		$jq("#pagePerRecordOrder").change(function() {
            searchOrder();
        });

		//검색버튼클릭
		$jq("#searchpagingButton").click(function() {
			searchOrder();
		});
		$jq('#searchText').keypress( function(event) {  
            if(event.which == 13) {
				searchOrder();
            }
		});
		
		$jq("#searchStatusOrder").change(function() {
			statusChangeOrder();
        });
		
	});


	//status Order 검색 변경
	statusChangeOrder = function() {
		var val = $jq("#searchStatusOrder option:selected").val();
		if(val == "9" || val == "1") {
			$jq("#date3").val('${nowDay_pre_14}');
			$jq("#date4").val("${nowDay}");
		} else if(val == "0") {
			$jq("#date3").val("");
			$jq("#date4").val("");
		}
		
		searchOrder();
	}

	//검색
	searchOrder = function() { 
		$fromDay = $jq('#date3').val();
		$toDay = $jq('#date4').val();
		
		if($fromDay=="" && $toDay=="") {
			$jq('#form').submit();
		} else {
			if($fromDay.length < 10) {
				alert("<ikep4j:message pre='${messagePrefix}' key='validation.fromDay'/>"); 
				return;
			} else {
				$jq('#form>input[name=fromDay]').val($jq("#date3").val() + " 00:00:00");
			}
			
			if($toDay.length < 10) {
				alert("<ikep4j:message pre='${messagePrefix}' key='validation.toDay'/>"); 
				$jq('#date4').focus();
				return;
			} else {
				$jq('#form>input[name=toDay]').val($jq("#date4").val() + " 23:59:59");
			}		
			
			$jq('#form').submit();
		}
	}	
	
	
})(jQuery);	
</script>
		
				<h1 class="none"></h1>
				
				<form id="form" action='<c:url value="/lightpack/todo/listOrderView.do"/>' method="post">
					<spring:bind path="todoSearchCondition.pageViewNum">
						<input type="hidden" name="${status.expression}" value="${status.value}"/>
					</spring:bind>
					<input type="hidden" name="fromDay"/>
					<input type="hidden" name="toDay"/>
					
				<!--blockListTable Start-->
				<div class="blockListTable msgTable">
					<!--tableTop Start-->
					<div class="tableTop">
						<div class="tableTop_bgR"></div>
						<h2>업무 지시 내역</h2>
						<div class="listInfo" id="topShowPage2">
							<spring:bind path="todoSearchCondition.pagePerRecord"> 
							<select id="pagePerRecordOrder" name="${status.expression}" title="<ikep4j:message pre='${prefix}' key='search.pageName'/>">
								<option <c:if test="${todoSearchCondition.pagePerRecord == 10}">selected="selected"</c:if> value="10">10</option>
								<option <c:if test="${todoSearchCondition.pagePerRecord == 15}">selected="selected"</c:if> value="15">15</option>
								<option <c:if test="${todoSearchCondition.pagePerRecord == 20}">selected="selected"</c:if> value="20">20</option>
								<option <c:if test="${todoSearchCondition.pagePerRecord == 30}">selected="selected"</c:if> value="30">30</option>
								<option <c:if test="${todoSearchCondition.pagePerRecord == 40}">selected="selected"</c:if> value="40">40</option>
								<option <c:if test="${todoSearchCondition.pagePerRecord == 50}">selected="selected"</c:if> value="50">50</option>							
							</select>
							</spring:bind>
							<div class="totalNum"><ikep4j:message pre='${preSearch}' key='pageCount.info' /> <span>${searchResult.recordCount}</span></div>
						</div>
						
						<div class="tableSearch">
							<div class="subInfo">
								<!--  ikep4j:message pre='${prefix}' key='search.statusTitle'/ -->	
								<spring:bind path="todoSearchCondition.todoStatus">
								<select title="<ikep4j:message pre='${prefix}' key='search.statusTitle'/>" name="${status.expression}" id="searchStatusOrder">
									<option value="9" <c:if test="${todoSearchCondition.todoStatus == '9'}">selected="selected"</c:if>><ikep4j:message pre='${prefix}' key='search.statusOption1'/></option>
									<option value="0" <c:if test="${todoSearchCondition.todoStatus == '0'}">selected="selected"</c:if>><ikep4j:message pre='${prefix}' key='search.statusOption4'/></option>
									<option value="1" <c:if test="${todoSearchCondition.todoStatus == '1'}">selected="selected"</c:if>><ikep4j:message pre='${prefix}' key='search.statusOption3'/></option>
								</select>
								</spring:bind>
							</div>						
							<div class="subInfo">
								<!-- ikep4j:message pre='${prefix}' key='search.dateTitle'/ -->
								<input type="text" class="inputbox" readonly="readonly" id="date3" value="<fmt:formatDate value='${todoSearchCondition.fromDay}' pattern='yyyy.MM.dd'/>" title="<ikep4j:message pre='${prefix}' key='search.dateOption1'/>" size="9" /> <img class="dateicon" src="<c:url value="/base/images/icon/ic_cal.gif"/>" alt="<ikep4j:message pre='${prefix}' key='search.calendar'/>"  style="cursor:pointer;"/> ~
								<input type="text" class="inputbox" readonly="readonly" id="date4" value="<fmt:formatDate value='${todoSearchCondition.toDay}' pattern='yyyy.MM.dd'/>" title="<ikep4j:message pre='${prefix}' key='search.dateOption2'/>" size="9" /> <img class="dateicon" src="<c:url value="/base/images/icon/ic_cal.gif"/>" alt="<ikep4j:message pre='${prefix}' key='search.calendar'/>"  style="cursor:pointer;"/>
							</div>								
							<spring:bind path="todoSearchCondition.searchType">
							<select title="" name="${status.expression}">
								<option value="A" <c:if test="${todoSearchCondition.searchType == 'A'}">selected="selected"</c:if>><ikep4j:message pre='${prefix}' key='search.textOption1'/></option>
								<option value="B" <c:if test="${todoSearchCondition.searchType == 'B'}">selected="selected"</c:if>><ikep4j:message pre='${prefix}' key='search.textOption3'/></option>
							</select>
							</spring:bind>												
							<spring:bind path="todoSearchCondition.searchText">
							<input type="text" class="inputbox" id="searchText" title="<ikep4j:message pre='${prefix}' key='search.text'/>" name="${status.expression}" value="${status.value}" size="15"/>
							</spring:bind>
							<a href="#none" id="searchpagingButton" class="ic_search"><span><ikep4j:message pre='${buttonPrefix}' key='search'/></span></a>
						</div>			
						<div class="clear"></div>
					</div>
					<!--//tableTop End-->
					
					<table summary="<ikep4j:message pre='${prefix}' key='table.summary'/>">
						<caption></caption>
						<thead>
							<tr>
								<th scope="col" width="5%"><ikep4j:message pre='${prefix}' key='table.column1'/></th>
								<th scope="col" width="43%"><ikep4j:message pre='${prefix}' key='table.column3'/></th>
								<th scope="col" width="16%"><ikep4j:message pre='${prefix}' key='table.column4'/></th>
								<th scope="col" width="11%"><ikep4j:message pre='${prefix}' key='table.column5'/></th>
								<th scope="col" width="16%"><ikep4j:message pre='${prefix}' key='table.column6'/></th>
								<th class="tdLast" scope="col" width="12%"><ikep4j:message pre='${prefix}' key='table.column7'/></th>
							</tr>
						</thead>
						<tbody>	
						
							<c:choose>
							    <c:when test="${searchResult.emptyRecord}">
									<tr>
										<td colspan="7" class="emptyRecord"><ikep4j:message pre='${preCommon}' key='emptyRecord' /></td> 
									</tr>				        
							    </c:when>
							    <c:otherwise>
							    
									<c:forEach var="item" items="${searchResult.entity}" varStatus="status">
									<tr>
										<td>${(todoSearchCondition.pagePerRecord*(todoSearchCondition.pageIndex-1))+status.count}</td>
										<c:if test="${item.userStatus != 'B'}">
											<td class="textLeft" width="30%"><div class="ellipsis"><a href="<c:url value="/lightpack/todo/readOrderView.do?taskId=${item.taskId}&amp;searchConditionString=${searchConditionString}"/>" title="${item.title}">${item.title}</a></div></td>
										</c:if>
										<c:if test="${item.userStatus == 'B'}">
											<td class="textLeft" width="30%"><div class="ellipsis"><a href="<c:url value="/lightpack/todo/readOrderView.do?taskId=${item.taskId}&amp;searchConditionString=${searchConditionString}"/>" title="${item.title}"><strike>${item.title}</strike></a></div></td>
										</c:if>
										<td class="textLeft">
											<div class="ellipsis" title="<c:choose><c:when test="${user.localeCode == portal.defaultLocaleCode}">${item.workerName}</c:when><c:otherwise>${item.workerEnglishName}</c:otherwise></c:choose><c:if test="${item.workersCount != '0'}">&nbsp;<ikep4j:message pre='${prefix}' key='table.workerCountName1'/>${item.workersCount}&nbsp;<ikep4j:message pre='${prefix}' key='table.workerCountName2'/></c:if>">
												<c:choose>
													<c:when test="${user.localeCode == portal.defaultLocaleCode}">${item.workerName}</c:when>
													<c:otherwise>${item.workerEnglishName}</c:otherwise>
												</c:choose>
												<c:if test="${item.workersCount != '0'}">&nbsp;<ikep4j:message pre='${prefix}' key='table.workerCountName1'/>${item.workersCount}&nbsp;<ikep4j:message pre='${prefix}' key='table.workerCountName2'/></c:if>
											</div>
										</td>
										<td><ikep4j:timezone date="${item.startDate}" pattern="yyyy.MM.dd"/></td>
										<td><ikep4j:timezone date="${item.dueDate}" pattern="yyyy.MM.dd HH:mm"/></td>
										<td class="tdLast">
											<c:if test="${item.userStatus == 'B'}">
												<div class="ellipsis" title="<ikep4j:message pre='${messagePrefix}' key='status.complete'/>">
													<ikep4j:message pre='${messagePrefix}' key='status.complete'/>
												</div>
											</c:if>
											<c:if test="${item.userStatus == 'A1'}">
												<div class="ellipsis" title="<ikep4j:message pre='${messagePrefix}' key='status.running1'/>">
													<ikep4j:message pre='${messagePrefix}' key='status.running1'/>
												</div>
											</c:if>
											<c:if test="${item.userStatus == 'A2'}">
												<div class="ellipsis" title="<ikep4j:message pre='${messagePrefix}' key='status.delay'/> D+${item.overDayCount}">
													<span class="colorPointS"><ikep4j:message pre='${messagePrefix}' key='status.delay'/></span> <span class="delay">D+${item.overDayCount}</span>
												</div>
											</c:if>
										</td>				
									</tr>	
									</c:forEach>	
								
								</c:otherwise> 
							</c:choose> 
																																
						</tbody>
					</table>		
				</div>
				<!--//blockListTable End-->	
				
						
			
				<c:if test="${searchResult.recordCount > 0}">
					<!--Page Numbur Start--> 
					<spring:bind path="todoSearchCondition.pageIndex">
					<ikep4j:pagination searchButtonId="searchpagingButton" pageIndexInput="${status.expression}" searchCondition="${todoSearchCondition}" />
					<input name="${status.expression}" type="hidden" value="${status.value}" title="<ikep4j:message pre='${preSearch}' key='${status.expression}'/>" />
					</spring:bind> 
					<!--//Page Numbur End-->
				</c:if>
				
				</form>					