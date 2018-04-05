<%--
=====================================================
	* 기능설명	:	팀 개설관리목록
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

<%-- 세션정보 선언 Start --%> 
<c:set var="portal" value="${sessionScope['ikep.portal']}" />
<c:set var="user" value="${sessionScope['ikep.user']}" />
<%-- 세션정보 선언 End --%>



<script type="text/javascript" src="<c:url value="/base/js/jquery/jquery.jstree.pack.js"/>"></script>

<script type="text/javascript">
var treeNode;
(function($) {
	//
		//정렬 조건 변경 함수
	/*sort = function(sortColumn, sortType) {
		$("input[name=sortColumn]").val(sortColumn);
		if( sortType == 'ASC') {
			$("input[name=sortType]").val('DESC');	
		} else {
			$("input[name=sortType]").val('ASC');
		}
		$("#searchButton").click();
	};*/
	
	//search Enter
	/*search = function() {
		//$jq("input[name=pageIndex]").val('1');
		$jq("#searchButton").click();
	};*/	
	

	var createOrgTree = function() {
		$jq("#treeOrg").jstree("destroy").empty();
		$jq("#treeOrg").unbind("dblclick");
		
		$("#treeOrg").bind("loaded.jstree", function (event, data) {
			var $TopItem = $("ul > li:first", this);
			$("#treeOrg").jstree("open_node", $TopItem.children("a")[0], false);
        });
		treeNode = $("#treeOrg").jstree({	// 맵 트리 생성
		plugins : [ "themes" ,"json_data", "ui"],
		json_data : {
				data : iKEP.arrayToTreeData(${rootString}),
				ajax : {
						type: "POST",
						url : "<c:url value='/collpack/collaboration/workspace/requestGroupChildren.do'/>",
						dataType: "json",
						data : function ($el) {	//$el : current item
							return { 
							 "operation" : "get_children",  
							 "groupId" : $el.attr("code")
							};
						},
						success : function(data) {
							iKEP.iFrameContentResize(); 
							return iKEP.arrayToTreeData(data.items);
						}
					 }
			}
        }).delegate("a[href$='#']", "dblclick", function (e) { // clicking node text expands and contracts it
			e.preventDefault();
			this.blur(); 
			$("#treeOrg").jstree("toggle_node", this, false, true);
			iKEP.iFrameContentResize(); 	 
        }).delegate("a[href$='#']", "click", function (e) { 
			e.preventDefault()
	    	$jq("#resultOrgOpenList").empty();

			$jq("#resultOrgOpenList").load('<c:url value="/collpack/collaboration/workspace/resultWaitingOpenOrgList.do"/>?groupId='+$(this).parent().attr("code"))
			.error(function(event, request, settings) { alert("error"); });
			
		});
		iKEP.iFrameContentResize(); 		
        

	};

	// onload시 수행할 코드
	$jq(document).ready(function() { 
		
		
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
		
		$("#resultOrgOpenList").load('<c:url value="/collpack/collaboration/workspace/resultWaitingOpenOrgList.do"/>')
		.error(function(event, request, settings) { alert("error"); });
				
		createOrgTree();
		
		iKEP.iFrameContentResize();  
		
				
	});
})(jQuery);  

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


<!--blockLeft Start-->
<div class="blockLeft" style="width:30%;height:400px;overflow:auto;">	
	<div class="leftTree treeBox">	
		<div id="treeOrg"></div>
	</div>
</div>
<!--//blockLeft End-->

<!--blockRight Start-->
<div id="resultOrgOpenList" class="blockRight" style="width:68%;"></div>

<%--div id="resultOrgOpenList1" class="blockRight" style="width:68%;">

	<!--blockListTable Start-->
	<form id="mainForm" name="mainForm" method="get" action="<c:url value="/collpack/collaboration/workspace/listWaitingOpenOrgView.do"/>">  
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
					<input name="${status.expression}" value="${status.value}" type="text" class="inputbox" title='<ikep4j:message pre='${preSearch}' key='${status.expression}' />'  size="20" />
				</spring:bind>
				<a id="searchButton" name="searchButton" href="#a"><img src="<c:url value='/base/images/theme/theme01/basic/ic_search.gif' />" alt="<ikep4j:message pre='${preSearch}' key='search' />" /></a> 
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
						<c:forEach var="groupList" items="${searchResult.entity}">
						<tr>
							<td>${groupList.countWorkspace} - ${groupList.teamId} - ${groupList.hasWorkspace}<input id="groupIds" name="groupIds" class="checkbox" title="checkbox" type="checkbox" value="${groupList.groupId}"/></td>
							<td>
								<c:choose>
								<c:when test="${user.localeCode == portal.defaultLocaleCode}">
									${groupList.groupName}
								</c:when>
								<c:otherwise>
									${groupList.groupEnglishName}
								</c:otherwise>
								</c:choose>	
							</td>
							<td>${groupList.fullPath}</td>
							<td>
								<a href="#a"  onclick="iKEP.showUserContextMenu(this, '${groupList.groupLeaderId}', 'bottom');iKEP.iFrameContentResize(); return false;">
								<c:choose>
								<c:when test="${user.localeCode == portal.defaultLocaleCode}">
									${groupList.groupLeaderName}
								</c:when>
								<c:otherwise>
									${groupList.groupLeaderEnglishName}
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
		<li><a id="createOrgWorkspaceOpenButton"   class="button" href="#a" title="<ikep4j:message pre='${preButton}' key='open'  />"><span><ikep4j:message pre='${preButton}' key='open'  /></span></a></li>
	</ul>
	</div>
	<!--//blockButton End-->
</div--%>
<!--blockRight End-->