<%@ include file="/base/common/taglibs.jsp"%>
<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ page import="com.lgcns.ikep4.support.tagging.constants.TagConstants" %>
<c:set var="preList"    	value="ui.collpack.dictionary.dictionaryList" />
<c:set var="preView"    	value="ui.collpack.dictionary.dictionaryView" />

							<c:forEach var="dictionary" items="${searchResult.entity}" varStatus="dictionaryLoopCount">								
								<tr>
								 		<c:choose>
								 			<c:when test="${(searchCondition.mode == 'myInputList' || searchCondition.mode == 'myViewList')}">
												<td class="corporate_s">
								 			</c:when>
								 			<c:otherwise>
								 				<td>
								 			</c:otherwise>		 			
								 		</c:choose>
										<div class="corporateViewTitle">
										<span class="titlebold"><a href="getDictionary.do?wordId=${dictionary.wordId}">${dictionary.wordName}</a></span> 
										<c:if test="${dictionary.wordEnglishName != '' && dictionary.wordEnglishName != null}">
										<span class="comment_num">(${dictionary.wordEnglishName})</span>
										</c:if>									
										<span class="corporateViewInfo">
										    <c:choose>
										    	<c:when test="${user.localeCode == portal.defaultLocaleCode}">
										    		<span class="corporateViewInfo_name"><a href="#a" onclick="viewPopUpProfile('${dictionary.registerId}');return false;" >${dictionary.registerName}</a> ${dictionary.jobRankName} ${dictionary.teamName}</span>
										      	</c:when>
										      	<c:otherwise>
										      		<span class="corporateViewInfo_name"><a href="#a" onclick="viewPopUpProfile('${dictionary.registerId}');return false;" >${dictionary.registerEnglishName}</a> ${dictionary.jobTitleEnglishName} ${dictionary.teamEnglishName}</span>
										      	</c:otherwise>
										    </c:choose>	
											<img src="<c:url value='/base/images/common/bar_info.gif' />" alt="" />
											<span><ikep4j:timezone date="${dictionary.registDate}" pattern="message.collpack.dictionary.timezone.dateformat.type2" keyString="true"/></span>
											<img src="<c:url value='/base/images/common/bar_info.gif' />" alt="" />
											<span><ikep4j:message pre='${preList}' key='search' /> <strong>${dictionary.hitCount}</strong></span>
										</span>
										</div>
										<div class="corporateViewCon"><ikep4j:extractText text="${dictionary.contents}" length="310"/></div>
										<!--tag list-->
										<div class="tableTag" id="tagForm_${dictionary.wordId}">
											<input type="hidden" name="tagItemType" value="<%=TagConstants.ITEM_TYPE_CONPORATE_VOCA %>">
											<input type="hidden" name="tagItemSubType" value="">
											<input type="hidden" name="tagItemName" value="${dictionary.wordName}">
											<input type="hidden" name="tagItemContents" value="${fn:escapeXml(dictionary.contents)}">
											<input type="hidden" name="tagItemUrl" value="/collpack/dictionary/getDictionary.do?wordId=${dictionary.wordId}">																	
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
							</c:forEach>	