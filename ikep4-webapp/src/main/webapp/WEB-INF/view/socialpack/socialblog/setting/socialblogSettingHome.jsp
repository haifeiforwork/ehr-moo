<%@ include file="/base/common/taglibs.jsp"%>
<%@ page language="java" errorPage="/base/common/error.jsp"  pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>

<%-- 메시지 관련 Prefix 선언 Start --%> 
<c:set var="preManage" value="ui.socialpack.socialblog.management" />
<c:set var="preButton" value="ui.socialpack.socialblog.common.button" />

<%-- 메시지 관련 Prefix 선언 End --%>
<c:set var="user" value="${sessionScope['ikep.user']}" />
<script type="text/javascript">

(function($){
	
	$jq(document).ready(function() {
		
		// 화면 로딩시 각각 페이지 호출 시작
		$jq.ajax({
		    url : '<c:url value="/socialpack/socialblog/setting/listCategory.do?blogOwnerId=${user.userId}"/>',
		    success : function(result) {
		    	$jq(".blog_layout").html(result);
		    }
		});
		// 화면 로딩시 각각 페이지 호출 종료

	});
	
})(jQuery);

</script>

	<!--popup_title Start-->
	<div id="popup_title">
		<h1><ikep4j:message pre='${preManage}' key='title' /></h1>
		<a href="javascript:iKEP.returnPopup();"><span><ikep4j:message pre='${preButton}' key='close' /></span></a>
	</div>
	<!--//popup_title End-->

	<!--popup_contents Start-->
	<div id="popup_contents">
		<!-- Modal window Start -->
		<!-- 사용시class="none"으로 설정 -->
		<div class="" id="layer_p" title="<ikep4j:message pre='${preManage}' key='title' />">
		
			<div class="blog_layout">
			
			</div>
			
		</div>	
		<!-- //Modal window End -->
	</div>

