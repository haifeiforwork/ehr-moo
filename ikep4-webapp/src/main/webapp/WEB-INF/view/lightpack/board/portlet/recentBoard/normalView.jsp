<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>  
<%@ include file="/base/common/taglibs.jsp"%> 
<c:set var="preList"   value="ui.lightpack.board.portlet.recentBoard" /> 
<c:set var="preButton" value="ui.lightpack.common.button" /> 
<script type="text/javascript">
//<![CDATA[
(function($){	 
	$(document).ready(function() {   
		$("#${portletConfigId} a.item").click(function() { 
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
	}); 

})(jQuery);
//]]>
</script> 	
<c:set var="preDetail"  value="ui.lightpack.board.portlet.recentBoard" />
<div id="${portletConfigId}">
<div id="portletlightpackRecentBoard" class="tableList_1"> 
	<table summary="<ikep4j:message pre="${preList}" key="summary" />">
		<caption></caption>
		<colgroup>
			<col width="*"/>
			<col width="15%"/>
			<col width="10%"/>
		</colgroup>
		<tbody>
			<c:choose>
			    <c:when test="${empty boardItemList}">
				    <tr>
				    	<td colspan="3" class="emptyRecord"><ikep4j:message pre='${preList}' key='emptyRecord' /></td> 
				    </tr>				        
			    </c:when>
			    <c:otherwise> 
					<c:forEach var="boardItem" items="${boardItemList}" varStatus="status"> 
						<tr>
							<td class="t_po1">
								<div class="ellipsis">
									<a 
									class="item"
									href="<c:url value='/lightpack/board/boardItem/readBoardItemLinkView.do'/>?itemId=${boardItem.itemId}" 
									title="<c:if test='${boardItem.itemDisplay eq 1}'><ikep4j:message pre='${preDetail}' key='notice' /></c:if>${fn:escapeXml(boardItem.title)}" >
									<c:if test="${boardItem.itemDisplay eq 1}"><ikep4j:message pre='${preDetail}' key='notice' /></c:if>${boardItem.title}
									</a>
								</div>
							</td>
							<td class="textCenter">
								<div class="ellipsis">
									<c:choose>
										<c:when test="${boardItem.board.anonymous eq 1}">
											<span><ikep4j:message pre='${preList}' key='anonymous'/></span>
										</c:when>   
										<c:otherwise>
											<c:set var="user"   value="${sessionScope['ikep.user']}" /> 
											<c:set var="portal" value="${sessionScope['ikep.portal']}" /> 
											<c:choose>
												<c:when test="${user.localeCode == portal.defaultLocaleCode}">   
													<a href="#a" onclick="iKEP.showUserContextMenu(this, '${boardItem.registerId}', 'bottom');" title="${boardItem.user.userName} ${boardItem.user.jobTitleName} ${boardItem.user.teamName}">${boardItem.user.userName}</a>
												</c:when>
												<c:otherwise> 
													<a href="#a" onclick="iKEP.showUserContextMenu(this, '${boardItem.registerId}', 'bottom');" title="${boardItem.user.userEnglishName} ${boardItem.user.jobTitleEnglishName} ${boardItem.user.teamEnglishName}">${boardItem.user.userEnglishName}</a>
												</c:otherwise>           
											</c:choose>							 
										</c:otherwise> 
									</c:choose>	 
								</div>
							</td> 
							<td class="textRight"><span class="date"><ikep4j:timezone date="${boardItem.registDate}" pattern="MM.dd"/></span></td>
						</tr>	
					</c:forEach>				    
			    </c:otherwise>	 
			</c:choose> 																																		
		</tbody>
	</table>							
	<div class="l_b_corner"></div><div class="r_b_corner"></div>
</div>
</div>