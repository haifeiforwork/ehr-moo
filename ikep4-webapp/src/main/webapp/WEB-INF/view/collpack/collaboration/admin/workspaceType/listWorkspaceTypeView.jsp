<%--
=====================================================
	* 기능설명	:	유형 관리 목록
	* 작성자		:	김종철
=====================================================
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%-- Tab Lib Start --%> 
<%@ include file="/base/common/taglibs.jsp"%>
<%-- Tab Lib End --%> 


<%-- 메시지 관련 Prefix 선언 Start --%> 
<c:set var="preHeader"  value="message.collpack.collaboration.type.listType.header" />
<c:set var="preSearch"  value="message.collpack.collaboration.type.listType.search" />
<c:set var="preList"    value="message.collpack.collaboration.type.listType.list" />
<c:set var="preButton"  value="message.collpack.collaboration.type.listType.button" />
<c:set var="preScript"  value="message.collpack.collaboration.type.listType.script" />
<%-- 메시지 관련 Prefix 선언 End --%>

<%-- 세션정보 선언 Start --%> 
<c:set var="portal" value="${sessionScope['ikep.portal']}" />
<c:set var="user" value="${sessionScope['ikep.user']}" />
<%-- 세션정보 선언 End --%>


<script type="text/javascript" src="<c:url value='/base/js/jquery/plugins.pack.js'/>" ></script>

<script type="text/javascript">
<!-- 
var unselect;

