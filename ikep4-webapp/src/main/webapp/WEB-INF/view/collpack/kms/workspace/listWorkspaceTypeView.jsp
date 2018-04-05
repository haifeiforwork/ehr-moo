<%--
=====================================================
	* 기능설명	:	Collaboration Type 별 목록(조직/TFT/Cop/Informal)
	* 작성자		:	김종철
=====================================================
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%-- Tab Lib Start --%> 
<%@ include file="/base/common/taglibs.jsp"%>
<%-- Tab Lib End --%> 

<%-- 메시지 관련 Prefix 선언 Start --%> 
<c:set var="preHeader"  value="message.collpack.collaboration.workspace.listWorkspaceType.header" />
<c:set var="preSearch"  value="message.collpack.collaboration.workspace.listWorkspaceType.search" />
<c:set var="preCommon"  value="message.collpack.common" />
<c:set var="preList"    value="message.collpack.collaboration.workspace.listWorkspaceType.list" />
<c:set var="preButton"  value="message.collpack.collaboration.workspace.listWorkspaceType.button" />
<c:set var="preScript"  value="message.collpack.collaboration.workspace.listWorkspaceType.script" />
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
	
	//뷰모드 체인지 함수
	changeViewMode = function(changeViewMode) {
		$jq("input[name=viewMode]").val(changeViewMode);
		$jq("#mainForm").submit(); 
		return false; 
	};
	
	searchCategory = function(id) {
		/**
		** 조직/ 조직외 구분
		**/
		if('${workspaceType.isOrganization}'=='1')
			$jq("select[name=groupId]").val(id)
		else
			$jq("select[name=categoryId]").val(id)

	    $jq("#searchButton").click();
	    
		return false; 
	};
	
	// Collaboration 조회
	viewWorkspace = function(workspaceId,status) {
		if(status=="O" || status=="WC")
		{
			/*$jq('form[name=mainForm]').attr({
				'action':"<c:url value="/collpack/collaboration/main/Workspace.do"/>",
				'method':"GET"
			});	
			$jq("input[name=workspaceId]").val(workspaceId);
			$jq("#mainForm").submit(); 
			*/
			parent.goWorkspace(workspaceId);		
		}
		else
		{
			$jq('form[name=mainForm]').attr({
				action:"<c:url value="/collpack/collaboration/workspace/readWorkspaceView.do"/>"
			});
			$jq("input[name=workspaceId]").val(workspaceId);
			$jq("#mainForm").submit(); 
		}

		
		return false;
	}; 

	// onload시 수행할 코드
	$jq(document).ready(function() { 
		
		iKEP.iFrameContentResize();  
		
		$jq("#searchButton").click(function() {
			$jq("#mainForm").submit();		
			return false; 
		});
		$jq("select[name=pagePerRecord]").change(function() {
			$jq("#searchButton").click(); 
		}); 
		$jq("#checkboxAll").click(function() { 
			$jq("input[name=workspaceIds]").attr("checked", $jq(this).is(":checked"));  
		}); 
		
	});
})(jQuery);  
//-->
</script>


<h1 class="none">${workspaceType.typeName}</h1> 

<!--pageTitle Start-->
<div id="pageTitle"> 
	<h2>${workspaceType.typeName} <span>: ${workspaceType.typeDescription}</span></h2> 
	<%--div id="pageLocation">
		<ul>
			<li class="liFirst"><ikep4j:message pre="${preHeader}" key="pageLocation.1depth" /></li>
			<li><ikep4j:message pre="${preHeader}" key="pageLocation.2depth" /></li>
			<li class="liLast">${workspaceType.typeName}</li>
		</ul>
	</div--%>
</div> 
<!--//pageTitle End-->  


<!--blockListTable Start-->
<form id="mainForm" name="mainForm" method="get" action="<c:url value='/collpack/collaboration/workspace/listWorkspaceTypeView.do' />">  
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
<spring:bind path="searchCondition.typeId">
<input name="${status.expression}" type="hidden" value="${status.value}" title="<ikep4j:message pre='${preSearch}' key='${status.expression}'/>" />
</spring:bind>
<spring:bind path="searchCondition.isOrganization">
<input name="${status.expression}" type="hidden" value="${workspaceType.isOrganization}" title="<ikep4j:message pre='${preSearch}' key='${status.expression}'/>" />
</spring:bind>
<spring:bind path="searchCondition.viewMode">
<input name="${status.expression}" type="hidden" value="${status.value}" title="<ikep4j:message pre='${preSearch}' key='${status.expression}'/>" />
</spring:bind>


