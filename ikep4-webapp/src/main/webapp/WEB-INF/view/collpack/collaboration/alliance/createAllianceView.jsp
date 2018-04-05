<%--
=====================================================
	* 기능설명	:	동맹 요청
	* 작성자		:	김종철
=====================================================
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%-- Tab Lib Start --%> 
<%@ include file="/base/common/taglibs.jsp"%>
<%-- Tab Lib End --%> 

<%-- 메시지 관련 Prefix 선언 Start --%> 
<c:set var="preHeader"  value="message.collpack.collaboration.alliance.createAlliance.header" />
<c:set var="preSearch"  value="message.collpack.collaboration.alliance.createAlliance.search" />
<c:set var="preCommon"  value="message.collpack.common" />
<c:set var="preDetail"  value="message.collpack.collaboration.alliance.createAlliance.detail" />
<c:set var="preButton"  value="message.collpack.collaboration.alliance.createAlliance.button" />
<c:set var="preScript"  value="message.collpack.collaboration.alliance.createAlliance.script" />
<%-- 메시지 관련 Prefix 선언 End --%>

<%-- 세션정보 선언 Start --%> 
<c:set var="portal" value="${sessionScope['ikep.portal']}" />
<c:set var="user" value="${sessionScope['ikep.user']}" />
<%-- 세션정보 선언 End --%>


<script type="text/javascript" src="<c:url value='/base/js/jquery/jquery.jstree.pack.js'/>"></script>

<script type="text/javascript">
<!--


var dialogWindow = null;
var fnCaller;

var tab, $groupTree;
var $treeNode;

