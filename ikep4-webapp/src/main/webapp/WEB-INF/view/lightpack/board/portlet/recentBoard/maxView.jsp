<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>  
<%@ include file="/base/common/taglibs.jsp"%>  
<c:set var="preList"   value="ui.lightpack.board.portlet.recentBoard" /> 
<c:set var="preButton" value="ui.lightpack.common.button" /> 
<script type="text/javascript">
//<![CDATA[
(function($){	 
	$(document).ready(function() {
		$("#divTab_s").tabs();
		
		$("a.item").click(function() {
			var url = $(this).attr("href")+ "&popupYn=true&portletYn=true";
			
			var options = {
				windowTitle : $(this).html(),
				documentTitle : "게시판 내용보기",
				width:600, height:500, modal:true, 
				callback : function(result) {
					$("#portletlightpackRecentBoard").parent().parent().parent().trigger("click:reload"); 
				}
			};
			
			iKEP.portletPopupOpen(url, options);
			
			return false;
		});
		
		viewPopUpProfile = function(targetUserId) { 
			var pageURL = "<c:url value='/support/profile/getProfilePopup.do' />?targetUserId=" + targetUserId;
			iKEP.popupOpen( pageURL , {width:800, height:280 , callback:function(result) {} });
		}
		
	}); 

})(jQuery);	
//]]>
</script> 	
<!--blockListTable Start-->
<div id="tagResult"> 
	<div id="splitterBox">
		<div> 
			<div class="blockListTable summaryView"> 
				<table summary="<ikep4j:message pre="${preList}" key="summary" />">
					<caption></caption>	 
					     <col width="*"/>
                         <col width="15%"/>
                         <col width="10%"/>
					<tbody>
						<c:choose>
						    <c:when test="${empty boardItemList}">
								<tr class="bgWhite">
									<td colspan="2" class="emptyRecord"><ikep4j:message pre='${preList}' key='emptyRecord' /></td> 
								</tr>				        
						    </c:when>
						    <c:otherwise>
								<c:forEach var="boardItem" items="${boardItemList}"> 
								<tr class="bgWhite" name="boardItemLine">  
									<td>
										<strong>      
											<a class="item" href="<c:url value='/lightpack/board/boardItem/readBoardItemView.do?boardId=${boardItem.boardId}&amp;itemId=${boardItem.itemId}'/>"><c:if test="${boardItem.itemDisplay eq 1}"><ikep4j:message pre='${preList}' key='notice' /></c:if>${boardItem.title}</a>
											<c:if test="${boardItem.linereplyCount gt 0}"><span class="colorPoint">(${boardItem.linereplyCount})</span></c:if> 
										</strong>
										<div class="summaryViewInfo">
											<c:if test="${boardItem.attachFileCount > 0}"><img src="<c:url value='/base/images/icon/ic_attach.gif' />" alt="<ikep4j:message pre='${preList}' key='attachFileCount' />" /></c:if>
											<span>
												<c:choose>
											 		<c:when test="${board.anonymous eq 1}">
														<span><ikep4j:message pre='${preList}' key='anonymous'/></span>
													</c:when>  
													<c:otherwise>
														<c:set var="user"   value="${sessionScope['ikep.user']}" />
														<c:set var="portal" value="${sessionScope['ikep.portal']}" />
														<c:choose>
															<c:when test="${user.localeCode == portal.defaultLocaleCode}"> 
																<a href="#a"  onclick="iKEP.showUserContextMenu(this, '${boardItem.registerId}', 'bottom');">${boardItem.user.userName} ${boardItem.user.jobTitleName} ${boardItem.user.teamName}</a>
															</c:when>
															<c:otherwise> 
																<a href="#a"  onclick="iKEP.showUserContextMenu(this, '${boardItem.registerId}', 'bottom');">${boardItem.user.userEnglishName} ${boardItem.user.jobTitleEnglishName} ${boardItem.user.teamEnglishName}</a>
															</c:otherwise>
														</c:choose>										 
													</c:otherwise>  
												</c:choose>  
											</span> 
											<img src="<c:url value='/base/images/common/bar_info.gif'/>" alt="" />
											<span class="summaryViewInfo_date"><ikep4j:timezone date="${boardItem.registDate}" pattern="yyyy.MM.dd HH:mm:ss"/></span>
											<img src="<c:url value='/base/images/common/bar_info.gif'/>" alt="" />
											<span>조회 <span class="summaryViewInfo_num">${boardItem.hitCount}</span></span>
											<img src="<c:url value='/base/images/common/bar_info.gif'/>" alt="" />
											<span>추천 <span class="summaryViewInfo_num">${boardItem.recommendCount}</span></span>
										</div>
										<div class="summaryViewCon"><ikep4j:extractText text="${boardItem.contents}" length="300"/></div>
										<div class="summaryViewTag"><span class="ic_tag"><span>태그</span></span>
									        <c:forEach var="tag" items="${boardItem.tagList}" varStatus="tagLoop">
									        	<c:if test="${tagLoop.count != 1}">, </c:if>
									        	<a href="#a" onclick="iKEP.tagging.tagSearchByName('${tag.tagName}', '${tag.tagItemType}', '${tag.tagItemSubType}');return false;">${tag.tagName}</a>
									        </c:forEach> 
										</div> 	 
									</td>
								</tr>  
								</c:forEach>				      
						    </c:otherwise> 
						</c:choose>  
					</tbody> 
				</table> 
			</div>
		</div>
	</div> 
</div>