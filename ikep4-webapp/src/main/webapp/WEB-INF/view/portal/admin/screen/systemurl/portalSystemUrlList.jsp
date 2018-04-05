<%@ include file="/base/common/taglibs.jsp"%>
<%@ page language="java" errorPage="/common/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ taglib uri="/WEB-INF/tld/ikep4j.tld" prefix="ikep4j" %> 

<c:set var="preButton" value="ui.portal.admin.screen.systemurl.portalSystemUrlList.button"/>
<c:set var="preMain" value="ui.portal.admin.screen.systemurl.portalSystemUrlList.main"/>
<c:set var="preForm" value="ui.portal.admin.screen.systemurl.portalSystemUrlList.form"/>
<c:set var="preAlert" value="message.portal.admin.screen.systemurl.portalSystemUrlList.alert"/>

<c:set var="preList" value="ui.portal.admin.screen.systemurl.portalSystemUrlList.list"/>
<c:set var="preSearch" value="ui.portal.admin.screen.systemurl.portalSystemUrlList.search" />

<script type="text/javascript">
//<![CDATA[
// 상단에 보이게 될 리스트를 가져오는 함수
function getList() {
	
	$jq("#searchForm").attr("action", "<c:url value='portalSystemUrlList.do' />");
	$jq("#searchForm")[0].submit();
	
}

// 하단에 보이게 될 시스템 URL 등록 페이지를 가져오는 함수
function getForm(serializeData) {

	$jq.ajax({
		url : "<c:url value='portalSystemUrlForm.do'/>",
		data : serializeData,
		type : "post",
		success : function(result) {
			$jq("#viewDiv").html(result);
		},
		error : function(event, request, settings) { 
			alert("<ikep4j:message pre='${preAlert}' key='error' />");
		}
	});
	
	//showCreateButton();
	
}

// 하단에 보이게 될 시스템 URL 수정 페이지를 가져오는 함수
function getSystemUrl(systemUrlId, fieldName, itemTypeCode, tr) {
	
	var selectClassName = "bgSelected";
	
	$jq(tr).parent().parent().children().removeClass(selectClassName).end()
			.end().end().addClass(selectClassName);
	
	$jq.ajax({
		url : "<c:url value='portalSystemUrlForm.do'/>",
		data : {
			systemUrlId : systemUrlId, 
			fieldName : fieldName,
			itemTypeCode : itemTypeCode
		},
		type : "post",
		success : function(result) {
			$jq("#viewDiv").html(result);
		},
		error : function(event, request, settings) { 
			alert("<ikep4j:message pre='${preAlert}' key='error' />");
		}
	});
	
}

// 목록 정렬
function sort(sortColumn, sortType) {
	
	$jq("input[name=sortColumn]").val(sortColumn);
	
	if(sortType == 'ASC') {
		
		$jq("input[name=sortType]").val('DESC');	
	} else {
		
		$jq("input[name=sortType]").val('ASC');
	}
	
	getList();
	
};

function resetInputForm() {

	$jq("input[name=url]").val("");
	
	<c:forEach var="i18nMessage" items="${portalSystemUrl.i18nMessageList}" varStatus="loopStatus">
	$jq("input[id=${i18nMessage.localeCode}]").val("");
	</c:forEach>
	$jq("#portalSystemUrlForm").validate().resetForm();
	
}

