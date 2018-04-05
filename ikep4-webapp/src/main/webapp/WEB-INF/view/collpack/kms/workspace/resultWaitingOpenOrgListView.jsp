<%--
=====================================================
	* 기능설명	:	미개설 팀Coll관리목록
	* 작성자		:	
=====================================================
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%-- Tab Lib Start --%> 
<%@ include file="/base/common/taglibs.jsp"%>
<%-- Tab Lib End --%> 

<%-- 메시지 관련 Prefix 선언 Start --%> 
<c:set var="preHeader"  value="message.collpack.collaboration.workspace.listOrgWorkspaceWaiting.header" />
<c:set var="preSearch"  value="message.collpack.collaboration.workspace.listOrgWorkspaceWaiting.search" />
<c:set var="preCode"    value="message.collpack.common.code" />
<c:set var="preList"    value="message.collpack.collaboration.workspace.listOrgWorkspaceWaiting.list" />
<c:set var="preButton"  value="message.collpack.collaboration.workspace.listOrgWorkspaceWaiting.button" />
<c:set var="preScript"  value="message.collpack.collaboration.workspace.listOrgWorkspaceWaiting.script" />
<%-- 메시지 관련 Prefix 선언 End --%>

<script type="text/javascript">

(function($) {

	//정렬 조건 변경 함수
	sort = function(sortColumn, sortType) {
		$("input[name=sortColumn]").val(sortColumn);
		if( sortType == 'ASC') {
			$("input[name=sortType]").val('DESC');	
		} else {
			$("input[name=sortType]").val('ASC');
		}
		$("#searchButton").click();
	};
	
	//search Enter
	search = function() {
		//$jq("input[name=pageIndex]").val('1');
		$jq("#searchButton").click();
	};	
	
	//체크박스 개수 체크
	$jq("#createOrgWorkspaceOpenButton").click(function() {
			
			var countCheckBox	=	$jq("input[name=groupIds]:checkbox:checked").length;

			if(countCheckBox>0&&countCheckBox<=10)
			{
				//최대 체크수 10개로 제한
				$jq.ajax({
					url : '<c:url value="/collpack/collaboration/workspace/createOrgWorkspaceOpen.do" />?'+$jq("#mainForm").serialize(),
					type : "get",
					success : function(result) {
						$("#resultOrgOpenList").load('<c:url value="/collpack/collaboration/workspace/resultWaitingOpenOrgList.do"/>')
						.error(function(event, request, settings) { alert("error"); });
						//$jq("#searchButton1").click(); 
					}
				});		
			}
			else
			{	
				if(countCheckBox<=0){
					alert('<ikep4j:message pre='${preScript}' key='checkboxOpen' />');
				}else{
					//개수 10개 제한 메세지
					alert('<ikep4j:message pre='${preScript}' key='checkboxLimit' />');
				}
				
			}
			return false; 	
	
		});
	
	//onload시 수행할 코드
$jq(document).ready(function() { 
	
	
	iKEP.iFrameContentResize();  
	
	$jq('tbody tr:odd').addClass('bgWhite');
	$jq('tbody tr:even').addClass('bgGray');
	
	$jq("#searchButton").click(function() {
		
		$("#resultOrgOpenList").load('<c:url value="/collpack/collaboration/workspace/resultWaitingOpenOrgList.do"/>', $("#mainForm").serialize())
		.error(function(event, request, settings) { alert("error"); });
		return false;
	});
	$jq("#searchButton1").click(function() {
		//alert('ss');
		$("#resultOrgOpenList").load('<c:url value="/collpack/collaboration/workspace/resultWaitingOpenOrgList.do"/>')
		.error(function(event, request, settings) { alert("error"); });
		return false;
	});	
	$jq("select[name=pagePerRecord]").change(function() {
		//$jq("input[name=pageIndex]").val('1');
		$jq("#searchButton").click(); 
	});
	
	$jq("#checkboxAll").click(function() { 
		$jq("input[name=groupIds]").attr("checked", $jq(this).is(":checked"));  
	});
	$jq('#searchWord').keypress( function(event) {
		$jq("#searchButton").click();			
	});

});
})(jQuery);