<!--blockbox Start-->
<c:if test="${workspaceType.isOrganization==0}">
<div class="Box_type_01">
	<table summary="<ikep4j:message pre="${preList}" key="summaryType" />">
		<caption></caption>
		<tbody>
			<c:forEach var="countWorkspaceTypeList" items="${countWorkspaceTypeList}" varStatus="status">
				<c:if test="${status.index==0 }">
				<tr>
					<td colspan="4" class="total">
					<a href="#a" onclick="searchCategory('')" <c:if test="${empty searchCondition.categoryId}"> class="selected"</c:if>><ikep4j:message pre="${preList}" key="totalWorkspace" /> (${countWorkspaceTypeList.countWorkspace })</a>		
					</td>
				</tr>			
				</c:if>
				
				<c:if test="${status.index!=0 }">
					<c:if test="${(status.index-1)%4==0 }">
					<tr>
					</c:if>
						
						<td width="25%"><a href="#a" onclick="searchCategory('${countWorkspaceTypeList.categoryId}')" <c:if test="${countWorkspaceTypeList.categoryId==searchCondition.categoryId}"> class="selected"</c:if>>
						<c:choose>
						<c:when test="${user.localeCode == portal.defaultLocaleCode}">
							${countWorkspaceTypeList.categoryName}
						</c:when>
						<c:otherwise>
							${countWorkspaceTypeList.categoryEnglishName}
						</c:otherwise>
						</c:choose>					
						<span class="colorPoint">(${countWorkspaceTypeList.countWorkspace })</span></a></td>
					
					<c:if test="${(status.count-1)%4==0 || status.last}">
					</tr>
					</c:if>
				</c:if>
			</c:forEach>
		</tbody>
	</table>
</div>	
</c:if>

<c:if test="${workspaceType.isOrganization==1}">

<div class="Box_type_01">
	<table summary="<ikep4j:message pre="${preList}" key="summaryType" />">
		<caption></caption>
		<tbody>
			<c:forEach var="countGroupList" items="${countGroupList}" varStatus="status">
			<c:if test="${status.index==0 }">
			<tr>
				<td colspan="4" class="total">
				<a href="#a" onclick="searchCategory('')" class="selected"><ikep4j:message pre="${preList}" key="totalWorkspace" /> (${countGroupList.countWorkspace })
				<c:if test="${!empty group}">
				<c:choose>
				<c:when test="${user.localeCode == portal.defaultLocaleCode}">
					- ${group.groupName}
				</c:when>
				<c:otherwise>
					- ${group.groupEnglishName}
				</c:otherwise>
				</c:choose>
				</c:if>				
				</a>
				</td>
			</tr>			
			</c:if>
			
			<c:if test="${status.index!=0 }">
				<c:if test="${(status.index-1)%4==0 }">
				<tr>
				</c:if>
				
					<td width="25%"><a href="#a" onclick="searchCategory('${countGroupList.groupId}')" <c:if test="${countGroupList.groupId==searchCondition.groupId}"> class="selected"</c:if>>
					<c:choose>
					<c:when test="${user.localeCode == portal.defaultLocaleCode}">
						${countGroupList.groupName} 
					</c:when>
					<c:otherwise>
						${countGroupList.groupEnglishName} 
					</c:otherwise>
					</c:choose>						
					<span class="colorPoint">(${countGroupList.countWorkspace })</span></a></td>
				
				<c:if test="${(status.count-1)%4==0 || status.last}">
				</tr>
				</c:if>
			</c:if>
			</c:forEach>
		</tbody>
	</table>
