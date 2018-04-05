<%--
=====================================================
	* 기능설명	:	workspace 내용 수정화면
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
(function($) {
	var orgWorkspaceName = null;
	var checkName = false;
	// form submit : select box Selected 
	selectedALL = function (selectName, isSelected){
		var $sel = $jq("select[name="+selectName+"]");
		
		if($sel) {
			$jq.each($sel.children(), function() {
				this.selected = isSelected;
			});
		}
	};
		
	
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
		}, 100);
	};
	
	setAddress = function(data) {
		var $sel = $jq("select[name=memberIds]")
			.children().remove()
			.end();
		$jq.each(data, function() {
			this.addrType = "TO";
			$.tmpl(iKEP.template.userOption, this)
				.appendTo($sel)
				.data("data", this);
		})
	};
	
	setAddressInit = function() {
		
		var $sel = $jq("select[name=memberIds]")
			.children().remove()
			.end();
		
		var addrObj;
		
		<c:forEach var="memberList" items="${workspace.memberList}">
			addrObj = $.parseJSON('{"id":"${memberList.memberId}","userName":"${memberList.memberName}","jobTitleName":"${memberList.jobTitleName}","teamName":"${memberList.teamName}"}');
			$.tmpl(iKEP.template.userOption, addrObj)
				.appendTo($sel)
				.data("data", addrObj);
		</c:forEach>
	};
	
	// onload시 수행할 코드
	$jq(document).ready(function() { 
		
		iKEP.iFrameContentResize();  
		
		changeType('${workspace.typeId}');
		
		$jq("#listWorkspaceButton").click(function() {
			//$jq('form[name=mainForm]').attr({
			//	action:"<c:url value="/collpack/collaboration/workspace/${searchCondition.listUrl}"/>",
			//	method:"GET"
			//});
			//$jq("#mainForm").submit(); 		
			
			document.location.href="<c:url value="/collpack/collaboration/workspace/${searchCondition.listUrl}"/>?listType=${searchCondition.listType}";
		});
		
        //파일업로드 버튼 이벤트 바인딩
		$jq('#fileuploadBtn').click(function(event) {

			//파일업로드 팝업창
			//파라미터(이벤트객체이름,에디터에서사용여부(0:일반,1:에디터에서),이미지여부(0:모든파일,1:이미지파일만 가능))
			iKEP.fileUpload(event.target.id,'0','1');

		});
        

		var dbItems = [];
		<c:forEach var="memberList" items="${workspace.memberList}" varStatus="status">
		dbItems.push({// 공통필수
			type:"user",				
			id:"${memberList.memberId }",
			jobTitle:"${memberList.jobTitleName }",
			name:"${memberList.memberName }",
			searchSubFlag:false,
			teamName:"${memberList.teamName }"
		});
		</c:forEach>
		
		var items = [];
		var $sel = $jq("#mainForm").find("[name=memberIds]");
		$sel.children().each(function(idx) {
			$jq.data(this, "data", dbItems[idx]);
		});
		
		
		$jq("#addMemberButton").click(function() {
			var items = [];
			$jq("select[name=memberIds]").children().each(function() {
				items.push($(this).data("data"));
			});

			iKEP.showAddressBook(setAddress, items, {selectType:"all", tabs:{group:0,common:0,personal:0,collaboration:1,sns:0}});
		});
		
		$jq("#delMemberButton").click(function(event) {
			event.preventDefault();
			var $emailList=$jq('#memberIds');
			$jq('option:selected',$emailList).remove();
		});

		$jq("#dupWorkspaceButton").click(function() {
			checkWorkspaceName(); 
		});
		
		
		$jq("#saveWorkspaceButton").click(function() {
			$jq("#mainForm").submit();
		});
		
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

			},
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
					alert('<ikep4j:message pre='${preScript}' key='checkWorkspaceName' />');
					return false;
				}
				// tag Check
				var tagNames = $jq("#mainForm").find('input[name=tag]').val();
				
				if(iKEP.tagging.lengthCheck(tagNames)){
					alert('<ikep4j:message pre='${preScript}' key='tagLength' />');
					return false;
				}

				// select Box Selected
				selectedALL("memberIds",true)
				
				if(!confirm("<ikep4j:message pre='${preCommon}' key='save' />")) {
					return;
				}
				
				$.ajax({
					url : "<c:url value='/collpack/collaboration/workspace/updateWorkspace.do' />",
					type : "post",
					data : $(form).serialize(),
					loadingElement : {container:"#pageLodingDiv"},
					success : function(result) {
						location.href= "<c:url value='/collpack/collaboration/workspace/readWorkspaceView.do'/>?workspaceId="+result+"&listUrl=${searchCondition.listUrl}&listType=${searchCondition.listType}"; //조회화면으로 포워딩
					},
					error : function(xhr, exMessage) {
						selectedALL("memberIds",false)
						
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
		
		setAddressInit();
	   
		setUser = function(data) {
			if(data=="")			{
				var searchVal = $jq('#userName').val();
				alert('['+searchVal+'] 검색어로 검색된 사용자가 없습니다.');
			} else {
				var $select = $jq("#memberIds");
				var duplicationUsers = [];
				$jq.each(data, function() {
					var hasOption = $select.find('option[value="'+this.id+'"]');

					if(hasOption.length > 0){
						duplicationUsers.push(this.userName);
					} else {
						$.tmpl(iKEP.template.userOption, this).appendTo($select)
							.data("data", this);
					}	
				})
				
				$jq("#totalMember").text("<ikep4j:message key='ui.servicepack.survey.common.total' />"+$select.children().length+"<ikep4j:message key='ui.servicepack.survey.common.totalMemberMsg' />");
				
				if(duplicationUsers.length > 0) alert("[" + duplicationUsers.join(",") + "] " + iKEPLang.searchUserGroup.duplicateUser);
				
				$jq('#userName').val('');
			}
		};
		//입력박스에 값을 입력하고 EnterKey를 눌렀을때 이벤트 바인딩
        $jq('#userName').keypress( function(event) {
            //파라미터(이벤트객체, 멀티선택팝업여부(Y/N), 콜백메소드)
            if(event.which == '13') {
				iKEP.searchUser(event, "Y", setUser);	
            }
		});	
		//검색버튼을 클릭했을때 이벤트 바인딩
		$jq('#userSearchBtn').click( function() {
		    //입력박스에 이벤트를 발생시켜서, 위와 동일한 작업을 하도록 함
			$jq('#userName').trigger("keypress");
 
		});
	});
	
	// java script 전역변수 항목에 추가
	$jq.template("addrBookItemUser", "<option value='\${id}'>\${name}/\${jobTitle}/\${teamName}</option>");
	$jq.template("addrBookItemGroup", "<option value='\${code}'>\${name}</option>");
	
})(jQuery);  
//-->
</script>

