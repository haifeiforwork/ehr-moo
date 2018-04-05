<%@ include file="/base/common/taglibs.jsp"%>
<%@ taglib uri="/WEB-INF/tld/ikep4j.tld" prefix="ikep4j" %> 

<c:set var="preHeader"  value="ui.support.activitystream.header" /> 
<c:set var="preList"    value="ui.support.activitystream.list" />
<c:set var="preDetail"  value="ui.support.activitystream.detail" />
<c:set var="preButton"  value="ui.support.activitystream.button" /> 
<c:set var="preMessage" value="ui.support.activitystream.message" />
<c:set var="preSearch"  value="ui.communication.common.searchCondition" /> 

<c:set var="user" value="${sessionScope['ikep.user']}" />
<c:set var="portal" value="${sessionScope['ikep.portal']}" />
						
<c:forEach var="item" items="${itemList}">

	<li>
		<div class="Intel_content_photo">
			<span><a href="#a"><img src="" alt="image" onerror="this.src='<c:url value='/base/images/common/photo_50x50.gif'/>'" /></a></span>
		</div>
		<div class="Intel_content_con">
			<p class="lntel_contentTitle ellipsis">
				<span class="cate_block_3"><span class="cate_tit_3">RSS</span></span>
				<span><a href="#a" onclick="winOpen('${item.itemUrl}')">${item.itemTitle}</a></span>
			</p>
			<p class="Intel_content_Info">
				<span class="Intel_content_Info_name">
				<a href="#a" >
					${item.itemRegister}
				</a></span>
				<span>

				</span>
				<span><ikep4j:timezone date="${item.itemPublishDate}" pattern="message.support.tagging.timezone.dateformat.type2" keyString="true"/></span>
				<!--
				<span><ikep4j:message pre="${preDetail}" key="hits"/><strong>0</strong></span>
				<span><ikep4j:message pre="${preDetail}" key="recommend"/> <strong>0</strong></span>
				<span><ikep4j:message pre="${preDetail}" key="reply"/> <strong>0</strong></span>
				<span><ikep4j:message pre="${preDetail}" key="share"/> <strong>0</strong></span>
				  -->
			</p>	
		</div>
		<div class="clear"></div>
	</li>

</c:forEach>																																																					
						
<script type="text/javascript" language="javascript">
(function($) {
	
	$jq(document).ready(function() {
		
		$jq("#recordCount").val("${searchCondition.recordCount}");
		$jq("#currentCount").val("${searchCondition.currentCount}");
		
		setMoreDiv();
	});
	
})(jQuery); 
</script>