<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/base/common/taglibs.jsp"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="/WEB-INF/tld/ikep4j.tld" prefix="ikep4j" %> 
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
		$("#realSearchBtn").data("endIndex", ${result.endIndex}).data("option","real");
		$("#monthSearchBtn").data("endIndex", 0).data("option","month");
		$("#weekSearchBtn").data("endIndex", 0).data("option","week");
		$("#daySearchBtn").data("endIndex", 0).data("option","day");
		
		
		$("#dayTabButton").data("option","day");
		$("#weekTabButton").data("option","week");
		$("#monthTabButton").data("option","month");
		$("#realTabButton").data("option","real");
		
		$("a[id$='TabButton']").click(function(){
			var setting ={'searchOption':$(this).data("option"),'clear':true};
			ajaxCall(setting);
		});
		
		var ajaxCall= function(settings) {
			
				var oldIndex=0;
				var config ={'startIndex':'0','next':'20','searchOption':'real'};
	     		if (settings) $.extend(config, settings); 
	     		var isEmpty=true;
	     		//alert( JSON.stringify(config));
	     		
				 $.post('<c:url value="/support/searchpreprocessor/searchhistory/ajax/popular.do"/>',
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
					 
					 $(data.historyList).each(function(){
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
					 
					 $(data.historyList).each(function(){
						 oldIndex++;
						 this.index =oldIndex;
						 
						 if( data.searchOption =='real' )
						 {
							 var upCount  = (parseInt(this.prevFrequencyCount) - parseInt(this.initFrequencyCount)  );
								
							 if( parseInt(this.initFrequencyCount) <= 0 ){
								 $.tmpl("rowtempletrealnew",this).appendTo( $("#"+config.searchOption+"ViewRow") );
							 }	 
							 else if( upCount > 0 ) 
							 {
								 this.initFrequencyCount  = upCount;
							 	$.tmpl("rowtempletrealdown",this).appendTo( $("#"+config.searchOption+"ViewRow") );
							 }	
							 else if( upCount < 0 )
							 {
								 this.initFrequencyCount  = (-1)*upCount;
							 	$.tmpl("rowtempletrealup",this).appendTo( $("#"+config.searchOption+"ViewRow") );
							 }
							 else if( parseInt(this.initFrequencyCount)!=0 && upCount == 0)
							 {
								 this.initFrequencyCount  = "-";
							 	$.tmpl("rowtempletrealno",this).appendTo( $("#"+config.searchOption+"ViewRow") );
							 }
							 else 
							 	$.tmpl("rowtempletrealnew",this).appendTo( $("#"+config.searchOption+"ViewRow") );
						 }
						 else
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
				   '<td scope="col" width="*" class="textLeft_p20"><a href="#a" onclick="searchCall(\'${searchKeyword}\');">\${searchKeyword}</a></td>'+
				   '<td scope="col" width="10%" class="textLeft_p20 tdLast">\${frequencyCount}<ikep4j:message key="ui.support.searchpreprocessor.count" /></td>'+
				   '</tr>');
		
		$.template("rowtempletrealup", '<tr class="msg_unread">'+
									   '<td scope="col" width="5%" class="tdFirst textRight_p20">\${index}</td>'+
									   '<td scope="col" width="*" class="textLeft_p20"><a href="#a" onclick="searchCall(\'${searchKeyword}\');">\${searchKeyword}</a></td>'+
									   '<td scope="col" width="10%" class="textLeft_p20">\${frequencyCount}<ikep4j:message key="ui.support.searchpreprocessor.count" /></td>'+
									   '<td scope="col" width="10%" class="textCenter tdLast"><p class="ic_searchTop"><span>top</span></p><span class="search_num">\${initFrequencyCount}</span></td>'+
									   '</tr>');
		
		$.template("rowtempletrealdown", '<tr class="msg_unread">'+
				   '<td scope="col" width="5%" class="tdFirst textRight_p20">\${index}</td>'+
				   '<td scope="col" width="*" class="textLeft_p20"><a href="#a" onclick="searchCall(\'${searchKeyword}\');">\${searchKeyword}</a></td>'+
				   '<td scope="col" width="10%" class="textLeft_p20">\${frequencyCount}<ikep4j:message key="ui.support.searchpreprocessor.count" /></td>'+
				   '<td scope="col" width="10%" class="textCenter tdLast"><p class="ic_searchDown"><span>down</span></p><span class="search_num">\${initFrequencyCount}</span></td>'+
				   '</tr>');
		
		$.template("rowtempletrealnew", '<tr class="msg_unread">'+
				   '<td scope="col" width="5%" class="tdFirst textRight_p20">\${index}</td>'+
				   '<td scope="col" width="*" class="textLeft_p20"><a href="#a" onclick="searchCall(\'${searchKeyword}\');">\${searchKeyword}</a></td>'+
				   '<td scope="col" width="10%" class="textLeft_p20">\${frequencyCount}<ikep4j:message key="ui.support.searchpreprocessor.count" /></td>'+
				   '<td scope="col" width="10%" class="textCenter tdLast"><p class="colorPoint">New</p></td>'+
				   '</tr>');
		$.template("rowtempletrealno", '<tr class="msg_unread">'+
				   '<td scope="col" width="5%" class="tdFirst textRight_p20">\${index}</td>'+
				   '<td scope="col" width="*" class="textLeft_p20"><a href="#a" onclick="searchCall(\'${searchKeyword}\');">\${searchKeyword}</a></td>'+
				   '<td scope="col" width="10%" class="textLeft_p20">\${frequencyCount}<ikep4j:message key="ui.support.searchpreprocessor.count" /></td>'+
				   '<td scope="col" width="10%" class="textCenter tdLast"><p class="colorPoint">-</p></td>'+
				   '</tr>');
	});
})(jQuery);
//-->
</script> 
<h1 class="none"><ikep4j:message pre="${preLeft}" key="3" /></h1> 
<!--pageTitle Start-->

	
<!--//pageTitle End-->

<div class="subTitle_3">
	<h3><ikep4j:message pre="${preHeader}" key="subTitle.3" /></h3>
</div>

<!--blockListTable Start-->
<div id="divTab1" class="iKEP_tab">
		<ul>
			<li><a href="#tabs-1" id="realTabButton"><ikep4j:message pre="${preField}" key="real" /></a></li>
			<li><a href="#tabs-2" id="dayTabButton"><ikep4j:message pre="${preField}" key="today" /></a></li>
			<li><a href="#tabs-3" id="weekTabButton"><ikep4j:message pre="${preField}" key="week" /></a></li>
			<li><a href="#tabs-4" id="monthTabButton"><ikep4j:message pre="${preField}" key="month" /></a></li>
		</ul>
		<div>
		    <div id="tabs-1">
				<div class="MyContentsTable">
					<table summary="My Document">
						<caption></caption>
						<tbody id="realViewRow">
						<c:choose>
							<c:when test="${!empty result.historyList}">
							<c:forEach var="info" items="${result.historyList}"  varStatus="loopStatus">
								<tr class="msg_unread">
									<td scope="col" width="5%" class="tdFirst textRight_p20">${loopStatus.index+1}</td>
									<td scope="col" width="*" class="textLeft_p20"><a href="#a" onclick="searchCall('${info.searchKeyword}');">${info.searchKeyword}</a></td>
									<td scope="col" width="10%" class="textLeft_p20">${info.frequencyCount}<ikep4j:message key="ui.support.searchpreprocessor.count" /></td>
									<td scope="col" width="10%" class="textCenter tdLast">
										<c:if test="${result.searchOption eq 'real'}">
										<c:set var="upCount" value="${info.prevFrequencyCount - info.initFrequencyCount}"/>
										<c:choose>
											<c:when test="${info.initFrequencyCount eq 0}"><p class="colorPoint">New</p></c:when>
											<c:when test="${upCount<0}"><p class="ic_searchTop"><span>top</span></p><span class="search_num">${-1*upCount}</span></c:when>
											<c:when test="${upCount>0}"><p class="ic_searchDown"><span>down</span></p><span class="search_num">${upCount}</span></c:when>
											<c:when test="${info.initFrequencyCount ne  0 && upCount eq 0 }"><p class="colorPoint">-</p></c:when>
											<c:otherwise><p class="colorPoint">New</p></c:otherwise>
										</c:choose>										
									</c:if>
									</td>
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
				<div class="blockButton_3" id="realNextButtonDiv"> 
					<a class="button_3" href="#a" id="realSearchBtn"><span><ikep4j:message pre="${preButton}" key="addview" arguments="20"/><img src="<c:url value='/base/images/icon/ic_more_ar.gif'/>" alt="" /></span></a>				
				</div>
				</c:if>
			</div> 
			<div id="tabs-2">
				<div class="MyContentsTable">
					<table summary="My Document">
						<caption></caption>
						<tbody id="dayViewRow">
						<tr class="msg_unread">
									<td scope="col" colspan="3"><ikep4j:message key="ui.support.searchpreprocessor.emptyRecord" /></td>
							</tr>
						</tbody>
					</table>
				</div>
				
				<div class="blockButton_3" id="dayNextButtonDiv"> 
					<a class="button_3" href="#a" id="daySearchBtn"><span><ikep4j:message pre="${preButton}" key="addview" arguments="20"/><img src="<c:url value='/base/images/icon/ic_more_ar.gif'/>" alt="" /></span></a>				
				</div>
			</div>
			<div id="tabs-3">
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
			<div id="tabs-4">
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
		</div>				
</div>	
	

<!--//blockListTable End-->


<!--//blockButton_3 End-->	

