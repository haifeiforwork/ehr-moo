<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/base/common/taglibs.jsp"%>  
<%-- 메시지 관련 Prefix 선언 Start --%>
<c:set var="preHeader"  value="message.collpack.collaboration.board.weekly.listBoardView.header" />
<c:set var="preDetail"  value="message.collpack.collaboration.board.weekly.createBoardView.detail" /> 
<c:set var="preList"    value="message.collpack.collaboration.board.weekly.listBoardView.list" />
<c:set var="preButton"  value="message.collpack.collaboration.board.weekly.listBoardView.button" /> 
<c:set var="preSearch"  value="message.collpack.collaboration.board.weekly.listBoardView.searchCondition" />
<c:set var="preMessage" value="message.collpack.collaboration.board.weekly.listBoardView.alert" />  
 <%-- 메시지 관련 Prefix 선언 End --%>
 <%-- 세션정보 선언 Start --%> 
<c:set var="portal" value="${sessionScope['ikep.portal']}" />
<c:set var="user" value="${sessionScope['ikep.user']}" />
<%-- 세션정보 선언 End --%>
<script type="text/javascript">
</script>

				<div class="blockLeft" style="width:100%;">
					<div class="blockListTable calTable">			
						<table summary="">
							<caption></caption>
							<thead>
								<tr>
									<th scope="col" width="20%" class="borderLeft"></th>
									<th scope="col" width="*"><span><ikep4j:message pre="${preList}" key="weeklyStatus"/></span></th>
									<th scope="col" width="16%" class="borderRight"></th>
								</tr>
							</thead>
							<tbody>
								<c:forEach var="weeklyItem" items="${searchResult.entity}" varStatus="idx">
								<tr <c:if test="${empty weeklyItem.title}">class="bgGray"</c:if>>
									<td class="borderLeft <c:if test='${idx.last}'>tdLast</c:if>">
										<c:choose>
											<c:when test="${user.localeCode == portal.defaultLocaleCode}">
												${weeklyItem.userName}&nbsp;${weeklyItem.jobTitleName}
											</c:when>
											<c:otherwise>
												${weeklyItem.userEnglishName}&nbsp;${weeklyItem.jobTitleEnglishName}
											</c:otherwise>
										</c:choose>	
									</td>
									<td class="textLeft <c:if test='${idx.last}'>tdLast</c:if>">
										<c:if test="${!empty weeklyItem.title}">
											<a href="#a">
												<c:if test="${weeklyItem.isSummary eq 1}"> 
													<span class="cate_block_5"><span class="cate_tit_5"><ikep4j:message pre="${preList}" key="summary"/></span></span>&nbsp;
												</c:if>
												<c:choose>
												<c:when test="${weeklyPermission>0}">
													<a href="<c:url value='/collpack/collaboration/board/weekly/readWeeklyItemView.do?itemId=${weeklyItem.itemId}&workspaceId=${workspaceId}'/>">${weeklyItem.title}</a>
												</c:when>
												<c:otherwise>
													${weeklyItem.title}
												</c:otherwise>
												</c:choose>
												<c:if test="${weeklyItem.isSummary eq 1}">
													</strong>
												</c:if>
												
											</a>
										</c:if>
										<c:if test="${empty weeklyItem.title && weeklyItem.memberId==user.userId}">
											<a id="createWeeklyItemButton" class="button_s" href="<c:url value='/collpack/collaboration/board/weekly/createWeeklyItemView.do?workspaceId=${workspaceId}'/>">
												<span>
													<ikep4j:message pre='${preButton}' key='createWeeklyItem'/>
												</span>
											</a>
										</c:if>
									</td>
									<td class="borderRight <c:if test='${idx.last}'>tdLast</c:if>">
										<c:if test="${!empty weeklyItem.registDate}">
											<ikep4j:timezone pattern="yyyy.MM.dd" date="${weeklyItem.registDate}"/>
										</c:if>
									</td>
								</tr>
								</c:forEach>
							</tbody>
						</table>
					</div>
				</div>
</form>		