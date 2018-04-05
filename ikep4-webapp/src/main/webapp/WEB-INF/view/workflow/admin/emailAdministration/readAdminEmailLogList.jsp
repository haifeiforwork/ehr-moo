<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/base/common/taglibs.jsp"%>

<%@ taglib uri="/WEB-INF/tld/ikep4j.tld" prefix="ikep4j" %> 


<%-- 메시지 관련 Prefix 선언 Start --%> 
<c:set var="preHeader"  value="ui.workflow.admin.adminEmail.header" />
<c:set var="preList"    value="ui.workflow.admin.adminEmail.list" />
<c:set var="preMsgs"    value="ui.workflow.admin.adminEmail.messages" />
<c:set var="preSearch"  value="ui.workflow.admin.adminEmail.searchCondition" />
<c:set var="preButton"  value="ui.workflow.admin.adminEmail.button" />

 <%-- 메시지 관련 Prefix 선언 End --%>
<c:if test="${!empty removeResult}">
<script language=javaScript>
	alert("<ikep4j:message pre="${preMsgs}" key="${removeResult}" />");
</script> 
</c:if>
<script type="text/javascript">
<!-- 
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
		
		$("#searchEmailLogsButton").click();
	};
	
	/**
	 * onload시 수행할 코드
	 */
	$(document).ready(function() { 
		/**
		 * 검색 버튼
		 */
		$("#searchEmailLogsButton").click(function() {
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

 		/**
 		 * 체크박스 ALL 
 		 */
		$("#checkAllEmailLogs").click(function() { 
			$("input[name=logIds]").attr("checked", $(this).is(":checked"));  
		}); 
 		
		/**
		 * 삭제 버튼
		 */
		$("#delButton").click(function() {   
			if( confirm("<ikep4j:message pre="${preMsgs}" key="delConfirm" />") ) {
				document.searchForm.action = "<c:url value='/workflow/admin/emailAdministration/removeAdminEmailLogs.do' />"; 
				document.searchForm.submit(); 
			}
		});
		
		/**
		 * 테스트 버튼
		 */
		$("#testButton").click(function() {   
			//alert("작업중");
			window.open("<c:url value='/workflow/admin/emailAdministration/testSend.do'/>","sendTest","width=800,height=450,menubar=N,scrolling=N");
		});
		
	});
})(jQuery);  
//-->
</script>

<h1 class="none"><ikep4j:message pre="${preHeader}" key="title" /></h1>

<!--pageTitle Start-->
<div id="pageTitle">
	<h2><ikep4j:message pre="${preHeader}" key="pageTitle" /></h2>
	<div id="pageLocation">
		<ul>
			<li class="liFirst">Home</li>
			<li>Workflow</li>
			<li><ikep4j:message pre='${preHeader}' key='submenu' /></li>
			<li class="liLast"><ikep4j:message pre='${preHeader}' key='pageTitle' /></li>
		</ul>
	</div>
</div>
<!--//pageTitle End-->

<!--blockSearch Start-->
<form name="searchForm" id="searchForm" method="post" action="<c:url value='/workflow/admin/emailAdministration/listAdminEmailLogs.do' />">

	<spring:bind path="searchCondition.sortColumn">
	<input name="${status.expression}" type="hidden" value="${status.value}" title="<ikep4j:message pre='${preSearch}' key='${status.expression}'/>" />
	</spring:bind> 		
	<spring:bind path="searchCondition.sortType">
	<input name="${status.expression}" type="hidden" value="${status.value}" title="<ikep4j:message pre='${preSearch}' key='${status.expression}'/>" />
	</spring:bind> 

