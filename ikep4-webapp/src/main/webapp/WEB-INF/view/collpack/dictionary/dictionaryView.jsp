<%@ include file="/base/common/taglibs.jsp"%>
<%@ page language="java" errorPage="/base/common/error.jsp"  pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ page import="com.lgcns.ikep4.support.tagging.constants.TagConstants" %>

<%@ taglib uri="/WEB-INF/tld/ikep4j.tld" prefix="ikep4j" %> 
<%pageContext.setAttribute("crlf", "\r\n"); %>
<c:set var="user" value="${sessionScope['ikep.user']}" />
<c:set var="portal" value="${sessionScope['ikep.portal']}" />

<c:set var="preSearch"  value="ui.communication.common.searchCondition" /> 
<c:set var="preMessage"    	value="message.collpack.dictionary" />
<c:set var="preView"    	value="ui.collpack.dictionary.dictionaryView" />
<c:set var="preForm"    	value="ui.collpack.dictionary.dictionaryForm" />
<c:set var="preList"    	value="ui.collpack.dictionary.dictionaryList" />

<script type="text/javascript">
<!--
(function($) { 
	$(document).ready(function() { 
		//수정버튼
		$jq("#modifyButton").click(function() {
			location.href="formDictionary.do?wordId="+${dictionary.wordId};	
		});
		//이력적용
		$jq("#historyApplyButton").click(function() {
			var historyVersion = $jq("input[name=historyVersion]:radio:checked").val();

			$jq("input[name=wordId]").val(historyVersion);
			var version = $jq("#version"+historyVersion).text();

			if(confirm(version+"<ikep4j:message pre='${preMessage}' key='confirmVersion' />") == true){
				$jq("input[name=mode]").val("apply");
				$jq("#searchForm").submit();
			}
		});		
		//용어 삭제
		$jq("#deleteButton").click(function() {
			if(confirm("<ikep4j:message pre='${preMessage}' key='confirmDeleteWord' />") == true){
            	$jq('#searchForm').attr('action', '<c:url value="/collpack/dictionary/deleteWord.do"/>');
				$jq('#searchForm').submit();					
			}
		});
		$jq("#listButton").click(function() {
			location.href="main.do?dictionaryId=${dictionary.dictionaryId}";
		});			
	});
	//수정이력 상세조회
	viewHistoryPage = function(targetId){
		var pageURL = "<c:url value='/collpack/dictionary/getDictionary.do' />" + "?wordId=" + targetId + "&docPopup=" + ${docPopup};
		location.href=pageURL;
	}	
})(jQuery); 
function mouseOver(obj){obj.className = "bgSelected";}
function mouseOut(obj){obj.className = "";}
//-->
</script> 
<div id="tagResult">
				<form id="searchForm" method="post" action="<c:url value='/collpack/dictionary/createDictionary.do'/>">
				<input type="hidden" name="wordId" value="${dictionary.wordId}"/>
				<input type="hidden" name="hitCount" value="${dictionary.hitCount}"/>
				<input type="hidden" name="docPopup" value="${docPopup}"/>
				<input type="hidden" name="mode" value=""/>
				<h1 class="none">contents area</h1>

				<!--pageTitle Start-->
				<div id="pageTitle">
					<h2><ikep4j:message pre='${preView}' key='title' /></h2>
					<div id="pageLocation" class="none">
						<ul>
							<li class="liFirst"><ikep4j:message pre='${preForm}' key='pageLocation1depth' /></li>
							<li><ikep4j:message pre='${preForm}' key='pageLocation2depth' /></li>
							<li class="liLast"><ikep4j:message pre='${preView}' key='title' /></li>
						</ul>
					</div>
				</div>
				<!--//pageTitle End-->
				
				<!--blockDetail Start-->
				<div class="blockDetail">
					<table summary="<ikep4j:message pre='${preView}' key='subTitle' />">
						<caption></caption>
						<tbody>
							<tr>
								<th colspan="2" scope="row" width="18%"><ikep4j:message pre='${preForm}' key='formDictionaryName' /></th>
								<td width="32%">${dictionary.dictionaryName}</td>
								<th scope="row" width="18%"><ikep4j:message pre='${preList}' key='search' /></th>
								<td width="32%">${dictionary.hitCount}</td>								
							</tr>
							<tr>
								<th colspan="2" scope="row"><ikep4j:message pre='${preForm}' key='formWordName' /></th>
								<td colspan="3">${dictionary.wordName} (Ver ${dictionary.version})</td>
							</tr>
							<tr>
								<th colspan="2" scope="row"><ikep4j:message pre='${preForm}' key='formWordEnglishName' /></th>
								<td colspan="3">${dictionary.wordEnglishName}</td>
							</tr>
							<tr>
								<th colspan="2" scope="row" width="18%"><ikep4j:message pre='${preView}' key='updateNameFinal' /></th>
							    <c:choose>
							    	<c:when test="${user.localeCode == portal.defaultLocaleCode}">
							    		<td width="32%">${dictionary.registerName} ${dictionary.jobRankName} ${dictionary.teamName}</td>
							      	</c:when>
							      	<c:otherwise>
							      		<td width="32%">${dictionary.registerEnglishName} ${dictionary.jobTitleEnglishName} ${dictionary.teamEnglishName}</td>
							      	</c:otherwise>
							    </c:choose>																	
								<th scope="row" width="18%"><ikep4j:message pre='${preView}' key='updateDate' /></th>
								<td width="32%"><ikep4j:timezone date="${dictionary.registDate}" pattern="message.collpack.dictionary.timezone.dateformat.type2" keyString="true"/></td>
							</tr>
							<tr>
								<th colspan="2" scope="row"><ikep4j:message pre='${preList}' key='searchMemo' /></th>
								<td colspan="3" >
						        <spring:htmlEscape defaultHtmlEscape="false"> 
									<spring:bind path="dictionary.contents">
										<p>${status.value}</p>
									</spring:bind> 
						        </spring:htmlEscape> 								
								</td>
							</tr>
							<tr>
								<th colspan="2" scope="row"><ikep4j:message pre='${preList}' key='searchTag' /></th>
								<td colspan="3">
						
								<!--tag list-->
								<div class="tableTag" id="tagForm_${dictionary.wordId}" style="border-top:0px">
									<input type="hidden" name="tagItemType" value="<%=TagConstants.ITEM_TYPE_CONPORATE_VOCA %>"/>
									<input type="hidden" name="tagItemSubType" value=""/>
									<input type="hidden" name="tagItemName" value="${dictionary.wordName}"/>
									<input type="hidden" name="tagItemContents" value="${fn:escapeXml(dictionary.contents)}"/>
									<input type="hidden" name="tagItemUrl" value="/collpack/dictionary/getDictionary.do?wordId=${dictionary.wordId}"/>								
									<div class="corporateViewTag">
										<span class="ic_tag"><span><ikep4j:message pre='${preList}' key='searchTag' /></span></span>
											<c:forEach var="tag" items="${tagList}" varStatus="tagLoop"><c:if test="${tagLoop.count != 1}">, </c:if><a href="#a" onclick="iKEP.tagging.tagSearchByName('${tag.tagName}', '${tag.tagItemType}', '${tag.tagItemSubType}');return false;">${tag.tagName}</a></c:forEach>
										<span class="rebtn">
											<a href="#a" onclick="iKEP.tagging.tagFormView('${dictionary.wordId}');return false;" title="<ikep4j:message pre='${preView}' key='viewModify' />" class="ic_modify"></a>
										</span>										
									</div>
								</div>
								<!--//tag list-->
														
								</td>
							</tr>					
						</tbody>
					</table>
				</div>
				<!--//blockDetail End-->
				
				<!--blockButton Start-->
				<div class="blockButton"> 
					<ul>
						<c:if test="${ dictionary.recentVersion == dictionary.version }">
						<li><a class="button" href="#a" id="modifyButton"><span><ikep4j:message pre='${preView}' key='viewModify' /></span></a></li>
						<c:if test="${( dictionary.recentInputRegisterId == user.userId || isAdmin == true ) }">
							<li><a class="button" href="#a" id="deleteButton"><span><ikep4j:message pre='${preView}' key='viewDelete' /></span></a></li>
						</c:if>
						</c:if>						
						<li><a class="button" href="#a" id="listButton"><span><ikep4j:message pre='${preView}' key='viewList' /></span></a></li>
					</ul>
				</div>
				<!--//blockButton End-->
				
				
				<c:if test="${ dictionary.recentVersion == dictionary.version }">
				<div class="blockBlank_20px"></div>
				
				<!--subTitle_2 Start-->
				<div class="subTitle_2 noline">
					<h3><ikep4j:message pre='${preView}' key='subTitleHistory' /></h3>
				</div>
				<!--//subTitle_2 End-->
				
				<!--blockListTable Start-->
				<div class="blockListTable">
					<table summary="<ikep4j:message pre='${preView}' key='subTitleHistory' />">
						<caption></caption>
						<thead>
							<tr>
								<th scope="col" width="8%" style="text-align:right;"><ikep4j:message pre='${preView}' key='version' /></th>
								<th scope="col" width="22%"><ikep4j:message pre='${preView}' key='updateName' /></th>
								<th scope="col" width="*"><ikep4j:message pre='${preView}' key='updateReason' /></th>
								<th scope="col" width="20%"><ikep4j:message pre='${preView}' key='updateDate' /></th>
							</tr>
						</thead>
						<tbody>
							
							<c:forEach var="wordHistoryItem" items="${wordHistoryList}" varStatus="wordHistoryItemLoopCount">
								<tr>
									<td style="text-align:right;">
									<c:if test="${wordHistoryItem.version != dictionary.version}">
										<input name="historyVersion" class="radio" title="${wordHistoryItem.wordName}" type="radio" value="${wordHistoryItem.wordId}"  />
									</c:if>
									<span id="version${wordHistoryItem.wordId}">${wordHistoryItem.version}</span>
									</td>
								    <c:choose>
								    	<c:when test="${user.localeCode == portal.defaultLocaleCode}">
								    		<td width="32%">${wordHistoryItem.registerName} ${wordHistoryItem.jobRankName} ${wordHistoryItem.teamName}</td>
								      	</c:when>
								      	<c:otherwise>
								      		<td width="32%">${wordHistoryItem.registerEnglishName} ${wordHistoryItem.jobTitleEnglishName} ${wordHistoryItem.teamEnglishName}</td>
								      	</c:otherwise>
								    </c:choose>										
									<td class="textLeft"><span id="contents${wordHistoryItem.wordId}"><a href="#a" onclick="viewHistoryPage('${wordHistoryItem.wordId}');return false;" >${fn:replace(ikep4j:cutString(wordHistoryItem.updateReason,80,"..."), crlf, "<br />")}</a></span></td>
									
									<td><ikep4j:timezone date="${wordHistoryItem.updateDate}" pattern="message.collpack.dictionary.timezone.dateformat.type2" keyString="true"/>
									<input type="hidden" name="wordEnglishName${wordHistoryItem.wordId}" id="wordEnglishName${wordHistoryItem.wordId}" value="${wordEnglishName}"/>
									<input type="hidden" name="wordName${wordHistoryItem.wordId}" id="wordName${wordHistoryItem.wordId}" value="${wordName}"/>
									</td>
								</tr>
							</c:forEach>																										
						</tbody>
					</table>						
				</div>
				<!--//blockListTable End-->
				
				
				<!--blockButton Start-->
				<div class="blockButton"> 
					<ul>
						<c:if test="${dictionary.version != '1.0'}">
						<li><a class="button" href="#a" id="historyApplyButton"><span><ikep4j:message pre='${preView}' key='viewApply' /></span></a></li>
						</c:if>
					</ul>
				</div>
				<!--//blockButton End-->
			
				</c:if>
				
				</form>
</div>				