<div id="pageLodingDiv">

<h1 class="none"><ikep4j:message pre="${preHeader}" key="pageTitle" /></h1> 

<!--pageTitle Start-->

<div id="pageTitle"> 
	<h2>${workspace.typeName} 개설 정보</h2> 

</div> 

<!--//pageTitle End-->  
<!--blockListTable Start-->
<form id="mainForm" name="mainForm" method="post" action="<c:url value='/collpack/collaboration/workspace/updateWorkspace.do' />">  
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

<input name="workspaceIds" id="workspaceIds" type="hidden" value="${workspace.workspaceId}"/>


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
			<td>${workspace.registerName}</td>
			<th scope="row"><ikep4j:message pre='${preDetail}' key='registDate' /></th>
			<td><ikep4j:timezone pattern="yyyy.MM.dd" date="${workspace.registDate}"/></td>
		</tr>
		
		<tr style="display:none">	
			<th scope="row"><ikep4j:message pre='${preDetail}' key='typeId' /></th>
			<td colspan="3">
			<c:forEach var="workspaceTypeList" items="${workspace.workspaceTypeList}" varStatus="status">
			<input type="radio" class="radio" id="typeId" name="typeId" value="${workspaceTypeList.typeId}" <c:if test="${workspaceTypeList.typeId==workspace.typeId}">checked</c:if> onclick="changeType('${workspaceTypeList.typeId}');" /> ${workspaceTypeList.typeName} 
			</c:forEach>	
			</td>
		</tr>

		<tr id="CATEGORY_SELECT" style="display: ;">
			<th scope="row"><span id="span_category_text"><font	color='#990000'>*</font></span><ikep4j:message pre='${preDetail}' key='selectCategory' /></th>
			<td colspan="3"><select name="categoryId" id="categoryId">		
			</select></td>
		</tr>		
		<tr>
			<th scope="row"><font	color='#990000'>*</font>${workspace.typeName} 명</th>
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
			<c:forEach var="fileDataList" items="${workspace.fileDataList}" varStatus="fileLoop">
			<img id='viewImageDiv' src='<c:url value="/support/fileupload/downloadFile.do"/>?fileId=${fileDataList.fileId}' width='110' height='90'/>
			</c:forEach>
			</span>	
			<a class="button" href="#a" >
				<span name="fileuploadBtn" id="fileuploadBtn"><ikep4j:message pre='${preButton}' key='introImage' /></span>
			</a>
			</td>
		</tr>
		<tr>
			<th scope="row"><ikep4j:message pre='${preDetail}' key='member' /></th>
			<td colspan="3">
			<input name="userName" id="userName" type="text" class="inputbox" size="20" title="userName" />
			<a name="userSearchBtn" id="userSearchBtn" class="button_ic" href="#a"><span><img src="<c:url value='/base/images/icon/ic_btn_search.gif' />" alt="" /><ikep4j:message pre='${preButton}' key='search' /></span></a>
			<a id="addMemberButton" class="button_s" href="#a" title="<ikep4j:message pre='${preButton}' key='addMember'/>"><span><ikep4j:message pre='${preButton}' key='addMember'/></span></a>
			<div style="padding-top:4px;"> 			
			<select name="memberIds" id="memberIds" class="input_select w80" style="height:100px;" multiple>
			</select>
			<a id="delMemberButton" class="button_s" href="#a" title="<ikep4j:message pre='${preButton}' key='delMember'/>"><span><ikep4j:message pre='${preButton}' key='delMember'/></span></a>
			</div>
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
<input name="selectType" title="" type="hidden" value="USER" />
<input name="selectMaxCnt" title="" type="hidden" value="100" />
<input name="searchSubFlag" title="" type="hidden" value="" />
</form>
<!--//blockListTable End--> 
	 
<!--blockButton Start-->
<div class="blockButton">
<ul>
	<li><a id="saveWorkspaceButton" class="button" href="#a" title="<ikep4j:message pre='${preButton}' key='save'/>"><span><ikep4j:message pre='${preButton}' key='save'/></span></a></li>
	<li><a id="listWorkspaceButton" class="button" href="#a" title="<ikep4j:message pre='${preButton}' key='list'/>"><span><ikep4j:message pre='${preButton}' key='list'/></span></a></li>
</ul>
</div>
<!--//blockButton End-->		

</div>


