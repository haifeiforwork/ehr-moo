<%@ include file="/base/common/taglibs.jsp"%>
<%@ taglib uri="/WEB-INF/tld/ikep4j.tld" prefix="ikep4j" %> 

<%-- 메시지 관련 Prefix 선언 Start --%> 
<c:set var="preHeader"  value="ui.support.rss.channelitem.header" /> 
<c:set var="preList"    value="ui.support.rss.channelitem.list" />
<c:set var="preDetail"  value="ui.support.rss.channelitem.detail" />
<c:set var="preButton"  value="ui.support.rss.button" /> 
<c:set var="preMessage" value="ui.support.rss.message" />
<c:set var="preSearch"  value="ui.communication.common.searchCondition" /> 
 <%-- 메시지 관련 Prefix 선언 End --%>
 
<%@ page language="java" errorPage="/common/error.jsp"  pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>


					<table summary="<ikep4j:message pre='${preList}' key='main.title' />">
						<caption></caption>
						<thead>
							<tr>
								<th scope="col" width="100%"><ikep4j:message pre='${preList}' key='itemTitle' /></th>
							</tr>
						</thead>
				
	
						
						<tbody>
							
							<c:choose>
							    <c:when test="${searchResult.emptyRecord}">
									<tr>
										<td colspan="8" class="emptyRecord"><ikep4j:message pre='${preMessage}' key='list.empty' /></td> 
									</tr>				        
							    </c:when>
							    <c:otherwise>
									<c:forEach var="channelItem" items="${searchResult.entity}">
										<tr>
											<td class="textLeft"><a href="#" onclick="getView('${channelItem.itemUrl}', this)">${ikep4j:cutString(channelItem.itemTitle,30,"")}</a></td>	
										</tr>
									</c:forEach>				      
							    </c:otherwise> 
							</c:choose>  
							
																																																													
						</tbody>
						
					</table>			
				
				
