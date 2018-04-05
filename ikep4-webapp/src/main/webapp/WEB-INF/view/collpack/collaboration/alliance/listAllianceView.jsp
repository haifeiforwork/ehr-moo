<%--
=====================================================
	* 기능설명	:	동맹관리 목록(동맹Collaboration 목록, 동맹 요청 받은 목록,동맹요청한 목록)
	* 작성자		:	김종철
=====================================================
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%-- Tab Lib Start --%> 
<%@ include file="/base/common/taglibs.jsp"%>
<%-- Tab Lib End --%> 

<%-- 메시지 관련 Prefix 선언 Start --%> 
<c:set var="preHeader"  value="message.collpack.collaboration.alliance.listAlliance.header" />
<c:set var="preSearch"  value="message.collpack.collaboration.alliance.listAlliance.search" />
<c:set var="preCommon"   value="message.collpack.common" />
<c:set var="preList"    value="message.collpack.collaboration.alliance.listAlliance.list" />
<c:set var="preButton"  value="message.collpack.collaboration.alliance.listAlliance.button" />
<c:set var="preScript"  value="message.collpack.collaboration.alliance.listAlliance.script" />
<%-- 메시지 관련 Prefix 선언 End --%>

<%-- 세션정보 선언 Start --%> 
<c:set var="portal" value="${sessionScope['ikep.portal']}" />
<c:set var="user" value="${sessionScope['ikep.user']}" />
<%-- 세션정보 선언 End --%>

