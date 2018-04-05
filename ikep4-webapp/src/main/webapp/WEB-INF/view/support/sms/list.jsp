<%@ include file="/base/common/taglibs.jsp"%>
<%@ page language="java" errorPage="/base/common/error.jsp"  pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>

<%@ taglib uri="/WEB-INF/tld/ikep4j.tld" prefix="ikep4j" %> 
<%pageContext.setAttribute("crlf", "\r\n"); %>

<c:set var="preList"    value="ui.support.sms.list" />
<c:set var="preSearch"  value="ui.communication.common.searchCondition" />
 
<c:set var="user" value="${sessionScope['ikep.user']}" />
<c:set var="portal" value="${sessionScope['ikep.portal']}" />

<script>
<!-- 
(function($) { 
	$(document).ready(function() { 
		//발신함의 보기 모드 설정(리스트모드,액정모드)
		$("input[name=curMode]").click(function() {
			$("input[name=pageIndex]").val("1");
			$("#searchForm").submit(); 
		});
	});
})(jQuery); 
//발신함 닫기
function closeWin() {
	parent.ifrmHide();
}
//-->
</script> 
	<!--sms_sendinglist Start-->
	<form id="searchForm" method="post" action="<c:url value='/support/sms/listSms.do' />">
	<div class="sms_sendinglist" style="width:336px; height:403px;top:0px;">
		<h2><ikep4j:message pre='${preList}' key='title' /></h2>
		<div class="sms_sendinglist_radio">
			<spring:bind path="searchCondition.curMode">
			<label><input name="${status.expression}" class="radio" title="<ikep4j:message pre='${preList}' key='listMode' />" type="radio" <c:if test="${status.value == '1'}">checked</c:if> value="1" ><ikep4j:message pre='${preList}' key='listMode' /></label>&nbsp;
			<label><input name="${status.expression}" class="radio" title="<ikep4j:message pre='${preList}' key='screenMode' />"   type="radio" <c:if test="${status.value == '2'}">checked</c:if> value="2" ><ikep4j:message pre='${preList}' key='screenMode' /></label>
			</spring:bind>
		</div>
		<div class="sms_sendinglist_close" style="right:15px;"><a href="#a" onclick="closeWin()"><img src="<c:url value='/base/images/icon/ic_sms_layer_x.gif' />" alt="닫기" /></a></div>

		<div id="loadMain">
		<c:if test="${searchCondition.curMode == '1'}">
		<table summary="receiver, registDate, resultCode, contents">
			<thead>
				<tr>
					<th scope="col" width="25%"><ikep4j:message pre='${preList}' key='receiver' /></th>
					<th scope="col" width="17%"><ikep4j:message pre='${preList}' key='registDate' /></th>
					<th scope="col" width="20%"><ikep4j:message pre='${preList}' key='resultCode' /></th>
					<th scope="col" width="38%"><ikep4j:message pre='${preList}' key='contents' /></th>	
				</tr>
			</thead>
			<tbody>
			 	<c:forEach var="sms" items="${searchResult.entity}" varStatus="smsLoopCount">
			 		<c:if test="${sms.resultCode == '0'}">
			 			<c:set var="resultName"><span class=pass><ikep4j:message pre='${preList}' key='resultCode1' /></span></c:set>
			 		</c:if>
			 		<c:if test="${sms.resultCode == '99'}">
			 			<c:set var="resultName"><span class=pass><ikep4j:message pre='${preList}' key='resultCode2' /></span></c:set>
			 		</c:if>
			 		<c:if test="${sms.resultCode == '-100'}">
			 			<c:set var="resultName"><span class=pass><ikep4j:message pre='${preList}' key='resultCode3' /></span></c:set>
			 		</c:if>
			 		<c:if test="${sms.resultCode != '0' && sms.resultCode != '99' && sms.resultCode != '-100'  }">
                        <c:set var="resultName"><span class=pass><ikep4j:message pre='${preList}' key='resultCode4' /></span></c:set>
                    </c:if>
					<tr class="bgWhite">
					    <c:choose>
					    	<c:when test="${user.localeCode == portal.defaultLocaleCode}">
					      		<c:if test="${sms.receiverName == null }">
					      			<td>${sms.receiverPhoneno}</td>
					      		</c:if>
					      		<c:if test="${sms.receiverName != null }">
					       			<td>${sms.receiverName}</td>
					       		</c:if>					    	
					      	</c:when>
					      	<c:otherwise>
					      		<c:if test="${sms.receiverName == null }">
					      			<td>${sms.receiverPhoneno}</td>
					      		</c:if>
					      		<c:if test="${sms.receiverName != null }">
					       			<td>${sms.receiverEnglishName}</td>
					       		</c:if>
					      	</c:otherwise>
					    </c:choose>						
						<td><ikep4j:timezone date="${sms.registDate}" pattern="message.support.sms.timezone.dateformat.type" keyString="true"/></td>
						<td>${resultName}</td>
						<td style="text-align:left"><font title="${sms.contents}" style="cursor:hand;">${ikep4j:cutString(sms.contents,20,"..")}</font></td>
					</tr>
				</c:forEach> 													
			</tbody>
		</table> 
		<!--Page Numbur Start--> 
		<spring:bind path="searchCondition.pageIndex">
		<ikep4j:pagination searchFormId="searchForm" pageIndexInput="${status.expression}" searchCondition="${searchCondition}" />
		<input name="${status.expression}" type="hidden" value="${status.value}" title="<ikep4j:message pre='${preSearch}' key='${status.expression}'/>" />
		</spring:bind>  
		<!--//Page Numbur End--> 
		</c:if>	
		
		<c:if test="${searchCondition.curMode eq '2'}">
		<ul class="sms_sendinglist_win">
			<c:forEach var="sms" items="${searchResult.entity}" varStatus="smsLoopCount">		
			<li>
				<div class="sms_window" title="${sms.contents }" style="cursor:hand;">
					<% pageContext.setAttribute("newLineChar", "\n"); %>
					<p>${fn:replace(ikep4j:cutString(sms.contents,95,".."), newLineChar, "<br />")}</p>
					<c:if test="${sms.resultCode == '0'}">
					<img src="<c:url value='/base/images/common/btn_sms_01.gif' />" alt="<ikep4j:message pre='${preList}' key='resultCode1' />" />
					</c:if>
					<c:if test="${sms.resultCode == '99'}">
					<img src="<c:url value='/base/images/common/btn_sms_02.gif' />" alt="<ikep4j:message pre='${preList}' key='resultCode2' />" />
					</c:if>	
					<c:if test="${sms.resultCode == '-100'}">
					<img src="<c:url value='/base/images/common/btn_sms_03.gif' />" alt="<ikep4j:message pre='${preList}' key='resultCode3' />" />
					</c:if>	
					<c:if test="${sms.resultCode != '0' && sms.resultCode != '99' && sms.resultCode != '-100'  }">
					<img src="<c:url value='/base/images/common/sms_send_fail.gif' />" alt="<ikep4j:message pre='${preList}' key='resultCode4' />" />
                    </c:if> 					
				</div>
				<div class="sms_win_info">
					<p><ikep4j:message pre='${preList}' key='receiverPhoneno' /> : ${sms.receiverPhoneno}</p>
					<p><ikep4j:message pre='${preList}' key='receiverName' /> :
					    <c:choose>
					    	<c:when test="${user.localeCode == portal.defaultLocaleCode}">

					       			${sms.receiverName}
					       		
					      	</c:when>
					      	<c:otherwise>

					       			${sms.receiverEnglishName}
					      	
					      	</c:otherwise>
					    </c:choose>					
					</p>
					<p><ikep4j:message pre='${preList}' key='registDate' /> : <ikep4j:timezone date="${sms.registDate}" pattern="message.support.sms.timezone.dateformat.type" keyString="true"/></p>
				</div>
			</li>
			</c:forEach> 		
		</ul>
		<table border=0><tr class="bgWhite"><td  height=1></td></tr></table> 		
		<!--Page Numbur Start--> 
		<spring:bind path="searchCondition.pageIndex">
		<ikep4j:pagination searchFormId="searchForm" pageIndexInput="${status.expression}" searchCondition="${searchCondition}" />
		<input name="${status.expression}" type="hidden" value="${status.value}" title="<ikep4j:message pre='${preSearch}' key='${status.expression}'/>" />
		</spring:bind>  
		<!--//Page Numbur End--> 		
		</c:if> 
		</div> 		
	</div>
	</form>
	<!--//sms_sendinglist End-->
