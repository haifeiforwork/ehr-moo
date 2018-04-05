<%--
=====================================================
	* 기능설명	:	workspace 내용 수정(회원정보수정 제외)화면
	* 작성자		:	김종철
=====================================================
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%-- Tab Lib Start --%> 
<%@ include file="/base/common/taglibs.jsp"%>
<%-- Tab Lib End --%> 

<%-- 메시지 관련 Prefix 선언 Start --%> 
<c:set var="preHeader"	value="message.collpack.collaboration.workspace.updateWorkspace.header" />
<c:set var="preSearch"	value="message.collpack.collaboration.workspace.updateWorkspace.search" />
<c:set var="preCode"	value="message.collpack.common.code" />
<c:set var="preDetail"	value="message.collpack.collaboration.workspace.updateWorkspace.detail" />
<c:set var="preButton"	value="message.collpack.collaboration.workspace.updateWorkspace.button" />
<c:set var="preScript"	value="message.collpack.collaboration.workspace.updateWorkspace.script" />
<c:set var="preCommon"  value="message.collpack.collaboration.workspace.common" />
<%-- 메시지 관련 Prefix 선언 End --%>

<%-- 세션정보 선언 Start --%> 
<c:set var="portal" value="${sessionScope['ikep.portal']}" />
<c:set var="user" value="${sessionScope['ikep.user']}" />
<%-- 세션정보 선언 End --%>

<script type="text/javascript" src="<c:url value='/base/js/jquery/plugins.pack.js'/>" ></script>

