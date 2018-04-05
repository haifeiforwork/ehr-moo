<%@ include file="/base/common/taglibs.jsp"%>
<%@ page language="java" errorPage="/common/error.jsp"  pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ taglib uri="/WEB-INF/tld/ikep4j.tld" prefix="ikep4j" %> 

<%-- 메시지 관련 Prefix 선언 Start --%> 
<c:set var="preHeader"  value="ui.lightpack.meetingroom.header" /> 
<c:set var="preDetail"  value="ui.lightpack.meetingroom.detail" />
<c:set var="preButton"  value="ui.lightpack.meetingroom.button" /> 
<c:set var="preMessage" value="ui.lightpack.meetingroom.message" />
<c:set var="preSearch"  value="ui.communication.common.searchCondition" /> 
<%-- 메시지 관련 Prefix 선언 End --%>
 
<script type="text/javascript" language="javascript">
<!-- 

(function($) {
	
	// onload시 수행할 코드
	$jq(document).ready(function() { 

	});
})(jQuery);  

//-->
</script>

<table summary="<ikep4j:message pre='${preHeader}' key='cartooletc' />">
	<caption></caption>
	<thead>
		<tr >
			<th scope="col" width="3%" class="textCenter">&nbsp;</th>
			
			<th scope="col" width="15%">
				장소
			</th>
			<th scope="col" width="15%">
				<ikep4j:message pre='${preDetail}' key='category'/>
			</th>
			<th scope="col" width="25%">
				<ikep4j:message pre='${preDetail}' key='cartooletcName'/>
			</th>
			<th scope="col" width="12%">
			 예약시스템 사용여부
			</th>
			<th scope="col" width="12%">
			 예약자동승인 여부
			</th>
			<th scope="col" width="10%">
				<ikep4j:message pre='${preDetail}' key='managerName'/>
			</th>
		</tr>
	</thead>

	
	<tbody>
		<c:choose>
	    <c:when test="${empty cartooletcList}">
		<tr>
			<td colspan="5" class="emptyRecord">
				<ikep4j:message pre='${preSearch}' key='emptyRecord' />
			</td> 
		</tr>				        
	    </c:when>
	    <c:otherwise>
		<c:forEach var="cartooletc" items="${cartooletcList}" varStatus="status">
		<tr id="${cartooletc.categoryId}" >
			<td class="textCenter">
				<input type="checkbox" id="chkCartooletcId" name="chkCartooletcId" value="${cartooletc.cartooletcId}" title="checkbox"/>
    		</td>
    		<td class="textCenter">${cartooletc.regionName}</td>
			<td class="textCenter">${cartooletc.categoryName}</td>
			<td class="textCenter"><a href="#a" onclick="getView('${cartooletc.cartooletcId}')">${cartooletc.cartooletcName}</a></td>
			<td class="textCeneter"><c:if test="${'1'==cartooletc.useFlag}">사용중</c:if><c:if test="${'0'==cartooletc.useFlag}">사용중지중</c:if></td>
			<td class="textCeneter"><c:if test="${cartooletc.autoApprove != 'N'}">자동승인</c:if><c:if test="${cartooletc.autoApprove == 'N'}">담당자승인</c:if></td>
			<td class="textCenter">${cartooletc.managerName}</td>
		</tr>
		</c:forEach>				      
	    </c:otherwise> 
		</c:choose>  																																																						
	</tbody>
</table>	