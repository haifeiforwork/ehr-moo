<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%-- Tab Lib Start --%> 
<%@ include file="/base/common/taglibs.jsp"%>
<%-- Tab Lib End --%> 

<%-- 메시지 관련 Prefix 선언 Start --%>
<c:set var="preMessage"	value="ui.approval.admin.apprReadGroup.listView"/>					
<c:set var="preHeader"	value="ui.approval.admin.apprReadGroup.listView.header"/>
<c:set var="preSearch"	value="ui.approval.admin.apprReadGroup.listView.search"/>
<c:set var="preList"	value="ui.approval.admin.apprReadGroup.listView.list"/>
<c:set var="preButton"	value="ui.approval.admin.apprReadGroup.listView.button"/>	
				
<%-- 메시지 관련 Prefix 선언 End --%>

<%-- 세션정보 선언 Start --%> 
<c:set var="portal" value="${sessionScope['ikep.portal']}" />
<c:set var="user" value="${sessionScope['ikep.user']}" />
<%-- 세션정보 선언 End --%>

<script type="text/javascript" src="<c:url value="/base/js/units/approval/approval.js"/>"></script>
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
	sort = function(sortColumn, sortType) {

		$jq("input[name=sortColumn]").val(sortColumn);
		
		if( sortType == 'ASC') {
			$jq("input[name=sortType]").val('DESC');	
		} else {
			$jq("input[name=sortType]").val('ASC');
		}
		
		$jq("#searchListButton").click();
	};
	
	
	// 결재선 수정
	viewReadGroup = function(userId) {
		location.href = "<c:url value='/approval/admin/config/updateApprReadGroupForm.do'/>?userId="+userId;
	};

	/**
	 * onload시 수행할 코드
	 */
	$(document).ready(function() {
		
		$jq("#apprReadGroupLinkOfLeft").click();
		
		$("#searchListButton").click(function() {
			$("input[name=pageIndex]").val('1');
			$("#mainForm").submit(); 
			return false; 
		});
		
		/**
		 * 페이징 버튼
		 */
		$("#pageIndex").click(function() {
			$("#mainForm").submit(); 
			return false; 
		});
		
		$("select[name=pagePerRecord]").change(function(){
            $("#pageIndex").click();
        });
 
		$("#checkboxAll").click(function() { 
			$("input[name=userIds]").attr("checked", $jq(this).is(":checked"));  
		});
		
		$("#deleteButton").click(function() {
			var countCheckBox	=	$("input[name=userIds]:checkbox:checked").length;
			alert($("#mainForm").serialize());
			if(countCheckBox>0)
			{
			
				$jq.ajax({
					url : '<c:url value='/approval/admin/config/deleteReadGroupAjax.do' />?'+$("#mainForm").serialize(),
					type : "get",
					success : function(result) {
						$jq("#searchListButton").click(); 
					},
					error : function(event, request, settings){
			 			alert("error");
					}
				});				
			}
			else
			{
				alert('<ikep4j:message pre='${preMessage}' key='checkbox' />');
			}
			return false; 	
	
		});
		
		//열람권한 설정
		$("#createButton").click(function() {  
			location.href = "<c:url value='/approval/admin/config/createApprReadGroupForm.do'/>";
		});	
		

	});
})(jQuery);  
//-->
</script>
			
<h1 class="none"><ikep4j:message pre='${preHeader}' key='pageTitle' /></h1>

<!--pageTitle Start-->
<div id="pageTitle">
	<h2><ikep4j:message pre='${preHeader}' key='pageTitle' /></h2>
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

<!--blockSearch Start-->
<form id="mainForm" name="mainForm" method="post" action="<c:url value="/approval/admin/config/listApprReadGroup.do"/>">