<div class="blockSearch">
	<div class="corner_RoundBox03">
		<table summary="검색">
			<caption></caption>
			<tbody>
				<tr>
					<spring:bind path="searchCondition.emailTitle">
					<th scope="row" width="5%"><ikep4j:message pre='${preSearch}' key='${status.expression}' /></th>
					<td width="28%">
						<input name="${status.expression}" value="${status.value}" type="text" class="inputbox" title='<ikep4j:message pre='${preSearch}' key='${status.expression}' />'  size="20" />
					</td>
					</spring:bind>
					
					<th scope="row" width="5%">
						<spring:bind path="searchCondition.userType">
						<select title="<ikep4j:message pre='${preSearch}' key='${status.expression}' />" name="${status.expression}">
							<option value="SENDER" <c:if test="${status.value eq 'SENDER'}"> selected</c:if>><ikep4j:message pre="${preSearch}.userType" key="sender"/></option>
							<option value="SENDER_EMAIL" <c:if test="${status.value eq 'SENDER_EMAIL'}"> selected</c:if>><ikep4j:message pre="${preSearch}.userType" key="senderEmail"/></option>
							<option value="RECEIVER" <c:if test="${status.value eq 'RECEIVER'}"> selected</c:if>><ikep4j:message pre="${preSearch}.userType" key="receiver"/></option>
							<option value="RECEIVER_EMAIL" <c:if test="${status.value eq 'RECEIVER_EMAIL'}"> selected</c:if>><ikep4j:message pre="${preSearch}.userType" key="receiverEmail"/></option>
						</select>
						</spring:bind>
					</th>
					<td width="28%">
						<spring:bind path="searchCondition.userValue">
						<input name="${status.expression}" value="${status.value}" type="text" class="inputbox" title='<ikep4j:message pre='${preSearch}' key='${status.expression}' />'  size="20" />
						</spring:bind>
					</td>
					
					<th scope="row" width="5%">
						<spring:bind path="searchCondition.dateType">
						<select title="<ikep4j:message pre='${preSearch}' key='${status.expression}' />" name="${status.expression}">
							<option value="SEND_DATE" <c:if test="${status.value eq 'SEND_DATE'}"> selected</c:if>><ikep4j:message pre="${preSearch}.dateType" key="sendDate"/></option>
							<option value="CREATE_DATE" <c:if test="${status.value eq 'CREATE_DATE'}"> selected</c:if>><ikep4j:message pre="${preSearch}.dateType" key="createDate"/></option>
						</select>
						</spring:bind>
					</th>
					<td width="28%">
						<spring:bind path="searchCondition.searchStartDate">
							<input type="text" class="inputbox" id="${status.expression}" name="${status.expression}" title="<ikep4j:message pre='${preSearch}' key='${status.expression}' />" value="${status.value}" size="10" /> <img class="dateicon" src="/ikep4-webapp/base/images/icon/ic_cal.gif" alt="달력" /> ~
						</spring:bind>
						<spring:bind path="searchCondition.searchEndDate">
							<input type="text" class="inputbox" id="${status.expression}" name="${status.expression}" title="<ikep4j:message pre='${preSearch}' key='${status.expression}' />" value="${status.value}" size="10" /> <img class="dateicon" src="/ikep4-webapp/base/images/icon/ic_cal.gif" alt="달력" />
						</spring:bind>							
					</td>
				</tr>								
			</tbody>
		</table>
		<div class="searchBtn">
			<a id="searchEmailLogsButton" name="searchEmailLogsButton" href="#a"><img src="<c:url value='/base/images/theme/theme01/basic/btn_search.gif' />" alt="검색" /></a>
		</div>
		<div class="l_t_corner"></div>
		<div class="r_t_corner"></div>
		<div class="l_b_corner"></div>
		<div class="r_b_corner"></div>
	</div>
</div>	
<!--//blockSearch End-->

<h1 class="none">
	<ikep4j:message pre='${preHeader}' key='pageTitle' />
</h1>

