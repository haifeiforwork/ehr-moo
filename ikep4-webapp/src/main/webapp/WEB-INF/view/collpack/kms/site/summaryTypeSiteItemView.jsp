<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/base/common/taglibs.jsp"%>  

<%-- 메시지 관련 Prefix 선언 Start --%> 
<c:set var="preHeader"  value="message.collpack.kms.site.boardItem.listBoardView.header" /> 
<c:set var="preList"    value="message.collpack.kms.site.boardItem.listBoardView.list" />
<c:set var="preButton"  value="message.collpack.kms.site.boardItem.listBoardView.button" /> 
<c:set var="preSearch"  value="message.collpack.kms.site.boardItem.listBoardView.searchCondition" />
<c:set var="preMessage" value="message.collpack.kms.site.boardItem.message.script" />  
<%-- 메시지 관련 Prefix 선언 End --%>

<!--blockListTable Start--> 
<table summary="일반게시판">
	<caption></caption>	 
	<tbody>
		<c:choose>
		    <c:when test="${searchResult.emptyRecord}">
				<tr class="bgWhite">
					<td colspan="2" class="emptyRecord"><ikep4j:message pre='${preSearch}' key='emptyRecord' /></td> 
				</tr>				        
		    </c:when>
		    <c:otherwise>
				<c:forEach var="siteItem" items="${searchResult.entity}"> 
				<tr class="bgWhite" name="boardItemLine"> 
						<td style="width:20px;"><input name="siteItemIds" class="checkbox" title="checkbox" type="checkbox" value="${siteItem.itemId}" /></td> 			
					<td>&nbsp;
						<strong>
							<c:choose>
								<c:when test="${siteItem.shareCount>0}">
									<span class="cate_tit_3"><ikep4j:message pre='${preList}' key='share' /></span>
								</c:when>
								<c:otherwise>
									<span class="cate_tit_4"><ikep4j:message pre='${preList}' key='notShare' /></span>
								</c:otherwise>
							</c:choose>
							<c:if test="${siteItem.itemDisplay eq 1}">
								<span class="cate_tit_5"><ikep4j:message pre='${preList}' key='mReading' /></span>
							</c:if>
							<c:choose>
								<c:when test="${sitePermission > 0}">
									<a name="siteItem" href="<c:url value='/collpack/collaboration/board/site/readSiteItemView.do?itemId=${siteItem.itemId}&workspaceId=${workspaceId}'/>">
										${siteItem.title}
									</a>
								</c:when>
								<c:otherwise>
									${siteItem.title}
								</c:otherwise>
							</c:choose>
						</strong>
						<div class="summaryViewInfo">
							<c:if test="${siteItem.attachFileCount > 0}">
								<img src="<c:url value='/base/images/icon/ic_attach.gif' />" alt="<ikep4j:message pre='${preList}' key='attachFileCount' />" />
							</c:if>
							<span>
								<c:choose>
								<c:when test="${user.localeCode == portal.defaultLocaleCode}">
									${siteItem.registerName}
								</c:when>
								<c:otherwise>
									${siteItem.registerEnglishName}
								</c:otherwise>
								</c:choose>								
							</span>
							<img src="<c:url value='/base/images/theme/theme01/basic/bar_pageNum.gif'/>" alt="" />
							<span class="summaryViewInfo_date"><ikep4j:timezone pattern="yyyy.MM.dd" date="${siteItem.registDate}"/></span>
							<img src="<c:url value='/base/images/theme/theme01/basic/bar_pageNum.gif'/>" alt="" />
							<span><ikep4j:message pre='${preList}' key='hitCount' /> <span class="summaryViewInfo_num">${siteItem.hitCount}</span></span>
							<img src="<c:url value='/base/images/theme/theme01/basic/bar_pageNum.gif'/>" alt="" />
						</div>
						<div class="summaryViewCon">
							${fn:substring(siteItem.contents, 0, 300)}
						</div>
						<c:if test="${!empty siteItem.tagList}">
						<div class="summaryViewTag">
							<img src="<c:url value='/base/images/theme/theme01/basic/ic_tag.gif'/>" alt="<ikep4j:message pre='${preList}' key='tag' />" /> 
							<c:forEach var="tags" items="${siteItem.tagList}">
								${tags.tagName}
							</c:forEach>
						</div>
						</c:if>
					</td>
				</tr>  
				</c:forEach>				      
		    </c:otherwise> 
		</c:choose>  
	</tbody> 
</table>