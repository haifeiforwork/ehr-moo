<%@ page language="java" errorPage="/common/error.jsp"  pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ include file="/base/common/taglibs.jsp"%>
<%-- 메시지 관련 Prefix 선언 Start --%>
<c:set var="preMessage" value="message.workflow.admin.todoAdministration" />
<c:set var="preCommonMessage" value="message.workflow.admin.common" />  
<c:set var="preHeader"  value="ui.workflow.admin.todoAdministration" /> 
<c:set var="preSearch"  value="ui.workflow.admin.todoAdministration.search" />
<c:set var="preButton"  value="ui.workflow.admin.todoAdministration.button" />
<link rel="stylesheet" type="text/css" href="<c:url value='/base/css/workflow/style.css'/>" />

<script type="text/javascript">
(function($){
	//=========================================================================
	//* OnLoad 함수
	//=========================================================================
	$(document).ready(function() {
		//=========================================================================
		//* 상태값(ComboBox) 설정 
		//=========================================================================
		$("#todoAdministrationSearchState > option[value=${todoSearchCondition.todoAdministrationSearchState}]").attr("selected",true);
		//=========================================================================
		//* 한페이지에 보여줄 건수 설정 
		//=========================================================================
		$("#pagePerRecord > option[value=${todoSearchCondition.pagePerRecord}]").attr("selected",true);
		
		$('#todoAdministrationSearchUser').keypress( function(event) {
			iKEP.searchUser(event, "N", setUser); 
		});
		
		$('#userSearchBtn').click( function() { 
			$('#todoAdministrationSearchUser').trigger("keypress");
		});

		
		//=========================================================================
		//* 검색 및 페이지 클릭이벤트
		//=========================================================================
		$("#todoSearchBoardItemButton").click(function() {
			$("#pageIndex").val("1");
			goSubmit();
		}); 
		
		//=========================================================================
		//* 페이지 건수 변경시 이벤트
		//=========================================================================
		$("#pagePerRecord").change(function() {
			$("#todoSearchBoardItemButton").click();
		});
		
		//=========================================================================
		//* 체크박스 전체 선택시 이벤트
		//=========================================================================
		$("#checkboxAllProcess").click(function() { 
			$("input[name=checkboxProcess]").attr("checked", $(this).is(":checked"));  
		}); 
		
		//=========================================================================
		// * 마우스 오버/아웃시 CSS적용하기 위한 함수
		//=========================================================================
		$("#tbody_todoList > tr").hover(
			function(){$(this).addClass("bgSelected").css("cursor","pointer");},
			function(){$(this).removeClass("bgSelected").css("cursor","pointer");}
		);
		
		//=========================================================================
		// * 프로세스중에 한건을 클릭시
		//=========================================================================
		$("#tbody_todoList > tr > td").click(function(){
		});
		
		$("#todoAdministrationSearchStartDate,#todoAdministrationSearchEndDate").datepicker({
			showOn: "button",     
			buttonImage: "<c:url value='/base/images/icon/ic_cal.gif'/>",     
			buttonImageOnly: true,
			buttonText : "<ikep4j:message pre='${preHeader}' key='calendar' />",
		 	onSelect: function(date, event) {
		 		var arrDate = date.split("-");
    	        var objDate = new Date(arrDate[0], (arrDate[1]-1), parseInt(arrDate[2], 10) + 6);
    	        
    	        if($(this).attr("id") == "todoAdministrationSearchStartDate" && $("#todoAdministrationSearchEndDate").attr("value").length > 0){
    	        	if((Number($("#todoAdministrationSearchEndDate").attr("value").replace(/\./g,""))) - (Number($("#todoAdministrationSearchStartDate").attr("value").replace(/\./g,""))) < 0 ){
    	        		$("#todoAdministrationSearchStartDate").val("");
    	        		$("#todoAdministrationSearchDateFlag").val("end");
    	    			show_layer("<ikep4j:message pre='${preCommonMessage}' key='alert.searchDayCondition' />","return");
    	    			return false;
    	        	}
    	        }
    	        
    	        if($(this).attr("id") == "todoAdministrationSearchEndDate"){
    	    		if((Number($("#todoAdministrationSearchEndDate").attr("value").replace(/\./g,""))) - (Number($("#todoAdministrationSearchStartDate").attr("value").replace(/\./g,""))) < 0 ){
    	    			$("#todoAdministrationSearchEndDate").val("");
    	    			$("#todoAdministrationSearchDateFlag").val("start");
    	    			show_layer("<ikep4j:message pre='${preCommonMessage}' key='alert.searchDayCondition' />","return");
    	    			return false;
    	    		}
    	        }
    	        
    	        event.input.next("input.datepicker:eq(0)").val(objDate.getFullYear() + "-" + (objDate.getMonth()+1) + "-" + objDate.getDate());
    	        
    	        
    	        if($("#todoAdministrationSearchStartDate").attr("value").length > 0 && $("#todoAdministrationSearchEndDate").attr("value").length > 0){
    	        	$("#todoAdministrationSearchDateFlag").val("start");
    	        }else if($("#todoAdministrationSearchStartDate").attr("value").length > 0){
    	        	$("#todoAdministrationSearchDateFlag").val("start");
    	        }else if($("#todoAdministrationSearchEndDate").attr("value").length > 0){
    	        	$("#todoAdministrationSearchDateFlag").val("end");
    	        }
		 	}
		}); 
	});
	
	goSubmit  = function(){
		var todoAdministrationForm = document.getElementById("todoAdministrationForm");
		todoAdministrationForm.action = "<c:url value='/workflow/admin/todoAdministration.do'/>";
		todoAdministrationForm.submit();
	}
	
	//=========================================================================
	//* ProcessList 정렬 이벤트
	//=========================================================================
	sortProcess = function(sortColumn, sortType){
		$("input[name=sortColumn]").val(sortColumn);
		
		if( sortType == 'ASC') {
			$("input[name=sortType]").val('ASC');	
		} else {
			$("input[name=sortType]").val('DESC');
		}
		
		$("#todoSearchBoardItemButton").click();
	};
	
	setUser = function(data) {
		$("#todoAdministrationSearchUser").val(data[0].userName);
	};
	
	//=========================================================================
	//* Alert 및 Confirm 팝업 
	//=========================================================================
	show_layer = function(alertName,returnValue){
		$("#tdAlertName").html(alertName);
		if(returnValue == "return"){
			$("#divAlertReturn").css("display","inline");
			$("#divAlertInfo").css("display","none");
			$("#divConfirm").css("display","none");
		}else if(returnValue == "info"){
			$("#divAlertReturn").css("display","none");
			$("#divAlertInfo").css("display","inline");
			$("#divConfirm").css("display","none");
		}else{
			$("#divAlertReturn").css("display","none");
			$("#divAlertInfo").css("display","none");
			$("#divConfirm").css("display","inline");
		}
		$("#layer_p").dialog({width: 300, height:150, modal:true, resizable: false, draggable : false, open:function(event,ui){ $('body').css('overflow','hidden');},close:function(event,ui){$('body').css('overflow','');}});
		$("#layer_p").dialog("widget").addClass("ui-dialog1").addClass("ui-widget-content1");
		$("#layer_p").dialog("widget").children(":first").addClass("ui-widget-workflow-header");
	}
	
	//=========================================================================
	//* 팝업창에서 확인 및 취소 버튼 클릭시 이벤트 
	//=========================================================================
	fnDialogType = function(type,flag){
		$("#layer_p").dialog("close");
	}
})(jQuery);
</script>
<!--blockSearch Start-->