(function($) {
	 
	fnCaller = function (params, dialog) {
		if(params) {
			if(params.items) {
				appendItem(params.items);
			}
			if(params.search) {
				$jq("#treesch").val(params.search);
			}
		}		
		dialogWindow = dialog;
		$jq("#cancelButton").click(function() {	
			//parent.location.href="<c:url value="/collpack/collaboration/alliance/listAllianceView.do"/>?workspaceId=${searchCondition.workspaceId}&listType=${parentListType}";
			dialogWindow.close();
		});
		
	};
	
	
	// onload시 수행할 코드
	$(document).ready(function() { 
	
		// save Button Hide
		$jq("#saveLi").hide();
		
		// Tab 
		<c:forEach var="workspaceTypeList" items="${workspaceTypeList}" varStatus="status">		
			<c:if test="${empty searchCondition.listType && status.index==0}">
			$("#divTabs").tabs({selected : 0});
			changeTab('${workspaceTypeList.typeId}','${workspaceTypeList.isOrganization}','${workspaceTypeList.typeName}');
			</c:if>
		
			<c:if test="${searchCondition.listType==workspaceTypeList.typeId}">
			$("#divTabs").tabs({selected : ${status.index}});
			changeTab('${workspaceTypeList.typeId}','${workspaceTypeList.isOrganization}','${workspaceTypeList.typeName}');
			</c:if>		
		</c:forEach>
		
		// change Tab
		<c:forEach var="workspaceTypeList" items="${workspaceTypeList}" varStatus="status">
			$jq("#tabs-${workspaceTypeList.typeId}").click(function() {
				location.href='<c:url value="/collpack/collaboration/alliance/createAllianceView.do"/>?workspaceId=${searchCondition.workspaceId}&listType=${workspaceTypeList.typeId}&parentListType=${parentListType}';
			}); 
		</c:forEach>
		
		$jq("#tabs-searchgroup").click(function() {
			$jq("#workspaceTree").empty();
			$jq("#workspaceTree").addClass("none");
			$jq("#tabSearchgroup").removeClass("none");			
		}); 
		
				
		// Save
		$jq("#saveButton").click(function() {			
			$jq("#mainForm").submit(); 		
			return false; 
		});

		/**
		 * Validation Logic Start
		 */
		
		var validOptions = {
			rules : {
				requestReason :	{
					required : true,
					maxlength : 500
				}
			},
			messages : {
				requestReason : {
					direction	:	"bottom",
					required	:	"<ikep4j:message key="NotEmpty.alliance.requestReason" />",
					maxlength	:	"<ikep4j:message key="Size.alliance.requestReason" />"
					
				}
			},
			submitHandler : function(form) {
				// 부가 검증 처리
				
				$jq.ajax({
					url : "<c:url value='/collpack/collaboration/alliance/createAlliance.do' />",
					type : "post",
					data : $jq(form).serialize(),
					success : function(result) {
						alert('<ikep4j:message pre='${preScript}' key='success' />');
						//$jq("#saveLi").hide();	
						$jq("#cancelButton").click();	
					},
					error : function(xhr, exMessage) {
						var errorItems = $jq.parseJSON(xhr.responseText).exception;
						validator.showErrors(errorItems);
					}
				});
								
				
			}
			
		};


		var validator = new iKEP.Validator("#mainForm", validOptions);

		/**
		 * Validation Logic End
		 */		

		$("#btnSearch").click(function(){

			var keyword = $("#schKeyword").val();

			if(!keyword) {
				alert("<ikep4j:message pre='${preScript}' key='noSearchWord' />");
				return false;
			}

			//tab.tabs('select', '#tab-searchgroup');
			$("#divSearchResult").jstree("destroy").empty();
			$("#divSearchResult").unbind("dblclick");
			$("#divSearchResult").unbind("click");
			
			$.post("<c:url value='/collpack/collaboration/workspace/listWorkspaceBySearch.do'/>", {keyword:keyword})
				.success(function(result) {
				
					if(result.items.length > 0) {
						$("#divSearchResult").jstree({
								plugins : [ "themes", "json_data", "ui" ],
								json_data : { data : iKEP.arrayToTreeData(result.items) },
								themes : {dots:false}
						})
						.delegate("a[href$='#']", "click", function (e) { // clicking node text expands and contracts it
						
							e.preventDefault();
							this.blur();
				
							var $groupData = $jq.parseJSON( $jq(this).parent().attr("data"));
							if($groupData.groupTypeId==0){
								getWorkspaceInfo(1,$groupData.code);
							} else {
								getWorkspaceInfo(0,$groupData.code);
							}
						});
								
					}
				})
				.error(function(event, request, settings) { alert("error"); });
		});
		$("#schKeyword").bind("keypress", function(event) {
			if(event.which == 13) {
				$("#btnSearch").trigger("click");
			}
		});
	});

	changeTab = function(typeId, isOrganization, typeName, categoryId) {

		$jq("#workspaceTree").empty();
		
		/** workspace 트리**/	
		$("#workspaceTree").bind("loaded.jstree", function (event, data) {
			var $TopItem = $("ul > li:first", this);
			$("#workspaceTree").jstree("open_node", $TopItem.children("a")[0], false);
		});
		
		var url =null
		
		if(isOrganization==0){
		
			$treeNode = $("#workspaceTree").jstree({	// 맵 트리 생성
				plugins : [ "themes" ,"json_data", "ui"],
				json_data : {
						data : iKEP.arrayToTreeData([{
									"groupTypeId"	:typeId,
									"name"			:typeName,
									"hasChild"		:"1",
									"parent"		:categoryId,
									"code"			:categoryId,
									"type"			:"group",
									"workspaceId"	:"",
									"workspaceName" :"",
									"sysopName":"",
									"openDate":"",
									"groupName":"",
									"hasWorkspace"	:""
								}]),
						ajax : {
								type: "POST",
								url : "<c:url value='/collpack/collaboration/workspace/listWorkspaceByType.do'/>",
								dataType: "json",
								data : function ($el) {	//$el : current item
									return { 
									 "operation"		:	"get_children",
									 "groupTypeId"		:	typeId,
									 "typeId"			:	typeId,
									 "categoryId"		:	$el.attr("code"),
									 "isOrganization"	:	isOrganization,
									 "hasWorkspace"		:	$el.attr("hasWorkspace")
									};								
								},
								success : function(data) {												
									return iKEP.arrayToTreeData(data.items);
								},
								error : function(data) {
									alert('Error')
								}
							 }
					}
				}).delegate("a[href$='#']", "click", function (e) { // clicking node text expands and contracts it
					e.preventDefault();
					this.blur();
					
					var $groupData = $jq.parseJSON( $jq(this).parent().attr("data"));

					if(isOrganization==0){
						$("#workspaceTree").jstree("toggle_node", this, false, true);
					}
					getWorkspaceInfo(isOrganization,$groupData.code);

				});
		} else {
			$treeNode = $("#workspaceTree").jstree({	// 맵 트리 생성
				plugins : [ "themes" ,"json_data", "ui"],
				json_data : {
						data : iKEP.arrayToTreeData(${rootString}),
						ajax : {
								type: "POST",
								url : "<c:url value='/collpack/collaboration/workspace/listWorkspaceByType.do'/>",
								dataType: "json",
								data : function ($el) {	//$el : current item
									return { 
									 "operation"		:	"get_children",
									 "groupTypeId"		:	typeId,
									 "typeId"			:	typeId,
									 "categoryId"		:	$el.attr("code"),
									 "isOrganization"	:	isOrganization,
									 "hasWorkspace"		:	$el.attr("hasWorkspace")
									};
									
								},
								success : function(data) {
													
									return iKEP.arrayToTreeData(data.items);
								},
								error : function(data) {
									alert('Error')
								}
							 }
					}
				}).delegate("a[href$='#']", "click", function (e) { // clicking node text expands and contracts it
					e.preventDefault();
					this.blur();
					
					var $groupData = $jq.parseJSON( $jq(this).parent().attr("data"));

					if(isOrganization==0){
						$("#workspaceTree").jstree("toggle_node", this, false, true);
					}
					getWorkspaceInfo(isOrganization,$groupData.code);

				});		
		
		}
	};
	
	// 조직이외의 wokspace 클릭시 기본 정보 Load 및 동맹정보 체크
	getWorkspaceInfo= function(isOrganization,toWorkspaceId){

		$jq.get('<c:url value="/collpack/collaboration/alliance/readAllianceView.do"/>',
				{workspaceId : '${searchCondition.workspaceId}', toWorkspaceId : toWorkspaceId,isOrganization:isOrganization},
				function(data){
					if(data.workspaceId)
					{
						if($jq("input[name=workspaceId]").val()==data.workspaceId)
						{
							$jq("#workspaceName>span").text('');
							$jq("input[name=toWorkspaceId]").val('');
							$jq("textarea[name=requestReason]").val('');
							$jq("#groupName>span").text('');
							$jq("#sysopName>span").text('');
							$jq("#openDate>span").text('');
							alert('<ikep4j:message pre='${preScript}' key='sameWorkspace' />');
							return;
						}
						else{

							$jq("#workspaceName>span").text(data.workspaceName);
							$jq("input[name=toWorkspaceId]").val(data.workspaceId);
							$jq("textarea[name=requestReason]").val(data.requestReason);
							$jq("#groupName>span").text(data.groupName);
							$jq("#sysopName>span").text(data.sysopName);
							$jq("#openDate>span").text(data.strDate);
							if(data.allianceId==null || data.allianceId=="")
								$jq("#saveLi").show();
							else
								$jq("#saveLi").hide();
						}
					}
					else{
						$jq("#workspaceName>span").text('');
						$jq("input[name=toWorkspaceId]").val('');
						$jq("textarea[name=requestReason]").val('');
						$jq("#groupName>span").text('');
						$jq("#sysopName>span").text('');
						$jq("#openDate>span").text('');					
						$jq("#saveLi").hide();
						return;
						
					}
				}
			)
			return false;
	}
	
})(jQuery);  
//-->
</script>