(function($){
	
	$(document).ready(function() {
		

		/**
		 * Validation Logic Start
		 */
		
		var validOptions = {
			rules : {
				typeName : {
					required : true,
					maxlength : 200
				},
				typeEnglishName : {
					required : true,
					maxlength : 200
				},
				typeDescription :	{
					required : true,
					maxlength : 500
				},
				typeEnglishDescription :	{
					required : true,
					maxlength : 500
				}			
			},
			messages : {
				typeName : {
					direction	:	"bottom",
					required	:	"<ikep4j:message key="NotEmpty.type.typeName" />",
					maxlength	:	"<ikep4j:message key="Size.type.typeName" />"
				},
				typeEnglishName : {
					direction	:	"bottom",
					required	:	"<ikep4j:message key="NotEmpty.type.typeEnglishName" />",
					maxlength	:	"<ikep4j:message key="Size.type.typeEnglishName" />"
				},
				typeDescription : {
					direction	:	"bottom",
					required  : "<ikep4j:message key="NotEmpty.type.typeDescription" />",
					maxlength : "<ikep4j:message key="Size.type.typeDescription" />"
				}				,
				typeEnglishDescription : {
					direction	:	"bottom",
					required  : "<ikep4j:message key="NotEmpty.type.typeEnglishDescription" />",
					maxlength : "<ikep4j:message key="Size.type.typeEnglishDescription" />"
				}
			},
			submitHandler : function(form) {
				// 부가 검증 처리
				var typeName = $("input[name=typeName]", "#divWorkspaceTypeItemControll").val();
				var typeEnglishName = $("input[name=typeEnglishName]", "#divWorkspaceTypeItemControll").val();
				var typeDescription = $("textarea[name=typeDescription]", "#divWorkspaceTypeItemControll").val();
				var typeEnglishDescription = $("textarea[name=typeEnglishDescription]", "#divWorkspaceTypeItemControll").val();
				
				var lastCnt = $(".orderBox li:last-child").text();
				lastCnt++;

				switch(workspaceTypeItemAction) {
					case "add" :
						//ajax : save
						$.ajax({    
							url : "<c:url value="/collpack/collaboration/admin/workspaceType/createWorkspaceType.do"/>",     
							data : {typeName:typeName,typeEnglishName:typeEnglishName,typeDescription : typeDescription,typeEnglishDescription : typeEnglishDescription},     
							type : "post",     
							success : function(result) {    
								// ajax success callback process
								//$("#divWorkspaceTypeItemControll").dialog("close");
								document.location.href="<c:url value="/collpack/collaboration/admin/workspaceType/listWorkspaceTypeView.do"/>";
							},
							error : function(xhr, exMessage) {
							
								var errorItems = $.parseJSON(xhr.responseText).exception;
								validator.showErrors(errorItems);
							}
						});
						
						break;
					case "modify" :
						//ajax : save
						$.ajax({    
							url : "<c:url value="/collpack/collaboration/admin/workspaceType/createWorkspaceType.do"/>",     
							data : {typeName:typeName,typeEnglishName:typeEnglishName, typeDescription:typeDescription,typeEnglishDescription : typeEnglishDescription, typeId:currTypeId},     
							type : "post",     
							success : function(result) {    
								// ajax success callback process	
								$("#divWorkspaceTypeItemControll").dialog("close");
								document.location.href="<c:url value="/collpack/collaboration/admin/workspaceType/listWorkspaceTypeView.do"/>";
							},
							error : function(xhr, exMessage) {
							
								var errorItems = $.parseJSON(xhr.responseText).exception;
								validator.showErrors(errorItems);
							}
						});
						break;
				}
			}
		};


		var validator = new iKEP.Validator("#mainForm", validOptions);
		
		/**
		 * Validation Logic End
		 */			

		
		$("#workspaceType").sortable({			
			start: function(e,ui){
				clearSelect();
            },
			stop: function(e,ui){
				sortArray();
            },
            placeholder: "ui-state-highlight",
			revert:true
		});
		
		$("#workspaceType > li").live({
		    "click" : function(event){
		    	selectItem(event.target);
		    }
		});
		
		$( "#workspaceType" ).disableSelection();
		
		$("#createButton").click(function() {
			clearSelect();
			addWorkspaceType();
		});
		
		$("#modifyButton").click(function() {
			modifyWorkspaceType();
		});
		
		$("#deleteButton").click(function() {
			deleteWorkspaceType();
		});
		
		$("#cancelButton").click(function() {
			clearSelect();
			dialog.dialog("close");
		});
		
		$.template("tmplSortOrder", '<li class="ui-state-default">\${id}</li>');
		$.template("tmplWorkspaceTypeItem", '<li class="ui-state-default" id="\${typeId}"><span class="ui-icon ui-icon-arrowthick-2-n-s" name="\${name}"></span>\${name}</li>');
		$.template("tmplWorkspaceTypeName", '<span class="ui-icon ui-icon-arrowthick-2-n-s" name="\${name}"></span>\${name}');
		$.template("tmplWorkspaceTypeEnglishName", '<span class="ui-state-default" id="\${typeId}"></span>\${typeEnglishName}');
		$.template("tmplWorkspaceTypeItemDescription", '<li class="ui-state-default" id="\${typeId}">\${typeDescription}</li>');
		$.template("tmplWorkspaceTypeItemEnglishDescription", '<li class="ui-state-default" id="\${typeId}">\${typeEnglishDescription}</li>');
		
		$("#saveButton").click(function() {
			$jq("#mainForm").submit(); 	
			return false; 

		});
		
	});
	
	//sort완료 후 처리
	sortArray = function(){		
		$("#workspaceType").sortable("refresh");

		var result = $("#workspaceType").sortable("toArray");
		
		var resultText =result.join(",");

		$.ajax({    
			url : "<c:url value="/collpack/collaboration/admin/workspaceType/updateWorkspaceTypeOrder.do"/>",     
			data : {typeIds:resultText},     
			type : "post",     
			success : function(result) {    
				//alert("success");
				document.location.href="<c:url value="/collpack/collaboration/admin/workspaceType/listWorkspaceTypeView.do"/>";
			},
			error : function(event, request, settings){
			 	alert("Error");
			}
		}); 
	}
	
	selectItem = function(item) {
		unselect();
		if(isToggle(item)){
			clearSelect();
		}else{
			$(item).addClass("ui-selected");
			currTypeId = $(item).attr("id");
			showBtn();
		}
	}
	
	isToggle = function(item){		
		if(currTypeId == null){
			return false;			
		}else{
			if(currTypeId == $(item).attr("id")){
				return true;
			}else{
				return false;
			}
		}
	}
	
	showBtn = function(){
		$("#modifyLi").show();
		$("#deleteLi").show();
	}
	
	hideBtn = function(){
		$("#modifyLi").hide();
		$("#deleteLi").hide();
	}
	
	unselect = function() {		
		$("#workspaceType > li").removeClass("ui-selected");
	}
	
	addWorkspaceType = function(){
		showWorkspaceTypeItemDialog("add");
	}
	
	modifyWorkspaceType = function(){
		showWorkspaceTypeItemDialog("modify");
	}
	
	deleteWorkspaceType = function(){
	
		// 하위 카테고리 존재유무 확인후 삭제
		$.ajax({    
			url : "<c:url value="/collpack/collaboration/admin/category/existsCategory.do"/>",     
			data : {typeId:currTypeId},     
			type : "get",     
			success : function(result) {    
				if(!result) {
					if(confirm("<ikep4j:message pre='${preScript}' key='delete' />")) {
						document.location.href="<c:url value="/collpack/collaboration/admin/workspaceType/deleteWorkspaceType.do"/>?typeId="+currTypeId;
					}
				}
				else {
					alert("<ikep4j:message pre='${preScript}' key='existsCategory' />");
					return false;
				}
					
			},
			error : function(event, request, settings){
			 	alert("<ikep4j:message pre='${preScript}' key='existsCategoryError' />");
			}
		});	
		
		/****
		if(!confirm('<ikep4j:message pre='${preScript}' key='confirmMessage' />'))
			return false;
		else {
			document.location.href="<c:url value="/collpack/collaboration/admin/workspaceType/deleteWorkspaceType.do"/>?typeId="+currTypeId;
		}
		**/
	}
	
	clearSelect = function(){
		currTypeId = null;
		unselect();
		hideBtn();
	}
	
	var currTypeId = null;
	var dialog = null;
	var workspaceTypeItemAction = "";
	
	function showWorkspaceTypeItemDialog(flag) {
		
		workspaceTypeItemAction = flag;
		var dialogTitle = workspaceTypeItemAction == "add" ? "<ikep4j:message pre='${preList}' key='addType' />" : "<ikep4j:message pre='${preList}' key='modifyType' />";
		
		if(dialog == null) {
			dialog = $("#divWorkspaceTypeItemControll").dialog({
				title : dialogTitle,
				modal : true,
				resizable : false,
				width : 450,
				height : 300,
				open : function() {

					switch(workspaceTypeItemAction) {
						case "add" :
							$("input[name=typeName]", this).val("");
							$("input[name=typeEnglishName]", this).val("");
							$("textarea[name=typeDescription]", this).val("");
							$("textarea[name=typeEnglishDescription]", this).val("");
							break;
						case "modify" :
							var typeName = $("#"+currTypeId+" span:first-child").attr("name");
							var typeEnglishName = $("#eng"+currTypeId+" span:first-child").attr("name");
							var typeDescription = $("#desc"+currTypeId+" span:first-child").attr("name");
							var typeEnglishDescription = $("#engdesc"+currTypeId+" span:first-child").attr("name");
							
							$("input[name=typeName]", this).val(typeName);
							$("input[name=typeEnglishName]", this).val(typeEnglishName);
							$("textarea[name=typeDescription]", this).val(typeDescription);
							$("textarea[name=typeEnglishDescription]", this).val(typeEnglishDescription);
							break;
					}
				}
			});
		} else {
			if(dialog.dialog("isOpen")) 
				dialog.dialog("close");
			
			dialog.dialog("option", "title", dialogTitle)
				.dialog("open");
		}
		
	}
	
})(jQuery);

