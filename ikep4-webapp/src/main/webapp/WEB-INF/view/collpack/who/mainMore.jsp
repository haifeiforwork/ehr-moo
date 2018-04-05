<%@ include file="/base/common/taglibs.jsp"%>
<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ page import="com.lgcns.ikep4.support.tagging.constants.TagConstants" %>

<c:set var="preForm"    value="ui.collpack.who.whoForm" />
<c:set var="preSub"    value="ui.collpack.who.whoMenu" />
<c:set var="preList"    value="ui.collpack.who.whoList" />
<c:set var="preMessage"    value="message.collpack.who" />
<c:set var="preSearch"  value="ui.communication.common.searchCondition" /> 

				<c:forEach var="who" items="${searchResult.entity}" varStatus="whoLoopCount">				
				<div class="whosList">
					<div class="whosPhoto">
					    <c:choose>
					    	<c:when test="${who.profilePictureId != null}">
					    		<a href="getWho.do?profileId=${who.profileId}"><img id="profilePictureImage" src="<c:url value='/support/fileupload/downloadFile.do?fileId=${who.profilePictureId}&profileYn=Y' />" width="100" height="100" alt="<ikep4j:message pre='${preForm}' key='formPicture' />" /></a>
					      	</c:when>
					      	<c:otherwise>
					      		<img id="profilePictureImage" src="<c:url value='/base/images/common/photo_170x170.gif' />" width="100" height="100" alt="<ikep4j:message pre='${preForm}' key='formPicture' />" />
					      	</c:otherwise>
					    </c:choose>		
					</div>
					<div class="whosInfo">
						<h4><a href="getWho.do?profileId=${who.profileId}">${who.name}</a></h4>
						<table summary="<ikep4j:message pre='${preMenu}' key='menuList' />">
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
									<td colspan="2"><span class="whos_tit"><ikep4j:message pre='${preForm}' key='formExpert' /></span> : <span>
									<!--tag list-->
									<div class="tableTag" id="tagForm_${who.profileId}">
										<input type="hidden" name="tagItemType" value="<%=TagConstants.ITEM_TYPE_WHO %>">
										<input type="hidden" name="tagItemSubType" value="${who.profileGroupId}">
										<input type="hidden" name="tagItemName" value="${who.name}">
										<input type="hidden" name="tagItemContents" value="${fn:escapeXml(who.memo)}">
										<input type="hidden" name="tagItemUrl" value="/collpack/who/getWho.do?profileId=${who.profileId}">								
										<div>									
										<span class="ic_tag"></span>
											<c:forEach var="tag" items="${who.tagList}" varStatus="tagLoop"><c:if test="${tagLoop.count != 1}">, </c:if><a href="#a" onclick="iKEP.tagging.tagSearchByName('${tag.tagName}', '${tag.tagItemType}', '${tag.tagItemSubType}');return false;">${tag.tagName}</a></c:forEach>
										<span class="rebtn">
											<a href="#a" onclick="iKEP.tagging.tagFormView('${who.profileId}');return false;" title="<ikep4j:message pre='${preView}' key='viewModify' />" class="ic_modify"></a>
										</span>	
										</div>
									</div>										
									<!--//tag list-->
									</td>
								</tr>																																																
							</tbody>
						</table>
					</div>			
				</div>
				</c:forEach>