<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%-- Tab Lib Start --%> 
<%@ include file="/base/common/taglibs.jsp"%>
<%-- Tab Lib End --%> 

<c:set var="prefix" value="message.portal.portlet.rss.configView"/>

<script type="text/javascript">
<!--   
$jq(document).ready(function() {
	var portletRssAddFormDiv = $jq("div[id=${portletRss.portletConfigId}]");
	
	$jq("#rssAddButton", portletRssAddFormDiv).click(function() {
		var rssUrl = $jq("input[name=rssUrl]", portletRssAddFormDiv).val();
		
		if(rssUrl==''){
			alert("<ikep4j:message pre="${prefix}" key="blankMsg" />");
			return;
		}
		
		$jq.ajax({
			url : '<c:url value="/support/rss/channel/checkChannel.do" />',
			data : {channelUrl:rssUrl},
			type : "post",
			dataType : "html",
			success : function(result) {
				if(result != 'urlError') {
					var listCount = $jq("select[name=listCount]", portletRssAddFormDiv).val();
					
					$jq.ajax({
						url : '<c:url value="/portal/portlet/rss/createPortletRss.do"/>',
						data : {portletConfigId:'${portletRss.portletConfigId}', rssUrl:rssUrl, listCount:listCount},
						type : "post",
						dataType : "html",
						success : function(result) {
							portletRssAddFormDiv.parent().parent().parent().trigger("click:reload");
							alert("<ikep4j:message pre="${prefix}" key="alert.saveSuccess" />");
						},
						error : function() {alert('error');}
					});
				} else {
					alert("<ikep4j:message pre="${prefix}" key="alert.failUrl" />");
				}
			},
			error : function() {alert('error');}
		});
	});
	
	$jq("#verificationButton", portletRssAddFormDiv).click(function() {
		var rssUrl = $jq("input[name=rssUrl]", portletRssAddFormDiv).val();
		
		if(rssUrl==''){
			alert("<ikep4j:message pre="${prefix}" key="blankMsg" />");
			return;
		}
		
		$jq.ajax({
			url : '<c:url value="/support/rss/channel/checkChannel.do" />',
			data : {channelUrl:rssUrl},
			type : "post",
			dataType : "html",
			success : function(result) {
				if(result != 'urlError') {
					alert("<ikep4j:message pre="${prefix}" key="alert.verificationMessage" />");
				} else {
					alert("<ikep4j:message pre="${prefix}" key="alert.failUrl" />");
				}
			},
			error : function() {alert('error');}
		});
	});
});
//-->
</script>

<h4 class="guidetitle_han"><ikep4j:message pre="${prefix}" key="title" /></h4>
<!--blockDetail Start-->
<div class="blockDetail" id="${portletRss.portletConfigId}">
	<table summary="<ikep4j:message pre="${prefix}" key="summary" />">
		<caption></caption>
		<tbody>
			<spring:bind path="portletRss.rssUrl">
			<tr>
				<th scope="row"><span class="colorPoint">*</span><ikep4j:message pre="${prefix}" key="url" /></th>
				<td>
					<input name="${status.expression}" title="URL" class="inputbox w70" type="text" value="${status.value}"/>
					<a id="verificationButton" class="button_s" href="#a" title="<ikep4j:message pre="${prefix}" key="verification" />"><span><ikep4j:message pre="${prefix}" key="verification" /></span></a>
				</td>
			</tr>
			</spring:bind>
			<spring:bind path="portletRss.listCount">
			<tr>
				<th scope="row"><ikep4j:message pre="${prefix}" key="count" /></th>
				<td>
					<select name="${status.expression}" title="<ikep4j:message pre="${prefix}" key="select.count" />">
						<option value="3" <c:if test="${3 eq status.value}">selected="selected"</c:if>>3</option>
						<option value="5" <c:if test="${0 eq status.value || 5 eq status.value}">selected="selected"</c:if>>5</option>
						<option value="7" <c:if test="${7 eq status.value}">selected="selected"</c:if>>7</option>
						<option value="10" <c:if test="${10 eq status.value}">selected="selected"</c:if>>10</option>
					</select>
				</td>
			</tr>
			</spring:bind>
		</tbody>
	</table>
	<div class="blockButton" style="padding-top: 5px;"> 
		<ul>
			<li><a id="rssAddButton" class="button_s" href="#a"><span><ikep4j:message pre="${prefix}" key="save" /></span></a></li>
		</ul>
	</div>
</div>