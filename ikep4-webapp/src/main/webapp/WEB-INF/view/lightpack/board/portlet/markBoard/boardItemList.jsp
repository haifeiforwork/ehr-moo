<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/base/common/taglibs.jsp"%> 
<c:set var="preList"    value="ui.lightpack.board.portlet.markBoard" /> 
<div class="line_1" style="margin-bottom:5px; padding-top:5px;"></div>
<ul>
<%
	java.util.Date todate = new java.util.Date(); 
	request.setAttribute("todate", todate);
%>	 



<div id="portletlightpackRecentBoard${portletConfigId}" class="tableList_1">
<table summary="<ikep4j:message pre="${preList}" key="normalView.documentTitle" />">
		<caption></caption>
		<colgroup>
            <col width="*"/>
            <col width="16px"/>
            <col width="15%"/>
            <col width="10%"/>
		</colgroup>
	<tbody>  
		<c:choose>
    		<c:when test="${empty boardItemList}">
	    		<tr>
				   <td colspan="3" class="emptyRecord"><ikep4j:message pre='${preList}' key='list.noData' /></td> 
				</tr>			        
    		</c:when>
		<c:otherwise>
		<c:forEach var="boardItem" items="${boardItemList}" varStatus="status">
			<tr>
				<td class="t_po1">
					<div class="ellipsis" >
						<a class="item"
						href="<c:url value='/lightpack/board/boardItem/readBoardItemLinkView.do?popupYn=true&amp;itemId=${boardItem.itemId}'/>" 
						title="<c:if test='${boardItem.itemDisplay eq 1}'><ikep4j:message pre='${preDetail}' key='notice' /></c:if>${fn:escapeXml(boardItem.title)}" >
						<c:if test="${boardItem.itemDisplay eq 1}"><ikep4j:message pre='${preDetail}' key='notice' /></c:if>${boardItem.title}
						</a>						
					</div>
				</td>
				<td class="textCenter" style="vertical-align: middle;">
				    <c:set var="diff">${ikep4j:getTwoDateDiff(todate, boardItem.startDate, "D")}</c:set>
				    <c:if test="${diff lt 3}"><img src="<c:url value='/base/images/theme/theme01/basic/ic_new.gif'/>" title="new" /></c:if> 
                </td>
			    <td class="textCenter">
			    	<div class="ellipsis">
			     		<c:choose>
							<c:when test="${boardItem.anonymous eq 1}">
								<span>${boardItem.user.userName}</span>
							</c:when>   
							<c:otherwise>
								<c:set var="user"   value="${sessionScope['ikep.user']}" /> 
								<c:set var="portal" value="${sessionScope['ikep.portal']}" /> 
								<c:choose>
									<c:when test="${user.localeCode == portal.defaultLocaleCode}">   
										<a href="#a" onclick="iKEP.showUserContextMenu(this, '${boardItem.registerId}', 'bottom');">${boardItem.user.userName}</a>
									</c:when>
									<c:otherwise> 
										<a href="#a" onclick="iKEP.showUserContextMenu(this, '${boardItem.registerId}', 'bottom');">${boardItem.user.userEnglishName}</a>
									</c:otherwise>           
								</c:choose>							 
							</c:otherwise> 
						</c:choose>
			      	</div>
			    </td>
				<td class="textRight t3"><span class="date"><ikep4j:timezone date="${boardItem.registDate}" pattern="MM.dd"/></span></td>			   
			</tr>
		</c:forEach>	
		</c:otherwise> 
		</c:choose> 
	</tbody>
</table>
</div>
<div class="dotline_3" style="margin-bottom:0px;"></div>
</ul>

<script type="text/javascript">
//<![CDATA[
	$jq("input[id=${boardId}_hidden]").val("open")
	$jq("#portletlightpackPublicBoard").ajaxLoadComplete();
//]]>
</script>