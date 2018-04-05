<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/base/common/taglibs.jsp"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="/WEB-INF/tld/ikep4j.tld" prefix="ikep4j" %> 
<c:set var="user" value="${sessionScope['ikep.user']}" />
<c:set var="portal" value="${sessionScope['ikep.portal']}" />
<c:set var="preHeader"  value="ui.support.searchpreprocessor.header" /> 
<c:set var="preButton"  value="ui.support.searchpreprocessor.common.button" /> 
<c:set var="preLeft"  value="ui.support.searchpreprocessor.common.left" /> 
<c:set var="preField"  value="message.support.searchpreprocessor.field" /> 
<script type="text/javascript" src="<c:url value='/base/js/searchpreprocessor/searchpreprocessor.js'/>"></script>
<script type="text/javascript">
<!--
<c:choose>
<c:when test="${user.localeCode == portal.defaultLocaleCode}">
 var myLocale =true;
</c:when>
<c:otherwise>
var myLocale =false; 
</c:otherwise>
</c:choose>
//-->
</script> 
<script type="text/javascript">
<!--
(function($) {
	$(document).ready(function() {
		var startIndex ='${result.endIndex}';
		var oldIndex=0;
		$("#searchBtn").click(function(){
			$.post('<c:url value="/support/searchpreprocessor/searchhistory/ajax/recommend.do"/>',
			 	{'startIndex':startIndex,'next':'20'})
			 .success(function(data) {
				 if( !data.next ){
					 $("#nextButtonDiv").hide();
				 }
				 //start index new setting
				 oldIndex=startIndex;
				 startIndex = data.endIndex;
				 
				 $(data.historyList).each(function(){
					 oldIndex++;
					 this.index =oldIndex;
					 $.tmpl("rowtemplet",this).appendTo( $("#viewRow") );
				 });
			 })
			 .error(function(event, request, settings) { alert("error"); }); 
		});	
		
		$.template("rowtemplet", '<tr class="msg_unread">'+
									   '<td scope="col" width="5%" class="tdFirst textRight_p20">\${index}</td>'+
									   '<td scope="col" width="*" class="textLeft_p20"><a href="#a" onclick="searchCall(\'${searchKeyword}\');">\${searchKeyword}</a></td>'+
									   '<td scope="col" width="10%" class="textLeft_p20 tdLast">\${frequencyCount}<ikep4j:message key="ui.support.searchpreprocessor.count" /></td>'+
									   '</tr>');
	});
})(jQuery);
//-->
</script> 
<h1 class="none"><ikep4j:message pre="${preLeft}" key="2" /></h1> 
<!--pageTitle Start-->

	
<!--//pageTitle End-->

<div class="subTitle_3">
	<h3><ikep4j:message pre="${preHeader}" key="subTitle.2" /></h3>
</div>

<!--blockListTable Start-->
<div class="MyContentsTable">
	<table summary="My Document">
		<caption></caption>
		<tbody id="viewRow">
		<c:choose>
			<c:when test="${!empty result.historyList}">
			<c:forEach var="info" items="${result.historyList}"  varStatus="loopStatus">
				<tr class="msg_unread">
					<td scope="col" width="5%" class="tdFirst textRight_p20">${loopStatus.index+1}</td>
					<td scope="col" width="*" class="textLeft_p20"><a href="#a" onclick="searchCall('${info.searchKeyword}');">${info.searchKeyword}(${info.frequencyCount}<ikep4j:message key="ui.support.searchpreprocessor.count" />)</a></td>
					<td scope="col" width="10%" class="textLeft_p20 tdLast">${info.frequencyCount}<ikep4j:message key="ui.support.searchpreprocessor.count" /></td>
				</tr>
			</c:forEach>	
			</c:when>
			<c:otherwise>
				<tr class="msg_unread">
					<td scope="col" colspan="3"><ikep4j:message key="ui.support.searchpreprocessor.emptyRecord" /></td>
				</tr>
			</c:otherwise>
		</c:choose>
			
																																																																																				
		</tbody>
	</table>
</div>
<!--//blockListTable End-->

<!--blockButton_3 Start-->
<c:if test="${result.next}">
<div class="blockButton_3" id="nextButtonDiv"> 
	<a class="button_3" href="#a" id="searchBtn"><span><ikep4j:message pre="${preButton}" key="addview" arguments="20"/><img src="<c:url value='/base/images/icon/ic_more_ar.gif'/>" alt="" /></span></a>				
</div>
</c:if>
<!--//blockButton_3 End-->	

