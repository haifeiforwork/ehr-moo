<%@ page contentType="text/html; charset=utf-8"%>
<%@ include file="/base/common/taglibs.jsp"%> 
<c:set var="user" value="${sessionScope['ikep.user']}" />
<script type="text/javascript">
(function($) {
	
	$(document).ready(function() {
		$.post("<c:url value='/collpack/kms/main/portlet/searchSimpleTab.do'/>", {"portletLayoutId" : "5"}) 
		.success(function(data) {
			$addFullHtml = $("#center").append
			("<div class='kmbox' style='padding-top:0px;padding-left:0px;padding-right:0px;padding-bottom:0px;border-top:0px;border-bottom:0px;border-left:0px;border-right:0px;'>						"
				+"	<div id='blockCenter' style='padding-top:0px'>	                "
				+ data
				+"	</div>                                  "
				+"</div>                                    "
			);			
		})	
		  
	});
	
})(jQuery);
</script>
<div id="center" style="height:140px"></div>