</div>
</c:if>
<!--//blockbox End-->



	<div class="<c:if test="${searchCondition.viewMode!='LIST'}">corporateView TeamColl_summaryView</c:if><c:if test="${searchCondition.viewMode=='LIST'}">blockListTable</c:if>">
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
			<div class="totalNum"><%--${searchResult.pageIndex}/ ${searchResult.pageCount} <ikep4j:message pre='${preSearch}' key='page' />--%> <ikep4j:message pre='${preSearch}' key='pageCount.info' /><span> ${searchResult.recordCount}</span></div>
		</div>
		
		
	
		<spring:bind path="searchCondition.viewMode">
		<div class="listView_1">
			<div class="none"><ikep4j:message pre='${preCommon}' key='${status.expression}' post="webstandard"/></div>
			<ul> 
				<c:choose>
				    <c:when test="${fn:contains(status.value, 'LIST')}">          
				       <li><a name="viewModeTypeButton" onclick="changeViewMode('LIST');" href="#a"><img src="<c:url value='/base/images/icon/ic_view_list_on.gif' />" alt="" title="<ikep4j:message pre='${preCommon}' key='viewMode.list.on' />" /></a></li>
				    </c:when>
				    <c:otherwise>
				      <li><a name="viewModeTypeButton" onclick="changeViewMode('LIST');" href="#a"><img src="<c:url value='/base/images/icon/ic_view_list.gif' />" alt="" title="<ikep4j:message pre='${preCommon}' key='viewMode.list' />" /></a></li>
				    </c:otherwise> 
				</c:choose> 
				<c:choose>
				    <c:when test="${fn:contains(status.value, 'SUMMARY') || empty status.value}">
				       <li><a name="viewModeTypeButton" onclick="changeViewMode('SUMMARY');" href="#a"><img src="<c:url value='/base/images/icon/ic_view_summary_on.gif' />" alt="" title="<ikep4j:message pre='${preCommon}' key='viewMode.summary.on' />" /></a></li>
				    </c:when>
				    <c:otherwise>
				       <li><a name="viewModeTypeButton" onclick="changeViewMode('SUMMARY');" href="#a"><img src="<c:url value='/base/images/icon/ic_view_summary.gif' />" alt="" title="<ikep4j:message pre='${preCommon}' key='viewMode.summary' />" /></a></li>
				    </c:otherwise> 
				</c:choose> 
			</ul> 
		</div>		
		</spring:bind>									
	
		<div class="tableSearch">
		
			<c:if test="${workspaceType.isOrganization==0}">
			<select id="categoryId" name="categoryId" title='<ikep4j:message pre='${preSearch}' key='selectCategory' />'>
				<c:forEach var="countWorkspaceTypeList" items="${countWorkspaceTypeList}" varStatus="status">
				<c:choose>
				<c:when test="${status.index==0}"> 
					<option value="" > <ikep4j:message pre='${preSearch}' key='searchAll'/> </option>
				</c:when>
				<c:otherwise>
					<option value="${countWorkspaceTypeList.categoryId}" <c:if test="${countWorkspaceTypeList.categoryId==searchCondition.categoryId }">selected="selected"</c:if> >
					<c:choose>
					<c:when test="${user.localeCode == portal.defaultLocaleCode}">
						${countWorkspaceTypeList.categoryName}
					</c:when>
					<c:otherwise>
						${countWorkspaceTypeList.categoryEnglishName}
					</c:otherwise>
					</c:choose>						
					</option>
				</c:otherwise>
				</c:choose>	
				</c:forEach>	
			</select>
			</c:if>
			
			<c:if test="${workspaceType.isOrganization==1}">
			<select id="groupId" name="groupId" title='<ikep4j:message pre='${preSearch}' key='selectCategory' />'>
				<c:forEach var="countGroupList" items="${countGroupList}" varStatus="status">
				<c:choose>
				<c:when test="${status.index==0}"> 
					<option value="" > <ikep4j:message pre='${preSearch}' key='searchAll'/> </option>
				</c:when>
				<c:otherwise>				
				<option value="${countGroupList.groupId}" <c:if test="${countGroupList.groupId==searchCondition.groupId }">selected="selected"</c:if> >
					<c:choose>
					<c:when test="${user.localeCode == portal.defaultLocaleCode}">
						${countGroupList.groupName} 
					</c:when>
					<c:otherwise>
						${countGroupList.groupEnglishName} 
					</c:otherwise>
					</c:choose>					
				</option>
				</c:otherwise>
				</c:choose>					
				</c:forEach>	
			</select>
			</c:if>		

			<spring:bind path="searchCondition.searchColumn">  
				<select name="${status.expression}" title='<ikep4j:message pre='${preSearch}' key='${status.expression}' />'>
					<option value="WORKSPACE_NAME" <c:if test="${'WORKSPACE_NAME' eq status.value}">selected="selected"</c:if> ><ikep4j:message pre='${preSearch}' key='${status.expression}' post='workspaceName'/></option>
					<option value="REGISTER_NAME" <c:if test="${'REGISTER_NAME' eq status.value}">selected="selected"</c:if> ><ikep4j:message pre='${preSearch}' key='${status.expression}' post='registerName'/></option>
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
	
	<c:if test="${searchCondition.viewMode=='LIST'}">
	<table summary="<ikep4j:message pre="${preList}" key="summary" />">   
		<caption></caption>
		<col style="width: 5%;"/>
		<col style="width: 15%;"/>
		<col style="width: 50%;"/>
		<col style="width: 10%;"/>
		<col style="width: 10%;"/>
		<col style="width: 10%;"/>
		<thead>
			<tr>
				<th scope="col"><input id="checkboxAll" class="checkbox" title="checkbox" type="checkbox" value="" /></th> 
				<c:choose>
				<c:when test="${workspaceType.isOrganization==0}">
				<th scope="col">
					<a onclick="sort('categoryName', '<c:if test="${searchCondition.sortColumn eq 'categoryName'}">${searchCondition.sortType}</c:if>');"  href="#a">
						<ikep4j:message pre='${preList}' key='categoryName' />
					</a>
				</th>
				</c:when>
				<c:otherwise>
				<th scope="col">
					<a onclick="sort('groupName', '<c:if test="${searchCondition.sortColumn eq 'groupName'}">${searchCondition.sortType}</c:if>');"  href="#a">
						<ikep4j:message pre='${preList}' key='groupName' />
					</a>
				</th>
				</c:otherwise>
				</c:choose>

				<th scope="col">
					<a onclick="sort('workspaceName', '<c:if test="${searchCondition.sortColumn eq 'workspaceName'}">${searchCondition.sortType}</c:if>');"  href="#a">
						<ikep4j:message pre='${preList}' key='workspaceName' />
					</a>
				</th>

								
				<th scope="col">
					<a onclick="sort('memberCount', '<c:if test="${searchCondition.sortColumn eq 'memberCount'}">${searchCondition.sortType}</c:if>');"  href="#a">
						<ikep4j:message pre='${preList}' key='memberCount' />
					</a>
				</th>

				<th scope="col">
					<a onclick="sort('sysopName', '<c:if test="${searchCondition.sortColumn eq 'sysopName'}">${searchCondition.sortType}</c:if>');"  href="#a">
						<ikep4j:message pre='${preList}' key='sysopName' />
					</a>
				</th>	
				
				<th scope="col">
					<a onclick="sort('openDate', '<c:if test="${searchCondition.sortColumn eq 'openDate'}">${searchCondition.sortType}</c:if>');"  href="#a">
						<ikep4j:message pre='${preList}' key='openDate' />
					</a>
				</th>
		
			</tr>
		</thead> 
		<tbody>
			<c:choose>
			    <c:when test="${searchResult.emptyRecord}">
					<tr>
						<td colspan="6" class="emptyRecord"><ikep4j:message pre='${preSearch}' key='emptyRecord0' /> ${typeName} <ikep4j:message pre='${preSearch}' key='emptyRecord1' /></td> 
					</tr>				        
			    </c:when>
			    <c:otherwise>
					<c:forEach var="workspaceList" items="${searchResult.entity}">
					<tr>
						<td><input id="workspaceIds" name="workspaceIds" class="checkbox" title="checkbox" type="checkbox" value="${workspaceList.workspaceId}" /></td>
						<c:choose>
						<c:when test="${workspaceType.isOrganization==0}">
						<td>
							<c:choose>
							<c:when test="${user.localeCode == portal.defaultLocaleCode}">
								${workspaceList.categoryName}
							</c:when>
							<c:otherwise>
								${workspaceList.categoryEnglishName}
							</c:otherwise>
							</c:choose>						
						</td>
						</c:when>
						<c:otherwise>
						<td>
							<c:choose>
							<c:when test="${user.localeCode == portal.defaultLocaleCode}">
								${workspaceList.groupName}
							</c:when>
							<c:otherwise>
								${workspaceList.groupEnglishName}
							</c:otherwise>
							</c:choose>						
						</td>
						</c:otherwise>
						</c:choose>
						<td class="textLeft">
							<a onclick="viewWorkspace('${workspaceList.workspaceId}','${workspaceList.workspaceStatus}');" href="#a" title="${workspaceList.workspaceName}">
							${workspaceList.workspaceName}
							</a>
						</td>
						<td>${workspaceList.memberCount}</td>
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
						<td><ikep4j:timezone pattern="yyyy.MM.dd" date="${workspaceList.openDate}"/></td>					
					</tr>
					</c:forEach>				      
			    </c:otherwise> 
			</c:choose>  
		</tbody>
	</table>
	</c:if>
	
	
	<c:if test="${searchCondition.viewMode!='LIST'}">
	<div class="corporateView TeamColl_summaryView">
	<table summary="<ikep4j:message pre="${preList}" key="summary" />">
		<caption></caption>						
		<tbody>
			<c:choose>
			    <c:when test="${searchResult.emptyRecord}">
					<tr>
						<td class="emptyRecord"><ikep4j:message pre='${preSearch}' key='emptyRecord0' /> ${typeName} <ikep4j:message pre='${preSearch}' key='emptyRecord1' /></td> 
					</tr>				        
			    </c:when>
			    <c:otherwise>
					<c:forEach var="workspaceList" items="${searchResult.entity}">		
					<tr>
						<td>
							<div class="TeamColl_summaryViewTitle">
								<span><a onclick="viewWorkspace('${workspaceList.workspaceId}','${workspaceList.workspaceStatus}');" href="#a" title="${workspaceList.workspaceName}">${workspaceList.workspaceName}</a></span>
							</div>
						
							<div class="corporateViewInfo">
								<span class="corporateViewInfo_name"><ikep4j:message pre='${preList}' key='sysopName' /> : 
								<a href="#a"  onclick="iKEP.showUserContextMenu(this, '${workspaceList.sysopId}', 'bottom');iKEP.iFrameContentResize(); return false;">
								<c:choose>
								<c:when test="${user.localeCode == portal.defaultLocaleCode}">
									${workspaceList.sysopName}
								</c:when>
								<c:otherwise>
									${workspaceList.sysopEnglishName}
								</c:otherwise>
								</c:choose>
								</a></span>
								<span><ikep4j:message pre='${preList}' key='openDate' /> : <ikep4j:timezone pattern="yyyy.MM.dd" date="${workspaceList.openDate}"/></span>
								<span><ikep4j:message pre='${preList}' key='memberCount' /> : <strong>${workspaceList.memberCount}</strong></span>
							</div>
							<div class="corporateViewCon">${workspaceList.description}</div>
							<c:if test="${not empty workspaceList.tagList}">
							<div class="corporateViewTag">
								<span class="ic_tag"><span><ikep4j:message pre='${preList}' key='tag' /></span></span>
								<!--tagList--> 
								<c:forEach var="tag" items="${workspaceList.tagList}" varStatus="tagLoop">
								<c:if test="${tagLoop.count != 1}">, </c:if>
								<a href="#a" onclick="iKEP.tagging.tagSearchByName('${tag.tagName}', '${tag.tagItemType}', '${tag.tagItemSubType}');return false;">${tag.tagName}</a>
								</c:forEach>
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
	</c:if>
	
	<!--Page Numbur Start--> 
	<spring:bind path="searchCondition.pageIndex">
	<ikep4j:pagination searchButtonId="searchButton" pageIndexInput="${status.expression}" searchCondition="${searchCondition}" />
	<input name="${status.expression}" type="hidden" value="${status.value}" title="<ikep4j:message pre='${preSearch}' key='${status.expression}'/>" />
	</spring:bind> 
	<!--//Page Numbur End--> 
	
</div>
</form>
<!--//blockListTable End--> 
