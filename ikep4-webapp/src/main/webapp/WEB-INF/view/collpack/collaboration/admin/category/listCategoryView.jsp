<%--
=====================================================
	* 기능설명	:	카테고리 관리 목록
	* 작성자		:	김종철
=====================================================
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%-- Tab Lib Start --%> 
<%@ include file="/base/common/taglibs.jsp"%>
<%-- Tab Lib End --%> 

<%-- 메시지 관련 Prefix 선언 Start --%> 
<c:set var="preHeader"  value="message.collpack.collaboration.category.listCategory.header" />
<c:set var="preSearch"  value="message.collpack.collaboration.category.listCategory.search" />
<c:set var="preCommon"  value="message.collpack.common" />
<c:set var="preList"    value="message.collpack.collaboration.category.listCategory.list" />
<c:set var="preButton"  value="message.collpack.collaboration.category.listCategory.button" />
<c:set var="preScript"  value="message.collpack.collaboration.category.listCategory.script" />
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
				categoryName : {
					required : true,
					maxlength : 200
				},
				categoryEnglishName : {
					required : true,
					maxlength : 200
				}				
			},
			messages : {
				categoryName : {
					direction	:	"bottom",
					required	:	"<ikep4j:message key="NotEmpty.category.categoryName" />",
					maxlength	:	"<ikep4j:message key="Size.category.categoryName" />"
				},
				categoryEnglishName : {
					direction	:	"bottom",
					required	:	"<ikep4j:message key="NotEmpty.category.categoryEnglishName" />",
					maxlength	:	"<ikep4j:message key="Size.category.categoryEnglishName" />"
				}
			},
			submitHandler : function(form) {
				// 부가 검증 처리
				var categoryName = $("input[name=categoryName]", "#divCategoryItemControll").val();
				var categoryEnglishName = $("input[name=categoryEnglishName]", "#divCategoryItemControll").val();
				var typeId = $("input[name=typeId]", "#divCategoryItemControll").val();

				var lastCnt = $(".orderBox li:last-child").text();
				lastCnt++;
				
				switch(categoryItemAction) {
					case "add" :
						//ajax : save

						$jq.ajax({    
							url : "<c:url value="/collpack/collaboration/admin/category/createCategory.do"/>",     
							data : {typeId:typeId,categoryName:categoryName,categoryEnglishName:categoryEnglishName},     
							type : "post",     
							success : function(result) {    
								$("#divCategoryItemControll").dialog("close");
								document.location.href="<c:url value="/collpack/collaboration/admin/category/listCategoryView.do"/>?typeId=${searchCondition.typeId}";

							},
							error : function(xhr, exMessage) {
							
								var errorItems = $.parseJSON(xhr.responseText).exception;
								validator.showErrors(errorItems);
							}
						});
						
										
						break;
					case "modify" :
						//ajax : save

						$jq.ajax({    
							url : "<c:url value="/collpack/collaboration/admin/category/createCategory.do"/>",     
							data : {typeId:typeId,categoryName:categoryName,categoryEnglishName:categoryEnglishName, categoryId:currCategoryId},     
							type : "post",     
							success : function(result) {    
								// ajax success callback process	
								$("#divCategoryItemControll").dialog("close");	
								document.location.href="<c:url value="/collpack/collaboration/admin/category/listCategoryView.do"/>?typeId=${searchCondition.typeId}";

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
		 		
		
		$("#categorys").sortable({			
			start: function(e,ui){
				clearSelect();
            },
			stop: function(e,ui){
				sortArray();
            },
            placeholder: "ui-state-highlight",
			revert:true
		});
		
		$("#categorys > li").live({
		    "click" : function(event){
		    	selectItem(event.target);
		    }
		});
		
		$( "#categorys" ).disableSelection();
		
		$("#createButton").click(function() {
			clearSelect();
			addCatetory();
		});
		
		$("#modifyButton").click(function() {
			modifyCatetory();
		});
		
		$("#deleteButton").click(function() {
			deleteCatetory();
		});
		
		$("#cancelButton").click(function() {
			clearSelect();
			dialog.dialog("close");
		});
		
		$.template("tmplCategoryOrder", '<li class="ui-state-default">\${id}</li>');
		$.template("tmplCategoryItem", '<li class="ui-state-default" id="\${categoryId}"><span class="ui-icon ui-icon-arrowthick-2-n-s" name="\${name}"></span>\${name}</li>');
		$.template("tmplCategoryName", '<span class="ui-icon ui-icon-arrowthick-2-n-s" name="\${name}"></span>\${name}');
		
		$("#saveButton").click(function() {
			<c:if test="${!empty typeName}">
			$jq("#mainForm").submit(); 
			</c:if>
			<c:if test="${empty typeName}">
			alert('<ikep4j:message pre='${preScript}' key='typeNameError' />');
			</c:if>				
			return false; 

		});
		
	});
		
	//sort완료 후 처리
	sortArray = function(){		
		$("#categorys").sortable("refresh");
		//iKEP.debug($("#categorys").sortable("serialize"));
		var result = $("#categorys").sortable("toArray");
		
		var typeId = $("input[name=typeId]", "#divCategoryItemControll").val();
		var resultText =result.join(",");
		
		$jq.ajax({    
			url : "<c:url value="/collpack/collaboration/admin/category/updateCategoryOrder.do"/>",     
			data : {categoryIds:resultText},     
			type : "post",     
			success : function(result) {    
				document.location.href="<c:url value="/collpack/collaboration/admin/category/listCategoryView.do"/>?typeId=${searchCondition.typeId}";
			},
			error : function(event, request, settings){
			 	alert("<ikep4j:message pre='${preScript}' key='sortError' />");
			}
		}); 
	}
	
	selectItem = function(item) {
		unselect();
		if(isToggle(item)){
			clearSelect();
		}else{
			$(item).addClass("ui-selected");
			currCategoryId = $(item).attr("id");
			showBtn();
		}
	}
	
	isToggle = function(item){		
		if(currCategoryId == null){
			return false;			
		}else{
			if(currCategoryId == $(item).attr("id")){
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
		$("#categorys > li").removeClass("ui-selected");
	}
	
	addCatetory = function(){
		showCategoryItemDialog("add");
	}
	
	modifyCatetory = function(){
		showCategoryItemDialog("modify");
	}
	
	deleteCatetory = function(){
	
	
		// 하위 Workspace 존재유무 확인후 삭제
		$.ajax({    
			url : "<c:url value="/collpack/collaboration/admin/category/existsWorkspace.do"/>",     
			data : {categoryId:currCategoryId},     
			type : "get",     
			success : function(result) {    
				if(!result) {
					if(confirm("<ikep4j:message pre='${preScript}' key='delete' />")) {
						document.location.href="<c:url value="/collpack/collaboration/admin/category/deleteCategory.do"/>?categoryId="+currCategoryId;
					}
				}else {
					alert("<ikep4j:message pre='${preScript}' key='existsWorkspace' />");
					return false;
				}					
			},
			error : function(event, request, settings){
			 	alert("<ikep4j:message pre='${preScript}' key='existsWorkspaceError' />");
			}
		});			
		
		//document.location.href="<c:url value="/collpack/collaboration/admin/category/deleteCategory.do"/>?categoryId="+currCategoryId;
	}
	
	clearSelect = function(){
		currCategoryId = null;
		unselect();
		hideBtn();
	}
	
	var currCategoryId = null;
	var dialog = null;
	var categoryItemAction = "";
	function showCategoryItemDialog(flag) {
		categoryItemAction = flag;
		var dialogTitle = categoryItemAction == "add" ? "<ikep4j:message pre='${preList}' key='addCategory' />" : "<ikep4j:message pre='${preList}' key='modifyCategory' />";
		
		if(dialog == null) {
			dialog = $("#divCategoryItemControll").dialog({
				title : dialogTitle,
				modal : true,
				resizable : false,
				width : 450,
				height : 200,
				open : function() {
					switch(categoryItemAction) {
						case "add" :
							$("input[name=categoryName]", this).val("");
							$("input[name=categoryEnglishName]", this).val("");
							break;
						case "modify" :
							var categoryName = $("#"+currCategoryId+" span:first-child").attr("title");
							var categoryEnglishName = $("#eng"+currCategoryId+" span:first-child").attr("title");
							$("input[name=categoryName]", this).val(categoryName);
							$("input[name=categoryEnglishName]", this).val(categoryEnglishName);
							break;
					}
				}
			});
		} else {
			if(dialog.dialog("isOpen")) dialog.dialog("close");
			
			dialog.dialog("option", "title", dialogTitle)
				.dialog("open");
			
		}
		
	}
	
	searchList	= function(typeId) {
		document.location.href="<c:url value="/collpack/collaboration/admin/category/listCategoryView.do"/>?typeId="+typeId;
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

<div id="divCategoryItemControll" style="display:none;">
	<form id="mainForm" name="mainForm" method="post" onsubmit="return false;" action="">  
	<input type="hidden" name="categoryId" id="categoryId" value=""/>

	<spring:bind path="searchCondition.typeId">
	<input name="${status.expression}" type="hidden" value="${status.value}" title="<ikep4j:message pre='${preSearch}' key='${status.expression}'/>" />
	</spring:bind>
	
		<!--blockDetail Start-->
		<div class="blockDetail">
			<table summary="<ikep4j:message pre="${preList}" key="summaryCreateCategory" />">
				<caption></caption>
				<tbody>
					<tr>
						<th scope="row" width="28%"><ikep4j:message pre='${preList}' key='typeId' /></th>
						<td width="*">${typeName}</td>
					</tr>
					<tr>
						<th scope="row" width="28%"><ikep4j:message pre='${preList}' key='categoryName' /></th>
						<td width="*">
							<div>
							<spring:bind path="category.categoryName">
							<input name="${status.expression}" id="${status.expression}" title="<ikep4j:message pre='${preList}' key='categoryName' />" class="inputbox w95" type="text" />
							<label for="${status.expression}" class="serverError"> ${status.errorMessage}</label>
							</spring:bind>
							</div>						
						</td>
					</tr>
					<tr>
						<th scope="row" width="28%"><ikep4j:message pre='${preList}' key='categoryEnglishName' /></th>
						<td width="*">
							<div>
							<spring:bind path="category.categoryEnglishName">						
							<input name="${status.expression}" id="${status.expression}" title="<ikep4j:message pre='${preList}' key='categoryEnglishName' />" class="inputbox w95" type="text" />
							<label for="${status.expression}" class="serverError"> ${status.errorMessage}</label>
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

<!--blockSearch Start-->
<div class="blockSearch">
	<div class="corner_RoundBox03">
		<form action="">
		<table summary="<ikep4j:message pre="${preSearch}" key="search" />">
			<caption></caption>
			<tbody>
				<tr>
					<th width="5%" scope="row"><ikep4j:message pre='${preList}' key='typeId' /></th>
					<td width="95%">
					<c:forEach var="workspaceTypeList" items="${workspaceTypeList}" varStatus="status">							
						<label><input type="radio" onclick="searchList('${workspaceTypeList.typeId}')" class="radio" title="${workspaceTypeList.typeId}" 
						<c:if test="${workspaceTypeList.typeId==searchCondition.typeId }">checked="checked"</c:if> name="sel2" value=""  />
						<c:choose>
						<c:when test="${user.localeCode == portal.defaultLocaleCode}">
							${workspaceTypeList.typeName}
						</c:when>
						<c:otherwise>
							${workspaceTypeList.typeEnglishName}
						</c:otherwise>
						</c:choose>	
						</label>&nbsp;
					</c:forEach>
					</td>								
				</tr>														
			</tbody>
		</table>
		</form>
		<div class="l_t_corner"></div>
		<div class="r_t_corner"></div>
		<div class="l_b_corner"></div>
		<div class="r_b_corner"></div>				
	</div>
</div>	
<!--//blockSearch End-->

	<div class="blockTableInfo"><ikep4j:message pre='${preHeader}' key='pageMessage' /></div>
	<div class="blockDetail clear">
		<table summary="<ikep4j:message pre="${preList}" key="summaryCategory" />">
		<caption></caption>
		<col style="width: 18%;"/>
		<col style="width: 32%;"/>
		<col style="width: 50%;"/>
		<tbody>
			<tr>
				<th scope="col" class="textCenter"><ikep4j:message pre='${preList}' key='no' /></th>
				<th scope="col" class="textCenter"><ikep4j:message pre='${preList}' key='categoryName' /></th>
				<th scope="col" class="textCenter"><ikep4j:message pre='${preList}' key='categoryEnglishName' /></th>
			</tr>
		</tbody>						
		</table>

		<div style="min-width:700px;width:100%;">

			<div style="float:left;width:18%;">
				<ul class="orderBox">
				<c:forEach begin="1" end="${size}" varStatus="status">
					<li class="ui-state-default">${status.count}</li>
				</c:forEach>
				</ul>
			</div>
			
		
			<div style="float:left;width:32%;">
				<ul class="list-selectable" id="categorys">
				<c:forEach var="categoryList" items="${categoryList}" varStatus="status">
					<li class="ui-state-default" id="${categoryList.categoryId}"><span class="ui-icon ui-icon-arrowthick-2-n-s" title="${categoryList.categoryName}"></span>${categoryList.categoryName}</li>	
				</c:forEach>	
				</ul>
			</div>
			<div style="float:left;width:50%;">
				<ul class="list-selectable">
				<c:forEach var="categoryList" items="${categoryList}" varStatus="status">
					<li class="ui-state-default" id="eng${categoryList.categoryId}"><span title="${categoryList.categoryEnglishName}"></span>${categoryList.categoryEnglishName}</li>	
				</c:forEach>	
				</ul>
			</div>			
			<div class="clear"></div>
		</div>
	</div>	
	
	 
<!--blockButton Start-->
<div class="blockButton">
<ul>
	<li><a id="createButton" class="button" href="#a" title="<ikep4j:message pre='${preButton}' key='create'/>"><span><ikep4j:message pre='${preButton}' key='create'/></span></a></li>
	<li id="modifyLi" style="display:none;"><a id="modifyButton" class="button" href="#a" title="<ikep4j:message pre='${preButton}' key='modify'/>"><span><ikep4j:message pre='${preButton}' key='modify'/></span></a></li>
	<li id="deleteLi" style="display:none;"><a id="deleteButton" class="button" href="#a" title="<ikep4j:message pre='${preButton}' key='delete'/>"><span><ikep4j:message pre='${preButton}' key='delete'/></span></a></li>
</ul>
</div>
<!--//blockButton End-->