<form id="todoAdministrationForm" method="post" onsubmit="return false;">
<spring:bind path="todoSearchCondition.sortColumn">
	<input name="${status.expression}" type="hidden" value="${status.value}" />
</spring:bind> 		
<spring:bind path="todoSearchCondition.sortType">
	<input name="${status.expression}" type="hidden" value="${status.value}" />
</spring:bind>
<spring:bind path="todoSearchCondition.todoAdministrationSearchDateFlag">
<input type="hidden" name="${status.expression}" id="${status.expression}"  value="${status.value}">
</spring:bind>
<h1 class="none"><ikep4j:message pre='${preHeader}' key='todoManagement'/></h1>
				
<!--pageTitle Start-->
<div id="pageTitle">
	<h2><ikep4j:message pre='${preHeader}' key='todoManagement'/></h2>
	<div id="pageLocation">
		<ul>
			<li class="liFirst"><ikep4j:message pre='${preHeader}' key='workflow'/></li>
			<li><ikep4j:message pre='${preHeader}' key='current'/></li>
			<li class="liLast"><ikep4j:message pre='${preHeader}' key='todoManagement'/></li>
		</ul>
	</div>
</div>
<!--//pageTitle End-->

<!--blockSearch Start-->
<div class="blockSearch">
	<div class="corner_RoundBox03">
		<table summary="<ikep4j:message pre='${preHeader}' key='searchCondition'/>">
			<caption></caption>
			<tbody>
				<tr>
					<th scope="row" width="5%"><ikep4j:message pre='${preHeader}' key='author'/></th>
					<td width="45%" colspan="3">
						<spring:bind path="todoSearchCondition.todoAdministrationSearchUser">
						<input type="text" class="inputbox" title="<ikep4j:message pre='${preHeader}' key='author'/>" name="${status.expression}" id="${status.expression}" value="${status.value}" size="20" />
						</spring:bind>	
						<a href="#a" class="ic_search" style="vertical-align:bottom;" name="userSearchBtn" id="userSearchBtn" title="<ikep4j:message pre='${preHeader}' key='button.search'/>"><span><ikep4j:message pre='${preHeader}' key='button.search'/></span></a>
					</td>
				</tr>
				<tr>
					<th scope="row"><ikep4j:message pre='${preHeader}' key='title'/></th>
					<td>
						<spring:bind path="todoSearchCondition.todoAdministrationSearchTitle">
						<input type="text" class="inputbox" title="<ikep4j:message pre='${preHeader}' key='title'/>" name="${status.expression}" id="${status.expression}" value="${status.value}" size="20" />
						</spring:bind>
					</td>
					<th scope="row" width="5%"><ikep4j:message pre='${preHeader}' key='todoStartDate'/></th>
					<td width="45%">								
						<div class="subInfo">
							<spring:bind path="todoSearchCondition.todoAdministrationSearchStartDate">
							<input type="text" id="${status.expression}" name="${status.expression}" class="inputbox datepicker" title="<ikep4j:message pre='${preSearch}' key='startDate'/>" value="${status.value}" size="10" readonly>
							</spring:bind>
							~
							<spring:bind path="todoSearchCondition.todoAdministrationSearchEndDate">
							<input type="text" id="${status.expression}" name="${status.expression}" class="inputbox datepicker" title="<ikep4j:message pre='${preSearch}' key='endDate'/>" value="${status.value}" size="10" readonly>
							</spring:bind>
						</div>
					</td>	
				</tr>
				</tbody>
		</table>
		<div class="searchBtn">
			<a id="todoSearchBoardItemButton" name="todoSearchBoardItemButton" href="#a" title="<ikep4j:message pre='${preHeader}' key='button.search'/>"><span><ikep4j:message pre='${preHeader}' key='button.search'/></span></a>
		</div>
		<div class="l_t_corner"></div>
		<div class="r_t_corner"></div>
		<div class="l_b_corner"></div>
		<div class="r_b_corner"></div>				
	</div>
