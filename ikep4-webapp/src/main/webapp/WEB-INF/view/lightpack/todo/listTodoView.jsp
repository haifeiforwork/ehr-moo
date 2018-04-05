<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/base/common/taglibs.jsp"%> 

<%-- 메시지 관련 Prefix 선언 Start --%> 
<c:set var="prefix">ui.lightpack.todo.listTodoView</c:set>
<c:set var="messagePrefix">ui.lightpack.todo.message</c:set>
<c:set var="buttonPrefix">ui.lightpack.todo.button</c:set>
<c:set var="preSearch">ui.lightpack.todo.common.searchCondition</c:set>
<c:set var="menuPrefix">ui.lightpack.todo.subMenu</c:set>
<c:set var="preCommon">ui.lightpack.todo.common</c:set>
<%-- 메시지 관련 Prefix 선언 End --%>

<c:set var="user" value="${sessionScope['ikep.user']}" />
<c:set var="portal" value="${sessionScope['ikep.portal']}" />
 
<script type="text/javascript">
//<![CDATA[
(function($) { 
	$jq(document).ready(function(){	
		// 메뉴 카운트 변경
		window.parent.setCountMyTodo("${countMyTodo}");

		$jq("#date1").datepicker().next().eq(0).click(function() { 
			$jq(this).prev().eq(0).datepicker("show"); 
		});
		$jq("#date2").datepicker().next().eq(0).click(function() { 
			$jq(this).prev().eq(0).datepicker("show"); 
		});
		
		$jq("#pagePerRecordTodo").change(function() {
            searchTodo();
        });
		
		//검색버튼클릭
		$jq("#searchpagingButton").click(function() {
			searchTodo();
		});
		$jq('#searchText').keypress( function(event) { 
            if(event.which == 13) {
				searchTodo();
            }
		});
		
		$jq("#searchStatusTodo").change(function() {
			statusChangeTodo();
        });
		
		//메뉴 페이지의 Todo 갯수 변경
		window.parent.setCountMyTodo("${countMyTodo}");
	});
	
	//status Todo 검색 변경
	statusChangeTodo = function() {
		var val = $jq("#searchStatusTodo option:selected").val();
		if(val == "9" || val == "1") {
			$jq("#date1").val('${nowDay_pre_14}');
			$jq("#date2").val("${nowDay}");
		} else if(val == "0") {
			$jq("#date1").val("");
			$jq("#date2").val("");
		}
		
		searchTodo();
	};
	
	//Todo 검색
	searchTodo = function() {
		$fromDay = $jq('#date1').val();
		$toDay = $jq('#date2').val();
		
		if($fromDay=="" && $toDay=="") {
			$jq('#form').submit();
		} else {
			$fromDay = $jq("#date1").val();
			if($fromDay.length < 10) {
				alert("<ikep4j:message pre='${messagePrefix}' key='validation.fromDay'/>");
				return;
			} else {
				$jq('#form>input[name=fromDay]').val($jq("#date1").val() + " 00:00:00");
			}
			
			$toDay = $jq("#date2").val();
			if($toDay.length < 10) {
				alert("<ikep4j:message pre='${messagePrefix}' key='validation.toDay'/>");
				return;
			} else {
				$jq('#form>input[name=toDay]').val($jq("#date2").val() + " 23:59:59");
			}
			
			$jq('#form').submit();
		}
	};

	//체크박스 전체선택
	selectAllTodo = function() {
		$jq('input[name=chkid]').attr("checked", $jq('#checkAll').is(":checked"));
	};
	
	//todo 제목 클릭
	goURL = function(isTodo, target, url, systemCode, subworkCode, taskKey, workerId) {
		if(isTodo == "N") {
			alert("<ikep4j:message pre='${messagePrefix}' key='goUrl'/>");
		} else {
			var pageURL = url + "?systemCode=" + systemCode + "&subworkCode=" + subworkCode + "&taskKey=" + taskKey + "&workerId=" + workerId;
			if(target == "WINDOW") {
				window.open (pageURL, '', 'fullscreen=yes, resizable=yes, scrollbar=yes, toolbar=yes, menubar=yes');
			} else {
				window.location.href = pageURL + "&searchConditionString=${searchConditionString}";
			}
		}		
	};
	
	//자동완료
	autoComplete = function() {
		var cnt = $jq('input[name=chkid]:checked').length;
		if(cnt < 1) {
			alert("<ikep4j:message pre='${messagePrefix}' key='validation.selected'/>"); 
			return;
		}
		
		var idlist = [];
		var itemstr ="";
		$jq('input[name=chkid]:checked').each(function(){idlist.push(this.value)});
		$jq.each(idlist,function(index, item) {itemstr += item + ',';});
		itemstr = itemstr.substring(0, itemstr.length-1);

		window.location.href = "<c:url value='/lightpack/todo/updateTodo.do?keys='/>" + itemstr + "&searchConditionString=${searchConditionString}";
	};	

})(jQuery);
//]]>
</script>


				<h1 class="none"></h1>

				<form id="form" action='<c:url value="/lightpack/todo/listTodoView.do"/>' method="post">
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
						<h2>나의 업무 내역</h2>
						<div class="listInfo" id="topShowPage2">
							<spring:bind path="todoSearchCondition.pagePerRecord">
							<select id="pagePerRecordTodo" name="${status.expression}" title="<ikep4j:message pre='${prefix}' key='search.pageName'/>">
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
								<select title="<ikep4j:message pre='${prefix}' key='search.statusTitle'/>" name="${status.expression}" id="searchStatusTodo">
									<option value="9" <c:if test="${todoSearchCondition.todoStatus == '9'}'">selected="selected"</c:if> ><ikep4j:message pre='${prefix}' key='search.statusOption1'/></option>
									<option value="0" <c:if test="${todoSearchCondition.todoStatus == '0'}">selected="selected"</c:if>><ikep4j:message pre='${prefix}' key='search.statusOption2'/></option>
									<option value="1" <c:if test="${todoSearchCondition.todoStatus == '1'}">selected="selected"</c:if>><ikep4j:message pre='${prefix}' key='search.statusOption3'/></option>
								</select>
								</spring:bind>
							</div>						
							<div class="subInfo">
								<!-- ikep4j:message pre='${prefix}' key='search.dateTitle'/ -->
								<input type="text" class="inputbox" readonly="readonly" id="date1" value="<fmt:formatDate value='${todoSearchCondition.fromDay}' pattern='yyyy.MM.dd'/>" title="<ikep4j:message pre='${prefix}' key='search.dateOption1'/>" size="9" /> <img class="dateicon" src="<c:url value="/base/images/icon/ic_cal.gif"/>" alt="<ikep4j:message pre='${prefix}' key='search.calendar'/>"  style="cursor:pointer;"/> ~
								<input type="text" class="inputbox" readonly="readonly" id="date2" value="<fmt:formatDate value='${todoSearchCondition.toDay}' pattern='yyyy.MM.dd'/>" title="<ikep4j:message pre='${prefix}' key='search.dateOption2'/>" size="9" /> <img class="dateicon" src="<c:url value="/base/images/icon/ic_cal.gif"/>" alt="<ikep4j:message pre='${prefix}' key='search.calendar'/>"  style="cursor:pointer;"/>&nbsp;&nbsp;
							</div>	
							<spring:bind path="todoSearchCondition.searchType">							
							<select name="${status.expression}">
								<option value="A" <c:if test="${todoSearchCondition.searchType == 'A'}">selected="selected"</c:if>><ikep4j:message pre='${prefix}' key='search.textOption1'/></option>
								<option value="B" <c:if test="${todoSearchCondition.searchType == 'B'}">selected="selected"d</c:if>><ikep4j:message pre='${prefix}' key='search.textOption2'/></option>
							</select>
							</spring:bind>
							<spring:bind path="todoSearchCondition.searchText">
							<input type="text" class="inputbox" id="searchText" title="<ikep4j:message pre='${prefix}' key='search.text'/>" name="${status.expression}" value="${status.value}" size="15" />
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
								<th scope="col" width="3%"><input type="checkbox" id="checkAll" class="chk" onclick="selectAllTodo()" title="checkbox"/></th>
								<th scope="col" width="3%"><ikep4j:message pre='${prefix}' key='table.column2'/></th>
								<%-- <th scope="col" width="18%"><ikep4j:message pre='${prefix}' key='table.column3'/></th> --%>
								<th scope="col" width="28%"><ikep4j:message pre='${prefix}' key='table.column4'/></th>
								<th scope="col" width="9%"><ikep4j:message pre='${prefix}' key='table.column5'/></th>
								<th scope="col" width="11%"><ikep4j:message pre='${prefix}' key='table.column6'/></th>
								<th scope="col" width="16%"><ikep4j:message pre='${prefix}' key='table.column7'/></th>
								<th class="tdLast" scope="col" width="12%"><ikep4j:message pre='${prefix}' key='table.column8'/></th>
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
										<c:if test="${item.isComplete == 0}">
											<c:if test="${item.systemCode == todoSystemCode && (item.subworkCode == todoSubworkCode || item.subworkCode == todoSubworkCode2)}">
												<td><input name="chkid" class="checkbox" title="checkbox" type="checkbox" value="${item.systemCode},${item.subworkCode},${item.taskKey},${item.workerId}" /></td>
											</c:if>
											<c:if test="${item.systemCode != todoSystemCode || (item.subworkCode != todoSubworkCode && item.subworkCode != todoSubworkCode2)}">
												<td><input name="" class="checkbox" title="checkbox" type="checkbox" disabled="disabled" readonly="readonly" /></td>
											</c:if>
											<td>${(todoSearchCondition.pagePerRecord*(todoSearchCondition.pageIndex-1))+status.count}</td>
											<%-- <td class="textLeft"><div class="ellipsis" title="[${item.systemName}]&nbsp;${item.subworkName}">[${item.systemName}]&nbsp;${item.subworkName}</div></td> --%>
											<td class="textLeft" width="30%">
												<c:if test="${item.systemCode == todoSystemCode && (item.subworkCode == todoSubworkCode || item.subworkCode == todoSubworkCode2)}">
													<div class="ellipsis" title="${item.title}"><a href="#a" onclick="goURL('Y', '${item.target}', '<c:url value="${item.url}"/>', '${item.systemCode}', '${item.subworkCode}', '${item.taskKey}', '${item.workerId}')">${item.title}</a></div>
												</c:if>
												<c:if test="${item.systemCode != todoSystemCode || (item.subworkCode != todoSubworkCode && item.subworkCode != todoSubworkCode2)}">
													<div class="ellipsis" title="${item.title}"><a href="#a" onclick="goURL('N', '${item.target}', '<c:url value="${item.url}"/>', '${item.systemCode}', '${item.subworkCode}', '${item.taskKey}', '${item.workerId}')">${item.title}</a></div>
												</c:if>
											</td>
										</c:if>
										<c:if test="${item.isComplete == 1}">
											<td><input name="" class="checkbox" title="checkbox" type="checkbox" disabled="disabled" readonly="readonly" /></td>
											<td>${(todoSearchCondition.pagePerRecord*(todoSearchCondition.pageIndex-1))+status.count}</td>
											<%-- <td class="textLeft"><div class="ellipsis" title="[${item.systemName}]&nbsp;${item.subworkName}"><strike>[${item.systemName}]&nbsp;${item.subworkName}</strike></div></td> --%>
											<td class="textLeft" width="30%">
												<c:if test="${item.systemCode == todoSystemCode && (item.subworkCode == todoSubworkCode || item.subworkCode == todoSubworkCode2)}">
													<div class="ellipsis" title="${item.title}"><a href="#a" onclick="goURL('Y', '${item.target}', '<c:url value="${item.url}"/>', '${item.systemCode}', '${item.subworkCode}', '${item.taskKey}', '${item.workerId}')"><strike>${item.title}</strike></a></div>
												</c:if>
												<c:if test="${item.systemCode != todoSystemCode || (item.subworkCode != todoSubworkCode && item.subworkCode != todoSubworkCode2)}">
													<div class="ellipsis" title="${item.title}"><a href="#a" onclick="goURL('N', '${item.target}', '<c:url value="${item.url}"/>', '${item.systemCode}', '${item.subworkCode}', '${item.taskKey}', '${item.workerId}'"><strike>${item.title}</strike></a></div>
												</c:if>
											</td>
										</c:if>
										<td><c:choose>
											    <c:when test="${user.localeCode == portal.defaultLocaleCode}">${item.etcName}</c:when>
											    <c:otherwise>${item.etcEnglishName}</c:otherwise>
										    </c:choose>
										</td>
										<td><ikep4j:timezone date="${item.syncInsertDate}" pattern="yyyy.MM.dd"/></td>
										<td><ikep4j:timezone date="${item.dueDate}" pattern="yyyy.MM.dd HH:mm"/></td>
										<td class="tdLast">
												<c:if test="${item.todoStatusName == 'B'}">
													<div class="ellipsis" title="<ikep4j:message pre='${messagePrefix}' key='status.complete'/>">
														<ikep4j:message pre='${messagePrefix}' key='status.complete'/>
													</div>
												</c:if>
												<c:if test="${fn:substring(item.todoStatusName, 0, 1) == 'A'}">
													<div class="ellipsis" title="${fn:substring(item.todoStatusName, 1, fn:length(item.todoStatusName))}">
														${fn:substring(item.todoStatusName, 1, fn:length(item.todoStatusName))}
													</div>
												</c:if>
												<c:if test="${fn:substring(item.todoStatusName, 0, 1) == 'D'}">
													<div class="ellipsis" title="<ikep4j:message pre='${messagePrefix}' key='status.delay'/> D+${fn:substring(item.todoStatusName, 1, fn:length(item.todoStatusName))}">
														<span class="colorPointS"><ikep4j:message pre='${messagePrefix}' key='status.delay'/></span> <span class="delay">D+${fn:substring(item.todoStatusName, 1, fn:length(item.todoStatusName))}</span>
													</div>
												</c:if>
										</td>   
									</tr>	
									</c:forEach>	
									
								</c:otherwise> 
							</c:choose> 
																																		
						</tbody>
					</table>	
					
					<!--Page Numbur Start-->
					<c:if test="${searchResult.recordCount > 0}">
						<spring:bind path="todoSearchCondition.pageIndex">
						<ikep4j:pagination searchButtonId="searchpagingButton" pageIndexInput="${status.expression}" searchCondition="${todoSearchCondition}" />
						<input name="${status.expression}" type="hidden" value="${status.value}" title="<ikep4j:message pre='${preSearch}' key='${status.expression}'/>" />
						</spring:bind>
					</c:if>
					<!--//Page Numbur End-->	
								
				</div>
				<!--//blockListTable End-->	
			
			
				<c:if test="${searchResult.recordCount > 0}">
					<!--blockButton Start-->
						<div class="blockButton"> 
							<ul>
								<li><a class="button" href="#none" onclick="autoComplete()"><span>업무완료</span></a></li>
							</ul>
						</div>
					<!--//blockButton End-->
				</c:if>
			</form>