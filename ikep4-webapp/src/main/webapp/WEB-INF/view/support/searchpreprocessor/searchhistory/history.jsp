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

Number.prototype.to2 = function() { return (this > 9 ? "" : "0")+this; };
Date.MONTHS = ["Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"];
Date.DAYS   = ["Sun", "Mon", "Tue", "Wed", "Tur", "Fri", "Sat"];
Date.prototype.getDateString = function(dateFormat) {
  var result = "";
  
  dateFormat = dateFormat == 8 && "YYYY.MM.DD" ||
               dateFormat == 6 && "HH:mm:ss" ||
               dateFormat ||
               "YYYY.MM.DD HH:mm:ss";
  for (var i = 0; i < dateFormat.length; i++) {
    result += dateFormat.indexOf("YYYY", i) == i ? (i+=3, this.getFullYear()                     ) :
              dateFormat.indexOf("YY",   i) == i ? (i+=1, String(this.getFullYear()).substring(2)) :
              dateFormat.indexOf("MMM",  i) == i ? (i+=2, Date.MONTHS[this.getMonth()]           ) :
              dateFormat.indexOf("MM",   i) == i ? (i+=1, (this.getMonth()+1).to2()              ) :
              dateFormat.indexOf("M",    i) == i ? (      this.getMonth()+1                      ) :
              dateFormat.indexOf("DDD",  i) == i ? (i+=2, Date.DAYS[this.getDay()]               ) :
              dateFormat.indexOf("DD",   i) == i ? (i+=1, this.getDate().to2()                   ) :
              dateFormat.indexOf("D"   , i) == i ? (      this.getDate()                         ) :
              dateFormat.indexOf("hh",   i) == i ? (i+=1, this.getHours().to2()                  ) :
              dateFormat.indexOf("h",    i) == i ? (      this.getHours()                        ) :
              dateFormat.indexOf("mm",   i) == i ? (i+=1, this.getMinutes().to2()                ) :
              dateFormat.indexOf("m",    i) == i ? (      this.getMinutes()                      ) :
              dateFormat.indexOf("ss",   i) == i ? (i+=1, this.getSeconds().to2()                ) :
              dateFormat.indexOf("s",    i) == i ? (      this.getSeconds()                      ) :
                                                   (dateFormat.charAt(i)                         ) ;
  }
  return result;
};
//-->
 </script> 
<script type="text/javascript">
<!--
(function($) {
	$(document).ready(function() {
		var startIndex =${result.endIndex};
		var oldIndex = 0;
		$("#searchBtn").click(function(){
			$.post('<c:url value="/support/searchpreprocessor/searchhistory/ajax/history.do"/>',
			 	{'startIndex':startIndex,'next':'20','searchOption':'month3'})
			 .success(function(data) {
				 if( !data.next ){
					 $("#nextButtonDiv").hide();
				 }
				 //start index new setting
				 oldIndex = startIndex;
				 startIndex = data.endIndex;
				 
				 $(data.historyList).each(function(index){
					 this.rowNum =oldIndex+(index+1);
					 this.registDate = new Date(this.registDate).getDateString('YYYY-MM-DD hh:mm:ss');
					 $.tmpl("rowtemplet",this).appendTo( $("#viewRow") );
				 });
			 })
			 .error(function(event, request, settings) { alert("error"); }); 
		});	
		
		$.template("rowtemplet", '<tr class="msg_unread">'+
									   '<td scope="col" width="5%" class="tdFirst textRight_p20">\${rowNum}</td>'+
									   '<td scope="col" width="*" class="textLeft_p20"><a href="#a" onclick="searchCall(\'${searchKeyword}\');">\${searchKeyword}</a></td>'+
									   '<td scope="col" width="20%" class="textLeft_p20 tdLast">\${registDate}</td>'+
									   '</tr>');
	});
})(jQuery);
//-->
 </script> 
<h1 class="none"><ikep4j:message pre="${preLeft}" key="0" /></h1> 
<!--pageTitle Start-->

	
<!--//pageTitle End-->

<div class="subTitle_3">
	<h3><ikep4j:message pre="${preHeader}" key="subTitle.0" /></h3>
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
					<td scope="col" width="*" class="textLeft_p20"><a href="#a" onclick="searchCall('${info.searchKeyword}');">${info.searchKeyword}</a></td>
					<td scope="col" width="20%" class="textLeft_p20 tdLast"><ikep4j:timezone  date="${info.registDate}"  pattern="message.support.searchpreprocessor.timezone.dateformat.yyyyMMddhhmmss" keyString="true"/></td>
				</tr>
			</c:forEach>	
			</c:when>
			<c:otherwise>
				<tr class="msg_unread">
					<td scope="col" colspan=3><ikep4j:message key="ui.support.searchpreprocessor.emptyRecord" /></td>
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