</div>	
<!--//blockSearch End-->

<div class="blockShuttle" >
	<div class="sbox">
		<c:if test="${!todoSearchResult.emptyRecord}">
		<div class="listInfo">  
			<spring:bind path="todoSearchCondition.pagePerRecord">  
				<select name="${status.expression}" id="${status.expression}" title='<ikep4j:message pre='${preHeader}' key='pageCount'/>' />'>
					<option value="10">10</option>
					<option value="15">15</option>
					<option value="20">20</option>
					<option value="30">30</option>
					<option value="40">40</option>
					<option value="50">50</option>
				</select> 
			</spring:bind>
			<div class="totalNum">${todoSearchResult.pageIndex}/ ${todoSearchResult.pageCount} <ikep4j:message pre='${preHeader}' key='page'/> ( <ikep4j:message pre='${preHeader}' key='totalCount'/><span> ${todoSearchResult.recordCount}</span>)</div>
		</div>
		<div class="clear"></div>
		</c:if>
		<div class="blockListTable">
			<table summary="<ikep4j:message pre='${preHeader}' key='todoManagement'/>" style="font-size:13;">
				<caption></caption>
				<thead>
					<tr>
						<th scope="col" width="5%"><input id="checkboxAllProcess" class="checkbox" title="checkbox" type="checkbox" value="" /></th>
						<th scope="col" width="10%">
							<a onclick="sortProcess('LOG_ID', '<c:if test="${todoSearchCondition.sortColumn eq 'LOG_ID'}">${todoSearchCondition.sortType}</c:if>');"  href="#a">
								<ikep4j:message pre='${preHeader}' key='taskId'/>
							</a>
						</th>
						<th scope="col" width="10%">
							<a onclick="sortProcess('PROCESS_NAME', '<c:if test="${todoSearchCondition.sortColumn eq 'PROCESS_NAME'}">${todoSearchCondition.sortType}</c:if>');"  href="#a">
								<ikep4j:message pre='${preHeader}' key='processName'/>
							</a>
						</th>
						<th scope="col" width="10%">
							<a onclick="sortProcess('ACTIVITY_NAME', '<c:if test="${todoSearchCondition.sortColumn eq 'ACTIVITY_NAME'}">${todoSearchCondition.sortType}</c:if>');"  href="#a">
								<ikep4j:message pre='${preHeader}' key='activityName'/>
							</a>
						</th>
						<th scope="col" width="35%">
							<a onclick="sortProcess('TITLE', '<c:if test="${todoSearchCondition.sortColumn eq 'TITLE'}">${todoSearchCondition.sortType}</c:if>');"  href="#a">
								<ikep4j:message pre='${preHeader}' key='title'/>
							</a>
						</th>
						<th scope="col" width="10%">
							<a onclick="sortProcess('AUTHOR', '<c:if test="${todoSearchCondition.sortColumn eq 'AUTHOR'}">${todoSearchCondition.sortType}</c:if>');"  href="#a">
								<ikep4j:message pre='${preHeader}' key='author'/>
							</a>
						</th>
						<th scope="col" width="10%">
							<a onclick="sortProcess('CREATE_DATE', '<c:if test="${todoSearchCondition.sortColumn eq 'CREATE_DATE'}">${todoSearchCondition.sortType}</c:if>');"  href="#a">
								<ikep4j:message pre='${preHeader}' key='startDate'/>
							</a>
						</th>
						<th scope="col" width="10%">
							<a onclick="sortProcess('STATE', '<c:if test="${todoSearchCondition.sortColumn eq 'STATE'}">${todoSearchCondition.sortType}</c:if>');"  href="#a">
								<ikep4j:message pre='${preHeader}' key='todoState'/>
							</a>
						</th>
					</tr>
				</thead>
				<c:choose>
				<c:when test="${todoSearchResult.emptyRecord}">
				<tbody>
					<tr>
						<td colspan="8"><ikep4j:message pre='${preCommonMessage}' key='dataNotFound'/></td>
					</tr>
					<c:forEach begin="${fn:length(todoSearchResult.entity)}" end="${todoSearchCondition.pagePerRecord-2}" step="1" >
					<tr>
						<td colspan="8">&nbsp;</td>
					</tr>
					</c:forEach>	
				</tbody>
				</c:when>
				<c:otherwise>
				<tbody id="tbody_todoList">
					<c:forEach var="todoList" items="${todoSearchResult.entity}" varStatus="status">
						<tr>
							<td width="5%"><input name="checkboxProcess" class="checkbox" title="checkbox" type="checkbox" value="${todoList.logId}" /></td>
							<td width="10%"><div class="ellipsis">${todoList.logId}</div></td>
							<td width="10%"><div class="ellipsis">${todoList.processName}</div></td>
							<td width="10%"><div class="ellipsis">${todoList.activityName}</div></td>					
							<td width="35%" id="td_todoId" class="textLeft"><div class="ellipsis">${todoList.title}</div></td>
							<td width="10%"><div class="ellipsis">${todoList.author}</div></td>
							<td width="10%"><div class="ellipsis"><ikep4j:timezone pattern="yyyy.MM.dd hh:mm" date="${todoList.createDate}"/></div></td>
							<td width="10%">
								<c:choose>
									<c:when test="${todoList.state == 'ASSIGN'}">
										<ikep4j:message pre='${preHeader}' key='assign'/>
									</c:when>
									<c:when test="${todoList.state == 'SELECT'}">
										<ikep4j:message pre='${preHeader}' key='select'/>
									</c:when>
									<c:otherwise>
										<ikep4j:message pre='${preHeader}' key='overdue'/>
									</c:otherwise>
								</c:choose>
							</td>
						</tr>
					</c:forEach>
				</tbody>
				<tbody>
					<c:forEach begin="${fn:length(todoSearchResult.entity)}" end="${todoSearchCondition.pagePerRecord-1}" step="1" >
					<tr>
						<td colspan="8">&nbsp;</td>
					</tr>
					</c:forEach>
				</tbody>
				</c:otherwise>
				</c:choose>
			</table>
		</div>
		<!--Page Numbur Start--> 
		<spring:bind path="todoSearchCondition.pageIndex">
		<ikep4j:pagination searchFormId="todoAdministrationForm" ajaxEventFunctionName="goSubmit" pageIndexInput="${status.expression}" searchCondition="${todoSearchCondition}" />
		<input name="${status.expression}" id="${status.expression}" type="hidden" value="${status.value}" title="<ikep4j:message pre='${preHeader}' key='currentPageIndex'/>" />
		</spring:bind> 
		<!--//Page Numbur End-->
	</div>