<script type="text/javascript">
<!-- 
var teamStatus;
(function($) {
	var orgWorkspaceName = null;
	var checkName = false;
	//Type category list
	changeType = function(typeId) {
		
        $jq.get('<c:url value="/collpack/collaboration/admin/category/listWorkspaceCategory.do"/>',
			{typeId:typeId},
			function(data) {
				$jq("#categoryId").empty();
				
				for(var i=0 ; i<data.length ; i++) {
					$jq("<option/>").attr("value",data[i].categoryId).text(data[i].categoryName).appendTo("#categoryId");
					if('${workspace.categoryId}'==data[i].categoryId )
						$("#categoryId").val("${workspace.categoryId}");
				}
				if(data.length == 0) {
					$jq('<option/>').attr('value','').text('<ikep4j:message pre="${preScript}" key="noCategory" />').appendTo('#categoryId');
				}
			}

		)
		return false; 
	};

	// duplication Workspace Name
	checkWorkspaceName = function() {
		
		var workspaceName = $jq("input[name=workspaceName]").val();
		if(workspaceName == '${workspace.workspaceName}')
		{
			alert('<ikep4j:message pre='${preScript}' key='sameWorkspaceName' />');
			return;
		}		
		$jq.get('<c:url value="/collpack/collaboration/workspace/checkWorkspaceName.do"/>',
			{workspaceName:workspaceName},
			function(data){
				if(data){
					checkName = true;
					alert('<ikep4j:message pre='${preScript}' key='successWorkspaceName' />');
					orgWorkspaceName=workspaceName;						
				}else{
					$jq("input[name=workspaceName]").val('');
					alert('<ikep4j:message pre='${preScript}' key='dupWorkspaceName' />');
				}
			}
		)
		return false;
	};	
	
	//업로드완료후 fileId 리턴
	afterFileUpload = function(status, fileId, fileName, message, gubun) {
		//리턴받은 fileId를 Hidden값으로 저장함
		//폼을 최종 저장할때 filId를 가지고, fileLink정보를 생성함
		var imgsrc ="<c:url value="/support/fileupload/downloadFile.do"/>?fileId="+fileId;
		var img ="<img id='viewImageDiv' src='"+imgsrc+"' width='110' height='90'/>";
		//$jq("#viewDiv").html(fileId);
		$jq("#viewDiv").html(img);
		$jq("input[name=fileId]").val(fileId);
		
		setTimeout(function() {
			iKEP.iFrameContentResize(); 
		}, 200); 
	};
	
	//검색되거나 선택된 값이 있을경우 실행되는 콜백 메소드
	//result: 검색되거나 선택된 값을 배열형태로 리턴함
	setAddress = function(data) {
		var addStr="";
		$jq.each(data, function() {
			//addStr = addStr + "\""+$jq.trim(this.name)+"\" "+$jq.trim(this.email)+",";
			addStr = $jq.trim(this.name);
			teamId = $jq.trim(this.code);
		});
		
		$jq.get('<c:url value="/collpack/collaboration/workspace/checkWorkspaceTeam.do"/>',
			{teamId : teamId},
			function(data){
				//$teamStatus = data;
				//return data;
				if(data=="WO"){
					alert('<ikep4j:message pre='${preScript}' key='waitingOpen' />');
					return false;
				} else if(data=="WC"){
					alert('<ikep4j:message pre='${preScript}' key='waitingClose' />');
					return false;
				} else if(data=="O") {
					alert('<ikep4j:message pre='${preScript}' key='open' />');
					return false;
				} else {
					$jq("#teamName").val(addStr);
					$jq("#teamId").val(teamId);				
				
				}			
			}
		)
				
		/**
		teamStatus = checkWorkspaceTeam(teamId);

		if(teamStatus=="WO"){
			alert('<ikep4j:message pre='${preScript}' key='waitingOpen' />');
			return false;
		}
		else if(teamStatus=="WC"){
			alert('<ikep4j:message pre='${preScript}' key='waitingClose' />');
			return false;
		}
		else if(teamStatus=="O") {
			alert('<ikep4j:message pre='${preScript}' key='open' />');
			return false;
		}**/
		//alert('sssss')
		//$jq("#teamName").val(addStr);
		//$jq("#teamId").val(teamId);
	};
	
	checkWorkspaceTeam = function(teamId) {

		$jq.get('<c:url value="/collpack/collaboration/workspace/checkWorkspaceTeam.do"/>',
			{teamId : teamId},
			function(data){
				//$teamStatus = data;
				return data;
			}
		)
		//alert("2"+$teamStatus)
		//return $teamStatus;
	};					
	// onload시 수행할 코드
	$jq(document).ready(function() { 
		
		iKEP.iFrameContentResize();  
		
		changeType('${workspace.typeId}');
		
		$jq("#initWorkspaceButton").click(function() {
			$jq("#mainForm")[0].reset(); 	
			return false; 	
		});
		//파일업로드 버튼 이벤트 바인딩
		$jq('#fileuploadBtn').click(function(event) {

			//파일업로드 팝업창
			//파라미터(이벤트객체이름,에디터에서사용여부(0:일반,1:에디터에서),이미지여부(0:모든파일,1:이미지파일만 가능))
			iKEP.fileUpload(event.target.id,'0','1');

		});

		$jq("#saveWorkspaceButton").click(function() {
			
			$jq("#mainForm").submit(); 	
		});

		$jq("#dupWorkspaceButton").click(function() {
			checkWorkspaceName(); 
		});
		
		//주소록 버튼에 이벤트 바인딩
		$jq('#addrBtn').click( function() {
			//파라미터(콜백함수, 전송파라미터, 팝업옵션)
			iKEP.showAddressBook(setAddress, "", {selectType:"group", isAppend:false, selectMaxCnt:1, tabs:{common:1}});
		});
		
		/*
		$jq("input[name=teamName]").click( function() {
			iKEP.showAddressBook(setAddress, "", {selectType:"group", isAppend:false, selectMaxCnt:1, tabs:{common:1}});
		});	
		 */
		/**
		 * Validation Logic Start
		 */
		
		var validOptions = {
			rules : {
				workspaceName :	{
					required : true,
					maxlength : 200
				},
				description :	{
					required : true,
					maxlength : 500
				}
			},
			messages : {
				workspaceName : {
					direction	:	"bottom",
					required	:	"<ikep4j:message key="NotEmpty.workspace.workspaceName" />",
					maxlength	:	"<ikep4j:message key="Size.workspace.workspaceName" />"
					
				},
				description : {
					direction	:	"bottom",
					required	:	"<ikep4j:message key="NotEmpty.workspace.description" />",
					maxlength	:	"<ikep4j:message key="Size.workspace.description" />"
				}

			}
			,
			notice : {
				workspaceName : {
					direction	:	"bottom",
					message		:	"<ikep4j:message key="Notice.workspace.changeWorkspaceName" />"
				}
			},
			submitHandler : function(form) {
			
				var workspaceName = $jq("input[name=workspaceName]").val();

				if(orgWorkspaceName != workspaceName && workspaceName!= '${workspace.workspaceName}')
				{
					alert('<ikep4j:message pre='${preScript}' key='checkWorkspaceName' />');
					return false;					
				}
		
				// 부가 검증 처리
				// Ws Name Dup Check
				if(!checkName && $jq("input[name=workspaceName]").val() != '${workspace.workspaceName}')
				{
					alert('sss')
					alert('<ikep4j:message pre='${preScript}' key='checkWorkspaceName' />');
					return false;
				}
				// tag Check
				var tagNames = $jq("#mainForm").find('input[name=tag]').val();
				
				if(iKEP.tagging.lengthCheck(tagNames)){
					alert('<ikep4j:message pre='${preScript}' key='tagLength' />');
					return false;
				}
				
				if(!confirm("<ikep4j:message pre='${preCommon}' key='save' />")) {
					return;
				}
			
				$.ajax({
					url : "<c:url value='/collpack/collaboration/workspace/updateWorkspaceInfo.do' />",
					type : "post",
					data : $(form).serialize(),
					loadingElement : {container:"#pageLodingDiv"},
					success : function(result) {
						location.href= "<c:url value='/collpack/collaboration/workspace/updateWorkspaceInfoView.do'/>?workspaceId="+result+"&listType=${searchCondition.listType}"; //조회화면으로 포워딩
					},
					error : function(xhr, exMessage) {
						
						var errorItems = $.parseJSON(xhr.responseText).exception;
						validator.showErrors(errorItems);
					}
				});		
			}
		};


		var validator = new iKEP.Validator("#mainForm", validOptions);

		/**
		 * Validation Logic End
		 */		
	});
	
})(jQuery);  
//-->
</script>

