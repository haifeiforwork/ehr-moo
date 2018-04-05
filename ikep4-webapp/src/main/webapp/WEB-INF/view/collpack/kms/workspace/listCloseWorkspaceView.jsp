<%--
=====================================================
	* 기능설명	:	나의 Collaboration 목록
	* 작성자		:	김종철
=====================================================
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%-- Tab Lib Start --%> 
<%@ include file="/base/common/taglibs.jsp"%>
<%-- Tab Lib End --%> 

<%-- 메시지 관련 Prefix 선언 Start --%> 
<c:set var="preHeader"  value="message.collpack.collaboration.workspace.listCloseWorkspace.header" />
<c:set var="preSearch"  value="message.collpack.collaboration.workspace.listCloseWorkspace.search" />
<c:set var="preCommon"  value="message.collpack.common" />
<c:set var="preList"    value="message.collpack.collaboration.workspace.listCloseWorkspace.list" />
<c:set var="preButton"  value="message.collpack.collaboration.workspace.listCloseWorkspace.button" />
<c:set var="preScript"  value="message.collpack.collaboration.workspace.listCloseWorkspace.script" />
<%-- 메시지 관련 Prefix 선언 End --%>

<%-- 세션정보 선언 Start --%> 
<c:set var="portal" value="${sessionScope['ikep.portal']}" />
<c:set var="user" value="${sessionScope['ikep.user']}" />
<%-- 세션정보 선언 End --%>

<script type="text/javascript">
<!-- 
(function($) {

	// 주의 : 글로벌 함수는 아래와 같이 글로벌 변수에 무명 함수를 assign해서 생성한다. 
	//정렬 조건 변경 함수
	sort = function(sortColumn, sortType) {

		$jq("input[name=sortColumn]").val(sortColumn);
		
		if( sortType == 'ASC') {
			$jq("input[name=sortType]").val('DESC');	
		} else {
			$jq("input[name=sortType]").val('ASC');
		}
		
		$jq("#searchButton").click();
	};
	search = function() {

		$jq("input[name=pageIndex]").val('1');
		$jq("#searchButton").click();
	};
	
	//뷰모드 체인지 함수
	changeViewMode = function(changeViewMode) {
		$jq("input[name=viewMode]").val(changeViewMode);
		$jq("#mainForm").submit(); 
		return false; 
	};
	searchCategory = function(id) {
		$jq("input[name=pageIndex]").val('1');
		$jq("select[name=typeId]").val(id)
	    $jq("#searchButton").click();
	    
		return false; 
	};	
	// Collaboration 조회
	viewWorkspace = function(workspaceId,status) {
		if(status=="O" || status=="WC")
		{
			parent.goWorkspace(workspaceId);
		}
		else
		{
			$jq('#mainForm').attr({
				action:"<c:url value="/collpack/collaboration/workspace/readWorkspaceView.do"/>"
			});
			$jq("input[name=workspaceId]").val(workspaceId);
			$jq("#mainForm").submit(); 			
		}
		
	
		return false;
	}; 
	defaultSetting = function(workspaceId) {
		
		$jq.get('<c:url value="/collpack/collaboration/member/updateWorkspaceDefaultSet.do"/>',
			{workspaceId:workspaceId},
			function(data){
				//alert(data)
				$jq("#searchButton").click();
			}
		)
		return false;
	}; 
	
	// onload시 수행할 코드
	$jq(document).ready(function() { 
		iKEP.iFrameContentResize();  
		$jq("#searchButton").click(function() {
			$jq("#mainForm").attr({method:'get'}).submit();		
			return false; 
		});
		$jq("select[name=pagePerRecord]").change(function() {
			//서브밋하는 코드 넣으시면 됩니다.
			//ex : $jq("#searchBoardItemButton").click(); 
			$jq("input[name=pageIndex]").val('1');
			$jq("#searchButton").click(); 
		});  
		
		$jq("#deleteMemberButton").click(function() {
			var countCheckBox	=	$jq("input[name=workspaceIds]:checkbox:checked").length;
			if(countCheckBox>0)
			{
			    $jq.post('<c:url value="/collpack/collaboration/member/deleteWorkspaceMemberAjax.do"/>', $jq("#mainForm").serialize())
			    .success(function() { alert("second success"); })
			    .error(function() { alert("error"); })
			    .complete(function() { alert("complete"); });
			}
			else
			{
				alert('<ikep4j:message pre='${preScript}' key='checkbox' />');
			}
			return false; 	
	
		}); 
		$jq("#deleteWorkspaceButton").click(function() {
			
			var countCheckBox	=	$jq("input[name=workspaceIds]:checkbox:checked").length;

			if(countCheckBox>0)
			{
				if( confirm('<ikep4j:message pre='${preScript}' key='delete' />') ){
					$jq.ajax({
						url : '<c:url value='/collpack/collaboration/workspace/deleteWorkspaceAjax.do' />?'+$jq("#mainForm").serialize(),
						type : "get",
						success : function(result) {
							$jq("#searchButton").click(); 
						}
					});	
				}
			}
			else
			{
				alert('<ikep4j:message pre='${preScript}' key='checkDelete' />');
			}
			return false; 	
	
		}); 		
		$jq("#checkboxAll").click(function() { 
			$jq("input[name=workspaceIds]").attr("checked", $jq(this).is(":checked"));  
		}); 
	   
	});
})(jQuery);  
//-->
</script>


