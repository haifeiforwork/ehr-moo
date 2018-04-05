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
		$("#divTab1").tabs();
		
		$("#dayTabButton").data("option","day");
		$("#weekTabButton").data("option","week");
		$("#monthTabButton").data("option","month");
		$("#colleagueListButton").data("option","colleague");
		
		
		$("#monthSearchBtn").data("endIndex", 0).data("option","month");
		$("#weekSearchBtn").data("endIndex", 0).data("option","week");
		$("#daySearchBtn").data("endIndex", ${result.endIndex}).data("option","day");
		$("#colleagueSearchButton").data("endIndex", 0).data("option","colleague");
		
		$("a[id$='TabButton']").click(function(){
			var setting ={'searchOption':$(this).data("option"),'clear':true};
			
			ajaxCall(setting);
		});
		
		var ajaxCall= function(settings) {
			
				var oldIndex=0;
				var config ={'startIndex':'0','next':'20','searchOption':'today'};
	     		if (settings) $.extend(config, settings); 
	     		var isEmpty=true;
	     		
	     		//alert( JSON.stringify(config));
	     		
				 $.post('<c:url value="/support/searchpreprocessor/searchhistory/ajax/recommendtag.do"/>',
						 config)
				 .success(function(data) {
					 if( !data.next ){
						 $("#"+config.searchOption+"NextButtonDiv").hide();
					 }
					 else
					 {
						 $("#"+config.searchOption+"NextButtonDiv").show();
					 }
					 //start index new setting
					 oldIndex=config.startIndex;
					 $("#"+config.searchOption+"SearchBtn").data("endIndex",data.endIndex);
					 
					 $(data.tagList).each(function(){
						 isEmpty = false;
					 });
					 
					 if( !isEmpty )
					 {
						 if(config.clear)  
							 $("#"+config.searchOption+"ViewRow").empty();
					 }else{
						 if(!config.clear)   
							 alert('<ikep4j:message key="ui.support.searchpreprocessor.emptyRecord" />');
					 }

					 
					 $(data.tagList).each(function(){
						 isEmpty = false;
						 oldIndex++;
						 this.index =oldIndex;
						 $.tmpl("rowtemplet",this).appendTo( $("#"+config.searchOption+"ViewRow") );
					 });
					 
				 })
				 .error(function(event, request, settings) { alert("error"); }); 
	 
	   };
	   
		
		$("a[id$='SearchBtn']").click(function(){
			var setting ={'startIndex':$(this).data("endIndex"),'searchOption':$(this).data("option"),'clear':false};
			
			ajaxCall(setting);
		});	
		
		
		$.template("rowtemplet", '<tr class="msg_unread">'+
									   '<td scope="col" width="5%" class="tdFirst textRight_p20">\${index}</td>'+
									   '<td scope="col" width="*" class="textLeft_p20"><a href="#a" onclick="searchCall(\'${tagName}\');">\${tagName}</a></td>'+
									   '<td scope="col" width="10%" class="textLeft_p20 tdLast">\${tagFrequency}<ikep4j:message key="ui.support.searchpreprocessor.count" /></td>'+
									   '</tr>');
	});
})(jQuery);
//-->
</script> 
<h1 class="none"><ikep4j:message pre="${preLeft}" key="5" /></h1> 
<!--pageTitle Start-->

	
<!--//pageTitle End-->

<div class="subTitle_3">
	<h3><ikep4j:message pre="${preHeader}" key="subTitle.5" /></h3>
</div>

<!--blockListTable Start-->
<div id="divTab1" class="iKEP_tab">
		<ul>
			<li><a href="#tabs-1" id="dayTabButton"><ikep4j:message pre="${preField}" key="today" /></a></li>
			<li><a href="#tabs-2" id="weekTabButton"><ikep4j:message pre="${preField}" key="week" /></a></li>
			<li><a href="#tabs-3" id="monthTabButton"><ikep4j:message pre="${preField}" key="month" /></a></li>
			<li><a href="#tabs-4"  id="colleagueListButton"><ikep4j:message pre="${preField}" key="relation" /></a></li>
		</ul>
		<div>
			<div id="tabs-1">
				<div class="MyContentsTable">
					<table summary="My Document">
						<caption></caption>
						<tbody id="dayViewRow">
						<c:choose>
							<c:when test="${!empty result.tagList}">
							<c:forEach var="info" items="${result.tagList}"  varStatus="loopStatus">
								<tr class="msg_unread">
									<td scope="col" width="5%" class="tdFirst textRight_p20">${loopStatus.index+1}</td>
									<td scope="col" width="*" class="textLeft_p20"><a href="#a" onclick="searchCall('${info.tagName}');">${info.tagName}</a></td>
									<td scope="col" width="10%" class="textLeft_p20 tdLast">${info.tagFrequency}<ikep4j:message key="ui.support.searchpreprocessor.count" /></td>
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
				<!--blockButton_3 Start-->
				<c:if test="${result.next}">
				<div class="blockButton_3" id="dayNextButtonDiv"> 
					<a class="button_3" href="#a" id="daySearchBtn"><span><ikep4j:message pre="${preButton}" key="addview" arguments="20"/><img src="<c:url value='/base/images/icon/ic_more_ar.gif'/>" alt="" /></span></a>				
				</div>
				</c:if>
			</div>
			<div id="tabs-2">
				<div class="MyContentsTable">
					<table summary="My Document">
						<caption></caption>
						<tbody id="weekViewRow">
							<tr class="msg_unread">
									<td scope="col" colspan="3"><ikep4j:message key="ui.support.searchpreprocessor.emptyRecord" /></td>
							</tr>
						</tbody>
					</table>
				</div>
				<div class="blockButton_3" id="weekNextButtonDiv"> 
					<a class="button_3" href="#a" id="weekSearchBtn"><span><ikep4j:message pre="${preButton}" key="addview" arguments="20"/><img src="<c:url value='/base/images/icon/ic_more_ar.gif'/>" alt="" /></span></a>				
				</div>
			</div>
			<div id="tabs-3">
				<div class="MyContentsTable">
					<table summary="My Document">
						<caption></caption>
						<tbody id="monthViewRow">
						<tr class="msg_unread">
									<td scope="col" colspan="3"><ikep4j:message key="ui.support.searchpreprocessor.emptyRecord" /></td>
							</tr>
						</tbody>
					</table>
				</div>
				<div class="blockButton_3" id="monthNextButtonDiv"> 
					<a class="button_3" href="#a" id="monthSearchBtn"><span><ikep4j:message pre="${preButton}" key="addview" arguments="20"/><img src="<c:url value='/base/images/icon/ic_more_ar.gif'/>" alt="" /></span></a>				
				</div>
			</div>
			<div id="tabs-4">
				<div class="MyContentsTable">
					<table summary="My Document">
						<caption></caption>
						<tbody id="colleagueListView">
						<tr class="msg_unread">
									<td scope="col" colspan="2"><ikep4j:message key="ui.support.searchpreprocessor.emptyRecord" /></td>
							</tr>
						</tbody>
					</table>
				</div>
				<div class="blockButton_3" id="colleagueListButtonDiv"> 
					<a class="button_3" href="#a" id="colleagueSearchButton"><span><ikep4j:message pre="${preButton}" key="addview" arguments="20"/><img src="<c:url value='/base/images/icon/ic_more_ar.gif'/>" alt="" /></span></a>				
				</div>
			</div>
		</div>				
</div>	
	

<!--//blockListTable End-->


<!--//blockButton_3 End-->	