<!--blockListTable Start-->
<div class="blockListTable">

	<!--tableTop Start-->
	<div class="tableTop">
		<div class="listInfo">
			<spring:bind path="searchCondition.pagePerRecord">  
				<select name="${status.expression}" title='<ikep4j:message pre='${preSearch}' key='${status.expression}' />'>
				<c:forEach var="code" items="${commonCode.pageNumList}">
					<option value="${code.key}" <c:if test="${code.key eq status.value}">selected="selected"</c:if>>${code.value}</option>
				</c:forEach> 
				</select> 
			</spring:bind>
			<div class="totalNum">${emailLogList.pageIndex}/ ${emailLogList.pageCount} <ikep4j:message pre='${preSearch}' key='page' /> ( <ikep4j:message pre='${preSearch}' key='pageCount.info' /><span> ${emailLogList.recordCount}</span>)</div>
		</div>			
		<div class="clear"></div>
	</div>
	<!--//tableTop End-->
	
	
	<table summary="<ikep4j:message pre='${preHeader}' key='pageTitle' />">
		<caption></caption>
		<col style="width: 5%;"/>
		<col style="width: 15%;"/>
		<col/>
		<col style="width: 15%;"/>
		<col style="width: 15%;"/>
		<col style="width: 10%;"/>
		<thead>
			<tr>
				<th scope="col"><input id="checkAllEmailLogs" class="checkbox" title="checkbox" type="checkbox" value="" /></th>
				<th scope="col">
					<ikep4j:message pre='${preList}' key='senderEmail' />
				</th>
				<th scope="col">
					<ikep4j:message pre='${preList}' key='emailTitle' />&nbsp;&nbsp;
					<a onclick="f_Sort('EMAIL_TITLE', '<c:if test="${searchCondition.sortColumn eq 'EMAIL_TITLE'}">${searchCondition.sortType}</c:if>');"  href="#a">
						<img src="<c:url value='/base/images/icon/ic_tablesort_up.gif'/>" alt="오름차순"/><img src="<c:url value='/base/images/icon/ic_tablesort_down.gif'/>" alt="내림차순"/>
					</a>
				</th>
				<th scope="col">
					<ikep4j:message pre='${preList}' key='receiverEmail' />
				</th>
				<th scope="col">
					<ikep4j:message pre='${preList}' key='sendDate' />&nbsp;&nbsp;
					<a onclick="f_Sort('SEND_DATE', '<c:if test="${searchCondition.sortColumn eq 'SEND_DATE'}">${searchCondition.sortType}</c:if>');"  href="#a">
						<img src="<c:url value='/base/images/icon/ic_tablesort_up.gif'/>" alt="오름차순"/><img src="<c:url value='/base/images/icon/ic_tablesort_down.gif'/>" alt="내림차순"/>
					</a>
				</th>
				<th scope="col">
					<ikep4j:message pre='${preList}' key='isSuccess' />
				</th>
			</tr>
		</thead>
		<tbody>
			<c:choose>
			    <c:when test="${emailLogList.emptyRecord}">
					<tr>
						<td colspan="6" class="emptyRecord"><ikep4j:message pre='${preSearch}' key='emptyRecord' /></td> 
					</tr>
			    </c:when>
			    <c:otherwise>
						<c:forEach var="loglist" items="${emailLogList.entity}" varStatus="i">
							<c:choose>
					 			<c:when test="${i.count % 2 == 0}">
					 				<c:set var="className" value="bgWhite"/>
					 			</c:when>
					 			<c:otherwise>
					 				<c:set var="className" value="bgSelected"/>
					 			</c:otherwise>
					 		</c:choose>
							<tr class="${className}">
								<td><input 	id="logIds" name="logIds" class="checkbox" title="checkbox" type="checkbox" value="${loglist.logId}"/></td>
								<td>${loglist.senderEmail}</td>
								<td style="text-align:left;">${loglist.emailTitle}</td>
								<td>${loglist.receiverEmail}</td>
								<td>${loglist.sendDate}</td>
								<td>${loglist.isSuccess}</td>
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

<!--blockButton Start-->
<div class="blockButton"> 
	<ul> 
		<li><a id="delButton" class="button" href="#a"><span><ikep4j:message pre='${preButton}' key='delete'/></span></a></li>
		<li><a id="testButton" class="button" href="#a"><span>발송테스트</span></a></li>
	</ul>
</div>
<script type="text/javascript" language="javascript">
(function($) {

		$jq(document).ready(function() {
		    
				$("#searchStartDate").datepicker()
					.next().eq(0).click(function() { $(this).prev().eq(0).datepicker("show"); });
				
				$("#searchEndDate").datepicker()
				.next().eq(0).click(function() { $(this).prev().eq(0).datepicker("show"); });
				
				$jq('#searchStartDate').change( function() {
					  if( $jq('#searchEndDate').val() != '' ) {
						    if( $jq('#searchStartDate').val().replaceAll('-','') > $jq('#searchEndDate').val().replaceAll('-','') ) {
						    	  alert("<ikep4j:message pre="${preMsgs}" key="dateFault" />");
						    	  $jq('#searchStartDate').val("");
						    } 
					  } else {
						    $jq('#searchEndDate').val($jq('#searchStartDate').val());
					  }
				});
				
				$jq('#searchEndDate').change( function() { 
					  if( $jq('#searchStartDate').val() != '' ) {
						    if( $jq('#searchStartDate').val().replaceAll('-','') > $jq('#searchEndDate').val().replaceAll('-','') ) {
						    	  alert("<ikep4j:message pre="${preMsgs}" key="dateFault" />");
						    	  $jq('#searchEndDate').val("");
						    }
					  } else {
						  	$jq('#searchStartDate').val($jq('#searchEndDate').val());
					  }
				});

		});

})(jQuery);
</script>
<!--//blockButton End-->