<h1 class="none"><ikep4j:message pre="${preHeader}" key="pageTitle" /></h1> 

<!--pageTitle Start-->
<div id="pageTitle"> 
	<h2><ikep4j:message pre="${preHeader}" key="pageTitle" /></h2> 
</div> 
<!--//pageTitle End--> 
 
<div class="corner_RoundBox01" style="margin-bottom:20px;">
	<ikep4j:message pre='${preHeader}' key='pageMessage' />
	<div class="l_t_corner"></div>
	<div class="r_t_corner"></div>
	<div class="l_b_corner"></div>
	<div class="r_b_corner"></div>					
</div>

<!--blockListTable Start-->
<form id="mainForm" name="mainForm" method="get" action="<c:url value="/collpack/collaboration/workspace/listCloseWorkspaceView.do"/>">  
<spring:bind path="searchCondition.sortColumn">
<input name="${status.expression}" type="hidden" value="${status.value}" title="<ikep4j:message pre='${preSearch}' key='${status.expression}'/>" />
</spring:bind> 		
<spring:bind path="searchCondition.sortType">
<input name="${status.expression}" type="hidden" value="${status.value}" title="<ikep4j:message pre='${preSearch}' key='${status.expression}'/>" />
</spring:bind> 
<spring:bind path="searchCondition.workspaceId">
<input name="${status.expression}" type="hidden" value="${status.value}" title="<ikep4j:message pre='${preSearch}' key='${status.expression}'/>" />
</spring:bind> 
<spring:bind path="searchCondition.workspaceStatus">
<input name="${status.expression}" type="hidden" value="${status.value}" title="<ikep4j:message pre='${preSearch}' key='${status.expression}'/>" />
</spring:bind>
<spring:bind path="searchCondition.listType">
<input name="${status.expression}" type="hidden" value="${status.value}" title="<ikep4j:message pre='${preSearch}' key='${status.expression}'/>" />
</spring:bind>
<spring:bind path="searchCondition.listUrl">
<input name="${status.expression}" type="hidden" value="${status.value}" title="<ikep4j:message pre='${preSearch}' key='${status.expression}'/>" />
</spring:bind>



