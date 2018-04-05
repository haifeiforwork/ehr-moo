<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/base/common/taglibs.jsp"%>

<%@ taglib uri="/WEB-INF/tld/ikep4j.tld" prefix="ikep4j" %> 

<%-- 메시지 관련 Prefix 선언 Start --%> 
<c:set var="preApCommList"    value="ui.approval.work.apprlist" />
<c:set var="preList"    			value="ui.approval.work.aplist.listTodo" />
<c:set var="preButton"  			value="ui.approval.common.button" />
<c:set var="preSearch"  			value="ui.approval.common.searchCondition" />
<c:set var="preWorkplace"     value="ui.workflow.workplace.worklist" />
<c:set var="preWorkplaceCommon"  value="ui.workflow.workplace.common" />
 <%-- 메시지 관련 Prefix 선언 End --%>
 <script type="text/javascript" src="<c:url value="/base/js/jquery/jquery.validate-1.8.min.js"/>"></script>
<c:choose>
	<c:when test="${searchCondition.listType eq 'myRequestList'}">
		<c:set var="formActUrl"     value="/approval/work/apprlist/popListApprMyRequest.do" />
	</c:when>
	<c:when test="${searchCondition.listType eq 'myRequestListComplete'}">
		<c:set var="formActUrl"     value="/approval/work/apprlist/popListApprMyRequestComplete.do" />
	</c:when>
	<c:when test="${searchCondition.listType eq 'listApprDocAllAdmin'}">
		<c:set var="formActUrl"     value="/approval/work/apprlist/listApprDocAllAdmin.do" />
	</c:when>
	<c:when test="${searchCondition.listType eq 'listApprDocAllUser'}">
		<c:set var="formActUrl"     value="/approval/work/apprlist/listApprDocAllUser.do" />
	</c:when>
	<c:when test="${searchCondition.listType eq 'listApprDocSecurity'}">
		<c:set var="formActUrl"     value="/approval/work/apprlist/listApprDocSecurity.do" />
	</c:when>
	<c:otherwise>
		<c:set var="formActUrl"     value="/approval/work/apprlist/popListApprMyRequest.do" />
	</c:otherwise>
</c:choose>	
			 
<script type="text/javascript">
<!-- 


(function($) {

	
	/**
	 * onload시 수행할 코드
	 */
	$(document).ready(function() {
		
		$jq("#confirmBtn").click(function() {
			
			var $sel = $jq("#iframeLink").contents().find("input[name=apprId]:checked");
			if($sel.length <1) {
				return;	
			}
				  
			var data = new Array();
			var i=0;
			$jq("#iframeLink").contents().find("input[name=apprId]:checked").each(function() {
				var $dataDiv = $(this).next("div.none");
   				data.push({
   					apprId: this.value,
   					apprTitle: $("span.data_apprTitle", $dataDiv).text()
   				});
    			i++;
	    	});
		      
		    iKEP.returnPopup(data);
	    });
		
		$jq("#cancelBtn").click(function() {
			iKEP.closePop();			
		});
		
	});
})(jQuery);  
//-->
</script>

<!--popup Start-->
<div id="popup">

	<!--popup_title Start-->
	<div id="popup_title_2">
        <div class="popup_bgTitle_l"></div>
		<h1><ikep4j:message pre='${preApCommList}.pageTitle' key='${searchCondition.listType}' /></h1>
		<a href="javascript:iKEP.closePop()"><span><ikep4j:message pre='${preButton}' key='close' /></span></a>
		<div class="popup_bgTitle_l"></div>
	</div>
	<!--//popup_title End-->	
	
	<!--popup_contents Start-->
	<div id="popup_contents">
			

		<iframe id="iframeLink" class="ui-layout-center" width="100%" height="470" frameborder="0" scrolling="auto" 
			src="<c:url value='/approval/work/apprlist/popListApprMyRequestCompleteSub.do'/>">
		</iframe>

		<div class="clear"></div>
		
		<!--blockButton Start-->
		<div class="blockButton"> 
			<ul>
				<li><a class="button" href="#" id="confirmBtn"><span><ikep4j:message pre='${preButton}' key='confirm' /></span></a></li>
				<li><a class="button" href="#" id="cancelBtn"><span><ikep4j:message pre='${preButton}' key='cancel' /></span></a></li>
			</ul>
		</div>
		<!--//blockButton End-->
	
	</div>
	<!--//popup_contents End-->
 
	<!--popup_footer Start-->
	<div id="popup_footer"></div>
	<!--//popup_footer End-->

</div>
<!--//popup End-->
		
