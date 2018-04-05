<%@ include file="/base/common/taglibs.jsp"%>
<%@ taglib uri="/WEB-INF/tld/ikep4j.tld" prefix="ikep4j" %> 

<%-- 메시지 관련 Prefix 선언 Start --%> 
<c:set var="preHeader"  value="ui.support.activitystream.header" /> 
<c:set var="preList"    value="ui.support.activitystream.list" />
<c:set var="preDetail"  value="ui.support.activitystream.detail" />
<c:set var="preButton"  value="ui.support.activitystream.button" /> 
<c:set var="preMessage" value="ui.support.activitystream.message" />
<c:set var="preSearch"  value="ui.communication.common.searchCondition" /> 
 <%-- 메시지 관련 Prefix 선언 End --%>

<c:set var="user" value="${sessionScope['ikep.user']}" />

											
						
									<c:forEach var="activity" items="${searchResult.entity}">
									
									<li class="dot">
										
										<div class="People_list_day">
											<span>
												<div class="ellipsis">${ikep4j:getTimeInterval(activity.activityDate, user.localeCode)}</div>
											</span>
										</div>							
										<div class="People_list_Photo_info">
											<span class="People_list_photo_name">
												${activity.activityDescription}
											</span>
											<span class="People_list_ViewInfo">
												<span>
													${activity.itemTypeName}
												</span>
											</span>
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
				
				