<!--blockListTable Start-->
<div class="blockListTable">

	<!--tableTop Start-->
	<div class="tableTop">
		<div class="listInfo">
			<spring:bind path="searchCondition.pagePerRecord">
			<select name="${status.expression}" title='<ikep4j:message pre='${preSearch}' key='${status.expression}' />'>
				<c:forEach var="commonCode" items="${commonCode.pageNumList}">
					<option value="${commonCode.key}" <c:if test="${commonCode.key eq status.value}">selected="selected"</c:if>>${commonCode.value}</option>
				</c:forEach> 
			</select> 
			</spring:bind>
			
			<div class="totalNum">${searchResult.pageIndex}/ ${searchResult.pageCount} <ikep4j:message pre='${preSearch}' key='page' /> ( <ikep4j:message pre='${preSearch}' key='pageCount.info' /><span> ${searchResult.recordCount}</span>)</div>
		</div>
		
		<div class="tableSearch"> 
			<spring:bind path="searchCondition.searchColumn">  
				<select name="${status.expression}" title='<ikep4j:message pre='${preSearch}' key='${status.expression}' />'>
					<option value="userName" <c:if test="${'userName' eq status.value}">selected="selected"</c:if>><ikep4j:message pre='${preSearch}' key='userName'/></option>
					<option value="groupName" <c:if test="${'groupName' eq status.value}">selected="selected"</c:if>><ikep4j:message pre='${preSearch}' key='groupName'/></option>
				</select>		
			</spring:bind>		
			<spring:bind path="searchCondition.searchWord">  					
				<input name="${status.expression}" value="${status.value}" type="text" class="inputbox" title='<ikep4j:message pre='${preSearch}' key='${status.expression}' />'  size="20" />
			</spring:bind>
			<a id="searchListButton" name="searchListButton" href="#a"><img src="<c:url value='/base/images/theme/theme01/basic/btn_search.gif' />" alt="search" /></a>			
		</div>				
		<div class="clear"></div>
	</div>
	<!--//tableTop End-->
	
	<table summary="<ikep4j:message pre='${preList}' key='summary1' />">
		<caption></caption>
		<colgroup>
<!-- 		<col width="3%"/> -->
		<col width="10%"/>
		<col width="20%"/>
		<col width="20%"/>
		<col width="20%"/>
		<col width="20%"/>
		</colgroup>
		<tbody>
			<tr>
<!-- 				<th scope="col"><input id="checkboxAll" class="checkbox" title="checkbox" type="checkbox" value="" /></th> -->
				<th scope="col"><ikep4j:message pre='${preList}' key='no' /></th>
				<th scope="col"><ikep4j:message pre='${preList}' key='userId' /></th>
				<th scope="col"><ikep4j:message pre='${preList}' key='userName' /></th>
				<th scope="col"><ikep4j:message pre='${preList}' key='userGroupName' /></th>
				<th scope="col"><ikep4j:message pre='${preList}' key='registDate' /></th>
			</tr>

			<c:choose>
			    <c:when test="${searchResult.emptyRecord}">
					<tr>
						<td colspan="5" class="emptyRecord"><ikep4j:message pre='${preSearch}' key='emptyRecord' /></td>
					</tr>
			    </c:when>
			    <c:otherwise>
					<c:forEach var="apprReadGroupList" items="${searchResult.entity}" varStatus="status">
						<tr>
<%-- 							<td><input id="userIds" name="userIds" class="checkbox" title="checkbox" type="checkbox" value="${apprReadGroupList.userId}|${apprReadGroupList.groupId}" /></td> --%>
							<td>${searchCondition.recordCount -((searchCondition.pageIndex - 1) * searchCondition.pagePerRecord) - status.index}</td>
							<td>${apprReadGroupList.userId}</td>
							<td><a href="#a" onclick="javascript:viewReadGroup('${apprReadGroupList.userId}')">${apprReadGroupList.userName}&nbsp;${apprReadGroupList.jobTitleName}</a></td>
							<td>${apprReadGroupList.teamName}</td>
						    <td><ikep4j:timezone date="${apprReadGroupList.registDate}" pattern="yyyy.MM.dd"/></td>			
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
		<li><a id="createButton" class="button" href="#a"><span><ikep4j:message pre='${preButton}' key='create'/></span></a></li>
<%-- 		<li><a id="deleteButton"   class="button" href="#a"><span><ikep4j:message pre='${preButton}' key='delete'/></span></a></li> --%>
	</ul>
</div>

<div id="previewDetailDiv"></div>

<!--//blockButton End-->