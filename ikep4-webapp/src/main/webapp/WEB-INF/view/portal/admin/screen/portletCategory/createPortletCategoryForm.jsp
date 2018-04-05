<%@ include file="/base/common/taglibs.jsp"%>
<%@ page language="java" errorPage="/common/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ taglib uri="/WEB-INF/tld/ikep4j.tld" prefix="ikep4j" %> 

<c:set var="preAlert" value="message.portal.admin.screen.portletCategory.createPortletCategoryForm.alert" />
<c:set var="preSearch" value="ui.portal.admin.screen.portletCategory.createPortletCategoryForm.search" />
<c:set var="preList" value="ui.portal.admin.screen.portletCategory.createPortletCategoryForm.list"/>
<c:set var="preForm" value="ui.portal.admin.screen.portletCategory.createPortletCategoryForm.form"/>
<c:set var="preButton" value="ui.portal.admin.screen.portletCategory.createPortletCategoryForm.button"/>

<script type="text/javascript">
//<![CDATA[
(function($) {
	
	$jq(document).ready(function() {
		
		//left menu setting
		$jq("#portletGroupLinkOfLeft").click();
		
		$jq("#mainForm:input:visible:enabled:first").focus();
		
		var url = "";
		
		<c:choose>
		<c:when test="${!empty portalPortletCategory.portletCategoryId}">
		url = "<c:url value='/portal/admin/screen/portletCategory/updatePortletCategory.do' />";
		</c:when>
		<c:otherwise>
		url = "<c:url value='/portal/admin/screen/portletCategory/createPortletCategory.do' />";
		</c:otherwise>
		</c:choose>
		
		/**
		 * Validation Logic Start
		 */
		var validOptions = {
			rules : {
				"portletCategoryName" : {
					required : true,
					maxlength : 100
				},
				"description" : {
					maxlength : 250
				}
			},
			messages : {
				"portletCategoryName" : {
					required : "<ikep4j:message key='NotEmpty.portalPortletCategory.portletCategoryName' />",
					maxlength : "<ikep4j:message key='Size.portalPortletCategory.portletCategoryName' />"
				},
				"description" : {
					maxlength : "<ikep4j:message key='Size.portalPortletCategory.description' />"
				}
			},
			submitHandler : function() {
				
				$jq.ajax({
					url : url,
					data : $jq("#mainForm").serialize(),
					type : "post",
					success : function(result) {
						alert("<ikep4j:message pre='${preAlert}' key='saveSuccess' />");
						
						$jq("#tempId").val(result);
						
						getList();
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
	
	cancel = function() {
		
		$jq("#portletCategoryName").val("");
		$jq("#description").val("");
		
	};
	
	save = function() {
		
		$jq("#mainForm").trigger("submit");
		
	};
	
	addForm = function() {
		
		getList();
		
	};
	
	modify = function() {
		
		$jq("#mainForm").trigger("submit");
		
	};
	
	remove = function() {
		
		if(confirm("<ikep4j:message pre='${preAlert}' key='confirmDelete' />")) {
			$jq.ajax({
				url : "<c:url value='/portal/admin/screen/portletCategory/removePortletCategory.do' />",
				data : $jq("#mainForm").serialize(),
				type : "post",
				success : function(result) {
					alert("<ikep4j:message pre='${preAlert}' key='deleteSuccess' />");
					
					$jq("#tempId").val(result);
					
					getList();
				},
				error : function() { 
					alert("<ikep4j:message pre='${preAlert}' key='deleteFail' />");
				}
			});
		} else {
			return;
		}
		
	};
	
	view = function(portletCategoryId) {
		
		$jq.ajax({
			url : "<c:url value='/portal/admin/screen/portletCategory/readPortletCategory.do'/>",
			data : {
				portletCategoryId : portletCategoryId
			},
			type : "post",
			success : function(result) {
				$jq("#viewDiv").html(result);
			},
			error : function(event, request, settings) { 
				alert("<ikep4j:message pre='${preAlert}' key='error' />");
			}
		});
		
	};
	
	getList = function() {
		
		$jq("#searchForm").attr("action", "<c:url value='/portal/admin/screen/portletCategory/createPortletCategoryForm.do' />");
		$jq("#searchForm")[0].submit();
		
	};
	
	sort = function(sortColumn, sortType) {
		
		$jq("input[name=sortColumn]").val(sortColumn);
		
		if(sortType == 'ASC') {
			
			$jq("input[name=sortType]").val('DESC');	
		} else {
			
			$jq("input[name=sortType]").val('ASC');
		}
		
		getList();
		
	};
	
})(jQuery);
//]]>
</script>

<!--blockListTable Start-->
<div class="blockListTable">

	<div id="listDiv">
		<form id="searchForm" name="searchForm" method="post" action="" onsubmit="getList(); return false;">
		<input type="hidden" id="tempId" name="tempId" value="" />
		
		<spring:bind path="searchCondition.sortColumn">
			<input name="${status.expression}" type="hidden" value="${status.value}"/>
		</spring:bind>
		<spring:bind path="searchCondition.sortType">
			<input name="${status.expression}" type="hidden" value="${status.value}"/>
		</spring:bind>
		
		<!--tableTop Start-->  
		<div class="tableTop">  
			<div class="tableTop_bgR"></div>
			<h2><ikep4j:message pre="${preList}" key="title" /></h2>
			<div class="listInfo">  
				<spring:bind path="searchCondition.pagePerRecord">  
					<select name="${status.expression}" title="<ikep4j:message pre='${preSearch}' key='${status.expression}' />" onchange="getList(); return false;">
					<c:forEach var="code" items="${portalCode.pageNumList}">
						<option value="${code.key}" <c:if test="${code.key eq status.value}">selected="selected"</c:if>>${code.value}</option>
					</c:forEach> 
					</select> 
				</spring:bind>
				<div class="totalNum">
					<ikep4j:message pre="${preSearch}" key="pageCount.info"/>
					<span>${searchResult.recordCount}</span>
				</div>
			</div>
			<div class="tableSearch"> 
				<spring:bind path="searchCondition.searchColumn">  
					<select name="${status.expression}" title="<ikep4j:message pre='${preSearch}' key='${status.expression}' />">
						<option value="portletCategoryName" <c:if test="${'portletCategoryName' eq status.value}">selected="selected"</c:if>><ikep4j:message pre="${preSearch}" key="${status.expression}" post="portletCategoryName"/></option>
					</select>		
				</spring:bind>		
				<spring:bind path="searchCondition.searchWord">  					
					<input name="${status.expression}" value="${status.value}" type="text" class="inputbox" title="<ikep4j:message pre='${preSearch}' key='${status.expression}' />" size="20"/>
				</spring:bind>
				<a href="#" id="searchBoardItemButton" name="searchBoardItemButton" onclick="getList(); return false;" class="ic_search">
					<span><ikep4j:message pre="${preSearch}" key="search" /></span>
				</a> 
			</div>	
			<div class="clear"></div>	
		</div>
		<!--//tableTop End-->	
		
		<table summary="<ikep4j:message pre='${preList}' key='tableSummary' />">
			<caption></caption>
			<thead>
				<tr>
					<th scope="col" width="10%"><ikep4j:message pre="${preList}" key="order" /></th>
					<th scope="col" width="30%">
						<a href="#" onclick="sort('A.PORTLET_CATEGORY_NAME', '<c:if test="${searchCondition.sortColumn eq 'A.PORTLET_CATEGORY_NAME'}">${searchCondition.sortType}</c:if>'); return false;">
							<ikep4j:message pre="${preList}" key="groupName" />
						</a>
					</th>
					<th scope="col" width="30%"><ikep4j:message pre="${preList}" key="systemName" /></th>
					<th scope="col" width="30%"><ikep4j:message pre="${preList}" key="description" /></th>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="item" items="${searchResult.entity}" varStatus="i">
				<tr>
					<td class="textCenter">
						<c:out value="${i.count}"/>
					</td>
					<td class="textLeft">
						<a href="#" onclick="view('<c:out value='${item.portletCategoryId}'/>'); return false;">
							<c:out value='${item.portletCategoryName}'/>
						</a>
					</td>
					<td class="textCenter">
						<a href="#" onclick="view('<c:out value='${item.portletCategoryId}'/>'); return false;">
							<c:out value="${item.systemName}"/>
						</a>						
					</td>
					<td class="textLeft">
						<div class="ellipsis">
							<a href="#" onclick="view('<c:out value='${item.portletCategoryId}'/>'); return false;">
								<c:out value="${item.description}"/>
							</a>
						</div>
					</td>
				</tr>
				</c:forEach>
				
				<c:if test="${empty searchResult.entity}">
				<tr>
					<td colspan="4" class="textCenter">
						<ikep4j:message pre="${preSearch}" key="emptyRecord" />
					</td>
				</tr>
				</c:if>			
			</tbody>
		</table>
		
		<!--Page Number Start--> 
		<spring:bind path="searchCondition.pageIndex">
			<ikep4j:pagination searchFormId="searchForm" ajaxEventFunctionName="getList" pageIndexInput="${status.expression}" searchCondition="${searchCondition}" />
			<input name="${status.expression}" type="hidden" value="${status.value}" title="<ikep4j:message pre='${preSearch}' key='${status.expression}'/>" />
		</spring:bind> 
		<!--//Page Number End-->
		
		</form>
			
	</div>

</div>
<!--//blockListTable End-->

<div class="blockBlank_10px"></div>

<!--subTitle_2 Start-->
<div class="subTitle_2 noline">
	<h3><ikep4j:message pre="${preForm}" key="subTitle" /></h3>
</div>
<!--//subTitle_2 End--> 

<!--blockDetail Start-->
<div class="blockDetail">

	<div id="viewDiv">
		<form id="mainForm" name="mainForm" action="" method="post" onsubmit="return false;">  
		<input type="hidden" id="portletCategoryId" name="portletCategoryId" value="<c:out value='${portalPortletCategory.portletCategoryId}'/>"/>
			
			<table summary="<ikep4j:message pre='${preForm}' key='subTitle' />">
				<caption></caption>
				<colgroup>
					<col width="18%"/>
					<col width="82%"/>
				</colgroup>
				<tbody>						
					<!--portletCategoryName Start-->
					<tr>
						<th>
							<span class="colorPoint">*</span><ikep4j:message pre="${preForm}" key="groupName" />
						</th>
						<td class="textLeft">
							<div>
							<spring:bind path="portalPortletCategory.portletCategoryName">
							<input type="text" id="${status.expression}" name="${status.expression}" class="inputbox w100" value="${portalPortletCategory.portletCategoryName}"/>
							<label for="${status.expression}" class="serverError">${status.errorMessage}</label>
							</spring:bind>
							</div>
						</td>
					</tr>	
					<!--//portletCategoryName End-->	
					
					<!--systemCode Start-->
					<tr>
						<th>
							<ikep4j:message pre="${preForm}" key="system" />
						</th>
						<td class="textLeft">
							<select id="systemCode" name="systemCode">
								<c:forEach var="item" items="${portalSystemMapList}">
								<c:if test="${item.systemCode != '0'}">
									<option value="<c:out value='${item.systemCode}'/>" <c:if test="${item.systemCode == portalPortletCategory.systemCode}">selected="selected"</c:if>><c:out value="${item.systemName}"/></option>
								</c:if>
								</c:forEach>
							</select>
						</td>
					</tr>	
					<!--//systemCode End-->	
					
					<!--description Start-->
					<tr>
						<th>
							<ikep4j:message pre="${preForm}" key="description" />
						</th>
						<td class="textLeft">
							<div>
							<spring:bind path="portalPortletCategory.description">
							<input type="text" id="${status.expression}" name="${status.expression}" class="inputbox w100" value="${portalPortletCategory.description}"/>
							<label for="${status.expression}" class="serverError">${status.errorMessage}</label>
							</spring:bind>
							</div>
						</td>
					</tr>	
					<!--//description End-->	
				</tbody>
			</table>
			
		</form>
		
		<div class="blockBlank_10px"></div>
		
		<!--blockButton Start-->
		<div class="blockButton">
			<ul>
				<c:choose>
				<c:when test="${empty portalPortletCategory.portletCategoryId}">
				<li><a name="saveButton" class="button" href="#" onclick="save(); return false;"><span><ikep4j:message pre="${preButton}" key="save" /></span></a></li>
				<li><a name="cancelButton" class="button" href="#" onclick="cancel(); return false;"><span><ikep4j:message pre="${preButton}" key="cancel" /></span></a></li>
				</c:when>
				<c:otherwise>
				<li><a name="addFormButton" class="button" href="#" onclick="addForm(); return false;"><span><ikep4j:message pre="${preButton}" key="new" /></span></a></li>
				<li><a name="modifyButton" class="button" href="#" onclick="modify(); return false;"><span><ikep4j:message pre="${preButton}" key="modify" /></span></a></li>
				<li><a name="removeButton" class="button" href="#" onclick="remove(); return false;"><span><ikep4j:message pre="${preButton}" key="delete" /></span></a></li>
				</c:otherwise>
				</c:choose>
			</ul>
		</div>
		<!--//blockButton End-->
				
	</div>

</div>
<!--//blockDetail End--> 