(function($) {
	
	$jq(document).ready(function() {
		
		//left menu setting
		$jq("#systemUrlLinkOfLeft").click();
		
		$jq("#portalSystemUrlForm:input:visible:enabled:first").focus();
		
		$jq("a[name=createSystemUrlButton]").click(function() {  
			$jq("#portalSystemUrlForm").trigger("submit");
		}); 
		
		$jq("a[name=cancelSystemUrlButton]").click(function() {  
			resetInputForm();
		});
		
		$jq("a[name=newSystemUrlButton]").click(function() {
			getList();
		});
		
		$jq("a[name=updateSystemUrlButton]").click(function() {  
			$jq("#portalSystemUrlForm").trigger("submit");
		}); 
		
		$jq("a[name=deleteSystemUrlButton]").click(function() {  
			if(confirm("<ikep4j:message pre='${preAlert}' key='confirmDelete' />")) {
				$jq.ajax({
					url : "<c:url value='portalSystemUrlDelete.do' />",
					data : $jq("#portalSystemUrlForm").serialize(),
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
		});
		
		var url = "";
		
		<c:choose>
		<c:when test="${!empty portalSystemUrl.systemUrlId}">
		url = "<c:url value='portalSystemUrlUpdate.do' />";
		</c:when>
		<c:otherwise>
		url = "<c:url value='portalSystemUrlCreate.do' />";
		</c:otherwise>
		</c:choose>
		
		/**
		 * Validation Logic Start
		 */
		var validOptions = {
			rules : {
				<c:forEach var="i18nMessage" items="${portalSystemUrl.i18nMessageList}" varStatus="loopStatus">
				"i18nMessageList[${loopStatus.index}].itemMessage" : {
					required : true,
					maxlength : 100
				},
				</c:forEach>
				"url" : {
					required : true,
					maxlength : 500
				}
			},
			messages : {
				<c:forEach var="i18nMessage" items="${portalSystemUrl.i18nMessageList}" varStatus="loopStatus">
				"i18nMessageList[${loopStatus.index}].itemMessage" : {
					required : "<ikep4j:message key='NotEmpty.portalSystemUrl.systemUrlName' />",
					maxlength : "<ikep4j:message key='Size.portalSystemUrl.systemUrlName' />"
				},
				</c:forEach>
				"url" : {
					required : "<ikep4j:message key='NotEmpty.portalSystemUrl.url' />",
					maxlength : "<ikep4j:message key='Size.portalSystemUrl.url' />"
				}
			},
			submitHandler : function() {
		    	
				$jq("input[name=systemUrlName]").val($jq("input[id=${userLocaleCode}]").val());
				
				$jq.ajax({
					url : url,
					data : $jq("#portalSystemUrlForm").serialize(),
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
		
		var validator = new iKEP.Validator("#portalSystemUrlForm", validOptions);

		/**
		 * Validation Logic End
		 */
	}); 
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
				<h2><ikep4j:message pre="${preMain}" key="pageTitle" /></h2>
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
							<option value="systemUrlName" <c:if test="${'systemUrlName' eq status.value}">selected="selected"</c:if>><ikep4j:message pre="${preSearch}" key="${status.expression}" post="systemUrlName"/></option>
							<option value="url" <c:if test="${'url' eq status.value}">selected="selected"</c:if>><ikep4j:message pre="${preSearch}" key="${status.expression}" post="url"/></option>
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
						<th scope="col"	width="20%">
							<a href="#" onclick="sort('A.SYSTEM_URL_ID', '<c:if test="${searchCondition.sortColumn eq 'A.SYSTEM_URL_ID'}">${searchCondition.sortType}</c:if>'); return false;">
								<ikep4j:message pre="${preList}" key="systemUrlId" />
							</a>
						</th>
						<th scope="col" width="30%">
							<a href="#" onclick="sort('A.SYSTEM_URL_NAME', '<c:if test="${searchCondition.sortColumn eq 'A.SYSTEM_URL_NAME'}">${searchCondition.sortType}</c:if>'); return false;">
								<ikep4j:message pre="${preList}" key="systemUrlName" />
							</a>
						</th>
						<th scope="col" width="50%">
							<a href="#" onclick="sort('A.URL', '<c:if test="${searchCondition.sortColumn eq 'A.URL'}">${searchCondition.sortType}</c:if>'); return false;">
								<ikep4j:message pre="${preList}" key="url" />
							</a>
						</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach var="systemUrl" items="${searchResult.entity}" varStatus="status">
					<tr>
						<td class="textCenter">
							<a href="#" onclick="getSystemUrl('${systemUrl.systemUrlId}', 'systemUrlName', 'PO', this); return false;">
								${systemUrl.systemUrlId}
							</a>
						</td>
						<td class="textLeft">
							<a href="#" onclick="getSystemUrl('${systemUrl.systemUrlId}', 'systemUrlName', 'PO', this); return false;">
								${systemUrl.systemUrlName}
							</a>
						</td>
						<td class="textLeft">
							<div class="ellipsis">
							<a href="#" onclick="getSystemUrl('${systemUrl.systemUrlId}', 'systemUrlName', 'PO', this); return false;">
								${systemUrl.url}
							</a>
							</div>
						</td>
					</tr>
					</c:forEach>
					
					<c:if test="${empty searchResult.entity}">
					<tr>
						<td colspan="3" class="textCenter">
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
	<h3><ikep4j:message pre='${preMain}' key='pageView' /></h3>
</div>
<!--//subTitle_2 End--> 

<!--blockDetail Start-->
<div class="blockDetail">

	<div id="viewDiv">
		<form id="portalSystemUrlForm" name="portalSystemUrlForm" action="" method="post">  
		<input type="hidden" name="systemUrlId" value="${portalSystemUrl.systemUrlId}"/>
		<input type="hidden" name="systemUrlName" id="systemUrlName" value="" />
			
			<table summary="<ikep4j:message pre='${preForm}' key='tableSummary' />">
				<caption></caption>
				<colgroup>
					<col width="10%"/>
					<col width="8%"/>
					<col width="82%"/>
				</colgroup>
				<tbody>
					<!--systemUrlName Start-->		
					<tr>
						<th scope="row" rowspan="${localeSize}">
							<span class="colorPoint">*</span><ikep4j:message pre="${preForm}" key="systemUrlName" />
						</th>
						<c:forEach var="i18nMessage" items="${portalSystemUrl.i18nMessageList}" varStatus="loopStatus">
						<c:if test="${i18nMessage.fieldName eq 'systemUrlName' }">
						<c:if test="${i18nMessage.index > 1}">
					<tr>
						</c:if>
						<th scope="row">
							<span class="colorPoint">*</span><c:out value="${i18nMessage.localeCode}"/>
						</th>
						<td>
							<div>
							<spring:bind path="portalSystemUrl.i18nMessageList[${loopStatus.index}].itemMessage">
								<input type="text" id="${i18nMessage.localeCode}" name="i18nMessageList[${loopStatus.index}].itemMessage" value="${i18nMessage.itemMessage}" class="inputbox w100" />
								<input type="hidden" name="i18nMessageList[${loopStatus.index}].localeCode" value="${i18nMessage.localeCode}" />
								<input type="hidden" name="i18nMessageList[${loopStatus.index}].fieldName" value="${i18nMessage.fieldName}" />
								<input type="hidden" name="i18nMessageList[${loopStatus.index}].messageId" value="${i18nMessage.messageId}" />
							</spring:bind>
							</div>
						</td>
					</tr>
						</c:if>
						</c:forEach>
					<!--//systemUrlName End-->
						
					<!--url Start-->
					<tr>
						<th colspan="2">
							<span class="colorPoint">*</span><ikep4j:message pre="${preForm}" key="url" />
						</th>
						<td class="textLeft">
							<div>
							<spring:bind path="portalSystemUrl.url">
							<input type="text" id="${status.expression}" name="${status.expression}" class="inputbox w100" value="${portalSystemUrl.url}"/>
							</spring:bind>
							</div>
						</td>
					</tr>	
					<!--//url End-->	
				</tbody>
			</table>
			
		</form>
		
		<div class="blockBlank_10px"></div>
		
		<!--blockButton Start-->
		<div class="blockButton">
			<ul>
				<c:choose>
				<c:when test="${empty portalSystemUrl.systemUrlId}">
				<li><a name="createSystemUrlButton" class="button" href="#" onclick="return false;"><span><ikep4j:message pre="${preButton}" key="create" /></span></a></li>
				<li><a name="cancelSystemUrlButton" class="button" href="#" onclick="return false;"><span><ikep4j:message pre="${preButton}" key="cancel" /></span></a></li>
				</c:when>
				<c:otherwise>
				<li><a name="newSystemUrlButton" class="button" href="#" onclick="return false;"><span><ikep4j:message pre="${preButton}" key="new" /></span></a></li>
				<li><a name="updateSystemUrlButton" class="button" href="#" onclick="return false;"><span><ikep4j:message pre="${preButton}" key="update" /></span></a></li>
				<li><a name="deleteSystemUrlButton" class="button" href="#" onclick="return false;"><span><ikep4j:message pre="${preButton}" key="delete" /></span></a></li>
				</c:otherwise>
				</c:choose>
			</ul>
		</div>
		<!--//blockButton End-->
				
	</div>

</div>
<!--//blockDetail End--> 