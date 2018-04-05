<%@ include file="/base/common/taglibs.jsp"%>
<%@ taglib uri="/WEB-INF/tld/ikep4j.tld" prefix="ikep4j" %> 

<%-- 메시지 관련 Prefix 선언 Start --%> 
<c:set var="prefix"  value="message.lightpack.cafe.main" />
 <%-- 메시지 관련 Prefix 선언 End --%>

<c:set var="user" value="${sessionScope['ikep.user']}" />
					
					
					<form id="searchForm" method="post" action="" onsubmit="return false">  
					
					<spring:bind path="searchCondition.sortColumn">
						<input name="${status.expression}" type="hidden" value="${status.value}" title="sortColumn" />
					</spring:bind> 		
					<spring:bind path="searchCondition.sortType">
						<input name="${status.expression}" type="hidden" value="${status.value}" title="sortType" />
					</spring:bind> 
					<spring:bind path="searchCondition.categoryId">
						<input name="${status.expression}" type="hidden" value="${status.value}" title="categoryId" />
					</spring:bind> 
					<spring:bind path="searchCondition.searchWord">
						<input name="${status.expression}" type="hidden" value="${status.value}" title="searchWord" />
					</spring:bind> 
					
					<!--expert_bl Start-->
					<div class="expert_bl block">
					
						<!--subTitle_2 Start-->
						<div class="cafeSubTitle mt15 border_b1">
							<h3><img src="<c:url value='/base/images/icon/ic_cafe_05.gif'/>" alt="" /> 
								
								<c:choose>
									<c:when test="${catetory.categoryName != null}"> 
										${catetory.categoryName}
									</c:when> 
									<c:otherwise>
										<ikep4j:message pre="${prefix}" key="totalCafe" />
									</c:otherwise>
								</c:choose>
								
							</h3>
							<div class="totalNum ml5"><span>(${searchResult.recordCount})</span></div>
							<div class="cafe_sort">
								<span class="cafe_sort_smenu">
									<span <c:if test="${searchCondition.sortColumn eq 'cafeName'}">class="current"</c:if>><a href="#a" onclick="javascript:getSubListForCafeBySortColumn('cafeName','${searchCondition.sortType}');">
										<ikep4j:message pre="${prefix}" key="cafeName" /> 
										<c:choose>
										<c:when test="${searchCondition.sortColumn eq 'cafeName' and searchCondition.sortType eq 'DESC'}"><img alt="" src="<c:url value='/base/images/icon/ic_cafesort_down_on.gif'/>"></c:when>
										<c:when test="${searchCondition.sortColumn eq 'cafeName' and searchCondition.sortType eq 'ASC'}"><img alt="" src="<c:url value='/base/images/icon/ic_cafesort_up_on.gif'/>"></c:when> 
										<c:otherwise><img alt="" src="<c:url value='/base/images/icon/ic_cafesort_down.gif'/>"></c:otherwise> 
										</c:choose>
									</a></span>
									<img src="<c:url value='/base/images/common/bar_info.gif'/>" alt="" />
									<span <c:if test="${searchCondition.sortColumn eq 'memberCount'}">class="current"</c:if>><a href="#a" onclick="javascript:getSubListForCafeBySortColumn('memberCount','${searchCondition.sortType}');">
										<ikep4j:message pre="${prefix}" key="member" /> 
										<c:choose>
										<c:when test="${searchCondition.sortColumn eq 'memberCount' and searchCondition.sortType eq 'DESC'}"><img alt="" src="<c:url value='/base/images/icon/ic_cafesort_down_on.gif'/>"></c:when>
										<c:when test="${searchCondition.sortColumn eq 'memberCount' and searchCondition.sortType eq 'ASC'}"><img alt="" src="<c:url value='/base/images/icon/ic_cafesort_up_on.gif'/>"></c:when> 
										<c:otherwise><img alt="" src="<c:url value='/base/images/icon/ic_cafesort_down.gif'/>"></c:otherwise> 
										</c:choose>
									</a></span>
									<img src="<c:url value='/base/images/common/bar_info.gif'/>" alt="" />
									<span <c:if test="${searchCondition.sortColumn eq 'boardItemCount'}">class="current"</c:if>><a href="#a" onclick="javascript:getSubListForCafeBySortColumn('boardItemCount','${searchCondition.sortType}');">
										<ikep4j:message pre="${prefix}" key="item" /> 
										<c:choose>
										<c:when test="${searchCondition.sortColumn eq 'boardItemCount' and searchCondition.sortType eq 'DESC'}"><img alt="" src="<c:url value='/base/images/icon/ic_cafesort_down_on.gif'/>"></c:when>
										<c:when test="${searchCondition.sortColumn eq 'boardItemCount' and searchCondition.sortType eq 'ASC'}"><img alt="" src="<c:url value='/base/images/icon/ic_cafesort_up_on.gif'/>"></c:when> 
										<c:otherwise><img alt="" src="<c:url value='/base/images/icon/ic_cafesort_down.gif'/>"></c:otherwise> 
										</c:choose>
									</a></span>
									<img src="<c:url value='/base/images/common/bar_info.gif'/>" alt="" />
									<span <c:if test="${searchCondition.sortColumn eq 'openDate'}">class="current"</c:if>><a href="#a" onclick="javascript:getSubListForCafeBySortColumn('openDate','${searchCondition.sortType}');">
										<ikep4j:message pre="${prefix}" key="date" /> 
										<c:choose>
										<c:when test="${searchCondition.sortColumn eq 'openDate' and searchCondition.sortType eq 'DESC'}"><img alt="" src="<c:url value='/base/images/icon/ic_cafesort_down_on.gif'/>"></c:when>
										<c:when test="${searchCondition.sortColumn eq 'openDate' and searchCondition.sortType eq 'ASC'}"><img alt="" src="<c:url value='/base/images/icon/ic_cafesort_up_on.gif'/>"></c:when> 
										<c:otherwise><img alt="" src="<c:url value='/base/images/icon/ic_cafesort_down.gif'/>"></c:otherwise> 
										</c:choose>
									</a></span>
								</span>					
							</div>								
						</div>
						<!--//subTitle_2 End-->	
						
						<div class="clear"></div>
						
						<ul>
						
							<c:choose>
							    <c:when test="${searchResult.emptyRecord}">
									<li>
										<ikep4j:message pre='${prefix}' key='list.empty' />
									</li>				        
							    </c:when>
							    <c:otherwise>
									<c:forEach var="cafe" items="${searchResult.entity}">
									
										<li>
											<div class="expert_photo">
												<span><a href="<c:url value='/lightpack/cafe/main/cafe.do'/>?cafeId=${cafe.cafeId}"><img src="<c:url value='/support/fileupload/downloadFile.do?fileId=${cafe.imageId}&thumbnailYn=Y'/>" alt="image"  onerror="this.src='<c:url value='/base/images/common/photo_90x70.gif'/>'" /></a></span>
											</div>
											<div class="expert_Photo_info">
												<span class="expert_photo_name"><a href="<c:url value='/lightpack/cafe/main/cafe.do'/>?cafeId=${cafe.cafeId}"><div class="ellipsis">${cafe.cafeName}</div></a></span>
												<div class="expert_photo_con ellipsis">
													<p class="pl0">${ikep4j:replaceQuot(cafe.description)}</p>
												</div>
												<span class="corporateViewInfo">
													<span class="corporateViewInfo_name"><a href="#a" onclick="iKEP.showUserContextMenu(this, '${cafe.sysopId}', 'bottom')">
														<ikep4j:message pre="${prefix}" key="owner" /> : ${cafe.sysopName}</a></span>
													<span><ikep4j:message pre="${prefix}" key="member" /> : ${cafe.memberCount}</span>
													<span><ikep4j:message pre="${prefix}" key="item" /> : ${cafe.boardItemCount}</span>
													<span><ikep4j:message pre="${prefix}" key="date" /> : <ikep4j:timezone pattern="yyyy.MM.dd" date="${cafe.openDate}"/></span>
												</span>
											</div>														
										</li>
										
									</c:forEach>				      
							    </c:otherwise> 
							</c:choose>  
							
						</ul>
						
						<!--Page Numbur Start--> 
						<spring:bind path="searchCondition.pageIndex">
							<ikep4j:pagination searchFormId="searchForm" ajaxEventFunctionName="getSubListForCafe" pageIndexInput="${status.expression}" searchCondition="${searchCondition}" />
							<input name="${status.expression}" type="hidden" value="${status.value}" title="<ikep4j:message pre='${preSearch}' key='${status.expression}'/>" />
						</spring:bind> 
						<!--//Page Numbur End-->
						
					</div>
					<!--//expert_bl End-->
							
					</form>	
					
<script type="text/javascript" language="javascript">
<!-- 

(function($) {
	
	
	
})(jQuery);  

//-->
</script>
					