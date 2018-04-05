<%@ include file="/base/common/taglibs.jsp"%>
<%@ page language="java" errorPage="/base/common/error.jsp"  pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>

<%@ taglib uri="/WEB-INF/tld/ikep4j.tld" prefix="ikep4j" %> 
<%pageContext.setAttribute("crlf", "\r\n"); %>

<c:set var="preList"    value="ui.support.sms.listSmsView.list" />
<c:set var="preSearch"  value="ui.communication.common.searchCondition" /> 
<c:set var="preMessage" 	value="message.collpack.dictionary" />
<c:set var="preForm"    	value="ui.collpack.dictionary.dictionaryForm" />
<c:set var="preView"    	value="ui.collpack.dictionary.dictionaryView" />

<c:set var="user" value="${sessionScope['ikep.user']}" />
<c:set var="portal" value="${sessionScope['ikep.portal']}" />

<script>
<!--
var dialogWindow;
function fnCaller(param, dialog) {
	dialogWindow = dialog;
}

(function($) { 
	$(document).ready(function() { 		

		$jq("#wordNameSearchButton").click(function() {
			var searchWord = $jq("#searchWord").val();	
			var dictionaryId = $jq("select[name=dictionaryId]").val();

			if ( searchWord == '' ) {
				//alert("<ikep4j:message key='NotEmpty.dictionary.wordName'/>");return;
			}	

			$("input[name=pageIndex]").val("1");
			$jq("#searchForm").submit();

		});		

		$jq("#saveButton").click(function() {

			var curRadioValue = $jq("input[name=linkId]:radio:checked").val();
			if ( curRadioValue == null) {
				alert("<ikep4j:message key='Alert.dictionary.wordId'/>");
				return;
			}
			
			curRadioValue = curRadioValue.split("|");
			var wordId = curRadioValue[0];
			var wordName = curRadioValue[1];

			var targetId = "contents";
			dialogWindow.callback({wordId:wordId, wordName:wordName, targetId:targetId});
			dialogWindow.close();
		});	
		$jq("#cancelButton").click(function() {
			dialogWindow.close();
		});			
	});
})(jQuery); 
function closeWin() {
	
}
//-->
</script> 
<!-- Modal window Start -->

<form id="searchForm" method="post" action="<c:url value='/collpack/dictionary/linkDictionary.do'/>">
<input type="hidden" name="searchColumn" value="wordName">
<div class="" id="layer_p" title="Category add">
	
	<!--blockDetail Start-->
		<div class="blockDetail">
			<table summary="<ikep4j:message pre='${preForm}' key='formLink' />">
				<caption></caption>
				<tbody>
					<tr>
						<th scope="row" width="18%"><ikep4j:message pre='${preForm}' key='formDictionaryName' /></th>
						<td width="82%">
							<select title="<ikep4j:message pre='${preForm}' key='formDictionaryName' />" name="dictionaryId" id="dictionaryId">
								<c:forEach var="dictionaryItem" items="${dictionaryList}" varStatus="dictionaryItemLoopCount">
									<option value="${dictionaryItem.dictionaryId}" <c:if test="${dictionaryItem.dictionaryId eq searchCondition.dictionaryId}">selected</c:if> >${dictionaryItem.dictionaryName}</option>
								</c:forEach>									
							</select>
						</td>
					</tr>
					<tr>
						<th scope="row"><ikep4j:message pre='${preForm}' key='formWordName' /></th>
						<td>
							<spring:bind path="searchCondition.searchWord">
								<input name="${status.expression}" id="${status.expression}" title="<ikep4j:message pre='${preForm}' key='formWordName' />" class="inputbox valign_baseline" type="text" size="20" value="${searchCondition.searchWord}" />
							</spring:bind>
							<a class="button_ic" href="#a" id="wordNameSearchButton"><span><img src="<c:url value='/base/images/icon/ic_btn_search.gif' />" alt="" />Search</span></a>
						</td>
					</tr>
				</tbody>
			</table>
			&nbsp;
			<table summary="contents">
				<thead>
					<tr><th style="text-align:left;"><ikep4j:message pre='${preView}' key='subTitle' /></th></tr>
				</thead>
				<tbody>
					<c:if test="${searchResult.pageCount == 0}">
						<tr class="bgWhite">
							<td height="200" align="center"><ikep4j:message pre='${preMessage}' key='noData' /></td>
						</tr>				
					</c:if>				
				 	<c:forEach var="dictionary" items="${searchResult.entity}" varStatus="dictionaryLoopCount">		 		
						<tr class="bgWhite">					
							<td><input name="linkId" class="radio" title="${dictionary.wordName}" type="radio" value="${dictionary.wordId}|${dictionary.wordName}">&nbsp;${dictionary.wordName}
							&nbsp;
							<span class="corporateViewInfo" style="padding-right:10px;">
							    <c:choose>
							    	<c:when test="${user.localeCode == portal.defaultLocaleCode}">
							    		<span class="corporateViewInfo_name">${dictionary.registerName}</span>
							    		<span class="corporateViewInfo_name">${dictionary.jobRankName}</span>
							    		<img src="<c:url value='/base/images/common/bar_info.gif' />" alt="" />	
							    		<span class="corporateViewInfo_name">${dictionary.teamName}</span>
							      	</c:when>
							      	<c:otherwise>
							      		<span class="corporateViewInfo_name">${dictionary.registerEnglishName}</span>
							    		<span class="corporateViewInfo_name">${dictionary.jobTitleEnglishName}</span>
							    		<img src="<c:url value='/base/images/common/bar_info.gif' />" alt="" />	
							    		<span class="corporateViewInfo_name">${dictionary.teamEnglishName}</span>							      		
							      	</c:otherwise>
							    </c:choose>											
								<img src="<c:url value='/base/images/common/bar_info.gif' />" alt="" />							
								<ikep4j:timezone date="${dictionary.registDate}" pattern="message.collpack.dictionary.timezone.dateformat.type" keyString="true"/>
							</span>
							<br>
							<ikep4j:extractText text="${dictionary.contents}" length="70"/>
							</td>
						</tr>
					</c:forEach> 													
				</tbody>
			</table> 
			<!--Page Numbur Start--> 
			<spring:bind path="searchCondition.pageIndex">
			<ikep4j:pagination searchFormId="searchForm" pageIndexInput="${status.expression}" searchCondition="${searchCondition}" />
			<input name="${status.expression}" type="hidden" value="${status.value}" title="<ikep4j:message pre='${preSearch}' key='${status.expression}'/>" />
			</spring:bind>  
			<!--//Page Numbur End--> 
			
						
		</div>
		<!--//blockDetail End-->

		<c:if test="${searchResult.pageCount != 0}">
		<!--blockButton Start-->
		<div class="blockButton"> 
			<ul>
				<li><a class="button" href="#a" id="saveButton"><span><ikep4j:message pre='${preView}' key='viewSave' /></span></a></li>
				<li><a class="button" href="#a" id="cancelButton"><span><ikep4j:message pre='${preView}' key='viewCancel' /></span></a></li>
			</ul>
		</div>
		<!--//blockButton End-->
		</c:if>
</div>	
<!-- //Modal window End -->
</form>