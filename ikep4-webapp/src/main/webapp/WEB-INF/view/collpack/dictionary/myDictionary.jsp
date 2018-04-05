<%@ include file="/base/common/taglibs.jsp"%>
<%@ page language="java" errorPage="/base/common/error.jsp"  pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ page import="com.lgcns.ikep4.support.tagging.constants.TagConstants" %>

<%@ taglib uri="/WEB-INF/tld/ikep4j.tld" prefix="ikep4j" %> 
<%pageContext.setAttribute("crlf", "\r\n"); %>
<c:set var="user" value="${sessionScope['ikep.user']}" />
<c:set var="portal" value="${sessionScope['ikep.portal']}" />

<c:set var="preSearch"  value="ui.communication.common.searchCondition" /> 
<c:set var="preMessage"    	value="message.collpack.dictionary" />
<c:set var="preSub"    value="ui.collpack.dictionary.dictionaryMenu" />
<c:set var="preForm"    	value="ui.collpack.dictionary.dictionaryForm" />
<c:set var="preList"    	value="ui.collpack.dictionary.dictionaryList" />
<c:set var="preView"    	value="ui.collpack.dictionary.dictionaryView" />

<script type="text/javascript">
<!--


(function($) { 
	$(document).ready(function() { 
	    
	});
	viewPopUpProfile = function(targetUserId){
		var pageURL = "<c:url value='/support/profile/getProfilePopup.do' />" + "?targetUserId=" + targetUserId;
		iKEP.popupOpen( pageURL , {width:800, height:280 , callback:function(result) {} });
	}	
})(jQuery); 
var pageIndex = 2;		
function moreList(dictionaryId){	
	/*if(${searchCondition.pageCount} < pageIndex){
		return;	
	}*/

	$jq.ajax({   
		url : "listMore.do",  
		data : {pageIndex:pageIndex
			,dictionaryId:dictionaryId
			,localeCode:'${user.localeCode}'
			,startSortChar:'${searchCondition.startSortChar}'
			,endSortChar:'${searchCondition.endSortChar}'
			,searchColumn:'${searchCondition.searchColumn}'
			,searchWord:'${searchCondition.searchWord}'
			,mode:'${searchCondition.mode}'
			,viewId:'${searchCondition.viewId}'
			,registerId:'${searchCondition.registerId}'
			,isMore:'${searchCondition.isMore}'
		},     
		type : "post",  
		dataType : "html",
		success : function(result) { 
			if(result){
				//alert(result);
				$jq('#listFrame_'+dictionaryId).append(result);
				++pageIndex;
				if(${searchCondition.pageCount} < 10){
					$jq('#moreText').text("<ikep4j:message pre='${preMessage}' key='noMore' />");
				}
			}
		},
		error : function(event, request, settings){
		 alert("error");
		}
	}); 
}
//-->
</script> 
				<form id="searchForm" method="post" action="<c:url value='/collpack/dictionary/main.do'/>">
				<input type="hidden" name="dictionaryId" value="${searchCondition.dictionaryId}"/>
				<input type="hidden" name="startSortChar" value="${searchCondition.startSortChar}"/>
				<input type="hidden" name="endSortChar" value="${searchCondition.endSortChar}"/>
				<input type="hidden" name="localeCode" value="${user.localeCode}"/>				
				<input type="hidden" name="dictionarySortIndex" value="${searchCondition.dictionarySortIndex}"/>
				<h1 class="none">contents</h1>

				<!--pageTitle Start-->
				<div id="pageTitle">
					<h2>
			 		<c:choose>
			 			<c:when test="${searchCondition.viewId != null}">
							<ikep4j:message pre='${preSub}' key='menuMyView' />
			 			</c:when>
			 			<c:otherwise>
			 				<ikep4j:message pre='${preSub}' key='menuMyInput' />
			 			</c:otherwise>		 			
			 		</c:choose>						
					</h2>
					<div id="pageLocation" class="none">
						<ul>
							<li class="liFirst"><ikep4j:message pre='${preForm}' key='pageLocation1depth' /></li>
							<li><ikep4j:message pre='${preForm}' key='pageLocation2depth' /></li>
					 		<c:choose>
					 			<c:when test="${searchCondition.viewId != null}">
									<li class="liLast"><ikep4j:message pre='${preSub}' key='menuMyView' /></li>
					 			</c:when>
					 			<c:otherwise>
					 				<li class="liLast"><ikep4j:message pre='${preSub}' key='menuMyInput' /></li>
					 			</c:otherwise>		 			
					 		</c:choose>								
						</ul>
					</div>
				</div>
				<!--//pageTitle End-->




				
				<!--blockListTable Start-->
				<div id="listFrame">
							<c:if test="${searchResult.pageCount == 0}">
								<div style="margin-top:25px;margin-bottom:25px;text-align:center;"><ikep4j:message pre='${preMessage}' key='noResult' /></div>				
							</c:if>
							<c:set var="tmpWordCount" value="1"/>
							<c:set var="tmpMore" value="0"/>
							<c:forEach var="dictionary" items="${searchResult.entity}" varStatus="dictionaryLoopCount">	
							<c:if test="${(searchCondition.mode != 'dictionaryGroup' && tmpWordCount < 11) || searchCondition.mode == 'dictionaryGroup'}">
								<c:if test="${tmpDictionaryId != dictionary.dictionaryId }">
								<c:set var="tmpWordCount" value="1"/>
								<c:if test="${tmpDictionaryId != null && tmpWordCount == 1 && tmpMore == 0 }">
								</tbody>
							</table>							
							<div class="corporate_more">
							</div>							
						</div>					
								</c:if>								
								<c:set var="tmpMore" value="0"/>
						<!--subTitle_2 Start-->
						<div class="subTitle_2 noline">
							<h3>${dictionary.dictionaryName}</h3>
						</div>
						<!--//subTitle_2 End-->		
						<div class="corporateView">
							<table summary="${dictionary.dictionaryName}">
								<caption></caption>						
								<tbody id="listFrame_${dictionary.dictionaryId}">									
									<c:set var="tmpDictionaryId" value="${dictionary.dictionaryId}"/>
								</c:if>							
								<tr>
									<td class="corporate_s">
										<div class="corporateViewTitle">
										<span class="titlebold"><a href="getDictionary.do?wordId=${dictionary.wordId}">${dictionary.wordName}
										<c:if test="${dictionary.wordEnglishName != '' && dictionary.wordEnglishName != null}">
										<span class="engText">(${dictionary.wordEnglishName})</span>
										</c:if>
										</a></span>									
										<span class="corporateViewInfo">
											<span class="corporateViewInfo_name">
											
										    <c:choose>
										    	<c:when test="${user.localeCode == portal.defaultLocaleCode}">
										    		<span class="corporateViewInfo_name"><a href="#a" onclick="viewPopUpProfile('${dictionary.registerId}');return false;" >${dictionary.registerName}</a> ${dictionary.jobRankName} ${dictionary.teamName}</span>
										      	</c:when>
										      	<c:otherwise>
										      		<span class="corporateViewInfo_name"><a href="#a" onclick="viewPopUpProfile('${dictionary.registerId}');return false;" >${dictionary.registerEnglishName}</a> ${dictionary.jobTitleEnglishName} ${dictionary.teamEnglishName}</span>
										      	</c:otherwise>
										    </c:choose>												
										 
											</span>
											<img src="<c:url value='/base/images/common/bar_info.gif' />" alt="" />
											<span><ikep4j:timezone date="${dictionary.registDate}" pattern="message.collpack.dictionary.timezone.dateformat.type2" keyString="true"/></span>
											<img src="<c:url value='/base/images/common/bar_info.gif' />" alt="" />
											<span><ikep4j:message pre='${preList}' key='search' /> <strong>${dictionary.hitCount}</strong></span>
										</span>
										</div>
										<div class="corporateViewCon"><ikep4j:extractText text="${dictionary.dictionaryId}" length="310"/></div>
										<!--tag list-->
										<div class="tableTag" id="tagForm_${dictionary.wordId}">
											<input type="hidden" name="tagItemType" value="<%=TagConstants.ITEM_TYPE_CONPORATE_VOCA %>"/>
											<input type="hidden" name="tagItemSubType" value="${dictionary.wordGroupId}"/>
											<input type="hidden" name="tagItemName" value="${dictionary.wordName}"/>
											<input type="hidden" name="tagItemContents" value="${fn:escapeXml(dictionary.dictionaryId)}"/>
											<input type="hidden" name="tagItemUrl" value="/collpack/dictionary/getDictionary.do?wordId=${dictionary.wordId}"/>																	
											<div class="corporateViewTag">
												<span class="ic_tag"><span><ikep4j:message pre='${preList}' key='searchTag' /></span></span>
													<c:forEach var="tag" items="${dictionary.tagList}" varStatus="tagLoop"><c:if test="${tagLoop.count != 1}">, </c:if><a href="#a" onclick="iKEP.tagging.tagSearchByName('${tag.tagName}', '${tag.tagItemType}', '${tag.tagItemSubType}');return false;">${tag.tagName}</a></c:forEach>
												<span class="rebtn">
													<a href="#a" onclick="iKEP.tagging.tagFormView('${dictionary.wordId}');return false;" title="<ikep4j:message pre='${preView}' key='viewModify' />" class="ic_modify"></a>
												</span>	
											</div>
										</div>
										<!--//tag list-->										
									</td>
								</tr>	
								<c:if test="${tmpWordCount == 10 && searchCondition.mode != 'dictionaryGroup'}">
									<c:set var="tmpMore" value="1"/>
								</tbody>
							</table>										
							<div class="corporate_more" style="padding-bottom:5px;">
								<div class="blockButton_3"> 
									<a class="button_3" href="#a" onclick="moreList('${dictionary.dictionaryId}');return false;"><span id="moreText"><ikep4j:message pre='${preList}' key='listMore' /> <img src="<c:url value='/base/images/icon/ic_more_ar.gif' />" alt="" /></span></a>				
								</div>							

							</div>
							
						</div>	
								<c:set var="tmpWordCount" value="1"/>
								</c:if>
														
							</c:if>	
							<c:set var="tmpWordCount" value="${tmpWordCount+1}"/>				
							</c:forEach>																																																						

							<!--//blockListTable End-->
							<c:if test="${searchResult.recordCount > 10}">
								</tbody>
							</table>
							<div class="corporate_more">
							</div>							
						</div>		
							</c:if>	
						
							<c:if test="${searchCondition.mode == 'dictionaryGroup'}">
								<!--blockButton_3 Start-->
							</div>	
								<div class="blockButton_3"> 
									<a class="button_3" href="#a" onclick="moreList();return false;"><span id="moreText"><ikep4j:message pre='${preList}' key='listMore' /> <img src="<c:url value='/base/images/icon/ic_more_ar.gif' />" alt="" /></span></a>				
								</div>
								<!--//blockButton_3 End-->								
							</c:if>	
					</div>					
				</form>
				<div class="clear"></div>