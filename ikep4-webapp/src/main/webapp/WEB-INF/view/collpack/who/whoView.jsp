<%@ include file="/base/common/taglibs.jsp"%>
<%@ page language="java" errorPage="/base/common/error.jsp"  pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ page import="com.lgcns.ikep4.support.tagging.constants.TagConstants" %>

<%@ taglib uri="/WEB-INF/tld/ikep4j.tld" prefix="ikep4j" %> 
<%pageContext.setAttribute("crlf", "\r\n"); %>


<c:set var="preMessage"    value="message.collpack.who" />
<c:set var="preView"    value="ui.collpack.who.whoView" />
<c:set var="preForm"    value="ui.collpack.who.whoForm" />

<c:set var="preSearch"  value="ui.communication.common.searchCondition" /> 
<c:set var="user" value="${sessionScope['ikep.user']}" />
<c:set var="portal" value="${sessionScope['ikep.portal']}" />

<script type="text/javascript">
//<![CDATA[
(function($) { 
	$(document).ready(function() { 
		$jq("#modifyButton").click(function() {
			location.href="formWho.do?profileId="+${who.profileId};	
		});
		
		$jq("#historyApplyButton").click(function() {
			var historyVersion = $jq("input[name=historyVersion]:radio:checked").val();

			if ( historyVersion == null ) {
				alert("<ikep4j:message key='NotEmpty.who.version'/>");
				return;
			}

			$jq("input[name=profileId]").val(historyVersion);
			var version = $jq("#version"+historyVersion).text();

			if(confirm(version+"<ikep4j:message pre='${preMessage}' key='confirmVersion' />") == true){
				$jq("input[name=mode]").val("apply");
				$jq("#searchForm").submit();
			}
		});		
		
		$jq("#deleteButton").click(function() {
			if(confirm("<ikep4j:message pre='${preMessage}' key='confirmDelete' />") == true){
            	$jq('#searchForm').attr('action', '<c:url value="/collpack/who/deleteWho.do"/>');
				$jq('#searchForm').submit();				
			}
		});		
		$jq("#listButton").click(function() {
		    <c:choose>
		    	<c:when test="${who.recentVersion == who.version }">
		    		location.href="<c:url value='/collpack/who/main.do' />" + "?whoSortIndex=${whoSortIndex}";
		      	</c:when>
		      	<c:otherwise>
		      		location.href="<c:url value='/collpack/who/getWho.do' />" + "?profileId=${who.recentProfileId}" + "&amp;docPopup=" + ${docPopup};
		      	</c:otherwise>
		    </c:choose>				

		});			
	});

	viewPopUpProfile = function(targetUserId){
		var pageURL = "<c:url value='/support/profile/getProfilePopup.do' />" + "?targetUserId=" + targetUserId;
		iKEP.popupOpen( pageURL , {width:800, height:280 , callback:function(result) {} });
	}	
	viewHistoryPage = function(targetId){
		var pageURL = "<c:url value='/collpack/who/getWho.do' />" + "?profileId=" + targetId + "&amp;docPopup=" + ${docPopup};
		location.href=pageURL;
	}	
})(jQuery); 
function mouseOver(obj){obj.className = "bgSelected";}
function mouseOut(obj){obj.className = "";}
function goProfile(userId) {
	iKEP.goProfileMain(userId);
}
//]]>
</script> 
<div id="tagResult">
				<form id="searchForm" method="post" action="<c:url value='/collpack/who/createWho.do'/>">
				<input type="hidden" name="mode" value=""/>
				<input type="hidden" name="docPopup" value="${docPopup}"/>
				<input type="hidden" name="hitCount" value="${who.hitCount}"/>
				<input type="hidden" name="profileId" value="${who.profileId}"/>
				<input type="hidden" name="tag" value="<c:forEach var="tag" items="${tagList}" varStatus="tagLoop"><c:if test="${tagLoop.count != 1}">, </c:if>${tag.tagName}</c:forEach>"/>
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
				
				<div class="clear"></div>
				
				<!--blockListTable Start-->
				<div class="whosList">
					<div class="whosPhoto">
					    <c:choose>
					    	<c:when test="${who.profilePictureId != null}">
					    		<img id="profilePictureImage" src="<c:url value='/support/fileupload/downloadFile.do?fileId=${who.profilePictureId}&amp;profileYn=Y' />" width="100" height="100" alt="<ikep4j:message pre='${preForm}' key='formPicture' />" />
					      	</c:when>
					      	<c:otherwise>
					      		<img id="profilePictureImage" src="<c:url value='/base/images/common/photo_170x170.gif' />" width="100" height="100" alt="<ikep4j:message pre='${preForm}' key='formPicture' />" />
					      	</c:otherwise>
					    </c:choose>										
					</div>
					<div class="whosInfo">
						<h4>${who.name}</h4>
						<table summary="<ikep4j:message pre='${preView}' key='subTitle' />" width="100%" border="0">
							<caption></caption>						
							<tbody>
								<tr>
									<td colspan="2"><span class="whos_tit"><ikep4j:message pre='${preForm}' key='formCompanyName' /></span> : <span>${who.companyName}</span></td>
								</tr>
								<tr>
									<td width=""><span class="whos_tit"><ikep4j:message pre='${preForm}' key='formTeamName' /></span> : <span>${who.teamName}</span></td>
									<td width="">&nbsp;&nbsp; <span class="whos_tit"><ikep4j:message pre='${preForm}' key='formJobRankName01' /></span> : <span>${who.jobRankName}</span></td>
								</tr>
								<tr>
									<td width=""><span class="whos_tit"><ikep4j:message pre='${preForm}' key='formOfficePhoneno' /></span> : <span>${who.officePhoneno}</span></td>
									<td width="">&nbsp;&nbsp; <span class="whos_tit"><ikep4j:message pre='${preForm}' key='formMobile' /></span> : <span>${who.mobile}</span></td>
								</tr>
								<tr>
									<td colspan="2"><span class="whos_tit"><ikep4j:message pre='${preForm}' key='formMail' /></span> : <span><a href="mailto:${who.mail}">${who.mail}</a></span></td>
								</tr>																																																
								<tr>
									<td colspan="2"><span class="whos_tit"><ikep4j:message pre='${preForm}' key='formCompanyAddress' /></span> : <span>${who.companyAddress}</span></td>
								</tr>
								<tr>
									<td colspan="2"><span class="whos_tit"><ikep4j:message pre='${preForm}' key='formMemo' /></span><span style="vertical-align:top">&nbsp; : &nbsp;</span><span>${fn:replace(who.memo, crlf, "<br />")}</span></td>
								</tr>																																																																																																																																																																	
							</tbody>
						</table>
						<!--tag list-->								
						<input type="hidden" name="tagItemType" value="<%=TagConstants.ITEM_TYPE_WHO %>"/>
						<input type="hidden" name="tagItemSubType" value="${who.profileGroupId}"/>
						<input type="hidden" name="tagItemName" value="${who.name}"/>
						<input type="hidden" name="tagItemContents" value="${fn:escapeXml(who.memo)}"/>
						<input type="hidden" name="tagItemUrl" value="/collpack/who/getWho.do?profileId=${who.profileId}"/>								
						<div class="tableTag">
							<span class="ic_tag"></span>
								<c:forEach var="tag" items="${tagList}" varStatus="tagLoop"><c:if test="${tagLoop.count != 1}">, </c:if><a href="#a" onclick="iKEP.tagging.tagSearchByName('${tag.tagName}', '${tag.tagItemType}', '${tag.tagItemSubType}');return false;">${tag.tagName}</a></c:forEach>
							<span class="rebtn">
								<a href="#a" onclick="iKEP.tagging.tagFormView('${who.profileId}');return false;" title="<ikep4j:message pre='${preView}' key='viewModify' />" class="ic_modify"></a>
							</span>										
						</div>																					
						<!--//tag list-->		
						
						<!--blockButton Start-->
						<div class="blockButton"> 
							<ul>
								<c:if test="${ who.recentVersion == who.version }">
								<li><a class="button" href="#a" id="modifyButton"><span><ikep4j:message pre='${preView}' key='viewModify' /></span></a></li>
								<c:if test="${( who.recentInputRegisterId == user.userId || isAdmin == true ) }">
									<li><a class="button" href="#a" id="deleteButton"><span><ikep4j:message pre='${preView}' key='viewDelete' /></span></a></li>
								</c:if>
								</c:if>
								<li><a class="button" href="#a" id="listButton"><span><ikep4j:message pre='${preView}' key='viewList' /></span></a></li>
							</ul>
						</div>
						<!--//blockButton End-->
														
					</div>			
				</div>
				<!--//blockListTable End-->


				<c:if test="${ who.recentVersion == who.version }">
				<!--subTitle_2 Start-->
				<div class="whosSubTitle pt10">
					<h3><ikep4j:message pre='${preView}' key='relatedInnerWho' /></h3>
				</div>
				<!--//subTitle_2 End-->
				<div style="border-bottom:1px solid #d7d7d7;position:relative;padding-bottom:0px;margin-bottom:10px;"></div>

				<c:if test="${fn:length(innerProList) == 0}">
					<div style="margin-top:5px;margin-left:15px;text-align:center;"><ikep4j:message pre='${preMessage}' key='noProResult' /></div>				
				</c:if>
				<!--expert_topList Start-->
				<div class="whosPhotoList">
					<c:forEach var="innerProItem" items="${innerProList}" varStatus="innerProLoopCount">
						<div>
						    <c:choose>
						    	<c:when test="${user.localeCode == portal.defaultLocaleCode}">
						    		<c:set var="summaryInfo" value="${innerProItem.userName} ${innerProItem.jobTitleName} ${innerProItem.teamName}"/>
						       		<c:set var="userNameInfo" value="${innerProItem.userName}"/>
						      	</c:when>
						      	<c:otherwise>
						      		<c:set var="summaryInfo" value="${innerProItem.userEnglishName} ${innerProItem.jobTitleEnglishName} ${innerProItem.teamEnglishName}"/>
						       		<c:set var="userNameInfo" value="${innerProItem.userEnglishName}"/>
						      	</c:otherwise>
						    </c:choose>						
							<div><a href="#a" onclick="viewPopUpProfile('${innerProItem.userId}');return false;" title="${summaryInfo}"><img src="<c:url value='${innerProItem.profilePicturePath}' />" width="50" height="50" alt="image" onerror="this.src='<c:url value='/base/images/common/photo_50x50.gif'/>'" /></a></div>
						    <div class="textCenter mt3">${ikep4j:cutString(userNameInfo,8,"..")}</div>								
						</div>					
					</c:forEach>
				</div>
				<div class="clear"></div>
				<!--//expert_topList End-->

				<!--subTitle_2 Start-->
				<div class="whosSubTitle pt15">
					<h3><ikep4j:message pre='${preView}' key='relatedouterWho' /></h3>
				</div>
				<!--//subTitle_2 End-->
				<div style="border-bottom:1px solid #d7d7d7;position:relative;padding-bottom:0px;margin-bottom:10px;"></div>
				
				<c:if test="${fn:length(outerProList) == 0}">
					<div style="margin-top:5px;margin-left:15px;text-align:center;"><ikep4j:message pre='${preMessage}' key='noWwResult' /></div>				
				</c:if>				
				<!--expert_topList Start-->
				<div class="whosPhotoList">
					<c:forEach var="outerProItem" items="${outerProList}" varStatus="outerProLoopCount">
						<div>
							<div>
							
							
						    <c:choose>
						    	<c:when test="${outerProItem.profilePictureId != null}">
						    		<a href="getWho.do?profileId=${outerProItem.tagItemId}" title="${outerProItem.userName} ${outerProItem.jobTitleName} ${outerProItem.teamName}"><img src="<c:url value='/support/fileupload/downloadFile.do?fileId=${outerProItem.profilePictureId}&amp;profileYn=Y' />" width="50" height="50" alt="image" /></a>
						      	</c:when>
						      	<c:otherwise>
						      		<a href="getWho.do?profileId=${outerProItem.tagItemId}" title="${outerProItem.userName} ${outerProItem.jobTitleName} ${outerProItem.teamName}"><img src="<c:url value='/base/images/common/photo_170x170.gif' />" width="50" height="50" alt="image" /></a>
						      	</c:otherwise>
						    </c:choose>	
					    							
							</div>
							<div class="textCenter mt3">${ikep4j:cutString(outerProItem.userName,8,"..")}</div>
						</div>					
					</c:forEach>				
				</div>
				<div class="clear"></div>
				<!--//expert_topList End-->
				</c:if>
			
				
				
				<c:if test="${ who.recentVersion == who.version }">
				
				<!--subTitle_2 Start-->
				<div class="whosSubTitle pt15">
					<h3><ikep4j:message pre='${preView}' key='subTitle' /></h3>
				</div>
				<!--//subTitle_2 End-->
				
				<!--blockListTable Start-->
				<div class="blockListTable">
					<table summary="<ikep4j:message pre='${preView}' key='subTitle' />" border="0">
						<caption></caption>
						<thead>
							<tr>
								<th scope="col" width="8%" style="text-align:right;"><ikep4j:message pre='${preView}' key='version' /></th>
								<th scope="col" width="22%"><ikep4j:message pre='${preView}' key='updateName' /></th>
								<th scope="col" width="*"><ikep4j:message pre='${preForm}' key='formUpdateReason' /></th>
								<th scope="col" width="20%"><ikep4j:message pre='${preView}' key='updateDate' /></th>
							</tr>
						</thead>
						<tbody>
							
							<c:forEach var="whoHistoryItem" items="${whoHistoryList}" varStatus="whoHistoryItemLoopCount">
								<tr>
									<td style="text-align:right;">
									<c:if test="${whoHistoryItem.version != who.version}">
										<input name="historyVersion" class="radio" title="${whoHistoryItem.name}" type="radio" value="${whoHistoryItem.profileId}" <c:if test="${whoHistoryItemLoopCount.count == 1}">checked</c:if> />
									</c:if>
									<span id="version${whoHistoryItem.profileId}">${whoHistoryItem.version}</span>
									</td>
								    <c:choose>
								    	<c:when test="${user.localeCode == portal.defaultLocaleCode}">
								       		<td>${whoHistoryItem.registerName} ${whoHistoryItem.jobTitleName} ${whoHistoryItem.registerTeamName}</td>
								      	</c:when>
								      	<c:otherwise>
								       		<td>${whoHistoryItem.registerEnglishName} ${whoHistoryItem.jobTitleEnglishName} ${whoHistoryItem.teamEnglishName}</td>
								      	</c:otherwise>
								    </c:choose>										
									<td class="textLeft"><span id="contents${whoHistoryItem.profileId}">
								    <c:choose>
								    	<c:when test="${whoHistoryItem.version != who.version}">
								       		<a href="#a" onclick="viewHistoryPage('${whoHistoryItem.profileId}');return false;" >${fn:replace(ikep4j:cutString(whoHistoryItem.updateReason,80,"..."), crlf, "<br />")}</a>
								      	</c:when>
								      	<c:otherwise>
								       		${fn:replace(ikep4j:cutString(whoHistoryItem.updateReason,80,"..."), crlf, "<br />")}
								      	</c:otherwise>
								    </c:choose>									
									
									</span></td>
						
									
									<td><ikep4j:timezone date="${whoHistoryItem.registDate}" pattern="message.collpack.who.timezone.dateformat.type2" keyString="true"/></td>
								</tr>
							</c:forEach>																										
						</tbody>
					</table>						
				</div>
				<!--//blockListTable End-->
				
				
				<!--blockButton Start-->
				<div class="blockButton"> 
						<c:if test="${who.version != '1.0'}">				
							<ul>
								<li><a class="button" href="#a" id="historyApplyButton"><span><ikep4j:message pre='${preView}' key='viewApply' /></span></a></li>
							</ul>
						</c:if>
				</div>
				<!--//blockButton End-->
				
				</c:if>
				
				</form>
</div>				