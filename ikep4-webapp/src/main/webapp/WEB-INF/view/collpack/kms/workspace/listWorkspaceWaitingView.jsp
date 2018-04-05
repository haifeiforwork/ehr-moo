<%--
=====================================================
	* 기능설명	:	개설관리목록
	* 작성자		:	김종철
=====================================================
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%-- Tab Lib Start --%> 
<%@ include file="/base/common/taglibs.jsp"%>
<%-- Tab Lib End --%> 

<%-- 메시지 관련 Prefix 선언 Start --%> 
<c:set var="preHeader"  value="message.collpack.collaboration.workspace.listWorkspaceWaiting.header" />
<c:set var="preSearch"  value="message.collpack.collaboration.workspace.listWorkspaceWaiting.search" />
<c:set var="preCommon"  value="message.collpack.common" />
<c:set var="preList"    value="message.collpack.collaboration.workspace.listWorkspaceWaiting.list" />
<c:set var="preButton"  value="message.collpack.collaboration.workspace.listWorkspaceWaiting.button" />
<c:set var="preScript"  value="message.collpack.collaboration.workspace.listWorkspaceWaiting.script" />
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
	
	searchStatus = function(status){
		var countCheckBox	=	$jq("input[name=openStatus]:checkbox:checked").length;

		if(countCheckBox==1)
		{
			$jq("input[name=workspaceStatus]").val(status);
			$jq("input[name=pageIndex]").val('1');
			$jq("#searchButton").click();
		}
		else
		{
			$jq("input[name=pageIndex]").val('1');
			$jq("#searchButton").click();
		}
	};

	// Collaboration 조회
	viewWorkspace = function(workspaceId,status) {
		if(status=="O" || status=="WC")
		{
			parent.goWorkspace(workspaceId);
			/**$jq('form[name=mainForm]').attr({
				action:"<c:url value="/collpack/collaboration/main/Workspace.do"/>",
				method:"GET"
			});**/				
		}
		else
		{
			$jq('form[name=mainForm]').attr({
				action:"<c:url value="/collpack/collaboration/workspace/readWorkspaceView.do"/>"
			});
			$jq("input[name=listUrl]").val("listWorkspaceWaitingView.do?listType=${searchCondition.listType}");
			$jq("input[name=workspaceId]").val(workspaceId);
			$jq("#mainForm").attr({method:'get'}).submit(); 			
		}

		
		return false;

	}; 
	
	//개설승인,개설반려
	updateStatus	=	function(status) {
		
		var countCheckBox	=	$("input[name=workspaceIds]:checkbox:checked").length;
		var listType		=	$("input[name=listType]").val();
		if(countCheckBox>0)
		{
			if(listType=='WaitingOpen' && status=='O')
			{
				if(!confirm('<ikep4j:message pre='${preScript}' key='openMessage' />'))
				{
					return false; 	
				}
			}
			else if(listType=='WaitingOpen' && status=='WR')
			{
				if(!confirm('<ikep4j:message pre='${preScript}' key='openRejectMessage' />'))
				{
					return false; 	
				}
			}
			else if(listType!='WaitingOpen' && status=='C')
			{
				if(!confirm('<ikep4j:message pre='${preScript}' key='closeMessage' />'))
				{
					return false; 		
				}
			}
			else if(listType!='WaitingOpen' && status=='O')
			{
				if(!confirm('<ikep4j:message pre='${preScript}' key='closeRejectMessage' />'))
				{
					return false; 		
				}
			}
				
			$jq("input[name=workspaceStatus]").val(status);
			
			$jq.ajax({
				url : '<c:url value='/collpack/collaboration/workspace/updateWorkspaceStatusAjax.do' />?'+$jq("#mainForm").serialize(),
				type : "get",
				success : function(result) {
					$jq("#searchButton").click(); 
				},
				error : function(event, request, settings){
			 		alert("<ikep4j:message pre='${preScript}' key='sortError' />");
				}
			});	
		}
		else
		{
			if(listType=='WaitingOpen')
				alert('<ikep4j:message pre='${preScript}' key='checkboxOpen' />');
			else if(listType!='WaitingOpen')
				alert('<ikep4j:message pre='${preScript}' key='checkboxClose' />');
		}
		return false; 		
		
	};

	// onload시 수행할 코드
	$jq(document).ready(function() { 
	
		<c:if test="${searchCondition.listType=='WaitingOpen' }">	
		$("#divTab1").tabs({selected : 0});
		</c:if>
		
		<c:if test="${searchCondition.listType!='WaitingOpen' }">	
		$("#divTab1").tabs({selected : 1});
		</c:if>
		
		$jq("#tabs-O").click(function() { 
			location.href='<c:url value="/collpack/collaboration/workspace/listWorkspaceWaitingView.do"/>?listType=WaitingOpen';
		}); 
		$jq("#tabs-C").click(function() { 
			location.href='<c:url value="/collpack/collaboration/workspace/listWorkspaceWaitingView.do"/>?listType=WaitingClose';
		}); 
		//alert("###########111"  );
		$jq("#searchButton").click(function() {
			$jq("#mainForm").submit();
			
			return false; 
		});

		$jq("select[name=pagePerRecord]").change(function() {
			$jq("input[name=pageIndex]").val('1');
			$jq("#searchButton").click(); 
		});
		
		$jq("#checkboxAll").click(function() { 
			$jq("input[name=workspaceIds]").attr("checked", $jq(this).is(":checked"));  
		});
		
		iKEP.iFrameContentResize();  
	   
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


<!--tab Start-->
<div id="divTab1" class="iKEP_tab">     
	<ul>
		<li><a id="tabs-O" href="#tabs-1"><ikep4j:message pre="${preHeader}" key="openManagement" /></a></li>
		<li><a id="tabs-C" href="#tabs-2"><ikep4j:message pre="${preHeader}" key="closeManagement" /></a></li>
	</ul>
	<div id="tabs-1" style="padding: 0px;"></div>
	<div id="tabs-2" style="padding: 0px;"></div>
</div>

<!--blockListTable Start-->
<form id="mainForm" name="mainForm" method="get" action="<c:url value="/collpack/collaboration/workspace/listWorkspaceWaitingView.do"/>">  
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
		
		<%--div class="tablefunction">
		<c:if test="${searchCondition.listType=='WaitingOpen' }">
			<input onclick="searchStatus('WO')" name="openStatus" type="checkbox" class="checkbox" title="<ikep4j:message pre='${preCommon}' key='code.WC' />" /><ikep4j:message pre='${preCommon}' key='code.WC' /> 
			<input onclick="searchStatus('WR')" name="openStatus" type="checkbox" class="checkbox" title="<ikep4j:message pre='${preCommon}' key='code.WR' />" /><ikep4j:message pre='${preCommon}' key='code.WR' /> 	
		</c:if>	
		</div--%>	

		<div class="tableSearch"> 
			<spring:bind path="searchCondition.searchColumn">  
				<select name="${status.expression}" title='<ikep4j:message pre='${preSearch}' key='${status.expression}' />'>
					<option value="WORKSPACE_NAME" <c:if test="${'WORKSPACE_NAME' eq status.value}">selected="selected"</c:if> ><ikep4j:message pre='${preSearch}' key='${status.expression}' post='workspaceName'/></option>
					<option value="UPDATER_NAME" <c:if test="${'UPDATER_NAME' eq status.value}">selected="selected"</c:if> ><ikep4j:message pre='${preSearch}' key='${status.expression}' post='updaterName'/></option>
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
	
<c:if test="${searchCondition.listType=='WaitingOpen' }">			
	<table summary="<ikep4j:message pre="${preList}" key="summaryOpen" />">   
		<caption></caption>
		<col style="width: 5%;"/>
		<col style="width: 7%;"/>
		<col style="width: 20%;"/>
		<col style="width: 10%;"/>
		<col style="width: 35%;"/>
		<col style="width: 7%;"/>
		<col style="width: 9%;"/>
		<col style="width: 7%;"/>
		<thead>
			<tr>
				<th scope="col"><input id="checkboxAll" class="checkbox" title="checkbox" type="checkbox" value="" /></th> 
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
					<a onclick="sort('groupName', '<c:if test="${searchCondition.sortColumn eq 'groupName'}">${searchCondition.sortType}</c:if>');"  href="#a">
						<ikep4j:message pre='${preList}' key='groupName' />
					</a>
				</th>
				
				<th scope="col">
					<a onclick="sort('description', '<c:if test="${searchCondition.sortColumn eq 'description'}">${searchCondition.sortType}</c:if>');"  href="#a">
						<ikep4j:message pre='${preList}' key='description' />
					</a>
				</th>
				<th scope="col">
					<a onclick="sort('registerName', '<c:if test="${searchCondition.sortColumn eq 'registerName'}">${searchCondition.sortType}</c:if>');"  href="#a">
						<ikep4j:message pre='${preList}' key='registerName' />
					</a>
				</th>	
				
				<th scope="col">
					<a onclick="sort('registDate', '<c:if test="${searchCondition.sortColumn eq 'registDate'}">${searchCondition.sortType}</c:if>');"  href="#a">
						<ikep4j:message pre='${preList}' key='registDate' />
					</a>
				</th>
				
				<th scope="col">
					<a onclick="sort('workspaceStatus', '<c:if test="${searchCondition.sortColumn eq 'workspaceStatus'}">${searchCondition.sortType}</c:if>');"  href="#a">
						<ikep4j:message pre='${preList}' key='workspaceStatus' />
					</a>
				</th>				
			</tr>
		</thead> 
		<tbody>
			<c:choose>
			    <c:when test="${searchResult.emptyRecord}">
					<tr>
						<td colspan="8" class="emptyRecord"><ikep4j:message pre='${preSearch}' key='emptyRecord' /></td> 
					</tr>				        
			    </c:when>
			    <c:otherwise>
					<c:forEach var="workspaceList" items="${searchResult.entity}">
					<tr>
						<td><input id="workspaceIds" name="workspaceIds" class="checkbox" title="checkbox" type="checkbox" value="${workspaceList.workspaceId}" /></td>
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
							<div class="ellipsis">${workspaceList.workspaceName}</div>
							</a>
						</td>
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
						<td class="textLeft" title="${workspaceList.description}"><div class="ellipsis">${workspaceList.description}</div></td>
						<td>
							<a href="#a"  onclick="iKEP.showUserContextMenu(this, '${workspaceList.registerId}', 'bottom');iKEP.iFrameContentResize(); return false;">
							<c:choose>
							<c:when test="${user.localeCode == portal.defaultLocaleCode}">
								${workspaceList.registerName}
							</c:when>
							<c:otherwise>
								${workspaceList.registerEnglishName}
							</c:otherwise>
							</c:choose>	
							</a>		
						</td>
						<td><ikep4j:timezone pattern="yyyy.MM.dd" date="${workspaceList.registDate}"/></td>
						<td>
							<c:choose>
					    		<c:when test="${workspaceList.workspaceStatus=='O'}"> 
									<ikep4j:message pre='${preCommon}' key='workspace.O' />
								</c:when>
					    		<c:when test="${workspaceList.workspaceStatus=='WO'}"> 
									<ikep4j:message pre='${preCommon}' key='workspace.WO' />
								</c:when>
								<c:when test="${workspaceList.workspaceStatus=='WC'}"> 
									<ikep4j:message pre='${preCommon}' key='workspace.WC' />
								</c:when>
					    		<c:when test="${workspaceList.workspaceStatus=='C'}"> 
									<ikep4j:message pre='${preCommon}' key='workspace.C' />
								</c:when>
								<c:when test="${workspaceList.workspaceStatus=='WR'}"> 
									<ikep4j:message pre='${preCommon}' key='workspace.WR' />
								</c:when>
								<c:otherwise>
								</c:otherwise>
							</c:choose>								
						</td>						
					</tr>
					</c:forEach>				      
			    </c:otherwise> 
			</c:choose>  
		</tbody>
	</table>
</c:if>	
<c:if test="${searchCondition.listType!='WaitingOpen' }">			
	<table summary="<ikep4j:message pre="${preList}" key="summaryClose" />">   
		<caption></caption>
		<col style="width: 5%;"/>
		<col style="width: 7%;"/>
		<col style="width: 20%;"/>
		<col style="width: 10%;"/>
		<col style="width: 35%;"/>
		<col style="width: 7%;"/>
		<col style="width: 9%;"/>
		<col style="width: 7%;"/>
		<thead>
			<tr>
				<th scope="col"><input id="checkboxAll" class="checkbox" title="checkbox" type="checkbox" value="" /></th> 
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
					<a onclick="sort('groupName', '<c:if test="${searchCondition.sortColumn eq 'groupName'}">${searchCondition.sortType}</c:if>');"  href="#a">
						<ikep4j:message pre='${preList}' key='groupName' />
					</a>
				</th>
				
				<th scope="col">
					<a onclick="sort('description', '<c:if test="${searchCondition.sortColumn eq 'description'}">${searchCondition.sortType}</c:if>');"  href="#a">
						<ikep4j:message pre='${preList}' key='description' />
					</a>
				</th>
				<th scope="col">
					<a onclick="sort('updaterName', '<c:if test="${searchCondition.sortColumn eq 'updaterName'}">${searchCondition.sortType}</c:if>');"  href="#a">
						<ikep4j:message pre='${preList}' key='updaterName' />
					</a>
				</th>	
				
				<th scope="col">
					<a onclick="sort('updateDate', '<c:if test="${searchCondition.sortColumn eq 'updateDate'}">${searchCondition.sortType}</c:if>');"  href="#a">
						<ikep4j:message pre='${preList}' key='updateDate' />
					</a>
				</th>
				
				<th scope="col">
					<a onclick="sort('workspaceStatus', '<c:if test="${searchCondition.sortColumn eq 'workspaceStatus'}">${searchCondition.sortType}</c:if>');"  href="#a">
						<ikep4j:message pre='${preList}' key='workspaceStatus' />
					</a>
				</th>				
			</tr>
		</thead> 
		<tbody>
			<c:choose>
			    <c:when test="${searchResult.emptyRecord}">
					<tr>
						<td colspan="8" class="emptyRecord"><ikep4j:message pre='${preSearch}' key='emptyRecord' /></td> 
					</tr>				        
			    </c:when>
			    <c:otherwise>
					<c:forEach var="workspaceList" items="${searchResult.entity}">
					<tr>
						<td><input id="workspaceIds" name="workspaceIds" class="checkbox" title="checkbox" type="checkbox" value="${workspaceList.workspaceId}" /></td>
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
							<div class="ellipsis">${workspaceList.workspaceName}</div>
							</a>
						</td>
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
						<td class="textLeft" title="${workspaceList.description}"><div class="ellipsis">${workspaceList.description}</div></td>
						<td>
							<a href="#a"  onclick="iKEP.showUserContextMenu(this, '${workspaceList.updaterId}', 'bottom');iKEP.iFrameContentResize(); return false;">
							<c:choose>
							<c:when test="${user.localeCode == portal.defaultLocaleCode}">
								${workspaceList.updaterName}
							</c:when>
							<c:otherwise>
								${workspaceList.updaterEnglishName}
							</c:otherwise>
							</c:choose>	
							</a>
						</td>
						<td><ikep4j:timezone pattern="yyyy.MM.dd" date="${workspaceList.updateDate}"/></td>
						<td>
							<c:choose>
					    		<c:when test="${workspaceList.workspaceStatus=='O'}"> 
									<ikep4j:message pre='${preCommon}' key='workspace.O' />
								</c:when>
					    		<c:when test="${workspaceList.workspaceStatus=='WO'}"> 
									<ikep4j:message pre='${preCommon}' key='workspace.WO' />
								</c:when>
								<c:when test="${workspaceList.workspaceStatus=='WC'}"> 
									<ikep4j:message pre='${preCommon}' key='workspace.WC' />
								</c:when>
					    		<c:when test="${workspaceList.workspaceStatus=='C'}"> 
									<ikep4j:message pre='${preCommon}' key='workspace.C' />
								</c:when>
								<c:when test="${workspaceList.workspaceStatus=='WR'}"> 
									<ikep4j:message pre='${preCommon}' key='workspace.WR' />
								</c:when>
								<c:otherwise>
								</c:otherwise>
							</c:choose>									
						</td>						
					</tr>
					</c:forEach>				      
			    </c:otherwise> 
			</c:choose>  
		</tbody>
	</table>
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
	 
<!--blockButton Start-->
<div class="blockButton">
<ul>
<c:if test="${searchCondition.listType=='WaitingOpen' }">	
	<li><a id="updateWorkspaceApprovedButton" class="button" href="#a" onclick="updateStatus('O')" title="<ikep4j:message pre='${preButton}' key='approveOpen'/>"><span><ikep4j:message pre='${preButton}' key='approveOpen'/></span></a></li>
	<li><a id="updateWorkspaceRejectButton"   class="button" href="#a" onclick="updateStatus('WR')" title="<ikep4j:message pre='${preButton}' key='rejectOpen'/>"><span><ikep4j:message pre='${preButton}' key='rejectOpen'/></span></a></li>
</c:if>
<c:if test="${searchCondition.listType!='WaitingOpen' }">	
	<li><a id="updateWorkspaceApprovedButton" class="button" href="#a" onclick="updateStatus('C')" title="<ikep4j:message pre='${preButton}' key='approveClose' />"><span><ikep4j:message pre='${preButton}' key='approveClose' /></span></a></li>
	<li><a id="updateWorkspaceRejectButton"   class="button" href="#a" onclick="updateStatus('O')" title="<ikep4j:message pre='${preButton}' key='rejectClose'  />"><span><ikep4j:message pre='${preButton}' key='rejectClose'  /></span></a></li>
</c:if>
</ul>
</div>
<!--//blockButton End-->
		

