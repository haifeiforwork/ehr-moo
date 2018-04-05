<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%-- Tab Lib Start --%> 
<%@ include file="/base/common/taglibs.jsp"%>
<%-- Tab Lib End --%> 

<%-- 메시지 관련 Prefix 선언 Start --%>
<c:set var="preMessage"	value="message.approval.admin.apprDefLine.listView"/>

<c:set var="preHeader"	value="ui.approval.admin.apprDefLine.listView.header"/>
<c:set var="preList"	value="ui.approval.admin.apprDefLine.listView.list"/>
<c:set var="preView"	value="ui.approval.admin.apprDefLine.listView.view"/>
<c:set var="preSearch"	value="ui.approval.admin.apprDefLine.listView.search"/>
<c:set var="preButton"	value="ui.approval.admin.apprDefLine.listView.button"/>	
				
<%-- 메시지 관련 Prefix 선언 End --%>

<%-- 세션정보 선언 Start --%> 
<c:set var="portal"	value="${sessionScope['ikep.portal']}" />
<c:set var="user"	value="${sessionScope['ikep.user']}" />
<%-- 세션정보 선언 End --%>

<script type="text/javascript">
<!-- 
(function($) {

	//주의 : 글로벌 함수는 아래와 같이 글로벌 변수에 무명 함수를 assign해서 생성한다.
	
	/**
	 * 정렬 조건 변경 함수
	 * 
	 * @param {Object} sortColumn
	 * @param {Object} sortType
	 */
	sort = function(sortColumn, sortType) {

		$("input[name=sortColumn]").val(sortColumn);
		
		if( sortType == 'ASC') {
			$("input[name=sortType]").val('DESC');	
		} else {
			$("input[name=sortType]").val('ASC');
		}
		
		$("#searchListButton").click();
	};
	
	
	createDefLine = function(defLineId) {  
		$('form[name=mainForm]').attr({
			action:"<c:url value='/approval/admin/apprDefLine/createView.do'/>"
		});

		$("input[name=defLineId]").val(defLineId);
		$("#mainForm").submit();
	};
	
	var checkDefLineId="";
	preViewDefLineInfo = function(defLineId){
		if(checkDefLineId==defLineId){
			$("#previewDiv").hide();
			checkDefLineId="";
		} else {
			$("#previewDiv").show();
			$target =$("#previewDetailDiv");
			$.post("<c:url value='/approval/admin/apprDefLine/previewDefLineView.do'/>", {defLineId:defLineId})
				.success(function(result) {
					$target.empty();
					$target.append(result);
				})
				.error(function(event, request, settings) { alert("error"); });
			checkDefLineId=defLineId;
		}
	};
	
	/**
	 * onload시 수행할 코드
	 */
	$(document).ready(function() { 
		
		$jq("#listViewLinkOfLeft").click();
		
		$("#searchListButton").click(function() {
			$("input[name=pageIndex]").val('1');
			$("#mainForm").submit(); 
			return false; 
		});
		
		/**
		 * 페이징 버튼
		 */
		$("#pageIndex").click(function() {
			$("#mainForm").submit(); 
			return false; 
		});
		
		$("select[name=pagePerRecord]").change(function(){
            $("#pageIndex").click();
        });
 
		$("#checkboxAll").click(function() { 
			$("input[name=defLineIds]").attr("checked", $jq(this).is(":checked"));  
		});
		
		$("#deleteButton").click(function() {
			var countCheckBox	=	$("input[name=defLineIds]:checkbox:checked").length;
			if(countCheckBox>0)
			{
				if(!confirm('<ikep4j:message pre='${preMessage}' key='confirmDelete' />?')) {
					return;
				}
				$jq.ajax({
					url : '<c:url value='/approval/admin/apprDefLine/deleteAjax.do' />?'+$("#mainForm").serialize(),
					type : "get",
					success : function(result) {
						$jq("#searchListButton").click(); 
					},
					error : function(event, request, settings){
			 			alert("<ikep4j:message pre='${preMessage}' key='deleteError' />");
					}
				});				
			}
			else
			{
				alert('<ikep4j:message pre='${preMessage}' key='checkbox' />');
			}
			return false; 	
	
		});


	});
})(jQuery);  
//-->
</script>
			
<h1 class="none"><ikep4j:message pre='${preHeader}' key='pageTitle' /></h1>

