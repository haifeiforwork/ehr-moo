<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ include file="/base/common/taglibs.jsp"%>

<%@ page import="com.lgcns.ikep4.collpack.innovation.constants.InnovationConstants" %>

<c:set var="preUi" 			value="ui.collpack.innovation" />


<script type="text/javascript">
var tmpOpen = false;

$jq(document).ready(function() {
	$jq("#menuReportBank")
		.mouseover(function(event){ event.stopPropagation(); $jq('#dropDown').show();})
		.mouseout(function(event){ event.stopPropagation(); $jq('#dropDown').hide();});
	
});

function openInfo(){
	var dialog1 = $jq("#infoDialog").dialog({width:670});  
}
</script>
<a href="main.do"><img src="<c:url value="/base/images/futureplanet/logo.gif"/>" alt="<ikep4j:message pre='${preUi}' key='main.futurePlanet'/>" /></a>
<ul>
	<li class="fFirst"><a href="subMain.do?boardId=<%=InnovationConstants.BOARD_HOT_ISSUE%>"><ikep4j:message pre='${preUi}' key='main.hotIssue'/></a></li>
    <li id="menuReportBank"><a href="#a" ><ikep4j:message pre='${preUi}' key='main.reportBank'/></a>
    <!-- drop-down Menu(2depth) -->
	<div class="dropDown" id="dropDown" style="display:none;">
        <ul>
            <li><a href="subMain.do?boardId=<%=InnovationConstants.BOARD_TECH_OUTLOOK%>"><ikep4j:message pre='${preUi}' key='main.techOutlook'/></a></li>
            <li><a href="subMain.do?boardId=<%=InnovationConstants.BOARD_TECH_INSIDE%>"><ikep4j:message pre='${preUi}' key='main.techInside'/></a></li>
            <li class="fLast"><a href="subMain.do?boardId=<%=InnovationConstants.BOARD_INSIGHT_FOCUS%>"><ikep4j:message pre='${preUi}' key='main.insightFocus'/></a></li>
        </ul>
	</div>
    </li>
    <li><a href="subMain.do?boardId=<%=InnovationConstants.BOARD_CREATIVE_THINKING%>"><ikep4j:message pre='${preUi}' key='main.creativeThinking'/></a></li>
    <li><a href="subMain.do?type=plan"><ikep4j:message pre='${preUi}' key='main.seminarInfo'/></a></li>
    <li><a href="subMain.do?type=discussion"><ikep4j:message pre='${preUi}' key='main.discussionRoom'/></a></li>
    <li><a href="subMain.do?type=idea"><ikep4j:message pre='${preUi}' key='main.ideaSquare'/></a></li>
</ul>


<div id="infoDialog" style="display:none;"><img src="<c:url value="/base/images/futureplanet/futureplanet_info.gif"/>" alt="<ikep4j:message pre='${preUi}' key='main.infoFuturePlanet'/>" /></div>
