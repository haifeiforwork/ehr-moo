<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/base/common/taglibs.jsp"%>  

<%-- 메시지 관련 Prefix 선언 Start --%> 
<c:set var="preHeader"  value="message.collpack.kms.announce.boardItem.listBoardView.header" /> 
<c:set var="preList"    value="message.collpack.kms.announce.boardItem.listBoardView.list" />
<c:set var="preButton"  value="message.collpack.kms.announce.boardItem.listBoardView.button" /> 
<c:set var="preSearch"  value="message.collpack.kms.announce.boardItem.listBoardView.searchCondition" />
<c:set var="preMessage" value="message.collpack.kms.announce.boardItem.message.script" />  
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
				<c:forEach var="noticeItem" items="${searchResult.entity}"> 
				<tr class="bgWhite" name="boardItemLine"> 
						<td style="width:20px;"><input name="noticeItemIds" class="checkbox" title="checkbox" type="checkbox" value="${noticeItem.itemId}" /></td> 			
					<td>&nbsp;
						<strong>
							<c:choose>
								<c:when test="${noticeItem.shareCount>0}">
									<span class="cate_tit_3"><ikep4j:message pre='${preList}' key='share' /></span>
								</c:when>
								<c:otherwise>
									<span class="cate_tit_4"><ikep4j:message pre='${preList}' key='notShare' /></span>
								</c:otherwise>
							</c:choose>
							<c:if test="${noticeItem.itemDisplay eq 1}">
								<span class="cate_tit_5"><ikep4j:message pre='${preList}' key='mReading' /></span>
							</c:if>
							<c:choose>
								<c:when test="${noticePermission > 0}">
									<a name="noticeItem" href="<c:url value='/collpack/collaboration/board/notice/readNoticeItemView.do?itemId=${noticeItem.itemId}&workspaceId=${workspaceId}'/>">
										${noticeItem.title}
									</a>
								</c:when>
								<c:otherwise>
									${noticeItem.title}
								</c:otherwise>
							</c:choose>
						</strong>
						<div class="summaryViewInfo">
							<c:if test="${noticeItem.attachFileCount > 0}">
								<img src="<c:url value='/base/images/icon/ic_attach.gif' />" alt="<ikep4j:message pre='${preList}' key='attachFileCount' />" />
							</c:if>
							<span>
								<c:choose>
								<c:when test="${user.localeCode == portal.defaultLocaleCode}">
									${noticeItem.registerName}
								</c:when>
								<c:otherwise>
									${noticeItem.registerEnglishName}
								</c:otherwise>
								</c:choose>								
							</span>
							<img src="<c:url value='/base/images/theme/theme01/basic/bar_pageNum.gif'/>" alt="" />
							<span class="summaryViewInfo_date"><ikep4j:timezone pattern="yyyy.MM.dd" date="${noticeItem.registDate}"/></span>
							<img src="<c:url value='/base/images/theme/theme01/basic/bar_pageNum.gif'/>" alt="" />
							<span><ikep4j:message pre='${preList}' key='hitCount' /> <span class="summaryViewInfo_num">${noticeItem.hitCount}</span></span>
							<img src="<c:url value='/base/images/theme/theme01/basic/bar_pageNum.gif'/>" alt="" />
						</div>
						<div class="summaryViewCon">
							${fn:substring(noticeItem.contents, 0, 300)}
						</div>
						<c:if test="${!empty noticeItem.tagList}">
						<div class="summaryViewTag">
							<img src="<c:url value='/base/images/theme/theme01/basic/ic_tag.gif'/>" alt="<ikep4j:message pre='${preList}' key='tag' />" /> 
							<c:forEach var="tags" items="${noticeItem.tagList}">
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