<%@ include file="/base/common/taglibs.jsp"%>
<%@ taglib uri="/WEB-INF/tld/ikep4j.tld" prefix="ikep4j" %> 

<%-- 메시지 관련 Prefix 선언 Start --%> 
<c:set var="prefix"  value="message.lightpack.cafe.main" />
 <%-- 메시지 관련 Prefix 선언 End --%>

<c:set var="user" value="${sessionScope['ikep.user']}" />
					

							<div class="subTitle_2">
								<h3><ikep4j:message pre="${prefix}" key="newCafe" /></h3>
							</div>
							<ul class="coll_list">
							
								
								<c:choose>
								    <c:when test="${empty cafeNewList}">
										<li class="pt5">
											<ikep4j:message pre='${prefix}' key='list.empty' />
										</li>				        
								    </c:when>
								    <c:otherwise>
										<c:forEach var="newcafe" items="${cafeNewList}">
										
											<li class="pt5">
												<div class="cafe_photo">
													<span><a href="<c:url value='/lightpack/cafe/main/cafe.do'/>?cafeId=${newcafe.cafeId}" title="${newcafe.cafeName}"><img src="<c:url value='/support/fileupload/downloadFile.do?fileId=${newcafe.imageId}&thumbnailYn=Y'/>" alt="image" onerror="this.src='<c:url value='/base/images/common/photo_90x70.gif'/>'" /></a></span>
												</div>							
												<div class="expert_Photo_info pl50">
													<span class="cafe_photo_name">
														<a href="<c:url value='/lightpack/cafe/main/cafe.do'/>?cafeId=${newcafe.cafeId}" title="${newcafe.cafeName}">${newcafe.cafeName}</a></span>
														<span class="cafe_photo_con">${ikep4j:replaceQuot(newcafe.description)}</span>
													<span class="cafeViewInfo pr0">
														<span><a href="#a" onclick="iKEP.showUserContextMenu(this, '${cafe.sysopId}', 'bottom')">
															<ikep4j:message pre="${prefix}" key="owner" /> : ${newcafe.sysopName}</a></span>
														<span><ikep4j:timezone pattern="yyyy.MM.dd" date="${newcafe.openDate}"/></span>
													</span>
												</div>								
											</li>
											
										</c:forEach>				      
								    </c:otherwise> 
								</c:choose>  

								
																									
							</ul>
						
					
					
					
<script type="text/javascript" language="javascript">
<!-- 

(function($) {
	
	
	
})(jQuery);  

//-->
</script>
					