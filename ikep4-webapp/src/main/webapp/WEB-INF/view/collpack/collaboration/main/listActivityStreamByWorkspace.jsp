<%@ include file="/base/common/taglibs.jsp"%>
<%-- 메시지 관련 Prefix 선언 Start --%> 
<c:set var="prefix"  value="message.collpack.collaboration.workspace.main" />

<%-- 메시지 관련 Prefix 선언 End --%>
<c:set var="user" value="${sessionScope['ikep.user']}" />
<c:if test="${!empty searchResult.entity}">
	<c:forEach var="activity" items="${searchResult.entity}">	
		<li class="dot">										
			<div class="People_list_day">
				<span>
					<div class="ellipsis"><img align="middle" src="<c:url value="/base/images/icon/ic_point_09.gif"/>"> ${ikep4j:getTimeInterval(activity.activityDate, user.localeCode)}</div>
				</span>
			</div>							
			<div class="People_list_Photo_info">
				<span class="People_list_photo_name">
					${activity.activityDescription}
				</span>
				<%--span class="People_list_ViewInfo">
					<span>
						${activity.itemTypeName}
					</span>
				</span--%>
			</div>
		</li>	
	</c:forEach>			
</c:if>
<c:if test="${empty searchResult.entity && searchCondition.pageIndex==1}">
		<li class="dot">										
				<span>
					<div class="textCenter" ><ikep4j:message pre="${prefix}" key="portlet.noDataActivity" /></div>
				</span>					
		</li>																																																							
</c:if>						
<script type="text/javascript" language="javascript">

(function($) {
	
	// onload시 수행할 코드
	$jq(document).ready(function() {
		$jq("#recordCount").val("${searchCondition.recordCount}");
		$jq("#currentCount").val("${searchCondition.currentCount}");

		setMoreDiv();
	});
	
	
})(jQuery);  


</script>		
				
				
