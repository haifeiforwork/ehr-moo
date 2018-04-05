<%@ include file="/base/common/taglibs.jsp"%>
<%@ taglib uri="/WEB-INF/tld/ikep4j.tld" prefix="ikep4j" %> 

<%-- 메시지 관련 Prefix 선언 Start --%> 
<c:set var="prefix"  value="message.lightpack.cafe.main" />
 <%-- 메시지 관련 Prefix 선언 End --%>

<c:set var="user" value="${sessionScope['ikep.user']}" />
					

							<div class="leftTree">
								
								
								<c:choose>
								    <c:when test="${empty myCafeList}">
										<div class="cafeTitle_1">
											<ikep4j:message pre='${prefix}' key='list.empty' />
										</div>				        
								    </c:when>
								    <c:otherwise>
										<c:forEach var="cafe" items="${myCafeList}">
											
											<c:set var="cnt" value="0"/>
											
											<form id="searchForm_${cafe.cafeId}" method="post" action="" onsubmit="return false">  
											
											<input name="recordCount_${cafe.cafeId}" id="recordCount_${cafe.cafeId}" type="hidden" value="3" />
											<input name="currentCount_${cafe.cafeId}" id="currentCount_${cafe.cafeId}" type="hidden" value="3" />
				
											<!--cafelist1 Start-->
											<div class="cafeTitle_1">
												<div class="title ellipsis"><a href="<c:url value='/lightpack/cafe/main/cafe.do'/>?cafeId=${cafe.cafeId}" title="${cafe.cafeName}">${cafe.cafeName}</a></div>
												<div class="cafe_text_1">${cafe.memberLevelName} 
													<a href="#a" onclick="javascript:leavelCafe('${cafe.cafeId}','${cafe.memberLevel}');"><span class="cafe_block_1"><span class="cafe_tit_1"><ikep4j:message pre='${prefix}' key='leave' /></span></span></a></div>
											</div>
											
											<!--
											<div class="cafe_list_1" id="itemDiv_${cafe.cafeId}">
												
												<c:choose>
												    <c:when test="${empty cafe.boardItemList}">
														<ul>
															<li class="cafe_text_2">
																<ikep4j:message pre='${prefix}' key='list.empty' />
															</li>
														</ul>				        
												    </c:when>
												    <c:otherwise>
														<c:forEach var="boardItem" items="${cafe.boardItemList}" varStatus="status">
															
															<ul>
																<li class="cafe_text_2 ellipsis">
																	<a href="#a" onclick="parent.parent.showMainFrameDialog('<c:url value='/lightpack/cafe/board/boardItem/readBoardItemLinkView.do?boardId=${boardItem.boardId}&itemId=${boardItem.itemId}'/>&docPopup=true', '${board.boardName}', 800, 500);">
																	${boardItem.title}</a></li>
																<li>
																	<span class="corporateViewInfo pr0">
																		<span><a href="#a" onclick="iKEP.showUserContextMenu(this, '${boardItem.registerId}', 'bottom')">${boardItem.registerName}</a></span>
																		<span><ikep4j:timezone pattern="yyyy.MM.dd" date="${boardItem.registDate}"/></span>
																	</span>								
																</li>
															</ul>
															
															<c:set var="cnt" value="${status.count}"/>
															
														</c:forEach>				      
												    </c:otherwise> 
												</c:choose>  
									
																							
											</div>		
											
											-->
											
											<c:if test="${cnt == 3}">
												<!--blockButton_3 Start-->
												<div class="blockButton_3 w95 pl5" id="moreDiv_${cafe.cafeId}" onclick="getMoreItem('${cafe.cafeId}')"> 
													<a class="button_3" href="#a"><span><ikep4j:message pre='${prefix}' key='list.more' /> <img src="<c:url value='/base/images/icon/ic_more_ar.gif'/>" alt="" /></span></a>				
												</div>
												<!--//blockButton_3 End-->
												<!--cafelist1 End-->	
											</c:if>
											
											</form>
											
										</c:forEach>				      
								    </c:otherwise> 
								</c:choose>  
							
							
								<!--cafelist2 Start-->
								<div class="cafeTitle_2">
									<ikep4j:message pre="${prefix}" key="closeCafe" />
								</div>
								<div class="cafe_list_2">
									<ul>
									
										<c:choose>
										    <c:when test="${empty closeCafeList}">
										    	<div class="cafeTitle_1">
													<ikep4j:message pre='${prefix}' key='list.empty' />
												</div>
										    </c:when>
										    <c:otherwise>
												<c:forEach var="cafe" items="${closeCafeList}">
													
													<div class="cafeTitle_1">
														<div class="ellipsis">${cafe.cafeName}</div>
														<div class="cafe_text_1">${cafe.memberLevelName}</div>
													</div>
											
												</c:forEach>				      
										    </c:otherwise> 
										</c:choose>  
									</ul>
								</div>		
								<!--cafelist2 End-->									
																														
							</div>			
							
						
					
					
					
<script type="text/javascript" language="javascript">
<!-- 

(function($) {
	
	$jq(document).ready(function() {
	
	});
	
	
})(jQuery);  

//-->
</script>
					