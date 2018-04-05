<%@ page language="java" errorPage="/base/common/error.jsp"  pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ include file="/base/common/taglibs.jsp"%>
<%@ taglib uri="/WEB-INF/tld/ikep4j.tld" prefix="ikep4j" %> 
<c:set var="sessionPortal" value="${sessionScope['ikep.portal']}" />
<c:set var="sessionUser" value="${sessionScope['ikep.user']}" />

<c:set var="preButton"  value="ui.servicepack.usagetracker.common.button" /> 
<c:set var="preCommon"  value="ui.servicepack.usagetracker.common" /> 
<c:set var="preContent"  value="ui.servicepack.usagetracker.logconfig" /> 
<c:set var="preConfirm"  value="message.servicepack.usagetracker.common.confirm" /> 
<c:set var="preSucess"  value="message.servicepack.usagetracker.common.sucess" /> 

<script  type="text/javascript">
<!--   
(function($) {  
	$(document).ready(function() {  
		$("#btnSave").click(function() {  
			if(confirm("<ikep4j:message pre='${preConfirm}' key='save'/>")){
			  $.post('<c:url value="/servicepack/usagetracker/logconfig/update.do"/>', $("#logConfig").serialize()).success(function(data){
					alert("<ikep4j:message pre='${preSucess}' key='save'/>.");
		       }).error(function(event, request, settings){
		           alert("error");
		       });		
			}
		 });			
	}); 
})(jQuery);
//-->
</script>

<form id="logConfig" action="">
				<h1 class="none">컨텐츠영역</h1>

				<!--pageTitle Start-->
				<div id="pageTitle">
					<h2><ikep4j:message pre='${preContent}' key='pageTitle'/></h2>
				</div>
				<!--//pageTitle End-->

				<!--blockDetail Start-->
				<div class="blockDetail">
					<table summary="Log Setting">
						<caption></caption>
						<tbody>
							<tr>
								<th scope="row" colspan="4" width="15%"><ikep4j:message pre='${preContent}' key='logtarget'/></th>
								<th scope="row" class="textCenter"><ikep4j:message pre='${preContent}' key='login'/></th>
								<th scope="row" class="textCenter"><ikep4j:message pre='${preContent}' key='menu'/></th>
								<th scope="row" class="textCenter"><ikep4j:message pre='${preContent}' key='portlet'/></th>
								<th scope="row" class="textCenter"><ikep4j:message pre='${preContent}' key='responseTime'/></th>
							</tr>	
							<tr>
								<th scope="row" colspan="4" ><ikep4j:message pre='${preContent}' key='logusage'/></th>

							<c:forEach var="tmp" items="${logConfigList}" varStatus="inx">
								<td class="textCenter">                                                     
									<label><input type="radio" class="radio" title="<ikep4j:message pre='${preCommon}' key='useage.0'/>"  name="cfg${tmp.logTarget}" value="${tmp.logTarget}|0" <c:if test="${tmp.usage eq '0'}">checked="checked"</c:if>/><ikep4j:message pre='${preCommon}' key='useage.0'/></label>&nbsp;
									<label><input type="radio" class="radio" title="<ikep4j:message pre='${preCommon}' key='useage.1'/>" name="cfg${tmp.logTarget}" value="${tmp.logTarget}|1" <c:if test="${tmp.usage eq '1'}">checked="checked"</c:if>/><ikep4j:message pre='${preCommon}' key='useage.1'/></label>
								</td>
							</c:forEach>
							</tr>	
						</tbody>
					</table>
				</div>
				<!--//blockDetail End-->
										
				<!--blockButton Start-->
				<div class="blockButton"> 
					<ul>
						<li><a class="button" href="#a" id="btnSave"><span><ikep4j:message pre='${preButton}' key='save'/></span></a></li>
					</ul>
				</div>
				<!--//blockButton End-->
</form>