//-->
</script>


<h1 class="none"><ikep4j:message pre="${preHeader}" key="pageTitle" /></h1> 

<!--pageTitle Start-->

<div id="pageTitle"> 
	<h2><ikep4j:message pre="${preHeader}" key="pageTitle" /></h2> 

</div> 

<!--//pageTitle End-->


<!--blockListTable Start-->


<div id="divWorkspaceTypeItemControll" style="display:none;">
<form id="mainForm" name="mainForm" method="post" onsubmit="return false;" action="" > 
<input type="hidden" name="typeId" id="typeId" value="" />
	<!--blockDetail Start-->
	<div class="blockDetail">
		<table summary="<ikep4j:message pre="${preList}" key="summaryType" />">
			<caption></caption>
			<tbody>
				<tr>
					<th scope="row" width="28%"><ikep4j:message pre='${preList}' key='typeName' /></th>
					<td width="*">
						<div>
						<spring:bind path="workspaceType.typeName">
						<input type="text" name="${status.expression}" id="${status.expression}" value="${status.value}" title="<ikep4j:message pre='${preList}' key='typeName' />" class="inputbox w95"  />
						<label for="${status.expression}" class="serverError">${status.errorMessage}</label>
						</spring:bind>
						</div>
					</td>
				</tr>
				<tr>
					<th scope="row" width="28%"><ikep4j:message pre='${preList}' key='typeEnglishName' /></th>
					<td width="*">
						<div>
						<spring:bind path="workspaceType.typeEnglishName">
						<input type="text" name="${status.expression}" id="${status.expression}"" value="${status.value}" title="<ikep4j:message pre='${preList}' key='typeEnglishName' />" class="inputbox w95"  />
						<label for="${status.expression}" class="serverError">${status.errorMessage}</label>
						</spring:bind>
						</div>
					</td>
				</tr>
				<tr>
					<th scope="row" width="28%"><ikep4j:message pre='${preList}' key='typeDescription' /></th>
					<td width="*">
						<div>
						<spring:bind path="workspaceType.typeDescription">
						<textarea name="${status.expression}" id="${status.expression}" class="w95" title="<ikep4j:message pre='${preList}' key='typeDescription'/>" cols="" rows="3"></textarea>
						<label for="${status.expression}" class="serverError">${status.errorMessage}</label>
						</spring:bind>
						</div>
					</td>
				</tr>	
				<tr>
					<th scope="row" width="28%"><ikep4j:message pre='${preList}' key='typeEnglishDescription' /></th>
					<td width="*">
						<div>
						<spring:bind path="workspaceType.typeEnglishDescription">
						<textarea name="${status.expression}" id="${status.expression}" class="w95" title="<ikep4j:message pre='${preList}' key='typeDescription'/>" cols="" rows="3"></textarea>
						<label for="${status.expression}" class="serverError">${status.errorMessage}</label>
						</spring:bind>
						</div>
					</td>
				</tr>											
			</tbody>
		</table>
	</div>
	<!--//blockDetail End-->
	
	<!--tableBottom Start-->
	<div class="tableBottom">										
		<!--blockButton Start-->
		<div class="blockButton"> 
			<ul>
				<li><a id="saveButton" class="button" href="#a" title="<ikep4j:message pre='${preButton}' key='save'/>"><span><ikep4j:message pre='${preButton}' key='save'/></span></a></li>
				<li><a id="cancelButton" class="button" href="#a" title="<ikep4j:message pre='${preButton}' key='cancel'/>"><span><ikep4j:message pre='${preButton}' key='cancel'/></span></a></li>
			</ul>
		</div>
		<!--//blockButton End-->
	</div>
	<!--//tableBottom End-->
	</form>
