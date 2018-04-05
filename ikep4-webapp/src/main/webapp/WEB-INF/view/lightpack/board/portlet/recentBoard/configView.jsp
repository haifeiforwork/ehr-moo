<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>  
<%@ include file="/base/common/taglibs.jsp"%>  
<c:set var="preList"   value="ui.lightpack.board.portlet.recentBoard" /> 
<c:set var="preButton" value="ui.lightpack.common.button" /> 
<script type="text/javascript">
//<![CDATA[
(function($){	 
	$(document).ready(function() { 
		$("#updatePageCount").click(function() { 
		    $.post('<c:url value="/lightpack/board/portlet/recentBoard/updatePageCount.do"/>', $("#configForm").serialize()) 
		    .success(function(data) {
		    	alert('<ikep4j:message pre='${preList}' key='updateSuccess' />');
		    	
		    	$("#portletlightpackRecentBoard").parent().parent().parent().trigger("click:reload");
		    })
		    .error(function(event, request, settings) { 
		    	alert('<ikep4j:message pre='${preList}' key='updateError' />');
		    	
		    });  
		});
	});  

})(jQuery);	
//]]>
</script> 
<form id="configForm">
<div class="poEdit">
	<div>
		<ikep4j:message pre='${preList}' key='selectPageCount' />:
		<select title="<ikep4j:message pre='${preList}' key='selectPageCount' />" name="count">
			<option value="3"  <c:if test="${count eq 3}">selected="selected"</c:if>>3</option>
			<option value="5"  <c:if test="${count eq 5}">selected="selected"</c:if>>5</option>
			<option value="7"  <c:if test="${count eq 7}">selected="selected"</c:if>>7</option>
			<option value="10" <c:if test="${count eq 10}">selected="selected"</c:if>>10</option>
		</select>
	</div>
	<div class="textRight"><a href="#a" id="updatePageCount" class="button_s"><span><ikep4j:message pre='${preButton}' key='save' /></span></a></div>
</div>
</form>
<div id="portletlightpackRecentBoard" class="tableList_1"> 
	<table summary="<ikep4j:message pre="${preList}" key="summary" />">
		<caption></caption>
		<colgroup>
			<col style="width: 75%;"/>
			<col style="width: 15%;"/>
			<col style="width: 10%;"/>
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
							<td>
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
												<a href="#a" onclick="iKEP.showUserContextMenu(this, '${boardItem.registerId}', 'bottom');">${boardItem.user.userName} ${boardItem.user.jobTitleName} ${boardItem.user.teamName}</a>
											</c:when>
											<c:otherwise> 
												<a href="#a" onclick="iKEP.showUserContextMenu(this, '${boardItem.registerId}', 'bottom');">${boardItem.user.userEnglishName} ${boardItem.user.jobTitleEnglishName} ${boardItem.user.teamEnglishName}</a>
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