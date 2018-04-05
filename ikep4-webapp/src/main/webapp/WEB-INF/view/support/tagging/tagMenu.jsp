<%@ include file="/base/common/taglibs.jsp"%>
<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ page import="com.lgcns.ikep4.support.tagging.constants.TagConstants" %>
<jsp:useBean id="CommonConstant" class="com.lgcns.ikep4.support.base.constant.JSTLCommonConstant"/>

<c:set var="preUi" 	value="ui.support.tagging" />

<script type="text/javascript">
//<![CDATA[
           
$jq(document).ready(function(){
		
	$jq("body").addClass("bg_leftline");
});


           
function viewPopUpProfile(targetUserId){
	var pageURL = "<c:url value='/support/profile/getProfilePopup.do' />" + "?targetUserId=" + targetUserId;
	iKEP.popupOpen( pageURL , {width:800, height:280 , callback:function(result) {} });

}
//]]>
</script>

<h1 class="none">Left Menu</h1>	
<h2><a href="mainMyTag.do">Tagging</a></h2>
<div class="left_fixed">
<ul>		
	<li class="liFirst no_child"><a href="mainMyTag.do" title="<ikep4j:message pre='${preUi}' key='menuMyTag'/>"><ikep4j:message pre='${preUi}' key='menuMyTag'/></a></li>
	
	<c:if test="${CommonConstant.PACKAGE_VERSION eq CommonConstant.IKEP_VERSION_FULL}">
		<li class="no_child"><a href="listCollTag.do?tagItemType=<%=TagConstants.ITEM_TYPE_WORKSPACE%>" title="<ikep4j:message pre='${preUi}' key='menuCollaborationTag'/>"><ikep4j:message pre='${preUi}' key='menuCollaborationTag'/></a></li>
		<li class="no_child"><a href="listAllTag.do?tagItemType=<%=TagConstants.ITEM_TYPE_PROFILE_PRO %>" title="<ikep4j:message pre='${preUi}' key='menuWholeTag'/>"><ikep4j:message pre='${preUi}' key='menuWholeTag'/></a></li>
	</c:if>
	<li class="no_child"></li>
</ul>
</div>