<div id="pageLodingDiv">

<h1 class="none"><ikep4j:message pre="${preHeader}" key="pageTitle" /></h1> 

<!--pageTitle Start-->

<div id="pageTitle"> 
	<h2><ikep4j:message pre="${preHeader}" key="pageTitleInfo" /></h2> 

</div> 

<!--//pageTitle End-->  
<!--blockListTable Start-->
<form id="mainForm" name="mainForm" method="post">  
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
<spring:bind path="searchCondition.isOrganization">
<input name="${status.expression}" type="hidden" value="${status.value}" title="<ikep4j:message pre='${preSearch}' key='${status.expression}'/>" />
</spring:bind>
<input name="workspaceIds" id="workspaceIds" type="hidden" value="${workspace.workspaceId}"/>
<input name="teamId" id="teamId" type="hidden" value="${workspace.teamId}"/>

<div class="blockDetail">

	<table summary="<ikep4j:message pre="${preDetail}" key="summary" />">
		<caption></caption>
		<col style="width: 18%;"/>
		<col style="width: 32%;"/>
		<col style="width: 18%;"/>
		<col style=""/>		
		<tbody>
		

		<tr>
			<th scope="row"><ikep4j:message pre='${preDetail}' key='registerName' /></th>
			<td>
				<c:choose>
				<c:when test="${user.localeCode == portal.defaultLocaleCode}">
					${workspace.registerName}
				</c:when>
				<c:otherwise>
					${workspace.registerEnglishName}
				</c:otherwise>
				</c:choose>	
			
			</td>
			<th scope="row"><ikep4j:message pre='${preDetail}' key='registDate' /></th>
			<td><ikep4j:timezone pattern="yyyy.MM.dd" date="${workspace.registDate}"/></td>
		</tr>
		<tr>
			<th scope="row"><span id="span_category_text"></span><ikep4j:message pre='${preDetail}' key='sysopName' /></th>
			<td colspan="3">
				<c:choose>
				<c:when test="${user.localeCode == portal.defaultLocaleCode}">
					${workspace.sysopName}
				</c:when>
				<c:otherwise>
					${workspace.sysopEnglishName}
				</c:otherwise>
				</c:choose>	
			</td>			
		</tr>
					
		<c:if test="${workspace.isOrganization!=1 }">
		<tr>	
			<th scope="row"><ikep4j:message pre='${preDetail}' key='typeId' /></th>
			<td colspan="3">
			<c:forEach var="workspaceTypeList" items="${workspace.workspaceTypeList}" varStatus="status">
			<input type="radio" class="radio" id="typeId" name="typeId" value="${workspaceTypeList.typeId}" <c:if test="${workspaceTypeList.typeId==workspace.typeId}">checked</c:if> onclick="changeType('${workspaceTypeList.typeId}');" /> 
				<c:choose>
				<c:when test="${user.localeCode == portal.defaultLocaleCode}">
					${workspaceTypeList.typeName}
				</c:when>
				<c:otherwise>
					${workspaceTypeList.typeEnglishName}
				</c:otherwise>
				</c:choose>					 
			</c:forEach>	
			</td>
		</tr>
		<tr>
			<th scope="row"><span id="span_category_text"><font	color='#990000'>*</font></span><ikep4j:message pre='${preDetail}' key='selectCategory' /></th>
			<td colspan="3"><select name="categoryId" id="categoryId">		
			</select></td>
		</tr>			
		</c:if>
		<c:if test="${workspace.isOrganization==1 }">
		<tr>
			<th scope="row"><font color='#990000'>*</font>&nbsp;<ikep4j:message pre='${preDetail}' key='teamSelect'/></td>
			<td colspan="3">
			<input type="text" class="inputbox w80" id="teamName" name="teamName" value="${workspace.groupName }" readonly="readonly" />
			<!--
			<a id="addrBtn" class="button_s" href="#a" title="<ikep4j:message pre='${preButton}' key='searchTeam'/>"><span><ikep4j:message pre='${preButton}' key='searchTeam'/></span></a>
			  -->
			</td>
		</tr>
		</c:if>
	
		<tr>
			<th scope="row"><ikep4j:message pre='${preDetail}' key='workspaceName' /></th>
			<td colspan="3">
			<div>
			<spring:bind path="workspace.workspaceName">			
			<input name="${status.expression}" id="${status.expression}"	class="inputbox w80" type="text" value="${status.value}"/>
			<a id="dupWorkspaceButton" class="button_s" href="#a" title="<ikep4j:message pre='${preButton}' key='dupWorkspace'/>"><span><ikep4j:message pre='${preButton}' key='dupWorkspace'/></span></a>
			<label for="${status.expression}" class="serverError"> ${status.errorMessage}</label>
			</spring:bind>
			</div>			
			</td>
		</tr>

		<tr>
			<th scope="row"><ikep4j:message pre='${preDetail}' key='introImage' /></th>
			<td colspan="3">
			
			<input name="fileId" type="hidden" value="" title="fileId" />
			
			<span id="viewDiv">
			<c:forEach var="fileDataList" items="${workspace.fileDataList}" varStatus="tagLoop">

			<img id='viewImageDiv' src='<c:url value="/support/fileupload/downloadFile.do"/>?fileId=${fileDataList.fileId}' width="110" height="90"/>
			</c:forEach>
			</span>	
			<a class="button" href="#a" >
				<span name="fileuploadBtn" id="fileuploadBtn"><ikep4j:message pre='${preButton}' key='introImage' /></span>
			</a>
			</td>
		</tr>
		<tr>
			<th scope="row"><font color='#990000'>*</font>&nbsp;<ikep4j:message pre='${preDetail}' key='description' /></th>
			<td colspan="3">
			<div>
			<spring:bind path="workspace.description">		
			<textarea id="${status.expression}" name="${status.expression}" class="w100"	title="<ikep4j:message pre='${preDetail}' key='description' />" cols="" rows="5">${status.value}</textarea>
			<label for="${status.expression}" class="serverError"> ${status.errorMessage}</label>
			</spring:bind>
			</div>				
			</td>
		</tr>
		<tr>
			<th scope="row">&nbsp;<ikep4j:message pre='${preDetail}' key='tag' /></th>
			<td colspan="3"><input type="text" id="tag" name="tag" class="inputbox w100" title="<ikep4j:message pre='${preDetail}' key='tag' />" value="<c:forEach var="tag" items="${workspace.tagList}" varStatus="tagLoop"><c:if test="${tagLoop.count != 1}">, </c:if>${tag.tagName}</c:forEach>"/>
			<div class="tdInstruction"><ikep4j:message pre='${preDetail}' key='tagMessage' /></div>
			</td>
		</tr>
		</tbody>
	</table>

	
	
</div>
<input name="controlTabType" title="" type="hidden" value="0:0:0:0" />
<input name="controlType" title="" type="hidden" value="ORG" />
<input name="selectType" title="" type="hidden" value="GROUP" />
<input name="selectMaxCnt" title="" type="hidden" value="1" />
<input name="searchSubFlag" title="" type="hidden" value="" />
</form>
<!--//blockListTable End--> 
	 
<!--blockButton Start-->
<div class="blockButton">
<ul>
	<li><a id="saveWorkspaceButton" class="button" href="#a" title="<ikep4j:message pre='${preButton}' key='save'/>"><span><ikep4j:message pre='${preButton}' key='save'/></span></a></li>
	<li><a id="initWorkspaceButton" class="button" href="#a" title="<ikep4j:message pre='${preButton}' key='init'/>"><span><ikep4j:message pre='${preButton}' key='init'/></span></a></li>
</ul>
</div>
<!--//blockButton End-->		

</div>