<div class="blockListTable">
	<!--tableTop Start-->
	<div class="tableTop">
		<div class="tableTop_bgR"></div>
		
		<div class="listInfo">  
			<spring:bind path="searchCondition.pagePerRecord">  
				<select name="${status.expression}" title='<ikep4j:message pre='${preSearch}' key='${status.expression}' />'>
				<c:forEach var="code" items="${workspaceCode.pageNumList}">
					<option value="${code.key}" <c:if test="${code.key eq status.value}">selected="selected"</c:if>>${code.value}</option>
				</c:forEach> 
				</select> 
			</spring:bind>
			<div class="totalNum"> <ikep4j:message pre='${preSearch}' key='pageCount.info' /><span> ${searchResult.recordCount}</span></div>
		</div>
		
		

		<div class="tableSearch">
			<select name="typeId" title='<ikep4j:message pre='${preSearch}' key='searchTypeId' />'>
				<option value="" > <ikep4j:message pre='${preSearch}' key='searchAll'/> </option>
				<c:forEach var="workspaceTypeList" items="${workspaceTypeList}" varStatus="status">
				<option value="${workspaceTypeList.typeId}" <c:if test="${workspaceTypeList.typeId==searchCondition.typeId }">selected</c:if> > 
					<c:choose>
					<c:when test="${user.localeCode == portal.defaultLocaleCode}">
						${workspaceTypeList.typeName}
					</c:when>
					<c:otherwise>
						${workspaceTypeList.typeEnglishName}
					</c:otherwise>
					</c:choose>	
				</option>
				</c:forEach>	
			</select>	
			<spring:bind path="searchCondition.searchColumn">  
				<select name="${status.expression}" title='<ikep4j:message pre='${preSearch}' key='${status.expression}' />'>
					<option value="WORKSPACE_NAME" <c:if test="${'WORKSPACE_NAME' eq status.value}">selected="selected"</c:if> ><ikep4j:message pre='${preSearch}' key='${status.expression}' post='workspaceName'/></option>
					<option value="SYSOP_NAME" <c:if test="${'SYSOP_NAME' eq status.value}">selected="selected"</c:if> ><ikep4j:message pre='${preSearch}' key='${status.expression}' post='sysopName'/></option>
				</select>		
			</spring:bind>		
			<spring:bind path="searchCondition.searchWord">  					
				<input name="${status.expression}" value="${status.value}" type="text" class="inputbox" title='<ikep4j:message pre='${preSearch}' key='${status.expression}' />'  size="20" />
			</spring:bind>
			<a id="searchButton" name="searchButton" href="#a" class="ic_search"><span>Search</span></a>
		</div>
						
		<div class="clear"></div>
	</div>
	<!--//tableTop End-->
	
	

	<table summary="<ikep4j:message pre="${preList}" key="summary" />">   
		<caption></caption>
		<col style="width: 5%;"/>
		<col style="width: 7%;"/>
		<col style="width: 10%;"/>		
		<col style="width: 58%;"/>
		<col style="width: 10%;"/>
		<col style="width: 10%;"/>


		<thead>
			<tr>
				<th scope="col"><input id="checkboxAll" class="checkbox" title="checkbox" type="checkbox" value="" /></th>
				<th scope="col"><ikep4j:message pre='${preList}' key='no' /></th>				
				<th scope="col">
					<a onclick="sort('typeId', '<c:if test="${searchCondition.sortColumn eq 'typeId'}">${searchCondition.sortType}</c:if>');"  href="#a">
						<ikep4j:message pre='${preList}' key='typeId' />
					</a>
				</th>

				<th scope="col">
					<a onclick="sort('workspaceName', '<c:if test="${searchCondition.sortColumn eq 'workspaceName'}">${searchCondition.sortType}</c:if>');"  href="#a">
						<ikep4j:message pre='${preList}' key='workspaceName' />
					</a>
				</th>


				<th scope="col">
					<a onclick="sort('sysopName', '<c:if test="${searchCondition.sortColumn eq 'sysopName'}">${searchCondition.sortType}</c:if>');"  href="#a">
						<ikep4j:message pre='${preList}' key='sysopName' />
					</a>
				</th>	
			
				<th scope="col">
					<a onclick="sort('joinDate', '<c:if test="${searchCondition.sortColumn eq 'joinDate'}">${searchCondition.sortType}</c:if>');"  href="#a">
						<ikep4j:message pre='${preList}' key='closeDate' />
					</a>
				</th>

			</tr>
		</thead> 
		<tbody>
			<c:choose>
			    <c:when test="${searchResult.emptyRecord}">
					<tr>
						<td colspan="6" class="emptyRecord"><ikep4j:message pre='${preSearch}' key='emptyRecord' /></td> 
					</tr>				        
			    </c:when>
			    <c:otherwise>
					<c:forEach var="workspaceList" items="${searchResult.entity}" varStatus="status">
					<tr>
						<td><input id="workspaceIds" name="workspaceIds" class="checkbox" title="checkbox" type="checkbox" value="${workspaceList.workspaceId}" /></td>
						<td>${searchResult.recordCount - status.index - (searchCondition.pagePerRecord * ( searchResult.pageIndex - 1))}</td>
						<td>
							<c:choose>
							<c:when test="${user.localeCode == portal.defaultLocaleCode}">
								${workspaceList.typeName}
							</c:when>
							<c:otherwise>
								${workspaceList.typeEnglishName}
							</c:otherwise>
							</c:choose>	
						</td>
						<td class="textLeft">
							<a onclick="viewWorkspace('${workspaceList.workspaceId}','${workspaceList.workspaceStatus}');" href="#a" title="${workspaceList.workspaceName}">
							${workspaceList.workspaceName}
							</a>
						</td>
						<td>
							<a href="#a"  onclick="iKEP.showUserContextMenu(this, '${workspaceList.sysopId}', 'bottom');iKEP.iFrameContentResize(); return false;">
							<c:choose>
							<c:when test="${user.localeCode == portal.defaultLocaleCode}">
								${workspaceList.sysopName}
							</c:when>
							<c:otherwise>
								${workspaceList.sysopEnglishName}
							</c:otherwise>
							</c:choose>		
							</a>						
						</td>
						<td>
						<c:if test="${not empty workspaceList.closeDate}"><ikep4j:timezone pattern="yyyy.MM.dd" date="${workspaceList.closeDate}"/></c:if>
						</td>
					</tr>
					</c:forEach>				      
			    </c:otherwise> 
			</c:choose>  
		</tbody>
	</table>

	
	
	<!--Page Numbur Start--> 
	<spring:bind path="searchCondition.pageIndex">
	<ikep4j:pagination searchButtonId="searchButton" pageIndexInput="${status.expression}" searchCondition="${searchCondition}" />
	<input name="${status.expression}" type="hidden" value="${status.value}" title="<ikep4j:message pre='${preSearch}' key='${status.expression}'/>" />
	</spring:bind> 
	<!--//Page Numbur End--> 
	
</div>
</form>
<!--//blockListTable End--> 
	 
<!--blockButton Start-->
<div class="blockButton">
<ul>
	<li><a id="deleteWorkspaceButton" class="button" href="#a" title="<ikep4j:message pre='${preButton}' key='remove'/>"><span><ikep4j:message pre='${preButton}' key='remove'/></span></a></li>
</ul>
</div>
<!--//blockButton End-->
		