<!--pageTitle Start-->
<div id="pageTitle">
	<h2><ikep4j:message pre='${preHeader}' key='pageTitle' /></h2>
</div>
<!--//pageTitle End-->

<!--blockSearch Start-->
<form id="mainForm" name="mainForm" method="post" action="<c:url value="/approval/admin/apprDefLine/listView.do"/>">

	<spring:bind path="searchCondition.sortColumn">
	<input name="${status.expression}" type="hidden" value="${status.value}" title="<ikep4j:message pre='${preSearch}' key='${status.expression}'/>" />
	</spring:bind> 		
	<spring:bind path="searchCondition.sortType">
	<input name="${status.expression}" type="hidden" value="${status.value}" title="<ikep4j:message pre='${preSearch}' key='${status.expression}'/>" />
	</spring:bind> 
	<input name="defLineId" title="" type="hidden" value="" />
    <input name="controlTabType" title="" type="hidden" value="1:0:0:0" />
    <input name="controlType" title="" type="hidden" value="ORG" />
    <input name="selectType" title="" type="hidden" value="ALL" />
    
	<input name="defLineType" title="" type="hidden" value="" />

	
<!--blockListTable Start-->
<div class="blockListTable">

	<!--tableTop Start-->
	<div class="tableTop">
	<div class="tableTop_bgR"></div>
		<div class="listInfo">
			<spring:bind path="searchCondition.pagePerRecord">
			<select name="${status.expression}" title='<ikep4j:message pre='${preSearch}' key='${status.expression}' />'>
				<c:forEach var="commonCode" items="${commonCode.pageNumList}">
					<option value="${commonCode.key}" <c:if test="${commonCode.key eq status.value}">selected="selected"</c:if>>${commonCode.value}</option>
				</c:forEach> 
			</select> 
			</spring:bind>
			
			<div class="totalNum">${searchResult.pageIndex}/ ${searchResult.pageCount} <ikep4j:message pre='${preSearch}' key='page' /> ( <ikep4j:message pre='${preSearch}' key='pageCount.info' /><span> ${searchResult.recordCount}</span>)</div>

		</div>
		<div class="tableSearch"> 
			<spring:bind path="searchCondition.systemId">  
				<select name="${status.expression}" title='<ikep4j:message pre='${preSearch}' key='${status.expression}' />'>
					<option value=""><ikep4j:message pre='${preSearch}' key='select' /></option>
					<c:forEach var="apprSystemList" items="${apprSystemList}">
					<option value="${apprSystemList.systemId}" <c:if test="${apprSystemList.systemId eq status.value}">selected="selected"</c:if> >${apprSystemList.systemName}</option>
					</c:forEach> 
				</select>		
			</spring:bind>				
			<spring:bind path="searchCondition.searchWord">
				<spring:bind path="searchCondition.searchColumn">
				<input name="${status.expression}" type="hidden" value="defLineName" title="<ikep4j:message pre='${preSearch}' key='defLineName'/>" />
				</spring:bind>
				<input type="text" class="inputbox" title="<ikep4j:message pre='${preSearch}' key='${status.expression}'/>" name="${status.expression}" value="${status.value}" size="35" />
				<a id="searchListButton" name="searchListButton" href="#a"><img src="<c:url value='/base/images/theme/theme01/basic/btn_search.gif' />" alt="<ikep4j:message pre='${preSearch}' key='search'/>" /></a>
			</spring:bind>			
		</div>			
		<div class="clear"></div>
	</div>
	<!--//tableTop End-->
	
	<table summary="<ikep4j:message pre='${preList}' key='summary1' />">
		<caption></caption>
		<col style="width: 3%;"/>
		<col style="width: 5%;"/>
		<col style="width: 10%;"/>		
		<col style="width: 10%;"/>
		<col />
		<col style="width: 15%;"/>
		<col style="width: 10%;"/>

		<thead>
			<tr>
				<th scope="col"><input id="checkboxAll" class="checkbox" title="checkbox" type="checkbox" value="" /></th>
				<th scope="col"><ikep4j:message pre='${preList}' key='no' /></th>
				<th scope="col">
					<a onclick="sort('systemName', '<c:if test="${searchCondition.sortColumn eq 'systemName'}">${searchCondition.sortType}</c:if>');"  href="#a">
						<ikep4j:message pre='${preList}' key='systemName' />
					</a>				
				</th>				
				<th scope="col">
					<a onclick="sort('defLineType', '<c:if test="${searchCondition.sortColumn eq 'defLineType'}">${searchCondition.sortType}</c:if>');"  href="#a">
						<ikep4j:message pre='${preList}' key='defLineType' />
					</a>				
				</th>
				<th scope="col">
					<a onclick="sort('defLineName', '<c:if test="${searchCondition.sortColumn eq 'defLineName'}">${searchCondition.sortType}</c:if>');"  href="#a">
						<ikep4j:message pre='${preList}' key='defLineName' />
					</a>				
				</th>
				<th scope="col">
					<a onclick="sort('registDate', '<c:if test="${searchCondition.sortColumn eq 'registDate'}">${searchCondition.sortType}</c:if>');"  href="#a">
						<ikep4j:message pre='${preList}' key='registDate' />
					</a>				
				</th>
				<th scope="col"><ikep4j:message pre='${preList}' key='preView' /></th>
			</tr>
		</thead> 
		<tbody>
			<c:choose>
			    <c:when test="${searchResult.emptyRecord}">
					<tr>
						<td colspan="7" class="emptyRecord"><ikep4j:message pre='${preMessage}' key='emptyRecord' /></td>
					</tr>
			    </c:when>
			    <c:otherwise>
					<c:forEach var="apprDefLineList" items="${searchResult.entity}" varStatus="status">
						<tr>
							<td><input id="defLineIds" name="defLineIds" class="checkbox" title="checkbox" type="checkbox" value="${apprDefLineList.defLineId}" /></td>
							<td>${searchCondition.recordCount -((searchCondition.pageIndex - 1) * searchCondition.pagePerRecord) - status.index}</td>
							<td>
							<c:choose>
							<c:when test="${user.localeCode == portal.defaultLocaleCode}">
								${apprDefLineList.systemName}
							</c:when>
							<c:otherwise>
								${apprDefLineList.systemEnName}
							</c:otherwise>
							</c:choose>								
							</td>							
							<td>
							<c:forEach var="code" items="${commonCode.defLineTypeList}"> 
								<c:if test="${code.key eq apprDefLineList.defLineType}"><ikep4j:message key='${code.value}'/></c:if>
							</c:forEach>							
							</td>
							<td class="textLeft"><a href="#a" onclick="javascript:createDefLine('${apprDefLineList.defLineId}')">${apprDefLineList.defLineName}</a></td>
						    <td><ikep4j:timezone date="${apprDefLineList.registDate}" pattern="yyyy.MM.dd"/></td>			
							<td>
							<a class="button_ic valign_bottom" href="javaScript:preViewDefLineInfo('${apprDefLineList.defLineId}');"><span><img src="/ikep4-webapp/base/images/icon/ic_btn_search.gif" alt="" /><ikep4j:message pre='${preList}' key='view' /></span></a>
							</td>
						</tr>
					</c:forEach>				      
			    </c:otherwise> 
			</c:choose>  
		</tbody>
	</table>
	
	<!--Page Numbur Start--> 
	<spring:bind path="searchCondition.pageIndex">
	<ikep4j:pagination searchButtonId="${status.expression}" pageIndexInput="${status.expression}" searchCondition="${searchCondition}" />
	<input  id="${status.expression}" name="${status.expression}" type="hidden" value="${status.value}" title="<ikep4j:message pre='${preSearch}' key='${status.expression}'/>" />
	</spring:bind> 
	<!--//Page Numbur End-->
	
</div>
</form>
<!--//blockListTable End-->	

<!--blockButton Start-->
<div class="blockButton"> 
	<ul>
		<li><a id="deleteButton" class="button" href="#a"><span><ikep4j:message pre='${preButton}' key='delete'/></span></a></li>
		<li><a id="createButton" class="button" href="#a" onclick="createDefLine();"><span><ikep4j:message pre='${preButton}' key='create'/></span></a></li>
	</ul>
</div>

<div id="previewDiv" class="none">
	<!--pageTitle Start-->
	<div id="pageTitle">
		<h2>Preview</h2>
	</div>
	<!--//pageTitle End-->
	<div id="previewDetailDiv"></div>
</div>
<!--//blockButton End-->