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
												
													<div class="cafeTitle_1">
															<div class="title ellipsis"><a href="<c:url value='/lightpack/cafe/main/cafe.do'/>?cafeId=${cafe.cafeId}" title="${cafe.cafeName}">${cafe.cafeName}</a></div>
															<div class="cafe_text_1">
															<a href="#a" onclick="javascript:cancelCafe('${cafe.cafeId}','${cafe.memberLevel}');"><span class="cafe_block_1"><span class="cafe_tit_1"><ikep4j:message pre='${prefix}' key='cancel' /></span></span></a></div>
													</div>
											
												</c:forEach>				      
										    </c:otherwise> 
										</c:choose>  
							
							</div>
						
					
					
					
<script type="text/javascript" language="javascript">
<!-- 

(function($) {
	
	
	
})(jQuery);  

//-->
</script>
					