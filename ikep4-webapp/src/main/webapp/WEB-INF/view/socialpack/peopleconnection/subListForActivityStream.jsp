<%@ include file="/base/common/taglibs.jsp"%>
<%@ taglib uri="/WEB-INF/tld/ikep4j.tld" prefix="ikep4j" %> 

<c:set var="user" value="${sessionScope['ikep.user']}" />

<c:forEach var="activity" items="${searchResult.entity}">
	<li class="dot">
		<div class="People_list_day">
			<span>${ikep4j:getTimeInterval(activity.activityDate, user.localeCode)}</span>
		</div>
		<div class="People_list_section">
			<span>${activity.itemTypeName}</span>
		</div>							
		<div class="People_list_Photo_info">
			<span class="People_list_photo_name">${activity.activityDescription}</span>
			<div class="People_list_ViewInfo">
				<span><ikep4j:timezone pattern="yyyy-MM-dd HH:mm:ss" date="${activity.activityDate}"/></span>
			</div>
		</div>														
	</li>
</c:forEach>				      
						
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
				
				