</script>
<!--blockListTable Start-->
<form id="mainForm" name="mainForm" method="post" action="<c:url value="/collpack/collaboration/workspace/resultWaitingOpenOrgList.do"/>" onsubmit="return false;">  
<spring:bind path="searchCondition.sortColumn">
<input name="${status.expression}" type="hidden" value="${status.value}" title="<ikep4j:message pre='${preSearch}' key='${status.expression}'/>" />
</spring:bind>
<spring:bind path="searchCondition.sortType">
<input name="${status.expression}" type="hidden" value="${status.value}" title="<ikep4j:message pre='${preSearch}' key='${status.expression}'/>" />
</spring:bind>
<spring:bind path="searchCondition.groupId">
<input name="${status.expression}" type="hidden" value="${status.value}" title="<ikep4j:message pre='${preSearch}' key='${status.expression}'/>" />
</spring:bind> 
<spring:bind path="searchCondition.workspaceStatus">
<input name="${status.expression}" type="hidden" value="${status.value}" title="<ikep4j:message pre='${preSearch}' key='${status.expression}'/>" />
</spring:bind>
<spring:bind path="searchCondition.listType">
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
			<spring:bind path="searchCondition.searchColumn">
				<select name="${status.expression}" title='<ikep4j:message pre='${preSearch}' key='${status.expression}' />'>
					<option value="groupName" <c:if test="${'groupName' eq status.value}">selected="selected"</c:if>><ikep4j:message pre='${preSearch}' key='${status.expression}' post='groupName'/></option>
					<option value="groupLeaderName" <c:if test="${'groupLeaderName' eq status.value}">selected="selected"</c:if>><ikep4j:message pre='${preSearch}' key='${status.expression}' post='groupLeaderName'/></option>
				</select>		
			</spring:bind>		
			<spring:bind path="searchCondition.searchWord">  					
				<input id="${status.expression}" name="${status.expression}" value="${status.value}" type="text" class="inputbox" title='<ikep4j:message pre='${preSearch}' key='${status.expression}' />'  size="20" />
			</spring:bind>
			<a id="searchButton" name="searchButton" href="#a" class="ic_search"><span>Search</span></a>
		</div>
						
		<div class="clear"></div>
	</div>
	<!--//tableTop End-->
	
	<table summary="<ikep4j:message pre="${preList}" key="summaryClose" />">   
		<caption></caption>
		<col style="width: 10%;"/>
		<col style="width: 18%;"/>
		<col style="width: 60%;"/>
		<col style="width: 12%;"/>
		<thead>
			<tr>
				<!-- 전체선택 -->
				<th scope="col"><input id="checkboxAll" class="checkbox" title="checkbox" type="checkbox" value="" /></th> 
				<!-- 부서명 -->
				<th scope="col">
					<a onclick="sort('groupName', '<c:if test="${searchCondition.sortColumn eq 'groupName'}">${searchCondition.sortType}</c:if>');"  href="#a">
						<ikep4j:message pre='${preList}' key='groupName' />
					</a>
				</th>
				<!-- 부서 Hierachy -->
				<th scope="col">
					<a onclick="sort('fullPath', '<c:if test="${searchCondition.sortColumn eq 'fullPath'}">${searchCondition.sortType}</c:if>');"  href="#a">
						<ikep4j:message pre='${preList}' key='fullPath' />
					</a>
				</th>
				<!-- 부서장 이름 -->
				<th scope="col">
					<a onclick="sort('groupLeaderName', '<c:if test="${searchCondition.sortColumn eq 'groupLeaderName'}">${searchCondition.sortType}</c:if>');"  href="#a">
						<ikep4j:message pre='${preList}' key='groupLeaderName' />
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
					<c:forEach var="workspaceList" items="${workspaceList}">
					<tr>
						<td>
						<c:if test="${workspaceList.countWorkspace!='1'}">
						<input id="groupIds" name="groupIds" class="checkbox" title="checkbox" type="checkbox" value="${workspaceList.groupId}"/>
						</c:if>
						</td>
						<td class="textLeft">
								<c:choose>
								<c:when test="${user.localeCode == portal.defaultLocaleCode}">
									${workspaceList.groupName}
								</c:when>
								<c:otherwise>
									${workspaceList.groupEnglishName}
								</c:otherwise>
								</c:choose>	
						</td>
						<td class="textLeft" title="${workspaceList.fullPath}"><div class="ellipsis">${workspaceList.fullPath}</div></td>
						<td>
							<a href="#a"  onclick="iKEP.showUserContextMenu(this, '${workspaceList.groupLeaderId}', 'left');iKEP.iFrameContentResize(); return false;">
								<c:choose>
								<c:when test="${user.localeCode == portal.defaultLocaleCode}">
									${workspaceList.groupLeaderName}
								</c:when>
								<c:otherwise>
									${workspaceList.groupLeaderEnglishName}
								</c:otherwise>
								</c:choose>	
							</a>
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
<%-- 보류 	
	<li><a id="createWorkspaceAllApprovedButton" class="button" href="#a" onclick="javascript:createWorkspaceAll()" title="<ikep4j:message pre='${preButton}' key='allApproveOpen' />"><span><ikep4j:message pre='${preButton}' key='allApproveOpen' /></span></a></li> --%>
	<li><a id="createOrgWorkspaceOpenButton"   class="button" href="#a" title="<ikep4j:message pre='${preButton}' key='open'  />"><span><ikep4j:message pre='${preButton}' key='open'  /></span></a></li>
</ul>
</div>
<!--//blockButton End-->