<script type="text/javascript"> 
(function($){
	

	sort = function(sortColumn, sortType) {
		$jq("input[name=sortColumn]").val(sortColumn);

		if( sortType == 'ASC') {
			$jq("input[name=sortType]").val('DESC');	
		} else {
			$jq("input[name=sortType]").val('ASC');
		}
		
		$jq("#mainForm").attr({method:'get'}).submit(); 

	};
	// 동맹 상태 변경
	updateStatus	=	function(status) {

		var countCheckBox	=	$jq("input[name=allianceIds]:checkbox:checked").length;

		if(countCheckBox>0)
		{
			$jq('form[name=mainForm]').attr({
				action:"<c:url value="/collpack/collaboration/alliance/updateAllianceStatus.do"/>"
			});

			$jq("input[name=status]").val(status);
			$jq("#mainForm").submit(); 
		}
		else
		{
			if(status=='1')
				alert('<ikep4j:message pre='${preScript}' key='checkboxApproved' />');
			else if(status=='2')
				alert('<ikep4j:message pre='${preScript}' key='checkboxHold' />');
			else if(status=='3')
				alert('<ikep4j:message pre='${preScript}' key='checkboxClose' />');
			else
				alert('<ikep4j:message pre='${preScript}' key='checkboxAlliance' />');			
		}
		return false; 		
		
	};
	// 동맹 상태 변경
	deleteAlliance	=	function() {

		var countCheckBox	=	$jq("input[name=allianceIds]:checkbox:checked").length;

		if(countCheckBox>0)
		{
			$jq('form[name=mainForm]').attr({
				action:"<c:url value="/collpack/collaboration/alliance/deleteAlliance.do"/>"
			});
			$jq("#mainForm").submit(); 
		}
		else
		{
			alert('<ikep4j:message pre='${preScript}' key='checkboxDelete' />');			
		}
		return false; 		
		
	};
	
	// Collaboration 조회
	viewWorkspace = function(workspaceId) {


		parent.goWorkspace(workspaceId);
		
		/***$jq('form[name=mainForm]').attr({
			action:"<c:url value="/collpack/collaboration/main/Workspace.do"/>",
			method:"GET"
		});			

		$jq("input[name=workspaceId]").val(workspaceId);
		$jq("#mainForm").submit(); 
		
		return false;**/
	};
	createAlliance	= function(){
		iKEP.showDialog({
			title: "<ikep4j:message pre="${preScript}" key="addPopupTitle" />",
			url: "<c:url value='/collpack/collaboration/alliance/createAllianceView.do' />?workspaceId=${searchCondition.workspaceId}&parentListType=${searchCondition.listType}",
			modal: true,
			width: 700,
			height: 400,
			//params : {search:"keyword", items:items },
			callback : function(result) {
				/**$sel.empty();
				$jq.each(result, function() {
					var tpl = "";
					switch(this.type) {
						case "group" : tpl = "addrBookItemGroup"; break;
						case "user" : tpl = "addrBookItemUser"; break;
					}
					var $option = $.tmpl(tpl, this).appendTo($sel);
					$.data($option[0], "data", this);
					if( this.searchSubFlag == true ){
						$jq('input[name=searchSubFlag]:hidden').val("true") ;
					}

				})**/
			},
			close : function() {
        		//location.reload();
        		location.replace(location.href.substring(0, location.href.indexOf("#")));
        		return false;
        	}
		});

		return false; 
	};
	
	closeAllianceDialog = function() {
		//location.reload();
 		location.replace(location.href.substring(0, location.href.indexOf("#")));
		return false;
	};
	
	helpAlliance	= function(){
		iKEP.showDialog({
			title: "<ikep4j:message pre="${preScript}" key="allianceTitle" />",
			url: "<c:url value='/collpack/collaboration/alliance/helpAllianceView.do' />",
			modal: true,
			width: 850,
			height: 500
		});

		return false; 
	};
	

	// onload시 수행할 코드
	$jq(document).ready(function() { 

		<c:forEach var="allianceList" items="${searchResult.entity}" varStatus="status">
		$jq("#divTab_s_${allianceList.workspaceId}").tabs();
		</c:forEach>


		
		<c:if test="${searchCondition.listType=='TO' }">	
		$jq("#divTab1").tabs({selected : 0});
		</c:if>
		
		<c:if test="${searchCondition.listType=='FROM' }">	
		$jq("#divTab1").tabs({selected : 1});
		</c:if>
		
		<c:if test="${empty searchCondition.listType}">	
		$jq("#divTab1").tabs({selected : 2});
		</c:if>
				
		$jq("#tabs").click(function() { 
			location.href="<c:url value="/collpack/collaboration/alliance/listAllianceView.do"/>?workspaceId=${searchCondition.workspaceId}&listType=";
		}); 
		$jq("#tabs-TO").click(function() { 
			location.href="<c:url value="/collpack/collaboration/alliance/listAllianceView.do"/>?workspaceId=${searchCondition.workspaceId}&listType=TO";
		}); 		
		$jq("#tabs-FROM").click(function() { 
			location.href="<c:url value="/collpack/collaboration/alliance/listAllianceView.do"/>?workspaceId=${searchCondition.workspaceId}&listType=FROM";
		}); 
		
		$jq("#checkboxAll").click(function() { 
			$jq("input[name=allianceIds]").attr("checked", $jq(this).is(":checked"));  
		}); 
	   
		$jq("select[name=pagePerRecord]").change(function() {
			$jq("input[name=pageIndex]").val('1');
			$jq("#mainForm").submit();
		});
	});		
	
	var showDivId = null;
	divBoard = function(id){
		
		if($jq("#board_"+id).is(':hidden')) {

			if(showDivId!=null)
				$jq("#board_"+showDivId).hide();
			$jq("#board_"+id).show();
			
			showDivId=id;
		}
		else {
			$jq("#board_"+id).hide();
		}
	}
	
})();	

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
		<li><a href="#tabs-1" id="tabs-TO"><ikep4j:message pre="${preHeader}" key="toAlliance" /></a></li>
		<li><a href="#tabs-2" id="tabs-FROM"><ikep4j:message pre="${preHeader}" key="fromAlliance" /></a></li>
		<li><a href="#tabs-3" id="tabs"><ikep4j:message pre="${preHeader}" key="approvedAll" /></a></li>
	</ul>
	<div id="tabs-1" style="padding: 0px;"></div>
	<div id="tabs-2" style="padding: 0px;"></div>
	<div id="tabs-3" style="padding: 0px;"></div>
	<!--blockButton Start-->
	<div class="blockButton tabBtn"> 
		<ul>
			<!--li><a class="button_s" onclick="createAlliance()" href="#a"><span><ikep4j:message pre='${preButton}' key='create'/></span></a></li-->
			<li><a class="button_s_pop" onclick="helpAlliance()" href="#a"><img src="<c:url value="/base/images/icon/ic_alliance_q.png"/>" alt="" title="<ikep4j:message pre='${preButton}' key='alliance'/>"/><span><ikep4j:message pre='${preButton}' key='alliance'/></span></a></li>
		</ul>
	</div>
	<!--//blockButton End-->	
</div>

