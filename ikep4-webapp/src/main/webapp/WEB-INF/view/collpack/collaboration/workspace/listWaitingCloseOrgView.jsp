<%--
=====================================================
	* 기능설명	:	팀 폐쇄 관리 목록
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
<c:set var="preList"    value="message.collpack.collaboration.workspace.listOrgWorkspaceWaiting.list" />
<c:set var="preButton"  value="message.collpack.collaboration.workspace.listOrgWorkspaceWaiting.button" />
<c:set var="preScript"  value="message.collpack.collaboration.workspace.listOrgWorkspaceWaiting.script" />
<%-- 메시지 관련 Prefix 선언 End --%>

<%-- 세션정보 선언 Start --%> 
<c:set var="portal" value="${sessionScope['ikep.portal']}" />
<c:set var="user" value="${sessionScope['ikep.user']}" />
<%-- 세션정보 선언 End --%>


<script type="text/javascript">
<!-- 
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
			$jq("input[name=listUrl]").val("listMyAppWorkspaceView.do?listType=${searchCondition.listType}");
			$jq("input[name=workspaceId]").val(workspaceId);
			$jq("#mainForm").attr({method:'get'}).submit(); 			
		}

		
		return false;

	}; 

	// onload시 수행할 코드
	$(document).ready(function() { 
		
		iKEP.iFrameContentResize();  
		
		//$jq('tbody tr:odd').addClass('bgGray');
		//$jq('tbody tr:even').addClass('bgWhite');
		
		$("#searchButton").click(function() {   
			$jq("#mainForm").attr({method:'get'}).submit();

			return false; 
		});
		
		$("#checkboxAll").click(function() { 
			$("input[name=workspaceIds]").attr("checked", $(this).is(":checked"));  
		}); 
		
		$jq("select[name=pagePerRecord]").change(function() {
			//$jq("input[name=pageIndex]").val('1');
			$jq("#searchButton").click(); 
		});
		
		<c:if test="${searchCondition.listType=='WaitingOpenOrg' }">	
		$("#divTab1").tabs({selected : 0});
		</c:if>
		
		<c:if test="${searchCondition.listType!='WaitingOpenOrg' }">	
		$("#divTab1").tabs({selected : 1});
		</c:if>
		
		$jq("#tabs-O").click(function() { 
			location.href='<c:url value="/collpack/collaboration/workspace/listWaitingOpenOrgView.do"/>?listType=WaitingOpenOrg';
		}); 
		$jq("#tabs-C").click(function() { 
			location.href='<c:url value="/collpack/collaboration/workspace/listWaitingCloseOrgView.do"/>?listType=WaitingCloseOrg';
		}); 
		
		
		//
		$jq('#sendMaildBtn').click(function(event) { 
			//WS Sysop list
			var countCheckBox	=	$jq("input[name=workspaceIds]:checkbox:checked").length;
			var nameList = [];
			var emailList =  [];
			if(countCheckBox>0)
			{
				
				$jq.ajax({
					url : '<c:url value="/collpack/collaboration/member/listSysop.do"/>?'+$jq("#mainForm").serialize(),
					type : "get",
					success : function(data) {	
						//iKEP.debug(data)
						
						for(var i=0 ; i<data.length ; i++) {
							nameList.push(data[i].memberName);
							emailList.push(data[i].mailId);
						}

						if(data.length>0){
							var title = "<ikep4j:message pre='${preList}' key='mailTitle' />" ;//$jq('#titleDiv').html();
							var content ="<ikep4j:message pre='${preList}' key='mailContent' />";// $jq('#contentDiv').html();
							var fileIdList =[];
							var fileNameList = [];

							iKEP.sendMailPop(nameList, emailList, title, content, fileIdList, fileNameList);
						}else{
							alert('<ikep4j:message pre='${preScript}' key='noUser' />');
							return false;
						}				
						
						
					}
				});		
			}
			else
			{	
				alert('<ikep4j:message pre='${preScript}' key='checkWorkspace' />');
				return false;
			}

		});

		//ORG WORKSPACE 폐쇄
		$jq("#updateWorkspaceCloseButton").click(function() {
			
			var countCheckBox	=	$("input[name=workspaceIds]:checkbox:checked").length;
			
			if(countCheckBox>0)
			{
				if( confirm('<ikep4j:message pre='${preScript}' key='closeWorkspace' />') ){

					$jq.ajax({
						url : '<c:url value="/collpack/collaboration/workspace/updateOrgWorkspaceCloseStatus.do" />?'+$jq("#mainForm").serialize(),
						type : "get",
						success : function(result) {
							$jq("#searchButton").click(); 
						}
					});
				}
				
			}
			else
			{
				alert('<ikep4j:message pre='${preScript}' key='checkboxClose' />');
			}
			return false; 		
			
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
	<c:if test="${searchCondition.listType=='WaitingOpenOrg'}">
	<ikep4j:message pre="${preHeader}" key="pageMessage" />
	</c:if>
	<c:if test="${searchCondition.listType!='WaitingOpenOrg'}">
	<ikep4j:message pre="${preHeader}" key="pageMessage2" />
	</c:if>
	<div class="l_t_corner"></div>
	<div class="r_t_corner"></div>
	<div class="l_b_corner"></div>
	<div class="r_b_corner"></div>					
</div>

<!--tab Start-->		
<div id="divTab1" class="iKEP_tab">
	<ul>
		<li><a id="tabs-O" href="#tabs-1"><ikep4j:message pre="${preHeader}" key="openManagement" /></a></li>
		<li><a id="tabs-C" href="#tabs-2"><ikep4j:message pre="${preHeader}" key="closeManagement" /></a></li>
</ul>
<div id="tabs-1" style="padding: 0px;"></div>
<div id="tabs-2" style="padding: 0px;"></div>
</div>		
<!--//tab End-->


<!--blockListTable Start-->
<form id="mainForm" name="mainForm" method="get" action="<c:url value="/collpack/collaboration/workspace/listWaitingCloseOrgView.do"/>">
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
					<option value="workspaceName" <c:if test="${'workspaceName' eq status.value}">selected="selected"</c:if>><ikep4j:message pre='${preSearch}' key='${status.expression}' post='workspaceName'/></option>
					<option value="sysopName" <c:if test="${'sysopName' eq status.value}">selected="selected"</c:if>><ikep4j:message pre='${preSearch}' key='${status.expression}' post='sysopName'/></option>
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
	
	<table summary="<ikep4j:message pre="${preList}" key="summaryClose" />">   
		<caption></caption>
		<col style="width: 5%;"/>
		<col style="width: 10%;"/>
		<col style="width: 25%;"/>
		<col style="width: 20%;"/>
		<col style="width: 10%;"/>
		<col style="width: 10%;"/>
		<col style="width: 10%;"/>
		<col style="width: 10%;"/>
		<thead>
			<tr>
				<th scope="col"><input id="checkboxAll" class="checkbox" title="checkbox" type="checkbox" value="" /></th> 
				<th scope="col">
					<a onclick="sort('typeName', '<c:if test="${searchCondition.sortColumn eq 'typeName'}">${searchCondition.sortType}</c:if>');"  href="#a">
						<ikep4j:message pre='${preList}' key='typeName' />
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
					<a onclick="sort('groupId', '<c:if test="${searchCondition.sortColumn eq 'groupId'}">${searchCondition.sortType}</c:if>');"  href="#a">
						<ikep4j:message pre='${preList}' key='groupId' />
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
						<td class="textLeft"><a onclick="viewWorkspace('${workspaceList.workspaceId}','${workspaceList.workspaceStatus}');" href="#a" title="${workspaceList.workspaceName}">
							${workspaceList.workspaceName}
							</a></td>
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
						<td>${workspaceList.teamId}</td>
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
						<td>
							<c:choose>
					    		<c:when test="${workspaceList.workspaceStatus=='O'}"> 
									<ikep4j:message pre='${preList}' key='O' />
								</c:when>
					    		<c:when test="${workspaceList.workspaceStatus=='WO'}"> 
									<ikep4j:message pre='${preList}' key='WO' />
								</c:when>
								<c:when test="${workspaceList.workspaceStatus=='WC'}"> 
									<ikep4j:message pre='${preList}' key='WC' />
								</c:when>
					    		<c:when test="${workspaceList.workspaceStatus=='C'}"> 
									<ikep4j:message pre='${preList}' key='C' />
								</c:when>
								<c:when test="${workspaceList.workspaceStatus=='WR'}"> 
									<ikep4j:message pre='${preList}' key='WR' />
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
	<li><a id="sendMaildBtn" class="button" href="#a" title="<ikep4j:message pre='${preButton}' key='encourageCloseMail' />"><span><ikep4j:message pre='${preButton}' key='encourageCloseMail' /></span></a></li>
	<li><a id="updateWorkspaceCloseButton"   class="button" href="#a" title="<ikep4j:message pre='${preButton}' key='close'  />"><span><ikep4j:message pre='${preButton}' key='close'  /></span></a></li>
</ul>
</div>
<!--//blockButton End-->