<h1 class="none"><ikep4j:message pre="${preHeader}" key="pageTitle" /></h1> 

<!--pageTitle Start-->

<!--div id="pageTitle"> 
	<h2><ikep4j:message pre="${preHeader}" key="pageTitle" /></h2> 
	<div id="pageLocation">
		<ul>
			<li class="liFirst"><ikep4j:message pre="${preHeader}" key="pageLocation.1depth" /></li>
			<li><ikep4j:message pre="${preHeader}" key="pageLocation.2depth" /></li>
			<li class="liLast"><ikep4j:message pre="${preHeader}" key="pageLocation.3depth" /></li>
		</ul>
	</div>
</div-->
<!--//pageTitle End-->


<div id="divTabs" class="iKEP_tab">     
	<ul>
		<c:forEach var="workspaceTypeList" items="${workspaceTypeList}" varStatus="status">
		<li><a href="#tab-${status.count}" id="tabs-${workspaceTypeList.typeId}">${workspaceTypeList.typeName}</a></li>
		</c:forEach>
		<li><a href="#tab-searchgroup" id="tabs-searchgroup">검색</a></li>
	</ul>
	<c:forEach var="workspaceTypeList" items="${workspaceTypeList}" varStatus="status">
	<div id="tab-${status.count}" style="padding: 0px;"></div>
	</c:forEach>
	
	<div id="tab-searchgroup" style="padding: 0px;"></div>