</div>


<div class="blockListTable">
	<div class="blockTableInfo"><ikep4j:message pre='${preHeader}' key='pageMessage' /></div>
	<div class="blockDetail clear">
		<table summary="<ikep4j:message pre="${preList}" key="summary" />">
		<caption></caption>
		<col style="width: 4%;"/>
		<col style="width: 15%;"/>
		<col style="width: 15%;"/>			
		<col style="width: 33%;"/>
		<col style="width: 33%;"/>
		<tbody>
			<tr>
				<th scope="col" class="textCenter"><ikep4j:message pre='${preList}' key='no' /></th>
				<th scope="col" class="textCenter"><ikep4j:message pre='${preList}' key='typeName' /></th>
				<th scope="col" class="textCenter"><ikep4j:message pre='${preList}' key='typeEnglishName' /></th>
				<th scope="col" class="textCenter"><ikep4j:message pre='${preList}' key='typeDescription' /></th>	
				<th scope="col" class="textCenter"><ikep4j:message pre='${preList}' key='typeEnglishDescription' /></th>					
			</tr>
		</tbody>						
		</table>
		<div style="min-width:700px;width:100%;">

			<div style="float:left;width:4%;">
				<ul class="orderBox"><li class="ui-state-default">1</li></ul>
			</div>

			<div style="float:left;width:15%;">
				<ul class="list-selectable">
				<c:forEach var="workspaceTypeList" items="${workspaceTypeList}" varStatus="status">
				<c:if test="${workspaceTypeList.isOrganization==1}">
				<li class="ui-state-default">${workspaceTypeList.typeName}</li>
				</c:if>
				</c:forEach>	
				</ul>
			</div>
			<div style="float:left;width:15%;">
				<ul class="list-selectable">
				<c:forEach var="workspaceTypeList" items="${workspaceTypeList}" varStatus="status">
				<c:if test="${workspaceTypeList.isOrganization==1}">
				<li class="ui-state-default">${workspaceTypeList.typeEnglishName}</li>
				</c:if>
				</c:forEach>	
				</ul>
			</div>
			<div style="float:left;width:33%;">
				<ul class="list-selectable">
				<c:forEach var="workspaceTypeList" items="${workspaceTypeList}" varStatus="status">
				<c:if test="${workspaceTypeList.isOrganization==1}">
				<li class="ui-state-default">${workspaceTypeList.typeDescription}</li>
				</c:if>
				</c:forEach>	
				</ul>				
			</div>		
			<div style="float:left;width:33%;">
				<ul class="list-selectable">
				<c:forEach var="workspaceTypeList" items="${workspaceTypeList}" varStatus="status">
				<c:if test="${workspaceTypeList.isOrganization==1}">
				<li class="ui-state-default">${workspaceTypeList.typeEnglishDescription}</li>
				</c:if>
				</c:forEach>	
				</ul>				
			</div>	
				
			<div class="clear"></div>
		</div>
				
		<div style="min-width:700px;width:100%;">

			<div style="float:left;width:4%;">
				<ul class="orderBox">
				<c:forEach begin="2" end="${size}" varStatus="status">
					<li class="ui-state-default">${status.count+1}</li>
				</c:forEach>
				</ul>
			</div>

			<div style="float:left;width:15%;">
				<ul class="list-selectable" id="workspaceType">				
				<c:forEach var="workspaceTypeList" items="${workspaceTypeList}" varStatus="status">
				<c:if test="${workspaceTypeList.isOrganization!=1}">
					<li class="ui-state-default" id="${workspaceTypeList.typeId}"><span class="ui-icon ui-icon-arrowthick-2-n-s" name="${workspaceTypeList.typeName}"></span>${workspaceTypeList.typeName}</li>
				</c:if>				
				</c:forEach>	
				</ul>	
			</div>
			<div style="float:left;width:15%;">
				<ul class="list-selectable">				
				<c:forEach var="workspaceTypeList" items="${workspaceTypeList}" varStatus="status">
				<c:if test="${workspaceTypeList.isOrganization!=1}">
					<li class="ui-state-default" id="eng${workspaceTypeList.typeId}"><span name="${workspaceTypeList.typeEnglishName}"></span>${workspaceTypeList.typeEnglishName}</li>
				</c:if>				
				</c:forEach>	
				</ul>	
			</div>			
			<div style="float:left;width:33%;">
				<ul class="list-selectable">				
				<c:forEach var="workspaceTypeList" items="${workspaceTypeList}" varStatus="status">
				<c:if test="${workspaceTypeList.isOrganization!=1}">
					<li class="ui-state-default" id="desc${workspaceTypeList.typeId}"><span name="${workspaceTypeList.typeDescription}"></span>${workspaceTypeList.typeDescription}</li>
				</c:if>				
				</c:forEach>	
				</ul>	
			</div>	
			<div style="float:left;width:33%;">
				<ul class="list-selectable">				
				<c:forEach var="workspaceTypeList" items="${workspaceTypeList}" varStatus="status">
				<c:if test="${workspaceTypeList.isOrganization!=1}">
					<li class="ui-state-default" id="engdesc${workspaceTypeList.typeId}"><span name="${workspaceTypeList.typeEnglishDescription}"></span>${workspaceTypeList.typeEnglishDescription}</li>
				</c:if>				
				</c:forEach>	
				</ul>	
			</div>			
			<div class="clear"></div>
		</div>
	</div>	
	
	
</div>

<!--//blockListTable End--> 
	 
<!--blockButton Start-->
<div class="blockButton">
<ul>
	<li><a id="createButton" class="button" href="#a" title="<ikep4j:message pre='${preButton}' key='create'/>"><span><ikep4j:message pre='${preButton}' key='create'/></span></a></li>
	<li id="modifyLi" style="display:none;"><a id="modifyButton" class="button" href="#a" title="<ikep4j:message pre='${preButton}' key='modify'/>"><span><ikep4j:message pre='${preButton}' key='modify'/></span></a></li>
	<li id="deleteLi" style="display:none;"><a id="deleteButton" class="button" href="#a" title="<ikep4j:message pre='${preButton}' key='delete'/>"><span><ikep4j:message pre='${preButton}' key='delete'/></span></a></li>
</ul>
</div>
<!--//blockButton End-->		
