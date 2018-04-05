<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%-- Tab Lib Start --%> 
<%@ include file="/base/common/taglibs.jsp"%>
<%-- Tab Lib End --%> 

<%-- 메시지 관련 Prefix 선언 Start --%> 
<c:set var="preHeader"  value="message.collpack.collaboration.workspace.listMyApplication.header" />
<c:set var="preSearch"  value="message.collpack.collaboration.workspace.listMyApplication.search" />
<c:set var="preCommon"  value="message.collpack.common" />
<c:set var="preList"    value="message.collpack.collaboration.workspace.listMyApplication.list" />
<c:set var="preButton"  value="message.collpack.collaboration.workspace.listMyApplication.button" />
<c:set var="preScript"  value="message.collpack.collaboration.workspace.listMyApplication.script" />
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
			$jq('#mainForm').attr({
				action:"<c:url value="/collpack/collaboration/workspace/readWorkspaceView.do"/>"
			});
			$jq("input[name=listUrl]").val("listMyApplicationView.do?listType=${searchCondition.listType}");
			$jq("input[name=workspaceId]").val(workspaceId);
			$jq("#mainForm").attr({method:'get'}).submit(); 			
		}

		
		return false;

	}; 

	// onload시 수행할 코드
	$jq(document).ready(function() { 
		
		iKEP.iFrameContentResize();  
		
		<c:if test="${searchCondition.listType=='listAppMember' }">	
		$("#divTab1").tabs({selected : 0});
		</c:if>
		
		<c:if test="${searchCondition.listType!='listAppMember' }">	
		$("#divTab1").tabs({selected : 1});

		</c:if>
		$jq("#tabs-m").click(function() { 
			location.href='<c:url value="/collpack/collaboration/workspace/listMyApplicationView.do"/>?listType=listAppMember';
		}); 
		$jq("#tabs-w").click(function() { 
			location.href='<c:url value="/collpack/collaboration/workspace/listMyApplicationView.do"/>?listType=listAppWorkspace';
		}); 
	
		//alert("###########111"  );
		$jq("#searchButton").click(function() {
			$jq("#mainForm").attr({method:'post'}).submit();	
	
			return false; 
		});
		
		$jq("select[name=pagePerRecord]").change(function() {
			//서브밋하는 코드 넣으시면 됩니다.
			//ex : $jq("#searchBoardItemButton").click(); 
			$jq("input[name=pageIndex]").val('1');
			$jq("#searchButton").click(); 
			
		});
		
		$jq("#checkboxAll").click(function() { 
			$jq("input[name=workspaceIds]").attr("checked", $jq(this).is(":checked"));  
		}); 
		
		$jq("#deleteMemberButton").click(function() {
			
			var countCheckBox	=	$jq("input[name=workspaceIds]:checkbox:checked").length;

			if(countCheckBox>0)
			{
				/*$jq.post('<c:url value='/collpack/collaboration/member/deleteMemberAjax.do' />', $("#mainForm").serialize())
        		.success(function(data) { $jq("#searchButton").click(); })
				.error(function(event, request, settings) { alert("error"); });
        		*/
				$jq.ajax({
					url : '<c:url value='/collpack/collaboration/member/deleteWorkspaceMemberAjax.do' />?'+$jq("#mainForm").serialize(),
					type : "get",
					success : function(result) {
						$jq("#searchButton").click(); 
					}
				});
			}
			else
			{
				alert('<ikep4j:message pre='${preScript}' key='checkboxMember' />');
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
				alert('<ikep4j:message pre='${preScript}' key='checkboxWorkspace' />');
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
	<ikep4j:message pre='${preHeader}' key='pageMessage' />
	<div class="l_t_corner"></div>
	<div class="r_t_corner"></div>
	<div class="l_b_corner"></div>
	<div class="r_b_corner"></div>					
</div>
	
<!--tab Start-->
<div id="divTab1" class="iKEP_tab">     
	<ul>
		<li><a id="tabs-m" href="#tabs-1"><ikep4j:message pre="${preHeader}" key="listAppMember" /></a></li>
		<li><a id="tabs-w" href="#tabs-2"><ikep4j:message pre="${preHeader}" key="listAppWorkspace" /></a></li>
	</ul>
	<div id="tabs-1" style="padding: 0px;"></div>
	<div id="tabs-2" style="padding: 0px;"></div>
</div>
<!--//tab End-->

<!--blockListTable Start-->
<form id="mainForm" name="mainForm" method="post" action="<c:url value="/collpack/collaboration/workspace/listMyApplicationView.do"/>">  
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
			<div class="totalNum"><%--${searchResult.pageIndex}/ ${searchResult.pageCount} <ikep4j:message pre='${preSearch}' key='page' /> (--%> <ikep4j:message pre='${preSearch}' key='pageCount.info' /><span> ${searchResult.recordCount}</span></div>
		</div>
		
			
		<div class="tableSearch">
		
			<select name="typeId" title='<ikep4j:message pre='${preSearch}' key='typeId' />'>
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
	
	
	
	
<c:if test="${searchCondition.listType=='listAppMember' }">		
	
	<table summary="<ikep4j:message pre="${preList}" key="summaryMember" />">   
		<caption></caption>
		<col style="width: 5%;"/>
		<col style="width: 10%;"/>
		<col style="width: 45%;"/>
		<col style="width: 10%;"/>
		<col style="width: 10%;"/>
		<col style="width: 10%;"/>
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
					<a onclick="sort('sysopName', '<c:if test="${searchCondition.sortColumn eq 'sysopName'}">${searchCondition.sortType}</c:if>');"  href="#a">
						<ikep4j:message pre='${preList}' key='sysopName' />
					</a>
				</th>

				<th scope="col">
					<a onclick="sort('registDate', '<c:if test="${searchCondition.sortColumn eq 'registDate'}">${searchCondition.sortType}</c:if>');"  href="#a">
						<ikep4j:message pre='${preList}' key='registDate' />
					</a>
				</th>	
				
				<th scope="col">
					<a onclick="sort('memberLevel', '<c:if test="${searchCondition.sortColumn eq 'memberLevel'}">${searchCondition.sortType}</c:if>');"  href="#a">
						<ikep4j:message pre='${preList}' key='memberLevel' />
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
					<c:forEach var="workspaceList" items="${searchResult.entity}">
					<tr>
						<td>
						<c:if test="${workspaceList.memberLevel!='1'}">
							<input id="workspaceIds" name="workspaceIds" class="checkbox" title="checkbox" type="checkbox" value="${workspaceList.workspaceId}" />
						</c:if>
						</td>
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
						<td><ikep4j:timezone pattern="yyyy.MM.dd" date="${workspaceList.registDate}"/></td>
						<td>
							<c:choose>
					    		<c:when test="${workspaceList.memberLevel=='1'}"> 
									<ikep4j:message pre='${preCommon}' key='member.1' />
								</c:when>
					    		<c:when test="${workspaceList.memberLevel=='2'}"> 
									<ikep4j:message pre='${preCommon}' key='member.2' />
								</c:when>
								<c:when test="${workspaceList.memberLevel=='3'}"> 
									<ikep4j:message pre='${preCommon}' key='member.3' />
								</c:when>
					    		<c:when test="${workspaceList.memberLevel=='4'}"> 
									<ikep4j:message pre='${preCommon}' key='member.4' />
								</c:when>
								<c:when test="${workspaceList.memberLevel=='5'}"> 
									<ikep4j:message pre='${preCommon}' key='member.5' />
								</c:when>
								<c:when test="${workspaceList.memberLevel=='6'}"> 
									<ikep4j:message pre='${preCommon}' key='member.6' />
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
				
				
<c:if test="${searchCondition.listType!='listAppMember' }">		

	<table summary="<ikep4j:message pre="${preList}" key="summaryOpenClose" />">   
		<caption></caption>
		<col style="width: 5%;"/>
		<col style="width: 10%;"/>
		<col style="width: 10%;"/>		
		<col style="width: 55%;"/>
		<col style="width: 10%;"/>
		<col style="width: 10%;"/>
		<thead>
			<tr>
				<th scope="col"><input id="checkboxAll" class="checkbox" title="checkbox" type="checkbox" value="" /></th> 
				<th scope="col">
					<a onclick="sort('typeId', '<c:if test="${searchCondition.sortColumn eq 'typeId'}">${searchCondition.sortType}</c:if>');"  href="#a">
						<ikep4j:message pre='${preList}' key='typeId' />
					</a>
				</th>
				
				<th scope="col">
					<a onclick="sort('workspaceStatus', '<c:if test="${searchCondition.sortColumn eq 'workspaceStatus'}">${searchCondition.sortType}</c:if>');"  href="#a">
						<ikep4j:message pre='${preList}' key='appType' />
					</a>
				</th>
				
				<th scope="col">
					<a onclick="sort('workspaceName', '<c:if test="${searchCondition.sortColumn eq 'workspaceName'}">${searchCondition.sortType}</c:if>');"  href="#a">
						<ikep4j:message pre='${preList}' key='workspaceName' />
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
						<td colspan="6" class="emptyRecord"><ikep4j:message pre='${preSearch}' key='emptyRecord' /></td> 
					</tr>				        
			    </c:when>
			    <c:otherwise>
					<c:forEach var="workspaceList" items="${searchResult.entity}">
					<tr>
						<td>
						<c:if test="${workspaceList.workspaceStatus=='WO' || workspaceList.workspaceStatus=='WR'}">
						<input id="workspaceIds" name="workspaceIds" class="checkbox" title="checkbox" type="checkbox" value="${workspaceList.workspaceId}" />
						</c:if>
						</td>
						
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

						<td>
							<c:choose>
					    		<c:when test="${workspaceList.workspaceStatus=='O'}"> 
									<ikep4j:message pre='${preList}' key='open' />
								</c:when>
					    		<c:when test="${workspaceList.workspaceStatus=='WO'}"> 
									<ikep4j:message pre='${preList}' key='open' />
								</c:when>
								<c:when test="${workspaceList.workspaceStatus=='WC'}"> 
									<ikep4j:message pre='${preList}' key='close' />
								</c:when>
					    		<c:when test="${workspaceList.workspaceStatus=='C'}"> 
									<ikep4j:message pre='${preList}' key='close' />
								</c:when>
								<c:when test="${workspaceList.workspaceStatus=='WR'}"> 
									<ikep4j:message pre='${preList}' key='reject' />
								</c:when>
								<c:otherwise>
								</c:otherwise>
							</c:choose>									
						</td>	
						<td class="textLeft">
							<a onclick="viewWorkspace('${workspaceList.workspaceId}','${workspaceList.workspaceStatus}');" href="#a" title="${workspaceList.workspaceName}">
							${workspaceList.workspaceName}
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
	<c:if test="${searchCondition.listType=='listAppMember' }">	
	<li><a id="deleteMemberButton" class="button" href="#a" title="<ikep4j:message pre='${preButton}' key='memberOut'/>"><span><ikep4j:message pre='${preButton}' key='memberOut'/></span></a></li>
	</c:if>
	<c:if test="${searchCondition.listType!='listAppMember' }">	
	<li><a id="deleteWorkspaceButton" class="button" href="#a" title="<ikep4j:message pre='${preButton}' key='cancel'/>"><span><ikep4j:message pre='${preButton}' key='cancel'/></span></a></li>
	</c:if>	
</ul>
</div>
<!--//blockButton End-->