</div>	
	
<!--blockLeft Start-->		
<div class="blockLeft" style="width:40%">
	<div class="leftTree treeBox"  style="overflow:auto;overflow-x:hidden;height:250px;">
		<!--div><img src="<c:url value="/base/images/common/img_title_cate.gif"/>" alt="category" /></div-->
		<div id="workspaceTree"></div>
		
		<div id="tabSearchgroup" class="shuttleTree none">
			<div class="shuttleSearch">
			<input type="text" title="<ikep4j:message pre='${preSearch}' key='search'/>" class="inputbox" id="schKeyword" value="" size="20" />
			<a id="btnSearch" class="ic_search" href="#"><span><ikep4j:message pre='${preSearch}' key='search'/></span></a> 
			&nbsp;&nbsp;&nbsp;
			</div>
			<div id="divSearchResult"></div>
		</div>		
	</div>
</div>	
<!--//blockLeft End-->



<div class="blockRight" style="width:55%">
	<!--blockDetail Start-->					
	<div class="blockDetail">
	<form id="mainForm" name="mainForm" method="post" action="<c:url value='/collpack/collaboration/alliance/createAlliance.do' />">  
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
	<spring:bind path="searchCondition.toWorkspaceId">
	<input name="${status.expression}" type="hidden" value="${status.value}" title="<ikep4j:message pre='${preSearch}' key='${status.expression}'/>" />
	</spring:bind> 	
		<table summary="<ikep4j:message pre='${preDetail}' key='limitSize'/>">
			<caption></caption>
			<tbody>
				<tr>
					<th scope="col" width="28%"><ikep4j:message pre='${preDetail}' key='workspaceName'/></th>
					<td class="textLeft" id="workspaceName"><span></span>
					</td>
				</tr>
				<tr>
					<th scope="col"><ikep4j:message pre='${preDetail}' key='groupName'/></th>
					<td id="groupName"><span></span>
					</td>
				</tr>
				<tr>
					<th scope="col"><ikep4j:message pre='${preDetail}' key='sysopName'/></th>
					<td id="sysopName"><span></span></td>
				</tr>
				<tr>
					<th scope="col"><ikep4j:message pre='${preDetail}' key='openDate'/></th>
					<td id="openDate"><span></span></td>
				</tr>
				<tr>
					<th scope="col"><ikep4j:message pre='${preDetail}' key='requestReason'/></th>
					<td id="">
						<div>
						<spring:bind path="alliance.requestReason">
						<textarea id="${status.expression}" name="${status.expression}" onclick="" class="w90" title="<ikep4j:message pre='${preDetail}' key='requestReason'/>" cols="" rows="5"></textarea>
						<label for="${status.expression}" class="serverError"> ${status.errorMessage}</label>
						</spring:bind>
						</div>
					</td>
				</tr>
			</tbody>
		</table>
	</form>
	</div>
	<!--//blockDetail End-->
	
	<!--blockButton Start-->
	<div class="blockButton suttle_btn"> 
		<ul>
			<li id="saveLi"><a id="saveButton" class="button" href="#a" title="<ikep4j:message pre='${preButton}' key='save'/>"><span><ikep4j:message pre='${preButton}' key='save'/></span></a></li>
			<li><a id="cancelButton" class="button" href="#a" title="<ikep4j:message pre='${preButton}' key='cancel'/>"><span><ikep4j:message pre='${preButton}' key='cancel'/></span></a></li>
		</ul>
	</div>
	<!--//blockButton End-->

</div>