<!--blockListTable Start-->
<form id="mainForm" name="mainForm" method="get" action="<c:url value='/collpack/collaboration/alliance/listAllianceView.do' />">  
<spring:bind path="searchCondition.sortColumn">
<input name="${status.expression}" type="hidden" value="${status.value}" title="<ikep4j:message pre='${preSearch}' key='${status.expression}'/>" />
</spring:bind> 		
<spring:bind path="searchCondition.sortType">
<input name="${status.expression}" type="hidden" value="${status.value}" title="<ikep4j:message pre='${preSearch}' key='${status.expression}'/>" />
</spring:bind> 
<spring:bind path="searchCondition.workspaceId">
<input name="${status.expression}" type="hidden" value="${status.value}" title="<ikep4j:message pre='${preSearch}' key='${status.expression}'/>" />
</spring:bind> 
<spring:bind path="searchCondition.listType">
<input name="${status.expression}" type="hidden" value="${status.value}" title="<ikep4j:message pre='${preSearch}' key='${status.expression}'/>" />
</spring:bind>
<spring:bind path="searchCondition.status">
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
							
		<div class="clear"></div>
	</div>
	<!--//tableTop End-->
	

	<c:if test="${!empty searchCondition.listType}">
	<table summary="<ikep4j:message pre="${preList}" key="summary" />">   
		<caption></caption>
		<col style="width: 5%;"/>
		<col style="width: 7%;"/>
		<col style="width: 20%;"/>
		<col style="width: 10%;"/>
		<col style="width: 43%;"/>
		<col style="width: 8%;"/>
		<col style="width: 7%;"/>
		<thead>
			<tr>
				<th scope="col"><input id="checkboxAll" class="checkbox" title="checkbox" type="checkbox" value="" /></th> 
				<th scope="col">
					<a onclick="sort('TYPE_ID', '<c:if test="${searchCondition.sortColumn eq 'TYPE_ID'}">${searchCondition.sortType}</c:if>');"  href="#a">
						<ikep4j:message pre='${preList}' key='typeId' />
					</a>
				</th>

				<th scope="col">
					<a onclick="sort('WORKSPACE_NAME', '<c:if test="${searchCondition.sortColumn eq 'WORKSPACE_NAME'}">${searchCondition.sortType}</c:if>');"  href="#a">
						<ikep4j:message pre='${preList}' key='workspaceName' />
					</a>
				</th>
				<c:choose>
			    <c:when test="${empty searchCondition.listType}">
				<th scope="col">
					<a onclick="sort('E.USER_NAME', '<c:if test="${searchCondition.sortColumn eq 'E.USER_NAME'}">${searchCondition.sortType}</c:if>');"  href="#a">
						<ikep4j:message pre='${preList}' key='sysopName' />
					</a>
				</th>									
				<th scope="col">
					<a onclick="sort('DESCRIPTION', '<c:if test="${searchCondition.sortColumn eq 'DESCRIPTION'}">${searchCondition.sortType}</c:if>');"  href="#a">
						<ikep4j:message pre='${preList}' key='description' />
					</a>
				</th>

				
				<th scope="col">
					<a onclick="sort('UPDATE_DATE', '<c:if test="${searchCondition.sortColumn eq 'UPDATE_DATE'}">${searchCondition.sortType}</c:if>');"  href="#a">
						<ikep4j:message pre='${preList}' key='updateDate' />
					</a>
				</th>
				</c:when>
				<c:otherwise>
				<th scope="col">
					<a onclick="sort('REGISTER_NAME', '<c:if test="${searchCondition.sortColumn eq 'REGISTER_NAME'}">${searchCondition.sortType}</c:if>');"  href="#a">
						<ikep4j:message pre='${preList}' key='sysopName' />
					</a>
				</th>									
				<th scope="col">
					<a onclick="sort('REQUEST_REASON', '<c:if test="${searchCondition.sortColumn eq 'REQUEST_REASON'}">${searchCondition.sortType}</c:if>');"  href="#a">
						<ikep4j:message pre='${preList}' key='requestReason' />
					</a>
				</th>

				
				<th scope="col">
					<a onclick="sort('REGIST_DATE', '<c:if test="${searchCondition.sortColumn eq 'REGIST_DATE'}">${searchCondition.sortType}</c:if>');"  href="#a">
						<ikep4j:message pre='${preList}' key='registDate' />
					</a>
				</th>						
				</c:otherwise>
				</c:choose>	
				
				<th scope="col">
					<a onclick="sort('STATUS', '<c:if test="${searchCondition.sortColumn eq 'STATUS'}">${searchCondition.sortType}</c:if>');"  href="#a">
						<ikep4j:message pre='${preList}' key='status' />
					</a>
				</th>
			</tr>
		</thead> 
		<tbody>
			<c:choose>
			    <c:when test="${searchResult.emptyRecord}">
					<tr>
						<td colspan="7" class="emptyRecord"><ikep4j:message pre='${preSearch}' key='emptyRecord' /></td> 
					</tr>				        
			    </c:when>
			    <c:otherwise>
					<c:forEach var="allianceList" items="${searchResult.entity}">
					<tr>
						<td>
						<c:if test="${allianceList.status!='1'}"> 
						<input id="allianceIds" name="allianceIds" class="checkbox" title="checkbox" type="checkbox" value="${allianceList.allianceId}" />
						</c:if>
						</td>
						<td>
							<c:choose>
							<c:when test="${user.localeCode == portal.defaultLocaleCode}">
								${allianceList.typeName}
							</c:when>
							<c:otherwise>
								${allianceList.typeEnglishName}
							</c:otherwise>
							</c:choose>	
						</td>
						<td class="textLeft">
							<a onclick="viewWorkspace('${allianceList.workspaceId}');" href="#a" title="${allianceList.workspaceName}">
							${allianceList.workspaceName}
							</a>
						</td>
					
						<c:choose>
					    	<c:when test="${empty searchCondition.listType}">				
								<td>
								<c:choose>
								<c:when test="${user.localeCode == portal.defaultLocaleCode}">
									${allianceList.sysopName}
								</c:when>
								<c:otherwise>
									${allianceList.sysopEnglishName}
								</c:otherwise>
								</c:choose>								
								</td>
								<td class="textLeft" title="${allianceList.workspaceDescription}"><div class="ellipsis">${allianceList.workspaceDescription}</div></td>
								<td><ikep4j:timezone pattern="yyyy.MM.dd" date="${allianceList.updateDate}"/></td>
							</c:when>
							<c:otherwise>
								<td>
								<c:choose>
								<c:when test="${user.localeCode == portal.defaultLocaleCode}">
									${allianceList.sysopName}
								</c:when>
								<c:otherwise>
									${allianceList.sysopEnglishName}
								</c:otherwise>
								</c:choose>										
								</td>
								<td class="textLeft" title="${allianceList.requestReason}"><div class="ellipsis">${allianceList.requestReason}</div></td>
								<td><ikep4j:timezone pattern="yyyy.MM.dd" date="${allianceList.registDate}"/></td>							
							</c:otherwise>
						</c:choose>								
						<td>
							<c:choose>
					    		<c:when test="${allianceList.status=='0'}"> 
									<ikep4j:message pre='${preList}' key='0' />
								</c:when>
					    		<c:when test="${allianceList.status=='1'}"> 
									<ikep4j:message pre='${preList}' key='1' />
								</c:when>
								<c:when test="${allianceList.status=='2'}"> 
									<ikep4j:message pre='${preList}' key='2' />
								</c:when>
					    		<c:when test="${allianceList.status=='3'}"> 
									<ikep4j:message pre='${preList}' key='3' />
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
	
	<c:if test="${empty searchCondition.listType}">
	<table summary="<ikep4j:message pre="${preList}" key="summary" />">   
		<caption></caption>
		<col style="width: 5%;"/>
		<col style="width: 7%;"/>
		<col style="width: 20%;"/>
		<col style="width: 10%;"/>
		<col style="width: 36%;"/>
		<col style="width: 8%;"/>
		<col style="width: 7%;"/>
		<col style="width: 7%;"/>
		<thead>
			<tr>
				<th scope="col"><input id="checkboxAll" class="checkbox" title="checkbox" type="checkbox" value="" /></th> 
				<th scope="col">
					<a onclick="sort('TYPE_ID', '<c:if test="${searchCondition.sortColumn eq 'TYPE_ID'}">${searchCondition.sortType}</c:if>');"  href="#a">
						<ikep4j:message pre='${preList}' key='typeId' />
					</a>
				</th>

				<th scope="col">
					<a onclick="sort('WORKSPACE_NAME', '<c:if test="${searchCondition.sortColumn eq 'WORKSPACE_NAME'}">${searchCondition.sortType}</c:if>');"  href="#a">
						<ikep4j:message pre='${preList}' key='workspaceName' />
					</a>
				</th>
				<c:choose>
			    <c:when test="${empty searchCondition.listType}">
				<th scope="col">
					<a onclick="sort('E.USER_NAME', '<c:if test="${searchCondition.sortColumn eq 'E.USER_NAME'}">${searchCondition.sortType}</c:if>');"  href="#a">
						<ikep4j:message pre='${preList}' key='sysopName' />
					</a>
				</th>									
				<th scope="col">
					<a onclick="sort('DESCRIPTION', '<c:if test="${searchCondition.sortColumn eq 'DESCRIPTION'}">${searchCondition.sortType}</c:if>');"  href="#a">
						<ikep4j:message pre='${preList}' key='description' />
					</a>
				</th>

				
				<th scope="col">
					<a onclick="sort('UPDATE_DATE', '<c:if test="${searchCondition.sortColumn eq 'UPDATE_DATE'}">${searchCondition.sortType}</c:if>');"  href="#a">
						<ikep4j:message pre='${preList}' key='updateDate' />
					</a>
				</th>
				</c:when>
				<c:otherwise>
				<th scope="col">
					<a onclick="sort('REGISTER_NAME', '<c:if test="${searchCondition.sortColumn eq 'REGISTER_NAME'}">${searchCondition.sortType}</c:if>');"  href="#a">
						<ikep4j:message pre='${preList}' key='sysopName' />
					</a>
				</th>									
				<th scope="col">
					<a onclick="sort('REQUEST_REASON', '<c:if test="${searchCondition.sortColumn eq 'REQUEST_REASON'}">${searchCondition.sortType}</c:if>');"  href="#a">
						<ikep4j:message pre='${preList}' key='requestReason' />
					</a>
				</th>

				
				<th scope="col">
					<a onclick="sort('REGIST_DATE', '<c:if test="${searchCondition.sortColumn eq 'REGIST_DATE'}">${searchCondition.sortType}</c:if>');"  href="#a">
						<ikep4j:message pre='${preList}' key='registDate' />
					</a>
				</th>						
				</c:otherwise>
				</c:choose>	
				
				<th scope="col">
					<a onclick="sort('STATUS', '<c:if test="${searchCondition.sortColumn eq 'STATUS'}">${searchCondition.sortType}</c:if>');"  href="#a">
						<ikep4j:message pre='${preList}' key='status' />
					</a>
				</th>
				<th scope="col"><ikep4j:message pre='${preList}' key='share' /></th>				
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
					<c:forEach var="allianceList" items="${searchResult.entity}" varStatus="status">
					<tr>
						<td><input id="allianceIds" name="allianceIds" class="checkbox" title="checkbox" type="checkbox" value="${allianceList.allianceId}" /></td>
						<td>
							<c:choose>
							<c:when test="${user.localeCode == portal.defaultLocaleCode}">
								${allianceList.typeName}
							</c:when>
							<c:otherwise>
								${allianceList.typeEnglishName}
							</c:otherwise>
							</c:choose>							
						</td>
						<td class="textLeft">
							<a onclick="viewWorkspace('${allianceList.workspaceId}');" href="#a" title="${allianceList.workspaceName}">
							${allianceList.workspaceName}
							</a>
						</td>
					
						<c:choose>
					    	<c:when test="${empty searchCondition.listType}">				
								<td>
								<c:choose>
								<c:when test="${user.localeCode == portal.defaultLocaleCode}">
									${allianceList.sysopName}
								</c:when>
								<c:otherwise>
									${allianceList.sysopEnglishName}
								</c:otherwise>
								</c:choose>								
								</td>
								<td class="textLeft" title="${allianceList.workspaceDescription}"><div class="ellipsis">${allianceList.workspaceDescription}</div></td>
								<td><ikep4j:timezone pattern="yyyy.MM.dd" date="${allianceList.updateDate}"/></td>
							</c:when>
							<c:otherwise>
								<td>
								<c:choose>
								<c:when test="${user.localeCode == portal.defaultLocaleCode}">
									${allianceList.registerName}
								</c:when>
								<c:otherwise>
									${allianceList.registerEnglishName}
								</c:otherwise>
								</c:choose>								
								</td>
								<td class="textLeft" title="${allianceList.requestReason}"><div class="ellipsis">${allianceList.requestReason}</div></td>
								<td><ikep4j:timezone pattern="yyyy.MM.dd" date="${allianceList.registDate}"/></td>							
							</c:otherwise>
						</c:choose>								
						<td>
							<c:choose>
					    		<c:when test="${allianceList.status=='0'}"> 
									<ikep4j:message pre='${preList}' key='0' />
								</c:when>
					    		<c:when test="${allianceList.status=='1'}"> 
									<ikep4j:message pre='${preList}' key='1' />
								</c:when>
								<c:when test="${allianceList.status=='2'}"> 
									<ikep4j:message pre='${preList}' key='2' />
								</c:when>
					    		<c:when test="${allianceList.status=='3'}"> 
									<ikep4j:message pre='${preList}' key='3' />
								</c:when>
								<c:otherwise>
								</c:otherwise>
							</c:choose>									
						</td>
						<td>
						<img onclick="divBoard('${status.index}');" src="<c:url value="/base/images/icon/ic_share.gif"/>" alt="" />
						<!--layer start-->
						<div id="board_${status.index}" class="process_layer none" style="right:0px; z-index:99; width:330px;">
							<div class="process_layer_t"><ikep4j:message pre='${preList}' key='share' />
							<a href="#a" onclick="divBoard('${status.index}');"><img src="<c:url value="/base/images/icon/ic_close_layer.gif"/>" alt="<ikep4j:message pre='${preList}' key='close' />" /></a>
							</div>
							<div class="process_corporate2">
								<!--tab Start-->		
								<div id="divTab_s_${allianceList.workspaceId}" class="iKEP_tab_s">
								<ul>
									<li><a href="#tabs-1_${allianceList.workspaceId}"><ikep4j:message pre='${preList}' key='giveBoard' /></a></li>
									<li><a href="#tabs-2_${allianceList.workspaceId}"><ikep4j:message pre='${preList}' key='receiveBoard' /></a></li>
								</ul>
								
								<div id="tabs-1_${allianceList.workspaceId}" class="process_corporate4">
								<ul>
								<c:if test="${!empty allianceList.giveAllianceList}">								
								<c:forEach var="boardList" items="${allianceList.giveAllianceList}">
									<li><span><a href="#a" onclick="parent.iKEP.iFrameMenuOnclick('<c:url value='/collpack/collaboration/board/boardItem/listBoardItemView.do?workspaceId=${workspace.workspaceId}&boardId=${boardList.boardId}'/>');"><c:out value="${boardList.boardName}"/></a></span></li>
								</c:forEach>
								</c:if>
								<c:if test="${empty allianceList.giveAllianceList}">
								공개한 게시판이 없습니다.
								</c:if>
								</ul>
								</div>
								
								<div id="tabs-2_${allianceList.workspaceId}" class="process_corporate4">
								<ul>
								<c:if test="${!empty allianceList.receiveAllianceList}">	
								<c:forEach var="boardList" items="${allianceList.receiveAllianceList}">
									<li><span><a href="#a" onclick="parent.iKEP.iFrameMenuOnclick('<c:url value='/collpack/collaboration/board/boardItem/listBoardItemView.do?workspaceId=${workspace.workspaceId}&boardId=${boardList.boardId}'/>');"><c:out value="${boardList.boardName}"/></a></span></li>
								</c:forEach>
								</c:if>
								<c:if test="${empty allianceList.receiveAllianceList}">
								공개받은 게시판이 없습니다.
								</c:if>								
								</ul>
								</div>									
											
								</div>		
								<!--//tab End-->
							</div>
															
						</div>		
						<!--layer end-->
						
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
	<li><a id="createAllianceButton" onclick="createAlliance()" class="button" href="#a" title="<ikep4j:message pre='${preButton}' key='create'/>"><span><ikep4j:message pre='${preButton}' key='create'/></span></a></li>
	<c:if test="${empty searchCondition.listType}">
	<li><a id="closeAllianceButton" onclick="updateStatus('3')" class="button" href="#a" title="<ikep4j:message pre='${preButton}' key='close'/>"><span><ikep4j:message pre='${preButton}' key='close'/></span></a></li>
	</c:if>
	<c:if test="${searchCondition.listType=='TO'}">
	<li><a id="holdAllianceButton" onclick="updateStatus('2')" class="button" href="#a" title="<ikep4j:message pre='${preButton}' key='hold'/>"><span><ikep4j:message pre='${preButton}' key='hold'/></span></a></li>
	<li><a id="approvedAllianceButton" onclick="updateStatus('1')" class="button" href="#a" title="<ikep4j:message pre='${preButton}' key='approved'/>"><span><ikep4j:message pre='${preButton}' key='approved'/></span></a></li>
	</c:if>
	<c:if test="${searchCondition.listType=='FROM'}">
	<li><a id="cancelAllianceButton" onclick="deleteAlliance()" class="button" href="#a" title="<ikep4j:message pre='${preButton}' key='cancel'/>"><span><ikep4j:message pre='${preButton}' key='cancel'/></span></a></li>
	</c:if> 
</ul>
</div>
<!--//blockButton End-->


