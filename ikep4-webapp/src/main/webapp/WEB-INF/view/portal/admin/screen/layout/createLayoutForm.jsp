<%@ include file="/base/common/taglibs.jsp"%>
<%@ page language="java" errorPage="/common/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ taglib uri="/WEB-INF/tld/ikep4j.tld" prefix="ikep4j" %> 

<c:set var="preAlert" value="message.portal.admin.screen.layout.createLayoutForm.alert"/>
<c:set var="preSearch" value="ui.portal.admin.screen.layout.createLayoutForm.search"/>
<c:set var="preList" value="ui.portal.admin.screen.layout.createLayoutForm.list"/>
<c:set var="preForm" value="ui.portal.admin.screen.layout.createLayoutForm.form"/>
<c:set var="preButton" value="ui.portal.admin.screen.layout.createLayoutForm.button"/>

<script type="text/javascript">
//<![CDATA[
(function($) {
	
	$jq(document).ready(function() {
		
		//left menu setting
		$jq("#portletLayoutLinkOfLeft").click();
		
		$jq("#mainForm:input:visible:enabled:first").focus();
		
		var url = "";
		
		<c:choose>
		<c:when test="${!empty portalLayout.layoutId}">
		url = "<c:url value='/portal/admin/screen/layout/updateLayout.do'/>";
		</c:when>
		<c:otherwise>
		url = "<c:url value='/portal/admin/screen/layout/createLayout.do'/>";
		</c:otherwise>
		</c:choose>
		
		/**
		 * Validation Logic Start
		 */
		var validOptions = {
			rules : {
				"layoutName" : {
					required : true,
					maxlength : 100
				},
				"layoutNum" : {
					digits : true,
					range : [1, 99999]
				},
				"description" : {
					maxlength : 250
				}
			},
			messages : {
				"layoutName" : {
					required : "<ikep4j:message key='NotEmpty.portalLayout.layoutName' />",
					maxlength : "<ikep4j:message key='Size.portalLayout.layoutName' />"
				},
				"layoutNum" : {
					digits : "<ikep4j:message key='Digits.portalLayout.layoutNum' />",
					range : "<ikep4j:message key='Range.portalLayout.layoutNum' />"
				},
				"description" : {
					maxlength : "<ikep4j:message key='Size.portalLayout.description' />"
				}
			},
			submitHandler : function(form) {
				/*
				form.action = "<c:url value='/portal/admin/screen/layout/createLayout.do'/>";
				form.submit();
				*/
				
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
		/*
		var mainForm = document.mainForm;
		
		mainForm.layoutName.value = '';
		mainForm.layoutNum.value = '';
		mainForm.description.value = '';
		*/
		
		$jq("#layoutName").val("");
		$jq("#layoutNum").val("");
		$jq("#description").val("");
		
	};
	
	save = function() {
		
		$jq("#mainForm").trigger("submit");
		
	};
	
	modify = function() {
		
		$jq("#mainForm").trigger("submit");
		
	};
	
	remove = function() {
		/*
		if(confirm("<ikep4j:message pre='${preAlert}' key='deleteConfirm' />")){
			var mainForm = document.mainForm;
			
			mainForm.action = '<c:url value="/portal/admin/screen/layout/removeLayout.do"/>';
			mainForm.submit();
		}
		*/
		
		if(confirm("<ikep4j:message pre='${preAlert}' key='confirmDelete' />")) {
			$jq.ajax({
				url : "<c:url value='/portal/admin/screen/layout/removeLayout.do' />",
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
	
	addForm = function() {
		
		//location.href = '<c:url value="createLayoutForm.do"/>';
		getList();
		
	};
	
	/*
	num_check = function(form_name) {
		
	 	if (isNaN(eval(form_name).value)) {
	  		alert ("<ikep4j:message pre='${preAlert}' key='onlyNumber' />");
	  		
	  		eval(form_name).value = "";
	  		eval(form_name).focus();
		}
	 	
	};
	*/
	
	view = function(layoutId) {
		/*
		location.href = '<c:url value="readLayout.do"/>?layoutId=' + layoutId;
		*/
		
		$jq.ajax({
			url : "<c:url value='/portal/admin/screen/layout/readLayout.do'/>",
			data : {
				layoutId : layoutId
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
		
		$jq("#searchForm").attr("action", "<c:url value='/portal/admin/screen/layout/createLayoutForm.do' />");
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
						<option value="layoutName" <c:if test="${'layoutName' eq status.value}">selected="selected"</c:if>><ikep4j:message pre="${preSearch}" key="${status.expression}" post="layoutName"/></option>
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
					<th scope="col" width="40%">
						<a href="#" onclick="sort('LAYOUT_NAME', '<c:if test="${searchCondition.sortColumn eq 'LAYOUT_NAME'}">${searchCondition.sortType}</c:if>'); return false;">
							<ikep4j:message pre="${preList}" key="layoutName" />
						</a>
					</th>
					<th scope="col" width="10%"><ikep4j:message pre="${preList}" key="tileCount" /></th>
					<th scope="col" width="40%"><ikep4j:message pre="${preList}" key="description" /></th>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="item" items="${searchResult.entity}" varStatus="i">
				<tr>
					<td class="textCenter">
						<c:out value="${i.count}"/>
					</td>
					<td class="textLeft">
						<a href="#" onclick="view('<c:out value='${item.layoutId}'/>'); return false;">
							<c:out value='${item.layoutName}'/>
						</a>
					</td>
					<td class="textCenter">
						<c:out value="${item.layoutNum}"/>
					</td>
					<td class="textLeft">
						<div class="ellipsis">
							<a href="#" onclick="view('<c:out value='${item.layoutId}'/>'); return false;">
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
		<input type="hidden" id="layoutId" name="layoutId" value="<c:out value='${portalLayout.layoutId}'/>"/>
							
			<table summary="<ikep4j:message pre='${preForm}' key='subTitle' />">
				<caption></caption>
				<colgroup>
					<col width="18%"/>
					<col width="82%"/>
				</colgroup>
				<tbody>						
					<!--layoutName Start-->
					<tr>
						<th>
							<span class="colorPoint">*</span><ikep4j:message pre="${preForm}" key="layoutName" />
						</th>
						<td class="textLeft">
							<div>
							<spring:bind path="portalLayout.layoutName">
							<input type="text" id="${status.expression}" name="${status.expression}" class="inputbox w100" value="${portalLayout.layoutName}"/>
							<label for="${status.expression}" class="serverError">${status.errorMessage}</label>
							</spring:bind>
							</div>
						</td>
					</tr>	
					<!--//layoutName End-->	
					
					<!--layoutNum Start-->
					<tr>
						<th>
							<ikep4j:message pre="${preForm}" key="tileCount" />
						</th>
						<td class="textLeft">
							<div>
							<spring:bind path="portalLayout.layoutNum">
							<input type="text" id="${status.expression}" name="${status.expression}" class="inputbox" value="${portalLayout.layoutNum}" />
							<label for="${status.expression}" class="serverError">${status.errorMessage}</label>
							</spring:bind>
							</div>
						</td>
					</tr>	
					<!--//layoutNum End-->	
					
					<!--description Start-->
					<tr>
						<th>
							<ikep4j:message pre="${preForm}" key="description" />
						</th>
						<td class="textLeft">
							<div>
							<spring:bind path="portalLayout.description">
							<input type="text" id="${status.expression}" name="${status.expression}" class="inputbox w100" value="${portalLayout.description}"/>
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
				<c:when test="${empty portalLayout.layoutId}">
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