</div>
</form>
<div class="none ui-dialog1" id="layer_p" title="<ikep4j:message pre='${preHeader}' key='alert'/>" style="overflow:hidden;">
	<!--blockDetail Start-->
	<div class="blockDetail_2" style="width:255px">
		<table summary="<ikep4j:message pre='${preHeader}' key='alert'/>">
			<caption></caption>
			<tbody>
				<tr>
					<td class="textCenter">
						<span id="tdAlertName" class="textCenter"></span>
					</td>
				</tr>
			</tbody>
		</table>
	</div>
	<!--//blockDetail End-->
	<!--blockButton Start-->
	<div id="divAlertReturn" class="blockButton" style="padding-left:120px;display:none;"> 
		<a class="button_s" href="#" onclick="fnDialogType('close','return');" title="<ikep4j:message pre='${preButton}' key='confirm'/>">
			<span><ikep4j:message pre='${preButton}' key='confirm'/></span>
		</a>
	</div>
	<div id="divAlertInfo" class="blockButton" style="padding-left:120px;display:none;"> 
		<a class="button_s" href="#" onclick="fnDialogType('close','info');" title="<ikep4j:message pre='${preButton}' key='confirm'/>">
			<span><ikep4j:message pre='${preButton}' key='confirm'/></span>
		</a>
	</div>
	<div id="divConfirm" class="blockButton" style="padding-left:110px;display:none;"> 
		<a class="button_s" href="#" onclick="fnDialogType('open');" title="<ikep4j:message pre='${preButton}' key='confirm'/>">
			<span><ikep4j:message pre='${preButton}' key='confirm'/></span>
		</a>
		<a class="button_s" href="#" onclick="fnDialogType('close','return');" title="<ikep4j:message pre='${preButton}' key='cancel'/>">
			<span><ikep4j:message pre='${preButton}' key='cancel'/></span>
		</a>
	</div>
</div>